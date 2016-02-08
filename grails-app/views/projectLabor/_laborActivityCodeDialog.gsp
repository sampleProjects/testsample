<!-- Modal -->
<div class="modal fade dialog-box" id="projLbrActScreeDialog" role="dialog">
	<div class="modal-dialog modal-lg">        
	      <!-- Modal content-->
		<div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">Search Activity</h4>
	        </div>
	        <!-- Modal Body-->
			<div class="modal-body">           
	        	<div class="row">  
	        	
	        		<div class="col-sm-3 col-md-3">
						<label for="coe"><g:message code="etech.dialog.coe.label" default="COE" /></label>							
						<g:select name="coe" class="selectpicker form-control"  from="${coeList}"  optionKey="code" optionValue="code" value="" noSelection="['': '--Select--']" />				
					</div>
	        	      	 
					<div class="col-sm-3 col-md-3">
						<label for="activityCode"><g:message code="etech.dialog.laborActivityCode.label" default="Activity Code" /></label>
						<g:textField name="laborActivityCode" value="" class="form-control"/>					
					</div>
							
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
							<input type="button" onclick="${remoteFunction(action:'getLaborActivityCodeDetailsList',controller:'projectLabor', onSuccess: 'activitySuccessSearch(data)',
			                     update: 'listGridDiv', params: '\'code=\'+document.getElementById(\'laborActivityCode\').value+\'&coe=\'+document.getElementById(\'coe\').value+\'\'')}" value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>	           	
			           		 <input type="button" class="btn btn-success search-btn" onclick="reset()" value="Reset"/>
			           	</div>
		           	</div>
				</div>		
				
					<div id="listGridDiv">
			     		<g:render template="laborActivityCodeList"/>
			     	</div>
				
				<div class="clearfix"> </div>			
			</div>  
			<div class="modal-footer">
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			</div> 
		</div>      
	</div>
</div>