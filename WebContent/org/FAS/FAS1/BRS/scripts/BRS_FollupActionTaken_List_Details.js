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

function initialLoad(path, cboAcc_UnitCode, cboOffice_code, cboCashBook_Year,
		cboCashBook_Month, cmbBankAccNo,TwadNonTwad,slno, ActionNo) {
	//alert(path);
	
	var url = path
			+ "/BRS_FollupActionTaken_List_Details?command=getPreviousDetails&cmbBankAccNo="
			+ cmbBankAccNo + "&cmbOffice_code=" + cboOffice_code
			+ "&cmbAcc_UnitCode=" + cboAcc_UnitCode + "&txtCB_Year="
			+ cboCashBook_Year + "&txtCB_Month=" + cboCashBook_Month+ "&TwadNonTwad=" + TwadNonTwad
			+ "&slno=" + slno + "&ActionNo=" + ActionNo;

	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			//alert(command);

			if (command == "getPreviousDetails") {
				//alert("manipulate");
				addRow(baseResponse);
			}
		}
	}
}

function addRow(baseResponse) {
	//alert("addRow");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);		

	if (flag == "success") {
		var len = baseResponse.getElementsByTagName("EntryDate").length;
		if (len !=0) {
		for ( var k = 0; k < len; k++) {
			var slno = baseResponse.getElementsByTagName("SerialNumber")[k].firstChild.nodeValue;			
			//alert(slno);
			var EntryDate = baseResponse.getElementsByTagName("EntryDate")[k].firstChild.nodeValue;
			if (EntryDate == 'null') {
				EntryDate = "-";
			}
			var ActionDate = baseResponse.getElementsByTagName("ActionDate")[k].firstChild.nodeValue;
			if (ActionDate == 'null') {
				ActionDate = "-";
			}
			var TwadNonTwad = baseResponse.getElementsByTagName("TwadNonTwad")[k].firstChild.nodeValue;			
			if (TwadNonTwad == 'null') {
				TwadNonTwad = "-";
			}
			var Doc_No = baseResponse.getElementsByTagName("Doc_No")[k].firstChild.nodeValue;
			if (Doc_No == 'null') {
				Doc_No = "-";
			}
			var DocType = baseResponse.getElementsByTagName("DocType")[k].firstChild.nodeValue;
			if (DocType == 'null') {
				DocType = "-";
			}
			var ActionNo = baseResponse.getElementsByTagName("ActionNo")[k].firstChild.nodeValue;			
			if (ActionNo == 'null') {
				ActionNo = "-";
			}
			var ActionTaken = baseResponse.getElementsByTagName("ActionTaken")[k].firstChild.nodeValue;			
			if (ActionTaken == 'null') {
				ActionTaken = "-";
			}
			var Cheqe_or_DDNo = baseResponse
					.getElementsByTagName("Cheqe_or_DDNo")[k].firstChild.nodeValue;
			if (Cheqe_or_DDNo == 'null') {
				Cheqe_or_DDNo = "-";
			}
			var CRAmount = baseResponse.getElementsByTagName("CRAmount")[k].firstChild.nodeValue;
			if (CRAmount == 'null') {
				CRAmount = "-";
			}
			var DRAmount = baseResponse.getElementsByTagName("DRAmount")[k].firstChild.nodeValue;
			if (DRAmount == 'null') {
				DRAmount = "-";
			}

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			var cell8 = document.createElement("TD");
			cell8.align = "left";
			var slno = document.createTextNode(slno);
			cell8.appendChild(slno);
			mycurrent_row.appendChild(cell8);

			var cell81 = document.createElement("TD");
			cell81.align = "left";
			var Cheqe_or_DDNo = document.createTextNode(Cheqe_or_DDNo);
			cell81.appendChild(Cheqe_or_DDNo);
			mycurrent_row.appendChild(cell81);

			var cell82 = document.createElement("TD");
			cell82.align = "left";
			var CRAmount = document.createTextNode(CRAmount);
			cell82.appendChild(CRAmount);
			mycurrent_row.appendChild(cell82);

			var cell83 = document.createElement("TD");
			cell83.align = "left";
			var DRAmount = document.createTextNode(DRAmount);
			cell83.appendChild(DRAmount);
			mycurrent_row.appendChild(cell83);

			var cell2 = document.createElement("TD");
			cell2.align = "left";
			var EntryDate = document.createTextNode(EntryDate);
			cell2.appendChild(EntryDate);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			cell3.align = "left";
			var TwadNonTwad = document.createTextNode(TwadNonTwad);
			cell3.appendChild(TwadNonTwad);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			cell4.align = "left";
			var Doc_No = document.createTextNode(Doc_No);
			cell4.appendChild(Doc_No);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			cell5.align = "left";
			var DocType = document.createTextNode(DocType);
			cell5.appendChild(DocType);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			cell6.align = "left";
			var ActionNo = document.createTextNode(ActionNo);
			cell6.appendChild(ActionNo);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			cell7.align = "left";
			var ActionDate = document.createTextNode(ActionDate);
			cell7.appendChild(ActionDate);
			mycurrent_row.appendChild(cell7);

			var cell9 = document.createElement("TD");
			cell9.align = "left";
			var ActionTaken = document.createTextNode(ActionTaken);
			cell9.appendChild(ActionTaken);
			mycurrent_row.appendChild(cell9);

			tbody.appendChild(mycurrent_row);			
		}
		}else
		{
			alert("Record Does Not Exist");
		}
	} else if (flag == "failure") {
		alert("Fail to Load Grid Values");
	}
}

function exitfun(path) {	
	window.close();
}
