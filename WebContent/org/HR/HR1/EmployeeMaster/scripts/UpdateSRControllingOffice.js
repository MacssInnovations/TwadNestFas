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
       return;
    }
    else
    {
        winemp=null
    }
     startwaiting(document.frmEmployee) ;   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

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
     winemp= window.open("EmpServicePopup.jsp","mywindow_NECO","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}


function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callServer1('Load','null');

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
       return;

    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_NECO","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
//alert(deptid);
//if(deptid==null)
{
    document.frmEmployee.Office_Id.value=jobid;
    document.frmEmployee.Dept_Id.value=deptid;
    callServer1('ExistgOff','null');
    return true;
}

}

window.onunload=function()
{
//alert('hello');
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
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
       // document.frmEmployee.Employee_Prefix.value="";
       // document.frmEmployee.Employee_Initial.value="";
        document.frmEmployee.Employee_Name.value="";
        document.frmEmployee.Gpf_Number.value="";
         document.frmEmployee.Office_Id.value="";
         document.frmEmployee.Dept_Id.value="TWAD";
         document.frmEmployee.Office_Name.value="";
        document.frmEmployee.txtOffAddress.value="";
          document.frmEmployee.txtdtjoin.value="";
          document.frmEmployee.cmbValidate.disabled=false;
          //document.frmEmployee.cmdDelete.disabled=false;
           document.frmEmployee.txtEmpId1.focus();
 }

 //This is to check the Redundancy of the Existing Record
  function checkForRedundancy()
{

                        strEmpId=document.frmEmployee.txtEmpId1.value;
                         strOffId=document.frmEmployee.Office_Id.value;
                         strDeptId=document.frmEmployee.Dept_Id.value;
                         strDtjoin=document.frmEmployee.txtdtjoin.value;
   if((strEmpId=="")&&(strOffId=="")&&(strDtjoin=="")&&(strDeptId==""))
          {           
              alert("Enter Employee Id");
                return true;
            }
            else 
            {
                      startwaiting(document.frmEmployee) ;
                       url="../../../../../UpdateEmployeeControllingOffice.con?command=check&EName="+strEmpId;
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
             strOffName=document.frmEmployee.Office_Id.value;
             strDeptId=document.frmEmployee.Dept_Id.value;
             strDtjoin=document.frmEmployee.txtdtjoin.value;

          if(command=="Add")
          {           
          startwaiting(document.frmEmployee) ;
                       url="../../../../../UpdateEmployeeControllingOffice.con?command=Add&EName="+strEmpName+"&OffName="+strOffName+"&Dtjoin=" +strDtjoin +"&DeptId="+strDeptId;
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
        //  document.frmEmployee.Employee_Prefix.selectedIndex=0;
       // document.frmEmployee.Employee_Initial.value="";
        document.frmEmployee.Employee_Name.value="";
        document.frmEmployee.Gpf_Number.value="";
         document.frmEmployee.Office_Id.value="";
         document.frmEmployee.Dept_Id.value="TWAD";
         document.frmEmployee.Office_Name.value="";
        document.frmEmployee.txtOffAddress.value="";
          document.frmEmployee.txtdtjoin.value="";
           
           startwaiting(document.frmEmployee);
           url="../../../../../UpdateEmployeeControllingOffice.con?command=Load&EName=" + strEmpName;
           var req=getTransport();
           
            req.open("GET",url,true); 
            
           req.onreadystatechange=function()
            {
            handleResponse(req);
            }
           // req.onreadystatechange=handleResponse(req);
             if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
        else if(command=="Delete")
        {
            var check=nullCheck();
            if(check==true)
            {
               if(confirm("Do You Really want to Delete it"))
                            {
                            startwaiting(document.frmEmployee) ;
                url="../../../../../UpdateEmployeeControllingOffice.con?command=Delete&EName=" + strEmpName;
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
        else if(command=="Update")
        {
            //alert('update');
            var check=nullCheck();
            //alert(check);
            if(check==true)
            {
               startwaiting(document.frmEmployee);
                url="../../../../../UpdateEmployeeControllingOffice.con?command=Update&EName="+strEmpName+"&OffName="+strOffName+"&Dtjoin=" +strDtjoin +"&DeptId="+strDeptId;
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
        else if(command=="ExistgOff")
       {
            if(document.frmEmployee.Office_Id.value=="")
           {
             alert("Select Office Id");
             document.frmEmployee.Office_Id.value="";
             document.frmEmployee.Office_Name.value=""; 
             document.frmEmployee.txtOffAddress.value="";
             document.frmEmployee.Office_Id.focus();
             return false;
           }
           var strOffId=document.frmEmployee.Office_Id.value;
           var strDeptId=document.frmEmployee.Dept_Id.value;
          startwaiting(document.frmEmployee) ;
          url="../../../../../UpdateEmployeeControllingOffice.con?command=ExistgOff&OffName=" + strOffId +"&DeptId="+strDeptId;
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
             //alert('test1');
              stopwaiting(document.frmEmployee);
             //alert('test2');
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="Add")
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
                            callServer1('Add','null');
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
                          var empid=baseResponse.getElementsByTagName("EMP_ID");
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EmpName"); 
                              var empprefix=baseResponse.getElementsByTagName("EmpPref");
                              var empinitial=baseResponse.getElementsByTagName("EmpInit");
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf");
                              var offid=baseResponse.getElementsByTagName("OFFICE_ID");
                              var deptid=baseResponse.getElementsByTagName("DEPARTMENT_ID");
                              var jdate=baseResponse.getElementsByTagName("JDate");
                              
                              var offname=baseResponse.getElementsByTagName("OFFICE_NAME"); 
                              var offaddr1=baseResponse.getElementsByTagName("OffAddr1"); 
                              var offaddr2=baseResponse.getElementsByTagName("OffAddr2"); 
                              var city=baseResponse.getElementsByTagName("City");
                            var District=baseResponse.getElementsByTagName("District");
                              var recordstatus=baseResponse.getElementsByTagName("recordstatus");
                             //alert(recordstatus);
                             var tempid=empid.item(j).firstChild.nodeValue;
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tempprefix=empprefix.item(j).firstChild.nodeValue;
                            var tempinitial=empinitial.item(j).firstChild.nodeValue;
                            var tempgpf=empgpf.item(j).firstChild.nodeValue;
                            if(tempgpf==0)
                                tempgpf="";
                            var toffid=offid.item(j).firstChild.nodeValue;
                            var tdeptid=deptid.item(j).firstChild.nodeValue;
                            var toffname=offname.item(j).firstChild.nodeValue;
                           
                            var tjdate=jdate.item(j).firstChild.nodeValue;
                            var toffaddr1=offaddr1.item(j).firstChild.nodeValue;
                            var toffaddr2=offaddr2.item(j).firstChild.nodeValue;
                             var tcity=city.item(j).firstChild.nodeValue;
                             var tDistrict=District.item(j).firstChild.nodeValue;
                            var trecordstatus=recordstatus.item(j).firstChild.nodeValue;
                            //alert(trecordstatus);
                           
                           if(toffaddr1=="null")
                                toffaddr1="";
                    if(toffaddr2=="null")
                                toffaddr2="";
                    if(tcity=='null')
                        tcity='';
                   if(tDistrict=='null')
                        tDistrict='';
                      
                   var toffaddr= toffaddr1 ;
                   
                   if(toffaddr2!="")
                    toffaddr= toffaddr+"\n" + toffaddr2;
                   if(tcity!="")
                    toffaddr= toffaddr+"\n" + tcity;
                    if(tDistrict!="")
                    toffaddr= toffaddr+"\n" + tDistrict;
                           // alert('test'); 
                            //document.frmEmployee.Employee_Prefix.value=tempprefix;
                            //document.frmEmployee.Employee_Initial.value=tempinitial;
                            document.frmEmployee.Employee_Name.value=tempname;
                            if(tempgpf==0)
                                tempgpf="";
                              
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                             document.frmEmployee.Office_Id.value=toffid;
                             document.frmEmployee.Dept_Id.value=tdeptid;
                             document.frmEmployee.Office_Name.value=toffname;
                            document.frmEmployee.txtOffAddress.value=toffaddr;
                            if(tjdate!="0")
                            {
                              document.frmEmployee.txtdtjoin.value=tjdate;
                            }
                          }
                          if(trecordstatus=="FR")
                          {
                           // alert("Record is Freezed");
                           if(confirm('Record is already Freezed. Do you want to Update?'))
                           {
                            document.frmEmployee.cmbValidate.disabled=false;
                            }
                            else
                             {
                            document.frmEmployee.cmbValidate.disabled=true;
                            }
                            //document.frmEmployee.cmdDelete.disabled=true;
                          }
                          else
                          {
                          //alert('else');
                          document.frmEmployee.cmbValidate.disabled=false;
                          //document.frmEmployee.cmdDelete.disabled=false;
                          }
                          
                       }
         else if(flag=="failure1")
    {
          // alert(document.frmEmployee.txtEmpId1.value);
            var id=document.frmEmployee.txtEmpId1.value;
            alert("Can not Update SR Control Office. Because Employee Id "+id+" is not under your Office!");
            clearAll();
    }
     else if(flag=="failure2")
    {
            var id=document.frmEmployee.txtEmpId1.value;
            alert("You have no Current Posting. Can not update SR Control Office for "+id+"!");
            clearAll();
    }
     else if(flag=="failure3")
    {
            var id=document.frmEmployee.txtEmpId1.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not update SR Control Office!");
            clearAll();
    }
     else if(flag=="failure4")
    {
            var id=document.frmEmployee.txtEmpId1.value;
            alert("Can not update SR Control Office. Access Denined!");
           clearAll();
    }
                      
                     else if(flag=="NoRecord")
                     {
                  
                        var tempprefix=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                        var tempinitial=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                        var tempname=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                        var tempgpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                        
                           // document.frmEmployee.Employee_Prefix.value=tempprefix;
                           // document.frmEmployee.Employee_Initial.value=tempinitial;
                           if(tempinitial=='null' || tempinitial=='')
                           {
                                 document.frmEmployee.Employee_Name.value=tempname;
                            }
                            else
                            {
                                  tempname=  tempinitial+'.'+tempname;
                                  document.frmEmployee.Employee_Name.value=tempname;
                            }
                            if(tempgpf==0)
                                tempgpf="";
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                            document.frmEmployee.cmbValidate.disabled=false;
                           // document.frmEmployee.cmdDelete.disabled=false;
                     }
                      else if(flag=="freezed")
                    {
                      alert("Given Employee Id is already freezed");
                      //document.frmEmployee.txtEmpId1.value="";
                     //document.frmEmployee.txtEmpId1.focus();
                     
                      var empid=baseResponse.getElementsByTagName("EMP_ID");
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EmpName"); 
                              var empprefix=baseResponse.getElementsByTagName("EmpPref");
                              var empinitial=baseResponse.getElementsByTagName("EmpInit");
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf");
                              var offid=baseResponse.getElementsByTagName("OFFICE_ID");
                              var deptid=baseResponse.getElementsByTagName("DEPARTMENT_ID");
                              var jdate=baseResponse.getElementsByTagName("JDate");
                              
                              var offname=baseResponse.getElementsByTagName("OFFICE_NAME"); 
                              var offaddr1=baseResponse.getElementsByTagName("OffAddr1"); 
                              var offaddr2=baseResponse.getElementsByTagName("OffAddr2"); 
                              var city=baseResponse.getElementsByTagName("City");
                              var District=baseResponse.getElementsByTagName("District");
                              var recordstatus=baseResponse.getElementsByTagName("recordstatus");
                             //alert(recordstatus);
                             var tempid=empid.item(j).firstChild.nodeValue;
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tempprefix=empprefix.item(j).firstChild.nodeValue;
                            var tempinitial=empinitial.item(j).firstChild.nodeValue;
                            var tempgpf=empgpf.item(j).firstChild.nodeValue;
                            if(tempgpf==0)
                                tempgpf="";
                            var toffid=offid.item(j).firstChild.nodeValue;
                            var tdeptid=deptid.item(j).firstChild.nodeValue;
                            var toffname=offname.item(j).firstChild.nodeValue;
                           
                            var tjdate=jdate.item(j).firstChild.nodeValue;
                            var toffaddr1=offaddr1.item(j).firstChild.nodeValue;
                            var toffaddr2=offaddr2.item(j).firstChild.nodeValue;
                            var tcity=city.item(j).firstChild.nodeValue;
                            var tDistrict=District.item(j).firstChild.nodeValue;
                            var trecordstatus=recordstatus.item(j).firstChild.nodeValue;
                            //alert(trecordstatus);
                    
                    if(toffaddr1=="null")
                                toffaddr1="";
                    if(toffaddr2=="null")
                                toffaddr2="";
                    if(tcity=='null')
                        tcity='';
                   if(tDistrict=='null')
                        tDistrict='';
                        
                   var toffaddr= toffaddr1 ;
                   
                   if(toffaddr2!="")
                    toffaddr= toffaddr+"\n" + toffaddr2;
                   if(tcity!="")
                    toffaddr= toffaddr+"\n" + tcity;
                    if(tDistrict!="")
                    toffaddr= toffaddr+"\n" + tDistrict;
                            
                            //document.frmEmployee.Employee_Prefix.value=tempprefix;
                            //document.frmEmployee.Employee_Initial.value=tempinitial;
                            document.frmEmployee.Employee_Name.value=tempname;
                            if(tempgpf==0)
                                tempgpf="";
                              
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                             document.frmEmployee.Office_Id.value=toffid;
                             document.frmEmployee.Dept_Id.value=tdeptid;
                             document.frmEmployee.Office_Name.value=toffname;
                            document.frmEmployee.txtOffAddress.value=toffaddr;
                            if(tjdate!="0")
                            {
                              document.frmEmployee.txtdtjoin.value=tjdate;
                            }
                          }
                          if(trecordstatus=="FR")
                          {
                           // alert("Record is Freezed");
                            document.frmEmployee.cmbValidate.disabled=true;
                            //document.frmEmployee.cmdDelete.disabled=true;
                          }
                          else
                          {
                          //alert('else');
                          document.frmEmployee.cmbValidate.disabled=false;
                          //document.frmEmployee.cmdDelete.disabled=false;
                          }
                          //////////
                     
                     
                     
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
              
                   var offaddr1=baseResponse.getElementsByTagName("OffAddr1")[0].firstChild.nodeValue; 
                   var offaddr2=baseResponse.getElementsByTagName("OffAddr2")[0].firstChild.nodeValue; 
                   var city=baseResponse.getElementsByTagName("City")[0].firstChild.nodeValue; 
                    var District=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                   
                    if(offaddr1=="null")
                                offaddr1="";
                    if(offaddr2=="null")
                                offaddr2="";
                    if(city=='null')
                        city='';
                   if(District=='null')
                        District='';
                        
                   var toffaddr= offaddr1 ;
                   
                   if(offaddr2!="")
                    toffaddr= toffaddr+"\n" + offaddr2;
                   if(city!="")
                    toffaddr= toffaddr+"\n" + city;
                    if(District!="")
                    toffaddr= toffaddr+"\n" + District;
                  
                   
                    document.frmEmployee.Office_Name.value=baseResponse.getElementsByTagName("OffName")[0].firstChild.nodeValue; 
                    document.frmEmployee.txtOffAddress.value=toffaddr;
              }
              else if(flag=="NoValue")
              {
                 alert("No Office Exists - Give Some other Office Id");
                 document.frmEmployee.Office_Id.value="";
                 document.frmEmployee.Office_Name.value=""; 
                 document.frmEmployee.txtOffAddress.value="";
                 document.frmEmployee.Office_Id.focus();
                 
              }
              else
              {
                alert("Failed to load office details");
                document.frmEmployee.Office_Id.value="";
                 document.frmEmployee.Office_Name.value=""; 
                 document.frmEmployee.txtOffAddress.value="";
                 document.frmEmployee.Office_Id.focus();
                 
              }
   }

function disp()
{
   if(document.frmEmployee.Office_Id.value=="")
   {
     alert("Select Office Id - It should not be empty");
     document.frmEmployee.Office_Id.focus();
     return false;
   }
}



// generalized code

//To Check the null values
function nullCheck()
{
   if(document.frmEmployee.txtEmpId1.value=="")
   {
      alert("Select the Employee Name");
      document.frmEmployee.txtEmpId1.focus();
      return false;
   }
   else if(document.frmEmployee.Office_Id.value=="")
   {
     alert("Select Office Id");
     document.frmEmployee.Office_Id.focus();
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
       //alert(unicode);
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
/*
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
    
}*/



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
    
}
