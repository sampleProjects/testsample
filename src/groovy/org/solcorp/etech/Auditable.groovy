package org.solcorp.etech

import java.text.SimpleDateFormat

import javax.persistence.Transient

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsHttpSession
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.util.WebUtils

abstract class Auditable implements Serializable {
	
	Date dateCreated
	Date lastUpdated
	User createdBy
	User updatedBy
	
	def userService
	
	@Transient
	def dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	/**
	 * This method returns Grails Http Session
	 * @return Grails HTTP Session
	 */
	protected GrailsHttpSession getSession() {
		GrailsWebRequest request = WebUtils.retrieveGrailsWebRequest()
		GrailsHttpSession session = request.session
		return session
	}
	
	static constraints = {
		createdBy (nullable: true, display: false)
		dateCreated (nullable: true, display: false)
		updatedBy (nullable: true, display: false)
		lastUpdated  (nullable: true, display: false)
	}
	
	def beforeInsert = {
		if(!createdBy){
			try {
				if(getSession())
					createdBy = userService.getLoggedInUser()
			} catch(Exception e) {
				createdBy = null
			}
			updatedBy = createdBy
		}
	}
	
	def beforeUpdate = {
		try {
			if(getSession()){
				updatedBy = userService.getLoggedInUser()
			}
		} catch(Exception e) {
			//updatedBy = null
		}
	}
}