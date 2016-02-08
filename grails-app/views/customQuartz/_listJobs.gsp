<%@ page import="org.quartz.Trigger" %>
<div class="col-xs-12" id="">
          			<div class="table-responsive list-table">
						<table class="table table-hover">
						<thead>
				<tr>
					<th class="text-left">Name</th>
					<th class="text-center">Next Trigger Time</th>
					<th class="text-left">Started On</th>
					<th class="text-left">Completed On</th>
					<th class="text-left">Status</th>
					<th class="text-left">Executed By</th>
					<th class="text-left">Batch Param</th>
					<th class="text-left" nowrap>Actions</th>
				</tr>
				</thead>
				<tbody>
				<g:each in="${jobs}" status="i" var="job">
					<%int flg = 0; %>
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td class="text-left text-nowrap">
						<g:if test="${jobRegisterCriteriaResults != null && jobRegisterCriteriaResults?.size() > 0}">
                        	<g:each in="${jobRegisterCriteriaResults}" status="j" var="jobRegisterDetails">
                        	<g:if test="${jobRegisterDetails?.className?.toString()?.trim()?.equals(job?.name?.toString()?.trim())}">
								<g:if test="${jobRegisterDetails?.displayName}">
									<g:link controller="customQuartz" action="getJobHistory" params="[className: job?.name]">${jobRegisterDetails?.displayName}
									</g:link>
								</g:if>
							</g:if>	
							</g:each>
						</g:if>
						
							<g:if test="${(job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)).equals('ImportLaborTransactionsJob') || (job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)).equals('ImportExpensesJob')}">
							
							<g:if test="${jobRegisterCriteriaResults != null && jobRegisterCriteriaResults?.size() > 0}">
                        	<g:each in="${jobRegisterCriteriaResults}" status="j" var="jobRegisterDetails">
                        	<g:if test="${jobRegisterDetails?.className?.toString()?.trim()?.equals(job?.name?.toString()?.trim())}">
								<g:if test="${jobRegisterDetails?.dataRangeFrom}">
									<div>
										Data From: <g:formatDate format="MM/dd/yyyy" date="${jobRegisterDetails?.dataRangeFrom}"/>
									</div>
								</g:if>
								<g:if test="${jobRegisterDetails?.dataRangeTo}">
									<div>
										Data To: <g:formatDate format="MM/dd/yyyy" date="${jobRegisterDetails?.dataRangeTo}"/>
									</div>
								</g:if>
							</g:if></g:each></g:if>
								
							</g:if>
						</td>
							<g:if test="${scheduler?.isInStandbyMode() || job?.triggerStatus == Trigger?.TriggerState?.PAUSED}">
                                <td class="text-center hasCountdown countdown_amount">Paused</td>
                            </g:if>
                            <g:elseif test="${job?.trigger?.nextFireTime?.time}">
                                <td class="text-center quartz-countdown" data-next-run="${job?.trigger?.nextFireTime?.time ?: ""}">
                                	<g:formatDate format="MM/dd/yyyy HH:mm:ss z" date="${job.trigger?.nextFireTime}"/>
                                </td>
                            </g:elseif>
                            <g:else>
                            	<td>Stopped</td>
                            </g:else>
                        
                            
                        <g:if test="${jobRegisterCriteriaResults != null && jobRegisterCriteriaResults?.size() > 0}">
                        	<g:each in="${jobRegisterCriteriaResults}" status="j" var="jobRegisterDetails">
                        	<g:if test="${jobRegisterDetails?.className?.toString()?.trim()?.equals(job?.name?.toString()?.trim())}">
                        	
                        		<g:if test="${jobRegisterDetails?.status?.toString()?.trim()?.equals('Fail')}">
                        			<g:set var="fontColor" value="red-text"/>
                        		</g:if>
                        		<g:elseif test="${jobRegisterDetails?.status?.toString()?.trim()?.equals('Running')}">
                        			<g:set var="fontColor" value="blue-text" />
                        		</g:elseif>
                        		<g:else>
                        			<g:set var="fontColor" value="black-text" />
                        		</g:else>
                        		<td class="text-left ${fontColor}">
                        			<g:if test="${jobRegisterDetails?.startDate}">
                        				<g:formatDate format="MM/dd/yyyy HH:mm:ss" date="${jobRegisterDetails?.startDate}"/>
                        			</g:if>
                        			<g:else>
                        				N/A
                        			</g:else>
                        		</td>
                        		<td class="text-left ${fontColor}">
                        			<g:if test="${jobRegisterDetails?.endDate}">
                        				<g:formatDate format="MM/dd/yyyy HH:mm:ss" date="${jobRegisterDetails?.endDate}"/>
                        			</g:if>
                        			<g:else>
                        				N/A
                        			</g:else>
                        		</td>
                        		<td class="text-left ${fontColor}">${jobRegisterDetails?.status?:'N/A'}</td>
                        		<td class="text-left ${fontColor}">${jobRegisterDetails?.initiatedBy?.username?:'N/A'}
                        			<g:if test="${jobRegisterDetails?.initiatedBy?.employee}">
                        				(${jobRegisterDetails?.initiatedBy?.employee?.getEmployeeName()})
                        			</g:if>
                        		</td>
                        		<%flg = 1; %>
                        	</g:if>
                        </g:each>
                        </g:if>
                        
                        <g:if test="${flg == 1}">
                        	<%flg = 0; %>
                        </g:if>
                        <g:else>
                        	<td class="text-left">N/A</td>
                        	<td class="text-left">N/A</td>
                        	<td class="text-left">N/A</td>
                        	<td class="text-left">N/A</td>
                        </g:else>
                        
                        <td>
                        	<g:if test="${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1).equalsIgnoreCase('ImportLaborTransactionsJob')}">
                        		<sup><font style="size: xx-small;color: blue;">Records Starting From:</font></sup>
                        		<g:render template="/common/dateComponent" model="['fieldName': 'laborTxnJobStDate', 'fieldValue': '']"></g:render>
                        	</g:if>
                        	<g:elseif test="${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1).equalsIgnoreCase('ImportExpensesJob')}">
                        		<sup><font style="size: xx-small;color: blue;">Records Starting From:</font></sup>
                        		<g:render template="/common/dateComponent" model="['fieldName': 'expenseStartDate', 'fieldValue': '']"></g:render>
                        	</g:elseif>
                        	<g:else>
                        		N/A
                        	</g:else>
                        </td>
                        <td class="text-left" nowrap>
                           		 <g:if test="${job?.status != 'running'}">
                                    <g:if test="${job?.trigger}">
                                        <a href="<g:createLink action="stop" params="[jobName:job?.name, triggerName:job?.trigger?.name, triggerGroup:job?.trigger?.group]"/>" onClick="if (!confirm('Are you sure you want to stop the job: ${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)}?')) {return false;}"><i class="fa fa-stop red-icon table-icon"></i></a>
                                        <g:if test="${job?.triggerStatus == Trigger?.TriggerState?.PAUSED}">
                                            <a href="<g:createLink action="resume" params="[jobName:job?.name, jobGroup:job?.group]"/>" onClick="if (!confirm('Are you sure you want to resume the job: ${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)}?')) {return false;}"><i class="fa fa-play-circle table-icon"></i></a>
                                        </g:if>
                                        <g:elseif test="${job?.trigger?.mayFireAgain()}">
                                            <a href="<g:createLink action="pause" params="[jobName:job?.name, jobGroup:job?.group]"/>" onClick="if (!confirm('Are you sure you want to pause the job: ${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)}?')) {return false;}"><i class="fa fa-pause pause-icon table-icon"></i></a>
                                        </g:elseif>
                                    </g:if>
                                    <g:else>
                                        <a href="<g:createLink action="start" params="[jobName:job?.name, jobGroup:job?.group]"/>" onClick="if (!confirm('Are you sure you want to schedule the job: ${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)}?')) {return false;}"><i class="fa fa-play-circle-o table-icon"></i></a>
                                    </g:else>
                                    <g:if test="${job?.triggerStatus != Trigger?.TriggerState?.BLOCKED}">
                                    	<g:if test="${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1).equalsIgnoreCase('ImportLaborTransactionsJob')}">
                                    		<a href="#" onClick="return test('${job?.name}', '${job?.group}', '${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)}');"><i class="fa fa-play table-icon"></i></a>
                                    	</g:if>
                                    	<g:elseif test="${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1).equalsIgnoreCase('ImportExpensesJob')}">
                                    		<a href="#" onClick="return expenseStDate('${job?.name}', '${job?.group}', '${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)}');"><i class="fa fa-play table-icon"></i></a>
                                    	</g:elseif>
                                    	<g:else>
                                    		<a href="<g:createLink action="runNow" params="[jobName:job?.name, jobGroup:job?.group]" />" onClick="if (!confirm('Are you sure you want to run the job: ${job?.name?.toString()?.trim()?.substring(job?.name?.toString()?.trim()?.lastIndexOf('.')+1)}?')) {return false;}"><i class="fa fa-play table-icon"></i></a>
                                    	</g:else>
                                    </g:if>
                           		</g:if>
                        	</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			</div>
			</div>
			
			<g:unless test="${grailsApplication.config.quartz.monitor.showCountdown == false}">
            <g:javascript src="customQuartz/jquery.countdown.js"/>
            <g:javascript src="customQuartz/jquery.color.js"/>
        </g:unless>
        <g:unless test="${grailsApplication.config.quartz.monitor.showTickingClock == false}">
            <g:javascript src="customQuartz/jquery.clock.js"/>
        </g:unless>
        <g:javascript src="customQuartz/quartz-monitor.js"/>
        <script>
			function test(jobName, jobGroup, jobNameWithOutClass){
				if (!confirm("Are you sure you want to run the job: "+jobNameWithOutClass+" ?")){
					return false;
				}else{
					var url = "${createLink(controller:'customQuartz', action: 'runNow')}";
					url = url+"?jobName="+jobName+"&jobGroup="+jobGroup+"&laborTxnJobStDate="+laborTxnJobStDate.value;
					document.location.href = url;
				}
			}

			function expenseStDate(jobName, jobGroup, jobNameWithOutClass){
				if (!confirm("Are you sure you want to run the job: "+jobNameWithOutClass+" ?")){
					return false;
				}else{
					var url = "${createLink(controller:'customQuartz', action: 'runNow')}";
					url = url+"?jobName="+jobName+"&jobGroup="+jobGroup+"&expenseStartDate="+expenseStartDate.value;
					document.location.href = url;
				}
			}
		</script>