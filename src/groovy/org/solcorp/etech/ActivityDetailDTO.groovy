package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import org.solcorp.etech.utils.MiscUtils
class ActivityDetailDTO implements Serializable{
	
	String projectCode
	
	String laborActivityCode
	
	String employeeCode
	
	Date transactionDate	
	
	BigDecimal hours
	
	BigDecimal standardRate
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualRate
	
	BigDecimal actualCost
	
	ActivityDetailDTO(laborActivityCode, employeeCode, transactionDate, hours, standardRate, standardCost, standardOverheadCost, standardTotalCost, actualRate, actualCost) {
		this.laborActivityCode = laborActivityCode
		this.employeeCode = employeeCode
		this.transactionDate = transactionDate
		this.standardRate = MiscUtils.removePrecision(standardRate ?:0.00)
		this.hours = MiscUtils.removePrecision(hours ?:0.00)
		this.standardCost = MiscUtils.removePrecision(standardCost ?:0.00)
		this.standardOverheadCost = MiscUtils.removePrecision(standardOverheadCost ?:0.00)
		this.standardTotalCost = MiscUtils.removePrecision(standardTotalCost ?:0.00)
		this.actualRate = MiscUtils.removePrecision(actualRate ?:0.00)
		this.actualCost = MiscUtils.removePrecision(actualCost ?:0.00)
	}
	
	ActivityDetailDTO(projectCode, laborActivityCode, employeeCode, transactionDate, hours, standardRate, standardCost, standardOverheadCost, standardTotalCost, actualRate, actualCost) {
		this.projectCode = projectCode
		this.laborActivityCode = laborActivityCode
		this.employeeCode = employeeCode
		this.transactionDate = transactionDate
		this.standardRate = MiscUtils.removePrecision(standardRate ?:0.00)
		this.hours = MiscUtils.removePrecision(hours ?:0.00)
		this.standardCost = MiscUtils.removePrecision(standardCost ?:0.00)
		this.standardOverheadCost = MiscUtils.removePrecision(standardOverheadCost ?:0.00)
		this.standardTotalCost = MiscUtils.removePrecision(standardTotalCost ?:0.00)		
		this.actualRate = MiscUtils.removePrecision(actualRate ?:0.00)
		this.actualCost = MiscUtils.removePrecision(actualCost ?:0.00)
	}
}
