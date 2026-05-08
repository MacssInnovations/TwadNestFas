var service;
var __pagination=10;
var totalblock=0;

//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
//alert('kkk');
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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.HRE_EmployeeServiceDetails.txtEmployeeid.value=emp;
doFunction('loademp','null');

}


window.onunload=function()
{

if (winemp && winemp.open && !winemp.closed) winemp.close();

}

var req=false;
try
{
    req=new ActiveXObject("Msxml2.XMLHTTP");
}
catch(e)
{
    try
    {
        req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(ee)
    {
        req=false;
    }
}

if(!req || typeof XMLHTTPRequest !='undefined')
{
    req=new XMLHttpRequest();
}

function toExit()
{
  //window.close();
var w=window.open(window.location.href,"_self");
w.close();
}


function notNull()
{

   
    if((document.HRE_EmployeeServiceDetails.txtEmployeeid.value==null)||(document.HRE_EmployeeServiceDetails.txtEmployeeid.value.length==0))
    {
        alert("Enter Employee Id");
        document.HRE_EmployeeServiceDetails.txtEmployee.value="";
        document.HRE_EmployeeServiceDetails.txtdob.value="";
        document.HRE_EmployeeServiceDetails.txtGpf.value="";
        document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                     if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        //clr();
        document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
        return false;
    }
    else if(isNaN(document.HRE_EmployeeServiceDetails.txtEmployeeid.value))
    {
        alert("Enter Numeric value");
        document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
        document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
        return false;
    }
    return true;
}
     
function doFunction(Command,param)
{
   // alert("command:"+Command);
    var empid=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
   document.HRE_EmployeeServiceDetails.txtEmployee.value="";
    document.HRE_EmployeeServiceDetails.txtdob.value="";
    document.HRE_EmployeeServiceDetails.txtGpf.value="";
    if(Command=='loademp')
    {
       var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                     if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        var check=notNull();
        //alert(check);
        if(check )
        {
         
                startwaiting(document.HRE_EmployeeServiceDetails) ;
                service=null;
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
               // alert('load emp');
                if(param=='exist')
                {
                var url="../../../../../HRE_EmployeeServiceDetailsServ.view?Command=loadempview&txtEmployeeid="+empid+"&param=exist";
                }
                else
                {
                var url="../../../../../HRE_EmployeeServiceDetailsServ.view?Command=loadempview&txtEmployeeid="+empid;
                }
                req.open("GET",url,true);
                req.onreadystatechange=handleResponse;
                if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();  
                
        }        
    
    }
    
    
   
}


function handleResponse()
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            stopwaiting(document.HRE_EmployeeServiceDetails);
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
             //alert('test');
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loademp")
            {
                loadEmp(baseResponse);
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
            
            
        }  
        
    }
    
}



