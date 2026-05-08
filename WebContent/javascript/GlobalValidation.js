
function setDateTime()
{
	
var d = new Date();
var curr_date = d.getDate();
var curr_month= d.getMonth()+1;
var curr_year = d.getFullYear();
var result=curr_date + "/" + curr_month + "/" + curr_year;
document.getElementById("cutOfEntryDate").value=result;

}


function alphanumeric(obj)
{
	var numaric = obj.value;
	
	for(var j=0; j<numaric.length; j++)
		{
		  var alphaa = numaric.charAt(j);
		  var hh = alphaa.charCodeAt(0);
		  if((hh > 47 && hh<58) || (hh > 64 && hh<91) || (hh > 96 && hh<123))
		  {
		  }
		else	{
			
					
			alert("No Special Characters are Allowed");
			
			obj.value="";
			
			document.forms[0].field1.focus();
                      
             return false;
			
		  }
 		}
 
 return true;
}



function iswString(textObj) {
	   var newValue = textObj.value;
	   var newLength = newValue.length;
	   var extraChars=". -,";

	   var search;
	   for(var i = 0; i != newLength; i++) {
	      aChar = newValue.substring(i,i+1);
	      aChar = aChar.toUpperCase();
	      search = extraChars.indexOf(aChar);
	      if(search == -1 && (aChar < "A" || aChar > "Z") ) {
	       
	         alert('not  a valid data');
	         return false;
	      }
	   }
	   return true;
	}





function within100(value)
{
	if(value>100)
		alert('DCRG % should not exceed 100');
}



function Commutationfactor(value)
{
	if(value>33)
         alert('Commutation Factor % should not exceed 33');
		
}



function IsNumeric(Object)
//  check for valid numeric strings	
{
var strValidChars = "0123456789.-";
var strChar;
var blnResult = true;
var strString=Object.value;

if (strString.length == 0) return false;

//  test strString consists of valid characters listed above
for (var i = 0; i < strString.length && blnResult == true; i++)
   {
   strChar = strString.charAt(i);
   if (strValidChars.indexOf(strChar) == -1)
      {
      blnResult = false;
      alert('Please check - non numeric value!');
      Object.value="";
      
      }
   }
return blnResult;
}


function ischarValid(event,field) { 
	var valo = new String();
	var numere = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ.-";
	var chars = field.value.split("");
	for (i = 0; i < chars.length; i++) {
		if (numere.indexOf(chars[i]) != -1) 
			valo += chars[i];	
	}
	if (field.value != valo) field.value = valo;
		  
}


function dob(value)
{

	var value1=value.trim();
	var len=value1.length;	
	var arr=new Array(len);
	
		for(var i=0;i<len;i++)
		{
		arr[i]=value1.charAt(i);
        }
		var date=arr[0];	
		var date1=arr[1];	
		var dates=date+""+date1;
	
		var mon=arr[2];
		var mont=arr[3];
		var month=mon+""+mont;
		
		var year=arr[4]+""+arr[5]+""+arr[6]+""+arr[7];
			
		res=dates+"/"+month+"/"+year;
	
		document.forms[0].dateOfBirth.value=res;
		
		
}



function dor(value)
{

	var value1=value.trim();
	var len=value1.length;	
	var arr=new Array(len);
	
		for(var i=0;i<len;i++)
		{
		arr[i]=value1.charAt(i);
        }
		var date=arr[0];	
		var date1=arr[1];	
		var dates=date+""+date1;
	
		var mon=arr[2];
		var mont=arr[3];
		var month=mon+""+mont;
		
		var year=arr[4]+""+arr[5]+""+arr[6]+""+arr[7];
			
		res=dates+"/"+month+"/"+year;
	
		document.forms[0].dateOfRetirement.value=res;
		
	
	
}


