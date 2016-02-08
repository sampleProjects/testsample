<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@page import="org.solcorp.etech.utils.MiscUtils"%>
<%@ page import="org.solcorp.etech.Constants" %>
<%@ page import="org.solcorp.etech.PermissionType" %>
<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_PRICING.name()}">
<g:set value="true" var="canViewPricingPopup"></g:set>
</shiro:hasPermission>
<div>
	<g:each var="projectProductInstance" in="${projectProductDetailsList}" status="i">	
		 <script>	
			 var productPlannedTotal = 0.00;
			 var productAcutalTotal = 0.00;
		 </script>
		 
		<hr/>
		
		 <div class="col-sm-4 productDetailsDiv">					
			<div class="form-group">
				<label class="control-label"><g:message code="etech.project.Product.label" default="Product" /></label>	
				<g:if test="${projectProductInstance?.id}">		
					<g:hiddenField name="projectProductDetails[${i}].id" value="${projectProductInstance?.id}"/>										
					<g:select id="projectProductDetails${i}_projectProduct" name="projectProductDetails[${i}].projectProduct" from="${projectProductList}" optionKey="id" optionValue="code" value="${projectProductInstance?.projectProduct?.id}" class="form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'projectProductDetails['+i+'].projectProduct', 'error')} prodCombo" noSelection="['': '--Select--']"
											onChange="addProjectServiceAjax(this.value,'${i}');" disabled="disabled"/>	
				</g:if>	
				<g:else>
					<g:select id="projectProductDetails${i}_projectProduct" name="projectProductDetails[${i}].projectProduct" from="${projectProductList}" optionKey="id" optionValue="code" value="${projectProductInstance?.projectProduct?.id}" class="form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'projectProductDetails['+i+'].projectProduct', 'error')} prodCombo" noSelection="['': '--Select--']"
											onChange="addProjectServiceAjax(this.value,'${i}');" />	
				</g:else>
			</div>	
		</div>
		<g:if test="${session['logedInUser']?.hoursOnly == false}">
		<g:if test="${canViewPricingPopup}">		
			<div class="col-sm-2">						
				<div class="form-group">
					<label class="control-label"><g:message code="etech.project.plan.revenue.label" default="Plan Revenue" /></label>
					<g:textField name="projectProductDetails[${i}].plannedRevenue" id="projectProductDetails${i}_plannedRevenue" value="${projectProductInstance?.plannedRevenue ?: 0.00}" onblur="revenueVarianceCal(${i})" maxlength="${Constants.PROJECT_REVENUE_LENGTH}"  class="form-control onlyDecimal"/>
				</div>				
			</div>
			
			 <div class="col-sm-2">						
				<div class="form-group">
					<label class="control-label" for="actualRevenue"><g:message code="etech.project.act.revenue.label" default="Act Revenue" /></label>
					<g:textField name="projectProductDetails[${i}].actualRevenue" id="projectProductDetails${i}_actualRevenue" value="${projectProductInstance?.actualRevenue ?: 0.00}" onblur="revenueVarianceCal(${i})" maxlength="${Constants.PROJECT_REVENUE_LENGTH}"  class="form-control onlyDecimal"/>
				</div>				
			</div>
				
			<div class="col-sm-2">						
				<div class="form-group">
					<label class="control-label" for="varianceRevenue"><g:message code="etech.project.var.revenue.label" default="Var Revenue"  /></label>
					<g:textField name="projectProductDetails[${i}].varianceRevenue" id="projectProductDetails${i}_varianceRevenue" value="${projectProductInstance?.varianceRevenue ?: 0.00}" maxlength="${Constants.PROJECT_REVENUE_LENGTH}"  class="form-control onlyDecimal" readOnly="true"/>
				</div>				
			</div>
			</g:if>
		</g:if>
		<g:if test="${projectProductInstance?.id}">
			
			<div class="prod-costing-summary-table">
				<g:if test="${session['logedInUser']?.hoursOnly == false}">
					<div class="prod-costing-details"><strong><g:message code="etech.project.planned.label" default="Planned"></g:message></strong> <span id="plannedId_${i}">0.00</span></div>
					<div class="prod-costing-details"><strong><g:message code="etech.project.actual.label" default="Actual"></g:message></strong> <span id="actualId_${i}">0.00</span></div>
					<div class="prod-costing-details"><strong><g:message code="etech.project.variance.label" default="Variance"></g:message></strong> <span id="variance_${i}">0.00</span></div>
				</g:if>
				<div data-toggle="tooltip" title="Product Detail" style="cursor: pointer;" class="pull-left prod-costing-action-table" onclick="productCostingPopup(${projectProductInstance?.id});"><div class="fa fa-info table-icon"></div></div>
			</div>
			
	  	</g:if>
	  	
		<div class="clearfix"></div>
	
		<table id="serviceTbl${i}" class="table serviceHeader margintop25">
			<thead>
				<tr>
					<th width="26%">Service</th>
					<th width="2%" class="text-center"></th>					
					<g:if test="${session['logedInUser']?.hoursOnly == false}">					
						<th width="7%" class="text-center">Labor</th>
						<th width="7%" class="text-center">Expense</th>
						<th width="7%" nowrap class="text-center">O/H</th>
						<th width="7%" nowrap class="text-center">Total</th>
					</g:if>
					<th width="3%" class="text-center">Hrs</th>
					<th width="2%" nowrap></th>
					<th width="13%">Schedule</th>
					<th width="13%">Revised</th>
					<th width="13%">Actual</th>
				</tr>
			</thead>
			<tbody>			
			<g:each var="serviceDetailInstance" in="${projectProductInstance?.projectServiceDetails}" status="j">		
				
				<tr id="projectDetails${j}" class="serviceDetailsDiv_${i}" >
					
					<g:if test="${serviceDetailInstance?.id}">
						${serviceDetailInstance?.getServiceSummary()}
						<g:hiddenField name="projectProductDetails[${i}].projectServiceDetails[${j}].id" value="${serviceDetailInstance?.id}"/>
					</g:if>
						
					<g:hiddenField name="projectProductDetails[${i}].projectServiceDetails[${j}].plannedLabor.id" value="${serviceDetailInstance?.plannedLabor?.id}"/>
					<g:hiddenField name="projectProductDetails[${i}].projectServiceDetails[${j}].plannedExpense.id" value="${serviceDetailInstance?.plannedExpense?.id}"/>
					<g:hiddenField name="tempService_${i}_${j}" value="${serviceDetailInstance?.service?.id}"/>
						
					<g:set value="${serviceDetailInstance?.plannedTotal != 0.00 ||	serviceDetailInstance?.actualTotal != 0.00}" var="isLaborExpenseAdded"></g:set>
						
					
					<td rowspan="3" class="services-combo">
					
						
						<span  id="serviceComboDiv_${i}_${j}" class="${hasErrors(bean: projectInstance, field: 'projectProductDetails['+i+'].projectServiceDetails['+j+'].service', 'error')}">
							<g:render template="serviceComboAjax" model="[i: i, j: j]"></g:render>
						</span>
						
						<script>		
										
						var productValue = $("#projectProductDetails${i}_projectProduct").val();							
						
						function loadServiceCombo(productId, serviceUpId, i, j, isLaborExpenseAdded){																 				 
							<g:remoteFunction controller="project" action="getServiceListByProject" asynchronous="false"  update="'+serviceUpId+'"  params="'productId='+productId+'&i='+i+'&j='+j+'&isLaborExpenseAdded='+isLaborExpenseAdded"/>
						}
								
						if(productValue){
							loadServiceCombo(productValue, "serviceComboDiv_${i}_${j}", ${i}, ${j}, ${isLaborExpenseAdded});					
							$("#projectProductDetails${i}_projectServiceDetails${j}_service").val($("#tempService_${i}_${j}").val());							
							$("#projectProductDetails${i}_projectServiceDetails${j}_service").addClass($("#serviceComboDiv_${i}_${j}").attr('class'));						
						}
								
						productPlannedTotal = productPlannedTotal + ${serviceDetailInstance.plannedTotal};
	
						productAcutalTotal = productAcutalTotal + ${serviceDetailInstance.actualTotal};					
								
						$("#plannedId_"+${i}).text(productPlannedTotal.toFixed(2));
						$("#actualId_"+${i}).text(productAcutalTotal.toFixed(2));
								
						var varianceTotal = productAcutalTotal.toFixed(2) - productPlannedTotal.toFixed(2);
						$("#variance_"+${i}).text(varianceTotal.toFixed(2));							
	
						if(varianceTotal > 0.00) {								
							 $("#variance_"+${i}).addClass('red-text-span');
						}		
						</script>
						
						<div class="col-sm-12 text-center margintopbottom15">						
							<div class="display-block">
									
								<g:if test="${!isLaborExpenseAdded}">								
									<a onclick="addServiceDetailRow('${i}','${j}');" class="fa fa-plus project-icons pull-left action-plus"></a>								
									<g:submitToRemote id="removeProjectDtl" class="icon-border-left remove-img pull-left marginleft5" url="[action: 'removeProjectDtl']" method="post"  before ="removeServiceDetailIndex('${i}', '${j}')" update="projectDtlDivLst" value="" />
								</g:if>
								<g:else>
									<a onclick="addServiceDetailRow('${i}','${j}');" class="fa fa-plus project-icons pull-left"></a>
								</g:else>	
									
								<g:if test="${serviceDetailInstance?.id}">
									<g:link data-toggle="tooltip" title="Labor" controller="projectLabor" action="create" id="${serviceDetailInstance?.id}" class="labor-icons project-icons icon-border-left pull-left isDirtyCheck" />
									<g:link data-toggle="tooltip" title="Expense" controller="projectExpense" action="create" id="${serviceDetailInstance?.id}" class="fa fa-briefcase project-icons icon-border-left pull-left isDirtyCheck" />																		 
								</g:if>								
							</div>
						</div>
					</td>
						
					<td><span class="planned-color">Plan</span></td>
					
					<g:if test="${session['logedInUser']?.hoursOnly == false}">
						<td class="text-right">${serviceDetailInstance?.plannedLaborTotal?:0.00}</td>					
							
						<td class="text-right">${serviceDetailInstance?.plannedExpenseTotal?:0.00}</td>
							
						<td class="text-right">${serviceDetailInstance?.plannedStdOHCostTotal?:0.00}</td>
							
						<td class="text-right">${serviceDetailInstance?.plannedTotal}</td>
					</g:if>
					<td class="text-right">${serviceDetailInstance?.plannedHoursTotal?:0.00}</td>	 
					<td><span class="planned-color">Start</span></td>
						
					<td><g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].scheduleStartDate', 'fieldValue': serviceDetailInstance?.scheduleStartDate, 'cssClass': 'project-datepicker']"></g:render></td>
					<td><g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].reviseStartDate', 'fieldValue': serviceDetailInstance?.reviseStartDate, 'cssClass': 'project-datepicker']"></g:render></td>
					<td><g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].actualStartDate', 'fieldValue': serviceDetailInstance?.actualStartDate, 'cssClass': 'project-datepicker']"></g:render></td> 
				</tr>
				
					<tr>							
						<td>
							<span class="planned-color">Act</span>
						</td>
												
						<g:if test="${session['logedInUser']?.hoursOnly == false}">		
							<g:if test="${serviceDetailInstance?.actualLabor?.laborDetails?.size() > 0 }">							
								<td class="text-right"><g:link action="getAllActualLaborEmployeeList" controller="project" id="${serviceDetailInstance?.id}" class="text-underline">${MiscUtils.removePrecision(serviceDetailInstance?.actualLaborTotal?:0.00)}</g:link></td>								
							</g:if>
							<g:else><td class="text-right">0.00</td></g:else>
							
							<g:if test="${serviceDetailInstance?.actualExpense?.actualExpenseDetails?.size() > 0 }">			
								<td class="text-right"><g:link action="getAllServiceActualExpenseList" controller="project" id="${serviceDetailInstance?.id}" class="text-underline">${MiscUtils.removePrecision(serviceDetailInstance?.actualExpenseTotal?:0.00)}</g:link></td>
							</g:if>
							<g:else><td class="text-right">0.00</td></g:else>
							<td class="text-right">${MiscUtils.removePrecision(serviceDetailInstance?.actualStdOHCostTotal)?:0.00 }</td>
								
							<td class="text-right">${MiscUtils.removePrecision(serviceDetailInstance?.actualTotal)?:0.00}</td>
						</g:if>
						<td class="text-right">${MiscUtils.removePrecision(serviceDetailInstance?.actualHoursTotal)?:0.00}</td>
						<td><span class="planned-color">End</span></td>
							
						<td><g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].scheduleCompDate', 'fieldValue': serviceDetailInstance?.scheduleCompDate, 'cssClass': 'project-datepicker']"></g:render></td>
				
						<td><g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].reviseCompDate', 'fieldValue': serviceDetailInstance?.reviseCompDate, 'cssClass': 'project-datepicker']"></g:render></td>
				
						<td><g:render template="/common/dateComponent" model="['fieldName': 'projectProductDetails['+i+'].projectServiceDetails['+j+'].actualCompDate', 'fieldValue': serviceDetailInstance?.actualCompDate, 'cssClass': 'project-datepicker']"></g:render></td>
					</tr>
					<tr>
						<td><span class="planned-color">Var</span></td>
						<g:if test="${session['logedInUser']?.hoursOnly == false}">
							<td class="text-right ${serviceDetailInstance?.varianceLaborTotal > 0.00 == false ?:'red-text' }">${MiscUtils.removePrecision(serviceDetailInstance?.varianceLaborTotal)}</td>
								
							<td class="text-right ${serviceDetailInstance?.varianceExpenseTotal > 0.00 == false ?:'red-text'}">${serviceDetailInstance?.varianceExpenseTotal}</td>
								
							<td class="text-right ${serviceDetailInstance?.varianceOHCostTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(serviceDetailInstance?.varianceOHCostTotal)}</td>
								
							<td class="text-right  ${serviceDetailInstance?.varianceTotal > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(serviceDetailInstance?.varianceTotal)}</td>
						</g:if>
						<td class="text-right ${MiscUtils.removePrecision(serviceDetailInstance?.varianceHoursTotal) > 0.00 == false ?:'red-text'}">${MiscUtils.removePrecision(serviceDetailInstance?.varianceHoursTotal)?:0.00}</td>	
						<td></td>
						<td></td>	
						<td></td>
					</tr>
			</g:each>			
			</tbody> 
		</table>		
	</g:each>  	
</div> 