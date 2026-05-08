var xmlhttp;var seq=0;var common="",mn,yr,v;
function Exit()
{
    window.close();
}

function doParentEmp(emp)
{
document.getElementById("txtEmpId").value=emp;
call('get');
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
    // startwaiting(document.Hrm_TransJoinForm);   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpJoiningPopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
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
   
        
        document.getElementById("rel_year").options[5].selected='selected';
     /*    var year2=document.getElementById("rel_year").value;
        
    
    if(year2==yr)
    {
        */
       for(var i=(mn+1);i<12;i++)
       {    
    
           document.getElementById("rel_month").options[i].disabled=true;
          
       }
      

        
        
                       /*--- Relative Month--*/
        document.getElementById("ac_month").options[mn].selected='selected';
         document.getElementById("rel_month").options[mn].selected='selected';
         document.Hrm_TransJoinForm.txtEmpId.value="";
        
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.designation.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.unit_name.focus();
        document.Hrm_TransJoinForm.amount.value="";
       document.Hrm_TransJoinForm.ref_amount.value="";
        document.Hrm_TransJoinForm.ref_no.value="";
        document.Hrm_TransJoinForm.ref_total.value="";
        document.Hrm_TransJoinForm.rec_amount.value="";
        document.Hrm_TransJoinForm.rec_no.value="";
        document.Hrm_TransJoinForm.rec_total.value=""; 
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.trans[0].checked='checked';
       document.getElementById("refund_div").disabled=true;
       document.Hrm_TransJoinForm.ref_amount.disabled=true; 
       document.Hrm_TransJoinForm.ref_no.disabled=true;
       document.Hrm_TransJoinForm.ref_total.disabled=true;
       
       document.getElementById("arrear_div").disabled=true;
       document.Hrm_TransJoinForm.rec_amount.disabled=true; 
       document.Hrm_TransJoinForm.rec_no.disabled=true;
       document.Hrm_TransJoinForm.rec_total.disabled=true;
        document.Hrm_TransJoinForm.ac_month.disabled=false;
        document.Hrm_TransJoinForm.ac_year.disabled=false;
       
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
                
	
}
 function loadvalue(empid)
    {      
          common=empid;
          var emp_id=document.getElementById(empid); 
          var rcells=emp_id.cells;
         // document.Hrm_TransJoinForm.txtEmpId.value=rcells.item(1).firstChild.nodeValue;
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
          document.Hrm_TransJoinForm.ac_month[j].selected='selected';
          document.Hrm_TransJoinForm.ac_month.disabled=true;
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
          document.Hrm_TransJoinForm.rel_month[k].selected='selected';
          }
          
          
          //document.getElementById("ac_month").options[document.getElementById("ac_month").selectedIndex].text=rcells.item(2).firstChild.nodeValue;
          //var acyear=rcells.item(2).lastChild.value;
          document.getElementById("ac_year").value=ac_month_year[1];
          document.Hrm_TransJoinForm.ac_year.disabled=true;
          //document.getElementById("rel_month").options[document.getElementById("rel_month").selectedIndex].text=rcells.item(3).firstChild.nodeValue;
          
          document.getElementById("rel_year").value=rel_month_year[1];
          
          var type_trans=rcells.item(3).firstChild.nodeValue;
          
        
          if(type_trans=="CR")
          {
          
          document.Hrm_TransJoinForm.trans[0].checked='checked';
          }
          else
          { 
          document.Hrm_TransJoinForm.trans[1].checked='checked';
          }
          var remark=rcells.item(3).lastChild.value;
         
          if(trim(remark)=="noRemarks")
          {
                 alert("noRemarks");
                  remark="";
          }
          document.getElementById("remarks").value=remark;
          document.Hrm_TransJoinForm.amount.value=rcells.item(4).firstChild.nodeValue;
           var ref_amount=rcells.item(5).firstChild.nodeValue;
          document.Hrm_TransJoinForm.ref_amount.value=ref_amount;
         
          if(ref_amount!=0)
          {
        document.getElementById("refund_div").disabled=false;
       document.Hrm_TransJoinForm.ref_amount.disabled=false; 
       document.Hrm_TransJoinForm.ref_no.disabled=false;
       document.Hrm_TransJoinForm.ref_total.disabled=false;
       document.Hrm_TransJoinForm.refund.checked=true;
 
           }
          var ref_no_total=rcells.item(6).lastChild.nodeValue.split("/");
          document.Hrm_TransJoinForm.ref_no.value=ref_no_total[0];
          document.Hrm_TransJoinForm.ref_total.value=ref_no_total[1];
          var rec_amount=rcells.item(7).firstChild.nodeValue;;
          document.Hrm_TransJoinForm.rec_amount.value=rec_amount;
          if(rec_amount!=0)
          {
        document.getElementById("arrear_div").disabled=false;
       document.Hrm_TransJoinForm.rec_amount.disabled=false; 
       document.Hrm_TransJoinForm.rec_no.disabled=false;
       document.Hrm_TransJoinForm.rec_total.disabled=false;
              document.Hrm_TransJoinForm.arrear.checked=true;
          }
          var rec_no_total=rcells.item(8).lastChild.nodeValue.split("/");
          document.Hrm_TransJoinForm.rec_no.value=rec_no_total[0];
          document.Hrm_TransJoinForm.rec_total.value=rec_no_total[1];
          
       

          document.Hrm_TransJoinForm.add.disabled=true;
        document.Hrm_TransJoinForm.update.disabled=false;
        document.Hrm_TransJoinForm.delete1.disabled=false;
        
          
      
    }
 
