 var maxtodate;
 var maxsession;
//alert('start ');
var jobflag;
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
       
         winjob.officeSelection(true,true,true,false);
       }
   
}

function doParentJob(jobid,deptid)
{
   
        //alert(jobflag);
        document.Hrm_TransJoinForm.txtDept_Id_work.value=deptid;
        document.Hrm_TransJoinForm.txtOffice_Id.value=jobid ;
        doFunction('Load',true);
        return true
    

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


//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;

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
        winemp=null;
    }
        
    winemp= window.open("EmpStudyJoiningPopup.jsp","mywindow_ESP1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}
//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
       return;
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}


window.onunload=function()
{
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

//alert("Hellooooooooooooo");
function getTransport()
{
 var req = false;
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
 return req;
}

function toExit()
{

  //window.open('','_parent','null');
   window.close();
}

function loadfun()
{
    //alert("wel");
    //document.Rws_SchPrgs.txtPrgId.focus();
   // document.first.cmdupdate.disabled;
}
function chdate()
{
if((document.Hrm_TransJoinForm.txtDOJ.value==null)||(document.Hrm_TransJoinForm.txtDOJ.value.length==0))
    {
        alert("Null Value not accepted..Enter Date of Joining");
        document.Hrm_TransJoinForm.txtDOJ.focus();
        return false;
    }
    return true;
}


function radioch()
{
if(FN.checked=true)
{
    //alert("hellooooooo");
}
}
function datCh()
{

    if((document.Hrm_TransJoinForm.txtDOJ.value==null)||(document.Hrm_TransJoinForm.txtDOJ.value.length==0))
    {
        alert("Null Value not accepted");
        document.Hrm_TransJoinForm.txtDOJ.focus();
        return false;
    }
    return true;
}



/*function checkdate()
{
//alert('check');
     if(maxtodate==null || maxtodate=='empty')
     {
            return true;
     }
     else
     {
        var fromdt=maxtodate;
        var todt=document.Hrm_TransJoinForm.txtDOC.value;
        
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
            
            alert('Completed Date Should be greater than or equal to '+maxtodate);
            //document.Hrm_TransJoinForm.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                     alert('Completed Date Should be greater than or equal to '+maxtodate);
                    //document.Hrm_TransJoinForm.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Completed Date Should be greater than or equal to '+maxtodate);
                           // document.Hrm_TransJoinForm.txtDateTo.focus();
                            return false;
                        }
                        else (fday==tday)
                        {
                                var radvalue;
                                   if(document.Hrm_TransJoinForm.optFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[1].value;
                                   }
                                   if(maxsession =='AN')
                                   {
                                        if(radvalue=='FN')
                                        {
                                            alert('Given Completed Date Session is not acceptable');
                                            return false;
                                        }
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
    
        var fromdt=document.Hrm_TransJoinForm.txtDOC.value;
        var todt=document.Hrm_TransJoinForm.txtDOJ.value;
        
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
           alert('Join Date Should be greater than or equal to Comleted Date');
            //document.Hrm_TransJoinForm.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than or equal to Comleted Date');
                    //document.Hrm_TransJoinForm.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Join Date Should be greater than or equal to Comleted Date');
                           // document.Hrm_TransJoinForm.txtDateTo.focus();
                            return false;
                        }
                        else (fday==tday)
                        {
                                var optvalue;
                                   if(document.Hrm_TransJoinForm.optFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.optFNAN[1].value;
                                   }
                                   var radvalue;
                                   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
                                   }

                                   if(optvalue =='AN')
                                   {
                                        if(radvalue=='FN')
                                        {
                                            alert('Given Join Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                        
                        }
                        
                }
        }
     return true;

}
*/

function checkdate2()
{
//alert('check');
     if(maxtodate==null || maxtodate=='empty')
     {
            return true;
     }
     else
     {
        var fromdt=maxtodate;
        var todt=document.Hrm_TransJoinForm.txtDOJ.value;
        
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
            alert('Join Date Should be greater than or equal to '+maxtodate);
            //document.Hrm_TransJoinForm.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than  or equal to '+maxtodate);
                    //document.Hrm_TransJoinForm.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Join Date Should be greater than or equal to  '+maxtodate);
                           // document.Hrm_TransJoinForm.txtDateTo.focus();
                            return false;
                        }
                        else (fday==tday)
                        {
                                   var radvalue;
                                   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
                                   }
                                   else
                                   {
                                            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
                                   }
                                   if(maxsession =='AN')
                                   {
                                        if(radvalue=='FN')
                                        {
                                            alert('Given Join Date Session is not acceptable');
                                            return false;
                                        }
                                    }
                      }
                        
                }
        }
    }
        return true;

}


 function nullch()
{   
     
     if((document.Hrm_TransJoinForm.txtEmpId.value==null)||(document.Hrm_TransJoinForm.txtEmpId.value.length==0))
    {
        alert("Select Employee ID");
        document.Hrm_TransJoinForm.txtEmpId.focus();
        return false;
    }
    else if((document.Hrm_TransJoinForm.txtInstLocation.value==null)||(document.Hrm_TransJoinForm.txtInstLocation.value.length==0))
    {
        alert("Enter Institution Loaction");
        document.Hrm_TransJoinForm.txtInstLocation.focus();
        return false;
    }
    else if((document.Hrm_TransJoinForm.txtCourseName.value==null)||(document.Hrm_TransJoinForm.txtCourseName.value.length==0))
    {
        alert("Enter Course Name");
        document.Hrm_TransJoinForm.txtCourseName.focus();
        return false;
    }
    
    
     else if((document.Hrm_TransJoinForm.txtDOJ.value==null)||(document.Hrm_TransJoinForm.txtDOJ.value.length==0))
    {
        alert("Enter Date Of Joining");
        document.Hrm_TransJoinForm.txtDOJ.focus();
        return false;
    }
     
    var empstatus=document.Hrm_TransJoinForm.txtempstatus.value;
    var c1;
  /*  if(empstatus!='WKG')
        c1=checkdate1();
    else*/
        c1=checkdate2();
       if(c1==false)
       {
           document.Hrm_TransJoinForm.txtDOJ.focus();
            return false;
        }
    
     else if((document.Hrm_TransJoinForm.txtOffice_Id.value==null)||(document.Hrm_TransJoinForm.txtOffice_Id.value.length==0))
    {
        alert("Select the SR Controlling Office Name");
        document.Hrm_TransJoinForm.txtOffice_Id.focus();
        return false;
    }
    
    return true;
}





