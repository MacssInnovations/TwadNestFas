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
    if(!req && typeof XMLHttpRequest!='undefined')
        {
        req=new XMLHttpRequest();
        }
   return req;
}   

function changeLabState()
{
    document.labwise.lab.value="";
    if(document.labwise.allLab.checked==true)
        document.labwise.lab.disabled=true;
    else
        document.labwise.lab.disabled=false;
}
function gen_report()
{
    var val=checkValid();
    if(val==true)
    {
        var labcode="";
        if(document.labwise.allLab.checked==true)
            labcode="All";
        else
        {
             var lab=document.labwise.lab.value;
             lab=lab.split("--");
             labcode=lab[0];
        }
        var fdate=document.labwise.fdate.value;
        var tdate=document.labwise.tdate.value;
        var url="../../../../../../WQS_Labwise_SampleReport_Serv?&fdate="+fdate+"&tdate="+tdate+"&lab="+labcode;
        document.labwise.action=url
        document.labwise.method="post";
        document.labwise.submit();
    }
}

function checkValid()
{
    if(document.labwise.fdate.value==""||document.labwise.fdate.value.length==0||document.labwise.tdate.value==""||document.labwise.tdate.value.length==0)
    {
        alert("Select Date");
        return false;
    }
    else if(document.labwise.allLab.checked==false && document.labwise.lab.value=="")
    {
        alert("Select Lab");
        return false;
    }
    else 
        return true;
}