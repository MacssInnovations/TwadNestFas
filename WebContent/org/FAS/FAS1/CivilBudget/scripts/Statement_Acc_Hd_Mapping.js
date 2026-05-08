//	Statement_Acc_Hd_Mapping	//
var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, baseResponse="";
var pagesize = 10;
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
			} else if (command == "edit") {
			
				edit1(baseResponse);
			} else if (command == "LoadStatementGroupNo") {
				LoadStatementGroupNo1(baseResponse);
			} else if (command == "loadStatementSubGroupNo") {
				loadStatementSubGroupNo1(baseResponse);
			}else if (command == "LoadAccHdDescFrom") {
				LoadAccHdDescFrom(baseResponse);
			} else if (command == "LoadAccHdDescTo") {
				LoadAccHdDescTo(baseResponse);
			} 
		}
	}
}
function LoadAccHdDescFrom1(path) {
	//alert("welcoeme");
	var txtFromAcc_HeadCode = document.getElementById("txtFromAccHdCode").value;
	if (txtFromAcc_HeadCode == "") {
		alert("Enter From Acc Head Code in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.txtFromAccHdCode.focus();
	} else {
		var url = path
				+ "/Statement_Acc_Hd_Mapping?command=LoadAccHdDescFrom&txtFromAcc_HeadCode=" + txtFromAcc_HeadCode;

		//alert("sdfs"+url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function LoadAccHdDescFrom(baseResponse) {

	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	//alert("inside txtFromAcc_HeadDesc return from  java");
	if (flag3 == "success") {
		var AccHdDesc = baseResponse.getElementsByTagName("AccHdDesc")[0].firstChild.nodeValue;
		document.getElementById("txtFromAcc_HeadDesc").value = AccHdDesc;
	} else {
		document.getElementById("txtFromAccHdCode").value ="";
		document.getElementById("txtFromAcc_HeadDesc").value ="";
		alert("Account Head Doesn't exists");
	}
}
function LoadAccHdDescTo1(path) {
	//alert("LoadAccHdDescTo1(");
	var txtToAcc_HeadCode = document.getElementById("txtToAccHdCode").value;
	if (txtToAcc_HeadCode == "") {
		alert("Enter To Acc Head Code in the Field");
		document.frmBudget_Heads_Ac_heads_mapping.txtToAccHdCode.focus();
	} else {
		var url = path
				+ "/Statement_Acc_Hd_Mapping?command=LoadAccHdDescTo&txtToAcc_HeadCode=" + txtToAcc_HeadCode;

		 
		 var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
	}
}function LoadAccHdDescTo(baseResponse) {

	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
	
	if (flag3 == "success") {
		var AccHdDesc = baseResponse.getElementsByTagName("AccHdDesc")[0].firstChild.nodeValue;
		document.getElementById("txtToAcc_HeadDesc").value = AccHdDesc;
	} else {
		document.getElementById("txtToAccHdCode").value ="";
		document.getElementById("txtToAcc_HeadDesc").value ="";
		alert("Account Head Doesn't exists");
	}
}

function statementname(path)
{
	var url = path + "/Statement_Acc_Hd_Mapping?command=statementname&path="+path;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {

	    baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
		var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		if (flag == "success") {
			var selectdiv=document.getElementById('cmbStatementName');
			var listOpt=document.createElement("option");
			selectdiv.length=0;
			selectdiv.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="";
			var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;						
			for(var i=0; i<len4; i++){
					listOpt=document.createElement("option");
					selectdiv.appendChild(listOpt);
					listOpt.text=baseResponse.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue
					listOpt.value=baseResponse.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
			    }
	          }
			}
		}
	};
	xmlrequest.send(null);
}
function initialLoad(path) {
	//alert("path::::"+path);
	var cmbStatementName=document.getElementById("cmbStatementName").value;
    if(cmbStatementName=="")
    {
	var url = path + "/Statement_Acc_Hd_Mapping?command=getGrid";
    
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		viewdata(xmlrequest);
	
	}

	xmlrequest.send(null);
   }
}
function viewdata(xmlrequest)
{
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
     // alert(xmlrequest.responseText);
	  baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	  changepagesize();
		}
		
	}
}
function secondload(path)
{
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGroupName=document.getElementById("cmbStatementGroupNo").value;
	var statementSubGroupNo=document.getElementById("statementSubGroupNo").value;
	var url = path + "/Statement_Acc_Hd_Mapping?command=secondload&path="+path+"&cmbStatementName="+cmbStatementName+
	                "&statementGroupName="+statementGroupName+"&statementSubGroupNo="+statementSubGroupNo;
    //alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		
		secondview(xmlrequest);
	}
	xmlrequest.send(null);
}

