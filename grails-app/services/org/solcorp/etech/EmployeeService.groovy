package org.solcorp.etech
import grails.transaction.Transactional

import org.hibernate.criterion.CriteriaSpecification
/**
 * 
 * @author hjjogiya
 *
 */

@Transactional
class EmployeeService {
	
	def getEmployeesToSync() {
		def employeeActivityCodeCriteria = EmployeeActivityCode.createCriteria()
		def employeeActivityList = employeeActivityCodeCriteria.list {
			and {
				ne ("status", 'ACTIVITY_ASSIGNED')
			}
		}
		
		return employeeActivityList
	}
	
	def syncEmployeeAndActivityCodes() {
		log.info "syncEmployeeAndActivityCodes @ EmployeeService - START"
		
		def employeeActivityCodeList = getEmployeesToSync()
		log.info "Employees to be synced" + employeeActivityCodeList?.size()
		
		def employeeInstance
		def activityCodeInstance
		
		def systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		log.info "System Job User Found" + systemJobUser.username
		
		def logString = ""
		employeeActivityCodeList.eachWithIndex { employeeActivityCodeInstance, recordCounter ->
			
			employeeInstance = null
			activityCodeInstance = null
			
			logString = "EmployeeCode: ${employeeActivityCodeInstance.employeeCode} :: ActivityCode: ${employeeActivityCodeInstance.activityCode}"
			
			employeeInstance = Employee.findByCode(employeeActivityCodeInstance.employeeCode)
			if(employeeInstance) {
				// Employee Found
				
				// Check for ActivityCode
				activityCodeInstance = LaborActivityCode.findByCode(employeeActivityCodeInstance.activityCode)
				if(activityCodeInstance) {
					// Activity Code Found
					employeeInstance.laborActivityCode = activityCodeInstance
					employeeInstance.laborActivityGroup = activityCodeInstance.laborActivityGroup
					employeeInstance.lastUpdated = new Date()
					employeeInstance.updatedBy = systemJobUser
					employeeInstance.save(flush: true)
					
					employeeActivityCodeInstance.status = 'ACTIVITY_ASSIGNED'
					employeeActivityCodeInstance.lastUpdated = new Date()
					employeeActivityCodeInstance.save(flush: true)
					
					logString = "Success: Activity: ${activityCodeInstance.id} assigned :: " + logString
				} else {
					// Activity Code Not Found
					employeeActivityCodeInstance.status = 'ACTIVITY_NOT_FOUND'
					employeeActivityCodeInstance.lastUpdated = new Date()
					employeeActivityCodeInstance.updatedBy = systemJobUser
					employeeActivityCodeInstance.save(flush: true)
					
					logString = "Failure: ACTIVITY_NOT_FOUND :: " + logString
				}
			} else {
				// Employee Not Found
				employeeActivityCodeInstance.status = 'EMPLOYEE_NOT_FOUND'
				employeeActivityCodeInstance.lastUpdated = new Date()
				employeeActivityCodeInstance.updatedBy = systemJobUser
				employeeActivityCodeInstance.save(flush: true)
				
				logString = "Failure: EMPLOYEE_NOT_FOUND :: " + logString
			}
			
			log.info "Record: ${recordCounter} :: " + logString
		}
		
		log.info "syncEmployeeAndActivityCodes @ EmployeeService - END"
	}
	
    def serviceMethod() {

    }
	
