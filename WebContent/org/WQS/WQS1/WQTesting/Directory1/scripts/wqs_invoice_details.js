var __pagination=5;
var totalblock=0;
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
function checkcustomer()
{
    if(document.invoice.ctype.value==0||document.invoice.ctype.value=="")
    {
        alert("select customer type");
        return false;
    }
    else
        return true;
}
function servicepopup()
{
    var res=checkcustomer();
    if(res==true)
    {
            var winemp;
            var my_window;
            var wininterval;
            if (winemp && winemp.open && !winemp.closed) 
            {
               winemp.resizeTo(500,600);
               winemp.moveTo(200,200); 
               winemp.focus();
               return ;
            }
            else
            {
                winemp=null
            }
            var ctype=document.invoice.ctype.value;   
            winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/wqs_invoice_details_popup.jsp?ctype="+ctype,"InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
            winemp.moveTo(250,250);  
            winemp.focus();
    }
}
function doParentEmp(cid)
{
   document.invoice.cid.value=cid;
   changeId();
}
function changecustomer()
{
    document.invoice.cid.value="";
    document.invoice.cname.value="";
    
}
function changepagesize()
{
    __pagination=document.invoice.cmbpagination.value;
    //var v=document.getElementsByName("sel");
   // if(v && items5)
   // {
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
        loadRecordVal(1);
   // }
}

function loadRecord()
{
    clearAll();
    var lab=document.invoice.lab.value;
    lab=lab.split("--");
    var url="../../../../../../wqs_invoice_details?command=loadRecord&lcode="+lab[0];
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        processResponse(req);
    }
    req.send(null);
}

