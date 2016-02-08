package org.solcorp.etech

import java.util.Date;

import grails.converters.JSON
import grails.util.GrailsUtil

import org.solcorp.etech.utils.DateFormatUtils
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

class HHProjectMasterService {
	def sessionFactory
	def jobRegisterService 
	def grailsApplication
	
	def getProjectRecordsForImport(projectId, missingProjectList, processRecordsFromDate) {
		def hhProjectMasterCriteria = HHProjectMaster.createCriteria()
		
		def hhProjectList = hhProjectMasterCriteria.list {
			
			projections {
				groupProperty('projectId')			//0
				groupProperty('businessUnit')		//1
				groupProperty('descr')				//2
				groupProperty('custId')				//3
				groupProperty('hhCustId')			//4
				groupProperty('name1')				//5
				groupProperty('effStatus')			//6
				groupProperty('startDt')			//7
				groupProperty('endDt')				//8
				groupProperty('salesPerson')		//9
				groupProperty('name2')				//10
				groupProperty('descrshort')			//11
				groupProperty('dttmStamp')			//12
			}
			
			// Looking for specific project?
			if(projectId) {
				// Yes
				and {
					eq("projectId", projectId)
					eq("descrshort", Constants.HH_PROJECTMASTERIMPORTSTATUS_LOADED)
				}
			} else {
				// No. 
			
				// Check for missing projects
				if(missingProjectList) {
					// Yes
					and {
						'in'("projectId", missingProjectList)
						eq("descrshort", Constants.HH_PROJECTMASTERIMPORTSTATUS_LOADED)
					}
				} else {
					// No
					//ge("dttmStamp", processRecordsFromDate)
					and {
						ge("endDt", Date.parse('dd-MMM-yy','01-JAN-13'))
						eq("descrshort", Constants.HH_PROJECTMASTERIMPORTSTATUS_LOADED)
					}
				}
			}
			
			order("projectId","asc")
			order("dttmStamp", "asc")
			order("startDt", "asc")
		}
		
		return hhProjectList
	}
	
	def getMissingProjects() {
		def hhMissingProjectsCriteria = HHMissingProject.createCriteria()
		def hhMissingProjectList = hhMissingProjectsCriteria.list {

			projections { property("sourceProjectId") }

			and {
				eq("status", HHMissingProjectStatusType.NOT_FOUND_IN_HHPC)
			}
		}

		return hhMissingProjectList
	}
	
