package org.solcorp.etech

import grails.transaction.Transactional;

class ProductCategoryController {

	static allowedMethods = [save : "POST", update : "PUT"]
	
	def productCategoryService
	def sessionFactory
	
    def clear() { 
		if(params?.productType){
			redirect action : "create", method : "GET", params:[productType: "all", previousAction: actionName]
		}else{
			redirect action : "create", method : "GET", params:[productClassType: params?.productClassType, previousAction: actionName]
		}
	}
	
	def newRecord() {
		flash.message =  message(code : 'etech.productCategory.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		if(params?.productType){
			redirect action : "create", method : "GET", params:[productType: "all", previousAction: actionName]
		}else{
			redirect action : "create", method : "GET", params:[productClassType: params?.productClassType, previousAction: actionName]
		}
	}
	
	def create() {
		def productCategoryInstance = null
		def productType = null
		params.put("disabledField","false")
		if(params?.productClassType == "PROJECT") {
			params.put("disabledField","true")
			productCategoryInstance =  new ProjectProductCategory(params)
			productCategoryInstance.productClassType = ProductClassType.PROJECT
		}else if(params?.productClassType == "MISCELLANEOUS") {
			params.put("disabledField","true")
			productCategoryInstance =  new MiscellaneousCategory(params)
			productCategoryInstance.productClassType = ProductClassType.MISCELLANEOUS
		}else if(params?.productClassType == "EXPENSES") {
			params.put("disabledField","true")
			productCategoryInstance =  new ExpenseCategory(params)
			productCategoryInstance.productClassType = ProductClassType.EXPENSES
		}else if(params?.productClassType == "SERVICES") {
			params.put("disabledField","true")
			productCategoryInstance =  new ServiceCategory(params)
			productCategoryInstance.productClassType = ProductClassType.SERVICES
		}else{
			productCategoryInstance =  new ProductCategory(params)
		}
		render(view : "create", model: [productCategoryInstance: productCategoryInstance])
	}
	
	@Transactional
	def save(ProductCategory productCategoryInstance) {
		Long productCategoryIntanceId = null
		params.put("disabledField",params?.disabledField)
		
		try {
			if (productCategoryInstance == null) {
				notFound()
				return
			}
			
			if(params?.productClassType == "PROJECT"){
				ProjectProductCategory projectProductCategoryInstance = new ProjectProductCategory(params)
				productCategoryInstance.validate()
				if (productCategoryInstance.hasErrors()) {
					projectProductCategoryInstance.validate()
					render(view : 'create', model: [productCategoryInstance: projectProductCategoryInstance, productType: params?.productType])
					return
				}else{
					projectProductCategoryInstance.save flush: true
					productCategoryIntanceId = projectProductCategoryInstance?.id
				}
			}else if(params?.productClassType == "MISCELLANEOUS"){
				MiscellaneousCategory miscellaneousCategoryInstance = new MiscellaneousCategory(params)
				productCategoryInstance.validate()
				if (productCategoryInstance.hasErrors()) {
					miscellaneousCategoryInstance.validate()
					render(view : 'create', model: [productCategoryInstance: miscellaneousCategoryInstance, productType: params?.productType])
					return
				}else{
					miscellaneousCategoryInstance.save flush: true
					productCategoryIntanceId = miscellaneousCategoryInstance?.id
				}
			}else if(params?.productClassType == "EXPENSES"){
				ExpenseCategory expenseCategoryInstance = new ExpenseCategory(params)
				productCategoryInstance.validate()
				if (productCategoryInstance.hasErrors()) {
					expenseCategoryInstance.validate()
					render(view : 'create', model: [productCategoryInstance: expenseCategoryInstance, productType: params?.productType])
					return
				}else{
					expenseCategoryInstance.save flush: true
					productCategoryIntanceId = expenseCategoryInstance?.id
				}
			}else if(params?.productClassType == "SERVICES"){
				ServiceCategory serviceCategoryInstance = new ServiceCategory(params)
				productCategoryInstance.validate()
				if (productCategoryInstance.hasErrors()) {
					serviceCategoryInstance.validate()
					render(view : 'create', model: [productCategoryInstance: serviceCategoryInstance, productType: params?.productType])
					return
				}else{
					serviceCategoryInstance.save flush: true
					productCategoryIntanceId = serviceCategoryInstance?.id
				}
			}
			flash.message =  message(code : 'default.added.success.message', default : "Record added successfully")
			chain(action : "edit", id : productCategoryIntanceId, model:[productType: params?.productType])
		}catch(Exception e) {
			flash.message = message(code : 'default.general.error.save.message', null)
			render(view : "create", model : [productCategoryInstance : productCategoryInstance, productType: params?.productType])
			return
		 }
	}
	
