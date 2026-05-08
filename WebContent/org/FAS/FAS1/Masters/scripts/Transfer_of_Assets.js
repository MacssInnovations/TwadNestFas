
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

function doAdd(command)  {  
 alert("hai");
	var accounting_unit_id=document.frmA52_Create_OB.cmbAcc_UnitCode.value;
var accounting_unit_office_id = document.frmA52_Create_OB.cmbOffice_code.value;
var assetmajor=document.frmA52_Create_OB.cmbmajorasset.value;
var financial_year = document.frmA52_Create_OB.cmbFinancialYear.value; 
var cmb_Vendor = document.frmA52_Create_OB.cmb_Vendor.value; 
var circle_ID = document.frmA52_Create_OB.circle_ID.value; 



var url="";
 if(command=="Add")
 {  
 	
 	//if(checkNull()){
 			url="../../../../../A52_Create_OB?command=AddEdit&cmbAcc_UnitCode="+accounting_unit_id+"&cmbOffice_code="+accounting_unit_office_id+"&cmbFinancialYear="+financial_year+
 			"&cmbmajorasset="+assetmajor+"&circle_ID="+circle_ID+"&cmb_Vendor="+cmb_Vendor;
 			alert(url);
 			var req=getTransport();
             req.open("POST",url,true);        
             req.onreadystatechange=function()
             {
                processResponse(req);
             };   
             req.send(null);
 		  //  }
 	
 }
} 
function checkNull(){
	var accounting_unit_id=document.frmA52_Create_OB.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmA52_Create_OB.cmbOffice_code.value;
	var assetmajor=document.frmA52_Create_OB.cmbmajorasset.value;
	var financial_year = document.frmA52_Create_OB.cmbFinancialYear.value; 
	if((accounting_unit_id=="")||(accounting_unit_id=="0")){
		alert("Select Accounting unit id");
		return false;
	}
	if((accounting_unit_office_id=="")||(accounting_unit_office_id=="0")){
		alert("Select Accounting office id");
		return false;
	}
	if(financial_year==""){
		   alert("select Finanical year");
		   return false;
	}
	if((assetmajor==0)||(assetmajor=="0")||(assetmajor=="")){
		   alert("select select Asset Major Code");
		   return false;
	}
	return true;

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
	      if(command=="loadMajor")
	       {
	    	 loadMajo(baseResponse);
     	  
	       }else if(command=="LoadVendor_circle"){
	    	   LoadVendor_circle(baseResponse);
	       }
	     else if(command=="GoInsertOB")
	       {
	    	 GoInsertOB1(baseResponse);
   	  
	       } else if(command=="LoadUnitWise_circle")
	       {
		    	 GoInsertCircle(baseResponse);
	   	  
		       }else if(command=="loadGrid"){
		    	   GoloadGrid(baseResponse);
		       }
		  
		  }
	}
	}
	function GoloadGrid(baseResponse){
		 var len=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID").length; 
		//alert(len);
		var tbody=document.getElementById("tblist");
		var t=0;
		for(t=tbody.rows.length-1;t>=0;t--)
			{
			tbody.deleteRow(0);
			}
		for(var i=0;i<len;i++)
			{
			var tbody=document.getElementById("tblist");
			 var ASSET_CODE=baseResponse.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue; 
			 var REMARKS=baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
			 var qty=baseResponse.getElementsByTagName("qty")[i].firstChild.nodeValue;
			 var vaalue=baseResponse.getElementsByTagName("vaalue")[i].firstChild.nodeValue;
			 var depre=baseResponse.getElementsByTagName("depre")[i].firstChild.nodeValue;
			 var appro=baseResponse.getElementsByTagName("appro")[i].firstChild.nodeValue;
			 var NET_DEPRE_COST=baseResponse.getElementsByTagName("NET_DEPRE_COST")[i].firstChild.nodeValue;
			  var mycurrent_row=document.createElement("TR");
			  var cell2=document.createElement("TD");
			   var ASSET_CODE1=document.createTextNode(ASSET_CODE);
               cell2.appendChild(ASSET_CODE1);
               mycurrent_row.appendChild(cell2);
               var cell2=document.createElement("TD");
               var REMARKS1=document.createTextNode(REMARKS);
               cell2.appendChild(REMARKS1);
               mycurrent_row.appendChild(cell2);
               var cell2=document.createElement("TD");
               var qty1=document.createTextNode(qty);
               cell2.appendChild(qty1);
               mycurrent_row.appendChild(cell2);
               var cell2=document.createElement("TD");
               var vaalue1=document.createTextNode(vaalue);
               cell2.appendChild(vaalue1);
               mycurrent_row.appendChild(cell2);
               var cell2=document.createElement("TD");
               var depre1=document.createTextNode(depre);
               cell2.appendChild(depre1);
               mycurrent_row.appendChild(cell2);
               var cell2=document.createElement("TD");
               var appro1=document.createTextNode(appro);
               cell2.appendChild(appro1); 
               mycurrent_row.appendChild(cell2);
               var cell2=document.createElement("TD");
               var NET_DEPRE_COST1=document.createTextNode(NET_DEPRE_COST);
               cell2.appendChild(NET_DEPRE_COST1);
               mycurrent_row.appendChild(cell2);
               tbody.appendChild(mycurrent_row);
			}
	}
	
	
	function callServer(command)  {  
		 
		var accounting_unit_id=document.frmA52_Create_OB.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmA52_Create_OB.cmbOffice_code.value;
	var assetmajor=document.frmA52_Create_OB.cmbmajorasset.value;
	var financial_year = document.frmA52_Create_OB.cmbFinancialYear.value; 



	var url="";
	 if(command=="loadGrid")
	 {  
	 	
	 	//if(checkNull()){
	 			url="../../../../../A52_Create_OB?command=loadGrid&cmbAcc_UnitCode="+accounting_unit_id+"&cmbOffice_code="+accounting_unit_office_id+"&cmbFinancialYear="+financial_year+"&cmbmajorasset="+assetmajor;
	 			var req=getTransport();
	             req.open("POST",url,true);        
	             req.onreadystatechange=function()
	             {
	                processResponse(req);
	             };   
	             req.send(null);
	 		  //  }
	 	
	 }else if(command=="GoInsertOB")
	 {  
		 	
		 	//if(checkNull()){
		 			url="../../../../../A52_Create_OB?command=GoInsertOB&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;
		 			var req=getTransport();
		             req.open("GET",url,true);        
		             req.onreadystatechange=function()
		             {
		                processResponse(req);
		             };   
		             req.send(null);
		 		  //  }
		 	
		 }
	 
	 else if(command=="loadMajor"){

	 	url="../../../../../A52_Create_OB?command=loadMajor";
	 	
			var req=getTransport();
			//alert("loadMajor ");
	     req.open("GET",url,true);  
	    
	     req.onreadystatechange=function(){
	        processResponse(req);
	     };   
	     req.send(null);
	 	
	 }
	 
	else if(command=="officeload"){
	 	url="../../../../../A52_Create_OB?command=officeload&unit_id="+accounting_unit_id;
			var req=getTransport();
	     req.open("GET",url,true);  
	     req.onreadystatechange=function(){
	        processResponse(req);
	     };   
	     req.send(null);
	 	
	 }
	else if(command=="checkVerifyA52"){
		if(checkNull()){
	 	url="../../../../../A52_Create_OB?command=checkVerifyA52&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year;
			var req=getTransport();
	     req.open("GET",url,true);  
	     req.onreadystatechange=function(){
	     	processResponse(req);
	     };   
	     req.send(null);
	 	 }
	}else if(command=="checkFreeze"){
		if(checkNull()){
		 	url="../../../../../A52_Create_OB?command=checkFreeze&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year;
				var req=getTransport();
		     req.open("GET",url,true);  
		     req.onreadystatechange=function(){
		     	processResponse(req);
		     };   
		     req.send(null);
		 	 }
		}
	 
	} 
	function GoInsertOB1(baseResponse)
	{
	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    
	    if(flag=="success")
	    {
	    	var exists1=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
	    	if(exists1=="Yes"){
	    		
	    		var flagValue1=baseResponse.getElementsByTagName("flagValue")[0].firstChild.nodeValue;
	    		if(flagValue1=="AlreadyExist"){
		     		alert("Already in Moved");
		     	}else{
		     		var insertRes1=baseResponse.getElementsByTagName("insertRes")[0].firstChild.nodeValue;
			     	if(insertRes1=="success"){
			     		alert("Moved to OB table");
			     	}else if(insertRes1=="notinsert"){
			     		alert("Not moved");
			     	}		
		     	}
	    					     	
	    	}
	    	else{
	    		alert("No value to Insert");
	    	}
	    	
	    }
	    else
	    {
	        alert("failure");
	    }
	    
	} 
	function loadMajo(baseResponse){
		
		  var cmbMajorClass = document.getElementById("cmbmajorasset");
 		  cmbMajorClass.length=0;
 		  var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 	
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
	}
	


	function load_CircleData()
	{
	        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_circle";
	        var req=getTransport();
	        req.open("GET",url,true);
	        req.onreadystatechange=function()
	        {
	        	processResponse(req);
	        }
	        req.send(null);
	        
	}
