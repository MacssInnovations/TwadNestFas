
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
var common_AHead_code_flag="";
var emp_flag;

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

function checkPayDate()
{
     var journalDt=document.getElementById("txtCrea_date").value;
     var dat=journalDt.split("/");
   
        var paymentDt=document.getElementById("pay_date").value;
        var pay=paymentDt.split("/");
   
                if(pay[2]==dat[2]){
                
                
                if(dat[1]<pay[1])
                {
                    if(dat[0]<pay[0])
                    {
                    alert("Journal Date should be Greater than Payment Date*");
                    document.getElementById("txtCrea_date").value="";
                    }
                }
                else if(dat[1]==pay[1])
                {
                    if(dat[0]<pay[0])
                    {
                    alert("Journal Date should be Greater than Payment Date");
                    document.getElementById("txtCrea_date").value="";
                    }
                }
                }
                else if(dat[2]<pay[2])
                {
                 alert("Journal Date should be Greater than Payment Date");
                }
                
       
}

 function checkTtlAmt()
{
        if(document.frmJournal_Imprest.finalPayment[0].checked==true)
        {
        // document.getElementById("hiddenPayment").value="yesFinal";
        
            if(document.frmJournal_Imprest.cmbSL_Code.value=="")
            {
                    alert("Enter SubLedger Code");
                    document.frmJournal_Imprest.finalPayment[0].checked=false;
                    document.frmJournal_Imprest.finalPayment[1].checked=false;
                    return false;
            }
               var  cmbAcc_UnitCode  = document.getElementById("cmbAcc_UnitCode").value;
               var  cmbOffice_code   = document.getElementById("cmbOffice_code").value; 
               var yr=document.frmJournal_Imprest.txtCB_Year.value;
               var mon=document.frmJournal_Imprest.txtCB_Month.value;
               var type1=document.frmJournal_Imprest.cmbMas_SL_type.value;
               var cmbSL_Code=document.frmJournal_Imprest.cmbSL_Code.value;
             //  alert(type1);
              /*  if(type1=="68-BPF")
		 {
			txtMode_of_creat=68;
		
		 }
		 else  if(type1=="68-SC")
		 {
			 
			 txtMode_of_creat=68;
			
		 }
		 else if(type1=="69-BPF")
		 {
			
			 txtMode_of_creat=69;
            	 }
		 else if(type1=="69-SC")
		 {
			
			 txtMode_of_creat=69;
		 } */
               
                 var url="../../../../../Imprest_Journal_Create?Command=finalPay&UnitCode="+cmbAcc_UnitCode+"&Office_Code="+cmbOffice_code+
                            "&txtCB_Year="+yr+"&txtCB_Month="+mon+"&txtMode_of_creat="+type1+"&cmbSL_Code="+cmbSL_Code;            
                //  alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                      loadProcess_Response(req);
                    }   
                       req.send(null);
           
           
            }
            else
            {
         //    alert("noooooo");
          document.getElementById("txtsub_Amount").value="";
       //   document.getElementById("hiddenPayment").value="noFinal";
          loadSLCodeText();
            }
}

function checkIndicator()
{
	var hCode=document.getElementById("txtAcc_HeadCode").value;
	if(hCode=="820103" || hCode=="820102")
	{
		document.frmJournal_Imprest.rad_sub_CR_DR[0].checked=true;
		document.frmJournal_Imprest.rad_sub_CR_DR[1].checked=false;
	}
	else
	{
		document.frmJournal_Imprest.rad_sub_CR_DR[0].checked=false;
		document.frmJournal_Imprest.rad_sub_CR_DR[1].checked=true;
	}
}

function changeLink()
{
	if(document.getElementById("txtJournalVou_No").value!="")
		document.getElementById("linkId").style.visibility="visible";
	else{
		document.getElementById("linkId").style.visibility="hidden";
                }
    var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
    var office_code=document.getElementById("cmbOffice_code").value;    
    var txtCB_Year= document.getElementById("txtCB_Year").value;   
    var txtCB_Month= document.getElementById("txtCB_Month").value;
    var cmbMas_SL_type= document.getElementById("cmbMas_SL_type").value;
   
    var txtJournalVou_No= document.getElementById("txtJournalVou_No").options[document.getElementById("txtJournalVou_No").selectedIndex].text;
    
     var fin_year1=document.getElementById("fin_year1").value;
    var f1=fin_year1.split("/");
//    var fin_year2=document.getElementById("fin_year2").value;
//    var f2=fin_year2.split("/");
 
    var url="../../../../../Imprest_Journal_Create?Command=loadVoucher_details&UnitCode="+UnitCode+"&Office_Code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtMode_of_creat="+cmbMas_SL_type+"&voucher_no="+txtJournalVou_No+"&fyear1="+f1[1]+"&fmonth="+f1[0];
  // alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
    		loadProcess_Response(req,'null');
    }   
    req.send(null);
}

