package org.solcorp.etech

class JobRegisterSummary {

	/**
	 * Used as primary key in all modules.
	 */
	Long id
	
	String summary
	
	String message
	
	String stacktrace
	
	static belongsTo = [jobRegister: JobRegister]
	
    static constraints = {
		message(nullable: true)
		summary(nullable: true)
		stacktrace(nullable: true)
		jobRegister(nullable: true)
    }
	
	static mapping = {
		message type: 'text'
		summary type: 'text'
		stacktrace type: 'text'
	}
}
