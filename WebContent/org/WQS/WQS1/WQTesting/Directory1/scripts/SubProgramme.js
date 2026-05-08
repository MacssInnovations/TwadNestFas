
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
   document.ProgForm.txtMainId.selectedindex=0;
   document.ProgForm.txtSubId.value="";
   document.ProgForm.txtDesc.value="";
   document.ProgForm.CmdAdd.disabled=false;
   document.ProgForm.CmdUpdate.disabled=true;
   document.ProgForm.CmdDelete.disabled=true;
   document.ProgForm.txtMainId.disabled=false;
          document.ProgForm.txtSubId.disabled=false;
  
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
                 
                  if((document.ProgForm.txtDesc.value=="") || (document.ProgForm.txtDesc.value.length<=0))
                  {
                       alert("Please Enter the Programme Sub Type Description");
                       document.ProgForm.txtDesc.focus();
                       return false;
                  }
                 if((document.ProgForm.txtSubId.value=="") || (document.ProgForm.txtSubId.value.length<=0))
                  {
                       alert("Please Enter the Programme Sub Type Id");
                       document.ProgForm.txtSubId.focus();
                       return false;
                  }
                  if((document.ProgForm.txtMainId.value=="") ||(document.ProgForm.txtMainId.value.length<=0) || (document.ProgForm.txtMainId.value=="0"))
                  {
                    alert("Please Select Programme Main Id");
                    document.ProgForm.txtMainId.focus();
                    return false;
                  }
                  return true;
        }
       
       
//******************************************** CallServer Coding *******************//

function callServer(command,param)
 {
  
       var txtprogrammemainid=document.ProgForm.txtMainId.value;
       //var cmbcategory=document.ProgForm.cmbCategory.value;
       var txtprogrammesubid=document.ProgForm.txtSubId.value;
       //var txtjournalledgertypecode=document.ProgForm.txtjournaltypecode.value;
       var txtprogrammedesc=document.ProgForm.txtDesc.value;
       //var txtjournalledgertypedesc=document.ProgForm.txtjournaltypedesc.value;
       
        var url="";
       
      
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../../subprogramme_servlet.view?command=Add&ProgrammeDesc=" + txtprogrammedesc+"&MainId="+txtprogrammemainid+"&SubId="+txtprogrammesubid;
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
                    url="../../../../../../subprogramme_servlet.view?command=Update&MainId="+txtprogrammemainid+"&SubId=" + txtprogrammesubid+"&ProgrammeDesc="+txtprogrammedesc;
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
                    url="../../../../../../subprogramme_servlet.view?command=Delete&MainId="+txtprogrammemainid+"&SubId="+txtprogrammesubid;
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
            url="../../../../../../subprogramme_servlet.view?command=Get";
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
                 did=baseResponse.getElementsByTagName("ProgrammeMainCode")[0].firstChild.nodeValue;
                 alert("Programme Main Code Is " +  did);
                 var items=new Array();                                    
                 cadid=baseResponse.getElementsByTagName("ProgrammeMainCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("ProgrammeSubCode")[0].firstChild.nodeValue;
                 Category=baseResponse.getElementsByTagName("ProgrammeDesc")[0].firstChild.nodeValue;
                 //document.ProgForm.txtjournaltypecode.value=cadid;
                 //document.ProgForm.txtjournaltypedesc.value=document.ProgForm.txtjournaltypedesc.value;
                
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
               
                 document.ProgForm.CmdAdd.disabled=false;
                document.ProgForm.CmdUpdate.disabled=true;
                document.ProgForm.CmdDelete.disabled=true;          
                document.ProgForm.txtMainId.selectedIndex=0;
                document.ProgForm.txtSubId.value="";
                document.ProgForm.txtDesc.value="";
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
               items[0]=document.ProgForm.txtMainId.value;
               items[1]=document.ProgForm.txtSubId.value;
               items[2]=document.ProgForm.txtDesc.value;
               var r=document.getElementById(com_id);   
               var rcells=r.cells;
                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
                document.ProgForm.CmdAdd.disabled=false;
                document.ProgForm.CmdUpdate.disabled=true;
                document.ProgForm.CmdDelete.disabled=true;          
                document.ProgForm.txtMainId.selectedIndex=0;
                document.ProgForm.txtSubId.value="";
                document.ProgForm.txtDesc.value="";
                document.ProgForm.txtMainId.disabled=false;
          document.ProgForm.txtSubId.disabled=false;
  
                                         
       }
       else
       {
           alert("Failed to update values");
       }                                 
    }
 
function deleteRow(baseResponse)
  {
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
                  if(flag=="success")
                  {
                      var programmemaincode=baseResponse.getElementsByTagName("ProgrammeMainCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");    
                      var r=document.getElementById(com_id);   
                      var ri=r.rowIndex;              
                      tbody.deleteRow(ri);
                       document.ProgForm.txtMainId.selectedIndex=0;
                       document.ProgForm.txtSubId.value="";
                       document.ProgForm.txtDesc.value="";
                        document.ProgForm.CmdAdd.disabled=false;
                        document.ProgForm.CmdUpdate.disabled=true;
                        document.ProgForm.CmdDelete.disabled=true;     
                        document.ProgForm.txtMainId.disabled=false;
          document.ProgForm.txtSubId.disabled=false;
  
                              
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
                                            
                            var ProgrammeMainCode=baseResponse.getElementsByTagName("ProgrammeMainCode");
                    
                     for(var k=0;k<ProgrammeMainCode.length;k++)
                        {
                        //var CustomerMainCode=baseResponse.getElementsByTagName("CustomerMainCode")[k].firstChild.nodeValue;
                        var ProgrammeSubCode=baseResponse.getElementsByTagName("ProgrammeSubCode");
                        var ProgrammeDesc=baseResponse.getElementsByTagName("ProgrammeDesc");
                        var ProgrammeMainCode=baseResponse.getElementsByTagName("ProgrammeMainCode");
                        var cProgrammeMainCode=ProgrammeMainCode.item(k).firstChild.nodeValue;
                        var cProgrammeSubCode=ProgrammeSubCode.item(k).firstChild.nodeValue;
                        var cProgrammeDesc=ProgrammeDesc.item(k).firstChild.nodeValue;
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
                         var txtprogrammemaincode=document.createTextNode(cProgrammeMainCode);                        
                         cell2.appendChild(txtprogrammemaincode);      
                         mycurrent_row.appendChild(cell2);      
                         var cell3 =document.createElement("TD");   
                         var txtprogrammesubcode=document.createTextNode(cProgrammeSubCode);                        
                         cell3.appendChild(txtprogrammesubcode);      
                         mycurrent_row.appendChild(cell3);
                        
                         var cell4 =document.createElement("TD");   
                         var txtprogrammedesc=document.createTextNode(cProgrammeDesc);                        
                         cell4.appendChild(txtprogrammedesc);      
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
        document.ProgForm.txtMainId.value=rcells.item(1).firstChild.nodeValue;
          document.ProgForm.txtSubId.value=rcells.item(2).firstChild.nodeValue;
          document.ProgForm.txtDesc.value=rcells.item(3).firstChild.nodeValue;
          document.ProgForm.CmdAdd.disabled=true;
        document.ProgForm.CmdUpdate.disabled=false;
        document.ProgForm.CmdDelete.disabled=false;
       document.ProgForm.txtMainId.disabled=true;
          document.ProgForm.txtSubId.disabled=true;
          document.ProgForm.CmdDelete.focus();
     
    }