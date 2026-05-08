function capitalise()
{
    var id=document.getElementById("id").value;
    capid=id.toUpperCase();
    document.getElementById("id").value=capid;
}

function loading()
{
    document.ChemDirFrm.id.focus();
    var url="../../../../../../WQS_ChemicalDirectory?command=load";
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
    var id=document.getElementById("id").value;

    var desc=document.getElementById("desc").value;
    var frmla=document.getElementById("frmla").value;
    var pflag=document.getElementById("pflag").value;
    if(id=="")
        alert("Fill in Chemical Code")
    else if(desc=="")
        alert("Fill in Chemical Specification")
    else if(frmla=="")
        alert("Fill in Chemical Formula")
    else if(pflag=="")
        alert("Fill in Priyarity Flag")       
    else
    {
        var url="../../../../../../WQS_ChemicalDirectory?command=add&id="+id+"&desc="+desc+"&frmla="+frmla+"&pflag="+pflag;
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
    var id=document.getElementById("id").value;
    var desc=document.getElementById("desc").value;
    var frmla=document.getElementById("frmla").value;
    var pflag=document.getElementById("pflag").value;
    if(id=="")
        alert("Fill in Chemical Code")
    else if(desc=="")
        alert("Fill in Chemical Specification")
    else if(frmla=="")
        alert("Fill in Chemical Formula")
    else if(pflag=="")
        alert("Fill in Priyarity Flag")   
    else
    {
        var url="../../../../../../WQS_ChemicalDirectory?command=upd&id="+id+"&desc="+desc+"&frmla="+frmla+"&pflag="+pflag;
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
            var url="../../../../../../WQS_ChemicalDirectory?command=del&id="+id;
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
               var display=response.getElementsByTagName("display");
               record1=new Array();
               record2=new Array();
               record3=new Array();
               record4=new Array();
               for(i=0;i<display.length;i++)
                            {                                                                   
                                    
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                                    record3[i]=display[i].getElementsByTagName("frmla")[0].firstChild.nodeValue;                                
                                    record4[i]=display[i].getElementsByTagName("pflag")[0].firstChild.nodeValue;
                                    //alert(record1[i]+" "+record2[i]+" "+record3[i]+" "+record4[i]+" "+record5[i]);
                            }
               
               if(cmd=="add")
               {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=="failure")
               {
               alert("Failed to insert values")
               document.ChemDirFrm.id.value="";
               document.ChemDirFrm.desc.value="";
               document.ChemDirFrm.frmla.value="";
               document.ChemDirFrm.pflag.value="";
               }
               else
               {
               alert("Record added")
               document.ChemDirFrm.id.value="";
               document.ChemDirFrm.desc.value="";
               document.ChemDirFrm.frmla.value="";
               document.ChemDirFrm.pflag.value="";
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
                        
               else if(cmd=="del")
               {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                   // alert(flag);
                    if(flag=="failure")
                    {
                        alert("Unable to delete")
                        document.ChemDirFrm.id.value="";
                        document.ChemDirFrm.desc.value="";
                        document.ChemDirFrm.frmla.value="";
                        document.ChemDirFrm.pflag.value="";
                        var tbody=document.getElementById("tb");
                        tbody.style.display="block";
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
                             record3=new Array();
                             record4=new Array();
                             for(i=0;i<display.length;i++)
                             {                                                                   
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;  
                                    record3[i]=display[i].getElementsByTagName("frmla")[0].firstChild.nodeValue;        
                                    record4[i]=display[i].getElementsByTagName("pflag")[0].firstChild.nodeValue;        
                             }
                                    loadPage();
                        }
                            
                       
                } 
                else if(flag=="success")
                {
                    alert("Record deleted")
                    document.ChemDirFrm.id.value="";
                    document.ChemDirFrm.desc.value="";
                    document.ChemDirFrm.frmla.value="";
                    document.ChemDirFrm.pflag.value="";
                    var tbody=document.getElementById("tb");
                    tbody.style.display="block";
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
                          record3=new Array();
                          record4=new Array();
                          for(i=0;i<display.length;i++)
                          {                                                                   
                                    
                                     record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;  
                                    record3[i]=display[i].getElementsByTagName("frmla")[0].firstChild.nodeValue;        
                                    record4[i]=display[i].getElementsByTagName("pflag")[0].firstChild.nodeValue;                                        
                            }
                                    loadPage(1);
                      }
                            
                       
                } 
                else 
                {
                    alert("Can not delete this item");
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
                        document.ChemDirFrm.id.value="";
                        document.ChemDirFrm.desc.value="";
                        document.ChemDirFrm.frmla.value="";
                        document.ChemDirFrm.pflag.value="";
                        var tbody=document.getElementById("tb");
                        tbody.style.display="block";
                        var display=response.getElementsByTagName("display");
                        if(display)
                        {
                    
                            var i=0;
                            var tbody=document.getElementById("tb");
                            tbody.style.display="";
                            try{tbody.innerHTML="";}
                            catch(e) {tbody.innerText="";}
                           
                         
                             record1=new Array();
                             record2=new Array();
                             record3=new Array();
                             record4=new Array();
                             for(i=0;i<display.length;i++)
                             {                                                                   
                                    record1[i]=display[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                    
                                    record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;  
                                    record3[i]=display[i].getElementsByTagName("frmla")[0].firstChild.nodeValue;        
                                    record4[i]=display[i].getElementsByTagName("pflag")[0].firstChild.nodeValue;        
                             }
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
            document.ChemDirFrm.delet.disabled=true;
            document.ChemDirFrm.update.disabled=true;
            document.ChemDirFrm.add.disabled=false;
            document.ChemDirFrm.id.disabled=false;
            document.ChemDirFrm.id.focus();            
            var i=0;
            var c=0;
            var p=0;//pagination*(page-1);
            
             var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            //document.ChemDirFrm.cmbpage.selectedIndex=page-1;
            
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
                var url="javascript:loadRecord('"+record1[i]+"','"+record2[i]+"','"+record3[i]+"','"+record4[i]+"')";
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
                            
                              cell3=document.createElement("TD");
                            cell3.setAttribute('align','left');
                            //alert(items[j]); 
                            if(record3[i]!="null")
                            {
                                var currentText=document.createTextNode(record3[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell3.appendChild(currentText);
                            mycurrent_row.appendChild(cell3);
                            
                                                        
                            cell5=document.createElement("TD");
                            cell5.setAttribute('align','left');
                            //alert(items[j]); 
                            if(record4[i]!="null")
                            {
                                var currentText=document.createTextNode(record4[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell5.appendChild(currentText);
                            mycurrent_row.appendChild(cell5);
                            
                        tbody.appendChild(mycurrent_row);
            }
}


function loadRecord(id,desc,frmla,pflag)
{
    document.ChemDirFrm.update.disabled=false;
    document.ChemDirFrm.delet.disabled=false;
    document.ChemDirFrm.add.disabled=true;
    document.ChemDirFrm.id.disabled=true;
    document.ChemDirFrm.id.value=id;
    document.ChemDirFrm.desc.value=desc;
    document.ChemDirFrm.frmla.value=frmla;
    document.ChemDirFrm.pflag.value=pflag;
}

function clr()
{
document.ChemDirFrm.id.value="";
document.ChemDirFrm.desc.value="";
document.ChemDirFrm.frmla.value="";
document.ChemDirFrm.pflag.value="";
document.ChemDirFrm.add.disabled=false;
document.ChemDirFrm.id.disabled=false;
document.ChemDirFrm.id.focus();
document.ChemDirFrm.update.disabled=true;
document.ChemDirFrm.delet.disabled=true;
    
}

function checkdup()
{
    var cid=document.ChemDirFrm.id.value;
    var req=getTransport();
    var url="../../../../../../WQS_ChemicalDirectory?command=duplicate&id="+cid;
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
        alert("Chemical code is already available");
        document.ChemDirFrm.id.value="";
	document.ChemDirFrm.id.focus(); 
    }
}

function checkNum(string) 
{
   var iChars = "0123456789";
   for (var i=0; i<string.length; i++)
   {
       if (iChars.indexOf(string.charAt(i))== -1)
        {
            alert("Numbers only allowed.....");
            document.ChemDirFrm.pflag.value="";
            document.ChemDirFrm.pflag.focus();
            break;
        }
       }
}

