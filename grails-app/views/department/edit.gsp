<%@ page import="org.solcorp.etech.Department" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.department.label" /></title>
		<r:require module="department"/>
		<content tag="pageName">departmentPage</content>
	</head>
	<body>

		<div class="navigation">	
			<h3><g:message code="org.solcorp.etech.Department.paydepartment.code" default="Pay Department" /> (Code: ${departmentInstance?.code})</h3>
		</div>
     		<div class="white-bg">
       			<div class="title"><h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2></div>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${departmentInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${departmentInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:departmentInstance, action:'update']" method="PUT" >
				<g:set value="true" var="editMode"></g:set>
				<g:hiddenField name="version" value="${departmentInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<div class="clearfix"></div>
				<div class="col-sm-12 text-center btn-space">
					<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'save')}" />
					<g:link class="btn btn-success"  action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
					<g:link class="btn btn-success"  action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
					<g:link class="btn btn-success" action="delete" id="${departmentInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
				</div>
			</g:form>
			<g:render template="laborActivityGroupDialog"/>		
		</div>
	</body>
</html>
