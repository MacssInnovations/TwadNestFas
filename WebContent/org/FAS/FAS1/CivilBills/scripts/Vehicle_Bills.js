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
    var vehno=document.getElementById("veh_no").value;
    var refno1=document.getElementById("ref_no").value;
    var anndate1=document.getElementById("ann_date1").value;
    var anndate=document.getElementById("ann_date").value;
    var fuelqty=document.getElementById("fuel_qty").value;
    var fuelamt=document.getElementById("fuel_amt").value;
    var oilqty=document.getElementById("oil_qty").value;
    var oilamt=document.getElementById("oil_amt").value;
    var txtRemarks=document.getElementById("txtRemarks").value;
    var active;
                if(document.VehicleBill.radActive[0].checked)
                {
                        active = "Y";
                }
                else
                {
                        active = "N";
               }
    var refno;
         if(refno1=="")
         {
             refno=0;
         }
         else
         {
             refno=refno1;                
         }                   
    if(Command=="Add")
        {
             alert("add");
         var flag=nullcheck();
         if(flag==true)
            {                   
                    var url="../../../../../Vehicle_Bill?Command=Add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                            "&cmbOffice_code="+cmbOffice_code+"&anndate="+anndate+
                            "&vehno="+vehno+"&refno="+refno+"&anndate1="+anndate1+"&fuelqty="+fuelqty+"&fuelamt="+fuelamt+"&oilqty="+oilqty+"&oilamt="+oilamt+"&active="+active+"&txtRemarks="+txtRemarks;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };   
                            req.send(null);
          }
         }
        else if(Command=="Update")
          { 
    	  alert("update");
            var flag=nullcheck();
            if(flag==true)
             {               
               var url="../../../../../Vehicle_Bill?Command=Update&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                            "&cmbOffice_code="+cmbOffice_code+"&anndate="+anndate+
                            "&vehno="+vehno+"&refno="+refno+"&anndate1="+anndate1+"&fuelqty="+fuelqty+"&fuelamt="+fuelamt+"&oilqty="+oilqty+"&oilamt="+oilamt+"&active="+active+"&txtRemarks="+txtRemarks;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                };   
                        req.send(null);
            }
       }
        else if(Command=="Delete")
        {
        
            if(confirm("Do You Really want to Cancel it?"))
            {
               var flag=nullcheck();
               if(flag==true)
               {  
                    var url="../../../../../Vehicle_Bill?Command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&anndate="+anndate+
                        "&vehno="+vehno;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };   
                            req.send(null);
              }
            }
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

function check()
{
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    var vehno=document.getElementById("veh_no").value;
    var url="../../../../../Vehicle_Bill?Command=aliasDisp&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&vehno="+vehno;
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
    if(document.getElementById("ann_date").value==""||document.getElementById("ann_date").value.length==0)
               {
                alert("Enter Date");
                document.getElementById("ann_date").focus();
                return false;
               }
      else if(document.getElementById("veh_no").value==""||document.getElementById("veh_no").selectedIndex==0)
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
    /*else if(document.getElementById("oil_qty").value==""||document.getElementById("oil_qty").value.length==0)
            {
                alert("Enter OilCeilingQty");
                document.getElementById("oil_qty").focus();
                return false;
            }
    else if(document.getElementById("oil_amt").value==""||document.getElementById("oil_amt").value.length==0)
            {
                alert("Enter OilCeilingAmt");
                document.getElementById("oil_amt").focus();
                return false;
            }*/
                else
                    return true;
}

function checklength(evt,item)
{
    var maxqty=document.VehicleBill.txtRemarks.value.length;
    var text =100;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}

var window_BankAccNumber;
function Lists()
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
         var cmbAcc_UnitCode=document.VehicleBill.cmbAcc_UnitCode.value;
         var cmbOffice_code=document.VehicleBill.cmbOffice_code.value;
         if(document.getElementById("ann_date").value==""||document.getElementById("ann_date").value.length==0)
         {
             alert("Enter Date");
             document.getElementById("ann_date").focus();
             return false;
            }
          var anndate=document.VehicleBill.ann_date.value;
         window_BankAccNumber= window.open("../jsps/Vehicle_Bill_List.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&anndate="+anndate,"mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
         window_BankAccNumber.focus();
    }
    
