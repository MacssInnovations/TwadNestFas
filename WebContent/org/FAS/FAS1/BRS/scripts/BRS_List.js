var seq=0;
var seq1=0;
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

function changeTab()
{
 if(document.frmBRSList.yearid[0].checked==true)
 {
 document.getElementById("passyear").style.display="block";
 document.getElementById("passmonth").style.display="block";
 document.getElementById("cashyear").style.display="none";
 document.getElementById("cashmonth").style.display="none";
 
 document.getElementById("passdividlist").style.display="block";
 document.getElementById("cashdividlist").style.display="none";
 
 }
 else
 {
  document.getElementById("passyear").style.display="none";
 document.getElementById("passmonth").style.display="none";
 document.getElementById("cashyear").style.display="block";
 document.getElementById("cashmonth").style.display="block";
 
 document.getElementById("passdividlist").style.display="none";
 document.getElementById("cashdividlist").style.display="block";
 }

}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			//alert("manipulate-command:--->>>"+command);

			if (command == "ListAll") {
				LoadList(baseResponse);
			}
                        else if (command == "ListAllPassSheet") {
				LoadList(baseResponse);
			}
                        else if (command == "ListAll_acknowledged") {
				LoadList(baseResponse);
			}
                         else if (command == "ListAllCash") {
				LoadList(baseResponse);
			}
                         else if(command=="ListAllBrs"){
                             Load_Brscashequalpass(baseResponse);
                         }
                         else if(command=="ListAll_acknowledged_Brs"){
                          	Load_Brscashequalpass(baseResponse);
                                     	 
                         }  else if(command=="ListAllPassSheetBrs"){
                          	Load_Brscashequalpass(baseResponse);
                         }
                         else if(command=="ListAllCashBrs"){
                 			
                          	Load_Brscashequalpass(baseResponse);
                         }
                        
		}
	}
}
function ListAllCashBrs(path) {
    var tbody = document.getElementById("grid_body_TWAD");
var rowcount=tbody.rows.length;
for(var i=0;i<rowcount;i++)
    {
	 var r=i.rowIndex;	   
       tbody.deleteRow(r);
    }
var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
var cmbOffice_code = document.getElementById("cmbOffice_code").value;
var txtCB_Year = document.getElementById("txtCB_Year_one").value;
var txtCB_Month = document.getElementById("txtCB_Month_one").value;
var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;

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

	var url = path + "/BRS_List?command=ListAllCashBrs&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
			+ "&cmbBankAccNo=" + cmbBankAccNo;

	var xmlrequest = AjaxFunction();

	xmlrequest.open("POST", url, true);
                 document.getElementById("imgfld").style.visibility = "visible";
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};
	xmlrequest.send(null);
}
}


function ListAllBrs(path) {
            var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
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
				var url = path + "/BRS_List?command=ListAllBrs&cmbAcc_UnitCode="
						+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
						+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
						+ "&cmbBankAccNo=" + cmbBankAccNo;
	
				var xmlrequest = AjaxFunction();
	
				xmlrequest.open("POST", url, true);
	                         document.getElementById("imgfld").style.visibility = "visible";
				xmlrequest.onreadystatechange = function() {
					manipulate(xmlrequest);
				};
				xmlrequest.send(null);
			
		}
	}

function printAll_BRS(path,val) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Month_one = document.getElementById("txtCB_Month_one").value;
	var txtCB_Year_one = document.getElementById("txtCB_Year_one").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var cmbBankAccNo_text=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
	var typelist=document.getElementById("typelist").value;
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
	
		var url = path + "/BRS_List?command=printAllBRS&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&txtCB_Year_one=" + txtCB_Year_one + "&txtCB_Month_one=" + txtCB_Month_one
				+ "&cmbBankAccNo=" + cmbBankAccNo+"&trans="+val+"&typelist="+typelist+"&cmbBankAccNo_text="+cmbBankAccNo_text;
		//alert(url);
		document.frmBRSList.method="POST";
		document.frmBRSList.action=url;
		document.frmBRSList.submit();
	}
}

function ListAllPassSheet_Brs(path) {
	
                var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
          //  document.getElementById("gd_bank").style.display="none";
               //  bankTrn.style.display="none";
	    //alert(path);	    
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		
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

			var url = path + "/BRS_List?command=ListAllPassSheetBrs&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			
			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);
                         document.getElementById("imgfld").style.visibility = "visible";
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
	}
