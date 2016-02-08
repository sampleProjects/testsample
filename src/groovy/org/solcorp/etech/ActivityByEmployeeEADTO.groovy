package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class ActivityByEmployeeEADTO  implements Serializable{
	
	String activityName
	
	BigDecimal hours
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualCost
	
	List<EmployeeDetailEADTO> employeeDetailList
}