package org.solcorp.etech

class Options {

    /**
     * Company Id 
     */
    Long Id
	
	// Company Information section
	
	/**
     * Company name 
     */
    String name
	
	/**
	 * Address line 1 
	 */
	String addressLine1
	
	/**
	 * Address line 2
	 */
	String addressLine2
	
	/**
	 * Name of city 
	 */
	String city
	
	/**
	 * ENUM State 
	 */
	StateType state
	
	/**
	 * Zip 1 
	 */
	String zip1
	
	/**
	 * Zip 2 
	 */
	String zip2
	
	/**
	 * Store County 
	 */
	String county
	
	/**
	 * Store country 
	 */
	String country
	
	/**
	 * Phone number 
	 */
	String phone
	
	/**
	 * Store fax 
	 */
	String fax 
	
	/**
	 * Store Email-address 
	 */
	String emailAddress
	
	/**
	 * Store Email-download 
	 */
	String emailDownload
	
	/**
	 * Store Email=support 
	 */
	String emailSupport
	
	/**
	 * Web-site address
	 */
	String website
	
	/**
	 * Store Federal Id
	 */
	String federalId
	
	/**
	 * Store State Id
	 */
	String stateId
	
	// Time Clock Options section
	
	/**
	 * ENUM optionsUsagetype 
	 */
	OptionsUsageType usage
	
	
	/**
	 * ENUM InOutTrackingMethodType 
	 */
	InOutTrackingMethodType inOutTrackingMethod
	
	// Expense Options section
	
	/**
	 * Constant :  BILL_DEFAULT
	 */
	String  billDefault
	
	/**
	 * Constant : REIMBURSE_DEFAULT
	 */
	String reimburseDefault
		
	/**
	 * Constant : ALLOWED_PAST_MONTHS
	 */
	String allowedPastMonths
	
	// Allocation Parameters section
	
	/**
	 * Constant : CAPACITY_FACTOR 
	 */
	String capacityFactor
	
	/**
	 * Constant : ENTRY_METHOD 
	 */
	String entryMethod
	
	/**
	 * Store Capacity 
	 */
	String capacity
	
	/**
	 * Constant : CAPACITY_TYPE  
	 */
	String capacityType
	
	/**
	 * Constant : OVER_ALLOCATION 
	 */
	String overAllocation
	
	/**
	 * Constant : YES_NO_OPTIONS 
	 */
	String editable
	
	// General section
	
	/**
	 *  Constant : DELIMITER_FOR_EXPORT
	 */
	String delimiterForExport
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String trackProductCode
	
	/**
	 * constant : COST_REPORTING 
	 */
	String costReporting
	
	/**
	 * Store project file 
	 */
	String projectFile
	
	/**
	 * Constant : BUDGET_BY 
	 */
	String budgetBy
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String payCode
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String restrictOperationByProjectType
	
	/**
	 * Store Hours per Month 
	 */
	String hoursPerMonth
	
	// Project Defaults section
	
	/**
	 * ENUM : BillCodeType
	 */
	BillCodeType billCode
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String phases
	
	/**
	 * Constant : TRACK_FORMAT 
	 */
	String trackFormat
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String schStartDateRequired
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String schCompDateRequred
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String estimationTimeByTask
	
	// Task section 
	
	/**
	 * Constant : TASK_NUMBER 
	 */
	String taskNumber
	
	/**
	 * Constant : TASK_UNIQUE 
	 */
	String taskUnique
	
	/**
	 * Constant : TASK_MASTER 
	 */
	String taskMaster
	
	/**
	 * Constant : EMPL_NONE_ASSIGNED_TASK_ACCESS 
	 */
	String emplNonAssignedTaskAccess
	
	/**
	 * Constant : TASK_NOTES_VISIBILITY
	 */
	String taskNotesVisibility
	
	/**
	 * Store Payroll Flag 
	 */
	String payrollFlag
	
	// Timesheet section
	
	/**
	 * Constant : START_WORK_WEEK
	 */
	String startWorkWeek
	
	/**
	 * Constant : MANUAL_TIMESHEET_POST_BY
	 */
	String manualTimesheetPostBy
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String permitOnlyDefaultOperation
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String useDropdowns
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String trackQuantity
	
	/**
	 * Constant : DATE_FORMAT 
	 */
	String dateFormat
	
	/**
	 * Store Mgr Email on Rejected TS 
	 */
	String mgrEmailonRejectedTS
	
	/**
	 * Constant : PROJECT_FILTER 
	 */
	String projectFilter
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String allocateEmployees
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String overTimeProcessing
	
	/**
	 * Store TimeSheet Format File 
	 */
	String timeSheetFormatFile
	
	/**
	 * Store Timesheet Summary 
	 */
	String timesheetSummary
	
	/**
	 * Store Timesheet Approval 
	 */
	String timesheetApproval
	
	/**
	 * Store payroll export 
	 */
	String payrollExport
	
	/**
	 * Store phase Time sheet
	 */ 
	String phaseTimeSheet
	
	/**
	 * Store operation
	 */
	String operation
	
	/**
	 * Store task 
	 */
	String task
	
	/**
	 * Store bill code 
	 */
	String billCodeTimeSheet
	
	//Invoice section
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String billTrxLessStartDate
	
	/**
	 * Constant : INVOICE_PRINT_OPTIONS 
	 */
	String invoicePrintOptions
	
	/**
	 * Store Invoice from email
	 */
	String invoiceFromEmail
	
