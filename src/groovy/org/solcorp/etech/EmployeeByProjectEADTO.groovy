package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class EmployeeByProjectEADTO implements Serializable{
	
	String employeeName
	
	String employeeCode
	
	BigDecimal hours
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualCost
	
	List<ActivityByEmployeeEADTO> groupByActivityList
	 
}
