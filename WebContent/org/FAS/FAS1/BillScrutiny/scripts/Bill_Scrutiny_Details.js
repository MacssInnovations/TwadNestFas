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
var check_memo_list;
function viewDetails(path)
{
	var unitcode = document.getElementById("cmbAcc_UnitCode").value;
    var offid = document.getElementById("cmbOffice_code").value;
    var yr = document.getElementById("cboCashBook_Year").value;
    var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
    var cboBillNo = document.getElementById("cboBillNo").value;
	check_memo_list= window.open("../../../../../org/FAS/FAS1/BillScrutiny/jsps/Bill_Scrutiny_Details_list.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&cashbook_yr="+yr+"&cboCashBook_Month="+cboCashBook_Month+"&cboBillNo="+cboBillNo.split("/")[0],"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
 	     
	check_memo_list.moveTo(250,250);  
	check_memo_list.focus();
}

function checksan()
{
	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtScrutinyDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	 
	 if(biisp_grid[2]>passsplit[2])
	 {
		 alert("BillScrutiny Date should be Greater than BillDate");
		 document.getElementById("txtScrutinyDate").value="";
		 return false;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
   	 if(biisp_grid[1]>passsplit[1])
   	 {
   		 alert("BillScrutiny Date should be Greater than BillDate");
		 document.getElementById("txtScrutinyDate").value="";
		 return false;
   	 }
   	 else if(biisp_grid[1]==passsplit[1])
   	 {
   		 var billspl;
   		// alert(biisp_grid[0].length);
   		 if(biisp_grid[0].length==2)
   		 {
   			billspl=biisp_grid[0];
   		 }
   		 else
   		 {
   			billspl="0"+biisp_grid[0];
   		 }
   		 if(billspl>passsplit[0])
       	 {
   			 alert("BillScrutiny Date should be Greater");
   			 document.getElementById("txtScrutinyDate").value="";
   			 return false;
       	 } 
   	 }
	 }
	
}

function initialLoads(path) {
	
	clearBillNo();
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
    var cboOffice_code = document.getElementById("cmbOffice_code").value;
    var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
    var cboCashBook_Month = document.getElementById("cboCashBook_Month").value; 
	var url = path + "/Bill_Scrutiny_Details.bs?command=gett&cmbAcc_UnitCode="
				+ cboAcc_UnitCode + "&cmbOffice_code=" + cboOffice_code
				+ "&cboCashBook_Year=" + cboCashBook_Year
				+ "&cboCashBook_Month=" + cboCashBook_Month	;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);

}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			//alert("manipulate-command:--->>>"+command);

			if (command == "gett") {
				//alert("manipulate");
				firstLoad(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "getDetails") {
				getDetails1(baseResponse);
			}else if (command == "loadDetails") {
				loadDetails1(baseResponse);
			}
			
		}
	}
}

