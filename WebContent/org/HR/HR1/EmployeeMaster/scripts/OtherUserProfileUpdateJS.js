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
                document.frmOtherUser.action="../../../../../OtherUserProfileUpdateServ.view";
                    document.frmOtherUser.method="POST";
                    document.frmOtherUser.submit();
           }
           
       }
       
       else if(command=="Exist")
       {
         
      
         
         // startwaiting(document.frmOtherUser) ; 
          var des=document.getElementById("txtuserid");
          des.innerHTML="";
          var option=document.createElement("OPTION");
                    option.text="Select User Id";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
         
         document.frmOtherUser.Employee_Name.value="";
        document.frmOtherUser.Employee_Initial.value="";
        document.frmOtherUser.Employee_Prefix.value="";
        document.frmOtherUser.txtofficename.value="";
        document.frmOtherUser.txtofficeaddress.value="";
        document.frmOtherUser.txtDesignation.value="";
        document.frmOtherUser.txtemail.value="";
         
         
          var usercat=document.frmOtherUser.txtusercategory.value;
          if(usercat==0)
          {
            alert('Select User Category');
            document.frmOtherUser.txtusercategory.focus();
            return false;
          }
          
          
            url="../../../../../OtherUserProfileUpdateServ.view?command=Exist&usercat=" + usercat ;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
              
                loadUserid(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
          
           
       }
       
       else if(command=="ExistRecord")
       {
         
      
         
         // startwaiting(document.frmOtherUser) ; 
          var usercat=document.frmOtherUser.txtusercategory.value;
          if(usercat==0)
          {
            alert('Select User Category');
            document.frmOtherUser.txtusercategory.focus();
            return false;
          }
          
           var userid=document.frmOtherUser.txtuserid.value;
          if(userid==0)
          {
            alert('Select User Id');
            document.frmOtherUser.txtuserid.focus();
            return false;
          }
            url="../../../../../OtherUserProfileUpdateServ.view?command=ExistRecord&usercat=" + usercat+"&userid="+userid ;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
              
                loadRecord(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
          
           
       }
       
       
       
}



function  loadRecord(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
               // var des=document.getElementById("txtuserid");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                //des.innerHTML="";
                
                
                
                    document.frmOtherUser.Employee_Name.value="";
                    document.frmOtherUser.Employee_Initial.value="";
                    document.frmOtherUser.Employee_Prefix.value="";
                    document.frmOtherUser.txtofficename.value="";
                    document.frmOtherUser.txtofficeaddress.value="";
                    document.frmOtherUser.txtDesignation.value="";
                    document.frmOtherUser.txtemail.value="";
                    document.frmOtherUser.txtoffid.value="";
                    
                
                
                // stopwaiting(document.HRE_EmployeeServiceDetails);
                if(flag=="failure")
                {
                    alert("No Userid exists under this level");
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
                    document.frmOtherUser.Employee_Name.value=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                    initial=response.getElementsByTagName("initial")[0].firstChild.nodeValue;
                    if(initial=='null' || initial=='NULL')
                        initial='';
                    document.frmOtherUser.Employee_Initial.value=initial;
                    document.frmOtherUser.Employee_Prefix.value=response.getElementsByTagName("prefix")[0].firstChild.nodeValue;
                    document.frmOtherUser.txtofficename.value=response.getElementsByTagName("office")[0].firstChild.nodeValue;
                    address=response.getElementsByTagName("off_address")[0].firstChild.nodeValue;
                    if(address=='null')
                        address='';
                    document.frmOtherUser.txtofficeaddress.value=address;
                    document.frmOtherUser.txtDesignation.value=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                    var email=response.getElementsByTagName("email")[0].firstChild.nodeValue;
                    if(email=='null')
                        email='';
                    document.frmOtherUser.txtemail.value=email;
                    
                    var offid=response.getElementsByTagName("offid")[0].firstChild.nodeValue;
                    //alert(offid);
                    //if(offid=='null')
                     // offfid='';
                    document.frmOtherUser.txtoffid.value=offid;  
                    
                    var wing=response.getElementsByTagName("wing")[0].firstChild.nodeValue;
                    if(wing==1)
                    {     
                    //alert('1');
                    document.frmOtherUser.txtwing.value=wing; 
                    }
                    else if(wing==2)
                    {        
                    //alert('2');
                    document.frmOtherUser.txtwing.value=wing; 
                    }
                     else if(wing==3)
                    {     
                   // alert('3');
                    document.frmOtherUser.txtwing.value=wing; 
                    }
                    else if(wing==4)
                    {      
                    //alert('4');
                    document.frmOtherUser.txtwing.value=wing; 
                    }
                   
                
                }
        
        }
        
    }
    

}


function  loadUserid(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("txtuserid");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                // stopwaiting(document.HRE_EmployeeServiceDetails);
                if(flag=="failure")
                {
                    alert("No Userid exists under this level");
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
                    option.text="Select User Id";
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
                    document.frmOtherUser.action="../../../../../OtherUserProfileUpdateServ.view";
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

   if(document.frmOtherUser.txtusercategory.value=="")
   {
      alert("Select User Category");
      document.frmOtherUser.txtusercategory.focus();
      return false;
   }
     else if(document.frmOtherUser.txtuserid.value=="0")
   {
      alert("Select User Id");
      document.frmOtherUser.txtuserid.focus();
      return false;
   }
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