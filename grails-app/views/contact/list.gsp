<%@ page import="org.solcorp.etech.Contact" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.contact.search.label" default="Contact Search"	/></title>
		<content tag="pageName">customerPage</content>
	</head>
	<body>
		
		<div class="navigation">
      		<h3><g:message code="etech.contact.label" default="Contact" /></h3>
    	</div>
		
		<div id="list-contact" class="col-sm-12" role="main">
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
				<div class="title">
            		<h2><g:message code="default.sub.title.list.label" default="List" /></h2>
          		</div>
          		
          		<g:render template="/common/paginationCombo" model="['totalRecord':contactInstanceCount, 'searchAction': 'list', 'searchController': 'contact', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
		</div>
	</div>
	</body>
</html>
