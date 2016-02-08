<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':supervisorInstanceListCount, 'searchAction': 'getSupervisorList', 'searchController': 'report', 'divIdToUpdate': 'supervisorListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.supervisor.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.supervisor.last.name.label" default="Last Name" /></th>
					
					<th><g:message code="etech.report.supervisor.first.name.label" default="First Name" /></th>
					
					<th><g:message code="etech.report.supervisor.pay.dept.label" default="Pay Dept" /></th>
					
				</tr>
			</thead>
			<g:if test="${supervisorInstanceList?.size() > 0}">
	    		<g:each in="${supervisorInstanceList}" status="i" var="supervisorInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setSupervisorCode('${supervisorInstance?.employee?.code}')" >${supervisorInstance?.employee?.code}</div></td>
							
						<td>${supervisorInstance?.employee?.lastName}</td>
						
						<td>${supervisorInstance?.employee?.firstName}</td>
						
						<td>${supervisorInstance?.employee?.payDept?.code}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="4" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getSupervisorList', 'searchController': 'report', 'divIdToUpdate': 'supervisorListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${supervisorInstanceListCount ?: 0}" maxsteps="5" />
				
	</div>