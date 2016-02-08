<%@page import="org.solcorp.etech.Constants"%>
<%@page import="org.solcorp.etech.AddressType"%>
<%@page import="org.solcorp.etech.StateType"%>
<%@ page import="org.solcorp.etech.Address" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label class="control-label">
			Customer Name
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'line1', 'error')}" name="customerName"  value="${addressInstance?.customer?.name}" disabled = "true" />
			
	</div>
</div>
	
<g:hiddenField name="customer.id" value="${addressInstance?.customer?.id}"/>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">	
			
		<label for="line1" class="control-label">
			<g:message code="address.line1.label" default="Line 1" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'line1', 'error')} " name="line1" cols="40" rows="5" value="${addressInstance?.line1}" maxlength="${Constants.ADDRESS_LINE_1_LENGTH}"/>
	
	</div>	
</div>	
			

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="line2" class="control-label">
			<g:message code="address.line2.label" default="Line 2" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'line2', 'error')}" name="line2" cols="40" rows="5" maxlength="${Constants.ADDRESS_LINE_2_LENGTH}" value="${addressInstance?.line2}"/>

	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">	
			
		<label for="city" class="control-label">
			<g:message code="address.city.label" default="City" />
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'city', 'error')}" name="city"  value="${addressInstance?.city}" maxlength="${Constants.ADDRESS_CITY_LENGTH}"/>
	
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="state" class="control-label">
			<g:message code="address.state.label" default="State" />
		</label>
		<g:select class="form-control  fieldcontain ${hasErrors(bean: addressInstance, field: 'state', 'error')}" name="state" from="${StateType?.stateList()}" keys="${StateType.values()*.name()}" value="${addressInstance?.state?.name()}" noSelection="['': '--Select--']"/>

	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">	
	
		<label for="zip" class="control-label">
			<g:message code="address.zip.label" default="Zip" />		
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'zip', 'error')} onlyInteger" name="zip" maxlength="${Constants.ADDRESS_ZIP_LENGTH}" value="${addressInstance?.zip}"/>
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">	
			
		<label for="country" class="control-label">
				<g:message code="address.country.label" default="Country" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'country', 'error')}" name="country" maxlength="${Constants.ADDRESS_COUNTRY_LENGTH}" value="${addressInstance?.country}"/>

	</div>
</div>