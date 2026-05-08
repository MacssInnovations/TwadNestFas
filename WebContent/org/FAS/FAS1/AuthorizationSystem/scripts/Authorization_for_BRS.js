var seq = 0;
var seqNT = 0;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////

/**
 *  Browser Type Detection 
 */

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

function brs_year(pat)
{
	//alert(pat);
	 var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		
		 var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
         if(pat==1)
         {
        	 var url = "../../../../../Authorization_for_BRS.kv?Command=ckyear&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode
					+ "&cmbOffice_code="
					+ cmbOffice_code
					 + "&cmbBankAccNo=" + cmbBankAccNo;
					// alert(url);
					
						var req = getTransport();
						req.open("GET", url, true);
						req.onreadystatechange = function() {
							if (req.readyState == 4) {
								if (req.status == 200) {
			
									var baseResponse = req.responseXML.getElementsByTagName("response")[0];
									var tagcommand = baseResponse.getElementsByTagName("command")[0];
									var Command = tagcommand.firstChild.nodeValue;
									var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

								    if(flag=="BRSfreezed")
								    {
									    var db_year=baseResponse.getElementsByTagName("year")[0].firstChild.nodeValue;
									    var db_month=baseResponse.getElementsByTagName("month")[0].firstChild.nodeValue;
									    
									    var txtFrom_date = document.getElementById("txtFrom_date").value.split("/");
										var textbox_mn=txtFrom_date[1];
										var textbox_yr=txtFrom_date[2];
										if(textbox_yr==db_year){
											//alert("db_month::"+db_month);
											//alert("textbox_mn:^^^^:"+);
												if(parseInt(textbox_mn)<=db_month)
												{
													document.getElementById("txtFrom_date").value="";
													 document.getElementById("butSub").disabled=true;//submit button
													    document.getElementById("ByMonth").disabled=true;//go button
													 alert("BRS Monthly Closure had already freezed ............ ");
												}
										}
										else if(textbox_yr<db_year)
										{
											document.getElementById("txtFrom_date").value="";
											 document.getElementById("butSub").disabled=true;//submit button
											    document.getElementById("ByMonth").disabled=true;//go button
											 alert("Records Can't be Loaded, Because BRS Monthly Closure had already freezed ............ ");
										}								
						   
								    }
								    else
								    {
								    	document.getElementById("butSub").disabled=false;//submit
								    	document.getElementById("ByMonth").disabled=false;//go button
								    }
								
							}
						}
					}
					req.send(null);
         }
			else
			{
				var fromyear=document.getElementById("txtFrom_date").value;
				if(fromyear=="")
				{
					alert("Enter From Date");
					return false;
				}
				
	        	 var url = "../../../../../Authorization_for_BRS.kv?Command=ckyear&cmbAcc_UnitCode="
						+ cmbAcc_UnitCode
						+ "&cmbOffice_code="
						+ cmbOffice_code
						 + "&cmbBankAccNo=" + cmbBankAccNo;
						// alert(url);
						
							var req = getTransport();
							req.open("GET", url, true);
							req.onreadystatechange = function() {
								if (req.readyState == 4) {
									if (req.status == 200) {
				
										var baseResponse = req.responseXML.getElementsByTagName("response")[0];
										var tagcommand = baseResponse.getElementsByTagName("command")[0];
										var Command = tagcommand.firstChild.nodeValue;
										var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

									    if(flag=="BRSfreezed")
									    {
										    var db_year=baseResponse.getElementsByTagName("year")[0].firstChild.nodeValue;
										    var db_month=baseResponse.getElementsByTagName("month")[0].firstChild.nodeValue;
										    
										    var txtTo_date = document.getElementById("txtTo_date").value.split("/");
											var textbox_mn=txtTo_date[1];
											var textbox_yr=txtTo_date[2];
											if(textbox_yr==db_year){
												//alert("db_month::"+db_month);
												//alert("textbox_mn:^^^^:"+);
													if(parseInt(textbox_mn)<=db_month)
													{
														document.getElementById("txtTo_date").value="";
														 document.getElementById("butSub").disabled=true;//submit button
														    document.getElementById("ByMonth").disabled=true;//go button
														 alert("BRS Monthly Closure had already freezed ............ ");
													}
											}
											else if(textbox_yr<db_year)
											{
												document.getElementById("txtTo_date").value="";
												 document.getElementById("butSub").disabled=true;//submit button
												    document.getElementById("ByMonth").disabled=true;//go button
												 alert("Records Can't be Loaded, Because BRS Monthly Closure had already freezed ............ ");
											}								
							   
									    }
									    else
									    {
									    	document.getElementById("butSub").disabled=false;//submit
									    	document.getElementById("ByMonth").disabled=false;//go button
									    }
									
								}
							}
						}
						req.send(null);
			}
}

