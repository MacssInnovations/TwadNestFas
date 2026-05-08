function NullCheck()
{		
	var ppoNo=document.getElementById('ppoNo').value;
	var pensionerInitial=document.getElementById("pensionerInitial").value;
	var pensionerName=document.getElementById("pensionerName").value;
	
	
	var dateOfBirth=document.getElementById("dateOfBirth").value;
	var dateOfRetirement=document.getElementById("dateOfRetirement").value;
	
	var idMark1=document.getElementById("idMark1").value;
	var idMark2=document.getElementById("idMark2").value;
	
	
	
	var classPensionId=document.getElementById("classPensionId");
	var originalPensionAmt=document.getElementById("originalPensionAmt").value;	
	var dearnessPay=document.getElementById("dearnessPay").value;
	
	
	
	
	var paymentOfficeId =document.getElementById("paymentOfficeId");


	
	
	
	//tab2 service details
	
	
	var address=document.getElementById("address").value;
	var district=document.getElementById("district").value;	
	var state=document.getElementById("state").value;
	
	

	var pensionNotPaidFrmMon=document.getElementById("pensionNotPaidFrmMon").value;
	var pensionNotPaidFrmYear=document.getElementById("pensionNotPaidFrmYear").value;
	var lastSignatureDate=document.getElementById("lastSignatureDate").value;
	

	
	
	if(ppoNo==null || ppoNo=="")
	{
		alert('Please Enter PPO No .');
		document.forms[0].ppoNo.focus();
		return false;
	}

	if(pensionerInitial==null || pensionerInitial=="")
	{
		alert('Please Enter Pensioner Intial');
		document.forms[0].pensionerInitial.focus();
		return false;		
	}

	if(pensionerName==null || pensionerName=="")
	{
		alert('Please Enter Pensioner Name');
		document.forms[0].pensionerName.focus();
		return false;		
	}
	
	
	if(dateOfBirth==null || dateOfBirth=="")
	{
		alert('Please Enter DateOfBirth');
		document.forms[0].dateOfBirth.focus();
		return false;		
	}
	
	
	if(dateOfRetirement==null || dateOfRetirement=="")
	{
		alert('Please Enter DateOfRetirement');
		document.forms[0].dateOfRetirement.focus();
		return false;
		
	}		
	
	if(idMark1==null || idMark1=="")
	{
		alert('Please Enter idMark1');
		document.forms[0].idMark1.focus();
		return false;
		
	}
	
	if(idMark2==null || idMark2=="")
	{
		alert('Please Enter idMark2');
		document.forms[0].idMark2.focus();
		return false;
		
	}
	
	
		
	if(classPensionId.selectedIndex==0)
	{
		alert('Please Select Class of Pension');
		document.forms[0].classPensionId.focus();
		return false;		
	}
	
	  if(originalPensionAmt==null || originalPensionAmt=="")
		{
			alert('Please Enter Original Pension Amount');
			document.forms[0].originalPensionAmt.focus();
			return false;
			
		}
				
		if(dearnessPay==null || dearnessPay=="")
		{
			alert('Please Enter DearnessPay');
			document.forms[0].dearnessPay.focus();
			return false;			
		}
		
		
		if(paymentOfficeId.selectedIndex==0)
		{
			alert('Please select Pension Payment Office');
			document.forms[0].paymentOfficeId.focus();
			return false;
			
		}
			
				
		if(address==null || address=="")
		{
			alert('Please Enter Address');
			document.forms[0].address.focus();
			return false;		
		}
		
		if(district==null || district=="")
		{
			alert('Please Enter District');
			document.forms[0].district.focus();
			return false;			
		}
		
		if(state.selectedIndex==0)
		{
			alert('Please select State');
			document.forms[0].state.focus();
			return false;
			
		}
		
		if(pensionNotPaidFrmMon.selectedIndex==0)
		{
			alert('Please Select Pension Not Paid Month');
			document.forms[0].pensionNotPaidFrmMon.focus();
			return false;			
		}
		
		if(pensionNotPaidFrmYear==null || pensionNotPaidFrmYear=="")
		{
			alert('Please Enter pensionNotPaidFrmYear');
			document.forms[0].pensionNotPaidFrmYear.focus();
			return false;			
		}
		
				
		if(lastSignatureDate==null || lastSignatureDate=="")
		{
			alert('Please Enter lastSignatureDate');
			document.forms[0].lastSignatureDate.focus();
			return false;			
		}
		
			
		return true; 
}



function Check()
{
	var ppoNo=document.getElementById('ppoNo').value;
	var pensionerInitial=document.getElementById("pensionerInitial").value;
	var pensionerName=document.getElementById("pensionerName").value;
	
	if(ppoNo==null || ppoNo=="")
	{
		alert('Please Enter PPO No .');
		document.forms[0].ppoNo.focus();
		return false;
	}

	if(pensionerInitial==null || pensionerInitial=="")
	{
		alert('Please Enter Pensioner Intial');
		document.forms[0].pensionerInitial.focus();
		return false;		
	}

	if(pensionerName==null || pensionerName=="")
	{
		alert('Please Enter Pensioner Name');
		document.forms[0].pensionerName.focus();
		return false;		
	}
	
return true;

}
	
