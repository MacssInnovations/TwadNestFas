f/*THIS SCRIPT IS USED TO GET THE OFFICE DETAILS BASED ON OFFICE ID,CLASS DETAILS,FEES DETAILS, VERIFICATION AND AUTOGENERATION OF
 REQUEST SEQUENCE NO
 SERVLET CALLED IS:ReqRegn_Servlet   */

//XMLHTTPREQUEST DECLARED AND CREATED
//alert("called now");
var winListAllBudget;

function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function clearAll() {
	document.frmNewRegn.cmbWing.value = "";
	document.frmNewRegn.txtContName.value = "";
	document.frmNewRegn.txtadd.value = "";
	document.frmNewRegn.txtResNo.value = "";
	document.frmNewRegn.txtDate.value = "";
	document.frmNewRegn.txtPhone.value = "";
	document.frmNewRegn.txtEmail.value = "";
	document.frmNewRegn.txtClass.value = "0";
	document.frmNewRegn.cmbjurisdiction.value = "0";
	document.frmNewRegn.txtContID.value = "";
	document.frmNewRegn.txtDate_Upto.value = "";
	document.frmNewRegn.txtAlias.value = "";	
	document.frmNewRegn.txtContID = "";

	var d = document.getElementById("cmdUpdate");	
	d.style.display = "none";
	var d2 = document.getElementById("cmdDelete");
	d2.style.display = "none";
	var d1 = document.getElementById("cmdSubmit");
	d1.style.display = "block";
	
}

//FUNCTION CALLED IN ONBLUR TO CALL THE SERVLET TO GET THE OFFICE DETAILS BASED ON OFFICE ID
function getOfficeDetails() {
	// alert("inside ofiice");
	var offid = document.frmNewRegn.txtOffID.value;
	//alert(offid);
	var url = "";
	url = "../../../../../ReqRegn_Servlet.view?command=Load&offid=" + offid;
	//alert(url);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processResponse(req);
	}
	req.send(null);

}

//XML RESPONSE RETURNED BY THE SERVLET
function processResponse(req) {
	// code for processing the xml returned by servlet  
	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
			if (command == "Load") {

				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				//alert("called with " + flag);
				if (flag == "success") {
					document.frmNewRegn.txtOffName.value = baseResponse
							.getElementsByTagName("offname")[0].firstChild.nodeValue;
					document.frmNewRegn.txtoffAddress.value = baseResponse
							.getElementsByTagName("address")[0].firstChild.nodeValue;

					document.frmNewRegn.txtDate_Upto.focus();
					// alert('test');
					// 
					//document.frmNewRegn.radio1.focus();
				} else {
					alert(" Office id not exist ");
					document.frmNewRegn.txtOffID.value = "";
					document.frmNewRegn.txtOffID.focus();
				}
			} else if (command == "verify") {
				verifyResp(baseResponse);
			} else if (command == "Display") {
				displaySNO(baseResponse);
			} else if (command == "loadWing") {
				loadWing1(baseResponse);
			}

		}
	}
}

function findHeadOffice() {
	var officeid = document.frmNewRegn.txtOffID.value;
	if (officeid == 5000) {
		document.getElementById("cmbWing").style.visibility = "visible";

		url = "../../../../../ProjectMasterServlet.con?command=loadWing&Office="
				+ officeid;
		// alert(url);
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			processResponse(req);
		}
		req.send(null);
	}
}

function loadWing1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len45 = baseResponse.getElementsByTagName("wing_id").length;
		if (len45 > 0) {
			for ( var i = 0; i < len45; i++) {
				var wing_id = baseResponse.getElementsByTagName("wing_id")[i].firstChild.nodeValue;
				var wing_name = baseResponse.getElementsByTagName("wing_name")[i].firstChild.nodeValue;
				var se = document.getElementById("cmbWing");
				var op = document.createElement("OPTION");
				op.value = wing_id;
				var txt = document.createTextNode(wing_name);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Wing Details Does Not Exist");
		}
	} else {
		alert("Failed to Load Wing");
	}
}

//FUNCTION TO CALL THE SERVLET TO GET TO CLASS DETAILS 
function getClassDetails() {
	//alert("class");
	var strofflevel = document.frmNewRegn.htxtOffLevel.value;
	//alert(strofflevel);
	var url = "";
	url = "../../../../../ReqRegn_Servlet.view?command=Class&offlevel="
			+ strofflevel;
	//alert(url);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processClass(req);
	}
	req.send(null);

}
//XML RESPONSE FOR CLASS DETAILS
function processClass(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {
			var i;
			var j;
			var fir1 = document.getElementsByName("txtClass")[0];
			fir1.innerHTML = "";
			var sel = req.responseXML.getElementsByTagName("select")[0];
			//alert(req.responseXML);
			var options = sel.getElementsByTagName("option");
			var htop = document.createElement("OPTION");
			htop.text = "--Select Here--";
			try {
				fir1.add(htop);
			} catch (e) {
				fir1.add(htop, null);
			}
			for (i = 0; i < options.length; i++) {

				var desc = options[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
				var id = options[i].getElementsByTagName("id")[0].firstChild.nodeValue;
				var htoption = document.createElement("OPTION");
				htoption.text = desc;
				htoption.value = id;
				try {
					fir1.add(htoption);
				} catch (e) {
					fir1.add(htoption, null);
				}
			}
		}

	}
}

