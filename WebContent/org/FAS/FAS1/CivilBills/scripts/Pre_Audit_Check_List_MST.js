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

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "ClearAll") {
				ClearAll1(baseResponse);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			}else if (command == "getBillMajorType") {
				// alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "getBillMinorType") {
				// alert("manipulate");
				getBillMinorType1(baseResponse);
			} else if (command == "getBillsubType") {				
				// alert("manipulate");
				getBillsubType1(baseResponse);
			}
		}
	}
}

function initialLoad(path) {
	//alert(path);

	var url = path + "/Pre_Audit_Check_List_MST?command=getBillMajorType";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function firstLoad(baseResponse) {    
	
	var chklstID = baseResponse.getElementsByTagName("chklstID")[0].firstChild.nodeValue;
	document.frm_Pre_Audit_Check_List_MST.txtCheckListCode.value = chklstID;
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("billMajorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMajorTypeCode = baseResponse
					.getElementsByTagName("billMajorTypeCode")[i].firstChild.nodeValue;
			var billMajorTypeDesc = baseResponse
					.getElementsByTagName("billMajorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMajorType");
			var op = document.createElement("OPTION");
			op.value = billMajorTypeCode;
			var txt = document.createTextNode(billMajorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
		}

		var len = baseResponse.getElementsByTagName("CheckListCode").length;
		for ( var k = 0; k < len; k++) {
//			var AccountingUnit = baseResponse.getElementsByTagName("AccountingUnit")[k].firstChild.nodeValue;
//			var AccountingForOffice = baseResponse.getElementsByTagName("AccountingForOffice")[k].firstChild.nodeValue;
			var CheckListCode1 = baseResponse.getElementsByTagName("CheckListCode")[k].firstChild.nodeValue;
			var CheckListCode = parseInt(CheckListCode1);
			var CheckListDesc = baseResponse.getElementsByTagName("CheckListDesc")[k].firstChild.nodeValue;
			var BillMajorType = baseResponse.getElementsByTagName("BillMajorType")[k].firstChild.nodeValue;
			var BillMinorType = baseResponse.getElementsByTagName("BillMinorType")[k].firstChild.nodeValue;		
			var BillSubType = baseResponse.getElementsByTagName("BillSubType")[k].firstChild.nodeValue;
			var Mandate = baseResponse.getElementsByTagName("Mandate")[k].firstChild.nodeValue;
			var NotApplicable = baseResponse.getElementsByTagName("NotApplicable")[k].firstChild.nodeValue;
			var view=baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = CheckListCode;
			
			var cell=document.createElement("TD");			
			if (view == "C") {
      			//var tid = document.createTextNode("Cancel");			
      			var priceSpan = document.createElement("span");
      			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
      			priceSpan.appendChild(document.createTextNode("Cancel"));			
      			cell.appendChild(priceSpan);          			
      		}else{
      			var anc = document.createElement("A");
    			var url = "javascript:loadValuesFromTable('" + CheckListCode + "')";
    			anc.href = url;
    			var txtedit = document.createTextNode("Edit");
    			anc.appendChild(txtedit);
    			cell.appendChild(anc);          						
      		}   		
			mycurrent_row.appendChild(cell);

//			var cell2 = document.createElement("TD");
//			var AccountingUnit = document.createTextNode(AccountingUnit);
//			cell2.appendChild(AccountingUnit);
//			mycurrent_row.appendChild(cell2);
//
//			var cell3 = document.createElement("TD");
//			var AccountingForOffice = document
//					.createTextNode(AccountingForOffice);
//			cell3.appendChild(AccountingForOffice);
//			mycurrent_row.appendChild(cell3);

			var cell2 = document.createElement("TD");
			var CheckListCode = document.createTextNode(CheckListCode);
			cell2.appendChild(CheckListCode);
			mycurrent_row.appendChild(cell2);
			
			var cell3 = document.createElement("TD");
			var CheckListDesc = document.createTextNode(CheckListDesc);
			cell3.appendChild(CheckListDesc);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var BillMajorType = document.createTextNode(BillMajorType);
			cell4.appendChild(BillMajorType);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var BillMinorType = document.createTextNode(BillMinorType);
			cell5.appendChild(BillMinorType);
			mycurrent_row.appendChild(cell5);
			
			var cell6 = document.createElement("TD");
			var BillSubType = document.createTextNode(BillSubType);
			cell6.appendChild(BillSubType);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			var Mandate = document.createTextNode(Mandate);
			cell7.appendChild(Mandate);
			mycurrent_row.appendChild(cell7);

			var cell8 = document.createElement("TD");
			var NotApplicable = document.createTextNode(NotApplicable);
			cell8.appendChild(NotApplicable);
			mycurrent_row.appendChild(cell8);
			
			var td5 = document.createElement("TD");
    		if(view=="C"){
    			var tdst = document.createTextNode("CANCEL");
    		}else{
    			var tdst = document.createTextNode("LIVE");
    		}
    		td5.appendChild(tdst);
    		mycurrent_row.appendChild(td5);

			tbody.appendChild(mycurrent_row);	
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function getBillMinorType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	// alert(cboBillMajorType);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillMajorType.focus();
	} else {
		var url = path	+ "/Pre_Audit_Check_List_MST?command=getBillMinorType&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function getBillMinorType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}

		var len5 = baseResponse.getElementsByTagName("EstimateSanctionNo").length;
		// alert(len5);
		for ( var i = 0; i < len5; i++) {
			var EstimateSanctionNo = baseResponse
					.getElementsByTagName("EstimateSanctionNo")[i].firstChild.nodeValue;
			// alert(EstimateSanctionNo);
			var se = document.getElementById("cboEstimateSanctionNumber");
			var op = document.createElement("OPTION");
			op.value = EstimateSanctionNo;
			var txt = document.createTextNode(EstimateSanctionNo);
			op.appendChild(txt);
			se.appendChild(op);
		}

	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}

function getBillsubType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	// alert(cboBillMajorType);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillMajorType.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.focus();
	} else {
		var url = path
				+ "/Pre_Audit_Check_List_MST?command=getBillsubType&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function getBillsubType1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_Pre_Audit_Check_List_MST.cboBillSubType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}

function add(path) {
   // alert(path);
    
//	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCheckListCode = document.getElementById("txtCheckListCode").value;
	var mtxtCheckListDesc = document.getElementById("mtxtCheckListDesc").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;	
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	//var rdoMandate = document.getElementById("rdoMandate").value;
	//var rdoNotApplicable = document.getElementById("rdoNotApplicable").value;
	if(document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[0].checked==true)
	{
		rdoNotApplicable=document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[0].value;
	}
   else
	{
	   rdoNotApplicable=document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[1].value;
	}
	if(document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].checked==true)
	{
		rdoMandate=document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].value;
	}
   else
	{
	   rdoMandate=document.frm_Pre_Audit_Check_List_MST.rdoMandate[1].value;
	}
	//alert(rdoNotApplicable);

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}
	
	if (txtCheckListCode == ""){
		alert("Enter Check List Code in the Field");
//		document.frm_Pre_Audit_Check_List_MST.cmbAcc_UnitCode.focus();
	}else if (mtxtCheckListDesc == ""){
		alert("Enter Check List Desc in the Field");
		document.frm_Pre_Audit_Check_List_MST.mtxtCheckListDesc.focus();
	}  else if ((cboBillMajorType == "")
			|| (cboBillMajorType <= 0)
			|| (cboBillMajorType == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillMajorType.focus();
	}else if ((cboBillMinorType == "")
			|| (cboBillMinorType <= 0)
			|| (cboBillMinorType == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.focus();
	}else if ((cboBillSubType == "")
			|| (cboBillSubType <= 0)
			|| (cboBillSubType == "s")) {
		alert("Select Bill Sub Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillSubType.focus();
	}else if ((rdoMandate == "Y")
			&& (rdoNotApplicable == "Y")) {
		alert("'Check Not Applicable as No in the Field' OR 'Check Mandate as No in the Field' ");
		document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable.focus();
	}  else {

		
		//changed on 18-11-2017
		
//		var url = path + "/Pre_Audit_Check_List_MST?command=add&cmbAcc_UnitCode="
//				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
//				+ "&txtCheckListCode=" + txtCheckListCode + "&mtxtCheckListDesc=" + mtxtCheckListDesc
//				+ "&cboBillMajorType=" + cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
//				+ "&cboBillSubType=" + cboBillSubType + "&rdoMandate=" + rdoMandate
//				+ "&rdoNotApplicable=" + rdoNotApplicable + "&month=" + month + "&year=" + year
//				+ "&year1=" + year1;

		var url = path + "/Pre_Audit_Check_List_MST?command=add&txtCheckListCode=" + txtCheckListCode 		
		+ "&mtxtCheckListDesc=" + mtxtCheckListDesc
		+ "&cboBillMajorType=" + cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
		+ "&cboBillSubType=" + cboBillSubType + "&rdoMandate=" + rdoMandate
		+ "&rdoNotApplicable=" + rdoNotApplicable + "&month=" + month + "&year=" + year
		+ "&year1=" + year1;

		
		
		
		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function addRow(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		var chklstID1 = baseResponse.getElementsByTagName("chklstID")[0].firstChild.nodeValue;
		var chklstID = parseInt(chklstID1);
		document.frm_Pre_Audit_Check_List_MST.txtCheckListCode.value = chklstID+1 ;
	}
	else
	{
		alert("Failed to Generate Check List Code.");
	}
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");

		refresh();

//		var AccountingUnit = baseResponse.getElementsByTagName("AccountingUnit")[0].firstChild.nodeValue;
//		var AccountingForOffice = baseResponse.getElementsByTagName("AccountingForOffice")[0].firstChild.nodeValue;
		var CheckListCode = baseResponse.getElementsByTagName("CheckListCode")[0].firstChild.nodeValue;
		var CheckListDesc = baseResponse.getElementsByTagName("CheckListDesc")[0].firstChild.nodeValue;
		var BillMajorType = baseResponse.getElementsByTagName("BillMajorType")[0].firstChild.nodeValue;
		var BillMinorType = baseResponse.getElementsByTagName("BillMinorType")[0].firstChild.nodeValue;		
		var BillSubType = baseResponse.getElementsByTagName("BillSubType")[0].firstChild.nodeValue;
		var Mandate = baseResponse.getElementsByTagName("Mandate")[0].firstChild.nodeValue;
		var NotApplicable = baseResponse.getElementsByTagName("NotApplicable")[0].firstChild.nodeValue;	

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = CheckListCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + CheckListCode + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

//		var cell2 = document.createElement("TD");
//		var AccountingUnit = document.createTextNode(AccountingUnit);
//		cell2.appendChild(AccountingUnit);
//		mycurrent_row.appendChild(cell2);
//
//		var cell3 = document.createElement("TD");
//		var AccountingForOffice = document
//				.createTextNode(AccountingForOffice);
//		cell3.appendChild(AccountingForOffice);
//		mycurrent_row.appendChild(cell3);

		var cell2 = document.createElement("TD");
		var CheckListCode = document.createTextNode(CheckListCode);
		cell2.appendChild(CheckListCode);
		mycurrent_row.appendChild(cell2);
		
		var cell3 = document.createElement("TD");
		var CheckListDesc = document.createTextNode(CheckListDesc);
		cell3.appendChild(CheckListDesc);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var BillMajorType = document.createTextNode(BillMajorType);
		cell4.appendChild(BillMajorType);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var BillMinorType = document.createTextNode(BillMinorType);
		cell5.appendChild(BillMinorType);
		mycurrent_row.appendChild(cell5);
		
		var cell6 = document.createElement("TD");
		var BillSubType = document.createTextNode(BillSubType);
		cell6.appendChild(BillSubType);
		mycurrent_row.appendChild(cell6);

		var cell7 = document.createElement("TD");
		var Mandate = document.createTextNode(Mandate);
		cell7.appendChild(Mandate);
		mycurrent_row.appendChild(cell7);

		var cell8 = document.createElement("TD");
		var NotApplicable = document.createTextNode(NotApplicable);
		cell8.appendChild(NotApplicable);
		mycurrent_row.appendChild(cell8);
		
		var cell9 = document.createElement("TD");
		var status = document.createTextNode("LIVE");
		cell9.appendChild(status);
		mycurrent_row.appendChild(cell9);

		tbody.appendChild(mycurrent_row);

	} else {
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(CheckListCode) {
	//alert(CheckListCode);
	
	var r = document.getElementById(CheckListCode);
	var rcells = r.cells;

//	document.frm_Pre_Audit_Check_List_MST.cmbAcc_UnitCode.value = rcells.item(1).firstChild.nodeValue;
//	document.frm_Pre_Audit_Check_List_MST.cmbOffice_code.value = rcells.item(2).firstChild.nodeValue;
	document.frm_Pre_Audit_Check_List_MST.txtCheckListCode.value = rcells.item(1).firstChild.nodeValue;
	document.frm_Pre_Audit_Check_List_MST.mtxtCheckListDesc.value = rcells.item(2).firstChild.nodeValue;
	document.frm_Pre_Audit_Check_List_MST.cboBillMajorType.value = rcells.item(3).firstChild.nodeValue;	
	if(rcells.item(6).firstChild.nodeValue == "Y")
	{
		document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].checked = true;
	}
	else
	{
		document.frm_Pre_Audit_Check_List_MST.rdoMandate[1].checked = true;
	}
	
	if(rcells.item(7).firstChild.nodeValue == "Y")
	{
		document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[0].checked = true;
	}
	else
	{
		document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[1].checked = true;
	}	
	document.frm_Pre_Audit_Check_List_MST.onsubmit.disabled = true;
	document.frm_Pre_Audit_Check_List_MST.ondelete.disabled = false;
	document.frm_Pre_Audit_Check_List_MST.onupdate.disabled = false;
	//alert("Edit rk");
	var url = "../../../../../Pre_Audit_Check_List_MST?command=Edit&CheckListCode=" + CheckListCode;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function Edit1(baseResponse) {
	document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.length="1";
	document.frm_Pre_Audit_Check_List_MST.cboBillSubType.length="1";
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		//alert(len4);
		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.value = billMinorTypeCode;			
		}
		var len5 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		//alert(len5);
		for ( var i = 0; i < len5; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_Pre_Audit_Check_List_MST.cboBillSubType.value = billSubTypeCode;	
		}		
	}	
}

function deleteeee(path) {
	//alert(path);
	var txtCheckListCode = document.frm_Pre_Audit_Check_List_MST.txtCheckListCode.value;	
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path + "/Pre_Audit_Check_List_MST?command=deleted&txtCheckListCode=" + txtCheckListCode;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function deleteRow(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		var chklstID = baseResponse.getElementsByTagName("chklstID")[0].firstChild.nodeValue;
		//alert(chklstID);
		document.frm_Pre_Audit_Check_List_MST.txtCheckListCode.value = chklstID;
	}
	else
	{
		alert("Failed to Generate Check List Code.");
	}

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		//var tbody = document.getElementById("Existing");
		//var r = document.getElementById(ApportCode);
		//var ri = r.rowIndex;
		//tbody.deleteRow(ri);
		var pat=document.getElementById('pat').value;
		//initialLoad(pat);
		alert("Cancel Successfully");
		self.close();
		//refresh();
	} else {
		alert("Unable to Cancel");
	}
}

function update(path) {
	//alert(path);
//	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCheckListCode = document.getElementById("txtCheckListCode").value;
	var mtxtCheckListDesc = document.getElementById("mtxtCheckListDesc").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;	
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	//var rdoMandate = document.getElementById("rdoMandate").value;
	//var rdoNotApplicable = document.getElementById("rdoNotApplicable").value;
	if(document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[0].checked==true)
	{
		rdoNotApplicable=document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[0].value;
	}
   else
	{
	   rdoNotApplicable=document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[1].value;
	}
	if(document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].checked==true)
	{
		rdoMandate=document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].value;
	}
   else
	{
	   rdoMandate=document.frm_Pre_Audit_Check_List_MST.rdoMandate[1].value;
	}	
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}
	
	if (txtCheckListCode == ""){
		alert("Enter Check List Code in the Field");
//		document.frm_Pre_Audit_Check_List_MST.cmbAcc_UnitCode.focus();
	}else if (mtxtCheckListDesc == ""){
		alert("Enter Check List Desc in the Field");
		document.frm_Pre_Audit_Check_List_MST.mtxtCheckListDesc.focus();
	}  else if ((cboBillMajorType == "")
			|| (cboBillMajorType <= 0)
			|| (cboBillMajorType == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillMajorType.focus();
	}else if ((cboBillMinorType == "")
			|| (cboBillMinorType <= 0)
			|| (cboBillMinorType == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.focus();
	}else if ((cboBillSubType == "")
			|| (cboBillSubType <= 0)
			|| (cboBillSubType == "s")) {
		alert("Select Bill Sub Type in the Field");
		document.frm_Pre_Audit_Check_List_MST.cboBillSubType.focus();
	} else if ((rdoMandate == "Y") && (rdoNotApplicable == "Y")) {
		alert("'Check Not Applicable as No in the Field' OR 'Check Mandate as No in the Field' ");
		document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable.focus();
	}  else {

//		var url = path + "/Pre_Audit_Check_List_MST?command=update&cmbAcc_UnitCode="
//				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
//				+ "&txtCheckListCode=" + txtCheckListCode + "&mtxtCheckListDesc=" + mtxtCheckListDesc
//				+ "&cboBillMajorType=" + cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
//				+ "&cboBillSubType=" + cboBillSubType + "&rdoMandate=" + rdoMandate
//				+ "&rdoNotApplicable=" + rdoNotApplicable + "&month=" + month + "&year=" + year
//				+ "&year1=" + year1;
		
		
		var url = path + "/Pre_Audit_Check_List_MST?command=update&txtCheckListCode=" + txtCheckListCode 
		+ "&mtxtCheckListDesc=" + mtxtCheckListDesc
		+ "&cboBillMajorType=" + cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
		+ "&cboBillSubType=" + cboBillSubType + "&rdoMandate=" + rdoMandate
		+ "&rdoNotApplicable=" + rdoNotApplicable + "&month=" + month + "&year=" + year
		+ "&year1=" + year1;
		

		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function updateRow(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		var chklstID = baseResponse.getElementsByTagName("chklstID")[0].firstChild.nodeValue;		
		document.frm_Pre_Audit_Check_List_MST.txtCheckListCode.value = chklstID;
	}
	else
	{
		alert("Failed to Generate Check List Code.");
	}
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
		var items = new Array();
//		items[0] = baseResponse.getElementsByTagName("AccountingUnit")[0].firstChild.nodeValue;
//		items[1] = baseResponse.getElementsByTagName("AccountingForOffice")[0].firstChild.nodeValue;
		items[0] = baseResponse.getElementsByTagName("CheckListCode")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("CheckListDesc")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("BillMajorType")[0].firstChild.nodeValue;
		items[3] = baseResponse.getElementsByTagName("BillMinorType")[0].firstChild.nodeValue;		
		items[4] = baseResponse.getElementsByTagName("BillSubType")[0].firstChild.nodeValue;
		items[5] = baseResponse.getElementsByTagName("Mandate")[0].firstChild.nodeValue;
		items[6] = baseResponse.getElementsByTagName("NotApplicable")[0].firstChild.nodeValue;

		var r = document.getElementById(items[0]);
		var rcells = r.cells;
//		rcells.item(1).firstChild.nodeValue = items[0];
//		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];		
		rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];
		rcells.item(6).firstChild.nodeValue = items[5];
		rcells.item(7).firstChild.nodeValue = items[6];				
	}
	else
	{
		alert("Failed to update values");
	}
}

