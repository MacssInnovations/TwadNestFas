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
    if(!req && typeof XMLHttpRequest != undefined)
        {
        req=new XMLHttpRequest();
        }
   return req;
   } 
function getFunction(param)
{
   
    clearfun1();
    clearfun();
    var id=param;
    var req=getTransport();
    var url="../../../../../HRM_Leave_JoiningDutyServ?command=changeid&cmdid="+id;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        loadResponse(req);   
    }  
    req.send(null);    
}

function loadResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
         {
           var baseResponse=req.responseXML.getElementsByTagName("response1")[0];
           var cmd=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
           if(cmd=="changeid")
           {
              Details(baseResponse);
           }
           else if(cmd=="changerid")
           {
              leaveDetails(baseResponse);
           } 
           else if(cmd=="radio")
           {
              radioDetails(baseResponse);
           }
           else
           {
              insertDeatils(baseResponse);
           }
         }
    }
} 
function Details(baseResponse)
{
    var tcode=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
    if(tcode=="Success")
    {
        var name=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
        var designation=baseResponse.getElementsByTagName("designation")[0].firstChild.nodeValue;
        document.joining.ename.value=name;
        document.joining.design.value=designation;
    }
    else
    {
        alert("Unable to Load");
    }
}

function getRequest(param)
{
    clearfun1();
    var rid=param;
    var id=document.joining.eid.value;
    var req=getTransport();
    var url="../../../../../HRM_Leave_JoiningDutyServ?command=changerid&cmdrid="+rid+"&cmdid="+id;
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
         loadResponse(req);   
    }  
    req.send(null);   
}
function leaveDetails(baseResponse)
{
  var tcode=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
  if(tcode=="Success")
  {
    var tdesc=baseResponse.getElementsByTagName("tdesc")[0].firstChild.nodeValue;
    var bleave=baseResponse.getElementsByTagName("leave_balance")[0].firstChild.nodeValue;
    var fdate=baseResponse.getElementsByTagName("fdate")[0].firstChild.nodeValue;
    var tdate=baseResponse.getElementsByTagName("tdate")[0].firstChild.nodeValue;
    var pday=baseResponse.getElementsByTagName("pday")[0].firstChild.nodeValue;
    var sday=baseResponse.getElementsByTagName("sday")[0].firstChild.nodeValue;
    var tleave=baseResponse.getElementsByTagName("tleave")[0].firstChild.nodeValue;
    var address=baseResponse.getElementsByTagName("address")[0].firstChild.nodeValue;
    var rdate=baseResponse.getElementsByTagName("rdate")[0].firstChild.nodeValue;
    document.joining.balanceEL.value=bleave;
    document.joining.periodFrom.value=fdate;
    document.joining.periodTo.value=tdate;
    document.joining.ph.value=pday;
    document.joining.sh.value=sday;
    document.joining.leaveDays.value=tleave;
    document.joining.address.value=address;
    document.joining.dateApplied.value=rdate;
    if(tdesc!="ML") 
    {
       var c=document.getElementById("DateFrom1");
       var d=document.getElementById("DateFrom2");
       var e=document.getElementById("optDateFrom1");
       var f=document.getElementById("optDateFrom2");
       c.disabled=true;
       d.disabled=true;
       e.disabled=true;
       f.disabled=true;
    }
    else
    {
       var c=document.getElementById("DateFrom1");
       var d=document.getElementById("DateFrom2");
       var e=document.getElementById("optDateFrom1");
       var f=document.getElementById("optDateFrom2");
       c.disabled=false;
       d.disabled=false;
       e.disabled=false;
       f.disabled=false;
     }
     
  }
}


function calinsert(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
    if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
    {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
    }
}


function insertfun()
{
    var eid=document.joining.eid.value;
    var rid=document.joining.rid.value;
    var rdate=document.joining.dateApplied.value;
    
    var fdate=document.joining.periodTo.value;
    var tdate=document.joining.periodFrom.value;  
    var tleave=document.joining.leaveDays.value;
    var jd=document.joining.jdate.value;
    var jt=document.joining.jtime.value;
    var datefromsession;
    if(document.joining.DateFrom[0].checked)
        datefromsession=document.joining.DateFrom[0].value;
    else if(document.joining.DateFrom[1].checked)
        datefromsession=document.joining.DateFrom[1].value;
    else
        datefromsession="";
    
    var datetosession;
    if(document.joining.optDateFrom[0].checked)
        datetosession=document.joining.optDateFrom[0].value;
    else if(document.joining.optDateFrom[1].checked)
        datetosession=document.joining.optDateFrom[1].value;
    else
        datetosession="";
    
    var frm=rdate.split('/');
    var fday=frm[0];
    var fmon=frm[1];
    var fyear=frm[2];
    
    var req=getTransport();
    var url= "../../../../../HRM_Leave_JoiningDutyServ?command=insertion&id="+eid+"&ryear="+fyear+"&rslno="+rid+"&rdate="+rdate+"&dateFrom="+fdate+"&dateTo="+tdate+"&leaveDays="+tleave+"&dateJoined="+jd+"&fc="+datefromsession+"&fcp="+datetosession+"&jt="+jt;
    req.open("GET",url,"true");
    req.onreadystatechange=function()
    {
        loadResponse(req);   
    }  
    req.send(null);   
}

function insertDeatils(baseResponse)
{
    var tcode=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(tcode=="Success")
    {
        alert("Record has successfully inserted");
    }
}

function clearfun()
{
    document.joining.ename.value="";
    document.joining.design.value="";
    document.joining.rid.value="";
}

function clearfun1()
{
    document.joining.balanceEL.value="";
    document.joining.periodFrom.value="";
    document.joining.periodTo.value="";
    document.joining.ph.value="";    
    document.joining.sh.value="";
    document.joining.leaveDays.value="";
    document.joining.address.value="";
    document.joining.dateApplied.value="";
    document.joining.jdate.value="";
    document.joining.reason.value="";
    var c=document.getElementById("DateFrom1");
    c.checked=false
    var d=document.getElementById("DateFrom2");
    d.checked=false
    var e=document.getElementById("optDateFrom1");
    e.checked=false
    var f=document.getElementById("optDateFrom2");
    f.checked=false
}

function selectfun()
{
   clearfun1();
    document.joining.rid.value="";
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
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/HRM_ExtensionSelect.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

function doParentRid(emp)
{
document.joining.rid.value=emp;
getRequest(emp);
}

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
var wininterval;
//alert('kkk');
function servicepopup()
{
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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.joining.eid.value=emp;
getFunction(emp);
}

function checkempid()
{
    if(document.joining.rid.value==null || document.joining.rid.value.length==0)
    {
            alert('Select Employee Id');
            document.joining.rid.focus();
            return false;
    }  
    return true;
}

function checkValue(value)
{
        var e=document.getElementById("optDateFrom1");
        var f=document.getElementById("optDateFrom2");
        if(value=='No')
        {
        e.disabled=true;
        f.disabled=true;
        e.checked=false;
        f.checked=false;
        }
        else
        {
        e.disabled=false;
        f.disabled=false;
        }
}

function calinsert(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }  
}
