<!-- Modal -->
<div class="modal fade dialog-box" id="projectManagerSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.project.manager.label" default="Search Project Manager"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogManagerCode"><g:message code="etech.report.emp.code.label" default="Code" /></label>
						<g:textField name="dialogManagerCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogManagerLastName"><g:message code="etech.report.emp.name.label" default="Last Name" /></label>
						<g:textField name="dialogManagerLastName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialogManagerFirstName"><g:message code="etech.report.emp.name.label" default="First Name" /></label>
						<g:textField name="dialogManagerFirstName" value="" class="form-control"/>
					</div>	
					
					<div class="col-sm-3 col-md-3">					
						<label for="dialogPayDept"><g:message code="etech.report.emp.name.label" default="Pay Dept" /></label>
						<g:textField name="dialogPayDept" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getProjectManagerList', controller: 'report', update: 'projectManagerListGridDiv', onSuccess: 'projectManagerSuccessSearch(data)', 
								params: '\'code=\'+document.getElementById(\'dialogManagerCode\').value+\'&lastName=\'+document.getElementById(\'dialogManagerLastName\').value+\'&firstName=\'+document.getElementById(\'dialogManagerFirstName\').value+\'&payDept=\'+document.getElementById(\'dialogPayDept\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetProjectManagerDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="projectManagerListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxProjectManagerList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>