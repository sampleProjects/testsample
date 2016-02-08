package org.solcorp.etech
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReportController {

	def reportService
	def userService
	def projectService
	def excelExportService
	def activityByEmployeeReportService
	def employeeByActivityReportService
	def projectByActivityReportService
	def activityByProjectReportService
	def laborHistoryByCOEReportService
    def index() { }
	
	def clear() {
		redirect(action: "activityByEmployeeReport")		
	}
		
	def getProjectList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxProjectList" , model: reportService.getProjectList(params))
	}
	
	def getCustomerList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxCustomerList", model: reportService.getCustomerList(params))
	}
	def getCOEList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxCOEList", model: reportService.getCOEList(params))
	}
	def getLaborActivityCodeList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxLaborActivityCodeList", model: reportService.getLaborActivityCodeList(params))
	}
	def getPayDepartmentList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxPayDepartmentList", model: reportService.getPayDepartmentList(params))
	}
	//TODO
	def getLaborActivityGroupList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxLaborActivityGroupList", model: reportService.getLaborActivityGroupList(params))
	}
	def getAcctExecutiveList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxAcctExecutiveList", model: reportService.getAcctExecutiveList(params))
	}
	
	def getSupervisorList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxSupervisorList", model: reportService.getAllSupervisorList(params))
	}
	
	def getProjectManagerList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxProjectManagerList", model: reportService.getProjectManagerList(params))
	}
	
	def getEmployeeList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxEmployeeList", model: reportService.getEmployeeList(params))
	}
	
	def getExpenseList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "../common/searchDialog/ajaxExpenseList", model: reportService.getExpenseList(params))
	}
	
	/*Activity By Employee Start*/
	def activityByEmployeeReport() {
		def  loggedInUserInstance = userService.getLoggedInUser()		
		def userInstance=User.get(loggedInUserInstance?.id)
		render(view : "projectByActivityByEmployee/activityByEmployeeReport", model: [userInstance: userInstance])
	}
	
	def getAllActivityByEmployeeReportData() {		
		try {
			params.max =  Constants.DEFAULT_REPORT_PAGINATION_RECORDS			
			def modelMap = activityByEmployeeReportService.getAllActivityByEmployeeReportList(params)			
			render(view: "projectByActivityByEmployee/activityByEmployeeList", model: modelMap)						
			return
		} catch(Exception e) {
			log.error "getAllActivityByEmployeeReportData Exception "+e
		}
	}
	/*Activity By Employee End*/
	
	/*Employee By Activity Report related Actions Start*/
	def employeeByActivityReport() {
		def  loggedInUserInstance = userService.getLoggedInUser()
		def userInstance=User.get(loggedInUserInstance?.id)
		render(view : "projectByEmployeeByActivity/employeeByActivityReport", model: [userInstance: userInstance])
	}
	
	def getEmployeeByActivityReportData() {
		try {
			params.max =  Constants.DEFAULT_REPORT_PAGINATION_RECORDS
			render(view: "projectByEmployeeByActivity/employeeByActivityList", model: employeeByActivityReportService.getAllEmployeeByActivityReportList(params))
			return
		} catch(Exception e) {
			log.error "getAllActivityByEmployeeReportData Exception "+e
		}
	}
		
	/*Employee By Activity Report related Actions End*/
	
	/*Project Performance Actions Start*/
	/**
	 * This action used to render Project Performance View gsp
	 * @return
	 */
	def projectPerformanceReport() {		
		def  loggedInUserInstance = userService.getLoggedInUser()
		def userInstance=User.get(loggedInUserInstance?.id)
		render(view : "projectPerformanceReport", model: [userInstance: userInstance])
	}
	
	/**
	 * This action used to clear Project Performance search params 
	 * @return
	 */
	def projectPerClear() {
		redirect(action: "projectPerformanceReport")
	}
	
	/**
	 * This action used to get all Project List by report search parameters
	 * @return
	 */
	def getAllProjectList() {		
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		
		def modelMap = reportService.getAllProjectListByReportSearchParams(params)
		
		if(request.xhr) {			
			render(template:"projectListRecords", model: modelMap)
		} else {
			render(view : "projectList", model: modelMap)
		}		
	}
	
	/**
	 * This Action used to get Project Performance View Details
	 * @param id : Project Id 
	 * @return ProjectPerformanceViewDTO
	 */
	def getProjectPerformanceDetail() {	
		if(projectService.isValidProjectAccess(Long.valueOf(params?.id))) {
			render(view : "projectPerformanceViewDetails", model: [projectPerfViewDTOInstance: reportService.getProjectPerformanceViewDetails(params), costOption: params?.costOption])
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}		
	}
	
	def getProjectExpenseSummary() {
		if(projectService.isValidProjectAccess(Long.valueOf(params?.id))) {
			render(view : "projectExpenseSummary", model: reportService.getProjectExpenseSummary(params))
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}
	}
		
	/** 
	 * This Action used to Drill Down Project Performance View Details
	 * Display Employee Hours and costing details By Project and Activity
	 * @param projectId : Project Domain Id
	 * @param activity : Labor Activity Code
	 * @return Group By Employee List through EmployeeByProjectAndActivityDTO
	 */
	def getEmployeeByProjectAndActivity() {
		if(projectService.isValidProjectAccess(Long.valueOf(params?.projectId))) {
			render(view : "employeeByProjectAndActivity", model: reportService.getEmployeeByProjectAndActivity(params))
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}
	}
	
	/**
	 * This Action used to Drill Down Project Performance View Details Report
	 * Display Employee Detail view by project, activity and empCode
	 * @param projectId : Project Domain Id
	 * @param activity : Labor Activity Code
	 * @param empCode : Employee Code
	 * @return Employee List through EmployeeByProjectAndActivityDTO
	 */
	def getEmployeeDtlByProjectActivityAndEmpCode() {
		if(projectService.isValidProjectAccess(Long.valueOf(params?.projectId))) {
			render(view : "employeeDtlByProjectActivityAndEmp", model: reportService.getEmployeeDtlByProjectActivityAndEmpCode(params))
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}
	}
	/*Project Performance Actions End*/
	
	/*Labor History By COE Report Actions Start*/
	def laborHistoryByCOEReport() {
		
	}	
	def getlaborHistoryByCOEList() {
		params.max =  Constants.DEFAULT_REPORT_PAGINATION_RECORDS
		def data = laborHistoryByCOEReportService.getAllActivityByEmployeeReportList(params)
		render(view: "laborHistoryReportByCOE/laborHistoryByCOEDetail", model: data)
	}
	/*Labor History By COE Report Actions End*/
	
	/*Employee By Project By Activity Report related Actions Start*/
	def employeeByProjectByActivityReport() {
		def  loggedInUserInstance = userService.getLoggedInUser()
		def userInstance=User.get(loggedInUserInstance?.id)
		render(view : "employeeByProjectByActivity/projectByActivityReport", model: [userInstance: userInstance])
	}
	
	def getAllEmployeeByProjectByActivityReportData() {
		try {
			params.max =  Constants.DEFAULT_REPORT_PAGINATION_RECORDS
			render(view: "employeeByProjectByActivity/projectByActivityList", model: projectByActivityReportService.getAllProjectByActivityReportList(params))
			return
		} catch(Exception e) {
			log.error "getAllActivityByEmployeeReportData Exception "+e
		}
	}
	
	/*Employee By Project By Activity Report related Actions End*/
	
	
	/*Employee By Activity By Project Report related Actions Start*/
	def employeeByActivityByProjectReport() {
		def  loggedInUserInstance = userService.getLoggedInUser()
		def userInstance=User.get(loggedInUserInstance?.id)
		render(view : "employeeByActivityByProject/activityByProjectReport", model: [userInstance: userInstance])
	}

	def getAllEmployeeByActivityByProjectReportData() {
		try {
			params.max =  Constants.DEFAULT_REPORT_PAGINATION_RECORDS
			render(view: "employeeByActivityByProject/activityByProjectList", model: activityByProjectReportService.getAllActivityByProjectReportList(params))
			return
		} catch(Exception e) {
			log.error "getAllEmployeeByActivityByProjectReportData Exception "+e
		}
	}
	/*Employee By Activity By Project Report related Actions End*/
	
	/**
	 * Export Report Data in xlsx format
	 * @return
	 */
	def exportReport() {
		
		response.setHeader("Content-disposition", "attachment; filename=${params.reportName}.xlsx")
		response.characterEncoding = "UTF-8"
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		
		if(params.reportName == Constants.ACTIVITY_BY_EMPLOYEE_REPORT_NAME){
			def data = activityByEmployeeReportService.getActivityByEmployeeListForExport(params)
			def filePath = excelExportService.activityByEmployee(data?.activityByEmployeeReportList, params, data?.reportTotalMap)
			response.outputStream << new File(filePath)?.readBytes()
			return
		}else if(params.reportName == Constants.EMPLOYEE_BY_ACTIVITY_REPORT_NAME){
			def data = employeeByActivityReportService.getEmployeeByActivityListForExport(params)
			def filePath = excelExportService.employeeByActivty(data?.employeeByActivityReportList, params, data?.reportTotalMap)
			response.outputStream << new File(filePath)?.readBytes()
			return
		} else if(params.reportName == Constants.PROJECT_BY_ACTIVITY_REPORT_NAME){			
			def data = projectByActivityReportService.getProjectByActivityListForExport(params)
		    def filePath = excelExportService.projectByActivity(data.employeeByProjectReportList, params, data.reportTotalMap)
			response.outputStream << new File(filePath)?.readBytes()
			return
		} else if(params.reportName == Constants.ACTIVITY_BY_PROJECT_REPORT_NAME){
			def data = activityByProjectReportService.getActivityByProjectListForExport(params)
			def filePath = excelExportService.activityByProject(data?.employeeByActivityByProjectReportList, params, data?.reportTotalMap)
			response.outputStream << new File(filePath)?.readBytes()
			return
		} else if(params.reportName == Constants.LABOR_ACTIVITY_BY_COE_REPORT_NAME){
			
			def data = laborHistoryByCOEReportService.getlaborHistoryByCOEForExport(params)
			def filePath = excelExportService.laborHistoryByCOE(data?.coeLaborHistoryList, params, data?.reportTotalMap)
			response.outputStream << new File(filePath)?.readBytes()
			return 				
		} else if(params.reportName == Constants.PROJECT_EXPENSE_REPORT_NAME){
			def data = reportService.getProjectExpensesByExpenseCodeReportData(params)
			def filePath = excelExportService.projectByExpense(data?.projectByExpenseList, params, data?.reportTotalMap)
			response.outputStream << new File(filePath)?.readBytes()
		}		
		return			
	}
	
	def projectExpensesByExpenseCode() {
		def  loggedInUserInstance = userService.getLoggedInUser()
		def userInstance=User.get(loggedInUserInstance?.id)
		render(view : "projectByExpense/projectByExpenseReport", model: [userInstance: userInstance])
	}
	
	def projectExpensesByExpenseCodeClear(){
		redirect(action: "projectExpensesByExpenseCode")
	}
	
	def getProjectExpensesByExpenseCodeReportData(){
		params.max =  Constants.DEFAULT_REPORT_PAGINATION_RECORDS
		render(view: "projectByExpense/projectByExpensesList", model: reportService.projectByExpenseReportList(params))
	}
	
	/**
	 * This Action used to get All Actual Expense List
	 * @param params?.id : Project Id
	 * @return getAllActualExpenseList List
	 */
	def getAllActualExpenseList() {
		if(projectService.isValidProjectAccess(Long.valueOf(params?.id))) {
			render(view: "reportActualExpenseDetail" , model: reportService.getAllActualExpenseList(params) )			
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}	
	}	
	/**
	 * This Action used to get All Actual Expense List By expense
	 * @param params?.projectId : Project Id
	 * @param params?.expId : Expense Id
	 * @return getAllActualExpenseDetailByExpenseCode List
	 */
	def getAllActualExpenseDetailByExpenseCode() {
		if(projectService.isValidProjectAccess(Long.valueOf(params?.prodId))) {
			render(view: "reportActualExpenseDetailByExp" , model: reportService.getAllActualExpenseDetailByExpenseCode(params) )
		} else {
		redirect(action: "unauthorized", controller:"auth")
		}
	}
	/**
	 * This Action used to get All Actual Labor Employee List
	 * @param params?.id : Project Id
	 * @return ProjectActualLaborDetail List
	 */
	def getAllActualLaborEmployeeListPage() {
		if(projectService.isValidProjectAccess(Long.valueOf(params?.id))) {
			render(view: "reportActualLaborDetail" , model: reportService.getAllActualLaborEmployeeList(params) )
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}
	}
	/**
	 * This Action used to get All Actual Labor Employee activity List
	 * @param params?.projectId : Project Id
	 * @param params?.employeeId : Employee Id
	 * @return ProjectActualLaborDetail List
	 */
	def getAllActualLaborActivityDetail() {
		if(projectService.isValidProjectAccess(Long.valueOf(params?.projId))) {
			render(view: "reportEmployeeActivityDetail" , model: reportService.getAllActualLaborActivityDetail(params) )
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}		
	}
}
