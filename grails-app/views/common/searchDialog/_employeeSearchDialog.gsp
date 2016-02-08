<!-- Modal -->
<div class="modal fade dialog-box" id="employeeSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.employee.label" default="Search Employee"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogEmployeeCode"><g:message code="etech.report.emp.code.label" default="Code" /></label>
						<g:textField name="dialogEmployeeCode" value="" class="form-control"/>					
					</div>
										
					<div class="col-sm-3 col-md-3">					
						<label for="dialogEmployeeLastName"><g:message code="etech.report.emp.name.label" default="Last Name" /></label>
						<g:textField name="dialogEmployeeLastName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialogEmployeeFirstName"><g:message code="etech.report.emp.name.label" default="First Name" /></label>
						<g:textField name="dialogEmployeeFirstName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialogEmployeePayDept"><g:message code="etech.report.emp.name.label" default="Pay Dept" /></label>
						<g:textField name="dialogEmployeePayDept" value="" class="form-control"/>
					</div>		
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getEmployeeList', controller: 'report', update: 'employeeListGridDiv', onSuccess: 'employeeSuccessSearch(data)', 
								params: '\'code=\'+document.getElementById(\'dialogEmployeeCode\').value+\'&lastName=\'+document.getElementById(\'dialogEmployeeLastName\').value+\'&firstName=\'+document.getElementById(\'dialogEmployeeFirstName\').value+\'&payDept=\'+document.getElementById(\'dialogEmployeePayDept\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetEmployeeDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="employeeListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxEmployeeList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>