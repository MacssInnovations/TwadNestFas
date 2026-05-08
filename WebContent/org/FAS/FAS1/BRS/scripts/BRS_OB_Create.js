var seq=0;
var seqNT=0;

var CatCode = new Array();
var CatDesc = new Array();


/** Get Browser Object */
function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}



var Common_branchID="";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

var window_BankAccNumber;

function ListHeads()
{ 
 
 
        if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
         {
             window_BankAccNumber.resizeTo(500,500);
             window_BankAccNumber.moveTo(250,250); 
             window_BankAccNumber.focus();
         }
         else
         {
             window_BankAccNumber=null
         }
         window_BankAccNumber= window.open("BRS_Reason_Catalogue_List.jsp","mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
 
}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doParentBankAccNumbers(reason_code,reason_short_desc, reason_desc )
{
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
       
    document.getElementById("txtReasonCode").value=reason_code;
    document.getElementById("txtReasonShortDesc").value=reason_short_desc;
    document.getElementById("txtReasonDesc").value=reason_desc;            
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function clr(path)
{
        //alert(path);
	//document.getElementById("txtPBBalance").value="";		
	document.frmBRS_OB_Create.Add.disabled = false;
        
        checkCount(path);
        
}  
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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

function checkCount(path)
{
        //alert("last");
        var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
        var url = path+ "/BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code;

	
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate_onload(xmlrequest);
	}
	xmlrequest.send(null);
}

function brs_ob_status()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;

	var url ="../../../../../BRS_OB_Create?Command=ob_freeze&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbBankAccNo=" + cmbBankAccNo;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("GET", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function cashbookYear_mon()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var url ="../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
	+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

	//	alert(url);
		var xmlrequest = AjaxFunction();
		
		xmlrequest.open("POST", url, true);
		
		xmlrequest.onreadystatechange = function() 
		{
			if (xmlrequest.readyState == 4) {
				if (xmlrequest.status == 200) {

					var baseResponse = xmlrequest.responseXML
							.getElementsByTagName("response")[0];

					var tagCommand = baseResponse.getElementsByTagName("command")[0];

					var command = tagCommand.firstChild.nodeValue;
					if (command == "LoadMonthYear") {
						var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
					       
						if (flag == "success") {
							var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
							var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
							document.getElementById("txtCB_Year").value=CB_Year1;
							document.getElementById("txtCB_Month").value=(CB_Month1-1);
							
							brs_ob_status();
						}
						else
						{
							alert("First Set BRS Initiation Month and Year");
						}
					}
					
				} 
			}
			
		}
		xmlrequest.send(null);
}

function LoadMonthYear(path) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_FollowUp.txtCB_Year.focus();
	}else{
	var url = path
			+ "/BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	}
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			}
			else if (command == "ob_freeze") {
                
				obFreeze(baseResponse);
			}
		}
	}
}

function manipulate_onload(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "LoadMonthYear") {
                       
				count_test(baseResponse);
			}
			
		} 
	}
}

function obFreeze(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if (flag == "success") {
    	alert("BRS OB is Already Freezed");
    document.getElementById("firstid").style.display="none";
    document.getElementById("secondid").style.display="block";
    }
    else 
    {
    
    	document.getElementById("firstid").style.display="block";
        document.getElementById("secondid").style.display="none";
    } 
}

function count_test(baseResponse) 
       {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if (flag == "success") {
        document.frmBRS_OB_Create.Go.disabled = false;
        }
        else if(flag == "NoData")
        {
        alert("No data Found For startmonth and year");
       
        document.frmBRS_OB_Create.Go.disabled = true;
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
              //  alert(txtCB_Year+"::::"+txtCB_Year1);
		 if(CB_Year1<txtCB_Year1)
                {
                alert("Cashook Year should be less than start month and year");
                document.getElementById("txtCB_Year").value="";
                }
                else if(CB_Year1>txtCB_Year1)
                {
                
                }
                else if(CB_Year1==txtCB_Year1)
                {
            // var txtCB_Month_text = document.getElementById("txtCB_Month").value;
               //alert("txtCB_Month_text"+txtCB_Month_text+"txtCB_Month"+txtCB_Month);
                    if(CB_Month1<=txtCB_Month)
                    {
                    alert("Cashook Month should be less than start month and year");
                     document.getElementById("txtCB_Month").value="s";
                    }
                    
                }
//                CB_Year = parseInt(CB_Year1);
//		CB_Month = parseInt(CB_Month1);               
		/* if((txtCB_Year==CB_Year) && (CB_Month >=6)){               
			if((CB_Month - txtCB_Month) <= 6)
			{
                        
			}else{
				alert("Cashook Year and Month Should be Within Six Months of the BRS Start Month and Year");
				document.getElementById("txtCB_Year").value = "";
				document.getElementById("txtCB_Month").value = "s";
			}
		}else if((txtCB_Year==CB_Year) && (CB_Month <6)){
			if((CB_Month - txtCB_Month) < CB_Month)	
			{
				alert("RK1");
			}			
			else
			{
                        alert("RK");
				alert("Cashook Year and Month Should be Within Six Months of the BRS Start Month and Year");
				document.getElementById("txtCB_Year").value = "";
				document.getElementById("txtCB_Month").value = "s";
			}
		}
                else if((txtCB_Year!=CB_Year) && (CB_Month <6)){
			if(((CB_Year - 1) == txtCB_Year) && ((CB_Month+6) < txtCB_Month))
			{
				
			}else
			{
				alert("Cashook Year and Month Should be Within Six Months of the BRS Start Month and Year");
				document.getElementById("txtCB_Year").value = "";
				document.getElementById("txtCB_Month").value = "s";
			}
		}  */
	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");		
	} else {
		alert("Failed to Load Month and Year");		
	}
}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/**
 *   Main Function 
 */

