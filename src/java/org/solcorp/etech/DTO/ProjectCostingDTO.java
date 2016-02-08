package org.solcorp.etech.DTO;

import java.math.BigDecimal;

public class ProjectCostingDTO {
	
	private String projectCode;
	
	private String projectName;
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	private String productCode;
	
	private BigDecimal plannedLaborTotal;
	
	private BigDecimal plannedExpenseTotal;
	
	private BigDecimal plannedOverHeadTotal;
	
	private BigDecimal actualLaborTotal;
	private BigDecimal actualExpenseTotal;
	private BigDecimal varianceExpenseTotal;
	
	private BigDecimal plannedStdOHCost;
	public BigDecimal getPlannedStdOHCost() {
		return plannedStdOHCost;
	}

	public void setPlannedStdOHCost(BigDecimal plannedStdOHCost) {
		this.plannedStdOHCost = plannedStdOHCost;
	}

	public BigDecimal getActualStdOHCost() {
		return actualStdOHCost;
	}

	public void setActualStdOHCost(BigDecimal actualStdOHCost) {
		this.actualStdOHCost = actualStdOHCost;
	}

	public BigDecimal getVarianceStdOHCostTotal() {
		return varianceStdOHCostTotal;
	}

	public void setVarianceStdOHCostTotal(BigDecimal varianceStdOHCostTotal) {
		this.varianceStdOHCostTotal = varianceStdOHCostTotal;
	}

	private BigDecimal actualStdOHCost;
	private BigDecimal varianceStdOHCostTotal;
	
	public BigDecimal getVarianceExpenseTotal() {
		return varianceExpenseTotal;
	}

	public void setVarianceExpenseTotal(BigDecimal varianceExpenseTotal) {
		this.varianceExpenseTotal = varianceExpenseTotal;
	}

	public BigDecimal getActualExpenseTotal() {
		return actualExpenseTotal;
	}

	public void setActualExpenseTotal(BigDecimal actualExpenseTotal) {
		this.actualExpenseTotal = actualExpenseTotal;
	}

	private BigDecimal varianceLaborTotal;
	
	
	private BigDecimal plannedTotal; 
	
	private BigDecimal actualTotal;
	
	private BigDecimal varianceTotal;
	
	
	private BigDecimal plannedRevenueTotal;
	
	public BigDecimal getPlannedRevenueTotal() {
		return plannedRevenueTotal;
	}

	public void setPlannedRevenueTotal(BigDecimal plannedRevenueTotal) {
		this.plannedRevenueTotal = plannedRevenueTotal;
	}

	public BigDecimal getActualRevenueTotal() {
		return actualRevenueTotal;
	}

	public void setActualRevenueTotal(BigDecimal actualRevenueTotal) {
		this.actualRevenueTotal = actualRevenueTotal;
	}

	public BigDecimal getVarianceRevenueTotal() {
		return varianceRevenueTotal;
	}

	public void setVarianceRevenueTotal(BigDecimal varianceRevenueTotal) {
		this.varianceRevenueTotal = varianceRevenueTotal;
	}

	public BigDecimal getPlannedProfit() {
		return plannedProfit;
	}

	public void setPlannedProfit(BigDecimal plannedProfit) {
		this.plannedProfit = plannedProfit;
	}

	public BigDecimal getActualProfit() {
		return actualProfit;
	}

	public void setActualProfit(BigDecimal actualProfit) {
		this.actualProfit = actualProfit;
	}

	public BigDecimal getVarianceProfit() {
		return varianceProfit;
	}

	public void setVarianceProfit(BigDecimal varianceProfit) {
		this.varianceProfit = varianceProfit;
	}

	public BigDecimal getPlannedHoursTotal() {
		return plannedHoursTotal;
	}

	public void setPlannedHoursTotal(BigDecimal plannedHoursTotal) {
		this.plannedHoursTotal = plannedHoursTotal;
	}

	public BigDecimal getActualHoursTotal() {
		return actualHoursTotal;
	}

	public void setActualHoursTotal(BigDecimal actualHoursTotal) {
		this.actualHoursTotal = actualHoursTotal;
	}

	public BigDecimal getVarianceHoursTotal() {
		return varianceHoursTotal;
	}

	public void setVarianceHoursTotal(BigDecimal varianceHoursTotal) {
		this.varianceHoursTotal = varianceHoursTotal;
	}

	private BigDecimal actualRevenueTotal;
	
	private BigDecimal varianceRevenueTotal;
	
	
	private BigDecimal plannedProfit;
	
	private BigDecimal actualProfit;
	
	private	BigDecimal varianceProfit;
	
	
	private	BigDecimal plannedHoursTotal;
	
	private	BigDecimal actualHoursTotal;
	
	private	BigDecimal varianceHoursTotal;
	
	private	BigDecimal plannedProfitPercentage;
	
	public BigDecimal getPlannedProfitPercentage() {
		return plannedProfitPercentage;
	}

	public void setPlannedProfitPercentage(BigDecimal plannedProfitPercentage) {
		this.plannedProfitPercentage = plannedProfitPercentage;
	}

	public BigDecimal getActualProfitPercentage() {
		return actualProfitPercentage;
	}

	public void setActualProfitPercentage(BigDecimal actualProfitPercentage) {
		this.actualProfitPercentage = actualProfitPercentage;
	}

	public BigDecimal getVarianceProfitPercentage() {
		return varianceProfitPercentage;
	}

	public void setVarianceProfitPercentage(BigDecimal varianceProfitPercentage) {
		this.varianceProfitPercentage = varianceProfitPercentage;
	}

	private	BigDecimal actualProfitPercentage;
	
	private	BigDecimal varianceProfitPercentage;
			
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getPlannedLaborTotal() {
		return plannedLaborTotal;
	}

	public void setPlannedLaborTotal(BigDecimal plannedLaborTotal) {
		this.plannedLaborTotal = plannedLaborTotal;
	}

	public BigDecimal getPlannedExpenseTotal() {
		return plannedExpenseTotal;
	}

	public void setPlannedExpenseTotal(BigDecimal plannedExpenseTotal) {
		this.plannedExpenseTotal = plannedExpenseTotal;
	}

	public BigDecimal getPlannedOverHeadTotal() {
		return plannedOverHeadTotal;
	}

	public void setPlannedOverHeadTotal(BigDecimal plannedOverHeadTotal) {
		this.plannedOverHeadTotal = plannedOverHeadTotal;
	}

	public BigDecimal getActualLaborTotal() {
		return actualLaborTotal;
	}

	public void setActualLaborTotal(BigDecimal actualLaborTotal) {
		this.actualLaborTotal = actualLaborTotal;
	}

	public BigDecimal getVarianceLaborTotal() {
		return varianceLaborTotal;
	}

	public void setVarianceLaborTotal(BigDecimal varianceLaborTotal) {
		this.varianceLaborTotal = varianceLaborTotal;
	}

	public BigDecimal getPlannedTotal() {
		return plannedTotal;
	}

	public void setPlannedTotal(BigDecimal plannedTotal) {
		this.plannedTotal = plannedTotal;
	}

	public BigDecimal getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(BigDecimal actualTotal) {
		this.actualTotal = actualTotal;
	}

	public BigDecimal getVarianceTotal() {
		return varianceTotal;
	}

	public void setVarianceTotal(BigDecimal varianceTotal) {
		this.varianceTotal = varianceTotal;
	}

	
	
	   
}
