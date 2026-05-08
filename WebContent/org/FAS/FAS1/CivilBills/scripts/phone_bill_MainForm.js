var com_id;
var seq=0;
var job_flag;
var trnmsg="";

/** Function for validate null values **/


function loadGridList(scod)
{
	//alert("load table");
	com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
    //clearall();
    var r=document.getElementById(scod);
    var rcells=r.cells;
    
    try {document.getElementById("cmbAcc_UnitCode").value=rcells.item(1).firstChild.value;}catch(e){}
    
    try {document.getElementById("cmbOffice_code").value=rcells.item(2).firstChild.value;}catch(e){}
   
    try {document.getElementById("cmb_Phone_No").value=rcells.item(3).firstChild.value;}catch(e){}
    try {document.getElementById("txtConnectionType").value=rcells.item(4).firstChild.value;}catch(e){}
    try {document.getElementById("txtPurpose").value=rcells.item(5).firstChild.value;}catch(e){}
    try {document.getElementById("txtProviderName").value=rcells.item(6).firstChild.value;}catch(e){}
    try {document.getElementById("txtProviderType").value=rcells.item(7).firstChild.value;}catch(e){}
    try {document.getElementById("txtCustodianName").value=rcells.item(8).firstChild.value;}catch(e){}
    try {document.getElementById("txtDesignation").value=rcells.item(9).firstChild.value;}catch(e){}
    try {document.getElementById("txtInvoiceNo").value=rcells.item(10).firstChild.value;}catch(e){}
    try {document.getElementById("txtDate").value=rcells.item(11).firstChild.value;}catch(e){}
    try {document.getElementById("txtBillYear").value=rcells.item(12).firstChild.value;}catch(e){}
    try {document.getElementById("txtBillMonth").value=rcells.item(13).firstChild.value;}catch(e){}
    try {document.getElementById("txtCurrentBillAmount").value=rcells.item(14).firstChild.value;}catch(e){}
    try {document.getElementById("txtDetailRemarks").value=rcells.item(15).firstChild.value;}catch(e){}
   // setTimeout("try {document.getElementById('cmb_Phone_No').value=rcells.item(3).firstChild.value; alert('hai da');}catch(e){}",900);
    //try {document.getElementById("txtDetailRemarks").value=rcells.item(15).firstChild.value;}catch(e){}
    var btndelete = document.getElementById("cmddelete");
    btndelete.disabled = false;
    
    var btnupdate = document.getElementById("cmdupdate");
    btnupdate.disabled = false;
    //alert(rcells.item(14).firstChild.name);
   
}
function setValues(rcells)
{
	
}

