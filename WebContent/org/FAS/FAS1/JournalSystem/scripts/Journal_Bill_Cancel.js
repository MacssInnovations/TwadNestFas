var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
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

function byUnitAndOfficeChange()
{
    doFunction('load_Voucher_No','null');
}
/////////////////////////////////////////////   AccHeadpopup  /////////////////////////////////////////////////////
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
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

function doParentAccHead(code)
{
   document.getElementById("txtAcc_HeadCode").value=code;
   doFunction('checkCode','null');
   return true;
}
window.onunload=function()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
    if (winjob && winjob.open && !winjob.closed) winjob.close();
}


//////////////   FOR DEPUTATION JOB POPUP WINDOW //////////////////////
function jobpopup_master()
{
    job_flag=true;
    jobpopup();
}

function jobpopup_trans()
{
    job_flag=false;
    jobpopup();
}

var winjob;
function jobpopup()
{
    if(winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
}
function forChildOption()
{
      if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,false);
}
function doParentJob(jobid,deptid)
{
       if(deptid=='TWAD')
        {
            doFunction('office',jobid);
        }
        else
        {
                alert('Please select an Office ');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false
        }
   
    return true
}

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;

function servicepopup()
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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.getElementById("txtAuth_By").value=emp;
doFunction('loademp','null');
}
window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
function doFunction(Command,param)
{   
        if(Command=="checkCode")
        {  
            txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
            if(txtAcc_HeadCode.length>=6)
            {
            var url="../../../../../Journal_SL.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
            }         
        }
         else if(Command=="loademp")
        {   //alert(eid);
            var eid=document.getElementById("txtAuth_By").value;
            var offid=document.getElementById("cmbOffice_code").value;
            var url="../../../../../Journal_SL.view?Command=loademp&eid="+eid+"&offid="+offid;
            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                    
        }
       else if(Command=="office")
        {   
            var oid=param;
            var url="../../../../../Journal_SL.view?Command=office&oid="+oid;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
       else if(Command=="loadcheckCode_grid")
        {  
            txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
            if(txtAcc_HeadCode.length>=6)
            {
            var url="../../../../../Journal_SL.view?Command=loadcheckCode_grid&txtAcc_HeadCode="+txtAcc_HeadCode;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
            }         
        }
         else if(Command=="Load_Journal_MasterSL_Code")
        {  
            
            var cmbSL_type=document.getElementById("cmbMas_SL_type").value;             // input from MASTER combo *
            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
           if(cmbSL_type!="")                              // called only not equal to null 
            {
                cmbMas_SL_type=0;              // no need this value that's why it made it as "ZERO", so that u can use same "URL"                                   //document.getElementById("cmbMas_SL_type").value;
                other_dept_off_alias_id=0;                                      //document.getElementById("cmbMas_SL_Code").value;
                var url="../../../../../Journal_SL.view?Command=Load_Journal_MasterSL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+"&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
        else if(Command=="Load_SL_Code")
        {  
            var cmbSL_type=document.getElementById("cmbSL_type").value;
            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            if(cmbSL_type==5 )
              {
              document.getElementById("offlist_div_trans").style.display='block';
              clear_Combo(document.getElementById("cmbSL_Code"));
              alert("USE search ICON to select the office");
              return true;
              }
            else
              document.getElementById("offlist_div_trans").style.display='none';
              
          if(cmbSL_type!="")                              // called only not equal to null
            {
                 cmbMas_SL_type=0;                  //document.getElementById("cmbMas_SL_type").value;
                 other_dept_off_alias_id=0;           //document.getElementById("cmbMas_SL_Code").value;
                 var url="../../../../../Journal_SL.view?Command=Load_SL_Code&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+"&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
        else  if(Command=="Load_SL_Code_grid")
        {  
          var cmbSL_type=common_cmbSL_type;                 // value come from grid...
          cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
          cmbOffice_code=document.getElementById("cmbOffice_code").value;
           
           if(cmbSL_type==5 )
              {
              document.getElementById("offlist_div_trans").style.display='block';
              job_flag=false;
              doFunction('office',common_cmbSL_Code);
              alert("USE search ICON to select the office");
              return true;
              }
            else
              document.getElementById("offlist_div_trans").style.display='none';
              
          if(cmbSL_type!="")                              // called only not equal to null
            {
                 cmbMas_SL_type=0;              //document.getElementById("cmbMas_SL_type").value;
                 other_dept_off_alias_id=0;         //document.getElementById("cmbMas_SL_Code").value;
                 var url="../../../../../Journal_SL.view?Command=Load_SL_Code_grid&cmbSL_type="+cmbSL_type+"&cmbMas_SL_type="+cmbMas_SL_type+"&other_dept_off_alias_id="+other_dept_off_alias_id+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
        else if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmJournal_Bill_Cancel.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../Journal_Bill_Cancel.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            //alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
            }         
        }
        else if(Command=="load_Voucher_Details")
        {  
            clearGeneral_Detail();
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.frmJournal_Bill_Cancel.txtCrea_date.value
            var  txtJournalVou_No=document.getElementById("txtJournalVou_No").value;
            if(txtJournalVou_No!="")
            {
            var url="../../../../../Journal_Bill_Cancel.view?Command=load_Voucher_Details&txtJournalVou_No="+txtJournalVou_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
//            alert("URL------------->"+url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
            }         
        }
}


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{  
    if(req.readyState==4)
//    	alert("req.readyState--------->"+req.readyState);
    {
        if(req.status==200)
//        	alert("req.status--------->"+req.status)
        {  
//            alert("Response--------->"+req.responseText);
        	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
//            alert("BaseResponse------------->"+baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
//           alert("Command------------->"+Command);
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
            else if(Command=="Load_Journal_MasterSL_Code")
            {
                Load_Journal_MasterSL_Code(baseResponse);
            }
            else if(Command=="Load_SL_Code")
            {
                Load_SL_Code(baseResponse);
            }
             else if(Command=="loademp")
            {
                loadEmployee(baseResponse);
            }
            else if(Command=="office")
            {
                loadOffice(baseResponse);
            }
           else if(Command=="loadcheckCode_grid")
            {   
                loadcheckCode_grid(baseResponse);
            }
            else if(Command=="Load_SL_Code_grid")
            {
                Load_SL_Code_grid(baseResponse);
            }
            else if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_Details")
            {
                load_Voucher_Details(baseResponse);
            }
        }
    }
}


function loadEmployee(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  //alert("success");
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
        var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
        
        document.getElementById("txtAuth_By").value=eid;
        document.getElementById("Auth_By").value=ename+" - "+desig;
    }
    else 
    {
        var eid=document.getElementById("txtAuth_By").value;
        alert("Employee Id '"+eid+"' doesn't Exists under this office");
        document.getElementById("txtAuth_By").value="";
        document.getElementById("Auth_By").value="";
    }
}
///////////////////////////////////// loadoffice ///////
function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
      // if(job_flag==true)
        //var cmbSL_Code=document.getElementById("cmbMas_SL_Code");                        // Get the select combo id from master
       if(job_flag==false)
        var cmbSL_Code=document.getElementById("cmbSL_Code");                            // Get the select combo id from transaction
        
            cmbSL_Code.innerHTML="";
            var option=document.createElement("OPTION");
            option.text=oname;
            option.value=oid;
            try
            {
                cmbSL_Code.add(option);
            }catch(errorObject)
            {
                cmbSL_Code.add(option,null);
            }
    }
    else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     alert("Office Id '"+oid+"' doesn't Exist");
    }
    job_flag="";
}

/////////////////////////////////////////////  For MASTER Combo SL Code //////////////////////////////////

function Load_Journal_MasterSL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbSL_type=document.getElementById("cmbMas_SL_type").value;

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
         
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
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   // value assigned to same local variable name
        clear_Combo(cmbSL_Code);
    }
}