var tbody=document.getElementById("grid_body");		    
for(t=tbody.rows.length-1;t>=0;t--)
{
      tbody.deleteRow(0);
}
function callHeadCode(parameter)
{
	var hCode=parameter;
	 if((hCode==390302) ||(hCode==390303) || (hCode==390305) || (hCode==391002) ||(hCode==391003) ||(hCode==391302) || (hCode==391303) ||(hCode==391502) ||(hCode==391503) )
    {			
    	  alert("GPF Account Head Code cannot be used here***");
          document.getElementById("txtAcc_HeadCode").value="";
          document.getElementById("txtAcc_HeadCode").focus();
         // return false;
      }
	 var url="../../../../../Imprest_Journal_Create?Command=hCodeChecking&hCode="+hCode;   
	  var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        responseCheck(req);
     }   
             req.send(null);
	
}
function  responseCheck(req)
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
			         var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
			         document.getElementById("txtAcc_HeadCode").value=hid;
			         var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
			         var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
			         
			         var sl_man = baseResponse.getElementsByTagName("sl_man")[0].firstChild.nodeValue;
			         
			         
			         document.getElementById("txtAcc_HeadCode").value=hid;
			         document.getElementById("txtAcc_HeadDesc").value=hdesc;
			      
			       var cmbSL_type=document.getElementById("cmbSL_type");   
		     try{   
		     
		       
		       if(SL_YN=="Y")
		       {
		            
		            
		            if(sl_man == "Y" ) 
		            {
		              isMan.account_head_status = true;     
		            }
		            
		            var items_SLcode=new Array();
		            var items_SLdesc=new Array();
		            var SLCODE=baseResponse.getElementsByTagName("SLCODE");
		            var SLDESC=baseResponse.getElementsByTagName("SLDESC");
		            for(var k=0;k<SLCODE.length;k++)
		            {
		                items_SLcode[k]=baseResponse.getElementsByTagName("SLCODE")[k].firstChild.nodeValue;
		                items_SLdesc[k]=baseResponse.getElementsByTagName("SLDESC")[k].firstChild.nodeValue;
		            }
		            
		            cmbSL_type.innerHTML="";
		            var option=document.createElement("OPTION");
		            option.text="--Select Type--";
		            option.value="";
		            try
		            {
		                cmbSL_type.add(option);
		            }catch(errorObject)
		            {
		                cmbSL_type.add(option,null);
		            }
		            for(var k=0;k<SLCODE.length;k++)
		            {   
		              var option=document.createElement("OPTION");
		              option.text=items_SLdesc[k];
		              option.value=items_SLcode[k];
		               try
		              {
		                  cmbSL_type.add(option);
		              }
		              catch(errorObject)
		              {
		                  cmbSL_type.add(option,null);
		              }
		            }
		            
		            if(common_cmbSL_type=="")
		                document.getElementById("cmbSL_type").value="";
		            else
		                document.getElementById("cmbSL_type").value=common_cmbSL_type;    //set from grid
		       }
		        
		     }catch(e)
		     {  
		       alert(e.description);
		       return false;
		     }   


		        if(SL_YN=="N" || SL_YN=="null")
		           {    
		                cmbSL_type.innerHTML=""; 
		                var option=document.createElement("OPTION");
		                option.text="--Select Type--";
		                option.value="";
		                try
		                {
		                    cmbSL_type.add(option);
		                }catch(errorObject)
		                {
		                    cmbSL_type.add(option,null);
		                }
		            }
          
				    }
				     else if(flag=="failure")
				     {
				         alert("This Account HeadCode cannot be used in this module");
				         document.getElementById("txtAcc_HeadCode").value="";
				         document.getElementById("txtAcc_HeadDesc").value="";
				         document.getElementById("txtAcc_HeadCode").focus();
				     }
				     
				       common_cmbSL_type="";
					     }
					   }
	
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





/////////////////////////////////////////////Load Account Head Based on the Journal Type Selection /////////////////////

function loadAccountHead()
{
		 var tbody=document.getElementById("grid_body");		    
		 for(t=tbody.rows.length-1;t>=0;t--)
		 {
		       tbody.deleteRow(0);
		 }
		 
		 var txtMode_of_creat1=document.getElementById("cmbMas_SL_type").value;
		
		 if(txtMode_of_creat1=="68-BPF")
		 {
			 
			 txtMode_of_creat=68;
			
			 document.getElementById("txtAcc_HeadCode").value=820103;
			
		 }
		 else  if(txtMode_of_creat1=="68-SC")
		 {
			 
			 txtMode_of_creat=68;
			
			 document.getElementById("txtAcc_HeadCode").value=820103;
			
		 }
		 else if(txtMode_of_creat1=="69-BPF")
		 {
			
			 txtMode_of_creat=69;
			 document.getElementById("txtAcc_HeadCode").value=820102;
		 }
		 else if(txtMode_of_creat1=="69-SC")
		 {
			
			 txtMode_of_creat=69;
			 document.getElementById("txtAcc_HeadCode").value=820102;
		 }
//		 if(txtMode_of_creat==68){	alert(txtMode_of_creat);	
//			   document.getElementById("txtAcc_HeadCode").value=820103;		}	 
//		 else	{	alert(txtMode_of_creat);
//			   document.getElementById("txtAcc_HeadCode").value=820102;	}		 
		 doFunction('checkCode','null');
			 
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function financialYear()
{
   // alert("financialYear");
    var mont=document.getElementById("txtCB_Month").value;
    if(mont==4)
    {
   // alert("444");
    var yea=document.getElementById("txtCB_Year").value;
    document.getElementById("fin_year1").value=mont+"/"+yea;
    }
    else if(mont>4)
    {
     var yea=document.getElementById("txtCB_Year").value;
    document.getElementById("fin_year1").value="4/"+yea;
    }
    else if(mont==1 || mont==2 || mont==3)
    {
     var yea=document.getElementById("txtCB_Year").value;
     var year1_one=yea-1;
    document.getElementById("fin_year1").value="4/"+year1_one;
    }
    loadVoucher();
}

function loadVoucher()
{	
	if(document.getElementById("txtCB_Month").value=="")
	{
			alert("Select Month");
			return false;
	}
	var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
    var office_code=document.getElementById("cmbOffice_code").value;    
    var txtCB_Year= document.getElementById("txtCB_Year").value;   
    var txtCB_Month= document.getElementById("txtCB_Month").value;
    var cmbMas_SL_type= document.getElementById("cmbMas_SL_type").value;
    var fin_year1=document.getElementById("fin_year1").value;
   var f1=fin_year1.split("/");
   // var fin_year2=document.getElementById("fin_year2").value;
   // var f2=fin_year2.split("/");

    var url="../../../../../Imprest_Journal_Create?Command=loadVoucher&UnitCode="+UnitCode+"&Office_Code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtMode_of_creat="+cmbMas_SL_type+"&fyear1="+f1[1]+"&fmonth="+f1[0];
    //    alert("url:::"+url);
	var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
    		loadProcess_Response(req,'null');
    }   
    req.send(null);
    
}


