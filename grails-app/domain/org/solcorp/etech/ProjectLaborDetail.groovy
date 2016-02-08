package org.solcorp.etech

import java.math.BigDecimal;

class ProjectLaborDetail extends Auditable {

	Long id
	
	LaborActivityCode laborActivityCode
	
	BigDecimal hours
	
	BigDecimal standardRate
	
	BigDecimal standardCost
	
	BigDecimal overHead
	
	BigDecimal overHeadCost
	
	BigDecimal totalCost
	
	BigDecimal billAmountTotal
	
	BigDecimal billRate
	
	Boolean isFromActualLabor = Boolean.FALSE
	
	static belongsTo = [projectLabor: ProjectLabor]
	
    static constraints = {
		hours (nullable : true, scale : 2)
		standardRate (nullable : true, scale : 2)
		standardCost (nullable : true, scale : 2)
		overHead (nullable : true, scale : 0)
		overHeadCost (nullable : true, scale : 2)
		totalCost (nullable : true, scale : 2)
		billAmountTotal (nullable : true, scale : 2)
		laborActivityCode(nullable : true)
		billRate (nullable : true, scale : 2)
		billAmountTotal (nullable : true, scale : 2)
		projectLabor(nullable : true)
    }
	
	static mapping = {
		isFromActualLabor defaultValue: Boolean.FALSE
	}
}
