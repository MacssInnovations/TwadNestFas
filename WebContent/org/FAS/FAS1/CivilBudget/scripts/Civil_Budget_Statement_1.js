//	Civil_Budget_Statement_1	//
var seq = 0;
var seq1 = 1;
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

function manipulate1(xmlrequest) {
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
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "get") {
				LoadGrid1(baseResponse);
			}
			else if (command == "get_head_office") {
				LoadGrid_head(baseResponse);
			}
			
			else if (command == "LoadData") {
				LoadData_View1(baseResponse);
			} else if (command == "getStatementName") {
				getStatementName1(baseResponse);
			}
			else if(command=="getofficeid"){
				loadofficeid(baseResponse);
			}
			else if(command=="getOfficeName"){
				loadofficename(baseResponse);
			}
			else if (command=="CheckFreeze")
			{
				CheckFreeze_res(baseResponse);
			}
			
		}
	}
}
function load()
{
	var url = "../../../../../Civil_Budget_Statement_1?command=getofficeid";
//alert(url);
var xmlrequest = AjaxFunction();
//alert("11111");
xmlrequest.open("GET", url, true);
//alert("22222");
xmlrequest.onreadystatechange = function() {
	//alert("3333");
	manipulate1(xmlrequest);
	//alert("444");
};
//alert("555");
xmlrequest.send(null);

}
function CheckFreeze()
{
	
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
//alert(" cmbAcc_UnitCode "+cmbAcc_UnitCode);
var cmbOffice_code=document.getElementById("txtRegionId").value;
var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;

var cmbStatementName=document.getElementById("cmbStatementName").value;
//var statementGp=document.getElementById("statementGp").value;
	
	var url ="../../../../../Civil_Budget_Statement_1?command=CheckFreeze" +
			"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"" +
			"&cmbFinancialYear="+cmbFinancialYear+""+
			"&cmbStatementName="+cmbStatementName;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}
function CheckFreeze_res(baseResponse)
{
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="Freeze")
	{
		
		alert("Allocation is already Frozen ");
		var tbody = document.getElementById("grid_body_two");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
		document.frmCivil_Budget_Statement_1.butSub.disabled = true;
		document.frmCivil_Budget_Statement_1.butDelete.disabled = true;
		//document.frmCivil_Budget_Statement_1.butUpdate.disabled = true;
		

	}
	else if(flag=="NotFreeze"){
		//alert("Not Freeze");
	}else{
		alert("Error ");
	}
}
function checkNull(){
    var cmbAcc_UnitCode=document.frmCivil_Budget_Statement_1.cmbAcc_UnitCode.value;
       var txtRegionId = document.frmCivil_Budget_Statement_1.txtRegionId.value;
       var cmbStatementName=document.frmCivil_Budget_Statement_1.cmbStatementName.value;
       var financial_year = document.frmCivil_Budget_Statement_1.cmbFinancialYear.value;
       if(cmbAcc_UnitCode==0){
           alert("Select Accounting unit Id");
           return false;
       }
       else if(txtRegionId==0){
          alert("Select RegionId ") ;
          return false;
       }
       else if(financial_year==""){
           alert("select Finanical year");
           return false;
       }else if(cmbStatementName==""){
           alert("select Statement Name");
           return false;
       }
       return true;
}

function LoadOfficeName()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var url = "../../../../../Civil_Budget_Statement_1?command=getOfficeName&cmbAcc_UnitCode=" + cmbAcc_UnitCode;
//alert(url);
var xmlrequest = AjaxFunction();
//alert("11111");
xmlrequest.open("GET", url, true);
//alert("22222");
xmlrequest.onreadystatechange = function() {
	//alert("3333");
	manipulate1(xmlrequest);
	//alert("444");
};
//alert("555");
xmlrequest.send(null);
}

function loadstatname()
{
	
	
	var url = "../../../../../Civil_Budget_Statement_1?command=getStatementName";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
}

function initialLoad(path) {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear = 0;
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear = year1 + "-" + year;
	} else {
		financialyear = year + "-" + year1;
	}
	var se = document.getElementById("cmbFinancialYear");
	var op = document.createElement("OPTION");
	op.value = financialyear;
	var txt = document.createTextNode(financialyear);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbFinancialYear").value = financialyear;

	//document.frmCivil_Budget_Statement_1.butView.disabled = false;
	document.frmCivil_Budget_Statement_1.butSub.disabled = false;
	document.frmCivil_Budget_Statement_1.butDelete.disabled = true;
	//document.frmCivil_Budget_Statement_1.butUpdate.disabled = true;

	var url = path + "/Civil_Budget_Statement_1?command=getStatementName";
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);
}

