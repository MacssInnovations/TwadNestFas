//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
//alert('asdfasdfasdfkkk');
function servicepopup1()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
       return;
    }
    else
    {
        winemp=null
    }
     startwaiting(document.frmEmployee) ;   
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
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
     
    //winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employee Selection for New UpdateEmployee","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
     winemp= window.open("EmpServicePopup.jsp","mywindow_NECO","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}


function doParentEmp(emp)
{
document.frmEmployee.txtEmpId1.value=emp;
callServer1('Load','null');
comboCadre();
}



///////////////////////////////////////////////////////////////////////////////////


//////////////   FOR JOB POPUP WINDOW //////////////////////



var winjob;

function jobpopup1()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
       return;

    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch_NECO","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
 
    winjob.focus();
    
}
function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed) 
         winjob.officeSelection(true,true,true,false);
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
//if(deptid==null)
{
    document.frmEmployee.Office_Id.value=jobid;
    document.frmEmployee.Dept_Id.value=deptid;
    callServer1('ExistgOff','null');
    return true;
}

}

window.onunload=function()
{
//alert('hello');
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}

 //***************************************************************************//

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


// function to clear all the combo and text fields and even the grid

 function clearAll()
 {        
        
        document.frmEmployee.txtEmpId1.value="";
        document.frmEmployee.Employee_Name.value="";
        document.frmEmployee.Gpf_Number.value="";
        document.frmEmployee.txtCurr_desig.value="";
        document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].value="";
        document.frmEmployee.txtdtPC.value="";
        document.frmEmployee.txtP_No.value="";
        document.frmEmployee.txtPdt.value="";
        document.frmEmployee.txt_rem.value="";     
        
        var tbody=document.getElementById("grid_body");
           try
            {tbody.innerHTML="";}
              catch(e) 
             {tbody.innerText="";}
        
          
           document.frmEmployee.txtEmpId1.focus();
           document.frmEmployee.cmbAdd.disabled=false;
            document.frmEmployee.cmbUpdate.disabled=true;
             document.frmEmployee.cmbDel.disabled=true;
 }

 //This is to check the Redundancy of the Existing Record
  function checkForRedundancy()
{

                        strEmpId=document.frmEmployee.txtEmpId1.value;
                         strOffId=document.frmEmployee.Office_Id.value;
                         strDeptId=document.frmEmployee.Dept_Id.value;
                         strDtjoin=document.frmEmployee.txtdtjoin.value;
   if((strEmpId=="")&&(strOffId=="")&&(strDtjoin=="")&&(strDeptId==""))
          {           
              alert("Enter Employee Id");
                return true;
            }
            else 
            {
                      startwaiting(document.frmEmployee) ;
                       url="../../../../../UpdateEmployeeControllingOffice.con?command=check&EName="+strEmpId;
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


function callServer1(command,param)
 {     
       
       
          var emp_id=document.frmEmployee.txtEmpId1.value;
          var cad_id=document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].value;
          var prob_date=document.frmEmployee.txtdtPC.value;
          var pro_no=document.frmEmployee.txtP_No.value;
          var pro_date=document.frmEmployee.txtPdt.value;
          var rem=document.frmEmployee.txt_rem.value;
          
          if(command=="Add")
          {
                    var check=nullCheck();
                   
                    if(check)
                    {
          startwaiting(document.frmEmployee) ;
                    url="../../../../../Probation_Completion_DetailServ?command=Add&EName="+emp_id+"&Cadr="+cad_id+"&Probdat="+prob_date+"&Pro_no="+pro_no+"&Prodat="+pro_date+"&Rem="+rem;
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
         else if(command=="Load")
         {          
           startwaiting(document.frmEmployee);
           url="../../../../../Probation_Completion_DetailServ?command=Load&EName="+emp_id;
           //alert('url..'+url);
           var req=getTransport();
           
            req.open("GET",url,true); 
            
           req.onreadystatechange=function()
            {
            handleResponse(req);
            }
           // req.onreadystatechange=handleResponse(req);
             if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
        }
        else if(command=="Delete")
        {
            var check=nullCheck();
            
            if(check==true)
            {
               if(confirm("Do You Really want to Delete it"))
               {
                            startwaiting(document.frmEmployee) ;
                url="../../../../../Probation_Completion_DetailServ?command=Delete&EName="+emp_id+"&cadr_id="+cad_id;
                //req.abort();
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
        }
        else if(command=="Update")
        {
            
            var check=nullCheck();           
            
            if(check==true)
            {
               startwaiting(document.frmEmployee);
                url="../../../../../Probation_Completion_DetailServ?command=Update&EName="+emp_id+"&Cadr="+cad_id+"&Probdat="+prob_date+"&Pro_no="+pro_no+"&Prodat="+pro_date+"&Rem="+rem;
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
        else if(command=="ExistgOff")
       {
            if(document.frmEmployee.Office_Id.value=="")
           {
             alert("Select Office Id");
             document.frmEmployee.Office_Id.value="";
             document.frmEmployee.Office_Name.value=""; 
             document.frmEmployee.txtOffAddress.value="";
             document.frmEmployee.Office_Id.focus();
             return false;
           }
           var strOffId=document.frmEmployee.Office_Id.value;
           var strDeptId=document.frmEmployee.Dept_Id.value;
          startwaiting(document.frmEmployee) ;
          url="../../../../../UpdateEmployeeControllingOffice.con?command=ExistgOff&OffName=" + strOffId +"&DeptId="+strDeptId;
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

function comboCadre()
{
 var e_id=document.frmEmployee.txtEmpId1.value;
 
 var url="../../../../../Probation_Completion_DetailServ?command=loadCadre&txteid="+e_id;
 
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                callCombo(req);
                }
                req.send(null);
}

function callCombo(req)
{
 if(req.readyState==4)
 {
  if(req.status==200)
  {
    var i;
    var j;
    
    var cad=document.getElementById("cmb_Cad");
    cad.innerHTML="";
    
    var sel=req.responseXML.getElementsByTagName("select")[0];
    
    var options=sel.getElementsByTagName("option");
    var ctop=document.createElement("OPTION");
    ctop.text="---Select Cadre---";
    ctop.value="";
    
    
    try
    {
      cad.add(ctop);
    }
    catch(e)
    {
     cad.add(ctop,null);
    }
    
    for(i=0;i<options.length;i++)
    {
      var id=options[i].getElementsByTagName("cadre_id")[0].firstChild.nodeValue;
      var cadr=options[i].getElementsByTagName("cadre")[0].firstChild.nodeValue;
      
      var cname=document.createElement("OPTION");
      cname.text=cadr;
      cname.value=id;
      
      try
      {
        cad.add(cname);
      }
      catch(e)
      {
       cad.add(cname,null);
      }
    }
    
  }
 }
}

function handleResponse(req)
    {   
   
         if(req.readyState==4)
        {
          if(req.status==200)
          {      
             //alert('test1');
              stopwaiting(document.frmEmployee);
             //alert('test2');
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="Add")
              { 
                  addRow(baseResponse);                 
              }
              else if(command=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
              else if(command=="check")
              {
              checkRow(baseResponse);
              }
              else if(command=="Delete")
              {
              deleteRow(baseResponse);
              }
              else if(command=="Load")
              {
              loadRow(baseResponse);
              }
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="ExistgOff")
              {
                  existOffRow(baseResponse);                 
              }

          }
        }
  }
function checkRow(baseResponse)
{ 
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

                  if(flag=="success")
                  {
                     var check=false;
                      check=nullCheck();
                      if(check==true)
                      {
                          callServer1('Update','null');
                       }   
                         
                 }
                    
                  else
                  {
                          var check=false;
                          check=nullCheck();
                          if(check==true)
                          {
                            callServer1('Add','null');
                          }
                 
                  }
 
}

function addRow(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
              
                  if(flag=="success")
                  {
                      alert("Record inserted into the database successfully");
                      //clearAll();
                      
                      var items=new Array();
                      
                      items[0]=document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].value;
                     
                      items[1]=document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].text;
                      
                      items[2]=document.frmEmployee.txtdtPC.value;
                      
                      items[3]=document.frmEmployee.txtP_No.value;
                     
                      items[4]=document.frmEmployee.txtPdt.value;
                     
                      items[5]=document.frmEmployee.txtEmpId1.value;
                    
                      items[6]=document.frmEmployee.txt_rem.value;
                                          
                      var tbody=document.getElementById("grid_body");
                      
                      var mycurrent_row=document.createElement("TR");
                      mycurrent_row.id=items[0];
                      
                      var cell=document.createElement("TD");
                      var anc=document.createElement("A");
                      var url="javascript:loadValuesFromTable('"+items[0]+"')";
                      anc.href=url;
                      
                      var txtedit=document.createTextNode("Edit");
                      anc.appendChild(txtedit);
                      cell.appendChild(anc);
                      mycurrent_row.appendChild(cell);
                      
                      var cell2=document.createElement("TD");
                      var cadr=document.createTextNode(items[1]);
                      cell2.appendChild(cadr);
                      
                      var cad_id=document.createElement("input");
                      cad_id.type="hidden";
                      cad_id.name="cadid";
                      cad_id.id="cadid";
                      cad_id.value=items[0];
                      cell2.appendChild(cad_id);
                      mycurrent_row.appendChild(cell2);
                      
                      var cell3=document.createElement("TD");
                      var dobc=document.createTextNode(items[2]);
                      cell3.appendChild(dobc);
                      mycurrent_row.appendChild(cell3);
                      
                      
                      var cell4=document.createElement("TD");
                      var pno=document.createTextNode(items[3]);
                      cell4.appendChild(pno);
                      mycurrent_row.appendChild(cell4);
                      
                      
                      
                      var cell5=document.createElement("TD");
                      var pdt=document.createTextNode(items[4]);
                      cell5.appendChild(pdt);
                      mycurrent_row.appendChild(cell5);
                      
                      var cell6=document.createElement("TD");
                      var rmk=document.createTextNode(items[6]);
                      cell6.appendChild(rmk);
                      mycurrent_row.appendChild(cell6);
                      
                      tbody.appendChild(mycurrent_row);
                     
                      document.frmEmployee.txtdtPC.value="";
                      document.frmEmployee.txtP_No.value="";
                      document.frmEmployee.txtPdt.value="";  
                      document.frmEmployee.txt_rem.value="";       
                      
                      
                      
                  }
                  else
                  {
                      alert("Already the Record Exists for these Cadre");   
                      
                      document.frmEmployee.txtdtPC.value="";
                      document.frmEmployee.txtP_No.value="";
                      document.frmEmployee.txtPdt.value="";  
                      document.frmEmployee.txt_rem.value=""; 
                  }
}
    
function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                      alert("Records are Deleted"); 
                       //clearAll();
                       var tbody=document.getElementById("mytable");
                       var r=document.getElementById(comid);
                       var ri=r.rowIndex;
                       tbody.deleteRow(ri);
                       
                    document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].value="";
                    document.frmEmployee.txtdtPC.value="";
                    document.frmEmployee.txtP_No.value="";
                    document.frmEmployee.txtPdt.value="";
                    document.frmEmployee.txt_rem.value="";
                    
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }

function loadRow(baseResponse)
 {
                 
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                  if(flag=="success")
                  {
                          var empid=baseResponse.getElementsByTagName("EMP_ID");
                          
                          
                          var empname=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                          //var empprefix=baseResponse.getElementsByTagName("EmpPref").firstChild.nodeValue; 
                          var empinitial=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                          var empgpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                          var desig_id=baseResponse.getElementsByTagName("desig_id")[0].firstChild.nodeValue; 
                          var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue; 
                          
                          document.frmEmployee.Employee_Name.value=empname;
                          if(empgpf==0)
                                empgpf="";
                          document.frmEmployee.Gpf_Number.value=empgpf;
                          document.frmEmployee.txtCurr_desig.value=desig;
                          
                          /*
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EmpName"); 
                              var empprefix=baseResponse.getElementsByTagName("EmpPref");
                              var empinitial=baseResponse.getElementsByTagName("EmpInit");
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf");
                              
                              
                              
                            var desig_id=baseResponse.getElementsByTagName("desig_id");
                            var desig=baseResponse.getElementsByTagName("desig");
                             
                             var tempid=empid.item(j).firstChild.nodeValue;
                            var tempname=empname.item(j).firstChild.nodeValue;
                            //var tempprefix=empprefix.item(j).firstChild.nodeValue;
                            var tempinitial=empinitial.item(j).firstChild.nodeValue;
                            var tempgpf=empgpf.item(j).firstChild.nodeValue;
                            if(tempgpf==0)
                                tempgpf="";
                            
                             var tdesig_id=desig_id.item(j).firstChild.nodeValue;
                             var tdesig=desig.item(j).firstChild.nodeValue;
                            //var trecordstatus=recordstatus.item(j).firstChild.nodeValue;
                          
                           
                            document.frmEmployee.Employee_Name.value=tempname;
                            if(tempgpf==0)
                                tempgpf="";
                              
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                             
                            
                            document.frmEmployee.txtCurr_desig.value=tdesig;
                            
                          
                          }*/
                          
                       var tbody=document.getElementById("grid_body");
                       var table=document.getElementById("mytable");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   
                          
                          var det=baseResponse.getElementsByTagName("details");
                          //alert(cad_id);
                          
                          for(var k=0;k<det.length;k++)
                          {
                            var item=new Array();
                            
                            var cad_id=baseResponse.getElementsByTagName("cad_id");
                            var cad_name=baseResponse.getElementsByTagName("cad_name");
                            var prob_date=baseResponse.getElementsByTagName("prob_date");
                            var proc_no=baseResponse.getElementsByTagName("proceed_no");
                            var proc_dat=baseResponse.getElementsByTagName("proceed_date");
                            var rem=baseResponse.getElementsByTagName("remarks");
                            
                            var tcad_id=cad_id.item(k).firstChild.nodeValue;
                            var tcadnm=cad_name.item(k).firstChild.nodeValue;
                            var tprob_dat=prob_date.item(k).firstChild.nodeValue;
                            var tproc_no=proc_no.item(k).firstChild.nodeValue;
                            var tproc_dat=proc_dat.item(k).firstChild.nodeValue;
                            var trem=rem.item(k).firstChild.nodeValue;
                            
                            
                            var tbody=document.getElementById("grid_body");
                      
                      var mycurrent_row=document.createElement("TR");
                      mycurrent_row.id=tcad_id;
                      
                      var cell=document.createElement("TD");
                      var anc=document.createElement("A");
                      var url="javascript:loadValuesFromTable('"+tcad_id+"')";
                      anc.href=url;
                      
                      var txtedit=document.createTextNode("Edit");
                      anc.appendChild(txtedit);
                      cell.appendChild(anc);
                      mycurrent_row.appendChild(cell);
                      
                      var cell2=document.createElement("TD");
                      var cadr=document.createTextNode(tcadnm);
                      cell2.appendChild(cadr);
                      
                      var cad_id=document.createElement("input");
                      cad_id.type="hidden";
                      cad_id.name="cadid";
                      cad_id.id="cadid";
                      cad_id.value=tcad_id;
                      cell2.appendChild(cad_id);
                      mycurrent_row.appendChild(cell2);
                      
                      var cell3=document.createElement("TD");
                      var dobc=document.createTextNode(tprob_dat);
                      cell3.appendChild(dobc);
                      mycurrent_row.appendChild(cell3);
                      
                      
                      var cell4=document.createElement("TD");
                      var pno=document.createTextNode(tproc_no);
                      cell4.appendChild(pno);
                      mycurrent_row.appendChild(cell4);
                      
                      
                      
                      var cell5=document.createElement("TD");
                      var pdt=document.createTextNode(tproc_dat);
                      cell5.appendChild(pdt);
                      mycurrent_row.appendChild(cell5);
                      
                      var cell6=document.createElement("TD");
                      var rmk=document.createTextNode(trem);
                      cell6.appendChild(rmk);
                      mycurrent_row.appendChild(cell6);
                      
                      tbody.appendChild(mycurrent_row);
                      
                          }
                         
                       }
         else if(flag=="failure1")
    {
          // alert(document.frmEmployee.txtEmpId1.value);
            var id=document.frmEmployee.txtEmpId1.value;
            alert("Can not Update SR Control Office. Because Employee Id "+id+" is not under your Office!");
            clearAll();
    }
     else if(flag=="failure2")
    {
            var id=document.frmEmployee.txtEmpId1.value;
            alert("You have no Current Posting. Can not update SR Control Office for "+id+"!");
            clearAll();
    }
     else if(flag=="failure3")
    {
            var id=document.frmEmployee.txtEmpId1.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not update SR Control Office!");
            clearAll();
    }
     else if(flag=="failure4")
    {
            var id=document.frmEmployee.txtEmpId1.value;
            alert("Can not update SR Control Office. Access Denined!");
           clearAll();
    }
                      
                     else if(flag=="NoRecord")
                     {
                  
                        var tempprefix=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                        var tempinitial=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                        var tempname=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                        var tempgpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                        
                           // document.frmEmployee.Employee_Prefix.value=tempprefix;
                           // document.frmEmployee.Employee_Initial.value=tempinitial;
                           if(tempinitial=='null' || tempinitial=='')
                           {
                                 document.frmEmployee.Employee_Name.value=tempname;
                            }
                            else
                            {
                                  tempname=  tempinitial+'.'+tempname;
                                  document.frmEmployee.Employee_Name.value=tempname;
                            }
                            if(tempgpf==0)
                                tempgpf="";
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                            document.frmEmployee.cmbValidate.disabled=false;
                           // document.frmEmployee.cmdDelete.disabled=false;
                     }
                      else if(flag=="freezed")
                    {
                      alert("Given Employee Id is already freezed");
                      //document.frmEmployee.txtEmpId1.value="";
                     //document.frmEmployee.txtEmpId1.focus();
                     
                      var empid=baseResponse.getElementsByTagName("EMP_ID");
                         for(j=0;j<empid.length;j++)
                          {
                              var items=new Array();
                              var empname=baseResponse.getElementsByTagName("EmpName"); 
                              var empprefix=baseResponse.getElementsByTagName("EmpPref");
                              var empinitial=baseResponse.getElementsByTagName("EmpInit");
                              var empgpf=baseResponse.getElementsByTagName("EmpGpf");
                              var offid=baseResponse.getElementsByTagName("OFFICE_ID");
                              var deptid=baseResponse.getElementsByTagName("DEPARTMENT_ID");
                              var jdate=baseResponse.getElementsByTagName("JDate");
                              
                              var offname=baseResponse.getElementsByTagName("OFFICE_NAME"); 
                              var offaddr1=baseResponse.getElementsByTagName("OffAddr1"); 
                              var offaddr2=baseResponse.getElementsByTagName("OffAddr2"); 
                              var city=baseResponse.getElementsByTagName("City");
                              var District=baseResponse.getElementsByTagName("District");
                              var recordstatus=baseResponse.getElementsByTagName("recordstatus");
                             //alert(recordstatus);
                             var tempid=empid.item(j).firstChild.nodeValue;
                            var tempname=empname.item(j).firstChild.nodeValue;
                            var tempprefix=empprefix.item(j).firstChild.nodeValue;
                            var tempinitial=empinitial.item(j).firstChild.nodeValue;
                            var tempgpf=empgpf.item(j).firstChild.nodeValue;
                            if(tempgpf==0)
                                tempgpf="";
                            var toffid=offid.item(j).firstChild.nodeValue;
                            var tdeptid=deptid.item(j).firstChild.nodeValue;
                            var toffname=offname.item(j).firstChild.nodeValue;
                           
                            var tjdate=jdate.item(j).firstChild.nodeValue;
                            var toffaddr1=offaddr1.item(j).firstChild.nodeValue;
                            var toffaddr2=offaddr2.item(j).firstChild.nodeValue;
                            var tcity=city.item(j).firstChild.nodeValue;
                            var tDistrict=District.item(j).firstChild.nodeValue;
                            var trecordstatus=recordstatus.item(j).firstChild.nodeValue;
                            //alert(trecordstatus);
                    
                    if(toffaddr1=="null")
                                toffaddr1="";
                    if(toffaddr2=="null")
                                toffaddr2="";
                    if(tcity=='null')
                        tcity='';
                   if(tDistrict=='null')
                        tDistrict='';
                        
                   var toffaddr= toffaddr1 ;
                   
                   if(toffaddr2!="")
                    toffaddr= toffaddr+"\n" + toffaddr2;
                   if(tcity!="")
                    toffaddr= toffaddr+"\n" + tcity;
                    if(tDistrict!="")
                    toffaddr= toffaddr+"\n" + tDistrict;
                            
                            //document.frmEmployee.Employee_Prefix.value=tempprefix;
                            //document.frmEmployee.Employee_Initial.value=tempinitial;
                            document.frmEmployee.Employee_Name.value=tempname;
                            if(tempgpf==0)
                                tempgpf="";
                              
                            document.frmEmployee.Gpf_Number.value=tempgpf;
                             document.frmEmployee.Office_Id.value=toffid;
                             document.frmEmployee.Dept_Id.value=tdeptid;
                             document.frmEmployee.Office_Name.value=toffname;
                            document.frmEmployee.txtOffAddress.value=toffaddr;
                            if(tjdate!="0")
                            {
                              document.frmEmployee.txtdtjoin.value=tjdate;
                            }
                          }
                          if(trecordstatus=="FR")
                          {
                           // alert("Record is Freezed");
                            document.frmEmployee.cmbValidate.disabled=true;
                            //document.frmEmployee.cmdDelete.disabled=true;
                          }
                          else
                          {
                          //alert('else');
                          document.frmEmployee.cmbValidate.disabled=false;
                          //document.frmEmployee.cmdDelete.disabled=false;
                          }
                          //////////
                     
                     
                     
                    }
                     else if(flag=="NoValue")
                    {
                      alert("Employee Id Does not Exists - Create an Id for the Employee First");
                      document.frmEmployee.txtEmpId1.value="";
                      document.frmEmployee.txtEmpId1.focus();
                    }
                     else
                     {
                      alert("Record does not exist. Insert a new Record");
                     }
}     


function updateRow(baseResponse)
{ 
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                    alert("Records Updated Successfully");
                    //clearAll();
                    
                    var items=new Array();
                    
                    items[1]=document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].text;
                    items[2]=document.frmEmployee.txtdtPC.value;
                    items[3]=document.frmEmployee.txtP_No.value;
                    items[4]=document.frmEmployee.txtPdt.value;
                    items[5]=document.frmEmployee.txt_rem.value;
                    
                    var r=document.getElementById(comid);
                    var rcells=r.cells;
                    
                    rcells.item(1).firstChild.nodeValue=items[1];
                    rcells.item(2).firstChild.nodeValue=items[2];
                    rcells.item(3).firstChild.nodeValue=items[3];
                    rcells.item(4).firstChild.nodeValue=items[4];
                    rcells.item(5).firstChild.nodeValue=items[5];
                    
                    document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].value="";
                    document.frmEmployee.txtdtPC.value="";
                    document.frmEmployee.txtP_No.value="";
                    document.frmEmployee.txtPdt.value="";
                    document.frmEmployee.txt_rem.value="";
                    
                    document.frmEmployee.cmbUpdate.disabled=true;
                    
                  }
                  else
                  {
                    alert("Failed to update");
                  }
}   

