function checkDate()
{
    if(document.frmTransfer.txtPDat.value==""||document.frmTransfer.txtPDat.value.length==0)
    {
        alert("Enter Proceeding Date");
        return false;
    }
    else
        return true;
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
   document.frmTransfer.txtEmployeeid.value=emp;
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
document.frmTransfer.txtP_desigId.value=des;
document.frmTransfer.txtP_DesigName.value=desname;

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
    
        document.frmTransfer.txtT_OffId.value=jobid;
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
  var eid=document.frmTransfer.txtEmployeeid.value;
  var ofid=document.frmTransfer.txtOffId.value;
  var rep;
  var trof=document.frmTransfer.txtT_OffId.value;
  var ord=document.frmTransfer.txtPid.value;
  
  
  if(document.frmTransfer.radT_Repost[0].checked==true)
  {
    rep=document.frmTransfer.radT_Repost[0].value;
  }
  else
  {
    rep=document.frmTransfer.radT_Repost[1].value;
  }
  
  //alert(rep);
  if(Command=="loademp")
  {
    var url="../../../../../Validate_Transfer_OrderServ?Command=loademp&txtEmployeeid="+eid+"&offid="+ofid;
    
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
  
  else if(Command=="office")
  {
            var oid=param;
            
            var url="../../../../../Validate_Transfer_OrderServ?Command=office&oid="+oid;
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
      var ord_id=document.frmTransfer.txtPid.options[document.frmTransfer.txtPid.selectedIndex].value;
      var off_id=document.frmTransfer.txtOffId.value;
      
      var url="../../../../../Validate_Transfer_OrderServ?Command=getDet&order="+ord_id+"&offid="+off_id;
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
   
   else if(Command=="Update")
   {
      var desi=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      var url="../../../../../Validate_Transfer_OrderServ?Command=Update&txtEmployeeid="+eid+"&offid="+ofid+"&repost="+rep+"&trfoff="+trof+"&desig_id="+desi+"&order="+ord;
      
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
   
   else if(Command=="Delete")
   {
      var url="../../../../../Validate_Transfer_OrderServ?Command=Delete&txtEmployeeid="+eid+"&offid="+ofid+"&order="+ord;
      
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
     var desig=document.frmTransfer.txtP_desigId.value;
   
      var url="../../../../../Validate_Transfer_OrderServ?Command=Desig&txtDesigid="+desig;
      
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
         getDetails(req);
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
    }
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
        
        maxfromdate=baseResponse.getElementsByTagName("maxfromdate")[0].firstChild.nodeValue;
       
        maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
       

        //document.frmTransfer.birthDate.value=dob;
        document.frmTransfer.txtEmployeeid.value=eid;
        document.frmTransfer.txtEmpName.value=ename;
        document.frmTransfer.txtEmpDesig.value=desig;  
        document.frmTransfer.txtCurOff.value=posting;
    }
    
    
    else if(flag=="failure1")
    {
        var eid=document.frmTransfer.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' doesn't have a post.");
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
    }
    else if(flag=="failure2")
    {
        var eid=document.frmTransfer.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a unfrezeed relival record.");
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.frmTransfer.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in working status. So can not create relieval.");
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
    }
     else if(flag=="failurea")
    {
           var eid=document.frmTransfer.txtEmployeeid.value;
          // alert("Can not Create Relieval. Because Employee Id "+eid+" is not under your Office!");
          alert("SR controling office for this employee is different from your office!");
           document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
            
    }
     else if(flag=="failureb")
    {
           var eid=document.frmTransfer.txtEmployeeid.value;
            alert("You have no Current Posting. Can not Create Relieval for "+eid+"!");
            document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.frmTransfer.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Relieval!");
            document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.frmTransfer.txtEmployeeid.value;
            alert("Can not Create Relieval. Access Denined!");
            document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
           
    }
    else 
    {
    var eid=document.frmTransfer.txtEmployeeid.value;
    alert("Employee Id '"+eid+"' doesn't Exists");
    document.frmTransfer.txtEmployeeid.value="";
    document.frmTransfer.txtEmployeeid.focus();
    document.frmTransfer.txtEmpName.value="";
    document.frmTransfer.txtEmpDesig.value="";
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
              var tr_ofid=new Array();
              var repos=new Array();
              var des_id=new Array();
              var emp_name=new Array();
              var designat=new Array();
              var off_name=new Array();
              var reas=new Array();
              var reas_id=new Array();
              var ta_da=new Array();
              //var ind_addr=new Array();
               
              
               
              for(i=0;i<service.length;i++)
              {
                empid[i]=service[i].getElementsByTagName("employee_id")[0].firstChild.nodeValue;
                //alert(empid[i]);
                tr_ofid[i]=service[i].getElementsByTagName("trans_office_id")[0].firstChild.nodeValue;
                //alert(tr_ofid[i])
                repos[i]=service[i].getElementsByTagName("repost_req")[0].firstChild.nodeValue;
                //alert(repos[i])
                emp_name[i]=service[i].getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                //alert("name "+emp_name[i])
                des_id[i]=service[i].getElementsByTagName("desig_id")[0].firstChild.nodeValue;
                //alert("desigid"+des_id[i]);
               
                designat[i]=service[i].getElementsByTagName("desig")[0].firstChild.nodeValue;
                //alert(designat[i]);
                off_name[i]=service[i].getElementsByTagName("off_name")[0].firstChild.nodeValue;
                //alert(off_name[i])
                
                reas[i]=service[i].getElementsByTagName("reason")[0].firstChild.nodeValue;
                
                reas[i]=service[i].getElementsByTagName("reason")[0].firstChild.nodeValue;
                
                ta_da[i]=service[i].getElementsByTagName("ta_da")[0].firstChild.nodeValue;
                
                //ind_addr[i]=service[i].getElementsByTagName("ind_addr")[0].firstChild.nodeValue;
                
                          
                
              var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=empid[i];
           
           /*
           var cell=document.createElement("TD");
           var anc=document.createElement("A");
           var url="javascript:loadValuesFromTable('" + empid[i] + "')";
           anc.href=url;
           
           var txtedit=document.createTextNode("EDIT");
           anc.appendChild(txtedit);
           cell.appendChild(anc);
           mycurrent_row.appendChild(cell);*/
           
           var cell2=document.createElement("TD");
           var emp=document.createTextNode(empid[i]);
           cell2.appendChild(emp);
           mycurrent_row.appendChild(cell2);
           
          var cell3=document.createElement("TD");
           var emp_name1=document.createTextNode(emp_name[i]);
           cell3.appendChild(emp_name1);
           mycurrent_row.appendChild(cell3);
           
           var cell4=document.createElement("TD");
             
           var desid=document.createTextNode(designat[i]);
           cell4.appendChild(desid);
           var dees_id=document.createElement("input");
             dees_id.type="hidden";
             dees_id.name="desig_id";
             dees_id.id="desig_id";
             dees_id.value=des_id[i];
             //alert(des_id[i]);
             cell4.appendChild(dees_id);
           mycurrent_row.appendChild(cell4);
           
           var cell5=document.createElement("TD");
            
           var trf_off_name=document.createTextNode(off_name[i]);
           cell5.appendChild(trf_off_name);
            var trf_off_id=document.createElement("input");
             trf_off_id.type="hidden";
             trf_off_id.name="trf_offid";
             trf_off_id.id="trf_offid";
             trf_off_id.value=tr_ofid[i];
           cell5.appendChild(trf_off_id);  
           mycurrent_row.appendChild(cell5);
           
           var cell6=document.createElement("TD");
           var reasn=document.createTextNode(reas[i]);
           cell6.appendChild(reasn);
           mycurrent_row.appendChild(cell6);
           tbody.appendChild(mycurrent_row);
           
           var cell7=document.createElement("TD");
           var repost=document.createTextNode(repos[i]);
           cell7.appendChild(repost);
           mycurrent_row.appendChild(cell7);
           
           tbody.appendChild(mycurrent_row);
           
           var cell8=document.createElement("TD");
           var tda=document.createTextNode(ta_da[i]);
           cell8.appendChild(tda);
           mycurrent_row.appendChild(cell8);
           
           tbody.appendChild(mycurrent_row);
           
           /*
           var cell9=document.createElement("TD");
           var pia=document.createTextNode(ind_addr[i]);
           cell9.appendChild(pia);
           mycurrent_row.appendChild(cell9);
           */
           
          // tbody.appendChild(mycurrent_row);
           //alert("000");
          
              }
              
              
              
           var pro_no=baseres.getElementsByTagName("proceed_no")[0].firstChild.nodeValue;            
           var pro_dat1=baseres.getElementsByTagName("proceed_date")[0].firstChild.nodeValue;
                       
           if(pro_no=='null')
            document.frmTransfer.txtRno.value="";
           else 
            document.frmTransfer.txtRno.value=pro_no;
            
            var m=pro_dat1.split('-');
            pro_dat=m[2]+"/"+m[1]+"/"+m[0];
            
            if(pro_dat1=='null')
             document.frmTransfer.txtPDat.value="";
            else 
             document.frmTransfer.txtPDat.value=pro_dat;   
             
             var prefix=baseres.getElementsByTagName("prefix")[0].firstChild.nodeValue;
             var pres_officer=baseres.getElementsByTagName("pres_off")[0].firstChild.nodeValue;
             var officer_desig=baseres.getElementsByTagName("pres_off_des")[0].firstChild.nodeValue;
             var subj=baseres.getElementsByTagName("pro_subj")[0].firstChild.nodeValue;
             var ref=baseres.getElementsByTagName("pro_ref")[0].firstChild.nodeValue;
             var add_par_1=baseres.getElementsByTagName("add_par1")[0].firstChild.nodeValue;
             var add_par_2=baseres.getElementsByTagName("add_para2")[0].firstChild.nodeValue;
             var copy_to=baseres.getElementsByTagName("copy_to")[0].firstChild.nodeValue;
             var suffix=baseres.getElementsByTagName("suffix")[0].firstChild.nodeValue;
             //var sign=baseres.getElementsByTagName("sign_po")[0].firstChild.nodeValue;
             var indi_addr=baseres.getElementsByTagName("ind_addr")[0].firstChild.nodeValue;
             
             
             if(prefix=='null')
               document.frmTransfer.txtPref.value="";
              else
                document.frmTransfer.txtPref.value=prefix;
             
             if(pres_officer=='null')
                document.frmTransfer.txtPO.value="";
             else 
                document.frmTransfer.txtPO.value=pres_officer;
                
             if(officer_desig=='null')
                document.frmTransfer.txtPOD.value="";
             else 
                document.frmTransfer.txtPOD.value=officer_desig;
                
             if(subj=='null')
                document.frmTransfer.txtSub.value="";
             else 
                document.frmTransfer.txtSub.value=subj; 
                
             if(ref=='null')
                document.frmTransfer.txtRef.value="";
             else 
                document.frmTransfer.txtRef.value=ref;  
                
             if(add_par_1=='null')
                document.frmTransfer.txtAdd1.value="";
             else 
                document.frmTransfer.txtAdd1.value=add_par_1;  
                
              if(add_par_2=='null')
                document.frmTransfer.txtAdd2.value="";
             else 
                document.frmTransfer.txtAdd2.value=add_par_2;   
                
              if(copy_to=='null')
                document.frmTransfer.txtcopy.value="";
             else 
                document.frmTransfer.txtcopy.value=copy_to;   
                
             if(suffix=='null')
                document.frmTransfer.txtSuf.value="";
             else
                document.frmTransfer.txtSuf.value=suffix;
                
                /*
             if(sign=='Y')   
               document.frmTransfer.rad_sig[0].checked=true;
             else
               document.frmTransfer.rad_sig[1].checked=true;
               */
               
             if(indi_addr=='Y')  
                document.frmTransfer.rad_indi[0].checked=true;
              else
                document.frmTransfer.rad_indi[1].checked=true;    
                
                document.frmTransfer.btsub.disabled=false;
            }
            
         }
         else if(flag=="failurea")
         {
           alert('Sorry there is no data');
           document.frmTransfer.txtPid.value="";
           document.frmTransfer.txtRno.value="";
           document.frmTransfer.txtPDat.value=""; 
           document.frmTransfer.txtEmployeeid.value="";
           document.frmTransfer.txtEmpName.value="";
           document.frmTransfer.txtEmpDesig.value="";    
           document.frmTransfer.txtT_OffId.value="";
           document.frmTransfer.txtT_OffName.value=""; 
           //document.frmTransfer.txtP_desigId.value="";
           //document.frmTransfer.txtP_DesigName.value=""; 
           document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
           
           var tbody=document.getElementById("grid_body");
           try
            {tbody.innerHTML="";}
              catch(e) 
             {tbody.innerText="";}
         }
         
         else if(flag=="failure")
         {
           alert('Failure to retrieve');
           document.frmTransfer.txtPid.value="";
           document.frmTransfer.txtRno.value="";
           document.frmTransfer.txtPDat.value=""; 
           document.frmTransfer.txtEmployeeid.value="";
           document.frmTransfer.txtEmpName.value="";
           document.frmTransfer.txtEmpDesig.value="";    
           document.frmTransfer.txtT_OffId.value="";
           document.frmTransfer.txtT_OffName.value=""; 
           //document.frmTransfer.txtP_desigId.value="";
           //document.frmTransfer.txtP_DesigName.value=""; 
           document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
           
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

var comid="";

function loadValuesFromTable(rid)
{
   comid=rid;
   var r=document.getElementById(rid);
   var rcells=r.cells;
   
   var tbody=document.getElementById("grid_body");
   var table=document.getElementById("mytable");
   
   document.frmTransfer.txtEmployeeid.value=rcells.item(1).firstChild.nodeValue;
   document.frmTransfer.txtEmpName.value=rcells.item(2).firstChild.nodeValue;
   
   /*
   document.frmTransfer.txtP_desigId.value=document.frmTransfer.desig_id.value;  
   document.frmTransfer.txtP_DesigName.value=rcells.item(3).firstChild.nodeValue;
   */
   var did=(document.getElementById("desig_id").value);
   //alert(did)
   var did1=rcells.item(3).lastChild.value;
   //alert(did1);
   loadServiceGroup1(did1);
   
   var destid=document.getElementById("desig_id").value;
  // alert(document.frmTransfer.trf_offid.value);
  
   document.frmTransfer.txtT_OffId.value=rcells.item(4).lastChild.value;
   document.frmTransfer.txtT_OffName.value=rcells.item(4).firstChild.nodeValue;
   
   if(rcells.item(5).firstChild.nodeValue=="Y")
     document.frmTransfer.radT_Repost[0].checked=true;
   else    
     document.frmTransfer.radT_Repost[1].checked=true;
   
        //document.frmTransfer.btadd.disabled=true;
        document.frmTransfer.btupdate.disabled=false;
        document.frmTransfer.btdelete.disabled=false;
        document.frmTransfer.btdelete.focus();   
        
        var off=rcells.item(2).firstChild.nodeValue;
        
        doFunction('loademp','null');
        //doFunction('office',off); 
 
}

function clearDat()
{
    document.frmTransfer.txtRno.value="";
    document.frmTransfer.txtPDat.value="";

    document.frmTransfer.txtEmployeeid.value="";
    document.frmTransfer.txtEmpName.value="";
    document.frmTransfer.txtEmpDesig.value="";    
    document.frmTransfer.txtT_OffId.value="";
    document.frmTransfer.txtT_OffName.value="";  
    //document.frmTransfer.txtP_desigId.value="";
    //document.frmTransfer.txtP_DesigName.value=""; 
    document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
    
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
     
       items[1]=document.frmTransfer.txtT_OffName.value;
       
       if(document.frmTransfer.radT_Repost[0].checked==true)
       {
       items[2]=document.frmTransfer.radT_Repost[0].value;
       }
       else
       {
        items[2]=document.frmTransfer.radT_Repost[1].value;
       }
       
       items[3]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].text;
       
       
       
       
       var r=document.getElementById(comid);
       var rcells=r.cells;
       
       rcells.item(3).firstChild.nodeValue=items[3];
       rcells.item(4).firstChild.nodeValue=items[1];
       rcells.item(5).firstChild.nodeValue=items[2];
       
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value=""; 
        //document.frmTransfer.txtP_desigId.value="";
        //document.frmTransfer.txtP_DesigName.value="";
        document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
     
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
    
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value="";
        //document.frmTransfer.txtP_desigId.value="";
        //document.frmTransfer.txtP_DesigName.value="";
        document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
        
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
      document.frmTransfer.txtP_DesigName.value=des;
   }
   else
   {
     alert('Failure to load');
   }
   }
}