function loadSLType(SLCode,SLType)
{
	var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	
	var office_code=document.getElementById("cmbOffice_code").value;

	var txtCB_Year= document.getElementById("txtCB_Year").value; 
	
	var txtCB_Month= document.getElementById("txtCB_Month").value;
	
	var txtVou_no= document.getElementById("txtJournalVou_No").value;
	
	var ac_head_code=document.getElementById("txtAcc_HeadCode").value;
	
	var cmbMas_SL_type= document.getElementById("cmbMas_SL_type").value;
         var fin_year1=document.getElementById("fin_year1").value;
        var f1=fin_year1.split("/");
//        var fin_year2=document.getElementById("fin_year2").value;
//        var f2=fin_year2.split("/");
	if((parseInt(ac_head_code)==820103 || parseInt(ac_head_code)==820102 ) && SLType==7)
	{
			if(txtVou_no=="")
			{
					alert("Select Voucher Number");
					document.getElementById("cmbSL_type").value="";
					return false;
			}
			
	   		var url="../../../../../Imprest_Journal_Create?Command=loadSLType&Option=Create&UnitCode="+UnitCode+"&Office_Code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtVou_no="+txtVou_no+"&txtMode_of_creat="+cmbMas_SL_type+"&fyear1="+f1[1]+"&fmonth="+f1[0];   		
	   		//alert(url);
                        var req=getTransport();
	   		req.open("GET",url,true); 
	   		req.onreadystatechange=function()
	   		{
	   				loadProcess_Response(req,SLCode);
	   		}   
	   		req.send(null);
	}
	else
			doFunction('Load_SL_Code',SLType);
}


function loadSLCodeText()
{
	var cmbSL_Code=document.getElementById("cmbSL_Code").value;
	document.getElementById("txtEmpID_trs").value=cmbSL_Code;	
	var ac_head_code=document.getElementById("txtAcc_HeadCode").value;
	if(parseInt(ac_head_code)==820103 || parseInt(ac_head_code)==820102)
	{			
			var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
			var office_code=document.getElementById("cmbOffice_code").value;    
			var txtCB_Year= document.getElementById("txtCB_Year").value;   
			var txtCB_Month= document.getElementById("txtCB_Month").value;
			var txtVou_no= document.getElementById("txtJournalVou_No").value;
			var cmbMas_SL_type= document.getElementById("cmbMas_SL_type").value;
                        var fin_year1=document.getElementById("fin_year1").value;
                        var f1=fin_year1.split("/");
//                        var fin_year2=document.getElementById("fin_year2").value;
//                        var f2=fin_year2.split("/");
                        
			var url="../../../../../Imprest_Journal_Create?Command=loadPaymentTotal&UnitCode="+UnitCode+"&Office_Code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtVou_no="+txtVou_no+"&cmbSL_Code="+cmbSL_Code+"&txtMode_of_creat="+cmbMas_SL_type+"&fyear1="+f1[1]+"&fmonth="+f1[0];		   		
	   		var req=getTransport();
	   		req.open("GET",url,true); 
	   		req.onreadystatechange=function()
	   		{
	   				loadProcess_Response(req);
	   		}   
	   		req.send(null);
	}
}

