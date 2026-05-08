var rid;
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
   document.frmDepreciationMaster.cmbassetclasscode.selectedIndex=0;
   document.frmDepreciationMaster.cmbFinancialYear.selectedIndex=0;
   document.frmDepreciationMaster.txtdepreciationrates.value="";
   document.frmDepreciationMaster.CmdAdd.disabled=false;
   document.frmDepreciationMaster.CmdUpdate.disabled=true;
   document.frmDepreciationMaster.CmdDelete.disabled=true;
   
   var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
 }
 
 function Exit()
 {
    self.close();
 }
 
 //Financial Year Coding Part ///

function loadfyr()
{
         var cyr, cdt,cdt1;
 	cdt=new Date();
 	cyr=cdt.getFullYear();
 	cmn=cdt.getMonth();
        //alert("cdate"+cdt);
        //alert("cmonth"+cmn);
        //alert("cyear"+cyr);
        var cmbFinancialYear=document.getElementById("cmbFinancialYear");
        cyr=cyr+1;
 	if (parseInt(cmn) <= 2)
        {
  
                document.frmDepreciationMaster.cmbFinancialYear.length=5;
                cmbFinancialYear.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select FinancialYear--";
                option.value=0;
                try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                for (var i = 0 ; i < 2; i++) 
                {
         
                  //document.frmDepreciationMaster.cmbFinancialYear.options[i].text=(cyr-2)+"-"+(cyr-1);
                  //document.frmDepreciationMaster.cmbFinancialYear.options[i].value=(cyr-2)+"-"+(cyr-1);
                  var id=(cyr-2)+"-"+(cyr-1);
                  var option=document.createElement("OPTION");
                  option.text=id;
                  option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        } 
                  
                  cyr--;
                }
 	}
 	else 
 	{
            //alert('hai');
            //alert(cmn);
           document.frmDepreciationMaster.cmbFinancialYear.length=5;
           cmbFinancialYear.innerHTML="";
           var option1=document.createElement("OPTION");
           option1.text="--Select FinancialYear--";
           option1.value=0;
           try
                        {
                            cmbFinancialYear.add(option1);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option1,null);
                        } 
        if(cmn>=12)
        {
            for (var i = 0 ; i < 2; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmDepreciationMaster.cmbFinancialYear.options[i].text=id;
              //document.frmDepreciationMaster.cmbFinancialYear.options[i].value=id;
              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
              cyr--;
            }
        }
        else
        {
            for (var i = 0 ; i < 2; i++) 
            {
                var id=(cyr-1)+"-"+(cyr);
              //document.frmDepreciationMaster.cmbFinancialYear.options[i].text=id;
              //document.frmDepreciationMaster.cmbFinancialYear.options[i].value=id;
              
              var option=document.createElement("OPTION");
              option.text=id;
              option.value=id;
                  try
                        {
                            cmbFinancialYear.add(option);
                        }catch(errorobject)
                        { 
                            cmbFinancialYear.add(option,null);
                        }
              cyr--;
            }
        }
 	}
        
}

 
 //******************************Validation Checking**************************//
 function nullCheck()
        {
                  
                  if((document.frmDepreciationMaster.cmbassetclasscode.value=="") || (document.frmDepreciationMaster.cmbassetclasscode.value.length<=0) || (document.frmDepreciationMaster.cmbassetclasscode.value=="0"))
                  { 
                       alert("Please Enter the Asset Class Code");
                       document.frmDepreciationMaster.cmbassetclasscode.focus();
                       return false;
                  }
                  
                  if((document.frmDepreciationMaster.cmbFinancialYear.value=="") ||(document.frmDepreciationMaster.cmbFinancialYear.value.length<=0) || (document.frmDepreciationMaster.cmbFinancialYear.value=="0"))
                  {
                    alert("Please Select Financial year");
                    document.frmDepreciationMaster.cmbFinancialYear.focus();
                    return false;
                  }
                  
                  if((document.frmDepreciationMaster.txtdepreciationrates.value=="") || (document.frmDepreciationMaster.txtdepreciationrates.value.length<=0))
                  {
                    alert("Please Enter Depreciation Rate");
                    document.frmDepreciationMaster.txtdepreciationrates.focus();
                    return false;
                  }
                  return true;
        }



/////////////////////////////////////////////////////  Amount limitation 
function limit_amt(field,e)
{
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(field.value.length<9)
      {
        if(field.value.length==6 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<46 || unicode==47 || unicode>57   ) 
                return false 
        }
      }
      else 
      return false;
      
}




