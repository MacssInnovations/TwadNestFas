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
   // alert("command"+command);
    if(command=="deptoff")
    {
        var otherdept=document.getElementById("cmb_Deptid").value;
       // alert("otherdept"+otherdept);
        var url="../../../../../Create_Dept_Willingness_DetailsServ?command=deptoff&txtdeptid="+otherdept;
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
   if(command=="loademp")
   {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        var ofid=document.frmDeptWilling.txtOffId.value;
        var url="../../../../../Create_Dept_Willingness_DetailsServ?command=loademp&txtEmployeeid="+eid+"&offid="+ofid;
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
           
      if(command=="deptoff")
      {      
         otherDeptOff(baseResponse);
      }
      if(command=="loademp")
      {      
         loadEmployee(baseResponse);
      }
    }
  }
}
 
 
function otherDeptOff(res)
{
    var flag=res.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert(flag);
    if(flag=="success")
    {
        var other_dept_id=res.getElementsByTagName("other_dept_id");//.firstChild.nodeValue;
        var dept_id=new Array();
        var dept_office_id=new Array();
        var dept_office_name=new Array();
        var deptid=document.getElementById("cmb_DeptOffice");
        deptid.innerHTML="";
        for(var i=0;i<other_dept_id.length;i++)
        {
            dept_id[i]=res.getElementsByTagName("other_dept_id")[i].firstChild.nodeValue;
            dept_office_id[i]=res.getElementsByTagName("other_dept_off_id")[i].firstChild.nodeValue;
            dept_office_name[i]=res.getElementsByTagName("other_dept_off_name")[i].firstChild.nodeValue;
        }
        
        var option=document.createElement("OPTION");
        option.text="--Select Department Office--";
        option.value="";
        
        try
        {
        deptid.add(option);
        }
        catch(errorObject)
        {
        deptid.add(option,null);
        }
        for(var i=0;i<other_dept_id.length;i++)
        {
        var option=document.createElement("OPTION");
        option.value=dept_office_id[i];
        
        option.text=dept_office_name[i];
        
        try{
        deptid.add(option);
        }catch(error)
        {
        deptid.add(option,null);
        }
        }
    }    
    else if(flag=="failure")
    {
     alert("No data found");
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
        var des_id=baseResponse.getElementsByTagName("desig_id")[0].firstChild.nodeValue;
        
        maxfromdate=baseResponse.getElementsByTagName("maxfromdate")[0].firstChild.nodeValue;
       
        maxsession=baseResponse.getElementsByTagName("maxsession")[0].firstChild.nodeValue;
       

        //document.frmDeptWilling.birthDate.value=dob;
        document.frmDeptWilling.txtEmployeeid.value=eid;
        document.frmDeptWilling.txtEmpName.value=ename;
        document.frmDeptWilling.txtEmpDesig.value=desig;  
        document.frmDeptWilling.txtCurOff.value=posting;
        document.frmDeptWilling.txt_desid.value=des_id;
    }
    
    
    else if(flag=="failure1")
    {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' doesn't have a post.");
        document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
    }
    else if(flag=="failure2")
    {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' alread has  a unfrezeed Willingness record.");
        document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
    }
    else if(flag=="failure3")
    {
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        alert("Employee Id '"+eid+"' is not in working status. So can not create Deputation Willingness.");
        document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
    }
     else if(flag=="failurea")
    {
           var eid=document.frmDeptWilling.txtEmployeeid.value;
          // alert("Can not Create Relieval. Because Employee Id "+eid+" is not under your Office!");
          alert("SR controling office for this employee is different from your office!");
           document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
            
    }
     else if(flag=="failureb")
    {
           var eid=document.frmDeptWilling.txtEmployeeid.value;
            alert("You have no Current Posting. Can not Create Deputation Willingness for "+eid+"!");
            document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
            
    }
     else if(flag=="failurec")
    {
            var eid=document.frmDeptWilling.txtEmployeeid.value;
            alert("Given Employee Id " +eid+" has no SR control Office. Can not Create Deputation Willingness!");
            document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
            
    }
     else if(flag=="failured")
    {
            var eid=document.frmDeptWilling.txtEmployeeid.value;
            alert("Can not Create Relieval. Access Denined!");
            document.frmDeptWilling.txtEmployeeid.value="";
        document.frmDeptWilling.txtEmployeeid.focus();
        document.frmDeptWilling.txtEmpName.value="";
        document.frmDeptWilling.txtEmpDesig.value="";
           
    }
    else 
    {
    var eid=document.frmDeptWilling.txtEmployeeid.value;
    alert("Employee Id '"+eid+"' doesn't Exists");
    document.frmDeptWilling.txtEmployeeid.value="";
    document.frmDeptWilling.txtEmployeeid.focus();
    document.frmDeptWilling.txtEmpName.value="";
    document.frmDeptWilling.txtEmpDesig.value="";
    }
}

