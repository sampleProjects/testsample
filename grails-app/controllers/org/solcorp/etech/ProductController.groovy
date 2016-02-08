package org.solcorp.etech

import grails.transaction.Transactional;
import grails.validation.Validateable

class ProductController {

	
	static allowedMethods = [save : "POST", update : "PUT"]
	
	def productService
	def laborActivityGroupService
	def sessionFactory
	def productCategoryService
    /**
	 * This action used to refresh create form
	 */
	def clear() {
		
		if(params?.productType){
			redirect action : "create", method : "GET", params:[productType: "all", previousAction: actionName]
		}else{
			redirect action : "create", method : "GET", params:[productClassType: params?.productClassType, previousAction: actionName]
		}
		
		
	}
	
	/**
	 * This action used to  Create form Reset
	 */
	def newRecord() {
		flash.message =  message(code : 'etech.product.insert.new.rec.click.label', default : "Enter data to insert new record and click Save")
		if(params?.productType){
			redirect action : "create", method : "GET", params:[productType: "all", previousAction: actionName]
		}else{
			redirect action : "create", method : "GET", params:[productClassType: params?.productClassType, previousAction: actionName]
		}
	}
	
	def create() {
		params.laborActivityGroupId = 0;
		params.projectProductId = 0;		
		
		def productInstance = null
		params.put("disabledField","false")
		if(params?.productClassType == "PROJECT") {
			params.put("disabledField","true")
			productInstance =  new ProjectProduct(params)
			productInstance.productClassType = ProductClassType.PROJECT
		} else if(params?.productClassType == "MISCELLANEOUS") {
			params.put("disabledField","true")
			productInstance = new Miscellaneous(params)
			productInstance.productClassType = ProductClassType.MISCELLANEOUS
		} else if(params?.productClassType == "EXPENSES") {
			params.put("disabledField","true")
			productInstance = new Expense(params)
			productInstance.productClassType = ProductClassType.EXPENSES
		} else if(params?.productClassType == "SERVICES") {
			params.put("disabledField","true")
			productInstance = new Service(params)
			productInstance.productClassType = ProductClassType.SERVICES
		} else {
			
			productInstance = new Product(params)
		}
		
		if(params?.previousAction.equals("newRecord")) {
			productInstance.standardRate = 0.00 
		}
		
		render(view : "create", model: [productInstance: productInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList()])
		
	}
	