function genderChange(value,id)
{
	
	var s = document.getElementById("titleId");
	   
	if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
    {
		var optn0 = document.createElement("OPTION");
		var optn1 = document.createElement("OPTION");
		var optn2 = document.createElement("OPTION");
	
		if(value=='M')
    	 {
			document.forms[0].titleId.options.length = 0;
			
			optn0.text = "Select";
			optn0.value="0";
			s.options.add(optn0);
			
			
			optn1.text = "Mr";
			optn1.value="1";
			s.options.add(optn1);
			   	
    	 }
		else
			{
			document.forms[0].titleId.options.length = 0; 			
			optn0.text = "Select";
			optn0.value="0";
			s.options.add(optn0);
			
			optn1.text = "Mrs";
			optn1.value="2";
			s.options.add(optn1);
			
			optn2.text = "Selvi";
			optn2.value="3";
			s.options.add(optn2);
			}
    	
    }
	
	else
		{
		
		if(value=='M')
		{
		s.innerHTML='<option value="0">Select</option><option value="1">Mr</option>';
		}
    	else
		{
		s.innerHTML='<option value="0">Select</option><option value="2">Mrs</option><option value="3">Selvi</option>';
		}

		
		}

}

function checkDate()
{
	var dates=new Date();
	var currYear=dates.getFullYear();
	var currMonth=dates.getMonth()+1;
	var currDate=dates.getDate();
	
	var formdata=document.getElementById("dateOfRetirement").value;
	
	var day=formdata.split('/')[0];
	var mon=formdata.split('/')[1];
	var yr=formdata.split('/')[2];
	
	
	if((yr>currYear)||(mon>currMonth)||(day>currDate)) {
		alert('Do not Enter Future Date.. ');
		document.forms[0].dateOfRetirement.value="";
	
	}
	
		var dob=document.forms[0].dateOfBirth.value;
		var dor=document.forms[0].dateOfRetirement.value;
		
		var date=new Date();
		var curryear=date.getFullYear();
		
		var dobyear=dob.split('/')[2];		
		var doryear=dor.split('/')[2];
		
		var diff=doryear-dobyear;
		
	
		
		if(diff>=40)
			{
		
			
			}	
	
		else
			
			{
			alert('Minimum diff.Between DOB and DOR should be 40 years');
			
			}
		
	
		
	
	
	
	
				
}



// email validation 

function echeck(str) {

		var at="@";
		var dot=".";
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID");
		    return false;
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID")
		    return false
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID")
		    return false
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID")
		    return false
		 }

 		 return true					
	}

function emailvalidation(){
	var emailID=document.PensionCutOffEntry.contactEmailId;
	
	if ((emailID.value==null)||(emailID.value=="")){
		alert("Please Enter your Email ID");
		emailID.focus();
		return false;
	}
	if (echeck(emailID.value)==false){
		emailID.value="";
		emailID.focus();
		return false;
	}
	return true;
 }



//  mobile number validation



function phoneValidation(value)
{

	var contactCell=document.forms[0].contactCell.value;
	if(contactCell.length>=10)
	{
		var mob=contactCell.substring(0,1);
		if(mob=="9" || mob=="8")
		{
			
		}
		
		else{  
		  alert("Mobile No Starts with only 8 or 9.");
		  document.forms[0].contactCell.value="";
		  document.forms[0].contactCell.focus();
	      return false;
      }
  }
	
	else
		alert('Mobile Number must contain 10 Digits');
 return true;
}






//  residential No validation 

function landLine()
{
		if(contactLandline.length>=10 && contactLandline.length<=20){
			}

		else
		{
			alert("Enter  Minimum 10 Numbers in Contact Phone No(with STD Code)...");
			document.forms[0].contactLandline.value="";
			document.forms[0].contactLandline.focus();
			return false;
		}

return true;

}



//   pincode validation

	function _isValidatePincode(t){
	 var pincode=document.forms[0].pincode.value;
	
	 	if(pincode=="")
	       {
	           alert("--Enter Pincode--");
	           return false;
	       }
	       else
	       {
	            if(isNaN(pincode))
	             {
	                  alert("Pincode Contains Only Numbers....."); 
	                  
	                  t.value="";
	                  return false;
	              }
	              
	            
	              if(pincode.length>6) 
	              {
	                  alert("Pincode Must be Six Digits Only.....");
	                  t.value="";
	                  
	                  return false;
	                  
	              }
	              
	              if(pincode.length<6) 
	              {
	                  alert("Pincode Must be Six Digits .....");
	                  t.value="";
	                  
	                  return false;
	              }
	            
	             if(pincode<600000 || pincode>700000)
	              {
	                 alert("Pincode must be within 600000-700000 ");
	                 t.value="";
	            
	                 return false;
	              }
	      }
	         return true;	

}




