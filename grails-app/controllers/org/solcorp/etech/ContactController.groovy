package org.solcorp.etech

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional(readOnly = true)
class ContactController {

    static allowedMethods = [save: "POST", update: "PUT"]
	
	def contactService
	
	/**
	 * This action used to refresh create form
	 * 
	 */
	def clear() {
		redirect(action: "create", params: ['customer.id': params.customer.id, previousAction: actionName], method: "get")
	}
	
	/**
	 * This action used to  Create form Reset
	 */
	def newRecord() {
		flash.message =  message(code : 'etech.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		redirect(action: "create", params: ['customer.id': params.customer.id, previousAction: actionName], method: "get")
	}
	
	def create() {
		respond new Contact(params)
    }

    @Transactional
    def save(Contact contactInstance) {
		
		try {

			if (contactInstance == null) {
	            notFound()
	            return
	        }
	
	        if (contactInstance.hasErrors()) {
	            respond contactInstance.errors, view: 'create'
	            return
	        }
			
			// check for Billing address exist or not
			if(contactInstance.type.equals(ContactType.BILLING)) {
				def contact = Contact.createCriteria().list {
					createAlias("customer", "customer")
					and {
						eq("type", ContactType.BILLING)
						eq("customer.id", contactInstance.customer.id)
					}
				}
				if(contact) {
					log.info("Billing address already exist.")
					flash.message =  message(code: 'contact.billing.add.message', default: "Billing address already exist.")
					redirect(action: "create", id: contactInstance.id, params: ['customer.id': contactInstance.customer.id])
				} else {
					contactInstance.save flush: true
					flash.message =  message(code: 'default.added.success.message', default: "Record added successfully")
					redirect(action: "edit", id: contactInstance.id)
				}
			} else {
					contactInstance.save flush: true
					flash.message =  message(code: 'default.added.success.message', default: "Record added successfully")
					redirect(action: "edit", id: contactInstance.id)
			}
		} catch(Exception e) {
			log.error "Exception " + e
			flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "edit", model: [contactInstance: contactInstance])
			return
		}
    }

    def edit(Contact contactInstance) {
        respond contactInstance
    }

	//@Transactional
    def update(Contact contactInstance) {
      
	try {
		    if (contactInstance == null) {
	            notFound()
	            return
	        }
	
	        if (contactInstance.hasErrors()) {
	            respond contactInstance.errors, view: 'edit'
	            return
	        }

			// check for billing address exist or not
			if(contactInstance.type.equals(ContactType.BILLING)) {
				def contact = Contact.createCriteria().list {
					createAlias("customer", "customer")
					and {
						eq("type", ContactType.BILLING)
						eq("customer.id", contactInstance.customer.id)
					}
				}
				
				if(contact.size == 0 || contact[0]?.id == contactInstance.id) {
					contactInstance.save flush: true
					flash.message = message(code: 'default.updated.success.message', default: "Record updated successfully")
					redirect(action: "edit", id: contactInstance.id)
					return
				} else {
					log.info("Billing address already exist.")
					flash.message =  message(code: 'contact.billing.add.message', default :"Billing address already exist.")
					redirect(action: "edit", id: contactInstance.id, params: ['customer.id': contactInstance.customer.id])
					return
				}
			} else {
					contactInstance.save flush: true
					flash.message = message(code: 'default.updated.success.message', default: "Record updated successfully")
					redirect(action: "edit", id: contactInstance.id)
			}
	   } catch(Exception e) {
		   log.error "Exception " + e
		   flash.message = message(code: 'default.general.error.save.message', null)
		   render(view: "edit", model: [contactInstance: contactInstance])
		   return
	   }
    }

    @Transactional
    def delete(Contact contactInstance) {
		try {
			if (contactInstance == null) {
	            notFound()
	            return
	        }
			contactInstance.delete flush: true
			flash.message = message(code: 'default.deleted.success.message', default: "Record deleted successfully")
			redirect (action: "create", params: ['customer.id': contactInstance?.customer?.id])
		} catch (DataIntegrityViolationException e) {
			log.error "Exception " + e
            flash.message = message(code: 'default.delete.child.ref.exists.error')
            redirect(action: "list")
        }

    }

	/**
	 * This action used to search record from DB by Form parameter
	 * 
	 */
	def list() {		
		try {	
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = contactService.getContactList(params)
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
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contactInstance.label', default: 'Contact'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
