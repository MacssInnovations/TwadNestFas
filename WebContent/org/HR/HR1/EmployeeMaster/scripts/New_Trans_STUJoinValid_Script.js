var maxtodate;
var jobflag;
//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
     if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,600);
       winjob.moveTo(200,200); 
       winjob.focus();
       return;

    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_for_SR","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(200,200);  
 
    winjob.focus();
     
}

function forChildOption()
{
  
      if (winjob && winjob.open && !winjob.closed)
      {
       
         winjob.officeSelection(true,true,true,false);
       }
   
}

function doParentJob(jobid,deptid)
{
   
        //alert(jobflag);
         document.frmEmployee.txtDept_Id_work.value=deptid;
        document.frmEmployee.txtOffice_Id.value=jobid ;
        doFunction('Load',true);
        return true
    

}

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

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var winjoin;
//alert('asdfasdfasdfkkk');
function servicepopup1()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
         return ;
    }
    else
    {
     winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpStudyJoiningPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    }
    
}

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////

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
        
    winemp= window.open("EmpStudyJoiningPopup.jsp","mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callServer1('Load','null');

}


//**************************************************************************//

function Joinpopup()
{
    
    if(document.frmEmployee.txtEmpId1.value.length==0)
    {
        alert("Enter the Employee Id ");
        document.frmEmployee.txtEmpId1.focus();
        return false;
    }
    if (winjoin && winjoin.open && !winjoin.closed) 
    {
       winjoin.resizeTo(500,500);
       winjoin.moveTo(250,250); 
       winjoin.focus();
        return ;
    }
    else
    {
        winjoin=null
    }
        
     var url="JoinValidate_popup.jsp?eid="+document.frmEmployee.txtEmpId1.value;
    winjoin= window.open(url,"mywindow2","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjoin.moveTo(250,250);  
    winjoin.focus();
    
}

function doParentJoin(slno)
{

document.frmEmployee.txtJRId.value=slno;
//alert(slno);
callServer1('LoadSer','null');
winjoin.close();
}
//********************************************************************************************************************************
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjoin && winjoin.open && !winjoin.closed) winjoin.close();
}

 /*function getDesignation1()
    {
   
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
       // alert("test" + type);
        if(type!=0)
        {
        
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.frmEmployee.cmbdes.style.visibility="visible";
            loadOfficesByType1(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.frmEmployee.cmbdes.style.visibility="hidden";
        }
    }
    
      function loadOfficesByType1(type)
    {
        //alert(type);
        startwaiting(document.frmEmployee) ;
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation1(req);
        }
        req.send(null);
    }
    
function  loadDesignation1(req)
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

*/



function doFunction(Command,param)
{
   if(Command=="Load")
    {
      var  Office_Id;
      var   Dept_Id;
      
            // alert("load office->");
             if(param==true)
            {
            
                jobflag=true;
                Dept_Id=document.getElementById("txtDept_Id_work").value;
               Office_Id =document.getElementById("txtOffice_Id").value;
                 url="../../../../../HrmTransDPNJoinServ.view?Command=LoadSROffice&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
                 //alert(url);
              
                 var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
               
                processResponse(req);
              
                }
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                
            }
            
            
            else if(param==false)
            {
                 jobflag=false;
               Dept_Id= document.Hrm_TransJoinForm.txtDepId.value;
               Office_Id =document.Hrm_TransJoinForm.txtDepOffId.value;
                 url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id; 
               // alert(url)
                 
                 var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
               
                processResponse1(req);
              
                }
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
            }
               // alert(Office_Id +'   '+Dept_Id);
          
               // alert(url);
                
         }       
          

               
}




