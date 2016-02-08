package org.solcorp.etech.reports.projectByExpense

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.solcorp.etech.ActualExpenseDetailDTO
import org.solcorp.etech.utils.MiscUtils

class ExpenseDetailDTO implements Serializable{
	
	String expenseCode
	
	BigDecimal amount
	
	List<ActualExpenseDetailDTO> actualExpenseDetailDTOList
	 
}