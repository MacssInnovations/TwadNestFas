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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpRelievalPopup_TRN_Order.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
//alert(Command);
  var eid=document.frmTransfer.txtEmployeeid.value;
  var ofid=document.frmTransfer.txtOffId.value;
  var rep;
  var trof=document.frmTransfer.txtT_OffId.value;
  var ord=document.frmTransfer.txtPid.value;
  var new_ord=document.frmTransfer.txthPid.value;
  //alert(new_ord);
  
 
  
  var req_transf="";
  var indi_addr="";
 
  
  
  if(document.frmTransfer.radT_Repost[0].checked==true)
  {
    rep=document.frmTransfer.radT_Repost[0].value;
  }
  else
  {
    rep=document.frmTransfer.radT_Repost[1].value;
  }
  
  if(document.frmTransfer.radTransfer[0].checked==true)
  {
    req_transf=document.frmTransfer.radTransfer[0].value;
  }
  else
  {
    req_transf=document.frmTransfer.radTransfer[1].value;
  }
  
  
  /*
  if(document.frmTransfer.rad_indi[0].checked==true)
  {
     indi_addr=document.frmTransfer.rad_indi[0].value;
  }
  else
  {
    indi_addr=document.frmTransfer.rad_indi[1].value;
  }*/
  
  //alert(rep);
  if(Command=="loademp")
  {
   var ord_cat="";
  
  if(document.frmTransfer.rad_ROC[0].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[0].value;
  }
  else if(document.frmTransfer.rad_ROC[1].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[1].value;
  }
  else if(document.frmTransfer.rad_ROC[2].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[2].value;
  } 
  
    var url="../../../../../Create_Repost_OrderServ?Command=loademp&txtEmployeeid="+eid+"&offid="+ofid+"&pro_id="+new_ord+"&ord_cat="+ord_cat;
    
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
            //var oid=param;
            var loginid=document.frmTransfer.txtOffId.value;
              var oid=document.frmTransfer.txtT_OffId.value;
            var url="../../../../../Create_Repost_OrderServ?Command=office&oid="+oid+"&loginid="+loginid;
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
      var ord_id=document.frmTransfer.txtPid.value;
      var off_id=document.frmTransfer.txtOffId.value;
      
      var url="../../../../../Create_Repost_OrderServ?Command=getDet&order="+ord_id+"&offid="+off_id;
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
   
   else if(Command=="Add")
   {
      var c=checkNull();
      
      if(c)
      {
      
        document.frmTransfer.btsub.disabled=false;
      var desig=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      //var desi;
      //var reas=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
      //var rs=document.getElementById("cmbdesig");
      /*
      if(reas==0)
      {
       alert('Select reason');
       return false;
      }
      else if((reas==2) || (reas==6))
      {
        desi=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
        rs.style.display="block";
        rs.disabled=false;
      }
      else
      {
       desi=document.frmTransfer.txt_desid.value;
        rs.style.display="none";
        rs.disabled=true;
      }
      */
      
      
      var url="../../../../../Create_Repost_OrderServ?Command=Add&txtEmployeeid="+eid+"&offid="+ofid+"&repost="+rep+"&trfoff="+trof+"&order="+new_ord+"&req_trans="+req_transf+"&desig="+desig;
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
      
   }
   
   else if(Command=="Update")
   {
      var desi=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      var url="../../../../../Create_Repost_OrderServ?Command=Update&txtEmployeeid="+eid+"&offid="+ofid+"&repost="+rep+"&trfoff="+trof+"&desig_id="+desi+"&order="+ord;
      
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
      var url="../../../../../Create_Repost_OrderServ?Command=Delete&txtEmployeeid="+eid+"&offid="+ofid+"&order="+ord;
      
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
   
   else if(Command=="getProcid")
   {
      var url="../../../../../Create_Repost_OrderServ?Command=ProcId&txtoffid="+ofid;
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
   
   else if(Command=="desig")
   {
     var desig=document.frmTransfer.txtP_desigId.value;
   
      var url="../../../../../Create_Repost_OrderServ?Command=Desig&txtDesigid="+desig;
      
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
      else if(Command=="add")
      {
        addData(baseResponse);
      }
      else if(Command=="ProcId")
      {
        orderId(baseResponse);
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
        var posting=baseResponse.getElementsByTagName("curr_post")[0].firstChild.nodeValue;
        var des_id=baseResponse.getElementsByTagName("desig_id")[0].firstChild.nodeValue;
        
        var repost_req=baseResponse.getElementsByTagName("repost_req")[0].firstChild.nodeValue;
       
        var ta_da=baseResponse.getElementsByTagName("ta_da")[0].firstChild.nodeValue;
       

        //document.frmTransfer.birthDate.value=dob;
        document.frmTransfer.txtEmployeeid.value=eid;
        document.frmTransfer.txtEmpName.value=ename;
        document.frmTransfer.txtEmpDesig.value=desig;  
        document.frmTransfer.txtCurOff.value=posting;
        document.frmTransfer.txt_desid.value=des_id;
        if(repost_req=="Y")
        document.frmTransfer.radT_Repost[0].checked=true;
        else if(repost_req=="N")
        document.frmTransfer.radT_Repost[1].checked=true;
         if(ta_da=="Y")
        document.frmTransfer.radTransfer[0].checked=true;
        else if(ta_da=="N")
        document.frmTransfer.radTransfer[1].checked=true;
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
     
     alert("Office Id '"+oid+"' is not under your juridiction");
     
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
           var repost=document.createTextNode(repos[i]);
           cell6.appendChild(repost);
           mycurrent_row.appendChild(cell6);
           
           tbody.appendChild(mycurrent_row);
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
             
             var pres_officer=baseres.getElementsByTagName("pres_off")[0].firstChild.nodeValue;
             var officer_desig=baseres.getElementsByTagName("pres_off_des")[0].firstChild.nodeValue;
             var subj=baseres.getElementsByTagName("pro_subj")[0].firstChild.nodeValue;
             var ref=baseres.getElementsByTagName("pro_ref")[0].firstChild.nodeValue;
             var add_par_1=baseres.getElementsByTagName("add_par1")[0].firstChild.nodeValue;
             var add_par_2=baseres.getElementsByTagName("add_para2")[0].firstChild.nodeValue;
             var copy_to=baseres.getElementsByTagName("copy_to")[0].firstChild.nodeValue;
             
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

function orderId(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
  if(flag=="success")
  {
     var ord_id1=baseResponse.getElementsByTagName("order_no")[0].firstChild.nodeValue;
     //alert(ord_id1);
     document.frmTransfer.txthPid.value=ord_id1;
   
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
  // alert(did)
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

function addData(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
   if(flag=="success")
   {
      alert('Records inserted Successfully');
      
      var items=new Array();
      
      
      items[1]=document.frmTransfer.txtEmployeeid.value;
      
      items[2]=document.frmTransfer.txtEmpName.value;
      
      //items[3]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].text;
      
      items[4]=document.frmTransfer.txtT_OffName.value;
       
       if(document.frmTransfer.radT_Repost[0].checked==true)
       {
       items[5]=document.frmTransfer.radT_Repost[0].value;
       }
       else
       {
        items[5]=document.frmTransfer.radT_Repost[1].value;
       }
       
       //items[6]=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
       //items[61]=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].text;
       
       //var rs=document.getElementById("cmbdesig");  
       //var rs1=document.getElementById("cmbDesignation");      
       
       /*
       if((items[6]==2) || (items[6]==6))
       {
         rs.style.display="block";
         rs1.disabled=false;
         
         items[3]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].text;
       }
       else
       {
         rs.style.display="none";
         rs1.disabled=true;
         items[3]=document.frmTransfer.txtEmpDesig.value;
       }
       */
       
       if(document.frmTransfer.radTransfer[0].checked==true)
       {
         items[7]=document.frmTransfer.radTransfer[0].value;
       }
       else
       {
         items[7]=document.frmTransfer.radTransfer[1].value;
       }
       
       
       /*
       if(document.frmTransfer.rad_indi[0].checked==true)
       {
         items[8]=document.frmTransfer.rad_indi[0].value;
       }
       else
       {
         items[8]=document.frmTransfer.rad_indi[1].value;
       }*/
       
      
        
        //var trf_desig=document.frmTransfer.txtEmpDesig.value;
        /*
        alert(items[1]);
        alert(items[2]);
        alert(items[3]);
        alert(items[4]);
        alert(items[5]);
        alert(items[6]);
        alert(items[7]);
        alert(items[8]);*/
        
       
          var tbody=document.getElementById("grid_body");
       
             var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=items[1];
           
           /*var cell=document.createElement("TD");
           var anc=document.createElement("A");
           var url="javascript:loadValuesFromTable('" + empid[i] + "')";
           anc.href=url;
           
           var txtedit=document.createTextNode("EDIT");
           anc.appendChild(txtedit);
           cell.appendChild(anc);
           mycurrent_row.appendChild(cell);*/
           
           var cell2=document.createElement("TD");
           var emp=document.createTextNode(items[1]);
           cell2.appendChild(emp);
           mycurrent_row.appendChild(cell2);
           
           
          var cell3=document.createElement("TD");
           var emp_name1=document.createTextNode(items[2]);
           cell3.appendChild(emp_name1);
           mycurrent_row.appendChild(cell3);
           
           /*
           var cell4=document.createElement("TD");             
           var desid=document.createTextNode(items[3]);
           cell4.appendChild(desid);          
           mycurrent_row.appendChild(cell4);    */      
           
           var cell3=document.createElement("TD");            
           var trf_off_name=document.createTextNode(items[4]);
           cell3.appendChild(trf_off_name);
            /*var trf_off_id=document.createElement("input");
             trf_off_id.type="hidden";
             trf_off_id.name="trf_offid";
             trf_off_id.id="trf_offid";
             trf_off_id.value=tr_ofid[i];
           cell5.appendChild(trf_off_id); */ 
           mycurrent_row.appendChild(cell3);
           
           /*           
           var cell6=document.createElement("TD");
           var reson=document.createTextNode(items[61]);
           cell6.appendChild(reson);
           mycurrent_row.appendChild(cell6);*/
           
           var cell4=document.createElement("TD");
           var repost=document.createTextNode(items[5]);
           cell4.appendChild(repost);
           mycurrent_row.appendChild(cell4);
           
           var cell5=document.createElement("TD");
           var ta_da=document.createTextNode(items[7]);
           cell5.appendChild(ta_da);
           mycurrent_row.appendChild(cell5);
           
           /*
           var cell9=document.createElement("TD");
           var indi=document.createTextNode(items[8]);
           cell9.appendChild(indi);
           mycurrent_row.appendChild(cell9);
           */
           
           tbody.appendChild(mycurrent_row);
       
       
       
       
      
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtCurOff.value="";
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value=""; 
        document.frmTransfer.cmbEmpid.value='0';
        
        //document.frmTransfer.cmbsgroup.value='0';
        /*
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
        */
        
        
        
       
        
   }
   else
   {
     alert('Given Employee Id is already added');
     
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtCurOff.value="";
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value=""; 
        
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
        document.frmTransfer.cmbEmpid.value="0";
        //document.frmTransfer.txtP_DesigName.value="";
        //document.frmTransfer.cmbsgroup.value='0';
        //var des=document.getElementById("cmbDesignation");
        //des.innerHTML='';
        //document.frmTransfer.cmbDesignation.value='0';
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

function getPost_reas()
{
    var rs1=document.getElementById("cmbDesignation");
    var rs=document.getElementById("cmbdesig");
    if(document.frmTransfer.rad_ROC[0].checked==true)
    {
      var reas=document.frmTransfer.rad_ROC[0].value;
       if(reas=="TRN")
        {
          rs.style.display="none";
          rs1.disabled=true;
        }
    }
    else if(document.frmTransfer.rad_ROC[1].checked==true)
    {
      var reas=document.frmTransfer.rad_ROC[1].value;
       if(reas=="PRO")
        {
          rs.style.display="block";
          rs1.disabled=false;
        }
    }
    else if(document.frmTransfer.rad_ROC[2].checked==true)
    {
      var reas=document.frmTransfer.rad_ROC[2].value;
       if(reas=="RPT")
        {
          rs.style.display="none";
          rs1.disabled=true;
        }
    }
  
  }
 
function getReference()
{

  refData1();

   var ord_cat="";
   var eid=document.getElementById("cmbEmpid");
  
  if(document.frmTransfer.rad_ROC[0].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[0].value;
    document.frmTransfer.txtProDat.value="";
    eid.innerHTML="";
    getPost_reas();
  }
  else if(document.frmTransfer.rad_ROC[1].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[1].value;
    document.frmTransfer.txtProDat.value="";
    eid.innerHTML="";
    getPost_reas();
  }
  else if(document.frmTransfer.rad_ROC[2].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[2].value;
    document.frmTransfer.txtProDat.value="";
    eid.innerHTML="";
    getPost_reas();
  }
  var off_id=document.frmTransfer.txtOffId.value;  
  
  startwaiting(document.frmTransfer);
  
  var url="../../../../../Create_Repost_OrderServ?Command="+ord_cat+"&off_id="+off_id;
  var req=getTransport();
  req.open("GET",url,true);
  
  req.onreadystatechange=function()
  {
    loadReference(req);
  }
  if(window.XMLHttpRequest)
            req.send(null);
  else req.send();  
  
}

function loadReference(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
      var ref=document.getElementById("cmbProceeding");
      var i=0;
      
      var response=req.responseXML.getElementsByTagName("response")[0];
      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
      
      ref.innerHTML="";
      
      stopwaiting(document.frmTransfer);
      
      if(flag=="failure")
      {
        alert('No Proceeding exists');
      }
      else
      {
         var value=response.getElementsByTagName("option");         
         var option=document.createElement("OPTION");
         option.text="---Select Proceeding No---";
         option.value="0";
         
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         
         for(i=0;i<value.length;i++)
         {
           var tmpoption=value.item(i);           
           var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
           var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
           
           var option=document.createElement("OPTION");
           option.text=name;
           option.value=id;
           
         try
         {
           ref.add(option);
         }
         catch(errorObject)
         {
           ref.add(option,null);
         }
         }
      }
    }
  }
}

function getDt()
{
   refData();
  
  var ord_cat="";
  
  if(document.frmTransfer.rad_ROC[0].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[0].value;
  }
  else if(document.frmTransfer.rad_ROC[1].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[1].value;
  }
  else if(document.frmTransfer.rad_ROC[2].checked==true)
  {
    ord_cat=document.frmTransfer.rad_ROC[2].value;
  }
  
  var pro_id=document.frmTransfer.cmbProceeding.options[document.frmTransfer.cmbProceeding.selectedIndex].value;
  var off_id=document.frmTransfer.txtOffId.value;
     startwaiting(document.frmTransfer);
  
       var url="../../../../../Create_Repost_OrderServ?Command=getDt&category="+ord_cat+"&pid="+pro_id+"&off_id="+off_id;
       
       var req=getTransport();
       req.open("GET",url,true);
       req.onreadystatechange=function()
       {
         loadDt(req);
       }
       
       if(window.XMLHttpRequest)
         req.send(null);
       else req.send();    
  
}

function loadDt(req)
{
  if(req.readyState==4)
  {
    if(req.status==200)
    {
   // alert("jjoo");
      
       var eid=document.getElementById("cmbEmpid");
       var i=0;
       
       var response=req.responseXML.getElementsByTagName("response")[0];
       var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
       eid.innerHTML="";
      
      stopwaiting(document.frmTransfer);
       
       if(flag=="failurea")
       {
         alert('Employee Id doesnt exists');
       }
       else
       {        
         var prodat=response.getElementsByTagName("pro_date")[0].firstChild.nodeValue;
         
         var m=prodat.split('-');
         var prodat1=m[2]+"/"+m[1]+"/"+m[0];
         
         if(prodat1!=null)
         document.frmTransfer.txtProDat.value=prodat1;
         else
         document.frmTransfer.txtProDat.value="";
        
         var prono=document.frmTransfer.cmbProceeding.options[document.frmTransfer.cmbProceeding.selectedIndex].text;
        //var detail= prodet+" Order Details"+"\n"+"Proceeding Number is:  "+prono+"\n"+"Proceeding Date is: "+prodat1;
         //alert(detail);
         var detail= "Proceedings No. :"+prono+" Dt. "+prodat1;
         //alert(detail);
         document.frmTransfer.txtRef.value=detail;
         var value=response.getElementsByTagName("option");
         
         var option=document.createElement("OPTION");
         option.text="---Select Employee Id---";
         option.value="0";
         
         try
         {
           eid.add(option)
         }
         catch(errorObject)
         {
           eid.add(option,null);
         }
         
         for(i=0;i<value.length;i++)
         {
           var tmpoption=value.item(i);
           
           var id=tmpoption.getElementsByTagName("employee_id")[0].firstChild.nodeValue;
           
           var option=document.createElement("OPTION");
           option.text=id;
           option.value=id;
           
           try
           {
             eid.add(option);
           }
           catch(errorObject)
           {
             eid.add(option,null);
           }
         }
         
         /*
         var prodat=response.getElementsByTagName("pro_date")[0].firstChild.nodeValue;
         
         if(prodat!=null)
         document.frmTransfer.txtProDat.value=prodat;
         else
         document.frmTransfer.txtProDat.value="";
         */
       }
       
        
    
    }
  }

}


function loadEmp()
{
  refData();

  var emp=document.frmTransfer.cmbEmpid.options[document.frmTransfer.cmbEmpid.selectedIndex].value;
  
  document.frmTransfer.txtEmployeeid.value=emp;
  
  doFunction('loademp','null');
}

function refData()
{
   document.frmTransfer.txtEmployeeid.value="";
   document.frmTransfer.birthDate.value="";
   document.frmTransfer.txtEmpName.value="";
   document.frmTransfer.txtEmpDesig.value="";
   document.frmTransfer.txtCurOff.value="";
   document.frmTransfer.txtT_OffId.value="";
   document.frmTransfer.txtT_OffName.value="";
   
}

function refData1()
{
   document.frmTransfer.txtRno.value="";
   document.frmTransfer.txtPDat.value="";
   document.frmTransfer.txtEmployeeid.value="";
   document.frmTransfer.birthDate.value="";
   document.frmTransfer.txtEmpName.value="";
   document.frmTransfer.txtEmpDesig.value="";
   document.frmTransfer.txtCurOff.value="";
   document.frmTransfer.txtT_OffId.value="";
   document.frmTransfer.txtT_OffName.value="";
   
}



/*
function checkProNo()
{
        
   var type=document.frmTransfer.cmbProceeding.options[document.frmTransfer.cmbProceeding.selectedIndex].value;
   if(type==0)
   {
     alert('Select Proceeding No.');
     document.frmTransfer.cmbProceeding.focus();
     return false;
   }
}
*/


function loadServiceGroup1(val)
{
   //alert(val); 
    var url="../../../../../Create_Repost_OrderServ?Command=SerGroup&cmbdes="+val;
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

/*
function getreason()
{
  var reas=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
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
    rs1.disabled=true;
  }
  else
  {
    rs.style.display="block";
    rs1.disabled=false;
  }
  }
}
*/
 
function checkNull()
{
  if((document.frmTransfer.txtEmployeeid.value=="") || (document.frmTransfer.txtEmployeeid.value.length==0))
  {
    alert('Please select Employee Id');
    return false;
  }
  
  if((document.frmTransfer.txtT_OffId.value=="") || (document.frmTransfer.txtT_OffId.value.length==0))
  {
    alert('Please select Office Id');
    return false;
  }
  
  //var reas=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
  /*
  if(reas==0)
  {
    alert('Please select Reason');
    return false;
  }
  
  var rea=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;  
  
  //alert(rea);
  
  if(rea == 1)
  {
    return true;
  }
 // else if((rea != 0) || (rea != 1))
  else
  {
    
    if((reas==2) || (reas==6))
    {
        var ser=document.frmTransfer.cmbsgroup.options[document.frmTransfer.cmbsgroup.selectedIndex].value;
    
      
        if(ser==0)
        {
          alert('Please select Service Group');
          return false;
        }
         
        var desi=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      
        if(desi!='0')
        {
          return true;
        }
        else
        {
          alert('Please select Designation');
          return false;
        }
        
        return true;
    }
    
  }
  */
  
  return true;
  
}

function checknull2()
{
  if((document.frmTransfer.txtRno.value=="") || (document.frmTransfer.txtRno.value.length==0))
  {
    alert('Please Enter Proceeding reference No.');
    return false;
  }

  else if((document.frmTransfer.txtPO.value=="") || (document.frmTransfer.txtPO.value.length==0))
  {
    alert('Please Enter Presiding Officer in Order details tab');
    return false;
  }
  else if((document.frmTransfer.txtPOD.value=="") || (document.frmTransfer.txtPOD.value.length==0))
  {
    alert('Please Enter Presiding Officer Designation in Order details tab');
    return false;
  }
  else if((document.frmTransfer.txtSub.value=="") || (document.frmTransfer.txtSub.value.length==0))
  {
    alert('Please Enter Subject in Order details tab');
    return false;
  }
  /*
  else if((document.frmTransfer.txtRef.value=="") || (document.frmTransfer.txtRef.value.length==0))
  {
    alert('Please Enter Reference in Order details tab');
    return false;
  }*/
  /*
  else if((document.frmTransfer.txtAdd1.value=="") || (document.frmTransfer.txtAdd1.value.length==0))
  {
    alert('Please Enter Additional Para 1 in Order details tab');
    return false;
  }
  
  else if((document.frmTransfer.txtAdd2.value=="") || (document.frmTransfer.txtAdd2.value.length==0))
  {
    alert('Please Enter Additional Para 2 in Order details tab');
    return false;
  }
  */
  else if((document.frmTransfer.txtcopy.value=="") || (document.frmTransfer.txtcopy.value.length==0))
  {
    alert('Please Enter Copy to in Order details tab');
    return false;
  }
  
  return true;
}


function chkSubj()
{
  var subj=document.frmTransfer.txtSub.value.length;
  
  if(subj>400)
  {
   alert('Character length is exceeding 400');
   //document.frmTransfer.txtSub.value="";
   document.frmTransfer.txtSub.focus();
   return false;
  }
  return true;
}

/*
function chkRef()
{
  var ref=document.frmTransfer.txtRef.value.length;
  
  if(ref>400)
  {
    alert('Character length is exceeding 400');
    //document.frmTransfer.txtRef.value="";
   document.frmTransfer.txtRef.focus();
    return false;
  }
  return true;
}
*/
function chkPara1()
{
   var para1=document.frmTransfer.txtAdd1.value.length;
   
   if(para1>400)
   {
     alert('Character length is exceeding 400');
     //document.frmTransfer.txtAdd1.value="";
   document.frmTransfer.txtAdd1.focus();
     return false;
   }
   return true;
}

function chkPara2()
{
   var para2=document.frmTransfer.txtAdd2.value.length;
   
   if(para2>400)
   {
     alert('Character length is exceeding 400');
     //document.frmTransfer.txtAdd2.value="";
   document.frmTransfer.txtAdd2.focus();
     return false;
   }
   return true;
}

function chkCopy()
{
  var cop=document.frmTransfer.txtcopy.value.length;
  
  if(cop>400)
  {
    alert('Character length is exceeding 400');
    //document.frmTransfer.txtcopy.value="";
   document.frmTransfer.txtcopy.focus();
     return false;
  }
  return true;
}

function showFor()
{
  var i1=document.getElementById("txtForwad1");
  var i2=document.getElementById("txtForwad2");
  
        i1.style.display="block";
        i1.disabled=false;
        
        i2.style.display="block";
        i2.disabled=false;
        
        document.frmTransfer.txtFON.value="";
        document.frmTransfer.txtFOD.value="";
        
}

function showHid()
{
   var i1=document.getElementById("txtForwad1");
   var i2=document.getElementById("txtForwad2");
  
        i1.style.display="none";
        i1.disabled=true;
        
        i2.style.display="none";
        i2.disabled=true;
}


function chkLen()
{
  var off_len=document.frmTransfer.txtT_OffId.value.length;
  //alert(off_len);
  
  if(off_len < 4)
  {
    alert('Please Enter Valid Office Id');
    document.frmTransfer.txtT_OffId.value="";
    document.frmTransfer.txtT_OffName.value="";
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
