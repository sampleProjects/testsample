
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
			<h3><g:message code="etech.laboractivitygroup.maintenance.label" default="Labor Department" /></h3>

	 
		</div>
		
	<div class="white-bg">
		<div class="title text-left"><h2><g:if test="${params?.previousAction == 'newRecord' || laborActivityGroupInstance?.hasErrors() }"><g:message code="default.sub.title.create.label" default= "Create" /></g:if><g:else><g:message code="default.sub.title.search.label" default= "Search" /></g:else></h2></div>	
	 
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
			
			<g:form method="post" name="createForm"  controller="laborActivityGroup">
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				
				<div class="clearfix"></div>
				
        		<div class="col-sm-12 text-center btn-space">
        			<g:actionSubmit action="save"  name="create" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}" />
        			<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
        			<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
        			<g:actionSubmit class="btn btn-success"  action="list" name="search" value="${message(code: 'default.button.search.label', default: 'Search')}"/>
        		</div>
			</g:form>
		</div>

	</body>
</html>
