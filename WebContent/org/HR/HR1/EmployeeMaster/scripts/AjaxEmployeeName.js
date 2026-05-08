// code for creating XMLHTTPREQUEST object
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


function callEmployee(command,param)
{
//alert("CallEmployee Called"+param);
    var url="";
    if(command=="Load")
    {
    
        var EmployeeName=param;
        url="../../../../../EmployeeNameFilter.con?command=Load&EmployeeName="+EmployeeName;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
          if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert(req.responseText);
                      var EmployeeName=document.getElementById("EmpName");
                      var EmployeeId=document.getElementById("id");
                      var pd=document.getElementById("popup");          
                      pd.innerHTML="";
                      var i=0;
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var tempDiv=document.createElement("DIV");
                              tempDiv.id=id;
                              var tempId=document.createTextNode(id);
                              var tempNode=document.createTextNode(name);
                              tempDiv.appendChild(tempNode);
                              //tempDiv.appendChild(tempId);
                              pd.appendChild(tempDiv);
                                 
                                 // wiring events
                             tempDiv.onmouseover=optionHover;
                             if (tempDiv.captureEvents) tempDiv.captureEvents(Event.MOUSEOVER);
                    
                             tempDiv.onmouseout=optionNormal;
                             if (tempDiv.captureEvents) tempDiv.captureEvents(Event.MOUSEOUT);
                                
                             tempDiv.onclick=optionSelected;             
                             if (tempDiv.captureEvents) tempDiv.captureEvents(Event.CLICK);
                              
                          }
                            pd.style.zIndex=1;           
                            pd.style.display="block";
                          
                      }
                    //eval("closeWindow()");  
                    //alert("*");
                }
            }
          }
          
          
    
    
    
    
    

}