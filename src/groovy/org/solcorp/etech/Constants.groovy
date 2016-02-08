package org.solcorp.etech

import java.util.List;

class Constants {
	public static final ADMIN_USERNAME = "admin"
	public static final SUPER_ADMIN_USERNAME = "etpadmin"
	public static final SYSTEM_JOB_USERNAME = "system_job"
	
	public static final PERMISSION_LABEL_MAP = [
		"canViewProject": "Project View","canViewProjectHours": "Project Hours Visibility", "canViewActualRate": "Actual Rate Visibilty",
		"canViewStandardCost": "Standard Cost Visibility", "canModifyStandardCost": "Modify Standard Cost",
		"canViewPricing": "Pricing Visibility", "canPerformTimesheetEntry": "Time Entry",
		"canModifyTimesheetEntry": "Modify Time", "canApproveTime": "Time Approval",
		"canPerformAccountingPeriodMaintenance": "Accounting Period Maintenance", "canViewProjectReports": "Project Reporting",
		"canViewEmployeeReports": "Employee Reporting", "canViewCOEReports": "COE Reporting",
		"canViewDepartmentReports": "Department Reporting", "canViewClientReports": "Client Reporting",
		"canSubmitProject": "Submit Project", "canApproveProject": "Approve Project"
		]
	
	public static final DEFAULT_EXECUTIVE_EMPLOYEE_CODE = "defExec"
	public static final DEFAULT_CUSTOMER_CODE = "defCust"
	public static final NONE_CUSTOMER_CODE = "[None]"
	public static final DEFAULT_PROJECT_CODE = "00000"
	
	public static String DATE_FORMAT = "MM/dd/yyyy"
	public static String DATE_FORMAT_DATEPICKER = "MM/dd/yyyy"
	public static String DATETIME_FORMAT_YYYY_MM_DD ="yyyy-MM-dd HH:mm:ss.S"
	public static final int MINUMUM_PREVIOUS_YEAR_COUNT = 100
	//Employee Constants Start
	public static int EMPLOYEE_CODE_LENGTH = 16
	public static int EMPLOYEE_SSNO_LENGTH = 11
	public static int EMPLOYEE_LAST_NAME_LENGTH = 50
	public static int EMPLOYEE_FIRST_NAME_LENGTH = 50
	public static int EMPLOYEE_MIDDLE_NAME_LENGTH = 50
	public static int EMPLOYEE_LABER_DEPT_LENGTH = 10
	public static int EMPLOYEE_LABER_CODE_LENGTH = 10
	public static int EMPLOYEE_PAY_DEPT_LENGTH = 10
	public static int EMPLOYEE_EMAIL_LENGTH = 255
	public static final PAY_DEPT_MAP = ["ADM": "Administration", "CONS": "Consulting", "HR": "Human Resource", "MKT": "Marketing"]
	public static final YES_NO = ["N": "No", "Y": "Yes"]
	//Employee Constants End
	
	//User Start
	public static int USER_NAME_LENGTH = 12
	public static int MAX_PASSWORD_LENGTH = 12
	
	//User End
	
	//LaborActivityGroup Constants Start
	public static int LABORACTIVITYGROUP_CODE_LENGTH = 10
	public static int LABORACTIVITYGROUP_DESCRIPTION_LENGTH = 100
	//LaborActivityGroup Constants End
	
	//LaborActivityCode Constants Start
	public static int LABORACTIVITYCODE_CODE_LENGTH = 12
	public static int LABORACTIVITYCODE_DESCRIPTION_LENGTH = 100
	public static int LABORACTIVITYCODE_STDCOST_LENGTH = 16
	public static int LABORACTIVITYCODE_OVERHEAD_LENGTH = 4
	public static int LABORACTIVITYCODE_BILLRATE_LENGTH = 16
	public static int LABORACTIVITYCODE_QCFLAG_LENGTH = 1
	public static int LABORACTIVITYCODE_CNTPOINT_LENGTH = 1
	public static int LABORACTIVITYCODE_OPERATIONS_LENGTH = 1
	public static int LABORACTIVITYCODE_ACTIVE_LENGTH = 1
	public static final YES_NO_MAP = ["N" : "No", "Y" : "Yes"]
	public static final LABORACTIVITYCODE_STATUS = ["Y" : "Active", "N" : "Inactive"]
	//LaborActivityCode Constants End
	
