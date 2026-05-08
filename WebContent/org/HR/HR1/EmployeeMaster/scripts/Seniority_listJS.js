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

function numbersonly1(e,t)
{
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
           try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}

var winemp;
var windesig;

function doParentEmp(emp)
{
   document.frmSeniority.txtEmployeeid.value=emp;
   doFunction('loademp','null');
}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (windesig && windesig.open && !windesig.closed) windesig.close();
}

/*
function desigpopup()
{
    if (windesig && windesig.open && !windesig.closed) 
    {
       windesig.resizeTo(500,500);
       windesig.moveTo(250,250); 
       windesig.focus();
    }
    else
    {
        windesig=null
    }
        
    windesig= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/Reliv_find_Desig.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    windesig.moveTo(250,250);  
    windesig.focus();
    
}

function doParentDesig(des,desname)
{
document.frmSeniority.txtP_desigId.value=des;
document.frmSeniority.txtP_DesigName.value=desname;

}*/

function servicepopup()
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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpRelievalPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

var winjob;


function doParentJob(jobid,deptid)
{    
    
        document.frmSeniority.txtT_OffId.value=jobid;
        doFunction('office',jobid); 

}

function jobpopup_trn()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP_TRN.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

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

function doFunction(Command,param)
{
  var eid=document.frmSeniority.txtEmployeeid.value;
  
  if(Command=="loademp")
  {
  
   var sgroup=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
   var cadid=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].value;
    var url="../../../../../Seniority_listServ?Command=loademp&txtEmployeeid="+eid+"&cad="+cadid+"&sgroup="+sgroup;
    //alert('url...'+url);
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
  
  else if(Command=="Add")
  {
    var c=checkNull();
   if(c)
   {
    var ord_typ;
    var cad_id=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].value;
    var emp_id=document.frmSeniority.txtEmployeeid.value;
    var sen_no=document.frmSeniority.txtRno.value;
    if(document.frmSeniority.radOrd_Typ[0].checked==true)
    {
     ord_typ=document.frmSeniority.radOrd_Typ[0].value;
    }
    else
    {
      ord_typ=document.frmSeniority.radOrd_Typ[1].value; 
    }
    
    var ord_no=document.frmSeniority.txtSoN.value;
    var ord_dat=document.frmSeniority.txtSOD.value;
    var rem=document.frmSeniority.txtRem.value;
    
    
    var url="../../../../../Seniority_listServ?Command=Add&txtEmpid="+eid+"&txtcad_id="+cad_id+"&txtseni_no="+sen_no+"&txtSen_ord_typ="+ord_typ+"&txtSan_ordno="+ord_no+"&txtSan_orddat="+ord_dat+"&txtRem="+rem;
    //alert('url...'+url);
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
  
  else if(Command=="office")
  {
            var oid=param;
            
            var url="../../../../../Edit_Transfer_OrderServ?Command=office&oid="+oid;
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
   
   else if(Command=="getDet")
   {
      var cad_id=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].value;
      
      
      var url="../../../../../Seniority_listServ?Command=getDet&cad_id="+cad_id;
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
   
   else if(Command=="Update")
   {
      
   var c=checkNull();
   if(c)
   {
      var ord_typ;
    var cad_id=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].value;
    var emp_id=document.frmSeniority.txtEmployeeid.value;
    var sen_no=document.frmSeniority.txtRno.value;
    if(document.frmSeniority.radOrd_Typ[0].checked==true)
    {
     ord_typ=document.frmSeniority.radOrd_Typ[0].value;
    }
    else
    {
      ord_typ=document.frmSeniority.radOrd_Typ[1].value; 
    }
    
    var ord_no=document.frmSeniority.txtSoN.value;
    var ord_dat=document.frmSeniority.txtSOD.value;
    var rem=document.frmSeniority.txtRem.value;
    
     
      
      var url="../../../../../Seniority_listServ?Command=Update&txtEmpid="+eid+"&txtcad_id="+cad_id+"&txtseni_no="+sen_no+"&txtSen_ord_typ="+ord_typ+"&txtSan_ordno="+ord_no+"&txtSan_orddat="+ord_dat+"&txtRem="+rem;
      
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
   
   else if(Command=="Delete")
   {
      var cad_id=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].value;
      var emp_id=document.frmSeniority.txtEmployeeid.value;
      
      var url="../../../../../Seniority_listServ?Command=Delete&txtEmployeeid="+emp_id+"&Cadreid="+cad_id;
      
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
   
   else if(Command=="desig")
   {
     var desig=document.frmSeniority.txtP_desigId.value;
   
      var url="../../../../../Edit_Transfer_OrderServ?Command=Desig&txtDesigid="+desig;
      
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

function handleResponse(req)
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
         loadEmployee(baseResponse);
      }
      else if(Command=="office")
      {
         loadOffice(baseResponse);
      }
      else if(Command=="getDet")
      {
         getDetails(baseResponse);
      }
      else if(Command=="update")
      {
         upDat(baseResponse);
      }
      else if(Command=="delete")
      {
         delrow(baseResponse);
      }
      else if(Command=="Desig")
      {
        desigLoad(req);
      }
      else if(Command=="Add")
      {
        addRow(baseResponse);
      }
    }
  }

}

function addRow(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
     //alert(flag);
     if(flag=="success")
     {                        
        alert("Record Inserted Into Database successfully.");
        
         var items=new Array();                   
         
         var cadname=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].text;
         
           var emp_id=document.frmSeniority.txtEmployeeid.value;
           var emp_name=document.frmSeniority.txtEmpName.value;
           var senior_no=document.frmSeniority.txtRno.value;
           
           var san_ord_type;
           if(document.frmSeniority.radOrd_Typ[0].checked==true)
           {
            san_ord_type=document.frmSeniority.radOrd_Typ[0].value;
           }
           else
           {
            san_ord_type=document.frmSeniority.radOrd_Typ[1].value;
           }
           
           
           var san_ord_no=document.frmSeniority.txtSoN.value;
           var san_ord_date=document.frmSeniority.txtSOD.value;
           var rem=document.frmSeniority.txtRem.value;
           
                   /*
                    var payid=document.DesigForm.cmbPayId.value;
                    var cadid=document.DesigForm.cmbCadreId.value;
                    var posid=document.DesigForm.cmbPostId.value;
                    var servid=document.DesigForm.cmbSerId.value;
                    */
                    
                     var tbody=document.getElementById("grid_body");
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=emp_id;
                     var cell=document.createElement("TD");
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + emp_id + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("EDIT");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     
                     
                      var cell4=document.createElement("TD");  
                     var ord_type=document.createTextNode(san_ord_type);
                     cell4.appendChild(ord_type);
                     mycurrent_row.appendChild(cell4);
                     
                     var cell5=document.createElement("TD");  
                     var san_ord_no=document.createTextNode(san_ord_no);
                     cell5.appendChild(san_ord_no);
                     mycurrent_row.appendChild(cell5);
                     
                     var cell6=document.createElement("TD");  
                     var san_ord_dat=document.createTextNode(san_ord_date);
                     cell6.appendChild(san_ord_dat);
                     mycurrent_row.appendChild(cell6);
                     
                     var cell1=document.createElement("TD");  
                     var em_id=document.createTextNode(emp_id);
                     cell1.appendChild(em_id);
                     mycurrent_row.appendChild(cell1);
                     
                     var cell2=document.createElement("TD");  
                     var em_name=document.createTextNode(emp_name);
                     cell2.appendChild(em_name);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell3=document.createElement("TD");  
                     var sn_no=document.createTextNode(senior_no);
                     cell3.appendChild(sn_no);
                     mycurrent_row.appendChild(cell3);
                     
                    
                     
                     var cell7=document.createElement("TD");  
                     var remarks=document.createTextNode(rem);
                     cell7.appendChild(remarks);
                     mycurrent_row.appendChild(cell7);
                     
                     
                    
                    /*
                     var cell4=document.createElement("TD");
                     var cid=document.createElement("INPUT");
                     var cname=document.createTextNode(cadname);
                     cid.type="HIDDEN";
                     cid.name="cadreid";
                     cid.text=cadid;
                     cid.value=cadid;
                     cell4.appendChild(cid);
                     cell4.appendChild(cname);
                     mycurrent_row.appendChild(cell4);
                     */
                       
                    
                     
                                tbody.appendChild(mycurrent_row); 
                                
                               document.frmSeniority.txtEmployeeid.value="";
                               document.frmSeniority.txtEmpName.value="";
                               document.frmSeniority.txtEmpDesig.value="";
                               document.frmSeniority.txtCurOff.value="";
                               document.frmSeniority.txtStat.value="";
                               
                               /*document.frmSeniority.txtRno.value="";
                               document.frmSeniority.radOrd_Typ.value="";
                               document.frmSeniority.txtSoN.value="";
                               document.frmSeniority.txtSOD.value="";*/
                               document.frmSeniority.txtRem.value="";
                               
                               
                               
                               
                               /*
                               document.DesigForm.htxtDesigId.value="";
                               document.DesigForm.txtDesigDesc.value="";
                               document.DesigForm.txtDesigsDesc.value="";
                               document.DesigForm.cmbPayId.value="";
                               document.DesigForm.cmbCadreId.selectedIndex=0;
                               document.DesigForm.cmbPostId.selectedIndex=0;
                               document.DesigForm.cmbSerId.selectedIndex=0;
                               document.DesigForm.txtRemarks.value="";
                               */
                }
             else
             {
                     alert("Failed to Insert Values");
             }
}
 

