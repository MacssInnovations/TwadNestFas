
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
   document.frmSubLedgerMaster.txtsubtypecode.value="";
   document.frmSubLedgerMaster.txtsubtypedesc.value="";
   
   document.frmSubLedgerMaster.CmdAdd.disabled=false;
   document.frmSubLedgerMaster.CmdUpdate.disabled=true;
   document.frmSubLedgerMaster.CmdDelete.disabled=true;
   
  /* var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }*/
 }
 
 function Exit()
 {
    self.close();
 }
 
 
 
 //******************************Validation Checking**************************//
 function nullCheck()
        {
                  
                  if((document.frmSubLedgerMaster.txtsubtypedesc.value=="") || (document.frmSubLedgerMaster.txtsubtypedesc.value.length<=0))
                  { 
                       alert("Please Enter the Sub-Ledger Type Description");
                       document.frmSubLedgerMaster.txtsubtypedesc.focus();
                       return false;
                  }
                  return true;
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var txtsubledgertypecode=document.frmSubLedgerMaster.txtsubtypecode.value;
       var txtsubledgertypedesc=document.frmSubLedgerMaster.txtsubtypedesc.value;
       
        var url="";
        
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../SubLedgerMasterServlet.con?command=Add&SubLedgerTypeDesc=" + txtsubledgertypedesc;
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
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../SubLedgerMasterServlet.con?command=Update&SubLedgerTypeCode="+txtsubledgertypecode+"&SubLedgerTypeDesc=" + txtsubledgertypedesc;
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
                    url="../../../../../SubLedgerMasterServlet.con?command=Delete&SubLedgerTypeCode="+txtsubledgertypecode;
                   var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
        }
        else if(command=="Get")
        {               
            url="../../../../../SubLedgerMasterServlet.con?command=Get";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
        
}  


//********************************* CallServer Response Coding ***************************************//

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
              
              else if(command=="Update")
              {
              updateRow(baseResponse);
              }
               else if(command=="Get")
              { 
              getRow(baseResponse);
              }
              
          }
        }
  }


function addRow(baseResponse)
{
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");
                 did=baseResponse.getElementsByTagName("SubLedgerTypeCode")[0].firstChild.nodeValue; 
                 alert("Your Sub-Ledger Type Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("SubLedgerTypeCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("SubLedgerTypeDesc")[0].firstChild.nodeValue;
                 //document.frmSubLedgerMaster.txtsubtypecode.value=cadid;
                 //document.frmSubLedgerMaster.txtsubtypedesc.value=document.frmSubLedgerMaster.txtsubtypedesc.value;
                 
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                 /*       var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   */
                                             
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=cadid;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable('" + cadid + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
             
                 var cell2 =document.createElement("TD");    
                 var txtcadid=document.createTextNode(cadid);                         
                 cell2.appendChild(txtcadid);       
                 mycurrent_row.appendChild(cell2);       

                 var cell3 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(sdesc);                         
                 cell3.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell3);
                 tbody.appendChild(mycurrent_row);
                
                 document.frmSubLedgerMaster.CmdAdd.disabled=false;
                document.frmSubLedgerMaster.CmdUpdate.disabled=true;
                document.frmSubLedgerMaster.CmdDelete.disabled=true;           
                document.frmSubLedgerMaster.txtsubtypecode.value="";
                document.frmSubLedgerMaster.txtsubtypedesc.value="";      
               }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.frmSubLedgerMaster.txtsubtypecode.value;
               items[1]=document.frmSubLedgerMaster.txtsubtypedesc.value;
               var r=document.getElementById(items[0]);    
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                
                document.frmSubLedgerMaster.CmdAdd.disabled=false;
                document.frmSubLedgerMaster.CmdUpdate.disabled=true;
                document.frmSubLedgerMaster.CmdDelete.disabled=true;           
                document.frmSubLedgerMaster.txtsubtypecode.value="";
                document.frmSubLedgerMaster.txtsubtypedesc.value="";
                
                           
       }
       else
       {
           alert("failed to update values");
       }                                  
    }



function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                  
                  if(flag=="success")
                  {
                      var SubLedgerTypeCode=baseResponse.getElementsByTagName("SubLedgerTypeCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(SubLedgerTypeCode);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmSubLedgerMaster.txtsubtypecode.value="";
                       document.frmSubLedgerMaster.txtsubtypedesc.value="";
                        document.frmSubLedgerMaster.CmdAdd.disabled=false;
                        document.frmSubLedgerMaster.CmdUpdate.disabled=true;
                        document.frmSubLedgerMaster.CmdDelete.disabled=true;      
                               
                      alert("Selected Sub-Ledger Type Details are Deleted");                      
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
   
  }


function getRow(baseResponse)
    {   
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              
              if(flag=="success")
              {          
                       var tbody=document.getElementById("tblList");
                       var table=document.getElementById("Existing");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   
                                             
                            var SubLedgerTypeCode=baseResponse.getElementsByTagName("SubLedgerTypeCode");
                            
                     for(var k=0;k<SubLedgerTypeCode.length;k++)
                        {
                         var SubLedgerTypeCode=baseResponse.getElementsByTagName("SubLedgerTypeCode");
                         var SubLedgerTypeDesc=baseResponse.getElementsByTagName("SubLedgerTypeDesc");
                         var cSubLedgerTypeCode=SubLedgerTypeCode.item(k).firstChild.nodeValue;
                         var cSubLedgerTypeDesc=SubLedgerTypeDesc.item(k).firstChild.nodeValue;
                       
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=cSubLedgerTypeCode;
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");       
                         var url="javascript:loadValuesFromTable('" + cSubLedgerTypeCode + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         mycurrent_row.appendChild(cell);
                     
                         var cell2 =document.createElement("TD");    
                         var txtsubledgercode=document.createTextNode(cSubLedgerTypeCode);                         
                         cell2.appendChild(txtsubledgercode);       
                         mycurrent_row.appendChild(cell2);       

                         var cell3 =document.createElement("TD");    
                         var txtsubledgerdesc=document.createTextNode(cSubLedgerTypeDesc);                         
                         cell3.appendChild(txtsubledgerdesc);       
                         mycurrent_row.appendChild(cell3);
                         tbody.appendChild(mycurrent_row);
                        }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
}



function loadValuesFromTable(rid)
    {      
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
          document.frmSubLedgerMaster.txtsubtypecode.value=rcells.item(1).firstChild.nodeValue;
          document.frmSubLedgerMaster.txtsubtypedesc.value=rcells.item(2).firstChild.nodeValue;
          
          document.frmSubLedgerMaster.CmdAdd.disabled=true;
        document.frmSubLedgerMaster.CmdUpdate.disabled=false;
        document.frmSubLedgerMaster.CmdDelete.disabled=false;
        
          document.frmSubLedgerMaster.CmdDelete.focus();
      
    }