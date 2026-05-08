//alert("show me nom admin");
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
document.EMP_FAMILY.txtEmployeeid.value=emp;
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
        if((document.EMP_FAMILY.txtDept_Id.value=='TWAD'))
        {
         //winjob.officeSelection(true,true,true,false);
         winjob.officeSelection(true,true,true,false,true);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.EMP_FAMILY.txtDept_Id.value;
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
    document.EMP_FAMILY.txtOffice_Id.value=jobid;
    document.EMP_FAMILY.txtDept_Id.value=deptid;
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

/*window.onunload=function()
{

if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();
if (wininterval && wininterval.open && !wininterval.closed) wininterval.close();
}
  function popwindow()
    {
        if((document.EMP_FAMILY.txtEmployeeid.value==null)||(document.EMP_FAMILY.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.EMP_FAMILY.txtEmployeeid.focus();
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
        var str="EMP_FAMILY_ListAll.jsp?id="+document.EMP_FAMILY.txtEmployeeid.value;
        my_window= window.open(str,"mywindow1","status=1,height=400,width="+screen.availWidth+",resizable=yes, scrollbars=yes"); 
      my_window.moveTo(250,250);    
    }*/

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
function radioclick()
{
 
if(document.EMP_FAMILY.radioage[0].checked==true)
           {
           document.EMP_FAMILY.age.value='';
           document.EMP_FAMILY.age.disabled=true;
           document.EMP_FAMILY.DOB.disabled=false;
           document.EMP_FAMILY.fromimg.disabled=false;
           }
          
}
function radioclick1()
{
   var relation=document.EMP_FAMILY.Relationship.options[document.EMP_FAMILY.Relationship.selectedIndex].text;
   relation=
    trid=document.getElementById("marry");
    trid_det=document.getElementById("marry_det");
     relation=relation.toUpperCase());
    if(relation=='SON' ||relation=='DAUGHTER'||relation=='ADOPTED SON'||relation=='ADOPTED DAUGHTER')
   {
   alert("Please Enter the DOB");
   document.EMP_FAMILY.DOB.disabled=false;
   document.EMP_FAMILY.age.disabled=true;
   }
   else
   {
           if(document.EMP_FAMILY.radioage[1].checked==true)
           {
           document.EMP_FAMILY.DOB.value='';
           document.EMP_FAMILY.DOB.disabled=true;
           document.EMP_FAMILY.age.disabled=false;
           //document.EMP_FAMILY.fromimg.disabled=true;
          // document.EMP_FAMILY.fromimg.style.display="none";
           //alert(document.getElementById("fromimg"));
         //  document.getElementById("fromimg").disabled=true;
           }
}
}
function checkradio()
{
        if(document.EMP_FAMILY.radioage[1].checked==true)
        {
            alert("You have Choosen Age,So you are not allowed to give Date of birth");
            return false;
        }
        return true;
}



