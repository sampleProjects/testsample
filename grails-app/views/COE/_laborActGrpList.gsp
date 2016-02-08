<div class="col-sm-12">
	<div class="panel-group accordion-panel-top" id="accordion">
    	<div class="panel panel-default">
        	<div class="panel-heading">
        		<h4 class="panel-title">
        			<a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">	
        			<span class="icon-section" style="display: none;">
        			<i class="fa fa-plus-circle"></i>
        			<i class="fa fa-minus-circle"></i> </span>
        	 Associated Labor Department <span class="small-text">(${COEInstance?.laborActivityGroup?.size()})</span></a>
                </h4>
            </div>
	        <div id="collapseOne" class="panel-collapse collapse">
	        	<div class="panel-body">   
					<table class="table table-hover">
						<thead>
							<tr>
								<th><g:message code="etech.COE.code.label" default="Code" /></th>

								<th><g:message code="etech.COE.description.label" default="Description" /></th>

							</tr>
						</thead>
						<tbody>
							<g:if test="${COEInstance?.laborActivityGroup?.size() > 0}">
							<g:each in="${COEInstance?.laborActivityGroup}" status="i" var="laborActivityGroupInstance">
								<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

									<td>${fieldValue(bean: laborActivityGroupInstance, field: "code")}</td>

									<td>${fieldValue(bean: laborActivityGroupInstance, field: "description")}</td>

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