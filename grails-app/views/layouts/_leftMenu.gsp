<%@page import="org.solcorp.etech.Constants"%>
<%@ page import="org.solcorp.etech.ProductClassType" %>
<%@ page import="org.solcorp.etech.PermissionType" %>
<%@ page import="org.solcorp.etech.RoleType" %>

<div class="container main-container">
<div id="wrapper" class="<g:if test="${cookie(name: 'leftPanelStatus')}">''</g:if><g:else>toggled</g:else>">
<div id="sidebar-wrapper">
	
    <div class="col-xs-12 logo-container">
       <h2 class="sidebar-title"><g:link controller="auth" action="dashboard" class="sidebar-title-link">Harte Hanks Project Costing System</g:link></h2>		
    </div>
    <ul class="topnav" id="topnav">    	
      	
      	<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_PROJECT.name()}">
	      	<li><a href="#"><i class="fa fa-fw"></i>Project Maintenance</a>
		        <ul>
		        	<li class="projectPage"><g:link controller="project" action="create">Projects</g:link></li>
		        </ul>
	      	</li>
		</shiro:hasPermission>
		
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_PROJECT_REPORTS.name()}">
			<li> <a href="#"><i class="fa fa-fw"></i>Project Detail Reports</a>
			 	<ul>
		        	<li class="activityByEmployeePage" ><g:link controller="report" action="activityByEmployeeReport">Activity by Employee</g:link></li>
		        	<li class="employeeByActivityPage" ><g:link controller="report" action="employeeByActivityReport">Employee by Activity</g:link></li>
		        	<li class="projectExpensesByExpenseCode" ><g:link controller="report" action="projectExpensesByExpenseCode">Expense by Expense Code</g:link></li>
		        </ul>	        
	      	</li>   
		 </shiro:hasPermission>
		
		<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_EMPLOYEE_REPORTS.name()}">
			<li> <a href="#"><i class="fa fa-fw"></i>Employee Reports</a>
				<ul>
					<li class="employeeByProjectByActivityPage" ><g:link controller="report" action="employeeByProjectByActivityReport">Project by Activity</g:link></li>
					<li class="employeeByActivityByProjectPage" ><g:link controller="report" action="employeeByActivityByProjectReport">Activity by Project</g:link></li>
				</ul>	       
	      	</li>   
		</shiro:hasPermission>
		
		 <shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_PROJECT_REPORTS.name()}">
			<li> <a href="#"><i class="fa fa-fw"></i>Performance Reports View</a>
		        <ul>	        	
		        	<li class="projectPerformanceReportPage"><g:link controller="report" action="projectPerformanceReport">Project Performance View</g:link></li>
		        </ul>
	      	</li>   
      	</shiro:hasPermission>
      	
      	<shiro:hasPermission permission="${PermissionType.PERMISSION_CAN_VIEW_COE_REPORTS.name()}">
	      	<li> <a href="#"><i class="fa fa-fw"></i>COE Reports</a>
		        <ul>	        	
		        	<li class="laborHistoryByCOEPage"><g:link controller="report" action="laborHistoryByCOEReport">Labor History by COE</g:link></li>
		        </ul>
	      	</li>      
      	</shiro:hasPermission>
      	
      	<shiro:hasAnyRole in="${RoleType.adminRoleList()*.name()}">
	      	<li><a href="#"><i class="fa fa-fw"></i>Reference Tables</a>
		        <ul>
		        	<li class="coePage"><g:link controller="COE" action="create">COE</g:link></li>
		          	<li class="laborActivityGroupPage"><g:link controller="laborActivityGroup" action="create">Labor Department</g:link></li>
		          	<li class="laborActivityCodePage"><g:link controller="laborActivityCode" action="create">Labor Activity Code</g:link></li>
		          	<g:set value="${params.productClassType ?: productInstance?.productClassType?.name() }" var="classType"></g:set>
		          	<g:set value="${params.productClassType ?: productCategoryInstance?.productClassType?.name() }" var="categoryClassType"></g:set>
		          	
		          	<g:if test="${controllerName=='productCategory'}">	          	
		          		<g:set value="${categoryClassType}_Category" var="productCategoryClassType"></g:set>
		          	</g:if>     	
		          	
		          	<li class="${classType=='PROJECT' ? classType  : ''}"><g:link controller="product" action="create" params="['productClassType': 'PROJECT']">Project Products</g:link></li>
		          	<li class="${classType=='SERVICES'  ? classType : ''}"><g:link controller="product" action="create" params="['productClassType': 'SERVICES']">Services</g:link></li>
					<li class="${classType=='EXPENSES'  ? classType : ''}"><g:link controller="product" action="create" params="['productClassType': 'EXPENSES']">Expenses</g:link></li>
					<li class="${classType=='MISCELLANEOUS'  ? classType : ''}"><g:link controller="product" action="create" params="['productClassType': 'MISCELLANEOUS']">Miscellaneous</g:link></li>
					
					<li class="projectCategoryPage"><g:link controller="projectCategory" action="create">Project Category</g:link></li>
					
					<li class="${productCategoryClassType=='PROJECT_Category' ? productCategoryClassType  : ''}"><g:link controller="productCategory" action="create" params="['productClassType': 'PROJECT']">Project Products Category</g:link></li>
					<li class="${productCategoryClassType=='SERVICES_Category'  ? productCategoryClassType : ''}"><g:link controller="productCategory" action="create" params="['productClassType': 'SERVICES']">Services Category</g:link></li>
					<li class="${productCategoryClassType=='EXPENSES_Category'  ? productCategoryClassType : ''}"><g:link controller="productCategory" action="create" params="['productClassType': 'EXPENSES']">Expenses Category</g:link></li>
					<li class="${productCategoryClassType=='MISCELLANEOUS_Category'  ? productCategoryClassType : ''}"><g:link controller="productCategory" action="create" params="['productClassType': 'MISCELLANEOUS']">Miscellaneous Category</g:link></li>
		       		<li class="industryPage"><g:link controller="industry" action="create">Industry</g:link></li>
		       	</ul>
		    </li>
	      	
		    <li><a href="#"><i class="fa fa-fw"></i>Admin</a>
			     <ul>
			       	<li class="${params.productType}"><g:link controller="product" action="create" params="['productType': 'all']">Products</g:link></li>
			       	<li class="customerPage"> <g:link controller="customer" action="create">Customer</g:link></li>
			       	<li class="employeePage"><g:link controller="employee" action="create">Employee</g:link></li>
			       	<li class="userPage"><g:link controller="user" action="create">User</g:link></li>
			       	<li class="${params.productType}_Category"><g:link controller="productCategory" action="create" params="['productType': 'all']">Products Category</g:link></li>
			       	<li class="departmentPage" ><g:link controller="department" action="create">Pay Department</g:link></li>
			       	
			       	<shiro:hasRole name="${RoleType.ROLE_SUPER_ADMIN.name()}">
						<li class="roleMaintenance"><g:link controller="roleMaintenance" action="list">Role Maintenance</g:link></li>
						<li class="optionsPage" ><g:link controller="options" action="edit">Company Control</g:link></li>				
						<li class="customQuartzPage" ><g:link controller="customQuartz" action="index">Scheduled Job Maintenance</g:link></li>
					</shiro:hasRole>
			     </ul>
			</li>
		</shiro:hasAnyRole>		
    </ul>
  </div>