package org.solcorp.etech;

import java.io.Serializable;

public class ImportExpenseJobRecordDTO implements Serializable{
	String projectId
	String account
	String journalId
	Long journalLine
	Long projectActualExpenseDetailId
	String message

	ImportExpenseJobRecordDTO(hhExpenseMsaterInstance, projectActualExpenseDetailInstance, message) {
		this.projectId = hhExpenseMsaterInstance.projectId
		this.account =  hhExpenseMsaterInstance.account
		this.journalId = hhExpenseMsaterInstance.journalId
		this.journalLine = hhExpenseMsaterInstance.journalLine
		this.projectActualExpenseDetailId = projectActualExpenseDetailInstance?.id
		this.message = message
	}
}