function loadEmployee(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
    if(flag=="success")
    {  
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
        var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
        var dob=baseResponse.getElementsByTagName("dob")[0].firstChild.nodeValue;
        var posting=baseResponse.getElementsByTagName("curr_post")[0].firstChild.nodeValue;
        var desig_id=baseResponse.getElementsByTagName("desig_id")[0].firstChild.nodeValue;
        var stat=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
        
        maxfromdate=baseResponse.getElementsByTagName("maxfromdate")[0].firstChild.nodeValue;
       
        maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
       

        //document.frmSeniority.birthDate.value=dob;
        document.frmSeniority.txtEmployeeid.value=eid;
        document.frmSeniority.txtEmpName.value=ename;
        document.frmSeniority.txtEmpDesig.value=desig;  
        document.frmSeniority.txtCurOff.value=posting;
        document.frmSeniority.txt_desid.value=desig_id;
        document.frmSeniority.txtStat.value=stat;
        
        
    }
    else if(flag=="available")
    {
        alert("Given Employee id is already exist");
    }
    else if(flag=="failure0")
    {
      alert('Given Employee Cadre is differing from selected Cadre');
      document.frmSeniority.txtEmployeeid.value="";
      document.frmSeniority.txtEmployeeid.focus();
    }
    
    
    else if(flag=="failure1")
    {
        var eid=document.frmSeniority.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' doesn't have a post.");
        document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmployeeid.focus();
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
    }
    else if(flag=="failure2")
    {
        var eid=document.frmSeniority.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a unfrezeed relival record.");
        document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmployeeid.focus();
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.frmSeniority.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in working status. So can not create relieval.");
        document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmployeeid.focus();
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
    }
     else if(flag=="failurea")
    {
           var eid=document.frmSeniority.txtEmployeeid.value;
          // alert("Can not Create Relieval. Because Employee Id "+eid+" is not under your Office!");
          alert("SR controling office for this employee is different from your office!");
           document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmployeeid.focus();
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
            
    }
     else if(flag=="failureb")
    {
           var eid=document.frmSeniority.txtEmployeeid.value;
            alert("You have no Current Posting. Can not Create Relieval for "+eid+"!");
            document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmployeeid.focus();
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.frmSeniority.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Relieval!");
            document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmployeeid.focus();
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.frmSeniority.txtEmployeeid.value;
            alert("Can not Create Relieval. Access Denined!");
            document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmployeeid.focus();
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
           
    }
    else 
    {
    var eid=document.frmSeniority.txtEmployeeid.value;
    alert("Employee Id '"+eid+"' doesn't Exists");
    document.frmSeniority.txtEmployeeid.value="";
    document.frmSeniority.txtEmployeeid.focus();
    document.frmSeniority.txtEmpName.value="";
    document.frmSeniority.txtEmpDesig.value="";
    document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
    }
}

