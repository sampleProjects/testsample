package org.solcorp.etech
import grails.transaction.Transactional

import org.hibernate.Criteria
import org.hibernate.criterion.CriteriaSpecification
import org.solcorp.etech.reports.projectByExpense.ExpenseDetailDTO
import org.solcorp.etech.reports.projectByExpense.ProjectExpenseDetailDTO
import org.solcorp.etech.utils.DateFormatUtils
import org.solcorp.etech.utils.FileUtils
import org.solcorp.etech.utils.MiscUtils
@Transactional
class ReportService {	
	
	def userService
	/**
	 * This method used to get Project List by search params
	 * @param params
	 * @return
	 */
	def getProjectList(params) {
		
		log.info "getProjectList @ ReportService Start"
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectCriteria = Project.createCriteria()		
		def projectInstanceList = projectCriteria.list(max: params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			
			and {
				
				if (params?.dialogProjectCode) {
					ilike("code", "%" + params?.dialogProjectCode + "%")
				}
				
				if (params?.dialogProjectName) {
					ilike("name", "%" + params?.dialogProjectName + "%")
				}
				// IF Account Director Logged in then Account Director related project display
				if(loggedInUserInstance) {
					or {
						eq("accExecutive", loggedInUserInstance?.employee)
						
						createAlias('projectEmployees', 'pm')
						eq('pm.employee', loggedInUserInstance?.employee)
					}
				}				
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def projectInstanceCount = projectInstanceList.totalCount
		
		log.info "getProjectList @ ReportService End"
		
		return [projectInstanceList: projectInstanceList, projectInstanceCount: projectInstanceCount, params: params]
	}
	
	/**
	 * This method used to get All Customer List by search params
	 * @param params
	 * @return
	 */
	def getCustomerList(params) {
		
		log.info "getCustomerList @ ReportService Start"
		def loggedInADInstance = userService.getLoggedInAccountDirector()
		def customerCriteria = Customer.createCriteria()
		def customerInstanceList = customerCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
		and {
	
			if (params?.dialogCustomerCode) {
				ilike("code", "%" + params?.dialogCustomerCode +"%")
			}
			
			if (params?.dialogCustomerName) {
				ilike("name", "%" + params?.dialogCustomerName + "%")
			}	
			if(loggedInADInstance) {
				eq("acctExecutive", loggedInADInstance?.employee)
			}
		}
		order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def customerInstanceCount = customerInstanceList.totalCount
		 
		return [customerInstanceList: customerInstanceList, customerInstanceCount: customerInstanceCount, params: params]
	}	
	
	/**
	 * This method used to get All COE List by search params 
	 * @param params
	 * @return
	 */
	def getCOEList(params) {
		
		log.info "getCOEList @ ReportService Start"
			
		def coeCriteria = COE.createCriteria()
		def coeInstanceList = coeCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
		and {
	
			if (params?.dialogCOECode) {
				ilike("code", "%" + params?.dialogCOECode +"%")
			}
			
			if (params?.dialogCOEDesc) {
				ilike("description", "%" + params?.dialogCOEDesc + "%")
			}
		}
		order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def coeInstanceCount = coeInstanceList.totalCount
		log.info "getCOEList @ ReportService End"
		return [coeInstanceList: coeInstanceList, coeInstanceCount: coeInstanceCount, params: params]
	}
	
	/**
	 * This method used to get All Labor Activity code List by search params
	 * @param params
	 * @return
	 */
	def getLaborActivityCodeList(params) {
		
		log.info "getLaborActivityCodeList @ ReportService Start"
			
		def laborActCodeCriteria = LaborActivityCode.createCriteria()
		def laborActCodeInstanceList = laborActCodeCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
		and {
	
			if (params?.laborActivityCode) {
				ilike("code", "%" + params?.laborActivityCode +"%")
			}
			
			if (params?.dialoglaborActCodeDesc) {
				ilike("description", "%" + params?.dialoglaborActCodeDesc + "%")
			}
		}
		order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def laborActCodeInstanceCount = laborActCodeInstanceList.totalCount
		log.info "getLaborActivityCodeList @ ReportService End"
		return [laborActCodeInstanceList: laborActCodeInstanceList, laborActCodeInstanceCount: laborActCodeInstanceCount, params: params]
	}
	
	def getLaborActivityGroupList(params) {
		
		log.info "getLaborActivityGroupList @ ReportService Start"
			
		def laborActGroupCriteria = LaborActivityGroup.createCriteria()
		def laborActGroupInstanceList = laborActGroupCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
		and {
	
			if (params?.laborActivityGrpCode) {
				ilike("code", "%" + params?.laborActivityGrpCode +"%")
			}
			
			if (params?.dialoglaborActGrpDesc) {
				ilike("description", "%" + params?.dialoglaborActGrpDesc + "%")
			}
		}
		order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def laborActGroupInstanceCount = laborActGroupInstanceList.totalCount
		log.info "getLaborActivityGroupList @ ReportService End"
		return [laborActGroupInstanceList: laborActGroupInstanceList, laborActGroupInstanceCount: laborActGroupInstanceCount, params: params]
	}
	def getPayDepartmentList(params) {
		
		def deptCodeCriteria = Department.createCriteria()
		
		def departmentList = deptCodeCriteria.list(max : params?.max, offset : params?.offset?:0) {
			and{
				if (params?.dialogDepartmentCode) {
					ilike("code", "%" + params?.dialogDepartmentCode +"%")
				}
			}
			order("code","asc")
		}
		
		def departmentListCount = departmentList.totalCount
		
		return [departmentList: departmentList, departmentListCount: departmentListCount, params: params]
	}
	/**
	 * This method used to get All Account Executive List by search params
	 * @param params
	 * @return
	 */	
	def getAcctExecutiveList(params) {
		log.info "getAcctExecutiveList @ ReportService Start"
		def loggedInADInstance = userService.getLoggedInAccountDirector()
		def userCriteria = User.createCriteria()
		def userInstanceList = userCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			createAlias('roles', 'role')
			createAlias('employee', 'emp')
			and {						
						
				if (params?.code) {
					ilike("emp.code", "%" + params?.code +"%")
				}
						
				if (params?.name) {
					ilike("emp.firstName", "%" + params?.name + "%")
				}
						
				if (params?.projectManagerChecked?.equals('true')) {
					'in' ('role.name', [RoleType.ROLE_PROJECT_MANAGER.name(), RoleType.ROLE_ACCOUNT_DIRECTOR.name()])
				} else {
					eq("role.name", RoleType.ROLE_ACCOUNT_DIRECTOR.name())
				}
				
				if(loggedInADInstance) {
					eq("id", loggedInADInstance?.id)
				}
			}					
			order("${params.sort ?: 'emp.code'}","${params.order ?: 'asc'}")
		}
			
		def acctExecutiveInstanceList = userInstanceList*.employee
		def acctExecutiveInstanceCount = userInstanceList.totalCount
			
		log.info "getAcctExecutiveList @ ReportService End"
			
		return [acctExecutiveInstanceList: acctExecutiveInstanceList, acctExecutiveInstanceCount: acctExecutiveInstanceCount, params: params]	
	}
	
	/**
	 * This method used to get All Employee List by search params
	 * @param params
	 * @return
	 */
	def getEmployeeList(params) {
		log.info "getEmployeeList @ ReportService Start"
		
		def employeeCriteria = Employee.createCriteria()
		def employeeInstanceList = employeeCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and {
				createAlias("payDept", "payDept")
				if (params?.code) {
						ilike("code", "%" + params?.code +"%")
					}
					
					if (params?.lastName) {
						ilike("lastName", "%" + params?.lastName + "%")
					}
					
					if (params?.firstName) {
						ilike("firstName", "%" + params?.firstName + "%")
					}
					
					if (params?.payDept) {
						ilike("payDept.code", "%" + params?.payDept + "%")
					}	 				
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def employeeInstanceCount = employeeInstanceList.totalCount
		
		log.info "getEmployeeList @ ReportService End"
		
		return [employeeInstanceList: employeeInstanceList, employeeInstanceCount: employeeInstanceCount, params: params]
	}
	
	/**
	 * This method used to get All Project Manager List by search params
	 * @param params
	 * @return
	 */
	def getProjectManagerList(params) {						
			
		def userCriteria = User.createCriteria()
		def	managerInstanceList = userCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			
			createAlias("employee", "employee")
			createAlias("employee.payDept", "payDept")
			createAlias('roles', 'role')
			and {	
					isNotNull("employee")
					
					if (params?.code) {
						ilike("employee.code", "%" + params?.code +"%")
					}
					
					if (params?.lastName) {
						ilike("employee.lastName", "%" + params?.lastName + "%")
					}
					
					if (params?.firstName) {
						ilike("employee.firstName", "%" + params?.firstName + "%")
					}
					
					if (params?.payDept) {
						ilike("payDept.code", "%" + params?.payDept + "%")
					}					
					
					eq("role.name", RoleType.ROLE_PROJECT_MANAGER.name())
										
				}
				
				order("employee.code","asc")
			}	
			
		 def managerInstanceListCount = managerInstanceList.totalCount		
		log.info "getProjectManagerList @ ReportService End"
		
		return [managerInstanceList: managerInstanceList, managerInstanceListCount: managerInstanceListCount, params: params]
	}
	
	/**
	 * This method used to get All Supervisor List by search params
	 * @param params
	 * @return
	 */
	def getAllSupervisorList(params) {
		
		def userCriteria = User.createCriteria()
		def supervisorInstanceList = userCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			
			createAlias("employee", "employee")
			createAlias("employee.payDept", "payDept")
			createAlias("roles", "role")
			and {
								
				isNotNull("employee")
				
				if (params?.code) {
					ilike("employee.code", "%" + params?.code +"%")
				}
				
				if (params?.lastName) {
					ilike("employee.lastName", "%" + params?.lastName + "%")
				}
				
				if (params?.firstName) {
					ilike("employee.firstName", "%" + params?.firstName + "%")
				}
				
				if (params?.payDept) {
					ilike("payDept.code", "%" + params?.payDept + "%")
				}
				
				eq("tsApprove", "Y")
				
				inList("role.name", RoleType.supervisorRoleList()*.name())				
			}
		}
		def supervisorInstanceListCount = supervisorInstanceList.totalCount
		log.info "getProjectManagerList @ ReportService End"		
		return [supervisorInstanceList: supervisorInstanceList, supervisorInstanceListCount: supervisorInstanceListCount, params: params]
		 
	}
	
	
	def getExpenseList(params) {
		
		def expenseCriteria = Expense.createCriteria();
		
		def expenseInstanceList = expenseCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			
			and {
								
				if (params?.dialogExpenseCode) {
					ilike("code", "%" + params?.dialogExpenseCode +"%")
				}
				
				if (params?.dialogDescription) {
					ilike("description", "%" + params?.dialogDescription + "%")
				}			
			}
			order("code","asc")
		}
		def expenseInstanceCount = expenseInstanceList.totalCount
		log.info "getExpenseList @ ReportService End"		
		return [expenseInstanceList: expenseInstanceList, expenseInstanceCount: expenseInstanceCount, params: params]
	}
	
	
	 
	/**
	 * This method used to search Project By report search parameters
	 * @param params
	 * @return
	 */
	def getAllProjectListByReportSearchParams(params) {
				
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectCriteria = Project.createCriteria()	
		def projectInstanceList = projectCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {
				if (params?.project) {
					ilike("code", "%" +  replaceString(params?.project) + "%")
				}
				if (params?.customer) {
					createAlias("customer", "cust")
					ilike("cust.code", "%" +  replaceString(params?.customer) + "%")
				}
				// IF Account Director Logged in then Account Director related project display
				if(loggedInUserInstance) {
					or {
						eq("accExecutive", loggedInUserInstance?.employee)
						if (params?.projectManager) {
							createAlias('projectEmployees', 'pm')
							eq('pm.employee', loggedInUserInstance?.employee)
						}
					}
				} else {
					if (params?.projectManager) {
						createAlias('projectEmployees', 'pm', CriteriaSpecification.LEFT_JOIN)
					}
				}
				if (params?.acctExecutive) {
						createAlias("accExecutive", "accExecutive")
						ilike("accExecutive.code", "%" + params?.acctExecutive +"%")					
				}
				if (params?.projectManager) {
					createAlias("pm.employee", "pmEmp")
					ilike("pmEmp.code", "%" + params?.projectManager +"%")	
				} 
				if (params?.projectCategory) {
					createAlias("projectCategory", "projectCategory")
					ilike("projectCategory.category", "%" + params?.projectCategory +"%")
				}
				if (params?.projectType ) {
					eq("projectType", ProjectType.valueOf(params?.projectType))
				}
				if (params?.billCodeType) {
					eq("billCodeType", BillCodeType.valueOf(params?.billCodeType))
				}					
				if (params?.status ) {
					eq("status", ProjectStatusType.valueOf(params?.status))
				}
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
			resultTransformer Criteria.DISTINCT_ROOT_ENTITY
		}	
		
		def projectCriteriaCount = Project.createCriteria()
		def projectCriteriaCountList = projectCriteriaCount.list() {
			and {
				projections {
					distinct("id")
				}
				if (params?.project) {
					ilike("code", "%" +  replaceString(params?.project) + "%")
				}
				if (params?.customer) {
					createAlias("customer", "cust")
					ilike("cust.code", "%" +  replaceString(params?.customer) + "%")
				}
				// IF Account Director Logged in then Account Director related project display
				if(loggedInUserInstance) {
					or {
						eq("accExecutive", loggedInUserInstance?.employee)
						if (params?.projectManager) {
							createAlias('projectEmployees', 'pm')
							eq('pm.employee', loggedInUserInstance?.employee)
						}
					}
				} else {
					if (params?.projectManager) {
						createAlias('projectEmployees', 'pm', CriteriaSpecification.LEFT_JOIN)
					}
				}
				if (params?.acctExecutive) {
						createAlias("accExecutive", "accExecutive")
						ilike("accExecutive.code", "%" + params?.acctExecutive +"%")
				}
				if (params?.projectManager) {
					createAlias("pm.employee", "pmEmp")
					ilike("pmEmp.code", "%" + params?.projectManager +"%")
				}
				if (params?.projectCategory) {
					
					createAlias("projectCategory", "projectCategory")
					ilike("projectCategory.category", "%" + params?.projectCategory +"%")
				}
				if (params?.projectType ) {
					eq("projectType", ProjectType.valueOf(params?.projectType))
				}
				if (params?.billCodeType) {
					eq("billCodeType", BillCodeType.valueOf(params?.billCodeType))
				}
				if (params?.status ) {
					eq("status", ProjectStatusType.valueOf(params?.status))
				}
			}	   
		}
		def projectInstanceCount = projectCriteriaCountList.size()		
		return [projectInstanceList: projectInstanceList, projectInstanceCount: projectInstanceCount, params: params]
	}
	
	/**
	 * This method used to get Project Performance View Details
	 * @param id : project Id
	 * @return projectPerformanceViewDTO
	 */
	def getProjectPerformanceViewDetails(params) {
		
		def projectInstance = Project.read(params?.id)

		//Set All Project Report related details in ProjectPerformanceViewDTO
		
		def projectPerformanceViewDTO = new ProjectPerformanceViewDTO()	
		
		projectPerformanceViewDTO.projectId   = projectInstance?.id		
		projectPerformanceViewDTO.projectCode =	projectInstance?.code	
		projectPerformanceViewDTO.projectName = projectInstance?.name		
		projectPerformanceViewDTO.customer    = projectInstance?.customer		
		projectPerformanceViewDTO.accExecutive = projectInstance?.accExecutive		
				
		projectPerformanceViewDTO.status = projectInstance?.status?.getValue()
		
		// Billing Calculation  TODO
		/*projectPerformanceViewDTO.planBilling =  BigDecimal.ZERO
		projectPerformanceViewDTO.actualBilling =  BigDecimal.ZERO
		
		projectPerformanceViewDTO.varianceBilling = projectPerformanceViewDTO?.actualBilling - projectPerformanceViewDTO?.planBilling*/ 
				
		// Expense Calculation
		projectInstance?.getProductSummary()
		
		projectPerformanceViewDTO.planExpenses = projectInstance?.plannedExpenseTotal		
		projectPerformanceViewDTO.actualExpenses = projectInstance?.actualExpenseTotal 
		
		projectPerformanceViewDTO.varianceExpenses = projectPerformanceViewDTO?.actualExpenses - projectPerformanceViewDTO?.planExpenses
		
		// Labor Calculation
		projectPerformanceViewDTO.planLabor = projectInstance?.plannedLaborTotal
		if(params?.costOption && params?.costOption?.equalsIgnoreCase(Constants.REPORT_COST_OPTION_ACTUAL) ) {
			// Display all product service Actual total cost(actualCost)
			projectPerformanceViewDTO.actualLabor = MiscUtils.removePrecision(projectInstance.getAcutalLaborActCostTotal())
		} else {
			// Display all product service Std total cost  
			projectPerformanceViewDTO.actualLabor = MiscUtils.removePrecision(projectInstance.actualLaborTotal)
		}
		
		projectPerformanceViewDTO.varianceLabor = projectPerformanceViewDTO?.actualLabor - projectPerformanceViewDTO?.planLabor
		
		// Overhead Calculation TODO
		projectPerformanceViewDTO.planOverhead = projectInstance.plannedStdOHCostTotal
		projectPerformanceViewDTO.actualOverhead = MiscUtils.removePrecision(projectInstance.actualStdOHCostTotal)
		
		projectPerformanceViewDTO.varianceOverhead = projectPerformanceViewDTO?.actualOverhead - projectPerformanceViewDTO?.planOverhead
		
		//Plan Total Cost Calculation
		projectPerformanceViewDTO.planTotalCost = projectPerformanceViewDTO.planExpenses.add(projectPerformanceViewDTO.planLabor).add(projectPerformanceViewDTO.planOverhead)
		
		//Actual Total Cost Calculation
		projectPerformanceViewDTO.actualTotalCost = projectPerformanceViewDTO.actualExpenses.add(projectPerformanceViewDTO.actualLabor).add(projectPerformanceViewDTO.actualOverhead)
		projectPerformanceViewDTO.varianceTotalCost = projectPerformanceViewDTO?.actualTotalCost - projectPerformanceViewDTO?.planTotalCost
		
		
		//Calculate Revenue
		projectPerformanceViewDTO.plannedRevenueTotal = projectInstance?.plannedRevenueTotal
		projectPerformanceViewDTO.actualRevenueTotal= projectInstance?.actualRevenueTotal
		projectPerformanceViewDTO.varianceRevenueTotal = projectInstance?.varianceRevenueTotal
		
		projectPerformanceViewDTO.planProfit = MiscUtils.removePrecision(projectInstance?.plannedProfit)		
		projectPerformanceViewDTO.actualProfit = MiscUtils.removePrecision(projectInstance?.actualProfit)		
		projectPerformanceViewDTO.varianceProfit = MiscUtils.removePrecision(projectInstance?.varianceProfit)
		
		projectPerformanceViewDTO.planProfitPercentage = MiscUtils.removePrecision(projectInstance?.plannedProfitPercentage)		
		projectPerformanceViewDTO.actualProfitPercentage = MiscUtils.removePrecision(projectInstance?.actualProfitPercentage)	
		projectPerformanceViewDTO.varianceProfitPercentage = MiscUtils.removePrecision(projectInstance?.varianceProfitPercentage)
		
	 
		
		
		//Profit TODO
		//	projectPerformanceViewDTO.planProfit = Billing - Total Cost
		//	projectPerformanceViewDTO.actualProfit = Billing - Total Cost
				
		//Get all Planned Labor Hour and Total cost 		
		def plannedLaborDetailMap = getPlannedLaborDetailMap(projectInstance)		
				
		// Set all Actual Labor Hour and Total cost in LaborSummaryDTO		
		def laborSummaryDTOList  = []
		def laborActivityCodeMap = [:]
		
		BigDecimal laborPlanTotal = 0.00
		BigDecimal laborActualTotal = 0.00
		
		BigDecimal laborPlanHoursTotal = 0.00
		BigDecimal laborActualHoursTotal = 0.00
		
		//Get Project Actual Labor Detail by Project 
		def projectActualLaborDtlCriteria = ProjectActualLaborDetail.createCriteria()		
		def performActualLaborList = projectActualLaborDtlCriteria.list() {			
			and {								
				createAlias("laborActivityCode", "activityCode")												
				eq("project", projectInstance)						
			}
			order("activityCode.code", "asc")
		}
		
		// Group By labor Activity Code
		def actualLaborActivityCodeGroupList = performActualLaborList.groupBy { activityLabor -> activityLabor?.laborActivityCode }
			
		actualLaborActivityCodeGroupList.each { activityCodeInstacneKey, actualLaborCodeList ->
			
			BigDecimal actualTotal = 0.00
			BigDecimal actualHoursTotal = 0.00
			
			def activityCode = activityCodeInstacneKey?.code
			def activityGroup = activityCodeInstacneKey?.laborActivityGroup
						
			actualLaborCodeList.each { laborInstance ->
				
				actualHoursTotal = actualHoursTotal.add(laborInstance?.hours ?:0.00)
				
				if(params?.costOption && params?.costOption?.equalsIgnoreCase(Constants.REPORT_COST_OPTION_ACTUAL) ) {
					actualTotal = actualTotal.add(laborInstance?.actualCost)
				} else {					
					actualTotal = actualTotal.add(laborInstance?.standardTotalCost)
				}
			}
			
			def activityMap = plannedLaborDetailMap.get(activityCode)			
			if(activityMap) {			
				plannedLaborDetailMap.remove(activityCode)				
			}
			
			BigDecimal plannedHours = activityMap?.get("hours") ?: 0.00
			BigDecimal plannedStdCost = activityMap?.get("stdCost") ?: 0.00
			
			laborPlanHoursTotal   = laborPlanHoursTotal.add(plannedHours)
			laborActualHoursTotal = laborActualHoursTotal.add(actualHoursTotal)
			
			laborPlanTotal   = laborPlanTotal.add(plannedStdCost)
			laborActualTotal = laborActualTotal.add(actualTotal)
			
			laborSummaryDTOList.add(new LaborSummaryDTO(activityGroup ,activityCode, null, plannedHours, actualHoursTotal, plannedStdCost, actualTotal))				
			plannedLaborDetailMap.remove(activityCode)											
		}
		
		// Set all Planned Labor Hour and Total cost in LaborSummaryDTO  
		plannedLaborDetailMap.each { code, activityMap ->	
			
			BigDecimal plannedHours = activityMap?.get("hours") ?: 0.00
			BigDecimal plannedStdCost = activityMap?.get("stdCost") ?: 0.00
			def groupCode = activityMap?.get("groupCode")
			
			laborPlanHoursTotal = laborPlanHoursTotal + plannedHours	
			laborPlanTotal = laborPlanTotal + plannedStdCost
			laborSummaryDTOList.add(new LaborSummaryDTO(groupCode, code, null, plannedHours, 0.00, plannedStdCost, 0.00))
		}	
		
		//Project Performance
		projectPerformanceViewDTO.laborPlanHoursTotal = MiscUtils.removePrecision(laborPlanHoursTotal)
		projectPerformanceViewDTO.laborActualHoursTotal = MiscUtils.removePrecision(laborActualHoursTotal)
		
		projectPerformanceViewDTO.laborPlanTotal = MiscUtils.removePrecision(laborPlanTotal)
		projectPerformanceViewDTO.laborActualTotal = MiscUtils.removePrecision(laborActualTotal)
		
		projectPerformanceViewDTO.laborVarianceHoursTotal = projectPerformanceViewDTO?.laborActualHoursTotal - projectPerformanceViewDTO?.laborPlanHoursTotal 
		projectPerformanceViewDTO.laborVarianceTotal = projectPerformanceViewDTO?.laborActualTotal - projectPerformanceViewDTO?.laborPlanTotal
		 
		if(params?.by && params?.by.equalsIgnoreCase(Constants.REPORT_TOTAL_BY_GROUP) ) {
			
			//Get Total By Group			
			projectPerformanceViewDTO.reportTotalBy = Constants.REPORT_TOTAL_BY_GROUP
			projectPerformanceViewDTO.laborSummaryDTOList = getLaborSummaryDTOListByGroupList(laborSummaryDTOList)
			
		} else if(params?.by && params?.by.equalsIgnoreCase(Constants.REPORT_BY_COE) ) {
		
			//Get Total By COE
			projectPerformanceViewDTO.reportTotalBy = Constants.REPORT_BY_COE
			projectPerformanceViewDTO.laborSummaryDTOList = getLaborSummaryDTOListByCOEList(laborSummaryDTOList)
		} else {
		
			projectPerformanceViewDTO.reportTotalBy = Constants.REPORT_TOTAL_BY_ACTIVITY
			projectPerformanceViewDTO.laborSummaryDTOList = laborSummaryDTOList
		}
		
		return projectPerformanceViewDTO
	}
	//TODO
	def getProjectExpenseSummary(params) {		
		def projectInstance = Project.findById(Long.valueOf(params?.id))
		def projectActualExpenseCriteria = ProjectActualExpenseDetail.createCriteria()
		def  projectActualExpenseList =  projectActualExpenseCriteria.list() {
			createAlias("project", "project")
			createAlias("expense", "exp")
			projections{
				groupProperty("exp.code") //0
				sum("monetaryAmount")//1
				count("exp.code")
			}
			and {
				eq("project.id", Long.valueOf(params?.id))
			}
		}
		def actualExpenseMap = [:]
		projectActualExpenseList.each { actExp ->			
			actualExpenseMap.put(actExp[0], actExp)
		}
		//Planned Expense Start
		def plannedExpenseList = [] 
		projectInstance?.projectProductDetails.each { productDtlInstance ->			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->		
				projectDtl?.plannedExpense?.expenseDetails?.each { plannedExpense ->					
					plannedExpenseList.add(plannedExpense)					
				}				
			}
		}
		//Planned Expense End
		def plannedExpenseInstacenList = []
		def expenseCodeMap = plannedExpenseList.groupBy{ exp -> exp?.expenseCode }
		BigDecimal plannedTtotalCost = 0.00
		BigDecimal plannedTotalQty = 0.00
		BigDecimal actualTtotalCost = 0.00
		BigDecimal actualTotalQty = 0.00
		
		expenseCodeMap.each { expInstance, actExpList->			
			def projectExpenseSummaryDTO = new ProjectExpenseSummaryDTO()
			projectExpenseSummaryDTO.expenseId = expInstance?.id
			projectExpenseSummaryDTO.expenseCode = expInstance?.code
						 
			BigDecimal totalCost = 0.00
			BigDecimal plannedQty = 0.00
			 
			actExpList.each { exp ->
				totalCost = totalCost.add(exp?.totalCost?:0.00)
				plannedQty = plannedQty.add(exp?.qty?:0.00)
			}
			projectExpenseSummaryDTO.plannedQty = plannedQty
			projectExpenseSummaryDTO.plannedTotal = totalCost
			
			def actualExpenseInstance =  actualExpenseMap.get(expInstance?.code)
			if(actualExpenseInstance) {				
				projectExpenseSummaryDTO.actualQty = actualExpenseInstance[2]
				projectExpenseSummaryDTO.actualTotal = actualExpenseInstance[1]?:0.00
			}
			
			projectExpenseSummaryDTO.varianceQty = projectExpenseSummaryDTO.actualQty - projectExpenseSummaryDTO.plannedQty
			projectExpenseSummaryDTO.varianceTotal = projectExpenseSummaryDTO.actualTotal - projectExpenseSummaryDTO.plannedTotal
			
			plannedTotalQty = plannedTotalQty.add(projectExpenseSummaryDTO.plannedQty) 
			plannedTtotalCost = plannedTtotalCost.add(projectExpenseSummaryDTO.plannedTotal) 
			actualTotalQty = actualTotalQty.add(projectExpenseSummaryDTO.actualQty)  
			actualTtotalCost = actualTtotalCost.add(projectExpenseSummaryDTO.actualTotal)  
			
			plannedExpenseInstacenList.add(projectExpenseSummaryDTO)
		}
		plannedExpenseInstacenList.sort{
			obj1,obj2 -> obj1.expenseCode.compareToIgnoreCase(obj2.expenseCode)
		}
		def projectExpenseTotalMap = [:]
		if(plannedExpenseInstacenList.size() > 0) {
			projectExpenseTotalMap.put("plannedTotalQty",plannedTotalQty)
			projectExpenseTotalMap.put("actualTotalQty",actualTotalQty)
			projectExpenseTotalMap.put("varianceTotalQty",actualTotalQty-plannedTotalQty)
			projectExpenseTotalMap.put("plannedTtotalCost",plannedTtotalCost)
			projectExpenseTotalMap.put("actualTtotalCost",actualTtotalCost)
			projectExpenseTotalMap.put("varianceTotalCost",actualTtotalCost-plannedTtotalCost)
			
		}
		return [plannedExpenseInstacenList: plannedExpenseInstacenList, projectExpenseTotalMap: projectExpenseTotalMap, projectInstance: projectInstance, params: params]		
	}
	
	/**
	 * This method used to Drill Down Project Performance View Details
	 * Display Employee Hours and costing details By Project and Activity 
	 * @param projectId : Project Domain Id
	 * @param activity : Labor Activity Code
	 * @return Group By Employee List through EmployeeByProjectAndActivityDTO  
	 */
	def getEmployeeByProjectAndActivity(params) {
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectActualLaborDtlCriteria = ProjectActualLaborDetail.createCriteria()
		
		def projectActualLaborDtlInstanceList = projectActualLaborDtlCriteria.list() {
			
			and {
				createAlias("project", "project")
				createAlias("employee", "emp")
				createAlias("laborActivityCode", "laborActivityCode")
												
				eq("project.id", Long.valueOf(params?.projectId))
				
				if(loggedInUserInstance) {
					or {
						eq("project.accExecutive", loggedInUserInstance?.employee)
						
						createAlias("project.projectEmployees", "pm")
						eq("pm.employee", loggedInUserInstance?.employee)
					}
				}
				eq("laborActivityCode.code", params?.activity)
			}
			order("emp.code", "asc")
		}		
		def employeeByProjectAndActivityList = []
		
		def reportHoursTotal = 0.00
		def reportCost = 0.00		
		def reportTotalCost = 0.00
		def reportdOverheadCost = 0.00
		
		def projectCode = Project.findById(Long.valueOf(params?.projectId))?.code
		
		// Group By Employee
		def actualLaborByEmployeeList = projectActualLaborDtlInstanceList.groupBy{ emp -> emp?.employee }
			
		actualLaborByEmployeeList.each { employeeKey, actualLaborInstacneList ->
			
				def empHoursTotal = 0.00
				def empCost = 0.00
				def empTotalCost = 0.00
				def empOverheadCost = 0.00
				
				actualLaborInstacneList.each { actInstance ->
					//If costOption is Actual 
					if(params?.costOption && params?.costOption?.equalsIgnoreCase(Constants.REPORT_COST_OPTION_ACTUAL) ) {
						empHoursTotal = empHoursTotal.add(MiscUtils.removePrecision(actInstance?.hours?:0.00))
						empCost = empCost.add(MiscUtils.removePrecision(actInstance?.actualCost?:0.00))
						empOverheadCost = empOverheadCost.add(MiscUtils.removePrecision(actInstance?.actualOverheadCost?:0.00))
						empTotalCost = empTotalCost.add(MiscUtils.removePrecision(actInstance?.actualTotalCost?:0.00))
					} else {
						empHoursTotal = empHoursTotal.add(MiscUtils.removePrecision(actInstance?.hours?:0.00))
						empCost = empCost.add(MiscUtils.removePrecision(actInstance?.standardCost?:0.00))
						empOverheadCost = empOverheadCost.add(MiscUtils.removePrecision(actInstance?.standardOverheadCost?:0.00))
						empTotalCost = empTotalCost.add(MiscUtils.removePrecision(actInstance?.standardTotalCost?:0.00))
					}
				}
				def employeeByProjectAndActivityDTO = new EmployeeByProjectAndActivityDTO()
				employeeByProjectAndActivityDTO.employeeCode = employeeKey?.code
				employeeByProjectAndActivityDTO.employeeName = employeeKey?.getEmployeeName()
				employeeByProjectAndActivityDTO.hours = empHoursTotal
				employeeByProjectAndActivityDTO.cost = empCost
				employeeByProjectAndActivityDTO.overheadCost = empOverheadCost
				employeeByProjectAndActivityDTO.totalCost = empTotalCost
					
				reportHoursTotal = reportHoursTotal.add(employeeByProjectAndActivityDTO.hours)
				reportCost =  reportCost.add(employeeByProjectAndActivityDTO.cost)
				reportdOverheadCost = reportdOverheadCost.add(employeeByProjectAndActivityDTO.overheadCost)
				reportTotalCost = reportTotalCost.add(employeeByProjectAndActivityDTO.totalCost)
					
				employeeByProjectAndActivityList.add(employeeByProjectAndActivityDTO)				
			}
		 
		def reportTotalMap = [:]		
		reportTotalMap.put("activity", params?.activity)
		reportTotalMap.put("projectId", params?.projectId)		
		reportTotalMap.put("projectCode", projectCode)
		reportTotalMap.put("reportHoursTotal", reportHoursTotal)
		reportTotalMap.put("reportCost", reportCost)
		reportTotalMap.put("reportdOverheadCost", reportdOverheadCost)
		reportTotalMap.put("reportTotalCost", reportTotalCost)
		return [employeeByProjectAndActivityList: employeeByProjectAndActivityList, reportTotalMap: reportTotalMap, params: params]
	}
	
	/**
	 * This method used to Drill Down Project Performance View Details Report
	 * Display Employee Detail view by project, activity and empCode
	 * @param projectId : Project Domain Id
	 * @param activity : Labor Activity Code
	 * @param empCode : Employee Code
	 * @return Employee List through EmployeeByProjectAndActivityDTO
	 */
	def getEmployeeDtlByProjectActivityAndEmpCode(params) {
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectActualLaborDtlCriteria = ProjectActualLaborDetail.createCriteria()
		
		def projectActualLaborDtlInstanceList = projectActualLaborDtlCriteria.list() {
			
			and {
				createAlias("project", "project")
				createAlias("employee", "emp")
				createAlias("laborActivityCode", "laborActivityCode")
												
				eq("project.id", Long.valueOf(params?.projectId))
				eq("emp.code", params?.empCode)
				if(loggedInUserInstance) {
					or {
						eq("project.accExecutive", loggedInUserInstance?.employee)
						
						createAlias("project.projectEmployees", "pm")
						eq("pm.employee", loggedInUserInstance?.employee)
					}
				}
				eq("laborActivityCode.code", params?.activity)
			}
			order("emp.code", "asc")
		}
		def employeeByProjectAndActivityList = []
			
		def projectCode = Project.findById(Long.valueOf(params?.projectId))?.code
		def employeeName = Employee.findByCode(params?.empCode)?.getEmployeeName()
		
		def reportCost = 0.00		
		def reportTotalCost = 0.00
		def reportHoursTotal = 0.00
		def reportdOverheadCost = 0.00
		
		projectActualLaborDtlInstanceList.each { actLaborInstance ->			
			def employeeByProjectAndActivityDTO = new EmployeeByProjectAndActivityDTO()
			if(params?.costOption && params?.costOption?.equalsIgnoreCase(Constants.REPORT_COST_OPTION_ACTUAL) ) {
				
				employeeByProjectAndActivityDTO.employeeCode = actLaborInstance?.employee?.code
				employeeByProjectAndActivityDTO.transactionDate = actLaborInstance?.transactionDate				
				employeeByProjectAndActivityDTO.hours = MiscUtils.removePrecision(actLaborInstance?.hours?:0.00)
				employeeByProjectAndActivityDTO.cost = MiscUtils.removePrecision(actLaborInstance?.actualCost?:0.00)
				employeeByProjectAndActivityDTO.overheadCost = MiscUtils.removePrecision(actLaborInstance?.actualOverheadCost?:0.00)
				employeeByProjectAndActivityDTO.totalCost = MiscUtils.removePrecision(actLaborInstance?.actualTotalCost?:0.00)
				
			} else {
			
				employeeByProjectAndActivityDTO.employeeCode = actLaborInstance?.employee?.code
				employeeByProjectAndActivityDTO.transactionDate = actLaborInstance?.transactionDate
				employeeByProjectAndActivityDTO.hours = MiscUtils.removePrecision(actLaborInstance?.hours?:0.00)
				employeeByProjectAndActivityDTO.cost = MiscUtils.removePrecision(actLaborInstance?.standardCost?:0.00)
				employeeByProjectAndActivityDTO.overheadCost = MiscUtils.removePrecision(actLaborInstance?.standardOverheadCost?:0.00)
				employeeByProjectAndActivityDTO.totalCost = MiscUtils.removePrecision(actLaborInstance?.standardTotalCost?:0.00)
			}
			reportHoursTotal = reportHoursTotal.add(employeeByProjectAndActivityDTO.hours)
			reportCost =  reportCost.add(employeeByProjectAndActivityDTO.cost)
			reportdOverheadCost = reportdOverheadCost.add(employeeByProjectAndActivityDTO.overheadCost)
			reportTotalCost = reportTotalCost.add(employeeByProjectAndActivityDTO.totalCost)
			
			employeeByProjectAndActivityList.add(employeeByProjectAndActivityDTO)
		}
		
		def reportTotalMap = [:]		
		reportTotalMap.put("activity", params?.activity)
		reportTotalMap.put("projectId", params?.projectId)		
		reportTotalMap.put("projectCode", projectCode)
		reportTotalMap.put("employeeName", employeeName)		
		reportTotalMap.put("reportHoursTotal", reportHoursTotal)
		reportTotalMap.put("reportCost", reportCost)
		reportTotalMap.put("reportdOverheadCost", reportdOverheadCost)
		reportTotalMap.put("reportTotalCost", reportTotalCost)
		return [employeeByProjectAndActivityList: employeeByProjectAndActivityList, reportTotalMap: reportTotalMap, params: params]
	}	
	
	/**
	 * This method used to Get Labor Summary List by Group
	 * @param laborSummaryDTOList
	 * @return
	 */
	private getLaborSummaryDTOListByGroupList(laborSummaryDTOList) {
		
		def laborSummaryByGroupList = []
		
		def actualLaborProjectGroupList = laborSummaryDTOList.groupBy{ laborSummary -> laborSummary?.laborActivityGroup	}
		
		actualLaborProjectGroupList.each { laborActivityGroupInstance, val ->
			
			BigDecimal plannedHours = 0.00
			BigDecimal actualHours = 0.00
			BigDecimal varianceHours = 0.00
			BigDecimal plannedTotal = 0.00
			BigDecimal actualTotal = 0.00
			BigDecimal varianceTotal = 0.00
			
			val.each { v->
				plannedHours = plannedHours.add(v?.plannedHours)
				actualHours = actualHours.add(v?.actualHours)
				plannedTotal = plannedTotal.add(v?.plannedTotal)
				actualTotal = actualTotal.add(v?.actualTotal)
				
			}
			laborSummaryByGroupList.add(new LaborSummaryDTO(laborActivityGroupInstance, null, null, plannedHours, actualHours, plannedTotal, actualTotal))
		}
		return laborSummaryByGroupList
	}
	
	/**
	 * This method used to Get Labor Summary List by COE
	 * @param laborSummaryDTOList
	 * @return
	 */

	def getLaborSummaryDTOListByCOEList(laborSummaryDTOList) {
		
		def laborSummaryByCOEList = []
		def actualLaborProjectGroupList = laborSummaryDTOList.groupBy{ laborSummary -> laborSummary?.laborActivityGroup?.coe	}
		
		actualLaborProjectGroupList.each { coeInstance, val ->
			
			BigDecimal plannedHours = 0.00
			BigDecimal actualHours = 0.00
			BigDecimal varianceHours = 0.00
			BigDecimal plannedTotal = 0.00
			BigDecimal actualTotal = 0.00
			BigDecimal varianceTotal = 0.00
			
			val.each { v->
				plannedHours = plannedHours.add(v?.plannedHours)
				actualHours = actualHours.add(v?.actualHours)
				plannedTotal = plannedTotal.add(v?.plannedTotal)
				actualTotal = actualTotal.add(v?.actualTotal)
				
			}
			laborSummaryByCOEList.add(new LaborSummaryDTO(null, null, coeInstance, plannedHours, actualHours, plannedTotal, actualTotal))
		}
		return laborSummaryByCOEList
	}
	
	/**
	 * This method used to Get Planned Labor Details
	 * @param projectInstance
	 * @return
	 */
	def	getPlannedLaborDetailMap(projectInstance) {
		
		def plannedLaborDetailMap = [:]
		
		projectInstance?.projectProductDetails.each { projectProductDtlInstance ->
		
			projectProductDtlInstance?.projectServiceDetails?.each { projectServiceDetail ->
				
				
			projectServiceDetail?.plannedLabor?.laborDetails?.each { laborDetail ->
				
				BigDecimal hoursTotal = 0.00
				BigDecimal stdCostTotal = 0.00
				def laborHoursAndStdCostMap = [:]
				
				def activityMap = plannedLaborDetailMap.get(laborDetail?.laborActivityCode?.code)
								
				BigDecimal activityHours = activityMap?.get("hours") ?:0.00
				BigDecimal activitystdCost = activityMap?.get("stdCost") ?:0.00
				
				if(activityHours) {					
					 hoursTotal = laborDetail?.hours + activityHours										 
				} else {				
					hoursTotal = laborDetail?.hours					
				}
				
				if(activitystdCost) {					
					stdCostTotal = laborDetail?.totalCost + activitystdCost					
				} else {			   
				   stdCostTotal = laborDetail?.totalCost				   
			    }
								
				laborHoursAndStdCostMap.put("hours", hoursTotal)
				laborHoursAndStdCostMap.put("stdCost", stdCostTotal)
				laborHoursAndStdCostMap.put("groupCode", laborDetail?.laborActivityCode?.laborActivityGroup)
				
				plannedLaborDetailMap.put(laborDetail?.laborActivityCode?.code, laborHoursAndStdCostMap)
			}		
			}
		}
		return plannedLaborDetailMap
	}
	
	
	private String replaceString(str) {		
		return str.replaceAll('\\[','[[]')		
	}	 
	
	def serializeObjectInFile(def dataObjectList, String fileName){
		
		FileOutputStream fout 
		ObjectOutputStream oos
		File file
		String filePath
		try {
			
			file = FileUtils.createTempFile(fileName, ".ser")
			filePath =  file.getAbsolutePath()
			fout = new FileOutputStream(file)
			oos = new ObjectOutputStream(fout)
			oos.writeObject(dataObjectList)
			oos.close()
			fout.close()
			
		}catch(Exception e){
			log.info "Error Occuren in serialization"+e
		}finally{
		
			if(oos){
				oos.close()
			}
			
			if(fout){
				fout.close()
			}
		}
		
		return filePath
		
	}
	
	def deSerializeObject(def filePath){
		
		try {
			
			def objectList
			new File(filePath).withObjectInputStream(getClass().classLoader) { instream ->
				 objectList = instream.readObject()
			 }
			return objectList
					
		}catch(Exception e){
			log.info "Error Occuren in deSerializeObject"+e
		}finally{
		
		}	
	}
	
	def getProjectExpensesByExpenseCodeReportData(params) {
		
		log.info  "getProjectExpensesByExpenseCodeReportData @ ReportService Start"	
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectActualExpenseDtlCriteria = ProjectActualExpenseDetail.createCriteria()
		def projectActualExpenseDtlInstanceList = projectActualExpenseDtlCriteria.list() {
			createAlias("project", "project")
			createAlias("project.customer", "customer")			
			createAlias("expense", "exp")
			and {								
				expenseCriteriaClosure.delegate = delegate
				expenseCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code","asc")
			order("exp.code","asc")
		}
		log.info "getProjectExpensesByExpenseCodeReportData DB List"+projectActualExpenseDtlInstanceList.size()
		def reportTotalMap = [:]
		def projectByExpenseList = []
		def reportActualTotalAmount = 0.00
		
		//Group By Project
		def actualExpenseProjectGroupList = projectActualExpenseDtlInstanceList.groupBy{ projectExpense -> projectExpense?.project	}
		
		actualExpenseProjectGroupList.each { projectKey, actualExpInstanceList ->
			
			ProjectExpenseDetailDTO projectExpenseDetailDTO =new ProjectExpenseDetailDTO()
			projectExpenseDetailDTO.projectCode = projectKey?.code
			projectExpenseDetailDTO.projectName = projectKey?.name
			projectExpenseDetailDTO.customerCode = projectKey?.customer?.code			
			projectExpenseDetailDTO.acctExecutiveCode = projectKey?.accExecutive?.code
						
			def expenseDtlList = []
			
			def actualExpenseCodeGroupList = actualExpInstanceList.groupBy { expseneInst -> expseneInst?.expense }
			def projectExpenseAmount = 0.00
			actualExpenseCodeGroupList.each { expenseInstance, actualExpDtlInstanceList ->	
				
				def expenseCodeList = []
				ExpenseDetailDTO expenseDetailDTO = new ExpenseDetailDTO()
				
				expenseDetailDTO.expenseCode = expenseInstance?.code
				def actualExpenseAmount = 0.00
				actualExpDtlInstanceList.each { actualExp ->
					ActualExpenseDetailDTO actualExpenseDetailDTO= new ActualExpenseDetailDTO()
					actualExpenseDetailDTO.expenseCode = actualExp?.expense?.code
					actualExpenseDetailDTO.postedDate = actualExp?.postedDate
					actualExpenseDetailDTO.vendorId = actualExp?.vendorId
					actualExpenseDetailDTO.vendorName = actualExp?.name1
					actualExpenseDetailDTO.glAmount = actualExp?.account
					actualExpenseDetailDTO.invoice = actualExp?.invoiceId
					actualExpenseDetailDTO.amount = actualExp?.monetaryAmount
					actualExpenseAmount = actualExpenseAmount.add(actualExp?.monetaryAmount?:0.00)
					expenseCodeList.add(actualExpenseDetailDTO)
				}
				projectExpenseAmount = projectExpenseAmount.add(actualExpenseAmount)				
				expenseDetailDTO.amount = actualExpenseAmount				
				expenseDetailDTO.actualExpenseDetailDTOList = expenseCodeList
				expenseDtlList.add(expenseDetailDTO)
			}
			reportActualTotalAmount = reportActualTotalAmount.add(projectExpenseAmount)
			projectExpenseDetailDTO.amount = projectExpenseAmount
			projectExpenseDetailDTO.expenseDetailDTOList = expenseDtlList
			projectByExpenseList.add(projectExpenseDetailDTO)
		}		
				
		reportTotalMap.put("reportActualTotalAmount", reportActualTotalAmount)
			
		log.info "getProjectExpensesByExpenseCodeReportData size "+projectByExpenseList.size()
		log.info  "getProjectExpensesByExpenseCodeReportData @ ReportService End"
		
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), projectByExpenseList: projectByExpenseList, reportTotalMap: reportTotalMap, params: params]
	}
	
	def projectByExpenseReportList(params) {
		def reportSubTotal = []
		def projectSubTotalMap = [:]
		def projectByExpenseReporList = []
		def projectByExpenseSubTotalMap =[:]
		def projectByExpenseReporCount = 0
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		if(params?.includeDetail) {
		
			projectByExpenseReporList = getAllActualExpenseReportList(params, loggedInUserInstance)
			if(projectByExpenseReporList.size() > 0) {
					
				def projectByExpenseSubTotalList = projectByExpenseCodeSubTotalList(params, loggedInUserInstance)
				projectByExpenseSubTotalList.each { exp->
					//Project _ Expense
					projectByExpenseSubTotalMap.put(exp[0]+"_"+exp[1], exp)
				}
					
				projectSubTotalMap = projectByExpenseByProjectSubTotalList(params, loggedInUserInstance)
				reportSubTotal = projectByExpenseByReportSubTotalList(params, loggedInUserInstance)
			}
			projectByExpenseReporCount = getAllActualExpenseReportCount(params, loggedInUserInstance)
		} else {
							
				projectByExpenseReporList = projectByExpenseCodeSubTotalList(params, loggedInUserInstance)
				
				projectByExpenseReporCount = projectByExpenseCodeSubTotalCount(params, loggedInUserInstance)
				
				if(projectByExpenseReporList.size() > 0) {
					projectSubTotalMap = projectByExpenseByProjectSubTotalList(params, loggedInUserInstance)
					
					reportSubTotal = projectByExpenseByReportSubTotalList(params, loggedInUserInstance)
				}
			}
			return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), params: params, projectByExpenseReporList: projectByExpenseReporList, projectByExpenseReporCount: projectByExpenseReporCount, projectByExpenseSubTotalMap: projectByExpenseSubTotalMap, projectSubTotalMap: projectSubTotalMap, reportSubTotal: reportSubTotal]
	}
	
	def getAllActualExpenseReportList(params, loggedInUserInstance) {
		
		def projectActualExpenseDtlCriteria = ProjectActualExpenseDetail.createCriteria()		
		def projectActualExpenseDtlInstanceList = projectActualExpenseDtlCriteria.list(max: params?.max?: Constants.DEFAULT_REPORT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			createAlias("project", "project")
			createAlias("project.customer", "customer")
			createAlias("expense", "exp")
			and {		
				expenseCriteriaClosure.delegate = delegate
				expenseCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code","asc")
			order("exp.code","asc")
		}
		return projectActualExpenseDtlInstanceList
	}
	def getAllActualExpenseReportCount(params, loggedInUserInstance) {
		
		def projectActualExpenseDtlCriteria = ProjectActualExpenseDetail.createCriteria()
		def projectActualExpenseDtlInstanceList = projectActualExpenseDtlCriteria.list() {
			createAlias("project", "project")
			createAlias("project.customer", "customer")
			createAlias("expense", "exp")
			and {
				expenseCriteriaClosure.delegate = delegate
				expenseCriteriaClosure(params, loggedInUserInstance)
			}			
		}		
		return projectActualExpenseDtlInstanceList.size()
	}
	def projectByExpenseCodeSubTotalList(params, loggedInUserInstance) {
		def listParams = [:]
		if(!params?.includeDetail) {
		 listParams = ["max": params?.max , "offset": params?.offset?:0]
		}
		def projectActualExpenseDtlCriteria = ProjectActualExpenseDetail.createCriteria()
		def projectByExpenseCodeSubTotalList = projectActualExpenseDtlCriteria.list(listParams) {
			createAlias("project", "project")
			createAlias("project.customer", "customer")
			createAlias("project.accExecutive", "accExt")
			createAlias("expense", "exp")
			and {
				projections{
					groupProperty("project.code") //0
					groupProperty("exp.code") //1
					sum("monetaryAmount")//2
					groupProperty("project.name") //3
					groupProperty("customer.code") //4
					groupProperty("accExt.code") //5					
				}
				expenseCriteriaClosure.delegate = delegate
				expenseCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code","asc")
			order("exp.code","asc")
		}		
		return projectByExpenseCodeSubTotalList
	}
	def projectByExpenseCodeSubTotalCount(params, loggedInUserInstance) {
		
		def projectActualExpenseDtlCriteria = ProjectActualExpenseDetail.createCriteria()
		def projectByExpenseCodeSubTotalList = projectActualExpenseDtlCriteria.list() {
			createAlias("project", "project")
			createAlias("project.customer", "customer")
			createAlias("project.accExecutive", "accExt")
			createAlias("expense", "exp")
			and {
				projections{
					groupProperty("project.code") //0
					groupProperty("exp.code") //1
					sum("monetaryAmount")//2
					groupProperty("project.name") //3
					groupProperty("customer.code") //4
					groupProperty("accExt.code") //5
				}
				expenseCriteriaClosure.delegate = delegate
				expenseCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code","asc")
			order("exp.code","asc")
		}
		return projectByExpenseCodeSubTotalList.size()
	}
	def projectByExpenseByProjectSubTotalList(params, loggedInUserInstance) {
		
		def projectActualExpenseDtlCriteria = ProjectActualExpenseDetail.createCriteria()
		def projectByExpenseByProjectSubTotalList = projectActualExpenseDtlCriteria.list() {
			createAlias("project", "project")
			createAlias("project.customer", "customer")
			createAlias("expense", "exp")
			and {
				projections{
					groupProperty("project.code") //0			
					sum("monetaryAmount")//1
				}
				expenseCriteriaClosure.delegate = delegate
				expenseCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code","asc")			
		}
		def projectSubTotalMap = [:]
		projectByExpenseByProjectSubTotalList.each { exp ->
			projectSubTotalMap.put(exp[0],exp)
		}
		return projectSubTotalMap
	}
	def projectByExpenseByReportSubTotalList(params, loggedInUserInstance) {
		
		def projectActualExpenseDtlCriteria = ProjectActualExpenseDetail.createCriteria()
		def reportSubTotalList = projectActualExpenseDtlCriteria.list() {
			createAlias("project", "project")
			createAlias("project.customer", "customer")
			createAlias("expense", "exp")
			and {
				projections{			
					sum("monetaryAmount")//0
				}
				expenseCriteriaClosure.delegate = delegate
				expenseCriteriaClosure(params, loggedInUserInstance)
			}			
		}			 
		return reportSubTotalList
	}
	def expenseCriteriaClosure = { params, loggedInUserInstance ->
		if (params?.project) {
			ilike("project.code", "%" + replaceString(params?.project) +"%")
		}
		if(params?.expense) {
			ilike("exp.code", "%" + replaceString(params?.expense) +"%")
		}
		if (params?.customer) {			
			ilike("customer.code", "%" + replaceString(params?.customer) +"%")
		}
		if (params?.glaccount) {
			ilike("account", "%" + replaceString(params?.glaccount) +"%")
		}
		if (params?.vendorId) {
			ilike("vendorId", "%" + replaceString(params?.vendorId) +"%")
		}
		if (params?.invoiceId) {
			ilike("invoiceId", "%" + replaceString(params?.invoiceId) +"%")
		}
		if(loggedInUserInstance) {
			or {
				eq("project.accExecutive", loggedInUserInstance?.employee)
				if (params?.projectManager) {
					createAlias("project.projectEmployees", "pm")
					eq("pm.employee", loggedInUserInstance?.employee)
				}
			}
		} else {
			if (params?.projectManager) {
				createAlias('project.projectEmployees', 'pm', CriteriaSpecification.LEFT_JOIN)
			}
		}
		if (params?.acctExecutive) {
				createAlias("project.accExecutive", "accExecutive")
				ilike("accExecutive.code", "%" + params?.acctExecutive +"%")
		}
		if (params?.projectManager) {
			createAlias("pm.employee", "pmEmp")
			ilike("pmEmp.code", "%" + params?.projectManager +"%")
		}
		if (params?.fromDate) {
			gt("postedDate",DateFormatUtils.getDateFromString(params?.fromDate))
		}
		if (params?.toDate) {
			lt("postedDate",DateFormatUtils.getDateFromString(params?.toDate))
		}
	
	}
	def getAllActualExpenseList(params) {
		
		log.info "getAllActualExpenseList @ ReportService Start"	
		def actualExpenseInstacenList = []
		def projectInstance = null
		BigDecimal totalAmount = 0.00
		if(params?.id) {
			projectInstance = Project.get(Long.valueOf(params?.id))			
			def actualExpenseCriteria = ProjectActualExpenseDetail.createCriteria()
			def actualExpenseList = actualExpenseCriteria.list() {
				createAlias("project", "project")
				createAlias("expense", "exp")				
				and {
					projections{
						groupProperty("exp.id") //0
						groupProperty("exp.code") //1
						sum("monetaryAmount")//2						
					}
					eq("project.id", Long.valueOf(params?.id))						
				}			
				order("exp.code","asc")
			}			
			actualExpenseList.each { exp ->
				def actualExpenseDetailDTO = new ActualExpenseDetailDTO()
				actualExpenseDetailDTO.expenseId = exp[0]
				actualExpenseDetailDTO.expenseCode = exp[1]
				actualExpenseDetailDTO.amount = exp[2] ?: 0.00
				actualExpenseInstacenList.add(actualExpenseDetailDTO)
				totalAmount = totalAmount.add(actualExpenseDetailDTO.amount)
			}
		}	
		log.info "getAllActualExpenseList @ ReportService End"
		return [actualExpenseInstacenList: actualExpenseInstacenList, projectInstance: projectInstance, params: params, totalAmount: totalAmount]
	}
	def getAllActualExpenseDetailByExpenseCode(params) {
		log.info "getAllActualExpenseDetailByExpCode @ ReportService Start"
		def projectInstance = Project.get(Long.valueOf(params?.prodId))
		def actualExpenseCriteria = ProjectActualExpenseDetail.createCriteria()
		def actualExpenseList = actualExpenseCriteria.list() {
			and {
					createAlias("project", "project")
					eq("expense.id", Long.valueOf(params?.expId))
					eq("project.id", Long.valueOf(params?.prodId))				
			}
			order("name1","asc")
		}		
		log.info "getAllActualExpenseDetailByExpCode @ ReportService End"		
		return [actualExpenseList: actualExpenseList, projectInstance: projectInstance, params: params]
	}
	
	def getAllActualLaborEmployeeList(params) {
		
		log.info "getAllActualLaborEmployeeList @ ReportService Start"		
		def employeeDetailDTOList = []		
		def projectInstance = Project.get(Long.valueOf(params?.id))
		def actualLaborCriteria = ProjectActualLaborDetail.createCriteria()		
		def actualLaborCriteriaList = actualLaborCriteria.list() {
			createAlias("project", "project")
			createAlias("employee", "emp")
			and {					
					eq("project.id", Long.valueOf(params?.id))				
			}
			order("emp.firstName","asc")
		}		
		def employeeGroupList = actualLaborCriteriaList.groupBy{ emp -> emp?.employee }
		employeeGroupList.each { empInstance, actualLaborEmpList ->
			
			def employeeHours = 0.00
			def employeeStandardCost = 0.00			
			def employeeActualCost = 0.00
			def employeeStandardTotalCost = 0.00
			def employeeStandardOverheadCost = 0.00			
			
			actualLaborEmpList.each { actualLaborActivityCodeInstance ->
											
				employeeHours = employeeHours.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.hours?:0.00))				
				employeeStandardCost = employeeStandardCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardCost))				
				employeeStandardOverheadCost = employeeStandardOverheadCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardOverheadCost))				
				employeeStandardTotalCost = employeeStandardTotalCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardTotalCost))				
				employeeActualCost = employeeActualCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.actualCost))				
			}		
			def employeeDetailDTO = new EmployeeDetailDTO(empInstance?.id, empInstance?.getEmployeeName(), employeeHours, employeeStandardCost, employeeStandardOverheadCost, employeeStandardTotalCost, employeeActualCost, null, null)
			employeeDetailDTOList.add(employeeDetailDTO)
		}		 
		log.info "getAllActualLaborEmployeeList @ ReportService End"		
		return [employeeDetailDTOList: employeeDetailDTOList, projectInstance: projectInstance, params: params]
	}
	def getAllActualLaborActivityDetail(params) {
		
		log.info "getAllActualLaborActivityDetail @ ParentProjectService Start"
		
		def projectInstance = Project.get(Long.valueOf(params?.projId))
				
		def actualLaborEmployeeCriteria = ProjectActualLaborDetail.createCriteria()
		def actualLaborActivityCriteriaList = actualLaborEmployeeCriteria.list() {   
		   createAlias("project", "project")
		   createAlias("employee", "emp")
		   createAlias("laborActivityCode", "lbrActCode")
		   
		   and {
				 eq("project.id", projectInstance?.id)			  
				 eq("emp.id", Long.valueOf(params?.empId))
		   }			  
		   order("lbrActCode.code","asc")		   
		}
				
		def activityHours = 0.00		
		def activityStandardCost = 0.00		
		def activityActualCost = 0.00
		def activityStandardTotalCost = 0.00	
		def activityStandardOverheadCost = 0.00		
		
		actualLaborActivityCriteriaList.each { actualInstance->
			
			activityHours = activityHours.add(MiscUtils.removePrecision(actualInstance?.hours?:0.00))
			
			activityStandardCost = activityStandardCost.add(MiscUtils.removePrecision(actualInstance?.standardCost))
			
			activityStandardOverheadCost = activityStandardOverheadCost.add(MiscUtils.removePrecision(actualInstance?.standardOverheadCost))
			
			activityStandardTotalCost = activityStandardTotalCost.add(MiscUtils.removePrecision(actualInstance?.standardTotalCost))
			
			activityActualCost = activityActualCost.add(MiscUtils.removePrecision(actualInstance?.actualCost))
		}
		
		def activityTotalMap =[:]		
		
		activityTotalMap.put("activityHours", activityHours)
		activityTotalMap.put("activityStandardCost", activityStandardCost)
		activityTotalMap.put("activityStandardOverheadCost", activityStandardOverheadCost)
		activityTotalMap.put("activityStandardTotalCost", activityStandardTotalCost)
		activityTotalMap.put("activityActualCost", activityActualCost)
		
		log.info "getAllActualLaborActivityDetail @ ParentProjectService End"
		
		return [actualLaborActivityCriteriaList: actualLaborActivityCriteriaList, activityTotalMap: activityTotalMap, projectInstance:projectInstance, params: params]
	}
}
