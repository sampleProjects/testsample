
<%@ page import="org.solcorp.etech.ProjectCategory" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="projectcategory.search.label" default="Project Category Search"	/></title>
		<content tag="pageName">projectCategoryPage</content>
	</head>
	<body>
		<div class="navigation">
			<h3><g:message code="projectcategory.list.label" default="Project Category List" /></h3>
    	</div>
		
		<div id="list-projectCategory" class="col-sm-12" role="main">
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
				<div class="title">
					<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>
          		
          		<g:render template="/common/paginationCombo" model="['totalRecord':projectCategoryInstanceCount, 'searchAction': 'list', 'searchController': 'projectCategory', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>			
		</div>
	</div>
	</body>
</html>
