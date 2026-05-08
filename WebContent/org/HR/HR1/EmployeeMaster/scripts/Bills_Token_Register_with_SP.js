//			Bills_Token_Register_with_SP			//

//alert("sgfg");

var acc_head_code="";
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
			
			if (command == "getProceedingNo") {
				getProceedingNo_res(baseResponse);
			}
//			
			else if (command == "Acc_Head_Code") {
				 //alert("manipulate");
				Acc_Head_Code11(baseResponse);
			}
			else if (command == "getBillMajorType") {
				 //alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "getBillMinorType") {
				// alert("manipulate");
				getBillMinorType1(baseResponse);
			} else if (command == "getBillsubType") {
				// getEstimateSanctionDate
				// alert("manipulate");
				getBillsubType1(baseResponse);
			} else if (command == "calculateBudget") {
				// alert("manipulate");
				calculateBudget1(baseResponse);
			} else if (command == "getOffice") {
				// alert("manipulate");
				getOffice1(baseResponse);
			} else if (command == "saveFunc") {
				 //alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "Edit") {
				// alert("manipulate");
				Edit1(baseResponse);
			} else if (command == "deleted") {
				// alert("manipulate");
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "ClearAll") {
				ClearAll1(baseResponse);
			} else if (command == "IVno") {
				IVno1(baseResponse);
			} else if (command == "InvoiceDetails") {
				InvoiceDetails1(baseResponse);
			}
			
			else if (command == "getProceedingDetails") {
				getProceedingDetails_res(baseResponse);
			}
			else if (command == "loadHead") {
				loadHead_res(baseResponse);
			}
			else if (command == "getAccDesc") {
				getAccDesc_res(baseResponse);
			}
			else if (command == "budgetProv") {
				budgetProv_res(baseResponse);
			}
			
		}
	}
}





