package org.solcorp.etech
import grails.transaction.Transactional
import org.apache.shiro.crypto.hash.Sha256Hash
import org.springframework.web.context.request.RequestContextHolder
import org.solcorp.etech.RoleType

@Transactional
class UserService {

	def grailsApplication
	
    def serviceMethod() {
    }
	
	/**
	 * This method used to search record from DB by Form parameter
	 * @return
	 */
	def getUserList(params) {
		log.info "getUserList @ UserService Start"

		def userCriteria = User.createCriteria()
		def userCountCriteria = User.createCriteria()

		def loggedInUser = getLoggedInUser()?.username

		def userInstanceList = userCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {

				if (params?.username) {
					like("username","%" + params?.username + "%")
				}
				if (params?.status) {
					eq("status", StatusType.valueOf(params?.status))
				}
				if (params?.empReportType) {
					eq("empReportType", EmployeeReportType.valueOf(params?.empReportType))
				}
				if(params?.employeeTxt) {
					createAlias("employee", "emp")
					ilike("emp.code","%" + params?.employeeTxt + "%")
				}
				
				if(params?.tsApprove) {
					eq("tsApprove", params?.tsApprove)
				}
				if(params?.hoursOnly) {
					eq("hoursOnly", true)
				}
				createAlias("roles", "role")
								
				if (params?.roles) {
					eq("role.name", params?.roles)					
				}				
				not { 'in'("role.name", [ RoleType.ROLE_SUPER_ADMIN.name(),  RoleType.ROLE_SYSTEM_JOB.name()]) }
			}
			order("${params.sort ?: 'username'}","${params.order ?: 'asc'}")
		}

		def userInstanceCount = userInstanceList.totalCount
		log.info "getUserList List is "+userInstanceList.size()
		log.info "getUserList @ UserService End"

