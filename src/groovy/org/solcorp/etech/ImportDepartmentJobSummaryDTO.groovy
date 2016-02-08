package org.solcorp.etech

import java.io.Serializable;

public class ImportDepartmentJobSummaryDTO implements Serializable{
	Long recordsToBeProcessed = 0
	Long recordsProcessed = 0
	Long recordsInserted = 0
	Long recordsFailed = 0
	
	List<ImportEmployeeJobRecordDTO> insertedRecordsList
	List<ImportEmployeeJobRecordDTO> failedRecordsList
}
