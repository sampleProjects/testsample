<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':projectInstanceCount, 'searchAction': 'getParentProjectList', 'searchController': 'project', 'divIdToUpdate': 'parentProjectCodelistGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.project.code.label" default="Code" /></th>
	
					<th><g:message code="etech.project.Name.label" default="Name" /></th>
					
				</tr>
			</thead>
			<g:if test="${projectInstanceList?.size() > 0}">
	    		<g:each in="${projectInstanceList}" status="i" var="projectInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setParentProjectCode('${projectInstance?.id}','${projectInstance?.code}')" >${fieldValue(bean: projectInstance, field: "code")}</div></td>
							
						<td>${fieldValue(bean: projectInstance, field: "name")}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getParentProjectList', 'searchController': 'project', 'divIdToUpdate': 'parentProjectCodelistGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${projectInstanceCount ?: 0}" maxsteps="5" />
				
	</div>