function clData()
{
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value="";
        document.frmTransfer.txtCurOff.value="";
        //document.frmTransfer.txtP_DesigName.value="";
        document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
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
    var type=document.frmTransfer.cmbsgroup.options[document.frmTransfer.cmbsgroup.selectedIndex].value;
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
      
       var type=document.frmTransfer.cmbsgroup.options[document.frmTransfer.cmbsgroup.selectedIndex].value;
       startwaiting(document.frmTransfer) ;
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
              
          var des=document.getElementById("cmbDesignation");
          var i=0;
          var response=req.responseXML.getElementsByTagName("response")[0];
          var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
          des.innerHTML="";
                
          stopwaiting(document.frmTransfer);
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
        
   var type=document.frmTransfer.cmbsgroup.options[document.frmTransfer.cmbsgroup.selectedIndex].value;
   if(type==0)
   {
     alert('Select Service Group');
     document.frmTransfer.cmbsgroup.focus();
     return false;
   }
}



function loadServiceGroup1(val)
{
   //alert(val); 
    var url="../../../../../Validate_Transfer_OrderServ?Command=SerGroup&cmbdes="+val;
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
                document.frmTransfer.cmbsgroup.value=gid;
                loadOfficesByType1(gid,val);
            }
        }
    }
}

function loadOfficesByType1(type,val)
{
       
    var type=document.frmTransfer.cmbsgroup.options[document.frmTransfer.cmbsgroup.selectedIndex].value;
    startwaiting(document.frmTransfer) ;
    var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
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
                
                stopwaiting(document.frmTransfer);
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
                    
                    //var destid=document.getElementById("desig_id").value;
                    //alert('destid...'+destid);
                    //alert(did1)
                   // var did1=rcells.item(3).lastChild.value;
                    //alert(val);
                    document.frmTransfer.cmbDesignation.value=val;
                
                }
        
        }
        
    }
    

}

