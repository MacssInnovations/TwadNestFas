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

function capitalise()
{
    var sn=document.getElementById("sn").value;
    capid=sn.toUpperCase();
    if(capid=='S'||capid=='N')
        document.getElementById("sn").value=capid;
    else
    {
        alert("Enter S or N");
        document.getElementById("sn").value="";
    }
}
function added()
{
    var val=checkValidation();
    if(val==true)
    {
        var es=document.getElementById("es").value;
        var desc=document.getElementById("desc").value;
        var sn=document.getElementById("sn").value;
        var val="";
        if(document.getElementById("phy").checked==true)
            val="Physical";
        else if(document.getElementById("chem").checked==true)
            val="Chemical";
        else if(document.getElementById("bact").checked==true)
            val="Bacteriological"
        else if(document.getElementById("bio").checked==true)
            val="Biological"
        var test_purpose=document.ElementSymbol.test_purpose.value;   
        test_purpose=test_purpose.split("--");
         if(test_purpose[0]=="SEW" || test_purpose[0]=="EFF")
            test_purpose[0]="DRI";
        var url="../../../../../../WQS_ElementSymbolServ?command=add&es="+es+"&desc="+desc+"&sn="+sn+"&val="+val+"&test_purpose="+test_purpose[0];
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
                manipulate(req);
            }
            req.send(null);
    }
}
function checkValidation()
{
    if(document.ElementSymbol.test_purpose.value==""||document.ElementSymbol.test_purpose.selectedIndex==0)
    {
        alert("Select Test Purpose");
        return false;
    }
    else if(document.ElementSymbol.es.value=="")
    {
        alert("Enter Parameter Name");
        return false;
    }
    else if(document.ElementSymbol.desc.value=="")
    {
        alert("Enter Parameter Description");
        return false;
    }
    else if(document.ElementSymbol.sn.value=="")
    {
        alert("Enter Standard/NonStandard");
        return false;
    }
    else if(document.ElementSymbol.b1[0].checked==false && document.ElementSymbol.b1[1].checked==false && document.ElementSymbol.b1[2].checked==false && document.ElementSymbol.b1[3].checked==false)
    {
        alert("Select Test Type");
        return false;
    }
    else
        return true;
    
}
function loading()
{
    clearFun();
    var tbody=document.getElementById("tb");
    tbody.style.display="";
    tbody.innerText='';                        
    try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}
    document.ElementSymbol.update.disabled=true;
    document.ElementSymbol.delet.disabled=true;
    var test_purpose=document.ElementSymbol.test_purpose.value;   
    test_purpose=test_purpose.split("--");
     if(test_purpose[0]=="SEW" || test_purpose[0]=="EFF")
        test_purpose[0]="DRI";
    var url="../../../../../../WQS_ElementSymbolServ?command=load&test_purpose="+test_purpose[0];
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
    var test_purpose=document.ElementSymbol.test_purpose.value;   
    test_purpose=test_purpose.split("--");
     if(test_purpose[0]=="SEW" || test_purpose[0]=="EFF")
        test_purpose[0]="DRI";
    var sno=document.getElementById("sno").value;
    var es=document.getElementById("es").value;
    var url="../../../../../../WQS_ElementSymbolServ?command=del&sno="+sno+"&test_purpose="+test_purpose[0];
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
    var val=checkValidation();
    if(val==true)
    {
        var sno=document.getElementById("sno").value;
        var es=document.getElementById("es").value;
        var dep=document.getElementById("desc").value;
        var sn=document.getElementById("sn").value;
        var val="";
        if(document.getElementById("phy").checked==true)
            val="Physical";
        else if(document.getElementById("chem").checked==true)
            val="Chemical";
        else if(document.getElementById("bact").checked==true)
            val="Bacteriological"
        var test_purpose=document.ElementSymbol.test_purpose.value;   
        test_purpose=test_purpose.split("--");
        if(test_purpose[0]=="SEW"||test_purpose[0]=="EFF")
            test_purpose[0]="DRI";
        var url="../../../../../../WQS_ElementSymbolServ?command=upd&sno="+sno+"&es="+es+"&desc="+dep+"&sn="+sn+"&val="+val+"&test_purpose="+test_purpose[0];
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        manipulate(req);
        }
        req.send(null);
    }
}

