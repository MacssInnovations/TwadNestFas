//alert("show me nom admin");
//var a=1;
var service;
var baseResponse;
var __pagination=10;
  var destid;
    var totalblock=0;
var page;
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
document.EmpTest.txtEmployeeid.value=emp;
doFunction('loademp','null');
}



///////////////////////////////////////////////////////////////////////////////////

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
        if((document.EmpTest.txtDept_Id.value=='TWAD'))
        {
         //winjob.officeSelection(true,true,true,false);
         winjob.officeSelection(true,true,true,false,true);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.EmpTest.txtDept_Id.value;
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
    document.EmpTest.txtOffice_Id.value=jobid;
    document.EmpTest.txtDept_Id.value=deptid;
    callServer1('Load1','null');
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
        if((document.EmpTest.txtEmployeeid.value==null)||(document.EmpTest.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.EmpTest.txtEmployeeid.focus();
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
        var str="EMP_NOMINEE_ListAll.jsp?id="+document.EmpTest.txtEmployeeid.value;
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


function toExit()
{
  //window.close();
var w=window.open(window.location.href,"_self");
w.close();
}

function checkdate()
{
//alert('check');
        var fromdt=document.EmpTest.txtDateFrom.value;
        var todt=document.EmpTest.txtDateTo.value;
        
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
            //document.EmpTest.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.EmpTest.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.EmpTest.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}
function checkDoE()
{
checkempid();
     if(document.EmpTest.EffectDate.length==0||document.EmpTest.EffectDate.value.length=='')
        {
            alert("Enter  Effect from Date ");
            document.EmpTest.EffectDate.focus();
            return false;
        }
    return true;
}


function notNull(p)
{
    
    if((document.EmpTest.txtEmployeeid.value==null)||(document.EmpTest.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.EmpTest.txtEmployee.value="";
        document.EmpTest.txtdob.value="";
        document.EmpTest.txtGpf.value="";
        document.EmpTest.txtEmployeeid.value="";
        
        document.EmpTest.txtEmployeeid.focus();
        return false;
    }
    
     if(isNaN(document.EmpTest.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.EmpTest.txtEmployeeid.value="";
        document.EmpTest.txtEmployeeid.focus();
        return false;
    }
    

       /*  var c=checkdate();
       if(c==false)
       {
            document.EmpTest.txtDateTo.focus();
            return false;
        }*/
        
         if((document.EmpTest.TestCode.value.length==0))
        {
            alert("Select Dept Test ");
            
                document.EmpTest.TestCode.focus();
          
            return false;
        }
        
        if((document.EmpTest.DateExam.value==0)||(document.EmpTest.DateExam.value.length==0))
        {
            alert("Select a Date of Exam");
            
                document.EmpTest.DateExam.focus();
          
            return false;
        }
        
         if((document.EmpTest.EffectDate.value.length==0)&&(document.EmpTest.EffectDate.value.length==0))
        {
            alert("Enter  Effective from Date ");
            document.EmpTest.EffectDate.focus();
            return false;
        }
        
   
    
    return true;
}

function doFunction(Command,param)
{
  
//alert("command :"+Command);
//alert();

    
    var empid=document.EmpTest.txtEmployeeid.value;
    var slno=document.EmpTest.Slno.value;
   
  //  alert(empid);
    
    
    
  /*   if(param!='null')
    {
        alert(param);
                var url="../../../../../TestsPassed.av?Command=loademp&txtEmployeeid="+param;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
         
    
    }*/
    if(Command=='efocus')
    {
    document.EmpTest.txtEmployeeid.focus();
    
    }
    
    if(Command=='loademp')
    {
       //alert("emp");
       // var check=notNull(null);
      //  if(check )
        {
               // startwaiting(document.EmpTest) ;
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
               /* if(param=='exist')
                {
                var url="../../../../../TestsPassed.av?Command=loademp&txtEmployeeid="+empid+"&param=exist";
                }
                else
                {*/
                var url="../../../../../TestsPassed.av?Command=loademp&txtEmployeeid="+empid;
               // alert("Admin   ->"+  url);
                //}
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }
    
    if(Command=='Add')
    {
    
   // alert("added");
        var check=notNull('Add');
        //alert(check);
        var DateExam='';
        var EffectDate='';
        var TestCode='';
        
        if(check)
        {
               // statuswaiting(document.EmpTest) ;
                //if(check)
                
                var DateExam=document.EmpTest.DateExam.value;
                var EffectDate=document.EmpTest.EffectDate.value;
                var TestCode=document.EmpTest.TestCode.value;
             //   alert(TestCode);
           var dcheck=compareDate();
               
            if(dcheck>0)
            {
          var tbody=document.getElementById("tb");
     record1=new Array();
   //  record2=new Array();
    var d=0;
 for(var i=1;i<=tbody.rows.length;i++)
            {
            var r=document.getElementById(i);
           
                var rcells=r.cells;
               // record1[i]=rcells.item(1).firstChild.nodeValue;
                record1[i]=rcells.item(2).firstChild.value;
             //   alert(rcells.item(2).firstChild.value);
                if(record1[i]==TestCode)
                {
                alert("You have already inserted ");
                d=1;
                clr();
                }
               
                }
               if(d==0)
               {
                    var url="../../../../../TestsPassed.av?Command=Add&txtEmployeeid="+empid;
                    url=url+"&DateExam="+DateExam+"&EffectDate="+EffectDate;
                    url=url+"&TestCode="+TestCode;
                  //  alert("Admin ->"+url);
                    req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();     
                }    
                }    // alert('in add');
               
                }
        
        }
    
    if(Command=='Update')
    {
   
        var check=notNull('Update');
       // alert(check);
      
        if(check)
        {
                  
                var DateExam=document.EmpTest.DateExam.value;
                var EffectDate=document.EmpTest.EffectDate.value;
                var TestCode=document.EmpTest.TestCode.value;
                var Slno=document.EmpTest.Slno.value;  
                var dcheck=compareDate();
               
            if(dcheck>0)
            {
     
                    var url="../../../../../TestsPassed.av?Command=Update&txtEmployeeid="+empid;
                    url=url+"&DateExam="+DateExam+"&EffectDate="+EffectDate;
                    url=url+"&TestCode="+TestCode+"&Slno="+Slno;
                   // alert("Update ->"+url);
                    req.open("POST",url,true);
                   
                    req.onreadystatechange=handleResponse;
                    if(window.XMLHttpRequest)
                            req.send(null);
                    else req.send();      
                    }
              
                
             }  
    }
    
    else if(Command=="Delete")
    {
    
        if(confirm('Do You want to delete this record?'))
        {
              //  statuswaiting(document.EmpTest) ;
               
                //var Nominee_Name=document.EmpTest.member.value;
                  var Slno=document.EmpTest.Slno.value;
                var url="../../../../../TestsPassed.av?Command=Delete&txtEmployeeid="+empid;
                 url=url+"&TestCode="+TestCode+"&Slno="+Slno;
               req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
        }
    
    
    
    }
     else if(Command=="Clear")
            {
               
                clr1();
                document.EmpTest.cmdadd.style.display="block";
                document.EmpTest.cmdupdate.style.display="none";
                //document.EmpTest.cmdadd.disabled=false;
               // document.EmpTest.cmdupdate.disabled=true;
                document.EmpTest.cmddelete.disabled=true;
               
               
                
            }
    else if(Command=="Load")
    {
           if(!(empid==null || empid.length==0))
           {
                 
                // alert(fund_id);
                  //startwaiting(document.EmpTest) ;
                var url="../../../../../TestsPassed.av?Command=Load&txtEmployeeid="+empid;
                //alert("url"+url)
                req.open("POST",url,true);
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
                //startwaiting(document.EmpTest) ;
                var url="../../../../../TestsPassed.av?Command=Submit&txtEmployeeid="+empid;
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }
}

function offclr()
{
    
    document.EmpTest.txtOffice_Id.value='';
    
    document.EmpTest.txtOffice_Name.value='';
    document.EmpTest.txtOffice_Address1.value='';
   // document.EmpTest.txtOffice_Address2.value='';
   // document.EmpTest.txtOffice_City.value='';
       
   

}

function checkdeptsel()
{
    // if((document.EmpTest.txtDept_Id.value==0)||(document.EmpTest.txtDept_Id.value.length==0))
        {
                offclr();
        }
}

function clr()
{

document.EmpTest.cmdadd.disabled=false;
//document.EmpTest.TestCode.value='';
document.EmpTest.TestCode.selectedIndex=0;
document.EmpTest.DateExam.value="";
document.EmpTest.EffectDate.value='';
document.EmpTest.Slno.value='';
document.EmpTest.TestCode.disabled=false;
}

function clr1()
{

document.EmpTest.cmdadd.disabled=false;
//document.EmpTest.TestCode.value='';
document.EmpTest.TestCode.selectedIndex=0;
document.EmpTest.DateExam.value="";
document.EmpTest.EffectDate.value='';
document.EmpTest.Slno.value='';
document.EmpTest.TestCode.disabled=false;
document.EmpTest.txtEmployeeid.value='';
document.EmpTest.txtEmployee.value='';
document.EmpTest.txtdob.value='';
document.EmpTest.txtGpf.value='';
}

function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
//            stopwaiting(document.EmpTest);
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // service=baseResponse;
          // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          //  alert('test'+tagcommand);
           var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loademp")
            {
          //  alert("load emp handler");
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
                         document.EmpTest.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.EmpTest.txtEmployeeid.focus();
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
                        //alert(flag1);
                        self.close();
                        return;
                    }
                    }catch(e){
                    //alert(e);
                    
                    }  
            }
           
            else if(Command=="Load")
            {
         
                   // loadPage(1)
                   var tbody=document.getElementById("tb");
                   try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                   var service =baseResponse.getElementsByTagName("test");
                  // alert("total"+a);
                var TestCode,DateExam,EffectDate;
                   
                                      
                 //  alert(service.length);
                // var j,age,dob;
                   for(i=0;i<service.length;i++)
                   
                {
                j=i+1;
                        var EMPLOYEE_ID = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
                       // alert("empid"+EMPLOYEE_ID);
                        var TestCode =baseResponse.getElementsByTagName("test")[i].firstChild.nodeValue;
                         var TestName =baseResponse.getElementsByTagName("testname")[i].firstChild.nodeValue;
                        var  EffectDate=baseResponse.getElementsByTagName("effectdate")[i].firstChild.nodeValue;
                        var DateExam=baseResponse.getElementsByTagName("dateexam")[i].firstChild.nodeValue;
                        var id=baseResponse.getElementsByTagName("listno")[i].firstChild.nodeValue;
                        //alert("id"+id);
                        var mycurrent_row=document.createElement("TR");
                      //alert(EMPLOYEE_ID+TestCode+EffectDate+DateExam+id);
                     /*  if(items[15]=='FR')
                       {
                             var descell=document.createElement("TD");
                             var sn=document.createTextNode('Freezed');
                             descell.setAttribute('style','color:red');
                             descell.appendChild(sn);
                             mycurrent_row.appendChild(descell);
                        }
                        else*/
                        {
                            mycurrent_row.id=j;
                            var cell=document.createElement("TD");
                            var anc=document.createElement("A");
                            //var url="javascript:loadTable('"+EMPLOYEE_ID+"')";
                            var url="javascript:loadTable('"+j+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("Edit");
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                        }
                        
                        var descell=document.createElement("TD");
                         var sn=document.createTextNode(id);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                          var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(TestName);
                         sc.type="hidden";
                        sc.name="TestCode";
                        sc.text=TestCode;
                        sc.value=TestCode;
                        descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                         var descell=document.createElement("TD");
                         var sn=document.createTextNode(DateExam);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                        var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(EffectDate);                         
                      descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                         
            }
           
            }
            
        }  
        
    }
    
}





function addfun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    // alert(flag);  
    if(flag=="success")
    {
        var id=baseResponse.getElementsByTagName("genid")[0].firstChild.nodeValue;
       // document.EmpTest.Slno.value=id;
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
             //alert("Record is Added successfully")
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
                         //    loadPage(1);
            
           }
           doFunction('Load','null');
            clr();
        //////////////////////////////////////////////////////////////////////////////
        alert("Records Added successfully");
        //window.location.reload(true);
    
    }
  
  
    else
    {
            
            alert("Records are not Saved");
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
            document.EmpTest.txtEmployee.value=ename;
            if(edob=="-")
                edob="";
            document.EmpTest.txtdob.value=edob;
          
            if(egpf==0)
                egpf="";
            document.EmpTest.txtGpf.value=egpf;
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
                //alert(service.length);
                                    /* <SERVICE_LIST_SLNO> <DATE_FROM> <DATE_FROM_SESSION> <DATE_TO> 
                     <DATE_TO_SESSION> <DESIGNATION_ID> <OFFICE_ID> <EMPLOYEE_STATUS_ID>
                     <STATUS_DETAIL> <REMARKS> 
                     
                     */ 
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
                            // loadPage(1);
                       
            
            }
            
           doFunction('Load','null');
            clr();
            
    }
    
    else if(flag=="failure1")
    {
            var id=document.EmpTest.txtEmployeeid.value;
            //alert("Can not Update SR. Because Employee Id "+id+" is not under your Office!");
            alert("SR controling office for this employee is different from your office!");
            document.EmpTest.txtEmployee.value="";
            document.EmpTest.txtdob.value="";
            document.EmpTest.txtGpf.value="";
            document.EmpTest.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpTest.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.EmpTest.txtEmployeeid.value;
            alert("You have no Current Posting. Can not update SR for "+id+"!");
            document.EmpTest.txtEmployee.value="";
            document.EmpTest.txtdob.value="";
            document.EmpTest.txtGpf.value="";
            document.EmpTest.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpTest.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.EmpTest.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not update SR!");
            document.EmpTest.txtEmployee.value="";
            document.EmpTest.txtdob.value="";
            document.EmpTest.txtGpf.value="";
            document.EmpTest.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpTest.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.EmpTest.txtEmployeeid.value;
            alert("Can not update SR. Access Denined!");
            document.EmpTest.txtEmployee.value="";
            document.EmpTest.txtdob.value="";
            document.EmpTest.txtGpf.value="";
            document.EmpTest.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EmpTest.txtEmployeeid.focus();
            clr();
    }
    else
    {
        
               
        alert('Enter a Valid Employee Number');
        document.EmpTest.txtEmployee.value="";
        document.EmpTest.txtdob.value="";
        document.EmpTest.txtGpf.value="";
        document.EmpTest.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        document.EmpTest.txtEmployeeid.focus();
        clr();
    }


}



