var rid=0;
var j=0;
var element = new Array();
var vals="";

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

function doFunction(command)
{
   if(command=="getDet")
   {
      var ord_id=document.frmDeptPanel.dptrefid.options[document.frmDeptPanel.dptrefid.selectedIndex].value;
      var off_id=document.frmDeptPanel.txtOffId.value;
      var url="../../../../../Edit_Dept_Panel_DetailsServ?command=getDet&order="+ord_id+"&offid="+off_id;
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
   else if(command=="employee")
   {
      var empid=document.frmDeptPanel.cmdempid.options[document.frmDeptPanel.cmdempid.selectedIndex].value;
      var url="../../../../../Edit_Dept_Panel_DetailsServ?command=employee&empid="+empid;
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
   else if(command=="getProcedDet")
   {
      var ord_id=document.frmDeptPanel.dptrefid.options[document.frmDeptPanel.dptrefid.selectedIndex].value;
      //alert("orid"+ord_id);
      var panel_id=document.frmDeptPanel.txtPid.options[document.frmDeptPanel.txtPid.selectedIndex].value;
      //alert("panelid"+panel_id);
      var off_id=document.frmDeptPanel.txtOffId.value;
      var url="../../../../../Edit_Dept_Panel_DetailsServ?command=getProcedDet&ord_id="+ord_id+"&panel_id="+panel_id+"&offid="+off_id;
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
   else if(command=="ProceedDetails")
   {
   // alert("hhii");
      var ord_id=document.frmDeptPanel.dptrefid.options[document.frmDeptPanel.dptrefid.selectedIndex].value;
      //alert("orid"+ord_id);
      var off_id=document.frmDeptPanel.txtOffId.value;
      var url="../../../../../Edit_Dept_Panel_DetailsServ?command=ProceedDetails&ord_id="+ord_id+"&offid="+off_id;
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
      else if(command=="add")
   {
        var c=checkNull();
        if(c)
        {
            var offid=document.frmDeptPanel.txtOffId.value;
            var refid=document.frmDeptPanel.txtPid.value;
            var eid=document.frmDeptPanel.txtEmployeeid.value;  
            var remark=document.frmDeptPanel.txtrem.value;
             
            var url="../../../../../Edit_Dept_Panel_DetailsServ?command=add&offid="+offid+"&refid="+refid+"&eid="+eid+"&remark="+remark;
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
   else if(command=="delete")
   {
            var offid=document.frmDeptPanel.txtOffId.value;
            var refid=document.frmDeptPanel.txtPid.value;
            var eid=document.frmDeptPanel.txtEmployeeid.value;  
           
            var url="../../../../../Edit_Dept_Panel_DetailsServ?command=delete&offid="+offid+"&refid="+refid+"&eid="+eid;
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
   else if(command=="update")
   {
        
            var offid=document.frmDeptPanel.txtOffId.value;
            var refid=document.frmDeptPanel.txtPid.value;
            var eid=document.frmDeptPanel.txtEmployeeid.value;  
            var remark=document.frmDeptPanel.txtrem.value;             
            var url="../../../../../Edit_Dept_Panel_DetailsServ?command=update&offid="+offid+"&refid="+refid+"&eid="+eid+"&remark="+remark;
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
      var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;   
      if(command=="getDet")
      {      
         getDetails(baseResponse);
         //alert("1");
      }
      else if(command=="getProcedDet")
      {      
         getProcedDet(baseResponse);
         //alert("1");
      } 
      else if(command=="ProceedDetails")
      {      
         ProceedDetails(baseResponse);
         //alert("1");
      } 
       else if(command=="employee")
      {      
         empDetails(baseResponse);
         //alert("1");
      }
      else if(command=="add")
      {      
         addDetails(baseResponse);
      }
      else if(command=="delete")
      {
         deleterow(baseResponse);
      }
      else if(command="update")
      {
         updaterow(baseResponse);
      }
    }
  }
}

function empDetails(res)
{
    var flag=res.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        var empid=res.getElementsByTagName("employee_id")[0].firstChild.nodeValue;
        var empname=res.getElementsByTagName("employee_name")[0].firstChild.nodeValue;
        var desig=res.getElementsByTagName("designation")[0].firstChild.nodeValue;
        var offname=res.getElementsByTagName("office_name")[0].firstChild.nodeValue;
       
        document.frmDeptPanel.txtEmployeeid.value=empid;
        document.frmDeptPanel.txtEmpName.value=empname;
        document.frmDeptPanel.txtEmpDesig.value=desig;
        document.frmDeptPanel.txtCurOff.value=offname;
    }
    else
    {
        alert("No Employee Details Found");
    }
}

function getProcedDet(req)
{
// alert("proceed");
 
         var i=0;         
         var flag=req.getElementsByTagName("flag")[0].firstChild.nodeValue;
        // alert(flag);
         if(flag=='success')
         {     
             var tbody=document.getElementById("grid_body");
            
            var service=req.getElementsByTagName("details");           
            //alert(service.length);
            if(service)
            {
             
              var empid=new Array();
              var emp_name=new Array();
              var designat=new Array();
              var off_name=new Array();
              var remark=new Array();
              
              for(i=0;i<service.length;i++)
              {
                empid[i]=service[i].getElementsByTagName("employeeid")[0].firstChild.nodeValue;
                emp_name[i]=service[i].getElementsByTagName("employee_name")[0].firstChild.nodeValue;
                designat[i]=service[i].getElementsByTagName("designation")[0].firstChild.nodeValue;
                off_name[i]=service[i].getElementsByTagName("office_name")[0].firstChild.nodeValue;
                remark[i]=service[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
                // alert("remark[i]"+remark[i]);       
           vals = empid[i]+"~"+emp_name[i]+"~"+designat[i]+"~"+off_name[i]+"~"+remark[i];  
           //alert(vals);
                var mycurrent_row=document.createElement("TR");
                mycurrent_row.id=vals;
                 
               var cell=document.createElement("TD");
               var anc=document.createElement("A");
               var url="javascript:loadTableValues('" + vals + "')";
               anc.href=url;
               var txtedit=document.createTextNode("Edit");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);

        var cell2;
        var cell3;
        var cell4;     
        var cell5;
        var cell6;
        
        cell2=document.createElement("TD");
        var empidd=document.createElement("input");
        empidd.type="hidden";
        empidd.name="eid";
        empidd.id="eid";
        empidd.value=empid[i];
        cell2.appendChild(empidd);
        var currentText=document.createTextNode(empid[i]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2); 
        
        cell3=document.createElement("TD");
        var empname=document.createElement("input");
        empname.type="hidden";
        empname.id="ename";
        empname.name="ename";
        empname.value=emp_name[i];
        cell3.appendChild(empname);
        var currentText=document.createTextNode(emp_name[i]);
        cell3.appendChild(currentText);
        mycurrent_row.appendChild(cell3); 
        
        cell4=document.createElement("TD");
        var designame=document.createElement("input");
        designame.type="hidden";
        designame.id="desig";
        designame.name="desig";
        designame.value=designat[i];
        cell4.appendChild(designame);
        var currentText=document.createTextNode(designat[i]);
        cell4.appendChild(currentText);
        mycurrent_row.appendChild(cell4); 
        
        cell5=document.createElement("TD");
        var curposting=document.createElement("input");
        curposting.type="hidden";
        curposting.id="posting";
        curposting.name="posting";
        curposting.value=off_name[i];
        cell5.appendChild(curposting);
        var currentText=document.createTextNode(off_name[i]);
        cell5.appendChild(currentText);
        mycurrent_row.appendChild(cell5);
        
        cell6=document.createElement("TD");
        var rem=document.createElement("input");
        rem.type="hidden";
        rem.id="remdet";
        rem.name="remdet";
        rem.value=remark[i];
        cell6.appendChild(rem);
        var currentText=document.createTextNode(remark[i]);
        cell6.appendChild(currentText);
        mycurrent_row.appendChild(cell6);
        
         
        tbody.appendChild(mycurrent_row);
       
            }
            
           var empproced=req.getElementsByTagName("EMPANEL_PROCEEDING_NO")[0].firstChild.nodeValue;            
           var empprocdat=req.getElementsByTagName("EMPANEL_PROCEEDING_DATE")[0].firstChild.nodeValue;
        
           
           document.frmDeptPanel.txtpno.value=empproced;
           document.frmDeptPanel.txtEDat.value=empprocdat;
           //document.frmDeptPanel.cmb_Deptid.options[document.frmDeptPanel.cmd_Deptid.selectedIndex].value=otherdeptid;
          
           
           document.frmDeptPanel.btadd.disabled=false;
           document.frmDeptPanel.btupdate.disabled=true;
           document.frmDeptPanel.btdelete.disabled=true;
           document.frmDeptPanel.btclear.disabled=false;
           document.frmDeptPanel.btsubmit.disabled=false;
            }
            
         }
         else if(flag=="failurea")
         {
           alert('Sorry there is no data');
                             
           var tbody=document.getElementById("grid_body");
           try
            {tbody.innerHTML="";}
              catch(e) 
             {tbody.innerText="";}
         }
         
         else if(flag=="failure")
         {
           alert('Failure to retrieve');
          
           var tbody=document.getElementById("grid_body");
          try
           {tbody.innerHTML="";}
          catch(e) 
          {tbody.innerText="";}
         }
  }       


function getDetails(res)
{
    var flag=res.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
       var detail=res.getElementsByTagName("details");//.firstChild.nodeValue;
       // var empanelid=new Array();
        var empid=new Array();
        var emp_id=new Array();
       /* var employee=document.getElementById("txtPid");
        employee.innerHTML="";*/
          var employeeid=document.getElementById("cmdempid");
        employeeid.innerHTML="";
        for(var i=0;i<detail.length;i++)
        {
           // empanelid[i]=res.getElementsByTagName("empanelid")[i].firstChild.nodeValue;
           // alert(empanelid[i]);
             empid[i]=res.getElementsByTagName("empid")[i].firstChild.nodeValue;
            //alert(empid[i]); 
            emp_id[i]=res.getElementsByTagName("will_employee_id")[i].firstChild.nodeValue;
            //alert(emp_id[i]); 
        }        
        
          var option1=document.createElement("OPTION");
        option1.text="--Select Employee Id--";
        option1.value="";
        
        try
        {
        employeeid.add(option1);
        }
        catch(errorObject)
        {
        employeeid.add(option1,null);
        }
        for(var i=0;i<detail.length;i++)
        {
        var option1=document.createElement("OPTION");
        option1.value=emp_id[i];
        
        option1.text=empid[i];
       
        try{
        employeeid.add(option1);
        }catch(error)
        {
        employeeid.add(option1,null);
        }
        }
        
        var deptrefno=res.getElementsByTagName("DEPT_REF_LTR_NO")[0].firstChild.nodeValue;
        var deptrefdate=res.getElementsByTagName("DEPT_REF_LTR_DATE")[0].firstChild.nodeValue;
        var deptname=res.getElementsByTagName("other_dept_name")[0].firstChild.nodeValue;
        var deptoffname=res.getElementsByTagName("other_dept_office_name")[0].firstChild.nodeValue;
        var postname=res.getElementsByTagName("post_name_dpn_dept")[0].firstChild.nodeValue;
        var payscale=res.getElementsByTagName("pay_scale_dpn_dept")[0].firstChild.nodeValue;
        var remarks=res.getElementsByTagName("remarks")[0].firstChild.nodeValue;
        document.frmDeptPanel.txtRno.value=deptrefno;
        document.frmDeptPanel.txtPDat.value=deptrefdate;
        document.frmDeptPanel.txtdept.value=deptname;
        document.frmDeptPanel.txtdeptoff.value=deptoffname;
        document.frmDeptPanel.txtpostname.value=postname;
        document.frmDeptPanel.txtpayscale.value=payscale;
        document.frmDeptPanel.txtremark.value=remarks;
    }
    else 
    {
     alert("No data found");
    }
}

 function ProceedDetails(res)
{
    var flag=res.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        var detail=res.getElementsByTagName("details");//.firstChild.nodeValue;
        var empanelid=new Array();
        var employee=document.getElementById("txtPid");
        employee.innerHTML="";
        for(var i=0;i<detail.length;i++)
        {
            empanelid[i]=res.getElementsByTagName("empanelid")[i].firstChild.nodeValue;
           // alert(empanelid[i]);
        }        
        var option=document.createElement("OPTION");
        option.text="--Select Proceeding Id--";
        option.value="";
        
        try
        {
        employee.add(option);
        }
        catch(errorObject)
        {
        employee.add(option,null);
        }
        for(var i=0;i<detail.length;i++)
        {
        var option=document.createElement("OPTION");
        option.value=empanelid[i];
        
        option.text=empanelid[i];
       
        try{
        employee.add(option);
        }catch(error)
        {
        employee.add(option,null);
        }
        }
    }
    else 
    {
     alert("No data found");
    }
}



function funclear()
{
    document.frmDeptPanel.txtEmployeeid.value="";
    document.frmDeptPanel.txtEmpName.value="";
    document.frmDeptPanel.txtEmpDesig.value="";  
    document.frmDeptPanel.txtCurOff.value="";
    document.frmDeptPanel.txtrem.value="";
    document.frmDeptPanel.cmdempid.selectedIndex="0";
     document.frmDeptPanel.btupdate.disabled=true;
    document.frmDeptPanel.btdelete.disabled=true;
    document.frmDeptPanel.btadd.disabled=false; 
}


function loadTableValues(scod)
{
  // alert("scod"+scod);
    rid=scod;
    var r=document.getElementById(scod);
    var rcells=r.cells;
    var tbody=document.getElementById("grid_body");
    var table=document.getElementById("mytable");
  
   // alert(rcells.item(1).lastChild.nodeValue); 
    document.frmDeptPanel.txtEmployeeid.value=rcells.item(1).lastChild.nodeValue;
    document.frmDeptPanel.txtEmpName.value=rcells.item(2).lastChild.nodeValue;
    document.frmDeptPanel.txtEmpDesig.value=rcells.item(3).lastChild.nodeValue;
    document.frmDeptPanel.txtCurOff.value=rcells.item(4).lastChild.nodeValue;
    document.frmDeptPanel.txtrem.value=rcells.item(5).lastChild.nodeValue;
    
    document.frmDeptPanel.btadd.disabled=true; 
    document.frmDeptPanel.btupdate.disabled=false;
    document.frmDeptPanel.btdelete.disabled=false;
}



function checkNull()
{
  var deptrefid=document.frmDeptPanel.dptrefid.options[document.frmDeptPanel.dptrefid.selectedIndex].value;
  
  if(deptrefid==0)
  {
     alert('Please Select Deputation Reference Id .');
    return false;
  }
  
  if((document.frmDeptPanel.txtpno.value=="") || (document.frmDeptPanel.txtpno.value.length==0))
  {
   alert('Please Enter Empanelment Proceeding No.');
    return false;
  }
  
  if((document.frmDeptPanel.txtEDat.value=="") || (document.frmDeptPanel.txtEDat.value.length==0))
  {
   alert('Please Enter Empanelment Proceeding Date.');
    return false;
  }
  
  var empid=document.frmDeptPanel.cmdempid.options[document.frmDeptPanel.cmdempid.selectedIndex].value;
  
  if(empid==0)
  {
     alert('Please Select Employee.');
    return false;
  }
 
  return true;
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

function fnsubmit()
{
    var url="../../../../../Edit_Dept_Panel_DetailsServ?command=submit";  
    var empid="";
    var remark="";  
    var remarks="";
    // alert(document.frmDeptPanel.eid.length);
    if(document.frmDeptPanel.eid.length>1)
    {
        for(i=0;i<document.frmDeptPanel.eid.length;i++)
        {
             empid=empid+document.frmDeptPanel.eid[i].value+"~";
            // alert(empid);
             remark=remark+document.frmDeptPanel.remdet[i].value+"~";
            //  alert(remark);
              if(remark=="")
              {
                remarks="";
              }
              else
              {
                remarks=remark;
              }
         }
        if(empid!="")
        {
             empid=empid.substring(0,empid.lastIndexOf("~"));
             url=url+"&empid="+empid; 
        }
        if(remarks!="")
        {
             remarks=remarks.substring(0,remarks.lastIndexOf("~"));
             url=url+"&remarks="+remarks; 
        }
        
        var offid=document.frmDeptPanel.txtOffId.value;
        url=url+"&offid="+offid; 
        var dptrefid=document.frmDeptPanel.dptrefid.value;
        url=url+"&dptrefid="+dptrefid; 
        var empdat=document.frmDeptPanel.txtEDat.value;
        url=url+"&empdat="+empdat;  
        var empno=document.frmDeptPanel.txtpno.value;
        url=url+"&empno="+empno; 
         var pid=document.frmDeptPanel.txtPid.value;
        url=url+"&pid="+pid; 
        var rem=document.frmDeptPanel.txtremark.value;
        var dpnrem="";
        if(rem=="")
        {
            dpnrem="";
        }
        else
        {
            dpnrem=rem;
        }
        url=url+"&dpnrem="+dpnrem; 
    }
    else
    {
        empid=document.frmDeptPanel.eid.value;
         url=url+"&empid="+empid; 
        remark=document.frmDeptPanel.remdet.value;
        //alert(remark);
        url=url+"&remarks="+remarks; 
        var offid=document.frmDeptPanel.txtOffId.value;
        url=url+"&offid="+offid; 
        var dptrefid=document.frmDeptPanel.dptrefid.value;
        url=url+"&dptrefid="+dptrefid; 
        var empdat=document.frmDeptPanel.txtEDat.value;
        url=url+"&empdat="+empdat;  
        var empno=document.frmDeptPanel.txtpno.value;
        url=url+"&empno="+empno; 
         var pid=document.frmDeptPanel.txtPid.value;
        url=url+"&pid="+pid; 
        var rem=document.frmDeptPanel.txtremark.value;
        var dpnrem="";
        if(rem=="")
        {
            dpnrem="";
        }
        else
        {
            dpnrem=rem;
        }
        url=url+"&dpnrem="+dpnrem; 
    }
    //alert("The url is"+url);
    document.frmDeptPanel.action=url;
    document.frmDeptPanel.method="post"; 
    document.frmDeptPanel.submit(); 
}

function addDetails(res)
{
 var flag=res.getElementsByTagName("flag")[0].firstChild.nodeValue;
   //alert(flag);
   if(flag=="success")
   {
     
      
        var eid=document.frmDeptPanel.txtEmployeeid.value;
        var ename=document.frmDeptPanel.txtEmpName.value;
        var desig=document.frmDeptPanel.txtEmpDesig.value;  
        var posting=document.frmDeptPanel.txtCurOff.value;
        var remark=document.frmDeptPanel.txtrem.value;
    
    vals = eid+"~"+ename+"~"+desig+"~"+posting+"~"+remark;  
    var flag = true;
    if(element.length > 0)
    {
        for(var l=0;l<element.length;l++)
        {
             if(element[l] == vals)
             {
                    alert('This Employee is Already Added in the Grid');
                    flag = false;
                }
                
            }
    }
    if(flag == true)
    {
     alert('Records inserted Successfully');
        element[j] = vals;
        j++;
   
        var tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
         var mycurrent_row=document.createElement("TR");
        
        mycurrent_row.id=vals;
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTableValues('"+vals+"')";
        anc.href=url;
        var txtedit=document.createTextNode("Edit");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0;
        var cell2;
        var cell3;
        var cell4;     
        var cell5;
        var cell6;
        
        cell2=document.createElement("TD");
        var empidd=document.createElement("input");
        empidd.type="hidden";
        empidd.name="eid";
        empidd.value=eid;
        cell2.appendChild(empidd);
        var currentText=document.createTextNode(eid);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2); 
        
        cell3=document.createElement("TD");
        var empname=document.createElement("input");
        empname.type="hidden";
        empname.name="ename";
        empname.value=ename;
        cell3.appendChild(empname);
        var currentText=document.createTextNode(ename);
        cell3.appendChild(currentText);
        mycurrent_row.appendChild(cell3); 
        
        cell4=document.createElement("TD");
        var designame=document.createElement("input");
        designame.type="hidden";
        designame.name="desig";
        designame.value=desig;
        cell4.appendChild(designame);
        var currentText=document.createTextNode(desig);
        cell4.appendChild(currentText);
        mycurrent_row.appendChild(cell4); 
        
        cell5=document.createElement("TD");
        var curposting=document.createElement("input");
        curposting.type="hidden";
        curposting.name="posting";
        curposting.value=posting;
        cell5.appendChild(curposting);
        var currentText=document.createTextNode(posting);
        cell5.appendChild(currentText);
        mycurrent_row.appendChild(cell5);
        
        cell6=document.createElement("TD");
        var rem=document.createElement("input");
        rem.type="hidden";
        rem.name="remark";
        rem.value=remark;
        cell6.appendChild(rem);
        var currentText=document.createTextNode(remark);
        cell6.appendChild(currentText);
        mycurrent_row.appendChild(cell6);
        
         
        tbody.appendChild(mycurrent_row);
        i++;
   
        funclear();   
        document.frmDeptPanel.btsubmit.disabled=false
        }
    }
    else
    {
        alert("Given Employee Id is already added");
    }
} 



 function updaterow(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   if(flag=="success")
   {
        alert('Records Updated Successfully');
        var empid=document.frmDeptPanel.txtEmployeeid.value;
        var ename=document.frmDeptPanel.txtEmpName.value;
        var desig=document.frmDeptPanel.txtEmpDesig.value;  
        var posting=document.frmDeptPanel.txtCurOff.value;
        var remark=document.frmDeptPanel.txtrem.value;
        var tbody=document.getElementById("mytable"); 
        var r=document.getElementById(rid); 
        //alert(r);
        var rcells=r.cells;
        rcells.item(1).lastChild.nodeValue=empid;
        rcells.item(2).lastChild.nodeValue=ename;
        rcells.item(3).lastChild.nodeValue=desig;
        //alert(rcells.item(4).lastChild.nodeValue);
        rcells.item(4).lastChild.nodeValue=posting;
        //alert(rcells.item(5).lastChild.nodeValue);
        rcells.item(5).lastChild.nodeValue=remark;
        funclear();
    }
    else
    {
        alert("Records are not updated");
    }
}

function deleterow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        document.frmDeptPanel.btsubmit.disabled = false;
        var trow=vals;
        var tbody=document.getElementById("mytable"); 
        var r=document.getElementById(trow);    
        var ri=r.rowIndex;   
        tbody.deleteRow(ri);
        funclear();
        alert('Selected Rows are Deleted');
    }
    else
    {
        alert('Unable to Delete');
    }  
}