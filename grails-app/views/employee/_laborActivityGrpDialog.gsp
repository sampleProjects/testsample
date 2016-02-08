<!-- Modal -->
<div class="modal fade dialog-box" id="myModal" role="dialog">
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
						<label for="laborActGrpNmae"><g:message code="etech.employee.labor.group.label" default="Department" /></label>
						<g:textField name="laborActGrpNmae" value="" class="form-control"/>					
					</div>
					<div class="col-sm-4 col-md-4">					
						<label for="laborActGrpDesc"><g:message code="etech.employee.labor.description.label" default="Description" /></label>
						<g:textField name="laborActGrpDesc" value="" class="form-control"/>
					</div>		
							
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
							
							<input type="button" onclick="${remoteFunction(action:'getLaborActGrpList',controller:'employee',  onSuccess: 'laborGrpSuccessSearch(data)',
			                     update: 'laborListGridDiv', params: '\'code=\'+document.getElementById(\'laborActGrpNmae\').value+\'&description=\'+document.getElementById(\'laborActGrpDesc\').value+\'\'')}" value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>	           	
			           		 <input type="button" class="btn btn-success search-btn" onclick="reset()" value="Reset"/>
						</div>
		           	</div>
								
				</div>
					<div id="laborListGridDiv">		
						<g:render template="laborActGrpList"/>
			     	</div>
				
				<div class="clearfix"> </div>			
			</div>  

		<div class="modal-footer">
			<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
		</div>			
		</div>      
	</div>
</div>
