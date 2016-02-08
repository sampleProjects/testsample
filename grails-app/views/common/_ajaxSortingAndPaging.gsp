<g:if test="${searchParams == 'true'}">

	<div id="${searchAction}_AjaxPagingParams">
	
		<g:each var="popupFieldParamMap" in="${params}">
		
			<g:if test="${popupFieldParamMap?.key != 'max' && popupFieldParamMap?.key != 'offset'}">
			
				<g:hiddenField name="${popupFieldParamMap?.key}" value="${popupFieldParamMap?.value?:''}"/>
				
			</g:if>  
			
		</g:each>
		
	</div>
	
</g:if>
 <script> 
	$(document).ready(function() {		
 
	$(".step, .nextLink, .prevLink").click(function(e) {
		e.preventDefault();

		if($(this).prop("href")==null){
			return false;
		}
		var href= $(this).attr("href");
		var max = getParamValue(href, "max");
		
		var offset = getParamValue(href, "offset");

		var sort;
		var order;
		
		if(href.indexOf("sort") > 0) {
			sort = getParamValue(href, "sort");
			order = getParamValue(href, "order");
		} else if(href.indexOf("_sort") > 0) {
			sort = getParamValue(href, "_sort");
			order = getParamValue(href, "_order");	
		}

		var fromPagination = true;
		performSearch(offset, max, sort, order, fromPagination);
	});

	$(".sortable a").click(function(e) {
		e.preventDefault();
				
		var href= $(this).attr("href");
		var max = getParamValue(href, "max");
		var offset = 0;

		var sort;
		var order;
		
		if(href.indexOf("sort") > 0) {
			sort = getParamValue(href, "sort");
			order = getParamValue(href, "order");
		} else if(href.indexOf("_sort") > 0) {
			sort = getParamValue(href, "_sort");
			order = getParamValue(href, "_order");	
		}

		var fromPagination = false;
		performSearch(offset, max, sort, order, fromPagination);
	});

	function performSearch(offsetIndex, firstAndIndex, sort, order, fromPagination) {
		var divIdToUpdate = 'listGridDiv';
		 
		if('${divIdToUpdate}') {
			divIdToUpdate = "${divIdToUpdate}";
		}
		
		var searchAction = '${searchAction}';
		var searchController = '${searchController}';
		
		var fixedData = 'max='+firstAndIndex+'&fromPagination='+fromPagination+'&offset='+offsetIndex+'&_sort='+sort+'&_order='+order;	
			
		if($("#${searchAction}_AjaxPagingParams").length) {
			data = $("#${searchAction}_AjaxPagingParams :input").serialize();			
			data = data +'&'+fixedData
		}else {
			data = fixedData
		}		
		<g:remoteFunction controller="${searchController}" action="${searchAction}" update="'+divIdToUpdate+'" params="''+data" />	 
		 
	}
	function getParamValue(  url, name ) {
		  if (!url) url = location.href
		  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
		  var regexS = "[\\?&]"+name+"=([^&#]*)";
		  var regex = new RegExp( regexS );
		  var results = regex.exec( url );
		  return results == null ? null : results[1];
	}
});
</script>