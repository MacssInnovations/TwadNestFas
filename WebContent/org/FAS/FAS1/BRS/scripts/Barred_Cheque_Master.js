//             Barred_Cheque_Master            //

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
/*
function test_c()
{
alert("test_c");
var txtCheque_Valid_Upto = document.getElementById("txtCheque_Valid_Upto").value;
var validSpl=txtCheque_Valid_Upto.split("/");
var txtCheque_Date = document.getElementById("txtCheque_Date").value;
var checkSpl=txtCheque_Date.split("/");

    if(checkSpl[1]==10 || checkSpl[1]==11 || checkSpl[1]==12)
    {
   // alert("iffffff");
         if(checkSpl[2]!=validSpl[2])
        {
            var fYr=parseInt(checkSpl[2])+1;
            if(fYr!=validSpl[2])
            {
            alert("upto 3 months is valid :");
            }
            else
            {
                if(checkSpl[1]==10)
                {
                    if(validSpl[1]>1 && (validSpl[2]==fYr))
                    {
                        alert("*valid upto 3 months only");
                        document.getElementById("txtCheque_Valid_Upto").value="";
                    }
                }
                else if(checkSpl[1]==11)
                {
                         if(validSpl[1]>2 && (validSpl[2]==fYr))
                    {
                        alert("valid upto 3 months only");
                        document.getElementById("txtCheque_Valid_Upto").value="";
                    }
                }
                else if(checkSpl[1]==12)
                {
                     if(validSpl[1]>3 && (validSpl[2]==fYr))
                    {
                        alert("valid upto 3 months only");
                        document.getElementById("txtCheque_Valid_Upto").value="";
                    }
                }
            }
        }
    
    }
    else
    {
   
        if(checkSpl[2]!=validSpl[2])
        {
        alert("valid upto 3 months only");
        document.getElementById("txtCheque_Valid_Upto").value="";
        }
        else if(checkSpl[2]==validSpl[2])
        {
       
            var normalD=parseInt(checkSpl[1])+03;
            alert("normalD:::"+normalD);
            if(normalD<validSpl[1])
            {//check month
             alert("valid upto 3 months only:");
             document.getElementById("txtCheque_Valid_Upto").value="";
            }
            else if(normalD==validSpl[1])
            {//check date
                if(checkSpl[0]!=validSpl[0])
                {
                 alert("Date should be valid upto 3 months only");
                 document.getElementById("txtCheque_Valid_Upto").value="";
                 }
            }
        }
    }

}  */

function manipulate_first(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			
			var command = tagCommand.firstChild.nodeValue;
			if (command == "Add") {
				Add1(baseResponse);
			} else if (command == "LoadDocNo") {
				LoadDocNo1(baseResponse);
			} 
                        else if (command == "LoadDocNo_2006") {
				LoadDocNo_2006(baseResponse);
			} 
                        else if (command == "LoadChequeNoDetails") {
				LoadChequeNoDetails1(baseResponse);
			} else if (command == "Add") {
				Add1(baseResponse);
			} else if (command == "List") {
				LoadList(baseResponse);
			} else if (command == "Update") {
				Update1(baseResponse);
			} else if (command == "Delete") {
				Delete1(baseResponse);
			} else if (command == "ParentDrawing") {
				ParentDrawing1(baseResponse);
			}
		}
	}
}
function LoadMonthYear(path) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_FollowUp.txtCB_Year.focus();
	}else{
	var url = path
			+ "/BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);
	}
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			}
		}
	}
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
              //  alert(txtCB_Year+"::::"+txtCB_Year1);
		 if(CB_Year1<txtCB_Year1)
                {
                alert("Cashook Year should be less than start month and year");
                document.getElementById("txtCB_Year").value="";
                }
                else if(CB_Year1>txtCB_Year1)
                {
                
                }
                else if(CB_Year1==txtCB_Year1)
                {
            // var txtCB_Month_text = document.getElementById("txtCB_Month").value;
               //alert("txtCB_Month_text"+txtCB_Month_text+"txtCB_Month"+txtCB_Month);
                    if(CB_Month1<=txtCB_Month)
                    {
                    alert("Cashook Month should be less than start month and year");
                     document.getElementById("txtCB_Month").value="s";
                    }
                    
                }
	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");		
	} else {
		alert("Failed to Load Month and Year");		
	}
}

