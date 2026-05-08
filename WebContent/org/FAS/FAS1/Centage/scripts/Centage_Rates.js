var Common_branchID="";

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ XML req ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  

var window_BankAccNumber;

function ListHeads()
{ 
        var  cmbAcc_UnitCode    = document.getElementById("cmbAcc_UnitCode").value;
        var  cmbOffice_code     = document.getElementById("cmbOffice_code").value; 
     
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
         
         window_BankAccNumber= window.open("Centage_Rates_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
         doFunction_ccm('Load_Category_Code','null');
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function doParentBankAccNumbers(txtCategory_Code, txtCentageRates , txtFinancialYear )
{
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
       
    document.getElementById("txtCategory_Code").value=txtCategory_Code;
    document.getElementById("txtCentageRates").value=txtCentageRates;       
    document.getElementById("txtFinancialYear").value=txtFinancialYear;   
    
    document.getElementById("txtCategory_Code").disabled=true;
    document.getElementById("cmdGO").disabled=true;    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doFunction_ccm(Command,param)
{   
   var  cmbAcc_UnitCode    = document.getElementById("cmbAcc_UnitCode").value;
   var  cmbOffice_code     = document.getElementById("cmbOffice_code").value;
   var  txtCategory_Code   = document.getElementById("txtCategory_Code").value;
   var  txtCentageRates    = document.getElementById("txtCentageRates").value;
   var  txtFinancialYear   = document.getElementById("txtFinancialYear").value;   
       
        if(Command=="Load_Category_Code")
        {   
                var url="../../../../../Centage_Rates.kv?Command=Load_Category_Code&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ccm(req);
                }   
                  req.send(null);
                
        }
       else if(Command=="Add")
        {   
            var flag=nullcheck('add');
            if(flag==true)
               {
                var url="../../../../../Centage_Rates.kv?Command=Add&txtCategory_Code="+txtCategory_Code+"&txtCentageRates="+txtCentageRates+
                        "&txtFinancialYear="+txtFinancialYear+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            
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
                     var url="../../../../../Centage_Rates.kv?Command=Delete&txtCategory_Code="+txtCategory_Code+"&txtCentageRates="+txtCentageRates+
                        "&txtFinancialYear="+txtFinancialYear+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
                var url="../../../../../Centage_Rates.kv?Command=Update&txtCategory_Code="+txtCategory_Code+"&txtCentageRates="+txtCentageRates+
                        "&txtFinancialYear="+txtFinancialYear+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            
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
            
           if (Command=="Load_Category_Code") 
            {
                LoadCategoryCode(baseResponse);
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

function LoadCategoryCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
         var txtCategory_Code=document.getElementById("txtCategory_Code");
             txtCategory_Code.innerHTML="";
             
            var cat_pair=baseResponse.getElementsByTagName("cat_pair");
       
          try
          {
            for(i=0;i<cat_pair.length;i++)
            {  
                var option=document.createElement("OPTION");
                var cat_code=baseResponse.getElementsByTagName("cat_code")[i].firstChild.nodeValue;
                var cat_desc=baseResponse.getElementsByTagName("cat_desc")[i].firstChild.nodeValue;
                option.text=cat_desc;
                option.value=cat_code;
                try
                {
                    txtCategory_Code.add(option);
                }
                catch(errorObject )
                {
                    txtCategory_Code.add(option,null);
                }   
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Category code ");
         }
             
    }
    else if(flag=="NotAvailable")
    {
        try
         {
            var txtCategory_Code=document.getElementById("txtCategory_Code");
            txtCategory_Code.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="-- Select Category Code --";
            option.value="";
            try
            {
                txtCategory_Code.add(option);
            }
            catch(errorObject )
            {
                txtCategory_Code.add(option,null);
            }
            
            alert("Category Code Not Available");
            
         }
         catch(err)
         {
            alert("Problem in Loading Category Code ");
         }         
    }
    else if(flag=="failure")
    {
        alert("Problem in Loading Category Code ");
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
    document.getElementById("txtCentageRates").value="";
    document.getElementById("txtFinancialYear").value="";        
    document.getElementById("txtCategory_Code").disabled=false;
    document.getElementById("cmdGO").disabled=false;
    
        
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
    if(document.getElementById("txtCategory_Code").value.length==0)
    {
        alert("Category Code not found");
        document.getElementById("txtCategory_Code").focus();
        return false;
    }    
    
   if(document.getElementById("txtCentageRates").value.length==0)
    {
        alert("Centage Rate not found");
        document.getElementById("txtCentageRates").focus();       
        return false;
    }       
       
    if (  document.getElementById("txtCentageRates").value < 0 )
     {
       alert("Centage Rate Should not be less than 0  ");       
       return false;      
     }
     
    if (document.getElementById("txtCentageRates").value > 100)
     {
       alert("Centage Rate Should not be greater than 100 ");       
       return false;      
     }
    
    if(document.getElementById("txtFinancialYear").value.length==0)
    {
       alert("Financial Year not found");
       document.getElementById("txtFinancialYear").focus();
       return false;
    }   
    return true;
}



//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      
function loadfyr()
{
        var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();
        var txtFinancialYear=document.getElementById("txtFinancialYear");
        cyr=cyr+1
 	if (parseInt(cmn) <= 2)
        {
  
              //  document.frmBudget.cmbFinancialYear.length=5;
                txtFinancialYear.innerHTML="";
                for (var i = 0 ; i < 1; i++) 
                {
                  var id=(cyr-2)+"-"+(cyr-1);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                  try
                        {
                            txtFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            txtFinancialYear.add(option,null);
                        } 
                  
                  cyr--;
                }
 	}
 	else 
 	{
          // document.frmBudget.cmbFinancialYear.length=5;
           txtFinancialYear.innerHTML="";
            for (var i = 0 ; i < 1; i++) 
            {
              var id=(cyr-1)+"-"+(cyr);
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            txtFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            txtFinancialYear.add(option,null);
                        }
              cyr--;
            }
 	}        
}




function numbersonly1(e,t)
{
        var unicode=e.charCode? e.charCode : e.keyCode;
        if(unicode==13)
        {
        
        }
        if (unicode!=8 && unicode !=9  )
        {          
            if (unicode != 47 && (unicode < 46 || unicode > 57)   )              
                return false;
        }
        return true;
}

function RateValidation(rate)
{
   if (parseInt(rate) < 0 || parseInt(rate) > 100 ) 
   {
      alert("Centage Rate should be within 0 to 100 ");
      return false;
   }
   return true;
}
