package org.solcorp.etech

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional(readOnly = true)
class AddressController {

    static allowedMethods = [save: "POST", update: "PUT"]
	
	def addressService
	
	/**
	 * This action used to refresh create form
	 * 
	 */
	def clear() {
		redirect (action: "create", params: ['customer.id': params.customer.id, previousAction: actionName], method: "get")
	}
	
	/**
	 * This action used to  Create form Reset
	 */
	def newRecord() {
		flash.message =  message(code : 'etech.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		redirect (action: "create", params: ['customer.id': params.customer.id, previousAction: actionName], method: "get")
	}
	
	def create() {
		respond new Address(params)
    }

    @Transactional
    def save(Address addressInstance) {
       try {
			if (addressInstance == null) {
	            notFound()
	            return
	        }
	
	        if (addressInstance.hasErrors()) {
	            respond addressInstance.errors, view: 'create'
	            return
	        }
	
			addressInstance.addressType = AddressType.SHIPPING
			addressInstance.save flush: true
			flash.message =  message(code: 'default.added.success.message', default: "Record added successfully")
			redirect(action: "edit", id: addressInstance.id)
       } catch(Exception e) {
	   		flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "create", id: addressInstance.id)
			return
	   }
    }

    def edit(Address addressInstance) {
		respond addressInstance
    }

    @Transactional
    def update(Address addressInstance) {
       try {
			 if (addressInstance == null) {
	            notFound()
	            return
	        }
	
	        if (addressInstance.hasErrors()) {
	            respond addressInstance.errors, view: 'edit'
	            return
	        }
	
			addressInstance.addressType = AddressType.SHIPPING
			addressInstance.save flush: true
			flash.message = message(code: 'default.updated.success.message', default: "Record updated successfully.")
			redirect(action: "edit", id: addressInstance.id)
       } catch(Exception e) {
	   		log.error "Exception " + e
			flash.message = message(code: 'default.general.error.save.message', null)
			redirect(action: "edit", id: addressInstance.id)
			return
	   }
    }

    @Transactional
    def delete(Address addressInstance) {
		try {
			
			if (addressInstance == null) {
				notFound()
				return
			}
			
			addressInstance.delete flush: true
			flash.message = message(code: 'default.deleted.success.message', default: "Record deleted successfully")
			redirect(action: "create", params: ['customer.id': addressInstance?.customer?.id])
		}catch (DataIntegrityViolationException e) {
			log.error "Exception " + e
            flash.message = message(code: 'default.delete.child.ref.exists.error')
            redirect(action: "list")
        }
    }
	
	/**
	 * This action used to search record from DB by Form parameter
	 * @return  
	 */
	def list() {
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = addressService.getAddressList(params)
			
			if(request.xhr) {
				
				render(template:"listRecords", model:modelMap)
			} else {
						
				render(view : "list", model : modelMap)
			}
			
			return
		} catch(Exception e) {
			log.error "Exception " + e
		}
	}
	
    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'addressInstance.label', default: 'Address'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

}
