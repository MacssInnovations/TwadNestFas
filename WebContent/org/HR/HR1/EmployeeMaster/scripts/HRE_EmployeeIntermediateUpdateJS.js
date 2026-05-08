var service;
var __pagination=10;
  
    var totalblock=0;

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
document.HRE_EmployeeServiceDetails.txtEmployeeid.value=emp;
doFunction('loademp','null');

}



///////////////////////////////////////////////////////////////////////////////////
function intervalpopup()
{

    if (wininterval && wininterval.open && !wininterval.closed) 
    {
       wininterval.close()
    }
    
    
     var url=  "../../../../../org/HR/HR1/EmployeeMaster/jsps/HRE_EmployeeServiceInterval.jsp";
     
    wininterval= window.open(url,"mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    wininterval.moveTo(250,250);  
    wininterval.focus();
    
}

//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,600);
       winjob.moveTo(200,200); 
       winjob.focus();
       return;

    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_for_SR","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(200,200);  
 
    winjob.focus();
    
    
}

function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed)
  {
        if((document.HRE_EmployeeServiceDetails.txtDept_Id.value=='TWAD'))
        {
         winjob.officeSelection(true,true,true,false);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.HRE_EmployeeServiceDetails.txtDept_Id.value;
           // winjob.getOtherOffice();
           //var opt4=document.getElementById("optother");
           //winjob.document.HRM_JobSearch.optother.checked=true;
           
        }
    }
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
//if(deptid==null)
{
    document.HRE_EmployeeServiceDetails.txtOffice_Id.value=jobid;
    document.HRE_EmployeeServiceDetails.txtDept_Id.value=deptid;
    callServer1('Load','null');
    return true
}
/*else
{
        alert('Please select a TWAD Office');
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
        }
        return false
}*/
}

window.onunload=function()
{

if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();
if (wininterval && wininterval.open && !wininterval.closed) wininterval.close();
}

///////////////////////////////////////////////////////////////////////////////////


