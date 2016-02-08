<%@ page import="org.solcorp.etech.Industry" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.industry.label" default="Industry" /></title>
		<content tag="pageName">industryPage</content>
	</head>
	<body>
	
		<div class="navigation">			
			<h3><g:message code="etech.industry.label" default="Industry" />(Code: ${industryInstance?.code})</h3>
		</div>
		
		<div class="col-sm-12">
     		<div class="white-bg">
     			<div class="title"><h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2></div>
     			
       			<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				
				<g:hasErrors bean="${industryInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${industryInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</g:hasErrors>
				<g:set value="true" var="editMode"></g:set>
				
				<g:form url="[resource:industryInstance, action:'update']" method="PUT" >
					<g:hiddenField name="version" value="${industryInstance?.version}" />
					<fieldset class="form">
						<g:render template="form"/>       
					</fieldset>				
					<div class="clearfix"></div>
					<div class="col-sm-12 text-center btn-space">
						<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />	
						<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
						<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
						<g:link class="btn btn-success" action="delete" id="${industryInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>							
					</div>			
				</g:form>
				
     		</div>
     	</div>
	</body>
</html>
