<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.emp.by.act.by.prj.title.label" default="Employee by Activity/Project Report Selection"/></title>
		<r:require module="report"/>
		<content tag="pageName">employeeByActivityByProjectPage</content>
	</head>
	<body>		
		<div class="navigation">			
			<h3><g:message code="etech.report.emp.by.act.by.prj.title.label" default="Employee by Activity/Project Report Selection" /></h3>			
		</div>		
		
     			<div class="white-bg">
					<g:form method="post" name="reportForm"  controller="report" action="getAllEmployeeByActivityByProjectReportData" >					
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
