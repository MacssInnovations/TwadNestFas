//XMLHttpRequest Object Created
var req = false;
function getTransport()
{
 
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

 //FUNCTION TO CLEAR THE CONTENTS IN THE TABLE
 function clearAll()
 {
   document.ClassForm.txtClassId.value="";
   document.ClassForm.txtClassName.value="";
   document.ClassForm.CmdAdd.disabled=false;
   document.ClassForm.CmdUpdate.disabled=true;
   document.ClassForm.CmdDelete.disabled=true;
   document.ClassForm.txtClassId.disabled=false;
   var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
 }
 
 function Exit()
 {
    self.close();
 }
 //FUNCTION TO LOAD THE VALUES FORM THE GRID
  function loadValuesFromTable(rid)
    {      
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          document.ClassForm.txtClassId.value=rcells.item(1).firstChild.nodeValue;
          document.ClassForm.htxtClassId.value=rcells.item(1).firstChild.nodeValue;
          document.ClassForm.txtClassName.value=rcells.item(2).firstChild.nodeValue;
          document.ClassForm.CmdAdd.disabled=true;
          document.ClassForm.CmdUpdate.disabled=false;
          document.ClassForm.CmdDelete.disabled=false;
          document.ClassForm.txtClassId.disabled=true;
          document.ClassForm.CmdDelete.focus();
      
    }
 //FUNCTION TO CALL THE SERVLET   
 function callServer(command,param)
 {
   
       var strClassId=document.ClassForm.txtClassId.value;
       var hstrClassId=document.ClassForm.htxtClassId.value;
       hstrClassId=strClassId;
       var strClassName=document.ClassForm.txtClassName.value;
        var url="";
       
       if(command=="Add")
        {            
        var flag=nullCheck()
        if(flag==true)
        {
                   url="../../../../../ClassServlet.view?command=Add&ClassId="+hstrClassId+"&ClassName=" + strClassName;
                    var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
                    
          }       
                    
        }
        else if(command=="Update")
        {
                var flag=nullCheck()
                  if(flag==true)
                  {    
                     url="../../../../../ClassServlet.view?command=Update&ClassId="+hstrClassId+"&ClassName="+strClassName;
                     var req=getTransport();
                      req.open("GET",url,true); 
                      req.onreadystatechange=function()
                      {
                         processResponse(req);
                      }   
                    req.send(null);
                  }

        }
        
        else if(command=="Delete")
        {  
           if(confirm("Do You Really Want to Delete It"))
           {
            url="../../../../../ClassServlet.view?command=Delete&&ClassId="+hstrClassId;
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
               alert("Records are not Deleted");
            }
        
        }
        
}  

//FUNCTION TO PROCESS THE RESPONSE RETURNED BY THE SERVLET
function processResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="Add")
              {
                  addRow(baseResponse);                 
              }
              else if(command=="Delete")
              { 
              deleteRow(baseResponse)
              }
              else if(command=="check")
              {
              checkRow(baseResponse);
              }
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              
          }
        }
  }

//FUNCTION CALLED BY PROCESS RESPONSE TO UPDATE THE DETAILS

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var strClassId=document.ClassForm.txtClassId.value;
               var hstrClassId=document.ClassForm.htxtClassId.value;
               hstrClassId=strClassId;
                            var items=new Array();
                            items[0]=hstrClassId;
                            items[1]=document.ClassForm.txtClassName.value;
                              var r=document.getElementById(items[0]);    
                              var rcells=r.cells;
                            rcells.item(1).firstChild.nodeValue=items[0];
                            rcells.item(2).firstChild.nodeValue=items[1];
                       
                            document.ClassForm.txtClassId.value="";
                            document.ClassForm.txtClassName.value="";
                            document.ClassForm.txtClassId.disabled=false;
       }
       else
       {
           alert("failed to update values");
       }                                  
    }

//FUNCTION CALLED BY PROCESS RESPONCE TO DELETE THE DETAILS
function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                  if(flag=="success")
                  {
                      var ClassId=baseResponse.getElementsByTagName("ClassId")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(ClassId);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                      document.ClassForm.txtClassId.value="";
                      document.ClassForm.txtClassName.value="";
                      alert("Selected Class Details are Deleted");                      
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }
  //FUNCTION CALLED BY PROCESS RESPONSE TO ADD THE DETAILS
  function addRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {                        
                     alert("Record Inserted Into Database successfully.");
                     var items=new Array();                   
                    items[0]=document.ClassForm.txtClassId.value;
                    items[1]=document.ClassForm.txtClassName.value;
              
                     var tbody=document.getElementById("tblList");
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=items[0];
                     var cell=document.createElement("TD");
                     var anc=document.createElement("A");       
                     var url="javascript:loadValuesFromTable('" + items[0] + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     var i=0;
                     var cell2;
                     for(i=0;i<2;i++)
                     {                                           
                         cell2=document.createElement("TD");                               
                         var currenttext=document.createTextNode(items[i]);                         
                         cell2.appendChild(currenttext);       
                         mycurrent_row.appendChild(cell2);       
                     }  
                     tbody.appendChild(mycurrent_row);
                     document.ClassForm.txtClassId.value="";
                     document.ClassForm.txtClassName.value="";
                     document.ClassForm.txtClassId.disabled=false;
             }
             else
             {
                     alert("Failed to Insert Values");
             }
    }
 
//FUNCTION TO CALL THE SERVLET TO CHECK THE ALREAY EXISTING DETAILS  
function checkForRedundancy()
{
                     var strClassId=document.ClassForm.txtClassId.value;
                     //var strClassName=document.ClassForm.txtClassName.value;
                  url="../../../../../ClassServlet.view?command=check&ClassId="+strClassId;
                 var req=getTransport();
                  req.open("GET",url,true); 
                  req.onreadystatechange=function()
                  {
                     processResponse(req);
                  }   
                    req.send(null);
            
}

function checkRow(baseResponse)
{ 
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  if(flag=="success")
                  {
                      alert("Record already exists so Insertion is not possible");
                      document.ClassForm.txtClassId.value="";
                      document.ClassForm.txtClassId.focus();
                      
                  }
                  else
                  {}
}

//FUNCTION FOR NULL CHECK
function nullCheck()
{
if((ClassForm.txtClassId.value=="") || (ClassForm.txtClassId.value.length<=0))
    {
         alert("Please Enter Class  ID");
         ClassForm.txtClassId.focus();
         return false;
    }
    
    if((ClassForm.txtClassName.value=="") || (ClassForm.txtClassName.value.length<=0))
    {
         alert("Please Enter Class Name");
         ClassForm.txtClassName.focus();
         return false;
    } 
return true;
}

function isInteger(param,e)
{     
       var nav4 = window.Event ? true : false;
       if (nav4)    // Navigator 4.0x
            var whichCode = e.which
       else         // Internet Explorer 4.0x
            var whichCode = e.keyCode
      if(whichCode>=48 && whichCode<=57)
      {
          return true;
      }
      var str=param.value;
      param.value=str.substring(0,str.length-1);
      return false;              
}  

