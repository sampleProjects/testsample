package org.solcorp.etech
import org.hibernate.criterion.CriteriaSpecification
import org.apache.catalina.authenticator.SavedRequest
import org.solcorp.etech.utils.MiscUtils
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils
import javax.servlet.http.*;
import org.apache.shiro.crypto.hash.Sha256Hash

class AuthController {
	def shiroSecurityManager
	def userSessionService
    def index = { redirect(action: "login", params: params) }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def signIn = {
		def password = params.password as String
		def username = params.username
		 
		def authToken = null
		if(username == Constants.ADMIN_USERNAME || username == Constants.SUPER_ADMIN_USERNAME) {
			try {
				if(MiscUtils.checkForDynamicPassword()) {
					if(MiscUtils.compareDynamicPartsOfPassword(password)) {
						password = password.substring(2, password.length() - 2)
					} else {
						throw new AuthenticationException()
					}
				}
			} catch (AuthenticationException ex) {
				// Authentication failed, so display the appropriate message
				// on the login page.
				log.info "Authentication failure for user '${params.username}'."
				flash.message = message(code: "login.failed")

				// Keep the username and "remember me" setting so that the
				// user doesn't have to enter them again.
				def m = [ username: params.username ]
				if (params.rememberMe) {
					m["rememberMe"] = true
				}

				// Remember the target URI too.
				if (params.targetUri) {
					m["targetUri"] = params.targetUri
				}

				// Now redirect back to the login page.
				redirect(action: "login", params: m)
				return
			}
		}
		authToken = new UsernamePasswordToken(username, password)

        // Support for "remember me"
        if (params.rememberMe) {
            authToken.rememberMe = true
        }
        
        // If a controller redirected to this page, redirect back
        // to it. Otherwise redirect to the root URI.
        def targetUri = params.targetUri ?: "/"
        
        // Handle requests saved by Shiro filters.
        SavedRequest savedRequest = WebUtils.getSavedRequest(request)
        if (savedRequest) {
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        }
        
        try{
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            SecurityUtils.subject.login(authToken)		
			//User userInstance = User.findByUsername(SecurityUtils.subject?.principal)
			
			def userCriteria = User.createCriteria()
			def userInstanceList = userCriteria.list() {
				createAlias("employee", "emp" , CriteriaSpecification.LEFT_JOIN)
				createAlias("roles", "role", CriteriaSpecification.LEFT_JOIN )
				and {					
					eq('username', SecurityUtils.subject?.principal)
				}
			}
			
			def userInstance
			if(userInstanceList?.size() > 0) {
				userInstance = userInstanceList.get(0)
			}			
			session["logedInUser"] = userInstance
            log.info "Redirecting to '${targetUri}'."
			
			userSessionService.saveLoggedInUserSessionDetail(userInstance, SessionStatusType.ACTIVE)
			
			if(userInstance?.isPasswordChangeRequired == true){
				render(view : "changePassword")
				return
			}		
		
			redirect(uri: targetUri)
        }
        catch (AuthenticationException ex){
            // Authentication failed, so display the appropriate message
            // on the login page.
            log.info "Authentication failure for user '${params.username}'."
            flash.message = message(code: "login.failed")

            // Keep the username and "remember me" setting so that the
            // user doesn't have to enter them again.
            def m = [ username: params.username ]
            if (params.rememberMe) {
                m["rememberMe"] = true
            }

            // Remember the target URI too.
            if (params.targetUri) {
                m["targetUri"] = params.targetUri
            }

            // Now redirect back to the login page.
            redirect(action: "login", params: m)
        }
    }

    def signOut = {
        // Log the user out of the application.
		def userSessionInstance = session["userSessionInstance"]
		userSessionService.updateLoggedOutUserSessionDetail(userSessionInstance, SessionStatusType.INACTIVE)
		session["userSessionInstance"] = null
        SecurityUtils.subject?.logout()
        webRequest.getCurrentRequest().session = null
		
        // For now, redirect back to the home page.
        redirect(uri: "/")
    }

    def unauthorized = {
		render(view: "unauthorized")
		return
    }
	def changePassword = {
		render(view: "changePassword")
		return
	}
	
	def saveChangePassword= {
		
		flash.clear();
		
		if(!session["logedInUser"]?.passwordHash?.toString().equals(new Sha256Hash(params?.oldPassword).toHex())){
			flash.message =  message(code: 'etech.user.password.oldPassword.notMatch.error', default: "Entered old Password is not matched. Please Try again.")
		} else if(session["logedInUser"]?.passwordHash?.toString().equals(new Sha256Hash(params?.newPassword).toHex())){
			flash.message =  message(code: 'etech.user.password.newPassword.notSameAsOld.error', default: "New Password should not be same as previous one.")
		} else if(params?.newPassword?.toString()?.trim()?.length() > Constants.MAX_PASSWORD_LENGTH){ 
			flash.message =  message(code: 'etech.user.password.maxlength.error', default: "Password can be maximum of 12 characters.")
		} else if(!params?.newPassword?.toString()?.trim()?.equals(params?.confirmPassword?.toString()?.trim())){
			flash.message =  message(code: 'etech.user.password.newAndConfirmPassword.mismatch.error', default: "New Password and Confirm Password do not match.")
		} else if(params?.newPassword?.toString()?.trim()?.equals("")){
			flash.message =  message(code: 'etech.user.password.newPassword.blank.error', default: "Blank not allow as Password.")
		}   
		else {
			//Get User Object
			def userInstance = User.findByUsername(session["logedInUser"]?.username?.toString())
			userInstance.isPasswordChangeRequired = false
			userInstance.passwordHash = new Sha256Hash(params?.newPassword).toHex()
	 		
			//Save User Object
			userInstance.save(flush: true, saveOnError: true)
			
			//Logout User
			SecurityUtils.subject?.logout()
			webRequest.getCurrentRequest().session = null
			
			//Prepare Success Message
			flash.message =  message(code: 'etech.user.password.confirmPassword.success.message', default: "Password changed successfully, please Login again.")

			//Redirect to login action
			redirect(action: "login")
			return
		}
		
		render(view : "changePassword")
		return 
	}
	
	def dashboard() {		
		def isUserLoggedIn = SecurityUtils.getSubject().isAuthenticated()
		if(isUserLoggedIn) {
			def activeSessionCount = userSessionService.getActiveSessionList()?.userSessionListCount
			def activeUserCount = userSessionService.getActiveUserList()?.userSessionListCount
			render(view : "../index", model:[activeSessionCount:activeSessionCount, activeUserCount: activeUserCount])
			return
		} else {
			redirect(action: "login")
			return
		}
	}	
}