function brsMonth()
{

                var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
                var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
            	var TransType;
        		if (document.frmAuthorizationBRS.TransType[0].checked == true)
        			TransType = 'T';
        		else if (document.frmAuthorizationBRS.TransType[1].checked == true)
        			TransType = 'NT';

                var url = "../../../../../Authorization_for_BRS.kv?Command=ckMonth&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&txtCB_Year="
				+ txtCB_Year
				+ "&txtCB_Month="
				+ txtCB_Month + "&cmbBankAccNo=" + cmbBankAccNo+"&TransType="+TransType;
		// alert(url);
		
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		}
		req.send(null);
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function doFunction(Command) {
	if (Command == "Load_TransDetails") {
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;

		var TransType;
		if (document.frmAuthorizationBRS.TransType[0].checked == true)
			TransType = 'T';
		else if (document.frmAuthorizationBRS.TransType[1].checked == true)
			TransType = 'NT';

		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;

		var url = "../../../../../Authorization_for_BRS.kv?Command=Load_TransDetails&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&txtCB_Year="
				+ txtCB_Year
				+ "&txtCB_Month="
				+ txtCB_Month
				+ "&TransType=" + TransType + "&cmbBankAccNo=" + cmbBankAccNo;
		// alert(url);
		// alert(TransType);

		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		}
		req.send(null);
	}
        else if (Command == "Load_TransDetails_date") 
        {
        var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
               var txtFrom_date = document.getElementById("txtFrom_date").value;
               var txtTo_date = document.getElementById("txtTo_date").value;
                if(txtFrom_date=="")
                {
                alert("Enter From Date");
                 return false;
                }
                else if(txtTo_date=="")
                {
                alert("Enter To Date");
                return false;
                }
		var TransType;
		if (document.frmAuthorizationBRS.TransType[0].checked == true)
			TransType = 'T';
		else if (document.frmAuthorizationBRS.TransType[1].checked == true)
			TransType = 'NT';

		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;

		var url = "../../../../../Authorization_for_BRS.kv?Command=Load_TransDetails_date&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&txtFrom_date="
				+ txtFrom_date
				+ "&txtTo_date="
				+ txtTo_date
				+ "&TransType=" + TransType + "&cmbBankAccNo=" + cmbBankAccNo;
		// alert(url);
		// alert(TransType);

		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			handleResponse(req);
		}
		req.send(null);
        }
}

//-----------------------------------------------------------------------------------------------------
function handleResponse(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagcommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagcommand.firstChild.nodeValue;

			if (Command == "Load_TransDetails") {
				Load_TransDetails(baseResponse);
			}
            else if (Command == "Load_TransDetails_date") {
            	Load_TransDetails(baseResponse);
            }
             else if (Command == "ckMonth") {
            	 ckMonth_res(baseResponse);
             }
             else if (Command == "ckyear") {
            	 ckyear_res(baseResponse);
 			}
			
		}
	}
}


