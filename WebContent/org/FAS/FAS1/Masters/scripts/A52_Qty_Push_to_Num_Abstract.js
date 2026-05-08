
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
 
	var accounting_unit_id=document.frmA52_Qty_Push_Abstract.cmbAcc_UnitCode.value;
//var accounting_unit_office_id = document.frmA52_Qty_Push_Abstract.cmbOffice_code.value;
//var assetmajor=document.frmA52_Qty_Push_Abstract.cmbmajorasset.value;
var financial_year = document.frmA52_Qty_Push_Abstract.cmbFinancialYear.value;  
var url="";
 if(command=="Go")
 {  
 	
 	//if(checkNull()){
 			url="../../../../../A52_Qty_Push_to_Num_Abstract?command=GoGet&unit_id="+accounting_unit_id+"&financial_year="+financial_year;//+"&assetmajor="+assetmajor;//"&office_id="+accounting_unit_office_id+
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

 	url="../../../../../A52_Qty_Push_to_Num_Abstract?command=loadMajor";
 	
		var req=getTransport();
     req.open("GET",url,true);  
     req.onreadystatechange=function(){
        processResponse(req);
     };   
     req.send(null);
 	
 }

else if(command=="checkStatus"){
	if(checkNull()){
 	url="../../../../../A52_Qty_Push_to_Num_Abstract?command=checkStatus&unit_id="+accounting_unit_id+"&financial_year="+financial_year;
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
	var accounting_unit_id=document.frmA52_Qty_Push_Abstract.cmbAcc_UnitCode.value;
	//var accounting_unit_office_id = document.frmA52_Qty_Push_Abstract.cmbOffice_code.value;
	//var assetmajor=document.frmA52_Qty_Push_Abstract.cmbmajorasset.value;
	var financial_year = document.frmA52_Qty_Push_Abstract.cmbFinancialYear.value; 
	if((accounting_unit_id=="")||(accounting_unit_id=="0")){
		alert("Select Accounting unit id");
		return false;
	}
	/*if((accounting_unit_office_id=="")||(accounting_unit_office_id=="0")){
		alert("Select Accounting office id");
		return false;
	}*/
	if(financial_year==""){
		   alert("select Finanical year");
		   return false;
	}
	/*if((assetmajor==0)||(assetmajor=="0")||(assetmajor=="")){
		   alert("select select Asset Major Code");
		   return false;
	}*/
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
	     /*else if(command=="loadMajor")
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
	     	  
	       }*/
	  

	  
		  }
	}
	}
	function checkStatus1(baseResponse)
	{
	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	/*   var d=document.getElementById("updateTotally");
	    d.disabled=false;*/
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
	        //alert("Cricle not freezed,After Freeze Only you can able to update from  A52 to Num");
	        callServer('Go'); 
	        
	        /*var d=document.getElementById("updateTotally");
		    d.disabled=true;*/
	       /* var d=document.getElementById("cmdAdd");
	        d.disabled=true;
	        var d1=document.getElementById("cmdUpdate");
	        d1.disabled=true;
	        var d3=document.getElementById("cmdDelete");
	        d3.disabled=true;*/
	      
	      
	        // ClearAll();
	    }
	    else
	    {
	        alert("failure");
	    }
	} 
	

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

	     			item[0]=baseResponse.getElementsByTagName("asset_major_class_code")[k].firstChild.nodeValue;
					item[1]=baseResponse.getElementsByTagName("accounting_unit_id")[k].firstChild.nodeValue;	
					item[2]=baseResponse.getElementsByTagName("unitname")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("asset_major_class_desc")[k].firstChild.nodeValue;
					item[4]=baseResponse.getElementsByTagName("aval_qty")[k].firstChild.nodeValue;	
					item[5]=baseResponse.getElementsByTagName("aval_value")[k].firstChild.nodeValue;	
					item[6]=baseResponse.getElementsByTagName("aval_crt_qty")[k].firstChild.nodeValue;	
					item[7]=baseResponse.getElementsByTagName("aval_crt_value")[k].firstChild.nodeValue;
					
						
	                var mycurrent_row=document.createElement("TR");
	                mycurrent_row.id=seq;                                  
	                valExists = "Yes";
 
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
	     			sno1.name="asset_major_class_desc";
	     			sno1.id="asset_major_class_desc";
	     			sno1.value=item[3];
	     			var sno2 = document.createTextNode(item[3]);
	     			cell01.appendChild(sno2);
	     			cell01.style.textAlign="left";
	     			cell01.appendChild(sno1);
	     			mycurrent_row.appendChild(cell01);
	     			
	     			var cell2 = document.createElement("TD");
	     			var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="aval_qty";
	     			sno1.id="aval_qty";
	     			sno1.value=item[4];
	     			var sno2 = document.createTextNode(item[4]);
	     			cell2.appendChild(sno2);
	     			cell2.appendChild(sno1);
	     			mycurrent_row.appendChild(cell2);
	     			
	     			 var cell1 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="aval_crt_qty";
		     			sno1.id="aval_crt_qty";
		     			sno1.value=item[6];
		     			var sno2 = document.createTextNode(item[6]);
		     			cell1.appendChild(sno2);
		     			//cell1.style.textAlign="left";
		     			cell1.appendChild(sno1);
		     			mycurrent_row.appendChild(cell1);
	     			
	     			
	     			    var cell2 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="aval_value";
		     			sno1.id="aval_value";
		     			sno1.value=item[5];
		     			var sno2 = document.createTextNode(item[5]);
		     			cell2.appendChild(sno2);
		     			cell2.appendChild(sno1);
		     			mycurrent_row.appendChild(cell2);
		     			
		     			
		     			
		     			var cell4 = document.createElement("TD");
		     			var sno1=document.createElement("input");
		     			sno1.type="hidden";
		     			sno1.name="aval_crt_value";
		     			sno1.id="aval_crt_value";
		     			sno1.value=item[7];
		     			var sno2 = document.createTextNode(item[7]);
		     			cell4.appendChild(sno2);
		     			cell4.appendChild(sno1);
		     			mycurrent_row.appendChild(cell4);

	    			var cell8 = document.createElement("TD");
	    			cell8.style.display="none";
	    			var minorcode1=document.createElement("input");
	    			minorcode1.type="hidden";
	    			minorcode1.name="asset_major_class_code";
	    			minorcode1.id="asset_major_class_code";
	    			minorcode1.value=item[0];
	    			minorcode1.size=1;
	    			cell8.appendChild(minorcode1);
	    			mycurrent_row.appendChild(cell8);
	    	
	    			var cell10 = document.createElement("TD");
	    			cell10.style.display="none";
	    			var offcode1=document.createElement("input");
	    			offcode1.type="hidden";
	    			offcode1.name="accounting_unit_id";
	    			offcode1.id="accounting_unit_id";
	    			offcode1.value=item[1];
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
	document.getElementById('cmbFinancialYear').value = "selected";
	//document.getElementById('cmbmajorasset').value = 0;
	var tbody = document.getElementById("tblList");
	/* var d=document.getElementById("updateTotally");
	    d.disabled=false;*/
	var table = document.getElementById("Existing");
	var t=0;
	for(t=tbody.rows.length-1;t>=0;t--)
	   {
	      tbody.deleteRow(0);
	   } 

	}
	