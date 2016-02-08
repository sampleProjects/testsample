<!-- Modal -->
<div class="modal fade dialog-box" id="customerSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.customer.label" default="Search Customer"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogCustomerCode"><g:message code="etech.report.customer.code.label" default="Code" /></label>
						<g:textField name="dialogCustomerCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogCustomerName"><g:message code="etech.report.customer.name.label" default="Name" /></label>
						<g:textField name="dialogCustomerName" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getCustomerList', controller: 'report', update: 'customerListGridDiv', onSuccess: 'customerSuccessSearch(data)', 
								params: '\'dialogCustomerCode=\'+document.getElementById(\'dialogCustomerCode\').value+\'&dialogCustomerName=\'+document.getElementById(\'dialogCustomerName\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetCustomerDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="customerListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxCustomerList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>