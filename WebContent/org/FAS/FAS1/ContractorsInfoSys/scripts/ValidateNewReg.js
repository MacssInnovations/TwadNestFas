
var req=false;
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
function nullCheck()
{
    if((frmNewRegn.txtContId.value=="") || (frmNewRegn.txtContId.value.length<=0))
    {
         alert("Please Enter Contractor ID");
         frmNewRegn.txtContId.focus();
         return false;
    }  
   
    if((frmNewRegn.txtContName.value=="") || (frmNewRegn.txtContName.value.length<=0))
    {
         alert("Please Enter Contractor Name");
         frmNewRegn.txtContName.focus();
         return false;
    }
 
    if((frmNewRegn.txtCompName.value=="") || (frmNewRegn.txtCompName.value.length<=0))
    {
         alert("Please Enter Company Name");
         frmNewRegn.txtCompName.focus();
         return false;
    }
    if((frmNewRegn.txtadd1.value=="") || (frmNewRegn.txtadd1.value.length<=0))
    {
         alert("Please Enter Address");
         frmNewRegn.txtadd1.focus();
         return false;
    }
    
    if((frmNewRegn.txtadd3.value=="") || (frmNewRegn.txtadd3.value.length<=0))
    {
         alert("Please Enter City");
         frmNewRegn.txtadd3.focus();
         return false;
    }
    
 if((frmNewRegn.txtPincode.value=="") || (frmNewRegn.txtPincode.value.length<=0))
    {
         alert("Please Enter Pincode");
         frmNewRegn.txtPincode.focus();
         return false;
    }
     if((frmNewRegn.txtCmbDistrict.value=="") || (frmNewRegn.txtCmbDistrict.value.length<=0))
    {
         alert("Please Enter District");
         frmNewRegn.txtCmbDistrict.focus();
         return false;
    } 
    
    if((frmNewRegn.txtOffId.value=="") || (frmNewRegn.txtOffId.value.length<=0))
    {
         alert("Please Enter Office Id");
         frmNewRegn.txtOffId.focus();
         return false;
    } 
    
    if((frmNewRegn.txtRegnNo.value=="") || (frmNewRegn.txtRegnNo.value.length<=0))
    {
         alert("Please Enter Registration Serial No");
         frmNewRegn.txtRegnNo.focus();
         return false;
    } 
    if((frmNewRegn.txtRef_FileNo.value=="") || (frmNewRegn.txtRef_FileNo.value.length<=0))
    {
         alert("Please Enter Reference File No");
         frmNewRegn.txtRef_FileNo.focus();
         return false;
    } 
  return true;
  
}

//function to validate the date
function DateCheck()
{ 

 alert("date check");
 
 //getting entered date
 var dt=document.frmNewRegn.DateOfRegn.value;
  var userDate=new Date(dt);
 var today = new Date();
  if(today<userDate)
  {
  alert("cannot take this value");
  return false;
  }
 return true;
  
}

// this function checks that no alphabets cannot
//   be entered in a numeric field
function isInteger(param,e)
{     
       var nav4 = window.Event ? true : false;
       if (nav4)    // Navigator 4.0x
            var whichCode = e.which
       else         // Internet Explorer 4.0x
            var whichCode = e.keyCode
      if((whichCode>=48 && whichCode<=57)||(whichCode>=97 && whichCode<=105))
      {
          return true;
      }
      var str=param.value;
      param.value=str.substring(0,str.length-1);
      return false;              
}

//Code to verify the duplication of existing Id
function Verify()
{

     // servlet Request sent to newRegnServlet             
          var strContractorId=document.frmNewRegn.txtContId.value;
               url="../../../../../newRegnServlet.view?command=verify&id=" + strContractorId;
                req.open("GET",url,true); 
                req.onreadystatechange=verifyRes;
               req.send(null);
  
}

//Xml response returned From newregnServlet

 function verifyRes()
  {
       if(req.readyState==4)
        {
       
          if(req.status==200)
          {        
            
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              verifyConId(baseResponse);
             
          }
          
      }    
  }
  //function called by verifyRes()
  function verifyConId(baseResponse)
  {
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {
       alert("Contractor Id already Exist please Enter a new Id");
       document.frmNewRegn.txtContId.value=""
       document.frmNewRegn.txtContId.focus();
       }
  
  }
  
  //code to validate the Registration No
  function verifyRegNo()
  {
  //alert("reg no");
        var strregno=document.frmNewRegn.txtRegnNo.value;
          alert(strregno);
            url="../../../../../newRegnServlet.view?command=verifyRNO&RNO=" + strregno;
          
                req.open("GET",url,true); 
               
               req.onreadystatechange=verifyRNORes;
               req.send(null);
  
  }
  //function called by verifyRegNo()
function verifyRNORes()
  {
       if(req.readyState==4)
        {
       
          if(req.status==200)
          {        
            
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {
       alert("Registration No already Exist please Enter a new Registration Id");
       document.frmNewRegn.txtRegnNo.value=""
        document.frmNewRegn.txtRegnNo.focus();
       }
      
  
      }
             
    }
          
 }    
  
//function to validate the Email Text Field

function validate()
{
var emailID=document.frmNewRegn.txtEmail;
if (echeck(emailID.value)==false)
{
                emailID.value=""
                 emailID.focus()
                return false
}
                   return true
 }


function echeck(str) 
            {
                var at="@"
                var dot="."
                var lat=str.indexOf(at)
                var lstr=str.length
                var ldot=str.indexOf(dot)
                   if (str.indexOf(at)==-1)
                   {
                        alert("Invalid E-mail ID")
                       return false
                   }
                   if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr)
                   {
                         alert("Invalid E-mail ID")
                      return false
                   }
                   if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr)
                   {
                         alert("Invalid E-mail ID")
                     return false
                   }
                   if (str.indexOf(at,(lat+1))!=-1)
                   {
                         alert("Invalid E-mail ID")
                     return false
                   }
                   if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot)
                   {
                          alert("Invalid E-mail ID")
                       return false
                   }
                   if (str.indexOf(dot,(lat+2))==-1)
                   {
                          alert("Invalid E-mail ID")
                       return false
                   }
                   if (str.indexOf(" ")!=-1)
                   {
                          alert("Invalid E-mail ID")
                       return false
                   }
                   return true
            }
            

//function to validate the pincode            
 function checkPincode()
{

if(isNaN(frmNewRegn.txtPincode.value)) 
              {
                alert('Enter Pincode in Number');
                frmNewRegn.txtPincode.value="";
                frmNewRegn.txtPincode.focus();
            		return false;
              }
              return true;
}



