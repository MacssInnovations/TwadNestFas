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


function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}

function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert("Flag----->"+flag);
            if(flag=="success")
              {
                 //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
              }
            else if(flag=="failure")
            {
            	datechk.value=""; 
            	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
            	datechk.focus();
            	document.getElementById("butSub").disabled=true;
            	
            	document.getElementById("txtReceipt_No").value="";
                 
            }
            else if(flag=="success1")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
            }
           else if(flag=="failure1")
           {
        	  alert("Document Date is Greater than DATE_OF_CLOSURE");
        	  datechk.value=""; 
          		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
          		datechk.focus();
          		document.getElementById("butSub").disabled=true;
          		document.getElementById("txtReceipt_No").value="";
           }
           else 
        	   {
        	    datechk.value=""; 
        	    alert("Date Value is Null");
           		datechk.focus();
           		document.getElementById("butSub").disabled=true;
           		document.getElementById("txtReceipt_No").value="";
        	   }
        }
    }
}




function check_dd_cheque()
{
     var cheque_no= document.getElementById("txtCheque_DD_NO").value;

     var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     var txtCB_Year=document.getElementById("txtCB_Year").value;

     var url="../../../../../Imprest_Account_Creation?command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year;
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
        {
           handleResponse_cheque_no(req);
        }   
     req.send(null); 
}

function handleResponse_cheque_no(req) 
{ 
  
   if(req.readyState==4)
   {
      
       if(req.status==200)
       {  
           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           var tagcommand=baseResponse.getElementsByTagName("command")[0];
           var Command=tagcommand.firstChild.nodeValue;
           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           if(flag=="failure")
             {
             alert("Cheque No Already Existed");
             document.getElementById("txtCheque_DD_NO").value="";
             document.getElementById("txtCheque_DD_NO").focus();
             }
      }
  }    
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


function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46)
         {
                        //	alert("Position of . "+item.value.indexOf("."));
                        if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                        else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                		// to avoid over flow
                        if(item.value.indexOf(".")<0)
                        {
                        		//			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                        // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0)
                        {
                        		//	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
         }else
         {
                        return false;
         }
}


function valid_amt(field)
{
    
	     amt=field.value;
	     if(amt.indexOf(".")!=amt.lastIndexOf("."))
	     {
		        alert("Enter a Valid Amount");
		        field.value="";
		        field.focus();
	     }
	     if(amt < 0 ) 
	     {
		        alert("Negative Amount Not Allowed");
		        field.value="";
		        field.focus();    
	     }
	     if(parseInt(document.getElementById("txtsub_Amount").value) > parseInt(document.getElementById("txtTotalAmt").value))
	     {
			  	alert("Enter Correct Amount");
			  	document.getElementById("txtsub_Amount").value='';
			  	document.getElementById("txtsub_Amount").focus();
			  	return false
	     }
	     else
	    	 	return true;
    
    
    
}


///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    
	     if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	     {
		    	 
	    	
	    		 var dt=document.imprest_account.txtCrea_date.value;
				 var dat=dt.split("/");		
				 var monthArray =new Array("January", "February", "March", 
		                "April", "May", "June", "July", "August",
		                "September", "October", "November", "December");
				 document.getElementById("txtCB_Year").value=dat[2]; 		
		         document.getElementById("txtCB_Month").value=monthArray[dat[1]-1];
		         call_clr();
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
	     call_clr();
	     if(checkdt(dateCtrl))
	     {
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
			                    // ('load_Voucher_No','null');                 //return true;
			            }
			            else if(flag=="failure")
				        {
				                dateCtrl.value="";
				                alert("Trial Balance Closed");//return false;//
				                dateCtrl.focus();
				                // document.getElementById("txtVoucher_No").value="";
				        }
			            else if(flag=="finyear")
			            {
			                    // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
				                dateCtrl.value="";
				                alert("Cash Book Control Not Found ");//return false;//
				                dateCtrl.focus();
				                //document.getElementById("txtVoucher_No").value="";     
			            }
			            dateCheck(dateCtrl);  
		        }
		 }
}


//////////////FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
var wininterval;
var callemp=0;
function servicepopup_mst()
{
		 if (winemp && winemp.open && !winemp.closed) 
	     {
		        winemp.resizeTo(500,600);
		        winemp.moveTo(200,200); 
		        winemp.focus();
		        return ;
	     }
	     else
	     {
	    	 	winemp=null
	     }
		 var txtLoginOfficeId=document.getElementById("LoginOffice").value;     
	     winemp= window.open("../../../../../org/FAS/FAS1/CommonControls/jsps/Com_ControllingOffice.jsp?LogginOffice="+txtLoginOfficeId,"Officesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
	     winemp.moveTo(250,250);  
	     winemp.focus();
    
}

/*
function doParentOff()
{				
		 document.imprest_account.txtOfficeId.value=document.imprest_account.LoginOffice.value;
		 loadOfficeDetails();
		 
}*/

///////////////////////////////////End////////////////////////////////////////////////



/*
* Check Account Head Code and Bank Account Number 
*/

function account_head_code()
{
	    var acc_head=document.getElementById("txtAcc_HeadCode").value;
	    var url="../../../../../Cash_Receipt_Creation.view?Command=Acc_Head_Check&acc_head="+acc_head;
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    		Account_Headcode_Check(req);
	    }   
	    req.send(null);
}

function Account_Headcode_Check(req)
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
				              alert("You are not allow to use this Account Head Code");
				              document.getElementById("txtAcc_HeadCode").value="";
				              document.getElementById("txtAcc_HeadDesc").value="";
			              
			           }
		      }
	    }    
}


function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    		alert("Please Enter Paticulars below 200 characters");
	    }	 
}


