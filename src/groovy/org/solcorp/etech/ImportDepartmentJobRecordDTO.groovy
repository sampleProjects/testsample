package org.solcorp.etech

import java.io.Serializable;

class ImportDepartmentJobRecordDTO implements Serializable{
	Long departmentId
	String hhDepartmentCode
	String message
	
	ImportDepartmentJobRecordDTO(departmentId, hhDepartmentCode, message) {
		this.departmentId = departmentId
		this.hhDepartmentCode = hhDepartmentCode
		this.message = message 
	}
}