//FUNCTION CALLED IN ONCHANGE OF REGN.CLASS TO GET THE FEES

function getFees() {
	//alert("inside getfees");
	var classid = document.frmNewRegn.txtClass
			.options(document.frmNewRegn.txtClass.selectedIndex).value;

	if (document.frmNewRegn.State[0].checked) {
		var state = document.frmNewRegn.State[0].value;
	} else {
		var state = document.frmNewRegn.State[1].value;
	}
	var url = "";
	url = "../../../../../ReqRegn_Servlet.view?command=Fees&classid=" + classid
			+ "&state=" + state;
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		feesResponse(req);
	}
	req.send(null);
}
//XML RESPONSE RETURNED BY THE SERVLET FOR FEES DETAILS
function feesResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {
				//alert(baseResponse);
				var nextRes = baseResponse.getElementsByTagName("fees")[0];
				//alert(nextRes);
				var fees = nextRes.firstChild.nodeValue;
				//alert(fees);
				document.frmNewRegn.txtRegn_Fees.value = fees;

			} else {
				alert("sorry--fees does not exist");
				document.frmNewRegn.txtRegn_Fees.value = "";

			}

		}

	}
}
//FUNCTION CALLED ON RADIO CLICK OF(NEW or EXISTING)
function radvalue() {
	//alert("hello")

	if (document.frmNewRegn.radio1[0].checked) {
		document.frmNewRegn.txtContId.disabled = false;
		//document.frmNewRegn.txtContId.focus();
	}

	else {

		document.frmNewRegn.txtContId.disabled = true;
		my_window = window.open("LoadExistingReqRegn.jsp", "mywindow1",
				"status=1,height=750,width=750,resizable,scrollbars");
		my_window.moveTo(250, 250);

	}
}

//FUNCTION CALLED IN ONBLUR TO VERIFY THE ALREADY EXISTING CONTRACTOR ID AND SERVLET IS CALLED
function verifyRegID() {

	if ((document.frmNewRegn.txtOffID.value == "")
			|| (document.frmNewRegn.txtOffID.value.length <= 0)) {
		alert("Please Enter Office Id");
		document.frmNewRegn.txtOffID.focus();
		return false;
	} else if ((document.frmNewRegn.txtDate.value == "")
			|| (document.frmNewRegn.txtDate.value.length <= 0)) {
		alert("Please Enter Registration Date");
		document.frmNewRegn.txtDate.focus();
		return false;
	} else if ((document.frmNewRegn.txtResNo.value == "")
			|| (document.frmNewRegn.txtResNo.value.length <= 0)) {
		alert("Please Enter Registration Serial No.");
		document.frmNewRegn.txtResNo.focus();
		return false;
	}
	var txtOffID = document.frmNewRegn.txtOffID.value;
	var txtDate = document.frmNewRegn.txtDate.value;
	var txtResNo = document.frmNewRegn.txtResNo.value;

	// alert(strContractorId);
	var url = "../../../../../ReqRegn_Servlet.view?command=verify&txtOffID="
			+ txtOffID;
	url = url + "&txtDate=" + txtDate + "&txtResNo=" + txtResNo;
	//alert(url);
	var req = getTransport();
	//alert(url);
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processResponse(req);
	}
	req.send(null);

}

//FUNCTION TO AUTOGENERATE THE REQUEST SEQUENCE NUMBER
function getSeqNo() {
	url = "../../../../../ReqRegn_Servlet.view?command=Display";
	//alert(url);
	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processResponse(req);
	}
	req.send(null);
}

//XML RESPONSE RETURNED BY THE SERVLET FOR DISPLAYING THE AUTOGENERATED NUMBER
function displaySNO(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var seq = baseResponse.getElementsByTagName("SNO")[0].firstChild.nodeValue;
		//alert(seq);
		document.frmNewRegn.txtReqNo.value = seq;
		//document.frmNewRegn.txtOffID.focus();
	}
}

//XML RESPONSE RETURNED BY THE SERVLET FOR VERIFICATION OF CONTRACTOR ID
function verifyResp(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Given Registration Sl.No Id already Exist please Enter a new Id");
		document.frmNewRegn.txtResNo.value = ""
		document.frmNewRegn.txtResNo.focus();
	}

}

