<%@page import="org.solcorp.etech.CustomerController"%>
<%@ page import="org.solcorp.etech.Customer" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Expense List</title>
		<content tag="pageName">projectPage</content>
	</head>
	<body>
		
		<div class="navigation">
      		<h3>Expense List</h3>
    	</div>
    	
		<div id="list-Customer" class="col-sm-12" role="main">
		
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
				<div class="title">
            		<h2><g:message code="etech.hh.expense.list.title" default="Expense List" /></h2>
          		</div>
          		
          		<g:render template="/common/paginationCombo" model="['totalRecord':hhExpenseMasterInstanceCount, 'searchAction': 'getHHExpenseMasterListByProjectCode', 'searchController': 'project', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="hhExpenseMasterListRecords"></g:render>
				</div>			
		</div>
	</div>
	</body>
</html>
