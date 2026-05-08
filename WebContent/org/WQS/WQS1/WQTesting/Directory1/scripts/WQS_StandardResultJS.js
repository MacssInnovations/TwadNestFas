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
function loadParameter()
{
    var es=document.getElementById("es");      
    var child=es.childNodes;
    for(var c=child.length-1;c>1;c--)
    {
            es.removeChild(child[c]);
            
    }
    var test_purpose=document.getElementById("test_purpose").value;
    test_purpose=test_purpose.split("--");
    var parameter="";
    if(document.StdResult.std[1].checked==true)
        parameter="NonStd";
    else 
        parameter="Std";
    var url="../../../../../../WQS_StandardResultServ?command=loadParameter&test_purpose="+test_purpose[0]+"&parameter="+parameter;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manip(req);
    }
    req.send(null);  
}
function changeStd()
{
    loadParameter();   
    var tbody1=document.getElementById("tb");
    tbody1.style.display="";
    tbody1.innerText='';                        
    try{tbody1.innerHTML="";}
        catch(e) {tbody1.innerText="";}
         var parameter="";
    var test_purpose=document.getElementById("test_purpose").value;
    test_purpose=test_purpose.split("--");   
    if(document.StdResult.std[0].checked==true)
        parameter="Std";
    else
        parameter="NonStd";
    var url="../../../../../../WQS_StandardResultServ?command=load&test_purpose="+test_purpose[0]+"&parameter="+parameter;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);    
}
function manip(req)
{
    if(req.readyState==4)
    {
      if(req.status==200)
      {
           var response=req.responseXML.getElementsByTagName("response")[0];
           var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
           if(flag=="success")
           {
                    var es=document.getElementById("es");      
                    var display=response.getElementsByTagName("display");                    
                    for(var j=0;j<display.length;j++)
                    {
                        element=response.getElementsByTagName("es")[j].firstChild.nodeValue;
                        var opt=document.createElement("option");
                        opt.setAttribute("value",element);
                        var opttext=document.createTextNode(element);
                        opt.appendChild(opttext);
                        es.appendChild(opt);
                    }
           }
      }
    }
}
function clearAll()
{
    var tbody1=document.getElementById("tb");
    tbody1.style.display="";
    tbody1.innerText='';                        
    try{tbody1.innerHTML="";}
        catch(e) {tbody1.innerText="";}
    var tbody2=document.getElementById("con_tb");
    tbody2.style.display="";
    tbody2.innerText='';                        
    try{tbody2.innerHTML="";}
        catch(e) {tbody2.innerText="";}
    var tbody3=document.getElementById("alum_tb");
    tbody3.style.display="";
    tbody3.innerText='';                        
    try{tbody3.innerHTML="";}
        catch(e) {tbody3.innerText="";}
}
function loading()
{
    clearAll();
    loadParameter();   
    var test_purpose=document.getElementById("test_purpose").value;
    test_purpose=test_purpose.split("--");   
    if(test_purpose[0]=="DRI")
    {
        document.getElementById("drinking").style.display="block";
        document.getElementById("construction").style.display="none";
        document.getElementById("alum").style.display="none";
        document.getElementById("std_div").style.display="block";
        document.StdResult.std[0].checked=true;
        document.StdResult.std[1].checked=false;
        document.StdResult.update.disabled=true;
        document.StdResult.delet.disabled=true;
    }
    else if(test_purpose[0]=="CON")
    {
        document.getElementById("drinking").style.display="none";
        document.getElementById("construction").style.display="block";
        document.getElementById("alum").style.display="none";
        document.getElementById("std_div").style.display="none";
        document.StdResult.std[0].checked=false;
        document.StdResult.std[1].checked=false;
        document.StdResult.cmdUpdate.disabled=true;
        document.StdResult.cmdDelete.disabled=true;
    }
    else if(test_purpose[0]=="ALUM")
    {
        document.getElementById("drinking").style.display="none";
        document.getElementById("construction").style.display="none";
        document.getElementById("alum").style.display="block";
        document.getElementById("std_div").style.display="none";
        document.StdResult.std[0].checked=false;
        document.StdResult.std[1].checked=false;
        document.StdResult.cmdUpdate1.disabled=true;
        document.StdResult.cmdDelete1.disabled=true;
    }
    var parameter="";
    if(document.StdResult.std[0].checked==true)
        parameter="Std";
    else
        parameter="NonStd";
    var url="../../../../../../WQS_StandardResultServ?command=load&test_purpose="+test_purpose[0]+"&parameter="+parameter;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);                                        
}

