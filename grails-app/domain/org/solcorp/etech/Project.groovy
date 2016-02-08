package org.solcorp.etech
import javax.persistence.Transient

import org.grails.databinding.BindingFormat;
import org.solcorp.etech.utils.DateFormatUtils;
/**
 * This class is used to store Project information
 * @author hjjogiya
 *
 */
class Project extends Auditable {
	
	Long id	
    
	String code
    
	String name
	
	ProjectCategory projectCategory
	
	ProjectType projectType 
	
	BillCodeType billCodeType 
	
	Customer customer
	
	Employee accExecutive
		
	Project parentProject
	
	ProjectStatusType status
	
	String comments
	
	@Transient
	String parentProjectTxt
	
	@Transient
	String customerTxt
	
	@Transient
	String accExecutiveTxt
		
	@BindingFormat("MM/dd/yyyy")
	Date startDate
	
	@BindingFormat("MM/dd/yyyy")
	Date endDate
	
	@BindingFormat("MM/dd/yyyy")
	Date lastTrx
	
	String businessUnit
	
	@Transient
	BigDecimal plannedRevenueTotal
	
	@Transient
	BigDecimal actualRevenueTotal
	@Transient
	BigDecimal varianceRevenueTotal
	
	@Transient
	BigDecimal plannedProfit
	
	@Transient
	BigDecimal actualProfit
	
	@Transient
	BigDecimal varianceProfit
	
	@Transient
	BigDecimal plannedTotal
	
	@Transient
	BigDecimal actualTotal
	
	@Transient
	BigDecimal plannedHoursTotal
	
	@Transient
	BigDecimal actualHoursTotal
	
	@Transient
	BigDecimal varianceHoursTotal
	
	@Transient
	BigDecimal varianceTotal
	
	@Transient
	BigDecimal plannedProfitPercentage
	
	@Transient
	BigDecimal actualProfitPercentage
	
	@Transient
	BigDecimal varianceProfitPercentage
	
	@Transient
	BigDecimal plannedLaborTotal
	
	@Transient
	BigDecimal plannedExpenseTotal
		
	@Transient
	BigDecimal actualLaborTotal
	
	@Transient
	BigDecimal actualExpenseTotal
	
	@Transient
	BigDecimal varianceLaborTotal
	
	@Transient
	BigDecimal varianceExpenseTotal
	@Transient
	BigDecimal varianceOHCostTotal
	
	@Transient
	BigDecimal actualStdOHCostTotal
	
	@Transient
	BigDecimal plannedStdOHCostTotal
	
	List projectEmployees = new ArrayList()
	
	List projectProductDetails = new ArrayList()
	
	static hasMany = [projectProductDetails: ProjectProductDetail, projectEmployees: ProjectEmployee]
	
	static transients = ['parentProjectTxt', 'customerTxt', 'accExecutiveTxt', 'plannedRevenueTotal', 'actualRevenueTotal', 'varianceRevenueTotal', 
		'plannedTotal','actualTotal', 'varianceTotal', 'plannedProfit', 'actualProfit','varianceProfit', 'plannedHoursTotal','actualHoursTotal', 'varianceHoursTotal',
		'plannedProfitPercentage', 'actualProfitPercentage', 'varianceProfitPercentage', 'plannedLaborTotal', 'plannedExpenseTotal', 'actualLaborTotal', 'actualExpenseTotal',
		'varianceLaborTotal', 'varianceExpenseTotal', 'varianceOHCostTotal', 'actualStdOHCostTotal', 'plannedStdOHCostTotal']
	
    static constraints = {
		code(nullable: false, blank: false, unique: true, maxSize: Constants.PROJECT_CODE_LENGTH )
		name(nullable: false, blank: false, maxSize: Constants.PROJECT_NAME_LENGTH )
		projectCategory(nullable: true)
		projectType(nullable: false)
		billCodeType(nullable: false)
		status(nullable: false)
		customer(nullable: false)
		accExecutive(nullable: false)				
		parentProject(nullable: true)		
		dateCreated(nullable: true, display: false)
		lastUpdated(nullable: true, display: false)
		comments(nullable: true, blank: true)
		startDate(nullable: true)
		endDate(nullable: true)
		lastTrx(nullable: true)
		businessUnit(nullable: true, blank: true)
    }
	
