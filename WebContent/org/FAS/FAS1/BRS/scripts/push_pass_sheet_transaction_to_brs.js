var seq=0;
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
			if (command == "do1") {
				dofunc(baseResponse);
			}
			if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			}
			if (command == "loadGrid") {
				loadGrid_new(baseResponse);
			}
			if (command == "ob_freeze") {
                
				obFreeze(baseResponse);
			}
		}
	}
}

function obFreeze(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if (flag == "success") {
    	//loadGrid();
    	LoadMonthYear();
    	
    
    }
    else 
    {
    	var tbody=document.getElementById("grid_body_TWAD");
        try{tbody.innerHTML="";}
    catch(e) {tbody.innerText="";}
    	alert("BRS OB is Not Freezed");
//        document.getElementById("subdivid").style.display="none";//none
//        document.getElementById("exitdivid").style.display="block";//block
    } 
}

function LoadMonthYear() {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	var url = "../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);

}
function LoadMonthYear1(baseResponse) {
	var txtCB_Year1 = document.getElementById("txtCB_Year").value;
	var txtCB_Month1 = document.getElementById("txtCB_Month").value;
	txtCB_Year = parseInt(txtCB_Year1);
	txtCB_Month = parseInt(txtCB_Month1);
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		CB_Year = parseInt(CB_Year1);
		//yess
		CB_Month = parseInt(CB_Month1);
		
		document.getElementById("txtCB_Year").value=CB_Year;
		//document.getElementById("txtCB_Year").value=2012;
		document.getElementById("txtCB_Year").readOnly="true";
			//yes change  Lachu
			document.getElementById("txtCB_Month").value=CB_Month;
			// document.getElementById("txtCB_Month").value=2;
		
		document.getElementById("txtCB_Month").setAttribute('readonly','readonly');
		
      
       closeBRS("/twadFas");
		
	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
		document.getElementById("txtPBBalance").value = "";
		document.getElementById("pbReadonly").value = "";
	} else {
		alert("Failed to Load Month and Year");
		document.getElementById("txtCB_Year").value = "";
		document.getElementById("txtCB_Month").value = "s";
		document.getElementById("txtPBBalance").value = "";
		document.getElementById("pbReadonly").value = "";
	}
}  
function doo(path) {
	    //alert(path);	    
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		var accnotype = document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
		var spltype=accnotype.split("-");
		if (txtCB_Year == ""){
			alert("Enter Cash Book Year in the Field");
			document.frmBRSList.txtCB_Year.focus();
		}else if (txtCB_Month == ""){
			alert("Enter Cash Book Month in the Field");
			document.frmBRSList.txtCB_Month.focus();
		}else if (cmbBankAccNo == ""){
			alert("Enter Bank Account No in the Field");
			document.frmBRSList.cmbBankAccNo.focus();
		} else {

			var url = path + "/Push_from_NT_OB_to_BRS_Tran?command=do1&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo+"&acctype="+spltype[0];

			//alert(url);
			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);

			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
			xmlrequest.send(null);
		}
	}

function dofunc(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	if (flag == "success") {
//			var tbody=document.getElementById("grid_body_TWAD");
//		      try{tbody.innerHTML="";}
//		  catch(e) {tbody.innerText="";} 
			alert("Datas are Moved from  Pass Sheet Transactions to BRS Transaction Successfully");
//			document.getElementById("subdivid").style.display="none";
//       	 document.getElementById("exitdivid").style.display="block";
       	 document.getElementById("txtCB_Month").value=s;
       	document.getElementById("cmbBankAccNo").value="";
	}
	else if(flag == "alreadyExistsinBRS")
		{
		alert("The Given Datas are Already Pushed into BRS");
		}
	else if(flag == "alreadyExistsinBRSNoEntry")
		{
		alert("The Given Datas are Already Pushed into BRS NoEntry");
		}
	else if(flag == "NoData"){
		alert("No Datas Found to Push");
	}else{
		alert("Data Transfer Failed");
	}
	var today= new Date(); 
    var day=today.getDate();
    var month=today.getMonth();
    month=month+1;
    var year=today.getYear();
    if(year < 1900) year += 1900;
               
   document.frmPush_from_OB_to_BRS_Tran.txtCB_Year.value=year;
   document.frmPush_from_OB_to_BRS_Tran.txtCB_Month.value=month;
   document.getElementById("cmbBankAccNo").value="";
   LoadAccountingUnitID('LIST_ALL_UNITS');
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
			return false
	}
}

function brs_ob_status()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;

	var url ="../../../../../BRS_OB_Create?Command=ob_freeze&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbBankAccNo=" + cmbBankAccNo;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("GET", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
}

