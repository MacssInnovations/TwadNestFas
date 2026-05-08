
//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
//alert('kkk');


function togetFocus()
{
   document.frmEmployee.Employee_Initial.focus();
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
   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee_Search","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callServer('Existg','null');

}
//this is the function to close the employee popup windows...
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();

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
 function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
 
  function clearAll()
 {
   document.frmEmployee.EmpId.value="";
   document.frmEmployee.txtEmpId1.value="";
   document.frmEmployee.Employee_Name.value="";
   document.frmEmployee.Gpf_Number.value="";
   document.frmEmployee.txtEmpId1.disabled=false;
   
   
                var id=document.getElementById("divAddAvail");
                  id.innerHTML="&nbsp";
                  var id=document.getElementById("divAddValid");
                  id.innerHTML="&nbsp";
                  
                  var id=document.getElementById("divSRCAvail");
                  id.innerHTML="&nbsp";
                  var id=document.getElementById("divSRCValid");
                  id.innerHTML="&nbsp";
                  
                  var id=document.getElementById("divCPAvail");
                  id.innerHTML="&nbsp";
                  var id=document.getElementById("divCPValid");
                  id.innerHTML="&nbsp";
                  var id=document.getElementById("divSRDAvail");
                  id.innerHTML="&nbsp";
                  var id=document.getElementById("divSRDValid");
                  id.innerHTML="&nbsp";
                  
   
   
   document.frmEmployee.txtEmpId1.focus();
  // alert('hello');
 }
 
 function bodyload()
 {
 document.frmEmployee.Employee_Initial.focus();
 }
 
 
 
function callServer(command,param)
{
      if(command=="Existg")
       {
            
       var strEmpId=document.frmEmployee.txtEmpId1.value;
       startwaiting(document.frmEmployee) ; 
          url="../../../../../ValidateEmployeeDetails.view?command=Existg&EmpId=" + strEmpId;
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
    
}


function processResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
          stopwaiting(document.frmEmployee) ;
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="Existg")
              {
                  existRow(baseResponse);                 
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
           
          }
        }
  }

function existRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              { 
                  document.frmEmployee.Employee_Name.value=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  var gpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(gpf==0)
                    gpf="";
                  document.frmEmployee.Gpf_Number.value=gpf;
                  document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value
                  document.frmEmployee.txtEmpId1.disabled=true;
                  
                  var id=document.getElementById("divAddAvail");
                  id.innerHTML=baseResponse.getElementsByTagName("AddAvail")[0].firstChild.nodeValue;
                  var id=document.getElementById("divAddValid");
                  id.innerHTML=baseResponse.getElementsByTagName("AddValid")[0].firstChild.nodeValue;
                  
                  var id=document.getElementById("divSRCAvail");
                  id.innerHTML=baseResponse.getElementsByTagName("SRCAvail")[0].firstChild.nodeValue;
                  var id=document.getElementById("divSRCValid");
                  id.innerHTML=baseResponse.getElementsByTagName("SRCValid")[0].firstChild.nodeValue;
                  
                  var id=document.getElementById("divCPAvail");
                  id.innerHTML=baseResponse.getElementsByTagName("CPAvail")[0].firstChild.nodeValue;
                  var id=document.getElementById("divCPValid");
                  id.innerHTML=baseResponse.getElementsByTagName("CPValid")[0].firstChild.nodeValue;
                  
                  var id=document.getElementById("divSRDAvail");
                  id.innerHTML=baseResponse.getElementsByTagName("SRDAvail")[0].firstChild.nodeValue;
                  var id=document.getElementById("divSRDValid");
                  id.innerHTML=baseResponse.getElementsByTagName("SRDValid")[0].firstChild.nodeValue;
                  
              }
            
              else
             {
                     alert("Given Employee Id is not exist ");
                     clearAll();
                    //  document.frmEmployee.txtEmpId1.focus();
                    //  document.frmEmployee.txtEmpId1.value='';
             }
              
     }         


function nullCheck()
{
   if(document.frmEmployee.Employee_Name.value=="")
   {
      alert("Please Enter the Employee Name");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
      else if(document.frmEmployee.Employee_Name.value.length<3)
   {
      alert("Employee Name should be greater than 3");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }

   
   return true;
}

function nullCheck1()
{
   if(document.frmEmployee.txtEmpId1.value=="")
   {
      alert("Please Enter the Employee Id");
      document.frmEmployee.txtEmpId1.focus();
      return false;
   }
   
   else if(document.frmEmployee.Employee_Name.value=="")
   {
      alert("Please Enter the Employee Name");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
   else if(document.frmEmployee.Employee_Name.value.length<3)
   {
      alert("Employee Name should be greater than 3");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
  
   
   return true;
}

function toCheck()
{

 if(!isNaN(document.frmEmployee.Employee_Name.value))
   {
      alert("Employee Name must be in Character");
      document.frmEmployee.Employee_Name.value="";
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
   
   return true;
}  

function toCheck1()
{
if(document.frmEmployee.Employee_Initial.value.length>0)
   {
     
         if(!isNaN(document.frmEmployee.Employee_Initial.value))
           {
              alert("Employee Initial must be in Character");
              document.frmEmployee.Employee_Initial.value="";
              document.frmEmployee.Employee_Initial.focus();
              return false;
           }
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
