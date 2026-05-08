
function loading()
{
    document.uomfrm.update.disabled=true;
    document.uomfrm.delet.disabled=true;
    var url="../../../../../../WQS_UOM_Masterserv?command=load";
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
var id=document.getElementById("code").value;

var desc=document.getElementById("desc").value;
if(id=="")
    alert("Fill in UOM Code")
else if(desc=="")
    alert("Fill in UOM Description")
else
    {
    var url="../../../../../../WQS_UOM_Masterserv?command=add&id="+id+"&desc="+desc;
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
var id=document.getElementById("code").value;
var desc=document.getElementById("desc").value;
if(id=="")
    alert("Fill in UOM Code")
else if(desc=="")
    alert("Fill in UOM Description")
else
    {
    var url="../../../../../../WQS_UOM_Masterserv?command=upd&id="+id+"&desc="+desc;
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
var id=document.getElementById("code").value;
if(id=="")
    alert("select some data to delete")
else
    {
     var r=confirm("Are You Sure?")
  if (r==true)
    {
    var url="../../../../../../WQS_UOM_Masterserv?command=del&id="+id;
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
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               if(cmd=="add")
               {
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="failure")
               {
               alert("Failed to insert values")
               document.uomfrm.code.value="";
               document.uomfrm.desc.value="";
               
               }
               else
               {
               alert("Record added")
               document.uomfrm.code.value="";
               document.uomfrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
                var display=response.getElementsByTagName("display");
                    if(display)
                    {
                    
                             var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                           
                         
                          record1=new Array();
                          record2=new Array();
                            for(i=0;i<display.length;i++)
                            {                                                                   
                                    
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                            }
                                    loadPage();
                            }
                            
                       
                } 
                
               }
               else if(cmd=="del")
               {
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="failure")
               {
               alert("Unable to delete")
               document.uomfrm.code.value="";
               document.uomfrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
                var display=response.getElementsByTagName("display");
                    if(display)
                    {
                    
                             var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';                        
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                           
                         
                          record1=new Array();
                          record2=new Array();
                            for(i=0;i<display.length;i++)
                            {                                                                   
                                    
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                            }
                                    loadPage();
                            }
                            
                       
                } 
               
               
               else
               {
               alert("Record deleted")
               document.uomfrm.code.value="";
               document.uomfrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
                var display=response.getElementsByTagName("display");
                //alert(display)
                    if(display)
                    {
                    
                          var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                           
                         
                          record1=new Array();
                          record2=new Array();
                            for(i=0;i<display.length;i++)
                            {                                                                   
                                    
                                    //alert(display.length)
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                            }
                                    loadPage(1);
                            }
                            
                       
                } 
                 
               }
               
               else if(cmd=="upd")
               {
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="failure")
               {
               alert("Failed to update")
               }
               else
               {
               alert("Record updated")
               document.uomfrm.code.value="";
               document.uomfrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
                var display=response.getElementsByTagName("display");
                    if(display)
                    {
                    
                             var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          //tbody.innerText='';
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                           
                         
                          record1=new Array();
                          record2=new Array();
                            for(i=0;i<display.length;i++)
                            {                                                                   
                                    
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                            }
                                    loadPage();
                            }
                            
                       
                }  
                 
               }
               
               else if(cmd=="load")
               {
                var tbody=document.getElementById("tb");
                tbody.style.display="block";
               //tbody.innertext='';
                var display=response.getElementsByTagName("display");
                    if(display)
                    {
                    
                             var i=0;
                          var tbody=document.getElementById("tb");
                          tbody.style.display="";
                          tbody.innerText='';                        
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                           
                         
                          record1=new Array();
                          record2=new Array();
                            for(i=0;i<display.length;i++)
                            {                                                                   
                                    
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                            }
                            
                           
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
            document.uomfrm.update.disabled=true;
            document.uomfrm.delet.disabled=true;
            document.uomfrm.code.disabled=false;
            document.uomfrm.code.focus();            
            var i=0;
            var c=0;

            
             var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            
            for(i=0;i<record1.length;i++)
            {
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
 
            clr();
}


function loadRecord(id,descval)
{
    document.uomfrm.code.value=id;
    document.uomfrm.desc.value=descval;
    document.uomfrm.add.disabled=true;
    document.uomfrm.delet.disabled=false;
    document.uomfrm.code.disabled=true;
    
    document.uomfrm.update.disabled=false;
    document.uomfrm.delet.disabled=false;
    var url="../../../../../../WQS_UOM_Masterserv?command=edit&id="+id;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
}

function checkdup()
{
    var cid=document.uomfrm.code.value;
    var req=getTransport();
    var url="../../../../../../WQS_UOM_Masterserv?command=duplicate&id="+cid;
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
        alert("UOM code is already available");
            document.uomfrm.code.value="";
            document.uomfrm.code.focus(); 
    }
}

function clr()
{
document.uomfrm.code.value="";
document.uomfrm.desc.value="";
document.uomfrm.code.disabled=false;
document.uomfrm.code.focus();
document.uomfrm.update.disabled=true;
document.uomfrm.delet.disabled=true;
document.uomfrm.add.disabled=false;
    
}