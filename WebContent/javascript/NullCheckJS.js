function NullCheck()
{

	var flag=true;

	var ppoNo=document.getElementById('ppoNo').value;
	var pensionerInitial=document.getElementById("pensionerInitial").value;
	var pensionerName=document.getElementById("pensionerName").value;
	var dateOfBirth=document.getElementById("dateOfBirth").value;
	var dateOfRetirement=document.getElementById("dateOfRetirement").value;
	var idMark1=document.getElementById("idMark1").value;
	var idMark2=document.getElementById("idMark2").value;
	var cutOfEntryDate=document.getElementById("cutOfEntryDate").value;
	var OfficeId=document.getElementById("OfficeId").value;

	var classPensionId=document.getElementById("classPensionId");
	var originalPensionAmt=document.getElementById("originalPensionAmt").value;
	var paymentOfficeId=document.getElementById("paymentOfficeId");

	var address=document.getElementById("address").value;
	var district=document.getElementById("district").value;
	var state=document.getElementById("state");

	
	var pensionNotPaidFrmMon=document.getElementById("pensionNotPaidFrmMon");
	var pensionNotPaidFrmYear=document.getElementById("pensionNotPaidFrmYear").value;
	var lastSignatureDate=document.getElementById("lastSignatureDate").value;

	var bankId=document.getElementById("bankId");
	var branchId=document.getElementById("branchId");
	var bankAcNo=document.getElementById("bankAcNo").value;


	if(ppoNo==null || ppoNo=="")
	{
		alert('Please Enter PPO No .');
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


	if(dateOfBirth==null || dateOfBirth=="")
	{
		alert('Please Enter DateOfBirth');
		document.forms[0].dateOfBirth.focus();
		flag=false;
		return flag;
	}


	if(dateOfRetirement==null || dateOfRetirement=="")
	{
		alert('Please Enter DateOfRetirement');
		document.forms[0].dateOfRetirement.focus();
		flag=false;
		return flag;

	}

	if(idMark1==null || idMark1=="")
	{
		alert('Please Enter idMark1');
		document.forms[0].idMark1.focus();
		flag=false;
		return flag;

	}

	if(idMark2==null || idMark2=="")
	{
		alert('Please Enter idMark2');
		document.forms[0].idMark2.focus();
		flag=false;
		return flag;

	}

	
	if(OfficeId==null || OfficeId=="")
	{
		alert('Please Enter last woking office.');
		flag=false;
		return flag;
	}

	if(cutOfEntryDate==null || cutOfEntryDate=="")
	{
		alert('Please Enter cutOffEntryDate');
		document.forms[0].cutOfEntryDate.focus();
		flag=false;
		return flag;
	}

	if(classPensionId.selectedIndex==0)
	{
		alert('Please Select Class of Pension');
		document.forms[0].classPensionId.focus();
		flag=false;
		return flag;
	}
	
	if(originalPensionAmt==null || originalPensionAmt=="")
	{
		alert('Please Enter original Pension Amount');
		document.forms[0].originalPensionAmt.focus();
		flag=false;
		return flag;
	}

	if(paymentOfficeId.selectedIndex==0)
	{
		alert('Please Select paymentOffice');
		document.forms[0].paymentOfficeId.focus();
		flag=false;
		return flag;
	}
	
	
	if(address==null || address=="")
	{
		alert('Please Enter Address.');
		
		flag=false;
		return flag;
	}


	if(district==null || district=="")
	{
		alert('Please Enter District.');
		document.forms[0].district.focus();
		flag=false;
		return flag;
	}


	if(state.selectedIndex==0)
	{
		alert('Please Select State');
		document.forms[0].state.focus();
		flag=false;
		return flag;
	}

	if(pensionNotPaidFrmMon.selectedIndex==0)
	{
		alert('Please Select Pension Not Paid From Month');
		
		flag=false;
		return flag;
	}
	
	if(pensionNotPaidFrmYear==null || pensionNotPaidFrmYear=="")
	{
		alert('Please Enter Pension Not Paid From Year.');
		document.forms[0].pensionNotPaidFrmYear.focus();
		flag=false;
		return flag;
	}
	
	if(lastSignatureDate==null || lastSignatureDate=="")
	{
		alert('Please Enter Last Signature Date.');
		document.forms[0].lastSignatureDate.focus();
		flag=false;
		return flag;
	}
	
	
	if(bankId.selectedIndex==0)
	{
		alert('Please Select Bank Name');		
		flag=false;
		return flag;
	}


	if(branchId.selectedIndex==0)
	{
		alert('Please Select Bank Branch Name');
		document.forms[0].branchId.focus();
		flag=false;
		return flag;
	}

	if(bankAcNo==null || bankAcNo=="")
	{
		alert('Please Enter Account No .');
		document.forms[0].bankAcNo.focus();
		flag=false;
		return flag;
	}


	var dOb=new Date(dateOfBirth);
	var dTr=new Date(dateOfRetirement);
	
	var Yr1=31536000000;
	var time1=dTr.getTime()-dOb.getTime();
	
	if(parseInt(time1/Yr1)<40)
		{
		alert('Minimum Diff. between DOB and DOR - 40 years ! ');
		flag=false;
		//document.getElementById("dateOfRetirement").value="";
		document.getElementById("dateOfRetirement").focus();
		}
	
	
	
return flag;


}