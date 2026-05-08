var __pagination=5;
var totalblock=0;
function checkid()
{
    if(document.getElementById("type").value==""||document.getElementById("type").value==0)
    {
        alert("select customer type");
        document.getElementById("name").value="";
        document.getElementById("type").focus();
        return false;
    }
    else if(document.getElementById("cid").value==""||document.getElementById("cid").value.length==0)
    {
        if(document.getElementById("type").value=='Twad'||document.getElementById("type").value=='Local Body'||document.getElementById("type").value=='Twad Staff'||document.getElementById("type").value=='Twad Student')
        {
            alert("select customer id");
            document.getElementById("name").value="";
            document.getElementById("cid").focus();
            return false;
        }
    }
    else
        return true;
}

function validateSubmit()
{
    var valid=checkid();
    if(valid==true)
    {
        if(document.getElementById("name").value==""||document.getElementById("name").value==0)
        {
            alert("Enter Customer Name");
            document.getElementById("name").focus();
            return false;
        }
        else 
            return true;
    }
}
var winjob="";
function servicepopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
    var ctype=document.getElementById("type").value; 
    if(ctype=="Twad")
    {
        winjob= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/WQS_JobPopupJSP.jsp","officesearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
        winjob.moveTo(250,250);  
        winjob.focus();
    }
    else if(ctype=="Twad Staff"||ctype=="Twad Student")
    {
        winemp= window.open("../../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }   
    else if(ctype=="Local Body")
    {
        winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/WQS_LocalBody_JSP.jsp","officesearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }   
}
function doParentJob(jobid,deptid,designation)
{
    if(deptid=="TWAD")
    {
       document.getElementById("name").value=designation;
       document.getElementById("cid").value=jobid;
       getCustomer();
       //checkoffice();
       return true
    }
    else
    {
            alert('Please select a TWAD Office');
            if (winjob && winjob.open && !winjob.closed) 
            {
               winjob.resizeTo(500,500);
               winjob.moveTo(250,250); 
               winjob.focus();
            }
            return false
    }
}

function doParentEmp(val)
{
    document.CustomerDet.cid.value=val;
    getCustomer();
}

function doParentLocal(cid,design)
{
     document.CustomerDet.cid.value=cid;
     document.CustomerDet.name.value=design;
}

function getCustomer()
{
        document.CustomerDet.address.value="";
        document.CustomerDet.address.value="";
        var ctype=document.CustomerDet.type.value;
        var lab=document.CustomerDet.labcode.value;
        lab=lab.split("--");
        var cid=document.CustomerDet.cid.value;
        var url="../../../../../../WQS_Customer_MasterServ?command=getCustomer&lab="+lab[0]+"&cid="+cid+"&ctype="+ctype;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            manip(req);
        }
        req.send(null); 
}
function manip(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="getCustomer")
            {
                if(flag=="success")
                {
                    var type=baseResponse.getElementsByTagName("type")[0].firstChild.nodeValue;
                    if(type=="twad")
                    {
                        var avail=baseResponse.getElementsByTagName("avail")[0].firstChild.nodeValue;
                        if(avail=="available")
                        {
                            var add1=baseResponse.getElementsByTagName("offadd1")[0].firstChild.nodeValue;
                            var add2=baseResponse.getElementsByTagName("offadd2")[0].firstChild.nodeValue;
                            var tname=baseResponse.getElementsByTagName("tname")[0].firstChild.nodeValue;
                            var code=baseResponse.getElementsByTagName("pcode")[0].firstChild.nodeValue;
                            var district=baseResponse.getElementsByTagName("district")[0].firstChild.nodeValue;
                            var level=baseResponse.getElementsByTagName("level")[0].firstChild.nodeValue;
                            var add="";
                            if(add1!=""||add1!="null")
                                add=add1;
                            if(add2!=""||add2!="null")
                                add=add+"\n"+add2;
                            if(tname!=""||tname!="null")
                                add=add+"\n"+tname;
                            if(code!=""||code!="null"||code!=0)
                                add=add+"\n"+code;
                            document.CustomerDet.address.value=add;
                        }
                        else
                        {
                            var message=baseResponse.getElementsByTagName("message")[0].firstChild.nodeValue;
                            alert(message);
                            document.CustomerDet.cid.value="";
                            document.CustomerDet.cid.focus();
                        }
                    }
                    else if(type=="twad_staff")
                    {
                        var avail=baseResponse.getElementsByTagName("avail")[0].firstChild.nodeValue;
                        if(avail=="available")
                        {
                            var emp_name=baseResponse.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                            var add1=baseResponse.getElementsByTagName("offadd1")[0].firstChild.nodeValue;
                            var add2=baseResponse.getElementsByTagName("offadd2")[0].firstChild.nodeValue;
                            var tname=baseResponse.getElementsByTagName("tname")[0].firstChild.nodeValue;
                            var code=baseResponse.getElementsByTagName("pcode")[0].firstChild.nodeValue;
                            var district=baseResponse.getElementsByTagName("district")[0].firstChild.nodeValue;
                            var level=baseResponse.getElementsByTagName("level")[0].firstChild.nodeValue;
                            var add="";
                            if(add1!=""||add1!="null")
                                add=add1;
                            if(add2!=""||add2!="null")
                                add=add+"\n"+add2;
                            if(tname!=""||tname!="null")
                                add=add+"\n"+tname;
                            if(code!=""||code!="null"||code!=0)
                                add=add+"\n"+code;
                            document.CustomerDet.name.value=emp_name;
                            document.CustomerDet.address.value=add;
                        }
                        else
                        {
                            var message=baseResponse.getElementsByTagName("message")[0].firstChild.nodeValue;
                            alert(message);
                            document.CustomerDet.cid.value="";
                            document.CustomerDet.cid.focus();
                        }
                    }
                }
                else
                {
                    alert("This customer id already exist");
                    document.CustomerDet.cid.value="";
                    document.CustomerDet.cid.focus();                
                }
            }
            else if(command=="duplicate")
            {
                if(flag!="success")
                {
                    alert("This Customer is already available");
                    document.CustomerDet.cid.value="";
                    document.CustomerDet.cid.focus(); 
                }
            }
        }
    }
}
function changeType()
{
    var ctype=document.CustomerDet.type.value;
    document.CustomerDet.cid.value="";
    document.CustomerDet.name.value="";
    document.CustomerDet.address.value="";
    document.CustomerDet.email_id.value="";
    var tbody=document.getElementById("tb");
    try
    {tbody.innerHTML="";
    }
    catch(e){tbody.innerText="";}
    
    if(ctype=="Twad"||ctype=="Twad Staff"||ctype=="Twad Student")
    {
        document.getElementById("icon").style.visibility="visible";
        //document.CustomerDet.address.disabled=true;
        //document.CustomerDet.address.style.backgroundColor="rgb(214,214,214)";
        document.CustomerDet.cid.disabled=false;
        document.CustomerDet.cid.style.backgroundColor="white";
    }
    else
    {
        if(ctype=="Local Body")
            document.getElementById("icon").style.visibility="visible";
        else
            document.getElementById("icon").style.visibility="hidden";
        //document.CustomerDet.address.disabled=false;
        //document.CustomerDet.address.style.backgroundColor="white";
        document.CustomerDet.cid.disabled=true;
        document.CustomerDet.cid.style.backgroundColor="rgb(214,214,214)";
    }
    var lab=document.CustomerDet.labcode.value;
    lab=lab.split("--");
    var url="../../../../../../WQS_Customer_MasterServ?command=load&lab="+lab[0]+"&customer_type="+ctype;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);    
}
function loading()
{
    clr();
    var lab=document.CustomerDet.labcode.value;
    lab=lab.split("--");
    document.CustomerDet.cid.focus();
    var url="../../../../../../WQS_Customer_MasterServ?command=load&lab="+lab[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipulate(req);
    }
    req.send(null);                                   
}

