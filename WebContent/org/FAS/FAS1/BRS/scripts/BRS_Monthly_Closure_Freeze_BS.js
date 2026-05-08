//                 BRS_Monthly_Closure_Freeze_BS              //

function AjaxFunction() {
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "Add") {
				Add1(baseResponse);
			}
		}
	}
}

function Add(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;	
        var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;	
		
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_Monthly_Closure_Freeze.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_Monthly_Closure_Freeze.txtCB_Month.focus();
	}
        else if (cmbBankAccNo == "") {
		alert("Choose Bank Acc/No");
		document.frmBRS_Monthly_Closure_Freeze.cmbBankAccNo.focus();
	}
        
        else {

		var url = path+ "/BRS_Monthly_Closure_Freeze_BS?command=Add&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function Add1(baseResponse) {
	
	var proceed = baseResponse.getElementsByTagName("proceed")[0].firstChild.nodeValue;
	if(proceed=="stop")
		{
	
		alert("Please Generate Part-1,part-2A,part-2B,part-2C Report and then Freeze");
		return false;
				
		}
	else{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("BRS Freezing Done Successfully");
		refresh();
	} else if (flag == "Exist") {
		alert("Freezing Already Done");
		refresh();
	} 
        else if (flag == "NoRecord")
        {
                alert("BRS Details Are Not Entered for This CashBookYear and Month");
		refresh();
        }
        else {
		alert("Freezing Failed");
		refresh();
	}
  }
}

function numbersonly1(e, t) {
	
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false
	}
}

function refresh() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frmBRS_Monthly_Closure_Freeze.txtCB_Year.value = year;
	document.frmBRS_Monthly_Closure_Freeze.txtCB_Month.value = month;	
}

function exitfun() {
	window.close();
}