	/**
	 * This Action used to save Product Data
	 * @param productInstance
	 */
	@Transactional
	def save(Product productInstance) {
		Long productIntanceId = null
		params.put("disabledField",params?.disabledField)
		
		try {
			if (productInstance == null) {
				notFound()
				return
			}
			
			if(params?.productClassType == "PROJECT"){
				ProjectProduct projectProductInstance = new ProjectProduct(params)
				params.laborActivityGroupId = ""
				params.projectProductId = ""
				if (productInstance.hasErrors()) {
					projectProductInstance.validate()
					render(view : 'create', model: [laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productInstance: projectProductInstance, productType: params?.productType])
					return
				}else{
					projectProductInstance.save flush: true
					productIntanceId = projectProductInstance?.id
				}
			}else if(params?.productClassType == "MISCELLANEOUS"){
				Miscellaneous miscellaneousInstance = new Miscellaneous(params)
				params.laborActivityGroupId = ""
				params.projectProductId = ""
				if (productInstance.hasErrors()) {
					miscellaneousInstance.validate()
					render(view : 'create', model: [laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productInstance: miscellaneousInstance, productType: params?.productType])
					return
				}else{
					miscellaneousInstance.save flush : true
					productIntanceId = miscellaneousInstance?.id
				}
			}else if(params?.productClassType == "EXPENSES"){
				Expense expenseInstance = new Expense(params)
				params.laborActivityGroupId = ""
				params.projectProductId = ""
				
				if(params?.glAccountNumber && params?.glAccountNumber.toString().length() > 10) {
					expenseInstance.errors.rejectValue('glAccountNumber', 'org.solcorp.etech.Expense.glAccountNumber.maxSize.exceeded')
				}
				
				if (productInstance.hasErrors() || expenseInstance.hasErrors()) {
					expenseInstance.validate()
					render(view : 'create', model: [laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productInstance: expenseInstance, productType: params?.productType])
					return
				}else{
					expenseInstance.save flush : true
					productIntanceId = expenseInstance?.id
				}
				
			}else if(params?.productClassType == "SERVICES"){
				Service serviceInstance = new Service(params)
				
				ValidationCommand cmd = new ValidationCommand()
				cmd.laborActivityGroup = params?.laborActivityGroup
				cmd.product = params?.product
				cmd.code = params?.code
				/*if(!params?.code) {
					serviceInstance.errors.rejectValue('code', 'Please enter code.')
				}*/
				
				if(!cmd.validate()){
					params.laborActivityGroupId = params?.laborActivityGroup
					params.projectProductId = params?.product
					render(view : 'create', model: [cmd: cmd, productInstance: serviceInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productType: params?.productType])
					return
				}else if (productInstance.hasErrors()) {
					params.laborActivityGroupId = params?.laborActivityGroup
					params.projectProductId = params?.product
					serviceInstance.validate()
					//serviceInstance.errors.rejectValue('code', 'org.solcorp.etech.Product.code.unique')
					render(view : 'create', model: [productInstance: serviceInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productType: params?.productType])
					return
				}else{
					serviceInstance.save(flush: true, failOnError: true)
					productIntanceId = serviceInstance?.id
				}
				
			}
			flash.message =  message(code : 'default.added.success.message', default : "Record added successfully")
			chain(action : "edit", id : productIntanceId, model:[productType: params?.productType])
		}catch(Exception e) {
			print "Inside Catch"+e
			 flash.message = message(code : 'default.general.error.save.message', null)
			 render(view : "create", model : [productInstance : productInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productType: params?.productType])
			 return
		 }
	}
	
	
	/*def static mergeParamsToCMD(def cmd, def params){
		cmd.properties.each { key, value ->
			if(value==null || value.toString().trim().length <= 0){
				cmd[key] = params[key]
			}
		}
		return cmd
	}*/


	
	/**
	 * This action render to Product Edit Screen
	 * @param productInstance
	 */
	def edit() {
		Product productInstance =  Product.findById(params?.id as Long)
		def productType = flash?.chainModel?.productType?:params?.productType 
		
		if(productInstance instanceof ProjectProduct){
			productInstance.productClassType = ProductClassType.PROJECT
		} else if (productInstance instanceof Miscellaneous) {
			productInstance.productClassType = ProductClassType.MISCELLANEOUS
		} else if (productInstance instanceof Expense) {
			productInstance.productClassType = ProductClassType.EXPENSES
		} else if (productInstance instanceof Service) {
			productInstance.productClassType = ProductClassType.SERVICES
			params.put("laborActivityGroupId",productInstance?.laborActivityGroup?.id)
			params.put("projectProductId", productInstance?.product?.id)
		} 
		render(view : "edit", model : [productInstance : productInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productType: productType])
	}
	
