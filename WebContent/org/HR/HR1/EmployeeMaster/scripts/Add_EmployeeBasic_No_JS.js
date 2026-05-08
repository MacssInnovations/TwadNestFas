
//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
//alert('kkk');


function togetFocus()
{
   //document.frmEmployee.Employee_Initial.focus();
   document.frmEmployee.txtEmpId1.focus();
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
   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee_Search_for_Emp_Editing","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
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
   document.frmEmployee.Employee_Prefix.selectedIndex=0;
   document.frmEmployee.Employee_Initial.value="";
   document.frmEmployee.Employee_Name.value="";
   document.frmEmployee.Gpf_Number.value="";
   document.frmEmployee.txtEmpId1.disabled=false;
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
            alert("Hello");
       var strEmpId=document.frmEmployee.txtEmpId1.value;
     //  startwaiting(document.frmEmployee) ; 
          url="../../../../../InsertEmployee5.con?command=Existg&EmpId=" + strEmpId;
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
      
       else if(command=="Verify")
       {
       //alert('test');
       var strhEmpId=document.frmEmployee.EmpId.value;
       var strEmpPref=document.frmEmployee.Employee_Prefix.value;
       var strEmpInit=document.frmEmployee.Employee_Initial.value.toUpperCase();
       var strEmpName=document.frmEmployee.Employee_Name.value;
       var strEmpGpf=document.frmEmployee.Gpf_Number.value;
       
       var flag=nullCheck1();
          if(flag==true)
          {
          startwaiting(document.frmEmployee) ; 
            url="../../../../../InsertEmployee5.con?command=Verify&EmpId=" + strhEmpId + "&EmpPref=" + strEmpPref + "&EmpInit=" + strEmpInit + "&EmpName=" + strEmpName +"&EmpGpf=" + strEmpGpf;
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
       
      
}


function processResponse(req)
    {   
         alert(" in Process response!");
      if(req.readyState==4)
        {
        alert("Hello2");
          if(req.status==200)
          {       
          alert("Hello3");
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
           
              else if(command=="Verify")
              {
                   verifyRow(baseResponse);
              }
              
          }
        }
        
        
  }

function existRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag!="success")
              
         /*     { 
           
           document.frmEmployee.Employee_Prefix.value=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue;
             alert(baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue);
                 if(baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue!='null')
                  document.frmEmployee.Employee_Initial.value=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                else
                    document.frmEmployee.Employee_Initial.value="";
                  document.frmEmployee.Employee_Name.value=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  var gpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(gpf==0)
                    gpf="";
                  
                  document.frmEmployee.Gpf_Number.value=gpf;
                  document.frmEmployee.EmpId.value=document.frmEmployee.txtEmpId1.value
                  document.frmEmployee.txtEmpId1.disabled=true;
                  
              }
              else if(flag=="frezeed")
                    {
                      alert("Given Temporary Employee Id has already been Validated ");
                      document.frmEmployee.txtEmpId1.focus();
                      document.frmEmployee.txtEmpId1.value='';
                     // alert('test');
                      return;
                    }
             else if(flag=="delete")
          
            {
              alert("Given Temporary Employee Id has already been Deleted");
              document.frmEmployee.txtEmpId1.focus();
              document.frmEmployee.txtEmpId1.value='';
             // alert('test');
              return;
            }
              else
          */
             {
                     alert("Given Employee Id already exists ");
                      document.frmEmployee.txtEmpId1.focus();
                      document.frmEmployee.txtEmpId1.value='';
             }
              
     }         

  
   function verifyRow(baseResponse)
    {
              //alert('test');
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              { 
                //alert("Records are verified Successfully");
               // clearAll();
               //eid=baseResponse.getElementsByTagName("EmpId")[0].firstChild.nodeValue; 
                var b;
                var msg="Employee detail is inserted Successfully.";
                var head="Insert  Employee's Basic Details";
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
              else if(flag="ExistRec")
              {
                 egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                 alert("Give Some other GPF Number-"+egpf+"- This Number is already in use");
              }
              else
              {
                //alert("Failed to insert");
                 var b;
                var msg="Employee detail is not  inserted Successfully";
                var head="Insert Employee's Basic Details with Emp.ID";
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
   
function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              //alert(flag);
              if(flag=="success")
              { 
                //alert("Records are inserted Successfully");
                 eid=baseResponse.getElementsByTagName("EmpId")[0].firstChild.nodeValue; 
                  /*    alert("Submitted Temporary Employee Details Id is: " + eid);
                      document.frmEmployee.Employee_Prefix.selectedIndex=0;
                      document.frmEmployee.Employee_Initial.value="";
                      document.frmEmployee.Employee_Name.value="";
                      document.frmEmployee.Gpf_Number.value="";
                //clearAll();
                */
                 var b;
                var msg="Employee details successfuly created. " + eid;
                var head="Create Employee's Basic Details";
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
              else if(flag="ExistRec")
              {
                  egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                 alert("Give Some other GPF Number-"+egpf+"- This Number is already in use");
              }
              else
              {
                //alert("Failed to insert Records");
                var b;
                var msg="Employee detail is not created";
                var head="Create Employee's Basic Details";
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
      alert("Length of Employee Name should be 3 characters atleast ");
      document.frmEmployee.Employee_Name.focus();
      return false;
   }
 /*  else if(document.frmEmployee.Gpf_Number.value=="")
   {
      alert("Please Enter the GPF Number");
      document.frmEmployee.Gpf_Number.focus();
      return false;
   }*/
  
   
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