function checkNull(req) {

	//alert('request is : ' + req);
	if(req == "general" || req=='all')
	{
		//alert('general condition');
		if (document.getElementById("cmbAcc_UnitCode").value.length == 0) {
			alert('Accouting unit code not available');
			return false;

		}
		if (document.getElementById("cmbOffice_code").value.length == 0) {
			alert('Office code not available ');
			//document.getElementById('txtTotalBillAmount').focus();
			return false;

		}
		if (document.getElementById("txtBillDate").value.length == 0) {
			alert('Enter bill date in General Tab');
			document.getElementById('txtBillDate').focus();
			return false;

		}
		/*if (document.getElementById("txtBillNo").value.length == 0) {
			alert('Enter Bill No in General Tab');
			document.getElementById('txtBillNo').focus();
			return false;

		}*/
		if (document.getElementById("txtTotalBillAmount").value.length == 0) {
			alert('Enter Total Bill Amount in General Tab');
			document.getElementById('txtTotalBillAmount').focus();
			return false;

		}
		/*if (document.getElementById("txtGeneralRemarks").value.length == 0) {
			alert('Enter Remarks General Tab');
			document.getElementById('txtGeneralRemarks').focus();
			return false;

		}*/
		/*if (document.getElementById("cmb_Phone_No").value.length == 0) {
			alert('Select phone no in detail tab');
			//document.getElementById('txtTotalBillAmount').focus();
			return false;

		}*/
		return true;
	}
	else if(req=='detail' || req == 'all')
	{
		/*if(document.getElementById("txtDetBillNo").value != document.getElementById("txtBillNo").value)
		{
			alert('Bill No should be same \n in General and Detail Tab');
			document.getElementById('txtDetBillNo').focus();
			return false;
		}*/
		/*if (document.getElementById("txtDetBillNo").value.length == 0) {
			alert('Enter Bill No in Detail Tab');
			document.getElementById('txtDetBillNo').focus();
			return false;

		}*/
		if (document.getElementById("cmb_Phone_No").value.length == 0) {
			alert('Select phone no in detail tab');
			//document.getElementById('txtTotalBillAmount').focus();
			return false;

		}
		if (document.getElementById("txtConnectionType").value.length == 0) {
			alert('Connection type not available from parent');
			//document.getElementById('txtConnectionType').focus();
			return false;

		}
		if (document.getElementById("txtPurpose").value.length == 0) {
			alert('Connection purpose not available from parent');
			//document.getElementById('txtConnectionType').focus();
			return false;

		}
		if (document.getElementById("txtProviderName").value.length == 0) {
			alert('Connection provider name not available from parent');
			//document.getElementById('txtTotalBillAmount').focus();
			return false;

		}
		if (document.getElementById("txtProviderType").value.length == 0) {
			alert('Connection provider type not available from parent');
			//document.getElementById('txtTotalBillAmount').focus();
			return false;

		}
		if (document.getElementById("txtCustodianName").value.length == 0) {
			alert('custodian name not available from parent');
			//document.getElementById('txtTotalBillAmount').focus();
			return false;

		}
		if (document.getElementById("txtDesignation").value.length == 0) {
			alert('Designation not available from parent');
			//document.getElementById('txtTotalBillAmount').focus();
			return false;

		}
		if (document.getElementById("txtInvoiceNo").value.length == 0) {
			alert('Enter invoice No');
			document.getElementById('txtInvoiceNo').focus();
			return false;

		}
		if (document.getElementById("txtDate").value.length == 0) {
			alert('Enter invoice date in detail tab');
			document.getElementById('txtDate').disabled=false;
			document.getElementById('txtBillYear').disabled=false;
			document.getElementById('txtBillMonth').disabled=false;
			document.getElementById('txtDate').focus();
			return false;

		}
		if (document.getElementById("txtBillYear").value.length == 0) {
			alert('Enter invoice year in detail tab');
			document.getElementById('txtBillYear').focus();
			return false;

		}
		if (document.getElementById("txtBillMonth").value.length == 0) {
			alert('Enter invoice month in detail tab');
			document.getElementById('txtBillMonth').focus();
			return false;

		}
		if (document.getElementById("txtCurrentBillAmount").value.length == 0) {
			alert('Enter current bill amount in detail tab');
			document.getElementById('txtCurrentBillAmount').focus();
			return false;

		}
		/*if (document.getElementById("txtDetailRemarks").value.length == 0) {
			alert('Enter remarks in detail tab');
			document.getElementById('txtDetailRemarks').focus();
			return false;

		}*/
	}
	else
	{
		//alert('else section called');
		return false;
	}
	return true;
}
function ADD_GRID() {

	var result = checkNull('detail');
	// var result = true;
	if (result == true) {

	//var tbody = document.getElementById("grid_body");

	var t = 0;
	// var exist=document.getElementById("txtAcc_HeadCode").value;

	var items = new Array();
	items[0] = document.getElementById("cmbAcc_UnitCode").value;
	items[1] = document.getElementById("cmbOffice_code").value;
	items[2] = document.getElementById("cmb_Phone_No").value;
	items[3] = document.getElementById("txtConnectionType").value;
	items[4] = document.getElementById("txtPurpose").value;
	items[5] = document.getElementById("txtProviderName").value;
	items[6] = document.getElementById("txtProviderType").value;
	items[7] = document.getElementById("txtCustodianName").value;
	items[8] = document.getElementById("txtDesignation").value;
	items[9] = document.getElementById("txtInvoiceNo").value;
	items[10] = document.getElementById("txtDate").value;
	items[11] = document.getElementById("txtBillYear").value;
	items[12] = document.getElementById("txtBillMonth").value;
	items[13] = document.getElementById("txtCurrentBillAmount").value;
	items[14] = document.getElementById("txtDetailRemarks").value;
	//items[15] = document.getElementById("txtDetBillNo").value;

	var frmValues = 'values  : ';
	for (stval = 0; stval < items.length; stval++) {
		frmValues = frmValues + '   Item [' + stval + ']  =  ' + items[stval];
	}
	//alert(frmValues);

	tbody=document.getElementById("grid_body");
    var mycurrent_row=document.createElement("TR");
    seq=seq+1;
    mycurrent_row.id=seq;
    //alert("row ID"+mycurrent_row.id);
    var cell=document.createElement("TD");
    var anc=document.createElement("A");
    var url="javascript: loadGridList('"+mycurrent_row.id+"')";
   // alert(url);
    anc.href=url;
    
    var txtedit=document.createTextNode("EDIT");
    anc.appendChild(txtedit);
    cell.appendChild(anc);
    mycurrent_row.appendChild(cell);

	var i = 0;
	var cell2;

	cell2 = document.createElement("TD");

	var acc_UnitId = document.createElement("input");
	acc_UnitId.type = "hidden";
	acc_UnitId.name = "hdnAccUnitId";
	acc_UnitId.value = items[0];
	cell2.appendChild(acc_UnitId);
	var currentText = document.createTextNode(items[0]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var acc_OfficeId = document.createElement("input");
	acc_OfficeId.type = "hidden";
	acc_OfficeId.name = "hdnAccOfficeId";
	acc_OfficeId.value = items[1];
	cell2.appendChild(acc_OfficeId);
	var currentText = document.createTextNode(items[1]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_type = document.createElement("input");
	SL_type.type = "hidden";
	SL_type.name = "hdnPhoneNo";
	SL_type.value = items[2];
	cell2.appendChild(SL_type);
	var currentText = document.createTextNode(items[2]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnConnectionType";
	SL_code.value = items[3];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[3]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnPurpose";
	SL_code.value = items[4];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[4]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnProviderName";
	SL_code.value = items[5];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[5]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnProviderType";
	SL_code.value = items[6];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[6]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnCustodianName";
	SL_code.value = items[7];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[7]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnDesignation";
	SL_code.value = items[8];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[8]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnInvoiceNo";
	SL_code.value = items[9];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[9]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);
	
	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnBillDate";
	SL_code.value = items[10];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[10]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnBillYear";
	SL_code.value = items[11];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[11]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnBillMonth";
	SL_code.value = items[12];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[12]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnCurrentBillAmount";
	SL_code.value = items[13];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[13]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnDetailRemarks";
	SL_code.value = items[14];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[14]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);

	/*cell2 = document.createElement("TD");
	var SL_code = document.createElement("input");
	SL_code.type = "hidden";
	SL_code.name = "hdnDetBillNo";
	SL_code.value = items[15];
	cell2.appendChild(SL_code);
	var currentText = document.createTextNode(items[15]);
	cell2.appendChild(currentText);
	mycurrent_row.appendChild(cell2);*/

	tbody.appendChild(mycurrent_row);
	trnmsg = "";
	clear_detail_fields();
	

 }
}
function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
            var tbody=document.getElementById("grid_body");
            var r=document.getElementById(com_id);
            var ri=r.rowIndex;
           // alert(ri-1);
            tbody.deleteRow(ri-1);
            clear_detail_fields();
            var btndelete = document.getElementById("cmddelete");
            btndelete.disabled = true;
            var btnupdate = document.getElementById("cmdupdate");
            btnupdate.disabled = true;
           // clearall();
        }
}
function clearGridList()
{
	
		tbody=document.getElementById("grid_body");
		tbody.innerHTML="";
		clear_detail_fields();
	
}

