
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////


/**--------------------LOAD VALUES FROM GRID INTO CORRESPONDING FIELDS  ---------------------------------*/
function loadTable(scod)
{
	com_id = scod; 
    clearall();
    var r=document.getElementById(scod);
    var rcells=r.cells;
  
    try{document.getElementById("txtDDNumber").value=rcells.item(1).firstChild.value;}catch(e){}
    try{document.getElementById("txtDDDate").value=rcells.item(2).firstChild.value;}catch(e){}
    try{document.getElementById("txtDDAmount").value=rcells.item(3).firstChild.value;}catch(e){}  
    try{document.getElementById("txtDDFavourOf").value=rcells.item(4).firstChild.value;}catch(e){}
    try{document.getElementById("txtCommissionCharge").value=rcells.item(5).firstChild.value;}catch(e){}
     
    document.YourSelfChequeForm.cmdupdate.style.display='block';
    document.YourSelfChequeForm.cmddelete.disabled=false;
    document.YourSelfChequeForm.cmdadd.style.display='none';
    
}


/**--------------------GRID ADDITION ---------------------------------*/
function ADD_GRID()
{
	    /*   DD Number  */ 
        if(document.getElementById("txtDDNumber").value.length==0)
        {
        	alert("Enter DD Number ");
        	return false;
        }
        
       /*   DD Date   */
       if(document.getElementById("txtDDDate").value=="")
       {
            alert("Enter DD Date");            
            return false;        
       }       
      
       
       /*  DD Amount  */
       if(document.getElementById("txtDDAmount").value=="")
       {
            alert("Enter DD Amount ");            
            return false;        
       }     
        
       /* DD Favour of */  
       if(document.getElementById("txtDDFavourOf").value=="")
       {
            alert("Enter DD Favour of ");           
            return false;    
       }
       
       /*  Commission Charge  */
       if(document.getElementById("txtCommissionCharge").value=="")
       {
            alert("Enter Comission Charge");            
            return false;        
       }     
       
       /* DD date can’t be less than cheque date  */
       if(document.getElementById("txtDDDate").value < document.getElementById("txtChequeDate").value )
       {
            alert("DD date can not be less than cheque date");            
            return false;        
       }  
       
       /*  DD amount cannot be less than commission charge  */
       if(document.getElementById("txtCommissionCharge").value > document.getElementById("txtDDAmount").value )
       {
            alert(" DD amount cannot be less than commission charge ");    
            document.getElementById("txtCommissionCharge").value="";
            return false;        
       }
       
       /* Get Grid Body Object */
       var tbody=document.getElementById("grid_body");       
      
       /* Array Declaration */
       var items=new Array();
       items[0]=document.getElementById("txtDDNumber").value;
       items[1]=document.getElementById("txtDDDate").value;
       items[2]=document.getElementById("txtDDAmount").value;
       items[3]=document.getElementById("txtDDFavourOf").value;
       items[4]=document.getElementById("txtCommissionCharge").value;       
       
       /** Create Table Row */
       var mycurrent_row=document.createElement("TR");
        
       mycurrent_row.id=seq;
        
            /** Edit Link Creation */ 
       		var cell1=document.createElement("TD");
       		var anc=document.createElement("A");
       		var url="javascript:loadTable('"+mycurrent_row.id+"')";
       		anc.href=url;
       		var txtedit=document.createTextNode("EDIT");
       		anc.appendChild(txtedit);
       		cell1.appendChild(anc);
       		mycurrent_row.appendChild(cell1);
        
       		/** DD Number Creation */
       		var cell2 = document.createElement("TD");
            var txtDDNumber=document.createElement("input");
            txtDDNumber.type="hidden";
            txtDDNumber.name="txtDDNumber"+seq;
            txtDDNumber.value=items[0];
            cell2.appendChild(txtDDNumber);
            var currentText=document.createTextNode(items[0]);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
            
            
            /** DD Date Creation */
             var cell3=document.createElement("TD"); 
             var txtDDDate=document.createElement("input");
             txtDDDate.type="hidden";
             txtDDDate.name="txtDDDate"+seq;
             txtDDDate.value=items[1];
             cell3.appendChild(txtDDDate);
             var currentText=document.createTextNode(items[1]);
             cell3.appendChild(currentText);
             mycurrent_row.appendChild(cell3);
             
             
             /** DD Amount Creation */
             var cell4=document.createElement("TD");
             var txtDDAmount=document.createElement("input");
             txtDDAmount.type="hidden";
             txtDDAmount.name="txtDDAmount"+seq;
             txtDDAmount.value=items[2];
             cell4.appendChild(txtDDAmount);
             var currentText=document.createTextNode(items[2]);
             cell4.appendChild(currentText);
             mycurrent_row.appendChild(cell4);
             
             /** DD Favour of */
             var cell5=document.createElement("TD");
             var txtDDFavourOf=document.createElement("input");
             txtDDFavourOf.type="hidden";
             txtDDFavourOf.name="txtDDFavourOf"+seq;
             txtDDFavourOf.value=items[3];
             cell5.appendChild(txtDDFavourOf);
             var currentText=document.createTextNode(items[3]);
             cell5.appendChild(currentText);
             mycurrent_row.appendChild(cell5);
            
             /** Commission Charge */
             var cell6=document.createElement("TD");  
             var txtCommissionCharge=document.createElement("input");
             txtCommissionCharge.type="hidden";
             txtCommissionCharge.name="txtCommissionCharge"+seq;
             txtCommissionCharge.value=items[4];
             cell6.appendChild(txtCommissionCharge);
             var currentText=document.createTextNode(items[4]);
             cell6.appendChild(currentText);
             mycurrent_row.appendChild(cell6);

             tbody.appendChild(mycurrent_row);
             clearall();
           
             /** Increment Sequence Number */ 
             seq=seq+1;
           
             
}




