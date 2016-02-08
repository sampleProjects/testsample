package org.solcorp.etech

import org.solcorp.etech.Employee;
import org.springframework.dao.DataIntegrityViolationException
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

class EmployeeController {

    static allowedMethods = [save: "POST", update: "PUT"]
	
	def employeeService
	def HHEmployeeMasterService
	def userService
	
	/**
	 * This action used to refresh create form
	 * @return
	 */
	def clear() {
		redirect action:"create", method:"GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to  Create form Reset
	 * @return
	 */
	def newRecord() {
		flash.message =  message(code: 'default.etech.insert.new.record.click.label', default : "Enter data to insert new record and click Save") 
		redirect action:"create", method:"GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to search record from DB by Form parameter 
	 * @return
	 */	
	def list() {		
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = employeeService.getEmployeeList(params)		 
			if(request.xhr) {
				
				render(template:"listRecords", model:modelMap)
			} else {
						
				render(view : "list", model : modelMap)
			}			
			return			
		} catch(Exception e) {
			log.error "Exception "+e
		}
	}	
	 
	/**
	 * This action used to Create Method
	 * @return
	 */
    def create() {
		def employeeInstance = new Employee(params)
		
		if(params?.previousAction.equals("newRecord")) {
			employeeInstance.status = StatusType.ACTIVE
		}
		
		respond employeeInstance, model: [supervisorList: employeeService.getSupervisorList()] 
    }
	
	/**
	 * This Action used to save Employee Data
	 * @param employeeInstance
	 * @return
	 */
	@Transactional
	def save(Employee employeeInstance) {
		 try {
			 
			 if (employeeInstance == null) {
				 notFound()
				 return
			 }
			 
			 if (employeeInstance.hasErrors()) {
				 respond employeeInstance.errors, model: [supervisorList: employeeService.getSupervisorList(), laborActCodeList: employeeService.getLaborActCodeList(params?.laborActivityGroup)], view:'create'
				 return
			 }
						 
			 employeeService.saveEmployee(employeeInstance)
			 
			 flash.message =  message(code: 'default.added.success.message', default :"Record added successfully")
			 flash.newUserMessage = true		
			 
			 redirect(action: "edit", id: employeeInstance.id)
			 return
		 } catch(Exception e) {
			 flash.message = message(code: 'default.general.error.save.message', null)			 
			 render(view: "create", model: [employeeInstance: employeeInstance, supervisorList: employeeService.getSupervisorList(), laborActCodeList: employeeService.getLaborActCodeList(params?.laborActivityGroup)])
			 
			 return
		 }
	}
		
	/**
	 * This action render to Employee Edit Screen
	 * @return
	 */
    def edit(Employee employeeInstance) {        
		respond employeeInstance, model: [supervisorList: employeeService.getSupervisorList(), laborActCodeList: employeeService.getLaborActCodeList(employeeInstance?.laborActivityGroup?.id), assignedCustomers: employeeService.getAssignedCustomersList(employeeInstance?.id), assignedProjects: employeeService.getAssignedProjectsList(employeeInstance?.id)]
		return
    }
	 

	/**
	 * This action used to update Employee data
	 * @param employeeInstance
	 * @return
	 */
    @Transactional
    def update(Employee employeeInstance) {		
		try {
			
	        if (employeeInstance == null) {
	            notFound()
	            return
	        }
	
	        if (employeeInstance.hasErrors()) {
	            respond employeeInstance.errors, model: [supervisorList: employeeService.getSupervisorList(), laborActCodeList: employeeService.getLaborActCodeList(params?.laborActivityGroup)], view:'edit'
	            return
	        }
		
		   employeeService.updateEmployee(employeeInstance)		   
		   flash.message = message(code: 'default.updated.success.message', default :"Record updated successfully")
		    
	       redirect(action: "edit", id: employeeInstance.id)
		   
		} catch(Exception e) {
			log.error "Exception "+e
			
			flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "edit", model: [employeeInstance: employeeInstance, supervisorList: employeeService.getSupervisorList(), laborActCodeList: employeeService.getLaborActCodeList(params?.laborActivityGroup)])
			
			return
		}
    }

	/**
	 * This Action used to delete record From database
	 * @return
	 */
	@Transactional
	def delete(Employee employeeInstance){
		try {			
			
			if (employeeInstance == null) {
				notFound()
				return
			}			
			
			employeeInstance.delete flush:true, failOnError:true
			
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			redirect action:"create", method:"GET"
			return
			
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
		
			flash.message = message(code: 'org.solcorp.etech.Employee.delete.child.ref.exists.error')
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: employeeInstance.id)
		return
	}
	 
	def getLaborActGrpList() {	
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "laborActGrpList" , model: employeeService.getLaborActGrpList(params), layout: "ajax")
	}
	def getLaborActCodeList() {		
		render(template:"laborActCodeAjaxCombo", model: [laborActCodeList: employeeService.getLaborActCodeList(params?.laborActivityGroup)])
		
	}
	
	def getEmployeeMstList() {		
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "employeeCodeList" , model: employeeService.getEmployeeMstList(params), layout: "ajax")
	}
	
	
	def getDepartmentList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "departmentCodeList" , model: employeeService.getDepartmentList(params), layout: "ajax")
	}
	
	protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'employeeInstance.label', default: 'Employee'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
