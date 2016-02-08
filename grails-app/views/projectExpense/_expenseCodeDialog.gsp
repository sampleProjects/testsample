<!-- Modal -->
<div class="modal fade dialog-box" id="myModal" role="dialog">
	<div class="modal-dialog modal-md">        
	      <!-- Modal content-->
		<div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title">Search Expense Code</h4>
	        </div>
	        <!-- Modal Body-->
			<div class="modal-body">           
	        	<div class="row">        	 
					<div class="col-sm-3 col-md-3">
						<label for="expenseCode"><g:message code="etech.projectExpense.code.label" default="Code" /></label>
						<g:textField name="expenseCode" value="" class="form-control"/>					
					</div>
					<div class="col-sm-4 col-md-4">					
						<label for="expenseDesc"><g:message code="etech.projectExpense.desc.label" default="Description" /></label>
						<g:textField name="expenseDesc" value="" class="form-control"/>
					</div>		
							
					<div class="col-sm-4 col-md-4 margintop25">
						<div class="form-group">
						
							<input type="button" onclick="${remoteFunction(action:'getExpenseList',controller:'projectExpense', onSuccess: 'expenseCodeSuccessSearch(data)',
			                     update: 'listGridDiv', params: '\'code=\'+document.getElementById(\'expenseCode\').value+\'&description=\'+document.getElementById(\'expenseDesc\').value+\'\'')}" value="${message(code: 'default.button.search.label', default: 'Search')}" id="search" class="btn btn-success search-btn"/>	           	
			           		 <input type="button" class="btn btn-success search-btn" onclick="reset()" value="Reset"/>
			           	</div>
		           	</div>
				</div>		
				
					<div id="listGridDiv">
			     		<g:render template="expenseCodeList"/>
			     	</div>
				
				<div class="clearfix"> </div>			
			</div>  
			<div class="modal-footer">
				<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
			</div> 
		</div>      
	</div>
</div>