
function capitalise()
{
var id=document.getElementById("id").value;
capid=id.toUpperCase();
document.getElementById("id").value=capid;
}

function loading()
{
    document.InstrDirFrm.id.focus();
    document.InstrDirFrm.update.disabled=true;
    document.InstrDirFrm.delet.disabled=true;
    var url="../../../../../../WQS_InstrumentCategory?command=load";
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
//alert("add")
var id=document.getElementById("id").value;

var desc=document.getElementById("desc").value;
if(id=="")
    alert("Fill in instrument Code")
else if(desc=="")
    alert("Fill in instrument Specification")
else
    {
    //alert(id)
    //alert(desc)
    var url="../../../../../../WQS_InstrumentCategory?command=add&id="+id+"&desc="+desc;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
    }
}

function close_win()
{
window.close();
}
function upd()
{
//alert("upd")
var id=document.getElementById("id").value;
var desc=document.getElementById("desc").value;
if(id=="")
    alert("Fill in instrument Code")
else if(desc=="")
    alert("Fill in instrument Specification")
else
    {
    var url="../../../../../../WQS_InstrumentCategory?command=upd&id="+id+"&desc="+desc;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
    }
}
function del()
{
var id=document.getElementById("id").value;
if(id=="")
    alert("select some data to delete")
else
    {
     var r=confirm("Are You Sure?")
  if (r==true)
    {
    var url="../../../../../../WQS_InstrumentCategory?command=del&id="+id;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
    }
    }
}

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
    if (!req && typeof XMLHttpRequest != 'undefined') 
        {
        req=new XMLHttpRequest();
        }
   return req;
   
}    

function manipulate(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               //alert(response);
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               //alert(cmd);
               var display=response.getElementsByTagName("display");
                          record1=new Array();
                          record2=new Array();
                            for(i=0;i<display.length;i++)
                            {                                                                   
                                    
                                    //alert(display.length)
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                            }
               
               if(cmd=="add")
               {
               //alert("yes");
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="failure")
               {
               alert("Failed to insert values")
               document.InstrDirFrm.id.value="";
               document.InstrDirFrm.desc.value="";
               
               }
               else
               {
               alert("Record added")
               document.InstrDirFrm.id.value="";
               document.InstrDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
              
                    if(display)
                    {
                    
                          var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';                        
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                         loadPage(); 
                    }
               }
               }
               else if(cmd=="edit")
               {
               var edit=response.getElementsByTagName("edit")[0];
               //alert(edit);
               if(edit)
               {
               var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
               //alert(id)
               var desc=response.getElementsByTagName("desc")[0].firstChild.nodeValue;
               //alert(desc)
               document.InstrDirFrm.id.value=id;
               document.InstrDirFrm.id.disabled=true;
               document.InstrDirFrm.add.disabled=true;               
               document.InstrDirFrm.desc.value=desc;
               }
               } 
               
               else if(cmd=="del")
               {
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               //alert(exists);
               if(flag=="failure")
               {
               alert("Unable to delete")
               document.InstrDirFrm.id.value="";
               document.InstrDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               
               loadPage();             
               }
               else if(flag=="success")
               {
               alert("Record deleted")
               document.InstrDirFrm.id.value="";
               document.InstrDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
              
                    if(display)
                    {
                    
                          var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';                        
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                          loadPage(); 
                    }
               }
               else
               {
                    alert("Can not delete this item");
               }
               }
               else if(cmd=="upd")
               {
               //alert("yes");
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               //alert(exists);
               if(flag=="failure")
               {
               alert("Failed to update")
               }
               else
               {
               alert("Record updated")
               document.InstrDirFrm.id.value="";
               document.InstrDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               
                    if(display)
                    {
                    
                             var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';                        
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                         loadPage(); 
                    }

               }
               }
               else if(cmd=="load")
               {
                var tbody=document.getElementById("tb");
               tbody.style.display="block";
               
                    if(display)
                    {
                    
                             var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';                        
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                         loadPage(); 
                    }

              
               }
                else
                {
                            CheckDuplicate(response);
                }
               
          }
        }  
}

function loadPage()
{
            document.InstrDirFrm.update.disabled=true;
            document.InstrDirFrm.delet.disabled=true;
            document.InstrDirFrm.add.disabled=false;            
            document.InstrDirFrm.id.disabled=false;
            document.InstrDirFrm.id.focus();            
            var i=0;
            var c=0;
            var p=0;//pagination*(page-1);
            
             var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            
            for(i=p;i<record1.length;i++)
            {
                        c++;
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR"); 
                          
                             cell2=document.createElement("TD");
                            cell2.setAttribute('align','left'); 

             
                try{cell2.innerHTML="";}
                  catch(e) {cell2.innerText="";}
                  
                var anc=document.createElement("A");
                var url="javascript:loadRecord('"+record1[i]+"','"+record2[i]+"')";
                anc.href=url;
                var txtedit=document.createTextNode("Edit");
                anc.appendChild(txtedit);
                cell2.appendChild(anc);
                mycurrent_row.appendChild(cell2);
                                                                           
                            var cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                           
                            if(record1[i]!="null")
                            {
                                //alert(record1[i])
                                var currentText=document.createTextNode(record1[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                             cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(record2[i]!="null")
                            {
                                var currentText=document.createTextNode(record2[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left'); 
                            
                            tbody.appendChild(mycurrent_row);
            }
}

function changepage()
{
var page=document.InstrDirFrm.cmbpage.value;
loadPage(parseInt(page));
}

function loadRecord(id,desc)
{
   document.InstrDirFrm.id.value=id;
   document.InstrDirFrm.desc.value=desc;
   document.InstrDirFrm.id.disabled=true;
   document.InstrDirFrm.add.disabled=true;
    document.InstrDirFrm.update.disabled=false;
    document.InstrDirFrm.delet.disabled=false;
  
}

function clr()
{
document.InstrDirFrm.id.value="";
document.InstrDirFrm.desc.value="";
document.InstrDirFrm.id.disabled=false;
document.InstrDirFrm.id.focus();
document.InstrDirFrm.add.disabled=false;
document.InstrDirFrm.update.disabled=true;
document.InstrDirFrm.delet.disabled=true;
    
}

function checkdup()
{
    var cid=document.InstrDirFrm.id.value;
    var req=getTransport();
    var url="../../../../../../WQS_InstrumentCategory?command=duplicate&id="+cid;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}

function CheckDuplicate(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='failure')
    {
        alert("Category code is already available");
        document.InstrDirFrm.id.value="";
	document.InstrDirFrm.id.focus(); 
    }
}