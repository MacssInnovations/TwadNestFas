//var no_of_pages;
//var pagination=4;
function capitalise()
{
var id=document.getElementById("id").value;
capid=id.toUpperCase();
document.getElementById("id").value=capid;
}

function loading()
{
    document.GlassDirFrm.id.focus();
    document.GlassDirFrm.update.disabled=true;
    document.GlassDirFrm.delet.disabled=true;
    var url="../../../../../../WQS_GlasswareDirectoryServ?command=load";
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
    alert("Fill in Glassware Code")
else if(desc=="")
    alert("Fill in Glassware Specification")
else
    {
    //alert(id)
    //alert(desc)
    var url="../../../../../../WQS_GlasswareDirectoryServ?command=add&id="+id+"&desc="+desc;
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
    alert("Fill in Glassware Code")
else if(desc=="")
    alert("Fill in Glassware Specification")
else
    {
    var url="../../../../../../WQS_GlasswareDirectoryServ?command=upd&id="+id+"&desc="+desc;
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
    var url="../../../../../../WQS_GlasswareDirectoryServ?command=del&id="+id;
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
               //var display=response.getElementsByTagName("display")[0];
               //alert(flag);
               if(flag=="failure")
               {
               alert("Failed to insert values")
               document.GlassDirFrm.id.value="";
               document.GlassDirFrm.desc.value="";
               
               }
               else
               {
               alert("Record added");
               clr();
               //document.GlassDirFrm.id.value="";
               //document.GlassDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
               //var display=response.getElementsByTagName("display");
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
               document.GlassDirFrm.id.value=id;
               document.GlassDirFrm.id.disabled=true;
               document.GlassDirFrm.add.disabled=true;               
               document.GlassDirFrm.desc.value=desc;
               }
               } 
               
               else if(cmd=="del")
               {
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               //alert(exists);
               if(flag=="failure")
               {
               alert("Unable to delete")
               document.GlassDirFrm.id.value="";
               document.GlassDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
               loadPage();             
               }
               else if(flag=="FoundData")
               {
                    alert("Can not delete this item");
               }
               else
               {
               alert("Record deleted")
               clr();
               //document.GlassDirFrm.id.value="";
               //document.GlassDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
               //var display=response.getElementsByTagName("display");
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
               clr();
               //document.GlassDirFrm.id.value="";
               //document.GlassDirFrm.desc.value="";
               var tbody=document.getElementById("tb");
               tbody.style.display="block";
               //tbody.innertext='';
               //var display=response.getElementsByTagName("display");
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
               //tbody.innertext='';
               //var display=response.getElementsByTagName("display");
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
            document.GlassDirFrm.update.disabled=true;
            document.GlassDirFrm.delet.disabled=true;
            document.GlassDirFrm.id.disabled=false;
            document.GlassDirFrm.id.disabled=false;            
            document.GlassDirFrm.id.focus();            
            var i=0;
            var c=0;
            var p=0;//pagination*(page-1);
            
             var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            //document.GlassDirFrm.cmbpage.selectedIndex=page-1;
            
            for(i=p;i<record1.length;i++)// && c<pagination;i++)
            {
                        c++;
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR"); 
                          
                             cell2=document.createElement("TD");
                            cell2.setAttribute('align','left'); 

             
                try{cell2.innerHTML="";}
                  catch(e) {cell2.innerText="";}
                  
                 var anc=document.createElement("A");
                var url="javascript:loadRecord('"+record1[i]+"')";
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


function loadRecord(id)
{
    document.GlassDirFrm.update.disabled=false;
    document.GlassDirFrm.delet.disabled=false;
    var url="../../../../../../WQS_GlasswareDirectoryServ?command=edit&id="+id;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);
}

function clr()
{
document.GlassDirFrm.id.value="";
document.GlassDirFrm.desc.value="";
document.GlassDirFrm.id.disabled=false;
document.GlassDirFrm.id.focus();
document.GlassDirFrm.add.disabled=false;
document.GlassDirFrm.update.disabled=true;
document.GlassDirFrm.delet.disabled=true;
    
}


function checkdup()
{
    var cid=document.GlassDirFrm.id.value;
    var req=getTransport();
    var url="../../../../../../WQS_GlasswareDirectoryServ?command=duplicate&id="+cid;
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
        alert("Glassware code is already available");
        document.GlassDirFrm.id.value="";
	document.GlassDirFrm.id.focus(); 
    }
}