function secondview(xmlrequest)
{
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
			baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			changepagesize();
		}
	}
}
function changepagesize() {	
	//alert(pagesize);
	pagesize = document.getElementById("cmbpagination").value;
	var len = baseResponse.getElementsByTagName("cmbStatementName").length;	
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var cmbpage = document.getElementById("cmbpage");
	try {	
		cmbpage.innerHTML = "";
	} catch (e) {
		cmbpage.innerText = "";
	}	
	var i = 1;
	for (i = 1; i <= ((len / pagesize) + 1); i++) {
		var option = document.createElement("OPTION");
		option.text = i;
		option.value = i;
		try {
			cmbpage.add(option);
		} catch (errorObject) {
			cmbpage.add(option, null);
		}
	}
	if(cmbStatementName=="")
	{
	firstLoad();
	}
	else
	{
	seconddata();
	}
	
}
function firstLoad() {
	var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	/*var selectdiv=document.getElementById('cmbStatementName');
	var listOpt=document.createElement("option");
	selectdiv.length=0;
	selectdiv.appendChild(listOpt);
	listOpt.text="select";
	listOpt.value="select";
	var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;						
	
	for(var i=0; i<len4; i++){
			listOpt=document.createElement("option");
			selectdiv.appendChild(listOpt);
			listOpt.text=baseResponse.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue
			listOpt.value=baseResponse.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
	}*/

	var len = baseResponse.getElementsByTagName("cmbStatementName").length;
	if (flag == "success") {
		var pageno = document.getElementById("cmbpage").value;
		var ul = 0, ll = 0;
		ul = pageno * pagesize;
		ll = ul - pagesize;
		try
		{
		var path=baseResponse.getElementsByTagName("path")[0].firstChild.nodeValue;
		//alert("path"+path);
		if(len>0){
			for ( var k = ll; k < ul; k++) {
				var cmbStatementName = baseResponse
				.getElementsByTagName("cmbStatementName")[k].firstChild.nodeValue;
		var cmbStatementDesc = baseResponse
		.getElementsByTagName("cmbStatementdesc")[k].firstChild.nodeValue;
		
		var txtStatementGroupNo = baseResponse
				.getElementsByTagName("txtStatementGroupNo")[k].firstChild.nodeValue;
		var txtStatementGroupName = baseResponse
		.getElementsByTagName("txtStatementGroupName")[k].firstChild.nodeValue;
		var txtFromAccHdCode = baseResponse
				.getElementsByTagName("txtFromAccHdCode")[k].firstChild.nodeValue;
		var txtToAccHdCode = baseResponse
				.getElementsByTagName("txtToAccHdCode")[k].firstChild.nodeValue;
		var statementSubGroupNo = baseResponse
		.getElementsByTagName("STATEMENT_SUB_GROUP_NO")[k].firstChild.nodeValue;
		var statementSubGroupName = baseResponse
		.getElementsByTagName("STATEMENT_SUB_GROUP_Name")[k].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = cmbStatementName + txtStatementGroupNo + statementSubGroupNo
				+ txtFromAccHdCode + txtToAccHdCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbStatementName
				+ "','" + txtStatementGroupNo + "','" + statementSubGroupNo + "','" + txtFromAccHdCode
				+ "','" + txtToAccHdCode + "')";
		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
				
		var cell1 = document.createElement("TD");
		var cmbStatementNamee = document.createTextNode(cmbStatementDesc);
		cell1.appendChild(cmbStatementNamee);
		mycurrent_row.appendChild(cell1);
	
		//var cell11=document.createElement("TD");
		var stname = document.createElement("input");
		stname.type="hidden";
		stname.name="statementname";
		stname.id="statementname";
		stname.value=cmbStatementName;	
		cell1.appendChild(stname);
    	mycurrent_row.appendChild(cell1);
  			
    	var cell2 = document.createElement("TD");
		var txtStatementGroupNoo = document
				.createTextNode(txtStatementGroupName);
		cell2.appendChild(txtStatementGroupNoo);
		mycurrent_row.appendChild(cell2);
		
		var stgpno=document.createElement("input");
		stgpno.type="hidden";
		stgpno.name="statementGroupname";
		stgpno.id="statementGroupname";
		stgpno.value=txtStatementGroupNo;
		cell2.appendChild(stgpno);
    	mycurrent_row.appendChild(cell2);
    		
		var cell21 = document.createElement("TD");
		var txtStatementsGroupNoo = document.createTextNode(statementSubGroupName);
		cell21.appendChild(txtStatementsGroupNoo);
		mycurrent_row.appendChild(cell21);
		
		var stsubgpno=document.createElement("input");
		stsubgpno.type="hidden";
		stsubgpno.name="statementsubgpname";
		stsubgpno.id="statementsubgpname";
		stsubgpno.value=statementSubGroupNo;	       
		cell21.appendChild(stsubgpno);
    	mycurrent_row.appendChild(cell21);
    
		var cell3 = document.createElement("TD");
		var txtFromAccHdCode = document.createTextNode(txtFromAccHdCode);
		cell3.appendChild(txtFromAccHdCode);
		mycurrent_row.appendChild(cell3);
		
		var cell4 = document.createElement("TD");
		var txtToAccHdCode = document.createTextNode(txtToAccHdCode);
		cell4.appendChild(txtToAccHdCode);
		mycurrent_row.appendChild(cell4);
	
		tbody.appendChild(mycurrent_row);
			
			}
		}else{
			var iframe=document.getElementById("tblList");
	         iframe.focus();
			 if(navigator.appName.indexOf('Microsoft')!=-1){
	             iframe.innerHTML="<tr><td align=center colspan=6>There is No Data to Display</td></tr>";
	             
			 } else{
				 iframe.innerText="There is No Data to Display";
		         iframe.innerHTML="<tr><td align=center colspan=6>There is No Data to Display</td></tr>";
				
			 }
		}
		}catch(e){}
	} else {
		alert("Fail to Load Grid");
	}
}
function seconddata()
{
	try{
		document.getElementById("tblList").innerHTML="";
	}catch(e){
		document.getElementById("tblList").innerText="";
	}
	var len = baseResponse.getElementsByTagName("cmbStatementName").length;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == "success") {
		var pageno = document.getElementById("cmbpage").value;
		var ul = 0, ll = 0;
		ul = pageno * pagesize;
		ll = ul - pagesize;
		try{
		var path=baseResponse.getElementsByTagName("path")[0].firstChild.nodeValue;
		//alert("path"+path);
		if(len>0){
			for ( var k = ll; k < ul; k++) {
				var cmbStatementName = baseResponse
				.getElementsByTagName("cmbStatementName")[k].firstChild.nodeValue;
		var cmbStatementDesc = baseResponse
		.getElementsByTagName("cmbStatementdesc")[k].firstChild.nodeValue;
		
		var txtStatementGroupNo = baseResponse
				.getElementsByTagName("txtStatementGroupNo")[k].firstChild.nodeValue;
		var txtStatementGroupName = baseResponse
		.getElementsByTagName("txtStatementGroupName")[k].firstChild.nodeValue;
		var txtFromAccHdCode = baseResponse
				.getElementsByTagName("txtFromAccHdCode")[k].firstChild.nodeValue;
		var txtToAccHdCode = baseResponse
				.getElementsByTagName("txtToAccHdCode")[k].firstChild.nodeValue;
		var statementSubGroupNo = baseResponse
		.getElementsByTagName("STATEMENT_SUB_GROUP_NO")[k].firstChild.nodeValue;
		var statementSubGroupName = baseResponse
		.getElementsByTagName("STATEMENT_SUB_GROUP_Name")[k].firstChild.nodeValue;

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = cmbStatementName + txtStatementGroupNo + statementSubGroupNo
				+ txtFromAccHdCode + txtToAccHdCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbStatementName
				+ "','" + txtStatementGroupNo + "','" + statementSubGroupNo + "','" + txtFromAccHdCode
				+ "','" + txtToAccHdCode + "')";
		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
				
		var cell1 = document.createElement("TD");
		var cmbStatementNamee = document.createTextNode(cmbStatementDesc);
		cell1.appendChild(cmbStatementNamee);
		mycurrent_row.appendChild(cell1);
	
		//var cell11=document.createElement("TD");
		var stname = document.createElement("input");
		stname.type="hidden";
		stname.name="statementname";
		stname.id="statementname";
		stname.value=cmbStatementName;	
		cell1.appendChild(stname);
    	mycurrent_row.appendChild(cell1);
  			
    	var cell2 = document.createElement("TD");
		var txtStatementGroupNoo = document
				.createTextNode(txtStatementGroupName);
		cell2.appendChild(txtStatementGroupNoo);
		mycurrent_row.appendChild(cell2);
		
		var stgpno=document.createElement("input");
		stgpno.type="hidden";
		stgpno.name="statementGroupname";
		stgpno.id="statementGroupname";
		stgpno.value=txtStatementGroupNo;
		cell2.appendChild(stgpno);
    	mycurrent_row.appendChild(cell2);
    		
		var cell21 = document.createElement("TD");
		var txtStatementsGroupNoo = document.createTextNode(statementSubGroupName);
		cell21.appendChild(txtStatementsGroupNoo);
		mycurrent_row.appendChild(cell21);
		
		var stsubgpno=document.createElement("input");
		stsubgpno.type="hidden";
		stsubgpno.name="statementsubgpname";
		stsubgpno.id="statementsubgpname";
		stsubgpno.value=statementSubGroupNo;	       
		cell21.appendChild(stsubgpno);
    	mycurrent_row.appendChild(cell21);
    
		var cell3 = document.createElement("TD");
		var txtFromAccHdCode = document.createTextNode(txtFromAccHdCode);
		cell3.appendChild(txtFromAccHdCode);
		mycurrent_row.appendChild(cell3);
		
		var cell4 = document.createElement("TD");
		var txtToAccHdCode = document.createTextNode(txtToAccHdCode);
		cell4.appendChild(txtToAccHdCode);
		mycurrent_row.appendChild(cell4);
	
		tbody.appendChild(mycurrent_row);
		
			}
		}else{
			var iframe=document.getElementById("tblList");
	         iframe.focus();
			 if(navigator.appName.indexOf('Microsoft')!=-1){
	             iframe.innerHTML="<tr><td align=center colspan=6>There is No Data to Display</td></tr>";
	             
			 } else{
				 iframe.innerText="There is No Data to Display";
		         iframe.innerHTML="<tr><td align=center colspan=6>There is No Data to Display</td></tr>";
				
			 }
		}
		}catch(e){}
	} else {
		alert("Fail to Load Grid");
	}
}