function upd(command)
{
    var test_purpose=document.getElementById("test_purpose").value;
    test_purpose=test_purpose.split("--");
    var es=document.getElementById("es").value;
    if(command=="Drinking")
    {
        var parameter="";
        if(document.StdResult.std[0].checked==true)
            parameter="Std";
        else
            parameter="NonStd";
        var scode=document.getElementById("scode").value;
        var dv=document.getElementById("dv").value;
        var mv=document.getElementById("mv").value;
        var url="../../../../../../WQS_StandardResultServ?command=upd&test_purpose="+test_purpose[0]+"&parameter="+parameter+"&es="+es+"&scode="+scode+"&dv="+dv+"&mv="+mv;
    }
    else if(command=="Construction")
    {
            var perm_limit=document.getElementById("perm_limit").value;            
            var url="../../../../../../WQS_StandardResultServ?command=upd&test_purpose="+test_purpose[0]+"&es="+es+"&perm_limit="+perm_limit;
    }
    else if(command=="Alum")
    {
            var grade1=document.getElementById("grade1").value;   
            var grade2=document.getElementById("grade2").value;   
            var grade3=document.getElementById("grade3").value;   
            var url="../../../../../../WQS_StandardResultServ?command=upd&test_purpose="+test_purpose[0]+"&es="+es+"&grade1="+grade1+"&grade2="+grade2+"&grade3="+grade3;
    }
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}

