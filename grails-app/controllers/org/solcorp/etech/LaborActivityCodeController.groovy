package org.solcorp.etech

import org.springframework.dao.DataIntegrityViolationException
import grails.transaction.Transactional
import org.solcorp.etech.LaborActivityCode
import org.solcorp.etech.LaborActivityGroup


class LaborActivityCodeController {

    static allowedMethods = [save : "POST", update : "PUT"]
	
	def laborActivityCodeService

	/**
	 * This action used to list record from DB by Form parameter
	 */
	def list() {
		try{
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = laborActivityCodeService.getLaborActivityCodeList(params)
				if(request.xhr) {
				
				render(template:"listRecords", model:modelMap)
			} else {
						
				render(view : "list", model : modelMap)
			}
			return
		}catch(Exception e) {
			log.error "Exception "+e
		}
	}
	
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
		flash.message =  message(code : 'etech.laboractivitycode.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		redirect action : "create", method : "GET", params: [previousAction: actionName]
	}

	/**
	 * This action used to Create Method
	 */
    def create() {
        
		def laborActivityCode = new LaborActivityCode(params)
		
		if(params?.previousAction.equals("newRecord")) {
			laborActivityCode.standardRate = 0.00
			laborActivityCode.overHead = 0.00
			laborActivityCode.billRate = 0.00
			
			laborActivityCode.qcFlag = 'N'
			laborActivityCode.countPoint = 'N'
			laborActivityCode.operations = 'N'
			laborActivityCode.active = 'Y'
		}
		
		respond laborActivityCode, model: [activityDepartmentList: laborActivityCodeService.getAllLaborActivityDepartment()]
    }

	
	/**
	 * This Action used to save LaborActivityCode Data
	 * @param laborActivityCodeInstance
	 */
    @Transactional
    def save(LaborActivityCode laborActivityCodeInstance) {
		try{
			if (laborActivityCodeInstance == null) {
				notFound()
				return
			}
			if (laborActivityCodeInstance.hasErrors()) {
				respond laborActivityCodeInstance.errors, view : 'create', model: [activityDepartmentList: laborActivityCodeService.getAllLaborActivityDepartment()]
				return
			}
			flash.message =  message(code : 'default.added.success.message', default : "Record added successfully")
			LaborActivityGroup laborActivityGroupInstance = LaborActivityGroup.read(params?.laborActivityGroup?.id)
			laborActivityCodeInstance.laborActivityGroup = laborActivityGroupInstance
			
			laborActivityCodeInstance.save  flush:true, failOnError:true
			
			redirect(action: "edit", id : laborActivityCodeInstance.id)
		}catch(Exception e) {
				
			 flash.message = message(code : 'default.general.error.save.message', null)
			 render(view : "create", model : [laborActivityCodeInstance : laborActivityCodeInstance, activityDepartmentList: laborActivityCodeService.getAllLaborActivityDepartment()])
			 return
		 }
    }

	/**
	 * This action render to LaborActivityCode Edit Screen
	 */
    def edit(LaborActivityCode laborActivityCodeInstance) {
		respond laborActivityCodeInstance, model: [activityDepartmentList: laborActivityCodeService.getAllLaborActivityDepartment()]
    }

    @Transactional
    def update(LaborActivityCode laborActivityCodeInstance) {
		try{
			if (laborActivityCodeInstance == null) {
				notFound()
				return
			}
			if (laborActivityCodeInstance.hasErrors()) {
				respond laborActivityCodeInstance.errors, view:'edit', model: [activityDepartmentList: laborActivityCodeService.getAllLaborActivityDepartment()]
				return
			}
			LaborActivityGroup laborActivityGroupInstance = LaborActivityGroup.read(params?.laborActivityGroup?.id)
			laborActivityCodeInstance.laborActivityGroup = laborActivityGroupInstance
			laborActivityCodeInstance.save flush : true
			flash.message = message(code : 'default.updated.success.message', default : "Record updated successfully")
			redirect(action : "edit", id : laborActivityCodeInstance.id)
		}catch(Exception e) {
			log.error "Exception "+e
			flash.message = message(code : 'default.general.error.save.message', null)
			render(view : "edit", model : [laborActivityCodeInstance : laborActivityCodeInstance, activityDepartmentList: laborActivityCodeService.getAllLaborActivityDepartment()])
			return
		}
        
    }

	/**
	 * This Action used to delete record From database
	 */
    @Transactional
    def delete(LaborActivityCode laborActivityCodeInstance) {
		try{
			if (laborActivityCodeInstance == null) {
				notFound()
				return
			}
			
			laborActivityCodeInstance.delete flush:true, failOnError:true
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			
			redirect action:"create", method:"GET"
			return
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
		
			log.error "Exception " + dive
            flash.message = message(code: 'org.solcorp.etech.LaborActivityCode.delete.child.ref.exists.error')             
        } catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: laborActivityCodeInstance.id)
		return
    }

	def getList(){
		def laborActivityGroup = LaborActivityGroup.findAll()
		return laborActivityGroup			
	}
	
    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code : 'default.not.found.message', args : [message(code : 'laborActivityCodeInstance.label', default : 'LaborActivityCode'), params.id])
                redirect action : "list", method : "GET"
            }
            '*'{ render status : NOT_FOUND }
        }
    }
}
