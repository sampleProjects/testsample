package org.solcorp.etech

import grails.converters.JSON
import grails.util.GrailsUtil

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

class DepartmentService {
	def grailsApplication
	def importDepartments(def currentJobId) {
		
		def recordsToBeProcessed = 0
		def recordsProcessed = 0
		def recordsInserted = 0
		def recordsFailed = 0 
		
		def insertedRecordsList = []
		def failedRecordsList = []
		
		def newHHEmployeeList = HHEmployeeMaster.findAll()
		recordsToBeProcessed = newHHEmployeeList.size()
		
		log.info "Departments to be processed: " + recordsToBeProcessed
	
		User systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		log.info "System Job User found: " + systemJobUser
		
		newHHEmployeeList.each { hhEmployeeInstance ->
			
			def resultMap = processRecord(hhEmployeeInstance, recordsProcessed, systemJobUser, insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed)
			
			recordsProcessed = resultMap.recordsProcessed
			recordsFailed = resultMap.recordsFailed
			recordsInserted = resultMap.recordsInserted
		}
		
		// Create import job summary and save along with the job
		return getImportDepartmentSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsFailed, insertedRecordsList,
			failedRecordsList)
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def processRecord(hhEmployeeInstance, recordsProcessed, systemJobUser, insertedRecordsList, recordsInserted, failedRecordsList, recordsFailed) {
		recordsProcessed++
		
		def logString = "HHEmployeeId: " + hhEmployeeInstance.employeeId + " HHDepartmentCode: " + hhEmployeeInstance.department
		def departmentInstance = null
		try {
			// Check if the department in concern is already available
			log.info "Checking if the department exists for: " + logString
			
			// String comparison
			departmentInstance = Department.findByCode(hhEmployeeInstance.department)
			if(! departmentInstance) {
				// Dept not found
				// Time to use numeric comparison
				
				def deptList = Department.getDepartmentByCode(hhEmployeeInstance.department, hhEmployeeInstance.department?.isNumber())
				if(deptList.size() > 0) {
					departmentInstance = deptList.get(0)
				}
			}
			
			if(departmentInstance == null) {
				// Department is new
				// insert operation is required
				
				log.info "No Department exists for: " + logString
			
				departmentInstance = Department.createDepartmentFromHHEmployee(hhEmployeeInstance, systemJobUser)
				 
				logString = "Insert: " + logString
				departmentInstance.save(flush: true, failOnError: true)
				
				ImportDepartmentJobRecordDTO insertedRecord = new ImportDepartmentJobRecordDTO(departmentInstance.id, departmentInstance.code, "Insert Successful")
				insertedRecordsList.add(insertedRecord)
				
				recordsInserted++
				
				logString = "Success: " + logString + " as ${departmentInstance.code}, departmentId: " + departmentInstance?.id
			}
		} catch (Exception e) {
			ImportDepartmentJobRecordDTO failedRecord = new ImportDepartmentJobRecordDTO(departmentInstance?.id, departmentInstance?.code, GrailsUtil.sanitize(e))
			failedRecordsList.add(failedRecord)
			
			recordsFailed++
			
			logString = "Failure: " + logString + ", departmentId: " + departmentInstance?.id
			log.error e
		}
		
		log.info "Rec#: ${recordsProcessed} :: " + logString
		
		return [recordsProcessed: recordsProcessed, recordsInserted: recordsInserted, recordsFailed: recordsFailed]
	}
	
	def getImportDepartmentSummaryJSON(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsFailed, insertedRecordsList, 
		failedRecordsList) {

		ImportDepartmentJobSummaryDTO summary = new ImportDepartmentJobSummaryDTO()
		summary.recordsToBeProcessed = recordsToBeProcessed
		summary.recordsProcessed = recordsProcessed
		summary.recordsInserted = recordsInserted
		summary.recordsFailed = recordsFailed
		
		summary.insertedRecordsList = insertedRecordsList
		summary.failedRecordsList = failedRecordsList
		
		return summary as JSON
	}
	
	def getDepartmentList(params) {
		
		log.info "getDepartmentList @ DepartmentService Start"
		
		def departmentCriteria = Department.createCriteria()
		def departmentCountCriteria = Department.createCriteria()
		
		def departmentInstanceList = departmentCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			
			and {
				
				if (params?.id) {
					eq("id", params.long('id'))
				}
				
				if (params?.code) {
					like("code", "%" + params?.code + "%")					
				}
				
				if (params?.laborActivityGrouptxt) {					
					createAlias("laborActivityGroup","lbrActGroup")					
					ilike("lbrActGroup.code", "%"+ params?.laborActivityGrouptxt +"%")
				}
				if (params?.description) {
					ilike("description", "%" + params?.description + "%")
				}				
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}	
		
		def departmentInstanceCount = departmentInstanceList.totalCount
		log.info "Department List is "+ departmentInstanceList.size()
		log.info "getDepartmentList @ DepartmentService End"
		
		return [departmentInstanceList: departmentInstanceList, departmentInstanceCount: departmentInstanceCount, params: params]
		
	}
	
	def getLaborActGroupList(params) {
		
		def laborActGroupCriteria = LaborActivityGroup.createCriteria()
		
		def laborActGroupInstanceList = laborActGroupCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			

			and {
				if (params?.dialogLaborActGrpCode) {
					ilike("code", "%" + params?.dialogLaborActGrpCode +"%")
				}
				
				if (params?.dialogDescription) {
					ilike("description", "%" + params?.dialogDescription +"%")
				}
			}
			order("code","asc")
		}
		
		def laborActGroupListCount = laborActGroupInstanceList.totalCount
		
		return [laborActGroupInstanceList: laborActGroupInstanceList, laborActGroupListCount: laborActGroupListCount, params: params]
		
	}
	
	def getDepartmentDialogList(params){
		
		def deptCodeCriteria = Department.createCriteria()
		
		def departmentList = deptCodeCriteria.list(max : params?.max, offset : params?.offset?:0) {
			
			and{
				if (params?.dialogDepartmentCode) {
					ilike("code", "%" + params?.dialogDepartmentCode +"%")
				}
				if (params?.dialogLaborActivityGroup) {
					createAlias("laborActivityGroup","lbrActGroup")

					ilike("lbrActGroup.code", "%" + params?.dialogLaborActivityGroup + "%")
				}
				if (params?.dialogDescription) {
					ilike("description", "%" + params?.dialogDescription + "%")
				}
			}
			order("id","asc")
		}
		
		def departmentListCount = departmentList.totalCount
		
		return [departmentList: departmentList, departmentListCount: departmentListCount, params: params]
	}
	 
	def setPayDepartment() {
		grailsApplication.config.departmentMap = [:]
		def departmentCriteria = Department.createCriteria()
		def departmentList = departmentCriteria.list() {
			isNotNull('laborActivityGroup')
		}
		def departmentMap = [:]
		departmentList.each {
			departmentMap.put(it?.laborActivityGroup?.code, it?.code)
		}
		grailsApplication.config.departmentMap = departmentMap
	}
}
