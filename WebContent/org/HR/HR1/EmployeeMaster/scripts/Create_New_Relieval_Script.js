//alert("comes here");
var windesig;
var maxfromdate;
var maxsession;
function desigpopup()
{
    if (windesig && windesig.open && !windesig.closed) 
    {
       windesig.resizeTo(500,500);
       windesig.moveTo(250,250); 
       windesig.focus();
    }
    else
    {
        windesig=null
    }
        
    windesig= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/Reliv_find_Desig.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    windesig.moveTo(250,250);  
    windesig.focus();
    
}

function doParentDesig(des,desname)
{
document.EmpRelieval.txtP_desigId.value=des;
document.EmpRelieval.txtP_DesigName.value=desname;
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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpRelievalPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.EmpRelieval.txtEmployeeid.value=emp;
doFunction('loademp','null');
}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (windesig && windesig.open && !windesig.closed) windesig.close();
}

///////////////////////////////////////////////////////////////////////////////////


//////////////   FOR DEPUTATION JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
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

function jobpopup_trn()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP_TRN.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}


function forChildOption()
{
    if(document.EmpRelieval.cmbReason.value=='DPN')
    {
      if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(false,false,false,true);
     }
     else
     {
     if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,true);
     }
     
}

function checkdatemax()
{
//alert('check');
     if(maxfromdate==null || maxfromdate=='empty' || maxfromdate=='null' )
     {
            return true;
     }
     else
     {
        var fromdt=maxfromdate;
        var todt=document.EmpRelieval.txtDORelieval.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            alert('Relieval Date Should be greater than or equal to '+maxfromdate);
            //document.EmpRelieval.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Relieval Date Should be greater than  or equal to  '+maxfromdate);
                    //document.EmpRelieval.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Relieval Date Should be greater than  or equal to  '+maxfromdate);
                           // document.EmpRelieval.txtDateTo.focus();
                            return false;
                        }
                         else (fday==tday)
                        {
                                var radvalue;
                                   if(document.EmpRelieval.rad_DORelieval[0].checked==true)
                                   {
                                            radvalue=document.EmpRelieval.rad_DORelieval[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.EmpRelieval.rad_DORelieval[1].value;
                                   }
                                   if(maxsession =='AN')
                                   {
                                        if(radvalue=='FN')
                                        {
                                            alert('Given Relieval Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                        
                        }
                        
                }
        }
    }
        return true;

}

function doParentJob(jobid,deptid)
{
//alert(deptid);

    if(document.EmpRelieval.cmbReason.value=='DPN')
    {
        if(deptid!='TWAD')
        {
            document.EmpRelieval.txtD_ODep_OffId.value=jobid;
            document.EmpRelieval.txtD_ODep_Id.value=deptid;
            document.EmpRelieval.txtDept_Id.value=deptid;
             doFunction('dept','null');
        //callServer1('Load','null');
        }
        else
        {
                alert('Please select a Other Office');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false
        }
    }
    else if(document.EmpRelieval.cmbReason.value=='DVN')
    {
        document.EmpRelieval.txtDv_OffId.value=jobid;
        doFunction('office',jobid);
       
    }
    else if(document.EmpRelieval.cmbReason.value=='PRO')
    {
      /*
        document.EmpRelieval.txtP_OffId.value=jobid;
        doFunction('office',jobid);*/
        
        if(deptid!='TWAD')
        {
            document.EmpRelieval.txtP_OffId.value=jobid;
            //document.EmpRelieval.txtP_OffName.value=deptid;
            document.EmpRelieval.txtP_depid.value=deptid;
            //alert('txtP_depid.value'+document.EmpRelieval.txtP_depid.value);
             doFunction('prodep','null');
        //callServer1('Load','null');
        }
        else
        {
          document.EmpRelieval.txtP_OffId.value=jobid;
          //document.EmpRelieval.txtP_depid.value='TWAD';
          doFunction('office',jobid);
        }
    }
    else if(document.EmpRelieval.cmbReason.value=='TRN')
    {
        document.EmpRelieval.txtT_OffId.value=jobid;
        doFunction('office',jobid);
       
    }
    return true

}


////////////////////////////////////////////////////////  Relieval Details  //////////////////////////
var eid;
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
function checkNull()
{
    if(document.EmpRelieval.txtOffId.value.length==0)
    {
        alert("Office Id Missing");
        return false;
    }
     if( document.EmpRelieval.txtEmployeeid.value.length==0)
    {
        alert("Enter the Employee Id ");
        document.EmpRelieval.txtEmployeeid.focus();
        return false;
    }
     if(document.EmpRelieval.txtDORelieval.value.length==0)
    {
        alert("Enter the Relieval Date");
        document.EmpRelieval.txtDORelieval.focus();
        return false;
    }
     
    
   /* var c=checkdate();
    //alert(c);
       if(c==false)
       {
          document.EmpRelieval.txtDORelieval.focus();
            return false;
        }*/
         var c=checkdatemax();
    //alert(c);
       if(c==false)
       {
          document.EmpRelieval.txtDORelieval.focus();
            return false;
        }
   if(document.EmpRelieval.cmbReason.value=="" )
    {
        alert("Select a Reason for Relieval");
       document.EmpRelieval.cmbReason.focus();
        return false;
    }
    
     if(document.getElementById("cmbReason").value=='DPN')
    {
      /* if(document.getElementById("txtD_ODep_Id").value.length==0)
        {
            alert("Click the ICON to select the Other Department Id ");
            document.EmpRelieval.txtD_ODep_Id.focus();
            return false;
        }
        else if(document.getElementById("txtD_ODep_OffId").value.length==0)
        {
            alert("Click the ICON to select the Other Department Office Id");
            document.EmpRelieval.txtD_ODep_OffId.focus();
            return false;
        }*/
        if(document.getElementById("txtDept_Id").value=="" || document.getElementById("txtDept_Id").value.length==0)
        {
            alert("Select a Department Id");
            document.EmpRelieval.txtDept_Id.focus();
            return false;
        }
    }
     if(document.getElementById("cmbReason").value=='STU')
    {
      
        if(document.getElementById("txtInst_Name").value=="" || document.getElementById("txtInst_Name").value.length==0)
        {
            alert("Enter Institution Name");
            document.EmpRelieval.txtInst_Name.focus();
            return false;
        }
        var c=checkdate();
         if(c==false)
       {
          document.EmpRelieval.txtSDate_From.focus();
            return false;
        }
    }
     if(document.getElementById("cmbReason").value=='TRN')
    {
        if(document.getElementById("txtT_OffId").value.length==0)
        {
        alert("Please Enter the Office Id");
        document.EmpRelieval.txtT_OffId.focus();
        return false;
        }
        
    }
   
     if(document.getElementById("cmbReason").value=='LLV')
    {
        if(document.getElementById("cmbLL_TypeId").value=="")
        {
            alert("Select the Leave Type");
            document.EmpRelieval.cmbLL_TypeId.focus();
            return false;
        }
        if(document.getElementById("txtL_Period_From").value!="" && document.getElementById("txtL_Period_To").value!="")
        {
            if(!checkFromTODate())
                return false;
        }
        
    }
     if(document.getElementById("cmbReason").value=='PRO')
    {
        if(document.getElementById("txtP_OffId").value.length==0)
        {
            alert("Enter the Office Id ");
            document.EmpRelieval.txtP_OffId.focus();
            return false;
        }
        else if(document.getElementById("txtP_desigId").value.length==0)
        {
            alert("Enter the Designation Id");
            document.EmpRelieval.txtP_desigId.focus();
            return false;
        }
    }
    
   return true;
}
function doFunction(Command,param)
{
//alert("jj");
    eid=document.EmpRelieval.txtEmployeeid.value;
    var reason=document.getElementById("cmbReason").value;
    //alert(reason);
    //alert("EID:"+eid);
    if(Command=="Suspension")
    {
    var url="../../../../../Create_New_RelievalServlet.view?Command=Suspension&txtEmployeeid="+eid+"&reasonid="+reason;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    else if(Command=="Add")
    {       
         if(chechNull()==true)
         {
        }
    }
        
        else if(Command=="loademp")
        {   //alert(eid);
        var offid=document.EmpRelieval.txtOffId.value;
            var url="../../../../../Create_New_RelievalServlet.view?Command=loademp&txtEmployeeid="+eid+"&offid="+offid;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    
                   
                    
        }
   
        else if(Command=="office")
        {   //alert(eid);
            var oid=param;
            
            var url="../../../../../Create_New_RelievalServlet.view?Command=office&oid="+oid;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                   if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    
        }
         else if(Command=="dept")
        {   //alert(eid);
            //var oid=param;
            //alert(param + "  " +oid);
            oid=document.EmpRelieval.txtD_ODep_OffId.value;
            deptid=document.EmpRelieval.txtD_ODep_Id.value;
            
            var url="../../../../../Create_New_RelievalServlet.view?Command=dept&oid="+oid+"&deptid="+deptid;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                   if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    
        }
        else if(Command=="prodep")
        {
            oid=document.EmpRelieval.txtP_OffId.value;
            deptid=document.EmpRelieval.txtP_depid.value;
            
            //alert('oid_prodep...'+oid);
            //alert('deptid_prodep...'+deptid);
            var url="../../../../../Create_New_RelievalServlet.view?Command=dept&oid="+oid+"&deptid="+deptid;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                   if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
         else if(Command=="desig")
        {   //alert(eid);
            var desigid=document.EmpRelieval.txtP_desigId.value;
            //alert(param + "  " +oid);txtP_desigId
            var url="../../../../../Create_New_RelievalServlet.view?Command=desig&desigid="+desigid;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                  if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    
        }
 }   

function handleResponse(req)
{  
//alert("inside handle resp")
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
        //alert(req.responseText)
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            //alert(tagcommand)
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="Add")
            {   
                addRow(baseResponse);
                
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                 try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
            else if(Command=="Suspension")
            {
            //alert("1")
             var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           // alert(z)
            if(flag=="success")
            {
            //alert("")
            document.getElementById("butSub").disabled=false;
            }
            else
            {
            //alert("3");
            alert("The Employee Id is Under Suspension");
           // disableAll();
           document.getElementById("Reason1").style.display='none';
            document.getElementById("Reason2").style.display='none';
            document.getElementById("Reason3").style.display='none';
            document.getElementById("Reason4").style.display='none';
            document.getElementById("Reason5").style.display='none';
            document.getElementById("Reason6").style.display='none';
            document.getElementById("butSub").disabled=true;
            }
            }
            else if(Command=="loademp")
            {
           // alert("show")
                loadEmployee(baseResponse);
            }
            /*else if(Command=="loademp1")
            {
                var reasonid=document.getElementById("cmbReason").value;
                alert(reasonid);
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
                if(flag=="success")
                {  //alert("success");
                    var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
                    var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
                    var dob=baseResponse.getElementsByTagName("dob")[0].firstChild.nodeValue;
                    //if()
                    alert(dob);
                        }
            }*/
             else if(Command=="office")
            {
                loadOffice(baseResponse);
            }
             else if(Command=="dept")
            {
                loadDept(baseResponse);
            }
            else if(Command=="desig")
            {
                loadDesig(baseResponse);
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                 try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
            
            
        }
    }
}
function addRow(baseResponse)
{
      if (winemp && winemp.open && !winemp.closed) winemp.close();
      if (winjob && winjob.open && !winjob.closed) winjob.close();
      if (windesig && windesig.open && !windesig.closed) windesig.close();
}
function loadDesig(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        var designame=baseResponse.getElementsByTagName("designame")[0].firstChild.nodeValue;
        document.EmpRelieval.txtP_DesigName.value=designame;
    }
    else 
    {
    var eid=document.EmpRelieval.txtP_desigId.value;
    alert("Designation Id '"+eid+"' doesn't Exists");
    document.EmpRelieval.txtP_desigId.value="";
    document.EmpRelieval.txtP_desigId.focus();
    document.EmpRelieval.txtP_DesigName.value="";
    }
}
function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  //alert("success");
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
           if(document.getElementById("cmbReason").value=='TRN')
           {
                document.getElementById("txtT_OffId").value=oid;
                document.getElementById("txtT_OffName").value=oname;
           }
          else if(document.getElementById("cmbReason").value=='PRO')
           {
               document.getElementById("txtP_OffId").value=oid;
                document.getElementById("txtP_OffName").value=oname;
                document.getElementById("txtP_depid").value='TWAD';
           }
          else if(document.getElementById("cmbReason").value=='DVN')
           {
               document.getElementById("txtDv_OffId").value=oid;
               document.getElementById("txtDv_OffName").value=oname;
           }
       
    }
    else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     alert("Office Id '"+oid+"' doesn't Exists");
          if(document.getElementById("cmbReason").value=='TRN')
           {
                document.getElementById("txtT_OffId").focus();
                document.getElementById("txtT_OffId").value="";
                document.getElementById("txtT_OffName").value="";
           }
          else if(document.getElementById("cmbReason").value=='PRO')
           {
               document.getElementById("txtP_OffId").focus();
               document.getElementById("txtP_OffId").value="";
               document.getElementById("txtP_OffName").value="";
           }
          else if(document.getElementById("cmbReason").value=='DVN')
           {
               document.getElementById("txtDv_OffId").focus();
               document.getElementById("txtDv_OffId").value="";
               document.getElementById("txtDv_OffName").value="";
           }
    }
}

