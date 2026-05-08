//alert("called");
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


function Exit()
{
    window.open('','_parent','');
    window.close();
}

 
/*function clearAll()
{
   document.frmconfirm.txtmonth.disabled=true;
    document.frmconfirm.txtyear.disabled=false;
   document.frmconfirm.txtyear.value="";
   document.frmconfirm.txtmonth.selectedIndex=0;
   var idmonth=document.getElementById("txtmonth");
   try
   {
   idmonth.innerHTML="";
   }
   catch(e)
   {
   idmonth.innerText="";
   }
   }
  // document.frmconfirm.txtmonth.disabled=false;
   
  /* document.frmconfirm..selectedIndex=0;
   document.frmconfirm.Employee_Prefix.selectedIndex=0;
   document.frmconfirm.Employee_Prefix.selectedIndex=0;
   document.frmconfirm.Employee_Prefix.selectedIndex=0;
   document.frmconfirm.Employee_Prefix.selectedIndex=0;
   document.frmconfirm.Employee_Prefix.selectedIndex=0;
   document.frmconfirm.Employee_Prefix.selectedIndex=0;
   document.frmconfirm.txtEmpId1.disabled=false;
   document.frmconfirm.txtEmpId1.focus();
   
   document.frmconfirm.cmdSub.disabled=false;*/
  



 

function bodyload()
{
 //document.frmconfirm.txtyear.focus();
}

function Validate(val1,val2)
{
    //alert('The Values are '+val1+' and '+val2);
  var txtOffId=document.frmconfirm.txtOffId.value;
   /*  var year=document.frmconfirm.txtyear.value;
     var month=document.frmconfirm.txtmonth.value;*/
        url="../../../../../ConfirmHR.av?Command=Valid&txtOffId="+txtOffId+"&year="+val1+ "&month=" + val2 ;
       // alert(url);
        
         var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
            processResponse(req);
        }          
        req.send(null);     
    
}
 
 
function callServer(command,param)
{

    var txtOffId=document.frmconfirm.txtOffId.value;
   // alert(txtOffId);
    if(command=="Add")
    {
    var d=0;
        var nflag=nullCheck();
        if(nflag==true)
        {
            var year=document.frmconfirm.txtyear.value;
            var month=document.frmconfirm.txtmonth.value;
          var tbody=document.getElementById("tb");
     record1=new Array();
     record2=new Array();
    var d=0;
 for(var i=1;i<=tbody.rows.length;i++)
            {
            var r=document.getElementById(i);
           
                var rcells=r.cells;
                record1[i]=rcells.item(1).firstChild.nodeValue;
                record2[i]=rcells.item(2).lastChild.value;
                if(record1[i]==year && record2[i]==month)
                {
                alert("You have already inserted ");
                d=1;
                }
               
                }
               if(d==0)
                {
                
            if(document.frmconfirm.allupdate[0].checked==true)
            allupdate=document.frmconfirm.allupdate[0].value;
            else
            allupdate=document.frmconfirm.allupdate[1].value;
            
            
            
            url="../../../../../ConfirmHR.av?Command=Add&year="+year+ "&month=" + month + "&allupdate=" + allupdate +"&txtOffId="+txtOffId;
           // alert(url);   
            }
                
    }
    }
    else if(command=='load')
    {
        url="../../../../../ConfirmHR.av?Command=Load"+"&txtOffId="+txtOffId;
       // alert(url);
    }
     
    
    else if(command=='Delete')
    {
       
        //alert("delete");
        var year=document.frmconfirm.txtyear.value;
        var month=document.frmconfirm.txtmonth.value;
        url="../../../../../ConfirmHR.av?Command=Delete"+"&txtOffId="+txtOffId+"&year="+year+ "&month=" + month;
        //alert(url);
    }
    else if(command=='Update')
    {
        var year=document.frmconfirm.txtyear.value;
        var month=document.frmconfirm.txtmonth.value;
        
         if(document.frmconfirm.allupdate[0].checked==true)
            allupdate=document.frmconfirm.allupdate[0].value;
        else
            allupdate=document.frmconfirm.allupdate[1].value;
            
            // alert(document.frmconfirm.proc_flow_status.value);
             
        proc_flow_status=document.frmconfirm.proc_flow_status.value;           
        var flag=nullCheck();       
        if(flag==true)
        {  
            //startwaiting(document.frmconfirm) ; 
            url="../../../../../ConfirmHR.av?Command=update&year="+year+ "&month=" + month + "&allupdate=" + allupdate +"&txtOffId="+txtOffId+"&proc_flow_status="+proc_flow_status;
          //  alert(url);        
        }
        }
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
            processResponse(req);
        }   
        
        req.send(null);     
}


