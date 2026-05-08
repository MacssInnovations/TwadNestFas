
var seq=0;


function initialLoad(s1,s2) {
	
	//document.getElementById("MTCRegisterNo").length = 0;
	var url = "../../../../../DrawingOfficerDetails?command=gridvalue&accountUnitId="+s1+"&accountOfficeId="+s2;
	
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
			} else if (command == "deleted") {
				//deleteRow(baseResponse);
			

	}

}
}
}

function addRow(baseResponse)
{
	
	
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		

		refresh();

		
		var len= baseResponse.getElementsByTagName("empID").length;
	//	alert("length"+len);
		
        for(k=0;k<len;k++)
        	{
        	//var empid1 = baseResponse.getElementsByTagName("empID")[k].lastChild.nodeValue;
        	var empid = baseResponse.getElementsByTagName("empName")[k].firstChild.nodeValue;
        	
    		var Designation = baseResponse.getElementsByTagName("DesignationName")[k].firstChild.nodeValue;
    		var remarks = baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
    		var hidempid= baseResponse.getElementsByTagName("empID")[k].firstChild.nodeValue;
    		
    		var hiddesid= baseResponse.getElementsByTagName("desID")[k].firstChild.nodeValue;
    		var status= baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
    		
    		

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");

		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url="javascript:loadValuesFromTable('"+seq+"')";
		anc.href = url;
		
		if (status == "C") {
			//var tid = document.createTextNode("Cancel");			
			var priceSpan = document.createElement("span");
			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
			priceSpan.appendChild(document.createTextNode("Cancel"));			
			cell.appendChild(priceSpan);
			mycurrent_row.appendChild(cell);
		}
		else
			{
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
			}
		var cell2 = document.createElement("TD");
		var tnoderegno = document.createTextNode(hiddesid+"-"+Designation);
		cell2.appendChild(tnoderegno);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var tnodeentrydate = document.createTextNode(hidempid+"-"+empid);
		cell3.appendChild(tnodeentrydate);
		mycurrent_row.appendChild(cell3);
		//--------------------------------------------------------		
		var cell4 = document.createElement("TD");
		var tnodereceivedby = document.createTextNode(remarks);
		cell4.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell4);
		
		var cell5 = document.createElement("TD");
		var status = document.createTextNode(status);
		cell5.appendChild(status);
		mycurrent_row.appendChild(cell5);
		
		
		
		tbody.appendChild(mycurrent_row);
		seq++;
	}
	}else {
		alert("Failed to Add values");
	}
	
	

}


function loadValuesFromTable(idd)
{
            
             r=document.getElementById(idd);
             rcells=r.cells;
             var desid;
             var empid;
             var remaks;
             var desname;
             var empname;
             var des=rcells.item(1).firstChild.nodeValue.split("-");
             desid=des[0];
             desname=des[1];
             var emp=rcells.item(2).firstChild.nodeValue.split("-");
             empid=emp[0];
             empname=emp[1];
             remaks=rcells.item(3).firstChild.nodeValue;
          //alert(desid);   
          
    Minimize();
    
    //alert(accHeadCode,accHeadDesc,bankid,operID)
    opener.ParentDrawing(desid,empid,remaks,desname,empname);
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