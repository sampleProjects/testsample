package org.solcorp.etech

import grails.util.Environment

class TempHHCustomerMaster implements Serializable {

	String hhCustId
	
	String name1
	
	String hhMasterCustId
	
	String custStatus
	
	String dateAdded
	
    static constraints = {
		
		hhCustId(nullable: true, blank: true)
		
		name1(nullable: true, blank: true)
		
		hhMasterCustId(nullable: true, blank: true)
		
		custStatus(nullable: true, blank: true)
		
		dateAdded(nullable: true, blank: true)
    }
	
	static mapping = {
		table "temp_ps_hhcr_allcust_vw"
		version false
	 }
	
	TempHHCustomerMaster(hhCustomerMasterInstance) {
		this.hhCustId = hhCustomerMasterInstance.hhCustId
		this.name1 = hhCustomerMasterInstance.name1
		this.hhMasterCustId = hhCustomerMasterInstance.hhMasterCustId
		this.custStatus = hhCustomerMasterInstance.custStatus
		this.dateAdded = hhCustomerMasterInstance.dateAdded
	}
}
