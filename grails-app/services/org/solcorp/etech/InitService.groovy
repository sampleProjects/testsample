package org.solcorp.etech

import grails.transaction.Transactional
import org.apache.shiro.crypto.hash.Sha256Hash
import org.solcorp.etech.RoleType
import org.solcorp.etech.PermissionType

@Transactional
class InitService {
	def grailsApplication
	def departmentService
	def userSessionService
	
    def bootstrapData() {
		
		grailsApplication.config.departmentMap = [:]
		grailsApplication.config.shouldJobrun = Boolean.TRUE
		
		generateRoles()
		
		createDefaultUsers()
		
		createSystemJobUser()
		
		createDefaultDepartments()
		
		createDefaultAccountExecutiveEmployee()
		
		createDefaultCustomer()
		
		createDefaultReferenceTables()
		
		createDefaultProjects()
		
		departmentService.setPayDepartment()
		
		userSessionService.updateAllUserSessionInstance()
		
		createDefaultActivity()
    }
	
	def createDefaultActivity() {
		if(! LaborActivityCode.findByCode(Constants.DEFAULT_LABOR_ACTIVITY_CODE)) {
			def defaultLaborActivityCode = new LaborActivityCode()
			defaultLaborActivityCode.code = Constants.DEFAULT_LABOR_ACTIVITY_CODE
			defaultLaborActivityCode.description = Constants.DEFAULT_LABOR_ACTIVITY_CODE
			defaultLaborActivityCode.laborActivityGroup = LaborActivityGroup.findByCode(Constants.DEFAULT_LABOR_ACTIVITY_GROUP)
			defaultLaborActivityCode.standardRate = new BigDecimal(1)
			defaultLaborActivityCode.overHead = new BigDecimal(0)
			defaultLaborActivityCode.billRate = new BigDecimal(0)
			defaultLaborActivityCode.dateCreated = new Date()
			defaultLaborActivityCode.lastUpdated = defaultLaborActivityCode.dateCreated 
			defaultLaborActivityCode.createdBy = User.findByUsername(Constants.SYSTEM_JOB_USERNAME) 
			defaultLaborActivityCode.updatedBy = defaultLaborActivityCode.createdBy 
			defaultLaborActivityCode.save(flush: true, failOnError: true)
		}
	}
	
	def createDefaultProjects() {
		if(! Project.findByCode(Constants.DEFAULT_PROJECT_CODE)) {
			// Create default Project
			def defaultProject = new Project()
			defaultProject.code = Constants.DEFAULT_PROJECT_CODE
			defaultProject.name = Constants.DEFAULT_PROJECT_CODE
			defaultProject.projectType = ProjectType.NON_UTILIZED //ProjectType.ADMINISTRATION
			defaultProject.billCodeType = BillCodeType.INTERNAL
			defaultProject.status = ProjectStatusType.OPEN
			defaultProject.customer = Customer.findByCode(Constants.NONE_CUSTOMER_CODE)
			defaultProject.accExecutive = Employee.findByCode(Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE)
			defaultProject.save(flush: true, failOnError: true)
		}
	}
	
	def createDefaultReferenceTables() {
		if(! COE.findByCode(Constants.DEFAULT_COE)) {
			// Create default COE
			def defaultCOE = new COE(code: Constants.DEFAULT_COE).save(flush: true, failOnError: true)
			
			// Create default Labor Activity Group
			def defaultLaborActivityGroup = new LaborActivityGroup(code: Constants.DEFAULT_LABOR_ACTIVITY_GROUP)
			defaultCOE.addToLaborActivityGroup(defaultLaborActivityGroup)
			defaultCOE.save(flush: true, failOnError: true)
			
			// Create default product
			def defaultProjectProduct = new ProjectProduct(code: Constants.DEFAULT_PRODUCT).save(flush: true, failOnError: true)
			
			// Create default service
			def defaultService = new Service(code: Constants.DEFAULT_SERVICE, laborActivityGroup: defaultLaborActivityGroup, product: defaultProjectProduct).save(flush: true, failOnError: true)
		}
	}
	
