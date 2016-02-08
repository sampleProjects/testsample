package org.solcorp.etech

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import org.solcorp.etech.utils.MiscUtils

class EmployeeDetailEADTO implements Serializable{
	
	String employeeCode
	
	String laborActivityCode
	
	Date transactionDate	
	
	BigDecimal hours
	
	BigDecimal standardRate
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualRate
	
	BigDecimal actualCost
	
	EmployeeDetailEADTO(employeeCode, laborActivityCode, transactionDate, hours, standardRate, standardCost, standardOverheadCost, standardTotalCost, actualRate, actualCost) {
		this.employeeCode = employeeCode
		this.laborActivityCode = laborActivityCode
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