	/**
	 * 
	 * @return
	 */
	def getEmployeeList(params) {
		log.info "getEmployeeList @ EmployeeService Start"
			
		def employeeCriteria = Employee.createCriteria()
		def employeeCountCriteria = Employee.createCriteria()
		
		def employeeInstanceList = employeeCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			 
			and {				 
				if (params?.code) {
					like("code","%" + params?.code + "%")
				}
				
				if (params?.ssNo) {
					like("ssNo","%" + params?.ssNo + "%")
				}
				
				if (params?.firstName) {
					like("firstName","%" + params?.firstName + "%")
				}
					
				if (params?.lastName) {
					like("lastName","%" + params?.lastName + "%")
				}
				
				if (params?.middleName) {
					like("middleName","%" + params?.middleName + "%")
				}
				
				if (params?.laborActivityGroup) {
					eq("laborActivityGroup.id", Long.valueOf(params?.laborActivityGroup))
				}
				
				if (params?.laborActivityCode) {
					eq("laborActivityCode.id", Long.valueOf(params?.laborActivityCode))
				}			
								
				if (params?.status) {
					eq("status", StatusType.valueOf(params?.status))
				}
				
				if (params?.employeeType) {
					eq("employeeType",EmployeeType.valueOf(params?.employeeType))
				}
				
				if (params?.payDept) {
					eq("payDept.id",Long.valueOf(params?.payDept))
				}
				
				if (params?.supervisor) {
					eq("supervisor.id",Long.valueOf(params?.supervisor))
				}
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def employeeInstanceCount = employeeInstanceList.totalCount
		log.info "Employee List is "+employeeInstanceList.size()
		log.info "getEmployeeList @ EmployeeService End"
		
		return [employeeInstanceList: employeeInstanceList, employeeInstanceCount: employeeInstanceCount, params: params]
	}
	
	def findByCode(code) {
		def employeeCriteria = Employee.createCriteria()
		def employeeInstance = employeeCriteria.get {
			createAlias('laborActivityGroup', 'laborActivityGroup', CriteriaSpecification.LEFT_JOIN)
			createAlias('laborActivityCode', 'laborActivityCode', CriteriaSpecification.LEFT_JOIN)
			createAlias('payDept', 'payDept', CriteriaSpecification.LEFT_JOIN)
			createAlias('payDept.laborActivityGroup', 'payDept.laborActivityGroup', CriteriaSpecification.LEFT_JOIN)
			
			and {
				eq("code", code)
			}
		}
		
		return employeeInstance
	}
	
	def getEmployeeByCode(code, numericComparision) {
		def employeeCriteria = Employee.createCriteria()
		def employeeInstanceList = employeeCriteria.list() {
			 
			and {
				if(numericComparision) {
					sqlRestriction "ISNUMERIC(code) = 1"
					sqlRestriction "SUBSTRING(code, PATINDEX('%[^0 ]%', code + 'a'), LEN(code)) = " + code as Integer
				} else {
					eq("code", code)
				}
			}
		}
		return employeeInstanceList
	}
	
	/**
	 * 
	 * @param employeeInstance
	 * @return
	 */
	def saveEmployee(employeeInstance) {		
		if (!employeeInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
	}
	
	/**
	 * 
	 * @param employeeInstance
	 * @return
	 */
	def updateEmployee(employeeInstance) {		 
		if (!employeeInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
	}

	/**
	 * 
	 * @return
	 */
	def getSupervisorList() {
		
		def userCriteria = User.createCriteria()		
		def userInstanceList = userCriteria.list() {
			and {			
				createAlias("roles", "role")
				isNotNull("employee")
				eq("tsApprove", "Y")
				inList("role.name", RoleType.supervisorRoleList()*.name())	 
			}
		}
		def empInstanceList = userInstanceList*.employee			
		return empInstanceList		
	}
	
	def getLaborActGrpList(params) {			
		def laborActivityCodeCriteria = LaborActivityGroup.createCriteria()
		def laborActGrpList = laborActivityCodeCriteria.list(max : params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and{
				if (params?.code) {
					ilike("code", "%" + params?.code +"%")
				}
				if (params?.description) {
					ilike("description", "%" + params?.description + "%")
				}
			}
			order("code","asc")
		}
		def laborActGrpListCount = laborActGrpList.totalCount		 
				
	return [laborActGrpList: laborActGrpList, laborActGrpListCount: laborActGrpListCount, params: params]
	}	
		
	def getLaborActCodeList(laborGrpId) {
		def laborActGrp = LaborActivityGroup.read(laborGrpId)
		def laborActivityCodeCriteria = LaborActivityCode.createCriteria()
		def laborActCodeList = laborActivityCodeCriteria.list() {
			and{				
				eq("laborActivityGroup",laborActGrp)				
			}
		}			
		
		return laborActCodeList
	}
	
	/*def getAccExecutiveList() {
		def employeeCriteria = Employee.createCriteria()
		def accExecutiveList = employeeCriteria.list() {
			and {
				eq("isAccExecutive", true)
			}
		}		
		return accExecutiveList
	}*/
	
	def getEmployeeMstList(params) {
		
		def employeeList = Employee.findAll().code		
		
		def empCodeCriteria = HHEmployeeMaster.createCriteria()
		def employeeMasterList = empCodeCriteria.list(max : params?.max, offset : params?.offset?:0) {
			and{
		
				if (params?.employeeId) {
					ilike("employeeId", "%" + params?.employeeId +"%")
				}
				
				if (params?.firstName) {
					ilike("firstName", "%" + params?.firstName + "%")
				}
				
				if (params?.lastName) {
					ilike("lastName", "%" + params?.lastName + "%")
				}
				/*if(params?.isImportedEmpShow == 'false') {
					 if(employeeList.size() > 0) {
						not { 'in'("employeeId", employeeList) }
					} 
				}*/
			}
			order("employeeId","asc") 
		}
		
		def employeeMasterCount = employeeMasterList.totalCount   
		 
		return [employeeMasterList: employeeMasterList, empMstListCount: employeeMasterCount, employeeList: employeeList, params: params]
	}
	
	def getDepartmentList(params) {
		
		def deptCodeCriteria = Department.createCriteria()
		
		def departmentList = deptCodeCriteria.list(max : params?.max, offset : params?.offset?:0) {
			and{
				if (params?.dialogDepartmentCode) {
					ilike("code", "%" + params?.dialogDepartmentCode +"%")
				}
				if (params?.dialogDescription) {
					ilike("description", "%" + params?.dialogDescription +"%")
				}
			}
			order("code","asc")
		}
		
		def departmentListCount = departmentList.totalCount
		
		return [departmentList: departmentList, departmentListCount: departmentListCount, params: params]
	}
	
	def getAssignedCustomersList(employeeInstanceID) {
		
		def customerCriteria = Customer.createCriteria()
		def customerInstanceList = customerCriteria.list() {
			or {
				and {
					isNotNull("acctExecutive")
					isNotNull("salesExecutive")
					eq("acctExecutive.id",employeeInstanceID)
					eq("salesExecutive.id",employeeInstanceID)
				}
				and {
					isNotNull("acctExecutive")
					isNotNull("salesExecutive")
					ne("acctExecutive.id",employeeInstanceID)
					eq("salesExecutive.id",employeeInstanceID)
				}
				and {
					isNotNull("acctExecutive")
					isNotNull("salesExecutive")
					eq("acctExecutive.id",employeeInstanceID)
					ne("salesExecutive.id",employeeInstanceID)
				}
				and {
					isNotNull("acctExecutive")
					isNull("salesExecutive")
					eq("acctExecutive.id",employeeInstanceID)
				}
			}
			
		}
		return customerInstanceList
	}
	
	def getAssignedProjectsList(employeeInstanceID) {
		
		def projectCriteria = Project.createCriteria()
		def projectInstanceList = projectCriteria.list() {
			or {
				and {
					eq("accExecutive.id",employeeInstanceID)
				}
				and {
					projectEmployees{
						eq("employee.id", employeeInstanceID)
					}
					
				}
			}
			
		}
		return projectInstanceList
	}
	
}
