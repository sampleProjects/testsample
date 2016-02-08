<!-- Modal -->
<div class="modal fade dialog-box" id="deptCodeDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.employee.search.employee.label" default="Search Department"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-4 col-md-4">
						<label for="dialogDepartmentCode"><g:message code="etech.employee.emp.code.label" default="Department Code" /></label>
						<g:textField name="dialogDepartmentCode" value="" class="form-control"/>					
					</div>
					<div class="col-sm-4 col-md-4">
						<label for="dialogDescription"><g:message code="etech.department.description.label" default="Description" /></label>
						<g:textField name="dialogDescription" value="" class="form-control"/>					
					</div>
					
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getDepartmentList', controller: 'employee', update: 'deptListGridDiv', onSuccess: 'deptSuccessSearch(data)', 
								params: '\'dialogDepartmentCode=\'+document.getElementById(\'dialogDepartmentCode\').value+\'&dialogDescription=\'+document.getElementById(\'dialogDescription\').value+\'\'')}"
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetDepartmentCode()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="deptListGridDiv">		
				 	<g:render template="departmentCodeList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>