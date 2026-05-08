//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
//alert('asdfasdfasdfkkk');
function servicepopup1()
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
    // startwaiting(document.frmEmployee) ;   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callServer1('Load','null');

}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();
}

///////////////////////////////////////////////////////////////////////////////////


//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup1()
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
        //startwaiting(document.frmEmployee) ;
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
 
    winjob.focus();
    
}

function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed) 
         winjob.officeSelection(true,true,true,true);
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
//if(deptid==null)
{
    document.frmEmployee.Office_Id.value=jobid;
    document.frmEmployee.Dept_Id.value=deptid;
    callServer1('ExistgOff','null');
    return true
}
/*else
{
        alert('Please select a TWAD Office');
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
        }
        return false
}*/
}



///////////////////////////////////////////////////////////////////////////////////

/*function getDesignation2()
    {
   
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
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
    
      function loadOfficesByType2(type)
    {
        //alert(type);
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation1(req);
        }
        req.send(null);
    }*/

//***************************************************************************//
 function getDesignation1()
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
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
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
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
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
    
    
    //***************************************************************************//

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
// function to clear all the combo and text fields and even the grid
 function clearAll()
 {        
        document.frmEmployee.txtEmpId1.value="";
        document.frmEmployee.txtEmpName.value="";
        document.frmEmployee.cmbsgroup.selectedIndex=0;
        document.frmEmployee.cmbdes.selectedIndex=0;
         document.frmEmployee.Office_Id.value="";
         document.frmEmployee.Dept_Id.value="";
         document.frmEmployee.Office_Name.value="";
          document.frmEmployee.txtdtjoin.value="";
          document.frmEmployee.txtremarks.value="";
           document.frmEmployee.cmdUpdate.disabled=false;
        
                       
       
 }
 
 //This is to check the Redundancy of the Existing Record
  function checkForRedundancy()
{
                        strEmpName=document.frmEmployee.txtEmpId1.value;
                         strDesig=document.frmEmployee.cmbdes.value;
                         strOffName=document.frmEmployee.Office_Id.value;
                         strDeptId=document.frmEmployee.Dept_Id.value;
                         strDtjoin=document.frmEmployee.txtdtjoin.value;
                         strRemarks=document.frmEmployee.txtremarks.value;
                         var strGrade;
                /*  if(document.frmEmployee.Office_Grade[0].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[0].value;
                  else if(document.frmEmployee.Office_Grade[1].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[1].value;
                      else
                         strGrade=document.frmEmployee.Office_Grade[2].value;
                */
                if(document.frmEmployee.Office_Grade[0].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[0].value;
                  else if(document.frmEmployee.Office_Grade[1].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[1].value;
                      else
                         strGrade=document.frmEmployee.Office_Grade[2].value;
   if((strEmpName=="")&&(strDesig=="")&&(strOffName=="")&&(strDtjoin=="")&&(strGrade==""))
          {           
          
              alert("Enter Employee Id");
                return true;
            }
            else 
            {startwaiting(document.frmEmployee) ;
                       url="../../../../../Current_Posting_Servlet.view?command=check&EName="+strEmpName;
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



function callServer1(command,param)
 {     
       
          var strEmpName;
          var strDesig;
          var strOffName;
          var strDtjoin;  
          var strRemarks;
          var strDeptId;
             strEmpName=document.frmEmployee.txtEmpId1.value;
             strDesig=document.frmEmployee.cmbdes.value;
             strOffName=document.frmEmployee.Office_Id.value;
             strDeptId=document.frmEmployee.Dept_Id.value;
             strDtjoin=document.frmEmployee.txtdtjoin.value;
             strRemarks=document.frmEmployee.txtremarks.value;
              var strGrade;
                  if(document.frmEmployee.Office_Grade[0].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[0].value;
                  else if(document.frmEmployee.Office_Grade[1].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[1].value;
                      else
                         strGrade=document.frmEmployee.Office_Grade[2].value;
          
          if(command=="Grant")
          {           
          startwaiting(document.frmEmployee) ;
                       url="../../../../../Current_Posting_Servlet.view?command=Grant&EName="+strEmpName+"&Desig=" + strDesig+ "&OffName="+strOffName+"&Dtjoin=" +strDtjoin +"&Remarks="+strRemarks+"&DeptId="+strDeptId+"&Grade="+strGrade;
                       //alert("grant"+url);
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
         else if(command=="Load")
         { 
         //alert("before url");
         document.frmEmployee.txtEmpName.value="";
        document.frmEmployee.cmbsgroup.selectedIndex=0;
        document.frmEmployee.cmbdes.selectedIndex=0;
         document.frmEmployee.Office_Id.value="";
         document.frmEmployee.Office_Name.value="";
          document.frmEmployee.txtdtjoin.value="";
          document.frmEmployee.txtremarks.value="";
          document.frmEmployee.Dept_Id.value="";
           var strGrade;
                  if(document.frmEmployee.Office_Grade[0].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[0].value;
                  else if(document.frmEmployee.Office_Grade[1].checked==true)
                      strGrade=document.frmEmployee.Office_Grade[1].value;
                      else
                         strGrade=document.frmEmployee.Office_Grade[2].value;
          
          startwaiting(document.frmEmployee) ;
            url="../../../../../Current_Posting_Servlet.view?command=Load&EName=" + strEmpName;
           // alert(url);
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
       else if(command=="Delete")
        {
           if(confirm("Do You Really want to Delete it"))
                        {
                        startwaiting(document.frmEmployee) ;
            url="../../../../../Current_Posting_Servlet.view?command=Delete&EName=" + strEmpName;
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
        else if(command=="Update")
        {
           startwaiting(document.frmEmployee) ;
            url="../../../../../Current_Posting_Servlet.view?command=Update&EName="+strEmpName+"&Desig=" + strDesig+ "&OffName="+strOffName+"&Dtjoin=" +strDtjoin +"&Remarks="+strRemarks+"&DeptId="+strDeptId+"&Grade="+strGrade;
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
        else if(command=="ExistgOff")
       {
        
           var strOffId=document.frmEmployee.Office_Id.value;
           var strDeptId=document.frmEmployee.Dept_Id.value;
          //  alert("s com in" +strOffId);
          startwaiting(document.frmEmployee) ;
          url="../../../../../Current_Posting_Servlet.view?command=ExistgOff&OffName=" + strOffId +"&DeptId="+strDeptId;
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


function handleResponse(req)
    {   
    if(req.readyState==4)
        {
          if(req.status==200)
          {      
          stopwaiting(document.frmEmployee) ;
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="Grant")
              { 
              
                  addRow(baseResponse);                 
              }
              else if(command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
              else if(command=="check")
              {
              checkRow(baseResponse);
              }
              else if(command=="Delete")
              {
              deleteRow(baseResponse);
              }
              else if(command=="Load")
              {
              loadRow(baseResponse);
              }
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="ExistgOff")
              {
                  existOffRow(baseResponse);                 
              }

          }
        }
  }
function checkRow(baseResponse)
{ 
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                     var check=false;
                  check=nullCheck();
                  if(check==true)
                  {
                      callServer1('Update','null');
                   }   
                     
                     }
                    
                  else
                  {
                  var check=false;
                  check=nullCheck();
                  if(check==true)
                  {
                  callServer1('Grant','null');
                  }
                 
                  }
 
}


function addRow(baseResponse)
    {
                

              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
              
                  if(flag=="success")
                  {
                       
                      alert("Record inserted into the database successfully");
                      clearAll();
                     
                  }
                  
                  else
                  {
                      alert("Failed to Insert values");                     
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

function loadRow(baseResponse)
 {
                    
                       
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                   var RecordStatus=baseResponse.getElementsByTagName("RecordStatus")[0].firstChild.nodeValue;
                           if(RecordStatus=='FR')
                       {
                       
                       alert("This Record has been Freezed - It is not available to Update");
                       
                        var empid=baseResponse.getElementsByTagName("EMP_ID");
                         
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EMPLOYEE_NAME"); 
                              var desig=baseResponse.getElementsByTagName("DESIGNATION");
                              var offid=baseResponse.getElementsByTagName("OFFICE_ID");
                              var deptid=baseResponse.getElementsByTagName("DEPARTMENT_ID");
                              var offname=baseResponse.getElementsByTagName("OFFICE_NAME"); 
                              var jdate=baseResponse.getElementsByTagName("JDate"); 
                              var remarks=baseResponse.getElementsByTagName("Remarks"); 
                              var sgroup=baseResponse.getElementsByTagName("ServGroup");
                              var designId=baseResponse.getElementsByTagName("DesignId");
                              var grade=baseResponse.getElementsByTagName("Grade");
                                                      // append a row
                              var tbody=document.getElementById("tblList");
                              var table=document.getElementById("Existing");

                            var tempid=empid.item(j).firstChild.nodeValue;
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tdesig=desig.item(j).firstChild.nodeValue;
                            var toffid=offid.item(j).firstChild.nodeValue;
                            var tdeptid=deptid.item(j).firstChild.nodeValue;
                            var toffname=offname.item(j).firstChild.nodeValue;
                           
                            var tjdate=jdate.item(j).firstChild.nodeValue;
                            var tremarks=remarks.item(j).firstChild.nodeValue;
                            var servgrp=sgroup.item(j).firstChild.nodeValue;
                            var tdesignId=designId.item(j).firstChild.nodeValue;
                            var tgrade=grade.item(j).firstChild.nodeValue;
                            
                            document.frmEmployee.txtEmpName.value=tempname;
                            document.frmEmployee.Office_Id.value=toffid;
                            document.frmEmployee.Dept_Id.value=tdeptid;
                            document.frmEmployee.Office_Name.value=toffname;
                            //alert(tjdate);
                            if(tjdate==0)
                                tjdate="";
                            document.frmEmployee.txtdtjoin.value=tjdate;
                            //alert(tremarks+"1");
                            if(tremarks=="Not Specified")
                              tremarks="";
                              else
                                tremarks=tremarks;
                            document.frmEmployee.txtremarks.value=tremarks;
                          /*  if(tgrade=="Normal")
                            document.frmEmployee.Office_Grade[0].checked=true;
                            else if(tgrade=="Selection")
                            document.frmEmployee.Office_Grade[1].checked=true;
                            else
                            document.frmEmployee.Office_Grade[2].checked=true;
                           */ 
                           if(tgrade=="Normal")
                            document.frmEmployee.Office_Grade[2].checked=true;
                            else if(tgrade=="Selection")
                            document.frmEmployee.Office_Grade[0].checked=true;
                            else
                            document.frmEmployee.Office_Grade[1].checked=true;
                            if(servgrp==0)
                               servgrp=0;
                            else   
                             document.frmEmployee.cmbsgroup.value =servgrp;
                     getDesignation1();
                   
                    if (tdesignId==0)
                    {
                      
                        tdesignId=0;
                      }  
                     else
                     {
                     //alert( document.frmEmployee.cmbdes.value = tdesignId);
                      document.frmEmployee.cmbdes.value = tdesignId;
                      }
                            
                            
                          }
                          
                      document.frmEmployee.cmdUpdate.disabled=true;
                       }
                  else   
                     {
                          var empid=baseResponse.getElementsByTagName("EMP_ID");
                         
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EMPLOYEE_NAME"); 
                              var desig=baseResponse.getElementsByTagName("DESIGNATION");
                              var offid=baseResponse.getElementsByTagName("OFFICE_ID");
                              var deptid=baseResponse.getElementsByTagName("DEPARTMENT_ID");
                              var offname=baseResponse.getElementsByTagName("OFFICE_NAME"); 
                              var jdate=baseResponse.getElementsByTagName("JDate"); 
                              var remarks=baseResponse.getElementsByTagName("Remarks"); 
                              var sgroup=baseResponse.getElementsByTagName("ServGroup");
                              var designId=baseResponse.getElementsByTagName("DesignId");
                              var grade=baseResponse.getElementsByTagName("Grade");
                                                      // append a row
                              var tbody=document.getElementById("tblList");
                              var table=document.getElementById("Existing");

                            var tempid=empid.item(j).firstChild.nodeValue;
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tdesig=desig.item(j).firstChild.nodeValue;
                            var toffid=offid.item(j).firstChild.nodeValue;
                            var tdeptid=deptid.item(j).firstChild.nodeValue;
                            var toffname=offname.item(j).firstChild.nodeValue;
                           
                            var tjdate=jdate.item(j).firstChild.nodeValue;
                            var tremarks=remarks.item(j).firstChild.nodeValue;
                            var servgrp=sgroup.item(j).firstChild.nodeValue;
                            var tdesignId=designId.item(j).firstChild.nodeValue;
                            var tgrade=grade.item(j).firstChild.nodeValue;
                            
                            document.frmEmployee.txtEmpName.value=tempname;
                            document.frmEmployee.Office_Id.value=toffid;
                            document.frmEmployee.Dept_Id.value=tdeptid;
                            document.frmEmployee.Office_Name.value=toffname;
                            if(tjdate==0)
                                tjdate="";
                            document.frmEmployee.txtdtjoin.value=tjdate;
                           if(tremarks=="Not Specified")
                              tremarks="";
                              else
                                tremarks=tremarks;
                            document.frmEmployee.txtremarks.value=tremarks;
                           /* if(tgrade=="Normal")
                            document.frmEmployee.Office_Grade[0].checked=true;
                            else if(tgrade=="Selection")
                            document.frmEmployee.Office_Grade[1].checked=true;
                            else
                            document.frmEmployee.Office_Grade[2].checked=true;
                          */  
                          if(tgrade=="Normal")
                            document.frmEmployee.Office_Grade[2].checked=true;
                            else if(tgrade=="Selection")
                            document.frmEmployee.Office_Grade[0].checked=true;
                            else
                            document.frmEmployee.Office_Grade[1].checked=true;
                            if(servgrp==0)
                               servgrp=0;
                            else   
                             document.frmEmployee.cmbsgroup.value =servgrp;
                     getDesignation1();
                   
                    if (tdesignId==0)
                    {
                      
                        tdesignId=0;
                      }  
                     else
                     {
                     //alert( document.frmEmployee.cmbdes.value = tdesignId);
                      document.frmEmployee.cmbdes.value = tdesignId;
                      }
                            
                            
                          }
                          document.frmEmployee.cmdUpdate.disabled=false;
                      }    
                          
                       }
                      
                     else if(flag=="NoRecord")
                     {
                    
                       // alert("No Record Exists for the given Employee Id - Insert New Record");
                        var tempname=baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue; 
                        //alert(tempname);
                        document.frmEmployee.txtEmpName.value=tempname;
                        
                       
                     }
                     else if(flag=="NoValue")
                    {
                      alert("Employee Id Does not Exists - Create an Id for the Employee First");
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
                    alert("Records are updated successfully");
                    clearAll();
                  }
                  else
                  {
                    alert("Failed to update");
                  }
}   

function existOffRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
             
              if(flag=="success")
              { 
                    document.frmEmployee.Office_Name.value=baseResponse.getElementsByTagName("OffName")[0].firstChild.nodeValue; 
              }
              else if(flag=="NoValue")
              {
                 alert("No Office Exists - Give Some other Office Id");
                 document.frmEmployee.Office_Id.value="";
                 document.frmEmployee.Office_Id.focus();
                 
              }
              else
              {
                alert("Failed to load office details");
              }
   }


function disp()
{
   if(document.frmEmployee.Office_Id.value=="")
   {
     alert("Select Office Name - It should not be empty");
     document.frmEmployee.Office_Id.focus();
     return false;
   }
}




//To Check the null values
function nullCheck()
{
   if(document.frmEmployee.txtEmpId1.value=="")
   {
      alert("Select the Employee Name");
      document.frmEmployee.txtEmpId1.focus();
      return false;
   }
   
   else if(document.frmEmployee.cmbsgroup.value=="")
   {
     alert("Select Service Group Name to select Designation");
     document.frmEmployee.cmbsgroup.focus();
     return false;
   }
   else if(document.frmEmployee.cmbdes.value=="")
   {
     alert("Select Employee Designation");
     document.frmEmployee.cmbdes.focus();
     return false;
   }
   else if(document.frmEmployee.Office_Id.value=="")
   {
     alert("Select Office Name");
     document.frmEmployee.Office_Id.focus();
     return false;
   }
   else if(document.frmEmployee.txtdtjoin.value=="")
   {
      alert("Enter Date of Joining");
       document.frmEmployee.txtdtjoin.focus();
       return false;
   }
  
   return true;
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
     alert("Please Enter Employee Id First");
     document.frmEmployee.txtEmpId1.focus();
     return false;
  }
   
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