function ListAll_acknowledged_BRS(path) 
{
          var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
	    //alert(path);	    
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		
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

			var url = path + "/BRS_List?command=ListAll_acknowledged_Brs&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);
                         document.getElementById("imgfld").style.visibility = "visible";
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
		
}


function Load_Brscashequalpass(baseResponse)
{
   // alert("test");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	if(count==0){
		alert('No Records !!!!!');
	}
	else{
	if (flag == "success") {
		document.getElementById("tgrid").style.display="block";
		
		
		  var tbody = document.getElementById("grid_body_TWAD");
		  var rowcount=tbody.rows.length;
		    for(var i=0;i<rowcount;i++)
		        {
		    	 var r=i.rowIndex;	   
		           tbody.deleteRow(r);
		        }	  
		    
			/*  var tbody1 = document.getElementById("grid_body_NONTWAD");
			  var rowcount1=tbody1.rows.length;
			    for(var i=0;i<rowcount1;i++)
			        {
			    	 var r1=i.rowIndex;	   
			           tbody1.deleteRow(r1);
			        }	*/
		var len = baseResponse.getElementsByTagName("RemitanceDate").length;
		for ( var k = 0; k < len; k++) {
			var RemitanceDate = baseResponse.getElementsByTagName("RemitanceDate")[k].firstChild.nodeValue;
			if(RemitanceDate == 'null' )
        	{
				RemitanceDate="-";
        	}  
			var WithdrawlDate = baseResponse.getElementsByTagName("WithdrawlDate")[k].firstChild.nodeValue;
			if(WithdrawlDate == 'null' )
        	{
				WithdrawlDate="-";
        	}
			var Voucher_or_ChallanNo = baseResponse.getElementsByTagName("Voucher_or_ChallanNo")[k].firstChild.nodeValue;
			if(Voucher_or_ChallanNo == 'null' )
        	{
				Voucher_or_ChallanNo="-";
        	}
			var Cheqe_or_DDNo = baseResponse.getElementsByTagName("Cheqe_or_DDNo")[k].firstChild.nodeValue;
			if(Cheqe_or_DDNo == 'null' )
        	{
				Cheqe_or_DDNo="-";
        	}
			var CRAmount = baseResponse.getElementsByTagName("CRAmount")[k].firstChild.nodeValue;
			if(CRAmount == 'null' )
        	{
				CRAmount="-";
        	}
			var DRAmount = baseResponse.getElementsByTagName("DRAmount")[k].firstChild.nodeValue;
			if(DRAmount == 'null' )
        	{
				DRAmount="-";
        	}
			var EntryFoundInPassBook = baseResponse.getElementsByTagName("EntryFoundInPassBook")[k].firstChild.nodeValue;
			if(EntryFoundInPassBook == 'null' )
        	{
				EntryFoundInPassBook="-";
        	}
			var EntryDate = baseResponse.getElementsByTagName("EntryDate")[k].firstChild.nodeValue;
			if(EntryDate == 'null' )
        	{
				EntryDate="-";
        	}
			var Amount = baseResponse.getElementsByTagName("Amount")[k].firstChild.nodeValue;
			if(Amount == 'null' )
        	{
				Amount="-";
        	}
			var Difference = baseResponse.getElementsByTagName("Difference")[k].firstChild.nodeValue;
			if(Difference == 'null' )
        	{
				Difference="-";
        	}
			var RsnForDiff = baseResponse.getElementsByTagName("RsnForDiff")[k].firstChild.nodeValue;
			if(RsnForDiff == 'null' )
        	{
				RsnForDiff="-";
        	}
			var FollowUpAction = baseResponse.getElementsByTagName("FollowUpAction")[k].firstChild.nodeValue;
			if(FollowUpAction == 'null' )
        	{
				FollowUpAction="-";
        	}
			var Clearance = baseResponse.getElementsByTagName("Clearance")[k].firstChild.nodeValue;
			if(Clearance == 'null' )
        	{
				Clearance="-";
        	}
			var doc_no = baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;
			if(doc_no == 'null' )
        	{
				doc_no="-";
        	}
			var doc_type = baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;
			if(doc_type == 'null' )
        	{
				doc_type="-";
        	}
			var tbody = document.getElementById("grid_body_TWAD");
			var table = document.getElementById("mytable");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
			
			/** Sl No */
        	var cell0 = document.createElement("TD");
 			var slno = document.createTextNode(seq+1);
 			cell0.appendChild(slno);
 			mycurrent_row.appendChild(cell0);
 			
			var cell2 = document.createElement("TD");
			var RemitanceDate = document.createTextNode(RemitanceDate);
			cell2.appendChild(RemitanceDate);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var WithdrawlDate = document.createTextNode(WithdrawlDate);
			cell3.appendChild(WithdrawlDate);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var Voucher_or_ChallanNo = document.createTextNode(Voucher_or_ChallanNo);
			cell4.appendChild(Voucher_or_ChallanNo);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
			var Cheqe_or_DDNo = document.createTextNode(Cheqe_or_DDNo);
			cell5.appendChild(Cheqe_or_DDNo);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var CRAmount = document.createTextNode(CRAmount);
			cell6.appendChild(CRAmount);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			var DRAmount = document.createTextNode(DRAmount);
			cell7.appendChild(DRAmount);
			mycurrent_row.appendChild(cell7);

			var cell9 = document.createElement("TD");
			var EntryDate = document.createTextNode(EntryDate);
			cell9.appendChild(EntryDate);
			mycurrent_row.appendChild(cell9);

			var cel20 = document.createElement("TD");
			var Amount = document.createTextNode(Amount);
			cel20.appendChild(Amount);
			mycurrent_row.appendChild(cel20);
			
			var cel21 = document.createElement("TD");
			var Difference = document.createTextNode(Difference);
			cel21.appendChild(Difference);
			mycurrent_row.appendChild(cel21);
			
			var cel22 = document.createElement("TD");
			var RsnForDiff = document.createTextNode(RsnForDiff);
			cel22.appendChild(RsnForDiff);
			mycurrent_row.appendChild(cel22);

			var cel23 = document.createElement("TD");
			var FollowUpAction = document.createTextNode(FollowUpAction);
			cel23.appendChild(FollowUpAction);
			mycurrent_row.appendChild(cel23);

			var cel24 = document.createElement("TD");
			var Clearance = document.createTextNode(Clearance);
			cel24.appendChild(Clearance);
			mycurrent_row.appendChild(cel24);			

			tbody.appendChild(mycurrent_row);
			seq++;
		}
		
      //  document.getElementById("imgfld").style.visibility = "hidden";
		document.getElementById('txtNoofRecords').value=seq;
	
		document.getElementById("div1").style.visibility='visible';
		document.getElementById('crAmount').value=baseResponse.getElementsByTagName("crAmount")[0].firstChild.nodeValue;
		document.getElementById('drAmount').value=baseResponse.getElementsByTagName("drAmount")[0].firstChild.nodeValue;
		/*seq = 0;
		var len1 = baseResponse.getElementsByTagName("PassbookDate1").length;
		
		for ( var k = 0; k < len1; k++) {
			var PassbookDate1 = baseResponse.getElementsByTagName("PassbookDate1")[k].firstChild.nodeValue;
			if(PassbookDate1 == 'null' )
        	{
				PassbookDate1="-";
        	}  
			var Particulars1 = baseResponse.getElementsByTagName("Particulars1")[k].firstChild.nodeValue;
			if(Particulars1 == 'null' )
        	{
				Particulars1="-";
        	}
			var Cheqe_or_DDNo1 = baseResponse.getElementsByTagName("Cheqe_or_DDNo1")[k].firstChild.nodeValue;
			if(Cheqe_or_DDNo1 == 'null' )
        	{
				Cheqe_or_DDNo1="-";
        	}
			var Details1 = baseResponse.getElementsByTagName("Details1")[k].firstChild.nodeValue;
			if(Details1 == 'null' )
        	{
				Details1="-";
        	}
			var CRAmount1 = baseResponse.getElementsByTagName("CRAmount1")[k].firstChild.nodeValue;
			if(CRAmount1 == 'null' )
        	{
				CRAmount1="-";
        	}
			var DRAmount1 = baseResponse.getElementsByTagName("DRAmount1")[k].firstChild.nodeValue;
			//alert("DRAmount1:::DRAmount1:"+DRAmount1);
			if(DRAmount1 == 'null' )
        	{
				DRAmount1="-";
        	}
                var TypeOfTransaction1 = baseResponse.getElementsByTagName("RsnForDiff")[k].firstChild.nodeValue;
         
			if(TypeOfTransaction1 == 'null' )
        	{
				TypeOfTransaction1="-";
        	}
			var FollowUpAction1 = baseResponse.getElementsByTagName("FollowUpAction1")[k].firstChild.nodeValue;
			if(FollowUpAction1 == 'null' )
        	{
				FollowUpAction1="-";
        	}
			var Clearance1 = baseResponse.getElementsByTagName("Clearance1")[k].firstChild.nodeValue;
			if(Clearance1 == 'null' )
        	{
				Clearance1="-";
        	}
			
			var tbody = document.getElementById("grid_body_NONTWAD");
			var table = document.getElementById("mytable_details");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq1;
			
			*//** Sl No *//*
        	var cell0 = document.createElement("TD");
 			var slno = document.createTextNode(seq1+1);
 			cell0.appendChild(slno);
 			mycurrent_row.appendChild(cell0);
 			
			var cell2 = document.createElement("TD");
			var PassbookDate1 = document.createTextNode(PassbookDate1);
			cell2.appendChild(PassbookDate1);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var Particulars1 = document.createTextNode(Particulars1);
			cell3.appendChild(Particulars1);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var Cheqe_or_DDNo1 = document.createTextNode(Cheqe_or_DDNo1);
			cell4.appendChild(Cheqe_or_DDNo1);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
			var Details1 = document.createTextNode(Details1);
			cell5.appendChild(Details1);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var CRAmount1 = document.createTextNode(CRAmount1);
			cell6.appendChild(CRAmount1);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			var DRAmount1 = document.createTextNode(DRAmount1);
			cell7.appendChild(DRAmount1);
			mycurrent_row.appendChild(cell7);

			var cell9 = document.createElement("TD");
			var TypeOfTransaction1 = document.createTextNode(TypeOfTransaction1);
			cell9.appendChild(TypeOfTransaction1);
			mycurrent_row.appendChild(cell9);
			
			var cel23 = document.createElement("TD");
			var FollowUpAction1 = document.createTextNode(FollowUpAction1);
			cel23.appendChild(FollowUpAction1);
			mycurrent_row.appendChild(cel23);

			var cel24 = document.createElement("TD");
			var Clearance1 = document.createTextNode(Clearance1);
			cel24.appendChild(Clearance1);
			mycurrent_row.appendChild(cel24);			

			tbody.appendChild(mycurrent_row);	
			seq1++;
		}
               document.getElementById('txtNoofRecords').value=seq;
		document.getElementById("div1").style.visibility='visible';
		document.getElementById('crAmount').value=baseResponse.getElementsByTagName("crAmount")[0].firstChild.nodeValue;
		document.getElementById('drAmount').value=baseResponse.getElementsByTagName("drAmount")[0].firstChild.nodeValue;
		seq1 = 0;*/
	} 
	}
         document.getElementById("imgfld").style.visibility = "hidden";
	
}


