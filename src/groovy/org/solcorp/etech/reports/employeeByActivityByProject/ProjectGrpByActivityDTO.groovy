package org.solcorp.etech.reports.employeeByActivityByProject

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class ProjectGrpByActivityDTO implements Serializable{
	
	String projectCode
	
	String projectName
		
	BigDecimal projectHours
	
	BigDecimal projectStandardCost
	
	BigDecimal projectStandardOverheadCost
	
	BigDecimal projectStandardTotalCost
	
	BigDecimal projectActualCost
	
	List<ActivityDetailDTO> activityDetailList
}