function getreason()
{
  var reas=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
  
   var rs2=document.getElementById("cmbsgroup");
   var rs1=document.getElementById("cmbDesignation");
  
  if(reas==0)
  {
   alert('Please select the Reason');
  }
  else
  {
  var rs=document.getElementById("cmbdesig");
  
  if(reas=='Transfer')
  {    
    rs.style.display="none";
  }
  else
  {
    rs.style.display="block";
    rs2.disabled=false;
    rs1.disabled=false;
  }
  }
}

function checkNull()
{
 //alert("asdf");
    var year=document.frmTransfer.txtPDat.value;
    if(year=="0" || year==null || year=="")
    {
        alert("Select the Proceeding Date");
        document.frmTransfer.txtPDat.focus();
        return false;
    }
  
    return true;
}

////for updating in grid

/*
function updDat()
{
  if(document.frmTransfer.txtEmployeeid.value.length==0)
  {
     alert('Select the Employee Id');
     document.frmTransfer.txtEmployeeid.focus();
     return false;
  }
  
  else if(document.frmTransfer.txtEmpName.value=="")
  {
     alert('Select Proper Employee Id');
     document.frmTransfer.txtEmployeeid.focus();
     return false;
  }
  
  else if(document.frmTransfer.txtT_OffId.value.length==0)
  {
     alert('Select Office id'); 
     document.frmTransfer.txtT_OffId.focus();
     return false;
  }
 
  
    var items=new Array();
    
      //items[0]=document.getElementById("txtEmployeeid");
      items[1]=document.getElementById("txtT_OffId").value;
      
      if(document.frmTransfer.radT_Repost[0].checked==true)
         items[2]=document.frmTransfer.radT_Repost[0].value;
      else
         items[2]=document.frmTransfer.radT_Repost[1].value;
         
        var r=document.getElementById(comid);
        var rcells=r.cells;
        
        rcells.item(2).firstChild.nodeValue=items[1];
        rcells.item(3).firstChild.nodeValue=items[2];
        
        alert('Record Updated');
        
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value="";  
        
}*/
     