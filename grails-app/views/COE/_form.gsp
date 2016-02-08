<%@ page import="org.solcorp.etech.COE"%>
<%@ page import="org.solcorp.etech.Constants"%>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${COEInstance?.id ? 'edit-code' : ''}">
		
		<label for="code" class="control-label">
			<g:message code="etech.COE.code.label" default="Code" />
			<span class="required-indicator">*</span>
		</label>
		<g:if test="${COEInstance?.id}">		
			<g:if test="${COEInstance?.code?.equals(Constants.DEFAULT_COE) == true}">
				<div>${COEInstance?.code}</div>
				<g:hiddenField name="code" value="${COEInstance?.code}"/>
			</g:if>
			<g:else>
				<g:textField name="code" value="${COEInstance?.code}" maxlength="${Constants.COE_CODE_LENGTH}" onblur="keyFilter(this);" class="form-control fieldcontain ${hasErrors(bean: COEInstance, field: 'code', 'error')} required pull-left" disabled="${editMode?:disabled}" />
				<g:if test="${COEInstance?.id}"><a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit: Code" data-placement="top"></a></g:if>
			</g:else>
		</g:if>
		<g:else>
			<g:textField name="code" value="${COEInstance?.code}" maxlength="${Constants.COE_CODE_LENGTH}" onblur="keyFilter(this);" class="form-control fieldcontain ${hasErrors(bean: COEInstance, field: 'code', 'error')} required pull-left" disabled="${editMode?:disabled}" />
		</g:else>
		
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">

		<label for="description" class="control-label">
			<g:message code="etech.COE.description.label" default="Description" />
		</label>
		<g:textArea rows="" cols="" name="description" value="${COEInstance?.description}" maxlength="${Constants.COE_DESCRIPTION_LENGTH}" class="form-control fieldcontain ${hasErrors(bean: COEInstance, field: 'description', 'error')}" />

	</div>
</div>