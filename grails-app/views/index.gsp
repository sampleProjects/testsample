<%@ page import="org.solcorp.etech.RoleType" %>
<!DOCTYPE html>
<html>
	<head>		
		<title>eTech My Portal</title>		
		<meta name="layout" content="main"/>		 
	</head>
	<body>
		<div class="navigation">
	      <h3>Dashboard</h3>
	    </div>	     
	    <shiro:hasAnyRole in="${RoleType.adminRoleList()*.name()}">
	       <div style="float:right; margin-right:40px">
		       <label>Active Sessions</label>: <g:if test="${activeSessionCount > 0}"><g:link controller="UserSession" action="activeSessionsList">${activeSessionCount}</g:link></g:if><g:else>0</g:else> 
		        &nbsp;|&nbsp; <label>Active Users</label>: <g:if test="${activeUserCount > 0}"><g:link controller="UserSession" action="activeUsersList">${activeUserCount}</g:link></g:if><g:else>0</g:else>
	       </div>
		 </shiro:hasAnyRole>
		 <div class="col-sm-12">
		      <div class="col-sm-6">
		        <div class="white-bg">
		          <div class="title">
		            <h2>Messages</h2>
		          </div>
		        </div>
	      	</div>
	      	<div class="col-sm-6">
	        	<div class="white-bg">
	          		<div class="title">
		        	  <h2>My Task</h2>
	          	</div>       
	        </div>
	      </div>
    	</div>	 
	</body>
</html>
