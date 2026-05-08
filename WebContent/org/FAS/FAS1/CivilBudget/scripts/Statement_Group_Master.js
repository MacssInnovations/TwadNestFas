//		Statement_Group_Master		//
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
			//alert(xmlrequest.responseText);
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
            //alert(baseResponse);
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
			}/* else if (command == "getGrid") {
				
				firstLoad(baseResponse);
			}*/
		}
	}
}
function manipulate1(xmlrequest,path) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
			//alert(xmlrequest.responseText);
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
            //alert(baseResponse);
			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				findDel(baseResponse,path);
			} else if (command == "update") {
				findUpdate(baseResponse,path);
			} else if (command == "Edit") {
				Edit1(baseResponse);
			}/* else if (command == "getGrid") {
				
				firstLoad(baseResponse);
			}*/
		}
	}
}
function changepagesize() {	
	//alert(pagesize);
	pagesize = document.getElementById("cmbpagination").value;
	//alert(pagesize = document.getElementById("cmbpagination").value);
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	//var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	var len = baseResponse.getElementsByTagName("cmbStatementcode").length;	
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

function initialLoad(path) {
	//alert("path::::"+path);
	var cmbStatementName=document.getElementById("cmbStatementName").value;
    if(cmbStatementName=="")
    {
	var url = path + "/Statement_Group_Master?command=getGrid";
    
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		viewdata(xmlrequest);
	
	}

	xmlrequest.send(null);
   }
}

function firstLoad() {
	try{
		document.getElementById('tblList').innerHTML="";
	}catch(e){
		document.getElementById('tblList').innerText="";
	}
	var len = baseResponse.getElementsByTagName("cmbStatementcode").length;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == "success") {
		
		var pageno = document.getElementById("cmbpage").value;
		var ul = 0, ll = 0;
		ul = pageno * pagesize;
		ll = ul - pagesize;
	
		try{
		for ( var k = ll; k < ul; k++) {
			var cmbStatementcode = baseResponse.getElementsByTagName("cmbStatementcode")[k].firstChild.nodeValue;
			var cmbStatementName = baseResponse.getElementsByTagName("cmbStatementName")[k].firstChild.nodeValue;
			//alert(cmbStatementName);
			var txtStatementGroupNo = baseResponse.getElementsByTagName("txtStatementGroupNo")[k].firstChild.nodeValue;
			var txtStatementGroupDesc = baseResponse
					.getElementsByTagName("txtStatementGroupDesc")[k].firstChild.nodeValue;
			
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = cmbStatementcode+txtStatementGroupNo;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbStatementcode + "','" + txtStatementGroupNo + "')";
            //alert(url);
			anc.href = url;
			
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell1 = document.createElement("TD");
			var cmbStatementName = document.createTextNode(cmbStatementName);
			cell1.appendChild(cmbStatementName);
			mycurrent_row.appendChild(cell1);
			
			var cell2 = document.createElement("TD");
			var txtStatementGroupNo = document.createTextNode(txtStatementGroupNo);
			cell2.appendChild(txtStatementGroupNo);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtStatementGroupDesc = document.createTextNode(txtStatementGroupDesc);
			cell3.appendChild(txtStatementGroupDesc);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);
		}
		}catch(e){}
	} else {
		alert("Fail to Load Grid");
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

function statementname(path)
{
	var url = path + "/Statement_Group_Master?command=statementname";
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {
				var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if (flag == "success") {
					//alert(flag);
					var se = document.getElementById("cmbStatementName");
					se.length=0;
					var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;
					
					for ( var i = 0; i < len4; i++) {
						var STATEMENT_NO = baseResponse.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
						var STATEMENT_DESC = baseResponse.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue;
						
						
						var op = document.createElement("OPTION");
						op.value = STATEMENT_NO;
						var txt = document.createTextNode(STATEMENT_DESC);
						op.appendChild(txt);
						se.appendChild(op);
						
					}		
				}
			}
		}
	};
	xmlrequest.send(null);
}

function secondload(path)
{
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var url = path + "/Statement_Group_Master?command=secondload&cmbStatementName="+cmbStatementName;
	 //alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		secondview(xmlrequest);
		
	}
	xmlrequest.send(null);
}
		
