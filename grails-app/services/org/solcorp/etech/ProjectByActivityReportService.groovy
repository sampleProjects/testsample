package org.solcorp.etech
import org.solcorp.etech.utils.MiscUtils
import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification
import org.solcorp.etech.reports.employeeByProjectByActivity.ActivityGrpByProjectDTO
import org.solcorp.etech.reports.employeeByProjectByActivity.EmployeeByProjectByActivityReportDTO
import org.solcorp.etech.reports.employeeByProjectByActivity.ProjectDetailDTO
import org.solcorp.etech.reports.employeeByProjectByActivity.ProjectGrpByEmployeeDTO
import org.solcorp.etech.utils.DateFormatUtils
@Transactional
class ProjectByActivityReportService {	
	
	def userService
	/**
	 * This method used to get Project List by search params
	 * @param params
	 * @return
	 */
	
	def getAllProjectByActivityReportList(params) {
		log.info  "getAllProjectByActivityReportList @ ProjectByActivityReportService Start"
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def reportTotal = []
		def employeeTotalMap = [:]
		def employeeByActByProjectMap = [:]
		def employeeByProjectActivityMap = [:]
		def projectActivityTotalCount = 0
		
		def projectActualLaborDtlInstanceList = []
		
		if(params?.includeDetail) {
			projectActualLaborDtlInstanceList = getAllActivityDetailList(params, loggedInUserInstance)
			if(projectActualLaborDtlInstanceList.size() > 0) {
				
				def employeeByActByProjectList = getGroupByEmployeeByActivityByProjectTotal(params, loggedInUserInstance)				
				employeeByActByProjectList.each { act->
					//Employee[0] _Activity[1] _ Project[2]
					employeeByActByProjectMap.put(act[0]+"_"+act[1]+"_"+act[2], act)
				}
				
				employeeByProjectActivityMap = getGroupByEmployeeByProjectTotal(params, loggedInUserInstance)
				employeeTotalMap = getAllGroupByEmployeeTotal(params, loggedInUserInstance)
				reportTotal = getReportTotal(params, loggedInUserInstance)
			}			
			projectActivityTotalCount = projectActualLaborDtlInstanceList.totalCount
		} else {
						
			projectActualLaborDtlInstanceList = getGroupByEmployeeByActivityByProjectTotal(params, loggedInUserInstance)
			/*projectActualLaborDtlInstanceList.each { emp->
				employeeByActByProjectMap.put(emp[0]+"_"+emp[1]+"_"+emp[2], emp)
			}*/
			
			projectActivityTotalCount = getGroupByProjectByEmployeeCount(params, loggedInUserInstance)
			
			if(projectActualLaborDtlInstanceList.size() > 0) {
				employeeByProjectActivityMap = getGroupByEmployeeByProjectTotal(params, loggedInUserInstance)
				employeeTotalMap = getAllGroupByEmployeeTotal(params, loggedInUserInstance)
				reportTotal = getReportTotal(params, loggedInUserInstance)
			}
		}
		log.info "Database projectActualLaborDtlInstanceList:::::::"+projectActualLaborDtlInstanceList.size()
		log.info "projectActivityTotalCount:::::::"+projectActivityTotalCount
		
		log.info  "getAllEmployeeByActivityReportList @ ActivityByEmployeeReportService End"
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), params: params, projectActivityTotalCount: projectActivityTotalCount, projectActualLaborDtlInstanceList: projectActualLaborDtlInstanceList, employeeTotalMap: employeeTotalMap, employeeByActByProjectMap: employeeByActByProjectMap, employeeByProjectActivityMap: employeeByProjectActivityMap, reportTotal:reportTotal]
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
			order("emp.code","asc")
			order("project.code","asc")
			order("laborActivityCode.code","asc")
		}
		log.info  "getAllActivityDetailList @ ActivityByEmployeeReportService End"
		return projectActualLaborDtlInstanceList
	}
	private getAllGroupByEmployeeTotal(params, loggedInUserInstance) {
		log.info  "getAllGroupByEmployeeTotal @ ActivityByEmployeeReportService Start"
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
		log.info  "getAllGroupByEmployeeTotal @ ActivityByEmployeeReportService End"
		return employeeTotalMap
	}
	private getGroupByEmployeeByActivityByProjectTotal(params, loggedInUserInstance) {
		log.info  "getGroupByEmployeeByActivityByProjectTotal @ ActivityByEmployeeReportService Start"
		
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
					groupProperty("laborActivityCode.code") //1
					groupProperty("project.code") //2					
					sum("hours")//3
					sum("standardCost") //4
					sum("standardOverheadCost") //5
					sum("standardTotalCost") //6
					sum("actualCost") //7		
					groupProperty("emp.firstName")//8
					groupProperty("emp.lastName")//9
				}
				sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("emp.code", "asc")
			order("project.code", "asc")
			order("laborActivityCode.code", "asc")
		}
		log.info  "getGroupByEmployeeByActivityByProjectTotal @ ActivityByEmployeeReportService End"
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
		log.info  "getGroupByProjectByEmployeeCount @ ActivityByEmployeeReportService End"
		return orderByProjectByEmployeeList.size()
	}
	private getGroupByEmployeeByProjectTotal(params, loggedInUserInstance) {
		log.info  "getGroupByEmployeeByProjectTotal @ ActivityByEmployeeReportService Start"
		def orderByProjectByActvityCriteria = ProjectActualLaborDetail.createCriteria()
		def orderByProjectByActivityList = orderByProjectByActvityCriteria.list() {
			and {
				createAlias("project", "project", CriteriaSpecification.LEFT_JOIN)
				createAlias("project.customer", "customer", CriteriaSpecification.LEFT_JOIN)
				createAlias("employee", "emp", CriteriaSpecification.LEFT_JOIN)
				createAlias("laborActivityCode", "laborActivityCode", CriteriaSpecification.LEFT_JOIN)
				
				projections{
					groupProperty("emp.code") //0
					groupProperty("project.code") //1
					sum("hours")//2
					sum("standardCost") //3
					sum("standardOverheadCost") //4
					sum("standardTotalCost") //5
					sum("actualCost") //6
					groupProperty("project.name") //1
					
				}
			sortCriteriaClosure.delegate = delegate
				sortCriteriaClosure(params, loggedInUserInstance)
			}
			order("emp.code", "asc")
			order("project.name", "asc")
		}
		
		def employeeByProjectActivityMap = [:]
		orderByProjectByActivityList.each { act->
			employeeByProjectActivityMap.put(act[0]+"_"+act[1], act)
		}
		log.info  "getGroupByEmployeeByProjectTotal @ ActivityByEmployeeReportService End"
		return employeeByProjectActivityMap
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
		}
		log.info  "getReportTotal @ ActivityByEmployeeReportService End"
		return reportTotal
	}
	
	def getProjectByActivityListForExport(params) {
		log.info  "getProjectByActivityListForExport @ ActivityByEmployeeReportService Start"
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
			order("project.code","asc")
			order("laborActivityCode.code","asc")
		}
		log.info "projectActualLaborDtlInstanceList"+projectActualLaborDtlInstanceList.size()
		
		//Group By Employee
		def employeeByProjectReportList = []
		def employeeGroupList = projectActualLaborDtlInstanceList.groupBy{actualLabor -> actualLabor?.employee}
		log.info "Employee By Project By Activity List ::::::::::"+employeeGroupList.size()
		
		def reportHoursTotal = 0.00
		def reportStandardCost = 0.00
		def reportStandardOverheadCost = 0.00
		def reportStandardTotalCost = 0.00
		def reportActualCost = 0.00
				
		employeeGroupList.each {  empInstance, projectGrpList ->
			def employeeByProjectByActivityReportDTO = new EmployeeByProjectByActivityReportDTO()
			employeeByProjectByActivityReportDTO.employeeCode = empInstance?.code
			employeeByProjectByActivityReportDTO.employeeName = empInstance?.getEmployeeName()
			employeeByProjectByActivityReportDTO.supervisorCode = empInstance?.supervisor?.code
			def groupPrjList = projectGrpList.groupBy { prj -> prj?.project }
			
			def groupByProjectList = []
			
			def projectHours = 0.00
			def projectStandardCost = 0.00
			def projectStandardOverheadCost = 0.00
			def projectStandardTotalCost = 0.00
			def projectActualCost = 0.00
			
			groupPrjList.each { prjInstance, activityGrpList ->
				def projectGrpByEmployeeDTO =new ProjectGrpByEmployeeDTO()
				projectGrpByEmployeeDTO.projectCode = prjInstance?.code
				projectGrpByEmployeeDTO.projectName = prjInstance?.name
				def grpActivityList = activityGrpList.groupBy {act ->act?.laborActivityCode}
				def activityHours = 0.00
				def activityStandardCost = 0.00
				def activityStandardOverheadCost = 0.00
				def activityStandardTotalCost = 0.00
				def activityActualCost = 0.00
				
				def groupByActivityList = []
				grpActivityList.each{ activityInstance, projectDetailInstanceList ->
					def activityGrpByProjectDTO = new ActivityGrpByProjectDTO()
					activityGrpByProjectDTO.activityName= activityInstance?.code
					def hours = 0.00
					def standardCost = 0.00
					def standardOverheadCost = 0.00
					def standardTotalCost = 0.00
					def actualCost = 0.00
					def projectDetailList = []
					
					projectDetailInstanceList.each { projectDetailInstance ->
						def projectDetailDTO = new ProjectDetailDTO(projectDetailInstance?.project?.code, projectDetailInstance?.laborActivityCode?.code, projectDetailInstance?.transactionDate, projectDetailInstance?.hours, projectDetailInstance?.standardRate, projectDetailInstance?.standardCost, projectDetailInstance?.standardOverheadCost, projectDetailInstance?.standardTotalCost, projectDetailInstance?.actualRate, projectDetailInstance?.actualCost)
						hours = hours.add(MiscUtils.removePrecision(projectDetailInstance?.hours ?:0.00))
						standardCost = standardCost.add(MiscUtils.removePrecision(projectDetailInstance?.standardCost ?:0.00))
						standardOverheadCost = standardOverheadCost.add(MiscUtils.removePrecision(projectDetailInstance?.standardOverheadCost ?:0.00))
						standardTotalCost = standardTotalCost.add(MiscUtils.removePrecision(projectDetailInstance?.standardTotalCost ?:0.00))
						actualCost =  actualCost.add(MiscUtils.removePrecision(projectDetailInstance?.actualCost ?:0.00))
						
						projectDetailList.add(projectDetailDTO)
					}
					activityGrpByProjectDTO.activityHours = hours
					activityGrpByProjectDTO.activityStandardCost = standardCost
					activityGrpByProjectDTO.activityStandardOverheadCost = standardOverheadCost
					activityGrpByProjectDTO.activityStandardTotalCost = standardTotalCost
					activityGrpByProjectDTO.activityActualCost = actualCost
					
					activityHours = activityHours.add(hours)
					activityStandardCost = activityStandardCost.add(standardCost)
					activityStandardOverheadCost = activityStandardOverheadCost.add(standardOverheadCost)
					activityStandardTotalCost = activityStandardTotalCost.add(standardTotalCost)
					activityActualCost = activityActualCost.add(actualCost)
					
					activityGrpByProjectDTO.projectDetailList = projectDetailList
					groupByActivityList.add(activityGrpByProjectDTO)
				}
				
				projectGrpByEmployeeDTO.projectHours = activityHours
				projectGrpByEmployeeDTO.projectStandardCost = activityStandardCost
				projectGrpByEmployeeDTO.projectStandardOverheadCost = activityStandardOverheadCost
				projectGrpByEmployeeDTO.projectStandardTotalCost = activityStandardTotalCost
				projectGrpByEmployeeDTO.projectActualCost = activityActualCost
				
				projectHours = projectHours.add(activityHours)
				projectStandardCost = projectStandardCost.add(activityStandardCost)
				projectStandardOverheadCost = projectStandardOverheadCost.add(activityStandardOverheadCost)
				projectStandardTotalCost = projectStandardTotalCost.add(activityStandardTotalCost)
				projectActualCost = projectActualCost.add(activityActualCost)
				
				projectGrpByEmployeeDTO.groupByActivityList = groupByActivityList
				groupByProjectList.add(projectGrpByEmployeeDTO)
			}
			
			employeeByProjectByActivityReportDTO.reportHours = projectHours
			employeeByProjectByActivityReportDTO.reportStandardCost = projectStandardCost
			employeeByProjectByActivityReportDTO.reportStandardOverheadCost = projectStandardOverheadCost
			employeeByProjectByActivityReportDTO.reportStandardTotalCost = projectStandardTotalCost
			employeeByProjectByActivityReportDTO.reportActualCost = projectActualCost
			
			reportHoursTotal = reportHoursTotal.add(projectHours)
			reportStandardCost = reportStandardCost.add(projectStandardCost)
			reportStandardOverheadCost = reportStandardOverheadCost.add(projectStandardOverheadCost)
			reportStandardTotalCost = reportStandardTotalCost.add(projectStandardTotalCost)
			reportActualCost = reportActualCost.add(projectActualCost)
			
			employeeByProjectByActivityReportDTO.groupByProjectList = groupByProjectList
			employeeByProjectReportList.add(employeeByProjectByActivityReportDTO)
		}
		
		def reportTotalMap = [:]
			 
		reportTotalMap.put("reportHoursTotal", reportHoursTotal)
		reportTotalMap.put("reportStandardCost", reportStandardCost)
		reportTotalMap.put("reportStandardOverheadCost", reportStandardOverheadCost)
		reportTotalMap.put("reportStandardTotalCost", reportStandardTotalCost)
		reportTotalMap.put("reportActualCost", reportActualCost)
		
		log.info "employeeByProjectReportList size "+employeeByProjectReportList.size()
		log.info  "getProjectByActivityListForExport @ ActivityByEmployeeReportService End"
		return ["currentDate": DateFormatUtils.getStringFromDate(new Date()), employeeByProjectReportList: employeeByProjectReportList, reportTotalMap: reportTotalMap, params: params]
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
