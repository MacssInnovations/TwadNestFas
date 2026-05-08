var Common_branchID="";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  

var window_BankAccNumber;

function List()
{ 
      // alert("inside list");
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
         window_BankAccNumber= window.open("Supplement_Minus_Master_List.jsp","mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
 
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function doParentBankAccNumbers(txtCashbook_Year, txtCashbook_Month,   txtStatus_GJV, txtStatus_SJV,  txtRemarks)
{
            var d=document.getElementById("cmdFreeze");
            d.style.display="block";
            var d2=document.getElementById("cmdUnfreeze");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
   
    document.getElementById("txtCashbook_Year").value=txtCashbook_Year;
    document.getElementById("txtCashbook_Month").value=txtCashbook_Month;
    
    if(txtStatus_GJV=="Y")
      document.frmSupplement_Minus.txtStatus_GJV[0].checked=true;
    if(txtStatus_GJV=="N")
      document.frmSupplement_Minus.txtStatus_GJV[1].checked=true;   
      if(txtStatus_SJV=="Y")
      document.frmSupplement_Minus.txtStatus_SJV[0].checked=true;
    if(txtStatus_SJV=="N")
      document.frmSupplement_Minus.txtStatus_SJV[1].checked=true; 
    
    document.getElementById("txtRemarks").value=txtRemarks; 
    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doFunction_Supplement_TB(Command,param)
{   
    
 //  var  txtSupplement_No      = document.getElementById("txtSupplement_No").value;
  // var  txtDate               = document.getElementById("txtDate").value;
   var  txtCashbook_Year      = document.getElementById("txtCashbook_Year").value;
   var  txtCashbook_Month     = document.getElementById("txtCashbook_Month").value;
 //  var  txtStatus_GJV              = document.getElementById("txtStatus_GJV").value;
 //  alert(txtStatus_GJV);
 //  var  txtStatus_SJV              = document.getElementById("txtStatus_SJV").value;
 //     alert(txtStatus_SJV);
   var  txtRemarks            = document.getElementById("txtRemarks").value;
   
   
   var  txtStatus_GJV ="";   
  
      if(document.frmSupplement_Minus.txtStatus_GJV[0].checked==true )
         txtStatus_GJV="Y";       
      else if(document.frmSupplement_Minus.txtStatus_GJV[1].checked==true )
         txtStatus_GJV="N"; 
   
   var  txtStatus_SJV="";
   if(document.frmSupplement_Minus.txtStatus_SJV[0].checked==true )
         txtStatus_SJV="Y";       
      else if(document.frmSupplement_Minus.txtStatus_SJV[1].checked==true )
         txtStatus_SJV="N"; 
   
    
        if(Command=="Add")
        {
            var flag=nullcheck('add');
            if(flag==true)
               {
                var url="../../../../../Supplement_Minus_Master?Command=Add&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month+"&txtStatus_GJV="+txtStatus_GJV+                       
                        "&txtStatus_SJV="+txtStatus_SJV+"&txtRemarks="+txtRemarks;
               // alert(url);       
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_bill(req);
                }   
                        req.send(null);
                }
        }
        else if(Command=="delete")
        {
        
            if(confirm("Do You Really want to delete it?"))
            {
                
               var flag=nullcheck('delete');
               if(flag==true)
               {  
                  var url="../../../../../Supplement_Minus_Master?Command=delete&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month;
                        
                        
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
       else if(Command=="update")
       {
            var flag=nullcheck('update');
            if(flag==true)
             {
                var url="../../../../../Supplement_Minus_Master?Command=update&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month+"&txtStatus_GJV="+txtStatus_GJV+                       
                        "&txtStatus_SJV="+txtStatus_SJV+"&txtRemarks="+txtRemarks;
               
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
            else if(Command=="delete")
            {
                DeleteRow(baseResponse);
            }
            
            else if(Command=="update")
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

function DeleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Record has been delete Successfully");
         ClearAll();
    }
    else
    {
        alert("Record not delete from database");
    }
}  

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        alert("Record has been Frozen Successfully");
        ClearAll();
    }
    else
    {
        alert("Record not Froze");
    }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function ClearAll()
{  //&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month+"&txtStatus_GJV="+txtStatus_GJV+                       
                      //  "&txtStatus_SJV="+txtStatus_SJV+"&txtRemarks="+txtRemarks;
    document.getElementById("txtCashbook_Year").value="";
    document.getElementById("txtCashbook_Month").value="";    
    document.frmSupplement_Minus.txtStatus_GJV[0].checked=true    
    document.frmSupplement_Minus.txtStatus_SJV[0].checked=true 
    document.getElementById("txtRemarks").value="";

        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdFreeze");
        d1.style.display="none";
        var d3=document.getElementById("cmdUnfreeze");
        d3.style.display="none";
        
   // Load_Supplement_No();    
    
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function nullcheck(nccommand)
{
    
    if(document.getElementById("txtCashbook_Year").value.length==0)
    {
        alert("Enter Cashbook Year ");
        document.getElementById("txtCashbook_Year").focus();
        return false;
    }
    if(document.getElementById("txtCashbook_Month").value.length==0)
    {
        alert("Enter Cashbook Month ");
        document.getElementById("txtCashbook_Month").focus();
        return false;
    }   
    
    
   
      
    return true;
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
 
      


function handleResponse_Supplement_No(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
        
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="Load_Supplement_No")
            {
                getSupplNo(baseResponse);
            }
           
           
        }
    }
}


function getSupplNo(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    { 
         var Supplement_No=baseResponse.getElementsByTagName("Supplement_No")[0].firstChild.nodeValue; 
         document.getElementById("txtSupplement_No").value=Supplement_No;
    }
    else
    {
        alert("Failed To Load Supplement Number");
    }
}
