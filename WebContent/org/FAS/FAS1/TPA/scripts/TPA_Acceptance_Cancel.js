var com_id;
var com_cmbSL_Code="";
var com_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;
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


function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         if(charCode==46)
         {                
                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                        // to avoid over flow
                if(item.value.indexOf(".")<0)
                {
                        //alert("Length without . ="+item.value.length); 
                        return (item.value.length<n)?true:false;
                }
                // dont allow more than 2 precision no's after the point
                if(item.value.indexOf(".")>0)
                {
                        //alert("precision count ="+item.value.split(".")[1].length);
                        if(item.value.split(".")[1].length<pre) return true;
                        else return false;
                }
                return false;
         }else
         {
                return false;
         }
}

function resetMonth()
{
         document.getElementById("txtCB_Month").value="";
         resetType();
}

function resetType()
{
	 	 document.getElementById("Org_CR_DR").value="";
         resetVoucher();
}

function resetVoucher()
{
         document.getElementById("originated_slno").value="";
         clrVoucherDetails();
}

function loadVoucher()
{   
		 document.getElementById("accepted_slno").length=1;
	     if(document.TPA.Org_CR_DR[0].checked==true)
	    	 document.getElementById("txtAcc_HeadCode").value==900301;
	     else
	    	 document.getElementById("txtAcc_HeadCode").value==620101;
		 var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value;   
	     var txtCB_Year= document.getElementById("txtCB_Year").value;   
	     var txtCB_Month= document.getElementById("txtCB_Month").value;
	     var cr_dr=document.getElementById("Org_CR_DR").value;
         url="../../../../../TPA_Acceptance_Cancel?command=loadVoucher&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&CR_DR="+cr_dr;
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                TPA_Acceptance_ServletResponse(req);
         }   
         req.send(null);     
}


function loadVoucherDetails()
{
	     clrVoucherDetails();
         if(document.TPA.Org_CR_DR[0].checked==true)
                document.getElementById("txtAcc_HeadCode").value=900301;
         else
                document.getElementById("txtAcc_HeadCode").value=620101;
         var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value  
	     var txtCB_Year= document.getElementById("txtCB_Year").value;   
	     var txtCB_Month= document.getElementById("txtCB_Month").value;
	     var cr_dr=document.getElementById("Org_CR_DR").value;
	     var accepted_slno= document.getElementById("accepted_slno").value;
	     url="../../../../../TPA_Acceptance_Cancel?command=loadVoucherDetails&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&CR_DR="+cr_dr+"&accepted_slno="+accepted_slno;
	     req=getTransport();
	     req.open("GET",url,true);        
	     req.onreadystatechange=function()
	     {        	  
	            TPA_Acceptance_ServletResponse(req);
	     }   
	     req.send(null);     
	
}

