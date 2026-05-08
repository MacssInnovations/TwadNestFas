 var maxtodate;
var destid=0;
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

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
//alert('asdfasdfasdfkkk');
function servicepopup1()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null
    }
     startwaiting(document.frmEmployee);   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpJoiningPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
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
        winemp=null
    }
        
    winemp= window.open("EmpJoiningPopup.jsp","mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
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


function doParentJob(jobid,deptid)
{
    document.frmEmployee.dept_id.value=deptid;
    document.frmEmployee.txtOffice_Id.value=jobid;
   // callServer2('Load','null');
    loadOffice(jobid);
    return true

}
 /*function nullcheck()
{
      if((document.frmEmployee.txtOffId.value=="") || (document.frmEmployee.txtOffId.value.length<=0))
      {
        alert("Please Enter Office_Id");
        document.frmEmployee.txtOffId.value='';
        document.frmEmployee.txtOffName.value='';
        document.frmEmployee.txtOffAddr.value='';
        document.frmEmployee.txtOffId.focus();
        return false;
        
      }
      
      return true;
}
 

 function callServer2(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    var c= nullcheck();
    if(c==true)
    if(command=="Load")
    {
        Office_Id=document.frmEmployee.txtOffId.value;
     
        //alert(Office_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+"TWAD";
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
}*/
/*function checkdate()
{
//alert('check');
     var status=document.frmEmployee.txtempstatus.value;

     if(status=='WKG')
     {
            return true;
     }
     else
     {
        var fromdt=maxtodate;
        var todt=document.frmEmployee.txtDOC.value;
        
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
            //document.frmEmployee.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                     alert('Completed Date Should be greater than or equal to '+maxtodate);
                    //document.frmEmployee.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Completed Date Should be greater than or equal to '+maxtodate);
                           // document.frmEmployee.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
    }
        return true;

}


function checkdate1()
{
//alert('check');
    
        var fromdt=document.frmEmployee.txtDOC.value;
        var todt=document.frmEmployee.txtDOJ.value;
        
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
            //document.frmEmployee.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than or equal to Comleted Date');
                    //document.frmEmployee.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                            alert('Join Date Should be greater than or equal to Comleted Date');
                           // document.frmEmployee.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
     return true;

}*/


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
        var todt=document.frmEmployee.txtDOJ.value;
        
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
            //document.frmEmployee.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Join Date Should be greater than  or equal to '+maxtodate);
                    //document.frmEmployee.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Join Date Should be greater than or equal to  '+maxtodate);
                           // document.frmEmployee.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
    }
        return true;

}

function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callServer1('Load','null');

}
//***************************************************************************//
 function getDesignation1()
    {
   
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
       // alert("test" + type);
        if(type!=0)
        {
        
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.frmEmployee.cmbdes.style.visibility="visible";
            loadOfficesByType1(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.frmEmployee.cmbdes.style.visibility="hidden";
        }
    }
    
      function loadOfficesByType1(type)
    {
        //alert(type);
        startwaiting(document.frmEmployee) ;
        var type=document.frmEmployee.cmbsgroup.options[document.frmEmployee.cmbsgroup.selectedIndex].value;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation1(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
function  loadDesignation1(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                stopwaiting(document.frmEmployee) ;
                var des=document.getElementById("cmbdes");
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
                    //alert(destid);
                    document.frmEmployee.cmbdes.selectedIndexe=0;
                     if (destid==0)
                    {
                       document.frmEmployee.cmbdes.selectedIndexe=0;
                     }  
                     else
                     {
                             document.frmEmployee.cmbdes.value = destid;
                             try{
                              document.frmEmployee.cmbdes1.value=document.frmEmployee.cmbdes.options[document.frmEmployee.cmbdes.selectedIndex].text;
                              }catch(e){}
                           
                             
                      }
                      destid=0;
                
                }
        
        }
        
    }
}


function clearAll()
{
document.frmEmployee.txtEmpId1.value="";
document.frmEmployee.Employee_Name.value="";
document.frmEmployee.Date_Of_Birth.value="";
document.frmEmployee.Gpf_Number.value="";
document.frmEmployee.txtJRId.value="";
document.frmEmployee.txtJR.value="";
document.frmEmployee.txtDOJ.value="";
document.frmEmployee.txtDOJ1.value="";
document.frmEmployee.cmbsgroup.value="";
document.frmEmployee.cmbdes.value="";
document.frmEmployee.comPostTow.value="";
document.frmEmployee.txtRemarks.value="";
document.frmEmployee.txtcad.value="";
document.frmEmployee.txtOffice_Id.value="";
document.frmEmployee.txtOfficeName.value="";

//document.frmEmployee.butSub.disabled=true;
}








