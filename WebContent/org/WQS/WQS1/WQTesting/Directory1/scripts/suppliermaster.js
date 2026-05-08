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
/*function clearAll()
 {
   document.SupplierForm.txtConId.value="";
   document.SupplierForm.txtMainId.selectedindex=0;
   document.SupplierForm.txtSubId.selectedindex=0;
   document.SupplierForm.txtRate.value="";
   document.SupplierForm.txtCon.value="";
   document.SupplierForm.txtPro.value="";
   document.SupplierForm.CmdAdd.disabled=false;
   document.SupplierForm.CmdUpdate.disabled=true;
   document.SupplierForm.CmdDelete.disabled=true;
  
   var tbody=document.getElementById("tblList");
        var t=0;
       
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
  callServer('Get','null');
 }*/
 
function Exit()
 {
    self.close();
 }
 
 
 
 //******************************Validation Checking**************************//
 /*function nullCheck()
        {
                 
                  if((document.SupplierForm.txtConId.value=="") || (document.SupplierForm.txtConId.value.length<=0))
                  {
                       alert("Please Enter the Concession Code");
                       document.SupplierForm.txtConId.focus();
                       return false;
                  }
                  if((document.SupplierForm.txtRate.value=="") || (document.SupplierForm.txtRate.value.length<=0))
                  {
                       alert("Please Enter the Rate");
                       document.SupplierForm.txtRate.focus();
                       return false;
                  }
                  if((document.SupplierForm.txtCon.value=="") || (document.SupplierForm.txtCon.value.length<=0))
                  {
                       alert("Please Enter the Actual Concession");
                       document.SupplierForm.txtCon.focus();
                       return false;
                  }
                  if((document.SupplierForm.txtPro.value=="") || (document.SupplierForm.txtPro.value.length<=0))
                  {
                       alert("Please Enter the Proceeding No");
                       document.SupplierForm.txtPro.focus();
                       return false;
                  }
                  
                  
                 if((document.SupplierForm.txtSubId.value=="") || (document.SupplierForm.txtSubId.value.length<=0))
                  {
                       alert("Please Select Customer Sub Type Id");
                       document.SupplierForm.txtSubId.focus();
                       return false;
                  }
                  
                  if((document.SupplierForm.txtMainId.value=="") ||(document.SupplierForm.txtMainId.value.length<=0) || (document.SupplierForm.txtMainId.value=="0"))
                  {
                    alert("Please Select Customer Main Id");
                    document.SupplierForm.txtMainId.focus();
                    return false;
                  }
                  return true;
        }*/
       
       

