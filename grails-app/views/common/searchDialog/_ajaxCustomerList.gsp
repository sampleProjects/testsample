<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':customerInstanceCount, 'searchAction': 'getCustomerList', 'searchController': 'report', 'divIdToUpdate': 'customerListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.report.customer.code.label" default="Code" /></th>
	
					<th><g:message code="etech.report.customer.Name.label" default="Name" /></th>
					
				</tr>
			</thead>
			<g:if test="${customerInstanceList?.size() > 0}">
	    		<g:each in="${customerInstanceList}" status="i" var="customerInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setCustomerCode('${customerInstance?.id}','${customerInstance?.code}')" >${fieldValue(bean: customerInstance, field: "code")}</div></td>
							
						<td>${fieldValue(bean: customerInstance, field: "name")}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getCustomerList', 'searchController': 'report', 'divIdToUpdate': 'customerListGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${customerInstanceCount ?: 0}" maxsteps="5" />
				
	</div>