	def generateRoles() {
		
		//Roles
		def roleList = []
		
		//Create roles if they dont exist in the database 
		RoleType.allRoleList().each { roleName ->
			def role = 	Role.findByName(roleName)
			if(!role) {
				role = new Role(name: roleName)
			} 
			roleList.add(role)
		}
		
		//Assign Permissions to roles
		ArrayList permissionList = getPermissionList()
		permissionList.each { permissionMap ->
			roleList.each { currentRole ->
				if(permissionMap.get(currentRole?.name)) {
					permissionMap.name.split(',').each{
						if(it) {
							currentRole.addToPermissions(it) 
						}
					}
				}
			}
		}
		
		//Save all roles for assigned permissions
		roleList.each { currentRole ->
			currentRole.save(flush:true)
		}
	}
	
	def getPermissionList() {
		def permissionList = new ArrayList()
		
		permissionList.add([name:(getDefaultPermissions()), (RoleType.ROLE_ADMIN.name()):true,(RoleType.ROLE_SUPER_ADMIN.name()):true,(RoleType.ROLE_SYSTEM_JOB.name()):true,
			(RoleType.ROLE_TIMESHEET.name()): true, (RoleType.ROLE_ACCOUNT_DIRECTOR.name()): true, (RoleType.ROLE_EXECUTIVE_LEADERS.name()): true,
			(RoleType.ROLE_OPERATIONS_LEADERS.name()): true, (RoleType.ROLE_PROJECT_MANAGER.name()): true,
			(RoleType.ROLE_FINANCE.name()): true, (RoleType.ROLE_PROJECT_COSTING.name()): true, (RoleType.ROLE_SALES.name()): true, (RoleType.ROLE_EMPLOYEE_SUPERVISOR.name()): true])
		
		return permissionList
	}

	def getDefaultPermissions() {
		return "address:*,auth:*,COE:*,contact:*,customer:*,customQuartz:*,department:*,employee:*,laborActivityCode:*,laborActivityGroup:*,options:*," +
			"productCategory:*,product:*,projectCategory:*,project:*,projectExpense:*,projectLabor:*,projectLaborDetail:*,report:*,roleMaintenance:*,user:*,industry:*"
	}
			
	def createDefaultCustomer() {
		def defaultAccountExecutiveEmployee = Employee.findByCode(Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE)
		
		if(! Customer.findByCode(Constants.DEFAULT_CUSTOMER_CODE)) {
			def customer = new Customer(code: Constants.DEFAULT_CUSTOMER_CODE, name: Constants.DEFAULT_CUSTOMER_CODE, acctExecutive: defaultAccountExecutiveEmployee)
			
			customer.save(flush: true, saveOnError: true)
		}
		
		// Create Customer with code [None]
		if(! Customer.findByCode(Constants.NONE_CUSTOMER_CODE)) {
			def customer = new Customer(code: Constants.NONE_CUSTOMER_CODE, name: Constants.NONE_CUSTOMER_CODE, acctExecutive: defaultAccountExecutiveEmployee)
			
			customer.save(flush: true, saveOnError: true)
		}
	}
	
	def createDefaultAccountExecutiveEmployee() {
		if(! Employee.findByCode(Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE)) {
			def departmentInstance = Department.findByCode("Consulting")
			
			def employee = new Employee(code: Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE, lastName: Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE, 
				firstName: Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE, status: StatusType.ACTIVE, payDept: departmentInstance, employeeType: EmployeeType.CONTRACT)
			
			employee.save(flush: true, saveOnError: true)
			println "employee.errors: " + employee.errors 
			
			def user = new User(username: employee.code, passwordHash: new Sha256Hash("password").toHex(), isPasswordChangeRequired: false, status: StatusType.ACTIVE, tsApprove: 'N', empReportType: EmployeeReportType.ALL_PROJECTS)
			user.addToRoles(Role.findByName(RoleType.ROLE_ACCOUNT_DIRECTOR))
			user.employee = employee 
			user.save(flush: true, saveOnError: true)
		}
	}
	
