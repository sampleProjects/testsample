
<%@ page import="org.solcorp.etech.Address" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.address.search.label" default="Address Search"	/></title>
		<content tag="pageName">customerPage</content>
	</head>
	<body>
		
		<div class="navigation">
      		<h3><g:message code="etech.address.label" default="Address" /></h3>
    	</div>
    	
		<div id="list-address" class="col-sm-12" role="main">
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
				<div class="title">
            		<h2><g:message code="default.sub.title.list.label" default="List" /></h2>
          		</div>
          		          		
                 <g:render template="/common/paginationCombo" model="['totalRecord':addressInstanceCount, 'searchAction': 'list', 'searchController': 'address', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
			</div>
		</div>	 
	</body>
</html>