function checkdate()
{
//alert('check');
        var fromdt=document.EMP_FAMILY.txtDateFrom.value;
        var todt=document.EMP_FAMILY.txtDateTo.value;
        
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
            //document.EMP_FAMILY.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.EMP_FAMILY.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.EMP_FAMILY.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}
function checkmember()
{
checkempid();
     if((document.EMP_FAMILY.Member_Name.value==0)||(document.EMP_FAMILY.Member_Name.value.length==0))
        {
            alert("Enter Family Member Name ");
            document.EMP_FAMILY.Member_Name.focus();
            return false;
        }
    return true;
}

function checkrelation()
{
  checkmember();
    if((document.EMP_FAMILY.Relationship.value==0)||(document.EMP_FAMILY.Relationship.value.length==0))
        {
            alert("Enter the Relationship ");
            document.EMP_FAMILY.Relationship.focus();
            return false;
        }
       return true;
}
function chkmarried()
{
    var relation=document.EMP_FAMILY.Relationship.options[document.EMP_FAMILY.Relationship.selectedIndex].text;
    trid=document.getElementById("marry");
    trid_det=document.getElementById("marry_det");
    relation=relation.toUpper();
 if(relation=='SON' ||relation=='DAUGHTER'||relation=='ADOPTED SON'||relation=='ADOPTED DAUGHTER')
    {
     trid.style.display="block";
     trid_det.style.display="block";
    }
    else
    {
     trid.style.display="none";
     trid_det.style.display="none";
    }
}

function checkpincode()
{

  if((document.EMP_FAMILY.pincode.value==0)||(document.EMP_FAMILY.pincode.value.length==0))
        {
            alert("Enter Pincode ");
            document.EMP_FAMILY.pincode.focus();
            return false;
        }
return true;
}
function checkage()
{
checkmember();
checkrelation();

        if((document.EMP_FAMILY.DOB.value.length==0)&&(document.EMP_FAMILY.age.value.length==0))
        {
            alert("Enter Date of Birth or Age");
            document.EMP_FAMILY.DOB.focus();
            return false;
        }
return true;
}
function chkappliable()
{
    var relation=document.EMP_FAMILY.Relationship.options[document.EMP_FAMILY.Relationship.selectedIndex].text;
    var gender=document.EMP_FAMILY.gender.value;
   //alert(relation+gender);
 relation=relation.toUpperCase());
   if((gender=='M' && relation=='HUSBAND')||(gender=='F' && relation=='WIFE'))
       {
       alert("select applicable relation");
       document.EMP_FAMILY.Relationship.SelectedIndex=0;
       document.EMP_FAMILY.Relationship.value="";
       document.EMP_FAMILY.Relationship.focus();
       }
   else
   {
if(relation=='HUSBAND'||relation=='WIFE')
{      // alert("hello");
       document.getElementById("spouse").style.display="block";
       document.getElementById("spouse_det").style.display="block";
       document.getElementById("handicapt").style.display="none";
       document.getElementById("handicapt_det").style.display="none";
       document.getElementById("marry").style.display="none";
       document.getElementById("marry_det").style.display="none";
       document.getElementById("divage").style.display="block";
}
else
{
         document.getElementById("spouse").style.display="none";
         document.getElementById("spouse_det").style.display="none";         
         document.EMP_FAMILY.working_category[0].checked=false;
         document.EMP_FAMILY.working_category[1].checked=false;
         document.EMP_FAMILY.working_category[2].checked=false;
         document.EMP_FAMILY.is_handicapt[0].checked=false;
         document.EMP_FAMILY.is_handicapt[1].checked=false;
         trid=document.getElementById("marry");
         trid_det=document.getElementById("marry_det");
         ageid=document.getElementById("divage");
         if(relation=='SON' ||relation=='DAUGHTER'||relation=='ADOPTED SON'||relation=='ADOPTED DAUGHTER')
        {
            
            ageid.style.display="none";
            trid.style.display="block";
            trid_det.style.display="block";
            document.EMP_FAMILY.age.value='';
            document.getElementById("handicapt").style.display="block";
            document.getElementById("handicapt_det").style.display="block";
       }
     else
        {
            trid.style.display="none";
            trid_det.style.display="none";
            ageid.style.display="block";
            document.getElementById("handicapt").style.display="none";
            document.getElementById("handicapt_det").style.display="none";
        
        }
}
}
}


