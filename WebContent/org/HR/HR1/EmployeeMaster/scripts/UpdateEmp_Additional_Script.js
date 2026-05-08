//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;
var imgpath;
var OfficeId;
var winemp;
var my_window;
var joindate;
var reason;
//alert("offid "  + OfficeId);
var DefaultImage="../../../../../images/sample_emp.bmp";

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
       return;
    }
    else
    {
        winjob=null
    }
     startwaiting(document.frmEmployee) ;    
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch for New Update Employee","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
if(deptid=='TWAD')
{
    document.frmEmployee.Office_Id.value=jobid;
    callServer('ExistgOff','null')
    return true
}
else
{
        alert('Please select a TWAD Office');
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
        }
        return false
}
}


//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////

//alert('kkk');
function servicepopup()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
     
    //winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee Selection for New UpdateEmployee","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
     winemp= window.open("EmpServicePopup.jsp","mywindow_NUE","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function servicepopupSR()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
     
    //winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee Selection for New UpdateEmployee","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
     winemp= window.open("EmpPopupSRCtrlOffice.jsp","mywindow_NUE","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}
function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callFunction('ExistgBasic','null');
}

window.onunload=function()
{
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

///////////////////////////////////////////////////////////////////////////////////



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



var s=0;
function getDesignation1()
    {
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.frmEmployee.cmbdes.style.visibility="visible";
            
            var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
       startwaiting(document.frmEmployee) ; 
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
          if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.frmEmployee.cmbdes.style.visibility="hidden";
        }
    }

 function getDesignation()
    {
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.frmEmployee.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.frmEmployee.cmbdes.style.visibility="hidden";
        }
    }
    
    
    
      function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
       startwaiting(document.frmEmployee) ; 
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                stopwaiting(document.frmEmployee) ;
                var des=document.getElementById("cmbdes");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Designation--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}

function callFunction(command,param)
{
    if(command=="ExistgBasic")
       {
         var strEmpId=document.frmEmployee.txtEmpId1.value;
         startwaiting(document.frmEmployee) ; 
          url="../../../../../InsertEmployee3.con?command=ExistgBasic&EmpId=" + strEmpId + "&OfficeId=" + OfficeId;
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processHandleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
       }
       
       
       else if(command=="toUpdate")
       {
       
           var strEmpId=document.frmEmployee.txtEmpId1.value;
           var strhEmpId=document.frmEmployee.EmpId.value;
           var strEmpPref=document.frmEmployee.Employee_Prefix.value;
           var strEmpInit=document.frmEmployee.Employee_Initial.value;
           var strEmpName=document.frmEmployee.Employee_Name.value;
           var strEmpGpf=document.frmEmployee.Gpf_Number.value;
           var hstrEmpId=document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value;;
           var strDob=document.frmEmployee.Date_Of_Birth.value;
           var strGender;
                  if(document.frmEmployee.Gender[0].checked)
                  {
                       strGender=document.frmEmployee.Gender[0].value;
                  }
                  else
                  {
                       strGender=document.frmEmployee.Gender[1].value;    
                  }
               
                  var strCommunity=document.frmEmployee.Community.value;
                  var strDistrict=document.frmEmployee.Native_District.value;
                  var strTaluk=document.frmEmployee.Native_Taluk.value;
                  var strOthers=document.frmEmployee.txtOther.value;
                  var strOtherState=document.frmEmployee.txtOtherState.value;
                 // alert(strOtherState);
               
                  var strQual=document.frmEmployee.Qualification.value;
                  var strEmpmStatus=document.frmEmployee.Post_Status.value;
                 
                  var strDesig=document.frmEmployee.Designation.value;
                  var strEmpeStatus=document.frmEmployee.Employee_Status.value;
                  //alert(strEmpeStatus);
                  var strMarital;
                  if(document.frmEmployee.Marital_Status[0].checked==true)
                      strMarital=document.frmEmployee.Marital_Status[0].value;
                  else
                      strMarital=document.frmEmployee.Marital_Status[1].value;
                 
                  var strRemarks=document.frmEmployee.Remarks.value;
                  
                  if(strRemarks==null)
                  {
                     strRemarks="Value Not Entered";
                  }
                  else
                   strRemarks=strRemarks;
                  
                  var strEmpInit=document.frmEmployee.Employee_Initial.value;
                  var strEmpPrefix=document.frmEmployee.Employee_Prefix.value;
                  var strEmpName=document.frmEmployee.Employee_Name.value;
                  var strEmpGpfNo=document.frmEmployee.Gpf_Number.value;
                  
                   var probation;
                  if(document.frmEmployee.optprobation[0].checked==true)
                      probation=document.frmEmployee.optprobation[0].value;
                  else
                      probation=document.frmEmployee.optprobation[1].value;
                    
                var dateOfRegPro=document.frmEmployee.Date_Of_Reg_Pro.value;
                 
                  var strRemarks=document.frmEmployee.Remarks.value;
                  
                var check=nullCheck(null)
                if(check==true)
                {
                  if(strDistrict!=999)
                  {
                             startwaiting(document.frmEmployee) ; 
                              url="../../../../../InsertEmployee3.con?command=toUpdate&EmpId="+hstrEmpId+"&EmpDOB="+strDob+"&EmpGender="+strGender+"&Comm="+strCommunity+"&District="+strDistrict+"&Taluk="+strTaluk+"&EmpQual="+strQual+"&EmpmStatus="+strEmpmStatus+"&EmpDesig="+strDesig+"&EmpeStatus="+strEmpeStatus+"&EmpMarital="+strMarital+"&Remarks="+strRemarks+"&EmpInitial="+strEmpInit+"&EmpPrefix="+strEmpPrefix+"&EmpName="+strEmpName+"&GpfNo="+strEmpGpfNo;
                              url=url+"&probation="+probation+"&dateOfRegPro="+dateOfRegPro;
                             // alert(url);
                                 var req=getTransport();
                                req.open("GET",url,true); 
                                req.onreadystatechange=function()
                                {
                                   processHandleResponse(req);
                                }   
                              if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    }
                    else
                    {
                         startwaiting(document.frmEmployee) ; 
                        url="../../../../../InsertEmployee3.con?command=toUpdate&EmpId="+hstrEmpId+"&EmpDOB="+strDob+"&EmpGender="+strGender+"&Comm="+strCommunity+"&District="+strDistrict+"&EmpQual="+strQual+"&EmpmStatus="+strEmpmStatus+"&EmpDesig="+strDesig+"&EmpeStatus="+strEmpeStatus+"&EmpMarital="+strMarital+"&Remarks="+strRemarks+ "&Others=" +strOthers+ "&OtherState=" +strOtherState+"&EmpInitial="+strEmpInit+"&EmpPrefix="+strEmpPrefix+"&EmpName="+strEmpName+"&GpfNo="+strEmpGpfNo;
                        url=url+"&probation="+probation+"&dateOfRegPro="+dateOfRegPro;
       //   alert(url);
                         var req=getTransport();
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                        {
                           processHandleResponse(req);
                        }   
                           if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                    
                    }
            }  
         }   
     
                    
        }

function processHandleResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {            
          stopwaiting(document.frmEmployee) ;
          
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
               if(command=="toUpdate")
              {
                  updateRow(baseResponse);                 
              }
              else if(command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
              else if(command=="ExistgBasic")
              {
                  existBasicRow(baseResponse);                 
              }
              
               }
        }
  }
  
  
  function checkdt1()
{
//alert(joindate);
     var flag=false;
     var fday;
     var fmon;
     var fyear;
     var t=document.frmEmployee.Date_Of_Reg_Pro;
      if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
                var c=t.value;
                try{
                        var f=DateFormat(t,c,event,true,'3');
                }catch(e){
                
                 if(joindate==null || joindate=='empty')
                         {
                            //alert('test');
                               flag=true;
                         }
                         else
                         {
                            var fromdt=joindate;
                            var todt=document.frmEmployee.Date_Of_Reg_Pro.value;
                            
                            var frm;
                            var to=todt.split('/');
                            
                            if(flag==true)
                            {
                               // alert('test1');
                                 if(document.frmEmployee.Date_Of_Birth.value==0 || document.frmEmployee.Date_Of_Birth.value=="")
                                   {
                                        alert("Please Enter or Select Date_Of_Birth");
                                        document.frmEmployee.Date_Of_Reg_Pro.value='';
                                        document.frmEmployee.Date_Of_Birth.focus();
                                        return false;
                                   }
                                fromdt=document.frmEmployee.Date_Of_Birth.value;
                                reason="DOB"
                                frm=fromdt.split('/');
                                 fday=frm[0];
                                 fmon=frm[1];
                                 fyear=frm[2]+18;
                            }
                            else
                            {
                                //alert('test2');
                                 frm=fromdt.split('/');
                                 fday=frm[0];
                                 fmon=frm[1];
                                 fyear=frm[2];
                            }
                            //alert('test3');
                            var tday=to[0];
                            var tmon=to[1];
                            var tyear=to[2];
                            
                            if(fyear>tyear)
                            {
                                alert('Probation Date Should be greater than or equal to '+joindate +" (as per "+reason+")" );
                                //document.Hrm_TransJoinForm.txtDateTo.focus();
                                document.frmEmployee.Date_Of_Reg_Pro.value='';
                                document.frmEmployee.Date_Of_Reg_Pro.focus();
                                return false;
                                return false;
                            }
                            else if(fyear==tyear)
                            {
                                    if(fmon>tmon)
                                    {
                                        alert('Probation Date Should be greater than  or equal to '+joindate+" (as per "+reason+")");
                                        //document.Hrm_TransJoinForm.txtDateTo.focus();
                                         document.frmEmployee.Date_Of_Reg_Pro.value='';
                                         document.frmEmployee.Date_Of_Reg_Pro.focus();
                                        return false;
                                        return false;
                                    }
                                    else if(fmon==tmon)
                                    {
                                            if(fday>tday)
                                            {
                                                 alert('Probation Date Should be greater than or equal to  '+joindate+" (as per "+reason+")");
                                               // document.Hrm_TransJoinForm.txtDateTo.focus();
                                                document.frmEmployee.Date_Of_Reg_Pro.value='';
                                                document.frmEmployee.Date_Of_Reg_Pro.focus();
                                                return false;
                                            }
                                           
                                            
                                    }
                            }
                        }
                            return true;
                
                
                }
    
     
                if(f==true)
                {
                         if(joindate==null || joindate=='empty')
                         {
                            //alert('test');
                               flag=true;
                         }
                         else
                         {
                            var fromdt=joindate;
                            var todt=document.frmEmployee.Date_Of_Reg_Pro.value;
                            
                            var frm;
                            var to=todt.split('/');
                            
                            if(flag==true)
                            {
                               //alert('test1');
                                 if(document.frmEmployee.Date_Of_Birth.value==0 || document.frmEmployee.Date_Of_Birth.value=="")
                                   {
                                        alert("Please Enter or Select Date_Of_Birth");
                                        document.frmEmployee.Date_Of_Reg_Pro.value='';
                                        document.frmEmployee.Date_Of_Birth.focus();
                                        return false;
                                   }
                                fromdt=document.frmEmployee.Date_Of_Birth.value;
                                 reason="DOB"
                                frm=fromdt.split('/');
                                 fday=frm[0];
                                 fmon=frm[1];
                                 fyear=frm[2]+18;
                            }
                            else
                            {
                                //alert('test2');
                                 frm=fromdt.split('/');
                                 fday=frm[0];
                                 fmon=frm[1];
                                 fyear=frm[2];
                            }
                            //alert('test3');
                            var tday=to[0];
                            var tmon=to[1];
                            var tyear=to[2];
                            
                            if(fyear>tyear)
                            {
                                alert('Probation Cleared Date Should be greater than or equal to '+joindate+" (as per "+reason+")");
                                //document.Hrm_TransJoinForm.txtDateTo.focus();
                                 document.frmEmployee.Date_Of_Reg_Pro.value='';
                                document.frmEmployee.Date_Of_Reg_Pro.focus();
                                return false;
                            }
                            else if(fyear==tyear)
                            {
                                    if(fmon>tmon)
                                    {
                                        alert('Probation Cleared Date Should be greater than  or equal to '+joindate+" (as per "+reason+")");
                                        //document.Hrm_TransJoinForm.txtDateTo.focus();
                                         document.frmEmployee.Date_Of_Reg_Pro.value='';
                                        document.frmEmployee.Date_Of_Reg_Pro.focus();
                                        return false;
                                    }
                                    else if(fmon==tmon)
                                    {
                                            if(fday>tday)
                                            {
                                                 alert('Probation Cleared Date Should be greater than or equal to  '+joindate+" (as per "+reason+")");
                                                // document.Hrm_TransJoinForm.txtDateTo.focus();
                                                document.frmEmployee.Date_Of_Reg_Pro.value='';
                                                document.frmEmployee.Date_Of_Reg_Pro.focus();
                                                return false;
                                            }
                                           
                                            
                                    }
                            }
                        }
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
                return false;
        }

}


  
  
  
  function existBasicRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
  //alert(flag);
            if(flag=="NoSROffice")
                    {
                      alert("SR Controlling Office Details is not available");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                    
                    else if(flag=="DifferentSROffice")
                    {
                      alert("This Employee Does not belong to your office");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                    else if(flag=="NoEmployee")
                    {
                       alert("Employee Id Does not Exist Create an Id First ");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                    else if(flag=="EmpDet")
                    {
                       var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                   if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                 
                  document.frmEmployee.Employee_Initial.value=einit;
                  
                   joindate=baseResponse.getElementsByTagName("joindate")[0].firstChild.nodeValue;
                    reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
                    //alert(reason);
                  
                  
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(egpf==0 || egpf==null)
                     egpf='';
                 // else   
                     document.frmEmployee.Gpf_Number.value=egpf;
                    }
                    else if(flag=="success")
                    {
                  var RecordStatus=baseResponse.getElementsByTagName("RecordStatus")[0].firstChild.nodeValue; 
                  //alert("first record status is" + RecordStatus);
                  if(RecordStatus=='FR')
                     {
                       
                       alert("This Record has been Freezed - It is not available to Update");
                       RecordStatus="Freezed";
                       
                       var distr=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                  if(distr==999)
                    {
                       var d=document.getElementById("original");
                d.style.display="none";
                var d=document.getElementById("other");
                d.style.display="block";
                    
                       var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                   if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                     
                     document.frmEmployee.Employee_Initial.value=einit;
                  
                  joindate=baseResponse.getElementsByTagName("joindate")[0].firstChild.nodeValue;
                    reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                   if(egpf==0 || egpf==null)
                     egpf='';  
                     document.frmEmployee.Gpf_Number.value=egpf;
                  
                  var edob=baseResponse.getElementsByTagName("EmpDOB")[0].firstChild.nodeValue; 
                  
                  if(edob==null || edob==0)
                    edob=""; //edob="Not Specified"
                 // else   
                    document.frmEmployee.Date_Of_Birth.value=edob;
                    
                  var g=baseResponse.getElementsByTagName("EmpGender")[0].firstChild.nodeValue; 
                  if(g==null)
                      document.frmEmployee.Gender[0].checked=true;
                  if(g=='M')
                  document.frmEmployee.Gender[0].checked=true;
                  else 
                  document.frmEmployee.Gender[1].checked=true;
                  
                  var comm=baseResponse.getElementsByTagName("Comm")[0].firstChild.nodeValue; 
                  if(comm==0)
                     comm=0;
                  else   
                   document.frmEmployee.Community.value=comm;
                   
                  var distr=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                  if(distr==0)
                      distr=0;
                  else
                     document.frmEmployee.Native_District.value=distr;
                  
                  
                  var qual=baseResponse.getElementsByTagName("EmpQual")[0].firstChild.nodeValue; 
                  if(qual==0 || qual=='Not Specified')
                     qual='';
                // else
                    document.frmEmployee.Qualification.value=qual;
                  
                  var empst=baseResponse.getElementsByTagName("EmpmStatus")[0].firstChild.nodeValue;   
                  if(empst==null)
                      empst=0;
                  document.frmEmployee.Post_Status.value=empst;
                 // alert(empst);
                 
                 if(empst=='REG')
                  {
                       var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='block';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Probation Cleared";
                        
                        var probation=baseResponse.getElementsByTagName("probation")[0].firstChild.nodeValue;   
                        if(probation=='Y')
                        {
                                document.frmEmployee.optprobation[0].checked=true;
                                     var id=document.getElementById("divdate");
                                            id.style.display='block';
                                            
                                     var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                     if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                        }
                        else  if(probation=='N')
                        {
                            document.frmEmployee.optprobation[1].checked=true;
                              var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        else
                        {
                                  var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        
                        
                        
                  }
                  else if(empst=='WCE')
                {
                        var id=document.getElementById("divdate");
                        id.style.display='block';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Regularisation";
                        
                         var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                      if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                }
                else
                {
                    var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                }
                  
                  
                  
                  var empdesi=baseResponse.getElementsByTagName("EmpDesig")[0].firstChild.nodeValue; 
                  if(empdesi==0)
                     empdesi=0;
                  else   
                     document.frmEmployee.Designation.value=empdesi
                  
                  var empes=baseResponse.getElementsByTagName("EmpeStatus")[0].firstChild.nodeValue; 
              // alert(empes);
                  
                     document.frmEmployee.Employee_Status.value=empes;
                 
                 var Marital=baseResponse.getElementsByTagName("EmpMarital")[0].firstChild.nodeValue; 
                 if(Marital==null)
                     document.frmEmployee.Marital_Status[0].checked=true;
                 if(Marital=='M')
                      document.frmEmployee.Marital_Status[0].checked=true;
                  else
                        document.frmEmployee.Marital_Status[1].checked=true;
                        
                  var rema=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue; 
                  if(rema=="Not Specified")
                     rema=" ";
                  else   
                     document.frmEmployee.Remarks.value=rema;
                  
                  var otherstate=baseResponse.getElementsByTagName("OtherState")[0].firstChild.nodeValue; 
                  if(otherstate==null)
                     otherstate="Not Specified";
                  else   
                  document.frmEmployee.txtOtherState.value=otherstate;
                  
                  
                  var other=baseResponse.getElementsByTagName("Others")[0].firstChild.nodeValue; 
                  if(other==null)
                     other="Not Specified";
                  else   
                  document.frmEmployee.txtOther.value=other;
                  
                  
                   var imagepath=baseResponse.getElementsByTagName("ImagePath")[0].firstChild.nodeValue; 
                   if(imagepath=="sample_emp.bmp")
                      {  
                         imagepath="../../../../../images/sample_emp.bmp";
                       document.frmEmployee.EmpImage.src=imagepath;
                       }
                   else
                   {
                //alert(imagepath);
                  // var newimage=imagepath.substr(4,9);
                   
                   document.frmEmployee.EmpImage.src=imgpath+imagepath;
                    }
                    //alert(RecordStatus);
                   document.frmEmployee.cmbRecordStatus.value=RecordStatus;
                  document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value
                  document.frmEmployee.txtEmpId1.focus();
                  document.frmEmployee.txtEmpId1.disabled=true;
                    
                    }
                  else
                  {
                  
                  var d=document.getElementById("original");
                d.style.display="block";
                var d=document.getElementById("other");
                d.style.display="none";
                
                  
                  var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                 
                  if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                     
                     document.frmEmployee.Employee_Initial.value=einit;
                  
                  joindate=baseResponse.getElementsByTagName("joindate")[0].firstChild.nodeValue;
                    reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                   if(egpf==0 || egpf==null)
                     egpf='';   
                     document.frmEmployee.Gpf_Number.value=egpf;
                  
                  var edob=baseResponse.getElementsByTagName("EmpDOB")[0].firstChild.nodeValue; 
                  
                  if(edob==null || edob==0)
                     edob="";//edob="Not Specified"
                 // else   
                    document.frmEmployee.Date_Of_Birth.value=edob;
                    
                  var g=baseResponse.getElementsByTagName("EmpGender")[0].firstChild.nodeValue; 
                  if(g==null)
                      document.frmEmployee.Gender[0].checked=true;
                  if(g=='M')
                  document.frmEmployee.Gender[0].checked=true;
                  else 
                  document.frmEmployee.Gender[1].checked=true;
                  
                  var comm=baseResponse.getElementsByTagName("Comm")[0].firstChild.nodeValue; 
                  if(comm==0)
                     comm=0;
                  else   
                   document.frmEmployee.Community.value=comm;
                   
                  var distr=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                  if(distr==0)
                      distr=0;
                  else
                     document.frmEmployee.Native_District.value=distr;
                  callServer1();
                  
                  
                  var staluk=baseResponse.getElementsByTagName("Taluk")[0].firstChild.nodeValue; 
                  if(staluk==0)
                     staluk=0;
                  else
                    document.frmEmployee.Native_Taluk.value=staluk;
                 // alert("test1:"+staluk);
                  var qual=baseResponse.getElementsByTagName("EmpQual")[0].firstChild.nodeValue; 
                  if(qual==0 || qual=='Not Specified')
                     qual='';
                 // else
                    document.frmEmployee.Qualification.value=qual;
                  
                  var empst=baseResponse.getElementsByTagName("EmpmStatus")[0].firstChild.nodeValue;   
                  if(empst==null)
                      empst=0;
                  document.frmEmployee.Post_Status.value=empst;
                  
                  if(empst=='REG')
                  {
                       var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='block';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Probation Cleared";
                        
                        var probation=baseResponse.getElementsByTagName("probation")[0].firstChild.nodeValue;   
                        if(probation=='Y')
                        {
                                document.frmEmployee.optprobation[0].checked=true;
                                     var id=document.getElementById("divdate");
                                            id.style.display='block';
                                             var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                      if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                        }
                        else  if(probation=='N')
                        {
                            document.frmEmployee.optprobation[1].checked=true;
                              var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        else
                        {
                                  var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        
                        
                        
                  }
                  else if(empst=='WCE')
                {
                        var id=document.getElementById("divdate");
                        id.style.display='block';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Regularisation";
                        
                         var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                      if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                }
                else
                {
                    var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                }
                  
                  
                  
                  var empdesi=baseResponse.getElementsByTagName("EmpDesig")[0].firstChild.nodeValue; 
                  if(empdesi==0)
                     empdesi=0;
                  else   
                     document.frmEmployee.Designation.value=empdesi
                  
                  var empes=baseResponse.getElementsByTagName("EmpeStatus")[0].firstChild.nodeValue; 
                  
                     document.frmEmployee.Employee_Status.value=empes;
                 
                 var Marital=baseResponse.getElementsByTagName("EmpMarital")[0].firstChild.nodeValue; 
                 if(Marital==null)
                     document.frmEmployee.Marital_Status[0].checked=true;
                 if(Marital=='M')
                      document.frmEmployee.Marital_Status[0].checked=true;
                  else
                        document.frmEmployee.Marital_Status[1].checked=true;
                        
                  var rema=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue; 
                  if(rema=="Not Specified")
                     rema=" ";
                  else   
                     document.frmEmployee.Remarks.value=rema;
                  
                  var imagepath=baseResponse.getElementsByTagName("ImagePath")[0].firstChild.nodeValue; 
                   if(imagepath=="sample_emp.bmp")
                      {  
                         imagepath="../../../../../images/sample_emp.bmp";
                       document.frmEmployee.EmpImage.src=imagepath;
                       }
                   else
                   {
                 
                   document.frmEmployee.EmpImage.src=imgpath+imagepath;
                    }
                    //alert(RecordStatus);
                  document.frmEmployee.cmbRecordStatus.value=RecordStatus;
                  document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value
                  document.frmEmployee.txtEmpId1.focus();
                  document.frmEmployee.txtEmpId1.disabled=true;
                  }
                  document.frmEmployee.cmdUpdate.disabled=true;
                       
                       }
                  else   
                    {      
                   
                    if(RecordStatus=='MD')
                  {
                    document.frmEmployee.cmbRecordStatus.value="Modified";
                  }
                  RecordStatus="Modified";
                 
                 var distr=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                  if(distr==999)
                    {
                       var d=document.getElementById("original");
                d.style.display="none";
                var d=document.getElementById("other");
                d.style.display="block";
                    
                       var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                  if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                     document.frmEmployee.Employee_Initial.value=einit;
                  
                  joindate=baseResponse.getElementsByTagName("joindate")[0].firstChild.nodeValue;
                    reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                   
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                   if(egpf==0 || egpf==null)
                     egpf=''; 
                     document.frmEmployee.Gpf_Number.value=egpf;
                  
                  var edob=baseResponse.getElementsByTagName("EmpDOB")[0].firstChild.nodeValue; 
                  
                  if(edob==null || edob==0)
                     edob="";//edob="Not Specified"
                 // else   
                    document.frmEmployee.Date_Of_Birth.value=edob;
                    
                  var g=baseResponse.getElementsByTagName("EmpGender")[0].firstChild.nodeValue; 
                  if(g==null)
                      document.frmEmployee.Gender[0].checked=true;
                  if(g=='M')
                  document.frmEmployee.Gender[0].checked=true;
                  else 
                  document.frmEmployee.Gender[1].checked=true;
                  
                  var comm=baseResponse.getElementsByTagName("Comm")[0].firstChild.nodeValue; 
                  if(comm==0)
                     comm=0;
                  else   
                   document.frmEmployee.Community.value=comm;
                   
                  var distr=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                  if(distr==0)
                      distr=0;
                  else
                     document.frmEmployee.Native_District.value=distr;
                  
                  
                  var qual=baseResponse.getElementsByTagName("EmpQual")[0].firstChild.nodeValue; 
                  if(qual==0 || qual=='Not Specified')
                     qual='';
                  //else
                    document.frmEmployee.Qualification.value=qual;
                 
                  var empst=baseResponse.getElementsByTagName("EmpmStatus")[0].firstChild.nodeValue;   
                  if(empst==null)
                      empst=0;
                  document.frmEmployee.Post_Status.value=empst;
                  
                  if(empst=='REG')
                  {
                       var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='block';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Probation Cleared";
                        
                        var probation=baseResponse.getElementsByTagName("probation")[0].firstChild.nodeValue;   
                        if(probation=='Y')
                        {
                                document.frmEmployee.optprobation[0].checked=true;
                                     var id=document.getElementById("divdate");
                                            id.style.display='block';
                                             var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                     if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                        }
                        else  if(probation=='N')
                        {
                            document.frmEmployee.optprobation[1].checked=true;
                              var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        else
                        {
                                  var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        
                        
                        
                  }
                  else if(empst=='WCE')
                {
                        var id=document.getElementById("divdate");
                        id.style.display='block';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Regularisation";
                        
                         var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                      if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                }
                else
                {
                    var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                }
                  
                  
                 
                  var empdesi=baseResponse.getElementsByTagName("EmpDesig")[0].firstChild.nodeValue; 
                  if(empdesi==0)
                     empdesi=0;
                  else   
                     document.frmEmployee.Designation.value=empdesi
                  
                  var empes=baseResponse.getElementsByTagName("EmpeStatus")[0].firstChild.nodeValue; 
                   
                     document.frmEmployee.Employee_Status.value=empes;
                 
                 var Marital=baseResponse.getElementsByTagName("EmpMarital")[0].firstChild.nodeValue; 
                 if(Marital==null)
                     document.frmEmployee.Marital_Status[0].checked=true;
                 if(Marital=='M')
                      document.frmEmployee.Marital_Status[0].checked=true;
                  else
                        document.frmEmployee.Marital_Status[1].checked=true;
                        
                  var rema=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue; 
                  if(rema=="Not Specified")
                     rema=" ";
                  else   
                     document.frmEmployee.Remarks.value=rema;
                  
                  var otherstate=baseResponse.getElementsByTagName("OtherState")[0].firstChild.nodeValue; 
                  if(otherstate==null)
                     otherstate="Not Specified";
                  else   
                  document.frmEmployee.txtOtherState.value=otherstate;
                  
                  
                  var other=baseResponse.getElementsByTagName("Others")[0].firstChild.nodeValue; 
                  if(other==null)
                     other="Not Specified";
                  else   
                  document.frmEmployee.txtOther.value=other;
                  
                  
                   var imagepath=baseResponse.getElementsByTagName("ImagePath")[0].firstChild.nodeValue; 
                   if(imagepath=="sample_emp.bmp")
                      {  
                         imagepath="../../../../../images/sample_emp.bmp";
                       document.frmEmployee.EmpImage.src=imagepath;
                       }
                   else
                   {
               
                   document.frmEmployee.EmpImage.src=imgpath+imagepath;
                    }
                    //alert(RecordStatus);
                  document.frmEmployee.cmbRecordStatus.value=RecordStatus;
                  document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value
                  document.frmEmployee.txtEmpId1.focus();
                  document.frmEmployee.txtEmpId1.disabled=true;
                    
                    }
                  else
                  {
                  
                  var d=document.getElementById("original");
                d.style.display="block";
                var d=document.getElementById("other");
                d.style.display="none";
                
                  
                  var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                 
                  if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                 
                  document.frmEmployee.Employee_Initial.value=einit;
                  
                  joindate=baseResponse.getElementsByTagName("joindate")[0].firstChild.nodeValue;
                    reason=baseResponse.getElementsByTagName("reason")[0].firstChild.nodeValue;
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                   if(egpf==0 || egpf==null)
                     egpf='';
                     document.frmEmployee.Gpf_Number.value=egpf;
                  
                  var edob=baseResponse.getElementsByTagName("EmpDOB")[0].firstChild.nodeValue; 
                  
                  if(edob==null || edob==0)
                     edob=""
                  //else   
                    document.frmEmployee.Date_Of_Birth.value=edob;
                    
                  var g=baseResponse.getElementsByTagName("EmpGender")[0].firstChild.nodeValue; 
                  if(g==null)
                      document.frmEmployee.Gender[0].checked=true;
                  if(g=='M')
                  document.frmEmployee.Gender[0].checked=true;
                  else 
                  document.frmEmployee.Gender[1].checked=true;
                  
                  var comm=baseResponse.getElementsByTagName("Comm")[0].firstChild.nodeValue; 
                  if(comm==0)
                     comm=0;
                  else   
                   document.frmEmployee.Community.value=comm;
                   
                  var distr=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                  if(distr==0)
                      distr=0;
                  else
                     document.frmEmployee.Native_District.value=distr;
                  callServer1();
                  
                  var staluk=baseResponse.getElementsByTagName("Taluk")[0].firstChild.nodeValue; 
                  if(staluk==0)
                     staluk=0;
                  else
                    document.frmEmployee.Native_Taluk.value=staluk;
                  //alert("test2:"+staluk);
                  var qual=baseResponse.getElementsByTagName("EmpQual")[0].firstChild.nodeValue; 
                  if(qual==0 || qual=='Not Specified')
                     qual='';
                 // else
                    document.frmEmployee.Qualification.value=qual;
                  
                  var empst=baseResponse.getElementsByTagName("EmpmStatus")[0].firstChild.nodeValue;   
                  if(empst==null)
                      empst=0;
                  document.frmEmployee.Post_Status.value=empst;
                  
                  
                  
                  if(empst=='REG')
                  {
                       var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='block';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Probation Cleared";
                        
                        var probation=baseResponse.getElementsByTagName("probation")[0].firstChild.nodeValue;   
                        if(probation=='Y')
                        {
                                document.frmEmployee.optprobation[0].checked=true;
                                     var id=document.getElementById("divdate");
                                            id.style.display='block';
                                       var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                      if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                        }
                        else  if(probation=='N')
                        {
                            document.frmEmployee.optprobation[1].checked=true;
                              var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        else
                        {
                                  var id=document.getElementById("divdate");
                                            id.style.display='none';
                        }
                        
                        
                        
                  }
                  else if(empst=='WCE')
                {
                        var id=document.getElementById("divdate");
                        id.style.display='block';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                        
                         var id=document.getElementById("divcaption");
                        id.innerText="Date of Regularisation";
                        
                         var dateOfRegPro=baseResponse.getElementsByTagName("dateOfRegPro")[0].firstChild.nodeValue;   
                                      if(dateOfRegPro=='0')
                                      document.frmEmployee.Date_Of_Reg_Pro.value="";
                                      else
                                         document.frmEmployee.Date_Of_Reg_Pro.value=dateOfRegPro;
                }
                else
                {
                    var id=document.getElementById("divdate");
                        id.style.display='none';
                        
                        var id=document.getElementById("divprobation");
                        id.style.display='none';
                }
                  
                  
                  
                  var empdesi=baseResponse.getElementsByTagName("EmpDesig")[0].firstChild.nodeValue; 
                  if(empdesi==0)
                     empdesi=0;
                  else   
                     document.frmEmployee.Designation.value=empdesi
                  
                  var empes=baseResponse.getElementsByTagName("EmpeStatus")[0].firstChild.nodeValue; 
                 
                    
                     document.frmEmployee.Employee_Status.value=empes;
                 
                 var Marital=baseResponse.getElementsByTagName("EmpMarital")[0].firstChild.nodeValue; 
                 if(Marital==null)
                     document.frmEmployee.Marital_Status[0].checked=true;
                 if(Marital=='M')
                      document.frmEmployee.Marital_Status[0].checked=true;
                  else
                        document.frmEmployee.Marital_Status[1].checked=true;
                        
                  var rema=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue; 
                  if(rema=="Not Specified")
                     rema=" ";
                  else   
                     document.frmEmployee.Remarks.value=rema;
                  
                  var imagepath=baseResponse.getElementsByTagName("ImagePath")[0].firstChild.nodeValue; 
                   if(imagepath=="sample_emp.bmp")
                      {  
                         imagepath="../../../../../images/sample_emp.bmp";
                       document.frmEmployee.EmpImage.src=imagepath;
                       }
                   else
                   {
                   
                   document.frmEmployee.EmpImage.src=imgpath+imagepath;
                    }
                    //alert(RecordStatus);
                  document.frmEmployee.cmbRecordStatus.value=RecordStatus;
                  document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value
                  document.frmEmployee.txtEmpId1.focus();
                  document.frmEmployee.txtEmpId1.disabled=true;
                  document.frmEmployee.cmdUpdate.disabled=false;
                  }
                 } 
                    }
              else
             {
                     alert("Failed to Load Values");
             }
}

  
 

function updateRow(baseResponse)
{
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              {
                 
                 var EmpId=document.frmEmployee.EmpId.value;
               
                 if(confirm("Records are updated Successfully - Do You want to Add or Update Your Photo"))
                 {
                     my_window= window.open("ImageSession.jsp?Command=init&Emp="+EmpId,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
                     my_window.moveTo(250,250); 
                     my_window.focus();
                     
                 }
                 
                 
                  clearAll();
                  try{
                  if (my_window && my_window.open && !my_window.closed)  my_window.focus();
                }catch(e){}
              }
              
              else
              {
                 alert("Failed to Update Records");
              }
}    



function clearAll()
{
                   document.frmEmployee.txtEmpId1.value="";
                   document.frmEmployee.Employee_Prefix.selectedIndex=0;
                   document.frmEmployee.Employee_Prefix.value='';
                   document.frmEmployee.Employee_Initial.value="";
                   document.frmEmployee.Employee_Name.value="";
                   document.frmEmployee.Gpf_Number.value="";
                  document.frmEmployee.Date_Of_Birth.value="";
                  document.frmEmployee.Qualification.value="";
                  document.frmEmployee.Community.selectedIndex=0;
                  document.frmEmployee.Native_District.selectedIndex=0;
                  document.frmEmployee.Native_Taluk.innerHTML="";
                  document.frmEmployee.cmbRecordStatus.value="";
        
         var taluk=document.createElement("OPTION");
        taluk.text="-- Select Taluk --";
        taluk.value="0";
        try
        {
        document.frmEmployee.Native_Taluk.add(taluk);
         }
         catch(e)
         {
         document.frmEmployee.Native_Taluk.add(taluk,null);
         }
               document.frmEmployee.Native_Taluk.value="0";
               var original=document.getElementById("original");
               original.style.display="block";
               var other=document.getElementById("other");
               other.style.display="none";
                  document.frmEmployee.txtOther.value="";
                  document.frmEmployee.Qualification.selectedIndex=0;
                  document.frmEmployee.Post_Status.selectedIndex=0;
                  document.frmEmployee.Date_Of_Twad.value="";
                  document.frmEmployee.Designation.selectedIndex=0;
                  document.frmEmployee.Employee_Status.selectedIndex=0;
                  document.frmEmployee.Remarks.value="";
                  document.frmEmployee.txtEmpId1.disabled=false;
                  document.frmEmployee.cmdUpdate.disabled=false;
                  document.frmEmployee.EmpImage.src=DefaultImage;
            
            
             var id=document.getElementById("divdate");
                    id.style.display='none';
                    
                    var id=document.getElementById("divprobation");
                    id.style.display='none';
                    
                    document.frmEmployee.Date_Of_Reg_Pro.value="";
                  
                  document.frmEmployee.txtEmpId1.focus();
                  
                  
}

function nullCheck(n)
{
   if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value.length<=0))
    {
         alert("Please Enter EmpId ");
         document.frmEmployee.txtEmpId1.focus();
         return false;
    }
  
   if(document.frmEmployee.Date_Of_Birth.value==0 || document.frmEmployee.Date_Of_Birth.value=="")
   {
     alert("Please Enter or Select Date_Of_Birth");
         document.frmEmployee.Date_Of_Birth.focus();
         return false;
   }
   else
   {
        checkdt( document.frmEmployee.Date_Of_Birth);
   }
   
    if((document.frmEmployee.Date_Of_Birth.value=="") || (document.frmEmployee.Date_Of_Birth.value.length<=0))
    {
         alert("Please Enter or Select Date_Of_Birth");
         document.frmEmployee.Date_Of_Birth.focus();
         return false;
    }
      if((document.frmEmployee.Community.value==0) || (document.frmEmployee.Community.value.length==0))
    {
         alert("Please Select Community");
         document.frmEmployee.Community.focus();
         return false;
    }
    if((document.frmEmployee.Native_District.value==0) || (document.frmEmployee.Native_District.value.length==0))
    {
         alert("Please Select Native_District");
         document.frmEmployee.Native_District.focus();
         return false;
    }
    else  if((document.frmEmployee.Native_District.value==999))
    {
        if((document.frmEmployee.txtOtherState.value==0) || (document.frmEmployee.txtOtherState.value.length==0))
        {
             alert("Please Enter Other State");
             document.frmEmployee.txtOtherState.focus();
             return false;
        } 
        if((document.frmEmployee.txtOther.value==0) || (document.frmEmployee.txtOther.value.length==0))
        {
             alert("Please Enter Other District");
             document.frmEmployee.txtOther.focus();
             return false;
        } 
    }
  //alert(document.frmEmployee.Native_District.value);
     if((document.frmEmployee.Qualification.value=="") || (document.frmEmployee.Qualification.value<=0))
    {
         alert("Please enter Qualification");
         document.frmEmployee.Qualification.focus();
         return false;
    }
    
  /*    if((document.frmEmployee.Employee_Status.value==0) || (document.frmEmployee.Employee_Status.value.length==0))
    {
         alert("Please Select Employee_Status");
         document.frmEmployee.Employee_Status.focus();
         return false;
    }*/
    
    if((document.frmEmployee.Date_Of_Reg_Pro.value!="") && (document.frmEmployee.Designation.value.Date_Of_Reg_Pro!=0))
    {
        
        return checkdt1();
        //return checkdtPro(document.frmEmployee.Date_Of_Reg_Pro);
    }
    
     if((document.frmEmployee.Post_Status.value=="NoVal") || (document.frmEmployee.Post_Status.value.length==0))
    {
         alert("Please select Post_Status");
         document.frmEmployee.Post_Status.focus();
         return false;
    }
    
   
   
  /* if((document.frmEmployee.Designation.value==0) || (document.frmEmployee.Designation.value.length==0))
    {
         alert("Please Select Designation");
         document.frmEmployee.Designation.focus();
         return false;
    }*/
   
   
    
    return true;
  
}

function toFocus()
{
 
  if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     document.frmEmployee.txtEmpId1.focus();
     return false;
  }
   
}

     
//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}


function checkdtPro(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
          var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
          t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
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
        
        }
        if( f==true)
        {
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
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
           
            return false
    }
    
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


function checkdt(t)
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
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            if(currentYear > getCurrentYear()-15  || currentYear<getCurrentYear()-60)
            {
                    alert('Given date is not acceptable');
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
      
        }
        if( f==true)
        {
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            if(currentYear > getCurrentYear()-15  || currentYear<getCurrentYear()-60)
            {
            
                    //alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    alert('Given date is not acceptable');
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
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
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
     
     
     function changestatus(v)
     {
            //var status=document.frmEmployee.Post_Status.value;
            if(v=='WCE')
            {
                    var id=document.getElementById("divdate");
                    id.style.display='block';
                    
                    var id=document.getElementById("divprobation");
                    id.style.display='none';
                    
                     var id=document.getElementById("divcaption");
                    id.innerText="Date of Regularisation";
                    
                    document.frmEmployee.optprobation[0].checked=false;
                    document.frmEmployee.optprobation[1].checked=false;
                    document.frmEmployee.Date_Of_Reg_Pro.value="";
                    
                    
            }
            else if(v=='REG')
            {
                    var id=document.getElementById("divdate");
                    id.style.display='none';
                    
                    var id=document.getElementById("divprobation");
                    id.style.display='block';
                    
                     var id=document.getElementById("divcaption");
                    id.innerText="Date of Probation Cleared";
                    
                    document.frmEmployee.optprobation[0].checked=false;
                    document.frmEmployee.optprobation[1].checked=false;
                    document.frmEmployee.Date_Of_Reg_Pro.value="";
            }
            else
            {
                var id=document.getElementById("divdate");
                    id.style.display='none';
                    
                    var id=document.getElementById("divprobation");
                    id.style.display='none';
                    
                    document.frmEmployee.optprobation[0].checked=false;
                    document.frmEmployee.optprobation[1].checked=false;
                    document.frmEmployee.Date_Of_Reg_Pro.value="";
            }
     
     }
     
     
     function clickProbation()
     {
     
        if(document.frmEmployee.optprobation[0].checked==true)
        {
                 var id=document.getElementById("divdate");
                    id.style.display='block';
        }
        else
        {
                 var id=document.getElementById("divdate");
                    id.style.display='none';
        }
     
     }