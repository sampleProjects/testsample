<%@ page import="org.solcorp.etech.Constants" %>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<%@ page import="org.solcorp.etech.PermissionType" %>
<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_PRICING.name()}">
<g:set value="true" var="canViewPricingPopup"></g:set>
</shiro:hasPermission>
<div>

	 <div id="d" class="col-sm-4 productDetailsDiv">				
		<div class="form-group">
			<label class="control-label"><g:message code="etech.project.Product.label" default="Product" /></label>
			<g:select id="projectProductDetails${i}_projectProduct" name="projectProductDetails[${i}].projectProduct.id" from="${projectProductList}" optionKey="id" optionValue="code" value="" class="form-control fieldcontain ${hasErrors(bean: projectInstance, field: 'projectDetails['+i+'].projectProduct', 'error')} prodCombo" noSelection="['': '--Select--']"
									onChange="addProjectServiceAjax(this.value,'${i}');"/>
		</div>			
	</div>
	<g:if test="${session['logedInUser']?.hoursOnly == false}">
	 <g:if test="${canViewPricingPopup}">	
		<div class="col-sm-2">						
			<div class="form-group">
				<label class="control-label"><g:message code="etech.project.plan.revenue.label" default="Plan Revenue" /></label>
				<g:textField name="projectProductDetails[${i}].plannedRevenue" id="projectProductDetails${i}_plannedRevenue" value="0.00" maxlength="${Constants.PROJECT_REVENUE_LENGTH}" onblur="revenueVarianceCal(${i})" class="form-control onlyDecimal"/>
			</div>				
		</div>
			
		<div class="col-sm-2">						
			<div class="form-group">
				<label class="control-label" for="actualRevenue"><g:message code="etech.project.act.revenue.label" default="Act Revenue" /></label>
				<g:textField name="projectProductDetails[${i}].actualRevenue" id="projectProductDetails${i}_actualRevenue"  value="0.00" maxlength="${Constants.PROJECT_REVENUE_LENGTH}" onblur="revenueVarianceCal(${i})" class="form-control onlyDecimal" />
			</div>				
		</div>
				
		<div class="col-sm-2">						
			<div class="form-group">
				<label class="control-label" for="varianceRevenue"><g:message code="etech.project.var.revenue.label" default="Var Revenue"  /></label>
				<g:textField name="projectProductDetails[${i}].varianceRevenue" id="projectProductDetails${i}_varianceRevenue" value="0.00" maxlength="${Constants.PROJECT_REVENUE_LENGTH}" class="form-control onlyDecimal" readOnly="true"/>
			</div>				
		</div>
		</g:if>
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
	
	</tbody>
	</table>
</div>
<script>onlyDecimal();</script>