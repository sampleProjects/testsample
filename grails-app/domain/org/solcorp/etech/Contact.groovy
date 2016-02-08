package org.solcorp.etech

class Contact {
	
	/**
	 * Contact id 
	 */
	Long id
	
	/**
	 * ENUM Contact type
	 */
	ContactType type
	
	/**
	 * Contact name 
	 */
	String name
	
	/**
	 * Contact phone number 
	 */
	String phoneNumber
	
	/**
	 * Contact Fax Number 
	 */
	String faxNumber
	
	/**
	 * Contact Email address 
	 */
	String emailAddress

	/**
	 * Contact Cellular 
	 */
	String cellular
	
	static belongsTo = [customer: Customer]
	
	static constraints = {
		type(nullable: false)
		name(nullable: true, maxSize: Constants.CONTACT_NAME_LENGTH)
		phoneNumber(nullable: true, maxSize: Constants.CONTACT_PHONE_NUMBER_LENGTH)
		faxNumber(nullable: true, maxSize: Constants.CONTACT_FAX_NUMBER_LENGTH)
		emailAddress(nullable: true, email: true, maxSize: Constants.CONTACT_EMAIL_ADDRESS_LENGTH)
		cellular(nullable: true, maxSize: Constants.CONTACT_CELLULAR_LENGTH)
    }
	
	static mapping = {
		type sqlType: 'varchar(30)'
	}
}
