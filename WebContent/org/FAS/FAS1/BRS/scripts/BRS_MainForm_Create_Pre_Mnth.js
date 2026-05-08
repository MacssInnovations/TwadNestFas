
var CatCode = new Array();
var CatDesc = new Array();

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
			return false
	}
}

function loadyear_month1()
{
	//load intially
var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
	var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;

	// alert(url);
	var xmlrequest = AjaxFunction();
    xmlrequest.open("POST", url, true);
    document.getElementById("imgfld").style.visibility = "visible";
    xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function loadYear()
{
	// works on year changing
	//alert("year");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
    var txtCB_Year = document.getElementById("txtCB_Year").value;
    if(txtCB_Year=="")
	{
	alert("Enter Year");
	return false;
	}
	var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear_again&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&txtCB_Year="+txtCB_Year;

	// alert(url);
	var xmlrequest = AjaxFunction();
    xmlrequest.open("POST", url, true);
    document.getElementById("imgfld").style.visibility = "visible";
    xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function loadmonth_again()
{

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
    var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
if(cmbBankAccNo=="")
	{
	alert("Enter A/C No");
	return false;
	}
	var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonth_again&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

	// alert(url);
	var xmlrequest = AjaxFunction();
    xmlrequest.open("POST", url, true);
  //  document.getElementById("imgfld").style.visibility = "visible";
    xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function LoadMonthYear() {

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];
                       
			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;

			if (command == "LoadTWADTransactions") {
				LoadTWADTransactions1(baseResponse);
			}
			else if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			}
			else if (command == "LoadMonthYear_again") {
				LoadMonthYear_again(baseResponse);
			}
			else if (command == "LoadMonth_again") {
				LoadMonth_again(baseResponse);
			}
			else if (command == "loadmnYEr") {
				loadmnYEr_again(baseResponse);
			}
			
		}
	}
}
function loadmnYEr_again(baseResponse){
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var CASHBOOK_YEAR = baseResponse.getElementsByTagName("CASHBOOK_YEAR")[0].firstChild.nodeValue;
		var CASHBOOK_MONTH = baseResponse.getElementsByTagName("CASHBOOK_MONTH")[0].firstChild.nodeValue;
		document.getElementById("hidMonth").value=CASHBOOK_MONTH;
		document.getElementById("hidYear").value=CASHBOOK_YEAR;
	}
		else{
			
		}
			
}
function LoadMonth_again(baseResponse)
{
	var txtCB_Year1 = document.getElementById("txtCB_Year").value;
	var txtCB_Month1 = document.getElementById("txtCB_Month").value;
	txtCB_Year = parseInt(txtCB_Year1);
	txtCB_Month = parseInt(txtCB_Month1);
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		CB_Year = parseInt(CB_Year1);
		CB_Month = parseInt(CB_Month1);
		 document.getElementById("imgfld").style.visibility = "hidden";
		
		if(CB_Year>txtCB_Year)
			{
			if(CB_Month==1)
			{
				document.getElementById("txtCB_Year").value=(CB_Year-1);
				document.getElementById("txtCB_Month").value=12;	
			}
			else
			{
				document.getElementById("txtCB_Year").value=(CB_Year);
				document.getElementById("txtCB_Month").value=(CB_Month-1);	
			}
				
		
			/*if(window.confirm("Do you want Current Year ")){
				document.getElementById("txtCB_Year").value=CB_Year;
				alert("CB_Year:::"+CB_Year);
			}  */
				
			}
		else if(CB_Year==txtCB_Year)
		  {
			if(CB_Month==txtCB_Month)
				{
				var mn=CB_Month-1;
					alert("Month Should be Less than Start Month");
					
					document.getElementById("txtCB_Month").value=mn;
					return false;
				}
			else if(CB_Month<txtCB_Month)
				{
				var mn=CB_Month-1;
					alert("Month Should be Less than Start Month");
					
					document.getElementById("txtCB_Month").value=mn;
					return false;
					
				
				}
			else if(CB_Month>txtCB_Month)
			{
				var mn=CB_Month-1;
				document.getElementById("txtCB_Month").value=mn;
			}
		
			}
		else if(CB_Year<txtCB_Year)
			{
			alert("Year Should be Less Than Start Year");
			document.getElementById("txtCB_Year").value="";
			return false;
			}
		
	} else if (flag == "NoData") {
		document.getElementById("imgfld").style.visibility = "hidden";
		alert("Year is Greater Chooser Previous Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
	} else {
		document.getElementById("imgfld").style.visibility = "hidden";
		alert("Failed to Load Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
	}
}

//year changes
function LoadMonthYear_again(baseResponse) {
	var txtCB_Year1 = document.getElementById("txtCB_Year").value;
	var txtCB_Month1 = document.getElementById("txtCB_Month").value;
	txtCB_Year = parseInt(txtCB_Year1);
	txtCB_Month = parseInt(txtCB_Month1);
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		CB_Year = parseInt(CB_Year1);
		CB_Month = parseInt(CB_Month1);
		 document.getElementById("imgfld").style.visibility = "hidden";
		if(CB_Month==1)
			{
			if(CB_Year==txtCB_Year)
				{
				alert("Year Should be Less Than Start Year");
				document.getElementById("txtCB_Year").value="";
				return false;
				}
			
			}
		else if(CB_Month==12)
			{
				if(CB_Year==txtCB_Year)
				{
					var month=CB_Month-1;
					 document.getElementById("txtCB_Month").value=month;
				}
				else if(CB_Year>txtCB_Year)
					{
					
					}
				else
					{
					alert("Year Should be Less Than Start Year");
					document.getElementById("txtCB_Year").value="";
					return false;
					}
			}
		else
			{
				if(txtCB_Year>CB_Year)
					{
					alert("Year Should be Less Than Start Year");
					document.getElementById("txtCB_Year").value="";
					return false;
					}
				else if(CB_Year==txtCB_Year)
					{
					 if(CB_Month<txtCB_Month)
						 {
						 var mn=CB_Month-1;
							//alert("Enter Month Lesser than "+CB_Month);
							document.getElementById("txtCB_Month").value=mn;
							return false;
						 }
					}
			}
		
		
	} else if (flag == "NoData") {
		document.getElementById("imgfld").style.visibility = "hidden";
		alert("BRS Initialization Year is Greater Chooser Previous Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
	} else {
		document.getElementById("imgfld").style.visibility = "hidden";
		alert("Failed to Load Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
	}
}


function LoadMonthYear1(baseResponse) {
	var txtCB_Year1 = document.getElementById("txtCB_Year").value;
	var txtCB_Month1 = document.getElementById("txtCB_Month").value;
	txtCB_Year = parseInt(txtCB_Year1);
	txtCB_Month = parseInt(txtCB_Month1);
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		  document.getElementById("imgfld").style.visibility = "hidden";
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		CB_Year = parseInt(CB_Year1);
		CB_Month = parseInt(CB_Month1);
		
		if(CB_Month==1)
			{
			FAS_BRS_TRANSACTION_NOENTRY
			var year=CB_Year-1;
			document.getElementById("txtCB_Year").value=year;
			document.getElementById("txtCB_Month").value=12;
			
			}
		else if(CB_Month==12)
			{
			document.getElementById("txtCB_Year").value=CB_Year;
			document.getElementById("txtCB_Month").value=CB_Month-1;
			}
		else
			{
			document.getElementById("txtCB_Year").value=CB_Year;
			document.getElementById("txtCB_Month").value=CB_Month-1;
			}
		
		  document.getElementById("imgfld").style.visibility = "hidden";
		
	} else if (flag == "NoData") {
		document.getElementById("imgfld").style.visibility = "hidden";
		alert("First Set BRS Initiation Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
	} else {
		document.getElementById("imgfld").style.visibility = "hidden";
		alert("Failed to Load Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
	}
}

function doFunction_brs(filter) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (filter == "CashbookYrMnth") {
		var filterflag = "CashbookYrMnth";
		var url = "../../../../../BRS_MainForm_Create_Pre_Mnth.kv?Command=LoadTWADTransactions&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&txtCB_Year="
				+ txtCB_Year
				+ "&txtCB_Month="
				+ txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&filterflag=" + filterflag;
        // alert(url);                      
		if (txtCB_Year == "") {
			alert("Enter Cash Book Year in the Field");
			document.frmBRS_MainForm_Create_Pre_Mnth.txtCB_Year.focus();
		} else if (txtCB_Month == "") {
			alert("Enter Cash Book Month in the Field");
			document.frmBRS_MainForm_Create_Pre_Mnth.txtCB_Month.focus();
		} else if (cmbBankAccNo == "") {
			alert("Enter Bank Account No in the Field");
			document.frmBRS_MainForm_Create_Pre_Mnth.cmbBankAccNo.focus();
		} else
			{
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		document.getElementById("imgfld").style.visibility = "visible";
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
			}
	} else if(filter=="loadmnYEr")
	{
		var url="../../../../../BRS_MainForm_Create_Pre_Mnth.kv?Command=loadmnYEr&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbBankAccNo=" + cmbBankAccNo;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		document.getElementById("imgfld").style.visibility = "visible";
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
			
	
	}else {
		var filterflag = "LastSixMonth";
		var url = "../../../../../BRS_MainForm_Create_Pre_Mnth.kv?Command=LoadTWADTransactions&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&filterflag=" + filterflag + "&cmbBankAccNo=" + cmbBankAccNo;
		if (cmbBankAccNo == "") {
			alert("Enter Bank Account No in the Field");
			document.frmBRS_MainForm_Create_Pre_Mnth.cmbBankAccNo.focus();
		} else
			{
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		document.getElementById("imgfld").style.visibility = "visible";
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
			}
	}
}

function LoadTWADTransactions1(baseResponse) {
	var seq=0;
	flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
	if(flag == "success")
	{		
		
		document.getElementById("butSub").disabled=false;
		document.getElementById("imgfld").style.visibility = "hidden";
		/** Delete Existing Values from Grid */
    	var tbody1=document.getElementById("grid_body_TWAD");
        var t=0;
        for(t=tbody1.rows.length-1;t>=0;t--)
        {
           tbody1.deleteRow(0);
        }
	var len6 = baseResponse.getElementsByTagName("r_cheque_dd_no").length;

	if(len6!=0)
	{
	for ( var k = 0; k < len6; k++) {
		var REMITTANCE_DATE = baseResponse.getElementsByTagName("REMITTANCE_DATE")[k].firstChild.nodeValue;
		var r_date = baseResponse.getElementsByTagName("r_date")[k].firstChild.nodeValue;
                //alert("REMITTANCE_DATE"+REMITTANCE_DATE+" r_date ");
		if(REMITTANCE_DATE=='null' || REMITTANCE_DATE == '0'){
			r_date = "";}
               /* if (r_date == 'null' || r_date == '0') {
				r_date = ""; }*/
			
            	var WITHDRAWAL_DATE = baseResponse.getElementsByTagName("WITHDRAWAL_DATE")[k].firstChild.nodeValue;
		var w_date = baseResponse.getElementsByTagName("w_date")[k].firstChild.nodeValue;
               //alert("w_date:"+w_date);
		if(WITHDRAWAL_DATE=='null' || WITHDRAWAL_DATE == '0'){
			w_date = "";}
              /*  if (w_date == 'null' || w_date == '0') {
				w_date = ""; }*/
			
		var w_challan_no = baseResponse.getElementsByTagName("w_challan_no")[k].firstChild.nodeValue;
		var r_cheque_dd_no = baseResponse.getElementsByTagName("r_cheque_dd_no")[k].firstChild.nodeValue;
		var cr_amount = baseResponse.getElementsByTagName("cr_amount")[k].firstChild.nodeValue;
                if(cr_amount=='0')
                {
                cr_amount="";
                }
		var dr_amount = baseResponse.getElementsByTagName("dr_amount")[k].firstChild.nodeValue;
                if(dr_amount=='0')
                {
                dr_amount="";
                }
		var doc_no = baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;
		var doc_type = baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;

		var doc_date1=baseResponse.getElementsByTagName("com_doc_date")[k].firstChild.nodeValue;   
        //alert("doc_date"+doc_date);
        if(doc_date1 == 'null' || doc_date1 == '0' )
	{
		doc_date1="";
	}
        
		var tbody = document.getElementById("grid_body_TWAD");
		var table = document.getElementById("mytable");
		var mycurrent_row = document.createElement("TR");
		//mycurrent_row.id = ProjectionNo;

		/** Sl No */
    	var cell0 = document.createElement("TD");
			var slno = document.createTextNode(seq+1);
			cell0.appendChild(slno);
			mycurrent_row.appendChild(cell0);
			
		var cell1 = document.createElement("TD");
    	var r_date1=document.createElement("input");
    	r_date1.type="hidden";
    	r_date1.name="r_date"+seq;
            r_date1.id="r_date"+seq;
    	r_date1.value=r_date;
    	cell1.appendChild(r_date1);
    	var currentText=document.createTextNode(r_date);
    	cell1.appendChild(currentText);
    	mycurrent_row.appendChild(cell1);
    	
    	 /** WithDrawl Date */
        var cell2=document.createElement("TD"); 
        var w_date1=document.createElement("input");
        w_date1.type="hidden";
        w_date1.name="w_date"+seq;
        w_date1.id="w_date"+seq;             
        w_date1.value=w_date;
        cell2.appendChild(w_date1);
        var currentText=document.createTextNode(w_date);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);
        
        var cell3=document.createElement("TD"); 
        var w_challan_no1=document.createElement("input");
        w_challan_no1.type="hidden";
        w_challan_no1.name="w_challan_no"+seq;
        w_challan_no1.id="w_challan_no"+seq;             
        w_challan_no1.value=w_challan_no;
        cell3.appendChild(w_challan_no1);
        var currentText=document.createTextNode(w_challan_no+"-"+doc_type+"("+doc_no+")");
        cell3.appendChild(currentText);
        mycurrent_row.appendChild(cell3);               

        var cell5=document.createElement("TD"); 
        var r_cheque_dd_no1=document.createElement("input");
        r_cheque_dd_no1.type="hidden";
        r_cheque_dd_no1.name="r_cheque_dd_no"+seq;
        r_cheque_dd_no1.id="r_cheque_dd_no"+seq;             
        r_cheque_dd_no1.value=r_cheque_dd_no;      
        cell5.appendChild(r_cheque_dd_no1);
        var currentText=document.createTextNode(r_cheque_dd_no);
        cell5.appendChild(currentText);
        mycurrent_row.appendChild(cell5);        
		
        /** CR Amount */
        var cell6=document.createElement("TD");  
        var cr_amount1=document.createElement("input");
        cr_amount1.type="hidden";
        cr_amount1.name="cr_amount"+seq;
        cr_amount1.id="cr_amount"+seq;
        cr_amount1.value=cr_amount;
        cell6.appendChild(cr_amount1);
        var currentText=document.createTextNode(cr_amount);
        cell6.appendChild(currentText);
        mycurrent_row.appendChild(cell6);
        
        
        /** DR Amount */
        var cell7=document.createElement("TD");  
        var dr_amount1=document.createElement("input");
        dr_amount1.type="hidden";
        dr_amount1.name="dr_amount"+seq;
        dr_amount1.id="dr_amount"+seq;
        dr_amount1.value=dr_amount;
        cell7.appendChild(dr_amount1);
        var currentText=document.createTextNode(dr_amount);
        cell7.appendChild(currentText);
        mycurrent_row.appendChild(cell7);       
		
		 /** Entry Found in Pass Book ? */
        var cell8=document.createElement("TD");
        cell8.style.textAlign='center';
        var sel="";            
        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
        {
      	  sel=document.createElement("<INPUT type='checkbox' name='EntryFoundInPassBook"+seq+"' id='EntryFoundInPassBook"+seq+"' value='Y' onkeypress='calins_new(event,this)' onclick='callme("+seq+")' />" );                       
        }
        else
        {    
          sel=document.createElement("input");     
          sel.type="checkbox";             
          sel.name="EntryFoundInPassBook"+seq;
          sel.id="EntryFoundInPassBook"+seq; 
          sel.setAttribute('onkeypress',"calins_new(event,this);");
          sel.setAttribute('onclick',"callme("+seq+")");
          sel.value="Y";                          
        }
        cell8.appendChild(sel);
        mycurrent_row.appendChild(cell8);
	     
        /** Entry Date */
        var cell9=document.createElement("TD");  
        var Entry_Date=document.createElement("input");
        Entry_Date.type="Text";
        Entry_Date.name="Entry_Date"+seq;
        Entry_Date.id="Entry_Date"+seq;
        Entry_Date.value="";
        Entry_Date.maxLength="10";
       // Entry_Date.setAttribute('onblur',"dateValidation_PBDte("+seq+");dateValidation("+seq+");");
        Entry_Date.setAttribute('onblur',"dateValidation_PBDte("+seq+");dateValidationNEW("+seq+")");
        Entry_Date.size="10";
        cell9.appendChild(Entry_Date);
        mycurrent_row.appendChild(cell9);   
        
        /** Amount in Pass Book */
        var cell10=document.createElement("TD");  
        var Amt_in_PassBk=document.createElement("input");
        Amt_in_PassBk.type="Text";
        Amt_in_PassBk.name="Amt_in_PassBk"+seq;
        Amt_in_PassBk.id="Amt_in_PassBk"+seq;
        Amt_in_PassBk.value="";
        Amt_in_PassBk.setAttribute('onblur',"callDifference("+seq+"),callme1("+seq+")");
        Amt_in_PassBk.size="10";
        cell10.appendChild(Amt_in_PassBk);
        mycurrent_row.appendChild(cell10);  
     
        /** Amount Difference */
        var cell11=document.createElement("TD");  
        var Amt_Diff=document.createElement("input");
        Amt_Diff.type="Text";
        Amt_Diff.name="Amt_Diff"+seq;
        Amt_Diff.id="Amt_Diff"+seq;              
        Amt_Diff.value="";
        Amt_Diff.setAttribute('onchange',"callme1("+seq+")");
        Amt_Diff.size="5";
        cell11.appendChild(Amt_Diff);
        mycurrent_row.appendChild(cell11);  
		
        /**
         *  Dynamic Combo Creation and Loading 
         */ 
        
        /* Reason for Difference */
//        cell12=document.createElement("TD");
//        cell12.style.textAlign='center';  
//        var cmbCategoryCode;
//        
//        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
//        { try
//          {
//            cmbCategoryCode =  document.createElement("<select name='cmbReason4Diff"+seq+"' id='cmbReason4Diff' >");
//          }catch( e ) { alert(e.description) }
//        }
//        else
//        {
//           cmbCategoryCode=document.createElement("select");                        
//         //  cmbCategoryCode.id="cmbReason4Diff";
//           
//         //cmbCategoryCode="cmbReason4Diff";
//           cmbCategoryCode.id="cmbReason4Diff"+seq;
//           
//           cmbCategoryCode.name="cmbReason4Diff"+seq;
//        }
//        
//        var cmbCategoryCodeObj = baseResponse.getElementsByTagName("reason_pair");
//        var option11=document.createElement("option");    
//            option11.value="";  
//            option11.text="document.getElementById("butSub").disabled=false;--Select--";
//            
//       try
//         {
//            cmbCategoryCode.add(option11);
//         }
//      catch(e)
//         {
//             cmbCategoryCode.add(option11,null);
//         }                      
//      
//      
//        for(var y=0;y<cmbCategoryCodeObj.length;y++)
//        {
//        
//            CatCode[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_code")[0].firstChild.nodeValue;
//            CatDesc[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_desc")[0].firstChild.nodeValue;
//           
//            
//            var option11=document.createElement("option");    
//            
//            option11.value=CatDesc[y];  
//            option11.text=CatDesc[y];
//            
//          
//           try
//             {
//                 cmbCategoryCode.add(option11);
//             }
//           catch(e)
//             {
//                  cmbCategoryCode.add(option11,null);
//             }
//             
//       }           
//        
//        
//       cell12.appendChild(cmbCategoryCode);                                                    
//       mycurrent_row.appendChild(cell12);   
        
        
        cell12 = document.createElement("TD");
		cell12.style.textAlign = 'center';
		var cmbReason4Diff;

		if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) {
			try {
				cmbReason4Diff = document
						.createElement("<select name='cmbReason4Diff" + seq+ "' id='cmbReason4Diff'"+seq+">");
			} catch (e) {
				alert(e.description)
			}
		} else {
			cmbReason4Diff = document.createElement("select");
			cmbReason4Diff.id = "cmbReason4Diff"+seq;
			cmbReason4Diff.name = "cmbReason4Diff" + seq;
		}

		var cmbCategoryCodeObj = baseResponse
				.getElementsByTagName("reason_pair");
		var option11 = document.createElement("option");
		option11.value = "";
		option11.text = "--Select--";

		try {
			cmbReason4Diff.add(option11);
		} catch (e) {
			cmbReason4Diff.add(option11, null);
		}
                // for nontwad
		for ( var y = 0; y < cmbCategoryCodeObj.length; y++) {

			CatCode[y] = cmbCategoryCodeObj[y]
					.getElementsByTagName("reason_code")[0].firstChild.nodeValue;
			CatDesc[y] = cmbCategoryCodeObj[y]
					.getElementsByTagName("reason_desc")[0].firstChild.nodeValue;

			var option11 = document.createElement("option");

			option11.value = CatCode[y];
			option11.text = CatDesc[y];

			try {
				cmbReason4Diff.add(option11);
			} catch (e) {
				cmbReason4Diff.add(option11, null);
			}
		}

		cell12.appendChild(cmbReason4Diff);
		mycurrent_row.appendChild(cell12);
		
        
        
        
        
        

		 /* Follow up Action Required */             
        var cell13=document.createElement("TD");
        cell13.style.textAlign='center';
        var sel="";            
        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
        {
      	   sel=document.createElement("<INPUT type='checkbox' name='FollowUpAction"+seq+"' id='FollowUpAction' value='Y' />" );                       
        }
        else
        {    
          sel=document.createElement("input");     
          sel.type="checkbox";             
          sel.name="FollowUpAction"+seq;
          sel.id="FollowUpAction"+seq;
          sel.checked=false;
          sel.value="Y";           
          sel.setAttribute('onClick',"disableCE("+seq+")");
        }
        cell13.appendChild(sel);
        mycurrent_row.appendChild(cell13);
     
        
        /* Is it a clearance entry based on follow-up */             
        var cell14=document.createElement("TD");
        cell14.style.textAlign='center';
        var sel="";            
        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
        {
       	sel=document.createElement("<INPUT type='checkbox' name='ClearanceEntry"+seq+"' id='ClearanceEntry' value='Y'/>" );                       
        }
        else
        {    
          sel=document.createElement("input");     
          sel.type="checkbox";             
          sel.name="ClearanceEntry"+seq;
          sel.id="ClearanceEntry"+seq;
          sel.checked=false;
          sel.value="Y";     
          sel.setAttribute('onclick',"list1("+seq+")");
        }
        cell14.appendChild(sel);
        mycurrent_row.appendChild(cell14);

        var cell4=document.createElement("TD"); 
        var doc_no1=document.createElement("input");
        doc_no1.type="hidden";
        doc_no1.name="doc_no"+seq;
        doc_no1.id="doc_no"+seq;       
        doc_no1.value=doc_no;        
        cell4.appendChild(doc_no1);
        var currentText=document.createTextNode(doc_no);
        cell4.appendChild(currentText);
        cell4.style.visibility="hidden";
        mycurrent_row.appendChild(cell4);
        
        var cell44=document.createElement("TD"); 
        var doc_type1=document.createElement("input");
        doc_type1.type="hidden";
        doc_type1.name="doc_type"+seq;
        doc_type1.id="doc_type"+seq;        
        doc_type1.value=doc_type;       
        cell44.appendChild(doc_type1);
        var currentText=document.createTextNode(doc_type);
        cell44.appendChild(currentText);
        cell44.style.visibility="hidden";
        mycurrent_row.appendChild(cell44);
        
        /** doc Date */ 
     	var doc_date = document.createElement("input");
     	doc_date.setAttribute("type","hidden");
     	doc_date.setAttribute("value",doc_date1);
     	doc_date.setAttribute("name","doc_date"+seq);
     	doc_date.setAttribute("id","doc_date"+seq);
     	document.getElementById("frmBRS_MainForm_Create_Pre_Mnth").appendChild(doc_date); 
     	
		tbody.appendChild(mycurrent_row);
		seq=seq+1;  			
		//alert("seq"+seq);
		//alert("k"+k);
		//alert("len6"+len6);		
		
	}
				
	} else{		
		alert("Record Does Not Exist***");
	}
	}
	else if(flag=="AlreadyFreezed")
	{
	var indexaccno=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
	var splaccno=indexaccno.split("-");
	
	//alert("splaccno==>"+splaccno[1])
	if(splaccno[2]=="OPR")
		{
		alert("BRS Part-2A ,Part-2B ,Part-2C Reports are Already Freezed!......");
		document.getElementById("butSub").disabled=true;
		document.getElementById("imgfld").style.visibility = "hidden";
		}
	else if(splaccno[2]=="COL")
		{
		alert("BRS Part-1  Report Already Freezed!......");
		document.getElementById("butSub").disabled=true;
		document.getElementById("imgfld").style.visibility = "hidden";
		}
	else
		{
		alert("BRS Part-1,Part-2A ,Part-2B ,Part-2C Reports are Already Freezed!......");
		document.getElementById("butSub").disabled=true;
		document.getElementById("imgfld").style.visibility = "hidden";
		}
	
	}
	else
	{
	alert("Failed to Load");
}
	
	//alert("seq"+seq);
	document.getElementById('txtNoofRecords').value=seq;	
       // alert(seq);
	//seq=0;
}
var winemp;
function list1(val)
{	
	//alert("welcome");
	
	if(document.getElementById("ClearanceEntry"+val).checked==true)
	{
		document.getElementById("FollowUpAction"+val).disabled = true;
		
		var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
		   var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
		   var  txtCB_Year       = document.getElementById("txtCB_Year").value;
		   var  txtCB_Month      = document.getElementById("txtCB_Month").value;
		   var  cmbBankAccNo      = document.getElementById("cmbBankAccNo").value;
		   var Amt_in_pasbook = document.getElementById("Amt_in_PassBk"+val).value;
		   
		   if(document.getElementById("Amt_Diff" + val).value!=0)
			{
				var Trans_Type_NT0 = document.getElementById("cmbReason4Diff0").value;
			}
			else
				{
				Trans_Type_NT0=0;
				}
		  // alert("Trans_Type_NT0==>"+Trans_Type_NT0);
		   
//		   if(document.getElementById("EntryFoundInPassBook"+val).checked==true)
//		   {
//	        winemp= window.open("BRS_Clearance_Entry.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&Amt_in_pasbook="+Amt_in_pasbook+"&Trans_Type_NT0="+Trans_Type_NT0,"list","status=1,height=450,width=800,resizable=YES, scrollbars=yes");	
//	        winemp.moveTo(200,200);  
//	        winemp.focus();
//		   }
		   
		   if(document.getElementById("dr_amount"+val).value!=0)
		   {
			   ind="CR";//fetch cr datas in popup
			  
		   }
		   else
		   {
			   ind="DR";//fetch dr datas in popup
			 
		   }
		   
		   var doctype=document.getElementById("doc_type"+val).value;
			 
		   var docno=document.getElementById("doc_no"+val).value;
		   var docdate=document.getElementById("Entry_Date"+val).value;
		  
		if (document.getElementById("EntryFoundInPassBook" + val).checked == true) {
			
			if (winemp && winemp.open && !winemp.closed) 
		    {
		       winemp.resizeTo(500,500);
		       winemp.moveTo(250,250); 
		       winemp.focus();
		    }
		    else
		    {
		    	winemp=null;
		    }
			
			winemp = window.open("BRS_Clearance_Entry.jsp?cmbAcc_UnitCode="
							+ cmbAcc_UnitCode + "&cmbOffice_code="
							+ cmbOffice_code + "&txtCB_Year=" + txtCB_Year
							+ "&txtCB_Month=" + txtCB_Month + "&cmbBankAccNo="
							+ cmbBankAccNo + "&Amt_in_pasbook="
							+ Amt_in_pasbook+"&doctype="+doctype+"&docno="+docno+"&docdate="+docdate+"&indicator="+ind+"&Trans_Type_NT0="+Trans_Type_NT0, "list",
							"status=1,height=450,width=800,resizable=YES, scrollbars=yes");
			winemp.moveTo(200, 200);
			winemp.focus();
		} 
		   else	   
		   {
			   alert("Select the Correspondence 'Entry Found in Passbook' CheckBox");
		   }
	}else
	{
		document.getElementById("FollowUpAction"+val).disabled = false;
	}	
	   
}

