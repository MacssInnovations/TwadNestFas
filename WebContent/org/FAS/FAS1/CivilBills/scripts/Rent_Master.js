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

function initialLoad(path) {
	
	
	
	
	document.getElementById("txtOwnersCode").length = 0;
	
//	var cmbAcc_UnitCode = document.RentMaster.cmbAcc_UnitCode.value;
//		//document.getElementById("cmbAcc_UnitCode").value;
//	//alert("cmbAcc_UnitCode*****"+cmbAcc_UnitCode);
//	var cmbOffice_code = document.RentMaster.cmbOffice_code.value;
//		//document.getElementById("cmbOffice_code").value;
////	alert("cmbOffice_code*****"+cmbOffice_code);
	
	var url = path + "/RentMasterServlet?command=gett";
	//alert("url==>"+url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}






function RentalPaymentOption() {
	var cboRentalPaymentOption = document.RentMaster.cboRentalPaymentOption.value;
	// alert(cboRentalPaymentOption);
	if (cboRentalPaymentOption == "S") {
		document.RentMaster.cboPaymentMonth11.disabled = true;
		document.RentMaster.cboPaymentMonth12.disabled = true;
		document.RentMaster.cboPaymentMonth13.disabled = true;
		document.RentMaster.cboPaymentMonth14.disabled = true;
	} else if (cboRentalPaymentOption == "M") {
		document.RentMaster.cboPaymentMonth11.disabled = true;
		document.RentMaster.cboPaymentMonth12.disabled = true;
		document.RentMaster.cboPaymentMonth13.disabled = true;
		document.RentMaster.cboPaymentMonth14.disabled = true;
	} else if (cboRentalPaymentOption == "Q") {
		document.RentMaster.cboPaymentMonth11.disabled = false;
		document.RentMaster.cboPaymentMonth12.disabled = false;
		document.RentMaster.cboPaymentMonth13.disabled = false;
		document.RentMaster.cboPaymentMonth14.disabled = false;
	} else if (cboRentalPaymentOption == "H") {
		document.RentMaster.cboPaymentMonth11.disabled = false;
		document.RentMaster.cboPaymentMonth12.disabled = false;
		document.RentMaster.cboPaymentMonth13.disabled = true;
		document.RentMaster.cboPaymentMonth14.disabled = true;
	} else {
		document.RentMaster.cboPaymentMonth11.disabled = false;
		document.RentMaster.cboPaymentMonth12.disabled = true;
		document.RentMaster.cboPaymentMonth13.disabled = true;
		document.RentMaster.cboPaymentMonth14.disabled = true;
	}
}
function deleteeee(path) {
	var txtOwnersCode = document.RentMaster.txtOwnersCode.value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path + "/RentMasterServlet?command=deleted&OwnersCode="
				+ txtOwnersCode;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function update(path) {
	var cmbAcc_UnitCode = document.RentMaster.cmbAcc_UnitCode.value;
	var cmbOffice_code = document.RentMaster.cmbOffice_code.value;
	var txtOwnersCode = document.RentMaster.txtOwnersCode.value;
	var txtOwnersName = document.RentMaster.txtOwnersName.value;
	var txtAdvancePaid = document.RentMaster.txtAdvancePaid.value;
	var txtRentValue = document.RentMaster.txtRentValue.value;
	var cboRentalPaymentOption = document.RentMaster.cboRentalPaymentOption.value;

	if (cboRentalPaymentOption == "Q") {
		cboPaymentMonth11 = document.RentMaster.cboPaymentMonth11.value;
		cboPaymentMonth12 = document.RentMaster.cboPaymentMonth12.value;
		cboPaymentMonth13 = document.RentMaster.cboPaymentMonth13.value;
		cboPaymentMonth14 = document.RentMaster.cboPaymentMonth14.value;
	} else if (cboRentalPaymentOption == "H") {
		cboPaymentMonth11 = document.RentMaster.cboPaymentMonth11.value;
		cboPaymentMonth12 = document.RentMaster.cboPaymentMonth12.value;
	} else if (cboRentalPaymentOption == "A") {
		cboPaymentMonth11 = document.RentMaster.cboPaymentMonth11.value;
	}

	var txtTDSDeductionifany = document.RentMaster.txtTDSDeductionifany.value;
	var txtAnnualAmounttoIT = document.RentMaster.txtAnnualAmounttoIT.value;
	var txtITPercentage = document.RentMaster.txtITPercentage.value;
	var radioITExempted;
	if(document.RentMaster.radioITExempted[0].checked==true)
		{
			radioITExempted = "Y";
			//alert("whether IT exempted::::::::"+radioITExempted);
	}
	else
		{
		radioITExempted = "N";
		}
	var officeId = 	document.RentMaster.officeId.value;
	var txtemp_office = document.RentMaster.txtemp_office.value;
	var mtxtRemarks = document.RentMaster.mtxtRemarks.value;

	var txtRentAgreementPeriodFrom = document
			.getElementById("txtRentAgreementPeriodFrom").value;
	var txtRentAgreementPeriodTo = document
			.getElementById("txtRentAgreementPeriodTo").value;
	var txtAdvancePaidDate = document.getElementById("txtAdvancePaidDate").value;
	var txtRentValueasonDate = document.getElementById("txtRentValueasonDate").value;

	var browser = navigator.appName;

	if (browser == "Netscape") {
		var dd1 = txtRentAgreementPeriodFrom.split('/');
		txtRentAgreementPeriodFrom = dd1[1] + "/" + dd1[0] + "/" + dd1[2];

		var dd2 = txtRentAgreementPeriodTo.split('/');
		txtRentAgreementPeriodTo = dd2[1] + "/" + dd2[0] + "/" + dd2[2];

		var dd3 = txtAdvancePaidDate.split('/');
		txtAdvancePaidDate = dd3[1] + "/" + dd3[0] + "/" + dd3[2];

		var dd4 = txtRentValueasonDate.split('/');
		txtRentValueasonDate = dd4[1] + "/" + dd4[0] + "/" + dd4[2];
	}
	var a = txtRentAgreementPeriodFrom.split('/');
	var b = txtRentAgreementPeriodTo.split('/');
	var c = txtAdvancePaidDate.split('/');
	var d = txtRentValueasonDate.split('/');

	var PeriodFrom = new Date(a[2], a[0] - 1, a[1]);
	var PeriodTo = new Date(b[2], b[0] - 1, b[1]);
	var PaidDate = new Date(c[2], c[0] - 1, c[1]);
	var RentValueasonDate = new Date(d[2], d[0] - 1, d[1]);

	var txtRentAgreementPeriodFrom = document
			.getElementById("txtRentAgreementPeriodFrom").value;
	var txtRentAgreementPeriodTo = document
			.getElementById("txtRentAgreementPeriodTo").value;
	var txtAdvancePaidDate = document.getElementById("txtAdvancePaidDate").value;
	var txtRentValueasonDate = document.getElementById("txtRentValueasonDate").value;

	if ((document.RentMaster.cmbAcc_UnitCode.value == "")
			|| (document.RentMaster.cmbAcc_UnitCode.value.length <= 0)
			|| (document.RentMaster.cmbAcc_UnitCode.value == "S")) {
		alert("Select Accounting Unit in the Field");
		document.RentMaster.cmbAcc_UnitCode.focus();
	} else if ((document.RentMaster.cmbOffice_code.value == "")
			|| (document.RentMaster.cmbOffice_code.value.length <= 0)
			|| (document.RentMaster.cmbOffice_code.value == "S")) {
		alert("Select Accounting For Office in the Field");
		document.RentMaster.cmbOffice_code.focus();
	} else if (txtOwnersCode == "") {
		alert("Select OwnersCode in the Field");
		document.RentMaster.txtOwnersCode.focus();
	} else if (txtOwnersName == "") {
		alert("Enter Owners Name in the Field");
		document.RentMaster.txtOwnersName.focus();
	} else if (txtRentAgreementPeriodFrom == "") {
		alert("Enter Rent Agreement Period From in the Field");
		document.RentMaster.txtRentAgreementPeriodFrom.focus();
	} else if (txtRentAgreementPeriodTo == "") {
		alert("Enter Rent Agreement Period To in the Field");
		document.RentMaster.txtRentAgreementPeriodTo.focus();
	} else if (txtAdvancePaid == "") {
		alert("Enter Advance Paid in the Field");
		document.RentMaster.txtAdvancePaid.focus();
	} else if (txtAdvancePaidDate == "") {
		alert("Enter Advance Paid Date in the Field");
		document.RentMaster.txtAdvancePaidDate.focus();
	} else if (txtRentValueasonDate == "") {
		alert("Enter Rent Value as on Date in the Field");
		document.RentMaster.txtRentValueasonDate.focus();
	} else if (txtRentValue == "") {
		alert("Enter RentValue in the Field");
		document.RentMaster.txtRentValue.focus();
	} else if ((document.RentMaster.cboRentalPaymentOption.value == "")
			|| (document.RentMaster.cboRentalPaymentOption.value.length <= 0)
			|| (document.RentMaster.cboRentalPaymentOption.value == "S")) {
		alert("Select Rental Payment Option in the Field");
		document.RentMaster.cboRentalPaymentOption.focus();
	}else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
		&& ((document.RentMaster.cboPaymentMonth11.value == "")
				|| (document.RentMaster.cboPaymentMonth11.value.length <= 0) || (document.RentMaster.cboPaymentMonth11.value == "0"))) {
	alert("Select Payment Month 1 Option in the Field");
	document.RentMaster.cboPaymentMonth11.focus();
} else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
		&& ((document.RentMaster.cboPaymentMonth12.value == "")
				|| (document.RentMaster.cboPaymentMonth12.value.length <= 0) || (document.RentMaster.cboPaymentMonth12.value == "0"))) {
	alert("Select Payment Month 2 Option in the Field");
	document.RentMaster.cboPaymentMonth12.focus();
} else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
		&& ((document.RentMaster.cboPaymentMonth13.value == "")
				|| (document.RentMaster.cboPaymentMonth13.value.length <= 0) || (document.RentMaster.cboPaymentMonth13.value == "0"))) {
	alert("Select Payment Month 3 Option in the Field");
	document.RentMaster.cboPaymentMonth13.focus();
} else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
		&& ((document.RentMaster.cboPaymentMonth14.value == "")
				|| (document.RentMaster.cboPaymentMonth14.value.length <= 0) || (document.RentMaster.cboPaymentMonth14.value == "0"))) {
	alert("Select Payment Month 4 Option in the Field");
	document.RentMaster.cboPaymentMonth14.focus();
} else if (document.RentMaster.cboRentalPaymentOption.value == "H"
		&& ((document.RentMaster.cboPaymentMonth11.value == "")
				|| (document.RentMaster.cboPaymentMonth11.value.length <= 0) || (document.RentMaster.cboPaymentMonth11.value == "0"))) {
	alert("Select Payment Month 1 Option in the Field");
	document.RentMaster.cboPaymentMonth11.focus();
} else if (document.RentMaster.cboRentalPaymentOption.value == "H"
		&& ((document.RentMaster.cboPaymentMonth12.value == "")
				|| (document.RentMaster.cboPaymentMonth12.value.length <= 0) || (document.RentMaster.cboPaymentMonth12.value == "0"))) {
	alert("Select Payment Month 2 Option in the Field");
	document.RentMaster.cboPaymentMonth12.focus();
} else if (document.RentMaster.cboRentalPaymentOption.value == "A"
		&& ((document.RentMaster.cboPaymentMonth11.value == "")
				|| (document.RentMaster.cboPaymentMonth11.value.length <= 0) || (document.RentMaster.cboPaymentMonth11.value == "0"))) {
	alert("Select Payment Month 1 Option in the Field");
	document.RentMaster.cboPaymentMonth11.focus();
} else if (document.RentMaster.txtTDSDeductionifany.value == "") {
		alert("Enter TDS Deduction if any in the Field");
		document.RentMaster.txtTDSDeductionifany.focus();
	} else if (PeriodFrom >= PeriodTo) {
		alert("Rent Agreement Period To must be greater than Rent Agreement Period From");
		return 0;
	} else if ((PeriodFrom >= PaidDate) || (PaidDate >= PeriodTo)) {
		alert("Advance Paid Date must be in Between Rent Agreement Period From and Rent Agreement Period To");
		return 0;

	} else if ((PaidDate >= RentValueasonDate) || (PaidDate >= PeriodTo)) {
		alert("Rent Value as on Date must be greater than Advance Paid Date");
		return 0;
	} else {
		if (cboRentalPaymentOption == "M") {
			var url = path
					+ "/RentMasterServlet?command=update&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks
					+ "&txtAnnualAmounttoIT="+txtAnnualAmounttoIT
					+ "&txtITPercentage="+txtITPercentage
					+ "&radioITExempted="+radioITExempted
					+ "&officeId="+officeId
					+ "&txtemp_office="+txtemp_office;
		} else if (cboRentalPaymentOption == "Q") {
			var url = path
					+ "/RentMasterServlet?command=update&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks + "&cboPaymentMonth11="
					+ cboPaymentMonth11 + "&cboPaymentMonth12="
					+ cboPaymentMonth12 + "&cboPaymentMonth13="
					+ cboPaymentMonth13 + "&cboPaymentMonth14="
					+ cboPaymentMonth14;
		} else if (cboRentalPaymentOption == "H") {
			var url = path
					+ "/RentMasterServlet?command=update&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks + "&cboPaymentMonth11="
					+ cboPaymentMonth11 + "&cboPaymentMonth12="
					+ cboPaymentMonth12;
		} else if (cboRentalPaymentOption == "A") {
			var url = path
					+ "/RentMasterServlet?command=update&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks + "&cboPaymentMonth11="
					+ cboPaymentMonth11;
		}
		// alert(url);
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

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse)
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "gett") {
				loadGrid(baseResponse);
			} else if (command == "gett1") {
				forRefresh1(baseResponse);
			}
			else if(command == "getname"){
				getname(baseResponse)
			}
				
		}
	}
}

function deleteRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Deleted Successfully");
		refresh();

		var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
		if (flag1 == "success1") {

			var ownerCode = baseResponse.getElementsByTagName("ownerCode")[0].firstChild.nodeValue;

			document.RentMaster.txtOwnersCode.value = ownerCode;
		}

	} else {
		alert("Unable to Delete");
	}
}
function updateRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();

		var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
		if (flag1 == "success1") {

			var ownerCode = baseResponse.getElementsByTagName("ownerCode")[0].firstChild.nodeValue;

			document.RentMaster.txtOwnersCode.value = ownerCode;
		}
		var items = new Array();
		items[0] = baseResponse.getElementsByTagName("AccountingUnit")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("AccountingForOffice")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("OwnersCode")[0].firstChild.nodeValue;
		items[3] = baseResponse.getElementsByTagName("OwnersName")[0].firstChild.nodeValue;
		items[4] = baseResponse.getElementsByTagName("RentAgreementPeriodFrom")[0].firstChild.nodeValue;
		items[5] = baseResponse.getElementsByTagName("RentAgreementPeriodTo")[0].firstChild.nodeValue;
		items[6] = baseResponse.getElementsByTagName("AdvancePaid")[0].firstChild.nodeValue;
		items[7] = baseResponse.getElementsByTagName("AdvancePaidDate")[0].firstChild.nodeValue;
		items[8] = baseResponse.getElementsByTagName("RentValueasonDate")[0].firstChild.nodeValue;
		items[9] = baseResponse.getElementsByTagName("RentValue")[0].firstChild.nodeValue;
		items[10] = baseResponse.getElementsByTagName("RentalPaymentOption")[0].firstChild.nodeValue;