function LoadDocNo(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var cmbDoc_Type = document.getElementById("cmbDoc_Type").value;
	var cmbCheque_No = document.getElementById("cmbCheque_No").value;
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBarred_Cheque_Master.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBarred_Cheque_Master.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Select Bank Account No in the Field");
		document.frmBarred_Cheque_Master.cmbBankAccNo.focus();
	} else if (cmbDoc_Type == "s") {
		alert("Select Doc Type in the Field");
		document.frmBarred_Cheque_Master.cmbDoc_Type.focus();
	} else if (cmbCheque_No == "") {
		alert("Enter Cheque No in the Field");
		document.frmBarred_Cheque_Master.cmbCheque_No.focus();
	} 
       
        else if(txtCB_Year>=2008)
        {
            document.getElementById("comboid").style.display='block';
        document.getElementById("textid").style.display='none';
        //alert("txtCB_Year"+txtCB_Year);
		var url = path
				+ "/BRS_Barred_Cheque_Master?command=LoadDocNo&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&cmbDoc_Type="
				+ cmbDoc_Type + "&cmbCheque_No=" + cmbCheque_No;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate_first(xmlrequest);
		}
		xmlrequest.send(null);
	}
        else
        {
        document.getElementById("comboid").style.display='none';
        document.getElementById("textid").style.display='block';
        
        var url = path
				+ "/BRS_Barred_Cheque_Master?command=LoadDocNo_2006&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&cmbDoc_Type="
				+ cmbDoc_Type + "&cmbCheque_No=" + cmbCheque_No;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate_first(xmlrequest);
		}
		xmlrequest.send(null);
        
        }
}

