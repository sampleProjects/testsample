package org.solcorp.etech.utils

import grails.util.Holders

class MiscUtils {
	def static trimParams(def params) {
		params.each { key, value ->
			value = value.toString().trim()
			params.put(key, value)
		}
		return params
	}
	
	
	def static checkForDynamicPassword() {
		boolean isDynamicPasswordCheckRequired = false
		if(Holders.config.etech.admin.dynamicpasswordlogic.use?.toBoolean() == Boolean.TRUE) {
			isDynamicPasswordCheckRequired = true
		}
		
		println " "
		println "------------------------------------------"
		println "Dynamic Password Match Required: " + isDynamicPasswordCheckRequired
		println "------------------------------------------"
		
		return isDynamicPasswordCheckRequired
	}
	
	/**
	 * This method checks for the dynamic parts of the password for Admin if the "etech.admin.dynamicpasswordlogic.use" flag is set to true 
	 * @param password
	 * @return boolean
	 */
	def static compareDynamicPartsOfPassword(def password) {
		boolean dynamicPartsMatch = false
		try {
			// Strip first two characters of the password and compare the same with the current month in MM format
			def now = new Date()
			def currentMonth = now[Calendar.MONTH] + 1
			def currentDay = now[Calendar.DAY_OF_MONTH]

			def strippedMonth = password?.substring(0, 2)
			if(strippedMonth && strippedMonth.toInteger() == currentMonth) {

				def strippedDay = password?.substring(password.length() - 2)
				if(strippedDay && strippedDay.toInteger() == currentDay) {

					dynamicPartsMatch = true
				}
			}
		}  catch (Exception e) {}

		println " "
		println "------------------------------------------"
		println "Dynamic Password Matched: " + dynamicPartsMatch
		println "------------------------------------------"

		return dynamicPartsMatch
	}
	
	def static removePrecision(value) {
		if(value !=null && value != ""){
			return value.setScale(2, BigDecimal.ROUND_HALF_UP)
		}
		return value
		
	}
}