function initialLoad(path) {
	//alert(path);

	var url = path
			+ "/Bills_Token_Register_with_SP?command=getBillMajorType";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function getOffice(path) {
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var url = path
			+ "/Bills_Token_Register_with_SP?command=getOffice&txtEmpID_mas="
			+ txtEmpID_mas;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function loadProceedingNo(path)
{
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}
	var url = path+ "/Bills_Token_Register_with_SP?command=getProceedingNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
function numbersonly1(e, t) {
	//alert("t.length "+t.value.length);
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
function calins(e,t) {
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
         
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false; 
        }
}

function loadAccountHeadDesc(path)
{
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if(txtAcc_HeadCode=="0")
		{
		alert("Select Account HeadCode");
		return false;
		}
	var url = path+ "/Bills_Token_Register_with_SP?command=getAccDesc&txtAcc_HeadCode="+txtAcc_HeadCode;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function checkdt1(t)
{

   if(t.value.length==0)
       return false;
   if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
   {


       // var c=t.value.replace(/-/g,'/');
        var c=t.value;
       try{
       var f=DateFormat(t,c,event,true,'3');
       }catch(e){
       //exception  start

        t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
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
           //alert(f);
           //t.value=c.replace(/\//g,'-');
           t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
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
           return false
   }

}
function loadprocDetails(path)
{
	//alert("%%%%%%");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}
	


    var combo1 = document.getElementById("txtProceedingNo");
    var txtProceedingNo = combo1.options[combo1.selectedIndex].text;


	
	
	//var txtProceedingNo = document.getElementById("txtProceedingNo").value;
	//alert(txtProceedingNo);
	if(txtProceedingNo=="")
		{
		alert("Choose Proceeding No");
		return false;
		}
	var url = path+ "/Bills_Token_Register_with_SP?command=getProceedingDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+
	"&cboBillSubType="+cboBillSubType+"&txtProceedingNo="+txtProceedingNo;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);	
}

function getOffice1(baseResponse) {
	document.getElementById("cboOffice").length = 1;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
}


function Acc_Head_Code11(baseResponse)
{
	
	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if(flag=="success")
		{
	//	alert(count);
		for ( var i = 0; i < count; i++) {
			
			var Acc_Head_Code = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
			//alert("*****/////==== "+Acc_Head_Code);
			var combo = document.getElementById("txtAcc_HeadCode");
			var option = document.createElement("OPTION");
			option.text = Acc_Head_Code;
		    option.value = Acc_Head_Code;
		    try {
		        combo.add(option, null); //Standard
		    }catch(error) {
		        combo.add(option); // IE only
		    }
			
		}
		}
}
function getProceedingNo_res(baseResponse)
{
	var k_count=0;
	document.getElementById("txtProceedingNo").length = 1;
	var len1 = baseResponse.getElementsByTagName("procNo").length;
	for ( var i = 0; i < len1; i++) {
		var procNo = baseResponse.getElementsByTagName("procNo")[i].firstChild.nodeValue;
		var procId = baseResponse.getElementsByTagName("BILL_SUB_TYPE_CODE")[i].firstChild.nodeValue;
		var se = document.getElementById("txtProceedingNo");
		var op = document.createElement("OPTION");
		op.value = procId;
		var txt = document.createTextNode(procNo);
		op.appendChild(txt);
		se.appendChild(op);
		k_count++;
	}
	if(k_count>0)
	{
		var cboBillMajorType = document.getElementById("cboBillMajorType").value;
		if(cboBillMajorType=="0")
			{
			alert("Choose Major Type");
			return false;
			}
		var cboBillMinorType = document.getElementById("cboBillMinorType").value;
		if(cboBillMinorType=="0")
			{
			alert("Choose Minor Type");
			return false;
			}
		var cboBillSubType = document.getElementById("cboBillSubType").value;
		if(cboBillSubType=="0")
			{
			alert("Choose Sub Type");
			return false;
			}
//		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
//		var url = "../../../../../Bills_Token_Register_with_SP?command=Acc_Head_Code&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
//		// alert(url);
//		var xmlrequest = AjaxFunction();
//		xmlrequest.open("POST", url, true);
//		xmlrequest.onreadystatechange = function() {
//			manipulate(xmlrequest);
//		}
//
//		xmlrequest.send(null);
  	}
}

function loadHead_res(baseResponse)
{
	document.getElementById("txtAcc_HeadCode").length=0;
	var len1 = baseResponse.getElementsByTagName("acchead").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("acchead")[i].firstChild.nodeValue;
		 //alert(empName);
		var se = document.getElementById("txtAcc_HeadCode");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}
	var budgetAllo = baseResponse.getElementsByTagName("budgetAllo")[0].firstChild.nodeValue;
	var spent = baseResponse.getElementsByTagName("spent")[0].firstChild.nodeValue;
	var finttl = baseResponse.getElementsByTagName("finttl")[0].firstChild.nodeValue;
	var head_desc = baseResponse.getElementsByTagName("head_desc")[0].firstChild.nodeValue;
	
	
	//document.getElementById("txtBudgetProvision").value=budgetAllo;
	//document.getElementById("txtBudgetSpent").value=spent;
	document.getElementById("budAvail").value=finttl;
	document.getElementById("txtAcc_HeadDesc").value=head_desc;
}

function getAccDesc_res(baseResponse)
{
	
		var headdesc = baseResponse.getElementsByTagName("headdesc")[0].firstChild.nodeValue;
		document.getElementById("txtAcc_HeadDesc").value=headdesc;
		
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		
		var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
		
		var url ="../../../../../Bills_Token_Register_with_SP?command=budgetProv&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};

		xmlrequest.send(null);
		
}

function budgetProv_res(baseResponse)
{
	//alert("in response...");
	
	var budgetAllo = baseResponse.getElementsByTagName("budgetAllo")[0].firstChild.nodeValue;
	var spent = baseResponse.getElementsByTagName("spent")[0].firstChild.nodeValue;
	var finttl = baseResponse.getElementsByTagName("finttl")[0].firstChild.nodeValue;
	
	var REF_NO = baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
	var REF_DATE = baseResponse.getElementsByTagName("REF_DATE")[0].firstChild.nodeValue;
	if(REF_NO=='null')
		{
			REF_NO="";
		}
	if(REF_DATE=='null')
	{
		REF_DATE="";
	}
	
//	document.getElementById("txtBudgetProvision").value=budgetAllo;
//	document.getElementById("txtBudgetSpent").value=spent;
	document.getElementById("budAvail").value=finttl;
	
	document.getElementById("txtRefNo").value=REF_NO;
	document.getElementById("txtRefDate").value=REF_DATE;
	
	
}
function getProceedingDetails_res(baseResponse)
{
	//var tot_amt=0;
	//alert("tot_amt == "+tot_amt);
	acc_head_code="";
	var PAYMENT_AMOUNT="";
	//var procNo = baseResponse.getElementsByTagName("procNo")[0].firstChild.nodeValue;
	//alert("procNo"+procNo);
	var PROCESS_FLOW_ID = baseResponse.getElementsByTagName("PROCESS_FLOW_ID")[0].firstChild.nodeValue;
	if(PROCESS_FLOW_ID=="MD")
		{
			alert("Records Already Updated");
			document.getElementById("onsubmit").disabled=true;
			document.getElementById("Print").disabled=false;
		}
	else
		{
		document.getElementById("onsubmit").disabled=false;
		document.getElementById("Print").disabled=true;
		}
	var procdate = baseResponse.getElementsByTagName("procdate")[0].firstChild.nodeValue;
	var sanAmt = baseResponse.getElementsByTagName("sanAmt")[0].firstChild.nodeValue;
	//var gpfno = baseResponse.getElementsByTagName("gpfno")[0].firstChild.nodeValue;
	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	var EMPLOYEE_NAME = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
	var sanc_id=baseResponse.getElementsByTagName("HRMS_SANCTION_ID")[0].firstChild.nodeValue;
	
	document.getElementById("txtPayableTo").value=EMPLOYEE_NAME;
	//alert(EMPLOYEE_NAME);//var offid = baseResponse.getElementsByTagName("offid")[0].firstChild.nodeValue;
	//var offname = baseResponse.getElementsByTagName("offname")[0].firstChild.nodeValue;
	document.getElementById("txtProceedingDate").value=procdate;
	//document.getElementById("txtPayeeCode").value=gpfno;
	document.getElementById("txtTotalSanctionedAmount").value=sanAmt;
	document.getElementById("txtTotalBillAmount").value=sanAmt;
	document.getElementById("txtPayeeCode").value=empid;
	
	document.getElementById("sanc_id").value=sanc_id;
	
	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	//alert(count);
	
	var length=document.getElementById("txtAcc_HeadCode").options.length;
    //alert("LENGTH===="+length);
	 	var optionss=document.getElementById("txtAcc_HeadCode");
	 	for(var i=length-1;i>=0;i--)
	 		{
	 			optionss.remove(i);
	 		}
	
	
	for(var i=0;i<count;i++)
		{
								
			var PAYMENT_HEAD_OF_AC = baseResponse.getElementsByTagName("PAYMENT_HEAD_OF_AC")[i].firstChild.nodeValue;
			acc_head_code=acc_head_code+baseResponse.getElementsByTagName("PAYMENT_HEAD_OF_AC")[i].firstChild.nodeValue+",";
			PAYMENT_AMOUNT=PAYMENT_AMOUNT+baseResponse.getElementsByTagName("PAYMENT_AMOUNT")[i].firstChild.nodeValue+",";
			var combo = document.getElementById("txtAcc_HeadCode");
			var option = document.createElement("OPTION");
			option.text = PAYMENT_HEAD_OF_AC;
		    option.value = PAYMENT_HEAD_OF_AC;
		    try {
		        combo.add(option, null); //Standard//////////////////   ////
		    }catch(error) {
		        combo.add(option); // IE only
		    }
		    
		    
		  
		}
	
	
	var head_code=acc_head_code.substring(0, acc_head_code.length-1);
	var PAYMENT_AMOUNT1=PAYMENT_AMOUNT.substring(0, PAYMENT_AMOUNT.length-1);
	document.getElementById("acc_code").value=head_code;
	
	
	var table=document.getElementById("acc_id");
	 var child=table.childNodes;
   for(var c=child.length-1;c>1;c--)
   {
   	table.removeChild(child[c]);
   }

	
	var row;
	var table1;
	for(var i=0;i<count;i++)
	{
		
		var PAYMENT_HEAD_OF_AC = baseResponse.getElementsByTagName("PAYMENT_HEAD_OF_AC")[i].firstChild.nodeValue;
		var ACCOUNT_HEAD_DESC = baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
		var pay_amount = baseResponse.getElementsByTagName("PAYMENT_AMOUNT")[i].firstChild.nodeValue
		//tot_amt= parseInt(tot_amt)+ parseInt(pay_amount);
		var CURRENT_YEAR_BUDGET_ALLOTTED = baseResponse.getElementsByTagName("CURRENT_YEAR_BUDGET_ALLOTTED")[i].firstChild.nodeValue;
		var BUDGET_SOFAR_SPENT = baseResponse.getElementsByTagName("BUDGET_SOFAR_SPENT")[i].firstChild.nodeValue;
		var REF_NO = baseResponse.getElementsByTagName("REF_NO")[i].firstChild.nodeValue;
		var REF_DATE = baseResponse.getElementsByTagName("REF_DATE")[i].firstChild.nodeValue;
		
		if( CURRENT_YEAR_BUDGET_ALLOTTED=="0" || CURRENT_YEAR_BUDGET_ALLOTTED=="null")
			{
				CURRENT_YEAR_BUDGET_ALLOTTED="";			
			}
		if( pay_amount=="0" || pay_amount=="null")
		{
			pay_amount="";			
		}
		if( BUDGET_SOFAR_SPENT=="0" || BUDGET_SOFAR_SPENT=="null")
		{
			BUDGET_SOFAR_SPENT="";			
		}
		if( REF_NO==0 || REF_NO=="null")
		{
			REF_NO="";			
		}
		if( REF_DATE=="0" || REF_DATE=="null")
		{
			REF_DATE="";			
		}
		
		
	
		
		
		//alert(PAYMENT_HEAD_OF_AC); 
		table1=document.getElementById("acc_id");
			row=document.createElement("tr");
			row.id=i;
			//alert(i);
			    var td=document.createElement("td");					  						
				var id=document.createTextNode(PAYMENT_HEAD_OF_AC);				
	            td.appendChild(id);	            
	        	td.setAttribute("align", "center");	        	
	        	row.appendChild(td);
	        	
	        	 var td=document.createElement("td");					  						
					var id=document.createTextNode(ACCOUNT_HEAD_DESC);				
		            td.appendChild(id);	            
		        	td.setAttribute("align", "center");	
		        	row.appendChild(td);
		        	
		        	
		        	 var td=document.createElement("td");					  						
						var id=document.createTextNode(pay_amount);				
			            td.appendChild(id);	            
			        	td.setAttribute("align", "center");	
			            row.appendChild(td);
		        	
	        	
		        	var td=document.createElement("td");					  						
					var id=document.createTextNode(CURRENT_YEAR_BUDGET_ALLOTTED);				
		            td.appendChild(id);	            
		        	td.setAttribute("align", "center");	        	
		        	row.appendChild(td);
		        	
		        	var td=document.createElement("td");					  						
					var id=document.createTextNode(BUDGET_SOFAR_SPENT);				
		            td.appendChild(id);	            
		        	td.setAttribute("align", "center");	        	
		        	row.appendChild(td);
		        	
		        	
		        	var td=document.createElement("td");					  						
					var id=document.createTextNode(REF_NO);				
		            td.appendChild(id);	            
		        	td.setAttribute("align", "center");	        	
		        	row.appendChild(td);
		        	
		        	var td=document.createElement("td");					  						
					var id=document.createTextNode(REF_DATE);				
		            td.appendChild(id);	            
		        	td.setAttribute("align", "center");	        	
		        	row.appendChild(td);
	        	
	        	
	        	table1.appendChild(row);
	        	
	}
	
	//document.getElementById("total_amt").value=tot_amt;
//	alert("pay_amount  ==="+tot_amt);
	
	//alert("shanmugam....");
//	document.getElementById("cboOffice").length = 0;
//	var se = document.getElementById("cboOffice");
//	var op = document.createElement("OPTION");
//	op.value = offid;
//	var txt = document.createTextNode(offname);
//	op.appendChild(txt);
//	se.appendChild(op);
	
}

function firstLoad(baseResponse) {
	 //alert("RKsbg");
	document.getElementById("cboOffice").length = 1;
	var len1 = baseResponse.getElementsByTagName("empName").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("empName")[i].firstChild.nodeValue;
		 //alert(empName);
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}

	var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas.value = empid;

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("billMajorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMajorTypeCode = baseResponse
					.getElementsByTagName("billMajorTypeCode")[i].firstChild.nodeValue;
			var billMajorTypeDesc = baseResponse
					.getElementsByTagName("billMajorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMajorType");
			var op = document.createElement("OPTION");
			op.value = billMajorTypeCode;
			var txt = document.createTextNode(billMajorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
		}

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.value = OfficeID;
		}

	} else {
		alert("Fail to Load Bill Major Type");
	}
}

