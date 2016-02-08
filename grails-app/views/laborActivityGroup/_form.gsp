<%@page import="org.solcorp.etech.LaborActivityGroupController"%>
<%@ page import="org.solcorp.etech.LaborActivityGroup" %>
<%@ page import="org.solcorp.etech.Constants" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${laborActivityGroupInstance?.id ? 'edit-code' : ''}">
		
		<label for="code" class="control-label"><g:message code="laborActivityGroup.code.label" default="Code" /><span class="required-indicator">*</span></label>		
		<g:if test="${laborActivityGroupInstance?.id}">		
			<g:if test="${laborActivityGroupInstance?.code?.equals(Constants.DEFAULT_LABOR_ACTIVITY_GROUP) == true}">
				<div>${laborActivityGroupInstance?.code}</div>
				<g:hiddenField name="code" value="${laborActivityGroupInstance?.code}"/>
			</g:if>
			<g:else>
				<g:textField class="form-control  fieldcontain ${hasErrors(bean: laborActivityGroupInstance, field: 'code', 'error')} required pull-left" onblur="keyFilter(this);" name="code" maxlength="${Constants.LABORACTIVITYGROUP_CODE_LENGTH}" value="${laborActivityGroupInstance?.code}" disabled = "${editMode?:disabled}"/>
				
				<a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Code" data-placement="top"></a>
			</g:else>
		</g:if>
		<g:else>
			<g:textField class="form-control  fieldcontain ${hasErrors(bean: laborActivityGroupInstance, field: 'code', 'error')} required pull-left" onblur="keyFilter(this);" name="code" maxlength="${Constants.LABORACTIVITYGROUP_CODE_LENGTH}" value="${laborActivityGroupInstance?.code}" disabled = "${editMode?:disabled}"/>
		</g:else>
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="coe" class="control-label"><g:message code="laborActivityGroup.coe.label" default="COE" /><span class="required-indicator">*</span></label>
		<g:set var="coeList" value="${new LaborActivityGroupController().getList()}" />
		<g:select name="coe" class="selectpicker form-control fieldcontain ${hasErrors(bean: laborActivityGroupInstance, field: 'coe', 'error')} required"  from="${coeList}"  optionKey="id" optionValue="code" value="${laborActivityGroupInstance?.coe?.id}" noSelection="['': '--Select--']" />
	
	</div>
</div>
<g:if test="${laborActivityGroupInstance?.id}">
<div class="col-sm-4 col-md-3">
	<div class="form-group">			
		<label for="description" class="control-label"><g:message code="laborActivityGroup.pay.department.label" default="Pay Department" /></label></br>
		${grailsApplication.config.departmentMap.get(laborActivityGroupInstance?.code)}	
	</div>
</div>
</g:if>
<div class="col-sm-4 col-md-3">
	<div class="form-group">
			
		<label for="description" class="control-label"><g:message code="laborActivityGroup.description.label" default="Description" /></label>
		<g:textArea class="form-control fieldcontain ${hasErrors(bean: laborActivityGroupInstance, field: 'description', 'error')} " name="description" maxlength="${Constants.LABORACTIVITYGROUP_DESCRIPTION_LENGTH}" value="${laborActivityGroupInstance?.description}"/>
	
	</div>
</div>

		

		
