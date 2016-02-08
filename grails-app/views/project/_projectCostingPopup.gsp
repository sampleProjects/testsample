<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="org.solcorp.etech.Project" %>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!-- Modal -->
<div class="modal fade dialog-box" id="projectCostingPopup" role="dialog">
	<div class="modal-dialog modal-lg">      
	      <!-- Modal content-->
		<div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.project.costing.dtl.label" default="Project Details"/></h4>
	        </div>
	        <!-- Modal Body-->
			<div class="modal-body">
			 <div class="row">			 
				<span class="text-left col-sm-12"><label><g:message code="etech.project.project.name.label"	default="Project Name" />:</label> ${projectInstance?.name}</span>
			</div>
			
			<div class="clearfix"></div><hr>
			
			<div class="table-responsive height-scroll">
	        	
						<table class="table table-striped">		
						<thead>
							<tr>
					
							<th ><g:message code="etech.project.product.label" default="Product" /></th>
						
							<th></th>
							<g:if test="${session['logedInUser']?.hoursOnly == false}">	 
								<th class="text-right"><g:message code="etech.project.labor.label" default="Labor" /></th>
								
								<th class="text-right"><g:message code="etech.project.expense.label" default="Expense" /></th>
								
								<th class="text-right"><g:message code="etech.project.overhead.label" default="O/H" /></th>
								
								<th class="text-right"><g:message code="etech.project.total.label" default="Total" /></th>
							 	<g:if test="${canViewPricing}">
									<th class="text-right"><g:message code="etech.project.revenue.label" default="Revenue" /></th>
				          			
				          			<th class="text-right"><g:message code="etech.project.profit.label" default="Profit" /></th>
				          			
				          			<th class="text-right"><g:message code="etech.project.profit.percentage.label" default="Profit %" /></th>
				          		</g:if>	   
	          				</g:if>       
	          				
	          				<th class="text-right"><g:message code="etech.project.hours.label" default="Hours" /></th>
							
							</tr>
						</thead>
				<tbody>
				<g:if test="${projectCostingList?.size() > 0}">		
						
				<g:each in="${projectCostingList}" status="i" var="projectCostingInst">				
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">						
							<td rowspan="3">${projectCostingInst?.productCode}</td>
						
						<td><g:message code="etech.project.planned.label" default="Planned" /></td>
						<g:if test="${session['logedInUser']?.hoursOnly == false}">	 
							<td class="text-right"><label for="labor">${projectCostingInst?.plannedLaborTotal}</label></td>
						
							<td class="text-right"><label for="expense">${projectCostingInst?.plannedExpenseTotal}</label></td>
							
							<td class="text-right"><label for="overhead">${projectCostingInst?.plannedStdOHCost}</label></td>
							
							<td class="text-right"><label for="total">${projectCostingInst?.plannedTotal}</label></td>
							<g:if test="${canViewPricing}">
								<td class="text-right">${projectCostingInst?.plannedRevenueTotal}</td>
								
			          			<td class="text-right">${projectCostingInst?.plannedProfit}</td>
			          			
			          			<td class="text-right">${MiscUtils.removePrecision(projectCostingInst?.plannedProfitPercentage)}</td>
			          		</g:if>
		       			</g:if>
	          				<td class="text-right">${projectCostingInst?.plannedHoursTotal}</td>
					
					</tr>					
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:message code="etech.project.actual.label" default="Actual" /></td>
						<g:if test="${session['logedInUser']?.hoursOnly == false}">
							<td class="text-right"><label for="labor">${projectCostingInst?.actualLaborTotal}</label></td>
						
							<td class="text-right"><label for="expense">${projectCostingInst?.actualExpenseTotal}</label></td>
							
							<td class="text-right"><label for="overhead">${projectCostingInst?.actualStdOHCost}</label></td>
							
							<td class="text-right"><label for="total">${projectCostingInst?.actualTotal}</label></td>
							<g:if test="${canViewPricing}">
								<td class="text-right">${projectCostingInst?.actualRevenueTotal}</td>
				          		<td class="text-right">${projectCostingInst?.actualProfit}</td>
			          	  		<td class="text-right">${MiscUtils.removePrecision(projectCostingInst?.actualProfitPercentage)}</td>
			          	  	</g:if>
		          		</g:if>
	          	  			<td class="text-right">${MiscUtils.removePrecision(projectCostingInst?.actualHoursTotal)}</td>	    
						
						</tr>
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:message code="etech.project.variance.label" default="Variance" /></td>
						<g:if test="${session['logedInUser']?.hoursOnly == false}">
							<td class="text-right ${projectCostingInst?.varianceLaborTotal  > 0.00 == false ?:'red-text'}"><label for="labor">${projectCostingInst?.varianceLaborTotal}</label></td>
						
							<td class="text-right ${projectCostingInst?.varianceExpenseTotal  > 0.00 == false ?:'red-text'}"><label for="expense">${projectCostingInst?.varianceExpenseTotal}</label></td>
							
							<td class="text-right ${projectCostingInst?.varianceStdOHCostTotal  > 0.00 == false ?:'red-text'}"><label for="overhead">${projectCostingInst?.varianceStdOHCostTotal}</label></td>
							
							<td class="text-right ${projectCostingInst?.varianceTotal  > 0.00 == false ?:'red-text'}"><label for="total">${projectCostingInst?.varianceTotal}</label></td>
	 					    <g:if test="${canViewPricing}">
		 					    <td class="text-right ${projectCostingInst?.varianceRevenueTotal > 0.00 == false ?:'red-text'}"">${projectCostingInst?.varianceRevenueTotal}</td>
				          		<td class="text-right ${projectCostingInst?.varianceProfit > 0.00 == false ?:'red-text'}">${projectCostingInst?.varianceProfit}</td>
				          		<td class="text-right ${projectCostingInst?.varianceProfitPercentage > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectCostingInst?.varianceProfitPercentage)}</td>
				          	</g:if>
			      		</g:if>
		          			<td class="text-right ${projectCostingInst?.varianceHoursTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(projectCostingInst?.varianceHoursTotal)}</td>
		     
						</tr>					
				</g:each>
				</g:if>
				 <g:else>	
					<tr><td colspan="6" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
				</g:else>
				</tbody>
			</table>
			</div>
		</div>
		<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
</div>
</div>
</div>
		