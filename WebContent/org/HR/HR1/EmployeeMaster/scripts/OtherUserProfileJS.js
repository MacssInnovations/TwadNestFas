var my_window;
//alert('kkk');


function togetFocus()
{
   //alert('test');
   document.frmOtherUser.Employee_Name.focus();
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
 
 
function callServer(command,param)
{
      
        if(command=="Add")
       {
         
      
          var flag=nullCheck();
         if(flag==true)
          {
          startwaiting(document.frmOtherUser) ; 
          var userid=document.frmOtherUser.txtuserid.value;
            url="../../../../../OtherUserProfileServ.view?command=Check&Userid=" + userid ;
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
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
          stopwaiting(document.frmOtherUser) ;
          //alert(req.responseTEXT);
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              //alert(baseResponse);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              //alert(command);
               if(command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
              else if(command=="Add")
              {//alert("addrw");
                   addRow(baseResponse);
              }
          }
        }
  }


function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
             // alert(flag);
              if(flag=="success")
              { 
                    document.frmOtherUser.action="../../../../../OtherUserProfileServ.view";
                    document.frmOtherUser.method="POST";
                    document.frmOtherUser.submit();
                   // alert('test');
              }
              else if(flag="ExistRec")
              {
                 alert("Given User Id is already exist");
              }
              else
              {
                alert("Failed to add Records");
                
              }

}

function nullCheck()
{
   if(document.frmOtherUser.Employee_Name.value=="")
   {
      alert("Please Enter the Employee Name");
      document.frmOtherUser.Employee_Name.focus();
      return false;
   }
      else if(document.frmOtherUser.Employee_Name.value.length<3)
   {
      alert("Employee Name should be greater than 3");
      document.frmOtherUser.Employee_Name.focus();
      return false;
   }
   else if(document.frmOtherUser.txtusercategory.value=="")
   {
      alert("Select User Category");
      document.frmOtherUser.txtusercategory.focus();
      return false;
   }
    else if(document.frmOtherUser.txtofficename.value=="")
   {
      alert("Enter Office name");
      document.frmOtherUser.txtofficename.focus();
      return false;
   }
   else if(document.frmOtherUser.txtDesignation.value=="")
   {
      alert("Enter Designation");
      document.frmOtherUser.txtDesignation.focus();
      return false;
   }
   
   else if(document.frmOtherUser.txtoffid.value=="")
   {
      alert("Enter Office Id");
      document.frmOtherUser.txtoffid.focus();
      return false;
   }
   
    else if(document.frmOtherUser.txtuserid.value=="")
   {
      alert("Enter User Id");
      document.frmOtherUser.txtuserid.focus();
      return false;
   }
    else if(document.frmOtherUser.txtpassword.value=="")
   {
      alert("Enter Preffered password");
      document.frmOtherUser.txtpassword.focus();
      return false;
   }
   else if(document.frmOtherUser.txtcpassword.value=="")
   {
      alert("Enter Confirm password");
      document.frmOtherUser.txtcpassword.focus();
      return false;
   }
   
   if(document.frmOtherUser.txtcpassword.value!=document.frmOtherUser.txtpassword.value)
   {
      alert("Preffered password & Confirm password should be equal");
      document.frmOtherUser.txtcpassword.focus();
      return false;
   }
    
   return true;
}

function nullCheck1()
{
   if(document.frmOtherUser.txtEmpId1.value=="")
   {
      alert("Please Enter the Employee Id");
      document.frmOtherUser.txtEmpId1.focus();
      return false;
   }
   
   else if(document.frmOtherUser.Employee_Name.value=="")
   {
      alert("Please Enter the Employee Name");
      document.frmOtherUser.Employee_Name.focus();
      return false;
   }
   else if(document.frmOtherUser.Employee_Name.value.length<3)
   {
      alert("Employee Name should be greater than 3");
      document.frmOtherUser.Employee_Name.focus();
      return false;
   }
   else if(document.frmOtherUser.txtoffid.value.length<3)
   {
      alert("Office Id should be greater than 3");
      document.frmOtherUser.txtoffid.focus();
      return false;
   }
  
   
   return true;
}

function toCheck()
{

 if(!isNaN(document.frmOtherUser.Employee_Name.value))
   {
      alert("Employee Name must be in Character");
      document.frmOtherUser.Employee_Name.value="";
      document.frmOtherUser.Employee_Name.focus();
      return false;
   }
   
   return true;
}  

function toCheck1()
{
if(document.frmOtherUser.Employee_Initial.value.length>0)
   {
     
         if(!isNaN(document.frmOtherUser.Employee_Initial.value))
           {
              alert("Employee Initial must be in Character");
              document.frmOtherUser.Employee_Initial.value="";
              document.frmOtherUser.Employee_Initial.focus();
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
  //var FirstField=document.frmOtherUser.txtEmpId1.value;
 if((document.frmOtherUser.txtEmpId1.value=="") || (document.frmOtherUser.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     document.frmOtherUser.txtEmpId1.focus();
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

function nonanum(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode
    //alert(unicode);
    if (unicode!=8)
    {
        if (unicode==32 || (unicode>=65 && unicode<=90) || (unicode>=97 && unicode<=122) || unicode==45 || unicode==95 ||unicode==46 || unicode==9)
            return true;
        else
            return false;
    }
}