function notNull(p)
{


    if((document.EMP_FAMILY.txtEmployeeid.value==null)||(document.EMP_FAMILY.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.EMP_FAMILY.txtEmployee.value="";
        document.EMP_FAMILY.txtdob.value="";
        document.EMP_FAMILY.txtGpf.value="";
        document.EMP_FAMILY.txtEmployeeid.value="";
        
        document.EMP_FAMILY.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.EMP_FAMILY.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.EMP_FAMILY.txtEmployeeid.value="";
        document.EMP_FAMILY.txtEmployeeid.focus();
        return false;
    }
    
    
       /*  var c=checkdate();
       if(c==false)
       {
            document.EMP_FAMILY.txtDateTo.focus();
            return false;
        }*/
        
        
         if((document.EMP_FAMILY.Member_Name.value==0)||(document.EMP_FAMILY.Member_Name.value.length==0))
        {
            alert("Enter a Family Member Name ");
            document.EMP_FAMILY.Member_Name.focus();
            return false;
        }
                if((document.EMP_FAMILY.Relationship.value==0)||(document.EMP_FAMILY.Relationship.value.length==0))
        {
            alert("Select a Relationship");
            
                document.EMP_FAMILY.Relationship.focus();
          
            return false;
        }

        
      if((document.EMP_FAMILY.age.value.length==0)&&(document.EMP_FAMILY.DOB.value.length==0))
        {
            alert("Enter a Date of Birth or Age ");
            document.EMP_FAMILY.DOB.focus();
            return false;
        }
        
         if((document.EMP_FAMILY.Dependent[0].checked==false)&&(document.EMP_FAMILY.Dependent[1].checked==false))
        {
            alert("select a Family member's Dependenence ");
           // document.EMP_FAMILY.Dependent.focus();
            return false;
        }
        
        
     /*     if((document.EMP_FAMILY.address1.value==0)||(document.EMP_FAMILY.address1.value.length==0))
        {
            alert("Enter a  address1 ");
            document.EMP_FAMILY.address1.focus();
            return false;
        }
        
     /*    if((document.EMP_FAMILY.address2.value==0)||(document.EMP_FAMILY.address2.value.length==0))
        {
            alert("Enter a Nominee address2 ");
            document.EMP_FAMILY.address2.focus();
            return false;
        }
         if((document.EMP_FAMILY.address3.value==0)||(document.EMP_FAMILY.address3.value.length==0))
        {
            alert("Enter a  address3 ");
            document.EMP_FAMILY.address3.focus();
            return false;
        }
         if((document.EMP_FAMILY.pincode.value==0)||(document.EMP_FAMILY.pincode.value.length==0))
        {
            alert("Enter a  pincode ");
            document.EMP_FAMILY.pincode.focus();
            return false;
        }

        /* if((document.EMP_FAMILY.Remarks.value==0)||(document.EMP_FAMILY.Remarks.value.length==0))
        {
            alert("Enter a Remarks ");
            document.EMP_FAMILY.Remarks.focus();
            return false;
        }*/
        
    
    return true;
}
function pincodecheck(e,t)
{
if(document.EMP_FAMILY.pincode.value.length<6)
    { 
    alert("Enter the correct pincode");
    document.EMP_FAMILY.pincode.focus();
    return 0;
    }
    else
    
    
    return 1;
    
    
}
function checkPin()
{
  if (parseInt(document.EMP_FAMILY.pincode.value.length)>0)
  {
  if(parseInt(document.EMP_FAMILY.pincode.value.length)<6)
    {
        alert("Pincode length must be in 6 digits");
        document.EMP_FAMILY.pincode.value='';
        document.EMP_FAMILY.pincode.focus();
        return false;
    }
    if ((document.EMP_FAMILY.pincode.value< '600000' )||(document.EMP_FAMILY.pincode.value> '700000'))
    {
        alert ("Pincode must be within 600000-700000 ");
        document.EMP_FAMILY.pincode.value='';
        document.EMP_FAMILY.pincode.focus();
        
        return;
  }
  }
}
function ischarValid(evt,item){


var chrcode = (evt.which) ? evt.which : evt.keyCode

if (chrcode!=46 && (chrcode!=08) && (chrcode!=09)&& (chrcode!=32)&&
 (chrcode < 97 || chrcode > 122) && (chrcode < 65 || chrcode > 90)){
	return false;	}
	
				return true;

} 

function doFunction(Command,param)
{
  
//alert("command :"+Command);
//alert();

    
    var empid=document.EMP_FAMILY.txtEmployeeid.value;
    var slno=document.EMP_FAMILY.txtSNo.value;
   
  //  alert(empid);
    
    
    
  /*   if(param!='null')
    {
        alert(param);
                var url="../../../../../emp_nomination_sevlet.av?Command=loademp&txtEmployeeid="+param;
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
         
    
    }*/
    if(Command=='efocus')
    {
    document.EMP_FAMILY.txtEmployeeid.focus();
       //alert("focus");
    }
    else
    if(Command=='loademp')
    {
       //alert("load emp");
       // var check=notNull(null);
      //  if(check )
        {
               // startwaiting(document.EMP_FAMILY) ;
                service=null;
/*var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';*/
                //alert('load emp');
               /* if(param=='exist')
                {
                var url="../../../../../emp_nomination_sevlet.av?Command=loademp&txtEmployeeid="+empid+"&param=exist";
                }
                else
                {*/
                var url="../../../../../emp_family_sevlet.av?Command=loademp&txtEmployeeid="+empid;
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
        if(check)
        {
               // statuswaiting(document.EMP_FAMILY) ;
                //if(check)
                {
                var AGE;
                var Member_Name=document.EMP_FAMILY.Member_Name.value;
                var DOB=document.EMP_FAMILY.DOB.value;
                var Remarks=document.EMP_FAMILY.Remarks.value;
                 var address1=document.EMP_FAMILY.address1.value;
                var address2=document.EMP_FAMILY.address2.value;
                 var address3=document.EMP_FAMILY.address3.value;
                var pincode=document.EMP_FAMILY.pincode.value;
                var age=document.EMP_FAMILY.age.value;
               // alert("add age"+age);
                if(age=='' ||age==null)
                    age=0;
                //alert("age  "+age);
                var depend;var working_category;var is_handicapt;
              if(document.EMP_FAMILY.Dependent[0].checked==true)
                   depend='Y';
              else
                   if(document.EMP_FAMILY.Dependent[1].checked==true)
                      depend='N';
            
                if(document.EMP_FAMILY.working_category[0].checked==true)
                    working_category='WG';
                else if(document.EMP_FAMILY.working_category[1].checked==true)
                     working_category='WP';
                else if(document.EMP_FAMILY.working_category[2].checked==true)
                     working_category='NW';
                else
                    working_category="";
                if(document.EMP_FAMILY.is_handicapt[0].checked==true)
                    is_handicapt='Y';
                else if(document.EMP_FAMILY.is_handicapt[1].checked==true)
                     is_handicapt='N';
                else
                    is_handicapt="";
                var Relationship=document.EMP_FAMILY.Relationship.value;
                var relation=document.EMP_FAMILY.Relationship.options[document.EMP_FAMILY.Relationship.selectedIndex].text;
                if((document.EMP_FAMILY.pincode.value==0)||(document.EMP_FAMILY.pincode.value.length==0))
                pincode=0;
              //  alert(relation);
                if(relation!='Son' && relation!='Daughter' && relation!='Adopted Son'&& relation!='Adopted Daughter')
                    married='Y';
                else
                {
                      if(document.EMP_FAMILY.married[0].checked==true)
                           married='Y';
                      else if(document.EMP_FAMILY.married[1].checked==true)
                           married='N';
                }
                  // alert(married);
                    var url="../../../../../emp_family_sevlet.av?Command=Add&txtEmployeeid="+empid;
                    url=url+"&Member_Name="+Member_Name+"&DOB="+DOB;
                    url=url+"&depend="+depend;
                    url=url+"&Relationship="+Relationship;
                    url=url+"&Remarks="+Remarks+"&address1="+address1;
                    url=url+"&address2="+address2+"&address3="+address3+"&pincode="+pincode+"&age="+age+"&married="+married+"&working_category="+working_category+"&is_handicapt="+is_handicapt;


                    req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();     
                    
                    // alert('in add');
                }
        }
    }
    if(Command=='Update')
    {
   
        var check=notNull('Update');
       // alert(check);
        if(check)
        {
        var Member_Name=document.EMP_FAMILY.Member_Name.value;
                var DOB=document.EMP_FAMILY.DOB.value;
                var Remarks=document.EMP_FAMILY.Remarks.value;
                var depend;
              if(document.EMP_FAMILY.Dependent[0].checked==true)
                   depend='Y';
                   else
                   if(document.EMP_FAMILY.Dependent[1].checked==true)
                   depend='N';
                var Relationship=document.EMP_FAMILY.Relationship.value;
                var txtSNO=document.EMP_FAMILY.txtSNo.value;
                 var address1=document.EMP_FAMILY.address1.value;
                var address2=document.EMP_FAMILY.address2.value;
                 var address3=document.EMP_FAMILY.address3.value;
                var pincode=document.EMP_FAMILY.pincode.value;
                 if((document.EMP_FAMILY.pincode.value==0)||(document.EMP_FAMILY.pincode.value.length==0))
                pincode=0;
                var age=document.EMP_FAMILY.age.value;
                if((age==null)||(age.length==0))
                 {
                 //alert(age)
                 age=0;
                 }
                if(document.EMP_FAMILY.working_category[0].checked==true)
                    working_category='WG';
                else if(document.EMP_FAMILY.working_category[1].checked==true)
                     working_category='WP';
                else if(document.EMP_FAMILY.working_category[2].checked==true)
                     working_category='NW';
                else
                    working_category="";
                if(document.EMP_FAMILY.is_handicapt[0].checked==true)
                    is_handicapt='Y';
                else if(document.EMP_FAMILY.is_handicapt[1].checked==true)
                     is_handicapt='N';
                else
                    is_handicapt="";
                    
                var Relationship=document.EMP_FAMILY.Relationship.value;
                var relation=document.EMP_FAMILY.Relationship.options[document.EMP_FAMILY.Relationship.selectedIndex].text;
                
                if(relation!='Son' && relation!='Daughter' && relation!='Adopted Son'&& relation!='Adopted Daughter')
                {
                    married='Y';
                }
                else
                {
                      if(document.EMP_FAMILY.married[0].checked==true)
                           married='Y';
                      else if(document.EMP_FAMILY.married[1].checked==true)
                           married='N';
                }
        
                    var url="../../../../../emp_family_sevlet.av?Command=Update&txtEmployeeid="+empid+"&txtSNO="+txtSNO;
                    url=url+"&Member_Name="+Member_Name+"&DOB="+DOB;
                    url=url+"&depend="+depend;
                    url=url+"&Relationship="+Relationship;
                    url=url+"&Remarks="+Remarks+"&address1="+address1;
                    url=url+"&address2="+address2+"&address3="+address3+"&pincode="+pincode+"&age="+age+"&married="+married+"&working_category="+working_category+"&is_handicapt="+is_handicapt;

                    req.open("POST",url,true);
                   
                    req.onreadystatechange=handleResponse;
                    if(window.XMLHttpRequest)
                            req.send(null);
                    else req.send();      
               
        }
    }
    else if(Command=="Delete")
    {
    
        if(confirm('Do You want to delete this record?'))
        {
               // statuswaiting(document.EMP_FAMILY) ;
                  //var Nominee_Name=document.EMP_FAMILY.Nominee_Name.value;
                  var txtSNO=document.EMP_FAMILY.txtSNo.value;
                var url="../../../../../emp_family_sevlet.av?Command=Delete&txtEmployeeid="+empid;
                url=url+"&txtSNO="+txtSNO;
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
        }
    
    
    
    }
     else if(Command=="Clear")
            {
               
                clr();
                document.EMP_FAMILY.cmdadd.style.display="block";
                document.EMP_FAMILY.cmdupdate.style.display="none";
                //document.EMP_FAMILY.cmdadd.disabled=false;
               // document.EMP_FAMILY.cmdupdate.disabled=true;
                document.EMP_FAMILY.cmddelete.disabled=true;
               
               
                
            }
    else if(Command=="Load")
    {
           if(!(empid==null || empid.length==0))
           {
              //startwaiting(document.EMP_FAMILY) ;
                var url="../../../../../emp_family_sevlet.av?Command=Load&txtEmployeeid="+empid;
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
    
    document.EMP_FAMILY.txtOffice_Id.value='';
    
    document.EMP_FAMILY.txtOffice_Name.value='';
    document.EMP_FAMILY.txtOffice_Address1.value='';
   // document.EMP_FAMILY.txtOffice_Address2.value='';
   // document.EMP_FAMILY.txtOffice_City.value='';
       
   

}

function checkdeptsel()
{
    // if((document.EMP_FAMILY.txtDept_Id.value==0)||(document.EMP_FAMILY.txtDept_Id.value.length==0))
        {
                offclr();
        }
}
function clr()
{

document.EMP_FAMILY.cmdadd.disabled=false;
//document.EMP_FAMILY.marry
document.getElementById("marry").style.display="none";
document.getElementById("marry_det").style.display="none";
document.EMP_FAMILY.txtSNo.value='';
document.EMP_FAMILY.Member_Name.value="";
document.EMP_FAMILY.Relationship.selectedIndex=0;
document.getElementById("spouse").style.display="none";
document.getElementById("spouse_det").style.display="none";
document.getElementById("handicapt").style.display="none";
document.getElementById("handicapt_det").style.display="none";
document.EMP_FAMILY.working_category[0].checked=false;
document.EMP_FAMILY.working_category[1].checked=false;
document.EMP_FAMILY.working_category[2].checked=false;
document.EMP_FAMILY.is_handicapt[0].checked=false;
document.EMP_FAMILY.is_handicapt[1].checked=false;
document.EMP_FAMILY.DOB.value='';
document.EMP_FAMILY.Dependent[0].checked=true;
document.EMP_FAMILY.Dependent[1].checked=false;
document.EMP_FAMILY.age.value='';
document.EMP_FAMILY.address1.value='';
document.EMP_FAMILY.address2.value='';
document.EMP_FAMILY.address3.value='';
document.EMP_FAMILY.pincode.value='';
document.EMP_FAMILY.Remarks.value='';
document.EMP_FAMILY.radioage[0].checked=true
document.EMP_FAMILY.radioage[1].checked=false
//document.EMP_FAMILY.txtDept_Id.value='0';
//offclr();

//alert('allclear');
//doFunction('LastDate',null);


}


function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
           // stopwaiting(document.EMP_FAMILY);
            
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
                         document.EMP_FAMILY.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.EMP_FAMILY.txtEmployeeid.focus();
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
            else if(Command=="Load")
            {
           // alert("load handlwersffffffffffffffff");
                   // loadPage(1)
                   var tbody=document.getElementById("tb");
                   try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                   var service =baseResponse.getElementsByTagName("EMPLOYEE_ID");
                   //alert(service.length);
                 var j;
               
                 
                   for(i=0;i<service.length;i++)
                   
                {
                j=i+1;
                        var EMPLOYEE_ID = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
                       // alert("empid"+EMPLOYEE_ID);
                        
                        var LIST_SL_NO =baseResponse.getElementsByTagName("LIST_SL_NO")[i].firstChild.nodeValue;
                        var MEMBER_NAME=baseResponse.getElementsByTagName("MEMBER_NAME")[i].firstChild.nodeValue;
                        var DEPEND=baseResponse.getElementsByTagName("DEPEND")[i].firstChild.nodeValue;
                        
                        var RELATIONSHIP_ID=baseResponse.getElementsByTagName("RELATIONSHIP_ID")[i].firstChild.nodeValue;
                        var DOB=baseResponse.getElementsByTagName("DOB")[i].firstChild.nodeValue;
                        if(DOB=='null')
                            DOB='';
                        var rel=baseResponse.getElementsByTagName("rel")[i].firstChild.nodeValue;
                        var REMARKS=baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;              
                        if(REMARKS=='null')
                        REMARKS='';
                        var ADDRESS1=baseResponse.getElementsByTagName("ADDRESS1")[i].firstChild.nodeValue;
                        var ADDRESS2=baseResponse.getElementsByTagName("ADDRESS2")[i].firstChild.nodeValue;
                        var ADDRESS3=baseResponse.getElementsByTagName("ADDRESS3")[i].firstChild.nodeValue;
                        var Married=baseResponse.getElementsByTagName("Married")[i].firstChild.nodeValue;
                        var spouse_work_status=baseResponse.getElementsByTagName("spouse_work_status")[i].firstChild.nodeValue;
                        var handicapped=baseResponse.getElementsByTagName("handicapped")[i].firstChild.nodeValue;
                        if(Married=='null')
                            Married='';
                        if(ADDRESS1=='null')
                            ADDRESS1='';
                        if(ADDRESS2=='null')
                            ADDRESS2='';
                        if(ADDRESS3=='null')
                            ADDRESS3='';
                        if(spouse_work_status=="-")
                            spouse_work_status='';
                        if(handicapped=="-")
                            handicapped='';
                        var PINCODE=baseResponse.getElementsByTagName("PINCODE")[i].firstChild.nodeValue;              
                        if(PINCODE=='null') 
                        PINCODE='';
                        if(PINCODE==0)
                        PINCODE="";
                        
                        var AGE=baseResponse.getElementsByTagName("AGE")[i].firstChild.nodeValue;              
                       
                        if(AGE==0)
                            AGE='';
                       
                        //alert("per"+PERCENTAGE_SHARE);
                        
                        var mycurrent_row=document.createElement("TR");
                      
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
                            var url="javascript:loadTable('"+j+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("Edit");
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                        }
                        
                        var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(LIST_SL_NO);                         
                      descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                       
                         
                         
                         var descell=document.createElement("TD");
                         var sn=document.createTextNode(MEMBER_NAME);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                         var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(RELATIONSHIP_ID);
                         sc.type="hidden";
                         sc.name="RELATION";
                         sc.text=rel;
                         sc.value=rel;
                         descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(spouse_work_status);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(handicapped);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(Married);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(DOB);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(AGE);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(DEPEND);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                          var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(ADDRESS1);                         
                     descell.appendChild(sn);
                         
                         mycurrent_row.appendChild(descell);
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(ADDRESS2);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(ADDRESS3);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                        var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(PINCODE);                         
                     descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(REMARKS);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        tbody.appendChild(mycurrent_row);
                        tbody.appendChild(mycurrent_row);
                       
                }
            }
           
            
            
        }  
        
    }
    
}