function isNumberKey(evt,item)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if(charCode > 31 && (charCode < 48 || charCode > 57))
     return false;
    return true;
}

function added()
{
    var res=validateSubmit();
    if(res!=false)
    {
        var lab=document.getElementById("labcode").value;
        lab=lab.split("--");
        var cid=document.getElementById("cid").value;
        var name=document.getElementById("name").value;
        var type=document.getElementById("type").value;       
        var address=document.getElementById("address").value;
        /*var addline=address.split("\n");
        var addval="";
        for(var i=0;i<addline.length;i++)
        {
            if(i==0)
                addval=addline[i];
            else
                addval=addval+"--"+addline[i];
        }*/
        var email_id=document.getElementById("email_id").value;
        document.CustomerDet.add.disabled=true;
        var url="../../../../../../WQS_Customer_MasterServ?command=add&lab="+lab[0]+"&cid="+cid+"&name="+name+"&type="+type+"&address="+address+"&email_id="+email_id;
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
    var res=validateSubmit();
    if(res!=false)
    {
        var lab=document.getElementById("labcode").value;
        lab=lab.split("--");
        var cid=document.getElementById("cid").value;
        var name=document.getElementById("name").value;
        var type=document.getElementById("type").value;
        var address=document.getElementById("address").value;
        var email_id=document.getElementById("email_id").value;
        document.CustomerDet.update.disabled=true;
        var url="../../../../../../WQS_Customer_MasterServ?command=upd&lab="+lab[0]+"&cid="+cid+"&name="+name+"&type="+type+"&address="+address+"&email_id="+email_id;
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
    var cid=document.getElementById("cid").value;
    var lab=document.getElementById("labcode").value;
    var type=document.getElementById("type").value;
    lab=lab.split("--");
    if(cid=="")
        alert("select a row to delete")
    else
    {
        var r=confirm("Are You Sure?")
        if(r==true)
        {
            document.CustomerDet.delet.disabled=true;
            var url="../../../../../../WQS_Customer_MasterServ?command=del&lab="+lab[0]+"&cid="+cid+"&type="+type;
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
    try
    {
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
function changepagesize()
{
        __pagination=document.CustomerDet.cmbpagination.value;
        totalblock=0;
        if(record1.length>0)
        {
            totalblock=parseInt(record1.length/__pagination);
            if(record1.length%__pagination!=0)
            {
                totalblock=totalblock+1;
            }
            var cmbpage=document.getElementById("cmbpage");
            try
            {
                cmbpage.innerHTML="";
            }
            catch(e)
            {
                cmbpage.innerText="";
            }
            for(i=1;i<=totalblock;i++)
            {
                var option=document.createElement("OPTION");
                option.text=i;
                option.value=i;
                try
                {
                    cmbpage.add(option);
                }
                catch(errorObject)
                {
                    cmbpage.add(option,null);
                }
            } 
        }
        loadPage(1);
   // }
}

function manipulate(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;              
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;   
               if(flag=="success")
               {
                       if(cmd=="add")
                       {
                              alert("record added");
                              document.CustomerDet.add.disabled=false;
                       }
                       else if(cmd=="upd")
                       {
                              alert("record updated");
                              document.CustomerDet.update.disabled=false;
                       }
                       else if(cmd=="del")
                       {
                              alert("record deleted");
                              document.CustomerDet.delet.disabled=false;
                       }
                       record1=new Array();
                       record2=new Array();
                       record3=new Array();
                       record4=new Array();
                       record5=new Array();
                       record6=new Array();
                       record7=new Array();
                       var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
                       document.CustomerDet.total.value=count;
                       if(count>0)
                       {
                           var display=response.getElementsByTagName("display");        
                           for(i=0;i<display.length;i++)
                           {                                                                   
                                    record5[i]=display[i].getElementsByTagName("lab_code")[0].firstChild.nodeValue;                        
                                    record1[i]=display[i].getElementsByTagName("cust_id")[0].firstChild.nodeValue;                        
                                    record2[i]=display[i].getElementsByTagName("cust_name")[0].firstChild.nodeValue;                                
                                    record3[i]=display[i].getElementsByTagName("cust_type")[0].firstChild.nodeValue;                                
                                    record6[i]=display[i].getElementsByTagName("address")[0].firstChild.nodeValue;
                                    record7[i]=display[i].getElementsByTagName("email_id")[0].firstChild.nodeValue;                                
                                    if(record7[i]=="-")
                                        record7[i]="";
                           }
                            totalblock=0;
                            if(record1.length>0)
                            {
                                totalblock=parseInt(record1.length/__pagination);
                                if(record1.length%__pagination!=0)
                                {
                                    totalblock=totalblock+1;
                                }
                                var cmbpage=document.getElementById("cmbpage");
                                try
                                { 
                                    cmbpage.innerHTML="";
                                }
                                catch(e){
                                     cmbpage.innerText="";
                                }
                                
                                for(i=1;i<=totalblock;i++)
                                {
                                    var option=document.createElement("OPTION");
                                    option.text=i;
                                    option.value=i;
                                    try
                                    {
                                    cmbpage.add(option);
                                    }
                                    catch(errorObject)
                                    {
                                    cmbpage.add(option,null);
                                    }
                                }  
                          
                                loadPage(1);
                            }
                       }                      
             }
             else if(flag=="failure")
             {
                    if(cmd=="add")
                    {
                        var ctype=document.getElementById("type").value;
                        if(ctype=="Twad"||ctype=="Twad Staff"||ctype=="Twad Student"||ctype=="Local Body")
                        {
                            alert("Duplicate id");
                        }
                        else
                            alert("Failed to insert ");
                        document.CustomerDet.add.disabled=false;
                    }
                    else if(cmd=="upd")
                    {
                        alert("Failed to update ");
                        document.CustomerDet.update.disabled=false;
                    }
                    else if(cmd=="del")
                    {
                        alert("Failed to delete ");
                        document.CustomerDet.delet.disabled=false;
                    }
                    else if(cmd=="duplicate")
                    {
                        alert("This Customer is already available");
                        document.CustomerDet.cid.value="";
                        document.CustomerDet.cid.focus(); 
                    }
             }
             else if(flag=="FoundData")
             {
                alert("Can not delete this record");
             }
          }
        }  
}
function changepage()
{
    var page=document.CustomerDet.cmbpage.value;
    loadPage(parseInt(page));

}
function loadPage(page)
{
            var i=0;
            var c=0;    
            var p=__pagination*(page-1);
            var sno=0;
            var tbody=document.getElementById("tb");
            try{tbody.innerHTML="";}
            catch(e) {tbody.innerText="";}
            document.CustomerDet.cmbpage.selectedIndex=page-1;
            for(i=p;i<record1.length && c<__pagination;i++)
            {
                        c++; sno++;
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR"); 
                        cell2=document.createElement("TD");
                        cell2.setAttribute('align','left'); 

             
                        try{cell2.innerHTML="";}
                        catch(e) {cell2.innerText="";}
                  
                        var anc=document.createElement("A");
                        var url="javascript:loadRecord('"+record1[i]+"','"+record2[i]+"','"+record3[i]+"','"+record6[i]+"','"+record7[i]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("Edit");
                        anc.appendChild(txtedit);
                        cell2.appendChild(anc);
                        mycurrent_row.appendChild(cell2);
                                                                          
                        var cell2=document.createElement("TD");
                        cell2.setAttribute('align','left');
                        if(record5[i]!="null")
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
                            
                        cell5=document.createElement("TD");
                        cell5.setAttribute('align','left');
                        if(record6[i]!="null")
                        {
                            var currentText=document.createTextNode(record6[i]);
                        }
                        else
                        {
                            var currentText=document.createTextNode('');
                        }
                        cell5.appendChild(currentText);
                        mycurrent_row.appendChild(cell5);
                        
                        cell7=document.createElement("TD");
                        cell7.setAttribute('align','left');                        
                        if(record7[i]!="null")
                        {
                            var currentText=document.createTextNode(record7[i]);
                        }
                        else
                        {
                            var currentText=document.createTextNode('');
                        }
                        cell7.appendChild(currentText);
                        mycurrent_row.appendChild(cell7);
                            
                        tbody.appendChild(mycurrent_row);
            }
            /*This Part Is Used To Move The Next Page Or The Previous Page In The Grid*/
    
            var cell=document.getElementById("divcmbpage");
            cell.style.display="block";
            var cell=document.getElementById("divpage");
            cell.style.display="block";
           // cell.innerText='/'+totalblock;
            try
            {cell.innerHTML='/'+totalblock;
            }
            catch(e){cell.innerText='/'+totalblock;
            }
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try
                {
                    cell.innerHTML="";
                }
                catch(e)
                {
                    cell.innerText="";
                }
                var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                var txtedit=document.createTextNode("<<Next>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                catch(e) {cell.innerText="";}
            }
            if(page>1)
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                try{cell.innerHTML="";}
                catch(e) {cell.innerText="";}
                var anc=document.createElement("A");
                var url="javascript:loadPage("+(page-1)+")";
                anc.href=url;
                var txtedit=document.createTextNode("<<Previous>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                try{cell.innerHTML="";}
                catch(e) {cell.innerText="";}
            }
            clr()
           // document.CustomerDet.cid.focus();  
}
function loadRecord(cid,name,type,address,email_id)
{
    document.CustomerDet.update.disabled=false;
    document.CustomerDet.delet.disabled=false;
    document.CustomerDet.add.disabled=true;
    document.CustomerDet.cid.disabled=true;
    if(type=="Twad Staff"||type=="Twad Student"||type=="Local Body")
    {
         while (cid.substr(0,1) == '0' && cid.length>1) 
         { 
            cid = cid.substr(1,9999);
         }
    }
    document.CustomerDet.cid.value=cid;
    document.CustomerDet.type.value=type;
    if(type=="Twad"||type=="Twad Staff"||type=="Twad Student")
    {
        document.getElementById("icon").style.visibility="visible";
        //document.CustomerDet.address.disabled=true;
        //document.CustomerDet.address.style.backgroundColor="rgb(214,214,214)";
        document.CustomerDet.cid.disabled=true;
        document.CustomerDet.cid.style.backgroundColor="white";
    }
    else
    {
        if(type=="Local Body")
            document.getElementById("icon").style.visibility="visible";
        else
            document.getElementById("icon").style.visibility="hidden";
        document.CustomerDet.name.disabled=false;
        //document.CustomerDet.address.disabled=false;
        //document.CustomerDet.address.style.backgroundColor="white";
        document.CustomerDet.cid.disabled=true;
        document.CustomerDet.cid.style.backgroundColor="rgb(214,214,214)";
    }
    document.CustomerDet.name.value=name;
    if(address==""||address=="null")
        address="";
    else
    {
        var addressval=address.split(",");
        var addval="";       
        for(var i=0;i<addressval.length;i++)
        {
            if(i==0)
                addval=addressval[i];
            else
            {
                if(addval.length>5)
                    addval=addval+","+"\n"+addressval[i];
                else
                    addval=addval+","+addressval[i];
            }
        }
    }     
    document.CustomerDet.address.value=addval;
    document.CustomerDet.email_id.value=email_id;
}
function clearAll()
{
    document.CustomerDet.cid.disabled=false;
    document.CustomerDet.cid.style.backgroundColor="white";
    document.CustomerDet.cid.focus();
    document.getElementById("icon").style.visibility="hidden";
    document.CustomerDet.total.value="";
    document.CustomerDet.cmbpagination.selectedIndex="0"
    document.CustomerDet.type.selectedIndex="";
    document.CustomerDet.type.focus();  
    clr();
}
function clr()
{
    if(document.CustomerDet.type.value=="Twad"||document.CustomerDet.type.value=="Twad Staff"||document.CustomerDet.type.value=="Twad Student")
    {
        document.CustomerDet.cid.disabled=false;
        document.CustomerDet.cid.style.backgroundColor="white";
        //document.CustomerDet.address.disabled=true;
        //document.CustomerDet.address.style.backgroundColor="rgb(214,214,214)";
    }
    else
    {
        document.CustomerDet.cid.disabled=true;
        document.CustomerDet.cid.style.backgroundColor="rgb(214,214,214)";
        //document.CustomerDet.address.disabled=false;
        //document.CustomerDet.address.style.backgroundColor="white";
    }
    document.CustomerDet.cid.value="";
    document.CustomerDet.name.value="";    
    document.CustomerDet.address.value="";
    document.CustomerDet.email_id.value="";
    document.CustomerDet.add.disabled=false;
    document.CustomerDet.update.disabled=true;
    document.CustomerDet.delet.disabled=true;
}

function checkdup()
{
    var lab=document.CustomerDet.labcode.value;
    lab=lab.split("--");
    var cid=document.CustomerDet.cid.value;
    var req=getTransport();
    var url="../../../../../../WQS_Customer_MasterServ?command=duplicate&lab="+lab[0]+"&cid="+cid;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manip(req);
    }
    req.send(null);
}

function checklength(evt,item)
{    
        var maxqty=document.CustomerDet.address.value.length;
        var text =100;
        var result = true;
        if(maxqty >= text)
        {
            result = false;	
        }
        if (window.event)
        {
            window.event.returnValue = result;
            return result;
        }
        return true;
}
function validmail()
{
        if(document.CustomerDet.email_id.value!= "")
	{
		var at=document.CustomerDet.email_id.value.indexOf("@");
		var att=document.CustomerDet.email_id.value.lastIndexOf(".");
		if (at<1||att-at<2) 
                {
			alert("Not a valid e-mail");
			document.CustomerDet.email_id.value="";
			document.CustomerDet.email_id.focus();
		
		}
            
	 }
}

function validatefun()
{
    if(document.getElementById("type").value==""||document.getElementById("type").value==0)
    {
        alert("select customer type");
        return false;
    }
    else
        return true;
}
function validatefun1()
{
    if(document.getElementById("cid").value==""||document.getElementById("cid").value.length==0)
    {
        alert("select customer id");
        return false;
    }
    else if(document.getElementById("name").value==""||document.getElementById("name").value.length==0)
    {
        alert("Enter Customer Name");
        return false;
    }
    else if(document.getElementById("address").value==""||document.getElementById("address").value.length==0)
    {
        alert("select customer address");
        return false;
    }
    else if(document.getElementById("address").value!=""||document.getElementById("address").value.length!=0)
    {
        var maxqty=document.CustomerDet.address.value.length;
        if(maxqty>100)
        {
            alert("address should not exceed 100 characters");
            return false;
        }
        else
            return true;
    }
    else
        return true;
}

