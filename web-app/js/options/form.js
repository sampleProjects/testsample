function validateForm() {
	
	if(document.company.permitOnlyDefaultOperation.value == "N")
	{
		if( document.company.operation.value == "N") {
			alert("Permit Default Operation Only should be Yes or Operation should be Yes");
			return false;
		}
	}
	
	if (document.company.name.value == "") {
		alert("Company Name is required.");
		document.company.name.focus();
		return false;
	}
	
	if (document.company.addressLine1.value == "") {
		alert("Address line 1 is required.");
		document.company.addressLine1.focus();
		return false;
	}
	
	if (document.company.city.value == "") {
			alert("City name is required.");
			document.company.city.focus();
			return false;
	}
	
	if (document.company.country.value == "") {
		alert("Country is required.");
		document.company.country.focus();
		return false;
	}
	
}

function checkDefOnlyOption() {

	if( document.company.permitOnlyDefaultOperation.value == "N")
    {
    	if( document.company.operation.value == "N")
            alert("Permit Default Operation Only should be Yes or Operation should be Yes");
            return false;
    }
    return true;
    
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

function isNumber(field) {
    
	if (isNaN(field.value)) {
        alert("Invalid Number, enter again.");
	   field.select();
        field.focus();
        return false;
    }
    else {
        return true;
    }
	
}