function clr()
{
    document.getElementById("snoid").style.display="none";
    document.getElementById("snoid1").style.display="none";
    document.ElementSymbol.sno.value="";
    clearFun();
    document.ElementSymbol.test_purpose.disabled=false;
    document.ElementSymbol.add.disabled=false;
    document.ElementSymbol.update.disabled=true;
    document.ElementSymbol.delet.disabled=true;
    
}

function clearFun()
{
    document.ElementSymbol.es.value="";
    document.ElementSymbol.desc.value="";
    document.ElementSymbol.sn.value="";
    for(var i=0;i<3;i++)
    {
        document.ElementSymbol.b1[i].checked=false;
    }
    document.ElementSymbol.es.focus();
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
                        record5=new Array();
                        record6=new Array();
                        for(i=0;i<display.length;i++)
                        {                                                                   
                                record5[i]=display[i].getElementsByTagName("sno")[0].firstChild.nodeValue;
                                record1[i]=display[i].getElementsByTagName("es")[0].firstChild.nodeValue;
                                record2[i]=display[i].getElementsByTagName("desc")[0].firstChild.nodeValue;                                
                                record3[i]=display[i].getElementsByTagName("sn")[0].firstChild.nodeValue;                                
                                record4[i]=display[i].getElementsByTagName("tr")[0].firstChild.nodeValue;                                
                        }
                       if(cmd=="add")
                       {
                           var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                           if(flag=="failure")
                           {
                                alert("Failed to insert values")
                           }
                           else
                           {
                           alert("Record added")
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
                                clr();
                           }
                           
               }
               else if(cmd=="checkParam")
               {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag!="Success")
                    {
                        alert("Record is already available");
                        document.ElementSymbol.es.value="";
                        document.ElementSymbol.es.focus();
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
                else if(cmd=="del")
               {
                       var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="failure")
                       {
                           alert("Unable to delete")
                           var tbody=document.getElementById("tb");
                           tbody.style.display="block";
                           
                           loadPage();             
                       }
                       else if(flag=="success")
                       {
                           alert("Record deleted")
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
                           clr();
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
                                    clr();
                       }
                      
               }
}
}
}

function loadPage()
{
            document.ElementSymbol.update.disabled=true;
            document.ElementSymbol.delet.disabled=true;
            document.ElementSymbol.add.disabled=false;            
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
                            var url="javascript:loadRecord('"+record1[i]+"','"+record2[i]+"','"+record3[i]+"','"+record4[i]+"','"+record5[i]+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("Edit");
                            anc.appendChild(txtedit);
                            cell2.appendChild(anc);
                            mycurrent_row.appendChild(cell2);
                                                                           
                            var cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                           
                            if(record1[i]!="null")
                            {
                                var currentText=document.createTextNode(record5[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
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

function loadRecord(es,desc,sn,tr,sno)
{
   document.getElementById("snoid").style.display="block";
   document.getElementById("snoid1").style.display="block";
   document.ElementSymbol.test_purpose.disabled=true;
   document.ElementSymbol.sno.value=sno;
   document.ElementSymbol.es.value=es;
   document.ElementSymbol.desc.value=desc;
   document.ElementSymbol.sn.value=sn;
   if(tr=="Physical")
        document.ElementSymbol.b1[0].checked=true;
   else if(tr=="Chemical")
        document.ElementSymbol.b1[1].checked=true;
   else if(tr=="Bacteriological")
        document.ElementSymbol.b1[2].checked=true;
   else if(tr=="Biological")
        document.ElementSymbol.b1[3].checked=true;
   document.ElementSymbol.add.disabled=true;
   document.ElementSymbol.update.disabled=false;
   document.ElementSymbol.delet.disabled=false;  
}

function checkParam()
{
        var test_purpose=document.ElementSymbol.test_purpose.value;   
        test_purpose=test_purpose.split("--");
        if(test_purpose[0]=="SEW"||test_purpose[0]=="EFF")
            test_purpose[0]="DRI";
        var es=document.getElementById("es").value;
        var url="../../../../../../WQS_ElementSymbolServ?command=checkParam&es="+es+"&test_purpose="+test_purpose[0];
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
          manipulate(req);
        }
        req.send(null);
}

function checkTestPurpose()
{
    if(document.ElementSymbol.test_purpose.value==""||document.ElementSymbol.test_purpose.selectedIndex==0)
    {
        alert("Select Test Purpose");
        document.ElementSymbol.es.value="";
    }
}