//		Statement_Group_Master		//
var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, baseResponse="";
var pagesize = 10;
var seq = 0;
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
			} else if (command == "update") {
				updateRow(baseResponse);
			} /*else if (command == "getGrid") {
				firstLoad(baseResponse);
			}*/else if (command == "loadStatementName") {
				loadStatementName(baseResponse);
			}
		}
	}
}

function changepagesize() {	
	pagesize = document.getElementById("cmbpagination").value;
	var len = baseResponse.getElementsByTagName("StatementNo").length;	
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
	}else
	{
    seconddata();
	}
}



function initialLoad(path) {
	//alert(path);
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	if(cmbStatementName=="")
	{
	var url = path + "/Statement_Sub_Group_Master?command=getGrid&path="+path;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		viewData(xmlrequest);
	}

	xmlrequest.send(null);
	}
}

function firstLoad() {
	var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var len = baseResponse.getElementsByTagName("StatementNo").length;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
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
				var statementNo = baseResponse.getElementsByTagName("StatementNo")[k].firstChild.nodeValue;
				var cmbStatementName = baseResponse.getElementsByTagName("cmbStatementName")[k].firstChild.nodeValue;
				var txtStatementGroupNo = baseResponse.getElementsByTagName("txtStatementGroupNo")[k].firstChild.nodeValue;
				var txtStatementGroupDesc=baseResponse.getElementsByTagName("txtStatementGroupDesc")[k].firstChild.nodeValue;
				var StatementSubGroupNo=baseResponse.getElementsByTagName("StatementSubGroupNo")[k].firstChild.nodeValue;
				var StatementSubGroupDesc=baseResponse.getElementsByTagName("StatementSubGroupDesc")[k].firstChild.nodeValue;
				var view=baseResponse.getElementsByTagName("STATUS")[k].firstChild.nodeValue;
				var tbody = document.getElementById("tblList");
				var table = document.getElementById("Existing");
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;
				var td = document.createElement("TD");
				var anc = document.createElement("A");
				if (view == "C") {
					var priceSpan = document.createElement("span");
					priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
					priceSpan.appendChild(document.createTextNode("Cancel"));			
					td.appendChild(priceSpan);
				}else{
					var url = "javascript:viewDetails('" + seq + "','" + path + "')";
					anc.href = url;
					var edit = document.createTextNode("Edit");
					anc.appendChild(edit);
					td.appendChild(anc);
					var sch_id=document.createElement("TEXT");
		        	sch_id.type="hidden";
		        	sch_id.name="name"+seq;
		        	sch_id.id="id"+seq;
		        	sch_id.value="&statementNo="+statementNo+"&statementGroupNo="+txtStatementGroupNo+"&statementSubGroupNo="+StatementSubGroupNo;	       
		        	td.appendChild(sch_id);
				}
				
				mycurrent_row.appendChild(td);
	
				var cell1 = document.createElement("TD");
				var cmbStatementName = document.createTextNode(cmbStatementName);
				cell1.appendChild(cmbStatementName);
				mycurrent_row.appendChild(cell1);
	
				var cell3 = document.createElement("TD");
				var txtStatementGroupDesc = document.createTextNode(txtStatementGroupDesc);
				cell3.appendChild(txtStatementGroupDesc);
				mycurrent_row.appendChild(cell3);

				var cell2 = document.createElement("TD");
				var txtStatementGroupNo = document.createTextNode(StatementSubGroupNo);
				cell2.appendChild(txtStatementGroupNo);
				mycurrent_row.appendChild(cell2);
				
				var cell12 = document.createElement("TD");
				var groupNo = document.createTextNode(StatementSubGroupDesc);
				cell12.appendChild(groupNo);
				mycurrent_row.appendChild(cell12);
				var td5 = document.createElement("TD");
				if(view=="C"){
					var tdst = document.createTextNode("CANCEL");
				}else{
					var tdst = document.createTextNode("LIVE");
				}
				td5.appendChild(tdst);
				mycurrent_row.appendChild(td5);
	
				tbody.appendChild(mycurrent_row);
				seq++;
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

function viewData(xmlrequest)
{
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
          //alert(xmlrequest.responseText);
			baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			changepagesize();
		}
	}
			
}