function callServer1(command,param)
 {
 
       if(command=="Load")
         { 
                var strEmpName=document.frmEmployee.txtEmpId1.value;
                document.frmEmployee.Employee_Name.value="";
                document.frmEmployee.Gpf_Number.value="";
                document.frmEmployee.Date_Of_Birth.value="";
           
                    startwaiting(document.frmEmployee) ;
                    url="../../../../../Employee_STUJoinReportValid_Servlet.view?command=Load&EName=" + strEmpName;
                    var req=getTransport();
                    //alert(url);
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                    handleResponse(req);
                    }
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                }
                
        if(command=="Load1")
         { 
                var strEmpName=document.frmEmployee.txtEmpId1.value;
                document.frmEmployee.Employee_Name.value="";
                document.frmEmployee.Gpf_Number.value="";
                document.frmEmployee.Date_Of_Birth.value="";
           
                    startwaiting(document.frmEmployee) ;
                    url="../../../../../Employee_STUJoinReportValid_Servlet.view?command=Load1&EName=" + strEmpName;
                    var req=getTransport();
                    alert(url);
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                    handleResponse(req);
                    }
                     if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                }
             if(command=="LoadEmp")
         { 
           
                var strEmpName=document.frmEmployee.txtEmpId1.value;
                document.frmEmployee.Employee_Name.value="";
                document.frmEmployee.Gpf_Number.value="";
                document.frmEmployee.Date_Of_Birth.value="";
           
                    startwaiting(document.frmEmployee) ;
                    url="../../../../../Employee_STUJoinReportValid_Servlet.view?command=LoadEmp&EName=" + strEmpName;
                    alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                    
                        handleResponse(req);
                    }
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                }
                 if(command=="LoadSer")
         { 
                
                var strSerid=document.frmEmployee.txtJRId.value;
                var strEmpName=document.frmEmployee.txtEmpId1.value;
                document.frmEmployee.Employee_Name.value="";
                document.frmEmployee.Gpf_Number.value="";
                document.frmEmployee.Date_Of_Birth.value="";
           
                    startwaiting(document.frmEmployee) ;
                    url="../../../../../Employee_STUJoinReportValid_Servlet.view?command=LoadSer&txtser=" + strSerid+"&EName=" + strEmpName;
                   // alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                }
                
                
           if(command=="Update")
            {
            
                  var flagg=nullch();
                    if(flagg==true)
                    {
                             var strEmpName=document.frmEmployee.txtEmpId1.value;
                             var strJdate=document.frmEmployee.txtDOJ.value;
                             var a=strJdate.split("/");
                             var strYear=a[2];
                             var strOffId=document.frmEmployee.txtOffId.value;
                             var strJoinId=document.frmEmployee.txtJRId.value;
                             //var strProcessId=document.frmEmployee.comProcFlowId.value;
                             var strProcessId='FR';
                             var radvalue;
                           if(document.frmEmployee.radFNAN[0].checked==true)
                           {
                                    radvalue=document.frmEmployee.radFNAN[0].value;
                           }
                           else
                           {
                                    radvalue=document.frmEmployee.radFNAN[1].value;
                           }
                         //var design=document.frmEmployee.cmbdes.value;
                         //var postcount=document.frmEmployee.comPostTow.value;
                         var rem=document.frmEmployee.txtRemarks.value;
                         
                         var empstatus=document.frmEmployee.txtempstatus.value;
                        var txtInstName=document.frmEmployee.txtInstName.value;
                         var txtInstLocation=document.frmEmployee.txtInstLocation.value;
                         var txtCourseName=document.frmEmployee.txtCourseName.value;
      
                var sr_cntrl_offid=document.frmEmployee.txtOffice_Id.value;
                        //var url="../../../../../Employee_STUJoinReportValid_Servlet.view?command=Update&txtOffId="+strOffId+"&txtEmpId="+strEmpName+"&txtDOJ="+strYear+"&radFNAN="+radvalue+"&cmbdes="+design+"&comPostTow="+postcount+"&txtRemarks="+rem+"&JoinId="+strJoinId+"&comProcFlowId="+strProcessId+"&txtjoindate="+strJdate+"&empstatus="+empstatus;//+"&compdate="+compdate+"&compsession="+compsession;
                        var url="../../../../../Employee_STUJoinReportValid_Servlet.view?command=Update&txtOffId="+strOffId+"&txtEmpId="+strEmpName+"&txtDOJ="+strYear+"&radFNAN="+radvalue+"&txtRemarks="+rem+"&JoinId="+strJoinId+"&comProcFlowId="+strProcessId+"&txtjoindate="+strJdate+"&empstatus="+empstatus;
                       url=url+"&txtInstName="+txtInstName+"&txtInstLocation="+txtInstLocation+"&txtCourseName="+txtCourseName;
                         url=url+"&sr_cntrl_offid="+sr_cntrl_offid; 
                         var req=getTransport();
                         
                        req.open("GET",url,true); 
                        req.onreadystatechange=function()
                         {
                             handleResponse(req);
                          }   
                            if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                   
                }
            
        }   
        
        
        else if(command=="Delete")
        {
                    var strEmpName=document.frmEmployee.txtEmpId1.value;
                     var strJdate=document.frmEmployee.txtDOJ.value;
                     var a=strJdate.split("/");
                     var strYear=a[2];
                     var strOffId=document.frmEmployee.txtOffId.value;
                     var strJoinId=document.frmEmployee.txtJRId.value;
                     
           if(confirm("Do You Really want to Delete it"))
                        {
                        startwaiting(document.frmEmployee) ;
            url="../../../../../Employee_STUJoinReportValid_Servlet.view?command=Delete&txtEmpId="+strEmpName+"&JoinId="+strJoinId+"&txtOffId="+strOffId+"&txtDOJ="+strYear;
            //req.abort();
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
            handleResponse(req);
            }
            if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
            }
        }
 
 }
 
 function handleResponse(req)
    {   
    if(req.readyState==4)
        {
        
          if(req.status==200)
          {      
          stopwaiting(document.frmEmployee) ;
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            //alert(baseResponse);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
             
             if(command=="Load")
              {
              loadRow(baseResponse);
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
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="Delete")
              {
              deleteRow(baseResponse);
              }
              else if(command=="LoadEmp")
              {
              // alert('load emp');
              loadEmpRow(baseResponse);
              }
              else if(command=="LoadSer")
              {
              //alert('hello');
              
              loadRow(baseResponse);
              }
              
          }
        }
  }
  
  function loadRow(baseResponse)
 {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
                  if(flag=="success")
                  {
                          var empid=baseResponse.getElementsByTagName("Emp_Id");
                          
                         for(j=0;j<empid.length;j++)
                         {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EmpName"); 
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf");
                              var jdate=baseResponse.getElementsByTagName("JDate");
                               var dtbirth=baseResponse.getElementsByTagName("Dtofbirth");
                                var joinid=baseResponse.getElementsByTagName("JoinId");
                                 var noon=baseResponse.getElementsByTagName("Noon");
                                 // var desigid=baseResponse.getElementsByTagName("DesigId");
                                  // var postid=baseResponse.getElementsByTagName("PostId");
                                    var remarks=baseResponse.getElementsByTagName("Remarks");
                                    var sgroup=baseResponse.getElementsByTagName("ServGroup");
                              var ProcId=baseResponse.getElementsByTagName("ProcId");
                            //alert('test');
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tdtbirth=dtbirth.item(j).firstChild.nodeValue;
                            var tjdate=jdate.item(j).firstChild.nodeValue;
                            var tempgpf=empgpf.item(j).firstChild.nodeValue;
                            var tjoinid=joinid.item(j).firstChild.nodeValue;
                           // var tdesignId=desigid.item(j).firstChild.nodeValue;
                            var tnoon=noon.item(j).firstChild.nodeValue;
                            //var tpostid=postid.item(j).firstChild.nodeValue;
                            var tremarks=remarks.item(j).firstChild.nodeValue;
                           //  var servgrp=sgroup.item(j).firstChild.nodeValue;
                             var ProcessId=ProcId.item(j).firstChild.nodeValue;
                             maxtodate=baseResponse.getElementsByTagName("maxtodate")[0].firstChild.nodeValue;
                            
                             
                            if(tjdate==0)
                            {
                               document.frmEmployee.txtDOJ.value=0;
                             }
                             else
                               {
                                document.frmEmployee.txtDOJ.value=tjdate;
                                document.frmEmployee.txtDOJ1.value=tjdate;
                                  //document.frmEmployee.txtDOJ.disabled=true;
                               }
                            
                             if(tdtbirth==0 || tdtbirth=='Not Specified')
                                    tdtbirth="";

                            document.frmEmployee.Date_Of_Birth.value=tdtbirth;
                            document.frmEmployee.txtJRId.value=tjoinid;
                            document.frmEmployee.Employee_Name.value=tempname;
                             if(tempgpf==0)
                                tempgpf="";
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                            
                           // document.frmEmployee.comPostTow.value=tpostid;
                             if(tremarks ==null || tremarks=='null')
                                tremarks="";
                            document.frmEmployee.txtRemarks.value=tremarks;
                            document.frmEmployee.comProcFlowId.value=ProcessId;
                            if(ProcessId=="FR")
                            {
                                
                                document.frmEmployee.butSub.disabled=true;
                                alert("SORRY CANNOT UPDATE FREEZE RECORD..");
                            }
                             document.frmEmployee.butSub.disabled=false;
                            
                           
                               if(tnoon=='FN')
                                 document.frmEmployee.radFNAN[0].checked=true;
                             else
                                 document.frmEmployee.radFNAN[1].checked=true;
                              
                          }
                          
                          
                                var inst_name=baseResponse.getElementsByTagName("inst_name")[0].firstChild.nodeValue;
                                var inst_location=baseResponse.getElementsByTagName("inst_location")[0].firstChild.nodeValue;
                                var course_name=baseResponse.getElementsByTagName("course_name")[0].firstChild.nodeValue;
                                 if(inst_name=='null')
                                            inst_name='';
                                if(inst_location=='null')
                                            inst_location='';
                                if(course_name=='null')
                                            course_name='';
                                document.frmEmployee.txtStuDoj.value=maxtodate;
                                document.frmEmployee.txtInstName.value=inst_name;
                                document.frmEmployee.txtInstLocation.value=inst_location;
                                document.frmEmployee.txtCourseName.value=course_name;

                                 var sr_ctrl_office_id=baseResponse.getElementsByTagName("sr_ctrl_office_id")[0].firstChild.nodeValue;
                                  document.frmEmployee.txtOffice_Id.value=sr_ctrl_office_id;
                                  doFunction('Load',true);

                       }
                      
                     else if(flag=="NoRecord")
                     {
                     
                             var empname=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;  
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                               var dtbirth=baseResponse.getElementsByTagName("Dtofbirth")[0].firstChild.nodeValue; 
                                   if(dtbirth=="")
                                        dtbirth="0";
                                   else
                                       dtbirth=dtbirth;
                            document.frmEmployee.Date_Of_Birth.value=dtbirth;
                            document.frmEmployee.Employee_Name.value=empname;
                            document.frmEmployee.Gpf_Number.value=empgpf;
                     }
                     else if(flag=="NoValue")
                    {
                      alert("Employee Id Does not Exists - Create an Id for the Employee First");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                    /* else  if(flag=="failure2")
                     {
                      alert("Record does not exist. First Crate a new Relieval Record");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                     }*/
                     else if(flag=="failure")
                    {
                        alert("Invalid Employee Id");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else if(flag=="failure2")
                    {
                        alert("This Employee  does not have joining Record to Validate.\n");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else if(flag=="failure3")
                    {
                        alert("This Employee does not have Deputation joining Record.\n");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                    else if(flag=="failure1")
                    {
                        alert("This Employee does not have a post.");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else
                     {
                      alert("Record does not exist.");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                     }
}     

function loadEmpRow(baseResponse)
 {
                //alert('hello');
                
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                  if(flag=="success")
                  {
                          var empid=baseResponse.getElementsByTagName("Emp_Id");
                          
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EmpName"); 
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf");
                              
                              var dtbirth=baseResponse.getElementsByTagName("Dtofbirth");
                                
                            
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tdtbirth=dtbirth.item(j).firstChild.nodeValue;
                            var tempgpf=empgpf.item(j).firstChild.nodeValue;
                                                       
                            document.frmEmployee.Date_Of_Birth.value=tdtbirth;
                            document.frmEmployee.Employee_Name.value=tempname;
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                            
                    }    
                }
                   
                      
                     else if(flag=="NoRecord")
                     {
                     
                             var empname=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;  
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                               var dtbirth=baseResponse.getElementsByTagName("Dtofbirth")[0].firstChild.nodeValue; 
                                   if(dtbirth=="")
                                        dtbirth="0";
                                   else
                                       dtbirth=dtbirth;
                            document.frmEmployee.Date_Of_Birth.value=dtbirth;
                            document.frmEmployee.Employee_Name.value=empname;
                            document.frmEmployee.Gpf_Number.value=empgpf;
                     }
                     else if(flag=="NoValue")
                    {
                      alert("Employee Id Does not Exists - Create an Id for the Employee First");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else if(flag=="failure1")
                    {
                        alert("This Employee already has a post.\nCann't create a post for this employee");
                        
                        document.frmEmployee.Date_Of_Birth.value="";
                        document.frmEmployee.Employee_Name.value="";
                        document.frmEmployee.Gpf_Number.value="";
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else
                     {
                      alert("Record does not exist. Insert a new Record");
                     }
}     


function updateRow(baseResponse)
{ 
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                    //alert("Records are validated successfully");
                    //clearAll();
                             var b;
                            var msg="Records are validated successfully";
                            var head="Validate Joining Report Details";
                           b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
                           
                           b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
                           b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
                           b=b+"</table></td></tr></table></form>";
                           var bid=document.getElementById("bodyid");
                            try{ bid.innerHTML=b;
                           }catch(e){
                            bid.innerText="";
                           }
                  }
                  else
                  {
                                if(baseResponse.getElementsByTagName("flag").length>1)
                                {
                                        //alert(baseResponse.getElementsByTagName("flag")[1].firstChild.nodeValue);
                                        var b;
                                        var msg=baseResponse.getElementsByTagName("flag")[1].firstChild.nodeValue;
                                        var head="Validate Joining Report Details";
                                       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
                                       
                                       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
                                       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
                                       b=b+"</table></td></tr></table></form>";
                                       var bid=document.getElementById("bodyid");
                                        try{ bid.innerHTML=b;
                                       }catch(e){
                                        bid.innerText="";
                                       }
                                }
                                else
                                {
                                   // alert("Failed to validated");
                                    var b;
                                    var msg="Failed to validated";
                                    var head="Validate Joining Report Details";
                                   b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
                                   
                                   b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
                                   b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
                                   b=b+"</table></td></tr></table></form>";
                                   var bid=document.getElementById("bodyid");
                                    try{ bid.innerHTML=b;
                                   }catch(e){
                                    bid.innerText="";
                                   }
                              }
                 }  
}

//Date validation
function validate_date(formName, textName)
 {
 
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    eval('document.' + formName + '.' + textName + '.focus()');
                    eval('document.' + formName + '.' + textName + '.select()');
                    
                    return false;
                }
                
                 return true;  
                     
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
     
function toFocus()
{
 //alert("test");
  //var FirstField=document.frmEmployee.txtEmpId1.value;
  if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value<=0))
  {
     alert("Please Select Employee Id ");
     document.frmEmployee.txtEmpId1.focus();
     return false;
  }
  return true;
   
}
     
//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

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

function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                      alert("Records are Deleted"); 
                       clearAll();
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }
  
function clearAll()
{
document.frmEmployee.Date_Of_Birth.value="";
document.frmEmployee.txtEmpId1.value="";
document.frmEmployee.Employee_Name.value="";
document.frmEmployee.Gpf_Number.value="";
document.frmEmployee.txtJRId.value="";
document.frmEmployee.txtJR.value="";
document.frmEmployee.txtDOJ.value="";
document.frmEmployee.txtDOJ1.value="";
//document.frmEmployee.cmbsgroup.value="";
//document.frmEmployee.cmbdes.value="";
//document.frmEmployee.comPostTow.value="";
document.frmEmployee.txtRemarks.value="";
document.frmEmployee.comProcFlowId.value="";
document.frmEmployee.butSub.diabled=false;

}



  
/*function checkdate()
{
//alert('check');
    var status=document.frmEmployee.txtempstatus.value;

     if(status=='WKG')
     {
            return true;
     }
     else
     {
        
        
        var fromdt=maxtodate;
        var todt=document.frmEmployee.txtDOC.value;
       
        //alert(todt);
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
            
            alert('Completed Date Should be greater than or equal to '+maxtodate);
            //document.frmEmployee.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                     alert('Completed Date Should be greater than or equal to '+maxtodate);
                    //document.frmEmployee.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Completed Date Should be greater than or equal to '+maxtodate);
                           // document.frmEmployee.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
    }
        return true;

}


function checkdate1()
{
//alert('check');
    
        var fromdt=document.frmEmployee.txtDOC.value;
        var todt=document.frmEmployee.txtDOJ.value;
        
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
           alert('Join Date Should be greater than or equal to Comleted Date');
            //document.frmEmployee.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than or equal to Comleted Date');
                    //document.frmEmployee.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Join Date Should be greater than or equal to Comleted Date');
                           // document.frmEmployee.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
     return true;

}*/


function checkdate2()
{
//alert('check');
     if(maxtodate==null || maxtodate=='empty')
     {
            return true;
     }
     else
     {
        var fromdt=maxtodate;
        var todt=document.frmEmployee.txtDOJ.value;
        
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
            alert('Join Date Should be greater than or equal to '+maxtodate);
            //document.frmEmployee.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than  or equal to '+maxtodate);
                    //document.frmEmployee.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Join Date Should be greater than or equal to  '+maxtodate);
                           // document.frmEmployee.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
    }
        return true;

}


function nullch()
{   
     
     if((document.frmEmployee.txtEmpId1.value==null)||(document.frmEmployee.txtEmpId1.value.length==0))
    {
        alert("Select Employee ID");
        document.frmEmployee.txtEmpId1.focus();
        return false;
    }
    
    
   else if((document.frmEmployee.txtInstLocation.value==null)||(document.frmEmployee.txtInstLocation.value.length==0))
    {
        alert("Enter Institution Loaction");
        document.frmEmployee.txtInstLocation.focus();
        return false;
    }
    else if((document.frmEmployee.txtCourseName.value==null)||(document.frmEmployee.txtCourseName.value.length==0))
    {
        alert("Enter Course Name");
        document.frmEmployee.txtCourseName.focus();
        return false;
    }
   
    if((document.frmEmployee.txtDOJ.value==null)||(document.frmEmployee.txtDOJ.value.length==0))
    {
        alert("Enter Date Of Joining");
        document.frmEmployee.txtDOJ.focus();
        return false;
    }
        else if((document.frmEmployee.txtOffice_Id.value==null)||(document.frmEmployee.txtOffice_Id.value.length==0))
    {
        alert("Select the SR Controlling Office Name");
        document.frmEmployee.txtOffice_Id.focus();
        return false;
    }
   // alert('test');
    var empstatus=document.frmEmployee.txtempstatus.value;
    var c1;
  /*  if(empstatus!='WKG')
        c1=checkdate1();
    else */
        c1=checkdate2();
       if(c1==false)
       {
           document.frmEmployee.txtDOJ.focus();
            return false;
        }
    /* if((document.frmEmployee.cmbsgroup.value=="0")||(document.frmEmployee.cmbsgroup.value.length==0))
    {
        alert("Select Service Group");
       document.frmEmployee.cmbsgroup.focus();
        return false;
    }
     else if((document.frmEmployee.cmbdes.value=="0")||(document.frmEmployee.cmbdes.value.length==0))
    {
        alert("Select Designation");
        try{
        document.frmEmployee.cmbdes.focus();
        }catch(e){document.frmEmployee.cmbsgroup.focus();}
        return false;
    }
    
    else if((document.frmEmployee.comPostTow.value==null)||(document.frmEmployee.comPostTow.value.length==0))
    {
        alert("Select Post towards");
        document.frmEmployee.comPostTow.focus();
        return false;
    }    
  */  
  
    if(document.frmEmployee.cmbWing)
    {
             if((document.frmEmployee.cmbWing.value==null)||(document.frmEmployee.cmbWing.value==0))
            {
                alert("Select Wing");
                document.frmEmployee.cmbWing.focus();
                return false;
            }
    }
   /* else if((document.frmEmployee.txtRemarks.value==null)||(document.frmEmployee.txtRemarks.value.length==0))
    {
        alert("Null Value not accepted");
        document.frmEmployee.txtRemarks.focus();
        return false;
    }*/
    
    return true;
}
/*
function postcount()
{

document.frmEmployee.comPostTow.value=document.frmEmployee.cmbdes.value;
}
*/


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




function processResponse(req)
{
//alert("hello processResponse");
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //var OfficeName=document.getElementById("txtOfficeName");
                      //var OfficeId=document.getElementById("txtOfficeId");
                    
                      var flag1=0;
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      

                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        
                               document.getElementById("txtOffice_Id").value='';
                               document.getElementById("txtOffice_Name").value='';
                                
                                
                                document.getElementById("txtOffice_Id").focus();
                            
                      }
                      else
                      {
                             
                              
                              var off_close=response.getElementsByTagName("off_close")[0].firstChild.nodeValue;  
                              var off_lev=response.getElementsByTagName("off_lev")[0].firstChild.nodeValue; 
                             // alert(off_close+off_lev);
                              if(off_close=='yes')
                                   {
                                       flag1=1;
                                       alert("Selected Office has Closed");
                                       document.getElementById("txtOffice_Id").value='';
                                       document.getElementById("txtOffice_Name").value='';
                                   }
                                   else if(off_lev=='invalid')
                                   {
                                       flag1=1;
                                       alert("Selected Office Level is not Valid");
                                       document.getElementById("txtOffice_Id").value='';
                                       document.getElementById("txtOffice_Name").value='';
                                   }
                              if(flag1==0)
                              {
                            //  alert("final")
                              var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                            //  alert(name);
                              document.getElementById("txtOffice_Name").value=name;
                               }      
                         }  
                    
                          
            }   
                     
       }
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