function calculateBudget(path) {
	// alert(path);
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtaccountheadcode = document.getElementById("txtAcc_HeadCode").value;

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	if (txtaccountheadcode == "") {
		alert("Enter Account Head Code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=calculateBudget&cboAcc_UnitCode="
				+ cboAcc_UnitCode + "&cboOffice_code=" + cboOffice_code
				+ "&year=" + year + "&year1=" + year1 + "&txtaccountheadcode="
				+ txtaccountheadcode;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function calculateBudget1(baseResponse) {
	// alert("RKsbg");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = BudgetProvided;
	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flag == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}
}

function getBillMinorType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=getBillMinorType&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function getBillMinorType1(baseResponse) {
      	document.getElementById("txtBillNo").value = "";
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	        if (flag1 == "success") {
		var BillNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = BillNo;
	} else {
		alert("Failed to Generate Bill No");
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}

		var len5 = baseResponse.getElementsByTagName("EstimateSanctionNo").length;
		// alert(len5);
		var se = document.getElementById("cboEstimateSanctionNumber");
		//se.length=0;
		for ( var i = 0; i < len5; i++) {
			var EstimateSanctionNo = baseResponse
					.getElementsByTagName("EstimateSanctionNo")[i].firstChild.nodeValue;
			// alert(EstimateSanctionNo);
			
			var op = document.createElement("OPTION");
			op.value = EstimateSanctionNo;
			var txt = document.createTextNode(EstimateSanctionNo);
			op.appendChild(txt);
			se.appendChild(op);
		}

	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}

function getBillsubType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	// alert(cboBillMajorType);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType
				.focus();
	} 
	
	
	else {
		
		if(document.getElementById("cboBillMinorType").value=="1")
		{
			document.getElementById("txtPayeeType").length=0;
		var se = document.getElementById("txtPayeeType");
		var op = document.createElement("OPTION");
		op.value = 4;
		var txt = document.createTextNode("Employee");
		op.appendChild(txt);
		se.appendChild(op);
		}
			var url = path
					+ "/Bills_Token_Register_with_SP?command=getBillsubType&cboBillMajorType="
					+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType;
			// alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
	
			xmlrequest.send(null);
	}

}

