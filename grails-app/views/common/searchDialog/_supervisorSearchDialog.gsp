<!-- Modal -->
<div class="modal fade dialog-box" id="supervisorSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.supervisor.label" default="Search Supervisor"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogSupervisorCode"><g:message code="etech.report.emp.code.label" default="Code" /></label>
						<g:textField name="dialogSupervisorCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogSupervisorLastName"><g:message code="etech.report.emp.name.label" default="Last Name" /></label>
						<g:textField name="dialogSupervisorLastName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialogSupervisorFirstName"><g:message code="etech.report.emp.name.label" default="First Name" /></label>
						<g:textField name="dialogSupervisorFirstName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialogSupervisorPayDept"><g:message code="etech.report.emp.name.label" default="Pay Dept" /></label>
						<g:textField name="dialogSupervisorPayDept" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getSupervisorList', controller: 'report', update: 'supervisorListGridDiv', onSuccess: 'supervisorSuccessSearch(data)', 
								params: '\'code=\'+document.getElementById(\'dialogSupervisorCode\').value+\'&lastName=\'+document.getElementById(\'dialogSupervisorLastName\').value+\'&firstName=\'+document.getElementById(\'dialogSupervisorFirstName\').value+\'&payDept=\'+document.getElementById(\'dialogSupervisorPayDept\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetSupervisorDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="supervisorListGridDiv">		
				 	<g:render template="../common/searchDialog/ajaxSupervisorList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>