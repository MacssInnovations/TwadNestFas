alert ("inside loading partial save");



function getReq() {
    var req = false;
    try {
         req = new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch(Ex) {
         try {
             req = new ActiveXObject("Microsoft.XMLHTTP");
         }
         catch(ex1) {
             req = false;
         }
    }
    if(!req && typeof XMLHttpRequest != 'undefined') {
            req = new XMLHttpRequest();
    }
    return req;
}


function loadPartialSave(ppoNo){
	alert("ppoNo"+ppoNo);
	
	var url='LoadPartialSave.html?ppoNo='+ppoNo;
	alert(url);
	var HttpPartialRequest = getReq();
	HttpPartialRequest.open("GET", url, true);
	HttpPartialRequest.onreadystatechange=function()
     {    
    	 getList(HttpPartialRequest);
     };  
     HttpPartialRequest.send(null);
	
}


function getList(HttpPartialRequest) {
	
if(HttpPartialRequest.readyState == 4){
		if(HttpPartialRequest.status == 200){
        	
				var root = HttpPartialRequest.responseXML.getElementsByTagName('response')[0];		   
		 
			 /*  var flag=root1.getElementsByTagName('flag')[0].firstChild.nodeValue;; 		    
			   var root=flag.responseXML.getElementsByTagName('record')[0].firstChild.nodeValue;
			   */
		        
		 
		    /*
		     *   var flag=root1.getElementsByTagName('flag')[0].firstChild.nodeValue; 		    
		    var root=flag.getElementsByTagName('record')[0].firstChild.nodeValue;    
		 
		 var len=record.length();
		 
		    if(len==0)
		    	{
		    	alert('No record found');
		    	}
		    */
			
				
				 
		    document.getElementById("employeeId").value=root.getElementsByTagName('empNo')[0].firstChild.nodeValue;
		    document.getElementById("pensionerInitial").value=root.getElementsByTagName('empInitial')[0].firstChild.nodeValue;
		    document.getElementById("pensionerName").value=root.getElementsByTagName('empName')[0].firstChild.nodeValue;		    
		   
		    var sex=root.getElementsByTagName('gender')[0].firstChild.nodeValue;			  
		    if(sex=='M')
		    	{
		    	 document.forms[0].sex[0].checked='checked';
		    	}
		    else
		    	{
		    	 document.forms[0].sex[1].checked='checked';
		    	}
		    
		    document.getElementById("titleId").value=root.getElementsByTagName('titleId')[0].firstChild.nodeValue;		    
		    document.getElementById("desigServGrp").value=root.getElementsByTagName('serviceGrpID')[0].firstChild.nodeValue;		   
		    document.getElementById("designationId").value=root.getElementsByTagName('designId')[0].firstChild.nodeValue;
		    
		    var dob1=root.getElementsByTagName('dob')[0].firstChild.nodeValue;			    
		    if(dob1=="null")
		    	{
		    	document.getElementById("dateOfBirth").value="";
		    	}
		    else
		    	{
			    var Yr=dob1.split('-')[0];
				var mon=dob1.split('-')[1];
				var dat=dob1.split('-')[2];
				var dates=dat+'/'+mon+'/'+Yr;		    
			    document.getElementById("dateOfBirth").value=dates;
			    }
		    
		   
		    var dor=root.getElementsByTagName('dor')[0].firstChild.nodeValue;		    
		    if(dor=="null")
	    	{
		    	document.getElementById("dateOfRetirement").value="";
	    	}
		    else
	    	{
			    		    
			    var Yr1=dor.split('-')[0];
				var mon1=dor.split('-')[1];
				var dat1=dor.split('-')[2];
				var dates1=dat1+'/'+mon1+'/'+Yr1;		    
			    document.getElementById("dateOfRetirement").value=dates1;
	    	}
		    
		    
		    document.getElementById("panNumber").value=root.getElementsByTagName('panNo')[0].firstChild.nodeValue;
		    document.getElementById("idMark1").value=root.getElementsByTagName('idMark1')[0].firstChild.nodeValue;
		    document.getElementById("idMark2").value=root.getElementsByTagName('idMark2')[0].firstChild.nodeValue;
		    document.getElementById("lastPayDrawnAmt").value=root.getElementsByTagName('lastPayDrawn')[0].firstChild.nodeValue;
		    document.getElementById("avgEmoulmentAmt").value=root.getElementsByTagName('avgEmoulment')[0].firstChild.nodeValue;	    
		    document.getElementById("lastWorkingOfficeLevel").value=root.getElementsByTagName('lastWorkingOfficeLevel')[0].firstChild.nodeValue;			    
		    document.getElementById("lastWorkingOfficeId").value=root.getElementsByTagName('lastWorkingOfficeId')[0].firstChild.nodeValue;		    	   
		    document.getElementById("currAccountOfficeId").value=root.getElementsByTagName('currAccOfficeId')[0].firstChild.nodeValue;
		    
		    var cutdate=root.getElementsByTagName('cutOffEntryDate')[0].firstChild.nodeValue;
		    if(cutdate=="null")
	    	{
		    	document.getElementById("cutOfEntryDate").value="";
	    	}
		    else
	    	{
		    var Yr2=cutdate.split('-')[0];
			var mon2=cutdate.split('-')[1];
			var dat2=cutdate.split('-')[2];
			var dates2=dat2+'/'+mon2+'/'+Yr2;			    
		    document.getElementById("cutOfEntryDate").value=dates2;		    
	    	}
		 
		
		    /*
		     * Tab 1 Pension Details 	
		    */
		   
		    document.getElementById("classPensionId").value=root.getElementsByTagName('classPensionId')[0].firstChild.nodeValue;
		    document.getElementById("originalPensionAmt").value=root.getElementsByTagName('originalPensionAmt')[0].firstChild.nodeValue;
		    document.getElementById("reducedPensionAmt").value=root.getElementsByTagName('reducedPension')[0].firstChild.nodeValue;
		    document.getElementById("dearnessPay").value=root.getElementsByTagName('dearnessPay')[0].firstChild.nodeValue;
		    document.getElementById("provisionalPensionAmt").value=root.getElementsByTagName('provisionalPension')[0].firstChild.nodeValue;
		    document.getElementById("dcrgAmt").value=root.getElementsByTagName('dcrgAmt')[0].firstChild.nodeValue;		    
		    document.getElementById("dcrgPertReceived").value=root.getElementsByTagName('dcrgPertReceived')[0].firstChild.nodeValue;		    
		    
		    var commOpt=root.getElementsByTagName('commOpted')[0].firstChild.nodeValue;    			   
		    if(commOpt=='Y')
		    	{
		    	 document.forms[0].commOpted[0].checked='checked';
		    	}
		    else
		    	{
		    	 document.forms[0].commOpted[1].checked='checked';
		    	}
		    
		    
		    var commReceived=root.getElementsByTagName('commReceived')[0].firstChild.nodeValue;    			   
		    if(commReceived=='Y')
		    	{
		    	 document.forms[0].commReceived[0].checked='checked';
		    	}
		    else
		    	{
		    	 document.forms[0].commReceived[1].checked='checked';
		    	}
		    
		   
		    var commfactorOnethird=root.getElementsByTagName('commfactorOnethird')[0].firstChild.nodeValue;    
			   
		    if(commfactorOnethird=='1/3')
		    	{
		    	 document.forms[0].commfactorOnethird[0].checked='checked';
		    	}
		    else
		    	{
		    	 document.forms[0].commfactorOnethird[1].checked='checked';
		    	}
		    
		    
		    document.getElementById("commAmt").value=root.getElementsByTagName('commAmt')[0].firstChild.nodeValue;
		  
		   
		    var commPayDate=root.getElementsByTagName('commPayDate')[0].firstChild.nodeValue;
		    if(commPayDate=="null")
	    	{
		    	document.getElementById("commPayDate").value="";
	    	}
		    else
	    	{
		    var Yr12=commPayDate.split('-')[0];
			var mon12=commPayDate.split('-')[1];
			var dat12=commPayDate.split('-')[2];
			var dates12=dat12+'/'+mon12+'/'+Yr12;			    
		    document.getElementById("commPayDate").value=dates12;		    
	    	}
		 
		    
		    
		    
		    
		    
		    var pshfSubscribed=root.getElementsByTagName('pshfSubscribed')[0].firstChild.nodeValue;    
			   
		    if(pshfSubscribed=='Y')
		    	{
		    	 document.forms[0].pshfSubscribed[0].checked='checked';
		    	}
		    else
		    	{
		    	 document.forms[0].pshfSubscribed[1].checked='checked';
		    	}
		    
		    
		    
		   
		    var familyPensionTillDate=root.getElementsByTagName('familyPensionTillDate')[0].firstChild.nodeValue;
		    if(familyPensionTillDate=="null")
	    	{
		    	document.getElementById("familyPensionTillDate").value="";
	    	}
		    else
	    	{
		    var Yr121=familyPensionTillDate.split('-')[0];
			var mon121=familyPensionTillDate.split('-')[1];
			var dat121=familyPensionTillDate.split('-')[2];
			var dates121=dat121+'/'+mon121+'/'+Yr121;			    
		    document.getElementById("familyPensionTillDate").value=dates121;		    
	    	}
		    
		    
		    
		    document.getElementById("familyPensionTillDateAmt").value=root.getElementsByTagName('familyPensionTillDateAmt')[0].firstChild.nodeValue;
		    document.getElementById("familyPensionAtferDateAmt").value=root.getElementsByTagName('familyPensionAtferDateAmt')[0].firstChild.nodeValue;
		   
		    var payCommissionRevisonFlag=root.getElementsByTagName('payCommissionRevisonFlag')[0].firstChild.nodeValue;    			   
		    if(payCommissionRevisonFlag=='Y')
		    	{
		    	 document.forms[0].payCommissionRevisonFlag[0].checked='checked';
		    	}
		    else
		    	{
		    	 document.forms[0].payCommissionRevisonFlag[1].checked='checked';
		    	}
		    
		    
		    
		    		  
		    /*
		     * Tab 2 Service Details 	
		    */
		   
		    document.getElementById("grossServiceYrs").value=root.getElementsByTagName('grossServiceYrs')[0].firstChild.nodeValue;
		    document.getElementById("grossServiceMonth").value=root.getElementsByTagName('grossServiceMonth')[0].firstChild.nodeValue;
		    document.getElementById("grossServiceDays").value=root.getElementsByTagName('grossServiceDays')[0].firstChild.nodeValue;
		    document.getElementById("totServiceYrs").value=root.getElementsByTagName('totServiceYrs')[0].firstChild.nodeValue;
		    document.getElementById("totServiceMonths").value=root.getElementsByTagName('totServiceMonths')[0].firstChild.nodeValue;
		    document.getElementById("totServiceDays").value=root.getElementsByTagName('totServiceDays')[0].firstChild.nodeValue;
		    document.getElementById("quaServiceYrs").value=root.getElementsByTagName('quaServiceYrs')[0].firstChild.nodeValue;
		    document.getElementById("quaServiceMonths").value=root.getElementsByTagName('quaServiceMonths')[0].firstChild.nodeValue;
		    document.getElementById("quaServiceDays").value=root.getElementsByTagName('quaServiceDays')[0].firstChild.nodeValue;
		    document.getElementById("nonquaServiceYrs").value=root.getElementsByTagName('nonquaServiceYrs')[0].firstChild.nodeValue;
		    document.getElementById("nonquaServiceMonths").value=root.getElementsByTagName('nonquaServiceMonths')[0].firstChild.nodeValue;
		    document.getElementById("nonquaServiceDays").value=root.getElementsByTagName('nonquaServiceDays')[0].firstChild.nodeValue;
		    document.getElementById("weightageServiceYrs").value=root.getElementsByTagName('weightageServiceYrs')[0].firstChild.nodeValue;
		    document.getElementById("weightageServiceMonths").value=root.getElementsByTagName('weightageServiceMonths')[0].firstChild.nodeValue;
		    document.getElementById("weightageServiceDays").value=root.getElementsByTagName('weightageServiceDays')[0].firstChild.nodeValue;
		    document.getElementById("netquaServiceYrs").value=root.getElementsByTagName('netquaServiceYrs')[0].firstChild.nodeValue;
		    document.getElementById("netquaServiceMonths").value=root.getElementsByTagName('netquaServiceMonths')[0].firstChild.nodeValue;
		    document.getElementById("netquaServiceDays").value=root.getElementsByTagName('netquaServiceDays')[0].firstChild.nodeValue;
		    
		    /*
		     * Tab3 Address Details 	
		    */
		    
		    document.getElementById("address").value=root.getElementsByTagName('address')[0].firstChild.nodeValue;
		    document.getElementById("district").value=root.getElementsByTagName('district')[0].firstChild.nodeValue;
		    document.getElementById("state").value=root.getElementsByTagName('state')[0].firstChild.nodeValue;
		    document.getElementById("pincode").value=root.getElementsByTagName('pincode')[0].firstChild.nodeValue;
		    document.getElementById("contactLandline").value=root.getElementsByTagName('contactLandline')[0].firstChild.nodeValue;
		    document.getElementById("contactCell").value=root.getElementsByTagName('contactCell')[0].firstChild.nodeValue;
		    document.getElementById("faxNo").value=root.getElementsByTagName('faxNo')[0].firstChild.nodeValue;
		    document.getElementById("contactEmailId").value=root.getElementsByTagName('contactEmailId')[0].firstChild.nodeValue;
		    
		    
		    /*
		     * Tab4 Nominee Details 	
		    */
		    
		    
		    
		    /*
		     * Tab5 Signature Details 	
		    */
		    
		    var pensionStatus=root.getElementsByTagName('pensionStatus')[0].firstChild.nodeValue;    
			   
		    if(pensionStatus=='Y')
		    	{
		    	 document.forms[0].pensionStatus[0].checked='checked';
		    	}
		    else
		    	{
		    	 document.forms[0].pensionStatus[1].checked='checked';
		    	}
		    
		    
		    document.getElementById("pensionNotPaidFrmMon").value=root.getElementsByTagName('pensionNotPaidFrmMon')[0].firstChild.nodeValue;
		    document.getElementById("pensionNotPaidFrmYear").value=root.getElementsByTagName('pensionNotPaidFrmYear')[0].firstChild.nodeValue;
		    document.getElementById("lastSignatureDate").value=root.getElementsByTagName('lastSignatureDate')[0].firstChild.nodeValue;
		    
		    var signDate=root.getElementsByTagName('lastSignatureDate')[0].firstChild.nodeValue;		    
		   
		    if(signDate=="null")
	    	{
		    	document.getElementById("lastSignatureDate").value="";
	    	}
		    else
	    	{
			    		    
			    var Yr5=signDate.split('-')[0];
				var mon5=signDate.split('-')[1];
				var dat5=signDate.split('-')[2];
				var dates5=dat5+'/'+mon5+'/'+Yr5;		    
			    document.getElementById("lastSignatureDate").value=dates5;
	    	}
		    
		    
		    /*
		     * Tab6 Bank Details 	
		    */
		    
		    document.getElementById("bankId").value=root.getElementsByTagName('bankId')[0].firstChild.nodeValue;
		    document.getElementById("branchId").value=root.getElementsByTagName('branchId')[0].firstChild.nodeValue;
		    document.getElementById("bankAcNo").value=root.getElementsByTagName('bankAcNo')[0].firstChild.nodeValue;
		    
        	
		}
	}
}

        	
        	



