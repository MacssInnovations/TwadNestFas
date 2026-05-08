
/** To handle Errors **/
//alert("js");
onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page.\n\n";
	txt+="Error: " + msg + "\n";
	txt+="URL: " + url + "\n";
	txt+="Line: " + l + "\n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}

/** To create javascript request object **/
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


//This Coding for Date Validation and Checking     
function numInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=35 && unicode !=36 )
        {
             if (unicode<48||unicode>57 ) 
                return false 
        }
}



function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
//This function allows users to enter Numbers only (with or without decimal places)
function numFloatInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(t.value.indexOf('.') != -1)alert('YES !');
        if((unicode == 8) || (unicode == 46) || (unicode == 37) || (unicode == 39)) 
        {
        	return true;
        	/*  8  --> Backspace
 			    46 --> Delete
 			    37 --> Left arrow
 			    39 --> Right arrow
        	 */
        }
        else
        {
	        var decimalPointIndex = (t.value.indexOf('.'));
	        if (unicode == 46)
	        {            
	            if (decimalPointIndex>=0)
	            return false
	        }
	        else if (unicode<48 || unicode>57)
	        {
	            return false 
	        }
        }
}


 function Exit()
 {
    self.close();
 }
 
 
 
//******************************Validation Checking**************************//
 function nullCheck()
{
     
		var accounting_unit_id=document.frmland_details.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmland_details.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

	/*	var appor_date=document.frmland_details.txt_date;
		if(appor_date.value=="")
		{ 
		     alert("Please fill the 'Date'");
		     appor_date.focus();
		     return false;
		}*/

		var financial_year = document.frmland_details.cmbFinancialYear;
		if(financial_year.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     financial_year.focus();
		     return false;
		}
           var asset_code = document.frmland_details.cmbasset;
             if(asset_code=="")
             {
             alert("Please select the asset code");
             asset_code.focus();
             return false;
             }
             
	   
		
		var remarks = document.frmland_details.txtRemarks;
		if(remarks.value=="")
		{ 
		     remarks.focus();
		     return confirm("Save without 'Remarks'?");
		}

        return true;
}
        
 function nullcheck(index)
	{
		if((index == null)||(index == '')||(index == ' '))
		{
			//alert("false");
			return false;
		}
		else
		{
			//alert("true");
			return true;
		}
	}     

//******************************************** CallServer Coding *******************//
 function callServer1(cmbAcc_UnitCode,cmbOffice_code,cmbFinancialYear)
 {
	
 	   
 	  /*var cmbAcc_UnitCode=document.frmland_details.cmbAcc_UnitCode.value;
	   var cmbOffice_code = document.frmland_details.cmbOffice_code.value;
          var cmbFinyr=document.frmland_details.txtproject_name.value;*/
	       // var cmbAcc_UnitCode=document.getElementById("accounting_unit_id").value;
	       // var cmbOffice_code=document.getElementById("accounting_unit_office_id").value;
	       // var cmbFinyr=document.getElementById("financial_year").value;
	       // alert("cmbAcc_UnitCode:=="+cmbAcc_UnitCode+"cmbAcc_UnitCode=="+cmbOffice_code+"cmbFinyr======"+cmbFinancialYear);
	        var flag=(nullcheck(cmbAcc_UnitCode) && nullcheck(cmbOffice_code) && nullcheck(cmbFinancialYear));
	        if(flag==true)																																								 
	        {
	        	//alert("hai hai hai/////")
	        	url="../../../../../Land_Details_ListAll?com=&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinyr="+cmbFinancialYear;
	        	//alert("cccccc")
		        var req=getTransport();	
	        	//alert("req"+req);
		        req.open("POST",url,true); 
		       // alert("req"+req);
		        req.onreadystatechange=function()
		        {
		        	//alert("reqfdsfsdfdsfdsf"+req);
		        	LoadRecordResponse(req);
		        }   

		        req.send(null);
	        }
	        else
	        {
	        	alert("Please fill all the fields");
	        }
	    
 }
