<%@page import="org.solcorp.etech.StatusType"%>
<%@page import="org.solcorp.etech.StateType"%>
<%@page import="org.solcorp.etech.StatementType"%>
<%@page import="org.solcorp.etech.CreditCodeType"%>
<%@page import="org.solcorp.etech.PaymentTermsType"%>
<%@page import="org.solcorp.etech.PriceLevelType"%>
<%@ page import="org.solcorp.etech.Constants" %>

<g:hiddenField name="address.id" value="${addressInstance?.id}"/>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${customerInstance?.id ? 'edit-code' : ''}">	
		<g:if test="${customerInstance?.id}">			
			<label for="code" class="control-label"><g:message code="customer.code.label" default="Code" /><span class="required-indicator">*</span></label>		
		</g:if>		
		<g:else>		
			<label for="code" class="control-label" style="cursor: pointer; text-decoration: underline;" data-toggle="modal" data-target="#customerImportDialog">
				<g:message code="customer.code.label" default="Code" /></label><label for="code" class="control-label"><span class="required-indicator">*</span></label>
		</g:else>						
		<g:textField class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'code', 'error')} pull-left" name="code" onblur="keyFilter(this);" maxlength="${Constants.CUSTOMER_CODE_LENGTH}"  value="${customerInstance?.code}" disabled="${editMode ?: disabled}"/>
		<g:if test="${customerInstance?.id}">
			<a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Code" data-placement="top"></a>
		</g:if>		
	</div>
</div>
			
<div class="col-sm-4 col-md-3">
	<div class="form-group"> 
	
		<label for="name" class="control-label">
			<g:message code="customer.name.label" default="Name" /></label>
			<label for="code" class="control-label"><span class="required-indicator">*</span>
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: customerInstance, field: 'name', 'error')}" name="name" maxlength="${Constants.CUSTOMER_NAME_LENGTH}" value="${customerInstance?.name}"/>
	
	</div>
</div>
			
<div class="col-sm-4 col-md-3">				
	<div class="form-group edit-code">			
		<label for="acctExecutive" class="control-label text-underline" onclick="accExecutivePopup()"><g:message code="app.accountDirector.label" default="Account Director" /></label>
		<label for="code" class="control-label"><span class="required-indicator">*</span></label>
		
		<g:hiddenField name="acctExecutive" value="${customerInstance?.acctExecutive?.id}" />
		 
		<g:textField name="acctExecutiveTxt" value="${customerInstance?.acctExecutive?.code ?: customerInstance?.acctExecutiveTxt}"  class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'acctExecutive', 'error')} pull-left" disabled="${editMode ?: disabled}" />
		
		<span onClick="clearAccountExecutive();" class="pull-left fa fa-remove cursor-pointer" data-toggle="tooltip" data-original-title="Clear: Account Director" data-placement="top"></span>
		
	</div>
</div>

<div class="col-sm-4 col-md-3">				
	<div class="form-group edit-code">			
		<label for="salesExecutive" class="control-label text-underline" onclick="salesExecutivePopup()"><g:message code="etech.customer.acct.executive.label" default="Sales Executive" /></label>
		
		<g:hiddenField name="salesExecutive" value="${customerInstance?.salesExecutive?.id}" />
		
		<g:textField name="salesExecutiveTxt" value="${customerInstance?.salesExecutive?.code ?: customerInstance?.salesExecutiveTxt}"  class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'salesExecutiveTxt', 'error')} pull-left" disabled="${editMode ?: disabled}"/>
	
		<span onClick="clearSalesExecutive();" class="pull-left fa fa-remove cursor-pointer" data-toggle="tooltip" data-original-title="Clear: Sales Executive" data-placement="top"></span>
		
	</div>
</div>

<div class="clearfix"></div>
			

<div class="col-sm-4 col-md-3">				
	<div class="form-group edit-code">			
		<label for="industry" class="control-label text-underline" onclick="industryPopup()"><g:message code="etech.customer.industry.label" default="Industry" /></label>
		
		<g:hiddenField name="industry" value="${customerInstance?.industry?.id}" />
		
		<g:textField name="industryTxt" value="${customerInstance?.industry?.code ?: customerInstance?.industryTxt}" maxlength="50" class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'industryTxt', 'error')} pull-left" disabled="${editMode ?: disabled}"/>
	
		<span onClick="clearIndustryExecutive();" class="pull-left fa fa-remove cursor-pointer" data-toggle="tooltip" data-original-title="Clear: Industry" data-placement="top"></span>
		
	</div>