var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function load_grid(cmd)
{
	
	    if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
		        alert("Enter A/c Head Code");
		        return false;
        }                
        var acc=document.getElementById("txtAcc_HeadCode").value;
        var kk=acc.charAt(0)+acc.charAt(1);
        if(kk=="82")
        {
	          	if(acc !="820102"  && acc !="820103")
	          	{		          
			           alert("This A/C code can not be used here ");
			           document.getElementById("txtAcc_HeadCode").value="";
			           document.getElementById("txtAcc_HeadDesc").value="";
			           return false;
	          	}  
        }    
        if(document.getElementById("cmbSL_type").value=="")
        {
        		alert("Select Sub Ledger Type");
        		return false;
        }
        if(document.getElementById("cmbSL_Code").value=="")
        {
        		alert("Select Sub Ledger Code");
        		return false;
        }
        if(document.getElementById("txtPaidTo").value.length==0)
        {
	        	alert("Enter Paid to");
	        	return false;
        }              
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
	            alert("Enter the Amount ");
	            return false;    
        }
        if(document.getElementById("txtCheque_DD_NO").value.length==0)
        {
	            alert("Enter Cheque or DD Number ");           
	            return false;    
        }
        if(document.getElementById("txtDDDate").value.length==0)
        {
	            alert("Enter Cheque or DD Date ");           
	            return false;    
        }
        var tbody=document.getElementById("grid_body");                            
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;     
        var items=new Array();
          
        items[0]=document.getElementById("cmbSL_type").value;        
        if(document.getElementById("cmbSL_type").value=="")
        {
                items[1]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        }
        else
        		items[1]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;         
        items[2]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        		items[3]="";//"Not Available";
        }
        else
        		items[3]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
               
        
        items[4]=document.getElementById("txtPaidTo").value;
        items[5]=document.getElementById("ho_ref_no").value;
        items[6]=document.getElementById("ho_ref_date").value;
        items[7]=document.getElementById("txtsub_Amount").value;
        
        
        if(document.imprest_account.cd[0].checked==true)
        		items[8]="C";
        else
        		items[8]="D";
        
        items[9]=document.getElementById("txtCheque_DD_NO").value;
        items[10]=document.getElementById("txtDDDate").value;
        if(document.imprest_account.payment_type[0].checked==true)
        		items[11]="N";
        else
        		items[11]="R";
        items[12]=document.getElementById("drawl_purpose").value;
        if(document.getElementById("drawl_purpose").value=="")
        		items[13]="";
        else	
        		items[13]=document.getElementById("drawl_purpose").options[document.getElementById("drawl_purpose").selectedIndex].text;        
        items[14]=document.getElementById("particulars").value;
        
     
      
        var recoup_body=document.getElementById("recoup_body");
        
        items[15]="";
   		items[16]="";
   		items[17]="";
   		items[18]="";
   		var re_count=0;
        if(document.imprest_account.payment_type[1].checked==true)
        {
        		if(recoup_body.rows.length>1)
        		{
		        	   for(i=0;i<recoup_body.rows.length;i++)
		               {
		                      if(document.imprest_account.sel[i].checked==true)
		                      {
		                    	   		items[15]=document.imprest_account.recoup_voucher[i].value;
		                    	   		items[16]=document.imprest_account.recoup_voucher_dt[i].value;
		                    	   		items[17]=document.imprest_account.recoup_voucher_amt[i].value;
		                    	   		items[18]=document.imprest_account.sno[i].value;
		                    	   		re_count++;
		                      }
		               }
        		}
        		else if(recoup_body.rows.length==1)
        		{
        			   if(document.imprest_account.sel.checked==true)
        			   {
        				   	  items[15]=document.imprest_account.recoup_voucher.value;
        				   	  items[16]=document.imprest_account.recoup_voucher_dt.value;
        				   	  items[17]=document.imprest_account.recoup_voucher_amt.value;
        				   	  items[18]=document.imprest_account.sno.value;
        				   	  re_count++;
        				   	 
        			   }        			   
        		}
        		else
        		{
        			   alert("No Voucher Available for Recoup");
        			   return false;
        		}
        		
        		if(re_count==0)
 			   	{
 				   	   alert("Select Recoup Voucher Number");
 				   	   return false;
 			   	}
        }
        
        var mycurrent_row=document.createElement("TR");    

        
        if(cmd=="ADD_GRID")
        {			
        	
        	
	        var tbody=document.getElementById("grid_body");
	     	//alert("tbody"+tbody.rows.length);
	     	if(tbody.rows.length>0)
	     	{
	     		var tt=tbody.rows[0];
	     	//	alert("tt"+tt);
	     		 var rcells=tt.cells;
	     		// alert("rcells"+rcells);
	     	//	 alert("99999"+rcells.item(6).lastChild.nodeValue);
	     		 var cqNo=document.getElementById("txtCheque_DD_NO").value;
	         	if(cqNo==rcells.item(6).lastChild.nodeValue)
	         	{
	         		alert("Cheque No Already Existed");
	         		document.getElementById("txtCheque_DD_NO").value="";
	         		return false;
	         	}
	         	else
	         	{
	         //		alert("cellllllllll");
	         		var mycurrent_row=document.createElement("TR");    
	         		mycurrent_row.id=seq;			       
	         	    var cell=document.createElement("TD");			        
	         	    var anc=document.createElement("A");
	         	    var url="javascript:loadTable("+mycurrent_row.id+")";
	         	    anc.href=url;
	         	    var txtedit=document.createTextNode("EDIT");
	         	    anc.appendChild(txtedit);
	         	    cell.appendChild(anc);
	         	    mycurrent_row.appendChild(cell);
	         	    var i=0;
	         	    var cell2;

	         	    cell2=document.createElement("TD");   
	         	     
	         	    var H_code=document.createElement("input");
	         	    H_code.type="hidden";
	         	    H_code.name="head_code";
	         	    H_code.id="head_code";
	         	    H_code.value=document.getElementById("txtAcc_HeadCode").value;
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
	         	    
	         	    cell2=document.createElement("TD");
	         	       
	         	    var voucher_no=document.createElement("input");
	         	    voucher_no.type="hidden";
	         	    voucher_no.name="recoup_vou_no";
	         	    voucher_no.id="recoup_vou_no";
	         	    voucher_no.value=items[15];
	         	    cell2.appendChild(voucher_no);
	         	    var sno=document.createElement("input");
	         	    sno.type="hidden";
	         	    sno.name="sl_no";
	         	    sno.id="sl_no";
	         	    sno.value=items[18];
	         	    cell2.appendChild(sno);
	         	    var currentText=document.createTextNode(items[15]);
	         	    cell2.appendChild(currentText);
	         	    mycurrent_row.appendChild(cell2);
	         	        
	         	    cell2=document.createElement("TD");
	         	    
	         	    var voucher_dt=document.createElement("input");
	         	    voucher_dt.type="hidden";
	         	    voucher_dt.name="recoup_vou_dt";
	         	    voucher_dt.id="recoup_vou_dt";
	         	    voucher_dt.value=items[16];
	         	    cell2.appendChild(voucher_dt);
	         	    var voucher_amt=document.createElement("input");
	         	    voucher_amt.type="hidden";
	         	    voucher_amt.name="recoup_vou_amt";
	         	    voucher_amt.id="recoup_vou_amt";
	         	    voucher_amt.value=items[17];
	         	    cell2.appendChild(voucher_amt);
	         	    var currentText=document.createTextNode(items[16]);
	         	    cell2.appendChild(currentText);
	         	    mycurrent_row.appendChild(cell2);
	         	      
	         	    

	         	    tbody.appendChild(mycurrent_row);
	         	    clearall();
	         	  
	         	 
	         	    seq=seq+1;
	         	}
	     	}
	     	else
	     	{
	     		
	     		var mycurrent_row=document.createElement("TR");    
	     		mycurrent_row.id=seq;			       
	     	    var cell=document.createElement("TD");			        
	     	    var anc=document.createElement("A");
	     	    var url="javascript:loadTable("+mycurrent_row.id+")";
	     	    anc.href=url;
	     	    var txtedit=document.createTextNode("EDIT");
	     	    anc.appendChild(txtedit);
	     	    cell.appendChild(anc);
	     	    mycurrent_row.appendChild(cell);
	     	    var i=0;
	     	    var cell2;

	     	    cell2=document.createElement("TD");   
	     	     
	     	    var H_code=document.createElement("input");
	     	    H_code.type="hidden";
	     	    H_code.name="head_code";
	     	    H_code.id="head_code";
	     	    H_code.value=document.getElementById("txtAcc_HeadCode").value;
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
	     	    
	     	    cell2=document.createElement("TD");
	     	       
	     	    var voucher_no=document.createElement("input");
	     	    voucher_no.type="hidden";
	     	    voucher_no.name="recoup_vou_no";
	     	    voucher_no.id="recoup_vou_no";
	     	    voucher_no.value=items[15];
	     	    cell2.appendChild(voucher_no);
	     	    var sno=document.createElement("input");
	     	    sno.type="hidden";
	     	    sno.name="sl_no";
	     	    sno.id="sl_no";
	     	    sno.value=items[18];
	     	    cell2.appendChild(sno);
	     	    var currentText=document.createTextNode(items[15]);
	     	    cell2.appendChild(currentText);
	     	    mycurrent_row.appendChild(cell2);
	     	        
	     	    cell2=document.createElement("TD");
	     	    
	     	    var voucher_dt=document.createElement("input");
	     	    voucher_dt.type="hidden";
	     	    voucher_dt.name="recoup_vou_dt";
	     	    voucher_dt.id="recoup_vou_dt";
	     	    voucher_dt.value=items[16];
	     	    cell2.appendChild(voucher_dt);
	     	    var voucher_amt=document.createElement("input");
	     	    voucher_amt.type="hidden";
	     	    voucher_amt.name="recoup_vou_amt";
	     	    voucher_amt.id="recoup_vou_amt";
	     	    voucher_amt.value=items[17];
	     	    cell2.appendChild(voucher_amt);
	     	    var currentText=document.createTextNode(items[16]);
	     	    cell2.appendChild(currentText);
	     	    mycurrent_row.appendChild(cell2);
	     	      
	     	    

	     	    tbody.appendChild(mycurrent_row);
	     	    clearall();
	     	  
	     	 
	     	    seq=seq+1;
	     	}
		 		   
        }
        else
        {
        		   
		           var r=document.getElementById(com_id);
		           var rcells=r.cells;
            
		           if(tbody.rows.length==1)
		           {
		           	
			             	document.imprest_account.s_type.value=document.getElementById("cmbSL_type").value;        	
			             	document.imprest_account.s_code.value=document.getElementById("txtEmpID_trs").value;
			             	document.imprest_account.paid_to.value=document.getElementById("txtPaidTo").value;
			             	document.imprest_account.ref_no.value=document.getElementById("ho_ref_no").value;
			             	document.imprest_account.ref_dt.value=document.getElementById("ho_ref_date").value;
			             	document.imprest_account.amount.value=document.getElementById("txtsub_Amount").value;
			             	if(document.imprest_account.cd[0].checked==true)
			             		 document.imprest_account.ch_dd.value="C";
			                else
			                	 document.imprest_account.ch_dd.value="D";
			             	document.imprest_account.ch_no.value=document.getElementById("txtCheque_DD_NO").value;
			             	document.imprest_account.ch_date.value=document.getElementById("txtDDDate").value;
			             	if(document.imprest_account.payment_type[0].checked==true)
			                 	 document.imprest_account.p_type.value="N";
			                else
			                	 document.imprest_account.p_type.value="R";
			             	document.imprest_account.part.value=document.getElementById("particulars").value;
			             	document.imprest_account.purpose.value=document.getElementById("drawl_purpose").value;
			             	document.imprest_account.recoup_vou_no.value=items[15];
			             	document.imprest_account.recoup_vou_dt.value=items[16];
			             	document.imprest_account.recoup_vou_amt.value=items[17];
			             	document.imprest_account.sl_no.value=items[18];
			             	
			             	
		           }
		           else
		           {
		            	 
		            	 
		            	    document.imprest_account.s_type[com_id].value=document.getElementById("cmbSL_type").value;        	
			             	document.imprest_account.s_code[com_id].value=document.getElementById("txtEmpID_trs").value;
			             	document.imprest_account.paid_to[com_id].value=document.getElementById("txtPaidTo").value;
			             	document.imprest_account.ref_no[com_id].value=document.getElementById("ho_ref_no").value;
			             	document.imprest_account.ref_dt[com_id].value=document.getElementById("ho_ref_date").value;
			             	document.imprest_account.amount[com_id].value=document.getElementById("txtsub_Amount").value;
			             	if(document.imprest_account.cd[0].checked==true)
			             		 document.imprest_account.ch_dd[com_id].value="C";
			                else
			                	 document.imprest_account.ch_dd[com_id].value="D";
			             	document.imprest_account.ch_no[com_id].value=document.getElementById("txtCheque_DD_NO").value;
			             	document.imprest_account.ch_date[com_id].value=document.getElementById("txtDDDate").value;
			             	if(document.imprest_account.payment_type[0].checked==true)
			                 	 document.imprest_account.p_type[com_id].value="N";
			                else
			                	 document.imprest_account.p_type[com_id].value="R";
			             	document.imprest_account.part[com_id].value=document.getElementById("particulars").value;
			             	document.imprest_account.purpose[com_id].value=document.getElementById("drawl_purpose").value;
			             	document.imprest_account.recoup_vou_no[com_id].value=items[15];
			             	document.imprest_account.recoup_vou_dt[com_id].value=items[16];
			             	document.imprest_account.recoup_vou_amt[com_id].value=items[17];
			             	document.imprest_account.sl_no[com_id].value=items[18];
			                 
			                 
		           }
                   try{rcells.item(1).lastChild.nodeValue=items[1];}catch(e){}
                                    
                   try{rcells.item(2).lastChild.nodeValue=items[3];}catch(e){}
                    
                   try{rcells.item(3).lastChild.nodeValue=items[5];}catch(e){}
                
                   try{rcells.item(4).lastChild.nodeValue=items[7];}catch(e){}
                
                   try{rcells.item(5).lastChild.nodeValue=items[8];}catch(e){}
                 
                   try{rcells.item(6).lastChild.nodeValue=items[9];}catch(e){}
                   
                   try{rcells.item(7).lastChild.nodeValue=items[10];}catch(e){}
                 
                   try{rcells.item(8).lastChild.nodeValue=items[11];}catch(e){}
                    
                   try{rcells.item(9).lastChild.nodeValue=items[13];}catch(e){}
                   
                   try{rcells.item(10).lastChild.nodeValue=items[15];}catch(e){}
                   
                   try{rcells.item(11).lastChild.nodeValue=items[16];}catch(e){}
		                    
		           alert("Record Updated");
		           clearall();
        }
}
   
