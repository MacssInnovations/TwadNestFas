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

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			//alert("manipulate-command:--->>>"+command);
			if (command == "ListAll") {
				//alert("manipulate");
				LoadList(baseResponse);
			}else if (command == "SaveFunc") {
				//alert("manipulate");
				SaveFunc1(baseResponse);
			}else if(command=="loadStart"){
				
				var hi_month=baseResponse.getElementsByTagName("cashbook_month")[0].firstChild.nodeValue;
				var hi_year=baseResponse.getElementsByTagName("cashbook_year")[0].firstChild.nodeValue;
				document.getElementById("hid_year").value=hi_year;
				document.getElementById("hid_month").value=hi_month;
			}
		}
	}
}

function ListAll(path) {
	    //alert(path);	    
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		
		if (txtCB_Year == ""){
			alert("Enter Cash Book Year in the Field");
			document.frmBRS_FollowUp.txtCB_Year.focus();
		}else if (txtCB_Month == ""){
			alert("Enter Cash Book Month in the Field");
			document.frmBRS_FollowUp.txtCB_Month.focus();
		}else if (cmbBankAccNo == ""){
			alert("Enter Bank Account No in the Field");
			document.frmBRS_FollowUp.cmbBankAccNo.focus();
		} else {

			var url = path + "/BRS_FollowUp?command=ListAll&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			//alert(url);
			var xmlrequest = AjaxFunction();

			xmlrequest.open("POST", url, true);

			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
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
			
			var sl_no = baseResponse.getElementsByTagName("sl_no")[k].firstChild.nodeValue;
			if(sl_no == 'null' )
        	{
				sl_no="-";
        	}
			var E_Date = baseResponse.getElementsByTagName("E_Date")[k].firstChild.nodeValue;
			if(E_Date == 'null' )
        	{
				E_Date="-";
        	}
			var tbody = document.getElementById("grid_body_TWAD");
			var table = document.getElementById("mytable");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
			
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
	        
			/** Action Date */
            var cell23=document.createElement("TD");  
            var Action_Date=document.createElement("input");
            Action_Date.type="Text";
            Action_Date.name="Action_Date"+seq;
            Action_Date.id="Action_Date"+seq;
            Action_Date.value="";
            Action_Date.maxLength="10";
            Action_Date.setAttribute('onblur',"dateValidation("+seq+")");
            Action_Date.size="10";
            cell23.appendChild(Action_Date);
            mycurrent_row.appendChild(cell23);                        
            
            /** Action Taken */
            var cell24=document.createElement("TD");  
            var Action_Taken=document.createElement('TEXTAREA','option1');           
            Action_Taken.name="Action_Taken"+seq;
            Action_Taken.id="Action_Taken"+seq;              
            Action_Taken.value="";   
            Action_Taken.setAttribute("cols","5");
            Action_Taken.style.height = "60px";      
            Action_Taken.style.width = "150px";
            cell24.appendChild(Action_Taken);
            mycurrent_row.appendChild(cell24);  			

            var cell41=document.createElement("TD"); 
	        var doc_no1=document.createElement("hidden");
	        doc_no1.type="hidden";
	        doc_no1.name="doc_no"+seq;
	        doc_no1.id="doc_no"+seq; 
	        doc_no1.value=doc_no;
	        cell41.appendChild(doc_no1);
	        var currentText=document.createTextNode(doc_no);
	        cell41.appendChild(currentText);
	        cell41.style.visibility="hidden"
	        mycurrent_row.appendChild(cell41);
	        
	        var cell44=document.createElement("TD"); 
	        var doc_type1=document.createElement("hidden");
	        doc_type1.type="hidden";
	        doc_type1.name="doc_type"+seq;
	        doc_type1.id="doc_type"+seq;    
	        doc_type1.value=doc_type;
	        cell44.appendChild(doc_type1);
	        var currentText=document.createTextNode(doc_type);
	        cell44.appendChild(currentText);
	        cell44.style.visibility="hidden"
	        mycurrent_row.appendChild(cell44);
	        
	        var cell51=document.createElement("TD"); 
	        var sl_nos=document.createElement("hidden");
	        sl_nos.type="hidden";
	        sl_nos.name="sl_nos"+seq; 
	        sl_nos.id="sl_nos"+seq; 
	       // alert("sl_nos.id:-->>"+sl_nos.id);
	        sl_nos.value=sl_no;
	       // alert("sl_nos.value:-->>"+sl_nos.value);
	        cell51.appendChild(sl_nos);
	        var currentText=document.createTextNode(sl_no);
	        cell51.appendChild(currentText);
	        cell51.style.visibility="hidden"
	        mycurrent_row.appendChild(cell51);
	        
	        var cell55=document.createElement("TD"); 
	        var E_Datee=document.createElement("hidden");
	        E_Datee.type="hidden";
	        E_Datee.name="E_Datee"+seq;
	        E_Datee.id="E_Datee"+seq;    
	        E_Datee.value=E_Date;
	        cell55.appendChild(E_Datee);
	        var currentText=document.createTextNode(E_Date);
	        cell55.appendChild(currentText);
	        cell55.style.visibility="hidden"
	        mycurrent_row.appendChild(cell55);
	        
			tbody.appendChild(mycurrent_row);
			seq++;
		}
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
			if(DRAmount1 == 'null' )
        	{
				DRAmount1="-";
        	}
			var TypeOfTransaction1 = baseResponse.getElementsByTagName("TypeOfTransaction1")[k].firstChild.nodeValue;
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
			var doc_no11 = baseResponse.getElementsByTagName("doc_no1")[k].firstChild.nodeValue;
			if(doc_no11 == 'null' )
        	{
				doc_no11="-";
        	}
			var doc_type11 = baseResponse.getElementsByTagName("doc_type1")[k].firstChild.nodeValue;
			if(doc_type11 == 'null' )
        	{
				doc_type11="-";
        	}
			
			var sl_no1 = baseResponse.getElementsByTagName("sl_no1")[k].firstChild.nodeValue;
			if(sl_no1 == 'null' )
        	{
				sl_no1="-";
        	}
			var E_Date1 = baseResponse.getElementsByTagName("E_Date1")[k].firstChild.nodeValue;
			if(E_Date1 == 'null' )
        	{
				E_Date1="-";
        	}
			var tbody = document.getElementById("grid_body_NONTWAD");
			var table = document.getElementById("mytable_details");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq1;
			
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
			
			/** Action Date */
            var cell23=document.createElement("TD");  
            var Action_Date_NT=document.createElement("input");
            Action_Date_NT.type="Text";
            Action_Date_NT.name="Action_Date_NT"+seq1;
            Action_Date_NT.id="Action_Date_NT"+seq1;
            Action_Date_NT.value="";
            Action_Date_NT.maxLength="10";
            Action_Date_NT.setAttribute('onblur',"dateValidation1("+seq1+")");
            Action_Date_NT.size="10";
            cell23.appendChild(Action_Date_NT);
            mycurrent_row.appendChild(cell23);                          
            
            /** Action Taken */
            var cell24=document.createElement("TD");  
            var Action_Taken_NT=document.createElement('TEXTAREA','option1');
            Action_Taken_NT.name="Action_Taken_NT"+seq1;
            Action_Taken_NT.id="Action_Taken_NT"+seq1;              
            Action_Taken_NT.value="";
            Action_Taken_NT.setAttribute("cols","5");
            Action_Taken_NT.style.height = "60px";       
            Action_Taken_NT.style.width = "150px";
            //Amt_Diff.setAttribute('onchange',"callme1("+seq+")");
            Action_Taken_NT.size="10";
            cell24.appendChild(Action_Taken_NT);
            mycurrent_row.appendChild(cell24);  			

            var cell41=document.createElement("TD"); 
	        var doc_no1=document.createElement("hidden");
	        doc_no1.type="hidden";
	        doc_no1.name="doc_no_NT"+seq1;
	        doc_no1.id="doc_no_NT"+seq1; 
	        doc_no1.value=doc_no11;
	        cell41.appendChild(doc_no1);
	        var currentText=document.createTextNode(doc_no11);
	        cell41.appendChild(currentText);
	        cell41.style.visibility="hidden"
	        mycurrent_row.appendChild(cell41);
	        
	        var cell44=document.createElement("TD"); 
	        var doc_type1=document.createElement("hidden");
	        doc_type1.type="hidden";
	        doc_type1.name="doc_type_NT"+seq1;
	        doc_type1.id="doc_type_NT"+seq1;    
	        doc_type1.value=doc_type11;
	        cell44.appendChild(doc_type1);
	        var currentText=document.createTextNode(doc_type11);
	        cell44.appendChild(currentText);
	        cell44.style.visibility="hidden"
	        mycurrent_row.appendChild(cell44);
	        
	        var cell51=document.createElement("TD"); 
	        var sl_no11=document.createElement("hidden");
	        sl_no11.type="hidden";
	        sl_no11.name="sl_no11"+seq1;   
	        sl_no11.id="sl_no11"+seq1; 
	        sl_no11.value=sl_no1;
	        cell51.appendChild(sl_no11);
	        var currentText=document.createTextNode(sl_no1);
	        cell51.appendChild(currentText);
	        cell51.style.visibility="hidden"
	        mycurrent_row.appendChild(cell51);
	        
	        var cell55=document.createElement("TD"); 
	        var E_Date11=document.createElement("hidden");
	        E_Date11.type="hidden";
	        E_Date11.name="E_Date11"+seq1;
	        E_Date11.id="E_Date11"+seq1;    
	        E_Date11.value=E_Date1;
	        cell55.appendChild(E_Date11);
	        var currentText=document.createTextNode(E_Date1);
	        cell55.appendChild(currentText);
	        cell55.style.visibility="hidden"
	        mycurrent_row.appendChild(cell55);
	        
			tbody.appendChild(mycurrent_row);	
			seq1++;
		}
		seq1 = 0;
	} else {
		alert("Fail to Load Grid Values");
	}
}

