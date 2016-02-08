<!-- Modal -->
<div class="modal fade dialog-box" id="laborActGroupSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">Search Labor Activity Department</h4>
	        </div>	        
	        <!-- Modal Body-->
			<div class="modal-body">       
	        	<div class="row">        	 
					<div class="col-sm-3 col-md-3">
						<label for="dialogLaborActivityGrpCode"><g:message code="etech.employee.labor.group.label" default="Department" /></label>
						<g:textField name="dialogLaborActivityGrpCode" value="" class="form-control"/>					
					</div>
					<div class="col-sm-3 col-md-3">					
						<label for="dialoglaborActGrpDesc"><g:message code="etech.employee.labor.description.label" default="Description" /></label>
						<g:textField name="dialogLaborActGrpDesc" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getLaborActivityGroupList', controller: 'report', update: 'laborActGroupListGridDiv', onSuccess: 'laborActGroupSuccessSearch(data)', 
								params: '\'laborActivityGrpCode=\'+document.getElementById(\'dialogLaborActivityGrpCode\').value+\'&dialoglaborActGrpDesc=\'+document.getElementById(\'dialogLaborActGrpDesc\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetLaborActGroupDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="laborActGroupListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxLaborActivityGroupList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>