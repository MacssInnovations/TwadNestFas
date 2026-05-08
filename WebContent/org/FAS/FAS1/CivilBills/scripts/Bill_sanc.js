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

function callminor()
{
        var req=getTransport();
        var majorType=document.BillSanc.majorType.value;
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var url="../../../../../bill_sanc?Command=check&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&majorType="+majorType;                
        var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
}
    
function nullcheck()
{
	if((document.BillSanc.fin_yr.value=="") || (document.BillSanc.fin_yr.value.length<=0) || (document.BillSanc.fin_yr.value=="0"))
    {
        alert("Select Financial Year");
        document.BillSanc.fin_yr.focus();
        return false;
    }
          else if(document.getElementById("majorType").value==""||document.getElementById("majorType").selectedIndex==0)
        {
            alert("Enter Major Type");
            document.getElementById("majorType").focus();
            return false;
        }
     else if(document.getElementById("txtEmpID_mas").value==""||document.getElementById("txtEmpID_mas").selectedIndex==0)
       {
            alert("Enter Employee Code");
            document.getElementById("txtEmpID_mas").focus();
            return false;
       }
      else
            return true;
}

function ClearAll()
{
        alert("clear"); 
        document.BillSanc.cmbAcc_UnitCode.selectedIndex=0;
        document.BillSanc.cmbOffice_code.selectedIndex=0;
        document.getElementById("fin_yr").value="";
        document.getElementById("majorType").value="";
        document.getElementById("minorType").value="";
        document.getElementById("txtEmpID_mas").value="";
        document.getElementById("txtOfficeID_mas").value="";
        document.getElementById("sanc").value="";
        document.getElementById("cmdEdit").disabled=false;
        document.getElementById("cmdDelete").disabled=false;
        document.getElementById("cmdAdd").disabled=false;
        document.getElementById("fin_yr").disabled = false;
        document.getElementById("majorType").disabled = false;
        document.getElementById("minorType").disabled = false;
        document.getElementById("txtOfficeID_mas").disabled = false;
        document.getElementById("txtEmpID_mas").disabled = false;
}

var window_BankAccNumber;
function Lists()
{  
         var cmbAcc_UnitCode=document.BillSanc.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.BillSanc.cmbOffice_code.value;
         if((document.BillSanc.fin_yr.value=="") || (document.BillSanc.fin_yr.value.length<=0) || (document.BillSanc.fin_yr.value=="0"))
         {
             alert("Select Financial Year");
             document.BillSanc.fin_yr.focus();
             return false;
         }
         var finyr=document.BillSanc.fin_yr.value;
         window_BankAccNumber= window.open("../jsps/Bill_Sanc_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
         window_BankAccNumber.focus();
}
    
function goBack(major,minor,empid,off,sanc)
{
         var s=minor.split("-");
         document.BillSanc.majorType.value = major; 
         minorType=document.getElementById("minorType");
         var option=document.createElement("OPTION");
                option.text=s[1];
                option.value=s[0];
                minorType.length=0;	
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }
        var s1=sanc.split("-");

        document.getElementById("txtEmpID_mas").value=empid; 
        document.getElementById("txtOfficeID_mas").value=off;
        document.getElementById("sanc").value=s1[0];
        document.getElementById("sanc").text=s1[1];
        document.BillSanc.cmdAdd.disabled = true;
        document.BillSanc.cmdEdit.disabled = false;
        document.BillSanc.cmdDelete.disabled = false;
        document.getElementById("fin_yr").disabled = true;
        document.getElementById("majorType").disabled = true;
        document.getElementById("minorType").disabled = true;
        document.getElementById("txtEmpID_mas").disabled = true;
}