//function callInnerMeth()
//{alert("come");
//
//}

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



/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////
function loadTable(scod)
{
        
		com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        var tbody=document.getElementById("grid_body");
        var recoup_body=document.getElementById("recoup_body");
        //alert(tbody.rows.length);
        if(tbody.rows.length==1)
        {
    	
        	       document.getElementById("cmbSL_type").value=document.imprest_account.s_type.value;        	
        	       document.getElementById("txtEmpID_trs").value=document.imprest_account.s_code.value;
        	       trs_employee(document.imprest_account.s_code.value);        	        	       
        	       document.getElementById("txtPaidTo").value=document.imprest_account.paid_to.value;
        	       document.getElementById("ho_ref_no").value=document.imprest_account.ref_no.value;
        	       document.getElementById("ho_ref_date").value=document.imprest_account.ref_dt.value;
        	       document.getElementById("txtsub_Amount").value=document.imprest_account.amount.value;        	               	     
        	       if(document.imprest_account.ch_dd.value=="C")
        	    	   	document.imprest_account.cd[0].checked=true;
        	       else
        	    	   	document.imprest_account.cd[1].checked=true;        	               	       
        	       document.getElementById("txtCheque_DD_NO").value=document.imprest_account.ch_no.value;
        	       document.getElementById("txtDDDate").value=document.imprest_account.ch_date.value;        	               	       
        	       if(document.imprest_account.p_type.value=="N")
        	    	   	document.imprest_account.payment_type[0].checked=true;
        	       else
        	    	   	document.imprest_account.payment_type[1].checked=true;        	               	       
        	       document.getElementById("particulars").value=document.imprest_account.part.value;
        	       document.getElementById("drawl_purpose").value=document.imprest_account.purpose.value;
        	       if(document.imprest_account.payment_type[1].checked==true)
        	    	   loadRecoupDetails('edit',scod);
        	       else
        	    	   loadRecoupDetails('create','null');
        	    
        	       
        }
        else
        {
        	
    			   document.getElementById("cmbSL_type").value=document.imprest_account.s_type[scod].value;        	
    			   document.getElementById("txtEmpID_trs").value=document.imprest_account.s_code[scod].value;
    			   trs_employee(document.imprest_account.s_code[scod].value);               			   
    			   document.getElementById("txtPaidTo").value=document.imprest_account.paid_to[scod].value;
    			   document.getElementById("ho_ref_no").value=document.imprest_account.ref_no[scod].value;
    			   document.getElementById("ho_ref_date").value=document.imprest_account.ref_dt[scod].value;
    			   document.getElementById("txtsub_Amount").value=document.imprest_account.amount[scod].value;
    			   if(document.imprest_account.ch_dd[scod].value=="C")
    				    document.imprest_account.cd[0].checked=true;
    			   else
    				   	document.imprest_account.cd[1].checked=true;
    			   document.getElementById("txtCheque_DD_NO").value=document.imprest_account.ch_no[scod].value;
    			   document.getElementById("txtDDDate").value=document.imprest_account.ch_date[scod].value;
    			   if(document.imprest_account.p_type[scod].value=="N")
    				   	document.imprest_account.payment_type[0].checked=true;
    			   else
    				   	document.imprest_account.payment_type[1].checked=true;
    			   document.getElementById("particulars").value=document.imprest_account.part[scod].value;
    			   document.getElementById("drawl_purpose").value=document.imprest_account.purpose[scod].value;
    			   
    			   if(document.imprest_account.payment_type[1].checked==true)
        	    	   loadRecoupDetails('edit',scod);
        	       else
        	    	   loadRecoupDetails('create','null');
    			 
        }
        document.imprest_account.cmdupdate.style.display='block';
	    document.imprest_account.cmddelete.disabled=false;
	    document.imprest_account.cmdadd.style.display='none';
}


