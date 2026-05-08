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
   
function clearAll()
{
    document.LeaveRequest.eid.value="";
    document.LeaveRequest.ename.value="";
    document.LeaveRequest.design.value="";
    document.LeaveRequest.balanceEL.value="";
    document.LeaveRequest.ltype.SelectedIndex=0;
    var mc1=document.getElementById("mc1");
    var mc2=document.getElementById("mc2");
    mc1.disabled=false;
    mc2.disabled=false;
    document.LeaveRequest.rid.value="";
    document.LeaveRequest.periodFrom.value="";
    document.LeaveRequest.periodTo.value="";
    document.LeaveRequest.ph.value="";
    document.LeaveRequest.sh.value="";
    document.LeaveRequest.leaveDays.value="";
    document.LeaveRequest.address.value="";
    document.LeaveRequest.dateApplied.value="";
    document.LeaveRequest.purpose.value="";
}
     
function getFunction(param)
{
    var id=param;
    var req=getTransport();
    var url="../../../../../HRM_EL_LeaveRequest?command=getValue&cmdid="+id;
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
            if(cmd=="getValue")
            {
                Details(baseResponse);
            }
            else if(cmd=="balanceValue")
            {
                balanceDetails(baseResponse);
            }
            else
            {
                insertDetails(baseResponse);   
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
        document.LeaveRequest.ename.value=name;
        document.LeaveRequest.design.value=designation;
     }
     else
     {
        alert("Failed to Load");
     }
}

function insertfun()
{
    var id=document.LeaveRequest.eid.value;
    var balance=document.LeaveRequest.balanceEL.value;
    var ltype1=document.LeaveRequest.ltype.value;
    var mc1;
    if(document.LeaveRequest.mc[0].checked)
        mc1=document.LeaveRequest.mc[0].value;
    else if(document.LeaveRequest.mc[1].checked)
        mc1=document.LeaveRequest.mc[1].value;
    else
        mc1="";

    var fromDate=document.LeaveRequest.periodFrom.value;
    var toDate=document.LeaveRequest.periodTo.value;
    var prefixDay=document.LeaveRequest.ph.value;
    var suffixDay=document.LeaveRequest.sh.value;
    var lday=document.LeaveRequest.leaveDays.value;
    var add=document.LeaveRequest.address.value;
    var date1=document.LeaveRequest.dateApplied.value;
    var purpose1=document.LeaveRequest.purpose.value;

    var frm=date1.split('/');
    var fday=frm[0];
    var fmon=frm[1];
    var fyear=frm[2];

    var req=getTransport();
    var url="../../../../../HRM_EL_LeaveRequest1?cmdid="+id+"&bal="+balance+"&year="+fyear+"&ltype="+ltype1+"&mc="+mc1+"&dateFrom="+fromDate+"&dateTo="+toDate+"&ph="+prefixDay+"&sh="+suffixDay+"&leaveDays="+lday+"&address="+add+"&dateApplied="+date1+"&purpose="+purpose1;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        loadResponse(req);   
    } 
    req.send(null);    
}

function insertDetails(baseResponse)
{
    var tcode=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
    if(tcode=="Success")
    {
        rid=baseResponse.getElementsByTagName("rid")[0].firstChild.nodeValue;
        alert("Request id:"+rid+"is inserted successfully");
    }      
}

function datefun()
{

        var fromdt=document.LeaveRequest.periodFrom.value;
        var todt=document.LeaveRequest.periodTo.value;
        
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
            alert('From Date should be less than To Date');
            document.LeaveRequest.periodTo.value="";
            document.LeaveRequest.periodTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    document.LeaveRequest.periodTo.value="";
                    document.LeaveRequest.periodTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                             document.LeaveRequest.periodTo.value="";
                             document.LeaveRequest.periodTo.focus();
                             return false;
                        }
                        
                }
                daysElapsed();
        }
        return true; 
}

function daysElapsed()
{
   var date1=document.LeaveRequest.periodFrom.value;
   var date2=document.LeaveRequest.periodTo.value;
   date1 = date1.split("/");
   date2 = date2.split("/");
   var sDate = new Date(date1[1]+"/"+date1[0]+"/"+date1[2]);
   var eDate = new Date(date2[1]+"/"+date2[0]+"/"+date2[2]);
   var daysApart = Math.abs(Math.round((sDate-eDate)/86400000));
  // alert(daysApart);
   document.LeaveRequest.leaveDays.value=daysApart;
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


function balancecalc()
{
    var empid=document.LeaveRequest.eid.value;
    var ltype=document.LeaveRequest.ltype.value;
    var mc1=document.getElementById("mc1");
    var mc2=document.getElementById("mc2");
    if(ltype!="ML")
    {
        mc1.disabled=true;
        mc2.disabled=true;
    }
    else
    {
        mc1.disabled=false;
        mc2.disabled=false;
    }   
    var req=getTransport();
    var url="../../../../../HRM_EL_LeaveRequest?command=balanceValue&cmdid="+empid+"&cmdtype="+ltype;
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
        loadResponse(req);   
    } 
    req.send(null);    
}
function balanceDetails(baseResponse)  
{
    var tcode=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
    if(tcode=="Success")
    {
        var balance=baseResponse.getElementsByTagName("balance")[0].firstChild.nodeValue;
        document.LeaveRequest.balanceEL.value=balance;
    }    
}

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
document.LeaveRequest.eid.value=emp;
getFunction(emp);
}

function ordercheck()
{

    if((document.LeaveRequest.eid.value=="") ||(document.LeaveRequest.eid.value.length<=0))
    {
        alert("Enter Employee Id");
        document.LeaveRequest.eid.focus();
        return false;
    }
    return true;

}