function nullCheck() {

	if ((document.frmNewRegn.txtOffID.value == "")
			|| (document.frmNewRegn.txtOffID.value.length <= 0)) {
		alert("Please Enter Office Id");
		document.frmNewRegn.txtOffID.focus();
		return false;
	} else if ((document.frmNewRegn.txtDate.value == "")
			|| (document.frmNewRegn.txtDate.value.length <= 0)) {
		alert("Please Enter Registration Date");
		document.frmNewRegn.txtDate.focus();
		return false;
	} else if ((document.frmNewRegn.txtResNo.value == "")
			|| (document.frmNewRegn.txtResNo.value.length <= 0)) {
		alert("Please Enter Registration Serial No.");
		document.frmNewRegn.txtResNo.focus();
		return false;
	}

	else if ((document.frmNewRegn.txtContName.value == "")
			|| (document.frmNewRegn.txtContName.value.length <= 0)) {
		alert("Please Enter Contractor Name/Firm");
		document.frmNewRegn.txtContName.focus();
		return false;
	}

	else if ((document.frmNewRegn.txtadd.value == "")
			|| (document.frmNewRegn.txtadd.value.length <= 0)) {
		alert("Please Enter Contractor Address");
		document.frmNewRegn.txtadd.focus();
		return false;
	}

	else if ((document.frmNewRegn.txtClass.value == "0")) {
		alert("Please Select Class Of Registration");
		document.frmNewRegn.txtClass.focus();
		return false;
	} else if ((document.frmNewRegn.txtDate_Upto.value == "")
			|| (document.frmNewRegn.txtDate_Upto.value.length <= 0)) {
		alert("Please Enter Registration Valid Date");
		document.frmNewRegn.txtDate_Upto.focus();
		return false;
	}

	/*  if(checkdate()==false)
	  {
	          return false;
	  }*/

	// alert('ok');
	return true;

}

function isInteger(param, e) {
	var nav4 = window.Event ? true : false;
	if (nav4) // Navigator 4.0x
		var whichCode = e.which
	else
		// Internet Explorer 4.0x
		var whichCode = e.keyCode
	if ((whichCode >= 48 && whichCode <= 57)
			|| (whichCode >= 97 && whichCode <= 105)) {
		return true;
	}
	var str = param.value;
	param.value = str.substring(0, str.length - 1);
	return false;
}

//function to validate the pincode            
function checkPincode() {

	if (isNaN(document.frmNewRegn.txtPincode.value)) {
		alert('Enter Pincode in Number');
		document.frmNewRegn.txtPincode.value = "";
		document.frmNewRegn.txtPincode.focus();
		return false;
	}
	return true;
}

/*function LoadNewReg()
 {

 window.close();
 my_window= window.open("../../../../../../../../../../PMS_ReqNewRegn.jsp","mywindow1","status=1,height=750,width=1000,resizable,scrollbars"); 
 my_window.moveTo(150,150);

 }
 function winClose()
 {
 window.open('','_parent','');
 window.close();
 }*/
/*function checkPhone()
 {

 if(isNaN(document.frmNewRegn.txtPhone.value)) 
 {
 alert('Enter the Phone Number');
 document.frmNewRegn.txtPhone.value="";
 document.frmNewRegn.txtPhone.focus();
 return false;
 }
 return true;
 }
 */

function getCurrentYear() {
	var year = new Date().getYear();
	if (year < 1900)
		year += 1900;
	return year;
}

function getCurrentMonth() {
	return new Date().getMonth() + 1;
}

function getCurrentDay() {
	return new Date().getDate();
}

