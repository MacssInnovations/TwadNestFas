//			MIS_Major_Group_Acc_Head_Mapping			//
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
			} else if (command == "FirstLoad1") {
				document.getElementById('ajax').style.width=0;
				document.getElementById('ajax').style.height=0;
				document.getElementById('ajax').style.visibility='hidden';
				firstLoad(baseResponse);
			} else if (command == "LoadMajorandSubBudgetHeads") {
				LoadMajorandSubBudgetHeads1(baseResponse);
			} else if (command == "LoadData") {
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

function deletee(path) {
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMajor
				.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbGroup_Head_Code.focus();
	} else if (cmbBudgetGroupMinor == "") {
		alert("Select Budget Group Minor in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMinor
				.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadCode.focus();
	} else {
		var r = confirm("Are U Sure?");
		if (r == true) {
			var url = path
			+ "/MIS_Major_Group_Acc_Head_Mapping?command=deleted&cmbBudgetGroupMajor="
			+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
			+ cmbGroup_Head_Code + "&cmbBudgetGroupMinor="
			+ cmbBudgetGroupMinor + "&txtAcc_HeadCode=" + txtAcc_HeadCode;
			
			//alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST",url,true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
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
		alert("Records Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function deleteExp(path){
    var xmlrequest = AjaxFunction();
    var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMajor
				.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbGroup_Head_Code.focus();
	} else if (cmbBudgetGroupMinor == "") {
		alert("Select Budget Group Minor in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMinor
				.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadCode.focus();
	} else {
		var r = confirm("Are U Sure?");
		if (r == true) {
			var url = path
			+ "/EXP_Major_Group_Acc_Head_Mapping?command=deleteExp&cmbBudgetGroupMajor="
			+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
			+ cmbGroup_Head_Code + "&cmbBudgetGroupMinor="
			+ cmbBudgetGroupMinor + "&txtAcc_HeadCode=" + txtAcc_HeadCode;
		  }
		}
			//alert(url);
			
			xmlrequest.open("POST",url,true);
			xmlrequest.onreadystatechange = function() {
			if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
			
			
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			deleteRow(baseResponse);
	 		
	
   }
 }
};
xmlrequest.send(null);
}
function DeleteValue(path){
if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[0].checked==true){
	//alert("test "+document.getElementById('checke1').value);
	deletee(path);
	}else if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[1].checked==true){
	//alert("test "+document.getElementById('checke2').value);
	deleteExp(path);
	}
}
function loadValuesFromTable(cmbBudgetGroupMajor, cmbGroup_Head_Code,
		cmbBudgetGroupMinor, txtAcc_HeadCode) {
	document.getElementById("cmbBudgetGroupMajor").disabled = true;
	document.getElementById("cmbGroup_Head_Code").disabled = true;
	document.getElementById("cmbBudgetGroupMinor").disabled = true;
	document.getElementById("txtAcc_HeadCode").disabled = true;
	document.getElementById("txtAcc_HeadDesc").value = "";
	var url = "../../../../../MIS_Major_Group_Acc_Head_Mapping?command=LoadData&cmbBudgetGroupMajor="
			+ cmbBudgetGroupMajor
			+ "&cmbGroup_Head_Code="
			+ cmbGroup_Head_Code
			+ "&cmbBudgetGroupMinor="
			+ cmbBudgetGroupMinor
			+ "&txtAcc_HeadCode=" + txtAcc_HeadCode;

	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function LoadData1(baseResponse) {

	document.getElementById("cmbGroup_Head_Code").length = 1;
	document.getElementById("cmbBudgetGroupMinor").length = 1;
	document.getElementById("txtAcc_HeadCode").length = 1;

	var cmbBudgetGroupMajor1 = baseResponse
			.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
	var cmbGroup_Head_Code1 = baseResponse
			.getElementsByTagName("cmbGroup_Head_Code")[0].firstChild.nodeValue;
	var cmbGroup_Head_Desc1 = baseResponse
			.getElementsByTagName("cmbGroup_Head_Desc")[0].firstChild.nodeValue;
	var cmbBudgetGroupMinor1 = baseResponse
			.getElementsByTagName("cmbBudgetGroupMinor")[0].firstChild.nodeValue;
	var cmbBudgetGroupMinorDesc1 = baseResponse
			.getElementsByTagName("cmbBudgetGroupMinorDesc")[0].firstChild.nodeValue;
	var txtAcc_HeadCode1 = baseResponse.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;
	var txtAcc_HeadCodeDesc1 = baseResponse
			.getElementsByTagName("txtAcc_HeadCodeDesc")[0].firstChild.nodeValue;
	
	document.getElementById("cmbBudgetGroupMajor").value = cmbBudgetGroupMajor1;
	
	var se = document.getElementById("cmbGroup_Head_Code");
	var op = document.createElement("OPTION");
	op.value = cmbGroup_Head_Code1;
	var txt = document.createTextNode(cmbGroup_Head_Desc1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbGroup_Head_Code").value = cmbGroup_Head_Code1;
	
	var se = document.getElementById("cmbBudgetGroupMinor");
	var op = document.createElement("OPTION");
	op.value = cmbBudgetGroupMinor1;
	var txt = document.createTextNode(cmbBudgetGroupMinorDesc1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbBudgetGroupMinor").value = cmbBudgetGroupMinor1;		

	var se = document.getElementById("txtAcc_HeadCode");
	var op = document.createElement("OPTION");
	op.value = txtAcc_HeadCode1;
	var txt = document.createTextNode(txtAcc_HeadCode1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("txtAcc_HeadCode").value = txtAcc_HeadCode1;
	document.getElementById("txtAcc_HeadDesc").value = txtAcc_HeadCodeDesc1;
	
	document.frmMIS_Major_Group_Acc_Head_Mapping.onsubmit.disabled = true;
	document.frmMIS_Major_Group_Acc_Head_Mapping.ondelete.disabled = false;

}


function add(path) {
	//alert(path);
	var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMajor
				.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbGroup_Head_Code.focus();
	} else if (cmbBudgetGroupMinor == "") {
		alert("Select Budget Group Minor in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMinor
				.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadCode.focus();
	} else {

		var url = path
				+ "/MIS_Major_Group_Acc_Head_Mapping?command=add&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
				+ cmbGroup_Head_Code + "&cmbBudgetGroupMinor="
				+ cmbBudgetGroupMinor + "&txtAcc_HeadCode=" + txtAcc_HeadCode ;

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

		refresh();

		var cmbBudgetGroupMajor = baseResponse
				.getElementsByTagName("cmbBudgetGroupMajor")[0].firstChild.nodeValue;
		var cmbGroup_Head_Code = baseResponse
				.getElementsByTagName("cmbGroup_Head_Code")[0].firstChild.nodeValue;
		var cmbBudgetGroupMinor = baseResponse
				.getElementsByTagName("cmbBudgetGroupMinor")[0].firstChild.nodeValue;
		var txtAcc_HeadCode = baseResponse
				.getElementsByTagName("txtAcc_HeadCode")[0].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = cmbBudgetGroupMajor + cmbGroup_Head_Code
				+ cmbBudgetGroupMinor + txtAcc_HeadCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbBudgetGroupMajor
				+ "','" + cmbGroup_Head_Code + "','" + cmbBudgetGroupMinor
				+ "','" + txtAcc_HeadCode + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var cmbBudgetGroupMajor = document.createTextNode(cmbBudgetGroupMajor);
		cell2.appendChild(cmbBudgetGroupMajor);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var cmbGroup_Head_Code = document.createTextNode(cmbGroup_Head_Code);
		cell3.appendChild(cmbGroup_Head_Code);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var cmbBudgetGroupMinor = document.createTextNode(cmbBudgetGroupMinor);
		cell4.appendChild(cmbBudgetGroupMinor);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var txtAcc_HeadCode = document.createTextNode(txtAcc_HeadCode);
		cell5.appendChild(txtAcc_HeadCode);
		mycurrent_row.appendChild(cell5);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Data are Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function LoadAccHdCode(path) {
	var cmbBudgetDescMain = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetGroupMinor").value;
	if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetDescMain.focus();
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetDescSub.focus();
	} else {
		var url = path
				+ "/Budget_Heads_Ac_heads_mapping?command=LoadAccHdCode&cmbBudgetDescMain="
				+ cmbBudgetDescMain + "&cmbBudgetDescSub=" + cmbBudgetDescSub;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
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
	var cmbBudgetDescMain = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetDescMain.focus();
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetDescSub.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadCode.focus();
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
		}
		xmlrequest.send(null);
	}
}

function LoadAccHdDesc1(baseResponse) {

	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var AccHdDesc = baseResponse.getElementsByTagName("AccHdDesc")[0].firstChild.nodeValue;
		document.getElementById("txtAcc_HeadDesc").value = AccHdDesc;
	} else {
		alert("Fail to Load Account Head Desc");
	}
}

function getMinorBudgetHeadDesc(path) {
	var cmbBudgetGroupMajor = document.getElementById('cmbBudgetGroupMajor').value;
	
	if (cmbBudgetGroupMajor=="") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Master.cmbBudgetGroupMajor.focus();
		
	} else {
     
		var url = path
				+ "/MIS_Major_Group_Acc_Head_Mapping?command=getMinorBudgetHeadDesc&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);
      // alert(url);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function getMinorBudgetHeadDesc1(baseResponse) {
	var MajorCode;
	document.getElementById("cmbGroup_Head_Code").length = 1;
	document.getElementById("cmbBudgetGroupMinor").length = 1;
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	
	if (flag3 == "success") {
		var len45 = baseResponse.getElementsByTagName("BudgetIdSub").length;
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var BudgetIdSub = baseResponse
						.getElementsByTagName("BudgetIdSub")[i].firstChild.nodeValue;
				var BudgetDescSub = baseResponse
						.getElementsByTagName("BudgetDescSub")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbBudgetGroupMinor");
				var op = document.createElement("OPTION");
				op.value = BudgetIdSub;
				var txt = document.createTextNode(BudgetDescSub);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Desc Minor Does Not Exist");
		}
	} else {
		alert("Fail to Load Budget Desc Minor");
	}
	try{
	var flag4 = baseResponse.getElementsByTagName("flag4")[0].firstChild.nodeValue;
	//alert("flag4::::"+flag4);
	if (flag4 == "success") {
		var len45 = baseResponse.getElementsByTagName("cmbGroup_Head_Code").length;
		//alert("length::"+len45);
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var cmbGroup_Head_Code = baseResponse
						.getElementsByTagName("cmbGroup_Head_Code")[i].firstChild.nodeValue;
				var cmbGroup_Head_Desc = baseResponse
						.getElementsByTagName("cmbGroup_Head_Desc")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbGroup_Head_Code");
				var op = document.createElement("OPTION");
				op.value = cmbGroup_Head_Code;
				var txt = document.createTextNode(cmbGroup_Head_Desc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Group Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Group Head Code");
	}
	}catch(e){
		alert(e.message);
	}
	
	
	
}
function initialLoad(path) {
	var budgetGroup="";
	var url=""; 
	//url = path + "/MIS_Major_Group_Acc_Head_Mapping?command=FirstLoad1";
	if(document.getElementById('cmbBudgetGroupMajor').value==""){
		url = path + "/MIS_Major_Group_Acc_Head_Mapping?command=FirstLoad1";
	}else{
		budgetGroup=document.getElementById('cmbBudgetGroupMajor').value;
		url = path + "/MIS_Major_Group_Acc_Head_Mapping?command=FirstLoad1&budgetGroup="+budgetGroup;
	}
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function firstLoad(baseResponse) {
	var MajorCode;
	var majorId=document.getElementById('cmbBudgetGroupMajor').value;
	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {
			var len1 = baseResponse.getElementsByTagName("BudgetIdMain").length;	
			var select=document.getElementById('cmbBudgetGroupMajor');
			var listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=baseResponse.getElementsByTagName("BudgetDescMain")[i].firstChild.nodeValue;
				listOpt.value=baseResponse.getElementsByTagName("BudgetIdMain")[i].firstChild.nodeValue;
			}
			select.value=majorId;
			
		} else {
			alert("Fail to Load Major Head Code");
	}
	try{
		document.getElementById('tblList').innerHTML="";
	}catch(e){
		document.getElementById('tblList').innerText="";
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len = baseResponse.getElementsByTagName("txtGroup_Head_Code").length;
		for ( var k = 0; k < len; k++) {
			var cmbBudgetGroupMajor = baseResponse
					.getElementsByTagName("cmbBudgetGroupMajor")[k].firstChild.nodeValue;
			var txtGroup_Head_Code = baseResponse
					.getElementsByTagName("txtGroup_Head_Code")[k].firstChild.nodeValue;
			var cmbBudgetGroupMinor = baseResponse
					.getElementsByTagName("cmbBudgetGroupMinor")[k].firstChild.nodeValue;
			var txtAcc_HeadCode = baseResponse
					.getElementsByTagName("txtAcc_HeadCode")[k].firstChild.nodeValue;
			var gROUPHEADDESC=baseResponse.getElementsByTagName("GROUP_HEAD_DESC")[k].firstChild.nodeValue;
			var mINORHEADDESC=baseResponse.getElementsByTagName("MINOR_HEAD_DESC")[k].firstChild.nodeValue;
			var aCCOUNTHEADDESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = cmbBudgetGroupMajor + txtGroup_Head_Code
					+ cmbBudgetGroupMinor + txtAcc_HeadCode;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbBudgetGroupMajor
					+ "','" + txtGroup_Head_Code + "','" + cmbBudgetGroupMinor
					+ "','" + txtAcc_HeadCode + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			if(cmbBudgetGroupMajor=="L")
			{
				MajorCode="Liabilities";
			}
			else if(cmbBudgetGroupMajor=="A")
			{
				MajorCode="Assets";
			}
			else if(cmbBudgetGroupMajor=="E")
			{
				MajorCode="Expenditure";
			}
			else if(cmbBudgetGroupMajor=="I")
			{
				MajorCode="Income";
			}
			
			var cell2 = document.createElement("TD");
			var cmbBudgetGroupMajor = document.createTextNode(MajorCode);
			//pp
			
			cell2.appendChild(cmbBudgetGroupMajor);
			mycurrent_row.appendChild(cell2);

			var cell21 = document.createElement("TD");
			var txtGroup_Head_Code = document
					.createTextNode(gROUPHEADDESC);
			cell21.appendChild(txtGroup_Head_Code);
			mycurrent_row.appendChild(cell21);

			var cell22 = document.createElement("TD");
			var cmbBudgetGroupMinor = document
					.createTextNode(mINORHEADDESC);
			cell22.appendChild(cmbBudgetGroupMinor);
			mycurrent_row.appendChild(cell22);

			var cell3 = document.createElement("TD");
			var txtAcc_HeadCode = document.createTextNode(aCCOUNTHEADDESC);
			cell3.appendChild(txtAcc_HeadCode);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);

		}
	} else {
		alert("Fail to Load Grid");
	}
}

function refresh() {
    document.frmMIS_Major_Group_Acc_Head_Mapping.checke1.value = "";
    document.frmMIS_Major_Group_Acc_Head_Mapping.checke2.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMajor.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.cmbGroup_Head_Code.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMinor.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadCode.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadDesc.value = "";
	 document.frmMIS_Major_Group_Acc_Head_Mapping.levelno.value = "";

	document.frmMIS_Major_Group_Acc_Head_Mapping.onsubmit.disabled = false;
	document.frmMIS_Major_Group_Acc_Head_Mapping.ondelete.disabled = true;
}

function refreshh() {
	document.getElementById("cmbBudgetGroupMajor").disabled = false;
	document.getElementById("cmbGroup_Head_Code").disabled = false;
	document.getElementById("cmbBudgetGroupMinor").disabled = false;
	document.getElementById("txtAcc_HeadCode").disabled = false;
	
	document.frmMIS_Major_Group_Acc_Head_Mapping.checke1.value = "";
    document.frmMIS_Major_Group_Acc_Head_Mapping.checke2.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMajor.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.cmbGroup_Head_Code.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMinor.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadCode.value = "";
	document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadDesc.value = "";
	 document.frmMIS_Major_Group_Acc_Head_Mapping.levelno.value = "";

	document.frmMIS_Major_Group_Acc_Head_Mapping.onsubmit.disabled = false;
	document.frmMIS_Major_Group_Acc_Head_Mapping.ondelete.disabled = true;
	var pat=document.getElementById('strpath').value;
    document.frmMIS_Major_Group_Acc_Head_Mapping.checke[0].checked=true;
	initialLoad(pat);
	
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
function checkvalue(path){
  //alert("ttttttt");
   var xmlrequest = AjaxFunction();
	if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[0].checked==true){
	document.frmMIS_Major_Group_Acc_Head_Mapping.levelno.disabled = true;
	//	alert("load mis...");
	createComboBox("BudgetGroupMajor", "cmbBudgetGroupMajor" );
		//document.getElementById('cmbBudgetGroupMajor').value="";
		createComboBox("Group_Head_Code", "cmbGroup_Head_Code" );
		createComboBox("BudgetGroupMinor", "cmbBudgetGroupMinor" );
		var url= path +"/EXP_Major_Group_Acc_Head_Mapping?command=FirstLoad2";
		
	}else if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[1].checked==true)
	{
		createComboBox("BudgetGroupMajor", "cmbBudgetGroupMajor" );
		createComboBox("Group_Head_Code", "cmbGroup_Head_Code" );
		createComboBox("BudgetGroupMinor", "cmbBudgetGroupMinor" );
		document.frmMIS_Major_Group_Acc_Head_Mapping.levelno.disabled = false;
	  // alert("load exp....");
		var url= path +"/EXP_Major_Group_Acc_Head_Mapping?command=Exp";
		
	}	
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange = function()
	{
		if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{
		var majorId=document.getElementById('cmbBudgetGroupMajor').value;
		var baseResponse=xmlrequest.responseXML.getElementsByTagName("root")[0];
	    var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
    	if (flag2 == "success") {
			var len1 = baseResponse.getElementsByTagName("BudgetIdMain").length;	
			var select=document.getElementById('cmbBudgetGroupMajor');
			var listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="-- Select --";
			listOpt.value="";
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=baseResponse.getElementsByTagName("BudgetDescMain")[i].firstChild.nodeValue;
				listOpt.value=baseResponse.getElementsByTagName("BudgetIdMain")[i].firstChild.nodeValue;
			}
			select.value=majorId;
			
		} else {
			alert("Fail to Load Major Head Code");
	}
	try{
		document.getElementById('tblList').innerHTML="";
	}catch(e){
		document.getElementById('tblList').innerText="";
	}
	
   }
  }
};
xmlrequest.send(null);
}
function getGroupMinorHead(path)
{
  var xmlrequest = AjaxFunction();
  var Expend = document.getElementById("cmbBudgetGroupMajor").value;
  //alert(Expend);
	if (Expend =="") {
		createComboBox("Group_Head_Code", "cmbGroup_Head_Code" );
		createComboBox("BudgetGroupMinor", "cmbBudgetGroupMinor" );
		document.frmBudget_Heads_Master.cmbBudgetGroupMajor.focus();
	} else {

		var url = path
				+ "/EXP_Major_Group_Acc_Head_Mapping?command=getGroupMinorHead&cmbBudgetGroupMajor="
				+ Expend;
			}

		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{
	document.getElementById("cmbGroup_Head_Code").length = 1;
	document.getElementById("cmbBudgetGroupMinor").length = 1;
	var baseResponse=xmlrequest.responseXML.getElementsByTagName("root")[0];
	var flagfirst = baseResponse.getElementsByTagName("flagfirst")[0].firstChild.nodeValue;
	if (flagfirst == "success") {
		var len45 = baseResponse.getElementsByTagName("BudgetIdSub1").length;
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var BudgetIdSub = baseResponse
						.getElementsByTagName("BudgetIdSub1")[i].firstChild.nodeValue;
				var BudgetDescSub = baseResponse
						.getElementsByTagName("BudgetDescSub1")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbBudgetGroupMinor");
				var op = document.createElement("OPTION");
				op.value = BudgetIdSub;
				var txt = document.createTextNode(BudgetDescSub);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Desc Minor Does Not Exist");
		}
	} else {
		alert("Fail to Load Budget Desc Minor");
	}

	var flag4 = baseResponse.getElementsByTagName("flag4")[0].firstChild.nodeValue;
	if (flag4 == "success") {
		var len45 = baseResponse.getElementsByTagName("cmbGroup_Head_Code1").length;
		if (len45 != 0) {
			for ( var i = 0; i < len45; i++) {
				var cmbGroup_Head_Code = baseResponse
						.getElementsByTagName("cmbGroup_Head_Code1")[i].firstChild.nodeValue;
				var cmbGroup_Head_Desc = baseResponse
						.getElementsByTagName("cmbGroup_Head_Desc1")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbGroup_Head_Code");
				var op = document.createElement("OPTION");
				op.value = cmbGroup_Head_Code;
				var txt = document.createTextNode(cmbGroup_Head_Desc);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Group Head Code Does Not Exist");
		}
	} else {
		alert("Fail to Load Group Head Code");
	}
  }
 }
};
xmlrequest.send(null);
}
function loadGroupMinor(path) {
	var xmlrequest=AjaxFunction();
	var type="";
	if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[0].checked==true){
	//alert("test "+document.getElementById('checke1').value);
	type=document.getElementById('checke1').value;
	getMinorBudgetHeadDesc(path);	
	}else if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[1].checked==true){
	//alert("test "+document.getElementById('checke2').value);
	type=document.getElementById('checke2').value;
	getGroupMinorHead(path);
	}
		
}