/////////////////////////////////////////////   Load_SL_Code() by User /////////////////////////////////////////////////////

function Load_SL_Code(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
var cmbSL_type=document.getElementById("cmbSL_type").value;
    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbSL_Code");
         
         var items_id=new Array();
         var items_name=new Array();

        // if(cmbSL_type=="11" || cmbSL_type=="1" || cmbSL_type=="2" || cmbSL_type=="3" )
         //{
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
          //}  
        
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
    }
    else if(flag=="failure")
    {
        alert("No data found");
         var cmbSL_Code=document.getElementById("cmbSL_Code");
         clear_Combo(cmbSL_Code);
    }
}



/////////////////////////////////////////////   Load_SL_Code() when loadTable calling /////////////////////////////////////////////////////


function Load_SL_Code_grid(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

var cmbSL_type=common_cmbSL_type;           // value  from grid

    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbSL_Code");
         
         var items_id=new Array();
         var items_name=new Array();
         
        // if(cmbSL_type=="11" || cmbSL_type=="1" || cmbSL_type=="2"  || cmbSL_type=="3" || cmbSL_type=="7" )
        // {
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
            }
         // }  
        
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
            //alert("sl_last"+common_cmbSL_Code)
            document.getElementById("cmbSL_Code").value=common_cmbSL_Code;
    }
     else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbSL_Code");
        clear_Combo(cmbSL_Code);
    }
  
}

