<%@ page import="org.solcorp.etech.COE"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Active Users</title>
</head>
<body>
	<div class="navigation">
		<h3>
			Active Users
		</h3>
	</div>
	<div id="list-employee" class="col-sm-12" role="main">
		<div class="white-bg">
			<div class="title">
				<h2>
					<g:message code="default.sub.title.list.label" default="List" />
				</h2>
			</div>
			<g:render template="/common/paginationCombo" model="['totalRecord':userSessionListCount, 'searchAction': 'activeUsersList', 'searchController': 'userSession', 'divIdToUpdate': 'listRecordsDiv']"></g:render>          		
                      
          		<div id="listRecordsDiv">
          			<g:render template="activeUsersListRecords"></g:render>
				</div>			
		</div>
	</div>

</body>
</html>
