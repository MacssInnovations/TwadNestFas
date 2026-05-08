var Common_branchID="";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  

var window_BankAccNumber;

function ListHeads()
{ 
 
 
        if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
         {
             window_BankAccNumber.resizeTo(500,500);
             window_BankAccNumber.moveTo(250,250); 
             window_BankAccNumber.focus();
         }
         else
         {
             window_BankAccNumber=null
         }
         window_BankAccNumber= window.open("BRS_Reason_Catalogue_List.jsp","mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
 
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doParentBankAccNumbers(reason_code,reason_short_desc, reason_desc )
{
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
       
    document.getElementById("txtReasonCode").value=reason_code;
    document.getElementById("txtReasonShortDesc").value=reason_short_desc;
    document.getElementById("txtReasonDesc").value=reason_desc;            
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


/**
 *   Main Function 
 */

function doFunction_brr(Command,param)
{   

   var  txtReasonCode       = document.getElementById("txtReasonCode").value;
   var  txtReasonShortDesc  = document.getElementById("txtReasonShortDesc").value;
   var  txtReasonDesc       = document.getElementById("txtReasonDesc").value;   
   
            
        if(Command=="Add")
        {
            var flag=nullcheck('add');
            if(flag==true)
               {
                var url="../../../../../BRS_Reason_Catalogue.kv?Command=Add&txtReasonCode="+txtReasonCode+"&txtReasonShortDesc="+txtReasonShortDesc+
                        "&txtReasonDesc="+txtReasonDesc;
            
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_bill(req);
                }   
                        req.send(null);
                }
        }
        else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Delete it?"))
            {
                
               var flag=nullcheck('delete');
               if(flag==true)
               {  
                    var url="../../../../../BRS_Reason_Catalogue.kv?Command=Delete&txtReasonCode="+txtReasonCode;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse_bill(req);
                    }   
                            req.send(null);
               }
            }
           
       }
       else if(Command=="Update")
       {
            var flag=nullcheck('update');
            if(flag==true)
             {
                var url="../../../../../BRS_Reason_Catalogue.kv?Command=Update&txtReasonCode="+txtReasonCode+"&txtReasonShortDesc="+txtReasonShortDesc+
                        "&txtReasonDesc="+txtReasonDesc;            
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_bill(req);
                }   
                        req.send(null);
              }
       }
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   handleResponse()   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function handleResponse_bill(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
         
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;           
           
            if(Command=="Add")
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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record Updated Successfully");
        ClearAll();
    }
    else
    {
        alert("Record not Updated");
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function ClearAll()
{    
    document.getElementById("txtReasonCode").value="";
    document.getElementById("txtReasonShortDesc").value="";
    document.getElementById("txtReasonDesc").value="";    
        
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
        
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function nullcheck(nccommand)
{
     if(document.getElementById("txtReasonShortDesc").value.length==0)
     {
        alert("Enter Reason Short Description ");
        document.getElementById("txtReasonShortDesc").focus();
        return false;
     }
     if(document.getElementById("txtReasonDesc").value.length==0)
     {
        alert("Enter Reason Description ");
        document.getElementById("txtReasonDesc").focus();
        return false;
     }
   
     return true;    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
 
function exit()
{
	self.close();
}