function doFunction(Command,param)
{
    //alert(Command);
   var OffCode=document.Hrm_TransJoinForm.txtOffId.value;
   //alert(OffCode);
   OffName=document.Hrm_TransJoinForm.txtOffName.value;
   var OffAddr=document.Hrm_TransJoinForm.txtOffAddr.value;
   var EmpId=document.Hrm_TransJoinForm.txtEmpId.value;
   
 // var Eid=document.Hrm_TransJoinForm.comEmpId[document.all.selectedIndex].value;
  var Eid=document.Hrm_TransJoinForm.comEmpId.value;
   //alert(Eid);
   
   var DOB=document.Hrm_TransJoinForm.txtDOB.value;
  
   var GPFNO=document.Hrm_TransJoinForm.txtGpfNo.value;
   
   var JRId=document.Hrm_TransJoinForm.txtDOJ.value;
   var a=JRId.split("/");
   
   JRId=a[2];
  //alert(JRId);
   
   var JR=document.Hrm_TransJoinForm.txtJR.value;
   var DOJ=document.Hrm_TransJoinForm.txtDOJ.value;
  // var design=document.Hrm_TransJoinForm.comDesign.value;
   
   //var postcount=document.Hrm_TransJoinForm.comPostTow.value;
  
   var rem=document.Hrm_TransJoinForm.txtRemarks.value;
   //var jrid=document.Hrm_TransJoinForm.txtJR.value;
   var radvalue;
   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
   }
   else
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
   }
    if(Command=="Add")
    {
        var flagg=nullch();
                
        if(flagg==true)
        {
            var url="../../../../../HrmTransSTUJoinServ.view?Command=dispDesign&txtOffId="+OffCode+"&JYear="+a[2];
                //alert(url);
                 var req=getTransport();
                  //alert("test");
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                 {
                     handleResponse(req);
                  }   
                      if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
             
            
       }
        }
        
         else  if(Command=="Load")
    {
      var  Office_Id;
      var   Dept_Id;
      
            // alert("load office->");
             if(param==true)
            {
            
                jobflag=true;
                Dept_Id=document.getElementById("txtDept_Id_work").value;
               Office_Id =document.getElementById("txtOffice_Id").value;
                 url="../../../../../HrmTransDPNJoinServ.view?Command=LoadSROffice&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
                 //alert(url);
              
                 var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
               
                processResponse(req);
              
                }
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                
            }
            
            
            else if(param==false)
            {
                 jobflag=false;
               Dept_Id= document.Hrm_TransJoinForm.txtDepId.value;
               Office_Id =document.Hrm_TransJoinForm.txtDepOffId.value;
                 url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id; 
               // alert(url)
                 
                 var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
               
                processResponse1(req);
              
                }
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
            }
               // alert(Office_Id +'   '+Dept_Id);
          
               // alert(url);
                
         }       
          

        
        else if(Command=="disp")
        {
            document.Hrm_TransJoinForm.txtDOB.focus();    
            var url="../../../../../HrmTransSTUJoinServ.view?Command=disp&txtEmpId="+EmpId;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                   if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
        
        else if(Command=="dispEmp")
        {
        
            if((document.Hrm_TransJoinForm.txtEmpId.value==null)||(document.Hrm_TransJoinForm.txtEmpId.value.length==0))
            {
                alert("Null Value not accepted...Select Employee ID");
                document.Hrm_TransJoinForm.txtEmpId.focus();
                return false;
            }
            //alert('test');
            clr();
            var url="../../../../../HrmTransSTUJoinServ.view?Command=dispEmp&comEmpId="+EmpId;
            // alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
            if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
        
         else if(Command=="txtDOB")
             {
                //alert("hereeeeeeeeeeeee");
                var month=DOB.substr(5,2);
                var year=DOB.substr(0,4);
                var day=DOB.substr(8,2);
                //alert("day"+day+"mon"+month+"yr"+year);
                document.Hrm_TransJoinForm.txtDOB.value=day+"/"+month+"/"+year;
             }
             else if(Command=="dispDesign")
             {
                
                
                var url="../../../../../HrmTransSTUJoinServ.view?Command=dispDesign&txtOffId="+OffCode+"&JYear="+a[2];
                //alert(url);
                 var req=getTransport();
                  //alert("test");
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                 {
                     handleResponse(req);
                  }   
                  if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
             
            
             }
        
   }     
   
   
   
   
        /*else if(Command=="ra")
        {
            if(document.Hrm_TransJoinForm.radFN.checked==true)
            {
            alert("yaaaaaaaaaaaaa");
           
            document.Hrm_TransJoinForm.radAN.checked==false;
            }
            else
            {
            alert("something");
            }
        }
        else if(Command=="rad")
        {
        if(document.Hrm_TransJoinForm.radAN.checked==true)
        {
            alert("nooooooooooo");
            document.Hrm_TransJoinForm.radFN.checked==false;
         }
        }*/
  
    
  function clr()
  {
        //document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtJRId.value="";
        //document.Hrm_TransJoinForm.txtDOC.value="";
        document.Hrm_TransJoinForm.txtDOJ.value="";
       // document.Hrm_TransJoinForm.cmbsgroup.selectedIndex=0;
       // document.Hrm_TransJoinForm.comDesign.selectedIndex=0;
       // document.Hrm_TransJoinForm.comPostTow.selectedIndex=0;
        document.Hrm_TransJoinForm.txtRemarks.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
  
  }
    
    
  


function handleResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
        
            if(Command=="Add")
            {
                //alert("Hellooooooooooo");
                addRow(baseResponse);
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
            else if(Command=="disp")
            {
                dispRow(baseResponse);
            }
            else if(Command=="dispEmp")
            {
                dispEmpRow(baseResponse);
            }
            
            else if(Command=="dispDesign")
            {
                dispDesignRow(baseResponse);
            }
        }
    }
}


