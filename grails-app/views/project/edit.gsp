<%@ page import="org.solcorp.etech.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.project.label" default="Project"/></title>
		<r:require module="project"/>	
		<content tag="pageName">projectPage</content>	
	</head>
	<body>
		<g:form url="[resource:projectInstance, action:'update']" method="PUT" >
			<div class="navigation">			
				<h3 class="pull-left">
					<span><g:message code="etech.project.label" default="Project" /> (Code: ${projectInstance.code})</span>
				</h3>
				
				<div class="pull-right heading-btns">
					<ul>
						<li><g:actionSubmit action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></li>
						<li><g:link action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link></li>
						<li><g:link action="clear"><g:message code="default.clear.label" default="Clear" /></g:link></li>
						<li><g:link action="delete" id="${projectInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link></li>
					</ul>					 
				</div>
			</div>		
		
			<div class="white-bg project-detail">
       			<div class="title text-left"><h2><g:message code="default.sub.title.edit.label" default= "Edit" /></h2></div>   
       				 
	       		<g:if test="${projectInstance.isSystemJobUser()}"> 
					<div class="col-sm-12"><div class="alert alert-danger"><g:message code="etech.project.project.imported.job.message" default="This record has recently been modified by the scheduled job. Please set appropriate values for Project Type, Bill Code Type, Status, Acc Executive if required."/></div></div>
				</g:if>
							
				<div class="clearfix"></div>
							
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
				
				<g:hasErrors bean="${projectInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${projectInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</g:hasErrors>
							
				<g:set value="true" var="editMode"></g:set>
				<g:hiddenField name="version" value="${projectInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
					
				<div class="clearfix"></div>					 
				<div class="col-sm-12 text-center btn-space">
					<g:actionSubmit class="btn btn-success" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" />	
					<g:link class="btn btn-success" action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
					<g:link class="btn btn-success" action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
					<g:link class="btn btn-success" action="delete" id="${projectInstance?.id}" onclick="return confirmDelete()"><g:message code="default.delete.label" default="Delete" /></g:link>							
				</div>			
			</div>
		</g:form>
		<div id="productCostingPopupDiv">
			<g:render template="productCostingPopup"/>
			<g:render template="hhProjectMasterDetailsDialog"/>
			<g:render template="customerCodeDialog" />
			<g:render template="parentProjectCodeDialog" />
			<g:render template="projectManagerDialog" />
			<g:render template="../common/searchDialog/acctExecutiveSearchDialog" model="['isProjectManagerEnabled': 'true']"></g:render>
		</div>			
	</body>
</html>