function firstLoad(baseResponse) {
	//alert("firstLoad");
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month >= 10) {
		var date = (day + "/" + month + "/" + year);
	} else {
		var date = (day + "/0" + month + "/" + year);
	}
	document.frm_Bill_Scrutiny_Details.txtScrutinyDate.value = date;
	//alert("check");	
	var flag1 = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.getElementById("cboBillNo").value="s";
	var options = [];
	if(flag1 == "success")
	{
		
		var memo = baseResponse.getElementsByTagName("memo")[0].firstChild.nodeValue;
		if(memo=="yes")
		{
			
			var with_without = baseResponse.getElementsByTagName("with_without")[0].firstChild.nodeValue;
			document.getElementById("withorwithout").value=with_without;
			var len1 = baseResponse.getElementsByTagName("billNo").length;
			//alert(len1);
			for ( var i = 0; i < len1; i++) {
				var with_without1 = baseResponse.getElementsByTagName("with_without")[i].firstChild.nodeValue;
				
				var billNo = baseResponse.getElementsByTagName("billNo")[i].firstChild.nodeValue;
				var se = document.getElementById("cboBillNo");
				var op = document.createElement("OPTION");
				document.getElementById("cboBillNo").options.add(op);
				op.value = billNo;
				//+"/"+with_without1;
				op.text = billNo;
				op.id='cbillNo'+i;
				//var txt = document.createTextNode(billNo);
				//op.appendChild(txt);
				//se.appendChild(op);
				//alert(op.value);
				 options.push(op.outerHTML);
		
			}
		}
		else
		{
			alert("Memo Of Payment Entry is Not Done");
			return false;
		}
	}
	else if(flag1 == "failure")
	{
		var b_value="s";
		alert("NO record found for Bill NO");
		document.getElementById("cboBillNo").length=0;
		var se = document.getElementById("cboBillNo");
		var op = document.createElement("OPTION");
		op.value =b_value;
		var txt = document.createTextNode("Select");
		op.appendChild(txt);
		se.appendChild(op);

		//document.getElementById("cboBillNo").options[document.getElementById("cboBillNo").selectedIndex].value=s;
		//document.getElementById("cboBillNo").options[document.getElementById("cboBillNo").selectedIndex].text="Select";
		
	}
}
function loadDetails(path) {
	
//	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
	var url = path
			+ "/Bill_Scrutiny_Details.bs?command=loadDetails";
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
function getDetails(path) {
	
	var tbody=document.getElementById("grid_body_new");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
	
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtSanctionProceedingNo").value="";
	document.getElementById("txtSanctionProceedingDate").value="";
	//document.getElementById("txtAccountHeadCode").value="";
	//document.getElementById("txtAccountHeadCodeDesc").value="";
//	document.getElementById("txtSubLedgerType").value="";
//	document.getElementById("txtSubLedgerCode").value="";
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	//document.getElementById("txtScrutinyDate").value="";
	document.getElementById("mtxtRemarks").value="";
	
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboBillNo = document.getElementById("cboBillNo").value;
	//var withorwithout=document.getElementById("withorwithout").value;
	
	document.getElementById("cboBillNo").value=cboBillNo.split("/")[0];
	document.getElementById("withorwithout").value=cboBillNo.split("/")[1];
	if (cboBillNo == "" || cboBillNo=="s") {
		alert("Select Bill No in the Field");
		document.frm_Bill_Scrutiny_Details.cboBillNo.focus();
	} else
	{
		
	var url = path
			+ "/Bill_Scrutiny_Details.bs?command=getDetails&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code + "&cboCashBook_Year="
			+ cboCashBook_Year + "&cboCashBook_Month=" + cboCashBook_Month + "&cboBillNo="
			+ cboBillNo.split("/")[0]+"";
	
	}
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);
}
function loadDetails1(baseResponse) {
	var seq =0;
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var count=baseResponse.getElementsByTagName("slno")[0].firstChild.nodeValue;
	
         var tbody=document.getElementById("grid_body");
         var t=0;
         for(t=tbody.rows.length-1;t>=0;t--)
             {
                tbody.deleteRow(0);
             } 
                                           
         for(var i=0;i< count;i++)
         {          
                
                 var slno1=baseResponse.getElementsByTagName("slno1")[i].firstChild.nodeValue;
                 var manda=baseResponse.getElementsByTagName("mandate")[i].firstChild.nodeValue;
                 var noappp=baseResponse.getElementsByTagName("noapp")[i].firstChild.nodeValue;
                 var checkdes=baseResponse.getElementsByTagName("checkdesc")[i].firstChild.nodeValue;
                 var checkcodee=baseResponse.getElementsByTagName("checkcode")[i].firstChild.nodeValue;
               
                 
                 var mycurrent_row=document.createElement("TR");
                 mycurrent_row.id=seq;

                 var cell2=document.createElement("TD");
                 var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="checkdes1";
	     			sno1.id="checkdes1";
	     			sno1.value=checkdes; 
                 var currentText1=document.createTextNode(checkdes);
                 cell2.appendChild(currentText1);
                 cell2.appendChild(sno1);
                 cell2.style.textAlign="left";
                 mycurrent_row.appendChild(cell2); 
            
                 var cell2=document.createElement("TD"); 
                 var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="manda1";
	     			sno1.id="manda1";
	     			sno1.value=manda; 
                 var currentText1=document.createTextNode(manda);
                 cell2.appendChild(currentText1);
                 cell2.appendChild(sno1);
                 cell2.style.textAlign="center";
                 mycurrent_row.appendChild(cell2); 
     
                 var cell2=document.createElement("TD");   
                 var sno1=document.createElement("input");
	     			sno1.type="hidden";
	     			sno1.name="noappp1";
	     			sno1.id="noappp1";
	     			sno1.value=noappp; 
                 var currentText1=document.createTextNode(noappp);
                 cell2.appendChild(currentText1);
                 cell2.appendChild(sno1);
                 cell2.style.textAlign="center";
                 mycurrent_row.appendChild(cell2);
                 
                 
                 var cell=document.createElement("TD");
                 cell.align='CENTER';
                 var anc=document.createElement("input");
                 anc.type="checkbox";
               /*  if(manda=='Y')
                 {
                	 anc.setAttribute('checked','checked');
                	 anc.setAttribute('disabled','disabled');
                 }
                 anc.value="CHECKED"; */
                 anc.id="verify_select";
                 anc.name="verify_select"; 
                 cell.appendChild(anc);
                 
                 var anc1=document.createElement("input");
                 anc1.type="hidden";
                 anc1.id="verify_select_status";
                 anc1.name="verify_select_status"; 
                 cell.appendChild(anc1);
                 mycurrent_row.appendChild(cell);
                 
                    var cell2=document.createElement("TD"); 
                    cell2.style.display="none";
                    var checkcodee1=document.createElement("input");
                    checkcodee1.type="hidden";
                    checkcodee1.name="checkcodee";
                    checkcodee1.id="checkcodee";
                    checkcodee1.value=checkcodee;
                    var currentText1=document.createTextNode(checkcodee);
                    cell2.appendChild(currentText1);
                    cell2.appendChild(checkcodee1);
                    mycurrent_row.appendChild(cell2);
       
                  
                
                 tbody.appendChild(mycurrent_row);
                 seq++;	
         		}
         
	}
	else if(flag == "NoData") {
		var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	       tbody.deleteRow(0);
	    }
		alert("Record Does Not Exists");
	}
	else
	{
		var tbody=document.getElementById("grid_body");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	       tbody.deleteRow(0);
	    } 
	alert("Failed to Load");
}
	
}

