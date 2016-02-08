<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<!-- Modal -->
<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_PRICING.name()}">
<g:set value="true" var="canViewPricingPopup"></g:set>
</shiro:hasPermission>
<div class="modal fade dialog-box" id="productCostingPopup"
	role="dialog">
	<div class="modal-dialog modal-lg">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"><g:message code="etech.project.product.dtl.label" default="Product Details"/></h4>
			</div>
			<!-- Modal Body-->
			<div class="modal-body">
				<div class="row">
					<span class="text-left col-sm-12"><label><g:message code="etech.project.project.name.label"	default="Project Name" />:</label> ${productCostingDtl?.projectName} </span> 
					<span class="text-left col-sm-12"><label><g:message code="etech.project.product.name.label" default="Product Code"/>:</label> ${productCostingDtl?.productCode}</span>
				</div>
				<div class="clearfix"></div>
				<hr>
				<div class="table-responsive">

					<table class="table table-striped">
						<thead>
							<tr>

								<th></th>
							<g:if test="${session['logedInUser']?.hoursOnly == false}">	 
								<th class="text-right"><g:message code="etech.project.labor.label"
										default="Labor" /></th>

								<th class="text-right"><g:message code="etech.project.expense.label"
										default="Expense" /></th>

								<th class="text-right"><g:message code="etech.project.overhead.label"
										default="O/H" /></th>

								<th class="text-right"><g:message code="etech.project.total.label"
										default="Total" /></th>
								<g:if test="${canViewPricingPopup}">										
									<th class="text-right"><g:message code="etech.project.revenue.label" default="Revenue" /></th>
				          			
				          			<th class="text-right"><g:message code="etech.project.profit.label" default="Profit" /></th>
				          			
				          			<th class="text-right"><g:message code="etech.project.profit.percentage.label" default="Profit %" /></th>
				          		</g:if>	   
	          				</g:if>       
	          				
	          				<th class="text-right"><g:message code="etech.project.hours.label" default="Hours" /></th>	
										
										

							</tr>
						</thead>
						<tbody>
							<g:if test="${productCostingDtl != null}">
								<tr>

									<td><label for="pln"><g:message
												code="etech.project.planned.label" default="Planned" /></label></td>
								<g:if test="${session['logedInUser']?.hoursOnly == false}">	 
									<td class="text-right"><label for="labor">
											${productCostingDtl?.plannedLaborTotal}
									</label></td>

									<td class="text-right"><label for="expense">
											${productCostingDtl?.plannedExpenseTotal}
									</label></td>

									<td class="text-right"><label for="overhead">${productCostingDtl?.plannedStdOHCost}</label></td>

									<td class="text-right"><label for="total">
											${productCostingDtl?.plannedTotal}
									</label></td>
									<g:if test="${canViewPricingPopup}">
										<td class="text-right">${productCostingDtl?.plannedRevenueTotal}</td>
								
			          					<td class="text-right">${productCostingDtl?.plannedProfit}</td>
			          			
			          					<td class="text-right">${MiscUtils.removePrecision(productCostingDtl?.plannedProfitPercentage)}</td>
			          				</g:if>
		       					</g:if>
	          					<td class="text-right">${productCostingDtl?.plannedHoursTotal}</td>

								</tr>								
								<tr>
	
										<td><label for="act"><g:message
													code="etech.project.actual.label" default="Actual" /></label></td>
									<g:if test="${session['logedInUser']?.hoursOnly == false}">	 
										<td class="text-right"><label for="labor">${MiscUtils.removePrecision(productCostingDtl?.actualLaborTotal)}</label></td>
	
										<td class="text-right"><label for="expense">${productCostingDtl?.actualExpenseTotal}</label></td>
	
										<td class="text-right"><label for="overhead">${MiscUtils.removePrecision(productCostingDtl?.actualStdOHCost)}</label></td>
	
										<td class="text-right"><label for="total">${MiscUtils.removePrecision(productCostingDtl?.actualTotal)}</label></td>
										<g:if test="${canViewPricingPopup}">
											<td class="text-right">${productCostingDtl?.actualRevenueTotal}</td>
							          		<td class="text-right">${MiscUtils.removePrecision(productCostingDtl?.actualProfit)}</td>
						          	  		<td class="text-right">${MiscUtils.removePrecision(productCostingDtl?.actualProfitPercentage)}</td>
						          	  	</g:if>
					          		</g:if>
				          	  			<td class="text-right">${MiscUtils.removePrecision(productCostingDtl?.actualHoursTotal)}</td>	   
									</tr>
									<tr>
	
										<td><label for="vrl"><g:message
													code="etech.project.variance.label" default="Variance" /></label></td>
									<g:if test="${session['logedInUser']?.hoursOnly == false}">	 
										<td class="text-right ${productCostingDtl?.varianceLaborTotal  > 0.00 == false ?:'red-text'}"><label for="labor">${MiscUtils.removePrecision(productCostingDtl?.varianceLaborTotal)}</label></td>
	
										<td class="text-right ${productCostingDtl?.varianceExpenseTotal > 0.00 == false ?:'red-text'}"><label for="expense">${productCostingDtl?.varianceExpenseTotal}</label></td>
	
										<td class="text-right ${productCostingDtl?.varianceStdOHCostTotal > 0.00 == false ?:'red-text'}"><label for="overhead">${MiscUtils.removePrecision(productCostingDtl?.varianceStdOHCostTotal)}</label></td>
	
										<td class="text-right ${productCostingDtl?.varianceTotal  > 0.00 == false ?:'red-text'}"><label for="total">${MiscUtils.removePrecision(productCostingDtl?.varianceTotal)}</label></td>
										<g:if test="${canViewPricingPopup}">
										 <td class="text-right ${productCostingDtl?.varianceRevenueTotal > 0.00 == false ?:'red-text'}"">${productCostingDtl?.varianceRevenueTotal}</td>
						          		 <td class="text-right ${productCostingDtl?.varianceProfit > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(productCostingDtl?.varianceProfit)}</td>
						          		 <td class="text-right ${productCostingDtl?.varianceProfitPercentage > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(productCostingDtl?.varianceProfitPercentage)}</td>
						          		</g:if>
						      		</g:if>
					          			<td class="text-right ${productCostingDtl?.varianceHoursTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(productCostingDtl?.varianceHoursTotal)}</td>
		     
									</tr>								
							</g:if>
							<g:else>
								<tr>
									<td colspan="5" class="no-record-found"><g:message
											code="default.record.not.found" /></td>
								</tr>
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