function doFunction_brs(Command1, ListSeq)
{   
	//alert(Command1);
	 // alert(ListSeq);	
            
	  var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
	   var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
	   var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
	   var  txtCB_Month      = document.getElementById("txtCB_Month").value;
	   var  cmbBankAccNo      = document.getElementById("cmbBankAccNo").value;
	   
        if( Command1 == "LoadTWADTransactions" )
        {
        	 var url="../../../../../BRS_OB_Create?Command=LoadTWADTransactions&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
             "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;            
	
        	 //alert(url);
     var req=getTransport();
     req.open("GET",url,true);      
     document.getElementById("imgfld").style.visibility = "visible";
     req.onreadystatechange=function()
     {                	                	                  	                	
        handleResponse_BRS(req);
     }                         
        req.send(null);          
        }
        
        if(Command1 == "LoadNONTWADTransactions")
        {
                var url="../../../../../BRS_OB_Create?Command=LoadNONTWADTransactions&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&ListSeq="+ListSeq;            
                //alert(url);
                var req=getTransport();                
                req.open("GET",url,true); 
                //document.getElementById("imgfld").style.visibility = "visible"
                req.onreadystatechange=function()
                {
                   handleResponse_BRS(req);
                }   
                   req.send(null);
        }

}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   handleResponse()   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function handleResponse_BRS(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;           
           
            if(Command=="LoadTWADTransactions")
            {
            	LoadTWADTransactions(baseResponse);
            }      
            if(Command=="TotalTransactions")
            {
            	TotalTransactions(baseResponse);
            }
            if(Command=="LoadNONTWADTransactions")
            {            	
            	LoadNONTWADTransactions(baseResponse);
            }
            if(Command=="LoadGrid")
            {            	
            	LoadGrid1(baseResponse);
            }
        }
    }
}
//----------------------------------------------------------------------------------------------
function LoadTWADTransactions(baseResponse)
{
	document.getElementById('filterFlag').value="T";
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
    
       /* Load TWAD Trasactions  Values */    	
    	
    	/** Delete Existing Values from Grid */
    	var tbody=document.getElementById("grid_body_TWAD");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        /*var tbody1=document.getElementById("grid_body_NONTWAD");
        var t1=0;
        for(t1=tbody1.rows.length-1;t1>=0;t1--)
        {
           tbody1.deleteRow(0);
        }*/
        //document.frmBRS_OB_Create.Add.disabled = false;
        /** Get DD Number Object for finding Total Number of Records */
        var r_no=baseResponse.getElementsByTagName("r_no");
        seq=0;
        var item=new Array();
        for(var k=0;k<r_no.length;k++)
        {
        	  item[0]=baseResponse.getElementsByTagName("r_challan_no")[k].firstChild.nodeValue;
              item[1]=baseResponse.getElementsByTagName("r_challan_date")[k].firstChild.nodeValue;
              if(item[1] == 'null' || item[1] == '0' )
      	{
      		item[1]="";
      	}
              
      	item[2]=baseResponse.getElementsByTagName("r_no")[k].firstChild.nodeValue;
      	if(item[2] == 'null' || item[2] == '0' )
      	{
      		item[2]="";
      	}      
           	item[3]=baseResponse.getElementsByTagName("r_date")[k].firstChild.nodeValue;
      	if(item[3] == 'null' || item[3] == '0' )
      	{
      		item[3]="";
      	}      
      	item[4]=baseResponse.getElementsByTagName("r_cheque_or_dd")[k].firstChild.nodeValue;
              if(item[4] == 'null' || item[4] == '0' )
      	{
      		item[4]="";
      	}                      
      	item[5]=baseResponse.getElementsByTagName("r_cheque_dd_no")[k].firstChild.nodeValue;
             	if(item[5] == 'null' || item[5] == '0' )
      	{
      		item[5]="";
      	}
              item[6]=baseResponse.getElementsByTagName("r_cheque_dd_date")[k].firstChild.nodeValue;
           	item[7]=baseResponse.getElementsByTagName("cr_amount")[k].firstChild.nodeValue;
      	if(item[7] == 'null' || item[7] == '0' )
      	{
      		item[7]="";
      	}
      	item[8]=baseResponse.getElementsByTagName("r_particulars")[k].firstChild.nodeValue;
      	
              
              
              
      	item[9]=baseResponse.getElementsByTagName("w_challan_no")[k].firstChild.nodeValue;
              item[10]=baseResponse.getElementsByTagName("w_challan_date")[k].firstChild.nodeValue;
      	item[11]=baseResponse.getElementsByTagName("w_no")[k].firstChild.nodeValue;
      	if(item[11] == 'null' || item[11] == '0' )
      	{
      		item[11]="";
      	}      
           	item[12]=baseResponse.getElementsByTagName("w_date")[k].firstChild.nodeValue;
      	if(item[12] == 'null' || item[12] == '0' )
      	{
      		item[12]="";
      	}                      
      	item[13]=baseResponse.getElementsByTagName("w_cheque_or_dd")[k].firstChild.nodeValue;
              if(item[13] == 'null' || item[13] == '0' )
      	{
      		item[13]="";
      	}                      
              
      	item[14]=baseResponse.getElementsByTagName("w_cheque_dd_no")[k].firstChild.nodeValue;
             	if(item[14] == 'null' || item[14] == '0' )
      	{
      		item[14]="";
      	}
              item[15]=baseResponse.getElementsByTagName("w_cheque_dd_date")[k].firstChild.nodeValue;
           	item[16]=baseResponse.getElementsByTagName("dr_amount")[k].firstChild.nodeValue;
      	if(item[16] == 'null' || item[16] == '0' )
      	{
      		item[16]="";
      	}
      	item[17]=baseResponse.getElementsByTagName("w_particulars")[k].firstChild.nodeValue;
              
              item[18]=baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;    
              
              item[19]=baseResponse.getElementsByTagName("com_doc_no")[k].firstChild.nodeValue;    
              if(item[19] == 'null' || item[19] == '0' )
      	{
      		item[19]="";
      	}
              item[20]=baseResponse.getElementsByTagName("com_doc_date")[k].firstChild.nodeValue;   
              //alert("item[20]"+item[20]);
              if(item[20] == 'null' || item[20] == '0' )
      	{
      		item[20]="";
      	}
              
              item[21]=baseResponse.getElementsByTagName("com_cheque_dd_no")[k].firstChild.nodeValue;    
              if(item[21] == 'null' || item[21] == '0' )
      	{
      		item[21]="";
      	}                               
  			
          	/** Create Table Row */
      	var mycurrent_row=document.createElement("TR");         
      	mycurrent_row.id=seq;
             
      	/** Remittance Date Creation */
    	var cell2 = document.createElement("TD");
    	var r_date=document.createElement("input");
    	r_date.type="text";
    	r_date.name="r_date"+seq;
        r_date.id="r_date"+seq;
    	r_date.value=item[1];      	
    	r_date.size="11";
    	r_date.readOnly = true;
    	r_date.setAttribute('onblur',"dateValidation_T1("+seq+")");
    	cell2.appendChild(r_date);
    	mycurrent_row.appendChild(cell2);
         
      
        /** WithDrawl Date */
         var cell3=document.createElement("TD"); 
         var w_date=document.createElement("input");
         w_date.type="text";
         w_date.name="w_date"+seq;
         w_date.id="w_date"+seq;             
         w_date.value=item[12];           
         w_date.size="11";
         w_date.readOnly = true;
         w_date.setAttribute('onblur',"dateValidation_T2("+seq+")");
         cell3.appendChild(w_date);
         mycurrent_row.appendChild(cell3);   
         
         /** Challan or Voucher Number Creation */
         var cell4=document.createElement("TD");
         var r_w_no=document.createElement("input");
         r_w_no.type="text";
         r_w_no.name="r_w_no"+seq;
         r_w_no.id="r_w_no"+seq;
         r_w_no.value=item[19];    
         r_w_no.size="10";
         r_w_no.readOnly = true;
         cell4.appendChild(r_w_no);
         mycurrent_row.appendChild(cell4);
         
         /** Cheque / DD Number */              
         var cell5=document.createElement("TD");
         var ccdd_no=document.createElement("input");
         ccdd_no.type="text";
         ccdd_no.name="ccdd_no"+seq;
         ccdd_no.id="ccdd_no"+seq;
         ccdd_no.value=item[21];
         ccdd_no.size="10";
         ccdd_no.readOnly = true;
         cell5.appendChild(ccdd_no);
         mycurrent_row.appendChild(cell5);

         /** doc_no */              
         var cell51=document.createElement("TD");
         var doc_no=document.createElement("input");
         doc_no.type="text";
         doc_no.name="doc_no"+seq;
         doc_no.id="doc_no"+seq;
         doc_no.value=item[2]+item[11]        
         doc_no.size="10";
         doc_no.readOnly = true;
         cell51.appendChild(doc_no);
         mycurrent_row.appendChild(cell51);
         
         /** doc_type */              
         var cell52=document.createElement("TD");
         var doc_type=document.createElement("input");
         doc_type.type="text";
         doc_type.name="doc_type"+seq;
         doc_type.id="doc_type"+seq;
         doc_type.value=item[18]           
         doc_type.size="10";
         doc_type.readOnly = true;
         cell52.appendChild(doc_type);
         mycurrent_row.appendChild(cell52);
         
         /** doc_date */              
         var cell53=document.createElement("TD");
         var doc_date=document.createElement("input");
         doc_date.type="text";
         doc_date.name="doc_date"+seq;
         doc_date.id="doc_date"+seq;
         doc_date.value=item[20];          
         doc_date.size="10";
         //doc_date.readOnly = true;
         cell53.appendChild(doc_date);
         mycurrent_row.appendChild(cell53);
         
         /** CR Amount */
         var cell6=document.createElement("TD");  
         var cr_amount=document.createElement("input");
         cr_amount.type="text";
         cr_amount.name="cr_amount"+seq;
         cr_amount.id="cr_amount"+seq;
         cr_amount.value=item[7]; 
         cr_amount.size="10";
         //cr_amount.readOnly = true;
         cell6.appendChild(cr_amount);
         mycurrent_row.appendChild(cell6);
         
         
         /** DR Amount */
         var cell7=document.createElement("TD");  
         var dr_amount=document.createElement("input");
         dr_amount.type="text";
         dr_amount.name="dr_amount"+seq;
         dr_amount.id="dr_amount"+seq;
         dr_amount.value=item[16];
         dr_amount.size="10";
         //dr_amount.readOnly = true;
         cell7.appendChild(dr_amount);
         mycurrent_row.appendChild(cell7); 
         
         /** Entry Found in Pass Book ? */
         var cell8=document.createElement("TD");
         cell8.style.textAlign='center';
         var sel="";            
         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
         {
       	  sel=document.createElement("<INPUT type='checkbox' onclick='callme("+seq+")' name='EntryFoundInPassBook"+seq+"' id='EntryFoundInPassBook"+seq+"' value='Y'/>" );                       
         }
         else
         {    
           sel=document.createElement("input");     
           sel.type="checkbox";             
           sel.name="EntryFoundInPassBook"+seq;
           sel.id="EntryFoundInPassBook"+seq;   
           sel.setAttribute('onclick', "callme(" + seq + ")");
           sel.value="Y";                          
         }
         cell8.appendChild(sel);
         mycurrent_row.appendChild(cell8);    
         
         tbody.appendChild(mycurrent_row);    
         
		    /** Increment Sequence Number */ 
         seq=seq+1;
  
        }
        
    }
    document.getElementById("imgfld").style.visibility = "hidden";
}
//----------------------------------------------------------------------------------------------       
function LoadGrid1(baseResponse)
{	
	/** Delete Existing Values from Grid */
	   var tbody=document.getElementById("grid_body_TWAD");
	   var t=0;
	   for(t=tbody.rows.length-1;t>=0;t--)
	   {
	      tbody.deleteRow(0);
	   }
	  
	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	  
	   if (flag == "success") {				   
		   var len = baseResponse.getElementsByTagName("r_date").length;			  
		   if(len!=0)
		   {
			   var passBookBalance = baseResponse.getElementsByTagName("passBookBalance")[0].firstChild.nodeValue;
				if(passBookBalance == 'null' )
	       	{
					passBookBalance=0;
	       	}
				//document.getElementById('txtPBBalance').value = passBookBalance;
			for ( var k = 0; k < len; k++) {
				document.getElementById('filterFlag').value="T";
				var RemitanceDate = baseResponse.getElementsByTagName("r_date")[k].firstChild.nodeValue;
				if(RemitanceDate == 'null' )
	        	{
					RemitanceDate="";
	        	}else if(RemitanceDate == "0/00/0"){
	        		RemitanceDate="";
	        	}
				
				var WithdrawlDate = baseResponse.getElementsByTagName("w_date")[k].firstChild.nodeValue;
				if(WithdrawlDate == 'null' )
	        	{
					WithdrawlDate="";
	        	}else if(WithdrawlDate == "0/00/0"){
	        		WithdrawlDate="";
	        	}
				
				var Voucher_or_ChallanNo = baseResponse.getElementsByTagName("challan_no")[k].firstChild.nodeValue;
				if(Voucher_or_ChallanNo == 'null' )
	        	{
					Voucher_or_ChallanNo="";
	        	}
				
				var Cheqe_or_DDNo = baseResponse.getElementsByTagName("cheque_dd_no")[k].firstChild.nodeValue;
				if(Cheqe_or_DDNo == 'null' )
	        	{
					Cheqe_or_DDNo="";
	        	}
				
				var CRAmount = baseResponse.getElementsByTagName("cr_amount")[k].firstChild.nodeValue;
				if(CRAmount == 'null' )
	        	{
					CRAmount="";
	        	}
				
				var DRAmount = baseResponse.getElementsByTagName("dr_amount")[k].firstChild.nodeValue;
				if(DRAmount == 'null' )
	        	{
					DRAmount="";
	        	}
				
				var EntryFoundInPassBook = baseResponse.getElementsByTagName("EntryFoundInPassBook")[k].firstChild.nodeValue;
				if(EntryFoundInPassBook == 'null' )
	        	{
					EntryFoundInPassBook="";
	        	}
				
				var Docno = baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;
				if(Docno == 'null' )
	        	{
					Docno="";
	        	}
			
				var Doctype = baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;
				if(Doctype == 'null' )
	        	{
					Doctype="";
	        	}
			
				var Docdate = baseResponse.getElementsByTagName("doc_date")[k].firstChild.nodeValue;
				if(Docdate == 'null' )
	        	{
					Docdate="";
	        	}else if(Docdate == "0/00/0"){
	        		Docdate="";
	        	}	
				//alert("Docdate::"+Docdate);
				var sno = baseResponse.getElementsByTagName("sno")[k].firstChild.nodeValue;
				
				
			    /** Create Table Row */
				var mycurrent_row=document.createElement("TR");         
				
	        	/** Remittance Date Creation */
	        	var cell2 = document.createElement("TD");
	        	var r_date=document.createElement("input");
	        	r_date.type="text";
	        	r_date.name="r_date"+seq;
	            r_date.id="r_date"+seq;
	        	r_date.value=RemitanceDate;        	
	        	r_date.size="10";
	        	r_date.setAttribute('onblur',"dateValidation_T1("+seq+")");
	        	cell2.appendChild(r_date);
	        	mycurrent_row.appendChild(cell2);	            
	           
	            /** WithDrawl Date */
	             var cell3=document.createElement("TD"); 
	             var w_date=document.createElement("input");
	             w_date.type="text";
	             w_date.name="w_date"+seq;
	             w_date.id="w_date"+seq;             
	             w_date.value=WithdrawlDate;             
	             w_date.size="10";
	             w_date.setAttribute('onblur',"dateValidation_T2("+seq+")");
	             cell3.appendChild(w_date);
	             mycurrent_row.appendChild(cell3);   
	            
	             /** Challan or Voucher Number Creation */
	             var cell4=document.createElement("TD");
	             var r_w_no=document.createElement("input");
	             r_w_no.type="text";
	             r_w_no.name="r_w_no"+seq;
	             r_w_no.id="r_w_no"+seq;
	             r_w_no.value=Voucher_or_ChallanNo;  
	             r_w_no.readOnly = true;
	             r_w_no.size="10";
	             cell4.appendChild(r_w_no);
	             mycurrent_row.appendChild(cell4);
	             
	             /** Cheque / DD Number */              
	             var cell5=document.createElement("TD");
	             var ccdd_no=document.createElement("input");
	             ccdd_no.type="text";
	             ccdd_no.name="ccdd_no"+seq;
	             ccdd_no.id="ccdd_no"+seq;
	             ccdd_no.value=Cheqe_or_DDNo;  
	             ccdd_no.readOnly = true;
	             ccdd_no.size="10";
	             cell5.appendChild(ccdd_no);
	             mycurrent_row.appendChild(cell5);
	             
	             /** doc_no */              
	             var cell51=document.createElement("TD");
	             var doc_no=document.createElement("input");
	             doc_no.type="text";
	             doc_no.name="doc_no"+seq;
	             doc_no.id="doc_no"+seq;
	             doc_no.value=Docno;         
	             doc_no.size="10";
	             doc_no.readOnly = true;
	             cell51.appendChild(doc_no);
	             mycurrent_row.appendChild(cell51);
	             
	             /** doc_type */              
	             var cell52=document.createElement("TD");
	             var doc_type=document.createElement("input");
	             doc_type.type="text";
	             doc_type.name="doc_type"+seq;
	             doc_type.id="doc_type"+seq;
	             doc_type.value=Doctype;            
	             doc_type.size="10";
	             doc_type.readOnly = true;
	             cell52.appendChild(doc_type);
	             mycurrent_row.appendChild(cell52);
	           
	             /** doc_date */              
	             var cell53=document.createElement("TD");
	             var doc_date=document.createElement("input");
	             doc_date.type="text";
	             doc_date.name="doc_date"+seq;
	             doc_date.id="doc_date"+seq;
	             doc_date.value=Docdate;            
	             doc_date.size="10";
	             cell53.appendChild(doc_date);
	             mycurrent_row.appendChild(cell53);
	           
	             /** CR Amount */
	             var cell6=document.createElement("TD");  
	             var cr_amount=document.createElement("input");
	             cr_amount.type="text";
	             cr_amount.name="cr_amount"+seq;
	             cr_amount.id="cr_amount"+seq;
	             cr_amount.value=CRAmount;  
	             cr_amount.size="10";
	             cell6.appendChild(cr_amount);
	             mycurrent_row.appendChild(cell6);
	                    
	             /** DR Amount */
	             var cell7=document.createElement("TD");  
	             var dr_amount=document.createElement("input");
	             dr_amount.type="text";
	             dr_amount.name="dr_amount"+seq;
	             dr_amount.id="dr_amount"+seq;
	             dr_amount.value=DRAmount;   
	             dr_amount.size="10";
	             cell7.appendChild(dr_amount);
	             mycurrent_row.appendChild(cell7); 
	             
	             
	             /** Entry Found in Pass Book ? */
	              var cell8=document.createElement("TD");
	              cell8.style.textAlign='center';
	              var sel="";            
	              if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	              {
	            	  if (item[12] =='Y' ) 
	                  {  
	            	     sel=document.createElement("<INPUT type='checkbox' onclick='callme("+seq+")' checked name='EntryFoundInPassBook"+seq+"' id='EntryFoundInPassBook"+seq+"' value='Y' />" );
	                  }
	            	  else
	            	  {
	             	     sel=document.createElement("<INPUT type='checkbox' onclick='callme("+seq+")' name='EntryFoundInPassBook"+seq+"' id='EntryFoundInPassBook"+seq+"' value='Y' />" );
	            	  }
	              }
	              else
	              {    
	                sel=document.createElement("input");     
	                sel.type="checkbox";             
	                sel.name="EntryFoundInPassBook"+seq;
	                sel.id="EntryFoundInPassBook"+seq;
	                sel.setAttribute('onclick', "callme(" + seq + ")");
	                sel.value="Y";    
	                if (EntryFoundInPassBook =='Y' ) 
	                {
	                  sel.checked=true;
	                }
	                else
	                {
	                  sel.checked=false;
	                } 	
	                
	              }
	              
	              cell8.appendChild(sel);
	              mycurrent_row.appendChild(cell8);
	              //added on may2013 for updating same cheque no
	              var cell77=document.createElement("TD");  
		             var serialno=document.createElement("input");
		             serialno.type="text";
		             serialno.name="slno"+seq;
		             serialno.id="slno"+seq;
		             serialno.value=sno;   
		             serialno.size="10";
		             serialno.readOnly = true;
		             cell77.appendChild(serialno);
		             mycurrent_row.appendChild(cell77);
	              
	             tbody.appendChild(mycurrent_row);    
		         
	 		   
	             seq=seq+1;
				
			}
		   }
		   else{
			   alert("Cashbook Transaction Records Does Not Exist");
		   }
		   //seq=0;
		   var len1 = baseResponse.getElementsByTagName("PassbookDate1").length;		   
		   if(len1!=0)
		   {
			for ( var k = 0; k < len1; k++) {
				document.getElementById('filterFlag1').value="NT";
				var PassbookDate1 = baseResponse.getElementsByTagName("PassbookDate1")[k].firstChild.nodeValue;
				if(PassbookDate1 == 'null' )
	        	{
					PassbookDate1="";
	        	}  
				var Particulars1 = baseResponse.getElementsByTagName("Particulars1")[k].firstChild.nodeValue;
				if(Particulars1 == 'null' )
	        	{
					Particulars1="";
	        	}
				var Cheqe_or_DDNo1 = baseResponse.getElementsByTagName("Cheqe_or_DDNo1")[k].firstChild.nodeValue;
				if(Cheqe_or_DDNo1 == 'null' )
	        	{
					Cheqe_or_DDNo1="";
	        	}
				var Details1 = baseResponse.getElementsByTagName("Details1")[k].firstChild.nodeValue;
				if(Details1 == 'null' )
	        	{
					Details1="";
	        	}
				var CRAmount1 = baseResponse.getElementsByTagName("CRAmount1")[k].firstChild.nodeValue;
				if(CRAmount1 == 'null' )
	        	{
					CRAmount1="";
	        	}
				var DRAmount1 = baseResponse.getElementsByTagName("DRAmount1")[k].firstChild.nodeValue;
				if(DRAmount1 == 'null' )
	        	{
					DRAmount1="";
	        	}
				var TypeOfTransaction1 = baseResponse.getElementsByTagName("TypeOfTransaction1")[k].firstChild.nodeValue;
				if(TypeOfTransaction1 == 'null' )
	        	{
					TypeOfTransaction1="";
	        	}

				var mycurrent_row=document.createElement("TR");         

				/** Pass Book Date */
				var cell2 = document.createElement("TD");
				var Entry_Date_NT=document.createElement("input");
				Entry_Date_NT.type="text";
				Entry_Date_NT.name="Entry_Date_NT"+seqNT;
				Entry_Date_NT.id="Entry_Date_NT"+seqNT;
				Entry_Date_NT.value=PassbookDate1;
				Entry_Date_NT.size="10";
				Entry_Date_NT.setAttribute('onblur',"dateValidation_NT("+seqNT+")");			
				cell2.appendChild(Entry_Date_NT);
				mycurrent_row.appendChild(cell2);
							
				/** Particulars */
			    var cell3=document.createElement("TD"); 
			    var Particualrs_NT=document.createElement("input");
			    Particualrs_NT.type="textarea";
			    Particualrs_NT.name="Particualrs_NT"+seqNT;
			    Particualrs_NT.value=Particulars1;
			    cell3.appendChild(Particualrs_NT);
			    mycurrent_row.appendChild(cell3);
			     
			     /** Cheque No. */
			     var cell4=document.createElement("TD");
			     var ChequeNo_NT=document.createElement("input");
			     ChequeNo_NT.type="text";
			     ChequeNo_NT.name="ChequeNo_NT"+seqNT;
			     ChequeNo_NT.id="ChequeNo_NT"+seqNT;
			     ChequeNo_NT.value=Cheqe_or_DDNo1;
			     ChequeNo_NT.size="10";
			     ChequeNo_NT.readOnly = true;
			     cell4.appendChild(ChequeNo_NT);
			     mycurrent_row.appendChild(cell4);
		
			     /** Details */              
			     var cell5=document.createElement("TD");
			     var Details_NT=document.createElement("input");
			     Details_NT.type="text";
			     Details_NT.name="Details_NT"+seqNT;
			     Details_NT.value=Details1;
			     cell5.appendChild(Details_NT);
			     mycurrent_row.appendChild(cell5);
				   
			     /** CR Amount */
			     var cell6=document.createElement("TD");  
			     var cr_amount_NT=document.createElement("input");
			     cr_amount_NT.type="text";
			     cr_amount_NT.name="cr_amount_NT"+seqNT;
			     cr_amount_NT.value=CRAmount1;
			     cr_amount_NT.size="10";
			     cell6.appendChild(cr_amount_NT);
			     mycurrent_row.appendChild(cell6);
						     
			     /** DR Amount */
			     var cell7=document.createElement("TD");  
			     var dr_amount_NT=document.createElement("input");
			     dr_amount_NT.type="text";
			     dr_amount_NT.name="dr_amount_NT"+seqNT;
			     dr_amount_NT.value=DRAmount1;
			     dr_amount_NT.size="10";
			     cell7.appendChild(dr_amount_NT);
			     mycurrent_row.appendChild(cell7);       
			   			     		     
			     /**
			      *  Dynamic Combo Creation and Loading 
			      */ 
			     
			     /* Trans_Type_NT */
			     cell12=document.createElement("TD");
			     cell12.style.textAlign='center';  
			     var Trans_Type_NT;
			     
			     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
			     { try
			       {
			    	 Trans_Type_NT =  document.createElement("<select name='Trans_Type_NT"+seqNT+"' id='Trans_Type_NT' >");
			       }catch( e ) { alert(e.description) }
			     }
			     else
			     {
			    	 Trans_Type_NT=document.createElement("select");                        
			    	 Trans_Type_NT.id="Trans_Type_NT";
			    	 Trans_Type_NT.name="Trans_Type_NT"+seqNT;
			     }
			     
			     var cmbCategoryCodeObj = baseResponse.getElementsByTagName("reason_pair");
			     var option11=document.createElement("option");    
			         option11.value="";  
			         option11.text="--Select--";
			         
			    try
			      {
			    	Trans_Type_NT.add(option11);
			      }
			   catch(e)
			      {
				    Trans_Type_NT.add(option11,null);
			      }     
			   
			   
			     for(var y=0;y<cmbCategoryCodeObj.length;y++)
			     {
			     
			         CatCode[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_code")[0].firstChild.nodeValue;
			         CatDesc[y]=cmbCategoryCodeObj[y].getElementsByTagName("reason_desc")[0].firstChild.nodeValue;       
			         
			         var option11=document.createElement("option");    
			         if (CatCode[y] == TypeOfTransaction1)
	                  {	  
	                	 option11.selected=true;
	                  }  
			         
			         option11.value=CatCode[y];  
			         option11.text=CatDesc[y];
			         
			       
			        try
			          {
			        	Trans_Type_NT.add(option11);
			          }
			        catch(e)
			          {
			        	Trans_Type_NT.add(option11,null);
			          }		          
			     }		         

			    cell12.appendChild(Trans_Type_NT);                                                    
			    mycurrent_row.appendChild(cell12);   

			    /** Delete ? */
	            var DeleteNT = document.getElementById('DeleteT').value;
	            var cell9=document.createElement("TD");
	            cell9.style.textAlign='center';
	            var sel="";            
	            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	            {
	          	  sell=document.createElement("<INPUT type='checkbox' name='Delete_NT"+seqNT+"' id='Delete_NT"+seqNT+"' value='Y'/>" );                       
	            }
	            else
	            {    
	              sell=document.createElement("input");     
	              sell.type="checkbox";             
	              sell.name="Delete_NT"+seqNT;
	              sell.id="Delete_NT"+seqNT;                   
	              sell.value="Y";     	              
	           	  sell.disabled = false;	              
	            }
	            cell9.appendChild(sell);
	            mycurrent_row.appendChild(cell9);

				tbody1.appendChild(mycurrent_row);    
			         
			    /** Increment Sequence Number */ 
				seqNT=seqNT+1;			   
			}
		   }
		  /* else{
			   alert("Bank Transaction Records Does Not Exist");
		   }*/
			//seqNT=0;
	   }
    
}
//----------------------------------------------------------------------------------------------
function LoadNONTWADTransactions(baseResponse)
{
    
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    if(flag=="success")
    {    	   
    	document.getElementById('filterFlag1').value="NT";
	        /* Load NON TWAD Trasactions Values */    	
			
			/** Get TBody Object */
			var tbody=document.getElementById("grid_body_NONTWAD");
		    	
		    /** Create Table Row */
			var mycurrent_row=document.createElement("TR");         
			mycurrent_row.id=seqNT;
		 
			
			/** Pass Book Date */
			var cell2 = document.createElement("TD");
			var Entry_Date_NT=document.createElement("input");
			Entry_Date_NT.type="text";
			Entry_Date_NT.name="Entry_Date_NT"+seqNT;
			Entry_Date_NT.id="Entry_Date_NT"+seqNT;
			Entry_Date_NT.value="";
			Entry_Date_NT.size="10";
			Entry_Date_NT.setAttribute('onblur',"dateValidation_NT("+seqNT+")");			
			cell2.appendChild(Entry_Date_NT);
			mycurrent_row.appendChild(cell2);
		
			
			/** Particulars */
		    var cell3=document.createElement("TD"); 
		    var Particualrs_NT=document.createElement("input");
		    Particualrs_NT.type="textarea";
		    Particualrs_NT.name="Particualrs_NT"+seqNT;
		    Particualrs_NT.value="";
		    cell3.appendChild(Particualrs_NT);
		    mycurrent_row.appendChild(cell3);
		    
		     
		     /** Cheque No. */
		     var cell4=document.createElement("TD");
		     var ChequeNo_NT=document.createElement("input");
		     ChequeNo_NT.type="text";
		     ChequeNo_NT.name="ChequeNo_NT"+seqNT;
		     ChequeNo_NT.id="ChequeNo_NT"+seqNT;
		     ChequeNo_NT.value="";
		     ChequeNo_NT.size="10";
		     cell4.appendChild(ChequeNo_NT);
		     mycurrent_row.appendChild(cell4);
		     
		  
		     /** Details */              
		     var cell5=document.createElement("TD");
		     var Details_NT=document.createElement("input");
		     Details_NT.type="text";
		     Details_NT.name="Details_NT"+seqNT;
		     Details_NT.value="";
		     cell5.appendChild(Details_NT);
		     mycurrent_row.appendChild(cell5);
		     
		   
		     /** CR Amount */
		     var cell6=document.createElement("TD");  
		     var cr_amount_NT=document.createElement("input");
		     cr_amount_NT.type="text";
		     cr_amount_NT.name="cr_amount_NT"+seqNT;
		     cr_amount_NT.value="";
		     cr_amount_NT.size="10";
		     cell6.appendChild(cr_amount_NT);
		     mycurrent_row.appendChild(cell6);
		     
		     
		     /** DR Amount */
		     var cell7=document.createElement("TD");  
		     var dr_amount_NT=document.createElement("input");
		     dr_amount_NT.type="text";
		     dr_amount_NT.name="dr_amount_NT"+seqNT;
		     dr_amount_NT.value="";
		     dr_amount_NT.size="10";
		     cell7.appendChild(dr_amount_NT);
		     mycurrent_row.appendChild(cell7);       
		     
		     		     
		     /**
		      *  Dynamic Combo Creation and Loading 
		      */ 
		     
		     /* Reason for Difference */
		     cell12=document.createElement("TD");
		     cell12.style.textAlign='center';  
		     var Trans_Type_NT;
		     
		     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
		     { try
		       {
		    	 Trans_Type_NT =  document.createElement("<select name='Trans_Type_NT"+seqNT+"' id='Trans_Type_NT' >");
		       }catch( e ) { alert(e.description) }
		     }
		     else
		     {
		    	 Trans_Type_NT=document.createElement("select");                        
		    	 Trans_Type_NT.id="Trans_Type_NT";
		    	 Trans_Type_NT.name="Trans_Type_NT"+seqNT;
		     }
		     
		     var cmbCategoryCodeObj = baseResponse.getElementsByTagName("trans_pair");
		     var option11=document.createElement("option");    
		         option11.value="";  
		         option11.text="--Select--";
		         
		    try
		      {
		    	Trans_Type_NT.add(option11);
		      }
		   catch(e)
		      {
			    Trans_Type_NT.add(option11,null);
		      }     
		   
		   
		     for(var y=0;y<cmbCategoryCodeObj.length;y++)
		     {
		     
		         CatCode[y]=cmbCategoryCodeObj[y].getElementsByTagName("trans_code")[0].firstChild.nodeValue;
		         CatDesc[y]=cmbCategoryCodeObj[y].getElementsByTagName("trans_desc")[0].firstChild.nodeValue;       
		         
		         var option11=document.createElement("option");    
		         
		         option11.value=CatCode[y];  
		         option11.text=CatDesc[y];
		         
		       
		        try
		          {
		        	Trans_Type_NT.add(option11);
		          }
		        catch(e)
		          {
		        	Trans_Type_NT.add(option11,null);
		          }		          
		     }		         

		    cell12.appendChild(Trans_Type_NT);                                                    
		    mycurrent_row.appendChild(cell12);   

		    /** Delete ? */
            var DeleteNT = document.getElementById('DeleteT').value;
            var cell9=document.createElement("TD");
            cell9.style.textAlign='center';
            var sel="";            
            if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
            {
          	  sell=document.createElement("<INPUT type='checkbox' name='Delete_NT"+seq+"' id='Delete_NT"+seqNT+"' value='Y'/>" );                       
            }
            else
            {    
              sell=document.createElement("input");     
              sell.type="checkbox";             
              sell.name="Delete_NT"+seqNT;
              sell.id="Delete_NT"+seqNT;                   
              sell.value="Y";     
              if(DeleteNT == 0)
              {
           	   sell.disabled = true;
              }else{
           	   sell.disabled = false;
              }
            }
            cell9.appendChild(sell);
            mycurrent_row.appendChild(cell9);
            
			tbody.appendChild(mycurrent_row);    
		         
		    /** Increment Sequence Number */ 
			seqNT=seqNT+1;
	
    }
}

function LoadGrid()
{	
	document.frmBRS_OB_Create.Add.disabled = true;
	document.getElementById('filterFlag2').value="List";
	   var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
	   var  cmbOffice_code   = document.getElementById("cmbOffice_code").value;
	   var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
	   var  txtCB_Month      = document.getElementById("txtCB_Month").value;
	   var  cmbBankAccNo      = document.getElementById("cmbBankAccNo").value;
	   
	   var url="../../../../../BRS_OB_Create?Command=LoadGrid&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
       "&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;
	   
	   //alert(url);
	   var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
          handleResponse_BRS(req);
       }   
          req.send(null);
	
}

