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
       return;
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../../org/WQS/WQS1/WQTesting/Reports/jsps/WQS_SampleResult_Customer_popupJSP.jsp","InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(ino)
{
    document.SampleResult.ino.value=ino;
    changeIno();
}
function clearFun()
{
    document.SampleResult.ino.value="";
    clearAll();
}
function clearAll()
{
    document.SampleResult.cid.value="";
    document.SampleResult.cname.value="";
    document.SampleResult.ctype.value="";
    document.SampleResult.off_rno.value="";
    document.SampleResult.det.value="";
    document.SampleResult.rno.value="";
    document.SampleResult.res.value="";
    var test_purpose=document.getElementById("test_purpose");
    var child=test_purpose.childNodes;
    for(var i=child.length-1;i>1;i--)
    {
        test_purpose.removeChild(child[i]);
    }    
    var iframe=document.getElementById("diviframeregion");
    iframe.innerHTML="";
    iframe.style.visibility="hidden";
}
function changeIno()
{
    clearAll();
    var lab=document.SampleResult.lab.value;
    lab=lab.split("--");
    var ino=document.SampleResult.ino.value;
    var url="../../../../../../WQS_SampleResult_CustomerRep?command=changeIno&lab="+lab[0]+"&invoice_no="+ino;
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        manipid(req);
    }
    if(window.XMLHttpRequest)
                req.send(null);
    else req.send();
}
function manipid(req)
{
    if(req.readyState==4)
    {
          if(req.status==200)
          {
               var response=req.responseXML.getElementsByTagName("response")[0];
               var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;
               var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
               if(flag=='Success')
               {                
                    var cust_id=response.getElementsByTagName("cid")[0].firstChild.nodeValue;
                    var cust_name=response.getElementsByTagName("cname")[0].firstChild.nodeValue;
                    var ctype=response.getElementsByTagName("ctype")[0].firstChild.nodeValue;
                    var rno=response.getElementsByTagName("rno")[0].firstChild.nodeValue
                    var off_rno=response.getElementsByTagName("off_rno")[0].firstChild.nodeValue
                    var inv_det=response.getElementsByTagName("inv_det")[0].firstChild.nodeValue
                    var final_result=response.getElementsByTagName("final_result")[0].firstChild.nodeValue
                    var test_purpose_id=response.getElementsByTagName("test_purpose_id")[0].firstChild.nodeValue;
                    var test_purpose=response.getElementsByTagName("test_purpose")[0].firstChild.nodeValue;
                    document.SampleResult.cid.value=cust_id;
                    document.SampleResult.cname.value=cust_name;
                    document.SampleResult.ctype.value=ctype;
                    document.SampleResult.rno.value=rno;
                    document.SampleResult.off_rno.value=off_rno;
                    document.SampleResult.det.value=inv_det;
                    var tp=document.getElementById("test_purpose");
                    var purpose_id=test_purpose_id.split(",");
                    var purpose=test_purpose.split(",");
                    for(var i=0;i<purpose.length;i++)
                    {
                        var opt=document.createElement("option");
                        opt.setAttribute("value",purpose_id[i]+"--"+purpose[i]);
                        var text=document.createTextNode(purpose[i]);
                        opt.appendChild(text);
                        tp.appendChild(opt);
                    }
                    document.getElementById("test_purpose").selectedIndex=0;
                    if(final_result=="-")
                        final_result="";
                    document.SampleResult.res.value=final_result;
               }
               else
               {
                    alert("Select Correct Invoice Number");
                    document.SampleResult.ino.value="";
                }
            }
     }
}
function load_sample()
{
     var iframe=document.getElementById("diviframeregion");
     iframe.innerHTML="";
     iframe.style.visibility="hidden";
}
function getSample()
{       
        var lab=document.SampleResult.lab.value;
        var lab_code=lab.split("--");
        var ino=document.SampleResult.ino.value;
        var test_purpose=document.SampleResult.test_purpose.value;
        test_purpose=test_purpose.split("--");
        var url="../../../../../../WQS_SampleResult_CustomerRep?command=sample&lab_code="+lab_code[0]+"&ino="+ino+"&test_purpose="+test_purpose[0];
        document.SampleResult.sno.focus();
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                    iframe.innerHTML=req.responseText;
                  }
            }
        }
        req.send(null);
}

