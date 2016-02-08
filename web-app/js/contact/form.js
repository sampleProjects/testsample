function confirmDelete() {
	var cd= confirm("This will delete the record.\n Are you sure?");
	if(!cd) {
		alert("The record is not deleted");
		return false;
	}
}

function validatePhone(_phone) {
   	var fldVal = _phone;
   	var i = 0;
   	var nphone = "";
	   
	for (i = 0; i < fldVal.value.length; i++) {
   		if (fldVal.value != "") {
   			if(!isNaN(fldVal.value.charAt(i))) {
				nphone = nphone + fldVal.value.charAt(i);
			}
		 }
	}

	if(fldVal == null) return true;
	if(fldVal.value == "") return true;
	
	if(isNaN(nphone) || (nphone.length != 10)) {
		fldVal.focus();
		fldVal.select();
		alert("Enter a valid 10-digit phone number");
		return false;
	}
	else {
		return false;
	}
}

function validateFax(_phone) {
   	var fldVal = _phone;
   	var i = 0;
   	var nphone = "";
	   
	for (i = 0; i < fldVal.value.length; i++) {
   		if (fldVal.value != "") {
   			if(!isNaN(fldVal.value.charAt(i))) {
				nphone = nphone + fldVal.value.charAt(i);
			}
		 }
	}

	if(fldVal == null) return true;
	if(fldVal.value == "") return true;
	
	if(isNaN(nphone) || (nphone.length != 10)) {
		fldVal.focus();
		fldVal.select();
		alert("Enter a valid 10-digit Fax number");
		return false;
	}
	else {
		return false;
	}
}