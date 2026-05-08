function checknull()
{

    if((document.frmOldTrailbalanceReport.cmbOffice_code.value=="") || (document.frmOldTrailbalanceReport.cmbOffice_code.value.length<=0) || (document.frmOldTrailbalanceReport.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmOldTrailbalanceReport.cmbOffice_code.focus();
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
   
    if(document.frmOldTrailbalanceReport.SpecificAHC[1].checked==true)
    {
        if((document.frmOldTrailbalanceReport.txtAcc_HeadCode.value=="") || (document.frmOldTrailbalanceReport.txtAcc_HeadCode.value.length<=0))
        {
            alert("Please Select or Enter Account Head Code");
            document.frmOldTrailbalanceReport.txtAcc_HeadCode.focus();
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
     optionAHC.style.display="block";
     optionAHC1.style.display="none";
  }
  if(id=="more_cb")
  {
    more.style.display="block";
    particular.style.display="none";
    optionAHC.style.display="none";
    optionAHC1.style.display="block";
   
   // document.getElementById("txtCB_Year_to").readonly = false;
    
    
  }
}

function finYr()
{
var fr_finyr=document.getElementById("txtCB_Year_from").value;
alert(fr_finyr+"******");
//document.getElementById("txtCB_Year_to").value=fr_finyr;
//alert(document.getElementById("txtCB_Year_to").value);
document.frmOldTrailbalanceReport.txtCB_Year_to.disabled=true;


document.getElementById("txtCB_Year_to").value=fr_finyr;

document.getElementById("myselect").value=fr_finyr;
//to_finyr=fr_finyr;
//var option=document.createElement("OPTION");
//option.text=fr_finyr;
//option.value=fr_finyr;
// try
//{
//	 to_finyr.add(option);
//}
//catch(errorObject)
//{
//	to_finyr.add(option,null);
//}
//alert("to_finyr>>>>"+document.getElementById("txtCB_Year_to").value);

}


function AccHead(id)
{
    var SpecificAHC=document.getElementById("acchead");
   if(id=="Specific" || id=="SpecificTB")
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
var SpecificAHC=document.getElementById("acchead");
SpecificAHC.style.display="block";
}
//CallServer Function

function doFunction(Command,param)
{  
    var url="";
    if(Command=="checkCode")
    {
        var headcode=document.frmOldTrailbalanceReport.txtAcc_HeadCode.value;
       
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
                //document.frmOldTrailbalanceReport.txtaccountheadname.value="";
                //document.frmOldTrailbalanceReport.txtaccountheadname.value=headname;
            }
            else
            {
                document.frmOldTrailbalanceReport.txtAcc_HeadCode.value="";
                //document.frmOldTrailbalanceReport.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmOldTrailbalanceReport.txtAcc_HeadCode.focus();
               
            }
        }
       
    }

}


function submit_form()
{
   
   /** Check whether All or Specific Account Head code has been selected */
  
   /** For All Account Head Code  */
  
   if(document.frmOldTrailbalanceReport.SpecificAHC[0].checked==true||document.frmOldTrailbalanceReport.SpecificAHC[1].checked==true
		||document.frmOldTrailbalanceReport.SpecificAHC[2].checked==true || document.frmOldTrailbalanceReport.SpecificAHC[3].checked==true
		|| document.frmOldTrailbalanceReport.SpecificAHC[4].checked==true|| document.frmOldTrailbalanceReport.SpecificAHC[5].checked==true
		||document.frmOldTrailbalanceReport.SpecificAHC[6].checked==true)
   {  
        var frm = document.getElementById("frmOldTrailbalanceReport");
        
         /** For Particular Month. It calls old report */
         if(document.frmOldTrailbalanceReport.month_year[0].checked==true)
             { // alert("inside");
                frm.action = "../../../../../../Old_Data_TrialBalance_Report_New";
                frm.submit();
             }
         /** For All Months . It calls New Report */ 
         if(document.frmOldTrailbalanceReport.month_year[1].checked==true)
            { // alert("Allfin");
                frm.action = "../../../../../../OldtrailbalanceReport_New";
                frm.submit();
            }
    }
  
  
   /** For Specific Account Head Code */
   //alert(document.frmOldTrailbalanceReport.SpecificAHC[3].value);
   
   //commanded on 24/04/2020
//   if(document.frmOldTrailbalanceReport.SpecificAHC[2].checked==true
//		   || document.frmOldTrailbalanceReport.SpecificAHC[3].checked==true)
//   {  
//    
//         var frm = document.getElementById("frmOldTrailbalanceReport");
//        
//         /** For Particular Month. It calls old report */
//         if(document.frmOldTrailbalanceReport.month_year[0].checked==true)
//             {  
//               frm.action = "../../../../../../Old_Data_TrialBalance_Report_New";
//                frm.submit();
//             }
//         /** For All Months . It calls New Report */ 
//        if(document.frmOldTrailbalanceReport.month_year[1].checked==true)
//            {// alert("Singlefin");
//               frm.action = "../../../../../../OldtrailbalanceReport_New";
//               frm.submit();
//            }
//  
//    }
 
}


function LoadAccountingUnitID()
{
	//alert("month")
    var CashbookYear=document.frmOldTrailbalanceReport.txtCB_Year.value;
   // alert(CashbookYear);
    var CashbookMonth=document.frmOldTrailbalanceReport.txtCB_Month.value;
     
       var url="../../../../../../Old_Data_TrialBalance_Report_New?Command=loadunit&txtCB_Year="+CashbookYear+"&txtCB_Month="+CashbookMonth;               
     // alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handle_loadAccountingUnitID(req);
        }        
        req.send(null);
        if(CashbookMonth==3){
    var opId=document.getElementById("more_supp");
           // alert("opId"+opId);
            opId.style.display="block";
            }
}
function LoadAccountingUnitIDfin()
{
    var txtCB_Year_from=document.frmOldTrailbalanceReport.txtCB_Year_from.value;
   // alert(CashbookYear);
    var txtCB_Year_to=document.frmOldTrailbalanceReport.myselect.value;
     
       var url="../../../../../../OldtrailbalanceReport_New?Command=loadunit&txtCB_Year_from="+txtCB_Year_from+"&txtCB_Year_to="+txtCB_Year_to;               
//      alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          handle_loadAccountingUnitfin(req);
        }        
        req.send(null);
  /*      if(CashbookMonth==3){
    var opId=document.getElementById("more_supp");
           // alert("opId"+opId);
            opId.style.display="block";
            }*/
}