	/**
	 * This Action used to delete record From database
	 */
	@Transactional
	def delete(Product productInstance) {
		def classTypeMessage = null
		def tempProductClassType = productInstance?.productClassType
		try{
			if (productInstance == null) {
				notFound()
				return
			}
			if(productInstance instanceof ProjectProduct){
				
				classTypeMessage =  message(code: 'org.solcorp.etech.Product.delete.child.ref.exists.error')
				
			} else if (productInstance instanceof Service) {
			
				classTypeMessage =  message(code: 'org.solcorp.etech.Product.service.delete.child.ref.exists.error')
							
			} else if (productInstance instanceof Expense) {
			
				classTypeMessage =  message(code: 'org.solcorp.etech.Product.expense.delete.child.ref.exists.error')
							
			} else if (productInstance instanceof Miscellaneous) {
				
				classTypeMessage =  message(code: 'org.solcorp.etech.Product.delete.child.ref.exists.error')
				
			} else {
				
				classTypeMessage =  message(code: 'default.delete.child.ref.exists.error')
			}
			
			productInstance.delete flush:true, failOnError:true			
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
		
		redirect(action: "edit", id: productInstance.id)
		return
		
	}
	
	/**
	 * This action used to list record from DB by Form parameter
	 */
	def list() {
		try{
			if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
			def modelMap = productService.getProductList(params)
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
	 * This action used to update LaborActivityGroup data
	 * @param laborActivityGroupInstance
	 */
    @Transactional
    def update() {
		Product productInstance = Product.get(params?.productInstanceId)
		Long productIntanceId = null
		try{
			if (productInstance == null) {
				notFound()
				return
			}
			
			if(params.containsKey('code')){
				productInstance.code = params?.code
			}else{
				productInstance.code = params?.code?:params?.productInstanceCode
			}
			//productInstance.code = params?.code?:params?.productInstanceCode
			productInstance.description = params?.description
			if(params?.productCategory){
				ProductCategory productCategoryInstance = ProductCategory.get(params?.productCategory as Long)
				productInstance.productCategory = productCategoryInstance 
			}else{
				productInstance.productCategory = null
			}
			
			if(params?.standardRate){
				productInstance.standardRate = new BigDecimal(params?.standardRate)
			}else{
				productInstance.standardRate = null
			}
			
			if(params?.productClassType == "EXPENSES" && params?.glAccountNumber){
				productInstance.glAccountNumber = params?.glAccountNumber
			}
			
			ValidationCommand cmd = new ValidationCommand()
			cmd.laborActivityGroup = params?.laborActivityGroup
			cmd.product = params?.product
			if(params.containsKey('code')){
				cmd.code = params?.code
			}else{
				cmd.code = params?.code?:params?.productInstanceCode
			}
			//cmd.code = params?.code?:params?.productInstanceCode
			
			if(params?.productClassType == "SERVICES" && (!cmd.validate())){
				params.laborActivityGroupId = params?.laborActivityGroup
				params.projectProductId = params?.product
				
				productInstance.discard()
				render(view : 'edit', model: [cmd: cmd, productInstance: productInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productType: params?.productType])
				return
			}else{
				productInstance.validate()
				if (productInstance.hasErrors()) {
					if(params?.productClassType == "SERVICES"){
						params.laborActivityGroupId = params?.laborActivityGroup
						params.projectProductId = params?.product
					}
					productInstance.discard()
					render(view : "edit", model : [productInstance : productInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productType: params?.productType])
					return
				}else{
					productInstance.save(flush: true, failOnError: true)
					if(params?.productClassType == "PROJECT"){
						params.laborActivityGroupId = ""
						params.projectProductId = ""
						final session = sessionFactory.currentSession
						final String query = "update product set labor_activity_group_id = NULL, product_id = NULL where id = "+productInstance?.id
						final sqlQuery = session.createSQLQuery(query)
						int updated = sqlQuery.executeUpdate();
					}else if(params?.productClassType == "MISCELLANEOUS"){
						params.laborActivityGroupId = ""
						params.projectProductId = ""
						final session = sessionFactory.currentSession
						final String query = "update product set labor_activity_group_id = NULL, product_id = NULL where id = "+productInstance?.id
						final sqlQuery = session.createSQLQuery(query)
						int updated = sqlQuery.executeUpdate();
					}else if(params?.productClassType == "EXPENSES"){
						params.laborActivityGroupId = ""
						params.projectProductId = ""
						final session = sessionFactory.currentSession
						final String query = "update product set labor_activity_group_id = NULL, product_id = NULL where id = "+productInstance?.id
						final sqlQuery = session.createSQLQuery(query)
						int updated = sqlQuery.executeUpdate();
					}else if(params?.productClassType == "SERVICES"){
						if(!params?.laborActivityGroup){
							params.put("laborActivityGroup",null)
						}
						if(!params?.product){
							params.put("product", null)
						}
						final session = sessionFactory.currentSession
						final String query = "update product set labor_activity_group_id = "+params?.laborActivityGroup+", product_id = "+params?.product+" where id = "+productInstance?.id
						final sqlQuery = session.createSQLQuery(query)
						int updated = sqlQuery.executeUpdate();
					}
					flash.message = message(code : 'default.updated.success.message', default : "Record updated successfully")
					chain(action : "edit", id : productInstance?.id, model:[productType: params?.productType])
				}
			}
		}catch(Exception e) {
			log.error "Exception "+e
			flash.message = message(code : 'default.general.error.save.message', null)
			render(view : "edit", model : [productInstance : productInstance, laborActGrpList: laborActivityGroupService.getLaborActGrpList(), projectProductList: productService.getProjectProductList(), productType: params?.productType])
			return
		}
    }
	
	def getProductCategoryList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "productCategoryList" , model: productCategoryService.getProductCategoryList(params), layout: "ajax")
	}
}

@grails.validation.Validateable
class ValidationCommand {
	String code
	String laborActivityGroup
	String product
	
	static constraints = {
		code(nullable: true, 
			validator:{val, obj, errors  ->
				if(val == null || val.toString().trim().length() <= 0){
					errors.rejectValue("code", "org.solcorp.etech.Product.code.nullable")
					return false
				}
			})
		
		laborActivityGroup(nullable: true, 
			validator:{val, obj, errors  ->
				if(val == null || val.toString().trim().length() <= 0){
					errors.rejectValue("laborActivityGroup", "org.solcorp.etech.Service.laborActivityGroup.nullable")
					return false
				}
			})
		
		product(nullable: true,
			validator:{val, obj, errors  ->
				if(val == null || val.toString().trim().length() <= 0){
					errors.rejectValue("product", "org.solcorp.etech.Service.product.nullable")
					return false
				}
			})
	}
}