function checkcurdt(t) {

	if (t.value.length == 0)
		return false;
	if (t.value.length == 10 && t.value.indexOf('/', 0) == 2
			&& t.value.indexOf('/', 3) == 5) {

		// var c=t.value.replace(/-/g,'/');
		var c = t.value;
		try {
			var f = DateFormat(t, c, event, true, '3');
		} catch (e) {
			//exception  start

			t.value = c;
			var sc = t.value.split('/');
			var currenDay = sc[0];
			var currentMonth = sc[1];
			var currentYear = sc[2];
			//alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
			if (currentYear > getCurrentYear()
					|| currentYear < _Service_Period_Beg_Year) {

				alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to ' + _Service_Period_Beg_Year);
				t.value = "";
				t.focus();
				return false;
			} else if (currentYear == getCurrentYear()) {
				if (currentMonth > getCurrentMonth()) {
					alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to ' + _Service_Period_Beg_Year);
					t.value = "";
					t.focus();
					return false;
				} else if (currentMonth == getCurrentMonth()) {
					if (currenDay > getCurrentDay()) {
						alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to ' + _Service_Period_Beg_Year);
						t.value = "";
						t.focus();
						return false;
					}
				}

			}

			t.value = c;
			if (err != 0) {
				t.value = "";
				return false;
			}
			return true;

			//exception end

		}
		if (f == true) {
			//alert(f);
			//t.value=c.replace(/\//g,'-');
			t.value = c;
			var sc = t.value.split('/');
			var currenDay = sc[0];
			var currentMonth = sc[1];
			var currentYear = sc[2];
			//alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

			if (currentYear > getCurrentYear()
					|| currentYear < _Service_Period_Beg_Year) {

				alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to ' + _Service_Period_Beg_Year);
				t.value = "";
				t.focus();
				return false;
			} else if (currentYear == getCurrentYear()) {
				if (currentMonth > getCurrentMonth()) {
					alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to ' + _Service_Period_Beg_Year);
					t.value = "";
					t.focus();
					return false;
				} else if (currentMonth == getCurrentMonth()) {
					if (currenDay > getCurrentDay()) {
						alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to ' + _Service_Period_Beg_Year);
						t.value = "";
						t.focus();
						return false;
					}
				}

			}

			t.value = c;

			return true;

		} else {
			if (err != 0) {
				t.value = "";
				return false;
			}
		}

	} else {
		alert('Date format  should be (dd-mm-yyyy)');
		t.value = "";
		//t.focus();
		return false
	}
	return true;

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


function checkdt(t) {

	if (t.value.length == 0)
		return false;
	if (t.value.length == 10 && t.value.indexOf('/', 0) == 2
			&& t.value.indexOf('/', 3) == 5) {

		// var c=t.value.replace(/-/g,'/');
		var c = t.value;
//		try {
//			var f = DateFormat(t, c, event, true, '3');
//		} catch (e) {
		
		///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
        try{
            var f=isValidDate(c);
           }
       catch(e){
		
			//exception  start

			t.value = c;
			var sc = t.value.split('/');
			var currenDay = sc[0];
			var currentMonth = sc[1];
			var currentYear = sc[2];
			//alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
			if (currentYear < 1970) {

				alert('Entered date should be greater than or equal to 1970');
				t.value = "";
				t.focus();
				return false;
			}

			t.value = c;
			if (err != 0) {
				t.value = "";
				return false;
			}
			return true;

			//exception end

		}
		if (f == true) {
			//alert(f);
			//t.value=c.replace(/\//g,'-');
			t.value = c;
			var sc = t.value.split('/');
			var currenDay = sc[0];
			var currentMonth = sc[1];
			var currentYear = sc[2];
			//alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

			if (currentYear < 1970) {

				alert('Entered date should be greater than or equal to 1970');
				t.value = "";
				t.focus();
				return false;
			}

			t.value = c;

			return true;

		} else {
			if (err != 0) {
				t.value = "";
				return false;
			}
		}

	} else {
		alert('Date format  should be (dd/mm/yyyy)');
		t.value = "";
		//t.focus();
		return false
	}

}

function calins(e, t) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	//alert(unicode);
	//if(unicode !=8)
	if (t.value.length == 2 || t.value.length == 5)
		t.value = t.value + '/';
	if (unicode != 8 && unicode != 9 && unicode != 37 && unicode != 39) {

		if (unicode < 48 || unicode > 57)
			return false
	}

}

//////////////   FOR JOB POPUP WINDOW //////////////////////
var winjob;

function jobpopup() {
	jobflag = true;

	if (winjob && winjob.open && !winjob.closed) {
		winjob.resizeTo(500, 600);
		winjob.moveTo(200, 200);
		winjob.focus();
		return;
	} else {
		winjob = null
	}

	winjob = window.open(
			"../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp",
			"JobSearch_for_SR",
			"status=1,height=500,width=600,resizable=YES, scrollbars=yes");
	winjob.moveTo(200, 200);

	winjob.focus();

}
/*
 function forChildOption()
 {

 if (winjob && winjob.open && !winjob.closed)
 {

 winjob.officeSelection(true,true,true,false);
 }

 }*/

function doParentJob(jobid, deptid) {

	// document.frmCurrentPosting.txtDept_Id_work.value=deptid;
	document.frmNewRegn.txtOffID.value = jobid;
	getOfficeDetails();
	return true

}

function numbersonly2(e, t) {
	// alert('hai');
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {
			t.blur();
		} catch (e) {
		}
		//document.frmCurrentPosting.txtSNo.focus();
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false
	}
}

function checkdate() {
	//alert('check');
	var fromdt = document.frmNewRegn.txtDate.value;
	var todt = document.frmNewRegn.txtDate_Upto.value;

	var frm = fromdt.split('/');
	var to = todt.split('/');

	var fday = frm[0];
	var fmon = frm[1];
	var fyear = frm[2];

	var tday = to[0];
	var tmon = to[1];
	var tyear = to[2];

	if (fyear > tyear) {
		alert('Registration Date should be less than valid Upto Date');
		//document.HRE_EmployeeServiceDetails.txtDateTo.focus();
		return false;
	} else if (fyear == tyear) {
		if (fmon > tmon) {
			alert('Registration Date should be less than valid Upto Date');
			//document.HRE_EmployeeServiceDetails.txtDateTo.focus();
			return false;
		} else if (fmon == tmon) {
			if (fday > tday) {
				alert('Registration Date should be less than valid Upto Date');
				// document.HRE_EmployeeServiceDetails.txtDateTo.focus();
				return false;
			}

		}
	}
	return true;

}

