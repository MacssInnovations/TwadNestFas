

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

function Report1()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtFrom_date = document.getElementById("txtFrom_date").value;
	var txtTo_date=document.getElementById("txtTo_date").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;


	var url="";
		url = "../../../../../../FundReceiptPayment?command=Report_month&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date
				+"&txtCB_Year_to="+txtCB_Year+"&txtCB_Month_to="+txtCB_Month;
		//alert(url);
		document.frmfundRecReport1.action = url;
		document.frmfundRecReport1.submit();	
}

function Report_date() {
	//alert('test');
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtFrom_date = document.getElementById("txtFrom_date").value;
	var txtTo_date=document.getElementById("txtTo_date").value;
	/*var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;*/
	var txtCB_Year=0;
	var txtCB_Month=0;


	var url="";
		url = "../../../../../../FundReceiptPayment?command=Report_date&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+"&txtFrom_date="+txtFrom_date+"&txtTo_date="+txtTo_date
				+"&txtCB_Year_to="+txtCB_Year+"&txtCB_Month_to="+txtCB_Month;
		//alert(url);
		document.frmfundRecReport1.action = url;
		document.frmfundRecReport1.submit();

	
}