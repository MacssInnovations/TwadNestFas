
var seq=0;


function initialLoad() {
	
	
	var url="../../../../../PowerToWriteOff?command=gridvalue";
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		};

	xmlrequest.send(null);

}

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
	
	
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		

		refresh();

		
		var len= baseResponse.getElementsByTagName("level").length;
	//	alert("length"+len);
		
        for(k=0;k<len;k++)
        	{
        	
        	var level = baseResponse.getElementsByTagName("level")[k].firstChild.nodeValue;
        	
    		var type = baseResponse.getElementsByTagName("type")[k].firstChild.nodeValue;
    		var value = baseResponse.getElementsByTagName("value")[k].firstChild.nodeValue;
    		var remarks= baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
    		var status= baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
    		
    	

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");

		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;
		var cell = document.createElement("TD");
		if(status=="L"){
			var anc = document.createElement("A");
			var url="javascript:loadValuesFromTable('"+seq+"')";
			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
		}else{
			var priceSpan = document.createElement("span");
			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
			priceSpan.appendChild(document.createTextNode("Cancel"));			
			cell.appendChild(priceSpan);
		}		
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var tnoderegno = document.createTextNode(level);
		cell2.appendChild(tnoderegno);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var tnodeentrydate = document.createTextNode(type);
		cell3.appendChild(tnodeentrydate);
		mycurrent_row.appendChild(cell3);
		//--------------------------------------------------------		
		var cell4 = document.createElement("TD");
		var tnodereceivedby = document.createTextNode(value);
		cell4.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell4);
		var cell5 = document.createElement("TD");
		var tnodereceivedby = document.createTextNode(remarks);
		cell5.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell5);
		
		var cell6 = document.createElement("TD");
		if(status=="L"){
			var tnodereceivedby = document.createTextNode("LIVE");
		}else{
			var tnodereceivedby = document.createTextNode("CANCEL");
		}		
		cell6.appendChild(tnodereceivedby);
		mycurrent_row.appendChild(cell6);
		
		/*var cell5 = document.createElement("TD");
		var hiddendesid = document.createElement("input");
		hiddendesid.setAttribute("type","hidden");
		hiddendesid.setAttribute("value",hiddesid);
		hiddendesid.setAttribute("name","h2");
		cell5.appendChild(hiddendesid);
		mycurrent_row.appendChild(cell5);

		var cell6 = document.createElement("TD");
		var hidempid1 = document.createElement("input");
		hidempid1.setAttribute("type","hidden");
		hidempid1.setAttribute("value",hidempid);
		hidempid1.setAttribute("id","h1");
		cell6.appendChild(hidempid1);
		mycurrent_row.appendChild(cell6);
	*/
		tbody.appendChild(mycurrent_row);
		seq++;
	}
	}else {
		alert("Failed to Add values");
	}
	
	

}


function loadValuesFromTable(idd){            
             r=document.getElementById(idd);
             rcells=r.cells;
             var level;
             var type;
             var value;
             var remark;           
             level=rcells.item(1).firstChild.nodeValue;
             type=rcells.item(2).firstChild.nodeValue;
             value=rcells.item(3).firstChild.nodeValue;
             remark=rcells.item(4).firstChild.nodeValue;             
            // alert(hname);
            //alert(desid);
           // alert(empid);
            //alert(remaks);
            //alert(rcells.item(4).firstChild.nodeName);
            //alert(rcells.item(5).firstChild.nodeValue);
            
    Minimize();
    
    //alert(accHeadCode,accHeadDesc,bankid,operID)
    opener.ParentDrawing(level,type,value,remark);
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