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
			// alert("manipulate-command:--->>>"+command);

			if (command == "getBillMajorType") {
				// alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "getBillMinorType") {
				// alert("manipulate");
				getBillMinorType1(baseResponse);
			} else if (command == "getBillsubType") {				
				// alert("manipulate");
				getBillsubType1(baseResponse);
			} else if (command == "calculateBudget") {
				// alert("manipulate");
				calculateBudget1(baseResponse);
			} else if (command == "getGrid") {
				// alert("manipulate");
				getGrid1(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "PONo") {
				// alert("manipulate");
				forPassOrderNo1(baseResponse);
			} else if (command == "PODate") {
				// alert("manipulate");
				forPassOrderDate1(baseResponse);
			}
		}
	}
}

function initialLoad(path) {
	//alert(path);

	var url = path + "/Sanc_Proc_forContingentMiscellaneous?command=getBillMajorType";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function firstLoad(baseResponse) {    
	var len1 = baseResponse.getElementsByTagName("empName").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("empName")[i].firstChild.nodeValue;
		// alert(empName);
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}

	var len2 = baseResponse.getElementsByTagName("accUnitID").length;
	for ( var i = 0; i < len2; i++) {
		var accUnitID = baseResponse.getElementsByTagName("accUnitID")[i].firstChild.nodeValue;
		var accUnitName = baseResponse.getElementsByTagName("accUnitName")[i].firstChild.nodeValue;
		// alert(empName);
		var se = document.getElementById("txtAccUnitInWhichPaymenttoBeMade");
		var op = document.createElement("OPTION");
		op.value = accUnitID;
		var txt = document.createTextNode(accUnitName);
		op.appendChild(txt);
		se.appendChild(op);

	}
	
	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;    
	document.frm_Sanc_Proc_forContingentMiscellaneous.txtEmpID_mas.value = empid;

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

		var len5 = baseResponse.getElementsByTagName("designationID").length;
		for ( var i = 0; i < len5; i++) {
			var designationID = baseResponse
					.getElementsByTagName("designationID")[i].firstChild.nodeValue;
			var designation = baseResponse.getElementsByTagName("designation")[i].firstChild.nodeValue;

			var se = document.getElementById("cboSanctioningAuthority");
			var op = document.createElement("OPTION");
			op.value = designationID;
			var txt = document.createTextNode(designation);
			op.appendChild(txt);
			se.appendChild(op);
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
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMajorType.focus();
	} else {
		var url = path	+ "/Sanc_Proc_forContingentMiscellaneous?command=getBillMinorType&cboBillMajorType="
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
	document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMinorType.length = 1;
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
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMajorType.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMinorType.focus();
	} else {
		var url = path
				+ "/Sanc_Proc_forContingentMiscellaneous?command=getBillsubType&cboBillMajorType="
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
	document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillSubType.length = 1;
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

function getGrid(path)
{
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMajorType.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMinorType.focus();
	} else if ((document.getElementById("cboBillSubType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillSubType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillSubType").value == "s")) {
		alert("Select Bill Sub Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillSubType.focus();
	}else {
		var url = path
				+ "/Sanc_Proc_forContingentMiscellaneous?command=getGrid&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType+ "&cboBillSubType=" + cboBillSubType +
				"&cmbAcc_UnitCode=" + cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;
		//alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
	
}

function getGrid1(baseResponse) {	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
		var supportingInvoice = baseResponse.getElementsByTagName("supportingInvoice")[0].firstChild.nodeValue;
		if(supportingInvoice = "Y")
		{
			document.frm_Sanc_Proc_forContingentMiscellaneous.rdoSupportingInvoices[0].checked=true;
		}
		else
		{
			document.frm_Sanc_Proc_forContingentMiscellaneous.rdoSupportingInvoices[1].checked=true;
		}
	}else if(flag == "NoData") {		
		alert("Supporting Invoice Record Does Not Exist");
	}
	else
	{
		alert("Failed to Check Supporting Invoice");
	}
	
	if (flag == "success") {
		
		 var tbody = document.getElementById("tblList");
		    var rowcount=tbody.rows.length;
		   
	//alert(rowcount);
		    for(var i=0;i<rowcount;i++)
		        {
		    	 var r=tbody.rows[i];
		    	var s=r.cells[1].firstChild.nodeValue;
		    	
		          var ri=s.rowIndex;
		      
		       
		           tbody.deleteRow(ri);
		        }	  
		    
		var len6 = baseResponse.getElementsByTagName("invoiceNo").length;
		// alert(len6);
		if(len6!=0)
		{
		for ( var k = 0; k <= len6; k++) {

			var invoiceNo = baseResponse.getElementsByTagName("invoiceNo")[k].firstChild.nodeValue;
			var invoiceDate = baseResponse.getElementsByTagName("invoiceDate")[k].firstChild.nodeValue;
			var invoiceAmount = baseResponse.getElementsByTagName("invoiceAmount")[k].firstChild.nodeValue;
			var invoiceParticulars = baseResponse.getElementsByTagName("invoiceParticulars")[k].firstChild.nodeValue;
			
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = invoiceNo;
			
			var cell = document.createElement("TD");
            var anc = document.createElement("input");
            anc.type = "checkbox";
            anc.id=invoiceNo;
            anc.name="check";
            anc.checked=true;
            anc.value=invoiceNo;
            cell.appendChild(anc);
            mycurrent_row.appendChild(cell);
            
			var cell1 = document.createElement("TD");
			var invoiceNo1 = document
					.createTextNode(invoiceNo);
			cell1.appendChild(invoiceNo1);
			mycurrent_row.appendChild(cell1);

			var cell2 = document.createElement("TD");
			var invoiceDate = document
					.createTextNode(invoiceDate);
			cell2.appendChild(invoiceDate);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var invoiceAmount = document.createTextNode(invoiceAmount);
			cell3.appendChild(invoiceAmount);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var invoiceParticulars = document.createTextNode(invoiceParticulars);
			cell4.appendChild(invoiceParticulars);
			mycurrent_row.appendChild(cell4);            

			tbody.appendChild(mycurrent_row);

		}
		}
		} else if(flag == "NoData") {		
			alert("Invoice Record Does Not Exist");
		}
		else
		{
		alert("Failed to Load");
	}
}

function calculateBudget(path) {
	// alert(path);
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtaccountheadcode = document.getElementById("txtAcc_HeadCode").value;

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

	if (txtaccountheadcode == "") {
		alert("Enter Account Head Code in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtAcc_HeadCode
				.focus();
	} else {
		var url = path
				+ "/Sanc_Proc_forContingentMiscellaneous?command=calculateBudget&cboAcc_UnitCode="
				+ cboAcc_UnitCode + "&cboOffice_code=" + cboOffice_code
				+ "&year=" + year + "&year1=" + year1 + "&txtaccountheadcode="
				+ txtaccountheadcode;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function calculateBudget1(baseResponse) {
	//alert("RKsbg");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var BudgetProvided = baseResponse.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;		
		var balanceAmount = baseResponse.getElementsByTagName("balanceAmount")[0].firstChild.nodeValue;
		
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtBudgetProvided.value = BudgetProvided;
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtBudgetSpent.value = BudgetSoFarSpent;	
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtBalanceAmount.value = balanceAmount;	
		
	} else if (flag == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}
}

function saveFunc(path) {

	//alert(path);
	
	var t=0;
	var k = 0
	var tbody = document.getElementById("tblList");
	var rowcount = tbody.rows.length;
	//alert(rowcount);
	var al = new Array();	
	for(var i=0;i<rowcount;i++)
    {		
       var r=tbody.rows[i];
       var s=r.cells.length;
       t=t+parseInt(r.cells[3].firstChild.nodeValue);
       for(var j=1;j<s;j++)
               {
               if(r.cells[0].firstChild.checked)
               {                         
               al[k]=r.cells[j].firstChild.nodeValue;
                k++;
               }
               }    
    }

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	
	var txtSanctionProceedingDate = document.getElementById("txtSanctionProceedingDate").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var rdoSupportingInvoices = document.getElementById("rdoSupportingInvoices").value;
	var cboPayeeType = document.getElementById("cboPayeeType").value;
	var cboPayeeCode = document.getElementById("cboPayeeCode").value;
	var txtPayeeNameDesignation = document.getElementById("txtPayeeNameDesignation").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var cboSanctioningAuthority = document.getElementById("cboSanctioningAuthority").value;
	var cmbSL_Code = document.getElementById("txtEmpID_mas").value;	
	var txtAccUnitInWhichPaymenttoBeMade = document.getElementById("txtAccUnitInWhichPaymenttoBeMade").value;
	var txtTotalSanctionedAmount = document.getElementById("txtTotalSanctionedAmount").value;
	var txtBudgetProvided = document.getElementById("txtBudgetProvided").value;
	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtBalanceAmount = document.getElementById("txtBalanceAmount").value;
	var txtaccountheadcode = document.getElementById("txtAcc_HeadCode").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;		
	var totalSancAmt = parseInt(txtTotalSanctionedAmount);

	if (document.getElementById("txtSanctionProceedingDate").value == "") {
		alert("Enter Sanction Proceeding Date in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtSanctionProceedingDate
				.focus();
	} else if (cboBillMajorType == "" || cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMajorType.focus();
	} else if (cboBillMinorType == "" || cboBillMinorType == "s") {
		alert("Select Bill Minor Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillMinorType.focus();
	} else if (cboBillSubType == "" || cboBillSubType == "s") {
		alert("Select Bill Sub Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboBillSubType.focus();
	} else if (document.getElementById("cboPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboPayeeType.focus();
	} else if (document.getElementById("cboPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboPayeeCode.focus();
	}else if (document.getElementById("txtPayeeNameDesignation").value == "") {
		alert("Enter Payee Name & Designation the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtPayeeNameDesignation.focus();
	}else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtRefDate.focus();
	} else if (cboSanctioningAuthority == "" || cboSanctioningAuthority == "s") {
		alert("Select Sanctioning Authority in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.cboSanctioningAuthority
				.focus();
	}  else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtAccUnitInWhichPaymenttoBeMade").value == "" || document.getElementById("txtAccUnitInWhichPaymenttoBeMade").value == "s") {
		alert("Select Account Unit In Which Payment to BeMade in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtAccUnitInWhichPaymenttoBeMade
				.focus();
	}else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_Sanc_Proc_forContingentMiscellaneous.txtTotalSanctionedAmount
				.focus();
	} else if (rowcount == 0) {
		alert("Data Does Not Exist for Transaction");		
	}else if (t != totalSancAmt) {
		alert("Sanction Amount Does Not Tally");		
	}else if (txtBalanceAmount < totalSancAmt) {
		alert("Total Sanctioned Amount is greater than Balance Amount in the Current Year");		
	}else {  		
		var url = path
				+ "/Sanc_Proc_forContingentMiscellaneous?command=saveFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month
				+ "&txtSanctionProceedingDate=" + txtSanctionProceedingDate
				+ "&cboBillMajorType=" + cboBillMajorType
				+ "&cboBillMinorType=" + cboBillMinorType + "&cboBillSubType="
				+ cboBillSubType + "&rdoSupportingInvoices="
				+ rdoSupportingInvoices + "&cboPayeeType=" + cboPayeeType
				+ "&cboPayeeCode=" + cboPayeeCode + "&txtPayeeNameDesignation="
				+ txtPayeeNameDesignation + "&txtRefNo=" + txtRefNo
				+ "&txtRefDate=" + txtRefDate + "&cboSanctioningAuthority="
				+ cboSanctioningAuthority + "&cmbSL_Code=" + cmbSL_Code
				+ "&txtAccUnitInWhichPaymenttoBeMade="
				+ txtAccUnitInWhichPaymenttoBeMade
				+ "&txtTotalSanctionedAmount=" + txtTotalSanctionedAmount
				+ "&txtaccountheadcode=" + txtaccountheadcode + "&mtxtRemarks="
				+ mtxtRemarks + "&al=" + al + "&txtBudgetProvided=" + txtBudgetProvided
				+ "&txtBudgetSpent=" + txtBudgetSpent;

		//alert(url);

		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}
function saveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		var tbody = document.getElementById("tblList");
		  var rowcount=tbody.rows.length;
		    for(var i=0;i<rowcount;i++)
		        {
		    	 var r=i.rowIndex;	   
		           tbody.deleteRow(r);
		        }
		alert("Record Updated Successfully");
		refresh();
	} else {
		alert("Record Updation Failed");
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
	
	  document.getElementById("txtSanctionProceedingDate").value="";
	  document.getElementById("cboBillMajorType").value="s";
	  document.getElementById("cboBillMinorType").value="s";
	  document.getElementById("cboBillSubType").value="s";
	  document.frm_Sanc_Proc_forContingentMiscellaneous.rdoSupportingInvoices[0].checked=false;
	  document.frm_Sanc_Proc_forContingentMiscellaneous.rdoSupportingInvoices[1].checked=false;
	  
	  document.getElementById("cboPayeeType").value="";
	  document.getElementById("cboPayeeCode").value="";
	  document.getElementById("txtPayeeNameDesignation").value="";
	  document.getElementById("txtRefNo").value="";
	  document.getElementById("txtRefDate").value="";
	  document.getElementById("cboSanctioningAuthority").value="s";	  	
	  document.getElementById("txtAcc_HeadCode").value="";
	  document.getElementById("txtAcc_HeadDesc").value="";
	  
	  document.getElementById("txtBudgetProvided").value="";
	  document.getElementById("txtBudgetSpent").value="";
	  document.getElementById("txtBalanceAmount").value="";
	  document.getElementById("txtAccUnitInWhichPaymenttoBeMade").value="s";
	  document.getElementById("txtTotalSanctionedAmount").value="";
	  document.getElementById("mtxtRemarks").value="";
	  
	  var tbody = document.getElementById("tblList");
	  var rowcount=tbody.rows.length;
	  
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=tbody.rows[i];
	    	var s=r.cells[1].firstChild.nodeValue;
	    	
	          var ri=s.rowIndex;
	      
	       
	           tbody.deleteRow(ri);
	        }	  
  }

function exitfun(path) {
	window.close();
}