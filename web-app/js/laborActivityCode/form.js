function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}


function isNumber(field) {
	try{
		if (isNaN(field.value)) {
			alert("Invalid Number, enter again.");
			field.select();
		    field.focus();
		    return false;
		}
		else if(field.value.toString().trim().length > 0){
			var x = parseFloat(field.value.toString());
			x = x.toFixed(2);
			field.value = x;
		}
	}
	catch(e){
		alert("Invalid Value, enter again.");
		field.select();
		field.focus();
		return false;
	}
}

function isNumberRound(field) {
	try{
		if (isNaN(field.value)) {
			alert("Invalid Number, enter again.");
			field.select();
		    field.focus();
		    return false;
		}
		else if(field.value.toString().trim().length > 0){
			var x = parseFloat(field.value.toString());
			x = Math.round(x);
			field.value = x;
		}
	}
	catch(e){
		alert("Invalid Value, enter again.");
		field.select();
		field.focus();
		return false;
	}
}