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
    document.StdResult.update.disabled=true;
    document.StdResult.delet.disabled=true;
    var url="../../../../../../WQS_NonStandard_ResultServ?command=load";
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    manipulate(req);
    }
    req.send(null);                                        
}

function upd()
{
    var es1=document.getElementById("es").value;
    es=es1.split("--");
    var scode=document.getElementById("scode").value;
    var dv=document.getElementById("dv").value;
    var mv=document.getElementById("mv").value;
    var url="../../../../../../WQS_NonStandard_ResultServ?command=upd&es="+es[0]+"&scode="+scode+"&dv="+dv+"&mv="+mv;
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
    var es=document.getElementById("es").value;
    var scode=document.getElementById("scode").value;
    var dv=document.getElementById("dv").value;
    var mv=document.getElementById("mv").value;
    var url="../../../../../../WQS_NonStandard_ResultServ?command=add&es="+es+"&scode="+scode+"&dv="+dv+"&mv="+mv;
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
    var es=document.getElementById("es").value;
    var scode=document.getElementById("scode").value;
    var url="../../../../../../WQS_NonStandard_ResultServ?command=del&es="+es+"&scode="+scode;
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
       document.StdResult.es.selectedIndex="";
       document.StdResult.scode.selectedIndex=0;
       document.StdResult.dv.value="";
       document.StdResult.mv.value="";
       document.StdResult.es.disabled=false;
       document.StdResult.es.focus();
       document.StdResult.scode.disabled=false;
       document.StdResult.add.disabled=false;
       document.StdResult.update.disabled=true;
       document.StdResult.delet.disabled=true;
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
                                record1[i]=display[i].getElementsByTagName("es")[0].firstChild.nodeValue;
                                record2[i]=display[i].getElementsByTagName("scode")[0].firstChild.nodeValue;                                
                                record3[i]=display[i].getElementsByTagName("dv")[0].firstChild.nodeValue;
                                record4[i]=display[i].getElementsByTagName("mv")[0].firstChild.nodeValue;                                
                        }
                      if(cmd=="add")
                        {
                           var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                           if(flag=="failure")
                           {
                           alert("Failed to insert values")
                           document.StdResult.es.selectedIndex="";
                           document.StdResult.scode.value="";
                           document.StdResult.dv.value="";
                           document.StdResult.mv.value="";
                           }
                           else
                           {
                           alert("Record added")
                           document.StdResult.es.selectedIndex="";
                           document.StdResult.scode.value="";
                           document.StdResult.dv.value="";
                           document.StdResult.mv.value="";
                           document.StdResult.es.disabled=false;
                           document.StdResult.scode.disabled=false;
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
                               document.StdResult.es.selectedIndex="";
                               document.StdResult.scode.value="";
                               document.StdResult.dv.value="";
                               document.StdResult.mv.value="";
                               document.StdResult.es.disabled=false;
                               document.StdResult.scode.disabled=false;
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
                       if(flag=="failure")
                       {
                           alert("Unable to delete")
                           document.StdResult.es.selectedIndex="";
                           document.StdResult.scode.selectedIndex=0;
                           document.StdResult.dv.value="";
                           document.StdResult.mv.value="";
                           var tbody=document.getElementById("tb");
                           tbody.style.display="block";
                           
                           loadPage();             
                       }
                       else if(flag=="success")
                       {
                           alert("Record deleted")
                           document.StdResult.es.selectedIndex="";
                           document.StdResult.scode.selectedIndex=0;
                           document.StdResult.dv.value="";
                           document.StdResult.mv.value="";
                           document.StdResult.es.disabled=false;
                           document.StdResult.scode.disabled=false;
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
               else if(cmd=="checkAvail")
               {
                       var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="Success")
                       {
                            alert("Values already available");
                            document.StdResult.es.selectedIndex="";
                            document.StdResult.scode.value="";
                       }
               }
        }
    }
}

function loadPage()
{
            document.StdResult.update.disabled=true;
            document.StdResult.delet.disabled=true;
            document.StdResult.add.disabled=false;            
            var i=0;
            var c=0;
            var p=0;
            
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
                var url="javascript:loadRecord('"+record1[i]+"','"+record2[i]+"','"+record3[i]+"','"+record4[i]+"')";
                anc.href=url;
                var txtedit=document.createTextNode("Edit");
                anc.appendChild(txtedit);
                cell2.appendChild(anc);
                mycurrent_row.appendChild(cell2);
                                                                           
                            var cell1=document.createElement("TD");
                            cell1.setAttribute('align','left');
                           
                            if(record1[i]!="null")
                            {
                                var currentText=document.createTextNode(record1[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell1.appendChild(currentText);
                            mycurrent_row.appendChild(cell1);
                            
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
                            
                            cell3=document.createElement("TD");
                            cell3.setAttribute('align','left'); 
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
                            
                            cell4=document.createElement("TD");
                            cell4.setAttribute('align','left'); 
                            if(record4[i]!="null")
                            {
                                var currentText=document.createTextNode(record4[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell4.appendChild(currentText);
                            mycurrent_row.appendChild(cell4);
                            
                            tbody.appendChild(mycurrent_row);
            }
}

function loadRecord(es,desc,dv,mv)
{
   document.StdResult.es.value=es;
   document.StdResult.scode.value=desc;
   document.StdResult.dv.value=dv;
   document.StdResult.mv.value=mv;
   document.StdResult.es.disabled=true;
   document.StdResult.scode.disabled=true;
   document.StdResult.add.disabled=true;
   document.StdResult.update.disabled=false;
   document.StdResult.delet.disabled=false;
  
}

function checkAvail()
{
    var es=document.StdResult.es.value;
    var scode=document.StdResult.scode.value;
    var url="../../../../../../WQS_NonStandard_ResultServ?command=checkAvail&es="+es+"&scode="+scode;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}