/*
function getbilldet(s)
{
	alert(s);
	alert(s.selectedIndex);
	alert(s.selectedIndex.text);
	//alert(s[s.selectedIndex]);
	var str_bl_no = s[s.selectedIndex].id;
	str_bl_no=1;
	
	document.getElementById(str_bl_no).setAttribute('selected','selected');
}*/
function getDetails1(baseResponse) {
	//alert("getDetails1(baseResponse)");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {				
	var billDate = baseResponse.getElementsByTagName("billDate")[0].firstChild.nodeValue;	
	var billAmount = baseResponse.getElementsByTagName("billAmount")[0].firstChild.nodeValue;
	var totalsancamt = baseResponse.getElementsByTagName("totalsancamt")[0].firstChild.nodeValue;
	
	var proceedingNo = baseResponse.getElementsByTagName("proceedingNo")[0].firstChild.nodeValue;
	var proceedingDate = baseResponse.getElementsByTagName("proceedingDate")[0].firstChild.nodeValue;
	var AccHeadCode = baseResponse.getElementsByTagName("AccHeadCode")[0].firstChild.nodeValue;
	var AccHeadCodeDesc = baseResponse.getElementsByTagName("AccHeadCodeDesc")[0].firstChild.nodeValue;
	var bill_type = baseResponse.getElementsByTagName("bill_type")[0].firstChild.nodeValue;
//	var remarks = baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
	if(baseResponse.getElementsByTagName("remarks")[0].firstChild == null){
		var remarks = "";
	}else {
		var remarks = baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
	}
	
	//var subLedgerCodeDesc1 = baseResponse.getElementsByTagName("subLedgerCodeDesc1")[0].firstChild.nodeValue;
var AMOUNT_cp=baseResponse.getElementsByTagName("AMOUNT_cp")[0].firstChild.nodeValue;
	document.frm_Bill_Scrutiny_Details.txtBillDate.value = billDate;
	document.frm_Bill_Scrutiny_Details.txtBillAmount.value = totalsancamt;
	document.frm_Bill_Scrutiny_Details.txtDeductedAmount.value = parseInt(totalsancamt)-parseInt(AMOUNT_cp);
	document.frm_Bill_Scrutiny_Details.txtSanctionProceedingNo.value = proceedingNo;
	document.frm_Bill_Scrutiny_Details.txtSanctionProceedingDate.value = proceedingDate;
	//alert(bill_type);
	document.frm_Bill_Scrutiny_Details.withorwithout.value = bill_type;
	document.frm_Bill_Scrutiny_Details.mtxtRemarks.value = remarks;
//	document.frm_Bill_Scrutiny_Details.txtSubLedgerCode.value = subLedgerCodeDesc1;
	document.frm_Bill_Scrutiny_Details.txtNetAmount.value =totalsancamt;
	document.frm_Bill_Scrutiny_Details.txtScrutinyDate.value =billDate;
//	document.getElementById("cboBillNo").setAttribute('selected','selected');
	
	/*var chk=document.getElementById("cboBillNo").value;
	var cel=document.getElementById("cboBillNo");
	
	
	
	if (chk)
		{
		cel.text=chk;
		}*/
	var service=baseResponse.getElementsByTagName("proceedingNo");
		//alert(service.length);
	 for(i=0;i<service.length;i++)
     {
		
   // c++;
     var items=new Array();
   
   //  alert(document.getElementById("cboBillNo").value);
     var bil_no=document.getElementById("cboBillNo").value;
     items[0]=bil_no.split("/")[0];
     items[0].selected=true;
    //alert(items[0]);
    cel.selectedIndex.text=items[0];
    items[1]=baseResponse.getElementsByTagName("billDate")[i].firstChild.nodeValue;
   // alert(items[1]);
    items[2]= baseResponse.getElementsByTagName("billAmount")[i].firstChild.nodeValue;
    
    
    items[3]= baseResponse.getElementsByTagName("subLedgerTypeCode")[i].firstChild.nodeValue;
    items[4]= baseResponse.getElementsByTagName("subLedgerTypeCodeDesc")[i].firstChild.nodeValue;
    items[5]= baseResponse.getElementsByTagName("totalsancamt")[i].firstChild.nodeValue;
    
    items[6]= baseResponse.getElementsByTagName("major_desc")[i].firstChild.nodeValue;
    items[7]= baseResponse.getElementsByTagName("proceedingNo")[i].firstChild.nodeValue;
    items[8]= baseResponse.getElementsByTagName("proceedingDate")[i].firstChild.nodeValue;
    
    items[9]= baseResponse.getElementsByTagName("AccHeadCode")[i].firstChild.nodeValue;
    items[10]= baseResponse.getElementsByTagName("AccHeadCodeDesc")[i].firstChild.nodeValue;
    items[11]= baseResponse.getElementsByTagName("indicator")[i].firstChild.nodeValue;
    
    items[12]= baseResponse.getElementsByTagName("subLedgerCode")[i].firstChild.nodeValue;
    items[13]= baseResponse.getElementsByTagName("code_desc")[i].firstChild.nodeValue;
    
    var tbody=document.getElementById("grid_body_new");
    var mycurrent_row=document.createElement("TR");


   // alert("tttt");
   
   cell2=document.createElement("TD");
   cell2.setAttribute('align','left');
   var v_id=document.createElement("input");
   v_id.type="hidden";
   v_id.name="billdate";
   v_id.value=items[1];
   cell2.appendChild(v_id);
   var currentText=document.createTextNode(items[1]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
   cell2=document.createElement("TD");
   cell2.setAttribute('align','left');
   var v_date=document.createElement("input");
   v_date.type="hidden";
   v_date.name="billamt";
   v_date.value=items[11];
   cell2.appendChild(v_date);
   var currentText=document.createTextNode(items[11]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
   cell2=document.createElement("TD");
   cell2.setAttribute('align','right');
   var v_date=document.createElement("input");
   v_date.type="hidden";
   v_date.name="billamt";
   v_date.value=items[2];
   cell2.appendChild(v_date);
   var currentText=document.createTextNode(items[2]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
   
   cell2=document.createElement("TD");
   cell2.setAttribute('align','right');
   var tot_amt=document.createElement("input");
   tot_amt.type="hidden";
   tot_amt.name="totalsancAmt";
   tot_amt.value=items[5];
   cell2.appendChild(tot_amt);
   var currentText=document.createTextNode(items[5]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
  var cell2=document.createElement("TD");
   cell2.setAttribute('align','left');
   var tot_amt=document.createElement("input");
   tot_amt.type="hidden";
   tot_amt.name="totalAmt";
   tot_amt.value=items[7];
   cell2.appendChild(tot_amt);
   var currentText=document.createTextNode(items[7]+"-"+items[8]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
   var cell2=document.createElement("TD");
   cell2.setAttribute('align','left');
   var tot_amt=document.createElement("input");
   tot_amt.type="hidden";
   tot_amt.name="headcode";
   tot_amt.value=items[9];
   cell2.appendChild(tot_amt);
   var currentText=document.createTextNode(items[9]+" / "+items[10]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
   var cell2=document.createElement("TD");
   cell2.setAttribute('align','left');
   var type_code=document.createElement("input");
   type_code.type="hidden";
   type_code.name="typecode";
   type_code.value=items[3];
   cell2.appendChild(type_code);
   var currentText=document.createTextNode(items[4]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
   
   var cell2=document.createElement("TD");
   cell2.setAttribute('align','left');
   var code_one=document.createElement("input");
   code_one.type="hidden";
   code_one.name="codedesc";
   code_one.value=items[12];
   cell2.appendChild(code_one);
   var currentText=document.createTextNode(items[13]);
   cell2.appendChild(currentText);
   mycurrent_row.appendChild(cell2);
   
    tbody.appendChild(mycurrent_row);
   // seq++;
	}
	
	}
	else if(flag == "NoData") {
		var tbody=document.getElementById("grid_body_new");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	       tbody.deleteRow(0);
	    }
		alert("Record Does Not Exist in Table");
	}
	else
	{
		var tbody=document.getElementById("grid_body_new");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	       tbody.deleteRow(0);
	    }
	alert("Failed to Load");
	}
	
}
function calculateNetAmount()
{
	var txtBillAmount = document.getElementById("txtBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	
	var BillAmount1 = parseInt(txtBillAmount);
	var DeductedAmount1 = parseInt(txtDeductedAmount);
	if(DeductedAmount1 > BillAmount1)
	{
		alert("Deducted Amount Should be Less than Bill Amount");
	}
	var BillAmount = parseInt(txtBillAmount);
	var DeductedAmount = parseInt(txtDeductedAmount);
	var NetAmount = BillAmount - DeductedAmount;	
	document.frm_Bill_Scrutiny_Details.txtNetAmount.value = NetAmount;
	
}

function checkNull_verify()
{
	//alert("checkNull_verify");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboBillNo = document.getElementById("cboBillNo").value;
	var BillScrunityDate = document.getElementById("txtScrutinyDate").value;
	var txtBillAmount = document.getElementById("txtBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	var DeductedAmount = document.getElementById("txtDeductedAmount").value;
	var NetAmount = document.getElementById("txtNetAmount").value;
	

	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtScrutinyDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	 
	 if(biisp_grid[2]>passsplit[2])
	 {
		 alert("BillScrutiny Date should be Greater than BillDate");
		 document.getElementById("txtScrutinyDate").value="";
		 return false;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
   	 if(biisp_grid[1]>passsplit[1])
   	 {
   		 alert("BillScrutiny Date should be Greater than BillDate");
		 document.getElementById("txtScrutinyDate").value="";
		 return false;
   	 }
   	 else if(biisp_grid[1]==passsplit[1])
   	 {
   		 var billspl;
   		// alert(biisp_grid[0].length);
   		 if(biisp_grid[0].length==2)
   		 {
   			billspl=biisp_grid[0];
   		 }
   		 else
   		 {
   			billspl="0"+biisp_grid[0];
   		 }
   		 if(billspl>passsplit[0])
       	 {
   			 alert("BillScrutiny Date should be Greater");
   			 document.getElementById("txtScrutinyDate").value="";
   			 return false;
       	 } 
   	 }
	 }
	
	  var tbody=document.getElementById("grid_body");

	  var checkbox = document.getElementsByName('verify_select');
	
	    var chvalue=document.getElementsByName("verify_select_status");
	  
	    var ln = checkbox.length;
	var count_nam=0;
	var count_chk=0;
		for(var i=0;i<ln;i++)
		{
			
			//alert("pro:::"+document.frm_Bill_Scrutiny_Details.manda1[i].value);
			 if(document.frm_Bill_Scrutiny_Details.manda1[i].value=="Y")
			 {
				 count_nam++;
				// alert("22:"+mand);
			 }
			
			try{
			if(checkbox[i].checked==true){
			
				count_chk++;
				checkbox[i].value="CHECKED";
				chvalue[i].value="CHECKED";
			}else{
				//alert("unchecked");
				checkbox[i].value="UNCHECKED";
				chvalue[i].value="UNCHECKED";
			}
			}catch(e){
				alert(e);
			}		
		}
		
	if(tbody.rows.length==0){
	alert("No values Found");
	return false;
	}
	
	else if (BillScrunityDate=="") {
		alert("Enter Bill Scrunity Date in the Field");
		document.frm_Bill_Scrutiny_Details.txtScrutinyDate.focus();
		return false;
	}
	else if(count_chk!=count_nam)
	{
		alert("Please Select the checkBox for Mandatory Field Y");
		return false;
	}
	else if (cboBillNo == "" || cboBillNo=="s") {
	alert("Select Bill No in the Field");
	document.frm_Bill_Scrutiny_Details.cboBillNo.focus();
	return false;
	}else if (DeductedAmount == "") {
		alert("Enter Deducted Amount in the Field");
		document.frm_Bill_Scrutiny_Details.txtDeductedAmount.focus();
		return false;
	}else if(DeductedAmount1 > BillAmount1)
	{
		alert("Deducted Amount Should be Less than Bill Amount");
		return false;
	}else if (NetAmount == "") {
		alert("Enter Net Amount in the Field");
		document.frm_Bill_Scrutiny_Details.txtNetAmount.focus();
		return false;
	}
	
	else {
		
		return true;
	}
	
	
}

function saveFunc(path) {
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month = document.getElementById("cboCashBook_Month").value;
	var cboBillNo = document.getElementById("cboBillNo").value;
	var BillScrunityDate = document.getElementById("txtScrutinyDate").value;
	var txtBillAmount = document.getElementById("txtBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	var DeductedAmount = document.getElementById("txtDeductedAmount").value;
	var NetAmount = document.getElementById("txtNetAmount").value;
	
	if(document.frm_Bill_Scrutiny_Details.rdoScrutinyDone[0].checked==true)
	{
		BillScrunityDone=document.frm_Bill_Scrutiny_Details.rdoScrutinyDone[0].value;
	}
   else
	{
	   BillScrunityDone=document.frm_Bill_Scrutiny_Details.rdoScrutinyDone[1].value;
	}

	var BillAmount1 = parseInt(txtBillAmount);
	var DeductedAmount1 = parseInt(txtDeductedAmount);		
	
	if (cboBillNo == "" || cboBillNo=="s") {
		alert("Select Bill No in the Field");
		document.frm_Bill_Scrutiny_Details.cboBillNo.focus();
	}else if (DeductedAmount == "") {
		alert("Enter Deducted Amount in the Field");
		document.frm_Bill_Scrutiny_Details.txtDeductedAmount.focus();		
	}else if(DeductedAmount1 > BillAmount1)
	{
		alert("Deducted Amount Should be Less than Bill Amount");
	}else if (NetAmount == "") {
		alert("Enter Net Amount in the Field");
		document.frm_Bill_Scrutiny_Details.txtNetAmount.focus();
	}else if (BillScrunityDate == "") {
		alert("Enter Bill Scrunity Date in the Field");
		document.frm_Bill_Scrutiny_Details.txtScrutinyDate.focus();
	}

	else {
	var url = path
	+ "/Bill_Scrutiny_Details.bs?command=saveFunc&cmbAcc_UnitCode="
	+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code + "&cboCashBook_Year="
	+ cboCashBook_Year + "&cboCashBook_Month=" + cboCashBook_Month + "&cboBillNo="
	+ cboBillNo.split("/")[0] + "&BillScrunityDate=" + BillScrunityDate + "&BillScrunityDone="
	+ BillScrunityDone+ "&DeductedAmount=" + DeductedAmount + "&NetAmount="
	+ NetAmount;



	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
}
function saveFunc1(baseResponse){
	var flag = baseResponse
	.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if(flag == "success")
	{
		alert("Record Updated Successfully");
		refresh();
	}
	else
	{
		alert("Record Updation Failed");
	}
}

function refresh()
{
	
	document.getElementById("cboBillNo").value="s";
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtSanctionProceedingNo").value="";
	document.getElementById("txtSanctionProceedingDate").value="";
	//document.getElementById("txtAccountHeadCode").value="";
	//document.getElementById("txtAccountHeadCodeDesc").value="";
	//document.getElementById("txtSubLedgerType").value="";
	//document.getElementById("txtSubLedgerCode").value="";
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	document.getElementById("txtScrutinyDate").value="";
	document.getElementById("mtxtRemarks").value="";
	
}
function clearBillNo()
{
	var tbody=document.getElementById("grid_body_new");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtSanctionProceedingNo").value="";
	document.getElementById("txtSanctionProceedingDate").value="";
	
	//document.getElementById("txtSubLedgerType").value="";
	//document.getElementById("txtSubLedgerCode").value="";
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	document.getElementById("txtScrutinyDate").value="";
	document.getElementById("mtxtRemarks").value="";
	//document.getElementById("cboBillNo").innerHTML=" ";
	document.getElementById("cboBillNo").value="s";
	
}

function clear_all()
{
	var tbody=document.getElementById("grid_body_new");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtSanctionProceedingNo").value="";
	document.getElementById("txtSanctionProceedingDate").value="";
	document.getElementById("txtSubLedgerType").value="";
	document.getElementById("txtSubLedgerCode").value="";
	document.getElementById("txtDeductedAmount").value="";
	document.getElementById("txtNetAmount").value="";	
	document.getElementById("txtScrutinyDate").value="";
	document.getElementById("mtxtRemarks").value="";
	document.getElementById("cboBillNo").value="s";
	
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


function selectAll(Opt)
{

  var len=  document.getElementById("grid_body").rows.length;

  if(len==1)
  {
          if (Opt =="ALL")
          {
        	 document.frm_Bill_Scrutiny_Details.verify_select.checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.frm_Bill_Scrutiny_Details.verify_select.checked=false;
        
          }
  }
  else if(len>1)
  {
    //  alert("else "); 
	  for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                	//alert("hi ");
                    document.frm_Bill_Scrutiny_Details.verify_select[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.frm_Bill_Scrutiny_Details.verify_select[i].checked=false;
                }
          }
  }

}
function OnREload()
{
	
		   if (window.name=='' || window.name==null || window.name=='Re_Load')
		   { location.reload();
		      window.name='Reload';
		   }else  if (window.name=='Reload')
			   {
			   
			   }
		 
} 

function onsub(path,msg){
	
	
	var Voucher_list_SL;
if(msg==1){
	window.close();
	 Voucher_list_SL= window.open(path+"/org/FAS/FAS1/BillScrutiny/jsps/Bill_Scrutiny_Details.jsp","Bill_Scrutiny_Details","status=1,resizable=YES, scrollbars=yes"); 
	 
	    Voucher_list_SL.focus();
}else if(msg==2){
	window.history.go(-1);
}
}
function exitfun(path) {
	window.close();
}