/*
function existOffRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
             
              if(flag=="success")
              { 
              
                   var offaddr1=baseResponse.getElementsByTagName("OffAddr1")[0].firstChild.nodeValue; 
                   var offaddr2=baseResponse.getElementsByTagName("OffAddr2")[0].firstChild.nodeValue; 
                   var city=baseResponse.getElementsByTagName("City")[0].firstChild.nodeValue; 
                    var District=baseResponse.getElementsByTagName("District")[0].firstChild.nodeValue; 
                   
                    if(offaddr1=="null")
                                offaddr1="";
                    if(offaddr2=="null")
                                offaddr2="";
                    if(city=='null')
                        city='';
                   if(District=='null')
                        District='';
                        
                   var toffaddr= offaddr1 ;
                   
                   if(offaddr2!="")
                    toffaddr= toffaddr+"\n" + offaddr2;
                   if(city!="")
                    toffaddr= toffaddr+"\n" + city;
                    if(District!="")
                    toffaddr= toffaddr+"\n" + District;
                  
                   
                    document.frmEmployee.Office_Name.value=baseResponse.getElementsByTagName("OffName")[0].firstChild.nodeValue; 
                    document.frmEmployee.txtOffAddress.value=toffaddr;
              }
              else if(flag=="NoValue")
              {
                 alert("No Office Exists - Give Some other Office Id");
                 document.frmEmployee.Office_Id.value="";
                 document.frmEmployee.Office_Name.value=""; 
                 document.frmEmployee.txtOffAddress.value="";
                 document.frmEmployee.Office_Id.focus();
                 
              }
              else
              {
                alert("Failed to load office details");
                document.frmEmployee.Office_Id.value="";
                 document.frmEmployee.Office_Name.value=""; 
                 document.frmEmployee.txtOffAddress.value="";
                 document.frmEmployee.Office_Id.focus();
                 
              }
   }
*/
function disp()
{
   if(document.frmEmployee.Office_Id.value=="")
   {
     alert("Select Office Id - It should not be empty");
     document.frmEmployee.Office_Id.focus();
     return false;
   }
}



