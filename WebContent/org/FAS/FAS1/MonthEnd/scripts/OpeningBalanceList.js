
var seq=0;


function initialLoad(s1,s2) {
	
	//alert("initial value");
	//document.getElementById("MTCRegisterNo").length = 0;
	var url = "../../../../../OpeningBalance?command=gridvalue&accountUnitId="+s1+"&accountOfficeId="+s2;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);

}

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




function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			//alert("baseresponse"+baseResponse);
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		//alert(command);

			if (command == "gridvalue") {
				addRow(baseResponse);
			}

}
}
}

function addRow(baseResponse)
{
	
	//alert("enter");
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		

		refresh();

		
		var len= baseResponse.getElementsByTagName("code").length;
		//alert("length"+len);
		
        for(k=0;k<len;k++)
        	{
        	//var empid = baseResponse.getElementsByTagName("empID")[k].lastChild.nodeValue;
        	var empid = baseResponse.getElementsByTagName("code")[k].firstChild.nodeValue;
        	
    		var Designation = baseResponse.getElementsByTagName("debit")[k].firstChild.nodeValue;
    		var remarks = baseResponse.getElementsByTagName("credit")[k].firstChild.nodeValue;
    		var hidempid= baseResponse.getElementsByTagName("debitdate")[k].firstChild.nodeValue;
    		
    		var hiddesid= baseResponse.getElementsByTagName("creditdate")[k].firstChild.nodeValue;
    		var accname= baseResponse.getElementsByTagName("accname")[k].firstChild.nodeValue;
    		
    		var newword=empid+"-"+accname;
          // alert(newword);
		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");

		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url="javascript:loadValuesFromTable('"+seq+"')";
		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		
		var cell1 = document.createElement("TD");
		var tnodeentrydate = document.createTextNode(newword);
		cell1.appendChild(tnodeentrydate);
		mycurrent_row.appendChild(cell1);
	
		
		var cell2 = document.createElement("TD");
		var tnodereceivedby = document.createTextNode(Designation);
		cell2.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var tnodereceivedby = document.createTextNode(remarks);
		cell3.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell3);
		
		
		var cell4 = document.createElement("TD");
		var tnodereceivedby = document.createTextNode(hidempid);
		cell4.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var tnodereceivedby = document.createTextNode(hiddesid);
		cell5.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell5);
	
		tbody.appendChild(mycurrent_row);
		seq++;
	}
	}
	else if(flag=="exception")
	{
		
		alert("Have an Error In Load The Date");
	}
		
	else {
		alert("Failed to Add values");
	}
	
	

}


function loadValuesFromTable(idd)
{
            
             r=document.getElementById(idd);
             rcells=r.cells;
             var code;
            
             var creadit;
             var debit;
             var ddate;
             var cdate;
             var accname;
             code=rcells.item(1).firstChild.nodeValue;
            
             debit=rcells.item(2).firstChild.nodeValue;
             creadit=rcells.item(3).firstChild.nodeValue;
             ddate=rcells.item(4).firstChild.nodeValue;
             cdate=rcells.item(5).firstChild.nodeValue;
             
            
    Minimize();
    
    //alert(accHeadCode,accHeadDesc,bankid,operID)
    opener.ParentDrawing(code,debit,creadit,ddate,cdate);
    //return true;
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}


function refresh()
{

}