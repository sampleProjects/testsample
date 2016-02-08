<%@ page import="org.solcorp.etech.Department" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.department.search.label" default="Department Search"	/></title>
		<content tag="pageName">departmentPage</content>
	</head>
	<body>
		<div class="navigation">
      		<h3><g:message code="org.solcorp.etech.Department.paydepartment.code" default="Pay Department" /></h3>
    	</div>
			
		<div id="list-department" class="col-sm-12" role="main">			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="white-bg">
          		<div class="title">
            		<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>      
          		    		
          		<g:render template="/common/paginationCombo" model="['totalRecord':departmentInstanceCount, 'searchAction': 'list', 'searchController': 'department', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
		</div>
	</div>	
		
		
	</body>
</html>
