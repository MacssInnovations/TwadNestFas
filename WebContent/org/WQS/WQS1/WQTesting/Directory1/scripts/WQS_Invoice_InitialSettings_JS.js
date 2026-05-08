function getTransport()
{
    var req=false;
    try
    {
    req=new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch(e1)
    {
        try{
        req=new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch(e2)
        {
        req=false;
        }
     }
     if (!req && typeof XMLHttpRequest != 'undefined') 
     {
            req=new XMLHttpRequest();
     }
     return req;
   
}    

function loading()
{
    document.Invoice_Init.update.disabled=true;
    document.Invoice_Init.delet.disabled=true;
    var lab=document.Invoice_Init.lab.value;
    lab=lab.split("--");
    var url="../../../../../../WQS_Invoice_InitialSettings_Serv?command=load&lab="+lab[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
                                            
}
function added()
{
    var tbody=document.getElementById("tb");
    if(tbody.rows.length==0)
    {
        var lab=document.Invoice_Init.lab.value;
        lab=lab.split("--");
        var invoice=document.Invoice_Init.invoice.value;
        var url="../../../../../../WQS_Invoice_InitialSettings_Serv?command=add&lab="+lab[0]+"&invoice="+invoice;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        manipulate(req);
        }
        req.send(null);
    }
    else
        alert("Invoice entry already available");
    
}

function close_win()
{
window.close();
}
function upd()
{
    var lab=document.Invoice_Init.lab.value;
    lab=lab.split("--");
    var invoice=document.Invoice_Init.invoice.value;
    var url="../../../../../../WQS_Invoice_InitialSettings_Serv?command=update&lab="+lab[0]+"&invoice="+invoice;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
    
}
function del()
{
    var lab=document.Invoice_Init.lab.value;
    lab=lab.split("--");
    var url="../../../../../../WQS_Invoice_InitialSettings_Serv?command=delete&lab="+lab[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
}

function manipulate(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               if(cmd=="delete")
               {
                        var avail=response.getElementsByTagName("avail")[0].firstChild.nodeValue;
                        if(avail=="notavailable")
                        {
                            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                            if(flag=="success")
                            {
                                alert("Record deleted successfully");
                                var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
                                if(count==0)
                                {                                                                 
                                    var tbody=document.getElementById("tb");
                                    try{tbody.innerHTML="";}
                                    catch(e) {tbody.innerText="";}
                                }
                                else
                                {
                                    record1=response.getElementsByTagName("lab")[0].firstChild.nodeValue;
                                    record2=response.getElementsByTagName("invoice")[0].firstChild.nodeValue;
                                    loadPage(record1,record2);
                                }
                                document.Invoice_Init.add.disabled=false;
                            }
                        }
                        else
                        {
                            alert("Can not delete this record");
                            document.Invoice_Init.add.disabled=true;
                        }
               }
               else if(cmd=="update")
               {
                        var avail=response.getElementsByTagName("avail")[0].firstChild.nodeValue;
                        if(avail=="notavailable")
                        {
                            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                            if(flag=="success")
                            {
                               alert("Record updated successfully");
                               record1=response.getElementsByTagName("lab")[0].firstChild.nodeValue;
                               record2=response.getElementsByTagName("invoice")[0].firstChild.nodeValue;
                               loadPage(record1,record2);
                            }
                        }
                        else
                        {
                            alert("Can not update this record");
                            return false;
                        }
                        document.Invoice_Init.add.disabled=true;
               }
               else
               {
                   if(cmd=="add")
                   {
                         
                            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                            if(flag=="success")
                            {
                               alert("Record inserted successfully");
                               document.Invoice_Init.add.disabled=true;
                            }
                            else
                            {
                            	document.Invoice_Init.add.disabled=false;
                            	return false;
                                
                            }
                   }
                   else if(cmd=="load")
                   {
                            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                            if(flag!="success")
                            {
                            	document.Invoice_Init.add.disabled=false;
                            	return false;
                            }
                            else
                            	document.Invoice_Init.add.disabled=true;
                   }
                   record1=response.getElementsByTagName("lab")[0].firstChild.nodeValue;
                   record2=response.getElementsByTagName("invoice")[0].firstChild.nodeValue;
                   loadPage(record1,record2);
               }
               document.Invoice_Init.invoice.value="";
               document.Invoice_Init.update.disabled=true;
               document.Invoice_Init.delet.disabled=true;
            }
        }
}

function loadPage(record1,record2)
{
            var tbody=document.getElementById("tb");
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}
           
            var mycurrent_row=document.createElement("TR"); 
              
            cell2=document.createElement("TD");
            cell2.setAttribute('align','left'); 

            var anc=document.createElement("A");
            var url="javascript:loadRecord('"+record1+"','"+record2+"')";
            anc.href=url;
            var txtedit=document.createTextNode("Edit");
            anc.appendChild(txtedit);
            cell2.appendChild(anc);
            mycurrent_row.appendChild(cell2);
                                                                   
            var cell3=document.createElement("TD");
            cell3.setAttribute('align','left');
            if(record1!="null")
            {
                var currentText=document.createTextNode(record1);
            }
            else
            {
                var currentText=document.createTextNode('');
            }
            cell3.appendChild(currentText);
            mycurrent_row.appendChild(cell3);
                    
            cell4=document.createElement("TD");
            cell4.setAttribute('align','left');
            if(record2!="null")
            {
                var currentText=document.createTextNode(record2);
            }
            else
            {
                var currentText=document.createTextNode('');
            }
            cell4.appendChild(currentText);
            mycurrent_row.appendChild(cell4);
            
            cell2=document.createElement("TD");
            cell2.setAttribute('align','left'); 
            
            tbody.appendChild(mycurrent_row);                        
}

function loadRecord(lab,invoice)
{
   document.Invoice_Init.lab.value=lab;
   document.Invoice_Init.invoice.value=invoice;
   document.Invoice_Init.add.disabled=true;
   document.Invoice_Init.update.disabled=false;
   document.Invoice_Init.delet.disabled=false;
}

function clr()
{
   document.Invoice_Init.lab.value="";
   document.Invoice_Init.invoice.value="";
   var tbody=document.getElementById("tb");   
   if(tbody.rows.length==0)
	   document.Invoice_Init.add.disabled=false;
   else
	   document.Invoice_Init.add.disabled=true;
   document.Invoice_Init.update.disabled=true;
   document.Invoice_Init.delet.disabled=true;
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
                return false 
            }
        }
}  