var Common_branchID="";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  

var window_BankAccNumber;

function ListHeads()
{ 
   var flag=nullcheck('list');
   if(flag==true)
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
         var  cmbAcc_UnitCode   = document.getElementById("cmbAcc_UnitCode").value;
         var  cmbOffice_code    = document.getElementById("cmbOffice_code").value;
         var  cmbMas_SL_type    = document.getElementById("cmbMas_SL_type").value;
         var  cmbMas_SL_Code    = document.getElementById("cmbMas_SL_Code").value;
         
         window_BankAccNumber= window.open("Bill_Receipt_Register_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbMas_SL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
   }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function doParentBankAccNumbers(cmbAcc_UnitCode , cmbOffice_code , cmbMas_SL_type, cmbMas_SL_Code, txtreceipt_date,txtbill_no, txtbill_date, txtbill_type, txtbill_description, txtbill_Amount, txtreference_book, txtRemarks )
{
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
        
       
    document.getElementById("cmbMas_SL_type").value=cmbMas_SL_type;
    document.getElementById("cmbMas_SL_Code").value=cmbMas_SL_Code;
    document.getElementById("txtreceipt_date").value=txtreceipt_date;
    document.getElementById("txtbill_no").value=txtbill_no;
    document.getElementById("txtbill_date").value=txtbill_date;
    document.getElementById("txtbill_type").value=txtbill_type;
    document.getElementById("txtbill_description").value=txtbill_description;
    document.getElementById("txtbill_Amount").value=txtbill_Amount;
    document.getElementById("txtreference_book").value=txtreference_book;
    document.getElementById("txtRemarks").value=txtRemarks;
    
    document.getElementById("cmbOffice_code").disabled=true;
    document.getElementById("cmbMas_SL_type").disabled=true;
    document.getElementById("cmbMas_SL_Code").disabled=true;
    document.getElementById("txtbill_no").disabled=true;
    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doFunction_brr(Command,param)
{   
    
   var  cmbAcc_UnitCode   = document.getElementById("cmbAcc_UnitCode").value;
   var  cmbOffice_code    = document.getElementById("cmbOffice_code").value;
   var  cmbMas_SL_type    = document.getElementById("cmbMas_SL_type").value;
   var  cmbMas_SL_Code    = document.getElementById("cmbMas_SL_Code").value;
   var  txtreceipt_date   = document.getElementById("txtreceipt_date").value;
   var  txtbill_no        = document.getElementById("txtbill_no").value;
   var  txtbill_date      = document.getElementById("txtbill_date").value;
   var  txtbill_type      = document.getElementById("txtbill_type").value;
   var  txtbill_description = document.getElementById("txtbill_description").value;
   var  txtbill_Amount    = document.getElementById("txtbill_Amount").value;
   var  txtreference_book = document.getElementById("txtreference_book").value;
   var  txtRemarks        = document.getElementById("txtRemarks").value;

     
        if(Command=="Add")
        {
            var flag=nullcheck('add');
            if(flag==true)
               {
                var url="../../../../../bill_receipt_register?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&cmbMas_SL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&txtreceipt_date="+txtreceipt_date+
                        "&txtbill_no="+txtbill_no+"&txtbill_date="+txtbill_date+"&txtbill_type="+txtbill_type+"&txtbill_description="+txtbill_description+
                        "&txtbill_Amount="+txtbill_Amount+"&txtreference_book="+txtreference_book+"&txtRemarks="+txtRemarks;
            
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
                    var url="../../../../../bill_receipt_register?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&cmbMas_SL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&txtreceipt_date="+txtreceipt_date+
                        "&txtbill_no="+txtbill_no+"&txtbill_date="+txtbill_date+"&txtbill_type="+txtbill_type+"&txtbill_description="+txtbill_description+
                        "&txtbill_Amount="+txtbill_Amount+"&txtreference_book="+txtreference_book+"&txtRemarks="+txtRemarks;
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
                var url="../../../../../bill_receipt_register?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&cmbMas_SL_type="+cmbMas_SL_type+"&cmbMas_SL_Code="+cmbMas_SL_Code+"&txtreceipt_date="+txtreceipt_date+
                        "&txtbill_no="+txtbill_no+"&txtbill_date="+txtbill_date+"&txtbill_type="+txtbill_type+"&txtbill_description="+txtbill_description+
                        "&txtbill_Amount="+txtbill_Amount+"&txtreference_book="+txtreference_book+"&txtRemarks="+txtRemarks;
               
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
           
            if(Command=="getBranch")
            {
                getBranch(baseResponse);
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
    clear_Combo2(document.getElementById("cmbMas_SL_Code"));
    document.getElementById("cmbMas_SL_type").value="";
    document.getElementById("txtreceipt_date").value="";
    document.getElementById("txtbill_no").value="";
    document.getElementById("txtbill_date").value="";
    document.getElementById("txtbill_type").value="";
    document.getElementById("txtbill_description").value="";
    document.getElementById("txtbill_Amount").value="";
    document.getElementById("txtreference_book").value="";
    document.getElementById("txtRemarks").value="";
    
    document.getElementById("cmbOffice_code").disabled=false;
    document.getElementById("cmbMas_SL_type").disabled=false;
    document.getElementById("cmbMas_SL_Code").disabled=false;
    document.getElementById("txtbill_no").disabled=false;
        
        var d=document.getElementById("cmdAdd");
        d.style.display="block";
        var d1=document.getElementById("cmdUpdate");
        d1.style.display="none";
        var d3=document.getElementById("cmdDelete");
        d3.style.display="none";
        
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function clear_Combo2(combo)
{
        var combo_Id=document.getElementById(combo.id);   
        combo_Id.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";
        try
        {
            combo_Id.add(option);
        }catch(errorObject)
        {
            combo_Id.add(option,null);
        }
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function nullcheck(nccommand)
{
    if(document.getElementById("cmbAcc_UnitCode").value.length==0)
    {
        alert("Accounting Unit code not found");
        document.getElementById("cmbAcc_UnitCode").focus();
        return false;
    }
   if(document.getElementById("cmbOffice_code").value.length==0)
    {
        alert("Accounting Office code not found");
        document.getElementById("cmbOffice_code").focus();
        return false;
    }
    if(document.getElementById("cmbMas_SL_type").value.length==0)
    {
        alert("Select Sub-Ledger Type");
        document.getElementById("cmbMas_SL_type").focus();
        return false;
    }
    if(document.getElementById("cmbMas_SL_Code").value.length==0)
    {
        alert("Select Sub-Ledger Code");
        document.getElementById("cmbMas_SL_Code").focus();
        return false;
    }   
    
    if( nccommand != "list")
    {
       if(document.getElementById("txtbill_no").value.length==0)
        {
        alert("Enter the Bill number");
        document.getElementById("txtbill_no").focus();
        return false;
       }
    }
    
  /*  
    if(document.getElementById("txtbill_date").value.length==0)
    {
        alert("Enter the Bill Date");
        document.getElementById("txtbill_date").focus();
        return false;
    }
    if(document.getElementById("txtbill_type").value.length==0)
    {
        alert("Enter the Bill Type");
        document.getElementById("txtbill_type").focus();
        return false;
    }
    if(document.getElementById("txtbill_description").value.length==0)
    {
        alert("Enter the Bill Description");
        document.getElementById("txtbill_description").focus();
        return false;
    }
    if(document.getElementById("txtbill_Amount").value.length==0)
    {
        alert("Enter the Bill Amount");
        document.getElementById("txtbill_Amount").focus();
        return false;
    }
    if(document.getElementById("txtreference_book").value.length==0)
    {
        alert("Enter the M-Book Reference");
        document.getElementById("txtreference_book").focus();
        return false;
    }
    
    if(document.getElementById("txtRemarks").value="")
    {
        alert("Enter the Remarks");
        document.getElementById("txtBalanceAmount").focus();
        return false;
    }
      */
      
      
    return true;
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
 
      
