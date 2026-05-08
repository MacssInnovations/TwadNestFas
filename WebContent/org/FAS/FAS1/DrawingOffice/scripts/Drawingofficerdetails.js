
var seq=0;

function AjaxFunction() {
	
	
	var xmlrequest = false;
	try {
		//xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
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

/*function initialLoad() {
	
	//alert("enter...initial load.");
	//document.getElementById("MTCRegisterNo").length = 0;
	var url = "../../../../../DrawingOfficerDetails?command=getv";
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		};

	xmlrequest.send(null);

}*/

function callemp(path)
{
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
		var url = path+ "/Bills_Token_Register_with_SP?command=getempname_off&txtEmpID_mas="+ txtEmpID_mas+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

}

function getempname_re(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") 
	{
		
		
		
		var len = baseResponse.getElementsByTagName("empid").length;
		var se = document.getElementById("cmbMas_SL_Code");
		se.length=0;
		for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;

			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);
		}
		designload();
		
	
		
	}
	else
	{
		alert("Enter Relevant EmployeeId For This Office");
		document.getElementById("txtEmpID_mas").value="";
		document.getElementById("cmbMas_SL_Code").value="";
	}
}

function designload() {
	
	//alert("enter...designload.");
	var AccountUnitId=document.getElementById("cmbAcc_UnitCode").value;
	var AccountUnitOfficeId=document.getElementById("cmbOffice_code").value;
	var employeeId=document.getElementById("txtEmpID_mas").value;
	
	var url = "../../../../../DrawingOfficerDetails?command=designload&accountUnitId="+AccountUnitId+"&accountOfficeId="+AccountUnitOfficeId+"&employeeId="+employeeId;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		};

	xmlrequest.send(null);

}