function disableCE(val)
{
	if(document.getElementById("FollowUpAction"+val).checked==true)
	{
		document.getElementById("ClearanceEntry"+val).disabled = true;	
	}else
	{
		document.getElementById("ClearanceEntry"+val).disabled = false;	
	}	
}

function callme(sam)
{
        var amt = 0;
	var fg = 0;
	var fg1 = 0;
	var ii = 0;
        var dd=0;
  if ( document.getElementById("EntryFoundInPassBook"+sam).checked == true)
   {
   
   var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
            
    for ( var i = 0; i < rowcount; i++)
    {
            var r = tbody.rows[i];
            	if (document.getElementById("r_cheque_dd_no" + sam).value != "") {
                     
				if ((document.getElementById("r_cheque_dd_no" + i).value) == (document
						.getElementById("r_cheque_dd_no" + sam).value)) {
                                             
					fg1 = fg1 + 1;
					if (fg1 == 1) {
						ii = i;
					}
					r.bgColor = "#FFCCCC";
					document.getElementById("EntryFoundInPassBook" + i).checked = true;
					document.getElementById("ClearanceEntry" + i).checked = false;
                                        //var tt=(document.getElementById("dr_amount" + i).value + document.getElementById("cr_amount" + i).value);
                                    
					document.getElementById("Amt_in_PassBk" + i).value = (document.getElementById("dr_amount" + i).value + document.getElementById("cr_amount" + i).value);
//					
                    document.getElementById("Entry_Date" + i).value = (document.getElementById("w_date" + i).value + document
							.getElementById("r_date" + i).value);         
					var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
                                        //  alert(document.getElementById("Entry_Date").value);
                                      // if(document.getElementById("Entry_Date" + i).value==""){
                                    //  alert("oooo");
                                        /*   if(cmbBankAccNo==6722)
                                           {
                                           document.getElementById("Entry_Date" + i).value=document.getElementById("r_date" + i).value;
                                           }
                                           else
                                           {
                                             document.getElementById("Entry_Date" + i).value = document.getElementById("w_date" + i).value;
                                           }  */
                                      //   }
                                       
                                       // document.getElementById("Entry_Date" + i).value = (document.getElementById("w_date" + i).value + document.getElementById("r_date" + i).value);
					document.getElementById("Amt_Diff" + i).value = "0";
					document.getElementById("cmbReason4Diff" + i).disabled = true;
					if (fg1 > 1) {
						fg = 1;
						amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + i).value);
                                               // alert(amt);
					}
				}
			} else {
				document.getElementById("EntryFoundInPassBook" + sam).checked = true;
				document.getElementById("ClearanceEntry" + sam).checked = false;
				document.getElementById("Amt_in_PassBk" + sam).value = (document
						.getElementById("dr_amount" + sam).value + document
						.getElementById("cr_amount" + sam).value);
				//document.getElementById("Entry_Date" + sam).value = (document.getElementById("w_date" + sam).value + document.getElementById("r_date" + sam).value);
                                if(cmbBankAccNo==6722)
                                       {
                                       document.getElementById("Entry_Date" + sam).value=document.getElementById("r_date" + sam).value;
                                       }
                                       else
                                       {
                                         document.getElementById("Entry_Date" + sam).value = document.getElementById("w_date" + sam).value;
                                       }
                                
				document.getElementById("Amt_Diff" + sam).value = "0";
				document.getElementById("cmbReason4Diff" + sam).disabled = true;
			}
            
    }
    if (fg == 1) {
   
			amt = amt+ parseInt(document.getElementById("Amt_in_PassBk" + ii).value);
			alert("The Total Amount of the Cheque No"+ document.getElementById("r_cheque_dd_no" + sam).value + " is "+ amt);
		}
