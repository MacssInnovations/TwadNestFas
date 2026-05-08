
//alert('hai');
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




function  selectoption1()
{
//alert('test');
    if(document.frmSR_Based_Query.optselect[0].checked)
    {
        var id=document.getElementById("divdest");
        id.style.display='block';
        var id=document.getElementById("divrank");
        id.style.display='none';
    }
    else
    {
         var id=document.getElementById("divdest");
        id.style.display='none';
        var id=document.getElementById("divrank");
        id.style.display='block';
    
    }

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
        
    winjob= window.open("../../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_for_SR","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(200,200);  
 
    winjob.focus();
    
    
}

function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed)
  {
        if((document.frmSR_Based_Query.txtDept_Id.value=='TWAD'))
        {
         //winjob.officeSelection(true,true,true,false);
         winjob.officeSelection(true,true,true,false,true);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.frmSR_Based_Query.txtDept_Id.value;
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
    document.frmSR_Based_Query.txtOffice_Id.value=jobid;
    document.frmSR_Based_Query.txtDept_Id.value=deptid;
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

function offclr()
{
    document.frmSR_Based_Query.txtOffice_Id.value='';
    document.frmSR_Based_Query.txtOffice_Name.value='';
    document.frmSR_Based_Query.txtOffice_Address1.value='';
   
}

function checkdeptsel()
{
     if((document.frmSR_Based_Query.txtDept_Id.value==0)||(document.frmSR_Based_Query.txtDept_Id.value.length==0))
        {
                offclr();
        }
}



function checkdeptid()
{
    if(document.frmSR_Based_Query.txtDept_Id.value==null || document.frmSR_Based_Query.txtDept_Id.value.length==0)
    {
            alert('Select Department Id');
            document.frmSR_Based_Query.txtDept_Id.focus();
            return false;
    }
   
    return true;

}



 function nullcheck()
            {
                  if((document.frmSR_Based_Query.txtOffice_Id.value=="") || (document.frmSR_Based_Query.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.frmSR_Based_Query.txtOffice_Id.value='';
                    document.frmSR_Based_Query.txtOffice_Name.value='';
                    document.frmSR_Based_Query.txtOffice_Address1.value='';
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
          //document.frmSR_Based_Query.txtSNo.focus();
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     

function callServer1(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    var c= nullcheck();
   
    if(c==true)
    if(command=="Load")
    {
        Office_Id=document.frmSR_Based_Query.txtOffice_Id.value;
      var   Dept_Id=document.frmSR_Based_Query.txtDept_Id.value;
        
        url="../../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
        var req=getTransport();
        //alert(url);
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
                      var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOfficeId");
                      
                      
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        document.frmSR_Based_Query.txtOffice_Id.value='';
                       document.frmSR_Based_Query.txtOffice_Name.value='';
                        document.frmSR_Based_Query.txtOffice_Address1.value='';
                        document.frmSR_Based_Query.txtOffice_Id.focus();
                      }
                      else
                      {
                            document.frmSR_Based_Query.txtOffice_Name.value='';
                        document.frmSR_Based_Query.txtOffice_Address1.value='';
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
                              
                              var district='';
                              try{
                               district=tmpoption.getElementsByTagName("district_name")[0].firstChild.nodeValue;
                              }catch(e){}
                            
                              document.frmSR_Based_Query.txtOffice_Name.value=name;
                              
                              if(officeAddress1=='null')
                                officeAddress1='';
                             if(officeAddress2=='null')
                                officeAddress2='';
                            if(officeAddress3=='null')
                                officeAddress3='';
                            
                                var fulladd=officeAddress1;
                                fulladd=fulladd+'\n'+officeAddress2;
                                fulladd=fulladd+'\n'+officeAddress3;
                                 
                                 try{
                                 if(district=='null')
                                        district='';
                                fulladd=fulladd+'\n'+district;
                                }catch(e){}
                               
                               document.frmSR_Based_Query.txtOffice_Address1.value=fulladd;
                           //  alert('test');
                          }
                          
                      }   
                     

            }
        }
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
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
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
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
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


//*********************  sevice group  selection  ************************
function getDesignation()
    {
        var type=document.frmSR_Based_Query.cmbsgroup.options[document.frmSR_Based_Query.cmbsgroup.selectedIndex].value;
       
        if(type!=0)
        {
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
        var type=document.frmSR_Based_Query.cmbsgroup.options[document.frmSR_Based_Query.cmbsgroup.selectedIndex].value;
        startwaiting(document.frmSR_Based_Query) ;
       var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
        req.send(null);
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
                
                 stopwaiting(document.frmSR_Based_Query);
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


function checkGroup()
    {
        
        var type=document.frmSR_Based_Query.cmbsgroup.options[document.frmSR_Based_Query.cmbsgroup.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.frmSR_Based_Query.cmbsgroup.focus();
            return false;
        }
    }


function notNull()
{

        /*if((document.frmSR_Based_Query.txtDept_Id.value==0)||(document.frmSR_Based_Query.txtDept_Id.value.length==0))
        {
            alert("Select Department Id");
            document.frmSR_Based_Query.txtDept_Id.focus();
            return false;
        }*/
        if((document.frmSR_Based_Query.txtOffice_Id.value==0)||(document.frmSR_Based_Query.txtOffice_Id.value.length==0))
        {
            alert("Select Office Id");
            document.frmSR_Based_Query.txtOffice_Id.focus();
            return false;
        }
      if((document.frmSR_Based_Query.txtDateFrom.value==null)||(document.frmSR_Based_Query.txtDateFrom.value.length==0))
        {
            alert("Enter a value for Date from ");
            document.frmSR_Based_Query.txtDateFrom.focus();
            return false;
        }
         if((document.frmSR_Based_Query.txtDateTo.value==null)||(document.frmSR_Based_Query.txtDateTo.value.length==0))
        {
            alert("Enter a value for Date To");
            document.frmSR_Based_Query.txtDateTo.focus();
            return false;
        }
       
       var c=checkdate();
       if(c==false)
       {
            document.frmSR_Based_Query.txtDateFrom.focus();
            return false;
        }
       
       if(document.frmSR_Based_Query.optselect[0].checked)
    { 
            
               if((document.frmSR_Based_Query.cmbDesignation.value==0)||(document.frmSR_Based_Query.cmbDesignation.value.length==0))
                {
                    alert("Seelct a Designation");
                    if((document.frmSR_Based_Query.cmbsgroup.value=="0")||(document.frmSR_Based_Query.cmbsgroup.value.length==0))
                    {
                        document.frmSR_Based_Query.cmbsgroup.focus();
                    }
                    else
                    {
                        document.frmSR_Based_Query.cmbDesignation.focus();
                    }
                    return false;
                }
       }
       else
       {
       
             if((document.frmSR_Based_Query.cmbRank.value==0)||(document.frmSR_Based_Query.cmbRank.value.length==0))
                {
                    alert("Seelct a Designation");
                    if((document.frmSR_Based_Query.cmbsgroup1.value=="0")||(document.frmSR_Based_Query.cmbsgroup1.value.length==0))
                    {
                        document.frmSR_Based_Query.cmbsgroup1.focus();
                    }
                    else
                    {
                        document.frmSR_Based_Query.cmbRank.focus();
                    }
                    return false;
                }
       
       }
    return true;

}


function btnsubmit()
{
        var c=notNull();
        if(c==true)
        {
          //  alert(true);
          document.frmSR_Based_Query.action="../../../../../../SR_Based_QueryServ.rep";
          document.frmSR_Based_Query.method="POST";
          document.frmSR_Based_Query.submit();
            return true;
        }
        else
        {
                return false;
        }
}

function checkdate()
{
//alert('check');
        var fromdt=document.frmSR_Based_Query.txtDateFrom.value;
        var todt=document.frmSR_Based_Query.txtDateTo.value;
        
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
            //document.frmSR_Based_Query.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.frmSR_Based_Query.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.frmSR_Based_Query.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}





//*********************  sevice group  selection  ************************
function getDesignation1()
    {
        var type=document.frmSR_Based_Query.cmbsgroup1.options[document.frmSR_Based_Query.cmbsgroup1.selectedIndex].value;
       
        if(type!=0)
        {
            loadOfficesByType1(type);
        }
        else
        {
           var des=document.getElementById("cmbRank");
           des.innerHTML='';
        }
    }
    
    function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.frmSR_Based_Query.cmbsgroup1.options[document.frmSR_Based_Query.cmbsgroup1.selectedIndex].value;
        startwaiting(document.frmSR_Based_Query) ;
       var url="../../../../../../SR_Based_QueryServ.rep?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        //alert(url);
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
                var des=document.getElementById("cmbRank");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.frmSR_Based_Query);
                if(flag=="failure")
                {
                    alert("No Rank exists under this level");
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
                    option.text="--Select Rank--";
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


function checkGroup1()
    {
        
        var type=document.frmSR_Based_Query.cmbsgroup1.options[document.frmSR_Based_Query.cmbsgroup1.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.frmSR_Based_Query.cmbsgroup1.focus();
            return false;
        }
    }


    