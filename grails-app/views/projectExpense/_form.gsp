<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>

<div class="col-sm-4 col-md-3">
	<div class="form-group">
        <label for="scheduleStartDate" class="date-picker control-label"><g:message code="projectExpense.scheduleStartDate.label" default="Date" /></label>
        	<g:set value="${hasErrors(bean: projectExpenseInstance, field: 'expenseDate', 'error')}" var="test"></g:set>
        	<g:render template="/common/dateComponent" class="" model="['fieldName': 'expenseDate','fieldValue': projectServiceDetailInstance?.plannedExpense?.expenseDate?:new Date(), 'cssClass': test]"></g:render>
     </div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">	
		<label for="Project" class="control-label"><g:message code="projectExpense.project.label" default="Project" /></label>			
		<g:textField name="project" value="${projectServiceDetailInstance?.projectProductDetail?.project?.name}" class="form-control" disabled = "disabled"/>
	</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
		<label for="Customer" class="control-label"><g:message code="projectExpense.customer.label" default="Customer" /></label>			
			<g:textField name="project" value="${projectServiceDetailInstance?.projectProductDetail?.project?.customer?.name}" class="form-control" disabled = "disabled"/>
		</div>
</div>
	
<div class="col-sm-4 col-md-3">
	<div class="form-group">
			<label for="Phase" class="control-label"><g:message code="projectExpense.service.label" default="Service" /></label>			
			<g:textField name="txtService" value="${projectServiceDetailInstance?.service?.code}" class="form-control" disabled = "disabled"/>
		</div>
</div>
	
<div class="clearfix"></div>
<g:if test="${session['logedInUser']?.hoursOnly == false}">
	<div id="list-projectExpense" class="col-sm-12" role="main">
		<div class="box-body table-responsive no-padding">
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center">Expense</th>
						<th class="text-center">Labor</th>
						<th class="text-center">Total Cost</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="text-center">${projectServiceDetailInstance?.getAllPlannedExpenseTotal()?:0.00}</td>
						<td class="text-center">${(projectServiceDetailInstance?.getAllPlannedLaborTotal()?:0.00) +(projectServiceDetailInstance?.getAllPlannedStdOHCostTotal()?:0.00)}</td>
						<td class="text-center">${(projectServiceDetailInstance?.getAllPlannedLaborTotal()?:0.00) +(projectServiceDetailInstance?.getAllPlannedStdOHCostTotal()?:0.00)+(projectServiceDetailInstance?.getAllPlannedExpenseTotal()?:0.00)}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</g:if>
<div class="clearfix"></div>

<div id="list-projectExpense" class="col-sm-12" role="main">
	<div class="title text-left"></div>
	<label for="Add Expense Details" style="cursor: pointer;text-decoration: underline;color: #337AB7" data-toggle="modal" data-target="#myModal"><g:message code="etech.projectExpense.expenseCode.label" default="Add Expense Details" /></label>
	<div class="box-body table-responsive no-padding">	
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="250px">Code</th>
					<th class="text-right" nowrap>Quantity</th>
					<th class="text-right" nowrap>Unit Cost</th>
					<th class="text-right" nowrap>Total Cost</th>
					<th class="text-center" nowrap>Action</th>
				</tr>
			</thead>
			<tbody id="addExpenseDiv">
				<g:render template="projectExpenseDetailList"></g:render>	
			</tbody>
		</table>
	</div>
	
	<g:render template="/common/returnLink" model="['returnAction': 'edit', 'returnController': 'project', 'messageCode':'etech.project.return.screen', 'defaultMessage': 'Return to Project Screen', 'linkId': projectServiceDetailInstance?.projectProductDetail?.project?.id, 'isDirtyCheck': 'isDirtyCheck']"></g:render>
	
	<g:hiddenField name="projectDetailInstanceID" id="projectDetailInstanceID" value="${projectServiceDetailInstance?.id}"/>
	<g:hiddenField name="id" id="id" value="${projectServiceDetailInstance?.plannedExpense?.id}"/>
	<g:hiddenField name="removeIndex" id="removeIndex" value="" />
</div>

<g:render template="expenseCodeDialog"/>

<script type="text/javascript">
$(document).ready(function() {			     
    $('#myModal').on('show.bs.modal', function (e) {  				
  		$('#listGridDiv').hide();  					
	})	 		
});

function addProjectFieldAjax(id,code,isFromActualExpense){	
	var i = $(".addExpense").length;	
	<g:remoteFunction controller="projectExpense" asynchronous="false"  params="'i='+i+'&expId='+id+'&isFromActualExpense='+isFromActualExpense" action="addExpenseDetail" onSuccess='addProjectExpenseFieldInTemplate(data,i,id,code,isFromActualExpense)'/>
}
</script>