//************list all *************************
 function popwindow()
    {
        if((document.HRE_EmployeeServiceDetails.txtEmployeeid.value==null)||(document.HRE_EmployeeServiceDetails.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            return false;
        }
        if (my_window && my_window.open && !my_window.closed) 
        {
          
           my_window.focus();
           return;
    
        }
        else
        {
            my_window=null
        }
        var str="HRE_EmployeeServiceDetails_ListAll.jsp?id="+document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
        my_window= window.open(str,"mywindow1","status=1,height=400,width="+screen.availWidth+",resizable=yes, scrollbars=yes"); 
      my_window.moveTo(250,250);    
    }

var req=false;
try
{
    req=new ActiveXObject("Msxml2.XMLHTTP");
}
catch(e)
{
    try
    {
        req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(ee)
    {
        req=false;
    }
}

if(!req || typeof XMLHTTPRequest !='undefined')
{
    req=new XMLHttpRequest();
}

function toExit()
{
  //window.close();
var w=window.open(window.location.href,"_self");
w.close();
}

function checkdate()
{
//alert('check');
        var fromdt=document.HRE_EmployeeServiceDetails.txtDateFrom.value;
        var todt=document.HRE_EmployeeServiceDetails.txtDateTo.value;
        
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
            alert('To Date should be greater than or equal To Date');
            //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('To Date should be greater than or equal To Date');
                    //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('To Date should be greater than or equal To Date');
                            //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                            return false;
                        }
                         else (fday==tday)
                        {
                                   var fradvalue;
                                   if(document.HRE_EmployeeServiceDetails.optDateFrom[0].checked==true)
                                   {
                                            fradvalue=document.HRE_EmployeeServiceDetails.optDateFrom[0].value;
                                   }
                                   else
                                   {
                                            fradvalue=document.HRE_EmployeeServiceDetails.optDateFrom[1].value;
                                   }
                                   var tradvalue;
                                   if(document.HRE_EmployeeServiceDetails.optDateTo[0].checked==true)
                                   {
                                            tradvalue=document.HRE_EmployeeServiceDetails.optDateTo[0].value;
                                   }
                                   else
                                   {
                                            tradvalue=document.HRE_EmployeeServiceDetails.optDateTo[1].value;
                                   }
                                   if(fradvalue =='AN')
                                   {
                                        if(tradvalue=='FN')
                                        {
                                            alert('Given From Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                      }
                        
                        
                }
        }
        return true;

}

function checkdate1()
{
//alert('check');
        var fromdt=document.HRE_EmployeeServiceDetails.txtDateFromOrg.value;
        var todt=document.HRE_EmployeeServiceDetails.txtDateFrom.value;
        
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
            alert('From Date should be greater than or equal to the  Date ' +fromdt);
            //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be  greater than or equal the  Date ' +fromdt);
                    //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be  greater than or equal the  Date ' +fromdt);
                           // document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                            return false;
                        }
                         else (fday==tday)
                        {
                                   var fradvalue=document.HRE_EmployeeServiceDetails.optDateFromOrg.value;
                                   
                                   var tradvalue;
                                   if(document.HRE_EmployeeServiceDetails.optDateFrom[0].checked==true)
                                   {
                                            tradvalue=document.HRE_EmployeeServiceDetails.optDateFrom[0].value;
                                   }
                                   else
                                   {
                                            tradvalue=document.HRE_EmployeeServiceDetails.optDateFrom[1].value;
                                   }
                                   if(fradvalue =='AN')
                                   {
                                        if(tradvalue=='FN')
                                        {
                                            alert('Given From Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                      }
                        
                }
        }
        return true;

}

function checkdate2()
{
//alert('check');
        var fromdt=document.HRE_EmployeeServiceDetails.txtDateTo.value;
        var todt=document.HRE_EmployeeServiceDetails.txtDateToOrg.value;
        
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
            alert('To Date should be less than or equal to the  Date ' +todt);
            //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('To Date should be less than or equal to the  Date ' +todt);
                    //document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('To Date should be less than or equal to the  Date ' +todt);
                           // document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                            return false;
                        }
                         else (fday==tday)
                        {
                                   var fradvalue;
                                   if(document.HRE_EmployeeServiceDetails.optDateTo[0].checked==true)
                                   {
                                            fradvalue=document.HRE_EmployeeServiceDetails.optDateTo[0].value;
                                   }
                                   else
                                   {
                                            fradvalue=document.HRE_EmployeeServiceDetails.optDateTo[1].value;
                                   }
                                   var tradvalue=document.HRE_EmployeeServiceDetails.optDateToOrg.value;
                                   
                                   if(fradvalue =='AN')
                                   {
                                        if(tradvalue=='FN')
                                        {
                                            alert('Given From Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                      }
                        
                }
        }
        return true;

}


function notNull(p)
{


    if((document.HRE_EmployeeServiceDetails.txtEmployeeid.value==null)||(document.HRE_EmployeeServiceDetails.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.HRE_EmployeeServiceDetails.txtEmployee.value="";
        document.HRE_EmployeeServiceDetails.txtdob.value="";
        document.HRE_EmployeeServiceDetails.txtGpf.value="";
        document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                     if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        clr();
        document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.HRE_EmployeeServiceDetails.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
        document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
        return false;
    }
    
    if(p!=null)
    {
        if((document.HRE_EmployeeServiceDetails.txtDateFrom.value==null)||(document.HRE_EmployeeServiceDetails.txtDateFrom.value.length==0))
        {
            alert("Enter a value for Date from ");
            document.HRE_EmployeeServiceDetails.txtDateFrom.focus();
            return false;
        }
        if((document.HRE_EmployeeServiceDetails.txtDateTo.value==null)||(document.HRE_EmployeeServiceDetails.txtDateTo.value.length==0))
        {
            alert("Enter a value for Date To");
            document.HRE_EmployeeServiceDetails.txtDateTo.focus();
            return false;
        }
           
           var c=checkdate();
           if(c==false)
           {
                document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                return false;
            }
           var c=checkdate2();
           if(c==false)
           {
                document.HRE_EmployeeServiceDetails.txtDateTo.focus();
                return false;
            }
        
       

    }
    
    return true;

}

function test()
{
        var check=notNull(null);
        if(check)
        {
                var empid=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
                var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=test&txtEmployeeid="+empid;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();      
        }    


}







function doFunction(Command,param)
{
    //alert("command:"+Command);
   
    
    var empid=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
    var slno=document.HRE_EmployeeServiceDetails.txtSNo.value;
   // alert(slno);
    var datefrom=document.HRE_EmployeeServiceDetails.txtDateFrom.value;
    var datefromsession;
    if(document.HRE_EmployeeServiceDetails.optDateFrom[0].checked)
        datefromsession=document.HRE_EmployeeServiceDetails.optDateFrom[0].value;
    else
        datefromsession=document.HRE_EmployeeServiceDetails.optDateFrom[1].value;
   
    var dateto=document.HRE_EmployeeServiceDetails.txtDateTo.value;
    var datetoOrg=document.HRE_EmployeeServiceDetails.txtDateToOrg.value;
    var datetosession;
    if(document.HRE_EmployeeServiceDetails.optDateTo[0].checked)
       datetosession=document.HRE_EmployeeServiceDetails.optDateTo[0].value;
    else
        datetosession=document.HRE_EmployeeServiceDetails.optDateTo[1].value;
     var datetosessionOrg=document.HRE_EmployeeServiceDetails.optDateToOrg.value;

     
    var status;
     if(document.HRE_EmployeeServiceDetails.optstatus[0].checked)
       status=document.HRE_EmployeeServiceDetails.optstatus[0].value;
    else if(document.HRE_EmployeeServiceDetails.optstatus[1].checked)
       status=document.HRE_EmployeeServiceDetails.optstatus[1].value;
    else if(document.HRE_EmployeeServiceDetails.optstatus[2].checked)
       status=document.HRE_EmployeeServiceDetails.optstatus[2].value;
    else 
       status=document.HRE_EmployeeServiceDetails.optstatus[3].value;
    
     var statusOrg=document.HRE_EmployeeServiceDetails.optstatusOrg.value;
        
    var detail=document.HRE_EmployeeServiceDetails.txtDetail.value;
    var detailOrg=document.HRE_EmployeeServiceDetails.txtDetailOrg.value;
    var remark=document.HRE_EmployeeServiceDetails.txtRemark.value;
    var remarkOrg=document.HRE_EmployeeServiceDetails.txtRemarkOrg.value;
    
  
    if(Command=='loademp')
    {
       
        var check=notNull(null);
        if(check )
        {
                startwaiting(document.HRE_EmployeeServiceDetails) ;
                service=null;
       var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';
                //alert('load emp');
                if(param=='exist')
                {
                var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=loademp&txtEmployeeid="+empid+"&param=exist";
                }
                else
                {
                var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=loademp&txtEmployeeid="+empid;
                }
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }
    
    if(Command=='Add')
    {
    
        var check=notNull('Add');
        //alert(check);
        if(check)
        {
                statuswaiting(document.HRE_EmployeeServiceDetails) ;
                //if(check)
                {
               
                    var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=Add&txtEmployeeid="+empid;
                    url=url+"&txtDateFrom="+datefrom+"&optDateFrom="+datefromsession;
                    url=url+"&txtDateTo="+dateto+"&optDateTo="+datetosession;
                   
                    
                    url=url+"&cmbStatus="+status+"&txtDetail="+detail;
                    url=url+"&txtRemark="+remark;
                    // alert(url);
                   req.open("POST",url,true);
                   
                   req.onreadystatechange=handleResponse;
                    if(window.XMLHttpRequest)
                            req.send(null);
                    else req.send();      
                  
                  //alert('add is ok');
                   
                }
        }
    }
    if(Command=='Update')
    {
   
        var check=notNull('Update');
       // alert(check);
        if(check)
        {
               statuswaiting(document.HRE_EmployeeServiceDetails) ;
                    var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=Update&txtEmployeeid="+empid;
                    url=url+"&txtSNo="+slno;
                    url=url+"&txtDateFrom="+datefrom+"&optDateFrom="+datefromsession;
                    url=url+"&txtDateTo="+dateto+"&optDateTo="+datetosession;
                    url=url+"&txtDateToOrg="+datetoOrg+"&optDateToOrg="+datetosessionOrg;
                    url=url+"&cmbStatus="+status+"&txtDetail="+detail;
                    url=url+"&cmbStatusOrg="+statusOrg+"&txtDetailOrg="+detailOrg;
                    url=url+"&txtRemark="+remark;
                    url=url+"&txtRemarkOrg="+remarkOrg;
                    //alert(url);
                  req.open("POST",url,true);
                   
                    req.onreadystatechange=handleResponse;
                    if(window.XMLHttpRequest)
                            req.send(null);
                    else req.send();     
                    //alert('update is ok');
               
        }
    }
    else if(Command=="Delete")
    {
    
        if(confirm('Do You want to delete this record?'))
        {
                statuswaiting(document.HRE_EmployeeServiceDetails) ;
                var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=Delete&txtEmployeeid="+empid;
                 url=url+"&txtSNo="+slno;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
        }
    
    
    
    }
     else if(Command=="Clear")
            {
               
                clr();
               // document.HRE_EmployeeServiceDetails.cmdadd.style.display="block";
                document.HRE_EmployeeServiceDetails.cmdupdate.style.display="none";
                //document.HRE_EmployeeServiceDetails.cmdadd.disabled=false;
               // document.HRE_EmployeeServiceDetails.cmdupdate.disabled=true;
               // document.HRE_EmployeeServiceDetails.cmddelete.disabled=true;
               // document.HRE_EmployeeServiceDetails.txtDept_Id.value="";
                
            }
    else if(Command=="LastDate")
    {
           if(!(empid==null || empid.length==0))
           {
              startwaiting(document.HRE_EmployeeServiceDetails) ;
                var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=LastDate&txtEmployeeid="+empid;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
            }
    
    }
    else if(Command=="Interval")
    {
           if(!(empid==null || empid.length==0))
           {
                startwaiting(document.HRE_EmployeeServiceDetails) ;
                var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=Interval&txtEmployeeid="+empid;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
            }
    
    }
    else if(Command=='Submit')
    {
       
        var check=notNull(null);
        if(check )
        {
                startwaiting(document.HRE_EmployeeServiceDetails) ;
                var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=Submit&txtEmployeeid="+empid;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }
}

function offclr()
{
    
    document.HRE_EmployeeServiceDetails.txtOffice_Id.value='';
    
    document.HRE_EmployeeServiceDetails.txtOffice_Name.value='';
    document.HRE_EmployeeServiceDetails.txtOffice_Address1.value='';
    document.HRE_EmployeeServiceDetails.txtOffice_Address2.value='';
    document.HRE_EmployeeServiceDetails.txtOffice_City.value='';
       
   

}

function checkdeptsel()
{
     if((document.HRE_EmployeeServiceDetails.txtDept_Id.value==0)||(document.HRE_EmployeeServiceDetails.txtDept_Id.value.length==0))
        {
                offclr();
        }
}
function clr()
{
//document.HRE_EmployeeServiceDetails.txtSNo.value='';
document.HRE_EmployeeServiceDetails.txtDateFrom.value='';
datefromsession=document.HRE_EmployeeServiceDetails.optDateFrom[0].checked=true;
document.HRE_EmployeeServiceDetails.txtDateTo.value='';
datetosession=document.HRE_EmployeeServiceDetails.optDateTo[1].checked=true;
//des.innerHTML='';
//document.HRE_EmployeeServiceDetails.cmbStatus.value='0';
document.HRE_EmployeeServiceDetails.txtDetail.value='';
document.HRE_EmployeeServiceDetails.txtRemark.value='';
//document.HRE_EmployeeServiceDetails.txtDept_Id.value='0';

}


function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            stopwaiting(document.HRE_EmployeeServiceDetails);
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
             //alert('test');
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loademp")
            {
                loadEmp(baseResponse);
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
            else if(Command=="test")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
                if(flag=="failure")
                {
                    
                         alert('Enter a Valid Employee Number');
                         document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
                         return false;
                }
                else
                {
                    return true;
                }
                    
                
            }
            else if(Command=="Add")
            {
            
                addfun(baseResponse);
            }
             else if(Command=="Update")
            {
                updatefun(baseResponse);
            }
             else if(Command=="Delete")
            {
                deletefun(baseResponse);
            }
             else if(Command=="SerGroup")
            {
                selectGroupfun(baseResponse);
            }
            else if(Command=="session")
            {
                
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
               
               try
                    {
                    
                    var flag1=baseResponse.getElementsByTagName("flag")[1].firstChild.nodeValue;
                   //  alert(flag1);
                   if(flag1!=null)
                    {
                        alert(flag1);
                        self.close();
                        return;
                    }
                    }catch(e){
                    //alert(e);
                    
                    }  
            }
            else if(Command=="LastDate")
            {
            
                    lastDateFun(baseResponse);
            }
            else if(Command=="Interval")
            {
            
                    IntervalFun(baseResponse);
            }
            else if(Command=="Submit")
            {
            
                    SubmitFun(baseResponse);
            }
             else if(Command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
            
            
        }  
        
    }
    
}

function SubmitFun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //  alert(flag);
    if(flag=="success")
    {
       // alert("Service Records are Submitted successfully");
       // document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
       // doFunction('loademp','null');
       var b;
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> SR Intermediate Period Updation </b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >Records are Submitted Successfully.</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"toExit()\"></input> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
       
            
    }
    if(flag=="failure")
    {
           alert('Service Records are not Submitted');
            
    }

}

function IntervalFun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //  alert(flag);
    if(flag=="success")
    {
         //var interval=baseResponse.getElementsByTagName("interval")[0].firstChild.nodeValue;
        //alert(interval);
        intervalpopup();
    }
    else  if(flag=="failure")
    {
           alert('There is no Intervals');
            
    }
    else if(flag=="failure1")
    {
           alert('There is a Problem in Validation.');
            
    }

}

function lastDateFun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //  alert(flag);
    if(flag=="success")
    {
           /* document.HRE_EmployeeServiceDetails.txtDateFrom.disabled=true;
            var i=document.getElementById("fromimg");
            i.disabled=true;
            i.alt="Disabled";
            */
            var nextdate=baseResponse.getElementsByTagName("nextdate")[0].firstChild.nodeValue;
            var nextses=baseResponse.getElementsByTagName("nextsession")[0].firstChild.nodeValue;
            
            try{
            document.HRE_EmployeeServiceDetails.txtDateFrom.value=nextdate;
              }catch(e){}  try{
                    if(nextses=='FN'){
                        document.HRE_EmployeeServiceDetails.optDateFrom[0].checked=true;
                     }
                    else {
                        document.HRE_EmployeeServiceDetails.optDateFrom[1].checked=true;
                    }
            }catch(e){}
            /*document.HRE_EmployeeServiceDetails.optDateFrom[0].disabled=true;
            document.HRE_EmployeeServiceDetails.optDateFrom[1].disabled=true;
            */
            return;
            
    }
  /*  else if(flag=="failure")
    {
            document.HRE_EmployeeServiceDetails.txtDateFrom.disabled=false;
            var i=document.getElementById("fromimg");
            i.disabled=false;
            i.alt="Show Calendar";
            document.HRE_EmployeeServiceDetails.optDateFrom[0].disabled=false;
            document.HRE_EmployeeServiceDetails.optDateFrom[1].disabled=false;
    }*/
}
function addfun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    // alert(flag);  
    if(flag=="success")
    {
        var id=baseResponse.getElementsByTagName("genid")[0].firstChild.nodeValue;
        document.HRE_EmployeeServiceDetails.txtSNo.value=id;
       //////////////////////////////////////////////////////////////////////////////////
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
              }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
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
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
           }
            clr();
        //////////////////////////////////////////////////////////////////////////////
        alert("Records are Saved successfully");
    
    }
    else if(flag=='failure1')
    {
            
            alert("From Date is overlaped.\nRecords r not Saved");
    }
    else if(flag=='failure2')
    {
            
            alert("To Date is overlaped.\nRecords r not Saved");
    }
    else if(flag=='failure3')
    {
            
            alert("Existing Dates are overlaped.\nRecords r not Saved");
    }
    else
    {
            
            alert("Records r not Saved");
    }


}

function loadEmp(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
    {
           
           if(calendarControl)
                calendarControl.hide();
           
            var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
            var edob=baseResponse.getElementsByTagName("edob")[0].firstChild.nodeValue;
            var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
            document.HRE_EmployeeServiceDetails.txtEmployee.value=ename;
            if(edob=="-")
                edob="";
            document.HRE_EmployeeServiceDetails.txtdob.value=edob;
          
            if(egpf==0)
                egpf="";
            document.HRE_EmployeeServiceDetails.txtGpf.value=egpf;
            var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    //alert(tbody.innerText !='undefined'  && tbody.innerText !=null );
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                        tbody.innerText='';
                    else 
                        tbody.innerHTML='';
                    
                   // for(i=0;i<tbody.rows.length;i++)
                   //     tbody.deleteRows(i);
            }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
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
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
                    
               
                    
                       
            
            }
            
           
            clr();
            
    }
    else if(flag=="failure1")
    {
            
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            //alert("Can not Update SR. Because Employee Id "+id+" is not under your Office!");
            alert("SR controling office for this employee is different from your office!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            alert("You have no Current Posting. Can not update SR for "+id+"!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not update SR!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            alert("Can not update SR. Access Denined!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            clr();
    }
    else
    {
        
               
        alert('Enter a Valid Employee Number');
        document.HRE_EmployeeServiceDetails.txtEmployee.value="";
        document.HRE_EmployeeServiceDetails.txtdob.value="";
        document.HRE_EmployeeServiceDetails.txtGpf.value="";
        document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
        clr();
    }


}



function changepage()
{
//alert('test');
var page=document.HRE_EmployeeServiceDetails.cmbpage.value;
loadPage(parseInt(page));

}



function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
           
            document.HRE_EmployeeServiceDetails.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                  
                  // alert(service);
             if(service)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                 
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                     c++;
                         var items=new Array();
                        items[0]=service[i].getElementsByTagName("SERVICE_LIST_SLNO")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("DATE_FROM")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("DATE_FROM_SESSION")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("DATE_TO")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("DATE_TO_SESSION")[0].firstChild.nodeValue;
                        
                       // items[5]=service[i].getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
                       // items[6]=service[i].getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
                        
                      //  items[7]=service[i].getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
                      //  items[8]=service[i].getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
                         
                        items[9]=service[i].getElementsByTagName("EMPLOYEE_STATUS_ID")[0].firstChild.nodeValue;
                        items[10]=service[i].getElementsByTagName("EMPLOYEE_STATUS_DESC")[0].firstChild.nodeValue;
                        
                           
                        items[11]=service[i].getElementsByTagName("STATUS_DETAIL")[0].firstChild.nodeValue;
                        
                        items[12]=service[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
                        
                      //  items[13]=service[i].getElementsByTagName("OFFICE_DEPT_ID")[0].firstChild.nodeValue;
                        items[14]=service[i].getElementsByTagName("SLNO")[0].firstChild.nodeValue;
                      //  items[15]=service[i].getElementsByTagName("PROCESS_FLOW_STATUS_ID")[0].firstChild.nodeValue;
                        
                        
                        
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR");
                      
                       
                            mycurrent_row.id=items[0];
                            var cell=document.createElement("TD");
                            var anc=document.createElement("A");
                            var url="javascript:loadTable('"+items[0]+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("Edit");
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                        
                        
                        var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(items[14]);
                         sc.type="HIDDEN";
                         sc.name="sScode";
                         sc.text=items[0];
                         sc.value=items[0];
                         descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                        var j=0;
                        var cell2;
                    
                        for(j=1;j<5;j++)
                        {
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            
                            if(items[j]!="null")
                            {
                                var currentText=document.createTextNode(items[j]);
                            }
                            else
                            {
                                
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }
                        
                        var stacell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         if(items[10]!="null")
                        {
                            var currentText=document.createTextNode(items[10]);
                        }
                        else
                        {
                            var currentText=document.createTextNode('');
                        }
                         
                         var sn=currentText;
                         sc.type="HIDDEN";
                         sc.name="sScode";
                         sc.text=items[9];
                         sc.value=items[9];
                         stacell.appendChild(sc);
                         stacell.appendChild(sn);
                         mycurrent_row.appendChild(stacell);
                         
                        for(j=12;j<13;j++)
                        {
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(items[j]!="null")
                            {
                                var currentText=document.createTextNode(items[j]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                       
                }
            
                    
                    
                    
                    ///////////////////////////////
                    
                    }          
                       
                       
            // alert(page);
           // alert(page<totalblock);
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
               
                if(navigator.appName.indexOf("Microsoft")!=-1)
                    cell.innerText= ' / ' +totalblock;
                else
                    cell.innerHTML= ' / ' +totalblock;
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
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
                //cell.innerText='';
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
}



function changepagesize()
{

           __pagination=document.HRE_EmployeeServiceDetails.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
        if(service)
        {
           
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
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
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
            
            
        }
           
}

/////////////////////////////////////////////////////////////////////////////



function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
     
function numbersonly1(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          //document.HRE_EmployeeServiceDetails.txtSNo.focus();
          return true;
        
        }
         if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
     
     
function calins(e,t)
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

/*
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39  )
        {
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

*/
    

function loadTable(scod)
{
    
    
    
    clr();
    var r=document.getElementById(scod);
    var rcells=r.cells;
    
    try{
    document.HRE_EmployeeServiceDetails.txtDateFrom.value=rcells.item(2).firstChild.nodeValue;
    document.HRE_EmployeeServiceDetails.txtDateFromOrg.value=rcells.item(2).firstChild.nodeValue;
      }catch(e){}try{
    document.HRE_EmployeeServiceDetails.optDateFromOrg.value=rcells.item(3).firstChild.nodeValue;
    if(rcells.item(3).firstChild.nodeValue=='AN'){
        document.HRE_EmployeeServiceDetails.optDateFrom[1].checked=true;
    }
    else {
        document.HRE_EmployeeServiceDetails.optDateFrom[0].checked=true;
    }
    }catch(e){}try{
   document.HRE_EmployeeServiceDetails.txtDateTo.value=rcells.item(4).firstChild.nodeValue;
   document.HRE_EmployeeServiceDetails.txtDateToOrg.value=rcells.item(4).firstChild.nodeValue;
      }catch(e){}try{
      document.HRE_EmployeeServiceDetails.optDateToOrg.value=rcells.item(5).firstChild.nodeValue;
    if(rcells.item(5).firstChild.nodeValue=='AN'){
        document.HRE_EmployeeServiceDetails.optDateTo[1].checked=true;
    }
    else {
        document.HRE_EmployeeServiceDetails.optDateTo[0].checked=true;
    }
    }catch(e){}try{
    //alert(rcells.item(6).firstChild.value);
     document.HRE_EmployeeServiceDetails.optstatusOrg.value=rcells.item(6).firstChild.value;
    if(rcells.item(6).firstChild.value=='LLV')
    {
            document.HRE_EmployeeServiceDetails.optstatus[0].checked=true;
    }
    else  if(rcells.item(6).firstChild.value=='TRT')
    {
            document.HRE_EmployeeServiceDetails.optstatus[1].checked=true;
    }
    else  if(rcells.item(6).firstChild.value=='DPT')
    {
            document.HRE_EmployeeServiceDetails.optstatus[2].checked=true;
    }
    else  
    {
            document.HRE_EmployeeServiceDetails.optstatus[3].checked=true;
    }
      }catch(e){}try{
  //  document.HRE_EmployeeServiceDetails.txtDetail.value=rcells.item(7).firstChild.nodeValue;
  //  document.HRE_EmployeeServiceDetails.txtDetailOrg.value=rcells.item(7).firstChild.nodeValue;
      }catch(e){}try{
  //  document.HRE_EmployeeServiceDetails.txtRemark.value=rcells.item(8).firstChild.nodeValue;
   // document.HRE_EmployeeServiceDetails.txtRemarkOrg.value=rcells.item(8).firstChild.nodeValue;
     document.HRE_EmployeeServiceDetails.txtRemark.value=rcells.item(7).firstChild.nodeValue;
    document.HRE_EmployeeServiceDetails.txtRemarkOrg.value=rcells.item(7).firstChild.nodeValue;
  
      }catch(e){}
    document.HRE_EmployeeServiceDetails.txtSNo.value=scod;
    //document.HRE_EmployeeServiceDetails.cmdadd.style.display="none";
    document.HRE_EmployeeServiceDetails.cmdupdate.style.display="block";
    //document.HRE_EmployeeServiceDetails.cmdupdate.disabled=false;
    //document.HRE_EmployeeServiceDetails.cmddelete.disabled=false;
    //document.HRE_EmployeeServiceDetails.cmdadd.disabled=true;
    

}



function updatefun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
  
       var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
              }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
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
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
           }
            clr();
         // document.HRE_EmployeeServiceDetails.cmdadd.style.display="block";
         document.HRE_EmployeeServiceDetails.cmdupdate.style.display="none";                  
         alert("Records are Updated");
         
         
    /*document.HRE_EmployeeServiceDetails.txtDateFrom.disabled=false;
    var i=document.getElementById("fromimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.HRE_EmployeeServiceDetails.optDateFrom[0].disabled=false;
    document.HRE_EmployeeServiceDetails.optDateFrom[1].disabled=false;
    
    document.HRE_EmployeeServiceDetails.txtDateTo.disabled=false;
    var i=document.getElementById("toimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.HRE_EmployeeServiceDetails.optDateTo[0].disabled=false;
    document.HRE_EmployeeServiceDetails.optDateTo[1].disabled=false;
     */    
         clr();
    }
    else if(flag=='failure1')
    {
            
            alert("From Date is overlaped.\nRecords are not Updated");
    }
    else if(flag=='failure2')
    {
            
            alert("To Date is overlaped.\nRecords are not Updated");
    }
    else if(flag=='failure3')
    {
            
            alert("Existing Dates are overlaped.\nRecords are not Updated");
    }
    else
    {
        
       alert("Record not Updated");
    }
}