/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
	     document.getElementById("ho_ref_no").value="";    
	     document.getElementById("ho_ref_date").value="";     
	     //document.getElementById("txtPaidTo").value="";     
	     document.getElementById("txtsub_Amount").value="";     
	     document.getElementById("txtCheque_DD_NO").value="";
	     document.getElementById("txtDDDate").value="";     
	     document.getElementById("drawl_purpose").selectedIndex=0;                    
	     document.getElementById("particulars").value="";       
		
		 document.imprest_account.payment_type[0].checked=true;
	     document.getElementById("recoupDiv").style.display="none";
	     document.getElementById("txtEmpID_trs").value="";
		 document.getElementById("txtPaidTo").value="";
	     var cmbSL_Code=document.getElementById("cmbSL_Code");
	     clear_Combo(cmbSL_Code);
	     
	     document.imprest_account.cmdupdate.style.display='none';
		 document.imprest_account.cmddelete.disabled=true;
		 document.imprest_account.cmdadd.style.display='block';
		 
}




function clrForm()
{
	     if(window.confirm("Do you want to clear ALL fields ?"))
		 {
	    	   	call_clr();
		 }
}

function call_clr()
{
	    document.getElementById("txtTotalAmt").value="";
	    document.getElementById("txtOfficeId").value="";
	    document.getElementById("txtOfficeDet").value="";	    
	    document.getElementById("txtRemarks").value="";
	    document.getElementById("txtTotalAmt").value="";
	    document.getElementById("cmbSL_Code").value="";
	    document.getElementById("txtEmpID_trs").value="";
	    document.getElementById("txtPaidTo").value="";
	    
	    var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	    		tbody.deleteRow(0);
	    }
	    document.getElementById("txtOfficeId").value=document.getElementById("LoginOffice").value;
	    loadOfficeDetails();
            
}