function loadDept(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        
        var dname=baseResponse.getElementsByTagName("dname")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        document.EmpRelieval.txtD_DepName.value=dname;
        document.EmpRelieval.txtD_OffName.value=oname;
        document.EmpRelieval.txtP_OffName.value=oname;
        
    }
    else 
    {
    }
}

function loadEmployee(baseResponse)
{
//alert('aaa');
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert('success');
    if(flag=="success")
    {  //alert("success");
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
        var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
        //var dob=baseResponse.getElementsByTagName("dob")[0].firstChild.nodeValue;
        //alert("dob"+dob);
        maxfromdate=baseResponse.getElementsByTagName("maxfromdate")[0].firstChild.nodeValue;
        //alert(maxfromdate);
        maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
        //document.EmpRelieval.txtDORelieval.value=maxfromdate;
//alert(maxfromdate);
         //document.EmpRelieval.birthDate.value=dob;
        document.EmpRelieval.txtEmployeeid.value=eid;
        document.EmpRelieval.txtEmpName.value=ename;
        document.EmpRelieval.txtEmpDesig.value=desig;
        
        /*var reasonid=document.getElementById("cmbReason").value;
        alert(reasonid);
        var year=dob.getYear();
        if()*/
        document.EmpRelieval.txtDORelieval.focus();
    }
    else if(flag=="failure1")
    {
        var eid=document.EmpRelieval.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' doesn't have a post.");
        document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        document.EmpRelieval.txtEmpDesig.value="";
    }
    else if(flag=="failure2")
    {
        var eid=document.EmpRelieval.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a unfrezeed relival record.");
        document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        document.EmpRelieval.txtEmpDesig.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.EmpRelieval.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in working status. So can not create relieval.");
        document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        document.EmpRelieval.txtEmpDesig.value="";
    }
     else if(flag=="failurea")
    {
           var eid=document.EmpRelieval.txtEmployeeid.value;
          // alert("Can not Create Relieval. Because Employee Id "+eid+" is not under your Office!");
          alert("SR controling office for this employee is different from your office!");
           document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        document.EmpRelieval.txtEmpDesig.value="";
            
    }
     else if(flag=="failureb")
    {
           var eid=document.EmpRelieval.txtEmployeeid.value;
            alert("You have no Current Posting. Can not Create Relieval for "+eid+"!");
            document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        document.EmpRelieval.txtEmpDesig.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.EmpRelieval.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Relieval!");
            document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        document.EmpRelieval.txtEmpDesig.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.EmpRelieval.txtEmployeeid.value;
            alert("Can not Create Relieval. Access Denined!");
            document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        document.EmpRelieval.txtEmpDesig.value="";
           
    }
    else 
    {
    var eid=document.EmpRelieval.txtEmployeeid.value;
    alert("Employee Id '"+eid+"' doesn't Exists");
    document.EmpRelieval.txtEmployeeid.value="";
    document.EmpRelieval.txtEmployeeid.focus();
    document.EmpRelieval.txtEmpName.value="";
    document.EmpRelieval.txtEmpDesig.value="";
    }      
  document.EmpRelieval.btsub.disabled=false;
}
        
        
function checkEID()
{
    if(document.EmpRelieval.txtEmployeeid.value.length==0)
    {
    alert("select Employee Id");
    document.EmpRelieval.txtEmpName.value="";
    document.EmpRelieval.txtEmpDesig.value="";
    document.EmpRelieval.txtEmployeeid.focus();
    return false;
    }
  return true;
}
function checkDate_tmp()
{
    if(document.EmpRelieval.txtDORelieval.value.length==0)
    {
    alert("Enter The Date Of Relieval");
    document.EmpRelieval.txtDORelieval.focus();
    return false;
    }
  return true;

}
function checkFromTODate()
{
//alert('check');

        var fromdt=document.EmpRelieval.txtL_Period_From.value;
        var todt=document.EmpRelieval.txtL_Period_To.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            alert('From Date should be less than To Date');
            document.EmpRelieval.txtL_Period_From.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    document.EmpRelieval.txtL_Period_From.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('From Date should be less than To Date');
                           document.EmpRelieval.txtL_Period_From.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}
