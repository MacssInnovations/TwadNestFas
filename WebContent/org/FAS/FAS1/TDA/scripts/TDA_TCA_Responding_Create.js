var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
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

function accDate()
{
            //alert("test");
            var respondingDate=document.getElementById("txtCrea_date").value;
            var accepted_jvr_date=document.getElementById("accepted_jvr_date").value;
           // alert("respondingDate::::::"+respondingDate);
           // alert("accepted_jvr_date::::::"+accepted_jvr_date);
           
     var str1 =respondingDate;
  //  alert(str1);
    var str2 = accepted_jvr_date;
 //    alert(str2);
    var dt1  = parseInt(str1.substring(0,2),10);
  //   alert(dt1);
    var mon1 = parseInt(str1.substring(3,5),10);
    // alert(mon1);
    var yr1  = parseInt(str1.substring(6,10),10);
  //   alert(yr1);
    var dt2  = parseInt(str2.substring(0,2),10);
  //   alert(dt2);
    var mon2 = parseInt(str2.substring(3,5),10);
  //   alert(mon2);
    var yr2  = parseInt(str2.substring(6,10),10);
  //   alert(yr2);
    var date1 = new Date(yr1, mon1, dt1);
  //   alert(date1);
    var date2 = new Date(yr2, mon2, dt2);
     
    
     // alert(date2);
     if( date1<date2 )
     {
         alert("Responding Date should not less than Accepting Date");
         document.getElementById("txtCrea_date").value="";
         document.getElementById("txtCrea_date").focus();
         return false;
     }
     return true;
//                   if((respondingDate)<(accepted_jvr_date))
//                    {
//                            alert("Responding Date should not less than Accepting Date");
//                            document.getElementById("txtCrea_date").value="";
//                            document.getElementById("txtCrea_date").focus();
//                            return true;
//                    }
//                    else if((respondingDate)>(accepted_jvr_date))
//                    {
//                        return false;
//                    }

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


function resetMonth()
{
		 document.getElementById("txtCB_Month").value="";
}

function loadVoucher()
{   

		 var originated_slno=document.getElementById("originated_slno");
	     var child=originated_slno.childNodes;
	     for(var i=child.length-1;i>1;i--)
	     {
	    	 	originated_slno.removeChild(child[i]);
	     }
	     clrVoucherDetails();
		 var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value 
	     var Journal_type; 
             var type2;
	     if(document.TDA_TCA.Journal_type[0].checked==true)
	    		{
                        Journal_type="TDAO";
                        type2="TDACB";
                        }
	     else{
	    		Journal_type="TCAO";
                        type2="TCACB";
                        }
	     var txtCB_Year= document.getElementById("txtCB_Year").value;   
	     var txtCB_Month= document.getElementById("txtCB_Month").value;
	    
         url="../../../../../TDA_TCA_Responding_Create?command=loadVoucher&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&Journal_type="+Journal_type+"&type2="+type2;
    // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                Acceptance_ServletResponse(req);
         }   
         req.send(null);     
}


function loadVoucherDetails()
{
	     clrVoucherDetails();
	     document.getElementById("butSub").disabled=false;
		 var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value;
	    // alert("off code *****"+office_code);
	     var txtCB_Year= document.getElementById("txtCB_Year").value;   
	     var txtCB_Month= document.getElementById("txtCB_Month").value;
	     var Journal_type; 
              var type2;
	     if(document.TDA_TCA.Journal_type[0].checked==true)
	    		{Journal_type="TDAO";
                        type2="TDACB";}
	     else
	    		{
                        Journal_type="TCAO";
                          type2="TCACB";
                        }
	     var originated_slno=document.getElementById("originated_slno").value;
	     url="../../../../../TDA_TCA_Responding_Create?command=loadVoucherDetails&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&Journal_type="+Journal_type+"&originated_slno="+originated_slno+"&type2="+type2;
	     //alert(url);
	     req=getTransport();
	     req.open("GET",url,true);        
	     req.onreadystatechange=function()
	     {        	  
	            Acceptance_ServletResponse(req);
	     }   
	     req.send(null);     
	
}