function loadOffice(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
  if(flag=="success")
  {
    
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        
        document.getElementById("txtT_OffId").value=oid;
        document.getElementById("txtT_OffName").value=oname;
  
  }
  else
  {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     
     alert("Office Id '"+oid+"' doesn't Exists");
     
                document.getElementById("txtT_OffId").focus();
                document.getElementById("txtT_OffId").value="";
                document.getElementById("txtT_OffName").value="";
  }
}
/*
function getDetails(req)
{
  
   if(req.readyState==4)
   {
    if(req.status==200)
    {
      
      var baseres=req.responseXML.getElementsByTagName("response")[0];
      
      var cmd=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
      
      
      if(cmd=='getDet')
      {
         var i=0;         
         var flag=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
         
         if(flag=='success')
         {     
             var tbody=document.getElementById("grid_body");
            
            var service=baseres.getElementsByTagName("details");           
            //alert(service.length);
            if(service)
            {
             
              var empid=new Array();
              var empnme=new Array();
              var sen_no=new Array();
              var ord_type=new Array();
              var ord_no=new Array();
              var ord_date=new Array();
              var rem=new Array();
              var cad_id=new Array();
               
              
               
              for(i=0;i<service.length;i++)
              {
                empid[i]=service[i].getElementsByTagName("employee_id")[0].firstChild.nodeValue;
                
                empnme[i]=service[i].getElementsByTagName("employee_name")[0].firstChild.nodeValue;
                
                sen_no[i]=service[i].getElementsByTagName("sen_no")[0].firstChild.nodeValue;
                
                ord_type[i]=service[i].getElementsByTagName("amend")[0].firstChild.nodeValue;
                
                ord_no[i]=service[i].getElementsByTagName("san_ord_no")[0].firstChild.nodeValue;
                
                ord_date[i]=service[i].getElementsByTagName("san_ord_date")[0].firstChild.nodeValue;
                
                rem[i]=service[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
                
                cad_id[i]=service[i].getElementsByTagName("cad_id")[0].firstChild.nodeValue;
                
                
                        
              var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=empid[i];
           
           var cell=document.createElement("TD");
           var anc=document.createElement("A");
           var url="javascript:loadValuesFromTable('" + empid[i] + "')";
           anc.href=url;
           
           var txtedit=document.createTextNode("EDIT");
           anc.appendChild(txtedit);
           cell.appendChild(anc);
           mycurrent_row.appendChild(cell);
           
           var cell2=document.createElement("TD");
           var emp=document.createTextNode(empid[i]);
           cell2.appendChild(emp);
           mycurrent_row.appendChild(cell2);
           
          var cell3=document.createElement("TD");
           var emp_name1=document.createTextNode(empnme[i]);
           cell3.appendChild(emp_name1);
           mycurrent_row.appendChild(cell3);
           
           var cell4=document.createElement("TD");
           var sen_no=document.createTextNode(sen_no[i]);
           cell4.appendChild(sen_no);
           mycurrent_row.appendChild(cell4);
           
           var cell5=document.createElement("TD");
           var ord_typ=document.createTextNode(ord_type[i]);
           cell5.appendChild(ord_typ);
           mycurrent_row.appendChild(cell5);
           
           var cell6=document.createElement("TD");
           var ord_no=document.createTextNode(ord_no[i]);
           cell6.appendChild(ord_no);
           mycurrent_row.appendChild(cell6);
           
           var cell7=document.createElement("TD");
           var ord_dat=document.createTextNode(ord_date[i]);
           cell7.appendChild(ord_dat);
           mycurrent_row.appendChild(cell7);
           
           var cell8=document.createElement("TD");
           var remarks=document.createTextNode(rem[i]);
           cell8.appendChild(remarks);
           mycurrent_row.appendChild(cell8);
           
           tbody.appendChild(mycurrent_row);
          
              }
              
              
            }
            
         }
         else if(flag=="failurea")
         {
           alert('Sorry there is no data');
           document.frmSeniority.txtPid.value="";
           document.frmSeniority.txtRno.value="";
           document.frmSeniority.txtPDat.value=""; 
           document.frmSeniority.txtEmployeeid.value="";
           document.frmSeniority.txtEmpName.value="";
           document.frmSeniority.txtEmpDesig.value="";    
           document.frmSeniority.txtT_OffId.value="";
           document.frmSeniority.txtT_OffName.value=""; 
           //document.frmSeniority.txtP_desigId.value="";
           //document.frmSeniority.txtP_DesigName.value=""; 
           document.frmSeniority.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmSeniority.cmbDesignation.value='0';
           
           var tbody=document.getElementById("grid_body");
           try
            {tbody.innerHTML="";}
              catch(e) 
             {tbody.innerText="";}
         }
         
         else if(flag=="failure")
         {
           alert('Failure to retrieve');
           document.frmSeniority.txtPid.value="";
           document.frmSeniority.txtRno.value="";
           document.frmSeniority.txtPDat.value=""; 
           document.frmSeniority.txtEmployeeid.value="";
           document.frmSeniority.txtEmpName.value="";
           document.frmSeniority.txtEmpDesig.value="";    
           document.frmSeniority.txtT_OffId.value="";
           document.frmSeniority.txtT_OffName.value=""; 
           //document.frmSeniority.txtP_desigId.value="";
           //document.frmSeniority.txtP_DesigName.value=""; 
           document.frmSeniority.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmSeniority.cmbDesignation.value='0';
           
           var tbody=document.getElementById("grid_body");
          try
           {tbody.innerHTML="";}
          catch(e) 
          {tbody.innerText="";}
         }
         
      }
    }
   }
}
*/

