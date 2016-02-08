<!-- Modal -->
<div class="modal fade dialog-box" id="deptLaborActCodeDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.dept.search.labor.act.code.label" default="Search labor Activity Code"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-4 col-md-4">
						<label for="dialogLaborActGrpCode"><g:message code="etech.dept.labor.activity.code.label" default="Labor Activity Group" /></label>
						<g:textField name="dialogLaborActGrpCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-4 col-md-4">
						<label for="dialogLaborActCode"><g:message code="etech.dept.labor.act.code.label" default="Labor Activity Code" /></label>
						<g:textField name="dialogLaborActCode" value="" class="form-control"/>					
					</div>
					
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getLaborActCodeList', controller: 'department', update: 'laborActivityCodeListGridDiv', onSuccess: 'deptLbrActCodeSuccessSearch(data)', 
								params: '\'dialogLaborActGrpCode=\'+document.getElementById(\'dialogLaborActGrpCode\').value+\'&dialogLaborActCode=\'+document.getElementById(\'dialogLaborActCode\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetDepartmentCode()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="laborActivityCodeListGridDiv">		
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