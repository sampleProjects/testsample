<!-- Modal -->
<div class="modal fade dialog-box" id="parentProjectCodeDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.project.search.project.label" default="Search Parent Project"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogProjectCode"><g:message code="etech.project.code.label" default="Code" /></label>
						<g:textField name="dialogProjectCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogProjectName"><g:message code="etech.project.name.label" default="Name" /></label>
						<g:textField name="dialogProjectName" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
							<g:hiddenField value= "${projectInstance?.id}" name="parentProjectID" />
							<input type="button" onclick="${remoteFunction(action: 'getParentProjectList', controller: 'project', update: 'parentProjectCodelistGridDiv', onSuccess: 'parentProjectSuccessSearch(data)', 
								params: '\'dialogProjectCode=\'+document.getElementById(\'dialogProjectCode\').value+\'&dialogProjectName=\'+document.getElementById(\'dialogProjectName\').value+\'&parentProjectID=\'+document.getElementById(\'parentProjectID\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetparentProjectCodeDialog()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="parentProjectCodelistGridDiv">		
				 	<g:render template="parentProjectCodeList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>