function CheckAccHdCode(path) {
	var txtFromAccHdCode = document.getElementById("txtFromAccHdCode").value;
	if (txtFromAccHdCode.length < 6) {
		alert("Acc Hd Code Length Should be Six Char");
		document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.focus();
	}
}

function CheckAccHdCode1(path) {
	var txtToAccHdCode = document.getElementById("txtToAccHdCode").value;

	if (txtToAccHdCode.length < 6) {
		alert("Acc Hd Code Length Should be Six Char");
		document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
	}
}
function loadStatementSubGroupNo(path){
	var statementNo = document.getElementById("cmbStatementName").value;
	var statementGroupNo = document.getElementById("cmbStatementGroupNo").value;
	if (statementNo == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Acc_Hd_Mapping.cmbStatementName.focus();
	}else if(statementGroupNo == ""){
		alert("Select Statement Group Name in the Field");
		document.frmStatement_Acc_Hd_Mapping.cmbStatementGroupNo.focus();
	}else {
		var url = path
				+ "/Statement_Acc_Hd_Mapping?command=loadStatementSubGroupNo&statementNo="
				+ statementNo+"&statementGroupNo="+statementGroupNo;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);

	}
}
function LoadStatementGroupNo(path) {
	//alert(path);
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Acc_Hd_Mapping.cmbStatementName.focus();
	} else {
		var url = path
				+ "/Statement_Acc_Hd_Mapping?command=LoadStatementGroupNo&cmbStatementName="
				+ cmbStatementName;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};

		xmlrequest.send(null);

	}
}

