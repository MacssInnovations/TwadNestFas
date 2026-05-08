	String.prototype.trim = function() {
	        a = this.replace(/^\s+/, '');
	        return a.replace(/\s+$/, '');
	        };
	        
	        
function checkSaveStatus1()
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
			flag1=epartialSaveNull();
		}
	
	
	else if(checkStatus=='no') // full save
		{
			flag1=efullSaveNull();
		}
		
		return flag1;
	
	}


	function epartialSaveNull()
		{
		
			deleteAllCookies();
			
			var flag2=true;
					
			var pensionerName=document.getElementById("pensionerName").value.trim();					
			var dateOfBirth=document.getElementById("dateOfBirth").value.trim();		
			var dobSplit=dateOfBirth;
			var dateOfRetirement=document.getElementById("dateOfRetirement").value.trim();
			
			var originalPensionAmt=document.getElementById("originalPensionAmt").value.trim();
			var reducedPensionAmt=document.getElementById("reducedPensionAmt").value.trim();
			
			var familyPenTillDateAmt=document.getElementById('familyPensionTillDateAmt').value.trim();
			var familyPenAfterDateAmt=document.getElementById('familyPensionAtferDateAmt').value.trim();
			
			var commOptedY=document.getElementById("commOptedY").checked;	
			var commOptedN=document.getElementById("commOptedN").checked;
			
			
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
			
			var pensionStatusY=document.getElementById("pensionStatustrue").checked;							
			var pensionNotPaidFrmMon=document.getElementById("pensionNotPaidFrmMon");
			var pensionNotPaidFrmYear=document.getElementById("pensionNotPaidFrmYear").value.trim();
	
			var lastSignatureDate=document.getElementById('lastSignatureDate').value.trim();
			var dY1=lastSignatureDate.split("/")[2];
			
			var chkj=document.getElementById("commReceivedYes");			
			var commRece=chkj.checked?chkj.value:document.getElementById("commReceivedNo").value;

			var optChk=document.getElementById("commOptedY");
			var optChkVal=optChk.checked?optChk.value:document.getElementById("commOptedN").value;
			
			
			if(pensionerName==null || pensionerName=="")
			{
				alert('Please Enter Pensioner Name');
				document.getElementById("pensionerName").focus();
				flag2=false;		
			}		
			
			else if(dobDorCheck(dateOfBirth,dateOfRetirement))
			{	
				flag2=false;
			}
		
			else if(!callsc())
			{				
				flag2=false;	
				
			}
					
			else if((commRece=="Yes") && (commAmt==null || commAmt=="")) 
			{			
				alert('Please Enter Commutation Amount.');			
				flag2=false;		
			}
				
		   else if((commRece=="Yes") &&(commPayDate==null || commPayDate==""))
			{
				alert('Please Enter Commutation Payment Date.');				
				flag2=false;			
			}		
			
			
			else if((optChkVal=="N" && reducedPensionAmt!="0"))
			{	
			   alert('Please Make it Zero in Reduced Pension Amount');			   
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
			
			else if(dY1<2000)
			{
				alert('Last Signature Date should be Greater than 2000.');
				document.getElementById("lastSignatureDate").value="";				
				flag2=false;				
			}			
			
			else if((pensionStatusY) && (pensionNotPaidFrmMon.selectedIndex==0)) 
			{							
				alert('Please Select Pension Not Paid From Month');			
				flag2=false;		
			}
			else if((pensionStatusY) && (pensionNotPaidFrmYear==null || pensionNotPaidFrmYear=="")) 
			{			
				alert('Please Enter Pension Not Paid From Year');			
				flag2=false;		
			}
			
			else
			{
				
				flag2=CheckSubmit();
				
			}
			
			
			
			return flag2;	
		}




	function efullSaveNull()
		{		
		deleteAllCookies();
	
		
			var flag3=true;
			
			var pensionerName=document.getElementById("pensionerName").value.trim();					
			var dateOfBirth=document.getElementById("dateOfBirth").value.trim();		
			var dobSplit=dateOfBirth;
			var dateOfRetirement=document.getElementById("dateOfRetirement").value.trim();
			
			var OfficeId=document.getElementById("OfficeId").value.trim();
			var classPensionId=document.getElementById("classPensionId");
			var cutOfEntryDate=document.getElementById("cutOfEntryDate").value.trim();
			
			var originalPensionAmt=document.getElementById("originalPensionAmt").value.trim();
			var reducedPensionAmt=document.getElementById("reducedPensionAmt").value.trim();
			
			var familyPenTillDateAmt=document.getElementById('familyPensionTillDateAmt').value.trim();
			var familyPenAfterDateAmt=document.getElementById('familyPensionAtferDateAmt').value.trim();
			
			var paymentOfficeId=document.getElementById("paymentOfficeId");
			
			var address=document.getElementById("address").value.trim();
			var district=document.getElementById("district").value.trim();
			var state=document.getElementById("state");
		
			
			var commOptedY=document.getElementById("commOptedY").checked;				
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
			
			var pensionStatusY=document.getElementById("pensionStatustrue").checked;							
			var pensionNotPaidFrmMon=document.getElementById("pensionNotPaidFrmMon");
			var pensionNotPaidFrmYear=document.getElementById("pensionNotPaidFrmYear").value.trim();
			
			var bankId=document.getElementById("bankId");
			var branchId=document.getElementById("branchId");
			var bankAcNo=document.getElementById("bankAcNo").value.trim();

			var lastSignatureDate=document.getElementById('lastSignatureDate').value.trim();
			var dY1=lastSignatureDate.split("/")[2];
			
			var chkj=document.getElementById("commReceivedYes");			
			var commRece=chkj.checked?chkj.value:document.getElementById("commReceivedNo").value;

			var optChk=document.getElementById("commOptedY");
			var optChkVal=optChk.checked?optChk.value:document.getElementById("commOptedN").value;
		
	
			if(pensionerName==null || pensionerName=="")
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
			
		   else if(!callsc())
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
			
			else if(paymentOfficeId.selectedIndex==0)
			{
				alert('Please Select Payment Office.');			
				flag3=false;			
			}
			
			else if((commRece=="Yes") && (commAmt==null || commAmt=="")) 
			{			
				alert('Please Enter Commutation Amount.');			
				flag3=false;		
			}
				
		   else if((commRece=="Yes") &&(commPayDate==null || commPayDate==""))
			{
				alert('Please Enter Commutation Payment Date.');				
				flag3=false;			
			}
			
			
			/*else if((commOptedY) && (commAmt==null || commAmt=="")) 
			{			
				alert('Please Enter Commutation Amount.');			
				flag3=false;		
			}
				
		   else if((commOptedY) &&(commPayDate==null || commPayDate==""))
			{
				alert('Please Enter Commutation Date.');				
				flag3=false;			
			}		*/
			
		   else if((optChkVal=="N" && reducedPensionAmt!="0"))
			{	
			   alert('Please Make it Zero in Reduced Pension Amount');			   
			   flag3=false;
			}
			
			
			else if(t1<t2)
			{
			    alert('Total Service  is Greater than Gross Service');
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
			
			
			else if(dY1<2000)
			{
				alert('Last Signature Date should be Greater than 2000.');
				document.getElementById("lastSignatureDate").value="";				
				flag3=false;				
			}			
			
			else if((pensionStatusY) && (pensionNotPaidFrmMon.selectedIndex==0)) 
			{							
				alert('Please Select Pension Not Paid From Month');			
				flag3=false;		
			}
			else if((pensionStatusY) && (pensionNotPaidFrmYear==null || pensionNotPaidFrmYear=="")) 
			{			
				alert('Please Enter Pension Not Paid From Year');			
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
	
	
	function deleteAllCookies() { 
	
		
	    var cookies = document.cookie.split(";"); 
	 
	    for (var i = 0; i < cookies.length; i++) { 
	        var cookie = cookies[i]; 
	        var eqPos = cookie.indexOf("="); 
	        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie; 
	        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT"; 
	    }
	    return true;
	} 


	function callsc()
	{
		
	  	var flag5=true;
	  	
		var t1=document.getElementById("originalPensionAmt").value.trim();
		var t2=document.getElementById("reducedPensionAmt").value.trim();
		
		var t3=document.getElementById('familyPensionTillDateAmt').value.trim();
		var t4=document.getElementById('familyPensionAtferDateAmt').value.trim();
			
		if(!((eval(t1)==0 && eval(t2)==0) || (eval(t1)>eval(t2))))
		{
		
			alert('Reduced Pension Amount is should be lesser than Original Pension Amount.');
			flag5=false;
		}
		
		else if(!((eval(t1)==eval(t3)) || (eval(t1)>eval(t3))))
		{
			
			alert('Family Pension Till Date Amount is should be Lesser than Original Pension Amount.');
			flag5=false;
		}
		else if(!((eval(t3)==0 && eval(t4)==0) || (eval(t3)>eval(t4))))
		{
			
			alert('Family Pension Till Date Amount is should be Lesser than Family Pension After Date Amount.');
				flag5=false;
		}
	
		
	

	return flag5;
	
	}
