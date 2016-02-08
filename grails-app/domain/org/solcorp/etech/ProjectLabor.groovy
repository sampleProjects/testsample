package org.solcorp.etech

import java.util.Date;
import org.grails.databinding.BindingFormat;

class ProjectLabor extends Auditable {
	
	Long id
		
	@BindingFormat("MM/dd/yyyy")
	Date laborActivityDate
	
	List laborDetails = new ArrayList()
	
	static hasMany = [laborDetails: ProjectLaborDetail]
	
	static mapping = {		
		laborDetails cascade: 'all-delete-orphan'
	 }
	
    static constraints = {	 
		laborActivityDate(nullable: false, blank: false)
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
	}
}
