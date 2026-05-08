var seq=0;
var com_id="";
function getTransport()
{
    var req=false;
    try
    {
        req=new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch(e1)
    {
        try
        {
            req=new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch(e2)
        {
            req=false;    
        }   
    }
    if(!req && typeof XMLHttpRequest !='undefined')
    {
        req=new XMLHttpRequest();
    }
    return req;
}

function nullCheck()
{
                  if((document.PurchaseDetails.order.value=="") ||(document.PurchaseDetails.order.value.length<=0))
                  {
                       alert("Please Enter the Quantity Order");
                       //document.SupplierForm.txtPro.focus();
                       return false;
                  }
                  return true;
}


function changeLab()
{
    document.PurchaseDetails.orderno.selectedIndex=0;
    var itemcombo=document.getElementById("orderno");
    var child=itemcombo.childNodes;
    for(var c=child.length-1;c>=0;c--)
    {
       itemcombo.removeChild(child[c]);
    }
    var option=document.createElement("option");
    var text=document.createTextNode("-- Select--");
    option.setAttribute("value","0");
    option.appendChild(text);
    itemcombo.appendChild(option);
    
    var labcode=document.PurchaseDetails.lab.value;
    var lab=labcode.split('--');
    var url="../../../../../../WQS_PurchaseOrder_Details?command=changeLab&LabCode="+lab[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }
    req.send(null);
}

function changeCat()
{
    //alert(document.PurchaseDetails.category.value);
    document.PurchaseDetails.item.selectedIndex=0;
    var itemcombo=document.getElementById("item");
    var child=itemcombo.childNodes;
    for(var c=child.length-1;c>=0;c--)
    {
       itemcombo.removeChild(child[c]);
    }
    var option=document.createElement("option");
    var text=document.createTextNode("-- Select Item--");
    option.setAttribute("value","0");
    option.appendChild(text);
    itemcombo.appendChild(option);
    
    var catcode=document.PurchaseDetails.category.value;
    var category=catcode.split('--');
    var url="../../../../../../WQS_PurchaseOrder_Details?command=changeCat&CatDesc="+category[1];
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
                  var baseresponse=req.responseXML.getElementsByTagName("response")[0];
                  var tagCommand=baseresponse.getElementsByTagName("command")[0];
                  var command=tagCommand.firstChild.nodeValue;
                  if(command=='changeLab')
                  {
                        retriveOrder(baseresponse);
                  }
                  else if(command=='changeCat')
                  {
                        retriveItem(baseresponse);
                  }
              else if(command=='Add')
              {
                    addDetails(baseresponse);
              }
              else if(command=='Get')
              {
                    getDetails(baseresponse);
              }
              else if(command=='Update')
              {
                    updateDetails(baseresponse);
              }
              else 
              {
                    deleteDetails(baseresponse);
              }
              
          }
     }
} 

function retriveItem(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
         var count=baseresponse.getElementsByTagName("count");
         var itemcombo=document.getElementById("item");
                 
         for(var i=0;i<count.length;i++)
         {
            var ItemCode=baseresponse.getElementsByTagName("ItemCode");
            var ItemDesc=baseresponse.getElementsByTagName("ItemDesc");
            var code=ItemCode.item(i).firstChild.nodeValue;
            var desc=ItemDesc.item(i).firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(code+"--"+desc);
            opt.setAttribute("value",code+"--"+desc);
            opt.appendChild(text);
            itemcombo.appendChild(opt);  
         }
    }
}

function retriveOrder(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
         var count=baseresponse.getElementsByTagName("count");
         var itemcombo=document.getElementById("orderno");
                 
         for(var i=0;i<count.length;i++)
         {
            var orderno=baseresponse.getElementsByTagName("OrderNo");
            var num=orderno.item(i).firstChild.nodeValue;
            var opt =document.createElement("option"); 
            var text=document.createTextNode(num);
            opt.setAttribute("value",num);
            opt.appendChild(text);
            itemcombo.appendChild(opt);  
         }
    }
    else
    {
        alert("This lab having no order");
    }
}