function statementname(path)
{
	var url = path + "/Statement_Sub_Group_Master?command=statementname&path="+path;
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
function secondload(path)
{
    //alert("second......"+path);
	var cmbStatementName=document.getElementById("cmbStatementName").value;
	var statementGroupName=document.getElementById("statementGroupName").value;
	var url = path + "/Statement_Sub_Group_Master?command=secondload&path="+path+"&cmbStatementName="+cmbStatementName+
	                "&statementGroupName="+statementGroupName;
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
		document.getElementById("tblList").innerHTML="";
	}catch(e){
		document.getElementById("tblList").innerText="";
	}
	var len = baseResponse.getElementsByTagName("StatementNo").length;
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
				var statementNo = baseResponse.getElementsByTagName("StatementNo")[k].firstChild.nodeValue;
				var cmbStatementName = baseResponse.getElementsByTagName("cmbStatementName")[k].firstChild.nodeValue;
				var txtStatementGroupNo = baseResponse.getElementsByTagName("txtStatementGroupNo")[k].firstChild.nodeValue;
				var txtStatementGroupDesc=baseResponse.getElementsByTagName("txtStatementGroupDesc")[k].firstChild.nodeValue;
				var StatementSubGroupNo=baseResponse.getElementsByTagName("StatementSubGroupNo")[k].firstChild.nodeValue;
				var StatementSubGroupDesc=baseResponse.getElementsByTagName("StatementSubGroupDesc")[k].firstChild.nodeValue;
				var view=baseResponse.getElementsByTagName("STATUS")[k].firstChild.nodeValue;
				var tbody = document.getElementById("tblList");
				var table = document.getElementById("Existing");
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;
				var td = document.createElement("TD");
				var anc = document.createElement("A");
				if (view == "C") {
					var priceSpan = document.createElement("span");
					priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
					priceSpan.appendChild(document.createTextNode("Cancel"));			
					td.appendChild(priceSpan);
				}else{
					var url = "javascript:viewDetails('" + seq + "','" + path + "')";
					anc.href = url;
					var edit = document.createTextNode("Edit");
					anc.appendChild(edit);
					td.appendChild(anc);
					var sch_id=document.createElement("TEXT");
		        	sch_id.type="hidden";
		        	sch_id.name="name"+seq;
		        	sch_id.id="id"+seq;
		        	sch_id.value="&statementNo="+statementNo+"&statementGroupNo="+txtStatementGroupNo+"&statementSubGroupNo="+StatementSubGroupNo;	       
		        	td.appendChild(sch_id);
				}
				
				mycurrent_row.appendChild(td);
	
				var cell1 = document.createElement("TD");
				var cmbStatementName = document.createTextNode(cmbStatementName);
				cell1.appendChild(cmbStatementName);
				mycurrent_row.appendChild(cell1);
	
				var cell3 = document.createElement("TD");
				var txtStatementGroupDesc = document.createTextNode(txtStatementGroupDesc);
				cell3.appendChild(txtStatementGroupDesc);
				mycurrent_row.appendChild(cell3);

				var cell2 = document.createElement("TD");
				var txtStatementGroupNo = document.createTextNode(StatementSubGroupNo);
				cell2.appendChild(txtStatementGroupNo);
				mycurrent_row.appendChild(cell2);
				
				var cell12 = document.createElement("TD");
				var groupNo = document.createTextNode(StatementSubGroupDesc);
				cell12.appendChild(groupNo);
				mycurrent_row.appendChild(cell12);
				var td5 = document.createElement("TD");
				if(view=="C"){
					var tdst = document.createTextNode("CANCEL");
				}else{
					var tdst = document.createTextNode("LIVE");
				}
				td5.appendChild(tdst);
				mycurrent_row.appendChild(td5);
	
				tbody.appendChild(mycurrent_row);
				seq++;
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

function secondview(xmlrequest)
{
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {
            //alert(xmlrequest.responseText);
			baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			//alert(baseResponse);
			changepagesize();
			
		}
		
		
	}
}

