package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

class ActualExpenseDetailDTO implements Serializable{
	
	Long expenseId
	
	String expenseCode
	
	Date postedDate
	
	String vendorId
	
	String vendorName
	
	String invoice
	
	String glAmount
	
	BigDecimal amount
	
	BigDecimal qty
	  
}