function chRegDate() {
	if ((document.frmNewRegn.txtOffID.value == "")
			|| (document.frmNewRegn.txtOffID.value.length <= 0)) {
		alert("Please Enter Office Id");
		document.frmNewRegn.txtOffID.focus();
		return false;
	} else if ((document.frmNewRegn.txtDate.value == "")
			|| (document.frmNewRegn.txtDate.value.length <= 0)) {
		alert("Please Enter Registration Date");
		document.frmNewRegn.txtDate.focus();
		return false;
	}
	return true;
}

function chkOffice() {
	if ((document.frmNewRegn.txtOffID.value == "")
			|| (document.frmNewRegn.txtOffID.value.length <= 0)) {
		alert("Please Enter Office Id");
		document.frmNewRegn.txtOffID.focus();
		return false;
	}
	return true;

}

function regvalidupto() {
	var fromdt = document.frmNewRegn.txtDate.value;

	var frm = fromdt.split('/');

	var fday = frm[0];
	var fmon = frm[1];
	var fyear = frm[2];

	if (fyear % 4 == 0 || (fyear % 100 == 0 && fyear % 400 == 0)) {
		if (fmon == 2) {
			if (fday == 29) {
				document.frmNewRegn.txtDate_Upto.value = (parseInt(fday) - 1)
						+ '/' + fmon + '/' + (parseInt(fyear) + 1);
			}
		}
	} else {
		document.frmNewRegn.txtDate_Upto.value = fday + '/' + fmon + '/'
				+ (parseInt(fyear) + 1);
	}
	// alert('test');
	return true;

}

// changes...

function EmailCheck() {
	if ((document.frmNewRegn.txtEmail.value.length != 0)) {
		var x = document.frmNewRegn.txtEmail.value;
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(x)) {
			alert('Enter correct email address');
			document.frmNewRegn.txtEmail.value = "";
			document.frmNewRegn.txtEmail.focus();
			return false;
		}
	}
	return true;
}

function Load_Edit_verifyRegID() {

	if ((document.frmNewRegn.txtOffID.value == "")
			|| (document.frmNewRegn.txtOffID.value.length <= 0)) {
		alert("Please Enter Office Id");
		document.frmNewRegn.txtOffID.focus();
		return false;
	} else if ((document.frmNewRegn.txtDate.value == "")
			|| (document.frmNewRegn.txtDate.value.length <= 0)) {
		alert("Please Enter Registration Date");
		document.frmNewRegn.txtDate.focus();
		return false;
	} else if ((document.frmNewRegn.txtResNo.value == "")
			|| (document.frmNewRegn.txtResNo.value.length <= 0)) {
		alert("Please Enter Registration Serial No.");
		document.frmNewRegn.txtResNo.focus();
		return false;
	}
	var txtOffID = document.frmNewRegn.txtOffID.value;
	var txtDate = document.frmNewRegn.txtDate.value;
	var txtResNo = document.frmNewRegn.txtResNo.value;

	// alert(strContractorId);
	var url = "../../../../../ReqRegn_Servlet.view?command=Load_Edit_verifyRegID&txtOffID="
			+ txtOffID;
	url = url + "&txtDate=" + txtDate + "&txtResNo=" + txtResNo;
	//alert(url);
	var req = getTransport();
	//alert(url);
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		processLoad_Edit_verifyRegID(req);
	}
	req.send(null);

}