function TPA_Acceptance_ServletResponse(req,slcode)
{
		 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                         var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                         var tagcommand=baseResponse.getElementsByTagName("command")[0];
                         var Command=tagcommand.firstChild.nodeValue;                                  
                         var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                         if(Command=="loadVoucher")
                         {                                       
                               if(flag=="success")
                               {
                            	   var verifiedYes=baseResponse.getElementsByTagName("verifiedYes")[0].firstChild.nodeValue;
                            	   alert("verifiedYes::::"+verifiedYes);
                            	   if(verifiedYes=="verifiedNo")
                            	   {
                            		   
                            		   document.getElementById("firstDivId").style.disply="block";
                            		   document.getElementById("secdivId").style.disply="none";     
                            	   }
                            	   else
                            	   {
                            		 //  alert("yes");
                            		   //document.getElementById('yr_mon').style.display='block';
                            		   document.getElementById("firstDivId").style.disply='block';
                            		   document.getElementById("secdivId").style.disply='block';
                            		   //alert("bbbbb");
                            	   }
                                       var accepted_slno=document.getElementById("accepted_slno");                                 
                                       var count=baseResponse.getElementsByTagName("voucher_no");
                                       for(var k=0;k<count.length;k++)
                                       {
                                                var voucher_no=baseResponse.getElementsByTagName("voucher_no")[k].firstChild.nodeValue;				       	                                                  
                                                var option=document.createElement("OPTION");
                                                option.text=voucher_no;
                                                option.value=voucher_no;
                                                try
                                                {
                                                		accepted_slno.add(option);
                                                }
                                                catch(errorObject)
                                                {
                                                		accepted_slno.add(option,null);
                                                }
                                       }
                               }
                               else
                               {                                                   
                                       document.getElementById("originated_slno").value="";
                                       alert("No Voucher Found");
                               }
                         }
                         else if(Command=="loadVoucherDetails")
                         {	                    	   
	                    	   if(flag=="success")
	                           {             
	                    		   var amount_to_be_correct=0;
	                    		   var cr_dr=document.getElementById("Org_CR_DR").value;
	                                   var org_voucher_no=baseResponse.getElementsByTagName("org_voucher_no")[0].firstChild.nodeValue;
	                                   var org_voucher_date=baseResponse.getElementsByTagName("org_voucher_date")[0].firstChild.nodeValue;
	                                   var org_accounting_unit=baseResponse.getElementsByTagName("org_accounting_unit")[0].firstChild.nodeValue;
                                      // alert(voucher_date);
                                       var reason=baseResponse.getElementsByTagName("org_reason_for_transfer")[0].firstChild.nodeValue;
                                       var voucher_date=baseResponse.getElementsByTagName("voucher_date")[0].firstChild.nodeValue;
                                       document.getElementById("txtCreate_Date").value=voucher_date;
                                       document.getElementById("originated_slno").value=org_voucher_no;
                                       document.getElementById("originated_date").value=org_voucher_date;
                                       document.getElementById("originated_unit").value=org_accounting_unit;
                                       document.getElementById("txtReason").value=reason;
                                       var count=baseResponse.getElementsByTagName("account_head_code");
	                                   var tbody=document.getElementById("grid_body");
	                                   for(var i=0;i<count.length;i++)
	                                   {
	                                	   		var account_head_code=baseResponse.getElementsByTagName("account_head_code")[i].firstChild.nodeValue;
	                                	   		var account_desc=baseResponse.getElementsByTagName("account_head_code_desc")[i].firstChild.nodeValue;
	                                	   		var cr_dr_indicator=baseResponse.getElementsByTagName("cr_dr_indicator")[i].firstChild.nodeValue;
	                                	   		var amount=baseResponse.getElementsByTagName("amount")[i].firstChild.nodeValue;
	                                	   		var sub_ledger_type_code=baseResponse.getElementsByTagName("sub_ledger_type_code")[i].firstChild.nodeValue;
	                                	   		var sub_ledger_type_desc=baseResponse.getElementsByTagName("sub_ledger_type_desc")[i].firstChild.nodeValue;
	                                	   		if(sub_ledger_type_desc=="--")
	                                	   			sub_ledger_type_desc="";
	                                	   	    var sub_ledger_code=baseResponse.getElementsByTagName("sub_ledger_code")[i].firstChild.nodeValue;
	                                	   	    var sub_ledger_code_desc=baseResponse.getElementsByTagName("sub_ledger_desc")[i].firstChild.nodeValue;
	                                	   	    if(sub_ledger_code_desc=="--")
	                                	   	    	sub_ledger_code_desc="";
	                                	   	    var particulars=baseResponse.getElementsByTagName("particulars")[i].firstChild.nodeValue;
	                                	   	    if(particulars=="--")
	                                	   	    	particulars="";
	                                	   	    
	                                	   	 if(account_head_code=='900301' || account_head_code=='620101')
	                                	   	    {
	                                	   		if(cr_dr!=cr_dr_indicator)
	                                	   		{
	                                	   	     document.getElementById("amount").value= amount;
	                                	   	  
	                                	   		 }
	                                	   	 // document.getElementById("particulars").value= particulars;
	                                	   	  
	                                	   	    } 		
	                                	   	    		
	                                	   	    		
		                                	   	var mycurrent_row=document.createElement("TR");                
		                                        mycurrent_row.id=seq;
		                                         
		                                        var cell=document.createElement("TD");
		                                        cell2=document.createElement("TD");       
		                                        var H_code=document.createElement("input");
		                                        H_code.type="hidden";
		                                        H_code.name="H_code";
		                                        H_code.value=account_head_code;
		                                        cell2.appendChild(H_code);
		                                        var currentText=document.createTextNode(account_head_code+"-"+account_desc);
		                                        cell2.appendChild(currentText);
		                                        mycurrent_row.appendChild(cell2);
		                                            
		                                        cell2=document.createElement("TD");
		                                        var SL_type=document.createElement("input");
		                                        SL_type.type="hidden";
		                                        SL_type.name="SL_type";
		                                        SL_type.value=sub_ledger_type_code;
		                                        cell2.appendChild(SL_type);
		                                        var currentText=document.createTextNode(sub_ledger_type_desc);
		                                        cell2.appendChild(currentText);
		                                        mycurrent_row.appendChild(cell2);
		                                         
		                                        cell2=document.createElement("TD");
		                                        var SL_code=document.createElement("input");
		                                        SL_code.type="hidden";
		                                        SL_code.name="SL_code";
		                                        SL_code.value=sub_ledger_code;
		                                        cell2.appendChild(SL_code);		                                         
		                                        var currentText=document.createTextNode(sub_ledger_code_desc);
		                                        cell2.appendChild(currentText);
		                                        mycurrent_row.appendChild(cell2);
		                                         
		                                         cell2=document.createElement("TD"); 
		                                         var CR_DR_type=document.createElement("input");
		                                         CR_DR_type.type="hidden";
		                                         CR_DR_type.name="CR_DR_type";
		                                         CR_DR_type.value=cr_dr_indicator;
		                                         cell2.appendChild(CR_DR_type);
		                                         var currentText=document.createTextNode(cr_dr_indicator);
		                                         cell2.appendChild(currentText);
		                                         mycurrent_row.appendChild(cell2);
		                                         
		                                         cell2=document.createElement("TD"); 
		                                         var sl_amt=document.createElement("input");
		                                         sl_amt.type="hidden";
		                                         sl_amt.name="sl_amt";
		                                         sl_amt.value=amount;
		                                         cell2.appendChild(sl_amt);
		                                         var currentText=document.createTextNode(amount);
		                                         cell2.appendChild(currentText);
		                                         mycurrent_row.appendChild(cell2);
		                                           
		                                         cell2=document.createElement("TD");                
		                                         var particular=document.createElement("input");
		                                         particular.type="hidden";
		                                         particular.name="sl_particular";
		                                         particular.value=particulars;
		                                         cell2.appendChild(particular);
		                                         var currentText=document.createTextNode(particulars);
		                                         cell2.appendChild(currentText);
		                                         mycurrent_row.appendChild(cell2);
	
		                                         tbody.appendChild(mycurrent_row);
		                                         //clearall();
		                                         // Increment Sequence Number 
		                                         seq=seq+1;            
	                                	   		
	                                   }
	                                   
	                           }
	                    	  
                         }
                         /*else if(Command=="loadSLType")
                         {          			    	   
         		    	       if(flag=="success")
         		    	       {         		    	    			 
         		    	    		   var cmbSL_Code=document.getElementById("cmbSL_Code");
         				        	   var child=cmbSL_Code.childNodes;
         				        	   for(var i=child.length-1;i>1;i--)
         				        	   {
         				        	    	 
         				        	    	    cmbSL_Code.removeChild(child[i]);
         				        	   } 
         				        	       
         				        	   var count=baseResponse.getElementsByTagName("office_id");           				        	   
         				               var sl_code="";var sl_desc="";			                
         				               for(var i=0;i<count.length;i++)
         				               {
	         				                	sl_code=baseResponse.getElementsByTagName("office_id")[i].firstChild.nodeValue;
	         				                	sl_desc=baseResponse.getElementsByTagName("office_name")[i].firstChild.nodeValue;
	         				                    var opt=document.createElement("option");
	         				                    opt.setAttribute("value",sl_code);
	         				                    var opttext=document.createTextNode(sl_desc);
	         				                    opt.appendChild(opttext);
	         				                    cmbSL_Code.appendChild(opt);
         				               }
         				               if(slcode!=null)
         				                	 	document.getElementById("cmbSL_Code").value=slcode;
         				               document.getElementById("offlist_div_trans").style.display='none';
         							   document.getElementById("emplist_div_trans").style.display='none';   
         			   	 	   }
         			   	 	   else
         			   	 	   {
         			   	 			   alert("No Sub Ledger Type Found");
         			   	 	   }
         		         }  */          
              }
                
		 }    
}


