<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':coeInstanceCount, 'searchAction': 'getCOEList', 'searchController': 'report', 'divIdToUpdate': 'coeListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.coe.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.coe.description.label" default="Description" /></th>
					
				</tr>
			</thead>
			<g:if test="${coeInstanceList?.size() > 0}">
	    		<g:each in="${coeInstanceList}" status="i" var="coeInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setCOECode('${coeInstance?.id}','${coeInstance?.code}')" >${fieldValue(bean: coeInstance, field: "code")}</div></td>
							
						<td>${fieldValue(bean: coeInstance, field: "description")}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getCOEList', 'searchController': 'report', 'divIdToUpdate': 'coeListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${coeInstanceCount ?: 0}" maxsteps="5" />
				
	</div>