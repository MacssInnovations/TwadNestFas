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

function manipulate(xmlrequest) {
//alert("manipulate");
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			//alert("manipulate-command:--->>>"+command);

			if (command == "saveFun") {
				//alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			}
		}
	}
}

function saveFunc(path) {
	//alert(path);
	var cmbFinancial_Year = document.getElementById("cmbFinancial_Year").value;
	
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month >= 10) {
		var date = (day + "/" + month + "/" + year);
	} else {
		var date = (day + "/0" + month + "/" + year);
	}
	if(cmbFinancial_Year == "")
	{
		alert("Select CashBook Year in the Field");
	}
	else
	{
	var url = path	+ "/AnnualTBClosure?command=save&cmbFinancial_Year=" + cmbFinancial_Year + "&date=" + date;
	
//	alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	}
}

function saveFunc1(baseResponse) {
	//alert("getDetails1(baseResponse)");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(flag == "Exist")
	{
		alert("Annual TB Closure Already Freezed");
	}
	else if(flag == "success")
	{
		alert("Annual TB Closure Freezed Successfully");
	}
	else
	{
		alert("Record Insertion Failed");
	}
}

function exitfun() {	
	window.close();
}