function getStatementName1(baseResponse) {
	document.frmCivil_Budget_Statement_1.cmbStatementName.length = 1;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;
		for ( var i = 0; i < len4; i++) {
			var STATEMENT_NO = baseResponse
					.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
			var STATEMENT_DESC = baseResponse
					.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbStatementName");
			var op = document.createElement("OPTION");
			op.value = STATEMENT_NO;
			var txt = document.createTextNode(STATEMENT_DESC);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Statement Name");
	}
}
function loadofficeid(baseResponse) {
	//alert("catherine");
	//System.out.println("inside the office id generation");
	document.frmCivil_Budget_Statement_1.cmbAcc_UnitCode.length = 0;
	//alert("flag");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert("flag"+flag);
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID").length;
		for ( var i = 0; i < len4; i++) {
			var ACCOUNTING_UNIT_ID = baseResponse
					.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
			var ACCOUNTING_UNIT_NAME = baseResponse
					.getElementsByTagName("ACCOUNTING_UNIT_NAME")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbAcc_UnitCode");
			var op = document.createElement("OPTION");
			op.value = ACCOUNTING_UNIT_ID;
			var txt = document.createTextNode(ACCOUNTING_UNIT_NAME);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Accounting Unit Name");
	}
}

function loadofficename(baseResponse) {
	//alert("catherine");
	//System.out.println("inside the office id generation");
	document.frmCivil_Budget_Statement_1.cmbOffice_code.length = 1;
	//alert("flag");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert("flag"+flag);
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID").length;
		for ( var i = 0; i < len4; i++) {
			var ACCOUNTING_UNIT_ID = baseResponse
					.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
			var ACCOUNTING_UNIT_NAME = baseResponse
					.getElementsByTagName("ACCOUNTING_UNIT_NAME")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbOffice_code");
			var op = document.createElement("OPTION");
			op.value = ACCOUNTING_UNIT_ID;
			var txt = document.createTextNode(ACCOUNTING_UNIT_NAME);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Accounting Unit Name");
	}
}
function LoadGrid() {

	var fy = document.getElementById("cmbFinancialYear").value;
	var fy1 = fy.split('-');
	var y1 = fy1[0];
	var y2 = fy1[1];

	//document.frmCivil_Budget_Statement_1.butView.disabled = false;
	document.frmCivil_Budget_Statement_1.butSub.disabled = false;
	document.frmCivil_Budget_Statement_1.butDelete.disabled = true;
	//document.frmCivil_Budget_Statement_1.butUpdate.disabled = true;

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("txtRegionId").value;
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmCivil_Budget_Statement_1.cmbStatementName.focus();
	} else {
			
			
				var url = "../../../../../Civil_Budget_Statement_1?command=get_head_office&y1="
					+ y1 + "&y2=" + y2 + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
					+ "&cmbOffice_code=" + cmbOffice_code + "&cmbStatementName="
					+ cmbStatementName+"&fy="+fy;
			//alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("GET", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate1(xmlrequest);
			};
	
			xmlrequest.send(null);	
			}  
	

}

