package org.solcorp.etech

class JobRegister {
	
	String name
	
	String displayName
	
	String className
	
	Date startDate
	
	Date endDate
	
	JobStatusType status
	
	User initiatedBy	
	
	Boolean hasSummary = false
	
	Boolean hasMessage = false
	
	Boolean hasStacktrace = false
	
	JobRegisterSummary jobRegisterSummary
	
	Date dataRangeFrom
	
	Date dataRangeTo
	
	static constraints = {
        endDate(nullable: true)
		initiatedBy(nullable: true)
		hasSummary(nullable: true)
		hasMessage(nullable: true)
		hasStacktrace(nullable: true)
		jobRegisterSummary(nullable: true)
		dataRangeFrom(nullable: true)
		dataRangeTo(nullable: true)
    }
	
	static mapping = {
		hasSummary defaultValue: false
		hasMessage defaultValue: false
		hasStacktrace defaultValue: false
	 }
}