function clear_detail_fields()
{
	document.getElementById("cmb_Phone_No").value = "";
	document.getElementById("txtConnectionType").value = "";
	document.getElementById("txtPurpose").value = "";
	document.getElementById("txtProviderName").value = "";
	document.getElementById("txtProviderType").value = "";
	document.getElementById("txtCustodianName").value = "";
	document.getElementById("txtDesignation").value = "";
	document.getElementById("txtInvoiceNo").value = "";
	document.getElementById("txtDate").value = "";
	document.getElementById("txtBillYear").value = "";
	document.getElementById("txtBillMonth").value = "";
	document.getElementById("txtCurrentBillAmount").value = "";
	document.getElementById("txtDetailRemarks").value= "";
	
}
function clear_all_fields()
{
	// Detail Section Fields
	document.getElementById("cmb_Phone_No").value = "";
	document.getElementById("txtConnectionType").value = "";
	document.getElementById("txtPurpose").value = "";
	document.getElementById("txtProviderName").value = "";
	document.getElementById("txtProviderType").value = "";
	document.getElementById("txtCustodianName").value = "";
	document.getElementById("txtDesignation").value = "";
	document.getElementById("txtInvoiceNo").value = "";
	document.getElementById("txtDate").value = "";
	document.getElementById("txtBillYear").value = "";
	document.getElementById("txtBillMonth").value = "";
	document.getElementById("txtCurrentBillAmount").value = "";
	document.getElementById("txtDetailRemarks").value= "";
	
	// General Section Fields
	
	document.getElementById("txtTotalBillAmount").value = "";
	document.getElementById("txtGeneralRemarks").value = "";
	document.getElementById("txtBillDate").value = "";
	
	//Function for clear grid

	var tbody=document.getElementById("grid_body");
	   var t=0;
	   for(t=tbody.rows.length-1;t>=0;t--)
	   {
	    	  tbody.deleteRow(0);
	   }
	
}
		
