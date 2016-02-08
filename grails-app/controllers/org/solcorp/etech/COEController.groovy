package org.solcorp.etech
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
/**
 * 
 * @author hjjogiya
 *
 */
@Transactional(readOnly = true)
class COEController {

	def COEService
	
	static allowedMethods = [save: "POST", update: "PUT"]

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
			def modelMap = COEService.getCOEList(params)
			if(request.xhr) {
								
				render(template:"listRecords", model:modelMap)
			} else {
			
				render(view: "list", model: modelMap)
			}			
			return
		} catch(Exception e) {
			log.error "Exception "+e
		}
	}

    def create() {		
        
		def coe = new COE(params)
		
		if(params?.previousAction.equals("newRecord")) {
			// Add default value here when require	
		}
		
		respond coe
    }

    @Transactional
    def save(COE COEInstance) {
		try {
	        if (COEInstance == null) {
	            notFound()
	            return
	        }
	
	        if (COEInstance.hasErrors()) {
	            respond COEInstance.errors, view:'create'
	            return
	        }
			COEService.saveCOE(COEInstance)
	        flash.message =  message(code: 'default.added.success.message', default :"Record added successfully")
			redirect(action: "edit", id: COEInstance.id)
			
		} catch(Exception e) {
			 flash.message = message(code: 'default.general.error.save.message', null)			 
			 render(view: "create", model: [COEInstance: COEInstance])
			 
			 return
		 }
    }

    def edit(COE COEInstance) {
        respond COEInstance
    }

    @Transactional
    def update(COE COEInstance) {
        try {
			if (COEInstance == null) {
	            notFound()
	            return
	        }
	
	        if (COEInstance.hasErrors()) {
	            respond COEInstance.errors, view:'edit'
	            return
	        }

			COEService.updateCOE(COEInstance)

	        flash.message = message(code: 'default.updated.success.message', default :"Record updated successfully")	    
		    redirect(action: "edit", id: COEInstance.id)
			
		} catch(Exception e) {
			log.error "Exception "+e
			
			flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "edit", model: [COEInstance: COEInstance])
		
			return
		}
    }

    @Transactional
    def delete(COE COEInstance) {
		try {
	        if (COEInstance == null) {
	            notFound()
	            return
	        }

			COEInstance.delete flush:true, failOnError:true
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			
			redirect action:"create", method:"GET"
			return
			
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
		
			flash.message = message(code: 'org.solcorp.etech.COE.delete.child.ref.exists.error')
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: COEInstance.id)
		return
    }
	
    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'COEInstance.label', default: 'COE'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
