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
    var url="../../../../../HRM_LeaveCancellation_Serv?command=changeid&cmdid="+id;
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
        document.LeaveCancellation.ename.value=name;
        document.LeaveCancellation.design.value=designation;
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
    var id=document.LeaveCancellation.eid.value;
    var req=getTransport();
    var url="../../../../../HRM_LeaveCancellation_Serv?command=changerid&cmdrid="+rid+"&cmdid="+id;
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
    var bleave=baseResponse.getElementsByTagName("leave_balance")[0].firstChild.nodeValue;
    var fdate=baseResponse.getElementsByTagName("fdate")[0].firstChild.nodeValue;
    var tdate=baseResponse.getElementsByTagName("tdate")[0].firstChild.nodeValue;
    var pday=baseResponse.getElementsByTagName("pday")[0].firstChild.nodeValue;
    var sday=baseResponse.getElementsByTagName("sday")[0].firstChild.nodeValue;
    var tleave=baseResponse.getElementsByTagName("tleave")[0].firstChild.nodeValue;
    var address=baseResponse.getElementsByTagName("address")[0].firstChild.nodeValue;
    var rdate=baseResponse.getElementsByTagName("rdate")[0].firstChild.nodeValue;
    document.LeaveCancellation.balanceEL.value=bleave;
    document.LeaveCancellation.periodFrom.value=fdate;
    document.LeaveCancellation.periodTo.value=tdate;
    document.LeaveCancellation.ph.value=pday;
    document.LeaveCancellation.sh.value=sday;
    document.LeaveCancellation.leaveDays.value=tleave;
    document.LeaveCancellation.address.value=address;
    document.LeaveCancellation.dateApplied.value=rdate;
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
    var eid=document.LeaveCancellation.eid.value;
    var rid=document.LeaveCancellation.rid.value;
    var bal=document.LeaveCancellation.balanceEL.value;

    var fdate=document.LeaveCancellation.periodTo.value;
    var tdate=document.LeaveCancellation.periodFrom.value;
    var rdate=document.LeaveCancellation.cdate.value;
    var rsn=document.LeaveCancellation.reason.value;
    var tleave=document.LeaveCancellation.leaveDays.value;
    
    var req=getTransport();
    var url= "../../../../../HRM_LeaveCancellation_Serv?command=insertion&id="+eid+"&balance="+bal+"&rslno="+rid+"&dateFrom="+fdate+"&dateTo="+tdate+"&leaveDays="+tleave+"&reason="+rsn+"&dateApplied="+rdate;
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
    document.LeaveCancellation.ename.value="";
    document.LeaveCancellation.design.value="";
    document.LeaveCancellation.rid.value="";
}

function clearfun1()
{
    document.LeaveCancellation.balanceEL.value="";
    document.LeaveCancellation.periodFrom.value="";
    document.LeaveCancellation.periodTo.value="";
    document.LeaveCancellation.ph.value="";    
    document.LeaveCancellation.sh.value="";
    document.LeaveCancellation.leaveDays.value="";
    document.LeaveCancellation.address.value="";
    document.LeaveCancellation.dateApplied.value="";
    document.LeaveCancellation.cancel.value="";
    document.LeaveCancellation.reason.value="";
}

function selectfun()
{
   clearfun1();
    document.LeaveCancellation.rid.value="";
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
document.LeaveCancellation.rid.value=emp;
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
document.LeaveCancellation.eid.value=emp;
getFunction(emp);
}

function ordercheck()
{

    if((document.LeaveCancellation.rid.value=="") ||(document.LeaveCancellation.rid.value.length<=0))
    {
        alert("Enter Order Id");
        document.LeaveCancellation.rid.focus();
        return false;
    }
    return true;

}