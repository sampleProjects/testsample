package org.solcorp.etech.reports.projectByExpense

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.solcorp.etech.utils.MiscUtils

class ProjectExpenseDetailDTO implements Serializable{
	
	String projectCode
	
	String projectName
	
	String expenseCode
	
	String customerCode
	
	String acctExecutiveCode
	
	BigDecimal amount
	
	List<ExpenseDetailDTO> expenseDetailDTOList	
	 
}