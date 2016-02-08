package org.solcorp.etech

class Address {

	
	/**
	 * Address id 
	 */
	Long id
	
	/**
	 * ENUM Address type
	 */
	AddressType addressType
	
	/**
	 * Address line 1 
	 */
	String line1
	
	/**
	 * Address line 2 
	 */
	String line2
	
	/**
	 * City 
	 */
	String city
	
	/**
	 * ENUM state 
	 */
	StateType state
	
	/**
	 * Zip 
	 */
	String zip
	
	/**
	 * Country 
	 */
	String country
	
	static belongsTo = [customer: Customer]
	
	static constraints = {
		addressType(nullable: true)
		line1(nullable: true, maxSize: Constants.ADDRESS_LINE_1_LENGTH)
		line2(nullable: true, maxSize: Constants.ADDRESS_LINE_2_LENGTH)
		city(nullable: true, maxSize: Constants.ADDRESS_CITY_LENGTH)
		state(nullable: true)
		zip(nullable: true, maxSize: Constants.ADDRESS_ZIP_LENGTH)
		country(nullable: true, maxSize: Constants.ADDRESS_COUNTRY_LENGTH)
	}
	
	static mapping = {
		addressType sqlType: 'varchar(30)'
		state sqlType: 'varchar(30)'
	}
}