function getDetails(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
  if(flag=="success")
              {          
                       var tbody=document.getElementById("grid_body");
                       var table=document.getElementById("mytable");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   
                           
                   var eid=baseResponse.getElementsByTagName("employee_id");
                            
                     for(var k=0;k<eid.length;k++)
                        {
                        
                var empid=baseResponse.getElementsByTagName("employee_id");
                
                var empnme=baseResponse.getElementsByTagName("employee_name");
                
                var sen_no=baseResponse.getElementsByTagName("sen_no");
                
                var ord_type=baseResponse.getElementsByTagName("amend");
                
                var ord_no=baseResponse.getElementsByTagName("san_ord_no");
                
                var ord_date=baseResponse.getElementsByTagName("san_ord_date");
                
                var rem=baseResponse.getElementsByTagName("remarks");
                
                var cad_id=baseResponse.getElementsByTagName("cad_id");
                        
                       var gOtyp=ord_type.item(k).firstChild.nodeValue;
                      
                      var gONo=ord_no.item(k).firstChild.nodeValue;
                      
                      var gODat=ord_date.item(k).firstChild.nodeValue;
                        
                      var gEid=empid.item(k).firstChild.nodeValue;
                      
                      var gEnme=empnme.item(k).firstChild.nodeValue;
                      
                      var gSNo=sen_no.item(k).firstChild.nodeValue;
                    
                      var gRem=rem.item(k).firstChild.nodeValue;
                      
           var mycurrent_row=document.createElement("TR");
           mycurrent_row.id=gEid;                                
                      
           var cell=document.createElement("TD");
           var anc=document.createElement("A");
           var url="javascript:loadValuesFromTable('" + gEid + "')";
           anc.href=url;
           
           var txtedit=document.createTextNode("EDIT");
           anc.appendChild(txtedit);
           cell.appendChild(anc);
           mycurrent_row.appendChild(cell);
           
           var cell5=document.createElement("TD");
           var ord_typ=document.createTextNode(gOtyp);
           cell5.appendChild(ord_typ);
           mycurrent_row.appendChild(cell5);
           
           var cell6=document.createElement("TD");
           var ord_no=document.createTextNode(gONo);
           cell6.appendChild(ord_no);
           mycurrent_row.appendChild(cell6);
           
           var cell7=document.createElement("TD");
           var ord_dat=document.createTextNode(gODat);
           cell7.appendChild(ord_dat);
           mycurrent_row.appendChild(cell7);
           
           var cell2=document.createElement("TD");
           var emp=document.createTextNode(gEid);
           cell2.appendChild(emp);
           mycurrent_row.appendChild(cell2);
           
          var cell3=document.createElement("TD");
           var emp_name1=document.createTextNode(gEnme);
           cell3.appendChild(emp_name1);
           mycurrent_row.appendChild(cell3);
           
           var cell4=document.createElement("TD");
           var sen_no=document.createTextNode(gSNo);
           cell4.appendChild(sen_no);
           mycurrent_row.appendChild(cell4);
           
           
           
           var cell8=document.createElement("TD");
           var remarks=document.createTextNode(gRem);
           cell8.appendChild(remarks);
           mycurrent_row.appendChild(cell8);
           
           tbody.appendChild(mycurrent_row);
                      
                      
                            
                            /*
                        var cDId=DId.item(k).firstChild.nodeValue;
                        var cDDesc=DDesc.item(k).firstChild.nodeValue;
                        var cDsDesc=DsDesc.item(k).firstChild.nodeValue;
                        var cCId=CId.item(k).firstChild.nodeValue;
                       // var cPId=PId.item(k).firstChild.nodeValue;
                       // var cPName=PName.item(k).firstChild.nodeValue;
                        var cCName=CName.item(k).firstChild.nodeValue;
                        var cPId=PId.item(k).firstChild.nodeValue;
                        var cPName=PName.item(k).firstChild.nodeValue;
                        var cSId=SId.item(k).firstChild.nodeValue;
                        var cSName=SName.item(k).firstChild.nodeValue;
                        var cRem=Rem.item(k).firstChild.nodeValue;
                    
                         if(cRem=="UnDefined Record Found")
                              cRem=" ";
                       else
                            cRem=cRem;                    
                   
                              */ 
                       
                        /*
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=cDId;
                     var cell=document.createElement("TD");
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + cDId + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     
                         var cell2 =document.createElement("TD");    
                         var desid=document.createTextNode(cDId);                         
                         cell2.appendChild(desid);       
                         mycurrent_row.appendChild(cell2);       

                         var cell3 =document.createElement("TD");    
                         var desdesc=document.createTextNode(cDDesc);                         
                         cell3.appendChild(desdesc);       
                         mycurrent_row.appendChild(cell3);       
                         
                        
                     
                     
                     var cell5=document.createElement("TD");
                     var cid=document.createElement("INPUT");
                     var cname=document.createTextNode(cCName);
                     cid.type="HIDDEN";
                     cid.name="cadreid";
                     cid.text=cCId;
                     cid.value=cCId;
                     cell5.appendChild(cid);
                     cell5.appendChild(cname);
                     mycurrent_row.appendChild(cell5);
                     
                     var cell6=document.createElement("TD");
                     var pid=document.createElement("INPUT");
                     var pname=document.createTextNode(cPName);
                     pid.type="HIDDEN";
                     pid.name="Rank";
                     pid.text=cPId;
                     pid.value=cPId;
                     cell6.appendChild(pid);
                     cell6.appendChild(pname);
                     mycurrent_row.appendChild(cell6);
                     
                  
                       
                     
                     var cell7=document.createElement("TD");
                     var sid=document.createElement("INPUT");
                     var sname=document.createTextNode(cSName);
                     sid.type="HIDDEN";
                     sid.name="servid";
                     sid.text=cSId;
                     sid.value=cSId;
                     //alert(sid.value);
                     cell7.appendChild(sid);
                     cell7.appendChild(sname);
                     mycurrent_row.appendChild(cell7);
                     
                      var cell4 =document.createElement("TD");    
                         var dessdesc=document.createTextNode(cDsDesc);                         
                         cell4.appendChild(dessdesc);       
                         mycurrent_row.appendChild(cell4);    
                     
                        var cell8 =document.createElement("TD");    
                         var rema=document.createTextNode(cRem);                         
                         cell8.appendChild(rema);       
                         mycurrent_row.appendChild(cell8); 
                         tbody.appendChild(mycurrent_row); 
                         */
                         }
                     
             }
             else
             {
                     alert("There is no data for corresponding Cadre");
                     
                     var tbody=document.getElementById("grid_body");
                     try
                      {tbody.innerHTML="";}
                       catch(e) 
                      {tbody.innerText="";}
             }
}