//		items[11] = baseResponse.getElementsByTagName("cboPaymentMonth11")[0].firstChild.nodeValue;
//		items[12] = baseResponse.getElementsByTagName("cboPaymentMonth12")[0].firstChild.nodeValue;
//		items[13] = baseResponse.getElementsByTagName("cboPaymentMonth13")[0].firstChild.nodeValue;
//		items[14] = baseResponse.getElementsByTagName("cboPaymentMonth14")[0].firstChild.nodeValue;
		items[11] = baseResponse.getElementsByTagName("TDSDeductionifany")[0].firstChild.nodeValue;
		
		items[12] = baseResponse.getElementsByTagName("AnnualAmount_toIT")[0].firstChild.nodeValue;
		items[13] = baseResponse.getElementsByTagName("ITPercentage")[0].firstChild.nodeValue;
		items[14] = baseResponse.getElementsByTagName("ITExemptionIfAny")[0].firstChild.nodeValue;
		items[15] = baseResponse.getElementsByTagName("officeid")[0].firstChild.nodeValue;
		//items[16] = baseResponse.getElementsByTagName("officename")[0].firstChild.nodeValue;
		
		items[16] = baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;

		var r = document.getElementById(items[2]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
		rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];
		rcells.item(6).firstChild.nodeValue = items[5];
		rcells.item(7).firstChild.nodeValue = items[6];
		rcells.item(8).firstChild.nodeValue = items[7];
		rcells.item(9).firstChild.nodeValue = items[8];
		rcells.item(10).firstChild.nodeValue = items[9];
		rcells.item(11).firstChild.nodeValue = items[10];

		rcells.item(12).firstChild.nodeValue = items[11];
		rcells.item(13).firstChild.nodeValue = items[12];
		rcells.item(14).firstChild.nodeValue = items[13];
		rcells.item(15).firstChild.nodeValue = items[14];

		rcells.item(16).firstChild.nodeValue = items[15];
		//alert(items[16]);
		if(items[16] == "nil")
		{
		rcells.item(17).firstChild.nodeValue = "";
		}else{
			rcells.item(17).firstChild.nodeValue = items[16];
		}

	} else {
		alert("Failed to update values");
	}
}


function loadownerName()
{
	
		//alert("Welcome");
		
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		//alert("cmbAcc_UnitCode*****"+cmbAcc_UnitCode);
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		//alert("cmbOffice_code*****"+cmbOffice_code);
		
		var url = "../../../../../RentMasterServlet?command=getname&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
		
}




function getname(baseResponse)
{
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success1") {
// code changed on 05-12-2017
		
//		var ownerCode = baseResponse.getElementsByTagName("ownerCode")[0].firstChild.nodeValue;
//
//		document.RentMaster.txtOwnersCode.value = ownerCode;
		
		
		
		var code=baseResponse.getElementsByTagName("ownerCode");
		//alert("Code===>"+code);
		
		//document.RentMaster.txtOwnersCode.value = code;
		
		var ownerCode=new Array();
        var ownerName=new Array();
        
        
		for(var k=0;k<code.length;k++)
        {
		ownerCode[k] = baseResponse.getElementsByTagName("ownerCode")[k].firstChild.nodeValue;
		ownerName[k] = baseResponse.getElementsByTagName("ownerName")[k].firstChild.nodeValue;
        }
		txtOwnersCode=document.getElementById("txtOwnersCode")
        txtOwnersCode.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";
        try
        {
        	txtOwnersCode.add(option);
        }catch(errorObject)
        {
        	txtOwnersCode.add(option,null);
        }
        for(var k=0;k<code.length;k++)
        {   
          var option=document.createElement("OPTION");
          
       //   option.value=ownerName[k];
          option.text=ownerName[k]+"/"+ownerCode[k];
          option.value=ownerCode[k];
          try
          {
        	   txtOwnersCode.add(option);
          }
          catch(errorObject)
          {
        	  txtOwnersCode.add(option,null);
          }
        }
        
        
       
        
        
        
		
	   //document.RentMaster.txtOwnersCode.value = ownerCode;
		
		
	}
			
			
			
}



