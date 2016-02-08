<!-- Modal -->
<div class="modal fade dialog-box" id="acctExecutiveSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.acct.executive.label" default="Search Account Director"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogAcctExecutiveCode"><g:message code="etech.report.emp.code.label" default="Code" /></label>
						<g:textField name="dialogAcctExecutiveCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogAcctExecutiveName"><g:message code="etech.report.emp.name.label" default="Name" /></label>
						<g:textField name="dialogAcctExecutiveName" value="" class="form-control"/>
					</div>	
					
					<g:if test="${isProjectManagerEnabled}">
						<div class="col-sm-5 col-md-5 margintop25">	
							<g:checkBox name="projectManagerChecked" id="projectManagerChecked"/> Include Project Manager
						</div>
						<div class="clearfix"> </div>	
						<div class="col-sm-4 col-md-4 margintop25">
							<div class="form-group">
								<input type="button" onclick="${remoteFunction(action: 'getAcctExecutiveList', controller: 'report', update: 'acctExecutiveListGridDiv', onSuccess: 'acctExecutiveSuccessSearch(data)', 
									params: '\'code=\'+document.getElementById(\'dialogAcctExecutiveCode\').value+\'&name=\'+document.getElementById(\'dialogAcctExecutiveName\').value+\'&projectManagerChecked=\'+document.getElementById(\'projectManagerChecked\').checked+\'\'')}" 
									value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
				           		 
				           		 <input type="button" class="btn btn-success search-btn" onclick="resetAcctExecutiveDialogDataWithCheckBox()" value="Reset"/>
				           	</div>
			           	</div>
		           	</g:if>
		           	<g:else>
		           		<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
							<input type="button" onclick="${remoteFunction(action: 'getAcctExecutiveList', controller: 'report', update: 'acctExecutiveListGridDiv', onSuccess: 'acctExecutiveSuccessSearch(data)', 
								params: '\'code=\'+document.getElementById(\'dialogAcctExecutiveCode\').value+\'&name=\'+document.getElementById(\'dialogAcctExecutiveName\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetAcctExecutiveDialogData()" value="Reset"/>
			           	</div>
		           	</div>
		           	</g:else>

				</div>					
				
				<div id="acctExecutiveListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxAcctExecutiveList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>