function cheakreason()
{
if(document.EmpRelieval.cmbReason.value=="")
{
alert("Select a Reason for Releival");
document.EmpRelieval.cmbReason.focus();
return false;
}
return true;
}

function enableReason(Reason)
{   
    disableALL();
    if(Reason=='TRN')
    {   
        
        document.getElementById("Reason1").style.display='block';
        document.EmpRelieval.txtT_OffId.focus();
        
        
        
    }
    else if(Reason=='DPN')
    {   
        document.getElementById("Reason2").style.display='block';
        
        //document.EmpRelieval.radDOD[0].disabled=false;
        //document.EmpRelieval.radDOD[1].disabled=false;
       // document.EmpRelieval.txtDept_Id.focus();
       
       
    }
    else if(Reason=='PRO')
    {   
        document.getElementById("Reason3").style.display='block';
        document.EmpRelieval.txtP_OffId.focus();
        
        
    }
    else if(Reason=='LLV')
    {   
        document.getElementById("Reason4").style.display='block';
        document.EmpRelieval.cmbLL_TypeId.focus();
        
        
    }
    
    else if(Reason=='DVN')
    {   
        document.getElementById("Reason5").style.display='block';
        document.EmpRelieval.txtDv_OffId.focus();
    }
    else if(Reason=='STU')
    {   
        document.getElementById("Reason6").style.display='block';
        document.EmpRelieval.txtInst_Name.focus();
        
       
    }
    else if(Reason=='SUS' || Reason=='VRS' || Reason=='DIS' || Reason=='SAN' || Reason=='DTH' || Reason=='ABR')
        document.EmpRelieval.txtRemarks.focus();
        
        
}