//------------------------------loadSLType()-----------------------------------
function loadSLType()
{
	 
		document.getElementById("cmbSL_type").value=7;
	    document.getElementById("emplist_div_trans").style.display="block";
	         
}



var winAcc_Bank_No;     // Fteching Account Head No and Bank  No

function MainAccNopopup()
{
	    Bank_popup_flag=true;
	    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) 
	    {
			        winAcc_Bank_No.resizeTo(500,500);
			        winAcc_Bank_No.moveTo(250,250); 
			        winAcc_Bank_No.focus();
	    }
	    else
	    {
	        	    winAcc_Bank_No=null;
	    }
	    //var Office_code=document.getElementById("cmbOffice_code").value;  
	    var txtModule_Type="MF031";
	    var cr_dr_indi="CR";
	    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	     
	    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    winAcc_Bank_No.moveTo(250,250);  
	    winAcc_Bank_No.focus();
}

function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
   
        document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
        document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
        document.getElementById("txtBankID").value=bankid;
        document.getElementById("txtBranchID").value=br_id;
        document.getElementById("txtBankName").value=B_name;
        
}



function loadOfficeDetails()
{
		//alert(document.getElementById("txtOfficeId").value);
		document.getElementById("txtOfficeDet").value="";
		var flag=nullCheck();	
	    if(flag==true)
	    { 
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
	    else
	    {
	    		   alert("Please Enter Office ID");
	    }            
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
				           else
				           {
				        	   	   alert("Invalid Office Id");
				        	   	   document.getElementById("txtOfficeId").value="";
				           }
			      }
	    }    
}

//Null check Function 
function nullCheck()
{
	    var txtOfficeId = document.getElementById("txtOfficeId").value;
	    if ( txtOfficeId=="")
	    	return false; 
	    else 
	    	return true;

}

function loadOPRAcc()
{
		setTimeout('LoadOprAccountHead("CR","MF031","OPR")',900);
}


function emp_servicepopup()
{
	    if (winemp && winemp.open && !winemp.closed) 
	    {
			       winemp.resizeTo(500,500);
			       winemp.moveTo(250,250); 
			       winemp.focus();
	    }
	    else
	    {
	    		   winemp=null
	    }	 
	    var OfficeId=document.getElementById("txtOfficeId").value;
	    if(OfficeId=="")
	    {
	    		   alert("Enter Office Id");
	    		   document.getElementById("txtEmpID_trs").value="";
	    		   return false;
	    }
	    if(document.getElementById("cmbSL_type").value=="")
	    {
	    		   alert("Select Sub Ledger Type");
	    		   document.getElementById("txtEmpID_trs").value="";
	    		   return false;
	    }
	    else
	    {
				   winemp= window.open("../../../../../org/FAS/FAS1/CommonControls/jsps/Common_EmpServicePopup.jsp?OfficeId="+OfficeId,"Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
				   winemp.moveTo(250,250);  
				   winemp.focus();
	    }
    
}

function doParentEmp(emp)
{
		document.getElementById("txtEmpID_trs").value=emp;
		load_EmployeeDet();
}

function load_employee(emp)
{
	 	var OfficeId=document.getElementById("txtOfficeId").value;
	    if(OfficeId=="")
	    {
	    		   alert("Enter Office Id");
	    		   document.getElementById("txtEmpID_trs").value="";
	    		   return false;
	    }
	    if(document.getElementById("cmbSL_type").value=="")
	    {
	    		   alert("Select Sub Ledger Type");
	    		   document.getElementById("txtEmpID_trs").value="";
	    		   return false;
	    }
	    else
	    {
	    		   document.getElementById("txtEmpID_trs").value=emp;
	    		   load_EmployeeDet();
	    }
	
}

function hideRecoup()
{
	var AdvType=document.getElementById("cmbPayment_type").value;
	if(AdvType=="T")
	{
		var rId=document.getElementById("recoupId");
		rId.style.display="none";
		var nId=document.getElementById("newId");
		nId.style.display="block";
	}
	else if(AdvType=="I")
	{
		var rId=document.getElementById("recoupId");
		rId.style.display="block";
		var nId=document.getElementById("newId");
		nId.style.display="block";
	}
	
}

function load_EmployeeDet()
{  
		document.getElementById("txtPaidTo").value="";
		var emp_id=document.getElementById("txtEmpID_trs").value;
        var txtOfficeId=document.getElementById("txtOfficeId").value;
        var url="../../../../../Imprest_Account_Creation?command=Load_SL_Code&txtOfficeId="+txtOfficeId+"&txtEmpID="+emp_id;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
        		   Employee_handleResponse(req);
        }   
        req.send(null);                       
}


