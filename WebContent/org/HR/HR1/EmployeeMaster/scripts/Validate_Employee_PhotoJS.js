var imgpath;
var OfficeId;
var my_window;
//alert("offid "  + OfficeId);
var DefaultImage="../../../../../images/sample_emp.bmp";


//alert("Show");


function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callFunction('ExistgBasic','null');
}

window.onunload=function()
{
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

///////////////////////////////////////////////////////////////////////////////////



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



var s=0;



function callFunction(command,param)
{
//alert("inside");
document.getElementById("Validate").disabled=false;
    if(command=="ExistgBasic")
       {
      // alert(command);
         var strEmpId=document.frmEmployee.txtEmpId1.value;
         //startwaiting(document.frmEmployee) ; 
         //alert(strEmpId);
          url="../../../../../Validate_Employee_PhotoServ.con?command=ExistgBasic&EmpId=" + strEmpId;
            var req=getTransport();
                    // alert(req);
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
           // alert("show");
               processHandleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
       }
     
                    
        }

function processHandleResponse(req)
    {   
    //alert(req);
      if(req.readyState==4)
        {
          if(req.status==200)
          {            
          stopwaiting(document.frmEmployee) ;
          
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
               //alert("show me");
               if(command=="ExistgBasic")
              {
                  existBasicRow(baseResponse);                 
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
   
function existBasicRow(baseResponse)
{
var strEmpId=document.frmEmployee.txtEmpId1.value;
//alert(strEmpId);
  
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
  //alert(flag);
   
  
   
            if(flag=="EmpDet")
                    {
                       var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                   if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                 
                  document.frmEmployee.Employee_Initial.value=einit;
                  
                   
                  
                  
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(egpf==0)
                     egpf=0;
                  else   
                     document.frmEmployee.Gpf_Number.value=egpf;
                     
                 var epho=baseResponse.getElementsByTagName("EmpPhoto")[0].firstChild.nodeValue; 
                 //alert(epho);
                  //if((epho.substr(epho.lastIndexOf(".jpg"))==".jpg")||(epho.substr(epho.lastIndexOf(".gif"))==".gif"))
                  var exist=(epho.substring(epho.length-3,epho.length)).toLowerCase();
                  //alert(exist);
                  if((exist != "jpg") && (exist!="gif"))
                                {
                                /*epho="twad.jpg";
                                document.getElementById("EmpImage").src="";
                                    document.getElementById("Validate").disabled=true;
                                    alert("Cannot be validated. Photo has not been uploaded");*/
                                       
                                    alert("Cannot be validated. Photo has not been uploaded");                              
                                epho="twad.jpg";
                                    document.getElementById("EmpImage").src="";
                                     /*document.frmEmployee.txtEmpId1.value="";
                                    document.frmEmployee.Employee_Initial.value="";
                                    document.frmEmployee.Employee_Name.value="";
                                    document.frmEmployee.Gpf_Number.value="";
                                    document.frmEmployee.Employee_Prefix.value="";*/
                                    document.getElementById("Validate").disabled=true;
                                    
                                  }  
                        
                                else   
                                    {
                                    alert("aaa");
                                   // document.getElementById("EmpImage").src=epho;
                                    document.getElementById("EmpImage").src=imgpath+epho;
                                    }
                                   // document.getElementById("Validate").disabled=false;
                    }
                    else if (flag=="exists")
                        {
                        //alert("Given Employee Photo already exist.");
                        /*
                        if(confirm("Do you want to Validate?"))
                            {*/
                            
                            var flag1=baseResponse.getElementsByTagName("flag")[1].firstChild.nodeValue; 
                            if(flag1=="EmpDet")
                            {
                                var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                                 if(epref==null)
                                    epref='Mr';
                                else   
                                    document.frmEmployee.Employee_Prefix.value=epref;
                  
                                var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                                if(einit==null)
                                     einit="";
                                else if(einit=='null')
                                    einit="";
                 
                                    document.frmEmployee.Employee_Initial.value=einit;
                  
                   
                  
                  
                                var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                                if(ename==null)
                                     ename="Not Specified";
                                else
                                        document.frmEmployee.Employee_Name.value=ename;
                  
                                    var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                                if(egpf==0)
                                    egpf=0;
                                else   
                                    document.frmEmployee.Gpf_Number.value=egpf;
                                    
                                var epho=baseResponse.getElementsByTagName("EmpPhoto")[0].firstChild.nodeValue; 
                                //alert(epho);
                                
                                //var a=epho.String().substring(0,4);
                                //alert(a);
                               //if((epho.substr(epho.lastIndexOf(".jpg"))==".jpg")||(epho.substr(epho.lastIndexOf(".gif"))==".gif"))
                                var exist=(epho.substring(epho.length-3,epho.length)).toLowerCase();
                                //alert(exist);
                                 if((exist == "jpg") || (exist=="gif"))
                                {
                                //alert("aaa");
                                   // document.getElementById("EmpImage").src=epho;
                                    document.getElementById("EmpImage").src=imgpath+epho;
                                  }  
                        
                                else   
                                    {
                                    epho="twad.jpg";
                                   /* document.getElementById("EmpImage").src="";
                                     document.frmEmployee.txtEmpId1.value="";
                                    document.frmEmployee.Employee_Initial.value="";
                                    document.frmEmployee.Employee_Name.value="";
                                    document.frmEmployee.Gpf_Number.value="";
                                    document.frmEmployee.Employee_Prefix.value="";*/
                                    document.getElementById("Validate").disabled=true;
                                    alert("Cannot be Validated. Photo has not been uploaded");
                                    
                                    
                                    }
                                document.getElementById("Validate").disabled=false;
                                 
                                 var flag2=baseResponse.getElementsByTagName("flag")[2].firstChild.nodeValue;
                                 if(flag2=="cancel")
                                     {
                                     alert("This Employee Id is not in your SR Controlling List. You are not allowed to Validate");
                                     document.getElementById("Validate").disabled=true;
                                     }
                             }
                            else
                            {
                            document.getElementById("Validate").disabled=true;
                            //document.frmEmployee.Employee_Initial.disabled=true;
                            }
                           
                            //document.frmEmployee.method="Get";
                            //document.frmEmployee.action="../../../../../InsertEmployee_11.con?command=ExistgBasic&EmpId=" + strEmpId;
                            //document.frmEmployee.submit();
                           // callFunction('ExistgBasic','');
                             
                            
                        
                        }
                    else if (flag=="freezed")
                        {
                        var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                   if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                 
                  document.frmEmployee.Employee_Initial.value=einit;
                  
                   
                  
                  
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(egpf==0)
                     egpf=0;
                  else   
                     document.frmEmployee.Gpf_Number.value=egpf;
                     
                 var epho=baseResponse.getElementsByTagName("EmpPhoto")[0].firstChild.nodeValue; 
                 //alert(epho);
                // if((epho.substr(epho.lastIndexOf(".jpg"))==".jpg")||(epho.substr(epho.lastIndexOf(".gif"))==".gif"))
                 var exist=(epho.substring(epho.length-3,epho.length)).toLowerCase();
                  //alert(exist);
                  if((exist != "jpg") && (exist!="gif"))
                                {
                                /*epho="twad.jpg";
                                document.getElementById("EmpImage").src="";
                                    document.getElementById("Validate").disabled=true;
                                    alert("Cannot be validated. Photo has not been uploaded");*/
                                       
                                       
                                epho="twad.jpg";
                                    /*document.getElementById("EmpImage").src="";
                                     document.frmEmployee.txtEmpId1.value="";
                                    document.frmEmployee.Employee_Initial.value="";
                                    document.frmEmployee.Employee_Name.value="";
                                    document.frmEmployee.Gpf_Number.value="";
                                    document.frmEmployee.Employee_Prefix.value="";*/
                                    document.getElementById("Validate").disabled=true;
                                    alert("Cannot be Validated. Photo has not been uploaded");                           
                                  }  
                        
                                else   
                                    {
                                    //alert("aaa");
                                    //document.getElementById("EmpImage").src=epho;
                                    document.getElementById("EmpImage").src=imgpath+epho;
                                    }
                                    
                              var flag2=baseResponse.getElementsByTagName("flag")[2].firstChild.nodeValue;
                                 if(flag2=="cancel")
                                     {
                                     alert("This Employee Id is not in your SR Controlling List. You are not allowed to Validate");
                                     document.getElementById("Validate").disabled=true;
                                     }    
                        
                        alert('Given Employee Id is already freezed');
                        document.frmEmployee.txtEmpId1.value="";
                     document.frmEmployee.Employee_Initial.value="";
                     document.frmEmployee.Employee_Name.value="";
                     document.frmEmployee.Gpf_Number.value="";
                     document.frmEmployee.Employee_Prefix.value="";
                     document.getElementById("Validate").disabled=true;
                     document.getElementById("EmpImage").src="";
                        }
                    
              else
             {
                     //alert("Failed to Load Values");
                     alert("No such Employee Id exists");
                     document.frmEmployee.txtEmpId1.value="";
                     document.frmEmployee.Employee_Initial.value="";
                     document.frmEmployee.Employee_Name.value="";
                     document.frmEmployee.Gpf_Number.value="";
                     document.frmEmployee.Employee_Prefix.value="";
                     document.getElementById("Validate").disabled=true;
                     document.getElementById("EmpImage").src="";
             }
}


function toFocus()
{
 
  if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     //document.frmEmployee.txtEmpId1.focus();
     //document.getElementById("Validate").disabled=false;
     return false;
  }
   
}

     
//This Coding for Date Validation and Checking     
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
     
     
    
     
     
    