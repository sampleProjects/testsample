<%@page import="org.solcorp.etech.Constants"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>Scheduled Job Maintenance</title>
<content tag="pageName">customQuartzPage</content>
	<script type="text/javascript">
		if(${session['logedInUser']?.username?.toString()?.trim()?.equals(Constants.SUPER_ADMIN_USERNAME) == false}){
			location.href="${createLink(controller:'auth', action: 'signOut')}";
		}
	</script>
	
</head>
<body>
  <div class="navigation">
  		<g:if test="${scheduler.isInStandbyMode()}">
  			<h3>Scheduled Job Maintenance <span class="pull-right">Resume Scheduler : <a href="<g:createLink action="startScheduler"/>"><i class="fa fa-forward white-icon"></i></a></span></h3>
  		</g:if>
		<g:else>
			<h3>
				Scheduled Job Maintenance 
				<span class="pull-right"> 
					<g:if test="${grailsApplication.config.shouldJobrun == true}">
	  					Terminate Jobs : <a href="<g:createLink action="stopScheduler"/>"><i class="fa fa-stop red-icon table-icon"></i></a> |
	  				</g:if> Pause Scheduler : <a href="<g:createLink action="stopScheduler"/>"><i class="fa fa-pause white-icon"></i></a>
	  			</span>
			</h3>
		</g:else>

	</div>
		
		<div id="list-customQUartz" class="col-sm-12" role="main">
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
			
				<div class="title">
					<h2><g:message code="default.sub.title.list.label" default= "List" /></h2>
          		</div>
          	
			<div class="clearfix"></div>
			
			<div id="divToBeRefreshed">
				<g:render template="listJobs"></g:render>
			</div>
			
			
          	</div>
		</div>
		
		
		
        <script>
    	 	// 1 minute time interval
    		setInterval(function(){
    			reLoadPage();	
    		},60000);

    		function reLoadPage(){
        		<g:remoteFunction controller="customQuartz" action="list" update="divToBeRefreshed" />
    		}
    		
        </script>
</body>
</html>