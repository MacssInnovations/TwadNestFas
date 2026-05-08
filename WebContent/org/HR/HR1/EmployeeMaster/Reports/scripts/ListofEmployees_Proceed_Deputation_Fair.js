//alert('called')
var __pagination=2;
var totalblock=0;
var items5;
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
//alert('first');
var prono=document.frmListEmpProceed.cmbProceed.value;
var offid=document.frmListEmpProceed.txtOffId.value;
//alert(offid);
if(Command=='loadEmp')
  {
 // alert('second')
    var req=getTransport();
    var url="../../../../../../ListofEmployees_Proceed_Deputation_Fair?Command=loadEmployee&prono="+prono+"&officeid="+offid;
   // alert(url);
    req.open("Get",url,true);
   // alert(req);
    req.onreadystatechange=function()
      {
    //  alert('third')
      requestResponse(req);
      }
    req.send(null);    
  }
else if(Command=='save')   
  {
  var prono=document.frmListEmpProceed.txtPNo.value;
  //alert(prono);
  var req=getTransport();
    var url="../../../../../../ListofEmployees_Proceed_Deputation_Fair?Command=save&prono="+prono;
   // alert(url);
    req.open("Get",url,true);
    //alert(req);
    req.onreadystatechange=function()
      {
      //alert('third')
      requestResponse(req);
      }
    req.send(null);  
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
           //alert('six')
           var i=0;
            var flag=baseRes.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert(flag);
            if(flag=='success')    
                {
               /* prono=new Array();         
                      subj=new Array();         
                      refer=new Array();         
                      predesig=new Array();         
                      preoff=new Array();         
                      addlparaone=new Array();         
                      addlparatwo=new Array();  
                      copyto=new Array();  */
                //alert('seven')
                          //var  prono=baseRes.getElementsByTagName("pro_no")[0].firstChild.nodeValue;
                          var prdate=baseRes.getElementsByTagName("pr_date")[0].firstChild.nodeValue;
                          //alert(prdate);
                          if(prdate!='null')
                          {
                          var m=prdate.split('-');
                            //alert(m[0]+"U"+m[1]+"U"+m[2])
                            prdate=m[2]+"/"+m[1]+"/"+m[0];
                            }
                            else 
                            {
                            prdate="";
                            }
                            //alert("after "+prdate)
                          var subj=baseRes.getElementsByTagName("sub")[0].firstChild.nodeValue;
                          var refer=baseRes.getElementsByTagName("ref")[0].firstChild.nodeValue;
                          var preoff=baseRes.getElementsByTagName("pr_off")[0].firstChild.nodeValue;
                          var predesig=baseRes.getElementsByTagName("pr_desig")[0].firstChild.nodeValue;
                          var addlparaone=baseRes.getElementsByTagName("addlparaone")[0].firstChild.nodeValue;
                          var addlparatwo=baseRes.getElementsByTagName("addlparatwo")[0].firstChild.nodeValue;
                          var copyto=baseRes.getElementsByTagName("copyto")[0].firstChild.nodeValue;
                          
                  var service=baseRes.getElementsByTagName("details");
                 // var tbody=document.getElementById("grid_body");
                //  alert(service);
                  if(service)
                      {
                      empid=new Array();         
                      empname=new Array();
                      empdesig=new Array();
                      emprelstatus=new Array();
                      empprocessflow=new Array();
                    items5=new Array();
                     // if(service.length>1)
                     // {
                      for(i=0;i<service.length;i++)
              
                          {
                            
                         empid[i]=service[i].getElementsByTagName("empid")[0].firstChild.nodeValue;
                        // alert(empid[i]);
                         empname[i]=service[i].getElementsByTagName("empname")[0].firstChild.nodeValue;
                          empdesig[i]=service[i].getElementsByTagName("empdesig")[0].firstChild.nodeValue;
                          emprelstatus[i]=service[i].getElementsByTagName("emptrntooffice")[0].firstChild.nodeValue;
                          
                          empprocessflow[i]=service[i].getElementsByTagName("processflow")[0].firstChild.nodeValue;
                          if(empprocessflow[i]=='CR')
                                   empprocessflow[i]="Created";
                          else if(empprocessflow[i]=='MD')
                                    empprocessflow[i]="Modified";
                           else
                                   empprocessflow[i]='';
                            items5[i]=false;                      
                       
                       }
                       totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(empid.length>0)
                            {
                                    totalblock=parseInt(empid.length/__pagination);
                                    //alert("total block is"+totalblock);
                                    if(empid.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    }                                  
                                    loadPage(1);
                            }
                       
                       //}
                     /*  else
                       {
                       //alert('only one details in service')
                         empid[0]=service[0].getElementsByTagName("empid")[0].firstChild.nodeValue;
                         //alert(empid[i]);
                         empname[0]=service[0].getElementsByTagName("empname")[0].firstChild.nodeValue;
                          empdesig[0]=service[0].getElementsByTagName("empdesig")[0].firstChild.nodeValue;
                          emprelstatus[0]=service[0].getElementsByTagName("emprelstatus")[0].firstChild.nodeValue;
                         empprocessflow[0]=service[0].getElementsByTagName("processflow")[0].firstChild.nodeValue;
                         
                         
                       }*/
                       //loadPage(1);
                      }
                 // alert('ok');
        if(prdate==null)
        document.frmListEmpProceed.txtPDat.value="";
        else
        document.frmListEmpProceed.txtPDat.value=prdate;            

                 if(preoff=='null')
        document.frmListEmpProceed.txtPO.value="";
        else
        document.frmListEmpProceed.txtPO.value=preoff;
        
        if(predesig=='null')
        document.frmListEmpProceed.txtPODesig.value="";
        else
        document.frmListEmpProceed.txtPODesig.value=predesig;
        
        if(subj=='null')
        document.frmListEmpProceed.txtSubject.value="";
        else
        document.frmListEmpProceed.txtSubject.value=subj;
        
        if(refer=='null')
        document.frmListEmpProceed.txtRef.value="";
        else
        document.frmListEmpProceed.txtRef.value=refer;
        
        if(addlparaone=='null')
        document.frmListEmpProceed.txtPara1.value="";
        else
        document.frmListEmpProceed.txtPara1.value=addlparaone;
        
        if(addlparatwo=='null')
        document.frmListEmpProceed.txtPara2.value="";
        else
        document.frmListEmpProceed.txtPara2.value=addlparatwo;
        
        if(copyto=='null')
        document.frmListEmpProceed.txtCopy.value="";
        else
        document.frmListEmpProceed.txtCopy.value=copyto;
                // loadPage(baseRes);
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
            
            // loadPage(1);
            }
        }   
    }
}
  
  

