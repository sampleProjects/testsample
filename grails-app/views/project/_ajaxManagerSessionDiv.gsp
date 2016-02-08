 <%@ page import="org.solcorp.etech.RoleType"%>
 <g:set value="${projectInstance?.id?.toString()?:params?.projectId}" var="projectIdStr"></g:set>
 <g:set value="${session[projectIdStr?: 'projectManagerList']}" var="projectEmployeeList"></g:set> 
 
 <g:if test="${projectEmployeeList != null && projectEmployeeList?.size() > 0}">	
	
	<g:each in="${projectEmployeeList}" status="i" var="projectEmployeeInstance">
		<span class="project-names">
		<g:if test="${projectEmployeeInstance?.id}">
			<g:hiddenField name="projectEmployees[${i}].id" value="${projectEmployeeInstance?.id}"/>
		</g:if>
		
		<g:hiddenField name="projectEmployees[${i}].employee.id" value="${projectEmployeeInstance?.employee?.id}"/>
		<span>${projectEmployeeInstance?.employee?.getEmployeeName()}</span>		
		<span class="marginleft5" onClick="removeProjectManager('${projectEmployeeInstance?.employee?.id}','${projectIdStr}')"><i class="fa fa-remove cursor-pointer"></i></span>		
		</br>
	 </span>
	</g:each>

</g:if>