function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
       /* alert("Records successfully saved");
        alert("Join Report Id generated...."+document.Hrm_TransJoinForm.txtJRId.value);
       document.Hrm_TransJoinForm.reset();
       */
       //alert('test');
        var b;
                var msg="Records successfully saved. \nJoin Report Id generated is "+document.Hrm_TransJoinForm.txtJRId.value;
                var head="Creation Of Joining Report Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
        
    }
    else if(flag=="failure1")
    {
            //alert("Unfreezed record is exist for this employee. Records r not saved");
             var b;
                var msg="Unfreezed record is exist for this employee. Records r not saved";
                var head="Creation Of Joining Report Details";
               b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
               
               b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
               b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
               b=b+"</table></td></tr></table></form>";
               var bid=document.getElementById("bodyid");
                try{ bid.innerHTML=b;
               }catch(e){
                bid.innerText="";
               }
    }
    else
    {
        //alert("Records r not saved");
         var b;
                var msg="Records are not saved";
                var head="Creation Of Joining Report Details";
       b="<form><table width=\"100%\"><tr><td><table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"100%\"><tr style=\"background-color: rgb(255,204,153);\"><th align=\"center\" colspan=\"2\" > <b> "+head+"</b> </th></tr>";
       
       b=b+"<tr style=\"background-color: rgb(255,255,225);\"><td  colspan=\"2\" >"+msg+"</td></tr>";
       b=b+"<tr style=\"background-color: rgb(255,204,153);\"><td align=\"center\" colspan=\"2\"> <input type=\"button\" id=\"Back\" name=\"Back\" value=\"Back\"     onclick=\"javascript:window.location.reload( false );\"></input><input type=\"button\" id=\"Exit\" name=\"Exit\" value=\"Exit\"     onclick=\"self.close();\"></input></td></tr>";
       b=b+"</table></td></tr></table></form>";
       var bid=document.getElementById("bodyid");
        try{ bid.innerHTML=b;
       }catch(e){
        bid.innerText="";
       }
    }
}