function loadGrid(baseResponse) {
//	alert("coming to load grid");
//	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
//	if (flag1 == "success1") {
//// code changed on 05-12-2017
//		
////		var ownerCode = baseResponse.getElementsByTagName("ownerCode")[0].firstChild.nodeValue;
////
////		document.RentMaster.txtOwnersCode.value = ownerCode;
//		
//		
//		
//		var code=baseResponse.getElementsByTagName("ownerCode");
//		//alert("Code===>"+code);
//		
//		//document.RentMaster.txtOwnersCode.value = code;
//		
//		var ownerCode=new Array();
//        var ownerName=new Array();
//        
//        
//		for(var k=0;k<code.length;k++)
//        {
//		ownerCode[k] = baseResponse.getElementsByTagName("ownerCode")[k].firstChild.nodeValue;
//		ownerName[k] = baseResponse.getElementsByTagName("ownerName")[k].firstChild.nodeValue;
//        }
//		txtOwnersCode=document.getElementById("txtOwnersCode")
//        txtOwnersCode.innerHTML="";
//        var option=document.createElement("OPTION");
//        option.text="--Select Code--";
//        option.value="";
//        try
//        {
//        	txtOwnersCode.add(option);
//        }catch(errorObject)
//        {
//        	txtOwnersCode.add(option,null);
//        }
//        for(var k=0;k<code.length;k++)
//        {   
//          var option=document.createElement("OPTION");
//          
//       //   option.value=ownerName[k];
//          option.text=ownerName[k]+"/"+ownerCode[k];
//          option.value=ownerCode[k];
//          try
//          {
//        	   txtOwnersCode.add(option);
//          }
//          catch(errorObject)
//          {
//        	  txtOwnersCode.add(option,null);
//          }
//        }
//        
//        
//       
//        
//        
//        
//		
//	   //document.RentMaster.txtOwnersCode.value = ownerCode;
//		
//		
//	}

//	var address = baseResponse.getElementsByTagName("address")[0].firstChild.nodeValue;
//	alert(address);
//	document.RentMaster.mtxtOfficeAddress.value = address;

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var len = baseResponse.getElementsByTagName("OwnersCode").length;
//		alert(len);
		for ( var k = 0; k < len; k++) {
			var AccountingUnit = baseResponse
					.getElementsByTagName("AccountingUnit")[k].firstChild.nodeValue;
			var AccountingForOffice = baseResponse
					.getElementsByTagName("AccountingForOffice")[k].firstChild.nodeValue;
			var OwnersCode = baseResponse.getElementsByTagName("OwnersCode")[k].firstChild.nodeValue;
			var OwnersName = baseResponse.getElementsByTagName("OwnersName")[k].firstChild.nodeValue;
			var RentAgreementPeriodFrom = baseResponse
					.getElementsByTagName("RentAgreementPeriodFrom")[k].firstChild.nodeValue;
			var RentAgreementPeriodTo = baseResponse
					.getElementsByTagName("RentAgreementPeriodTo")[k].firstChild.nodeValue;
			var AdvancePaid = baseResponse.getElementsByTagName("AdvancePaid")[k].firstChild.nodeValue;
			var AdvancePaidDate = baseResponse
					.getElementsByTagName("AdvancePaidDate")[k].firstChild.nodeValue;
			var RentValueasonDate = baseResponse
					.getElementsByTagName("RentValueasonDate")[k].firstChild.nodeValue;
			var RentValue = baseResponse.getElementsByTagName("RentValue")[k].firstChild.nodeValue;
			var RentalPaymentOption = baseResponse
					.getElementsByTagName("RentalPaymentOption")[k].firstChild.nodeValue;
			var cboPaymentMonth11 = baseResponse
					.getElementsByTagName("cboPaymentMonth11")[k].firstChild.nodeValue;
			var cboPaymentMonth12 = baseResponse
					.getElementsByTagName("cboPaymentMonth12")[k].firstChild.nodeValue;
			var cboPaymentMonth13 = baseResponse
					.getElementsByTagName("cboPaymentMonth13")[k].firstChild.nodeValue;
			var cboPaymentMonth14 = baseResponse
					.getElementsByTagName("cboPaymentMonth14")[k].firstChild.nodeValue;
			var TDSDeductionifany = baseResponse
					.getElementsByTagName("TDSDeductionifany")[k].firstChild.nodeValue;
			
			var AnnualAmount_toIT = baseResponse.getElementsByTagName("AnnualAmount_toIT")[k].firstChild.nodeValue;
//			alert(AnnualAmount_toIT);
			var ITPercentage = baseResponse.getElementsByTagName("ITPercentage")[k].firstChild.nodeValue;
			var ITExemptionIfAny = baseResponse.getElementsByTagName("ITExemptionIfAny")[k].firstChild.nodeValue;
			var officeid = baseResponse.getElementsByTagName("officeid")[k].firstChild.nodeValue;
			var officename = baseResponse.getElementsByTagName("officename")[k].firstChild.nodeValue;
			
			var Remarks = baseResponse.getElementsByTagName("Remarks")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = OwnersCode;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + OwnersCode + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var tnodeAccountingUnit = document.createTextNode(AccountingUnit);
			cell2.appendChild(tnodeAccountingUnit);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var tnodeAccountingForOffice = document
					.createTextNode(AccountingForOffice);
			cell3.appendChild(tnodeAccountingForOffice);
			mycurrent_row.appendChild(cell3);
			// --------------------------------------------------------
			var cell4 = document.createElement("TD");
			var tnodeOwnersCode = document.createTextNode(OwnersCode);
			cell4.appendChild(tnodeOwnersCode);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var tnodeOwnersName = document.createTextNode(OwnersName);
			cell5.appendChild(tnodeOwnersName);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var RentAgreementPeriodFrom = document
					.createTextNode(RentAgreementPeriodFrom);
			cell6.appendChild(RentAgreementPeriodFrom);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			var tnodeRentAgreementPeriodTo = document
					.createTextNode(RentAgreementPeriodTo);
			cell7.appendChild(tnodeRentAgreementPeriodTo);
			mycurrent_row.appendChild(cell7);

			var cell8 = document.createElement("TD");
			var tnodeAdvancePaid = document.createTextNode(AdvancePaid);
			cell8.appendChild(tnodeAdvancePaid);
			mycurrent_row.appendChild(cell8);

			var cell9 = document.createElement("TD");
			var tnodeAdvancePaidDate = document.createTextNode(AdvancePaidDate);
			cell9.appendChild(tnodeAdvancePaidDate);
			mycurrent_row.appendChild(cell9);

			var cel20 = document.createElement("TD");
			var tnodeRentValueasonDate = document
					.createTextNode(RentValueasonDate);
			cel20.appendChild(tnodeRentValueasonDate);
			mycurrent_row.appendChild(cel20);

			var cel21 = document.createElement("TD");
			var tnodeRentValue = document.createTextNode(RentValue);
			cel21.appendChild(tnodeRentValue);
			mycurrent_row.appendChild(cel21);

			var cel22 = document.createElement("TD");
			var tnodeRentalPaymentOption = document
					.createTextNode(RentalPaymentOption);
			cel22.appendChild(tnodeRentalPaymentOption);
			mycurrent_row.appendChild(cel22);

			/*var cel23 = document.createElement("TD");
			var tnodecboPaymentMonth11 = document
					.createTextNode(cboPaymentMonth11);
			cel23.appendChild(tnodecboPaymentMonth11);
			mycurrent_row.appendChild(cel23);

			var cel24 = document.createElement("TD");
			var tnodecboPaymentMonth12 = document
					.createTextNode(cboPaymentMonth12);
			cel24.appendChild(tnodecboPaymentMonth12);
			mycurrent_row.appendChild(cel24);

			var cel25 = document.createElement("TD");
			var tnodecboPaymentMonth13 = document
					.createTextNode(cboPaymentMonth13);
			cel25.appendChild(tnodecboPaymentMonth13);
			mycurrent_row.appendChild(cel25);

			var cel26 = document.createElement("TD");
			var tnodecboPaymentMonth14 = document
					.createTextNode(cboPaymentMonth14);
			cel26.appendChild(tnodecboPaymentMonth14);
			mycurrent_row.appendChild(cel26);*/

			var cel27 = document.createElement("TD");
			var tnodeTDSDeductionifany = document
					.createTextNode(TDSDeductionifany);
			cel27.appendChild(tnodeTDSDeductionifany);
			mycurrent_row.appendChild(cel27);
			var cel29 = document.createElement("TD");
			var tnodeAnnualAmount_toIT = document.createTextNode(AnnualAmount_toIT);
			cel29.appendChild(tnodeAnnualAmount_toIT);
			mycurrent_row.appendChild(cel29);
			
			var cel30 = document.createElement("TD");
			var tnodeITPercentage = document.createTextNode(ITPercentage);
			cel30.appendChild(tnodeITPercentage);
			mycurrent_row.appendChild(cel30);

			var cel31 = document.createElement("TD");
			var tnodeITExemptionIfAny = document.createTextNode(ITExemptionIfAny);
			cel31.appendChild(tnodeITExemptionIfAny);
			mycurrent_row.appendChild(cel31);
			
			var cel32 = document.createElement("TD");
			var tnodeofficeid = document.createTextNode(officeid);
			cel32.appendChild(tnodeofficeid);
			mycurrent_row.appendChild(cel32);
			
			if(Remarks == "null")
			{
					var cel28 = document.createElement("TD");
					var tnodeRemarks = document.createTextNode("");
					cel28.appendChild(tnodeRemarks);
					mycurrent_row.appendChild(cel28);
			}
			else
			{
				var cel28 = document.createElement("TD");
				var tnodeRemarks = document.createTextNode(Remarks);
				cel28.appendChild(tnodeRemarks);
				mycurrent_row.appendChild(cel28);
			}

			tbody.appendChild(mycurrent_row);
		}

	} else {
		alert("Fail to Load");
	}

	var len1 = baseResponse.getElementsByTagName("Acc_Unit_ID").length;
	for ( var i = 0; i < len1; i++) {
		var Acc_Unit_ID = baseResponse.getElementsByTagName("Acc_Unit_ID")[i].firstChild.nodeValue;
		var Acc_Unit_Name = baseResponse.getElementsByTagName("Acc_Unit_Name")[i].firstChild.nodeValue;

		var se = document.getElementById("cmbAcc_UnitCode");
		var op = document.createElement("OPTION");
		op.value = Acc_Unit_ID;
		var txt = document.createTextNode(Acc_Unit_Name);
		op.appendChild(txt);
		se.appendChild(op);

	}

	var len2 = baseResponse.getElementsByTagName("Acc_Office_ID").length;
	for ( var i = 0; i < len2; i++) {
		var Acc_Office_ID = baseResponse.getElementsByTagName("Acc_Office_ID")[i].firstChild.nodeValue;
		var Acc_Office_Name = baseResponse
				.getElementsByTagName("Acc_Office_Name")[i].firstChild.nodeValue;

		var se = document.getElementById("cmbOffice_code");
		var op = document.createElement("OPTION");
		op.value = Acc_Office_ID;
		var txt = document.createTextNode(Acc_Office_Name);
		op.appendChild(txt);
		se.appendChild(op);

	}
}

