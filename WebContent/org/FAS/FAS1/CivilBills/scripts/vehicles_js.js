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

function nullcheck()
{
   if(document.getElementById("veh_no").value==""||document.getElementById("veh_no").selectedIndex==0)
    {
        alert("Enter Vehicle no");
        document.getElementById("veh_no").focus();
        return false;
    }
     else if(document.getElementById("fuel_qty").value==""||document.getElementById("fuel_qty").value.length==0)
    {
        alert("Enter FuelCeilingQty");
        document.getElementById("fuel_qty").focus();
        return false;
    }
    else if(document.getElementById("fuel_amt").value==""||document.getElementById("fuel_amt").value.length==0)
    {
        alert("Enter FuelCeilingAmt");
        document.getElementById("fuel_amt").focus();
        return false;
    }
//    else if(document.getElementById("oil_qty").value==""||document.getElementById("oil_qty").value.length==0)
//    {
//        alert("Enter OilCeilingQty");
//        document.getElementById("oil_qty").focus();
//        return false;
//    }
//    else if(document.getElementById("oil_amt").value==""||document.getElementById("oil_amt").value.length==0)
//    {
//        alert("Enter OilCeilingAmt");
//        document.getElementById("oil_amt").focus();
//        return false;
//    }
    else
        return true;
}

function filter_real(evt,item,n,pre)
{
     var charCode = (evt.which) ? evt.which : event.keyCode;
     // allow "." for one time 
     if(charCode==46){
                    //	alert("Position of . "+item.value.indexOf("."));
                            if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                            else return false;
      }
     if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
            // to avoid over flow
                    if(item.value.indexOf(".")<0){
    //			alert("Length without . ="+item.value.length);
                            return (item.value.length<n)?true:false;
                    }
            // dont allow more than 2 precision no's after the point
                    if(item.value.indexOf(".")>0){
                    //	alert("precision count ="+item.value.split(".")[1].length);
                            if(item.value.split(".")[1].length<pre) return true;
                            else return false;
                    }
                    return false;
    }else{
                    return false;
    }
}

function calling(Command)         
{
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var finyr=document.getElementById("fin_yr").value;
    var vehno=document.getElementById("veh_no").value;
    var vehalias=document.getElementById("veh_alias").value;
    var vehdesc=document.getElementById("veh_desc").value;
    var offuse1=document.getElementById("off_use").options[document.getElementById("off_use").selectedIndex].text;
    var ceillimit1=document.getElementById("ceil_limit").value;
    var annno1=document.getElementById("ann_no").value;
    var anndate=document.getElementById("ann_date").value;
    var fuelqty=document.getElementById("fuel_qty").value;
    var fuelamt=document.getElementById("fuel_amt").value;
    var oilqty=document.getElementById("oil_qty").value;
    var oilamt=document.getElementById("oil_amt").value;
    var txtRemarks=document.getElementById("txtRemarks").value;
        if(ceillimit1=="")
         {
             ceillimit=0;
         }
         else
         {
             ceillimit=ceillimit1;                
         }
         if(annno1=="")
         {
             annno=0;
         }
         else
         {
             annno=annno1;                
         }
         if(offuse1=="--Select Here--")
         {
            offuse="";
         }
         else
         {
           offuse=offuse1;
         }
   if(Command=="Add")
     {
        alert("add");
        var flag=nullcheck();
       
        if(flag==true)
         {
           var url="../../../../../veh_ser?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                    "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+"&vehno="+vehno+"&vehalias="+vehalias+
                    "&vehdesc="+vehdesc+"&offuse="+offuse+"&ceillimit="+ceillimit+
                    "&annno="+annno+"&anndate="+anndate+"&fuelqty="+fuelqty+"&fuelamt="+fuelamt+"&oilqty="+oilqty+"&oilamt="+oilamt+"&txtRemarks="+txtRemarks;
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
            var flag=nullcheck();
            if(flag==true)
             {
               var url="../../../../../veh_ser?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+"&vehno="+vehno+"&vehalias="+vehalias+
                        "&vehdesc="+vehdesc+"&offuse="+offuse+"&ceillimit="+ceillimit+
                        "&annno="+annno+"&anndate="+anndate+"&fuelqty="+fuelqty+"&fuelamt="+fuelamt+"&oilqty="+oilqty+"&oilamt="+oilamt+"&txtRemarks="+txtRemarks;
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
                
               var flag=nullcheck();
               if(flag==true)
               {  
                  var url="../../../../../veh_ser?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+"&vehno="+vehno;
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
   else if(Command=="Disp")
     {
                alert('disp');
                var url="../../../../../veh_ser?Command=aliasDisp&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&vehno=" +vehno;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
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

function checklength(evt,item)
{
    var maxqty=document.invoice.rem.value.length;
    var text =100;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}

function ClearAll()
{
        alert("clear"); 
        document.vehicleMaster.cmbAcc_UnitCode.selectedIndex=0;
        document.vehicleMaster.cmbOffice_code.selectedIndex=0;
        document.vehicleMaster.fin_yr.selectedIndex=0;
        document.vehicleMaster.veh_no.selectedIndex=0;
        document.getElementById("ceil_limit").value="";
        document.getElementById("veh_alias").value="";
        document.getElementById("veh_desc").value="";
        document.getElementById("off_use").options[document.getElementById("off_use").selectedIndex].text="";
        document.getElementById("fin_yr").value="";
        document.getElementById("txtRemarks").value="";
        document.getElementById("ann_no").value="";
        document.getElementById("ann_date").value="";
        document.getElementById("fuel_qty").value="";
        document.getElementById("fuel_amt").value="";
        document.getElementById("oil_qty").value="";
        document.getElementById("oil_amt").value="";  
        document.getElementById("cmdEdit").disabled=false;
        document.getElementById("cmdDelete").disabled=false;
}

function goBack(vehno,off,limit,fuelqty,fuelamt,oilqty,oilamt,rem)
{
        document.vehicleMaster.veh_no.value = vehno;
        calling('Disp');
        checkNo();
        document.getElementById("off_use").options[document.getElementById("off_use").selectedIndex].text=off;
        document.vehicleMaster.ceil_limit.value = limit;
        document.vehicleMaster.ann_no.value = "";
        document.vehicleMaster.ann_date.value = "";
        document.vehicleMaster.fuel_qty.value = fuelqty;
        document.vehicleMaster.fuel_amt.value = fuelamt;
        document.vehicleMaster.oil_qty.value = oilqty;
        document.vehicleMaster.oil_amt.value = oilamt;
        document.vehicleMaster.txtRemarks.value = rem;
        document.vehicleMaster.cmdAdd.disabled = true;
        document.vehicleMaster.cmdEdit.disabled = false;
        document.vehicleMaster.cmdDelete.disabled = false;
}

function checkParam()
{
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var finyr=document.getElementById("fin_yr").value;
        var vehno=document.getElementById("veh_no").value;
        var url="../../../../../veh_ser?Command=checkParam&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+"&vehno="+vehno;
        var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
}

function checkNo()
{
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var finyr=document.getElementById("fin_yr").value;
        var vehno=document.getElementById("veh_no").value;
        var url="../../../../../veh_ser?Command=checkNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr+"&vehno="+vehno;
        var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
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
            else if(Command=="Display")
            {
                DisplayRow(baseResponse);
            }
            else if(Command=="checkParam")
            {
                       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="success")
                       {
                            alert("Values already available");
                            document.vehicleMaster.veh_no.selectedIndex=0;
                            document.vehicleMaster.veh_no.focus();
                       } 
             }
           }
         }
}

function CheckRow(baseResponse)
{
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag!="success")
                 {
                        alert("Record is already available");
                        document.vehicleMaster.veh_no.selectedIndex=0;
                        document.getElementById("veh_alias").value="";
                        document.getElementById("veh_desc").value="";
                        document.vehicleMaster.veh_no.focus();
                    }
}

function DispRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        var aliascode=baseResponse.getElementsByTagName("aliascode")[0].firstChild.nodeValue;
        var assetdesc=baseResponse.getElementsByTagName("assetdesc")[0].firstChild.nodeValue;
//        var estimateno=baseResponse.getElementsByTagName("estimateno")[0].firstChild.nodeValue;
//        var estimatedate=baseResponse.getElementsByTagName("estimatedate")[0].firstChild.nodeValue;
        document.vehicleMaster.veh_alias.value=aliascode;
        document.vehicleMaster.veh_desc.value=assetdesc;
//        document.vehicleMaster.ann_no.value = estimateno;
//        document.vehicleMaster.ann_date.value = estimatedate;
    }
}

function DisplayRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
          var estimateno=baseResponse.getElementsByTagName("estimateno")[0].firstChild.nodeValue;
          var estimatedate=baseResponse.getElementsByTagName("estimatedate")[0].firstChild.nodeValue;
          document.vehicleMaster.ann_no.value = estimateno;
          document.vehicleMaster.ann_date.value = estimatedate;
          document.vehicleMaster.ann_no.disabled = true;
          document.vehicleMaster.ann_date.disabled = true;
    }
    else
    {
        document.getElementById("ann_no").value="";
        document.getElementById("ann_date").value="";
        document.vehicleMaster.ann_no.disabled = false;
        document.vehicleMaster.ann_date.disabled = false;
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
        document.getElementById("off_use").options[document.getElementById("off_use").selectedIndex].text="";
        document.getElementById("fin_yr").value="";
        document.getElementById("txtRemarks").value="";
        document.getElementById("ann_no").value="";
        document.getElementById("ann_date").value="";
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
        alert("Record deleted from database");
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
         
var window_BankAccNumber;
function ListAll()
{  
   
     if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
    {
       window_BankAccNumber.resizeTo(500,500);
       window_BankAccNumber.moveTo(250,250); 
       window_BankAccNumber.focus();
    }
    else
    {
        window_BankAccNumber=null;
    }
      if((document.vehicleMaster.cmbAcc_UnitCode.value=="")||(document.vehicleMaster.cmbAcc_UnitCode.value.length<=0)||(document.vehicleMaster.cmbAcc_UnitCode.value=="0"))
    {
        alert("Select Accounting Unit Id");
        document.vehicleMaster.cmbAcc_UnitCode.focus();
        return false;
    }
    
    if((document.vehicleMaster.cmbOffice_code.value=="") || (document.vehicleMaster.cmbOffice_code.value.length<=0) || (document.vehicleMaster.cmbOffice_code.value=="0"))
    {
        alert("Select Office Code");
        document.vehicleMaster.cmbOffice_code.focus();
        return false;
    }   
    if((document.vehicleMaster.fin_yr.value=="") || (document.vehicleMaster.fin_yr.value.length<=0) || (document.vehicleMaster.fin_yr.value=="0"))
    {
        alert("Select Financial Year");
        document.vehicleMaster.fin_yr.focus();
        return false;
    }  
         var cmbAcc_UnitCode=document.vehicleMaster.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.vehicleMaster.cmbOffice_code.value;
         var finyr=document.getElementById("fin_yr").value;
         var vehno=document.getElementById("veh_no").value;
         window_BankAccNumber= window.open("../jsps/Vehicle_Master_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finyr="+finyr,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
         window_BankAccNumber.focus();
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
                        //  callYear();
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
