
/** To handle Errors **/
//onerror=handleErr;
var txt="";
/*function handleErr(msg,url,l)
{
	txt="There was an error on this page.\n\n";
	txt+="Error: " + msg + "\n";
	txt+="URL: " + url + "\n";
	txt+="Line: " + l + "\n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}*/

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


function call_mainJSP_script(dateField,blur_flag)
{}

function enableWarrantyDates()
{
	document.getElementById('imgCal1').style.display='inline';
	document.getElementById('imgCal2').style.display='inline';
	
	document.getElementById('txtWarrantyFrom').disabled=false;
	document.getElementById('txtWarrantyTo').disabled=false;
}

function disableWarrantyDates()
{
	document.getElementById('imgCal1').style.display='none';
	document.getElementById('imgCal2').style.display='none';
	
	document.getElementById('txtWarrantyFrom').value="";
	document.getElementById('txtWarrantyTo').value="";
	
	document.getElementById('txtWarrantyFrom').disabled=true;
	document.getElementById('txtWarrantyTo').disabled=true;
}

function ifDepre(dep)
{
	var app = document.getElementById('cmbApportCat');
	var txtAssetAfterApport = document.getElementById('txtAssetAfterApport');
	
	if(dep != "0")
	{
		app.value="0";
		app.disabled = true; 
		txtAssetAfterApport.value = "";
		txtAssetAfterApport.disabled = true;
	}
	else
	{
		app.disabled = false;
		txtAssetAfterApport.disabled = false;
	}
}


