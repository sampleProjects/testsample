<%@ page import="org.solcorp.etech.Employee" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.employee.search.label" default="Employee Search"	/></title>
		<content tag="pageName">employeePage</content>
	</head>
	<body>
		<div class="navigation">
      		<h3><g:message code="etech.employee.emp.maintenance.label" default="Employee Maintenance"/></h3>
    	</div>
		<div id="list-employee" class="col-sm-12" role="main">			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="white-bg">
          		<div class="title">
            		<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>      
          		    		
          		<g:render template="/common/paginationCombo" model="['totalRecord':employeeInstanceCount, 'searchAction': 'list', 'searchController': 'employee', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
		</div>
	</div>
	</body>
</html>
