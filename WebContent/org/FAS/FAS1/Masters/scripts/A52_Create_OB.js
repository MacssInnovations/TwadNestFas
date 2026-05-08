
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
/*
function common_LoadOfficeCode()
{
	
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;     
  // alert("unitID_val"+unitID_val);
    if(unitID_val!="")
    {
    	
    	//alert("unit id ");
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Freeze_Qty_A52?command=LoadUnitWise_OfficeCode&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice_oly1(req);
        };
        req.send(null);
    }     
}

function handle_loadOffice_oly1(req)
{
  
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
      
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
         
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
           
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
       
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var uuid=baseresponse.getElementsByTagName("uuid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")"+"("+uuid+")";
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
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
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
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
            
             
     }
    }
}
*/
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

function callServer(command)  {  
 
	var accounting_unit_id=document.frmA52_Create_OB.cmbAcc_UnitCode.value;
var accounting_unit_office_id = document.frmA52_Create_OB.cmbOffice_code.value;
var assetmajor=document.frmA52_Create_OB.cmbmajorasset.value;
var financial_year = document.frmA52_Create_OB.cmbFinancialYear.value; 



var url="";
 if(command=="Go")
 {  
 	
 	//if(checkNull()){
 			url="../../../../../A52_Create_OB?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;
 			var req=getTransport();
             req.open("GET",url,true);        
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
//alert('Enter in response');
	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	   //alert("baseResponse "+baseResponse);
	   var tagCommand=baseResponse.getElementsByTagName("command")[0];
	   
	   var command=tagCommand.firstChild.nodeValue; 
	 // alert("command=="+command);
		  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
		 //   alert("Flag command "+flag+"------------"+command);   

		  if(command=="GoGet"){ 
	     	  getRow(baseResponse);
	     	  
	       }
	       else if(command=="checkFreeze"){
           	checkStatus1(baseResponse);
           }
		 // checkFreeze
	     else if(command=="loadMajor")
	       {
	    	 loadMajo(baseResponse);
     	  
	       }
	     else if(command=="GoInsertOB")
	       {
	    	 GoInsertOB1(baseResponse);
   	  
	       }
		  
		  }
	}
	}
	function GoInsertOB1(baseResponse)
	{
	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    
	    if(flag=="success")
	    {
	    	//alert("succes"); 	
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
	function checkStatus1(baseResponse)
	{
	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    
	    if(flag=="freezeCricle")
	    {
	     // alert("freezeCricle");
	      callServer('GoInsertOB'); 
	    }
	    else  if(flag=="notfreezeCricle")
	    {
	        alert("Cricle not freezed,After Freeze Only you can able to insert A52 OB from Verified A52");
	      
	    }else if(flag=="freezeUnit"){
	    	alert("First Freeze your Unit");
	    	
	    }
	    else
	    {
	        alert("failure");
	    }
	} 

	

	function clearAll()
	{
	document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	common_LoadOfficeCode();
	//document.getElementById('cmbOffice_code').options[0].selected = "selected";
	document.getElementById('cmbFinancialYear').value = "selected";
	document.getElementById('cmbmajorasset').value = 0;
	}
	