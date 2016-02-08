package org.solcorp.etech



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DepartmentController {

    static allowedMethods = [save: "POST", update: "PUT"]
	
	def departmentService
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Department.list(params), model:[departmentInstanceCount: Department.count()]
    }

	/**
	 * This action used to search record from DB by Form parameter
	 * @return
	 */
    def list() {
		
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = departmentService.getDepartmentList(params)
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
	
	
	
    def create() {
		
		def department = new Department(params)
		
		if(params?.previousAction.equals("newRecord")) {
			// Add default value here when require
		}
		
        respond department
    }

    //@Transactional
    def save() {
		def departmentInstance = null
		
		try {
			def isNewInstance = false
			departmentInstance = Department.read(params?.id)
			
			if(!departmentInstance) {
				isNewInstance = true			
				departmentInstance = new Department()
			}
			departmentInstance.properties = params
			
			departmentInstance.validate()
			if(params?.laborActivityGrouptxt) {
				
				def laborDepartmentInstance = LaborActivityGroup.findByCode(params?.laborActivityGrouptxt)
				if(!laborDepartmentInstance) {
					departmentInstance?.laborActivityGrouptxt = params?.laborActivityGrouptxt
					departmentInstance.errors.rejectValue('laborActivityGroup', 'org.solcorp.etech.Department.labor.department.error.notFound')
				} else {				
					def departmentInstList = Department.findAllByLaborActivityGroup(laborDepartmentInstance).code
					if(departmentInstList?.size() > 0) {									
						if(!departmentInstList.contains(departmentInstance?.code)) {		
							departmentInstance?.laborActivityGrouptxt = params?.laborActivityGrouptxt
							departmentInstance.errors.rejectValue('laborActivityGroup', 'org.solcorp.etech.Department.already.associated.with.another.dept.error')
						}
					}else {
						departmentInstance?.laborActivityGroup = laborDepartmentInstance
					}
				}						
			}
			
	        if (departmentInstance.hasErrors()) {
	            respond departmentInstance.errors, view:'create'
	            return
	        }
	
	        departmentInstance.save flush:true
			departmentService.setPayDepartment()
			if(isNewInstance) {
				flash.message =  message(code: 'default.added.success.message', default :"Record added successfully")
			} else {			
				flash.message = message(code: 'default.updated.success.message', default :"Record updated successfully")
			}
			
			redirect(action: "edit", id: departmentInstance.id)
			
		} catch(Exception e) {
			log.error "Department Save Exception "+e
		   flash.message = message(code: 'default.general.error.save.message', null)
			
			render(view: "create", model: [departmentInstance: departmentInstance])
			return
		}
    }

    def edit(Department departmentInstance) {
		 respond departmentInstance
    }

   
    def update(Department departmentInstance) {
		
		try {
		
			if (departmentInstance == null) {
	            notFound()
	            return
	        }
			
			if(departmentInstance?.laborActivityGroup) {				
				def departmentInstList = Department.findAllByLaborActivityGroup(departmentInstance?.laborActivityGroup).code
				if(departmentInstList?.size() > 0) {									
					if(!departmentInstList.contains(departmentInstance?.code)) {						
						departmentInstance.errors.rejectValue('laborActivityGroup', 'org.solcorp.etech.Department.already.associated.with.another.dept.error')
					}
				}						
			}
			
	        if (departmentInstance.hasErrors()) {
	            respond departmentInstance.errors, view:'edit'
	            return
	        }
			
			
	        departmentInstance.save flush:true
			departmentService.setPayDepartment()
			flash.message = message(code: 'default.updated.success.message', default :"Record updated successfully")
			
		   redirect(action: "edit", id: departmentInstance.id)
		
		   }catch(Exception e) {
			
		   log.error "Exception "+e
			
			flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "edit", model: [departmentInstance: departmentInstance])
			
			return
		}
    }

    @Transactional
    def delete(Department departmentInstance) {
		
		try {
			
			if (departmentInstance == null) {
				notFound()
				return
			}

			departmentInstance.delete flush:true
			
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			redirect action:"list", method:"GET"
			return
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
		
			flash.message = message(code: 'org.solcorp.etech.Department.delete.child.ref.exists.error')
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: departmentInstance.id)
		return
		
		
		
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'departmentInstance.label', default: 'Department'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def getLaborActGroupList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "laborActivityGroupList" , model: departmentService.getLaborActGroupList(params), layout: "ajax")
	}
	
	def getDepartmentDialogList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "departmentCodeList" , model: departmentService.getDepartmentDialogList(params), layout: "ajax")
	}
}
