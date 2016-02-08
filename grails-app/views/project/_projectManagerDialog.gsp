<!-- Modal -->
<div class="modal fade dialog-box" id="projectScreenPMSearchDialog" role="dialog">
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
						<label for="dialogEmpCode"><g:message code="etech.report.project.manager.code.label" default="Code" /></label>
						<g:textField name="dialogEmpCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogEmpFirstName"><g:message code="etech.report.first.emp.name.label" default="First Name" /></label>
						<g:textField name="dialogEmpFirstName" value="" class="form-control"/>
					</div>
						
					<div class="col-sm-3 col-md-3">					
						<label for="dialogEmpLastName"><g:message code="etech.report.last.emp.name.label" default="Last Name" /></label>
						<g:textField name="dialogEmpLastName" value="" class="form-control"/>
					</div>	
					<g:hiddenField name="projectId" value="${projectInstance?.id}"/>
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getProjectManagerList', controller: 'project', update: 'projectManagerListGridDiv', onSuccess: 'pmSuccessSearch(data)', 
								params: '\'code=\'+document.getElementById(\'dialogEmpCode\').value+\'&firstName=\'+document.getElementById(\'dialogEmpFirstName\').value+\'&lastName=\'+document.getElementById(\'dialogEmpLastName\').value+\'&projectId=\'+document.getElementById(\'projectId\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetProjectManagerDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="projectManagerListGridDiv">		
				 	<g:render template="ajaxUpdatedProjectManagerList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>