function ClearAll()
{
        alert("clear"); 
        document.VehicleBill.cmbAcc_UnitCode.selectedIndex=0;
        document.VehicleBill.cmbOffice_code.selectedIndex=0;
        document.VehicleBill.ann_date.value = "";
        document.VehicleBill.veh_no.selectedIndex=0;
        document.VehicleBill.ref_no.value = "";
        document.VehicleBill.ann_date1.value = "";
        document.getElementById("fuel_qty").value="";
        document.getElementById("fuel_amt").value="";
        document.getElementById("oil_qty").value="";
        document.getElementById("oil_amt").value=""; 
        document.getElementById("radActive").value="";
        document.getElementById("txtRemarks").value="";
        document.getElementById("cmdEdit").disabled=false;
        document.getElementById("cmdDelete").disabled=false;
}
function goBack(vehno,fuelqty,fuelamt,oilqty,oilamt)
{
        document.VehicleBill.veh_no.value = vehno;
        document.VehicleBill.ref_no.value = "";
        document.VehicleBill.ann_date1.value = "";
        document.VehicleBill.fuel_qty.value = fuelqty;
        document.VehicleBill.fuel_amt.value = fuelamt;
        document.VehicleBill.oil_qty.value = oilqty;
        document.VehicleBill.oil_amt.value = oilamt;
        document.VehicleBill.txtRemarks.value = "";
        document.VehicleBill.cmdAdd.disabled = true;
        document.VehicleBill.cmdEdit.disabled = false;
        document.VehicleBill.cmdDelete.disabled = false;
}
function checkParam()
{
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var anndate=document.getElementById("ann_date").value;
        var vehno=document.getElementById("veh_no").value;
        var url="../../../../../Vehicle_Bill?Command=checkParam&cmbAcc_UnitCode="+cmbAcc_UnitCode+
                        "&cmbOffice_code="+cmbOffice_code+"&anndate="+anndate+"&vehno="+vehno;
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
            else if(Command=="checkParam")
            {
                       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="success")
                       {
                            alert("Values already available");
                            document.VehicleBill.veh_no.selectedIndex=0;
                            document.VehicleBill.veh_no.focus();
                       } 
             }
           }
         }
    }
function asset()
{
   if(document.VehicleBill.veh_no.selectedIndex==0)
        {
            alert("Enter Vehicle No");
            document.getElementById("veh_no").focus();
        }
}
function DispRow(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
   {    
    var fuelqty=baseResponse.getElementsByTagName("fuelqty")[0].firstChild.nodeValue;
    var fuelamt=baseResponse.getElementsByTagName("fuelamt")[0].firstChild.nodeValue;
    var oilqty=baseResponse.getElementsByTagName("oilqty")[0].firstChild.nodeValue;
    var oilamt=baseResponse.getElementsByTagName("oilamt")[0].firstChild.nodeValue;
    if(parseFloat(document.VehicleBill.fuel_qty.value) > parseFloat(fuelqty))
    {
        alert("Enter less then..."+fuelqty);
        document.VehicleBill.fuel_qty.value="";
        document.VehicleBill.fuel_qty.focus();
    }
    else if(parseFloat(document.VehicleBill.fuel_amt.value) > parseFloat(fuelamt))
    {
    alert("Enter less then..."+fuelamt);
    document.VehicleBill.fuel_amt.value="";
    document.VehicleBill.fuel_amt.focus();
    }
    else if(parseFloat(document.VehicleBill.oil_qty.value) > parseFloat(oilqty))
    {
        alert("Enter less then..."+oilqty);
        document.VehicleBill.oil_qty.value="";
        document.VehicleBill.oil_qty.focus();
    }
    else if(parseFloat(document.VehicleBill.oil_amt.value) > parseFloat(oilamt))
    {
    alert("Enter less then..."+oilamt);
    document.VehicleBill.oil_amt.value="";
    document.VehicleBill.oil_amt.focus();
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
        document.VehicleBill.ref_no.value = "";
        document.VehicleBill.ann_date1.value = "";
        document.getElementById("txtRemarks").value="";
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
        alert("Records cancelled successfully");
         ClearAll();
    }
    else
    {
        alert("Record not cancel");
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
                      //    callYear();
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