	//Customer constants start
	public static int CUSTOMER_CODE_LENGTH = 10
	public static int CUSTOMER_NAME_LENGTH = 50
	public static int CUSTOMER_TYPE_LENGTH = 30
	public static int CUSTOMER_CATEGORY_LENGTH = 6
	public static int CUSTOMER_CREDIT_LIMIT_LENGTH = 6
	public static int CUSTOMER_OUTSIDE_REP_LENGTH = 6
	public static int CUSTOMER_FEDERAL_ID_LENGTH = 15
	public static int CUSTOMER_STATE_ID_LENGTH = 15
	public static int CUSTOMER_COMMENTS_LENGTH = 2048
	public static int CUSTOMER_CREDIT_CODE_LENGTH = 15
	//Customer constants end
	
	
	//Address constants start
	public static int ADDRESS_LINE_1_LENGTH = 255
	public static int ADDRESS_LINE_2_LENGTH = 255
	public static int ADDRESS_CITY_LENGTH = 30
	public static int ADDRESS_ZIP_LENGTH = 15
	public static int ADDRESS_COUNTRY_LENGTH = 30
	public static int ADDRESS_STATE_LENGTH = 30
	//Address constants end
	
	//contact constants start
	public static int CONTACT_NAME_LENGTH = 50
	public static int CONTACT_PHONE_NUMBER_LENGTH = 15
	public static int CONTACT_FAX_NUMBER_LENGTH = 15
	public static int CONTACT_EMAIL_ADDRESS_LENGTH = 255
	public static int CONTACT_CELLULAR_LENGTH = 15
	//contact constants end
	
	//COE Start	
	public static int COE_CODE_LENGTH = 10
	public static int COE_DESCRIPTION_LENGTH = 300
	//COE End
	
	//Project Start
	public static int PROJECT_CODE_LENGTH = 20
	public static int PROJECT_NAME_LENGTH = 50
	public static int PROJECT_REVENUE_LENGTH = 15
	public static String PROJECT_MANAGER_SESSION_ID = "projectManagerList"
	//Project End
	
	//Department Start
	public static int DEPARTMENT_CODE_LENGTH = 10
	//Department End
	
	//Options constants start
	
	//Drop down values
	//YES-NO for all
	public static final YES_NO_OPTIONS = [ "Y" : "Yes", "N" : "No"]
	// Expense Options section
	public static final BILL_DEFAULT = [ "N" : "Non-Bill", "B" : "Bill"]
	public static final REIMBURSE_DEFAULT = [ "N" : "Non-Reimb", "Y" : "Reimburse "]
	public static final ALLOWED_PAST_MONTHS = [ "0" : "0",  "1" : "1",  "2" : "2",  "3" : "3",  "4" : "4",  "5" : "5",  "6" : "6",  "7" : "7",  "8" : "8",  "9" : "9",  "10" : "10",  "11" : "11",  "12" : "12"]
	// Allocation Parameters section
	public static final CAPACITY_FACTOR = [ "ACTIVE_EMPLOYEES" : "Active Employees", "EMPLOYESS_DAYS_MONTHS" : "Employee Days/Month"]
	public static final ENTRY_METHOD = [ "P" : "Percentage", "D" : "Days", "H" : "Hours"]
	public static final CAPACITY_TYPE = [ "DAYS_MONTH" : "Days/Month", "HOURS_WEEK" : "Hours/Week" ]
	public static final OVER_ALLOCATION = [ "IGNORE":"Ignore", "WARN":"Warn", "ERROR":"Error" ]
	// General section
	public static final DELIMITER_FOR_EXPORT = [ "TAB" : "Tab", "PIPE" : "Pipe -|"]
	public static final COST_REPORTING = [ "STANDARD_RATE" : "Standard Rate", "ACTUAL_RATE" : "Actual Rate"]  
	public static final BUDGET_BY = [ "COST_PLANNING" : "Cost Planning", "HOURS_UTILIZATION" : "Hours Utilization"]
	// Project Defaults section
	public static final TRACK_FORMAT = [ "BY_OPERATION" : "By Operation", "BY_TASK" : "By Task"] 
	// Task section
	public static final TASK_NUMBER = [ "USER_ENTERED" : "User Entered", "AUTO_ASSIGN" : "Auto Assign"] 
	public static final TASK_UNIQUE = [ "PROJ_PHASE_OPER" : "Proj/Phase/Oper", "SYSTEM_WIDE" : "System Wide"]
	public static final TASK_MASTER = [ "REQUIRED_NO_VALIDATE" : "Required, No Validate", "REQUIRED_VALIDATE" : "Required, Validate", "NOT_REQUIRED" : "Not Required"] 
	public static final EMPL_NONE_ASSIGNED_TASK_ACCESS = [ "PERMIT" : "Permit", "WARN" : "Warn", "RESTRICT" : "Restrict"]
	public static final TASK_NOTES_VISIBILITY = [ "CREATOR" : "Creator", "ALL" : "All"] 
	// Timesheet section
	public static final START_WORK_WEEK = [ "SUNDAY" : "Sunday", "MONDAY" : "Monday", "TUESDAY" : "Tuesday", "WEDNESDAY" : "Wednesday", "THURSDAY" : "Thursday", "FRIDAY" : "Friday", "SATURDAY" : "Saturday"]
	public static final MANUAL_TIMESHEET_POST_BY = [ "USER_MANAGER" : "User/Manager", "MANAGER_ONLY" : "Manager Only", "PAY_DEPT_CTRL" : "Pay Dept Ctrl", "SUBMIT" : "Submit"]
	public static final DATE_FORMAT_OPTIONS = [ "MM_DD_YY" : "mm/dd/yy", "DD_MON_YY" : "dd-Mon-yy", "DD_MM_YY" : "dd/mm/yy"]
	public static final PROJECT_FILTER = [ "ALL_PROJECTS" : "All Projects", "EMPLOYEE_ASSIGNED" : "Employee Assigned"]
	//Invoice section
	public static final INVOICE_PRINT_OPTIONS = [ "BY_PROJECT_BILL_RATE" : "By Project/Bill Rate", "BY_PROJECT": "By Project"]	
	
