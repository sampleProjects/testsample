<!-- Modal -->
<div class="modal fade dialog-box" id="empCodeDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.employee.search.employee.label" default="Search Employee"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogEmpId"><g:message code="etech.employee.emp.code.label" default="Code" /></label>
						<g:textField name="dialogEmpId" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogFirstName"><g:message code="etech.employee.firstName.label" default="First Name" /></label>
						<g:textField name="dialogFirstName" value="" class="form-control"/>
					</div>	
					
					 
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialoglastName"><g:message code="etech.employee.lastName.label" default="Last Name" /></label>
						<g:textField name="dialoglastName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3 invisible">
						<label for="isImportedEmpShow"><g:message code="etech.employee.assigned.employees.label" default="Assigned" /></label><br/>			
						<g:checkBox name="isAssignedEmpShow" id="isAssignedEmpShow" value="" />	
					</div>
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getEmployeeMstList', controller: 'employee', update: 'empCodelistGridDiv', onSuccess: 'empSuccessSearch(data)', 
								params: '\'employeeId=\'+document.getElementById(\'dialogEmpId\').value+\'&firstName=\'+document.getElementById(\'dialogFirstName\').value+\'&lastName=\'+document.getElementById(\'dialoglastName\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetEmpCodeDialog()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="empCodelistGridDiv">		
				 	<g:render template="employeeCodeList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>