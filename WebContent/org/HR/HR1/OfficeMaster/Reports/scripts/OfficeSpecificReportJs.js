//alert('specific report');
//alert( __pagination);
var __pagination=5;
  var items1;
    var items2;
    var items3;
    var items4;
    var items5;
    var items6;
    var totalblock=0;
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

 function getOfficesByLevel()
    {
        var level=document.OfficeSpecificReport.cmbControllingLevel.options[document.OfficeSpecificReport.cmbControllingLevel.selectedIndex].value;
        var levelt=document.OfficeSpecificReport.cmbControllingLevel.options[document.OfficeSpecificReport.cmbControllingLevel.selectedIndex].text;
       
        
        document.OfficeSpecificReport.chkall.checked=false;
        var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
         var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';
         items1=null;
         items2=null;
         items3=null;
         items4=null;
         items5=null;
         items6=null;
        if(level=="DN" || level=="SD" || level=="SN" )
        {
            var din=document.getElementById("divown");
            din.style.display="block";
            var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            document.OfficeSpecificReport.cmbOfficeType.focus();      
            
        }
        else
        {
           
            var din=document.getElementById("divown");
            din.style.display="none";
            if(level=="")
            {
                var tbody=document.getElementById("tb");
                try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                          
                 var cmbpage=document.getElementById("cmbpage");
                                   
               try{ cmbpage.innerHTML="";
               }catch(e){
                cmbpage.innerText="";
               }
                        
            }
            else
            {
            loadOfficesByLevel(level);
                    
            }
        }
        
    }
 
    function loadOfficesByLevel(level)
    {
        startwaiting(document.OfficeSpecificReport);
         
        var url="../../../../../../OfficeSpecificReportServ.rep?Command=level&level="+level;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadTable(req);
        }
        req.send(null);
    }
  
  
 
 function getOfficesByType()
    {
         startwaiting(document.OfficeSpecificReport);
         document.OfficeSpecificReport.chkall.checked=false;
         var cell=document.getElementById("divpre");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divcmbpage");
        cell.style.display="none";
        var cell=document.getElementById("divpage");
        cell.style.display="none";
        cell.innerText='';
        var cell=document.getElementById("divnext");
        cell.style.display="none";
        cell.innerText='';
        items1=null;
         items2=null;
         items3=null;
         items4=null;
         items5=null;
         items6=null;
        var own=document.OfficeSpecificReport.cmbOfficeType.options[document.OfficeSpecificReport.cmbOfficeType.selectedIndex].value;
        if(own=="0")
        {
             var cmbpage=document.getElementById("cmbpage");
                                       
           try{ cmbpage.innerHTML="";
           }catch(e){
            cmbpage.innerText="";
           }
        }
        else
        {
                var level=document.OfficeSpecificReport.cmbControllingLevel.options[document.OfficeSpecificReport.cmbControllingLevel.selectedIndex].value;
                var url="../../../../../../OfficeSpecificReportServ.rep?Command=own&level="+level+"&own="+own;
                var req=getTransport();
                req.open("GET",url,true);        
                req.onreadystatechange=function()
                {
                    loadTable(req);
                }
                req.send(null);
        }
       
    }
    
    
  
    
    
    function loadTable(req)
{

        if(req.readyState==4)
        {
          if(req.status==200)
          { 
            
               stopwaiting(document.OfficeSpecificReport);
               var response=req.responseXML.getElementsByTagName("response")[0];
                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                     document.OfficeSpecificReport.chkall.checked=false;
                    var cell=document.getElementById("divpre");
                    cell.style.display="none";
                    cell.innerText='';
                     var cell=document.getElementById("divcmbpage");
                    cell.style.display="none";
                    var cell=document.getElementById("divpage");
                    cell.style.display="none";
                    cell.innerText='';
                    var cell=document.getElementById("divnext");
                    cell.style.display="none";
                    cell.innerText='';                  
                    alert("No Offices exists under this criterial");
                    var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                }
                else
                {   
                
                    var service=response.getElementsByTagName("office");
                    if(service)
                    {
                    
                             var i=0;
                          var tbody=document.getElementById("tb");
                         
                          try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
                           
                          s=0;
                          items1=new Array();
                           items2=new Array();
                           items3=new Array();
                          items4=new Array();
                          items5=new Array();
                            for(i=0;i<service.length;i++)
                            {
                                
                                    var items=new Array();
                                    items1[i]=service[i].getElementsByTagName("offid")[0].firstChild.nodeValue;
                                    items2[i]=service[i].getElementsByTagName("offname")[0].firstChild.nodeValue;
                                    items3[i]=service[i].getElementsByTagName("offlevel")[0].firstChild.nodeValue;
                                    items4[i]=service[i].getElementsByTagName("offaddress")[0].firstChild.nodeValue;
                                    // items4[i]=service[i].getElementsByTagName("offaddress")[0].firstChild.nodeValue;
                                    items5[i]=false;
                                   
                                   
                            }
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(items1.length>0)
                            {
                                    totalblock=parseInt(items1.length/__pagination);
                                    if(items1.length%__pagination!=0)
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
                            
                       
                }
                
        }
    }
    }
       

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

function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
             var tbody=document.getElementById("tb");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
            document.OfficeSpecificReport.cmbpage.selectedIndex=page-1;
            for(i=p;i<items1.length && c<__pagination;i++)
            {
                        c++;
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR");
                      
                        
                        
                         var descell=document.createElement("TD");
                         if(items5[i]==true)
                         {
                                 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                                 {
                                    var sc=document.createElement("<INPUT type='checkbox' name='sel' id='sel" +i+"' checked  onclick='setTrue(this,"+i+")'>");
                                 }
                                 else
                                 {
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
                         sc.value=items1[i];
                         
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);
                         
                         var cbox=document.getElementById('sel'+i);
                         //alert(cbox.value);
                         
                          
                        
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(items1[i]!="null")
                            {
                                var currentText=document.createTextNode(items1[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                             cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(items2[i]!="null")
                            {
                                var currentText=document.createTextNode(items2[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                             cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(items3[i]!="null")
                            {
                                var currentText=document.createTextNode(items3[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                            
                             cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(items4[i]!="null")
                            {
                                var currentText=document.createTextNode(items4[i]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                              
                        tbody.appendChild(mycurrent_row);
            }
            // alert(page);
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
}


function btncancel()
{

 document.OfficeSpecificReport.cmbControllingLevel.selectedIndex=0;
 var tbody=document.getElementById("tb");
                try{tbody.innerHTML="";}
                          catch(e) {tbody.innerText="";}
}

function btnsubmit()
{

var v=document.getElementsByName("sel");
var s='';

/*if(v)
{

    for(i=0;i<v.length;i++)
    {
                  
        if(v[i].checked==true)
        {
     
          document.OfficeSpecificReport.action="../../../../../../OfficeSpecificReportServ.rep";
          document.OfficeSpecificReport.method="POST";
          document.OfficeSpecificReport.submit();
            return true;
        }
       
    }
   
}*/
if(v && items5)
{
        for(i=0;i<items5.length;i++)
        {
                if(items5[i]==true)
                {
                    s=s+items1[i]+',';
                }
        }
        if(s=='')
        {
             alert('Select the office(s) ');
             return false; 
        }
        else
        {
            //alert(s.substring(0,s.length-1));
            document.OfficeSpecificReport.txtseloff.value=s.substring(0,s.length-1);
            document.OfficeSpecificReport.action="../../../../../../OfficeSpecificReportServ.rep";
          document.OfficeSpecificReport.method="POST";
          document.OfficeSpecificReport.submit();
            return true;
        }


}
else
{
           alert('Select the office(s) ');
           return false; 

}

}
  
function changepage()
{
//alert('hai');
var page=document.OfficeSpecificReport.cmbpage.value;
loadPage(parseInt(page));

}

function sellectall()
{
        var v=document.getElementsByName("sel");
        if(v && items5)
        {
            if(document.OfficeSpecificReport.chkall.checked==true)
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


function inverseselect()
{
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


function changepagesize()
{

           __pagination=document.OfficeSpecificReport.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
        if(v && items5)
        {
           
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(items1.length>0)
                            {
                                    totalblock=parseInt(items1.length/__pagination);
                                    if(items1.length%__pagination!=0)
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
            
            
            
        }
           
}