</div>


<div class="col-sm-4 col-md-3">
	<div class="form-group"> 	 
	
		<label for="type" class="control-label">
			<g:message code="customer.type.label" default="Type" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'type', 'error')}" name="type" maxlength="${Constants.CUSTOMER_TYPE_LENGTH}" value="${customerInstance?.type}"/>
	
	</div>
</div>
			
<div class="col-sm-4 col-md-3">
	<div class="form-group"> 
		
		<label for="category" class="control-label">
			<g:message code="customer.category.label" default="Category" />	
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: customerInstance, field: 'category', 'error')}" name="category" maxlength="${Constants.CUSTOMER_CATEGORY_LENGTH}" value="${customerInstance?.category}"/>
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group"> 
			
		<label for="status" class="control-label">
			<g:message code="customer.status.label" default="Status" />	
		</label>
		<g:select class="selectpicker form-control  fieldcontain ${hasErrors(bean: customerInstance, field: 'status', 'error')}" name="status" from="${StatusType?.customerStatusTypeList()}" keys="${StatusType?.values()*.name()}" value="${customerInstance?.status?.name()}" noSelection="['': '--Select--']"/>
	
	</div>
</div>
			
<div class="col-sm-4 col-md-3">
	<div class="form-group"> 
			
		<label for="priceLevel" class="control-label">
			<g:message code="customer.priceLevel.label" default="Price Level" />	
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'priceLevel', 'error')}" name="priceLevel" from="${PriceLevelType?.priceLevelList()}" keys="${PriceLevelType?.values()*.name()}" value="${customerInstance?.priceLevel?.name()}" noSelection="['': '--Select--']"/>
	
	</div>
</div>


<div class="col-sm-4 col-md-3">
	<div class="form-group">
			
		<label for="paymentTerms" class="control-label">
			<g:message code="customer.paymentTerms.label" default="Payment Terms" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'paymentTerms', 'error')} " name="paymentTerms" from="${PaymentTermsType?.PaymentTermsList()}" keys="${PaymentTermsType.values()*.name()}" value="${customerInstance?.paymentTerms?.name()}" noSelection="['': '--Select--']"/>

	</div>
</div>


<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="creditCode" class="control-label">
			<g:message code="customer.creditCode.label" default="Credit Code" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'creditCode', 'error')}" name="creditCode" from="${CreditCodeType?.CreditCodeList()}" keys="${CreditCodeType.values()*.name()}" value="${customerInstance?.creditCode?.name()}" noSelection="['': '--Select--']"/>
			
	</div>
</div>
	

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label for="creditLimit" class="control-label">
			<g:message code="customer.creditLimit.label" default="Credit Limit" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'creditLimit', 'error')} onlyDecimal" name="creditLimit"  value="${customerInstance?.creditLimit}" />
	
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="statementType" class="control-label">
			<g:message code="customer.statementType.label" default="Statement Type" />	
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'statementType', 'error')}" name="statementType" from="${StatementType?.StatementTypeList()}" keys="${StatementType.values()*.name()}" value="${customerInstance?.statementType?.name()}" noSelection="['': '--Select--']"/>
			
	</div>
</div>
	
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="outsideRep" class="control-label">
			<g:message code="customer.outsideRep.label" default="Outside Rep" />	
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: customerInstance, field: 'outsideRep', 'error')}  onlyInteger" name="outsideRep"  maxlength="${Constants.CUSTOMER_OUTSIDE_REP_LENGTH}" value="${customerInstance?.outsideRep}"  style="text-align: right"/>

	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="federalId" class="control-label" >
			<g:message code="customer.federalId.label" default="Federal Id" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'federalId', 'error')}" name="federalId" maxlength="${Constants.CUSTOMER_FEDERAL_ID_LENGTH}"  value="${customerInstance?.federalId}"/>	
	
	</div>
</div>
	  
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="stateId" class="control-label">
			<g:message code="customer.stateId.label" default="State Id" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'stateId', 'error')}" name="stateId" maxlength="${Constants.CUSTOMER_STATE_ID_LENGTH}" value="${customerInstance?.stateId}"/>
	
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		
		<label for="comments" class="control-label">
			<g:message code="customer.comments.label" default="Comments" />	
		</label>
		<g:textArea rows="" cols="" class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'comments', 'error')}" name="comments" maxlength="${Constants.CUSTOMER_COMMENTS_LENGTH}" value="${customerInstance?.comments}"/>
	
	</div>