function callServer(command)
{ // alert("inside callserver");
           var accounting_unit_id=document.frmland_details.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmland_details.cmbOffice_code.value;
           var txtproject_name=document.frmland_details.txtproject_name.value;
	   var financial_year = document.frmland_details.cmbFinancialYear.value;
	   var asset_code = document.frmland_details.cmbasset.value;
	   var txtBP_No = document.frmland_details.txtBP_No.value;
	   var txttaluk = document.frmland_details.txttaluk.value;
	   var txtvillage = document.frmland_details.txtvillage.value;
	   var cmbacqn = document.frmland_details.cmbacqn.value;
           var txtnorth = document.frmland_details.txtnorth.value;
	   var txtSouth = document.frmland_details.txtSouth.value;
           var txtEast = document.frmland_details.txtEast.value;
           var txtWest = document.frmland_details.txtWest.value;
	   
	   var cmblandtype = document.frmland_details.cmblandtype.value;
	 //  var txtlandtype = document.frmland_details.txtlandtype.value;
	   var txtext_area = document.frmland_details.txtext_area.value;
	   var txtsurvey_no = document.frmland_details.txtsurvey_no.value;
	   var txtfound = document.frmland_details.txtfound.value;
	   var txtowner = document.frmland_details.txtowner.value;
	   var txtlease = document.frmland_details.txtlease.value;
           
	   var txttotal_amount = document.frmland_details.txttotal_amount.value;
 	   var txtpaid_land = document.frmland_details.txtpaid_land.value;
           var txtpaid_buildings= document.frmland_details.txtpaid_buildings.value;
           var txtvoucher_no = document.frmland_details.txtvoucher_no.value;
           var txtvoucher_date = document.frmland_details.txtvoucher_date.value;
           var txtregister_office = document.frmland_details.txtregister_office.value;
           var txtdoc_no = document.frmland_details.txtdoc_no.value;
           var txtdoc_date = document.frmland_details.txtdoc_date.value;
           var txtdetails= document.frmland_details.txtdetails.value;
           var remarks = document.frmland_details.txtRemarks.value;
	   
	   
	   
       var url="";

       if(command=="Add")
        {              
    	   			var flag=nullCheck() ;
                    if(flag==true)
                    {
                    url="../../../../../Land_details?command=Add&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code+"&txtBP_No="+txtBP_No+"&txtproject_name="+txtproject_name+
                    "&txttaluk="+txttaluk+"&txtvillage="+txtvillage+"&cmbacqn="+cmbacqn+"&txtnorth="+txtnorth+"&txtSouth="+txtSouth+"&txtEast="+txtEast+"&txtWest="+txtWest+"&cmblandtype="+cmblandtype+
                    "&txtext_area="+txtext_area+"&txtsurvey_no="+txtsurvey_no+"&txtfound="+txtfound+
                    "&txtowner="+txtowner+"&txtlease="+txtlease+"&txttotal_amount="+txttotal_amount+"&txtpaid_land="+txtpaid_land+
                    "&txtpaid_buildings="+txtpaid_buildings+"&txtvoucher_no="+txtvoucher_no+"&txtvoucher_date="+txtvoucher_date+"&txtregister_office="+txtregister_office+
                    "&txtdoc_no="+txtdoc_no+"&txtdoc_date="+txtdoc_date+"txtdetails="+txtdetails+"&remarks= " + remarks;
               //     alert(url);
                    var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }
         }
       
     
        else if(command=="Update")
        {
                    var flag=nullCheck() ;
                    if(flag==true)
                    {
                    url="../../../../../Land_details?command=Update&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code+"&txtBP_No="+txtBP_No+"&txtproject_name="+txtproject_name+
                    "&txttaluk="+txttaluk+"&txtvillage="+txtvillage+"&cmbacqn="+cmbacqn+"&txtnorth="+txtnorth+"&txtSouth="+txtSouth+"&txtEast="+txtEast+"&txtWest="+txtWest+"&cmblandtype="+cmblandtype+
                    "&txtext_area="+txtext_area+"&txtsurvey_no="+txtsurvey_no+"&txtfound="+txtfound+
                    "&txtowner="+txtowner+"&txtlease="+txtlease+"&txttotal_amount="+txttotal_amount+"&txtpaid_land="+txtpaid_land+
                    "&txtpaid_buildings="+txtpaid_buildings+"&txtvoucher_no="+txtvoucher_no+"&txtvoucher_date="+txtvoucher_date+"&txtregister_office="+txtregister_office+
                    "&txtdoc_no="+txtdoc_no+"&txtdoc_date="+txtdoc_date+"txtdetails="+txtdetails+"&remarks="+remarks;
                    var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
                    }
        }
        else if(command=="Delete")
        {  
        			url="../../../../../Land_details?command=Delete&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
        			var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    }   
                    req.send(null);
        }
        else if(command=="Get")
        {               
        	url="../../../../../Land_details?command=Get&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
        		//alert(url);	
                                var req=getTransport();
		            req.open("POST",url,true);  
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            }   
		            req.send(null);
        }
}  


