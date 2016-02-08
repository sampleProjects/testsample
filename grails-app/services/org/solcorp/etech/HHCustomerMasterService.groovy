package org.solcorp.etech

import java.util.Date;

import grails.converters.JSON
import grails.util.GrailsUtil

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


class HHCustomerMasterService {
	
	def grailsApplication
	
	/*def importHHCustomersInLocal() {
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		
		def newHHCustomerList = HHCustomerMaster.findAll()
		recordsToBeProcessed = newHHCustomerList?.size()
		
		log.info "Customers to be imported: " + recordsToBeProcessed
		
		newHHCustomerList.each { hhCustomerInstance ->
			def logString = "HHCustomerId: " + hhCustomerInstance.hhCustId
			
			recordsProcessed ++
			
			println "hhCustomerInstance: " + hhCustomerInstance
			
			log.info "Processing Record# ${recordsProcessed}: " + logString
			
			def tempHHCustomerMasterInstance = new TempHHCustomerMaster(hhCustomerInstance).save(flush: true, failOnError: true)
			recordsInserted++
			
			println "Records Inserted# ${recordsInserted}"
		}
	}*/
	
	def importHHCustomers(def currentJobId) {
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		def recordsUpdated = 0
		def recordsFailed = 0 
		
		def insertedRecordsList = []
		def updatedRecordsList = []
		def failedRecordsList = []
		
		def currentJob = JobRegister.read(currentJobId)
		
		def newHHCustomerList = HHCustomerMaster.findAllByDescrshort(Constants.HH_CUSTOMERMASTERIMPORTSTATUS_LOADED)		
		recordsToBeProcessed = newHHCustomerList?.size()
		
		log.info "Customers to be imported: " + recordsToBeProcessed
		
		User systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		log.info "System Job User found: " + systemJobUser
		
		def customerCodeList = Customer.findAll().code
		
		def defaultAccountExecutiveEmployee = Employee.findByCode(Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE) 
		
		for (HHCustomerMaster hhCustomerInstance : newHHCustomerList) {
			if(grailsApplication.config.shouldJobrun) {
				try {
					def resultMap = processRecord(hhCustomerInstance, recordsProcessed, customerCodeList, systemJobUser, updatedRecordsList, 
						recordsUpdated, defaultAccountExecutiveEmployee, insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed, currentJob)
					
					recordsProcessed = resultMap.recordsProcessed
					recordsFailed = resultMap.recordsFailed
					recordsInserted = resultMap.recordsInserted
					recordsUpdated = resultMap.recordsUpdated
				} catch (Exception e) {
					e.printStackTrace()
				}
			} else {
				log.info "Emergency Stop. ImportCustomerJob is terminated gracefully."
				break;
			}
		}
		
		// Create import job summary and save along with the job
		return getImportProjectSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList,
			updatedRecordsList, failedRecordsList)
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def processRecord(hhCustomerInstance, recordsProcessed, customerCodeList, systemJobUser, updatedRecordsList, 
		recordsUpdated, defaultAccountExecutiveEmployee, insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed, currentJob) {
		
		recordsProcessed++
		
		def logString = "HHCustomerId: " + hhCustomerInstance.hhCustId
		def customerInstance = null
		
		try {
			// Check if the Customer in concern is already available
			log.info "Checking if the customer exists for: " + logString
			 
			if(customerCodeList?.contains(hhCustomerInstance.hhCustId)) {
				// Customer is available
				// Update operation is required
				
				customerInstance = Customer.findByCode(hhCustomerInstance.hhCustId)
				
				log.info "Customer: ${customerInstance.id} exists for: " + logString
				
				// Check if the data is modified
				if(customerInstance.name != hhCustomerInstance.name1 ||
					 customerInstance.status != Customer.getCustomerStatusFromHHCustomerStatus(hhCustomerInstance.custStatus)) {
				
					customerInstance.name = hhCustomerInstance.name1
					customerInstance.status = Customer.getCustomerStatusFromHHCustomerStatus(hhCustomerInstance.custStatus)
					customerInstance.updatedBy = systemJobUser
					customerInstance.save(flush: true, failOnError: true)
					
					ImportCustomerJobRecordDTO updatedCustomerRecord = new ImportCustomerJobRecordDTO(hhCustomerInstance.hhCustId, customerInstance.id, "Update Successful")
					updatedRecordsList.add(updatedCustomerRecord)
					
					logString = "Update: " + logString
					
					// Insert this record in CustomerJobLineRecord for future reference
					ImportCustomerJobLineRecord successLineRecord = new ImportCustomerJobLineRecord(hhCustomerInstance, currentJob, customerInstance.id, logString, ImportCustomerStatusType.UPDATE_SUCCESS, systemJobUser).save(flush: true)
					
					recordsUpdated++
					
				} else {
					 logString = "No Update Required: " + logString
					 
					 // Insert this record in ExpenseJobLineRecord for future reference
					ImportCustomerJobLineRecord successLineRecord = new ImportCustomerJobLineRecord(hhCustomerInstance, currentJob, customerInstance.id, logString, ImportCustomerStatusType.UPDATE_NOT_REQUIRED_SUCCESS, systemJobUser).save(flush: true)
				}
			} else {
				// Customer is new
				// insert operation is required
				
				log.info "No customer exists for: " + logString
			
				customerInstance = Customer.createCustomerFromHHCustomer(hhCustomerInstance, systemJobUser, defaultAccountExecutiveEmployee)
				customerInstance.save(flush: true, failOnError: true)
				
				ImportCustomerJobRecordDTO insertedCustomerRecord = new ImportCustomerJobRecordDTO(hhCustomerInstance.hhCustId, customerInstance.id, "Insert Successful")
				insertedRecordsList.add(insertedCustomerRecord)
				
				logString = "Insert: " + logString
				
				recordsInserted++
				
				// Insert this record in ExpenseJobLineRecord for future reference
				ImportCustomerJobLineRecord successLineRecord = new ImportCustomerJobLineRecord(hhCustomerInstance, currentJob, customerInstance.id, logString, ImportCustomerStatusType.INSERT_SUCCESS, systemJobUser).save(flush: true)
			}
			
			
			hhCustomerInstance.descrshort = Constants.HH_CUSTOMERMASTERIMPORTSTATUS_IMPORTED
			hhCustomerInstance.save(flush: true)
			
			logString = "Success: " + logString + ", customerId: " + customerInstance?.id
			
			log.info "Rec#: ${recordsProcessed} :: " + logString
		} catch (Exception e) {
			ImportCustomerJobRecordDTO failedCustomerRecord = new ImportCustomerJobRecordDTO(hhCustomerInstance?.hhCustId, customerInstance?.id, e.getCause())
			failedRecordsList.add(failedCustomerRecord)
			
			recordsFailed++
			
			logString = "Failure: " + logString + ", customerId: " + customerInstance?.id
			
			// Insert this record in ExpenseJobLineRecord for future reference
			ImportCustomerJobLineRecord failedLineRecord = new ImportCustomerJobLineRecord(hhCustomerInstance, currentJob, null, e.getCause(), ImportCustomerStatusType.OTHER, systemJobUser).save(flush: true)
			
			log.error e
			log.info "Rec#: ${recordsProcessed} :: " + logString
			
			throw new RuntimeException(e)
		}
		
		return [recordsProcessed: recordsProcessed, recordsUpdated: recordsUpdated, recordsFailed: recordsFailed, recordsInserted: recordsInserted]
	}
	
	def getImportProjectSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList, 
		updatedRecordsList, failedRecordsList) {

		ImportCustomerJobSummaryDTO summary = new ImportCustomerJobSummaryDTO()
		summary.recordsToBeProcessed = recordsToBeProcessed
		summary.recordsProcessed = recordsProcessed
		summary.recordsInserted = recordsInserted
		summary.recordsUpdated = recordsUpdated
		summary.recordsFailed = recordsFailed
		
		summary.insertedRecordsList = insertedRecordsList
		summary.updatedRecordsList = updatedRecordsList
		summary.failedRecordsList = failedRecordsList
		
		return summary as JSON
	}
}
