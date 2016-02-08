<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.projectLabor.label" default="Project Labor" /></title>
		<r:require module="projectLabor"/>
		<content tag="pageName">projectPage</content>
	</head>
	<body>
		<div class="navigation">
			<h3>Project Labor for ${projectServiceDetailInstance?.service?.product?.code} / ${projectServiceDetailInstance?.service?.code}</h3>
		</div>
		
		<div class="col-sm-12">
			<div class="white-bg project-detail">
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					<br>&nbsp;
				</g:if>
				<g:hasErrors bean="${projectLaborInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${projectLaborInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</g:hasErrors>
				
				<g:form method="post" name="createForm"  controller="projectLabor" style="padding-top: 20px;">
					<fieldset class="form">
						<g:render template="form"/>
					</fieldset>
				
					<div class="clearfix"></div>
				
        			<div class="col-sm-12 text-center btn-space">
        				<g:actionSubmit action="save"  name="create" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}" />
        				<g:actionSubmit action="create"  name="clear" class="btn btn-success" value="${message(code: 'default.clear.label', default: 'Clear')}" />
        			</div>
				</g:form>
				<g:render template="laborActivityCodeDialog"/>
			</div>
		</div>
	</body>
</html>