function changeLink()
{
		 if(document.getElementById("originated_slno").value!="")
		 {
				document.getElementById("linkId").style.visibility="visible";
				document.getElementById("linkId1").style.visibility="visible";
				document.getElementById("linkId2").style.visibility="visible";
				document.getElementById("linkId3").style.visibility="visible";
		 }
		 else
		 {
				document.getElementById("linkId").style.visibility="hidden";
				document.getElementById("linkId1").style.visibility="hidden";
				document.getElementById("linkId2").style.visibility="hidden";
				document.getElementById("linkId3").style.visibility="hidden";
		 }
}

function Acceptance_ServletResponse(req,slcode)
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
                                       var originated_slno=document.getElementById("originated_slno");                                 
                                       var count=baseResponse.getElementsByTagName("voucher_no");
                                       for(var k=0;k<count.length;k++)
                                       {
                                                var voucher_no=baseResponse.getElementsByTagName("voucher_no")[k].firstChild.nodeValue;				       	                                                  
                                                var option=document.createElement("OPTION");
                                                option.text=voucher_no;
                                                option.value=voucher_no;
                                                try
                                                {
                                                		originated_slno.add(option);
                                                }
                                                catch(errorObject)
                                                {
                                                		originated_slno.add(option,null);
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
	                    		   	   
	                        	   	   var originated_sldate=baseResponse.getElementsByTagName("originated_sldate")[0].firstChild.nodeValue;
	                        	   	   var originated_jvr_no=baseResponse.getElementsByTagName("originated_jvr_no")[0].firstChild.nodeValue;
	                        	   	   var originated_jvr_date=baseResponse.getElementsByTagName("originated_jvr_date")[0].firstChild.nodeValue;
	                        	   	   var accepted_slno=baseResponse.getElementsByTagName("accepted_slno")[0].firstChild.nodeValue;
	                        	   	   var accepted_sldate=baseResponse.getElementsByTagName("accepted_sldate")[0].firstChild.nodeValue;
	                        	   	   var accepted_jvr_no=baseResponse.getElementsByTagName("accepted_jvr_no")[0].firstChild.nodeValue;
	                        	   	   var accepted_jvr_date=baseResponse.getElementsByTagName("accepted_jvr_date")[0].firstChild.nodeValue;
	                        	   	   var accounting_office_id=baseResponse.getElementsByTagName("accounting_office_id")[0].firstChild.nodeValue;
	                        	   	   var accounting_unit_id=baseResponse.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
	                        	   	   var accounting_unit_name=baseResponse.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
	                        	   	   var amount=baseResponse.getElementsByTagName("amount")[0].firstChild.nodeValue;	                        	   	   
	                        	   	   document.getElementById("originated_sldate").value=originated_sldate
	                        	   	   document.getElementById("originated_jvr_no").value=originated_jvr_no
	                        	   	   document.getElementById("originated_jvr_date").value=originated_jvr_date
	                        	   	   document.getElementById("accepted_slno").value=accepted_slno
	                        	   	   document.getElementById("accepted_sldate").value=accepted_sldate
	                        	   	   document.getElementById("accepted_jvr_no").value=accepted_jvr_no
	                        	   	   document.getElementById("accepted_jvr_date").value=accepted_jvr_date
	                        	   	   document.getElementById("accepted_unit_name").value=accounting_unit_name
	                        	   	   document.getElementById("accepted_unit_id").value=accounting_unit_id;
	                        	   	   document.getElementById("accepted_office_id").value=accounting_office_id;
	                        	   	   document.getElementById("txtTotalAmt").value=amount
	                           }
	                    	  
                         }
                                 
                }
                
		 }    
}

function clrVoucherDetails()
{
		document.getElementById("originated_sldate").value="";
   	    document.getElementById("originated_jvr_no").value="";
   	    document.getElementById("originated_jvr_date").value="";   	    
   	    document.getElementById("accepted_slno").value="";
   	    document.getElementById("accepted_sldate").value="";
   	    document.getElementById("accepted_jvr_no").value="";
   	    document.getElementById("accepted_jvr_date").value="";
   	    document.getElementById("accepted_unit_name").value="";
   	    document.getElementById("accepted_office_id").value="";
   	    document.getElementById("accepted_unit_id").value="";
 	   
}

function clrForm(param)
{	
		document.TDA_TCA.Journal_type[0].checked=true;
		document.getElementById("cr_accHead_code").value="900108";
		document.getElementById("dr_accHead_code").value="900109";
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
		document.getElementById("linkId").style.visibility="hidden";
        document.getElementById("txtCB_Month").value="";
        document.getElementById("originated_slno").value="";
        clrVoucherDetails();
        document.getElementById("txtRemarks").value="";        
}

