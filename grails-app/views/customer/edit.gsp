<%@ page import="org.solcorp.etech.Customer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.customer.label" args="Customer" /></title>
		<r:require module="customer"/>
		<content tag="pageName">customerPage</content>
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'default.header.title.edit.label', default: 'Edit')}" />
			<h3><g:message code="etech.customer.maintenance.label" args="[action]" default="Customer Maintenance"/> (Code: ${customerInstance?.code})</h3>
		</div>
		
      		<div class="white-bg">
		
			<div class="title">
				<h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2>
			</div>
			
			<g:if test="${customerInstance.isSystemJobUser()}"> 
					<div class="col-sm-12"><div class="alert alert-danger"><g:message code="etech.customer.emp.imported.job.message" default="This record has recently been modified by the scheduled job."/></div></div>
			</g:if>
			
			<div class="clearfix"></div>
				
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${customerInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${customerInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:set value="true" var="editMode"></g:set>
			<g:form url="[resource:customerInstance, action:'update']" method="PUT" >
				<g:hiddenField name="version" value="${customerInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				
				<div class="clearfix"></div>
					<div class="col-sm-12 text-center btn-space">
						<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />
						<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
						<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
						<g:link class="btn btn-success" action="delete" id="${customerInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
						<g:link class="btn btn-success" controller="contact" action="create" params="['customer.id': customerInstance?.id]">Add Contact</g:link>
					</div>
	
			</g:form>
			<g:render template="../common/searchDialog/acctExecutiveSearchDialog"></g:render>
			<g:render template="salesSearchDialog"></g:render>
			<g:render template="industrySearchDialog"></g:render>	
		</div>
		
	</body>
</html>
