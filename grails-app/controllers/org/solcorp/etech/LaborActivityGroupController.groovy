package org.solcorp.etech

import org.springframework.dao.DataIntegrityViolationException
import grails.transaction.Transactional
import org.solcorp.etech.LaborActivityGroup


class LaborActivityGroupController {

    static allowedMethods = [save : "POST", update : "PUT"]

	def laborActivityGroupService
	
	/**
	 * This action used to refresh create form
	 */
	def clear() {
		redirect action : "create", method : "GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to  Create form Reset
	 */
	def newRecord() {
		flash.message =  message(code : 'etech.laboractivitygroup.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		redirect action : "create", method : "GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to list record from DB by Form parameter
	 */
	def list() {
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = laborActivityGroupService.getLaborActivityGroupList(params)
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
	 */
    def create() {
        
		def laborActivityGroup = new LaborActivityGroup(params)
		
		if(params?.previousAction.equals("newRecord")) {
			// Add default value here when require
		}
		
		respond laborActivityGroup,  model: [coeList: laborActivityGroupService.getCOEList()] 
    }

	/**
	 * This Action used to delete record From database
	 */
	@Transactional
	def delete(LaborActivityGroup laborActivityGroupInstance) {
		try{
			
			if (laborActivityGroupInstance == null) {
				notFound()
				return
			}
						
			laborActivityGroupInstance.delete flush:true, failOnError:true
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			
			redirect action:"create", method:"GET"
			return
			
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
			log.error "Exception " + dive
			flash.message = message(code: 'org.solcorp.etech.LaborActivityGroup.delete.child.ref.exists.error')
			
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: laborActivityGroupInstance.id)
		return
	}
	
	/**
	 * This Action used to save LaborActivityGroup Data
	 * @param laborActivityGroupInstance
	 */
    @Transactional
    def save(LaborActivityGroup laborActivityGroupInstance) {
		try {
			if (laborActivityGroupInstance == null) {
				notFound()
				return
			}
			if (laborActivityGroupInstance.hasErrors()) {
				respond laborActivityGroupInstance.errors, model : [coeList: laborActivityGroupService.getCOEList()], view : 'create'
				return
			}
			flash.message =  message(code : 'default.added.success.message', default : "Record added successfully")
			laborActivityGroupInstance.save flush : true
			redirect(action : "edit", id : laborActivityGroupInstance.id)
		}catch(Exception e) {
			 flash.message = message(code : 'default.general.error.save.message', null)
			 render(view : "create", model : [laborActivityGroupInstance : laborActivityGroupInstance, coeList: laborActivityGroupService.getCOEList()])
			 return
		 }
    }

	/**
	 * This action render to LaborActivityGroup Edit Screen
	 * @param laborActivityGroupInstance
	 */
    def edit(LaborActivityGroup laborActivityGroupInstance) {
		respond laborActivityGroupInstance, model: [coeList: laborActivityGroupService.getCOEList()]
    }

	/**
	 * This action used to update LaborActivityGroup data
	 * @param laborActivityGroupInstance
	 */
    @Transactional
    def update(LaborActivityGroup laborActivityGroupInstance) {
		try{
			if (laborActivityGroupInstance == null) {
				notFound()
				return
			}
			if (laborActivityGroupInstance.hasErrors()) {
				respond laborActivityGroupInstance.errors, model: [coeList: laborActivityGroupService.getCOEList()], view : 'edit'
				return
			}
			laborActivityGroupInstance.save flush : true
			flash.message = message(code : 'default.updated.success.message', default : "Record updated successfully")
			redirect(action: "edit", id : laborActivityGroupInstance.id)
		}catch(Exception e) {
			log.error "Exception "+e
			flash.message = message(code : 'default.general.error.save.message', null)
			render(view : "edit", model : [laborActivityGroupInstance : laborActivityGroupInstance, coeList: laborActivityGroupService.getCOEList()])
			return
		}
    }
		
	def getList(){		
		def coeCriteria = COE.createCriteria()
		def coeCriteriaList = coeCriteria.list() {
			order("code","asc")
		}
		return coeCriteriaList
	}
	
    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code : 'default.not.found.message', args : [message(code : 'laborActivityGroupInstance.label', default : 'LaborActivityGroup'), params.id])
                redirect action : "list", method : "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
