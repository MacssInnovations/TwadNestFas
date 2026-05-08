
function togetFocus()
{
   //document.frmEmployee.Employee_Initial.focus();
   document.frmEmployee.txtEmpId1.focus();
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
   
   document.frmEmployee.cmdSub.disabled=false;
  
}

function onClear()
{
   
   
   document.frmEmployee.Employee_Prefix.selectedIndex=0;
   document.frmEmployee.Employee_Initial.value="";
   document.frmEmployee.Employee_Name.value="";
   document.frmEmployee.Gpf_Number.value="";
   //document.frmEmployee.txtEmpId1.disabled=false;
   //document.frmEmployee.txtEmpId1.focus();
   
   document.frmEmployee.cmdSub.disabled=false;
  
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
     
      var url="../../../../../InsertEmployee5_New.con?command=Existg&EmpId=" + strEmpId;
           
      var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
      {
        processResponse(req);
      }   
      
      req.send(null);
          
     
   }
      
       else if(command=="Verify")
       {
       
        //alert("inside verify");
                       
       //var strhEmpId=document.frmEmployee.EmpId.value;
       var strhEmpId=document.frmEmployee.txtEmpId1.value;
       var strEmpPref=document.frmEmployee.Employee_Prefix.value;
       var strEmpInit=document.frmEmployee.Employee_Initial.value.toUpperCase();
       var strEmpName=document.frmEmployee.Employee_Name.value;
       var strEmpGpf=document.frmEmployee.Gpf_Number.value;
       //alert("EmpGpf"+strEmpGpf);
       
       
       
       var flag=nullCheck1();       
       if(flag==true)
       {
          
            
          //startwaiting(document.frmEmployee) ; 
            url="../../../../../InsertEmployee5_New.con?command=Verify&EmpId=" + strhEmpId + "&EmpPref=" + strEmpPref + "&EmpInit=" + strEmpInit + "&EmpName=" + strEmpName +"&EmpGpf=" + strEmpGpf;
            //alert(url);
            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    
             req.send(null);
                
        }         
       }
       
      
}


function processResponse(req)
{   
    if(req.readyState==4)
    {
      if(req.status==200)
      {       
          
         //stopwaiting(document.frmEmployee) ;
   
        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        var tagCommand=baseResponse.getElementsByTagName("command")[0];
        var Command=tagCommand.firstChild.nodeValue; 
   
           if(Command=="Existg")
           {
                  
             existRow(baseResponse);                 
           }
           else if(Command=="sessionout")
           {
             alert('Session is closed');
   
            try
            {
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
            }
            catch(e){}
            
            self.close();
            return;
         }
           
         else if(Command=="Verify")
         {
            insertRow(baseResponse);
         }
              
          }
        }
        
        
  }

function existRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Given Employee Id already exists ");
        document.frmEmployee.txtEmpId1.focus();
        document.frmEmployee.txtEmpId1.value='';
        
        var emp_id=baseResponse.getElementsByTagName("EmpId")[0].firstChild.nodeValue;
        var emp_pre=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue;
        var emp_init=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue;
        var emp_name=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
        var emp_gpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue;
        
        document.frmEmployee.txtEmpId1.value=emp_id;
        
        //alert(emp_pre);
        if(emp_pre=="Mr")
        {
          document.frmEmployee.Employee_Prefix.value=emp_pre;
        }
        
        else if(emp_pre=="Ms")
        {
          document.frmEmployee.Employee_Prefix.value=emp_pre;
        }
        
        else if(emp_pre=="Thiru")
        {
          document.frmEmployee.Employee_Prefix.value=emp_pre;
        }
        
        else if(emp_pre=="Thiru")
        {
          document.frmEmployee.Employee_Prefix.value=emp_pre;
        }
        
        document.frmEmployee.Employee_Prefix.value=emp_pre;
        
        if(emp_init=="null")
        {
          document.frmEmployee.Employee_Initial.value="";
        }
        else
        {
        document.frmEmployee.Employee_Initial.value=emp_init;
        }
        document.frmEmployee.Employee_Name.value=emp_name;
        document.frmEmployee.Gpf_Number.value=emp_gpf;
        
        document.frmEmployee.cmdSub.disabled=true;
        
     }
              
             
              
}

function insertRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
    
     alert("Employee ID is created successfully");
     
   document.frmEmployee.txtEmpId1.value="";
   document.frmEmployee.Employee_Prefix.selectedIndex=0;
   document.frmEmployee.Employee_Initial.value="";
   document.frmEmployee.Employee_Name.value="";
   document.frmEmployee.Gpf_Number.value="";
   
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
   
   /*else if(document.frmEmployee.Employee_Initial.value=="")
   {
     alert("Please Enter the Employee Initial");
      return false;
   }*/
   
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
   
   /*
   else if(document.frmEmployee.Gpf_Number.value.length==0)
   {
     alert("Please Enter the GPF Number");
     return false;
   }*/
   /*
   if(document.frmEmployee.txtEmpId1.value!=document.frmEmployee.Gpf_Number.value)
   {
         alert("Please Enter the Correct GPF Number");
         document.frmEmployee.Gpf_Number.focus();
         return false;
    }*/
  
  return true;
}


function notEqual()
{

  //alert("inside notEqual method");
  
  if(document.frmEmployee.Gpf_Number.value.length==0)
    {
      return true;
    }
    else
    {
  if(document.frmEmployee.txtEmpId1.value!=document.frmEmployee.Gpf_Number.value)
   {
         alert("Please Enter the Correct GPF Number");
         document.frmEmployee.Gpf_Number.value="";
         document.frmEmployee.Gpf_Number.focus();
         return false;
    }
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