function GenerateRep()
{
        var res=checkid();
        if(res==true)
        {
                var lab=document.SampleResult.lab.value;
                var lab_code=lab.split("--");
                var ino=document.SampleResult.ino.value;
                var test_purpose=document.SampleResult.test_purpose.value;
                test_purpose=test_purpose.split("--");
                var cid=document.SampleResult.cid.value;
                var ctype=document.SampleResult.ctype.value;
                var sno="";
                if(document.SampleResult.chkelement.length>1)
                {
                    for(i=0;i<document.SampleResult.chkelement.length;i++)
                    {
                            if(document.SampleResult.chkelement[i].checked==true)
                                    sno= sno+document.SampleResult.chkelement[i].value +",";
                            
                    }
                    sno=sno.substring(0,sno.length-1);
                }
                else 
                    sno=document.SampleResult.chkelement.value;
                var orno=document.SampleResult.off_rno.value;
                var details=document.SampleResult.det.value;
                var rno=document.SampleResult.rno.value;
                var final_result=document.SampleResult.res.value;
                final_result=final_result.replace("%"," Percent ");
                res=document.SampleResult.res.value;
                var result=res.split("\n");
                for(var i=0;i<result.length;i++)
                {
                    if(i==0)
                        res=result[i];
                    else
                        res=res+"--"+result[i];
                }
                res=res.replace("%"," Percent ");
                var psize="";
                if(document.SampleResult.rsize[0].checked==true)
                    psize="A3";
                else
                    psize="A4";
                var pre_printed="";
                if(document.SampleResult.printed)
                {
                    if(document.SampleResult.printed[0].checked==true)
                        pre_printed="YES";
                    else
                        pre_printed="NO";
                }
                else
                    pre_printed="NO";
                var url="../../../../../../WQS_SampleResult_CustomerRep?lab_code="+lab_code[0]+"&invoice_num="+ino+"&test_purpose="+test_purpose[0]+"&cid="+cid+"&ctype="+ctype+"&sno="+sno+"&off_rno="+orno+"&det="+details+"&rno="+rno+"&result="+res+"&psize="+psize+"&final_result="+final_result+"&pre_printed="+pre_printed;
                document.SampleResult.action=url
                document.SampleResult.method="post";
                document.SampleResult.submit();
        }
}
function checkid()
{
    var count=0;
    if(document.SampleResult.chkelement)
    {
        for(i=0;i<document.SampleResult.chkelement.length;i++)
        {
            if(document.SampleResult.chkelement[i].checked==true)
                count=count+1;
        }
        if(count>5)
        {
            alert("Only 5 Samples should be selected for printing");
            return false;
        }
        else
         return true;
    }
    else
    {
        alert("Select Sample Number");
        return false;
    }
}

function restrictLength()
{
    var maxqty=document.SampleResult.res.value.length;
    var text =700;
    var result = true;
    if(maxqty >= text)
    {
        result = false;	
    }  
    return result;
}

function addResult()
{
    var lab=document.SampleResult.lab.value;
    var lab_code=lab.split("--");
    var ino=document.SampleResult.ino.value;
    var sno="";var count=0;
    if(document.SampleResult.chkelement.length>1)
    {
        for(i=0;i<document.SampleResult.chkelement.length;i++)
        {
                if(document.SampleResult.chkelement[i].checked==true)
                {
                        sno= sno+document.SampleResult.chkelement[i].value +",";
                        count++
                }
                
        }
    }
    else
    {
        if(document.SampleResult.chkelement.checked==true)
        {            
            sno=document.SampleResult.chkelement.value;
            count++;
        }
        else
        {
            document.SampleResult.res.value="";
            return false;
        }
    }
    if(count<=5)
    {
            if(count==0)
            {
                alert("Select Sample Number");
                return false;
            }
            else
            {
                    var req=getTransport();
                    var url="../../../../../../WQS_SampleResult_CustomerRep?command=addResult&lab_code="+lab_code[0]+"&ino="+ino+"&sample_number="+sno;
                    req.open("GET",url,true);
                    req.onreadystatechange=function()
                    {
                        if(req.readyState==4)
                        { 
                              if(req.status==200)
                              {  
                                    var baseresponse=req.responseXML.getElementsByTagName("response")[0];
                                    var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                                    if(flag=="success")
                                    {
                                        document.SampleResult.res.value="";
                                        var result=baseresponse.getElementsByTagName("result")[0].firstChild.nodeValue;
                                        if(result=="-"||result==" ")
                                            result=""
                                        document.SampleResult.res.value=result;
                                    }
                                    else
                                    {
                                        var message=baseresponse.getElementsByTagName("message")[0].firstChild.nodeValue;
                                    }
                              }
                        }
                    }
                    req.send(null);
            }
    }
    else
    {
        alert("Only 5 Samples should be selected for printing");
        return false;
    }
    
}