//alert('called')

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

function loadServer(Command)
{
document.getElementById("btnRelieve").disabled=true;
//alert('first');
var year=document.frmEmployeeRelievalOnRetirement.cmbyear.value;
var month=document.frmEmployeeRelievalOnRetirement.cmbmonth.value;
//alert(year+ ''+month)
if(Command=='loadEmployee')
  {
  //alert('second')
    var req=getTransport();
    var url="../../../../../AutoRelievalOnRetirementServ?Command=loadEmployee&curYear="+year+"&curMonth="+(month);
   // alert(url);
    req.open("Post",url,true);
    //alert(req);
    req.onreadystatechange=function()
      {
      //alert('third')
      requestResponse(req);
      }
    req.send(null);    
  }
else if(Command=='relieveEmployee')  
  {
//var x=check();
 // alert(x)
  if(check()==true)
  {
 // alert('1')
  var selEmpId="";
  var url="../../../../../AutoRelievalOnRetirementServ?Command=relieveEmployee&curYear="+year+"&curMonth="+month;
//alert("iiiii"+document.frmEmployeeRelievalOnRetirement.sell.length);
var leng=parseInt(document.frmEmployeeRelievalOnRetirement.sell.length);

           if(leng>0)
           {
           //alert('inside length');
           
             for(i=0;i<document.frmEmployeeRelievalOnRetirement.sell.length;i++)
             {
               if(document.frmEmployeeRelievalOnRetirement.sell[i].checked==true)
               {
               selEmpId=selEmpId+document.frmEmployeeRelievalOnRetirement.sell[i].value +",";
               }
             }
             if(selEmpId!="")
             {
             //alert('here .....')
              
             var selEmpIdnew=selEmpId.substring(0,selEmpId.length-1); 
               url=url+"&selected="+selEmpIdnew;
               //alert(url);
           //document.frmbphabitation.officeselected.value=region;
              //alert(document.frmValidationSummaryRep.officeselected.value);
             }
             else
               {
               
               // alert("Nothing is selected")
                 document.getElementById("btnRelieve").disabled=true;
               }
               
               
               
             //alert(selEmpIdnew);
           //  alert("1")
          //   alert(url);
            document.frmEmployeeRelievalOnRetirement.action=url;
          //  alert(document.frmbphabitation.action);
             document.frmEmployeeRelievalOnRetirement.method="post"; 
             document.frmEmployeeRelievalOnRetirement.submit();  
           }
           else
           {
             //alert('get the data from here');
               if(document.frmEmployeeRelievalOnRetirement.sell.checked==true)
               {
               selEmpId=document.frmEmployeeRelievalOnRetirement.sell.value ;
               //alert(selEmpId)
               }
            
             if(selEmpId!="")
             {
             //alert('here .....')
              
               url=url+"&selected="+selEmpId;
               //alert(url);
               //document.frmbphabitation.officeselected.value=region;
              //alert(document.frmValidationSummaryRep.officeselected.value);
             }
             else
               {
                //alert("Nothing is selected")
                 document.getElementById("btnRelieve").disabled=true;
               }
               
               
               
             //alert(selEmpIdnew);
           //  alert("1")
          //   alert(url);
            document.frmEmployeeRelievalOnRetirement.action=url;
          //  alert(document.frmbphabitation.action);
             document.frmEmployeeRelievalOnRetirement.method="post"; 
             document.frmEmployeeRelievalOnRetirement.submit();
           }
  
  }
  else
    {
   // alert('Select the employee id first...');
    }
  }
  
}