var comid="";

function loadValuesFromTable(rid)
{
   comid=rid;
   //alert('rid...'+rid)
   var r=document.getElementById(rid);
   var rcells=r.cells;
   
   var tbody=document.getElementById("grid_body");
   var table=document.getElementById("mytable");
    if(rcells.item(1).firstChild.nodeValue=="N")
     document.frmSeniority.radOrd_Typ[0].checked=true;
   else    
     document.frmSeniority.radOrd_Typ[1].checked=true;
     
    document.frmSeniority.txtSoN.value=rcells.item(2).firstChild.nodeValue;
    
    document.frmSeniority.txtSOD.value=rcells.item(3).firstChild.nodeValue;
   document.frmSeniority.txtEmployeeid.value=rcells.item(4).firstChild.nodeValue;
   document.frmSeniority.txtEmpName.value=rcells.item(5).firstChild.nodeValue;
   document.frmSeniority.txtRno.value=rcells.item(6).firstChild.nodeValue;
   
  
    
    document.frmSeniority.txtRem.value=rcells.item(7).firstChild.nodeValue;
   
   //loadServiceGroup1(did1);
    /*       
     if(rcells.item(8).firstChild.nodeValue=="Y")
       document.frmSeniority.rad_indi[0].checked=true;
     else
       document.frmSeniority.rad_indi[1].checked=true;
   */
       /*
        document.frmSeniority.btadd.disabled=false;
        document.frmSeniority.btdelete.disabled=false;
        document.frmSeniority.btdelete.focus();   
        */
        document.frmSeniority.btadd.disabled=true;
        doFunction('loademp','null');
        
 
}