function calins(e,t)
{
	
    var unicode=e.charCode? e.charCode : e.keyCode;
       if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

function cashbook_ck()
{
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtDoc_Date = document.getElementById("txtDoc_Date").value.split("/");
	if(txtDoc_Date[1]!=txtCB_Month)
	{
		document.getElementById("txtDoc_Date").value="";
		alert("Doc Date Should be within cashbook year and month");
		return false;
		
	}
	if(txtDoc_Date[2]!=txtCB_Year)
	{
		document.getElementById("txtDoc_Date").value="";
		alert("Doc Date Should be within Cashbook year and month");
		return false;
		
	}
}

function LoadChequeNoDetails(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var cmbDoc_Type = document.getElementById("cmbDoc_Type").value;
	var txtDoc_No = document.getElementById("txtDoc_No").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBarred_Cheque_Master.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBarred_Cheque_Master.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Enter Bank Account No in the Field");
		document.frmBarred_Cheque_Master.cmbBankAccNo.focus();
	} else if (txtDoc_No == "s") {
		alert("Select Doc No in the Field");
		document.frmBarred_Cheque_Master.txtDoc_No.focus();
	} else {

		var url = path
				+ "/BRS_Barred_Cheque_Master?command=LoadChequeNoDetails&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&cmbDoc_Type="
				+ cmbDoc_Type + "&txtDoc_No=" + txtDoc_No;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate_first(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function LoadDocNo1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
        var flag_l = baseResponse.getElementsByTagName("flag_l")[0].firstChild.nodeValue;
      
        if(flag_l=="loading")
        {
		var DocNo1 = baseResponse.getElementsByTagName("DocNo");
		document.getElementById("txtDoc_No").length = 1;
		document.getElementById("txtDoc_Date").value = "";
		document.getElementById("txtCheque_Date").value = "";
		document.getElementById("txtCheque_Amount").value = "";

		if (DocNo1.length != 0) {
			for ( var k = 0; k < DocNo1.length; k++) {
				var DocNo = baseResponse.getElementsByTagName("DocNo")[k].firstChild.nodeValue;
				var se = document.getElementById("txtDoc_No");
				var op = document.createElement("OPTION");
				op.value = DocNo;
				var txt = document.createTextNode(DocNo);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Doc No Does Not Exist");
		}
                
                }
                else if(flag_l=="notexist")
                {
              alert("Doc No Does Not Exist");
              document.getElementById("cmbCheque_No").value="";
              
                }
                else
                {
                document.getElementById("cmbCheque_No").value="";
                alert("This Cheque No Already Exist");
                }
	} 
        else {
		alert("Fail to Load Cheque No");
	}
}

function LoadDocNo_2006(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
        var flag_l = baseResponse.getElementsByTagName("flag_l")[0].firstChild.nodeValue;
     //  alert(flag_l);
        if(flag_l=="loading")
        {
	}
        else
        {
        document.getElementById("cmbCheque_No").value="";
        alert("This Cheque No already Barred");
        }
	} 
        else {
		alert("Fail to Load Cheque No");
	}
}

function LoadChequeNoDetails1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var count = baseResponse.getElementsByTagName("count");
		if (count.length != 0) {
			var ChequeDate = baseResponse.getElementsByTagName("ChequeDate")[0].firstChild.nodeValue;
			var DocDate = baseResponse.getElementsByTagName("DocDate")[0].firstChild.nodeValue;
			var ChequeAmount = baseResponse
					.getElementsByTagName("ChequeAmount")[0].firstChild.nodeValue;

			if (ChequeDate == null) {
				ChequeDate = "";
			}
			if (DocDate == null) {
				DocDate = "";
			}
			if (ChequeAmount == null) {
				ChequeAmount = "";
			}
			document.getElementById("txtCheque_Date").value = DocDate;
			document.getElementById("txtDoc_Date").value = ChequeDate;
			document.getElementById("txtCheque_Amount").value = ChequeAmount;
                        //dhana
                     //   alert(DocDate);
                        var checkSpl=DocDate.split("/");
                        if(checkSpl[1]==12)
                        {
                         var fYr=parseInt(checkSpl[2])+1;//2012
                       //  var mn=5;
                         var dat=checkSpl[0];
                         document.getElementById("txtCheque_Valid_Upto").value =checkSpl[0]+"/06/"+fYr;
                        }
                        else  if(checkSpl[1]==11)
                        {
                         var fYr=parseInt(checkSpl[2])+1;//2012
                         var dat=checkSpl[0];
                         document.getElementById("txtCheque_Valid_Upto").value =checkSpl[0]+"/05/"+fYr;
                        }
                        else  if(checkSpl[1]==10)
                        {
                         var fYr=parseInt(checkSpl[2])+1;//2012
                         var dat=checkSpl[0];
                         document.getElementById("txtCheque_Valid_Upto").value =checkSpl[0]+"/04/"+fYr;
                        }
                        else  if(checkSpl[1]==9)
                        {
                         var fYr=parseInt(checkSpl[2])+1;//2012
                         var dat=checkSpl[0];
                         document.getElementById("txtCheque_Valid_Upto").value =checkSpl[0]+"/03/"+fYr;
                        }
                        else  if(checkSpl[1]==8)
                        {
                         var fYr=parseInt(checkSpl[2])+1;//2012
                         var dat=checkSpl[0];
                         document.getElementById("txtCheque_Valid_Upto").value =checkSpl[0]+"/02/"+fYr;
                        }
                        else  if(checkSpl[1]==7)
                        {
                         var fYr=parseInt(checkSpl[2])+1;//2012
                         var dat=checkSpl[0];
                         document.getElementById("txtCheque_Valid_Upto").value =checkSpl[0]+"/01/"+fYr;
                        }
                        else
                        {
                        var fmn=parseInt(checkSpl[1])+6;//2012
                         var dat=checkSpl[0];
                         document.getElementById("txtCheque_Valid_Upto").value =checkSpl[0]+"/"+fmn+"/"+checkSpl[2];
                        }  
                        document.getElementById("txtCheque_Valid_Upto").readonly=true;
                                                
                        
		} else {
			alert("Records Does Not Exist");
		}
	} else {
		alert("Fail to Load Cheque No");
	}
}