var winemp;
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

function doParentEmp(emp)
{
document.frmDeptWilling.txtEmployeeid.value=emp;
doFunction('loademp','null');
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

function funadd()
{
   var c=checkNull();
   if(c)
   {      
        var eid=document.frmDeptWilling.txtEmployeeid.value;
        var ename=document.frmDeptWilling.txtEmpName.value;
        var desig=document.frmDeptWilling.txtEmpDesig.value;  
        var posting=document.frmDeptWilling.txtCurOff.value;
        var willingdate=document.frmDeptWilling.txtPDat.value;
        
        vals = eid+"~"+ename+"~"+desig+"~"+posting+"~"+willingdate;  
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
            var willdat=document.createElement("input");
            willdat.type="hidden";
            willdat.name="willingdate";
            willdat.id="willingdate";
            willdat.value=willingdate;
            cell6.appendChild(willdat);
            var currentText=document.createTextNode(willingdate);
            cell6.appendChild(currentText);
            mycurrent_row.appendChild(cell6);
            
             
            tbody.appendChild(mycurrent_row);
            i++;
           /* document.frmDeptWilling.txtRno.disabled=true;
            document.frmDeptWilling.txtdptdate.disabled=true;
            document.frmDeptWilling.cmb_Deptid.disabled=true;
            document.frmDeptWilling.cmb_DeptOffice.disabled=true;
            document.frmDeptWilling.txtpostname.disabled=true;
            document.frmDeptWilling.txtpayscale.disabled=true;
            document.frmDeptWilling.txtremark.disabled=true;*/
            
            funclear();   
            document.frmDeptWilling.btsubmit.disabled=false
            }
        }
}

function funclear()
{
    document.frmDeptWilling.txtEmployeeid.value="";
    document.frmDeptWilling.txtEmpName.value="";
    document.frmDeptWilling.txtEmpDesig.value="";  
    document.frmDeptWilling.txtCurOff.value="";
    document.frmDeptWilling.txt_desid.value="";
    document.frmDeptWilling.txtPDat.value="";
     document.frmDeptWilling.btupdate.disabled=true;
    document.frmDeptWilling.btdelete.disabled=true;
    document.frmDeptWilling.btadd.disabled=false; 
}


function loadTable(scod)
{
   
    
     var r=document.getElementById(scod);
    var rcells=r.cells;
    var tbody=document.getElementById("grid_body");
    var table=document.getElementById("mytable");
  
    document.frmDeptWilling.txtEmployeeid.value=rcells.item(1).lastChild.nodeValue;
    document.frmDeptWilling.txtEmpName.value=rcells.item(2).lastChild.nodeValue;
    document.frmDeptWilling.txtEmpDesig.value=rcells.item(3).lastChild.nodeValue;
    document.frmDeptWilling.txtCurOff.value=rcells.item(4).lastChild.nodeValue;
    document.frmDeptWilling.txtPDat.value=rcells.item(5).lastChild.nodeValue;
    
    document.frmDeptWilling.btupdate.disabled=false;
    document.frmDeptWilling.btdelete.disabled=false;
    document.frmDeptWilling.btadd.disabled=true; 
}

function funupdate()
{
    
       var empid=document.frmDeptWilling.txtEmployeeid.value;
        var ename=document.frmDeptWilling.txtEmpName.value;
        var desig=document.frmDeptWilling.txtEmpDesig.value;  
        var posting=document.frmDeptWilling.txtCurOff.value;
        var willingdate=document.frmDeptWilling.txtPDat.value;
        var r=document.getElementById(vals); 
        var rcells=r.cells;
        rcells.item(1).lastChild.nodeValue=empid;
        rcells.item(2).lastChild.nodeValue=ename;
        rcells.item(3).lastChild.nodeValue=desig;
        rcells.item(4).lastChild.nodeValue=posting;
        rcells.item(5).lastChild.nodeValue=willingdate;
        funclear();
        alert("Record UPdated");
    
}

function deleterow()
{
    document.frmDeptWilling.btsubmit.disabled = false;
    var trow=vals;
    var tbody=document.getElementById("mytable"); 
    var r=document.getElementById(trow);    
    var ri=r.rowIndex;   
    tbody.deleteRow(ri);
     funclear();
     alert("Record Deleted");
}

