package org.solcorp.etech

import grails.util.Environment

import javax.persistence.Transient

class TempHHProjectMaster implements Serializable {

	String projectId
	
	String businessUnit
	
	String descr
	
	String custId
	
	String hhCustId
	
	String name1
	
	String effStatus
	
	String startDt
	
	String endDt
	
	String salesPerson
	
	String name2
	
    static constraints = {
		
		projectId(nullable: true, blank: true)
		
		businessUnit(nullable: true, blank: true)
		
		descr(nullable: true, blank: true)
		
		custId(nullable: true, blank: true)
		
		hhCustId(nullable: true, blank: true)
		
		name1(nullable: true, blank: true)
		
		effStatus(nullable: true, blank: true)
		
		startDt(nullable: true, blank: true)
		
		endDt(nullable: true, blank: true)
		
		salesPerson(nullable: true, blank: true)
		
		name2(nullable: true, blank: true)
    }
	
	static mapping = {
		table "temp_ps_hhpc_prjcust_vw"
		version false
	 }
	
	TempHHProjectMaster(hhProjectMasterInstance) {
		this.projectId = hhProjectMasterInstance[0]
		this.businessUnit = hhProjectMasterInstance[1]
		this.descr = hhProjectMasterInstance[2]
		this.custId = hhProjectMasterInstance[3]
		this.hhCustId = hhProjectMasterInstance[4]
		this.name1 = hhProjectMasterInstance[5]
		this.effStatus = hhProjectMasterInstance[6]
		this.startDt = hhProjectMasterInstance[7]
		this.endDt = hhProjectMasterInstance[8]
		this.salesPerson =hhProjectMasterInstance[9]
		this.name2 = hhProjectMasterInstance[10]
	}
}
