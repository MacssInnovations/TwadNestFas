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

function changeCat()
{
    document.SupplierItem.txtItemCode.selectedIndex=0;
    var itemcombo=document.getElementById("txtItemCode");
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
    
    var catcode=document.SupplierItem.txtCatCode.value;
    var category=catcode.split('--');
    var url="../../../../../../WQS_SupplierItemServ?command=changeCat&urlCatDesc="+category[1];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }
    req.send(null);
}

function callServer(command,param)
{
    var lab=document.SupplierItem.lab.value;
    lab=lab.split("--");
    var supplier=document.SupplierItem.txtSupId.value;
    var category=document.SupplierItem.txtCatCode.value;
    var item=document.SupplierItem.txtItemCode.value;
    var sup=supplier.split("--");
    var cat=category.split("--");
    var item1=item.split("--");
    if(command=='Add')
    {
            url="../../../../../../WQS_SupplierItemServ?command=Add&lab="+lab[0]+"&urlSupCode="+sup[0]+"&urlSupName="+sup[1]+"&urlCatCode="+cat[0]+"&urlCatDesc="+cat[1]+"&urlItemCode="+item1[0]+"&urlItemDesc="+item1[1];
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
            clearone();
            var req=getTransport();
            var url="../../../../../../WQS_SupplierItemServ?command=Get&lab="+lab[0];
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                 processResponse(req);
            }  
            req.send(null);
    }
    else
    {   
            var req=getTransport();
            var url="../../../../../../WQS_SupplierItemServ?command=Delete&lab="+lab[0]+"&urlSupCode="+sup[0]+"&urlSupName="+sup[1]+"&urlCatCode="+cat[0]+"&urlCatDesc="+cat[1]+"&urlItemCode="+item1[0]+"&urlItemDesc="+item1[1];
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                 processResponse(req);
            }  
            req.send(null);
    } 
     
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
              if(command=='Update')
              {
                    updateDetails(baseresponse);
              }
              else if(command=='changeCat')
              {
                    retriveItem(baseresponse);
              }
              else if(command=='Add')
              {
                    addDetails(baseresponse);
              }
              else if(command=='Delete')
              {
                    deleteDetails(baseresponse);
              }
              else if(command=='checkAvail')
              {
                    checkAvailable(baseresponse);
              }
              else 
              {
                    getDetails(baseresponse);  
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
         var itemcombo=document.getElementById("txtItemCode");
                 
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

function addDetails(baseresponse)
{
    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='Success')
    {
        alert("Record Inserted Into Database successfully.");
        var items=new Array();    
        var lcode=baseresponse.getElementsByTagName("LabCode")[0].firstChild.nodeValue;
        scode=baseresponse.getElementsByTagName("SupplierCode")[0].firstChild.nodeValue;
        sname=baseresponse.getElementsByTagName("SupplierName")[0].firstChild.nodeValue;
        catdesc=baseresponse.getElementsByTagName("CatDesc")[0].firstChild.nodeValue;
        itemcode=baseresponse.getElementsByTagName("ItemCode")[0].firstChild.nodeValue;
        itemdesc=baseresponse.getElementsByTagName("ItemDesc")[0].firstChild.nodeValue;
        
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
                // cell.setAttribute("align","center");
                 mycurrent_row.appendChild(cell);
                 
                 var cell21 =document.createElement("TD");   
                 var txtlab=document.createTextNode(lcode);                        
                 cell21.appendChild(txtlab);  
                // cell2.setAttribute("align","center");
                 mycurrent_row.appendChild(cell21);  
                 
                 var cell2 =document.createElement("TD");   
                 var txtsucode=document.createTextNode(scode);                        
                 cell2.appendChild(txtsucode);  
                // cell2.setAttribute("align","center");
                 mycurrent_row.appendChild(cell2);   
                         
                         
                 var cell3 =document.createElement("TD");   
                 var txtsuname=document.createTextNode(sname);   
                 cell3.appendChild(txtsuname);   
                 //cell3.setAttribute("align","center");
                 mycurrent_row.appendChild(cell3);
                        
                 var cell4 =document.createElement("TD");   
                 var cdesc=document.createTextNode(catdesc);                        
                 cell4.appendChild(cdesc);
                 //cell4.setAttribute("align","center");
                 mycurrent_row.appendChild(cell4);
                        
                 var cell5 =document.createElement("TD");   
                 var icode=document.createTextNode(itemcode);                        
                 cell5.appendChild(icode);     
                 //cell5.setAttribute("align","center");
                 mycurrent_row.appendChild(cell5);
                        
                 var cell6 =document.createElement("TD");   
                 var idesc=document.createTextNode(itemdesc);                        
                 cell6.appendChild(idesc);     
                 //cell6.setAttribute("align","center");
                 mycurrent_row.appendChild(cell6);
                 
                 tbody.appendChild(mycurrent_row); 
                 
                document.SupplierItem.CmdAdd.disabled=false;
                document.SupplierItem.CmdDelete.disabled=true;  
                document.SupplierItem.txtSupId.disabled=false;   
                clearone(); 
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
            var count=baseResponse.getElementsByTagName("getcount");
            for(var k=0;k<count.length;k++)
            {
                   var LabCode=baseResponse.getElementsByTagName("LabCode");
                   var SupplierCode=baseResponse.getElementsByTagName("SupplierCode");
                   var SupplierName=baseResponse.getElementsByTagName("SupplierName");
                   var CatDesc=baseResponse.getElementsByTagName("CatDesc");
                   var ItemCode=baseResponse.getElementsByTagName("ItemCode");
                   var ItemDesc=baseResponse.getElementsByTagName("ItemDesc");
                                       
                        
                  var cLabCode=LabCode.item(k).firstChild.nodeValue;
                  var cSupplierCode=SupplierCode.item(k).firstChild.nodeValue;
                  var cSupplierName=SupplierName.item(k).firstChild.nodeValue;
                  var cCatDesc=CatDesc.item(k).firstChild.nodeValue;
                  var cItemCode=ItemCode.item(k).firstChild.nodeValue;
                  var cItemDesc=ItemDesc.item(k).firstChild.nodeValue;
                                          
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

                  var cell21 =document.createElement("TD");   
                  var txtlab=document.createTextNode(cLabCode);                        
                  cell21.appendChild(txtlab);     
                  mycurrent_row.appendChild(cell21);   
                    
                  var cell2 =document.createElement("TD");   
                  var txtsucode=document.createTextNode(cSupplierCode);                        
                  cell2.appendChild(txtsucode);     
                  mycurrent_row.appendChild(cell2);   
                                                 
                  var cell3 =document.createElement("TD");   
                  var txtsuname=document.createTextNode(cSupplierName);   
                  cell3.appendChild(txtsuname);  
                  mycurrent_row.appendChild(cell3);
                        
                  var cell4 =document.createElement("TD");   
                  var txtcdesc=document.createTextNode(cCatDesc);                        
                  cell4.appendChild(txtcdesc); 
                  mycurrent_row.appendChild(cell4);
                        
                  var cell5 =document.createElement("TD");   
                  var txticode=document.createTextNode(cItemCode);                        
                  cell5.appendChild(txticode);   
                  mycurrent_row.appendChild(cell5);
                        
                  var cell6 =document.createElement("TD");   
                  var txtidesc=document.createTextNode(cItemDesc);    
                  cell6.appendChild(txtidesc);     
                  mycurrent_row.appendChild(cell6);
                       
                   
                  tbody.appendChild(mycurrent_row);
                }
            }
            else
            {
                  alert("Failed to Load Values");
            }
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
                 
                document.SupplierItem.CmdAdd.disabled=false;
                document.SupplierItem.CmdDelete.disabled=true;     
                clearone();
                document.SupplierItem.txtSupId.disabled=false;                                                                              
                alert("Selected Records are Deleted");                     
        }
        else if("FoundData")
        {
                    alert("Can not delete this Supplier");
        }
        else
        {
                alert("Unable to Delete");
               
        }
                
                
                
  
} 

