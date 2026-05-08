/** To handle Errors **/
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
                return false ;
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

function valid_amt()
{
	var Q1 = parseInt(document.frmA52_Register.txtQ1.value);
	var Q2 = parseInt(document.frmA52_Register.txtQ2.value);
	var Q3 = parseInt(document.frmA52_Register.txtQ3.value);
        
	document.frmA52_Register.txtQ_total.value=(Q1+Q2)-Q3;
}
function total_value()
{
	var V1 = parseInt(document.frmA52_Register.txtV1.value);
	var V2 = parseInt(document.frmA52_Register.txtV2.value);
	var V3 = parseInt(document.frmA52_Register.txtV3.value);
        
	document.frmA52_Register.txtV_total.value=(V1+V2)-V3;
}

function total_depr()
{
	var dep_previous = parseInt(document.frmA52_Register.txtdepre_prev_yr.value);
	var dep_recieved = parseInt(document.frmA52_Register.txtdepre_recieved.value);
        var dep_allowed = parseInt(document.frmA52_Register.txtdepre_allowed_yr.value);

	document.frmA52_Register.txttotal_depre.value=dep_previous+dep_recieved+dep_allowed;
}
function total_appr()
{
	var appr_previous = parseInt(document.frmA52_Register.txtappor_grant.value);
	var appr_recieved = parseInt(document.frmA52_Register.txtappor_recieved.value);
        var appr_allowed = parseInt(document.frmA52_Register.txtappor_allowed.value);

	document.frmA52_Register.txttotal_appor.value=appr_previous+appr_recieved+appr_allowed;
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
                return false ;
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
	            return false;
	        }
	        else if (unicode<48 || unicode>57)
	        {
	            return false ;
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
     
		var accounting_unit_id=document.frmA52_Register.cmbAcc_UnitCode;
		if(accounting_unit_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Unit'");
		     accounting_unit_id.focus();
		     return false;
		}

		var accounting_unit_office_id = document.frmA52_Register.cmbOffice_code;
		if(accounting_unit_office_id.value=="")
		{ 
		     alert("Please Select the 'Accounting Office'");
		     accounting_unit_office_id.focus();
		     return false;
		}

		var appor_date=document.frmA52_Register.txt_date;
		if(appor_date.value=="")
		{ 
		     alert("Please fill the 'Date'");
		     appor_date.focus();
		     return false;
		}

		var financial_year = document.frmA52_Register.cmbFinancialYear;
		if(financial_year.value=="")
		{ 
		     alert("Please select the 'Financial Year'");
		     financial_year.focus();
		     return false;
		}
             var asset_code = document.frmA52_Register.cmbasset;
             if(asset_code=="")
             {
             alert("Please select the asset code");
             asset_code.focus();
             return false;
             }
             
	   var txtQ1 = document.frmA52_Register.txtQ1.value;
            if(txtQ1=="")
            {
            alert("please select the Openeing balance for quantity");
            txtQ1.focus();
            return false;
            }
            
	   var txtV1 = document.frmA52_Register.txtV1.value;
            if(txtV1=="")
            {
            alert("please select the Openeing balance for Value");
            txtV1.focus();
            return false;
            }
	   var txtQ2 = document.frmA52_Register.txtQ2.value;
            if(txtQ2=="")
              {
               alert("please select the Reciepts during the year for quantity");
               txtQ2.focus();
               return false;
            }
	   var txtV2 = document.frmA52_Register.txtV2.value;
            if(txtV2=="")
            {
              alert("please select the Reciepts during the year for quantity");
              txtV2.focus();
               return false;
            }
           var txtQ3 = document.frmA52_Register.txtQ3.value;
           if(txtQ3=="")
            {
               alert("please select the Issues during the year for quantity");
               txtQ3.focus();
               return false;
             }
                            
	   var txtV3 = document.frmA52_Register.txtV3.value;
           if(txtV3=="")
            {
              alert("please select the Issues during the year for value");
              txtV3.focus();
               return false;
            }
            
           var txtQ_total = document.frmA52_Register.txtQ_total.value;
            if(txtQ_total=="")
            {
               alert("please select the Total during the year for quantity");
               txtQ_total.focus();
               return false;
             }
             
	   var txtV_total = document.frmA52_Register.txtV_total.value;
           if(txtV_total=="")
            {
               alert("please select the Total during the year for value");
               txtV_total.focus();
               return false;
             }
	   var txtdepre_prev_yr = document.frmA52_Register.txtdepre_prev_yr.value;
           if(txtdepre_prev_yr=="")
            {
               alert("please select the depreciation allowed upto previous year");
               txtdepre_prev_yr.focus();
               return false;
             }
	   var txtdepre_recieved = document.frmA52_Register.txtdepre_recieved.value;
           if(txtdepre_recieved=="")
            {
               alert("please select the Depreciation received through proforma A/c");
               txtdepre_recieved.focus();
               return false;
             }
	   var txtdepre_allowed_yr = document.frmA52_Register.txtdepre_allowed_yr.value;
            if(txtdepre_allowed_yr=="")
            {
               alert("please select Depreciation allowed during the year ");
               txtdepre_allowed_yr.focus();
               return false;
             }
	   var txttotal_depre = document.frmA52_Register.txttotal_depre.value;
            if(txttotal_depre=="")
            {
               alert("please select Total Depreciation ");
               txttotal_depre.focus();
               return false;
             }
	   var dep_transfer = document.frmA52_Register.dep_transfer.value;
            if(dep_transfer=="")
            {
               alert("please select Depreciation transfer through Proforma A/c ");
               dep_transfer.focus();
               return false;
             }
	   var txtdepre_date = document.frmA52_Register.txtdepre_date.value;
           if(txtdepre_date=="")
            {
               alert("please select  Depreciation Upto Date ");
               txtdepre_date.focus();
               return false;
             }
          
	   var txtnet_depre = document.frmA52_Register.txtnet_depre.value;
           if(txtnet_depre=="")
            {
               alert("please select Net Depreciation Cost ");
               txtnet_depre.focus();
               return false;
             }
          
	   var txtappor_grant = document.frmA52_Register.txtappor_grant.value;
           if(txtappor_grant=="")
            {
               alert("please select Apportionment of grant allowed upto previous year  ");
               txtappor_grant.focus();
               return false;
             }
	   var txtappor_recieved = document.frmA52_Register.txtappor_recieved.value;
           if(txtappor_recieved=="")
            {
               alert("please select Apportionment grant received through proforma A/c   ");
               txtappor_recieved.focus();
               return false;
             }
	   var txtappor_allowed = document.frmA52_Register.txtappor_allowed.value;
            if(txtappor_allowed=="")
            {
               alert("please select Apportionment of grant allowed during the year   ");
               txtappor_allowed.focus();
               return false;
             }
	   var txttotal_appor = document.frmA52_Register.txttotal_appor.value;
           if(txttotal_appor=="")
            {
               alert("please select Apportionment of grant transfer through Proforma A/c ");
               txttotal_appor.focus();
               return false;
             }
	   var txtapp_transfer = document.frmA52_Register.txtapp_transfer.value;
           if(txtapp_transfer=="")
            {
               alert("please select Apportionment of grant transfer through Proforma A/c ");
               txtapp_transfer.focus();
               return false;
             }
		
		var remarks = document.frmA52_Register.txtRemarks;
		if(remarks.value=="")
		{ 
		     remarks.focus();
		     return confirm("Save without 'Remarks'?");
		}

        return true;
}
        
        

