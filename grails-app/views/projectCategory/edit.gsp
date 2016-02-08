<%@ page import="org.solcorp.etech.ProjectCategory" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'projectCategory.label', default: 'ProjectCategory')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<content tag="pageName">projectCategoryPage</content>
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'projectcategory.edit.label', default: 'Edit')}" />
			<h3><g:message code="etech.projectcategory.maintenance.label" default="Project Category Maintenance" /> (Category: ${projectCategoryInstance?.category})</h3>
		</div>


	<div class="white-bg">
		<div class="title">
			<h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2>
		</div>		
		
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			
		<g:hasErrors bean="${projectCategoryInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${projectCategoryInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
			
		<g:form url="[resource:projectCategoryInstance, action:'update']" method="PUT" >
			<g:hiddenField name="version" value="${projectCategoryInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				
				<div class="clearfix"></div>
				
				<div class="col-sm-12 text-center btn-space">
					<g:actionSubmit action="update" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}" />
					<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
					<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
					<g:link class="btn btn-success" action="delete" id="${projectCategoryInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
        		</div>
		</g:form>
	</div>
			
		<script type="text/javascript">
		function confirmDelete() {
			var cd = confirm("This will delete the record.\n Are you sure?");
			if(!cd) {
				alert("The record is not deleted");
				return false;
			}
		} 
		</script>
	</body>
</html>