function loadExp(path)
{
    //alert("continue.......");
     var xmlrequest=AjaxFunction();
     if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[0].checked==true){
	//alert("test "+document.getElementById('checke1').value);
	initialLoad(path);
	}else if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[1].checked==true){
	//alert("test "+document.getElementById('checke2').value);
	secondLoad(path);
	}
}

function EditExp(BudgetGroupMajor,Group_Head_Code,BudgetGroupMinor,Acc_HeadCode) 
{
       var xmlrequest = AjaxFunction();
     //alert("jjjjj");
	document.getElementById("cmbBudgetGroupMajor").disabled = true;
	document.getElementById("cmbGroup_Head_Code").disabled = true;
	document.getElementById("cmbBudgetGroupMinor").disabled = true;
	document.getElementById("txtAcc_HeadCode").disabled = true;
	document.getElementById("txtAcc_HeadCode").value = "";
	var url = "../../../../../EXP_Major_Group_Acc_Head_Mapping?command=LoadLib&BudgetGroupMajor="
			+ BudgetGroupMajor
			+ "&Group_Head_Code="
			+ Group_Head_Code
			+ "&BudgetGroupMinor="
			+ BudgetGroupMinor
			+ "&Acc_HeadCode=" + Acc_HeadCode;

	xmlrequest.open("POST",url,true);
	//alert(url);
	xmlrequest.onreadystatechange = function() 
	{	
	if(xmlrequest.readyState == 4) 
		{
	if(xmlrequest.status == 200) 
		{
	//alert("vvvvv:"+xmlrequest.responseText);
	
    var  baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];
					
   // alert(baseResponse);
	var BudgetGroupMajor1 = baseResponse
			.getElementsByTagName("BudgetGroupMajor")[0].firstChild.nodeValue;
	var Group_Head_Code1 = baseResponse
			.getElementsByTagName("Group_Head_Code")[0].firstChild.nodeValue;
	var Group_Head_Desc1 = baseResponse
			.getElementsByTagName("Group_Head_Desc")[0].firstChild.nodeValue;
	var BudgetGroupMinor1 = baseResponse
			.getElementsByTagName("BudgetGroupMinor")[0].firstChild.nodeValue;
	var BudgetGroupMinorDesc1 = baseResponse
			.getElementsByTagName("BudgetGroupMinorDesc")[0].firstChild.nodeValue;
	var Acc_HeadCode1 = baseResponse.getElementsByTagName("Acc_HeadCode")[0].firstChild.nodeValue;
	var Acc_HeadCodeDesc1 = baseResponse
		.getElementsByTagName("Acc_HeadCodeDesc")[0].firstChild.nodeValue;
	
	document.getElementById("cmbBudgetGroupMajor").value =BudgetGroupMajor1;
	
	var se = document.getElementById("cmbGroup_Head_Code");
	var op = document.createElement("OPTION");
	
	op.value = Group_Head_Code1;
	var txt = document.createTextNode(Group_Head_Desc1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbGroup_Head_Code").value = Group_Head_Code1;
	
	var se = document.getElementById("cmbBudgetGroupMinor");
	var op = document.createElement("OPTION");
	op.value = BudgetGroupMinor1;
	var txt = document.createTextNode(BudgetGroupMinorDesc1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("cmbBudgetGroupMinor").value = BudgetGroupMinor1;		

	var se = document.getElementById("txtAcc_HeadCode");
	var op = document.createElement("OPTION");
	op.value = Acc_HeadCode1;
	var txt = document.createTextNode(Acc_HeadCode1);
	op.appendChild(txt);
	se.appendChild(op);

	document.getElementById("txtAcc_HeadCode").value = Acc_HeadCode1;
	document.getElementById("txtAcc_HeadDesc").value = Acc_HeadCodeDesc1;
	
	document.frmMIS_Major_Group_Acc_Head_Mapping.onsubmit.disabled = true;
	document.frmMIS_Major_Group_Acc_Head_Mapping.ondelete.disabled = false;

	}
	}
	};
	xmlrequest.send(null);
}

function secondLoad(path){
  //   alert("second.......");
    var xmlrequest=AjaxFunction();
	var budgetGroup=document.getElementById('cmbBudgetGroupMajor').value;
	var	url = path + "/EXP_Major_Group_Acc_Head_Mapping?command=secondLoad&budgetGroup="+budgetGroup;
	
	xmlrequest.open("POST",url,true);
	//alert(url);
	xmlrequest.onreadystatechange = function() {
	if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{
		
		//alert(xmlrequest.responseText);
        var baseResponse=xmlrequest.responseXML.getElementsByTagName("root")[0];
	    var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    
		//alert(flag);
	if (flag == "success") {

		var len = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		for ( var k = 0; k < len; k++) {
		
		    var BudgetGroupMajor = baseResponse
					.getElementsByTagName("BudgetGroupMajor")[k].firstChild.nodeValue;
			var Group_Head_Code = baseResponse
					.getElementsByTagName("Group_Head_Code")[k].firstChild.nodeValue;
			var BudgetGroupMinor = baseResponse
					.getElementsByTagName("BudgetGroupMinor")[k].firstChild.nodeValue;
			var Acc_HeadCode = baseResponse
					.getElementsByTagName("Acc_HeadCode")[k].firstChild.nodeValue;
			var mAJORHEADDESC=baseResponse.getElementsByTagName("MAJOR_HEAD_DESC")[k].firstChild.nodeValue
			var gROUPHEADDESC=baseResponse.getElementsByTagName("GROUP_HEAD_DESC")[k].firstChild.nodeValue;
			var mINORHEADDESC=baseResponse.getElementsByTagName("MINOR_HEAD_DESC")[k].firstChild.nodeValue;
			var aCCOUNTHEADDESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = BudgetGroupMajor + Group_Head_Code
					+ BudgetGroupMinor + Acc_HeadCode;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:EditExp('" + BudgetGroupMajor
					+ "','" + Group_Head_Code + "','" + BudgetGroupMinor
					+ "','" + Acc_HeadCode + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			if(cmbBudgetGroupMajor=="L")
			{
				MajorCode="Liabilities";
			}
			else if(cmbBudgetGroupMajor=="A")
			{
				MajorCode="Assets";
			}
			else if(cmbBudgetGroupMajor=="E")
			{
				MajorCode="Expenditure";
			}
			else if(cmbBudgetGroupMajor=="I")
			{
				MajorCode="Income";
			}
			
			var cell2 = document.createElement("TD");
			var cmbBudgetGroupMajor = document.createTextNode(mAJORHEADDESC);
			
			
			cell2.appendChild(cmbBudgetGroupMajor);
			mycurrent_row.appendChild(cell2);

			var cell21 = document.createElement("TD");
			var txtGroup_Head_Code = document
					.createTextNode(gROUPHEADDESC);
			cell21.appendChild(txtGroup_Head_Code);
			mycurrent_row.appendChild(cell21);

			var cell22 = document.createElement("TD");
			var cmbBudgetGroupMinor = document
					.createTextNode(mINORHEADDESC);
			cell22.appendChild(cmbBudgetGroupMinor);
			mycurrent_row.appendChild(cell22);

			var cell3 = document.createElement("TD");
			var txtAcc_HeadCode = document.createTextNode(aCCOUNTHEADDESC);
			cell3.appendChild(txtAcc_HeadCode);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);

		}
	} else {
		alert("Fail to Load Grid");
	}
   }
 }
};
xmlrequest.send(null);
  
}
function addExp(path)
         {
    var xmlrequest = AjaxFunction();
    var cmbBudgetGroupMajor = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var levelno=document.getElementById("levelno").value;

	if (cmbBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMajor
				.focus();
	} else if (cmbGroup_Head_Code == "") {
		alert("Select Group Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbGroup_Head_Code.focus();
	} else if (cmbBudgetGroupMinor == "") {
		alert("Select Budget Group Minor in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetGroupMinor
				.focus();
	} else if (txtAcc_HeadCode == "") {
		alert("Select Acc Head Code in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.txtAcc_HeadCode.focus();
	} else if (levelno == "") {
		alert("Select level_no in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.levelno.focus();
	}else {

		var url = path
				+ "/EXP_Major_Group_Acc_Head_Mapping?command=add&cmbBudgetGroupMajor="
				+ cmbBudgetGroupMajor + "&cmbGroup_Head_Code="
				+ cmbGroup_Head_Code + "&cmbBudgetGroupMinor="
				+ cmbBudgetGroupMinor + "&txtAcc_HeadCode=" + txtAcc_HeadCode +"&levelno=" + levelno;
                
		//alert(url);
		

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function(){
		
		if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
	     //alert(xmlrequest.responseText);
	     addNew(xmlrequest);
	   }
	}
};
    xmlrequest.send(null);
    }
}  
function addNew(xmlrequest)
	{    
	    
		var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
		//alert(baseResponse);
	    var flagA = baseResponse.getElementsByTagName("flagA")[0].firstChild.nodeValue;
        //alert(flagA);
	if(flagA == "success") {
	
		alert("Record Inserted Into Database successfully.");

	   refresh();

		var BudgetGroupMajor = baseResponse
				.getElementsByTagName("BudgetGroupMajor")[0].firstChild.nodeValue;
		var Group_Head_Code = baseResponse
				.getElementsByTagName("Group_Head_Code")[0].firstChild.nodeValue;
		var BudgetGroupMinor = baseResponse
				.getElementsByTagName("BudgetGroupMinor")[0].firstChild.nodeValue;
		var Acc_HeadCode = baseResponse
				.getElementsByTagName("Acc_HeadCode")[0].firstChild.nodeValue;
       var mAJORHEADDESC=baseResponse.getElementsByTagName("MAJOR_HEAD_DESC")[0].firstChild.nodeValue
	   var gROUPHEADDESC=baseResponse.getElementsByTagName("GROUP_HEAD_DESC")[0].firstChild.nodeValue;
	   var mINORHEADDESC=baseResponse.getElementsByTagName("MINOR_HEAD_DESC")[0].firstChild.nodeValue;
	   var aCCOUNTHEADDESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[0].firstChild.nodeValue;
		var tbody = document.getElementById("tblList");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = BudgetGroupMajor + Group_Head_Code
				+ BudgetGroupMinor + Acc_HeadCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:EditExp('" + BudgetGroupMajor
				+ "','" + Group_Head_Code + "','" + BudgetGroupMinor
				+ "','" + Acc_HeadCode + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var cmbBudgetGroupMajor = document.createTextNode(mAJORHEADDESC);
		cell2.appendChild(cmbBudgetGroupMajor);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var cmbGroup_Head_Code = document.createTextNode(gROUPHEADDESC);
		cell3.appendChild(cmbGroup_Head_Code);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var cmbBudgetGroupMinor = document.createTextNode(mINORHEADDESC);
		cell4.appendChild(cmbBudgetGroupMinor);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var txtAcc_HeadCode = document.createTextNode(aCCOUNTHEADDESC);
		cell5.appendChild(txtAcc_HeadCode);
		mycurrent_row.appendChild(cell5);

		tbody.appendChild(mycurrent_row);

	} else if (flagA == "Exist") {
		alert("The Given Data are Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
  }
function AddValue(path){
     
     if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[0].checked==true){
	//alert("test "+document.getElementById('checke1').value);
	add(path);
	}else if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[1].checked==true){
	//alert("test "+document.getElementById('checke2').value);
	addExp(path);
	}
}
function LoadtxtAccCode(path){
if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[0].checked==true){
	//alert("test "+document.getElementById('checke1').value);
	LoadAccHdCode(path);
	}else if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[1].checked==true){
	//alert("test "+document.getElementById('checke2').value);
	 LoadAccCode(path);
	}
} 
function LoadAccCode(path) {
     //alert("ttttttttttttt....");
    var xmlrequest = AjaxFunction();
	var cmbBudgetDescMain = document.getElementById("cmbBudgetGroupMajor").value;
	var cmbBudgetDescSub = document.getElementById("cmbBudgetGroupMinor").value;
	if (cmbBudgetDescMain == "s") {
		alert("Select Budget Desc Main in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetDescMain.focus();
	} else if (cmbBudgetDescSub == "s") {
		alert("Select Budget Desc Sub in the Field");
		document.frmMIS_Major_Group_Acc_Head_Mapping.cmbBudgetDescSub.focus();
	} else {
		var url = path
				+ "/EXP_Major_Group_Acc_Head_Mapping?command=LoadAccCode&cmbBudgetDescMain="
				+ cmbBudgetDescMain + "&cmbBudgetDescSub=" + cmbBudgetDescSub;
      
		// alert(url);
		

		xmlrequest.open("POST", url, true);
        //alert(url);
		xmlrequest.onreadystatechange = function() {
			if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
		LoadAccCodeSecond(xmlrequest);
		}
	}
};
xmlrequest.send(null);
 }
}

