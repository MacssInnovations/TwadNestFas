/*
 * 
 * 
 * 
 * Developed By Nandakumar 
 * 
 * 

 * */

var __pagination = 11;
var Command, finYear,minorsortOrder,minorHead, majorsortOrder,majorHead,subHead,subOrder,finYearStart, finYearEnd,service;

function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function load() {
	Command = document.getElementById("Command").value;
	finYearStart = document.getElementById("finyearStart").value;
	finYearEnd = document.getElementById("finyearEnd").value
	Command = document.getElementById("Command").value;
	minorsortOrder = document.getElementById("minOrder").value;
	minorHead = document.getElementById("minHead").value;
	majorsortOrder = document.getElementById("majOrder").value;
	majorHead = document.getElementById("majHead").value;
	subOrder = document.getElementById("subOrder").value;
	subHead = document.getElementById("subHead").value;
	finYear = document.getElementById("finYear").value;
	finYearStart = document.getElementById("finyearStart").value;
	finYearEnd = document.getElementById("finyearEnd").value;
	
	
	if (Command = "load_Acc_Heads")
	{
		
		/*var url = "../../../../../Accounts_Hierachy.view?Command=load_Acc_Heads&"+res[2]+"&"+res[3]+"&"+res[4]+"&"+res[5]+"&"+res[6]+"&"+res[7]+"&finYearStart=" +finYearStart + "&finYearEnd=" + finYearEnd;*/
		var url = "../../../../../Accounts_Hierachy.view?Command=load_Acc_Heads&minOrder="+minorsortOrder+"&minHead="+encodeURIComponent(minorHead)+"&majOrder="+majorsortOrder+"&majHead="+encodeURIComponent(majorHead)+"&subOrder="+subOrder+"&subHead="+encodeURIComponent(subHead)+"&finYearStart=" +finYearStart + "&finYearEnd=" + finYearEnd;
		/*alert(url);*/
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		}
		req.send(null);
	}

}


function handleResponse(request) {
	var x, parser, xmlDoc,tagcommand,baseResponse;
	if (request.readyState == 4) {
		if (request.status == 200) {
			if(request.responseXML!=null)
				{
				 baseResponse = request.responseXML.getElementsByTagName("response")[0];
				 tagcommand = baseResponse.getElementsByTagName("command")[0];
				}
			else if(request.responseXML==null)
			{
				document.getElementById("res").innerHTML=request.responseText;
				x =document.getElementById("res").innerHTML;
				//alert(x);
				parser = new DOMParser();
				xmlDoc = parser.parseFromString(x,"text/xml");
				 tagcommand = xmlDoc.getElementsByTagName("command")[0];
				}
			
			/*var baseResponse = request.responseText;*/
			
			
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "load_Acc_Heads" && request.responseXML!=null) {
				loadTable(baseResponse);
			}
			else if(Command == "load_Acc_Heads" && request.responseXML==null)
				{
				baseResponse=xmlDoc;
				loadTable(baseResponse);
				}

		}
	}
}


function loadTable(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "failure") {
		s = 0;
		var tbody = document.getElementById("tbody");
		try {
			tbody.innerHTML = "";
		} catch (e) {
			tbody.innerText = "";
		}

		var cell = document.getElementById("divcmbpage");
		cell.style.display = "none";
		var cell = document.getElementById("divpage");
		cell.style.display = "none";

		var cell = document.getElementById("divnext");
		cell.style.display = "none";
		var cell = document.getElementById("divpre");
		cell.style.display = "none";
		alert("No Record exists");
	} else {
		service = baseResponse.getElementsByTagName("leng");
		if (service) {
			
             Minheadorder = baseResponse.getElementsByTagName("minheadorder")[0].firstChild.nodeValue;
             Minhead = baseResponse.getElementsByTagName("minhead")[0].firstChild.nodeValue;
			Debit = baseResponse.getElementsByTagName("debit")[0].firstChild.nodeValue;
			Credit = baseResponse.getElementsByTagName("credit")[0].firstChild.nodeValue;
			Net = baseResponse.getElementsByTagName("net")[0].firstChild.nodeValue;
			Netdisp = baseResponse.getElementsByTagName("netdisp")[0].firstChild.nodeValue;

			var tbody = document.getElementById("tbody");
			var i = 0;
			totalblock = 0;
			if (service.length > 0) {
				totalblock = parseInt(service.length / __pagination);
				if (service.length % __pagination != 0) {
					totalblock = totalblock + 1;
				}
				var cmbpage = document.getElementById("cmbpage");
				try {
					cmbpage.innerHTML = "";
				} catch (e) {
					cmbpage.innerText = "";
				}
				for (i = 1; i <= totalblock; i++) {
					var option = document.createElement("OPTION");
					option.text = i;
					option.value = i;
					try {
						cmbpage.add(option);
					} catch (errorObject) {
						cmbpage.add(option, null);
					}
				}
			}
			loadPage(1);

		}
	}
}