function disableALL()
{
        document.getElementById("Reason1").style.display='none';
        document.getElementById("Reason2").style.display='none';
        document.getElementById("Reason3").style.display='none';
        document.getElementById("Reason4").style.display='none';
        document.getElementById("Reason5").style.display='none';
        document.getElementById("Reason6").style.display='none';
        
}






/* Date Validation Checking */
function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }


function checkdt(t)
{

   if(t.value.length==0)
       return false;
   if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
   {


       // var c=t.value.replace(/-/g,'/');
        var c=t.value;
       try{
       var f=DateFormat(t,c,event,true,'3');
       }catch(e){
       //exception  start

        t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;
            if(err!=0)
               {
                   t.value="";
                   return false;
               }
           return true;


       //exception end

       }
       if( f==true)
       {
           //alert(f);
           //t.value=c.replace(/\//g,'-');
           t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;

           return true;

       }
       else
       {
               if(err!=0)
               {
                   t.value="";
                   return false;
               }
       }

   }
   else
   {
           alert('Date format  should be (dd/mm/yyyy)');
           t.value="";
           //t.focus();
           return false
   }

}

function checkdt1(t)
{

   if(t.value.length==0)
       return false;
   if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
   {


        var c=t.value;
       try{
       var f=DateFormat(t,c,event,true,'3');
       }catch(e){
       //exception  start

        
       }
       if( f==true)
       {
           return true;
        }
       else
       {
               if(err!=0)
               {
                   t.value="";
                   return false;
               }
       }

   }
   else
   {
           alert('Date format  should be (dd/mm/yyyy)');
           t.value="";
           //t.focus();
           return false
   }

}