///////////////////////////////////////////  valid amount or not
function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
        
    }
}
     
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
   
       
       var cmbassetclasscode=document.frmDepreciationMaster.cmbassetclasscode.value;
       var cmbFinancialYear=document.frmDepreciationMaster.cmbFinancialYear.value;
       var txtdepreciationrates=document.frmDepreciationMaster.txtdepreciationrates.value;
        var url="";
        
       
       if(command=="Add")
        {              var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../DepreciationMasterServlet.con?command=Add&AssetClassCode="+cmbassetclasscode+"&FinancialYear=" + cmbFinancialYear+"&DepreciationRate="+txtdepreciationrates;
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
                    url="../../../../../DepreciationMasterServlet.con?command=Update&AssetClassCode="+cmbassetclasscode+"&FinancialYear=" +cmbFinancialYear+"&DepreciationRate="+txtdepreciationrates;
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
                    url="../../../../../DepreciationMasterServlet.con?command=Delete&AssetClassCode="+cmbassetclasscode+"&FinancialYear=" +cmbFinancialYear;
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
            url="../../../../../DepreciationMasterServlet.con?command=Get&FinancialYear=" +cmbFinancialYear;
            
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
            url="../../../../../DepreciationMasterServlet.con?command=Asset";
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
          }
        }
  }


function addRow(baseResponse)
{
               var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="success")
              {                        
                 alert("Record Inserted Into Database successfully.");
                 did=baseResponse.getElementsByTagName("AssetClassCode")[0].firstChild.nodeValue; 
                 alert("Your Asset Class Code Is " +  did);
                 var items=new Array();                                     
                 cadid=baseResponse.getElementsByTagName("AssetClassCode")[0].firstChild.nodeValue;
                 sdesc=baseResponse.getElementsByTagName("FinancialYear")[0].firstChild.nodeValue;
                 DepreciationRate=baseResponse.getElementsByTagName("DepreciationRate")[0].firstChild.nodeValue;
                 //document.frmDepreciationMaster.txtassetclasscode.value=cadid;
                 //document.frmDepreciationMaster.txtassetclassdesc.value=document.frmDepreciationMaster.txtassetclassdesc.value;
                 var Drate=document.frmDepreciationMaster.txtdepreciationrates.value;
                 
                 var assetvalue=document.frmDepreciationMaster.cmbassetclasscode.options[document.frmDepreciationMaster.cmbassetclasscode.selectedIndex].text;
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
                 var txtcadid=document.createTextNode(assetvalue);                         
                 cell2.appendChild(txtcadid);  
                 var hidden1=document.createElement("input");
                 hidden1.type="hidden";
                 hidden1.name="cAssetClassCode";
                 hidden1.value=cadid;
                 cell2.appendChild(hidden1);
                 mycurrent_row.appendChild(cell2);       

                 var cell3 =document.createElement("TD");    
                 var txtsdesc=document.createTextNode(sdesc);                         
                 cell3.appendChild(txtsdesc); 
                 var hidden2=document.createElement("input");
                 hidden2.type="hidden";
                 hidden2.name="financialyear";
                 hidden2.value=sdesc;
                 cell3.appendChild(hidden2);
                 mycurrent_row.appendChild(cell3);
                 
                 var cell4 =document.createElement("TD");    
                 var txtassettype=document.createTextNode(Drate);                         
                 cell4.appendChild(txtassettype);       
                 mycurrent_row.appendChild(cell4);
                 
                 tbody.appendChild(mycurrent_row);
                
                 document.frmDepreciationMaster.CmdAdd.disabled=false;
                document.frmDepreciationMaster.CmdUpdate.disabled=true;
                document.frmDepreciationMaster.CmdDelete.disabled=true;           
                document.frmDepreciationMaster.cmbassetclasscode.selectedIndex=0;
                document.frmDepreciationMaster.cmbFinancialYear.selectedIndex=0;
                document.frmDepreciationMaster.txtdepreciationrates.value="";
               }
               if(flag=="failure")
               {
                alert("Record Not Inserted");
               }
               if(flag=="AlreadyExist")
               {
                alert("Record AlreadyExist.so,can't Inserted");
               }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");
               var items=new Array();
               
               items[0]=document.frmDepreciationMaster.cmbassetclasscode.value;
               items[1]=document.frmDepreciationMaster.cmbFinancialYear.value;
               items[2]=document.frmDepreciationMaster.txtdepreciationrates.value;
               items[3]=document.frmDepreciationMaster.cmbassetclasscode.options[document.frmDepreciationMaster.cmbassetclasscode.selectedIndex].text;
               
               var r=document.getElementById(rid);    
               var rcells=r.cells;
                //rcells.item(1).firstChild.nodeValue=items[0];
                //rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(3).firstChild.nodeValue=items[2];
