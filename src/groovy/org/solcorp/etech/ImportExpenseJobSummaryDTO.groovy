package org.solcorp.etech

public class ImportExpenseJobSummaryDTO implements Serializable{
	Long recordsToBeProcessed = 0
	Long recordsProcessed = 0
	Long recordsInserted = 0
	Long recordsFailed = 0
	
	List<String> projectsNotfound
	List<String> expenseNotfound
	
	ImportExpenseJobSummaryDTO(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsFailed, insertedRecordsList,
			failedRecordsList, projectsNotfound, expenseNotfound) {
		
		this.recordsToBeProcessed = recordsToBeProcessed
		this.recordsProcessed = recordsProcessed
		this.recordsInserted = recordsInserted
		this.recordsFailed = recordsFailed
		this.projectsNotfound = projectsNotfound
		this.expenseNotfound = expenseNotfound
	}
}
