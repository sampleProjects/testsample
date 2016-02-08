package org.solcorp.etech
import org.grails.databinding.BindingFormat;
import java.util.Date;

class UserSession {
	
	/**
	 * Used as a UUID PK
	 */
	Long id
	
	User user
	
	String sessionId
	
	@BindingFormat("MM/dd/yyyy")
	Date startDate
	
	@BindingFormat("MM/dd/yyyy")
	Date endDate
	
	SessionStatusType status
	
    static constraints = {
		user(nullable: false)
		sessionId(nullable: false, blank: false)
		startDate(nullable: false)
		endDate(nullable: true)
		status(nullable: false)
    }
	static mapping = {
		table "app_user_session"		
	}
}