function LoadGrid_head(baseResponse)
{

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	//	alert("flag:::"+flag);
		
		var tbody = document.getElementById("grid_body_two");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var r_no = baseResponse.getElementsByTagName("STATEMENT_GROUP_NO");
		seq = 0;
		
		var item = new Array();
		for ( var k = 0; k < r_no.length; k++) {

			item[0] = baseResponse.getElementsByTagName("STATEMENT_GROUP_NO")[k].firstChild.nodeValue;
			item[1] = baseResponse.getElementsByTagName("STATEMENT_GROUP_DESC")[k].firstChild.nodeValue;
			item[2]= baseResponse.getElementsByTagName("already")[0].firstChild.nodeValue;

			if(item[2]>0)
			{
				item[3] = baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
				
				
			}
			/** Create Table Row */
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;

			/** Sl No */
			var cell0 = document.createElement("TD");
			var slno = document.createTextNode(seq + 1);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);

			/** Head of Account */
			var cell2 = document.createElement("TD");
			var Head_of_Account = document.createElement('TEXTAREA', 'option1');
			Head_of_Account.name = "Head_of_Account" + seq;
			Head_of_Account.id = "Head_of_Account" + seq;
			Head_of_Account.value = item[1];
			Head_of_Account.readOnly = true;
			Head_of_Account.setAttribute("cols", "5");
			Head_of_Account.style.height = "20px";
			Head_of_Account.style.width = "450px";
			cell2.appendChild(Head_of_Account);
			mycurrent_row.appendChild(cell2);

		
			if(item[2]>0)
			{
				item[3] = baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue;
				var cell20 = document.createElement("TD");
				var Amount = document.createElement("input");
				Amount.type = "Text";
				Amount.name = "Amount" + seq;
				Amount.id = "Amount" + seq;
				Amount.value = item[3];
				Amount.style.textAlign = "right";
				Amount.setAttribute('onblur', "focus_next("+seq+","+r_no.length+")");
				cell20.appendChild(Amount);
				mycurrent_row.appendChild(cell20);
			}
			else
			{
				var cell20 = document.createElement("TD");
				var Amount = document.createElement("input");
				Amount.type = "Text";
				Amount.name = "Amount" + seq;
				Amount.id = "Amount" + seq;
				Amount.value = "";
				Amount.style.textAlign = "right";
				Amount.setAttribute('onblur', "focus_next("+seq+","+r_no.length+")");
				//Amount.setAttribute('onchange', "CalculateTotalAmount()");
				//Amount.maxLength = "10";
				//Amount.size = "10";
				cell20.appendChild(Amount);
				var currentText = document.createTextNode("");
				cell20.appendChild(currentText);
				mycurrent_row.appendChild(cell20);
			}
			
			

			/** Statement_Group_No */
			var Statement_Group_No = document.createElement("input");
			Statement_Group_No.setAttribute("type", "hidden");
			Statement_Group_No.setAttribute("value", item[0]);
			Statement_Group_No.setAttribute("name", "Statement_Group_No" + seq);
			Statement_Group_No.setAttribute("id", "Statement_Group_No" + seq);
			document.getElementById("frmCivil_Budget_Statement_1").appendChild(
					Statement_Group_No);
			tbody.appendChild(mycurrent_row);

			/** Increment Sequence Number */
			seq = seq + 1;
		}
		document.getElementById("RecordCount").value = seq;
		if(item[2]>0)
		{
			var tamount=baseResponse.getElementsByTagName("ttl_amount")[0].firstChild.nodeValue;
			document.getElementById("txtTotalAmount").value= tamount;
			var Remarks=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
			var REF_NO=baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
			document.getElementById("rem").value= Remarks;
			document.getElementById("refno").value= REF_NO;
			
		}
		else
		{
			document.getElementById("txtTotalAmount").value="";
		//	document.getElementById("rem").value= "";
		//	document.getElementById("refno").value= "";
		}
	} else if (flag == "Exist") {
		alert("Statement have Already Freezed");
		clrForm1();
	} else {
		alert("Failed to Load Data");
	}	
}


function LoadData(path) {
	//alert(path);
	//document.frmCivil_Budget_Statement_1.butView.disabled = false;
	document.frmCivil_Budget_Statement_1.butSub.disabled = true;
	document.frmCivil_Budget_Statement_1.butDelete.disabled = false;
	//document.frmCivil_Budget_Statement_1.butUpdate.disabled = false;

	document.getElementById("filter").value = "view";
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("txtRegionId").value;
	var cmbFinancialYear = document.getElementById("cmbFinancialYear").value;
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var RecordCount = 0;
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmCivil_Budget_Statement_1.cmbStatementName.focus();
	}
	else if(cmbOffice_code=="")
	{
		alert("Select Region Name");
		document.frmCivil_Budget_Statement_1.txtRegionId.focus();
	}
	else if(cmbFinancialYear=="")
	{
		alert("Select Financial Year");
		document.frmCivil_Budget_Statement_1.cmbFinancialYear.focus();
	}
	else {
	var url = path + "/Civil_Budget_Statement_1?filter=view&cmbFinancialYear="
			+ cmbFinancialYear + "&cmbAcc_UnitCode=" + cmbAcc_UnitCode
			+ "&txtRegionId=" + cmbOffice_code + "&RecordCount="
			+ RecordCount + "&cmbStatementName="
			+ cmbStatementName;
	alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
	};

	xmlrequest.send(null);
	}

}



