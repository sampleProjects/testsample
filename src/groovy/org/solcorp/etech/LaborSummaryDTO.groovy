package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.solcorp.etech.utils.MiscUtils
class LaborSummaryDTO implements Serializable{
		
	LaborActivityGroup laborActivityGroup
	
	COE coe
	
	String laborActivityCode
	
	BigDecimal plannedHours
	
	BigDecimal actualHours
	
	BigDecimal varianceHours
		
	BigDecimal plannedTotal
	
	BigDecimal actualTotal
	
	BigDecimal varianceTotal

	LaborSummaryDTO(groupCode, laborActivityCode, coe, plannedHours, actualHours, plannedTotal, actualTotal) {
		this.laborActivityGroup = groupCode
		this.laborActivityCode = laborActivityCode
		this.coe = coe
		this.plannedHours = MiscUtils.removePrecision(plannedHours)
		this.actualHours = MiscUtils.removePrecision(actualHours)
		this.varianceHours = this.actualHours - this.plannedHours
		
		this.plannedTotal = MiscUtils.removePrecision(plannedTotal)
		this.actualTotal = MiscUtils.removePrecision(actualTotal)
		this.varianceTotal = this.actualTotal - this.plannedTotal
	} 
}
