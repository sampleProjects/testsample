package org.solcorp.etech

import org.apache.shiro.SecurityUtils

/**
 * Generated by the Shiro plugin. This filters class protects all URLs
 * via access control by convention.
 */
class SecurityFilters {
	def filters = {
        all(uri: "/**") {
            before = {
				
				if(session["logedInUser"]?.isPasswordChangeRequired == true && controllerName!= 'auth') {
					redirect(controller: 'auth', action : 'changePassword')
					return
				}
				
				// Ignore direct views (e.g. the default main index page).
				if (!controllerName) return true
				
				if((controllerName == "roleMaintenance" || controllerName == "options" || controllerName == "customQuartz") 
					&& ! SecurityUtils.subject.hasRole(RoleType.ROLE_SUPER_ADMIN.name()))  {
					redirect(controller: 'auth', action: 'unauthorized')
					return false
				}
				
				// Access control by convention.
				accessControl()
            }
        }
    }
}