//	  document.getElementById("ClearanceEntry"+sam).checked = false;	
//      document.getElementById("Amt_in_PassBk"+sam).value = ( parseInt(document.getElementById("dr_amount"+sam).value) +  parseInt(document.getElementById("cr_amount"+sam).value) );
//      document.getElementById("Entry_Date"+sam).value = ( document.getElementById("r_date"+sam).value );   
//      document.getElementById("Amt_Diff"+sam).value = "0";   

                var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		var r = tbody.rows[sam];
   }
   else
   {
      /* document.getElementById("Amt_in_PassBk"+sam).value = "";
      document.getElementById("Entry_Date"+sam).value = "";         
      document.getElementById("Amt_Diff"+sam).value = "";   
      document.getElementById("FollowUpAction"+sam).checked = false;
      document.getElementById("FollowUpAction"+sam).disabled = false;  */
      
      var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			//r.bgColor = "rgb(255,255,225)";
			if (document.getElementById("r_cheque_dd_no" + sam).value != "") {
				if ((document.getElementById("r_cheque_dd_no" + i).value) == (document
						.getElementById("r_cheque_dd_no" + sam).value)) {
					//r.bgColor = "rgb(255,255,225)";
					document.getElementById("Amt_in_PassBk" + i).value = "";
					document.getElementById("Entry_Date" + i).value = "";
					document.getElementById("Amt_Diff" + i).value = "";
					document.getElementById("cmbReason4Diff" + i).disabled = false;
					document.getElementById("EntryFoundInPassBook" + i).checked = false;
					document.getElementById("FollowUpAction" + i).checked = false;
					document.getElementById("FollowUpAction" + i).readOnly = false;
				}
			} else {
				document.getElementById("Amt_in_PassBk" + sam).value = "";
				document.getElementById("Entry_Date" + sam).value = "";
				document.getElementById("Amt_Diff" + sam).value = "";
				document.getElementById("cmbReason4Diff" + sam).disabled = false;
				document.getElementById("EntryFoundInPassBook" + sam).checked = false;
				document.getElementById("FollowUpAction" + sam).checked = false;
				document.getElementById("FollowUpAction" + sam).readOnly = false;
			}
		}
      
   }
  
  var f = parseInt(document.getElementById("Amt_Diff"+sam).value);
	if(f <0)
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = false;
	  }else if(f >0)
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = false;
	  }else
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = true;
	  }
  
}

