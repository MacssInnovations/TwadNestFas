//alert("show me nom admin");
//var a=1;
var service;
var baseResponse;
var __pagination=10;
var destid;
var totalblock=0;
var slno = 1;    
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
document.EMP_NOMINEE.txtEmployeeid.value=emp;
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
        if((document.EMP_NOMINEE.txtDept_Id.value=='TWAD'))
        {
         //winjob.officeSelection(true,true,true,false);
         winjob.officeSelection(true,true,true,false,true);
        }
        else
        {
            winjob.officeSelection(false,false,false,true);
           // winjob.document.HRM_JobSearch.cmbOName.value=document.EMP_NOMINEE.txtDept_Id.value;
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
    document.EMP_NOMINEE.txtOffice_Id.value=jobid;
    document.EMP_NOMINEE.txtDept_Id.value=deptid;
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

///////////////////////////////////////////////////////////////////////////////////


//************list all *************************
 function popwindow()
    {
        if((document.EMP_NOMINEE.txtEmployeeid.value==null)||(document.EMP_NOMINEE.txtEmployeeid.value.length==0))
        {
            alert("Enter the Employee Id");
            document.EMP_NOMINEE.txtEmployeeid.focus();
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
        var str="EMP_NOMINEE_ListAll.jsp?id="+document.EMP_NOMINEE.txtEmployeeid.value;
        my_window= window.open(str,"mywindow1","status=1,height=400,width="+screen.availWidth+",resizable=yes, scrollbars=yes"); 
      my_window.moveTo(250,250);    
}
*/
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
        var fromdt=document.EMP_NOMINEE.txtDateFrom.value;
        var todt=document.EMP_NOMINEE.txtDateTo.value;
        
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
            //document.EMP_NOMINEE.txtDateTo.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('From Date should be less than To Date');
                    //document.EMP_NOMINEE.txtDateTo.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('From Date should be less than To Date');
                           // document.EMP_NOMINEE.txtDateTo.focus();
                            return false;
                        }
                        
                }
        }
        return true;

}
function checkmember()
{
checkempid();
     if((document.EMP_NOMINEE.omember.value.length==0||document.EMP_NOMINEE.omember.value.length=='')&&(document.EMP_NOMINEE.member.value.length==0||document.EMP_NOMINEE.member.value.length==''))
        {
            alert("Enter  Nominee Name ");
            document.EMP_NOMINEE.member.focus();
            return false;
        }
    return true;
}

/*function 
()
{
  checkmember();
  if((document.EMP_NOMINEE.Nominee_Relationship.value==0)||(document.EMP_NOMINEE.Nominee_Relationship.value.length==0))
        {
            alert("Enter  Nominee Relationship ");
            document.EMP_NOMINEE.Nominee_Relationship.focus();
            return false;
        }
       return true;
}*/

function checkpincode()
{
//checkage();
  if((document.EMP_NOMINEE.Nominee_pincode.value==0)||(document.EMP_NOMINEE.Nominee_pincode.value.length==0))
        {
            alert("Enter  Nominee pincode ");
            document.EMP_NOMINEE.Nominee_pincode.focus();
            return false;
        }
return true;
}

function checkage()
{
checkmember();
//checkrelation();

        if((document.EMP_NOMINEE.Nominee_DOB.value.length==0)&&(document.EMP_NOMINEE.Nominee_Age.value.length==0))
        {
            alert("Enter Date of Birth or Age");
            document.EMP_NOMINEE.Nominee_DOB.focus();
            return false;
        }
return true;
}
/*function checkrelation()
{
  checkmember();
  if((document.EMP_NOMINEE.Nominee_Relationship.value==0)||(document.EMP_NOMINEE.Nominee_Relationship.value.length==0))
        {
            alert("Enter  Nominee Relationship ");
            document.EMP_NOMINEE.Nominee_Relationship.focus();
            return false;
        }
       return true;
}*/


