package org.solcorp.etech

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional(readOnly = true)
class ProjectCategoryController {
	
	def projectCategoryService
	
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
		flash.message =  message(code: 'etech.projectCategory.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		redirect action:"create", method:"GET", params: [previousAction: actionName]
	}
	
	/**
	 * This action used to search record from DB by Form parameter
	 * @return
	 */
	def list() {
		try {
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = projectCategoryService.getProjectCategoryList(params)
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
		
		def projectCategory = new ProjectCategory(params)
		
		if(params?.previousAction.equals("newRecord")) {
			// Add default value here when require
		}
		
		respond projectCategory
	}

	@Transactional
	def save(ProjectCategory projectCategoryInstance) {
		try {
			if (projectCategoryInstance == null) {
				notFound()
				return
			}
	
			if (projectCategoryInstance.hasErrors()) {
				respond projectCategoryInstance.errors, view:'create'
				return
			}
	
			
			flash.message =  message(code : 'default.added.success.message', default : "Record added successfully")
			projectCategoryInstance.save flush : true
			redirect(action : "edit", id : projectCategoryInstance.id)
			
		} catch(Exception e) {
			 flash.message = message(code: 'default.general.error.save.message', null)
			 
			 render(view: "create", model: [projectCategoryInstance: projectCategoryInstance])
			 return
		 }  
	}

	def edit(ProjectCategory projectCategoryInstance) {
		respond projectCategoryInstance
	}

	@Transactional
	def update(ProjectCategory projectCategoryInstance) {
		try{
			if (projectCategoryInstance == null) {
				notFound()
				return
			}
			if (projectCategoryInstance.hasErrors()) {
				respond projectCategoryInstance.errors, view : 'edit'
				return
			}
			projectCategoryInstance.save flush : true
			flash.message = message(code : 'default.updated.success.message', default : "Record updated successfully")
			redirect(action: "edit", id : projectCategoryInstance.id)
		}catch(Exception e) {
			log.error "Exception "+e
			flash.message = message(code : 'default.general.error.save.message', null)
			render(view : "edit", model : [projectCategoryInstance : projectCategoryInstance])
			return
		}
	}

	@Transactional
	def delete(ProjectCategory projectCategoryInstance) {
		try{
			
			if (projectCategoryInstance == null) {
				notFound()
				return
			}
			
			projectCategoryInstance.delete flush:true, failOnError:true
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			
			redirect action:"create", method:"GET"
			return
			
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
		
			log.error "Exception " + dive
		
			flash.message = message(code: 'org.solcorp.etech.ProjectCategory.delete.child.ref.exists.error')
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: projectCategoryInstance.id)
		return
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'projectCategoryInstance.label', default: 'ProjectCategory'), params.id])
				redirect action: "list", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