function SaveFunc(path)
{
	var k = 0
	var kk = 0
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;	
	var al = new Array();		
	var al1 = new Array();
	for(var i=0;i<rowcount;i++)
    {
		if(document.getElementById("Action_Date"+i).value != "")
		{
		   al[k] = "T";
		   k++;    
	       al[k]=document.getElementById("Action_Date"+i).value;
	       k++; 
	       al[k]=document.getElementById("Action_Taken"+i).value;
	       k++;       
	       al[k]=document.getElementById("doc_no"+i).value;
	       k++; 
	       al[k]=document.getElementById("doc_type"+i).value;
	       k++; 
	       al[k]=document.getElementById("sl_nos"+i).value;
	       k++; 
	       al[k]=document.getElementById("E_Datee"+i).value;
	       k++;        
		}
    }
	//alert("al:->"+al);
	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var rowcount1 = tbody1.rows.length;
	for(var i=0;i<rowcount1;i++)
    {
		if(document.getElementById("Action_Date_NT"+i).value != "")
		{
		   al1[kk] = "NT";
		   kk++;     
	       al1[kk]=document.getElementById("Action_Date_NT"+i).value;
	       kk++;    
	       al1[kk]=document.getElementById("Action_Taken_NT"+i).value;
	       kk++;    
	       al1[kk]=document.getElementById("doc_no_NT"+i).value;
	       kk++; 
	       al1[kk]=document.getElementById("doc_type_NT"+i).value;
	       kk++; 
	       al1[kk]=document.getElementById("sl_no11"+i).value;
	       kk++; 
	       al1[kk]=document.getElementById("E_Date11"+i).value;
	       kk++; 
		}
    }	
	//alert("al1:->"+al1)
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
if(dateCheck() && dateCheck1())
{
	if(emptyCheck())
	{
	if (txtCB_Year == ""){
		alert("Enter Cash Book Year in the Field");
		document.frmBRS_FollowUp.txtCB_Year.focus();
	}else if (txtCB_Month == ""){
		alert("Enter Cash Book Month in the Field");
		document.frmBRS_FollowUp.txtCB_Month.focus();
	}else if (cmbBankAccNo == ""){
		alert("Enter Bank Account No in the Field");
		document.frmBRS_FollowUp.cmbBankAccNo.focus();
	} else {

		var url = path + "/BRS_FollowUp?command=SaveFunc&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo+ "&al=" + al+ "&al1=" + al1;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}
}
}

function emptyCheck()
{

	var tbodyEC1 = document.getElementById("grid_body_TWAD");
	var rowcountEC1 = tbodyEC1.rows.length;	
	for(var i=0;i<rowcountEC1;i++)
    {
		if((document.getElementById("Action_Date"+i).value !="") && (document.getElementById("Action_Taken"+i).value == ""))
		{
			alert("Enter Cash Book Transactions's - Action Taken in the Field");
			document.getElementById("Action_Taken"+i).focus();
			return false;
		}else if((document.getElementById("Action_Date"+i).value=="") && (document.getElementById("Action_Taken"+i).value !=""))
		{
			alert("Enter Cash Book Transactions's - Action Date in the Field");
			document.getElementById("Action_Date"+i).focus();
			return false;
		}
    }
	
	var tbodyEC = document.getElementById("grid_body_NONTWAD");
	var rowcountEC = tbodyEC.rows.length;	
	for(var i=0;i<rowcountEC;i++)
    {
		if((document.getElementById("Action_Date_NT"+i).value !="") && (document.getElementById("Action_Taken_NT"+i).value == ""))
		{
			alert("Enter Bank Transactions's - Action Taken in the Field");
			document.getElementById("Action_Taken_NT"+i).focus();
			return false;
		}else if((document.getElementById("Action_Date_NT"+i).value =="") && (document.getElementById("Action_Taken_NT"+i).value !=""))
		{
			alert("Enter Bank Transactions's - Action Date in the Field");
			document.getElementById("Action_Date_NT"+i).focus();
			return false;
		}
    }
	return true;
}
function dateCheck()
{
	var g = 0;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0"+txtCB_Month);
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;	
	var tbody1 = document.getElementById("grid_body_NONTWAD");
	var rowcount1 = tbody1.rows.length;
	for ( var i = 0; i < rowcount; i++) {
		var r = tbody.rows[i];
		var s = r.cells.length;	
		var Entry_Datee = document.getElementById("Action_Date"+g).value; 
		var Entry_Date = document.getElementById("Action_Date"+g).value; 
		//alert("Entry_Date:->"+Entry_Date);
	    var r_date = r.cells[0].firstChild.nodeValue;
	    //alert("r_date:->"+r_date);
	    var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = r_date.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);
		if(Entry_Datee != "")
		{
		if(Entry_Date1 < r_date1){
			alert("Cash Book Transactions's - Action Date Should Be Greater Than Remitance Date");
			document.getElementById("Action_Date"+g).focus();
			return false;
		}else if(dd1[2] != txtCB_Year){
			alert("Cash Book Transactions's - Action Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Action_Date"+g).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1){
			alert("Cash Book Transactions's - Action Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Action_Date"+g).focus();
			return false;
		}
		}
		g = g+1;				
	}	
	g = 0;	
	return true;
}

function dateCheck1()
{
	
	var gg = 0;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var txtCB_Month1 = ("0"+txtCB_Month);
	var tbody = document.getElementById("grid_body_TWAD");
	var rowcount = tbody.rows.length;	
	var tbody1 = document.getElementById("grid_body_NONTWAD");
	  var  txtCB_Year_hid       = document.getElementById("hid_year").value;   
      var  txtCB_Month_hid    = document.getElementById("hid_month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);
      var txtCB_Month_hid1 = ("0"+txtCB_Month_hid);
	var rowcount1 = tbody1.rows.length;
	for ( var i = 0; i < rowcount1; i++) {
		var r = tbody1.rows[i];
		var s = r.cells.length;	
		var Entry_Datee = document.getElementById("Action_Date_NT"+gg).value; 
		var Entry_Date = document.getElementById("Action_Date_NT"+gg).value; 
		//alert("Entry_Date:->"+Entry_Date);
	    var r_date = r.cells[0].firstChild.nodeValue;
	    //alert("Pass Book Date:->"+r_date);
	    
      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = r_date.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);
		if(Entry_Datee != "")
		{
		if(Entry_Date1 < r_date1){
			alert("Bank Transactions's - Action Date Should Be Greater Than Remitance Date");
			document.getElementById("Action_Date"+gg).focus();
			return false;
		}else if(dd1[2] != txtCB_Year){
			alert("Bank Transactions's - Action Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Action_Date"+gg).focus();
			return false;
		}else if(dd1[1] != txtCB_Month1 && dd1[1] != txtCB_Month_hid1){
			alert("Bank Transactions's - Action Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Action_Date"+gg).focus();
			return false;
		}
		}
		gg = gg+1;				
	}	
	gg = 0;	
	return true;
}
function SaveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		alert("Record Inserted Successfully");
		refresh();
	} else {
		alert("Record Insertion Failed");
		var date1 = baseResponse.getElementsByTagName("Date")[0].firstChild.nodeValue;
		if (date1 == "invalid") {
			alert(" Enter Valid Action Date ex:-dd/mm/yyyy ");
		}
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

function dateValidation(seq)
{
      var Entry_Date = document.getElementById("Action_Date"+seq).value; 
      
		var tbody = document.getElementById("grid_body_TWAD");
		var rowcount = tbody.rows.length;
		var remDate = 0;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			var s = r.cells.length;		
				if(i == seq)
				{
				remDate = r.cells[0].firstChild.nodeValue;						
			}
		}
      var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var hid_year=document.getElementById("hid_year").value;
      var hid_month=document.getElementById("hid_month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);
      if(hid_month.length==1)
var hid_month1="0"+hid_month;
      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = remDate.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);   
		if((document.getElementById("Action_Date"+seq).value!="") &&(Entry_Date1 < r_date1)){
			alert("Action Date Should Be Greater Than Remitance Date ....");
			document.getElementById("Action_Date"+seq).value="";
			document.getElementById("Action_Date"+seq).focus();
			return false;
		}
		//Joan Change on 22 Sep 2014
		/*else if((document.getElementById("Action_Date"+seq).value!="") && (dd1[2] != txtCB_Year)){
			alert("Action Date Year Should Be Equal To Cashbook Year ...");
			document.getElementById("Action_Date"+seq).value="";
			document.getElementById("Action_Date"+seq).focus();
			return false;
		}else if((document.getElementById("Action_Date"+seq).value!="") &&(dd1[1] != txtCB_Month1)){
			alert("Action Date Month Should Be Equal To Cashbook Month ...");
			document.getElementById("Action_Date"+seq).value="";
			document.getElementById("Action_Date"+seq).focus();
			return false;
		}*/
		else if((document.getElementById("Action_Date"+seq).value!="") && (dd1[2] != hid_year || dd1[2] !=dd2[2])){
			
			
			alert("Action Date Year Should Be Equal To Cashbook Year ..."+dd1[2]+hid_year+dd1[2]+dd2[2]);
			document.getElementById("Action_Date"+seq).value="";
			document.getElementById("Action_Date"+seq).focus();
			return false;
		}else if((document.getElementById("Action_Date"+seq).value!="") && (dd1[1]!= hid_month1 && dd1[1] !=dd2[1])){
			
			alert("Action Date Month Should Be Equal To Cashbook Month ...");
			document.getElementById("Action_Date"+seq).value="";
			document.getElementById("Action_Date"+seq).focus();
			return false;
		}
		
		
		else{
			return true;
		}
}