	def edit() {
		ProductCategory productCategoryInstance =  ProductCategory.findById(params?.id as Long)
		def productType = flash?.chainModel?.productType?:params?.productType
		
		if(productCategoryInstance instanceof ProjectProductCategory){
			productCategoryInstance.productClassType = ProductClassType.PROJECT
		} else if (productCategoryInstance instanceof MiscellaneousCategory) {
			productCategoryInstance.productClassType = ProductClassType.MISCELLANEOUS
		} else if (productCategoryInstance instanceof ExpenseCategory) {
			productCategoryInstance.productClassType = ProductClassType.EXPENSES
		} else if (productCategoryInstance instanceof ServiceCategory) {
			productCategoryInstance.productClassType = ProductClassType.SERVICES
		}
		render(view : "edit", model : [productCategoryInstance: productCategoryInstance, productType: productType])
	}
	
	@Transactional
	def delete(ProductCategory productCategoryInstance) {
		def classTypeMessage = null
		def tempProductClassType = productCategoryInstance?.productClassType
		try{
			if (productCategoryInstance == null) {
				notFound()
				return
			}
			
			if(productCategoryInstance instanceof ProjectProductCategory){
				classTypeMessage =  message(code: 'org.solcorp.etech.ProductCategory.delete.child.ref.exists.error')
				
			} else if (productCategoryInstance instanceof ServiceCategory) {
				classTypeMessage =  message(code: 'org.solcorp.etech.ProductCategory.service.delete.child.ref.exists.error')
							
			} else if (productCategoryInstance instanceof ExpenseCategory) {
				classTypeMessage =  message(code: 'org.solcorp.etech.ProductCategory.expense.delete.child.ref.exists.error')
							
			} else if (productCategoryInstance instanceof MiscellaneousCategory) {
				classTypeMessage =  message(code: 'org.solcorp.etech.ProductCategory.delete.child.ref.exists.error')
				
			} else {
				classTypeMessage =  message(code: 'default.delete.child.ref.exists.error')
			}

			productCategoryInstance.delete flush:true, failOnError:true
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			if(params?.productType == 'all'){
				redirect action:"create", method:"GET", params: [productType: params?.productType]
				return
			} else {
				redirect action:"create", method:"GET", params: [productClassType: tempProductClassType?.toString()?.trim()?.toUpperCase()]
				return
			}
			
		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
			log.error "Exception " + dive
			flash.message = classTypeMessage
			
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: productCategoryInstance.id, fromCategory: 'yes')
		return
	}
	
	def list() {
		try{
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = productCategoryService.getProductCategoryList(params)
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
	
	@Transactional
	def update() {
		ProductCategory productCategoryInstance = ProductCategory.get(params?.productCategoryInstanceId)
		Long productCategoryIntanceId = null
		try{
			if (productCategoryInstance == null) {
				notFound()
				return
			}
			
			if(params.containsKey('category')){
				productCategoryInstance.category = params?.category
			}else{
				productCategoryInstance.category = params?.category?:params?.productCategoryInstanceCategory
			}
			productCategoryInstance.description = params?.description
			
			productCategoryInstance.validate()
			if (productCategoryInstance.hasErrors()) {
				productCategoryInstance.discard()
				render(view : "edit", model : [productCategoryInstance : productCategoryInstance, productType: params?.productType])
				return
			}else{
				productCategoryInstance.save(flush: true, failOnError: true)
				flash.message = message(code : 'default.updated.success.message', default : "Record updated successfully")
				chain(action : "edit", id : productCategoryInstance?.id, model:[productType: params?.productType])
			}
		}catch(Exception e) {
			log.error "Exception "+e
			flash.message = message(code : 'default.general.error.save.message', null)
			render(view : "edit", model : [productCategoryInstance : productCategoryInstance, productType: params?.productType])
			return
		}
	}
}
