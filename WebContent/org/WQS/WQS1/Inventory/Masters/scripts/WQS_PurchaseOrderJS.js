var seq=0;
function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if(!req && typeof XMLHttpRequest!='undefined')
        {
        req=new XMLHttpRequest();
        }
   return req;
}    

function nullCheck()
{
                 
                  if(document.PurchaseOrder.lab.value=="0")
                  {
                       alert("Please Select the Lab");
                      // document.PurchaseOrder.lab.focus();
                       return false;
                  }
                  if((document.PurchaseOrder.orderno.value=="") || (document.PurchaseOrder.orderno.length<=0))
                  {
                       alert("Please Enter the Purchase Order Number");
                       document.PurchaseOrder.orderno.focus();
                       return false;
                  }
                  if((document.PurchaseOrder.orderdate.value=="") || (document.PurchaseOrder.orderdate.value.length<=0))
                  {
                       alert("Please Enter the Purchase Order Date");
                       document.PurchaseOrder.orderdate.focus();
                       return false;
                  }
                  if(document.PurchaseOrder.sup.value=="") 
                  {
                       alert("Please Select the Supplier");
                       //document.PurchaseOrder.sup.focus();
                       return false;
                  }
                  if((document.PurchaseOrder.status.value=="") || (document.PurchaseOrder.status.value.length<=0))
                  {
                       alert("Please Enter the Phone Nos");
                       document.PurchaseOrder.status.focus();
                       return false;
                  }
                  return true;
}


function changeSup()
{
    var supplier=document.PurchaseOrder.sup.value;
    var sup=supplier.split("--");
    var url="../../../../../../WQS_PurchaseOrder?command=changeSup&SupCode="+sup[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
            processResponse(req);
    }  
    req.send(null);
}

function processResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var val=baseResponse.getElementsByTagName("command")[0];
            var cmd=val.firstChild.nodeValue;
            if(cmd=='changeSup')
            {
                supplierDetails(baseResponse);
            }
            else if(cmd=='Add')
            {
                addRow(baseResponse);
            }
            else if(cmd=='Get')
            {
                getRow(baseResponse);
            }
            else if(cmd=='Delete')
            {
                deleteRow(baseResponse);
            }
            else if(cmd=='checkAvail')
            {
                checkAvailable(baseResponse);
            }
            else
            {
                updateRow(baseResponse);
            }
        }   
    }    
}

function supplierDetails(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
        var status=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
        document.PurchaseOrder.status.value=status;
    }
}

function callServer(command,param)
{
    var lab1=document.PurchaseOrder.lab.value;
    var lab=lab1.split("--");
    var orderno=document.PurchaseOrder.orderno.value;
    var orderdate=document.PurchaseOrder.orderdate.value;
    var supplier=document.PurchaseOrder.sup.value;
    var sup=supplier.split("--");
    var status=document.PurchaseOrder.status.value;
    var remarks=document.PurchaseOrder.remarks.value;
    if(command=='Add')
    {
        var url="../../../../../../WQS_PurchaseOrder?command=Add&LabCode="+lab[0]+"&LabDesc="+lab[1]+"&OrderNo="+orderno+"&OrderDate="+orderdate+"&SupCode="+sup[0]+"&SupDesc="+sup[1]+"&Status="+status+"&Remarks="+remarks;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
                processResponse(req);
        }  
        req.send(null);
    }
    else if(command=="Update")
    {
        var flag=nullCheck();
        if(flag==true)
        {
                url="../../../../../../WQS_PurchaseOrder?command=Update&LabCode="+lab[0]+"&LabDesc="+lab[1]+"&OrderNo="+orderno+"&OrderDate="+orderdate+"&SupCode="+sup[0]+"&SupDesc="+sup[1]+"&Status="+status+"&Remarks="+remarks;
                var req=getTransport();
                req.open("GET",url,true);       
                req.onreadystatechange=function()
                {
                    processResponse(req);                    
                }  
                req.send(null);
        }
    }
       
    else if(command=="Delete")
    { 
                   
            url="../../../../../../WQS_PurchaseOrder?command=Delete&LabCode="+lab[0]+"&LabDesc="+lab[1]+"&OrderNo="+orderno+"&OrderDate="+orderdate+"&SupCode="+sup[0]+"&SupDesc="+sup[1]+"&Status="+status+"&Remarks="+remarks;
            var req=getTransport();
            req.open("GET",url,true);       
            req.onreadystatechange=function()
            {
                    processResponse(req);
            }  
            req.send(null);
    }
    else if(command=="Get")
    {              
            clearAll();
            url="../../../../../../WQS_PurchaseOrder?command=Get";
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
               processResponse(req);
            }  
            req.send(null);
    }
    else
    {
        clearAll();
    }
        
} 