function callServer1(command,param)
 {
 
       if(command=="Load")
         { 
                var strEmpName=document.frmEmployee.txtEmpId1.value;
                document.frmEmployee.Employee_Name.value="";
                document.frmEmployee.Gpf_Number.value="";
                document.frmEmployee.Date_Of_Birth.value="";
           
                    startwaiting(document.frmEmployee) ;
                    url="../../../../../Employee_JoinReport_Servlet_New.view?command=Load&EName=" + strEmpName;
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
                
                
           if(command=="Update")
            {
                var flagg=nullch();
                if(flagg==true)
                {
                          //  var c=checkdate();
                            /*if(c==false)
                           {
                               document.frmEmployee.txtDOJ.focus();
                                return false;
                            }*/
                             var strEmpName=document.frmEmployee.txtEmpId1.value;
                             var strJdate=document.frmEmployee.txtDOJ.value;
                             var a=strJdate.split("/");
                             var strYear=a[2];
                             //alert('strYear...'+strYear);
                             var strOffId=document.frmEmployee.txtOffice_Id.value;
                             //alert(strOffId);
                             var strJoinId=document.frmEmployee.txtJRId.value;
                             var txtdoj=document.frmEmployee.txtDOJ.value;
                             //alert("new date..."+txtdoj);
                             
                             var grade;
                             
                              if(document.frmEmployee.Office_Grade[0].checked==true)
                               {
                                 grade=document.frmEmployee.Office_Grade[0].value;
                               }
                              else if(document.frmEmployee.Office_Grade[1].checked==true)
                               {
                                 grade=document.frmEmployee.Office_Grade[1].value;
                               }
                              else if(document.frmEmployee.Office_Grade[2].checked==true)
                               {
                                 grade=document.frmEmployee.Office_Grade[2].value; 
                                }
                              else if(document.frmEmployee.Office_Grade[3].checked==true)
                              {
                                grade=document.frmEmployee.Office_Grade[3].value; 
                              }
   
                             
                             var radvalue;
                           if(document.frmEmployee.radFNAN[0].checked==true)
                           {
                                    radvalue=document.frmEmployee.radFNAN[0].value;
                           }
                           else
                           {
                                    radvalue=document.frmEmployee.radFNAN[1].value;
                           }
                         var design=document.frmEmployee.cmbdes.value;
                         var postcount=document.frmEmployee.comPostTow.value;
                         var rem=document.frmEmployee.txtRemarks.value;
                         
                         var empstatus=document.frmEmployee.txtempstatus.value;
                          
                         var jyear=document.frmEmployee.jyear.value;
                         
                         var strOffId1=document.frmEmployee.txtOffId.value;
                         var dept_id=document.frmEmployee.dept_id.value;
                         //alert("jyear..."+jyear);
                        /* var compdate=document.frmEmployee.txtDOC.value;
                         var compsession;
                         if(document.frmEmployee.optFNAN[0].checked==true)
                           {
                                    compsession=document.frmEmployee.optFNAN[0].value;
                           }
                           else
                           {
                                    compsession=document.frmEmployee.optFNAN[1].value;
                           }*/
    var optjoin='O';
   var currentoffice;
   if(document.frmEmployee.optjoin)
    {
           if(document.frmEmployee.optjoin[0].checked==true)
            {
                optjoin=document.frmEmployee.optjoin[0].value;
                currentoffice=strOffId;
                
            }
            else
            {
                    optjoin=document.frmEmployee.optjoin[1].value;
                    currentoffice=document.frmEmployee.cmbsubdivision.value;
            }
    }
    else
    {
         //////////////////////seee here arun dont search
    
            currentoffice=strOffId;
             var wing=0;
            try{
            wing=document.frmEmployee.cmbWing.value;
            }catch(e){}
            }
               var url="../../../../../Employee_JoinReport_Servlet_New.view?command=Update&txtOffId="+strOffId1+"&dept_id="+dept_id+
               "&txtEmpId="+strEmpName+"&txtjoin_yr="+strYear+"&txtDOJ="+txtdoj+"&radFNAN="+radvalue+
               "&cmbdes="+design+"&txtgrade="+grade+"&comPostTow="+postcount+"&txtRemarks="+rem+"&JoinId="+strJoinId+
               "&empstatus="+empstatus;//+"&compdate="+compdate+"&compsession="+compsession;
               url=url+"&optjoin="+optjoin+"&currentoffice="+currentoffice+"&wing="+wing+"&jyear="+jyear;
                         
                         //alert(url);
                         var req=getTransport();
                       //  alert(url);
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
        
        
        else if(command=="Delete")
        {
                    var strEmpName=document.frmEmployee.txtEmpId1.value;
                     var strJdate=document.frmEmployee.txtDOJ.value;
                     var a=strJdate.split("/");
                     var strYear=a[2];
                     var strOffId=document.frmEmployee.txtOffice_Id.value;
                     var strJoinId=document.frmEmployee.txtJRId.value;
                     
           if(confirm("Do You Really want to Delete it"))
            {
            startwaiting(document.frmEmployee) ;
            url="../../../../../Employee_JoinReport_Servlet_New.view?command=Delete&txtEmpId="+strEmpName+"&JoinId="+strJoinId+"&txtOffId="+strOffId+"&txtDOJ="+strYear;
            //req.abort();
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
        }
 
 }
 
 function handleResponse(req)
    {   
    if(req.readyState==4)
        {
          if(req.status==200)
          {      
          stopwaiting(document.frmEmployee) ;
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             //alert(baseResponse);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
           //  alert(command);
             if(command=="Load")
              {//alert("here");
              loadRow(baseResponse);
              }
              else if(command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="Delete")
              {
              deleteRow(baseResponse);
              }
              
          }
        }
  }
  
  function loadRow(baseResponse)
 {
         var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                          var empid=baseResponse.getElementsByTagName("Emp_Id");
                          
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EmpName"); 
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf");
                              var jdate=baseResponse.getElementsByTagName("JDate");
                               var dtbirth=baseResponse.getElementsByTagName("Dtofbirth");
                               var cadre=baseResponse.getElementsByTagName("Cadre");
                                var joinid=baseResponse.getElementsByTagName("JoinId");
                                 var noon=baseResponse.getElementsByTagName("Noon");
                                  var desigid=baseResponse.getElementsByTagName("DesigId");
                                  var offgrade=baseResponse.getElementsByTagName("Grade");
                                   var postid=baseResponse.getElementsByTagName("PostId");
                                    var remarks=baseResponse.getElementsByTagName("Remarks");
                                   var sgroup=baseResponse.getElementsByTagName("ServGroup");
                                   var optjoin=baseResponse.getElementsByTagName("optjoin");
                                   var subdivoffid=baseResponse.getElementsByTagName("subdivoffid");
                                   var offnme=baseResponse.getElementsByTagName("Off_Name");
                                    var dept_id=baseResponse.getElementsByTagName("dept_id");                                    
                                   var tadm=baseResponse.getElementsByTagName("admin");
                                   var j_year=baseResponse.getElementsByTagName("join_year");
                                   
                              //var ProcId=baseResponse.getElementsByTagName("ProcId");
                            
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tdtbirth=dtbirth.item(j).firstChild.nodeValue;
                            if(tdtbirth==0 || tdtbirth=='Not Specified')
                            tdtbirth="";
                            
                            var tjdate=jdate.item(j).firstChild.nodeValue;
                            
                            var tcadre=cadre.item(j).firstChild.nodeValue;
                            if(tcadre=='null')
                            tcadre="";
                            var tempgpf=empgpf.item(j).firstChild.nodeValue;
                            var tjoinid=joinid.item(j).firstChild.nodeValue;
                            var tdesignId=desigid.item(j).firstChild.nodeValue;
                            var tnoon=noon.item(j).firstChild.nodeValue;
                            var tpostid=postid.item(j).firstChild.nodeValue;
                            var tremarks=remarks.item(j).firstChild.nodeValue;
                            var servgrp=sgroup.item(j).firstChild.nodeValue;
                            var toptjoin=optjoin.item(j).firstChild.nodeValue;
                            var tsubdivoffid=subdivoffid.item(j).firstChild.nodeValue;
                             var toffname=offnme.item(j).firstChild.nodeValue;
                            var dept=dept_id.item(j).firstChild.nodeValue;
                            var tgrade=offgrade.item(j).firstChild.nodeValue;
                            var adm=tadm.item(j).firstChild.nodeValue;
                            var jyear=j_year.item(j).firstChild.nodeValue;
                            //alert("jyear..."+jyear);
                            
                            
                            
                            document.frmEmployee.txtOffice_Id.value=tsubdivoffid;
                            document.frmEmployee.txtcad.value=tcadre;
                            document.frmEmployee.txtOfficeName.value=toffname;
                            document.frmEmployee.dept_id.value=dept;
                            document.frmEmployee.jyear.value=jyear;
                            
                            
                            
                            document.frmEmployee.Employee_Name.value=tempname;
                            document.frmEmployee.Date_Of_Birth.value=tdtbirth;
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                            
                            document.frmEmployee.txtJRId.value=tjoinid;
                            document.frmEmployee.txtDOJ.value=tjdate;
                            /*document.frmEmployee.cmbsgroup.value=tdesignId;
                            document.frmEmployee.comPostTow1.value=tpostid;
                            document.frmEmployee.txtRemarks.value=tremarks;*/
                            
                            
                            if(tgrade=='Normal')
                            {
                              document.frmEmployee.Office_Grade[0].checked=true;
                            }
                           else if(tgrade=='Selection')
                           {
                             document.frmEmployee.Office_Grade[1].checked=true;
                           }
                          else if(tgrade=='Special')
                           {
                             document.frmEmployee.Office_Grade[2].checked=true;
                           }
                           else
                           {
                             document.frmEmployee.Office_Grade[3].checked=true;
                           }
                           
                           
                           
                           
                           //if(adm=='YES')   
                           //{
                           /*
                           if(tcadre=='CE' || tcadre=='SE' || tcadre=='EE' || adm=='YES')
                           {
                            document.frmEmployee.txtOffice_Id.disabled=false;
                            document.frmEmployee.off_img.disabled=false;   
       
                           }   
                           //}
    
                          //else if(adm=='NO')
                          else
                          {
                           document.frmEmployee.txtOffice_Id.disabled=true;
                           document.frmEmployee.off_img.disabled=true;
      
                          document.frmEmployee.txtOffice_Id.value=document.frmEmployee.txtOffId.value;
                          document.frmEmployee.txtOfficeName.value=document.frmEmployee.txtOffName.value;      
      
                          }
                            */
                            /*
                            document.frmEmployee.Employee_Name.value=tempname;
                            document.frmEmployee.Date_Of_Birth.value=tdtbirth;
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                            
                            document.frmEmployee.txtJRId.value=tjoinid;
                            document.frmEmployee.txtempstatus.value=tjdate;
                            document.frmEmployee.cmbdes1.value=tdesignId;
                            document.frmEmployee.comPostTow1.value=tpostid;
                            document.frmEmployee.txtRemarks.value=tremarks;
                            */
                            
                            
                            //alert(document.frmEmployee.txtOffice_Id.value);
                            
                           try{
                            var wing=baseResponse.getElementsByTagName("wing");
                            
                            var wingid=wing.item(j).firstChild.nodeValue;
                           // alert(wingid);
                            document.frmEmployee.cmbWing.value=wingid;
                            }catch(e){}
                             //var ProcessId=ProcId.item(j).firstChild.nodeValue;
                            //alert('test');
                             maxtodate=baseResponse.getElementsByTagName("maxtodate")[0].firstChild.nodeValue;
                            //alert(maxtodate);
                            //alert(toptjoin);
                            if(toptjoin=='S')
                            {
                                    if(document.getElementById("divsubdivision"))
                                    {
                                    var id=document.getElementById("divsubdivision");
                                    id.style.display='block';
                                    document.frmEmployee.optjoin[1].checked=true;
                                    
                                    document.frmEmployee.cmbsubdivision.value=tsubdivoffid;
                                    }
                            }
                            else 
                            {
                                    //document.frmEmployee.optjoin[0].checked=true;
                                    if(document.getElementById("divsubdivision"))
                                    {
                                    var id=document.getElementById("divsubdivision");
                                    id.style.display='none';
                                    }
                            }
                            
                            
                             var empstatus=baseResponse.getElementsByTagName("workingstatus")[0].firstChild.nodeValue;
                            //  var compsession=baseResponse.getElementsByTagName("compsession")[0].firstChild.nodeValue;
                             if(empstatus=='null')
                             empstatus="";
                            //  alert(empstatus); 
                            document.frmEmployee.txtempstatus.value=empstatus;
                          /*  if(empstatus!='WKG')
                            {
                                   //alert(empstatus);
                                   var id=document.getElementById("drcompdate");
                                   id.style.display="block";
                                   var msg="";
                                   
                                    id=document.getElementById("divcompleted");
                                    // alert(id);
                                         if(empstatus=='DPN')
                                            {
                                                msg="Deputation Completed Date";  
                                            }
                                            else  if(empstatus=='LLV')
                                            {
                                                 msg="Long Leave Completed Date";    
                                            }
                                             else  if(empstatus=='SUS')
                                            {
                                                msg="Suspension Completed Date";     
                                            }
                                              else  if(empstatus=='ABS')
                                            {
                                                  msg="Absconded Completed Date";    
                                            } 
                                            else  if(empstatus=='TRT')
                                            {
                                                  msg="Transit Completed Date";    
                                            }
                                  
                                    if(id.innerText !='undefined'  && id.innerText !=null  )
                                                id.innerText=msg;
                                            else 
                                                id.innerHTML=msg;
                                     var compdate=baseResponse.getElementsByTagName("completeddate")[0].firstChild.nodeValue;
                                     document.frmEmployee.txtDOC.value=compdate;
                                     
                                      if(compsession=='FN')
                                         document.frmEmployee.optFNAN[0].checked=true;
                                     else
                                         document.frmEmployee.optFNAN[1].checked=true;
                           
                           }
                            else
                            {
                                   // alert('else part');
                                    var id=document.getElementById("drcompdate");
                                    id.style.display="none";
                            }
                           */ 
                            
                            if(tjdate==0)
                            {
                               document.frmEmployee.txtDOJ.value="";
                             }
                             else
                               {
                                document.frmEmployee.txtDOJ.value=tjdate;
                                document.frmEmployee.txtDOJ1.value=tjdate;
                                  //document.frmEmployee.txtDOJ.disabled=true;
                               }
                            
                             if(tdtbirth==0 || tdtbirth=='Not Specified')
                                    tdtbirth="";
                            document.frmEmployee.Date_Of_Birth.value=tdtbirth;
                            document.frmEmployee.txtJRId.value=tjoinid;
                            document.frmEmployee.Employee_Name.value=tempname;
                             if(tempgpf==0)
                                tempgpf="";
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                            
                             document.frmEmployee.comPostTow.value=tpostid;
                            try
                            {
                             document.frmEmployee.comPostTow1.value=document.frmEmployee.comPostTow.options[document.frmEmployee.comPostTow.selectedIndex].text;
                            }catch(e){}
                             if(tremarks==null || tremarks=='null' )
                                tremarks="";
                            document.frmEmployee.txtRemarks.value=tremarks;
                            //document.frmEmployee.comProcFlowId.value=ProcessId;
                            
                            
                             // alert(ProcessId);
                              if(servgrp==0)
                               servgrp=0;
                            else   
                             document.frmEmployee.cmbsgroup.value =servgrp;
                              destid=tdesignId;
                     getDesignation1();
                  
                   
                   
                   
                               
                               if(tnoon=='FN')
                                 document.frmEmployee.radFNAN[0].checked=true;
                             else
                                 document.frmEmployee.radFNAN[1].checked=true;
                              
                          }
                          
                       }
                                      
                     else if(flag=="NoRecord")
                     {
                     
                             var empname=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;  
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                               var dtbirth=baseResponse.getElementsByTagName("Dtofbirth")[0].firstChild.nodeValue; 
                                   if(dtbirth=="")
                                        dtbirth="0";
                                   else
                                       dtbirth=dtbirth;
                            document.frmEmployee.Date_Of_Birth.value=dtbirth;
                            document.frmEmployee.Employee_Name.value=empname;
                            document.frmEmployee.Gpf_Number.value=empgpf;
                     }
                     else if(flag=="NoValue")
                    {
                      alert("Employee Id Does not Exists - Create an Id for the Employee First");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                   else if(flag=="failure")
                    {
                        alert("Invalid Employee Id");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else if(flag=="failure2")
                    {
                        //alert("This Employee does not have joining Record.\n");
                        alert("This Employee Record is Freezed which could not be Deleted.\n");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                    else if(flag=="failure3")
                    {
                        alert("This Employee does not have TWAD joining Record.\n");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                    else if(flag=="failure1")
                    {
                        alert("This Employee does not have a post.");
                        document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else
                     {
                      alert("Record does not exist.");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                     }
}     


function updateRow(baseResponse)
{ 
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                   // alert("Records are updated successfully");
                   // clearAll();
                    var b;
                var msg="Records are updated successfully";
                var head="Edit Joining Report Details";
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
                   // alert("Failed to update");
                     var b;
                    var msg="Records are not updated successfully";
                    var head="Edit Joining Report Details";
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

//Date validation
function validate_date(formName, textName)
 {
 
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
                    eval('document.' + formName + '.' + textName + '.select()');
                    
                    return false;
                }
                
                 return true;  
                     
 }
function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
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
     
function toFocus()
{
 //alert("test");
  //var FirstField=document.frmEmployee.txtEmpId1.value;
  if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value<=0))
  {
     alert("Please Select Employee Id");
     document.frmEmployee.txtEmpId1.focus();
     return false;
  }
  return true;
   
}
     
//This Coding for Date Validation and Checking     
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

function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
        var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
          t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
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
        
        }
        if( f==true)
        {
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
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
           
            return false
    }
    
}

function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                      //alert("Records are Deleted"); 
                      // clearAll();
                            var b;
                            var msg="Records are deleted successfully";
                            var head="Delete Joining Report Details";
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
                     // alert("Unable to Delete");
                        var b;
                        var msg="Records are not deleted successfully";
                        var head="Delete Joining Report Details";
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
  
  
  
  function nullch()
{   
     
     if((document.frmEmployee.txtEmpId1.value==null)||(document.frmEmployee.txtEmpId1.value.length==0))
    {
        alert("Select Employee ID");
        document.frmEmployee.txtEmpId1.focus();
        return false;
    }
    
    
   /*  else if((document.frmEmployee.txtDOC.value==null)||(document.frmEmployee.txtDOC.value.length==0))
    {
        alert("Enter Completed Date");
        document.frmEmployee.txtDOC.focus();
        return false;
    }
    
    var c=checkdate();
       if(c==false)
       {
           document.frmEmployee.txtDOC.focus();
            return false;
        }
    
    */
    else if((document.frmEmployee.txtDOJ.value==null)||(document.frmEmployee.txtDOJ.value.length==0))
    {
        alert("Enter Date Of Joining");
        document.frmEmployee.txtDOJ.focus();
        return false;
    }
    var empstatus=document.frmEmployee.txtempstatus.value;
    var c1;
   /* if(empstatus!='WKG')
        c1=checkdate1();
    else*/
        c1=checkdate2();
       if(c1==false)
       {
           document.frmEmployee.txtDOJ.focus();
            return false;
        }
    if(document.frmEmployee.optjoin)
    {
            if(document.frmEmployee.optjoin[1].checked==true)
            {
               if((document.frmEmployee.cmbsubdivision.value==null)||(document.frmEmployee.cmbsubdivision.value.length==0))
                {
                    alert("Select SubDivision");
                    document.frmEmployee.cmbsubdivision.focus();
                    return false;
                }
            }
    }
     if((document.frmEmployee.cmbsgroup.value=="0")||(document.frmEmployee.cmbsgroup.value.length==0))
    {
        alert("Select Service Group");
       document.frmEmployee.cmbsgroup.focus();
        return false;
    }
     else if((document.frmEmployee.cmbdes.value=="0")||(document.frmEmployee.cmbdes.value.length==0))
    {
        alert("Select Designation");
        try{
        document.frmEmployee.cmbdes.focus();
        }catch(e){document.frmEmployee.cmbsgroup.focus();}
        return false;
    }
    
    else if((document.frmEmployee.comPostTow.value==null)||(document.frmEmployee.comPostTow.value.length==0))
    {
        alert("Select Post towards");
        document.frmEmployee.comPostTow.focus();
        return false;
    }    
    
  /*   if(document.frmEmployee.cmbWing)
    {
             if((document.frmEmployee.cmbWing.value==null)||(document.frmEmployee.cmbWing.value==0))
            {
                alert("Select Wing");
                document.frmEmployee.cmbWing.focus();
                return false;
            }
    }*/
    
   /* else if((document.frmEmployee.txtRemarks.value==null)||(document.frmEmployee.txtRemarks.value.length==0))
    {
        alert("Null Value not accepted");
        document.frmEmployee.txtRemarks.focus();
        return false;
    }*/
    
    return true;
}


function postcount()
{
document.frmEmployee.comPostTow.value=document.frmEmployee.cmbdes.value;
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

function subdivisioncheck()
{
    if(document.frmEmployee.optjoin[0].checked==true)
    {
        var id=document.getElementById("divsubdivision");
        id.style.display='none';
    }
    else
    {
            var id=document.getElementById("divsubdivision");
            id.style.display='block';
    }
}

function loadOffice(id)
{   
  //alert('loadoffice');
  if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.frmEmployee.txtOffice_Id.focus();
    }
    else
    {
            startwaiting(document.frmEmployee) ;
            var dept_id=document.frmEmployee.dept_id.value;
            if(dept_id=="")
                dept_id="TWAD";
            if(dept_id!="TWAD")
            {
                var url="../../../../../ServletLoadOfficeDetails.con?command=OtherOffice&ID="+id+"&DeptId="+dept_id;                    
            }
            else
            {
                var url="../../../../../ServletLoadOfficeDetails.con?command=TwadOffice&ID="+id;            
            }
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               LoadOfficeDetails(req);             
            }
            req.send(null);        
    }
}

function LoadOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmEmployee) ;
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var off_name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                    /*var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;*/
                    
                    document.frmEmployee.txtOfficeName.value=off_name;
                    
                    //alert(level);
                    /*
                    if(level=="H.O.")
                    {
                        alert("As this Office controls some other offices,so this Office cannot be Attached");
                        document.Hrm_TransJoinForm.txtOffice_Id.value="";
                        document.Hrm_TransJoinForm.txtOfficeName.value="";
                        document.Hrm_TransJoinForm.txtOfficeType.value="";
                        document.Hrm_TransJoinForm.txtOfficeAddress.value="";
                        document.Hrm_TransJoinForm.txtExistOfficeId.value="";
                        document.Hrm_TransJoinForm.txtExistOfficeName.value="";
                        document.Hrm_TransJoinForm.txtExistOfficeAddress.value="";
                        document.Hrm_TransJoinForm.txtOffice_Id.focus();
                    }
                    else
                    {
                    document.Hrm_TransJoinForm.txtOfficeName.value=name.firstChild.nodeValue;
                    if(level=="Division" || level=="Sub-Division")
                        document.Hrm_TransJoinForm.txtOfficeType.value=type;
                    else
                        document.Hrm_TransJoinForm.txtOfficeType.value="Office level : " + level;
                    
                   if(address1!="null")
                    {
                    document.Hrm_TransJoinForm.txtOfficeAddress.value=address1+"\n";
                    }
                    else
                    {
                    //document.Hrm_TransJoinForm.txtOfficeAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+address2+"\n";
                    }
                    else
                    {
                      //  document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+city+"\n";
                    }
                    else
                    {
                       // document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+"\n"+"";
                    }
                    if(district!="null")
                    {
                        document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+district+"\n";
                    }
                    else
                    {
                       // document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+"\n"+"";
                    }
                    if(pincode!=0)
                    {
                        if(pincode!="null")
                        {
                            document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value+pincode;
                        }
                    }
                    else
                    {
                        //document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value;
                    }*/
                    
                    //document.Hrm_TransJoinForm.txtOfficeAddress.value=document.Hrm_TransJoinForm.txtOfficeAddress.value;
                    
                    //document.Hrm_TransJoinForm.txtAttachedOfficeID.focus();
                    //OfficeLevel();
                    }
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess); 
                    document.frmEmployee.txtOfficeName.value="";
                    //document.Hrm_TransJoinForm.txtOfficeType.value="";
                    //document.Hrm_TransJoinForm.txtOfficeAddress.value="";
                } 
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

