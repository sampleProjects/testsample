package org.solcorp.etech;

import java.io.Serializable;

public class ImportEmployeeJobRecordDTO implements Serializable{
	String hhEmployeeId
	Long employeeId
	String message

	ImportEmployeeJobRecordDTO(hhEmployeeId, employeeId, message) {
		this.hhEmployeeId = hhEmployeeId
		this.employeeId = employeeId
		this.message = message
	}
}