	//Maximum value
	
	public static final OPTIONS_COMPANY_NAME_LENGTH = 35
	public static final OPTIONS_ADDRESS_LINE_1_LENGTH = 30
	public static final OPTIONS_ADDRESS_LINE_2_LENGTH = 30
	public static final OPTIONS_CITY_LENGTH = 25
	public static final OPTIONS_ZIP_1_LENGTH = 15
	public static final OPTIONS_ZIP_2_LENGTH = 15
	public static final OPTIONS_COUNTY_LENGTH = 25
	public static final OPTIONS_COUNTRY_LENGTH = 25
	public static final OPTIONS_PHONE_NUMBER_LENGTH = 15
	public static final OPTIONS_FAX_LENGTH = 15
	public static final OPTIONS_EMAIL_LENGTH = 255
	public static final OPTIONS_EMAIL_DWONLOAD_LENGTH = 255
	public static final OPTIONS_EMAIL_SUPPORT_LENGTH = 255
	public static final OPTIONS_WEBSITE_LENGTH = 255
	public static final OPTIONS_FEDERAL_ID_LENGTH = 10
	public static final OPTIONS_STATE_ID_LENGTH = 13
	public static final OPTIONS_CAPACITY_LENGTH = 16
	public static final OPTIONS_PROJECT_FILE_LENGTH = 40
	public static final OPTIONS_HOURS_PER_MONTH_LENGTH = 40
	public static final OPTIONS_PAYROLL_FLAG_LENGTH = 6
	public static final OPTIONS_MANAGER_FROM_EMAIL_LENGTH = 255
	public static final OPTIONS_TIMESHEET_fROMAT_LENGTH = 40
	public static final OPTIONS_TIMESHEET_SUMMARY_LENGTH = 40
	public static final OPTIONS_TIMESHEET_APPROVAL_LENGTH = 40
	public static final OPTIONS_PAYROLL_EXPORT_LENGTH = 40
	public static final OPTIONS_INVOICE_FROM_EMAIL_LENGTH = 255
	public static final OPTIONS_INVOICE_REPORT_FILE_LENGTH = 40
	//Options constants end
	
	//Product constants start
	public static final PRODUCT_CODE_LENGTH = 20
	public static final PRODUCT_LISTPRICE_LENGTH = 19
	public static final PRODUCT_DESCRIPTION_LENGTH = 100
	public static final PRODUCT_CATEGORY_LENGTH = 100
	public static final PRODUCT_EXPENSE_CATEGORY_GLACCOUNT_LENGTH = 10
	//Product constants end
	
	public static final int DEFAULT_PAGINATION_RECORDS = 50
	public static final int DEFAULT_REPORT_PAGINATION_RECORDS = 1000	
	
	// HH EmployeeType
	public static final String HH_EMPLOYEETYPE_SALARY = "S"
	public static final String HH_EMPLOYEETYPE_HOURLY = "H"
	public static final String HH_EMPLOYEETYPE_CONTRACTOR = "CK"
	