function refresh() {

	document.frm_Pre_Audit_Check_List_MST.mtxtCheckListDesc.value = "";
	document.frm_Pre_Audit_Check_List_MST.cboBillMajorType.value = "s";
	document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.value = "s";
	document.frm_Pre_Audit_Check_List_MST.cboBillSubType.value = "s";
	document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].checked = true;
	document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[1].checked = true;

	document.frm_Pre_Audit_Check_List_MST.onsubmit.disabled = false;
	document.frm_Pre_Audit_Check_List_MST.ondelete.disabled = true;
	document.frm_Pre_Audit_Check_List_MST.onupdate.disabled = true;
	document.frm_Pre_Audit_Check_List_MST.mtxtCheckListDesc.focus;

}

function ClearAll(path) {
	var url = path + "/Pre_Audit_Check_List_MST?command=ClearAll";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
	
}

function ClearAll1(baseResponse) {
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
		var chklstID = baseResponse.getElementsByTagName("chklstID")[0].firstChild.nodeValue;		
		document.frm_Pre_Audit_Check_List_MST.txtCheckListCode.value = chklstID;
	}
	else
	{
		alert("Failed to Generate Check List Code.");
	}
	document.frm_Pre_Audit_Check_List_MST.mtxtCheckListDesc.value = "";
	document.frm_Pre_Audit_Check_List_MST.cboBillMajorType.value = "s";
	document.frm_Pre_Audit_Check_List_MST.cboBillMinorType.value = "s";
	document.frm_Pre_Audit_Check_List_MST.cboBillSubType.value = "s";
	document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].checked = true;
	document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[1].checked = true;

	document.frm_Pre_Audit_Check_List_MST.onsubmit.disabled = false;
	document.frm_Pre_Audit_Check_List_MST.ondelete.disabled = true;
	document.frm_Pre_Audit_Check_List_MST.onupdate.disabled = true;
	document.frm_Pre_Audit_Check_List_MST.mtxtCheckListDesc.focus;	
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
			return false;
	}
}

notApp=function(){	
	if(document.frm_Pre_Audit_Check_List_MST.rdoNotApplicable[0].checked==true){
		alert("Mandate No ");
		document.frm_Pre_Audit_Check_List_MST.rdoMandate[1].checked=true;
	}else{
		alert("Mandate Yes ");
		document.frm_Pre_Audit_Check_List_MST.rdoMandate[0].checked=true;
	}
};

function exitfun() {
	window.close();
}