//			Budget_Heads_Ac_heads_mapping			//

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
			} else if (command == "Edit") {
				Edit1(baseResponse);
			} else if (command == "getGrid") {
				//alert(xmlrequest.responseText);
				firstLoad(baseResponse);
			} else if (command == "LoadMajorandSubBudgetHeads") {
				LoadMajorandSubBudgetHeads1(baseResponse);
			} else if (command == "LoadData") {
				//alert(xmlrequest.responseText);
				LoadData1(baseResponse);
			} else if (command == "getMinorBudgetHeadDesc") {
				getMinorBudgetHeadDesc1(baseResponse);
			} else if (command == "LoadAccHdCode") {
				LoadAccHdCode1(baseResponse);
			} else if (command == "LoadAccHdDesc") {
				LoadAccHdDesc1(baseResponse);
			} else if (command == "getBudgetGroupMinor") {
				getBudgetGroupMinor1(baseResponse);
			}
		}
	}
}
function formateno(path)
{
	//alert(path);
	var url = path + "/Budget_Heads_Ac_heads_mapping?command=formateno";
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {
			  var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
				var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
				if (flag1 == "success") {
					var len4 = baseResponse.getElementsByTagName("FormatNo").length;		
						for ( var i = 0; i < len4; i++) {
							var FormatNo = baseResponse.getElementsByTagName("FormatNo")[i].firstChild.nodeValue;
							var no_value=i+1;
							var se = document.getElementById("cmbFormatNo");
							var op = document.createElement("OPTION");
							op.value = no_value;
							var txt = document.createTextNode(FormatNo);
							op.appendChild(txt);
							se.appendChild(op);
						}		
				} else {
					alert("Fail to Load Format No");
				}
			}
		}

	};
	xmlrequest.send(null);
}



function initialLoad(path) {
	//alert(path);
    var cmbFormatNo=document.getElementById("cmbFormatNo").value;;
    var cmbBudgetGroupMajor=document.getElementById("cmbBudgetGroupMajor").value;
    if(cmbFormatNo == "" && cmbBudgetGroupMajor=="" )
    {
	var url = path + "/Budget_Heads_Ac_heads_mapping?command=getGrid";
	//alert(url);
    } 
    else
    {
    	var url = path + "/Budget_Heads_Ac_heads_mapping?command=getGrid&cmbFormatNo="+cmbFormatNo+"&cmbBudgetGroupMajor="+
    	                  cmbBudgetGroupMajor;
    	//alert(url);
    }
    	
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}

