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

function servicepopup()
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
        
    winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/WQS_ResultEntry_popupJSP.jsp?command=validate","InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

function doParentEmp(val)
{
   document.ResultEntry.ino.value=val;
   changeInvoice();
}

function changeInvoice()
{
    document.getElementById("ctype").value="";
    document.getElementById("total").value="";
    document.getElementById("amt").value="";
    var tb=document.getElementById("tbody");
    var t=tb.rows.length   
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
    req=getTransport();
    var lab=document.getElementById("labcode").value;
    lab=lab.split("--");
    var invoice=document.getElementById("ino").value;
    var url="../../../../../../WQS_ResultEntry_ValidateServ?command=changeInvoice&lab="+lab[0]+"&invoice="+invoice;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        process_response(req);
    }
    req.send(null);
}

function process_response(req)
{
     if(req.readyState==4)
        {
            if(req.status==200)
            {
                var response=req.responseXML.getElementsByTagName("response")[0];
                var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
                if(command=="changeInvoice")
                {
                       var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="Success")
                       {
                                var invoice=document.getElementById("ino").value;
                                var tbody=document.getElementById("tbody");
                                var count=response.getElementsByTagName("row_count");
                                var total_samples=response.getElementsByTagName("total_samples")[0].firstChild.nodeValue;                                
                                var ctype=response.getElementsByTagName("ctype")[0].firstChild.nodeValue;
                                var amt=response.getElementsByTagName("amt")[0].firstChild.nodeValue;
                                document.getElementById("total").value=total_samples;
                                document.getElementById("ctype").value=ctype;
                                document.getElementById("amt").value=amt;
                                for(var i=0;i<count.length;i++)
                                {
                                        var sample_number=count[i].getElementsByTagName("sample")[0].firstChild.nodeValue;
                                        var test_purpose=count[i].getElementsByTagName("test_purpose")[0].firstChild.nodeValue;
                                        var test_purpose_id=count[i].getElementsByTagName("test_purpose_id")[0].firstChild.nodeValue;
                                        var receiptdate=count[i].getElementsByTagName("receiptdate")[0].firstChild.nodeValue;
                                        var district_name=count[i].getElementsByTagName("district")[0].firstChild.nodeValue;
                                        var location=count[i].getElementsByTagName("location")[0].firstChild.nodeValue;
                                        var tr=document.createElement("tr");
                                        tr.id=i;
                                        
                                        
                                        var td3=document.createElement("td");
                                        var textnode3=document.createTextNode(sample_number);
                                        td3.appendChild(textnode3);
                                        tr.appendChild(td3);
                                         
                                        var td7=document.createElement("td");
                                        var textnode7=document.createTextNode(test_purpose);
                                        td7.appendChild(textnode7);
                                        tr.appendChild(td7);
                                      
                                        var td4=document.createElement("td");
                                        var textnode4=document.createTextNode(receiptdate);
                                        td4.appendChild(textnode4);
                                        tr.appendChild(td4);
                                        
                                        var td5=document.createElement("td");
                                        var textnode5=document.createTextNode(district_name);
                                        td5.appendChild(textnode5);
                                        tr.appendChild(td5);
                                        
                                        var td16=document.createElement("td");
                                        var textnode16=document.createTextNode(location);
                                        td16.appendChild(textnode16);
                                        tr.appendChild(td16);
                                        
                                        var td1=document.createElement("td");
                                        var anch=document.createElement("A");
                                        var textnode1=document.createTextNode("View");
                                        var url="javascript:ViewRecord('"+invoice+"','"+sample_number+"','"+test_purpose_id+"')";
                                        anch.href=url;
                                        anch.appendChild(textnode1);
                                        td1.appendChild(anch);
                                        tr.appendChild(td1);
                                        tbody.appendChild(tr);
                                }
                        }
                        else
                        {
                            alert("Invalid Invoice Number");
                            document.getElementById("ino").value="";
                            document.getElementById("ino").focus();
                        }
                    }
                    else if(command=="Validate")
                    {
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="success")
                        {
                            alert("Record Validated Successfully");
                            clearAll();
                        }
                        else 
                            alert(response.getElementsByTagName("exception")[0].firstChild.nodeValue);
                    }
            }
    }
}

function ViewRecord(ino,sno,purpose_id)
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
        
    winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Directory1/jsps/WQS_ResultEntry_VewJSP.jsp?invoice="+ino+"&sample="+sno+"&purpose_id="+purpose_id,"InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

function validate_record()
{
     var lab=document.getElementById("labcode").value;
     lab=lab.split("--");
     var ino=document.getElementById("ino").value;
     var len=document.getElementById("tbody");
     var record=new Array();
     for(var i=0;i<len.rows.length;i++)
     {
        rid=i;
        var r=document.getElementById(rid);
        var rcells=r.cells;
        record[i]=rcells.item(0).firstChild.nodeValue;
     }
     var url="../../../../../../WQS_ResultEntry_ValidateServ?command=Validate&lab="+lab[0]+"&invoice_no="+ino+"&sample_no="+record;
     req.open("GET",url,true);
     req.onreadystatechange=function()
     {
        process_response(req);
     }
     if(window.XMLHttpRequest)
        req.send(null);
     else req.send();
}

function clearAll()
{
   
    document.getElementById("ino").value="";
    document.getElementById("ctype").value="";
    document.getElementById("total").value="";
    document.getElementById("amt").value="";
    var tb=document.getElementById("tbody");
    var t=tb.rows.length   
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
}