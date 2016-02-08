package org.solcorp.etech

import javax.persistence.Transient;

class Customer extends Auditable {
	
	/**
	 * Customer id 
	 */
	Long id
	
	/**
	 *	Customer code 
	 */
	String code

	/**
	 *  Customer name
	 */
	String name
	
	/**
	 *  Customer type 
	 */
	String type
	
	/**
	 * Customer category 
	 */
	String category
	
	/**
	 * ENUM customer status 
	 */
	StatusType status
	
	/**
	 * ENUM Price level 
	 */
	PriceLevelType priceLevel
	
	/**
	 * ENUM Payment terms 
	 */
	PaymentTermsType paymentTerms 
	
	/**
	 * ENUM Credit Code
	 */
	CreditCodeType creditCode 
	
	/** 
	 * Store credit limit 
	 */
	BigDecimal creditLimit
	
	/**
	 * ENUM Statement Type 
	 */
	StatementType statementType 
	
	/**
	 * Store OutsideRep
	 */
	Long outsideRep
	
	/**
	 * Store Federal Id 
	 */
	String federalId
	
	/**
	 * Store State Id 
	 */
	String stateId
	
	/**
	 * Store customer comments 
	 */
	String comments
	
	/**
	 * Account Executive
	 */
	Employee acctExecutive

	/**
	 * Sales Executive
	 */
	Employee salesExecutive
	
	/**
	 * Industry Reference 
	 */
	Industry industry
	
	@Transient
	String acctExecutiveTxt
	
	@Transient
	String salesExecutiveTxt
		
	@Transient
	String industryTxt
	
	static hasMany = [contacts: Contact, addresses: Address]
	
	static transients = ['acctExecutiveTxt', 'salesExecutiveTxt', 'industryTxt']
	
	static constraints = {
		code(nullable: false, blank: false, unique: true, maxSize: Constants.CUSTOMER_CODE_LENGTH)
		name(nullable: false, blank: false, maxSize: Constants.CUSTOMER_NAME_LENGTH)
		type(nullable: true, maxSize: Constants.CUSTOMER_TYPE_LENGTH)
		category(nullable: true, maxSize: Constants.CUSTOMER_CATEGORY_LENGTH)
		status(nullable: true)
		priceLevel(nullable: true)
		paymentTerms(nullable: true)
		creditCode(nullable: true)
		creditLimit(nullable: true)
		statementType(nullable: true)
		outsideRep(nullable: true)
		federalId(nullable: true, maxSize: Constants.CUSTOMER_FEDERAL_ID_LENGTH)
		stateId(nullable: true, maxSize: Constants.CUSTOMER_STATE_ID_LENGTH)
		comments(nullable: true, maxSize: Constants.CUSTOMER_COMMENTS_LENGTH)
		acctExecutive(nullable: false)
		salesExecutive(nullable: true)
		industry(nullable: true)
	}
	
	static mapping = {
		addresses cascade: 'all-delete-orphan'
		contacts cascade: 'all-delete-orphan'
		
		status sqlType: 'varchar(30)'
		priceLevel sqlType: 'varchar(30)'
		paymentTerms sqlType: 'varchar(30)'
		creditCode sqlType: 'varchar(30)'
		statementType sqlType: 'varchar(30)'
	}
	
	@Transient
	static def getCustomerStatusFromHHCustomerStatus(status) {
		StatusType customerStatus = StatusType.ACTIVE // Default
		
		if(status) {
			if (status.equalsIgnoreCase(Constants.HH_CUSTOMERSTATUS_ACTIVE)) {
				customerStatus = StatusType.ACTIVE
			} else if (status.equalsIgnoreCase(Constants.HH_CUSTOMERSTATUS_INACTIVE)) {
				customerStatus = StatusType.INACTIVE
			}
		}
		
		return customerStatus
	}
	
	@Transient
	static def createCustomerFromHHCustomer(HHCustomerMaster hhCustomerInstance, User createdBy, Employee acctExecutive) {
		Customer customerInstance = new Customer()
		
		customerInstance.code = hhCustomerInstance.hhCustId.trim()
		customerInstance.name = hhCustomerInstance.name1
		customerInstance.status = getCustomerStatusFromHHCustomerStatus(hhCustomerInstance.custStatus)
		customerInstance.createdBy = createdBy
		customerInstance.updatedBy = createdBy
		customerInstance.acctExecutive = acctExecutive
		return customerInstance
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