///////////////////////////////////////////    TB_checking and Calender control return value handling
function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
         if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
         {
                var dt=document.TPA.txtCrea_date.value;
                var dat=dt.split("/");		
                var monthArray =new Array("January", "February", "March", 
                                           "April", "May", "June", "July", "August",
                                           "September", "October", "November", "December");
                 
                document.getElementById("txtCB_Year").value=dat[2]; 		
                document.getElementById("txtCB_Month").value=monthArray[dat[1]-1];                 
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
                         if(flag=="failure")
                         {
                                  dateCtrl.value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                            
                         }
                         else if(flag=="finyear")
                         {
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                  dateCtrl.value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                                  //document.getElementById("txtVoucher_No").value="";     
                         }
                }
         }
}


function clrVoucherDetails()
{
        document.getElementById("txtAcc_HeadCode").value="";
   	document.getElementById("txtReason").value="";    
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
                tbody.deleteRow(0);
        }
}

function clrForm(param)
{		
		if(param=="cancel")
		{
			    if(window.confirm("Do you want to clear ALL fields ?"))
			    {
			               resetType();
			    }
		}
		else
			    resetType();
}


function checkNull()
{               
        var tbody=document.getElementById("grid_body");
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
        if(document.getElementById("txtCB_Year").value=="")
        {
            	alert("Select Cashbook Year");
            	return false;
        }
        if(document.getElementById("txtCB_Month").value=="")
        {
            	alert("Select Cashbook Month");
            	return false;
        }
        if(document.getElementById("Org_CR_DR").value=="")
        {
                alert("Select Transfer Proforma Type");
            	return false;
        }
        if(document.getElementById("originated_slno").value=="")
        {
            	alert("Select Originated Sl.No.");
            	return false;
        }
         if(document.getElementById("txtAcc_HeadCode").value=="")
        {
            	alert("Select Account Head Code");
            	return false;
        }
        if(document.getElementById("txtCreate_Date").value.length==0)
        {
	            alert("Enter the Date of Creation");           
	            return false;    
        }
        if(document.TPA.isAccept[1].checked==true)
        {
        		if(document.getElementById("notAccepting").value=="")
        		{
                                    alert("Enter the Reason for Not Accepting");         
		    	            return false;
        		}
                        else
                                return true;
        } 
        else
        {
        		if(tbody.rows.length==0)
        		{
                                    alert("Enter Details Part");
                                    return false;
        		}
        		else
                        {
                                    return true;
                                
                        }       
        }
       		
}

function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    	    alert("Please Enter below 200 characters");
	    }	 
}




setTimeout('clear_all()',50);

function clear_all()
{ 
document.getElementById("Org_CR_DR").value="";

document.getElementById("originated_slno").value="";
document.getElementById("originated_unit").value="";
document.getElementById("txtAcc_HeadCode").value="";
document.getElementById("txtReason").value="";
document.getElementById("amount").value="";
document.getElementById("particulars").value="";

	
	  
	        var tbody=document.getElementById("grid_body");
	        var t=0;
	        for(t=tbody.rows.length-1;t>=0;t--)
	        {
	                tbody.deleteRow(0);
	        }
	        
	        
	        

}