function firstLoad(baseResponse) {
	
	
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
		try{
			document.getElementById('tblList').innerHTML="";
		}catch(e){
			document.getElementById('tblList').innerText="";
		}

		var len = baseResponse.getElementsByTagName("cmbFormatNo").length;
		for ( var k = 0; k < len; k++) {
			var cmbFormatNo = baseResponse.getElementsByTagName("cmbFormatNo")[k].firstChild.nodeValue;
			//var cmbBudgetDescMain = baseResponse.getElementsByTagName("cmbBudgetDescMain")[k].firstChild.nodeValue;
			//var cmbBudgetDescSub = baseResponse.getElementsByTagName("cmbBudgetDescSub")[k].firstChild.nodeValue;
			var txtAcc_HeadCode = baseResponse.getElementsByTagName("txtAcc_HeadCode")[k].firstChild.nodeValue;
			var BudgetGroupMajor = baseResponse.getElementsByTagName("BudgetGroupid")[k].firstChild.nodeValue;
			//var BudgetGroupMinor = baseResponse.getElementsByTagName("BudgetGroupMinor")[k].firstChild.nodeValue;
			var budgetGroupDesc=baseResponse.getElementsByTagName("BudgetGroupMajor")[k].firstChild.nodeValue;
			/*if(BudgetGroupMinor=="s")
				{
				BudgetGroupMinor="";
				}*/
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = cmbFormatNo + BudgetGroupMajor + txtAcc_HeadCode;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbFormatNo + "','"
					+ BudgetGroupMajor + "' ,'" + txtAcc_HeadCode + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var cmbFormatNo = document.createTextNode(cmbFormatNo);
			cell2.appendChild(cmbFormatNo);
			mycurrent_row.appendChild(cell2);

			/*var cell21 = document.createElement("TD");
			var BudgetGroupMajor = document.createTextNode(BudgetGroupMajor);
			cell21.appendChild(BudgetGroupMajor);
			mycurrent_row.appendChild(cell21);*/

			var cell22 = document.createElement("TD");
			var BudgetGroupMinor = document.createTextNode(budgetGroupDesc);
			cell22.appendChild(BudgetGroupMinor);
			mycurrent_row.appendChild(cell22);

			/*var cell3 = document.createElement("TD");
			var cmbBudgetDescMain = document.createTextNode(cmbBudgetDescMain);
			cell3.appendChild(cmbBudgetDescMain);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var cmbBudgetDescSub = document.createTextNode(cmbBudgetDescSub);
			cell4.appendChild(cmbBudgetDescSub);
			mycurrent_row.appendChild(cell4);*/

			var cell5 = document.createElement("TD");
			var txtAcc_HeadCode = document.createTextNode(txtAcc_HeadCode);
			cell5.appendChild(txtAcc_HeadCode);
			mycurrent_row.appendChild(cell5);
			tbody.appendChild(mycurrent_row);

		}
	} else {
		alert("Fail to Load Grid");
	}
}
function LoadMajorandSubBudgetHeads(path) {
	
	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	if (cmbFormatNo == "") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.focus();
	} else {
		//alert("LoadMajorandSubBudgetHeads  calll");
		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=LoadMajorandSubBudgetHeads&cmbFormatNo="
				+ cmbFormatNo;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function LoadMajorandSubBudgetHeads1(baseResponse) {
	document.getElementById("cmbBudgetGroupMajor").length = 1;
	document.getElementById("cmbBudgetDescMain").length = 1;
	document.getElementById("cmbBudgetDescSub").length = 1;
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	var flag11 = baseResponse.getElementsByTagName("flag11")[0].firstChild.nodeValue;
	if (flag1 == "fas") {
		var len4 = baseResponse.getElementsByTagName("BudgetHeadMajorid").length;
		if (len4 > 0) {
			for ( var i = 0; i < len4; i++) {
				var BudgetHeadMajorid = baseResponse
						.getElementsByTagName("BudgetHeadMajorid")[i].firstChild.nodeValue;
				var BudgetHeadMajorDesc = baseResponse
						.getElementsByTagName("BudgetHeadMajorDesc")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbBudgetDescMain");
				var op = document.createElement("OPTION");
				op.value = BudgetHeadMajorid;
				var txt = document.createTextNode(BudgetHeadMajorDesc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			//alert("Balance Sheet Major Does Not Exist");
		}
	} else {
		//alert("Fail to Load Balance Sheet Major Desc");
	}

	if (flag11 == "success") {
		var len44 = baseResponse.getElementsByTagName("BudgetGroupMajorid").length;
		if (len44 > 0) {
			for ( var i = 0; i < len44; i++) {
				var BudgetGroupMajorid = baseResponse
						.getElementsByTagName("BudgetGroupMajorid")[i].firstChild.nodeValue;
				var BudgetGroupMajorDesc = baseResponse
						.getElementsByTagName("BudgetGroupMajorDesc")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbBudgetGroupMajor");
				var op = document.createElement("OPTION");
				op.value = BudgetGroupMajorid;
				var txt = document.createTextNode(BudgetGroupMajorDesc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Group Major Does Not Exist");
		}
	} else {
		alert("Fail to Load Budget Group Major Desc");
	}

}
function getBudgetGroupMinor(path) {
	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	if (cmbFormatNo == "") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.focus();
	} else if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMajor.focus();
	} else {

		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=getBudgetGroupMinor&cmbFormatNo="
				+ cmbFormatNo + "&cmbBudgetGroupMajor=" + cmbBudgetGroupMajor;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function getMinorBudgetHeadDesc(path) {
	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var cmbBudgetDescMain = document.getElementById("cmbBudgetDescMain").value;
	if (cmbFormatNo == "") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.focus();
	} else if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescMain.focus();
	} else {

		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=getMinorBudgetHeadDesc&cmbFormatNo="
				+ cmbFormatNo + "&cmbBudgetDescMain=" + cmbBudgetDescMain;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function getBudgetGroupMinor1(baseResponse) {
	document.getElementById("cmbBudgetGroupMinor").length = 1;
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var len45 = baseResponse.getElementsByTagName("BudgetGroupMinor").length;		
			for ( var i = 0; i < len45; i++) {
				var BudgetGroupMinor = baseResponse
						.getElementsByTagName("BudgetGroupMinor")[i].firstChild.nodeValue;
				var se = document.getElementById("cmbBudgetGroupMinor");
				var op = document.createElement("OPTION");
				op.value = BudgetGroupMinor;
				var txt = document.createTextNode(BudgetGroupMinor);
				op.appendChild(txt);
				se.appendChild(op);
			}		
	} else {
		alert("Fail to Load Budget Group Minor");
	}
}

function getMinorBudgetHeadDesc1(baseResponse) {
	document.getElementById("cmbBudgetDescSub").length = 1;
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var len45 = baseResponse.getElementsByTagName("BudgetIdSub").length;
		if (len45 > 0) {
			for ( var i = 0; i < len45; i++) {
				var BudgetIdSub = baseResponse
						.getElementsByTagName("BudgetIdSub")[i].firstChild.nodeValue;
				var BudgetDescSub = baseResponse
						.getElementsByTagName("BudgetDescSub")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbBudgetDescSub");
				var op = document.createElement("OPTION");
				op.value = BudgetIdSub;
				var txt = document.createTextNode(BudgetDescSub);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Balance Sheet Minor Does Not Exist");
		}
	} else {
		alert("Fail to Load Balance Sheet Minor");
	}
}

function LoadAccHdCode(path) {
	var cmbBudgetDescMain = document.getElementById("cmbBudgetDescMain").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetDescSub").value;
	if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescMain.focus();
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescSub.focus();
	} else {
		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=LoadAccHdCode&cmbBudgetDescMain="
				+ cmbBudgetDescMain + "&cmbBudgetDescSub=" + cmbBudgetDescSub;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function LoadAccHdCode1(baseResponse) {
	document.getElementById("txtAcc_HeadCode").length = 1;
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var len4 = baseResponse.getElementsByTagName("AccHdCode").length;
		if (len4 > 0) {
			for ( var i = 0; i < len4; i++) {
				var AccHdCode = baseResponse.getElementsByTagName("AccHdCode")[i].firstChild.nodeValue;

				var se = document.getElementById("txtAcc_HeadCode");
				var op = document.createElement("OPTION");
				op.value = AccHdCode;
				var txt = document.createTextNode(AccHdCode);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Account Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Account Head Code");
	}
}

function LoadAccHdDesc(path) {
	var cmbBudgetDescMain = document.getElementById("cmbBudgetDescMain").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetDescSub").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescMain.focus();
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescSub.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.txtAcc_HeadCode.focus();
	} else {
		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=LoadAccHdDesc&cmbBudgetDescMain="
				+ cmbBudgetDescMain + "&cmbBudgetDescSub=" + cmbBudgetDescSub
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function LoadAccHdDesc1(baseResponse) {

	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var AccHdDesc = baseResponse.getElementsByTagName("AccHdDesc")[0].firstChild.nodeValue;
		document.getElementById("txtAcc_HeadDesc").value = AccHdDesc;
	} else {
		document.getElementById("txtAcc_HeadCode").value ="";
		document.getElementById("txtAcc_HeadDesc").value ="";
		alert("Account Head Doesn't exist");
	}
}

function add(path) {
	//alert(path);

	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	if (cmbFormatNo == "") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.focus();
	} else if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMajor.focus();
	}  else if (txtAcc_HeadCode == "") {
		alert("Select Acc_Head Code in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.txtAcc_HeadCode.focus();
	} else {

		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=add&cmbFormatNo="
				+ cmbFormatNo +  "&txtAcc_HeadCode="
				+ txtAcc_HeadCode + "&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor ;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function addRow(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
		
		//initialLoad(path);
		
		refresh();

		/*try{
			document.getElementById('tblList').innerHTML="";
		}catch(e){
			document.getElementById('tblList').innerText="";
		}*/

			var cmbFormatNo = baseResponse.getElementsByTagName("cmbFormatNo")[0].firstChild.nodeValue;
			//alert(cmbFormatNo);
			//var cmbBudgetDescMain = baseResponse.getElementsByTagName("cmbBudgetDescMain")[k].firstChild.nodeValue;
			//var cmbBudgetDescSub = baseResponse.getElementsByTagName("cmbBudgetDescSub")[k].firstChild.nodeValue;
			var txtAcc_HeadCode = baseResponse.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;
			//alert(txtAcc_HeadCode);
			var BudgetGroupMajor = baseResponse.getElementsByTagName("BudgetGroupid")[0].firstChild.nodeValue;
			//alert(BudgetGroupMajor);
			//var BudgetGroupMinor = baseResponse.getElementsByTagName("BudgetGroupMinor")[k].firstChild.nodeValue;
			var budgetGroupDesc=baseResponse.getElementsByTagName("BudgetGroupMajor")[0].firstChild.nodeValue;
			//alert(BudgetGroupMajor);
			/*if(BudgetGroupMinor=="s")
				{
				BudgetGroupMinor="";
				}*/
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = cmbFormatNo + BudgetGroupMajor + txtAcc_HeadCode;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbFormatNo + "','"
					+ BudgetGroupMajor + "' ,'" + txtAcc_HeadCode + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2=document.createElement("TD");
			var formateno=document.createTextNode(cmbFormatNo);
			cell2.appendChild(formateno);
			mycurrent_row.appendChild(cell2);
			
			var cell2=document.createElement("TD");
			var budgetgroup=document.createTextNode(budgetGroupDesc);
			cell2.appendChild(budgetgroup);
			mycurrent_row.appendChild(cell2);
			
			var cell2=document.createElement("TD");
			var acchead=document.createTextNode(txtAcc_HeadCode);
			cell2.appendChild(acchead);
			mycurrent_row.appendChild(cell2);
            
			tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Datas are Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(cmbFormatNo,BudgetGroupMajor, txtAcc_HeadCode) {
	//alert("edittttttttttttttt");
	document.getElementById("cmbFormatNo").disabled = true;	
	document.getElementById("cmbBudgetGroupMajor").disabled = true;		
	//document.getElementById("cmbBudgetDescMain").disabled = true;	
	//document.getElementById("cmbBudgetDescSub").disabled = true;		
	document.getElementById("txtAcc_HeadCode").disabled = true;
	//document.getElementById("flag").value = "1";
	//document.getElementById("txtAcc_HeadDesc").value = "";
	var url = "../../../../../Budget_Heads_Ac_heads_mapping?command=LoadData&cmbFormatNo="
			+ cmbFormatNo
			+ "&cmbBudgetGroupMajor="
			+ BudgetGroupMajor + "&txtAcc_HeadCode="
			+ txtAcc_HeadCode;
    //alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function LoadData1(baseResponse) {	
	/*document.getElementById("cmbBudgetGroupMajor").length = 1;
	document.getElementById("cmbBudgetGroupMinor").length = 1;
	document.getElementById("cmbBudgetDescMain").length = 1;
	document.getElementById("cmbBudgetDescSub").length = 1;
	document.getElementById("txtAcc_HeadCode").length = 1;*/
	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == "success") {
	
		var formateno = baseResponse
				.getElementsByTagName("formateNo")[0].firstChild.nodeValue;	
		//alert(formateno);
		var BudgetGroupid = baseResponse
				.getElementsByTagName("BudgetGroupid")[0].firstChild.nodeValue;
		var BudgetGroupMajorDesc = baseResponse
				.getElementsByTagName("BudgetGroupMajorDesc")[0].firstChild.nodeValue;
		var AccHdCode = baseResponse
				.getElementsByTagName("AccHdCode")[0].firstChild.nodeValue;	
		//alert(AccHdCode);
		var AccHdDesc = baseResponse
				.getElementsByTagName("AccHdDesc")[0].firstChild.nodeValue;
		
		
		var se = document.getElementById("cmbFormatNo");
		var op = document.createElement("OPTION");
		op.value = formateno;
		var txt = document.createTextNode(formateno);
		op.appendChild(txt);
		se.appendChild(op);
		document.getElementById("cmbFormatNo").value=formateno;
		
		var se = document.getElementById("cmbBudgetGroupMajor");
		var op = document.createElement("OPTION");
		op.value = BudgetGroupid;
		var txt = document.createTextNode(BudgetGroupMajorDesc);
		op.appendChild(txt);
		se.appendChild(op);		
		document.getElementById("cmbBudgetGroupMajor").value=BudgetGroupid;
		
		var se = document.getElementById("txtAcc_HeadCode");
		var op = document.createElement("OPTION");
		op.value = AccHdCode;
		var txt = document.createTextNode(AccHdCode);
		op.appendChild(txt);
		se.appendChild(op);
		document.getElementById("txtAcc_HeadCode").value=AccHdCode;
		//alert("acccode"+document.getElementById("txtAcc_HeadCode").value);
	    document.getElementById("txtAcc_HeadDesc").value=AccHdDesc;
		
		
		
		document.frmBudget_Heads_Ac_heads_mapping.onsubmit.disabled = true;
		document.frmBudget_Heads_Ac_heads_mapping.ondelete.disabled = false;
		document.frmBudget_Heads_Ac_heads_mapping.onupdate.disabled = false;
		/*var se = document.getElementById("cmbBudgetDescMain");
		var op = document.createElement("OPTION");
		op.value = BudgetHeadMajorid;
		var txt = document.createTextNode(BudgetHeadMajorDesc);
		op.appendChild(txt);
		se.appendChild(op);

		var se = document.getElementById("cmbBudgetDescSub");
		var op = document.createElement("OPTION");
		op.value = BudgetHeadSubId;
		var txt = document.createTextNode(BudgetHeadSubDesc);
		op.appendChild(txt);
		se.appendChild(op);*/

		//document.getElementById("txtAcc_HeadDesc").value = AccHdDesc;
	
	}
	/*var len44 = baseResponse.getElementsByTagName("AccHdCode").length;
	for ( var i = 0; i < len44; i++) {
	var AccHdCode = baseResponse.getElementsByTagName("AccHdCode")[i].firstChild.nodeValue;
	var se = document.getElementById("txtAcc_HeadCode");
	var op = document.createElement("OPTION");
	op.value = AccHdCode;
	var txt = document.createTextNode(AccHdCode);
	op.appendChild(txt);
	se.appendChild(op);
	}
	
	var len45 = baseResponse.getElementsByTagName("BudgetGroupMinorDesc").length;
	for ( var i = 0; i < len45; i++) {
		var BudgetGroupMinorDesc = baseResponse
		.getElementsByTagName("BudgetGroupMinorDesc")[i].firstChild.nodeValue;
		if(BudgetGroupMinorDesc=="s")
		{
			BudgetGroupMinorDesc="";
			document.getElementById("cmbBudgetGroupMinor").length=1;
		}
		var se = document.getElementById("cmbBudgetGroupMinor");
		var op = document.createElement("OPTION");
		op.value = BudgetGroupMinorDesc;
		var txt = document.createTextNode(BudgetGroupMinorDesc);
		op.appendChild(txt);
		se.appendChild(op);
	}
	
	var FormatNo = baseResponse.getElementsByTagName("FormatNo")[0].firstChild.nodeValue;
	var BudgetDescMain = baseResponse.getElementsByTagName("BudgetDescMain")[0].firstChild.nodeValue;
	var BudgetDescSub = baseResponse.getElementsByTagName("BudgetDescSub")[0].firstChild.nodeValue;
	var BudgetGroupMain = baseResponse.getElementsByTagName("BudgetGroupMain")[0].firstChild.nodeValue;
	var Acc_HeadCode = baseResponse.getElementsByTagName("Acc_HeadCode")[0].firstChild.nodeValue;

	var r = document.getElementById(FormatNo + BudgetGroupMain + Acc_HeadCode);
	var rcells = r.cells;
	document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.value = rcells
			.item(1).firstChild.nodeValue;
	document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMajor.value = rcells
			.item(2).firstChild.nodeValue;*/
	/*if(rcells.item(3).firstChild.nodeValue=="")
		{
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMinor.value="s";
		}else{
	document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMinor.value = rcells
			.item(3).firstChild.nodeValue;
		}*/
	/*document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescMain.value = rcells
			.item(4).firstChild.nodeValue;
	document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescSub.value = rcells
			.item(5).firstChild.nodeValue;*/
	/*document.frmBudget_Heads_Ac_heads_mapping.txtAcc_HeadCode.value = rcells
			.item(4).firstChild.nodeValue;*/
	//document.getElementById("txtAcc_HeadDesc").value=baseResponse.getElementsByTagName("AccHdCode")[i].firstChild.nodeValue
	
}

function update(path) {
	//alert(path);
	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	var cmbBudgetDescMain = document.getElementById("cmbBudgetDescMain").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetDescSub").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	if (cmbFormatNo == "") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.focus();
	} else if (cmbBudgetGroupMajor == "s") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMajor.focus();
	} else if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc_Head Code in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.txtAcc_HeadCode.focus();
	} else {

		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=update&cmbFormatNo="
				+ cmbFormatNo + "&cmbBudgetDescMain=" + cmbBudgetDescMain
				+ "&cmbBudgetDescSub=" + cmbBudgetDescSub + "&txtAcc_HeadCode="
				+ txtAcc_HeadCode + "&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&cmbBudgetGroupMinor="
				+ cmbBudgetGroupMinor;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function updateRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
		var items = new Array();
		items[0] = baseResponse.getElementsByTagName("cmbFormatNo")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("BudgetGroupMajor")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("BudgetGroupMinor")[0].firstChild.nodeValue;
		items[3] = baseResponse.getElementsByTagName("cmbBudgetDescMain")[0].firstChild.nodeValue;
		items[4] = baseResponse.getElementsByTagName("cmbBudgetDescSub")[0].firstChild.nodeValue;
		items[5] = baseResponse.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;

		var r = document.getElementById(items[0] + items[3] + items[4] + items[1] + items[5]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
		rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];
		rcells.item(6).firstChild.nodeValue = items[5];
		NoData
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Failed to update values");
	}
}

function deleteeee(path) {
	//alert(path);
	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	
	if (cmbFormatNo == "") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.focus();
	} else if (cmbBudgetGroupMajor == "s") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMajor.focus();
	} else if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
	} else {
		var r = confirm("Are U Sure?");
		if (r == true) {
			var url = path
					+ "/Budget_Heads_Ac_heads_mapping?command=deleted&cmbFormatNo="
					+ cmbFormatNo + "&cmbBudgetGroupMajor=" + cmbBudgetGroupMajor + "&txtAcc_HeadCode="
					+ txtAcc_HeadCode;

			// alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
			xmlrequest.send(null);
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
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function refresh() {
	document.getElementById("cmbFormatNo").disabled = false;	
	document.getElementById("cmbBudgetGroupMajor").disabled = false;		
	document.getElementById("cmbBudgetDescMain").disabled = false;	
	document.getElementById("cmbBudgetDescSub").disabled = false;	
	document.getElementById("txtAcc_HeadCode").disabled = false;
	document.frmBudget_Heads_Ac_heads_mapping.cmbFormatNo.value = "";
	document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMajor.value = "";
	document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetGroupMinor.value = "";
	document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescMain.value = "";
	document.frmBudget_Heads_Ac_heads_mapping.cmbBudgetDescSub.value = "";
	document.frmBudget_Heads_Ac_heads_mapping.txtAcc_HeadCode.value = "";
	document.frmBudget_Heads_Ac_heads_mapping.txtAcc_HeadDesc.value = "";

	document.frmBudget_Heads_Ac_heads_mapping.onsubmit.disabled = false;
	document.frmBudget_Heads_Ac_heads_mapping.ondelete.disabled = true;
	document.frmBudget_Heads_Ac_heads_mapping.onupdate.disabled = true;

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
