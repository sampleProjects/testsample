package org.solcorp.etech;

import java.io.Serializable;
import java.math.BigDecimal;

public class ActivityByEmployeeReportDTO implements Serializable{
	
	String projectCode
	
	String projectName
	
	String customerCode
	
	String acctExecutiveCode
		
	BigDecimal projectHours
	
	BigDecimal projectStandardCost
	
	BigDecimal projectStandardOverheadCost
	
	BigDecimal projectStandardTotalCost
	
	BigDecimal projectActualCost
	
	List<OperationDetailDTO> operationDetailDTOList
}
