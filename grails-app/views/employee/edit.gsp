<%@ page import="org.solcorp.etech.Employee" %>
<%@ page import="org.solcorp.etech.StatusType" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.employee.label" default="Employee" /></title>	
		 <r:require module="employee"/>	 			 
		 <content tag="pageName">employeePage</content>
	</head>
	<body>
		<div class="navigation">			
			<h3><g:message code="etech.employee.emp.maintenance.label" default="Employee Maintenance"/> (Code: ${employeeInstance?.code})</h3>
		</div>
		 <div class="col-sm-12">
     		<div class="white-bg">
       			<div class="title"><h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2></div>       			
       			<g:if test="${employeeInstance.isSystemJobUser()}"> 
					<div class="col-sm-12"><div class="alert alert-danger"><g:message code="etech.employee.emp.imported.job.message" default="This record has recently been modified by the scheduled job. Please set appropriate values for Labor Department, Labor Activity Code, Pay Dept, Status and Employee Type if required."/></div></div>
				</g:if>
				<div class="clearfix"></div>
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				
				<g:if test="${flash.newUserMessage}">
					<div class="message" role="status">
						<g:message code="etech.employee.user.login.emp.label" default="User Login does not exist for this employee."/>
						<g:link action="create" controller="user" id="${employeeInstance?.id}" ><g:message code="default.delete.label" default="Click here" /></g:link>
						<g:message code="etech.employee.create.user.login.label" default="to create the User Login"/>
					</div>
				</g:if>
				
				<g:hasErrors bean="${employeeInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${employeeInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</g:hasErrors>
				<g:set value="true" var="editMode"></g:set>
				
				<g:form url="[resource:employeeInstance, action:'update']" method="PUT" >
					<g:hiddenField name="version" value="${employeeInstance?.version}" />
					<fieldset class="form">
						<g:render template="form"/>						
					</fieldset>
					<div class="clearfix"></div>
					<g:render template="assignedCustomersList"/>
					<div class="clearfix"></div>
					<g:render template="assignedProjectsList"/>
					<div class="col-sm-12 text-center btn-space">
						<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />	
						<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
						<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
						<g:link class="btn btn-success" action="delete" id="${employeeInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>							
					</div>
				</g:form>
				<g:render template="laborActivityGrpDialog"/>
				<g:render template="departmentCodeDialog"/>
			</div>
		</div>		 
	</body>
</html>
