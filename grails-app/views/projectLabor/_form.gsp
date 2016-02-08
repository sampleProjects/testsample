<%@page import="org.solcorp.etech.Constants"%>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
        <label for="scheduleStartDate" class="date-picker control-label"><g:message code="projectLabor.scheduleStartDate.label" default="Date" /></label>
			<g:render template="/common/dateComponent" class="form-control fieldcontain ${hasErrors(bean: projectExpenseInstance, field: 'laborActivityDate', 'error')}" model="['fieldName': 'txtlaborActivityDate','fieldValue': projectServiceDetailInstance?.plannedLabor?.laborActivityDate?:new Date()]"></g:render>
      </div>
    </div>
	
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
			<label for="Project" class="control-label"><g:message code="projectLabor.project.label" default="Project" /></label>			
			<g:textField name="project" value="${projectServiceDetailInstance?.projectProductDetail?.project?.name}" class="form-control" disabled = "disabled"/>
		</div>
	</div>
	
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
		<label for="Customer" class="control-label"><g:message code="projectLabor.customer.label" default="Customer" /></label>			
			<g:textField name="project" value="${projectServiceDetailInstance?.projectProductDetail?.project?.customer?.name}" class="form-control" disabled = "disabled"/>
		</div>
	</div>
	
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
			<label for="Executive" class="control-label"><g:message code="projectLabor.executive.label" default="Executive" /></label>			
			<g:textField name="project" value="${projectServiceDetailInstance?.projectProductDetail?.project?.accExecutive?.firstName}" class="form-control" disabled = "disabled"/>
		</div>
	</div>
	
	<div class="clearfix"></div>
	
	<div class="col-sm-4 col-md-3">
		<div class="form-group">
			<label for="Service" class="control-label"><g:message code="projectLabor.service.label" default="Service" /></label>			
			<g:textField name="txtService" value="${projectServiceDetailInstance?.service?.code}" class="form-control" disabled = "disabled"/>
		</div>
	</div>

	<div class="clearfix"></div>
<g:if test="${session['logedInUser']?.hoursOnly == false}">
	<div id="list-projectLabor" class="col-sm-12" role="main">
			<div class="box-body table-responsive no-padding">
				<table class="table table-hover">
					<thead>
						<tr>
							<th class="text-center">Expense</th>
							<th class="text-center">Labor</th>
							<th class="text-center">OH</th>
							<th class="text-center">Total Cost</th>
							<th class="text-center">Bill Amount</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center">${projectServiceDetailInstance?.getAllPlannedExpenseTotal()?:0.00}</td>
							<td class="text-center">${projectServiceDetailInstance?.getAllPlannedLaborTotal()?:0.00}</td>
							<td class="text-center">${projectServiceDetailInstance?.getAllPlannedStdOHCostTotal()?:0.00}</td>
							<td class="text-center">${(projectServiceDetailInstance?.getAllPlannedExpenseTotal()?:0.00)+(projectServiceDetailInstance?.getAllPlannedLaborTotal()?:0.00)+(projectServiceDetailInstance?.getAllPlannedStdOHCostTotal()?:0.00)}</td>
							<td class="text-center">${projectServiceDetailInstance?.getPlannedBillAmountTotal()?:0.00}</td>
						</tr>
					</tbody>
				</table>
			</div>
	
	</div>
</g:if>
<div class="clearfix"></div>

