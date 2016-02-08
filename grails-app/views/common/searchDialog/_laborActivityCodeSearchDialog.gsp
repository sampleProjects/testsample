<!-- Modal -->
<div class="modal fade dialog-box" id="laborActCodeSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.labor.act.code.label" default="Search Labor Activity Code"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="activityCode"><g:message code="etech.dialog.laborActivityCode.label" default="Activity Code" /></label>
						<g:textField name="laborActivityCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogCOEDesc"><g:message code="etech.report.coe.description.label" default="Description" /></label>
						<g:textField name="dialoglaborActCodeDesc" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getLaborActivityCodeList', controller: 'report', update: 'laborActCodeListGridDiv', onSuccess: 'laborActCodeSuccessSearch(data)', 
								params: '\'laborActivityCode=\'+document.getElementById(\'laborActivityCode\').value+\'&dialoglaborActCodeDesc=\'+document.getElementById(\'dialoglaborActCodeDesc\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetLaborActCodeDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="laborActCodeListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxLaborActivityCodeList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>