//********************************* CallServer Response Coding ***************************************//
/*function clearAllGrids(tbl)
{
	var tbody = document.getElementById(tbl);
	var k = tbody.childNodes.length;
	for(var i=k-1;i>=0;i--)
	{
		tbody.deleteRow(i-1);
	}
}*/
function processResponse(req)
{   
  if(req.readyState==4)
  {
      if(req.status==200)
      {   

    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          
          var command=tagCommand.firstChild.nodeValue; 
          
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
              if(command=="Add")
              {
            	  if(flag=="success")
            	  {                  
            		  alert("Record Inserted into Database successfully.");
            		  clearDetails();
            	  }
            	  else
                  {
            		  alert("Failed to Insert the record.");
                  }
              }
              
              else if(command=="Delete")
              { 
            	  if(flag=="success")
            	  {
            		  alert("Record Deleted from Database Successfully.");
            		  clearDetails();
            	  }
            	  else
                  {
            		  alert("Failed to Insert the record.");
                  }
              }
              
              else if(command=="Update")
              {
            	  if(flag=="success")
            	  {
            		  alert("Record Updated into Database Successfully.");
                          clearDetails();
            	  }
            	  else
                  {
            		  alert("Failed to Update the record.");
                  }
              }
              else if(command=="List")
              {
            	  
            	  
              }
              
              else if(command=="Get")
              {
            	  if(flag=="success")
            	  {
            		 var exists = baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
            		  if(exists == "Yes")
            		  {
                        	  document.getElementById('CmdAdd').disabled = true;
            			  document.getElementById('CmdUpdate').disabled = false;
            			  document.getElementById('CmdDelete').disabled = false;
            			
                               document.getElementById('txtproject_name').value = baseResponse.getElementsByTagName("txtproject_name")[0].firstChild.nodeValue;
	                       document.getElementById('txtBP_No').value=baseResponse.getElementsByTagName("txtBP_No")[0].firstChild.nodeValue;
                               document.getElementById('txttaluk').value=baseResponse.getElementsByTagName("txttaluk")[0].firstChild.nodeValue;
                               document.getElementById('txtvillage').value=baseResponse.getElementsByTagName("txtvillage")[0].firstChild.nodeValue;
                               document.getElementById('cmbacqn').value=baseResponse.getElementsByTagName("cmbacqn")[0].firstChild.nodeValue;
                               document.getElementById('txtnorth').value=baseResponse.getElementsByTagName("txtnorth")[0].firstChild.nodeValue;
                               document.getElementById('txtSouth').value=baseResponse.getElementsByTagName("txtSouth")[0].firstChild.nodeValue;
                               document.getElementById('txtEast').value=baseResponse.getElementsByTagName("txtEast")[0].firstChild.nodeValue;
                               document.getElementById('cmblandtype').value=baseResponse.getElementsByTagName("cmblandtype")[0].firstChild.nodeValue;
                               //document.getElementById('txtlandtype').value=baseResponse.getElementsByTagName("txtlandtype")[0].firstChild.nodeValue;
                               document.getElementById('txtext_area').value=baseResponse.getElementsByTagName("txtext_area")[0].firstChild.nodeValue;
                               document.getElementById('txtsurvey_no').value=baseResponse.getElementsByTagName("txtsurvey_no")[0].firstChild.nodeValue;
                               document.getElementById('txtfound').value=baseResponse.getElementsByTagName("txtfound")[0].firstChild.nodeValue;
                               document.getElementById('txtowner').value=baseResponse.getElementsByTagName("txtowner")[0].firstChild.nodeValue;
                               document.getElementById('txtlease').value=baseResponse.getElementsByTagName("txtlease")[0].firstChild.nodeValue;
                               document.getElementById('txtWest').value=baseResponse.getElementsByTagName("txtWest")[0].firstChild.nodeValue;
                               document.getElementById('txtRemarks').value = baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
            	               document.getElementById('txttotal_amount').value=baseResponse.getElementsByTagName("txttotal_amount")[0].firstChild.nodeValue;
                               document.getElementById('txtpaid_land').value=baseResponse.getElementsByTagName("txtpaid_land")[0].firstChild.nodeValue;
 	                       document.getElementById('txtpaid_buildings').value=baseResponse.getElementsByTagName("txtpaid_buildings")[0].firstChild.nodeValue;
                               document.getElementById('txtvoucher_no').value=baseResponse.getElementsByTagName("txtvoucher_no")[0].firstChild.nodeValue;
                               document.getElementById('txtvoucher_date').value=baseResponse.getElementsByTagName("voucher_date")[0].firstChild.nodeValue;
                               document.getElementById('txtregister_office').value=baseResponse.getElementsByTagName("txtregister_office")[0].firstChild.nodeValue;
                               document.getElementById('txtdoc_no').value=baseResponse.getElementsByTagName("txtdoc_no")[0].firstChild.nodeValue;
                               document.getElementById('txtdoc_date').value=baseResponse.getElementsByTagName("doc_date")[0].firstChild.nodeValue;
                               document.getElementById('txtdetails').value=baseResponse.getElementsByTagName("txtdetails")[0].firstChild.nodeValue;
          
            	  }
                  else
            		  {
            			  clearDetails();
            			  
            			  document.getElementById('CmdAdd').disabled = false;
            			  document.getElementById('CmdUpdate').disabled = true;
            			  document.getElementById('CmdDelete').disabled = true;
            		  }
            	  }
            	  else
            	  {
            		  alert("Error Retrieving values from Database.");
            	  }
              }
         }
        }
     }   