//joan on 24 July 2014
/*function dateValidation_PBDte(seq)
{
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var month=document.getElementById("txtCB_Month").value;
	var PassBk_dte=document.getElementById("Entry_Date" + seq).value;
	if(month.length<2){
		month="0"+month;}
	if(PassBk_dte.split("/")[1]!=month)
	{
		
		if(PassBk_dte.split("/")[2]!=txtCB_Year)
		{
		alert('"'+PassBk_dte+'"  PassBook date should be with in the  CashBook Month & CashBook Year ..... ');
	
	
	///	document.getElementById("Entry_Date" + seq).value=PassBk_dte.split("/")[0]+"/"+month+"/"+txtCB_Year;
		document.getElementById("Entry_Date" + seq).focus();
		}else{
			alert('"'+PassBk_dte+'" PassBook date should be with in the  CashBook Month  ..... ');
			
			
			//	document.getElementById("Entry_Date" + seq).value=PassBk_dte.split("/")[0]+"/"+month+"/"+txtCB_Year;
				document.getElementById("Entry_Date" + seq).focus();
			}
		
	}else if(PassBk_dte.split("/")[2]!=txtCB_Year)
	{
	alert('"'+PassBk_dte+'"  PassBook date should be with in the  CashBook Year ..... ');


///	document.getElementById("Entry_Date" + seq).value=PassBk_dte.split("/")[0]+"/"+month+"/"+txtCB_Year;
	document.getElementById("Entry_Date" + seq).focus();
	}
	else
	{
		
	}
	
}
*/
function dateValidation_PBDte(seq)
{
	
	//var txtCB_Year=document.getElementById("txtCB_Year").value;
	///var month=document.getElementById("txtCB_Month").value;
	var  txtCB_Month       = document.getElementById("hidMonth").value;   
	var  txtCB_Year      = document.getElementById("hidYear").value;
	var PassBk_dte=document.getElementById("Entry_Date" + seq).value;
	var r_cheque_dd_no1=document.getElementById("r_cheque_dd_no" + seq).value;
	var w_challan_no1=document.getElementById("w_challan_no" + seq).value;
	
		
	//alert(txtCB_Month.length);
	if(txtCB_Month.length==1){
	 txtCB_Month = "0"+txtCB_Month;
	}
	var dateFrom = "01/"+txtCB_Month+"/"+txtCB_Year;
	var dateTo = "31/"+txtCB_Month+"/"+txtCB_Year;
	
	//alert(PassBk_dte);
	var dateCheck = PassBk_dte;
//	alert(dateFrom+"   00' "+dateTo+"   "+dateCheck);

	var d1 = dateFrom.split("/");
	var d2 = dateTo.split("/");
	var c = dateCheck.split("/");

	var from = new Date(d1[2], d1[1]-1, d1[0]);  // -1 because months are from 0 to 11
	var to   = new Date(d2[2], d2[1]-1, d2[0]);
	var check = new Date(c[2], c[1]-1, c[0]);
	/*from = Date.parse(dateFrom);
	to = Date.parse(dateTo);
	check = Date.parse(c);*/

	//alert(">>>>> :::  "+from+"  >> ' "+to+"   "+check);
	
	/*if(month.length<2){
		month="0"+month;}*/
//console.log('********************************************************'+check > from && check < to)
	if(c[2]!=txtCB_Year){
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");	
		document.getElementById("Entry_Date" + seq).value="";
	}
	else if(c[1]!=txtCB_Month)
	{
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			
			
					if (document.getElementById("EntryFoundInPassBook" + i).checked == true && 
							document.getElementById("r_cheque_dd_no" + i).value==	r_cheque_dd_no1	 &&
							document.getElementById("w_challan_no" + i).value==	w_challan_no1)	{
								r.bgColor = "#FFCCCC";
								document.getElementById("Entry_Date" + i).value = "";
							}
						//}
			  }
		//document.getElementById("Entry_Date" + seq).value="";
	}
	
	
	/*if(to>check){
		alert('test .... ');
		alert(" PassBook date should be with in the Reconcilation Month  ..... ");
		return false;	
	}*/
	//alert('test final .... ');
	return true;	
	
}




