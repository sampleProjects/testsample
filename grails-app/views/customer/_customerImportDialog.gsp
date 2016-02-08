<!-- Modal -->
<div class="modal fade dialog-box" id="customerImportDialog" role="dialog">

	<div class="modal-dialog modal-md">
	        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	        
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          
	          <h4 class="modal-title"><g:message code="etech.customer.search.customer.label" default="Search Customer"/></h4>
	          
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
					
						<label for="dialogCustomerId"><g:message code="etech.customer.emp.code.label" default="Code" /></label>						
						<g:textField name="dialogCustomerId" value="" class="form-control"/>					
						
					</div>
			
					<div class="col-sm-3 col-md-3">
										
						<label for="dialogName"><g:message code="etech.customer.name.label" default="Name" /></label>
						<g:textField name="dialogName" value="" class="form-control"/>
						
					</div>	
									
					<div class="col-sm-2 col-md-2 invisible">
					
						<label for="isImportedCustShow"><g:message code="etech.customer.assigned.customer.label" default="Assigned" /></label><br/>			
						<g:checkBox name="isAssignedCustShow" id="isAssignedCustShow" value="" />
							
					</div>
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getHHCustomerMasterList', controller: 'customer', update: 'custImportListGridDiv', onSuccess: 'custSuccessSearch(data)', 
								params: '\'customerId=\'+document.getElementById(\'dialogCustomerId\').value+\'&name=\'+document.getElementById(\'dialogName\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetCustomerImportDialog()" value="Reset"/>
			           		 
			           	</div>
			           	
		           	</div>
		           	
				</div>					
				
				<div id="custImportListGridDiv">
						
				 	<g:render template="customerImportList"></g:render>
				 	
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>