function getxmlhttpObject()
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
function stateChanged()
{
    var flag,command,response;
    var month=new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="get")
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
                    var emp_id=response.getElementsByTagName("emp_id")[i].firstChild.nodeValue;
                     
                    var ac_month=response.getElementsByTagName("ac_month")[i].firstChild.nodeValue;
                    ac_month=month[ac_month-1];
                    var acyear=response.getElementsByTagName("ac_year")[i].firstChild.nodeValue;
                    var rel_month=response.getElementsByTagName("rel_month")[i].firstChild.nodeValue;
                    rel_month=month[rel_month-1];
                    var relyear=response.getElementsByTagName("rel_year")[i].firstChild.nodeValue;
                    var type_trans=response.getElementsByTagName("type_trans")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[i].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[i].firstChild.nodeValue;
                    var rec_no=response.getElementsByTagName("rec_no")[i].firstChild.nodeValue;
                    var rec_total=response.getElementsByTagName("rec_total")[i].firstChild.nodeValue;
                   
                     if(remark=="null"||remark==null||remark=="noRemarks"||remark=="")
                     {
                        remark="noRemarks";
                     }
                     
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
                    var typetrans=document.createTextNode(type_trans);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td4.appendChild(h7);
                    tr.appendChild(td4);
                      
                    var td5=document.createElement("TD");
                    var subamt=document.createTextNode(amount);
                    td5.appendChild(subamt);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var refamt=document.createTextNode(ref_amount);
                    td6.appendChild(refamt);
                    tr.appendChild(td6);
                      
                    var td7=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="ref_no";
                    h8.value=ref_no;
                    td7.appendChild(h8);
                    var h9=document.createElement("TEXT");
                    h9.type="hidden";
                    h9.name="ref_total";
                    h9.value=ref_total;
                    td7.appendChild(h9);
                    var h13=document.createTextNode(ref_no+"/"+ref_total);
                    td7.appendChild(h13);
                    tr.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var recamt=document.createTextNode(rec_amount);
                    td8.appendChild(recamt);
                    tr.appendChild(td8);
                     
                    var td9=document.createElement("TD");
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="rec_no";
                    h10.value=rec_no;
                    td9.appendChild(h10);
                    var h11=document.createElement("TEXT");
                    h11.type="hidden";
                    h11.name="rec_total";
                    h11.value=rec_total;
                    td9.appendChild(h11);
                    var h12=document.createTextNode(rec_no+"/"+rec_total);
                    td9.appendChild(h12);
                    tr.appendChild(td9);
                 
                    tlist.appendChild(tr);             
                    seq++;
                    }
                }
                else
                    {
                     document.Hrm_TransJoinForm.txtEmpId.value="";
                     document.getElementById("comEmpId").value="";
                    document.getElementById("txtDOB").value="";
                    document.getElementById("txtGpfNo").value="";
                    document.getElementById("designation").value="";
                    document.getElementById("amount").value="";
                    document.getElementById("ref_amount").value="";
                    document.getElementById("ref_no").value="";
                    document.getElementById("ref_total").value="";
                    document.getElementById("rec_amount").value="";
                    document.getElementById("rec_no").value="";
                    document.getElementById("rec_total").value="";
                    document.Hrm_TransJoinForm.txtEmpId.focus();
                    alert("This Employee ID doesnot Exist");
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
                    var type_trans=response.getElementsByTagName("type_trans")[0].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[0].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[0].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[0].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[0].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[0].firstChild.nodeValue;
                    var rec_no=response.getElementsByTagName("rec_no")[0].firstChild.nodeValue;
                    var rec_total=response.getElementsByTagName("rec_total")[0].firstChild.nodeValue;
                 
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
                    var typetrans=document.createTextNode(type_trans);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td4.appendChild(h7);
                    tr.appendChild(td4);
                      
                    var td5=document.createElement("TD");
                    var subamt=document.createTextNode(amount);
                    td5.appendChild(subamt);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var refamt=document.createTextNode(ref_amount);
                    td6.appendChild(refamt);
                    tr.appendChild(td6);
                      
                    var td7=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="ref_no";
                    h8.value=ref_no;
                    td7.appendChild(h8);
                    var h9=document.createElement("TEXT");
                    h9.type="hidden";
                    h9.name="ref_total";
                    h9.value=ref_total;
                    td7.appendChild(h9);
                    var h13=document.createTextNode(ref_no+"/"+ref_total);
                    td7.appendChild(h13);
                    tr.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var recamt=document.createTextNode(rec_amount);
                    td8.appendChild(recamt);
                    tr.appendChild(td8);
                     
                    var td9=document.createElement("TD");
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="rec_no";
                    h10.value=rec_no;
                    td9.appendChild(h10);
                    var h11=document.createElement("TEXT");
                    h11.type="hidden";
                    h11.name="rec_total";
                    h11.value=rec_total;
                    td9.appendChild(h11);
                    var h12=document.createTextNode(rec_no+"/"+rec_total);
                    td9.appendChild(h12);
                    tr.appendChild(td9);
                 
                    tlist.appendChild(tr);             
                    seq++;
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                      clear1();
                    
                }
                else
                    alert("Same data wont be allowed");
            }
          else  if(command=="Update")
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
                    var type_trans=response.getElementsByTagName("type_trans")[0].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[0].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[0].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[0].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[0].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[0].firstChild.nodeValue;
                    var rec_no=response.getElementsByTagName("rec_no")[0].firstChild.nodeValue;
                    var rec_total=response.getElementsByTagName("rec_total")[0].firstChild.nodeValue;

                    var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                    //cells.item(1).firstChild.nodeValue=emp_id;
                    cells.item(1).lastChild.nodeValue=ac_month+"-"+acyear;
                    //cells.item(2).firstChild.value=ac_month;
                    //cells.item(2).lastChild.value=ac_year;
                    cells.item(2).lastChild.nodeValue=rel_month+"-"+relyear;
                    //cells.item(3).firstChild.value=rel_month;
                    //cells.item(3).lastChild.value=rel_year;
                    cells.item(3).firstChild.nodeValue=type_trans;
                    cells.item(3).lastChild.value=remark;
                    cells.item(4).firstChild.nodeValue=amount;
                    cells.item(5).firstChild.nodeValue=ref_amount;
                    cells.item(6).lastChild.nodeValue=ref_no+"/"+ref_total;
                    cells.item(7).firstChild.nodeValue=rec_amount;
                    cells.item(8).lastChild.nodeValue=rec_no+"/"+rec_total;
                  
                    
                    alert("Updated Successfully");
                     clear1();
                    
                     document.Hrm_TransJoinForm.add.disabled=false;
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;
                   
                                       
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
                          tist.innerHTML="";
                        }
                     catch(e)
                          {
                           tlist.innerText="";
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
                    var type_trans=response.getElementsByTagName("type_trans")[i].firstChild.nodeValue;
                    var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                    var remark=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                    var ref_amount=response.getElementsByTagName("ref_amount")[i].firstChild.nodeValue;
                    var ref_no=response.getElementsByTagName("ref_no")[i].firstChild.nodeValue;
                    var ref_total=response.getElementsByTagName("ref_total")[i].firstChild.nodeValue;
                    var rec_amount=response.getElementsByTagName("rec_amount")[i].firstChild.nodeValue;
                    var rec_no=response.getElementsByTagName("rec_no")[i].firstChild.nodeValue;
                    var rec_total=response.getElementsByTagName("rec_total")[i].firstChild.nodeValue;
                   

                    
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
                    var typetrans=document.createTextNode(type_trans);
                    td4.appendChild(typetrans);
                    var h7=document.createElement("TEXT");
                    h7.type="hidden";
                    h7.name="remarks";
                    h7.value=remark;
                    td4.appendChild(h7);
                    tr.appendChild(td4);
                      
                    var td5=document.createElement("TD");
                    var subamt=document.createTextNode(amount);
                    td5.appendChild(subamt);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var refamt=document.createTextNode(ref_amount);
                    td6.appendChild(refamt);
                    tr.appendChild(td6);
                      
                    var td7=document.createElement("TD");
                    var h8=document.createElement("TEXT");
                    h8.type="hidden";
                    h8.name="ref_no";
                    h8.value=ref_no;
                    td7.appendChild(h8);
                    var h9=document.createElement("TEXT");
                    h9.type="hidden";
                    h9.name="ref_total";
                    h9.value=ref_total;
                    td7.appendChild(h9);
                    var h13=document.createTextNode(ref_no+"/"+ref_total);
                    td7.appendChild(h13);
                    tr.appendChild(td7);
                    
                    var td8=document.createElement("TD");
                    var recamt=document.createTextNode(rec_amount);
                    td8.appendChild(recamt);
                    tr.appendChild(td8);
                     
                    var td9=document.createElement("TD");
                    var h10=document.createElement("TEXT");
                    h10.type="hidden";
                    h10.name="rec_no";
                    h10.value=rec_no;
                    td9.appendChild(h10);
                    var h11=document.createElement("TEXT");
                    h11.type="hidden";
                    h11.name="rec_total";
                    h11.value=rec_total;
                    td9.appendChild(h11);
                    var h12=document.createTextNode(rec_no+"/"+rec_total);
                    td9.appendChild(h12);
                    tr.appendChild(td9);
                 
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
                     clear1();
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;       
                    
                }
                else
                {
                    alert("Not success in Delete");
                    }
            
        }
        else if(command=="unit")
            {
                if(flag=='success')
                {
                     var unit_id=response.getElementsByTagName("unit_id")[0].firstChild.nodeValue;
                     document.getElementById("oldcode").value=unit_id;
               }
                else
                {
                  
                    alert("problem in unit_id");
                }
            
        }
        }
    }
           
}
function clear1()
{
    document.Hrm_TransJoinForm.amount.value="";
        document.Hrm_TransJoinForm.ref_amount.value="";
        document.Hrm_TransJoinForm.ref_no.value="";
        document.Hrm_TransJoinForm.ref_total.value="";
        document.Hrm_TransJoinForm.rec_amount.value="";
        document.Hrm_TransJoinForm.rec_no.value="";
        document.Hrm_TransJoinForm.rec_total.value="";
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.trans[0].checked='checked';
        document.Hrm_TransJoinForm.refund.checked=false;
        document.Hrm_TransJoinForm.arrear.checked=false;
        document.Hrm_TransJoinForm.ac_month.disabled=false;
        document.Hrm_TransJoinForm.ac_year.disabled=false;
        document.getElementById("refund_div").disabled=true;
       document.Hrm_TransJoinForm.ref_amount.disabled=true; 
       document.Hrm_TransJoinForm.ref_no.disabled=true;
       document.Hrm_TransJoinForm.ref_total.disabled=true;
       
       document.getElementById("arrear_div").disabled=true;
       document.Hrm_TransJoinForm.rec_amount.disabled=true; 
       document.Hrm_TransJoinForm.rec_no.disabled=true;
       document.Hrm_TransJoinForm.rec_total.disabled=true;
        
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
   
        
        document.getElementById("rel_year").options[5].selected='selected';
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
        
      
}

