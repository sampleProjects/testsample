<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.by.emp.activity.title.label" default="Project by Employee/Activity Report Selection"/></title>
		<r:require module="report"/>
		<content tag="pageName">employeeByActivityPage</content>
	</head>
	<body>		
		<div class="navigation">			
			<h3><g:message code="etech.report.project.by.emp.activity.title.label" default="Project by Employee/Activity Report Selection" /></h3>			
		</div>		
		
     			<div class="white-bg">
					<g:form method="post" name="reportForm"  controller="report" action="getEmployeeByActivityReportData" >					
						<fieldset class="form">
							<g:render template="reportSearchPage"></g:render>
						</fieldset>
						<div class="clearfix"></div>
						<div class="col-sm-12 text-center btn-space">						
							<g:submitButton  name="create" class="btn btn-success" value="${message(code: 'etech.report.generate.report.label', default: 'Generate Report')}"/>						
							<g:link class="btn btn-success"  action="clear"><g:message code="default.clear.label" default="Clear" /></g:link>
						</div>
					</g:form>
					<g:render template="includeDialogTemplate"></g:render>
				</div>		
	</body>
</html>
