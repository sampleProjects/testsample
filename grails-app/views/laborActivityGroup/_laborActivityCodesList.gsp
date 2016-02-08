<div class="col-sm-12">
	<div class="panel-group accordion-panel-top" id="accordion1">
    	<div class="panel panel-default">
        	<div class="panel-heading">
        		<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion1" href="#collapseOne1">	
        			<span class="icon-section" style="display: none;">
        			<i class="fa fa-plus-circle"></i>
        			<i class="fa fa-minus-circle"></i> </span>
        	  Associated Labor Activity Codes</a> 
        	  <span class="small-text">(${ laborActivityGroupInstance?.laborActivityCodes?.size()})</span>
                </h4>
            </div>
	        <div id="collapseOne1" class="panel-collapse collapse">
	        	<div class="panel-body">   
					<table class="table table-hover">
						<thead>
							<tr>
								<th><g:message code="etech.laborActivityCode.code.label" default="Code" /></th>

								<th><g:message code="etech.laborActivityCode.description.label" default="Description" /></th>
								
								<th style="text-align:right"><g:message code="etech.laborActivityCode.standardRate.label" default="Standard Rate" /></th>
								
								<th style="text-align:right"><g:message code="etech.laborActivityCode.Overhead.label" default="Overhead %" /></th>
								
								<th style="text-align:right"><g:message code="etech.laborActivityCode.billRate.label" default="Bill Rate" /></th>
								
							</tr> 
						</thead>
						<tbody>
							<g:if test="${laborActivityGroupInstance?.laborActivityCodes?.size() > 0}">
							<g:each in="${laborActivityGroupInstance?.laborActivityCodes}" status="i" var="laborActivityCodesInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

									<td>${fieldValue(bean: laborActivityCodesInstance, field: "code")}</td>

									<td>${fieldValue(bean: laborActivityCodesInstance, field: "description")}</td>
									
									<td style="text-align:right">${laborActivityCodesInstance?.standardRate}</td>
									
									<td style="text-align:right"><g:formatNumber number="${laborActivityCodesInstance?.overHead}" format="0.00"/></td>
									
									<td style="text-align:right">${laborActivityCodesInstance?.billRate}</td>

								</tr>
							</g:each>
							</g:if>
							<g:else>	
							<tr><td colspan="4" class="no-record-found"><g:message code="default.record.not.found" /></td></tr>
						</g:else>
						</tbody>
					</table>           			
				</div>
			</div>
		</div>
	</div>
</div>