function deletefun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records are deleted");
       //  var sc=baseResponse.getElementsByTagName("scd")[0].firstChild.nodeValue;
       /* var tbody=document.getElementById("mytable");
        var r=document.getElementById(document.HRE_EmployeeServiceDetails.txtSNo.value);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        */
        //clr();
        
       // document.HRE_EmployeeServiceDetails.cmdadd.style.display="block";
        document.HRE_EmployeeServiceDetails.cmdupdate.style.display="none";
       // document.HRE_EmployeeServiceDetails.cmdadd.disabled=false;
       // document.HRE_EmployeeServiceDetails.cmdupdate.disabled=true;
      //  document.HRE_EmployeeServiceDetails.cmddelete.disabled=true;
       //doFunction('loademp','exist');
      
       //////////////////////////////////////////////////////////////////////////////////
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
              }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
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
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
           }
            clr();
        //////////////////////////////////////////////////////////////////////////////
   }
    else
    {
         
        alert("Records are not deleted");
    }
}


function trm(t)
{
   if(t!=null)
   {
        if(t.value.charAt(0)==String.fromCharCode(32))
        {
            if(t.value.length==1)
                t.value='';
        }
     
    }
}



function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }


function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

function checkempid()
{
    if(document.HRE_EmployeeServiceDetails.txtEmployeeid.value==null || document.HRE_EmployeeServiceDetails.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}

function checkdeptid()
{
    if(document.HRE_EmployeeServiceDetails.txtDept_Id.value==null || document.HRE_EmployeeServiceDetails.txtDept_Id.value.length==0)
    {
            alert('Select Department Id');
            document.HRE_EmployeeServiceDetails.txtDept_Id.focus();
            return false;
    }
   
    return true;

}


//*********************  sevice group  selection  ************************
function getDesignation()
    {
        var type=document.HRE_EmployeeServiceDetails.cmbsgroup.options[document.HRE_EmployeeServiceDetails.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
           // var din=document.getElementById("divdes");
            //din.style.visibility="visible";
            //document.HRE_EmployeeServiceDetails.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
           var des=document.getElementById("cmbDesignation");
           des.innerHTML='';
        }
    }
    
    function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.HRE_EmployeeServiceDetails.cmbsgroup.options[document.HRE_EmployeeServiceDetails.cmbsgroup.selectedIndex].value;
        startwaiting(document.HRE_EmployeeServiceDetails) ;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadDesignation(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
     function loadOfficesByType1(type)
    {
        //alert(type);
       var type=document.HRE_EmployeeServiceDetails.cmbsgroup.options[document.HRE_EmployeeServiceDetails.cmbsgroup.selectedIndex].value;
       startwaiting(document.HRE_EmployeeServiceDetails) ;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var req=getTransport();
       req.open("GET",url,true);        
       req.onreadystatechange=function()
       {
            loadDesignation(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbDesignation");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.HRE_EmployeeServiceDetails);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Designation--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}


function loadServiceGroup(val)
{
//alert(val);
       var url="../../../../../HRE_EmployeeIntermediateUpdateServ.view?Command=SerGroup&cmbdes="+val;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
}

function selectGroupfun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var items=new Array();
    
    if(flag=="success")
    {
        var gid=baseResponse.getElementsByTagName("sid")[0].firstChild.nodeValue;
        document.HRE_EmployeeServiceDetails.cmbsgroup.value=gid;
        loadOfficesByType1(gid);
    }
    
  

}
    
function checkGroup()
    {
        
        var type=document.HRE_EmployeeServiceDetails.cmbsgroup.options[document.HRE_EmployeeServiceDetails.cmbsgroup.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.HRE_EmployeeServiceDetails.cmbsgroup.focus();
            return false;
        }
    }