function add(path) {
    var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("statementGroupName").value;
	var txtStatementGroupDesc = document.getElementById("statementSubGroupDesc").value;
	
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Group_Master.cmbStatementName.focus();
	} else if (txtStatementGroupNo == "") {
		alert("Select Statement Group No in the Field");
		document.frmStatement_Group_Master.txtStatementGroupNo.focus();
	} else if (txtStatementGroupDesc == "") {
		alert("Enter Statement Sub Group Desc in the Field");
		document.frmStatement_Group_Master.txtStatementGroupDesc.focus();
	} else {

		var url = path + "/Statement_Sub_Group_Master?command=add&statementGroupNo="
				+ txtStatementGroupNo + "&statementSubGroupDesc=" + txtStatementGroupDesc + "&statementNo=" + cmbStatementName+"&path="+path;

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
	var path=baseResponse.getElementsByTagName("path")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully. Sub Group Id is "+baseResponse.getElementsByTagName("SUBGROUPID")[0].firstChild.nodeValue);
	} else if (flag == "Exist") {
		alert("The Given Data is Already Exist");
	} else {
		alert("Failed to Add values");
	}
	clearGrid();
	initialLoad(path);
}

function update(path) {
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("statementGroupName").value;
	var statementSubGroupNo = document.getElementById("statementSubGroupNo").value;
	var statementSubGroupDesc = document.getElementById("statementSubGroupDesc").value;
	
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.statementSubGroupMaster.cmbStatementName.focus();
	} else if (txtStatementGroupNo == "") {
		alert("Select Statement Group No in the Field");
		document.statementSubGroupMaster.statementGroupName.focus();
	} else if (statementSubGroupDesc == "") {
		alert("Enter Statement Group Desc in the Field");
		document.statementSubGroupMaster.statementSubGroupDesc.focus();
	} else {

		var url = path + "/Statement_Sub_Group_Master?command=update&statementGroupNo="
				+ txtStatementGroupNo + "&statementSubGroupNo=" + statementSubGroupNo+ "&statementNo="
				+ cmbStatementName+"&statementSubGroupDesc="+statementSubGroupDesc+"&path="+path;

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
	var path=baseResponse.getElementsByTagName("path")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated successfully. For Sub Group Id "+baseResponse.getElementsByTagName("SUBGROUPID")[0].firstChild.nodeValue);
	} else if (flag == "Exist") {
		alert("The Given Data is Already Exist");
	} else {
		alert("Failed to Add values");
	}
	clearGrid();
	initialLoad(path);
}

function deleteeee(path) {
	//alert(path);
	var cmbStatementName = document.getElementById("cmbStatementName").value;
	var txtStatementGroupNo = document.getElementById("statementGroupName").value;
	var statementSubGroupNo = document.getElementById("statementSubGroupNo").value;
	
	if (cmbStatementName == "") {
		alert("Select Statement Name in the Field");
		document.frmStatement_Group_Master.cmbStatementName.focus();
	} else if (txtStatementGroupNo == "") {
		alert("Enter Statement Group No in the Field");
		document.frmStatement_Group_Master.txtStatementGroupNo.focus();
	} else {
		var r = confirm("Are U Sure to Continue?");
		if (r == true) {
			var url = path + "/Statement_Sub_Group_Master?command=deleted&txtStatementGroupNo="
					+ txtStatementGroupNo + "&cmbStatementName=" + cmbStatementName
					+"&statementSubGroupNo="+statementSubGroupNo+"&path="+path;
			// alert(url);
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
		var ApportCode1 = baseResponse.getElementsByTagName("id1")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ApportCode+ApportCode1);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);
		alert("Records Deleted Successfully");
		clearGrid();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Unable to Delete");
	}
}