function callServer(command,param)
{
    var lcode=document.PurchaseDetails.lab.value;
    var lab=lcode.split("--");
    var ono=document.PurchaseDetails.orderno.value;
    var category=document.PurchaseDetails.category.value;
    var cat=category.split("--");
    var itm=document.PurchaseDetails.item.value;
    var item=itm.split("--");
    var qty=document.PurchaseDetails.order.value;
    var remarks=document.PurchaseDetails.remarks.value;
    if(command=='Add')
    {
            url="../../../../../../WQS_PurchaseOrder_Details?command=Add&LabCode="+lab[0]+"&OrderNo="+ono+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0]+"&ItemDesc="+item[1]+"&QtyOrdered="+qty+"&Remarks="+remarks;
            var req=getTransport();
            req.open("GET",url,true);       
            req.onreadystatechange=function()
            {
                 processResponse(req);
            }  
            req.send(null); 
    }
    else if(command=='Get')
    {              
            clearAll();
            var req=getTransport();
            var url="../../../../../../WQS_PurchaseOrder_Details?command=Get";
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                 processResponse(req);
            }  
            req.send(null);
    }
    else if(command=='Delete')
    {
            var req=getTransport();
            var url="../../../../../../WQS_PurchaseOrder_Details?command=Delete&LabCode="+lab[0]+"&OrderNo="+ono+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0]+"&ItemDesc="+item[1]+"&QtyOrdered="+qty+"&Remarks="+remarks;
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                 processResponse(req);
            }  
            req.send(null);
    }
    else if(command=='Update')
    {
            var flag=nullCheck();
            if(flag==true)
            {
                url="../../../../../../WQS_PurchaseOrder_Details?command=Update&LabCode="+lab[0]+"&OrderNo="+ono+"&CatCode="+cat[0]+"&CatDesc="+cat[1]+"&ItemCode="+item[0]+"&ItemDesc="+item[1]+"&QtyOrdered="+qty+"&Remarks="+remarks;
                var req=getTransport();
                req.open("GET",url,true);       
                req.onreadystatechange=function()
                {
                    processResponse(req);
                }  
                req.send(null);
            }
    }            
}


 
 function addDetails(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
        alert("Record Inserted Into Database successfully.");
        var items=new Array();    
        lcode=baseresponse.getElementsByTagName("LabCode")[0].firstChild.nodeValue;
        orderno=baseresponse.getElementsByTagName("OrderNo")[0].firstChild.nodeValue;
        catdesc=baseresponse.getElementsByTagName("CatDesc")[0].firstChild.nodeValue;
        itemcode=baseresponse.getElementsByTagName("ItemCode")[0].firstChild.nodeValue;
        itemdesc=baseresponse.getElementsByTagName("ItemDesc")[0].firstChild.nodeValue;
        order=baseresponse.getElementsByTagName("QtyOrdered")[0].firstChild.nodeValue;
        remarks=baseresponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
        
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
                 var LabCode=document.createTextNode(lcode);                        
                 cell2.appendChild(LabCode);  
                 mycurrent_row.appendChild(cell2);   
                         
                         
                 var cell3 =document.createElement("TD");   
                 var OrderNo=document.createTextNode(orderno);   
                 cell3.appendChild(OrderNo);   
                 mycurrent_row.appendChild(cell3);
                        
                 var cell4 =document.createElement("TD");   
                 var CatDesc=document.createTextNode(catdesc);                        
                 cell4.appendChild(CatDesc);
                 mycurrent_row.appendChild(cell4);
                        
                 var cell5 =document.createElement("TD");   
                 var ItemCode=document.createTextNode(itemcode);                        
                 cell5.appendChild(ItemCode);     
                 mycurrent_row.appendChild(cell5);
                        
                 var cell6 =document.createElement("TD");   
                 var ItemDesc=document.createTextNode(itemdesc);                        
                 cell6.appendChild(ItemDesc);     
                 mycurrent_row.appendChild(cell6);
                 
                 var cell7 =document.createElement("TD");   
                 var Order=document.createTextNode(order);                        
                 cell7.appendChild(Order);     
                 mycurrent_row.appendChild(cell7);
                 
                 var cell8 =document.createElement("TD");   
                 var Remarks=document.createTextNode(remarks);                        
                 cell8.appendChild(Remarks);     
                 mycurrent_row.appendChild(cell8);
                 
                 tbody.appendChild(mycurrent_row); 
                 
                //document.PurchaseDetails.CmdAdd.disabled=false;
                //document.PurchaseDetails.CmdUpdate.disabled=true; 
                //document.PurchaseDetails.CmdDelete.disabled=true;  
                clearAll();
                
                
    }
}

