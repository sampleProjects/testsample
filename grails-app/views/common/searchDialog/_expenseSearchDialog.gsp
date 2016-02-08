<!-- Modal -->
<div class="modal fade dialog-box" id="expenseSearchDialog" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
		
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"><g:message code="etech.report.search.expense.label" default="Search Expense"/></h4>
	        </div>
	        
	        <!-- Modal Body-->
			<div class="modal-body">       
			    
	        	<div class="row">        	 
			
					<div class="col-sm-3 col-md-3">
						<label for="dialogProjectCode"><g:message code="etech.report.expense.code.label" default="Code" /></label>
						<g:textField name="dialogExpenseCode" value="" class="form-control"/>					
					</div>
			
					<div class="col-sm-3 col-md-3">					
						<label for="dialogProjectName"><g:message code="etech.report.expense.description.label" default="Description" /></label>
						<g:textField name="dialogDescription" value="" class="form-control"/>
					</div>	
						
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action: 'getExpenseList', controller: 'report', update: 'expenseListGridDiv', onSuccess: 'expenseSuccessSearch(data)', 
								params: '\'dialogExpenseCode=\'+document.getElementById(\'dialogExpenseCode\').value+\'&dialogDescription=\'+document.getElementById(\'dialogDescription\').value+\'\'')}" 
								value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>           	
			           		 
			           		 <input type="button" class="btn btn-success search-btn" onclick="resetExpenseDialogData()" value="Reset"/>
			           		 
			           	</div>
		           	</div>
		           	
				</div>					
				
				<div id="expenseListGridDiv">		
				 	<g:render template="/common/searchDialog/ajaxExpenseList"/>
			    </div>
				
				<div class="clearfix"> </div>	
						
			</div>
			  
			<div class="modal-footer">
			
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			
			</div> 
			
		</div>      
	</div>
</div>