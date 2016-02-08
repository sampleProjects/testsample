<div class="clearfix"></div><hr>

<g:render template="/common/paginationCombo" model="['totalRecord':laborActGrpListCount, 'searchAction': 'getLaborActGrpList', 'searchController': 'employee', 'divIdToUpdate': 'laborListGridDiv']"></g:render>

<div id="ajaxListView" class="table-responsive height-scroll">	

  	<table class="table table-striped">	
		<thead>
			<tr>
				<th><g:message code="etech.employee.labor.group.label" default="Department" /></th>
				<th><g:message code="etech.employee.labor.description.label" default="Description" /></th>
			</tr>
		</thead>
	
		<g:if test="${laborActGrpList?.size() > 0}">
	
			<g:each in="${laborActGrpList}" status="i" var="laborActivityGroupInstance">	
	
				<tr>
						
					<td><div style="cursor: pointer;" onclick="setClickVal('${laborActivityGroupInstance?.id}','${laborActivityGroupInstance?.code}')" >${fieldValue(bean: laborActivityGroupInstance, field: "code")}</div></td>
				
					<td><div style="cursor: pointer;" onclick="setClickVal('${laborActivityGroupInstance?.id}','${laborActivityGroupInstance?.code}')" >${fieldValue(bean: laborActivityGroupInstance, field: "description")}</div></td>
				
				</tr>				
	
			</g:each>	
	
		</g:if>
	
		<g:else>
	
			<tr><td colspan="2" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
		</g:else>				
	
	</table>
	
</div>	
	
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getLaborActGrpList', 'searchController': 'employee', 'divIdToUpdate': 'laborListGridDiv',  'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
	
		<g:paginate total="${laborActGrpListCount ?: 0}"  />
	
	</div>