function seconddata()
{
	try{
		document.getElementById('tblList').innerHTML="";
	}catch(e){
		document.getElementById('tblList').innerText="";
	}
               // alert(xmlrequest.responseText);
	var len = baseResponse.getElementsByTagName("txtStatementGroupNo").length;
				//alert(baseResponse);
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				//alert(flag);
				if (flag == "success") {
		   
					var pageno = document.getElementById("cmbpage").value;
					var ul = 0, ll = 0;
					ul = pageno * pagesize;
					ll = ul - pagesize;
				
					try{	
					
		   
		   for ( var k = ll; k < ul; k++) {
			   var cmbStatementcode = baseResponse.getElementsByTagName("cmbStatementcode")[k].firstChild.nodeValue;
			var cmbStatementName = baseResponse.getElementsByTagName("cmbStatementName")[k].firstChild.nodeValue;
			var txtStatementGroupNo = baseResponse.getElementsByTagName("txtStatementGroupNo")[k].firstChild.nodeValue;
			var txtStatementGroupDesc = baseResponse
					.getElementsByTagName("txtStatementGroupDesc")[k].firstChild.nodeValue;
			
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");
			var mycurrent_row = document.createElement("TR");

			mycurrent_row.id = cmbStatementcode+txtStatementGroupNo;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cmbStatementcode + "','" + txtStatementGroupNo + "')";

			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);
			
			//document.getElementById("sname").style.display='block';

			var cell1 = document.createElement("TD");
			var cmbStatementName = document.createTextNode(cmbStatementName);
			cell1.appendChild(cmbStatementName);
			mycurrent_row.appendChild(cell1);
			
			var cell2 = document.createElement("TD");
			var txtStatementGroupNo = document.createTextNode(txtStatementGroupNo);
			cell2.appendChild(txtStatementGroupNo);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtStatementGroupDesc = document.createTextNode(txtStatementGroupDesc);
			cell3.appendChild(txtStatementGroupDesc);
			mycurrent_row.appendChild(cell3);

			tbody.appendChild(mycurrent_row);
		}
	}catch(e){}
	} else {
		alert("Fail to Load Grid");
	}
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

function add(path) {
	//alert(path);
    var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("txtStatementGroupNo").value;
	var txtStatementGroupDesc = document.getElementById("txtStatementGroupDesc").value;
	
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Group_Master.cmbStatementName.focus();
	} else if (txtStatementGroupDesc == "") {
		alert("Enter Statement Group Desc in the Field");
		document.frmStatement_Group_Master.txtStatementGroupDesc.focus();
	} else {

		var url = path + "/Statement_Group_Master?command=add&txtStatementGroupNo="
				+ txtStatementGroupNo + "&txtStatementGroupDesc=" + txtStatementGroupDesc + "&cmbStatementName=" + cmbStatementName;

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

	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");

		refresh();
	/*	var cmbStatementDesc = baseResponse.getElementsByTagName("cmbStatementDesc")[0].firstChild.nodeValue;
		alert(cmbStatementDesc);*/
		var cmbStatementName = baseResponse.getElementsByTagName("cmbStatementName")[0].firstChild.nodeValue;
		//alert(cmbStatementName);
		var txtStatementGroupNo = baseResponse.getElementsByTagName("txtStatementGroupNo")[0].firstChild.nodeValue;
		var txtStatementGroupDesc = baseResponse
				.getElementsByTagName("txtStatementGroupDesc")[0].firstChild.nodeValue;
		
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");

		mycurrent_row.id = cmbStatementName+txtStatementGroupNo;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cmbStatementName + "','" + txtStatementGroupNo + "')";

		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell1 = document.createElement("TD");
		var cmbStatementDesc = document.createTextNode(cmbStatementName);
		cell1.appendChild(cmbStatementDesc);
		mycurrent_row.appendChild(cell1);
		
		var cell2 = document.createElement("TD");
		var txtStatementGroupNo = document.createTextNode(txtStatementGroupNo);
		cell2.appendChild(txtStatementGroupNo);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var txtStatementGroupDesc = document.createTextNode(txtStatementGroupDesc);
		cell3.appendChild(txtStatementGroupDesc);
		mycurrent_row.appendChild(cell3);

		tbody.appendChild(mycurrent_row);

	} else if (flag == "Exist") {
		alert("The Given Data is Already Exist");
	} else {
		refresh();
		alert("Failed to Add values");
	}
}