function addRow(baseResponse)
{
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
            {                       
                 alert("Record Inserted Into Database successfully.");
                 
                 var items=new Array();    
                 lcode=baseResponse.getElementsByTagName("LabCode")[0].firstChild.nodeValue;
                 ldesc=baseResponse.getElementsByTagName("LabDesc")[0].firstChild.nodeValue;
                 orderno=baseResponse.getElementsByTagName("OrderNo")[0].firstChild.nodeValue;
                 orderdate=baseResponse.getElementsByTagName("OrderDate")[0].firstChild.nodeValue;
                 supcode=baseResponse.getElementsByTagName("SupCode")[0].firstChild.nodeValue;
                 supdesc=baseResponse.getElementsByTagName("SupDesc")[0].firstChild.nodeValue;
                 status=baseResponse.getElementsByTagName("Status")[0].firstChild.nodeValue;
                 remarks=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                 
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                 
                 seq=seq+1;                         
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");   
                
                 var url="javascript:loadValuesFromTable('" + seq + "')";             
                 anc.href=url;
                 
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
                    
                        
                 var cell2 =document.createElement("TD");   
                 var lcode=document.createTextNode(lcode);  
                 cell2.appendChild(lcode);  
                 mycurrent_row.appendChild(cell2);   
                         
                         
                 var cell3 =document.createElement("TD");   
                 var orderno=document.createTextNode(orderno);   
                 cell3.appendChild(orderno);      
                 mycurrent_row.appendChild(cell3);
                        
                 var cell4 =document.createElement("TD");   
                 var orderdate=document.createTextNode(orderdate);                        
                 cell4.appendChild(orderdate);      
                 mycurrent_row.appendChild(cell4);
                        
                 var cell5 =document.createElement("TD");   
                 var sup=document.createTextNode(supcode);                        
                 cell5.appendChild(sup);      
                 mycurrent_row.appendChild(cell5);
                 
                 var cell6 =document.createElement("TD");   
                 var supdesc=document.createTextNode(supdesc);                        
                 cell6.appendChild(supdesc);      
                 mycurrent_row.appendChild(cell6);
                 
                 var cell7 =document.createElement("TD");   
                 var status=document.createTextNode(status);                        
                 cell7.appendChild(status);      
                 mycurrent_row.appendChild(cell7);
                        
                        
                 var cell8 =document.createElement("TD");   
                 var remarks=document.createTextNode(remarks);                        
                 cell8.appendChild(remarks);      
                 mycurrent_row.appendChild(cell8);
                
                 tbody.appendChild(mycurrent_row);
               
                clearAll(); 
             
            }
            else
            {
                 alert("Record already exists, Insertion not possible");
            }
}