function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39  )
        {
        
            
            if (unicode<48||unicode>57 ) 
                return false 
        }
       

}
    
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
     
/*function DisplayDate(periodValue)
{   
    if(periodValue=="YES")
        document.getElementById("Reason5_3").style.display='';
    else if(periodValue=="NO")
        document.getElementById("Reason5_3").style.display='none';
}*/
function clear()
{
          
           document.EmpRelieval.txtEmployeeid.value="";
           document.EmpRelieval.txtEmpName.value="";
           document.EmpRelieval.txtEmpDesig.value="";
           document.EmpRelieval.txtRel_SLNO.value="";
           document.EmpRelieval.txtDORelieval.value="";
           document.EmpRelieval.rad_DORelieval[0].checked=true;
           document.EmpRelieval.cmbReason.value="";
           document.EmpRelieval.txtRemarks.value="";
           disableALL();
}



function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }
function checkcurdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    return true;
    
}


function deptchange()
{
        if(document.getElementById("txtDept_Id").value=="" || document.getElementById("txtDept_Id").value.length==0)
        {
            alert("Select a Department Id");
            document.EmpRelieval.txtDept_Id.focus();
            
            document.EmpRelieval.txtD_ODep_Id.value='';
            document.EmpRelieval.txtD_DepName.value='';
            document.EmpRelieval.txtD_ODep_OffId.value='';
            document.EmpRelieval.txtD_OffName.value='';
            return false;
        }
        else
        {
            document.EmpRelieval.txtD_ODep_Id.value=document.getElementById("txtDept_Id").value;
            document.EmpRelieval.txtD_DepName.value='';
            document.EmpRelieval.txtD_ODep_OffId.value='';
            document.EmpRelieval.txtD_OffName.value='';
        }

}


function checkdate()
{
//alert('check');
        var fromdt=document.EmpRelieval.txtSDate_From.value;
        var todt=document.EmpRelieval.txtSDate_To.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
        if(fyear>tyear)
        {
            alert('To Date should be greater than or equal to To Date');
            //document.EmpRelieval.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('To Date should be greater than or equal to To Date');
                    //document.EmpRelieval.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('To Date should be greater than or equal to To Date');
                            //document.EmpRelieval.txtDateTo.focus();
                            return false;
                        }
                     
                        
                }
        }
        return true;

}


function check_SAN()
{
var reasonid=document.getElementById("cmbReason").value;
if(reasonid=='SAN')
{
var bryear=document.getElementById("birthDate").value;
//alert("byear"+bryear);
var reldate=new Date(document.getElementById("txtDORelieval").value);
//alert("rd"+reldate);

var ryr=reldate.getYear()-1900;
//alert(ryr)
var diff=ryr-bryear;
//alert(diff)
if(diff<=58)
 {
 alert("The Selected Employee is not eligible for this option");
 }
}
else
{}
}


function checkDemise()
{
var empid=document.getElementById("txtEmployeeid").value;
//alert(empid);


return true;
}


function displaydetails()
{
  var c=document.EmpRelieval.cmb_relproc_no.options[document.EmpRelieval.cmb_relproc_no.selectedIndex].value;
  
  if(c!=0)
  {
    //document.getElementById("cmb_proc_no").style.display='none';
    document.EmpRelieval.txtPNo.value=c;
  }  
  else
  {
     document.EmpRelieval.txtPNo.value="";
  }


}

function getReason()
{
  var rsn=document.EmpRelieval.cmbReason.options[document.EmpRelieval.cmbReason.selectedIndex].text;
 // alert(rsn);
  
  document.EmpRelieval.txtReason.value=rsn;
   /*  var prodet=document.EmpRelieval.txtReason.value;
         
         var det= prodet+" Order Details";
         //alert(det);
         document.EmpRelieval.txtdet.value=det;*/
}

