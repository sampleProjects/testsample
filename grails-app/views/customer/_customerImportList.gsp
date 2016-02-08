<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':customerMstListCount, 'searchAction': 'getHHCustomerMasterList', 'searchController': 'customer', 'divIdToUpdate': 'custImportListGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
		
			<thead>
		
				<tr>
	
					<th><g:message code="etech.customer.code.label" default="Code" /></th>
	
					<th><g:message code="etech.customer.name.label" default="Name" /></th>
					
				</tr>
		
			</thead>
		
			<g:if test="${customerMstList?.size() > 0}">
			
	    		<g:each in="${customerMstList}" status="i" var="customerMstInstance">
	    			
	    			<g:if test="${customerList.contains(customerMstInstance?.hhCustId)}" >
	    			
		    			<tr class="selected-row">
		    			
		    				<td><div>${fieldValue(bean: customerMstInstance, field: "hhCustId")}</div></td>
						
							<td><div>${fieldValue(bean: customerMstInstance, field: "name1")}</div></td>
							
						</tr>
						
	    			</g:if>
	    			
	    			<g:else>
	    			
					<tr >			
						<g:hiddenField name="customerName_${i}" id="customerName_${i}" value="${customerMstInstance?.name1}"/>	
						<td><div style="cursor: pointer;" onclick="setCustomerDtl('${customerMstInstance?.hhCustId}','${i}')" >${fieldValue(bean: customerMstInstance, field: "hhCustId")}</div></td>
					
						<td><div style="cursor: pointer;" onclick="setCustomerDtl('${customerMstInstance?.hhCustId}','${i}')" >${fieldValue(bean: customerMstInstance, field: "name1")}</div></td>
					
					</tr>
						
					</g:else>
								
				</g:each>
				
			</g:if>
			
			<g:else>
	
				<tr><td colspan="2" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>
	
<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getHHCustomerMasterList', 'searchController': 'customer', 'divIdToUpdate': 'custImportListGridDiv', 'searchParams': 'true']" />			
	
<div class="pagination margintopbottom15">
			
	<g:paginate total="${customerMstListCount ?: 0}" maxsteps="5" />
				
</div>