function load() {
	document.getElementById("ah").value = " ";
	document.getElementById("txtfin_year").value = "";
	document.getElementById("btnSubmit").disabled = true;

}

/**
 * XML REQUEST
 */

function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function annualReport() {
	if (checkNull()) {

		return true;
	}
}

function reportResponse(request) {
	if (request.readyState == 4) {
		if (request.status == 200) {
			var baseResponse = request.responseXML
					.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "load_Account_Head") {
				load_Account_Head(baseResponse);
			}
		}
	}
}

function checkNull() {
	if (document.getElementById("txtfin_year").value == "") {
		alert("Select the Account Head code");
		return false;
	}
	if (document.getElementById("ah").value == "AccountHeadMissed") {
		alert("AccountHeadMissed");
		return false;
	}

	return true;

}

function doFunction(Command, param) {
	if (Command == "load_Account_Details") {
		document.getElementById("btnSubmit").disabled = true;
		var res = param.split("-")
		var url = "../../../../../Accounts_Data.view?Command=load_Account_Head&from_year="
				+ res[0] + "&to_year=" + res[1];
		// alert(url)
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		}
		req.send(null);

	}

}

/**
 * HANDLE RESPONSE
 */

function handleResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) { // alert(req.responseText);
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "load_Account_Head") {
				load_Account_Head(baseResponse);
			}

		}
	}
}

/**
 * LOAD ACCOUNT HEAD
 */

function load_Account_Head(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	/* var txtVoucher_No=document.getElementById("txtVoucher_No"); */
	if (flag == "success") {
		document.getElementById("ah").value = " ";
		document.getElementById("btnSubmit").disabled = false;

	} else if (flag == "failure") {
		var headCode = baseResponse.getElementsByTagName("account_head_code");

		var items_id = new Array();
		/* var Rec_No=baseResponse.getElementsByTagName("Rec_No"); */
		var headCode = baseResponse.getElementsByTagName("account_head_code");
		for (var k = 0; k < headCode.length; k++) {
			items_id[k] = baseResponse
					.getElementsByTagName("account_head_code")[k].firstChild.nodeValue;

		}
		document.getElementById("ah").value = "AccountHeadMissed";
		document.getElementById("txtfin_year").value = ""
		alert("The following AccountHeadCodes not available in AnnualGroupingFile: "
				+ items_id);
		document.getElementById("btnSubmit").disabled = true;

		return false;
	}
}