function LoadRecordResponse(req)
{
    if(req.readyState==4)
    {
    	//alert("inside load record response");
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
            
            
            
           
	            if(flag=="success")
	            {   

	            	var len = baseResponse.getElementsByTagName("ASSET_CODE").length;
	            	
	            	for(var i=0; i<len; i++)
	            	{
		    	        //document.frmListBuildings.cmbAcc_UnitCode.value = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
		    	        //document.frmListBuildings.cmbOffice_code.value = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[0].firstChild.nodeValue;
		    	        //document.frmListBuildings.cmbFinyr.value = baseResponse.getElementsByTagName("FINANCIAL_YEAR")[0].firstChild.nodeValue;
		    	        var val = '';
		    	        
	            		val += baseResponse.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;
		    	           
		            	//document.frmListBuildings.txtDate.value = baseResponse.getElementsByTagName("TRANS_DATE")[i].firstChild.nodeValue;
	            		val += '#@/' + baseResponse.getElementsByTagName("TALUK")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.cmbOwner.value = baseResponse.getElementsByTagName("OWNER_CODE")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtSurvey.value = baseResponse.getElementsByTagName("SURVEY_NO")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtDoorNo.value = baseResponse.getElementsByTagName("DOOR_NO")[i].firstChild.nodeValue;
	
		    	        //document.frmListBuildings.txtVillage.value = baseResponse.getElementsByTagName("VILLAGE")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtBuidConsYear.value = baseResponse.getElementsByTagName("YEAR_OF_CONSTRUCTION")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtBuildType.value = baseResponse.getElementsByTagName("TYPE_OF_BUILDING")[i].firstChild.nodeValue;
	            		val += '#@/' + baseResponse.getElementsByTagName("VILLAGE")[i].firstChild.nodeValue;
	            		val += '#@/' + baseResponse.getElementsByTagName("NATURE_ACQN")[i].firstChild.nodeValue;
	
		    	        //document.frmListBuildings.txtStructuralElements.value = baseResponse.getElementsByTagName("STRUCTURED_BLDG_ELEMENTS")[i].firstChild.nodeValue;
	            		val += '#@/' + baseResponse.getElementsByTagName("OWNER_NAME")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtElectricalCost.value = baseResponse.getElementsByTagName("TOTAL_ELECTRICAL_COST")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtExternalServCost.value = baseResponse.getElementsByTagName("TOTAL_EXTERNAL_COST")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtAdditionCost.value = baseResponse.getElementsByTagName("TOTAL_ADDITIONS_COST")[i].firstChild.nodeValue;
	
	            		val += '#@/' + baseResponse.getElementsByTagName("LEASE_PERIOD")[i].firstChild.nodeValue;
	            		//val += '#@/' + baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
	            		val += '#@/' + baseResponse.getElementsByTagName("TOTAL_AMOUNT")[i].firstChild.nodeValue;
		    	        //document.frmListBuildings.txtRemarks.value = baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
	            		val += '#@/' + baseResponse.getElementsByTagName("REG_OFFICE")[i].firstChild.nodeValue;
	            		
	            		addRow('tblBuild',val,true);
	            	}
	            }
            	else
            	{
            		alert("Failed to load Buildings!");
            	}
                
	            
            
            
            
	            
	            
            if(command=="LoadFloor")
            {
	            if(flag=="success")
	            {   
	    	        var floor = baseResponse.getElementsByTagName("FLOOR");
	    	        var flen = floor.length;
	    	        	    	        
	    	        for(var i=0; i<flen; i++)
	    	        {
	    	        	var val = '';
	    	        	
	    	        	val += floor[i].getElementsByTagName("FLOOR_NO")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("YEAR_OF_CONSTRUCTION")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("FLOOR_HEIGHT")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("PLINTH_AREA")[0].firstChild.nodeValue;
	
	    	        	val += '#@/' + floor[i].getElementsByTagName("CIVIL_COST")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("ELECTRICAL_COST")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("EXTERNAL_COST")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("ADDITIONAL_COST")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("BOOK_VALUE")[0].firstChild.nodeValue;
	
	    	        	val += '#@/' + floor[i].getElementsByTagName("BP_NO_FOR_CONSTRUCTION")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
	    	        	val += '#@/' + floor[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
		    	        
		    	        addRow('tblListFloor',val,true);
	    	        }
	            }
            	else
            	{
            		alert("Failed to load Floor!");
            	}
            }    
	            
    	        
    	        
    	        
            
            if(command=="LoadOffice")
            {         alert(flag);
	            if(flag=="success")
	            {   alert("inside loadoffice");
	                var office = baseResponse.getElementsByTagName("OFFICE");
	    	        var olen = office.length;
	    	        for(var i=0; i<olen; i++)
	    	        {
	    	        	document.frmland_details.txtOfficeFloorNo.value = office[i].getElementsByTagName("FLOOR_NO")[0].firstChild.nodeValue;
		    	        document.frmland_details.hidOfficeType.value = office[i].getElementsByTagName("TYPE_OF_OCCUPYING_OFFICE")[0].firstChild.nodeValue;
		    	        document.frmland_details.cmbOffice.value = office[i].getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
		    	        document.frmland_details.txtOfficeRemarks.value = office[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
		    	        
		    	        addRow('tblListOffice',val,false);
		    	        //addTempRow('funEditOffice','txtOfficeFloorNo/cmbOffice','tblListOffice/txtOfficeFloorNo/cmbOffice/hidOfficeType/txtOfficeRemarks'); 
		    	        //hideRemarks('tblListOffice','txtOfficeRemarks');  
		    	        //cmbCaptionGrid('tblListOffice/cmbOffice'); 
		    	        //clearOffice();
	    	        }
	            }
            	else
            	{
            		alert("Failed to load Office!");
            	}
            }    
	            
            
            
            
        }
    }
}
function clearAll()
{
	  document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	  document.getElementById('cmbOffice_code').options[0].selected = "selected";
	  clearDetails();
}
function clearDetails()
{
	  // document.getElementById('cmbasset').value = "";
	   document.getElementById('txtBP_No').value="";
	   document.getElementById('txttaluk').value="";
	   document.getElementById('txtvillage').value="";
	   document.getElementById('cmbacqn').value="";
           document.getElementById('txtnorth').value="";
	   document.getElementById('txtSouth').value="";
           document.getElementById('txtEast').value="";
           document.getElementById('cmblandtype').value="";
	 //  document.getElementById('txtlandtype').value="";
	   document.getElementById('txtext_area').value="";
	   document.getElementById('txtsurvey_no').value="";
	   document.getElementById('txtfound').value="";
	   document.getElementById('txtowner').value="";
	   document.getElementById('txtlease').value="";
	   document.getElementById('txtproject_name').value = "";
           document.getElementById('txtWest').value = "";
           document.getElementById('txtRemarks').value = "";
           document.getElementById('txttotal_amount').value="";
           document.getElementById('txtpaid_land').value="";
           document.getElementById('txtpaid_buildings').value="";
           document.getElementById('txtvoucher_no').value="";
           document.getElementById('txtvoucher_date').value="";
           document.getElementById('txtregister_office').value="";
           document.getElementById('txtdoc_no').value="";
           document.getElementById('txtdoc_date').value="";
           document.getElementById('txtdetails').value="";

	  document.getElementById('CmdAdd').disabled = false;
	  document.getElementById('CmdUpdate').disabled = true;
	  document.getElementById('CmdDelete').disabled = true;
}


var window_Asset_ValueAC_Details;
function ListAll()
{
	//alert("jjjjj");
	var accounting_unit_id=document.frmland_details.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmland_details.cmbOffice_code.value;
	var financial_year = document.frmland_details.cmbFinancialYear.value;
	//ar asset_code = document.frmland_details.cmbasset.value;

    if (window_Asset_ValueAC_Details && window_Asset_ValueAC_Details.open && !window_Asset_ValueAC_Details.closed) 
    {
    	window_Asset_ValueAC_Details.resizeTo(500,500);
    	window_Asset_ValueAC_Details.moveTo(250,250); 
    	window_Asset_ValueAC_Details.focus();
    }
    else
    {
    	window_Asset_ValueAC_Details=null
    }
     var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var url = "Land_details_List.jsp?" +
     			"accounting_unit_id="+accounting_unit_id + 
     			"&accounting_unit_office_id="+accounting_unit_office_id +
     			"&financial_year="+financial_year;
     window_Asset_ValueAC_Details= window.open(url,"mywindow1","resizable=YES, scrollbars=yes"); 
     window_Asset_ValueAC_Details.moveTo(250,250);    
}

window.onunload=function()
{
	if (window_Asset_ValueAC_Details && window_Asset_ValueAC_Details.open && !window_Asset_ValueAC_Details.closed) window_Asset_ValueAC_Details.close();
}

function doParent(accounting_unit_id,accounting_unit_office_id,financial_year,asset_code)
{
    document.getElementById('cmbAcc_UnitCode').value=accounting_unit_id;
    document.getElementById('cmbOffice_code').value=accounting_unit_office_id;
    document.getElementById('cmbFinancialYear').value=financial_year;
    document.getElementById('cmbasset').value=asset_code;

    callServer('Get');
}

function toCheck1()
{
if(document.frmland_details.txtWest.value.length>0)
   {
     
         if(!isNaN(document.frmland_details.txtWest.value))
           {
              alert("Person name must be in Character");
              document.frmland_details.txtWest.value="";
              document.frmland_details.txtWest.focus();
              return false;
           }
        }
   return true;
}
function toFocus()
{
 //alert("test");
  //var FirstField=document.frmland_details.txtWest.value;
 if((document.frmland_details.txtWest.value=="") || (document.frmland_details.txtWest.value<=0))
  {
     alert("Please Enter Person name First");
     document.frmland_details.txtWest.focus();
     return false;
  }
   
}

     
 
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
                
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

var window_BankAccNumber;
function ListHeads()
    {
    
     if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
    {
       window_BankAccNumber.resizeTo(500,500);
       window_BankAccNumber.moveTo(250,250); 
       window_BankAccNumber.focus();
    }
    else
    {
        window_BankAccNumber=null
    }
         var unitid=document.getElementById("cmbAcc_UnitCode").value;
     //    var cmbDistrict=document.getElementById("cmbDistrict").value;
         window_BankAccNumber= window.open("Project_Scheme_List.jsp?","mywindow1","resizable=YES, scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}


function doParentBankAccNumbers(projectId,projectname)
{
   
         
         
          /*  var d=document.getElementById("CmdUpdate");
            d.style.display="block";
            var d2=document.getElementById("CmdDelete");
            d2.style.display="block";
            var d1=document.getElementById("CmdAdd");
            d1.style.display="none";  */
     document.getElementById("txtproject_name").value=projectId;     
     document.getElementById("txtsubledger").value=projectname; 
                    
}