<%@ page import="org.solcorp.etech.Industry" %>
<%@ page import="org.solcorp.etech.Constants"%>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${industryInstance?.id ? 'edit-code' : ''}">
		<label for="code" class="control-label">
			<g:message code="industry.code.label" default="Code" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="code" value="${industryInstance?.code}" maxlength="${Constants.INDUSTRY_CODE_LENGTH}" onblur="keyFilter(this);" class="form-control fieldcontain ${hasErrors(bean: industryInstance, field: 'code', 'error')} required pull-left" disabled="${editMode?:disabled}" />
		<g:if test="${industryInstance?.id}"><a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit: Code" data-placement="top"></a></g:if>
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		<label for="name" class="control-label">
			<g:message code="industry.name.label" default="Name" />
		</label>
		<g:textField name="name" maxlength="${Constants.INDUSTRY_NAME_LENGTH}" value="${industryInstance?.name}" class="form-control fieldcontain ${hasErrors(bean: industryInstance, field: 'name', 'error')}" />
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
		<label for="description" class="control-label">
			<g:message code="industry.description.label" default="Description" />
		</label>
		<g:textArea name="description" maxlength="${Constants.INDUSTRY_DESCRIPTION_LENGTH}" value="${industryInstance?.description}" class="form-control fieldcontain ${hasErrors(bean: industryInstance, field: 'description', 'error')}" />
	</div>
</div>