package org.solcorp.etech
import grails.transaction.Transactional
import org.springframework.web.context.request.RequestContextHolder
/**
 * 
 * @author hjjogiya
 *
 */

@Transactional
class UserSessionService {
	
	def userService
    def serviceMethod() {

    }
	
	/**
	 * This method used to save logged in User Details 
	 * @param userInstance
	 * @param status
	 * @return
	 */
	def saveLoggedInUserSessionDetail(userInstance, status) {
		log.info "UserSessionService @ saveUserSessionDetails Start"
		try {
			def session = RequestContextHolder.currentRequestAttributes().getSession()
			def sessionId = RequestContextHolder.getRequestAttributes()?.getSessionId()
			
			def userSessionInstance = new  UserSession()
			userSessionInstance?.user =userInstance
			userSessionInstance?.sessionId = sessionId
			userSessionInstance?.startDate = new Date()			
			userSessionInstance?.status = status
			userSessionInstance.save(flush: true, saveOnError: true)
			session["userSessionInstance"] =  userSessionInstance
		} catch(Exception e) {
			log.error "User Session Error "+e
		}
		log.info "UserSessionService @ saveUserSessionDetails End"
	}
	 
	/**
	 * This method is call when user logged out or session expired  
	 * @param userSessionInstance
	 * @param status
	 * @return
	 */
	def updateLoggedOutUserSessionDetail(userSessionInstance, status) {
		log.info "UserSessionService @ saveLoggedOutUserSessionDetail Start"
		try {
			if(userSessionInstance) {
				userSessionInstance?.status = status
				userSessionInstance.endDate = new Date()
				userSessionInstance.save()
			}
		}catch(Exception e) {
			log.error "Exception @ saveLoggedOutUserSessionDetail "+e
		}	
		log.info "UserSessionService @ saveLoggedOutUserSessionDetail End"
	}
	
	/**
	 * This method used to update all the user session details when server restart
	 * @return
	 */
	def updateAllUserSessionInstance() {
		log.info "UserSessionService @ updateAllUserSessionInstance Start"
		try {
			def userSessionCriteria = UserSession.createCriteria()
			def userSessionList = userSessionCriteria.list(){
				eq("status", SessionStatusType.ACTIVE)
				isNull("endDate")
			}
			userSessionList.each { userInstance ->
				updateLoggedOutUserSessionDetail(userInstance, SessionStatusType.EXPIRED_ON_RESET)
			}
		}catch(Exception e) {
			log.error "Exception @ updateAllUserSessionInstance "+e
		}		
		log.info "UserSessionService @ updateAllUserSessionInstance End"
	}
	
	/**
	 * This method used to get All Active Session List
	 * @return
	 */
	def getActiveSessionList(params) {
		log.info "UserSessionService @ getActiveSessionList Start"
		def userSessionList = []
		try{
			def loggedInUserInstance = userService.getLoggedInUser()
			def userSessionCriteria = UserSession.createCriteria()
			userSessionList = userSessionCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
				createAlias("user","user")
				and {
					eq("status", SessionStatusType.ACTIVE)
				
					if(loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_ADMIN.name()) || 
						loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_SUPER_ADMIN.name())){
						not { 'in'("user", loggedInUserInstance) }
					}			
				}			
			}
		}catch(Exception e) {
			log.error "UserSessionService @ getActiveSessionList Exception "+e
		}
		log.info "UserSessionService @ getActiveSessionList End"
		return [userSessionList: userSessionList, userSessionListCount: userSessionList.totalCount, params: params]
	}

	/**
	 * This method used to get All Active User List
	 * @return
	 */
	def getActiveUserList(params) {
		log.info "UserSessionService @ getActiveUserList Start"
		def userSessionList = []
		try {
			def loggedInUserInstance = userService.getLoggedInUser()
			def userSessionCriteria = UserSession.createCriteria()
			userSessionList = userSessionCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
				and {
					projections {
						groupProperty('user')
						groupProperty('startDate')
					}				
					if(loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_ADMIN.name()) ||
						loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_SUPER_ADMIN.name())){
						not { 'in'("user", loggedInUserInstance) }
					}
					eq("status", SessionStatusType.ACTIVE)
				}				
			}			
		}catch(Exception e) {
			log.error "UserSessionService @ getActiveSessionList Exception "+e
		}
		log.info "UserSessionService @ getActiveUserList End"
		return [userSessionList: userSessionList, userSessionListCount: userSessionList.totalCount, params: params]		
	}
}
