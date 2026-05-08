//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////

var winemp=null;
var my_window;
var jobflag;
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
     //   alert('test');     
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmCurrentPosting.txtEmployeeid.value=emp;
//document.frmCurrentPosting.reset();
//clr();
doFunction('loademp','null');

}

//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
    jobflag=true;
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
    if(jobflag==true)
    {
      if (winjob && winjob.open && !winjob.closed)
      {
       
         winjob.officeSelection(true,true,true,false);
       }
    }
    else
    {
             if (winjob1 && winjob1.open && !winjob1.closed)
              {
               
                 winjob1.officeSelection(false,false,false,true);
               }
    }
}

function doParentJob(jobid,deptid)
{
    //alert("Do parent Job jobflag:"+jobflag);
    if(jobflag==true)
    {
        document.frmCurrentPosting.txtDept_Id_work.value=deptid ;
        document.frmCurrentPosting.txtOffice_Id.value=jobid;
        doFunction('Load',true);
        return true
    }
    else
    {
        document.frmCurrentPosting.txtDept_Id.value=deptid;
        document.frmCurrentPosting.txtOthOffice_Id.value=jobid ;
        doFunction('Load',false);
        return true
    }

}

//////////////   FOR JOB POPUP WINDOW1 //////////////////////
var winjob1;

function jobpopup1()
{
    jobflag=false;
    //alert('hsi');
    if (winjob1 && winjob1.open && !winjob1.closed) 
    {
       winjob1.resizeTo(500,600);
       winjob1.moveTo(200,200); 
       winjob1.focus();
       return;

    }
    else
    {
        winjob1=null
    }
        
    winjob1= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_for_SR","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winjob1.moveTo(200,200);  
 
    winjob1.focus();
    
    
}



/*function doParentJob(jobid,deptid)
{

    document.frmCurrentPosting.txtOffice_Id.value=jobid;
    document.frmCurrentPosting.txtDept_Id.value=deptid;
    doFunction('Load','null');
    return true

}*/

window.onunload=function()
{

if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winjob1 && winjob1.open && !winjob1.closed) winjob1.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();
//if (wininterval && wininterval.open && !wininterval.closed) wininterval.close();
}

///////////////////////////////////////////////////////////////////////////////////


