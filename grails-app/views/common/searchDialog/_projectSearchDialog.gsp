<!-- Modal -->
<div class="modal fade dialog-box" id="projectSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.project.label" default="Search Project"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogProjectCode"><g:message code="etech.report.project.code.label" default="Code" /></label>
						<g:textField name="dialogProjectCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogProjectName"><g:message code="etech.report.project.name.label" default="Name" /></label>
						<g:textField name="dialogProjectName" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getProjectList', controller: 'report', update: 'projectListGridDiv', onSuccess: 'projectSuccessSearch(data)', 
								params: '\'dialogProjectCode=\'+document.getElementById(\'dialogProjectCode\').value+\'&dialogProjectName=\'+document.getElementById(\'dialogProjectName\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetProjectDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="projectListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxProjectList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>