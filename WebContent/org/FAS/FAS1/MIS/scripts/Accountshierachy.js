/*
 * 
 * 
 * 
 * Developed By NandaKumar 27/Nov/2019
 * 
 * FAS DashBoard
 * 
 * 
 * */
var service;
var __pagination = 11;

function load() {
	var finYear = document.getElementById("txtfin_year").value;
	if (finYear != "") {
		document.getElementById("txtfin_year").value = "";
	}
	

}

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

function returnMajorHead(Command,finYear)
{
	document.getElementById("txtfin_year").value=finYear;
	 /*document.getElementById("Command").value=Command;*/
	doFunction(Command,finYear);
	}


function doFunction(Command, param) {

	if (Command == "load_Major_Heads") {

		var finYear = param.split("-");
		var finYearStart = finYear[0];
		var finYearEnd = finYear[1];

			var url = "../../../../../Accounts_Data.view?Command=load_Account_Head&from_year="
					+ finYearStart + "&to_year=" + finYearEnd;
			// alert(url)
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				if (this.readyState == 4) {
					if (this.status == 200) { // alert(req.responseText);
						var baseResponse = this.responseXML.getElementsByTagName("response")[0];
						var tagcommand = baseResponse.getElementsByTagName("command")[0];
						var Command = tagcommand.firstChild.nodeValue;

						if (Command == "load_Account_Head") {
							var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
							
							if (flag == "success") {
								document.getElementById("tbody").style.display = "";
								var url = "../../../../../Accounts_Hierachy.view?Command=load_Major_Heads&finYearStart="+finYearStart+"&finYearEnd="+finYearEnd;
								/* alert(url); */
								var req = getTransport();
								req.open("GET", url, true);
								req.onreadystatechange = function() {
									handleResponse(req);
								}
								req.send(null);

							} else if (flag == "failure") {
								var headCode = baseResponse.getElementsByTagName("account_head_code");

								var items_id = new Array();
								/* var Rec_No=baseResponse.getElementsByTagName("Rec_No"); */
								var headCode = baseResponse.getElementsByTagName("account_head_code");
								for (var k = 0; k < headCode.length; k++) {
									items_id[k] = baseResponse
											.getElementsByTagName("account_head_code")[k].firstChild.nodeValue;

								}
								alert("The following AccountHeadCodes not available in AnnualGroupingFile: "
										+ items_id);
								
								document.getElementById("tbody").style.display = "none";
								

								return false;
							}
						}

					}
				}
			}
			req.send(null);

		//}
		
		
		
		
		/*if (finYear.length != 0) {

			var url = "../../../../../Accounts_Hierachy.view?Command=load_Major_Heads&finYearStart="+finYearStart+"&finYearEnd="+finYearEnd;
			 alert(url); 
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				handleResponse(req);
			}
			req.send(null);

		}*/
	}
}

function handleResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "load_Major_Heads") {
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
			Majheadorder = baseResponse.getElementsByTagName("Majheadorder")[0].firstChild.nodeValue;
			Majhead = baseResponse.getElementsByTagName("Majhead")[0].firstChild.nodeValue;
			Debit = baseResponse.getElementsByTagName("Debit")[0].firstChild.nodeValue;
			Credit = baseResponse.getElementsByTagName("Credit")[0].firstChild.nodeValue;
			Net = baseResponse.getElementsByTagName("Net")[0].firstChild.nodeValue;
			Netdisp = baseResponse.getElementsByTagName("Netdisp")[0].firstChild.nodeValue;

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
	document.frmAccounts_hierachy_dashboard.cmbpage.selectedIndex = page - 1;
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
					+ parseFloat(service[i].getElementsByTagName("Debit")[0].firstChild.nodeValue);
			creditAmount = parseFloat(creditAmount)
					+ parseFloat(service[i].getElementsByTagName("Credit")[0].firstChild.nodeValue);
		}

		netAmount = debitAmount - creditAmount;
		var netAmountdisp;
		if (netAmount >= 0) {
			netAmountdisp = 'Dr';
		} else {
			netAmountdisp = 'Cr';
		}
	
		for (i = p; i < service.length && c < __pagination; i++) {
			c++;
			var items = new Array();

			items[0] = service[i].getElementsByTagName("Majheadorder")[0].firstChild.nodeValue;
			items[1] = service[i].getElementsByTagName("Majhead")[0].firstChild.nodeValue;
			items[2] = service[i].getElementsByTagName("Debit")[0].firstChild.nodeValue;
			items[3] = service[i].getElementsByTagName("Credit")[0].firstChild.nodeValue;
			items[4] = service[i].getElementsByTagName("Net")[0].firstChild.nodeValue;
			items[5] = service[i].getElementsByTagName("Netdisp")[0].firstChild.nodeValue;

			var tbody = document.getElementById("tbody");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.setAttribute("id", items[0]);

			for (j = 1; j < 5; j++) {
				cell2 = document.createElement("TD");

				if (j == 1) {
					cell2.setAttribute('align', 'center');
					{

						var anc = document.createElement("A");
						var url = "javascript:openReport('" +items[j - 1]+","+items[j]+"')";
						/*var url = "javascript:openReport('" + items[j - 1]+","+items[j]+","+ items[j+5]+"')";*/
						anc.href = url;
						var txtedit = document.createTextNode(items[j]);
						anc.appendChild(txtedit);
						cell2.appendChild(anc);
						mycurrent_row.appendChild(cell2);
					}
				} else if (j == 2 || j == 3 || j == 4) {
					cell2.setAttribute('align', 'right');
				}

				if (items[j] != "null" && j != 1 && j != 4) {
					var currentText = document.createTextNode(items[j]);
				} else if (items[j] != "null" && j == 4) {
					var currentText = document.createTextNode(items[j]
							+ items[j + 1]);
				} else {
					var currentText = document.createTextNode('');
				}
				cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);
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

function appendOpenerURI(res) {
	var uri = "";
	uri ="&finYear="+getValue("txtfin_year")+"&majorheadSortOrder="+res[0]+"&majorHead="+encodeURIComponent(res[1]);
	return uri;
}

function getValue(elementId) {
	return document.getElementById(elementId).value;
}

function openReport(rptType) {
	var res=rptType.split(",");
	if (res[0] == 1) {
		openFormInWindow("AccountshierachyMinorHeadWise.jsp?Command=load_Minor_Heads"
				+ appendOpenerURI(res), "Income_FORM");

	} else if (res[0] == 2) {
		openFormInWindow("AccountshierachyMinorHeadWise.jsp?Command=load_Minor_Heads"
				+ appendOpenerURI(res), "Expenditure_FORM");
	} else if (res[0] == 3) {
		openFormInWindow("AccountshierachyMinorHeadWise.jsp?Command=load_Minor_Heads"
				+ appendOpenerURI(res), "Assets_FORM");
	} else if (res[0] == 4) {
		openFormInWindow("AccountshierachyMinorHeadWise.jsp?Command=load_Minor_Heads"
				+ appendOpenerURI(res), "Liability_FORM");
	}

	else if (res[0] == 5) {
		openFormInWindow("AccountshierachyMinorHeadWise.jsp?Command=load_Minor_Heads"
				+ appendOpenerURI(res), "Others");
	}

}

function openFormInWindow(formUrl, winName) {
	var win;
	win = window.open(formUrl, "view_" + winName,
			"status=1,height=1600,width=1600,resizable=YES,scrollbars=yes");
	win.moveTo(150, 50);
	win.focus();
}

function btncancel() {

	self.close();
	sessionStorage.removeItem('finYear');
}