function Add(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var cmbDoc_Type = document.getElementById("cmbDoc_Type").value;
	var cmbCheque_No = document.getElementById("cmbCheque_No").value;
	var txtDoc_No = document.getElementById("txtDoc_No").value;
	var txtDoc_Date = document.getElementById("txtDoc_Date").value;
	var txtCheque_Date = document.getElementById("txtCheque_Date").value;
	var txtCheque_Amount = document.getElementById("txtCheque_Amount").value;
	var txtCheque_Valid_Upto = document.getElementById("txtCheque_Valid_Upto").value;
	var mtxtFollowup_Action = document.getElementById("mtxtFollowup_Action").value;

	if (document.frmBarred_Cheque_Master.txtCleared_Entries[0].checked == true) {
		txtCleared_Entries = document.frmBarred_Cheque_Master.txtCleared_Entries[0].value;
                if(document.getElementById("txtCleared_Date").value=="")
                {
                alert("Please Enter Cleared Date");
                return false;
                }
	} else {
		txtCleared_Entries = document.frmBarred_Cheque_Master.txtCleared_Entries[1].value;
	}

	var txtCleared_Date = document.getElementById("txtCleared_Date").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBarred_Cheque_Master.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBarred_Cheque_Master.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Enter Bank Account No in the Field");
		document.frmBarred_Cheque_Master.cmbBankAccNo.focus();
	} else if (cmbDoc_Type == "s") {
		alert("Select Doc Type in the Field");
		document.frmBarred_Cheque_Master.cmbDoc_Type.focus();
	} else if (cmbCheque_No == "") {
		alert("Enter Cheque No in the Field");
		document.frmBarred_Cheque_Master.cmbCheque_No.focus();
	} else if (txtDoc_No == "s") {
		alert("Select Doc No in the Field");
		document.frmBarred_Cheque_Master.txtDoc_No.focus();
	} else if (txtDoc_Date == "") {
		alert("Enter Doc Date in the Field");
		document.frmBarred_Cheque_Master.txtDoc_Date.focus();
	} else if (txtCheque_Date == "") {
		alert("Enter Cheque Date in the Field");
		document.frmBarred_Cheque_Master.txtCheque_Date.focus();
	} else if (txtCheque_Amount == "") {
		alert("Enter Cheque Amount in the Field");
		document.frmBarred_Cheque_Master.txtCheque_Amount.focus();
	} else if (txtCheque_Valid_Upto == "") {
		alert("Enter Cheque Valid Upto in the Field");
		document.frmBarred_Cheque_Master.txtCheque_Valid_Upto.focus();
	} else {

		var url = path
				+ "/BRS_Barred_Cheque_Master?command=Add&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&cmbDoc_Type="
				+ cmbDoc_Type + "&cmbCheque_No=" + cmbCheque_No + "&txtDoc_No="
				+ txtDoc_No + "&txtDoc_Date=" + txtDoc_Date
				+ "&txtCheque_Date=" + txtCheque_Date + "&txtCheque_Amount="
				+ txtCheque_Amount + "&txtCheque_Amount=" + txtCheque_Amount
				+ "&txtCheque_Valid_Upto=" + txtCheque_Valid_Upto
				+ "&mtxtFollowup_Action=" + mtxtFollowup_Action
				+ "&txtCleared_Entries=" + txtCleared_Entries
				+ "&txtCleared_Date=" + txtCleared_Date;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate_first(xmlrequest);
		}
		xmlrequest.send(null);
	}
}
function Update(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var cmbDoc_Type = document.getElementById("cmbDoc_Type").value;
	var cmbCheque_No = document.getElementById("cmbCheque_No").value;
	var txtDoc_No = document.getElementById("txtDoc_No").value;
	var txtDoc_Date = document.getElementById("txtDoc_Date").value;
	var txtCheque_Date = document.getElementById("txtCheque_Date").value;
	var txtCheque_Amount = document.getElementById("txtCheque_Amount").value;
	var txtCheque_Valid_Upto = document.getElementById("txtCheque_Valid_Upto").value;
	var mtxtFollowup_Action = document.getElementById("mtxtFollowup_Action").value;
	if (document.frmBarred_Cheque_Master.txtCleared_Entries[0].checked == true) {
		txtCleared_Entries = document.frmBarred_Cheque_Master.txtCleared_Entries[0].value;
                if(document.getElementById("txtCleared_Date").value=="")
                {
                alert("Please Enter Cleared Date");
                return false;
                }
	} else {
		txtCleared_Entries = document.frmBarred_Cheque_Master.txtCleared_Entries[1].value;
	}
	var txtCleared_Date = document.getElementById("txtCleared_Date").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBarred_Cheque_Master.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBarred_Cheque_Master.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Enter Bank Account No in the Field");
		document.frmBarred_Cheque_Master.cmbBankAccNo.focus();
	} else if (cmbDoc_Type == "s") {
		alert("Select Doc Type in the Field");
		document.frmBarred_Cheque_Master.cmbDoc_Type.focus();
	} else if (cmbCheque_No == "") {
		alert("Enter Cheque No in the Field");
		document.frmBarred_Cheque_Master.cmbCheque_No.focus();
	} else if (txtDoc_No == "s") {
		alert("Select Doc No in the Field");
		document.frmBarred_Cheque_Master.txtDoc_No.focus();
	} else if (txtDoc_Date == "") {
		alert("Enter Doc Date in the Field");
		document.frmBarred_Cheque_Master.txtDoc_Date.focus();
	} else if (txtCheque_Date == "") {
		alert("Enter Cheque Date in the Field");
		document.frmBarred_Cheque_Master.txtCheque_Date.focus();
	} else if (txtCheque_Amount == "") {
		alert("Enter Cheque Amount in the Field");
		document.frmBarred_Cheque_Master.txtCheque_Amount.focus();
	} else if (txtCheque_Valid_Upto == "") {
		alert("Enter Cheque Valid Upto in the Field");
		document.frmBarred_Cheque_Master.txtCheque_Valid_Upto.focus();
	} else {

		var url = path
				+ "/BRS_Barred_Cheque_Master?command=Update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&cmbDoc_Type="
				+ cmbDoc_Type + "&cmbCheque_No=" + cmbCheque_No + "&txtDoc_No="
				+ txtDoc_No + "&txtDoc_Date=" + txtDoc_Date
				+ "&txtCheque_Date=" + txtCheque_Date + "&txtCheque_Amount="
				+ txtCheque_Amount + "&txtCheque_Amount=" + txtCheque_Amount
				+ "&txtCheque_Valid_Upto=" + txtCheque_Valid_Upto
				+ "&mtxtFollowup_Action=" + mtxtFollowup_Action
				+ "&txtCleared_Entries=" + txtCleared_Entries
				+ "&txtCleared_Date=" + txtCleared_Date;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate_first(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function Update1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Updated Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist Exist");
		refresh();
	} else {
		alert("Record Updation Failed");
		refresh();
	}
}