function loadPage(page) {
	var i = 0;
	var c = 0;
	var p = __pagination * (page - 1);
	document.frmAccountshierachyAccHeadWise.cmbpage.selectedIndex = page - 1;
	var tbody = document.getElementById("tbody");
	try {
		tbody.innerHTML = "";
	} catch (e) {
		tbody.innerText = "";
	}

	if (service) {
		s = 0;
		var i = 0;
	
		var debitAmount = creditAmount = netAmount = 0.0;
		for (i = 0; i < service.length && c < __pagination; i++) 
		{
			debitAmount = parseFloat(debitAmount)
					+ parseFloat(service[i].getElementsByTagName("debit")[0].firstChild.nodeValue);
			creditAmount = parseFloat(creditAmount)
					+ parseFloat(service[i].getElementsByTagName("credit")[0].firstChild.nodeValue);
		}

		var netAmount1 = debitAmount - creditAmount;
		if(netAmount1<0)
			{
			netAmount=netAmount1*-1;
			}
		else
			{
			netAmount=netAmount1;
			}
		var netAmountdisp;
		if (netAmount1 >= 0) {
			netAmountdisp = 'Dr';
		} else {
			netAmountdisp = 'Cr';
		}
	
		for (i = p; i < service.length && c < __pagination; i++) {
			c++;
			var items = new Array();
			items[0] = service[i].getElementsByTagName("minheadorder")[0].firstChild.nodeValue;
			items[1] = service[i].getElementsByTagName("minhead")[0].firstChild.nodeValue;
			items[2] = service[i].getElementsByTagName("majheadorder")[0].firstChild.nodeValue;
			items[3] = service[i].getElementsByTagName("majhead")[0].firstChild.nodeValue;
			items[4] = service[i].getElementsByTagName("subheadorder")[0].firstChild.nodeValue;
			items[5] = service[i].getElementsByTagName("subhead")[0].firstChild.nodeValue;
			items[6] = service[i].getElementsByTagName("acchead")[0].firstChild.nodeValue;
			items[7] = service[i].getElementsByTagName("debit")[0].firstChild.nodeValue;
			items[8] = service[i].getElementsByTagName("credit")[0].firstChild.nodeValue;
			items[9] = service[i].getElementsByTagName("net")[0].firstChild.nodeValue;
			items[10] = service[i].getElementsByTagName("netdisp")[0].firstChild.nodeValue;
			

			var tbody = document.getElementById("tbody");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.setAttribute("id", items[4]);

			for (j = 6; j < 10; j++) {
				

				if (j == 6) {
					cell2 = document.createElement("TD");
					cell2.setAttribute('align', 'center');
					{
						
						var anc = document.createElement("A");
						var url = "javascript:openReport('" + items[j - j]+","+items[j-5]+","+items[j-4]+","
								+items[j-3]+","+items[j-2]+","+items[j-1]+","+items[j]+"')";
						anc.href = url;
						var txtedit = document.createTextNode(items[j]);
						anc.appendChild(txtedit);
						cell2.appendChild(anc);
						mycurrent_row.appendChild(cell2);
					}
				} else if (j==7 || j == 8 ) {
					cell2 = document.createElement("TD");
					cell2.setAttribute('align', 'right');
					var currentText = document.createTextNode(items[j]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);
				}

				else if (items[j] != "null" && j == 9) {
					
					cell2 = document.createElement("TD");
					cell2.setAttribute('align', 'right');
					var currentText = document.createTextNode(items[j]
							+ items[j + 1]);
					cell2.appendChild(currentText);
					mycurrent_row.appendChild(cell2);
				}
				
			}

			tbody.appendChild(mycurrent_row);

		}

	}

	mycurrent_row1 = document.createElement("TR");
	mycurrent_row1.setAttribute("id", "total");

	var cell0 = document.createElement("TD");
	cell0.align = 'CENTER';
	var descTot = document.createTextNode("Total Amount");
	cell0.appendChild(descTot);
	mycurrent_row1.appendChild(cell0);

	var cell1 = document.createElement("TD");
	cell1.align = 'right';
	var debitAmountTot = document.createTextNode(debitAmount);
	cell1.appendChild(debitAmountTot);
	mycurrent_row1.appendChild(cell1);

	var cell2 = document.createElement("TD");
	cell2.align = 'right';
	var crdeitAmountTot = document.createTextNode(creditAmount);
	cell2.appendChild(crdeitAmountTot);
	mycurrent_row1.appendChild(cell2);

	var cell3 = document.createElement("TD");
	cell3.align = 'right';
	var netAmountTot = document.createTextNode(netAmount + netAmountdisp);
	cell3.appendChild(netAmountTot);
	mycurrent_row1.appendChild(cell3);

	tbody.appendChild(mycurrent_row1);

	var cell = document.getElementById("divcmbpage");
	cell.style.display = "block";
	var cell = document.getElementById("divpage");
	cell.style.display = "block";
	netAmount = debitAmount - creditAmount;
	var netAmountdisp;
	if (netAmount < 0) {
		netAmountdisp = 'Cr';
	} else {
		netAmountdisp = 'Dr';
	}

	if (navigator.appName.indexOf("Microsoft") != -1)
		cell.innerText = ' / ' + totalblock;

	else
		cell.innerText = ' / ' + totalblock;

	cell.innerHTML = ' / ' + totalblock;

	if (page < totalblock) {
		var cell = document.getElementById("divnext");
		cell.style.display = "block";
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}
		var anc = document.createElement("A");
		var url = "javascript:loadPage(" + (page + 1) + ")";
		anc.href = url;
		// anc.setAttribute('style','text-decoratin:none');
		var txtedit = document.createTextNode("<<Next>>");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
	} else {
		var cell = document.getElementById("divnext");
		cell.style.display = "block";
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}

	}
	if (page > 1) {
		var cell = document.getElementById("divpre");
		cell.style.display = "block";
		// cell.innerText='';
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}
		var anc = document.createElement("A");
		var url = "javascript:loadPage(" + (page - 1) + ")";
		anc.href = url;
		var txtedit = document.createTextNode("<<Previous>>");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
	} else {
		var cell = document.getElementById("divpre");
		cell.style.display = "block";
		try {
			cell.innerHTML = "";
		} catch (e) {
			cell.innerText = "";
		}

	}
}