	// HH CustomerStatusType
	public static final String HH_CUSTOMERSTATUS_ACTIVE = "A"
	public static final String HH_CUSTOMERSTATUS_INACTIVE = "I"
	
	// HH ProjectStatusType
	public static final String HH_PROJECTSTATUS_ACTIVE = "A"
	public static final String HH_PROJECTSTATUS_INACTIVE = "I"
	
	//Project Category constants start
	public static final PROJECT_CATEGORY_LENGTH = 10
	public static final PROJECT_CATEGORY_DESCRIPTION_LENGTH = 100
	//Project Category constants end

	// HH CustomerMasterImportStatusType
	public static final String HH_CUSTOMERMASTERIMPORTSTATUS_LOADED = "Loaded"
	public static final String HH_CUSTOMERMASTERIMPORTSTATUS_IMPORTED = "Imported"
	
	// HH ExpenseMasterImportStatusType
	public static final String HH_EXPENSEMASTERIMPORTSTATUS_LOADED = "Loaded"
	public static final String HH_EXPENSEMASTERIMPORTSTATUS_IMPORTED = "Imported"
	
	// HH ProjectMasterImportStatusType
	public static final String HH_PROJECTMASTERIMPORTSTATUS_LOADED = "Loaded"
	public static final String HH_PROJECTMASTERIMPORTSTATUS_IMPORTED = "Imported"
	
	// HH LaborTransactionImportStatusType
	public static final String HH_LABORTRANSACTIONIMPORTSTATUS_LOADED = "Loaded"
	public static final String HH_LABORTRANSACTIONIMPORTSTATUS_IMPORTED = "Imported"
	
	// HH EmployeeMasterImportStatusType
	public static final String HH_EMPLOYEE_IMPORTSTATUS_LOADED = "Loaded"
	public static final String HH_EMPLOYEE_IMPORTSTATUS_IMPORTED = "Imported"
	
	// Default Product and Service
	public static final String DEFAULT_PRODUCT = "Legacy"
	public static final String DEFAULT_SERVICE = "Legacy_Service"
	public static final String DEFAULT_LABOR_ACTIVITY_GROUP = "Undefined"
	public static final String DEFAULT_COE = "Undefined"
	public static final String DEFAULT_LABOR_ACTIVITY_CODE = "Undefined"
	
	// Project Performance view Report Start
	public static int DEFAULT_CODE_LENGTH = 15
	public static final String REPORT_TOTAL_BY_ACTIVITY = "activity"
	public static final String REPORT_TOTAL_BY_GROUP = "group"
	public static final String REPORT_BY_COE = "coe"
	public static final String REPORT_COST_OPTION_STANDARD = "Standard"
	public static final String REPORT_COST_OPTION_ACTUAL = "Actual"
	public static final String REPORT_VIEW_EXP = 'exp'
	// Project Performance view Report Start
	
	//Product Category constants start
	public static final PRODUCTCATEGORY_CATEGORY_LENGTH = 20
	public static final PRODUCTCATEGORY_DESCRIPTION_LENGTH = 100
	//Product Category constants end
	
	//Industry Constants Start
	public static int INDUSTRY_CODE_LENGTH = 10
	public static int INDUSTRY_NAME_LENGTH = 50
	public static int INDUSTRY_DESCRIPTION_LENGTH = 100
	//Industry Constants End
	public static final PROJECT_BY_ACTIVITY_REPORT_NAME="projectByActivity"
	public static final ACTIVITY_BY_PROJECT_REPORT_NAME="activityByProject"
	public static final ACTIVITY_BY_EMPLOYEE_REPORT_NAME="activityByEmployee"
	public static final EMPLOYEE_BY_ACTIVITY_REPORT_NAME="employeeByActivity"
	public static final LABOR_ACTIVITY_BY_COE_REPORT_NAME="laborActivityByCOE"
	public static final PROJECT_EXPENSE_REPORT_NAME="projectByExpense"
	
	public static final String DISPLAY_NAME_CUSTOMER_JOB = "Import Customers"
	public static final String DISPLAY_NAME_DEPARTMENT_JOB = "Import Departments"
	public static final String DISPLAY_NAME_EMPLOYEE_JOB = "Import Employees"
	public static final String DISPLAY_NAME_LABOR_JOB = "Import Labor Txns"
	public static final String DISPLAY_NAME_MISSING_PROJECTS_JOB = "Import Missing Projects"
	public static final String DISPLAY_NAME_PROJECTS_JOB = "Import Projects"
	public static final String DISPLAY_NAME_EXPENSES_JOB = "Import Expenses"
	
}