function Delete(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var cmbDoc_Type = document.getElementById("cmbDoc_Type").value;
	var cmbCheque_No = document.getElementById("cmbCheque_No").value;

	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBarred_Cheque_Master.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBarred_Cheque_Master.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Enter Bank Account No in the Field");
		document.frmBarred_Cheque_Master.cmbBankAccNo.focus();
	} else if (cmbDoc_Type == "s") {
		alert("Select Doc Type in the Field");
		document.frmBarred_Cheque_Master.cmbDoc_Type.focus();
	} else if (cmbCheque_No == "") {
		alert("Enter Cheque No in the Field");
		document.frmBarred_Cheque_Master.cmbCheque_No.focus();
	} else {

		var url = path
				+ "/BRS_Barred_Cheque_Master?command=Delete&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo + "&cmbDoc_Type="
				+ cmbDoc_Type + "&cmbCheque_No=" + cmbCheque_No;

		// alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate_first(xmlrequest);
		}
		xmlrequest.send(null);
	}
}

function Delete1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist Exist");
		refresh();
	} else {
		alert("Record Deletion Failed");
		refresh();
	}
}

function Add1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Inserted Successfully");
		refresh();
	} else if (flag == "Exist") {
		alert("Record Already Exist");
		refresh();
	} else {
		alert("Record Insertion Failed");
		refresh();
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
			return false
	}
}

function list() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;	
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBarred_Cheque_Master.txtCB_Year.focus();
	} else if (txtCB_Month == "") {
		alert("Enter Cash Book Month in the Field");
		document.frmBarred_Cheque_Master.txtCB_Month.focus();
	} else if (cmbBankAccNo == "") {
		alert("Select Bank Acc No in the Field");
		document.frmBarred_Cheque_Master.cmbBankAccNo.focus();
	} else {
		winemp = window.open("Barred_Cheque_Master_list.jsp?cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month,
				"list",
				"status=1,height=550,width=1200,resizable=YES, scrollbars=yes");
		winemp.moveTo(150, 150);
		winemp.focus();
	}
}

function initialLoad(path, cmbAcc_UnitCode, cmbOffice_code, txtCB_Year,txtCB_Month) {
	var url = path + "/BRS_Barred_Cheque_Master?command=List&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month;
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate_first(xmlrequest);
	}

	xmlrequest.send(null);

}