	static mapping = {
		projectProductDetails cascade: 'all-delete-orphan'
		projectEmployees cascade: 'all-delete-orphan'
	}
	
	@Transient
	def getProductSummary() {
		//Calculate Planned Labor and Planned Expense 
		this.plannedLaborTotal = this.getAllPlannedLaborTotal()
		this.plannedExpenseTotal = this.getAllPlannedExpenseTotal()		
		this.plannedStdOHCostTotal = this.getAllPlannedStdOHCostTotal()
		
		this.plannedTotal = this?.plannedLaborTotal.add(this?.plannedExpenseTotal).add(this.plannedStdOHCostTotal)
		
		//Calculate Actual Labor and Actual Expense 
		this.actualLaborTotal  = this.getAllAcutalLaborTotal()
		this.actualExpenseTotal = this.getHHExpenseMonetaryAmountTotal() ?:0.00
		
		this.actualStdOHCostTotal = getAllActualStdOHCostTotal()
		this.actualTotal  = this?.actualLaborTotal.add(this?.actualExpenseTotal).add(this.actualStdOHCostTotal)
		
		//Calculate Variance Labor and Variance Expense 
		this.varianceLaborTotal = this?.actualLaborTotal -this?.plannedLaborTotal
		this.varianceExpenseTotal = this?.actualExpenseTotal - this?.plannedExpenseTotal
		this.varianceOHCostTotal = this?.actualStdOHCostTotal - this?.plannedStdOHCostTotal
		this.varianceTotal = this?.actualTotal -this?.plannedTotal

		//Calculate Revenue		
		this.plannedRevenueTotal = getAllPlannedRevenueTotal()
		this.actualRevenueTotal = getAllActualRevenueTotal()
		this.varianceRevenueTotal = this.actualRevenueTotal - this.plannedRevenueTotal
		  
		//Calculate Profit
		this.plannedProfit = this.plannedRevenueTotal - this.plannedTotal		
		this.actualProfit = this.actualRevenueTotal - this.actualTotal		
		this.varianceProfit = varianceRevenueTotal - this.varianceTotal

		//Calculate Profit Percentage
		if(this.plannedTotal !=0.00 && this.plannedProfit !=0.00) {
			this.plannedProfitPercentage =(this.plannedProfit*100) / this.plannedTotal			
		} else {
			this.plannedProfitPercentage = 0.00
		} 

		if(this.actualTotal !=0.00 && this.actualProfit !=0.00) {
			this.actualProfitPercentage =(this.actualProfit*100) / this.actualTotal
		} else {
			this.actualProfitPercentage = 0.00
		}

		if(this.varianceTotal !=0.00 && this.varianceProfit !=0.00) {
			this.varianceProfitPercentage =(this.varianceProfit*100) / this.varianceTotal
		} else {
			this.varianceProfitPercentage = 0.00
		}

		//Calculate Hours
		this.plannedHoursTotal = getAllPlannedHoursTotal()
		this.actualHoursTotal = getAllActualHoursTotal()
		this.varianceHoursTotal = this?.actualHoursTotal.minus(this?.plannedHoursTotal) 
		
		return ""
	}
	
	@Transient
	def getAllPlannedRevenueTotal() {
		
		BigDecimal plannedRevenueTotal = 0.00
				
		projectProductDetails.each { productDtlInstance ->
			
			if(productDtlInstance?.plannedRevenue) {
				
				plannedRevenueTotal = plannedRevenueTotal.add(productDtlInstance?.plannedRevenue)
			}
		}		
		return plannedRevenueTotal
	}
	
	@Transient
	def getAllActualRevenueTotal() {
		
		BigDecimal actualRevenueTotal = 0.00
				
		projectProductDetails.each { productDtlInstance ->
			
			if(productDtlInstance?.actualRevenue) {
					
				actualRevenueTotal = actualRevenueTotal.add(productDtlInstance?.actualRevenue)
			}
		}
		return actualRevenueTotal
	}
	
