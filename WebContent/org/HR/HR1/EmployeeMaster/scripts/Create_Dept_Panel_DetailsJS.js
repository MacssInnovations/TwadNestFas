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
      var url="../../../../../Create_Dept_Panel_DetailsServ?command=getDet&order="+ord_id+"&offid="+off_id;
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
      var url="../../../../../Create_Dept_Panel_DetailsServ?command=employee&empid="+empid;
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
      else if(command=="employee")
      {      
         empDetails(baseResponse);
         //alert("1");
      }
    }
  }
}

function getDetails(res)
{
    var flag=res.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
       var detail=res.getElementsByTagName("details");//.firstChild.nodeValue;
        var empid=new Array();
        var empdet=new Array();
        var employee=document.getElementById("cmdempid");
        employee.innerHTML="";
        for(var i=0;i<detail.length;i++)
        {
            empid[i]=res.getElementsByTagName("employeeid")[i].firstChild.nodeValue;
            empdet[i]=res.getElementsByTagName("empdet")[i].firstChild.nodeValue;   
        }
        
        var option=document.createElement("OPTION");
        option.text="--Select Employee--";
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
        option.value=empid[i];
        
        option.text=empdet[i];
       
        try{
        employee.add(option);
        }catch(error)
        {
        employee.add(option,null);
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


function funadd()
{
     var c=checkNull();
   if(c)
   {      
        var eid=document.frmDeptPanel.txtEmployeeid.value;
        var ename=document.frmDeptPanel.txtEmpName.value;
        var desig=document.frmDeptPanel.txtEmpDesig.value;  
        var posting=document.frmDeptPanel.txtCurOff.value;
        var remark=document.frmDeptPanel.txtrem.value;
        
        vals = eid+"~"+ename+"~"+desig+"~"+posting;  
        var flag = true;
        //alert(flag)
        if(element.length > 0)
        {
            for(var l=0;l<element.length;l++)
            {
                 if(element[l] == vals)
                 {
                        alert('This Employee is Already Added in the Grid');
                        flag = false;
                         funclear();
                    }
                    
                }
        }
        if(flag == true)
        {
            element[j] = vals;
            j++;
       
            var tbody=document.getElementById("grid_body");
            var mycurrent_row=document.createElement("TR");
             var mycurrent_row=document.createElement("TR");
            
            mycurrent_row.id=vals;
            var cell=document.createElement("TD");
            var anc=document.createElement("A");
            var url="javascript:loadTable('"+vals+"')";
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
             empidd.id="eid";
            empidd.value=eid;
            cell2.appendChild(empidd);
            var currentText=document.createTextNode(eid);
            cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2); 
            
            cell3=document.createElement("TD");
            var empname=document.createElement("input");
            empname.type="hidden";
            empname.name="ename";
             empname.id="ename";
            empname.value=ename;
            cell3.appendChild(empname);
            var currentText=document.createTextNode(ename);
            cell3.appendChild(currentText);
            mycurrent_row.appendChild(cell3); 
            
            cell4=document.createElement("TD");
            var designame=document.createElement("input");
            designame.type="hidden";
            designame.name="desig";
             designame.id="desig";
            designame.value=desig;
            cell4.appendChild(designame);
            var currentText=document.createTextNode(desig);
            cell4.appendChild(currentText);
            mycurrent_row.appendChild(cell4); 
            
            cell5=document.createElement("TD");
            var curposting=document.createElement("input");
            curposting.type="hidden";
            curposting.name="posting";
             curposting.id="posting";
            curposting.value=posting;
            cell5.appendChild(curposting);
            var currentText=document.createTextNode(posting);
            cell5.appendChild(currentText);
            mycurrent_row.appendChild(cell5);
            
            cell6=document.createElement("TD");
            var rem=document.createElement("input");
            rem.type="hidden";
            rem.name="panrem";
             rem.id="panrem";
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


function loadTable(scod)
{
   
    
     var r=document.getElementById(scod);
    var rcells=r.cells;
    var tbody=document.getElementById("grid_body");
    var table=document.getElementById("mytable");
  
    document.frmDeptPanel.txtEmployeeid.value=rcells.item(1).lastChild.nodeValue;
    document.frmDeptPanel.txtEmpName.value=rcells.item(2).lastChild.nodeValue;
    document.frmDeptPanel.txtEmpDesig.value=rcells.item(3).lastChild.nodeValue;
    document.frmDeptPanel.txtCurOff.value=rcells.item(4).lastChild.nodeValue;
    document.frmDeptPanel.txtrem.value=rcells.item(5).lastChild.nodeValue;
    
    document.frmDeptPanel.btadd.disabled=true; 
    document.frmDeptPanel.btupdate.disabled=false;
    document.frmDeptPanel.btdelete.disabled=false;
}

function funupdate()
{
     
       var empid=document.frmDeptPanel.txtEmployeeid.value;
        var ename=document.frmDeptPanel.txtEmpName.value;
        var desig=document.frmDeptPanel.txtEmpDesig.value;  
        var posting=document.frmDeptPanel.txtCurOff.value;
        var remark=document.frmDeptPanel.txtrem.value;
        var r=document.getElementById(vals); 
        var rcells=r.cells;
        rcells.item(1).lastChild.nodeValue=empid;
        rcells.item(2).lastChild.nodeValue=ename;
        rcells.item(3).lastChild.nodeValue=desig;
        rcells.item(4).lastChild.nodeValue=posting;
        rcells.item(5).lastChild.nodeValue=remark;
        funclear();
        alert("Record UPdated");
}

function deleterow()
{
    document.frmDeptPanel.btsubmit.disabled = false;
    var trow=vals;
    var tbody=document.getElementById("mytable"); 
    var r=document.getElementById(trow);    
    var ri=r.rowIndex;   
    tbody.deleteRow(ri);
     funclear();
     alert("Record Deleted");
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
    var url="../../../../../Create_Dept_Panel_DetailsServ?command=submit";  
    var empid="";
    var remark="";  
    var remarks="";
     //alert(document.frmDeptPanel.eid.length);
    if(document.frmDeptPanel.eid.length>1)
    {
        for(i=0;i<document.frmDeptPanel.eid.length;i++)
        {
             empid=empid+document.frmDeptPanel.eid[i].value+"~";
            // alert(empid);
             remark=remark+document.frmDeptPanel.panrem[i].value+"~";
             // alert(remark);
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
        remark=document.frmDeptPanel.panrem.value;
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
