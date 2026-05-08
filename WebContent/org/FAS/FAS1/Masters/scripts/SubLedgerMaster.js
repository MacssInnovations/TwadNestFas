
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
   document.frmSubledgerMaster.txtsubledgercode.value="";
   document.frmSubledgerMaster.txtsubledgerdesc.value="";
   document.frmSubledgerMaster.cmbStatus.selectedIndex=0;
   document.frmSubledgerMaster.CmdAdd.disabled=false;
   document.frmSubledgerMaster.CmdUpdate.disabled=true;
   document.frmSubledgerMaster.CmdDelete.disabled=true;
   
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
                  
                  if((document.frmSubledgerMaster.txtsubledgerdesc.value=="") || (document.frmSubledgerMaster.txtsubledgerdesc.value.length<=0))
                  { 
                       alert("Please Enter the SubLedger Description");
                       document.frmSubledgerMaster.txtsubledgerdesc.focus();
                       return false;
                  }
                  
                  if((document.frmSubledgerMaster.cmbStatus.value=="") ||(document.frmSubledgerMaster.cmbStatus.value.length<=0) || (document.frmSubledgerMaster.cmbStatus.value=="0"))
                  {
                    alert("Please Select Status");
                    document.frmSubledgerMaster.cmbStatus.focus();
                    return false;
                  }
                  return true;
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var txtsubledgercode=document.frmSubledgerMaster.txtsubledgercode.value;
       var txtsubledgerdesc=document.frmSubledgerMaster.txtsubledgerdesc.value;
       var cmbStatus=document.frmSubledgerMaster.cmbStatus.value;
        var url="";
        
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../SubLedgerMaster?command=Add&SubLedgerDesc=" + txtsubledgerdesc+"&Status="+cmbStatus;
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
                    url="../../../../../SubLedgerMaster?command=Update&SubLedgerCode="+txtsubledgercode+"&SubLedgerDesc=" + txtsubledgerdesc+"&Status="+cmbStatus;
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
                    url="../../../../../SubLedgerMaster?command=Delete&SubLedgerCode="+txtsubledgercode;
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
            url="../../../../../SubLedgerMaster?command=Get";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
                    req.send(null);
        }
        else if(command=="Asset")
        {               
            url="../../../../../AssetClassMasterServlet.con?command=Asset";
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
            url="../../../../../AssetClassMasterServlet.con?command=Load&AssetType="+cmbAssetType;
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
              else if(command=="Asset")
              { 
              assetRow(baseResponse);
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
                         
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");
                 did=baseResponse.getElementsByTagName("Slcode")[0].firstChild.nodeValue; 
                 alert("Your SubLedger Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("Slcode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("SubLedgerDesc")[0].firstChild.nodeValue;
                 AssetType=baseResponse.getElementsByTagName("StatusCurr")[0].firstChild.nodeValue;
                 //document.frmAssetClassMaster.txtassetclasscode.value=cadid;
                 //document.frmAssetClassMaster.txtassetclassdesc.value=document.frmAssetClassMaster.txtassetclassdesc.value;
                 
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
                 
                 var cell4 =document.createElement("TD");    
                 var txtassettype=document.createTextNode(AssetType);                         
                 cell4.appendChild(txtassettype);       
                 mycurrent_row.appendChild(cell4);
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmAssetClassMaster.CmdAdd.disabled=false;
                document.frmAssetClassMaster.CmdUpdate.disabled=true;
                document.frmAssetClassMaster.CmdDelete.disabled=true;           
                document.frmAssetClassMaster.txtassetclasscode.value="";
                document.frmAssetClassMaster.txtassetclassdesc.value="";
                document.frmAssetClassMaster.cmbAssetType.selectedIndex=0;
               }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.frmSubledgerMaster.txtsubledgercode.value;
               items[1]=document.frmSubledgerMaster.txtsubledgerdesc.value;
               items[2]=document.frmSubledgerMaster.cmbStatus.value;
               var r=document.getElementById(items[0]);    
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                document.frmSubledgerMaster.CmdAdd.disabled=false;
                document.frmSubledgerMaster.CmdUpdate.disabled=true;
                document.frmSubledgerMaster.CmdDelete.disabled=true;           
                document.frmSubledgerMaster.txtsubledgercode.value="";
                document.frmSubledgerMaster.txtsubledgerdesc.value="";
                document.frmSubledgerMaster.cmbStatus.selectedIndex=0;
                           
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
                      var SubLedgerCode=baseResponse.getElementsByTagName("SubLedgerCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(SubLedgerCode);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmSubledgerMaster.txtsubledgercode.value="";
                       document.frmSubledgerMaster.txtsubledgerdesc.value="";
                       document.frmSubledgerMaster.cmbStatus.selectedIndex=0;
                        document.frmSubledgerMaster.CmdAdd.disabled=false;
                        document.frmSubledgerMaster.CmdUpdate.disabled=true;
                        document.frmSubledgerMaster.CmdDelete.disabled=true;      
                               
                      alert("Selected SubledgerDescription are Deleted");                      
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
                      /*  xml=xml+ "<SubLedgerTypeCode>" + SubLedgerTypeCode +
                        "</SubLedgerTypeCode><SubLedgerTypeDesc>" + SubLedgerTypeDesc + "</SubLedgerTypeDesc><Status>"+Status+"</Status>" +
                        		"<SubLedgerCode>"+SubLedgerCode+"</SubLedgerCode>";*/
                                             
                            var SubLedgerTypeCode=baseResponse.getElementsByTagName("SubLedgerTypeCode");
                            
                     for(var k=0;k<SubLedgerTypeCode.length;k++)
                        {
                    	                         
                         var SubLedgerTypeCode=baseResponse.getElementsByTagName("SubLedgerTypeCode");
                        
                         var SubLedgerTypeDesc=baseResponse.getElementsByTagName("SubLedgerTypeDesc");
                         var Status=baseResponse.getElementsByTagName("Status")[k].firstChild.nodeValue;
                         
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
                         var txtassetclasscode=document.createTextNode(cSubLedgerTypeCode);                         
                         cell2.appendChild(txtassetclasscode);       
                         mycurrent_row.appendChild(cell2);       

                         var cell3 =document.createElement("TD");    
                         var txtassetclassdesc=document.createTextNode(cSubLedgerTypeDesc);                         
                         cell3.appendChild(txtassetclassdesc);       
                         mycurrent_row.appendChild(cell3);
                         
                         var cell4 =document.createElement("TD");    
                         var txtassettype=document.createTextNode(Status);                         
                         cell4.appendChild(txtassettype);       
                         mycurrent_row.appendChild(cell4);
                         tbody.appendChild(mycurrent_row);
                        }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
}

function assetRow(baseResponse)
{
        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
            
              if(flag=="success")
              {
                var cmbAssetType=document.getElementById("cmbAssetType");
                var option=document.createElement("OPTION");
                cmbAssetType.innerHTML="";
                    option.text="--Select AssetType--";
                    try
                                {
                                    cmbAssetType.add(option);
                            }catch(errorobject)
                            { 
                                     cmbAssetType.add(option,null);
                            }
                            
                var AssetTypeCode=baseResponse.getElementsByTagName("options"); 
                
                for(var i=0;i<AssetTypeCode.length;i++)
                {
                    var tmpoption=AssetTypeCode.item(i);
                    
                    var AssetTypeCode1=tmpoption.getElementsByTagName("AssetTypeCode")[0].firstChild.nodeValue;
                    var AssetTypeDesc1=tmpoption.getElementsByTagName("AssetTypeDesc")[0].firstChild.nodeValue;
                    var option=document.createElement("OPTION");
                        option.text=AssetTypeDesc1;
                        option.value=AssetTypeCode1;
                              try
                                {
                                    cmbAssetType.add(option);
                            }catch(errorobject)
                            { 
                                     cmbAssetType.add(option,null);
                            }
                }
              }
    
}

function loadValuesFromTable(rid)
    {      
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          
          document.frmSubledgerMaster.txtsubledgercode.value=rcells.item(1).firstChild.nodeValue;
          document.frmSubledgerMaster.txtsubledgerdesc.value=rcells.item(2).firstChild.nodeValue;
          document.frmSubledgerMaster.cmbStatus.value=rcells.item(3).firstChild.nodeValue;
          document.frmSubledgerMaster.CmdAdd.disabled=true;
        document.frmSubledgerMaster.CmdUpdate.disabled=false;
        document.frmSubledgerMaster.CmdDelete.disabled=false;
        
          document.frmAssetClassMaster.CmdDelete.focus();
      
    }