function ckyear_res(baseResponse) 
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="BRSfreezed")
    {
	    var db_year=baseResponse.getElementsByTagName("year")[0].firstChild.nodeValue;
	    var db_month=baseResponse.getElementsByTagName("month")[0].firstChild.nodeValue;
	    
	    var txtFrom_date = document.getElementById("txtFrom_date").value.split("/");
		var textbox_mn=txtFrom_date[1];
		var textbox_yr=txtFrom_date[2];
		if(textbox_yr==db_year){
				if(textbox_mn<=db_month)
				{
					document.getElementById("txtFrom_date").value="";
					 document.getElementById("butSub").disabled=true;//submit button
					    document.getElementById("ByMonth").disabled=true;//go button
					 alert("BRS Monthly Closure had already freezed ............ ");
				}
		}
		else if(textbox_yr<db_year)
		{
			document.getElementById("txtFrom_date").value="";
			 document.getElementById("butSub").disabled=true;//submit button
			    document.getElementById("ByMonth").disabled=true;//go button
			 alert("Records Can't be Loaded, Because BRS Monthly Closure had already freezed ............ ");
		}
	   
    }
    else
    {
    	document.getElementById("butSub").disabled=false;//submit
    	document.getElementById("ByMonth").disabled=false;//go button
    }
}

function ckMonth_res(baseResponse) 
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
//dhana
    if(flag=="BRSfreezed")
    {
    document.getElementById("txtCB_Year").value="";
    document.getElementById("txtCB_Month").value="0";
    document.getElementById("cmbBankAccNo").value="";
    
    document.getElementById("butSub").disabled=true;//submit button
    document.getElementById("List").disabled=true;//go button
    var tbody = document.getElementById("grid_body_TWAD");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	
	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var t1 = 0;
	for (t1 = tbody1.rows.length - 1; t1 >= 0; t1--) {
		tbody1.deleteRow(0);
	}
    alert("Records Can't be Loaded, Because BRS Monthly Closure had already freezed ............ ");
    
    }
    else
    {
    	document.getElementById("butSub").disabled=false;//submit
    	document.getElementById("List").disabled=false;//go button
    }
}

