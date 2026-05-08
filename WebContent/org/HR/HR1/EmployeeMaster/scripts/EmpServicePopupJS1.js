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

var s=0;
 function getDesignation()
    {
        var type=document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.HRM_EmpSearch.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.HRM_EmpSearch.cmbdes.style.visibility="hidden";
        }
    }
    
  function getEmployee()  
  
  {
       if(document.HRM_EmpSearch.txtEmpName.value.length==0 && document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value==0)
       {
            alert('Enter or Select the employee values');
            return false;
        }
       var group=document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value;
      try{
       var des=document.HRM_EmpSearch.cmbdes.options[document.HRM_EmpSearch.cmbdes.selectedIndex].value;
       }catch(e){var des=0;}
       var name=document.HRM_EmpSearch.txtEmpName.value;
       var url="../../../../../EmpServicePopupServ.view?Command=Emp&cmbsgroup=" + group ;
       url=url+"&cmbdes="+des+"&txtEmpName="+name;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadTable(req);
        }
          if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
  
  }
    
     function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.HRM_EmpSearch.cmbsgroup.options[document.HRM_EmpSearch.cmbsgroup.selectedIndex].value;
       var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
         if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbdes");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Designation--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}



function loadTable(req)
{

        if(req.readyState==4)
        {
          if(req.status==200)
          { 
              
               var response=req.responseXML.getElementsByTagName("response")[0];
                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                    alert("No Employee exists under this level");
                    document.HRM_EmpSearch.txtEmpName.value="";
                    document.HRM_EmpSearch.txtgpf.value="";
                    s=0;
                    var tbody=document.getElementById("tb");
                  tbody.innerText="";
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                     var service=response.getElementsByTagName("employee");
                    if(service)
                    {
                    ///////////////////////////////
                       var i=0;
                  var tbody=document.getElementById("tb");
                  tbody.innerText="";
                  s=0;
                for(i=0;i<service.length;i++)
                {
                    
                        var items=new Array();
                        items[0]=service[i].getElementsByTagName("empid")[0].firstChild.nodeValue;
                        items[1]=service[i].getElementsByTagName("empname")[0].firstChild.nodeValue;
                        items[2]=service[i].getElementsByTagName("initial")[0].firstChild.nodeValue;
                        items[3]=service[i].getElementsByTagName("designation")[0].firstChild.nodeValue;
                        items[4]=service[i].getElementsByTagName("dob")[0].firstChild.nodeValue;
                        
                        items[5]=service[i].getElementsByTagName("gpf")[0].firstChild.nodeValue;
                        
                        
                        var tbody=document.getElementById("tb");
                        var mycurrent_row=document.createElement("TR");
                      
                        
                        
                         var descell=document.createElement("TD");
                       /*  var sc=document.createElement("<INPUT type='radio' name='sel' id='sel" +i+"' >");
                         //sc.type="radio";
                         //sc.name="sel";
                         
                         sc.setAttribute("onclick","pick(this)");
                         //sc.text=items[0];
                        // sc.id='rad'+i;
                         sc.value=items[0];
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);*/
                        // alert(window.navigator.appName.toLowerCase().indexOf("netscape") == -1);
                         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                         {
                            var sc=document.createElement("<INPUT type='radio' name='sel' id='sel" +i+"' >");
                         }
                         else
                         {
                         var sc=document.createElement("input");
                         sc.type="radio";
                         sc.name="sel";
                         sc.id='sel' +i;
                         }
                         sc.setAttribute("onclick","pick(this)");
                         sc.value=items[0];
                         descell.appendChild(sc);
                         mycurrent_row.appendChild(descell);
                         
                         
                          
                        for(j=0;j<6;j++)
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
                
                }
            }
        }
       

}   

function btnsubmit()
{
//alert('test');
var v=document.getElementsByName("sel");
if(v)
{
    for(i=0;i<v.length;i++)
    {
        if(v[i].checked==true)
        {
            //alert(v[i].value);
            opener.document.frmEmployee.txtEmpId1.value = (v[i].value);
            opener.document.frmEmployee.EmpId.value=(v[i].value);
            try{self.opener.callServer('Existg','null');}catch(e){}
            
            
                
            self.close();
            return true;
        }
       
    }
}
else
{
           alert('Select an Employee ');
           return false; 

}
    

}
function pick(t)
{

//alert(t.value);
    s=t.value;
}

function btncancel()
{

 self.close();
}