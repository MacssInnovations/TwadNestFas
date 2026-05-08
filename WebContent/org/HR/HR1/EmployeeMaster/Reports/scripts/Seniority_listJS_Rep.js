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
function getDesignation()
{
    var type=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
    if(type!=0)
    {
      
      loadOfficesByType(type);
    }
    else
    {
      var des=document.getElementById("cmbDesignation");
      des.innerHTML='';
    }
}

function loadOfficesByType(type)
{
      
       var type=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
       startwaiting(document.frmSeniority) ;
       var url="../../../../../../Seniority_listServ_Rep?Command=SGroup&cmbsgroup=" + type ;
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
              
          var des=document.getElementById("cmbDesignation");
          var i=0;
          var response=req.responseXML.getElementsByTagName("response")[0];
          var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
          des.innerHTML="";
                
          stopwaiting(document.frmSeniority);
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
             option.text="--Select Cadre--";
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

function doFunction()
{
    var c=checkNull();
   if(c)
   {
        var service_group=document.frmSeniority.cmbsgroup.options[document.frmSeniority.cmbsgroup.selectedIndex].value;
        var cad_id=document.frmSeniority.cmbDesignation.options[document.frmSeniority.cmbDesignation.selectedIndex].value;
        var url="../../../../../../Seniority_listServ_Rep?service_group="+service_group+"&cad_id="+cad_id;
        document.frmSeniority.action=url;
        document.frmSeniority.method="POST";
        document.frmSeniority.submit();
    }
}

function checkNull()
{
    if(document.frmSeniority.cmbDesignation.value=="0"||document.frmSeniority.cmbDesignation.selectedIndex==0)
    {
        alert("Select Designation");
        return false;
    }
    else
        return true;
}