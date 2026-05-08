
var seq=0;
var com_id;
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
   document.CusForm.txtMainId.selectedindex=0;
   document.CusForm.txtSubId.value="";
   document.CusForm.txtDesc.value="";
   document.CusForm.CmdAdd.disabled=false;
   document.CusForm.CmdUpdate.disabled=true;
   document.CusForm.CmdDelete.disabled=true;
   document.CusForm.txtMainId.disabled=false;
       document.CusForm.txtSubId.disabled=false;
       
  
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
                 
                  if((document.CusForm.txtDesc.value=="") || (document.CusForm.txtDesc.value.length<=0))
                  {
                       alert("Please Enter the Customer Sub Type Description");
                       document.CusForm.txtDesc.focus();
                       return false;
                  }
                 if((document.CusForm.txtSubId.value=="") || (document.CusForm.txtSubId.value.length<=0))
                  {
                       alert("Please Enter the Customer Sub Type Id");
                       document.CusForm.txtSubId.focus();
                       return false;
                  }
                  if((document.CusForm.txtMainId.value=="") ||(document.CusForm.txtMainId.value.length<=0) || (document.CusForm.txtMainId.value=="0"))
                  {
                    alert("Please Select Customer Main Id");
                    document.CusForm.txtMainId.focus();
                    return false;
                  }
                  return true;
        }
       
       
//******************************************** CallServer Coding *******************//

function callServer(command,param)
 {
  
       var txtcustomermainid=document.CusForm.txtMainId.value;
       //var cmbcategory=document.CusForm.cmbCategory.value;
       var txtcustomersubid=document.CusForm.txtSubId.value;
       //var txtjournalledgertypecode=document.CusForm.txtjournaltypecode.value;
       var txtcustomerdesc=document.CusForm.txtDesc.value;
       //var txtjournalledgertypedesc=document.CusForm.txtjournaltypedesc.value;
       
        var url="";
       
      
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../../customersub_servlet.view?command=Add&CustomerDesc=" + txtcustomerdesc+"&MainId="+txtcustomermainid+"&SubId="+txtcustomersubid;
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
                    url="../../../../../../customersub_servlet.view?command=Update&MainId="+txtcustomermainid+"&SubId=" + txtcustomersubid+"&CustomerDesc="+txtcustomerdesc;
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
                    url="../../../../../../customersub_servlet.view?command=Delete&MainId="+txtcustomermainid+"&SubId="+txtcustomersubid;
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
            url="../../../../../../customersub_servlet.view?command=Get";
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
                 did=baseResponse.getElementsByTagName("CustomerMainCode")[0].firstChild.nodeValue;
                 
                 var items=new Array();                                    
                 cadid=baseResponse.getElementsByTagName("CustomerMainCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("CustomerSubCode")[0].firstChild.nodeValue;
                 Category=baseResponse.getElementsByTagName("CustomerDesc")[0].firstChild.nodeValue;
                 //document.CusForm.txtjournaltypecode.value=cadid;
                 //document.CusForm.txtjournaltypedesc.value=document.CusForm.txtjournaltypedesc.value;
                
                 var tbody=document.getElementById("tblList");
                 var table=document.getElementById("Existing");
                 /*       var t=0;
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }   */
                 seq=seq+1;                         
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;
                 var cell=document.createElement("TD");
                 var anc=document.createElement("A");      
                
                 var url="javascript:loadValuesFromTable('" + seq + "')";             
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
                
                 tbody.appendChild(mycurrent_row);
               
                 document.CusForm.CmdAdd.disabled=false;
                document.CusForm.CmdUpdate.disabled=true;
                document.CusForm.CmdDelete.disabled=true;          
                document.CusForm.txtMainId.selectedIndex=0;
                document.CusForm.txtSubId.value="";
                document.CusForm.txtDesc.value="";
                 }
                 else
                 {
                 alert("Record already exists, Insertion not possible");
                 }
}
function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {  
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.CusForm.txtMainId.value;
               items[1]=document.CusForm.txtSubId.value;
               items[2]=document.CusForm.txtDesc.value;
               var r=document.getElementById(com_id);   
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                document.CusForm.CmdAdd.disabled=false;
                document.CusForm.CmdUpdate.disabled=true;
                document.CusForm.CmdDelete.disabled=true;          
                document.CusForm.txtMainId.selectedIndex=0;
                document.CusForm.txtSubId.value="";
                document.CusForm.txtDesc.value="";
                 document.CusForm.txtMainId.disabled=false;
       document.CusForm.txtSubId.disabled=false;
       
                                         
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
                      var customermaincode=baseResponse.getElementsByTagName("CustomerMainCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");    
                      var r=document.getElementById(com_id);   
                      var ri=r.rowIndex;              
                      tbody.deleteRow(ri);
                       document.CusForm.txtMainId.selectedIndex=0;
                       document.CusForm.txtSubId.value="";
                       document.CusForm.txtDesc.value="";
                        document.CusForm.CmdAdd.disabled=false;
                        document.CusForm.CmdUpdate.disabled=true;
                        document.CusForm.CmdDelete.disabled=true; 
                         document.CusForm.txtMainId.disabled=false;
       document.CusForm.txtSubId.disabled=false;
       
                              
                      alert("Selected Records are Deleted");                     
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
                                            
                            var CustomerMainCode=baseResponse.getElementsByTagName("CustomerMainCode");
                    
                     for(var k=0;k<CustomerMainCode.length;k++)
                        {
                        //var CustomerMainCode=baseResponse.getElementsByTagName("CustomerMainCode")[k].firstChild.nodeValue;
                        var CustomerSubCode=baseResponse.getElementsByTagName("CustomerSubCode");
                        var CustomerDesc=baseResponse.getElementsByTagName("CustomerDesc");
                        var CustomerMainCode=baseResponse.getElementsByTagName("CustomerMainCode");
                        var cCustomerMainCode=CustomerMainCode.item(k).firstChild.nodeValue;
                        var cCustomerSubCode=CustomerSubCode.item(k).firstChild.nodeValue;
                        var cCustomerDesc=CustomerDesc.item(k).firstChild.nodeValue;
                        seq=seq+1;
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=seq;
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");      
                         var url="javascript:loadValuesFromTable('" + seq + "')";             
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         mycurrent_row.appendChild(cell);
                    
                         var cell2 =document.createElement("TD");   
                         var txtcustomermaincode=document.createTextNode(cCustomerMainCode);                        
                         cell2.appendChild(txtcustomermaincode);      
                         mycurrent_row.appendChild(cell2);      
                         var cell3 =document.createElement("TD");   
                         var txtcustomersubcode=document.createTextNode(cCustomerSubCode);                        
                         cell3.appendChild(txtcustomersubcode);      
                         mycurrent_row.appendChild(cell3);
                        
                         var cell4 =document.createElement("TD");   
                         var txtcustomerdesc=document.createTextNode(cCustomerDesc);                        
                         cell4.appendChild(txtcustomerdesc);      
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
          com_id=rid;
          var r=document.getElementById(rid);
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
        document.CusForm.txtMainId.value=rcells.item(1).firstChild.nodeValue;
          document.CusForm.txtSubId.value=rcells.item(2).firstChild.nodeValue;
          document.CusForm.txtDesc.value=rcells.item(3).firstChild.nodeValue;
          document.CusForm.CmdAdd.disabled=true;
        document.CusForm.CmdUpdate.disabled=false;
        document.CusForm.CmdDelete.disabled=false;
       document.CusForm.txtMainId.disabled=true;
       document.CusForm.txtSubId.disabled=true;
          document.CusForm.CmdDelete.focus();
     
    }