function addfun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert(flag);  
    if(flag=="success")
    {
        var id=baseResponse.getElementsByTagName("genid")[0].firstChild.nodeValue;
        document.EMP_FAMILY.txtSNo.value=id;
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
        alert("Record  Added successfully");
    
    }
    else if(flag=='failure1')
    {
            
            alert("Share Value is not validated");
            clr();
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
            var egen=baseResponse.getElementsByTagName("gender")[0].firstChild.nodeValue
            document.EMP_FAMILY.txtEmployee.value=ename;
            if(edob=="-")
                edob="";
            document.EMP_FAMILY.txtdob.value=edob;
          
            if(egpf==0)
                egpf="";
            document.EMP_FAMILY.txtGpf.value=egpf;
            document.EMP_FAMILY.gender.value=egen;
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
             
             
               var details=baseResponse.getElementsByTagName("details"); 
        var family_id=new Array();
        var family_desc=new Array();
        var relation=document.getElementById("Relationship");
        relation.innerHTML="";
        //alert("sd"+details.length);
        for(var i=0;i<details.length;i++)
        {
            family_id[i]=baseResponse.getElementsByTagName("family_id")[i].firstChild.nodeValue;
            family_desc[i]=baseResponse.getElementsByTagName("family_desc")[i].firstChild.nodeValue;
             
        }
        
        var option=document.createElement("OPTION");
        option.text="- Select the Relationship -";
        option.value="";
        
        try
        {
             relation.add(option);
        }
        catch(errorObject)
        {
             relation.add(option,null);
        }
        for(var i=0;i<details.length;i++)
        {
            var option=document.createElement("OPTION");
            option.value=family_id[i];
            option.text=family_desc[i];
            try
            {
                relation.add(option);
            }
            catch(error)
            {
                relation.add(option,null);
            }
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
           //alert("load");
            clr();
            
    }
    else if(flag=="failure1")
    {
            var id=document.EMP_FAMILY.txtEmployeeid.value;
            //alert("Can not Update SR. Because Employee Id "+id+" is not under your Office!");
            alert("SR controling office for this employee is different from your office!");
            document.EMP_FAMILY.txtEmployee.value="";
            document.EMP_FAMILY.txtdob.value="";
            document.EMP_FAMILY.txtGpf.value="";
            document.EMP_FAMILY.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_FAMILY.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.EMP_FAMILY.txtEmployeeid.value;
            alert("You have no Current Posting. Can not update SR for "+id+"!");
            document.EMP_FAMILY.txtEmployee.value="";
            document.EMP_FAMILY.txtdob.value="";
            document.EMP_FAMILY.txtGpf.value="";
            document.EMP_FAMILY.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_FAMILY.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.EMP_FAMILY.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not update SR!");
            document.EMP_FAMILY.txtEmployee.value="";
            document.EMP_FAMILY.txtdob.value="";
            document.EMP_FAMILY.txtGpf.value="";
            document.EMP_FAMILY.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_FAMILY.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.EMP_FAMILY.txtEmployeeid.value;
            alert("Can not update SR. Access Denined!");
            document.EMP_FAMILY.txtEmployee.value="";
            document.EMP_FAMILY.txtdob.value="";
            document.EMP_FAMILY.txtGpf.value="";
            document.EMP_FAMILY.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_FAMILY.txtEmployeeid.focus();
            clr();
    }
    else
    {
        
               
        alert('Enter a Valid Employee Number');
        document.EMP_FAMILY.txtEmployee.value="";
        document.EMP_FAMILY.txtdob.value="";
        document.EMP_FAMILY.txtGpf.value="";
        document.EMP_FAMILY.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        document.EMP_FAMILY.txtEmployeeid.focus();
        clr();
    }


}



