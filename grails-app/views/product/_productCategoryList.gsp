<div class="clearfix"></div><hr>
 
<g:render template="/common/paginationCombo" model="['totalRecord':productCategoryInstanceCount, 'searchAction': 'getProductCategoryList', 'searchController': 'product', 'divIdToUpdate': 'productCategorylistGridDiv']"></g:render>

	<div id="ajaxListView" class="table-responsive height-scroll">	

  		<table class="table table-striped">	
			<thead>
				<tr>
	
					<th><g:message code="etech.productCategory.category.label" default="Category" /></th>
	
					<th><g:message code="etech.productCategory.description.label" default="Description" /></th>
					
				</tr>
			</thead>
			<g:if test="${productCategoryInstanceList?.size() > 0}">
	    		<g:each in="${productCategoryInstanceList}" status="i" var="productCategoryInstance">
					<tr>			
						<td><div style="cursor: pointer;" onclick="setProductCategoryCode('${productCategoryInstance?.id}','${productCategoryInstance?.category}')" >${fieldValue(bean: productCategoryInstance, field: "category")}</div></td>
							
						<td>${fieldValue(bean: productCategoryInstance, field: "description")}</td>
						
					</tr>
				</g:each>
			</g:if>
			<g:else>
	
				<tr><td colspan="3" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
	
			</g:else>
			
		</table>
</div>

	<g:render template="/common/ajaxSortingAndPaging" model="['searchAction': 'getProductCategoryList', 'searchController': 'product', 'divIdToUpdate': 'productCategorylistGridDiv', 'searchParams': 'true']" />			
	<div class="pagination margintopbottom15">
			
		<g:paginate total="${productCategoryInstance ?: 0}" maxsteps="5" />
				
	</div>