<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.address.label" default="Address" /></title>
		<r:require module="address" />
		<content tag="pageName">customerPage</content>	 	
	</head>
	<body>
		<div class="navigation">
			<g:set var="action" value="${message(code: 'default.header.title.add.label', default: 'Add')}" />
			<h3><g:message code="etech.address.maintenance.label" default="Address Maintenance" /></h3>
		</div>
		
		<div class="col-sm-12">
			<div class="white-bg">
		
			<div class="title text-left">
				<h2><g:if test="${params?.previousAction == 'newRecord' || addressInstance?.hasErrors() }"><g:message code="default.sub.title.create.label" default= "Create" /></g:if><g:else><g:message code="default.sub.title.search.label" default= "Search" /></g:else></h2>
			</div>
		
			<div id="create-address" class="content scaffold-create" role="main">
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				<g:hasErrors bean="${addressInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${addressInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</g:hasErrors>
				
					<g:form name="createForm" controller="address" method="post">
					
					<fieldset class="form">
						<g:render template="form"/>	
					</fieldset>
					
					<div class="clearfix"></div>
					
					<div class="col-sm-12 text-center btn-space">
						<g:actionSubmit name="create" action="save" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}" />
						<g:actionSubmit class="btn btn-success"  action="newRecord" name="newRecord" value="${message(code: 'default.new.link.label', default: 'New')}"/>
						<g:actionSubmit class="btn btn-success"  action="clear" name="clear" value="${message(code: 'default.button.clear.label', default: 'Clear')}"/>
						<g:actionSubmit class="btn btn-success"  action="list" name="search" value="${message(code: 'default.button.search.label', default: 'Search')}"/>
					</div>
        			
					<div class="col-xs-12">
						<g:render template="/common/returnLink" model="['returnAction': 'edit', 'returnController': 'customer', 'messageCode':'etech.customer.return.screen', 'defaultMessage': 'Return to Customer Screen', 'linkId': params.customer?.id, 'isDirtyCheck': 'isDirtyCheck']"></g:render>
					</div>
					
				</g:form>
			</div>
		</div>
	</div>
	</body>
</html>
