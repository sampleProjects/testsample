<!-- Modal -->
<div class="modal fade dialog-box" id="salesExecutiveSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.employee.label" default="Search Sales Executive"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogEmployeeCode"><g:message code="etech.report.emp.code.label" default="Code" /></label>
						<g:textField name="dialogSalesExecutiveCode" value="" class="form-control"/>					
					</div>
										
					<div class="col-sm-3 col-md-3">					
						<label for="dialogEmployeeLastName"><g:message code="etech.report.emp.name.label" default="Last Name" /></label>
						<g:textField name="dialogSalesExecutiveLastName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialogEmployeeFirstName"><g:message code="etech.report.emp.name.label" default="First Name" /></label>
						<g:textField name="dialogSalesExecutiveFirstName" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getSalesExecutiveList', controller: 'customer', update: 'salesExecutiveListGridDiv', onSuccess: 'salesExecutiveSuccessSearch(data)', 
								params: '\'code=\'+document.getElementById(\'dialogSalesExecutiveCode\').value+\'&lastName=\'+document.getElementById(\'dialogSalesExecutiveLastName\').value+\'&firstName=\'+document.getElementById(\'dialogSalesExecutiveFirstName\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetSalesExecutiveDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="salesExecutiveListGridDiv">		
				 	<g:render template="ajaxSalesList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>