//******************************************** CallServer Coding *******************//
function callServer(command){  
           var accounting_unit_id=document.frmA52_Register.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmA52_Register.cmbOffice_code.value;
	   var assetmajor=document.frmA52_Register.cmbmajorasset.value;
	   var assetminor = document.frmA52_Register.cmbminorasset.value;
	   var assetcode = document.frmA52_Register.cmbasset.value;
	   
           var appor_date=document.frmA52_Register.txt_date.value;
	   var financial_year = document.frmA52_Register.cmbFinancialYear.value;
	   var asset_code = document.frmA52_Register.cmbasset.value;
	   var txtQ1 = document.frmA52_Register.txtQ1.value;
	   var txtV1 = document.frmA52_Register.txtV1.value;
	   var txtQ2 = document.frmA52_Register.txtQ2.value;
	   var txtV2 = document.frmA52_Register.txtV2.value;
           var txtQ3 = document.frmA52_Register.txtQ3.value;
	   var txtV3 = document.frmA52_Register.txtV3.value;
           var txtQ_total = document.frmA52_Register.txtQ_total.value;
	   var txtV_total = document.frmA52_Register.txtV_total.value;
	   var txtdepre_prev_yr = document.frmA52_Register.txtdepre_prev_yr.value;
	   var txtdepre_recieved = document.frmA52_Register.txtdepre_recieved.value;
	   var txtdepre_allowed_yr = document.frmA52_Register.txtdepre_allowed_yr.value;
	   var txttotal_depre = document.frmA52_Register.txttotal_depre.value;
	   var dep_transfer = document.frmA52_Register.dep_transfer.value;
	   var txtdepre_date = document.frmA52_Register.txtdepre_date.value;
	   var txtnet_depre = document.frmA52_Register.txtnet_depre.value;
	   var txtgrant = document.frmA52_Register.txtappor_grant.value;
          // alert(txtgrant);
	   var txtrecieved = document.frmA52_Register.txtappor_recieved.value;
          // alert(txtrecieved);
	   var txtallowed = document.frmA52_Register.txtappor_allowed.value;
          // alert(txtallowed);
	   var txttotal = document.frmA52_Register.txttotal_appor.value;
         //  alert(txttotal);
	   var txtapp_transfer = document.frmA52_Register.txtapp_transfer.value;
	   //var txtdepre_date = document.frmA52_Register.txtdepre_date.value;
	   var remarks = document.frmA52_Register.txtRemarks.value;
	 /*  var current_value_after_depre = document.frmA52_Register.txtAssetAfterDepre.value;
	   var current_value_after_apportion = document.frmA52_Register.txtAssetAfterApport.value;*/
	   
	   
	   
       var url="";

       if(command=="Add")
        {              
    	   			var flag=nullCheck();
                    if(flag==true)
                    {
                    url="../../../../../A52_Register_OB?command=Add&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&appor_date="+appor_date+"&financial_year="+financial_year+"&asset_code="+asset_code+"&txtQ1="+txtQ1+
                    "&txtV1="+txtV1+"&txtQ2="+txtQ2+"&txtV2="+txtV2+"&txtQ3="+txtQ3+"&txtV3="+txtV3+"&txtQ_total="+txtQ_total+"&txtV_total="+txtV_total+"&txtdepre_prev_yr="+txtdepre_prev_yr+"&txtdepre_recieved="+txtdepre_recieved+
                    "&txtdepre_allowed_yr="+txtdepre_allowed_yr+"&txttotal_depre="+txttotal_depre+"&dep_transfer="+dep_transfer+
                    "&txtdepre_date="+txtdepre_date+"&txtnet_depre="+txtnet_depre+
                    "&txtappor_grant="+txtgrant+"&txtappor_recieved="+txtrecieved+
                    "&txtappor_allowed="+txtallowed+"&txttotal_appor="+txttotal+"&txttransfer="+txtapp_transfer+"&remarks="+remarks;
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
                    var flag=nullCheck() ;
                    if(flag==true)
                    {
                    url="../../../../../A52_Register_OB?command=Update&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&appor_date="+appor_date+"&financial_year="+financial_year+"&asset_code="+asset_code+"&txtQ1="+txtQ1+
                    "&txtV1="+txtV1+"&txtQ2="+txtQ2+"&txtV2="+txtV2+"&txtQ3="+txtQ3+"&txtV3="+txtV3+"&txtQ_total="+txtQ_total+"&txtV_total="+txtV_total+"&txtdepre_prev_yr="+txtdepre_prev_yr+"&txtdepre_recieved="+txtdepre_recieved+
                    "&txtdepre_allowed_yr="+txtdepre_allowed_yr+"&txttotal_depre="+txttotal_depre+"&dep_transfer="+dep_transfer+
                    "&txtdepre_date="+txtdepre_date+"&txtnet_depre="+txtnet_depre+
                    "&txtappor_grant="+txtgrant+"&txtappor_recieved="+txtrecieved+
                    "&txtappor_allowed="+txtallowed+"&txttotal_appor="+txttotal+"&txttransfer="+txtapp_transfer+"&remarks="+remarks;
                    var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    } ;  
                    req.send(null);
                    }
        }
        else if(command=="Delete")
        {  
        			url="../../../../../A52_Register_OB?command=Delete&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
        			var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        }
        else if(command=="Go")
        {  
        			url="../../../../../A52_Register_OB?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor+"&assetminor="+assetminor;
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
        	url="../../../../../A52_Register_OB?command=Get&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code;
            		var req=getTransport();
		            req.open("GET",url,true);  
		            req.onreadystatechange=function(){
		               processResponse(req);
		            };   
		            req.send(null);
        }else if(command=="loadMajor"){
        	url="../../../../../A52_Register_OB?command=loadMajor";
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }else if(command=="loadMinor"){
        	url="../../../../../A52_Register_OB?command=loadMinor&assetmajor="+assetmajor;
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }else if(command=="loadAssetCode"){
        	url="../../../../../A52_Register_OB?command=loadAssetCode&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&assetmajor="+assetmajor+"&assetminor="+assetminor;
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }
}  
function callList()
{
    winemp= window.open("A52_Register_OB_list.jsp","list1","status=1,height=500,width=600,resizable=YES,scrollbars=yes"); 
    winemp.moveTo(300,300);  
    winemp.focus();
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
            		  clearAll();
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
            		  clearAll();
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
            		  clearAll();
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
                          alert("Details of Asset Code already exists ? "+exists);
            		  if(exists == "Yes")
            		  {
            			  /*document.getElementById('cmbAssetType').value = baseResponse.getElementsByTagName("asset_type_code")[0].firstChild.nodeValue;
            			  listMajorClass();*/
            			  
            			  document.getElementById('CmdAdd').disabled = true;
            			  document.getElementById('CmdUpdate').disabled = false;
            			  document.getElementById('CmdDelete').disabled = false;
            			  
            			  document.getElementById('cmbAcc_UnitCode').value = baseResponse.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
            			  document.getElementById('cmbOffice_code').value = baseResponse.getElementsByTagName("accounting_unit_office_id")[0].firstChild.nodeValue;
            			  document.getElementById('txt_date').value = baseResponse.getElementsByTagName("appor_date")[0].firstChild.nodeValue;
            			  document.getElementById('cmbFinancialYear').value = baseResponse.getElementsByTagName("financial_year")[0].firstChild.nodeValue;
            			  document.getElementById('cmbasset').value = baseResponse.getElementsByTagName("asset_code")[0].firstChild.nodeValue;
            			  document.getElementById('txtQ1').value = baseResponse.getElementsByTagName("txtQ1")[0].firstChild.nodeValue;
            			  document.getElementById('txtV1').value = baseResponse.getElementsByTagName("txtV1")[0].firstChild.nodeValue;
            			  document.getElementById('txtQ2').value = baseResponse.getElementsByTagName("txtQ2")[0].firstChild.nodeValue;
            			  document.getElementById('txtV2').value = baseResponse.getElementsByTagName("txtV2")[0].firstChild.nodeValue;
            			  document.getElementById('txtQ3').value = baseResponse.getElementsByTagName("txtQ3")[0].firstChild.nodeValue;
            			  document.getElementById('txtV3').value = baseResponse.getElementsByTagName("txtV3")[0].firstChild.nodeValue;
            			    
            			//  document.getElementById('txtQ_total').value = baseResponse.getElementsByTagName("txtQ_total")[0].firstChild.nodeValue;
            			//  document.getElementById('txtV_total').value = baseResponse.getElementsByTagName("txtV_total")[0].firstChild.nodeValue;
            			
            			  document.getElementById('txtdepre_prev_yr').value = baseResponse.getElementsByTagName("txtdepre_prev_yr")[0].firstChild.nodeValue;
            			  document.getElementById('txtdepre_recieved').value = baseResponse.getElementsByTagName("txtdepre_recieved")[0].firstChild.nodeValue;
            			  
            			  document.getElementById('txtdepre_allowed_yr').value = baseResponse.getElementsByTagName("txtdepre_allowed_yr")[0].firstChild.nodeValue;
            			//  document.getElementById('txttotal_depre').value = baseResponse.getElementsByTagName("txttotal_depre")[0].firstChild.nodeValue;
            			  document.getElementById('dep_transfer').value = baseResponse.getElementsByTagName("dep_transfer")[0].firstChild.nodeValue;
            			  
            			  document.getElementById('txtdepre_date').value = baseResponse.getElementsByTagName("txtdepre_date")[0].firstChild.nodeValue;
            			  document.getElementById('txtnet_depre').value=baseResponse.getElementsByTagName("txtnet_depre")[0].firstChild.nodeValue;
            			  document.getElementById('txtappor_grant').value = baseResponse.getElementsByTagName("txtappor_grant")[0].firstChild.nodeValue;
            			 // display();
            			  
            			  document.getElementById('txtappor_recieved').value = baseResponse.getElementsByTagName("txtappor_recieved")[0].firstChild.nodeValue;
            			  document.getElementById('txtappor_allowed').value = baseResponse.getElementsByTagName("txtappor_allowed2")[0].firstChild.nodeValue;

            			//  document.getElementById('txttotal_appor').value = baseResponse.getElementsByTagName("txttotal_appor")[0].firstChild.nodeValue;
            			  document.getElementById('txtapp_transfer').value = baseResponse.getElementsByTagName("txtapp_transfer")[0].firstChild.nodeValue;
            			  document.getElementById('txt_date').value = baseResponse.getElementsByTagName("txtdepre_date")[0].firstChild.nodeValue;
            			  document.getElementById('txtRemarks').value = baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
            		}
            		  else
            		  {
            			 // clearDetails();
            			 clearAll(); 
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

            else if(command=="loadMajor")
              {
        		  var cmbMajorClass = document.getElementById('cmbmajorasset');
        		  cmbMajorClass.length=0;
        		 // var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        		
            	  if(flag=="success"){
            		  
            		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
              		  if(exists=="Yes"){
              		
            		  var mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE');
            		  
                	  var len = mjrCode.length;
            	  for(i=0; i<len; i++)
            	  {
            		  mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE')[i].firstChild.nodeValue;
            		  var mjrDesc = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_DESC')[i].firstChild.nodeValue;
            		  var opt = document.createElement("option");
            		  opt.value = mjrCode;
            		  opt.innerHTML = mjrDesc;
            		  
            		  cmbMajorClass.appendChild(opt);
            	  }
              		 }else{
             			  alert("No Records");
             		  }
            	  } else
			        {
				        alert("No Major AssetCode in Table");
				     
				        
				        }
            	 // fetchAlias();
            	  
              }
            else if(command=="loadMinor")
            {
      		  var cmbMinorClass = document.getElementById('cmbminorasset');
      		cmbMinorClass.length=0;
      		  //alert("loadMinor");
      		//  var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      		
          	  if(flag=="success"){
          		 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
         		  if(exists=="Yes"){
         		
          		  var minorCode = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_CODE');
          		  
              	  var len = minorCode.length;
          	  for(i=0; i<len; i++)
          	  {
          		  minorCode = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_CODE')[i].firstChild.nodeValue;
          		  var minorDesc = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_DESC')[i].firstChild.nodeValue;
          		  var opt = document.createElement("option");
          		  opt.value = minorCode;
          		  opt.innerHTML = minorDesc;  		  
          		cmbMinorClass.appendChild(opt);
          	  }
         		 }else{
         			  alert("choose other major code...No minor ");
         		  }
          	  } 
          	  else
			        {
          		  alert("No Minor AssetCode in Table");   
				        }
            }
            else if(command=="loadAssetCode")
            {
      		  var cmbasset = document.getElementById('cmbasset');
      		cmbasset.length=0;
      		 // alert("load asset code");
      		//  var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      		  
          	  if(flag=="success"){
          		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
          		  if(exists=="Yes"){
          		
          		  var assCode = baseResponse.getElementsByTagName('ASSET_CODE');
          		  
              	  var len = assCode.length;
          	  for(i=0; i<len; i++)
          	  {
          		  assCode = baseResponse.getElementsByTagName('ASSET_CODE')[i].firstChild.nodeValue;
          		// var minorDesc = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_DESC')[i].firstChild.nodeValue;
          		  var opt = document.createElement("option");
          		  opt.value = assCode;
          		  opt.innerHTML = assCode;  		  
          		cmbasset.appendChild(opt);
          	  }
        	  
          		  }else{
          			  alert("choose other major code And Minor ..no Records");
          		  }
          	  } 
          	  else
			        {
          		  alert("No AssetCode in Table");   
				        }
            }
              /* 
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
              } */
    	  }
     }
}



