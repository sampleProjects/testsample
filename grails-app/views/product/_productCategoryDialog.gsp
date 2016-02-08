<!-- Modal -->
<div class="modal fade dialog-box" id="productCategoryDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.product.search.productCategory.label" default="Search Product Category"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogProductCategory"><g:message code="etech.product.productCategory.label" default="Category" /></label>
						<g:textField name="dialogProductCategory" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogProductCategoryDescription"><g:message code="etech.product.productCategoryDescription.label" default="Description" /></label>
						<g:textField name="dialogProductCategoryDescription" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getProductCategoryList', controller: 'product', update: 'productCategorylistGridDiv', onSuccess: 'productCategorySuccessSearch(data)', 
								params: '\'dialogProductCategory=\'+document.getElementById(\'dialogProductCategory\').value+\'&dialogProductCategoryDescription=\'+document.getElementById(\'dialogProductCategoryDescription\').value+\'&productClassType=\'+document.getElementById(\'productClassType\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetProductCategoryDialog()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="productCategorylistGridDiv">		
				 	<g:render template="productCategoryList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>