function getRow(baseResponse)
{  
              
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="Success")
    {         
            var tbody=document.getElementById("tblList");
            var table=document.getElementById("Existing");
            var t=0;
            for(t=tbody.rows.length-1;t>=0;t--)
            {
                  tbody.deleteRow(0);
            }  
            var count=baseResponse.getElementsByTagName("count");
            for(var k=0;k<count.length;k++)
            {
                   var LabCode=baseResponse.getElementsByTagName("LabCode");
                   var LabDesc=baseResponse.getElementsByTagName("LabDesc");
                   var OrderNo=baseResponse.getElementsByTagName("OrderNo");
                   var OrderDate=baseResponse.getElementsByTagName("OrderDate");
                   var SupCode=baseResponse.getElementsByTagName("SupCode");
                   var SupDesc=baseResponse.getElementsByTagName("SupDesc");
                   var Status=baseResponse.getElementsByTagName("Status");
                   var Remarks=baseResponse.getElementsByTagName("Remarks");
                        
                        
                  var cLabCode=LabCode.item(k).firstChild.nodeValue;
                  var cLabDesc=LabDesc.item(k).firstChild.nodeValue;
                  var cOrderNo=OrderNo.item(k).firstChild.nodeValue;
                  var cOrderDate=OrderDate.item(k).firstChild.nodeValue;
                  var cSupCode=SupCode.item(k).firstChild.nodeValue;
                  var cSupDesc=SupDesc.item(k).firstChild.nodeValue;
                  var cStatus=Status.item(k).firstChild.nodeValue;
                  var cRemarks=Remarks.item(k).firstChild.nodeValue;
                        
                  seq=seq+1;
                  var mycurrent_row=document.createElement("TR");
                  mycurrent_row.id=seq;
                         
                  var cell=document.createElement("TD");
                  var anc=document.createElement("A");      
                  var url="javascript:loadValuesFromTable('" + seq + "')";             
                  anc.href=url;
                         
                  var txtedit=document.createTextNode("Edit");
                  anc.appendChild(txtedit);
                  cell.appendChild(anc);
                  mycurrent_row.appendChild(cell);
                    
                  var cell2 =document.createElement("TD");   
                  var lab=document.createTextNode(cLabCode);                        
                  cell2.appendChild(lab);      
                  mycurrent_row.appendChild(cell2);   
                                                                        
                  var cell4 =document.createElement("TD");   
                  var orderno=document.createTextNode(cOrderNo);                        
                  cell4.appendChild(orderno);      
                  mycurrent_row.appendChild(cell4);
                        
                  var cell5 =document.createElement("TD");   
                  var orderdate=document.createTextNode(cOrderDate);                        
                  cell5.appendChild(orderdate);      
                  mycurrent_row.appendChild(cell5);
                        
                  var cell6 =document.createElement("TD");   
                  var sup=document.createTextNode(cSupCode);                        
                  cell6.appendChild(sup);      
                  mycurrent_row.appendChild(cell6);
                  
                  var cell7 =document.createElement("TD");   
                  var supdesc=document.createTextNode(cSupDesc);                        
                  cell7.appendChild(supdesc);      
                  mycurrent_row.appendChild(cell7);
                        
                        
                  var cell8 =document.createElement("TD");   
                  var status=document.createTextNode(cStatus);                        
                  cell8.appendChild(status);      
                  mycurrent_row.appendChild(cell8);
                         
                               
                  var c9=document.createElement("TD");   
                  var remarks=document.createTextNode(cRemarks);                        
                  c9.appendChild(remarks);      
                  mycurrent_row.appendChild(c9);
                        
                  tbody.appendChild(mycurrent_row);
                }
            }
            else
            {
                  alert("Failed to Load Values");
            }
}

function clearAll()
{
    document.PurchaseOrder.lab.disabled=false;
    document.PurchaseOrder.orderno.disabled=false;
    document.PurchaseOrder.lab.selectedIndex=0;
    document.PurchaseOrder.orderno.value="";
    document.PurchaseOrder.orderdate.value="";
    document.PurchaseOrder.sup.selectedIndex=0;
    document.PurchaseOrder.status.value="";
    document.PurchaseOrder.remarks.value="";
    document.PurchaseOrder.cmdAdd.disabled=false;
    document.PurchaseOrder.cmdUpdate.disabled=true;
    document.PurchaseOrder.cmdDelete.disabled=true;
    
}