function processResponse(req)
{   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var response=req.responseXML.getElementsByTagName("response")[0]; 
              var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue; 
              var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;              
              record1=new Array();record2=new Array();
              record3=new Array();record4=new Array();
              record5=new Array();record6=new Array();
              record7=new Array();record8=new Array();
              record9=new Array();record10=new Array();
              record11=new Array();record12=new Array();
              record13=new Array();record14=new Array();
              record15=new Array();record16=new Array();
              record17=new Array();record18=new Array();record19=new Array();
              if(flag=="Success")
              {
                             
                                var count=response.getElementsByTagName("count")[0].firstChild.nodeValue; 
                                document.invoice.total.value=count;
                                var display=response.getElementsByTagName("display");                                                               
                                for(i=0;i<display.length;i++)
                                {
                                    record1[i]=display[i].getElementsByTagName("lcode")[0].firstChild.nodeValue;
                                    record2[i]=display[i].getElementsByTagName("ino")[0].firstChild.nodeValue;                                
                                    record3[i]=display[i].getElementsByTagName("ctype")[0].firstChild.nodeValue;
                                    record4[i]=display[i].getElementsByTagName("cid")[0].firstChild.nodeValue;                                
                                    record5[i]=display[i].getElementsByTagName("date")[0].firstChild.nodeValue;
                                    record6[i]=display[i].getElementsByTagName("amt")[0].firstChild.nodeValue;                                
                                    record7[i]=display[i].getElementsByTagName("mode")[0].firstChild.nodeValue;
                                    if(record7[i]=="null")
                                        record7[i]="";
                                    record8[i]=display[i].getElementsByTagName("rno")[0].firstChild.nodeValue;
                                    if(record8[i]=="null")
                                        record8[i]="";
                                    record9[i]=display[i].getElementsByTagName("ddate")[0].firstChild.nodeValue;
                                    record11[i]=display[i].getElementsByTagName("samples")[0].firstChild.nodeValue;
                                    record12[i]=display[i].getElementsByTagName("rem")[0].firstChild.nodeValue;
                                    if(record12[i]=="null")
                                        record12[i]="";
                                    record13[i]=display[i].getElementsByTagName("cnum")[0].firstChild.nodeValue;
                                    record14[i]=display[i].getElementsByTagName("chqdate")[0].firstChild.nodeValue;                                
                                    record15[i]=display[i].getElementsByTagName("bank")[0].firstChild.nodeValue;
                                    record16[i]=display[i].getElementsByTagName("branch")[0].firstChild.nodeValue;
                                    record17[i]=display[i].getElementsByTagName("name")[0].firstChild.nodeValue;
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
                              
                                    loadRecordVal(1);
                                }
               }
               else
               {
            	   document.invoice.total.value=0;
            	   var tbody=document.getElementById("tblList");
            	   try{tbody.innerHTML="";
            	   }catch(e) {tbody.innerText="";}
               }
            }
        }
}
function changepage()
{
    var page=document.invoice.cmbpage.value;
    loadRecordVal(parseInt(page));

}
function loadRecordVal(page)
{
    var i=0;
    var c=0;    
    var p=__pagination*(page-1);
    var sno=0;
    var tbody=document.getElementById("tblList");
    try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
    document.invoice.cmbpage.selectedIndex=page-1;
    for(i=p;i<record1.length && c<__pagination;i++)
    {
            c++;
            sno++;
            var mycurrent_row=document.createElement("TR"); 
            mycurrent_row.id=sno;
            cell2=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadValuesFromTable('"+record1[i]+"','"+record2[i]+"','"+record3[i]+"','"+record4[i]+"','"+record5[i]+"','"+record6[i]+"','"+record7[i]+"','"+record8[i]+"','"+record9[i]+"','"+record11[i]+"','"+record12[i]+"','"+record13[i]+"','"+record14[i]+"','"+record15[i]+"','"+record16[i]+"','"+record17[i]+"')";
            anc.href=url;
            var txtedit=document.createTextNode("Edit");
            anc.appendChild(txtedit);
            cell2.appendChild(anc);
            mycurrent_row.appendChild(cell2);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record1[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record2[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record3[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record4[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record5[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);

            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record6[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record7[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record8[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record9[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record11[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            cell1=document.createElement("TD");
            var currentText=document.createTextNode(record12[i]);
            cell1.appendChild(currentText);
            mycurrent_row.appendChild(cell1);
            
            tbody.appendChild(mycurrent_row);
            
    }
    /*This Part Is Used To Move The Next Page Or The Previous Page In The Grid*/
    
    var cell=document.getElementById("divcmbpage");
    cell.style.display="block";
    var cell=document.getElementById("divpage");
    cell.style.display="block";
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
        var url="javascript:loadRecordVal("+(page+1)+")";
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
        var url="javascript:loadRecordVal("+(page-1)+")";
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

}

function loadValuesFromTable(record1,record2,record3,record4,record5,record6,record7,record8,record9,record11,record12,record13,record14,record15,record16,record17)
{      
        var req=getTransport();
        var url="../../../../../../wqs_invoice_details?command=checkAvail&lcode="+record1+"&invoice="+record2;
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            if(req.readyState==4)
            {
              if(req.status==200)
              {    
                var response=req.responseXML.getElementsByTagName("response")[0]; 
                var avail=response.getElementsByTagName("avail")[0].firstChild.nodeValue; 
                if(avail=="yes")
                {
                    document.getElementById("ctype").disabled=true;
                    document.getElementById("cid").disabled=true;
                    document.getElementById("imgdiv").style.visibility="hidden";
                    var entry=req.responseXML.getElementsByTagName("entry")[0].firstChild.nodeValue; 
                    if(entry=="yes")
                    {
                        var unfreezed=req.responseXML.getElementsByTagName("unfreezed")[0].firstChild.nodeValue; 
                        if(unfreezed=="yes")
                        {
                           // document.getElementById("stype").disabled=true;
                            document.getElementById("tot_samples").disabled=false;
                        }
                        else
                        {
                           // document.getElementById("stype").disabled=true;
                            document.getElementById("tot_samples").disabled=true;
                        }                        
                    }
                    else
                    {
                        //document.getElementById("stype").disabled=false;
                        document.getElementById("tot_samples").disabled=false;
                    }
                }
                else
                {
                     document.getElementById("ctype").disabled=false;
                     document.getElementById("cid").disabled=false;
                     document.getElementById("imgdiv").style.visibility="visible";
                    // document.getElementById("stype").disabled=false;
                     document.getElementById("tot_samples").disabled=false;
                }
                document.getElementById("in1").style.display="block"
                document.getElementById("in2").style.display="block"
                document.getElementById("ino").value=record2;
                document.getElementById("ctype").value=record3;
                document.getElementById("cid").value=record4;
                document.getElementById("cname").value=record17;
                document.getElementById("invoice_date").value=record5;
                document.getElementById("amt").value=record6;
                document.getElementById("paymode").value=record7;
                if(record7=="Cash")
                {
                    var type=document.getElementById("chequedt");
                    type.style.display="none";
                }
                else if(record7=="DD" || record7=="Cheque" || record7=="TCA")
                {
                    var type=document.getElementById("chequedt");
                    type.style.display="block";
                    if(record13==""||record13=="null")
                    record13="";
                    document.getElementById("che_num").value=record13;
                    document.getElementById("che_date").value=record14;
                    if(record15==""||record15=="null")
                    record15="";
                    if(record16==""||record16=="null")
                    record16="";
                    document.getElementById("bank_name").value=record15;
                    document.getElementById("branch").value=record16;
                }
                document.getElementById("rno").value=record8;
                if(record9=="-")
                    record9="";
                document.getElementById("due_date").value=record9;
                //document.getElementById("stype").value=record10;
                document.getElementById("tot_samples").value=record11;
                document.getElementById("rem").value=record12;   
                document.invoice.add.disabled=true;
                document.invoice.update.disabled=false;
                document.invoice.del.disabled=false;
              }
            }
        }
        req.send(null);
}

function clearAll()
{
        document.getElementById("ino").value=""
        document.getElementById("ctype").selectedIndex="";
        document.getElementById("cid").value="";
        document.getElementById("cname").value="";
        document.getElementById("invoice_date").value="";
        document.getElementById("amt").value="";
        document.getElementById("paymode").selectedIndex="";
        var type=document.getElementById("chequedt");
        type.style.display="none";
        document.getElementById("che_num").value="";
        document.getElementById("che_date").value="";
        document.getElementById("bank_name").value="";
        document.getElementById("branch").value="";
        
        document.getElementById("rno").value="";
        document.getElementById("due_date").value="";
        document.getElementById("tot_samples").value="";
        document.getElementById("rem").value="";
        document.invoice.add.disabled=false;
        document.invoice.update.disabled=true;
        document.invoice.del.disabled=true;
        document.getElementById("in1").style.display="none";
        document.getElementById("in2").style.display="none";
        document.getElementById("ctype").disabled=false;
        document.getElementById("cid").disabled=false;
        document.getElementById("tot_samples").disabled=false;
        document.getElementById("imgdiv").style.visibility="visible";
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
function changeId()
{
     var lcode  = document.getElementById("lab").value;
     lcode=lcode.split("--");
     var type  = document.getElementById("ctype").value;
     var cid  = document.getElementById("cid").value;
     var req=getTransport();
     var url="../../../../../../wqs_invoice_details?command=changeId&lcode="+lcode[0]+"&type="+type+"&cid="+cid;
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
                manipulate(req);
     }
     req.send(null);
}
function chgBank()
{
        if(document.invoice.paymode.value=="Cash")
        {
            var type=document.getElementById("chequedt");
            type.style.display="none";
        }
        else if(document.invoice.paymode.value=="DD" || document.invoice.paymode.value=="Cheque" || document.invoice.paymode.value=="TCA")
        {
            var type=document.getElementById("chequedt");
            type.style.display="block";
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
               if(cmd=="changeId")
               {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="Success")
                    {
                        var name=response.getElementsByTagName("cname")[0].firstChild.nodeValue;                
                        document.getElementById("cname").value=name;
                    }
                    else
                   {
                        alert("Invalid Customer Id");
                        document.getElementById("cid").value="";
                        document.getElementById("cid").focus();
                   }
               }
               else if(cmd=="Add")
               {
                    var invoice=response.getElementsByTagName("invoice")[0].firstChild.nodeValue;
                    if(invoice=="available")
                    {
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="Success")
                        {
                           var invoice_no=response.getElementsByTagName("invoice_no")[0].firstChild.nodeValue;       
                           alert("Invoice Number "+invoice_no+" Created Successfully");
                           clearAll();
                           loadRecord();
                        }
                        else                        
                           System.out.println("Failed to insert");
                    }
                    else
                    {
                        alert("Invoice Number Initialization not found for this Lab");
                    }
               }
               else if(cmd=="Update")
               {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="Success")
                    {
                       alert("Updated Successfully");
                       clearAll();
                       loadRecord();
                    }
                    else
                    {
                       alert("Can not update this record");
                       document.invoice.add.disabled=false;
                       document.invoice.update.disabled=true;
                       document.invoice.del.disabled=true;
                    }
               }
               else if(cmd=="Delete")
               {
                    var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="Success")
                    {
                       alert("Deleted Successfully");
                       clearAll();
                       loadRecord();
                    }
                    else 
                    {
                        alert("Failed to delete");
                    }
                   
               }
          }
        }
}
function callServer(command)
{
    var lab  = document.getElementById("lab").value;
    lab=lab.split("--");
    var ino  = document.getElementById("ino").value;
    var ctype  = document.getElementById("ctype").value;
    var cid  = document.getElementById("cid").value;
    var cname  = document.getElementById("cname").value;
    var invoice_date  = document.getElementById("invoice_date").value;
    var amt  = document.getElementById("amt").value;
    var paymode  = document.getElementById("paymode").value;
    var che_num  = document.getElementById("che_num").value;
    var che_date  = document.getElementById("che_date").value;
    var bank_name  = document.getElementById("bank_name").value;
    var branch  = document.getElementById("branch").value;
    var rno  = document.getElementById("rno").value;
    var due_date  = document.getElementById("due_date").value;
    var tot_samples  = document.getElementById("tot_samples").value;
    var rem  = document.getElementById("rem").value;
    if(command=="Add")
    {
        var validation=nullCheck();
        if(validation==true)
        {
            var url = "../../../../../../wqs_invoice_details?command=Add&lcode="+lab[0]+"&type="+ctype+"&cid="+cid+"&invoice_date="+invoice_date+"&amt="+amt+"&paymode="+paymode+"&che_num="+che_num+"&che_date="+che_date+"&bank_name="+bank_name+"&branch="+branch+"&rno="+rno+"&due_date="+due_date+"&tot_samples="+tot_samples+"&rem="+rem;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               manipulate(req);
            }   
            req.send(null);
        }
    }
    else if(command=="Update")
    {
        var validation=nullCheck();
        if(validation==true)
        {
            var url = "../../../../../../wqs_invoice_details?command=Update&lcode="+lab[0]+"&ino="+ino+"&type="+ctype+"&cid="+cid+"&invoice_date="+invoice_date+"&amt="+amt+"&paymode="+paymode+"&che_num="+che_num+"&che_date="+che_date+"&bank_name="+bank_name+"&branch="+branch+"&rno="+rno+"&due_date="+due_date+"&tot_samples="+tot_samples+"&rem="+rem;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               manipulate(req);
            }   
            req.send(null);
        }
    }
    else if(command=="Delete")
    {
        var url = "../../../../../../wqs_invoice_details?command=checkDelete&lcode="+lab[0]+"&ino="+ino;
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           if(req.readyState==4)
            {
              if(req.status==200)
              {
                        var response=req.responseXML.getElementsByTagName("response")[0];
                        var flag=req.responseXML.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="NotPossible")
                        {
                            alert("Can not Delete this record");
                        }
                        else
                        {
                            var val="";
                            if(flag=="Possible")
                                val=true;
                            else if(flag=="NextRecordAvailble")
                            {
                                val=confirm("Can not create same invoice number again, Are You Sure?")
                            }
                            if(val==true)
                            {
                                var url = "../../../../../../wqs_invoice_details?command=Delete&lcode="+lab[0]+"&ino="+ino;
                                var req1=getTransport();
                                req1.open("GET",url,true); 
                                req1.onreadystatechange=function()
                                {
                                   manipulate(req1);
                                }   
                                req1.send(null);
                            }
                        }
              }
            }
        }   
        req.send(null);               
    }
}