function callme(sam) {
        var amt = 0;
	var fg = 0;
	var fg1 = 0;
	var ii = 0;
	if (document.getElementById("EntryFoundInPassBook" + sam).checked == true) {
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			//r.bgColor = "rgb(255,255,225)";
			if (document.getElementById("ccdd_no" + sam).value != "") {
				if ((document.getElementById("ccdd_no" + i).value) == (document
						.getElementById("ccdd_no" + sam).value)) {
                                                fg1 = fg1 + 1;
					if (fg1 == 1) {
						ii = i;
					}
					
					r.bgColor = "#FFCCCC";
					document.getElementById("EntryFoundInPassBook" + i).checked = true;
                                     //   document.getElementById("Amt_in_PassBk" + i).value = (document.getElementById("dr_amount" + i).value + document.getElementById("cr_amount" + i).value);
					var tt= document.getElementById("cr_amount" + i).value+document.getElementById("dr_amount" + i).value;
                                     //   alert(tt);
                                        if (fg1 > 1) {
						fg = 1;
						amt = amt+ parseInt(document.getElementById("dr_amount" + i).value +document.getElementById("cr_amount" + i).value);
					}
				}
			}
                        else {
				document.getElementById("EntryFoundInPassBook" + sam).checked = true;				
			}
		}
                if (fg == 1) {
			amt = amt+ parseInt(document.getElementById("cr_amount" + ii).value+document.getElementById("dr_amount" + ii).value);
			alert("The Total Amount of the Cheque No"+ document.getElementById("ccdd_no" + sam).value + " is "+ amt);
		}
	} 
	/*else {
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
		//	r.bgColor = "rgb(255,255,225)";
			if (document.getElementById("ccdd_no" + sam).value != "") {
				if ((document.getElementById("ccdd_no" + i).value) == (document
						.getElementById("ccdd_no" + sam).value)) {
					r.bgColor = "rgb(255,255,225)";					
					document.getElementById("EntryFoundInPassBook" + i).checked = false;					
				}
			} else {				
				document.getElementById("EntryFoundInPassBook" + sam).checked = false;				
			}
		}
	}	*/
}