function clearGPF()
{
          
        document.Hrm_TransJoinForm.ac_month.disabled=false;
        document.Hrm_TransJoinForm.ac_year.disabled=false;
        displayMonth();
        document.Hrm_TransJoinForm.txtEmpId.value="";
        document.Hrm_TransJoinForm.comEmpId.value="";
        document.Hrm_TransJoinForm.designation.value="";
        document.Hrm_TransJoinForm.txtDOB.value="";
        document.Hrm_TransJoinForm.txtGpfNo.value="";
        document.Hrm_TransJoinForm.amount.value="";
        document.Hrm_TransJoinForm.ref_amount.value="";
        document.Hrm_TransJoinForm.ref_no.value="";
        document.Hrm_TransJoinForm.ref_total.value="";
        document.Hrm_TransJoinForm.rec_amount.value="";
        document.Hrm_TransJoinForm.rec_no.value="";
        document.Hrm_TransJoinForm.rec_total.value="";
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.trans[0].checked='checked';
        document.Hrm_TransJoinForm.refund.checked=false;
        document.Hrm_TransJoinForm.arrear.checked=false;
        document.Hrm_TransJoinForm.txtEmpId.focus();
      
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
       
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
        
}
function call(command)
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
    
        
        var office_id;
        var division_id;
        var ac_month;
        var rel_month;
        var ac_year;
        var rel_year;
        var type_trans;
        var ref_amount,ref_no,ref_total;
        var emp_id;
        var amount;
        var rec_amount,rec_no,rec_total;
        var remarks;
        
        var url="";
        if(command=="Add")
        { 
                if(nullCheck())
                {
                office_id=document.getElementById("txtOffId").value;
                division_id=document.getElementById("oldcode").value;
                emp_id=document.getElementById("txtEmpId").value;
                ac_month=document.getElementById("ac_month").value;
                ac_year=document.getElementById("ac_year").value;
                rel_month=document.getElementById("rel_month").value;
                rel_year=document.getElementById("rel_year").value;
                amount=document.getElementById("amount").value;
                remarks=document.getElementById("remarks").value;
                 if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                     {
                        remarks="noRemarks";
                     }
                 if (document.Hrm_TransJoinForm.trans[0].checked)
               {
                    type_trans=document.Hrm_TransJoinForm.trans[0].value;
               }
               else
                {
                   type_trans=document.Hrm_TransJoinForm.trans[1].value;
                }
                url="../../../../../GPF_Subscription.view?command=Add&office_id="+office_id+"&division_id="+division_id+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_trans="+type_trans+"&amount="+amount+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year;
                if (document.Hrm_TransJoinForm.refund.checked==true)
               {
                    ref_amount=document.getElementById("ref_amount").value;
                    ref_no=document.getElementById("ref_no").value;
                    ref_total=document.getElementById("ref_total").value;
                    url=url+"&ref_amount="+ref_amount+"&ref_no="+ref_no+"&ref_total="+ref_total;
               }
               else
               {
                url=url+"&ref_amount=0&ref_no=0&ref_total=0";
               }
               if (document.Hrm_TransJoinForm.arrear.checked==true)
                {
                    rec_amount=document.getElementById("rec_amount").value;
                    rec_no=document.getElementById("rec_no").value;
                    rec_total=document.getElementById("rec_total").value;
                    url=url+"&rec_amount="+rec_amount+"&rec_no="+rec_no+"&rec_total="+rec_total;
                }
                else
                {
                    url=url+"&rec_amount=0&rec_no=0&rec_total=0";
                }
                
          
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
        }
    }
    else if(command=="Update")
    { 
                 office_id=document.getElementById("txtOffId").value;
                division_id=document.getElementById("oldcode").value;
                emp_id=document.getElementById("txtEmpId").value;
                ac_month=document.getElementById("ac_month").value;
                ac_year=document.getElementById("ac_year").value;
                rel_month=document.getElementById("rel_month").value;
                rel_year=document.getElementById("rel_year").value;
                amount=document.getElementById("amount").value;
                remarks=document.getElementById("remarks").value;
                 if(remarks=="null"||remarks==""||remarks==null||remarks==" ")
                     {
                        remarks="noRemarks";
                     }
                 if (document.Hrm_TransJoinForm.trans[0].checked)
               {
                    type_trans=document.Hrm_TransJoinForm.trans[0].value;
               }
               else
                {
                   type_trans=document.Hrm_TransJoinForm.trans[1].value;
                }
                url="../../../../../GPF_Subscription.view?command=Update&office_id="+office_id+"&division_id="+division_id+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&type_trans="+type_trans+"&amount="+amount+"&remarks="+remarks+"&rel_month="+rel_month+"&rel_year="+rel_year;
                    ref_amount=document.getElementById("ref_amount").value;
                    ref_no=document.getElementById("ref_no").value;
                    ref_total=document.getElementById("ref_total").value;
                    url=url+"&ref_amount="+ref_amount+"&ref_no="+ref_no+"&ref_total="+ref_total;
                    rec_amount=document.getElementById("rec_amount").value;
                    rec_no=document.getElementById("rec_no").value;
                    rec_total=document.getElementById("rec_total").value;
                    url=url+"&rec_amount="+rec_amount+"&rec_no="+rec_no+"&rec_total="+rec_total;
               
                
                
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);      
    }
    else if(command=="Delete")
    {           office_id=document.getElementById("txtOffId").value;
                emp_id=document.getElementById("txtEmpId").value;
                ac_month=document.getElementById("ac_month").value;
                ac_year=document.getElementById("ac_year").value;
                
                
      url="../../../../../GPF_Subscription.view?command=Delete&office_id="+office_id+"&emp_id="+emp_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
        
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);      
    }
   
    else if(command=="get")
    {   
        
        if((document.Hrm_TransJoinForm.txtEmpId.value=="")||(document.Hrm_TransJoinForm.txtEmpId.value.length==0))
            {
                alert("Null Value not accepted...Select Employee ID");
                document.Hrm_TransJoinForm.txtEmpId.focus();
                return false;
            }
        var emp_id=document.Hrm_TransJoinForm.txtEmpId.value;
       
        url="../../../../../GPF_Subscription.view?command=get&emp_id="+emp_id;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
            if((document.Hrm_TransJoinForm.unit_name.value==""))
            {
                alert("Must Choose Accounting unit ");
                return 0;
            }
            else if((document.Hrm_TransJoinForm.txtEmpId.value=="")||(document.Hrm_TransJoinForm.txtEmpId.value=="0"))
            {
                alert("Employee Id must be Correct");
                 return 0;
            }
            else if(document.Hrm_TransJoinForm.amount.value=="")
            {
                 alert("Enter the Subscription Amount");
                  return 0;
            }
      return 1;
}
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
        }else{
                        return false;
        }
}
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