function dispRow(baseResponse)
{
    //alert("hellooooooooo");
       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       //alert(flag);
      var sename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
      //alert(sename);
     var sdob=baseResponse.getElementsByTagName("dob")[0].firstChild.nodeValue;
     //alert(sdob);
     var sgpfno=baseResponse.getElementsByTagName("gpfno")[0].firstChild.nodeValue;



    if(flag=="success")
    {
        document.Hrm_TransJoinForm.comEmpId.selectedText=sename;
        //document.Hrm_TransJoinForm.txtDOB.value=sdob;
        document.Hrm_TransJoinForm.txtGpfNo.value=sgpfno;

        alert("Records successfully inserted into DB");
        
    }
    else
    {
        alert("Records r not inserted");
    }
}




function dispEmpRow(baseResponse)
{
    //alert("hellooooooooo");
       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       //alert(flag);
     
    if(flag=="success")
    {
    //alert('hello');
         var sid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
      //alert(sename);
     var sdob=baseResponse.getElementsByTagName("dob")[0].firstChild.nodeValue;
     //alert(sdob);
     var sgpfno=baseResponse.getElementsByTagName("gpfno")[0].firstChild.nodeValue;

    var name=baseResponse.getElementsByTagName("name")[0].firstChild.nodeValue;
    
    maxtodate=baseResponse.getElementsByTagName("maxtodate")[0].firstChild.nodeValue;
    maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
   
   
   // document.Hrm_TransJoinForm.txtDOJ.value=maxtodate;
    //alert(maxtodate);
   
    var empstatus=baseResponse.getElementsByTagName("workingstatus")[0].firstChild.nodeValue;
    //  alert(empstatus); 
    document.Hrm_TransJoinForm.txtempstatus.value=empstatus;
  
    
        document.Hrm_TransJoinForm.txtEmpId.value=sid;
        if(sdob==0 || sdob=='Not Specified')
            sdob="";
        document.Hrm_TransJoinForm.txtDOB.value=sdob;
        if(sgpfno==0)
            sgpfno="";
        document.Hrm_TransJoinForm.txtGpfNo.value=sgpfno;
        document.Hrm_TransJoinForm.comEmpId.value=name;
        //alert("Records successfully inserted into DB");
        
         var inst_name=baseResponse.getElementsByTagName("inst_name")[0].firstChild.nodeValue;
        var inst_location=baseResponse.getElementsByTagName("inst_location")[0].firstChild.nodeValue;
        var course_name=baseResponse.getElementsByTagName("course_name")[0].firstChild.nodeValue;
         if(inst_name=='null')
                    inst_name='';
        if(inst_location=='null')
                    inst_location='';
        if(course_name=='null')
                    course_name='';
        document.Hrm_TransJoinForm.txtStuDoj.value=maxtodate;
        document.Hrm_TransJoinForm.txtInstName.value=inst_name;
        document.Hrm_TransJoinForm.txtInstLocation.value=inst_location;
        document.Hrm_TransJoinForm.txtCourseName.value=course_name;
        
    }
    else if(flag=="failure")
    {
        alert("Invalid Employee Id");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
     else if(flag=="failure3_1")
    {
        alert("This Employee  is already working.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
     else if(flag=="failure3_2")
    {
        alert("This Employee  has already retired status.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_3")
    {
        alert("This Employee  has dismissed status.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_4")
    {
        alert("This Employee  already got voluntary retirement.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_5")
    {
        alert("Can not give a Study Leave Joining for this employee. This employee is not releived for Study Leave.");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure3_6")
    {
        alert("This Employee  is on Deputation.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure2")
    {
        alert("This Employee already has an unfrezeed joining Record.\n So cann't create a new joining record for this Employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
    else if(flag=="failure1")
    {
        alert("This Employee does not have a post.\nCan not create a new join for this employee");
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        
        document.Hrm_TransJoinForm.txtEmpId.focus();
    }
}

function dispDesignRow(baseResponse)
{
    var OffCode=document.Hrm_TransJoinForm.txtOffId.value;
   OffName=document.Hrm_TransJoinForm.txtOffName.value;
   var OffAddr=document.Hrm_TransJoinForm.txtOffAddr.value;
   var EmpId=document.Hrm_TransJoinForm.txtEmpId.value;
  var Eid=document.Hrm_TransJoinForm.comEmpId.value;
   var DOB=document.Hrm_TransJoinForm.txtDOB.value;
   var GPFNO=document.Hrm_TransJoinForm.txtGpfNo.value;
   var JRId=document.Hrm_TransJoinForm.txtDOJ.value;
   var a=JRId.split("/");
   JRId=a[2];
   var JR=document.Hrm_TransJoinForm.txtJR.value;
   var DOJ=document.Hrm_TransJoinForm.txtDOJ.value;
  // var design=document.Hrm_TransJoinForm.comDesign.value;
   //var postcount=document.Hrm_TransJoinForm.comPostTow.value;
   var rem=document.Hrm_TransJoinForm.txtRemarks.value;
   var radvalue;
   if(document.Hrm_TransJoinForm.radFNAN[0].checked==true)
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[0].value;
   }
   else
   {
            radvalue=document.Hrm_TransJoinForm.radFNAN[1].value;
   }





    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var jj=baseResponse.getElementsByTagName("j")[0].firstChild.nodeValue;
    DOJ=document.Hrm_TransJoinForm.txtDOJ.value;
    OffCode=document.Hrm_TransJoinForm.txtOffId.value;
     var yer=DOJ.substr(6,4);
     //alert("doj.."+yer);
    if(flag=="success")
    {
       document.Hrm_TransJoinForm.txtJR.value=jj;
       document.Hrm_TransJoinForm.txtJRId.value=OffCode+"/"+yer+"/"+jj;
       var empstatus=document.Hrm_TransJoinForm.txtempstatus.value;
       
        var txtInstName=document.Hrm_TransJoinForm.txtInstName.value;
        var txtInstLocation=document.Hrm_TransJoinForm.txtInstLocation.value;
        var txtCourseName=document.Hrm_TransJoinForm.txtCourseName.value;
      var sr_cntrl_offid=document.Hrm_TransJoinForm.txtOffice_Id.value;
           //var url="../../../../../HrmTransSTUJoinServ.view?Command=Add&txtOffId="+OffCode+"&txtEmpId="+EmpId+"&txtJR="+jj+"&radFNAN="+radvalue+"&txtDOJ="+DOJ+"&comDesign="+design+"&comPostTow="+postcount+"&txtRemarks="+rem+"&JYear="+a[2]+"&empstatus="+empstatus;//+"&compdate="+compdate+"&compsession="+compsession;
           var url="../../../../../HrmTransSTUJoinServ.view?Command=Add&txtOffId="+OffCode+"&txtEmpId="+EmpId+"&txtJR="+jj+"&radFNAN="+radvalue+"&txtDOJ="+DOJ+"&txtRemarks="+rem+"&JYear="+a[2]+"&empstatus="+empstatus;
           url=url+"&txtInstName="+txtInstName+"&txtInstLocation="+txtInstLocation+"&txtCourseName="+txtCourseName;
            url=url+"&sr_cntrl_offid="+sr_cntrl_offid;
           //alert(url);
           var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    else
    {       
        document.Hrm_TransJoinForm.txtJR.value=jj;
        document.Hrm_TransJoinForm.txtJRId.value=OffCode+"/"+yer+"/"+jj;
        // alert("Programme ID already exists");
    }
}

function validate_date(formName, textName)
 {
        //alert("test");
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    eval('document.' + formName + '.' + textName + '.focus()');
                    //alert(eval);
                    eval('document.' + formName + '.' + textName + '.select()');
                    
                    return false;
                }
                
                 return true;  
                     
 }
 
 
 ///////////////////////////////////////////////////////////////////////
 
  function nullcheck()
            {
                  if((document.Hrm_TransJoinForm.txtOffId.value=="") || (document.Hrm_TransJoinForm.txtOffId.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.Hrm_TransJoinForm.txtOffId.value='';
                    document.Hrm_TransJoinForm.txtOffName.value='';
                    document.Hrm_TransJoinForm.txtOffAddr.value='';
                    document.Hrm_TransJoinForm.txtOffId.focus();
                    return false;
                    
                  }
                  
                  return true;
            }

/* 
 function callServer1(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    var c= nullcheck();
    if(c==true)
    if(command=="Load")
    {
        Office_Id=document.Hrm_TransJoinForm.txtOffId.value;
     
        //alert(Office_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+"TWAD";
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
        req.send(null);
    }
    
   
    function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert(req.responseText);
                    //  var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOffId");
                      
                      
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        document.Hrm_TransJoinForm.txtOffId.value='';
                        document.Hrm_TransJoinForm.txtOffName.value='';
                        document.Hrm_TransJoinForm.txtOffAddr.value='';
                       // document.Hrm_TransJoinForm.cmbsgroup.value=0;
                       // document.Hrm_TransJoinForm.cmbsgroup.selectedIndex=0;
                        document.Hrm_TransJoinForm.txtOffId.focus();
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
                       
                        document.Hrm_TransJoinForm.txtOffName.value='';
                        document.Hrm_TransJoinForm.txtOffAddr.value='';
                         var value=response.getElementsByTagName("options");
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                             // var district=tmpoption.getElementsByTagName("District")[0].firstChild.nodeValue;
                            
                              document.Hrm_TransJoinForm.txtOffName.value=name;
                              //document.Hrm_TransJoinForm.cmbDistrict.value=district;
                              if(officeAddress1!="null")
                              {
                              document.Hrm_TransJoinForm.txtOffAddr.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.Hrm_TransJoinForm.txtOffAddr.value+=" "+officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                   document.Hrm_TransJoinForm.txtOffAddr.value+=" "+officeAddress3;
                             }
                             
                             
                             
                          }
                          
                      }   
                     

            }
        }
    }
}

*/

function doParentEmp(emp)
{
document.Hrm_TransJoinForm.txtEmpId.value=emp;
doFunction('dispEmp','null');

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
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
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

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
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
           alert('Date format  should be (dd/mm/yyyy)');
           t.value="";
           //t.focus();
           return false
   }

}

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


function checkEID()
{
    if(document.Hrm_TransJoinForm.txtEmpId.value.length==0)
    {
    alert("select Employee Id");
    document.Hrm_TransJoinForm.comEmpId.value="";
    document.Hrm_TransJoinForm.txtDOB.value="";
    document.Hrm_TransJoinForm.txtGpfNo.value="";
    document.Hrm_TransJoinForm.txtEmpId.focus();
    return false;
    }
  return true;
}

/*function postcount()
{

document.Hrm_TransJoinForm.comPostTow.value=document.Hrm_TransJoinForm.comDesign.value;
}*/

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
function checkcurdt(t)
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
    return true;
    
}


/*function getDesignation1()
    {
   
        var type=document.Hrm_TransJoinForm.cmbsgroup.options[document.Hrm_TransJoinForm.cmbsgroup.selectedIndex].value;
       // alert("test" + type);
        if(type!=0)
        {
        
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.Hrm_TransJoinForm.comDesign.style.visibility="visible";
            loadOfficesByType1(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.Hrm_TransJoinForm.comDesign.style.visibility="hidden";
        }
    }
    
    function loadOfficesByType1(type)
    {
        //alert(type);
        startwaiting(document.Hrm_TransJoinForm) ;
        var type=document.Hrm_TransJoinForm.cmbsgroup.options[document.Hrm_TransJoinForm.cmbsgroup.selectedIndex].value;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation1(req);
        }
        req.send(null);
    }
    
    
    function  loadDesignation1(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                stopwaiting(document.Hrm_TransJoinForm) ;
                var des=document.getElementById("comDesign");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
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

*/



function processResponse(req)
{
//alert("hello processResponse");
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //var OfficeName=document.getElementById("txtOfficeName");
                      //var OfficeId=document.getElementById("txtOfficeId");
                    
                      var flag1=0;
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      

                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        
                               document.getElementById("txtOffice_Id").value='';
                               document.getElementById("txtOffice_Name").value='';
                                
                                
                                document.getElementById("txtOffice_Id").focus();
                            
                      }
                      else
                      {
                             
                              
                              var off_close=response.getElementsByTagName("off_close")[0].firstChild.nodeValue;  
                              var off_lev=response.getElementsByTagName("off_lev")[0].firstChild.nodeValue; 
                             // alert(off_close+off_lev);
                              if(off_close=='yes')
                                   {
                                       flag1=1;
                                       alert("Selected Office has Closed");
                                       document.getElementById("txtOffice_Id").value='';
                                       document.getElementById("txtOffice_Name").value='';
                                   }
                                   else if(off_lev=='invalid')
                                   {
                                       flag1=1;
                                       alert("Selected Office Level is not Valid");
                                       document.getElementById("txtOffice_Id").value='';
                                       document.getElementById("txtOffice_Name").value='';
                                   }
                              if(flag1==0)
                              {
                            //  alert("final")
                              var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                            //  alert(name);
                              document.getElementById("txtOffice_Name").value=name;
                               }      
                         }  
                    
                          
            }   
                     
       }
 }
    
function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       //alert(unicode);
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
     
function noEnter(e)
{
   
   isIE=document.all? 1:0
       
   keyEntry = !isIE? e.which:event.keyCode;
                  
   if(keyEntry=='38')
   {
     return false;
   }
}