function Load_TransDetails(baseResponse) {	
//	var freeze = baseResponse.getElementsByTagName("freeze")[0].firstChild.nodeValue;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	
	//alert("flag-->>"+flag);
	if (flag == "success") {
     
		var TransType = baseResponse.getElementsByTagName("TransType")[0].firstChild.nodeValue;
		//alert("TransType-->>"+TransType);
		if (TransType == 'T') {
			// alert("seq T 1"+seq)
			/* Load TWAD Trasactions  Values */

			/** Delete Existing Values from Grid */
			var tbody = document.getElementById("grid_body_TWAD");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			var Sl_No = baseResponse.getElementsByTagName("Sl_No");

			var item = new Array();
			document.getElementById("txtTotTrans").value="";
			for ( var k = 0; k < Sl_No.length; k++) {

				item[0] = baseResponse.getElementsByTagName("Vou_No")[k].firstChild.nodeValue;
				if (item[0] == 'null') {
					item[0] = "";
				}

				item[1] = baseResponse.getElementsByTagName("Rem_Date")[k].firstChild.nodeValue;
				if (item[1] == 'null') {
					item[1] = "";
				}

				item[2] = baseResponse.getElementsByTagName("With_Date")[k].firstChild.nodeValue;
				if (item[2] == 'null') {
					item[2] = "";
				}

				item[3] = baseResponse.getElementsByTagName("Cheque_No")[k].firstChild.nodeValue;
				if (item[3] == 'null') {
					item[3] = "";
				}

				item[4] = baseResponse.getElementsByTagName("cr_amt")[k].firstChild.nodeValue;
				if (item[4] == 'null') {
					item[4] = "";
				}

				item[5] = baseResponse.getElementsByTagName("dr_amt")[k].firstChild.nodeValue;
				if (item[5] == 'null') {
					item[5] = "";
				}

				item[6] = baseResponse.getElementsByTagName("Sl_No")[k].firstChild.nodeValue;

				item[7] = baseResponse.getElementsByTagName("docType")[k].firstChild.nodeValue;
				if (item[7] == 'null') {
					item[7] = "";
				}

				item[8] = baseResponse.getElementsByTagName("docNo")[k].firstChild.nodeValue;
				if (item[8] == 'null') {
					item[8] = "";
				}
				item[9] = baseResponse.getElementsByTagName("Amount_Passbook")[k].firstChild.nodeValue;
				if (item[9] == 'null') {
					item[9] = "";
				}
				//alert("hh::");
                item[10] = baseResponse.getElementsByTagName("entryDate")[k].firstChild.nodeValue;
				if (item[10] == 'null') {
					item[10] = "";
				}
                                
				//alert("item[9]:-"+item[9]);
				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				/** Create Check Box */
				cell1 = document.createElement("TD");

				var sel = "";
				if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
					sel = document
							.createElement("<INPUT type='checkbox' onclick='callme("+seq+")' name='sel" 
									+ seq + "' id='sel" + seq + "' value="
									+ item[6] + " >");
				} else {
					sel = document.createElement("input");
					sel.type = "checkbox";
					sel.name = "sel" + seq;
					//alert("sel.name:"+sel.name);
					sel.id = "sel" + seq;
					//sel.checked = true;
					// sel.value=item[6];                          
					sel.value = seq;
					 sel.setAttribute('onclick',"callme("+seq+")");
				}
				cell1.appendChild(sel);
				mycurrent_row.appendChild(cell1);

				/** Challan or Voucher Number Creation */
				var cell4 = document.createElement("TD");
				var r_w_no = document.createElement("input");
				r_w_no.type = "hidden";
				r_w_no.name = "r_w_no" + seq;
				r_w_no.id = "r_w_no" + seq;
				r_w_no.value = item[0];
				cell4.appendChild(r_w_no);
				var currentText = document.createTextNode(item[0]);
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);

                                var cell21 = document.createElement("TD");
				var entry_on = document.createElement("input");
				entry_on.type = "hidden";
				entry_on.name = "entry_one" + seq;
				entry_on.id = "entry_one" + seq;
				entry_on.value = item[10];
				cell21.appendChild(entry_on);
				var currentText = document.createTextNode(item[10]);
				cell21.appendChild(currentText);
				mycurrent_row.appendChild(cell21);

				/** Docuemnt Number and Type */
				var cell4_1 = document.createElement("TD");

				var docType = document.createElement("input");
				docType.type = "hidden";
				docType.name = "docType" + seq;
				docType.id = "docType" + seq;
				docType.value = item[7];
				cell4_1.appendChild(docType);

				var docNo = document.createElement("input");
				docNo.type = "hidden";
				docNo.name = "docNo" + seq;
				docNo.id = "docNo" + seq;
				docNo.value = item[8];
				cell4_1.appendChild(docNo);
				var currentText = document.createTextNode(item[8] + " "
						+ item[7]);
				cell4_1.appendChild(currentText);

				mycurrent_row.appendChild(cell4_1);
				
				
				
				/** Sl No */
				
				var cell2_1 = document.createElement("TD");
				var ssl_no = document.createElement("input");
				ssl_no.type = "hidden";
				ssl_no.name = "sno" + seq;
				ssl_no.id = "sno" + seq;
				ssl_no.value = item[6];
				cell2_1.appendChild(ssl_no);
				var currentText = document.createTextNode(item[6]);
				cell2_1.appendChild(currentText);
				mycurrent_row.appendChild(cell2_1);

				

				/** Remittance Date Creation */
				var cell2 = document.createElement("TD");
				var r_date = document.createElement("input");
				r_date.type = "hidden";
				r_date.name = "r_date" + seq;
				r_date.id = "r_date" + seq;
				r_date.value = item[1];
				cell2.appendChild(r_date);
				var currentText = document.createTextNode(item[1]);
				cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);

				/** WithDrawl Date */
				var cell3 = document.createElement("TD");
				var w_date = document.createElement("input");
				w_date.type = "hidden";
				w_date.name = "w_date" + seq;
				w_date.id = "w_date" + seq;
				w_date.value = item[2];
				cell3.appendChild(w_date);
				var currentText = document.createTextNode(item[2]);
				cell3.appendChild(currentText);
				mycurrent_row.appendChild(cell3);

				/** Cheque / DD Number */
				var cell5 = document.createElement("TD");
				var ccdd_no = document.createElement("input");
				ccdd_no.type = "hidden";
				ccdd_no.name = "ccdd_no" + seq;
				ccdd_no.id = "ccdd_no" + seq;
				ccdd_no.value = item[3];
				cell5.appendChild(ccdd_no);
				var currentText = document.createTextNode(item[3]);
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);

				/** CR Amount */
				var cell6 = document.createElement("TD");
				var cr_amount = document.createElement("input");
				cr_amount.type = "hidden";
				cr_amount.name = "cr_amount" + seq;
				cr_amount.id = "cr_amount" + seq;
				cr_amount.value = item[4];
				cell6.appendChild(cr_amount);
				var currentText = document.createTextNode(item[4]);
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);

				/** DR Amount */
				var cell7 = document.createElement("TD");
				var dr_amount = document.createElement("input");
				dr_amount.type = "hidden";
				dr_amount.name = "dr_amount" + seq;
				dr_amount.id = "dr_amount" + seq;
				dr_amount.value = item[5];
				cell7.appendChild(dr_amount);
				var currentText = document.createTextNode(item[5]);
				cell7.appendChild(currentText);
				mycurrent_row.appendChild(cell7);

				/** Amount in Passbook */
				var cell8 = document.createElement("TD");
				var amount_Passbook = document.createElement("input");
				amount_Passbook.type = "hidden";
				amount_Passbook.name = "amount_Passbook" + seq;
				amount_Passbook.id = "amount_Passbook" + seq;
				amount_Passbook.value = item[9];
				cell8.appendChild(amount_Passbook);
				var currentText = document.createTextNode(item[9]);
				cell8.appendChild(currentText);
				mycurrent_row.appendChild(cell8);

				tbody.appendChild(mycurrent_row);

				/** Increment Sequence Number */
				seq = seq + 1;
			}
			document.getElementById("txtTotTrans").value=seq;
		// alert("seq T 2"+seq)
		} else if (TransType == "NT") {
			// alert("seq NT 1"+seq)
			/* Load NON-TWAD Trasaction  Values */

			/** Delete Existing Values from Grid */
			var tbody = document.getElementById("grid_body_NONTWAD");
			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}

			var Sl_No = baseResponse.getElementsByTagName("Sl_No");
			//alert(":--->>"+Sl_No);
			var item = new Array();

			for ( var k = 0; k < Sl_No.length; k++) {
				item[0] = baseResponse.getElementsByTagName("Passbook_Date")[k].firstChild.nodeValue;
				if (item[0] == 'null') {
					item[0] = "";
				}

				item[1] = baseResponse.getElementsByTagName("Particulars")[k].firstChild.nodeValue;
				if (item[1] == 'null') {
					item[1] = "";
				}

				item[2] = baseResponse.getElementsByTagName("Cheque_No")[k].firstChild.nodeValue;
				if (item[2] == 'null') {
					item[2] = "";
				}

				item[3] = baseResponse.getElementsByTagName("cr_amt")[k].firstChild.nodeValue;
				if (item[3] == 'null') {
					item[3] = "";
				}

				item[4] = baseResponse.getElementsByTagName("dr_amt")[k].firstChild.nodeValue;
				if (item[4] == 'null') {
					item[4] = "";
				}

				item[5] = baseResponse.getElementsByTagName("Sl_No")[k].firstChild.nodeValue;

				item[7] = baseResponse.getElementsByTagName("docType")[k].firstChild.nodeValue;
				if (item[7] == 'null') {
					item[7] = "";
				}

				item[8] = baseResponse.getElementsByTagName("docNo")[k].firstChild.nodeValue;
				if (item[8] == 'null') {
					item[8] = "";
				}
				
				/** Create Table Row */
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = seq;

				/** Create Check Box */
				cell1 = document.createElement("TD");
				var sel = "";
				if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
					sel = document
							.createElement("<INPUT type='checkbox' name='sel"
									+ seq + "' id='sel" + seq + "' value="
									+ item[5] + " >");
				} else {
					sel = document.createElement("input");
					sel.type = "checkbox";
					sel.name = "sel" + seq;
					
					sel.id = "sel" + seq;
					sel.checked = false;
					sel.value = seq;
					// alert("sel.value:"+sel.value);
				}
				cell1.appendChild(sel);
				mycurrent_row.appendChild(cell1);

				/** PassBook  Date Creation */
				var cell2 = document.createElement("TD");
				var pass_date = document.createElement("input");
				pass_date.type = "hidden";
				pass_date.name = "pass_date" + seq;
				pass_date.id = "pass_date" + seq;
				pass_date.value = item[0];
				cell2.appendChild(pass_date);
				var currentText = document.createTextNode(item[0]);
				cell2.appendChild(currentText);
				mycurrent_row.appendChild(cell2);
				
				var cells2 = document.createElement("TD");
				var serialno = document.createElement("input");
				serialno.type = "hidden";
				serialno.name = "sno" + seq;
				serialno.id = "sno" + seq;
				serialno.value = item[5];
				cells2.appendChild(serialno);
				var currentText = document.createTextNode(item[5]);
				cells2.appendChild(currentText);
				mycurrent_row.appendChild(cells2);
				
				
				/** Particulars */
				var cell4 = document.createElement("TD");
				var Part = document.createElement("input");
				Part.type = "hidden";
				Part.name = "parti" + seq;
				Part.id = "parti" + seq;
				Part.value = item[1];
				cell4.appendChild(Part);
				var currentText = document.createTextNode(item[1]);
				cell4.appendChild(currentText);
				mycurrent_row.appendChild(cell4);
				
		
				/** Cheque / DD Number */
				var cell5 = document.createElement("TD");
				var ccdd_no = document.createElement("input");
				ccdd_no.type = "hidden";
				ccdd_no.name = "ccdd_no" + seq;
				ccdd_no.id = "ccdd_no" + seq;
				ccdd_no.value = item[2];
				cell5.appendChild(ccdd_no);
				var currentText = document.createTextNode(item[2]);
				cell5.appendChild(currentText);
				mycurrent_row.appendChild(cell5);
				
				var cell6 = document.createElement("TD");
				var cr_amount = document.createElement("input");
				cr_amount.type = "hidden";
				cr_amount.name = "cr_amount" + seq;
				cr_amount.id = "cr_amount" + seq;
				cr_amount.value = item[3];
				cell6.appendChild(cr_amount);
				var currentText = document.createTextNode(item[3]);
				cell6.appendChild(currentText);
				mycurrent_row.appendChild(cell6);
				
				var cell7 = document.createElement("TD");
				var dr_amount = document.createElement("input");
				dr_amount.type = "hidden";
				dr_amount.name = "dr_amount" + seq;
				dr_amount.id = "dr_amount" + seq;
				dr_amount.value = item[4];
				cell7.appendChild(dr_amount);
				var currentText = document.createTextNode(item[4]);
				cell7.appendChild(currentText);
				mycurrent_row.appendChild(cell7);

								
				tbody.appendChild(mycurrent_row);

				
				
				
				/** Increment Sequence Number */
				seq = seq + 1;

			}
			//alert("seq NT 2"+seq)
		}
		document.getElementById("txtTotTrans").value=seq;
	} else if (flag=="BRSfreezed") {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body_TWAD");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
		alert("Records Can't be Loaded, Because BRS Monthly Closure had already freezed ............ ");
	} else {
		/** Delete Existing Values from Grid */
		var tbody = document.getElementById("grid_body_TWAD");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}
		
		var tbody1 = document.getElementById("grid_body_NONTWAD");
		var t1 = 0;
		for (t1 = tbody1.rows.length - 1; t1 >= 0; t1--) {
			tbody1.deleteRow(0);
		}
		
		alert("Records not found");
	}

}
//rcd
function beforeTest()
{
//alert("checking From Date");
var fDate=document.getElementById("txtFrom_date").value.split("/");
var txtCB_Year=document.getElementById("txtCB_Year").value;
var txtCB_Month=document.getElementById("txtCB_Month").value;
    if(fDate[1]!=txtCB_Month)
    {
    alert("FromDate Month Should be within CashBook Month");
    document.getElementById("txtFrom_date").value="";
    document.getElementById("txtFrom_date").focus();
        return false;
    }
else if(fDate[2]!=txtCB_Year)
    {
    alert("FromDate Year Should be within CashBook Year");
    document.getElementById("txtFrom_date").value="";
    document.getElementById("txtFrom_date").focus();
    return false;
    }
    else
    {
    return true;
    }
    
}

