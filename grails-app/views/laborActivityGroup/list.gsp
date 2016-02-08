
<%@ page import="org.solcorp.etech.LaborActivityGroup" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.laborActivitygroup.search.label" default="Labor Department Search"	/></title>
		<content tag="pageName">laborActivityGroupPage</content>
	</head>
	<body>
		<div class="navigation">
			<h3><g:message code="laborActivitygroup.list.label" default="Labor Department" /></h3>
    	</div>
		
		<div id="list-laborActivityGroup" class="col-sm-12" role="main">
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
				<div class="title">
					<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>
          		
          		<g:render template="/common/paginationCombo" model="['totalRecord':laborActivityGroupInstanceCount, 'searchAction': 'list', 'searchController': 'laborActivityGroup', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
		</div>
	</div>
	</body>
</html>
