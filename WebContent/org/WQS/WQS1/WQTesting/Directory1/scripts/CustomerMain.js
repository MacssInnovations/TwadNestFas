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
 
 
 function clearAll()
 {
   document.CusForm.wtxtCusId.value="";
   document.CusForm.txtCusDesc.value="";
  document.CusForm.CmdAdd.disabled=false;
                            document.CusForm.CmdUpdate.disabled=true;
                            document.CusForm.CmdDelete.disabled=true;
                            document.CusForm.wtxtCusId.disabled=false;
      
 }
 
  function Exit()
 {
    window.open('','_parent','');
    window.close();
 }

 
  function loadValuesFromTable(rid)
    {      
    
                
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          document.CusForm.wtxtCusId.value=rcells.item(1).firstChild.nodeValue;
          document.CusForm.txtCusDesc.value=rcells.item(2).firstChild.nodeValue;
          document.CusForm.wtxtCusId.disabled=true;
          document.CusForm.CmdAdd.disabled=true;
          document.CusForm.CmdUpdate.disabled=false;
          document.CusForm.CmdDelete.disabled=false;
          
    }
    
 function callServer(command,param)
 {
   
       var wstrCusId=document.getElementById("wtxtCusId").value;
       //alert(wstrCusId);
       var strCusDesc=document.CusForm.txtCusDesc.value;
        var url="";
       
       if(command=="Add")
        {              
                    if(document.CusForm.txtCusDesc.value=="")
                    {
                       alert("Please Enter the Customer Main Description");
                    }
                    else
                    {
                    url="../../../../../../customermain_servlet.view?command=Add&CusDesc=" + strCusDesc+"&CusId="+wstrCusId;
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
                    
                    if(document.CusForm.txtCusDesc.value=="")
                    {
                       alert("Please Enter the Customer Main Description");
                    }
                    else
                    {
                    url="../../../../../../customermain_servlet.view?command=Update&CusId="+wstrCusId+"&CusDesc="+strCusDesc;
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
          if(confirm("Do You Really want to Delete the Selected Record"))
             {
                    url="../../../../../../customermain_servlet.view?command=Delete&CusId="+wstrCusId;
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
              deleteRow(baseResponse);
              }
             
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              
          }
        }
  }

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var wstrCusId=document.CusForm.wtxtCusId.value;
                            var items=new Array();
                            items[0]=wstrCusId;
                            items[1]=document.CusForm.txtCusDesc.value;
                              var r=document.getElementById(items[0]);    
                              var rcells=r.cells;
                            rcells.item(1).firstChild.nodeValue=items[0];
                            rcells.item(2).firstChild.nodeValue=items[1];
                       
                            document.CusForm.txtCusDesc.value="";
                            document.CusForm.wtxtCusId.value="";
                            document.CusForm.CmdAdd.disabled=false;
                            document.CusForm.CmdUpdate.disabled=true;
                            document.CusForm.CmdDelete.disabled=true;
                            document.CusForm.wtxtCusId.disabled=false;
       }
       else
       {
           alert("failed to update values");
       }                                  
    }

function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  //alert(flag);
                  if(flag=="success")
                  {
                     
                      var CusId=baseResponse.getElementsByTagName("CusId")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(CusId);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                               document.CusForm.txtCusDesc.value="";
                               document.CusForm.wtxtCusId.value="";
                               document.CusForm.CmdAdd.disabled=false;
                            document.CusForm.CmdUpdate.disabled=true;
                            document.CusForm.CmdDelete.disabled=true;
                            document.CusForm.wtxtCusId.disabled=false;
      
                      alert("Selected Records are Deleted");                      
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }
  function addRow(baseResponse)
    {
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {                        
                     alert("Record Inserted Into Database successfully.");
                     cid=baseResponse.getElementsByTagName("CusId")[0].firstChild.nodeValue; 
                      alert("Please Note Down Your Customer Main Id " +  cid);
                     var items=new Array();                   
                    items[0]=baseResponse.getElementsByTagName("CusId")[0].firstChild.nodeValue; 
                   items[1]=document.CusForm.txtCusDesc.value;
              
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
                            document.CusForm.txtCusDesc.value="";
                            document.CusForm.wtxtCusId.value="";
             }
             else
             {
                     alert("Failed to Insert Values");
             }
    }
 