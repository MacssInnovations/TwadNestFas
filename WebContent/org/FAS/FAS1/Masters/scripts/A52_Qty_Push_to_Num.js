
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
 
	var accounting_unit_id=document.frmA52_Qty_Push.cmbAcc_UnitCode.value;
var accounting_unit_office_id = document.frmA52_Qty_Push.cmbOffice_code.value;
var assetmajor=document.frmA52_Qty_Push.cmbmajorasset.value;
var financial_year = document.frmA52_Qty_Push.cmbFinancialYear.value;  
var url="";
 if(command=="Go")
 {  
 	
 	//if(checkNull()){
 			url="../../../../../A52_Qty_Push_to_Num?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;
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

 	url="../../../../../A52_Qty_Push_to_Num?command=loadMajor";
 	
		var req=getTransport();
     req.open("GET",url,true);  
    
     req.onreadystatechange=function(){
        processResponse(req);
     };   
     req.send(null);
 	
 }
else if(command=="officeload"){
 	url="../../../../../A52_Qty_Push_to_Num?command=officeload&unit_id="+accounting_unit_id;
		var req=getTransport();
     req.open("GET",url,true);  
     req.onreadystatechange=function(){
        processResponse(req);
     };   
     req.send(null);
 	
 }
else if(command=="checkVerifyA52"){
	if(checkNull()){
 	url="../../../../../A52_Qty_Push_to_Num?command=checkVerifyA52&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year;
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
 	url="../../../../../A52_Qty_Push_to_Num?command=checkStatus&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year;
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
	var accounting_unit_id=document.frmA52_Qty_Push.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmA52_Qty_Push.cmbOffice_code.value;
	var assetmajor=document.frmA52_Qty_Push.cmbmajorasset.value;
	var financial_year = document.frmA52_Qty_Push.cmbFinancialYear.value; 
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
	       else if(command=="checkStatus"){
           	checkStatus1(baseResponse);
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
     	  
	       }
	 /*  else if(command=="officeload")
	     {
	 	  officeload1(baseResponse);
	     }*/

	  
		  }
	}
	}
	function checkStatus1(baseResponse)
	{
	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	   var d=document.getElementById("updateTotally");
	    d.disabled=false;
	    /*var d1=document.getElementById("cmdUpdate");
	    d1.disabled=false;
	    var d3=document.getElementById("cmdDelete");
	    d3.disabled=false;*/
	    if(flag=="freezeCricle")
	    {
	     // alert("freezeCricle");
	      callServer('Go'); 
	    }
	    else  if(flag=="notfreezeCricle")
	    {
	        alert("Cricle not freezed,After Freeze Only you can able to update from  A52 to Num");
	        var d=document.getElementById("updateTotally");
		    d.disabled=true;
	       /* var d=document.getElementById("cmdAdd");
	        d.disabled=true;
	        var d1=document.getElementById("cmdUpdate");
	        d1.disabled=true;
	        var d3=document.getElementById("cmdDelete");
	        d3.disabled=true;*/
		    
	      
	        // ClearAll();
	    }else if(flag=="freezeUnit"){
	    	alert("First Freeze your Unit");
	    	  var d=document.getElementById("updateTotally");
			    d.disabled=true;
	    	/*var tbody = document.getElementById("tblList");
			 
			 var table = document.getElementById("Existing");
			 var t=0;
			 for(t=tbody.rows.length-1;t>=0;t--)
			     {
			        tbody.deleteRow(0);
			     } */
	    }
	    else
	    {
	        alert("failure");
	    }
	} 
	/*function officeload1(baseResponse){
	var ofcecode1 = document.getElementById('cmbOffice_code');
	ofcecode1.length=0;
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
	 var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	  if(flag=="success"){
		  if(exists=="Yes"){

				  for(i=0; i<count; i++)
				  {
					 var ofcecode = baseResponse.getElementsByTagName('officeID')[i].firstChild.nodeValue;
					  var offDesc = baseResponse.getElementsByTagName('officeName')[i].firstChild.nodeValue;
					  var opt = document.createElement("option");
					  opt.value = ofcecode;
					  opt.innerHTML = offDesc+"("+ofcecode+")";  		  
					  ofcecode1.appendChild(opt);
				  }
		 }else{
			  alert("Please select Circle");
		  }
	  } 
	  else
	 {
		  alert("Please select Circle");//you selected units,Not circle,Please select Circle   
	 }

	}*/


	function  getRow(baseResponse)
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
					item[1]=baseResponse.getElementsByTagName("ASSETMINORCLASSCODE1")[k].firstChild.nodeValue;	
					item[2]=baseResponse.getElementsByTagName("aval_qty")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("aval_value")[k].firstChild.nodeValue;
					item[4]=baseResponse.getElementsByTagName("crt_aval_qty")[k].firstChild.nodeValue;	
					item[5]=baseResponse.getElementsByTagName("crt_aval_value")[k].firstChild.nodeValue;	
					item[6]=baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;	
					item[7]=baseResponse.getElementsByTagName("accounting_unit_office_id")[k].firstChild.nodeValue;
					item[8]=baseResponse.getElementsByTagName("ASSET_MAJOR_CLASS_CODE")[k].firstChild.nodeValue;
					item[9]=baseResponse.getElementsByTagName("ASSET_MINOR_CLASS_DESC")[k].firstChild.nodeValue;
					item[10]=baseResponse.getElementsByTagName("offName")[k].firstChild.nodeValue;
					
					
					
	                var mycurrent_row=document.createElement("TR");
	                mycurrent_row.id=seq;                                  

	                var cell0 = document.createElement("TD");
	     			var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="sno";
	     			sno1.id="sno";
	     			sno1.value=lll;
	     			var sno2 = document.createTextNode(lll);
	     			cell0.appendChild(sno2);
	     			cell0.appendChild(sno1);
	     			mycurrent_row.appendChild(cell0);
	     			
	     			
	     			var cell01 = document.createElement("TD");
	     			var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="offName";
	     			sno1.id="offName";
	     			sno1.value=item[10];
	     			var sno2 = document.createTextNode(item[10]);
	     			cell01.appendChild(sno2);
	     			cell01.style.textAlign="left";
	     			cell01.appendChild(sno1);
	     			mycurrent_row.appendChild(cell01);
	     			
	     			var cell2 = document.createElement("TD");
	     			var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="assetcode";
	     			sno1.id="assetcode";
	     			sno1.value=item[0];
	     			var sno2 = document.createTextNode(item[0]);
	     			cell2.appendChild(sno2);
	     			cell2.appendChild(sno1);
	     			mycurrent_row.appendChild(cell2);
	     			
	     			 var cell1 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="PARTICULARS";
		     			sno1.id="PARTICULARS";
		     			sno1.value=item[6];
		     			var sno2 = document.createTextNode(item[6]);
		     			cell1.appendChild(sno2);
		     			cell1.style.textAlign="left";
		     			cell1.appendChild(sno1);
		     			mycurrent_row.appendChild(cell1);
	     			
	     			
	     			    var cell2 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="aval_qty";
		     			sno1.id="aval_qty";
		     			sno1.value=item[2];
		     			var sno2 = document.createTextNode(item[2]);
		     			cell2.appendChild(sno2);
		     			cell2.appendChild(sno1);
		     			mycurrent_row.appendChild(cell2);
		     			
		     			
		     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="crt_aval_qty";
		     			sno1.id="crt_aval_qty";
		     			sno1.value=item[4];
		     			var sno2 = document.createTextNode(item[4]);
		     			cell4.appendChild(sno2);
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
		     			
		     		/*	
	    			var cell3 = document.createElement("TD");
	    			var OPENING_BAL_VALUE1=document.createElement("input");
	    			OPENING_BAL_VALUE1.type="text";
	    			OPENING_BAL_VALUE1.size=7;
	    			OPENING_BAL_VALUE1.name="N_OPENING_BAL_VALUE";
	    			OPENING_BAL_VALUE1.id="N_OPENING_BAL_VALUE";
	    			OPENING_BAL_VALUE1.value=item[14];
	    			OPENING_BAL_VALUE1.setAttribute("onkeypress", "return numbersonly1(event,this)");
	    		    //cell2.style.textAlign="right";
	    			cell3.appendChild(OPENING_BAL_VALUE1);
	    			mycurrent_row.appendChild(cell3);
	    			
	    		
	    			    var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="RECIEPTS_YEAR_QTY";
		     			sno1.id="RECIEPTS_YEAR_QTY";
		     			sno1.value=item[4];
		     			var sno2 = document.createTextNode(item[4]);
		     			cell4.appendChild(sno2);
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
	     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="N_RECIEPTS_YEAR_QTY";
		     			sno1.id="N_RECIEPTS_YEAR_QTY";
		     			sno1.value=item[15];
		     			var sno2 = document.createTextNode(item[15]);
		     			cell4.appendChild(sno2);
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
		     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="RECIEPTS_YR_VALUE";
		     			sno1.id="RECIEPTS_YR_VALUE";
		     			sno1.value=item[5];
		     			var sno2 = document.createTextNode(item[5]);
		     			cell4.appendChild(sno2);
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);
		     			
	    			
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

	    			
	    			
	    			    var cell6 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="ISSUES_YEAR_QTY";
		     			sno1.id="ISSUES_YEAR_QTY";
		     			sno1.value=item[6];
		     			var sno2 = document.createTextNode(item[6]);
		     			cell6.appendChild(sno2);
		     			cell6.appendChild(sno1);
		     			mycurrent_row.appendChild(cell6);

	    			    var cell6 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="N_ISSUES_YEAR_QTY";
		     			sno1.id="N_ISSUES_YEAR_QTY";
		     			sno1.value=item[17];
		     			var sno2 = document.createTextNode(item[17]);
		     			cell6.appendChild(sno2);
		     			cell6.appendChild(sno1);
		     			mycurrent_row.appendChild(cell6);
		     			
		     			
		     			

	    			    var cell6 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="ISSUES_YR_VALUE";
		     			sno1.id="ISSUES_YR_VALUE";
		     			sno1.value=item[7];
		     			var sno2 = document.createTextNode(item[7]);
		     			cell6.appendChild(sno2);
		     			cell6.appendChild(sno1);
		     			mycurrent_row.appendChild(cell6);
		     			
	    			var cell7 = document.createElement("TD");
	    			var ISSUES_YR_VALUE1=document.createElement("input");
	    			ISSUES_YR_VALUE1.type="text";
	    			ISSUES_YR_VALUE1.size=7;
	    			ISSUES_YR_VALUE1.name="N_ISSUES_YR_VALUE";
	    			ISSUES_YR_VALUE1.id="N_ISSUES_YR_VALUE";
	    			ISSUES_YR_VALUE1.value=item[18];	
	    			ISSUES_YR_VALUE1.setAttribute("onkeypress", "return numbersonly1(event,this)");
	    			cell7.appendChild(ISSUES_YR_VALUE1);
	    			mycurrent_row.appendChild(cell7);
	    			*/
	    			
	    			
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
	    		/*	
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
	    			*/
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

	function clearAll()
	{
	document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	document.getElementById('cmbOffice_code').options[0].selected = "selected";
	document.getElementById('cmbFinancialYear').value = "selected";
	document.getElementById('cmbmajorasset').value = 0;
	// document.getElementById('cmbminorasset').value = 0;
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
	