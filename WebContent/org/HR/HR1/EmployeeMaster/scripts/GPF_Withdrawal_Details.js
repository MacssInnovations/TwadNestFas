
var seq=0;var common="",mn,yr,v;
var winemp;
var my_window;


function togetFocus()
{
   document.frmEmployeeProfile.Employee_Initial.focus();
}
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
   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee_Search_for_Emp_Editing","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.getElementById("txtEmployeeid").value=emp;
 callfun('emp');

}

function Exit()
{
    window.close();
}
function displayMonth()
{
       
         v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();
      
                        /*--- Account Month--*/
	
        
        var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
        document.getElementById("ac_year").options[1].selected='selected';
    /*     var year1=document.getElementById("ac_year").value;
    
    if(year1==yr)
    {  */
        
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("ac_month").options[i].disabled=true;
          
       }
   
        
        document.getElementById("rel_year").options[1].selected='selected';
     /*    var year2=document.getElementById("rel_year").value;
        
    
    if(year2==yr)
    {
        */
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("rel_month").options[i].disabled=true;
          
       }
        document.getElementById("ac_month").options[mn].selected='selected';
        document.getElementById("rel_month").options[mn].selected='selected';
        
        
                       /*--- Relative Month--*/
      
       /*  document.Hrm_GPFForm.txtEmployeeid.value="";
        
        document.Hrm_GPFForm.comEmpId.value="";
        document.Hrm_GPFForm.designation.value="";
        document.Hrm_GPFForm.txtDOB.value="";
        document.Hrm_GPFForm.txtGpfNo.value="";
        document.Hrm_GPFForm.unit_name.focus();
       
        document.Hrm_GPFForm.add.disabled=false;
        document.Hrm_GPFForm.update.disabled=true;
        document.Hrm_GPFForm.delete1.disabled=true;*/
	
}
 function loadvalue(empid)
    {      
          common=empid;
          var emp_id=document.getElementById(empid); 
          var rcells=emp_id.cells;
          //document.Hrm_GPFForm.txtEmployeeid.value=rcells.item(0).lastChild.nodeValue;
          var ac_month_year=rcells.item(1).lastChild.nodeValue.split("-");
          var rel_month_year=rcells.item(2).lastChild.nodeValue.split("-");
          var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
           var j,k;
           for(var i=0;i<12;i++)
          {
            if(ac_month_year[0]==month[i])
            {
               j=i;
            }
            if(rel_month_year[0]==month[i])
            {
                k=i;
            }
          }
          document.Hrm_GPFForm.ac_month[j].selected='selected';
          document.Hrm_GPFForm.ac_month.disabled=true;
           if(yr==rel_month_year[1])
         {
            
          for(var i=(mn+1);i<12;i++)
            {    
            
           document.getElementById("rel_month").options[i].disabled=true;
          
            }
       document.getElementById("rel_month").options[k].selected='selected';
       }
          else
          {
           for(var i=0;i<12;i++)
            {    
    
           document.getElementById("rel_month").options[i].disabled=false;
          
            }
          document.Hrm_GPFForm.rel_month[k].selected='selected';
          }
          
          
          //document.getElementById("ac_month").options[document.getElementById("ac_month").selectedIndex].text=rcells.item(2).firstChild.nodeValue;
          //var acyear=rcells.item(2).lastChild.value;
          document.getElementById("ac_year").value=ac_month_year[1];
          document.Hrm_GPFForm.ac_year.disabled=true;
          //document.getElementById("rel_month").options[document.getElementById("rel_month").selectedIndex].text=rcells.item(3).firstChild.nodeValue;
          
          document.getElementById("rel_year").value=rel_month_year[1];
          
          var type_withdraw=rcells.item(3).lastChild.value;
          
         // alert("type_withdraw"+type_withdraw);
          document.Hrm_GPFForm.type_withdraw.value=type_withdraw;
          var remark=rcells.item(4).lastChild.value;
          if(remark=="noRemarks")
          {
             remark='';
          }
          document.getElementById("remarks").value=remark;
          
          document.Hrm_GPFForm.amount.value=rcells.item(4).firstChild.nodeValue;
          document.Hrm_GPFForm.date.value=rcells.item(5).firstChild.value;
          
          
        
          document.Hrm_GPFForm.add.disabled=true;
        document.Hrm_GPFForm.update.disabled=false;
        document.Hrm_GPFForm.delete1.disabled=false;
        
          
      
    }
 //Ajax Object Creation 
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

