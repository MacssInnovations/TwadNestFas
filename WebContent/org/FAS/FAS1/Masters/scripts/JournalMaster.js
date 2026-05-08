
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
   document.frmJournalMaster.txtjournaltypecode.value="";
   document.frmJournalMaster.txtjournaltypedesc.value="";
   document.frmJournalMaster.cmbCategory.selectedIndex=0;
   document.frmJournalMaster.CmdAdd.disabled=false;
   document.frmJournalMaster.CmdUpdate.disabled=true;
   document.frmJournalMaster.CmdDelete.disabled=true;
   
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
                  
                  if((document.frmJournalMaster.txtjournaltypedesc.value=="") || (document.frmJournalMaster.txtjournaltypedesc.value.length<=0))
                  { 
                       alert("Please Enter the Journal Type Description");
                       document.frmJournalMaster.txtjournaltypedesc.focus();
                       return false;
                  }
                  
                  if((document.frmJournalMaster.cmbCategory.value=="") ||(document.frmJournalMaster.cmbCategory.value.length<=0) || (document.frmJournalMaster.cmbCategory.value=="0"))
                  {
                    alert("Please Select Category");
                    document.frmJournalMaster.cmbCategory.focus();
                    return false;
                  }
                  return true;
        }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var txtjournalledgertypecode=document.frmJournalMaster.txtjournaltypecode.value;
       var txtjournalledgertypedesc=document.frmJournalMaster.txtjournaltypedesc.value;
       var cmbcategory=document.frmJournalMaster.cmbCategory.value;
       var remark=document.frmJournalMaster.remark.value;
       if(document.frmJournalMaster.displayRes[0].checked==true)
       {
       var dis="Y";
       }
       else if(document.frmJournalMaster.displayRes[1].checked==true)
       {
       var dis="N";
       }
       
       if(document.frmJournalMaster.usageRes[0].checked==true)
       {
       var usageRes="Y";
       }
       else if(document.frmJournalMaster.usageRes[1].checked==true)
       {
       var usageRes="N";
       }
       
       
       
       
        var url="";
       
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../JournalMasterServlet.con?command=Add&JournalTypeDesc=" + txtjournalledgertypedesc+"&Category="+cmbcategory+"&display="+dis+"&remark="+remark+"&usageRes="+usageRes;
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
                    url="../../../../../JournalMasterServlet.con?command=Update&JournalTypeCode="+txtjournalledgertypecode+"&JournalTypeDesc=" + txtjournalledgertypedesc+"&Category="+cmbcategory+"&display="+dis+"&remark="+remark+"&usageRes="+usageRes;
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
                    url="../../../../../JournalMasterServlet.con?command=Delete&JournalTypeCode="+txtjournalledgertypecode+"&display="+dis+"&remark="+remark+"&usageRes="+usageRes;
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
            url="../../../../../JournalMasterServlet.con?command=Get";
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
            url="../../../../../JournalMasterServlet.con?command=Load&Category="+cmbcategory;
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
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");
                 did=baseResponse.getElementsByTagName("JournalTypeCode")[0].firstChild.nodeValue; 
                 alert("Your Journal Type Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("JournalTypeCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("JournalTypeDesc")[0].firstChild.nodeValue;
                 Category=baseResponse.getElementsByTagName("Category")[0].firstChild.nodeValue;
                  var dis=baseResponse.getElementsByTagName("display")[0].firstChild.nodeValue;
                  var remark=baseResponse.getElementsByTagName("remark")[0].firstChild.nodeValue;
                  var Usage=baseResponse.getElementsByTagName("Usage")[0].firstChild.nodeValue;
                        
                 //document.frmJournalMaster.txtjournaltypecode.value=cadid;
                 //document.frmJournalMaster.txtjournaltypedesc.value=document.frmJournalMaster.txtjournaltypedesc.value;
                 
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
                 var txtcategory=document.createTextNode(Category);                         
                 cell4.appendChild(txtcategory);       
                 mycurrent_row.appendChild(cell4);
                 
                 var cell5 =document.createElement("TD");    
                 var txtdis=document.createTextNode(dis);                         
                 cell5.appendChild(txtdis);       
                 mycurrent_row.appendChild(cell5);
                 
                 var cell6 =document.createElement("TD");    
                 var txtremark=document.createTextNode(remark);                         
                 cell6.appendChild(txtremark);       
                 mycurrent_row.appendChild(cell6);
                 
                 var cell7 =document.createElement("TD");    
                 var txtUsage=document.createTextNode(Usage);                         
                 cell7.appendChild(txtUsage);       
                 mycurrent_row.appendChild(cell7);
                 
                 
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmJournalMaster.CmdAdd.disabled=false;
                document.frmJournalMaster.CmdUpdate.disabled=true;
                document.frmJournalMaster.CmdDelete.disabled=true;           
                document.frmJournalMaster.txtjournaltypecode.value="";
                document.frmJournalMaster.txtjournaltypedesc.value="";
                document.frmJournalMaster.cmbCategory.selectedIndex=0;
                document.frmJournalMaster.remark.value="";
                document.frmJournalMaster.usageRes.selectedIndex=0;
               }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.frmJournalMaster.txtjournaltypecode.value;
               items[1]=document.frmJournalMaster.txtjournaltypedesc.value;
               items[2]=document.frmJournalMaster.cmbCategory.value;
               
               
               
               if(document.frmJournalMaster.displayRes[0].checked==true)
               {
               items[3]="Y";
               }
               else if(document.frmJournalMaster.displayRes[1].checked==true)
               {
               items[3]="N";
               }
               items[4]=document.frmJournalMaster.remark.value;
               
               if(document.frmJournalMaster.usageRes[0].checked==true)
               {
               items[5]="Y";
               }
               else if(document.frmJournalMaster.usageRes[1].checked==true)
               {
               items[5]="N";
               }
               
               
               var r=document.getElementById(items[0]);    
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                rcells.item(4).firstChild.nodeValue=items[3];
                rcells.item(5).firstChild.nodeValue=items[4];
                rcells.item(6).firstChild.nodeValue=items[5];
                document.frmJournalMaster.CmdAdd.disabled=false;
                document.frmJournalMaster.CmdUpdate.disabled=true;
                document.frmJournalMaster.CmdDelete.disabled=true;           
                document.frmJournalMaster.txtjournaltypecode.value="";
                document.frmJournalMaster.txtjournaltypedesc.value="";
                document.frmJournalMaster.cmbCategory.selectedIndex=0;
                document.frmJournalMaster.remark.value="";
                document.frmJournalMaster.usageRes.selectedIndex=0;
                           
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
                      var JournalTypeCode=baseResponse.getElementsByTagName("JournalTypeCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(JournalTypeCode);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmJournalMaster.txtjournaltypecode.value="";
                       document.frmJournalMaster.txtjournaltypedesc.value="";
                       document.frmJournalMaster.cmbCategory.selectedIndex=0;
                       document.frmJournalMaster.remark.value="";
                       document.frmJournalMaster.usageRes.selectedIndex=0;
                        document.frmJournalMaster.CmdAdd.disabled=false;
                        document.frmJournalMaster.CmdUpdate.disabled=true;
                        document.frmJournalMaster.CmdDelete.disabled=true;      
                               
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
                                             
                            var JournalTypeCode=baseResponse.getElementsByTagName("JournalTypeCode");
                            
                     for(var k=0;k<JournalTypeCode.length;k++)
                        {
                         var JournalTypeCode=baseResponse.getElementsByTagName("JournalTypeCode");
                         var JournalTypeDesc=baseResponse.getElementsByTagName("JournalTypeDesc");
                         var Category=baseResponse.getElementsByTagName("Category")[k].firstChild.nodeValue;
                         var dis=baseResponse.getElementsByTagName("display")[k].firstChild.nodeValue;
                         var remarks=baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
                         
                         if (remarks == 'null' || remarks == null) {
                        	 remarks = " ";
             				}
                         var usage=baseResponse.getElementsByTagName("usage")[k].firstChild.nodeValue;
                         if (usage == 'null' || usage == null) {
                        	 usage = " ";
             				}
                         
                         var cJournalTypeCode=JournalTypeCode.item(k).firstChild.nodeValue;
                         var cJournalTypeDesc=JournalTypeDesc.item(k).firstChild.nodeValue;
                       
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=cJournalTypeCode;
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");       
                         var url="javascript:loadValuesFromTable('" + cJournalTypeCode + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         mycurrent_row.appendChild(cell);
                     
                         var cell2 =document.createElement("TD");    
                         var txtjournalledgercode=document.createTextNode(cJournalTypeCode);                         
                         cell2.appendChild(txtjournalledgercode);       
                         mycurrent_row.appendChild(cell2);       

                         var cell3 =document.createElement("TD");    
                         var txtjournalledgerdesc=document.createTextNode(cJournalTypeDesc);                         
                         cell3.appendChild(txtjournalledgerdesc);       
                         mycurrent_row.appendChild(cell3);
                         
                         var cell4 =document.createElement("TD");    
                         var txtcategory=document.createTextNode(Category);                         
                         cell4.appendChild(txtcategory);       
                         mycurrent_row.appendChild(cell4);
                         
                         var cell5 =document.createElement("TD");    
                         var txtdis=document.createTextNode(dis);                         
                         cell5.appendChild(txtdis);       
                         mycurrent_row.appendChild(cell5);
                         
                         var cell6 =document.createElement("TD");    
                         var txtremarks=document.createTextNode(remarks);                         
                         cell6.appendChild(txtremarks);       
                         mycurrent_row.appendChild(cell6);
                         
                         var cell7 =document.createElement("TD");    
                         var txtusage=document.createTextNode(usage);                         
                         cell7.appendChild(txtusage);       
                         mycurrent_row.appendChild(cell7);
                         
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
          
          document.frmJournalMaster.txtjournaltypecode.value=rcells.item(1).firstChild.nodeValue;
          document.frmJournalMaster.txtjournaltypedesc.value=rcells.item(2).firstChild.nodeValue;
          document.frmJournalMaster.cmbCategory.value=rcells.item(3).firstChild.nodeValue;
          document.frmJournalMaster.remark.value=rcells.item(5).firstChild.nodeValue;
          
          
          
          document.frmJournalMaster.CmdAdd.disabled=true;
        document.frmJournalMaster.CmdUpdate.disabled=false;
        document.frmJournalMaster.CmdDelete.disabled=false;
        
          document.frmJournalMaster.CmdDelete.focus();
      
    }