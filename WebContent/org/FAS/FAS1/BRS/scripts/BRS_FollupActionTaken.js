var seq = 0;
var seq1 = 0;
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

function ListAll(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var txtFromDate = document.getElementById("txtFromDate").value;
	var txtToDate = document.getElementById("txtToDate").value;
	var browser = navigator.appName;
	if (browser == "Netscape") {
		var dd1 = txtFromDate.split('/');
		txtFromDate = dd1[1] + "/" + dd1[0] + "/" + dd1[2];

		var dd2 = txtToDate.split('/');
		txtToDate = dd2[1] + "/" + dd2[0] + "/" + dd2[2];

	}
	var a = txtFromDate.split('/');
	var b = txtToDate.split('/');

	var eDate = new Date(a[2], a[0] - 1, a[1]);
	var rDate = new Date(b[2], b[0] - 1, b[1]);

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_ActionTaken.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_ActionTaken.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Enter Bank Account No in the Field");
		document.frmBRSList.cmbBankAccNo.focus();
	} else if (document.getElementById("txtFromDate").value == "") {
		alert("Enter From Date in the Field");
	} else if (document.getElementById("txtToDate").value == "") {
		alert("Enter To Date in the Field");
	} else if (eDate >= rDate) {
		alert("To Date must be greater than From Date");
		return 0;
	} else {
		var url = path
				+ "/BRS_FollupActionTaken?command=ListAll&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&txtFromDate="
				+ txtFromDate + "&txtToDate=" + txtToDate;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function manipulate(xmlrequest) {
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
			//alert("manipulate-command:--->>>"+command);
			if (command == "ListAll") {
				//alert("manipulate");
				LoadList(baseResponse);
			}
		}
	}
}
function LoadList(baseResponse) {	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);		

	if (flag == "success") {
		var len = baseResponse.getElementsByTagName("EntryDate").length;
		if (len !=0) {
		for ( var k = 0; k < len; k++) {
			var slno = baseResponse.getElementsByTagName("SerialNumber")[k].firstChild.nodeValue;
			var slno1 = baseResponse.getElementsByTagName("SerialNumber")[k].firstChild.nodeValue;
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
			var TwadNonTwad1 = baseResponse.getElementsByTagName("TwadNonTwad")[k].firstChild.nodeValue;
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
			var ActionNo1 = baseResponse.getElementsByTagName("ActionNo")[k].firstChild.nodeValue;
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

			var tbody = document.getElementById("grid_body");
			var table = document.getElementById("mytable");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

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

			var cell = document.createElement("TD");
			var anc = document.createElement("A");			
			var url = "javascript:PreviousList('" + slno1 + "','" + ActionNo1 + "','" + TwadNonTwad1+ "')";

			anc.href = url;
			var txtedit = document.createTextNode("LIST");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);
			
			tbody.appendChild(mycurrent_row);
			seq++;
		}
		}else
		{
			alert("Record Does Not Exist");
		}

	} else if (flag == "failure") {
		alert("Fail to Load Grid Values");
	}
}

function PreviousList(slno,ActionNo,TwadNonTwad)
{	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;

        winemp= window.open("BRS_FollupActionTaken_List_Details.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&TwadNonTwad="+TwadNonTwad+"&slno="+slno+"&ActionNo="+ActionNo,"list","status=1,height=450,width=900,resizable=YES, scrollbars=yes");	
        winemp.moveTo(100,100);  
        winemp.focus();
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
	LoadAccountingUnitID('LIST_ALL_UNITS');		
	document.getElementById("cmbBankAccNo").value="";
	document.getElementById("txtFromDate").value="";
	document.getElementById("txtToDate").value="";

          var today= new Date(); 
          var day=today.getDate();
          var month=today.getMonth();
          month=month+1;
          var year=today.getYear();
          if(year < 1900) year += 1900;
                     
         document.frmBRS_ActionTaken.txtCB_Year.value=year;
         document.frmBRS_ActionTaken.txtCB_Month.value=month;

	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	for ( var i = 0; i < rowcount; i++) {
		var r = i.rowIndex;
		tbody.deleteRow(r);
	}
}

function exitfun() {
	window.close();
}