	def createDefaultUsers() {
		if(! User.findByUsername(Constants.ADMIN_USERNAME)) {
			//def user = new User(username: Constants.ADMIN_USERNAME, passwordHash: new Sha256Hash("password").toHex(), isPasswordChangeRequired: false, status: StatusType.ACTIVE, projectAccessLevel: ProjectAccessLevelType.ADMIN, tsApprove: 'N', empReportType: EmployeeReportType.ALL_PROJECTS)
			def user = new User(username: Constants.ADMIN_USERNAME, passwordHash: new Sha256Hash("password").toHex(), isPasswordChangeRequired: false, status: StatusType.ACTIVE, tsApprove: 'N', empReportType: EmployeeReportType.ALL_PROJECTS)
			user.addToRoles(Role.findByName(RoleType.ROLE_ADMIN))
			user.addToPermissions("*:*")
			user.save(flush: true, saveOnError: true)
		}
		if(! User.findByUsername(Constants.SUPER_ADMIN_USERNAME)) {
			//def user = new User(username: Constants.SUPER_ADMIN_USERNAME, passwordHash: new Sha256Hash("super").toHex(), isPasswordChangeRequired: false, status: StatusType.ACTIVE, projectAccessLevel: ProjectAccessLevelType.ADMIN, tsApprove: 'N', empReportType: EmployeeReportType.ALL_PROJECTS)
			def user = new User(username: Constants.SUPER_ADMIN_USERNAME, passwordHash: new Sha256Hash("super").toHex(), isPasswordChangeRequired: false, status: StatusType.ACTIVE, tsApprove: 'N', empReportType: EmployeeReportType.ALL_PROJECTS)
			user.addToRoles(Role.findByName(RoleType.ROLE_SUPER_ADMIN))
			user.addToPermissions("*:*")
			user.save(flush: true, saveOnError: true)
		}
	}
	
	def createSystemJobUser() {
		if(! User.findByUsername(Constants.SYSTEM_JOB_USERNAME)) {
			//def user = new User(username: Constants.SYSTEM_JOB_USERNAME, passwordHash: new Sha256Hash("password").toHex(), isPasswordChangeRequired: false, status: StatusType.ACTIVE, projectAccessLevel: ProjectAccessLevelType.ADMIN, tsApprove: 'N', empReportType: EmployeeReportType.ALL_PROJECTS)
			def user = new User(username: Constants.SYSTEM_JOB_USERNAME, passwordHash: new Sha256Hash("password").toHex(), isPasswordChangeRequired: false, status: StatusType.ACTIVE, tsApprove: 'N', empReportType: EmployeeReportType.ALL_PROJECTS)
			user.addToRoles(Role.findByName(RoleType.ROLE_SYSTEM_JOB))
			user.addToPermissions("*:*")
			user.save(flush: true, saveOnError: true)
		}
	}
	
	def createDefaultDepartments() {
		
		if(Department.count() <= 0) {
			User adminUser = User.findByUsername(Constants.ADMIN_USERNAME)
			
			new Department(code: 'Administration', createdBy: adminUser, updatedBy: adminUser).save(flush: true, saveOnError: true)
			new Department(code: 'Consulting', createdBy: adminUser, updatedBy: adminUser).save(flush: true, saveOnError: true)
			new Department(code: 'Human Resource', createdBy: adminUser, updatedBy: adminUser).save(flush: true, saveOnError: true)
			new Department(code: 'Marketing', createdBy: adminUser, updatedBy: adminUser).save(flush: true, saveOnError: true)
		}
	}
}
