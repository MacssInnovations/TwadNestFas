


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





function EditPartialSave()
{
	alert('INSIDE EDIT PARTIAL SAVE');
	var flag=true;
	var ppoNo=document.getElementById("ppoNo").value;		
	var pensionerInitial=document.getElementById("pensionerInitial").value;	
	var pensionerName=document.getElementById("pensionerName").value;
	
	if(ppoNo==null || ppoNo=="")
	{
		alert('Please Enter PPO No');
		document.forms[0].ppoNo.focus();
		flag=false;
		return flag;
	}

	
	if(pensionerInitial==null || pensionerInitial=="")
	{
		alert('Please Enter Pensioner Initial');
		document.forms[0].pensionerInitial.focus();
		flag=false;
		return flag;
	}

	if(pensionerName==null || pensionerName=="")
	{
		alert('Please Enter Pensioner Name');
		document.forms[0].pensionerName.focus();
		flag=false;
		return flag;
	}
	
	
	if(flag==true)
		{
		partialSave2();
		}
		
	return flag;
		
		
	
}

function partialSave2(){
	
	var url;	
	var ppoNo=document.getElementById("ppoNo").value;
	var employeeId=document.getElementById("employeeId").value;	
	var pensionerInitial=document.getElementById("pensionerInitial").value;	
	var pensionerName=document.getElementById("pensionerName").value;	
	//alert(document.PensionCutOffEntry.sex.value)
	/*var genlen=document.PensionCutOffEntry.sex.length;
	var gender;
		
	for(i=0;i<genlen;i++)
		{
		if (document.PensionCutOffEntry.sex[i].checked)
	      {
			gender=document.PensionCutOffEntry.sex[i].value;		
	      }
		}
		*/
	var titleId=document.getElementById("titleId").value;	
	var dateOfBirth=document.getElementById("dateOfBirth").value;
	var dateOfRetirement=document.getElementById("dateOfRetirement").value;
	var desigServGrp=document.getElementById("desigServGrp").value;
	var designationId=document.getElementById("designationId").value;	
	var panNumber=document.getElementById("panNumber").value;
	var idMark1=document.getElementById("idMark1").value;
	var idMark2=document.getElementById("idMark2").value;
	var lastPayDrawnAmt=document.getElementById("lastPayDrawnAmt").value;
	var avgEmoulmentAmt=document.getElementById("avgEmoulmentAmt").value;	
    var lastWorkingOfficeLevel=document.getElementById("lastWorkingOfficeLevel").value;
	var lastWorkingOfficeId=document.getElementById("lastWorkingOfficeId").value;
	var currAccountOfficeId=document.getElementById("currAccountOfficeId").value;	
	var cutOfEntryDate=document.getElementById("cutOfEntryDate").value;
	
	var classPensionId=document.getElementById("classPensionId").value;
	var originalPensionAmt=document.getElementById("originalPensionAmt").value;	
	var reducedPensionAmt=document.getElementById("reducedPensionAmt").value;
	var dearnessPay=document.getElementById("dearnessPay").value;
	var provisionalPensionAmt=document.getElementById("provisionalPensionAmt").value;
	var dcrgAmt=document.getElementById("dcrgAmt").value;
	var commAmt=document.getElementById("commAmt").value;
	var dcrgPertReceived=document.getElementById("dcrgPertReceived").value;
	var paymentOfficeId =document.getElementById("paymentOfficeId").value;
	
/*	var commOptLen=document.PensionCutOffEntry.commOpted.length;
	var commOpted;
		
	for(i=0;i<commOptLen;i++)
		{
		if (document.PensionCutOffEntry.commOpted[i].checked)
	      {

			commOpted=document.PensionCutOffEntry.commOpted[i].value;
		
	      } 
		}
	
	var commRecLen=document.PensionCutOffEntry.commReceived.length;
	var commReceived;
		
	for(i=0;i<commRecLen;i++)
		{
		if (document.PensionCutOffEntry.commReceived[i].checked)
	      {

			commReceived=document.PensionCutOffEntry.commReceived[i].value;
		
	      }  }
	

	
	var commFacLen=document.PensionCutOffEntry.commfactorOnethird.length;
	var commfactorOnethird;
		
	for(i=0;i<commFacLen;i++)
		{
		if (document.PensionCutOffEntry.commfactorOnethird[i].checked)
	      {

			commfactorOnethird=document.PensionCutOffEntry.commfactorOnethird[i].value;
		
	      }  }
	
*/		
	
	var commFactorPert=document.getElementById("commFactorPert").value;
	var commAmt=document.getElementById("commAmt").value;	
	var commPayDate=document.getElementById("commPayDate").value;
	
	/*var pshfLen=document.PensionCutOffEntry.pshfSubscribed.length;
	var pshfSubscribed;
		
	for(i=0;i<pshfLen;i++)
		{
		if (document.PensionCutOffEntry.pshfSubscribed[i].checked)
	      {

			pshfSubscribed=document.PensionCutOffEntry.pshfSubscribed[i].value;
		
	      }
		}*/
	
	
	
	var familyPensionTillDate=document.getElementById("familyPensionTillDate").value;
	var familyPensionTillDateAmt=document.getElementById("familyPensionTillDateAmt").value;
	var familyPensionAtferDateAmt=document.getElementById("familyPensionAtferDateAmt").value;
	
	
	/*var payCommLen=document.PensionCutOffEntry.payCommissionRevisonFlag.length;
	var payCommissionRevisonFlag;
		
	for(i=0;i<payCommLen;i++)
		{
		if (document.PensionCutOffEntry.payCommissionRevisonFlag[i].checked)
	      {

			payCommissionRevisonFlag=document.PensionCutOffEntry.payCommissionRevisonFlag[i].value;
		
	        }
		}*/
	
	var grossServiceYrs=document.getElementById("grossServiceYrs").value;
	var grossServiceMonth=document.getElementById("grossServiceMonth").value;
	var grossServiceDays=document.getElementById("grossServiceDays").value;
	var totServiceYrs=document.getElementById("totServiceYrs").value;
	var totServiceMonths=document.getElementById("totServiceMonths").value;
	var totServiceDays=document.getElementById("totServiceDays").value;
	var quaServiceYrs=document.getElementById("quaServiceYrs").value;
	var quaServiceMonths=document.getElementById("quaServiceMonths").value;
	var quaServiceDays=document.getElementById("quaServiceDays").value;
	var nonquaServiceYrs=document.getElementById("nonquaServiceYrs").value;
	var nonquaServiceMonths=document.getElementById("nonquaServiceMonths").value;
	var nonquaServiceDays=document.getElementById("nonquaServiceDays").value;
	var weightageServiceYrs=document.getElementById("weightageServiceYrs").value;
	var weightageServiceMonths=document.getElementById("weightageServiceMonths").value;
	var weightageServiceDays=document.getElementById("weightageServiceDays").value;
	var netquaServiceYrs=document.getElementById("netquaServiceYrs").value;
	var netquaServiceMonths=document.getElementById("netquaServiceMonths").value;
	var netquaServiceDays=document.getElementById("netquaServiceDays").value;
	
	var address=document.getElementById("address").value;
	var district=document.getElementById("district").value;
	var state=document.getElementById("state").value;
	var pincode=document.getElementById("pincode").value;
	var contactLandline=document.getElementById("contactLandline").value;
	var contactCell=document.getElementById("contactCell").value;
	var faxNo=document.getElementById("faxNo").value;
	var contactEmailId=document.getElementById("contactEmailId").value;
	
	
	/*var penStatusLen=document.PensionCutOffEntry.pensionStatus.length;
	var pensionStatus;
		
	for(i=0;i<penStatusLen;i++)
		{
		if (document.PensionCutOffEntry.pensionStatus[i].checked)
	      {

			pensionStatus=document.PensionCutOffEntry.pensionStatus[i].value;
		
	      }  }*/
	
	var pensionNotPaidFrmMon=document.getElementById("pensionNotPaidFrmMon").value;
	var pensionNotPaidFrmYear=document.getElementById("pensionNotPaidFrmYear").value;
	var lastSignatureDate=document.getElementById("lastSignatureDate").value;
	
	var bankId=document.getElementById("bankId").value;
	var branchId=document.getElementById("branchId").value;
	var bankAcNo=document.getElementById("bankAcNo").value;
	
	
	url = 'EditPensionCutOff.html?mstcutoffentry.ppoNo='+ppoNo 
    +"&mstcutoffentry.employeeId="+employeeId 
    + "&mstcutoffentry.pensionerInitial="+pensionerInitial 
    +"&mstcutoffentry.pensionerName="+pensionerName 
   // +"&mstcutoffentry.sex="+gender
    +"&mstcutoffentry.titleId="+titleId
    +"&mstcutoffentry.dateOfBirth="+dateOfBirth
    +"&mstcutoffentry.dateOfRetirement="+dateOfRetirement
    +"&mstcutoffentry.desigServGrp="+desigServGrp
    +"&mstcutoffentry.designationId="+designationId
    +"&mstcutoffentry.panNumber="+panNumber
    +"&mstcutoffentry.idMark1="+idMark1
    +"&mstcutoffentry.idMark2="+idMark2
    +"&mstcutoffentry.lastPayDrawnAmt="+lastPayDrawnAmt
    +"&mstcutoffentry.avgEmoulmentAmt="+avgEmoulmentAmt
    +"&mstcutoffentry.lastWorkingOfficeLevel="+lastWorkingOfficeLevel
    +"&mstcutoffentry.lastWorkingOfficeId="+lastWorkingOfficeId
    +"&mstcutoffentry.currAccountOfficeId="+currAccountOfficeId
    +"&mstcutoffentry.cutOfEntryDate="+cutOfEntryDate
 
    +"&mstcutoffentry.classPensionId="+classPensionId
    +"&mstcutoffentry.originalPensionAmt="+originalPensionAmt
    +"&mstcutoffentry.reducedPensionAmt="+reducedPensionAmt
    +"&mstcutoffentry.dearnessPay="+dearnessPay
    +"&mstcutoffentry.provisionalPensionAmt="+provisionalPensionAmt
    +"&mstcutoffentry.dcrgAmt="+dcrgAmt
    +"&mstcutoffentry.dcrgPertReceived="+dcrgPertReceived
    +"&mstcutoffentry.paymentOfficeId="+paymentOfficeId
   // +"&mstcutoffentry.commOpted="+commOpted
 //   +"&mstcutoffentry.commReceived="+commReceived       
 //   +"&mstcutoffentry.commfactorOnethird="+commfactorOnethird
    +"&mstcutoffentry.commFactorPert="+commFactorPert
    +"&mstcutoffentry.commAmt="+commAmt
    +"&mstcutoffentry.commPayDate="+commPayDate
   // +"&mstcutoffentry.pshfSubscribed="+pshfSubscribed
    +"&mstcutoffentry.familyPensionTillDate="+familyPensionTillDate
    +"&mstcutoffentry.familyPensionTillDateAmt="+familyPensionTillDateAmt
    +"&mstcutoffentry.familyPensionAtferDateAmt="+familyPensionAtferDateAmt
  //  +"&mstcutoffentry.payCommissionRevisonFlag="+payCommissionRevisonFlag
    
    +"&mstcutoffentry.grossServiceYrs="+grossServiceYrs
    +"&mstcutoffentry.grossServiceMonth="+grossServiceMonth
    +"&mstcutoffentry.grossServiceDays="+grossServiceDays
    +"&mstcutoffentry.totServiceYrs="+totServiceYrs
    +"&mstcutoffentry.totServiceMonths="+totServiceMonths
    +"&mstcutoffentry.totServiceDays="+totServiceDays
    +"&mstcutoffentry.quaServiceYrs="+quaServiceYrs
    +"&mstcutoffentry.quaServiceMonths="+quaServiceMonths
    +"&mstcutoffentry.quaServiceDays="+quaServiceDays
    +"&mstcutoffentry.nonquaServiceYrs="+nonquaServiceYrs
    +"&mstcutoffentry.nonquaServiceMonths="+nonquaServiceMonths
    +"&mstcutoffentry.nonquaServiceDays="+nonquaServiceDays
    +"&mstcutoffentry.weightageServiceYrs="+weightageServiceYrs
    +"&mstcutoffentry.weightageServiceMonths="+weightageServiceMonths
    +"&mstcutoffentry.weightageServiceDays="+weightageServiceDays
    +"&mstcutoffentry.netquaServiceYrs="+netquaServiceYrs
    +"&mstcutoffentry.netquaServiceMonths="+netquaServiceMonths
    +"&mstcutoffentry.netquaServiceDays="+netquaServiceDays
   
    +"&mstcutoffentry.address="+address
    +"&mstcutoffentry.district="+district
    +"&mstcutoffentry.state="+state
    +"&mstcutoffentry.pincode="+pincode
    +"&mstcutoffentry.contactLandline="+contactLandline
    +"&mstcutoffentry.contactCell="+contactCell
    +"&mstcutoffentry.faxNo="+faxNo
    +"&mstcutoffentry.contactEmailId="+contactEmailId
    
 //   +"&mstcutoffentry.pensionStatus="+pensionStatus
    +"&mstcutoffentry.pensionNotPaidFrmMon="+pensionNotPaidFrmMon
    +"&mstcutoffentry.pensionNotPaidFrmYear="+pensionNotPaidFrmYear
    +"&mstcutoffentry.lastSignatureDate="+lastSignatureDate
    
    +"&mstcutoffentry.bankId="+bankId
    +"&mstcutoffentry.branchId="+branchId
    +"&mstcutoffentry.bankAcNo="+bankAcNo;
	
	alert(url);
    
	 var HttpRequest = getReq();
     HttpRequest.open("GET", url, true);
     
     
     HttpRequest.onreadystatechange=function()
     {    
    	
    	 partials(HttpRequest);
     };  
     
     
     HttpRequest.send(null);
	
}



function partials(HttpRequest)
{	
	if(HttpRequest.readyState == 4){
		if(HttpRequest.status == 200)
		{   
			alert('Partially Saved Successfully..');
			document.PensionCutOffEntry.reset();
		}
		else
			{
			alert(' Partial Save cannot be done ');
			}
	}
	
}