function processResponse(req)
{   
    if(req.readyState==4)
    {
      if(req.status==200)
      {       
          
         //stopwaiting(document.frmconfirm) ;
   
        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        var tagCommand=baseResponse.getElementsByTagName("command")[0];
        var Command=tagCommand.firstChild.nodeValue; 
   
           if(Command=="Add")
           {
                  
             insertRow(baseResponse);     
              Load(baseResponse);                
           }
           if(Command=="Update")
           {
                  
             UpdateRow(baseResponse);     
                            
           }
           else if(Command=="sessionout")
           {
             alert('Session is closed');
   
            try
            {
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
            }
            catch(e){}
            
            self.close();
            return;
         }
           
         else if(Command=="Load")
         {
            Load(baseResponse);
         }
         else if(Command=="Delete")
         {
            deleteRow(baseResponse);
         }
          else if(Command=="Valid")
         {
            Valid(baseResponse);
         }
              
          }
        }
        
        
  }


function insertRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
    
     alert("Record has inserted successfully");
      callServer('load','null');

    }

}
function Valid(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
    
     alert("Record Freezed successfully");
     callServer('load','null');   

    }

}

function deleteRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     document.frmconfirm.txtmonth.disabled=false;
    document.frmconfirm.txtyear.disabled=false;
    if(flag=="success")
    {
    
     alert("Record has Deleted successfully");
     callServer('load','null');   

    }

}
function UpdateRow(baseResponse)
{
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     document.frmconfirm.txtmonth.disabled=false;
    document.frmconfirm.txtyear.disabled=false;
    if(flag=="success")
    {
    
     alert(" Record has Updated successfully");
      callServer('load','null'); 

    }

}
 function Load(baseResponse)
 { //alert("Load");
 clearfun();
                document.frmconfirm.cmdupdate.style.display="block";
                document.frmconfirm.cmdadd.disabled=false;
                document.frmconfirm.cmdupdate.disabled=true;
                document.frmconfirm.cmddelete.disabled=true;
           // alert("load handlwersffffffffffffffff");
                   // loadPage(1)
                   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
      if(flag=="success")
      {
                   var tbody=document.getElementById("tb");
                   try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                   var service =baseResponse.getElementsByTagName("year");
                   //alert(service.length);
                   
                   //var mycurrent_row=document.createElement("TR");
                 var j;                 
                   for(i=0;i<service.length;i++)
                   
                {
                j=i+1;
                        var year = baseResponse.getElementsByTagName("year")[i].firstChild.nodeValue;
                       // alert("empid"+EMPLOYEE_ID);
                        
                        var month =baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
                        var month_arry= new Array(5)
                                month_arry[1]="January";
                                month_arry[2]="Febuary";
                                month_arry[3]="March";
                                month_arry[4]="April";
                                month_arry[5]="May";
                                month_arry[6]="June";
                                month_arry[7]="July";
                                month_arry[8]="August";
                                month_arry[9]="September";
                                month_arry[10]="October";
                                month_arry[11]="November";
                                month_arry[12]="December";
                        
                        var allupdate=baseResponse.getElementsByTagName("allupdate")[i].firstChild.nodeValue;
                        var processid=baseResponse.getElementsByTagName("processid")[i].firstChild.nodeValue;
                        if(allupdate=='N')
                          allupdate='No'
                          else
                          allupdate='Yes';
                        
                         var mycurrent_row=document.createElement("TR");
                        {
                            mycurrent_row.id=j;
                            var cell=document.createElement("TD");                            
                            var anc=document.createElement("A");
                            anc.id="rid"+i;
                            var url="javascript:loadTable('"+j+"')";
                            anc.href=url;
                            var txtedit
                               if(processid=='FR')
                                    {
                                     txtedit=document.createTextNode("Revalidate");   
                                    }
                                else
                                    txtedit=document.createTextNode("Edit");
                                     
                           
                            anc.appendChild(txtedit);
                            cell.appendChild(anc);
                            mycurrent_row.appendChild(cell);
                        }
                        
                        var descell=document.createElement("TD");                         
                         var sn=document.createTextNode(year);                         
                      descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                       
                         
                         
                         var descell=document.createElement("TD");
                         
                         var sn=document.createTextNode(month_arry[month]);
                         descell.appendChild(sn);
                          var sc=document.createElement("INPUT");
                           sc.type="hidden";
                         sc.name="month";
                         sc.text=month;
                         sc.value=month;
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);
                         
                         
                         var descell=document.createElement("TD");
                         var sn=document.createTextNode(allupdate);
                         descell.appendChild(sn);
                          var sc=document.createElement("INPUT");
                           sc.type="hidden";
                         sc.name="allupdate";
                         sc.text=allupdate;
                         sc.value=allupdate;
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);
                         
                       //  alert("load i="+i);
                         
                          var descell=document.createElement("TD"); 
                         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                {
                                others=document.createElement("<INPUT type='button' name='sub"+i+"' id='sub"+i+"' value='Validate' onclick='Validate("+year+","+month+")' >");
                                }
                        else
                            {
                                others=document.createElement("input");
                                others.type="button";
                                others.name="sub";
                                others.id="sub"+i+"";
                               // alert(others.id);
                                others.value="Validate";
                                others.setAttribute("onclick","Validate("+year+","+month+")");
                            }
                    descell.appendChild(others);
                    mycurrent_row.appendChild(descell);

                        // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                        buttonid=document.getElementById('sub'+i);
                        if(processid=='FR')
                        {
                      document.getElementById('sub'+i).disabled=true;
                   // document.getElementById('rid'+i).style.display='none';    
                        }
                       // alert(buttonid);
                     //   tbody.appendChild(mycurrent_row);
                       
                }
           }
         
}
         
    
    





  
   