function clearDat()
{

    document.frmSeniority.txtEmployeeid.value="";
    document.frmSeniority.txtEmpName.value="";
    document.frmSeniority.txtEmpDesig.value="";
    document.frmSeniority.txtCurOff.value="";
    document.frmSeniority.txtStat.value="";
    document.frmSeniority.txtRno.value="";
    document.frmSeniority.txtSoN.value="";
    document.frmSeniority.txtSOD.value="";    
    
    document.frmSeniority.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmSeniority.cmbDesignation.value='0';
    
    ///for grid values clear
    
    var tbody=document.getElementById("grid_body");
     try
     {tbody.innerHTML="";}
     catch(e) 
     {tbody.innerText="";}
}


function upDat(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
   if(flag=="success")
   {
     alert('Records Updated Successfully');
     
     var items=new Array();
     
     /*
     var senior_no=document.frmSeniority.txtRno.value;
           
           var san_ord_type;
           if(document.frmSeniority.radOrd_Typ[0].checked==true)
           {
            san_ord_type=document.frmSeniority.radOrd_Typ[0].value;
           }
           else
           {
            san_ord_type=document.frmSeniority.radOrd_Typ[1].value;
           }
           
           
           var san_ord_no=document.frmSeniority.txtSoN.value;
           var san_ord_date=document.frmSeniority.txtSOD.value;
           var rem=document.frmSeniority.txtRem.value;
           */
     
       items[1]=document.frmSeniority.txtEmployeeid.value;
       
       items[2]=document.frmSeniority.txtEmpName.value;
       
       items[3]=document.frmSeniority.txtRno.value;
       
       if(document.frmSeniority.radOrd_Typ[0].checked==true)
       {
         items[4]=document.frmSeniority.radOrd_Typ[0].value;
       }
        else
        {
          items[4]=document.frmSeniority.radOrd_Typ[1].value;
        }
        
        items[5]=document.frmSeniority.txtSoN.value;
        
        items[6]=document.frmSeniority.txtSOD.value;
        
        items[7]=document.frmSeniority.txtRem.value;      
       
       
       var r=document.getElementById(comid);
       var rcells=r.cells;
       
       rcells.item(1).firstChild.nodeValue=items[4];
       rcells.item(2).firstChild.nodeValue=items[5];
       rcells.item(3).firstChild.nodeValue=items[6];
       rcells.item(4).firstChild.nodeValue=items[1];
       rcells.item(5).firstChild.nodeValue=items[2];
       rcells.item(6).firstChild.nodeValue=items[3];
     
       rcells.item(7).firstChild.nodeValue=items[7];
       
       document.frmSeniority.txtEmployeeid.value="";
       document.frmSeniority.txtEmpName.value="";
       document.frmSeniority.txtEmpDesig.value=""; 
       document.frmSeniority.txtCurOff.value="";
       document.frmSeniority.txtStat.value="";
       document.frmSeniority.txtRno.value="";
       document.frmSeniority.txtSoN.value="";
       document.frmSeniority.txtSOD.value=""; 
       document.frmSeniority.txtRem.value="";
       
       document.frmSeniority.radOrd_Typ[0].checked=true;
       
       document.frmSeniority.btadd.disabled=false;
        
       
       
       /*
        document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";    
        document.frmSeniority.txtT_OffId.value="";
        document.frmSeniority.txtT_OffName.value=""; 
        document.frmSeniority.txtCurOff.value="";
        //document.frmSeniority.txtP_DesigName.value="";
        document.frmSeniority.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmSeniority.cmbDesignation.value='0';
        
        document.frmSeniority.cmbreasid.value='0';
      */
   }
}