//                rcells.item(1).f
                document.frmDepreciationMaster.CmdAdd.disabled=false;
                document.frmDepreciationMaster.CmdUpdate.disabled=true;
                document.frmDepreciationMaster.CmdDelete.disabled=true;           
                document.frmDepreciationMaster.cmbassetclasscode.selectedIndex=0;
                document.frmDepreciationMaster.txtdepreciationrates.value="";
                document.frmDepreciationMaster.cmbFinancialYear.selectedIndex=0;
                           
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
                      var AssetClassCode=baseResponse.getElementsByTagName("AssetClassCode")[0].firstChild.nodeValue;
                      var tbody=document.getElementById("Existing");     
                      var r=document.getElementById(rid);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 
                       document.frmDepreciationMaster.cmbassetclasscode.selectedIndex=0;
                       document.frmDepreciationMaster.cmbFinancialYear.selectedIndex=0;
                       document.frmDepreciationMaster.txtdepreciationrates.value="";
                        document.frmDepreciationMaster.CmdAdd.disabled=false;
                        document.frmDepreciationMaster.CmdUpdate.disabled=true;
                        document.frmDepreciationMaster.CmdDelete.disabled=true;      
                               
                      alert("Selected Asset Class Type Details are Deleted");                      
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
                                             
                            var AssetClassCode=baseResponse.getElementsByTagName("AssetClassCode");
                       j=1;     
                     for(var k=0;k<AssetClassCode.length;k++)
                        {
                         
                         var AssetClassCode=baseResponse.getElementsByTagName("AssetClassCode");
                        
                         var AssetClassDesc=baseResponse.getElementsByTagName("AssetClassDesc");
                         var AssetType=baseResponse.getElementsByTagName("AssetType")[k].firstChild.nodeValue;
                         var FinacialYear=baseResponse.getElementsByTagName("FinancialYear");
                         var DepreciationRate=baseResponse.getElementsByTagName("DepreciationRate");
                         
                         var cAssetClassCode=AssetClassCode.item(k).firstChild.nodeValue;
                         var cAssetClassDesc=AssetClassDesc.item(k).firstChild.nodeValue;
                         var cFinacialYear=FinacialYear.item(k).firstChild.nodeValue;
                         var cDepreciationRate=DepreciationRate.item(k).firstChild.nodeValue;
                       
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=cAssetClassCode;
                         var cell=document.createElement("TD");
                         var anc=document.createElement("A");       
                         var url="javascript:loadValuesFromTable('" + cAssetClassCode + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                         
                         var hidden=document.createElement("input");
                         hidden.type="hidden";
                         hidden.name="k";
                         hidden.value=j;
                         cell.appendChild(hidden);
                         mycurrent_row.appendChild(cell);
                     
                         var cell2 =document.createElement("TD");    
                         var txtassetclassdesc=document.createTextNode(cAssetClassDesc);                         
                         cell2.appendChild(txtassetclassdesc);
                         
                         var hidden1=document.createElement("input");
                         hidden1.type="hidden";
                         hidden1.name="cAssetClassCode";
                         hidden1.value=cAssetClassCode;
                         cell2.appendChild(hidden1);
                         mycurrent_row.appendChild(cell2);
                        
                        
                         var cell3 =document.createElement("TD");    
                         var txtfinancialyear=document.createTextNode(cFinacialYear);                         
                         cell3.appendChild(txtfinancialyear);   
                         
                         var hidden2=document.createElement("input");
                         hidden2.type="hidden";
                         hidden2.name="financialyear";
                         hidden2.value=cFinacialYear;
                         cell3.appendChild(hidden2);
                         mycurrent_row.appendChild(cell3);
                         
                         var cell4 =document.createElement("TD");    
                         var txtdepreciationrate=document.createTextNode(cDepreciationRate);                         
                         cell4.appendChild(txtdepreciationrate);       
                         mycurrent_row.appendChild(cell4);
                         j++;
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
                
                var cmbassetclasscode=document.getElementById("cmbassetclasscode");
                var option=document.createElement("OPTION");
                cmbassetclasscode.innerHTML="";
                    option.text="--Select AssetClassCode--";
                    try
                                {
                                    cmbassetclasscode.add(option);
                            }catch(errorobject)
                            { 
                                     cmbassetclasscode.add(option,null);
                            }
                            
                var AssetClassCode=baseResponse.getElementsByTagName("options"); 
                
                for(var i=0;i<AssetClassCode.length;i++)
                {
                    var tmpoption=AssetClassCode.item(i);
                    
                    var AssetClassCode1=tmpoption.getElementsByTagName("AssetClassCode")[0].firstChild.nodeValue;
                    var AssetClassDesc1=tmpoption.getElementsByTagName("AssetClassDesc")[0].firstChild.nodeValue;
                    var option=document.createElement("OPTION");
                        option.text=AssetClassDesc1;
                        option.value=AssetClassCode1;
                              try
                                {
                                    cmbassetclasscode.add(option);
                            }catch(errorobject)
                            { 
                                     cmbassetclasscode.add(option,null);
                            }
                }
              }
    
}

function loadValuesFromTable(rid1)
    {      
        rid=rid1;
          var r=document.getElementById(rid); 
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          
          var table=document.getElementById("Existing");
          
          document.frmDepreciationMaster.cmbassetclasscode.value=rcells.item(1).lastChild.value;
          document.frmDepreciationMaster.cmbFinancialYear.value=rcells.item(2).lastChild.value;
          document.frmDepreciationMaster.txtdepreciationrates.value=rcells.item(3).firstChild.nodeValue;
          document.frmDepreciationMaster.CmdAdd.disabled=true;
        document.frmDepreciationMaster.CmdUpdate.disabled=false;
        document.frmDepreciationMaster.CmdDelete.disabled=false;
        
          document.frmDepreciationMaster.CmdDelete.focus();
      
    }