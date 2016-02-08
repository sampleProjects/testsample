
<%@ page import="org.solcorp.etech.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.selection.screen.label" default="Project Selection Screen"	/></title>
		<content tag="pageName">projectPerformanceReportPage</content>
	</head>
	<body>
		<div class="navigation">
      		<h3><g:message code="etech.report.project.selection.screen.label" default="Project Selection Screen"/></h3>
    	</div>
		<div id="list-project" class="content scaffold-list" role="main">			 
		
			<div class="white-bg">
          		<div class="title">
            		<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>          		
          		
          		<g:render template="/common/paginationCombo" model="['totalRecord':projectInstanceCount, 'searchAction': 'getAllProjectList', 'searchController': 'report', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="projectListRecords"></g:render>
				</div>			
		</div>
	</div>
	 
	</body>
</html>