function checklength(evt,item)
{
    var maxqty=document.invoice.rem.value.length;
    var text =100;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}
function checkId()
{
    if(document.getElementById("ctype").value==""||document.getElementById("ctype").selectedIndex==0)
    {
        alert("Enter Customer Type");
        document.getElementById("ctype").focus();
        return false;
    }
    else 
        true;
}
function nullCheck()
{
    if(document.getElementById("ctype").value==""||document.getElementById("ctype").selectedIndex==0)
    {
        alert("Enter Customer Type");
        document.getElementById("ctype").focus();
        return false;
    }
    else if(document.getElementById("cid").value==""||document.getElementById("cid").value.length==0)
    {
        alert("Enter Customer Id");
        document.getElementById("cid").focus();
        return false;
    }
    else if(document.getElementById("cname").value==""||document.getElementById("cname").value.length==0)
    {
        alert("Enter Customer Name");
        document.getElementById("cname").focus();
        return false;
    }
    else if(document.getElementById("invoice_date").value==""||document.getElementById("invoice_date").value.length==0)
    {
        alert("Enter Invoice Date");
        document.getElementById("invoice_date").focus();
        return false;
    }
    else if(document.getElementById("amt").value==""||document.getElementById("amt").value.length==0)
    {
        alert("Enter Invoice Amount");
        document.getElementById("amt").focus();
        return false;
    }
    else if(document.getElementById("due_date").value==""||document.getElementById("due_date").value.length==0)
    {
        alert("Enter due_date");
        document.getElementById("due_date").focus();
        return false;
    }
    else if(document.getElementById("tot_samples").value==""||document.getElementById("tot_samples").value.length==0)
    {
        alert("Enter Total Samples");
        document.getElementById("tot_samples").focus();
        return false;
    }
    else
        return true;
}

function checkDueDate()
{
    if((document.invoice.invoice_date.value!=""||document.invoice.invoice_date.value.length==0) && (document.invoice.due_date.value!==""||document.invoice.due_date.value.length==0))
    {
        var fromdt=document.invoice.invoice_date.value;
        var todt=document.invoice.due_date.value;
        
        var frm=fromdt.split('/');
        var to=todt.split('/');
        
        var fday=frm[0];
        var fmon=frm[1];
        var fyear=frm[2];
        
        var tday=to[0];
        var tmon=to[1];
        var tyear=to[2];
        
       if(fyear>tyear)
        {
            alert('Check Due Date');
            document.invoice.due_date.value="";
            document.invoice.due_date.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Check Due Date');
                    document.invoice.due_date.value="";
                    document.invoice.due_date.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Check Due Date');
                             document.invoice.due_date.value="";
                             document.invoice.due_date.focus();
                             return false;
                        }
                        
                }
        }
        return true; 
    }
}