<span>
	<g:if test="${linkId}">
		 <g:link action="${returnAction}" controller="${returnController}" id="${linkId}" params="${linkParam}" class="btn btn-success ${isDirtyCheck?:''}">
		 	<i class="fa fa-chevron-left marginright5"></i> 
		 	<g:message code="${messageCode ?: 'default.return'}" default="${defaultMessage ?: 'Return'}" />
	 	 </g:link>
	</g:if>
	<g:elseif test="${linkParam}">
		 <g:link action="${returnAction}" controller="${returnController}" params="${linkParam}" class="btn btn-success ${isDirtyCheck?:''}">
		 	<i class="fa fa-chevron-left marginright5"></i> 
		 	<g:message code="${messageCode ?: 'default.return'}" default="${defaultMessage ?: 'Return'}" />
	 	 </g:link>
	</g:elseif>
	<g:else>
		 <g:link action="${returnAction}" controller="${returnController}" class="btn btn-success ${isDirtyCheck?:''}">
		 	<i class="fa fa-chevron-left marginright5"></i> 
		 	<g:message code="${messageCode ?: 'default.return'}" default="${defaultMessage ?: 'Return'}" />
	 	 </g:link>
	</g:else>
</span>