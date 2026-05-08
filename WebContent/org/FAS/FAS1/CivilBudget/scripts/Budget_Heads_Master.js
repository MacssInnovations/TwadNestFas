//			Budget_Heads_Master			//
var browserName=navigator.appName;
var brow="";
if (browserName=="Netscape")
{ 	brow="nets";}
else if (browserName=="Microsoft Internet Explorer")
{ 	brow="iex";}

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
				firstLoad(baseResponse);
			} else if (command == "getMinorBudgetHeadDesc") {
				getMinorBudgetHeadDesc1(baseResponse);
			} else if (command == "LoadData") {
				LoadData1(baseResponse);
			}
			else if (command == "loadgp")
			{
				loadgp(baseResponse);
			}
			else if (command == "loadsec")
			{
				loadgp(baseResponse);
				
			}
			
		}
	}
}

function loadBudgetGp(val)
{
	var url = "../../../../../Budget_Heads_Master?command=loadgp&format="+val;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);
	
}
function secLoad()
{
var format=document.getElementById("cmbFormatNo").value;
var txtBudgetGroupMajor=document.getElementById("txtBudgetGroupMajor").value;
	var url = "../../../../../Budget_Heads_Master?command=loadsec&format="+format+"&txtBudgetGroupMajor="+txtBudgetGroupMajor;
//	 alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);
}

function loadgp(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
		var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }

		var len = baseResponse.getElementsByTagName("cmbFormatNo").length;
		
		for ( var k = 0; k < len; k++) {
			var cmbFormatNo = baseResponse.getElementsByTagName("cmbFormatNo")[k].firstChild.nodeValue;
			var txtBudgetGroupMajor1 = baseResponse
					.getElementsByTagName("txtBudgetGroupMajor1")[k].firstChild.nodeValue;
			var txtBudgetGroupMinor = baseResponse
					.getElementsByTagName("txtBudgetGroupMinor")[k].firstChild.nodeValue;
			
			if ((txtBudgetGroupMinor == "") || (txtBudgetGroupMinor == 'null')) {
				txtBudgetGroupMinor = "";
			}	
			var txtCanExceedBudget = baseResponse.getElementsByTagName("txtCanExceedBudget")[k].firstChild.nodeValue;
			//alert(txtCanExceedBudget);
			var txtCanbeReAppropriated = baseResponse
					.getElementsByTagName("txtCanbeReAppropriated")[k].firstChild.nodeValue;
			//alert(txtCanbeReAppropriated);
			var txtCanbeRatified = baseResponse.getElementsByTagName("txtCanbeRatified")[k].firstChild.nodeValue;
			//alert(txtCanbeRatified);
			var budgetGroupMajorDesc = baseResponse.getElementsByTagName("BUDGET_GROUP_MAJOR")[k].firstChild.nodeValue;
			//alert("budgetGroupMajorDesc"+budgetGroupMajorDesc);
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id=cmbFormatNo+txtBudgetGroupMajor1;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbFormatNo+ "','"+ txtBudgetGroupMajor1 + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var cmbFormatNo = document.createTextNode(cmbFormatNo);
			cell2.appendChild(cmbFormatNo);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtBudgetGroupMajor = document
					.createTextNode(budgetGroupMajorDesc);
			cell3.appendChild(txtBudgetGroupMajor);
			mycurrent_row.appendChild(cell3);

			

			var cell7 = document.createElement("TD");
			var txtCanExceedBudget = document
					.createTextNode(txtCanExceedBudget);
			cell7.appendChild(txtCanExceedBudget);
			mycurrent_row.appendChild(cell7);

			var cell8 = document.createElement("TD");
			var txtCanbeReAppropriated = document
					.createTextNode(txtCanbeReAppropriated);
			cell8.appendChild(txtCanbeReAppropriated);
			mycurrent_row.appendChild(cell8);

			var cell9 = document.createElement("TD");
			var txtCanbeRatified = document.createTextNode(txtCanbeRatified);
			cell9.appendChild(txtCanbeRatified);
			mycurrent_row.appendChild(cell9);
			if(brow=="iex"){
				var vartab = tbody.insertRow(-1);			
				vartab.appendChild(cell);
				vartab.appendChild(cell2);
				vartab.appendChild(cell3);
				vartab.appendChild(cell7);			
				vartab.appendChild(cell8);
				vartab.appendChild(cell9);								
			}else
			{
				tbody.appendChild(mycurrent_row);
			}
			
		}
	} else {
		//alert("No Data Found");

		var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }
	}
}

