package org.solcorp.etech

/**
 * This class is used to store Project information
 * @author hjjogiya
 *
 */
class ProjectProductDetail extends Auditable {
	
	Long id	
	
	BigDecimal plannedRevenue
	
	BigDecimal actualRevenue
	
	BigDecimal varianceRevenue
	
	ProjectProduct projectProduct
	
	List projectServiceDetails = new ArrayList()
	
	static hasMany = [projectServiceDetails: ProjectServiceDetail]
	
	static belongsTo = [project: Project]
	
    static constraints = {
		plannedRevenue(nullable: true, blank: true)
		actualRevenue(nullable: true, blank: true)
		varianceRevenue(nullable: true, blank: true)
		projectProduct(nullable: false)
    }
	static mapping = {
		projectServiceDetails cascade: 'all-delete-orphan'
		 
	}
	
	def beforeInsert = {
		def auditableBeforeInsert = super.beforeInsert.clone()
		auditableBeforeInsert.delegate = delegate
		auditableBeforeInsert.call()
		
		setDefaults()
	}
	
	def beforeUpdate = {
		def auditableBeforeUpdate = super.beforeUpdate.clone()
		auditableBeforeUpdate.delegate = delegate
		auditableBeforeUpdate.call()
		
		setDefaults()
	}
	
	private def setDefaults = {
		if(! plannedRevenue) {
			plannedRevenue = 0.00
		}
		if(! actualRevenue) {
			actualRevenue = 0.00
		}
		if(! varianceRevenue) {
			varianceRevenue = 0.00
		}
	}
	
	ProjectProductDetail(defaultProjectProduct, defaultService) {
		this.projectProduct = defaultProjectProduct
		this.projectServiceDetails = []
		this.addToProjectServiceDetails(new ProjectServiceDetail(defaultService))
	}
	 
}