<div id="list-projectLabor" class="col-sm-12" role="main">
	<div class="title text-left">
		<h2>Group ${projectServiceDetailInstance?.service?.laborActivityGroup?.code}: Labor Detail</h2>
	</div>
		<div class="box-body table-responsive no-padding">
		<g:hiddenField name="isHoursOnly" id="isHoursOnly" value="${session['logedInUser']?.hoursOnly}" />
			<table class="table table-hover" id="dataTable">
				<thead>
					<tr>
						<th width="250px">Code</th>
						<th class="text-right" nowrap>Hours</th>
						<g:if test="${session['logedInUser']?.hoursOnly == false}">
							<th class="text-right" nowrap>Std Rate</th>
							<th class="text-right" nowrap>Std Cost</th>
							<th class="text-right" nowrap>% OH</th>
							<th class="text-right" nowrap>OH Cost</th>
							<th class="text-right" nowrap>Std Cost + OH</th>
							<th class="text-right" nowrap>Bill Rate</th>
							<th class="text-right" nowrap>Bill Amt</th>
						</g:if>
						<th class="text-center" nowrap>Action</th>
					</tr>
				</thead>
				<tbody>
				<%int totalRows = 0; %>
				<g:if test="${projectServiceDetailInstance?.plannedLabor != null && projectServiceDetailInstance?.plannedLabor?.laborDetails != null && projectServiceDetailInstance?.plannedLabor?.laborDetails?.size() > 0}">					
					<g:each in="${projectServiceDetailInstance?.plannedLabor?.laborDetails}" status="i" var="laborDetailsInstance">
						<% totalRows = i; %>
						<tr>
							<td>
							<p id="laborActivityCode_${i}" class="marginbtm5">${laborDetailsInstance?.laborActivityCode?.code}</p>
							<p id="laborDesc_${i}">${laborDetailsInstance?.laborActivityCode?.description}</p></td>
							<td>
								<div class="col-sm-12 pull-right">
									<div class="row">
									<g:textField id="laborHours_${i}" name="laborHours_${i}" value="${laborDetailsInstance?.hours}" style="width: 120px;" class="form-control pull-right onlyDecimal" onblur="calculateCost(this.value,${i});" />
									</div>
								</div>
							</td>
							<g:if test="${session['logedInUser']?.hoursOnly == false}">
								<td class="text-right">
									<span id="laborStdRate_${i}" class="pull-right">${laborDetailsInstance?.standardRate}</span>
								</td>
								<td class="text-right"><span id="laborStdCost_${i}" class="pull-right">${laborDetailsInstance?.standardCost}</span></td>
								<td class="text-right"><span id="laborOverHead_${i}" class="pull-right">${laborDetailsInstance?.overHead}</span></td>
								<td class="text-right"><span id="laborOverHeadCost_${i}" class="pull-right">${laborDetailsInstance?.overHeadCost}</span></td>
								<td class="text-right"><span id="laborTotalCost_${i}" class="pull-right">${laborDetailsInstance?.totalCost}</span></td>
								<td class="text-right"><span id="laborBillRate_${i}" class="pull-right">${laborDetailsInstance?.billRate}</span></td>
								<td class="text-right"><span id="laborBillAmount_${i}" class="pull-right">${laborDetailsInstance?.billAmountTotal}</span></td>
							</g:if>							
							<td class="text-center">
								<g:if test="${!laborActGrpCodeList.contains(laborDetailsInstance?.laborActivityCode?.code)}">
									<input type='button' id="deleteBtn_${i}" class='remove-img marginleft5' onclick='javascript: deleteSaveRow(${i});' />
								</g:if>
							</td>
							<g:hiddenField id="storedRowID_${i}" name="storedRowID_${i}" value="${laborDetailsInstance?.id}"  />
							<g:hiddenField id="laborActivityCode_var_${i}" name="laborActivityCode_var_${i}" value="${laborDetailsInstance?.laborActivityCode?.id}"  />
							<g:hiddenField id="laborDesc_var_${i}" name="laborDesc_var_${i}" value="${laborDetailsInstance?.laborActivityCode?.description}"  />
							<g:hiddenField id="laborHours_var_${i}" name="laborHours_var_${i}" value="${laborDetailsInstance?.hours}"  />
							<g:hiddenField id="laborStdRate_var_${i}" name="laborStdRate_var_${i}" value="${laborDetailsInstance?.standardRate}"  />
							<g:hiddenField id="laborStdCost_var_${i}" name="laborStdCost_var_${i}" value="${laborDetailsInstance?.standardCost}"  />
							<g:hiddenField id="laborOverHead_var_${i}" name="laborOverHead_var_${i}" value="${laborDetailsInstance?.overHead}"  />
							<g:hiddenField id="laborOverHeadCost_var_${i}" name="laborOverHeadCost_var_${i}" value="${laborDetailsInstance?.overHeadCost}"  />
							<g:hiddenField id="laborTotalCost_var_${i}" name="laborTotalCost_var_${i}" value="${laborDetailsInstance?.totalCost}"  />
							<g:hiddenField id="laborBillRate_var_${i}" name="laborBillRate_var_${i}" value="${laborDetailsInstance?.billRate}"  />
							<g:hiddenField id="laborBillAmount_var_${i}" name="laborBillAmount_var_${i}" value="${laborDetailsInstance?.billAmountTotal}"  />
						</tr>
					</g:each>
				</g:if>
				<g:else>
					<g:each var="i" in="${(0..< laborActCodeList?.size())}">
						<tr>
							<td><span id="laborActivityCode_${i}">${laborActCodeList[i]?.code}</span><br><span id="laborDesc_${i}">${finalRowsresult['row_'+i]['laborActivityGroupDesc'] }</span></td>
							<td><g:textField id="laborHours_${i}" name="laborHours_${i}" value="0.00" class="form-control pull-right onlyDecimal" style="width: 120px;" onblur="calculateCost(this.value,${i});" /></td>
							
							<g:if test="${session['logedInUser']?.hoursOnly == false}">
								<td class="text-right"><span id="laborStdRate_${i}" class="pull-right">${finalRowsresult['row_'+i]['standardRate'] }</span></td>
								<td class="text-right"><span id="laborStdCost_${i}" class="pull-right">0.00</span></td>
								<td class="text-right"><span id="laborOverHead_${i}" class="pull-right">${finalRowsresult['row_'+i]['overHeadPer'] }</span></td>
								<td class="text-right"><span id="laborOverHeadCost_${i}" class="pull-right">0.00</span></td>
								<td class="text-right"><span id="laborTotalCost_${i}" class="pull-right">0.00</span></td>
								<td class="text-right"><span id="laborBillRate_${i}" class="pull-right">${finalRowsresult['row_'+i]['billRate'] }</span></td>
								<td class="text-right"><span id="laborBillAmount_${i}" class="pull-right">0.00</span></td>
							</g:if>
							<td><span class="remove-img marginleft5"></span></td>
							<g:hiddenField id="storedRowID_${i}" name="storedRowID_${i}" value=""  />
							<g:hiddenField id="laborActivityCode_var_${i}" name="laborActivityCode_var_${i}" value="${laborActCodeList[i]?.id}"  />
							<g:hiddenField id="laborDesc_var_${i}" name="laborDesc_var_${i}" value="${finalRowsresult['row_'+i]['laborActivityGroupDesc'] }"  />
							<g:hiddenField id="laborHours_var_${i}" name="laborHours_var_${i}" value="0.00"  />
							<g:hiddenField id="laborStdRate_var_${i}" name="laborStdRate_var_${i}" value="${finalRowsresult['row_'+i]['standardRate'] }"  />
							<g:hiddenField id="laborStdCost_var_${i}" name="laborStdCost_var_${i}" value="0.00"  />
							<g:hiddenField id="laborOverHead_var_${i}" name="laborOverHead_var_${i}" value="${finalRowsresult['row_'+i]['overHeadPer'] }"  />
							<g:hiddenField id="laborOverHeadCost_var_${i}" name="laborOverHeadCost_var_${i}" value="0.00"  />
							<g:hiddenField id="laborTotalCost_var_${i}" name="laborTotalCost_var_${i}" value="0.00"  />
							<g:hiddenField id="laborBillRate_var_${i}" name="laborBillRate_var_${i}" value="${finalRowsresult['row_'+i]['billRate'] }"  />
							<g:hiddenField id="laborBillAmount_var_${i}" name="laborBillAmount_var_${i}" value="0.00"  />
						</tr>
					</g:each>
				</g:else>
				</tbody>
			</table>
			<g:if test="${projectServiceDetailInstance?.service?.product?.code == Constants.DEFAULT_PRODUCT}">
				<label for="Add Activity" style="cursor: pointer;text-decoration: underline;color: #337AB7" data-toggle="modal" data-target="#projLbrActScreeDialog">
					<g:message code="etech.laborActivityCode.label" default="Add Activity" />
				</label>
			</g:if>
		</div>
	
	<br>
	<g:render template="/common/returnLink" model="['returnAction': 'edit', 'returnController': 'project', 'messageCode':'etech.project.return.screen', 'defaultMessage': 'Return to Project Screen', 'linkId': projectServiceDetailInstance?.projectProductDetail?.project?.id, 'isDirtyCheck': 'isDirtyCheck']"></g:render>
	<g:hiddenField name="projectDetailInstanceID" id="projectDetailInstanceID" value="${projectServiceDetailInstance?.id}"/>
	<g:hiddenField name="projectLaborInstanceID" id="projectLaborInstanceID" value="${projectServiceDetailInstance?.plannedLabor?.id}"/>
	<g:hiddenField name="maxRows" id="maxRows" value="${laborActCodeList?.size()}"/>
	<g:hiddenField name="removedRecordId" id="removedRecordId" value=""/>
</div>

<script type="text/javascript">
$(document).ready(function() {			     
    $('#projLbrActScreeDialog').on('show.bs.modal', function (e) {
  		reset();
  		$('#listGridDiv').hide(); 					
	})	 		
});

function addLaborActivityCodeDetailsAjax(id,code){			
	<g:remoteFunction controller="projectLabor" params="'laborActivityCodeId='+id" action="addLaborActivityCodeDetail" onSuccess='addLaborActivityCodeDetails(data,id,code)'/>
}
</script>