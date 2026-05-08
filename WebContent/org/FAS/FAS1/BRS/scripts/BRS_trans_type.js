
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
	 document.frmBRS_trans.txtbrscode.value="";
     document.frmBRS_trans.txtbrs_shdesc.value="";
     document.frmBRS_trans.txtdesc.value="";
      document.frmBRS_trans.txtbrstype[0].checked==false;
      document.frmBRS_trans.txtbrstype[1].checked==false;
         //document.frmBRS_trans.txtbrstype.value="";
          
   document.frmBRS_trans.CmdAdd.disabled=false;
   document.frmBRS_trans.CmdUpdate.disabled=true;
   document.frmBRS_trans.CmdDelete.disabled=true;
   
   var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        callServer('Get','null');
 }
 
 function Exit()
 {
    self.close();
 }
 
 
 
 //******************************Validation Checking**************************//
 function nullCheck()
        {
                  
                  if((document.frmBRS_trans.txtbrsdesc.value=="") || (document.frmBRS_trans.txtbrsdesc.value.length<=0))
                  { 
                       alert("Please Enter the Trans Description");
                       document.frmBRS_trans.txtbrsdesc.focus();
                       return false;
                  }
                  
                  if((document.frmBRS_trans.txtbrs_shdesc.value=="") ||(document.frmBRS_trans.txtbrs_shdesc.value.length<=0))
                  {
                    alert("Please Enter the Trans Short Description");
                    document.frmBRS_trans.txtbrs_shdesc.focus();
                    return false;
                  }
                  return true;
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var TransTypeDesc=document.frmBRS_trans.txtdesc.value;
       var TransTypeShDesc=document.frmBRS_trans.txtbrs_shdesc.value;
      
       if(document.frmBRS_trans.txtbrstype[0].checked==true)
          var TransType="T";
        else if(document.frmBRS_trans.txtbrstype[0].checked==false)
          var TransType="NT";
    
       var TransTypeCode=document.frmBRS_trans.txtbrscode.value;
        var url="";
     
  
      
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../BRS_trans_type.con?command=Add&TransTypeDesc=" + TransTypeDesc+"&TransTypeShDesc="+TransTypeShDesc+"&TransType="+TransType;
                  
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
                    url="../../../../../BRS_trans_type.con?command=Update&TransTypeCode="+TransTypeCode+"&TransTypeDesc=" + TransTypeDesc+"&TransTypeShDesc="+TransTypeShDesc+"&TransType="+TransType;
                  //alert(url);
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
                    url="../../../../../BRS_trans_type.con?command=Delete&TransTypeCode="+TransTypeCode;
                    //alert(url);
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
            url="../../../../../BRS_trans_type.con?command=Get";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
        else if(command=="Load")
        {               
            url="../../../../../BRS_trans_type.con?command=Load&Category="+cmbcategory;
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
              else if(command=="Load")
              {
              getRow(baseResponse);
              }
              
          }
        }
  }


