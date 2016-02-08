package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class OperationDetailDTO implements Serializable{
	
	String laborActivityCode
	
	BigDecimal hours
	
	BigDecimal standardRate
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualCost
	
	List<EmployeeDetailDTO> employeeDetailList
	 
}