function added(command)
{
    var test_purpose=document.getElementById("test_purpose").value;
    test_purpose=test_purpose.split("--");
    var es=document.getElementById("es").value;
    if(command=="Drinking")
    {
        var validation=validatefun();
        if(validation==true)
        {
            var parameter="";
            if(document.StdResult.std[0].checked==true)
                parameter="Std";
            else
                parameter="NonStd";
            var scode=document.getElementById("scode").value;
            var dv=document.getElementById("dv").value;
            var mv=document.getElementById("mv").value;
            var url="../../../../../../WQS_StandardResultServ?command=add&test_purpose="+test_purpose[0]+"&parameter="+parameter+"&es="+es+"&scode="+scode+"&dv="+dv+"&mv="+mv;
        }
    }
    else if(command=="Construction")
    {
        var validation=validatefun1();
        if(validation==true)
        {
            var perm_limit=document.getElementById("perm_limit").value;            
            var url="../../../../../../WQS_StandardResultServ?command=add&test_purpose="+test_purpose[0]+"&es="+es+"&perm_limit="+perm_limit;
        }
    }
    else if(command=="Alum")
    {
        var validation=validatefun2();
        if(validation==true)
        {
            var grade1=document.getElementById("grade1").value;   
            var grade2=document.getElementById("grade2").value;   
            var grade3=document.getElementById("grade3").value;   
            var url="../../../../../../WQS_StandardResultServ?command=add&test_purpose="+test_purpose[0]+"&es="+es+"&grade1="+grade1+"&grade2="+grade2+"&grade3="+grade3;
        }
    }
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
    var test_purpose=document.getElementById("test_purpose").value;
    test_purpose=test_purpose.split("--");
    var es=document.getElementById("es").value;
    var parameter="";
    if(document.StdResult.std[0].checked==true)
        parameter="Std";
    else
        parameter="NonStd";
    var url="../../../../../../WQS_StandardResultServ?command=del&test_purpose="+test_purpose[0]+"&es="+es+"&parameter="+parameter;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46){
                        //	alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}
function clr()
{
       var test_purpose=document.StdResult.test_purpose.value;
       test_purpose=test_purpose.split("--");
       document.StdResult.test_purpose.disabled=false;
       document.StdResult.es.selectedIndex="";
       document.StdResult.es.disabled=false;
       if(test_purpose[0]=="DRI")
       {
           
           document.StdResult.scode.selectedIndex=0;
           document.StdResult.dv.value="";
           document.StdResult.mv.value="";                  
          // document.StdResult.es.focus();
           document.StdResult.scode.disabled=false;
           document.StdResult.add.disabled=false;
           document.StdResult.update.disabled=true;
           document.StdResult.delet.disabled=true;
        }
        else if(test_purpose[0]=="CON")
        {
           document.StdResult.perm_limit.value="";
           document.StdResult.cmdAdd.disabled=false;
           document.StdResult.cmdUpdate.disabled=true;
           document.StdResult.cmdDelete.disabled=true;
        }
        else if(test_purpose[0]=="ALUM")
        {
           document.StdResult.grade1.value="";
           document.StdResult.grade2.value="";
           document.StdResult.grade3.value="";
           document.StdResult.cmdAdd1.disabled=false;
           document.StdResult.cmdUpdate1.disabled=true;
           document.StdResult.cmdDelete1.disabled=true;
        }
}

function manipulate(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               var purpose=response.getElementsByTagName("purpose")[0].firstChild.nodeValue;
               var display=response.getElementsByTagName("display");
               record1=new Array();
               record2=new Array();
               record3=new Array();
               record4=new Array();
               if(purpose=="Drinking")
               {
                        for(i=0;i<display.length;i++)
                        {                                                                   
                                record1[i]=display[i].getElementsByTagName("es")[0].firstChild.nodeValue;
                                record2[i]=display[i].getElementsByTagName("scode")[0].firstChild.nodeValue;                                
                                record3[i]=display[i].getElementsByTagName("dv")[0].firstChild.nodeValue;
                                record4[i]=display[i].getElementsByTagName("mv")[0].firstChild.nodeValue;                                
                        }
                }
                else if(purpose=="Construction")
                {
                        for(i=0;i<display.length;i++)
                        {                                                                   
                                record1[i]=display[i].getElementsByTagName("es")[0].firstChild.nodeValue;
                                record2[i]=display[i].getElementsByTagName("plimit")[0].firstChild.nodeValue;                                
                        }
                }
                else if(purpose=="Alum")
                {
                        for(i=0;i<display.length;i++)
                        {                                                                   
                                record1[i]=display[i].getElementsByTagName("es")[0].firstChild.nodeValue;
                                record2[i]=display[i].getElementsByTagName("grade1")[0].firstChild.nodeValue;                                
                                record3[i]=display[i].getElementsByTagName("grade2")[0].firstChild.nodeValue;
                                record4[i]=display[i].getElementsByTagName("grade3")[0].firstChild.nodeValue;                                
                        }
                }
                if(cmd=="add")
                {
                   var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                   if(flag=="failure")
                       alert("Failed to insert values")
                   else
                   {
                       alert("Record added")
                       if(display)
                          loadPage(); 
                   }
                   clr();
               }
               else if(cmd=="load")
               {
                    if(display)
                          loadPage(); 
               }
               else if(cmd=="upd")
               {
                       var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="failure")
                            alert("Failed to update")
                       else
                       {
                               alert("Record updated")
                               if(display)
                                      loadPage(); 
                       }
                       clr();
               }
               else if(cmd=="del")
               {
                       var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="failure")
                       {
                           alert("Unable to delete")
                           clr();
                           loadPage();             
                       }
                       else if(flag=="success")
                       {
                           alert("Record deleted");
                           clr();
                           if(display)
                                  loadPage(); 
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
                       }
               }
                
        }
    }
}

function loadPage()
{
            var test_purpose=document.StdResult.test_purpose.value;
            test_purpose=test_purpose.split("--");
            var c=0;
            if(test_purpose[0]=="DRI")
                var tbody=document.getElementById("tb");
            else if(test_purpose[0]=="CON")
                var tbody=document.getElementById("con_tb");
            else 
                var tbody=document.getElementById("alum_tb");
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}
            for(i=0;i<record1.length;i++)
            {
                        c++;                       
                        var mycurrent_row=document.createElement("TR"); 
                          
                        cell2=document.createElement("TD");
                        cell2.setAttribute('align','left'); 

                        try{cell2.innerHTML="";}
                          catch(e) {cell2.innerText="";}
                          
                        var anc=document.createElement("A");
                        if(test_purpose[0]=="DRI")
                            var url="javascript:loadRecord('"+record1[i]+"','"+record2[i]+"','"+record3[i]+"','"+record4[i]+"')";
                        else if(test_purpose[0]=="CON")
                        {
                            var url="javascript:loadRecord('"+record1[i]+"','"+record2[i]+"')";
                        }
                        else if(test_purpose[0]=="ALUM")
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
                        if(test_purpose[0]!="CON")
                        {
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
                        }
                        tbody.appendChild(mycurrent_row);
            }
}

