package org.solcorp.etech
import java.math.BigDecimal;
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.criterion.CriteriaSpecification
import javax.persistence.Transient;

import org.springframework.web.context.request.RequestContextHolder
import org.solcorp.etech.utils.MiscUtils

import grails.converters.JSON;
import grails.transaction.Transactional

import org.hibernate.criterion.CriteriaSpecification
import org.solcorp.etech.DTO.ProjectCostingDTO
import org.solcorp.etech.utils.DateFormatUtils
@Transactional
class ProjectService {
	
	def userService
    
	def getProjectList(params) {
			
		def projectCriteria = Project.createCriteria()
		def projectCriteriaCount = Project.createCriteria()
		def projectManagerSessionList = getProjectManagerFromSession(null)		
				
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()		
		
		def projectInstanceList = projectCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {				 
				if (params?.code) {
					like("code", "%" + params?.code + "%")
				}				
				if (params?.name) {
					like("name", "%" + params?.name + "%")
				}
								 
				if (params?.projectCategory) {
					eq("projectCategory.id", Long.valueOf(params?.projectCategory))
				} 				
				
				if (params?.projectType ) {
					eq("projectType", ProjectType.valueOf(params?.projectType))
				}
								
				if (params?.billCodeType) {
					eq("billCodeType", BillCodeType.valueOf(params?.billCodeType))
				}
				
				if (params?.customer) {
					eq("customer.id", Long.valueOf(params?.customer))
				}
				
				if (params?.customerTxt) {
					createAlias('customer', 'cust')
					ilike("cust.code","%" + params?.customerTxt + "%")
				}
				// IF Account Director Logged in then Account Director related project display
				if(loggedInUserInstance) {					
					or {
						eq("accExecutive", loggedInUserInstance?.employee)
						
						createAlias('projectEmployees', 'pm')
						eq('pm.employee', loggedInUserInstance?.employee)
					}
				} else {						
					createAlias('projectEmployees', 'pm', CriteriaSpecification.LEFT_JOIN)
				} 
				/*if (params?.accExecutive) {
					eq("accExecutive.id", Long.valueOf(params?.accExecutive))
				}*/
				
				if (params?.accExecutiveTxt) {
					createAlias('accExecutive', 'accExe')					
					ilike("accExe.code", "%" + params?.accExecutiveTxt + "%")
				}
				if(projectManagerSessionList?.size() > 0) {										
					inList('pm.employee', projectManagerSessionList?.employee)
				}
			 								
				if (params?.parentProject) {
					eq("parentProject.id", Long.valueOf(params?.parentProject))
				}
				if (params?.status ) {
					eq("status", ProjectStatusType.valueOf(params?.status))
				}
				if (params?.startDate) {
					gt("startDate",DateFormatUtils.getDateFromString(params?.startDate))
				}
				if (params?.endDate) {
					lt("endDate",DateFormatUtils.getDateFromString(params?.endDate))
				}
				if (params?.parentProjectTxt) {
					createAlias('parentProject', 'p')
					ilike("p.code","%" + params?.parentProjectTxt + "%")
				}
			}			
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
			resultTransformer Criteria.DISTINCT_ROOT_ENTITY
		}
		def projectInstanceCountList = projectCriteriaCount.list() {
			and {
				
				projections {					
					distinct("id")
				}
				if (params?.code) {
					like("code", "%" + params?.code + "%")
				}
				if (params?.name) {
					like("name", "%" + params?.name + "%")
				}
								 
				if (params?.projectCategory) {
					eq("projectCategory.id", Long.valueOf(params?.projectCategory))
				}
				
				if (params?.projectType ) {
					eq("projectType", ProjectType.valueOf(params?.projectType))
				}
								
				if (params?.billCodeType) {
					eq("billCodeType", BillCodeType.valueOf(params?.billCodeType))
				}
				
				if (params?.customer) {
					eq("customer.id", Long.valueOf(params?.customer))
				}
				
				if (params?.customerTxt) {
					createAlias('customer', 'cust')
					ilike("cust.code","%" + params?.customerTxt + "%")
				}
				// IF Account Director Logged in then Account Director related project display
				if(loggedInUserInstance) {
					or {
						eq("accExecutive", loggedInUserInstance?.employee)
						
						createAlias('projectEmployees', 'pm')
						eq('pm.employee', loggedInUserInstance?.employee)
					}
				} else {
					createAlias('projectEmployees', 'pm', CriteriaSpecification.LEFT_JOIN)
				}
				/*if (params?.accExecutive) {
					eq("accExecutive.id", Long.valueOf(params?.accExecutive))
				}*/
				
				if (params?.accExecutiveTxt) {
					createAlias('accExecutive', 'accExe')					
					ilike("accExe.code", "%" + params?.accExecutiveTxt + "%")
				}
				if(projectManagerSessionList?.size() > 0) {
					inList('pm.employee', projectManagerSessionList?.employee)
				}
											 
				if (params?.parentProject) {
					eq("parentProject.id", Long.valueOf(params?.parentProject))
				}
				if (params?.status ) {
					eq("status", ProjectStatusType.valueOf(params?.status))
				}
				if (params?.startDate) {
					gt("startDate",DateFormatUtils.getDateFromString(params?.startDate))
				}
				if (params?.endDate) {
					lt("endDate",DateFormatUtils.getDateFromString(params?.endDate))
				}
				if (params?.parentProjectTxt) {
					createAlias('parentProject', 'p')
					ilike("p.code","%" + params?.parentProjectTxt + "%")
				}
			}			
		}		
		def projectInstanceCount = projectInstanceCountList.size()
		
