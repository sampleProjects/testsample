package org.solcorp.etech;

import java.io.Serializable;

public class ImportLaborTransactionJobRecordDTO implements Serializable{
	String sourceTransactionId
	Long actualLaborId
	String message

	ImportLaborTransactionJobRecordDTO(sourceTransactionId, actualLaborId, message) {
		this.sourceTransactionId = sourceTransactionId
		this.actualLaborId = actualLaborId
		this.message = message
	}
}