/*

function dateValidation1() {
	var g = 0;
	var  txtCB_Year       = document.getElementById("hidMonth").value;   
	var  txtCB_Month      = document.getElementById("hidYear").value;
	if(txtCB_Month.length<2){
	 txtCB_Month = ("0"+txtCB_Month);
	}
	var dateFrom = "01/"+txtCB_Month+"/"+txtCB_Year;
	var dateTo = "31/"+txtCB_Month+"/"+txtCB_Year;
	var dateCheck = "02/07/2013";

	var d1 = dateFrom.split("/");
	var d2 = dateTo.split("/");
	var c = dateCheck.split("/");

	var from = new Date(d1[2], d1[1]-1, d1[0]);  // -1 because months are from 0 to 11
	var to   = new Date(d2[2], d2[1]-1, d2[0]);
	var check = new Date(c[2], c[1]-1, c[0]);

	//console.log(check > from && check < to)
	
	//var txtCB_Month1 = ("0"+txtCB_Month);
	
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;	
	for ( var i = 0; i < rowcount; i++) {
	if(document.getElementById("EntryFoundInPassBook"+g).checked == true)
	{
	    var Entry_Date = document.getElementById("Entry_Date"+g).value; 
	    var r_date = document.getElementById("r_date"+g).value;
	    var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = r_date.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
			var from = new Date(d1[2], d1[1]-1, d1[0]);  // -1 because months are from 0 to 11
			var to   = new Date(d2[2], d2[1]-1, d2[0]);
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		//var r_date1 = new Date(b[2], b[0] - 1, b[1]);  
		var from = new Date(d1[2], d1[1]-1, d1[0]);  // -1 because months are from 0 to 11
		var to   = new Date(d2[2], d2[1]-1, d2[0]);
		if(from>Entry_Date1 && to<Entry_Date1)
		
		{
			
		}
		

		if(document.getElementById("Entry_Date"+g).value == ""){
			alert("Enter Entry Date in the Field");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else if(Entry_Date1 < r_date1){
			alert("Entry Date Should Be Greater Than Remitance Date");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else if(dd1[2] != txtCB_Year){
			alert("Entry Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
			alert("Entry Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}					
	}g = g+1;
	}
	g = 0;
	return true;
}

*/


