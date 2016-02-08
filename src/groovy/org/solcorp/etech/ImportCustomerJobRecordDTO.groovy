package org.solcorp.etech;

import java.io.Serializable;

public class ImportCustomerJobRecordDTO implements Serializable{
	String hhCustomerId
	Long customerId
	String message

	ImportCustomerJobRecordDTO (hhCustomerId, customerId, message) {
		this.hhCustomerId = hhCustomerId
		this.customerId = customerId
		this.message = message
	}
}
