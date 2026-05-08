
function AjaxFunction() {
	var xmlrequest = false;
	try {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e1) {
		try {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			if (command == "printFunc") {
				// alert("manipulate saveFunc");
				printFunc1(baseResponse);
			}
		}
	}
}

function reportFunction(path) {
	//alert("txtFrom_date");
	var txtoption = document.getElementById("txtoption").value;
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtFrom_date = document.getElementById("txtFrom_date").value;
	var txtTo_date=document.getElementById("txtTo_date").value;
//alert(txtFrom_date);
	var txtSubBankAccountNo = document.getElementById("txtSubBankAccountNo").value;
	
	var url="";
		url = "../../../../../IBT_From_Report?command="+txtoption+"&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+"&txtSubBankAccountNo="+txtSubBankAccountNo
				+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date;
		document.frminterbankReport1.action = url;
		document.frminterbankReport1.submit();

	
}

function refresh() {
	var year1;
    var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
    if(month>3)           
	 {
	 year1 = year+1;
	 }else{
	 year1 = year-1;
	 }

	LoadAccountingUnitID('LIST_ALL_UNITS');
	if(month>3)           
	 {
	 document.frminterbankReport1.txtCB_Year.value=year;
	document.frminterbankReport1.txtCB_Year2.value=year1;
	 }else{
	document.frminterbankReport1.txtCB_Year.value=year1;
	document.frminterbankReport1.txtCB_Year2.value=year;
	 }
}

function numbersonly1(e, t) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false;
	}
}

function exitfun(path) {
	window.close();
}



function checknull()
{
    if((document.getElementById("cmbAcc_UnitCode").value=="") || (document.getElementById("cmbAcc_UnitCode").value.length<=0) || (document.getElementById("cmbAcc_UnitCode").value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.getElementById("cmbAcc_UnitCode").focus();
        return false;
    }
    if((document.getElementById("cmbOffice_code").value=="") || (document.getElementById("cmbOffice_code").value.length<=0) || (document.getElementById("cmbOffice_code").value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.getElementById("cmbOffice_code").focus();
        return false;
    
    }
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
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
            return false ;
    }
 }

function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }

function checkdt(t)
{
	    if(t.value.length==0)
	    	return false;
	    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
	    {
	      
	            var c=t.value;
//		        try{
//		        var f=DateFormat(t,c,event,true,'3');
//		        }catch(e){
	            
	            try{
	                var f=isValidDate(c);
	               }
	           catch(e){
	            
	            
		        //exception  start
		        
		        t.value=c;
	            var sc=t.value.split('/');
	            var currenDay =sc[0];
	            var currentMonth=sc[1];
	            var currentYear=sc[2];
	            
	            if(currentYear<_Service_Period_Beg_Year)
	            {
	            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
	            t.value="";
	            t.focus();
	            return false;
	            }
	            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
	            if(currentYear > getCurrentYear())
	            {
	            
	                    alert('Entered date should be less than current date ');
	                    t.value="";
	                    t.focus();
	                    return false;
	            } 
	            else if(currentYear == getCurrentYear())
	            {
	                    if( currentMonth > getCurrentMonth())
	                    {
	                        	alert('Entered date should be less than current date');
	                        	t.value="";
	                        	t.focus();
	                        	return false;
	                    }
	                    else if( currentMonth == getCurrentMonth())
	                    {
	                        	if(currenDay > getCurrentDay() )
	                        	{
		                                alert('Entered date should be less than current date ');
		                                t.value="";
		                                t.focus();
		                                return false;
	                        	}
	                    }
	                    
	            }
		            
	            t.value=c;
	            if(err!=0)
	            {
	                    t.value="";
	                    return false;
	            }
	            return true;
		        
		        
		        //exception end
		        
		        }
		        if( f==true)
			    {
			           
			            t.value=c;
			            var sc=t.value.split('/');
			            var currenDay =sc[0];
			            var currentMonth=sc[1];
			            var currentYear=sc[2];
			            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
			             if(currentYear<_Service_Period_Beg_Year)
			            {
			            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
			            t.value="";
			            t.focus();
			            return false;
			            }
			            if(currentYear > getCurrentYear())
			            {
			            
			                    alert('Entered date should be less than current date');
			                    t.value="";
			                    t.focus();
			                    return false;
			           } 
			           else if(currentYear == getCurrentYear())
			           {
			                    if( currentMonth > getCurrentMonth())
			                    {
			                        alert('Entered date should be less than current date');
			                        t.value="";
			                        t.focus();
			                        return false;
			                    }
			                    else if( currentMonth == getCurrentMonth())
			                    {
			                        if(currenDay > getCurrentDay() )
			                        {
			                                alert('Entered date should be less than current date ');
			                                t.value="";
			                                t.focus();
			                                return false;
			                        }
			                    }
			                    
			           }
			            
			           t.value=c;
			           
			            return true;
			            
			    }
		        else
		        {
		                if(err!=0)
		                {
		                    t.value="";
		                    return false;
		                }
		        }
	            
	    }
	    else
	    {
	            alert('Date format  should be (dd/mm/yyyy)');
	            t.value="";
	            //t.focus();
	            return false;
	    }
	    
}