function beforeTest_one()
{
alert("checking To Date");
var toDate=document.getElementById("txtTo_date").value.split("/");
var txtCB_Year=document.getElementById("txtCB_Year").value;
var txtCB_Month=document.getElementById("txtCB_Month").value;
    if(toDate[1]!=txtCB_Month)
    {
    alert("FromDate Month Should be within CashBook Month");
    document.getElementById("txtTo_date").value="";
    document.getElementById("txtTo_date").focus();
        return false;
    }
else if(toDate[2]!=txtCB_Year)
    {
    alert("FromDate Year Should be within CashBook Year");
    document.getElementById("txtTo_date").value="";
    document.getElementById("txtTo_date").focus();
    return false;
    }
    else
    {
    return true;
    }
}

function TWAD_NONTWAD(id) {
	var TWAD = document.getElementById("TWAD");
	var NON_TWAD = document.getElementById("NON_TWAD");

	if (id == "T") {
		TWAD.style.display = "block";
		NON_TWAD.style.display = "none";
	}
	if (id == "NT") {
		NON_TWAD.style.display = "block";
		TWAD.style.display = "none";
	}
        //pradha
        var tbody = document.getElementById("grid_body_TWAD");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var t1 = 0;
	for (t1 = tbody1.rows.length - 1; t >= 0; t--) {
		tbody1.deleteRow(0);
	}

}

