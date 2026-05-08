/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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



/////////////////////////////////////////////   AccHeadpopup  /////////////////////////////////////////////////////
var winAccHeadCode;
var winListAllSchedule;
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
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

//This is ListAll Budget Coding//////

function ListAllBudget()
{
    if (winListAllSchedule && winListAllSchedule.open && !winListAllSchedule.closed) 
    {
       winListAllSchedule.resizeTo(500,500);
       winListAllSchedule.moveTo(250,250); 
       winListAllSchedule.focus();
    }
    else
    {
        winListAllSchedule=null
    }
        
    winListAllSchedule= window.open("../../../../../org/FAS/FAS1/Masters/jsps/ListofScheduleMaster.jsp","ListAllSchedule","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winListAllSchedule.moveTo(250,250);  
    winListAllSchedule.focus();
    
}


function doParentAccHead(code)
{
   document.frmScheduleMaster.txtaccountheadcode.value=code;
   doFunction('checkCode','null');
   return true;
}

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winListAllSchedule && winListAllSchedule.open && !winListAllSchedule.closed) winListAllSchedule.close();

}


//CallServer Function
function doFunction1(Command,param)
{   
	
    var url="";
    if(Command=="checkCode")
    {
        var headcode=document.frmjournal_Integration.txtaccountheadcode.value;
        
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
function doFunction(Command,param)
{   
	
    var url="";
    if(Command=="checkCode")
    {
        var headcode=document.frmScheduleMaster.txtaccountheadcode.value;
        
        url="../../../../../BudgetMasterServlet.con?Command=HeadCode&txtAcc_Head_code="+headcode;
        var req=getTransport();
        req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse(req);
            }   
                    req.send(null);
    }
    
    if(Command=="Add")
    {
        var txtaccountheadcode=document.frmScheduleMaster.txtaccountheadcode.value;
        var txtScheduleId=document.frmScheduleMaster.txtScheduleId.value.toUpperCase();
        /*if(document.frmScheduleMaster.radCrDrInd[0].checked==true)
        {
            radCrDrInd="CR";
        }
        else if(document.frmScheduleMaster.radCrDrInd[1].checked==true)
        {
            radCrDrInd="DR";
        }*/
        
        var flag=nullcheck();
        
        if(flag==true)
        {
        url="../../../../../ScheduleMasterServlet.con?Command=Add&&txtAcc_Head_code="+txtaccountheadcode+"&txtScheduleId="+txtScheduleId;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           AddRecordResponse(req);
        }   
        
        req.send(null);
        }  
        
    }
    if(Command=="Update")
    {
        var txtaccountheadcode=document.frmScheduleMaster.txtaccountheadcode.value;
        document.frmScheduleMaster.txtScheduleId.disabled=false;
        var txtScheduleId=document.frmScheduleMaster.txtScheduleId.value.toUpperCase();
        
        /*if(document.frmScheduleMaster.radCrDrInd[0].checked==true)
        {
            radCrDrInd="CR";
        }
        else if(document.frmScheduleMaster.radCrDrInd[1].checked==true)
        {
            radCrDrInd="DR";
        }*/
        var flag=nullcheck();
        
        if(flag==true)
        {
        url="../../../../../ScheduleMasterServlet.con?Command=Update&&txtAcc_Head_code="+txtaccountheadcode+"&txtScheduleId="+txtScheduleId;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           UpdateResponse(req);
        }   
        
        req.send(null);
        }  
        
    }
    if(Command=="Delete")
    {
            var txtaccountheadcode=document.frmScheduleMaster.txtaccountheadcode.value;
            document.frmScheduleMaster.txtScheduleId.disabled=false;
        var txtScheduleId=document.frmScheduleMaster.txtScheduleId.value.toUpperCase();
        /*if(document.frmScheduleMaster.radCrDrInd[0].checked==true)
        {
            radCrDrInd="CR";
        }
        else if(document.frmScheduleMaster.radCrDrInd[1].checked==true)
        {
            radCrDrInd="DR";
        }*/
        url="../../../../../ScheduleMasterServlet.con?Command=Delete&&txtAcc_Head_code="+txtaccountheadcode+"&txtScheduleId="+txtScheduleId;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           DeleteRecordResponse(req);
        }   
        req.send(null);
        //document.frmScheduleMaster.txtScheduleId.disabled=true;
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
                document.getElementById("txtaccountheadname").value="";
                document.getElementById("txtaccountheadname").value=headname;
            }
            else
            {
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmScheduleMaster.txtaccountheadcode.focus();
                
            }
        }
        
    }

}

function AddRecordResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
                alert("Record Inserted Successfully");
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                document.frmScheduleMaster.txtScheduleId.value="";
                
                //document.frmScheduleMaster.radCrDrInd[0].checked=true;
                
            }
            else if(flag=="failure")
            {
                alert("Record Not Inserted Successfully");
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                document.frmScheduleMaster.txtScheduleId.value="";
                
                //document.frmScheduleMaster.radCrDrInd[0].checked=true;
                
            }
            if(flag=="AlreadyExist")
            {
                alert("Record Already Exist");
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                document.frmScheduleMaster.txtScheduleId.value="";
                
                //document.frmScheduleMaster.radCrDrInd[0].checked=true;
            }
            
        }
    }
}

function UpdateResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Updated Successfully");
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                document.frmScheduleMaster.txtScheduleId.value="";
                
                //document.frmScheduleMaster.radCrDrInd[0].checked=true;
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Updated");
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                document.frmScheduleMaster.txtScheduleId.value="";
                
                //document.frmScheduleMaster.radCrDrInd[0].checked=true;
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
        }
    }
}

function DeleteRecordResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                alert("Record Deleted Successfully");
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                document.frmScheduleMaster.txtScheduleId.value="";
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
            else
            {
                alert("Record Not Deleted");
                document.frmScheduleMaster.txtaccountheadcode.value="";
                document.frmScheduleMaster.txtaccountheadname.value="";
                document.frmScheduleMaster.txtScheduleId.value="";
                var d=document.getElementById("cmdUpdate");
                d.style.display="none";
                
                var d2=document.getElementById("cmdDelete");
                d2.style.display="none";
                
                var d1=document.getElementById("cmdAdd");
                d1.style.display="block";
            }
        }
    }
}

//********************************** Numbers Only Checking *****************************//

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

function ClearAll()
    {
        //document.firmsForm.cmbAcc_UnitCode.value="";
        //document.firmsForm.comOffId.selectedIndex=0;
        
        document.frmScheduleMaster.txtaccountheadcode.value="";
        document.frmScheduleMaster.txtaccountheadname.value="";
        document.frmScheduleMaster.txtScheduleId.value="";
        document.frmScheduleMaster.txtScheduleId.disabled=false;
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
    
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
        
    }
    
    
function nullcheck()
{
  /*  if((document.frmScheduleMaster.cmbAcc_UnitCode.value=="")||(document.frmScheduleMaster.cmbAcc_UnitCode.value.length<=0)||(document.frmScheduleMaster.cmbAcc_UnitCode.value=="0"))
    {
        alert("Select Accounting Unit Id");
        document.frmScheduleMaster.cmbAcc_UnitCode.focus();
        return false;
    }
    
    if((document.frmScheduleMaster.cmbOffice_code.value=="") || (document.frmScheduleMaster.cmbOffice_code.value.length<=0) || (document.frmScheduleMaster.cmbOffice_code.value=="0"))
    {
        alert("Select Office Code");
        document.frmScheduleMaster.cmbOffice_code.focus();
        return false;
    
    }
    if((document.frmScheduleMaster.cmbFinancialYear.value=="") || (document.frmScheduleMaster.cmbFinancialYear.value.lengt<=0) || (document.frmScheduleMaster.cmbFinancialYear.value=="0"))
    {
        alert("Select Financial Year");
        documnet.frmScheduleMaster.cmbFinancialYear.focus();
        return false;
    }*/
    
    if((document.frmScheduleMaster.txtaccountheadcode.value=="") || (document.frmScheduleMaster.txtaccountheadcode.value.length<=0))
    {
        alert("Please Select or Enter Account Head Code");
        document.frmScheduleMaster.txtaccountheadcode.focus();
        return false;
    }
    if((document.frmScheduleMaster.txtScheduleId.value=="") || (document.frmScheduleMaster.txtScheduleId.value.length<=0))
    {
        alert("Please Enter Schedule Id");
        document.frmScheduleMaster.txtScheduleId.focus();
        return false;
    }
    return true;
}

function List(acchead,schedule)
{

    document.frmScheduleMaster.txtaccountheadcode.value=acchead;
    doFunction('checkCode','null');
    document.frmScheduleMaster.txtScheduleId.value=schedule;
    
    document.frmScheduleMaster.txtScheduleId.disabled=true;
    
    /*if(ind=="CR")
    {
    document.frmScheduleMaster.radCrDrInd[0].checked=true;
    }
    else
    {
    document.frmScheduleMaster.radCrDrInd[1].checked=true;
    }*/
    var d=document.getElementById("cmdUpdate");
    d.style.display="block";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="block";
    
    var d1=document.getElementById("cmdAdd");
    d1.style.display="none";
}

function key(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
    
    if(unicode==32 || unicode==43 || unicode==45 || unicode==61 || unicode==92)
    {
        return false;
    }
    else
    {
    return true;
    }
    
}