function Employee_handleResponse(req)
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
				       		   	   var cmbSL_Code=document.getElementById("cmbSL_Code");      // value assigned to same local variable name
				       	         
				       	           var items_id=new Array();
				       	           var items_name=new Array();
				       	        
				       	           var cid=baseResponse.getElementsByTagName("cid");
				       	           var cname=baseResponse.getElementsByTagName("cname");
				       	           for(var k=0;k<cid.length;k++)
				       	           {
				       	                	items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
				       	                	items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;				       	               
				       	           }
				       	           clear_Combo(cmbSL_Code);
				       	           for(var k=0;k<items_id.length;k++)
				       	           {   
				       	        	   		var option=document.createElement("OPTION");
				       	        	   		option.text=items_name[k];
				       	        	   		option.value=items_id[k];
				       	        	   		try
				       	        	   		{
				       	        	   				cmbSL_Code.add(option);
				       	        	   		}
				       	        	   		catch(errorObject)
				       	        	   		{
				       	        	   				cmbSL_Code.add(option,null);
				       	        	   		}
				       	           }
				       	           document.getElementById("cmbSL_Code").value=items_id[0];
				       	           document.getElementById("txtPaidTo").value=items_name[0];	
				       	           loadRecoupDetails('create','null');
				                
		       		   	   }
		       		   	   else
		       		   	   {
		       		   		   	   alert("Selected Employee Id is invalid");
		       		   		   	   document.getElementById("txtEmpID_trs").value="";
		       		   		       var cmbSL_Code=document.getElementById("cmbSL_Code");
		       		   		       clear_Combo(cmbSL_Code);
		       		   	   }
		       	   }
    	}    
		
}



function checkRecoup()
{
	
		if(document.imprest_account.payment_type[0].checked==true)
				   document.getElementById("recoupDiv").style.display="none";
		else
				   document.getElementById("recoupDiv").style.display="block";
}


function loadRecoupDetails(command,param)
{
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var txtCrea_date=document.getElementById("txtCrea_date").value;		
		var txtEmpID=document.getElementById("txtEmpID_trs").value;
		var txtMode_of_creat=document.getElementById("cmbPayment_type").value;		
	    var url="../../../../../Imprest_Account_Creation?command=loadRecoupDetails&Option=Create&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtOfficeId="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&txtEmpID="+txtEmpID+"&txtMode_of_creat="+txtMode_of_creat;	    
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    		   process_handleResponse(req,command,param);
	    }   
	    req.send(null);                       
}