function processLoad_Edit_verifyRegID(req) {
	// code for processing the xml returned by servlet  
	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
			if (command == 'Load_Edit_verifyRegID') {
				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if (flag == "success") {
					//alert("Given Registration Sl.No Id already Exist please Enter a new Id");
					//document.frmNewRegn.txtResNo.value=""
					//document.frmNewRegn.txtResNo.focus();
					document.frmNewRegn.txtOffID.readOnly = true;
					document.frmNewRegn.txtDate.readOnly = true;
					document.frmNewRegn.txtResNo.readOnly = true;

					document.frmNewRegn.txtContID.value = baseResponse
							.getElementsByTagName("CONTRACTOR_ID")[0].firstChild.nodeValue;
					document.frmNewRegn.txtContName.value = baseResponse
							.getElementsByTagName("CONTRACTOR_NAME")[0].firstChild.nodeValue;
					document.frmNewRegn.txtadd.value = baseResponse
							.getElementsByTagName("ADDRESS")[0].firstChild.nodeValue;

					if (baseResponse.getElementsByTagName("PHONE")[0].firstChild.nodeValue != "null") {
						document.frmNewRegn.txtPhone.value = baseResponse
								.getElementsByTagName("PHONE")[0].firstChild.nodeValue;
					}
					if (baseResponse.getElementsByTagName("EMAIL")[0].firstChild.nodeValue != "null") {
						document.frmNewRegn.txtEmail.value = baseResponse
								.getElementsByTagName("EMAIL")[0].firstChild.nodeValue;
					}
					document.frmNewRegn.txtClass.value = baseResponse
							.getElementsByTagName("REGN_CLASS_ID")[0].firstChild.nodeValue;
					if (baseResponse
							.getElementsByTagName("REGN_STATE_COVERAGE")[0].firstChild.nodeValue == "Y")
						document.frmNewRegn.State[0].checked = true;
					else
						document.frmNewRegn.State[1].checked = true;

					if (baseResponse
							.getElementsByTagName("REGN_VALID_UPTO_VALUE")[0].firstChild.nodeValue != "null")
						document.frmNewRegn.txtDate_Upto.value = baseResponse
								.getElementsByTagName("REGN_VALID_UPTO_VALUE")[0].firstChild.nodeValue;
					if (baseResponse.getElementsByTagName("REGN_ALIAS_CODE")[0].firstChild.nodeValue != "null")
						document.frmNewRegn.txtAlias.value = baseResponse
								.getElementsByTagName("REGN_ALIAS_CODE")[0].firstChild.nodeValue;
					if (baseResponse.getElementsByTagName("JURISDICTION")[0].firstChild.nodeValue != "null")
						document.frmNewRegn.cmbjurisdiction.value = baseResponse
								.getElementsByTagName("JURISDICTION")[0].firstChild.nodeValue;

				} else {
					alert("Contractor doesn't exist");
					document.frmNewRegn.txtResNo.value = "";
				}
			}
		}
	}
}