function add(path) {
	//alert("adding records");
	var cmbAcc_UnitCode = document.RentMaster.cmbAcc_UnitCode.value;
	var cmbOffice_code = document.RentMaster.cmbOffice_code.value;
	
	var codeandname= document.getElementById("txtOwnersCode").value;
	//alert("codeandname==>"+codeandname);
	var codeandnametext= document.getElementById("txtOwnersCode")[1].innerHTML;
	//alert("codeandnametextHTML==>"+codeandnametext);
	
	var OwnersCode=codeandnametext.split("/");
	//alert("OwnersCode==>"+OwnersCode[1]);
	//alert("txtOwnersName==>"+OwnersCode[0]);
	
	var txtOwnersCode =OwnersCode[1];
	var txtOwnersName =OwnersCode[0];
	//var txtOwnersCode = document.RentMaster.txtOwnersCode.value;
	//var txtOwnersName = document.RentMaster.txtOwnersName.value;
	var txtAdvancePaid = document.RentMaster.txtAdvancePaid.value;
	var txtRentValue = document.RentMaster.txtRentValue.value;
	var cboRentalPaymentOption = document.RentMaster.cboRentalPaymentOption.value;
	if (cboRentalPaymentOption == "Q") {
		cboPaymentMonth11 = document.RentMaster.cboPaymentMonth11.value;
		cboPaymentMonth12 = document.RentMaster.cboPaymentMonth12.value;
		cboPaymentMonth13 = document.RentMaster.cboPaymentMonth13.value;
		cboPaymentMonth14 = document.RentMaster.cboPaymentMonth14.value;
	} else if (cboRentalPaymentOption == "H") {
		cboPaymentMonth11 = document.RentMaster.cboPaymentMonth11.value;
		cboPaymentMonth12 = document.RentMaster.cboPaymentMonth12.value;
	} else if (cboRentalPaymentOption == "A") {
		cboPaymentMonth11 = document.RentMaster.cboPaymentMonth11.value;
	}
	var txtTDSDeductionifany = document.RentMaster.txtTDSDeductionifany.value;
	//added new fields by Dec 2012
	var txtAnnualAmounttoIT = document.RentMaster.txtAnnualAmounttoIT.value;
	var txtITPercentage = document.RentMaster.txtITPercentage.value;
	var radioITExempted;
//	var radioITExempted = document.RentMaster.radioITExempted.value;
//	alert(radioITExempted);
	
	if(document.RentMaster.radioITExempted[0].checked==true)
		{
			radioITExempted = "Y";
			//alert("whether IT exempted::::::::"+radioITExempted);
	}
	else
		{
		radioITExempted = "N";
		}
	var officeId = 	document.RentMaster.officeId.value;
	var txtemp_office = document.RentMaster.txtemp_office.value;
	
	var mtxtRemarks = document.RentMaster.mtxtRemarks.value;

	var txtRentAgreementPeriodFrom = document
			.getElementById("txtRentAgreementPeriodFrom").value;
	var txtRentAgreementPeriodTo = document
			.getElementById("txtRentAgreementPeriodTo").value;
	var txtAdvancePaidDate = document.getElementById("txtAdvancePaidDate").value;
	var txtRentValueasonDate = document.getElementById("txtRentValueasonDate").value;

	var browser = navigator.appName;

	if (browser == "Netscape") {
		var dd1 = txtRentAgreementPeriodFrom.split('/');
		txtRentAgreementPeriodFrom = dd1[1] + "/" + dd1[0] + "/" + dd1[2];

		var dd2 = txtRentAgreementPeriodTo.split('/');
		txtRentAgreementPeriodTo = dd2[1] + "/" + dd2[0] + "/" + dd2[2];

		var dd3 = txtAdvancePaidDate.split('/');
		txtAdvancePaidDate = dd3[1] + "/" + dd3[0] + "/" + dd3[2];

		var dd4 = txtRentValueasonDate.split('/');
		txtRentValueasonDate = dd4[1] + "/" + dd4[0] + "/" + dd4[2];
	}
	var a = txtRentAgreementPeriodFrom.split('/');
	var b = txtRentAgreementPeriodTo.split('/');
	var c = txtAdvancePaidDate.split('/');
	var d = txtRentValueasonDate.split('/');

	var PeriodFrom = new Date(a[2], a[0] - 1, a[1]);
	var PeriodTo = new Date(b[2], b[0] - 1, b[1]);
	var PaidDate = new Date(c[2], c[0] - 1, c[1]);
	var RentValueasonDate = new Date(d[2], d[0] - 1, d[1]);

	var txtRentAgreementPeriodFrom = document
			.getElementById("txtRentAgreementPeriodFrom").value;
	var txtRentAgreementPeriodTo = document
			.getElementById("txtRentAgreementPeriodTo").value;
	var txtAdvancePaidDate = document.getElementById("txtAdvancePaidDate").value;
	var txtRentValueasonDate = document.getElementById("txtRentValueasonDate").value;

	if ((document.RentMaster.cmbAcc_UnitCode.value == "")
			|| (document.RentMaster.cmbAcc_UnitCode.value.length <= 0)
			|| (document.RentMaster.cmbAcc_UnitCode.value == "S")) {
		alert("Select Accounting Unit in the Field");
		document.RentMaster.cmbAcc_UnitCode.focus();
	} else if ((document.RentMaster.cmbOffice_code.value == "")
			|| (document.RentMaster.cmbOffice_code.value.length <= 0)
			|| (document.RentMaster.cmbOffice_code.value == "S")) {
		alert("Select Accounting For Office in the Field");
		document.RentMaster.cmbOffice_code.focus();
	} else if (txtOwnersCode == "") {
		alert("Select OwnersCode in the Field");
		document.RentMaster.txtOwnersCode.focus();
	} 
//	else if (txtOwnersName == "") {
//		alert("Enter Owners Name in the Field");
//		document.RentMaster.txtOwnersName.focus();
//	} 
	
	else if (txtRentAgreementPeriodFrom == "") {
		alert("Enter Rent Agreement Period From in the Field");
		document.RentMaster.txtRentAgreementPeriodFrom.focus();
	} else if (txtRentAgreementPeriodTo == "") {
		alert("Enter Rent Agreement Period To in the Field");
		document.RentMaster.txtRentAgreementPeriodTo.focus();
	} else if (txtAdvancePaid == "") {
		alert("Enter Advance Paid in the Field");
		document.RentMaster.txtAdvancePaid.focus();
	} else if (txtAdvancePaidDate == "") {
		alert("Enter Advance Paid Date in the Field");
		document.RentMaster.txtAdvancePaidDate.focus();
	} else if (txtRentValueasonDate == "") {
		alert("Enter Rent Value as on Date in the Field");
		document.RentMaster.txtRentValueasonDate.focus();
	} else if (txtRentValue == "") {
		alert("Enter RentValue in the Field");
		document.RentMaster.txtRentValue.focus();
	} else if ((document.RentMaster.cboRentalPaymentOption.value == "")
			|| (document.RentMaster.cboRentalPaymentOption.value.length <= 0)
			|| (document.RentMaster.cboRentalPaymentOption.value == "S")) {
		alert("Select Rental Payment Option in the Field");
		document.RentMaster.cboRentalPaymentOption.focus();
	} else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
			&& ((document.RentMaster.cboPaymentMonth11.value == "")
					|| (document.RentMaster.cboPaymentMonth11.value.length <= 0) || (document.RentMaster.cboPaymentMonth11.value == "0"))) {
		alert("Select Payment Month 1 Option in the Field");
		document.RentMaster.cboPaymentMonth11.focus();
	} else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
			&& ((document.RentMaster.cboPaymentMonth12.value == "")
					|| (document.RentMaster.cboPaymentMonth12.value.length <= 0) || (document.RentMaster.cboPaymentMonth12.value == "0"))) {
		alert("Select Payment Month 2 Option in the Field");
		document.RentMaster.cboPaymentMonth12.focus();
	} else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
			&& ((document.RentMaster.cboPaymentMonth13.value == "")
					|| (document.RentMaster.cboPaymentMonth13.value.length <= 0) || (document.RentMaster.cboPaymentMonth13.value == "0"))) {
		alert("Select Payment Month 3 Option in the Field");
		document.RentMaster.cboPaymentMonth13.focus();
	} else if (document.RentMaster.cboRentalPaymentOption.value == "Q"
			&& ((document.RentMaster.cboPaymentMonth14.value == "")
					|| (document.RentMaster.cboPaymentMonth14.value.length <= 0) || (document.RentMaster.cboPaymentMonth14.value == "0"))) {
		alert("Select Payment Month 4 Option in the Field");
		document.RentMaster.cboPaymentMonth14.focus();
	} else if (document.RentMaster.cboRentalPaymentOption.value == "H"
			&& ((document.RentMaster.cboPaymentMonth11.value == "")
					|| (document.RentMaster.cboPaymentMonth11.value.length <= 0) || (document.RentMaster.cboPaymentMonth11.value == "0"))) {
		alert("Select Payment Month 1 Option in the Field");
		document.RentMaster.cboPaymentMonth11.focus();
	} else if (document.RentMaster.cboRentalPaymentOption.value == "H"
			&& ((document.RentMaster.cboPaymentMonth12.value == "")
					|| (document.RentMaster.cboPaymentMonth12.value.length <= 0) || (document.RentMaster.cboPaymentMonth12.value == "0"))) {
		alert("Select Payment Month 2 Option in the Field");
		document.RentMaster.cboPaymentMonth12.focus();
	} else if (document.RentMaster.cboRentalPaymentOption.value == "A"
			&& ((document.RentMaster.cboPaymentMonth11.value == "")
					|| (document.RentMaster.cboPaymentMonth11.value.length <= 0) || (document.RentMaster.cboPaymentMonth11.value == "0"))) {
		alert("Select Payment Month 1 Option in the Field");
		document.RentMaster.cboPaymentMonth11.focus();
	} else if (document.RentMaster.txtTDSDeductionifany.value == "") {
		alert("Enter TDS Deduction if any in the Field");
		document.RentMaster.txtTDSDeductionifany.focus();
	}else if (document.RentMaster.txtAnnualAmounttoIT.value == "") {
		alert("Enter annual amount subject to IT if any in the Field");
		document.RentMaster.txtAnnualAmounttoIT.focus();
	}else if (document.RentMaster.txtITPercentage.value == "") {
		alert("Enter Percentage of IT if any in the Field");
		document.RentMaster.txtITPercentage.focus();
	}	
	else if (PeriodFrom >= PeriodTo) {
		alert("Rent Agreement Period To must be greater than Rent Agreement Period From");
		return 0;
	} else if ((PeriodFrom >= PaidDate) || (PaidDate >= PeriodTo)) {
		alert("Advance Paid Date must be in Between Rent Agreement Period From and Rent Agreement Period To");
		return 0;

	} else if ((PaidDate >= RentValueasonDate) || (PaidDate >= PeriodTo)) {
		alert("Rent Value as on Date must be greater than Advance Paid Date");
		return 0;
	} else {

		if (cboRentalPaymentOption == "M") {
			var url = path + "/RentMasterServlet?command=add&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks
					+ "&txtAnnualAmounttoIT="+txtAnnualAmounttoIT
					+ "&txtITPercentage="+txtITPercentage
					+ "&radioITExempted="+radioITExempted
					+ "&officeId="+officeId
					+ "&txtemp_office="+txtemp_office;
		} else if (cboRentalPaymentOption == "Q") {
			var url = path + "/RentMasterServlet?command=add&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks + "&cboPaymentMonth11="
					+ cboPaymentMonth11 + "&cboPaymentMonth12="
					+ cboPaymentMonth12 + "&cboPaymentMonth13="
					+ cboPaymentMonth13 + "&cboPaymentMonth14="
					+ cboPaymentMonth14;
		} else if (cboRentalPaymentOption == "H") {
			var url = path + "/RentMasterServlet?command=add&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks + "&cboPaymentMonth11="
					+ cboPaymentMonth11 + "&cboPaymentMonth12="
					+ cboPaymentMonth12;
		} else if (cboRentalPaymentOption == "A") {
			var url = path + "/RentMasterServlet?command=add&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtOwnersCode=" + txtOwnersCode + "&txtOwnersName="
					+ txtOwnersName + "&txtRentAgreementPeriodFrom="
					+ txtRentAgreementPeriodFrom + "&txtRentAgreementPeriodTo="
					+ txtRentAgreementPeriodTo + "&txtAdvancePaid="
					+ txtAdvancePaid + "&txtAdvancePaidDate="
					+ txtAdvancePaidDate + "&txtRentValueasonDate="
					+ txtRentValueasonDate + "&txtRentValue=" + txtRentValue
					+ "&cboRentalPaymentOption=" + cboRentalPaymentOption
					+ "&txtTDSDeductionifany=" + txtTDSDeductionifany
					+ "&mtxtRemarks=" + mtxtRemarks + "&cboPaymentMonth11="
					+ cboPaymentMonth11;
		}
//		 alert(url);
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
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");

		refresh();
		var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
		if (flag1 == "success1") {

			var ownerCode = baseResponse.getElementsByTagName("ownerCode")[0].firstChild.nodeValue;

			document.RentMaster.txtOwnersCode.value = ownerCode;
		}

		var AccountingUnit = baseResponse
				.getElementsByTagName("AccountingUnit")[0].firstChild.nodeValue;
		var AccountingForOffice = baseResponse
				.getElementsByTagName("AccountingForOffice")[0].firstChild.nodeValue;
		var OwnersCode = baseResponse.getElementsByTagName("OwnersCode")[0].firstChild.nodeValue;
		var OwnersName = baseResponse.getElementsByTagName("OwnersName")[0].firstChild.nodeValue;
		var RentAgreementPeriodFrom = baseResponse
				.getElementsByTagName("RentAgreementPeriodFrom")[0].firstChild.nodeValue;
		var RentAgreementPeriodTo = baseResponse
				.getElementsByTagName("RentAgreementPeriodTo")[0].firstChild.nodeValue;
		var AdvancePaid = baseResponse.getElementsByTagName("AdvancePaid")[0].firstChild.nodeValue;
		var AdvancePaidDate = baseResponse
				.getElementsByTagName("AdvancePaidDate")[0].firstChild.nodeValue;
		var RentValueasonDate = baseResponse
				.getElementsByTagName("RentValueasonDate")[0].firstChild.nodeValue;
		var RentValue = baseResponse.getElementsByTagName("RentValue")[0].firstChild.nodeValue;
		var RentalPaymentOption = baseResponse
				.getElementsByTagName("RentalPaymentOption")[0].firstChild.nodeValue;
		var cboPaymentMonth11 = baseResponse
				.getElementsByTagName("cboPaymentMonth11")[0].firstChild.nodeValue;
		var cboPaymentMonth12 = baseResponse
				.getElementsByTagName("cboPaymentMonth12")[0].firstChild.nodeValue;
		var cboPaymentMonth13 = baseResponse
				.getElementsByTagName("cboPaymentMonth13")[0].firstChild.nodeValue;
		var cboPaymentMonth14 = baseResponse
				.getElementsByTagName("cboPaymentMonth14")[0].firstChild.nodeValue;
		var TDSDeductionifany = baseResponse
				.getElementsByTagName("TDSDeductionifany")[0].firstChild.nodeValue;
		var AnnualAmount_toIT = baseResponse.getElementsByTagName("AnnualAmount_toIT")[0].firstChild.nodeValue;
		var ITPercentage = baseResponse.getElementsByTagName("ITPercentage")[0].firstChild.nodeValue;
		var ITExemptionIfAny = baseResponse.getElementsByTagName("ITExemptionIfAny")[0].firstChild.nodeValue;
		var officeid = baseResponse.getElementsByTagName("officeid")[0].firstChild.nodeValue;
		var officename = baseResponse.getElementsByTagName("officename")[0].firstChild.nodeValue;
		
		var Remarks = baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = OwnersCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + OwnersCode + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var tnodeAccountingUnit = document.createTextNode(AccountingUnit);
		cell2.appendChild(tnodeAccountingUnit);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var tnodeAccountingForOffice = document
				.createTextNode(AccountingForOffice);
		cell3.appendChild(tnodeAccountingForOffice);
		mycurrent_row.appendChild(cell3);
		// --------------------------------------------------------
		var cell4 = document.createElement("TD");
		var tnodeOwnersCode = document.createTextNode(OwnersCode);
		cell4.appendChild(tnodeOwnersCode);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var tnodeOwnersName = document.createTextNode(OwnersName);
		cell5.appendChild(tnodeOwnersName);
		mycurrent_row.appendChild(cell5);

		var cell6 = document.createElement("TD");
		var RentAgreementPeriodFrom = document
				.createTextNode(RentAgreementPeriodFrom);
		cell6.appendChild(RentAgreementPeriodFrom);
		mycurrent_row.appendChild(cell6);

		var cell7 = document.createElement("TD");
		var tnodeRentAgreementPeriodTo = document
				.createTextNode(RentAgreementPeriodTo);
		cell7.appendChild(tnodeRentAgreementPeriodTo);
		mycurrent_row.appendChild(cell7);

		var cell8 = document.createElement("TD");
		var tnodeAdvancePaid = document.createTextNode(AdvancePaid);
		cell8.appendChild(tnodeAdvancePaid);
		mycurrent_row.appendChild(cell8);

		var cell9 = document.createElement("TD");
		var tnodeAdvancePaidDate = document.createTextNode(AdvancePaidDate);
		cell9.appendChild(tnodeAdvancePaidDate);
		mycurrent_row.appendChild(cell9);

		var cel20 = document.createElement("TD");
		var tnodeRentValueasonDate = document.createTextNode(RentValueasonDate);
		cel20.appendChild(tnodeRentValueasonDate);
		mycurrent_row.appendChild(cel20);

		var cel21 = document.createElement("TD");
		var tnodeRentValue = document.createTextNode(RentValue);
		cel21.appendChild(tnodeRentValue);
		mycurrent_row.appendChild(cel21);
		
		var cel22 = document.createElement("TD");
		var tnodeRentalPaymentOption = document
				.createTextNode(RentalPaymentOption);
		cel22.appendChild(tnodeRentalPaymentOption);
		mycurrent_row.appendChild(cel22);
		/*var cel23 = document.createElement("TD");
		var tnodecboPaymentMonth11 = document.createTextNode(cboPaymentMonth11);
		cel23.appendChild(tnodecboPaymentMonth11);
		mycurrent_row.appendChild(cel23);

		var cel24 = document.createElement("TD");
		var tnodecboPaymentMonth12 = document.createTextNode(cboPaymentMonth12);
		cel24.appendChild(tnodecboPaymentMonth12);
		mycurrent_row.appendChild(cel24);

		var cel25 = document.createElement("TD");
		var tnodecboPaymentMonth13 = document.createTextNode(cboPaymentMonth13);
		cel25.appendChild(tnodecboPaymentMonth13);
		mycurrent_row.appendChild(cel25);

		var cel26 = document.createElement("TD");
		var tnodecboPaymentMonth14 = document.createTextNode(cboPaymentMonth14);
		cel26.appendChild(tnodecboPaymentMonth14);
		mycurrent_row.appendChild(cel26);*/

		var cel27 = document.createElement("TD");
		var tnodeTDSDeductionifany = document.createTextNode(TDSDeductionifany);
		cel27.appendChild(tnodeTDSDeductionifany);
		mycurrent_row.appendChild(cel27);
		//added dec 2012
		
		var cel29 = document.createElement("TD");
		var tnodeAnnualAmount_toIT = document.createTextNode(AnnualAmount_toIT);
		cel29.appendChild(tnodeAnnualAmount_toIT);
		mycurrent_row.appendChild(cel29);
		
		var cel30 = document.createElement("TD");
		var tnodeITPercentage = document.createTextNode(ITPercentage);
		cel30.appendChild(tnodeITPercentage);
		mycurrent_row.appendChild(cel30);

		var cel31 = document.createElement("TD");
		var tnodeITExemptionIfAny = document.createTextNode(ITExemptionIfAny);
		cel31.appendChild(tnodeITExemptionIfAny);
		mycurrent_row.appendChild(cel31);
		
		var cel32 = document.createElement("TD");
		var tnodeofficeid = document.createTextNode(officeid);
		cel32.appendChild(tnodeofficeid);
		mycurrent_row.appendChild(cel32);
//		var hidden1=document.createElement("input");
//        hidden1.type="hidden";
//        hidden1.name="hidoffice_id";
//        hidden1.value=officeid;
//        cell32.appendChild(hidden1);
//        mycurrent_row.appendChild(cell32); 
				
if(Remarks == "nil")
{
		var cel28 = document.createElement("TD");
		var tnodeRemarks = document.createTextNode("");
		cel28.appendChild(tnodeRemarks);
		mycurrent_row.appendChild(cel28);
}
else
{
	var cel28 = document.createElement("TD");
	var tnodeRemarks = document.createTextNode(Remarks);
	cel28.appendChild(tnodeRemarks);
	mycurrent_row.appendChild(cel28);
}
		tbody.appendChild(mycurrent_row);

	} else {
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(idd) {
	//alert(idd);
	var r = document.getElementById(idd);
	var rcells = r.cells;
	// var tbody = document.getElementById("tblList");
	//refresh();
	document.RentMaster.cmbAcc_UnitCode.value = rcells.item(1).firstChild.nodeValue;
	// alert("rk");
	
	common_LoadOffice();
	//alert("rcells.item(2).firstChild.nodeValue==>"+rcells.item(2).firstChild.nodeValue);
	document.RentMaster.cmbOffice_code.value = rcells.item(2).firstChild.nodeValue;
	//alert("document.RentMaster.cmbOffice_code.value==>"+document.RentMaster.cmbOffice_code.value);
	//document.RentMaster.cmbOffice_code.value = rcells.item(2).firstChild.nodeValue;
	
      var code =rcells.item(3).firstChild.nodeValue;
      var name =rcells.item(4).firstChild.nodeValue;
      var txtOwnersCode=document.RentMaster.txtOwnersCode.value;
     // alert( name +"/"+ code);
      txtOwnersCode=( name +"/"+ code );
      //alert("txtOwnersCode==>"+txtOwnersCode);
      loadownerName(txtOwnersCode);
     // alert("after function call==>"+txtOwnersCode);
      setTimeout('document.RentMaster.txtOwnersCode.value=txtOwnersCode',300); 
      
      
	//alert(document.getElementById("txtOwnersCode").value);
	//document.RentMaster.txtOwnersName.value = rcells.item(4).firstChild.nodeValue;
	document.RentMaster.txtRentAgreementPeriodFrom.value = rcells.item(5).firstChild.nodeValue;
	document.RentMaster.txtRentAgreementPeriodTo.value = rcells.item(6).firstChild.nodeValue;
	document.RentMaster.txtAdvancePaid.value = rcells.item(7).firstChild.nodeValue;
	document.RentMaster.txtAdvancePaidDate.value = rcells.item(8).firstChild.nodeValue;
	document.RentMaster.txtRentValueasonDate.value = rcells.item(9).firstChild.nodeValue;
	document.RentMaster.txtRentValue.value = rcells.item(10).firstChild.nodeValue;
	document.RentMaster.cboRentalPaymentOption.value = rcells.item(11).firstChild.nodeValue;

//	document.RentMaster.cboPaymentMonth11.value = rcells.item(12).firstChild.nodeValue;
//	document.RentMaster.cboPaymentMonth12.value = rcells.item(13).firstChild.nodeValue;
//	document.RentMaster.cboPaymentMonth13.value = rcells.item(14).firstChild.nodeValue;
//	document.RentMaster.cboPaymentMonth14.value = rcells.item(15).firstChild.nodeValue;
	
	document.RentMaster.txtTDSDeductionifany.value = rcells.item(12).firstChild.nodeValue;
	
	document.RentMaster.txtAnnualAmounttoIT.value = rcells.item(13).firstChild.nodeValue;
	document.RentMaster.txtITPercentage.value = rcells.item(14).firstChild.nodeValue;
//	alert("rcells.item(15).firstChild.nodeValue==>"+rcells.item(15).firstChild.nodeValue)
	
	
	 if(rcells.item(15).firstChild.value=="Y")
         document.RentMaster.radioITExempted[0].checked=true;
	if(rcells.item(15).firstChild.value=="N")
        document.RentMaster.radioITExempted[1].checked=true;
		
	   
	document.RentMaster.officeId.value = rcells.item(16).firstChild.nodeValue;
	callServer('Check','null');
	document.RentMaster.mtxtRemarks.value = rcells.item(17).firstChild.nodeValue;

	document.RentMaster.onsubmit.disabled = true;
	document.RentMaster.ondelete.disabled = false;
	document.RentMaster.onupdate.disabled = false;
}
function refresh() {
	
	var xmlrequest = AjaxFunction();
	//alert("Refresh Function");
	document.RentMaster.cmbAcc_UnitCode.value = "S";
	document.RentMaster.cmbOffice_code.value = "S";
	document.RentMaster.txtOwnersCode.value = "";
	//document.RentMaster.txtOwnersName.value = "";
	
	document.RentMaster.officeId.value = "";
	document.RentMaster.txtemp_office.value = ""; 
	
	document.RentMaster.txtRentAgreementPeriodFrom.value = "";
	document.RentMaster.txtRentAgreementPeriodTo.value = "";
	document.RentMaster.txtAdvancePaid.value = "";
	document.RentMaster.txtAdvancePaidDate.value = "";
	document.RentMaster.txtRentValueasonDate.value = "";
	document.RentMaster.txtRentValue.value = "";
	document.RentMaster.cboRentalPaymentOption.value = "S";

//	document.RentMaster.cboPaymentMonth11.value = "0";
//	document.RentMaster.cboPaymentMonth12.value = "0";
//	document.RentMaster.cboPaymentMonth13.value = "0";
//	document.RentMaster.cboPaymentMonth14.value = "0";

	document.RentMaster.txtTDSDeductionifany.value = "";
	document.RentMaster.txtAnnualAmounttoIT.value = "";
	document.RentMaster.txtITPercentage.value = "";
	
	document.RentMaster.mtxtRemarks.value = "";

	document.RentMaster.onsubmit.disabled = false;
	document.RentMaster.ondelete.disabled = true;
	document.RentMaster.onupdate.disabled = true;
	//document.RentMaster.txtOwnersName.focus;

}
function forRefresh(path) {
	var url = path + "/RentMasterServlet?command=gett1";
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function forRefresh1(baseResponse) {
	refresh();
	var flag2 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag2 == "success1") {
		var ownerCode = baseResponse.getElementsByTagName("ownerCode")[0].firstChild.nodeValue;
		document.RentMaster.txtOwnersCode.value = ownerCode;
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

function exitfun() {
	window.close();
}
/////////////////////////////// Loading office details ////////////////////////////////////////////////////////////////////////////////////////////
function callServer(command, param) {
	if (command == "Check") {
		var txtOffice_Id = document.RentMaster.officeId.value;
		var flag = document.RentMaster.officeId.value;
		if (flag.length > 0) {
			url="../../../../../RentMasterServlet?command=check&txtOffice_Id=" + txtOffice_Id;
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				officeprocessResponse(req);
			};
			req.send(null);
		}
	}
}
function officeprocessResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			updateResponse(baseResponse);
		}
	}
}
function updateResponse(response) {
	var res = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if (res == "success"){
		if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "existing") {
			document.RentMaster.txtemp_office.value=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
		}else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Notexisting") {
			alert("The given office Id does not Exist");
			document.RentMaster.officeId.value="";
			document.RentMaster.txtemp_office.value="";
			document.RentMaster.officeId.focus();
		}else if (response.getElementsByTagName("command")[0].firstChild.nodeValue == "Notexisting_1") {
			alert("The given office Id does not Exist");
			document.RentMaster.officeId.value="";
			document.RentMaster.txtemp_office.value="";
			document.RentMaster.officeId.focus();
		}
	} else {

		alert("Process failure");
	}

}
var winjob;
var txtid1="";
var txtid2="";
function jobpopup(s1,s2) {
	txtid1=s1;
	txtid2=s2;
	
	if (winjob && winjob.open && !winjob.closed) {
//		alert("11111");
		winjob.resizeTo(500, 500);
		winjob.moveTo(250, 250);
		winjob.focus();
		return;
	} else {
		winjob = null;
	}

	winjob = window.open(
			"../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp",
			"JobSearch",
	"status=1,height=500,width=500,resizable=YES, scrollbars=yes");

	winjob.moveTo(250, 250);
	winjob.focus();	
	document.RentMaster.officeId.focus();
}
function doParentJob(jobid, deptid) {
	//document.leave_unavail.dept_id.value = deptid;	
	document.getElementById('officeId').value=jobid;
	//loadOffice(jobid);
	return true;
}