function checkNull() {
	
	if(document.forms[0].TransType[0].checked==true)
	{
		var tbody=document.getElementById("grid_body_TWAD");
	}
		else
		{
			var tbody=document.getElementById("grid_body_NONTWAD");
		}
	
	if(document.getElementById("cmbBankAccNo").value=="")
	{
		alert("Choose Bank Account No");
		return false;
	}

	if(tbody.rows.length==0)
	{
	    alert("Enter the Details Part");
	    // document.getElementById("txtAmount").focus();
	    return false; 
	}
	
}

function clr() {
	document.getElementById("cmbBankAccNo").value = "";
	document.getElementById("txtReferNO_edit").value = "";
	document.getElementById("txtReferDate_edit").value = "";
	document.getElementById("txtRemak_edit").value = "";

	var tbody = document.getElementById("grid_body_TWAD");
	var t = 0;
	for (t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}

	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var t1 = 0;
	for (t1 = tbody1.rows.length - 1; t >= 0; t--) {
		tbody1.deleteRow(0);
	}
}

function exit() {
	self.close();
}
function selectAll(Opt)
{
  var len=  document.getElementById("grid_body_TWAD").rows.length;

  if(len==1)
  {
          if (Opt =="ALL")
          {
        	 document.getElementById("sel0").checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.getElementById("sel0").checked=false;
        
          }
  }
  else if(len>1)
  {
 
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.getElementById("sel"+i).checked=true;
                }
                else if(Opt=="UNSelect")
                {
               //sel0,sel1,...
                   document.getElementById("sel"+i).checked=false;
                }
          }
  }

}
function selectAll_two()
{
var len=  document.getElementById("grid_body_NONTWAD").rows.length;

  if(len==1)
  {
          if (Opt =="ALL")
          {
        	 document.getElementById("sel0").checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.getElementById("sel0").checked=false;
        
          }
  }
  else if(len>1)
  {
 
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.getElementById("sel"+i).checked=true;
                }
                else if(Opt=="UNSelect")
                {
               //sel0,sel1,...
                   document.getElementById("sel"+i).checked=false;
                }
          }
  }
}
function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          //t.blur();
          //return true;-------------------- for taking action when press ENTER
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48 || unicode>57 ) 
                return false 
        }
     }
     

