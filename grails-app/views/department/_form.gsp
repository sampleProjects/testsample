<%@page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.Department" %>


<div class="col-sm-4 col-md-3">
	<div class="form-group ${departmentInstance?.id ? 'edit-code' : ''}">
		<g:if test="${!editMode}">
		<label for="code" class="control-label" style="cursor: pointer; text-decoration: underline;" data-toggle="modal" data-target="#deptCodeDialog">
			<g:message code="department.code.label" default="Code" />
		</label>
		</g:if>
		<g:else>
		<label for="code" class="control-label"><g:message code="department.code.label" default="Code" /></label>
		</g:else>
		<label class="control-label"><span class="required-indicator" >*</span></label>
		<g:textField name="code" onblur="keyFilter(this);" id="code" value="${departmentInstance?.code}" class="form-control fieldcontain ${hasErrors(bean: departmentInstance, field: 'code', 'error')} pull-left" disabled="${editMode ?: disabled}" maxlength="${Constants.DEPARTMENT_CODE_LENGTH}" />
		<g:if test="${departmentInstance?.id}">
			<a href="#" onClick="enableCodeField('code');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Code" data-placement="top"></a>
		</g:if>
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${departmentInstance?.id? 'edit-code' : ''}">		
		<label for="laborActivityGroup" class="control-label" style="cursor: pointer; text-decoration: underline;" data-toggle="modal" data-target="#deptLaborActGroupDialog">			
			<g:message code="department.laborActivityGroup.label" default="Labor Department" />
		</label>				
		<g:hiddenField name="laborActivityGroup" id="laborActivityGroup" value="${departmentInstance?.laborActivityGroup?.id}"/>			
		<g:textField name="laborActivityGrouptxt" id="laborActivityGrouptxt" value="${departmentInstance?.laborActivityGroup?.code ?: departmentInstance?.laborActivityGrouptxt}" class="form-control  fieldcontain ${hasErrors(bean: departmentInstance, field: 'laborActivityGroup', 'error')} pull-left required" disabled="${editMode ?: disabled}"/>
		<g:if test="${departmentInstance?.id}">
			<a href="#" onClick="clearLaborActivityGroup();" class="pull-left fa fa-remove" data-toggle="tooltip" data-original-title="Clear: Labor Activity Group" data-placement="top"></a>
		</g:if>
	</div>
</div>

<div class="col-sm-4 col-md-3">
	<div class="form-group">		
		<label for="description" class="control-label" >			
		<g:message code="department.description.label" default="Description" /></label>				
		<g:textArea rows="" cols="" class="form-control fieldcontain ${hasErrors(bean: departmentInstance, field: 'description', 'error')}" name="description"  value="${departmentInstance?.description}"/>
	</div>
</div>