	@Transient
	def getAllPlannedLaborTotal() {
		
		BigDecimal plannedLaborTotal = 0.00
				
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->			
		
				projectDtl?.plannedLabor?.laborDetails?.each { projectLaborDetail->
					
					if(projectLaborDetail?.standardCost) {
						plannedLaborTotal = plannedLaborTotal.add(projectLaborDetail?.standardCost)
					}
				}
			}			
		}
		return plannedLaborTotal 
	}
	
	@Transient
	def getAllPlannedExpenseTotal() {
	
		BigDecimal plannedExpenseTotal = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->			
		
				projectDtl?.plannedExpense?.expenseDetails?.each { plannedExpense ->
					if(plannedExpense?.totalCost){
						plannedExpenseTotal = plannedExpenseTotal.add(plannedExpense?.totalCost)
					}
				}				
			}
		}
		return plannedExpenseTotal
	}
	
	@Transient
	def getAllAcutalLaborTotal() {
	
		BigDecimal actualLaborTotal = 0.00
		projectProductDetails.each { productDtlInstance ->			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
				projectDtl?.actualLabor?.laborDetails?.each { projectActualDetail->
					if(projectActualDetail?.standardTotalCost) {
						actualLaborTotal = actualLaborTotal.add(projectActualDetail?.standardTotalCost)
					}
				}				
			}
		}
		return actualLaborTotal
	}
	
	@Transient
	def getAcutalLaborActCostTotal() {
	
		BigDecimal totalActCostSum = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
		
				if(projectDtl?.actualLabor) {
				
					projectDtl?.actualLabor?.laborDetails?.each { projectActualDetail->
						
						if(projectActualDetail?.actualCost) {
							totalActCostSum = totalActCostSum.add(projectActualDetail?.actualCost)
						}
					}					
				}
			}
		}
		return totalActCostSum
	}
		
	@Transient
	def getAllVarianceLaborTotal() {
		
		BigDecimal varianceLaborTotal = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
				varianceLaborTotal = varianceLaborTotal.add(projectDtl?.getServiceLaborVarianceTotal())
			}
		}		
		return varianceLaborTotal		
	}
	
	@Transient
	def getAllPlannedHoursTotal() {
		
		BigDecimal plannedHours = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
				
				if(projectDtl?.plannedLabor) {
					
					projectDtl?.plannedLabor?.laborDetails?.each { projectLaborDetail->
						
						if(projectLaborDetail?.hours) {
							plannedHours = plannedHours.add(projectLaborDetail?.hours)
						}
					}			
				}
			}
		}
		return plannedHours
	}
	
	@Transient
	def getAllActualHoursTotal() {
		
		BigDecimal actualHours = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
				
				if(projectDtl?.actualLabor) {
					
					projectDtl?.actualLabor?.laborDetails?.each { projectActualDetail->
						
						if(projectActualDetail?.hours) {
							actualHours = actualHours.add(projectActualDetail?.hours)
						}
					}
				}
			}
		}
		return actualHours
	}
	
	@Transient
	def getHHExpenseMonetaryAmountTotal() {
		
		BigDecimal actualExpenseTotal = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
				projectDtl?.actualExpense?.actualExpenseDetails?.each { actExpense ->
					
					if(actExpense?.monetaryAmount) {
						actualExpenseTotal = actualExpenseTotal.add(actExpense?.monetaryAmount)
					}
				}				
			}
		}
		return actualExpenseTotal
	}
	
	@Transient
	def getAllPlannedStdOHCostTotal() {
		
		BigDecimal plannedOHCost = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
				projectDtl?.plannedLabor?.laborDetails?.each { projectLaborDetail->
					if(projectLaborDetail?.overHeadCost) {
						plannedOHCost = plannedOHCost.add(projectLaborDetail?.overHeadCost)
					}
				}				 
			}
		}
		return plannedOHCost
	}
	
	@Transient
	def getAllActualStdOHCostTotal() {
		
		BigDecimal actualOHCost = 0.00
		projectProductDetails.each { productDtlInstance ->
			
			productDtlInstance?.projectServiceDetails?.each { projectDtl ->
				
				if(projectDtl?.actualLabor) {
					
					projectDtl?.actualLabor?.laborDetails?.each { projectActualDetail->
						
						if(projectActualDetail?.standardOverheadCost) {
							actualOHCost = actualOHCost.add(projectActualDetail?.standardOverheadCost)
						}
					}
				}
			}
		}
		return actualOHCost
	}
	 
	/*@Transient
	static def createProjectFromHHProject(hhProjectInstance, customer, User createdBy) {
		Project projectInstance = new Project()
		
		projectInstance.code = hhProjectInstance.projectId.trim()
		projectInstance.name = hhProjectInstance.descr
		projectInstance.status = getProjectStatusFromHHProjectStatus(hhProjectInstance.effStatus)
		
		def customer = Customer.findByCode(hhProjectInstance.hhCustId)
		if(! customer) {
			customer = Customer.findByCode(Constants.DEFAULT_CUSTOMER_CODE)
			projectInstance.comments = "Assigned to the default customer as the customer - HH_CUST_ID: " + hhProjectInstance.hhCustId + " of this project is not found in Customer List."
		}
		
		projectInstance.customer = customer
		projectInstance.createdBy = createdBy
		projectInstance.updatedBy = createdBy
		projectInstance.accExecutive = Employee.findByCode(Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE)
		projectInstance.businessUnit = hhProjectInstance.businessUnit
		projectInstance.startDate = hhProjectInstance.startDt
		projectInstance.endDate = hhProjectInstance.endDt
		
		// Setting the default values
		projectInstance.projectType = ProjectType.UTILIZED //ProjectType.CUSTOMER
		projectInstance.billCodeType = BillCodeType.FLAT_RATE
		
		return projectInstance
	}*/
		
	@Transient
	static def getProjectStatusFromHHProjectStatus(status) {
		ProjectStatusType projectStatus = ProjectStatusType.OPEN// Default
		
		if(status) {
			if (status.equalsIgnoreCase(Constants.HH_PROJECTSTATUS_ACTIVE)) {
				projectStatus = ProjectStatusType.OPEN
			} else if (status.equalsIgnoreCase(Constants.HH_PROJECTSTATUS_INACTIVE)) {
				projectStatus = ProjectStatusType.CLOSE
			}
		}
		
		return projectStatus
	}
	
	@Transient
	static def createProjectFromHHProject(HHProjectMaster hhProjectInstance, Customer customer, User createdBy) {
		Project projectInstance = new Project()
		
		projectInstance.code = hhProjectInstance.projectId.trim()
		projectInstance.name = hhProjectInstance.descr
		projectInstance.status = getProjectStatusFromHHProjectStatus(hhProjectInstance.effStatus)
		
		/*def customer = Customer.findByCode(hhProjectInstance.hhCustId)
		if(! customer) {
			customer = Customer.findByCode(Constants.DEFAULT_CUSTOMER_CODE)
			projectInstance.comments = "Assigned to the default customer as the customer - HH_CUST_ID: " + hhProjectInstance.hhCustId + " of this project is not found in Customer List."
		}*/
		
		projectInstance.customer = customer 
		projectInstance.createdBy = createdBy
		projectInstance.updatedBy = createdBy
		projectInstance.accExecutive = Employee.findByCode(Constants.DEFAULT_EXECUTIVE_EMPLOYEE_CODE)
		projectInstance.businessUnit = hhProjectInstance.businessUnit		
		projectInstance.startDate = hhProjectInstance.startDt
		projectInstance.endDate = hhProjectInstance.endDt
		
		// Setting the default values
		projectInstance.projectType = ProjectType.UTILIZED //ProjectType.CUSTOMER
		projectInstance.billCodeType = BillCodeType.FLAT_RATE
		
		return projectInstance
	}
	
	@Transient
	def isSystemJobUser() {
		def isSystemJobUser = false
		def systemJobUser = User.findByUsername(Constants.SYSTEM_JOB_USERNAME)
		if(this.updatedBy?.id == systemJobUser?.id) {
			isSystemJobUser = true
		}
		return isSystemJobUser
	}
}