/*
function dateValidation_PBDte(seq)
{
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var month=document.getElementById("txtCB_Month").value;
	var PassBk_dte=document.getElementById("Entry_Date" + seq).value;
	if(month.length<2){
		month="0"+month;}
	if(PassBk_dte.split("/")[1]!=month)
	{
		
		if(PassBk_dte.split("/")[2]!=txtCB_Year)
		{
		alert('"'+PassBk_dte+'"  PassBook date should be with in the  CashBook Month & CashBook Year ..... ');
	
	
	///	document.getElementById("Entry_Date" + seq).value=PassBk_dte.split("/")[0]+"/"+month+"/"+txtCB_Year;
		document.getElementById("Entry_Date" + seq).focus();
		}else{
			alert('"'+PassBk_dte+'" PassBook date should be with in the  CashBook Month  ..... ');
			
			
			//	document.getElementById("Entry_Date" + seq).value=PassBk_dte.split("/")[0]+"/"+month+"/"+txtCB_Year;
				document.getElementById("Entry_Date" + seq).focus();
			}
		
	}else if(PassBk_dte.split("/")[2]!=txtCB_Year)
	{
	alert('"'+PassBk_dte+'"  PassBook date should be with in the  CashBook Year ..... ');


///	document.getElementById("Entry_Date" + seq).value=PassBk_dte.split("/")[0]+"/"+month+"/"+txtCB_Year;
	document.getElementById("Entry_Date" + seq).focus();
	}
	else
	{
		
	}
	
}*/





