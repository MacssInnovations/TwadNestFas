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

function clearJournal()
{
		 document.TDA_TCA.Journal_type[0].checked=false;
		 document.TDA_TCA.Journal_type[1].checked=false; 
		 document.getElementById("responded_slno").length=1;
}

function doFunction_TDA(command)
{   
		 
	     clrVoucherDetails();
		 var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value 
	     var Journal_type= document.getElementById("Journal_type").value;   
	     var txtCrea_date= document.getElementById("txtCrea_date").value;       
	     var supNo= document.getElementById("supNo").value;       
         if(command=="load_Voucher_No") 
		 {
        	    url="../../../../../TDA_TCA_Responding_Edit_Supp?command=loadVr_sup&Option=Edit&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type+"&supNo="+supNo;           			  
		 }
		 else if(command=="load_Voucher_Details") 
		 {
			    var responded_slno=document.getElementById("responded_slno").value;
			    url="../../../../../TDA_TCA_Responding_Edit?command=loadVoucherDetails&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCrea_date="+txtCrea_date+"&Journal_type="+Journal_type+"&responded_slno="+responded_slno+"&supNo="+supNo;            			  
		 }
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                Acceptance_ServletResponse(req);
         }   
         req.send(null);     
}

/*
function loadVoucherDetails()
{
	     clrVoucherDetails();
		 var UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
	     var office_code=document.getElementById("cmbOffice_code").value 
	     var Journal_type= document.getElementById("Journal_type").value;   
	     var txtCB_Year= document.getElementById("txtCB_Year").value;   
	     var txtCB_Month= document.getElementById("txtCB_Month").value;
	     if(Journal_type=="TDAR")
	   			Journal_type="TDAO";
	     else
	   			Journal_type="TCAO";
	     var originated_slno= document.getElementById("originated_slno").value;
	     url="../../../../../TDA_TCA_Responding_Create?command=loadVoucherDetails&cmbAcc_UnitCode="+UnitCode+"&cmbOffice_code="+office_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&Journal_type="+Journal_type+"&originated_slno="+originated_slno;
	     req=getTransport();
	     req.open("GET",url,true);        
	     req.onreadystatechange=function()
	     {        	  
	            Acceptance_ServletResponse(req);
	     }   
	     req.send(null);     
	
}
*/
function changeLink()
{
		 if(document.getElementById("responded_slno").value!="")
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
                         if(Command=="loadVr_sup")
                         {                                       
                               if(flag=="success")
                               {                                      
                                       var responded_slno=document.getElementById("responded_slno");                                 
                                       var count=baseResponse.getElementsByTagName("voucher_no");
                                       for(var k=0;k<count.length;k++)
                                       {
                                                var voucher_no=baseResponse.getElementsByTagName("voucher_no")[k].firstChild.nodeValue;				       	                                                  
                                                var option=document.createElement("OPTION");
                                                option.text=voucher_no;
                                                option.value=voucher_no;
                                                try
                                                {
                                                	responded_slno.add(option);
                                                }
                                                catch(errorObject)
                                                {
                                                	responded_slno.add(option,null);
                                                }
                                       }
                               }
                               else
                               {                                                   
                                       document.getElementById("responded_slno").value="";
                                       alert("No Voucher Found");
                               }
                         }
                        /* else if(Command=="loadVoucherDetails")
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
	                        	   	   var accounting_unit_name=baseResponse.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
	                        	   	   var amount=baseResponse.getElementsByTagName("amount")[0].firstChild.nodeValue;	                        	   	   
	                        	   	   document.getElementById("originated_sldate").value=originated_sldate
	                        	   	   document.getElementById("originated_jvr_no").value=originated_jvr_no
	                        	   	   document.getElementById("originated_jvr_date").value=originated_jvr_date
	                        	   	   document.getElementById("accepted_slno").value=accepted_slno
	                        	   	   document.getElementById("accepted_sldate").value=accepted_sldate
	                        	   	   document.getElementById("accepted_jvr_no").value=accepted_jvr_no
	                        	   	   document.getElementById("accepted_jvr_date").value=accepted_jvr_date
	                        	   	   document.getElementById("accepted_unit_id").value=accounting_unit_name
	                        	   	   document.getElementById("txtTotalAmt").value=amount
	                           }
	                    	  
                         }*/
                                 
              }
                
		 }    
}

function clrVoucherDetails()
{
	    document.getElementById("originated_slno").value="";
		document.getElementById("originated_sldate").value="";
   	    document.getElementById("originated_jvr_no").value="";
   	    document.getElementById("originated_jvr_date").value="";   	    
   	    document.getElementById("accepted_slno").value="";
   	    document.getElementById("accepted_sldate").value="";
   	    document.getElementById("accepted_jvr_no").value="";
   	    document.getElementById("accepted_jvr_date").value="";
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
				document.getElementById("cr_accHead_code").value="901001";
				document.getElementById("dr_accHead_code").value="901002";
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
        return true;
       		
}

function ShowDetails(param)
{
	var unitcode=document.getElementById("cmbAcc_UnitCode").value;	
    var offid=document.getElementById("cmbOffice_code").value 
    if(param=="originated_slno")
    {
	    var vou_no= document.getElementById("originated_slno").value;   
    }
    else if(param=="originated_jvr")
    {
    	var vou_no= document.getElementById("originated_slno").value;  
    	var originated_sldate=document.getElementById("originated_sldate").value;  
    	dt=originated_sldate.split("/");
 	    var mon=dt[1];   
 	    var yr=dt[2];
    }
    else if(param=="accepted_slno")
    {
    	var vou_no= document.getElementById("accepted_slno").value;  
    	var accepted_sldate=document.getElementById("accepted_sldate").value;  
    	dt=accepted_sldate.split("/");
 	    var mon=dt[1];   
 	    var yr=dt[2];
    }
    else if(param=="accepted_jvr")
    {
    	var vou_no= document.getElementById("accepted_jvr_no").value;  
    	var accepted_jvr_date=document.getElementById("accepted_jvr_date").value;  
    	dt=accepted_jvr_date.split("/");
 	    var mon=dt[1];   
 	    var yr=dt[2];
    }
    if(param=="originated_slno" || param=="accepted_slno")
    {
	    var Voucher_list_SL= window.open("../../../../../org/FAS/FAS1/TDA/jsps/TDA_TCA_ListAll_SL.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cashbook_mn="+mon+"&voucher_no="+vou_no,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
function Check_Supplement_No()
{
      var txtCrea_date=document.getElementById("txtCrea_date").value;
     
       var url="../../../../../Supplement_Journal_Create.kv?Command=Check_Supplement_No&txtCrea_date="+txtCrea_date;
      // alert(url);
       var req=getTransport();
       req.open("GET",url,true); 
       req.onreadystatechange=function()
       {
           Check_Supplement_No_Response(req);
       }   
       req.send(null);
}

function Check_Supplement_No_Response(req)
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
                var suppl_error=baseResponse.getElementsByTagName("suppl_error")[0].firstChild.nodeValue;
                alert(suppl_error);  
                //document.getElementById("txtCrea_date").value="";
              }
              else if(flag=="success")
              {
               var supNo1 = document.forms[0].supNo;
                 var supno = baseResponse.getElementsByTagName("supno"); 
                 for(var i=0; i<supno.length; i++)
                     {
                         var opt = document.createElement('option');
                         opt.value = supno[i].firstChild.nodeValue;
                         opt.innerHTML = supno[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                         supNo1.appendChild(opt);
                     }
              
              
              }

       }
  }
}



