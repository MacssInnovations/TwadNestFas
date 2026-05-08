var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, response;
var gridrepsonse="";
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
var browserName=navigator.appName;
var brow="";
if (browserName=="Netscape")
{ 	brow="nets";}
else if (browserName=="Microsoft Internet Explorer")
{ 	brow="iex";} 
function clearAll(){	 
	document.getElementById('categoryCode').selectedIndex=0;
	document.getElementById('subCategoryCode').value="";
	document.getElementById('subCategoryDesc').value="";	
	document.getElementById('butSub').disabled=false;
	document.getElementById('butCan').disabled=true;
	document.getElementById('update').disabled=true;
	 
 }
 function Exit(){
    self.close();
 }
 
 function checkNull()
 {
	 //alert("checkNull");
         
         if(document.getElementById("cmbAcc_UnitCode").value=="")
         {
             alert("Select the Account Unit code");
            // document.getElementById("txtAcc_HeadDesc").focus();
             return false;    
         }
         if(document.getElementById("cmbOffice_code").value=="")
         {
             alert("Select the Office Code");
             //document.getElementById("cmbOffice_code").focus();
             return false;
         }
                 
         if(document.getElementById("subLedgerType").value=="select")
         {
             alert("Please Select Subledger Type");
             document.getElementById("subLedgerType").focus();
                       
         }
         
 }
 
 
 
 
 
 function sub_LedgerCode(id)
 {
     var SpecificSL=document.getElementById("sl_code");
    if(id=="Specific")
    {
     SpecificSL.style.display="block";
    }
    else
    {
    SpecificSL.style.display="none";
    }
 }
 
 
function callServer(command) {	 
	 //var categoryDesc=document.getElementById('categoryCode').value;
	 //var subcategoryDesc=document.getElementById('subCategoryDesc').value;
     var url="";
       if(command=="subType"){
    	   	var flag=true;    	   	
            if(flag==true){            	
            	url="../../../../../../subtypecombo.report?command=subType";
            //	alert("URL===>"+url);
                var req=getTransport();
                req.open("POST",url,true);   
                req.onreadystatechange=function(){
                	loadCombo(req);
                 };   
                 req.send(null);
                }
        }else if(command=="subCode"){
                    var flag=true;
                    if(flag==true){
                    	var subCode=document.getElementById('subLedgerType').value;
//                    	alert("subCode==============>"+subCode);
                    	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
                    	var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
                    	
                    	
//                    	var SpecificSL_Code;
//                    	if (document.subLedgertypewise.SpecificSL[0].checked) {
//                    		SpecificSL_Code = document.getElementById('SpecificSL_A').value;
//                    		}
//                    	else if(document.subLedgertypewise.SpecificSL[1].checked) {
//                    		SpecificSL_Code = document.getElementById('SpecificSL_S').value;
//                		}
//                    	alert("Specific_SL=========>"+SpecificSL_Code);
//                    	
//                    	
                    	
                    	
                    	
                    	 	url="../../../../../../subtypecombo.report?command=subCode&cmbSL_type="+subCode+"&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
//                    	alert("subCode&&&"+subCode); 
                    	 	var req=getTransport();
                    	 req.open("POST",url,true);        
                    	 req.onreadystatechange=function(){
                    		 loadSubCombo(req);
                    	 };   
                    	 req.send(null);
                   }
                         
}  
        else if(command=="subCode1"){
            var flag=true;
            if(flag==true){
            	var subCode=document.getElementById('subLedgerType').value;
//            	alert("subCode==============>"+subCode);
            	var cmbOffice_code=document.getElementById('cmbOffice_code').value;
            	var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
            	var txtCB_Year=document.getElementById("txtCB_Year").value;
                var txtCB_Month=document.getElementById("txtCB_Month").value;
                var txtCB_Year_to=document.getElementById("txtCB_Year_to").value;
                var txtCB_Month_to=document.getElementById("txtCB_Month_to").value;
                
            	
//            	var SpecificSL_Code;
//            	if (document.subLedgertypewise.SpecificSL[0].checked) {
//            		SpecificSL_Code = document.getElementById('SpecificSL_A').value;
//            		}
//            	else if(document.subLedgertypewise.SpecificSL[1].checked) {
//            		SpecificSL_Code = document.getElementById('SpecificSL_S').value;
//        		}
//            	alert("Specific_SL=========>"+SpecificSL_Code);
//            	
//            	
            	
            	
            	
            	 	url="../../../../../../subtypecombo.report?command=subCode1&cmbSL_type="+subCode+"&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year+
            	 	"&txtCB_Month="+txtCB_Month+"&txtCB_Year_to="+txtCB_Year_to+"&txtCB_Month_to="+txtCB_Month_to;
//           alert("subCode&&&"+subCode); 
           
             
             
             var req=getTransport();
             req.open("POST",url,true);   
             req.onreadystatechange=function(){
            	 loadSubCombo(req);
              };   
              req.send(null);
             
             
            }
}   

}

