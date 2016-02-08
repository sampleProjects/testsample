<!-- Modal -->
<div class="modal fade dialog-box" id="coeSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.coe.label" default="Search COE"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogCOECode"><g:message code="etech.report.coe.code.label" default="Code" /></label>
						<g:textField name="dialogCOECode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogCOEDesc"><g:message code="etech.report.coe.description.label" default="Description" /></label>
						<g:textField name="dialogCOEDesc" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getCOEList', controller: 'report', update: 'coeListGridDiv', onSuccess: 'coeSuccessSearch(data)', 
								params: '\'dialogCOECode=\'+document.getElementById(\'dialogCOECode\').value+\'&dialogCOEDesc=\'+document.getElementById(\'dialogCOEDesc\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetCOEDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="coeListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxCOEList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>