/**--------------------GRID UPDATION ---------------------------------*/

function update_GRID()
{      
	/*   DD Number  */ 
   	if(document.getElementById("txtDDNumber").value.length==0)
    {
    	alert("Enter DD Number ");
    	return false;
    }
    
   /*   DD Date   */
   if(document.getElementById("txtDDDate").value=="")
   {
        alert("Enter DD Date");            
        return false;        
   }       
  
   
   /*  DD Amount  */
   if(document.getElementById("txtDDAmount").value=="")
   {
        alert("Enter DD Amount ");            
        return false;        
   }     
    
   /* DD Favour of */  
   if(document.getElementById("txtDDFavourOf").value=="")
   {
        alert("Enter DD Favour of ");           
        return false;    
   }
   
   /*  Commission Charge  */
   if(document.getElementById("txtCommissionCharge").value=="")
   {
        alert("Enter Comission Charge");            
        return false;        
   }     
 
   
   /* DD date can’t be less than cheque date  */
   if(document.getElementById("txtDDDate").value < document.getElementById("txtChequeDate").value )
   {
        alert("DD date can not be less than cheque date");            
        return false;        
   }  
   
   
   /*  DD amount cannot be less than commission charge  */
   if(document.getElementById("txtCommissionCharge").value > document.getElementById("txtDDAmount").value )
   {
        alert(" DD amount cannot be less than commission charge ");        
        document.getElementById("txtCommissionCharge").value="";
        return false;        
   }
	
   
   
   /* Array Declaration */
   var items=new Array();
   items[0]=document.getElementById("txtDDNumber").value;
   items[1]=document.getElementById("txtDDDate").value;
   items[2]=document.getElementById("txtDDAmount").value;
   items[3]=document.getElementById("txtDDFavourOf").value;
   items[4]=document.getElementById("txtCommissionCharge").value;       
   
        
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        try{rcells.item(1).firstChild.value=items[0];}catch(e){}
        try{rcells.item(1).lastChild.nodeValue=items[0];}catch(e){}
             
        try{rcells.item(2).firstChild.value=items[1];}catch(e){}
        try{rcells.item(2).lastChild.nodeValue=items[1];}catch(e){}
              
        try{rcells.item(3).firstChild.value=items[2];}catch(e){}
        try{rcells.item(3).lastChild.nodeValue=items[2];}catch(e){}
            
        try{rcells.item(4).firstChild.value=items[3];}catch(e){}
        try{rcells.item(4).lastChild.nodeValue=items[3];}catch(e){}
             
        try{rcells.item(5).firstChild.value=items[4];}catch(e){}
        try{rcells.item(5).lastChild.nodeValue=items[4];}catch(e){}
             
                
            
        alert("Record Updated");
        clearall();
  }