function loadEmp(baseResponse)
{

    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
    {
           
           var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
            var edob=baseResponse.getElementsByTagName("edob")[0].firstChild.nodeValue;
            var egpf=baseResponse.getElementsByTagName("egpf")[0].firstChild.nodeValue;
            document.HRE_EmployeeServiceDetails.txtEmployee.value=ename;
            if(edob=="-")
                edob="";
            document.HRE_EmployeeServiceDetails.txtdob.value=edob;
          
            if(egpf==0)
                egpf="";
            document.HRE_EmployeeServiceDetails.txtGpf.value=egpf;
            var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {       
                    //alert(tbody.innerText !='undefined'  && tbody.innerText !=null );
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
                    
                   // for(i=0;i<tbody.rows.length;i++)
                   //     tbody.deleteRows(i);
            }
             
            
             service=baseResponse.getElementsByTagName("servicedata");
            if(service)
            {
                //alert(service.length);
                                    /* <SERVICE_LIST_SLNO> <DATE_FROM> <DATE_FROM_SESSION> <DATE_TO> 
                     <DATE_TO_SESSION> <DESIGNATION_ID> <OFFICE_ID> <EMPLOYEE_STATUS_ID>
                     <STATUS_DETAIL> <REMARKS> 
                     
                     */ 
                     var i=0;
                      totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
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
    else if(flag=="failure1")
    {
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            //alert("Can not view SR. Because Employee Id "+id+" is not under your Office!");
            alert("SR controling office for this employee is different from your office!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            //clr();
    }
     else if(flag=="failure2")
    {
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            alert("You have no Current Posting. Can not view SR for "+id+"!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            //clr();
    }
     else if(flag=="failure3")
    {
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            alert("Given Employee Id " +id+" has no SR control Office. Can not view SR!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            //clr();
    }
     else if(flag=="failure4")
    {
            var id=document.HRE_EmployeeServiceDetails.txtEmployeeid.value;
            alert("Can not view SR. Access Denined!");
            document.HRE_EmployeeServiceDetails.txtEmployee.value="";
            document.HRE_EmployeeServiceDetails.txtdob.value="";
            document.HRE_EmployeeServiceDetails.txtGpf.value="";
            document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
            var tbody=document.getElementById("tb");
               
                if(tbody.rows.length >0)
                {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            //clr();
    }
    else
    {
        
               
        alert('Enter a Valid Employee Number');
        document.HRE_EmployeeServiceDetails.txtEmployee.value="";
        document.HRE_EmployeeServiceDetails.txtdob.value="";
        document.HRE_EmployeeServiceDetails.txtGpf.value="";
        document.HRE_EmployeeServiceDetails.txtEmployeeid.value="";
        var tbody=document.getElementById("tb");
           
            if(tbody.rows.length >0)
            {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
        //clr();
    }


}



function changepage()
{
//alert('test');
var page=document.HRE_EmployeeServiceDetails.cmbpage.value;
loadPage(parseInt(page));

}



function loadPage(page)
{
            var i=0;
            var c=0;
            var p=__pagination*(page-1);
            
            document.HRE_EmployeeServiceDetails.cmbpage.selectedIndex=page-1;
                var tbody=document.getElementById("tb");
                 
                  try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}  
                  
                  // alert(service);
             if(service)
                    {
                    ///////////////////////////////
                   
                   
                  s=0;
                 
                for(i=p;i<service.length&& c<__pagination;i++)
                {
                     c++;
                         var items=new Array();
                        items[0]=service[i].getElementsByTagName("SERVICE_LIST_SLNO")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("DATE_FROM")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("DATE_FROM_SESSION")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("DATE_TO")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("DATE_TO_SESSION")[0].firstChild.nodeValue;
                        
                        items[5]=service[i].getElementsByTagName("DESIGNATION_ID")[0].firstChild.nodeValue;
                        items[6]=service[i].getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
                        
                        items[7]=service[i].getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
                        items[8]=service[i].getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
                         
                        items[9]=service[i].getElementsByTagName("EMPLOYEE_STATUS_ID")[0].firstChild.nodeValue;
                        items[10]=service[i].getElementsByTagName("EMPLOYEE_STATUS_DESC")[0].firstChild.nodeValue;
                        
                           
                        items[11]=service[i].getElementsByTagName("STATUS_DETAIL")[0].firstChild.nodeValue;
                        
                        items[12]=service[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
                        
                        items[13]=service[i].getElementsByTagName("OFFICE_DEPT_ID")[0].firstChild.nodeValue;
                        items[14]=service[i].getElementsByTagName("SLNO")[0].firstChild.nodeValue;
                        items[15]=service[i].getElementsByTagName("PROCESS_FLOW_STATUS_ID")[0].firstChild.nodeValue;
                        
                        
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR");
                      
                        if(items[15]=='FR')
                       {
                             var descell=document.createElement("TD");
                             var sn=document.createTextNode('Freezed');
                            // descell.setAttribute('style','width:400');
                             descell.appendChild(sn);
                             mycurrent_row.appendChild(descell);
                        }
                        else
                        {
                           var descell=document.createElement("TD");
                             var sn=document.createTextNode('UnFreezed');
                             //descell.setAttribute('style','color:red');
                             descell.appendChild(sn);
                             mycurrent_row.appendChild(descell);
                        }
                        
                        var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         var sn=document.createTextNode(items[14]);
                         sc.type="HIDDEN";
                         sc.name="sScode";
                         sc.text=items[0];
                         sc.value=items[0];
                         descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                        
                        var j=0;
                        var cell2;
                    
                        for(j=1;j<5;j++)
                        {
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            
                            if(items[j]!="null")
                            {
                                var currentText=document.createTextNode(items[j]);
                            }
                            else
                            {
                                
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }
                        
                    
                        var descell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                        // var sn=document.createTextNode(items[6]);
                         if(items[6]!="null")
                        {
                            var sn=document.createTextNode(items[6]);
                        }
                        else
                        {
                            var sn=document.createTextNode('');
                        }
                         sc.type="HIDDEN";
                         sc.name="sScode";
                         sc.text=items[5];
                         sc.value=items[5];
                         descell.appendChild(sc);
                         descell.appendChild(sn);
                         mycurrent_row.appendChild(descell);
                         
                         
                         cell2=document.createElement("TD");
                         cell2.setAttribute('align','left');
                        // var currentText=document.createTextNode(items[13]);
                         if(items[13]!="null")
                        {
                            var currentText=document.createTextNode(items[13]);
                        }
                        else
                        {
                            var currentText=document.createTextNode('');
                        }
                         cell2.appendChild(currentText);
                         mycurrent_row.appendChild(cell2);
                         
                          
                         var offcell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         
                          if(items[8]!="null")
                        {
                            var currentText=document.createTextNode(items[8]);
                        }
                        else
                        {
                            var currentText=document.createTextNode('');
                        }
                         
                         var sn=currentText;
                         //var sn=document.createTextNode(items[8]);
                         sc.type="HIDDEN";
                         sc.name="sScode";
                         sc.text=items[7];
                         sc.value=items[7];
                         offcell.appendChild(sc);
                         offcell.appendChild(sn);
                         mycurrent_row.appendChild(offcell);
                         
                          
                          
                         
                        var stacell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         if(items[10]!="null")
                        {
                            var currentText=document.createTextNode(items[10]);
                        }
                        else
                        {
                            var currentText=document.createTextNode('');
                        }
                         
                         var sn=currentText;
                         sc.type="HIDDEN";
                         sc.name="sScode";
                         sc.text=items[9];
                         sc.value=items[9];
                         stacell.appendChild(sc);
                         stacell.appendChild(sn);
                         mycurrent_row.appendChild(stacell);
                         
                         
                         
                         
                      /*   
                         var offcell=document.createElement("TD");
                         var sc=document.createElement("INPUT");
                         
                          if(items[8]!="null")
                        {
                            var currentText=document.createTextNode(items[8]);
                        }
                        else
                        {
                            var currentText=document.createTextNode('');
                        }
                         
                         var sn=currentText;
                         //var sn=document.createTextNode(items[8]);
                         sc.type="HIDDEN";
                         sc.name="sScode";
                         sc.text=items[11];
                         sc.value=items[11];
                         offcell.appendChild(sc);
                         offcell.appendChild(sn);
                         mycurrent_row.appendChild(offcell);
                         
                        */ 
                         
                         
                        for(j=12;j<13;j++)
                        {
                            cell2=document.createElement("TD");
                            cell2.setAttribute('align','left');
                            //alert(items[j]); 
                            if(items[j]!="null")
                            {
                                var currentText=document.createTextNode(items[j]);
                            }
                            else
                            {
                                var currentText=document.createTextNode('');
                            }
                            cell2.appendChild(currentText);
                            mycurrent_row.appendChild(cell2);
                        }
                       
                         // alert('ok');        
                        tbody.appendChild(mycurrent_row);
                       
                }
            
                    
                    
                    
                    ///////////////////////////////
                    
                    }          
                       
                       
            // alert(page);
           // alert(page<totalblock);
           var cell=document.getElementById("divcmbpage");
                cell.style.display="block";
           var cell=document.getElementById("divpage");
                cell.style.display="block";
               
                if(navigator.appName.indexOf("Microsoft")!=-1)
                    cell.innerText= ' / ' +totalblock;
                else
                    cell.innerHTML= ' / ' +totalblock;
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



function changepagesize()
{

           __pagination=document.HRE_EmployeeServiceDetails.cmbpagination.value;
            var v=document.getElementsByName("sel");
            //alert(v);
        if(service)
        {
           
                            totalblock=0;
                            //alert(parseInt(items1.length/__pagination));
                            if(service.length>0)
                            {
                                    totalblock=parseInt(service.length/__pagination);
                                    if(service.length%__pagination!=0)
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

/////////////////////////////////////////////////////////////////////////////



function checkempid()
{
    if(document.HRE_EmployeeServiceDetails.txtEmployeeid.value==null || document.HRE_EmployeeServiceDetails.txtEmployeeid.value.length==0)
    {
            alert('Select Employee Id');
            document.HRE_EmployeeServiceDetails.txtEmployeeid.focus();
            return false;
    }
   
    return true;

}

function numbersonly1(e,t)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          //document.HRE_EmployeeServiceDetails.txtSNo.focus();
          return true;
        
        }
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }
     
     