function ListAllBudget() {

	//alert("comes inside");
	var offid = document.getElementById("txtOffID").value;	
	//alert("after this");
	//alert(offid);
	if (winListAllBudget && winListAllBudget.open && !winListAllBudget.closed) {
		winListAllBudget.resizeTo(500, 500);
		winListAllBudget.moveTo(250, 250);
		winListAllBudget.focus();
	} else {
		winListAllBudget = null
	}
	if (offid == 5000) {
		var cmbWing = document.getElementById("cmbWing").value;
		//alert(cmbWing);
		winListAllBudget = window
		/*.open(
				"../../../../../org/PMS/PMS1/ContractorsInfoSys/jsps/ListAllContractorsJSP.jsp?param="
						+ offid + "&cmbWing=" + cmbWing, "ListAllAccounting",
				"status=1,height=500,width=500,resizable=YES, scrollbars=yes");
				
				
				*/
		.open(
				"../../../../../org/FAS/FAS1/ContractorsInfoSys/jsps/ListAllContractorsJSP.jsp?param="
						+ offid + "&cmbWing=" + cmbWing, "ListAllAccounting",
				"status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	}else{
		winListAllBudget = window
		/*.open(
				"../../../../../org/PMS/PMS1/ContractorsInfoSys/jsps/ListAllContractorsJSP.jsp?param="
						+ offid, "ListAllAccounting",
				"status=1,height=500,width=500,resizable=YES, scrollbars=yes");*/
		.open(
				"../../../../../org/FAS/FAS1/ContractorsInfoSys/jsps/ListAllContractorsJSP.jsp?param="
						+ offid, "ListAllAccounting",
				"status=1,height=500,width=500,resizable=YES, scrollbars=yes");
	}
	
	winListAllBudget.moveTo(250, 250);
	winListAllBudget.focus();

}

window.onunload = function() {
	//if (winemp && winemp.open && !winemp.closed) winemp.close();
	if (winjob && winjob.open && !winjob.closed)
		winjob.close();
	if (winjob1 && winjob1.open && !winjob1.closed)
		winjob1.close();
	if (winListAllBudget && winListAllBudget.open && !winListAllBudget.closed)
		winListAllBudget.close();
}

function List(contratco_name, address, reg_slno, reg_date, class_id, phone,
		email, juris, contid, date_upto, length, statewise_coverage,
		region_alias_code, status, wing_id) {

	var d = document.getElementById("cmdUpdate");
	d.style.display = "block";
	var d2 = document.getElementById("cmdDelete");
	d2.style.display = "block";
	var d1 = document.getElementById("cmdSubmit");
	d1.style.display = "none";
	document.frmNewRegn.txtContID.value = contid;
	document.frmNewRegn.txtContName.value = contratco_name;
	document.frmNewRegn.txtadd.value = address;
	document.frmNewRegn.txtResNo.value = reg_slno;
	document.frmNewRegn.txtDate.value = reg_date;

	if ((wing_id == "null") || (wing_id == "0")) {
		document.frmNewRegn.cmbWing.value = "";
	} else {
		document.frmNewRegn.cmbWing.value = wing_id;
	}
	
	if (phone == "null") {
		document.frmNewRegn.txtPhone.value = " ";
	} else {
		document.frmNewRegn.txtPhone.value = phone;
	}

	if (email == "null") {
		document.frmNewRegn.txtEmail.value = "";
	} else {
		document.frmNewRegn.txtEmail.value = email;
	}

	if (statewise_coverage == "Y") {
		document.frmNewRegn.State[0].checked = true;
		document.frmNewRegn.State[1].checked = false;
	} else if (statewise_coverage == "N") {
		document.frmNewRegn.State[0].checked = false;
		document.frmNewRegn.State[1].checked = true;
	}

	if (region_alias_code == "null") {
		document.frmNewRegn.txtAlias.value = "";
	} else {
		document.frmNewRegn.txtAlias.value = region_alias_code;
	}

	if (status == "L") {
		document.frmNewRegn.txtstatus[0].checked = true;
		document.frmNewRegn.txtstatus[1].checked = false;
	} else if (status == "C") {
		document.frmNewRegn.txtstatus[0].checked = false;
		document.frmNewRegn.txtstatus[1].checked = true;
	}

	// alert('ok');
	document.frmNewRegn.txtClass.value = class_id;
	document.frmNewRegn.cmbjurisdiction.value = juris;
	document.frmNewRegn.txtDate_Upto.value = date_upto;
	// alert('after ok');

	/*  var url="../../../../../AccountingUnitServlet.con?command=AccountUnit&AccountUnitId="+accountingunitid;
	  
	  var req=getTransport();
	  req.open("Post",url,true); 
	  req.onreadystatechange=function()
	      {
	         LoadValuesResponse(req);
	      }   
	       req.send(null);
	  
	  document.frmAccountUnit.txtHAccountid.value=accountingunitid;*/

}

function LoadValuesResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {

			var response = req.responseXML.getElementsByTagName("response")[0];
			var flag = response.getElementsByTagName("flag")[0].firstChild.nodeValue;
			var tbody = document.getElementById("tblList");
			var table = document.getElementById("Existing");

			var t = 0;
			for (t = tbody.rows.length - 1; t >= 0; t--) {
				tbody.deleteRow(0);
			}
			var options = response.getElementsByTagName("leng");
			for ( var i = 0; i < options.length; i++) {
				var mycurrent_row = document.createElement("TR");
				var cell1 = document.createElement("TD");
				var cell2 = document.createElement("TD");
				var cell3 = document.createElement("TD");
				var cell4 = document.createElement("TD");
				var cell5 = document.createElement("TD");

				var txtoffice = response.getElementsByTagName("accountid")[0].firstChild.nodeValue;
				var txtunit = response.getElementsByTagName("unitname")[0].firstChild.nodeValue;
				//  alert("txtunit"+txtunit);
				var accoffice = response.getElementsByTagName("accofficeid")[0].firstChild.nodeValue;
				var officenameL = response.getElementsByTagName("officenameL")[0].firstChild.nodeValue;
				var accountforofficeid = response
						.getElementsByTagName("accountforofficeid")[i].firstChild.nodeValue;
				var officename = response.getElementsByTagName("officename")[i].firstChild.nodeValue;

				document.frmAccountUnit.txtLOffice.value = accoffice;
				document.frmAccountUnit.txtLofficename.value = officenameL;
				if (accoffice == accountforofficeid) {
					var anc = document.createElement("A");
					mycurrent_row.id = i;
					var url = "javascript:loadValuesFromTable('" + i + "')";
					//anc.href=url;
					var txtedit = document.createTextNode("Edit");
					anc.appendChild(txtedit);
					cell1.appendChild(anc);
				} else {
					var anc = document.createElement("A");
					mycurrent_row.id = i;
					var url = "javascript:loadValuesFromTable('" + i + "')";
					anc.href = url;
					var txtedit = document.createTextNode("Edit");
					anc.appendChild(txtedit);
					cell1.appendChild(anc);

				}
				mycurrent_row.appendChild(cell1);

				txtoffice1 = document.createTextNode(accountforofficeid);
				cell2.appendChild(txtoffice1);
				mycurrent_row.appendChild(cell2);
				var hidden = document.createElement("input");
				hidden.type = "hidden";
				hidden.name = "officeid";
				hidden.value = accountforofficeid;
				cell2.appendChild(hidden);
				mycurrent_row.appendChild(cell2);

				txtunit1 = document.createTextNode(officename);
				cell3.appendChild(txtunit1);
				mycurrent_row.appendChild(cell3);
				tbody.appendChild(mycurrent_row);

			}
		}
	}

}

