
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



function checkNull_verify()
{
	
	  var tbody=document.getElementById("tblList");
	if(tbody.rows.length==0){
	alert("No values Found");
	return false;
	}
	return true;
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

function callServer(command)  {  
 
	var accounting_unit_id=document.frmA52_Qty_Push1.cmbAcc_UnitCode.value;
var accounting_unit_office_id = document.frmA52_Qty_Push1.cmbOffice_code.value;
var assetmajor=document.frmA52_Qty_Push1.cmbmajorasset.value;
var financial_year = document.frmA52_Qty_Push1.cmbFinancialYear.value; 
var cmbheadac = document.frmA52_Qty_Push1.cmbheadac.value; 
var cmbdepreciat = document.frmA52_Qty_Push1.cmbdepreciat.value; 
var cmbapport = document.frmA52_Qty_Push1.cmbapport.value; 


var url="";
 /*if(command=="Go")
 {  
 	
 	//if(checkNull()){
 			url="../../../../../A52_Push_OB?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;
 			var req=getTransport();
             req.open("GET",url,true);        
             req.onreadystatechange=function()
             {
                processResponse(req);
             };   
             req.send(null);
 		  //  }
 	
 }
 else*/ if(command=="loadMajor"){

 	url="../../../../../A52_Push_OB?command=loadMajor";
 	
		var req=getTransport();
		//alert("loadMajor ");
     req.open("GET",url,true);  
    
     req.onreadystatechange=function(){
        processResponse(req);
     };   
     req.send(null);
 	
 }
 else if(command=="loadHeadDesc"){

	 	url="../../../../../A52_Push_OB?command=loadHeadDesc"+"&assetmajor="+assetmajor+"&financial_year="+financial_year;
	 	
			var req=getTransport();
			//alert("loadHeadDesc ");
	     req.open("GET",url,true);  
	    
	     req.onreadystatechange=function(){
	        processResponse(req);
	     };   
	     req.send(null);
	 	
	 }
 else if(command=="loadDep"){

	 	url="../../../../../A52_Push_OB?command=loadDep"+"&assetmajor="+assetmajor+"&financial_year="+financial_year;
	 	
			var req=getTransport();
			//alert("loadDep ");
	     req.open("GET",url,true);  
	    
	     req.onreadystatechange=function(){
	        processResponse(req);
	     };   
	     req.send(null);
	 	
	 }
 else if(command=="loadApp"){

	 	url="../../../../../A52_Push_OB?command=loadApp"+"&assetmajor="+assetmajor+"&financial_year="+financial_year;
	 	
			var req=getTransport();
			//alert("loadApp ");
	     req.open("GET",url,true);  
	    
	     req.onreadystatechange=function(){
	        processResponse(req);
	     };   
	     req.send(null);
	 	
	 }
else if(command=="officeload"){
 	url="../../../../../A52_Push_OB?command=officeload&unit_id="+accounting_unit_id;
		var req=getTransport();
     req.open("GET",url,true);  
     req.onreadystatechange=function(){
        processResponse(req);
     };   
     req.send(null);
 	
 }

else if(command=="getValue"){  

	if(checkNull()){
 	url="../../../../../A52_Push_OB?command=getValue&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;//+"&cmbheadac="+cmbheadac
 //	+"&cmbdepreciat="+cmbdepreciat+"&cmbapport="+cmbapport;
		var req=getTransport();
     req.open("GET",url,true);  
     req.onreadystatechange=function(){
     	processResponse(req);
     };   
     req.send(null);
 	 }
}
else if(command=="checkStatus"){  

	if(checkNull()){
 	url="../../../../../A52_Push_OB?command=checkStatus&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;//+"&cmbheadac="+cmbheadac
 //	+"&cmbdepreciat="+cmbdepreciat+"&cmbapport="+cmbapport;
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
	var accounting_unit_id=document.frmA52_Qty_Push1.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmA52_Qty_Push1.cmbOffice_code.value;
	var assetmajor=document.frmA52_Qty_Push1.cmbmajorasset.value;
	var financial_year = document.frmA52_Qty_Push1.cmbFinancialYear.value; 
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

		 /* if(command=="GoGet"){ 
	     	  getRow(baseResponse);
	     	  
	       }
	       
	       else*/ if(command=="getValue"){
	    	   getValue(baseResponse);
	           }
	     else if(command=="loadMajor")
	       {
	    	 loadMajo(baseResponse);
     	  
	       }
	     else if(command=="loadHeadDesc")
	       {
	    	 loadHeadd(baseResponse);
   	  
	       }
	     else if(command=="loadDep")
	       {
	    	 loadDepp(baseResponse);
 	  
	       }
		  
	     else if(command=="loadApp")
	       {
	    	 loadAppp(baseResponse);
 	  
	       }
		  
		  
	     else if(command=="checkStatus")
         {
       	  // alert("command  inside checkStatus ");
       	  if(flag=="success")
       	  {
       		 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
       		 alert("exists"+exists);
       		  if(exists=="Yes"){
       			  
       			 var accunitid=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
       			 var a52status=baseResponse.getElementsByTagName("A52_STATUS")[0].firstChild.nodeValue;
       			// alert("a52status "+a52status);
       			 if(a52status=="Y"){
       				alert("You already Freezed A52 Unit,So you cant edit the content"); 
       				//clearAll();
       			 }
       			// return true;
       			 
       		  }else {
       			  
       			 // alert("Not verify");
       			 callServer('getValue');
       			 //return true;
       		  }
       		// return true;
       	  }
       	  else
             {
       		  alert("Failed to check.");
       		 
             }
         }
		  }
	}
	}
	function loadHeadd(baseResponse){
		 var cmbheadac = document.getElementById("cmbheadac");
 		// cmbheadac.length=0;
 		 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 	
     	  if(flag=="success"){
     		  
     		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
       		  if(exists=="Yes"){
       		
     		  var hdCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE');
     		  
         	  var len = hdCode.length;
         	
         	 var headDesc = baseResponse.getElementsByTagName('ACCOUNT_HEAD_CODE')[0].firstChild.nodeValue;
         	 
     	  /*for(i=0; i<len; i++)
     	  {
     		
     		  var headDesc = baseResponse.getElementsByTagName('ACCOUNT_HEAD_CODE')[i].firstChild.nodeValue;
     		  var opt = document.createElement("option");
     		  opt.value = headDesc;
     		  opt.innerHTML = headDesc;
     		  
     		 cmbheadac.appendChild(opt);
     	  }*/
         	 cmbheadac.value=headDesc;
       		 }else{
       			/* var opt1 = document.createElement("option");
	     		  opt1.value = 0;
	     		  opt1.innerHTML = 'No HeadCode';
	     		 cmbheadac.appendChild(opt1);*/
      			  //alert("No HeadCode");
       			cmbheadac.value='No HeadCode';
      		  }
     	  } else
		        {
     		 /*var opt1 = document.createElement("option");
     		  opt1.value = 0;
     		  opt1.innerHTML = 'No HeadCode';
     		 cmbheadac.appendChild(opt1);*/
			        //alert("No HeadCode");
     		 cmbheadac.value='No HeadCode';
			        
			        }
	}
	function loadDepp(baseResponse){
	
    		 var cmbdepreciat1 = document.getElementById("cmbdepreciat");
    	// cmbdepreciat1.length=0;
 		
    	 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     	  if(flag=="success"){
     		  
     		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
       		  if(exists=="Yes"){
       		
     		  var depCode = baseResponse.getElementsByTagName('DEPRECIATION_CATE_CODE');
     		  
         	  var len = depCode.length;
         	  var deprate = baseResponse.getElementsByTagName('DEPRECIATION_RATE')[0].firstChild.nodeValue;
     	 /* for(i=0; i<len; i++)
     	  {
     		
     		  var deprate = baseResponse.getElementsByTagName('DEPRECIATION_RATE')[i].firstChild.nodeValue;
     		  var opt = document.createElement("option");
     		  opt.value = deprate;
     		  opt.innerHTML = deprate;
     		  
     		 cmbdepreciat1.appendChild(opt);
     	  }*/
     	 cmbdepreciat1.value=deprate;
       		 }else{
       			 /*var opt2 = document.createElement("option");
	     		  opt2.value = 0;
	     		  opt2.innerHTML = 'No Depreciation Rate';
	     		 cmbdepreciat1.appendChild(opt2);*/
	     		 cmbdepreciat1.value=0;
      			 // alert("No Depreciation Rate");
	     		 
      		  }
     	  } else
		        {
     		/* var opt3 = document.createElement("option");
     		  opt3.value = 0;
     		  opt3.innerHTML = 'No Depreciation Rate';
     		 cmbdepreciat1.appendChild(opt3);*/
     		 cmbdepreciat1.value=0;
			       // alert("No Depreciation Rate");
			     
			        
			        }
	}
	function loadAppp(baseResponse){
		var cmbapport = document.getElementById("cmbapport");
		//cmbapport.length=0;
		  var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
    	  if(flag=="success"){
    		  
    		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
      		  if(exists=="Yes"){
      		
    		  var appCode = baseResponse.getElementsByTagName('APPORTION_GRANT_CATE_CODE');
    		  
        	  var len = appCode.length;
        	  var apprate = baseResponse.getElementsByTagName('APPORTIONMENT_RATE')[0].firstChild.nodeValue;
    	  /*for(i=0; i<len; i++)
    	  {
    		
    		
    		  var apprate = baseResponse.getElementsByTagName('APPORTIONMENT_RATE')[i].firstChild.nodeValue;
    		  var opt = document.createElement("option");
    		  opt.value = apprate;
    		  opt.innerHTML = apprate;
    		  
    		  cmbapport.appendChild(opt);
    	  }*/
        	  cmbapport.value=apprate;
      		 }else{
      			/* var opt1 = document.createElement("option");
	     		  opt1.value = 0;
	     		  opt1.innerHTML = 'No Apportionment';
	     		 cmbapport.appendChild(opt1);*/
	     		cmbapport.value=0;
     			//  alert("No HeadCode");
     		  }
    	  } else
		        {
    		/* var opt1 = document.createElement("option");
    		  opt1.value = 0;
    		  opt1.innerHTML = 'No Apportionment';
    		  cmbapport.appendChild(opt1);*/
    		  cmbapport.value=0;
			      //  alert("No HeadCode");   
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
	 

	function  getValue(baseResponse)
	{  
	var seq=0;
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	if(flag=="success")
	{          
	 var tbody = document.getElementById("tblList");
	 
	 var table = document.getElementById("Existing");
	 var t=0;
	 for(t=tbody.rows.length-1;t>=0;t--)
	     {
	        tbody.deleteRow(0);
	     }                        
	 var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
	 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;  
	// alert("len "+len);
	if(len==0){
		   if(exists=="No"){
			   //alert(exists);
			   alert("No Records");
		   }
	 }else{
	 	 var lll=1;
	 	 var item = new Array();

	        for(var k=0;k<len;k++)
	           {  
	        	
            
	     			item[0]=baseResponse.getElementsByTagName("asset_code")[k].firstChild.nodeValue;
					item[1]=baseResponse.getElementsByTagName("ASSETMINORCLASSCODE")[k].firstChild.nodeValue;	
					item[2]=baseResponse.getElementsByTagName("OPEN_BAL_QTY")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("OPENING_BAL_VALUE")[k].firstChild.nodeValue;
					item[4]=baseResponse.getElementsByTagName("reciepts_year_qty")[k].firstChild.nodeValue;	
					item[5]=baseResponse.getElementsByTagName("reciepts_yr_value")[k].firstChild.nodeValue;
					item[6]=baseResponse.getElementsByTagName("tot_qty")[k].firstChild.nodeValue;
					item[7]=baseResponse.getElementsByTagName("tot_value")[k].firstChild.nodeValue;
					item[8]=baseResponse.getElementsByTagName("ISSUES_YEAR_QTY")[k].firstChild.nodeValue;	
					item[9]=baseResponse.getElementsByTagName("issues_yr_value")[k].firstChild.nodeValue;
					item[10]=baseResponse.getElementsByTagName("cb_qty")[k].firstChild.nodeValue;
					item[11]=baseResponse.getElementsByTagName("cb_value")[k].firstChild.nodeValue;
					item[12]=baseResponse.getElementsByTagName("dep_prev_year")[k].firstChild.nodeValue;
					item[13]=baseResponse.getElementsByTagName("APP_PRE_YR")[k].firstChild.nodeValue;
					item[14]=baseResponse.getElementsByTagName("depre_rec_ac")[k].firstChild.nodeValue;
					item[15]=baseResponse.getElementsByTagName("app_grant_recieved")[k].firstChild.nodeValue;
					item[16]=baseResponse.getElementsByTagName("depre_allowed_yr")[k].firstChild.nodeValue;
					item[17]=baseResponse.getElementsByTagName("appro_during_yr")[k].firstChild.nodeValue;
					item[18]=baseResponse.getElementsByTagName("tot_dep")[k].firstChild.nodeValue;
					item[19]=baseResponse.getElementsByTagName("tot_app")[k].firstChild.nodeValue;
					item[20]=baseResponse.getElementsByTagName("DEPRE_TR_AC")[k].firstChild.nodeValue;
					item[21]=baseResponse.getElementsByTagName("APP_GRANT_TR")[k].firstChild.nodeValue;
					item[22]=baseResponse.getElementsByTagName("upto_dep")[k].firstChild.nodeValue;
					item[23]=baseResponse.getElementsByTagName("upto_app")[k].firstChild.nodeValue;
					item[24]=baseResponse.getElementsByTagName("net_depre_cost")[k].firstChild.nodeValue;
					item[25]=baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;	
					item[26]=baseResponse.getElementsByTagName("accounting_unit_office_id")[k].firstChild.nodeValue;
					item[27]=baseResponse.getElementsByTagName("ASSET_MAJOR_CLASS_CODE")[k].firstChild.nodeValue;
					item[28]=baseResponse.getElementsByTagName("ASSET_MINOR_CLASS_DESC")[k].firstChild.nodeValue;
					item[29]=baseResponse.getElementsByTagName("asset_major_desc")[k].firstChild.nodeValue;
					item[30]=baseResponse.getElementsByTagName("offName")[k].firstChild.nodeValue;

					
	              //  var mycurrent_row=document.createElement("TR");
	               // mycurrent_row.id=seq;  
	                
	                
	                var mycurrent_row=document.createElement("TR");
	                seq=seq+1;
	                mycurrent_row.id=seq;
	                //alert("row ID"+mycurrent_row.id);
	                var cell=document.createElement("TD");
	                var anc=document.createElement("A");
	                var url="javascript:loadTable('"+mycurrent_row.id+"')";
	                anc.href=url;
	                var txtedit=document.createTextNode("EDIT");
	                txtedit.size="7";
	                anc.appendChild(txtedit);
	                cell.appendChild(anc);
	                mycurrent_row.appendChild(cell);
	                

	                var cell0 = document.createElement("TD");
	     			var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="particular";
	     			sno1.id="particular";
	     			sno1.value=item[25];
	     			var sno2 = document.createTextNode(item[25]);
	     			sno2.size="5";
	     			cell0.appendChild(sno2);
	     			cell0.appendChild(sno1);
	     			mycurrent_row.appendChild(cell0);
	     			
	     			
	     			var cell01 = document.createElement("TD");
	     			var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="ob_qty";
	     			sno1.id="ob_qty";
	     			sno1.value=item[2];
	     			var sno2 = document.createTextNode(item[2]);
	     			sno2.size="5";
	     			cell01.appendChild(sno2);
	     			cell01.style.textAlign="right";
	     			cell01.appendChild(sno1);
	     			mycurrent_row.appendChild(cell01);
	     			
	     			var cell2 = document.createElement("TD");
	     			var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="ob_value";
	     			sno1.id="ob_value";
	     			sno1.value=item[3];
	     			var sno2 = document.createTextNode(item[3]);
	     			cell2.appendChild(sno2);
	     			cell2.style.textAlign="right";
	     			cell2.appendChild(sno1);
	     			mycurrent_row.appendChild(cell2);
	     			
	     			 var cell1 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="rec_dr_qty";
		     			sno1.id="rec_dr_qty";
		     			sno1.value=item[4];
		     			var sno2 = document.createTextNode(item[4]);
		     			cell1.appendChild(sno2);
		     			cell1.style.textAlign="right";
		     			cell1.appendChild(sno1);
		     			mycurrent_row.appendChild(cell1);
	     			
	     			
	     			    var cell2 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="rec_dr_value";
		     			sno1.id="rec_dr_value";
		     			sno1.value=item[5];
		     			var sno2 = document.createTextNode(item[5]);
		     			cell2.appendChild(sno2);
		     			cell2.style.textAlign="right";
		     			cell2.appendChild(sno1);
		     			mycurrent_row.appendChild(cell2);
		     			
		     			
		     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="iss_cr_qty";
		     			sno1.id="iss_cr_qty";
		     			sno1.value=item[8];
		     			var sno2 = document.createTextNode(item[8]);
		     			cell4.appendChild(sno2);
		     			cell4.style.textAlign="right";
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
		     			
		     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="iss_cr_value";
		     			sno1.id="iss_cr_value";
		     			sno1.value=item[9];
		     			var sno2 = document.createTextNode(item[9]);
		     			cell4.appendChild(sno2);
		     			cell4.style.textAlign="right";
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);

	    			    var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="Upto_pre_yr_dep";
		     			sno1.id="Upto_pre_yr_dep";
		     			sno1.value=item[12];
		     			var sno2 = document.createTextNode(item[12]);
		     			cell4.appendChild(sno2);
		     			cell4.style.textAlign="right";
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
	     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="Upto_pre_yr_app";
		     			sno1.id="Upto_pre_yr_app";
		     			sno1.value=item[13];
		     			var sno2 = document.createTextNode(item[13]);
		     			cell4.appendChild(sno2);
		     			cell4.style.textAlign="right";
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
		     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="rec_thr_dep";
		     			sno1.id="rec_thr_dep";
		     			sno1.value=item[14];
		     			var sno2 = document.createTextNode(item[14]);
		     			cell4.appendChild(sno2);
		     			cell4.style.textAlign="right";
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
		     			
	    			/*
	    			var cell5 = document.createElement("TD");
	    			var RECIEPTS_YR_VALUE1=document.createElement("input");
	    			RECIEPTS_YR_VALUE1.type="text";
	    			RECIEPTS_YR_VALUE1.size=8;
	    			RECIEPTS_YR_VALUE1.name="N_RECIEPTS_YR_VALUE";
	    			RECIEPTS_YR_VALUE1.id="N_RECIEPTS_YR_VALUE";
	    			RECIEPTS_YR_VALUE1.value=item[16];	
	    			RECIEPTS_YR_VALUE1.setAttribute("onkeypress", "return numbersonly1(event,this)");
	    			cell5.appendChild(RECIEPTS_YR_VALUE1);
	    			mycurrent_row.appendChild(cell5);
*/
	    			
	    			
	    			    var cell6 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="rec_thr_app";
		     			sno1.id="rec_thr_app";
		     			sno1.value=item[15];
		     			var sno2 = document.createTextNode(item[15]);
		     			cell6.appendChild(sno2);
		     			cell6.style.textAlign="right";
		     			cell6.appendChild(sno1);
		     			mycurrent_row.appendChild(cell6);

	    			    var cell6 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="allow_dur_dep_cr";
		     			sno1.id="allow_dur_dep_cr";
		     			sno1.value=item[16];
		     			var sno2 = document.createTextNode(item[16]);
		     			cell6.appendChild(sno2);
		     			cell6.style.textAlign="right";
		     			cell6.appendChild(sno1);
		     			mycurrent_row.appendChild(cell6);
		     			
		     			
		     			

	    			    var cell6 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="allow_dur_app_cr";
		     			sno1.id="allow_dur_app_cr";
		     			sno1.value=item[17];
		     			var sno2 = document.createTextNode(item[17]);
		     			cell6.appendChild(sno2);
		     			cell6.style.textAlign="right";
		     			cell6.appendChild(sno1);
		     			mycurrent_row.appendChild(cell6);
		     			
		     			  var cell6 = document.createElement("TD");
			     			var sno1=document.createElement("input");
			     			sno1.type="hidden";
			     			sno1.name="transf_thr_dep";
			     			sno1.id="transf_thr_dep";
			     			sno1.value=item[20];
			     			var sno2 = document.createTextNode(item[20]);
			     			cell6.appendChild(sno2);
			     			cell6.style.textAlign="right";
			     			cell6.appendChild(sno1);
			     			mycurrent_row.appendChild(cell6);
			     			
			     			
			     			  var cell6 = document.createElement("TD");
				     			var sno1=document.createElement("input");
				     			sno1.type="hidden";
				     			sno1.name="transf_thr_app";
				     			sno1.id="transf_thr_app";
				     			sno1.value=item[21];
				     			var sno2 = document.createTextNode(item[21]);
				     			cell6.appendChild(sno2);
				     			cell6.style.textAlign="right";
				     			cell6.appendChild(sno1);
				     			mycurrent_row.appendChild(cell6);
		     			
	    		/*	var cell7 = document.createElement("TD");
	    			var ISSUES_YR_VALUE1=document.createElement("input");
	    			ISSUES_YR_VALUE1.type="text";
	    			ISSUES_YR_VALUE1.size=7;
	    			ISSUES_YR_VALUE1.name="N_ISSUES_YR_VALUE";
	    			ISSUES_YR_VALUE1.id="N_ISSUES_YR_VALUE";
	    			ISSUES_YR_VALUE1.value=item[18];	
	    			ISSUES_YR_VALUE1.setAttribute("onkeypress", "return numbersonly1(event,this)");
	    			cell7.appendChild(ISSUES_YR_VALUE1);
	    			mycurrent_row.appendChild(cell7);*/
	    			
				     			var cell9 = document.createElement("TD");
				    			cell9.style.display="none";
				    			var assetcode1=document.createElement("input");
				    			assetcode1.type="hidden";
				    			assetcode1.name="assetcode";
				    			assetcode1.id="assetcode";
				    			assetcode1.value=item[0];
				    			assetcode1.size=1;
				    			cell9.appendChild(assetcode1);
				    			mycurrent_row.appendChild(cell9);
	    			
	    			var cell8 = document.createElement("TD");
	    			cell8.style.display="none";
	    			var minorcode1=document.createElement("input");
	    			minorcode1.type="hidden";
	    			minorcode1.name="minorcode";
	    			minorcode1.id="minorcode";
	    			minorcode1.value=item[1];
	    			minorcode1.size=1;
	    			cell8.appendChild(minorcode1);
	    			mycurrent_row.appendChild(cell8);

	    			
	    			var cell10 = document.createElement("TD");
	    			cell10.style.display="none";
	    			var offcode1=document.createElement("input");
	    			offcode1.type="hidden";
	    			offcode1.name="offcode";
	    			offcode1.id="offcode";
	    			offcode1.value=item[7];
	    			offcode1.size=1;          			
	    			cell10.appendChild(offcode1);
	    			mycurrent_row.appendChild(cell10);
	
	    			var cell10 = document.createElement("TD");
	    			cell10.style.display="none";
	    			var netdepcode1=document.createElement("input");
	    			netdepcode1.type="hidden";
	    			netdepcode1.name="netdepcode";
	    			netdepcode1.id="netdepcode";
	    			netdepcode1.value=item[24];
	    			netdepcode1.size=1;          			
	    			cell10.appendChild(netdepcode1);
	    			mycurrent_row.appendChild(cell10);
	    			
	             tbody.appendChild(mycurrent_row);
	             seq+=1; 
	             lll++;
	           }	 	
	 }
	  }
		  else
		  {
		    alert("Failed to Load Values");
		  }           
	}
	function totalQty(){
		
		//alert("totalqty "+document.getElementById("Total_Qty").value);
		//alert("totalqty document.frmA52_Qty_Push1.OB_Qty.value "+document.frmA52_Qty_Push1.Total_Qty.value);
		
		document.getElementById("Total_Qty").value=parseInt(parseInt(document.frmA52_Qty_Push1.OB_Qty.value)+parseInt(document.frmA52_Qty_Push1.Receipts_Dr_Qty.value)-parseInt(document.frmA52_Qty_Push1.Receipts_Cr_Qty.value));
		//alert("document.getElementById(Total_Qty).value "+document.getElementById("Total_Qty").value);
	}
	function totalValue(){
		//alert("totalvalue  ");
		document.getElementById("Total_Value").value=parseInt(parseInt(document.frmA52_Qty_Push1.OB_Value.value)+parseInt(document.frmA52_Qty_Push1.Receipts_Dr_Value.value)-parseInt(document.frmA52_Qty_Push1.Receipts_Cr_Value.value));
	}
	
	function CBtotqty(){
		
		document.getElementById("CB_Qty").value=parseInt(parseInt(document.frmA52_Qty_Push1.Total_Qty.value)-parseInt(document.frmA52_Qty_Push1.Issues_Cr_Qty.value)+parseInt(document.frmA52_Qty_Push1.Issues_Dr_Qty.value));
	}
	function CBtotvalue(){
		
		document.getElementById("CB_Value").value=parseInt(parseInt(document.frmA52_Qty_Push1.Total_Value.value)-parseInt(document.frmA52_Qty_Push1.Issues_Cr_Value.value)+parseInt(document.frmA52_Qty_Push1.Issues_Dr_Value.value));
	}
	
	//Total  = Upto Previous year Depreciation + Received through proforma account + allowed during the year credit – allowed during the year debit
function totdep(){
		
	//	document.getElementById("tot_Dep").value=parseInt(parseInt(document.frmA52_Qty_Push1.Upto_Pre_Depr.value)+parseInt(document.frmA52_Qty_Push1.Rec_Thr_dep.value)+parseInt(document.frmA52_Qty_Push1.allow_dur_dep_cr.value)-parseInt(document.frmA52_Qty_Push1.allow_dur_dep_dr.value));
	document.getElementById("tot_Dep").value=parseInt(parseInt(document.getElementById("Upto_Pre_Depr").value)+parseInt(document.getElementById("Rec_Thr_dep").value)+parseInt(document.getElementById("allow_dur_dep_cr").value)-parseInt(document.getElementById("allow_dur_dep_dr").value));
	}
function totapp(){
	
	//document.getElementById("tot_App").value=parseInt(parseInt(document.frmA52_Qty_Push1.Upto_Pre_Appr.value)+parseInt(document.frmA52_Qty_Push1.Rec_Thr_app.value)+parseInt(document.frmA52_Qty_Push1.allow_dur_app_cr.value)-parseInt(document.frmA52_Qty_Push1.allow_dur_app_dr.value));
	document.getElementById("tot_App").value=parseInt(parseInt(document.getElementById("Upto_Pre_Appr").value)+parseInt(document.getElementById("Rec_Thr_app").value)+parseInt(document.getElementById("allow_dur_app_cr").value)-parseInt(document.getElementById("allow_dur_app_dr").value));
}
function uptodep(){
	
	//document.getElementById("Upto_date_Dep").value=parseInt(parseInt(document.frmA52_Qty_Push1.tot_Dep.value)-parseInt(document.frmA52_Qty_Push1.trasf_Thr_dep.value));
	document.getElementById("Upto_date_Dep").value=parseInt(parseInt(document.getElementById("tot_Dep").value)-parseInt(document.getElementById("trasf_Thr_dep").value));
}
function uptoapp(){
	
	//document.getElementById("Upto_date_App").value=parseInt(parseInt(document.frmA52_Qty_Push1.tot_App.value)-parseInt(document.frmA52_Qty_Push1.trasf_Thr_app.value));
	document.getElementById("Upto_date_App").value=parseInt(parseInt(document.getElementById("tot_App").value)-parseInt(document.getElementById("trasf_Thr_app").value));
}
function netdep(){
	document.getElementById("dep_Cost").value=parseInt(parseInt(document.getElementById("CB_Value").value)-(parseInt(document.getElementById("Upto_date_Dep").value)+parseInt(document.getElementById("Upto_date_App").value)));
	}


/*function netcost(){
	
	//document.getElementById("dep_Cost").value=parseInt(parseInt(document.frmA52_Qty_Push1.CB_Value.value)-(parseInt(document.frmA52_Qty_Push1.Upto_date_Dep.value)+parseInt(document.frmA52_Qty_Push1.Upto_date_App.value)));
	document.getElementById("dep_Cost").value=parseInt(parseInt(document.getElementById("CB_Value").value)-(parseInt(document.getElementById("Upto_date_Dep").value)+parseInt(document.getElementById("Upto_date_App").value)));
}*/
	//Total – Issues Credit + Issues Debit
//  ///   load Table() ////////

	function loadTable(scod)
	{
		
	        com_id=scod;                                   
	    
	        var r=document.getElementById(scod);
	        var rcells=r.cells;   
	       // alert(" rcells.item(1).firstChild.value   "+rcells.item(1).firstChild.value);
	       // alert(" rcells.item(1).firstChild.nodeValue   "+rcells.item(1).firstChild.nodeValue);
	        
	     //   alert(" rcells.item(1).lastChild.nodeValue   "+rcells.item(1).lastChild.nodeValue);
	        
	        try {document.getElementById("desc").value=rcells.item(1).firstChild.nodeValue;}catch(e){}
	        try {document.getElementById("OB_Qty").value=rcells.item(2).firstChild.nodeValue;}catch(e){}
	        try {document.getElementById("OB_Value").value=rcells.item(3).firstChild.nodeValue;}catch(e){}
	        try {document.getElementById("Receipts_Dr_Qty").value=rcells.item(4).firstChild.nodeValue;}catch(e){}  
	        try {document.getElementById("Receipts_Dr_Value").value=rcells.item(5).firstChild.nodeValue;}catch(e){}	        
	        try {document.frmA52_Qty_Push1.Total_Qty.value=(parseInt(rcells.item(2).firstChild.nodeValue)+parseInt(rcells.item(4).firstChild.nodeValue));}catch(e){}
	        try {document.frmA52_Qty_Push1.Total_Value.value=(parseInt(rcells.item(3).firstChild.nodeValue)+parseInt(rcells.item(5).firstChild.nodeValue));}catch(e){}
	        try {document.getElementById("Issues_Cr_Qty").value=rcells.item(6).firstChild.nodeValue;}catch(e){}  
	        try {document.getElementById("Issues_Cr_Value").value=rcells.item(7).firstChild.nodeValue;}catch(e){}
	        //document.frmA52_Qty_Push1.CB_Value.value
	       // try {document.getElementById("CB_Qty").value=(parseInt(document.getElementById("Total_Qty").value)-parseInt(rcells.item(6).firstChild.nodeValue));}catch(e){}  
	        //try {document.getElementById("CB_Value").value=(parseInt(document.getElementById("Total_Value").value)-parseInt(rcells.item(7).firstChild.nodeValue));}catch(e){}
	        document.frmA52_Qty_Push1.Receipts_Cr_Qty.value=0;
	        	document.frmA52_Qty_Push1.Receipts_Cr_Value.value=0;
	        	document.frmA52_Qty_Push1.Issues_Dr_Qty.value=0;
	        	document.frmA52_Qty_Push1.Issues_Dr_Value.value=0;
	        	document.frmA52_Qty_Push1.allow_dur_dep_dr.value=0;
	        	document.frmA52_Qty_Push1.allow_dur_app_dr.value=0;
	        	
	        	//Upto_date_App
	        
	        try {document.frmA52_Qty_Push1.CB_Qty.value=(parseInt(document.frmA52_Qty_Push1.Total_Qty.value)-parseInt(rcells.item(6).firstChild.nodeValue));}catch(e){}
	        try {document.frmA52_Qty_Push1.CB_Value.value=(parseInt(document.frmA52_Qty_Push1.Total_Value.value)-parseInt(rcells.item(7).firstChild.nodeValue));}catch(e){}
	        
	        try {document.frmA52_Qty_Push1.Upto_Pre_Depr.value=rcells.item(8).firstChild.nodeValue;}catch(e){}
	        try {document.frmA52_Qty_Push1.Upto_Pre_Appr.value=rcells.item(9).firstChild.nodeValue;}catch(e){}
	        
	        try {document.frmA52_Qty_Push1.Rec_Thr_dep.value=rcells.item(10).firstChild.nodeValue;}catch(e){}
	        try {document.frmA52_Qty_Push1.Rec_Thr_app.value=rcells.item(11).firstChild.nodeValue;}catch(e){}	 
	      // alert("rcells.item(12).firstChild.nodeValue "+rcells.item(12).firstChild.nodeValue);
	      //  alert("rcells.item(13).firstChild.nodeValue "+rcells.item(13).firstChild.nodeValue);
	        try {document.getElementById("allow_dur_dep_cr").value=rcells.item(12).firstChild.nodeValue;}catch(e){}
	        try {document.getElementById("allow_dur_app_cr").value=rcells.item(13).firstChild.nodeValue;}catch(e){}
	        
	        try {document.frmA52_Qty_Push1.tot_Dep.value=(parseInt(document.frmA52_Qty_Push1.Upto_Pre_Depr.value)+parseInt(rcells.item(10).firstChild.nodeValue)+parseInt(rcells.item(12).firstChild.nodeValue));}catch(e){} 
	        try {document.frmA52_Qty_Push1.tot_App.value=(parseInt(document.frmA52_Qty_Push1.Upto_Pre_Appr.value)+parseInt(rcells.item(11).firstChild.nodeValue)+parseInt(rcells.item(13).firstChild.nodeValue));}catch(e){}
	        try {document.frmA52_Qty_Push1.trasf_Thr_dep.value=rcells.item(14).firstChild.nodeValue;}catch(e){}
	        try {document.frmA52_Qty_Push1.trasf_Thr_app.value=rcells.item(15).firstChild.nodeValue;}catch(e){}
	        try {document.frmA52_Qty_Push1.Upto_date_Dep.value=(parseInt(document.frmA52_Qty_Push1.tot_Dep.value)-parseInt(document.frmA52_Qty_Push1.trasf_Thr_dep.value));}catch(e){}
	        try {document.frmA52_Qty_Push1.Upto_date_App.value=(parseInt(document.frmA52_Qty_Push1.tot_App.value)-parseInt(document.frmA52_Qty_Push1.trasf_Thr_app.value));}catch(e){}
	        try {document.frmA52_Qty_Push1.dep_Cost.value=(parseInt(document.frmA52_Qty_Push1.CB_Value.value)-(parseInt(document.frmA52_Qty_Push1.Upto_date_Dep.value)) +parseInt(document.frmA52_Qty_Push1.Upto_date_App.value));}catch(e){}
	        try {document.getElementById("asset_code").value=rcells.item(16).firstChild.value;}catch(e){}
	      //19
	        try {document.getElementById("dep_Cost").value=rcells.item(19).firstChild.value;}catch(e){}
	       // alert("rcells.item(16).firstChild.nodeValue "+rcells.item(16).firstChild.nodeValue);
	       // alert("rcells.item(16).firstChild.nodeValue "+rcells.item(16).firstChild.value);
	       // document.getElementById("").value=(parseInt(document.frmA52_Qty_Push1.CB_Value.value)-(parseInt(document.frmA52_Qty_Push1.Upto_date_Dep.value)+parseInt(document.frmA52_Qty_Push1.Upto_date_App.value)));
	       
	  
	}

	function clearAll()
	{
	document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	document.getElementById('cmbOffice_code').options[0].selected = "selected";
	document.getElementById('cmbFinancialYear').value = "selected";
	document.getElementById('cmbmajorasset').value = 0;
	document.getElementById('cmbheadac').value = "";
	document.getElementById('cmbdepreciat').value = "";
	document.getElementById('cmbapport').value = "";
	
	var tbody = document.getElementById("tblList");
	 var d=document.getElementById("updateTotally");
	    d.disabled=false;
	var table = document.getElementById("Existing");
	var t=0;
	for(t=tbody.rows.length-1;t>=0;t--)
	   {
	      tbody.deleteRow(0);
	   } 

	}
	