function notNull(p)
{
    
    if((document.EMP_NOMINEE.txtEmployeeid.value==null)||(document.EMP_NOMINEE.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.EMP_NOMINEE.txtEmployee.value="";
        document.EMP_NOMINEE.txtdob.value="";
        document.EMP_NOMINEE.txtGpf.value="";
        document.EMP_NOMINEE.txtEmployeeid.value="";
        
        document.EMP_NOMINEE.txtEmployeeid.focus();
        return false;
    }
    
     if(isNaN(document.EMP_NOMINEE.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.EMP_NOMINEE.txtEmployeeid.value="";
        document.EMP_NOMINEE.txtEmployeeid.focus();
        return false;
    }
    

       /*  var c=checkdate();
       if(c==false)
       {
            document.EMP_NOMINEE.txtDateTo.focus();
            return false;
        }*/
        
        if((document.EMP_NOMINEE.fund_id.value==0)||(document.EMP_NOMINEE.fund_id.value.length==0))
        {
            alert("Select a Fund Name");
            
                document.EMP_NOMINEE.fund_id.focus();
          
            return false;
        }
        
         if((document.EMP_NOMINEE.omember.value.length==0)&&(document.EMP_NOMINEE.member.value.length==0))
        {
            alert("Enter  Nominee Name ");
            document.EMP_NOMINEE.member.focus();
            return false;
        }
        if((document.EMP_NOMINEE.Nominee_DOB.value.length==0)&&(document.EMP_NOMINEE.Nominee_Age.value.length==0))
        {
            alert("Enter Date of Birth or Age");
            document.EMP_NOMINEE.Nominee_DOB.focus();
            return false;
        }
       
      /*  if((document.EMP_NOMINEE.Nominee_address1.value==0)||(document.EMP_NOMINEE.Nominee_address1.value.length==0))
        {
            alert("Enter a Nominee address1 ");
            document.EMP_NOMINEE.Nominee_address1.focus();
            return false;
        }
        
         if((document.EMP_NOMINEE.Nominee_address2.value==0)||(document.EMP_NOMINEE.Nominee_address2.value.length==0))
        {
            alert("Enter a Nominee address2 ");
            document.EMP_NOMINEE.Nominee_address2.focus();
            return false;
        }
         if((document.EMP_NOMINEE.Nominee_address3.value==0)||(document.EMP_NOMINEE.Nominee_address3.value.length==0))
        {
            alert("Enter a Nominee address3 ");
            document.EMP_NOMINEE.Nominee_address3.focus();
            return false;
        }
         if((document.EMP_NOMINEE.Nominee_pincode.value==0)||(document.EMP_NOMINEE.Nominee_pincode.value.length==0))
        {
            alert("Enter  Nominee pincode ");
            document.EMP_NOMINEE.Nominee_pincode.focus();
            return false;
        }

        /* if((document.EMP_NOMINEE.Nominee_Relationship.value==0)||(document.EMP_NOMINEE.Nominee_Relationship.value.length==0))
        {
            alert("Enter Nominee Relationship ");
            document.EMP_NOMINEE.Nominee_Relationship.focus();
            return false;
        }*/
        if((document.EMP_NOMINEE.Nomin_Date.value==0)||(document.EMP_NOMINEE.Nomin_Date.value.length==0))
        {
            alert("Enter Nomination Date ");
            document.EMP_NOMINEE.Nomin_Date.focus();
            return false;
        }
         if((document.EMP_NOMINEE.Nominee_Pecentshare.value==0)||(document.EMP_NOMINEE.Nominee_Pecentshare.value.length==0))
        {
            alert("Enter Nominee's Pecentage of Share ");
            document.EMP_NOMINEE.Nominee_Pecentshare.focus();
            return false;
        }
        
   
    
    return true;
}
function chknomindate()
{
  if((document.EMP_NOMINEE.Nomin_Date.value==0)||(document.EMP_NOMINEE.Nomin_Date.value.length==0))
        {
            alert("Enter Nomination Date ");
            document.EMP_NOMINEE.Nomin_Date.focus();
            return false;
        }   
return true;
}

function validage()
{
if(document.EMP_NOMINEE.Nominee_Age.value>100)
{
        
        alert("Enter Valid Age");
        document.EMP_NOMINEE.Nominee_Age.value='';
        
}
}
function doFunction(Command,param)
{
  
//alert("command :"+Command);
//alert();
var total=0;
    
    var empid=document.EMP_NOMINEE.txtEmployeeid.value;
   // var slno=document.EMP_NOMINEE.txtSNo.value;
 //  alert(slno);
  
if(Command=='efocus')
    {
    document.EMP_NOMINEE.txtEmployeeid.focus();
    document.EMP_NOMINEE.total.value='';
    //alert("focus");
    }
    else if(Command=='loadnominee')
    {
   // alert("oadnominee");
    var nomineeid=document.getElementById("member");
    var nominee=document.EMP_NOMINEE.member.value;
    txt=document.EMP_NOMINEE.member.options[document.EMP_NOMINEE.member.selectedIndex].text;
    var tbody=document.getElementById("tb");
     record1=new Array();
    var d=0;
   // alert("len"+tbody.rows.length);
 for(var i=1;i<=tbody.rows.length;i++)
            {
            var r=document.getElementById(i);
           //alert("r"+r);
                var rcells=r.cells;
                record1[i]=rcells.item(2).firstChild.nodeValue;
                if(record1[i]==nominee)
                {
                alert("You have already inserted ");
                d=1;
                }
                }
 
        if(d==0)
    {
    
    
     var url="../../../../../Emp_Nomin_Revalid.av?Command=loadnominee&txtEmployeeid="+empid+"&nominee="+nominee;
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
     }           
    }
  else  if(Command=='loademp')
    {
        {
               // startwaiting(document.EMP_NOMINEE) ;
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
        var fund_id=document.EMP_NOMINEE.fund_id.value;
                var url="../../../../../Emp_Nomin_Revalid.av?Command=loademp&txtEmployeeid="+empid+"&fund_id="+fund_id;
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }
    
    
else    if(Command=='Add')
    {
    var check=notNull();
   // alert("added");
   document.EMP_NOMINEE.Submit.disabled=false;
   document.EMP_NOMINEE.Validate.disabled=true;
    if(check)
    {
    
    //alert(tbody);
     var tbody=document.getElementById("tb");
     record1=new Array();
    var d=0;
    
 for(var i=1;i<=tbody.rows.length;i++)
            {
            var r=document.getElementById(i);
           //alert("r"+r);
                var rcells=r.cells;
               // alert(total+parseFloat(rcells.item(12).firstChild.nodeValue));
             total= total+parseFloat(rcells.item(12).firstChild.nodeValue);
             rcells.item(11).firstChild.nodeValue=document.EMP_NOMINEE.Nomin_Date.value;
                }
                total=parseFloat(total);
          var empid=document.EMP_NOMINEE.txtEmployeeid.value;
                     //var slno=document.EMP_NOMINEE.txtSNo.value;
                    // alert(slno);
                    var Nominee_Name='';
                    var Nominee_Relationship='';
                    var relationship_id;
                
                 var fund_id=document.EMP_NOMINEE.fund_id.value;
                    if(document.EMP_NOMINEE.family[0].checked==true)
                     {
                        Nominee_Name=document.EMP_NOMINEE.member.value;
                        relationship_id='F';
                        
                     }
                    else
                     {
                         Nominee_Name=document.EMP_NOMINEE.omember.value;
                         relationship_id='O';
                         //Nominee_Relationship=document.EMP_NOMINEE.oNominee_Relationship.value;
                      }
                      //alert(relationship_id);
                Nominee_Relationship=document.EMP_NOMINEE.Nominee_Relationship.value;
                var Nominee_DOB=document.EMP_NOMINEE.Nominee_DOB.value;
                if(Nominee_DOB=='null')
                Nominee_DOB=''
                //var Nominee_Name=document.EMP_NOMINEE.Nominee_Name.value;
                var Nominee_address1=document.EMP_NOMINEE.Nominee_address1.value;
                var Nominee_address2=document.EMP_NOMINEE.Nominee_address2.value;
                var Nominee_address3=document.EMP_NOMINEE.Nominee_address3.value;
                var Nominee_pincode=document.EMP_NOMINEE.Nominee_pincode.value;
                var Nominee_Pecentshare=parseFloat(document.EMP_NOMINEE.Nominee_Pecentshare.value);
                
                    total=parseFloat(total)+parseFloat(Nominee_Pecentshare);
                var chkper=percentagecheck(Nominee_Pecentshare,total);
                var nomindate=document.EMP_NOMINEE.Nomin_Date.value;
               if(chkper)
                 {
                var age=document.EMP_NOMINEE.Nominee_Age.value;
               if(age==0 ||age==null)
                            age='';
                
               seq=tbody.rows.length;
               seq=seq+1;
                //alert(seq);        
                 
                  /*  document.EMP_NOMINEE.total.value=total;
                    //alert(a);
                  if(a==0)
                     alert("You have given Compelete Nomination Details for Provident Fund");    
                     else
                     alert("You have yet to given share for "+a+"%");
                     }*/
                     
                 //  alert(service.length);
                 var j,age,dob;
                
                   //for(i=0;i<service.length;i++)
                   
               // {
                //j=i+1;
                        /*if((dob==null)||(dob=='null'))
                        dob='';
                        var NOMINEE_DOB=dob;*/
                //      var fund=baseResponse.getElementsByTagName("fund")[i].firstChild.nodeValue;
                      //  alert("rel"+Nominee_Relationship);
                        if(Nominee_Relationship=='null')
                        Nominee_Relationship='';
                       
                        var NOMINEE_Age=age;
                        if(Nominee_address1=='null')
                        {
                        Nominee_address1='';
                        }
                        if(Nominee_address2=='null')
                        {
                        Nominee_address2='';
                        }
                        
                        if(Nominee_address1=='null')
                        {
                        Nominee_address1='';
                        }
                       if(Nominee_pincode=='null' || Nominee_pincode==0)
                       Nominee_pincode='';
                   
                        //alert("per"+PERCENTAGE_SHARE);
                        if(total<=100)
                        {
                        document.EMP_NOMINEE.total.value=parseFloat(total);
                        }
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
                            mycurrent_row.id=seq;
                            var cell=document.createElement("TD");
                            var anc=document.createElement("A");
                            //var url="javascript:loadTable('"+EMPLOYEE_ID+"')";
                            var url="javascript:loadTable('"+seq+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("Edit");
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                        
                                      // alert("adddddddddd");
                                       
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(seq);                         
                          descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                             var descell=document.createElement("TD");
                             var sn=document.createTextNode(Nominee_Name);
                             descell.appendChild(sn);
                             mycurrent_row.appendChild(descell);
                             
                        
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(Nominee_address1);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(Nominee_address2);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(Nominee_address3);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                        var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(Nominee_pincode);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(Nominee_DOB);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                          var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(age);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                         
                         var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(Nominee_Relationship );
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                                        
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(relationship_id);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(nomindate);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                          var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(parseFloat(Nominee_Pecentshare));                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                         
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                       // slno++;
                        tbody.appendChild(mycurrent_row);
                               
                clr();
                }
                }
       }
    
 else   if(Command=='Update')
    {     
    var reduce;
    document.EMP_NOMINEE.Submit.disabled=false;
   document.EMP_NOMINEE.Validate.disabled=true;
     var empid=document.EMP_NOMINEE.txtEmployeeid.value;
     var sno=document.EMP_NOMINEE.txtSNo.value;
            var check=notNull();
            if(check)
            {
               var tbody=document.getElementById("tb");
                 record1=new Array();
                var d=0;
     for(var i=1;i<=tbody.rows.length;i++)
            {
                    var r=document.getElementById(i);
                    var rcells=r.cells;
                    if(sno==i)
                    {
                    reduce=rcells.item(12).firstChild.nodeValue
                    //alert("reduced"+parseFloat(reduce));  
                   
                    }
             total= total+parseFloat(rcells.item(12).firstChild.nodeValue);
             rcells.item(11).firstChild.nodeValue=document.EMP_NOMINEE.Nomin_Date.value;
                }
                 total=parseFloat(total)-parseFloat(reduce);
                total=parseFloat(total);
                 var Nominee_Name='';
                    var Nominee_Relationship='';
                    var relationship_id;
                
                 var fund_id=document.EMP_NOMINEE.fund_id.value;
                    if(document.EMP_NOMINEE.family[0].checked==true)
                     {
                       // Nominee_Name=document.EMP_NOMINEE.member.value;
                        //alert(Nominee_Name);
                        relationship_id='F';
                        
                     }
                    else
                     {
                       
                       
                         relationship_id='O';
                         //Nominee_Relationship=document.EMP_NOMINEE.oNominee_Relationship.value;
                      }
                        Nominee_Name=document.EMP_NOMINEE.omember.value;
                      //alert(relationship_id);
                Nominee_Relationship=document.EMP_NOMINEE.Nominee_Relationship.value;
                var Nominee_DOB=document.EMP_NOMINEE.Nominee_DOB.value;
                if(Nominee_DOB=='null')
                Nominee_DOB=''
                //var Nominee_Name=document.EMP_NOMINEE.Nominee_Name.value;
                var Nominee_address1=document.EMP_NOMINEE.Nominee_address1.value;
                var Nominee_address2=document.EMP_NOMINEE.Nominee_address2.value;
                var Nominee_address3=document.EMP_NOMINEE.Nominee_address3.value;
                var Nominee_pincode=document.EMP_NOMINEE.Nominee_pincode.value;
                var Nominee_Pecentshare=document.EMP_NOMINEE.Nominee_Pecentshare.value;
                total=parseFloat(total)+parseFloat(Nominee_Pecentshare);
                var chkper=percentagecheck(Nominee_Pecentshare,total);
                if(total<=100)
                {
                document.EMP_NOMINEE.total.value=total;
                }
               if(chkper)
               {
                var age=document.EMP_NOMINEE.Nominee_Age.value;
                var nomindate=document.EMP_NOMINEE.Nomin_Date.value;
               if(age==0 ||age==null)
                            age='';
                  var items=new Array();
                    items[0]=sno;
                    items[1]=Nominee_Name;
                    items[2]=Nominee_address1;
                   
                   
                    items[3]=Nominee_address2;
                    
                    items[4]=Nominee_address3;
                  
                    items[5]=Nominee_pincode
                    items[6]=Nominee_DOB;
                  
                    items[7]=age;
                    items[8]=Nominee_Relationship;
                    items[9]=relationship_id;
                    items[10]=nomindate;
                    items[11]=Nominee_Pecentshare;
                    var r=document.getElementById(items[0]);    
                     var rcells=r.cells;
                            rcells.item(1).firstChild.nodeValue=items[0];
                            rcells.item(2).firstChild.nodeValue=items[1];
                            rcells.item(3).firstChild.nodeValue=items[2];
                            rcells.item(4).firstChild.nodeValue=items[3];
                            rcells.item(5).firstChild.nodeValue=items[4];
                            rcells.item(6).firstChild.nodeValue=items[5];
                            rcells.item(7).firstChild.nodeValue=items[6];
                            rcells.item(8).firstChild.nodeValue=items[7];
                            rcells.item(9).firstChild.nodeValue=items[8];
                            rcells.item(10).firstChild.nodeValue=items[9];
                            rcells.item(11).firstChild.nodeValue=items[10];
                            rcells.item(12).firstChild.nodeValue=items[11];
                          //  alert(rcells.item(12).firstChild.nodeValue);
                            alert("Updated Successfully");
                      clr();      
                           // rcells.item(4).firstChild.nodeValue=items[4];
                }            
                            
             }       

    }
 else 
    if(Command=="Validate")
    {
  var sum=0;
     var tb=document.getElementById("tb");
    var t=tb.rows.length;
    if(t>0)
    {   var record = ""; 
  // alert( tb.rows.length);
        for(var i=1;i<=tb.rows.length;i++)
        {
            rid=i;
            var r=document.getElementById(i);
            var rcells=r.cells;
            
            var per = rcells.item(12).firstChild.nodeValue;
           // alert(rcells.item(12).firstChild.nodeValue);
            sum=parseFloat(sum)+parseFloat(per);
        //    alert("sum-------->"+sum);
            
        }
        if(parseFloat(sum)==100)
        {
        
                if(confirm('Do You want to Revalidate all records?'))
                {
                      //  statuswaiting(document.EMP_NOMINEE) ;
                      
                         var fund_id=document.EMP_NOMINEE.fund_id.value;
                        var url="../../../../../Emp_Nomin_Revalid.av?Command=Validate&txtEmployeeid="+empid;
                        url=url+"&fund_id="+fund_id;
                       // alert(url);
                        req.open("POST",url,true);
                        req.onreadystatechange=handleResponse;
                        if(window.XMLHttpRequest)
                                req.send(null);
                        else req.send(); 
                }
        }
        else 
              {
            
               var remining=100-parseFloat(sum);
               alert("Enter the Nomination Details for remaining  "+remining+"%");
           }       
    }
    else
    {
        alert("Enter  Details");
    } 
}

else if(Command=="Load")
    {
           if(!(empid==null || empid.length==0))
           {
                 var fund_id=document.EMP_NOMINEE.fund_id.value;
                // alert(fund_id);
                  //startwaiting(document.EMP_NOMINEE) ;
                var url="../../../../../Emp_Nomin_Revalid.av?Command=Load&txtEmployeeid="+empid;
                url=url+"&fund_id="+fund_id
               // alert("url"+url)
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send(); 
            }
    
    }
    else if(Command=="Delete")
    {
          document.EMP_NOMINEE.Submit.disabled=false;
   document.EMP_NOMINEE.Validate.disabled=true;
            var item1=new Array();
        var item2=new Array();
        var item3=new Array();
        var item4=new Array();
        var item5=new Array();
        var item6=new Array();
        var item7=new Array();
        var item8=new Array();
        var item9=new Array();
        var item10=new Array();
        var item11=new Array();
        var j=0;
          
        if(confirm('Do You want to delete this record?'))
        {
            var sno=document.EMP_NOMINEE.txtSNo.value;
            var check=notNull();
            
               var tbody=document.getElementById("tb");
                var d=0;
                
                var tbody=document.getElementById("tb");
                var d=0;
                 for(var i=1;i<=tbody.rows.length;i++)
            {
                    var r=document.getElementById(i);
                    var rcells=r.cells;
                    if(sno==i)
                    {
                    var deletevalue=rcells.item(12).firstChild.nodeValue;
                    }
                    }
                    document.EMP_NOMINEE.total.value=parseFloat(document.EMP_NOMINEE.total.value)-parseFloat(deletevalue);
     for(var i=1;i<=tbody.rows.length;i++)
            {
                    var r=document.getElementById(i);
                    var rcells=r.cells;
                    if(sno!=i)
                    {
                    item1[j]=rcells.item(2).firstChild.nodeValue;
                    item2[j]=rcells.item(3).firstChild.nodeValue;
                    item3[j]=rcells.item(4).firstChild.nodeValue;
                    item4[j]=rcells.item(5).firstChild.nodeValue;
                    item5[j]=rcells.item(6).firstChild.nodeValue;
                    item6[j]=rcells.item(7).firstChild.nodeValue;
                    item7[j]=rcells.item(8).firstChild.nodeValue;
                    item8[j]=rcells.item(9).firstChild.nodeValue;
                    item9[j]=rcells.item(10).firstChild.nodeValue;
                    item10[j]=rcells.item(11).firstChild.nodeValue;
                    item11[j]=rcells.item(12).firstChild.nodeValue;
                    //alert(item1[j]+" "+item2[j]+" "+item3[j]+" "+item4[j]+" "+item5[j]+" "+item6[j]+" "+item7[j]+" "+item8[j]+" "+item9[j]+" "+item10[j]);
                         j++;
                } 
               }
                var tb=document.getElementById("tb");
                var t=tb.rows.length;   
                for(var i=t-1;i>=0;i--)
                {
                     //if(sno==i)
                      tb.deleteRow(i);
                } 
                for(var i=0;i<j;i++)
                {
                       rowDetails(item1[i],item2[i],item3[i],item4[i],item5[i],item6[i],item7[i],item8[i],item9[i],item10[i],item11[i]);  
                }
            
             }
clr();
        }
    
    
     else if(Command=="Clear")
            {
               
                clr1();
                document.EMP_NOMINEE.cmdadd.style.display="block";
                document.EMP_NOMINEE.cmdupdate.style.display="none";
                //document.EMP_NOMINEE.cmdadd.disabled=false;
               // document.EMP_NOMINEE.cmdupdate.disabled=true;
                document.EMP_NOMINEE.cmddelete.disabled=true;
               
               
                
            }
   
    
  /*  else if(Command=='Submit')
    {
       
        var check=notNull(null);
        if(check )
        {
                //startwaiting(document.EMP_NOMINEE) ;
                var url="../../../../../Emp_Nomin_Revalid.av?Command=Submit&txtEmployeeid="+empid;
                req.open("POST",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }*/
}

function offclr()
{
    
    document.EMP_NOMINEE.txtOffice_Id.value='';
    
    document.EMP_NOMINEE.txtOffice_Name.value='';
    document.EMP_NOMINEE.txtOffice_Address1.value='';
   // document.EMP_NOMINEE.txtOffice_Address2.value='';
   // document.EMP_NOMINEE.txtOffice_City.value='';
       
   

}


function rowDetails(a,b,c,d,e,f,g,h,i,j,k)
{
var tbody=document.getElementById("tb");
seq=tbody.rows.length;
seq++;
       
   
                 
                 //alert(seq);
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
                 
                 var cell=document.createElement("TD");
                var anc=document.createElement("A");
                //var url="javascript:loadTable('"+EMPLOYEE_ID+"')";
                var url="javascript:loadTable('"+seq+"')";
                anc.href=url;
                var txtedit=document.createTextNode("Edit");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
                mycurrent_row.appendChild(cell);
                
                 var sno =document.createElement("TD");   
                 var no=document.createTextNode(seq);                        
                 sno.appendChild(no);  
                 mycurrent_row.appendChild(sno);  
                 
                 var cell2 =document.createElement("TD");   
                 var cat=document.createTextNode(a);                        
                 cell2.appendChild(cat);  
                 mycurrent_row.appendChild(cell2);   
                         
                         
                 var cell3 =document.createElement("TD");   
                 var icode=document.createTextNode(b);   
                 cell3.appendChild(icode);   
                 mycurrent_row.appendChild(cell3);
                        
                 var cell4 =document.createElement("TD");   
                 var idesc=document.createTextNode(c);                        
                 cell4.appendChild(idesc);
                 mycurrent_row.appendChild(cell4);
                                               
                 var cell5 =document.createElement("TD");   
                 var qty=document.createTextNode(d);                        
                 cell5.appendChild(qty);     
                 mycurrent_row.appendChild(cell5);
                 
                 var cell7 =document.createElement("TD");   
                 var uom=document.createTextNode(e);                        
                 cell7.appendChild(uom);     
                 mycurrent_row.appendChild(cell7);
                 
                 var cell6 =document.createElement("TD");   
                 var rem=document.createTextNode(f);                        
                 cell6.appendChild(rem);     
                 mycurrent_row.appendChild(cell6);
                
                var cell6 =document.createElement("TD");   
                 var rem=document.createTextNode(g);                        
                 cell6.appendChild(rem);     
                 mycurrent_row.appendChild(cell6);
                
                var cell6 =document.createElement("TD");   
                 var rem=document.createTextNode(h);                        
                 cell6.appendChild(rem);     
                 mycurrent_row.appendChild(cell6);
                
                var cell6 =document.createElement("TD");   
                 var rem=document.createTextNode(i);                        
                 cell6.appendChild(rem);     
                 mycurrent_row.appendChild(cell6);
                
                var cell6 =document.createElement("TD");   
                 var rem=document.createTextNode(j);                        
                 cell6.appendChild(rem);     
                 mycurrent_row.appendChild(cell6);
                
                var cell6 =document.createElement("TD");   
                 var rem=document.createTextNode(k);                        
                 cell6.appendChild(rem);     
                 mycurrent_row.appendChild(cell6);
               
              //seq++;       
               //  seq++;
                 tbody.appendChild(mycurrent_row); 
}    

function checkdeptsel()
{
    // if((document.EMP_NOMINEE.txtDept_Id.value==0)||(document.EMP_NOMINEE.txtDept_Id.value.length==0))
        {
                offclr();
        }
}
function radioclick()
{
if(document.EMP_NOMINEE.radioage[0].checked==true)
           {
           document.EMP_NOMINEE.Nominee_Age.value='';
           document.EMP_NOMINEE.Nominee_Age.disabled=true;
           document.EMP_NOMINEE.Nominee_DOB.disabled=false;
           document.EMP_NOMINEE.fromimg.disabled=false;
           }
          
}

function radioclick1()
{
           if(document.EMP_NOMINEE.radioage[1].checked==true)
           {
           document.EMP_NOMINEE.Nominee_DOB.value='';
           document.EMP_NOMINEE.Nominee_DOB.disabled=true;
           document.EMP_NOMINEE.Nominee_Age.disabled=false;
           //document.EMP_NOMINEE.fromimg.disabled=true;
          // document.EMP_NOMINEE.fromimg.style.display="none";
           //alert(document.getElementById("fromimg"));
         //  document.getElementById("fromimg").disabled=true;
           }

}
function checkradio()
{
        if(document.EMP_NOMINEE.radioage[1].checked==true)
        {
            alert("You have Choosen Age,So you are not allowed to give Date of birth");
            return false;
        }
        return true;

}

function familyclick()
{
//alert("hello");
if(document.EMP_NOMINEE.family[0].checked==true)
    {
    
    var id=document.getElementById("member");
    id.style.display="block";
    var id1=document.getElementById("omember");
    id1.style.display="none";
    clr();
    disablefun();
    var id2=document.getElementById("hrel");
    id2.style.display="block";
    // var id3=document.getElementById("hdob");
    //id3.style.display="block";
    }
    else
    {
    //alert("click");
        var id=document.getElementById("omember");
        id.style.display="block";
        var id1=document.getElementById("member");
        id1.style.display="none";
       var id2=document.getElementById("hrel");
       id2.style.display="none";
//var id3=document.getElementById("hdob");
  //  id3.style.display="none";
    document.EMP_NOMINEE.Nominee_Age.disabled=true;
    clr();
    setfun();
    }
}
function disablefun()
{         //  alert("click"); 
            document.EMP_NOMINEE.Nominee_Relationship.disabled=true;
            document.EMP_NOMINEE.Nominee_address1.disabled=true;
            document.EMP_NOMINEE.Nominee_address2.disabled=true;
            document.EMP_NOMINEE.Nominee_address3.disabled=true;
            document.EMP_NOMINEE.Nominee_DOB.disabled=true;
            document.EMP_NOMINEE.Nominee_pincode.disabled=true;
            document.EMP_NOMINEE.Nominee_Age.disabled=true;
}

function setfun()
{
            document.EMP_NOMINEE.Nominee_Relationship.disabled=false;
            document.EMP_NOMINEE.Nominee_address1.disabled=false;
            document.EMP_NOMINEE.Nominee_address2.disabled=false;
            document.EMP_NOMINEE.Nominee_address3.disabled=false;
            document.EMP_NOMINEE.Nominee_DOB.disabled=false;
            document.EMP_NOMINEE.Nominee_pincode.disabled=false;
          //  document.EMP_NOMINEE.Nominee_Age.disabled=false;
}

function clr()
{
//alert("clr()");
           document.EMP_NOMINEE.cmdupdate.style.display='none';
           document.EMP_NOMINEE.cmdupdate.disabled=true;
            document.EMP_NOMINEE.cmddelete.disabled=true;
            document.EMP_NOMINEE.cmdadd.disabled=false;
            document.EMP_NOMINEE.fund_id.disabled=false;
            document.EMP_NOMINEE.txtSNo.value='';
            //document.EMP_NOMINEE.fund_id.selectedIndex=0;
            document.EMP_NOMINEE.member.selectedIndex=0;
            document.EMP_NOMINEE.Nominee_Relationship.value='';
            document.EMP_NOMINEE.Nominee_address1.value="";
            document.EMP_NOMINEE.Nominee_address2.value='';
            document.EMP_NOMINEE.Nominee_address3.value='';
            document.EMP_NOMINEE.Nominee_DOB.value='';
            document.EMP_NOMINEE.Nominee_pincode.value='';
            document.EMP_NOMINEE.Nominee_Pecentshare.value='';
            document.EMP_NOMINEE.Nominee_Age.value='';
            document.EMP_NOMINEE.omember.value='';
            document.EMP_NOMINEE.Nomin_Date.value='';
           // document.EMP_NOMINEE.family[0].checked=true;
 if(document.EMP_NOMINEE.family[0].checked==true)
                {
                            
                            document.EMP_NOMINEE.omember.style.display='none';
                            document.EMP_NOMINEE.member.style.display='block';
                //alert("aloy");
                }
                else
                {
                    document.EMP_NOMINEE.omember.style.display='block';
                    document.EMP_NOMINEE.member.style.display='none';
                
                  relationship_id='O';

                 }
               
//document.EMP_NOMINEE.txtDept_Id.value='0';
//offclr();

//alert('allclear');
//doFunction('LastDate',null);


}


function clr1()
{           
            
             var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
            document.EMP_NOMINEE.total.value="";            
            document.EMP_NOMINEE.txtGpf.value="";
            document.EMP_NOMINEE.txtdob.value="";
            document.EMP_NOMINEE.txtEmployee.value="";
            document.EMP_NOMINEE.cmdadd.disabled=false;
            document.EMP_NOMINEE.fund_id.disabled=false;
            document.EMP_NOMINEE.txtSNo.value='';
            document.EMP_NOMINEE.txtEmployeeid.value="";
            //document.EMP_NOMINEE.fund_id.selectedIndex=0;
            document.EMP_NOMINEE.member.selectedIndex=0;
            document.EMP_NOMINEE.Nominee_Relationship.value='';
            document.EMP_NOMINEE.Nominee_address1.value="";
            document.EMP_NOMINEE.Nominee_address2.value='';
            document.EMP_NOMINEE.Nominee_address3.value='';
            document.EMP_NOMINEE.Nominee_DOB.value='';
            document.EMP_NOMINEE.Nominee_pincode.value='';
            document.EMP_NOMINEE.Nominee_Pecentshare.value='';
            document.EMP_NOMINEE.Nominee_Age.value='';
            document.EMP_NOMINEE.omember.value='';
            document.EMP_NOMINEE.Submit.disabled=true;
   document.EMP_NOMINEE.Validate.disabled=false;
 if(document.EMP_NOMINEE.family[0].checked==true)
                {
                            
                            document.EMP_NOMINEE.omember.style.display='none';
                            document.EMP_NOMINEE.member.style.display='block';
                //alert("aloy");
                }
                else
                {
                    document.EMP_NOMINEE.omember.style.display='block';
                    document.EMP_NOMINEE.member.style.display='none';
                
                  relationship_id='O';

                 }
               
//document.EMP_NOMINEE.txtDept_Id.value='0';
//offclr();

//alert('allclear');
//doFunction('LastDate',null);


}

function submitResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
          //  alert('test'+Command);
            if(Command=="Submit")
            {
               // alert("load handlwersffffffffffffffff");
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                if(flag=="failure")
                {       
                    alert('Failed to Insert');
                }
                else
                {
                    document.EMP_NOMINEE.Submit.disabled=true;
                    document.EMP_NOMINEE.Validate.disabled=false;
                    alert("Records Saved Sucessfully");              
            }  }
        }
    }
}
function handleResponse()
{


    if(req.readyState==4)
    {
        if(req.status==200)
        {
                    
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
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
                         document.EMP_NOMINEE.txtEmployeeid.value="";
                          var tbody=document.getElementById("tb");
           
                        if(tbody.rows.length >0)
                        {        
                                 if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                        tbody.innerText='';
                                else 
                                    tbody.innerHTML='';
                        }
                         document.EMP_NOMINEE.txtEmployeeid.focus();
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
             else if(Command=="Validate")
            {
               
               Validatefun(baseResponse);
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
            else if(Command=="loadnominee")
            {
           // alert("load nominee");
                                    //var LIST_SL_NO =baseResponse.getElementsByTagName("LIST_SL_NO")[i].firstChild.nodeValue;
                               var address1,address2,address3;     
                               address1=baseResponse.getElementsByTagName("ADDRESS1")[0].firstChild.nodeValue;
                               if(address1=='null')
                               address1='';
                               address2=baseResponse.getElementsByTagName("ADDRESS2")[0].firstChild.nodeValue;
                               if(address2=='null')
                               address2='';
                               address3=baseResponse.getElementsByTagName("ADDRESS3")[0].firstChild.nodeValue;
                               if(address3=='null')
                               address3='';
                               
                                    document.EMP_NOMINEE.Nominee_address1.value=address1;
                                    //document.EMP_NOMINEE.Nominee_address2.value=baseResponse.getElementsByTagName("NOMINEE_NAME")[i].firstChild.nodeValue;
                                    document.EMP_NOMINEE.Nominee_address2.value=address2;
                                    document.EMP_NOMINEE.Nominee_address3.value=address3;
                                    document.EMP_NOMINEE.Nominee_pincode.value=baseResponse.getElementsByTagName("PINCODE")[0].firstChild.nodeValue;
                         if(document.EMP_NOMINEE.Nominee_pincode.value=='null' || document.EMP_NOMINEE.Nominee_pincode.value==0)
                                document.EMP_NOMINEE.Nominee_pincode.value='';
                                document.EMP_NOMINEE.Nominee_Relationship.value=baseResponse.getElementsByTagName("RELATIONSHIP_ID")[0].firstChild.nodeValue;
                                var dob=baseResponse.getElementsByTagName("DOB")[0].firstChild.nodeValue;
                            if ((dob==null) ||(dob=='null'))
                            {         dob='';
                            
                                document.EMP_NOMINEE.radioage[1].checked=true;
                                document.EMP_NOMINEE.radioage[0].checked=false;
                            // radioclick1();
                            }
                            
                            document.EMP_NOMINEE.Nominee_DOB.value=dob;
                            var age=baseResponse.getElementsByTagName("AGE")[0].firstChild.nodeValue;
                            if((age==null)||(age=='null')||(age==0))
                            {
                                age='';
                                
                                document.EMP_NOMINEE.radioage[0].checked=true;
                                document.EMP_NOMINEE.radioage[1].checked=false;
                            // radioclick();
                            }
                            
                                 document.EMP_NOMINEE.Nominee_Age.value=age;
                            //document.EMP_NOMINEE.Nominee_address2.value=baseResponse.getElementsByTagName("fund")[i].firstChild.nodeValue;
                       // var rel=baseResponse.getElementsByTagName("rel")[i].firstChild.nodeValue;
                       // document.EMP_NOMINEE.Nominee_Percentage_share.value=baseResponse.getElementsByTagName("PERCENTAGE_SHARE")[0].firstChild.nodeValue;              
                       //alert("Enter the Percentage Share Value");
            
            }
           
           else if(Command=="Load")
            {
           //alert("load handlwersffffffffffffffff");
                   // loadPage(1)
                   var tbody=document.getElementById("tb");
                   seq=tbody.rows.length;
                   seq=seq+1;
                  
                   try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                   var service =baseResponse.getElementsByTagName("FUND_ID");
                   var total=baseResponse.getElementsByTagName("total")[0].firstChild.nodeValue;
                   if(total>0)
                   {
                   var a=100-total;
                  // alert("total"+a);
                
                   
                    document.EMP_NOMINEE.total.value=total;
                    //alert(a);
                
                 //  alert(service.length);
                 var j,age,dob;
                
                   for(i=0;i<service.length;i++)
                   
                {
               
                        var EMPLOYEE_ID = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
                       // alert("empid"+EMPLOYEE_ID);
                        var FUND_ID =baseResponse.getElementsByTagName("FUND_ID")[i].firstChild.nodeValue;
                        var LIST_SL_NO =baseResponse.getElementsByTagName("LIST_SL_NO")[i].firstChild.nodeValue;
                        var NOMINEE_ADDRESS1=baseResponse.getElementsByTagName("NOMINEE_ADDRESS1")[i].firstChild.nodeValue;
                        var NOMINEE_NAME=baseResponse.getElementsByTagName("NOMINEE_NAME")[i].firstChild.nodeValue;
                        var NOMINEE_ADDRESS2=baseResponse.getElementsByTagName("NOMINEE_ADDRESS2")[i].firstChild.nodeValue;
                        var NOMINEE_ADDRESS3=baseResponse.getElementsByTagName("NOMINEE_ADDRESS3")[i].firstChild.nodeValue;
                        var NOMINEE_PINCODE=baseResponse.getElementsByTagName("NOMINEE_PINCODE")[i].firstChild.nodeValue;
                        var RELATIONSHIP_ID=baseResponse.getElementsByTagName("RELATIONSHIP_ID")[i].firstChild.nodeValue;
                        dob=baseResponse.getElementsByTagName("NOMINEE_DOB")[i].firstChild.nodeValue;
                        if((dob==null)||(dob=='null'))
                        dob='';
                        var NOMINEE_DOB=dob;
                         var fund=baseResponse.getElementsByTagName("fund")[i].firstChild.nodeValue;
                        var rel=baseResponse.getElementsByTagName("rel")[i].firstChild.nodeValue;
                        var PERCENTAGE_SHARE=baseResponse.getElementsByTagName("PERCENTAGE_SHARE")[i].firstChild.nodeValue;              
                        age=baseResponse.getElementsByTagName("NOMINEE_AGE")[i].firstChild.nodeValue;
                        nomindate=baseResponse.getElementsByTagName("NOMINDATE")[i].firstChild.nodeValue;
                        if((age==0)|| (age==null))
                        age='';
                        var NOMINEE_Age=age;
                        if(NOMINEE_ADDRESS2=='null')
                        {
                        NOMINEE_ADDRESS2='';
                        }
                        if(NOMINEE_ADDRESS3=='null')
                        {
                        NOMINEE_ADDRESS3='';
                        }
                        
                        if(NOMINEE_ADDRESS1=='null')
                        {
                        NOMINEE_ADDRESS1='';
                        }
                        if(NOMINEE_PINCODE=='0')
                          NOMINEE_PINCODE='';
                        //alert("per"+PERCENTAGE_SHARE);
                        if(RELATIONSHIP_ID=='null')
                        RELATIONSHIP_ID='';
                        
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
                       
                            mycurrent_row.id=seq;
                            var cell=document.createElement("TD");
                            var anc=document.createElement("A");
                            //var url="javascript:loadTable('"+EMPLOYEE_ID+"')";
                            var url="javascript:loadTable('"+seq+"')";
                            anc.href=url;
                            var txtedit=document.createTextNode("Edit");
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                        
                        
                       
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(LIST_SL_NO);                         
                      descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                         var descell=document.createElement("TD");
                         var sn=document.createTextNode(NOMINEE_NAME);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                        
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(NOMINEE_ADDRESS1);                         
                     descell.appendChild(sn);
                         
                         mycurrent_row.appendChild(descell);
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(NOMINEE_ADDRESS2);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(NOMINEE_ADDRESS3);                         
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                        var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(NOMINEE_PINCODE);                         
                     descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(NOMINEE_DOB);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                          var descell=document.createElement("TD");                        
                         var sn=document.createTextNode(NOMINEE_Age);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                         
                         var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(RELATIONSHIP_ID);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                         
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(rel);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(nomindate);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                          var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(PERCENTAGE_SHARE);                        
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                         
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                        
                     /*    var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(fund);
                         sc.type="hidden";
                         sc.name="fund";
                         sc.text=fund;
                         sc.value=fund;
                        //descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                        
                         var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(rel);
                         sc.type="hidden";
                         sc.name="rel";
                         sc.text=rel;
                         sc.value=rel;
                        //descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);*/
                         
                         // alert('ok');        
                       seq++;
                         
            }
           
            }
            
        }  
      }
  } 
}

function Validatefun(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
    {
     alert("Records are Freezed");  
     clr1();
           }
      else
      alert("Not Freezed");
}

function addfun(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    // alert(flag);  
    if(flag=="success")
    {
        var id=baseResponse.getElementsByTagName("genid")[0].firstChild.nodeValue;
        document.EMP_NOMINEE.txtSNo.value=id;
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
                            //alert(parseFloat(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseFloat(service.length/__pagination);
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
           //doFunction('Load','null');
            clr();
        //////////////////////////////////////////////////////////////////////////////
        alert("Records Added successfully");
        //window.location.reload(true);
    
    }
    else if(flag=='failure1')
    {
            
            alert("Total Share should not be Exceed 100");
            clr();
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
                        var exist=baseResponse.getElementsByTagName("exist")[0].firstChild.nodeValue;
                        //alert("exist"+exist);
                        if(exist=="exist")
                        {
                        var status=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
                      //  alert(status);
                        if (status=='FR')
                        {
                                var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
                                var edob=baseResponse.getElementsByTagName("edob")[0].firstChild.nodeValue;
                                var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
                                document.EMP_NOMINEE.txtEmployee.value=ename;
                                if(edob=="-")
                                edob="";
                                document.EMP_NOMINEE.txtdob.value=edob;
                                
                                if(egpf==0)
                                egpf="";
                                document.EMP_NOMINEE.txtGpf.value=egpf;
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
                                            //alert(parseFloat(items1.length/__pagination));
                                            if(service.length>0)
                                            {
                                                    totalblock=parseFloat(service.length/__pagination);
                                                    if(service.length%__pagination!=0)
                                                    {
                                                            totalblock=totalblock+1;
                                                    }
                                                    
                                                    
                                                    var cmbpage=document.getElementById("cmbpage");
                                                   
                                                   try{ cmbpage.innerHTML="";
                                                   }catch(e){
                                                    cmbpage.innerText="";
                                                   }
                                                     
                                                    
                                                    for(i=1;i<= block;i++)
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
                                var first=document.getElementById("member");
                                        // alert("dfhdhdghhg"+first);
                                first.innerHTML="";
                                
                                var sel=baseResponse.getElementsByTagName("select")[0];
                                // alert("succuss 4 combo");
                                
                                var options=baseResponse.getElementsByTagName("option");
                                var htop=document.createElement("OPTION");
                                htop.text="--Select Nominee Name--";
                                try
                                {
                                first.add(htop);
                                }
                                catch(e)
                                {
                                first.add(htop,null);
                                }
                                for(i=0;i<options.length;i++)
                                {
                                
                                var desc=options[i].getElementsByTagName("member")[0].firstChild.nodeValue;
                                var id=options[i].getElementsByTagName("member")[0].firstChild.nodeValue;
                                var htoption=document.createElement("OPTION");
                                htoption.text=desc;
                                htoption.value=id;
                                try
                                {
                                first.add(htoption);
                                }
                                catch(e)
                                {
                                first.add(htoption,null);
                                }
                                       
                                }
                                
                                
                                }
                               // alert("Employee Already given Details of Provident Fund");
                                //document.EMP_NOMINEE.txtEmployeeid.value="";
                                doFunction('Load','null');
                        }
                        else
                            alert("Employee's Nomination Details yet to be Freezed");
                        }
            else 
            {
             alert("No details Exists,Enter the Nomination details in the  Creation Form");
             }
         //  doFunction('Load','null');
            clr();
        /*    var pf=baseResponse.getElementsByTagName("pf")[0].firstChild.nodeValue;                
    var gf=baseResponse.getElementsByTagName("gf")[0].firstChild.nodeValue;                
    var inf=baseResponse.getElementsByTagName("inf")[0].firstChild.nodeValue;                
   // alert("pf"+pf+"gf"+gf+"inf"+inf);
    if(pf==1)
    {
    document.EMP_NOMINEE.pf[0].checked=true;
    document.EMP_NOMINEE.pf[1].checked=false;           
    document.EMP_NOMINEE.pf[1].disabled=true;
    document.EMP_NOMINEE.pf[0].disabled=false;
    }
    else
   {
    document.EMP_NOMINEE.pf[1].checked=true;
    document.EMP_NOMINEE.pf[0].checked=false;           
    document.EMP_NOMINEE.pf[0].disabled=true;
    document.EMP_NOMINEE.pf[1].disabled=false;
    
    }
    if(inf==1)
    {
    document.EMP_NOMINEE.inf[0].checked=true;
    document.EMP_NOMINEE.inf[1].checked=false;           
    document.EMP_NOMINEE.inf[1].disabled=true;
    document.EMP_NOMINEE.inf[0].disabled=false;
    }
    else
   {
    document.EMP_NOMINEE.inf[1].checked=true;
    document.EMP_NOMINEE.inf[0].checked=false; 
    document.EMP_NOMINEE.inf[0].disabled=true;
    document.EMP_NOMINEE.inf[1].disabled=false;
    }
    
    if(gf==1)
    {
    document.EMP_NOMINEE.gf[0].checked=true;
    document.EMP_NOMINEE.gf[1].checked=false;           
    document.EMP_NOMINEE.gf[1].disabled=true;
    document.EMP_NOMINEE.gf[0].disabled=false;

    }
    else
   {
    document.EMP_NOMINEE.gf[1].checked=true;
    document.EMP_NOMINEE.gf[0].checked=false;           
    document.EMP_NOMINEE.gf[0].disabled=true;
    document.EMP_NOMINEE.gf[1].disabled=false;

    }*/
                       
            
    }
    else if(flag=="failure1")
    {
            var id=document.EMP_NOMINEE.txtEmployeeid.value;
            //alert("Can not Update SR. Because Employee Id "+id+" is not under your Office!");
            alert("SR controling office for this employee is different from your office!");
            document.EMP_NOMINEE.txtEmployee.value="";
            document.EMP_NOMINEE.txtdob.value="";
            document.EMP_NOMINEE.txtGpf.value="";
            document.EMP_NOMINEE.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_NOMINEE.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure2")
    {
            var id=document.EMP_NOMINEE.txtEmployeeid.value;
            alert("You have no Current Posting. Can not update SR for "+id+"!");
            document.EMP_NOMINEE.txtEmployee.value="";
            document.EMP_NOMINEE.txtdob.value="";
            document.EMP_NOMINEE.txtGpf.value="";
            document.EMP_NOMINEE.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_NOMINEE.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure3")
    {
            var id=document.EMP_NOMINEE.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not update SR!");
            document.EMP_NOMINEE.txtEmployee.value="";
            document.EMP_NOMINEE.txtdob.value="";
            document.EMP_NOMINEE.txtGpf.value="";
            document.EMP_NOMINEE.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_NOMINEE.txtEmployeeid.focus();
            clr();
    }
     else if(flag=="failure4")
    {
            var id=document.EMP_NOMINEE.txtEmployeeid.value;
            alert("Can not update SR. Access Denined!");
            document.EMP_NOMINEE.txtEmployee.value="";
            document.EMP_NOMINEE.txtdob.value="";
            document.EMP_NOMINEE.txtGpf.value="";
            document.EMP_NOMINEE.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.EMP_NOMINEE.txtEmployeeid.focus();
            clr();
    }
    else
    {
        
               
        alert('Enter a Valid Employee Number');
        document.EMP_NOMINEE.txtEmployee.value="";
        document.EMP_NOMINEE.txtdob.value="";
        document.EMP_NOMINEE.txtGpf.value="";
        document.EMP_NOMINEE.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        document.EMP_NOMINEE.txtEmployeeid.focus();
        clr();
    }


}



function changepage()
{
//alert('test');
var page=document.EMP_NOMINEE.cmbpage.value;
loadPage(parseFloat(page));

}




function changepagesize()
{

           __pagination=document.EMP_NOMINEE.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
        if(service)
        {
           
                             block=0;
                            //alert(parseFloat(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseFloat(service.length/__pagination);
                                    if(service.length%__pagination!=0)
                                    {
                                             block= block+1;
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
function checkPin()
{
  if(parseFloat(document.EMP_NOMINEE.Nominee_pincode.value.length)>0)
{
  if(parseFloat(document.EMP_NOMINEE.Nominee_pincode.value.length)<6)
    {
        alert("Pincode length must be in 6 digits");
        document.EMP_NOMINEE.Nominee_pincode.value='';
        document.EMP_NOMINEE.Nominee_pincode.focus();
        return false;
    }
    if ((document.EMP_NOMINEE.Nominee_pincode.value< '600000' )||(document.EMP_NOMINEE.Nominee_pincode.value> '700000'))
    {
        alert ("Pincode must be within 600000-700000 ");
        document.EMP_NOMINEE.Nominee_pincode.value='';
        document.EMP_NOMINEE.Nominee_pincode.focus();
        
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


function percentagecheck(e,t)
{
if(document.EMP_NOMINEE.Nominee_Pecentshare.value>100)
    { 
    alert("Percentage of Share should not Exceed 100");
        document.EMP_NOMINEE.Nominee_Pecentshare.value='';
         document.EMP_NOMINEE.Nominee_Pecentshare.focus();
         return 0;
   }
      else if(parseFloat(t)>100)
       {
        alert("Total Percentage Share Value should not be Exceeding 100");
         document.EMP_NOMINEE.Nominee_Pecentshare.value='';
         document.EMP_NOMINEE.Nominee_Pecentshare.focus();
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
          //document.EMP_NOMINEE.txtSNo.focus();
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
  //  alert(scod+"value");
    var rel;
    
    clr();
             document.EMP_NOMINEE.cmdupdate.style.display="block";
             document.EMP_NOMINEE.fund_id.disabled=true;
             document.EMP_NOMINEE.cmdadd.disabled=true;
               document.EMP_NOMINEE.cmdupdate.disabled=false;
                document.EMP_NOMINEE.cmddelete.disabled=false;
                
    var r=document.getElementById(scod);
    //alert(r);
    var rcells=r.cells;
    
    var age=0;
    var dob=null;
    try{
   /* document.EMP_NOMINEE.txtSNo1.value=rcells.item(1).lastChild.nodeValue;
     document.EMP_NOMINEE.txtSNo.value=rcells.item(1).firstChild.value;*/
     
    //document.EMP_NOMINEE.fund_id.value=rcells.item(1).firstChild.value;
    document.EMP_NOMINEE.txtSNo.value=rcells.item(1).firstChild.nodeValue;
    document.EMP_NOMINEE.Nominee_Relationship.value=rcells.item(9).firstChild.nodeValue;
    document.EMP_NOMINEE.Nomin_Date.value=rcells.item(11).firstChild.nodeValue;
    //alert(rcells.item(11).firstChild.nodeValue);
         rel=rcells.item(10).firstChild.nodeValue;
         
        // alert("name"+rcells.item(2).firstChild.nodeValue);
             if(rel=='F')
                {
                document.EMP_NOMINEE.family[0].checked=true;
                document.EMP_NOMINEE.family[1].checked=false;
                document.EMP_NOMINEE.omember.style.display="block";
                document.EMP_NOMINEE.member.style.display="none";
                document.EMP_NOMINEE.omember.value=rcells.item(2).firstChild.nodeValue;
           
                }
                else
                {
                document.EMP_NOMINEE.family[0].checked=false;
                document.EMP_NOMINEE.family[1].checked=true;
                 document.EMP_NOMINEE.member.style.display="none";
                document.EMP_NOMINEE.omember.style.display="block";
                document.EMP_NOMINEE.omember.value=rcells.item(2).firstChild.nodeValue;
                                 }
                
     
     document.EMP_NOMINEE.Nominee_address1.value=rcells.item(3).firstChild.nodeValue;
     
     document.EMP_NOMINEE.Nominee_address2.value=rcells.item(4).firstChild.nodeValue;
     document.EMP_NOMINEE.Nominee_address3.value=rcells.item(5).firstChild.nodeValue;
     document.EMP_NOMINEE.Nominee_pincode.value=rcells.item(6).firstChild.nodeValue;
     
     age=rcells.item(8).firstChild.nodeValue;
      if(age=='' ||age==null|| age=='')
                   {
                   age='';                
//alert("age");
                    document.EMP_NOMINEE.radioage[0].checked=true;
                    document.EMP_NOMINEE.radioage[1].checked=false;
                   
                   }
     document.EMP_NOMINEE.Nominee_Age.value=age;
     //alert(rcells.item(11).firstChild.nodeValue);
     dob=rcells.item(7).firstChild.nodeValue;
      //alert(dob);
     document.EMP_NOMINEE.Nominee_Pecentshare.value=rcells.item(12).firstChild.nodeValue;
   if((dob==null) || (dob=='null')||(dob.lenght==0)||(dob==''))
      {
      dob='';
      document.EMP_NOMINEE.radioage[1].checked=true;
       document.EMP_NOMINEE.radioage[0].checked=false;
       //alert("hello");
     
      //document.EMP_NOMINEE.radioage[1].checked==true
      }
      
      document.EMP_NOMINEE.Nominee_DOB.value=dob;
    
     setfun();
    
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
          document.EMP_NOMINEE.cmdadd.style.display="block";
         document.EMP_NOMINEE.cmdupdate.style.display="none";  
          document.EMP_NOMINEE.fund_id.disabled=false;
        // changeempstatus();
         alert("Records are Updated");
         //doFunction('Load','null');
         
    /*document.EMP_NOMINEE.txtDateFrom.disabled=false;
    var i=document.getElementById("fromimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EMP_NOMINEE.optDateFrom[0].disabled=false;
    document.EMP_NOMINEE.optDateFrom[1].disabled=false;
    
    document.EMP_NOMINEE.txtDateTo.disabled=false;
    var i=document.getElementById("toimg");
    i.disabled=false;
    i.alt="Show Calender";
    document.EMP_NOMINEE.optDateTo[0].disabled=false;
    document.EMP_NOMINEE.optDateTo[1].disabled=false;
     */    
     //doFunction('Load','null');
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
        var r=document.getElementById(document.EMP_NOMINEE.txtSNo.value);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        */
        //clr();
         if(document.EMP_NOMINEE.family[0].checked==true)
                {
                                 
                document.EMP_NOMINEE.omember.style.display='none';
                document.EMP_NOMINEE.member.style.display='block';
                //alert("aloy");
                }
                else
                {
                document.EMP_NOMINEE.omember.style.display='block';
                document.EMP_NOMINEE.member.style.display='none';
                          

                 }
               
          document.EMP_NOMINEE.fund_id.disabled=false;
        document.EMP_NOMINEE.cmdadd.style.display="block";
        document.EMP_NOMINEE.cmdupdate.style.display="none";
       // document.EMP_NOMINEE.cmdadd.disabled=false;
       // document.EMP_NOMINEE.cmdupdate.disabled=true;
        document.EMP_NOMINEE.cmddelete.disabled=true;
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
                            //alert(parseFloat(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseFloat(service.length/__pagination);
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
      //  doFunction('Load','null');
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
    if(document.EMP_NOMINEE.txtEmployeeid.value==null || document.EMP_NOMINEE.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.EMP_NOMINEE.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}

function onSubmit()
{
   
    var sum=0;
    var empid=document.EMP_NOMINEE.txtEmployeeid.value;
    var fund_id=document.EMP_NOMINEE.fund_id.value;
   // var nomindate=documemt.EMP_NOMINEE.Nomin_Date.value;
    var tb=document.getElementById("tb");
    var t=tb.rows.length;
    if(t>0)
    {   var record = ""; 
  // alert( tb.rows.length);
        for(var i=1;i<=tb.rows.length;i++)
        {
            rid=i;
            var r=document.getElementById(i);
            var rcells=r.cells;
            
            var sno = rcells.item(1).firstChild.nodeValue;
            var name = rcells.item(2).firstChild.nodeValue; 
            var adrs1 = rcells.item(3).firstChild.nodeValue;
            var adrs2 = rcells.item(4).firstChild.nodeValue;
            var adrs3 = rcells.item(5).firstChild.nodeValue;
            var pcode = rcells.item(6).firstChild.nodeValue;
            var db = rcells.item(7).firstChild.nodeValue;
            var age = rcells.item(8).firstChild.nodeValue;
            var rel = rcells.item(9).firstChild.nodeValue;
            var rtype = rcells.item(10).firstChild.nodeValue;
            var nomindate=rcells.item(11).firstChild.nodeValue;
            var per = rcells.item(12).firstChild.nodeValue;
           // alert(rcells.item(12).firstChild.nodeValue);
            sum=parseFloat(sum)+parseFloat(per);
        //    alert("sum-------->"+sum);
            if(adrs1 == '')
                adrs1 = "null";
            if(adrs2 == '')
                adrs2 = "null";
            if(adrs3 == '')
                adrs3 = "null";
            if(age == '')
                age = '0';
            if(pcode=='')
                pcode=0;
            if(rel=='')
                rel="null";
            record += sno +","+name+","+adrs1+","+adrs2+","+adrs3+","+pcode+","+db+","+age+","+rel+","+rtype+","+per+"~";
            
        }
        record = record.substring(0,record.lastIndexOf("~"));
        if(parseFloat(sum)==100)
        {
                var url="../../../../../Emp_Nomin_Revalid.av?Command=submit&record="+record+"&txtEmployeeid="+empid+"&fund_id="+fund_id+"&nomindate="+nomindate;
               // alert(url);
                var req=getTransport();
                req.open("POST",url,true);
                req.onreadystatechange=function()
                {
                    submitResponse(req);
                }
                req.send(null);
        }
        else 
              {
              sum=0;
              for(var i=1;i<=tb.rows.length;i++)
                {
                  var r=document.getElementById(i);
                 var rcells=r.cells;
                var per = rcells.item(12).firstChild.nodeValue;
               // alert("Percentage"+per);
                sum=parseFloat(sum)+parseFloat(per);
                //alert("sum llllll"+sum);
                }
            
               var remining=100-parseFloat(sum);
               alert("Enter the Nomination Details for remaining  "+remining+"%");
           }       
    }
    else
    {
        alert("Enter Item Details");
    }
} 



