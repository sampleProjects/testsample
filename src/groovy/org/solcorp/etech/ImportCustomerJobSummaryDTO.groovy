package org.solcorp.etech;

import java.io.Serializable;

public class ImportCustomerJobSummaryDTO implements Serializable{
	Long recordsToBeProcessed = 0
	Long recordsProcessed = 0
	Long recordsInserted = 0
	Long recordsUpdated = 0
	Long recordsFailed = 0
	
	List<ImportEmployeeJobRecordDTO> insertedRecordsList
	List<ImportEmployeeJobRecordDTO> updatedRecordsList
	List<ImportEmployeeJobRecordDTO> failedRecordsList
}