function fnsubmit()
{
    var url="../../../../../Create_Dept_Willingness_DetailsServ?command=submit";  
    var empid="";
    var willingdate="";
    //alert(document.frmDeptWilling.eid.length);
    if(document.frmDeptWilling.eid.length>0)
    {        
        for(i=0;i<document.frmDeptWilling.eid.length;i++)
        {
             empid=empid+document.frmDeptWilling.eid[i].value+"~";
            // alert(empid);
             willingdate=willingdate+document.frmDeptWilling.willingdate[i].value+"~";
            // alert(willingdate);
        }
        if(empid!="")
        {
             empid=empid.substring(0,empid.lastIndexOf("~"));
             url=url+"&empid="+empid; 
        }
        if(willingdate!="")
        {
             willingdate=willingdate.substring(0,willingdate.lastIndexOf("~"));
             url=url+"&willingdate="+willingdate; 
        }
        
        var offid=document.frmDeptWilling.txtOffId.value;
        url=url+"&offid="+offid; 
        var ltrrefno=document.frmDeptWilling.txtRno.value;
        url=url+"&ltrrefno="+ltrrefno; 
        var ltrrefdat=document.frmDeptWilling.txtdptdate.value;
        url=url+"&ltrrefdat="+ltrrefdat; 
        var deptid=document.getElementById("cmb_Deptid").value;
        url=url+"&deptid="+deptid; 
        var deptoff=document.getElementById("cmb_DeptOffice").value;
        url=url+"&deptoff="+deptoff; 
        var postname=document.frmDeptWilling.txtpostname.value;
        url=url+"&postname="+postname; 
        var payscale=document.frmDeptWilling.txtpayscale.value;
        url=url+"&payscale="+payscale; 
        var rem=document.frmDeptWilling.txtremark.value;
        var remark="";
        if(rem=="")
        {
            remark="";
        }
        else
        {
            remark=rem;
        }
        url=url+"&remark="+remark; 
    }
    else
    {
        empid=document.frmDeptWilling.eid.value;
        url=url+"&empid="+empid; 
        willingdate=document.frmDeptWilling.willingdate.value;
        url=url+"&willingdate="+willingdate; 
        
        var offid=document.frmDeptWilling.txtOffId.value;
        url=url+"&offid="+offid; 
        var ltrrefno=document.frmDeptWilling.txtRno.value;
        url=url+"&ltrrefno="+ltrrefno; 
        var ltrrefdat=document.frmDeptWilling.txtdptdate.value;
        url=url+"&ltrrefdat="+ltrrefdat; 
        var deptid=document.getElementById("cmb_Deptid").value;
        url=url+"&deptid="+deptid; 
        var deptoff=document.getElementById("cmb_DeptOffice").value;
        url=url+"&deptoff="+deptoff; 
        var postname=document.frmDeptWilling.txtpostname.value;
        url=url+"&postname="+postname; 
        var payscale=document.frmDeptWilling.txtpayscale.value;
        url=url+"&payscale="+payscale; 
        var rem=document.frmDeptWilling.txtremark.value;
        var remark="";
        if(rem=="")
        {
            remark="";
        }
        else
        {
            remark=rem;
        }
        url=url+"&remark="+remark; 
    }
    //alert("The url is"+url);
    document.frmDeptWilling.action=url;
    document.frmDeptWilling.method="post"; 
    document.frmDeptWilling.submit(); 
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
  
function checkNull()
{
  if((document.frmDeptWilling.txtRno.value=="") || (document.frmDeptWilling.txtRno.value.length==0))
  {
    alert('Please Enter Deputation Reference Letter Number.');
    return false;
  }
  
  if((document.frmDeptWilling.txtdptdate.value=="") || (document.frmDeptWilling.txtdptdate.value.length==0))
  {
   alert('Please Enter Deputation Reference Letter Date.');
    return false;
  }
  
  var deptid=document.frmDeptWilling.cmb_Deptid.options[document.frmDeptWilling.cmb_Deptid.selectedIndex].value;
  
  if(deptid==0)
  {
     alert('Please Select Deputation Department.');
    return false;
  }
  
  var deptoffid=document.frmDeptWilling.cmb_DeptOffice.options[document.frmDeptWilling.cmb_DeptOffice.selectedIndex].value;
  
  if(deptoffid==0)
  {
     alert('Please Select Deputation Department Office .');
    return false;
  }
   if((document.frmDeptWilling.txtpostname.value=="") || (document.frmDeptWilling.txtpostname.value.length==0))
  {
   alert('Please Enter The Post Name.');
    return false;
  }
   if((document.frmDeptWilling.txtpayscale.value=="") || (document.frmDeptWilling.txtpayscale.value.length==0))
  {
   alert('Please Enter The Pay Scale.');
    return false;
  }
  
  /* if((document.frmDeptWilling.txtremark.value=="") || (document.frmDeptWilling.txtremark.value.length==0))
  {
   alert('Please Enter The Remarks.');
    return false;
  }*/
   if((document.frmDeptWilling.txtEmployeeid.value=="") || (document.frmDeptWilling.txtEmployeeid.value.length==0))
  {
   alert('Please Select Employee Id.');
    return false;
  }
   if((document.frmDeptWilling.txtPDat.value=="") || (document.frmDeptWilling.txtPDat.value.length==0))
  {
   alert('Please Select Willingness Date.');
    return false;
  }
  return true;
}

 