function checkNull()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_OB_Create.txtCB_Year.focus();
		return false;
	} else if (txtCB_Month == "s") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_OB_Create.txtCB_Month.focus();
		return false;
	} else {
  document.getElementById('RecordCount').value=seq;
  document.getElementById('RecordCountNT').value=seqNT;  
  return true;
	}
}

function exit()
{
   self.close();
}


/** Allows Number only */
function numbersonly1(e,t)
{
   var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      try{t.blur();}catch(e){}
      return true;
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
}

function dateValidation1() {
	var g = 0;
	var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
	var  txtCB_Month      = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0"+txtCB_Month);
	
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;	
	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var rowcount1 = tbody1.rows.length;

/*	for ( var i = 0; i < rowcount; i++) {
		var r_date = document.getElementById("r_date"+g).value; 
		var w_date = document.getElementById("w_date"+g).value; 		
		var r_w_no = document.getElementById("r_w_no"+g).value; 
		var ccdd_no = document.getElementById("ccdd_no"+g).value; 
		var doc_no = document.getElementById("doc_no"+g).value; 
		var doc_type = document.getElementById("doc_type"+g).value; 		
		
	      var browser = navigator.appName;

			if (browser == "Netscape") {
				var dd1 = r_date.split('/');
				var dd2 = w_date.split('/');
				r_date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];		
				w_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
			}
			var a = r_date.split('/');	
			var b = w_date.split('/');
			
			var r_date1 = new Date(a[2], a[0] - 1, a[1]);
			var w_date1 = new Date(b[2], b[0] - 1, b[1]);
			
			if(document.getElementById("r_date"+g).value == ""){
				alert("Enter Cashbook Transaction's Remitance Date in the Field");
				document.getElementById("r_date"+g).focus();
				return false;
			}else if(dd1[2] != txtCB_Year){
				alert("Cashbook Transaction's Remitance Date Year Should Be Equal To Cashbook Year");
				document.getElementById("r_date"+g).focus();
				return false;
			}else if(dd1[1] != txtCB_Month1){
				alert("Cashbook Transaction's Remitance Date Month Should Be Equal To Cashbook Month");
				document.getElementById("r_date"+g).focus();
				return false;
			}else if(document.getElementById("w_date"+g).value == ""){
				alert("Enter Cashbook Transaction's Withdrawal Date in the Field");
				document.getElementById("w_date"+g).focus();
				return false;
			}else if(dd2[2] != txtCB_Year){
				alert("Cashbook Transaction's Withdrawal Date Year Should Be Equal To Cashbook Year");
				document.getElementById("w_date"+g).focus();
				return false;
			}else if(dd2[1] != txtCB_Month1){
				alert("Cashbook Transaction's Withdrawal Date Month Should Be Equal To Cashbook Month");
				document.getElementById("w_date"+g).focus();
				return false;
			}else if(r_date1 > w_date1){
				alert("Cashbook Transaction's Withdrawal Date Should Be Greater than Remitance Date");
				document.getElementById("w_date"+g).focus();
				return false;
			}else if(document.getElementById("r_w_no"+g).value == ""){
				alert("Enter Cashbook Transaction's Challan/Voucher No in the Field");
				document.getElementById("r_w_no"+g).focus();
				return false;
			}else if(document.getElementById("ccdd_no"+g).value == ""){
				alert("Enter Cashbook Transaction's Cheque/DD No in the Field");
				document.getElementById("ccdd_no"+g).focus();
				return false;
			}else if(document.getElementById("doc_no"+g).value == ""){
				alert("Enter Cashbook Transaction's Doc No in the Field");
				document.getElementById("doc_no"+g).focus();
				return false;
			}else if(document.getElementById("doc_type"+g).value == ""){
				alert("Enter Cashbook Transaction's Doc Type in the Field");
				document.getElementById("doc_type"+g).focus();
				return false;
			}
			g = g+1;
		}
		g = 0;	*/	
	for ( var i = 0; i < rowcount1; i++) {		
		var Entry_Date_NT = document.getElementById("Entry_Date_NT"+g).value; 		
		var ChequeNo_NT = document.getElementById("ChequeNo_NT"+g).value;				
	     var browser1 = navigator.appName;

			if (browser1 == "Netscape") {
				var dd1 = Entry_Date_NT.split('/');
				Entry_Date_NT = dd1[1] + "/" + dd1[0] + "/" + dd1[2];			
			}
			var a = Entry_Date_NT.split('/');		
			if(document.getElementById("Entry_Date_NT"+g).value == ""){
				alert("Enter Bank Transaction's Pass book Date in the Field");
				document.getElementById("Entry_Date_NT"+g).focus();
				return false;
			}else if(dd1[2] != txtCB_Year){
				alert("Bank Transaction's Pass book Date Year Should Be Equal To Cashbook Year");
				document.getElementById("Entry_Date_NT"+g).focus();
				return false;
			}else if(dd1[1] != txtCB_Month1){
				alert("Bank Transaction's Pass book Date Month Should Be Equal To Cashbook Month");
				document.getElementById("Entry_Date_NT"+g).focus();
				return false;
			}else if(document.getElementById("ChequeNo_NT"+g).value == ""){
				alert("Enter Bank Transaction's Cheque No in the Field");
				document.getElementById("ChequeNo_NT"+g).focus();
				return false;
			}
		g = g+1;					
	}
	g = 0;
	return true;
}

