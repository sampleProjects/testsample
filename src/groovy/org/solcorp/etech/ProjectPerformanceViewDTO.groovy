package org.solcorp.etech;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Transient;

public class ProjectPerformanceViewDTO implements Serializable {
		
	Long projectId
	
	String projectCode
	
	String projectName
	
	Customer customer
	
	Employee accExecutive
	
	String order
	
	String status
	
	String reportTotalBy = Constants.REPORT_TOTAL_BY_ACTIVITY
	
	BigDecimal planBilling
	
	BigDecimal planExpenses  
	
	BigDecimal planLabor 
	
	BigDecimal planOverhead 
	
	BigDecimal planTotalCost 
	
	
	BigDecimal actualBilling 
	
	BigDecimal actualExpenses 
	
	BigDecimal actualLabor 
	
	BigDecimal actualOverhead 
	
	BigDecimal actualTotalCost 
	
	
	BigDecimal varianceBilling 
	
	BigDecimal varianceExpenses 
	
	BigDecimal varianceLabor 
	
	BigDecimal varianceOverhead 
	
	BigDecimal varianceTotalCost 
	
	
	BigDecimal planProfit 
	
	BigDecimal actualProfit 
	
	BigDecimal varianceProfit
	
	BigDecimal planProfitPercentage 
	
	BigDecimal actualProfitPercentage
	
	BigDecimal varianceProfitPercentage
	
	List<LaborSummaryDTO> laborSummaryDTOList	
	
	BigDecimal laborPlanHoursTotal 
	BigDecimal laborActualHoursTotal 
	BigDecimal laborVarianceHoursTotal 
	
	BigDecimal laborPlanTotal 
	BigDecimal laborActualTotal 
	BigDecimal laborVarianceTotal 
	
	BigDecimal plannedRevenueTotal
	BigDecimal actualRevenueTotal
	BigDecimal varianceRevenueTotal
}
