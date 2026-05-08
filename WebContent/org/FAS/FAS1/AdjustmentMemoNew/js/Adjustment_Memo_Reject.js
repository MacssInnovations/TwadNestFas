
var seq=0;

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




function mani(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		//alert(command);

			if (command == "add") {
				getValue(baseResponse);
			}
		
			

}
}
}


function getValue(baseResponse)
{
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == "success") {	
		
		alert("Memo Advice No is Rejected Successfully");
		refreash();
		
	}
	
	else
	{
		alert("Fail To Reject");
	}
	
	
}
function add()
{
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtDate=document.getElementById("txtDate").value;
	var cmbAdviceNO=document.getElementById("cmbAdviceNO").value;
	var txtReject=document.getElementById("txtReject").value;
    var valid=txt_empty('txtDate|cmbAdviceNO|txtReject');
    if(valid)
    {
	var url = "../../../../../Adjustment_Memo_Reject?command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtDate="+txtDate+"&cmbAdviceNO="+cmbAdviceNO+"&txtReject="+txtReject;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		mani(xmlrequest);
		}

	xmlrequest.send(null);	
}
}

function refreash()
{
	
	document.getElementById("txtDate").value='';
	document.getElementById("cmbAdviceNO").value='s';
	document.getElementById("txtLetterNO").value='';
	document.getElementById("txtLetterDate").value='';
	document.getElementById("txtRemarks1").value='';
	document.getElementById("txtAmount").value='';
	document.getElementById("txtReject").value='';
	document.getElementById("txtAuthority").value='';
	document.getElementById("txtAuthorityaddress").value='';
}
function exitfun() {
	window.close();
}

function txt_empty(txt) 
{
	//alert(txt);
	var k=0;
	var s=txt.split('|');
	
//	 var tbody = document.getElementById("tblList");
//		var rowcount=tbody.rows.length;
//		//alert(rowcount+"************************************");
	for(var i=0;i<s.length;i++)
	{
		//alert("val:::"+s[i]);
	if(document.getElementById(s[i]).value == "") 
	{ 
		
		var a=s[i].split('txt');
		
	alert(a[1]+"   Should Not Be Blank");
	document.getElementById(s[i]).focus();
	return false;
	} 
	else if(document.getElementById(s[i]).value == "s")
	{

		var a=s[i].split('cmb');
		alert(a[1]+"   Should Not Be Blank");
		document.getElementById(s[i]).focus();
	   return false;
		
	}
	
	}
    return true;
}