function dateValidation1() {
	var g = 0;
	var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
	var  txtCB_Month      = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0"+txtCB_Month);
	
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;	
	for ( var i = 0; i < rowcount; i++) {
	if(document.getElementById("EntryFoundInPassBook"+g).checked == true)
	{
	    var Entry_Date = document.getElementById("Entry_Date"+g).value; 
	    var r_date = document.getElementById("r_date"+g).value;
	    var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = r_date.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);  

		if(document.getElementById("Entry_Date"+g).value == ""){
			alert("Enter Entry Date in the Field");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else if(Entry_Date1 < r_date1){
			alert("Entry Date Should Be Greater Than Remitance Date");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else if(dd1[2] != txtCB_Year){
			alert("Entry Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}
		/*else if(dd1[1] != txtCB_Month1){
			alert("Entry Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}*/					
	}g = g+1;
	}
	g = 0;
	return true;
}

function dateValidation(seq)
{
	  var  startyear       = document.getElementById("txtCB_Year").value;   
      var  startmonth      = document.getElementById("txtCB_Month").value;
     
if (document.getElementById("EntryFoundInPassBook" + seq).checked == true) {
		var Entry_Datee1 = document.getElementById("Entry_Date" + seq).value;
		var test_entry_one=Entry_Datee1.split("/");
		//alert(":::not convert::::"+test_entry_one[1]);
		//alert("::::"+parseInt(test_entry_one[1]));
		if(startmonth==12)
		{
			if(test_entry_one[1]!=01 || test_entry_one[1]!=1){
			//	alert("paa");
			}
			else if(test_entry_one[2]!=parseInt(startyear+1))
			{
			//	alert("paa");
			}
			
		}
		else 
		{
			//2011 jan
			if(test_entry_one[2]!=startyear)
			{
				//alert("paa");
			}
			else if(test_entry_one[1]==startmonth)
			{
				//alert("paa");
			}
		}
		
		
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
             //   alert("gggg");
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			// r.bgColor = "rgb(255,255,225)";
			if (document.getElementById("r_cheque_dd_no" + seq).value != "") {
				if ((document.getElementById("r_cheque_dd_no" + i).value) == (document
						.getElementById("r_cheque_dd_no" + seq).value)) {
					r.bgColor = "#FFCCCC";
					document.getElementById("Entry_Date" + i).value = Entry_Datee1;
				}
			}
		}
	}
      var Entry_Date = document.getElementById("Entry_Date"+seq).value; 
      var r_date = document.getElementById("r_date"+seq).value;
      var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);

      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = r_date.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);   
		if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (document.getElementById("Entry_Date"+seq).value == "")){
			alert("Enter Entry Date in the Field");
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}else if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (Entry_Date1 < r_date1)){
			alert("Entry Date Should Be Greater Than Remitance Date");
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}
		else{
			return true;
		}
}

function callme1(sam)
{
	var f = parseInt(document.getElementById("Amt_Diff"+sam).value)	;
	if(f <0)
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = false;
	  }else if(f >0)
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = false;
	  }else
	  {		 
		  document.getElementById("cmbReason4Diff"+sam).disabled = true;
	  }
}

/*DATE VALIDATION NEW JOan 12 DEc2014*/

function dateValidationNEW(seq) {
	
	//alert('TESTING');

	var Entry_Date = document.getElementById("Entry_Date" + seq).value;
	var r_date = document.getElementById("r_date" + seq).value;
	
	
	//var w_date = document.getElementById("w_date" + seq).value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0" + txtCB_Month);

	var browser = navigator.appName;

	if (browser == "Netscape") {
		var dd1 = Entry_Date.split('/');
		Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
		var dd2 = r_date.split('/');
		r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
	/*	var dd3 = w_date.split('/');
		w_date = dd3[1] + "/" + dd3[0] + "/" + dd3[2];*/
	}
	var a = Entry_Date.split('/');
	var b = r_date.split('/');
	//var c = w_date.split('/');

	var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
	var r_date1 = new Date(b[2], b[0] - 1, b[1]);
	//var w_date1 = new Date(c[2], c[0] - 1,c[1]);
	
	
	if ((document.getElementById("EntryFoundInPassBook" + seq).checked == true)
			&& (document.getElementById("Entry_Date" + seq).value == "")) {
		alert("Enter PassBook Date in the Field");
		document.getElementById("Entry_Date" + seq).focus();
		return false;
	} else if ((document.getElementById("EntryFoundInPassBook" + seq).checked == true)
			&& (Entry_Date1 < r_date1)) {
		alert("PassBook Date Should Be Greater Than Remitance Date");
		document.getElementById("Entry_Date" + seq).focus();
		return false;
	} 
	/*else if ((document.getElementById("EntryFoundInPassBook" + seq).checked == true)
			&& (Entry_Date1 < w_date1)) {
		alert("PassBook Date Should Be Greater Than WithDrawal Date...");
		document.getElementById("Entry_Date" + seq).value="";
		document.getElementById("Entry_Date" + seq).focus();
		return false;
	}*/
	else {
		if (document.getElementById("EntryFoundInPassBook" + seq).checked == true) {
			var Entry_Datee1 = document.getElementById("Entry_Date" + seq).value;
			var r_cheque_dd_no1 = document.getElementById("r_cheque_dd_no" + seq).value;
			var w_challan_no1 = document.getElementById("w_challan_no" + seq).value;
			
			var tbody = document.getElementById("grid_body_TWAD");
			var rowcount = tbody.rows.length;
			for ( var i = 0; i < rowcount; i++) {
				var r = tbody.rows[i];
				//if
				var doctype_one=document.getElementById("doc_type" + seq).value;
				//alert("doctype_one :: "+doctype_one);
				//alert("EntryFoundInPassBook :: "+document.getElementById("EntryFoundInPassBook" + i).checked);
				if(doctype_one!='CR')
				 {
				
							/*if (document.getElementById("ccdd_no" + seq).value != "") {
								if ((document.getElementById("ccdd_no" + i).value) == (document
										.getElementById("ccdd_no" + seq).value)) {*/
					if (document.getElementById("EntryFoundInPassBook" + i).checked == true && 
							document.getElementById("r_cheque_dd_no" + i).value==	r_cheque_dd_no1	 &&
							document.getElementById("w_challan_no" + i).value==	w_challan_no1			
					){
									r.bgColor = "#FFCCCC";
									document.getElementById("Entry_Date" + i).value = Entry_Datee1;
								}
							//}
				  }
				else
				  {
							if (document.getElementById("r_w_no" + seq).value != "") 
							{
								
								/*	if ((document.getElementById("r_w_no" + i).value) == (document
											.getElementById("r_w_no" + seq).value))
								{*/
								if (document.getElementById("EntryFoundInPassBook" + i).checked == true && 
										document.getElementById("r_cheque_dd_no" + i).value==	r_cheque_dd_no1	 &&
										document.getElementById("w_challan_no" + i).value==	w_challan_no1)	{
									
									r.bgColor = "#FFCCCC";
									document.getElementById("Entry_Date" + seq).value = Entry_Datee1;
								}
								
							}
					
				  }
			}
		}
		return true;
	}
}


