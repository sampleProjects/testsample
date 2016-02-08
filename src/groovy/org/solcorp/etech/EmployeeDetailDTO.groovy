package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class EmployeeDetailDTO implements Serializable{
	
	Long employyeeId
	
	String employeeName
	
	String employeeCode
	
	BigDecimal hours
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualCost
	
	List<ActivityDetailDTO> activityDetailList
	
	EmployeeDetailDTO(employeeName,  hours, standardCost, standardOverheadCost, standardTotalCost, actualCost, activityDetailList, empCode) {
		this.employeeName = employeeName
		this.hours = hours
		this.standardCost = standardCost
		this.standardOverheadCost = standardOverheadCost
		this.standardTotalCost = standardTotalCost
		this.actualCost = actualCost
		this.activityDetailList = activityDetailList
		this.employeeCode = empCode
	}
	
	EmployeeDetailDTO(employyeeId, employeeName, hours, standardCost, standardOverheadCost, standardTotalCost, actualCost, activityDetailList, empCode) {
		this.employyeeId = employyeeId
		this.employeeName = employeeName
		this.hours = hours
		this.standardCost = standardCost
		this.standardOverheadCost = standardOverheadCost
		this.standardTotalCost = standardTotalCost
		this.actualCost = actualCost
		this.activityDetailList = activityDetailList
		this.employeeCode = empCode
	}
}