function GoInsertCircle(baseResponse){
	   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(flag=="success"){
		var circle_id=document.getElementsByTagName("circle_ID");
		circle_id.length=0;
		  var UniCode = baseResponse.getElementsByTagName('offid_name');
   	  var len = UniCode.length;
	  for(i=0; i<len; i++)
	  {
		  UniCode = baseResponse.getElementsByTagName('offid1')[i].firstChild.nodeValue;
		  var UniDesc = baseResponse.getElementsByTagName('offid_name')[i].firstChild.nodeValue;
		  var circle_id = document.getElementById("circle_ID");
		  var opt = document.createElement("option");
		  opt.value = UniCode;
		  opt.innerHTML = UniDesc;
		  circle_id.appendChild(opt);
	  }
 		 
	}
}
	function LoadVendor_circle(baseResponse){

		   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		   var Vendorid = document.getElementById("cmb_Vendor");
		   Vendorid.innerHTML="";
		if(flag=="success"){
			  var UniCode = baseResponse.getElementsByTagName('offid_name');
	   	  var len = UniCode.length;
		  for(var i=0; i<len; i++)
		  {
			  var Vendor_id = document.getElementById("cmb_Vendor");
			  UniCode = baseResponse.getElementsByTagName('offid1')[i].firstChild.nodeValue;
			  var UniDesc = baseResponse.getElementsByTagName('offid_name')[i].firstChild.nodeValue;
			  var opt = document.createElement("option");
			  opt.value = UniCode;
			  opt.innerHTML = UniDesc.trim();
			  Vendor_id.appendChild(opt);
		  }
	 		 
		}else{
			alert("No Data Found .. ");
		}

	}
function Vendor_unit(val)
{
if(val=="" || val == "null")val=42;
    var url="../../../../../Receipt_SL.view?Command=LoadVendor_circle&unit="+val;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true);
    req.onreadystatechange=function()
    {
    	processResponse(req);
    };
    req.send(null);
	
	}

	function clearAll()
	{
	document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	common_LoadOfficeCode();
	document.getElementById('cmbFinancialYear').value = "selected";
	document.getElementById('cmbmajorasset').value = 0;
	}
	