/*
function checkProced()
{
  var id=document.getElementById("proceed");
  //alert("id");
  var res=document.EmpRelieval.cmbReason.options[document.EmpRelieval.cmbReason.selectedIndex].value;
  //alert(res);
  if(res=='RES')
  {
    id.style.display="none";
  }
  else
  {
     id.style.display="block";
  }
}
*/
function getFunction()
{
  var new_reas=document.EmpRelieval.cmbReason.options[document.EmpRelieval.cmbReason.selectedIndex].value;
  
  var offid=document.EmpRelieval.txtOffId.value;
  
  if(new_reas=='TRN')
  {
     startwaiting(document.EmpRelieval);
  
     var url="../../../../../Create_Proceeding_DetailsServ?Command=TRN&Offid="+offid;
     //alert(url);
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadReference(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
  }
  
  else if(new_reas=='PRO')
  {
     var url="../../../../../Create_Proceeding_DetailsServ?Command=PRO&Offid="+offid;
     
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadReference_PRO(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
  }
  
  else if(new_reas=='DPN')
  {
     var url="../../../../../Create_Proceeding_DetailsServ?Command=DPN&Offid="+offid;
     
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadReference_DPN(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
  }
}

function loadReference(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
      var ref=document.getElementById("cmb_proc_no");
      var i=0;
      
      var response=req.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      
      ref.innerHTML="";
      
      stopwaiting(document.EmpRelieval);
      
      if(flag=="failure")
      {
        alert('No Proceeding exists');
      }
      else
      {
         var value=response.getElementsByTagName("option");         
         var option=document.createElement("OPTION");
         option.text="---Select Proceeding No---";
         option.value="0";
         
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         
         for(i=0;i<value.length;i++)
         {
           var tmpoption=value.item(i);           
           var id=tmpoption.getElementsByTagName("order_id")[0].firstChild.nodeValue;
           var name=tmpoption.getElementsByTagName("proceeding_no")[0].firstChild.nodeValue;
           
           var option=document.createElement("OPTION");
           option.text=name;
           option.value=id;
           
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         }
         
        
      }
    }
  }
}

function loadReference_DPN(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
      var ref=document.getElementById("cmb_proc_no");
      var i=0;
      
      var response=req.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      
      ref.innerHTML="";
      
      stopwaiting(document.EmpRelieval);
      
      if(flag=="failure")
      {
        alert('No Proceeding exists');
      }
      else
      {
         var value=response.getElementsByTagName("option");         
         var option=document.createElement("OPTION");
         option.text="---Select Proceeding No---";
         option.value="0";
         
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         
         for(i=0;i<value.length;i++)
         {
           var tmpoption=value.item(i);           
           var id=tmpoption.getElementsByTagName("order_id")[0].firstChild.nodeValue;
           var name=tmpoption.getElementsByTagName("proceeding_no")[0].firstChild.nodeValue;
           
           var option=document.createElement("OPTION");
           option.text=name;
           option.value=id;
           
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         }
      }
    }
  }
}

function loadReference_PRO(req)
{
if(req.readyState==4)
  {
    if(req.status==200)
    {
      var ref=document.getElementById("cmb_proc_no");
      var i=0;
      
      var response=req.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      
      ref.innerHTML="";
      
      stopwaiting(document.EmpRelieval);
      
      if(flag=="failure")
      {
        alert('No Proceeding exists');
      }
      else
      {
         var value=response.getElementsByTagName("option");         
         var option=document.createElement("OPTION");
         option.text="---Select Proceeding No---";
         option.value="0";
         
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         
         for(i=0;i<value.length;i++)
         {
           var tmpoption=value.item(i);           
           var id=tmpoption.getElementsByTagName("order_id")[0].firstChild.nodeValue;
           var name=tmpoption.getElementsByTagName("proceeding_no")[0].firstChild.nodeValue;
           
           var option=document.createElement("OPTION");
           option.text=name;
           option.value=id;
           
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         }
      }
    }
  }
}

