<%@ page import="org.solcorp.etech.Constants"%>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.coe.labor.history.report.title.label" default="Labor History Report By COE" /></title>
		<r:require module="report"/>
		<content tag="pageName">laborHistoryByCOEPage</content>		
	</head>
	<body>		
		
	<div class="navigation">			
		<h3><g:message code="etech.report.coe.labor.history.report.title.label" default="Labor History Report By COE" /></h3>			
	</div>				
    <div class="white-bg">		 	
					
		<g:form method="post" name="reportForm" controller="report" action="getlaborHistoryByCOEList" >					
			<fieldset class="form">
				<g:render template="coeReportSearchPage"></g:render>
			</fieldset>
			<div class="clearfix"></div>				
			<div class="col-sm-12 text-center btn-space">						
				<g:submitButton  name="create" class="btn btn-success" value="${message(code: 'etech.report.generate.report.label', default: 'Generate Report')}"/>						
				<g:link class="btn btn-success"  action="laborHistoryByCOEReport"><g:message code="default.clear.label" default="Clear" /></g:link>
			</div>
		</g:form>	
			 
		<g:render template="../common/searchDialog/coeSearchDialog"></g:render>	 
		<g:render template="../common/searchDialog/projectSearchDialog"></g:render>
		<g:render template="../common/searchDialog/customerSearchDialog"></g:render>
		<g:render template="../common/searchDialog/acctExecutiveSearchDialog"></g:render>
		<g:render template="../common/searchDialog/projectManagerSearchDialog"></g:render>
		<g:render template="../common/searchDialog/supervisorSearchDialog"></g:render>
		<g:render template="../common/searchDialog/employeeSearchDialog"></g:render>
		<g:render template="../common/searchDialog/laborActivityCodeSearchDialog"></g:render>
		<g:render template="../common/searchDialog/payDepartmentSearchDialog"></g:render>
		<g:render template="../common/searchDialog/laborActivityGroupSearchDialog"></g:render>
	</div>		
	</body>
</html>