function process_handleResponse(req,cmd,param)
{
	    if(req.readyState==4)
	    {
			       if(req.status==200)
			       {  
				           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
				           var tagcommand=baseResponse.getElementsByTagName("command")[0];
				           var Command=tagcommand.firstChild.nodeValue;
				           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				           
				           var items=new Array();				          
				           if(flag=="success")
				           {
				        	 //  document.getElementById("recoupDiv").style.display="block";
				        	   	   var voucher=baseResponse.getElementsByTagName("voucher_no");				        	   	  
				        	   	   var tbody=document.getElementById("recoup_body");
				        	   	   var tb=document.getElementById("grid_body");
				        	   	 
				        	   	   try{tbody.innerHTML="";
				        	   	   }catch(e) {tbody.innerText="";
				        	   	   }
			        	   	   
				        	   	   for(var i=0;i<voucher.length;i++)
				        	   	   {
				        	   		 
				        	   		   		items[0]=baseResponse.getElementsByTagName("voucher_no")[i].firstChild.nodeValue;
				        	   		   		items[1]=baseResponse.getElementsByTagName("payment_dt")[i].firstChild.nodeValue;
				        	   		   		items[2]=baseResponse.getElementsByTagName("purpose")[i].firstChild.nodeValue;
				        	   		   		items[3]=baseResponse.getElementsByTagName("amount")[i].firstChild.nodeValue;	
				        	   		   		items[4]=baseResponse.getElementsByTagName("slno")[i].firstChild.nodeValue;		
				        	   		   		var seq=i+1;
				        	   		   		
				        	   		   		
				        	   		   		var availability=false;
				        	   		   		////////////////////////////  checking with grid ////////////////////////////////////////						                    
						                    if(tb.rows.length>1)
				        	        		{
						                    		
								        	        	for(var j=0;j<tb.rows.length;)
								        	    	   	{
								        	    	   			   
								        	        				if(document.imprest_account.recoup_vou_no[j].value==items[0] && document.imprest_account.sl_no[j].value==items[4] && document.imprest_account.recoup_vou_dt[j].value==items[1])
								        	    	   			    {								        	    	   				 
								        	    	   			    		availability=true;
								        	    	   			    		j=tb.rows.length;       // if any one of the grid row is matching with recoup voucher number ,the loop exit, no need to check with next row in the grid
								        	    	   			    }
								        	    	   			    else
								        	    	   			    		j++;
								        	    	   	}
				        	        		}
				        	        		else if(tb.rows.length==1)
				        	        		{						        	        			
				        	        					if(document.imprest_account.recoup_vou_no.value==items[0] && document.imprest_account.sl_no.value==items[4] && document.imprest_account.recoup_vou_dt.value==items[1])
					        	    	   			 	{					        	    	   				 
						        	        						availability=true;   	    	   				 	 					        	    	   				 		
					        	    	   			 	}
				        	        		} 						                    
						                    //////////////////////////////////////  End  ////////////////////////////////////////////
						                    
						                    
						                   
				        	   		   		
				        	   		   		var mycurrent_row=document.createElement("TR");        
				        	   		   		var cell1 =document.createElement("TD"); 
						                    var hidden1="";
						                    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)  // for internet explorer
						                    {
						                            	if(availability)
						                            			    hidden1=document.createElement("<input type='radio' name='sel' id='sel' value="+seq+" disabled='disabled'>");
						                            	else
						                            				hidden1=document.createElement("<input type='radio' name='sel' id='sel' value="+seq+">");
						                    }
						                    else   // for mizila
						                    {    
							                          	hidden1=document.createElement("input");    
							                          	hidden1.type="radio";             
							                          	hidden1.name="sel";
							                          	hidden1.id="sel";
							                          	if(availability)
							                          				hidden1.disabled="disabled";						                          	
							                          	hidden1.value=seq;
						                    }
						                    cell1.appendChild(hidden1);
						                    mycurrent_row.appendChild(cell1);
						                    					                    						                   						                    
						                    
						                    var cell2 =document.createElement("TD"); 
						                    var r_voucher=document.createElement("input");
						                    r_voucher.type="hidden";
						                    r_voucher.name="recoup_voucher";
						                    r_voucher.id="recoup_voucher";
						                    r_voucher.value=items[0];
						 	               	cell2.appendChild(r_voucher);
						                    var hidden2=document.createTextNode(items[0]);                        
						                    cell2.appendChild(hidden2);  
						                    mycurrent_row.appendChild(cell2);  
						                    
						                    var cell2 =document.createElement("TD"); 
						                    var sno=document.createElement("input");
						                    sno.type="hidden";
						                    sno.name="sno";
						                    sno.id="sno";
						                    sno.value=items[4];
						 	               	cell2.appendChild(sno);
						                    var hidden2=document.createTextNode(items[4]);                        
						                    cell2.appendChild(hidden2);  
						                    mycurrent_row.appendChild(cell2);  
						                    
						                    var cell3 =document.createElement("TD");  
						                    var r_voucher_dt=document.createElement("input");
						                    r_voucher_dt.type="hidden";
						                    r_voucher_dt.name="recoup_voucher_dt";
						                    r_voucher_dt.id="recoup_voucher_dt";
						                    r_voucher_dt.value=items[1];
						                    cell3.appendChild(r_voucher_dt);
						                    var hidden3=document.createTextNode(items[1]);                        
						                    cell3.appendChild(hidden3);  
						                    mycurrent_row.appendChild(cell3);  
						                            
						                            
						                    var cell4 =document.createElement("TD"); 						                    
						                    var hidden4=document.createTextNode(items[2]);                        
						                    cell4.appendChild(hidden4);  
						                    mycurrent_row.appendChild(cell4);  
						                           
						                    var cell5 =document.createElement("TD");   
						                    var r_voucher_amt=document.createElement("input");
						                    r_voucher_amt.type="hidden";
						                    r_voucher_amt.name="recoup_voucher_amt";
						                    r_voucher_amt.id="recoup_voucher_amt";
						                    r_voucher_amt.value=items[3];
						                    cell4.appendChild(r_voucher_amt);
						                    var hidden5=document.createTextNode(items[3]);                        
						                    cell5.appendChild(hidden5);  
						                    mycurrent_row.appendChild(cell5);  
						                    
						                    tbody.appendChild(mycurrent_row); 
				        	   	   }
				        	   	   
				        	   	   if(cmd=="edit")
				        	   	   {
				        	   		   		/*var tbody=document.getElementById("recoup_body");
				        	   		   		var tb=document.getElementById("grid_body");	*/	
				        	   		   
				        	   		   
						        	        if(tb.rows.length==1)  // table body checking
						        	        {
						        	        	  	// Check whether recoup tbody have more than one row //
						        	        		if(tbody.rows.length>1)
						        	        		{
									        	        		for(var i=0;i<tbody.rows.length;i++)
									        	        	  	{						        	    	   			 	
									        	    	   			 	if(document.imprest_account.recoup_vou_no.value==document.imprest_account.recoup_voucher[i].value && document.imprest_account.sl_no.value==document.imprest_account.sno[i].value && document.imprest_account.recoup_vou_dt.value==document.imprest_account.recoup_voucher_dt[i].value)
									        	    	   			 	{
									        	    	   				 
									        	    	   				 	    document.imprest_account.sel[i].checked=true;	
									        	    	   				 	    document.imprest_account.sel[i].disabled=false;											        	    	   				 	   
									        	    	   				 		
									        	    	   			 	}
									        	        	  	}
									        	        	  	
						        	        		}
						        	        		else
						        	        		{
								        	        			if(document.imprest_account.recoup_vou_no.value==document.imprest_account.recoup_voucher.value && document.imprest_account.sl_no.value==document.imprest_account.sno.value && document.imprest_account.recoup_vou_dt.value==document.imprest_account.recoup_voucher_dt.value)
							        	    	   			 	{
							        	    	   				 
							        	    	   				 	    document.imprest_account.sel.checked=true;
							        	    	   				 	    document.imprest_account.sel.disabled=false;							        	    	   				 	    
							        	    	   				 		
							        	    	   			 	}
						        	        		}
						        	        		
						        	        	 		
						        	        }
						        	        else
						        	        {
							        	        	// Check whether recoup tbody have more than one row //
						        	        		if(tbody.rows.length>1)
						        	        		{
										        	        	for(var i=0;i<tbody.rows.length;i++)
										        	    	   	{
										        	    	   			    if(document.imprest_account.recoup_vou_no[param].value==document.imprest_account.recoup_voucher[i].value && document.imprest_account.sl_no[param].value==document.imprest_account.sno[i].value && document.imprest_account.recoup_vou_dt[param].value==document.imprest_account.recoup_voucher_dt[i].value)
										        	    	   			    {
										        	    	   				 
										        	    	   				 	    document.imprest_account.sel[i].checked=true
										        	    	   				 	    document.imprest_account.sel[i].disabled=false;											        	    	   				 	   
										        	    	   				 		
										        	    	   			    }
										        	    	   	}
						        	        		}
						        	        		else
						        	        		{
								        	        			if(document.imprest_account.recoup_vou_no[param].value==document.imprest_account.recoup_voucher.value && document.imprest_account.sl_no[param].value==document.imprest_account.sno.value && document.imprest_account.recoup_vou_dt[param].value==document.imprest_account.recoup_voucher_dt.value)
							        	    	   			 	{
							        	    	   				 
							        	    	   				 	    	document.imprest_account.sel.checked=true	
							        	    	   				 	    	document.imprest_account.sel.disabled=false;											        	    	   				 	   
							        	    	   				 		
							        	    	   			 	}
						        	        		}
							        	    	   	
						        	        }
						        	        document.getElementById("recoupDiv").style.display="block";
				        	         
				        	   	   }
				        	   	  
				           }
				           else
				           {
				        	   document.getElementById("recoupDiv").style.display="none";
//				        	   	   alert("else");
//				               	       var tbody=document.getElementById("mytable");
//				               	       alert(tbody);
//				               	       var r=document.getElementById(com_id);
//				               	    alert(r);
//				               	       var ri=r.rowIndex;               	      
//				               	       tbody.deleteRow(ri);
//				               	       alert("be4 e");
//				               	       clearall();
				              
				        	   	   alert("No Data Found");
				        	   	   
				           }
			      }
	    }    
}


