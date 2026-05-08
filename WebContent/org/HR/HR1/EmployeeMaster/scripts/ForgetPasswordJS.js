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


function notNull(p)
{


    if((document.frmforgetpassword.txtusername.value==null)||(document.frmforgetpassword.txtusername.value.length==0))
    {
        alert("Enter User Name");
        document.frmforgetpassword.txtusername.value="";
       document.frmforgetpassword.txtusername.focus();
        return false;
    }
    else if((document.frmforgetpassword.txtdob.value==null)||(document.frmforgetpassword.txtdob.value.length==0))
    {
        alert("Enter DOB");
        document.frmforgetpassword.txtdob.value="";
       document.frmforgetpassword.txtdob.focus();
        return false;
    }
    else if((document.frmforgetpassword.txtgpf.value==null)||(document.frmforgetpassword.txtgpf.value.length==0))
    {
        alert("Enter GPF No.");
        document.frmforgetpassword.txtgpf.value="";
       document.frmforgetpassword.txtgpf.focus();
        return false;
    }
    
    return true;
}

function doFunction(Command,param)
{
    //alert("command:"+Command);
   
    
    var empid=document.frmforgetpassword.txtusername.value;
    var dob=document.frmforgetpassword.txtdob.value;
    var gpf=document.frmforgetpassword.txtgpf.value;
    
    
  
    if(Command=='test')
    {
       
        var check=notNull(null);
        //alert(check);
        if(check )
        {
               // startwaiting(document.HRE_EmployeeServiceDetails) ;
                var url="../../../../../ForgetPasswordServ.view?Command=test&txtusername="+empid+"&txtdob="+dob+"&txtgpf="+gpf;
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
             if(Command=="test")
                {
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
                    if(flag=="failure1")
                    {
                        alert('Invalid User Name!');
                        document.frmforgetpassword.txtusername.value="";
                        document.frmforgetpassword.txtdob.value="";
                        document.frmforgetpassword.txtgpf.value="";
                       document.frmforgetpassword.txtusername.focus();
                        return false;
                    }
                    else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                    else if(flag=="failure")
                    {
                        alert('Invalid User Information!');
                        document.frmforgetpassword.txtusername.value="";
                        document.frmforgetpassword.txtdob.value="";
                        document.frmforgetpassword.txtgpf.value="";
                       document.frmforgetpassword.txtusername.focus();
                        return false;
                    }
                    else
                    {
                            alert('New Password will be sent to your E-mail');
                            self.close();
                            
                    }
                }
            }
    }
}


function checkdtdob(t)
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
            //t.focus();
            return false
    }
    
}


function checkempid()
{
    if(document.frmforgetpassword.txtusername.value==null || document.frmforgetpassword.txtusername.value.length==0)
    {
            alert('Enter User Name');
            document.frmforgetpassword.txtusername.focus();
            return false;
    }
   
    return true;

}

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
       if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }