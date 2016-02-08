<%@page import="org.solcorp.etech.Constants"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.quartz.Trigger" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="layout" content="main"/>
<title>Job History</title>
<content tag="pageName">customQuartzPage</content>
<script type="text/javascript">
		if(${session['logedInUser']?.username?.toString()?.trim()?.equals(Constants.SUPER_ADMIN_USERNAME) == false}){
			location.href="${createLink(controller:'auth', action: 'signOut')}";
		}
</script>
</head>
<body>
  		<div class="navigation">
			<h3>Job History: ${jobName}</h3>
    	</div>
		
		<div id="list-customQUartz" class="col-sm-12" role="main">
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<div class="white-bg">
				<g:render template="/common/paginationCombo" model="['totalRecord':result.jobListCount, 'searchAction': 'getJobHistory', 'searchController': 'customQuartz', 'divIdToUpdate': 'listRecordsDiv']"></g:render>
				<div id="listRecordsDiv">
					<div class="clearfix">
						<div class="pull-left">
							<g:if test="${jobName.equals('ImportExpensesJob')}">
								<g:link  action="jobExpensesDetail" id="" params="[jobName: 'ImportExpensesJob', detailType: 'cumulative']"><span style="margin-left:15px;">View Full Import Status</span></g:link>								
								<span style="margin-right:5px;">|</span>
								<g:link  action="viewExpenseSummary" id="" params="[jobName: 'ImportExpensesJob']"><span style="margin-right:10px;">View Import Summary</span></g:link>	
							</g:if>
							<g:elseif test="${jobName.equals('ImportLaborTransactionsJob')}">
								<g:link  action="jobDetail" id="" params="[jobName: 'ImportLaborTransactionsJob', detailType: 'cumulative']"><span style="margin-left:15px;">View Full Import Status</span></g:link>
								<span style="margin-right:5px;">|</span>
								<g:link  action="viewLaborSummary" id="" params="[jobName: 'ImportLaborTransactionsJob']"><span style="margin-right:10px;">View Import Summary</span></g:link>
							</g:elseif>
							<g:elseif test="${jobName.equals('ImportProjectsJob')}">
								<g:link  action="jobProjectDetail" id="" params="[jobName: 'ImportProjectsJob', detailType: 'cumulative']"><span style="margin-left:15px;">View Full Import Status</span></g:link>
							</g:elseif>
							<g:elseif test="${jobName.equals('ImportCustomersJob')}">
								<g:link  action="jobCustomerDetail" id="" params="[jobName: 'ImportCustomersJob', detailType: 'cumulative']"><span style="margin-left:15px;">View Full Import Status</span></g:link>
							</g:elseif>
							<g:elseif test="${jobName.equals('ImportEmployeesJob')}">
								<g:link  action="jobEmployeeDetail" id="" params="[jobName: 'ImportEmployeesJob', detailType: 'cumulative']"><span style="margin-left:15px;">View Full Import Status</span></g:link>
							</g:elseif>
						</div>
					</div>
					<g:render template="listRecords"></g:render>
				</div>
          	</div>
		</div>
		
		
		<%--Job Summary Dialog Start--%>
		<div class="modal fade dialog-box" id="modalSummary" role="dialog">
			<div class="modal-dialog modal-md">        
				<div class="modal-content">
	        		<div class="modal-header">
	          			<button type="button" class="close" data-dismiss="modal">&times;</button>
	          			<h4 class="modal-title">${jobName}: Summary</h4>
	        		</div>
					<div class="modal-body word-wrap" id="summaryData">           
	        			
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success search-btn" onClick="copySummary();">Copy Summary</button>
						<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		<%--Job Summary Dialog End--%>
		
		
		<%--Job Stack Trace Dialog Start--%>
		<div class="modal fade dialog-box" id="modalStackTrace" role="dialog">
			<div class="modal-dialog modal-md">        
				<div class="modal-content">
	        		<div class="modal-header">
	          			<button type="button" class="close" data-dismiss="modal">&times;</button>
	          			<h4 class="modal-title">${jobName}: StackTrace</h4>
	        		</div>
					<div class="modal-body word-wrap" id="stackTraceData">           
	        			
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-success search-btn" onClick="copyStackTrace();">Copy StackTrace</button>
						<button type="button" class="btn btn-success search-btn" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		<%--Job Stack Trace Dialog End--%>
		
		
<script type="text/javascript">
function summaryDialog(jobId) {
	//$("#summaryData").html(summary);
	<g:remoteFunction controller="customQuartz" action="getSummaryOnJobId" params="'jobId='+jobId"  onSuccess="populateData(data,'summary')" />
}

function stacktraceDialog(jobId) {
	//$("#stackTraceData").html(stackTrace);
	<g:remoteFunction controller="customQuartz" action="getStackTraceOnJobId" params="'jobId='+jobId"  onSuccess="populateData(data,'stackTrace')" />
}

function populateData(data, modalToShow){
	if(modalToShow == 'summary'){
		$("#summaryData").html(data.summary);
		$("#modalSummary").modal('show');
	}else if(modalToShow == 'stackTrace'){
		$("#stackTraceData").html(data.stacktrace);
		$("#modalStackTrace").modal('show');
	}
}



function copySummary(){
	window.prompt("Copy to clipboard by pressing Ctrl+C", $("#summaryData").html());
}

function copyStackTrace(){
	window.prompt("Copy to clipboard by pressing Ctrl+C", $("#stackTraceData").html());
}

</script>
</body>

</html>