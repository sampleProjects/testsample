package org.solcorp.etech

class UserSessionController {

	def userSessionService
	
    def index() { }
	
	
	def activeSessionsList() {
		
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = userSessionService.getActiveSessionList(params)
			if(request.xhr) {
				render(template:"activeSessionsListRecords", model:modelMap)
			} else {
				render(view : "activeSessionsList", model : modelMap)
			}
			return
		} catch(Exception e) {
			log.error "Exception " + e
		}
		
	}
	
	def activeUsersList() {
		
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = userSessionService.getActiveUserList(params)
			if(request.xhr) {
				render(template:"activeUsersListRecords", model:modelMap)
			} else {
				render(view : "activeUsersList", model : modelMap)
			}
			return
		} catch(Exception e) {
			log.error "Exception " + e
		}
		
	}
	
}