function set_Message()
{
	var flag1 = checkNull("general"); 
				//alert(flag1);
	if(flag1==true)
	{
		var rowCount=document.getElementById("grid_body").getElementsByTagName("TR").length;
		var totBillAmount = parseFloat(document.getElementById("txtTotalBillAmount").value);
		if(rowCount==0)
		{
			/*if(totBillAmount == 0)
			{
			document.getElementById("hdnMessage").value = "AddMst";
			}
			else
			{
				alert('The Total Bill Amount Shold be Rs: 0.00');
				document.getElementById("txtTotalBillAmount").focus();
				return false;
			}*/
			alert('Add Transaction Details.....');
			return false;
		}
		else if(rowCount > 0)
		{
			//alert('Row Count : ' + rowCount);
			document.getElementById("hdnMessage").value = "AddTrn";
			
			
			//alert(" Message : " + document.getElementById("hdnMessage").value);
			
			 //var r=document.getElementById(scod);
			 try
			 {
			 var arCurBillAmount=document.getElementsByName("hdnCurrentBillAmount");
			 var totalAmt = 0;
			 for(var i=0;i<arCurBillAmount.length;i++)
			 {
				 var curAmt=parseFloat(arCurBillAmount[i].value);
				 totalAmt = totalAmt + curAmt;
				// try {alert(curAmt);}catch(e){alert(e)}
			 }
			 if(totBillAmount != totalAmt)
			 {
				 alert('Transction total not equal to the total bill amt');
				 return false;
			 }
			 
			 }
			 catch(ex)
			 {
				 alert(ex);
				 return false;
			 }
			 
			
		}
		else
		{
			
		}
		
	}
	else
	{
		return false;
	}
	return true;
}




function getTransport()
{
	//alert('fuction getTransport');
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}


function chk_TrnRecordExist()
{
	var result = checkNull('detail');
	if(result==true)
	{
	var accountingUnitId = document.frmPhoneBillMainForm.cmbAcc_UnitCode.value;
	var accountingOfficeId = document.frmPhoneBillMainForm.cmbOffice_code.value;
	var bookDate =  document.frmPhoneBillMainForm.txtBillDate.value;
	//var genbillNo = document.frmPhoneBillMainForm.txtDetBillNo.value;
	
	var bookMonthYear = bookDate.split("/");
	var bookYear = bookMonthYear[2];
	var bookMonth = bookMonthYear[1];
	
	/** tab - Details values */

	var phoneNo = document.frmPhoneBillMainForm.cmb_Phone_No.value;
	var invoiceNo = document.frmPhoneBillMainForm.txtInvoiceNo.value;
	
	
	
	//alert(trnmsg);
	if(trnmsg=="")
	{
		//alert('ajax called');
/*	var url = '../../../../../phone_bill_servlet?' + 'command=CHKTRN'
			+ '&cmbAcc_UnitCode=' + accountingUnitId + '&cmbOffice_code='
			+ accountingOfficeId + '&bookYear='+bookYear + '&bookMonth='+bookMonth 
			+'&cmb_Phone_No=' + phoneNo + '&txtInvoiceNo='+ invoiceNo+'&genBillNo='+genbillNo; */

	var url = '../../../../../phone_bill_servlet?' + 'command=CHKTRN'
	+ '&cmbAcc_UnitCode=' + accountingUnitId + '&cmbOffice_code='
	+ accountingOfficeId + '&bookYear='+bookYear + '&bookMonth='+bookMonth 
	+'&cmb_Phone_No=' + phoneNo + '&txtInvoiceNo='+ invoiceNo;

	//alert(url);

	var req = getTransport();
	req.open("GET", url, true);
	req.onreadystatechange = function() {
		chk_TrnRecordExistRes(req);
	}
	req.send(null);
	}
	else
	{
		//alert('ajax not called');
		
	}
	}
}

function chk_TrnRecordExistRes(req)
{
	var msg="";
	 if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	             var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            // alert("command : "+ tagcommand);
	             var Command=tagcommand.firstChild.nodeValue;
	           
	            if(Command=="CHKTRN")
	            {
	            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	 
	            //	 alert('flag value :' + flag);
	            	 if(flag=="exist")
	            	    {
	            	          
	            	           alert('record already exist on this invoice No...!');
	            	           //clearValues();
	            	    } 
	            	 else if(flag=="notexist")
	            	 {
	            		 trnmsg="notexist";
	            		 //alert('record not exist');
	            		
	            	 }
	            	 else if(flag=="mstnotexist")
	            	 {
	            		 alert('First Create Maseter For This No');
	            	 }
	            	           /** Get Phone No */
	            	       
	              }
	              else
	              {
	            	    
	              }
	        
	        }
	        if(trnmsg=="notexist")
	        {
	        	 ADD_GRID();
	        }
	    }
	
		
	
}
	

