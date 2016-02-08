<%@page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.ProjectCategory" %>

<div class="col-sm-4 col-md-3">
	<div class="form-group ${projectCategoryInstance?.id ? 'edit-code' : ''}">
		
		<label for="category" class="control-label">
			<g:message code="projectCategory.category.label" default="Category" />
			<span class="required-indicator">*</span>
		</label>
		<g:if test="${projectCategoryInstance?.id}">
			<g:textField name="category" onblur="keyFilter(this);" class="form-control fieldcontain ${hasErrors(bean: projectCategoryInstance, field: 'category', 'error')} required pull-left" value="${projectCategoryInstance?.category}" disabled = "disabled" maxlength="${Constants.PROJECT_CATEGORY_LENGTH}" />
			<a href="#" onClick="enableCodeField('category');" class="pull-left fa fa-pencil" data-toggle="tooltip" data-original-title="Edit : Category" data-placement="top"></a>
		</g:if>
		<g:else>
			<g:textField name="category" onblur="keyFilter(this);" class="form-control fieldcontain ${hasErrors(bean: projectCategoryInstance, field: 'category', 'error')}" value="${projectCategoryInstance?.category}" maxlength="${Constants.PROJECT_CATEGORY_LENGTH}" disabled = "${projectCategoryInstance?.id?:disabled}"/>
		</g:else>
		
	</div>
</div>
			
<div class="col-sm-4 col-md-3">
	<div class="form-group">
	
		<label for="description" class="control-label">
			<g:message code="projectCategory.description.label" default="Description" />
		</label>
		<g:textArea name="description" class="form-control fieldcontain ${hasErrors(bean: projectCategoryInstance, field: 'description', 'error')} required" value="${projectCategoryInstance?.description}" maxlength="${Constants.PROJECT_CATEGORY_DESCRIPTION_LENGTH}" />
				
	</div>
</div>