function initialLoad(path) {
	//alert(path);

	var url = path + "/Budget_Heads_Master?command=getGrid";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}
function getMinorBudgetHeadDesc(path) {
	var txtBudgetDescMain = document.getElementById("txtBudgetDescMain").value;
	if (txtBudgetDescMain == "") {
		alert("Enter Budget Desc Main in the Field");
		document.frmBudget_Heads_Master.txtBudgetDescMain.focus();
	} else {

		var url = path
				+ "/Budget_Heads_Master?command=getMinorBudgetHeadDesc&txtBudgetDescMain="
				+ txtBudgetDescMain;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}
function getMinorBudgetHeadDesc1(baseResponse) {
	document.getElementById("txtBudgetDescSub").length = 1;
	var flag3 = "fas";//baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if (flag3 == "success") {
		var len45 = baseResponse.getElementsByTagName("BudgetIdSub").length;
		if (len45 > 0) {
			for ( var i = 0; i < len45; i++) {
				var BudgetIdSub = baseResponse
						.getElementsByTagName("BudgetIdSub")[i].firstChild.nodeValue;
				var BudgetDescSub = baseResponse
						.getElementsByTagName("BudgetDescSub")[i].firstChild.nodeValue;

				var se = document.getElementById("txtBudgetDescSub");
				var op = document.createElement("OPTION");
				op.value = BudgetIdSub;
				var txt = document.createTextNode(BudgetDescSub);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			//alert("Budget Desc Minor Does Not Exist");
		}
	}
}
function firstLoad(baseResponse) {

	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag1 == "success") {
		var len4 = baseResponse.getElementsByTagName("FormatNo").length;		
			for ( var i = 0; i < len4; i++) {
				var FormatNo = baseResponse.getElementsByTagName("FormatNo")[i].firstChild.nodeValue;

				var se = document.getElementById("cmbFormatNo");
				var op = document.createElement("OPTION");
				op.value = FormatNo;
				var txt = document.createTextNode(FormatNo);
				op.appendChild(txt);
				se.appendChild(op);
			}		
	} else {
		alert("Fail to Load Format No");
	}

	var flag11 = baseResponse.getElementsByTagName("flag11")[0].firstChild.nodeValue;
	if (flag11 == "success") {
		var len44 = baseResponse.getElementsByTagName("txtBudgetGroupId").length;		
			for ( var i = 0; i < len44; i++) {
				var BudgetGroupId = baseResponse.getElementsByTagName("txtBudgetGroupId")[i].firstChild.nodeValue;
				var BudgetGroupMajor = baseResponse.getElementsByTagName("txtBudgetGroupMajor")[i].firstChild.nodeValue;
				
				var se = document.getElementById("txtBudgetGroupMajor");
				var op = document.createElement("OPTION");
				op.value = BudgetGroupId;
				var txt = document.createTextNode(BudgetGroupMajor);
				op.appendChild(txt);
				se.appendChild(op);
			}		
	} else {
		alert("Fail to Load Budget Group Major");
	}
	
	var flag2 = "mis";//baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {
		var len44 =0;// baseResponse.getElementsByTagName("BudgetIdMain").length;
		if (len44 > 0) {
			for ( var i = 0; i < len44; i++) {
				var BudgetIdMain = baseResponse
						.getElementsByTagName("BudgetIdMain")[i].firstChild.nodeValue;
				var BudgetDescMain = baseResponse
						.getElementsByTagName("BudgetDescMain")[i].firstChild.nodeValue;

				var se = document.getElementById("txtBudgetDescMain");
				var op = document.createElement("OPTION");
				op.value = BudgetIdMain;
				var txt = document.createTextNode(BudgetDescMain);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			//alert("Budget Desc Major Does Not Exist");
		}
	} else {
		//alert("Fail to Load Budget Desc Major");
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }

		var len = baseResponse.getElementsByTagName("cmbFormatNo").length;
		var len = baseResponse.getElementsByTagName("cmbFormatNo").length;
		for ( var k = 0; k < len; k++) {
			var cmbFormatNo = baseResponse.getElementsByTagName("cmbFormatNo")[k].firstChild.nodeValue;
			var txtBudgetGroupMajor1 = baseResponse
					.getElementsByTagName("txtBudgetGroupMajor1")[k].firstChild.nodeValue;
			var txtBudgetGroupMinor = baseResponse
					.getElementsByTagName("txtBudgetGroupMinor")[k].firstChild.nodeValue;
			
			if ((txtBudgetGroupMinor == "") || (txtBudgetGroupMinor == 'null')) {
				txtBudgetGroupMinor = "";
			}	
			var txtCanExceedBudget = baseResponse
					.getElementsByTagName("txtCanExceedBudget")[k].firstChild.nodeValue;
			var txtCanbeReAppropriated = baseResponse
					.getElementsByTagName("txtCanbeReAppropriated")[k].firstChild.nodeValue;
			var txtCanbeRatified = baseResponse
					.getElementsByTagName("txtCanbeRatified")[k].firstChild.nodeValue;
			var budgetGroupMajorDesc = baseResponse
						.getElementsByTagName("BUDGET_GROUP_MAJOR")[k].firstChild.nodeValue;

			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id=cmbFormatNo+txtBudgetGroupMajor1;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbFormatNo+ "','"+ txtBudgetGroupMajor1 + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var cmbFormatNo = document.createTextNode(cmbFormatNo);
			cell2.appendChild(cmbFormatNo);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtBudgetGroupMajor = document
					.createTextNode(budgetGroupMajorDesc);
			cell3.appendChild(txtBudgetGroupMajor);
			mycurrent_row.appendChild(cell3);

			/*var cell33 = document.createElement("TD");
			var txtBudgetGroupMinor = document
					.createTextNode(txtBudgetGroupMinor);
			cell33.appendChild(txtBudgetGroupMinor);
			mycurrent_row.appendChild(cell33);

			var cell4 = document.createElement("TD");
			var txtBudgetDescMain = document.createTextNode(txtBudgetDescMain);
			cell4.appendChild(txtBudgetDescMain);
			mycurrent_row.appendChild(cell4);

			var cell6 = document.createElement("TD");
			var txtBudgetDescSub = document.createTextNode(txtBudgetDescSub);
			cell6.appendChild(txtBudgetDescSub);
			mycurrent_row.appendChild(cell6);*/

			var cell7 = document.createElement("TD");
			var txtCanExceedBudget = document
					.createTextNode(txtCanExceedBudget);
			cell7.appendChild(txtCanExceedBudget);
			mycurrent_row.appendChild(cell7);

			var cell8 = document.createElement("TD");
			var txtCanbeReAppropriated = document
					.createTextNode(txtCanbeReAppropriated);
			cell8.appendChild(txtCanbeReAppropriated);
			mycurrent_row.appendChild(cell8);

			var cell9 = document.createElement("TD");
			var txtCanbeRatified = document.createTextNode(txtCanbeRatified);
			cell9.appendChild(txtCanbeRatified);
			mycurrent_row.appendChild(cell9);
			if(brow=="iex"){
				var vartab = tbody.insertRow(-1);			
				vartab.appendChild(cell);
				vartab.appendChild(cell2);
				vartab.appendChild(cell3);
				vartab.appendChild(cell7);			
				vartab.appendChild(cell8);
				vartab.appendChild(cell9);								
			}else
			{
				tbody.appendChild(mycurrent_row);
			}
			
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function add(path) {
	//alert(path);

	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var txtBudgetGroupMajor = document.getElementById("txtBudgetGroupMajor").value;
	var txtBudgetGroupMinor = document.getElementById("txtBudgetGroupMinor").value;
	var txtBudgetDescMain = document.getElementById("txtBudgetDescMain").value;
	var txtBudgetDescSub = document.getElementById("txtBudgetDescSub").value;
	if (document.frmBudget_Heads_Master.txtCanExceedBudget[0].checked == true) {
		txtCanExceedBudget = document.frmBudget_Heads_Master.txtCanExceedBudget[0].value;
	} else if (document.frmBudget_Heads_Master.txtCanExceedBudget[1].checked == true) {
		txtCanExceedBudget = document.frmBudget_Heads_Master.txtCanExceedBudget[1].value;
	} else {
		txtCanExceedBudget = document.frmBudget_Heads_Master.txtCanExceedBudget[2].value;
	}
	if (document.frmBudget_Heads_Master.txtCanbeReAppropriated[0].checked == true) {
		txtCanbeReAppropriated = document.frmBudget_Heads_Master.txtCanbeReAppropriated[0].value;
	} else if (document.frmBudget_Heads_Master.txtCanbeReAppropriated[1].checked == true) {
		txtCanbeReAppropriated = document.frmBudget_Heads_Master.txtCanbeReAppropriated[1].value;
	} else {
		txtCanbeReAppropriated = document.frmBudget_Heads_Master.txtCanbeReAppropriated[2].value;
	}
	if (document.frmBudget_Heads_Master.txtCanbeRatified[0].checked == true) {
		txtCanbeRatified = document.frmBudget_Heads_Master.txtCanbeRatified[0].value;
	} else if (document.frmBudget_Heads_Master.txtCanbeRatified[1].checked == true) {
		txtCanbeRatified = document.frmBudget_Heads_Master.txtCanbeRatified[1].value;
	} else {
		txtCanbeRatified = document.frmBudget_Heads_Master.txtCanbeRatified[2].value;
	}

	if (cmbFormatNo == "s") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Master.cmbFormatNo.focus();
	} else if (txtBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Master.txtBudgetGroupMajor.focus();
	} else if (txtBudgetDescMain == "") {
		alert("Enter Budget Desc Main in the Field");
	} else if (txtBudgetDescSub == "") {
		alert("Enter Budget Desc Sub in the Field");
	} else {

		var url = path + "/Budget_Heads_Master?command=add&cmbFormatNo="
				+ cmbFormatNo + "&txtBudgetDescMain=" + txtBudgetDescMain
				+ "&txtBudgetDescSub=" + txtBudgetDescSub
				+ "&txtCanExceedBudget=" + txtCanExceedBudget
				+ "&txtCanbeReAppropriated=" + txtCanbeReAppropriated
				+ "&txtCanbeRatified=" + txtCanbeRatified
				+ "&txtBudgetGroupMajor=" + txtBudgetGroupMajor
				+ "&txtBudgetGroupMinor=" + txtBudgetGroupMinor;

		// alert(url);
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

		var cmbFormatNo = baseResponse.getElementsByTagName("cmbFormatNo")[0].firstChild.nodeValue;
		var txtBudgetGroupMajor = baseResponse
				.getElementsByTagName("txtBudgetGroupMajor")[0].firstChild.nodeValue;
		var txtBudgetGroupMinor = baseResponse
				.getElementsByTagName("txtBudgetGroupMinor")[0].firstChild.nodeValue;
		if ((txtBudgetGroupMinor == "") || (txtBudgetGroupMinor == 'null')) {
			txtBudgetGroupMinor = "";
		}
		
		var group_desc = baseResponse.getElementsByTagName("group_desc")[0].firstChild.nodeValue;
		//alert(group_desc);
		var txtBudgetDescMain = baseResponse
				.getElementsByTagName("txtBudgetDescMain")[0].firstChild.nodeValue;
		var txtBudgetDescSub = baseResponse
				.getElementsByTagName("txtBudgetDescSub")[0].firstChild.nodeValue;
		var txtCanExceedBudget = baseResponse
				.getElementsByTagName("txtCanExceedBudget")[0].firstChild.nodeValue;
		var txtCanbeReAppropriated = baseResponse
				.getElementsByTagName("txtCanbeReAppropriated")[0].firstChild.nodeValue;
		var txtCanbeRatified = baseResponse
				.getElementsByTagName("txtCanbeRatified")[0].firstChild.nodeValue;
		//var group_desc = baseResponse.getElementsByTagName("group_desc")[0].firstChild.nodeValue;
		
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id =cmbFormatNo+txtBudgetGroupMajor;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbFormatNo + "','" + txtBudgetGroupMajor + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var cmbFormatNo = document.createTextNode(cmbFormatNo);
		cell2.appendChild(cmbFormatNo);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var txtBudgetGroupMajor = document.createTextNode(group_desc);
		cell3.appendChild(txtBudgetGroupMajor);
		mycurrent_row.appendChild(cell3);

		/*var cell33 = document.createElement("TD");
		var txtBudgetGroupMinor = document.createTextNode(txtBudgetGroupMinor);
		cell33.appendChild(txtBudgetGroupMinor);
		mycurrent_row.appendChild(cell33);

		var cell4 = document.createElement("TD");
		var txtBudgetDescMain = document.createTextNode(txtBudgetDescMain);
		cell4.appendChild(txtBudgetDescMain);
		mycurrent_row.appendChild(cell4);

		var cell6 = document.createElement("TD");
		var txtBudgetDescSub = document.createTextNode(txtBudgetDescSub);
		cell6.appendChild(txtBudgetDescSub);
		mycurrent_row.appendChild(cell6);*/

		var cell7 = document.createElement("TD");
		var txtCanExceedBudget = document.createTextNode(txtCanExceedBudget);
		cell7.appendChild(txtCanExceedBudget);
		mycurrent_row.appendChild(cell7);

		var cell8 = document.createElement("TD");
		var txtCanbeReAppropriated = document
				.createTextNode(txtCanbeReAppropriated);
		cell8.appendChild(txtCanbeReAppropriated);
		mycurrent_row.appendChild(cell8);

		var cell9 = document.createElement("TD");
		var txtCanbeRatified = document.createTextNode(txtCanbeRatified);
		cell9.appendChild(txtCanbeRatified);
		mycurrent_row.appendChild(cell9);
		if(brow=="iex"){
			var vartab = tbody.insertRow(-1);			
			vartab.appendChild(cell);
			vartab.appendChild(cell2);
			vartab.appendChild(cell3);
			//vartab.appendChild(cell33);			
			vartab.appendChild(cell7);
			vartab.appendChild(cell8);								
			vartab.appendChild(cell9);
		}else
		{
			tbody.appendChild(mycurrent_row);
		}

	} else if (flag == "Exist") {
		alert("The Given Datas are Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(txtFormatNo, txtBudgetGroupMajor) {

	var url = "../../../../../Budget_Heads_Master?command=LoadData&txtFormatNo="
			+ txtFormatNo
			+ "&txtBudgetIdMain="
			+ 0
			+ "&txtBudgetIdSub=" + 0 + "&txtBudgetGroupMajor=" + txtBudgetGroupMajor;

	// alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}

function LoadData1(baseResponse) {
	document.getElementById("cmbFormatNo").disabled = true;
	document.getElementById("txtBudgetDescMain").disabled = true;
	document.getElementById("txtBudgetGroupMajor").disabled = true;
	document.getElementById("txtBudgetDescSub").disabled = true;
	document.getElementById("txtBudgetDescSub").length = 1;
	var flag3=baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	if(flag3=="success"){
		document.frmBudget_Heads_Master.cmbFormatNo.value=baseResponse.getElementsByTagName("cmbFormatNo")[0].firstChild.nodeValue;
		document.frmBudget_Heads_Master.txtBudgetGroupMajor.value=baseResponse.getElementsByTagName("BudgetIdSub")[0].firstChild.nodeValue;
		if (baseResponse.getElementsByTagName("CAN_EXCEED_BUDGET")[0].firstChild.nodeValue=="Y") {
			document.frmBudget_Heads_Master.txtCanExceedBudget[0].checked = true;
		} else if (baseResponse.getElementsByTagName("CAN_EXCEED_BUDGET")[0].firstChild.nodeValue=="N") {
			document.frmBudget_Heads_Master.txtCanExceedBudget[1].checked = true;
		} else {
			document.frmBudget_Heads_Master.txtCanExceedBudget[2].checked = true;
		}
		if (baseResponse.getElementsByTagName("CAN_BE_RE_APPROPRIATED")[0].firstChild.nodeValue=="Y") {
			document.frmBudget_Heads_Master.txtCanbeReAppropriated[0].checked = true;
		} else if (baseResponse.getElementsByTagName("CAN_BE_RE_APPROPRIATED")[0].firstChild.nodeValue=="N") {
			document.frmBudget_Heads_Master.txtCanbeReAppropriated[1].checked = true;
		} else {
			document.frmBudget_Heads_Master.txtCanbeReAppropriated[2].checked = true;
		}
		if (baseResponse.getElementsByTagName("CAN_BE_RATIFIED")[0].firstChild.nodeValue=="Y") {
			document.frmBudget_Heads_Master.txtCanbeRatified[0].checked = true;
		} else if (baseResponse.getElementsByTagName("CAN_BE_RATIFIED")[0].firstChild.nodeValue=="N") {
			document.frmBudget_Heads_Master.txtCanbeRatified[1].checked = true;
		} else {
			document.frmBudget_Heads_Master.txtCanbeRatified[2].checked = true;
		}
		document.frmBudget_Heads_Master.onsubmit.disabled = true;
		document.frmBudget_Heads_Master.ondelete.disabled = false;
		document.frmBudget_Heads_Master.onupdate.disabled = false;
	}else{
		alert("Process fail");
	}
}

function update(path) {
	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var txtBudgetGroupMajor = document.getElementById("txtBudgetGroupMajor").value;
	var txtBudgetGroupMinor = document.getElementById("txtBudgetGroupMinor").value;
	var txtBudgetDescMain = document.getElementById("txtBudgetDescMain").value;
	var txtBudgetDescSub = document.getElementById("txtBudgetDescSub").value;
	if (document.frmBudget_Heads_Master.txtCanExceedBudget[0].checked == true) {
		txtCanExceedBudget = document.frmBudget_Heads_Master.txtCanExceedBudget[0].value;
	} else if (document.frmBudget_Heads_Master.txtCanExceedBudget[1].checked == true) {
		txtCanExceedBudget = document.frmBudget_Heads_Master.txtCanExceedBudget[1].value;
	} else {
		txtCanExceedBudget = document.frmBudget_Heads_Master.txtCanExceedBudget[2].value;
	}
	if ((txtBudgetGroupMinor == "") || (txtBudgetGroupMinor == null)) {
		txtBudgetGroupMinor = null;
	}
	if (document.frmBudget_Heads_Master.txtCanbeReAppropriated[0].checked == true) {
		txtCanbeReAppropriated = document.frmBudget_Heads_Master.txtCanbeReAppropriated[0].value;
	} else if (document.frmBudget_Heads_Master.txtCanbeReAppropriated[1].checked == true) {
		txtCanbeReAppropriated = document.frmBudget_Heads_Master.txtCanbeReAppropriated[1].value;
	} else {
		txtCanbeReAppropriated = document.frmBudget_Heads_Master.txtCanbeReAppropriated[2].value;
	}
	if (document.frmBudget_Heads_Master.txtCanbeRatified[0].checked == true) {
		txtCanbeRatified = document.frmBudget_Heads_Master.txtCanbeRatified[0].value;
	} else if (document.frmBudget_Heads_Master.txtCanbeRatified[1].checked == true) {
		txtCanbeRatified = document.frmBudget_Heads_Master.txtCanbeRatified[1].value;
	} else {
		txtCanbeRatified = document.frmBudget_Heads_Master.txtCanbeRatified[2].value;
	}
	if (cmbFormatNo == "s") {
		alert("Select Format No in the Field");
		document.frmBudget_Heads_Master.cmbFormatNo.focus();
	} else if (txtBudgetGroupMajor == "") {
		alert("Select Budget Group Major in the Field");
		document.frmBudget_Heads_Master.txtBudgetGroupMajor.focus();
	} else if (txtBudgetDescMain == "") {
		alert("Enter Budget Desc Main in the Field");
		document.frmBudget_Heads_Master.txtBudgetDescMain.focus();
	} else if (txtBudgetDescSub == "") {
		alert("Enter Budget Desc Sub in the Field");
		document.frmBudget_Heads_Master.txtBudgetDescSub.focus();
	} else {
		var url = path + "/Budget_Heads_Master?command=update&cmbFormatNo="
				+ cmbFormatNo + "&txtBudgetDescMain=" +txtBudgetDescMain
				+ "&txtBudgetDescSub=" + txtBudgetDescSub
				+ "&txtCanExceedBudget=" + txtCanExceedBudget
				+ "&txtCanbeReAppropriated=" + txtCanbeReAppropriated
				+ "&txtCanbeRatified=" + txtCanbeRatified
				+ "&txtBudgetGroupMajor=" + txtBudgetGroupMajor
				+ "&txtBudgetGroupMinor=" + txtBudgetGroupMinor;

		//alert("Update--->"+url);
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
		items[1] = baseResponse.getElementsByTagName("txtBudgetGroupMajor")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("txtBudgetGroupMinor")[0].firstChild.nodeValue;
		if ((items[2] == "") || (items[2] == null)) {
			items[2] = null;
		}
		items[3] = baseResponse.getElementsByTagName("txtBudgetDescMain")[0].firstChild.nodeValue;
		items[4] = baseResponse.getElementsByTagName("txtBudgetDescSub")[0].firstChild.nodeValue;
		items[5] = baseResponse.getElementsByTagName("txtCanExceedBudget")[0].firstChild.nodeValue;
		items[6] = baseResponse.getElementsByTagName("txtCanbeReAppropriated")[0].firstChild.nodeValue;
		items[7] = baseResponse.getElementsByTagName("txtCanbeRatified")[0].firstChild.nodeValue;
		items[8]=baseResponse.getElementsByTagName("BUDGET_GROUP_MAJOR")[0].firstChild.nodeValue;

		var r = document.getElementById(items[0] + items[1]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[5];
		/*rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];*/
		rcells.item(4).firstChild.nodeValue = items[6];
		rcells.item(5).firstChild.nodeValue = items[7];
		//rcells.item(6).firstChild.nodeValue = items[7];
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
		refresh();
	} else {
		alert("Failed to update values");
		refresh();
	}
}

function deleteeee(path) {
	//alert(path);
	var cmbFormatNo = document.getElementById("cmbFormatNo").value;
	var txtBudgetGroupMajor = document.getElementById("txtBudgetGroupMajor").value;
	var txtBudgetDescMain = document.getElementById("txtBudgetDescMain").value;
	var txtBudgetDescSub = document.getElementById("txtBudgetDescSub").value;
	if (cmbFormatNo == "s") {
		alert("Enter Format No in the Field");
		document.frmBudget_Heads_Master.cmbFormatNo.focus();
	} else if (txtBudgetDescMain == "s") {
		alert("select Budget Desc Main in the Field");
		document.frmBudget_Heads_Master.txtBudgetDescMain.focus();
	} else if (txtBudgetDescSub == "s") {
		alert("select Budget Desc Sub in the Field");
		document.frmBudget_Heads_Master.txtBudgetDescSub.focus();
	} else {
		var r = confirm("This Process will Delete the Records in 'Budget Heads Master' and also the Related Records in 'Budget Heads A/c heads mapping'.Are U Sure to Continue?");
		if (r == true) {
			var url = path
					+ "/Budget_Heads_Master?command=deleted&cmbFormatNo="
					+ cmbFormatNo + "&txtBudgetDescMain=" + txtBudgetDescMain
					+ "&txtBudgetDescSub=" + txtBudgetDescSub + "&txtBudgetGroupMajor=" + txtBudgetGroupMajor;
			 //alert("Deleteee---->"+url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
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
		alert("Records in 'Budget Heads Master' and also the Related Records in 'Budget Heads A/c heads mapping' Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function refresh() {
	document.getElementById("cmbFormatNo").disabled = false;
	document.getElementById("txtBudgetGroupMajor").disabled = false;
	document.getElementById("txtBudgetDescMain").disabled = false;
	document.getElementById("txtBudgetDescSub").disabled = false;
	document.frmBudget_Heads_Master.cmbFormatNo.value = "s";
	document.frmBudget_Heads_Master.txtBudgetGroupMajor.value = "";
	document.frmBudget_Heads_Master.txtBudgetGroupMinor.value = "";
	document.frmBudget_Heads_Master.txtBudgetDescMain.value = 0;
	document.frmBudget_Heads_Master.txtBudgetDescSub.value = 0;
	document.frmBudget_Heads_Master.txtBudgetDescSub.length = 1;

	document.frmBudget_Heads_Master.txtCanExceedBudget[0].checked = true;
	document.frmBudget_Heads_Master.txtCanbeReAppropriated[0].checked = true;
	document.frmBudget_Heads_Master.txtCanbeRatified[0].checked = true;

	document.frmBudget_Heads_Master.onsubmit.disabled = false;
	document.frmBudget_Heads_Master.ondelete.disabled = true;
	document.frmBudget_Heads_Master.onupdate.disabled = true;

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