// According Process chanage -grid genaration
function stateChanged(req)
{
    var flag,command,response;
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    if( req.readyState==4)
    {
       if( req.status==200)
       {
            response= req.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="emp")
            {
                if(flag=='success')
                {
                      var tlist=document.getElementById("tlist");
                    var empname=response.getElementsByTagName("emp_name")[0].firstChild.nodeValue;
                      var d_id=response.getElementsByTagName("designation")[0].firstChild.nodeValue;
                       var dob=response.getElementsByTagName("date_of_birth")[0].firstChild.nodeValue;
                        var gpfno=response.getElementsByTagName("gpf_no")[0].firstChild.nodeValue;
                    document.getElementById("comEmpId").value=empname;
                    document.getElementById("txtDOB").value=dob;
                    document.getElementById("txtGpfNo").value=gpfno;
                    document.getElementById("designation").value=d_id;
                   
                    var len=response.getElementsByTagName("emp_id").length;
                  
                    for(var i=0;i<len;i++)
                    {
                                      
                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var type_withdraw=response.getElementsByTagName("type_withdraw")[i].firstChild.nodeValue;
                  
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[i].firstChild.nodeValue;
                    
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                      if(remark=='noRemarks')
                              remark='';
                    var with_desc=response.getElementsByTagName("with_desc")[i].firstChild.nodeValue;
                    var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                   
                    tr.appendChild(td);
                    
                    /* var td1=document.createElement("TD");
                    var empid=document.createTextNode(emp_id);
                    td1.appendChild(empid);
                    tr.appendChild(td1);*/     
                                        
                    
                    var td2=document.createElement("TD");
                    var h1=document.createElement("TEXT");
                    h1.type="hidden";
                    h1.name="ac_month";
                    h1.value=ac_month;
                    td2.appendChild(h1);
                    var h5=document.createElement("TEXT");
                    h5.type="hidden";
                    h5.name="ac_year";
                    h5.value=acyear;
                    td2.appendChild(h5);
                    var h2=document.createTextNode(ac_month+"-"+acyear);
                    td2.appendChild(h2);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var h3=document.createElement("TEXT");
                    h3.type="hidden";
                    h3.name="rel_month";
                    h3.value=rel_month;
                    td3.appendChild(h3);
                    
                    var h6=document.createElement("TEXT");
                    h6.type="hidden";
                    h6.name="rel_year";
                    h6.value=relyear;
                    td3.appendChild(h6);
                    var h4=document.createTextNode(rel_month+"-"+relyear);
                    td3.appendChild(h4);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(with_desc);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="type_withdraw";
                    h7.value=type_withdraw;
                    td4.appendChild(h7);
                    tr.appendChild(td4);
                    
                    var td5=document.createElement("TD");
                    var amt=document.createTextNode(amount);
                    td5.appendChild(amt);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td5.appendChild(h7);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="date_trans";
                    h8.value=date_trans;
                    td6.appendChild(h8);
                    var trans=new Array();
                    trans=date_trans.split("/");
                    var day=trans[0];
                    var mon=trans[1];
                    var yer=trans[2];
                     var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                    mon=month[eval(mon)-1];
                   var h9=document.createTextNode(day+"-"+mon+"-"+yer);
                   td6.appendChild(h9);
                    tr.appendChild(td6);
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    
                }
                else
                    {
                        document.Hrm_GPFForm.txtEmployeeid.value="";
                      document.getElementById("comEmpId").value="";
                    document.getElementById("txtDOB").value="";
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("designation").value="";
                    document.Hrm_GPFForm.txtEmployeeid.focus();
                    alert("This Employee ID not in Data Base");
                    }
            }
           else if(command=="Add")
            {
                if(flag=='success')
                {
                      var tlist=document.getElementById("tlist");
                   var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                   var ac_month=response.getElementsByTagName("ac_month")[0].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[0].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[0].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[0].firstChild.nodeValue;
                    var type_withdraw=response.getElementsByTagName("type_trans")[0].firstChild.nodeValue;
                  
                    var amount=response.getElementsByTagName("amount")[0].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[0].firstChild.nodeValue;
                    
                    var remark=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    
                    var with_desc=response.getElementsByTagName("with_desc")[0].firstChild.nodeValue;
                    var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    var h5=document.createElement("TEXT");
                    h5.type="hidden";
                    h5.name="emp_id";
                    h5.value=emp_id;
                    td.appendChild(h5);
                    tr.appendChild(td);
                    
                    /* var td1=document.createElement("TD");
                    var empid=document.createTextNode(emp_id);
                    td1.appendChild(empid);
                    tr.appendChild(td1);*/     
                                        
                    
                    var td2=document.createElement("TD");
                    var h1=document.createElement("TEXT");
                    h1.type="hidden";
                    h1.name="ac_month";
                    h1.value=ac_month;
                    td2.appendChild(h1);
                    var h5=document.createElement("TEXT");
                    h5.type="hidden";
                    h5.name="ac_year";
                    h5.value=acyear;
                    td2.appendChild(h5);
                    var h2=document.createTextNode(ac_month+"-"+acyear);
                    td2.appendChild(h2);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var h3=document.createElement("TEXT");
                    h3.type="hidden";
                    h3.name="rel_month";
                    h3.value=rel_month;
                    td3.appendChild(h3);
                    var h6=document.createElement("TEXT");
                    h6.type="hidden";
                    h6.name="rel_year";
                    h6.value=relyear;
                    td3.appendChild(h6);
                    var h4=document.createTextNode(rel_month+"-"+relyear);
                    td3.appendChild(h4);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(with_desc);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="type_withdraw";
                    h7.value=type_withdraw;
                    td4.appendChild(h7);
                    tr.appendChild(td4);
                    
                    var td5=document.createElement("TD");
                    var amt=document.createTextNode(amount);
                    td5.appendChild(amt);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td5.appendChild(h7);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="date_trans";
                    h8.value=date_trans;
                    td6.appendChild(h8);
                    var trans=new Array();
                    trans=date_trans.split("/");
                    var day=trans[0];
                    var mon=trans[1];
                    var yer=trans[2];
                     var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                    mon=month[eval(mon)-1];
                   var h9=document.createTextNode(day+"-"+mon+"-"+yer);
                   td6.appendChild(h9);
                    tr.appendChild(td6);
                    
                    tlist.appendChild(tr);             
                    seq++;
 
                   
                     document.Hrm_GPFForm.update.disabled=true;
                     document.Hrm_GPFForm.delete1.disabled=true;
                     alert("Added Successfully");
                     clearField();
                    
                }
                else
                    alert("Same data wont be allowed");
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {
                 
                    var tlist=document.getElementById("tlist");
                    var ac_month=response.getElementsByTagName("ac_month")[0].firstChild.nodeValue;
                    var emp_id=response.getElementsByTagName("emp_id")[0].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[0].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[0].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[0].firstChild.nodeValue;
                    var type_withdraw=response.getElementsByTagName("type_withdraw")[0].firstChild.nodeValue;
                  
                    var amount=response.getElementsByTagName("amount")[0].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[0].firstChild.nodeValue;
                      
                    var remark=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    var with_desc=response.getElementsByTagName("with_desc")[0].firstChild.nodeValue;
                   //alert(with_desc);
                
                    
                  var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                    cells.item(1).lastChild.nodeValue=ac_month+"-"+acyear;
                    //cells.item(1).firstChild.value=ac_month;
                    //cells.item(1).lastChild.value=acyear;
                    cells.item(2).lastChild.nodeValue=rel_month+"-"+relyear;
                    //cells.item(2).firstChild.value=rel_month;
                    //cells.item(3).lastChild.value=relyear;
                    
                    cells.item(3).firstChild.nodeValue=with_desc;
                                      
                    cells.item(3).lastChild.value=type_withdraw;
                    
                    cells.item(4).firstChild.nodeValue=amount;
                     cells.item(4).lastChild.value=remark;
                    cells.item(5).firstChild.value=date_trans;
                    var trans=new Array();
                    trans=date_trans.split("/");
                    var day=trans[0];
                   
                    var mon=trans[1];
                    var yer=trans[2];
                     var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                    mon=month[eval(mon)-1];
                    cells.item(5).lastChild.nodeValue=day+"-"+mon+"-"+yer;;
                   
                   
                    alert("Updated Successfully");
                    clearField();
                    /* document.Hrm_GPFForm.add.disabled=true;
                    document.Hrm_GPFForm.update.disabled=true;
                    document.Hrm_GPFForm.delete1.disabled=true;*/
                   
                                       
                }
                else
                    alert("Failure in Update");
            }
          else if(command=="Delete")
            {
                if(flag=='success')
                {   
                
                
                  
                         var tlist=document.getElementById("tlist");
                        try{
                        //tlist.innerText="";
                                 tlist.innerHTML="";
                        
                        }
                        catch(e)
                        {
                                 tlist.innerText="";
                        //tlist.innerHTML="";
                        
                        } 
                    var len=response.getElementsByTagName("emp_id").length;
                    
                    for(var i=0;i<len;i++)
                    {
                    var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var type_withdraw=response.getElementsByTagName("type_withdraw")[i].firstChild.nodeValue;
                  
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var date_trans=response.getElementsByTagName("date_trans")[i].firstChild.nodeValue;
                    
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                                        

                    var with_desc=response.getElementsByTagName("with_desc")[i].firstChild.nodeValue;
                    
                    var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    var h5=document.createElement("TEXT");
                    h5.type="hidden";
                    h5.name="emp_id";
                    h5.value=emp_id;
                    td.appendChild(h5);
                    tr.appendChild(td);
                    
                    /* var td1=document.createElement("TD");
                    var empid=document.createTextNode(emp_id);
                    td1.appendChild(empid);
                    tr.appendChild(td1);*/     
                                    
                    
                    var td2=document.createElement("TD");
                    var h1=document.createElement("TEXT");
                    h1.type="hidden";
                    h1.name="ac_month";
                    h1.value=ac_month;
                    td2.appendChild(h1);
                    var h5=document.createElement("TEXT");
                    h5.type="hidden";
                    h5.name="ac_year";
                    h5.value=acyear;
                    td2.appendChild(h5);
                    var h2=document.createTextNode(ac_month+"-"+acyear);
                    td2.appendChild(h2);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var h3=document.createElement("TEXT");
                    h3.type="hidden";
                    h3.name="rel_month";
                    h3.value=rel_month;
                    td3.appendChild(h3);
                    var h6=document.createElement("TEXT");
                    h6.type="hidden";
                    h6.name="rel_year";
                    h6.value=relyear;
                    td3.appendChild(h6);
                    var h4=document.createTextNode(rel_month+"-"+relyear);
                    td3.appendChild(h4);
                    tr.appendChild(td3);
                     
                    var td4=document.createElement("TD");
                    var typetrans=document.createTextNode(with_desc);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="type_withdraw";
                    h7.value=type_withdraw;
                    td4.appendChild(h7);
                    tr.appendChild(td4);
                    
                    var td5=document.createElement("TD");
                    var amt=document.createTextNode(amount);
                    td5.appendChild(amt);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td5.appendChild(h7);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="date_trans";
                    h8.value=date_trans;
                    td6.appendChild(h8);
                    var trans=new Array();
                    trans=date_trans.split("/");
                    var day=trans[0];
                    var mon=trans[1];
                    var yer=trans[2];
                     var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
                    mon=month[eval(mon)-1];
                   var h9=document.createTextNode(day+"-"+mon+"-"+yer);
                   td6.appendChild(h9);
                    tr.appendChild(td6);
                    tlist.appendChild(tr);             
                    seq++;
                    }
                   
                    
                    
                  /*  var tlist=document.getElementById("tlist");
                   
                    alert(common);
                    if(common==0)
                    {
                    var field=document.getElementById(common);
                    }
                    else
                    {
                    var field=document.getElementById(common-1);
                    }
                    var index=field.rowIndex;
                    tlist.deleteRow(index);         */
                    alert("Deleted Successfully");
                    clearField();
                    /*document.Hrm_GPFForm.update.disabled=true;
                    document.Hrm_GPFForm.delete1.disabled=true;    */   
                    
                }
                else
                {
                    alert("Not success in Delete");
                    }
            
        }
       }
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
         if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
// clearing the ctrls
function clearGPF()
{
          
        document.Hrm_GPFForm.ac_month.disabled=false;
        document.Hrm_GPFForm.ac_year.disabled=false;
       
        displayMonth();
        document.Hrm_GPFForm.txtEmployeeid.value="";
        
        document.Hrm_GPFForm.comEmpId.value="";
        document.Hrm_GPFForm.designation.value="";
        document.Hrm_GPFForm.txtDOB.value="";
        document.Hrm_GPFForm.txtGpfNo.value="";
        document.Hrm_GPFForm.unit_name.focus();
     
        document.getElementById("ac_month").selectedIndex=0;
        document.getElementById("ac_year").selectedIndex=1;
        document.getElementById("rel_month").selectedIndex=0;
        document.getElementById("rel_year").selectedIndex=1;
         document.getElementById("type_withdraw").selectedIndex=0;
        document.getElementById("amount").value='';
        document.getElementById("date").value='';
        document.getElementById("remarks").value='';
                
       // type_withdraw=document.getElementById("type_withdraw").value;
        var tlist=document.getElementById("tlist");
        try{
                //tlist.innerText="";
                tlist.innerHTML="";
            
       }
    catch(e)
       {
                tlist.innerText="";
                //tlist.innerHTML="";
               
       }
       
       
   
    
         document.Hrm_GPFForm.add.disabled=false;
        document.Hrm_GPFForm.update.disabled=true;
        document.Hrm_GPFForm.delete1.disabled=true;
        
}
function clearField()
{
          
        document.Hrm_GPFForm.ac_month.disabled=false;
        document.Hrm_GPFForm.ac_year.disabled=false;
       
        displayMonth();
          
        document.getElementById("ac_month").selectedIndex=0;
        document.getElementById("ac_year").selectedIndex=1;
        document.getElementById("rel_month").selectedIndex=0;
        document.getElementById("rel_year").selectedIndex=1;
         document.getElementById("type_withdraw").selectedIndex=0;
        document.getElementById("amount").value='';
        document.getElementById("date").value='';
        document.getElementById("remarks").value='';
        document.Hrm_GPFForm.add.disabled=false;
        document.Hrm_GPFForm.update.disabled=true;
        document.Hrm_GPFForm.delete1.disabled=true;
        
}
// Servlet Calls
function callfun(command)
{
 var req=getTransport();
if(req==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
    
     
        var office_id;
        var Acc_unit_id;
        var ac_month;
        var rel_month;
        var ac_year;
        var rel_year;
        var type_withdraw;
        var emp_id;
        var amount;
        var date_trans;
        var remarks;
        
        var url="";
        
        if(command=="Add")
        { 
              if(nullCheck())
              {

                office_id=document.getElementById("txtOffId").value;
                Acc_unit_id=document.getElementById("Acc_unit_code").value;
                emp_id=document.getElementById("txtEmployeeid").value;
                ac_month=document.getElementById("ac_month").value;
                ac_year=document.getElementById("ac_year").value;
                rel_month=document.getElementById("rel_month").value;
                rel_year=document.getElementById("rel_year").value;
                amount=document.getElementById("amount").value;
                date_trans=document.getElementById("date").value;
                remarks=document.getElementById("remarks").value;
                if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                     {
                        remarks="noRemarks";
                     }
                
                       
                type_withdraw=document.getElementById("type_withdraw").value;
                var with_desc=document.Hrm_GPFForm.type_withdraw.options[document.Hrm_GPFForm.type_withdraw.selectedIndex].text;
                //alert(with_desc);
              
             
        url="../../../../../GPF_Withdrawal_Details.av?command=Add&office_id="+office_id+"&Acc_unit_id="+Acc_unit_id+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_withdraw="+type_withdraw+"&amount="+amount+"&date_trans="+date_trans+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year+"&with_desc="+with_desc;
        url=url+"&sid="+Math.random();//for Security add some randaom value in to url
         req.open("GET",url,true);
         req.onreadystatechange=function()
        {
           stateChanged(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();     
         }
    }
    else if(command=="Update")
    {
              if(nullCheck())
                {
                office_id=document.getElementById("txtOffId").value;
                Acc_unit_id=document.getElementById("Acc_unit_code").value;
                emp_id=document.getElementById("txtEmployeeid").value;
                ac_month=document.getElementById("ac_month").value;
                ac_year=document.getElementById("ac_year").value;
                rel_month=document.getElementById("rel_month").value;
                rel_year=document.getElementById("rel_year").value;
                amount=document.getElementById("amount").value;
                date_trans=document.getElementById("date").value;
                remarks=document.getElementById("remarks").value;
                if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                     {
                        remarks="noRemarks";
                     }
                
                var  type_withdraw=document.Hrm_GPFForm.type_withdraw.options[document.Hrm_GPFForm.type_withdraw.selectedIndex].value;
               // alert(type_withdraw);
                var with_desc=document.Hrm_GPFForm.type_withdraw.options[document.Hrm_GPFForm.type_withdraw.selectedIndex].text;
               // alert(with_desc);
               // type_withdraw=document.Hrm_GPFForm.type_withdraw.value;
        url="../../../../../GPF_Withdrawal_Details.av?command=Update&office_id="+office_id+"&Acc_unit_id="+Acc_unit_id+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_withdraw="+type_withdraw+"&amount="+amount+"&date_trans="+date_trans+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year+"&with_desc="+with_desc;
        url=url+"&sid="+Math.random();
         req.open("GET",url,true);
          req.onreadystatechange=function()
        {
           stateChanged(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
    }
    else if(command=="Delete")
    {
            
                emp_id=document.getElementById("txtEmployeeid").value;
                ac_month=document.getElementById("ac_month").value;
                ac_year=document.getElementById("ac_year").value;
          
                 type_withdraw=document.getElementById("type_withdraw").value;  
        url="../../../../../GPF_Withdrawal_Details.av?command=Delete&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
        url=url+"&sid="+Math.random();//for Security add some randaom value in to url
         req.open("GET",url,true);
           req.onreadystatechange=function()
        {
           stateChanged(req);
        }
       
         req.send(null);      
    }
   else if(command=="UnitName")
    {
           var unit_id=document.getElementById("unit_name").value;
            //alert("unit_id"+unit_id);
           document.getElementById("Acc_unit_code").value=unit_id;
    /*    url="../../../../../GPF_Withdrawal_Details.av?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
         req.open("GET",url,true);
         req.onreadystatechange=stateChanged;
         req.send(null);   */
    } 
    else if(command=="emp")
    {   
        
        if((document.Hrm_GPFForm.txtEmployeeid.value=="")||(document.Hrm_GPFForm.txtEmployeeid.value.length==0))
            {
                alert("Null Value not accepted...Select Employee ID");
                document.Hrm_GPFForm.txtEmployeeid.focus();
                return false;
            }
        var emp_id=document.Hrm_GPFForm.txtEmployeeid.value;
       
        url="../../../../../GPF_Withdrawal_Details.av?command=loademp&emp_id="+emp_id;
        url=url+"&sid="+Math.random();//for Security add some randaom value in to url
         req.open("GET",url,true);
         req.onreadystatechange=function()
        {
           stateChanged(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
   
    }
}


function nullCheck()
{
            if((document.Hrm_GPFForm.unit_name.value==""))
            {
              alert("Must Choose Accounting unit ");
              return 0;
              
            }
            else if((document.Hrm_GPFForm.txtEmployeeid.value=="")||(document.Hrm_GPFForm.txtEmployeeid.value=="0"))
            {
                alert("Employee Id must be Correct");
                return 0;
            }
            else if(document.Hrm_GPFForm.amount.value=="")
            {
                 alert("Enter the  Withdrawal Amount");
                 return 0;
            }
            else if(document.Hrm_GPFForm.date.value=="")
            {
                alert("Enter the Withdrawal date");
                return 0;
            }
            else if(document.Hrm_GPFForm.type_withdraw.value==0)
           {    
            alert("Select Withdrawal Type");
            return 0;
           }
          return 1;
               
             
}
// Filering  SPL Chars
function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
         if(charCode==46){
                        //    alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)    return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //            alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //    alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }
                       
        
}
//Month  Combo loading / getting value 
function selectActMonth()
{
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    var year1=document.getElementById("ac_year").value;
    
    if(year1==yr)
    {
        
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("ac_month").options[i].disabled=true;
          
       }
       document.getElementById("ac_month").options[mn].selected='selected';
       
    }
    else
    {
         document.getElementById("ac_month").disabled=false;
       for(var i=0;i<12;i++)
       {    
    
           document.getElementById("ac_month").options[i].disabled=false;
          
       }
      
    }
}


function selectRelMonth()
{
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    var year2=document.getElementById("rel_year").value;
    
    if(year2==yr)
    {
        
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("rel_month").options[i].disabled=true;
          
       }
       document.getElementById("rel_month").options[mn].selected='selected';
       
    }
    else
    {
         //document.getElementById("rel_month").disabled=false;
         
       for(var i=0;i<12;i++)
       {    
     
           document.getElementById("rel_month").options[i].disabled=false;
          
       }
      
    }
}