function LoadList(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
        var flagdat = baseResponse.getElementsByTagName("flagdat")[0].firstChild.nodeValue;
        if(flagdat=="Data")
        {

		var tbody = document.getElementById("tblList");
		var table = document.getElementById("Existing");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var len6 = baseResponse.getElementsByTagName("Cheque_No").length;
               // alert("dddd"+len6);
		if (len6 != 0) {
			for ( var k = 0; k < len6; k++) {
				var Acc_Unit_id = baseResponse
						.getElementsByTagName("Acc_Unit_id")[k].firstChild.nodeValue;
				var Acc_Office_id = baseResponse
						.getElementsByTagName("Acc_Office_id")[k].firstChild.nodeValue;
				var Cb_year = baseResponse.getElementsByTagName("Cb_year")[k].firstChild.nodeValue;
				var Cb_Month = baseResponse.getElementsByTagName("Cb_Month")[k].firstChild.nodeValue;
				var Acc_no = baseResponse.getElementsByTagName("Acc_no")[k].firstChild.nodeValue;
				var Doc_Type = baseResponse.getElementsByTagName("Doc_Type")[k].firstChild.nodeValue;
				var Cheque_No1 = baseResponse.getElementsByTagName("Cheque_No")[k].firstChild.nodeValue;
				var Cheque_No2 = baseResponse.getElementsByTagName("Cheque_No")[k].firstChild.nodeValue;
				var Doc_No = baseResponse.getElementsByTagName("Doc_No")[k].firstChild.nodeValue;
				var ChequeDate = baseResponse
						.getElementsByTagName("ChequeDate")[k].firstChild.nodeValue;
				var DocDate = baseResponse.getElementsByTagName("DocDate")[k].firstChild.nodeValue;
				var ChequeAmount = baseResponse
						.getElementsByTagName("ChequeAmount")[k].firstChild.nodeValue;
				var CheckValidUpto = baseResponse
						.getElementsByTagName("CheckValidUpto")[k].firstChild.nodeValue;

				var Follow_Up = baseResponse.getElementsByTagName("Follow_Up")[k].firstChild.nodeValue;
				if (Follow_Up == 'null') {
					Follow_Up = "";
				}
				var Cleared_Entries = baseResponse
						.getElementsByTagName("Cleared_Entries")[k].firstChild.nodeValue;
				var ClearedDate = baseResponse
						.getElementsByTagName("ClearedDate")[k].firstChild.nodeValue;

				if (ClearedDate == "30/11/2") {
					ClearedDate = "";
				}

				var tbody = document.getElementById("tblList");
				var table = document.getElementById("Existing");
				var mycurrent_row = document.createElement("TR");
				mycurrent_row.id = Cheque_No1;

				var cell = document.createElement("TD");
				var Acc_Unit_id = document.createTextNode(Acc_Unit_id);
				cell.appendChild(Acc_Unit_id);
				mycurrent_row.appendChild(cell);

				var cell2 = document.createElement("TD");
				var Acc_Office_id = document.createTextNode(Acc_Office_id);
				cell2.appendChild(Acc_Office_id);
				mycurrent_row.appendChild(cell2);

				var cell3 = document.createElement("TD");
				var Cb_year = document.createTextNode(Cb_year);
				cell3.appendChild(Cb_year);
				mycurrent_row.appendChild(cell3);

				var cell4 = document.createElement("TD");
				var Cb_Month = document.createTextNode(Cb_Month);
				cell4.appendChild(Cb_Month);
				mycurrent_row.appendChild(cell4);

				var cell5 = document.createElement("TD");
				var Acc_no = document.createTextNode(Acc_no);
				cell5.appendChild(Acc_no);
				mycurrent_row.appendChild(cell5);

				var cell6 = document.createElement("TD");
				var Doc_Type = document.createTextNode(Doc_Type);
				cell6.appendChild(Doc_Type);
				mycurrent_row.appendChild(cell6);

				var cell7 = document.createElement("TD");
				var Cheque_No1 = document.createTextNode(Cheque_No1);
				cell7.appendChild(Cheque_No1);
				mycurrent_row.appendChild(cell7);

				var cell8 = document.createElement("TD");
				var Doc_No = document.createTextNode(Doc_No);
				cell8.appendChild(Doc_No);
				mycurrent_row.appendChild(cell8);

				var cell9 = document.createElement("TD");
				var ChequeDate = document.createTextNode(ChequeDate);
				cell9.appendChild(ChequeDate);
				mycurrent_row.appendChild(cell9);

				var cel20 = document.createElement("TD");
				var DocDate = document.createTextNode(DocDate);
				cel20.appendChild(DocDate);
				mycurrent_row.appendChild(cel20);

				var cel21 = document.createElement("TD");
				var ChequeAmount = document.createTextNode(ChequeAmount);
				cel21.appendChild(ChequeAmount);
				mycurrent_row.appendChild(cel21);

				var cel22 = document.createElement("TD");
				var CheckValidUpto = document.createTextNode(CheckValidUpto);
				cel22.appendChild(CheckValidUpto);
				mycurrent_row.appendChild(cel22);

				var cel23 = document.createElement("TD");
				var Follow_Up = document.createTextNode(Follow_Up);
				cel23.appendChild(Follow_Up);
				mycurrent_row.appendChild(cel23);

				var cel24 = document.createElement("TD");
				var Cleared_Entries = document.createTextNode(Cleared_Entries);
				cel24.appendChild(Cleared_Entries);
				mycurrent_row.appendChild(cel24);

				var cel25 = document.createElement("TD");
				var ClearedDate = document.createTextNode(ClearedDate);
				cel25.appendChild(ClearedDate);
				mycurrent_row.appendChild(cel25);

				var cell26 = document.createElement("TD");
				var anc = document.createElement("A");
				var url = "javascript:Edit('" + Cheque_No2 + "')"

				anc.href = url;
				var txtedit = document.createTextNode("EDIT");
				anc.appendChild(txtedit);
				cell26.appendChild(anc);
				mycurrent_row.appendChild(cell26);

				tbody.appendChild(mycurrent_row);
			}
		} else {
			alert("Record Does Not Exist for Given Cashbook Month & Year");
			refresh();
		}
                }
                else
                {
                alert("No Data Found");
                }
	}
}