function getDetails(baseResponse)
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
                   var OrderNo=baseResponse.getElementsByTagName("OrderNo");
                   var CatCode=baseResponse.getElementsByTagName("CatCode");
                   var CatDesc=baseResponse.getElementsByTagName("CatDesc");
                   var ItemCode=baseResponse.getElementsByTagName("ItemCode");
                   var ItemDesc=baseResponse.getElementsByTagName("ItemDesc");
                   var QtyOrdered=baseResponse.getElementsByTagName("QtyOrdered");
                   var Remarks=baseResponse.getElementsByTagName("Remarks");
                        
                  var cLabCode=LabCode.item(k).firstChild.nodeValue;
                  var cOrderNo=OrderNo.item(k).firstChild.nodeValue;
                  var cCatCode=CatCode.item(k).firstChild.nodeValue;
                  var cCatDesc=CatDesc.item(k).firstChild.nodeValue;
                  var cItemCode=ItemCode.item(k).firstChild.nodeValue;
                  var cItemDesc=ItemDesc.item(k).firstChild.nodeValue;
                  var cQtyOrdered=QtyOrdered.item(k).firstChild.nodeValue;
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
                  var LabCode=document.createTextNode(cLabCode);                        
                  cell2.appendChild(LabCode);     
                  mycurrent_row.appendChild(cell2);   
                                                 
                  var cell3 =document.createElement("TD");   
                  var OrderNo=document.createTextNode(cOrderNo);   
                  cell3.appendChild(OrderNo);  
                  mycurrent_row.appendChild(cell3);
                                         
                  var cell4 =document.createElement("TD");   
                  var cdesc=document.createTextNode(cCatDesc);                        
                  cell4.appendChild(cdesc); 
                  mycurrent_row.appendChild(cell4);
                        
                  var cell5 =document.createElement("TD");   
                  var icode=document.createTextNode(cItemCode);                        
                  cell5.appendChild(icode);   
                  mycurrent_row.appendChild(cell5);
                        
                  var cell6 =document.createElement("TD");   
                  var idesc=document.createTextNode(cItemDesc);                        
                  cell6.appendChild(idesc);     
                  mycurrent_row.appendChild(cell6);
                  
                  var cell7 =document.createElement("TD");   
                  var QtyOrdered=document.createTextNode(cQtyOrdered);                        
                  cell7.appendChild(QtyOrdered);   
                  mycurrent_row.appendChild(cell7);
                        
                  var cell8 =document.createElement("TD");   
                  var Remarks=document.createTextNode(cRemarks);                        
                  cell8.appendChild(Remarks);     
                  mycurrent_row.appendChild(cell8);
                       
                   
                  tbody.appendChild(mycurrent_row);
                }
            }
            else
            {
                  alert("Failed to Load Values");
            }
} 

function updateDetails(baseResponse)
{
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="Success")
      {  
               alert("Record Updated Successfully.");
               var items=new Array();
               var lb=document.PurchaseDetails.lab.value;
               lab=lb.split("--");
               items[0]=lab[0];
               items[1]=document.PurchaseDetails.orderno.value;
               var cat=document.PurchaseDetails.category.value;
               var category=cat.split("--");
               items[2]=category[1];
               var item=document.PurchaseDetails.item.value;
               ite=item.split("--");
               items[3]=ite[0];
               item[4]=ite[1];
               items[5]=document.PurchaseDetails.order.value;
               items[6]=document.PurchaseDetails.remarks.value;
                              
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
       clearAll();
}