function changepage()
{
//alert('test');
var page=document.EmpTest.cmbpage.value;
loadPage(parseInt(page));

}



function loadPage(page)
{
alert("load page33333333333333333333333333");
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             alert(baseResponse);
           // service=baseResponse;
           alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          service=baseResponse.getElementsByTagName("servicedata");
            document.EmpTest.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                  
                  // alert(service);
             if(service)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                 alert("lenght"+service.length);
                 alert("service"+service);
                
            
                    
                    
                    
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

           __pagination=document.EmpTest.cmbpagination.value;
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

function ischarValid(evt,item){


var chrcode = (evt.which) ? evt.which : evt.keyCode

if (chrcode!=46 && (chrcode!=08) && (chrcode!=09)&& (chrcode!=32)&&
 (chrcode < 97 || chrcode > 122) && (chrcode < 65 || chrcode > 90)){
	return false;	}
	
				return true;

} 

function compareDate()
{
var d=1;
    Date1=new Array();
    Date2=new Array();
            var DateExam=document.EmpTest.DateExam.value;
            var EffectDate=document.EmpTest.EffectDate.value;
            Date1=DateExam.split("/");
            Date2=EffectDate.split("/");
            //alert("Year"+Date1[2]+Date2[2]+"Month"+Date1[1]+Date2[1]+"Date"+Date1[0]+Date2[0]);
            if(Date1[2] >Date2[2])
                d=0;
            else
            if(Date1[1]>Date2[1])
                d=0;
            else
            if(Date1[0]>Date2[0])
                d=0;
        if(d==0)
         {    alert("Effect from Date should be Greater than or Equal Exam Date ");
              clr();
             return 0;      
             }
       else
       return 1;
                
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
          //document.EmpTest.Slno.focus();
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
    
   // var rel;
    
    clr();
    document.EmpTest.cmdupdate.style.display="block";
 
                document.EmpTest.cmdadd.disabled=true;
               // document.EmpTest.cmdupdate.disabled=true;
                document.EmpTest.cmddelete.disabled=false;
    var r=document.getElementById(scod);
    var rcells=r.cells;
    
    try{
   /* alert("list2"+rcells.item(2).firstChild.nodeValue);
    alert("list1"+rcells.item(1).firstChild.nodeValue);
    alert("list3"+rcells.item(3).firstChild.nodeValue);
    alert("list4"+rcells.item(4).firstChild.nodeValue);*/
   /* document.EmpTest.Slno1.value=rcells.item(1).lastChild.nodeValue;*/
    document.EmpTest.Slno.value=rcells.item(1).firstChild.nodeValue;
    //rcells.item(1).firstChild.nodeValue;
     //document.EmpTest.Slno.value=
     //alert(rcells.item(2).lastChild.nodeValue);
    document.EmpTest.TestCode.value=rcells.item(2).firstChild.value;
   document.EmpTest.DateExam.value=rcells.item(3).firstChild.nodeValue;
    document.EmpTest.EffectDate.value=rcells.item(4).firstChild.nodeValue;
     document.EmpTest.TestCode.disabled=true;
    }
        catch(e){
      alert("error");
      }
}



function updatefun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
    {
    var items=new Array();
          document.EmpTest.cmdadd.style.display="block";
         document.EmpTest.cmdupdate.style.display="none";  
         document.EmpTest.TestCode.disabled=false;
         
        // changeempstatus();
         alert("Records are Updated");
         doFunction('Load','null');
         
    /*document.EmpTest.txtDateFrom.disabled=false;
    var i=document.getElementById("fromimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EmpTest.optDateFrom[0].disabled=false;
    document.EmpTest.optDateFrom[1].disabled=false;
    
    document.EmpTest.txtDateTo.disabled=false;
    var i=document.getElementById("toimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EmpTest.optDateTo[0].disabled=false;
    document.EmpTest.optDateTo[1].disabled=false;
     */    
     doFunction('Load','null');
         clr();
         //changeempstatus();
    }
    else if(flag=='failure1')
    {
            
            alert("Total share value should not exceed 100");
            clr();
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
        var r=document.getElementById(document.EmpTest.Slno.value);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        */
        //clr();
        document.EmpTest.cmdadd.style.display="block";
        document.EmpTest.cmdupdate.style.display="none";
       // document.EmpTest.cmdadd.disabled=false;
       // document.EmpTest.cmdupdate.disabled=true;
        document.EmpTest.cmddelete.disabled=true;
        document.EmpTest.TestCode.disabled=false;
       //doFunction('loademp','exist');
      
       //////////////////////////////////////////////////////////////////////////////////
  /*      var tbody=document.getElementById("tb");
           
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
                           //  loadPage(1);
            
           }*/
            clr();
        //////////////////////////////////////////////////////////////////////////////
        doFunction('Load','null');
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
  _Service_Period_Beg_Year=1960;
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
    if(document.EmpTest.txtEmployeeid.value==null || document.EmpTest.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.EmpTest.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}


