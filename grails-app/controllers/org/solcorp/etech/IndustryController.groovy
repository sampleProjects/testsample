package org.solcorp.etech

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class IndustryController {
	def industryService
    static allowedMethods = [save: "POST", update: "PUT"]
	
	/**
	 * This action used to refresh create form
	 */
	def clear() {
		def industryInstance
		redirect action : "create", method : "GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to  Create form Reset
	 */
	def newRecord() {
		flash.message =  message(code : 'org.solcorp.etech.industry.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		redirect action : "create", method : "GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to list record from DB by Form parameter
	 */
	def list() {
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = industryService.getIndustryList(params)
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
	
	def create() {
		def industry = new Industry(params)
		if(params?.previousAction.equals("newRecord")) {
			// Add default value here when require
		}
		respond industry
	}
	
	@Transactional
	def save(Industry industryInstance) {
		try {
			if (industryInstance == null) {
				notFound()
				return
			}
			if (industryInstance.hasErrors()) {
				respond industryInstance.errors, view:'create'
				return
			}
			industryService.saveIndustry(industryInstance)
			flash.message =  message(code: 'default.added.success.message', default :"Record added successfully")
			redirect(action: "edit", id: industryInstance.id)
		} catch(Exception e) {
			 flash.message = message(code: 'default.general.error.save.message', null)
			 render(view: "create", model: [industryInstance: industryInstance])
			 return
		 }
	}
	
	def edit(Industry industryInstance) {
		respond industryInstance
	}
	
	@Transactional
	def update(Industry industryInstance) {
		try {
			if (industryInstance == null) {
				notFound()
				return
			}
			if (industryInstance.hasErrors()) {
				respond industryInstance.errors, view:'edit'
				return
			}
			industryService.updateIndustry(industryInstance)
			flash.message = message(code: 'default.updated.success.message', default :"Record updated successfully")
			redirect(action: "edit", id: industryInstance.id)
		} catch(Exception e) {
			log.error "Exception "+e
			flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "edit", model: [industryInstance: industryInstance])
			return
		}
	}
	
	@Transactional
	def delete(Industry industryInstance) {
		try {
			if (industryInstance == null) {
				notFound()
				return
			}
			industryInstance.delete flush:true, failOnError:true
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			redirect action:"create", method:"GET"
			return
		}catch (Exception e) {
			log.error "Exception " + e
			flash.message = message(code: 'default.general.error.delete.message')
		}
		redirect(action: "edit", id: industryInstance.id)
		return
	}

	protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'industryInstance.label', default: 'Industry'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