function changepage()
{
//alert('test');
var page=document.EMP_FAMILY.cmbpage.value;
loadPage(parseInt(page));

}



function loadPage(page)
{
alert("load page33333333333333333333333333");
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             //alert(baseResponse);
           // service=baseResponse;
          // alert(baseResponse);
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          service=baseResponse.getElementsByTagName("servicedata");
            document.EMP_FAMILY.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                  
                  // alert(service);
             if(service)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                 
                
            
                    
                    
                    
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

           __pagination=document.EMP_FAMILY.cmbpagination.value;
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
          //document.EMP_FAMILY.txtSNo.focus();
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
    document.EMP_FAMILY.cmdupdate.style.display="block";
    
    document.EMP_FAMILY.cmdadd.disabled=true;
    document.EMP_FAMILY.cmddelete.disabled=false;
    var r=document.getElementById(scod);
    var rcells=r.cells;
    var address1,address2,address3;
    
    try{
   /* document.EMP_FAMILY.txtSNo1.value=rcells.item(1).lastChild.nodeValue;
     document.EMP_FAMILY.txtSNo.value=rcells.item(1).firstChild.value;*/
     
    document.EMP_FAMILY.Member_Name.value=rcells.item(2).firstChild.nodeValue;
    document.EMP_FAMILY.txtSNo.value=rcells.item(1).firstChild.nodeValue;
     document.EMP_FAMILY.Relationship.value=rcells.item(3).firstChild.value;
     //alert(document.EMP_FAMILY.Relationship.value);
    
     if(rcells.item(6).firstChild.nodeValue=='Y')
           document.EMP_FAMILY.married[0].checked=true;
     else
        document.EMP_FAMILY.married[1].checked=true;
        
    // alert("depend"+rcells.item(5).firstChild.nodeValue);
    if(rcells.item(9).firstChild.nodeValue=='Y')
        document.EMP_FAMILY.Dependent[0].checked=true;
        else
        document.EMP_FAMILY.Dependent[1].checked=true;
        var spouse_det=document.getElementById("spouse_det");
        var spouse=document.getElementById("spouse");
         var handicapt=document.getElementById("handicapt");
         var handicapt_det=document.getElementById("handicapt_det");
      if((rcells.item(3).lastChild.nodeValue=='Son')||(rcells.item(3).lastChild.nodeValue=='Daughter')||(rcells.item(3).lastChild.nodeValue=='Adopted Son')||(rcells.item(3).lastChild.nodeValue=='Adopted Daughter'))
     {
       
        var ageid=document.getElementById("divage");
        ageid.style.display="none";
        document.getElementById("marry").style.display="block";
        document.getElementById("marry_det").style.display="block";
        spouse_det.style.display="none";
        spouse.style.display="none";
        handicapt.style.display="block";
        handicapt_det.style.display="block";
     }
     else if(rcells.item(3).lastChild.nodeValue=='Husband'||rcells.item(3).lastChild.nodeValue=='Wife')
     {
        spouse_det.style.display="block";
        spouse.style.display="block";
        handicapt.style.display="none";
        handicapt_det.style.display="none";
        document.getElementById("marry").style.display="none";
        document.getElementById("marry_det").style.display="none";
        document.getElementById("divage").style.display="block";
     }
     else
     {
        
        var ageid=document.getElementById("divage");
        ageid.style.display="block";
        document.getElementById("marry").style.display="none";
        document.getElementById("marry_det").style.display="none";
        spouse_det.style.display="none";
        spouse.style.display="none";
        handicapt.style.display="none";
        handicapt_det.style.display="none";
     }
      if(rcells.item(4).firstChild.nodeValue=='')
      {
        document.EMP_FAMILY.working_category[0].checked=false
        document.EMP_FAMILY.working_category[1].checked=false
        document.EMP_FAMILY.working_category[2].checked=false
      }
      else
      {
        if(rcells.item(4).firstChild.nodeValue=='WG')
            document.EMP_FAMILY.working_category[0].checked=true
        else if(rcells.item(4).firstChild.nodeValue=='WP')
            document.EMP_FAMILY.working_category[1].checked=true
        else 
            document.EMP_FAMILY.working_category[2].checked=true
      }
      if(rcells.item(5).firstChild.nodeValue=='')
      {
        document.EMP_FAMILY.is_handicapt[0].checked=false
        document.EMP_FAMILY.is_handicapt[1].checked=false
      }
      else
      {
        if(rcells.item(5).firstChild.nodeValue=='Y')
            document.EMP_FAMILY.is_handicapt[0].checked=true;
        else
            document.EMP_FAMILY.is_handicapt[1].checked=true;
      }
       if(rcells.item(7).firstChild.nodeValue=='')
             {
             document.EMP_FAMILY.radioage[0].checked=false
             document.EMP_FAMILY.radioage[1].checked=true
             document.EMP_FAMILY.DOB.disabled=true;
             document.EMP_FAMILY.age.disabled=false;
             
             //radiocheck();
             
             }
             else
             {
             document.EMP_FAMILY.radioage[0].checked=true
             document.EMP_FAMILY.radioage[1].checked=false
             document.EMP_FAMILY.age.disabled=true;
             document.EMP_FAMILY.DOB.disabled=false;
             //radiocheck1();
             
             }   
     document.EMP_FAMILY.DOB.value=rcells.item(7).firstChild.nodeValue;
     document.EMP_FAMILY.age.value=rcells.item(8).firstChild.nodeValue;
      
      
     document.EMP_FAMILY.address1.value=rcells.item(10).firstChild.nodeValue;
     document.EMP_FAMILY.address2.value=rcells.item(11).firstChild.nodeValue;
     document.EMP_FAMILY.address3.value=rcells.item(12).firstChild.nodeValue;
     document.EMP_FAMILY.pincode.value=rcells.item(13).firstChild.nodeValue;
      document.EMP_FAMILY.Remarks.value=rcells.item(14).firstChild.nodeValue;
    
     
    // alert(document.EMP_FAMILY.txtSNo);
      }catch(e){
      alert("error");
      }
}



