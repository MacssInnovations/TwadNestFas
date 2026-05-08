var seq=0;
/** Browser Identification */

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



function numbersonly(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur(); }catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false 
            }
        }
}     
function clear_Combo(combo)
{       
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        } 
}



///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
	    
    		 clrForm('load');
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                 
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }   
                 req.send(null);
            }
    }
}

function call_date(dateCtrl)                        // TB_checking 
{
	clrForm('load');
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req,dateCtrl);
             }   
             req.send(null);
       }
        //doFunction('load_Voucher_No','null');
    }
    
}



function check_TB(req,dateCtrl)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
            		doFunction_voucher('load_Voucher_No');              //return true;
            }
             else if(flag=="failure")
	         {
	                dateCtrl.value="";
	                alert("Trial Balance Closed");//return false;//
	                dateCtrl.focus();
	               
	         }
             else if(flag=="finyear")
             {
                     
	                dateCtrl.value="";
	                alert("Cash Book Control Not Found ");//return false;//
	                dateCtrl.focus();
	                    
             }
        }
    }
}


function doFunction_voucher(Command)
{   
	  // alert(Command);
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCrea_date= document.getElementById("txtCrea_date").value;  
       var payment_type=document.getElementById("cmbPayment_type").value;  
       if(Command=="load_Voucher_No")
       {  
    	   			call_clr();
    	   			var txtVoucher_No=document.getElementById("txtVoucher_No");
    	   			try
			   	    {		txtVoucher_No.innerHTML="";  }
			   	    catch(e)
			   	    {    	txtVoucher_No.innerText="";  }
			   	     
			   	    
    	    		if(txtCrea_date.length!=0)
		            { 
			            	var url="../../../../../Imprest_Account_Edit?Command=load_Voucher_No&option=Cancel&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
			            	cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtMode_of_creat="+payment_type;           
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
		        	
		        	document.getElementById("txtCash_Acc_code").value="";
		            document.getElementById("txtBankAccountNo").value="";
		            document.getElementById("txtBankName").value="";
		        	document.getElementById("txtOfficeId").value="";
		            document.getElementById("txtOfficeDet").value="";
		            document.getElementById("txtRemarks").value="";
		            document.getElementById("txtTotalAmt").value="";
		          
		          
		        	var tbody=document.getElementById("grid_body");            	    
		    	    try 
		    	    { tbody.innerHTML="";    }
		    	    catch(e)
		    	    { 	tbody.innerText="";  }
		    	    
		    	    
		    	    
		            if(txtVoucher_No!="")
		            {
			            	var url="../../../../../Imprest_Account_Edit?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
			            	cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtMode_of_creat="+payment_type;    
			            	
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
            var cmd=tagcommand.firstChild.nodeValue;
           
            if(cmd=="load_Voucher_No")
            {
            	load_Voucher_No(baseResponse);
            }
            else if(cmd=="load_Voucher_Details")
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
           
           
            var option=document.createElement("OPTION");
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
    	document.getElementById("txtCash_Acc_code").value=baseResponse.getElementsByTagName("OPR_HEAD_CODE")[0].firstChild.nodeValue;
    	document.getElementById("txtBankAccountNo").value=baseResponse.getElementsByTagName("ACCOUNT_NO")[0].firstChild.nodeValue;
        document.getElementById("txtBankID").value=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBranchID").value=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
        document.getElementById("txtBankName").value=baseResponse.getElementsByTagName("bankNAME")[0].firstChild.nodeValue;
        document.getElementById("txtOfficeId").value=baseResponse.getElementsByTagName("OFF_CODE")[0].firstChild.nodeValue;
        loadOfficeDetails();
        var remarks=baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
        if(remarks=="-")
        	remarks="";
        document.getElementById("txtRemarks").value=remarks;
        document.getElementById("txtTotalAmt").value=baseResponse.getElementsByTagName("TOTAL_AMOUNT")[0].firstChild.nodeValue;
        
        var tbody=document.getElementById("grid_body");                            
        var items=new Array();
        
        var hcode=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE");
       
        for(var i=0;i<hcode.length;i++)
        {
	        	var head_code=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
        		items[0]=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[i].firstChild.nodeValue;
	           
	            if(items[0]=="-")
	            {
	                   items[1]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
	                   items[0]=""
	            }
	            else
	            		items[1]=baseResponse.getElementsByTagName("SUB_LEDGER_TYPE_DESC")[i].firstChild.nodeValue;
	           
	            items[2]=baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[i].firstChild.nodeValue;	            	          
	            if(items[2]=="-")
	            {
	            		items[3]="";//"Not Available";
	            		items[2]="";
	            }
	            else
	            		items[3]=baseResponse.getElementsByTagName("ENAME")[i].firstChild.nodeValue;
	                   
	            
	            items[4]=baseResponse.getElementsByTagName("PAID_TO")[i].firstChild.nodeValue;
	            if(items[4]=="-")
	            	items[4]="";
	            items[5]=baseResponse.getElementsByTagName("BILL_NO")[i].firstChild.nodeValue;
	            if(items[5]=="-")
	            	items[5]="";
	            items[6]=baseResponse.getElementsByTagName("BILL_DATE")[i].firstChild.nodeValue;
	            if(items[6]=="-")
	            	items[6]="";
	            items[7]=baseResponse.getElementsByTagName("AMOUNT")[i].firstChild.nodeValue;
	            
	            
             	items[8]=baseResponse.getElementsByTagName("CHEQUE_OR_DD")[i].firstChild.nodeValue;
	            items[9]=baseResponse.getElementsByTagName("CHEQUE_DD_NO")[i].firstChild.nodeValue;
	            items[10]=baseResponse.getElementsByTagName("CHEQUE_DD_DATE")[i].firstChild.nodeValue;
	            items[11]=baseResponse.getElementsByTagName("TYPE_OF_PAYMENT")[i].firstChild.nodeValue;
	            if(items[11]=="-")
	            	items[11]="";
	            items[12]=baseResponse.getElementsByTagName("WD_PURPOSE_ID")[i].firstChild.nodeValue;
	            if(items[12]=="-")
	            {
	            	items[13]="";
	            	items[12]="";
	            }
	            else	
	            	items[13]=baseResponse.getElementsByTagName("wd_purpose_desc")[i].firstChild.nodeValue;        
	            items[14]=baseResponse.getElementsByTagName("PARTICULARS")[i].firstChild.nodeValue;   
	            if(items[14]=='-')
	            	items[14]="";
	            
	            var mycurrent_row=document.createElement("TR");
	            mycurrent_row.id=seq;
		        
		        
		        var cell=document.createElement("TD");
				        var anc=document.createElement("A");
				        var url="javascript:loadTable('"+mycurrent_row.id+"')";
				        anc.href=url;
				        var txtedit=document.createTextNode("EDIT");
				        anc.appendChild(txtedit);
				        cell.appendChild(anc);
				        mycurrent_row.appendChild(cell);
				        //var i=0;
				        var cell2;
		
		        cell2=document.createElement("TD");   
				      var H_code=document.createElement("input");
				      H_code.type="hidden";
				      H_code.name="head_code";
				      H_code.id="head_code";
				      H_code.value=head_code;
				      cell2.appendChild(H_code);
		              var S_TYPE=document.createElement("input");
		              S_TYPE.type="hidden";
		              S_TYPE.name="s_type";
		              S_TYPE.id="s_type";
		              S_TYPE.value=items[0];
		              cell2.appendChild(S_TYPE);
		              var currentText=document.createTextNode(items[1]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		              
		         cell2=document.createElement("TD"); 
		              var S_CODE=document.createElement("input");
		              S_CODE.type="hidden";
		              S_CODE.name="s_code";
		              S_CODE.id="s_code";
		              S_CODE.value=items[2];
		              cell2.appendChild(S_CODE);
		              var PAID_TO=document.createElement("input");
		              PAID_TO.type="hidden";
		              PAID_TO.name="paid_to";
		              PAID_TO.id="paid_to";
		              PAID_TO.value=items[4];
		              cell2.appendChild(PAID_TO);
		              var currentText=document.createTextNode(items[3]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		        
		         cell2=document.createElement("TD");
		              var REF_NO=document.createElement("input");
		              REF_NO.type="hidden";
		              REF_NO.name="ref_no";
		              REF_NO.id="ref_no";
		              REF_NO.value=items[5];
		              cell2.appendChild(REF_NO);
		              var REF_DT=document.createElement("input");
		              REF_DT.type="hidden";
		              REF_DT.name="ref_dt";
		              REF_DT.id="ref_dt";
		              REF_DT.value=items[6];
		              cell2.appendChild(REF_DT);
		              var currentText=document.createTextNode(items[5]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		              
		         
		        
		         cell2=document.createElement("TD");
		              var AMT=document.createElement("input");
		              AMT.type="hidden";
		              AMT.name="amount";
		              AMT.id="amount";
		              AMT.value=items[7];
		              cell2.appendChild(AMT);
		              var currentText=document.createTextNode(items[7]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		         
		        
		         cell2=document.createElement("TD"); 
		              var CH_DD=document.createElement("input");
		              CH_DD.type="hidden";
		              CH_DD.name="ch_dd";
		              CH_DD.id="ch_dd";
		              CH_DD.value=items[8];
		              cell2.appendChild(CH_DD);
		              var currentText=document.createTextNode(items[8]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		              
		          
		          
		          cell2=document.createElement("TD");
		         
		              var CH_NO=document.createElement("input");
		              CH_NO.type="hidden";
		              CH_NO.name="ch_no";
		              CH_NO.id="ch_no";
		              CH_NO.value=items[9];
		              cell2.appendChild(CH_NO);
		              var currentText=document.createTextNode(items[9]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		              
		            
		              
		          cell2=document.createElement("TD");
		              
		              var CH_DATE=document.createElement("input");
		              CH_DATE.type="hidden";
		              CH_DATE.name="ch_date";
		              CH_DATE.id="ch_date";
		              CH_DATE.value=items[10];
		              cell2.appendChild(CH_DATE);
		              var currentText=document.createTextNode(items[10]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		              
		           
		              
		          cell2=document.createElement("TD");
		              
		              var P_TYPE=document.createElement("input");
		              P_TYPE.type="hidden";
		              P_TYPE.name="p_type";
		              P_TYPE.id="p_type";
		              P_TYPE.value=items[11];
		              cell2.appendChild(P_TYPE);
		              var PM=document.createElement("input");
		              PM.type="hidden";
		              PM.name="part";
		              PM.id="part";
		              PM.value=items[14];
		              cell2.appendChild(PM);
		              var currentText=document.createTextNode(items[11]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		            
		              
		          cell2=document.createElement("TD");
		              
		              var PURPOSE=document.createElement("input");
		              PURPOSE.type="hidden";
		              PURPOSE.name="purpose";
		              PURPOSE.id="purpose";
		              PURPOSE.value=items[12];
		              cell2.appendChild(PURPOSE);
		              var currentText=document.createTextNode(items[13]);
		              cell2.appendChild(currentText);
		              mycurrent_row.appendChild(cell2);
		              
		         
		
		         tbody.appendChild(mycurrent_row);
		        
		         /** Increment Sequence Number */ 
		         seq=seq+1;
        }
    }
}


/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clrForm(param)
{
		// document.getElementById("txtCrea_date").value="";
		 document.getElementById("cmbPayment_type").value="";
		 if(param=="cancel")
		 {
			     if(window.confirm("Do you want to clear ALL fields ?"))
				 {
			    	 	
			    	 	call_clr();
				 } 
		 }
		 else
			 	 call_clr();
}


function call_clr()
{
   
	    document.getElementById("txtVoucher_No").value="";
	    document.getElementById("txtCash_Acc_code").value="";
	    document.getElementById("txtBankAccountNo").value="";
	    document.getElementById("txtBankName").value="";
	    document.getElementById("txtOfficeId").value="";
	    document.getElementById("txtOfficeDet").value="";
	    document.getElementById("txtRemarks").value="";
	    document.getElementById("txtTotalAmt").value="";
	
	
	    
	    var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	    	tbody.deleteRow(0);
	    }
}

function loadOfficeDetails()
{
		document.getElementById("txtOfficeDet").value="";		
        var txtOfficeId=document.getElementById("txtOfficeId").value;     
        var txtLoginOfficeId=document.getElementById("LoginOffice").value;     
        url="../../../../../Imprest_Account_Creation?command=loadOfficeDetails&txtOfficeId="+txtOfficeId+"&txtLoginOfficeId="+txtLoginOfficeId;
        req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
   	    {        	  
     	   	   OfficeDetailsResponse(req);
   	    }   
        req.send(null);            
	           
}

function OfficeDetailsResponse(req)
{
	    if(req.readyState==4)
	    {
			       if(req.status==200)
			       {  
				           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
				           var tagcommand=baseResponse.getElementsByTagName("command")[0];
				           var Command=tagcommand.firstChild.nodeValue;
				           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				           if(flag=="success")
				           {
				        	   	   document.getElementById("txtOfficeDet").value=baseResponse.getElementsByTagName("office_short_name")[0].firstChild.nodeValue;			              
				           }
				           
			      }
	    }    
}

function checkNull()
{
		var tbody=document.getElementById("grid_body");
  
	    if(window.confirm('Do you want to Cancel?'))
	    {
		    	  if(document.getElementById("cmbAcc_UnitCode").value=="")
		    	  {
	    	               alert("Select the Account Unit code");
	    	               return false;    
		    	  }
		    	  if(document.getElementById("cmbOffice_code").value=="")
		    	  {
	    	               alert("Select the Office Code");           
	    	               return false;
		    	  }
		    	  if(document.getElementById("cmbPayment_type").value=="")
		    	  {
	    	               alert("Select Journal Type");           
	    	               return false;    
		    	  }    
		    	  if(document.getElementById("txtCrea_date").value.length==0)
		    	  {
	    	               alert("Enter the Date of Creation");           
	    	               return false;    
		    	  }    
		    	  if(document.getElementById("txtVoucher_No").value.length==0)
		    	  {
	    	               alert("select Voucher Number");           
	    	               return false;    
		    	  }    
		    	  return true;
	    }
	    else
	    		  return false;
}

