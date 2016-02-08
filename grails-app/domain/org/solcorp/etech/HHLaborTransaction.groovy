package org.solcorp.etech

class HHLaborTransaction implements Serializable {

	String transactionId

	String transactionDate

	String employeeId

	String projectFullName

	String projectUnit

	String projectCustRevId

	String projectId

	String hours

	String dollarAmount

	String importStatus

	String importStatusDate

	static constraints = {
	}

	static mapping = {
		table "kronos_labor_transactions"
		id composite: ['transactionId']
		version false
	}
}
