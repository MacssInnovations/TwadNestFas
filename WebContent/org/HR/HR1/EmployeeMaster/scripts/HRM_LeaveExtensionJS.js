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
    clearfun();
    clearfun1();
    var id=param;
    var req=getTransport();
    var url="../../../../../HRM_LeaveExtensionServ?command=changeid&cmdid="+id;
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
        document.LeaveExtension.ename.value=name;
        document.LeaveExtension.design.value=designation;
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
    var id=document.LeaveExtension.eid.value;
    var req=getTransport();
    var url="../../../../../HRM_LeaveExtensionServ?command=changerid&cmdrid="+rid+"&cmdid="+id;
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
    var lname=baseResponse.getElementsByTagName("rname")[0].firstChild.nodeValue;
    var tid=baseResponse.getElementsByTagName("tid")[0].firstChild.nodeValue;
    var fdate=baseResponse.getElementsByTagName("fdate")[0].firstChild.nodeValue;
    var tdate=baseResponse.getElementsByTagName("tdate")[0].firstChild.nodeValue;
    var pday=baseResponse.getElementsByTagName("pday")[0].firstChild.nodeValue;
    var sday=baseResponse.getElementsByTagName("sday")[0].firstChild.nodeValue;
    var tleave=baseResponse.getElementsByTagName("tleave")[0].firstChild.nodeValue;
    var address=baseResponse.getElementsByTagName("address")[0].firstChild.nodeValue;
    var rdate=baseResponse.getElementsByTagName("rdate")[0].firstChild.nodeValue;
    document.LeaveExtension.balanceEL.value=bleave;
    
    var mc1=document.getElementById("mc1");
    var mc2=document.getElementById("mc2");
    if(lname!="ML")
    {
        mc1.disabled=true;
        mc2.disabled=true;
    }
    else
    {
        mc1.disabled=false;
        mc2.disabled=false;
    }   
    //document.LeaveExtension.tname.value=lname;
    document.LeaveExtension.tid.value=tid;
    document.LeaveExtension.periodFrom.value=fdate;
    document.LeaveExtension.periodTo.value=tdate;
    document.LeaveExtension.ph.value=pday;
    document.LeaveExtension.sh.value=sday;
    document.LeaveExtension.leaveDays.value=tleave;
    document.LeaveExtension.address.value=address;
    document.LeaveExtension.dateApplied.value=rdate;
  }
}

function datefun(fromdt,todt)
{
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
            alert('Extension date should be greater than Period To date');
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Extension date should be greater than Period To date');
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Extension date should be greater than Period To date');
                            return false;
                        }
                        
                }
        }
        var reqLeave=parseInt(document.LeaveExtension.leaveDays.value);
        var days=daysCalc();
        var totleave=days+reqLeave;
        document.LeaveExtension.totdays.value=totleave;  
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

/*function daysElapsed()
{
   var reqLeave=parseInt(document.LeaveExtension.leaveDays.value);
   var days=daysCalc();
   var totleave=days+reqLeave;
   document.LeaveExtension.totdays.value=totleave;  
}*/

function daysCalc()
{
    var date1=document.LeaveExtension.periodTo.value;
    var date2=document.LeaveExtension.extension.value;
    date1 = date1.split("/");
    date2 = date2.split("/");
    var sDate = new Date(date1[1]+"/"+date1[0]+"/"+date1[2]);
    var eDate = new Date(date2[1]+"/"+date2[0]+"/"+date2[2]);
    var daysApart = Math.abs(Math.round((sDate-eDate)/86400000));
    return daysApart;
}

function insertfun()
{
    var eid=document.LeaveExtension.eid.value;
    var rid=document.LeaveExtension.rid.value;
    var bal=document.LeaveExtension.balanceEL.value;

    var lname=document.LeaveExtension.tname.value;
    var lid=document.LeaveExtension.tid.value;
    var fdate=document.LeaveExtension.periodTo.value;
    var tdate=document.LeaveExtension.extension.value;
    var sh=document.LeaveExtension.suffix.value;
    var daysExtended=daysCalc();
    var rsn=document.LeaveExtension.reason.value;
    
    var mc;
    if(document.LeaveExtension.mc[0].checked)
        mc=document.LeaveExtension.mc[0].value;
    else if(document.LeaveExtension.mc[1].checked)
        mc=document.LeaveExtension.mc[1].value;
    else
        mc="";
  
    var today=new Date();
    var mon=parseInt(today.getMonth())+1;
    var ryear=today.getFullYear();
    var rdate=today.getDate()+"/"+mon+"/"+ryear;
    var req=getTransport();
    var url= "../../../../../HRM_LeaveExtensionSel?command=insertion&id="+eid+"&balance="+bal+"&year="+ryear+"&rslno="+rid+"&typeid="+lid+"&dateFrom="+fdate+"&dateTo="+tdate+"&suffix="+sh+"&leaveDays="+daysExtended+"&reason="+rsn+"&mc="+mc+"&dateApplied="+rdate;
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
    document.LeaveExtension.ename.value="";
    document.LeaveExtension.design.value="";
    document.LeaveExtension.rid.value="";
}

function clearfun1()
{
    document.LeaveExtension.balanceEL.value="";
    document.LeaveExtension.tname.value="";
    document.LeaveExtension.tid.value="";
    document.LeaveExtension.periodFrom.value="";
    document.LeaveExtension.periodTo.value="";
    document.LeaveExtension.ph.value="";    
    document.LeaveExtension.sh.value="";
    document.LeaveExtension.leaveDays.value="";
    document.LeaveExtension.address.value="";
    document.LeaveExtension.dateApplied.value="";
    var mc1=document.getElementById("mc1");
    var mc2=document.getElementById("mc2");
    mc1.disabled=false;
    mc2.disabled=false;
    document.LeaveExtension.extension.value="";
    document.LeaveExtension.suffix.value="";
    document.LeaveExtension.reason.value="";
    document.LeaveExtension.totdays.value="";
}

function selectfun()
{
   clearfun1();
    document.LeaveExtension.rid.value="";
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
document.LeaveExtension.rid.value=emp;
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
document.LeaveExtension.eid.value=emp;
getFunction(emp);
}

function ordercheck()
{

    if((document.LeaveExtension.rid.value=="") ||(document.LeaveExtension.rid.value.length<=0))
    {
        alert("Enter Request Id");
        document.LeaveExtension.rid.focus();
        return false;
    }
    return true;

}