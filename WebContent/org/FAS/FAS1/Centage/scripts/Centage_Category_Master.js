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
         
         var  cmbAcc_UnitCode    = document.getElementById("cmbAcc_UnitCode").value;
         var  cmbOffice_code     = document.getElementById("cmbOffice_code").value;
         
         window_BankAccNumber= window.open("Centage_Category_Master_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
   
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function doParentBankAccNumbers(txtCategory_Code, txtCategory_Desc , cmbCategoryType )
{
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
            
    document.getElementById("txtCategory_Code").value=txtCategory_Code;
    document.getElementById("txtCategory_Desc").value=txtCategory_Desc;   
    
    if ( cmbCategoryType =="Common")
    {    
      document.getElementById("cmbCategoryType").value="Common";
    }
    else 
    {
      document.getElementById("cmbCategoryType").value="Specific";
    }    
    document.getElementById("txtCategory_Code").disabled=true;    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doFunction_ccm(Command,param)
{   
 
   var  cmbAcc_UnitCode    = document.getElementById("cmbAcc_UnitCode").value;
   var  cmbOffice_code     = document.getElementById("cmbOffice_code").value;
   var  txtCategory_Code   = document.getElementById("txtCategory_Code").value;
   var  txtCategory_Desc   = document.getElementById("txtCategory_Desc").value;   
   var  cmbCategoryType    = document.getElementById("cmbCategoryType").value;   
   
   if ( cmbCategoryType =="Common" ) 
     cmbCategoryType ="C" 
    
   if ( cmbCategoryType =="Specific" ) 
     cmbCategoryType ="S" 
   
     
        if(Command=="Add")
        {   
            var flag=nullcheck('add');
            if(flag==true)
               {
                var url="../../../../../Centage_Category_Master.kv?Command=Add&txtCategory_Code="+txtCategory_Code+"&txtCategory_Desc="+txtCategory_Desc+
                        "&cmbCategoryType="+cmbCategoryType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ccm(req);
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
                     var url="../../../../../Centage_Category_Master.kv?Command=Delete&txtCategory_Code="+txtCategory_Code+"&txtCategory_Desc="+txtCategory_Desc+
                        "&cmbCategoryType="+cmbCategoryType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse_ccm(req);
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
                var url="../../../../../Centage_Category_Master.kv?Command=Update&txtCategory_Code="+txtCategory_Code+"&txtCategory_Desc="+txtCategory_Desc+
                        "&cmbCategoryType="+cmbCategoryType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ccm(req);
                }   
                        req.send(null);
              }
       }
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   handleResponse()   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function handleResponse_ccm(req)
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
        alert("Record inserted into database Successfully");
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
        alert("Records deleted from database Successfully");
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
    document.getElementById("txtCategory_Code").value="";
    document.getElementById("txtCategory_Desc").value="";    
    
    document.getElementById("txtCategory_Code").disabled=false;
        
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
  /*if(document.getElementById("txtCategory_Code").value.length==0)
    {
        alert("Category Code not found");
        document.getElementById("txtCategory_Code").focus();
        return false;
    }
  */  
   if(document.getElementById("txtCategory_Desc").value.length==0)
    {
        alert("Category Description not found");
        document.getElementById("txtCategory_Desc").focus();
        return false;
    } 
    return true;
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  
 
      
