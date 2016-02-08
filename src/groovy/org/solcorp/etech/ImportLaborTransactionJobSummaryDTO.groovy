package org.solcorp.etech;

import java.io.Serializable;

public class ImportLaborTransactionJobSummaryDTO implements Serializable{
	Long recordsToBeProcessed = 0
	Long recordsProcessed = 0
	Long recordsInserted = 0
	Long recordsUpdated = 0
	Long recordsFailed = 0
	
	/*List<ImportEmployeeJobRecordDTO> insertedRecordsList
	List<ImportEmployeeJobRecordDTO> updatedRecordsList
	List<ImportEmployeeJobRecordDTO> failedRecordsList*/
	
	List<String> projectsNotfound
	List<String> customersNotfound
	List<String> employeesNotfound
	List<String> employeesWithNoDepartment
	List<String> departmentsWithNoLaborActivityGroup
	List<String> activityGroupsWithNoActivityCode
	List<String> employeesWithNoActivityCode
	List<String> projectsAffected
	
	ImportLaborTransactionJobSummaryDTO(recordsToBeProcessed, recordsProcessed, recordsInserted, recordsUpdated, recordsFailed, insertedRecordsList, 
		updatedRecordsList, failedRecordsList, projectsNotfound, customersNotfound, employeesNotfound, employeesWithNoDepartment, departmentsWithNoLaborActivityGroup, 
		activityGroupsWithNoActivityCode, employeesWithNoActivityCode, projectsAffected) {
		
		this.recordsToBeProcessed = recordsToBeProcessed
		this.recordsProcessed = recordsProcessed
		this.recordsInserted = recordsInserted
		this.recordsUpdated = recordsUpdated
		this.recordsFailed = recordsFailed
		/*this.insertedRecordsList = insertedRecordsList
		this.updatedRecordsList = updatedRecordsList
		this.failedRecordsList = failedRecordsList*/
		this.projectsNotfound = projectsNotfound
		this.customersNotfound = customersNotfound
		this.employeesNotfound = employeesNotfound
		this.employeesWithNoDepartment = employeesWithNoDepartment
		this.departmentsWithNoLaborActivityGroup = departmentsWithNoLaborActivityGroup
		this.activityGroupsWithNoActivityCode = activityGroupsWithNoActivityCode
		this.employeesWithNoActivityCode = employeesWithNoActivityCode 
		this.projectsAffected = projectsAffected
	}
}
