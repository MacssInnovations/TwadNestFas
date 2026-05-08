//			BRS_Create_From_Journal			//

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


function LoadMonthYear() {

	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;
	//alert("url::::"+url);
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
			if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			}
		}
	}
}


function LoadMonthYear1(baseResponse) {
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
		
		document.getElementById("txtCB_Year").value=CB_Year;
		document.getElementById("txtCB_Year").readOnly="true";
	
		document.getElementById("txtCB_Month").value=CB_Month;
		
		document.getElementById("txtCB_Month").setAttribute('readonly','readonly');
		
		
	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
	} else {
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
	
	var txtOprMode = document.getElementById("txtOprMode").value;
	var txtBankID = document.getElementById("txtBankID").value;
	var txtBranchID = document.getElementById("txtBranchID").value;
	
	if (filter == "CashbookYrMnth") {
		var filterflag = "CashbookYrMnth";
		var url = "../../../../../BRS_Create_From_Journal.kv?Command=LoadTWADTransactions&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode
				+ "&cmbOffice_code="
				+ cmbOffice_code
				+ "&txtCB_Year="
				+ txtCB_Year
				+ "&txtCB_Month="
				+ txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&txtOprMode=" + txtOprMode + "&txtBankID=" + txtBankID + "&txtBranchID=" + txtBranchID;
	//alert("url >>>  "+url);
		if (txtCB_Year == "") {
			alert("Enter Cash Book Year in the Field");
			document.frmBRS_Create_From_Journal.txtCB_Year.focus();
		} else if (txtCB_Month == "") {
			alert("Enter Cash Book Month in the Field");
			document.frmBRS_Create_From_Journal.txtCB_Month.focus();
		} else if (cmbBankAccNo == "") {
			alert("Enter Bank Account No in the Field");
			document.frmBRS_Create_From_Journal.cmbBankAccNo.focus();
		} else
			{
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		document.getElementById("imgfld").style.visibility = "visible"
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
		document.getElementById("imgfld").style.visibility = "hidden";
		/** Delete Existing Values from Grid */
    	var tbody1=document.getElementById("grid_body_TWAD");
        var t=0;
        for(t=tbody1.rows.length-1;t>=0;t--)
        {
           tbody1.deleteRow(0);
        }
	//var len6 = baseResponse.getElementsByTagName("r_cheque_dd_no").length;
        var len6 = baseResponse.getElementsByTagName("doc_no").length;
	if(len6!=0)
	{
	for ( var k = 0; k < len6; k++) {

		var r_date = baseResponse.getElementsByTagName("r_date")[k].firstChild.nodeValue;
		var w_date = baseResponse.getElementsByTagName("w_date")[k].firstChild.nodeValue;
		var w_challan_no = baseResponse.getElementsByTagName("w_challan_no")[k].firstChild.nodeValue;
		var r_cheque_dd_no = baseResponse.getElementsByTagName("r_cheque_dd_no")[k].firstChild.nodeValue;
		if(r_cheque_dd_no=='null')
			{
			r_cheque_dd_no="";
			}
		var cr_amount = baseResponse.getElementsByTagName("cr_amount")[k].firstChild.nodeValue;
		var dr_amount = baseResponse.getElementsByTagName("dr_amount")[k].firstChild.nodeValue;
		var doc_no = baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;
		var doc_type = baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;
		if(doc_type=='null')
		{
			doc_type="";
		}
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
      	  sel=document.createElement("<INPUT type='checkbox' name='EntryFoundInPassBook"+seq+"' id='EntryFoundInPassBook"+seq+"' value='Y' onclick='callme("+seq+")' />" );                       
        }
        else
        {    
          sel=document.createElement("input");     
          sel.type="checkbox";             
          sel.name="EntryFoundInPassBook"+seq;
          sel.id="EntryFoundInPassBook"+seq;    
          sel.setAttribute('onclick',"callme("+seq+")");
          sel.value="Y";                          
        }
        cell8.appendChild(sel);
        mycurrent_row.appendChild(cell8);
	     
        /** Entry Date */
        var cell9=document.createElement("TD"); 
        var Entry_Date=document.createElement("input");
        Entry_Date.type="hidden";
        Entry_Date.name="Entry_Date"+seq;
        Entry_Date.id="Entry_Date"+seq;             
        Entry_Date.value=w_date;
        cell9.appendChild(Entry_Date);
        var currentText=document.createTextNode(w_date);
        cell9.appendChild(currentText);
        mycurrent_row.appendChild(cell9);
        /*
        var cell9=document.createElement("TD");  
        var Entry_Date=document.createElement("input");
        Entry_Date.type="hidden";
        Entry_Date.name="Entry_Date"+seq;
        Entry_Date.id="Entry_Date"+seq;
        Entry_Date.value=r_date;
        Entry_Date.maxLength="10";
        Entry_Date.setAttribute('onblur',"dateValidation("+seq+")");
        Entry_Date.size="10";
        cell9.appendChild(Entry_Date);
        mycurrent_row.appendChild(cell9);  */ 
        
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
        cell12=document.createElement("TD");
        cell12.style.textAlign='center';  
        var cmbCategoryCode;
        
        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
        { try
          {
            cmbCategoryCode =  document.createElement("<select name='cmbReason4Diff"+seq+"' id='cmbReason4Diff' >");
          }catch( e ) { alert(e.description) }
        }
        else
        {
           cmbCategoryCode=document.createElement("select");                        
         //  cmbCategoryCode.id="cmbReason4Diff";
           
         //cmbCategoryCode="cmbReason4Diff";
           cmbCategoryCode.id="cmbReason4Diff"+seq;
           
           cmbCategoryCode.name="cmbReason4Diff"+seq;
        }
        
        var cmbCategoryCodeObj = baseResponse.getElementsByTagName("reason_pair");
        var option11=document.createElement("option");    
            option11.value="";  
            option11.text="--Select--";
            
       try
         {
            cmbCategoryCode.add(option11);
         }
      catch(e)
         {
             cmbCategoryCode.add(option11,null);
         }                      
      
      
        for(var y=0;y<cmbCategoryCodeObj.length;y++)
        {
        
            CatCode[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_code")[0].firstChild.nodeValue;
            CatDesc[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_desc")[0].firstChild.nodeValue;
           
            
            var option11=document.createElement("option");    
            
            option11.value=CatDesc[y];  
            option11.text=CatDesc[y];
            
          
           try
             {
                 cmbCategoryCode.add(option11);
             }
           catch(e)
             {
                  cmbCategoryCode.add(option11,null);
             }
             
       }           
        
        
       cell12.appendChild(cmbCategoryCode);                                                    
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
     
        /* entry based on follow-up */             
        var cell14=document.createElement("TD");
        cell14.style.textAlign='center';
        var clearent="";            
        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
        {
            clearent=document.createElement("<INPUT type='checkbox' name='ClearanceEntry"+seq+"' id='ClearanceEntry"+seq+"' onclick='list_new("+seq+")' value='Y'/>" );                       
        }
        else
        {    
        	clearent=document.createElement("input");     
        	clearent.type="checkbox";             
        	clearent.name="ClearanceEntry"+seq;
        	clearent.id="ClearanceEntry"+seq;
        	clearent.checked=false;
        	clearent.value="Y";
        	clearent.setAttribute("onclick","list_new("+seq+")");
          
        }
        cell14.appendChild(clearent);
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
        cell4.style.visibility="hidden"
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
        cell44.style.visibility="hidden"
        mycurrent_row.appendChild(cell44);
        
        /** doc Date */ 
     	var doc_date = document.createElement("input");
     	doc_date.setAttribute("type","hidden");
     	doc_date.setAttribute("value",doc_date1);
     	doc_date.setAttribute("name","doc_date"+seq);
     	doc_date.setAttribute("id","doc_date"+seq);
     	document.getElementById("frmBRS_Create_From_Journal").appendChild(doc_date); 
     	
		tbody.appendChild(mycurrent_row);
		seq=seq+1;  			
//		alert("seq"+seq);
//		alert("k"+k);
//		alert("len6"+len6);		
	}
				
	} else{		
		alert("Record Does Not Exist");
	}
	}
	else
	{
	alert("Failed to Load");
}	
	//alert("seq"+seq);
	document.getElementById('txtNoofRecords').value=seq;		
	seq=0;
}


var winemp;
function list_new(val)
{	
	
	
	var ind="";
	if(document.getElementById("ClearanceEntry"+val).checked==true)
	{
		document.getElementById("FollowUpAction"+val).disabled = true;
		
		var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
		   var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
		   var  txtCB_Year       = document.getElementById("txtCB_Year").value;
		   var  txtCB_Month      = document.getElementById("txtCB_Month").value;
		   var  cmbBankAccNo      = document.getElementById("cmbBankAccNo").value;
		   var Amt_in_pasbook = document.getElementById("Amt_in_PassBk"+val).value;
		   if(document.getElementById("dr_amount"+val).value!=0)
		   {
			   ind="CR";//fetch cr datas in popup
		   }
		   else
		   {
			   ind="DR";//fetch dr datas in popup
		   }
			   
			  
		   var doctype='J'; 
		   var docno=document.getElementById("doc_no"+val).value;
		   var docdate=document.getElementById("doc_date"+val).value;
		  // alert("docdate:::"+docdate);
		  // alert("ff:::"+document.getElementById("EntryFoundInPassBook"+val).checked);
		   if(document.getElementById("EntryFoundInPassBook"+val).checked==true)
		   {
			   
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
			  
	        winemp= window.open("BRS_Clearance_Entry.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&Amt_in_pasbook="+Amt_in_pasbook+"&doctype="+doctype+"&docno="+docno+"&docdate="+docdate+"&indicator="+ind,"list","status=1,height=450,width=800,resizable=YES, scrollbars=yes");	
	        winemp.moveTo(200,200);  
	        winemp.focus();
		   }else
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
  if ( document.getElementById("EntryFoundInPassBook"+sam).checked == true)
   {
	  document.getElementById("ClearanceEntry"+sam).checked = false;	
      document.getElementById("Amt_in_PassBk"+sam).value = ( parseInt(document.getElementById("dr_amount"+sam).value) +  parseInt(document.getElementById("cr_amount"+sam).value) );
      document.getElementById("Entry_Date"+sam).value = ( document.getElementById("r_date"+sam).value );   
      document.getElementById("Amt_Diff"+sam).value = "0";   
   }
   else
   {
      document.getElementById("Amt_in_PassBk"+sam).value = "";
      document.getElementById("Entry_Date"+sam).value = "";         
      document.getElementById("Amt_Diff"+sam).value = "";   
      document.getElementById("FollowUpAction"+sam).checked = false;
      document.getElementById("FollowUpAction"+sam).disabled = false;
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
		}/*else if(dd1[2] != txtCB_Year){
			alert("Entry Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date"+g).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
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
		}/*else if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (dd1[2] != txtCB_Year)){
			alert("Entry Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}else if((document.getElementById("EntryFoundInPassBook"+seq).checked == true) && (dd1[1] != txtCB_Month1)){
			alert("Entry Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Entry_Date"+seq).focus();
			return false;
		}*/else{
			return true;
		}
}

function callme1(sam)
{
	var f = parseInt(document.getElementById("Amt_Diff"+sam).value)	
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



/** Difference Calculate */
function callDifference(indexID)
{
	if(document.getElementById("dr_amount"+indexID).value == "")
	{
		document.getElementById("dr_amount"+indexID).value ==0;
	}
  var RWamt   = ( parseInt(document.getElementById("dr_amount"+indexID).value) +  parseInt(document.getElementById("cr_amount"+indexID).value) ) ; 
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
  document.getElementById('RecordCount').value=seq;
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
   document.getElementById("txtPBBalance").value="";
}

function exit()
{
   self.close();
}