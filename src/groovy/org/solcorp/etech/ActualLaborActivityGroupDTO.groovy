package org.solcorp.etech

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

class ActualLaborActivityGroupDTO implements Serializable{
	
	String laborActivityGroupCode
	
	BigDecimal hours
	
	BigDecimal standardRate
	
	BigDecimal standardCost
	
	BigDecimal standardOverheadCost
	
	BigDecimal standardTotalCost
	
	BigDecimal actualTotalCost
	
	List<ActivityDetailDTO> activityDetailDTOList
	 
}