function getBillsubType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}
function IVno(path) {
	if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoInvoiceEntryOption[0].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithProceeding.rdoInvoiceEntryOption[0].value;
	} else {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithProceeding.rdoInvoiceEntryOption[1].value;
	}

	if (rdoInvoiceEntryOption == "Entry") {
		document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList.disabled = true;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceNo.disabled = false;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceNo.value = "";
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceDate.value = "";
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceAmount.value = "";
	} else {
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceNo.disabled = true;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList.disabled = false;

		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var today = new Date();
		var day = today.getDate();
		var month = today.getMonth();
		month = month + 1;
		var year = today.getYear();
		if (year < 1900)
			year += 1900;

		var url = path
				+ "/Bills_Token_Register_with_SP?command=IVno&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&month=" + month + "&year=" + year;

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

	}
}

function IVno1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList.length = 1;
	if (flag == "success1") {
		var len4 = baseResponse.getElementsByTagName("InvoiceNo").length;
		for ( var i = 0; i < len4; i++) {
			var InvoiceNo = baseResponse.getElementsByTagName("InvoiceNo")[i].firstChild.nodeValue;
			var se = document.getElementById("txtIfSelectfromList");
			var op = document.createElement("OPTION");
			op.value = InvoiceNo;
			var txt = document.createTextNode(InvoiceNo);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}

function InvoiceDetails(path) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtIfSelectfromList = document.getElementById("txtIfSelectfromList").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (txtIfSelectfromList == "" || txtIfSelectfromList == "s") {
		alert("Select Invoice No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtIfSelectfromList
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=InvoiceDetails&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&month=" + month + "&year=" + year + "&txtIfSelectfromList="
				+ txtIfSelectfromList;

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function InvoiceDetails1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag == "success1") {
		var InvoiceDate = baseResponse.getElementsByTagName("InvoiceDate")[0].firstChild.nodeValue;
		var InvoiceAmount = baseResponse.getElementsByTagName("InvoiceAmount")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceDate.value = InvoiceDate;
		document.frm_BillTokenRegisterEntry_WithProceeding.txtInvoiceAmount.value = InvoiceAmount;
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}

function saveFunc(path) {

	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	
        var cboBillMinorType = document.getElementById("cboBillMinorType").value;
       
	var cboBillSubType = document.getElementById("cboBillSubType").value;
         
        var len=document.getElementById("cboBillSubType").length;
         
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	
        var txtProceedingNo = document
			.getElementById("txtProceedingNo").value;
	var txtProceedingDate = document
			.getElementById("txtProceedingDate").value;
//	var txtInvoiceReceivedDate = document
//			.getElementById("txtInvoiceReceivedDate").value;
//	var txtNoofInvoices = document.getElementById("txtNoofInvoices").value;
	if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].value;
	}
        var txtPayeeType = document.getElementById("txtPayeeType").value;
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;
        var txtTotalSanctionedAmount= document.getElementById("txtTotalSanctionedAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
        var txtPayableTo=document.getElementById("txtPayableTo").value;
        
	//var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	
        
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cboOffice = document.getElementById("cboOffice").value;
//	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
//	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
        
	//var BalanceAmount = (parseInt(txtBudgetProvision) - parseInt(txtBudgetSpent));
	var cashyear=document.getElementById("cash_year").value;
	var cashmonth=document.getElementById("cash_month").value;
	
	if (cboBillMajorType == "" || cboBillMajorType == 0) 
        {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} 
       
         if((document.getElementById("cboBillMinorType").length > 1) && (cboBillMinorType == "" || cboBillMinorType == 0))
        {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType
				.focus();
	} 
         if((document.getElementById("cboBillSubType").length >1) && (cboBillSubType == "" || cboBillSubType == 0))
        {
               alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType
				.focus();
        }
        else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.focus();
	} 
        
        else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate
				.focus();
	} else if (document.getElementById("txtProceedingNo").value == "") {
		alert("Enter Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo
				.focus();
	} else if (document.getElementById("txtProceedingDate").value == "") {
		alert("Enter Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate
				.focus();
	} else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount
				.focus();
	}  else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType
				.focus();
	} else if (document.getElementById("txtPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtPayableTo").value == "") {
		alert("Enter Payable To in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo
				.focus();
	} 
        else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas
				.focus();
	} else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.focus();
	} /*else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate
				.focus();
	}*/
	else if (parseInt(txtTotalBillAmount) > parseInt(BalanceAmount)) {
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount
				.focus();
	} else {
                //alert("Else Part ********");
		/*var url = path
				+ "/Bills_Token_Register_with_SP?command=saveFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtProceedingDate=" + txtProceedingDate
				+ "&txtProceedingNo=" + txtProceedingNo
				
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
				+ "&txtPayableTo=" +txtPayableTo
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtTotalSanctionedAmount=" + txtTotalSanctionedAmount
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode 
                                + "&txtPayeeType="+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
				+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice=" + cboOffice
				+ "&txtBudgetProvision=" + txtBudgetProvision
				+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
				+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;*/
		
		 //alert(url);
		var url = path
		+ "/Bills_Token_Register_with_SP?command=saveFunc&cmbAcc_UnitCode="
		+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
		+ "&year=" + cashyear + "&month=" +cashmonth+"&txtBillNo="+txtBillNo;
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function saveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	var BillNo1 = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
	var BillNo = parseInt(BillNo1);
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = BillNo + 1;

	if (flag == "success") {
		alert("Record Inserted Successfully");
		refresh();
	} else {
		alert("Record Insertion Failed");
	}
}