function appendOpenerURI(rptType) {
	var str=rptType.split(",");
	var minOrder=str[0];
	var minHead=str[1];
	var majOrder=str[2];
	var majHead=str[3];
	var subOrder=str[4];
	var subHead=str[5];
	
	var uri = "";
	uri = "&finYear=" + getValue("finYear") + "&minOrder="+minOrder+"&minHead="+encodeURIComponent(minHead)+"&majOrder=" + majOrder+"&majHead="+encodeURIComponent(majHead)+"&subOrder="+subOrder+"&subHead="+encodeURIComponent(subHead);
	
	return uri;
}

function getValue(elementId) {
	return document.getElementById(elementId).value;
}

function openReport(rptType) {
	openFormInWindow("AccountshierachyAccHeadWise.jsp?Command=load_Acc_Heads"
				+ appendOpenerURI(rptType),"Account_Head");
}

function openFormInWindow(formUrl, winName) {
	var win;
	win = window.open(formUrl, "view_" + winName,
			"status=1,height=1600,width=1600,resizable=YES,scrollbars=yes");
	win.moveTo(150, 50);
	win.focus();
}

function migration(id)
{
	var Command,minorsortOrder,minorHead,majorsortOrder,majorHead,finYear,finYearStart,finYearEnd;
	Command =id.trim();
	minorsortOrder = document.getElementById("minOrder").value.trim();
	minorHead = document.getElementById("minHead").value.trim();
	majorsortOrder = document.getElementById("majOrder").value.trim();
	majorHead = document.getElementById("majHead").value.trim();
	finYear = document.getElementById("finYear").value.trim();
	finYearStart = document.getElementById("finyearStart").value.trim();
	finYearEnd = document.getElementById("finyearEnd").value.trim();
	
	var result = false;
	if(Command=="load_Major_Heads"){
		opener.window.focus();
		self.close();
		opener.returnMajorHead(Command,finYear);
		result = true;
}
	if(Command=="load_Minor_Heads"){
		opener.window.focus();
		self.close();
		opener.returnMinorHead(Command,finYear,majorsortOrder,finYearStart,finYearEnd);
		result = true;
}
	
if(Command=="load_Sub_Heads"){
		opener.window.focus();
		self.close();
		opener.returnSubHead(Command, minorsortOrder,minorHead,majorsortOrder,majorHead,finYear,finYearStart,finYearEnd);
		result = true;
}


	return result;	
	
}
function btncancel() {

	self.close();
}       