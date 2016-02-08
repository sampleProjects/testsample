package org.solcorp.etech

import grails.converters.JSON
import grails.util.GrailsUtil

import org.solcorp.etech.utils.DateFormatUtils
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


class HHEmployeeMasterService {
	
	def grailsApplication
	
	def getRecordsForImport() {
		def hhEmployeeMasterCriteria = HHEmployeeMaster.createCriteria()
		def recordsForImportList = hhEmployeeMasterCriteria.list() {
			and {
				eq("importStatus", Constants.HH_EMPLOYEE_IMPORTSTATUS_LOADED)
			}
		}
		
		return recordsForImportList
	}
	
	def importHHEmployees(def currentJobId) {
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		def recordsUpdated = 0
		def recordsFailed = 0 
		
		def insertedRecordsList = []
		def updatedRecordsList = []
		def failedRecordsList = []
		
		def currentJob = JobRegister.read(currentJobId)
		
		/*def newHHEmployeeList = []
		newHHEmployeeList.add(HHEmployeeMaster.findByEmployeeId('150173'))*/
		
		def newHHEmployeeList = getRecordsForImport()
		//def newHHEmployeeList = HHEmployeeMaster.findAll()
		recordsToBeProcessed = newHHEmployeeList.size()
		
		log.info "Employees to be imported: " + recordsToBeProcessed
		
		User systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		log.info "System Job User found: " + systemJobUser
		
		for (HHEmployeeMaster hhEmployeeInstance : newHHEmployeeList) {
			if(grailsApplication.config.shouldJobrun) {
				try {
					def resultMap = processRecord(hhEmployeeInstance, recordsProcessed, systemJobUser, updatedRecordsList, 
						recordsUpdated, insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed, currentJob)
			
					recordsProcessed = resultMap.recordsProcessed
					recordsFailed = resultMap.recordsFailed
					recordsInserted = resultMap.recordsInserted
					recordsUpdated = resultMap.recordsUpdated
				} catch (Exception e) {
					e.printStackTrace()
				}
			} else {
				log.info "Emergency Stop. ImportEmployeeJob is terminated gracefully."
				break;
			}
		}
		
		// Create import job summary and save along with the job
		return getImportEmployeeSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList,
			updatedRecordsList, failedRecordsList)
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def processRecord(hhEmployeeInstance, recordsProcessed, systemJobUser, updatedRecordsList, recordsUpdated, 
		insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed, currentJob) {
		
		recordsProcessed++
		
		def logString = "HHEmployeeId: " + hhEmployeeInstance.employeeId
		def employeeInstance = null
		try {
			// Check if the Employee in concern is already available
			log.info "Checking if the employee exists for: " + logString
			 
			employeeInstance = Employee.findByCode(hhEmployeeInstance.employeeId)
			if(employeeInstance) {
				// Employee is available
				// Update operation is required
				
				log.info "Employee: ${employeeInstance.id} exists for: " + logString
				
				// Fetch PayDept from SourceData for comparison
				def deptList = Department.getDepartmentByCode(hhEmployeeInstance.department, hhEmployeeInstance.department?.isNumber())
				def sourceHHPayDept = null
				if(deptList.size() > 0) {
					sourceHHPayDept = deptList?.get(0)
				}
				
				// Check if the data is modified
				if(employeeInstance.lastName != hhEmployeeInstance.lastName
					|| employeeInstance.firstName != hhEmployeeInstance.firstName
					|| employeeInstance.employeeType != Employee.getEmployeeTypeFromHHEmployeeType(hhEmployeeInstance.type)
					|| (sourceHHPayDept != null && employeeInstance.payDept.id != sourceHHPayDept?.id)) {
					
					// Yes
					// Actual update required
					employeeInstance.lastName = hhEmployeeInstance.lastName
					employeeInstance.firstName = hhEmployeeInstance.firstName
					employeeInstance.updatedBy = systemJobUser
					employeeInstance.employeeType = Employee.getEmployeeTypeFromHHEmployeeType(hhEmployeeInstance.type)
					
					// Set PayDept
					def payDept = Department.findByCode(hhEmployeeInstance.department)
					if(! payDept) {
						employeeInstance.payDept = sourceHHPayDept
					} else {
						employeeInstance.payDept = payDept
					}
					
					logString = "Update: " + logString
					employeeInstance.save(flush: true, failOnError: true)
				
					ImportEmployeeJobRecordDTO updatedEmployeeRecord = new ImportEmployeeJobRecordDTO(hhEmployeeInstance.employeeId, employeeInstance.id, "Update Successful")
					updatedRecordsList.add(updatedEmployeeRecord)
					
					// Change the importStatus of the source record if required
					if(hhEmployeeInstance.importStatus != Constants.HH_EMPLOYEE_IMPORTSTATUS_IMPORTED) {
						hhEmployeeInstance.importStatus = Constants.HH_EMPLOYEE_IMPORTSTATUS_IMPORTED
					}
					
					// Set current date
					hhEmployeeInstance.importStatusDate = new Date()
					hhEmployeeInstance.save(flush: true, failOnError: true)
					
					// Insert this record in EmployeeJobLineRecord for future reference
					ImportEmployeeJobLineRecord successLineRecord = new ImportEmployeeJobLineRecord(hhEmployeeInstance, currentJob, employeeInstance.id, logString, ImportEmployeeStatusType.UPDATE_SUCCESS, systemJobUser).save(flush: true)
					
					recordsUpdated++
				} else {
					logString = "No Update Required: " + logString
					
					// Change the importStatus of the source record if required
					if(hhEmployeeInstance.importStatus != Constants.HH_EMPLOYEE_IMPORTSTATUS_IMPORTED) {
						hhEmployeeInstance.importStatus = Constants.HH_EMPLOYEE_IMPORTSTATUS_IMPORTED
					}
					
					// Set current date
					hhEmployeeInstance.save(flush: true, failOnError: true)
				}
			} else {
				// Employee is new
				// insert operation is required
				
				log.info "No employee exists for: " + logString
			
				employeeInstance = Employee.createEmployeeFromHHEmployee(hhEmployeeInstance, systemJobUser)
				if(employeeInstance.payDept != null) {
					logString = "Insert: " + logString
					employeeInstance.save(flush: true, failOnError: true)
					
					ImportEmployeeJobRecordDTO insertedEmployeeRecord = new ImportEmployeeJobRecordDTO(hhEmployeeInstance.employeeId, employeeInstance.id, "Insert Successful")
					insertedRecordsList.add(insertedEmployeeRecord)
					
					// Change the importStatus and date of the source record
					hhEmployeeInstance.importStatus = Constants.HH_EMPLOYEE_IMPORTSTATUS_IMPORTED
					hhEmployeeInstance.importStatusDate = new Date()
					hhEmployeeInstance.save(flush: true, failOnError: true)
					
					logString = "Success: " + logString + ", employeeId: " + employeeInstance?.id
					
					recordsInserted++
					
					// Insert this record in EmployeeJobLineRecord for future reference
					ImportEmployeeJobLineRecord successLineRecord = new ImportEmployeeJobLineRecord(hhEmployeeInstance, currentJob, employeeInstance.id, logString, ImportEmployeeStatusType.INSERT_SUCCESS, systemJobUser).save(flush: true)
					
				} else {
					ImportEmployeeJobRecordDTO failedEmployeeRecord = new ImportEmployeeJobRecordDTO(hhEmployeeInstance?.employeeId, employeeInstance?.id, "PayDept: ${hhEmployeeInstance.department} not found")
					failedRecordsList.add(failedEmployeeRecord)
				
					logString = "Failure: PayDept: ${hhEmployeeInstance.department} not found: " + logString + ", employeeId: " + employeeInstance?.id
					
					recordsFailed++
					
					// Insert this record in EmployeeJobLineRecord for future reference
					ImportEmployeeJobLineRecord faliedLineRecord = new ImportEmployeeJobLineRecord(hhEmployeeInstance, currentJob, employeeInstance?.id, logString, ImportEmployeeStatusType.PAY_DEPT_NOT_FOUND, systemJobUser).save(flush: true)
				}
			}
			
			log.info "Rec#: ${recordsProcessed} :: " + logString
		} catch (Exception e) {
			e.printStackTrace()
		
			ImportEmployeeJobRecordDTO failedEmployeeRecord = new ImportEmployeeJobRecordDTO(hhEmployeeInstance?.employeeId, employeeInstance?.id, e.getCause())
			failedRecordsList.add(failedEmployeeRecord)
			
			recordsFailed++
			
			logString = "Failure: " + logString + ", employeeId: " + employeeInstance?.id
			log.error e
			
			// Insert this record in EmployeeJobLineRecord for future reference
			ImportEmployeeJobLineRecord faliedLineRecord = new ImportEmployeeJobLineRecord(hhEmployeeInstance, currentJob, employeeInstance?.id, e.getCause(), ImportEmployeeStatusType.PAY_DEPT_NOT_FOUND, systemJobUser).save(flush: true)
			
			log.info "Rec#: ${recordsProcessed} :: " + logString
			
			throw new RuntimeException(e)
		}
		
		return [recordsProcessed: recordsProcessed, recordsInserted: recordsInserted, recordsFailed: recordsFailed, recordsUpdated: recordsUpdated]
	}
	
	def getImportEmployeeSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList, 
		updatedRecordsList, failedRecordsList) {

		ImportEmployeeJobSummaryDTO summary = new ImportEmployeeJobSummaryDTO()
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
