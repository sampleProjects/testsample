<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.coe.label" default="COE" /></title>
		<content tag="pageName">coePage</content>	
	</head>
	<body>		
		<div class="navigation">			
			<h3><g:message code="etech.coe.label" default="COE" /></h3>
		</div>		
		
     			<div class="white-bg">
       				 <div class="title text-left"><h2><g:if test="${params?.previousAction == 'newRecord' || COEInstance?.hasErrors()}"><g:message code="default.sub.title.create.label" default= "Create" /></g:if><g:else><g:message code="default.sub.title.search.label" default= "Search" /></g:else></h2></div>	
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<g:hasErrors bean="${COEInstance}">
						<ul class="errors" role="alert">
							<g:eachError bean="${COEInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
							</g:eachError>
						</ul>
					</g:hasErrors>
					<g:form method="post" name="createForm"  controller="COE" action="save" >					
						<fieldset class="form">
							<g:render template="form"/>
						</fieldset>
						<div class="clearfix"></div>
						<div class="col-sm-12 text-center btn-space">						
							<g:submitButton  name="create" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}"/> 		
							<g:link class="btn btn-success"  action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
							<g:link class="btn btn-success"  action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
							<input type="button" value="${message(code: 'default.button.search.label', default: 'Search')}" id="searchbtn" class="btn btn-success"/>					 
						</div>
					</g:form>
				</div>
		
	</body>
</html>
