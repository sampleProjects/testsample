package org.solcorp.etech.reports.employeeByProjectByActivity

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class ProjectGrpByEmployeeDTO implements Serializable{
	
	String projectCode
	
	String projectName
		
	BigDecimal projectHours
	
	BigDecimal projectStandardCost
	
	BigDecimal projectStandardOverheadCost
	
	BigDecimal projectStandardTotalCost
	
	BigDecimal projectActualCost
	
	List<ActivityGrpByProjectDTO> groupByActivityList
}