function loadPage(page)
{

//alert('eight')
var i=0;
            var c=0;
            var p=__pagination*(page-1);
           // alert("p"+p)
             var tbody=document.getElementById("grid_body");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            document.frmListEmpProceed.cmbpage.selectedIndex=page-1;
           // alert(empid.length);
           // alert("pagination"+__pagination)

  //  if(empid.length>1)
  //  {
    for(i=p;i<empid.length && c<__pagination;i++)
    {
    //alert("c is:"+c)
    c++;
    var tbody=document.getElementById("grid_body");
    /*try
    {tbody.innerHTML="";}
    catch(e) 
    {tbody.innerText="";}*/
   // var i=0;    
    //alert('check')
    //alert(empid.length)
    var j=1;
   // for( i=0;i<empid.length;i++)
   // {
    //alert('after eiight1')
    
          var mycurrent_row=document.createElement("TR");
          mycurrent_row.id=j;
        
          
                         var descell=document.createElement("TD");
                         if(items5[i]==true)
                         {
                                 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                 {
                                 //alert("1")
                                    var sc=document.createElement("<INPUT type='checkbox' name='sel' id='sel" +i+"' checked  onclick='setTrue(this,"+i+")'>");
                                 }
                                 else
                                 {
                                 //alert("2")
                                 var sc=document.createElement("input");
                                 sc.type="checkbox";
                                 sc.name="sel";
                                 sc.id='sel' +i;
                                 sc.checked=true;
                                 sc.setAttribute('onclick','setTrue(this,'+i+')');
                                  //alert('setTrue(this,'+i+')');
                                 }
                                 
                        }
                        else
                        {
                                if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                 {
                                    var sc=document.createElement("<INPUT type='checkbox' name='sel' id='sel" +i+"' onclick='setTrue(this,"+i+")' >");
                                 }
                                 else
                                 {
                                 var sc=document.createElement("input");
                                 sc.type="checkbox";
                                 sc.name="sel";
                                 sc.id='sel' +i;
                                 sc.checked=false;
                                 sc.setAttribute('onclick','setTrue(this,'+i+')');
                                 //alert('setTrue(this,'+i+')');
                         }
                        
                        }
                       /*  var sc=document.createElement("input");
                         sc.type="checkbox";
                         sc.name="sel";
                         //sc.id='sel' +i;*/
                         
                         //sc.setAttribute("onclick","pick(this)");
                         sc.value=empid[i];
                         
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);
                         
                         var cbox=document.getElementById('sel'+i);
                         
                         
          
          var cell2=document.createElement("TD");
          var txtempid=document.createTextNode(empid[i]);
          cell2.appendChild(txtempid);
          mycurrent_row.appendChild(cell2);
          
          var cell3=document.createElement("TD");
          cell3.setAttribute('align','left');
          var txtempname=document.createTextNode(empname[i]);
          cell3.appendChild(txtempname);
          mycurrent_row.appendChild(cell3);
          
          var cell4=document.createElement("TD");
          var txtempdesig=document.createTextNode(empdesig[i]);
          cell4.appendChild(txtempdesig);
          mycurrent_row.appendChild(cell4);
          
          var cell5=document.createElement("TD");
          var txtempstatus=document.createTextNode(emprelstatus[i]);
          cell5.appendChild(txtempstatus);
          mycurrent_row.appendChild(cell5);
          
           var cell6=document.createElement("TD");
          var txtempprocessstatus=document.createTextNode(empprocessflow[i]);
          cell6.appendChild(txtempprocessstatus);
          mycurrent_row.appendChild(cell6);
                                      
          tbody.appendChild(mycurrent_row);
          j++;
      // }
    }  
           // alert(page<totalblock);
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
                cell.innerText= ' / ' +totalblock;
            if(page<totalblock)
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page+1)+")";
                anc.href=url;
                //anc.setAttribute('style','text-decoratin:none');
                var txtedit=document.createTextNode("<<Next>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divnext");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            }
             if(page>1)
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                //cell.innerText='';
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
                 var anc=document.createElement("A");
                var url="javascript:loadPage("+(page-1)+")";
                anc.href=url;
                var txtedit=document.createTextNode("<<Previous>>");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
            }
            else
            {
                var cell=document.getElementById("divpre");
                cell.style.display="block";
                try{cell.innerHTML="";}
                  catch(e) {cell.innerText="";}
            
            }
    //}
  // }
   
  /*  else
    {
   // alert('only one emp')
     var mycurrent_row=document.createElement("TR");
          mycurrent_row.id=0;
          //alert(mycurrent_row.id);
                  
          
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
          var txtempdesig=document.createTextNode(empdesig[0]);
          cell4.appendChild(txtempdesig);
          mycurrent_row.appendChild(cell4);
          
          var cell5=document.createElement("TD");
          var txtempstatus=document.createTextNode(emprelstatus[0]);
          cell5.appendChild(txtempstatus);
          mycurrent_row.appendChild(cell5);
          
           var cell6=document.createElement("TD");
          var txtempprocessstatus=document.createTextNode(empprocessflow[0]);
          cell6.appendChild(txtempprocessstatus);
          mycurrent_row.appendChild(cell6);
          
          tbody.appendChild(mycurrent_row);
          j++;
    }*/
   //} 
   
   // document.getElementById("btnRelieve").disabled=true;
}

  function checknull()
  {
  var prono=document.frmListEmpProceed.cmbProceed.value;
  var prdate=document.frmListEmpProceed.txtPDat.value;
  var preoff=document.frmListEmpProceed.txtPO.value;
  var predesig=document.frmListEmpProceed.txtPODesig.value;
 if (prono=="")
 {
   alert("Select Proceeding Number");
   return false;
 }  
 
 else if (prdate=="")
 {
   alert("Enter Proceeding Date");
   return false;
 }  
 
 else if (preoff=="")
 {
   alert("Enter the Name of Presiding Officer");
   return false;
 }  
 
 else if (predesig=="")
 {
   alert("Enter the Designation of Presiding Officer");
   return false;
 }
 else
   return true;
  }
  
  
  
