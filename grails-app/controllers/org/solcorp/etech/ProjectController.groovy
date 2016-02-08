package org.solcorp.etech
import static org.springframework.http.HttpStatus.*
import org.hibernate.criterion.CriteriaSpecification
import org.solcorp.etech.DTO.ProjectCostingDTO;
import org.hibernate.Criteria
import org.hibernate.Session
import grails.transaction.Transactional
@Transactional(readOnly = true)
class ProjectController {

    static allowedMethods = [save: "POST", update: "PUT"]
	
	def projectService
	
	def employeeService
	
	def productService
	
	def userService
	   
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
			def modelMap = projectService.getProjectList(params)
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
		
		def project = new Project(params)
		
		//Clear Project Manger Instance List If exist
		projectService.clearProjectManagerSession()
		
		if(params?.previousAction.equals("newRecord")) {
			project.projectType = ProjectType.UTILIZED //ProjectType.CUSTOMER
			project.billCodeType = BillCodeType.FLAT_RATE
			project.status = ProjectStatusType.OPEN
		
			def  loggedInUserInstance = userService.getLoggedInUser()						
			//If Project Manager Logged in then set logged in Project Manager as a default project manager
			if(loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_PROJECT_MANAGER.name())) {					
				projectService.addProjectMangerInSession(['employeeId':loggedInUserInstance?.employee?.id])
			}
			//If Account Director Logged in then set Logged in AD as a default Account Director
			if(loggedInUserInstance?.roles*.name.contains(RoleType.ROLE_ACCOUNT_DIRECTOR.name())) {
				project.accExecutive = loggedInUserInstance?.employee
			}
		}
		
		respond project, model: [projectProductList: productService.getProjectProductList()]
    }

    
    def save() {
		
		def projectInstance = new Project(params)
		
		try {
	        if (projectInstance == null) {
	            notFound()
	            return
	        }					
			
			projectInstance = projectService.saveProject(projectInstance, params)
			
	        if (projectInstance.hasErrors()) {
								
	            respond projectInstance.errors, model: [projectProductList: productService.getProjectProductList()], view:'create'
	            return
	        }
		
			flash.message =  message(code: 'default.added.success.message', default :"Record added successfully")
			redirect(action: "edit", id: projectInstance.id)
			
		} catch(Exception e) {
			flash.message = message(code: 'default.general.error.save.message', null)
			
			render(view: "create", model: [ projectInstance: projectInstance, projectProductList: productService.getProjectProductList()])
			
			return
		}
    }

    def edit(Project projectInstance) {	
			
		if(projectService.isValidProjectAccess(projectInstance?.id)) {
			projectService.setProjectManagerInstanceInSession(projectInstance?.projectEmployees, projectInstance?.id?.toString())
		
			respond projectInstance,  model: [ projectProductList: productService.getProjectProductList(),										   										   
										   projectCostingList: projectService.getProjectCostingSummary(projectInstance),
										   hhProjectMstDetailList: projectService.getHHProjectMasterDtlByProjectCode(projectInstance?.code)]
		} else {
			redirect(action: "unauthorized", controller:"auth")
		}
    }
    
    def update() {
		
		def projectInstance = null
		try {        
			
			projectInstance = projectService.updateProject(params)
			
			if (projectInstance.hasErrors()) {						
						
	            respond projectInstance.errors, model: [ projectProductList: productService.getProjectProductList(),														 														 
														 projectCostingList: projectService.getProjectCostingSummary(projectInstance),
														 hhProjectMstDetailList: projectService.getHHProjectMasterDtlByProjectCode(projectInstance?.code)], view:'edit'
	            return
	        }
			
			flash.message = message(code: 'default.updated.success.message', default :"Record updated successfully")			
			redirect(action: "edit", id: projectInstance?.id)
			
		 } catch(Exception e) {
			 log.error "Exception "+e
			 
			 flash.message = message(code: 'default.general.error.save.message', null)
			 render(view: "edit",  model: [ projectInstance: projectInstance,																						
											projectProductList: productService.getProjectProductList(),											
											projectCostingList: projectService.getProjectCostingSummary(projectInstance),
											hhProjectMstDetailList: projectService.getHHProjectMasterDtlByProjectCode(projectInstance?.code)])
			 
			 return
		 }
    }

    @Transactional
    def delete(Project projectInstance) {
		try {

			if (projectInstance == null) {
	            notFound()
	            return
	        }
					 
			projectInstance.delete flush:true, failOnError:true
			flash.message = message(code: 'default.deleted.success.message', default : "Record deleted successfully")
			
			redirect action:"create", method:"GET"
			return		 

		} catch (org.springframework.dao.DataIntegrityViolationException dive) {
		
			flash.message = message(code: 'default.delete.child.ref.exists.error')
		} catch (Exception e) {
			log.error "Exception " + e
			
			flash.message = message(code: 'default.general.error.delete.message')
		}
		
		redirect(action: "edit", id: projectInstance.id)
		return
    }
	
	/**
	 * Add Product Combo
	 * @param i: Row index
	 * @return
	 */
	def addProductComboAjax() {
		render (template : "addProductComboAjax", model:[i : params.i, projectProductList: productService.getProjectProductList(params)])
	}
	
	/**
	 * This action used to Add Service combo and Date  
	 * @return
	 */
	def addServiceDetailTemplate() {			
		render (template : "addProjectDetail", model:[i : params.i, j: params.j, projectProductList: productService.getProjectProductList(), serviceList: productService.getServiceListByProjectProduct(params?.productId), tableId: params?.tableId])
	}
	
	/**
	 * This action used to filled service combo by Project Product
	 * @return
	 */
	def getServiceListByProject() {		
		render (template : "serviceComboAjax", model: [i: params?.i, j: params?.j, serviceList: productService.getServiceListByProjectProduct(params?.productId), isLaborExpenseAdded: params?.isLaborExpenseAdded])
	}
	
	/**
	 * This action used to Add Service combo and Date
	 * @return
	 */
	def addProjectServiceAjax() {
		render (template : "addProjectDetail", model: [i: params?.i, j: params?.j, serviceList: productService.getServiceListByProjectProduct(params?.productId)])
	}
	
	/**
	 * This action used to remove project details record 
	 * @return
	 */
	def removeProjectDtl() {	
		render (template : "addProjectDetailList", model: [projectProductDetailsList: projectService.removeProjectDetail(params), projectProductList: productService.getProjectProductList()])
	}
		
	def productCostingPopup() {
		render (template : "productCostingPopup", model: [productCostingDtl: projectService.getProductCostingSummary(params?.projectProductDtlId)])
	}
	
	def getHHProjectMasterList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "hhProjectImportList" , model: projectService.getHHProjectMasterList(params), layout: "ajax")
	}
	
	def isCustomerExistInDB() {		
		def isCustomerExistInDB = projectService.isCustomerExistInDB(params)		
		if(isCustomerExistInDB) {
			render isCustomerExistInDB
			return 
		}
		render ""
		 	
	}
	
	def getCustomerList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "customerCodeList" , model: projectService.getCustomerList(params), layout: "ajax")
	}
	
	def getParentProjectList() {
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "parentProjectCodeList" , model: projectService.getParentProjectList(params), layout: "ajax")
	}
	
	/**
	 * This Action used to get All Actual Labor Employee List
	 * @param params?.id : Project Id
	 * @return ProjectActualLaborDetail List
	 */
	def getAllActualLaborEmployeeList() {			
		render(view: "actualLaborDetail" , model: projectService.getAllActualLaborEmployeeList(params) )
	}
	
	/**
	 * This Action used to get All Actual Labor Employee activity List
	 * @param params?.projectId : Project Id
	 * @param params?.employeeId : Employee Id
	 * @return ProjectActualLaborDetail List
	 */
	def getActualLaborActivityDetail() {		
		render(view: "employeeActivityDetail" , model: projectService.getActualLaborActivityDetailList(params) )
	}
	
	/**
	 * This method used to Import data to ProjectProductDetails and ProjectServiceDetails from ProductDetail Table
	 * @return
	 */
	def importProjectDetailData() {
		
		projectService.importProjectDetailData()
		
		render "Done....."
	}
	
	String globalString = "";
	
	def treeView(String code, String status) {
		
		def project = Project.findByCode(code)
		if(project == null){
			notFound()
			return
		}
		
		globalString = "";
		List treeViewResult = new ArrayList();
		treeViewResult.add("\""+code+"\"")
		traverseChild(code, treeViewResult)
		globalString = "[";
		iterateTreeViewSecond(treeViewResult)
		if(globalString.charAt(globalString.length() - 1) == ','){
			String temp = globalString.substring(0,(globalString.length() - 1))
			globalString = temp;
		}
		globalString = globalString + "]}]";
		render(view:'treeView', model :[treeView: globalString])
	}
	
	def iterateTreeViewSecond(treeViewResult){
		
		for(int i=0;i<treeViewResult.size();i++){
			
			if(treeViewResult[i].class == java.lang.String){
				globalString = globalString + "{ \"label\":  " + treeViewResult[i] + ",\"children\": [";
			}else if(treeViewResult[i].size() == 1){
				globalString = globalString + "{ \"label\": " + treeViewResult[i][0] +" },";
			}else{
				iterateTreeViewSecond(treeViewResult[i])
				if(globalString.charAt(globalString.length() - 1) == ','){
					String temp = globalString.substring(0,(globalString.length() - 1))
					globalString = temp;
				}
				globalString = globalString + "]},"
			}
		}
	}
	
	def traverseChild(String code, treeViewResult){
		
		def chiledProjectsList = Project.createCriteria().list {
			and {
				eq("parentProject",Project.findByCode(code))
			}
		}
		
		if(chiledProjectsList.size() > 0){
			for(int i=0;i<chiledProjectsList.size();i++){
				List temp = new ArrayList();
				temp.add("\""+chiledProjectsList[i]?.code+"\"")
				treeViewResult.add(temp)
				traverseChild(chiledProjectsList[i]?.code,temp)
			}
		}
	}
	
	def loadParentProject(){
		redirect(action: "edit", id: Project.findByCode(params?.code)?.id)
	}
	
	/**
	 * This Action used to get All Project Manager
	 * @return
	 */
	def getProjectManagerList() {	
			
		if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
		render(template: "ajaxUpdatedProjectManagerList" , model: projectService.getEmpByProjectManagerTypeList(params))
		
	}
	
	/**
	 * This Action used to add Project Manger in Session
	 * @return
	 */
	def addProjectMangerInSession() {
		projectService.addProjectMangerInSession(params)		
		render(template: "ajaxManagerSessionDiv" , model: [projectId:params?.projectId])
	}
	
	/**
	 * This Action used to remove Project Manager from session
	 * @return
	 */
	def removeProjectMangerFromSession() {
		projectService.removeProjectMangerFromSession(params)		
		render(template: "ajaxManagerSessionDiv" , model: [projectId:params?.projectId])
	}
	
	def setDefaultAccountExecutive(){
		render(projectService.setDefaultAccountExecutive(params))
	}

	def checkAccountExecutive(){
		render(projectService.checkAccountExecutive(params))
	}
	/**
	 * Get HHExpense List by project code
	 * @return
	 */
	def getHHExpenseMasterListByProjectCode(){
		
		try {
			if(projectService.isValidProjectAccess(Long.valueOf(params?.id))) {			
			
				if(!params.max) params.max = Constants.DEFAULT_PAGINATION_RECORDS
				def modelMap = projectService.getHHExpenseMasterListByProjectCode(params)
				if(request.xhr) {
					render(template:"hhExpenseMasterListRecords", model:modelMap)
				} else {
					render(view : "hhExpenseMstList", model : modelMap)
				}
				return
			} else {
				redirect(action: "unauthorized", controller:"auth")
			}
		} catch(Exception e) {
			log.error "Exception " + e
		}
	}
	
	
	/**
	 * This Action used to get All Actual Expense List
	 * @param params?.id : Project Id
	 * @return getAllServiceActualExpenseList List
	 */
	def getAllServiceActualExpenseList() {		
		render(view: "actualExpenseDetail" , model: projectService.getAllServiceActualExpenseList(params) )
	}
	
	/**
	 * This Action used to get All Actual Expense List By expense
	 * @param params?.projectId : Project Id
	 * @param params?.expId : Expense Id
	 * @return getActualExpenseDetailByExpCode List
	 */
	def getActualExpenseDetailByExpCode() {
		render(view: "actualExpenseDetailByExp" , model: projectService.getActualExpenseDetailByExpCode(params) )
	}
	
	
    protected void notFound() {
        request.withFormat {
            form {				
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'projectInstance.label', default: 'Project'), params.id])
                redirect action: "list", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