function ifApport(app)
{
	var dep = document.getElementById('cmbDepreCat'); 
	var txtAssetAfterDepre = document.getElementById('txtAssetAfterDepre');

	if(app != "0")
	{
		dep.value="0";
		dep.disabled = true;
		txtAssetAfterDepre.value = "";
		txtAssetAfterDepre.disabled = true;
	}
	else
	{
		dep.disabled = false;
		txtAssetAfterDepre.disabled = false;
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
     
		var accounting_unit_id=document.frmAssetValueACDetails.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmAssetValueACDetails.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

		var date_transaction=document.frmAssetValueACDetails.txtDate;
		if(date_transaction.value=="")
		{ 
		     alert("Please fill the 'Date'");
		     date_transaction.focus();
		     return false;
		}

		var financial_year = document.frmAssetValueACDetails.cmbFinYear;
		if(financial_year.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     financial_year.focus();
		     return false;
		}

		var num_ac_office_id = document.frmAssetValueACDetails.cmbNAOffice;
		if(num_ac_office_id.value=="")
		{ 
		     alert("Please select the 'office where NA is maintained'");
		     num_ac_office_id.focus();
		     return false;
		}

		var asset_type_code = document.frmAssetValueACDetails.cmbAssetType;
		if(asset_type_code.value=="")
		{ 
		     alert("Please select the 'Asset Type'");
		     asset_type_code.focus();
		     return false;
		}

		var asset_major_class_code = document.frmAssetValueACDetails.cmbMajorClass;
		if(asset_major_class_code.value=="")
		{ 
		     alert("Please select the 'Major Classification'");
		     asset_major_class_code.focus();
		     return false;
		}

		var asset_minor_class_code = document.frmAssetValueACDetails.cmbMinorClass;
		if(asset_major_class_code.value=="")
		{ 
		     alert("Please select the 'Minor Classification'");
		     asset_major_class_code.focus();
		     return false;
		}

		var depreciation_cate_code = document.frmAssetValueACDetails.cmbDepreCat;
		var apportion_grant_cate_code = document.frmAssetValueACDetails.cmbApportCat;
		if((depreciation_cate_code.value=="0")&&(apportion_grant_cate_code.value=="0"))
		{ 
		     alert("Please select either a 'Depreciation Category' or an 'Apportion Grant Category'");
		     depreciation_cate_code.focus();
		     return false;
		}
		else
		{
			var current_value_after_depre = document.frmAssetValueACDetails.txtAssetAfterDepre;
			if((depreciation_cate_code.value!=0)&&(current_value_after_depre.value==""))
			{ 
			     alert("Please enter the 'Current Value of Asset after Depreciation'");
			     current_value_after_depre.focus();
			     return false;
			}
	
			var current_value_after_apportion = document.frmAssetValueACDetails.txtAssetAfterApport;
			if((apportion_grant_cate_code.value!=0)&&(current_value_after_apportion.value==""))
			{ 
				alert("Please enter the 'Current Value of Asset after Apportionment of grant'");
			     current_value_after_apportion.focus();
			     return false;
			}
		}

		var alias_code = document.frmAssetValueACDetails.txtAlias;
		if(alias_code.value=="")
		{ 
		     alert("Please reselect 'Asset Type' and/or 'Major Classification' to generate 'Alias Code'");
		     alias_code.focus();
		     return false;
		}

		var particulars = document.frmAssetValueACDetails.txtAssetDesc;
		if(particulars.value=="")
		{ 
		     alert("Please enter 'Description of the Asset'");
		     particulars.focus();
		     return false;
		}

		var number_of_assets = document.frmAssetValueACDetails.txtNoOfAssets;
		if(number_of_assets.value=="")
		{ 
		     alert("Please enter the 'Number of Assets'");
		     number_of_assets.focus();
		     return false;
		}

		var office_id_asset_is_available = document.frmAssetValueACDetails.txtOffice_Id;
		if(office_id_asset_is_available.value=="")
		{ 
		     alert("Please fill the 'Location in which the Asset is available'");
		     office_id_asset_is_available.focus();
		     return false;
		}

		var ownership_code = document.frmAssetValueACDetails.cmbApportGrantCat;
		if(ownership_code.value=="")
		{ 
		     alert("Please select the Ownership Code - 'Apportionment of Grant Category Code (Hired/Donated/Grant)'");
		     ownership_code.focus();
		     return false;
		}

		var donating_agency_name = document.frmAssetValueACDetails.txtAgency;
		if((ownership_code.value=="Donated") && (donating_agency_name.value==""))
		{ 
		     alert("Please enter the 'Donating Agency Name'");
		     donating_agency_name.focus();
		     return false;
		}

		var project_code = document.frmAssetValueACDetails.cmbProjectCode;
		if((ownership_code.value=="Grant") && (project_code.value==0))
		{ 
		     alert("Please select a 'Project'");
		     project_code.focus();
		     return false;
		}

		var year_of_purchase = document.frmAssetValueACDetails.txtPurchaseYear;
		if(year_of_purchase.value=="")
		{ 
		     alert("Please enter the 'Year of Purchase'");
		     year_of_purchase.focus();
		     return false;
		}

		var month_of_purchase = document.frmAssetValueACDetails.cmbPurchaseMonth;
		if(month_of_purchase.value=="")
		{ 
			 alert("Please select the 'Month of Purchase'");
		     month_of_purchase.focus();
		     return false;
		}

		var original_cost = document.frmAssetValueACDetails.txtOriginalCost;
		if(original_cost.value=="")
		{ 
		     alert("Please enter the 'Original Cost'");
		     original_cost.focus();
		     return false;
		}

		var under_warrenty = document.frmAssetValueACDetails.radWarranty;
		if(!(under_warrenty[0].checked) && !(under_warrenty[1].checked))
		{
		     alert("Please specify if it is 'Under Warranty'");
		     return false;
		}
		
		if(under_warrenty[0].checked) 
		{
			var warranty_period_from = document.frmAssetValueACDetails.txtWarrantyFrom;
			if(warranty_period_from.value=="")
			{ 
			     alert("Please fill the 'Warranty Period From' date");
			     warranty_period_from.focus();
			     return false;
			}

			var warranty_period_upto = document.frmAssetValueACDetails.txtWarrantyTo;
			if(warranty_period_upto.value=="")
			{ 
			     alert("Please fill the 'Warranty Period To' date");
			     warranty_period_upto.focus();
			     return false;
			}
		}

		var is_under_usable_condition = document.frmAssetValueACDetails.radUsable;
		if(!(is_under_usable_condition[0].checked) && !(is_under_usable_condition[1].checked))
		{
		     alert("Please specify if it is 'Under Usable Condition'");
		     return false;
		}
		
		var is_in_use = document.frmAssetValueACDetails.radInUse;
		if(!(is_in_use[0].checked) && !(is_in_use[1].checked))
		{
		     alert("Please specify if it is 'In Use'");
		     return false;
		}
		
		var remarks = document.frmAssetValueACDetails.txtRemarks;
		if(remarks.value=="")
		{ 
		     remarks.focus();
		     return confirm("Save without 'Remarks'?");
		}

        return true;
}
        
        

//******************************************** CallServer Coding *******************//
function callServer(command)
{
       var accounting_unit_id=document.frmAssetValueACDetails.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmAssetValueACDetails.cmbOffice_code.value;
       var date_transaction=document.frmAssetValueACDetails.txtDate.value;
	   var financial_year = document.frmAssetValueACDetails.cmbFinYear.value;
	   var asset_code = document.frmAssetValueACDetails.txtAssetCode.value;
	   var num_ac_office_id = document.frmAssetValueACDetails.cmbNAOffice.value;
	   var asset_type_code = document.frmAssetValueACDetails.cmbAssetType.value;
	   var asset_major_class_code = document.frmAssetValueACDetails.cmbMajorClass.value;
	   var asset_minor_class_code = document.frmAssetValueACDetails.cmbMinorClass.value;
	   var depreciation_cate_code = document.frmAssetValueACDetails.cmbDepreCat.value;
	   var apportion_grant_cate_code = document.frmAssetValueACDetails.cmbApportCat.value;
	   var alias_code = document.frmAssetValueACDetails.txtAlias.value;
	   var particulars = document.frmAssetValueACDetails.txtAssetDesc.value;
	   var number_of_assets = document.frmAssetValueACDetails.txtNoOfAssets.value;
	   var office_id_asset_is_available = document.frmAssetValueACDetails.txtOffice_Id.value;
	   var ownership_code = document.frmAssetValueACDetails.cmbApportGrantCat.value;
	   var donating_agency_name = document.frmAssetValueACDetails.txtAgency.value;
	   var project_code = document.frmAssetValueACDetails.cmbProjectCode.value;
	   var year_of_purchase = document.frmAssetValueACDetails.txtPurchaseYear.value;
	   var month_of_purchase = document.frmAssetValueACDetails.cmbPurchaseMonth.value;
	   var original_cost = document.frmAssetValueACDetails.txtOriginalCost.value;
	   var under_warrenty;
	   if(document.frmAssetValueACDetails.radWarranty[0].checked)
	   {
		   under_warrenty = 'Y';
	   }
	   else
	   {
		   under_warrenty = 'N';
	   }
	   var warranty_period_from = document.frmAssetValueACDetails.txtWarrantyFrom.value;
	   var warranty_period_upto = document.frmAssetValueACDetails.txtWarrantyTo.value;
	   var current_value_after_depre = document.frmAssetValueACDetails.txtAssetAfterDepre.value;
	   var current_value_after_apportion = document.frmAssetValueACDetails.txtAssetAfterApport.value;
	   
	   var is_under_usable_condition;
	   if(document.frmAssetValueACDetails.radUsable[0].checked == true)
	   {
		   is_under_usable_condition = document.frmAssetValueACDetails.radUsable[0].value;
	   }
	   else
	   {
		   is_under_usable_condition = document.frmAssetValueACDetails.radUsable[1].value;
	   }
	   
	   var is_in_use;
	   if(document.frmAssetValueACDetails.radInUse[0].checked == true)
	   {
		   is_in_use = document.frmAssetValueACDetails.radUsable[0].value;
	   }
	   else
	   {
		   is_in_use = document.frmAssetValueACDetails.radUsable[1].value;
	   }

	   var remarks = document.frmAssetValueACDetails.txtRemarks.value;
	   
       var url="";

       if(command=="Add")
        {              
    	   			var flag=nullCheck() && domainCheckDate();
                    if(flag==true)
                    {
                    url="../../../../../Asset_Value_AC_Details?command=Add&accounting_unit_id="+accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&date_transaction="+date_transaction+"&financial_year="+financial_year+"&asset_code="+asset_code+"&num_ac_office_id="+num_ac_office_id+"&asset_type_code="+asset_type_code+"&asset_major_class_code="+asset_major_class_code+"&asset_minor_class_code="+asset_minor_class_code+"&depreciation_cate_code="+depreciation_cate_code+"&apportion_grant_cate_code="+apportion_grant_cate_code+"&alias_code="+alias_code+"&particulars="+particulars+"&number_of_assets="+number_of_assets+"&office_id_asset_is_available="+office_id_asset_is_available+"&ownership_code="+ownership_code+"&donating_agency_name="+donating_agency_name+"&project_code="+project_code+"&year_of_purchase="+year_of_purchase+"&month_of_purchase="+month_of_purchase+"&original_cost="+original_cost+"&under_warrenty="+under_warrenty+"&warranty_period_from="+warranty_period_from+"&warranty_period_upto="+warranty_period_upto+"&current_value_after_depre="+current_value_after_depre+"&current_value_after_apportion="+current_value_after_apportion+"&is_under_usable_condition="+is_under_usable_condition+"&is_in_use="+is_in_use+"&remarks="+remarks;
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
                    var flag=nullCheck() && domainCheckDate();
                    if(flag==true)
                    {
                    url="../../../../../Asset_Value_AC_Details?command=Update&accounting_unit_id="+accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&date_transaction="+date_transaction+"&financial_year="+financial_year+"&asset_code="+asset_code+"&num_ac_office_id="+num_ac_office_id+"&asset_type_code="+asset_type_code+"&asset_major_class_code="+asset_major_class_code+"&asset_minor_class_code="+asset_minor_class_code+"&depreciation_cate_code="+depreciation_cate_code+"&apportion_grant_cate_code="+apportion_grant_cate_code+"&alias_code="+alias_code+"&particulars="+particulars+"&number_of_assets="+number_of_assets+"&office_id_asset_is_available="+office_id_asset_is_available+"&ownership_code="+ownership_code+"&donating_agency_name="+donating_agency_name+"&project_code="+project_code+"&year_of_purchase="+year_of_purchase+"&month_of_purchase="+month_of_purchase+"&original_cost="+original_cost+"&under_warrenty="+under_warrenty+"&warranty_period_from="+warranty_period_from+"&warranty_period_upto="+warranty_period_upto+"&current_value_after_depre="+current_value_after_depre+"&current_value_after_apportion="+current_value_after_apportion+"&is_under_usable_condition="+is_under_usable_condition+"&is_in_use="+is_in_use+"&remarks="+remarks;
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
        			url="../../../../../Asset_Value_AC_Details?command=Delete&accounting_unit_id="+accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
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
        			url="../../../../../Asset_Value_AC_Details?command=Get&accounting_unit_id="+accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
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
              
              else if(command=="Get")
              {
            	  if(flag=="success")
            	  {
            		  var exists = baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
            		  if(exists == "Yes")
            		  {
            			  document.getElementById('cmbAssetType').value = baseResponse.getElementsByTagName("asset_type_code")[0].firstChild.nodeValue;
            			  listMajorClass();
            			  
            			  document.getElementById('CmdAdd').disabled = true;
            			  document.getElementById('CmdUpdate').disabled = false;
            			  document.getElementById('CmdDelete').disabled = false;
            			  
            			  document.getElementById('cmbAcc_UnitCode').value = baseResponse.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
            			  document.getElementById('cmbOffice_code').value = baseResponse.getElementsByTagName("accounting_unit_office_id")[0].firstChild.nodeValue;
            			  document.getElementById('txtDate').value = baseResponse.getElementsByTagName("date_transaction")[0].firstChild.nodeValue;
            			  document.getElementById('cmbFinYear').value = baseResponse.getElementsByTagName("financial_year")[0].firstChild.nodeValue;
            			  document.getElementById('txtAssetCode').value = baseResponse.getElementsByTagName("asset_code")[0].firstChild.nodeValue;
            			  document.getElementById('cmbNAOffice').value = baseResponse.getElementsByTagName("num_ac_office_id")[0].firstChild.nodeValue;
            			alert(''+baseResponse.getElementsByTagName("asset_major_class_code")[0].firstChild.nodeValue);
            			  document.getElementById('cmbMajorClass').value = baseResponse.getElementsByTagName("asset_major_class_code")[0].firstChild.nodeValue;
            			  //alert(baseResponse.getElementsByTagName("asset_major_class_code")[0].firstChild.nodeValue);
            		
            			 minorCde_load();
            			 alert(''+baseResponse.getElementsByTagName("asset_minor_class_code")[0].firstChild.nodeValue);
            			  document.getElementById('cmbMinorClass').value = baseResponse.getElementsByTagName("asset_minor_class_code")[0].firstChild.nodeValue;
            			  
            			   
            			  document.getElementById('txtAssetAfterDepre').value = baseResponse.getElementsByTagName("current_value_after_depre")[0].firstChild.nodeValue;
            			  document.getElementById('txtAssetAfterApport').value = baseResponse.getElementsByTagName("current_value_after_apportion")[0].firstChild.nodeValue;
            			
            			  ifDepre(document.getElementById('cmbDepreCat').value = baseResponse.getElementsByTagName("depreciation_cate_code")[0].firstChild.nodeValue);
            			  ifApport(document.getElementById('cmbApportCat').value = baseResponse.getElementsByTagName("apportion_grant_cate_code")[0].firstChild.nodeValue);
            			  
            			  document.getElementById('txtAlias').value = baseResponse.getElementsByTagName("alias_code")[0].firstChild.nodeValue;
            			  document.getElementById('txtAssetDesc').value = baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
            			  document.getElementById('txtNoOfAssets').value = baseResponse.getElementsByTagName("number_of_assets")[0].firstChild.nodeValue;
            			  
            			  document.getElementById('txtOffice_Id').value = baseResponse.getElementsByTagName("office_id_asset_is_available")[0].firstChild.nodeValue;
            			  LoadLocationOffice(document.getElementById('txtOffice_Id').value);
            			  
            			  document.getElementById('cmbApportGrantCat').value = baseResponse.getElementsByTagName("ownership_code")[0].firstChild.nodeValue;
            			  display();
            			  
            			  document.getElementById('txtAgency').value = baseResponse.getElementsByTagName("donating_agency_name")[0].firstChild.nodeValue;
            			  document.getElementById('cmbProjectCode').value = baseResponse.getElementsByTagName("project_code")[0].firstChild.nodeValue;

            			  document.getElementById('txtPurchaseYear').value = baseResponse.getElementsByTagName("year_of_purchase")[0].firstChild.nodeValue;
            			  document.getElementById('cmbPurchaseMonth').value = baseResponse.getElementsByTagName("month_of_purchase")[0].firstChild.nodeValue;
            			  document.getElementById('txtOriginalCost').value = baseResponse.getElementsByTagName("original_cost")[0].firstChild.nodeValue;
            			  
            			  var under_warrenty = baseResponse.getElementsByTagName("under_warrenty")[0].firstChild.nodeValue;
            			  if(under_warrenty == "Y")
            			  {
            				  document.getElementsByName('radWarranty')[0].checked = true;
            				  enableWarrantyDates();
                			  document.getElementById('txtWarrantyFrom').value = baseResponse.getElementsByTagName("warranty_period_from")[0].firstChild.nodeValue;
                			  document.getElementById('txtWarrantyTo').value = baseResponse.getElementsByTagName("warranty_period_upto")[0].firstChild.nodeValue;
            			  }
            			  else
            			  {
            				  document.getElementsByName('radWarranty')[1].checked = true;
            				  disableWarrantyDates();
            			  }
            			  
            			  var is_usable = baseResponse.getElementsByTagName("is_under_usable_condition")[0].firstChild.nodeValue;
            			  if(is_usable == "Y")
            			  {
            				  document.getElementsByName('radUsable')[0].checked = true;
            			  }
            			  else
            			  {
            				  document.getElementsByName('radUsable')[1].checked = true;
            			  }
            			  
            			  var is_usable = baseResponse.getElementsByTagName("is_in_use")[0].firstChild.nodeValue;
            			  if(is_usable == "Y")
            			  {
            				  document.getElementsByName('radInUse')[0].checked = true;
            			  }
            			  else
            			  {
            				  document.getElementsByName('radInUse')[1].checked = true;
            			  }

            			  document.getElementById('txtRemarks').value = baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
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

              else if(command=="ListMajorClass")
              {
        		  var cmbMajorClass = document.getElementById('cmbMajorClass');
        		  var ln = cmbMajorClass.length;
        		  for(i=0; i<ln; i++)
        		  {
        			  cmbMajorClass.remove(0);
        		  }
        		  var opt = document.createElement("option");
        		  opt.value = "";
        		  opt.innerHTML = "--Select--";
        		  cmbMajorClass.appendChild(opt);
        		  
        		  var mjrCode = baseResponse.getElementsByTagName('asset_major_class_code');
            	  var len = mjrCode.length;
            	  for(i=0; i<len; i++)
            	  {
            		  mjrCode = baseResponse.getElementsByTagName('asset_major_class_code')[i].firstChild.nodeValue;
            		  var mjrDesc = baseResponse.getElementsByTagName('asset_major_class_desc')[i].firstChild.nodeValue;
            		  var opt = document.createElement("option");
            		  opt.value = mjrCode;
            		  opt.innerHTML = mjrDesc;
            		  
            		  cmbMajorClass.appendChild(opt);
            	  }
            	  fetchAlias();
            	  
              }
              else if(command=="LoadMinor"){
            	  if(flag=="success")
            	  {
            	  var cmbMinorClass = document.getElementById('cmbMinorClass');
        		  var ln = cmbMinorClass.length;
        		  for(i=0; i<ln; i++)
        		  {
        			  cmbMinorClass.remove(0);
        		  }
        		  var mirCode = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_CODE');
            	  var len = mirCode.length;
            	  for(i=0; i<len; i++)
            	  {
            		  mirCode = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_CODE')[i].firstChild.nodeValue;
            		  var mirDesc = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_DESC')[i].firstChild.nodeValue;
            		  var opt = document.createElement("option");
            		  opt.value = mirCode;
            		  opt.innerHTML = mirDesc;
            		  
            		  cmbMinorClass.appendChild(opt);
            	  }
            	  //fetchAlias();
            	  }
              }
              else if(command=="LoadNAOffice")
              {
        		  var NAOffice = document.getElementById('cmbNAOffice');
        		  var ln = NAOffice.length;
        		  for(i=0; i<ln; i++)
        		  {
        			  NAOffice.remove(0);
        		  }
        		  var NAOff = baseResponse.getElementsByTagName('rendering_unit_office_id');
            	  var len = NAOff.length;
            	  for(i=0; i<len; i++)
            	  {
                       		  
            		  NAOffId = baseResponse.getElementsByTagName('rendering_unit_office_id')[i].firstChild.nodeValue;
            		  var NAOffName = baseResponse.getElementsByTagName('office_name')[i].firstChild.nodeValue;
            		  
            		  var opt = document.createElement("option");
            		  opt.value = NAOffId;
            		  opt.innerHTML = NAOffName;
            		  
            		  NAOffice.appendChild(opt);
            	  }
            	  callServer('Get');
              }

              else if(command=="FetchAlias")
              {
        		  document.getElementById('txtAlias').value = baseResponse.getElementsByTagName('alias_code')[0].firstChild.nodeValue;
              }
              else if(command=="LoadLocationOffice")
              {
        		  document.getElementById('txtOfficeName').value = baseResponse.getElementsByTagName('office_name')[0].firstChild.nodeValue;
              }
    	  }
     }
}



function listMajorClass()
{
	   var asset_type_code = document.frmAssetValueACDetails.cmbAssetType.value;
	   var command = "ListMajorClass";
	   var url="";
       if(command == "ListMajorClass")
       {              
			url="../../../../../Asset_Value_AC_Details?command=ListMajorClass&asset_type_code="+asset_type_code;
			var req=getTransport();
			req.open("GET",url,true);
			req.onreadystatechange=function()
			{
			   processResponse(req);
			}
			req.send(null);
       }
}


function fetchAlias()
{
	   var asset_major_class_code = document.frmAssetValueACDetails.cmbMajorClass.value;
	   var command = "FetchAlias";
	   var url="";
	   if(command == "FetchAlias")
	   {              
			url="../../../../../Asset_Value_AC_Details?command=FetchAlias&asset_major_class_code="+asset_major_class_code;
			var req=getTransport();
			req.open("GET",url,true);
			req.onreadystatechange=function()
			{
			   processResponse(req);
			}
			req.send(null);
	   }
}


function display()
{
	var grant = document.getElementById('cmbApportGrantCat').value;
	var agency = document.getElementById('txtAgency');
	var proj = document.getElementById('cmbProjectCode');
	agency.value = "";
	proj.value = 0;
	
	if(grant == "Hired")
	{
		agency.disabled = true;
		proj.disabled = true;
	}
	else if(grant == "Donated")
	{
		agency.disabled = false;
		proj.disabled = true;
	}
	else if(grant == "Grant")
	{
		agency.disabled = true;
		proj.disabled = false;
	}
}





//Function for Icon Office Selection
var winjob;

function jobpopup()
{
  if (winjob && winjob.open && !winjob.closed) 
  {
     winjob.resizeTo(500,500);
     winjob.moveTo(250,250); 
     winjob.focus();
  }
  else
  {
      winjob=null
  }
      
  winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
  winjob.moveTo(250,250);  
  winjob.focus();
  
}

function forChildOption()
{

if (winjob && winjob.open && !winjob.closed) 
       winjob.officeSelection(true,true,true,false);
}
function doParentJob(jobid,deptid)
{
	
	if(deptid=="TWAD")
	{
	  document.frmAssetValueACDetails.txtOffice_Id.value=jobid;
	  LoadLocationOffice(jobid);
	  
	  return true
	}
	else
	{
	      alert('Please select a TWAD Office');
	      if (winjob && winjob.open && !winjob.closed) 
	      {
	         winjob.resizeTo(500,500);
	         winjob.moveTo(250,250); 
	         winjob.focus();
	      }
	      return false
	}
}

window.onunload=function()
{

//if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}

function LoadLocationOffice(jobid)
{
	url="../../../../../Asset_Value_AC_Details?command=LoadLocationOffice&office_id_asset_is_available="+jobid;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	   processResponse(req);
	}
	req.send(null);
}

function LoadNAOffice(unitId)
{
	url="../../../../../Asset_Value_AC_Details?command=LoadNAOffice&accounting_unit_id="+unitId;
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	   processResponse(req);
	}
	req.send(null);
}

function clearAll()
{
	  document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	  document.getElementById('cmbOffice_code').options[0].selected = "selected";
	  clearDetails();
}
function clearDetails()
{
	  document.getElementById('txtAssetCode').value = "";
	  
	  document.getElementById('cmbAssetType').options[0].selected = "selected";
	  listMajorClass();
	  
	  var dep = document.getElementById('cmbDepreCat');
	  dep.disabled = false;
	  dep.options[0].selected = "selected";
	  ifDepre(dep.value);
	  
	  var app = document.getElementById('cmbApportCat');
	  app.disabled = false;
	  app.options[0].selected = "selected";
	  ifApport(app.value);
	  
	  document.getElementById('txtAlias').value = "";
	  document.getElementById('txtAssetDesc').value = "";
	  document.getElementById('txtNoOfAssets').value = "";
	  document.getElementById('txtOffice_Id').value = "";
	  document.getElementById('txtOfficeName').value = "";
	  document.getElementById("cmbMinorClass").Value="";
	
	  document.getElementById('cmbApportGrantCat').options[0].selected = "selected";
	  display();
	  
	  document.getElementById('txtAgency').value = "";
	  document.getElementById('cmbProjectCode').options[0].selected = "selected";
	  document.getElementById('txtPurchaseYear').value = "";
	  document.getElementById('cmbPurchaseMonth').options[0].selected = "selected";
	  document.getElementById('txtOriginalCost').value = "";
	  
	  document.getElementsByName('radWarranty')[0].checked = true;
	  enableWarrantyDates();
	  
	  document.getElementById('txtWarrantyFrom').value = "";
	  document.getElementById('txtWarrantyTo').value = "";
	  
/*
	  document.getElementById('txtAssetAfterDepre').value = "";
	  document.getElementById('txtAssetAfterDepre').disabled = false;
	  
	  document.getElementById('txtAssetAfterApport').value = "";
	  document.getElementById('txtAssetAfterApport').disabled = false;
*/
	  
	  document.getElementsByName('radUsable')[0].checked = true;
	  document.getElementsByName('radInUse')[0].checked = true;
	  document.getElementById('txtRemarks').value = "";

	  document.getElementById('CmdAdd').disabled = false;
	  document.getElementById('CmdUpdate').disabled = true;
	  document.getElementById('CmdDelete').disabled = true;
}


var window_Asset_ValueAC_Details;
function ListAll()
{
	var accounting_unit_id=document.frmAssetValueACDetails.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmAssetValueACDetails.cmbOffice_code.value;
	var financial_year = document.frmAssetValueACDetails.cmbFinYear.value;
	var asset_code = document.frmAssetValueACDetails.txtAssetCode.value;

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
     var url = "Asset_Value_AC_Details_ListAll.jsp?" +
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
function minorCde_load()
{
	var val=document.getElementById("cmbMajorClass").value;
    var url="../../../../../Asset_Value_AC_Details?command=LoadMinor&Major_code="+val;
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	   processResponse(req);
	}
	req.send(null);
}

function doParent(accounting_unit_id,accounting_unit_office_id,financial_year,asset_code)
{
    document.getElementById('cmbAcc_UnitCode').value=accounting_unit_id;
    document.getElementById('cmbOffice_code').value=accounting_unit_office_id;
    document.getElementById('cmbFinYear').value=financial_year;
    document.getElementById('txtAssetCode').value=asset_code;

    callServer('Get');
}

function domainCheckDate()
{
    var txtDtFrm = document.getElementById('txtWarrantyFrom').value;
    var txtDtTo = document.getElementById('txtWarrantyTo').value;
    var flag = true;
    if(txtDtFrm == "")
    {
        alert("Please fill the 'From' field of Warranty Period");
        document.getElementById('txtWarrantyTo').value = "";
    }
    else 
    {
        var redep=txtDtFrm.split('/');
        var rel=txtDtTo.split('/');
        var redepDay=redep[0];
        var redepMon=redep[1];
        var redepYear=redep[2];
        var rday=rel[0];
        var rmon=rel[1];
        var ryear=rel[2];
        if(redepYear>ryear)
        {
        	alert("'To' date of Warranty Period should be greater than or equal to 'From' date");
        	document.getElementById('txtWarrantyTo').value = "";
        	flag = false;
        }
        else if(redepYear==ryear)
        {
            if(redepMon>rmon)
            {
            	alert("'To' date of Warranty Period should be greater than or equal to 'From' date");
        		document.getElementById('txtWarrantyTo').value = "";
        		flag = false;
            }
            else if(redepMon==rmon)
            {
                if(redepDay>rday)
                {
                	alert("'To' date of Warranty Period should be greater than or equal to 'From' date");
        			document.getElementById('txtWarrantyTo').value = "";
        			flag = false;
                }
            }
        }
    }
    return flag;
}