function funcSave() {
	var gridAmt=0;
	document.getElementById("filter").value = "save";
	if(document.getElementById("refno").value=="")
	{
		alert("Enter RefNo");
		return false;
	}
	if(document.getElementById("rem").value=="")
	{
		alert("Enter Remarks");
		return false;
	}
	var totalAmt=document.getElementById("txtTotalAmount").value;
	if(document.getElementById("txtRegionId").value==5000)
			{
				var tbody = document.getElementById("grid_body_two");
				var rowcount = tbody.rows.length;
				if (rowcount != 0) {
						for ( var i = 0; i < rowcount; i++) {
							if (document.getElementById("Amount" + i).value == "") {
								alert("Enter Amount in the Field");
								document.getElementById("Amount" + i).focus();
								return false;
							}
							else
							{
								
								gridAmt=(gridAmt+parseFloat(document.getElementById("Amount" + i).value));
								//gridAmt=gridAmt+parseDouble(document.getElementById("Amount" + i).value);
								//gridAmt=gridAmt+parseInt(document.getElementById("Amount" + i).value);
								//alert("be4");
								//alert(gridAmt);
							}
						}
						//alert("totalAmt:::"+totalAmt);
						//alert("g:::"+parseInt(gridAmt));
						//alert("double:::"+parseDouble(gridAmt));
						if(totalAmt!=parseFloat(gridAmt))
						{
							alert("Total Amount Doesn't Tally For HeadOffice");
							return false;
						}
				} else {
					alert("No Records Found to Insert...");
					return false;
				}	 
			}
	else
		{
				var tbody = document.getElementById("grid_body_one");
				var rowcount = tbody.rows.length;
				if (rowcount != 0) {
						for ( var i = 0; i < rowcount; i++) 
						{
							if (document.getElementById("Amount" + i).value == "") 
							{
								alert("Enter Amount in the Field");
								document.getElementById("Amount" + i).focus();
								return false;
							}
							else
							{
								gridAmt=gridAmt+parseInt(document.getElementById("Amount" + i).value);
							}
						}
						if(totalAmt!=parseInt(gridAmt))
						{
							alert("Total Amount Doesn't Tally");
							return false;
						}
				} else {
					alert("No Records Found to Insert...");
					return false;
				}
	}
	return true;
}
function funcUpdate() {
	document.getElementById("filter").value = "update";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	if (rowcount != 0) {
		for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("Amount" + i).value == "") {
				alert("Enter Amount in the Field");
				document.getElementById("Amount" + i).focus();
				return false;
			} 
		}
	} else {
		alert("No Records Found to Insert...");
		return false;
	}
	return true;
}

function funcDelete() {
	var r = confirm("Are U Sure to Continue?");
	if (r == true) {
		document.getElementById("filter").value = "delete";
		return true;
	} else {
		return false;
	}
}

function focus_save()
{
	document.getElementById("butSub").focus();
}

function focus_next(ii,len)
{
	//	alert(ii+"::::"+len);
		if(ii==(len-1))
		{
			document.getElementById("txtTotalAmount").focus();
		}
		else{
			var orderno=ii+1;
			document.getElementById("Amount" +orderno).focus();
		}
}

function CalculateTotalAmount() {
	document.getElementById("filter").value = "update";
	var tbody = document.getElementById("grid_body");
	var rowcount = tbody.rows.length;
	var total_Amt=0;
		for ( var i = 0; i < rowcount; i++) {
			if (document.getElementById("Amount" + i).value != "") {
			total_Amt=parseFloat(total_Amt)+parseFloat(document.getElementById("Amount" + i).value);			
			}
			} 				
		document.getElementById("txtTotalAmount").value = total_Amt;
		document.getElementById("txtTotalAmount").style.textAlign = "right";
}

function clrForm1() {
	/** Delete Existing Values from Grid */
	var tbody = document.getElementById("grid_body");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	document.getElementById("cmbStatementName").value="";
	document.getElementById("txtTotalAmount").value="";
	
	//document.frmCivil_Budget_Statement_1.butView.disabled = false;
	document.frmCivil_Budget_Statement_1.butSub.disabled = false;
	document.frmCivil_Budget_Statement_1.butDelete.disabled = true;
//	document.frmCivil_Budget_Statement_1.butUpdate.disabled = true;
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

function exitfun() {
	window.close();
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{

}