function loadValuesFromTable(rid)
{
    com_id=rid;
    var r=document.getElementById(rid);
    var rcells=r.cells;
    var tbody=document.getElementById("tblList");
    var table=document.getElementById("Existing");
    document.PurchaseDetails.lab.selectedIndex=0;
    document.PurchaseDetails.orderno.selectedIndex=0;
    document.PurchaseDetails.category.selectedIndex=0;
    document.PurchaseDetails.item.selectedIndex=0;
    var labcode=rcells.item(1).firstChild.nodeValue;
    var orderno=rcells.item(2).firstChild.nodeValue;
    var catdesc=rcells.item(3).firstChild.nodeValue;
    var itemcode=rcells.item(4).firstChild.nodeValue;
    var itemdesc=rcells.item(5).firstChild.nodeValue;
    var order=rcells.item(6).firstChild.nodeValue; 
    var remarks=rcells.item(7).firstChild.nodeValue; 
    
   //document.SupplierItem.txtSupId.value=supid+"--"+supname;
    var ldesc="";
    var labcombo=document.getElementById("lab");
    for(var c=labcombo.length-1;c>=0;c--)
    {
        var lb=labcombo.options[c].text;
        lab=lb.split("--");
        if(labcode==lab[0])
        {
            ldesc=lab[1];
        }
    }
    document.PurchaseDetails.lab.value=labcode+"--"+ldesc;
 //   changeLab();
  //  document.PurchaseDetails.orderno.value=orderno;
    
    var ctcode="";
    var catcombo=document.getElementById("category");
    for(var c=catcombo.length-1;c>=0;c--)
    {
        var ct=catcombo.options[c].text;
        cat=ct.split("--");
        if(catdesc==cat[1])
        {
            ctcode=cat[0];
        }
    }
    document.PurchaseDetails.category.value=ctcode+"--"+catdesc;
  //  changeCat();
  //  document.PurchaseDetails.item.value=itemcode+"--"+itemdesc;
    document.PurchaseDetails.order.value=order;
    document.PurchaseDetails.remarks.value=remarks;

    document.PurchaseDetails.CmdAdd.disabled=true;
    document.PurchaseDetails.CmdDelete.disabled=false;
    document.PurchaseDetails.CmdUpdate.disabled=false;
    //document.PurchaseDetails.CmdDelete.focus(); 
    document.PurchaseDetails.lab.disabled=true;
    document.PurchaseDetails.orderno.disabled=true;
    document.PurchaseDetails.category.disabled=true;
    document.PurchaseDetails.item.disabled=true;
    alterLab(orderno,itemcode,itemdesc);
}

function alterLab(orderno,itemcode,itemdesc)
{
    var ono=orderno;
    var ic=itemcode;
    var id=itemdesc;
    var labcode=document.PurchaseDetails.lab.value;
    var lab=labcode.split('--');
    var url="../../../../../../WQS_PurchaseOrder_Details?command=changeLab&LabCode="+lab[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
         if(req.readyState==4)
         {
              if(req.status==200)
              {    
                  var baseresponse=req.responseXML.getElementsByTagName("response")[0];
                  var tagCommand=baseresponse.getElementsByTagName("command")[0];
                  var command=tagCommand.firstChild.nodeValue;
                  var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=='Success')
                  {
                        var count=baseresponse.getElementsByTagName("count");
                        var itemcombo=document.getElementById("orderno");
                 
                        for(var i=0;i<count.length;i++)
                        {
                            var orderno=baseresponse.getElementsByTagName("OrderNo");
                            var num=orderno.item(i).firstChild.nodeValue;
                            var opt =document.createElement("option"); 
                            var text=document.createTextNode(num);
                            opt.setAttribute("value",num);
                            opt.appendChild(text);
                            itemcombo.appendChild(opt);  
                        }
                  }
                    document.PurchaseDetails.CmdDelete.focus();
                    document.PurchaseDetails.orderno.value=ono;
                    alterCat(ic,id);
             }
           }
        }
            
        req.send(null);
}