/**--------------------GRID DELETION ---------------------------------*/

function delete_GRID()
{
     if(confirm("Do you want to delete ?"))
     {
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clearall();
     }
}


/**-------------------- CLEAR ALL THE VALUES IN DETAILS PART TEXT FIELDS ---------------------------------*/

function clearall()
{
    document.getElementById("txtDDNumber").value="";   
    document.getElementById("txtDDDate").value="";   
    document.getElementById("txtDDAmount").value="";
    document.getElementById("txtDDFavourOf").value="";    
    document.getElementById("txtCommissionCharge").value="";
  
    document.YourSelfChequeForm.cmdadd.style.display='block';
    document.YourSelfChequeForm.cmdupdate.style.display='none';
    document.YourSelfChequeForm.cmddelete.disabled=true;    
    
}


/**-------------------- CLEAR ALL THE VALUES IN DETAILS PART TEXT FIELDS + ALL GRID VALUES ------------------*/

function call_clr()
{
    document.getElementById("txtDDNumber").value="";   
	document.getElementById("txtDDDate").value="";   
	document.getElementById("txtDDAmount").value="";
	document.getElementById("txtDDFavourOf").value="";    
	document.getElementById("txtCommissionCharge").value="";
	
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    
    
}


/** Clear All the Fields in General Part */
function clrForm()
{
    call_clr();
    
    document.getElementById("txtCrea_date").value="";
    document.getElementById("txtChequeDate").value="";
    document.getElementById("cmbBankAccNo").value="";
    document.getElementById("txtChequeNo").value="";
    document.getElementById("txtChequeAmt").value="";
    document.getElementById("txtRefNo").value="";
    document.getElementById("txtRefDate").value="";
    document.getElementById("txtRemarks").value="";
    
 
}

		
/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////


function checkNull()
{
        var tbody=document.getElementById("grid_body");
      
        
        document.getElementById('RecordCount').value=seq;
        
        
        /** Accounting Unit ID */
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");            
            return false;    
        }
        
        /** Accounting for Office ID */
        if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select the Office Code");           
            return false;
        }
        
        /** Date in General Part */
        if(document.getElementById("txtCrea_date").value.length==0)
        {
            alert("Enter the Date");            
            return false;    
        }
        
        
        /** Bank Account Number */
        if(document.getElementById("cmbBankAccNo").value=="")
        {
            alert("Enter Bank Account Number");            
            return false;
        }
        
        
        /** Cheque Date */
        if(document.getElementById("txtChequeDate").value.length==0)
        {
            alert("Enter the Cheque Date");            
            return false;    
        }
        
        
        /** Cheque Number*/
        if(document.getElementById("txtChequeNo").value.length==0)
        {
            alert("Enter the Cheque Number");            
            return false;    
        }
                
        
        /** Cheque Amount*/
        if(document.getElementById("txtChequeAmt").value.length==0)
        {
            alert("Enter the Cheque Amount ");            
            return false;    
        }
          
        if(tbody.rows.length==0)
        {
            alert("Enter the Details Part");
            return false; 
        }
        
               
        
        if ( document.YourSelfChequeForm.txtDDObtained[0].checked == true ) 
        {		   
        	          
           /** Check Amount Value */
           if(tbody.rows.length>0)
           {
                   var check_amt=0;
                   rows=tbody.getElementsByTagName("tr");
                   for(i=0;i<rows.length;i++)
                   {
                       var cells=rows[i].cells;                   
                       check_amt=parseFloat(check_amt) + parseFloat(cells.item(3).lastChild.nodeValue)  + parseFloat(cells.item(5).lastChild.nodeValue);
                   }
                 
                   if(parseFloat(document.getElementById("txtChequeAmt").value) != parseFloat(check_amt))
                   {
                   	alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtChequeAmt").value)-parseFloat(check_amt)))
                   	return false;
                   }
                   
           }	
        }    
       return true;

}