function changepagesize()
{

           __pagination=document.frmListEmpProceed.cmbpagination.value;
            //var v=document.getElementsByName("sel");
            //alert(v);
        //if(v && items5)
       // {
           
                            totalblock=0;
                           
                            if(empid.length>0)
                            {
                                    totalblock=parseInt(empid.length/__pagination);
                                    if(empid.length%__pagination!=0)
                                    {
                                            totalblock=totalblock+1;
                                    }
                                    
                                    
                                    var cmbpage=document.getElementById("cmbpage");
                                   
                                   try{ cmbpage.innerHTML="";
                                   }catch(e){
                                    cmbpage.innerText="";
                                   }
                                     
                                    
                                    for(i=1;i<=totalblock;i++)
                                    {
                                            var option=document.createElement("OPTION");
                                            option.text=i;
                                            option.value=i;
                                            try
                                            {
                                                cmbpage.add(option);
                                            }catch(errorObject)
                                            {
                                            cmbpage.add(option,null);
                                            }
                                    } 
                            }
                             loadPage(1);
            
            
            
      //  }
           
}


  
function changepage()
{
var page=document.frmListEmpProceed.cmbpage.value;
loadPage(parseInt(page));

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


function clearAll()
{
var tbody=document.getElementById("grid_body");
    try
    {tbody.innerHTML="";}
    catch(e) 
    {tbody.innerText="";}
 document.frmListEmpProceed.cmbProceed.value="";
 document.frmListEmpProceed.txtPDat.value="";
 document.frmListEmpProceed.txtPO.value="";
 document.frmListEmpProceed.txtPODesig.value="";
 document.frmListEmpProceed.txtSubject.value="";
 document.frmListEmpProceed.txtPara1.value="";
 document.frmListEmpProceed.txtPara2.value="";
 document.frmListEmpProceed.txtCopy.value="";
 document.frmListEmpProceed.txtRef.value="";
 document.frmListEmpProceed.txtseloff.value="";
 document.getElementById("divcmbpage").style.display='none';
 document.getElementById("divpage").style.display='none';
 document.getElementById("divnext").style.display='none';
 document.getElementById("divpre").style.display='none';
 
 
 
}


function btnsubmit()
{
//alert('comes here')
var check=checknull();
//alert(check);
if(check)
{
//alert("comes here also")
var v=document.getElementsByName("sel");
var s='';

if(v && items5)
{
        for(i=0;i<items5.length;i++)
        {
                if(items5[i]==true)
                {
                    s=s+empid[i]+',';
                }
        }
        //alert(s);
        if(s=='')
        {
             alert('Select the office(s) ');
             return false; 
        }
        else
        {
            //alert(s.substring(0,s.length-1));
            document.frmListEmpProceed.txtseloff.value=s.substring(0,s.length-1);
            document.frmListEmpProceed.action="../../../../../../ListofEmployees_Proceed_Deputation_Fair";
          document.frmListEmpProceed.method="POST";
          document.frmListEmpProceed.submit();
            return true;
        }


}
else
{
           alert('Select the office(s) ');
           return false; 

}
}
else 
  return false;
}
  
  
  function setTrue(t,index)
{
    //alert(index);
    //alert(t);
    if(t.checked==true)
    items5[index]=true;
    else
    items5[index]=false;
    
}

function inverseselect()
{
document.frmListEmpProceed.chkall.checked=false;
        var v=document.getElementsByName("sel");
        if(v && items5)
        {
                for(i=0;i<items5.length;i++)
                {
                        if(items5[i]==true)
                        {
                            items5[i]=false;
                        }
                        else
                        {
                            items5[i]=true;
                        }
                }
                for(i=0;i<v.length;i++)
                {
                        if(v[i].checked==true)
                        {
                            v[i].checked=false;
                        }
                        else
                        {
                            v[i].checked=true;
                        }
                        
                }
           
        }

}


function sellectall()
{
        var v=document.getElementsByName("sel");
        if(v && items5)
        {
            if(document.frmListEmpProceed.chkall.checked==true)
            {
                for(i=0;i<items5.length;i++)
                {
                        items5[i]=true;
                }
                for(i=0;i<v.length;i++)
                {
                        v[i].checked=true;
                }
            }
            else
            {
            
                for(i=0;i<items5.length;i++)
                {
                        items5[i]=false;
                }
                for(i=0;i<v.length;i++)
                {
                        v[i].checked=false;
                }
            }
        }
}