function alterCat(ic,id)
{
    var catcode=document.PurchaseDetails.category.value;
    var category=catcode.split('--');
    var url="../../../../../../WQS_PurchaseOrder_Details?command=changeCat&CatDesc="+category[1];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
         if(req.readyState==4)
         {
              if(req.status==200)
              {    
                  var baseresponse=req.responseXML.getElementsByTagName("response")[0];
                  var tagCommand=baseresponse.getElementsByTagName("command")[0];
                  var command=tagCommand.firstChild.nodeValue;
                  var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=='Success')
                  {
                        var count=baseresponse.getElementsByTagName("count");
                        var itemcombo=document.getElementById("item");
                        for(var i=0;i<count.length;i++)
                        {
                            var ItemCode=baseresponse.getElementsByTagName("ItemCode");
                            var ItemDesc=baseresponse.getElementsByTagName("ItemDesc");
                            var code=ItemCode.item(i).firstChild.nodeValue;
                            var desc=ItemDesc.item(i).firstChild.nodeValue;
                            var opt =document.createElement("option"); 
                            var text=document.createTextNode(code+"--"+desc);
                            opt.setAttribute("value",code+"--"+desc);
                            opt.appendChild(text);
                            itemcombo.appendChild(opt);  
                        } 
                   }
                    document.PurchaseDetails.CmdDelete.focus();
                    document.PurchaseDetails.item.value=ic+"--"+id;
             }
           }
        }
            
        req.send(null);
}

function deleteDetails(baseResponse)
{
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="Success")
        {
                      //var concessioncode=baseResponse.getElementsByTagName("ConcessionCode")[0].firstChild.nodeValue;
                var tbody=document.getElementById("Existing"); 
                
                var r=document.getElementById(com_id);   
                var ri=r.rowIndex;              
                tbody.deleteRow(ri);                                                                  
                alert("Selected Records are Deleted");                     
        }
        else
        {
                alert("Unable to Delete");
        }
        clearAll();
} 

function clearAll()
{
    document.PurchaseDetails.lab.disabled=false;
    document.PurchaseDetails.orderno.disabled=false;
    document.PurchaseDetails.category.disabled=false;
    document.PurchaseDetails.item.disabled=false;
    
    document.PurchaseDetails.lab.selectedIndex=0;
    document.PurchaseDetails.orderno.selectedIndex=0;
    document.PurchaseDetails.category.selectedIndex=0;
    document.PurchaseDetails.item.selectedIndex=0;
    document.PurchaseDetails.order.value="";
    document.PurchaseDetails.remarks.value="";
    
    document.PurchaseDetails.CmdAdd.disabled=false;
    document.PurchaseDetails.CmdDelete.disabled=true;  
    document.PurchaseDetails.CmdUpdate.disabled=true;  
}

function checkAvail()
{
    var lcode=document.PurchaseDetails.lab.value;
    var lab=lcode.split("--");
    var ono=document.PurchaseDetails.orderno.value;
    var category=document.PurchaseDetails.category.value;
    var cat=category.split("--");
    var itm=document.PurchaseDetails.item.value;
    var item=itm.split("--");
    url="../../../../../../WQS_PurchaseOrder_Details?command=checkAvail&LabCode="+lab[0]+"&OrderNo="+ono+"&CatCode="+cat[0]+"&ItemCode="+item[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        Response(req);
    }  
    req.send(null);   
}

function Response(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {    
              var baseresponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseresponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue;
              checkDuplicate(baseresponse);
           }
    }       
}

function checkDuplicate(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='failure')
    {
        alert("Values already available");
        document.PurchaseDetails.item.selectedIndex=0;
    }
}