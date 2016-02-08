
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.project.label" default="Project"/></title>
		<r:require module="project"/>
		<content tag="pageName">projectPage</content>
	</head>
	<body>
		<g:form method="post" name="createForm"  controller="project" action="save" >
		<div class="navigation">			
			<h3 class="pull-left"><g:message code="etech.project.label" default="Project" /></h3>
			<div class="pull-right heading-btns">
				<ul>
					<li><g:submitButton  name="create" value="${message(code: 'default.button.save.label', default: 'Save')}"/></li>
					<li><g:link action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link></li>
					<li><g:link action="clear"><g:message code="default.clear.label" default="Clear" /></g:link></li>
					<li><input type="button" value="${message(code: 'default.button.search.label', default: 'Search')}" id="searchbtn"/></li>
				</ul>					 
			</div>
		</div>		
		
		<div class="white-bg project-detail">
			
			<div class="title text-left"><h2><g:if test="${params?.previousAction == 'newRecord' || projectInstance?.hasErrors() }"><g:message code="default.sub.title.create.label" default= "Create" /></g:if><g:else><g:message code="default.sub.title.search.label" default= "Search" /></g:else></h2></div>	
			
				<g:set var="i" value="0"></g:set>
			
				<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
				</g:if>
			
				<g:hasErrors bean="${projectInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${projectInstance}" var="error">					
							<script>
								if(${error.field == 'parentProjectTxt'}) {
									parentProjectError = true;				
								}
								if(${error.field == 'customerTxt'}) {
									customerError = true;				
								}
								if(${error.field == 'accExecutiveTxt'}) {
									accExecutiveError = true;				
								}				
							</script>						
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</g:hasErrors>
			
			
			
					<fieldset class="form">
						<g:render template="form"/>
					</fieldset>
					
					<div class="clearfix"></div>
					
					<div class="col-sm-12 text-center btn-space">						
						<g:submitButton  name="create" class="btn btn-success" value="${message(code: 'default.button.save.label', default: 'Save')}"/> 		
						<g:link class="btn btn-success"  action="newRecord"><g:message code="default.new.link.label" default="New" /></g:link>
						<g:link class="btn btn-success"  action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
						<input type="button" value="${message(code: 'default.button.search.label', default: 'Search')}" id="searchbtn" class="searchbutton btn btn-success"/>					 
					</div>										
			</div>
			</g:form>
			<g:render template="hhProjectImportDialog"/>
			<g:render template="customerCodeDialog" />
			<g:render template="parentProjectCodeDialog" />
			<g:render template="projectManagerDialog" />
			<g:render template="../common/searchDialog/acctExecutiveSearchDialog"  model="['isProjectManagerEnabled': 'true']"></g:render>					 
	</body>
</html>