//     Service check 
	
	
	function ServiceCheck(Obj)
	{
	
	var value=Obj.value;
	var id=Obj.id;
	
		IsNumeric(Obj);
		
		if(id=='grossServiceYrs' && value>50)
		{
			
		alert('Years must be less than 50');
		document.forms[0].grossServiceYrs.value="";
		
			return false;
		}
		
	
		if(id=='grossServiceMonth' && value>12)
			{
			alert('Months must be less than 12 ');
			document.forms[0].grossServiceMonth.value="";
			
				return false;
			}
	
	
		if(id=='grossServiceDays' && value>30)
		{
		alert('Days must be less than 30 ');
		document.forms[0].grossServiceDays.value="";
			return false;
		}
		
		
		if(id=='totServiceYrs' && value>50)
		{
		alert('Years must be less than 50');
		document.forms[0].totServiceYrs.value="";
		
			return false;
		}
		
		
		if(id=='totServiceMonths' && value>12)
		{
		alert('Months must be less than 12 ');
		document.forms[0].totServiceMonths.value="";
			return false;
		}


		if(id=='totServiceDays' && value>30)
		{
		alert('Days must be less than 30 ');
		document.forms[0].totServiceDays.value="";
			return false;
		}
		
		if(id=='quaServiceYrs' && value>50)
		{
		alert('Years must be less than 50');
		document.forms[0].quaServiceYrs.value="";
		
			return false;
		}
		
		if(id=='quaServiceMonths' && value>12)
		{
		alert('Months must be less than 12 ');
		document.forms[0].quaServiceMonths.value="";
			return false;
		}


		if(id=='quaServiceDays' && value>30)
		{
		alert('Days must be less than 30 ');
		document.forms[0].quaServiceDays.value="";
			return false;
		}
	
		if(id=='nonquaServiceYrs' && value>50)
		{
		alert('Years must be less than 50');
		document.forms[0].nonquaServiceYrs.value="";
		
			return false;
		}
		
		
		if(id=='nonquaServiceMonths' && value>12)
		{
		alert('Months must be less than 12 ');
		document.forms[0].nonquaServiceMonths.value="";
			return false;
		}


		if(id=='nonquaServiceDays' && value>30)
		{
		alert('Days must be less than 30 ');
		document.forms[0].nonquaServiceDays.value="";
			return false;
		}
		
		if(id=='weightageServiceYrs' && value>5)
		{
		alert('Years must be less than 5');
		document.forms[0].weightageServiceYrs.value="";
		
			return false;
		}
		
		
		if(id=='weightageServiceMonths' && value>12)
		{
		alert('Months must be less than 12 ');
		document.forms[0].weightageServiceMonths.value="";
			return false;
		}


		if(id=='weightageServiceDays' && value>30)
		{
		alert('Days must be less than 30 ');
		document.forms[0].weightageServiceDays.value="";
			return false;
		}
		
		if(id=='netquaServiceYrs' && value>50)
		{
		alert('Years must be less than 50');
		document.forms[0].netquaServiceYrs.value="";
		
			return false;
		}
		
		
		if(id=='netquaServiceMonths' && value>12)
		{
		alert('Months must be less than 12 ');
		document.forms[0].netquaServiceMonths.value="";
			return false;
		}


		if(id=='netquaServiceDays' && value>30)
		{
		alert('Days must be less than 30 ');
		document.forms[0].netquaServiceDays.value="";
		
			return false;
		}
		
	return true;
	}

function disable1(command)
{
	
	if(command=='commOptedY')
	{
		document.PensionCutOffEntry.commAmt.disabled=false;
		document.PensionCutOffEntry.commPayDate.disabled=false;
		
	}	
	
	else
	{
	document.PensionCutOffEntry.commAmt.disabled=true;
	document.PensionCutOffEntry.commPayDate.disabled=true;
	document.PensionCutOffEntry.commFactorPert.disabled=true;	
	document.PensionCutOffEntry.commfactorOnethird.disabled=true; 
		
	}
}


