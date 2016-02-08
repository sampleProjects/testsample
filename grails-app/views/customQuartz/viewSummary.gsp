<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="main" />
<title>Job Summary </title>
<content tag="pageName">customQuartzPage</content>
</head>
<body>
	<div class="navigation">
		<h3>Job Summary: ${params?.jobName}</h3>
	</div>
	<div class="white-bg">
	<div id="list-customQUartz" class="col-sm-12" role="main">
		<div id="list-projectLabor" class="col-sm-12" role="main">
			<div id="jobSummaryListGridDiv">
					<div id="ajaxListView" class="table-responsive">
						<div class="table-responsive no-padding">
							<table class="table table-hover" style="margin-top:25px;">
								<thead>
									<tr>
										<th class="text-right">Year</th>
										<th class="text-right">Month</th>
										<th class="text-right">Total Txns</th>
										<th class="text-right">Imported</th>
										<th class="text-right">Pending</th>
									</tr>
								</thead>
								<tbody>
								<g:each in="${laborTrnsRecordList}" status="i" var="laborTrnsRecordInstance">
									<tr>
										<td class="text-right">${laborTrnsRecordInstance.year}</td>
										<td class="text-right">${laborTrnsRecordInstance.month}</td>
										<td class="text-right">${laborTrnsRecordInstance.totalLaborTransaction}</td>
										<td class="text-right">${laborTrnsRecordInstance.importedLaborTransaction}</td>
										<td class="text-right">${laborTrnsRecordInstance.pendingLaborTransaction}</td>
									</tr>
								</g:each>
								</tbody>
							</table>
						</div>
					</div>
		 <g:render template="/common/returnLink" model="['returnAction': 'getJobHistory', 'returnController': 'customQuartz', 'messageCode':'etech.customQuartz.return.screen', 'defaultMessage': 'Return to Job History', 'linkParam': [className:jobRegisterInstance?.className?:'org.solcorp.etech.'+params?.jobName]]"></g:render>	
	</div>
	</div></div></div>
</body>
</html>