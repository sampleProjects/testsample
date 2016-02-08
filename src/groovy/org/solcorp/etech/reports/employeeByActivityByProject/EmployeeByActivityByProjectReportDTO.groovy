package org.solcorp.etech.reports.employeeByActivityByProject

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class EmployeeByActivityByProjectReportDTO implements Serializable{
	
	String employeeCode
	
	String employeeName
	
	String supervisorCode
	
	//String customerCode
	
	//String acctExecutiveCode
		
	BigDecimal reportHours
	
	BigDecimal reportStandardCost
	
	BigDecimal reportStandardOverheadCost
	
	BigDecimal reportStandardTotalCost
	
	BigDecimal reportActualCost
	
	List<ActivityGrpByEmployeeDTO> groupByActivityList
}