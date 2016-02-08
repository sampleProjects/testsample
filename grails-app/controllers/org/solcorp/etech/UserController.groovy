package org.solcorp.etech


import org.solcorp.etech.Employee;
import org.springframework.dao.DataIntegrityViolationException

import grails.transaction.Transactional
/**
 * 
 * @author hjjogiya
 *
 */
class UserController {

    static allowedMethods = [save: "POST", update: "PUT"]
	
	def userService
	 
	/**
	 * This action used to refresh create form
	 * @return
	 */
	def clear() {
		redirect action:"create", method: "GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to  Create form Reset
	 * @return
	 */
	def newRecord() {		
		flash.message =  message(code: 'default.etech.insert.new.record.click.label', default: "Enter data to insert new record and click Save") 
		redirect action:"create", method: "GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to search record from DB by Form parameter
	 * render search.gsp page 
	 * @return
	 */	
	def list() {		
		try {
			
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = userService.getUserList(params)			
			if(request.xhr) {
				
				render(template:"listRecords", model:modelMap)
			} else {
						
				render(view : "list", model : modelMap)
			}			
			
			return			
		} catch(Exception e) {
			log.error "User Search Exception "+e
		}
	}	
	 
	/**
	 * This action used to render create.gsp page 
	 * @return
	 */
    def create() {
		def userInstance = userService.userCreate(params)		
		
		if(params?.previousAction.equals("newRecord")) {
			userInstance.status = StatusType.ACTIVE
			userInstance.tsApprove = 'N'
			userInstance.empReportType = EmployeeReportType.ALL_PROJECTS
			userInstance.isPasswordChangeRequired = true
		}
		
        respond userInstance , model: [employeeListWithoutUser: userService.getEmployeeListWithoutUser()]
    }
	
	/**
	 * This action used to save User Data, 
	 * On success render edit.gsp page 
	 * @param employeeInstance
	 * @return
	 */
	
	def save() {		
		def userInstance		
		 try {
			 userInstance = userService.saveUser(params)
			 
			 if (userInstance.hasErrors()) {
				 respond userInstance.errors, model: [employeeListWithoutUser: userService.getEmployeeListWithoutUser()], view:'create'
				 return
			 }
			 
			 flash.message =  message(code: 'default.added.success.message', default: "Record added successfully")			 
			 redirect(action: "edit", id: userInstance.id)
		 } catch(Exception e) {
		 	log.error "User Save Exception "+e
			flash.message = message(code: 'default.general.error.save.message', null)
			 
			 render(view: "create", model: [userInstance: userInstance, employeeListWithoutUser: userService.getEmployeeListWithoutUser()])
			 return
		 }
	}
	
	/**
	 * This action render to User edit.gsp page
	 * @return
	 */
	def edit(User userInstance) {
		respond userInstance,  model: [employeeListWithoutUser: userService.getEmployeeListWithoutUser()]		
	}
	
	/**
	 * This action used to update User data 
	 * @param userInstance
	 * @return
	 */
	
	def update() {
		def userInstance
		try {
			userInstance = userService.updateUser(params)
			
			if (userInstance == null) {
				notFound()
				return
			}
	
			if (userInstance.hasErrors()) {
				respond userInstance.errors, view:'edit'
				return
			} 
		   
		   flash.message = message(code: 'default.updated.success.message', default: "Record updated successfully")
		   
		   redirect(action: "edit", id: userInstance.id)
		   
		} catch(Exception e) {
			log.error("User Update Exception "+e)
			
			flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "edit", model: [userInstance: userInstance])
			
			return
		}
	}
	
	def getEmployeeListWithoutUser(){
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "ajaxEmployeeList" , model: userService.getEmployeeListWithoutUser(params), layout: "ajax")
	}
	
	def checkEmployee(){
		render(userService.checkEmployee(params?.employeeCode))
	}
	
	protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userInstance.label', default: 'User'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