function clear_subcode()
{
	document.getElementById('subLedgerType').value="select";
	document.getElementById('subLedgerCode').length=0;
	//document.getElementById('subLedgerCode').value="select";
	document.subLedgertypewise.SpecificSL[0].checked=true;
	document.getElementById("sl_code").style.display="none";
	document.subLedgertypewise.acchead[0].checked=true;
		
	
}
 function processResponse(req){
	 if(req.readyState==4){ 
         if(req.status==200){  
        	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];        	 
      	   		updateResponse(baseResponse);
         }
     }
 }
var checkflag=false;
 function updateResponse(response){
	 	var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	 	if(res=="success"){
	 		if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="added"){
	 			alert("Records Added Successfully, Sub Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
	 			clearAll();
	 			callServer('Get');
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="Notadded"){
	 			alert("Data already added for the particular Sub Category code & Sub Category name");
	 			clearAll();
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="update"){
	 			alert("Records Updated Successfully for Sub Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
	 			clearAll();
	 			callServer('Get');
	 		}	 		 		
	 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="delete")
	 		{
	 		alert("Records Cancelled Successfully,Sub Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
	 		clearAll();
	 		callServer('Get');
	 		}
	 	}
	 	else
	 	{
	 		alert("Process failure");
	 	}
	 }
 function loadCombo(req){
	 if(req.readyState==4){ 
         if(req.status==200){
        	response=req.responseXML.getElementsByTagName("response")[0]; 
        	mainCategoryCombo(response);
         }
     }
 }
 function mainCategoryCombo(response){
	 var len1 = response.getElementsByTagName("SUB_LEDGER_TYPE_CODE").length;	
//	 alert(len1);
		var select=document.getElementById('subLedgerType');
//		alert("Select==>"+select);
//		alert("Status=====>"+response.getElementsByTagName("status")[0].firstChild.nodeValue);
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){		
			var listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="Select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("SUB_LEDGER_TYPE_DESC")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("SUB_LEDGER_TYPE_CODE")[i].firstChild.nodeValue;
			}
		}else{
			select.length=1;
			select.selectedIndex=0;
			//alert("Fail");
		}
 }
 
 function loadSubCombo(req){
	 if(req.readyState==4){ 
         if(req.status==200){
        	 response=req.responseXML.getElementsByTagName("response")[0]; 
        	// alert(req.response);
        	subCategoryCombo(response);
         }
     }
 }
 
 
function subCategoryCombo(response){
	// alert("Welcome to subCategoryCombo function!...........");
	 
        	
			 var len1 = response.getElementsByTagName("cid").length;	
//	alert(len1);
		var select=document.getElementById('subLedgerCode');
//		alert("Select==>"+select);
		if(response.getElementsByTagName("flag")[0].firstChild.nodeValue=="success"){		
			var listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="Select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				var cname=response.getElementsByTagName("cname")[i].firstChild.nodeValue;
				var cid=response.getElementsByTagName("cid")[i].firstChild.nodeValue;
				
				
				listOpt.text=cname+"("+cid+")";
				listOpt.value=cid;
//				listOpt.text=response.getElementsByTagName("cname")[i].firstChild.nodeValue;
//				listOpt.value=response.getElementsByTagName("cid")[i].firstChild.nodeValue;
			}
		}else{
			select.length=1;
			select.selectedIndex=0;
			alert("No Data");
		}
		
	 
 }
 
function nullCheck(){
	if(document.getElementById('categoryCode').value=="select"){
		alert("Please select the Main Category Code");
		return false;
	}
	if(document.getElementById('subCategoryDesc').value==""){
		alert("Please Enter the Sub Category Description");
		return false;
	}	
	return true;
}
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
    if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false 
    }
 }