/*function listMajorClass()
{
	   var asset_type_code = document.frmA52_Register.cmbAssetType.value;
	   var command = "ListMajorClass";
	   var url="";
       if(command == "ListMajorClass")
       {              
			url="../../../../../A52_Register_OB?command=ListMajorClass&asset_type_code="+asset_type_code;
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
	   var asset_major_class_code = document.frmA52_Register.cmbMajorClass.value;
	   var command = "FetchAlias";
	   var url="";
	   if(command == "FetchAlias")
	   {              
			url="../../../../../A52_Register_OB?command=FetchAlias&asset_major_class_code="+asset_major_class_code;
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
*/




//Function for Icon Office Selection
/*var winjob;

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
	  document.frmA52_Register.txtOffice_Id.value=jobid;
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
	url="../../../../../A52_Register_OB?command=LoadLocationOffice&office_id_asset_is_available="+jobid;
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
	url="../../../../../A52_Register_OB?command=LoadNAOffice&accounting_unit_id="+unitId;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	   processResponse(req);
	}
	req.send(null);
}
*/
function clearAll()
{
	 //alert("inside clearAll.........."); 
	 document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	  document.getElementById('cmbOffice_code').options[0].selected = "selected";
	  
	  document.getElementById('txt_date').value = "";
	  document.getElementById('cmbFinancialYear').value = "selected";;
	  document.getElementById('cmbasset').value = "selected";;
	  document.getElementById('txtQ1').value = "";
	  document.getElementById('txtV1').value = "";
	  document.getElementById('txtQ2').value = "";
	  document.getElementById('txtV2').value = "";
	  document.getElementById('txtQ3').value = "";
	  document.getElementById('txtV3').value = "";
	    
	  document.getElementById('txtQ_total').value = "";
	  document.getElementById('txtV_total').value = "";
	
	  document.getElementById('txtdepre_prev_yr').value = "";
	  document.getElementById('txtdepre_recieved').value = "";
	  
	  document.getElementById('txtdepre_allowed_yr').value = "";
	  document.getElementById('txttotal_depre').value = "";
	  document.getElementById('dep_transfer').value = "";
	  
	  document.getElementById('txtdepre_date').value = "";
	  document.getElementById('txtnet_depre').value="";
	  document.getElementById('txtappor_grant').value = "";
	 // display();
	  
	  document.getElementById('txtappor_recieved').value = "";
	  document.getElementById('txtappor_allowed').value = "";

	  document.getElementById('txttotal_appor').value = "";
	  document.getElementById('txtapp_transfer').value = "";
	  document.getElementById('txt_date').value = "";
	  document.getElementById('txtRemarks').value = "";
	  //clearDetails();
}
function clearDetails()
{
/*	  document.getElementById('cmbasset').value = "";
	  
	  document.getElementById('cmbAssetType').options[0].selected = "selected";
	  //listMajorClass();
	  
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
	
	  document.getElementById('cmbApportGrantCat').options[0].selected = "selected";
	  //display();
	  
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

	  
	  document.getElementsByName('radUsable')[0].checked = true;
	  document.getElementsByName('radInUse')[0].checked = true;
	  document.getElementById('txtRemarks').value = "";
*/
	  document.getElementById('CmdAdd').disabled = false;
	  document.getElementById('CmdUpdate').disabled = true;
	  document.getElementById('CmdDelete').disabled = true;
}


