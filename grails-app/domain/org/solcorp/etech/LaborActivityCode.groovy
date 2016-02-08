package org.solcorp.etech

import java.util.Date;

class LaborActivityCode extends Auditable {

	/**
	 * Used as primary key in all modules.
	 */
	Long id
	
	/**
	 * Code
	 */
	String code
	
	/**
	 * description
	 */
	String description
	
	/**
	 * labor_activity_department_id
	 */
	static belongsTo = [laborActivityGroup: LaborActivityGroup]
	
	/**
	 * standard_Cost
	 */
	BigDecimal standardRate
	
	/**
	 * over_Head
	 */
	BigDecimal overHead
	
	/**
	 * bill_Rate
	 */
	BigDecimal billRate
	
	/**
	 * qc_Flag
	 */
	String qcFlag
	
	/**
	 * count_Point
	 */
	String countPoint
	
	/**
	 * Operations
	 */
	String operations
	
	/**
	 * active
	 */
	String active
		
    static constraints = {
		code (nullable : false, blank : false, unique : true, maxSize : Constants.LABORACTIVITYCODE_CODE_LENGTH)
		description (nullable : true, maxSize : Constants.LABORACTIVITYCODE_DESCRIPTION_LENGTH)
		laborActivityGroup (nullable : false)
		standardRate (nullable : false, scale : 2)
		overHead (nullable : true, scale : 0)
		billRate (nullable : true, scale : 2)
		qcFlag (nullable : true, maxSize : Constants.LABORACTIVITYCODE_QCFLAG_LENGTH)
		countPoint (nullable : true, maxSize : Constants.LABORACTIVITYCODE_CNTPOINT_LENGTH)
		operations (nullable: true, maxSize : Constants.LABORACTIVITYCODE_OPERATIONS_LENGTH)
		active (nullable : true, maxSize : Constants.LABORACTIVITYCODE_ACTIVE_LENGTH)
		dateCreated (nullable : true, display : false)
		lastUpdated (nullable : true, display : false)
    }
	
	static mapping = {
		//id generator: 'uuid'
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
		if(! standardRate) {
			standardRate = 0.00
		}
		
		if(! overHead) {
			overHead = 0.00
		}
		
		if(! billRate) {
			billRate = 0.00
		}
		
		if(! active) {
			active = 'Y'
		}
	}
}
