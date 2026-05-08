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
   document.NonStandardForm.txtStanId.value="";
   document.NonStandardForm.txtMainId.selectedindex=0;
   document.NonStandardForm.txtPurposeId.selectedindex=0;
   document.NonStandardForm.txtElement.value="";
   document.NonStandardForm.txtTestName.value="";
   document.NonStandardForm.txtTestType.value="";
   document.NonStandardForm.txtCphe.value="";
   document.NonStandardForm.txtBis.value="";
   document.NonStandardForm.txtWho.value="";
   document.NonStandardForm.txtPrac.value="";
   document.NonStandardForm.txtPrint.value="";
   
   document.NonStandardForm.CmdAdd.disabled=false;
   document.NonStandardForm.CmdUpdate.disabled=true;
   document.NonStandardForm.CmdDelete.disabled=true;
  document.NonStandardForm.txtStanId.disabled=false;
  document.NonStandardForm.txtMainId.disabled=false;
  document.NonStandardForm.txtPurposeId.disabled=false;
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
                 
                  if((document.NonStandardForm.txtStanId.value=="") || (document.NonStandardForm.txtStanId.value.length<=0))
                  {
                       alert("Please Enter the Non Standard Test Code");
                       document.NonStandardForm.txtStanId.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtElement.value=="") || (document.NonStandardForm.txtElement.value.length<=0))
                  {
                       alert("Please Enter the Element Symbol");
                       document.NonStandardForm.txtElement.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtTestName.value=="") || (document.NonStandardForm.txtTestName.value.length<=0))
                  {
                       alert("Please Enter the Test Name");
                       document.NonStandardForm.txtTestName.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtCphe.value=="") || (document.NonStandardForm.txtCphe.value.length<=0))
                  {
                       alert("Please Enter the CPHE Standard");
                       document.NonStandardForm.txtCphe.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtBis.value=="") || (document.NonStandardForm.txtBis.value.length<=0))
                  {
                       alert("Please Enter the BIS Standard");
                       document.NonStandardForm.txtBis.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtWho.value=="") || (document.NonStandardForm.txtWho.value.length<=0))
                  {
                       alert("Please Enter the Who Standard");
                       document.NonStandardForm.txtWho.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtPrac.value=="") || (document.NonStandardForm.txtPrac.value.length<=0))
                  {
                       alert("Please Enter the Prac Standard");
                       document.NonStandardForm.txtPrac.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtPrint.value=="") || (document.NonStandardForm.txtPrint.value.length<=0))
                  {
                       alert("Please Enter the Print Priority");
                       document.NonStandardForm.txtPrint.focus();
                       return false;
                  }
                              
                  
                 if((document.NonStandardForm.txtMainId.value=="") || (document.NonStandardForm.txtMainId.value.length<=0))
                  {
                       alert("Please Select Sample Type Id");
                       document.NonStandardForm.txtMainId.focus();
                       return false;
                  }
                  if((document.NonStandardForm.txtPurposeId.value=="") || (document.NonStandardForm.txtPurposeId.value.length<=0))
                  {
                       alert("Please Select Test Purpose Id");
                       document.NonStandardForm.txtPurposeId.focus();
                       return false;
                  }
                 
                  return true;
        }
       
       

