<%@ page import="org.solcorp.etech.LaborActivityGroup" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'laborActivityGroup.label', default: 'LaborActivityGroup')}" />
		<title><g:message code="etech.laboractivitygroup.label" default="Labor Department" /></title>
		<r:require module="laborActivityGroup"/>
		<content tag="pageName">laborActivityGroupPage</content>
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'laborActivitygroup.edit.label', default: 'Edit')}" />
			<h3><g:message code="etech.laboractivitygroup.maintenance.label" default="Labor Department" args="[action]" /> (Code: ${laborActivityGroupInstance?.code})</h3>
		</div>

<div class="col-sm-12">
	<div class="white-bg">
		<div class="title">
			<h2><g:message code="laborActivitygroup.edit.label" default="Edit" /></h2>
		</div>		
		
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:hasErrors bean="${laborActivityGroupInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${laborActivityGroupInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:set value="true" var="editMode"></g:set>
			<g:form url="[resource:laborActivityGroupInstance, action:'update']" method="PUT" >
				<g:hiddenField name="version" value="${laborActivityGroupInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
										
				</fieldset>
				<div class="clearfix"></div>
				<g:render template="laborGrpServiceList"/>
				<g:render template="laborActivityCodesList"/>
				<div class="col-sm-12 text-center btn-space">
        			<g:actionSubmit action="update"  name="create" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}" />
        			<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
        			<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
        			<g:link class="btn btn-success" action="delete" id="${laborActivityGroupInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
        		</div>
				
			</g:form>
		</div>
	</div>
	</body>
</html>