function loadProcess_Response(req,com_cmbSL_Code)
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
                                               var txtBankAccountNo=document.getElementById("txtJournalVou_No");
                                               document.forms[0].txtJournalVou_No.length=0;
                                               vouch_no=baseResponse.getElementsByTagName("voucher_no");
                                               sl_code=baseResponse.getElementsByTagName("sl_code");
                                               
                                               var pay_date1=baseResponse.getElementsByTagName("payment_date")[0].firstChild.nodeValue;
                                               document.getElementById("pay_date").value=pay_date1;
                                              var dateSplit=pay_date1.split("/");
                                           //   alert(dateSplit[1]+":::::"+dateSplit[2]);
                                                document.getElementById("pay_month").value=dateSplit[1];
                                               for(var i=0; i<vouch_no.length; i++)
                                               {
                                                       if(vouch_no.length==1)
                                                              {
                                                                                    //document.forms[0].txtJournalVou_No.length=0;
                                                                            var opt1 = document.createElement('option');
                                                                    opt1.value = 0;
                                                                    opt1.innerHTML ="select Voucher"; 
                                                                    txtBankAccountNo.appendChild(opt1);
                                                                      
                                                              }
                                                       var opt1 = document.createElement('option');
                                                       opt1.value = vouch_no[i].firstChild.nodeValue;
                                                       opt1.innerHTML = vouch_no[i].firstChild.nodeValue+"-"+sl_code[i].firstChild.nodeValue; 
                                                       txtBankAccountNo.appendChild(opt1);
                                               }
		   	 		}
		    	   else
		   	 		{
		   	 				alert("No Voucher Found");
		   	 				document.getElementById("txtJournalVou_No").length=0;
		   	 		}
  	   
			   }
                           else if(Command=="loadVoucherdetails")
                           {
                            if(flag=="success")
                                {
                                 var pay_date1=baseResponse.getElementsByTagName("payment_date")[0].firstChild.nodeValue;
                                     // alert("ddddddddd"+pay_date1);        
                                               document.getElementById("pay_date").value=pay_date1;
                                                var dateSplit=pay_date1.split("/");
                                           //   alert(dateSplit[1]+":::::"+dateSplit[2]);
                                                document.getElementById("pay_month").value=dateSplit[1];
                                }
                           }
		       else if(Command=="loadSLType")
		       { 
			    	   
		    	    	if(flag=="success")
			   	 		{
		    	    			 
		    	    			 var cmbSL_Code=document.getElementById("cmbSL_Code");
				        	     var child=cmbSL_Code.childNodes;
				        	     for(var i=child.length-1;i>1;i--)
				        	     {
				        	    	 
				        	    	 cmbSL_Code.removeChild(child[i]);
				        	     } 
				        	       
				        	     var count=baseResponse.getElementsByTagName("SUB_LEDGER_CODE");  
				        	     //alert(count.length);
				                 var sl_code="";var sl_desc="";			                
				                 for(var i=0;i<count.length;i++)
				                 {
				                	 sl_code=baseResponse.getElementsByTagName("SUB_LEDGER_CODE")[i].firstChild.nodeValue;
				                	 sl_desc=baseResponse.getElementsByTagName("ENAME")[i].firstChild.nodeValue;
				                     var opt=document.createElement("option");
				                     opt.setAttribute("value",sl_code);
				                     var opttext=document.createTextNode(sl_desc);
				                     opt.appendChild(opttext);
				                     cmbSL_Code.appendChild(opt);
				                 }
				                 if(com_cmbSL_Code!=null)
				                	 document.getElementById("cmbSL_Code").value=com_cmbSL_Code;
			   	 		}
			   	 		else
			   	 		{
			   	 				 alert("No Sub Ledger Type Found");
			   	 		}
		       }
                     else if(Command=="loadPaymentTotal")
		       {
                      // alert("loadPaymentTotal");
		    	        
			    	    if(flag=="success")
			   	 		{		    	    			 		    	    		
			                	 var pay_amt=baseResponse.getElementsByTagName("pay_amt")[0].firstChild.nodeValue;
			                	 var jour_amt=baseResponse.getElementsByTagName("jour_amt")[0].firstChild.nodeValue;
			                	 var rec_amt=baseResponse.getElementsByTagName("rec_amt")[0].firstChild.nodeValue;					                	 
			                	 var total_remaining_amt=pay_amt-jour_amt-rec_amt;
                                                // alert(total_remaining_amt);
			                	 document.getElementById("total_remaining_amt").value=total_remaining_amt;
			   	 		}
			   	 		
		    	   
		       }
                        else if(Command=="finalPay")
                        {
                            
                            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;  
                          // alert("flag"+flag);
                            if(flag=="Success")
                            {
                             document.getElementById("txtsub_Amount").value="";
                             var ttlAmt=baseResponse.getElementsByTagName("ttl")[0].firstChild.nodeValue;  
                             document.getElementById("txtsub_Amount").value=ttlAmt;
                          //   alert(ttlAmt);
                             document.getElementById("total_remaining_amt").value=ttlAmt;
                          
                            }
                        }  
	           
	       }
	           
	    }
}



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
    		 
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
	            		//loadVoucher();
	            }
	            else if(flag=="failure")
	            {
	                    dateCtrl.value="";
	                    alert("Trial Balance Closed");//return false;//
	                    dateCtrl.focus();
	                    document.getElementById("txtCrea_date").value="";
	            }
	            else if(flag=="finyear")
	            {
	                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
	                    dateCtrl.value="";
	                    alert("Cash Book Control Not Found ");//return false;//
	                    dateCtrl.focus();
	                    document.getElementById("txtCrea_date").value="";     
	            } 
	            
	            dateCheck(dateCtrl);  
	           
	            	 
	        }
    }
}


function limit_amt_journal(field,e)
{

      var Journal_Creation_date=document.getElementById("txtCrea_date").value.split("/"); 
      var unicode=e.charCode? e.charCode : e.keyCode;

      if(field.value.length<17)
      {
	        if(field.value.length==14 && field.value.indexOf('.')==-1  )
	        field.value=field.value+'.';
	        if (unicode!=8 && unicode !=9  )
	        {
	                            
		            if ( Journal_Creation_date[1] <=8 && Journal_Creation_date[2]<=2007 )     
		            {
		             
			              if (unicode<45 || unicode==47 || unicode>57   )        // It  allow the negative amount
			                  return false;  
		             }
		             else  
		             {
		             
			              if (unicode<45 || unicode==47 || unicode>57   )       // It won't allow the negative amount   
			                return false;
		             }     
	                  
	        }
      }
      else   
    	  return false;  
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
	     else
	    	 	return true;
  
}


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


function check_leng(param,val)
{	 
		if((val.length)>=190)
		{
			  if(param=='remarks')			  
				  	   alert("Please Enter Remarks below 200 characters");			           			  
			  else			  
				  	   alert("Please Enter Paticulars below 200 characters");				  	  
			  
		}
		
}
function chkHeadcode()
{
	var tbody=document.getElementById("grid_body");
	
	var HeadCode=document.getElementById("txtAcc_HeadCode").value;
//	alert("HeadCode value==>"+HeadCode);
	
	for(var i=0;i<tbody.rows.length;i++)

	{
		//alert(tbody.rows.length);
//		alert(tbody.rows[i].cells[1].textContent);
		var headck=tbody.rows[i].cells[1].textContent;
		headck=headck.substring(0,headck.indexOf("-"));
//		alert("headck===>"+headck);
		if (HeadCode == headck )
			{
			alert("This HeadCode already present");
			document.frmJournal_Imprest.cmdadd.disabled=true;
			 document.frmJournal_Imprest.cmdupdate.disabled=true;
			 return false;

			}
		else
			{
			
			 document.frmJournal_Imprest.cmdadd.disabled=false;
			 document.frmJournal_Imprest.cmdupdate.disabled=false;
			}
		
		
	}
}

