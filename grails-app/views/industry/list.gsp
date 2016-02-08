<%@ page import="org.solcorp.etech.Industry" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="etech.industry.label" default="Industry" /></title>
		<content tag="pageName">industryPage</content>
	</head>
	<body>
	
		<div class="navigation">			
			<h3><g:message code="etech.industry.label" default="Industry" /></h3>
		</div>
	
	
		<div id="list-employee" class="col-sm-12" role="main">
			<g:if test="${flash.message}">
				<div class="message" role="status">
					${flash.message}
				</div>
			</g:if>
			
			<div class="white-bg">
				<div class="title">
					<h2>
						<g:message code="default.sub.title.list.label" default="List" />
					</h2>
				</div>
				<g:render template="/common/paginationCombo" model="['totalRecord':industryInstanceCount, 'searchAction': 'list', 'searchController': 'industry', 'divIdToUpdate': 'listRecordsDiv']"></g:render>          		
                      
          		<div id="listRecordsDiv">
          			<g:render template="listRecords"></g:render>
				</div>
						
			</div>
		</div>
	</body>
</html>
