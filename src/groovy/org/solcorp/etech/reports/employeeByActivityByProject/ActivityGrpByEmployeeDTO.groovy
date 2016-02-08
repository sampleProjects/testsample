package org.solcorp.etech.reports.employeeByActivityByProject

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class ActivityGrpByEmployeeDTO implements Serializable{
	
	String activityName
	
	BigDecimal activityHours
	
	BigDecimal activityStandardCost
	
	BigDecimal activityStandardOverheadCost
	
	BigDecimal activityStandardTotalCost
	
	BigDecimal activityActualCost
	
	List<ProjectGrpByActivityDTO> groupByProjectList
}