var window_Asset_ValueAC_Details;
function ListAll()
{
	var accounting_unit_id=document.frmA52_Register.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmA52_Register.cmbOffice_code.value;
	var financial_year = document.frmA52_Register.cmbFinancialYear.value;
	var asset_code = document.frmA52_Register.cmbasset.value;

    if (window_Asset_ValueAC_Details && window_Asset_ValueAC_Details.open && !window_Asset_ValueAC_Details.closed) 
    {
    	window_Asset_ValueAC_Details.resizeTo(500,500);
    	window_Asset_ValueAC_Details.moveTo(250,250); 
    	window_Asset_ValueAC_Details.focus();
    }
    else
    {
    	window_Asset_ValueAC_Details=null;
    }
     var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var url = "A52_Register_OB_ListAll.jsp?" +
     			"accounting_unit_id="+accounting_unit_id + 
     			"&accounting_unit_office_id="+accounting_unit_office_id +
     			"&financial_year="+financial_year;
     window_Asset_ValueAC_Details= window.open(url,"mywindow1","resizable=YES, scrollbars=yes"); 
     window_Asset_ValueAC_Details.moveTo(250,250);    
}

window.onunload=function()
{
	if (window_Asset_ValueAC_Details && window_Asset_ValueAC_Details.open && !window_Asset_ValueAC_Details.closed) window_Asset_ValueAC_Details.close();
};

function doParent(accounting_unit_id,accounting_unit_office_id,financial_year,asset_code)
{
    document.getElementById('cmbAcc_UnitCode').value=accounting_unit_id;
    document.getElementById('cmbOffice_code').value=accounting_unit_office_id;
    document.getElementById('cmbFinancialYear').value=financial_year;
    document.getElementById('cmbasset').value=asset_code;

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