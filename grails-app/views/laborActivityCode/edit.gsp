<%@ page import="org.solcorp.etech.LaborActivityCode" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.laboractivitycode.label" default="Employee" /></title>
		<r:require module="laborActivityCode"/>
		<content tag="pageName">laborActivityCodePage</content>
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'laboractivitycode.edit.label', default: 'Edit')}" />
			<h3><g:message code="etech.laboractivitycode.maintenance.label" default="Labor Activity Code"  args="[action]"/> (Code: ${laborActivityCodeInstance?.code})</h3>
		</div>

	<div class="white-bg">
		<div class="title">
			<h2><g:message code="laboractivitycode.edit.label" default="Edit" /></h2>
		</div>
		
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
			
		<g:hasErrors bean="${laborActivityCodeInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${laborActivityCodeInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
			
		<g:form url="[resource:laborActivityCodeInstance, action:'update']" method="PUT" >
			<g:hiddenField name="version" value="${laborActivityCodeInstance?.version}" />
			<fieldset class="form">
				<g:render template="form"/>
			</fieldset>
			
			<div class="clearfix"></div>
			
			<div class="col-sm-12 text-center btn-space">
				<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />
        		<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
        		<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
        		<g:link class="btn btn-success" action="delete" id="${laborActivityCodeInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
        	</div>
		</g:form>
	</div>
		
	</body>
</html>