	def importHHProjects(def currentJobId, def projectId, def importOnlyMissingProjects) {
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		def recordsUpdated = 0
		def recordsFailed = 0 
		
		def insertedRecordsList = []
		def updatedRecordsList = []
		def failedRecordsList = []
		def newHHProjectList = []
		def customerNotFound = []
		def customerMap = [:]
		
		def jobRegisterInstance = JobRegister.read(currentJobId)
		def logString
		
		// Check if a specific project to be imported
		if (projectId) {
			// Yes
			// Fetch that single project
			newHHProjectList = getProjectRecordsForImport(projectId, null, null)
			logString = "ImportSingleProject"
		} else {
			// No
		
			// Check if we want to import only missing projects
			if(importOnlyMissingProjects) {
				// Yes
				
				logString = "ImportMissingProjects"
				
				// Find missing project list
				def hhMissingProjectList = getMissingProjects()
				
				log.info "hhMissingProjectList.size(): " + hhMissingProjectList?.size() 
				
				// Slice huge list into sublists of 900 elements each
				def mapHHMissingProjectList = hhMissingProjectList?.collate(900)
				
				log.info "hhMissingProjectList.size(): " + mapHHMissingProjectList?.size()
				
				// Query PSViews for all the sliced batches
				
				log.info "Query for batches"
				def counter = 0;
				mapHHMissingProjectList.each { it ->
					
					log.info "Query for batch Counter: " + counter
					
					newHHProjectList.addAll(getProjectRecordsForImport(null, it, null))
					
					log.info "newHHProjectList size after batch: " + counter++
				}
			} else {
				logString = "ImportProjects"
				//def processRecordsFromDate = DateFormatUtils.getDateFromString("05/01/2015")
				
				/*try {
					processRecordsFromDate = jobRegisterService.getLastSuccessfulRunDate("ImportProjectsJob")
					if(! processRecordsFromDate) {
						processRecordsFromDate = DateFormatUtils.getDateFromString("09/01/2015")
					}
				} catch (Exception e) {
					processRecordsFromDate = DateFormatUtils.getDateFromString("09/01/2015")
				}*/
				
				newHHProjectList = getProjectRecordsForImport(null, null, null)
			}
		}
		
		/*newHHProjectList.eachWithIndex { it, idx ->
			println "Record: " + idx
			println "it.projectId: " + it[0]
			println "it.businessUnit: " + it[1]
			println "it.descr: " + it[2]
			println "it.custId: " + it[3]
			println "it.hhCustId: " + it[4]
			println "it.name1: " + it[5]
			println "it.effStatus: " + it[6]
			println "it.startDt: " + it[7]
			println "it.endDt: " + it[8]
			println "it.salesPerson: " + it[9]
			println "it.name2: " + it[10]
		}*/
		
		recordsToBeProcessed = newHHProjectList?.size()
		
		log.info "Projects to be imported: " + recordsToBeProcessed
		
		User systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		log.info "System Job User found: " + systemJobUser
		
		def projectCodeList = projectId ? [] : Project.findAll().code
		
		def hhProjectMasterDetailList = []
		def projectInstance
		
		def stopJob = false
		for (HHProjectMaster hhProjectInstance : newHHProjectList) {
			if(! grailsApplication.config.shouldJobrun) {
				if(projectInstance?.code != hhProjectInstance.projectId.trim()) {
					stopJob = true
				}
			}
			
			if(!stopJob) {
				try {
					def resultMap = processRecord(hhProjectInstance, projectCodeList, recordsProcessed, systemJobUser, updatedRecordsList, recordsUpdated, insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed, logString, jobRegisterInstance, customerNotFound, customerMap)
					
					recordsProcessed = resultMap.recordsProcessed
					recordsFailed = resultMap.recordsFailed
					recordsInserted = resultMap.recordsInserted
					projectInstance = resultMap.projectInstance
					
					logString = resultMap.logString
				} catch (Exception e) {
					e.printStackTrace()
				}
			} else {
				log.info "Emergency Stop. ImportProjectsJob is terminated gracefully."
				break;
			}
		}
		
		// Return LogString and Status in case of specific Project
		if(projectId) {
			return [projectInstance: projectInstance, logString: logString]		
		} else {
			// Create import job summary and save along with the job
			return getImportProjectSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList,
				updatedRecordsList, failedRecordsList)
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def processRecord(hhProjectInstance, projectCodeList, recordsProcessed, systemJobUser, updatedRecordsList, recordsUpdated, insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed, logString, jobRegisterInstance, customerNotFound, customerMap) {
		
		recordsProcessed ++
		
		def projectInstance
		try {
			
			logString = "HHProjectId: " + hhProjectInstance.projectId
			
			def customer
			if(! customerNotFound.contains(hhProjectInstance.hhCustId)) {
				if(customerMap.containsKey(hhProjectInstance.hhCustId)) {
					customer = customerMap.get(hhProjectInstance.hhCustId)
				} else {
					customer = Customer.findByCode(hhProjectInstance.hhCustId)
				}
			}
			
			// Customer not found
			if(! customer) {
				
				// Add this customer in the list
				// We don't want to query for the same once again
				if(! customerNotFound.contains(hhProjectInstance.hhCustId)) {
					customerNotFound.add(hhProjectInstance.hhCustId)
				}
				
				logString = "Failure: " + logString + " :: No Customer Found :: projectId: " + projectInstance?.id + " :: hhCustId: " + hhProjectInstance.hhCustId
				
				ImportProjectJobRecordDTO failedProjectRecord = new ImportProjectJobRecordDTO(hhProjectInstance.projectId, projectInstance?.id, logString)
				failedRecordsList.add(failedProjectRecord)
				
				recordsFailed++
				
				ImportProjectJobLineRecord failedLineRecord = new ImportProjectJobLineRecord(hhProjectInstance, jobRegisterInstance, projectInstance?.id, logString, ImportProjectStatusType.CUSTOMER_NOT_FOUND, systemJobUser).save(flush: true)
			} 
			
			// Customer found
			if (customer) {
				
				// Add this customer in the map
				// We don't want to query for the same once again
				if(! customerMap.containsKey(hhProjectInstance.hhCustId)) {
					customerMap.put(hhProjectInstance.hhCustId, customer)
				}
				
				// Check if the Project in concern is already available
				//log.info "Checking if the project exists for: " + logString
				if(projectCodeList?.contains(hhProjectInstance.projectId)) {
					// Project is available
					// Update operation is required
					
					// Get existing project instance	
					projectInstance = Project.findByCode(hhProjectInstance.projectId.trim())
					log.info "Project: ${projectInstance.id} exists for: " + logString
					
					if(projectInstance.customer?.id != customer?.id 
						|| projectInstance.status != Project.getProjectStatusFromHHProjectStatus(hhProjectInstance.effStatus)
						|| projectInstance.businessUnit != hhProjectInstance.businessUnit 
						|| projectInstance.startDate != hhProjectInstance.startDt 
						|| projectInstance.endDate != hhProjectInstance.endDt 
						|| projectInstance.name != hhProjectInstance.descr) {
							
						projectInstance.name = hhProjectInstance.descr
						projectInstance.customer = customer
						projectInstance.status = Project.getProjectStatusFromHHProjectStatus(hhProjectInstance.effStatus)
						projectInstance.updatedBy = systemJobUser
						projectInstance.businessUnit = hhProjectInstance.businessUnit
						projectInstance.startDate = hhProjectInstance.startDt
						projectInstance.endDate = hhProjectInstance.endDt
							
						logString = "Update: " + logString
						projectInstance.save(flush: true, failOnError: true)
						
						logString = "Success: " + logString + " :: projectId: " + projectInstance?.id + " :: hhCustId: " + hhProjectInstance.hhCustId + " :: customerId: " + projectInstance?.customer?.id
						
						ImportProjectJobRecordDTO updatedProjectRecord = new ImportProjectJobRecordDTO(hhProjectInstance.projectId, projectInstance?.id, "Update Successful")
						updatedRecordsList.add(updatedProjectRecord)
							
						recordsUpdated++
						
						ImportProjectJobLineRecord successLineRecord = new ImportProjectJobLineRecord(hhProjectInstance, jobRegisterInstance, projectInstance?.id, logString, ImportProjectStatusType.UPDATE_SUCCESS, systemJobUser).save(flush: true)
					} else {
						logString = "Success: " + logString + ", projectId: " + projectInstance?.id + ", hhCustId: " + hhProjectInstance.hhCustId + ", customerId: " + projectInstance?.customer?.id
						logString = "No Update Required: " + logString
						
						ImportProjectJobLineRecord successLineRecord = new ImportProjectJobLineRecord(hhProjectInstance, jobRegisterInstance, projectInstance?.id, logString, ImportProjectStatusType.UPDATE_NOT_REQUIRED_SUCCESS, systemJobUser).save(flush: true)
					}
				} else {
				
					// Project is new
					// insert operation is required
					
					log.info "No Project exists for: " + logString
				
					projectInstance = Project.createProjectFromHHProject(hhProjectInstance, customer, systemJobUser)
				
					logString = "Insert: " + logString
					projectInstance.save(flush: true, failOnError: true)
					
					logString = "Success: " + logString + " :: projectId: " + projectInstance?.id + " :: hhCustId: " + hhProjectInstance.hhCustId + " :: customerId: " + projectInstance?.customer?.id
					
					ImportProjectJobRecordDTO insertedProjectRecord = new ImportProjectJobRecordDTO(hhProjectInstance.projectId, projectInstance.id, "Insert Successful")
					insertedRecordsList.add(insertedProjectRecord)
						
					recordsInserted++
					
					ImportProjectJobLineRecord successLineRecord = new ImportProjectJobLineRecord(hhProjectInstance, jobRegisterInstance, projectInstance?.id, logString, ImportProjectStatusType.INSERT_SUCCESS, systemJobUser).save(flush: true)
					
					// Prepare HHProjectMasterDetails
					// def hhProjectDetailInstance = new HHProjectMasterDetail(hhProjectInstance, systemJobUser).save(flush: true)
					// hhProjectMasterDetailList.add(hhProjectDetailInstance)
						
					// Time to update the missing project record
					def hhMissingProject = HHMissingProject.findBySourceProjectId(hhProjectInstance.projectId)
					if(hhMissingProject) {
						hhMissingProject.project = projectInstance
						hhMissingProject.status = HHMissingProjectStatusType.IMPORTED_FROM_PS_VIEW_TO_HHPC
						hhMissingProject.jobRegister = jobRegisterInstance
						hhMissingProject.save(flush: true)
					}
				}
					
				projectCodeList.add(hhProjectInstance.projectId.trim())
				
				// Time to update the source record
				hhProjectInstance.descrshort = Constants.HH_PROJECTMASTERIMPORTSTATUS_IMPORTED
				hhProjectInstance.save(flush: true)
			}
		
			log.info "Rec#: ${recordsProcessed} :: " + logString
		} catch (Exception e) {
			ImportProjectJobRecordDTO failedProjectRecord = new ImportProjectJobRecordDTO(hhProjectInstance.projectId, projectInstance?.id, e.getCause())
			failedRecordsList.add(failedProjectRecord)
			
			recordsFailed++
			logString = "Failure: " + logString + ", projectId: " + projectInstance?.id + ", hhCustId: " + hhProjectInstance.hhCustId + ", customerId: " + projectInstance?.customer?.id
			
			ImportProjectJobLineRecord failedLineRecord = new ImportProjectJobLineRecord(hhProjectInstance, jobRegisterInstance, projectInstance?.id, e.getCause(), ImportProjectStatusType.OTHER, systemJobUser).save(flush: true)
			
			log.error e
			log.info "Rec#: ${recordsProcessed} :: " + logString
			
			throw new RuntimeException(e)
		}
			
		return [projectInstance: projectInstance, logString: logString, recordsProcessed: recordsProcessed, 
			recordsInserted: recordsInserted, recordsFailed: recordsFailed]
	}
	
	/*def importHHProjectsInLocal() {
		log.info "importHHProjectsInLocal - STARTED"
		
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		def recordsUpdated = 0
		def recordsFailed = 0
		
		def insertedRecordsList = []
		def updatedRecordsList = []
		def failedRecordsList = []
		
		def newHHProjectList = getProjectRecordsForImport()
		recordsToBeProcessed = newHHProjectList?.size()
		
		log.info "Projects to be imported: " + recordsToBeProcessed
		
		User systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		log.info "System Job User found: " + systemJobUser
		
		def projectCodeList = Project.findAll().code
		
		def insertProject = true
		def tempHHProjectMasterList = []
		
		newHHProjectList.each { hhProjectInstance ->
			def logString = "HHProjectId: " + hhProjectInstance[0]
			
			recordsProcessed++
			
			println "hhProjectInstance: " + hhProjectInstance
			
			log.info "Processing Record# ${recordsProcessed}: " + logString
			
			def tempHHProjectMasterInstance = new TempHHProjectMaster(hhProjectInstance).save(flush: true)
			recordsInserted++
			
			println "Records Inserted# ${recordsInserted}"
		}
		
		log.info "importHHProjectsInLocal - Completed"
	}*/
	
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