function loadValuesFromTable(rid)
{     
          com_id=rid;
          var r=document.getElementById(rid);
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          var lb=rcells.item(1).firstChild.nodeValue;
          var labcombo=document.getElementById("lab");
          for(var c=labcombo.length-1;c>=0;c--)
          {
                var lb1=labcombo.options[c].text;
                lab=lb1.split("--");
                if(lb==lab[0])
                {
                    ldesc=lab[1];
                }
         }
          document.PurchaseOrder.lab.value=lb+"--"+ldesc;            
          document.PurchaseOrder.orderno.value=rcells.item(2).firstChild.nodeValue;
          document.PurchaseOrder.orderdate.value=rcells.item(3).firstChild.nodeValue;
          var supcode=rcells.item(4).firstChild.nodeValue;
          var supdesc=rcells.item(5).firstChild.nodeValue;
          document.PurchaseOrder.sup.value=supcode+"--"+supdesc;
          document.PurchaseOrder.status.value=rcells.item(6).firstChild.nodeValue;
          document.PurchaseOrder.remarks.value=rcells.item(7).firstChild.nodeValue;
                  
          document.PurchaseOrder.cmdAdd.disabled=true;
          document.PurchaseOrder.cmdUpdate.disabled=false;
          document.PurchaseOrder.cmdDelete.disabled=false;
          document.PurchaseOrder.lab.disabled=true;
          document.PurchaseOrder.orderno.disabled=true;
         // document.PurchaseOrder.txtSupId.disabled=true;        
          document.PurchaseOrder.cmdDelete.focus();
     
}

function deleteRow(baseResponse)
{
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
               if(flag=="Success")
               {
                      //var concessioncode=baseResponse.getElementsByTagName("ConcessionCode")[0].firstChild.nodeValue;
                var tbody=document.getElementById("Existing"); 
                
                var r=document.getElementById(com_id);   
                var ri=r.rowIndex;              
                tbody.deleteRow(ri);
                 
                document.PurchaseOrder.cmdAdd.disabled=false;
                document.PurchaseOrder.cmdUpdate.disabled=true;
                document.PurchaseOrder.cmdDelete.disabled=true;     
                clearAll();
                
                //document.SupplierForm.txtSupId.disabled=false;                                                                              
                alert("Selected Records are Deleted");                     
            }
            else
            {
                alert("Unable to Delete");
            }
  
  }
  
function updateRow(baseResponse)
{
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="Success")
      {  
               alert("Record Updated Successfully.");
               var items=new Array();
               var lab=document.PurchaseOrder.lab.value;
               lb=lab.split("--");
               items[0]=lb[0];
               items[1]=document.PurchaseOrder.orderno.value;
               items[2]=document.PurchaseOrder.orderdate.value;
               var sup=document.PurchaseOrder.sup.value;
               supplier=sup.split("--");
               items[3]=supplier[0];
               items[4]=supplier[1];
               items[5]=document.PurchaseOrder.status.value;
               items[6]=document.PurchaseOrder.remarks.value;
                             
               var r=document.getElementById(com_id);   
               var rcells=r.cells;
               
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                rcells.item(4).firstChild.nodeValue=items[3];
                rcells.item(5).firstChild.nodeValue=items[4];
                rcells.item(6).firstChild.nodeValue=items[5];
                rcells.item(7).firstChild.nodeValue=items[6];
                
                clearAll();
       }
       else
       {
           alert("Failed to update values");
       }                                 
}


function checkAvail()
{
    var lab=document.PurchaseOrder.lab.value;
    var lab=lab.split("--");
    var orderno=document.PurchaseOrder.orderno.value;
    
    url="../../../../../../WQS_PurchaseOrder?command=checkAvail&LabCode="+lab[0]+"&OrderNo="+orderno;
    var req=getTransport();
    req.open("GET",url,true);       
    req.onreadystatechange=function()
    {
            processResponse(req);
    }  
    req.send(null);  
}

function checkAvailable(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="failure")
    { 
            alert("Record already Available");
            document.PurchaseOrder.orderno.value="";
            document.PurchaseOrder.orderno.focus();  
    }
}
 