		return [projectInstanceList: projectInstanceList, projectInstanceCount: projectInstanceCount, params: params]
    }
	
	/*def findByCode(code) {
		def projectCriteria = Project.createCriteria()
		def projectInstance = projectCriteria.get {
			createAlias('customer', 'customer', CriteriaSpecification.LEFT_JOIN)
			createAlias('projectDetails', 'projectDetails', CriteriaSpecification.LEFT_JOIN)
			
			and {
				eq("code", code)
			}
		}
		
		return projectInstance
	}*/
	
	def findByCode(code) {
		def projectCriteria = Project.createCriteria()
		def projectInstance = projectCriteria.get {
			createAlias('customer', 'customer', CriteriaSpecification.LEFT_JOIN)
			createAlias('projectProductDetails', 'projectProductDetail', CriteriaSpecification.LEFT_JOIN)
			createAlias('projectProductDetail.projectServiceDetails', 'projectServiceDetail', CriteriaSpecification.LEFT_JOIN)
			
			and {
				eq("code", code)
			}
		}
		
		return projectInstance
	}
	
	def getProjectByCode(code, numericComparision) {
		/*log.info "getProjectByCode @ ProjectService Start"*/
		
		def projectCriteria = Project.createCriteria()
		def projectInstanceList = projectCriteria.list() {
			 
			and {
				if(numericComparision) {
					sqlRestriction "ISNUMERIC(code) = 1"
					sqlRestriction "SUBSTRING(code, PATINDEX('%[^0 ]%', code + 'a'), LEN(code)) = " + code
				} else {
					eq("code", code)
				}
			}
		}
		
		/*log.info "getProjectByCode @ ProjectService End"*/
		return projectInstanceList
	}
	
	/**
	 *
	 * @param projectInstance
	 * @return
	 */
	def saveProject(projectInstance, params) {

			
		def isCustomerExistInDB = false 
		
		if(params?.customerTxt) {
			
			def customerInstance = Customer.findByCode(params?.customerTxt)
			
			if(customerInstance) {
				
				isCustomerExistInDB = true
				
				projectInstance?.customer = customerInstance
				
			}
		}
		
		projectInstance.validate()
		
		projectFieldValidation(projectInstance, params, isCustomerExistInDB)
		
		if(params?.accExecutiveTxt){
			def employee = Employee.findByCode(params?.accExecutiveTxt)
			if(employee) {				
				if(userService.getLoggedInAccountDirector()?.employee && employee?.id != userService.getLoggedInAccountDirector()?.employee?.id) {
					
					projectInstance?.accExecutiveTxt = params?.accExecutiveTxt
					projectInstance.errors.rejectValue('accExecutive', 'org.solcorp.etech.Customer.acctExecutiveTxt.error.notFound')					
				} else {
					projectInstance?.accExecutive = employee
				}
			}else {			
				projectInstance?.accExecutiveTxt = params?.accExecutiveTxt
				projectInstance.errors.rejectValue('accExecutive', 'org.solcorp.etech.Customer.acctExecutiveTxt.error.notFound')
			}
		}
		
		if(projectInstance.hasErrors()) {
			
			return projectInstance
			
		}
		
		if (!projectInstance.save(flush: true, saveOnError: true)) {
			
			throw new RuntimeException()
			return
			
		}
		
		//Save HH Project Master Details table
		saveHHProjectMasterDetails(projectInstance?.code)
				
		clearProjectManagerSession()
		
		return projectInstance
		
	}
	
	/**
	 * Update Project, Project Details
	 * @param projectInstance
	 * @param params
	 * @return
	 */
	def updateProject(params) {		
		
		def projectInstance = Project.get(params.id)
		
		def tempCode = params?.code ?: projectInstance?.code
			
		params.put("code", tempCode)		
		def newProjectInstance = new Project(params)
				
		newProjectInstance.validate()
		if(newProjectInstance?.projectEmployees?.size() <= 0) {
			
				newProjectInstance.errors.rejectValue('projectEmployees', 'org.solcorp.etech.Project.project.manager.nullable')

		}
		if(newProjectInstance.hasErrors()) {
			
			newProjectInstance?.projectProductDetails?.each { product->
				product.projectServiceDetails.each { serv ->
					serv.discard()
				}
				product.discard()
			}	
			
			newProjectInstance.discard()
			projectInstance?.projectEmployees?.each { projectManager->
				projectManager.discard()
			}
			
			return 	newProjectInstance
		}
		
		projectInstance?.projectProductDetails?.each { product->
			product.projectServiceDetails.clear()
		}

		projectInstance?.projectProductDetails.clear()
		
		projectInstance?.projectEmployees.clear()
		
		projectInstance.properties = params
				
		if (!projectInstance.save(flush: true, saveOnError: true)) {
			throw new RuntimeException()
			return
		}
		
		//Clear Project Manager Session
		clearProjectManagerSession(projectInstance?.id.toString())
		
		return 	projectInstance
	}
	
	def getProjectList() {
		 
		def projectList = Project.findAll()		
		return projectList
	}	
	
	def removeProjectDetail(params) {
		
		def projectInstance = new Project(params)
		 
		def parentIndex = params?.removeParentIndex
		def serviceIndex = params?.removeServiceIndex
				
		if(parentIndex!="" && parentIndex!= null && parentIndex.toInteger() >=0 ) {
			parentIndex = parentIndex.toInteger()
		}
		
		if(serviceIndex!="" && serviceIndex!= null && serviceIndex.toInteger() >=0 ) {
			serviceIndex = serviceIndex.toInteger()
		}
	   
		List productDetailList = []
				
		if(projectInstance?.projectProductDetails) {			
			
			int prodIndex = 0
			
			projectInstance?.projectProductDetails?.eachWithIndex {  productDetailInstance, i ->
				
				List serviceDetailList = []
				
				if(productDetailInstance?.projectServiceDetails) {
					
					int temp = 0
					
					productDetailInstance?.projectServiceDetails.eachWithIndex { serviceInstance, j ->
											
						if(prodIndex == parentIndex && temp ==  serviceIndex) {							
						} else {							 					
							serviceDetailList.add(serviceInstance)							
						}
						temp ++
					}
					
				}		
				
				if (serviceDetailList.size() > 0){
					def projectProductDetails = new ProjectProductDetail()
					projectProductDetails.id = productDetailInstance?.id 
					projectProductDetails.projectServiceDetails =  serviceDetailList
					projectProductDetails.projectProduct = productDetailInstance?.projectProduct
					projectProductDetails.plannedRevenue = productDetailInstance?.plannedRevenue
					productDetailList.add(projectProductDetails)
				}
				prodIndex ++
			}	
		}
		return productDetailList
	}
		
	/**
	 * This method used to get Project Costing summary
	 * @param projectInstance
	 * @return
	 */
	def getProjectCostingSummary(projectInstance) {
		
		def projectProductDetailsList = projectInstance?.projectProductDetails
		
		def projectCostingList = []
		
		if(projectProductDetailsList?.size() > 0){
		
			projectProductDetailsList.each {  projectProductDetailsInstance ->
				
				ProjectCostingDTO projectCostingDTO = new ProjectCostingDTO()
			
				projectCostingDTO.productCode = projectProductDetailsInstance?.projectProduct?.code
				
				BigDecimal plannedLaborTotal = 0.00
				
				BigDecimal plannedExpenseTotal = 0.00
				BigDecimal actualExpenseTotal = 0.00
				BigDecimal actualLaborTotal = 0.00
				
				BigDecimal plannedHours = 0.00
				BigDecimal actualHours = 0.00
				
				BigDecimal plannedStdOHCost = 0.00
				BigDecimal actualStdOHCost = 0.00
				BigDecimal varianceStdOHCostTotal = 0.00
				 
				def projectServicetInstanceList = projectProductDetailsInstance?.projectServiceDetails
				
				projectServicetInstanceList.each { projectDtl ->
					
					//Planned Labor Total
					if(projectDtl?.plannedLabor) {
						
						projectDtl?.plannedLabor?.laborDetails?.each { projectLaborDetail->
							
							if(projectLaborDetail?.standardCost) {
								plannedLaborTotal = plannedLaborTotal.add(projectLaborDetail?.standardCost)
							}
							
							if(projectLaborDetail?.overHeadCost) {
								plannedStdOHCost = plannedStdOHCost.add(projectLaborDetail?.overHeadCost)
							}
							
							if(projectLaborDetail?.hours) {
								plannedHours = plannedHours.add(projectLaborDetail?.hours)
							}
						}
					}
					
					//Actual Labor Total
					if(projectDtl?.actualLabor) {
						
						projectDtl?.actualLabor?.laborDetails?.each { projectActualDetail->
							
							if(projectActualDetail?.standardTotalCost) {
								actualLaborTotal = actualLaborTotal.add(projectActualDetail?.standardTotalCost)
							}
							
							if(projectActualDetail?.hours) {
								actualHours = actualHours.add(projectActualDetail?.hours)
							}
							
							if(projectActualDetail?.standardOverheadCost) {
								actualStdOHCost = actualStdOHCost.add(projectActualDetail?.standardOverheadCost)
							}
						}
					}
					
					//Planned  Expense total
					if(projectDtl?.plannedExpense) {
						projectDtl?.plannedExpense?.expenseDetails?.each { plannedExpense ->
							if(plannedExpense?.totalCost){
								plannedExpenseTotal = plannedExpenseTotal.add(plannedExpense?.totalCost)
							}
						}
					}					
					
					//Actual Expense total
					if(projectDtl?.actualExpense) {
						
						projectDtl?.actualExpense?.actualExpenseDetails?.each { actExpense ->
							
							if(actExpense?.monetaryAmount) {
								actualExpenseTotal = actualExpenseTotal.add(actExpense?.monetaryAmount)
							}
						}						
					}					
				}
				
				projectCostingDTO.plannedLaborTotal = plannedLaborTotal
				
				projectCostingDTO.plannedExpenseTotal = plannedExpenseTotal
				projectCostingDTO?.plannedStdOHCost = plannedStdOHCost
				projectCostingDTO.plannedTotal = plannedLaborTotal.add(plannedExpenseTotal).add(plannedStdOHCost)
								
				projectCostingDTO.actualLaborTotal = MiscUtils.removePrecision(actualLaborTotal)
				
				projectCostingDTO.actualExpenseTotal = actualExpenseTotal
				projectCostingDTO?.actualStdOHCost = MiscUtils.removePrecision(actualStdOHCost)
				projectCostingDTO.actualTotal = projectCostingDTO.actualLaborTotal.add(projectCostingDTO.actualExpenseTotal).add(projectCostingDTO?.actualStdOHCost)
				
				projectCostingDTO.varianceLaborTotal = projectCostingDTO?.actualLaborTotal - projectCostingDTO?.plannedLaborTotal
				
				projectCostingDTO.varianceExpenseTotal = projectCostingDTO?.actualExpenseTotal - projectCostingDTO?.plannedExpenseTotal
				
				projectCostingDTO.varianceStdOHCostTotal = projectCostingDTO?.actualStdOHCost - projectCostingDTO?.plannedStdOHCost
				
				projectCostingDTO.varianceTotal = projectCostingDTO.actualTotal - projectCostingDTO.plannedTotal
				
				projectCostingDTO.plannedRevenueTotal = projectProductDetailsInstance?.plannedRevenue ?:0.00
				
				projectCostingDTO.actualRevenueTotal = projectProductDetailsInstance?.actualRevenue ?:0.00
				
				projectCostingDTO.varianceRevenueTotal = projectCostingDTO?.actualRevenueTotal - projectCostingDTO?.plannedRevenueTotal 
				
				projectCostingDTO.plannedProfit =  projectCostingDTO?.plannedRevenueTotal - projectCostingDTO?.plannedTotal
				
				projectCostingDTO.actualProfit =  projectCostingDTO.actualRevenueTotal - projectCostingDTO.actualTotal
				
				projectCostingDTO.varianceProfit = projectCostingDTO.actualProfit - projectCostingDTO.plannedProfit 
				
				projectCostingDTO.plannedHoursTotal = plannedHours
				
				projectCostingDTO.actualHoursTotal = actualHours
				
				projectCostingDTO.varianceHoursTotal = projectCostingDTO.actualHoursTotal - projectCostingDTO.plannedHoursTotal
				
				if(projectCostingDTO?.plannedTotal !=0.00 && projectCostingDTO?.plannedProfit !=0.00) {
					projectCostingDTO?.plannedProfitPercentage =(projectCostingDTO?.plannedProfit*100) / projectCostingDTO?.plannedTotal
				} else {
					projectCostingDTO.plannedProfitPercentage = 0.00
				}
		
				if(projectCostingDTO?.actualTotal !=0.00 && projectCostingDTO?.actualProfit !=0.00) {
					projectCostingDTO.actualProfitPercentage =(projectCostingDTO?.actualProfit*100) / projectCostingDTO?.actualTotal
				} else {
					projectCostingDTO.actualProfitPercentage = 0.00
				}
		
				if(projectCostingDTO?.varianceTotal !=0.00 && projectCostingDTO?.varianceProfit !=0.00) {
					projectCostingDTO.varianceProfitPercentage =(projectCostingDTO?.varianceProfit*100) / projectCostingDTO?.varianceTotal
				} else {
					projectCostingDTO.varianceProfitPercentage = 0.00
				}				
				
				projectCostingList.add(projectCostingDTO)
			}
		}
		return projectCostingList
	}
	
	def getProductCostingSummary(projectProductDtlId) {
		
		def projectProductDetailInstance = ProjectProductDetail.findById(Long.valueOf(projectProductDtlId))
		
		def projectServicetInstanceList = projectProductDetailInstance?.projectServiceDetails
								
		ProjectCostingDTO productCostingDTO = new ProjectCostingDTO()
		
		productCostingDTO.projectCode = projectProductDetailInstance?.project?.code
		productCostingDTO.projectName = projectProductDetailInstance?.project?.name
		productCostingDTO.productCode = projectProductDetailInstance?.projectProduct?.code
						
		BigDecimal plannedLaborTotal = 0.00
				
		BigDecimal plannedExpenseTotal = 0.00
				
		BigDecimal actualExpenseTotal = 0.00
		
		BigDecimal actualLaborTotal = 0.00
		
		BigDecimal plannedHours = 0.00
		
		BigDecimal actualHours = 0.00
		
		BigDecimal plannedStdOHCost = 0.00
		
		BigDecimal actualStdOHCost = 0.00
		BigDecimal varianceStdOHCostTotal = 0.00
		
		projectServicetInstanceList.each { projectServiceInstance ->
					
			//Planned Labor
			if(projectServiceInstance?.plannedLabor) {
				
				projectServiceInstance?.plannedLabor?.laborDetails?.each { projectLaborDetail->
					if(projectLaborDetail?.standardCost) {
						plannedLaborTotal = plannedLaborTotal.add(projectLaborDetail?.standardCost)
					}
					if(projectLaborDetail?.overHeadCost) {
						plannedStdOHCost = plannedStdOHCost.add(projectLaborDetail?.overHeadCost)
					}
					if(projectLaborDetail?.hours) {
						plannedHours = plannedHours.add(projectLaborDetail?.hours)
					}
				}
			}
			//Actual Labor
			if(projectServiceInstance?.actualLabor) {
				
				projectServiceInstance?.actualLabor?.laborDetails?.each { projectActualDetail->
					if(projectActualDetail?.standardTotalCost) {
						actualLaborTotal = actualLaborTotal.add(projectActualDetail?.standardTotalCost)
					}
					if(projectActualDetail?.standardOverheadCost) {
						actualStdOHCost = actualStdOHCost.add(projectActualDetail?.standardOverheadCost)
					}
					if(projectActualDetail?.hours) {
						actualHours = actualHours.add(projectActualDetail?.hours)
					}
				}
			}
			//Planned  Expense total
			if(projectServiceInstance?.plannedExpense) {
				projectServiceInstance?.plannedExpense?.expenseDetails?.each { plannedExpense ->
					if(plannedExpense?.totalCost){
						plannedExpenseTotal = plannedExpenseTotal.add(plannedExpense?.totalCost)
					}
				}
			}			
			//Actual Expense total
			if(projectServiceInstance?.actualExpense) {
				
				projectServiceInstance?.actualExpense?.actualExpenseDetails?.each { actExpense ->
					
					if(actExpense?.monetaryAmount) {
						actualExpenseTotal = actualExpenseTotal.add(actExpense?.monetaryAmount)
					}
				}
			} 
		}
				
		productCostingDTO.plannedLaborTotal = plannedLaborTotal
				
		productCostingDTO.plannedExpenseTotal = plannedExpenseTotal
		
		productCostingDTO.plannedStdOHCost = plannedStdOHCost
				
		productCostingDTO.plannedTotal = plannedLaborTotal.add(plannedExpenseTotal).add(plannedStdOHCost)
								
		productCostingDTO.actualLaborTotal = actualLaborTotal
				
		productCostingDTO.actualExpenseTotal = actualExpenseTotal
		
		productCostingDTO.actualStdOHCost = actualStdOHCost
		
		productCostingDTO.actualTotal = actualLaborTotal.add(actualExpenseTotal).add(actualStdOHCost)
				
		productCostingDTO.varianceLaborTotal = productCostingDTO?.actualLaborTotal - productCostingDTO?.plannedLaborTotal
		
		productCostingDTO.varianceExpenseTotal = productCostingDTO?.actualExpenseTotal - productCostingDTO?.plannedExpenseTotal
		
		productCostingDTO.varianceStdOHCostTotal = productCostingDTO?.actualStdOHCost - productCostingDTO?.plannedStdOHCost
				
		productCostingDTO.varianceTotal = productCostingDTO.actualTotal - productCostingDTO.plannedTotal
						
		
		productCostingDTO.plannedRevenueTotal = projectProductDetailInstance?.plannedRevenue ?:0.00
		
		productCostingDTO.actualRevenueTotal = projectProductDetailInstance?.actualRevenue ?:0.00
		
		productCostingDTO.varianceRevenueTotal = productCostingDTO.actualRevenueTotal -  productCostingDTO.plannedRevenueTotal
		
		
		productCostingDTO.plannedProfit =  productCostingDTO?.plannedRevenueTotal - productCostingDTO?.plannedTotal
		
		productCostingDTO.actualProfit =  productCostingDTO.actualRevenueTotal - productCostingDTO.actualTotal
		
		productCostingDTO.varianceProfit = productCostingDTO.actualProfit - productCostingDTO.plannedProfit
				
		productCostingDTO.plannedHoursTotal = plannedHours
		
		productCostingDTO.actualHoursTotal = actualHours
		
		productCostingDTO.varianceHoursTotal = productCostingDTO.actualHoursTotal - productCostingDTO.plannedHoursTotal
		
		if(productCostingDTO?.plannedTotal !=0.00 && productCostingDTO?.plannedProfit !=0.00) {
			productCostingDTO?.plannedProfitPercentage =(productCostingDTO?.plannedProfit*100) / productCostingDTO?.plannedTotal
		} else {
			productCostingDTO.plannedProfitPercentage = 0.00
		}

		if(productCostingDTO?.actualTotal !=0.00 && productCostingDTO?.actualProfit !=0.00) {
			productCostingDTO.actualProfitPercentage =(productCostingDTO?.actualProfit*100) / productCostingDTO?.actualTotal
		} else {
			productCostingDTO.actualProfitPercentage = 0.00
		}

		if(productCostingDTO?.varianceTotal !=0.00 && productCostingDTO?.varianceProfit !=0.00) {
			productCostingDTO.varianceProfitPercentage =(productCostingDTO?.varianceProfit*100) / productCostingDTO?.varianceTotal
		} else {
			productCostingDTO.varianceProfitPercentage = 0.00
		}	
		
		return productCostingDTO
	}
	
	def getHHProjectMasterList(params) {
		
		def projectList = Project.findAll().code
		
		def hhProjectCriteria = HHProjectMaster.createCriteria()
		def hhProjectCountCriteria = HHProjectMaster.createCriteria()
		
		def projectMasterList = hhProjectCriteria.list(max : params?.max, offset : params?.offset?:0) {		
			and{		
			
				if (params?.projectId) {
					ilike("projectId", "%" + params?.projectId +"%")
				}
				
				if (params?.name) {
					ilike("descr", "%" + params?.name + "%")
				}
					
				if (params?.customerCode) {
					ilike("hhCustId", "%" + params?.customerCode + "%")
				}
				
				/*if(params?.isAssigned == 'false') {
					 if(projectList.size() > 0) {
						not { 'in'("projectId", projectList) }
					}
				}*/
				gt("startDt",'01-JAN-14')			
			}		 
			order("businessUnit","asc")
		}
		//Set projectListSet = new HashSet(projectMasterCrtList);		
		
		def projectMasterCount = projectMasterList.totalCount
		
		return [projectMasterList: projectMasterList, projectMasterCount: projectMasterCount, projectList: projectList, params: params]
	}
	
	def isCustomerExistInDB(params) {		
		return Customer.findByCode(params?.customerCode)?.id		
	}
	
	def saveHHProjectMasterDetails(projectCode) {
		
		try {
		
			def hhProjectCriteria = HHProjectMaster.createCriteria()
		
			def hhProjectMasterList = hhProjectCriteria.list() {
				
				projections {
					groupProperty('projectId')
					groupProperty('businessUnit')
					groupProperty('descr')
					groupProperty('custId')
					groupProperty('hhCustId')
					groupProperty('name1')
					groupProperty('effStatus')
					groupProperty('startDt')
					groupProperty('endDt')
					groupProperty('salesPerson')
					groupProperty('name2')
				}
				and {
					eq("projectId",projectCode)
				}
				order("businessUnit","asc")
			}
			
			log.info "saveHHProjectMasterDetails service HH project List "+hhProjectMasterList.size()
			
			def projectMasterDetail = null
			
			hhProjectMasterList.each { it ->
			
				 projectMasterDetail = new HHProjectMasterDetail()
						 
				  log.info "Project id "+it[0] +" businessUnit "+it[1]
				  
				 projectMasterDetail.projectId = it[0]
				 
				 projectMasterDetail.businessUnit = it[1]
				 
				 projectMasterDetail.descr = it[2]
				 
				 projectMasterDetail.custId = it[3]
				 
				 projectMasterDetail.hhCustId = it[4]
				 
				 projectMasterDetail.name1 = it[5]
				 
				 projectMasterDetail.effStatus = it[6]
				 
				 projectMasterDetail.startDt = it[7]
				 
				 projectMasterDetail.endDt = it[8]
				 
				 projectMasterDetail.salesPerson =it[9]
				 
				 projectMasterDetail.name2 = it[10]
				 
				projectMasterDetail.save(flush: true, saveOnError: true)
			}
			
		}catch(Exception e) {
			log.error "saveHHProjectMasterDetails service Exception "+ e
			throw new RuntimeException()
			return
		}
	}
	
	def getHHProjectMasterDtlByProjectCode(projectCode) {
		return HHProjectMasterDetail.findAllByProjectId(projectCode)
	}
	
	def getCustomerList(params) {
		
		log.info "getCustomerList @ CustomerService Start"
		def loggedInADInstance = userService.getLoggedInAccountDirector()
		def customerCriteria = Customer.createCriteria()
		def customerCountCriteria = Customer.createCriteria()
		
		def customerInstanceList = customerCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			
			and {
				
				if (params?.dialogCustomerCode) {
					ilike("code", "%" + params?.dialogCustomerCode + "%")
				}
				
				if (params?.dialogCustomerName) {
					ilike("name", "%" + params?.dialogCustomerName + "%")
				}
				
				if(loggedInADInstance) {					
					eq("acctExecutive", loggedInADInstance?.employee)					
				}
				
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def customerInstanceCount = customerInstanceList.totalCount
		
		log.info "Customer List is "+ customerInstanceList.size()
		log.info "getCustomerList @ CustomerService End"
		
		return [customerInstanceList: customerInstanceList, customerInstanceCount: customerInstanceCount, params: params]
	}
	
	def getParentProjectList(params) {
		
		log.info "getParentProjectList @ ParentProjectService Start"
		
		if(params?.parentProjectID){
			params.put("parentProjectID",params?.parentProjectID)
		}
		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectCriteria = Project.createCriteria()
		def projectCountCriteria = Project.createCriteria()
		
		def projectInstanceList = projectCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			
			and {
				
				if (params?.dialogProjectCode) {
					ilike("code", "%" + params?.dialogProjectCode + "%")
				}
				
				if (params?.dialogProjectName) {
					ilike("name", "%" + params?.dialogProjectName + "%")
				}
				
				if(params?.parentProjectID){
					ne("id",params?.parentProjectID as Long)
				}
				
				// IF Account Director Logged in then Account Director related project display
				if(loggedInUserInstance) {
					or {
						eq("accExecutive", loggedInUserInstance?.employee)	
												
						createAlias('projectEmployees', 'pm')
						eq('pm.employee', loggedInUserInstance?.employee)
					}
				} 
				
			}
			order("${params.sort ?: 'code'}","${params.order ?: 'asc'}")
		}
		
		def projectInstanceCount = projectInstanceList.totalCount
		
		log.info "project List is "+ projectInstanceList.size()
		log.info "getParentProjectList @ ParentProjectService End"
		
		return [projectInstanceList: projectInstanceList, projectInstanceCount: projectInstanceCount, params: params]
	}
	
	def getAllActualLaborEmployeeList(params) {
		
		log.info "getAllActualLaborEmployeeList @ ParentProjectService Start"
		
		def employeeDetailDTOList = []
		
		def projectDetailInstance = ProjectServiceDetail.get(params?.id)
		
		def projectId = projectDetailInstance?.projectProductDetail?.project?.id
		
		def actualLaborCriteria = ProjectActualLaborDetail.createCriteria()
		
		def actualLaborCriteriaList = actualLaborCriteria.list() {
			createAlias("project", "project")
			createAlias("employee", "emp")
			and {	
				eq("project.id", projectId)				
			}	
			order("emp.firstName","asc")			
		}
		
		def employeeGroupList = actualLaborCriteriaList.groupBy{ emp -> emp?.employee }
		employeeGroupList.each { empInstance, actualLaborEmpList ->
			
			def employeeHours = 0.00

			def employeeStandardCost = 0.00
			
			def employeeStandardOverheadCost = 0.00
			
			def employeeStandardTotalCost = 0.00
			
			def employeeActualCost = 0.00
			
			actualLaborEmpList.each { actualLaborActivityCodeInstance ->
											
				employeeHours = employeeHours.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.hours?:0.00))
				
				employeeStandardCost = employeeStandardCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardCost))
				
				employeeStandardOverheadCost = employeeStandardOverheadCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardOverheadCost))
				
				employeeStandardTotalCost = employeeStandardTotalCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.standardTotalCost))
				
				employeeActualCost = employeeActualCost.add(MiscUtils.removePrecision(actualLaborActivityCodeInstance?.actualCost))
				
			}
		
			def employeeDetailDTO = new EmployeeDetailDTO(empInstance?.id, empInstance?.getEmployeeName(), employeeHours, employeeStandardCost, employeeStandardOverheadCost, employeeStandardTotalCost, employeeActualCost, null, null)
			employeeDetailDTOList.add(employeeDetailDTO)
		}
				
		def isAdmin = false
		
		def loggedInUser = userService.getLoggedInUser()
		
		if(loggedInUser.username?.toString()?.trim()?.equals(Constants.SUPER_ADMIN_USERNAME) == true) {
			isAdmin = true
		}
		log.info "getAllActualLaborEmployeeList @ ParentProjectService End"
		
		return [employeeDetailDTOList: employeeDetailDTOList, isAdmin: isAdmin, params: params, projectDetailInstance: projectDetailInstance]
	}
	
	def getActualLaborActivityDetailList(params) {
		
		log.info "getActualLaborActivityDetailList @ ParentProjectService Start"
		
		def projectDetailInstance = ProjectServiceDetail.get(params?.prodId)
		def projectId = projectDetailInstance?.projectProductDetail?.project?.id
				
		def actualLaborEmployeeCriteria = ProjectActualLaborDetail.createCriteria()

		def actualLaborActivityCriteriaList = actualLaborEmployeeCriteria.list() {
   
		   createAlias("project", "project")
		   createAlias("employee", "emp")
		   createAlias("laborActivityCode", "lbrActCode")
		   and {
				 eq("project.id", projectId)
			  
				 eq("emp.id", Long.valueOf(params?.empId))
		   }			      
		   order("lbrActCode.code","asc")	
		   
		}
		
		def activityHours = 0.00
		
		def activityStandardCost = 0.00
		
		def activityStandardOverheadCost = 0.00
		
		def activityStandardTotalCost = 0.00
		
		def activityActualCost = 0.00
		
		actualLaborActivityCriteriaList.each { actualInstance->
			
			activityHours = activityHours.add(MiscUtils.removePrecision(actualInstance?.hours?:0.00))
			
			activityStandardCost = activityStandardCost.add(MiscUtils.removePrecision(actualInstance?.standardCost))
			
			activityStandardOverheadCost = activityStandardOverheadCost.add(MiscUtils.removePrecision(actualInstance?.standardOverheadCost))
			
			activityStandardTotalCost = activityStandardTotalCost.add(MiscUtils.removePrecision(actualInstance?.standardTotalCost))
			
			activityActualCost = activityActualCost.add(MiscUtils.removePrecision(actualInstance?.actualCost))
		}
			
		def isAdmin = false
		
		def loggedInUser = userService.getLoggedInUser()
		
		if(loggedInUser.username?.toString()?.trim()?.equals(Constants.SUPER_ADMIN_USERNAME) == true) {
			isAdmin = true
		}
		
		def activityTotalMap =[:]
		
		
		activityTotalMap.put("activityHours", activityHours)
		activityTotalMap.put("activityStandardCost", activityStandardCost)
		activityTotalMap.put("activityStandardOverheadCost", activityStandardOverheadCost)
		activityTotalMap.put("activityStandardTotalCost", activityStandardTotalCost)
		activityTotalMap.put("activityActualCost", activityActualCost)
		
		log.info "getActualLaborActivityDetailList @ ParentProjectService End"
		
		return [actualLaborActivityCriteriaList: actualLaborActivityCriteriaList, activityTotalMap: activityTotalMap, isAdmin:isAdmin, params: params, projectDetailInstance: projectDetailInstance]
	}
	
	private projectFieldValidation(projectInstance, params, isCustomerExistInDB) {
		
		if(projectInstance?.projectEmployees?.size() <= 0) {
						
			projectInstance.errors.rejectValue('projectEmployees', 'org.solcorp.etech.Project.project.manager.nullable')
			
		}
		
		if(params?.parentProjectTxt) {
			
			Project parentProjectInstance = Project.findByCode(params?.parentProjectTxt)
			
			if(parentProjectInstance == null) {
				
				projectInstance?.parentProjectTxt = params?.parentProjectTxt
				projectInstance.errors.rejectValue('parentProjectTxt', 'org.solcorp.etech.Project.parentProjectTxt.notFound')
				
			} else {
			
				projectInstance?.parentProject = parentProjectInstance
				
			}			
		}
		
		if(params?.customerTxt) {
						
			if(!isCustomerExistInDB) {
				
				projectInstance?.customerTxt = params?.customerTxt
				projectInstance.errors.rejectValue('customerTxt', 'org.solcorp.etech.Project.customer.notFound')
				
			}		
		}
	}
	
	/**
	 * This method used to Import data to ProjectProductDetails and ProjectServiceDetails from ProductDetail Table
	 * @return
	 */
	def importProjectDetailData() {
		
		log.info "importProjectDetailData Service Start"
		def projectCriteria = Project.createCriteria()		
		def projectList = projectCriteria.list() {			
			
		   and {					  
		   	// inList("id", [23837L,23853L,24049L])
			   isNotEmpty("projectDetails")
		   }	   
		}		
		//Get All Project From Project Table
		projectList.each { projectInstance ->
			
			try {
				
				def newProjectInstance = Project.get(projectInstance?.id)
		
				if(projectInstance?.projectDetails) {
				
					def productDetailMap = projectInstance?.projectDetails?.groupBy{ prod -> prod?.projectProduct }
				
					productDetailMap?.each { productDetailInstance, projectDtlInstanceList ->
					
						def projectProductDetail = new ProjectProductDetail()
					
						projectProductDetail?.plannedRevenue = 0.00
						projectProductDetail?.projectProduct = productDetailInstance
						
						projectDtlInstanceList?.each{ projectDetailInstance ->
						
							def projectServiceDetail = new ProjectServiceDetail()
								
							projectServiceDetail?.scheduleStartDate = projectDetailInstance?.scheduleStartDate
							projectServiceDetail?.scheduleCompDate = projectDetailInstance?.scheduleCompDate
							projectServiceDetail?.reviseStartDate = projectDetailInstance?.reviseStartDate
							projectServiceDetail?.reviseCompDate = projectDetailInstance?.reviseCompDate
							projectServiceDetail?.actualStartDate = projectDetailInstance?.actualStartDate
							projectServiceDetail?.actualCompDate = projectDetailInstance?.actualCompDate
							projectServiceDetail?.service = projectDetailInstance?.service
							projectServiceDetail?.plannedLabor = projectDetailInstance?.plannedLabor
							projectServiceDetail?.plannedExpense = projectDetailInstance?.plannedExpense
							projectServiceDetail?.actualLabor = projectDetailInstance?.actualLabor
												
							projectProductDetail.addToProjectServiceDetails(projectServiceDetail)
						}
						newProjectInstance.addToProjectProductDetails(projectProductDetail)
					}
					newProjectInstance.save(flush: true, saveOnError: true)
				}
				
				
			}catch(Exception e) {
				println "Project Exception "+e
			}
						
		}
		log.info "importProjectDetailData Service End"
	}
	/**
	 *
	 * @return
	 */
	def getEmpByProjectManagerTypeList(params) {
		
		log.info "getEmpByProjectManagerTypeList @ ProjectService Start"
		//Get Project Manager List form session 
		def existingEmployeeList = []
		def projectManagerList = getProjectManagerFromSession(params?.projectId)
		def employeeList = projectManagerList?.employee
		
		employeeList.each {
			existingEmployeeList.add(it?.id)
		}
		def loggedInPMInstnace = userService.getLoggedInProjectManager()
		def userCriteria = User.createCriteria()
		def userInstanceList = userCriteria.list(max: params?.max ?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {
			and {		
					createAlias('employee', 'employee')
					createAlias('roles', 'role')
					isNotNull("employee")
					eq("role.name", RoleType.ROLE_PROJECT_MANAGER.name())
					
					if(params?.code) {
						ilike("employee.code", "%"+ params?.code +"%")
					}
					if(params?.firstName) {
						ilike("employee.firstName", "%"+ params?.firstName +"%")
					}
					if(params?.lastName) {
						ilike("employee.lastName", "%"+ params?.lastName +"%")
					}
					if(existingEmployeeList != null && existingEmployeeList?.size() >0) {
						not { 'in'("employee.id", existingEmployeeList?:0) }
					}
					if(loggedInPMInstnace) {
						eq("employee", loggedInPMInstnace?.employee) 
					}
					
				}
				order("${params.sort ?: 'employee.code'}","${params.order ?: 'asc'}")
		}
		def employeeInstanceList = userInstanceList.employee
			
		def userInstanceCount = userInstanceList.totalCount
		
		log.info "getEmpByProjectManagerTypeList @ ProjectService End"
		
		return [employeeInstanceList: employeeInstanceList, employeeInstanceCount: userInstanceCount, params: params]
	}
	
	/**
	 * This Method used to add Project Manager in Session
	 * @param params
	 * @return
	 */
	def addProjectMangerInSession(params) {
		
		if(params?.employeeId) { 
			
			def projectManagerList = []
		
			 // Get existing ProjectEmployee Instance List from Session and add in new List
			def existingUserList = getProjectManagerFromSession(params?.projectId)
			
			if(existingUserList !=null && existingUserList?.size() > 0) {
				projectManagerList.addAll(existingUserList)
			}
			
			def employeeInstance = Employee.get(params?.employeeId)
			if(employeeInstance) {
				
				def projectEmployees =new ProjectEmployee()
				projectEmployees.employee = employeeInstance
				projectManagerList.add(projectEmployees)
			}
			setProjectManagerInstanceInSession(projectManagerList, params?.projectId)
		}
	}
	
	/**
	 * This Method used to remove Project Manager From session
	 * @return
	 */
	def removeProjectMangerFromSession(params) {
		
		if(params?.employeeId) {
			
			// Get existing ProjectEmployee Instance List from Session
			def projectManagerList = getProjectManagerFromSession(params?.projectId)
			
			if(projectManagerList !=null && projectManagerList?.size() > 0) {
				
				def employeeInstance = Employee.get(params?.employeeId)
				
				if(employeeInstance) {
				
					def newProjectManagerList = []
					
					projectManagerList.each{ projectEmployee->
						
						if(projectEmployee?.employee?.id != employeeInstance?.id) {
							
							newProjectManagerList.add(projectEmployee)
							
						}
					}
					
					setProjectManagerInstanceInSession(newProjectManagerList, params?.projectId)
				}
			}
		}
	}
	
	def setDefaultAccountExecutive(params) {
		def customerInstance = Customer.findById(params?.customerId)
		
		if(customerInstance?.acctExecutive?.code != null) {
			return [acctExecutiveId: customerInstance?.acctExecutive?.id, acctExecutiveCode: customerInstance?.acctExecutive?.code, acctExecutiveName: customerInstance?.acctExecutive?.getEmployeeName()] as JSON
		} else {
			return null
		}
	}
	
	def checkAccountExecutive(params) {
		
		def employee = Employee.findByCode(params?.accountExecutiveCode)
		if(employee){
			return employee?.id
		}else{
			return null
		}
	}
	
	def isValidProjectAccess(projectId) {		
		def loggedInUserInstance = userService.getLoggedInUserRoleBaseAccess()
		
		def projectCriteria = Project.createCriteria()
		def projectInstanceList = projectCriteria.list() {
			and {			
			
				eq("id", projectId)			
		 
				// IF Account Director Logged in then Account Director related project display
				if(loggedInUserInstance) {
					or {
						eq("accExecutive", loggedInUserInstance?.employee)
					
						createAlias('projectEmployees', 'pm')
						eq('pm.employee', loggedInUserInstance?.employee)
					}
				} 	 
			}		
		}		
		if(projectInstanceList?.size() > 0) {
			return true
		} else {
			return false
		}		
	}
	/**
	 * Get HH Expense Master List By Project Code
	 * @param params
	 * @return
	 */
	def getHHExpenseMasterListByProjectCode(params) {
		
		log.info "getHHExpenseMasterList @ HHExpenseMasterListService Start"
		def projectInstance = Project.get(Long.valueOf(params?.id))
		def hhExpenseMasterInstanceList = []
		
		if(projectInstance?.code) {
		
			def projectActualExpenseDetailCriteria = ProjectActualExpenseDetail.createCriteria()			
			hhExpenseMasterInstanceList = projectActualExpenseDetailCriteria.list(max: params?.max?: Constants.DEFAULT_PAGINATION_RECORDS, offset: params?.offset?:0) {	
				and {			
					eq("project", projectInstance)
				}
				order("postedDate","desc")
			}
		}
	
		log.info "HHExpenseMaster List is " + hhExpenseMasterInstanceList.size()
		log.info "getHHExpenseMasterList @ HHExpenseMasterListService End"
		
		return [hhExpenseMasterInstanceList: hhExpenseMasterInstanceList, hhExpenseMasterInstanceCount: hhExpenseMasterInstanceList.totalCount, params: params]
	}
	
	def getAllServiceActualExpenseList(params) {
		
		log.info "getAllServiceActualExpenseList @ ProjectService Start"		
				
		def projectDetailInstance = ProjectServiceDetail.get(params?.id)		
		def projectId = projectDetailInstance?.projectProductDetail?.project?.id
		
		def actualExpenseCriteria = ProjectActualExpenseDetail.createCriteria()		
		def actualExpenseList = actualExpenseCriteria.list() {			
			and {
					createAlias("project", "project")
					createAlias("expense", "exp")
					eq("project.id", projectId)				
			}
		
			order("exp.code","asc")
		} 
			
		 def actualExpenseInstacenList = []
		 def expenseCodeMap = actualExpenseList.groupBy{ exp -> exp?.expense }
		 
		 expenseCodeMap.each { expInstance, actExpList->		
			 
			 def actualExpenseDetailDTO = new ActualExpenseDetailDTO()
			 actualExpenseDetailDTO.expenseId = expInstance?.id
			 actualExpenseDetailDTO.expenseCode = expInstance?.code
			 
			 def vendorId = ''			 
			 def vendorName = ''
			 def invoice = ''			 
			 def glAmount = '' 
			 def monetaryAmount = 0.00 
			 
			 actExpList.each { exp ->
				 monetaryAmount = monetaryAmount.add(exp?.monetaryAmount?:0.00)				   				 
			 }			  	 
			 actualExpenseDetailDTO.amount = monetaryAmount			 
			 actualExpenseInstacenList.add(actualExpenseDetailDTO)
		 }		 	
		 
		def isAdmin = false		
		def loggedInUser = userService.getLoggedInUser()		
		if(loggedInUser.username?.toString()?.trim()?.equals(Constants.SUPER_ADMIN_USERNAME) == true) {
			isAdmin = true
		}
		log.info "getAllServiceActualExpenseList @ ProjectService End"		
		return [actualExpenseInstacenList: actualExpenseInstacenList, isAdmin: isAdmin, params: params, projectDetailInstance: projectDetailInstance]
	}
	
	def getActualExpenseDetailByExpCode(params) {		
		log.info "getActualExpenseDetailByExpCode @ ProjectService Start"
		
		def projectDetailInstance = ProjectServiceDetail.get(params?.prodId)
		def projectId = projectDetailInstance?.projectProductDetail?.project?.id
				
		def actualExpenseCriteria = ProjectActualExpenseDetail.createCriteria()		
		def actualExpenseList = actualExpenseCriteria.list() {			
			and {
					createAlias("project", "project")
					eq("expense.id", Long.valueOf(params?.expId))
					eq("project.id", projectId)
				
			}
			order("name1","asc")
		}		 
		def isAdmin = false		
		def loggedInUser = userService.getLoggedInUser()		
		if(loggedInUser.username?.toString()?.trim()?.equals(Constants.SUPER_ADMIN_USERNAME) == true) {
			isAdmin = true
		}
		log.info "getActualExpenseDetailByExpCode @ ProjectService End"
		
		return [actualExpenseList: actualExpenseList, isAdmin: isAdmin, params: params, projectDetailInstance: projectDetailInstance]
	}
	/**
	 * This Method used to set Project Manager Instance list in Session
	 * @param projectManagerList
	 * @return
	 */
	private static setProjectManagerInstanceInSession(projectManagerList, sessionName) {
		
		def session = RequestContextHolder.currentRequestAttributes().getSession()
		
		session[sessionName?: Constants.PROJECT_MANAGER_SESSION_ID] = projectManagerList
	}
	private static getProjectManagerFromSession(sessionName) {
		def session = RequestContextHolder.currentRequestAttributes().getSession()
		List userList = []
		userList = session[sessionName?:Constants.PROJECT_MANAGER_SESSION_ID]
		return userList
	
	}
	private static clearProjectManagerSession(sessionName) {
		def session = RequestContextHolder.currentRequestAttributes().getSession()		
		 session[sessionName?: Constants.PROJECT_MANAGER_SESSION_ID] = []
	}
}