function delrow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
  if(flag=="success")
  {
    var tbody=document.getElementById("mytable");
    var r=document.getElementById(comid);
    var ri=r.rowIndex;
    tbody.deleteRow(ri);
    
        document.frmSeniority.txtEmployeeid.value="";
       document.frmSeniority.txtEmpName.value="";
       document.frmSeniority.txtEmpDesig.value=""; 
       document.frmSeniority.txtCurOff.value="";
       document.frmSeniority.txtStat.value="";
       document.frmSeniority.txtRno.value="";
       document.frmSeniority.txtSoN.value="";
       document.frmSeniority.txtSOD.value=""; 
       document.frmSeniority.txtRem.value="";
       
       document.frmSeniority.radOrd_Typ[0].checked=true;
       /*
        document.frmSeniority.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmSeniority.cmbDesignation.value='0';
        */
        alert('Selected Rows are Deleted');
  }
  else
  {
      alert('Unable to Delete');
  }

}

function desigLoad(req)
{
   var basRes=req.responseXML.getElementsByTagName("response")[0];
   var tagcom=basRes.getElementsByTagName("command")[0].firstChild.nodeValue;
   if(tagcom=="Desig")
   {
   var flag=basRes.getElementsByTagName("flag")[0].firstChild.nodeValue;
   //alert(flag);
   if(flag=="success")
   {
      var des=basRes.getElementsByTagName("designame")[0].firstChild.nodeValue;
      //alert(des);
      document.frmSeniority.txtP_DesigName.value=des;
   }
   else
   {
     alert('Failure to load');
   }
   }
}