/////////////////////////////////////////////   loadcheckCode() by User /////////////////////////////////////////////////////
function loadcheckCode(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
      var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
      document.getElementById("txtAcc_HeadCode").value=hid;
       var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
        var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
       var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
       document.getElementById("txtAcc_HeadCode").value=hid;
       document.getElementById("txtAcc_HeadDesc").value=hdesc;
       if(BalType=="CR")
             { document.frmJournal_Bill_Cancel.rad_sub_CR_DR[0].checked=true;}
       else if(BalType=="DR")
       {
            document.frmJournal_Bill_Cancel.rad_sub_CR_DR[1].checked=true;
            }
       var cmbSL_type=document.getElementById("cmbSL_type");   
       
       if(SL_YN=="Y")
       {
        
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
            var cmbSL_Code=document.getElementById("cmbSL_Code");   
            clear_Combo(cmbSL_Code);
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
}
/////////////////////////////////////////////   loadcheckCode_grid() when loadTable calling /////////////////////////////////////////////////////

function loadcheckCode_grid(baseResponse)
{
 doFunction('Load_SL_Code_grid','null');                // calling next combo values

var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {                    
    var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
      document.getElementById("txtAcc_HeadCode").value=hid;
       var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
        var BalType=baseResponse.getElementsByTagName("BalType")[0].firstChild.nodeValue;
       var SL_YN =baseResponse.getElementsByTagName("SL_YN")[0].firstChild.nodeValue;
       document.getElementById("txtAcc_HeadCode").value=hid;
       document.getElementById("txtAcc_HeadDesc").value=hdesc;
       /*if(BalType=="CR")                                                      // It is checked in load_table function itself
             { document.frmJournal_Bill_Cancel.rad_sub_CR_DR[0].checked=true;}
       else if(BalType=="DR")
       {
            document.frmJournal_Bill_Cancel.rad_sub_CR_DR[1].checked=true;
            }*/
       var cmbSL_type=document.getElementById("cmbSL_type");   
       if(SL_YN=="Y")
       {
        
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
           document.getElementById("cmbSL_type").value=common_cmbSL_type;   //set from grid
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
                var cmbSL_Code=document.getElementById("cmbSL_Code"); 
                clear_Combo(cmbSL_Code);
            }
            //alert("set type"+common_cmbSL_type);
       
    }
    else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
   
}
/////////////////////////////////////////////   load Table() /////////////////////////////////////////////////////

function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
       // document.FasAcc_Headform.cmdadd.disabled=true;
       //document.getElementById("txtAcc_HeadCode").readOnly=true;                // do not change the Account Head 
       //text_field.readOnly=true;
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
        try{common_cmbSL_type=rcells.item(3).firstChild.value;} catch(e){common_cmbSL_type=""}
        //alert("U"+common_cmbSL_type+"U")
        try{common_cmbSL_Code=rcells.item(4).firstChild.value;} catch(e){common_cmbSL_Code=""} 
                doFunction('loadcheckCode_grid','null');
        
         if(rcells.item(2).firstChild.value=="CR")
         document.frmJournal_Bill_Cancel.rad_sub_CR_DR[0].checked=true;
         else if(rcells.item(2).firstChild.value=="DR")
         document.frmJournal_Bill_Cancel.rad_sub_CR_DR[1].checked=true;
         
       //try{document.getElementById("txtsub_Recei_from").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_NO").value=rcells.item(5).firstChild.value;}catch(e){}
       try{document.getElementById("txtBill_date").value=rcells.item(6).firstChild.value;}catch(e){}
       
        try{document.getElementById("txtBill_type").value=rcells.item(7).firstChild.value;}catch(e){}
       
        var nex=rcells.item(7).firstChild.nextSibling  
        try{document.getElementById("txtAgree_No").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtAgree_Date").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtsub_Amount").value=nex.value;}catch(e){}
        nex=nex.nextSibling;
        try{document.getElementById("txtParticular").value=nex.value;}catch(e){}
       
       
    document.frmJournal_Bill_Cancel.cmdupdate.style.display='block';
    document.frmJournal_Bill_Cancel.cmddelete.disabled=false;
    document.frmJournal_Bill_Cancel.cmdadd.style.display='none';
}


