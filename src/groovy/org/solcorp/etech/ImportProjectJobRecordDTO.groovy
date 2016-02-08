package org.solcorp.etech;

import java.io.Serializable;

public class ImportProjectJobRecordDTO implements Serializable{
	String hhProjectId
	Long projectId
	String message

	ImportProjectJobRecordDTO (hhProjectId, projectId, message) {
		this.hhProjectId = hhProjectId
		this.projectId = projectId
		this.message = message
	}
}