function displaydet()
{
  var pro=document.EmpRelieval.cmb_proc_no.options[document.EmpRelieval.cmb_proc_no.selectedIndex].value;
  //alert(pro);  
  var reas=document.EmpRelieval.cmbReason.options[document.EmpRelieval.cmbReason.selectedIndex].value;
  //alert(reas);
  
  
  if(reas==0)
  {
    alert('Please Select Ref Proceeding No.');
    return false;
  }
  else
  {
    if(reas=='TRN')
    {
      startwaiting(document.EmpRelieval);
  
     var url="../../../../../Create_Proceeding_DetailsServ?Command=GetTRN&OrderId="+pro;
     //alert(url);
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadDetails(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
    }
    else if(reas=='DPN')
    {
      startwaiting(document.EmpRelieval);
  
     var url="../../../../../Create_Proceeding_DetailsServ?Command=GetDPN&OrderId="+pro;
     //alert(url);
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadDetails(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
    } 
    
    else if(reas=='PRO')
    {
      startwaiting(document.EmpRelieval);
  
     var url="../../../../../Create_Proceeding_DetailsServ?Command=GetPRO&OrderId="+pro;
     //alert(url);
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadDetails(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
    } 
  }
}

function loadDetails(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
      var ref=document.getElementById("cmb_EmpId");
      var i=0;
      
      var response=req.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      
      ref.innerHTML="";
      
      stopwaiting(document.EmpRelieval);
      
      if(flag=="failure")
      {
        alert('No Employee exists');
      }
      else
      {
         //alert('inside else');
      
         var dat=response.getElementsByTagName("proc_date")[0].firstChild.nodeValue;
         
         var m=dat.split('-');
         var prodat1=m[2]+"/"+m[1]+"/"+m[0];
         
         if(prodat1!=null)         
           document.EmpRelieval.txtRDat.value=prodat1;
         else
           document.EmpRelieval.txtRDat.value="";
         var prodet=document.EmpRelieval.txtReason.value;
         var prono=document.EmpRelieval.cmb_proc_no.options[document.EmpRelieval.cmb_proc_no.selectedIndex].text;
        //var detail= prodet+" Order Details"+"\n"+"Proceeding Number is:  "+prono+"\n"+"Proceeding Date is: "+prodat1;
         //alert(detail);
         var detail= "Proceedings No. :"+prono+" Dt. "+prodat1;
         document.EmpRelieval.txtRef.value=detail;
         var value=response.getElementsByTagName("details");         
         var option=document.createElement("OPTION");
         option.text="---Select Employee Id---";
         option.value="0";
         
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         
         for(i=0;i<value.length;i++)
         {
           var tmpoption=value.item(i);           
           var id=tmpoption.getElementsByTagName("employ_id")[0].firstChild.nodeValue;
           //var name=tmpoption.getElementsByTagName("employ_id")[0].firstChild.nodeValue;
           
           var option=document.createElement("OPTION");
           option.text=id;
           option.value=id;
           
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         }
      }
    }
  }
}

function displayEmployees()
{
  var emp=document.EmpRelieval.cmb_EmpId.options[document.EmpRelieval.cmb_EmpId.selectedIndex].value;
  
  if(emp==0)
  {
    alert('Please Select Employee Id');
    return false;
  }
  else
  {
    document.EmpRelieval.txtEmployeeid.value=emp;
  }
}

function getOther()
{
   var eid=document.EmpRelieval.txtEmployeeid.value;
   var ord=document.EmpRelieval.cmb_proc_no.options[document.EmpRelieval.cmb_proc_no.selectedIndex].value;
   var reas=document.EmpRelieval.cmbReason.options[document.EmpRelieval.cmbReason.selectedIndex].value;
   
   if(reas==0)
  {
    alert('Please Select Ref Proceeding No.');
    return false;
  }
  else
  {
    if(reas=='TRN')
    {
      startwaiting(document.EmpRelieval);
  
     var url="../../../../../Create_Proceeding_DetailsServ?Command=GetOtherDet&OrderId="+ord+"&Emp="+eid+"&reas="+reas;
     //alert(url);
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadOthDetails(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
    }
    
    else if(reas=='DPN')
    {
      startwaiting(document.EmpRelieval);
  
     var url="../../../../../Create_Proceeding_DetailsServ?Command=GetOtherDet&OrderId="+ord+"&Emp="+eid+"&reas="+reas;
     //alert(url);
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadOthDetails(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
    }
    
    else if(reas=='PRO')
    {
      startwaiting(document.EmpRelieval);
  
     var url="../../../../../Create_Proceeding_DetailsServ?Command=GetOtherDet&OrderId="+ord+"&Emp="+eid+"&reas="+reas;
     //alert(url);
     var req=getTransport();
     
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
       loadOthDetails(req);
     }
     
     if(window.XMLHttpRequest)
       req.send(null);
     else req.send();
    }
  }
   
   
}

function loadOthDetails(req)
{
  if(req.readyState==4)
    {
        if(req.status==200)
        {   
        
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="TRNdata")
            {   
                TRNdat(baseResponse);
                
            }
            else if(Command=="DPNdata")
            {   
                DPNdat(baseResponse);
                
            }
            else if(Command=="PROdata")
            {   
                PROdat(baseResponse);
                
            }
        }
     }
}

function TRNdat(baseResponse)
{

   stopwaiting(document.EmpRelieval);
   var employee_id=baseResponse.getElementsByTagName("employ")[0].firstChild.nodeValue;
   
   var new_office=baseResponse.getElementsByTagName("new_offid")[0].firstChild.nodeValue;
   
   var off_name=baseResponse.getElementsByTagName("off_name")[0].firstChild.nodeValue;
   
   var desig_id=baseResponse.getElementsByTagName("desig_id")[0].firstChild.nodeValue;
   
   var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
   
   var repost_req=baseResponse.getElementsByTagName("repost")[0].firstChild.nodeValue;
   
   
   document.EmpRelieval.txtT_OffId.value=new_office;
   
   document.EmpRelieval.txtT_OffName.value=off_name;
   
   document.EmpRelieval.TRN_desigId.value=desig_id;
   
   document.EmpRelieval.TRN_DesigName.value=desig;
   
   if(repost_req=='Y')
     document.EmpRelieval.radT_Repost[0].checked=true;
   else
     document.EmpRelieval.radT_Repost[1].checked=true;
}

function DPNdat(baseResponse)
{
  stopwaiting(document.EmpRelieval);
 
   var emp_id=baseResponse.getElementsByTagName("employ")[0].firstChild.nodeValue;
   
   var dept_id=baseResponse.getElementsByTagName("dept_id")[0].firstChild.nodeValue;
   
   var off_id=baseResponse.getElementsByTagName("new_offid")[0].firstChild.nodeValue;
   
   var off_name=baseResponse.getElementsByTagName("off_name")[0].firstChild.nodeValue;
   
   document.EmpRelieval.txtDept_Id.value=dept_id;
   
   document.EmpRelieval.txtD_ODep_Id.value=dept_id;
   
   document.EmpRelieval.txtD_ODep_OffId.value=off_id;
   
   document.EmpRelieval.txtD_OffName.value=off_name;
}

function PROdat(baseResponse)
{

   stopwaiting(document.EmpRelieval);
   var employee_id=baseResponse.getElementsByTagName("employ")[0].firstChild.nodeValue;
   
   var new_office=baseResponse.getElementsByTagName("new_offid")[0].firstChild.nodeValue;
   
   var off_name=baseResponse.getElementsByTagName("off_name")[0].firstChild.nodeValue;
   
   var desig_id=baseResponse.getElementsByTagName("desig_id")[0].firstChild.nodeValue;
   
   var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
   
   var repost_req=baseResponse.getElementsByTagName("repost")[0].firstChild.nodeValue;
   
   var dep_id=baseResponse.getElementsByTagName("dep_id")[0].firstChild.nodeValue;
   
   
   document.EmpRelieval.txtP_OffId.value=new_office;
   
   document.EmpRelieval.txtP_depid.value=dep_id;
   
   document.EmpRelieval.txtP_OffName.value=off_name;
   
   document.EmpRelieval.txtP_desigId.value=desig_id;
   
   document.EmpRelieval.txtP_DesigName.value=desig;
   
   if(repost_req=='Y')
     document.EmpRelieval.radP_Repost[0].checked=true;
   else
     document.EmpRelieval.radP_Repost[1].checked=true;
}

function disab_Element()
{

   var reas=document.EmpRelieval.cmbReason.options[document.EmpRelieval.cmbReason.selectedIndex].value;
   
   clearElement();
   
   if(reas=='TRN' || reas=='PRO' || reas=='DPN')
   {
        document.getElementById("cmb_proc_no").disabled=false;
        document.getElementById("cmb_EmpId").disabled=false;
        document.getElementById("img_emp").disabled=true;
   }
   else
   {
        document.getElementById("cmb_proc_no").disabled=true;
        document.getElementById("cmb_EmpId").disabled=true;
        document.getElementById("img_emp").disabled=false;
   }  

}

function clearElement()
{
  document.EmpRelieval.cmb_proc_no.value=0;
  document.EmpRelieval.txtRDat.value="";
  document.EmpRelieval.cmb_EmpId.value=0;
  
  document.EmpRelieval.cmb_relproc_no.value=0;
  document.EmpRelieval.txtPNo.value="";
  document.EmpRelieval.txtT_proc_Date.value="";
}


function checknull2()
{
 /* if((document.EmpRelieval.txtT_proc_Date.value=="") || (document.EmpRelieval.txtT_proc_Date.value.length==0))
  {
    alert('Please Enter Relieval Proceeding Date ');
    return false;
  } */
  
  if((document.EmpRelieval.txtDORelieval.value=="") || (document.EmpRelieval.txtDORelieval.value.length==0))
  {
    alert('Please Enter Relieval Date ');
    return false;
  } 

  else if((document.EmpRelieval.txtPO.value=="") || (document.EmpRelieval.txtPO.value.length==0))
  {
    alert('Please Enter Presiding Officer in Order details tab');
    return false;
  }
  else if((document.EmpRelieval.txtPOD.value=="") || (document.EmpRelieval.txtPOD.value.length==0))
  {
    alert('Please Enter Presiding Officer Designation in Order details tab');
    return false;
  }
  else if((document.EmpRelieval.txtSub.value=="") || (document.EmpRelieval.txtSub.value.length==0))
  {
    alert('Please Enter Subject in Order details tab');
    return false;
  }
  
  else if((document.EmpRelieval.txtRef.value=="") || (document.EmpRelieval.txtRef.value.length==0))
  {
    alert('Please Enter Reference in Order details tab');
    return false;
  } 
  /*
  else if((document.EmpRelieval.txtAdd1.value=="") || (document.EmpRelieval.txtAdd1.value.length==0))
  {
    alert('Please Enter Additional Para 1 in Order details tab');
    return false;
  }
  
  else if((document.EmpRelieval.txtAdd2.value=="") || (document.EmpRelieval.txtAdd2.value.length==0))
  {
    alert('Please Enter Additional Para 2 in Order details tab');
    return false;
  }
  */
  else if((document.EmpRelieval.txtcopy.value=="") || (document.EmpRelieval.txtcopy.value.length==0))
  {
    alert('Please Enter Copy to in Order details tab');
    return false;
  }
  
  return true;
}
