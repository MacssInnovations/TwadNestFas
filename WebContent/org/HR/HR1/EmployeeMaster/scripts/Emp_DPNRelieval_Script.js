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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpDeputationRelievalPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
             winjob.officeSelection(true,true,true,false);
     }
     
}

function checkdate()
{
//alert('check');
     if(maxfromdate==null || maxfromdate=='empty')
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
        document.EmpRelieval.txtP_OffId.value=jobid;
        doFunction('office',jobid);
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
    else if( document.EmpRelieval.txtEmployeeid.value.length==0)
    {
        alert("Enter the Employee Id ");
        document.EmpRelieval.txtEmployeeid.focus();
        return false;
    }
    else if(document.EmpRelieval.txtDORelieval.value.length==0)
    {
        alert("Enter the Relieval Date");
        document.EmpRelieval.txtDORelieval.focus();
        return false;
    }
    
    var c=checkdate();
       if(c==false)
       {
          document.EmpRelieval.txtDORelieval.focus();
            return false;
        }
   /* else if(document.EmpRelieval.cmbReason.value=="" )
    {
        alert("Select a Reason for Relieval");
       document.EmpRelieval.cmbReason.focus();
        return false;
    }
    
    else if(document.getElementById("cmbReason").value=='DPN')
    {
       if(document.getElementById("txtD_ODep_Id").value.length==0)
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
        } 
    }
    else if(document.getElementById("cmbReason").value=='TRN')
    {
        if(document.getElementById("txtT_OffId").value.length==0)
        {
        alert("Please Enter the Office Id");
        document.EmpRelieval.txtT_OffId.focus();
        return false;
        }
        
    }
   
    else if(document.getElementById("cmbReason").value=='LLV')
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
    else if(document.getElementById("cmbReason").value=='PRO')
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
    */
   return true;
}
function doFunction(Command,param)
{
    eid=document.EmpRelieval.txtEmployeeid.value;
    //alert("EID:"+eid);
    if(Command=="Add")
    {       
         if(chechNull()==true)
         {
        }
    }
        
        else if(Command=="loademp")
        {   //alert(eid);
        var offid=document.EmpRelieval.txtOffId.value;
            var url="../../../../../Create_Relieval_ReportServ.view?Command=loademp&txtEmployeeid="+eid+"&offid="+offid;
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
            
            var url="../../../../../Create_Relieval_ReportServ.view?Command=office&oid="+oid;
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
            var url="../../../../../Create_Relieval_ReportServ.view?Command=dept&oid="+oid+"&deptid="+deptid;
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
            var url="../../../../../Create_Relieval_ReportServ.view?Command=desig&desigid="+desigid;
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
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="Add")
            {   
                addRow(baseResponse);
                
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
            else if(Command=="loademp")
            {
                loadEmployee(baseResponse);
            }
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
    }
    else 
    {
    }
}

function loadEmployee(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  //alert("success");
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
       // var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
        
        maxfromdate=baseResponse.getElementsByTagName("maxfromdate")[0].firstChild.nodeValue;
        maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
        
        var otherdeptname=baseResponse.getElementsByTagName("otherdeptname")[0].firstChild.nodeValue;
        var otherdeptoffice=baseResponse.getElementsByTagName("otherdeptoffice")[0].firstChild.nodeValue;
        //document.EmpRelieval.txtDORelieval.value=maxfromdate;
        //alert(maxfromdate);
        document.EmpRelieval.txtEmployeeid.value=eid;
        document.EmpRelieval.txtEmpName.value=ename;
       // document.EmpRelieval.txtEmpDesig.value=desig;
        
        document.EmpRelieval.txtDepDoj.value=maxfromdate;
        document.EmpRelieval.txtDepName.value=otherdeptname;
        document.EmpRelieval.txtDepOffName.value=otherdeptoffice;
        
        document.EmpRelieval.txtDORelieval.focus();
    }
    else if(flag=="failure1")
    {
        var eid=document.EmpRelieval.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' doesn't have a post.");
        document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        //document.EmpRelieval.txtEmpDesig.value="";
    }
    else if(flag=="failure2")
    {
        var eid=document.EmpRelieval.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a unfrezeed relival record.");
        document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
       // document.EmpRelieval.txtEmpDesig.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.EmpRelieval.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in Deputation status. So can not create deputation relieval.");
        document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        //document.EmpRelieval.txtEmpDesig.value="";
    }
     else if(flag=="failurea")
    {
           var eid=document.EmpRelieval.txtEmployeeid.value;
          // alert("Can not Create Relieval. Because Employee Id "+eid+" is not under your Office!");
          alert("SR controling office for this employee is different from your office!");
           document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
        //document.EmpRelieval.txtEmpDesig.value="";
            
    }
     else if(flag=="failureb")
    {
           var eid=document.EmpRelieval.txtEmployeeid.value;
            alert("You have no Current Posting. Can not Create Relieval for "+eid+"!");
            document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
       // document.EmpRelieval.txtEmpDesig.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.EmpRelieval.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Relieval!");
            document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
       // document.EmpRelieval.txtEmpDesig.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.EmpRelieval.txtEmployeeid.value;
            alert("Can not Create Relieval. Access Denined!");
            document.EmpRelieval.txtEmployeeid.value="";
        document.EmpRelieval.txtEmployeeid.focus();
        document.EmpRelieval.txtEmpName.value="";
       // document.EmpRelieval.txtEmpDesig.value="";
           
    }
    else 
    {
    var eid=document.EmpRelieval.txtEmployeeid.value;
    alert("Employee Id '"+eid+"' doesn't Exists");
    document.EmpRelieval.txtEmployeeid.value="";
    document.EmpRelieval.txtEmployeeid.focus();
    document.EmpRelieval.txtEmpName.value="";
    //document.EmpRelieval.txtEmpDesig.value="";
    }
}
        
        
function checkEID()
{
    if(document.EmpRelieval.txtEmployeeid.value.length==0)
    {
    alert("select Employee Id");
    document.EmpRelieval.txtEmpName.value="";
   // document.EmpRelieval.txtEmpDesig.value="";
    document.EmpRelieval.txtEmployeeid.focus();
    return false;
    }
  return true;
}
function checkDate()
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
        document.EmpRelieval.txtD_ODep_Id.focus();
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
    else if(Reason=='SUS' || Reason=='VRS' || Reason=='DIS' || Reason=='SAN')
        document.EmpRelieval.txtRemarks.focus();
}

function disableALL()
{
        document.getElementById("Reason1").style.display='none';
        document.getElementById("Reason2").style.display='none';
        document.getElementById("Reason3").style.display='none';
        document.getElementById("Reason4").style.display='none';
        document.getElementById("Reason5").style.display='none';
        
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
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
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
           //document.EmpRelieval.txtEmpDesig.value="";
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

function noEnter(e)
{
   
   isIE=document.all? 1:0
       
   keyEntry = !isIE? e.which:event.keyCode;
                  
   if(keyEntry=='38')
   {
     return false;
   }
}