function LoadStatementGroupNo1(baseResponse) {
	document.getElementById("cmbStatementGroupNo").length = 1;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("STATEMENT_GROUP_NO").length;
		for ( var i = 0; i < len4; i++) {
			var STATEMENT_GROUP_NO = baseResponse
					.getElementsByTagName("STATEMENT_GROUP_NO")[i].firstChild.nodeValue;
			var STATEMENT_GROUP_DESC = baseResponse
					.getElementsByTagName("STATEMENT_GROUP_DESC")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbStatementGroupNo");
			var op = document.createElement("OPTION");
			op.value = STATEMENT_GROUP_NO;
			var txt = document.createTextNode(STATEMENT_GROUP_DESC);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Statement Group No");
	}
}
function loadStatementSubGroupNo1(baseResponse) {
	document.getElementById("statementSubGroupNo").length = 1;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("STATEMENT_SUB_GROUP_NO").length;
		for ( var i = 0; i < len4; i++) {
			var STATEMENT_SUB_GROUP_NO = baseResponse.getElementsByTagName("STATEMENT_SUB_GROUP_NO")[i].firstChild.nodeValue;
			var STATEMENT_SUB_GROUP_DESC = baseResponse.getElementsByTagName("STATEMENT_SUB_GROUP_DESC")[i].firstChild.nodeValue;

			var sel = document.getElementById("statementSubGroupNo");
			var opt = document.createElement("OPTION");
			opt.value = STATEMENT_SUB_GROUP_NO;
			var txtt = document.createTextNode(STATEMENT_SUB_GROUP_DESC);
			opt.appendChild(txtt);
			sel.appendChild(opt);
		}
	} else {
		alert("Failed to Load Statement Sub Group No");
	}
}