function forList(path) {
	// alert(path);
	var unitcode=document.getElementById("cmbAcc_UnitCode").value;
	winemp = window.open("Bills_Token_Register_with_SP_List.jsp?cmbAcc_UnitCode="+unitcode, "list",
			"status=1,height=550,width=1200,resizable=YES, scrollbars=yes");
	winemp.moveTo(30, 150);
	winemp.focus();
}

function ParentDrawing(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13,
		v14, v15, v16, v17, v18, v19, v20 , v21, v22) {
	 //alert(v3);
                 
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	document.frm_BillTokenRegisterEntry_WithProceeding.cmbAcc_UnitCode.value = v1;
	document.frm_BillTokenRegisterEntry_WithProceeding.cmbOffice_code.value = v2;
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType.value = v3;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = v6;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate.value = v7;
//	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo.value = v8;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate.value = v9;
	document.getElementById("txtPayeeType").length=0;
	if(v10==4)
	{
		
		var paytype=document.getElementById("txtPayeeType");
		var opt=document.createElement("OPTION");
		opt.value=4;
		opt.text="Employee";
		paytype.appendChild(opt);
		
	}
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType.value = v10;
	
	document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode.value = v11;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount.value = v12;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount.value = v13;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo.value = v14;
        document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas.value = v15;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode.value = v17;
        //document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = v18;
        //document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = v19;
        document.frm_BillTokenRegisterEntry_WithProceeding.txtRefNo.value = v20;
	document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate.value = v21;
	document.frm_BillTokenRegisterEntry_WithProceeding.mtxtRemarks.value = v22;
	
	if (v16 == "Y") {
		document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].checked = true;
	} else {
		document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].checked = true;
	}
       	document.frm_BillTokenRegisterEntry_WithProceeding.onsubmit.disabled = true;
	document.frm_BillTokenRegisterEntry_WithProceeding.ondelete.disabled = false;
	document.frm_BillTokenRegisterEntry_WithProceeding.onupdate.disabled = false;

	var url = "../../../../../Bills_Token_Register_with_SP?command=Edit&txtBillNo="
			+ v6
			+ "&txtEmpID_mas="
			+ v15
			+ "&cmbAcc_UnitCode="
			+ v1
			+ "&cmbOffice_code="
			+ v2
			+ "&year="
			+ year
			+ "&year1="
			+ year1
			+ "&txtAcc_HeadCode=" + v17+"&procNo="+v8;
	 //alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);

}

