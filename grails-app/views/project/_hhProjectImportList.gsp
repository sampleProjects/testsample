<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':projectMasterCount, 'searchAction': 'getHHProjectMasterList', 'searchController': 'project', 'divIdToUpdate': 'projectImportListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
		
			<thead>
		
				<tr>
	
					<th><g:message code="etech.project.code.label" default="Code" /></th>
	
					<th><g:message code="etech.project.name.label" default="Name" /></th>
					
					<th><g:message code="etech.project.customer.code.label" default="Customer Code" /></th>
				</tr>
		
			</thead>
		
			<g:if test="${projectMasterList?.size() > 0}">
			
	    		<g:each in="${projectMasterList}" status="i" var="projectMstInstance">
	    			
	    			<g:if test="${projectList.contains(projectMstInstance?.projectId)}" >
	    			
		    			<tr class="selected-row">
		    			
		    				<td><div>${fieldValue(bean: projectMstInstance, field: "projectId")}</div></td>
						
							<td><div>${fieldValue(bean: projectMstInstance, field: "descr")}</div></td>
							
							<td><div>${fieldValue(bean: projectMstInstance, field: "hhCustId")}</div></td>
						</tr>
						
	    			</g:if>
	    			
	    			<g:else>
	    			
					<tr >			
						<g:hiddenField name="desc_${i}" id="desc_${i}" value="${projectMstInstance?.descr}"/>							
							
						<td><div style="cursor: pointer;" onclick="setHhProjectDetails('${projectMstInstance?.projectId}','${i}','${projectMstInstance?.hhCustId}','${projectMstInstance?.getStartDate()}','${projectMstInstance?.getEndDate()}','${projectMstInstance?.businessUnit}')" >${fieldValue(bean: projectMstInstance, field: "projectId")}</div></td>
					
						<td><div style="cursor: pointer;" onclick="setHhProjectDetails('${projectMstInstance?.projectId}','${i}','${projectMstInstance?.hhCustId}','${projectMstInstance?.getStartDate()}','${projectMstInstance?.getEndDate()}','${projectMstInstance?.businessUnit}')" >${fieldValue(bean: projectMstInstance, field: "descr")}</div></td>
						
						<td><div style="cursor: pointer;" onclick="setHhProjectDetails('${projectMstInstance?.projectId}','${i}','${projectMstInstance?.hhCustId}','${projectMstInstance?.getStartDate()}','${projectMstInstance?.getEndDate()}','${projectMstInstance?.businessUnit}')" >${fieldValue(bean: projectMstInstance, field: "hhCustId")}</div></td>
					
					</tr>
						
					</g:else>
								
				</g:each>
				
			</g:if>
			
			<g:else>
	
				<tr><td colspan="2" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>
	
<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getHHProjectMasterList', 'searchController': 'project', 'divIdToUpdate': 'projectImportListGridDiv', 'searchParams': 'true']" />			
	
<div class="pagination margintopbottom15">
			
	<g:paginate total="${projectMasterCount ?: 0}" maxsteps="5" />
				
</div>
<script>
$("#customerExistMessageDiv").hide();
</script>