function call_clr()
{
    
 document.getElementById("txtJournalVou_No").value="";  
 clearGeneral_Detail();
}
function clearGeneral_Detail()
{ 
   // document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("cmbMas_SL_type").value="";
    document.getElementById("cmbMas_SL_Code").value="";
    //document.getElementById("txtAuth_By").value="";
    //document.getElementById("Auth_By").value="";
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

function checkNull_cancel()
{

var tbody=document.getElementById("grid_body");
   //alert("tbody.rows.length :"+tbody.rows.length);   
   if(window.confirm('Do you want to Cancel?'))
    {
            if(document.getElementById("cmbAcc_UnitCode").value=="")
            {
                alert("Select the Account Unit code");
                //document.getElementById("txtAcc_HeadDesc").focus();
                return false;    
            }
            if(document.getElementById("cmbOffice_code").value=="")
            {
                alert("Select the Office Code");
                //document.getElementById("cmbOffice_code").focus();
                return false;
            }
            if(document.getElementById("txtCrea_date").value.length==0)
            {
                alert("Enter the Date of Creation");
                //document.getElementById("txtCrea_date").focus();
                return false;    
            }
            if(document.getElementById("txtJournalVou_No").value.length==0)
            {
                alert("Select Voucher Number");
                //document.getElementById("txtJournalVou_No").focus();
                return false;
            }
           /*
           if(document.getElementById("txtAuth_By").value.length==0)
            {
                alert("Enter Name of the Authorized person under Modification Details");
                //document.getElementById("txtReferNO_edit").focus();
                return false;    
            }
           */
            return true;
    }
    else
      return false;
}


/*function enable_cheque(Jr_type)           // only for general Journal
{
    if(Jr_type==6 || Jr_type==7)
        document.getElementById("CHD").style.display='block';
    else
    {
        
        document.getElementById("txtBill_NO").value="";
        document.getElementById("txtBill_date").value="";
        document.getElementById("CHD").style.display='none';
    }
}
*/
function clear_Combo(combo)
{
        //alert(combo.id)
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

//function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
//{
//    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
//    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
//    {
//             call_clr();
//             doFunction('load_Voucher_No','null');
//    }
//}

function call_date(dateCtrl)                        // TB_checking not needed
{
    call_clr();
    if(checkdt(dateCtrl))
    {
        doFunction('load_Voucher_No','null');
    }
    else
    {
        var cmbSL_Code=document.getElementById("txtJournalVou_No");   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="-- Select Voucher Number --";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
    }
}

function load_Voucher_No(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 var txtJournalVou_No=document.getElementById("txtJournalVou_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
            
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtJournalVou_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtJournalVou_No.add(option);
            }catch(errorObject)
            {
                txtJournalVou_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtJournalVou_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtJournalVou_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtJournalVou_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtJournalVou_No.add(option);
            }catch(errorObject)
            {
                txtJournalVou_No.add(option,null);
            }
         alert("No Receipt Found");
    }
}

////////////////////

function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert("FF");
    if(flag=="success")
    {
       //var cheq_No=baseResponse.getElementsByTagName("cheq_No")[0].firstChild.nodeValue;         // here i assigned 
       //var cheq_Date=baseResponse.getElementsByTagName("cheq_Date")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
      
       var sltype=new Array(13);
       sltype[1]="Supplier";
       sltype[11]="Contractors";
       sltype[9]="Other departments";
       sltype[2]="Firms";
       
       if(Mas_SL_type!=0)
       {
           for(i=1;i<=13;i++)
           {
               if(Mas_SL_type==i)
               {
               document.getElementById("cmbMas_SL_type").value=sltype[i];
               break;
               }
           }
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var Mas_SLCODE=baseResponse.getElementsByTagName("cid");
            
            for(var k=0;k<Mas_SLCODE.length;k++)
            {
                items_SLcode[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
                if(items_SLcode[k]==Mas_SL_code)
                {
                 document.getElementById("cmbMas_SL_Code").value=items_SLdesc[k];
                 break;
                }
            }
       }
       
         
       //document.getElementById("txtAmount").value=Total_amt;
      /* if(Rec_From!="null")
      document.getElementById("txtRecei_from").value=Rec_From;
      else
      document.getElementById("txtRecei_from").value="";*/
      
      
       if(Remak!="null")
         document.getElementById("txtRemarks").value=Remak;
        else
        document.getElementById("txtRemarks").value="";
       
       //var miHC =baseResponse.getElementsByTagName("miHC")[0].firstChild.nodeValue;
       
       var tbody=document.getElementById("grid_body");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                        
         //var SLCODE=baseResponse.getElementsByTagName("SLCODE");
        
         var AHcode=baseResponse.getElementsByTagName("AHcode");
        
        var items=new Array();
        for(var k=0;k<AHcode.length;k++)
        {
        items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;   
        items[1]=baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;   
         items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue;
         items[3]=baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue;
         if(items[3]==0)
         items[3]="";
         
        items[4]=baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue;
        if(items[4]=="null")
        items[4]="";
        
        items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
        if(items[5]==0)
        items[5]="";
        
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
        if(items[6]=="null")
        items[6]="";
        //items[7]=baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild.nodeValue;
        //if(items[7]=="null")
        //items[7]="";
       
        //items[7]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue;
        
        if(baseResponse.getElementsByTagName("Bill_NO")[k].firstChild == null){
			        items[7]="";
		}else{
			        items[7]=baseResponse.getElementsByTagName("Bill_NO")[k].firstChild.nodeValue;
		}
		
        items[8]=baseResponse.getElementsByTagName("Bill_date")[k].firstChild.nodeValue;
     //   items[9]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
        
         if(baseResponse.getElementsByTagName("Bill_type")[k].firstChild == null){
			        items[9]="";
		}else{
			        items[9]=baseResponse.getElementsByTagName("Bill_type")[k].firstChild.nodeValue;
		}
        
        
        
          if(baseResponse.getElementsByTagName("Agree_No")[k].firstChild == null){
			        items[10]="";
		}else{
			        items[10]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
			}
        
        
       // items[10]=baseResponse.getElementsByTagName("Agree_No")[k].firstChild.nodeValue;
        items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        
        items[12]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        items[13]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        
       
       if(items[7]=="null")
        items[7]="";
         if(items[8]=="null")
        items[8]="";
         if(items[9]=="null")
        items[9]="";
         if(items[10]=="null")
        items[10]="";
         if(items[11]=="null")
        items[11]="";
        if(items[13]=="null")
        items[13]="";
        
         tbody=document.getElementById("grid_body");
       var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
        
        var cell2;
        
       
            cell2=document.createElement("TD");
           
                
                  var currentText=document.createTextNode(items[0]+"-"+items[1]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
             cell2=document.createElement("TD"); 
                 
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
                
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
              
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
           cell2=document.createElement("TD");
                 
                   var currentText=document.createTextNode(items[7]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
            
                   var currentText=document.createTextNode(items[8]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                
             cell2=document.createElement("TD"); 
              
                  var currentText=document.createTextNode(items[12]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
        }
    }
    else if(flag=="failure")
     alert("Failed to load data");
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
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
                 //doFunction('load_Receipt_No','null');                 //return true;
            	call_clr();
            	doFunction('load_Voucher_No','null');
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    //document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";     
               }
             else if(flag=="finyearLJVN")
             {
                        // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                  dateCtrl.value="";
                  alert("Cash Book Control Not Found for Journal");//return false;//
                  dateCtrl.focus();
                 // document.getElementById("txtReceipt_No").value="";     
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