/////////////////////////////////////////////   ADD & UPDATE & DELETE /////////////////////////////////////////////////////
function ADD_GRID()
{
var final_one;
       if(document.getElementById("txtAcc_HeadCode").value.length==0)
       {
	        alert("Enter A/c Head Code");
	        return false;
       }  
       if(document.getElementById("txtAcc_HeadDesc").value=="")
       {
            alert("Please Wait Account Head is Loading .......................");            
            return false;        
       }  
       if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
       {
		        if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
		        {
			             if(document.getElementById("cmbSL_type").value=="")
			             {
				                alert("Select a Sub-Ledger Type");
				                return false;
			             } 
		        }
		                 
        }
        if(document.getElementById("cmbSL_type").value!="")
        {
	          	if(document.getElementById("cmbSL_Code").value=="")
	            {
			             alert("Select The Sub Ledger Code");
			             return false;
	            }
        }
       if(document.getElementById("txtsub_Amount").value.length==0)
       {
	            alert("Enter the Amount ");
	            //document.getElementById("txtAmount").focus();
	            return false;    
       }
       var tbody=document.getElementById("grid_body");
                              
       var t=0;
       var exist=document.getElementById("txtAcc_HeadCode").value;
       var items=new Array();
       items[0]=document.getElementById("txtAcc_HeadCode").value;
       items[1]=document.getElementById("txtAcc_HeadDesc").value;
       if(document.frmJournal_Imprest.rad_sub_CR_DR[0].checked==true)
    	   items[2]=document.frmJournal_Imprest.rad_sub_CR_DR[0].value;
       else if(document.frmJournal_Imprest.rad_sub_CR_DR[1].checked==true)
    	   items[2]=document.frmJournal_Imprest.rad_sub_CR_DR[1].value;
        
       items[3]=document.getElementById("cmbSL_type").value;      
       if(document.getElementById("cmbSL_type").value=="")
       {        
    	   items[4]=""; 
       }
       else
    	   items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
        {
        	items[6]="";
        }
        else
        	items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
        

        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
       
       
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        if(document.frmJournal_Imprest.finalPayment[0].checked==true)
        {
      //  alert("iffffffff");
        final_one="Y";
        }
        else
        {
         final_one="N";
     //   alert("else");
        }
        items[14]=final_one;
                  
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
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
	    H_code.name="H_code";
	    H_code.value=items[0];
	    cell2.appendChild(H_code);
	    var currentText=document.createTextNode(items[0]+"-"+items[1]);
	    cell2.appendChild(currentText);
	    mycurrent_row.appendChild(cell2);
	            
	    cell2=document.createElement("TD"); 
	   var CR_DR_type=document.createElement("input");
	    CR_DR_type.type="hidden";
	    CR_DR_type.name="CR_DR_type";
	    CR_DR_type.value=items[2];
	    cell2.appendChild(CR_DR_type);
	    var currentText=document.createTextNode(items[2]);
	    cell2.appendChild(currentText);
	    mycurrent_row.appendChild(cell2);
                         
	    cell2=document.createElement("TD");
	    var SL_type=document.createElement("input");
	    SL_type.type="hidden";
	    SL_type.name="SL_type";
	    SL_type.value=items[3];
	    cell2.appendChild(SL_type);
	    var currentText=document.createTextNode(items[4]);
	    cell2.appendChild(currentText);
	    mycurrent_row.appendChild(cell2);
            
        cell2=document.createElement("TD");
        var SL_code=document.createElement("input");
        SL_code.type="hidden";
        SL_code.name="SL_code";
        SL_code.value=items[5];
        cell2.appendChild(SL_code);
        var currentText=document.createTextNode(items[6]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);
                
        cell2=document.createElement("TD");
        var Bill_NO=document.createElement("input");
        Bill_NO.type="hidden";
        Bill_NO.name="Bill_NO";
        Bill_NO.value=items[7];
        cell2.appendChild(Bill_NO);
        var currentText=document.createTextNode(items[7]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);
            
        cell2=document.createElement("TD");
        var Bill_date=document.createElement("input");
        Bill_date.type="hidden";
        Bill_date.name="Bill_date";
        Bill_date.value=items[8];
        cell2.appendChild(Bill_date);
        var currentText=document.createTextNode(items[8]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);
                                           
        cell2=document.createElement("TD");
        var Bill_type=document.createElement("input");
        Bill_type.type="hidden";
        Bill_type.name="Bill_type";
        Bill_type.value=items[9];
        cell2.appendChild(Bill_type);
       //   if
        var Agree_No=document.createElement("input");
        Agree_No.type="hidden";
        Agree_No.name="Agree_No";
        Agree_No.value=items[10];
        cell2.appendChild(Agree_No);
        
        var finalPayment=document.createElement("input");
        finalPayment.type="hidden";
        finalPayment.name="final_payment";
        finalPayment.value=items[14];
        cell2.appendChild(finalPayment);
            
        var Agree_date=document.createElement("input");
        Agree_date.type="hidden";
        Agree_date.name="Agree_date";
        Agree_date.value=items[11];
        cell2.appendChild(Agree_date);

        var sl_amt=document.createElement("input");
        sl_amt.type="hidden";
        sl_amt.name="sl_amt";
        sl_amt.value=items[12];
        cell2.appendChild(sl_amt);

        var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount hidden box    
        particular.type="hidden";
        particular.name="particular";
        particular.value=items[13];
        cell2.appendChild(particular);

        var currentText=document.createTextNode(items[12]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
      //  clear_main_fields();
        clearall();
}

function clear_main_fields()
{
     	document.getElementById("offlist_div_trans").style.display='none';
	    document.getElementById("emplist_div_trans").style.display='none';     
		document.frmJournal_Imprest.rad_sub_CR_DR[0].checked=true;
		document.getElementById("cmbSL_Code").value="";
		document.getElementById("txtEmpID_trs").value=""; 
		document.getElementById("txtOfficeID_trs").value=""; 
	    document.getElementById("txtsub_Amount").value="";
	    document.getElementById("txtParticular").value="";
     
	    var cmbSL_type=document.getElementById("cmbSL_type").value=""; 
	  
	    document.getElementById("offlist_div_trans").style.display='none';       
	    var cmbSL_Code=document.getElementById("cmbSL_Code");   
	    clear_Combo(cmbSL_Code);   

	    document.frmJournal_Imprest.cmdadd.style.display='block';
	    document.frmJournal_Imprest.cmdupdate.style.display='none';
	    document.frmJournal_Imprest.cmddelete.disabled=true;
}

function update_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
		        alert("Enter A/c Head Code");
		        return false;
        }
        if(document.getElementById("cmbSL_type").length>1 && document.getElementById("cmbSL_type").value=="")
        {
		        if(window.confirm("You have not selected Sub-Ledger Type \n Do you want to select it,click 'OK'?"))
		        {
			             if(document.getElementById("cmbSL_type").value=="")
			             {
				                alert("Select a Sub-Ledger Type");
				                return false;
			             } 
		        }
		                 
        }
        if(document.getElementById("cmbSL_type").value!="")
        {
	          	if(document.getElementById("cmbSL_Code").value=="")
	            {
			             alert("Select The Sub Ledger Code");
			             return false;
	            }
        }
        if(document.getElementById("txtsub_Amount").value.length==0)
        {
	            alert("Enter the Amount ");
	            document.getElementById("txtsub_Amount").focus();
	            return false;    
        }
        var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
       
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        if(document.frmJournal_Imprest.rad_sub_CR_DR[0].checked==true)
        		items[2]=document.frmJournal_Imprest.rad_sub_CR_DR[0].value;
        else if(document.frmJournal_Imprest.rad_sub_CR_DR[1].checked==true)
       	   		items[2]=document.frmJournal_Imprest.rad_sub_CR_DR[1].value;
        items[3]=document.getElementById("cmbSL_type").value;
        if(document.getElementById("cmbSL_type").value=="")
        		items[4]="";
        else
        		items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        
        items[5]=document.getElementById("cmbSL_Code").value;
        if(document.getElementById("cmbSL_Code").value=="")
           		items[6]="";     
        else
        		items[6]=document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text; 
    
        items[7]=document.getElementById("txtBill_NO").value;
        items[8]=document.getElementById("txtBill_date").value;
        items[9]=document.getElementById("txtBill_type").value;
        items[10]=document.getElementById("txtAgree_No").value;
        items[11]=document.getElementById("txtAgree_Date").value;
        
        items[12]=document.getElementById("txtsub_Amount").value;
        items[13]=document.getElementById("txtParticular").value;
        
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        
        try{rcells.item(1).firstChild.value=items[0];}catch(e){}
        try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
     
        try{rcells.item(2).firstChild.value=items[2];}catch(e){}
        try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
      
        try{rcells.item(3).firstChild.value=items[3];}catch(e){}
        try{rcells.item(3).lastChild.nodeValue=items[4];}catch(e){}
    
        try{rcells.item(4).firstChild.value=items[5];}catch(e){}
        try{rcells.item(4).lastChild.nodeValue=items[6];}catch(e){}
    
        try{rcells.item(5).firstChild.value=items[7];}catch(e){}
        try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
     
         try{rcells.item(6).firstChild.value=items[8];}catch(e){}
        try{rcells.item(6).lastChild.nodeValue=items[8];}catch(e){} 
        
        
        
               // alert("1111::"+items[11]);
               // alert("12222:::"+items[12]);
              //  alert("13333:::"+items[13]);
                
        rcells.item(7).firstChild.value=items[9];//txtBill_type
        var nex_cell=rcells.item(7).firstChild.nextSibling;
        nex_cell.value=items[10];
        var nex_cell=nex_cell.nextSibling;//txtAgree_Date
        nex_cell.value=items[11];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[11];
        
       
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[12];
        var nex_cell=nex_cell.nextSibling;
        nex_cell.value=items[13];
        rcells.item(7).lastChild.nodeValue=items[12];
        alert("Record Updated");
        clearall();
  }

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