function doFunction(Command, param) {

	if (Command == "Add") {
		var flag = nullCheck();
		if (flag == true) {
			//alert("Add");

			var url = "../../../../../NewReqRegn_Servlet.view?command=Add";
			//      alert(url);
			document.frmNewRegn.action = url;
			document.frmNewRegn.method = "Post";
			document.frmNewRegn.submit();
		}
	} else if (Command == "Update") {
		var flag = nullCheck();
		if (flag == true) {

			var url = "../../../../../NewReqRegn_Servlet.view?command=Update";
			//       alert(url);
			document.frmNewRegn.action = url;
			document.frmNewRegn.method = "Post";
			document.frmNewRegn.submit();
		}
	} else if (Command == "Cancel") {
		//alert("delete");

		var url = "../../../../../NewReqRegn_Servlet.view?command=Cancel";
		//       alert(url);
		document.frmNewRegn.action = url;
		document.frmNewRegn.method = "Post";
		document.frmNewRegn.submit();

	}

}

function nullCheck() {
	var office = document.frmNewRegn.txtOffID.value;
	if (office == 5000) {
		if (document.frmNewRegn.cmbWing.value == "") {
			alert("Please Select Wing");
			document.frmNewRegn.cmbWing.focus();
			return false;
		} else if (document.frmNewRegn.txtDate.value == "") {
			alert("Please Enter Registration Date");
			document.frmNewRegn.txtDate.focus();
			return false;
		} else if (document.frmNewRegn.txtResNo.value == "") {
			alert("Please Enter Registration Sl.No");
			document.frmNewRegn.txtResNo.focus();
			return false;
		}else if (document.frmNewRegn.txtContName.value == "") {
			alert("Please Enter Name of the Contractor/Firm");
			document.frmNewRegn.txtContName.focus();
			return false;
		} else if (document.frmNewRegn.txtadd.value == "") {
			alert("Please Enter Address");
			document.frmNewRegn.txtadd.focus();
			return false;
		} else if (document.frmNewRegn.txtPhone.value == "") {
			alert("Please Enter Phone");
			document.frmNewRegn.txtPhone.focus();
			return false;
		} else if (document.frmNewRegn.htxtClass.value == "0") {
			alert("Please Enter Class Of Registration");
			document.frmNewRegn.htxtClass.focus();
			return false;
		} else if (document.frmNewRegn.cmbjurisdiction.value == "0") {
			alert("Please Select cmbjurisdiction");
			document.frmNewRegn.cmbjurisdiction.focus();
			return false;
		} else if (document.frmNewRegn.txtDate_Upto.value == "") {
			alert("Please Enter Registration Reneval due on");
			document.frmNewRegn.txtDate_Upto.focus();
			return false;
		} else if (document.frmNewRegn.txtAlias.value == "") {
			alert("Please Enter Old Registration Code");
			document.frmNewRegn.txtAlias.focus();
			return false;
		}
	} else {
		if (document.frmNewRegn.txtDate.value == "") {
			alert("Please Enter Registration Date");
			document.frmNewRegn.txtDate.focus();
			return false;
		} else if (document.frmNewRegn.txtResNo.value == "") {
			alert("Please Enter Registration Sl.No");
			document.frmNewRegn.txtResNo.focus();
			return false;
		} 
//                else if (document.frmNewRegn.txtContID.value == "") {
//			alert("Please Enter Contractor ID");
//			document.frmNewRegn.txtContID.focus();
//			return false;
//		} 
                else if (document.frmNewRegn.txtContName.value == "") {
			alert("Please Enter Name of the Contractor/Firm");
			document.frmNewRegn.txtContName.focus();
			return false;
		} else if (document.frmNewRegn.txtadd.value == "") {
			alert("Please Enter Address");
			document.frmNewRegn.txtadd.focus();
			return false;
		} else if (document.frmNewRegn.txtPhone.value == "") {
			alert("Please Enter Phone");
			document.frmNewRegn.txtPhone.focus();
			return false;
		} else if (document.frmNewRegn.htxtClass.value == "0") {
			alert("Please Enter Class Of Registration");
			document.frmNewRegn.htxtClass.focus();
			return false;
		} else if (document.frmNewRegn.cmbjurisdiction.value == "") {
			alert("Please Select cmbjurisdiction");
			document.frmNewRegn.cmbjurisdiction.focus();
			return false;
		} else if (document.frmNewRegn.txtDate_Upto.value == "") {
			alert("Please Enter Registration Reneval due on");
			document.frmNewRegn.txtDate_Upto.focus();
			return false;
		} else if (document.frmNewRegn.txtAlias.value == "") {
			alert("Please Enter Old Registration Code");
			document.frmNewRegn.txtAlias.focus();
			return false;
		}
	}
	return true;
}