function LoadAccCodeSecond(xmlrequest) {
    //alert("rrrrrrnnnn");
   document.getElementById("txtAcc_HeadCode").length = 1;
   var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
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
function LoadGroupHeadCode(path){
       // alert("jghejklfhwe");
      var xmlrequest = AjaxFunction();
 
       var cmbGroup_Head_Code = document.getElementById("cmbGroup_Head_Code").value;
	   var cmbBudgetGroupMinor = document.getElementById("cmbBudgetGroupMinor").value;
	   var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
 if(document.frmMIS_Major_Group_Acc_Head_Mapping.checke[1].checked==true){
 var url=path
  +"/EXP_Major_Group_Acc_Head_Mapping?command=LoadGroupHeadCode&cmbGroup_Head_Code="+cmbGroup_Head_Code+"&cmbBudgetGroupMinor="+cmbBudgetGroupMinor+
       "&txtAcc_HeadCode="+txtAcc_HeadCode;
     
     xmlrequest.open("POST", url, true);
     //(url);
     xmlrequest.onreadystatechange = function() {
	if (xmlrequest.readyState == 4) 
      {
    if (xmlrequest.status == 200) 
    {
    	//alert(xmlrequest.responseText);
    	GroupSubAcc(xmlrequest);	
    }
    }
  };
 xmlrequest.send(null);
}
}
function GroupSubAcc(xmlrequest){

	var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	
    var flagB = baseResponse.getElementsByTagName("flagB")[0].firstChild.nodeValue;
   
     if(flagB == "success") {
    	 try{
    			document.getElementById('tblList').innerHTML="";
    		}catch(e){
    			document.getElementById('tblList').innerText="";
    		}
    	 
    	 var len = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
 		for ( var k = 0; k < len; k++) {
 		
 		    var BudgetGroupMajor = baseResponse
 					.getElementsByTagName("BudgetGroupMajor")[k].firstChild.nodeValue;
 		   // alert(BudgetGroupMajor);
 			var Group_Head_Code = baseResponse
 					.getElementsByTagName("Group_Head_Code")[k].firstChild.nodeValue;
 			var BudgetGroupMinor = baseResponse
 					.getElementsByTagName("BudgetGroupMinor")[k].firstChild.nodeValue;
 			var Acc_HeadCode = baseResponse
 					.getElementsByTagName("Acc_HeadCode")[k].firstChild.nodeValue;
 			var mAJORHEADDESC=baseResponse.getElementsByTagName("MAJOR_HEAD_DESC")[k].firstChild.nodeValue
 			var gROUPHEADDESC=baseResponse.getElementsByTagName("GROUP_HEAD_DESC")[k].firstChild.nodeValue;
 			var mINORHEADDESC=baseResponse.getElementsByTagName("MINOR_HEAD_DESC")[k].firstChild.nodeValue;
 			var aCCOUNTHEADDESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[k].firstChild.nodeValue;

 			var tbody = document.getElementById("tblList");
 			var table = document.getElementById("Existing");
 			var mycurrent_row = document.createElement("TR");

 			mycurrent_row.id = BudgetGroupMajor + Group_Head_Code
 					+ BudgetGroupMinor + Acc_HeadCode;
 			var cell = document.createElement("TD");
 			var anc = document.createElement("A");
 			var url = "javascript:EditExp('" + BudgetGroupMajor
 					+ "','" + Group_Head_Code + "','" + BudgetGroupMinor
 					+ "','" + Acc_HeadCode + "')";

 			anc.href = url;
 			var txtedit = document.createTextNode("Edit");
 			anc.appendChild(txtedit);
 			cell.appendChild(anc);
 			mycurrent_row.appendChild(cell);

 			if(cmbBudgetGroupMajor=="L")
 			{
 				MajorCode="Liabilities";
 			}
 			else if(cmbBudgetGroupMajor=="A")
 			{
 				MajorCode="Assets";
 			}
 			else if(cmbBudgetGroupMajor=="E")
 			{
 				MajorCode="Expenditure";
 			}
 			else if(cmbBudgetGroupMajor=="I")
 			{
 				MajorCode="Income";
 			}
 			
 			var cell2 = document.createElement("TD");
 			var cmbBudgetGroupMajor = document.createTextNode(mAJORHEADDESC);
 			
 			
 			cell2.appendChild(cmbBudgetGroupMajor);
 			mycurrent_row.appendChild(cell2);

 			var cell21 = document.createElement("TD");
 			var txtGroup_Head_Code = document.createTextNode(gROUPHEADDESC);
 			cell21.appendChild(txtGroup_Head_Code);
 			mycurrent_row.appendChild(cell21);

 			var cell22 = document.createElement("TD");
 			var cmbBudgetGroupMinor = document.createTextNode(mINORHEADDESC);
 			cell22.appendChild(cmbBudgetGroupMinor);
 			mycurrent_row.appendChild(cell22);

 			var cell3 = document.createElement("TD");
 			var txtAcc_HeadCode = document.createTextNode(aCCOUNTHEADDESC);
 			cell3.appendChild(txtAcc_HeadCode);
 			mycurrent_row.appendChild(cell3);

 			tbody.appendChild(mycurrent_row);

 		}
 	} else {
 		alert("Fail to Load Grid");
 	}
   
}
function exitfun() {
	window.close();
}

function createComboBox(name, cmbId )
{	
	var option = document.createElement("option");
	option.value = "" ;
	option.text = " -- Select -- " +  name;

	try
	{
		document.getElementById( cmbId ).innerHTML = "";
	}
	catch(e)
	{
		document.getElementById( cmbId ).innerText = "";
	}

	try
	{
		document.getElementById( cmbId ).add(option);
	}
	catch(errorObject)
	{
		document.getElementById( cmbId ).add(option,null);
	}
}