function nullCheck()
{
 if(document.frmconfirm.txtyear.value=="")
   {
      alert("Please Enter the Year");
      document.frmconfirm.txtyear.focus();
      return false;
   }
   
   
   if(document.frmconfirm.txtmonth.value=="")
   {
      alert("Select the Month");
      document.frmconfirm.txtmonth.focus();
      return false;
   }
   
   /*else if(document.frmconfirm.Employee_Initial.value=="")
   {
     alert("Please Enter the Employee Initial");
      return false;
   }*/
   
   /*
   else if(document.frmconfirm.Gpf_Number.value.length==0)
   {
     alert("Please Enter the GPF Number");
     return false;
   }*/
   /*
   if(document.frmconfirm.txtEmpId1.value!=document.frmconfirm.Gpf_Number.value)
   {
         alert("Please Enter the Correct GPF Number");
         document.frmconfirm.Gpf_Number.focus();
         return false;
    }*/
  
  return true;
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
     

     
 
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
                
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

function monthcall()
{
            var month_arry= new Array(5)
            month_arry[1]="January";
            month_arry[2]="Febuary";
            month_arry[3]="March";
            month_arry[4]="April";
            month_arry[5]="May";
            month_arry[6]="June";
            month_arry[7]="July";
            month_arry[8]="August";
            month_arry[9]="September";
            month_arry[10]="October";
            month_arry[11]="November";
            month_arry[12]="December";
            var asd=new Date();
            var year1=asd.getYear();
            
            if(year1 < 1900)
            year1 += 1900;
                // alert("date1"+date1+"/"+month1+"/"+year1);
                if(document.frmconfirm.txtyear.value.length!=0)
                {
                var year=document.frmconfirm.txtyear.value;
                
                if(year>2005 && year<=year1)
                {
            if( year==year1)
            {
             mon=asd.getMonth()+1;
            }
            else
            mon=12;
            var id=document.getElementById("txtmonth");
            try{
            id.innerHTML="";
            }
            catch(e)
            {
            id.innerText="";
            }
            option_var=document.createElement("OPTION");
            option_var.text="--Select the Month--";
            try
            {
            id.add(option_var);
            
            }
            catch(Exception)
            {
            id.add(option_var,null);
            }
            for(var i=1;i<=mon;i++)
            {
            var option_var=document.createElement("OPTION");
            option_var.text=month_arry[i];
            option_var.value=i;
            //alert(mon);
            
            try
            {
            id.add(option_var);
            
            }
            catch(Exception)
            {
            id.add(option_var,null);
            }
            }
            }
            else
            {
            alert("Enter the Valid Year");
            document.frmconfirm.txtyear.focus();
            }
            }  
} 
function loadTable(scod)
{
    
    
    document.frmconfirm.txtmonth.disabled=true;
    document.frmconfirm.txtyear.disabled=true;
   // clr();
    document.frmconfirm.cmdupdate.style.display="block";
    document.frmconfirm.cmdupdate.disabled=false;
                document.frmconfirm.cmdadd.disabled=true;
                document.frmconfirm.cmdclear.disabled=false;
                document.frmconfirm.cmddelete.disabled=false;
    var r=document.getElementById(scod);
    var rcells=r.cells;
      var i=parseInt(scod);
      i=i-1;// get id value for validate button
    //  alert("i="+i);
       if(document.getElementById('sub'+i).disabled==true)
           {
           document.frmconfirm.proc_flow_status.value='FR';
           
           }
       else
          {
          
          document.frmconfirm.proc_flow_status.value='MD';
          }
    try{
           /* document.frmconfirm.txtSNo1.value=rcells.item(1).lastChild.nodeValue;
             document.frmconfirm.txtSNo.value=rcells.item(1).firstChild.value;*/
             
            document.frmconfirm.txtyear.value=rcells.item(1).firstChild.nodeValue;
            monthcall();
            document.frmconfirm.txtmonth.value=rcells.item(2).lastChild.value;
            // alert(rcells.item(3).lastChild.value);
            if(rcells.item(3).lastChild.value=='Yes')
                document.frmconfirm.allupdate[0].checked=true;
                else
                document.frmconfirm.allupdate[1].checked=true;
                        
             
            // alert(document.frmconfirm.txtSNo);
      }
      
      catch(e)
      {
              alert("error");
      }
      
}

function clearfun()
{
    document.frmconfirm.txtyear.value="";
    document.frmconfirm.txtmonth.selectedIndex=0;
    document.frmconfirm.allupdate[1].checked=true;
    document.frmconfirm.txtmonth.disabled=false;
    document.frmconfirm.txtyear.disabled=false;
    //document.frmconfirm.cmdupdate.style.display="block";
    document.frmconfirm.cmdadd.disabled=false;
    document.frmconfirm.cmdupdate.disabled=true;
    document.frmconfirm.cmddelete.disabled=false;    
    document.frmconfirm.cmdclear.disabled=true;
}

