<!-- Modal -->
<div class="modal fade dialog-box" id="hhProjectImportDialog" role="dialog">

	<div class="modal-dialog modal-md">
	        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	        
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          
	          <h4 class="modal-title"><g:message code="etech.project.search.project.label" default="Search Project"/></h4>
	          
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
					
						<label for="dialogProjectId"><g:message code="etech.project.code.label" default="Code" /></label>						
						<g:textField name="dialogProjectId" value="" class="form-control"/>					
						
					</div>
			
					<div class="col-sm-3 col-md-3">
										
						<label for="dialogName"><g:message code="etech.project.name.label" default="Name" /></label>
						<g:textField name="dialogName" value="" class="form-control"/>
						
					</div>	
					
					<div class="col-sm-4 col-md-4">
										
						<label for="dialogCustCode"><g:message code="etech.project.customer.code.label" default="Customer Code" /></label>
						<g:textField name="dialogCustCode" value="" class="form-control"/>
						
					</div>	
									
					<div class="col-sm-2 col-md-2 invisible">
					
						<label for="isAssigned"><g:message code="etech.project.assigned.project.label" default="Assigned" /></label><br/>			
						<g:checkBox name="isAssigned" id="isAssigned" value="" />
							
					</div>
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getHHProjectMasterList', controller: 'project', update: 'projectImportListGridDiv', onSuccess: 'projectSuccessSearch(data)', 
								params: '\'projectId=\'+document.getElementById(\'dialogProjectId\').value+\'&name=\'+document.getElementById(\'dialogName\').value+\'&customerCode=\'+document.getElementById(\'dialogCustCode\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetProjectImportDialog()" value="Reset"/>
			           		 
			           	</div>
			           	
		           	</div>
		           	
				</div>
				
				<div id="customerExistMessageDiv"><div id="custMessageDiv" style="margin: 6px 0 6px 28px;"><g:message code="etech.project.customer.not.exist.system"/></div></div>								
				
				<div id="projectImportListGridDiv">
						
				 	<g:render template="hhProjectImportList"></g:render>
				 	
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>