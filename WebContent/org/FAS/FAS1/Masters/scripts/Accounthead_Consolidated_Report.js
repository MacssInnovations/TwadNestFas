////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
//alert("here js");
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
//alert("accounthead");
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
var window_accHeadList;
function doParentAccHead(code)
{
   document.frmAccount_Head_Consd_Report.txtaccountheadcode.value=code;
   doFunction('checkCode','null');
   return true;
}

window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winListAllSchedule && winListAllSchedule.open && !winListAllSchedule.closed) winListAllSchedule.close();

}


//CallServer Function

function doFunction(Command,param)
{   
    var url="";
    if(Command=="checkCode")
    {
        var headcode=document.frmAccount_Head_Consd_Report.txtaccountheadcode.value;
        
        url="../../../../../BudgetMasterServlet.con?Command=HeadCode&txtAcc_Head_code="+headcode;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse(req);
            }   
                    req.send(null);
    }
    if(Command=="Load_Group_Code")
    {  
    	//alert("inside Load_Group_Code");
        var cmbsecid=document.getElementById("txtSectionId").value;             // input from MASTER combo *
        
          
       if(cmbsecid!="")                              // called only not equal to null and 5 is for office
        {
            
            var url="../../../../../Accounthead_Consolidated_Report?Command=Load_Group_Code&txtSectionId="+cmbsecid; 
        //    alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               
               handleResponse(req,param);
            }   
                    req.send(null);
        }
        else if(cmbsecid=="")
           clear_Combo(document.getElementById("txtGroupId")); 
    }
    if(Command=="Add")
    {
        var txtaccountheadcode=document.frmAccount_Head_Consd_Report.txtaccountheadcode.value;
        var txtSectionId=document.frmAccount_Head_Consd_Report.txtSectionId.value;
        var txtGroupId=document.frmAccount_Head_Consd_Report.txtGroupId.value;
       
        
        var flag=nullcheck();
        
        if(flag==true)
        {
        url="../../../../../Accounthead_Consolidated_Report?Command=Add&&txtaccountheadcode="+txtaccountheadcode+"&txtSectionId="+txtSectionId+"&txtGroupId="+txtGroupId;
        //alert(url);
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
        var txtaccountheadcode=document.frmAccount_Head_Consd_Report.txtaccountheadcode.value;
     
        var txtSectionId=document.frmAccount_Head_Consd_Report.txtSectionId.value;
        var txtGroupId=document.frmAccount_Head_Consd_Report.txtGroupId.value;
        var flag=nullcheck();
        
        if(flag==true)
        {
        url="../../../../../Accounthead_Consolidated_Report?Command=Update&&txtaccountheadcode="+txtaccountheadcode+"&txtSectionId="+txtSectionId+"&txtGroupId="+txtGroupId;
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
            var txtaccountheadcode=document.frmAccount_Head_Consd_Report.txtaccountheadcode.value;
           // document.frmAccount_Head_Consd_Report.cmbSectionId.disabled=false;
        var txtSectionId=document.frmAccount_Head_Consd_Report.txtSectionId.value;
        var txtGroupId=document.frmAccount_Head_Consd_Report.txtGroupId.value;
        url="../../../../../Accounthead_Consolidated_Report?Command=Delete&&txtaccountheadcode="+txtaccountheadcode+"&txtSectionId="+txtSectionId+"&txtGroupId="+txtGroupId;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           DeleteRecordResponse(req);
        }   
        req.send(null);
        //document.frmAccount_Head_Consd_Report.cmbSectionId.disabled=true;
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
                document.frmAccount_Head_Consd_Report.txtaccountheadname.value="";
                document.frmAccount_Head_Consd_Report.txtaccountheadname.value=headname;
            }
            else
            {
                document.frmAccount_Head_Consd_Report.txtaccountheadcode.value="";
                document.frmAccount_Head_Consd_Report.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmAccount_Head_Consd_Report.txtaccountheadcode.focus();
                
            }
        }
        
    }

}

function nullcheck()
{
    if(document.getElementById("txtaccountheadcode").value.length==0)
    {
        alert("Enter the A/c head code");
        document.getElementById("txtaccountheadcode").focus();
        return false;
    }
    if(document.getElementById("txtSectionId").value=="")
    {
        alert("Select Section Name");
        document.getElementById("txtSectionId").focus();
        return false;
    }
   
    return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
function sixdigit()
{
 if( document.getElementById("txtaccountheadcode").value!=0)
    {
        if(( document.getElementById("txtaccountheadcode").value).length<6)
        {
            alert("Account Head Code Shouldn't be less than 6 digit number");
            document.getElementById("txtAcc_HeadCode").focus();
            return false;
        }
        
    }
}

///////////////////////////////////////  Numbers only fields
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false 
    }
 } 
 
/////////////////////// exit  function