/*
 * Check Remarks Field Length 
 */ 

function check_leng(remarks,param)
{
  if(param=='remarks')
  {
    if((remarks.length)>=240)
    {
    alert("Please Enter Remarks below 250 characters");
    }
  }
  S
  if(param=='favour') 
  {
    if((remarks.length)>=190)
    {
    alert("Please Enter Favour of Details below 250 characters");
    }
  }
}




























/**--------------------Load Bank Account Number ---------------------------------*/

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



function LoadBankAccountNumber()
{  
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var url="../../../../../YourSelfCheque_Edit.kv?Command=LoadBankAccounts&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	 var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
    	 LoadBankAccountNumberRes(req);
     }   
        req.send(null);
	  
}

function LoadBankAccountNumberRes(req)
{  
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="LoadBankAccountNumber")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	           var bank_ac_no =new Array();
            	           var acc_desc   =new Array();
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	           
            	           
            	            for(var k=0;k<acc_no.length;k++)
            	            {
            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            }
            	         
            	            cmbBankAccNo.innerHTML="";
            	            var option=document.createElement("OPTION");
            	            option.text="--Select Bank Acc. Number--";
            	            option.value="";
            	            try
            	            {
            	            	cmbBankAccNo.add(option);
            	            }catch(errorObject)
            	            {
            	            	cmbBankAccNo.add(option,null);
            	            }
            	            
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
            	            
            	        doFunction_voucher('load_Voucher_Details','null');   
            	    }
            	
            }
        
        }
    }
}






/** Amount Limit Validation */
function limit_amt(field,e)
{
    
    var unicode=e.charCode? e.charCode : e.keyCode;
    if(field.value.length<17)
    {
      if(field.value.length==14 && field.value.indexOf('.')==-1  )
      field.value=field.value+'.';
      if (unicode!=8 && unicode !=9  )
      {
          //if (unicode<46 || unicode==47 || unicode>57   )         // before if statement without '-' 
          if (unicode<45 || unicode==47 || unicode>57   )         // 45 is allowed to enter '-' value
              return false 
      }
    }
    else 
    return false;
}


