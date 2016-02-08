package org.solcorp.etech;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LaborHistoryCOEReportDTO implements Serializable{
	
	String code
		
	BigDecimal hours
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualCost
	
	
	List<ActivityByEmployeeReportDTO>  activityByEmployeeReportDTO	
	
}