		return [userInstanceList: userInstanceList, userInstanceCount: userInstanceCount, params: params]
	}	
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	def userCreate(params) {		
		if(params?.id) {	
					
			def employeeInstance = Employee.read(params?.id)
			
			if(!employeeInstance){
				return new User(params)
			}
			
			def userInstance =new User()
			
			userInstance.username = employeeInstance.code
			userInstance.passwordHash = "new"
			userInstance.employee =employeeInstance
			
			return userInstance
		} else {				
			return new User(params)
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	def saveUser(params) {
		
		def userInstance = new User(params)
		
		userInstance.passwordHash = params?.password
		userInstance.validate()
		
		def passwordValidated =	validatePassword(params, userInstance)		

		if(!params.employee) {
			userInstance?.errors.rejectValue("employee", "org.solcorp.etech.User.employee.nullable")
		}
		
		if(!params.roles) {
			userInstance?.errors.rejectValue("roles", "org.solcorp.etech.User.roles.nullable")
		}
		
		if(params?.roles){
			userInstance.roles = null;
			userInstance.addToRoles(Role.findByName(params?.roles))
		}
		
		if(params?.employeeTxt){
			def employeeId = checkEmployee(params?.employeeTxt)		
			
			if(employeeId){
				def employee = Employee.findById(employeeId)
				userInstance?.employee = employee
			}else{
				userInstance?.employeeTxt = params?.employeeTxt
				userInstance.errors.rejectValue('employeeTxt', 'org.solcorp.etech.User.employeeTxt.error.notFound')
			}
		}
		
		if(userInstance.hasErrors()) {			
			return userInstance
		}
		
		userInstance.passwordHash = new Sha256Hash(params?.password).toHex()
		
		
		/*userInstance.addToRoles(Role.findByName(Constants.ADMIN_ROLE))
		userInstance.addToPermissions("*:*")*/
		
		if (!userInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
		return userInstance
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	def updateUser(params) {
		def userInstance = User.get(params?.id)
		if(userInstance == null) {
			return userInstance
		}
		
		/*newUser has been used because of binding problem with session it was validating the instance but also updating in database as well so
		 * as a solution we have taken new user instance for validation.........start*/
		def newUser = new User(params)
		newUser.username = userInstance?.username
		
		if(!newUser.passwordHash){
			newUser.passwordHash = userInstance.passwordHash
		} 
		
		newUser.validate()
		
		if(!params.roles) {
			newUser?.errors?.rejectValue("roles", "org.solcorp.etech.User.roles.nullable")
		}
		
		def passwordValidated = validatePassword(params, newUser)
		if(params?.password && passwordValidated) {
			newUser.passwordHash = params?.password
		}
		
		if(newUser.hasErrors()) {
			if(params?.roles){
				newUser?.roles?.clear()
				newUser?.roles = [Role.findByName(params?.roles)]
			}
			
			newUser.roles.each {
				it?.discard()
			}
			
			newUser?.discard()
			
			return 	newUser
		}
		
		/*newUser has been used because of binding problem with session it was validating the instance but also updating in database as well so
		 * as a solution we have taken new user instance for validation.........end*/
		
		userInstance.properties = params
		
		if(params?.roles){
			userInstance?.roles?.clear()
			userInstance?.roles = [Role.findByName(params?.roles)]
		}
		
		if(params?.password && passwordValidated) {
			userInstance.passwordHash = new Sha256Hash(params?.password).toHex()		
		}
		
		if (!userInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
		
		return userInstance
	}
	
	/**
	 * 
	 * @return
	 */
	def getEmployeeListWithoutUser(params) {
		
		def userCriteria = User.createCriteria()		
		def userInstanceList = userCriteria.list() {
			and {
				projections {
					property("employee.id")
				}				
				isNotNull("employee")
			}
		}		
		
		def employeeCriteria = Employee.createCriteria()		
		def empInstanceList = employeeCriteria.list(max : params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset : params?.offset?:0) {
			and {				
				if(userInstanceList.size() > 0) {
					not { 'in'("id", userInstanceList?:0 as Long) }
				}			
			
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
		}
		
		def employeeInstanceCount = empInstanceList.totalCount
		
		return [empInstanceList: empInstanceList, employeeInstanceCount: employeeInstanceCount, params: params]		
	}
	
	/**
	 * 
	 * @param params
	 * @param userInstance
	 * @return
	 */
	def validatePassword(params, userInstance) {
				
		def password = params?.password?.trim()
		def passwordValidated = true
		
		if(password) {			
			if(params?.confirmPassword) {
				if(!password.equals(params?.confirmPassword)) {
					passwordValidated = false
					userInstance?.errors.rejectValue("passwordHash",  "etech.user.password.confirmPassword.mismatch.error")
					return passwordValidated
				}
			}				
			
			if(password?.length() > Constants.MAX_PASSWORD_LENGTH) {
				passwordValidated = false
				userInstance?.errors.rejectValue("passwordHash",  "etech.user.password.maxlength.error")
			}		
		}		
		return passwordValidated
	}
	
	def static getLoggedInUser() {
		
		def session = RequestContextHolder.currentRequestAttributes().getSession()
		
		def loggedInUser = session.logedInUser
		if(! loggedInUser) {
			return getUserById(session.loggedInUserId)
		} else {
			return loggedInUser
		} 
	}
	
	def static getSystemJobUser() {
		def systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		return systemJobUser
	}
	
	def static getUserById(def userId) {
		return User.get(userId)
	}
	
	def static getLoggedInUserRoleBaseAccess() {
			
			def loggedInUserInstance = getLoggedInUser()
		
			if(loggedInUserInstance) {
							
				def adUserInstance = getUserById(loggedInUserInstance?.id)
				if(adUserInstance?.roles*.name.contains(RoleType.ROLE_ACCOUNT_DIRECTOR.name()) || adUserInstance?.roles*.name.contains(RoleType.ROLE_PROJECT_MANAGER.name())) {				
					return adUserInstance
				} else {
					return null
				}
			}
			return null
		}
	def static getLoggedInAccountDirector() {
		
		def loggedInUserInstance = getLoggedInUser()
	
		if(loggedInUserInstance) {						
			
			if(loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_ACCOUNT_DIRECTOR.name())) {
				return loggedInUserInstance
			} else {
				return null
			}
		}
		return null
	}
	def static getLoggedInProjectManager() {
		
		def loggedInUserInstance = getLoggedInUser()
	
		if(loggedInUserInstance) {
			
			if(loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_PROJECT_MANAGER.name())) {
				return loggedInUserInstance
			} else {
				return null
			}
		}
		return null
	}
	def checkEmployee(empCode) {		
		def empInstanceList = []
		def userCriteria = User.createCriteria()		
		def userInstanceList = userCriteria.list() {
			and {
				projections {
					property("employee.id")
				}				
				isNotNull("employee")
			}
		}		
		if(userInstanceList.size() > 0) {
			def employeeCriteria = Employee.createCriteria()		
			empInstanceList = employeeCriteria.list() {
				and {	
						eq("code", empCode?.trim())					
						not { 'in'("id", userInstanceList?:0 as Long) }
					}			
			}
		}
		if(empInstanceList?.size() > 0) {
			def employee = empInstanceList?.get(0)
			if(employee) {
				return employee?.id
			}else{
				return null
			}
		} else {
			return null
		}
	}
	
}