<%@page import="org.solcorp.etech.Constants"%>
<%@page import="org.solcorp.etech.utils.DateFormatUtils"%>

<div class='input-group'>
	<g:textField class="form-control bootstrapDatepicker ${cssClass}" name="${fieldName}" value="${fieldValue ? DateFormatUtils.getStringFromDate(fieldValue) : ''}" placeholder="MM/DD/YYYY"/>   	
</div>	
<script>
$(document).ready(function() {

	var curr = new Date();
	var minimumYearDate = new Date(curr); ;
	 
	minimumYearDate.setFullYear(curr.getFullYear() - ${Constants.MINUMUM_PREVIOUS_YEAR_COUNT});

    //$('.bootstrapDatepicker').data("DateTimePicker").minDate(minimumYearDate);
    
    $('.bootstrapDatepicker').datetimepicker({
    	format: 'MM/DD/YYYY'
    });
	
    
});
</script>