/*function list_chk()
{
	 var winemp;
	alert('test');
        var unit_id=document.getElementById("cmbAcc_UnitCode").value;
        var office_id=document.getElementById("cmbOffice_code").value;
        winemp= window.open("DrawingList.jsp?unit_id="+unit_id+"&office_id="+office_id,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
      
}*/
function Load_DrawOff()
{
	
	 var winemp;
		
	        var unit_id=document.getElementById("cmbAcc_UnitCode").value;
	        var office_id=document.getElementById("cmbOffice_code").value;
	        winemp= window.open("DrawingList.jsp?unit_id="+unit_id+"&office_id="+office_id,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
	        winemp.moveTo(250,250);  
	        winemp.focus();
}

function Cancel(){
	var AccountUnitId=document.getElementById("cmbAcc_UnitCode").value;
	var AccountUnitOfficeId=document.getElementById("cmbOffice_code").value;
	var desid=document.getElementById("cboOfficerDesignation").value;
	var employeeId=document.getElementById("txtEmpID_mas").value;
	
	var url="../../../../../DrawingOfficerDetails?command=cancel&accountUnitId="+AccountUnitId+"&accountOfficeId="+AccountUnitOfficeId+"&employeeId="+employeeId+"&DESIGNATION_ID="+desid;
	//alert("URL===>"+url)
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	
}

function delete1()
{
	var AccountUnitId=document.getElementById("cmbAcc_UnitCode").value;
	var AccountUnitOfficeId=document.getElementById("cmbOffice_code").value;
	var desid=document.getElementById("cboOfficerDesignation").value;
	var employeeId=document.getElementById("txtEmpID_mas").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		
		var url="../../../../../DrawingOfficerDetails?command=deleted&accountUnitId="+AccountUnitId+"&accountOfficeId="+AccountUnitOfficeId+"&employeeId="+employeeId+"&DesId="+desid;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function comboload()
{
	
	var url="../../../../../DrawingOfficerDetails?command=getload";
	//alert(url);
	var xmlrequest=AjaxFunction();
	
	xmlrequest.open("POST",url,true);
	
	xmlrequest.onreadystatechange=function(){
	
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}
function add()
{

	var AccountUnitId=document.getElementById("cmbAcc_UnitCode").value;
	var AccountUnitOfficeId=document.getElementById("cmbOffice_code").value;
	var desid=document.getElementById("cboOfficerDesignation").value;
	var employeeId=document.getElementById("txtEmpID_mas").value;
	var remarks=document.getElementById("mtxtRemarks").value;
	
	 var valid=txt_empty('cboOfficerDesignation|txtEmpID_mas|mtxtRemarks');
				
				if(valid)
				{
	
	var url="../../../../../DrawingOfficerDetails?command=add&accountUnitId="+AccountUnitId+"&accountOfficeId="+AccountUnitOfficeId+"&employeeId="+employeeId+"&remarks="+remarks+"&DesId="+desid;
	var xmlrequest=AjaxFunction();
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange=function(){
		
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
				}
}
function getname()
{
	
	var employeeId=document.FAS_DrawingOfficerDetails.cboCurrentOfficer.value;
	
	var url="../../../../../DrawingOfficerDetails?command=getname&employeeId="+employeeId;
	var xmlrequest=AjaxFunction();
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange=function(){
		
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	
}
function update()
{
	var AccountUnitId=document.getElementById("cmbAcc_UnitCode").value;
	var AccountUnitOfficeId=document.getElementById("cmbOffice_code").value;
	var desid=document.getElementById("cboOfficerDesignation").value;
	var employeeId=document.getElementById("txtEmpID_mas").value;
	var remarks=document.getElementById("mtxtRemarks").value;
	
	 var valid=txt_empty('cboOfficerDesignation|txtEmpID_mas|mtxtRemarks');
		
		if(valid)
		{
		
	var url="../../../../../DrawingOfficerDetails?command=update&accountUnitId="+AccountUnitId+"&accountOfficeId="+AccountUnitOfficeId+"&employeeId="+employeeId+"&remarks="+remarks+"&DesId="+desid;
	var xmlrequest=AjaxFunction();
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange=function(){
		
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
		}
	
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		

			if (command == "add") {
				addRow(baseResponse);
			} else if (command == "deleted") {
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} /*else if (command == "getv") {
				loadValue(baseResponse);
			}*/
			else if(command=="cancel"){
				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

           	 if(flag=="success"){
           		 alert("Record cancel successfully");
           	 }else{
           		 alert("Cancel fail. Record Already in Table");
           	 } 
			}
			else if(command=="getload"){
				
				loadComboValue(baseResponse);
			}
			else if(command=="getname")
			{
				getNameValue(baseResponse);
		}
			else if (command == "designload") {
				designloadloadValue(baseResponse);
			}
			else if (command == "getempname_off") {
				 //alert("manipulate");
				getempname_re(baseResponse);
			}

	}

}
}
function designloadloadValue(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		var len = baseResponse.getElementsByTagName("DesgnationId").length;
		var se = document.getElementById("cboOfficerDesignation");
		se.length=0;
		for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("DesgnationId")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("DesgnationName")[i].firstChild.nodeValue;

			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else {
		alert("Fail to Load");
	}
}
/*function loadValue(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		var len = baseResponse.getElementsByTagName("DesgnationId").length;
		
		var cc=baseResponse.getElementsByTagName("countid")[0].firstChild.nodeValue;
		var cc1=parseInt(cc);
		//alert("cc1 "+cc1);
		var se = document.getElementById("cboOfficerDesignation");
		se.length=0;
		for ( var i = 0; i < cc1; i++) {
			var com1id = baseResponse.getElementsByTagName("DesgnationId")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("DesgnationName")[i].firstChild.nodeValue;

			
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else {
		alert("Fail to Load");
	}
}*/
function loadComboValue(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		var len = baseResponse.getElementsByTagName("id").length;
		
		for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("id")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("name")[i].firstChild.nodeValue;

			var se = document.getElementById("cboCurrentOfficer");
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else {
		alert("Fail to Load");
	}
}
function getNameValue(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
		
		document.FAS_DrawingOfficerDetails.txtOfficerName.value=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue
	}
	else
	{
		alert("Please Select EmployeeID");
	}
}

function ParentDrawing(r1,r2,r3,r4,r5)
{
	//alert("parent"+r1);
		document.FAS_DrawingOfficerDetails.mtxtRemarks.value=r3;
		document.FAS_DrawingOfficerDetails.cmbMas_SL_Code.Value=r2;
		document.getElementById("cmbMas_SL_Code").options[document.getElementById("cmbMas_SL_Code").selectedIndex].text=r5;
		document.getElementById("txtEmpID_mas").value=r2;
		document.getElementById("cboOfficerDesignation").value=r1;
	   // document.FAS_DrawingOfficerDetails.cboOfficerDesignation.Value=r1;
		document.getElementById("cboOfficerDesignation").options[document.getElementById("cboOfficerDesignation").selectedIndex].text=r4;
	
	
	//document.FAS_DrawingOfficerDetails.txtEmpID_mas.value=r5;
	document.FAS_DrawingOfficerDetails.onsubmit.disabled = true;
	document.FAS_DrawingOfficerDetails.ondelete.disabled = false;
	document.FAS_DrawingOfficerDetails.onupdate.disabled = false;
	document.FAS_DrawingOfficerDetails.oncancel.disabled = false;

	
	}


function updateRow(baseResponse) {
	/*var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
		var items = new Array();
		items[0] = baseResponse.getElementsByTagName("cmbAcc_UnitCode")[0].firstChild.nodeValue;
		items[1] = baseResponse.getElementsByTagName("cmbOffice_code")[0].firstChild.nodeValue;
		items[2] = baseResponse.getElementsByTagName("txtOfficerDesignation")[0].firstChild.nodeValue;
		items[3] = baseResponse.getElementsByTagName("cboCurrentOfficer")[0].firstChild.nodeValue;
		items[4] = baseResponse.getElementsByTagName("txtOfficerName")[0].firstChild.nodeValue;
		items[5] = baseResponse.getElementsByTagName("mtxtRemarks")[0].firstChild.nodeValue;
		

		var r = document.getElementById(items[0]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
		rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];
		rcells.item(6).firstChild.nodeValue = items[5];
		

	}*/ 
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	alert("Update successfully");
	refresh();
	}
	
	else {
		alert("Failed to update values");
	}
}
function deleteRow(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	alert("delete successfully");
	refresh();
	}
}
function addRow(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
		refresh();
	}
	else
	{
		alert("Already Exists...... Check PrimaryKey");
	}
}
function refresh()
{
	document.getElementById("cmbMas_SL_Code").value='s';
	document.getElementById("cboOfficerDesignation").value='s';
    document.getElementById("txtEmpID_mas").value='';
    document.getElementById("mtxtRemarks").value='';	
   
}
function exitfun() {
	//alert("exit ");
	window.close();
}

function txt_empty(txt) 
{
	var k=0;
	var s=txt.split('|');

	for(var i=0;i<s.length;i++)
	{
	if(document.getElementById(s[i]).value == "") 
	{ 
		
	var a=s[i].split('txt');	
	alert(a[1]+"   Should Not Be Blank");
	document.getElementById(s[i]).focus();
	return false;
	} 
	else if(document.getElementById(s[i]).value == "s")
	{

		var a=s[i].split('cbo');
		alert(a[1]+"   Should Not Be Blank");
		document.getElementById(s[i]).focus();
	    return false;	
	}
	
	}
    return true;
}