<%@ page import="org.solcorp.etech.Contact" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.contact.label" default="Contact" /></title>	
		 <r:require module="contact"/>
		 <content tag="pageName">customerPage</content>	
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'default.header.title.edit.label', default: 'Edit')}" />
			<h3><g:message code="etech.contact.maintenance.label" default="Contact Maintenance"/></h3>
		</div>
		
		<div class="col-sm-12">
			<div class="white-bg">
		
				<div class="title">
					<h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2>
				</div>
				
				<div id="edit-contact" class="content scaffold-edit" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<g:hasErrors bean="${contactInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${contactInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
					</g:hasErrors>
					
					<g:form url="[resource:contactInstance, action:'update']" method="PUT" >
						<g:hiddenField name="version" value="${contactInstance?.version}" />
						<fieldset class="form">
							<g:render template="form"/>
						</fieldset>
						
						<div class="clearfix">

						<div class="col-sm-12 text-center btn-space">
							<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />
							<g:actionSubmit class="btn btn-success"  action="newRecord" name="newRecord" value="${message(code: 'default.new.link.label', default: 'New')}"/>
							<g:actionSubmit class="btn btn-success"  action="clear" name="clear" value="${message(code: 'default.button.clear.label', default: 'Clear')}"/>
							<g:link class="btn btn-success" action="delete" id="${contactInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>
						</div>
        				
						</div>
						
						<div class="col-xs-12">
							<g:render template="/common/returnLink" model="['returnAction': 'edit', 'returnController': 'customer', 'messageCode':'etech.customer.return.screen', 'defaultMessage': 'Return to Customer Screen', 'linkId': contactInstance?.customer?.id, 'isDirtyCheck': 'isDirtyCheck']"></g:render>
						</div>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
