package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class EmployeeByActivityReportDTO implements Serializable{
	
	String projectCode
	
	String projectName
	
	//String customerCode
	
	//String acctExecutiveCode
		
	BigDecimal projectHours
	
	BigDecimal projectStandardCost
	
	BigDecimal projectStandardOverheadCost
	
	BigDecimal projectStandardTotalCost
	
	BigDecimal projectActualCost
	
	List<EmployeeByProjectEADTO> groupByEmployeeList
}