function loadValuesFromTable(cmbStatementcode,txtStatementGroupNo) {
	//alert(cmbStatementcode);
	//alert(txtStatementGroupNo);
	var url ="../../../../../Statement_Group_Master?command=EditData&cmbStatementcode="+cmbStatementcode+
	              "&txtStatementGroupNo="+txtStatementGroupNo;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {
              //alert(xmlrequest.responseText);
				var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
				//alert(baseResponse);
				var cmbStatementcode = baseResponse.getElementsByTagName("cmbStatementcode")[0].firstChild.nodeValue;
				//alert(cmbStatementcode);
				var cmbStatementName = baseResponse.getElementsByTagName("cmbStatementName")[0].firstChild.nodeValue;
				//alert(cmbStatementName);
				var txtStatementGroupNo = baseResponse.getElementsByTagName("txtStatementGroupNo")[0].firstChild.nodeValue;
			    var txtStatementGroupDesc=baseResponse.getElementsByTagName("txtStatementGroupDesc")[0].firstChild.nodeValue;
				
			    var se = document.getElementById("cmbStatementName");
				var op = document.createElement("OPTION");
				op.value = cmbStatementcode;
				var txt = document.createTextNode(cmbStatementName);
				op.appendChild(txt);
				se.appendChild(op);

				document.getElementById("cmbStatementName").value = cmbStatementcode;
				
				document.getElementById("txtStatementGroupNo").value=txtStatementGroupNo;
				
				document.getElementById("txtStatementGroupDesc").value=txtStatementGroupDesc;
				
				document.getElementById("cmbStatementName").disabled = true;
				document.getElementById("txtStatementGroupNo").disabled = true;
				/*var r = document.getElementById(cmbStatementName+txtStatementGroupNo);
				var rcells = r.cells;

				document.frmStatement_Group_Master.cmbStatementName.value = rcells.item(1).firstChild.nodeValue;
				alert(document.frmStatement_Group_Master.cmbStatementName.value = rcells.item(1).firstChild.nodeValue);
				document.frmStatement_Group_Master.txtStatementGroupNo.value = rcells.item(2).firstChild.nodeValue;
				document.frmStatement_Group_Master.txtStatementGroupDesc.value = rcells.item(3).firstChild.nodeValue;*/

				document.frmStatement_Group_Master.onsubmit.disabled = true;
				document.frmStatement_Group_Master.ondelete.disabled = false;
				document.frmStatement_Group_Master.onupdate.disabled = false;	
			
			}
		}
	
	
	
	
	
	};
	xmlrequest.send(null);
}

function update(path) {
	//alert(path);
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("txtStatementGroupNo").value;
	var txtStatementGroupDesc = document.getElementById("txtStatementGroupDesc").value;
	
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Group_Master.cmbStatementName.focus();
	} else if (txtStatementGroupNo == "") {
		alert("Enter Statement Group No in the Field");
		document.frmStatement_Group_Master.txtStatementGroupNo.focus();
	} else if (txtStatementGroupDesc == "") {
		alert("Enter Statement Group Desc in the Field");
		document.frmStatement_Group_Master.txtStatementGroupDesc.focus();
	} else {

		var url = path + "/Statement_Group_Master?command=update&txtStatementGroupNo="
				+ txtStatementGroupNo + "&txtStatementGroupDesc=" + txtStatementGroupDesc+ "&cmbStatementName=" + cmbStatementName;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate1(xmlrequest,path);
		}
		xmlrequest.send(null);
	}
}

function findUpdate(baseResponse,path) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
		statementname(path);
	   
		/*var items = new Array();
		items[0] = baseResponse.getElementsByTagName("cmbStatementName")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("txtStatementGroupNo")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("txtStatementGroupDesc")[0].firstChild.nodeValue;
		
		var r = document.getElementById(items[0]+items[1]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];		
		rcells.item(3).firstChild.nodeValue = items[2];*/
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Failed to update values");
	}
}

function deleteeee(path) {
	//alert(path);
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("txtStatementGroupNo").value;	
	
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Group_Master.cmbStatementName.focus();
	} else if (txtStatementGroupNo == "") {
		alert("Enter Statement Group No in the Field");
		document.frmStatement_Group_Master.txtStatementGroupNo.focus();
	} else {
		var r = confirm("Are U Sure to Continue?");
		if (r == true) {
			var url = path + "/Statement_Group_Master?command=deleted&txtStatementGroupNo="
					+ txtStatementGroupNo + "&cmbStatementName=" + cmbStatementName;
			// alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate1(xmlrequest,path);
			}
			xmlrequest.send(null);
		}
	}
}

function findDel(baseResponse,path) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var ApportCode = baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
		var ApportCode1 = baseResponse.getElementsByTagName("id1")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode+ApportCode1);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Records Deleted Successfully");
		refresh();
		statementname(path);
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function refresh() {
	document.getElementById("cmbStatementName").disabled = false;
	//document.getElementById("txtStatementGroupNo").disabled = false;
	document.frmStatement_Group_Master.cmbStatementName.value = "";
	document.frmStatement_Group_Master.txtStatementGroupNo.value = "";
	document.frmStatement_Group_Master.txtStatementGroupDesc.value = "";
	
	document.frmStatement_Group_Master.onsubmit.disabled = false;
	document.frmStatement_Group_Master.ondelete.disabled = true;
	document.frmStatement_Group_Master.onupdate.disabled = true;
	var pat=document.getElementById('strpath').value;
	//alert(pat);
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

function exitfun() {
	window.close();
}