/** Amount Validation */
function valid_amt(field)
{
  
  amt=field.value;
  if(amt.indexOf(".")!=amt.lastIndexOf("."))
  {
      alert("Enter a Valid Amount");
      field.value="";
      field.focus();
  }
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


/** Form Exit Function */
function exit()
{
	self.close();
}






























function call_mainJSP_script(fromcal_dateCtrl,blr_flag) 
{
   if ( blr_flag=="1")
   {
	   doFunction_voucher('load_Voucher_No','null');
   }
}




/**
 *   Load Voucher Number and Voucher Details for Modification  
 */ 
//-----------------------------------------------------------------------------------------------

function doFunction_voucher(Command,param)
{   
	  
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCrea_date= document.getElementById("txtCrea_date").value
       
       if(Command=="load_Voucher_No")
        {  
    	    if(txtCrea_date.length!=0)
            { 
            	var url="../../../../../YourSelfCheque_Edit.kv?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            	cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            	var req=getTransport();
            	req.open("GET",url,true); 
            	req.onreadystatechange=function()
            	{
            		handleResponse_voucher(req);
            	}   
                req.send(null);
            }         
        }
        else if(Command=="load_Voucher_Details")
        {  
            var  txtVoucher_No=document.getElementById("txtVoucher_No").value;           
            if(txtVoucher_No!="")
            {
            	var url="../../../../../YourSelfCheque_Edit.kv?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            	cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            	
            	var req=getTransport();
            	req.open("GET",url,true); 
            	req.onreadystatechange=function()
            	{
            		handleResponse_voucher(req);
            	}   
                    req.send(null);
            }         
        }
        

}

function handleResponse_voucher(req)
{  
	
    if(req.readyState==4)
    {
    	 
        if(req.status==200)
        {    
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             
            if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_Details")
            {
                load_Voucher_Details(baseResponse);
            }
            
        }
    }
}


function load_Voucher_No(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var txtVoucher_No=document.getElementById("txtVoucher_No");
	if(flag=="success")
    {
           var items_id=new Array();
           var Voc_No=baseResponse.getElementsByTagName("Voc_No");
            
            for(var k=0;k<Voc_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Voc_No")[k].firstChild.nodeValue;
                
            }
            
            txtVoucher_No.innerHTML="";
            var option = document.createElement("OPTION");
            option.text="--Select Vocher Number--";
            option.value="";
            try
            {
            	txtVoucher_No.add(option);
            }catch(errorObject)
            {
            	txtVoucher_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                	   txtVoucher_No.add(option);
                  }
                  catch(errorObject)
                  {
                	  txtVoucher_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
    		txtVoucher_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
            	txtVoucher_No.add(option);
            }catch(errorObject)
            {
            	txtVoucher_No.add(option,null);
            }
            alert("No Voucher Found");
    }
}



function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
    	
    	/* Load General Tab Values */
    	
    	var BANK_ACCOUNT_NO=baseResponse.getElementsByTagName("BANK_ACCOUNT_NO")[0].firstChild.nodeValue;
    	var WHEATHER_DD_OBTAINED=baseResponse.getElementsByTagName("WHEATHER_DD_OBTAINED")[0].firstChild.nodeValue;
    	var CHEQUE_DATE=baseResponse.getElementsByTagName("CHEQUE_DATE")[0].firstChild.nodeValue;
    	var CHEQUE_NUMBER=baseResponse.getElementsByTagName("CHEQUE_NUMBER")[0].firstChild.nodeValue;
    	var CHEQUE_AMOUNT=baseResponse.getElementsByTagName("CHEQUE_AMOUNT")[0].firstChild.nodeValue;
    	var REF_NO=baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
    	var ref_date=baseResponse.getElementsByTagName("ref_date")[0].firstChild.nodeValue;
    	var REMARKS=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
    	
    	document.getElementById("cmbBankAccNo").value=BANK_ACCOUNT_NO; 
    	
    	if(WHEATHER_DD_OBTAINED=='Y')
        {
          document.YourSelfChequeForm.txtDDObtained[0].checked=true;
        } 
        else
        {
          document.YourSelfChequeForm.txtDDObtained[1].checked=true;
        } 
    	document.getElementById("txtChequeDate").value= CHEQUE_DATE;
    	document.getElementById("txtChequeNo").value=CHEQUE_NUMBER;
    	document.getElementById("txtChequeAmt").value=CHEQUE_AMOUNT;
    	
    	if (REF_NO !="null")
    	{		
    		document.getElementById("txtRefNo").value=REF_NO;
    	}
    	else
    	{
    		document.getElementById("txtRefNo").value="";
    	}
    	
    	if (ref_date !="null")
    	{		
    		document.getElementById("txtRefDate").value=ref_date;
    	}
    	else
    	{
    		document.getElementById("txtRefDate").value="";
    	}		
    	if (REMARKS !="null")
    	{		
    		document.getElementById("txtRemarks").value=REMARKS;
    	}
    	else
    	{
    		document.getElementById("txtRemarks").value="";
    	}	
    	
    	/* Load Detail Tab Values */
    	
    	  /** Delete Existing Values from Grid */
    	  var tbody=document.getElementById("grid_body");
          var t=0;
          for(t=tbody.rows.length-1;t>=0;t--)
          {
             tbody.deleteRow(0);
          }
          
          /** Get DD Number Object for finding Total Number of Records */
          var DD_NUMBER=baseResponse.getElementsByTagName("DD_NUMBER");
          var items=new Array();
          for(var k=0;k<DD_NUMBER.length;k++)
          {
        	  
        	 items[0]=baseResponse.getElementsByTagName("DD_NUMBER")[k].firstChild.nodeValue;
             if(items[0]==0)
             items[0]="";
             
             
             items[1]=baseResponse.getElementsByTagName("DD_DATE")[k].firstChild.nodeValue;
             if(items[1]=="null")
             items[1]="";
             
             
             items[2]=baseResponse.getElementsByTagName("DD_AMOUNT")[k].firstChild.nodeValue;
             if(items[2]==0)
             items[2]="";
             
             
             items[3]=baseResponse.getElementsByTagName("DD_IN_FAVOUR_OF")[k].firstChild.nodeValue;
             if(items[3]=="null")
             items[3]="";
             
             
             items[4]=baseResponse.getElementsByTagName("COMMISSION_CHARGES")[k].firstChild.nodeValue;
             if(items[4]=="null")
             items[4]="";
             
             
             /** Create Table Row */
             var mycurrent_row=document.createElement("TR");
              
             mycurrent_row.id=seq;
              
                  /** Edit Link Creation */ 
             		var cell1=document.createElement("TD");
             		var anc=document.createElement("A");
             		var url="javascript:loadTable('"+mycurrent_row.id+"')";
             		anc.href=url;
             		var txtedit=document.createTextNode("EDIT");
             		anc.appendChild(txtedit);
             		cell1.appendChild(anc);
             		mycurrent_row.appendChild(cell1);
              
             		/** DD Number Creation */
             		var cell2 = document.createElement("TD");
             		var txtDDNumber=document.createElement("input");
             		txtDDNumber.type="hidden";
             		txtDDNumber.name="txtDDNumber"+seq;
             		txtDDNumber.value=items[0];
             		cell2.appendChild(txtDDNumber);
             		var currentText=document.createTextNode(items[0]);
             		cell2.appendChild(currentText);
             		mycurrent_row.appendChild(cell2);
                  
                  
                  /** DD Date Creation */
                   var cell3=document.createElement("TD"); 
                   var txtDDDate=document.createElement("input");
                   txtDDDate.type="hidden";
                   txtDDDate.name="txtDDDate"+seq;
                   txtDDDate.value=items[1];
                   cell3.appendChild(txtDDDate);
                   var currentText=document.createTextNode(items[1]);
                   cell3.appendChild(currentText);
                   mycurrent_row.appendChild(cell3);
                   
                   
                   /** DD Amount Creation */
                   var cell4=document.createElement("TD");
                   var txtDDAmount=document.createElement("input");
                   txtDDAmount.type="hidden";
                   txtDDAmount.name="txtDDAmount"+seq;
                   txtDDAmount.value=items[2];
                   cell4.appendChild(txtDDAmount);
                   var currentText=document.createTextNode(items[2]);
                   cell4.appendChild(currentText);
                   mycurrent_row.appendChild(cell4);
                   
                   /** DD Favour of */
                   var cell5=document.createElement("TD");
                   var txtDDFavourOf=document.createElement("input");
                   txtDDFavourOf.type="hidden";
                   txtDDFavourOf.name="txtDDFavourOf"+seq;
                   txtDDFavourOf.value=items[3];
                   cell5.appendChild(txtDDFavourOf);
                   var currentText=document.createTextNode(items[3]);
                   cell5.appendChild(currentText);
                   mycurrent_row.appendChild(cell5);
                  
                   /** Commission Charge */
                   var cell6=document.createElement("TD");  
                   var txtCommissionCharge=document.createElement("input");
                   txtCommissionCharge.type="hidden";
                   txtCommissionCharge.name="txtCommissionCharge"+seq;
                   txtCommissionCharge.value=items[4];
                   cell6.appendChild(txtCommissionCharge);
                   var currentText=document.createTextNode(items[4]);
                   cell6.appendChild(currentText);
                   mycurrent_row.appendChild(cell6);

                   tbody.appendChild(mycurrent_row);
                   clearall();
                   
                   /** Increment Sequence Number */ 
                   seq=seq+1;                 
                     
              
          }   
    	
    	
    }
}

		

function exit()
{
	self.close();
}

		