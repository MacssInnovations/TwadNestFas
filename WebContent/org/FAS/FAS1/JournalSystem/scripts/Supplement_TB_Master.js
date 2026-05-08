var Common_branchID="";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  

var window_BankAccNumber;

function List()
{ 
        var  txtCashbook_Year      = document.getElementById("txtCashbook_Year").value;
        var  txtCashbook_Month     = document.getElementById("txtCashbook_Month").value;
            
      
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
         window_BankAccNumber= window.open("Supplement_TB_Master_List.jsp?Command=Load_Supplement_No&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);     
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function doParentBankAccNumbers(txtSupplement_No , txtSuppl_Closure_date , txtStatus, txtRemarks )
{
            var d=document.getElementById("cmdFreeze");
            d.style.display="block";
            var d2=document.getElementById("cmdUnfreeze");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
   
    document.getElementById("txtSupplement_No").value=txtSupplement_No;
    document.getElementById("txtSuppl_Closure_date").value=txtSuppl_Closure_date;
    if(txtStatus=="L")
      document.frmSupplementTB.txtStatus[0].checked=true;
    if(txtStatus=="C")
      document.frmSupplementTB.txtStatus[1].checked=true;    
    
    document.getElementById("txtRemarks").value=txtRemarks; 
    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doFunction_Supplement_TB(Command,param)
{   
    
   var  txtSupplement_No      = document.getElementById("txtSupplement_No").value;
   var  txtDate               = document.getElementById("txtDate").value;
   var  txtCashbook_Year      = document.getElementById("txtCashbook_Year").value;
   var  txtCashbook_Month     = document.getElementById("txtCashbook_Month").value;
   var  txtSuppl_Closure_date = document.getElementById("txtSuppl_Closure_date").value;
   
   
   var  txtStatus ="";   
   
      if(document.frmSupplementTB.txtStatus[0].checked==true )
         txtStatus="L";       
      else if(document.frmSupplementTB.txtStatus[1].checked==true )
         txtStatus="C";        
   
  // alert(txtStatus);
   
   var  txtRemarks  = document.getElementById("txtRemarks").value; 
   
    
        if(Command=="Add")
        {
            var flag=nullcheck('add');
            if(flag==true)
               {
                var url="../../../../../Supplement_TB_Master.kv?Command=Add&txtSupplement_No="+txtSupplement_No+"&txtDate="+txtDate+
                        "&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month+"&txtSuppl_Closure_date="+txtSuppl_Closure_date+                       
                        "&txtStatus="+txtStatus+"&txtRemarks="+txtRemarks;
                        
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_bill(req);
                }   
                        req.send(null);
                }
        }
        else if(Command=="Unfreeze")
        {
        
            if(confirm("Do You Really want to UnFreeze it?"))
            {
                
               var flag=nullcheck('Unfreeze');
               if(flag==true)
               {  
                  var url="../../../../../Supplement_TB_Master.kv?Command=Unfreeze&txtSupplement_No="+txtSupplement_No+"&txtDate="+txtDate+
                        "&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month+"&txtSuppl_Closure_date="+txtSuppl_Closure_date+                       
                        "&txtStatus="+txtStatus+"&txtRemarks="+txtRemarks;
                        
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
       else if(Command=="Freeze")
       {
            var flag=nullcheck('Freeze');
            if(flag==true)
             {
                var url="../../../../../Supplement_TB_Master.kv?Command=Freeze&txtSupplement_No="+txtSupplement_No+"&txtDate="+txtDate+
                        "&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month+"&txtSuppl_Closure_date="+txtSuppl_Closure_date+                       
                        "&txtStatus="+txtStatus+"&txtRemarks="+txtRemarks;
               
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
            else if(Command=="Unfreeze")
            {
                UnfreezeRow(baseResponse);
            }
            
            else if(Command=="Freeze")
            {
                FreezeRow(baseResponse);
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

function UnfreezeRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Record has been UnFreeze Successfully");
         ClearAll();
    }
    else
    {
        alert("Record not UnFreeze from database");
    }
}  

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function FreezeRow(baseResponse)
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
{
    document.getElementById("txtSupplement_No").value="";
    document.getElementById("txtSuppl_Closure_date").value="";    
    document.frmSupplementTB.txtStatus[0].checked=true    
    document.getElementById("txtRemarks").value="";

        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdFreeze");
        d1.style.display="none";
        var d3=document.getElementById("cmdUnfreeze");
        d3.style.display="none";
        
    Load_Supplement_No();    
    
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function nullcheck(nccommand)
{
    if(document.getElementById("txtSupplement_No").value.length==0)
    {
        alert("Enter the Supplement Nubmer ");
        document.getElementById("txtSupplement_No").focus();
        return false;
    }
   if(document.getElementById("txtDate").value.length==0)
    {
        alert("Enter the Date ");
        document.getElementById("txtDate").focus();
        return false;
    }
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
    
    if(document.getElementById("txtSuppl_Closure_date").value.length==0)
    {
        alert("Enter the Supplement Closure Date");
        document.getElementById("txtSuppl_Closure_date").focus();
        return false;
    }   
   
      
    return true;
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
 
      
function Load_Supplement_No()
{
                var  txtCashbook_Year      = document.getElementById("txtCashbook_Year").value;
                var  txtCashbook_Month     = document.getElementById("txtCashbook_Month").value;
                    
                var url="../../../../../Supplement_TB_Master.kv?Command=Load_Supplement_No&txtCashbook_Year="+txtCashbook_Year+"&txtCashbook_Month="+txtCashbook_Month;
              
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_Supplement_No(req);
                }   
                req.send(null);
              


}

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



function Date_Assign()
{  
           try
           { 
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
                       
            document.getElementById("txtDate").value="31/03/"+year;
            
           } 
           catch(e)
           {
              alert(e.description);
           }

}



function YearLoad()
{
      var txtCashbook_Year = document.getElementById("txtCashbook_Year");      
      
      var today= new Date(); 
      var year=today.getFullYear();
      var i; 
      
     for( i=2008;i<=year; i++) 
     {
      
        var option11=document.createElement("option");    
        option11.value=i;
        option11.text=i;      
        try
         {
             txtCashbook_Year.add(option11);
         }
         catch(e)
         {
             txtCashbook_Year.add(option11,null);
         }   
     }       
     
     document.getElementById("txtCashbook_Year").value=year;

}


function YearChange()
{
     document.getElementById("txtDate").value="31/03/"+ document.getElementById("txtCashbook_Year").value;
     Load_Supplement_No();
}

