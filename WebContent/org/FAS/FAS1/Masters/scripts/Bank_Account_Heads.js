////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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

var window_accHeadList;
function ListHeads()
    {
    
     if (window_accHeadList && window_accHeadList.open && !window_accHeadList.closed) 
    {
       window_accHeadList.resizeTo(500,500);
       window_accHeadList.moveTo(250,250); 
       window_accHeadList.focus();
    }
    else
    {
        window_accHeadList=null
    }
         //var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value  ;  
        // var cmbOffice_code=document.getElementById("comOffId").value;
        // window_accHeadList= window.open("FirmListJSP.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_accHeadList= window.open("Bank_Account_Heads_List.jsp?","mywindow1","resizable=YES, scrollbars=yes"); 
         window_accHeadList.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_accHeadList && window_accHeadList.open && !window_accHeadList.closed) window_accHeadList.close();
}

function doParentBankAccHeads(accHeadCode,accHeadDesc,bankid,operID)
{
    //alert(accHeadCode)
    //alert(accHeadDesc)
    //alert(bankid)
    //alert(operID)
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            //var d2=document.getElementById("cmdDelete");
            //d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            
        document.getElementById("txtAcc_HeadCode").value=accHeadCode;
        //document.getElementById("txtAcc_HeadCode").readOnly=true;
        document.getElementById("txtAcc_HeadDesc").value=accHeadDesc;
        document.getElementById("cmbBankId").value=bankid;
        document.getElementById("cmbOperation_mode").value=operID;
}
function doFunction(Command,param)
{  
    var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
    var cmbBankId=document.getElementById("cmbBankId").value;
    var cmbOperation_mode=document.getElementById("cmbOperation_mode").value;
        
       if(Command=="checkCode")
        {  
            if(((document.getElementById("txtAcc_HeadCode").value).substring(0,1))!=8 )
            {
                alert("Account head Code should start with 82..");
                document.getElementById("txtAcc_HeadCode").value="";
                document.getElementById("txtAcc_HeadCode").focus();
                return false;
            }
            else if(((document.getElementById("txtAcc_HeadCode").value).substring(0,1))==8 && ((document.getElementById("txtAcc_HeadCode").value).substring(1,2))!=2)
            {
                alert("Account head Code should start with 82..");
                document.getElementById("txtAcc_HeadCode").value="";
                document.getElementById("txtAcc_HeadCode").focus();
                return false;
            }
            else if(txtAcc_HeadCode.length>=6)
            {
                var url="../../../../../Bank_Account_Heads.view?Command=checkCode&txtAcc_HeadCode="+txtAcc_HeadCode;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }         
        }
       else if(Command=="Add")
        {
            var flag=nullcheck();
            if(flag==true)
               {
                var url="../../../../../Bank_Account_Heads.view?Command=Add&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbBankId="+cmbBankId+"&cmbOperation_mode="+cmbOperation_mode;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
                }
        }
        else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Delete it?"))
            {
               if(txtAcc_HeadCode.length>=6)
               {  
                    var url="../../../../../Bank_Account_Heads.view?Command=Delete&txtAcc_HeadCode="+txtAcc_HeadCode;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
               }
            }
           
       }
       else if(Command=="Update")
       {
            var flag=nullcheck();
            if(flag==true)
             {
                var url="../../../../../Bank_Account_Heads.view?Command=Update&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbBankId="+cmbBankId+"&cmbOperation_mode="+cmbOperation_mode;
                //alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
              }
       }
}


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="checkCode")
            {
                loadcheckCode(baseResponse);
            }
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
        }
    }
}

function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Record inserted into database");
        ClearAll();
    }
    else
    {
        alert("Record not inserted into database");
    }
}

function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records deleted from database");
         ClearAll();
    }
    else
    {
        alert("Record not deleted from database");
    }
}  

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated");
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}
function ClearAll()
{
        //document.getElementById("txtAcc_HeadCode").readOnly=false;
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadDesc").value="";
        document.getElementById("cmbBankId").value="";
        document.getElementById("cmbOperation_mode").value="";
         var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        //var d3=document.getElementById("cmdDelete");
        //d3.style.display="none";
}
/////////////////////////////////////////////   loadcheckCode() by User /////////////////////////////////////////////////////
function loadcheckCode(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
       var hid=baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
       var hdesc=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
       document.getElementById("txtAcc_HeadCode").value=hid;
       document.getElementById("txtAcc_HeadDesc").value=hdesc;
      
    }
     else if(flag=="failure")
     {
         alert("Account Head Code '"+document.getElementById("txtAcc_HeadCode").value+"' doesn't Exist");
         document.getElementById("txtAcc_HeadCode").value="";
         document.getElementById("txtAcc_HeadDesc").value="";
         document.getElementById("txtAcc_HeadCode").focus();
     }
     else if(flag=="exist")
     {
        alert("Record already exist to this Account Head ' "+document.getElementById("txtAcc_HeadCode").value+" '");
        document.getElementById("txtAcc_HeadCode").value="";
     }
}

function nullcheck()
{
    if(document.getElementById("txtAcc_HeadCode").value.length==0)
    {
        alert("Enter the A/c head code");
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
    }
    if(document.getElementById("cmbBankId").value=="")
    {
        alert("Select Bank Name");
        document.getElementById("cmbBankId").focus();
        return false;
    }
    if(document.getElementById("cmbOperation_mode").value=="")
    {
        alert("Select Operational mode");
        document.getElementById("cmbOperation_mode").focus();
        return false;
    }
    return true;
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
function sixdigit()
{
 if( document.getElementById("txtAcc_HeadCode").value!=0)
    {
        if(( document.getElementById("txtAcc_HeadCode").value).length<6)
        {
            alert("Account Head Code Shouldn't be less than 6 digit number");
            document.getElementById("txtAcc_HeadCode").focus();
            return false;
        }
        //alert((document.getElementById("txtAcc_HeadCode").value).substring(0,1)!=8)
        //alert((document.getElementById("txtAcc_HeadCode").value).substring(1,2)!=2)
        
    }
}

///////////////////////////////////////  Numbers only fields
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false 
    }
 } 
 
/////////////////////// exit  function

function exit()
{
       self.close();
}