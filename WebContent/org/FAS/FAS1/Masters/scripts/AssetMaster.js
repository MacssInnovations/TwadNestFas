
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
   document.frmAssetMaster.txtassettypecode.value="";
   document.frmAssetMaster.txtassettypedesc.value="";
   
   document.frmAssetMaster.CmdAdd.disabled=false;
   document.frmAssetMaster.CmdUpdate.disabled=true;
   document.frmAssetMaster.CmdDelete.disabled=true;
   
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
                  
                  if((document.frmAssetMaster.txtassettypecode.value=="") || (document.frmAssetMaster.txtassettypecode.value.length<=0))
                  { 
                       alert("Please Enter the Asset Type Code");
                       document.frmAssetMaster.txtassettypecode.focus();
                       return false;
                  }
                  
                  if((document.frmAssetMaster.txtassettypedesc.value=="") || (document.frmAssetMaster.txtassettypedesc.value.length<=0))
                  { 
                       alert("Please Enter the Asset Type Description");
                       document.frmAssetMaster.txtassettypedesc.focus();
                       return false;
                  }
                  
                  
                  return true;
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var txtassetledgertypecode=document.frmAssetMaster.txtassettypecode.value;
       var txtassetledgertypedesc=document.frmAssetMaster.txtassettypedesc.value;
       
        var url="";
        
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../AssetMasterServlet.con?command=Add&AssetTypeCode="+txtassetledgertypecode+"&AssetTypeDesc=" + txtassetledgertypedesc;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }
                   
                    
        }
        else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../AssetMasterServlet.con?command=Update&AssetTypeCode="+txtassetledgertypecode+"&AssetTypeDesc=" + txtassetledgertypedesc;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }

        }
        
        else if(command=="Delete")
        {  
        		   url="../../../../../AssetMasterServlet.con?command=Delete&AssetTypeCode="+txtassetledgertypecode+"&AssetTypeDesc=" + txtassetledgertypedesc;
                   var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        }
        else if(command=="Get")
        {               
            url="../../../../../AssetMasterServlet.con?command=Get";
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            };   
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
              deleteRow(baseResponse);
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
                 did=baseResponse.getElementsByTagName("AssetTypeCode")[0].firstChild.nodeValue; 
                 //alert("Your Asset Type Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("AssetTypeCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("AssetTypeDesc")[0].firstChild.nodeValue;
                 stat=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
                 
                 //document.frmAssetMaster.txtassettypecode.value=cadid;
                 //document.frmAssetMaster.txtassettypedesc.value=document.frmAssetMaster.txtassettypedesc.value;
                 
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
                 
                 var cell3 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(stat);                         
                 cell3.appendChild(txtsdesc);       
                 mycurrent_row.appendChild(cell3);
                 
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmAssetMaster.CmdAdd.disabled=false;
                document.frmAssetMaster.CmdUpdate.disabled=true;
                document.frmAssetMaster.CmdDelete.disabled=true;           
                document.frmAssetMaster.txtassettypecode.value="";
                document.frmAssetMaster.txtassettypedesc.value="";
                
               }
                if(flag=="AlreadyExist"){
                alert("Record already exists for this Asset Type");
                clearAll();
                }
               
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
              alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.frmAssetMaster.txtassettypecode.value;
               items[1]=document.frmAssetMaster.txtassettypedesc.value;
               //alert(items[0]);
               var r=document.getElementById(items[0]); 
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                
                document.frmAssetMaster.CmdAdd.disabled=false;
                document.frmAssetMaster.CmdUpdate.disabled=true;
                document.frmAssetMaster.CmdDelete.disabled=true;           
                document.frmAssetMaster.txtassettypecode.value="";
                document.frmAssetMaster.txtassettypedesc.value="";
                
                           
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
                     /* var AssetTypeCode=baseResponse.getElementsByTagName("AssetTypeCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(AssetTypeCode);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); */
                       document.frmAssetMaster.txtassettypecode.value="";
                       document.frmAssetMaster.txtassettypedesc.value="";
                       alert("Selected Asset Type Details are Cancelled");
                       document.frmAssetMaster.CmdAdd.disabled=false;
                       document.frmAssetMaster.CmdUpdate.disabled=true;
                       document.frmAssetMaster.CmdDelete.disabled=true;      
                       callServer('Get','');        
                                            
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
                                             
                            var AssetTypeCode=baseResponse.getElementsByTagName("AssetTypeCode");
                            
                     for(var k=0;k<AssetTypeCode.length;k++)
                        {
                         var AssetTypeCode=baseResponse.getElementsByTagName("AssetTypeCode");
                         var AssetTypeDesc=baseResponse.getElementsByTagName("AssetTypeDesc");
                         var view=baseResponse.getElementsByTagName("status").item(k).firstChild.nodeValue;
                     //  alert("view:"+view);
                         var cAssetTypeCode=AssetTypeCode.item(k).firstChild.nodeValue;
                         var cAssetTypeDesc=AssetTypeDesc.item(k).firstChild.nodeValue;
                       
                         var mycurrent_row=document.createElement("TR");
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");
                         mycurrent_row.id=cAssetTypeCode;
                         if(view=="C"){
                        	 var priceSpan = document.createElement("span");
                 			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
                 			priceSpan.appendChild(document.createTextNode("Cancel"));			
                 			cell.appendChild(priceSpan);
                 			mycurrent_row.appendChild(cell);
                         }else{
                        	 var url="javascript:loadValuesFromTable('" + cAssetTypeCode + "')";              
                             anc.href=url;
                             var txtedit=document.createTextNode("Edit");
                             anc.appendChild(txtedit);
                             cell.appendChild(anc);
                             mycurrent_row.appendChild(cell);
                         }                 
                     
                         var cell2 =document.createElement("TD");    
                         var txtassetledgercode=document.createTextNode(cAssetTypeCode);                         
                         cell2.appendChild(txtassetledgercode);       
                         mycurrent_row.appendChild(cell2);       

                         var cell3 =document.createElement("TD");    
                         var txtassetledgerdesc=document.createTextNode(cAssetTypeDesc);                         
                         cell3.appendChild(txtassetledgerdesc);       
                         mycurrent_row.appendChild(cell3);
                         
                         var cell4=document.createElement("TD");
                         if(view=="L"){
                        	 var txtassetledgerdesc=document.createTextNode("LIVE");
                         }else{
                        	 var txtassetledgerdesc=document.createTextNode("CANCEL");
                         }
                         cell4.appendChild(txtassetledgerdesc);       
                         mycurrent_row.appendChild(cell4);
                         
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
          
          document.frmAssetMaster.txtassettypecode.value=rcells.item(1).firstChild.nodeValue;
          document.frmAssetMaster.txtassettypedesc.value=rcells.item(2).firstChild.nodeValue;
          
          document.frmAssetMaster.CmdAdd.disabled=true;
        document.frmAssetMaster.CmdUpdate.disabled=false;
        document.frmAssetMaster.CmdDelete.disabled=false;
        
          document.frmAssetMaster.CmdDelete.focus();
      
    }