function ListAllCash(path) {
	//    alert(path);
            var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year_one").value;
		var txtCB_Month = document.getElementById("txtCB_Month_one").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		
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

			var url = path + "/BRS_List?command=ListAllCash&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			alert(url);
			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);
                         document.getElementById("imgfld").style.visibility = "visible";
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
	}


function ListAll(path) {
            var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
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
				var url = path + "/BRS_List?command=ListAll&cmbAcc_UnitCode="
						+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
						+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
						+ "&cmbBankAccNo=" + cmbBankAccNo;
	
				//alert(url);
				var xmlrequest = AjaxFunction();
	
				xmlrequest.open("POST", url, true);
	                         document.getElementById("imgfld").style.visibility = "visible";
				xmlrequest.onreadystatechange = function() {
					manipulate(xmlrequest);
				};
				xmlrequest.send(null);
			
		}
	}
function printAll(path,val) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var cmbBankAccNo_text=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
	var typelist=document.getElementById("typelist").value;
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
	
		var url = path + "/BRS_List?command=printAll&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo+"&trans="+val+"&typelist="+typelist+"&cmbBankAccNo_text="+cmbBankAccNo_text;
		document.frmBRSList.method="POST";
		document.frmBRSList.action=url;
		document.frmBRSList.submit();
	}
}
function ListAllPassSheet(path) {
                var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
          //  document.getElementById("gd_bank").style.display="none";
               //  bankTrn.style.display="none";
	    //alert(path);	    
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		
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

			var url = path + "/BRS_List?command=ListAllPassSheet&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			//alert(url);
			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);
                         document.getElementById("imgfld").style.visibility = "visible";
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
	}

