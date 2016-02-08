
<%@ page import="org.solcorp.etech.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.project.search.label" default="Project Search"	/></title>
		<content tag="pageName">projectPage</content>
	</head>
	<body>
		<div class="navigation">
      		<h3><g:message code="etech.project.label" default="Project"/></h3>
    	</div>
		<div id="list-project" class="content scaffold-list" role="main">
			 
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="white-bg">
          		<div class="title">
            		<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>          		
          		
          		<g:render template="/common/paginationCombo" model="['totalRecord':projectInstanceCount, 'searchAction': 'list', 'searchController': 'project', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
		</div>
	</div>
	 
	</body>
</html>
