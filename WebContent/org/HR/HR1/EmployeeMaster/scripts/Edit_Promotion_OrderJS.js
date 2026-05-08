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

/*
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
*/
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
              
        var post;
        
        if(document.frmTransfer.radT_Posting[0].checked==true)
        {
          post=document.frmTransfer.radT_Posting[0].value;
        }
        else
        {
          post=document.frmTransfer.radT_Posting[1].value;
        }
        
        var departtid;
        var offid;
        if(document.frmTransfer.radT_Posting[0].checked==true)
        {
          departtid=document.frmTransfer.cmb_Deptid.options[document.frmTransfer.cmb_Deptid.selectedIndex].value;
          offid=document.frmTransfer.txtT_OffId.value;
        }
        else
        {
          departtid="TWAD";
          offid=document.frmTransfer.txt_offid.value;
        }
        
        var rep;
        if(document.frmTransfer.radT_Repost[0].checked==true)
        {
           rep=document.frmTransfer.radT_Repost[0].value;
        }
        else
        {
           rep=document.frmTransfer.radT_Repost[1].value;
        }
        
        var req_transf;
        if(document.frmTransfer.radTransfer[0].checked==true)
        {
           req_transf=document.frmTransfer.radTransfer[0].value;
        }
        else
         {
           req_transf=document.frmTransfer.radTransfer[1].value;
         }
            
        
       
        var ord=document.frmTransfer.txtPid.options[document.frmTransfer.txtPid.selectedIndex].value;
        //var new_ord=document.frmTransfer.txthPid.value;  
  
  if(Command=="loademp")
  {
    var url="../../../../../Edit_Promotion_OrderServ?Command=loademp&txtEmployeeid="+eid+"&offid="+ofid;
    
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
            
            var url="../../../../../Edit_Promotion_OrderServ?Command=office&oid="+oid;
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
      
      var url="../../../../../Edit_Promotion_OrderServ?Command=getDet&order="+ord+"&offid="+off_id;
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
   
   if(Command=="Add")
   {
      var c=checkNull();
     
      if(c)
      {
      
         document.frmTransfer.btsub.disabled=false;
      
      var desnew=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      
      var url="../../../../../Edit_Promotion_OrderServ?Command=Add&txtEmployeeid="+eid+"&offid="+ofid+"&new_desig="+desnew+"&posting="+post+"&department="+departtid+"&post_offid="+offid+"&repost="+rep+"&ta_da="+req_transf+"&order="+ord;
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
       var c=checkNull();
       
       if(c)
       {
        var desnew=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      
      var url="../../../../../Edit_Promotion_OrderServ?Command=Update&txtEmployeeid="+eid+"&offid="+ofid+"&new_desig="+desnew+"&posting="+post+"&department="+departtid+"&post_offid="+offid+"&repost="+rep+"&ta_da="+req_transf+"&order="+ord;
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
   
   else if(Command=="Delete")
   {
      var url="../../../../../Edit_Promotion_OrderServ?Command=Delete&txtEmployeeid="+eid+"&offid="+ofid+"&order="+ord;
      
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
   
      var url="../../../../../Edit_Promotion_OrderServ?Command=Desig&txtDesigid="+desig;
      
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
   
   else if(Command=="dept")
   {
     var depart=document.frmTransfer.cmb_Deptid.options[document.frmTransfer.cmb_Deptid.selectedIndex].value;
     var dpt_offid=document.frmTransfer.txtT_OffId.value;
     
     var url="../../../../../Edit_Promotion_OrderServ?Command=Deptid&dept_id="+depart+"&office_id="+dpt_offid;
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
      else if(Command=="Department")
      {
        deptName(baseResponse);
      }
    }
  }

}

function deptName(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
  if(flag=="success")
  {
    var offname=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
    
    document.frmTransfer.txtT_OffName.value=offname;
  }
  else
  {
    alert('Sorry there is no Office Name');
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
        var off_id=baseResponse.getElementsByTagName("office_id")[0].firstChild.nodeValue;
        
        maxfromdate=baseResponse.getElementsByTagName("maxfromdate")[0].firstChild.nodeValue;
       
        maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
       
        var ofid;
        if((off_id==null) || (off_id==0))
        {
         ofid=0;
        }
        else
        {
          ofid=off_id;
        }

        //document.frmTransfer.birthDate.value=dob;
        document.frmTransfer.txtEmployeeid.value=eid;
        document.frmTransfer.txtEmpName.value=ename;
        document.frmTransfer.txtEmpDesig.value=desig;  
        document.frmTransfer.txtCurOff.value=posting;
        document.frmTransfer.txt_offid.value=ofid;
        document.frmTransfer.txt_desid.value=desig_id;
        
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
        alert("Employee Id '"+eid+"' alread has  a unfrezeed Promotion record.");
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.frmTransfer.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in working status. So can not create Promotion.");
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
            alert("You have no Current Posting. Can not Create Promotion for "+eid+"!");
            document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.frmTransfer.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Promotion!");
            document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmployeeid.focus();
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.frmTransfer.txtEmployeeid.value;
            alert("Can not Create Promotion. Access Denined!");
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
      
      items[41]=document.frmTransfer.txtT_OffId.value;
       
       if(document.frmTransfer.radT_Repost[0].checked==true)
       {
       items[5]=document.frmTransfer.radT_Repost[0].value;
       }
       else
       {
        items[5]=document.frmTransfer.radT_Repost[1].value;
       }
       
       items[6]=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
       
       items[61]=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].text;
       
       var rs=document.getElementById("cmbdesig");  
       var rs1=document.getElementById("cmbDesignation");

       if(items[6]==2)
       {
        rs.style.display="none";
        rs1.disabled=true;
        items[3]=document.frmTransfer.txtEmpDesig.value;
        items[31]=document.frmTransfer.txt_desid.value;
       }
       else
       {
         rs.style.display="block";
         rs1.disabled=false;
         
         items[3]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].text;
         items[31]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
       }
       
       if(document.frmTransfer.radTransfer[0].checked==true)
       {
         items[7]=document.frmTransfer.radTransfer[0].value;
       }
       else
       {
         items[7]=document.frmTransfer.radTransfer[1].value;
       }
       
       
       items[8]=document.frmTransfer.txtP_DesigName.value;
      
      
        var dep=document.frmTransfer.cmb_Deptid.options[document.frmTransfer.cmb_Deptid.selectedIndex].value;
        
        if(dep=='0')
        {
          alert('Select appropriate Department');
        }
        else
        {
          items[9]=dep;
        }
        
      
        
       
          var tbody=document.getElementById("grid_body");
       
             var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=items[1];
           
           var cell=document.createElement("TD");
           var anc=document.createElement("A");
           var url="javascript:loadValuesFromTable('" + items[1] + "')";
           anc.href=url;
           
           var txtedit=document.createTextNode("Edit");
           anc.appendChild(txtedit);
           cell.appendChild(anc);
           mycurrent_row.appendChild(cell);
           
           var cell2=document.createElement("TD");
           var emp=document.createTextNode(items[1]);
           cell2.appendChild(emp);
           mycurrent_row.appendChild(cell2);
           
           
          var cell3=document.createElement("TD");
           var emp_name1=document.createTextNode(items[2]);
           cell3.appendChild(emp_name1);
           mycurrent_row.appendChild(cell3);
           
           var cell4=document.createElement("TD");             
           var desid=document.createTextNode(items[3]);
           cell4.appendChild(desid);
             var desig_id=document.createElement("input");
             desig_id.type="hidden";
             desig_id.name="did";
             desig_id.id="did";
             desig_id.value=items[31];
           cell4.appendChild(desig_id);
           mycurrent_row.appendChild(cell4);
           
           var cell5=document.createElement("TD");
           var depid1=document.createTextNode(items[9]);
           cell5.appendChild(depid1);
           mycurrent_row.appendChild(cell5);
           
                      
           var cell6=document.createElement("TD");            
           var trf_off_name=document.createTextNode(items[4]);
           cell6.appendChild(trf_off_name);
             var trf_off_id=document.createElement("input");
             trf_off_id.type="hidden";
             trf_off_id.name="trf_offid";
             trf_off_id.id="trf_offid";
             trf_off_id.value=items[41];
             cell6.appendChild(trf_off_id); 
           mycurrent_row.appendChild(cell6);
           
           var cell7=document.createElement("TD");
           var dpn_desig=document.createTextNode(items[8]);
           cell7.appendChild(dpn_desig);
           mycurrent_row.appendChild(cell7);
           
           var cell8=document.createElement("TD");
           var reson=document.createTextNode(items[61]);
           cell8.appendChild(reson);
             var reas_id=document.createElement("input");
             reas_id.type="hidden";
             reas_id.name="reason_id";
             reas_id.id="reason_id";
             reas_id.value=items[6];
           cell8.appendChild(reas_id);
           mycurrent_row.appendChild(cell8);
           
           var cell9=document.createElement("TD");
           var repost=document.createTextNode(items[5]);
           cell9.appendChild(repost);
           mycurrent_row.appendChild(cell9);
           
           var cell10=document.createElement("TD");
           var ta_da=document.createTextNode(items[7]);
           cell10.appendChild(ta_da);
           mycurrent_row.appendChild(cell10);
           
           /*
           var cell10=document.createElement("TD");
           var indi=document.createTextNode(items[8]);
           cell10.appendChild(indi);
           mycurrent_row.appendChild(cell10);
           */
           
           tbody.appendChild(mycurrent_row);
       
       
       
       
      
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtCurOff.value="";
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value=""; 
        document.frmTransfer.txtP_DesigName.value="";
        
        document.frmTransfer.cmbsgroup.value='0';
        
        document.frmTransfer.cmb_Deptid.value='0';
        
        document.frmTransfer.cmbreasid.value='0';
        
        if(document.frmTransfer.cmbreasid.value='0')
        {
          getreason1(); 
        }
        
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
        
        
        
        
       
        
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
        document.frmTransfer.txtP_DesigName.value="";
        
        document.frmTransfer.cmbsgroup.value='0';
        
        document.frmTransfer.cmb_Deptid.value='0';
        
        document.frmTransfer.cmbreasid.value='0';
        
        if(document.frmTransfer.cmbreasid.value='0')
        {
          getreason1(); 
        }
        
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
   }
   
}

function getreason1()
{
  var reas=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
  var rs1=document.getElementById("cmbDesignation");
  
  var rs=document.getElementById("cmbdesig");
  
  if(reas==0)
  {
   //alert('Please select the Reason');
    rs.style.display="none";
    rs1.disabled=true;
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
              var reas_name=new Array();
              var ta_da=new Array();
              var pst=new Array();
              var dep_id=new Array();
              var tdpn_desig=new Array();
              //var dpndesig=new Array();
               
              
               
              for(i=0;i<service.length;i++)
              {
                empid[i]=service[i].getElementsByTagName("employee_id")[0].firstChild.nodeValue;
                //alert(empid[i]);
                tr_ofid[i]=service[i].getElementsByTagName("pro_office_id")[0].firstChild.nodeValue;
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
                
                if(off_name[i]=='null')
                {
                  off_name[i]="";
                }
                
                
                //reas[i]=service[i].getElementsByTagName("reason")[0].firstChild.nodeValue;
                
                //reas_name[i]=service[i].getElementsByTagName("reas")[0].firstChild.nodeValue;
                
                ta_da[i]=service[i].getElementsByTagName("ta_da")[0].firstChild.nodeValue;
                
                //ind_addr[i]=service[i].getElementsByTagName("ind_addr")[0].firstChild.nodeValue;
                
                dep_id[i]=service[i].getElementsByTagName("dep_name")[0].firstChild.nodeValue;
                
                pst[i]=service[i].getElementsByTagName("posting")[0].firstChild.nodeValue;
                
                //tdpn_desig[i]=service[i].getElementsByTagName("dpn_desig")[0].firstChild.nodeValue;
                //alert(tdpn_desig[i]);
              
                if(tdpn_desig[i]=='null')
                {
                  tdpn_desig[i]="";
                }         
                
                          
                
              var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=empid[i];
           
           var cell=document.createElement("TD");
           var anc=document.createElement("A");
           var url="javascript:loadValuesFromTable('" + empid[i] + "')";
           anc.href=url;
           
           var txtedit=document.createTextNode("Edit");
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
             cell4.appendChild(dees_id);
           mycurrent_row.appendChild(cell4);
           
           
           var cell5=document.createElement("TD");
           var posting=document.createTextNode(pst[i]);
           cell5.appendChild(posting);
           mycurrent_row.appendChild(cell5);
           
           
           var cell6=document.createElement("TD");
           var dep=document.createTextNode(dep_id[i]);
           cell6.appendChild(dep);
           mycurrent_row.appendChild(cell6);
           
           
           var cell7=document.createElement("TD");           
           var trf_off_name=document.createTextNode(off_name[i]);
           cell7.appendChild(trf_off_name);
            var trf_off_id=document.createElement("input");
             trf_off_id.type="hidden";
             trf_off_id.name="trf_offid";
             trf_off_id.id="trf_offid";
             trf_off_id.value=tr_ofid[i];
           cell7.appendChild(trf_off_id);  
           mycurrent_row.appendChild(cell7);           
          
           
           var cell8=document.createElement("TD");
           var repost=document.createTextNode(repos[i]);
           cell8.appendChild(repost);
           mycurrent_row.appendChild(cell8);
           
          
           
           var cell9=document.createElement("TD");
           var tda=document.createTextNode(ta_da[i]);
           cell9.appendChild(tda);
           mycurrent_row.appendChild(cell9);        
          
           
           tbody.appendChild(mycurrent_row);
          
          
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
             
             var pref=baseres.getElementsByTagName("prefix")[0].firstChild.nodeValue;
             var pres_officer=baseres.getElementsByTagName("pres_off")[0].firstChild.nodeValue;
             var suf=baseres.getElementsByTagName("suffix")[0].firstChild.nodeValue;
             var officer_desig=baseres.getElementsByTagName("pres_off_des")[0].firstChild.nodeValue;
             var subj=baseres.getElementsByTagName("pro_subj")[0].firstChild.nodeValue;
             var ref=baseres.getElementsByTagName("pro_ref")[0].firstChild.nodeValue;
             var add_par_1=baseres.getElementsByTagName("add_par1")[0].firstChild.nodeValue;
             var add_par_2=baseres.getElementsByTagName("add_para2")[0].firstChild.nodeValue;
             var copy_to=baseres.getElementsByTagName("copy_to")[0].firstChild.nodeValue;
             var ind_addr=baseres.getElementsByTagName("ind_addr")[0].firstChild.nodeValue;
             
             if(pref=='null')
                document.frmTransfer.txtPref.value="";
             else
               document.frmTransfer.txtPref.value=pref;
             
             if(pres_officer=='null')
                document.frmTransfer.txtPO.value="";
             else 
                document.frmTransfer.txtPO.value=pres_officer;
                
             if(suf=='null')
                document.frmTransfer.txtSuf.value="";
             else
               document.frmTransfer.txtSuf.value=suf;   
                
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
              
             if(ind_addr=='Y')
                document.frmTransfer.rad_indi[0].checked=true;
             else
               document.frmTransfer.rad_indi[1].checked=true;
               
               document.frmTransfer.btadd.disabled=false;
               document.frmTransfer.btupdate.disabled=true;
               document.frmTransfer.btdelete.disabled=true;
               document.frmTransfer.btclear.disabled=false;
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

               document.frmTransfer.btadd.disabled=true;
               document.frmTransfer.btupdate.disabled=false;
               document.frmTransfer.btdelete.disabled=false;
               document.frmTransfer.btclear.disabled=false;

   comid=rid;
   var r=document.getElementById(rid);
   var rcells=r.cells;
   
   var tbody=document.getElementById("grid_body");
   var table=document.getElementById("mytable");
   
   document.frmTransfer.txtEmployeeid.value=rcells.item(1).firstChild.nodeValue;
   
   document.frmTransfer.txtEmpName.value=rcells.item(2).firstChild.nodeValue;
   
   var did1=rcells.item(3).lastChild.value
   
   loadServiceGroup1(did1);   
     
   //document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value=rcells.item(3).lastChild.value;
   
   document.frmTransfer.cmbDesignation.value=rcells.item(3).lastChild.value;
   
   var depart=document.getElementById("dep_row");
   var off_id=document.getElementById("offid_row");
   var off_name=document.getElementById("offnme_row"); 
   
   //alert(rcells.item(4).firstChild.nodeValue);
   
   if(rcells.item(4).firstChild.nodeValue=="Y")
   {
     depart.style.display="block";
     off_id.style.display="block";
     off_name.style.display="block";
    
     document.frmTransfer.radT_Posting[0].checked=true;
     
     document.frmTransfer.cmb_Deptid.value=rcells.item(5).firstChild.nodeValue;
     
     document.frmTransfer.txtT_OffId.value=rcells.item(6).lastChild.value;
     
     document.frmTransfer.txtT_OffName.value=rcells.item(6).firstChild.nodeValue;
   }
   else
   {
     depart.style.display="none";
     off_id.style.display="none";
     off_name.style.display="none";
     
     document.frmTransfer.cmb_Deptid.value='0';
     
     document.frmTransfer.txtT_OffId.value="";
     
     document.frmTransfer.txtT_OffName.value="";
   
     document.frmTransfer.radT_Posting[1].checked=true;
   }
   
   if(rcells.item(7).firstChild.nodeValue=="Y")
     document.frmTransfer.radT_Repost[0].checked=true;
   else    
     document.frmTransfer.radT_Repost[1].checked=true;
     
     if(rcells.item(8).firstChild.nodeValue=="Y")
       document.frmTransfer.radTransfer[0].checked=true;
     else
       document.frmTransfer.radTransfer[1].checked=true;
   
   
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


function addData(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
   if(flag=="success")
   {
      alert('Records inserted Successfully');
      
      var items=new Array();
      
      
      items[1]=document.frmTransfer.txtEmployeeid.value;
      
      items[2]=document.frmTransfer.txtEmpName.value;
      
      items[3]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].text;
      
      items[31]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      
      if(document.frmTransfer.radT_Posting[0].checked==true)
      {
       items[4]=document.frmTransfer.radT_Posting[0].value;
      }
      else
      {
       items[4]=document.frmTransfer.radT_Posting[1].value;
      }
      
      if(items[4]=="Y")
      {
        items[5]=document.frmTransfer.cmb_Deptid.options[document.frmTransfer.cmb_Deptid.selectedIndex].value;
        
        items[6]=document.frmTransfer.txtT_OffId.value;
        
        items[61]=document.frmTransfer.txtT_OffName.value;
      }
      else
      {
        items[5]="TWAD";
        
        items[6]=document.frmTransfer.txt_offid.value;
        
        items[61]=document.frmTransfer.txtCurOff.value;
      }
      
             
       if(document.frmTransfer.radT_Repost[0].checked==true)
       {
       items[7]=document.frmTransfer.radT_Repost[0].value;
       }
       else
       {
        items[7]=document.frmTransfer.radT_Repost[1].value;
       }
       
       if(document.frmTransfer.radTransfer[0].checked==true)
       {
         items[8]=document.frmTransfer.radTransfer[0].value;
       }
       else
       {
         items[8]=document.frmTransfer.radTransfer[1].value;
       }
        
       
          var tbody=document.getElementById("grid_body");
       
             var mycurrent_row=document.createElement("TR");
              mycurrent_row.id=items[1];
           
           var cell=document.createElement("TD");
           var anc=document.createElement("A");
           var url="javascript:loadValuesFromTable('" + items[1] + "')";
           anc.href=url;
           
           var txtedit=document.createTextNode("Edit");
           anc.appendChild(txtedit);
           cell.appendChild(anc);
           mycurrent_row.appendChild(cell);
           
           var cell2=document.createElement("TD");
           var emp=document.createTextNode(items[1]);
           cell2.appendChild(emp);
           mycurrent_row.appendChild(cell2);
           
           
          var cell3=document.createElement("TD");
           var emp_name1=document.createTextNode(items[2]);
           cell3.appendChild(emp_name1);              
           mycurrent_row.appendChild(cell3);
           
           var cell4=document.createElement("TD");             
           var desid=document.createTextNode(items[3]);
           cell4.appendChild(desid);   
            var des_id=document.createElement("input");
             des_id.type="hidden";
             des_id.name="new_des";
             des_id.id="new_des";
             des_id.value=items[31];
           cell4.appendChild(des_id);
           mycurrent_row.appendChild(cell4);
           
           var cell5=document.createElement("TD");
           var posting=document.createTextNode(items[4]);
           cell5.appendChild(posting);
           mycurrent_row.appendChild(cell5);
           
                      
           var cell6=document.createElement("TD");            
           var dept_name=document.createTextNode(items[5]);
           cell6.appendChild(dept_name);
           mycurrent_row.appendChild(cell6);
           
           var cell7=document.createElement("TD");
           var off_name=document.createTextNode(items[61]);
           cell7.appendChild(off_name);
             var off_id=document.createElement("input");
             off_id.type="hidden";
             off_id.name="officeid";
             off_id.id="officeid";
             off_id.value=items[6];
           cell7.appendChild(off_id);
           mycurrent_row.appendChild(cell7);
           
           var cell8=document.createElement("TD");
           var repost=document.createTextNode(items[7]);
           cell8.appendChild(repost);
           mycurrent_row.appendChild(cell8);
           
           var cell9=document.createElement("TD");
           var ta_da=document.createTextNode(items[8]);
           cell9.appendChild(ta_da);
           mycurrent_row.appendChild(cell9);
           
           /*
           var cell10=document.createElement("TD");
           var indi=document.createTextNode(items[8]);
           cell10.appendChild(indi);
           mycurrent_row.appendChild(cell10);
           */
           
           tbody.appendChild(mycurrent_row);
       
       
       
       
      
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value="";    
        document.frmTransfer.txtCurOff.value="";
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value=""; 
        //document.frmTransfer.txtP_DesigName.value="";
        
        document.frmTransfer.cmbsgroup.value='0';
        
        document.frmTransfer.cmb_Deptid.value='0';    
        
        
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
        
   }
   
}


function upDat(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
   if(flag=="success")
   {
     alert('Records Updated Successfully');
     
     var items=new Array();
     
       
       
       items[1]=document.frmTransfer.txtEmployeeid.value;
      
      items[2]=document.frmTransfer.txtEmpName.value;
      
      items[3]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].text;
      
      items[31]=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
      
      if(document.frmTransfer.radT_Posting[0].checked==true)
      {
       items[4]=document.frmTransfer.radT_Posting[0].value;
      }
      else
      {
       items[4]=document.frmTransfer.radT_Posting[1].value;
      }
      
      if(items[4]=="Y")
      {
        items[5]=document.frmTransfer.cmb_Deptid.options[document.frmTransfer.cmb_Deptid.selectedIndex].value;
        
        items[6]=document.frmTransfer.txtT_OffId.value;
        
        items[61]=document.frmTransfer.txtT_OffName.value;
      }
      else
      {
        items[5]="TWAD";
        
        items[6]=document.frmTransfer.txt_offid.value;
        
        items[61]=document.frmTransfer.txtCurOff.value;
      }
      
             
       if(document.frmTransfer.radT_Repost[0].checked==true)
       {
       items[7]=document.frmTransfer.radT_Repost[0].value;
       }
       else
       {
        items[7]=document.frmTransfer.radT_Repost[1].value;
       }
       
       if(document.frmTransfer.radTransfer[0].checked==true)
       {
         items[8]=document.frmTransfer.radTransfer[0].value;
       }
       else
       {
         items[8]=document.frmTransfer.radTransfer[1].value;
       }
      
       
       
       var r=document.getElementById(comid);
       var rcells=r.cells;
       
       rcells.item(3).firstChild.nodeValue=items[3];
       rcells.item(3).lastChild.value=items[31];
       rcells.item(4).firstChild.nodeValue=items[4];
       rcells.item(5).firstChild.nodeValue=items[5];
       //rcells.item(5).lastChild.value=items[4];
       rcells.item(6).firstChild.nodeValue=items[61];
       rcells.item(6).lastChild.value=items[6];      
       rcells.item(7).firstChild.nodeValue=items[7];
       rcells.item(8).firstChild.nodeValue=items[8];
       
        document.frmTransfer.txtEmployeeid.value="";
        document.frmTransfer.txtEmpName.value="";
        document.frmTransfer.txtEmpDesig.value=""; 
        document.frmTransfer.txtCurOff.value="";
        
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value=""; 
       
        //document.frmTransfer.txtP_DesigName.value="";
        document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
        
        //document.frmTransfer.cmbreasid.value='0';
        
        document.frmTransfer.cmb_Deptid.value='0';
        
        document.frmTransfer.btadd.disabled=false;
        document.frmTransfer.btupdate.disabled=true;
        document.frmTransfer.btdelete.disabled=true;
     
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
        document.frmTransfer.txtCurOff.value="";
        
        document.frmTransfer.txtT_OffId.value="";
        document.frmTransfer.txtT_OffName.value="";
        
        //document.frmTransfer.txtP_DesigName.value="";
        document.frmTransfer.cmbsgroup.value='0';
        var des=document.getElementById("cmbDesignation");
        des.innerHTML='';
        document.frmTransfer.cmbDesignation.value='0';
        
        document.frmTransfer.cmbsgroup.value='0';
        
        document.frmTransfer.cmb_Deptid.value='0';
        
        //document.frmTransfer.cmbreasid.value='0';
        
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
        document.frmTransfer.cmb_Deptid.value='0';
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
    var url="../../../../../Edit_Promotion_OrderServ?Command=SerGroup&cmbdes="+val;
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
  
   var rs2=document.getElementById("cmbsgroup");
   var rs1=document.getElementById("cmbDesignation");
  
  if(reas==0)
  {
   alert('Please select the Reason');
  }
  else
  {
  var rs=document.getElementById("cmbdesig");
  
  if(reas=='Deputation on Transfer')
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
*/
/*
function getreason()
{
  var reas=document.frmTransfer.cmbreasid.options[document.frmTransfer.cmbreasid.selectedIndex].value;
  var rs1=document.getElementById("cmbDesignation");
  //var rs=document.getElementById("cmbdesig");
  var clas=document.getElementById("cmbsgroup");
  
  if(reas==0)
  {
   alert('Please select the Reason');
  }
  else
  {
  var rs=document.getElementById("cmbdesig");
  
  if(reas==2)
  {    
    rs.style.display="none";
    rs1.disabled=true;
  }
  else
  {
    rs.style.display="block";
    rs1.disabled=false;
    rs.disabled=false;
    clas.disabled=false;
  }
  }
}

*/

var winjob;

function jobpopup_pro()
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
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP_PRO.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

function doParentJob(offid,deptid)
{
   var dept=deptid;
   var frm_dept=document.frmTransfer.cmb_Deptid.options[document.frmTransfer.cmb_Deptid.selectedIndex].value;
   
   if(frm_dept!=0)
   {
     if(frm_dept==deptid)
     {
       document.frmTransfer.txtT_OffId.value=offid;
       doFunction('dept',null);
     }
     else
     {
      alert('Please Select Proper Department Id');
      document.frmTransfer.cmb_Deptid.value='0';
      document.frmTransfer.txtT_OffId.value="";
      document.frmTransfer.txtT_OffName.value="";
      return false;
     }   
   }
   else
   {
     alert('Please Select Department Id');
   }

  //document.frmTransfer.txtT_OffId.value=offid;
  //document.frmTransfer.txtT_OffName.value=
}

function forChildOption()
{
    if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,true);
}


function checkNull()
{
  if((document.frmTransfer.txtEmployeeid.value=="") || (document.frmTransfer.txtEmployeeid.value.length==0))
  {
    alert('Please Select Employee Id');
    return false;
  }   
  
  var reas=document.frmTransfer.cmbDesignation.options[document.frmTransfer.cmbDesignation.selectedIndex].value;
  
  if(reas==0)
  {
    alert('Please Select New Designation');
    return false;
  }
  
  if(document.frmTransfer.radT_Posting[0].checked==true)
  {
    var dept=document.frmTransfer.cmb_Deptid.options[document.frmTransfer.cmb_Deptid.selectedIndex].value;
  
     if(dept==0)
     {
       alert('Please Select Department');
       return false;
     }
     
     if((document.frmTransfer.txtT_OffId.value=="") || (document.frmTransfer.txtT_OffId.value.length==0))
     {
       alert('Please Select Office Id');
       return false;
     }
  }
  
  return true;
  
}


function checkNull2()
{
  if((document.frmTransfer.txtRno.value=="") || (document.frmTransfer.txtRno.value.length==0))
  {
    alert('Please Enter Proceeding refence no.');
    return false;
  }
  else if((document.frmTransfer.txtPref.value=="") || (document.frmTransfer.txtPref.value.length==0))
  {
    alert('Please Enter Prefix');
    return false;
  }
  else if((document.frmTransfer.txtPO.value=="") || (document.frmTransfer.txtPO.value.length==0))
  {
    alert('Please Enter Presiding Officer');
    return false;
  }
  else if((document.frmTransfer.txtSuf.value=="") || (document.frmTransfer.txtSuf.value.length==0))
  {
    alert('Please Enter Suffix');
    return false;
  }
  else if((document.frmTransfer.txtPOD.value=="") || (document.frmTransfer.txtPOD.value.length==0))
  {
    alert('Please Enter Presiding Officer Designation');
    return false;
  }
  else if((document.frmTransfer.txtSub.value=="") || (document.frmTransfer.txtSub.value.length==0))
  {
    alert('Please Enter Subject');
    return false;
  }
 /* else if((document.frmTransfer.txtRef.value=="") || (document.frmTransfer.txtRef.value.length==0))
  {
    alert('Please Enter Reference');
    return false;
  }*/
  else if((document.frmTransfer.txtcopy.value=="") || (document.frmTransfer.txtcopy.value.length==0))
  {
    alert('Please Enter Copy To');
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

function getPost()
{
  var depart=document.getElementById("dep_row");
  var off_id=document.getElementById("offid_row");
  var off_name=document.getElementById("offnme_row");
  
  if(document.frmTransfer.radT_Posting[0].checked==true)
  {
     depart.style.display="block";
     off_id.style.display="block";
     off_name.style.display="block";
  }
  else
  {
     depart.style.display="none";
     off_id.style.display="none";
     off_name.style.display="none";
  }
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
     