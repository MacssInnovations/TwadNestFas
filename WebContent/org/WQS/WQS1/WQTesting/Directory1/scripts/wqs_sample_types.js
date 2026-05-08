function getTransport()
{
var req=false;
try
{
req=new ActiveXObject("Msxml2.XMLHTTP");
}catch(e1)
 {
    try{
    req=new ActiveXObject("Microsoft.XMLHTTP");
    }
    catch(e2)
    {
    req=false;
    }
 }
    if (!req && typeof XMLHttpRequest != 'undefined') 
        {
        req=new XMLHttpRequest();
        }
   return req;
   
}  


function callServer(command)
{
    //var sid  = document.getElementById("txtSamId").value;
    var stype  = document.getElementById("txtSamTyp").value;
    if(command=="Add")
    {
                var url = "../../../../../../wqs_sample_types?command=Add&stype="+stype;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   processResponse(req);
                }   
                req.send(null);
    }
    else if(command=="Update")
    {
                var sid  = document.getElementById("txtSamId").value;   
                url="../../../../../../wqs_sample_types?command=Update&sid="+sid+"&stype="+stype;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   processResponse(req);
                }   
                req.send(null);
    }      
    else if(command=="Delete")
    {    
         var sid  = document.getElementById("txtSamId").value;
         if(confirm("Do You Really want to Delete the Selected Record"))
         {  
                url="../../../../../../wqs_sample_types?command=Delete&sid="+sid
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                    processResponse(req);
                }   
                req.send(null);
          }
          else
          {
              alert("Records are not Deleted ");
          }
    }
}
/*
function chkSamId()
{
    
    var sid = document.getElementById("txtSamId").value;
    if(sid=="")
    {
        document.getElementById("txtSamId").focus();
    }
    else if(sid != "")
    {
        url="../../../../../../wqs_sample_types?command=chk&sid="+sid;
        var req=getTransport();
        req.open("GET",url,true); 
            
        req.onreadystatechange=function()
        {
           processResponse(req);
        }   
          req.send(null);
        
    }
}*/

function processResponse(req)
{   
      if(req.readyState==4)
        {
          if(req.status==200)
              var response=req.responseXML.getElementsByTagName("response")[0]; 
              var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue; 
              var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(cmd=="Add")
              {
                  if(flag=="Record")
                  {
                        alert("Record Exist");
                        document.getElementById("txtSamId").value="";
                        document.getElementById("txtSamId").focus();
                  }
                  else if(flag=="Success")
                  {
                        alert("Inserted Successfully");
                        var code=response.getElementsByTagName("code")[0].firstChild.nodeValue; 
                        var items=new Array();                   
                        item2=document.frmSamType.txtSamTyp.value;
                  
                        var tbody=document.getElementById("tblList");
                        
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=code;
                         var cell=document.createElement("TD");
                         
                         var anc=document.createElement("A");       
                         var url="javascript:loadValuesFromTable('" + code + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         mycurrent_row.appendChild(cell);
                             
                         cell2=document.createElement("TD");                               
                         var currenttext=document.createTextNode(code);                         
                         cell2.appendChild(currenttext);       
                         mycurrent_row.appendChild(cell2);       
                                                          
                         cell2=document.createElement("TD");                               
                         var currenttext=document.createTextNode(item2);                         
                         cell2.appendChild(currenttext);       
                         mycurrent_row.appendChild(cell2);       
                         
                         tbody.appendChild(mycurrent_row); 
                         document.frmSamType.txtSamId.value="";
                         document.frmSamType.txtSamTyp.value="";
                         document.frmSamType.CmdAdd.disabled=false;
                         document.frmSamType.CmdUpdate.disabled=true;
                         document.frmSamType.CmdDelete.disabled=true;
                  }
                  else if(flag=="Fail")
                  {
                    alert("Failed to Insert");
                  }
                  
              }
              else if(cmd=="Update")
              {
                   if(flag=="Success")
                   {   
                        alert("Record Updated Successfully.");
                        var samId=document.frmSamType.txtSamId.value;
                        var items=new Array();
                        items[0]=samId;
                        items[1]=document.frmSamType.txtSamTyp.value;
                        var r=document.getElementById(items[0]);    
                        var rcells=r.cells;
                        rcells.item(1).firstChild.nodeValue=items[0];
                        rcells.item(2).firstChild.nodeValue=items[1];
                        document.frmSamType.txtSamId.value="";
                        document.frmSamType.txtSamTyp.value="";
                        document.frmSamType.txtSamId.disabled=false; 
                        document.frmSamType.CmdAdd.disabled=false;
                        document.frmSamType.CmdUpdate.disabled=true;
                        document.frmSamType.CmdDelete.disabled=true;
                   }
                   else
                   {
                       alert("Failed to update values");
                   }       
              }
              else if(cmd=="Delete")
              { 
                    if(flag=="success")
                    {
                          var sid=response.getElementsByTagName("sid")[0].firstChild.nodeValue;
                          var tbody=document.getElementById("Existing");     
                          var r=document.getElementById(sid);    
                          var ri=r.rowIndex;               
                          tbody.deleteRow(ri); 
                          document.frmSamType.txtSamId.value="";
                          document.frmSamType.txtSamTyp.value="";
                          document.frmSamType.txtSamId.disabled=false;
                          alert("Selected Details are Deleted");    
                          document.frmSamType.CmdAdd.disabled=false;
                          document.frmSamType.CmdUpdate.disabled=true;
                          document.frmSamType.CmdDelete.disabled=true;
                     }
                     else
                     {
                          alert("Unable to Delete");
                     }         
              }
              else if(cmd=="chk")
              {
                 if(flag=="Success")
                 {
                        document.getElementById("txtSamTyp").focus();
                 }
                 else if(flag=="Failure")
                 {
                        alert("Not Insert");
                 }
                 else if(flag=="Record")
                 {
                        alert("Already Exist");
                        document.getElementById("txtSamId").value="";
                        document.getElementById("txtSamId").focus();
                  }
            }
        }
}     
   

function clearAll()
 {
   document.frmSamType.txtSamId.value="";
   document.frmSamType.txtSamTyp.value="";
   document.frmSamType.CmdAdd.disabled=false;
   document.frmSamType.CmdUpdate.disabled=true;
   document.frmSamType.CmdDelete.disabled=true;
   document.frmSamType.txtSamId.disabled=false;
 }
 
function loadValuesFromTable(rid)
{      
      var r=document.getElementById(rid); 
      var rcells=r.cells;
      document.getElementById("Existing");
      document.frmSamType.txtSamId.value=rcells.item(1).firstChild.nodeValue;
      document.frmSamType.txtSamTyp.value=rcells.item(2).firstChild.nodeValue;
        
      document.frmSamType.CmdAdd.disabled=true;
      document.frmSamType.CmdUpdate.disabled=false;
      document.frmSamType.CmdDelete.disabled=false;
      document.frmSamType.txtSamId.disabled=true;
      document.frmSamType.CmdDelete.focus();
}