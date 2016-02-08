package org.solcorp.etech
import grails.transaction.Transactional

import org.hibernate.criterion.CriteriaSpecification
import org.solcorp.etech.utils.DateFormatUtils
import org.solcorp.etech.utils.MiscUtils
@Transactional
class LaborHistoryByCOEReportService {	
	
	def userService
	
	/**
	 * This method used to get Project List by search params
	 * @param params
	 * @return
	 */	
	def getAllActivityByEmployeeReportList(params) {		
		log.info  "getAllActivityByEmployeeReportList @ ActivityByEmployeeReportService Start"
		
		def reportTotal = []
		def coeTotalMap = [:]
		def projectTotalMap = [:]		
		def projectByActivityMap = [:]
		def projectActivityTotalCount = 0
		def projectActualLaborDtlInstanceList = []
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
				
		projectActualLaborDtlInstanceList = getProjectByEmployeeByActivityList(params, loggedInUserInstance)			
		projectActivityTotalCount = getProjectByEmployeeByActivityCount(params, loggedInUserInstance)
			
		if(projectActualLaborDtlInstanceList.size() > 0) {			
			projectTotalMap = getAllGroupByProjectTotal(params, loggedInUserInstance)			
			projectByActivityMap = getGroupByProjectByActivityTotal(params, loggedInUserInstance)
			reportTotal = getReportTotal(params, loggedInUserInstance)
			coeTotalMap = getAllGroupByCOETotal(params, loggedInUserInstance)
		}
		 		
		log.info "Database projectActualLaborDtlInstanceList:::::::"+projectActualLaborDtlInstanceList.size()
		log.info "projectActivityTotalCount:::::::"+projectActivityTotalCount
		
		log.info  "getAllActivityByEmployeeReportList @ ActivityByEmployeeReportService End"
		
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), params: params, projectActivityTotalCount: projectActivityTotalCount, projectActualLaborDtlInstanceList: projectActualLaborDtlInstanceList, projectTotalMap: projectTotalMap, projectByActivityMap: projectByActivityMap, reportTotal:reportTotal, coeTotalMap: coeTotalMap]
	}	
	/**
	 * If includeDetail flag = false then get all record by pagination else All Activity Record list 
	 * @param params
	 * @param loggedInUserInstance
	 * @return
	 */
	private getProjectByEmployeeByActivityList(params, loggedInUserInstance) {
		log.info  "getProjectByEmployeeByActivityList @ ActivityByEmployeeReportService Start"
				
		def orderByProjectByEmployeeCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByEmployeeList = orderByProjectByEmployeeCriteria.list(max: params?.max , offset: params?.offset?:0) {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)				
			
				createAlias("laborActivityCode.laborActivityGroup", "activityGroup", CriteriaSpecification.LEFT_JOIN)
				createAlias("activityGroup.coe", "coe", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("coe.code") //0
					groupProperty("project.code") //1
					groupProperty("emp.code") //2
					groupProperty("laborActivityCode.code") //3
					sum("hours")//4					
					sum("standardCost") //5
					sum("standardOverheadCost") //6
					sum("standardTotalCost") //7
					sum("actualCost") //8
					groupProperty("customer.code")//9
					groupProperty("project.name")//10
					groupProperty("emp.firstName")//11
					groupProperty("emp.lastName")//12
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("coe.code", "asc")
			order("project.code", "asc")
			order("laborActivityCode.code", "asc")
			order("emp.code", "asc")
		}
		log.info  "getProjectByEmployeeByActivityList @ ActivityByEmployeeReportService End"
		return orderByProjectByEmployeeList
	}
	/**
	 * 
	 * @param params
	 * @param loggedInUserInstance
	 * @return
	 */
	private getProjectByEmployeeByActivityCount(params, loggedInUserInstance) {
		log.info  "getProjectByEmployeeByActivityCount @ ActivityByEmployeeReportService Start"
		def orderByProjectByEmployeeCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByEmployeeList = orderByProjectByEmployeeCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)			
			
				createAlias("laborActivityCode.laborActivityGroup", "activityGroup", CriteriaSpecification.LEFT_JOIN)
				createAlias("activityGroup.coe", "coe", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("coe.code") //0
					groupProperty("project.code") //1
					groupProperty("emp.code") //2
					groupProperty("laborActivityCode.code") //3
					sum("hours")//4					
					sum("standardCost") //5
					sum("standardOverheadCost") //6
					sum("standardTotalCost") //7
					sum("actualCost") //8
					groupProperty("customer.code")//9
					groupProperty("project.name")//10
					groupProperty("emp.firstName")//11
					groupProperty("emp.lastName")//12
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("coe.code", "asc")
			order("project.code", "asc")
			order("emp.code", "asc")
			order("laborActivityCode.code", "asc")
		}
		log.info  "getProjectByEmployeeByActivityCount @ ActivityByEmployeeReportService End"
		return orderByProjectByEmployeeList.size()
	}
	/**
	 * 
	 * @param params
	 * @param loggedInUserInstance
	 * @return
	 */
	private getGroupByProjectByActivityTotal(params, loggedInUserInstance) {
		log.info  "getGroupByProjectByActivityTotal @ ActivityByEmployeeReportService Start"
		def orderByProjectByActvityCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByActivityList = orderByProjectByActvityCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode.laborActivityGroup", "activityGroup" , CriteriaSpecification.LEFT_JOIN)
				createAlias("activityGroup.coe", "coe", CriteriaSpecification.LEFT_JOIN)
				projections{
					groupProperty("project.code") //0					
					groupProperty("laborActivityCode.code") //1
					sum("hours")//2					
					sum("standardCost") //3
					sum("standardOverheadCost") //4
					sum("standardTotalCost") //5
					sum("actualCost") //6
					groupProperty("coe.code") //7
			 
				}								
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code", "asc")			
			order("laborActivityCode.code", "asc")
		}
		
		def projectByActivityMap = [:]
		orderByProjectByActivityList.each { act->
			projectByActivityMap.put(act[7]+'_'+act[0]+"_"+act[1], act)
		}
		log.info  "getGroupByProjectByActivityTotal @ ActivityByEmployeeReportService End"
		return projectByActivityMap
	}
	/**
	 * 
	 * @param params
	 * @param loggedInUserInstance
	 * @return
	 */
	private getAllGroupByProjectTotal(params, loggedInUserInstance) {
		log.info  "getAllGroupByProjectTotal @ ActivityByEmployeeReportService Start"
		def orderByProjectCriteria = ProjectActualLaborDetail.createCriteria()
		def projectTotalList = orderByProjectCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.INNER_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode.laborActivityGroup", "activityGroup" , CriteriaSpecification.LEFT_JOIN)
				createAlias("activityGroup.coe", "coe", CriteriaSpecification.LEFT_JOIN)
				projections{
					groupProperty("coe.code") //0
					groupProperty("hhProjectId") //0
					sum("hours")//1
					sum("standardCost") //2
					sum("standardOverheadCost") //3
					sum("standardTotalCost") //4
					sum("actualCost") //5
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("coe.code","asc")
			order("hhProjectId","asc")
		}
		def projectTotalMap = [:]
		projectTotalList.each{ groupByProject ->
			projectTotalMap.put(groupByProject[0]+'_'+groupByProject[1], groupByProject)
		}
		log.info  "getAllGroupByProjectTotal @ ActivityByEmployeeReportService End"
		return projectTotalMap
	}
	/**
	 *
	 * @param params
	 * @param loggedInUserInstance
	 * @return
	 */
	private getAllGroupByCOETotal(params, loggedInUserInstance) {
		log.info  "getAllGroupByCOETotal @ ActivityByEmployeeReportService Start"
		def orderByProjectCriteria = ProjectActualLaborDetail.createCriteria()
		def projectTotalList = orderByProjectCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.INNER_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode.laborActivityGroup", "activityGroup", CriteriaSpecification.LEFT_JOIN)
				createAlias("activityGroup.coe", "coe", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("coe.code") //0
					sum("hours")//1
					sum("standardCost") //2
					sum("standardOverheadCost") //3
					sum("standardTotalCost") //4
					sum("actualCost") //5
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("coe.code","asc")
		}
		def coeTotalMap = [:]
		projectTotalList.each{ groupBycoe ->
			coeTotalMap.put(groupBycoe[0], groupBycoe)
		}
		log.info  "getAllGroupByCOETotal @ ActivityByEmployeeReportService End"
		return coeTotalMap
	}
	/**
	 * 
	 * @param params
	 * @param loggedInUserInstance
	 * @return
	 */
	private getReportTotal(params, loggedInUserInstance) {
		log.info  "getReportTotal @ ActivityByEmployeeReportService Start"
		def orderByProjectByActvityCriteria = ProjectActualLaborDetail.createCriteria()
		def reportTotal = orderByProjectByActvityCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode.laborActivityGroup", "activityGroup" , CriteriaSpecification.LEFT_JOIN)
				createAlias("activityGroup.coe", "coe", CriteriaSpecification.LEFT_JOIN)
				projections{
					sum("hours")//0
					sum("standardCost") //1
					sum("standardOverheadCost") //2
					sum("standardTotalCost") //3
					sum("actualCost") //4
			 
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
		} 
		log.info  "getReportTotal @ ActivityByEmployeeReportService End"
		return reportTotal
	}
	def getlaborHistoryByCOEForExport(params) {
		
		log.info "getlaborHistoryByCOEForExport Start"
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectActualLaborDtlCriteria = ProjectActualLaborDetail.createCriteria()
		def projectActualLaborDtlInstanceList = projectActualLaborDtlCriteria.list() {
			
			createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
			createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
			createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
			createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
			createAlias("laborActivityCode.laborActivityGroup", "activityGroup" , CriteriaSpecification.LEFT_JOIN)
			createAlias("activityGroup.coe", "coe", CriteriaSpecification.LEFT_JOIN)
			and {
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("coe.code", "asc")
			order("project", "asc")
		}
		log.info "Total Labor trnx Table Rows::"+projectActualLaborDtlInstanceList.size()
		
		def actualLaborCOEMap = projectActualLaborDtlInstanceList.groupBy{ laborCoe -> laborCoe?.laborActivityCode?.laborActivityGroup?.coe }
		
		def coeLaborHistoryList = []
		
		def reportHoursTotal = 0.00
		def reportStandardCost = 0.00
		def reportActualCost = 0.00
		def reportStandardTotalCost = 0.00
		def reportStandardOverheadCost = 0.00
						
		actualLaborCOEMap.each { coeInstance, projectLaborDtlInstanceList ->
			
			def laborHistoryCOEReportDTO = new LaborHistoryCOEReportDTO()
			
			laborHistoryCOEReportDTO.code = coeInstance?.code

			def coeHours = 0.00
			def coeStandardCost = 0.00
			def coeActualCost = 0.00
			def coeStandardTotalCost = 0.00
			def coeStandardOverheadCost = 0.00
			def activityByEmployeeReportList = []
			//Group By Project Start
			def actualLaborProjectGroupList = projectLaborDtlInstanceList.groupBy{ actualLabor -> actualLabor?.project	}
			
			actualLaborProjectGroupList.each {  projectKey, actualLaborInstanceList ->
				
				def activityByEmployeeReportDTO = new ActivityByEmployeeReportDTO()
				
				activityByEmployeeReportDTO.projectCode = projectKey?.code
				activityByEmployeeReportDTO.projectName = projectKey?.name
				activityByEmployeeReportDTO.customerCode = projectKey?.customer?.code
				activityByEmployeeReportDTO.acctExecutiveCode = projectKey?.accExecutive?.code
				//Group By Activity Code
				def actualLaborActivityCodeGroupList = actualLaborInstanceList.groupBy { activityLabor -> activityLabor?.laborActivityCode }
									
				def projectHours = 0.00
				def projectStandardCost = 0.00
				def projectActualCost = 0.00
				def projectStandardTotalCost = 0.00
				def projectStandardOverheadCost = 0.00
				
				def operationDetailDTOList = []
				
				actualLaborActivityCodeGroupList.each { activityCodeInstacneKey, actualLaborCodeList ->
						
					def operationDetailDTO = new OperationDetailDTO()
						
					// Group By Employee
					def employeeGroupList =	actualLaborCodeList.groupBy{ emp -> emp?.employee }
														
						def operationHours = 0.00
						def operationStandardCost = 0.00
						def operationActualCost = 0.00
						def operationStandardTotalCost = 0.00
						def operationStandardOverheadCost = 0.00
						
						def employeeDetailList = []
						
						employeeGroupList.each { empInstance, actualLaborEmpList ->
							
							def employeeHours = 0.00
							def employeeStandardCost = 0.00
							def employeeActualCost = 0.00
							def employeeStandardTotalCost = 0.00
							def employeeStandardOverheadCost = 0.00
							
							def activityDetailDTOList = []
							
							actualLaborEmpList.each { actualLaborActivityCodeInstance ->
															
								employeeHours = employeeHours.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.hours ?:0.00))
								employeeStandardCost = employeeStandardCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardCost ?:0.00))
								employeeStandardOverheadCost = employeeStandardOverheadCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardOverheadCost ?:0.00))
								employeeStandardTotalCost = employeeStandardTotalCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardTotalCost ?:0.00))
								employeeActualCost = employeeActualCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.actualCost ?:0.00))
							}
							
							def employeeDetailDTO = new EmployeeDetailDTO(empInstance?.getEmployeeName(), employeeHours, employeeStandardCost, employeeStandardOverheadCost, employeeStandardTotalCost, employeeActualCost, activityDetailDTOList, empInstance?.code)
							
							employeeDetailList.add(employeeDetailDTO)
							operationHours = operationHours.add(employeeHours)
							operationStandardCost = operationStandardCost.add(employeeStandardCost)
							operationStandardOverheadCost = operationStandardOverheadCost.add(employeeStandardOverheadCost)
							operationStandardTotalCost = operationStandardTotalCost.add(employeeStandardTotalCost)
							operationActualCost = operationActualCost.add(employeeActualCost)
						}
						
						operationDetailDTO.hours = operationHours
						operationDetailDTO.standardCost = operationStandardCost
						operationDetailDTO.standardOverheadCost = operationStandardOverheadCost
						operationDetailDTO.standardTotalCost = operationStandardTotalCost
						operationDetailDTO.actualCost = operationActualCost
						operationDetailDTO.laborActivityCode = activityCodeInstacneKey?.code
						operationDetailDTO.employeeDetailList = employeeDetailList
						operationDetailDTOList.add(operationDetailDTO)

						projectHours = projectHours.add(operationHours)
						projectStandardCost = projectStandardCost.add(operationStandardCost)
						projectStandardOverheadCost = projectStandardOverheadCost.add(operationStandardOverheadCost)
						projectStandardTotalCost = projectStandardTotalCost.add(operationStandardTotalCost)
						projectActualCost = projectActualCost.add(operationActualCost)
						
					}
				
					activityByEmployeeReportDTO.projectHours = projectHours
					activityByEmployeeReportDTO.projectStandardCost = projectStandardCost
					activityByEmployeeReportDTO.projectStandardOverheadCost = projectStandardOverheadCost
					activityByEmployeeReportDTO.projectStandardTotalCost = projectStandardTotalCost
					activityByEmployeeReportDTO.projectActualCost = projectActualCost
					activityByEmployeeReportDTO.operationDetailDTOList = operationDetailDTOList
					activityByEmployeeReportList.add(activityByEmployeeReportDTO)
					
					coeHours = coeHours.add(projectHours)
					coeStandardCost = coeStandardCost.add(projectStandardCost)
					coeActualCost = coeActualCost.add(projectActualCost)
					coeStandardTotalCost =coeStandardTotalCost.add(projectStandardTotalCost)
					coeStandardOverheadCost = coeStandardOverheadCost.add(projectStandardOverheadCost)
					
			}
			laborHistoryCOEReportDTO.hours = coeHours
			laborHistoryCOEReportDTO.standardCost = coeStandardCost
			laborHistoryCOEReportDTO.standardOverheadCost = coeStandardOverheadCost
			laborHistoryCOEReportDTO.standardTotalCost = coeStandardTotalCost
			laborHistoryCOEReportDTO.actualCost = coeActualCost
			laborHistoryCOEReportDTO.activityByEmployeeReportDTO = activityByEmployeeReportList
			
			reportHoursTotal = reportHoursTotal.add(coeHours)
			reportStandardCost = reportStandardCost.add(coeStandardCost)
			reportStandardOverheadCost = reportStandardOverheadCost.add(coeStandardOverheadCost)
			reportStandardTotalCost =reportStandardTotalCost.add(coeStandardTotalCost)
			reportActualCost = reportActualCost.add(coeActualCost)
			coeLaborHistoryList.add(laborHistoryCOEReportDTO)
		}		
		def reportTotalMap = [:] 
		
		reportTotalMap.put("reportHoursTotal", reportHoursTotal)
		reportTotalMap.put("reportStandardCost", reportStandardCost)
		reportTotalMap.put("reportStandardOverheadCost", reportStandardOverheadCost)
		reportTotalMap.put("reportStandardTotalCost", reportStandardTotalCost)
		reportTotalMap.put("reportActualCost", reportActualCost)
		
		log.info "coeLaborHistoryList::::::::::"+coeLaborHistoryList.size()
		log.info "getlaborHistoryByCOEForExport End"
		return [coeLaborHistoryList: coeLaborHistoryList, reportTotalMap: reportTotalMap, params: params]
	}
	
	def sortCriteriaClosure = { params, loggedInUserInstance ->
		log.info "sortCriteriaClosure @ ActivityByEmployeeReportService Start"
		
		isNotNull("laborActivityCode")
		isNotNull("laborActivityCode.laborActivityGroup")
		isNotNull("activityGroup.coe")		
		
		if (params?.coeCode) {
			like("coe.code", "%" + params?.coeCode + "%")
		}
		if (params?.project) {
			ilike("project.code", "%" + replaceString(params?.project) +"%")
		}
		if (params?.customer) {
			ilike("customer.code", "%" + replaceString(params?.customer) +"%")
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
		if (params?.employee) {
			ilike("emp.code", "%" + params?.employee +"%")
		}
		if (params?.laborActCode) {
			ilike("laborActivityCode.code", "%" + params?.laborActCode +"%")
		}
		if (params?.laborActGroupCode) {		
			ilike("activityGroup.code", "%" + params?.laborActGroupCode +"%")
		}
		if (params?.operations) {
			eq("laborActivityCode.operations", params?.operations)
		}
		if (params?.payDepartment) {
			createAlias("emp.payDept", "payDpt")
			ilike("payDpt.code", "%" + params?.payDepartment +"%")
		}
		if (params?.projectCategory) {
			createAlias("project.projectCategory", "projectCategory")
			ilike("projectCategory.category", "%" + params?.projectCategory +"%")
		}
		if (params?.projectType ) {
			eq("project.projectType", ProjectType.valueOf(params?.projectType))
		}
		if (params?.billCodeType) {
			eq("project.billCodeType", BillCodeType.valueOf(params?.billCodeType))
		}
		if (params?.fromDate) {
			gt("transactionDate",DateFormatUtils.getDateFromString(params?.fromDate))
		}
		if (params?.toDate) {
			lt("transactionDate",DateFormatUtils.getDateFromString(params?.toDate))
		}
		log.info "sortCriteriaClosure @ ActivityByEmployeeReportService End"
	}
	private String replaceString(str) {
		return str.replaceAll('\\[','[[]')
	}
}
