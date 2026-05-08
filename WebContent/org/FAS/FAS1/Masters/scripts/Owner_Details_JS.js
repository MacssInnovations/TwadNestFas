
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
   document.frmOwnerDetails.txtOwnerCode.value="";
   document.frmOwnerDetails.radOwnerType[0].checked=true;
   document.frmOwnerDetails.txtOwnerDesc.value="";
   document.frmOwnerDetails.hidRowId.value="";
   
   document.frmOwnerDetails.CmdAdd.disabled=false;
   document.frmOwnerDetails.CmdUpdate.disabled=true;
   document.frmOwnerDetails.CmdDelete.disabled=true;
   
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
		
		if((document.frmOwnerDetails.cmbAcc_UnitCode.value=="") ||(document.frmOwnerDetails.cmbAcc_UnitCode.value==0))
		{
			alert("Please select the Accounting Unit to which the Owner is related");
			document.frmOwnerDetails.cmbAcc_UnitCode.focus();
			return false;
		}
	/*		
		if((document.frmOwnerDetails.cmbOffice_code.value=="") ||(document.frmOwnerDetails.cmbOffice_code.value==0))
		{
			alert("Please select the Accounting Unit Office to which the Owner is related");
			document.frmOwnerDetails.cmbOffice_code.focus();
			return false;
		}
			*/
		if((document.frmOwnerDetails.radOwnerType[0].checked==false) && (document.frmOwnerDetails.radOwnerType[1].checked==false))
		{ 
			alert("Please select the Type of Ownership");
			document.frmOwnerDetails.radOwnerType[0].focus();
			return false;
		}
			 
		if((document.frmOwnerDetails.txtOwnerDesc.value=="") ||(document.frmOwnerDetails.txtOwnerDesc.value.length<=0))
		{
			alert("Please enter Ownership Description");
			document.frmOwnerDetails.txtOwnerDesc.focus();
			return false;
		}
		return true;
 }
        
        
//******************************************** CallServer Coding *******************//


