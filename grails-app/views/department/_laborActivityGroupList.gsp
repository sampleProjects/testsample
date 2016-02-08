<div class="clearfix"></div><hr>

<g:render template="/common/paginationCombo" model="['totalRecord':laborActGroupListCount, 'searchAction': 'getLaborActGroupList', 'searchController': 'department', 'divIdToUpdate': 'laborActivityGroupListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
					<th>Labor Department</th>
					
					<th><g:message code="etech.department.description.label" default="Description"/> </th>
				</tr>
			</thead>
			<g:if test="${laborActGroupInstanceList?.size() > 0}">
	    		<g:each in="${laborActGroupInstanceList}" status="i" var="deptLbrActGroupInstance">
					<tr>			
						
						<td><div style="cursor: pointer;" onclick="setLaborActGroup('${deptLbrActGroupInstance?.id}','${deptLbrActGroupInstance?.code}')" >${fieldValue(bean: deptLbrActGroupInstance, field: "code")}</div></td>
							
						<td><div>${fieldValue(bean: deptLbrActGroupInstance, field: "description")}</div></td>
					</tr>
				</g:each>
			</g:if>
			<g:else>
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
			</g:else>
		</table>
</div>
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getLaborActGroupList', 'searchController': 'department', 'divIdToUpdate': 'laborActivityGroupListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
		<g:paginate total="${laborActGroupListCount ?: 0}" maxsteps="5" />
	</div>