/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
window.onunload=function()
{
	    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
	    if (winjob && winjob.open && !winjob.closed) winjob.close();
	    if (winemp && winemp.open && !winemp.closed) winemp.close();

}
/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
       
        var cmbSL_type=document.getElementById("cmbSL_type");
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_type);   
        clear_Combo(cmbSL_Code);   
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        doFunction('checkCode','null');
        try{com_cmbSL_type=rcells.item(3).firstChild.value; } catch(e){com_cmbSL_type="";}
        try{com_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){com_cmbSL_Code="";} 

        if(com_cmbSL_type==5)
        {
              document.getElementById("txtOfficeID_trs").value=com_cmbSL_Code;
              job_flag=false;              
        }
        
        if(com_cmbSL_type==7)
        {
              document.getElementById("txtEmpID_trs").value=com_cmbSL_Code;
              emp_flag=false;             
        }
        
       
        if((document.getElementById("txtAcc_HeadCode").value==820103 || document.getElementById("txtAcc_HeadCode").value==820102) && com_cmbSL_type==7)   
        {        	  
	          loadSLType(com_cmbSL_Code,com_cmbSL_type);
        }
        else
        	  doFunction('Load_SL_Code',com_cmbSL_type);
                
        if(rcells.item(2).firstChild.value=="CR")
        	 document.frmJournal_Imprest.rad_sub_CR_DR[0].checked=true;
        else if(rcells.item(2).firstChild.value=="DR")
        	 document.frmJournal_Imprest.rad_sub_CR_DR[1].checked=true;
         
      
       try{document.getElementById("txtBill_NO").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_date").value=rcells.item(6).firstChild.value;}catch(e){}       
       try{document.getElementById("txtBill_type").value=rcells.item(7).firstChild.value;}catch(e){}       
       var nex=rcells.item(7).firstChild.nextSibling;  
   //  alert(":::::"+nex.value);
     try{document.getElementById("txtAgree_No").value=nex.value;}catch(e){}
       nex=nex.nextSibling;
     //  if
       if(nex.value=="N")
       {
    	 
    	   document.frmJournal_Imprest.finalPayment[1].checked=true;
    	   document.frmJournal_Imprest.finalPayment[0].checked=false;
       }
       else if(nex.value=="Y")
       {
    	   document.frmJournal_Imprest.finalPayment[0].checked=true;
    	   document.frmJournal_Imprest.finalPayment[1].checked=false;
       }
     //  nex=nex.nextSibling;
     //  try{document.getElementById("txtAgree_No").value=nex.value;}catch(e){}
       nex=nex.nextSibling;
       try{document.getElementById("txtAgree_Date").value=nex.value;}catch(e){}
       nex=nex.nextSibling;
       try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
       nex=nex.nextSibling;
       try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
      
       
	   document.frmJournal_Imprest.cmdupdate.style.display='block';
	   document.frmJournal_Imprest.cmddelete.disabled=false;
	   document.frmJournal_Imprest.cmdadd.style.display='none';
	   //document.getElementById("cmbSL_Code").value=com_cmbSL_Code;   
	   setTimeout('document.getElementById("cmbSL_Code").value=com_cmbSL_Code',900); 
	   setTimeout('document.getElementById("cmbSL_type").value=com_cmbSL_type',900); 

}