function clearAll() {
	document.getElementById("cmbStatementName").disabled = false;
	document.getElementById("statementGroupName").disabled = false;
	document.getElementById("cmbStatementName").selectedIndex=0;
	document.getElementById("statementGroupName").selectedIndex=0;
	document.getElementById("statementGroupName").length=1;
	document.getElementById("statementSubGroupNo").value="";
	document.getElementById("statementSubGroupDesc").value="";
	document.getElementById('cmbStatementName').disabled=false;
 	document.getElementById('statementGroupName').disabled=false;
	
	document.statementSubGroupMaster.onsubmit.disabled = false;
	document.statementSubGroupMaster.ondelete.disabled = true;
	document.statementSubGroupMaster.onupdate.disabled = true;
	var pat=document.getElementById('strpath').value;
	initialLoad(pat);
	//alert(pat);

}
function clearGrid(){
	clearAll();
	try{
		document.getElementById("tblList").innerHTML="";
	}catch(e){
		document.getElementById("tblList").innerText="";
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
			return false;
	}
}
function callGroupName(path){
	var statementNo=document.getElementById('cmbStatementName').value;
	var url = path + "/Statement_Sub_Group_Master?command=loadStatementName&statementNo="+statementNo;
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}
function loadStatementName(baseResponse){
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;			
	var selectdiv=document.getElementById('statementGroupName');
	var listOpt=document.createElement("option");
	selectdiv.length=0;
	selectdiv.appendChild(listOpt);
	listOpt.text="select";
	listOpt.value="";
	if (flag == "nodata"){
			alert("Sorry! Statement Group is Not Found for this Accounting unit ");
			selectdiv.selectedIndex=0;
			selectdiv.length=1;
	}else if(flag == "success"){
			var len=baseResponse.getElementsByTagName("STATEMENT_GROUP_NO").length;						
			for(var i=0; i<len; i++){
				listOpt=document.createElement("option");
				selectdiv.appendChild(listOpt);
				listOpt.text=baseResponse.getElementsByTagName("STATEMENT_GROUP_DESC")[i].firstChild.nodeValue;
				listOpt.value=baseResponse.getElementsByTagName("STATEMENT_GROUP_NO")[i].firstChild.nodeValue;
			}
	}
		
}
function viewDetails(id,path){
	 var jid=document.getElementById("id"+id).value;
	 var url = path + "/Statement_Sub_Group_Master?command=edit"+jid;	 
    var req=AjaxFunction();
     req.open("POST",url,true);        
     req.onreadystatechange=function()
     {
        editview(req);
     };  
     req.send(null);
}
function editview(req){
	 if(req.readyState==4){ 
        if(req.status==200){        	 
       	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
     	   	 editResponse(baseResponse);
        }
    } 
}
function editResponse(response){
	 var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	 	if(res=="success"){ 		
	 		document.getElementById('cmbStatementName').value=response.getElementsByTagName("STATEMENT_NO")[0].firstChild.nodeValue;	 		
	 		//var flag=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	 		var selectdiv=document.getElementById('statementGroupName');
			var listOpt=document.createElement("option");
			selectdiv.length=0;
			selectdiv.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";
			//var len=response.getElementsByTagName("STATEMENT_GROUP_DESC").length;
			
				
				var statenentname=response.getElementsByTagName("STATEMENT_GROUP_DESC")[0].firstChild.nodeValue;
				var statementno=response.getElementsByTagName("STATEMENT_GROUP_NO")[0].firstChild.nodeValue;
			    var se = document.getElementById("statementGroupName");
				var op = document.createElement("OPTION");
				op.value = statementno;
				var txt = document.createTextNode(statenentname);
				op.appendChild(txt);
				se.appendChild(op);
				document.getElementById("statementGroupName").value=statementno;
			//document.getElementById('statementGroupName').value=response.getElementsByTagName("STATEMENT_GROUP_NO")[0].firstChild.nodeValue;
			document.getElementById('statementSubGroupNo').value=response.getElementsByTagName("STATEMENT_SUB_GROUP_NO")[0].firstChild.nodeValue; 			
	 		document.getElementById('statementSubGroupDesc').value=response.getElementsByTagName("STATEMENT_SUB_GROUP_DEC")[0].firstChild.nodeValue;
	 	}else{
	 		alert("Process Failure");
	 	}
	 	document.getElementById('cmbStatementName').disabled=true;
	 	document.getElementById('statementGroupName').disabled=true;
	 	document.statementSubGroupMaster.onsubmit.disabled = true;
		document.statementSubGroupMaster.ondelete.disabled = false;
		document.statementSubGroupMaster.onupdate.disabled = false;
}
function exitfun() {
	window.close();
}