function showRefund()
{
    if (document.Hrm_TransJoinForm.refund.checked)
    {
         document.getElementById("refund_div").disabled=false;
       document.Hrm_TransJoinForm.ref_amount.disabled=false; 
       document.Hrm_TransJoinForm.ref_no.disabled=false;
       document.Hrm_TransJoinForm.ref_total.disabled=false;

    }
    else
    {
         document.getElementById("refund_div").disabled=true;
        document.Hrm_TransJoinForm.ref_amount.disabled=true; 
       document.Hrm_TransJoinForm.ref_no.disabled=true;
       document.Hrm_TransJoinForm.ref_total.disabled=true;

    }

    
}
function showArrear()
{
    if (document.Hrm_TransJoinForm.arrear.checked)
    {
                
       document.getElementById("arrear_div").disabled=false;
       document.Hrm_TransJoinForm.rec_amount.disabled=false; 
       document.Hrm_TransJoinForm.rec_no.disabled=false;
       document.Hrm_TransJoinForm.rec_total.disabled=false;
    }
    else
    {
        document.getElementById("arrear_div").disabled=true;
        document.Hrm_TransJoinForm.rec_amount.disabled=true; 
       document.Hrm_TransJoinForm.rec_no.disabled=true;
       document.Hrm_TransJoinForm.rec_total.disabled=true;
    }
  
}
function checkName()
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }  
    var unit_name=document.getElementById("unit_name").value;
    
        url="../../../../../GPF_Subscription.view?command=unit&unit_name="+unit_name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
}