function handle_loadAccountingUnitID(req)
{
   
    if(req.readyState==4)
    {
   
     if(req.status==200)
     {
   
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
      //  alert("test >>> "+baseresponse);
       // alert(req.responseText);
        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
                cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                
                option.text=accounting_unit_name;
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
          
        }
       else if(flag=="nodata")
        {
           alert("Data does not exists for this Month and Year");
          }
        else if(flag=="failure"){
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}
}
function handle_loadAccountingUnitfin(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode2");         
                cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                
                option.text=accounting_unit_name;
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
          
        }
       else if(flag=="nodata")
        {
           alert("Data does not exists for this Month and Year");
          }
        else if(flag=="failure"){
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}
}
function loadsupp(){
	//alert("inside");
var flagid=document.getElementById("suppmnt").value;
//alert(flagid);

var txtCB_Month=document.getElementById("txtCB_Month").value;

if(txtCB_Month==3 && flagid!=0)
   {
            var opId=document.getElementById("more_supp");
           // alert("opId"+opId);
            opId.style.display="block";
             var txtCB_Year=document.getElementById("txtCB_Year").value;
    
             var txtCB_Month=document.getElementById("txtCB_Month").value;
            
         var frm = document.getElementById("frmOldTrailbalanceReport");
           {  
               frm.action = "../../../../../../Old_Data_TrialBalance_Supplment?Command=loadsupp&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&flagid="+flagid;
//               alert(frm.action);
                frm.submit();
             }
   }

}

function loadsupp2()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode2").value;
	//alert(cmbAcc_UnitCode);
	var frm = document.getElementById("frmOldTrailbalanceReport");
	if(cmbAcc_UnitCode!=null){ 
		alert("test acc no.... OldtrailbalanceReport_unitwise");
		frm.action = "../../../../../../OldtrailbalanceReport_unitwise";
		frm.submit();
	}
	else {
		alert("test acc no.... OldtrailbalanceReport_New ");
		frm.action = "../../../../../../OldtrailbalanceReport_New";
		frm.submit();  
	}
}
/*var flagid=document.getElementById("suppmnt").value;
alert(flagid);

var txtCB_Month=document.getElementById("txtCB_Month").value;

if(txtCB_Month==3 && flagid!=0)
   {
            var opId=document.getElementById("more_supp");
           // alert("opId"+opId);
            opId.style.display="block";
             var txtCB_Year=document.getElementById("txtCB_Year").value;
    
             var txtCB_Month=document.getElementById("txtCB_Month").value;
            
         var frm = document.getElementById("frmOldTrailbalanceReport");
           {  
               frm.action = "../../../../../../Old_Data_TrialBalance_Supplment?Command=loadsupp&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&flagid="+flagid;               
                frm.submit();
             }
   }*/