	/**
	 * Store invoice report file
	 */
	String invoiceReportFile
	
	// Main Menu section
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String myProjects
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String myEmployees
	
	/**
	 * Constant : YES_NO_OPTIONS
	 */
	String myTasks
	
	static constraints = {
		name(nullable: false, blank: false, maxSize: Constants.OPTIONS_COMPANY_NAME_LENGTH)
		addressLine1(nullable: false, blank: false, maxSize: Constants.OPTIONS_ADDRESS_LINE_1_LENGTH)
		addressLine2(nullable: true, maxSize: Constants.OPTIONS_ADDRESS_LINE_2_LENGTH)
		city(nullable: false, blank: false, maxSize: Constants.OPTIONS_CITY_LENGTH)
		state(nullable: true)
		zip1(nullable: true, maxSize: Constants.OPTIONS_ZIP_1_LENGTH)
		zip2(nullable: true, maxSize: Constants.OPTIONS_ZIP_2_LENGTH)
		county(nullable: true, maxSize: Constants.OPTIONS_COUNTY_LENGTH)
		country(nullable: false, blank: false, maxSize: Constants.OPTIONS_COUNTRY_LENGTH)
		phone(nullable: true, maxSize: Constants.OPTIONS_PHONE_NUMBER_LENGTH)
		fax(nullable: true, maxSize: Constants.OPTIONS_FAX_LENGTH)
		emailAddress(nullable: true, email: true, maxSize: Constants.OPTIONS_EMAIL_LENGTH)
		emailDownload(nullable: true, email: true, maxSize: Constants.OPTIONS_EMAIL_DWONLOAD_LENGTH)
		emailSupport(nullable: true, email: true, maxSize: Constants.OPTIONS_EMAIL_SUPPORT_LENGTH)
		website(nullable: true, url: true, maxSize: Constants.OPTIONS_WEBSITE_LENGTH)
		federalId(nullable: true, maxSize: Constants.OPTIONS_FEDERAL_ID_LENGTH)
		stateId(nullable: true, maxSize: Constants.OPTIONS_STATE_ID_LENGTH)
		usage(nullable: true)
		inOutTrackingMethod(nullable: true)
		billDefault(nullable: true)
		reimburseDefault(nullable: true)
		allowedPastMonths(nullable: true)
		capacityFactor(nullable: true)
		entryMethod(nullable: true)
		capacity(nullable: true, maxSize: Constants.OPTIONS_CAPACITY_LENGTH)
		capacityType(nullable: true)
		overAllocation(nullable: true)
		editable(nullable: true)
		delimiterForExport(nullable: true)
		trackProductCode(nullable: true)
		costReporting(nullable: true)
		projectFile(nullable: true, maxSize: Constants.OPTIONS_PROJECT_FILE_LENGTH)
		budgetBy(nullable: true)
		payCode(nullable: true)
		restrictOperationByProjectType(nullable: true)
		hoursPerMonth(nullable: true, maxSize: Constants.OPTIONS_HOURS_PER_MONTH_LENGTH)
		billCode(nullable: true)
		phases(nullable: true)
		trackFormat(nullable: true)
		schStartDateRequired(nullable: true)
		schCompDateRequred(nullable: true)
		estimationTimeByTask(nullable: true)
		taskNumber(nullable: true)
		taskUnique(nullable: true)
		taskMaster(nullable: true)
		emplNonAssignedTaskAccess(nullable: true)
		taskNotesVisibility(nullable: true)
		payrollFlag(nullable: true, maxSize: Constants.OPTIONS_PAYROLL_FLAG_LENGTH)
		startWorkWeek(nullable: true)
		manualTimesheetPostBy(nullable: true)
		permitOnlyDefaultOperation(nullable: true)
		useDropdowns(nullable: true)
		trackQuantity(nullable: true)
		dateFormat(nullable: true)
		mgrEmailonRejectedTS(nullable: true, maxSize: Constants.OPTIONS_MANAGER_FROM_EMAIL_LENGTH)
		projectFilter(nullable: true)
		allocateEmployees(nullable: true)
		overTimeProcessing(nullable: true)
		timeSheetFormatFile(nullable: true, maxSize: Constants.OPTIONS_TIMESHEET_fROMAT_LENGTH)
		timesheetSummary(nullable: true, maxSize: Constants.OPTIONS_TIMESHEET_SUMMARY_LENGTH)
		timesheetApproval(nullable: true, maxSize: Constants.OPTIONS_TIMESHEET_APPROVAL_LENGTH)
		payrollExport(nullable: true, maxSize: Constants.OPTIONS_PAYROLL_EXPORT_LENGTH)
		phaseTimeSheet(nullable: true)
		operation(nullable: true)
		task(nullable: true)
		billCodeTimeSheet(nullable: true)
		billTrxLessStartDate(nullable: true)
		invoicePrintOptions(nullable: true)
		invoiceFromEmail(nullable: true, email: true, maxSize: Constants.OPTIONS_INVOICE_FROM_EMAIL_LENGTH)
		invoiceReportFile(nullable: true, maxSize: Constants.OPTIONS_INVOICE_REPORT_FILE_LENGTH)
		myProjects(nullable: true)
		myEmployees(nullable: true)
		myTasks(nullable: true)
	}
	
	static mapping = {
		state sqlType: 'varchar(30)'
		usage sqlType: 'varchar(30)'
		usage column: '`usage`'
		inOutTrackingMethod sqlType: 'varchar(30)'
		billCode sqlType: 'varchar(30)'
	}
	
	
}
