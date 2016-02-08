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
			<h3><g:message code="org.solcorp.etech.Department.paydepartment.code" default="Pay Department" /></h3>
		</div>
		
		<div class="white-bg">
			<div class="title text-left"><h2><g:if test="${params?.previousAction == 'newRecord' || departmentInstance?.hasErrors() }"><g:message code="default.sub.title.create.label" default= "Create" /></g:if><g:else><g:message code="default.sub.title.search.label" default= "Search" /></g:else></h2></div>
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				<g:if test="${flash.errorMessage}">
					<ul class="errors" role="alert">
						<li>${flash.errorMessage}</li>
					</ul>
				</g:if>
				<g:hasErrors bean="${departmentInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${departmentInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</g:hasErrors>
				
				<g:form method="post" name="createForm"  controller="department" action="save" >				
				<fieldset class="form">
					<g:hiddenField name="id" value=""/>
					<g:render template="form"/>
				</fieldset>
				<div class="clearfix"></div>
				<div class="col-sm-12 text-center btn-space">
					<g:submitButton name="update" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}" />
					<g:link class="btn btn-success"  action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
					<g:link class="btn btn-success"  action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
					<g:actionSubmit class="btn btn-success"  action="list" name="search" value="${message(code: 'default.button.search.label', default: 'Search')}"/>
				</div>
				</g:form>			 
				<g:render template="departmentCodeDialog"/>
				<g:render template="laborActivityGroupDialog"/>	
		</div>
	</body>
</html>