function loadRecord(val1,val2,val3,val4)
{
    document.StdResult.test_purpose.disabled=true;
    document.StdResult.es.disabled=true;
    var test_purpose=document.StdResult.test_purpose.value;
    test_purpose=test_purpose.split("--");
    document.StdResult.es.value=val1;
    if(test_purpose[0]=="DRI")
    {
       document.StdResult.scode.disabled=true;
       document.StdResult.add.disabled=true;
       document.StdResult.update.disabled=false;
       document.StdResult.delet.disabled=false;       
       document.StdResult.scode.value=val2;
       document.StdResult.dv.value=val3;
       document.StdResult.mv.value=val4;
    }
    else if(test_purpose[0]=="CON")
    {
       document.StdResult.perm_limit.value=val2;
       document.StdResult.cmdAdd.disabled=true;
       document.StdResult.cmdUpdate.disabled=false;
       document.StdResult.cmdDelete.disabled=false;
    }
    else if(test_purpose[0]=="ALUM")
    {
       document.StdResult.grade1.value=val2;
       document.StdResult.grade2.value=val3;
       document.StdResult.grade3.value=val4;
       document.StdResult.cmdAdd1.disabled=true;
       document.StdResult.cmdUpdate1.disabled=false;
       document.StdResult.cmdDelete1.disabled=false;
    }
}
function checkParameter()
{
    if(document.StdResult.es.value==""||document.StdResult.es.selectedIndex==0)
    {
        alert("Select Parameter");
        document.StdResult.es.focus();
        return false;
    }
    else
        return true;
}
function checkStandard()
{
    var val=checkParameter();
    if(val==true)
    {
        if(document.StdResult.scode.value==""||document.StdResult.scode.selectedIndex==0)
        {
            alert("Select Standard Code");
            document.StdResult.scode.focus();
            return false;
        }
        else
            return true;
    }
}
function checkDesirable()
{
    var val1=checkStandard();
    if(val1==true)
    {
        if(document.StdResult.dv.value==""||document.StdResult.dv.value.length==0)
        {
            alert("Enter Desirable Value");
            document.StdResult.dv.focus();
            return false;
        }
        else
            return true;
    }
}
function validatefun()
{
        var val2=checkDesirable();
        if(val2==true)
        {
            if(document.StdResult.mv.value==""||document.StdResult.mv.value.length==0)
            {
                alert("Enter Maximum Value");
                document.StdResult.mv.focus();
                return false;
            }
            else
                return true;
        }
}
function validatefun1()
{
        var val2=checkParameter();
        if(val2==true)
        {
            if(document.StdResult.perm_limit.value==""||document.StdResult.perm_limit.value.length==0)
            {
                alert("Enter Permissible Limit");
                document.StdResult.perm_limit.focus();
                return false;
            }
            else
                return true;
        }
}
function validatefun2()
{
        var val2=checkGrade2();
        if(val2==true)
        {
            if(document.StdResult.grade3.value==""||document.StdResult.grade3.value.length==0)
            {
                alert("Enter Grade 3");
                document.StdResult.grade3.focus();
                return false;
            }
            else
                return true;
        }
}
function checkGrade1()
{
    var val2=checkParameter();
    if(val2==true)
    {
        if(document.StdResult.grade1.value==""||document.StdResult.grade1.value.length==0)
        {
            alert("Enter Grade 1");
            document.StdResult.grade1.focus();
            return false;
        }
        else
            return true;
    }
}
function checkGrade2()
{
    var val2=checkGrade1();
    if(val2==true)
    {
        if(document.StdResult.grade2.value==""||document.StdResult.grade2.value.length==0)
        {
            alert("Enter Grade 2");
            document.StdResult.grade2.focus();
            return false;
        }
        else
            return true;
    }
}
function checkAvail()
{
    var es=document.StdResult.es.value;
    var test_purpose=document.getElementById("test_purpose").value;
    test_purpose=test_purpose.split("--");
    var parameter="";
    if(document.StdResult.std[0].checked==true)
        parameter="Std";
    else
        parameter="NonStd";
    var url="../../../../../../WQS_StandardResultServ?command=checkAvail&es="+es+"&test_purpose="+test_purpose[0]+"&parameter="+parameter;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);
}