function dateValidation1(seq)
{
      var Entry_Date = document.getElementById("Action_Date_NT"+seq).value; 
      
		var tbody = document.getElementById("grid_body_NONTWAD");
		var rowcount = tbody.rows.length;
		var remDate = 0;
		for ( var i = 0; i < rowcount; i++) {
			var r = tbody.rows[i];
			var s = r.cells.length;		
				if(i == seq)
				{
				remDate = r.cells[0].firstChild.nodeValue;						
			}
		}
      var  txtCB_Year       = document.getElementById("txtCB_Year").value;   
      var  txtCB_Month      = document.getElementById("txtCB_Month").value;
      var  txtCB_Year_hid       = document.getElementById("hid_year").value;   
      var  txtCB_Month_hid    = document.getElementById("hid_month").value;
      var txtCB_Month1 = ("0"+txtCB_Month);
      var txtCB_Month_hid1 = ("0"+txtCB_Month_hid);
      var browser = navigator.appName;

		if (browser == "Netscape") {
			var dd1 = Entry_Date.split('/');
			Entry_Date = dd1[1] + "/" + dd1[0] + "/" + dd1[2];
			var dd2 = remDate.split('/');
			r_date = dd2[1] + "/" + dd2[0] + "/" + dd2[2];
		}
		var a = Entry_Date.split('/');
		var b = r_date.split('/');
		
		var Entry_Date1 = new Date(a[2], a[0] - 1, a[1]);
		var r_date1 = new Date(b[2], b[0] - 1, b[1]);   
		if((document.getElementById("Action_Date_NT"+seq).value!="") &&(Entry_Date1 < r_date1)){
			alert("Action Date Should Be Greater Than Pass Book Date");
			document.getElementById("Action_Date_NT"+seq).focus();
			return false;
		}else if((document.getElementById("Action_Date_NT"+seq).value!="") && ((dd1[2] != txtCB_Year)|| (dd1[2] != txtCB_Year_hid))){
			alert("Action Date Year Should Be Equal To Cashbook Year");
			document.getElementById("Action_Date_NT"+seq).focus();
			return false;
		}/*else if((document.getElementById("Action_Date_NT"+seq).value!="") &&(dd1[1] != txtCB_Month1)){
			alert("Action Date Month Should Be Equal To Cashbook Month");
			document.getElementById("Action_Date_NT"+seq).focus();
			return false;
		}*/
	
		else if(dd1[1] != txtCB_Month_hid1 &&  dd1[1] != txtCB_Month1)
		{	//alert(" dd1[1] "+dd1[1]+"  txtCB_Month_hid1 "+ txtCB_Month_hid1+" txtCB_Month1 "+txtCB_Month1 );
			alert("Action Date Month Should Be Equal To Cashbook Month Or Start Month");
			return false;
		}/*else if (dd1[1] == txtCB_Month_hid1){
			
		 if(dd1[0]>01 && dd1[0]<31)
		{
			
		}else {
			alert("Action Date Should Be Equal To Cashbook Month Or Start Month");
		}
		}*/
		else{
			return true;
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
	                   
	       document.frmBRS_FollowUp.txtCB_Year.value=year;
	       document.frmBRS_FollowUp.txtCB_Month.value=month;
	       document.getElementById("cmbBankAccNo").value="";
	       LoadAccountingUnitID('LIST_ALL_UNITS');
}

function exitfun(path) {
	window.close();
}



function loadStart(path)
{
	
		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		var txtCB_Year=document.getElementById("txtCB_Year").value;
		var txtCB_Month=document.getElementById("txtCB_Month").value;
		var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;

		var url = path + "/BRS_FollowUp?command=loadStart&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;
	//
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	
}