function ListAll_acknowledged(path) {
          var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
	    //alert(path);	    
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		
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

			var url = path + "/BRS_List?command=ListAll_acknowledged&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			//alert(url);
			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);
                         document.getElementById("imgfld").style.visibility = "visible";
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
	}

function LoadList(baseResponse) {    
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		
		  var tbody = document.getElementById("grid_body_TWAD");
		  var rowcount=tbody.rows.length;
		    for(var i=0;i<rowcount;i++)
		        {
		    	 var r=i.rowIndex;	   
		           tbody.deleteRow(r);
		        }	  
		    
			  var tbody1 = document.getElementById("grid_body_NONTWAD");
			  var rowcount1=tbody1.rows.length;
			    for(var i=0;i<rowcount1;i++)
			        {
			    	 var r1=i.rowIndex;	   
			           tbody1.deleteRow(r1);
			        }	
		var len = baseResponse.getElementsByTagName("RemitanceDate").length;
//		alert("remittancedate length"+len);
		for ( var k = 0; k < len; k++) {
			var RemitanceDate = baseResponse.getElementsByTagName("RemitanceDate")[k].firstChild.nodeValue;
			if(RemitanceDate == 'null' )
        	{
				RemitanceDate="-";
        	}  
			var WithdrawlDate = baseResponse.getElementsByTagName("WithdrawlDate")[k].firstChild.nodeValue;
			if(WithdrawlDate == 'null' )
        	{
				WithdrawlDate="-";
        	}
			var Voucher_or_ChallanNo = baseResponse.getElementsByTagName("Voucher_or_ChallanNo")[k].firstChild.nodeValue;
			if(Voucher_or_ChallanNo == 'null' )
        	{
				Voucher_or_ChallanNo="-";
        	}
			var Cheqe_or_DDNo = baseResponse.getElementsByTagName("Cheqe_or_DDNo")[k].firstChild.nodeValue;
			if(Cheqe_or_DDNo == 'null' )
        	{
				Cheqe_or_DDNo="-";
        	}
			var CRAmount = baseResponse.getElementsByTagName("CRAmount")[k].firstChild.nodeValue;
			if(CRAmount == 'null' )
        	{
				CRAmount="-";
        	}
			var DRAmount = baseResponse.getElementsByTagName("DRAmount")[k].firstChild.nodeValue;
			if(DRAmount == 'null' )
        	{
				DRAmount="-";
        	}
			var EntryFoundInPassBook = baseResponse.getElementsByTagName("EntryFoundInPassBook")[k].firstChild.nodeValue;
			if(EntryFoundInPassBook == 'null' )
        	{
				EntryFoundInPassBook="-";
        	}
			var EntryDate = baseResponse.getElementsByTagName("EntryDate")[k].firstChild.nodeValue;
			if(EntryDate == 'null' )
        	{
				EntryDate="-";
        	}
			var Amount = baseResponse.getElementsByTagName("Amount")[k].firstChild.nodeValue;
			if(Amount == 'null' )
        	{
				Amount="-";
        	}
			var Difference = baseResponse.getElementsByTagName("Difference")[k].firstChild.nodeValue;
			if(Difference == 'null' )
        	{
				Difference="-";
        	}
			var RsnForDiff = baseResponse.getElementsByTagName("RsnForDiff")[k].firstChild.nodeValue;
			if(RsnForDiff == 'null' )
        	{
				RsnForDiff="-";
        	}
			var FollowUpAction = baseResponse.getElementsByTagName("FollowUpAction")[k].firstChild.nodeValue;
			if(FollowUpAction == 'null' )
        	{
				FollowUpAction="-";
        	}
			var Clearance = baseResponse.getElementsByTagName("Clearance")[k].firstChild.nodeValue;
			if(Clearance == 'null' )
        	{
				Clearance="-";
        	}
			var doc_no = baseResponse.getElementsByTagName("doc_no")[k].firstChild.nodeValue;
			if(doc_no == 'null' )
        	{
				doc_no="-";
        	}
			var doc_type = baseResponse.getElementsByTagName("doc_type")[k].firstChild.nodeValue;
			if(doc_type == 'null' )
        	{
				doc_type="-";
        	}
			var tbody = document.getElementById("grid_body_TWAD");
			var table = document.getElementById("mytable");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
			
			/** Sl No */
        	var cell0 = document.createElement("TD");
 			var slno = document.createTextNode(seq+1);
 			cell0.appendChild(slno);
 			mycurrent_row.appendChild(cell0);
 			
			var cell2 = document.createElement("TD");
			var RemitanceDate = document.createTextNode(RemitanceDate);
			cell2.appendChild(RemitanceDate);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var WithdrawlDate = document.createTextNode(WithdrawlDate);
			cell3.appendChild(WithdrawlDate);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var Voucher_or_ChallanNo = document.createTextNode(Voucher_or_ChallanNo);
			cell4.appendChild(Voucher_or_ChallanNo);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
			var Cheqe_or_DDNo = document.createTextNode(Cheqe_or_DDNo);
			cell5.appendChild(Cheqe_or_DDNo);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			cell6.setAttribute('align','right');
			var CRAmount = document.createTextNode(CRAmount);
			cell6.appendChild(CRAmount);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			cell7.setAttribute('align','right');
			var DRAmount = document.createTextNode(DRAmount);
			cell7.appendChild(DRAmount);
			mycurrent_row.appendChild(cell7);

			var cell9 = document.createElement("TD");
			var EntryDate = document.createTextNode(EntryDate);
			cell9.appendChild(EntryDate);
			mycurrent_row.appendChild(cell9);

			var cel20 = document.createElement("TD");
			cel20.setAttribute('align','right');
			var Amount = document.createTextNode(Amount);
			cel20.appendChild(Amount);
			mycurrent_row.appendChild(cel20);
			
			var cel21 = document.createElement("TD");
			var Difference = document.createTextNode(Difference);
			cel21.appendChild(Difference);
			mycurrent_row.appendChild(cel21);
			
			//alert(RsnForDiff);
			var cel22 = document.createElement("TD");
			var RsnForDiff = document.createTextNode(RsnForDiff);
			cel22.appendChild(RsnForDiff);
			mycurrent_row.appendChild(cel22);

			var cel23 = document.createElement("TD");
			var FollowUpAction = document.createTextNode(FollowUpAction);
			cel23.appendChild(FollowUpAction);
			mycurrent_row.appendChild(cel23);

			var cel24 = document.createElement("TD");
			var Clearance = document.createTextNode(Clearance);
			cel24.appendChild(Clearance);
			mycurrent_row.appendChild(cel24);			

			tbody.appendChild(mycurrent_row);
			seq++;
		}
      //  document.getElementById("imgfld").style.visibility = "hidden";
		document.getElementById('txtNoofRecords').value=seq;
		document.getElementById("div1").style.visibility='visible';
		document.getElementById('crAmount').value=baseResponse.getElementsByTagName("crAmount")[0].firstChild.nodeValue;
		document.getElementById('drAmount').value=baseResponse.getElementsByTagName("drAmount")[0].firstChild.nodeValue;
		seq = 0;
		var len1 = baseResponse.getElementsByTagName("PassbookDate1").length;
		
		for ( var k = 0; k < len1; k++) {
			var PassbookDate1 = baseResponse.getElementsByTagName("PassbookDate1")[k].firstChild.nodeValue;
			if(PassbookDate1 == 'null' )
        	{
				PassbookDate1="-";
        	}  
			var Particulars1 = baseResponse.getElementsByTagName("Particulars1")[k].firstChild.nodeValue;
			if(Particulars1 == 'null' )
        	{
				Particulars1="-";
        	}
			var Cheqe_or_DDNo1 = baseResponse.getElementsByTagName("Cheqe_or_DDNo1")[k].firstChild.nodeValue;
			if(Cheqe_or_DDNo1 == 'null' )
        	{
				Cheqe_or_DDNo1="-";
        	}
			var Details1 = baseResponse.getElementsByTagName("Details1")[k].firstChild.nodeValue;
			if(Details1 == 'null' )
        	{
				Details1="-";
        	}
			var CRAmount1 = baseResponse.getElementsByTagName("CRAmount1")[k].firstChild.nodeValue;
			if(CRAmount1 == 'null' )
        	{
				CRAmount1="-";
        	}
			var DRAmount1 = baseResponse.getElementsByTagName("DRAmount1")[k].firstChild.nodeValue;
			//alert("DRAmount1:::DRAmount1:"+DRAmount1);
			if(DRAmount1 == 'null' )
        	{
				DRAmount1="-";
        	}
                var TypeOfTransaction1 = baseResponse.getElementsByTagName("RsnForDiff1")[k].firstChild.nodeValue;
                //alert(TypeOfTransaction1);
			if(TypeOfTransaction1 == 'null' )
        	{
				TypeOfTransaction1="-";
        	}
			var FollowUpAction1 = baseResponse.getElementsByTagName("FollowUpAction1")[k].firstChild.nodeValue;
			if(FollowUpAction1 == 'null' )
        	{
				FollowUpAction1="-";
        	}
			var Clearance1 = baseResponse.getElementsByTagName("Clearance1")[k].firstChild.nodeValue;
			if(Clearance1 == 'null' )
        	{
				Clearance1="-";
        	}
			
			var tbody = document.getElementById("grid_body_NONTWAD");
			var table = document.getElementById("mytable_details");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq1;
			
			/** Sl No */
        	var cell0 = document.createElement("TD");
 			var slno = document.createTextNode(seq1+1);
 			cell0.appendChild(slno);
 			mycurrent_row.appendChild(cell0);
 			
			var cell2 = document.createElement("TD");
			var PassbookDate1 = document.createTextNode(PassbookDate1);
			cell2.appendChild(PassbookDate1);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var Particulars1 = document.createTextNode(Particulars1);
			cell3.appendChild(Particulars1);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var Cheqe_or_DDNo1 = document.createTextNode(Cheqe_or_DDNo1);
			cell4.appendChild(Cheqe_or_DDNo1);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
			var Details1 = document.createTextNode(Details1);
			cell5.appendChild(Details1);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var CRAmount1 = document.createTextNode(CRAmount1);
			cell6.appendChild(CRAmount1);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
			var DRAmount1 = document.createTextNode(DRAmount1);
			cell7.appendChild(DRAmount1);
			mycurrent_row.appendChild(cell7);
//alert(TypeOfTransaction1);
			var cell9 = document.createElement("TD");
			var TypeOfTransaction1 = document.createTextNode(TypeOfTransaction1);
			cell9.appendChild(TypeOfTransaction1);
			mycurrent_row.appendChild(cell9);
			
			var cel23 = document.createElement("TD");
			var FollowUpAction1 = document.createTextNode(FollowUpAction1);
			cel23.appendChild(FollowUpAction1);
			mycurrent_row.appendChild(cel23);

			var cel24 = document.createElement("TD");
			var Clearance1 = document.createTextNode(Clearance1);
			cel24.appendChild(Clearance1);
			mycurrent_row.appendChild(cel24);			

			tbody.appendChild(mycurrent_row);	
			seq1++;
		}
               document.getElementById('txtNoofRecords_NT').value=seq1;
		document.getElementById("div2").style.visibility='visible';
		document.getElementById('crAmount_bank').value=baseResponse.getElementsByTagName("crAmount_bank")[0].firstChild.nodeValue;
		document.getElementById('drAmount_bank').value=baseResponse.getElementsByTagName("drAmount_bank")[0].firstChild.nodeValue;
		seq1 = 0;
	} 
     
         document.getElementById("imgfld").style.visibility = "hidden";
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

function refresh() {
	

	  var tbody = document.getElementById("grid_body_TWAD");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	 var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }	  
	    
		  var tbody1 = document.getElementById("grid_body_NONTWAD");
		  var rowcount1=tbody1.rows.length;
		    for(var i=0;i<rowcount1;i++)
		        {
		    	 var r1=i.rowIndex;	   
		           tbody1.deleteRow(r1);
		        }
		    var today= new Date(); 
	        var day=today.getDate();
	        var month=today.getMonth();
	        month=month+1;
	        var year=today.getYear();
	        if(year < 1900) year += 1900;
	                   
	       document.frmBRSList.txtCB_Year.value=year;
	       document.frmBRSList.txtCB_Month.value=month;
	       document.getElementById("cmbBankAccNo").value="";
	       LoadAccountingUnitID('LIST_ALL_UNITS');
	       document.getElementById("crAmount").value="";
		   document.getElementById("drAmount").value="";
		   document.getElementById("div1").style.visibility='hidden';
		   document.getElementById("crAmount_bank").value="";
		   document.getElementById("drAmount_bank").value="";
		   document.getElementById("div2").style.visibility='hidden';

}
function setListType(val){
	document.getElementById('typelist').value=val;
}

function exitfun(path) {
	window.close();
}