function callme(sam)
{

        var amt = 0;
	var fg = 0;
	var fg1 = 0;
	var ii = 0;
        var dd=0;
  if ( document.getElementById("sel"+sam).checked == true)
   {
  // alert("1111::::"+sam);
   var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
            
    for ( var i = 0; i < rowcount; i++)
    {
            var r = tbody.rows[i];
            
            var doctype_one=document.getElementById("docType" + sam).value;
    		
			if(doctype_one!='CR'){
            	if (document.getElementById("ccdd_no" + sam).value != "") {
                     
                        if ((document.getElementById("ccdd_no" + i).value) == (document
                                        .getElementById("ccdd_no" + sam).value)) 
                      {
                                     
                                fg1 = fg1 + 1;
                                if (fg1 == 1) {
                                        ii = i;
                                }
                                r.bgColor = "#FFCCCC";
                                document.getElementById("sel" + i).checked = true;
                                
                               
                        }
					} else {
						document.getElementById("sel" + sam).checked = true;
						
					}
			}
			else
			{
				if (document.getElementById("entry_one" + sam).value != "") {
					//	alert("loop");
						if ((document.getElementById("entry_one" + i).value) == (document
								.getElementById("entry_one" + sam).value)) {
							  fg1 = fg1 + 1;
                              if (fg1 == 1) {
                                      ii = i;
                              }
                              r.bgColor = "#FFCCCC";
                              document.getElementById("sel" + i).checked = true;
						}
				}
				else {
					document.getElementById("sel" + sam).checked = true;
					
				}
			}
            
    }


                var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		var r = tbody.rows[sam];
   }
   else
   {
      
      var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			 var doctype_one=document.getElementById("docType" + sam).value;
	    		
				if(doctype_one!='CR'){	
						if (document.getElementById("ccdd_no" + sam).value != "") {
							if ((document.getElementById("ccdd_no" + i).value) == (document
									.getElementById("ccdd_no" + sam).value)) {
								   document.getElementById("sel" + i).checked = false;
								
							}
						} 
						else {
							
						}
				}
				else
				{
					
					if (document.getElementById("entry_one" + sam).value != "") {
						//	alert("loop");
							if ((document.getElementById("entry_one" + i).value) == (document
									.getElementById("entry_one" + sam).value)) {
								 document.getElementById("sel" + i).checked = false;
							}
					}
					
				}
		}
      
   }
}

