<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">		
		<title><g:message code="etech.report.project.expenses.by.expense.code.title.label" default="Project expenses by expense code"/></title>
		<r:require module="report"/>
		<content tag="pageName">projectExpensesByExpenseCode</content>
	</head>
	<body>		
		<div class="navigation">			
			<h3><g:message code="etech.report.project.expenses.by.expense.code.title.label" default="Project expenses by expense code Report Selection" /></h3>			
		</div>	
     	<div class="white-bg">
			<div class="title ">			
       		 	<div class="marginleftright15">       		 		       		
	       			<span class="pull-right">&nbsp;${currentDate}</span> &nbsp;&nbsp;<label class="control-label pull-right"><g:message code="etech.report.run.date.label" default="Run Date"/>:</label>				
				 	<label class="control-label pull-left"><g:message code="etech.report.from.label" default="From"/>:</label> <span class="pull-left">&nbsp;${params?.fromDate}</span>				
				 	<label  class="control-label pull-left marginleft5"><g:message code="etech.report.to.label" default="To"/>:</label> <span class="pull-left">&nbsp;${params?.toDate}</span>&nbsp;&nbsp;				
				</div>				  
       		</div>		
       		<g:set value="" var="old_Proj"></g:set>
			<g:set value="" var="old_Exp"></g:set>	
			<g:set value="" var="lproj"></g:set>
			<div class="col-xs-12">							 
				<div class="table-responsive">					
					<table class="table without-border">
						<g:if test="${projectByExpenseReporList?.size() > 0}">	
						<g:if test="${params?.includeDetail == 'true' }">
							<g:each in="${projectByExpenseReporList}" status="i" var="projectByExpenseInst">																		 
								<g:set value="${projectByExpenseInst?.hhProjectId }" var="lproj"></g:set>								 
								<g:if test="${!old_Proj.equals(lproj)}" >
									<g:if test="${!old_Proj.equals("")}">
									
										<tr style="background-color: #f8f8f8;">
											<td><strong><g:message code="etech.report.expense.code.label" default="Expense Code"/></strong></td>
											<td colspan="5">${oldExpenseInstance[1]} </td>
											<td>${oldExpenseInstance[2]}</td>
										</tr>
										<tr style="background-color: #cdcdcd;">
											<td class="report-green-txt"><strong><g:message code="etech.report.expenes.project.total.label" default="Project Total"/></strong></td>											
											<td colspan="5">${oldProjectInstance[0]}</td>
											<td>${oldProjectInstance[1]}</td>											
										</tr>
										<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>	
									</g:if>
									
									<tr class="report-tr">										
										<td colspan="7">											
											<span class="marginright20"><strong><g:message code="etech.report.project.label" default="Project"/>:</strong>
											${projectByExpenseInst?.project?.code}</span>
											<span class="marginright20">${projectByExpenseInst?.project?.name}</span>
											<span class="marginright20"><strong><g:message code="etech.report.customer.label" default="Customer"/>:</strong>
											${projectByExpenseInst?.project?.customer?.code ?: '' }</span>
											<span class="marginright20"><strong><g:message code="etech.report.exec.label" default="Exec"/>:</strong>
											${projectByExpenseInst?.project?.accExecutive?.code}</span>
										</td>
									</tr>	
									<tr class="report-dark">
											<td><g:message code="etech.report.expense.expense.code.label" default="Expense Code"/></td>
											<td><g:message code="etech.report.expense.date.label" default="Date"/></td>
											<td><g:message code="etech.report.expense.vendor.label" default="Vendor#"/></td>
											<td><g:message code="etech.report.expense.vendor.name.label" default="Vendor Name"/></td>
											<td><g:message code="etech.report.expense.gl.acct.label" default="GL Account"/></td>
											<td><g:message code="etech.report.expense.invoice.label" default="Invoice#"/></td>
											<td><g:message code="etech.report.expense.amount.label" default="Amount"/></td>																
										</tr>
								</g:if> 
								
								<g:set value="${projectByExpenseInst?.expense?.code }" var="lExp"></g:set>								
								<g:if test="${old_Exp != lExp && old_Proj == lproj}">									
									<tr style="background-color: #f8f8f8;">
										<td><strong><g:message code="etech.report.expense.code.label" default="Expense Code"/></strong></td>
										<td colspan="5">${oldExpenseInstance[1]} </td>
										<td>${oldExpenseInstance[2]}</td>
									</tr>	
								</g:if>
								<g:if test="${params?.includeDetail == 'true' }">	
									<tr>
										<td>${projectByExpenseInst?.expense?.code}</td>
										<td>${DateFormatUtils.getStringFromDate(projectByExpenseInst?.postedDate)}</td>
										<td>${projectByExpenseInst?.vendorId}</td>
										<td>${projectByExpenseInst?.name1}</td>
										<td>${projectByExpenseInst?.account}</td>
										<td>${projectByExpenseInst?.invoiceId}</td>											
										<td>${projectByExpenseInst?.monetaryAmount}</td>
				 					</tr>	
								</g:if>	
								<g:set value="${lproj }" var="old_Proj"></g:set>
								<g:set value="${lExp}" var="old_Exp"></g:set>								 
								<g:set value="${projectByExpenseSubTotalMap?.get(old_Proj+'_'+old_Exp)}" var="oldExpenseInstance"></g:set>
								<g:set value="${projectSubTotalMap?.get(old_Proj)}" var="oldProjectInstance"></g:set>
							</g:each>					
						</g:if>
						<g:else>
							<g:each in="${projectByExpenseReporList}" status="i" var="projectByExpenseInst">																		 
								<g:set value="${projectByExpenseInst[0] }" var="lproj"></g:set>								 
								<g:if test="${!old_Proj.equals(lproj)}" >
									<g:if test="${!old_Proj.equals("")}">
									
										<tr style="background-color: #f8f8f8;">
											<td><strong><g:message code="etech.report.expense.code.label" default="Expense Code"/></strong></td>
											<td colspan="5">${oldExpenseInstance[1]} </td>
											<td>${oldExpenseInstance[2]}</td>
										</tr>
										<tr style="background-color: #cdcdcd;">
											<td class="report-green-txt"><strong><g:message code="etech.report.expenes.project.total.label" default="Project Total"/></strong></td>											
											<td colspan="5">${oldProjectInstance[0]}</td>
											<td>${oldProjectInstance[1]}</td>											
										</tr>
										<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>	
									</g:if>
									
									<tr class="report-tr">										
										<td colspan="7">											
											<span class="marginright20"><strong><g:message code="etech.report.project.label" default="Project"/>:</strong>
											${projectByExpenseInst[0]}</span>											
											<span class="marginright20">${projectByExpenseInst[3]}</span>
											<span class="marginright20"><strong><g:message code="etech.report.customer.label" default="Customer"/>:</strong>
											${projectByExpenseInst[4] ?: '' }</span>
											<span class="marginright20"><strong><g:message code="etech.report.exec.label" default="Exec"/>:</strong>
											${projectByExpenseInst[5]}</span>
										</td>
									</tr>									
								</g:if> 
								
								<g:set value="${projectByExpenseInst[1] }" var="lExp"></g:set>								
								<g:if test="${old_Exp != lExp && old_Proj == lproj}">									
									<tr style="background-color: #f8f8f8;">
										<td><strong><g:message code="etech.report.expense.code.label" default="Expense Code"/></strong></td>
										<td colspan="5">${oldExpenseInstance[1]} </td>
										<td>${oldExpenseInstance[2]}</td>
									</tr>	
								</g:if>								
								<g:set value="${lproj }" var="old_Proj"></g:set>
								<g:set value="${lExp}" var="old_Exp"></g:set>								 
								<g:set value="${projectByExpenseInst}" var="oldExpenseInstance"></g:set>
								<g:set value="${projectSubTotalMap?.get(old_Proj)}" var="oldProjectInstance"></g:set>
							</g:each>	
						
						
						</g:else>
							<g:if test="${lProj.equals("")}">
								<tr class="report-dark">
									<td><g:message code="etech.report.expense.expense.code.label" default="Expense Code"/></td>
									<td><g:message code="etech.report.expense.date.label" default="Date"/></td>
									<td><g:message code="etech.report.expense.vendor.label" default="Vendor#"/></td>
									<td><g:message code="etech.report.expense.vendor.name.label" default="Vendor Name"/></td>
									<td><g:message code="etech.report.expense.gl.acct.label" default="GL Account"/></td>
									<td><g:message code="etech.report.expense.invoice.label" default="Invoice#"/></td>
									<td><g:message code="etech.report.expense.amount.label" default="Amount"/></td>																
								</tr>
							</g:if>
							
								<tr style="background-color: #f8f8f8;">
									<td><strong><g:message code="etech.report.expense.code.label" default="Expense Code"/></strong></td>
									<td colspan="5">${oldExpenseInstance[1]} </td>
									<td>${oldExpenseInstance[2]}</td>
								</tr>
							
							<g:if test="${oldProjectInstance}">
								<tr style="background-color: #cdcdcd;">
									<td class="report-green-txt"><strong><g:message code="etech.report.expenes.project.total.label" default="Project Total"/></strong></td>											
									<td colspan="5">${oldProjectInstance[0]}</td>
									<td>${oldProjectInstance[1]}</td>											
								</tr>
							</g:if>
													
							<tr><td colspan="10" class="border-left-right-none" height="30"></td></tr>
							<g:if test="${reportSubTotal}">	
								<tr style="background-color: #e0e0e0;">								
									<td  colspan="6"><strong><g:message code="etech.report.expense.total.label" default="Report Total:"/></strong></td>
									<td>${reportSubTotal[0]}</td>
								</tr>
							</g:if>
							
						</g:if>
						<g:else>
							<tr>
								<td colspan="7" style="color: red;" ><g:message code="default.record.not.found" /></td>
							</tr>
						</g:else>
					</table>
					<div class="pagination margintopbottom15">
						<g:paginate total="${projectByExpenseReporCount ?: 0}"  params="${params}"/>
					</div>
				</div>
				<g:if test="${projectByExpenseReporList?.size() > 0}">
					<g:form controller="report"  action = "exportReport" method="post">
						<g:each var="reportParamsVar" in = "${params}">
							<g:if test="${reportParamsVar.key != 'action' && reportParamsVar.key != 'action'  && reportParamsVar.key !='controller'  && reportParamsVar.key !='action'}">
								<g:hiddenField  name="${reportParamsVar.key}" value="${reportParamsVar.value}"/>
							</g:if>
						</g:each>
						<g:hiddenField name="reportName" value="${org.solcorp.etech.Constants.PROJECT_EXPENSE_REPORT_NAME}"/>
						<a  class="btn btn-default btn-sm" onclick="exportExcel(this)"><i class="fa fa-file-excel-o" ></i> Export Excel</a>
					</g:form>						 
					<br/>
				</g:if>		
				<g:render template="/common/returnLink" model="['returnAction': 'projectExpensesByExpenseCode', 'returnController': 'report', 'messageCode':'etech.report.return.filter', 'defaultMessage': 'Return to Report Filter']"></g:render>										
			</div>		
		</div>
		<script type="text/javascript">	
			function exportExcel(link){
		        link.parentNode.submit();
		        return false;
			}
		</script>
	</body>
</html>