function clData()
{
        document.frmSeniority.txtEmployeeid.value="";
        document.frmSeniority.txtEmpName.value="";
        document.frmSeniority.txtEmpDesig.value="";    
        document.frmSeniority.txtCurOff.value="";
        document.frmSeniority.txtStat.value="";
        document.frmSeniority.txtRno.value="";
        document.frmSeniority.txtSoN.value="";
        document.frmSeniority.txtSOD.value="";
        document.frmSeniority.txtRem.value="";
        
        document.frmSeniority.radOrd_Typ[0].checked=true;
        
        document.frmSeniority.btadd.disabled=false;
        
        /*
        document.frmSeniority.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmSeniority.cmbDesignation.value='0';
        */
        
        
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

function getDesignation()
{
    var type=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
    //alert('type...'+type);
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
      
       var type=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
       startwaiting(document.frmSeniority) ;
       var url="../../../../../Seniority_listServ?Command=SGroup&cmbsgroup=" + type ;
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
              
          var des=document.getElementById("cmbDesignation");
          var i=0;
          var response=req.responseXML.getElementsByTagName("response")[0];
          var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
          des.innerHTML="";
                
          stopwaiting(document.frmSeniority);
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
             option.text="--Select Cadre--";
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
        
   var type=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
   if(type==0)
   {
     alert('Select Service Group');
     document.frmSeniority.cmbsgroup.focus();
     return false;
   }
}



function loadServiceGroup1(val)
{
   //alert(val); 
    var url="../../../../../Edit_Transfer_OrderServ?Command=SerGroup&cmbdes="+val;
    //alert(url);
    var req= getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
       handleResponse1(req,val);
    }
   if(window.XMLHttpRequest)
      req.send(null);
   else req.send();  
}

function handleResponse1(req,val)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert(flag);
            if(flag=="success")
            {
                var gid=baseResponse.getElementsByTagName("sid")[0].firstChild.nodeValue;
                document.frmSeniority.cmbsgroup.value=gid;
                loadOfficesByType1(gid,val);
            }
        }
    }
}

function loadOfficesByType1(type,val)
{
       
    var type=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
    startwaiting(document.frmSeniority) ;
    var url="../../../../../Seniority_listServ?Command=SGroup&cmbsgroup=" + type ;
    var req=getTransport();
    req.open("GET",url,true);        
    req.onreadystatechange=function()
    {
            
      loadDesignation1(req,val);
    }
    if(window.XMLHttpRequest)
        req.send(null);
    else req.send();
}

function  loadDesignation1(req,val)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
              
                var des=document.getElementById("cmbDesignation");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                stopwaiting(document.frmSeniority);
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
                    option.text="--Select Cadre--";
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
                    
                    //var destid=document.getElementById("desig_id").value;
                    //alert('destid...'+destid);
                    //alert(did1)
                   // var did1=rcells.item(3).lastChild.value;
                    //alert(val);
                    document.frmSeniority.cmbDesignation.value=val;
                
                }
        
        }
        
    }
    

}



/*
function getPost_reas()
{
  var reas=document.frmSeniority.cmbreasid.options[document.frmSeniority.cmbreasid.selectedIndex].value;
  //alert(reas);
  var rs1=document.getElementById("cmbDesignation");
  var sg=document.getElementById("cmbsgroup");
  //alert('reas...'+reas);
  
  var tdl=document.getElementById("td_label");
  var tdc=document.getElementById("td_cmb");
  
  //var over=document.getElementById("oall");
  
  if(reas==0)
  {
    alert('Please Select Reason');
  }
  else
  {
   
    if((reas==2) || (reas==6))
    {
    
      document.frmSeniority.cmbDesignation.disabled = false;
      document.frmSeniority.cmbsgroup.disabled = false;     
      
    }
    else
    {
    
      document.frmSeniority.cmbDesignation.disabled = true;
      document.frmSeniority.cmbsgroup.disabled = true;
     
      
      
    }
    
  }
}
*/

function checkNull()
{
  var cadr=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].value;
  //alert('cadr...'+cadr);
  
  if(cadr==0)
  {
    alert('Please Select Cadre');
    return false;
  }
  else if((document.frmSeniority.txtEmployeeid.value=="") || (document.frmSeniority.txtEmployeeid.value.length==0))
  {
    alert('Please Enter Employee Id');
    return false;
  }
  else if((document.frmSeniority.txtRno.value=="") || (document.frmSeniority.txtRno.value.length==0))
  {
    alert('Please Enter Seniority No.');
    return false;
  }
  else if((document.frmSeniority.txtSoN.value=="") || (document.frmSeniority.txtSoN.value.length==0))
  {
    alert('Please Enter Sanction Order No.');
    return false;
  }
  else if((document.frmSeniority.txtSOD.value=="") || (document.frmSeniority.txtSOD.value.length==0))
  {
    alert('Please Enter Sanction Order Date');
    return false;
  }  
  
  return true;
}


function chkRem()
{
  var rem=document.frmSeniority.txtRem.value.length;
  
  if(rem>400)
  {
   alert('Character length is exceeding 400');
   document.frmSeniority.txtRem.value="";
   document.frmSeniority.txtRem.focus();
   return false;
  }
  return true;
}









     