// generalized code

//To Check the null values

function nullCheck()
{
   if(document.frmEmployee.txtEmpId1.value=="")
   {
      alert("Enter Employee Id");
      document.frmEmployee.txtEmpId1.focus();
      return false;
   }
   else if(document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].value=="")
   {
     alert("Select Cadre");
     document.frmEmployee.cmb_Cad.focus();
     return false;
   }
   else if(document.frmEmployee.txtdtPC.value=="")
   {
      alert("Enter Date of Probation Completion");
      document.frmEmployee.txtdtPC.focus();
      return false;
   }
   else if(document.frmEmployee.txtP_No.value=="")
   {
      alert("Enter Date of Proceeding No.");
      document.frmEmployee.txtP_No.focus();
      return false;
   }
   else if(document.frmEmployee.txtPdt.value=="")
   {
      alert("Enter Date of Proceeding Date");
      document.frmEmployee.txtPdt.focus();
      return false;
   }
   datefun();
  
   return true;
}  

//Date validation
function validate_date(formName, textName)
 {
 
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    eval('document.' + formName + '.' + textName + '.focus()');
                    eval('document.' + formName + '.' + textName + '.select()');
                    
                    return false;
                }
                
                 return true;  
                     
 }
function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       //alert(unicode);
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
     
function toFocus()
{
 //alert("test");
  //var FirstField=document.frmEmployee.txtEmpId1.value;
  if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     document.frmEmployee.txtEmpId1.focus();
     return false;
  }
  return true;
   
}
     