function requestResponse(req)
{
//alert('four');
if(req.readyState==4)
    {
   // alert(req.readyState+"state");
    if(req.status==200)
        {
        //alert(req.status);
        //alert('five');
        var baseRes=req.responseXML.getElementsByTagName("response")[0];
       // alert(baseRes);
        var cmd=baseRes.getElementsByTagName("command")[0].firstChild.nodeValue;
        //alert(cmd);
        if(cmd=='loadEmployee')
            {
          // alert('six')
           var i=0;
            var flag=baseRes.getElementsByTagName("flag")[0].firstChild.nodeValue;
           // alert(flag);
            if(flag=='success')    
                {
               // alert('seven')
                  
                  var service=baseRes.getElementsByTagName("details");
                 // var tbody=document.getElementById("grid_body");
                //  alert(service);
                  if(service)
                      {
                      empid=new Array();         
                      empname=new Array();
                      empstatus=new Array();
                      empoffice=new Array();
                      empdob=new Array();
                      empdor=new Array();
                      empoffid=new Array();
                      emprelieved=new Array();
                     // alert(service.length);
                      dateOfRetirement=new Array();
                      if(service.length>1)
                      {
                      for(i=0;i<service.length;i++)
              
                          {
                            
                         empid[i]=service[i].getElementsByTagName("empid")[0].firstChild.nodeValue;
                      //   alert(empid[i]);
                         empname[i]=service[i].getElementsByTagName("empname")[0].firstChild.nodeValue;
                          empstatus[i]=service[i].getElementsByTagName("empstatus")[0].firstChild.nodeValue;
                          empoffice[i]=service[i].getElementsByTagName("empoffice")[0].firstChild.nodeValue;
                          //alert(empoffice[i])
                          if(empoffice[i]=='null')
                            empoffice[i]="";
                          else  
                            empoffice[i]=empoffice[i];
                          empdob[i]=service[i].getElementsByTagName("empdob")[0].firstChild.nodeValue;
                          if(empdob[i]=='null')
                            empdob[i]="";
                          else
                            {
                            var dob=empdob[i].split('-');
                            dob=dob[2]+"/"+dob[1]+"/"+dob[0];
                            //alert(dob);
                            empdob[i]=dob;
                            }
                         // dateOfRetirement[i]=service[i].getElementsByTagName("empdor")[0].firstChild.nodeValue;
                          //alert("inside getting:"+dateOfRetirement[i]);
                          empdor[i]=service[i].getElementsByTagName("empdor")[0].firstChild.nodeValue;
                          
                          if(empdor[i]=='null')
                            empdor[i]="";
                          else
                            {
                            var dor=empdor[i].split('-');
                            dor=dor[2]+"/"+dor[1]+"/"+dor[0];
                            //alert(dor);
                            empdor[i]=dor;
                            }
                          empoffid[i]=service[i].getElementsByTagName("empofficeid")[0].firstChild.nodeValue;
                          if(empoffid[i]==0)
                            empoffid[i]="";
                          else  
                            empoffid[i]=empoffid[i];
                          emprelieved[i]=service[i].getElementsByTagName("relieved")[0].firstChild.nodeValue;
                          
                        // alert("ssss"+empoffid[i]);
                       }
                       }
                       else
                       {
                       //alert('only one details in service')
                         empid[0]=service[0].getElementsByTagName("empid")[0].firstChild.nodeValue;
                         //alert(empid[i]);
                         empname[0]=service[0].getElementsByTagName("empname")[0].firstChild.nodeValue;
                          empstatus[0]=service[0].getElementsByTagName("empstatus")[0].firstChild.nodeValue;
                          empoffice[0]=service[0].getElementsByTagName("empoffice")[0].firstChild.nodeValue;
                          //alert(empoffice[i])
                          if(empoffice[0]=='null')
                            empoffice[0]="";
                          else  
                            empoffice[0]=empoffice[0];
                          empdob[0]=service[0].getElementsByTagName("empdob")[0].firstChild.nodeValue;
                          if(empdob[0]=='null')
                            empdob[0]="";
                          else
                            {
                            var dob=empdob[0].split('-');
                            dob=dob[2]+"/"+dob[1]+"/"+dob[0];
                            //alert(dob);
                            empdob[0]=dob;
                            }
                          empdor[0]=service[0].getElementsByTagName("empdor")[0].firstChild.nodeValue;
                          if(empdor[0]=='null')
                            empdor[0]="";
                          else
                            {
                            var dor=empdor[0].split('-');
                            dor=dor[2]+"/"+dor[1]+"/"+dor[0];
                            //alert(dor);
                            empdor[0]=dor;
                            }
                          empoffid[0]=service[0].getElementsByTagName("empofficeid")[0].firstChild.nodeValue;
                          if(empoffid[0]==0)
                            empoffid[0]="";
                          else  
                            empoffid[0]=empoffid[0];
                          emprelieved[0]=service[0].getElementsByTagName("relieved")[0].firstChild.nodeValue;
                          
                        // alert("ssss"+empoffid[i]);
                       
                       }
                      }
                 // alert('ok');
                  loadPage(baseRes);
                }
                else
                {
                alert('No data Found');
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


function loadPage(baseRes)
{
//alert('eight')
var tbody=document.getElementById("grid_body");
    try
    {tbody.innerHTML="";}
    catch(e) 
    {tbody.innerText="";}
    var i=0;    
    //alert('check')
    //alert(empid.length)
    var j=1;
    if(empid.length>1)
    {
    for( i=0;i<empid.length;i++)
    {
    //alert('after eiight1')
    
          var mycurrent_row=document.createElement("TR");
          mycurrent_row.id=j;
         // alert("current row id:"+mycurrent_row.id);
          var cell1=document.createElement("TD");
          var sel="";            
          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
          {
        //  alert('here is err')
            sel=document.createElement("<INPUT type='checkbox' name='sell' id="+(i+1)+"   value="+empid[i]+" onmouseover=checkEmployee(this,this.id)"+" onclick=checkEmp(this,this.id)"+">");
       //     alert('after here')
       
       //alert("checkboxid is"+sel.id);
          // alert("checkbox value is"+sel.value);
          }
          else
          {  
        //  alert('check here')
           sel=document.createElement("input");     // serial number generation
           sel.type="checkbox";             
          // sel.name="sell["+i+"]";
            sel.name="sell";
           sel.id=i+1;
           sel.value=empid[i];
           //alert(sel.name);
         //  alert(sel.value);
          // alert("checkboxid is"+sel.id);
         //  alert("checkbox value is"+sel.value);
           sel.setAttribute('onmouseover','checkEmployee(this,'+sel.id+')');
           sel.setAttribute('onclick','checkEmp(this,'+sel.id+')');
           //sel.value=j;                          
          }
          cell1.appendChild(sel);
          mycurrent_row.appendChild(cell1);
        
          
          var cell2=document.createElement("TD");
          //alert(empid[i]+"aaa");
          var txtempid=document.createTextNode(empid[i]);
          cell2.appendChild(txtempid);
          mycurrent_row.appendChild(cell2);
          
          var cell3=document.createElement("TD");
          cell3.setAttribute('align','left');
          var txtempname=document.createTextNode(empname[i]);
          cell3.appendChild(txtempname);
          mycurrent_row.appendChild(cell3);
          
          var cell4=document.createElement("TD");
          var txtempstatus=document.createTextNode(empdob[i]);
          cell4.appendChild(txtempstatus);
          mycurrent_row.appendChild(cell4);
          
          var cell5=document.createElement("TD");
          var txtempofficeid=document.createTextNode(empdor[i]);
          cell5.appendChild(txtempofficeid);
          mycurrent_row.appendChild(cell5);
          
          var cell6=document.createElement("TD");
          var txtempoffice=document.createTextNode(empstatus[i]);
          cell6.appendChild(txtempoffice);
          mycurrent_row.appendChild(cell6);
          
          var cell7=document.createElement("TD");
          var txtempdob=document.createTextNode(emprelieved[i]);
          cell7.appendChild(txtempdob);
          mycurrent_row.appendChild(cell7);
          
          var cell8=document.createElement("TD");
          var txtempdor=document.createTextNode(empoffid[i]);
          cell8.appendChild(txtempdor);
          mycurrent_row.appendChild(cell8);
     /*     var hiddor=document.createElement("input");
         // alert('1')
          hiddor.type="hidden";
          hiddor.id='hiddenDor';
          hiddor.name='hiddenDor';
          hiddor.value=dateOfRetirement[i];
          //alert('2')
           var hidempdor=document.createTextNode(dateOfRetirement[i]);
           alert(hidempdor)
          hiddor.appendChild(hidempdor);
          mycurrent_row.appendChild(hiddor);*/
          
          var cell9=document.createElement("TD");
          var txtemprelieved=document.createTextNode(empoffice[i]);
          cell9.appendChild(txtemprelieved);
          mycurrent_row.appendChild(cell9);
          
          tbody.appendChild(mycurrent_row);
          j++;
       
    }  
    }
    else
    {
   // alert('only one emp')
     var mycurrent_row=document.createElement("TR");
          mycurrent_row.id=0;
          //alert(mycurrent_row.id);
          var cell1=document.createElement("TD");
          var sel="";            
          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
          {
           // sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel'   value="+j+" >");
           sel=document.createElement("<INPUT type='checkbox' name='sell' id=0"+"   value="+empid[0]+" onmouseover=checkEmployee(this,this.id)"+" onclick=checkEmp(this,this.id)"+">");
          }
          else
          {    
           sel=document.createElement("input");     // serial number generation
           sel.type="checkbox";             
          // sel.name="sell["+i+"]";
            sel.name="sell";
           sel.id=0;
           sel.value=empid[0];
           //alert(sel.name);
         //  alert(sel.value);
          // alert("checkboxid is"+sel.id);
          // alert("checkboxid is"+sel.value);
           sel.setAttribute('onmouseover','checkEmployee(this,'+sel.id+')');
           //sel.value=j;                          
          }
          cell1.appendChild(sel);
          mycurrent_row.appendChild(cell1);
        
          
          var cell2=document.createElement("TD");
         // alert(empid[i]+"aaa");
          var txtempid=document.createTextNode(empid[0]);
          cell2.appendChild(txtempid);
          mycurrent_row.appendChild(cell2);
          
          var cell3=document.createElement("TD");
          var txtempname=document.createTextNode(empname[0]);
          cell3.appendChild(txtempname);
          mycurrent_row.appendChild(cell3);
          
          var cell4=document.createElement("TD");
          var txtempstatus=document.createTextNode(empdob[0]);
          cell4.appendChild(txtempstatus);
          mycurrent_row.appendChild(cell4);
          
          var cell5=document.createElement("TD");
          var txtempofficeid=document.createTextNode(empdor[0]);
          cell5.appendChild(txtempofficeid);
          mycurrent_row.appendChild(cell5);
          
          var cell6=document.createElement("TD");
          var txtempoffice=document.createTextNode(empstatus[0]);
          cell6.appendChild(txtempoffice);
          mycurrent_row.appendChild(cell6);
          
          var cell7=document.createElement("TD");
          var txtempdob=document.createTextNode(emprelieved[0]);
          cell7.appendChild(txtempdob);
          mycurrent_row.appendChild(cell7);
          
          var cell8=document.createElement("TD");
          var txtempdor=document.createTextNode(empoffid[0]);
          cell8.appendChild(txtempdor);
          mycurrent_row.appendChild(cell8);
          
          var cell9=document.createElement("TD");
          var txtemprelieved=document.createTextNode(empoffice[0]);
          cell9.appendChild(txtemprelieved);
          mycurrent_row.appendChild(cell9);
          
          tbody.appendChild(mycurrent_row);
          j++;
    }
    document.getElementById("btnRelieve").disabled=true;
}





function checkEmployee(t,selid)
{
var flag=true;
//alert(selid)
//var flag=true;
//alert(emprelieved[selid])
if(selid>=1)
{

    if(emprelieved[selid-1]=='yes')
    {
    //alert('yes')
    document.frmEmployeeRelievalOnRetirement.sell[selid-1].disabled=true;
   // alert('checkbox disabled')
    document.getElementById("btnRelieve").disabled=true;
    //alert('relieval disabled')
   // document.frmEmployeeRelievalOnRetirement.sell[selid-1].checked=false;
    //alert("Employee Already Relieved");
   // alert('2')
    }
   else if(emprelieved[selid-1]=='no')
   {
   document.getElementById("btnRelieve").disabled=false;
   }
   // alert('check');
   // alert(empid.length);
   // for(var i=0;i<empid.length;i++)
    //   {
       //alert('inside for')
       if(document.frmEmployeeRelievalOnRetirement.sell[selid-1].disabled==true)
            {
            //alert('disabled')
             flag==false;
             //break;
            }
       else
            {
            //alert('enabled')
             flag=true;
            // break;
            }     
           
      // }
       if(flag==true)
         {
            document.getElementById("btnGo").disabled=true;
             document.getElementById("btnRelieve").disabled=true;
         }
       else
         {
         document.getElementById("btnGo").disabled=false;
             document.getElementById("btnRelieve").disabled=false;
         }
   /*    for(var i=0;i<empid.length;i++)
       {
        if(document.frmEmployeeRelievalOnRetirement.sell[i].checked==true)
            {
           // alert('checked')
             document.getElementById("btnGo").disabled=false;
             document.getElementById("btnRelieve").disabled=false;
            }
        }    */
}
else
    {
    //alert('check')
    if(emprelieved[0]=='yes')
    {
    //alert('only one yes')
    document.frmEmployeeRelievalOnRetirement.sell.disabled=true;
    document.frmEmployeeRelievalOnRetirement.sell.checked=false;
    document.getElementById("btnGo").disabled=true;
    document.getElementById("btnRelieve").disabled=true;
    //alert("Employee Already Relieved");
    //alert('2')
    }
    else
      document.getElementById("btnRelieve").disabled=false;
    }
}

function getDaysInMonth(year, month) {
    return [31,((!(year % 4 ) && ( (year % 100 ) || !( year % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31][month-1];
  }
  
function validateMonth()
{
document.getElementById("btnRelieve").disabled=true;
var tbody=document.getElementById("grid_body");
                    try
                    {tbody.innerHTML="";}
                    catch(e) 
                    {tbody.innerText="";}
/*var hidDateOfRet=document.getElementById("hiddenDor").value;
alert(hidDateOfRet);
var lastday=hidDateOfRet.getDate();
alert("last day is:"+lastday); */

//isLeapYear(yr) ( ((yr) % 4 == 0) && ( ((yr) % 100 != 0) || ((yr) % 400 == 0) ) ) 
var curdate=new Date();
//alert("curremt date is:"+curdate)
var date=curdate.getDate();
var month=curdate.getMonth()+1;
var year=curdate.getYear();
var day=curdate.getDay();
//alert("current day is:"+day);
//alert(date+' '+month+' '+year);


var selMonth=document.getElementById("cmbmonth").value;
//alert("selected month:"+selMonth);
var selYear=document.getElementById("cmbyear").value;
//alert("selected year:"+selYear)
var daysInMonth = getDaysInMonth(year, month);

//alert("Days in month:"+daysInMonth);

/*if((selMonth<month&&selYear<=year) ||(selMonth==month&&selYear<year) )
    {
        document.getElementById("btnGo").disabled=false;
        document.getElementById("btnRelieve").disabled=false;
    }
else  if(selMonth>month&&selYear==year)  
    {
        document.getElementById("btnGo").disabled=true;
        document.getElementById("btnRelieve").disabled=true;
        alert('Month should not exceed current month');
    }
else if (selMonth==month&&selYear==year) 
    {
        if(lastday==6)
            daysInMonth=daysInMonth-1;
        else if(lastday==7)
            daysInMonth=daysInMonth-2;
        else 
            daysInMonth=daysInMonth;
            
        if(date>=daysInMonth) 
            {
                 document.getElementById("btnGo").disabled=true;
                 document.getElementById("btnRelieve").disabled=true;
            }
        else 
            {
            document.getElementById("btnGo").disabled=false;
            document.getElementById("btnRelieve").disabled=false;
            alert('Relieve the employees at the end of this month');
            }
            
    }*/

        if((selYear<year && selMonth>month)||(selYear<year && selMonth<month)||
          (selYear==year && selMonth<month))
          {
          //alert('1')
          document.getElementById("btnGo").disabled=false;
          //document.getElementById("btnRelieve").disabled=false;
          }
       
        else if((selYear==year && selMonth>month))
            {
                    //alert('2')
                 document.getElementById("btnGo").disabled=true;
                 document.getElementById("btnRelieve").disabled=true;
                 alert('Month should not exceed current month');
            } 
        else  if(selYear==year && selMonth==month)
          {
          //alert("date"+date+" "+daysInMonth);
          //alert('3')
              if(date<daysInMonth)
                {
                //alert('a')
                    document.getElementById("btnGo").disabled=true;
                    document.getElementById("btnRelieve").disabled=true;
                    alert('You can Relieve the employees on '+daysInMonth+'only..');
                    
                }
               else if(date==daysInMonth) 
               {
               //alert('b')
               document.getElementById("btnGo").disabled=false;
                    document.getElementById("btnRelieve").disabled=false;
               }
               else
                {
                //alert('c')
                  document.getElementById("btnGo").disabled=true;
                  document.getElementById("btnRelieve").disabled=true;
                  alert('You can Relieve the employees on '+daysInMonth+'only..');
                }
          }
          else
          {
         // alert('4')
          document.getElementById("btnGo").disabled=false;
                  document.getElementById("btnRelieve").disabled=true;
          }

}


function checkEmp(t,selid)
{
//document.getElementById("btnRelieve").disabled=false;
//alert('cdd')
var flag=true;
//alert(selid)
//if(selid!=0)
   // {
     //   for(var i=0;i<empid.length;i++)
     //  {
      /*  if(document.frmEmployeeRelievalOnRetirement.sell[selid-1].checked==true)
            {
           alert('checked')
             document.getElementById("btnGo").disabled=false;
             document.getElementById("btnRelieve").disabled=false;
             alert('after checked')
            }*/
         
      //  }  
  //  }
   
/*   if(selid>0)
    {
       for(var i=0;i<empid.length;i++)
       {
        if(document.frmEmployeeRelievalOnRetirement.sell[i].checked==true)
            {
           alert('checked')
             document.getElementById("btnGo").disabled=false;
             document.getElementById("btnRelieve").disabled=false;
            }
         else
            {
                document.getElementById("btnGo").disabled=true;
                document.getElementById("btnRelieve").disabled=true;
            }
        }  
    }*/
    
if(selid>=1)
{    
 for(var i=0;i<empid.length;i++)
       {
       //alert('inside for')
       if(document.frmEmployeeRelievalOnRetirement.sell[i].checked==true)
            {
            //alert('disabled')
             flag==false;
            }
       else
            {
            //alert('enabled')
             flag=true;
            } 
       if(flag==true)
         {
             document.getElementById("btnGo").disabled=false;
             document.getElementById("btnRelieve").disabled=false;
         }
       else
         {
         document.getElementById("btnGo").disabled=true;
             document.getElementById("btnRelieve").disabled=true;
         }     
           
       }
  }
  else
  {
  //alert('check')
       //alert('only one yes')
       if(document.frmEmployeeRelievalOnRetirement.sell.checked==true)
            {
            //alert('disabled')
             flag==false;
            }
       else
            {
            //alert('enabled')
             flag=true;
            } 
       if(flag==true)
         {
         //alert('1')
             document.getElementById("btnGo").disabled=false;
             document.getElementById("btnRelieve").disabled=false;
         }
       else
         {
         //alert('2')
         document.getElementById("btnGo").disabled=true;
             document.getElementById("btnRelieve").disabled=true;
         } 
  //  document.frmEmployeeRelievalOnRetirement.sell.disabled=true;
 //   document.frmEmployeeRelievalOnRetirement.sell.checked=false;
 //   document.getElementById("btnGo").disabled=true;
  //  document.getElementById("btnRelieve").disabled=true;
    //alert("Employee Already Relieved");
    //alert('2')
   
  }
}


function check()
{
var leng=parseInt(document.frmEmployeeRelievalOnRetirement.sell.length);

//alert(empid.length);
var TotalBoxes = 0;
  var TotalOn = 0;
  for (var i=0;i<leng;i++) {
    var e = document.frmEmployeeRelievalOnRetirement.sell[i];
    if ((e.type=='checkbox')) {
      TotalBoxes++;
      if (e.checked) {
       TotalOn++;
      }
    }
  }
  if(TotalOn==0)
    {
    //alert("total on is"+TotalOn)
    alert('Select the Employee Id first..');
    return false;
    }
 // alert("total on isaaaaa"+TotalOn)
    return true;  
  
/*  if (TotalBoxes==TotalOn) {
    //document.frmEmployeeRelievalOnRetirement.sell.checked=true;
    
  }
  else {
  
  // document.frmEmployeeRelievalOnRetirement.sell.checked=false;
  }*/

}