var bank_ac_no =new Array();
var acc_desc   =new Array();
var bank_name  = new Array();
var branch_name = new Array();
var bank_id  = new Array();
var branch_id = new Array();
var opr_mode  = new Array();

function LoadBankAccountNumber()
{ 
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   //alert("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
           var url;
         
            url="../../../../../Common_Bank_Account_Number_Loading.kv?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   
        
           if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
           
           // alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 load_bank_no(req);
	   }   
       req.send(null);
	  
}



function load_bank_no(req)
{  
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            // alert("baseResponse"+baseResponse);
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
         //
            if(Command=="LoadBankAccountNumber")
            { 
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	          
            		 
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	          
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	           
            	            for(var k=0;k<acc_no.length;k++)
            	            {
            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
            	            }
            	       //  alert("tttt");
            	            cmbBankAccNo.innerHTML="";
            	            var option=document.createElement("OPTION");
            	          //  option.text="--Select Bank Acc. Number--";
            	          //  option.value="";
//            	            try
//            	            {
//            	            	cmbBankAccNo.add(option);
//            	            }catch(errorObject)
//            	            {
//            	            	cmbBankAccNo.add(option,null);
//            	            }
            	            
            	            for(var k=0;k<acc_no.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=acc_desc[k];
            	                  option.value=bank_ac_no[k];
            	                  try
            	                  {
            	                	  cmbBankAccNo.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbBankAccNo.add(option,null);
            	                  }
            	            }
            	    }
              }
              else
              {
            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
             	    if(flag=="success")
             	    {
             	    		var acc_head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
             	    		document.getElementById("txtOprCode").value=acc_head_code;
             	    }
              }
        
        }
    }
}

