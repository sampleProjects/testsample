<g:if test="${laborActivityCodeList?.size() > 0}">
<g:render template="/common/paginationCombo" model="['totalRecord':laborActivityCodeListCount, 'searchAction': 'getLaborActivityCodeDetailsList', 'searchController': 'projectLabor', 'divIdToUpdate': 'listGridDiv']"></g:render>
<div id="ajaxListView" class="title-content">
	<div class="table-responsive height-scroll">
	
  		<table class="table table-striped">	
			<thead>
				<tr>
					<th><g:message code="etech.select.activity.coe.label" default="COE" /></th>
					
					<th><g:message code="etech.select.activity.group.label" default="Labor Dept" /></th>
					
					<th><g:message code="etech.laborActivityCode.label" default="Activity Code" /></th>
	
					<th><g:message code="etech.laborActivityCodeDescription.label" default="Description" /></th>
	
				</tr>
			</thead>
			<g:each in="${laborActivityCodeList}" status="i" var="laborActivityCodeInstance">	
				<tr>
					<td><div style="cursor: pointer;" onclick="setClickVal('${laborActivityCodeInstance?.id}','${laborActivityCodeInstance?.code}')" >${laborActivityCodeInstance?.laborActivityGroup?.coe?.code}</div></td>
					
					<td><div style="cursor: pointer;" onclick="setClickVal('${laborActivityCodeInstance?.id}','${laborActivityCodeInstance?.code}')" >${laborActivityCodeInstance?.laborActivityGroup?.code}</div></td>
						
					<td><div style="cursor: pointer;" onclick="setClickVal('${laborActivityCodeInstance?.id}','${laborActivityCodeInstance?.code}')" >${fieldValue(bean: laborActivityCodeInstance, field: "code")}</div></td>
				
					<td><div style="cursor: pointer;" onclick="setClickVal('${laborActivityCodeInstance?.id}','${laborActivityCodeInstance?.code}')" >${fieldValue(bean: laborActivityCodeInstance, field: "description")}</div></td>
				
				</tr>				
			</g:each>				
		</table>
	</div>
	
	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getLaborActivityCodeDetailsList', 'searchController': 'projectLabor', 'divIdToUpdate': 'listGridDiv',  'searchParams': 'true']" />
	
	<div class="pagination margintopbottom15">
		<g:paginate total="${laborActivityCodeListCount ?: 0}" />
	</div>
</div>
</g:if>