function Edit(Cheque_No2) {	
	r = document.getElementById(Cheque_No2);
	rcells = r.cells;

	var AccountingUnit = rcells.item(0).firstChild.nodeValue;
	var AccountingForOffice = rcells.item(1).firstChild.nodeValue;
	var Cb_year = rcells.item(2).firstChild.nodeValue;
	var Cb_Month = rcells.item(3).firstChild.nodeValue;
	var Acc_no = rcells.item(4).firstChild.nodeValue;
	var Doc_Type = rcells.item(5).firstChild.nodeValue;
	var Cheque_No1 = rcells.item(6).firstChild.nodeValue;
	var Doc_No = rcells.item(7).firstChild.nodeValue;
	var ChequeDate = rcells.item(8).firstChild.nodeValue;
	var DocDate = rcells.item(9).firstChild.nodeValue;
	var ChequeAmount = rcells.item(10).firstChild.nodeValue;
	var CheckValidUpto = rcells.item(11).firstChild.nodeValue;

	var Follow_Up = rcells.item(12).firstChild.nodeValue;
	var Cleared_Entries = rcells.item(13).firstChild.nodeValue;
	var ClearedDate = rcells.item(14).firstChild.nodeValue;

	exitfun();
	opener.ParentDrawing(AccountingUnit, AccountingForOffice, Cb_year,
			Cb_Month, Acc_no, Doc_Type, Cheque_No1, Doc_No, ChequeDate,
			DocDate, ChequeAmount, CheckValidUpto, Follow_Up, Cleared_Entries,
			ClearedDate);
}

function ParentDrawing(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13,
		v14, v15) {	

	var url ="../../../../../BRS_Barred_Cheque_Master?command=ParentDrawing&v1=" + v1
			+ "&v2=" + v2 + "&v3=" + v3 + "&v4=" + v4 + "&v5=" + v5 + "&v6="
			+ v6 + "&v7=" + v7 + "&v8=" + v8 + "&v9=" + v9 + "&v10=" + v10
			+ "&v11=" + v11 + "&v12=" + v12 + "&v13=" + v13 + "&v14=" + v14
			+ "&v15=" + v15;

	//alert(url);
	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);

	xmlrequest.onreadystatechange = function() {
		manipulate_first(xmlrequest);
	}
	xmlrequest.send(null);

}