function checkAccountHead()
{
		if(document.TDA_TCA.Journal_type[0].checked==true)
		{
				document.getElementById("cr_accHead_code").value="900108";
				document.getElementById("dr_accHead_code").value="900109";
		}
		else
		{
				document.getElementById("cr_accHead_code").value="901002";
				document.getElementById("dr_accHead_code").value="901001";
		}
				
}


function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    	    alert("Please Enter below 200 characters");
	    }	 
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
        if(document.getElementById("originated_slno").value=="")
        {
            	alert("Select Originated Sl.No.");
            	return false;
        }
        if(document.getElementById("txtCrea_date").value.length==0)
        {
	            alert("Enter the Date of Creation");           
	            return false;    
        }
         document.getElementById("butSub").disabled=true;
        return true;
       		
}

function ShowDetails(param)
{
		
	    if(param=="originated_slno")
	    {
	    		var unitcode=document.getElementById("cmbAcc_UnitCode").value;	
	    		var offid=document.getElementById("cmbOffice_code").value; 
		    	var vou_no= document.getElementById("originated_slno").value;   
			    var yr= document.getElementById("txtCB_Year").value;   
			    var mon= document.getElementById("txtCB_Month").value;
	    }
	    else if(param=="originated_jvr")
	    {
	    		var unitcode=document.getElementById("cmbAcc_UnitCode").value;	
	    		var offid=document.getElementById("cmbOffice_code").value;
		    	var vou_no= document.getElementById("originated_jvr_no").value;  
		    	var originated_sldate=document.getElementById("originated_jvr_date").value;  
		    	dt=originated_sldate.split("/");
		 	    var mon=dt[1];   
		 	    var yr=dt[2];
		 	
	    }
	    else if(param=="accepted_slno")
	    {
	    		var offid=document.getElementById("accepted_office_id").value;
	    		var unitcode=document.getElementById("accepted_unit_id").value;
		    //	var vou_no= document.getElementById("accepted_slno").value; 
                    
                    var vou_no= document.getElementById("originated_slno").value; 
                    
		    	//var accepted_sldate=document.getElementById("accepted_sldate").value;  
                        var accepted_sldate=document.getElementById("originated_sldate").value;  
		    	dt=accepted_sldate.split("/");
		 	    var mon=dt[1];   
		 	    var yr=dt[2];
	    }
	    else if(param=="accepted_jvr")
	    {
		    	var offid=document.getElementById("accepted_office_id").value;
	    		var unitcode=document.getElementById("accepted_unit_id").value;
		    	var vou_no= document.getElementById("accepted_jvr_no").value;  
		    	var accepted_jvr_date=document.getElementById("accepted_jvr_date").value;  
		    	dt=accepted_jvr_date.split("/");
		 	    var mon=dt[1];   
		 	    var yr=dt[2];
	    }
	    if(param=="originated_slno")
	    {
	    var pp1="originated_slno";
			    var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+vou_no+"&param="+pp1,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
			    Voucher_list_SL.moveTo(250,250);  
			    Voucher_list_SL.focus();
	    }
	    else if(param=="accepted_slno")
	    {
	    	var pp="accepted_slno";
	    	 	var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+vou_no+"&param="+pp,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
			    Voucher_list_SL.moveTo(250,250);  
			    Voucher_list_SL.focus();
	    }
	    else
	    {
		    	Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/Journal_General_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&recNo="+vou_no,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
		 	    Voucher_list_SL.moveTo(250,250);  
		 	    Voucher_list_SL.focus();
	    }
}
// added on 25/07/2011
function call_date(dateCtrl)                        // TB_checking 
{      
//	alert("call_date");
        //document.TDA_TCA.isAccept[0].checked=false;
    //document.TDA_TCA.isAccept[1].checked=false;
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
                                  document.getElementById("txtCrea_date").value="";
                                  alert("Trial Balance Closed");//return false;//
                                  dateCtrl.focus();                                            
                         }
                         else if(flag=="finyear")
                         {
                                  // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                                  dateCtrl.value="";
                                  document.getElementById("txtCrea_date").value="";
                                  alert("Cash Book Control Not Found ");//return false;//
                                  dateCtrl.focus();
                                  //document.getElementById("txtVoucher_No").value="";     
                         }
                         dateCheck(dateCtrl); 
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