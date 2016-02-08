package org.solcorp.etech

import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification
import org.solcorp.etech.utils.DateFormatUtils
import org.solcorp.etech.utils.MiscUtils
@Transactional
class EmployeeByActivityReportService {	
	
	def userService
	/**
	 * This method used to get Project List by search params
	 * @param params
	 * @return
	 */
	
	def getAllEmployeeByActivityReportList(params) {
		log.info  "getAllEmployeeByActivityReportList @ ActivityByEmployeeReportService Start"
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def reportTotal = []
		def projectTotalMap = [:]
		def projectByActByEmployeeMap = [:]
		def projectByEmployeeActivityMap = [:]
		def projectActivityTotalCount = 0
		
		def projectActualLaborDtlInstanceList = []
		
		if(params?.includeDetail) {
			projectActualLaborDtlInstanceList = getAllActivityDetailList(params, loggedInUserInstance)
			if(projectActualLaborDtlInstanceList.size() > 0) {
				
				def projectByActByEmployeeList = getGroupByProjectByActivityByEmployeeTotal(params, loggedInUserInstance)				
				projectByActByEmployeeList.each { act->
					projectByActByEmployeeMap.put(act[0]+"_"+act[1]+"_"+act[2], act)
				}
				
				projectByEmployeeActivityMap = getGroupByProjectByEmployeeTotal(params, loggedInUserInstance)
				projectTotalMap = getAllGroupByProjectTotal(params, loggedInUserInstance)
				reportTotal = getReportTotal(params, loggedInUserInstance)
			}			
			projectActivityTotalCount = projectActualLaborDtlInstanceList.totalCount
		} else {
						
			projectActualLaborDtlInstanceList = getGroupByProjectByActivityByEmployeeTotal(params, loggedInUserInstance)
			/*projectActualLaborDtlInstanceList.each { emp->
				projectByActByEmployeeMap.put(emp[0]+"_"+emp[1]+"_"+emp[2], emp)
			}*/
			
			projectActivityTotalCount = getGroupByProjectByEmployeeCount(params, loggedInUserInstance)
			
			if(projectActualLaborDtlInstanceList.size() > 0) {
				projectByEmployeeActivityMap = getGroupByProjectByEmployeeTotal(params, loggedInUserInstance)
				projectTotalMap = getAllGroupByProjectTotal(params, loggedInUserInstance)
				reportTotal = getReportTotal(params, loggedInUserInstance)
			}
		}
		log.info "Database projectActualLaborDtlInstanceList:::::::"+projectActualLaborDtlInstanceList.size()
		log.info "projectActivityTotalCount:::::::"+projectActivityTotalCount
		
		log.info  "getAllEmployeeByActivityReportList @ ActivityByEmployeeReportService End"
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), params: params, projectActivityTotalCount: projectActivityTotalCount, projectActualLaborDtlInstanceList: projectActualLaborDtlInstanceList, projectTotalMap: projectTotalMap, projectByActByEmployeeMap: projectByActByEmployeeMap, projectByEmployeeActivityMap: projectByEmployeeActivityMap, reportTotal:reportTotal]
	}
	
	private getAllActivityDetailList(params, loggedInUserInstance ) {
		log.info  "getAllActivityDetailList @ ActivityByEmployeeReportService Star"
		def projectActualLaborDtlCriteria = ProjectActualLaborDetail.createCriteria()
		def projectActualLaborDtlInstanceList = projectActualLaborDtlCriteria.list(max: params?.max?: Constants.DEFAULT_REPORT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("hhProjectId","asc")
			order("emp.code","asc")
			order("laborActivityCode.code","asc")
		}
		log.info  "getAllActivityDetailList @ ActivityByEmployeeReportService End"
		return projectActualLaborDtlInstanceList
	}
	private getAllGroupByProjectTotal(params, loggedInUserInstance) {
		log.info  "getAllGroupByProjectTotal @ ActivityByEmployeeReportService Start"
		def orderByProjectCriteria = ProjectActualLaborDetail.createCriteria()
		def projectTotalList = orderByProjectCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				projections{
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
			order("hhProjectId","asc")
		}		
		def projectTotalMap = [:]
		projectTotalList.each{ groupByProject ->
			projectTotalMap.put(groupByProject[0], groupByProject)
		}
		log.info  "getAllGroupByProjectTotal @ ActivityByEmployeeReportService End"
		return projectTotalMap
	}
	private getGroupByProjectByActivityByEmployeeTotal(params, loggedInUserInstance) {
		log.info  "getGroupByProjectByActivityByEmployeeTotal @ ActivityByEmployeeReportService Start"
		
		def orderByProjectByActivityByEmployeeCriteria = ProjectActualLaborDetail.createCriteria()
		def listParams = [:]
		if(!params?.includeDetail) {
		 listParams = ["max": params?.max , "offset": params?.offset?:0]
		}		
		def orderByProjectByActByEmployeeList = orderByProjectByActivityByEmployeeCriteria.list(listParams) {			
	
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
							
				projections{
					groupProperty("project.code") //0					
					groupProperty("laborActivityCode.code") //1
					groupProperty("emp.code") //2
					sum("hours")//3					
					sum("standardCost") //4
					sum("standardOverheadCost") //5
					sum("standardTotalCost") //6
					sum("actualCost") //7
					groupProperty("customer.code")//8		
					groupProperty("project.name")//9
					groupProperty("emp.firstName")
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code", "asc")
			order("emp.firstName", "asc")
			order("laborActivityCode.code", "asc")
		}
		log.info  "getGroupByProjectByActivityByEmployeeTotal @ ActivityByEmployeeReportService End"
		return orderByProjectByActByEmployeeList
	}
	private getGroupByProjectByEmployeeCount(params, loggedInUserInstance) {
		log.info  "getGroupByProjectByEmployeeCount @ ActivityByEmployeeReportService Start"
		def orderByProjectByEmployeeCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByEmployeeList = orderByProjectByEmployeeCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("project.code") //0					
					groupProperty("laborActivityCode.code") //1
					groupProperty("emp.code") //2
					sum("hours")//3					
					sum("standardCost") //4
					sum("standardOverheadCost") //5
					sum("standardTotalCost") //6
					sum("actualCost") //7					 
					groupProperty("project.name")//8
					groupProperty("emp.firstName")
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code", "asc")
			order("emp.firstName", "asc")
			order("laborActivityCode.code", "asc")
		}
		log.info  "getGroupByProjectByEmployeeCount @ ActivityByEmployeeReportService End"
		return orderByProjectByEmployeeList.size()
	}
	private getGroupByProjectByEmployeeTotal(params, loggedInUserInstance) {
		log.info  "getGroupByProjectByEmployeeTotal @ ActivityByEmployeeReportService Start"
		def orderByProjectByActvityCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByActivityList = orderByProjectByActvityCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("project.code") //0
					groupProperty("emp.code") //1
					sum("hours")//2
					sum("standardCost") //3
					sum("standardOverheadCost") //4
					sum("standardTotalCost") //5
					sum("actualCost") //6
					groupProperty("emp.firstName")//7
					groupProperty("emp.lastName")//8
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code", "asc")
			order("emp.firstName", "asc")
		}
		
		def projectByEmployeeActivityMap = [:]
		orderByProjectByActivityList.each { act->
			projectByEmployeeActivityMap.put(act[0]+"_"+act[1], act)
		}
		log.info  "getGroupByProjectByEmployeeTotal @ ActivityByEmployeeReportService End"
		return projectByEmployeeActivityMap
	}
	private getReportTotal(params, loggedInUserInstance) {
		log.info  "getReportTotal @ ActivityByEmployeeReportService Start"
		def orderByProjectByActvityCriteria = ProjectActualLaborDetail.createCriteria()
		def reportTotal = orderByProjectByActvityCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
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
			//order("project.code", "asc")
			//order("laborActivityCode.code", "asc")
		}
		log.info  "getReportTotal @ ActivityByEmployeeReportService End"
		return reportTotal
	}
	
	def getEmployeeByActivityListForExport(params) {
		log.info  "getEmployeeByActivityListForExport @ ReportService Start"
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		def projectActualLaborDtlCriteria = ProjectActualLaborDetail.createCriteria()
		def projectActualLaborDtlInstanceList = projectActualLaborDtlCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("project.code","asc")
			order("emp.firstName","asc")
			order("laborActivityCode.code","asc")
		}
		log.info "projectActualLaborDtlInstanceList"+projectActualLaborDtlInstanceList.size()
		
		//Group By Project
		def employeeByActivityReportList = []
		def projectGroupList = projectActualLaborDtlInstanceList.groupBy{actualLabor -> actualLabor?.project}
		log.info "Employee By Activity Project List ::::::::::"+projectGroupList.size()
		
		def reportHoursTotal = 0.00
		def reportStandardCost = 0.00
		def reportStandardOverheadCost = 0.00
		def reportStandardTotalCost = 0.00
		def reportActualCost = 0.00
				
		projectGroupList.each {  projectKey, employeeList ->
			def employeeByActivityReportDTO = new EmployeeByActivityReportDTO()
			employeeByActivityReportDTO.projectCode = projectKey?.code
			employeeByActivityReportDTO.projectName = projectKey?.name
			def groupEmpList = employeeList.groupBy { emp -> emp?.employee }
			
			def groupByEmployeeList = []
			
			def projectHours = 0.00
			def projectStandardCost = 0.00
			def projectStandardOverheadCost = 0.00
			def projectStandardTotalCost = 0.00
			def projectActualCost = 0.00
			
			groupEmpList.each { empInstance, activityList ->
				def employeeByProjectEADTO =new EmployeeByProjectEADTO()
				employeeByProjectEADTO.employeeName = empInstance?.getEmployeeName()
				employeeByProjectEADTO.employeeCode = empInstance?.code
				
				def grpActivityList = activityList.groupBy {a1 ->a1?.laborActivityCode}
				def employeeHours = 0.00
				def employeeStandardCost = 0.00
				def employeeStandardOverheadCost = 0.00
				def employeeStandardTotalCost = 0.00
				def employeeActualCost = 0.00
				
				def groupByActivityList = []
				grpActivityList.each{ actvInstance, employeeDetailInstList ->
					def activityByEmployeeEADTO = new ActivityByEmployeeEADTO()
					activityByEmployeeEADTO.activityName= actvInstance?.code
					def operationHours = 0.00
					def operationStandardCost = 0.00
					def operationStandardOverheadCost = 0.00
					def operationStandardTotalCost = 0.00
					def operationActualCost = 0.00
					def employeeDetailList = []
					
					employeeDetailInstList.each { laborInstance ->
						
						if(params?.includeDetail) {
							def employeeDetailEADTO = new EmployeeDetailEADTO(laborInstance?.employee?.code, laborInstance?.laborActivityCode?.code, laborInstance?.transactionDate, laborInstance?.hours, laborInstance?.standardRate, laborInstance?.standardCost, laborInstance?.standardOverheadCost, laborInstance?.standardTotalCost, laborInstance?.actualRate, laborInstance?.actualCost)
							employeeDetailList.add(employeeDetailEADTO)
						}
						operationHours = operationHours.add(MiscUtils.removePrecision(laborInstance?.hours ?:0.00))
						operationStandardCost = operationStandardCost.add(MiscUtils.removePrecision(laborInstance?.standardCost ?:0.00))
						operationStandardOverheadCost = operationStandardOverheadCost.add(MiscUtils.removePrecision(laborInstance?.standardOverheadCost ?:0.00))
						operationStandardTotalCost = operationStandardTotalCost.add(MiscUtils.removePrecision(laborInstance?.standardTotalCost ?:0.00))
						operationActualCost =  operationActualCost.add(MiscUtils.removePrecision(laborInstance?.actualCost ?:0.00))
					}
					activityByEmployeeEADTO.hours = operationHours
					activityByEmployeeEADTO.standardCost = operationStandardCost
					activityByEmployeeEADTO.standardOverheadCost = operationStandardOverheadCost
					activityByEmployeeEADTO.standardTotalCost = operationStandardTotalCost
					activityByEmployeeEADTO.actualCost = operationActualCost
					
					employeeHours = employeeHours.add(operationHours)
					employeeStandardCost = employeeStandardCost.add(operationStandardCost)
					employeeStandardOverheadCost = employeeStandardOverheadCost.add(operationStandardOverheadCost)
					employeeStandardTotalCost = employeeStandardTotalCost.add(operationStandardTotalCost)
					employeeActualCost = employeeActualCost.add(operationActualCost)
					
					activityByEmployeeEADTO.employeeDetailList = employeeDetailList
					groupByActivityList.add(activityByEmployeeEADTO)
				}
				
				employeeByProjectEADTO.hours = employeeHours
				employeeByProjectEADTO.standardCost = employeeStandardCost
				employeeByProjectEADTO.standardOverheadCost = employeeStandardOverheadCost
				employeeByProjectEADTO.standardTotalCost = employeeStandardTotalCost
				employeeByProjectEADTO.actualCost = employeeActualCost
				
				projectHours = projectHours.add(employeeHours)
				projectStandardCost = projectStandardCost.add(employeeStandardCost)
				projectStandardOverheadCost = projectStandardOverheadCost.add(employeeStandardOverheadCost)
				projectStandardTotalCost = projectStandardTotalCost.add(employeeStandardTotalCost)
				projectActualCost = projectActualCost.add(employeeActualCost)
				
				employeeByProjectEADTO.groupByActivityList = groupByActivityList
				groupByEmployeeList.add(employeeByProjectEADTO)
			}
			
			employeeByActivityReportDTO.projectHours = projectHours
			employeeByActivityReportDTO.projectStandardCost = projectStandardCost
			employeeByActivityReportDTO.projectStandardOverheadCost = projectStandardOverheadCost
			employeeByActivityReportDTO.projectStandardTotalCost = projectStandardTotalCost
			employeeByActivityReportDTO.projectActualCost = projectActualCost
			
			reportHoursTotal = reportHoursTotal.add(projectHours)
			reportStandardCost = reportStandardCost.add(projectStandardCost)
			reportStandardOverheadCost = reportStandardOverheadCost.add(projectStandardOverheadCost)
			reportStandardTotalCost = reportStandardTotalCost.add(projectStandardTotalCost)
			reportActualCost = reportActualCost.add(projectActualCost)
			
			employeeByActivityReportDTO.groupByEmployeeList = groupByEmployeeList
			employeeByActivityReportList.add(employeeByActivityReportDTO)
		}
		
		def reportTotalMap = [:]
			
		reportTotalMap.put("reportHoursTotal", reportHoursTotal)
		reportTotalMap.put("reportStandardCost", reportStandardCost)
		reportTotalMap.put("reportStandardOverheadCost", reportStandardOverheadCost)
		reportTotalMap.put("reportStandardTotalCost", reportStandardTotalCost)
		reportTotalMap.put("reportActualCost", reportActualCost)
		log.info "employeeByActivityReportList size "+employeeByActivityReportList.size()
		log.info  "getEmployeeByActivityListForExport @ ReportService End"
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), employeeByActivityReportList: employeeByActivityReportList, reportTotalMap: reportTotalMap, params: params]
	}
	
	
	
	def sortCriteriaClosure = { params, loggedInUserInstance ->
		log.info "sortCriteriaClosure @ ActivityByEmployeeReportService Start"
		
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
			createAlias("laborActivityCode.laborActivityGroup", "lbrGrp")
			ilike("lbrGrp.code", "%" + params?.laborActGroupCode +"%")
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
