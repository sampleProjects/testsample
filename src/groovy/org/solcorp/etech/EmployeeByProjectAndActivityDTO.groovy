package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

class EmployeeByProjectAndActivityDTO implements Serializable{
	
	Long employyeeId
	
	String employeeCode
	
	String employeeName
	
	Date transactionDate
	
	BigDecimal hours
	
	BigDecimal cost
	
	BigDecimal overheadCost
	
	BigDecimal totalCost
	 
}
