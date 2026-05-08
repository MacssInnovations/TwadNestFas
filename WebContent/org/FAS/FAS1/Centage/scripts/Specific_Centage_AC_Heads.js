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
         
         var  cmbAcc_UnitCode   = document.getElementById("cmbAcc_UnitCode").value;
         var  cmbOffice_code   =  document.getElementById("cmbOffice_code").value; 
         
         window_BankAccNumber= window.open("Specific_Centage_AC_Heads_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);  
         
         doFunction_ccm('ProjectSL_Code','null');
         doFunction_ccm('Load_Category_Code','null');
   
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
   
window.onunload=function()
{
    if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function doParentBankAccNumbers( txtWorkExpACHeadCode, txtWorkExpACHeadCode_Desc, txtDftCentageCtgy, txtDftCentageCtgy_Desc, txtCrACHeadCode, txtCrACHeadCode_Desc, txtDrACHeadCode, txtDrACHeadCode_Desc, txtRemarks, txtProjectSL )
{
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";          
          
        document.frmSpecificCentageACHeads.WxpTourch.style.visibility='hidden';
     
        document.getElementById("txtWorkExpACHeadCode").value  =  txtWorkExpACHeadCode;
        document.getElementById("txtWorkExpACHeadCode_Desc").value  =  txtWorkExpACHeadCode_Desc;
        
        
        document.getElementById("txtProjectSLCode").value = txtProjectSL;
        
        document.getElementById("txtDftCentageCtgy").value = txtDftCentageCtgy;
        
        document.getElementById("txtCrACHeadCode").value  = txtCrACHeadCode;
        document.getElementById("txtCrACHeadCode_Desc").value  = txtCrACHeadCode_Desc;
        
        document.getElementById("txtDrACHeadCode").value  = txtDrACHeadCode;
        document.getElementById("txtDrACHeadCode_Desc").value  = txtDrACHeadCode_Desc;
        
        document.getElementById("txtRemarks").value = txtRemarks;     
       
        document.getElementById("txtWorkExpACHeadCode").disabled=true;
        document.getElementById("txtProjectSLCode").disabled=true; 
        document.getElementById("cmdGO").disabled=true;
    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


function doFunction_ccm(Command,param)
{   

   var  cmbAcc_UnitCode   = document.getElementById("cmbAcc_UnitCode").value;
   var  cmbOffice_code   =  document.getElementById("cmbOffice_code").value;
   var  txtWorkExpACHeadCode   =  document.getElementById("txtWorkExpACHeadCode").value;
   var  txtProjectSLCode =  document.getElementById("txtProjectSLCode").value;
   var  txtDftCentageCtgy  =  document.getElementById("txtDftCentageCtgy").value;
   var  txtCrACHeadCode   =  document.getElementById("txtCrACHeadCode").value;
   var  txtDrACHeadCode  =  document.getElementById("txtDrACHeadCode").value;
   var  txtRemarks    =  document.getElementById("txtRemarks").value;   
     
       if(Command=="ProjectSL_Code")
        {   
               var url="../../../../../Specific_Centage_AC_Heads.kv?Command=ProjectSL_Code&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtWorkExpACHeadCode="+txtWorkExpACHeadCode+  
                        "&txtProjectSLCode="+txtProjectSLCode+        
                        "&txtDftCentageCtgy="+txtDftCentageCtgy+        
                        "&txtCrACHeadCode="+txtCrACHeadCode+        
                        "&txtDrACHeadCode="+txtDrACHeadCode+   
                        "&txtRemarks="+txtRemarks;
                
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse_ccm(req);
                }   
                   req.send(null);
                
        }
       else 
        
       if(Command=="Load_Category_Code")
        {   
                var url="../../../../../Specific_Centage_AC_Heads.kv?Command=Load_Category_Code&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtWorkExpACHeadCode="+txtWorkExpACHeadCode+  
                        "&txtProjectSLCode="+txtProjectSLCode+        
                        "&txtDftCentageCtgy="+txtDftCentageCtgy+        
                        "&txtCrACHeadCode="+txtCrACHeadCode+        
                        "&txtDrACHeadCode="+txtDrACHeadCode+   
                        "&txtRemarks="+txtRemarks;
                
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
               
                var url="../../../../../Specific_Centage_AC_Heads.kv?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtWorkExpACHeadCode="+txtWorkExpACHeadCode+  
                        "&txtProjectSLCode="+txtProjectSLCode+        
                        "&txtDftCentageCtgy="+txtDftCentageCtgy+        
                        "&txtCrACHeadCode="+txtCrACHeadCode+        
                        "&txtDrACHeadCode="+txtDrACHeadCode+   
                        "&txtRemarks="+txtRemarks;   
                        
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
                    var url="../../../../../Specific_Centage_AC_Heads.kv?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtWorkExpACHeadCode="+txtWorkExpACHeadCode+"&txtProjectSLCode="+txtProjectSLCode;   
                        
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
             
               var url="../../../../../Specific_Centage_AC_Heads.kv?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
                        "&txtWorkExpACHeadCode="+txtWorkExpACHeadCode+   
                        "&txtProjectSLCode="+txtProjectSLCode+        
                        "&txtDftCentageCtgy="+txtDftCentageCtgy+        
                        "&txtCrACHeadCode="+txtCrACHeadCode+        
                        "&txtDrACHeadCode="+txtDrACHeadCode+   
                        "&txtRemarks="+txtRemarks;   
            
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
           
           if (Command=="ProjectSL_Code") 
            {
                ProjectSL_Code(baseResponse);
            }            
           else if (Command=="Load_Category_Code") 
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

function ProjectSL_Code(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
         var txtProjectSLCode=document.getElementById("txtProjectSLCode");
             txtProjectSLCode.innerHTML="";
             
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
                    txtProjectSLCode.add(option);
                }
                catch(errorObject )
                {
                    txtProjectSLCode.add(option,null);
                }     
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Project SL code ");
         }
             
    }
    else if(flag=="NotAvailable")
    {
        try
         {
            var txtProjectSLCode=document.getElementById("txtProjectSLCode");
            txtProjectSLCode.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="-- Select Project SL Code --";
            option.value="";
            try
            {
                txtProjectSLCode.add(option);
            }
            catch(errorObject )
            {
                txtProjectSLCode.add(option,null);
            }
            
            alert("Project SL Code Not Available");
            
         }
         catch(err)
         {
            alert("Problem in Loading Project SL Code ");
         }         
    }
    else if(flag=="failure")
    {
        alert("Problem in Loading Project SL Code ");
    }    
    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

function LoadCategoryCode(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
         var txtDftCentageCtgy=document.getElementById("txtDftCentageCtgy");
             txtDftCentageCtgy.innerHTML="";
             
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
                    txtDftCentageCtgy.add(option);
                }
                catch(errorObject )
                {
                    txtDftCentageCtgy.add(option,null);
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
            var txtDftCentageCtgy=document.getElementById("txtDftCentageCtgy");
            txtDftCentageCtgy.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="-- Select Category Code --";
            option.value="";
            try
            {
                txtDftCentageCtgy.add(option);
            }
            catch(errorObject )
            {
                txtDftCentageCtgy.add(option,null);
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
    document.getElementById("txtWorkExpACHeadCode").value="";
    document.getElementById("txtProjectSLCode").value=""; 
    document.getElementById("txtDftCentageCtgy").value="";
    document.getElementById("txtCrACHeadCode").value="";    
    document.getElementById("txtDrACHeadCode").value="";    
    document.getElementById("txtRemarks").value="";    
    
    document.getElementById("txtWorkExpACHeadCode_Desc").value="";    
    document.getElementById("txtCrACHeadCode_Desc").value="";    
    document.getElementById("txtDrACHeadCode_Desc").value="";   
    
    
    document.getElementById("txtWorkExpACHeadCode").disabled=false;
    document.getElementById("txtProjectSLCode").disabled=false;
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
   if(document.getElementById("txtWorkExpACHeadCode").value.length==0)
    {
        alert("Work Expenditure Account Head Code not found");
        document.getElementById("txtWorkExpACHeadCode").focus();
        return false;
    }    
    
    if(document.getElementById("txtProjectSLCode").value.length==0)
    {
        alert("Project SL Code not found");
        document.getElementById("txtProjectSLCode").focus();
        return false;
    }    
    
    if(document.getElementById("txtDftCentageCtgy").value.length==0)
    {
        alert("Centage Category Code not found");
        document.getElementById("txtWorkExpACHeadCode").focus();
        return false;
    }        
    
    if(document.getElementById("txtCrACHeadCode").value.length==0)
    {
        alert("Credit Account Head Code not found");
        document.getElementById("txtCrACHeadCode").focus();
        return false;
    }    
    
    if(document.getElementById("txtDrACHeadCode").value.length==0)
    {
        alert("Debit Account Head Code not found");
        document.getElementById("txtDrACHeadCode").focus();
        return false;
    }    
    
    if(document.getElementById("txtRemarks").value.length==0)
    {
        alert("Remarks not found");
        document.getElementById("txtRemarks").focus();
        return false;
    }    
    
    if(document.getElementById("txtWorkExpACHeadCode_Desc").value.length==0)
    {
        alert("Work Expenditure Account Head Code Description not found");
        document.getElementById("txtWorkExpACHeadCode_Desc").focus();
        return false;
    }    
     if(document.getElementById("txtCrACHeadCode_Desc").value.length==0)
    {
        alert("Credit Account Head Code Description not found");
        document.getElementById("txtCrACHeadCode").focus();
        return false;
    }    
    
    if(document.getElementById("txtDrACHeadCode_Desc").value.length==0)
    {
        alert("Debit Account Head Code Description not found");
        document.getElementById("txtDrACHeadCode_Desc").focus();
        return false;
    }    
    return true;    
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~      
