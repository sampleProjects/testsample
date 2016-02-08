<!-- Modal -->
<div class="modal fade dialog-box" id="industrySearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">Search Industry</h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogIndustryCode"><g:message code="industry.code.label" default="Code" /></label>
						<g:textField name="dialogIndustryCode" value="" class="form-control"/>					
					</div>
										
					<div class="col-sm-3 col-md-3">					
						<label for="dialogIndustryName"><g:message code="industry.name.label" default="Name" /></label>
						<g:textField name="dialogIndustryName" value="" class="form-control"/>
					</div>	
							
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getIndustryList', controller: 'customer', update: 'industryListGridDiv', onSuccess: 'industrySuccessSearch(data)', 
								params: '\'code=\'+document.getElementById(\'dialogIndustryCode\').value+\'&name=\'+document.getElementById(\'dialogIndustryName\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetIndustryDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="industryListGridDiv">		
				 	<g:render template="ajaxIndustryList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>