function calling(Command)         
 {  
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var finyr=document.getElementById("fin_yr").value;
    var majorType=document.getElementById("majorType").value;
    var minorType=document.getElementById("minorType").value;
    var txtEmpIDmas=document.getElementById("txtEmpID_mas").value;
    var txtOfficeIDmas1=document.getElementById("txtOfficeID_mas").value;
    var sanc1=document.getElementById("sanc").value;
    if(Command=="Add")
    {
         alert("add");
         var txtOfficeIDmas;
         if(txtOfficeIDmas1=="")
         {
             txtOfficeIDmas=0;
         }
         else
         {
             txtOfficeIDmas=txtOfficeIDmas1;                
         }
         var sanc;
         if(sanc1=="")
         {
             sanc=0;
         }
         else
         {
             sanc=sanc1;                
         }  
         var flag=nullcheck();
         if(flag==true)
           {                
                    var url="../../../../../bill_sanc?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                            "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+
                            "&majorType="+majorType+"&minorType="+minorType+"&txtEmpIDmas="+txtEmpIDmas+"&txtOfficeIDmas="+txtOfficeIDmas+"&sanc="+sanc;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
          }
    }
    else if(Command=="Update")
       { 
   	     alert("update");
             var txtOfficeIDmas;
             if(txtOfficeIDmas1=="")
             {
                 txtOfficeIDmas=0;
             }
             else
             {
                 txtOfficeIDmas=txtOfficeIDmas1;                
             }
             var sanc;
             if(sanc1=="")
             {
                 sanc=0;
             }
             else
             {
                 sanc=sanc1;                
             }  
            var flag=nullcheck();
            if(flag==true)
            {
                var url="../../../../../bill_sanc?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+
                        "&majorType="+majorType+"&minorType="+minorType+"&txtEmpIDmas="+txtEmpIDmas+"&txtOfficeIDmas="+txtOfficeIDmas+"&sanc="+sanc;
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
                 var txtOfficeIDmas;
                 if(txtOfficeIDmas1=="")
                 {
                     txtOfficeIDmas=0;
                 }
                 else
                 {
                     txtOfficeIDmas=txtOfficeIDmas1;                
                 }
               var flag=nullcheck();
               if(flag==true)
               {  
                  var url="../../../../../bill_sanc?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+
                        "&majorType="+majorType+"&minorType="+minorType+"&txtEmpIDmas="+txtEmpIDmas;
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
}

function handleResponse(req)
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
            else if(Command=="Updated")
            {                
                UpdateRow(baseResponse);
            }
            else if(Command=="Disp")
            {
                DispRow(baseResponse);
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
    else if(flag=="AlreadyExist")
       {
        alert("Record AlreadyExist.so,can't Inserted");
        document.getElementById("majorType").value="";
        document.getElementById("minorType").value="";
        document.getElementById("txtEmpID_mas").value="";
        document.getElementById("txtOfficeID_mas").value="";
        document.getElementById("sanc").value="";
       }
    else
    {
        alert("Record not inserted into database");
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

function numbersonly(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur(); }catch(e){}
          return true;
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
            {
                return false;
            }
        }
}

function DispRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        minorType=document.getElementById("minorType");
        minorType.length=0;
        mtype=baseResponse.getElementsByTagName("mindesc");    
        for(var i=0;i<mtype.length;i++)
               {
                var items_id=baseResponse.getElementsByTagName("mincode")[i].firstChild.nodeValue;
                var items_name=baseResponse.getElementsByTagName("mindesc")[i].firstChild.nodeValue;				       	                                                  
                var option=document.createElement("OPTION");
                option.text=items_name;
                option.value=items_id;
                try
                {
                        minorType.add(option);
                }
                catch(errorObject)
                {
                        minorType.add(option,null);
                }
        }
    }
    else
       {
               alert("No records");
               document.BillSanc.majorType.selectedIndex="";
        }
}

function call_date(dateCtrl)                        // TB_checking
{        
     if(checkdt(dateCtrl))
          {
                  var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                  var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                  var TB_date=dateCtrl.value;
                  if(dateCtrl.value.length!=0)
                  {
                          var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                          var req=getTransport();
                          req.open("GET",url,true);
                          req.onreadystatechange=function()
                          {
                                   check_TB(req,dateCtrl);
                          }  
                          req.send(null);
                  }
         }
    
 }
 function check_TB(req,dateCtrl)
 {
      if(req.readyState==4)
          {
                 if(req.status==200)
                 { 
                          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                          var tagcommand=baseResponse.getElementsByTagName("command")[0];
                          var Command=tagcommand.firstChild.nodeValue;
                          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;                       
                          //callYear();
                          if(flag=="failure")
                          {
                         
                                   dateCtrl.value="";
                                   alert("Trial Balance Closed");//return false;//
                                   dateCtrl.focus();                                           
                          }
                          else if(flag=="finyear")
                          {
                         
                                   // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date
                                   dateCtrl.value="";
                                   alert("Cash Book Control Not Found ");//return false;//
                                   dateCtrl.focus();
                              
                                   //document.getElementById("txtVoucher_No").value="";    
                          }
                     }
          }
 }

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)   
{
         if(blr_flag==1)        
         {                             
                 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                 var TB_date=fromcal_dateCtrl.value;                
                 if(fromcal_dateCtrl.value.length!=0)
                 {
                         var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;                        
                         var req=getTransport();
                         req.open("GET",url,true);
                         req.onreadystatechange=function()
                         {
                                  check_TB(req,fromcal_dateCtrl);
                         }  
                         req.send(null);
                 }
         }
}

      