//This Coding for Date Validation and Checking     
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
function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
          var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
          t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
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
        
        }
        if( f==true)
        {
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
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
           
            return false
    }
    
}*/



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



function datefun()
{
        var fromdt = document.frmEmployee.txtdtPC.value;
        var todt = document.frmEmployee.txtPdt.value;
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
            alert('Proceeding Date should be greater than Date of Probation Completion');
            document.frmEmployee.txtPdt.value="";
            document.frmEmployee.txtPdt.focus();
            return false;
        }
        else if(fyear==tyear)
        {
                if(fmon>tmon)
                {
                    alert('Proceeding Date should be greater than Date of Probation Completion');
                    document.frmEmployee.txtPdt.value="";
                    document.frmEmployee.txtPdt.focus();
                    return false;
                }
                else if(fmon==tmon)
                {
                        if(fday>tday)
                        {
                             alert('Proceeding Date should be greater than Date of Probation Completion');
                             document.frmEmployee.txtPdt.value="";
                             document.frmEmployee.txtPdt.focus();
                            return false;
                        }
                        
                }
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

var comid="";

function loadValuesFromTable(rid)
{
   comid=rid;
   //alert('comid...'+comid);
   
   var r=document.getElementById(rid);
   var rcells=r.cells;
   
   //alert(rcells.items(1).firstChild.nodeValue);
   //alert(rcells.items(1).lastChild.value);
   
   document.frmEmployee.cmb_Cad.value=comid;
   //document.frmEmployee.cmb_Cad.options[document.frmEmployee.cmb_Cad.selectedIndex].text=rcells.item(1).firstChild.nodeValue;
   document.frmEmployee.txtdtPC.value=rcells.item(2).firstChild.nodeValue;
   document.frmEmployee.txtP_No.value=rcells.item(3).firstChild.nodeValue;
   document.frmEmployee.txtPdt.value=rcells.item(4).firstChild.nodeValue;
   document.frmEmployee.txt_rem.value=rcells.item(5).firstChild.nodeValue;
   
   document.frmEmployee.cmbAdd.disabled=true;
   document.frmEmployee.cmbUpdate.disabled=false;
   document.frmEmployee.cmbDel.disabled=false;
   
}


