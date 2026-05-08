
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



function list()
{
	
        var unit_id=document.getElementById("cmbAcc_UnitCode").value;
        var office_id=document.getElementById("cmbOffice_code").value;
        winemp= window.open("OpeningBalanceList.jsp?unit_id="+unit_id+"&office_id="+office_id,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
}

function delete1()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtFinanYr=document.getElementById("txtFinanYr").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert(txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert(txtCB_Month);
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		
		var url="../../../../../OpeningBalance?command=deleted&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+
		"&txtCB_Year="+txtCB_Year;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function add()
{

	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtFinanYr=document.getElementById("txtFinanYr").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert(txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert(txtCB_Month);
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var txtDebit=document.getElementById("txtDebit").value;
	var txtCredit=document.getElementById("txtCredit").value;
	var txtDrLUpdate=document.getElementById("txtDrLUpdate").value;
	var txtCrLUpdate=document.getElementById("txtCrLUpdate").value;
	
	 var valid=txt_empty('txtAcc_HeadCode|txtDebit|txtCredit|txtDrLUpdate|txtCrLUpdate');
				
				if(valid)
				{
	
	var url="../../../../../OpeningBalance?command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinanYr="+txtFinanYr+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+
	"&txtDebit="+txtDebit+"&txtCredit="+txtCredit+"&txtDrLUpdate="+txtDrLUpdate+"&txtCrLUpdate="+txtCrLUpdate+"&txtCB_Year="+txtCB_Year;
	var xmlrequest=AjaxFunction();
	xmlrequest.open("POST",url,true);
	xmlrequest.onreadystatechange=function(){
		
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
				}
}

function update()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtFinanYr=document.getElementById("txtFinanYr").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert(txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert(txtCB_Month);
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var txtDebit=document.getElementById("txtDebit").value;
	var txtCredit=document.getElementById("txtCredit").value;
	var txtDrLUpdate=document.getElementById("txtDrLUpdate").value;
	var txtCrLUpdate=document.getElementById("txtCrLUpdate").value;
	
	 var valid=txt_empty('txtAcc_HeadCode|txtDebit|txtCredit|txtDrLUpdate|txtCrLUpdate');
				
				if(valid)
				{
	
	var url="../../../../../OpeningBalance?command=update&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtFinanYr="+txtFinanYr+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+
	"&txtDebit="+txtDebit+"&txtCredit="+txtCredit+"&txtDrLUpdate="+txtDrLUpdate+"&txtCrLUpdate="+txtCrLUpdate+"&txtCB_Year="+txtCB_Year;
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
			}
			else if(command=="verify")
			{
				VerifyResult(baseResponse);
			}

	}

}
}
function VerifyResult(baseResponse)
{
	
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		//alert("Record Inserted Into Database successfully.");
		//refresh();
	}
	else if(flag=="exists")
	{
		alert("Already Exists...... Check Account_Head_Code");
		
		refresh();
		document.getElementById("txtAcc_HeadCode").focus();
	}
	
	
	
	
}
function checkExists()
{
	//alert("enter");
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	//alert(txtCB_Year);
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	//alert(txtCB_Month);
	if(txtAcc_HeadCode=='')
	{
		alert("AccountHeadCode Should Not Be Blank");
		document.getElementById("txtAcc_HeadCode").focus();
		return false;
		
	}
	

var url="../../../../../OpeningBalance?command=verify&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Month="+txtCB_Month+"&txtAcc_HeadCode="+txtAcc_HeadCode+"&txtCB_Year="+txtCB_Year;
//alert(url);
var xmlrequest=AjaxFunction();
xmlrequest.open("POST",url,true);
xmlrequest.onreadystatechange=function(){

manipulate(xmlrequest);
}
xmlrequest.send(null);
		}
	

function ParentDrawing(code,debit,creadit,ddate,cdate)
{
	//alert("parent");
	var acc=code.split("-");
	//alert(acc);
	document.getElementById("txtAcc_HeadCode").value=acc[0];
	document.getElementById("txtAcc_HeadDesc").value=acc[1];
	document.getElementById("txtDebit").value=debit;
	document.getElementById("txtCredit").value=creadit;
	document.getElementById("txtDrLUpdate").value=ddate;
    document.getElementById("txtCrLUpdate").value=cdate;
	document.OpeningBalForm.onsubmit.disabled = true;
	document.OpeningBalForm.ondelete.disabled = false;
    document.OpeningBalForm.onupdate.disabled = false;

	
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
	alert("Delete successfully");
	refresh();
	}
}
function addRow(baseResponse) {
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
		refresh();
	}
	else if(flag=="exists")
	{
		alert("Already Exists...... Check PrimaryKey");
		refresh();
	}
	else
	{
		alert("Fail To Add");
		refresh();
	}
}
function refresh()
{
	document.getElementById("txtDrLUpdate").value='';
	document.getElementById("txtCrLUpdate").value='';
    document.getElementById("txtDebit").value='';
    document.getElementById("txtCredit").value='';	
    document.getElementById("txtAcc_HeadCode").value='';
    document.getElementById("txtAcc_HeadDesc").value='';	
}
function exitfun() {
	alert("Exit ");
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
	
	
	}
    return true;
}