/////////////////////////////////////////////   clearall() by User /////////////////////////////////////////////////////

function clearall()
{
	   document.getElementById("offlist_div_trans").style.display='none';
	   document.getElementById("emplist_div_trans").style.display='none';   
	   
	   document.frmJournal_Imprest.rad_sub_CR_DR[0].checked=false;
	   document.frmJournal_Imprest.rad_sub_CR_DR[1].checked=false;
	   
	   
	   document.getElementById("cmbSL_type").value="";
	   document.getElementById("cmbSL_Code").value="";
	   document.getElementById("txtEmpID_trs").value=""; 
	   document.getElementById("txtBill_NO").value="";
	   document.getElementById("txtBill_date").value="";
	   document.getElementById("txtBill_type").value="";
	   document.getElementById("txtAgree_No").value="";
	   document.getElementById("txtAgree_Date").value="";
	   document.getElementById("txtsub_Amount").value="";
	   document.getElementById("txtParticular").value="";
	    
	   document.getElementById("offlist_div_trans").style.display='none';              
	   var cmbSL_Code=document.getElementById("cmbSL_Code");   
	   clear_Combo(cmbSL_Code);   
	
	   document.frmJournal_Imprest.cmdadd.style.display='block';
	   document.frmJournal_Imprest.cmdupdate.style.display='none';
	   document.frmJournal_Imprest.cmddelete.disabled=true;
}
function call_clr()
{
	  // document.getElementById("txtCB_Month").value="";
	   document.getElementById("txtRemarks").value="";
	//   document.getElementById("txtJournalVou_No").value="";
       document.getElementById("total_remaining_amt").value="";
   //    document.frmJournal_Imprest.cmbMas_SL_type.selectedIndex=0;
         if(document.getElementById("cmbMas_SL_type").value=="68-BPF" || document.getElementById("cmbMas_SL_type").value=="68-SC")
             {
               document.getElementById("txtAcc_HeadCode").value=820103;
               }
               else
               {
               document.getElementById("txtAcc_HeadCode").value=820102;
               }
	   var tbody=document.getElementById("grid_body");
	   var t=0;
	   for(t=tbody.rows.length-1;t>=0;t--)
	   {
	    	  tbody.deleteRow(0);
	   }
}
function clrForm()
{
	   if(window.confirm("Do you want to clear ALL fields ?"))
	   {
		   	  call_clr();
	   }
}


