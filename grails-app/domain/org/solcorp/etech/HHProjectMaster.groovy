package org.solcorp.etech

import grails.util.Environment

import javax.persistence.Transient

class HHProjectMaster implements Serializable {

	String projectId
	
	String businessUnit
	
	String descr
	
	String custId
	
	String hhCustId
	
	String name1
	
	String effStatus
	
	Date startDt
	
	Date endDt
	
	String salesPerson
	
	String name2
	
	String descrshort
	
	Date dttmStamp
	
    static constraints = {
    }
	
	static mapping = {
		if (grails.util.Environment.current.name == "hh_stage" || grails.util.Environment.current.name == "hh_prod") {
			datasource 'hh'
			id composite: ['projectId', 'businessUnit', 'custId', 'hhCustId', 'salesPerson']
		}
		
		//table "ps_hhpc_prjcust_vw"
		table "PS_HHPC_PRJCUS_TBL"
		version false
	 }
	
	@Transient
	def getStartDate() {
		//return org.solcorp.etech.utils.DateFormatUtils.dateConvertFromString(startDt)
		return null
	}
	
	@Transient
	def getEndDate() {
		//return org.solcorp.etech.utils.DateFormatUtils.dateConvertFromString(endDt)
		return null
	}
	
	HHProjectMaster(String projectId, String businessUnit, String descr, String custId, String hhCustId, String name1, String effStatus,
		Date startDt, Date endDt, String salesPerson, String name2, String descrshort, Date dttmStamp) {
		this.projectId = projectId 
		this.businessUnit = businessUnit 
		this.descr = descr
		this.custId = custId
		this.hhCustId = hhCustId
		this.name1 = name1
		this.effStatus = effStatus
		this.startDt = startDt
		this.endDt = endDt
		this.salesPerson = salesPerson
		this.name2 = name2
		this.descrshort = descrshort
		this.dttmStamp = dttmStamp
	}
		
	@Override
	public String toString() {
		println "projectId: " + this.projectId 
		println "businessUnit: " + this.businessUnit 
		println "descr: " + this.descr
		println "custId: " + this.custId
		println "hhCustId: " + this.hhCustId
		println "name1: " + this.name1
		println "effStatus: " + this.effStatus
		println "startDt: " + this.startDt
		println "endDt: " + this.endDt
		println "salesPerson: " + this.salesPerson
		println "name2: " + this.name2
		println "descrshort: " + this.descrshort
		println "dttmStamp: " + this.dttmStamp
	}
}