function Edit1(baseResponse) {
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.length = "1";

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		var len = baseResponse.getElementsByTagName("BillProcessingDoneBy").length;

		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.value = billMinorTypeCode;
		}
		var len5 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		// alert(len5);
		for ( var i = 0; i < len5; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.value = billSubTypeCode;
		}

		var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithProceeding.cmbMas_SL_Code.length = "1";
		for ( var i = 0; i < len; i++) {
			var BillProcessingDoneBy = baseResponse
					.getElementsByTagName("BillProcessingDoneBy")[0].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(BillProcessingDoneBy);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cmbMas_SL_Code.value = empid;
		}
	}

	var flagg = baseResponse.getElementsByTagName("flagg")[0].firstChild.nodeValue;
	if (flagg == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = BudgetProvided;
	//	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flagg == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}

	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
	
	var flag_no = baseResponse.getElementsByTagName("flag_no")[0].firstChild.nodeValue;
	if (flag_no == "success") {
		document.getElementById("txtProceedingNo").length=0;
		for ( var i = 0; i <1; i++) {
			var sancid = baseResponse.getElementsByTagName("sancid")[i].firstChild.nodeValue;
			var sancno = baseResponse.getElementsByTagName("sancno")[i].firstChild.nodeValue;
			var se = document.getElementById("txtProceedingNo");
			var op = document.createElement("OPTION");
			op.value = sancid;
			var txt = document.createTextNode(sancno);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
	
}

function deleteeee(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;

	var txtBillNo = document.getElementById("txtBillNo").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path
				+ "/Bills_Token_Register_with_SP?command=deleted&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtBillNo=" + txtBillNo + "&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function Acc_Head_Code(path)
{
	//alert("*****");
	var url =  "../../../../../Bills_Token_Register_with_SP?command=Acc_Head_Code";
 //alert(url);
var xmlrequest = AjaxFunction();
xmlrequest.open("POST", url, true);
xmlrequest.onreadystatechange = function() {
manipulate(xmlrequest);
};
xmlrequest.send(null);
}

function jasper() {
	// alert("******");
	
	//alert(acc_head_code);
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cmbReportType = document.getElementById("cmbReportType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	//var r = confirm("Are U Sure?");
	
		var url = "../../../../../Bills_Token_Register_with_SP?command=Report&cmbReportType="+cmbReportType+"&head_code="+head_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	
}




function deleteRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
		refresh();
	} else {
		alert("Unable to Delete");
		refresh();
	}
}

function update(path) {
//	 alert("update");
	
	
	
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = document.getElementById("cash_month").value;
	
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	
	
	var selectnews=document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo;
	var txtProceedingNo=selectnews.options[selectnews.selectedIndex].text;
	
	//var txtProceedingNo =document.getElementByID("txtProceedingNo").options[document.getElementByID("txtProceedingNo").selectedIndex].text;
	//var txtProceedingNo = document
	//		.getElementById("txtProceedingNo").value;
	var txtProceedingDate = document
			.getElementById("txtProceedingDate").value;
	
	if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithProceeding.rdoMTC_70_Register[1].value;
	}
	var txtPayeeType = document.getElementById("txtPayeeType").value;
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;
        var txtTotalSanctionedAmount= document.getElementById("txtTotalSanctionedAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
        var txtPayableTo=document.getElementById("txtPayableTo").value;
      	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cboOffice = document.getElementById("cboOffice").value;
//	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	//var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
	
	var sanction_id=document.getElementById("sanc_id").value;
	
	if (cboBillMajorType == "" || cboBillMajorType == 0) 
        {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType
				.focus();
	} 
       
         if((document.getElementById("cboBillMinorType").length > 1) && (cboBillMinorType == "" || cboBillMinorType == 0))
        {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType
				.focus();
	} 
         if((document.getElementById("cboBillSubType").length >1) && (cboBillSubType == "" || cboBillSubType == 0))
        {
               alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType
				.focus();
        } else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate
				.focus();
	}else if (document.getElementById("txtProceedingNo").value == "") {
		alert("Enter Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo
				.focus();
	} else if (document.getElementById("txtProceedingDate").value == "") {
		alert("Enter Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate
				.focus();
	}
        else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount
				.focus();
	}  else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType
				.focus();
	} else if (document.getElementById("txtPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtPayableTo").value == "") {
		alert("Enter Payable To in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo
				.focus();
	} 
        else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas
				.focus();
	} /*else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.focus();
	} else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate
				.focus();
	} else if (parseInt(txtTotalBillAmount) > parseInt(BalanceAmount)) {
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount
				.focus();
	}*/ else {

		var url = path
				+ "/Bills_Token_Register_with_SP?command=update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtProceedingDate=" + txtProceedingDate
				+ "&txtProceedingNo=" + txtProceedingNo
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
				+ "&txtPayableTo=" +txtPayableTo
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtTotalSanctionedAmount=" + txtTotalSanctionedAmount
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode 
                                + "&txtPayeeType="+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
				+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice=" + cboOffice
				+ "&txtRefNo=" + txtRefNo
				+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks+"&sanction_id="+sanction_id;
				var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function updateRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	if (flag == "success") {
		alert("Record Updated Successfully.");
		document.frm_BillTokenRegisterEntry_WithProceeding.onsubmit.disabled=true;
		document.frm_BillTokenRegisterEntry_WithProceeding.Print.disabled=false;
		//refresh();
	} else {
		alert("Failed to update values");
	}
}

function refresh() 
        {
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMajorType.value = "0";
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.cboBillSubType.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBillNo.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingNo.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate.value = "";
	
	document.frm_BillTokenRegisterEntry_WithProceeding.sanc_id.value = "";
	
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalBillAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtTotalSanctionedAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadCode.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtAcc_HeadDesc.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeType.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtPayeeCode.value = "";
        document.frm_BillTokenRegisterEntry_WithProceeding.txtPayableTo.value = "";
        
	document.frm_BillTokenRegisterEntry_WithProceeding.cmbMas_SL_Code.length = "1";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtEmpID_mas.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.cboOffice.length = "1";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetSpent.value = "";
	//document.frm_BillTokenRegisterEntry_WithProceeding.txtBudgetProvision.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtRefNo.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate.value = "";
	document.frm_BillTokenRegisterEntry_WithProceeding.mtxtRemarks.value = "";
        
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

function exitfun(path) {
	window.close();
}














function LoadAccountingUnitID(COMMAND)
{
	
        command_for_office = COMMAND;
        var url="../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
       // alert("command_for_office&&&&&&&&"+url+"ssssss"+command_for_office);
        var req=AjaxFunction();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        	//alert("gsfg");
          handle_loadAccountingUnitID(req);
        };        
        req.send(null);
   // alert("ebd");
}


function handle_loadAccountingUnitID(req)
{
   
    if(req.readyState==4)
    {
   
     if(req.status==200)
     {
    	 //alert("200");
   
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
   
      
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
                cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                
                option.text=accounting_unit_name+"("+accounting_unit_id+")";
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
                       
        
            /** Load Accounting Office ID */ 
            if ( (command_for_office == "ONLY_UNITS") || (command_for_office=="LIST_ALL_UNITS_ONLY") || (command_for_office=="FOR_LIST_0" ) )
            {
            
            }
            else
            {
               common_LoadOffice();            
            }         
            
            
        }
        else
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}



function common_LoadOffice()
{
	//alert("comes");
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;     
   // alert("unitID_val"+unitID_val);
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Bills_Token_Register_with_SP?command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=AjaxFunction();
        req.open("POST",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        }
        req.send(null);
    }     
}




function handle_loadOffice(req)
{
  //alert("handle_loadOffice");
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
    	 //alert("20000");
      
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        { 
         
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
       
            for(i=0;i<offidvalues.length;i++)
            {  //alert("i"+offidvalues.length);
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")";
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
            
             
     }
    }
}

function selectMonth1(curyear)
{
	 v=new Date();
	 mn=v.getMonth();
	 yr=v.getFullYear();
     //alert(mn);
var rel_year=document.getElementById("cash_year").value;
//alert(yr);
var i;
for(i=document.frm_BillTokenRegisterEntry_WithProceeding.cash_month.options.length-1;i>=0;i--)
{

	document.frm_BillTokenRegisterEntry_WithProceeding.cash_month.remove(i);
}

var minorcmb = document.frm_BillTokenRegisterEntry_WithProceeding.cash_month;
	 var start_months = new Array('JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC');
	    var start_months_val = new Array(1,2,3,4,5,6,7,8,9,10,11,12);
	   
if(parseInt(rel_year)==parseInt(curyear))
{
	
	for(var i=0; i<(parseInt(mn)+1); i++)
	{
		 var opt1 = document.createElement('option');
         opt1.value = start_months_val[i];
         opt1.innerHTML = start_months[i];
         minorcmb.appendChild(opt1);
	}
	
}else{
	for(var i=0; i<12; i++)
	{
		 var opt1 = document.createElement('option');
         opt1.value = start_months_val[i];
         opt1.innerHTML = start_months[i];
         minorcmb.appendChild(opt1);
	}
}

document.getElementById("cash_month").value=parseInt(mn)+1;

}



function cash_monthss()
{
	//alert("******");
	var combo1 = document.getElementById("cash_month");
    var txtProceedingNo = combo1.options[combo1.selectedIndex].text;
    document.getElementById("cash_mont").value=txtProceedingNo;
}