//************list all *************************
 function popwindow()
    {
        if((document.frmCurrentPosting.txtEmployeeid.value==null)||(document.frmCurrentPosting.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.frmCurrentPosting.txtEmployeeid.focus();
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
        var str="frmCurrentPosting_ListAll.jsp?id="+document.frmCurrentPosting.txtEmployeeid.value;
        my_window= window.open(str,"mywindow1","status=1,height=400,width="+screen.availWidth+",resizable=yes, scrollbars=yes"); 
      my_window.moveTo(250,250);    
    }

var req=false;
try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 //alert(req);


function getTransport()
{
 var req1 = false;
 try 
 {
       req1= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req1 = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req1 = false;
       }
 }
 if (!req1 && typeof XMLHttpRequest != 'undefined') 
 {
       req1 = new XMLHttpRequest();
 }   
 //alert(req1);
 return req1;
}



function checkdate()
{
//alert('check');
        var fromdt=document.frmCurrentPosting.txtDateFrom.value;
        var todt=document.frmCurrentPosting.txtDateTo.value;
        
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
            //document.frmCurrentPosting.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.frmCurrentPosting.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.frmCurrentPosting.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}


function notNull(p)
{


    if((document.frmCurrentPosting.txtEmployeeid.value==null)||(document.frmCurrentPosting.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        alert(document.frmCurrentPosting.txtEmployee);
        document.frmCurrentPosting.txtEmployee.value="";
        document.frmCurrentPosting.txtGpf.value="";
        document.frmCurrentPosting.txtEmployeeid.value="";
        
        //clr();
        document.frmCurrentPosting.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.frmCurrentPosting.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.frmCurrentPosting.txtEmployeeid.value="";
        document.frmCurrentPosting.txtEmployeeid.focus();
        return false;
    }
    
    if(p!=null)
    {
      
        
        if((document.frmCurrentPosting.cmbDesignation.value==0)||(document.frmCurrentPosting.cmbDesignation.value.length==0))
        {
            alert("Select a Designation");
            if((document.frmCurrentPosting.cmbsgroup.value=="0")||(document.frmCurrentPosting.cmbsgroup.value.length==0))
            {
                document.frmCurrentPosting.cmbsgroup.focus();
            }
            else
            {
                document.frmCurrentPosting.cmbDesignation.focus();
            }
            return false;
        }
        
         if((document.frmCurrentPosting.cmbstatus.value=="")||(document.frmCurrentPosting.cmbstatus.value.length==0))
        {
            alert("Select a Employee Status ");
            document.frmCurrentPosting.cmbstatus.focus();
            return false;
        }
        else
        {
                var type=document.frmCurrentPosting.cmbstatus.options[document.frmCurrentPosting.cmbstatus.selectedIndex].value;
                if(type!="")
                {
                    //alert(type);
                    
                    if(type=='DPN')
                    {
                          // alert('deputation');
                            if((document.frmCurrentPosting.txtDept_Id.value=='')||(document.frmCurrentPosting.txtDept_Id.value.length==0))
                            {
                                alert("Select the Department");
                                document.frmCurrentPosting.txtDept_Id.focus();
                                return false;
                            }
                            else if((document.frmCurrentPosting.txtOthOffice_Id.value==null)||(document.frmCurrentPosting.txtOthOffice_Id.value.length==0))
                            {
                                alert("Select Office Id");
                                document.frmCurrentPosting.txtOthOffice_Id.focus();
                                return false;
                            }
                            else if((document.frmCurrentPosting.txtDepDateFrom.value==null)||(document.frmCurrentPosting.txtDepDateFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtDepDateFrom.focus();
                                return false;
                            }
                            return true;
                    }
                    else  if(type=='WKG')
                    {
                            //alert('working');
                            if((document.frmCurrentPosting.txtOffice_Id.value==null)||(document.frmCurrentPosting.txtOffice_Id.value.length==0))
                            {
                                alert("Select Office Id");
                                document.frmCurrentPosting.txtOffice_Id.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtWorkDateFrom.value==null)||(document.frmCurrentPosting.txtWorkDateFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtWorkDateFrom.focus();
                                return false;
                            }
                            return true;
                    }
                     else  if(type=='LLV')
                    {
                           if((document.frmCurrentPosting.cmbleavetype.value=='')||(document.frmCurrentPosting.cmbleavetype.value.length==0))
                            {
                                alert("Select the leave type");
                                document.frmCurrentPosting.cmbleavetype.focus();
                                return false;
                            }
                            
                            if((document.frmCurrentPosting.txtDateFrom.value==null)||(document.frmCurrentPosting.txtDateFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtDateFrom.focus();
                                return false;
                            }
                         /*    if((document.frmCurrentPosting.txtDateTo.value==null)||(document.frmCurrentPosting.txtDateTo.value.length==0))
                            {
                                alert("Enter a value for Date To");
                                document.frmCurrentPosting.txtDateTo.focus();
                                return false;
                            }
                             var c=checkdate();
                               if(c==false)
                               {
                                    document.frmCurrentPosting.txtDateFrom.focus();
                                    return false;
                                }
                                return true;*/
                    }
                     else  if(type=='SUS')
                    {
                            if((document.frmCurrentPosting.txtSusFrom.value==null)||(document.frmCurrentPosting.txtSusFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtSusFrom.focus();
                                return false;
                            }
                             if((document.frmCurrentPosting.txtsusreson.value==null)||(document.frmCurrentPosting.txtsusreson.value.length==0))
                            {
                                alert("Enter the Reason for suspension ");
                                document.frmCurrentPosting.txtsusreson.focus();
                                return false;
                            }
                            return true;
                            
                    }
                      else  if(type=='ABS')
                    {
                            if((document.frmCurrentPosting.txtAbsFrom.value==null)||(document.frmCurrentPosting.txtAbsFrom.value.length==0))
                            {
                                alert("Enter a value for Date from ");
                                document.frmCurrentPosting.txtAbsFrom.focus();
                                return false;
                            }
                            return true;
                    }
                        
                return true;;
                
                }
        }
        
        

    }
    
    return true;

}


function doFunction(Command,param)
{
    //alert("command:"+Command);
   
    
    var empid=document.frmCurrentPosting.txtEmployeeid.value;
  /*  var slno=document.frmCurrentPosting.txtSNo.value;
    var datefrom=document.frmCurrentPosting.txtDateFrom.value;
    
    var datefromsession;
    if(document.frmCurrentPosting.optDateFrom[0].checked)
        datefromsession=document.frmCurrentPosting.optDateFrom[0].value;
    else
        datefromsession=document.frmCurrentPosting.optDateFrom[1].value;
    var dateto=document.frmCurrentPosting.txtDateTo.value;
    var datetosession;
    if(document.frmCurrentPosting.optDateTo[0].checked)
       datetosession=document.frmCurrentPosting.optDateTo[0].value;
    else
        datetosession=document.frmCurrentPosting.optDateTo[1].value;
        
    var designation=document.frmCurrentPosting.cmbDesignation.value;
    var place=document.frmCurrentPosting.txtOffice_Id.value;
    var deptid=document.frmCurrentPosting.txtDept_Id.value;
    var status=document.frmCurrentPosting.cmbStatus.value;
    var detail=document.frmCurrentPosting.txtDetail.value;
    var remark=document.frmCurrentPosting.txtRemark.value;
    
    */
    

    if(Command=='loademp')
    {
       
        var check=notNull(null);
        if(check )
        {
                startwaiting(document.frmCurrentPosting) ;
                service=null;
      
                //alert('load emp');
               
                var url="../../../../../CurrentPostingServ.view?Command=loademp&txtEmployeeid="+empid;
               // var req=getTransport();
              // alert(req);
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
        }        
    
    }
    
    else if(Command=='Add')
    {
    
        var check=notNull('Add');
       // alert(check);
        if(check)
        {
                statuswaiting(document.frmCurrentPosting) ;
                //if(check)
                {
                        document.frmCurrentPosting.action="../../../../../CurrentPostingServ.view?Command=Add&txtEmployeeid="+empid;
                         document.frmCurrentPosting.method="POST";
                         document.frmCurrentPosting.submit();
                       return;
                  /*  var url="../../../../../CurrentPostingServ.view?Command=Add&txtEmployeeid="+empid;
                    url=url+"&txtDateFrom="+datefrom+"&optDateFrom="+datefromsession;
                    url=url+"&txtDateTo="+dateto+"&optDateTo="+datetosession;
                    url=url+"&cmbDesignation="+designation+"&cmbPlace="+place;
                    url=url+"&txtdeptid="+deptid;
                    url=url+"&cmbStatus="+status+"&txtDetail="+detail;
                    url=url+"&txtRemark="+remark;
                    // alert(url);
                    req.open("POST",url,true);
                   
                   req.onreadystatechange=handleResponse;
                    if(window.XMLHttpRequest)
                            req.send(null);
                    else req.send();      
                  */  
                    // alert('in add');
                }
        }
    }
    
   
    else if(Command=="Load")
    {
      var  Office_Id;
      var   Dept_Id;
      
     // alert(param);
     if(param==true)
    {
        jobflag=true;
        Dept_Id=document.frmCurrentPosting.txtDept_Id_work.value;
       Office_Id =document.frmCurrentPosting.txtOffice_Id.value;
        
    }
    else if(param==false)
    {
         jobflag=false;
       Dept_Id= document.frmCurrentPosting.txtDept_Id.value;
       Office_Id =document.frmCurrentPosting.txtOthOffice_Id.value;
    }
       // alert(Office_Id +'   '+Dept_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
        //var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
     else if(Command=="Clear")
            {
               
                clr();
                document.frmCurrentPosting.cmdadd.style.display="block";
                document.frmCurrentPosting.cmdupdate.style.display="none";
                //document.frmCurrentPosting.cmdadd.disabled=false;
               // document.frmCurrentPosting.cmdupdate.disabled=true;
                document.frmCurrentPosting.cmddelete.disabled=true;
                document.frmCurrentPosting.txtDept_Id.value="";
                
            }
   
}

function offclr()
{
    
    document.frmCurrentPosting.txtOffice_Id.value='';
    
    document.frmCurrentPosting.txtOffice_Name.value='';
    document.frmCurrentPosting.txtOffice_Address1.value='';
    document.frmCurrentPosting.txtOffice_Address2.value='';
    document.frmCurrentPosting.txtOffice_City.value='';
       
   

}

function checkdeptsel()
{
     if((document.frmCurrentPosting.txtDept_Id.value==0)||(document.frmCurrentPosting.txtDept_Id.value.length==0))
        {
                offclr();
        }
}
function clr()
{
        document.frmCurrentPosting.txtEmployee.value="";
        document.frmCurrentPosting.txtGpf.value="";
        document.frmCurrentPosting.txtEmployeeid.value="";
        document.frmCurrentPosting.cmbsgroup.value="0";
        document.frmCurrentPosting.cmbstatus.value="";
        disableAll();
         document.frmCurrentPosting.txtEmployeeid.focus();

}


function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            stopwaiting(document.frmCurrentPosting);
            
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
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
            else if(Command=="test")
            {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
                if(flag=="failure")
                {
                    
                         alert('Enter a Valid Employee Number');
                         document.frmCurrentPosting.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.frmCurrentPosting.txtEmployeeid.focus();
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
            
             else if(Command=="SerGroup")
            {
                selectGroupfun(baseResponse);
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
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
                        //alert(flag1);
                        self.close();
                        return;
                    }
                    }catch(e){
                    //alert(e);
                    
                    }  
            }
           
            
            
        }  
        
    }
    
}




function lastDateFun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //  alert(flag);
    if(flag=="success")
    {
           /* document.frmCurrentPosting.txtDateFrom.disabled=true;
            var i=document.getElementById("fromimg");
            i.disabled=true;
            i.alt="Disabled";
            */
            var nextdate=baseResponse.getElementsByTagName("nextdate")[0].firstChild.nodeValue;
            var nextses=baseResponse.getElementsByTagName("nextsession")[0].firstChild.nodeValue;
            
            try{
            document.frmCurrentPosting.txtDateFrom.value=nextdate;
              }catch(e){}  try{
                    if(nextses=='FN'){
                        document.frmCurrentPosting.optDateFrom[0].checked=true;
                     }
                    else {
                        document.frmCurrentPosting.optDateFrom[1].checked=true;
                    }
            }catch(e){}
            /*document.frmCurrentPosting.optDateFrom[0].disabled=true;
            document.frmCurrentPosting.optDateFrom[1].disabled=true;
            */
            return;
            
    }
  /*  else if(flag=="failure")
    {
            document.frmCurrentPosting.txtDateFrom.disabled=false;
            var i=document.getElementById("fromimg");
            i.disabled=false;
            i.alt="Show Calendar";
            document.frmCurrentPosting.optDateFrom[0].disabled=false;
            document.frmCurrentPosting.optDateFrom[1].disabled=false;
    }*/
}

function loadEmp(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert(flag);
     if(flag=="success")
    {
           
           if(calendarControl)
                calendarControl.hide();
           //clr();
            var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
            var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
            document.frmCurrentPosting.txtEmployee.value=ename;
            if(egpf==0)
                egpf="";
            document.frmCurrentPosting.txtGpf.value=egpf;
            var tbody=document.getElementById("tb");
           
            
            
    }
    else if(flag=="failure1")
    {
          // alert(document.frmCurrentPosting.txtEmployeeid.value);
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Create Current Posting. Because Employee Id "+id+" is not under your Office!");
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("You have no Current Posting. Can not Create Current Posting for "+id+"!");
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not Create Current Posting!");
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.frmCurrentPosting.txtEmployeeid.value;
            alert("Can not Create Current Posting. Access Denined!");
           clr();
    }
    else  if(flag=="exists")
    {
            alert('Given Employee Id already exist');
                clr();
               
    }
    else  if(flag=="freezed")
    {
            alert('Given Employee Id is already freezed');
            clr();
    }
    else
    {
        
               
        alert('Enter a Valid Employee Number');
        clr();
        //document.frmCurrentPosting.reset();
        
    }


}

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
          //document.frmCurrentPosting.txtSNo.focus();
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
    if(document.frmCurrentPosting.txtEmployeeid.value==null || document.frmCurrentPosting.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.frmCurrentPosting.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}

function checkdeptid()
{
    if(document.frmCurrentPosting.txtDept_Id.value==null || document.frmCurrentPosting.txtDept_Id.value.length==0)
    {
            alert('Select Department Id');
            document.frmCurrentPosting.txtDept_Id.focus();
            return false;
    }
   
    return true;

}


//*********************  sevice group  selection  ************************
function getDesignation()
    {
        var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
           // var din=document.getElementById("divdes");
            //din.style.visibility="visible";
            //document.frmCurrentPosting.cmbdes.style.visibility="visible";
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
        var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
        startwaiting(document.frmCurrentPosting) ;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req1=getTransport();
        req1.open("GET",url,true);        
        req1.onreadystatechange=function()
        {
            
             loadDesignation(req1);
        }
        req1.send(null);
    }
    
  /*   function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
        startwaiting(document.frmCurrentPosting) ;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
        req.send(null);
    }*/
    
    function  loadDesignation(req1)
{
     if(req1.readyState==4)
        {
          if(req1.status==200)
          { 
                //alert(req1);            
                var response=req1.responseXML.getElementsByTagName("response")[0];
                //alert(response);
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                //des.innerHTML="";
               // alert('test');
                var des=document.getElementById("cmbDesignation");
                var i=0;
                 stopwaiting(document.frmCurrentPosting);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
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
       var url="../../../../../CurrentPostingServ.view?Command=SerGroup&cmbdes="+val;
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
        document.frmCurrentPosting.cmbsgroup.value=gid;
        loadOfficesByType1(gid);
    }
    
  

}
    
function checkGroup()
    {
        
        var type=document.frmCurrentPosting.cmbsgroup.options[document.frmCurrentPosting.cmbsgroup.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.frmCurrentPosting.cmbsgroup.focus();
            return false;
        }
    }
    
//////////////////////////////////////////////New Code////////////////////////////////
function changeStatus()
{

        var type=document.frmCurrentPosting.cmbstatus.options[document.frmCurrentPosting.cmbstatus.selectedIndex].value;
        if(type!="")
        {
            //alert(type);
             disableAll();
            if(type=='DPN')
            {
                    var id=document.getElementById("divdep");
                    id.style.display="block";
            }
            else  if(type=='WKG')
            {
                    var id=document.getElementById("divwork");
                    id.style.display="block";
            }
             else  if(type=='LLV')
            {
                    var id=document.getElementById("divll");
                    id.style.display="block";
            }
             else  if(type=='SUS')
            {
                    var id=document.getElementById("divsus");
                    id.style.display="block";
            }
              else  if(type=='ABS')
            {
                    var id=document.getElementById("divabs");
                    id.style.display="block";
            }
            
        }
        else
        {
                disableAll();
        }

}


function disableAll()
{

    var id=document.getElementById("divwork");
    id.style.display="none";
    var id=document.getElementById("divdep");
    id.style.display="none";
    var id=document.getElementById("divll");
    id.style.display="none";
    var id=document.getElementById("divsus");
    id.style.display="none";
    var id=document.getElementById("divabs");
    id.style.display="none";

}

function reset()
{


}


function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //var OfficeName=document.getElementById("txtOfficeName");
                      //var OfficeId=document.getElementById("txtOfficeId");
                    
                      
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      

                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        document.frmCurrentPosting.txtOffice_Id.value='';
                        //document.frmCurrentPosting.txtDept_Id.value='';
                        document.frmCurrentPosting.txtOffice_Name.value='';
                        document.frmCurrentPosting.txtOffice_Address1.value='';
                        document.frmCurrentPosting.txtOffice_Address2.value='';
                        document.frmCurrentPosting.txtOffice_City.value='';
                               
                        /*document.frmCurrentPosting.cmbControllingLevel.selectedIndex=0;
                        document.frmCurrentPosting.cmbOfficeType.selectedIndex=0;
                        document.frmCurrentPosting.cmbSelectOffice.selectedIndex=0;*/
                        document.frmCurrentPosting.txtOffice_Id.focus();
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
                     
                        document.frmCurrentPosting.txtOffice_Name.value='';
                        document.frmCurrentPosting.txtOffice_Address1.value='';
                        document.frmCurrentPosting.txtOffice_Address2.value='';
                        document.frmCurrentPosting.txtOffice_City.value='';
                          var value=response.getElementsByTagName("options");
                          //alert(value);
                          for(var i=0;i<value.length;i++)
                          {
                            
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                             // var district=tmpoption.getElementsByTagName("District")[0].firstChild.nodeValue;
                             
                             if(jobflag==true)
                             {
                                      document.frmCurrentPosting.txtOffice_Name.value=name;
                                      //document.frmCurrentPosting.cmbDistrict.value=district;
                                      if(officeAddress1!="null")
                                      {
                                      document.frmCurrentPosting.txtOffice_Address1.value=officeAddress1;
                                      }
                                      if(officeAddress2!="null")
                                      {
                                        document.frmCurrentPosting.txtOffice_Address2.value=officeAddress2;
                                      }
                                      if(officeAddress3!="null")
                                      {
                                           document.frmCurrentPosting.txtOffice_City.value=officeAddress3;
                                     }
                            }  
                             else
                             {
                                      document.frmCurrentPosting.txtOthOffice_Name.value=name;
                                      //document.frmCurrentPosting.cmbDistrict.value=district;
                                      if(officeAddress1!="null")
                                      {
                                      document.frmCurrentPosting.txtOthOffice_Address1.value=officeAddress1;
                                      }
                                      if(officeAddress2!="null")
                                      {
                                        document.frmCurrentPosting.txtOthOffice_Address2.value=officeAddress2;
                                      }
                                      if(officeAddress3!="null")
                                      {
                                           document.frmCurrentPosting.txtOthOffice_City.value=officeAddress3;
                                     }
                            }    
                             
                             
                          }
                          
                      }   
                     

            }
        }
    }




