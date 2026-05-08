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

function report_Units() {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	alert(cmbAcc_UnitCode);
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Year_from=document.getElementById("txtCB_Year_from").value;
	var txtCB_Month_from=document.getElementById("txtCB_Month_from").value;
	var txtCB_Year_to=document.getElementById("txtCB_Year_to").value;
	var txtCB_Month_to=document.getElementById("txtCB_Month_to").value;
	/*if(txtCB_Month=""){ if(txtCB_Year="" )
	{
		txtCB_Month=0;
		txtCB_Year=0;
	}}
	if(txtCB_Year_from="")
	{
	 if(txtCB_Month_from="")
		{
	 if(txtCB_Year_to="")
		{
	if(txtCB_Month_to=" "){
		txtCB_Year_from=0;
		 txtCB_Month_from=0;
		 txtCB_Year_to=0;
		 txtCB_Month_to=0;
		}

		}
	}
	}	*/	 
	
	var url="";
		url = "../../../../../../UnitsJournal?command=journal_report&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+"&txtCB_Month="+txtCB_Month+"&txtCB_Year="+txtCB_Year
				+"&txtCB_Year_from="+txtCB_Year_from+"&txtCB_Month_from="+txtCB_Month_from
				+"&txtCB_Year_to="+txtCB_Year_to+"&txtCB_Month_to="+txtCB_Month_to;
		alert(url);
		document.journal_report.action = url;
		document.journal_report.submit();
}