/** Difference Calculate */
function callDifference(indexID)
{
	var cramt=0;
	var dramt=0;
//	if(document.getElementById("dr_amount"+indexID).value == "")
//	{
//		document.getElementById("dr_amount"+indexID).value ==0;
//	}
	if(document.getElementById("cr_amount"+indexID).value=="")
	{
		
		cramt=0;
	}
	else{
		cramt=parseInt(document.getElementById("cr_amount"+indexID).value);
	}
	if(document.getElementById("dr_amount"+indexID).value == "")
	{
		dramt=0;
	}
	else
	{
		dramt=parseInt(document.getElementById("dr_amount"+indexID).value);
	}
	
  var RWamt   = (cramt+dramt) ; 
  //alert("RWamt::"+RWamt);
  var Passamt = document.getElementById("Amt_in_PassBk"+indexID).value ; 
  document.getElementById("Amt_Diff"+indexID).value =  RWamt - parseInt(Passamt) ;   
  if((RWamt - parseInt(Passamt)) != 0)
  {
  document.getElementById("FollowUpAction"+indexID).checked = true;
  document.getElementById("FollowUpAction"+indexID).disabled = true;
  }
}

function checkNull()
{
  var count_two=0;
        var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
        if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_MainForm_Create_Pre_Mnth.txtCB_Year.focus();
		return false;
	} 
                 var tbody = document.getElementById("grid_body_TWAD");
                 var rowcount = tbody.rows.length;
                 for ( var i = 0; i < rowcount; i++) {
                if (document.getElementById("EntryFoundInPassBook" + i).checked == true) {
			//var r = tbody.rows[i];
                	
                	if((document.getElementById("Entry_Date" + i).value=="")||(document.getElementById("Entry_Date" + i).value==null)){
                		alert('Enter the PassBook ... ');
                		document.getElementById("Entry_Date" + i).focus();
                		return false;
                	}
                	var dateCheck=document.getElementById("Entry_Date" + i).value;
                	
                	var c = dateCheck.split("/");
                	if(c[2]!=txtCB_Year){
                		alert(" PassBook date should be with in the Reconcilation Month  ..... ");	
                		document.getElementById("Entry_Date" + seq).value="";
                		return false;
                	}
                	else if(c[1]!=txtCB_Month)
                	{
                		alert(" PassBook date should be with in the Reconcilation Month  ..... ");
                		var tbody = document.getElementById("grid_body_TWAD");
                		var rowcount = tbody.rows.length;
                		for ( var i = 0; i < rowcount; i++) {
                			var r = tbody.rows[i];
                			
                			
                					if (document.getElementById("EntryFoundInPassBook" + i).checked == true && 
                							document.getElementById("r_cheque_dd_no" + i).value==	r_cheque_dd_no1	 &&
                							document.getElementById("w_challan_no" + i).value==	w_challan_no1)	{
                								r.bgColor = "#FFCCCC";
                								document.getElementById("Entry_Date" + i).value = "";
                								return false;
                							}
                						
                			  }
                	}
                
                	
                	
                	
                	
                	

                	if(document.getElementById("Amt_Diff" + i).value!=0)
                		{
                		alert("Please Select Reason for Difference");
                		document.getElementById("Amt_Diff" + i).focus();
                        return false;
                		}
                	
                	
			if (document.getElementById("r_cheque_dd_no" + i).value != "") {
                       
				
                                           //dhana   
					var txtCB_Year=document.getElementById("txtCB_Year").value;
                                        var txtCB_Month=document.getElementById("txtCB_Month").value;
                                        if(txtCB_Month==12)
                                        {
                                        	 var incMonth=01;
                                        	 txtCB_Year=parseInt(txtCB_Year)+1;
                                        }
                                        else
                                        {
                                        	// joe change on 21 Aug 2014
                                        	 //var incMonth=parseInt(txtCB_Month)+1;
                                        	var incMonth=parseInt(txtCB_Month);
                                        }
                                      // alert("incMonth::"+incMonth);
                                       var entry_check=document.getElementById("Entry_Date" + i).value;
                                        var test_entry_one=entry_check.split("/");
                                       // alert(test_entry_one+" year cb >> "+txtCB_Year+"  incMonth  "+incMonth);
                                        if(test_entry_one[2]>txtCB_Year)
                                        {
                                       // alert("2:::");
                                         count_two++;
                                        }
                                        else if(test_entry_one[2]<txtCB_Year)
                                        { 
                                        	if(test_entry_one[1]==1)
                                            {
                                        	
                                           
                                            }
                                        	else if(test_entry_one[1]==01)
                                            {
                                        	
                                           
                                            }
                                        	else
                                        	{
                                        		 count_two++;
                                        	}
                                        }
                                        else if(test_entry_one[2]==txtCB_Year)
                                        {
                                        	if(test_entry_one[1]!=incMonth)
                                            {
                                        	//	alert("4:::");
                                            count_two++;
                                            }	
                                        	 
                                        }
                                        
                                   
					
					
				
			} 
                        }//me
		}
		/*if(count_two>0)
                {
               
                alert("date :::::"+count_two);
                alert("PassBookDate Should be equal to CashBook Month and Year**");
                  return false;
                }
                else{ 
                 document.getElementById('RecordCount').value=seq;
                 return true;
              //  alert(seq);
                }*/
                 
 return true;
}
function calins_new(e,t)
{
	
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

function clears()
{	
	var tbody=document.getElementById("grid_body_TWAD");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
	    
    LoadAccountingUnitID('LIST_ALL_UNITS');		
   document.getElementById("cmbBankAccNo").value="";
  // document.getElementById("txtPBBalance").value="";
}

function exit()
{
   self.close();
}