function updatefun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
    {
    var items=new Array();
          document.EMP_FAMILY.cmdadd.style.display="block";
         document.EMP_FAMILY.cmdupdate.style.display="none";  
            // changeempstatus();
         alert("Records are Updated");
        // doFunction('Load','null');
         
    /*document.EMP_FAMILY.txtDateFrom.disabled=false;
    var i=document.getElementById("fromimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EMP_FAMILY.optDateFrom[0].disabled=false;
    document.EMP_FAMILY.optDateFrom[1].disabled=false;
    
    document.EMP_FAMILY.txtDateTo.disabled=false;
    var i=document.getElementById("toimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EMP_FAMILY.optDateTo[0].disabled=false;
    document.EMP_FAMILY.optDateTo[1].disabled=false;
     */    
     doFunction('Load','null');
         clr();
         //changeempstatus();
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
//    alert(baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue);
    
    if(flag=="success")
    {
        alert("Records are deleted");
       //  var sc=baseResponse.getElementsByTagName("scd")[0].firstChild.nodeValue;
       /* var tbody=document.getElementById("mytable");
        var r=document.getElementById(document.EMP_FAMILY.txtSNo.value);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        */
        //clr();
        document.EMP_FAMILY.cmdadd.style.display="block";
        document.EMP_FAMILY.cmdupdate.style.display="none";
       // document.EMP_FAMILY.cmdadd.disabled=false;
       // document.EMP_FAMILY.cmdupdate.disabled=true;
        document.EMP_FAMILY.cmddelete.disabled=true;
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
    if(document.EMP_FAMILY.txtEmployeeid.value==null || document.EMP_FAMILY.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.EMP_FAMILY.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}