function addRow(baseResponse)
{
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
             // alert(flag);
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");
                 did=baseResponse.getElementsByTagName("TransTypeCode")[0].firstChild.nodeValue; 
                 alert("Your TransTypeCode Is " +  did);
                 var items=new Array();                                     
                 transid=baseResponse.getElementsByTagName("TransTypeCode")[0].firstChild.nodeValue;
                 alert(transid);
                 trdesc=baseResponse.getElementsByTagName("TransTypeDesc")[0].firstChild.nodeValue;
                 trshdesc=baseResponse.getElementsByTagName("TransTypeShDesc")[0].firstChild.nodeValue;
                 trtype=baseResponse.getElementsByTagName("TransType")[0].firstChild.nodeValue;
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
         /* var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   */
                                             
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=transid;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");       
                 var url="javascript:loadValuesFromTable('" + transid + "')";              
                 anc.href=url;
                 var txtedit=document.createTextNode("Edit");
                 anc.appendChild(txtedit);
                 cell.appendChild(anc);
                 mycurrent_row.appendChild(cell);
             
                 var cell2 =document.createElement("TD");    
                 var txttransid=document.createTextNode(transid);                         
                 cell2.appendChild(txttransid);       
                 mycurrent_row.appendChild(cell2);       

                 var cell3 =document.createElement("TD");    
                 var txtshdesc=document.createTextNode(trshdesc);                         
                 cell3.appendChild(txtshdesc);       
                 mycurrent_row.appendChild(cell3);
                 
                 var cell4 =document.createElement("TD");    
                 var txtdesc=document.createTextNode(trdesc);                         
                 cell4.appendChild(txtdesc);       
                 mycurrent_row.appendChild(cell4);
                 
                 var cell5 =document.createElement("TD");    
                 var txttrtype=document.createTextNode(trtype);                         
                 cell5.appendChild(txttrtype);       
                 mycurrent_row.appendChild(cell5);
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmBRS_trans.CmdAdd.disabled=false;
                document.frmBRS_trans.CmdUpdate.disabled=true;
                document.frmBRS_trans.CmdDelete.disabled=true;           
                document.frmBRS_trans.txtbrscode.value="";
                document.frmBRS_trans.txtbrs_shdesc.value="";
                document.frmBRS_trans.txtbrsdesc.value="";
                document.frmBRS_trans.txtbrstype.value="";
               }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               
               items[0]=document.frmBRS_trans.txtbrscode.value;
               items[1]=document.frmBRS_trans.txtbrs_shdesc.value;
               items[2]=document.frmBRS_trans.txtdesc.value;
               items[3]=document.frmBRS_trans.txtbrstype.value;
               var r=document.getElementById(items[0]);    
               var rcells=r.cells;
              // alert(items[0])
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                rcells.item(4).firstChild.nodeValue=items[3];
                document.frmBRS_trans.CmdAdd.disabled=false;
                document.frmBRS_trans.CmdUpdate.disabled=true;
                document.frmBRS_trans.CmdDelete.disabled=true;           
                document.frmBRS_trans.txtbrscode.value="";
                document.frmBRS_trans.txtbrs_shdesc.value="";
                document.frmBRS_trans.txtdesc.value="";
                document.frmBRS_trans.txtbrstype.value="";
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
                	  var TransTypecode=baseResponse.getElementsByTagName("TransTypeCode")[0].firstChild.nodeValue;
                     
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(TransTypecode);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmBRS_trans.txtbrscode.value="";
                       document.frmBRS_trans.txtbrsdesc.value="";
                       document.frmBRS_trans.cmbCategory.selectedIndex=0;
                        document.frmBRS_trans.CmdAdd.disabled=false;
                        document.frmBRS_trans.CmdUpdate.disabled=true;
                        document.frmBRS_trans.CmdDelete.disabled=true;      
                               
                      alert("Selected BRS Type Details are Deleted");                      
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
              {         // alert(flag);
                       var tbody=document.getElementById("tblList");
                       var table=document.getElementById("Existing");
                        var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   
                                             
                            var TransTypeCode=baseResponse.getElementsByTagName("TransTypeCode");
                          //  alert(TransTypeCode);
                     for(var k=0;k<TransTypeCode.length;k++)
                        {
                         var TransTypeCode=baseResponse.getElementsByTagName("TransTypeCode");
                         var TransTypeShDesc=baseResponse.getElementsByTagName("TransTypeShDesc");
                         var TransTypeDesc=baseResponse.getElementsByTagName("TransTypeDesc");
                         var TransType=baseResponse.getElementsByTagName("TransType");
                        // var Category=baseResponse.getElementsByTagName("Category")[k].firstChild.nodeValue;
                         
                         var cTransTypeCode=TransTypeCode.item(k).firstChild.nodeValue;
                         //alert(cTransTypeCode);
                         var cTransTypeDesc=TransTypeDesc.item(k).firstChild.nodeValue;
                         var cTransTypeShDesc=TransTypeShDesc.item(k).firstChild.nodeValue;
                         var cTransType=TransType.item(k).firstChild.nodeValue;
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=cTransTypeCode;
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");       
                         var url="javascript:loadValuesFromTable('" + cTransTypeCode + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         mycurrent_row.appendChild(cell);
                     
                         var cell2 =document.createElement("TD");    
                         var txtTransTypeCode=document.createTextNode(cTransTypeCode);                         
                         cell2.appendChild(txtTransTypeCode);       
                         mycurrent_row.appendChild(cell2);       

                         var cell3 =document.createElement("TD");    
                         var txtTransTypeShdesc=document.createTextNode(cTransTypeShDesc);                         
                         cell3.appendChild(txtTransTypeShdesc);       
                         mycurrent_row.appendChild(cell3);
                         //alert(cTransTypeDesc);
                         var cell4 =document.createElement("TD");    
                         var txtTransTypedesc=document.createTextNode(cTransTypeDesc);                         
                         cell4.appendChild(txtTransTypedesc);       
                         mycurrent_row.appendChild(cell4);
                         
                         var cell5 =document.createElement("TD");    
                         var txtTransType=document.createTextNode(cTransType);                         
                         cell5.appendChild(txtTransType);       
                         mycurrent_row.appendChild(cell5);
                         tbody.appendChild(mycurrent_row);
                        }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
}



function loadValuesFromTable(rid)
    {   // alert("inside load")  
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          document.frmBRS_trans.txtbrscode.value=rcells.item(1).firstChild.nodeValue;
          document.frmBRS_trans.txtbrs_shdesc.value=rcells.item(2).firstChild.nodeValue;
          document.frmBRS_trans.txtdesc.value=rcells.item(3).firstChild.nodeValue;
          document.frmBRS_trans.txtbrstype.value=rcells.item(4).firstChild.nodeValue;
        document.frmBRS_trans.CmdAdd.disabled=false;
        document.frmBRS_trans.CmdUpdate.disabled=true;
        document.frmBRS_trans.CmdDelete.disabled=true;
        
          
          document.frmBRS_trans.CmdAdd.disabled=true;
        document.frmBRS_trans.CmdUpdate.disabled=false;
        document.frmBRS_trans.CmdDelete.disabled=false;
        
          document.frmBRS_trans.CmdDelete.focus();
      
    }