function callServer(command,param)
 {
		var accounting_unit_id=document.frmOwnerDetails.cmbAcc_UnitCode.value;
       	var accounting_unit_office_id=document.frmOwnerDetails.cmbOffice_code.value;	
       	var owner_code=document.frmOwnerDetails.txtOwnerCode.value;
       	var owner_type;
       	if(document.frmOwnerDetails.radOwnerType[0].checked)
       	{
       		owner_type = document.frmOwnerDetails.radOwnerType[0].value;
       	}
       	else
       	{
       		owner_type = document.frmOwnerDetails.radOwnerType[1].value;
       	}
       	var owner_desc=document.frmOwnerDetails.txtOwnerDesc.value;
       	
       	
        var url="";
        
      
        if(command=="Add")
        {              
			var flag=nullCheck();
            if(flag==true)
            {
                url="../../../../../Owner_Details_Serv?command=Add&accounting_unit_id=" + accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&owner_code="+owner_code+"&owner_type="+owner_type+"&owner_desc="+owner_desc;
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
                url="../../../../../Owner_Details_Serv?command=Update&accounting_unit_id=" + accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&owner_code="+owner_code+"&owner_type="+owner_type+"&owner_desc="+owner_desc;
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
			url="../../../../../Owner_Details_Serv?command=Delete&accounting_unit_id=" + accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&owner_code="+owner_code;
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
        	url="../../../../../Owner_Details_Serv?command=Get&accounting_unit_id=" + accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id;
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

                 var tbody=document.getElementById("tblList");
                 //var table=document.getElementById("Existing");
				 var k=tbody.rows.length;
				 
				 var accounting_unit_id=baseResponse.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
				 var accounting_unit_office_id=baseResponse.getElementsByTagName("accounting_unit_office_id")[0].firstChild.nodeValue;
				 var owner_code=baseResponse.getElementsByTagName("owner_code")[0].firstChild.nodeValue;
				  var owner_type;
				  if(baseResponse.getElementsByTagName("owner_type")[0].firstChild.nodeValue == 'T')
				  {
					  owner_type = "TWAD";
				  }
				  else
				  {
					  owner_type = "Non-TWAD";
				  }
				  var owner_desc=baseResponse.getElementsByTagName("owner_desc")[0].firstChild.nodeValue;

				  
				  var mycurrent_row=document.createElement("TR");
				  mycurrent_row.id=k;
				  var cell=document.createElement("TD");
				  var anc=document.createElement("A");       
				  var url="javascript:loadValuesFromTable('" + k + "')";              
				  anc.href=url;
				  var txtedit=document.createTextNode("Edit");
				  anc.appendChild(txtedit);
				  cell.appendChild(anc);
				  mycurrent_row.appendChild(cell);
				
				  var cell1 =document.createElement("TD");    
				  var txtownercode=document.createTextNode(owner_code);                         
				  cell1.appendChild(txtownercode);
				  mycurrent_row.appendChild(cell1);
				
				  var cell2 =document.createElement("TD");    
				  var txtownertype=document.createTextNode(owner_type);
				  cell2.appendChild(txtownertype);
				  mycurrent_row.appendChild(cell2);

				  var cell3 =document.createElement("TD");    
				  var txtownerdesc=document.createTextNode(owner_desc);                         
				  cell3.appendChild(txtownerdesc);       
				  mycurrent_row.appendChild(cell3);
				
				  var cell4 =document.createElement("TD");    
				  var txt_accounting_unit_id=document.createTextNode(accounting_unit_id);
				  cell4.style.display = 'none';
				  cell4.appendChild(txt_accounting_unit_id);       
				  mycurrent_row.appendChild(cell4);       
				
				  var cell5 =document.createElement("TD");    
				  var txt_accounting_unit_office_id=document.createTextNode(accounting_unit_office_id);
				  cell5.style.display = 'none';
				  cell5.appendChild(txt_accounting_unit_office_id);       
				  mycurrent_row.appendChild(cell5);       
				
				  tbody.appendChild(mycurrent_row);
          
                document.frmOwnerDetails.CmdAdd.disabled=false;
                document.frmOwnerDetails.CmdUpdate.disabled=true;
                document.frmOwnerDetails.CmdDelete.disabled=true;     
                
                document.frmOwnerDetails.txtOwnerCode.value="";
                document.frmOwnerDetails.txtOwnerDesc.value="";
                document.frmOwnerDetails.radOwnerType[0].checked = true;
                document.frmOwnerDetails.hidRowId.value="";
                callServer('Get','');
               }
              else
              {
            	  alert("Failed to Add");
              }
}

function updateRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
       {   
           alert("Record Updated Successfully.");

           	   var items=new Array();
               items[0]=document.frmOwnerDetails.txtOwnerCode.value;
               if(document.frmOwnerDetails.radOwnerType[0].checked==true)
               {
            	   items[1]="TWAD";
               }
               else
               {
            	   items[1]="Non-TWAD";
               }
//               alert("item[1] = "+items[1]);
               items[2]=document.frmOwnerDetails.txtOwnerDesc.value;
               
               var rowNum=document.getElementById("hidRowId").value;
               var r=document.getElementById(rowNum);    
               var rcells=r.cells;			        

                rcells.item(1).firstChild.nodeValue=items[0];
                rcells.item(2).firstChild.nodeValue=items[1];
                rcells.item(4).firstChild.nodeValue=items[2];
                document.frmOwnerDetails.CmdAdd.disabled=false;
                document.frmOwnerDetails.CmdUpdate.disabled=true;
                document.frmOwnerDetails.CmdDelete.disabled=true;    
                
                document.frmOwnerDetails.txtOwnerCode.value="";
                document.frmOwnerDetails.txtOwnerDesc.value="";
                document.frmOwnerDetails.radOwnerType[0].checked = true;
                document.frmOwnerDetails.hidRowId.value="";
                callServer('Get','');

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
                	  alert("Record Cancelled Successfully.");
                      var rowNum=document.getElementById("hidRowId").value;
                      var tbody=document.getElementById("Existing");     
                      /*var r=document.getElementById(rowNum);    
                      var ri=r.rowIndex;               
                      tbody.deleteRow(ri); 

                      document.frmOwnerDetails.txtOwnerCode.value="";
                      document.frmOwnerDetails.radOwnerType[0].checked=true;
		              document.frmOwnerDetails.txtOwnerDesc.value="";
		              document.frmOwnerDetails.hidRowId.value="";*/
                      
                      document.frmOwnerDetails.CmdAdd.disabled=false;
                      document.frmOwnerDetails.CmdUpdate.disabled=true;
                      document.frmOwnerDetails.CmdDelete.disabled=true;
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
                       
                  document.frmOwnerDetails.txtOwnerCode.value="";
                  document.frmOwnerDetails.radOwnerType[0].checked=true;
	              document.frmOwnerDetails.txtOwnerDesc.value="";
	              document.frmOwnerDetails.hidRowId.value="";

            	   document.frmOwnerDetails.CmdAdd.disabled=false;
            	   document.frmOwnerDetails.CmdUpdate.disabled=true;
            	   document.frmOwnerDetails.CmdDelete.disabled=true;

            	   var tbody=document.getElementById("tblList");
                   //var table=document.getElementById("Existing");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }   
                                         
                    var len=baseResponse.getElementsByTagName("owner_code").length;
                        
                    for(var k=0;k<len;k++)
                    {

                     var owner_code=baseResponse.getElementsByTagName("owner_code")[k].firstChild.nodeValue;
                     var owner_type;
                     if(baseResponse.getElementsByTagName("owner_type")[k].firstChild.nodeValue == 'T')
                     {
                    	 owner_type="TWAD";
                     }
                     else
                     {
                    	 owner_type="Non-TWAD";
                     }
                     var owner_desc=baseResponse.getElementsByTagName("owner_desc")[k].firstChild.nodeValue;
                     var accounting_unit_id=baseResponse.getElementsByTagName("accounting_unit_id")[k].firstChild.nodeValue;
                     var accounting_unit_office_id=baseResponse.getElementsByTagName("accounting_unit_office_id")[k].firstChild.nodeValue;
                     var view = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
                     
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=k;
                     var cell=document.createElement("TD");
                     var anc=document.createElement("A");
                     if(view=="C"){
                    	 var priceSpan = document.createElement("span");
              			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
              			priceSpan.appendChild(document.createTextNode("Cancel"));			
              			cell.appendChild(priceSpan);      			
                     }else{
                    	 var url="javascript:loadValuesFromTable('" + k + "')";              
                         anc.href=url;
                         var txtedit=document.createTextNode("Edit");
                         anc.appendChild(txtedit);
                         cell.appendChild(anc);
                     }                     
                     mycurrent_row.appendChild(cell);
                 
                     var cell1 =document.createElement("TD");    
                     var txt_owner_code=document.createTextNode(owner_code);                         
                     cell1.appendChild(txt_owner_code);
                     mycurrent_row.appendChild(cell1);

                     var cell2 =document.createElement("TD");    
                     var txt_owner_type=document.createTextNode(owner_type);
                     cell2.appendChild(txt_owner_type);
                     mycurrent_row.appendChild(cell2);

                     var cell3 =document.createElement("TD");    
                     var txt_owner_desc=document.createTextNode(owner_desc);                         
                     cell3.appendChild(txt_owner_desc);       
                     mycurrent_row.appendChild(cell3);

                     var cell4 =document.createElement("TD");    
                     var txt_accounting_unit_id=document.createTextNode(accounting_unit_id);
                     cell4.style.display = 'none';
                     cell4.appendChild(txt_accounting_unit_id);       
                     mycurrent_row.appendChild(cell4);       

                     var cell5 =document.createElement("TD");    
                     var txt_accounting_unit_office_id=document.createTextNode(accounting_unit_office_id);
                     cell5.style.display = 'none';
                     cell5.appendChild(txt_accounting_unit_office_id);       
                     mycurrent_row.appendChild(cell5);
                     
                     var cell6 =document.createElement("TD");    
                     if(view=="L"){
                    	 var txtstat=document.createTextNode("LIVE");
                     }else{
                    	 var txtstat=document.createTextNode("CANCEL");
                     }       
                     cell6.appendChild(txtstat);       
                     mycurrent_row.appendChild(cell6);

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
      //var table=document.getElementById("Existing");

      document.getElementById("hidRowId").value = rid;
      document.frmOwnerDetails.txtOwnerCode.value=rcells.item(1).firstChild.nodeValue;
      if(rcells.item(2).firstChild.nodeValue == "TWAD")
      {
    	  document.frmOwnerDetails.radOwnerType[0].checked=true;
      }
      else
      {
    	  document.frmOwnerDetails.radOwnerType[1].checked=true;
      }
      document.frmOwnerDetails.txtOwnerDesc.value=rcells.item(3).firstChild.nodeValue;
      
      document.frmOwnerDetails.CmdAdd.disabled=true;
      document.frmOwnerDetails.CmdUpdate.disabled=false;
      document.frmOwnerDetails.CmdDelete.disabled=false;
    
      document.frmOwnerDetails.CmdDelete.focus();
}
function LoadOffice(unitID_val)
{
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
      //  alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        }
        req.send();
    }
}
function common_LoadOffice()
{
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;          
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        }
        req.send(null);
    }     
}
function handle_loadOffice(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {
        //alert(req.responseText);
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
       // alert(baseresponse);
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
          
            var offidvalues=baseresponse.getElementsByTagName("offid");
           
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
        }
        else
        {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
        }
            
             
     }
    }
}

//CURRENTLY WORKING ====== 
//accounting_unit_id,accounting_unit_office_id,owner_code,owner_type,owner_desc
//cmbAcc_UnitCode  cmbOffice_code  txtOwnerCode  radOwnerType  txtOwnerDesc  hidRowId