function callServer(command,param)
 {
       var txtcatid=document.SupplierForm.txtCatId.value;
       var txtsupid=document.SupplierForm.txtSupId.value;
       var txtsupname=document.SupplierForm.txtSupName.value;
       var txtaddr1=document.SupplierForm.txtAddr1.value;
       var txtaddr2=document.SupplierForm.txtAddr2.value;
       var txtaddr3=document.SupplierForm.txtAddr3.value;
       var txtpin=document.SupplierForm.txtPin.value;
       var txtphone1=document.SupplierForm.txtPhone1.value;
       var txtphone2=document.SupplierForm.txtPhone2.value;
       var txtfax=document.SupplierForm.txtFax.value;
       var txtmail=document.SupplierForm.txtMail.value;
       var url="";
 if(command=="Add")
        {           //var flag=nullCheck();
                    //if(flag==true)
                   //{
                    alert(txtmail);
                    url="../../../../../../suppliermaster_servlet.view?command=Add&CategoryId="+txtcatid+"&SupplierId="+txtsupid+"&SupplierName="+txtsupname+"&Address1="+txtaddr1+"&Address2="+txtaddr2+"&Address3="+txtaddr3+"&Pin="+txtpin +"&Phone1="+txtphone1 +"&Phone2="+txtphone2 +"&Fax="+ txtfax+"&Mail="+txtmail;
                    var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);
                    
                  
                  
        }
       /*  else if(command=="Update")
        {
                    var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../suppliermaster_servlet.view?command=Update&MainId="+txtcustmainid+"&SubId=" + txtcustsubid+"&ConcessionId="+txtconid+"&Rate="+txtrate+"&Actual="+txtactual+"&Proceeding="+txtproc;
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
                   
                    url="../../../../suppliermaster_servlet.view?command=Delete&MainId="+txtcustmainid+"&SubId="+txtcustsubid+"&ConcessionId="+txtconid;
                   var req=getTransport();
                    req.open("GET",url,true);       
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }  
                    req.send(null);
        }*/
  else if(command=="Get")
        {              
          
            url="../../../../../../suppliermaster_servlet.view?command=Get";
            
            var req=getTransport();
            req.open("GET",url,true);
            req.onreadystatechange=function()
            {
               processResponse(req);
            }  
                    req.send(null);
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
             if(command=="Get")
              {
              getRow(baseResponse);
              }
              else if(command=="Add")
              {
              addRow(baseResponse);
              }
             /*  else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="Delete")
              {
              deleteRow(baseResponse)
              }*/
             
          }
        }
  }
  function addRow(baseResponse)
{
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
              if(flag=="success")
              {                       
                 alert("Record Inserted Into Database successfully.");
                 did=baseResponse.getElementsByTagName("CategoryCode")[0].firstChild.nodeValue;
                // alert("Concession Code Is " +  did);
                 var items=new Array();                                    
                 cadid=baseResponse.getElementsByTagName("CategoryCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("SupplierCode")[0].firstChild.nodeValue;
                 category=baseResponse.getElementsByTagName("SupplierName")[0].firstChild.nodeValue;
                 rate=baseResponse.getElementsByTagName("Address1")[0].firstChild.nodeValue;
                 actual=baseResponse.getElementsByTagName("Address2")[0].firstChild.nodeValue;
                 proc=baseResponse.getElementsByTagName("Address3")[0].firstChild.nodeValue;
                 pin=baseResponse.getElementsByTagName("Pin")[0].firstChild.nodeValue;
                 ph1=baseResponse.getElementsByTagName("Phone1")[0].firstChild.nodeValue;
                 ph2=baseResponse.getElementsByTagName("Phone2")[0].firstChild.nodeValue;
                 fax=baseResponse.getElementsByTagName("Fax")[0].firstChild.nodeValue;
                 mail=baseResponse.getElementsByTagName("Mail")[0].firstChild.nodeValue;
                 //document.SupplierForm.txtjournaltypecode.value=cadid;
                 //document.SupplierForm.txtjournaltypedesc.value=document.SupplierForm.txtjournaltypedesc.value;
                
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
            
                var cell1 =document.createElement("TD");   
                 var txtcadid=document.createTextNode(cadid);                        
                 cell1.appendChild(txtcadid);      
                 mycurrent_row.appendChild(cell1);      
                                
                  var cell2 =document.createElement("TD");   
                 var txtsdesc=document.createTextNode(sdesc);                        
                 cell2.appendChild(txtsdesc);      
                 mycurrent_row.appendChild(cell2);
                
                 var cell3 =document.createElement("TD");   
                 var txtcategory=document.createTextNode(category);                        
                 cell3.appendChild(txtcategory);      
                 mycurrent_row.appendChild(cell3);
                 
                var cell4 =document.createElement("TD");   
                 var txtrate=document.createTextNode(rate);                        
                 cell4.appendChild(txtrate);      
                 mycurrent_row.appendChild(cell4);      
                
                 var cell5 =document.createElement("TD");   
                 var txtactual=document.createTextNode(actual);                        
                 cell5.appendChild(txtactual);      
                 mycurrent_row.appendChild(cell5);      
                 
                 var cell6 =document.createElement("TD");   
                 var txtproc=document.createTextNode(proc);                        
                 cell6.appendChild(txtproc);      
                 mycurrent_row.appendChild(cell6);    
                 
                 var cell7 =document.createElement("TD");   
                 var txtpin=document.createTextNode(pin);                        
                 cell7.appendChild(txtpin);      
                 mycurrent_row.appendChild(cell7);      
                
                  
                 var cell8 =document.createElement("TD");   
                 var txtph1=document.createTextNode(ph1);                        
                 cell8.appendChild(txtph1);      
                 mycurrent_row.appendChild(cell8);      
                
                var cell9 =document.createElement("TD");   
                 var txtph2=document.createTextNode(ph2);                        
                 cell9.appendChild(txtph2);      
                 mycurrent_row.appendChild(cell9);      
                
                 var cell10 =document.createElement("TD");   
                 var txtfa=document.createTextNode(fax);                        
                 cell10.appendChild(txtfa);      
                 mycurrent_row.appendChild(cell10);      
                
                 var cell11 =document.createElement("TD");   
                 var txtma=document.createTextNode(mail);                        
                 cell11.appendChild(txtma);      
                 mycurrent_row.appendChild(cell11);      
                
                 tbody.appendChild(mycurrent_row);
               
                 document.SupplierForm.CmdAdd.disabled=false;
                document.SupplierForm.CmdUpdate.disabled=true;
                document.SupplierForm.CmdDelete.disabled=true;          
                document.SupplierForm.txtMainId.selectedIndex=0;
                document.SupplierForm.txtSubId.selectedIndex=0;
                document.SupplierForm.txtConId.value="";
                document.SupplierForm.txtRate.value="";
                document.SupplierForm.txtCon.value="";
                document.SupplierForm.txtPro.value="";
                 }
                 else
                 {
                 alert("Record already exists, Insertion not possible");
                 }
}
/*function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {  
           alert("Record Updated Successfully.");
               var items=new Array();
               items[0]=document.SupplierForm.txtConId.value;
               items[1]=document.SupplierForm.txtMainId.value;
               items[2]=document.SupplierForm.txtSubId.value;
               items[3]=document.SupplierForm.txtRate.value;
               items[4]=document.SupplierForm.txtCon.value;
               items[5]=document.SupplierForm.txtPro.value;
               var r=document.getElementById(com_id);   
               var rcells=r.cells;
                //rcells.item(1).firstChild.nodeValue=items[0];
                //rcells.item(2).firstChild.nodeValue=items[1];
                //rcells.item(3).firstChild.nodeValue=items[2];
                rcells.item(4).firstChild.nodeValue=items[3];
                rcells.item(5).firstChild.nodeValue=items[4];
                rcells.item(6).firstChild.nodeValue=items[5];
                document.SupplierForm.CmdAdd.disabled=false;
                document.SupplierForm.CmdUpdate.disabled=true;
                document.SupplierForm.CmdDelete.disabled=true;          
                document.SupplierForm.txtMainId.selectedIndex=0;
                document.SupplierForm.txtSubId.selectedIndex=0;
               document.SupplierForm.txtConId.value="";
                document.SupplierForm.txtRate.value="";
                document.SupplierForm.txtCon.value="";
                document.SupplierForm.txtPro.value="";
                document.SupplierForm.txtConId.disabled=false;
        document.SupplierForm.txtMainId.disabled=false;
        document.SupplierForm.txtSubId.disabled=false;
                        
                                                
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
                      var concessioncode=baseResponse.getElementsByTagName("ConcessionCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");    
                      var r=document.getElementById(com_id);   
                      var ri=r.rowIndex;              
                      tbody.deleteRow(ri);
                       document.SupplierForm.txtMainId.selectedIndex=0;
                       document.SupplierForm.txtSubId.selectedIndex=0;
                       document.SupplierForm.txtConId.value="";
                        document.SupplierForm.txtRate.value="";
                        document.SupplierForm.txtCon.value="";
                        document.SupplierForm.txtPro.value="";
                       document.SupplierForm.CmdAdd.disabled=false;
                        document.SupplierForm.CmdUpdate.disabled=true;
                        document.SupplierForm.CmdDelete.disabled=true;
                        document.SupplierForm.txtConId.disabled=false;
        document.SupplierForm.txtMainId.disabled=false;
        document.SupplierForm.txtSubId.disabled=false;
                              
                      alert("Selected Records are Deleted");                     
                  }
                  else
                  {
                      alert("Unable to Delete");
                  }
  
  }
*/
  
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
                                            
                    var CategoryCode=baseResponse.getElementsByTagName("CategoryCode");
                   alert(CategoryCode.length);
                    for(var k=0;k<CategoryCode.length;k++)
                        {
                        //var CustomerMainCode=baseResponse.getElementsByTagName("CustomerMainCode")[k].firstChild.nodeValue;
                        var CategoryCode=baseResponse.getElementsByTagName("CategoryCode");
                        var SupplierCode=baseResponse.getElementsByTagName("SupplierCode");
                        var SupplierName=baseResponse.getElementsByTagName("SupplierName");
                        var Address1=baseResponse.getElementsByTagName("Address1");
                        var Address2=baseResponse.getElementsByTagName("Address2");
                        var Address3=baseResponse.getElementsByTagName("Address3");
                        var Pin=baseResponse.getElementsByTagName("Pin");
                        var Phone1=baseResponse.getElementsByTagName("Phone1");
                        var Phone2=baseResponse.getElementsByTagName("Phone2");
                        var Fax=baseResponse.getElementsByTagName("Fax");
                        var Mail=baseResponse.getElementsByTagName("Mail");
                        
                        
                        var cCategoryCode=CategoryCode.item(k).firstChild.nodeValue;
                        var cSupplierCode=SupplierCode.item(k).firstChild.nodeValue;
                        var cSupplierName=SupplierName.item(k).firstChild.nodeValue;
                        var cAddress1=Address1.item(k).firstChild.nodeValue;
                        var cAddress2=Address2.item(k).firstChild.nodeValue;
                        var cAddress3=Address3.item(k).firstChild.nodeValue;
                        var cPin=Pin.item(k).firstChild.nodeValue;
                        var cPhone1=Phone1.item(k).firstChild.nodeValue;
                        var cPhone2=Phone2.item(k).firstChild.nodeValue;
                        var cFax=Fax.item(k).firstChild.nodeValue;
                        var cMail=Mail.item(k).firstChild.nodeValue;
                        
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
                    
                        var cell1 =document.createElement("TD");   
                         var txtcacode=document.createTextNode(cCategoryCode);                        
                         cell1.appendChild(txtcacode);      
                         mycurrent_row.appendChild(cell1);      
                        
                        
                        
                         var cell2 =document.createElement("TD");   
                         var txtsucode=document.createTextNode(cSupplierCode);                        
                         cell2.appendChild(txtsucode);      
                         mycurrent_row.appendChild(cell2);      
                         
                         var cell3 =document.createElement("TD");   
                         var txtsuname=document.createTextNode(cSupplierName);                        
                         cell3.appendChild(txtsuname);      
                         mycurrent_row.appendChild(cell3);
                        
                         var cell4 =document.createElement("TD");   
                         var txtad1=document.createTextNode(cAddress1);                        
                         cell4.appendChild(txtad1);      
                         mycurrent_row.appendChild(cell4);
                        
                        var cell5 =document.createElement("TD");   
                         var txtad2=document.createTextNode(cAddress2);                        
                         cell5.appendChild(txtad2);      
                         mycurrent_row.appendChild(cell5);
                        
                        var cell6 =document.createElement("TD");   
                         var txtad3=document.createTextNode(cAddress3);                        
                         cell6.appendChild(txtad3);      
                         mycurrent_row.appendChild(cell6);
                        
                        
                        var cell7 =document.createElement("TD");   
                         var txtpin=document.createTextNode(cPin);                        
                         cell7.appendChild(txtpin);      
                         mycurrent_row.appendChild(cell7);
                        
                         var cell8 =document.createElement("TD");   
                         var txtph1=document.createTextNode(cPhone1);                        
                         cell8.appendChild(txtph1);      
                         mycurrent_row.appendChild(cell8);
                        
                         var cell9 =document.createElement("TD");   
                         var txtph2=document.createTextNode(cPhone2);                        
                         cell9.appendChild(txtph2);      
                         mycurrent_row.appendChild(cell9);
                         
                          var cell10 =document.createElement("TD");   
                         var txtfa=document.createTextNode(cFax);                        
                         cell10.appendChild(txtfa);      
                         mycurrent_row.appendChild(cell10);
                        
                         var cell11 =document.createElement("TD");   
                         var txtma=document.createTextNode(cMail);                        
                         cell11.appendChild(txtma);      
                         mycurrent_row.appendChild(cell11);
                        
                         tbody.appendChild(mycurrent_row);
                        }
                  }
                  else
                  {
                    alert("Failed to Load Values");
                  }
}
  /*function loadValuesFromTable(rid)
    {     
          com_id=rid;
          var r=document.getElementById(rid);
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
        document.SupplierForm.txtConId.value=rcells.item(1).firstChild.nodeValue;
        document.SupplierForm.txtMainId.value=rcells.item(2).firstChild.nodeValue;
          document.SupplierForm.txtSubId.value=rcells.item(3).firstChild.nodeValue;
          document.SupplierForm.txtRate.value=rcells.item(4).firstChild.nodeValue;
          document.SupplierForm.txtCon.value=rcells.item(5).firstChild.nodeValue;
          document.SupplierForm.txtPro.value=rcells.item(6).firstChild.nodeValue;
          document.SupplierForm.CmdAdd.disabled=true;
        document.SupplierForm.CmdUpdate.disabled=false;
        document.SupplierForm.CmdDelete.disabled=false;
        document.SupplierForm.txtConId.disabled=true;
        document.SupplierForm.txtMainId.disabled=true;
        document.SupplierForm.txtSubId.disabled=true;
        
       
          document.SupplierForm.CmdDelete.focus();
     
    }*/