function ParentDrawing1(baseResponse) {
	document.getElementById("txtDoc_No").length = 1;	
	var DocNo1 = baseResponse.getElementsByTagName("DocNo");			
	if (DocNo1.length != 0) {
		for ( var k = 0; k < DocNo1.length; k++) {
			var DocNo = baseResponse.getElementsByTagName("DocNo")[k].firstChild.nodeValue;
			var se = document.getElementById("txtDoc_No");
			var op = document.createElement("OPTION");
			op.value = DocNo;
			var txt = document.createTextNode(DocNo);
			op.appendChild(txt);
			se.appendChild(op);
		}
	}	
	var v1 = baseResponse.getElementsByTagName("v1")[0].firstChild.nodeValue;
	var v2 = baseResponse.getElementsByTagName("v2")[0].firstChild.nodeValue;
	var v3 = baseResponse.getElementsByTagName("v3")[0].firstChild.nodeValue;
	var v4 = baseResponse.getElementsByTagName("v4")[0].firstChild.nodeValue;
	var v5 = baseResponse.getElementsByTagName("v5")[0].firstChild.nodeValue;
	var v6 = baseResponse.getElementsByTagName("v6")[0].firstChild.nodeValue;
	var v7 = baseResponse.getElementsByTagName("v7")[0].firstChild.nodeValue;
	var v8 = baseResponse.getElementsByTagName("v8")[0].firstChild.nodeValue;
	var v9 = baseResponse.getElementsByTagName("v9")[0].firstChild.nodeValue;
	var v10 = baseResponse.getElementsByTagName("v10")[0].firstChild.nodeValue;
	var v11 = baseResponse.getElementsByTagName("v11")[0].firstChild.nodeValue;
	var v12 = baseResponse.getElementsByTagName("v12")[0].firstChild.nodeValue;
	
	var v13 = baseResponse.getElementsByTagName("v13")[0].firstChild.nodeValue;
	if(v13=='null')
		{
		v13="";
		}
	var v14 = baseResponse.getElementsByTagName("v14")[0].firstChild.nodeValue;
	if(v14=='null')
	{
	v14="";
	}
	var v15 = baseResponse.getElementsByTagName("v15")[0].firstChild.nodeValue;
	if(v15=='null')
	{
	v15="";
	}
	document.frmBarred_Cheque_Master.cmbAcc_UnitCode.value = v1;
	document.frmBarred_Cheque_Master.cmbOffice_code.value = v2;
	document.frmBarred_Cheque_Master.txtCB_Year.value = v3;
	document.frmBarred_Cheque_Master.txtCB_Month.value = v4;
	document.frmBarred_Cheque_Master.cmbBankAccNo.value = v5;
	document.frmBarred_Cheque_Master.cmbDoc_Type.value = v6;
	document.frmBarred_Cheque_Master.cmbCheque_No.value = v7;
	document.frmBarred_Cheque_Master.txtDoc_No.value = v8;
	document.frmBarred_Cheque_Master.txtDoc_Date.value = v9;
	document.frmBarred_Cheque_Master.txtCheque_Date.value = v10;
	document.frmBarred_Cheque_Master.txtCheque_Amount.value = v11;
	document.frmBarred_Cheque_Master.txtCheque_Valid_Upto.value = v12;
	document.frmBarred_Cheque_Master.mtxtFollowup_Action.value = v13;
	if (v14 == "Y") {
		document.frmBarred_Cheque_Master.txtCleared_Entries[0].checked = true;
	} else {
		document.frmBarred_Cheque_Master.txtCleared_Entries[1].checked = true;
	}

	document.frmBarred_Cheque_Master.txtCleared_Date.value = v15;

	document.frmBarred_Cheque_Master.onsubmit1.disabled = true;
	document.frmBarred_Cheque_Master.butUpdate.disabled = false;
	document.frmBarred_Cheque_Master.butDelete.disabled = false;
}

function refresh() {
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frmBarred_Cheque_Master.txtCB_Year.value = year;
	document.frmBarred_Cheque_Master.txtCB_Month.value = month;
	document.getElementById("cmbBankAccNo").value = "";
	document.getElementById("cmbDoc_Type").value = "s";
	document.getElementById("cmbCheque_No").value = "";
	document.getElementById("txtDoc_No").length = 1;
	document.getElementById("txtDoc_Date").value = "";
	document.getElementById("txtCheque_Date").value = "";
	document.getElementById("txtCheque_Amount").value = "";
	document.getElementById("txtCheque_Valid_Upto").value = "";
	document.getElementById("mtxtFollowup_Action").value = "";
	document.getElementById("txtCleared_Entries").value = "";
	document.getElementById("txtCleared_Date").value = "";

	document.frmBarred_Cheque_Master.onsubmit1.disabled = false;
	document.frmBarred_Cheque_Master.butUpdate.disabled = true;
	document.frmBarred_Cheque_Master.butDelete.disabled = true;
}

function exitfun() {
	window.close();
}
