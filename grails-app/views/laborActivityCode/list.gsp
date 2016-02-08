<%@ page import="org.solcorp.etech.LaborActivityCode" %>

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="laboractivitycode.search.label" default="Labor Activity Code Search"	/></title>
		<content tag="pageName">laborActivityCodePage</content>
	</head>
	<body>
		<div class="navigation">
			<h3><g:message code="laboractivitycode.list.label" default="Labor Activity Code" /></h3>
    	</div>
		
		<div id="list-laborActivityCode" class="col-sm-12" role="main">
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
			
				<div class="title">
					<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>
          		
          		<g:render template="/common/paginationCombo" model="['totalRecord':laborActivityCodeInstanceCount, 'searchAction': 'list', 'searchController': 'laborActivityCode', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>
			
          	</div>
		</div>
	</body>
</html>