function loadGrid()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_FollowUp.txtCB_Year.focus();
	}
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_FollowUp.txtCB_Month.focus();
	}
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (cmbBankAccNo == "") {
		alert("Choose BankAccNo");
		
	}
	else{
		var url ="../../../../../Push_from_OB_to_BRS_Tran?command=loadGrid&cmbAcc_UnitCode="
		+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
		+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
		+ "&cmbBankAccNo=" + cmbBankAccNo;

			var xmlrequest = AjaxFunction();
			
			xmlrequest.open("POST", url, true);
			
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
			xmlrequest.send(null);
	}
}


function loadGrid_new(baseResponse)
    {
             var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           var incr=0;
             if(flag=="success"){
//            	 document.getElementById("subdivid").style.display="block";
//            	 document.getElementById("exitdivid").style.display="none";
            	   var count=baseResponse.getElementsByTagName("year");
                 var tbody=document.getElementById("grid_body_TWAD");
                 try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";}               
                 for(var i=0;i<count.length;i++)
                 {              
                	 var withdrawldate;
                         var year=baseResponse.getElementsByTagName("year")[i].firstChild.nodeValue;
                         var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
                         var entrydate=baseResponse.getElementsByTagName("entrydate")[i].firstChild.nodeValue;
                         var twadtype=baseResponse.getElementsByTagName("twadtype")[i].firstChild.nodeValue;
                         
                         var remdate=baseResponse.getElementsByTagName("remdate")[i].firstChild.nodeValue;
                         var withdate=baseResponse.getElementsByTagName("withdrawldate")[i].firstChild.nodeValue;
                         var vou_chall_no=baseResponse.getElementsByTagName("vou_chall_no")[i].firstChild.nodeValue;
                         var chequeno=baseResponse.getElementsByTagName("chequeno")[i].firstChild.nodeValue;
                         var cramt=baseResponse.getElementsByTagName("cramt")[i].firstChild.nodeValue;
                         
                         var dramt=baseResponse.getElementsByTagName("dramt")[i].firstChild.nodeValue;
                         var entrypass=baseResponse.getElementsByTagName("entrypass")[i].firstChild.nodeValue;
                         var passdate=baseResponse.getElementsByTagName("passdate")[i].firstChild.nodeValue;
                         var amtinpass=baseResponse.getElementsByTagName("amtinpass")[i].firstChild.nodeValue;
                         var difference=baseResponse.getElementsByTagName("difference")[i].firstChild.nodeValue;
                         var docno=baseResponse.getElementsByTagName("docno")[i].firstChild.nodeValue;
                         
                        // alert(withdate);
                        if(withdate=="null")
                         {
                        	withdrawldate="-";
                         }
                         else 
                         {
                        	 withdrawldate=withdate;
                         } 
                        if(remdate=="null")
                        {
                       	remittancedate="-";
                        }
                        else 
                        {
                        	remittancedate=remdate;
                        } 
                         
                         
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=seq;
                    
                         var cell2=document.createElement("TD");
                         cell2.align="center";
                         var currentText1=document.createTextNode(year+" / "+month);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                    
                         var cell2=document.createElement("TD"); 
                         cell2.align="center";
                         var currentText1=document.createTextNode(entrydate);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
             
                         var cell2=document.createElement("TD");  
                         cell2.align="center";
                         var currentText1=document.createTextNode(twadtype);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD"); 
                         cell2.align="center";
                         var currentText1=document.createTextNode(remittancedate);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");
                         cell2.align="center";
                         var currentText1=document.createTextNode(withdrawldate);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");
                         cell2.align="center";
                         var currentText1=document.createTextNode(vou_chall_no);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");
                         cell2.align="center";
                         var currentText1=document.createTextNode(chequeno);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                         var cell2=document.createElement("TD");
                         cell2.align="right";
                         var currentText1=document.createTextNode(cramt);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                         var cell2=document.createElement("TD");
                         cell2.align="right";
                         var currentText1=document.createTextNode(dramt);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                     /*    var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(entrypass);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(passdate);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(amtinpass);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(difference);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(docno);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);  */
                         
                         tbody.appendChild(mycurrent_row);
                         seq++;	                                          
                 }
                
	               
                 }
                 else
                 {
              
                 var tbody=document.getElementById("grid_body_TWAD");
                 try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";} 
             alert("No Record Exist");
             
//             document.getElementById("subdivid").style.display="none";
//        	 document.getElementById("exitdivid").style.display="block";
                 
                 }
    }

function refresh() {
		    
		    var today= new Date(); 
	        var day=today.getDate();
	        var month=today.getMonth();
	        month=month+1;
	        var year=today.getYear();
	        if(year < 1900) year += 1900;
	                   
	       document.frmPush_from_OB_to_BRS_Tran.txtCB_Year.value=year;
	       document.frmPush_from_OB_to_BRS_Tran.txtCB_Month.value=month;
	       document.getElementById("cmbBankAccNo").value="";
	       LoadAccountingUnitID('LIST_ALL_UNITS');
}

function clr() {
	//alert("clear");
	  var tbody=document.getElementById("grid_body_TWAD");
      try{tbody.innerHTML="";}
  catch(e) {tbody.innerText="";} 
}

function exit() {
	window.close();
}