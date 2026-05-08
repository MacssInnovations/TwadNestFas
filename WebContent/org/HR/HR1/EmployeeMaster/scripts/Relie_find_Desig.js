
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
        var type=document.HRM_DesigSearch.cmbsgroup.options[document.HRM_DesigSearch.cmbsgroup.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divdes");
            din.style.visibility="visible";
            document.HRM_DesigSearch.cmbdes.style.visibility="visible";
            loadOfficesByType(type);
        }
        else
        {
             var din=document.getElementById("divdes");
            din.style.visibility="hidden";
            document.HRM_DesigSearch.cmbdes.style.visibility="hidden";
        }
    }
    
    
     function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.HRM_DesigSearch.cmbsgroup.options[document.HRM_DesigSearch.cmbsgroup.selectedIndex].value;
       var url="../../../../../Relie_find_Desig_serv.view?Command=SGroup&cmbsgroup=" + type ;
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
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
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




function btnsubmit()
{
//alert('test');

 if(document.HRM_DesigSearch.cmbsgroup.options[document.HRM_DesigSearch.cmbsgroup.selectedIndex].value==0 && document.HRM_DesigSearch.cmbdes.options[document.HRM_DesigSearch.cmbdes.selectedIndex].value==0)
       {
            alert('Select Designation value ');
            return false;
        }
        var group=document.HRM_DesigSearch.cmbsgroup.options[document.HRM_DesigSearch.cmbsgroup.selectedIndex].value;
        var des=document.HRM_DesigSearch.cmbdes.options[document.HRM_DesigSearch.cmbdes.selectedIndex].value;
        var desname=document.HRM_DesigSearch.cmbdes.options[document.HRM_DesigSearch.cmbdes.selectedIndex].text;
        Minimize();
        opener.doParentDesig(des,desname);
        return true;
       
}

function btncancel()
{

 self.close();
}

function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}
