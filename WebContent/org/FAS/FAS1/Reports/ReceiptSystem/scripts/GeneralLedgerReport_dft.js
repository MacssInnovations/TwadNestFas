function checknull()
{

    if((document.frmGeneralLedgerReport.cmbOffice_code.value=="") || (document.frmGeneralLedgerReport.cmbOffice_code.value.length<=0) || (document.frmGeneralLedgerReport.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmGeneralLedgerReport.cmbOffice_code.focus();
        return false;
    
    }
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    
    if(document.frmGeneralLedgerReport.SpecificAHC[1].checked==true)
    {
        if((document.frmGeneralLedgerReport.txtAcc_HeadCode.value=="") || (document.frmGeneralLedgerReport.txtAcc_HeadCode.value.length<=0))
        {
            alert("Please Select or Enter Account Head Code");
            document.frmGeneralLedgerReport.txtAcc_HeadCode.focus();
            return false;
        }
        else
        {
            return true;
        }
    }
 return true;
}


/*
 *  Display one of Cash Book month and Year 
 */

function cb_month_year(id)
{
   var particular=document.getElementById("particular");
   var more=document.getElementById("more");
       
  if(id=="particular_cb")
  {
     particular.style.display="block";
     more.style.display="none";
  }
  if(id=="more_cb")
  {
    more.style.display="block";
    particular.style.display="none";
  }
  if(id=="more_cb_monthwise")
  {
    more.style.display="block";
    particular.style.display="none";
  }
}




function AccHead(id)
{
    var SpecificAHC=document.getElementById("acchead");
   if(id=="Specific")
   {
    SpecificAHC.style.display="block";
   }
   else
   {
   SpecificAHC.style.display="none";
   }
}


var winAccHeadCode;

function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

function doParentAccHead(code)
{
   document.getElementById("txtAcc_HeadCode").value=code;
   //doFunction('checkCode','null');
   return true;
}
window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
}

function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode
         if(unicode==13)
        {
          //try{t.blur();}catch(e){}
          //return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}

function clear()
{
//alert('cal');
var SpecificAHC=document.getElementById("acchead");
SpecificAHC.style.display="block";
}
//CallServer Function

function doFunction(Command,param)
{   
    var url="";
    if(Command=="checkCode")
    {
        var headcode=document.frmGeneralLedgerReport.txtAcc_HeadCode.value;
        
        url="../../../../../BudgetMasterServlet.con?Command=HeadCode&txtAcc_Head_code="+headcode;
        var req=getTransport();
        req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse(req);
            }   
                    req.send(null);
    }
}


function headcodeResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                var headname=baseResponse.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                //document.frmGeneralLedgerReport.txtaccountheadname.value="";
                //document.frmGeneralLedgerReport.txtaccountheadname.value=headname;
            }
            else
            {
                document.frmGeneralLedgerReport.txtAcc_HeadCode.value="";
                //document.frmGeneralLedgerReport.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmGeneralLedgerReport.txtAcc_HeadCode.focus();
                
            }
        }
        
    }

}