// --- Code for update grid --- //

function update_GRID()
{
	var result = checkNull('detail');
	if(result == true)
	{
		//var exist = document.getElementById("txtAcc_HeadCode").value;

		var items = new Array();
		items[0] = document.getElementById("cmbAcc_UnitCode").value;
		items[1] = document.getElementById("cmbOffice_code").value;
		items[2] = document.getElementById("cmb_Phone_No").value;
		items[3] = document.getElementById("txtConnectionType").value;
		items[4] = document.getElementById("txtPurpose").value;
		items[5] = document.getElementById("txtProviderName").value;
		items[6] = document.getElementById("txtProviderType").value;
		items[7] = document.getElementById("txtCustodianName").value;
		items[8] = document.getElementById("txtDesignation").value;
		items[9] = document.getElementById("txtInvoiceNo").value;
		items[10] = document.getElementById("txtDate").value;
		items[11] = document.getElementById("txtBillYear").value;
		items[12] = document.getElementById("txtBillMonth").value;
		items[13] = document.getElementById("txtCurrentBillAmount").value;
		items[14] = document.getElementById("txtDetailRemarks").value;
		//items[15] = document.getElementById("txtDetBillNo").value;
		
		var r = document.getElementById(com_id);
		var rcells = r.cells;

		rcells.item(1).firstChild.value = items[0];
		rcells.item(1).lastChild.nodeValue = items[0];
		
		rcells.item(2).firstChild.value = items[1];
		rcells.item(2).lastChild.nodeValue = items[1];
		
		rcells.item(3).firstChild.value = items[2];
		rcells.item(3).lastChild.nodeValue = items[2];
		
		rcells.item(4).firstChild.value = items[3];
		rcells.item(4).lastChild.nodeValue = items[3];
		
		rcells.item(5).firstChild.value = items[4];
		rcells.item(5).lastChild.nodeValue = items[4];
		
		rcells.item(6).firstChild.value = items[5];
		rcells.item(6).lastChild.nodeValue = items[5];
		
		rcells.item(7).firstChild.value = items[6];
		rcells.item(7).lastChild.nodeValue = items[6];
		
		rcells.item(8).firstChild.value = items[7];
		rcells.item(8).lastChild.nodeValue = items[7];
		
		rcells.item(9).firstChild.value = items[8];
		rcells.item(9).lastChild.nodeValue = items[8];
		
		rcells.item(10).firstChild.value = items[9];
		rcells.item(10).lastChild.nodeValue = items[9];
		
		rcells.item(11).firstChild.value = items[10];
		rcells.item(11).lastChild.nodeValue = items[10];
		
		rcells.item(12).firstChild.value = items[11];
		rcells.item(12).lastChild.nodeValue = items[11];
		
		rcells.item(13).firstChild.value = items[12];
		rcells.item(13).lastChild.nodeValue = items[12];
		
		rcells.item(14).firstChild.value = items[13];
		rcells.item(14).lastChild.nodeValue = items[13];

		rcells.item(15).firstChild.value = items[14];
		rcells.item(15).lastChild.nodeValue = items[14];

		//rcells.item(16).firstChild.value = items[15];
		//rcells.item(16).lastChild.nodeValue = items[15];
		
		alert("Record Updated");
		clear_detail_fields();
		//clearall();
		}
	
}

function btncancel()
{

 self.close();
}

function clrForm()
{
	   if(window.confirm("Do you want to clear ALL fields ?"))
	   {
		   clear_all_fields();
	   }
}

/*
 * chid hide start
 * if(document.getElementById("txtAcc_HeadCode").value.length==0) { alert("Enter
 * A/c Head Code"); document.getElementById("txtAcc_HeadCode").focus(); return
 * false; }
 * 
 * 
 * var acc=document.getElementById("txtAcc_HeadCode").value; var
 * kk=acc.charAt(0)+acc.charAt(1);
 * 
 * if(kk=="82") { if(acc !="820102" && acc !="820103") { // alert("You are not
 * allow to use this Account Head Code "+acc); alert("This A/C code can not be
 * used here "); document.getElementById("txtAcc_HeadCode").value="";
 * document.getElementById("txtAcc_HeadDesc").value=""; return false; } } chid
 * hide end
 */        

