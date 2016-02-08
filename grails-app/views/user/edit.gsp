<%@ page import="org.solcorp.etech.Employee" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.user.label" default="User" /></title>
		<r:require module="user"/>	 		 			 
		<content tag="pageName">userPage</content>
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'default.header.title.edit.label', default: 'Edit')}" />
			<h3>
			 	User (ID: ${userInstance?.username}, Emp ID: ${userInstance?.employee?.code}, Emp Name: ${userInstance?.employee?.getEmployeeName()})
			</h3>
		</div>
		
     		<div class="white-bg">
       			<div class="title"><h2>Edit</h2></div>			
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				<g:hasErrors bean="${userInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${userInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</g:hasErrors>
				
				<g:form url="[resource:userInstance, action:'update']" method="PUT" >
					<g:set value="true" var="editMode"></g:set>
					<g:hiddenField name="version" value="${userInstance?.version}" />
					<fieldset class="form">
						<g:render template="form"/>
					</fieldset>
					<div class="clearfix"></div>	
					<div class="col-sm-12 text-center btn-space">				
						<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />	
						<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
						<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
					</div>				
				</g:form>
				<g:render template="employeeSearchDialog"></g:render>
			</div>
		
	</body>
</html>