/////////////////////////////////////////////Load Account Head Based on the Journal Type Selection /////////////////////

function loadAccountHead()
{
		 var tbody=document.getElementById("grid_body");		    
		 for(t=tbody.rows.length-1;t>=0;t--)
		 {
		       tbody.deleteRow(0);
		 }
		 
		 var payment_type=document.getElementById("cmbPayment_type").value;		 
		 if(payment_type=='I')		
			   document.getElementById("txtAcc_HeadCode").value=820103;			 
		 else		
			   document.getElementById("txtAcc_HeadCode").value=820102;			 
		// doFunction('checkCode','null');
		 doFunctionBLOCK('checkCode','null');
			 
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////Load Purpose of Drawl /////////////////////////////////////////////////////
function loaddrawlPurpose()
{		
	    var payment_type=document.getElementById("cmbPayment_type").value;
	    var drawl_purpose=document.getElementById("drawl_purpose");
	    var child=drawl_purpose.childNodes;
	   
	    for(var c=child.length-1;c>1;c--)
	    {
	    	   drawl_purpose.removeChild(child[c]);
	    }
		var url="../../../../../Imprest_Account_Creation?command=loaddrawlPurpose&txtMode_of_creat="+payment_type;	    
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    	   drawlPurpose_handleResponse(req);
	    }   
	    req.send(null);    
	
}


function drawlPurpose_handleResponse(req)
{
	    if(req.readyState==4)
	    {
			       if(req.status==200)
			       {  

				           var baseResponse=req.responseXML.getElementsByTagName("response")[0];				          
				           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				           if(flag=="success")
				           {
				        	   	   var rec=baseResponse.getElementsByTagName("wd_purpose_id");
				        	   	   var drawl_purpose=document.getElementById("drawl_purpose");
				        	   	   for(var i=0;i<rec.length;i++)
				        	   	   {
				        	   		   		var code=baseResponse.getElementsByTagName("wd_purpose_id")[i].firstChild.nodeValue;
				        	   		   		var desc=baseResponse.getElementsByTagName("wd_purpose_desc")[i].firstChild.nodeValue;
					        	   		   	var opt=document.createElement("option");
			                                opt.setAttribute("value",code);
			                                var opttext=document.createTextNode(desc);
			                                opt.appendChild(opttext);
			                                drawl_purpose.appendChild(opt);
				        	   	   }
				           }
			       }
	    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

chequeRange=function(){	
	if((document.imprest_account.cd[0].checked==true)&&(document.getElementById('txtCheque_DD_NO').value!="")){
		//alert("test "+document.frmBankPay_PendingBill_create.txtCheque_DD[0].checked);
		var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	//	var officeId=document.getElementById('cmbOffice_code').value;
		var chequeNo=document.getElementById('txtCheque_DD_NO').value;
		var accountNo=document.getElementById('txtBankAccountNo').value;
                 var txtCrea_date=document.getElementById('txtCrea_date').value;
                var dated=txtCrea_date.split("/");
                if(dated[2]==2011 || dated[2]>2011){
               // if(dated[1]==11 || dated[1]>11){
              
		var url="../../../../../BankPay_PendingBill_Create.view?Command=chequeRange&chequeNo="+chequeNo+"&accunitId="+accunitId+
				"&accountNo="+accountNo;
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function(){
        	processResponse(req);
        };   
        req.send(null);
	//}
                }	
	}	
};
function processResponse(req){
	 if(req.readyState==4)
    { 
        if(req.status==200)
        {  
       	 var rangeResponse=req.responseXML.getElementsByTagName("response")[0];
     	   chequeRangeResponse(rangeResponse);
        }
    }
}
function chequeRangeResponse(response){
	var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="fail"){
		alert("Cheque No does not exist Please Fill the Cheque No Details in the ChequeBook Master");
		document.getElementById('txtCheque_DD_NO').value="";
	}
}

