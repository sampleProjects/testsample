package org.solcorp.etech.reports.employeeByProjectByActivity

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class ActivityGrpByProjectDTO implements Serializable{
	
	String activityName
	
	BigDecimal activityHours
	
	BigDecimal activityStandardCost
	
	BigDecimal activityStandardOverheadCost
	
	BigDecimal activityStandardTotalCost
	
	BigDecimal activityActualCost
	
	List<ProjectDetailDTO> projectDetailList
}