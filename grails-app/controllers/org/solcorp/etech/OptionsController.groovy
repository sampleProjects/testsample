package org.solcorp.etech



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OptionsController {

    static allowedMethods = [save: "POST", update: "PUT" ]
	
    def edit() {
	
		def optionsInstance = Options.find("FROM Options")
		if(optionsInstance) {
			respond optionsInstance
		} else { 
			respond new Options()
		}
			
    }

    @Transactional
    def update(Options optionsInstance) {
      try {
			
		  if (optionsInstance == null) {
	            notFound()
	            return
	       }
	
	       if (optionsInstance.hasErrors()) {
	            respond optionsInstance.errors, view:'edit'
	            return
	       }
	
	       optionsInstance.save flush: true
		   flash.message = message(code: 'default.updated.success.message', default: "Record updated successfully")
		   redirect(action: "edit", id: optionsInstance.id)
		   
      } catch(Exception e) {
	 		
	  		log.error "Exception While updating Options " + e
			flash.message = message(code: 'default.general.error.save.message', null)
			render(view: "edit", model: [optionsInstance: optionsInstance])
			return
	  }
    }
	
    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'optionsInstance.label', default: 'Options'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