function dateValidation_NT(seqNT)
{
      var Entry_Date_NT = document.getElementById("Entry_Date_NT"+seqNT).value; 

      var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);

      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date_NT.split('/');
			Entry_Date_NT = dd1[1] + "/" + dd1[0] + "/" + dd1[2];			
		}
		var a = Entry_Date_NT.split('/');		
		
		if(document.getElementById("Entry_Date_NT"+seqNT).value == ""){
			alert("Enter Pass book Date in the Field");
			document.getElementById("Entry_Date_NT"+seqNT).focus();
			return false;
		}else if(dd1[2] != txtCB_Year){
			alert("Pass book Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Entry_Date_NT"+seqNT).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
			alert("Pass book Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Entry_Date_NT"+seqNT).focus();
			return false;
		}else{
			return true;
		}
}

function dateValidation_T1(seq)
{
      var r_date = document.getElementById("r_date"+seq).value; 

      var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);

      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = r_date.split('/');
			r_date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];			
		}
		var a = r_date.split('/');		
		
		if(document.getElementById("r_date"+seq).value == ""){
			alert("Enter Remitance Date in the Field");
			document.getElementById("r_date"+seq).focus();
			return false;
		}else if(dd1[2] != txtCB_Year){
			alert("Remitance Date Year Should Be Equal To Cashbook Year");
			document.getElementById("r_date"+seq).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
			alert("Remitance Date Month Should Be Equal To Cashbook Month");
			document.getElementById("r_date"+seq).focus();
			return false;
		}else{
			return true;
		}
}


function dateValidation_T2(seq)
{
      var w_date = document.getElementById("w_date"+seq).value; 
      var r_date = document.getElementById("r_date"+seq).value;

      var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);

      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = w_date.split('/');
			var dd2 = r_date.split('/');
			
			w_date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];	
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = w_date.split('/');		
		var b = r_date.split('/');
		
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);
		var w_date1 = new Date(a[2], a[0] - 1, a[1]);
		
		if(document.getElementById("w_date"+seq).value == ""){
			alert("Enter Withdrawal Date in the Field");
			document.getElementById("w_date"+seq).focus();
			return false;
		}else if(dd1[2] != txtCB_Year){
			alert("Withdrawal Date Year Should Be Equal To Cashbook Year");
			document.getElementById("w_date"+seq).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
			alert("Withdrawal Date Month Should Be Equal To Cashbook Month");
			document.getElementById("w_date"+seq).focus();
			return false;
		}else if(r_date1 > w_date1){
			alert("Withdrawal Date Should Be Greater than Remitance Date");
			document.getElementById("w_date"+g).focus();
			return false;
		}else{
			return true;
		}
}