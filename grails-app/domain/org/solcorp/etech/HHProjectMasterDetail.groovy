package org.solcorp.etech

import org.apache.commons.beanutils.BeanUtils;


class HHProjectMasterDetail extends Auditable  {

	Long id

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
		projectId(nullable: true)
		businessUnit(nullable: true)
		descr(nullable: true)
		custId(nullable: true)
		hhCustId(nullable: true)
		name1(nullable: true)
		effStatus(nullable: true)
		startDt(nullable: true)
		endDt(nullable: true)
		salesPerson(nullable: true)
		name2(nullable: true)
		dateCreated(nullable: true, display: false)
		lastUpdated(nullable: true, display: false)
	}

	static mapping = {
	}
	
	HHProjectMasterDetail(hhProjectMasterInstance, user) {
		/*this.projectId = hhProjectMasterInstance.projectId
		this.businessUnit = hhProjectMasterInstance.businessUnit
		this.descr = hhProjectMasterInstance.descr
		this.custId = hhProjectMasterInstance.custId
		this.hhCustId = hhProjectMasterInstance.hhCustId
		this.name1 = hhProjectMasterInstance.name1
		this.effStatus = hhProjectMasterInstance.effStatus
		this.startDt = hhProjectMasterInstance.startDt
		this.endDt = hhProjectMasterInstance.endDt
		this.salesPerson = hhProjectMasterInstance.salesPerson
		this.name2 = hhProjectMasterInstance.name2*/
		
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
		this.createdBy = user
		this.updatedBy = user
	}
}