/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

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
   	   if(document.getElementById("txtJournalVou_No").value=="")
	   {
		    alert("Select Voucher Number");
		    return false;    
	   }
   	   if(document.getElementById("txtCrea_date").value.length==0)
   	   {
		    alert("Enter the Date of Creation");
		    return false;    
   	   }
            if (document.getElementById("txtRemarks").value== "") {
		alert("Enter Remarks");
		// document.getElementById("txtCrea_date").focus();
		return false;
	}
           
                var journalDt=document.getElementById("txtCrea_date").value;
                var dat=journalDt.split("/");
   
                var paymentDt=document.getElementById("pay_date").value;
                var pay=paymentDt.split("/");
   
                if(pay[2]==dat[2]){
                
                
                if(dat[1]<pay[1])
                {
                    if(dat[0]<pay[0])
                    {
                    alert("Journal Date should be Greater than Payment Date*");
                    document.getElementById("txtCrea_date").value="";
                    }
                }
                else if(dat[1]==pay[1])
                {
                    if(dat[0]<pay[0])
                    {
                    alert("Journal Date should be Greater than Payment Date");
                    document.getElementById("txtCrea_date").value="";
                    }
                }
                }
                else if(dat[2]<pay[2])
                {
                 alert("Journal Date should be Greater than Payment Date");
                }
           
   	   if(document.getElementById("txtRemarks").value!="")
   	   {
   		    if((document.getElementById("txtRemarks").value.length)>=190)
   		    {
   		    		  alert("Please Enter Remarks below 200 characters");
   		    		  document.getElementById("txtRemarks").value="";
   		    		  return false;
   		    }
   	   }
   	   if(document.getElementById("txtParticular").value!="")
	   {
		    if((document.getElementById("txtParticular").value.length)>=190)
		    {
		    		  alert("Please Enter Particulars below 200 characters");
		    		  document.getElementById("txtParticular").value="";
		    		  return false;
		    }
	   }
   	   if(document.getElementById("cmbMas_SL_type").value=="")
   	   {
		   
		    alert("Select The Journal Type in General");
		    return false;
		   
   	   }
   	   if(tbody.rows.length==0)
   	   {
		    alert("Enter the Details Part");
		    return false; 
   	   }
	   if(tbody.rows.length>0)
	   {
	        var dr_check_amt=0;var cr_check_amt=0;var counting=0;
	        rows=tbody.getElementsByTagName("tr");
	        for(i=0;i<rows.length;i++)
	        {
	                  var cells=rows[i].cells;  
	                  var s=cells.length;
	                 
	                 
	              //    alert("cells"+cells.item(1).firstChild.value);
	                  if(cells.item(2).lastChild.nodeValue=='DR')
	                  {
	                	  dr_check_amt=parseFloat(dr_check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
	                  }  
	                  else
	                  {
	                	  cr_check_amt=parseFloat(cr_check_amt) + parseFloat(cells.item(7).lastChild.nodeValue);
	                  }
	                	  
	               	//  if(document.getElementById("cmbMas_SL_type").value==68)
	                  if(document.getElementById("cmbMas_SL_type").value=="68-BPF")
	                	  {
		                	   if(cells.item(1).firstChild.value==820103)	 
		                		  {
		                		     counting++;
		                		  }
	                	  }
	                  else if(document.getElementById("cmbMas_SL_type").value=="68-SC")
		                	  {
				               	   if(cells.item(1).firstChild.value==820103)	 
				               		  {
				               		     counting++;
				               		  }
		                	  	}
	                   else if(document.getElementById("cmbMas_SL_type").value=="69-BPF")
                                {
				               	   if(cells.item(1).firstChild.value==820102)	 
				               		  {
				               		     counting++;
				               		  }
                                }	
                                else
	                	  {
	                		  if(cells.item(1).firstChild.value==820102)
	                			  counting++;
	                	  }
	              //    }
	                  
	        }      
	        if(dr_check_amt!=cr_check_amt)
	        {
	            	 alert("Total Amount of DR & CR should be equal");
	                 return false; 
	        }
             //   
	        else
	        {
	            	if(counting==0)
	            	{
	            			
	            		alert(document.getElementById("cmbMas_SL_type").value);
	            		 if(document.getElementById("cmbMas_SL_type").value=="68-BPF")
                                 {
	            				alert("Detail should have at least one Account Head 820103 ");
                                  }
	            		 else if(document.getElementById("cmbMas_SL_type").value=="68-SC")
                                 {
	            				alert("Detail should have at least one Account Head 820103 ");
                                  }
	            		 else if(document.getElementById("cmbMas_SL_type").value=="69-BPF")
                                 {
	            				alert("Detail should have at least one Account Head 820102 ");
                                 }
                                 else if(document.getElementById("cmbMas_SL_type").value=="69-SC")
                                 {
	            				alert("Detail should have at least one Account Head 820102 ");
                                 }
	            			return false;
	            	}
	        }  
            var total_remaining_amt=document.getElementById("total_remaining_amt").value;
            //alert("total_amount"+total_remaining_amt);
         //   alert("cr_check_amt::::"+cr_check_amt);
	        if(cr_check_amt>total_remaining_amt)
	        {
	        		alert("Amount should not exceed "+total_remaining_amt);
	        		return false;
	        }
	            
	   }
	   return true;

}


function checkVoucherNo()
{
	   if(document.getElementById("txtJournalVou_No").value=="")
	   {
		    alert("Select Voucher Number");
		    
	   }
	   else
	   {
		   //dhana
		   var txtCB_Year;
	   		var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	   		var office_code=document.getElementById("cmbOffice_code").value; 
                        var pay_date= document.getElementById("pay_date").value;
                        var spl1=pay_date.split("/");
                        //alert(spl1[2].length);
                        if(spl1[2].length==2)
                        {
                        	txtCB_Year="20"+spl1[2];	
                        }
                        else{
                        		txtCB_Year=spl1[2];
                        }
	   		
	   		var txtCB_Month= spl1[1];
	   		var txtVou_no= document.getElementById("txtJournalVou_No").value;
	   		//var txtHead_code= document.getElementById("txtJournalVou_No").value;
	   		var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/Imprest/jsps/Imprest_Account_ListAll_SL.jsp?cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&yr="+txtCB_Year+"&mon="+txtCB_Month+"&recNo="+txtVou_no,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	   	    Voucher_list_SL.moveTo(250,250);  
	   	    Voucher_list_SL.focus();
	   }
		   
}

function leapYear()
{
alert("leapYear");
var today= new Date(); 
	         var day=today.getDate();
	         var month=today.getMonth();
	         month=month+1;
	         
	         if(day<=9 && day>=1)
	         day="0"+day;
	         if(month<=9 && month>=1)
	         month="0"+month;
	         var year=today.getYear();
	         if(year < 1900) year += 1900;
	         var monthArray =new Array("January", "February", "March", 
	                   "April", "May", "June", "July", "August",
	                   "September", "October", "November", "December");
/*int year = 1700;
    int day = 0;
    if (year % 4 == 0) {
        if (year % 100 == 0)
        {
            if (year % 400 == 0)
            {
                day = 29;
            } else {
                day = 28;
            }
        } else
        {
            day = 29;
        }
    } else {
        day = 28;
    }  */
}
//added as on 24Feb2012 date checkinf for trial balance-----------
function call_date(dateCtrl)                        // TB_checking 
{         
    //alert("checking date for TB");    
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
                        dateCheck(dateCtrl); 
                }
         }
}