function submit_form()
{
    	 
	   //alert("coming here to check all offices for the single unit*********");
	   if(document.frmGeneralLedgerReport.SpecificAHC[0].checked==true )
	   {
	        var frm = document.getElementById("frmGeneralLedgerReport");
	         
	         /** For Particular Month. It calls old report */
	         if(document.frmGeneralLedgerReport.month_year[0].checked==true)
	             {   
	                frm.action = "../../../../../../GeneralLedgerReport.con?command=rep";
	                frm.submit();
	             }
	         /** For All Months . It calls New Report */  
	         if(document.frmGeneralLedgerReport.month_year[1].checked==true)
	            { 
	                frm.action = "../../../../../../GeneralLedgerReport_two.kv";
	                frm.submit();
	            }
	           
	            
	    }
	   if(document.frmGeneralLedgerReport.SpecificAHC[1].checked==true )
   	   {   
	     
	         var frm = document.getElementById("frmGeneralLedgerReport");
	         
	         /** For Particular Month. It calls old report */
	         if(document.frmGeneralLedgerReport.month_year[0].checked==true)
	             {   
	                frm.action = "../../../../../../GeneralLedgerReport.con?command=rep";
	                frm.submit();
	             }
	         /** For All Months . It calls New Report */  
	         if(document.frmGeneralLedgerReport.month_year[1].checked==true ||document.frmGeneralLedgerReport.month_year[2].checked==true)
	            {
	                frm.action = "../../../../../../GeneralLedgerReport_one.kv";
	                frm.submit();
	            }
	   
	   } 
   
//   else 
//	   {
//	   alert("Loading....");
//	      //alert("coming here for not choosing the all office option");
//	   		if(document.frmGeneralLedgerReport.SpecificAHC[0].checked==true )
//		   {
//		        var frm = document.getElementById("frmGeneralLedgerReport");
//		         
//		         /** For Particular Month. It calls old report */
//		         if(document.frmGeneralLedgerReport.month_year[0].checked==true)
//		             {   
//		        	
//		        	 	frm.action = "../../../../../../GeneralLedgerReport.con?command=rep";
//		                frm.submit();
//		             }
//		         /** For All Months . It calls New Report */  
//		         if(document.frmGeneralLedgerReport.month_year[1].checked==true)
//		            { 
//		               
//		        	    frm.action = "../../../../../../GeneralLedgerReport_two.kv";
//		                frm.submit();
//		            }
//		            if(document.frmGeneralLedgerReport.month_year[2].checked==true)
//		            {
//		            	//alert("111");
//		            	frm.action = "../../../../../../GeneralLedgerReport_two.kv";
//		                frm.submit();
//		            }
//		            
//		    }
//		   if(document.frmGeneralLedgerReport.SpecificAHC[1].checked==true )
//	   	   {   
//		     
//		         var frm = document.getElementById("frmGeneralLedgerReport");
//		         
//		         /** For Particular Month. It calls old report */
//		         if(document.frmGeneralLedgerReport.month_year[0].checked==true)
//		             {   
//		        	
//		        	 	frm.action = "../../../../../../GeneralLedgerReport.con?command=rep";
//		                frm.submit();
//		             }
//		         /** For All Months . It calls New Report */  
//		         if(document.frmGeneralLedgerReport.month_year[1].checked==true ||document.frmGeneralLedgerReport.month_year[2].checked==true)
//		            {
//		        	 //alert("sss");   
//		        	    frm.action = "../../../../../../GeneralLedgerReport_one.kv";
//		                frm.submit();
//		            }
//		   
//		   } 
//	   }  
  
}
//added for more than one offices for single acc unitd id ***********
function call_AllOfiices(id)
{
	if(id=="all_office")
		{
		 alert("select other options");
		}
}
function show_effectivedetails()
{
		var office_code=document.getElementById("cmbOffice_code").value;
		
		url="../../../../../../GeneralLedgerReport.con?command=getofficedetails&office_id="+office_code;
       
		var req=getTransport();
        req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse1(req);
            }   
                    req.send(null);

}
function headcodeResponse1(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var command = baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
            if(command=="getofficedetails")
            	{
            		var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            			
			            if(flag=="success")
			            {
			                var formation_date=baseResponse.getElementsByTagName("formationdate")[0].firstChild.nodeValue;
			                var effective_from=baseResponse.getElementsByTagName("effectivefrom")[0].firstChild.nodeValue;
			                document.getElementById("starting_date").value=formation_date;
			                document.getElementById("effective_from").value=effective_from;
			            }
			            else
			            {
			                document.frmGeneralLedgerReport.starting_date.value="";
			                document.frmGeneralLedgerReport.effective_from.value="";
			               // alert("Invalid HeadCode");
			                //document.frmGeneralLedgerReport.txtAcc_HeadCode.focus();
			                
			            }
            	}
        }
        
    }

}
