<%@page import="org.solcorp.etech.ContactType"%>
<%@ page import="org.solcorp.etech.Contact" %>
<%@ page import="org.solcorp.etech.Constants" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label class="control-label">
			Customer Name
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'addressLine1', 'error')} " name="customerName" value="${contactInstance?.customer?.name}" disabled = "true"/>
	
	</div>
</div>

<g:hiddenField name="customer.id" value="${contactInstance?.customer?.id}"/>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="type" class="control-label">
			<g:message code="contact.contactType.label" default="Type" />
			<span class="required-indicator">*</span>
		</label>
		<g:select class="form-control fieldcontain ${hasErrors(bean: contactInstance, field: 'type', 'error')} required" name="type" from="${ContactType?.contactTypeList()}" keys="${ContactType.values()*.name()}" value="${contactInstance?.type?.name()}" noSelection="['': '--Select--']"/>
	
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="name" class="control-label">
			<g:message code="contact.name.label" default="Name" />	
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: contactInstance, field: 'name', 'error')} " name="name" maxlength="${Constants.CONTACT_NAME_LENGTH}" value="${contactInstance?.name}"/>
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="phoneNumber" class="control-label">
			<g:message code="contact.phoneNumber.label" default="Phone" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: contactInstance, field: 'phoneNumber', 'error')}  phoneNumber" name="phoneNumber" maxlength="${Constants.CONTACT_PHONE_NUMBER_LENGTH}" value="${contactInstance?.phoneNumber}" />
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="faxNumber" class="control-label">
			<g:message code="contact.faxNumber.label" default="Fax" />		
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: contactInstance, field: 'faxNumber', 'error')} faxNumber" name="faxNumber" maxlength="${Constants.CONTACT_FAX_NUMBER_LENGTH}" value="${contactInstance?.faxNumber}" />
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
			
		<label for="emailAddress" class="control-label">
			<g:message code="contact.emailAddress.label" default="Email" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: contactInstance, field: 'emailAddress', 'error')}" name="emailAddress" maxlength="${Constants.CONTACT_EMAIL_ADDRESS_LENGTH}" value="${contactInstance?.emailAddress}"/>
	
	</div>
</div>


<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="cellular" class="control-label">
			<g:message code="contact.cellular.label" default="Cellular" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: contactInstance, field: 'cellular', 'error')}  phoneNumber" name="cellular" value="${contactInstance?.cellular}" maxlength="${Constants.CONTACT_CELLULAR_LENGTH}"/>
	
	</div>
</div>