function add(path) {
	//alert(path);
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("cmbStatementGroupNo").value;
	var statementSubGroupNo = document.getElementById("statementSubGroupNo").value;
	var txtFromAccHdCode = document.getElementById("txtFromAccHdCode").value;
	var txtToAccHdCode = document.getElementById("txtToAccHdCode").value;
	var groupCheck="";
	var accHdCode="";
	if(document.frmStatement_Acc_Hd_Mapping.groupType[0].checked==true){
		groupCheck=document.frmStatement_Acc_Hd_Mapping.groupType[0].value;
		accHdCode=document.getElementById("txtFromAccHdCode").value+" to "+document.getElementById("txtToAccHdCode").value;
	}else{
		groupCheck=document.frmStatement_Acc_Hd_Mapping.groupType[1].value;
		accHdCode=document.getElementById("txtFromAccHdCode").value+" , "+document.getElementById("txtToAccHdCode").value;
	}

	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Acc_Hd_Mapping.cmbStatementName.focus();
	} else if (txtStatementGroupNo == "") {
		alert("Select Statement Group No in the Field");
		document.frmStatement_Acc_Hd_Mapping.txtStatementGroupNo.focus();
	} else if (statementSubGroupNo == "") {
		alert("Select Statement Sub Group No in the Field");
		document.frmStatement_Acc_Hd_Mapping.txtStatementGroupNo.focus();
	} else if (txtFromAccHdCode == "") {
		alert("Enter From Acc Hd Code in the Field");
		document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.focus();
	} else if (txtToAccHdCode == "") {
		alert("Enter To Acc Hd Code in the Field");
		document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
	} else if (txtFromAccHdCode.length < 6) {
		alert("Acc Hd Code Length Should be Six Char");
		document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.focus();
	} else if (txtToAccHdCode.length < 6) {
		alert("Acc Hd Code Length Should be Six Char");
		document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
	}  else if ( (parseInt(txtFromAccHdCode)) > (parseInt(txtToAccHdCode)) ) {
		alert("To Acc Hd Code Should be Greater than From Acc Hd Code");
		document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
	} else {

		var url = path
				+ "/Statement_Acc_Hd_Mapping?command=add&txtStatementGroupNo="
				+ txtStatementGroupNo + "&txtFromAccHdCode=" + txtFromAccHdCode
				+ "&cmbStatementName=" + cmbStatementName + "&txtToAccHdCode="
				+ txtToAccHdCode+"&groupCheck="+groupCheck+"&accHdCode="+accHdCode
				+"&statementSubGroupNo="+statementSubGroupNo;

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

		var cmbStatementName = baseResponse
				.getElementsByTagName("cmbStatementName")[0].firstChild.nodeValue;
		var txtStatementGroupNo = baseResponse
				.getElementsByTagName("txtStatementGroupNo")[0].firstChild.nodeValue;
		var txtFromAccHdCode = baseResponse
				.getElementsByTagName("txtFromAccHdCode")[0].firstChild.nodeValue;
		var txtToAccHdCode = baseResponse
				.getElementsByTagName("txtToAccHdCode")[0].firstChild.nodeValue;
		var statementSubGroup = baseResponse
		.getElementsByTagName("statementSubGroup")[0].firstChild.nodeValue;
		
		var txtStatementGroupName = baseResponse
		.getElementsByTagName("txtStatementGroupName")[0].firstChild.nodeValue;
		var statementSubGroupName = baseResponse
		.getElementsByTagName("statementSubGroupName")[0].firstChild.nodeValue;
		var statementName = baseResponse
		.getElementsByTagName("statementName")[0].firstChild.nodeValue;
		
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = cmbStatementName + txtStatementGroupNo + statementSubGroup
				+ txtFromAccHdCode + txtToAccHdCode;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbStatementName + "','"
				+ txtStatementGroupNo + "','" + statementSubGroup + "','" + txtFromAccHdCode + "','"
				+ txtToAccHdCode + "')";
//		alert(url);
		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell1 = document.createElement("TD");
		var cmbStatementNamee = document.createTextNode(statementName);
		cell1.appendChild(cmbStatementNamee);
		mycurrent_row.appendChild(cell1);
		
		var stname = document.createElement("input");
		stname.type="hidden";
		stname.name="statementname";
		stname.id="statementname";
		stname.value=cmbStatementName;	       
		cell1.appendChild(stname);
    	mycurrent_row.appendChild(cell1);
		

		var cell2 = document.createElement("TD");
		var txtStatementGroupNoo = document.createTextNode(txtStatementGroupName);
		cell2.appendChild(txtStatementGroupNoo);
		mycurrent_row.appendChild(cell2);
		
		var stgpname = document.createElement("input");
		stgpname.type="hidden";
		stgpname.name="statementGroupname";
		stgpname.id="statementGroupname";
		stgpname.value=txtStatementGroupNo;	       
		cell2.appendChild(stgpname);
    	mycurrent_row.appendChild(cell2);
		
		var cell21 = document.createElement("TD");
		var statementsGroupNoo = document.createTextNode(statementSubGroupName);
		cell21.appendChild(statementsGroupNoo);
		mycurrent_row.appendChild(cell21);
		
		var stsubgpname = document.createElement("input");
		stsubgpname.type="hidden";
		stsubgpname.name="statementSubGroupname";
		stsubgpname.id="statementSubGroupname";
		stsubgpname.value=statementSubGroup;	       
		cell21.appendChild(stsubgpname);
    	mycurrent_row.appendChild(cell21);

		var cell3 = document.createElement("TD");
		var txtFromAccHdCode = document.createTextNode(txtFromAccHdCode);
		cell3.appendChild(txtFromAccHdCode);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var txtToAccHdCode = document.createTextNode(txtToAccHdCode);
		cell4.appendChild(txtToAccHdCode);
		mycurrent_row.appendChild(cell4);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exists") {
		var flag_up = baseResponse
		.getElementsByTagName("flag_up")[0].firstChild.nodeValue;
		if (flag_up == "success") {
			alert("Record Updated Into Database successfully.");
			document.getElementById("onsubmit").disabled=false;
			document.getElementById("onUpdate").disabled=true;
			document.getElementById("ondelete").disabled=true;
			refresh();
		
		}else{
			alert("Not Updated.");
		}
		
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(cmbStatementName, txtStatementGroupNo,statementSubGroupNo,
		txtFromAccHdCode, txtToAccHdCode) {	
	var url = "../../../../../Statement_Acc_Hd_Mapping?command=edit&statementNo="+cmbStatementName+
			  "&statementGroup="+txtStatementGroupNo+"&statementSub="+statementSubGroupNo+
			  "&txtFromAccHdCode="+txtFromAccHdCode+"&txtToAccHdCode="+txtToAccHdCode;
//	 alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

	/*var r = document.getElementById(cmbStatementName + txtStatementGroupNo + statementSubGroupNo
			+ txtFromAccHdCode + txtToAccHdCode);
	var rcells = r.cells;

	document.frmStatement_Acc_Hd_Mapping.cmbStatementName.value = rcells
			.item(1).firstChild.nodeValue;
	document.frmStatement_Acc_Hd_Mapping.cmbStatementGroupNo.value = rcells
			.item(2).firstChild.nodeValue;
	document.frmStatement_Acc_Hd_Mapping.statementSubGroupNo.value = rcells
			.item(3).firstChild.nodeValue;
	document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.value = rcells
			.item(4).firstChild.nodeValue;
	document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.value = rcells.item(5).firstChild.nodeValue;*/

}

function deleteeee(path) {
	//alert(path);
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("cmbStatementGroupNo").value;
	var txtFromAccHdCode = document.getElementById("txtFromAccHdCode").value;
	var txtToAccHdCode = document.getElementById("txtToAccHdCode").value;

	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Acc_Hd_Mapping.cmbStatementName.focus();
	} else if (txtStatementGroupNo == "") {
		alert("Enter Statement Group No in the Field");
		document.frmStatement_Acc_Hd_Mapping.txtStatementGroupNo.focus();
	} else if (txtFromAccHdCode == "") {
		alert("Enter From Acc Hd Code in the Field");
		document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.focus();
	} else if (txtToAccHdCode == "") {
		alert("Enter To Acc Hd Code in the Field");
		document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
	} else if (txtFromAccHdCode.length < 6) {
		alert("Acc Hd Code Length Should be Six Char");
		document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.focus();
	} else if (txtToAccHdCode.length < 6) {
		alert("Acc Hd Code Length Should be Six Char");
		document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
	} else {
		var r = confirm("Are U Sure to Continue?");
		if (r == true) {
			var url = path
					+ "/Statement_Acc_Hd_Mapping?command=deleted&txtStatementGroupNo="
					+ txtStatementGroupNo + "&txtFromAccHdCode="
					+ txtFromAccHdCode + "&cmbStatementName="
					+ cmbStatementName + "&txtToAccHdCode=" + txtToAccHdCode;
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
		var ApportCode1 = baseResponse.getElementsByTagName("id1")[0].firstChild.nodeValue;
		var ApportCode2 = baseResponse.getElementsByTagName("id2")[0].firstChild.nodeValue;
		var ApportCode3 = baseResponse.getElementsByTagName("id3")[0].firstChild.nodeValue;
		var ApportCode4 = baseResponse.getElementsByTagName("id4")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode+ApportCode1+ApportCode4+ApportCode2+ApportCode3);
		//var r = document.getElementById(ApportCode+','+ApportCode1+','+ApportCode2+','+ApportCode3);
//		alert("value of row id::::::::"+r);
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

function refresh() {

	document.getElementById("cmbStatementName").disabled = false;
	document.getElementById("cmbStatementGroupNo").disabled = false;
	document.getElementById("txtFromAccHdCode").disabled = false;
	document.getElementById("txtToAccHdCode").disabled = false;
	document.frmStatement_Acc_Hd_Mapping.groupType[0].checked=true;

	document.frmStatement_Acc_Hd_Mapping.cmbStatementName.value = "";
	//document.frmStatement_Acc_Hd_Mapping.cmbStatementGroupNo.value = "";
	document.getElementById("cmbStatementGroupNo").length=1;
	document.getElementById("statementSubGroupNo").length=1;
	document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.value = "";
	document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.value = "";
 
	document.frmStatement_Acc_Hd_Mapping.txtFromAcc_HeadDesc.value = "";
	document.frmStatement_Acc_Hd_Mapping.txtToAcc_HeadDesc.value = "";

	document.frmStatement_Acc_Hd_Mapping.onsubmit.disabled = false;
	document.frmStatement_Acc_Hd_Mapping.ondelete.disabled = true;
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
function clearGroupCheck(){
	if(document.getElementById('ondelete').disabled==true){
		document.getElementById('txtFromAccHdCode').value="";
		document.getElementById('txtToAccHdCode').value="";
	}
	if(document.frmStatement_Acc_Hd_Mapping.groupType[0].checked==true){
	
		document.getElementById('txtFromAccHdCode').disabled=false;
		document.getElementById('txtToAccHdCode').disabled=false;				
	}else{	
		document.getElementById('txtFromAccHdCode').disabled=false;
	//	document.getElementById('txtFromAccHdCode').disabled=true;
		document.getElementById('txtToAccHdCode').disabled=false;
	}
}

function checkGrouptype(){
	//alert("checkGrouptype");
	if(document.frmStatement_Acc_Hd_Mapping.groupType[0].checked==true){
	//	alert(document.getElementById('txtFromAccHdCode').value);
		//alert(document.getElementById('txtToAccHdCode').value);
		document.getElementById('txtToAccHdCode').disabled=false;
		if((document.getElementById('txtToAccHdCode').value!="")&&(document.getElementById('txtFromAccHdCode').value>document.getElementById('txtToAccHdCode').value)){
			
			alert("From Account Head Code should be less than To Account Head Code ");
			document.getElementById('txtFromAccHdCode').value="";
			document.getElementById('txtToAccHdCode').value="";
		}
	}else{
	//alert("Else part ... ");
		document.getElementById('txtFromAccHdCode').disabled=false;
		document.getElementById('txtToAccHdCode').disabled=false;
		// joe Change
		//document.getElementById('txtToAccHdCode').value=document.getElementById('txtFromAccHdCode').value;
	}
}
 function Update_DAta(path){

		//alert(path);
		var cmbStatementName = document.getElementById("cmbStatementName").value;
		var txtStatementGroupNo = document.getElementById("cmbStatementGroupNo").value;
		var statementSubGroupNo = document.getElementById("statementSubGroupNo").value;
		var txtFromAccHdCode = document.getElementById("txtFromAccHdCode").value;
		var txtToAccHdCode = document.getElementById("txtToAccHdCode").value;
		var groupCheck="";
		var accHdCode="";
		if(document.frmStatement_Acc_Hd_Mapping.groupType[0].checked==true){
			groupCheck=document.frmStatement_Acc_Hd_Mapping.groupType[0].value;
			accHdCode=document.getElementById("txtFromAccHdCode").value+" to "+document.getElementById("txtToAccHdCode").value;
		}else{
			groupCheck=document.frmStatement_Acc_Hd_Mapping.groupType[1].value;
			accHdCode=document.getElementById("txtFromAccHdCode").value+" , "+document.getElementById("txtToAccHdCode").value;
		}

		if (cmbStatementName == "") {
			alert("Select Statement Name in the Field");
			document.frmStatement_Acc_Hd_Mapping.cmbStatementName.focus();
		} else if (txtStatementGroupNo == "") {
			alert("Select Statement Group No in the Field");
			document.frmStatement_Acc_Hd_Mapping.txtStatementGroupNo.focus();
		} else if (statementSubGroupNo == "") {
			alert("Select Statement Sub Group No in the Field");
			document.frmStatement_Acc_Hd_Mapping.txtStatementGroupNo.focus();
		} else if (txtFromAccHdCode == "") {
			alert("Enter From Acc Hd Code in the Field");
			document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.focus();
		} else if (txtToAccHdCode == "") {
			alert("Enter To Acc Hd Code in the Field");
			document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
		} else if (txtFromAccHdCode.length < 6) {
			alert("Acc Hd Code Length Should be Six Char");
			document.frmStatement_Acc_Hd_Mapping.txtFromAccHdCode.focus();
		} else if (txtToAccHdCode.length < 6) {
			alert("Acc Hd Code Length Should be Six Char");
			document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
		}  else if ( (parseInt(txtFromAccHdCode)) > (parseInt(txtToAccHdCode)) ) {
			alert("To Acc Hd Code Should be Greater than From Acc Hd Code");
			document.frmStatement_Acc_Hd_Mapping.txtToAccHdCode.focus();
		} else {

			var url = path
					+ "/Statement_Acc_Hd_Mapping?command=Update&txtStatementGroupNo="
					+ txtStatementGroupNo + "&txtFromAccHdCode=" + txtFromAccHdCode
					+ "&cmbStatementName=" + cmbStatementName + "&txtToAccHdCode="
					+ txtToAccHdCode+"&groupCheck="+groupCheck+"&accHdCode="+accHdCode
					+"&statementSubGroupNo="+statementSubGroupNo;

			 //alert(url);
			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);

			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}

 }

function edit1(baseResponse){
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success"){
		
		document.getElementById("cmbStatementName").disabled = true;
		document.getElementById("cmbStatementGroupNo").disabled = false;
		document.getElementById("statementSubGroupNo").disabled = false;
		document.getElementById('cmbStatementName').value=baseResponse.getElementsByTagName("STATEMENT_NO")[0].firstChild.nodeValue;
		
		var selectdiv1=document.getElementById('cmbStatementGroupNo');
		selectdiv1.removeChild(selectdiv1.lastChild);
		var listOpt1=document.createElement("option");
//		alert("group desc"+baseResponse.getElementsByTagName("GROUP_DESC")[0].firstChild.nodeValue);
		listOpt1.text=baseResponse.getElementsByTagName("GROUP_DESC")[0].firstChild.nodeValue;
		listOpt1.value=baseResponse.getElementsByTagName("GROUP_NO")[0].firstChild.nodeValue;
		selectdiv1.appendChild(listOpt1);
		//document.getElementById('cmbStatementGroupNo').value=baseResponse.getElementsByTagName("GROUP_NO")[0].firstChild.nodeValue;
		
		var selectdiv=document.getElementById('statementSubGroupNo');
		selectdiv.removeChild(selectdiv.lastChild);
//		alert("sub groFup desc"+baseResponse.getElementsByTagName("SUB_GROUP_DEC")[0].firstChild.nodeValue);
		var listOpt=document.createElement("option");		
		listOpt.text=baseResponse.getElementsByTagName("SUB_GROUP_DEC")[0].firstChild.nodeValue;
		listOpt.value=baseResponse.getElementsByTagName("SUB_GROUP_NO")[0].firstChild.nodeValue;
		selectdiv.appendChild(listOpt);
	
	//	var dd = document.getElementById('cmbStatementGroupNo');
		LoadStatementGroupNo("../../../../../");
		alert(baseResponse.getElementsByTagName("GROUP_NO")[0].firstChild.nodeValue);
		document.getElementById('cmbStatementGroupNo').value=baseResponse.getElementsByTagName("GROUP_NO")[0].firstChild.nodeValue;
		loadStatementSubGroupNo("../../../../../");
		alert(baseResponse.getElementsByTagName("SUB_GROUP_NO")[0].firstChild.nodeValue);
		document.getElementById('statementSubGroupNo').value=baseResponse.getElementsByTagName("SUB_GROUP_NO")[0].firstChild.nodeValue;
		
		
	/*	for (var i = 0; i < dd.options.length; i++) {
			//
			alert(dd.options[i].value+"  "+baseResponse.getElementsByTagName("GROUP_NO")[0].firstChild.nodeValue);
		    if (dd.options[i].value == baseResponse.getElementsByTagName("GROUP_NO")[0].firstChild.nodeValue) {
		       //dd.selectedIndex = i;
		    	dd.options[i].selected = true;
		        break;
		        
		    }
		  
		}
		var dd1 = document.getElementById('statementSubGroupNo');
		loadStatementSubGroupNo("../../../../../");
	
		for (var j = 0; j < dd1.options.length; j++) {
			alert(dd1.options[j].value+"  "+baseResponse.getElementsByTagName("SUB_GROUP_NO")[0].firstChild.nodeValue);
		    if (dd1.options[j].value == baseResponse.getElementsByTagName("SUB_GROUP_NO")[0].firstChild.nodeValue) {
		       //dd1.selectedIndex = j;
		    	 dd1.options[j].selectedIndex = true;
		        break;
		    }
		}*/
		
		if(baseResponse.getElementsByTagName("GROUP_TYPE")[0].firstChild.nodeValue=="G"){
			document.frmStatement_Acc_Hd_Mapping.groupType[0].checked=true;
		}else if(baseResponse.getElementsByTagName("GROUP_TYPE")[0].firstChild.nodeValue=="H"){
			document.frmStatement_Acc_Hd_Mapping.groupType[1].checked=true;
		}		
		document.getElementById('txtFromAccHdCode').value=baseResponse.getElementsByTagName("FROM_ACC_HD_CODE")[0].firstChild.nodeValue;
		LoadAccHdDescFrom1("../../../../../");
		document.getElementById('txtToAccHdCode').value=baseResponse.getElementsByTagName("TO_ACC_HD_CODE")[0].firstChild.nodeValue;
		LoadAccHdDescTo1("../../../../../");
		checkGrouptype();
		document.frmStatement_Acc_Hd_Mapping.onsubmit.disabled = true;
		document.frmStatement_Acc_Hd_Mapping.onUpdate.disabled=false;
		document.frmStatement_Acc_Hd_Mapping.ondelete.disabled = false;
	}else{
		alert("Connection Closed");
	}
}
