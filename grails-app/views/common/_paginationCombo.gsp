<%@ page import="org.solcorp.etech.Pagination" %>
<div class="text-right margintopbottom15">	

	<span class="marginright15"><g:message code="etech.pagination.total.records.label" default="Total Records:" /> ${totalRecord ?: 0}</span>	
	
	<g:message code="etech.pagination.show.label" default="Show" />
	
	<g:select name="paginationParam" id="paginationParam" from="${Pagination.list()}" optionKey="value" optionValue="value" value="${params.max}"
				onchange="pagination(this.value)" class="marginleftright5"/><span class="paginationComboMargin"><g:message code="etech.pagination.records.label" default="Records" /></span> 
</div>
				
<div id="${searchAction}_paginationCombo">

	<g:each var="paramsMap" in="${params}">
	
		<g:if test="${paramsMap?.key != 'max' && paramsMap?.key != 'offset'}">
		
			<g:hiddenField name="${paramsMap?.key}" value="${paramsMap?.value?:''}"/>
			
		</g:if>
		  
	</g:each>
	
</div>

<script type="text/javascript">
$(document).ready(function(){
	var paginationCookieIsExist = getCookie('maxPaginationRecords');
    if(paginationCookieIsExist==null) {
    	setCookie('maxPaginationRecords',$('#paginationParam').val());  	
	}else{
		$('#paginationParam').val(paginationCookieIsExist);
	}
});

function pagination(max){
	setCookie('maxPaginationRecords',max);
	var divIdToUpdate = 'listGridDiv';
	 
	if('${divIdToUpdate}') {
		divIdToUpdate = "${divIdToUpdate}";
	}
	
	var searchAction = '${searchAction}';
	
	var searchController = '${searchController}';
	
	var fixedData = 'max='+max;
			
	if($("#${searchAction}_paginationCombo").length){
		
		data = $("#${searchAction}_paginationCombo :input").serialize();
					
		data = data +'&'+fixedData
		
	}else {
		
		data = fixedData
	}		
	<g:remoteFunction controller="${searchController}" action="${searchAction}" update="'+divIdToUpdate+'" params="''+data" />	 
}
</script>