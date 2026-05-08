	
String.prototype.trim = function() {
	        a = this.replace(/^\s+/, '');
	        return a.replace(/\s+$/, '');
	        };
	        

function checkSaveStatus()
	{	
		
		var flag1=true;
		
		var checkStatusLen=document.PensionCutOffEntry.checkStatus.length;
		var checkStatus;		
		
		for(var i=0;i<checkStatusLen;i++)
			{
			if (document.PensionCutOffEntry.checkStatus[i].checked)
		      {
				checkStatus=document.PensionCutOffEntry.checkStatus[i].value;			
		      } 
			}
		
		
		if(checkStatus=='yes')  //partial save
			{
				flag1=partialSaveNull();
			}
		
		
		else if(checkStatus=='no')
			{
				flag1=fullSaveNull();
			}
			
		
		return flag1;
	}

	function partialSaveNull()
		{
	
			var flag2=true;			
			
			var ppoNo=document.getElementById('ppoNo').value.trim();			
			var pensionerName=document.getElementById("pensionerName").value.trim();
			
			var originalPensionAmt=document.getElementById("originalPensionAmt").value.trim();
			var reducedPensionAmt=document.getElementById("reducedPensionAmt").value.trim();
			
			var commAmt=document.getElementById("commAmt").value.trim();
			var commPayDate=document.getElementById("commPayDate").value.trim();			
			
			var yr=document.getElementById('grossServiceYrs').value.trim();
			var mo=document.getElementById('grossServiceMonth').value.trim();
			var da=document.getElementById('grossServiceDays').value.trim();
			var t1=((yr * 365)+(mo*30)+(da*1));

			var yr2=document.getElementById('totServiceYrs').value.trim();
			var mo2=document.getElementById('totServiceMonths').value.trim();
			var da2=document.getElementById('totServiceDays').value.trim();
			var t2=((yr2 * 365)+(mo2*30)+(da2*1));

			var yr3=document.getElementById('quaServiceYrs').value.trim();
			var mo3=document.getElementById('quaServiceMonths').value.trim();
			var da3=document.getElementById('quaServiceDays').value.trim();
			var t3=((yr3 * 365)+(mo3*30)+(da3*1));

			var yr4=document.getElementById('nonquaServiceYrs').value.trim();
			var mo4=document.getElementById('nonquaServiceMonths').value.trim();
			var da4=document.getElementById('nonquaServiceDays').value.trim();
			var t4=((yr4 * 365)+(mo4*30)+(da4*1));

			var yr5=document.getElementById('netquaServiceYrs').value.trim();
			var mo5=document.getElementById('netquaServiceMonths').value.trim();
			var da5=document.getElementById('netquaServiceDays').value.trim();
			var t5=((yr5 * 365)+(mo5*30)+(da5*1));
			
			var familyPenTillDateAmt=document.getElementById('familyPensionTillDateAmt').value.trim();
			var familyPenAfterDateAmt=document.getElementById('familyPensionAtferDateAmt').value.trim();
			
			var chkj=document.getElementById("commOpted1");
			var commOpted=chkj.checked?chkj.value:document.getElementById("commOpted2").value;
		
			var lastSignatureDate=document.getElementById('lastSignatureDate').value.trim();
			var dY1=lastSignatureDate.split("/")[2];
			
			var dateOfBirth=document.getElementById("dateOfBirth").value.trim();			
			var dobSplit=dateOfBirth;
			var dateOfRetirement=document.getElementById("dateOfRetirement").value.trim();
						
			var chkjSta=document.getElementById("pensionStatus1");
			var pensionStatus=chkjSta.checked?chkjSta.value:document.getElementById("pensionStatus2").value;
			
			var pensionNotPaidFrmMon=document.getElementById("pensionNotPaidFrmMon");
			var pensionNotPaidFrmYear=document.getElementById("pensionNotPaidFrmYear").value.trim();
	
						
			if(ppoNo==null || ppoNo=="")
			{
				alert('Please Enter PPO No .');
				document.getElementById("ppoNo").focus();
				flag2=false;					
			}
		
			else if(pensionerName==null || pensionerName=="")
			{
				alert('Please Enter Pensioner Name');
				document.getElementById("pensionerName").focus();
				flag2=false;		
			}				
			
			else if(dobDorCheck(dateOfBirth,dateOfRetirement))
			{					
				flag2=false;
			}
			
			else if(originalPensionAmt<reducedPensionAmt)
			{
				alert('Reduced Pension Amount is Greater than Original Pension Amount.');
				flag2=false;
				
			}			
			
			else if(originalPensionAmt<familyPenTillDateAmt) 
			{
				alert('Family Pension Till Date Amount is Greater than Original Pension Amount.');
				document.getElementById("familyPensionTillDateAmt").value="";			
				flag2=false;
			}
			
			else if((commOpted=="Y") && (commAmt==null || commAmt=="")) 
			{			
				alert('Please Enter Commutation Amount.');			
				flag2=false;		
			}
				
		   else if((commOpted=="Y") &&(commPayDate==null || commPayDate==""))
			{
				alert('Please Enter Commutation Date.');				
				flag2=false;			
			}				
			
			else if(familyPenTillDateAmt<familyPenAfterDateAmt) 
			{
				alert('Family Pension After Date Amount is Greater than Family Pension Till Date Amount.');
				document.getElementById("familyPensionAtferDateAmt").value="";				
				flag2=false;
			}
			
			else if(t1<t2)
			{
			    alert('Total Service  is Greater than Gross Service');
			    flag2=false;
			}
			
			else if(t2<t3) 
			{
				alert('Qualified Service is Greater than Total Service');			
				flag2=false;
			}

			else if(t2<t4) 
			{
				alert('Non Qualified Service is Greater than Total Service');			
				flag2=false;
			}
			
			else if(t2<t5) 
			{
				alert('Net Qualified Service is Greater than Total Service');			
				flag2=false;
			}
							
			else if((pensionStatus=="Y") && (pensionNotPaidFrmMon.selectedIndex==0)) 
			{							
				alert('Please Select Pension Not Paid From Month');			
				flag2=false;		
			}
				
			else if((pensionStatus=="Y") && (pensionNotPaidFrmYear==null || pensionNotPaidFrmYear=="")) 
			{			
				alert('Please Enter Pension Not Paid From Year');			
				flag2=false;		
			}
			
			else if(dY1<2000)
			{
				alert('Last Signature Date should be Greater than 2000.');
				document.getElementById("lastSignatureDate").value="";				
				flag2=false;				
			}
			
			return flag2;	
		}

	

	function fullSaveNull()
		{
			var flag3=true;
			
			var ppoNo=document.getElementById('ppoNo').value.trim();		
			var pensionerName=document.getElementById("pensionerName").value.trim();
					
			var dateOfBirth=document.getElementById("dateOfBirth").value.trim();
			var dateOfRetirement=document.getElementById("dateOfRetirement").value.trim();
			
			var cutOfEntryDate=document.getElementById("cutOfEntryDate").value.trim();
			var OfficeId=document.getElementById("OfficeId").value.trim();

			var classPensionId=document.getElementById("classPensionId");
			var originalPensionAmt=document.getElementById("originalPensionAmt").value.trim();
			var reducedPensionAmt=document.getElementById("reducedPensionAmt").value.trim();
						
			var paymentOfficeId=document.getElementById("paymentOfficeId");
					
			var address=document.getElementById("address").value.trim();
			var district=document.getElementById("district").value.trim();
			var state=document.getElementById("state");
			
			var pensionNotPaidFrmMon=document.getElementById("pensionNotPaidFrmMon");
			var pensionNotPaidFrmYear=document.getElementById("pensionNotPaidFrmYear").value.trim();
			var lastSignatureDate=document.getElementById("lastSignatureDate").value.trim();
			var dY1=lastSignatureDate.split("/")[2];
			
			var bankId=document.getElementById("bankId");
			var branchId=document.getElementById("branchId");
			var bankAcNo=document.getElementById("bankAcNo").value.trim();

			var commReceived=document.getElementById("commReceived").value.trim();			
			var commAmt=document.getElementById("commAmt").value.trim();
			var commPayDate=document.getElementById("commPayDate").value.trim();
		
			var chkj=document.getElementById("commOpted1");
			var commOpted=chkj.checked?chkj.value:document.getElementById("commOpted2").value;
					
			var yr=document.getElementById('grossServiceYrs').value.trim();
			var mo=document.getElementById('grossServiceMonth').value.trim();
			var da=document.getElementById('grossServiceDays').value.trim();
			var t1=((yr * 365)+(mo*30)+(da*1));

			var yr2=document.getElementById('totServiceYrs').value.trim();
			var mo2=document.getElementById('totServiceMonths').value.trim();
			var da2=document.getElementById('totServiceDays').value.trim();
			var t2=((yr2 * 365)+(mo2*30)+(da2*1));

			var yr3=document.getElementById('quaServiceYrs').value.trim();
			var mo3=document.getElementById('quaServiceMonths').value.trim();
			var da3=document.getElementById('quaServiceDays').value.trim();
			var t3=((yr3 * 365)+(mo3*30)+(da3*1));

			var yr4=document.getElementById('nonquaServiceYrs').value.trim();
			var mo4=document.getElementById('nonquaServiceMonths').value.trim();
			var da4=document.getElementById('nonquaServiceDays').value.trim();
			var t4=((yr4 * 365)+(mo4*30)+(da4*1));

			var yr5=document.getElementById('netquaServiceYrs').value.trim();
			var mo5=document.getElementById('netquaServiceMonths').value.trim();
			var da5=document.getElementById('netquaServiceDays').value.trim();
			var t5=((yr5 * 365)+(mo5*30)+(da5*1));

			var familyPenTillDateAmt=document.getElementById('familyPensionTillDateAmt').value.trim();
			var familyPenAfterDateAmt=document.getElementById('familyPensionAtferDateAmt').value.trim();
			
			var chkjSta=document.getElementById("pensionStatus1");
			var pensionStatus=chkjSta.checked?chkjSta.value:document.getElementById("pensionStatus2").value;
								
			
			if(ppoNo==null || ppoNo=="")
			{
				alert('Please Enter PPO No .');
				document.getElementById("ppoNo").focus();
				flag3=false;				
			}
		 
			
		   else if(pensionerName==null || pensionerName=="")
			{
				alert('Please Enter Pensioner Name');
				document.getElementById("pensionerName").focus();
				flag3=false;		
			}									
			
			
		   else if(dateOfBirth==null || dateOfBirth=="")
			{
				alert('Please Enter Date Of Birth');
				document.getElementById("dateOfBirth").focus();
				flag3=false;		
			}
		   else if(dateOfRetirement==null || dateOfRetirement=="")
			{
				alert('Please Enter Date Of Retirement');
				document.getElementById("dateOfRetirement").focus();
				flag3=false;
			}
				
			else if(dobDorCheck(dateOfBirth,dateOfRetirement))
			{					
				flag3=false;
			}
									
			else if(OfficeId==null || OfficeId=="")
			{
				alert('Please Enter Last Woking Office.');				
				flag3=false;				
			}

			else if(cutOfEntryDate==null || cutOfEntryDate=="")
			{
				alert('Please Enter CutOffEntry Date.');
				document.getElementById("cutOfEntryDate").focus();
				flag3=false;							
			}
						
			else if(classPensionId.selectedIndex==0)
			{
				alert('Please Select Class of Pension.');				
				flag3=false;				
			}
			
			else if(originalPensionAmt==null || originalPensionAmt=="")
			{
				alert('Please Enter Original Pension Amount.');			
				flag3=false;				
			}
			
			else if(originalPensionAmt<reducedPensionAmt)
			{
				alert('Original Pension Amount less than Reduced Pension Amount.');
				document.getElementById("reducedPensionAmt").value="";
				flag3=false;				
			}		

			else if(paymentOfficeId.selectedIndex==0)
			{
				alert('Please Select Payment Office.');			
				flag3=false;			
			}
			
			else if((commOpted=="Y") && (commAmt==null || commAmt=="")) 
			{			
				alert('Please Enter Commutation Amount.');			
				flag3=false;		
			}
				
		   else if((commOpted=="Y") &&(commPayDate==null || commPayDate==""))
			{
				alert('Please Enter Commutation Date.');				
				flag3=false;			
			}				
			
					
			else if(familyPenTillDateAmt<familyPenAfterDateAmt) 
			{
				alert('Family Pension After Date Amount is Greater than Family Pension Till Date Amount.');
				document.getElementById("familyPensionAtferDateAmt").value="";
				flag3=false;
			}
			
			else if(t1<t2)
			{
			    alert('Total Service is Greater than Gross Service');			    
			    flag3=false;
			}
			
			else if(t2<t3) 
			{
				alert('Qualified Service is Greater than Total Service');				
				flag3=false;
			}

			else if(t2<t4) 
			{
				alert('Non Qualified Service is Greater than Total Service');				
				flag3=false;
			}
			
			else if(t2<t5) 
			{
				alert('Net Qualified Service is Greater than Total Service');				
				flag3=false;
			}

		   
		   else if(address==null || address=="")
			{
				alert('Please Enter Address.');					
				flag3=false;
			
			}

			else if(district==null || district=="")
			{
				alert('Please Enter District.');				
				flag3=false;
			
			}

			else if(state.selectedIndex==0)
			{
				alert('Please Select State');			
				flag3=false;
				
			}
			
			else if((pensionStatus=="Y") && (pensionNotPaidFrmMon.selectedIndex==0)) 
			{							
				alert('Please Select Pension Not Paid From Month');			
				flag3=false;		
			}
				
			else if((pensionStatus=="Y") && (pensionNotPaidFrmYear==null || pensionNotPaidFrmYear=="")) 
			{			
				alert('Please Select Pension Not Paid From Year');			
				flag3=false;		
			}			
					  
			else if(lastSignatureDate==null || lastSignatureDate=="")
			{
				alert('Please Enter Last Signature Date.');				
				flag3=false;
			
			}
			
			else if(dY1<2000)
			{
				alert('Last Signature Date should be Greater than 2000.');
				document.getElementById("lastSignatureDate").value="";			
				flag3=false;
			
			}
			
			else if(bankId.selectedIndex==0)
			{
				alert('Please Select Bank Name');			
				flag3=false;
				
			}
			
			else if(branchId.selectedIndex==0)
			{
				alert('Please Select Branch Name');					
				flag3=false;
				
			}

			else if(bankAcNo==null || bankAcNo=="")
			{
				alert('Please Enter Account No .');			
				flag3=false;
				
			}			
				
					
			else
			{
				flag3=CheckSubmit();
			}
			
				
			return flag3;
	
}

	
	function dobDorCheck(dObs,dOrs)
	{
		
		var f=false;
		var dB=dObs.split("/");
		var dR=dOrs.split("/");
		var yr1=31536000000;
		var dy1=86400000;
		
		var DdB=new Date();
		DdB.setFullYear(dB[2],parseInt(dB[1])-1,dB[0]);
		
		var DdR=new Date();
		DdR.setFullYear(dR[2],parseInt(dR[1])-1,dR[0]);
		
		var chk40=new Date(DdB.getTime()+(yr1*40)+(dy1*10));
					
		if((chk40.getTime() > DdR.getTime()))
			{
				alert("Minimum Difference Between Date of Birth & Date of Retirement should be 40 Yrs");
				f=true;
			}		
		
		return f;
	}