function loadValuesFromTable(rid)
{
    com_id=rid;
    var r=document.getElementById(rid);
    var rcells=r.cells;
    var tbody=document.getElementById("tblList");
    var table=document.getElementById("Existing");
    document.SupplierItem.txtSupId.SelectedIndex=0;
    document.SupplierItem.txtCatCode.SelectedIndex=0;
    var supid=rcells.item(2).firstChild.nodeValue;
    var supname=rcells.item(3).firstChild.nodeValue;
    var catdesc=rcells.item(4).firstChild.nodeValue;
    var icode=rcells.item(5).firstChild.nodeValue;
    var idesc=""; 
    document.SupplierItem.txtSupId.value=supid+"--"+supname;
    var ctcode="";
    var catcombo=document.getElementById("txtCatCode");
    for(var c=catcombo.length-1;c>=0;c--)
    {
        var ct=catcombo.options[c].text;
        cat=ct.split("--");
        if(catdesc==cat[1])
        {
            ctcode=cat[0];
        }
    }
    document.SupplierItem.txtCatCode.value=ctcode+"--"+catdesc;
    document.SupplierItem.txtSupId.disabled=true;
    document.SupplierItem.txtCatCode.disabled=true;
    document.SupplierItem.txtItemCode.disabled=true;
    document.SupplierItem.CmdAdd.disabled=true;
    document.SupplierItem.CmdDelete.disabled=false;
    document.SupplierItem.txtItemCode.selectedIndex=0; 
 
     var itemcombo=document.getElementById("txtItemCode");
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
  
    var url="../../../../../../WQS_SupplierItemServ?command=changeCat&urlCatDesc="+catdesc;
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
                    var itemcombo=document.getElementById("txtItemCode");
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
                    for(var c=itemcombo.length-1;c>=0;c--)
                    {
                        var cat=new Array();
                        var ct=itemcombo.options[c].text;
                        cat=ct.split("--");
                        if(icode==cat[0])
                        {
                            idesc=cat[1];
                        }
                    }
                     
                }
                
                document.SupplierItem.CmdDelete.focus(); 
                var itcode=icode+"--"+idesc;
                document.SupplierItem.txtItemCode.value=itcode;
            }
        }
    }
    req.send(null); 
}

