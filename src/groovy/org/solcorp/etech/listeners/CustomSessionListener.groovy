package org.solcorp.etech.listeners
import org.solcorp.etech.SessionStatusType
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

class CustomSessionListener implements HttpSessionListener {

	def userSessionService
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent){
		log.info "New Session created...."
	}
	
	@Override
	void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
	 
		try{			
			userSessionService.updateLoggedOutUserSessionDetail(httpSessionEvent?.session?.userSessionInstance,  SessionStatusType.EXPIRED)
		}catch(Exception e){
			log.error " Error in destroy custom session listerner::"+e
			e.printStackTrace()
		}
	}
}
