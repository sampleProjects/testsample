<%@page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.Options" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.options.company.control.label" default="Company Control"/></title>
		<script type="text/javascript">
			if(${session['logedInUser']?.username?.toString()?.trim()?.equals(Constants.SUPER_ADMIN_USERNAME) == false}){
				location.href="${createLink(controller:'auth', action: 'signOut')}";
			}
		</script>
		<r:require module="options"/>
		<content tag="pageName">optionsPage</content>
	</head>
	<body>
		
		<div class="navigation">			
			<h3><g:message code="etech.options.company.control.label" default="Company Control"/></h3>
		</div>
		
		<div class="col-sm-12">
      		<div class="white-bg">
			
			<div class="title">
				<h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2>
			</div>
		
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<g:hasErrors bean="${optionsInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${optionsInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			
			<g:form url="[resource:optionsInstance, action:'update']" method="PUT" name="company">
				<g:hiddenField name="version" value="${optionsInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				
				<div class="clearfix">
					
					<div class="col-sm-12 text-center btn-space">
						<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" onClick="return validateForm()"/>
					</div>
				</div>
				
			</g:form>
			
		</div>
		</div>
	</body>
</html>