function callServer(command,param)
 {
       var txtstandardid=document.NonStandardForm.txtStanId.value;
       var txtsampleid=document.NonStandardForm.txtMainId.value;
       var txtpurposeid=document.NonStandardForm.txtPurposeId.value;
       var txtelement=document.NonStandardForm.txtElement.value;
       var txttestname=document.NonStandardForm.txtTestName.value;
        var txttesttype=document.NonStandardForm.txtTestType.value;
       var txtcphe=document.NonStandardForm.txtCphe.value;
       var txtbis=document.NonStandardForm.txtBis.value;
       var txtwho=document.NonStandardForm.txtWho.value;
       var txtprac=document.NonStandardForm.txtPrac.value;
       var txtprint=document.NonStandardForm.txtPrint.value;
       var url="";
 if(command=="Add")
        {           var flag=nullCheck();
                    if(flag==true)
                    {
                    //url="../../../../tariffconcession_servlet.view?command=Add&MainId="+txtcustmainid+"&SubId="+txtcustsubid+"&ConcessionId="+txtconid+"&Rate="+txtrate+"&Actual="+txtactual+"&Proceeding="+txtproc;
                    
                    url="../../../../../../nonstandardtest_servlet.view?command=Add&SampleId="+txtsampleid+"&StandardId="+txtstandardid+"&PurposeId="+txtpurposeid+"&Element="+txtelement+"&TestName="+txttestname+"&TestType="+txttesttype+"&Cphe="+txtcphe+"&Bis="+txtbis+"&Who="+txtwho+"&Prac="+txtprac+"&Print="+txtprint;
                    
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
                    //url="../../../../tariffconcession_servlet.view?command=Update&MainId="+txtcustmainid+"&SubId=" + txtcustsubid+"&ConcessionId="+txtconid+"&Rate="+txtrate+"&Actual="+txtactual+"&Proceeding="+txtproc;
                    url="../../../../../../nonstandardtest_servlet.view?command=Update&SampleId="+txtsampleid+"&StandardId="+txtstandardid+"&PurposeId="+txtpurposeid+"&Element="+txtelement+"&TestName="+txttestname+"&TestType="+txttesttype+"&Cphe="+txtcphe+"&Bis="+txtbis+"&Who="+txtwho+"&Prac="+txtprac+"&Print="+txtprint;
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
                   
                    //url="../../../../tariffconcession_servlet.view?command=Delete&MainId="+txtcustmainid+"&SubId="+txtcustsubid+"&ConcessionId="+txtconid;
                   url="../../../../../../nonstandardtest_servlet.view?command=Delete&SampleId="+txtsampleid+"&StandardId="+txtstandardid+"&PurposeId="+txtpurposeid+"&Element="+txtelement+"&TestName="+txttestname+"&TestType="+txttesttype+"&Cphe="+txtcphe+"&Bis="+txtbis+"&Who="+txtwho+"&Prac="+txtprac+"&Print="+txtprint;
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
            
            url="../../../../../../nonstandardtest_servlet.view?command=Get";
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
               else if(command=="Update")
              {
              updateRow(baseResponse);
              }
              else if(command=="Delete")
              {
              deleteRow(baseResponse)
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
                 did=baseResponse.getElementsByTagName("StandardCode")[0].firstChild.nodeValue;
                 
                 var items=new Array();                                    
                 cadid=baseResponse.getElementsByTagName("StandardCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("SampleCode")[0].firstChild.nodeValue;
                 category=baseResponse.getElementsByTagName("PurposeCode")[0].firstChild.nodeValue;
                 element=baseResponse.getElementsByTagName("Element")[0].firstChild.nodeValue;
                 testname=baseResponse.getElementsByTagName("TestName")[0].firstChild.nodeValue;
                  testtype=baseResponse.getElementsByTagName("TestType")[0].firstChild.nodeValue;
                 cphe=baseResponse.getElementsByTagName("Cphe")[0].firstChild.nodeValue;
                 bis=baseResponse.getElementsByTagName("Bis")[0].firstChild.nodeValue;
                 who=baseResponse.getElementsByTagName("Who")[0].firstChild.nodeValue;
                 prac=baseResponse.getElementsByTagName("Prac")[0].firstChild.nodeValue;
                 printa=baseResponse.getElementsByTagName("Print")[0].firstChild.nodeValue;
                 alert(printa);
                 //document.NonStandardForm.txtjournaltypecode.value=cadid;
                 //document.NonStandardForm.txtjournaltypedesc.value=document.NonStandardForm.txtjournaltypedesc.value;
                
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
                 var txtelement=document.createTextNode(element);                        
                 cell4.appendChild(txtelement);      
                 mycurrent_row.appendChild(cell4);      
                
                 var cell5 =document.createElement("TD");   
                 var txttestname=document.createTextNode(testname);                        
                 cell5.appendChild(txttestname);      
                 mycurrent_row.appendChild(cell5);      
                 
                var cell6 =document.createElement("TD");   
                 var txttesttype=document.createTextNode(testtype);                        
                 cell6.appendChild(txttesttype);      
                 mycurrent_row.appendChild(cell6);      
                
                 var cell7 =document.createElement("TD");   
                 var txtcphe=document.createTextNode(cphe);                        
                 cell7.appendChild(txtcphe);      
                 mycurrent_row.appendChild(cell7);      
                
                var cell8 =document.createElement("TD");   
                 var txtbis=document.createTextNode(bis);                        
                 cell8.appendChild(txtbis);      
                 mycurrent_row.appendChild(cell8);
                 
                 var cell9 =document.createElement("TD");   
                 var txtwho=document.createTextNode(who);                        
                 cell9.appendChild(txtwho);      
                 mycurrent_row.appendChild(cell9);      
                
                 var cell10 =document.createElement("TD");   
                 var txtprac=document.createTextNode(prac);                        
                 cell10.appendChild(txtprac);      
                 mycurrent_row.appendChild(cell10);      
                
                var cell11 =document.createElement("TD");   
                 var txtprint=document.createTextNode(printa);                        
                 cell11.appendChild(txtprint);      
                 mycurrent_row.appendChild(cell11);      
                
                
                 tbody.appendChild(mycurrent_row);
                document.NonStandardForm.txtStanId.value="";
                       document.NonStandardForm.txtMainId.selectedIndex=0;
                       document.NonStandardForm.txtPurposeId.selectedindex=0;
                        document.NonStandardForm.txtElement.value="";
                        document.NonStandardForm.txtTestName.value="";
                        document.NonStandardForm.txtTestType.value="";
                        document.NonStandardForm.txtCphe.value="";
                        document.NonStandardForm.txtBis.value="";
                        document.NonStandardForm.txtWho.value="";
                        document.NonStandardForm.txtPrac.value="";
                        document.NonStandardForm.txtPrint.value="";
   
                 document.NonStandardForm.CmdAdd.disabled=false;
                document.NonStandardForm.CmdUpdate.disabled=true;
                document.NonStandardForm.CmdDelete.disabled=true;          
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
               items[0]=document.NonStandardForm.txtStanId.value;
               items[1]=document.NonStandardForm.txtMainId.value;
               items[2]=document.NonStandardForm.txtPurposeId.value;
               items[3]=document.NonStandardForm.txtElement.value;
               items[4]=document.NonStandardForm.txtTestName.value;
                items[5]=document.NonStandardForm.txtTestType.value;
               items[6]=document.NonStandardForm.txtCphe.value;
               items[7]=document.NonStandardForm.txtBis.value;
               items[8]=document.NonStandardForm.txtWho.value;
               items[9]=document.NonStandardForm.txtPrac.value;
               items[10]=document.NonStandardForm.txtPrint.value;
              
               
               var r=document.getElementById(com_id);   
               var rcells=r.cells;
                //rcells.item(1).firstChild.nodeValue=items[0];
                //rcells.item(2).firstChild.nodeValue=items[1];
                //rcells.item(3).firstChild.nodeValue=items[2];
                 rcells.item(4).firstChild.nodeValue=items[3];
                rcells.item(5).firstChild.nodeValue=items[4];
                rcells.item(6).firstChild.nodeValue=items[5];
                 rcells.item(7).firstChild.nodeValue=items[6];
                  rcells.item(8).firstChild.nodeValue=items[7];
                   rcells.item(9).firstChild.nodeValue=items[8];
                    rcells.item(10).firstChild.nodeValue=items[9];
                    rcells.item(11).firstChild.nodeValue=items[10];
                document.NonStandardForm.CmdAdd.disabled=false;
                document.NonStandardForm.CmdUpdate.disabled=true;
                document.NonStandardForm.CmdDelete.disabled=true;          
                 document.NonStandardForm.txtStanId.value="";
                       document.NonStandardForm.txtMainId.selectedIndex=0;
                       document.NonStandardForm.txtPurposeId.selectedindex=0;
                        document.NonStandardForm.txtElement.value="";
                        document.NonStandardForm.txtTestName.value="";
                        document.NonStandardForm.txtTestType.value="";
                        document.NonStandardForm.txtCphe.value="";
                        document.NonStandardForm.txtBis.value="";
                        document.NonStandardForm.txtWho.value="";
                        document.NonStandardForm.txtPrac.value="";
                        document.NonStandardForm.txtPrint.value="";
                        document.NonStandardForm.txtStanId.disabled=false;
                        document.NonStandardForm.txtMainId.disabled=false;
                        document.NonStandardForm.txtPurposeId.disabled=false;
                                  
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
                      var standardcode=baseResponse.getElementsByTagName("StandardCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");    
                      var r=document.getElementById(com_id);   
                      var ri=r.rowIndex;              
                      tbody.deleteRow(ri);
                       document.NonStandardForm.txtStanId.value="";
                       document.NonStandardForm.txtMainId.selectedIndex=0;
                       document.NonStandardForm.txtPurposeId.selectedindex=0;
                        document.NonStandardForm.txtElement.value="";
                        document.NonStandardForm.txtTestName.value="";
                        document.NonStandardForm.txtTestType.value="";
                        document.NonStandardForm.txtCphe.value="";
                        document.NonStandardForm.txtBis.value="";
                        document.NonStandardForm.txtWho.value="";
                        document.NonStandardForm.txtPrac.value="";
                        document.NonStandardForm.txtPrint.value="";
   
                       document.NonStandardForm.CmdAdd.disabled=false;
                        document.NonStandardForm.CmdUpdate.disabled=true;
                        document.NonStandardForm.CmdDelete.disabled=true;   
                        document.NonStandardForm.txtStanId.disabled=false;
                        document.NonStandardForm.txtMainId.disabled=false;
                        document.NonStandardForm.txtPurposeId.disabled=false;
                              
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
                                            
                    var StandardCode=baseResponse.getElementsByTagName("StandardCode");
                    
                    for(var k=0;k<StandardCode.length;k++)
                        {
                        //var CustomerMainCode=baseResponse.getElementsByTagName("CustomerMainCode")[k].firstChild.nodeValue;
                        var StandardCode=baseResponse.getElementsByTagName("StandardCode");
                        var SampleCode=baseResponse.getElementsByTagName("SampleCode");
                        
                 var PurposeCode=baseResponse.getElementsByTagName("PurposeCode");
                 var Element=baseResponse.getElementsByTagName("Element");
                 var TestName=baseResponse.getElementsByTagName("TestName");
                 var TestType=baseResponse.getElementsByTagName("TestType");
                 var Cphe=baseResponse.getElementsByTagName("Cphe");
                 var Bis=baseResponse.getElementsByTagName("Bis");
                 var Who=baseResponse.getElementsByTagName("Who");
                 var Prac=baseResponse.getElementsByTagName("Prac");
                 var Print=baseResponse.getElementsByTagName("Print");
                        
                        
                        var cStandardCode=StandardCode.item(k).firstChild.nodeValue;
                        var cSampleCode=SampleCode.item(k).firstChild.nodeValue;
                        var cPurposeCode=PurposeCode.item(k).firstChild.nodeValue;
                        var cElement=Element.item(k).firstChild.nodeValue;
                        var cTestName=TestName.item(k).firstChild.nodeValue;
                        var cTestType=TestType.item(k).firstChild.nodeValue;
                        var cCphe=Cphe.item(k).firstChild.nodeValue;
                        var cBis=Bis.item(k).firstChild.nodeValue;
                        var cWho=Who.item(k).firstChild.nodeValue;
                        var cPrac=Prac.item(k).firstChild.nodeValue;
                        var cPrint=Print.item(k).firstChild.nodeValue;
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
                         var txtscode=document.createTextNode(cStandardCode);                        
                         cell1.appendChild(txtscode);      
                         mycurrent_row.appendChild(cell1);      
                        
                        
                        
                         var cell2 =document.createElement("TD");   
                         var txtsacode=document.createTextNode(cSampleCode);                        
                         cell2.appendChild(txtsacode);      
                         mycurrent_row.appendChild(cell2);      
                         
                         var cell3 =document.createElement("TD");   
                         var txtpcode=document.createTextNode(cPurposeCode);                        
                         cell3.appendChild(txtpcode);      
                         mycurrent_row.appendChild(cell3);
                        
                         var cell4 =document.createElement("TD");   
                         var txtet=document.createTextNode(cElement);                        
                         cell4.appendChild(txtet);      
                         mycurrent_row.appendChild(cell4);
                        
                        var cell5 =document.createElement("TD");   
                         var txttn=document.createTextNode(cTestName);                        
                         cell5.appendChild(txttn);      
                         mycurrent_row.appendChild(cell5);
                        
                        var cell6 =document.createElement("TD");   
                         var txttt=document.createTextNode(cTestType);                        
                         cell6.appendChild(txttt);      
                         mycurrent_row.appendChild(cell6);
                        
                         var cell7 =document.createElement("TD");   
                         var txtcp=document.createTextNode(cCphe);                        
                         cell7.appendChild(txtcp);      
                         mycurrent_row.appendChild(cell7);
                        
                         var cell8 =document.createElement("TD");   
                         var txtbi=document.createTextNode(cBis);                        
                         cell8.appendChild(txtbi);      
                         mycurrent_row.appendChild(cell8);
                         
                         var cell9 =document.createElement("TD");   
                         var txtwh=document.createTextNode(cWho);                        
                         cell9.appendChild(txtwh);      
                         mycurrent_row.appendChild(cell9);
                        
                        var cell10 =document.createElement("TD");   
                         var txtpr=document.createTextNode(cPrac);                        
                         cell10.appendChild(txtpr);      
                         mycurrent_row.appendChild(cell10);
                        
                         var cell11 =document.createElement("TD");   
                         var txtpr=document.createTextNode(cPrint);                        
                         cell11.appendChild(txtpr);      
                         mycurrent_row.appendChild(cell11);
                        
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
        document.NonStandardForm.txtStanId.value=rcells.item(1).firstChild.nodeValue;
        document.NonStandardForm.txtMainId.value=rcells.item(2).firstChild.nodeValue;
          document.NonStandardForm.txtPurposeId.value=rcells.item(3).firstChild.nodeValue;
          document.NonStandardForm.txtElement.value=rcells.item(4).firstChild.nodeValue;
          document.NonStandardForm.txtTestName.value=rcells.item(5).firstChild.nodeValue;
         document.NonStandardForm.txtTestType.value=rcells.item(6).firstChild.nodeValue;
          document.NonStandardForm.txtCphe.value=rcells.item(7).firstChild.nodeValue;
          document.NonStandardForm.txtBis.value=rcells.item(8).firstChild.nodeValue;
          document.NonStandardForm.txtWho.value=rcells.item(9).firstChild.nodeValue;
          document.NonStandardForm.txtPrac.value=rcells.item(10).firstChild.nodeValue;
          document.NonStandardForm.txtPrint.value=rcells.item(11).firstChild.nodeValue;
          document.NonStandardForm.CmdAdd.disabled=true;
        document.NonStandardForm.CmdUpdate.disabled=false;
        document.NonStandardForm.CmdDelete.disabled=false;
        document.NonStandardForm.txtStanId.disabled=true;
        document.NonStandardForm.txtMainId.disabled=true;
       document.NonStandardForm.txtPurposeId.disabled=true;
          document.NonStandardForm.CmdDelete.focus();
     
    }