function exit()
{
       self.close();
}
var listPopupwindow;
var window_BankAccNumber;
function ListAll()
    {
    //alert("inside listall");
     if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
    {
       window_BankAccNumber.resizeTo(500,500);
       window_BankAccNumber.moveTo(250,250); 
       window_BankAccNumber.focus();
    }
    else
    {
        window_BankAccNumber=null
    }
         
        var txtSectionId=document.getElementById("txtSectionId").value;
       // alert("txtSectionId"+txtSectionId)
        var groupid=document.getElementById("txtGroupId").value;
      //  alert("groupid"+groupid);
       // var officeid=document.frmAccount_Head_Consd_Report.txtOfficeId.value;
        window_BankAccNumber= window.open("ListofAHMaster.jsp?txtSectionId="+txtSectionId+"&groupid="+groupid,"mywindow1","resizable=YES, scrollbars=yes"); 
        // window_BankAccNumber= window.open("ListofAHMaster.jsp?txtSectionId="+txtSectionId+"&groupid="+groupid","mywindow1","resizable=YES, scrollbars=yes" ); 
         window_BankAccNumber.moveTo(250,250);  
         window_BankAccNumber.focus();
        // Addfund()
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}

function doParentSection(secid,accnt_head_code,Accnt_head,sect_name,grpid,grp_name)
{
var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";          
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
 document.frmAccount_Head_Consd_Report.txtaccountheadcode.value=accnt_head_code;
 document.frmAccount_Head_Consd_Report.txtaccountheadname.value=Accnt_head;
 
 document.frmAccount_Head_Consd_Report.txtSectionId.value=secid;
 document.frmAccount_Head_Consd_Report.txtGroupId.value=grpid
}

function ClearAll()
{
    //document.getElementById("cmbAcc_UnitCode").value;
    document.getElementById("txtSectionId").value="";
    document.getElementById("txtaccountheadcode").value="";
    document.getElementById("txtaccountheadname").value="";
  
  //  document.getElementById("txtGroup").value="";
   
   
    
   
  
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
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
                //document.getElementById("txtSectionId").value="";
    document.getElementById("txtaccountheadcode").value="";
    document.getElementById("txtaccountheadname").value="";
  
                
            }
            else if(flag=="failure")
            {
                alert("Record Not Inserted Successfully");
                //document.getElementById("txtSectionId").value="";
    document.getElementById("txtaccountheadcode").value="";
    document.getElementById("txtaccountheadname").value="";
  
                
            }
            if(flag=="AlreadyExist")
            {
                alert("Record Already Exist");
                //document.getElementById("txtSectionId").value="";
    document.getElementById("txtaccountheadcode").value="";
    document.getElementById("txtaccountheadname").value="";
  
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
               // document.getElementById("txtSectionId").value="";
             document.getElementById("txtaccountheadcode").value="";
              document.getElementById("txtaccountheadname").value="";
  
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
             //  document.getElementById("txtSectionId").value="";
    document.getElementById("txtaccountheadcode").value="";
    document.getElementById("txtaccountheadname").value="";
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
           //    document.getElementById("txtSectionId").value="";
              document.getElementById("txtaccountheadcode").value="";
              document.getElementById("txtaccountheadname").value="";
  
      //        document.getElementById("txtGroup").value="";
   
   
    
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
     ///           document.getElementById("txtSectionId").value="";
    document.getElementById("txtaccountheadcode").value="";
    document.getElementById("txtaccountheadname").value="";
  
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
function handleResponse(req,param)
{    
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
  
            if(Command=="Load_Group_Code")
            {
                Load_Group_Code(baseResponse,param);
            }
        }
    }
}
function clear_Combo(combo)
{
        //alert(combo.id)
        var cmbgroup_Code=document.getElementById(combo.id);   
        cmbgroup_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Group--";
        option.value="";
        try
        {
        	cmbgroup_Code.add(option);
        }catch(errorObject)
        {
        	cmbgroup_Code.add(option,null);
        }
}
function Load_Group_Code(baseResponse,param)
{
//alert('inside function'+param)
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbsecid=document.getElementById("txtSectionId").value;
//alert(flag);
    if(flag=="success")
    {
         var cmbgroup_Code=document.getElementById("txtGroupId");      // value assigned to same local variable name
        
         var items_id=new Array();
         var items_name=new Array();
         //alert("sl_type"+cmbSL_type);
         //if(cmbSL_type=="11" || cmbSL_type=="1" || cmbSL_type=="2"  || cmbSL_type=="3" || cmbSL_type=="7" )
         //{
            var cid=baseResponse.getElementsByTagName("cid");
        //    alert(cid);
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
               // alert("items_id[k]"+items_id[k]);
               // alert("items_name[k]"+items_name[k]);
            }
          //}  
           clear_Combo(cmbgroup_Code);
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k];
                  option.value=items_id[k];
                   try
                  {
                      cmbgroup_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbgroup_Code.add(option,null);
                  }
            }
            //alert('before this')
            if(param!='null')
              {
              //alert('not null')
             document.getElementById("txtGroupId").value=param;
             }
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbgroup_Code=document.getElementById("txtGroupId");   // value assigned to same local variable name
        clear_Combo(cmbgroup_Code);
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