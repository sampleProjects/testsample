package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

class ProjectExpenseSummaryDTO implements Serializable{
	
	Long expenseId
	String expenseCode
	
	BigDecimal plannedQty = 0.00	
	BigDecimal actualQty = 0.00
	BigDecimal varianceQty = 0.00
	
	BigDecimal plannedTotal	= 0.00
	BigDecimal actualTotal	= 0.00
	BigDecimal varianceTotal = 0.00

	  
}
