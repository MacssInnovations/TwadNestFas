var req=false;
try
{
    req=new ActiveXObject("Msxml2.XMLHTTP");
}
catch(e)
{
    try
    {
        req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(ee)
    {
        req=false;
    }
}

if(!req || typeof XMLHTTPRequest !='undefined')
{
    req=new XMLHttpRequest();
}

function exitfun() {
	window.close();
}
function notNull(p)
{


    if((document.frmchangepassword.txtoldPassword.value==null)||(document.frmchangepassword.txtoldPassword.value.length==0))
    {
        alert("Enter Old Password");
        document.frmchangepassword.txtoldPassword.value="";
       document.frmchangepassword.txtoldPassword.focus();
        return false;
    }
    else if((document.frmchangepassword.txtnewPassword.value==null)||(document.frmchangepassword.txtnewPassword.value.length==0))
    {
        alert("Enter New Password");
        document.frmchangepassword.txtnewPassword.value="";
       document.frmchangepassword.txtnewPassword.focus();
        return false;
    }
    else if((document.frmchangepassword.txtconfirmPassword.value==null)||(document.frmchangepassword.txtconfirmPassword.value.length==0))
    {
        alert("Enter Confirm Password.");
        document.frmchangepassword.txtconfirmPassword.value="";
       document.frmchangepassword.txtconfirmPassword.focus();
        return false;
    }
    else if(document.frmchangepassword.txtnewPassword.value!=document.frmchangepassword.txtconfirmPassword.value)
    {
        alert("New Password and Confirm Password are not same.");
         document.frmchangepassword.txtnewPassword.value="";
        document.frmchangepassword.txtconfirmPassword.value="";
       document.frmchangepassword.txtnewPassword.focus();
        return false;
    }
    
    return true;
}

function doFunction(Command,param)
{
    //alert("command:"+Command);
   
    
    var oldpass=document.frmchangepassword.txtoldPassword.value;
    var newpass=document.frmchangepassword.txtnewPassword.value;
   
    
    
  
    if(Command=='test')
    {
       
        var check=notNull(null);
        //alert(check);
        if(check )
        {
               // startwaiting(document.HRE_EmployeeServiceDetails) ;
                var url="../../../../../ChangePasswordServ.view?Command=test&txtnewpass="+newpass+"&txtoldpass="+oldpass;
                //alert(url);
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        } 
    }
}

function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
        
         var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
             if(Command=="test")
                {
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
                    if(flag=="failure")
                    {
                        alert('Invalid Old Password!\nPassword not changed');
                           document.frmchangepassword.txtoldPassword.value="";
                         document.frmchangepassword.txtnewPassword.value="";
                            document.frmchangepassword.txtconfirmPassword.value="";
                       document.frmchangepassword.txtoldPassword.focus();
                        return false;
                    }
                    
                    else
                    {
                            alert('Password successfully changed');
                            self.close();
                            
                    }
                }
            }
    }
}




function checkoldpass()
{
     if((document.frmchangepassword.txtoldPassword.value==null)||(document.frmchangepassword.txtoldPassword.value.length==0))
    {
        alert("Enter Old Password");
        document.frmchangepassword.txtoldPassword.value="";
       document.frmchangepassword.txtoldPassword.focus();
        return false;
    }
   
    return true;

}