function clearone()
 {
   document.SupplierItem.txtSupId.selectedIndex=0;
   document.SupplierItem.txtCatCode.selectedIndex=0;
   document.SupplierItem.txtItemCode.selectedIndex=0;
   var itemcombo=document.getElementById("txtItemCode");
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
  
   document.SupplierItem.CmdAdd.disabled=false;
   document.SupplierItem.CmdDelete.disabled=true;
   document.SupplierItem.txtSupId.disabled=false;
   document.SupplierItem.txtCatCode.disabled=false;
   document.SupplierItem.txtItemCode.disabled=false;
  
 } 
 
 function checkAvail()
{
    var lab=document.SupplierItem.lab.value;
    lab=lab.split("--");
    var supid=document.SupplierItem.txtSupId.value;
    var sup=supid.split("--");
    var catcode=document.SupplierItem.txtCatCode.value;
    var cat=catcode.split("--");
    var itemcode=document.SupplierItem.txtItemCode.value;
    var item1=itemcode.split("--");
    url="../../../../../../WQS_SupplierItemServ?command=checkAvail&lab="+lab[0]+"&urlSupCode="+sup[0]+"&urlSupName="+sup[1]+"&urlCatCode="+cat[0]+"&urlCatDesc="+cat[1]+"&urlItemCode="+item1[0]+"&urlItemDesc="+item1[1];
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
            alert("These items are already exsist");
            document.SupplierItem.txtItemCode.selectedIndex=0;      
    }
}
 

 