</div>

<div class="clearfix"></div>

<div class="title">		
	<g:if test="${customerInstance?.id}">
		<h2><g:link controller="address" action="create" params="['customer.id': customerInstance?.id]" class="isDirtyCheck">Ship-to Address</g:link></h2>
	</g:if>
	<g:else>
		<h2>Billing Address</h2>
	</g:else>  
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="line1" class="control-label">
			<g:message code="address.line1.label" default="Line 1" />	
		</label>
		<g:textArea rows="" cols="" class="form-control fieldcontain ${hasErrors(bean: customerInstance, field: 'line1', 'error')}" name="line1"  maxlength="${Constants.ADDRESS_LINE_1_LENGTH}" value="${addressInstance?.line1}"/>
	
	</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">
				
		<label for="line2" class="control-label">
			<g:message code="address.line2.label" default="Line 2" />	
		</label>
		<g:textArea rows="" cols="" class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'line2', 'error')} " name="line2" maxlength="${Constants.ADDRESS_LINE_2_LENGTH}" value="${addressInstance?.line2}"/>

	</div>
</div>
			
<div class="col-sm-4 col-md-3">
	<div class="form-group">
			
		<label for="city" class="control-label">
			<g:message code="address.city.label" default="City" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'city', 'error')} " name="city" maxlength="${Constants.ADDRESS_CITY_LENGTH}" value="${addressInstance?.city}"/>

	</div>
</div>
				
				
<div class="col-sm-4 col-md-3">
	<div class="form-group">
				
		<label for="state" class="control-label">
			<g:message code="address.state.label" default="State" />
		</label>
		<g:select class="selectpicker form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'state', 'error')}" name="state" from="${StateType?.stateList()}" keys="${StateType.values()*.name()}" value="${addressInstance?.state?.name()}" noSelection="['': '--Select--']"/>
		
	</div>	
</div>
		
<div class="clearfix"></div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
			 
		<label for="zip" class="control-label">
			<g:message code="address.zip.label" default="Zip" />	
		</label>
		<g:textField class="form-control  fieldcontain ${hasErrors(bean: addressInstance, field: 'zip', 'error')}  onlyInteger" name="zip" maxlength="${Constants.ADDRESS_ZIP_LENGTH}" value="${addressInstance?.zip}"/>
		
	</div>
</div>
		
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="country" class="control-label">
			<g:message code="address.country.label" default="Country" />	
		</label>
		<g:textField class="form-control fieldcontain ${hasErrors(bean: addressInstance, field: 'country', 'error')} " name="country" maxlength="${Constants.ADDRESS_COUNTRY_LENGTH}" value="${addressInstance?.country}"/>
	
	</div>
</div>


<div class="clearfix"></div>
	
<g:if test="${customerInstance?.id}">
	<div class="title"><h2><g:link controller="contact" action="create" params="['customer.id': customerInstance?.id]" class="isDirtyCheck">Contact</g:link></h2></div>
		<g:if test="${!contactInstance?.id}">
			<div class="col-sm-6">
				<div class="form-group">
					Billing Contact is not added. <g:link controller="contact" action="create" params="['customer.id': customerInstance?.id]" class="isDirtyCheck">Click here to add Contact</g:link>
				</div>
			</div>
		</g:if>
</g:if>
	
<g:if test="${contactInstance?.id}">
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
		
			<label for="name" class="control-label">
				<g:message code="contact.name.label" default="Name" />
			</label>
			${contactInstance?.name}

		</div>
	</div>
						
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
		
			<label for="phoneNumber" class="control-label">
				<g:message code="contact.phoneNumber.label" default="Phone Number" />
			</label>
			${contactInstance?.phoneNumber}
		
		</div>
	</div>
			
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
				
			<label for="faxNumber" class="control-label">
				<g:message code="contact.faxNumber.label" default="Fax Number" />	
			</label>
			${contactInstance?.faxNumber}

		</div>
	</div>
			
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
				
			<label for="emailAddress" class="control-label">
				<g:message code="contact.emailAddress.label" default="Email Address" />	
			</label>
			${contactInstance?.emailAddress}
		
		</div>
	</div>
				
	<div class="clearfix"></div>	
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
		
			<label for="cellular" class="control-label">
				<g:message code="contact.cellular.label" default="Cellular" />
			</label>
			${contactInstance?.cellular}
			
		</div>
	</div>
</g:if> 