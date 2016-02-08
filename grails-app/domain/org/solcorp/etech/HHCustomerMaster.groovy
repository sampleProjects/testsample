package org.solcorp.etech

import org.grails.databinding.BindingFormat
import grails.util.Environment

class HHCustomerMaster implements Serializable {

	String hhCustId
	
	String name1
	
	String hhMasterCustId
	
	String custStatus
	
	@BindingFormat("dd-MON-yy")
	Date dateAdded
	
	@BindingFormat("dd-MON-yy")
	Date lastMaintDttm
	
	@BindingFormat("dd-MON-yy")
	Date dttm_stamp
	
	String descrshort
	
    static constraints = {
    }
	
	static mapping = {
		if (grails.util.Environment.current.name == "hh_stage" || grails.util.Environment.current.name == "hh_prod") {
			datasource 'hh'
			id composite: ['hhCustId']
		}
		
		//table "ps_hhcr_allcust_vw"
		table "PS_HHCR_ALLCUS_TBL"
		version false
	 }
}