function onethird(command)
{
	
	if(command=='commfactorOnethird1/3')
	{
		document.PensionCutOffEntry.commFactorPert.disabled=true;
	}
	
	else
		{
		document.PensionCutOffEntry.commFactorPert.disabled=false;
		}
}


function pensionstatus(value)
{
	if(value=='Y')   // 1 means Yes pension withheld 
		{

		document.PensionCutOffEntry.pensionNotPaidFrmMon.disabled=false;
		document.PensionCutOffEntry.pensionNotPaidFrmYear.disabled=false;
		}
		
	else if(value=='N')
		{
		
		
		document.PensionCutOffEntry.pensionNotPaidFrmYear.value="";
		document.PensionCutOffEntry.pensionNotPaidFrmMon.disabled=true;
		document.PensionCutOffEntry.pensionNotPaidFrmYear.disabled=true;
		}
}

	function pensionWithHeldYear(value,id)
	{
	
		var date= new Date();
		var year =date.getFullYear();
		
		if(value<=1970)
			{
				document.forms[0].pensionNotPaidFrmYear.value="";
				alert('Year Should be less than 1970');
				return false;
			}
		
		if(year<value)
			{
			document.forms[0].pensionNotPaidFrmYear.value="";
			alert('Should Not be Future Date');
			return false;
			}
	
		return true;
	}

	
	// automatic '/' generator in  date format
	
	function dtval(d,e) {
		var pK = e ? e.which : window.event.keyCode;
		if (pK == 8) {d.value = substr(0,d.value.length-1); return;}
		var dt = d.value;
		var da = dt.split('/');
		for (var a = 0; a < da.length; a++) {if (da[a] != +da[a]) da[a] = da[a].substr(0,da[a].length-1);}
		if (da[0] > 31) {da[1] = da[0].substr(da[0].length-1,1);da[0] = '0'+da[0].substr(0,da[0].length-1);}
		if (da[1] > 12) {da[2] = da[1].substr(da[1].length-1,1);da[1] = '0'+da[1].substr(0,da[1].length-1);}
		if (da[2] > 9999) da[1] = da[2].substr(0,da[2].length-1);
		dt = da.join('/');
		if (dt.length == 2 || dt.length == 5) dt += '/';
		d.value = dt;
		}
	
	
	
	function checkFutureDOB(Obj)
	{
		var dates=new Date();
		var currYear=dates.getFullYear();
		var currMonth=dates.getMonth()+1;
		var currDate=dates.getDate();
		
		var formdata=document.getElementById("dateOfBirth").value;
		
		var day=formdata.split('/')[0];
		var mon=formdata.split('/')[1];
		var yr=formdata.split('/')[2];
		
		
		if((yr>currYear)||(mon>currMonth)||(day>currDate)){
			alert('Do not Enter Future Date.. ');
			Obj.value="";
		}
	}
	
	
	
	

	
	function checkFutureCommPayDate(obj)
	{
		var dates=new Date();
		var currYear=dates.getFullYear();
		var currMonth=dates.getMonth()+1;
		var currDate=dates.getDate();
		
		var formdata=document.getElementById("commPayDate").value;
		
		var day=formdata.split('/')[0];
		var mon=formdata.split('/')[1];
		var yr=formdata.split('/')[2];
		
		
		if((yr>currYear)||(mon>currMonth)||(day>currDate)){
			alert('Do not Enter Future Date.. ');
			obj.value="";
		}
	}
	
	
	
	

	function checkfamilyPensionTillDate(obj)
	{
		
		var dates=new Date();
		var currYear=dates.getFullYear();
		var currMonth=dates.getMonth()+1;
		var currDate=dates.getDate();
		
		var formdata=document.getElementById("familyPensionTillDate").value;
		
		var day=formdata.split('/')[0];
		var mon=formdata.split('/')[1];
		var yr=formdata.split('/')[2];
		
		
		if((yr>currYear)||(mon>currMonth)||(day>currDate)){
			alert('Do not Enter Future Date.. ');
			obj.value="";
		}
	}
	
	
	
	
	
	
	
	