package org.solcorp.etech
import org.solcorp.etech.utils.MiscUtils
import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification
import org.solcorp.etech.reports.employeeByActivityByProject.ActivityGrpByEmployeeDTO
import org.solcorp.etech.reports.employeeByActivityByProject.EmployeeByActivityByProjectReportDTO
import org.solcorp.etech.reports.employeeByActivityByProject.ProjectGrpByActivityDTO
import org.solcorp.etech.utils.DateFormatUtils
@Transactional
class ActivityByProjectReportService {	
	
	def userService
	/**
	 * This method used to get Project List by search params
	 * @param params
	 * @return
	 */
	
	def getAllActivityByProjectReportList(params) {
		log.info  "getAllActivityByProjectReportList @ ActivityByProjectReportService Start"
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def reportTotal = []
		def employeeTotalMap = [:]
		def employeeByActByProjectMap = [:]
		def employeeByActivityMap = [:]
		def projectActivityTotalCount = 0
		
		def projectActualLaborDtlInstanceList = []
		
		if(params?.includeDetail) {
			projectActualLaborDtlInstanceList = getAllActivityDetailList(params, loggedInUserInstance)
			if(projectActualLaborDtlInstanceList.size() > 0) {
				
				def employeeByActByProjectList = getGroupByEmployeeByProjectByActivityList(params, loggedInUserInstance)				
				employeeByActByProjectList.each { act->
					//Employee[0]  Project[1] _Activity[2] 
					employeeByActByProjectMap.put(act[0]+"_"+act[1]+"_"+act[2], act)
				}
				
				employeeByActivityMap = getGroupByEmployeeByActivityList(params, loggedInUserInstance)
				employeeTotalMap = getAllGroupByEmployeeTotal(params, loggedInUserInstance)
				reportTotal = getReportTotal(params, loggedInUserInstance)
			}			
			projectActivityTotalCount = projectActualLaborDtlInstanceList.totalCount
		} else {
						
			projectActualLaborDtlInstanceList = getGroupByEmployeeByProjectByActivityList(params, loggedInUserInstance)
						
			projectActivityTotalCount = getGroupByProjectByEmployeeCount(params, loggedInUserInstance)
			
			if(projectActualLaborDtlInstanceList.size() > 0) {
				employeeByActivityMap = getGroupByEmployeeByActivityList(params, loggedInUserInstance)
				employeeTotalMap = getAllGroupByEmployeeTotal(params, loggedInUserInstance)
				reportTotal = getReportTotal(params, loggedInUserInstance)
			}
		}
		log.info "Database projectActualLaborDtlInstanceList:::::::"+projectActualLaborDtlInstanceList.size()
		log.info "projectActivityTotalCount:::::::"+projectActivityTotalCount
		
		log.info  "getAllActivityByProjectReportList @ ActivityByProjectReportService End"
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), params: params, projectActivityTotalCount: projectActivityTotalCount, projectActualLaborDtlInstanceList: projectActualLaborDtlInstanceList, employeeTotalMap: employeeTotalMap, employeeByActByProjectMap: employeeByActByProjectMap, employeeByActivityMap: employeeByActivityMap, reportTotal:reportTotal]
	}
	
	private getAllActivityDetailList(params, loggedInUserInstance ) {
		log.info  "getAllActivityDetailList @ ActivityByProjectReportService Star"
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
			order("emp.code","asc")
			order("laborActivityCode.code","asc")
			order("project.code","asc")
		}
		log.info  "getAllActivityDetailList @ ActivityByProjectReportService End"
		return projectActualLaborDtlInstanceList
	}
	private getAllGroupByEmployeeTotal(params, loggedInUserInstance) {
		log.info  "getAllGroupByEmployeeTotal @ ActivityByProjectReportService Start"
		def orderByProjectCriteria = ProjectActualLaborDetail.createCriteria()
		def projectTotalList = orderByProjectCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("emp.code") //0
					sum("hours")//1
					sum("standardCost") //2
					sum("standardOverheadCost") //3
					sum("standardTotalCost") //4
					sum("actualCost") //5
				}
								
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("emp.code","asc")
		}
		
		def employeeTotalMap = [:]
		projectTotalList.each{ groupByProject ->
			employeeTotalMap.put(groupByProject[0], groupByProject)
		}
		log.info  "getAllGroupByEmployeeTotal @ ActivityByProjectReportService End"
		return employeeTotalMap
	}
	private getGroupByEmployeeByProjectByActivityList(params, loggedInUserInstance) {
		log.info  "getGroupByEmployeeByProjectByActivityList @ ActivityByProjectReportService Start"
		
		def orderByProjectByActivityByEmployeeCriteria = ProjectActualLaborDetail.createCriteria()
		def listParams = [:]
		if(!params?.includeDetail) {
		 listParams = ["max": params?.max , "offset": params?.offset?:0]
		}		
		def orderByProjectByActByEmployeeList = orderByProjectByActivityByEmployeeCriteria.list(listParams) {			
	
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("emp.code") //0
					groupProperty("project.code") //1					
					groupProperty("laborActivityCode.code") //2
					sum("hours")//3
					sum("standardCost") //4
					sum("standardOverheadCost") //5
					sum("standardTotalCost") //6
					sum("actualCost") //7					
					groupProperty("project.name")//8
					groupProperty("emp.firstName")//9
					groupProperty("emp.lastName")//10					
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("emp.code", "asc")
			order("project.code", "asc")
			order("laborActivityCode.code", "asc")
		}
		log.info  "getGroupByEmployeeByProjectByActivityList @ ActivityByProjectReportService End"
		return orderByProjectByActByEmployeeList
	}
	private getGroupByProjectByEmployeeCount(params, loggedInUserInstance) {
		log.info  "getGroupByProjectByEmployeeCount @ ActivityByProjectReportService Start"
		def orderByProjectByEmployeeCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByEmployeeList = orderByProjectByEmployeeCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("emp.code") //0
					groupProperty("laborActivityCode.code") //1
					groupProperty("project.code") //2					
					sum("hours")//3
					sum("standardOverheadCost") //4
					sum("standardTotalCost") //5
					sum("actualCost") //6					
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("emp.code", "asc")
			order("project.code", "asc")
			order("laborActivityCode.code", "asc")
		}
		log.info  "getGroupByProjectByEmployeeCount @ ActivityByProjectReportService End"
		return orderByProjectByEmployeeList.size()
	}
	private getGroupByEmployeeByActivityList(params, loggedInUserInstance) {
		log.info  "getGroupByEmployeeByActivityList @ ActivityByProjectReportService Start"
		def orderByProjectByActvityCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByActivityList = orderByProjectByActvityCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("emp.code") //0
					groupProperty("laborActivityCode.code") //1
					sum("hours")//2
					sum("standardCost") //3
					sum("standardOverheadCost") //4
					sum("standardTotalCost") //5
					sum("actualCost") //6					
					
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("emp.code", "asc")
			order("laborActivityCode.code", "asc")
		}
		
		def employeeByProjectActivityMap = [:]
		orderByProjectByActivityList.each { act->
			employeeByProjectActivityMap.put(act[0]+"_"+act[1], act)
		}
		log.info  "getGroupByEmployeeByActivityList @ ActivityByProjectReportService End"
		return employeeByProjectActivityMap
	}
	private getReportTotal(params, loggedInUserInstance) {
		log.info  "getReportTotal @ ActivityByProjectReportService Start"
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
		log.info  "getReportTotal @ ActivityByProjectReportService End"
		return reportTotal
	}

	def getActivityByProjectListForExport(params) {
		log.info  "getActivityByProjectListForExport @ ActivityByProjectReportService Start"
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
			order("emp.code","asc")
			order("laborActivityCode.code","asc")
			order("project.code","asc")
		}
		log.info "projectActualLaborDtlInstanceList"+projectActualLaborDtlInstanceList.size()
		
		//Group By Employee
		def employeeByActivityByProjectReportList = []
		def employeeGroupList = projectActualLaborDtlInstanceList.groupBy{actualLabor -> actualLabor?.employee}
		log.info "Employee By Activity By Project List ::::::::::"+employeeGroupList.size()
		
		def reportHoursTotal = 0.00
		def reportStandardCost = 0.00
		def reportStandardOverheadCost = 0.00
		def reportStandardTotalCost = 0.00
		def reportActualCost = 0.00
				
		employeeGroupList.each {  empInstance, activityGrpList ->
			def employeeByActivityByProjectReportDTO = new EmployeeByActivityByProjectReportDTO()
			employeeByActivityByProjectReportDTO.employeeCode = empInstance?.code
			employeeByActivityByProjectReportDTO.employeeName = empInstance?.getEmployeeName()
			employeeByActivityByProjectReportDTO.supervisorCode = empInstance?.supervisor?.code
			def groupActList = activityGrpList.groupBy { act -> act?.laborActivityCode }
			
			def activityHours = 0.00
			def activityStandardCost = 0.00
			def activityStandardOverheadCost = 0.00
			def activityStandardTotalCost = 0.00
			def activityActualCost = 0.00
			def groupByActivityList = []
			
			groupActList.each { activityInstance, projectGrpList ->
				def activityGrpByEmployeeDTO =new ActivityGrpByEmployeeDTO()
				activityGrpByEmployeeDTO.activityName = activityInstance?.code
				def grpProjectList = projectGrpList.groupBy {prj ->prj?.project}
				
				def projectHours = 0.00
				def projectStandardCost = 0.00
				def projectStandardOverheadCost = 0.00
				def projectStandardTotalCost = 0.00
				def projectActualCost = 0.00
				def groupByProjectList = []
				
				grpProjectList.each{ projectInstance, activityDetailInstanceList ->
					def projectGrpByActivityDTO = new ProjectGrpByActivityDTO()
					projectGrpByActivityDTO.projectCode= projectInstance?.code
					projectGrpByActivityDTO.projectName= projectInstance?.name
					
					def hours = 0.00
					def standardCost = 0.00
					def standardOverheadCost = 0.00
					def standardTotalCost = 0.00
					def actualCost = 0.00
					def activityDetailList = []
					
					activityDetailInstanceList.each { activityDetailInstance ->
						if(params?.includeDetail) {
							def activityDetailDTO = new org.solcorp.etech.reports.employeeByActivityByProject.ActivityDetailDTO(activityDetailInstance?.laborActivityCode?.code, activityDetailInstance?.project?.code, activityDetailInstance?.transactionDate, activityDetailInstance?.hours, activityDetailInstance?.standardRate, activityDetailInstance?.standardCost, activityDetailInstance?.standardOverheadCost, activityDetailInstance?.standardTotalCost, activityDetailInstance?.actualRate, activityDetailInstance?.actualCost)
							activityDetailList.add(activityDetailDTO)
						}
						hours = hours.add(MiscUtils.removePrecision(activityDetailInstance?.hours ?:0.00))
						standardCost = standardCost.add(MiscUtils.removePrecision(activityDetailInstance?.standardCost ?:0.00))
						standardOverheadCost = standardOverheadCost.add(MiscUtils.removePrecision(activityDetailInstance?.standardOverheadCost ?:0.00))
						standardTotalCost = standardTotalCost.add(MiscUtils.removePrecision(activityDetailInstance?.standardTotalCost ?:0.00))
						actualCost =  actualCost.add(MiscUtils.removePrecision(activityDetailInstance?.actualCost ?:0.00))
					}
					projectGrpByActivityDTO.projectHours = hours
					projectGrpByActivityDTO.projectStandardCost = standardCost
					projectGrpByActivityDTO.projectStandardOverheadCost = standardOverheadCost
					projectGrpByActivityDTO.projectStandardTotalCost = standardTotalCost
					projectGrpByActivityDTO.projectActualCost = actualCost
					
					projectHours = projectHours.add(hours)
					projectStandardCost = projectStandardCost.add(standardCost)
					projectStandardOverheadCost = projectStandardOverheadCost.add(standardOverheadCost)
					projectStandardTotalCost = projectStandardTotalCost.add(standardTotalCost)
					projectActualCost = projectActualCost.add(actualCost)
					
					projectGrpByActivityDTO.activityDetailList = activityDetailList
					groupByProjectList.add(projectGrpByActivityDTO)
				}
				
				activityGrpByEmployeeDTO.activityHours = projectHours
				activityGrpByEmployeeDTO.activityStandardCost = projectStandardCost
				activityGrpByEmployeeDTO.activityStandardOverheadCost = projectStandardOverheadCost
				activityGrpByEmployeeDTO.activityStandardTotalCost = projectStandardTotalCost
				activityGrpByEmployeeDTO.activityActualCost = projectActualCost
				
				activityHours = activityHours.add(projectHours)
				activityStandardCost = activityStandardCost.add(projectStandardCost)
				activityStandardOverheadCost = activityStandardOverheadCost.add(projectStandardOverheadCost)
				activityStandardTotalCost = activityStandardTotalCost.add(projectStandardTotalCost)
				activityActualCost = activityActualCost.add(projectActualCost)
				
				activityGrpByEmployeeDTO.groupByProjectList = groupByProjectList
				groupByActivityList.add(activityGrpByEmployeeDTO)
			}
			
			employeeByActivityByProjectReportDTO.reportHours = activityHours
			employeeByActivityByProjectReportDTO.reportStandardCost = activityStandardCost
			employeeByActivityByProjectReportDTO.reportStandardOverheadCost = activityStandardOverheadCost
			employeeByActivityByProjectReportDTO.reportStandardTotalCost = activityStandardTotalCost
			employeeByActivityByProjectReportDTO.reportActualCost = activityActualCost
			
			reportHoursTotal = reportHoursTotal.add(activityHours)
			reportStandardCost = reportStandardCost.add(activityStandardCost)
			reportStandardOverheadCost = reportStandardOverheadCost.add(activityStandardOverheadCost)
			reportStandardTotalCost = reportStandardTotalCost.add(activityStandardTotalCost)
			reportActualCost = reportActualCost.add(activityActualCost)
			
			employeeByActivityByProjectReportDTO.groupByActivityList = groupByActivityList
			employeeByActivityByProjectReportList.add(employeeByActivityByProjectReportDTO)
		}
		def reportTotalMap = [:]
			
		reportTotalMap.put("reportHoursTotal", reportHoursTotal)
		reportTotalMap.put("reportStandardCost", reportStandardCost)
		reportTotalMap.put("reportStandardOverheadCost", reportStandardOverheadCost)
		reportTotalMap.put("reportStandardTotalCost", reportStandardTotalCost)
		reportTotalMap.put("reportActualCost", reportActualCost)
		
		log.info "employeeByActivityByProjectReportList size "+employeeByActivityByProjectReportList.size()
		log.info  "getActivityByProjectListForExport @ ActivityByProjectReportService End"
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), employeeByActivityByProjectReportList: employeeByActivityByProjectReportList, reportTotalMap: reportTotalMap, params: params]
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
