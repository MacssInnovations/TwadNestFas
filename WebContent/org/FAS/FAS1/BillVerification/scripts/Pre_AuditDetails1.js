
var seq=0;
var com_id;
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


function loadDetails(path) {
		var url = path+ "/Bill_Scrutiny_Details.bs?command=loadDetails";
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function loadDetails1(baseResponse) {
	var seq =0;
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert("kkk");
	if (flag == "success") {

		var count=baseResponse.getElementsByTagName("slno")[0].firstChild.nodeValue;
	
         var tbody=document.getElementById("tblList");
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
       //  LoadBillNo();
	}
	else if(flag == "NoData") {
		var tbody=document.getElementById("tblList");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	       tbody.deleteRow(0);
	    }
		alert("Record Does Not Exists");
	}
	else
	{
		var tbody=document.getElementById("tblList");
	    var t=0;
	    for(t=tbody.rows.length-1;t>=0;t--)
	    {
	       tbody.deleteRow(0);
	    } 
	alert("Failed to Load");
}
	
}
function testcall()
{

	var preauditsentondate=document.getElementById("preauditsentondate").value;
	var txtBillDate_one=preauditsentondate.split("/");
	
	var txtDateOfReceipt=document.getElementById("txtDateOfReceipt").value;
	 var txtChecked_two=txtDateOfReceipt.split("/");
	
	 if(txtBillDate_one[2]>txtChecked_two[2])
	 {
		document.getElementById("txtDateOfReceipt").value="";
		alert("Date Of Receipt Should be Greater than PreAudit-Sent on Date");
		return false;
		
	 }
	 else if(txtBillDate_one[2]==txtChecked_two[2])
	 {
		
   	 if(txtBillDate_one[1]>txtChecked_two[1])
   	 {
   		document.getElementById("txtDateOfReceipt").value="";
		alert("Date Of Receipt Should be Greater than PreAudit-Sent on Date");
		return false;
   	 }
   	 else if(txtBillDate_one[1]==txtChecked_two[1])
   	 {
   		 var apl;
   		 if(txtBillDate_one[0].length==2)
   		 {
   			apl=txtBillDate_one[0];
   		 }
   		 else
   		 {
   			apl="0"+txtBillDate_one[0];
   		 }
   		 if(apl>txtChecked_two[0])
       	 {
   			document.getElementById("txtDateOfReceipt").value="";
   			alert("Date Of Receipt Should be Greater than PreAudit-Sent on Date");
   			return false;
       	 } 
   	 }
	 }
	 document.getElementById("txtAuditDate").value="";
	 document.getElementById("txtDate").value="";
}
function checkmtc()
{
	
	var txtAuditDate=document.getElementById("txtAuditDate").value;
	 var txtChecked_two=txtAuditDate.split("/");
	
	 var txtDateOfReceipt=document.getElementById("txtDateOfReceipt").value;
		var txtMTCUpdatedDate_one=txtDateOfReceipt.split("/");
	 
	 if(txtMTCUpdatedDate_one[2]>txtChecked_two[2])
	 {
		document.getElementById("txtAuditDate").value="";
		alert("txtAuditDate Should not be less than Date Of Receipt");
		return false;
		
	 }
	 else if(txtMTCUpdatedDate_one[2]==txtChecked_two[2])
	 {
		
   	 if(txtMTCUpdatedDate_one[1]>txtChecked_two[1])
   	 {
   		document.getElementById("txtAuditDate").value="";
		alert("txtAuditDate Should not be less than Date Of Receipt");
		return false;
   	 }
   	 else if(txtMTCUpdatedDate_one[1]==txtChecked_two[1])
   	 {
   		 var apl;
   		 if(txtMTCUpdatedDate_one[0].length==2)
   		 {
   			apl=txtMTCUpdatedDate_one[0];
   		 }
   		 else
   		 {
   			apl="0"+txtMTCUpdatedDate_one[0];
   		 }
   		 if(apl>txtChecked_two[0])
       	 {
   			document.getElementById("txtAuditDate").value="";
   			alert("txtAuditDate Should not be less than Date Of Receipt");
   			return false;
       	 } 
   	 }
	 }
	 document.getElementById("txtDate").value="";
}
function checkaudit()
{

	
	
	 var txtAuditDate=document.getElementById("txtAuditDate").value;
		var txtMTCUpdatedDate_one=txtAuditDate.split("/");
		
		var txtDate=document.getElementById("txtDate").value;
		 var SenttoPreAuditontwo=txtDate.split("/");
	 
	 if(txtMTCUpdatedDate_one[2]>SenttoPreAuditontwo[2])
	 {
		document.getElementById("txtDate").value="";
		alert("Send to treasury section on Date Should not be less than Audit Date");
		return false;
		
	 }
	 else if(txtMTCUpdatedDate_one[2]==SenttoPreAuditontwo[2])
	 {
		
   	 if(txtMTCUpdatedDate_one[1]>SenttoPreAuditontwo[1])
   	 {
   		document.getElementById("txtDate").value="";
		alert("Send to treasury section on Date Should not be less than Audit Date");
		return false;
   	 }
   	 else if(txtMTCUpdatedDate_one[1]==SenttoPreAuditontwo[1])
   	 {
   		 var apl;
   		 if(txtMTCUpdatedDate_one[0].length==2)
   		 {
   			apl=txtMTCUpdatedDate_one[0];
   		 }
   		 else
   		 {
   			apl="0"+txtMTCUpdatedDate_one[0];
   		 }
   		 if(apl>SenttoPreAuditontwo[0])
       	 {
   			document.getElementById("txtDate").value="";
   			alert("Send to treasury section on Date Should not be less than Audit Date");
   			return false;
       	 } 
   	 }
	 }	
}


function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
	        if(command=="add")
			{
			getValues(baseResponse);
			}
		    else if(command=="loadgrid")
		    {
		    LoadGridValues(baseResponse);
			}
			else if (command == "loadmajortype") {
			loadMajor(baseResponse);
		    }
			else if(command=="loadbillno")
			{
				LoadBillNo1(baseResponse);
			}
			 else if(command=="loadempdetails")
             {
                   LoadEmpDetails(baseResponse);
             }
			 else if(command=="getbilldatails")
			 {
				 //alert("enter if");
				 LoadBillDetails(baseResponse);	 
			 }
			 else if (command == "loadDetails") {
					loadDetails1(baseResponse);
				}
	         
}
}
}
function LoadBillDetails(baseResponse)
{
	//alert("enter");
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if (flag == "success") {
	document.getElementById("txtBillDate").value= baseResponse.getElementsByTagName("billdate")[0].firstChild.nodeValue;
	document.getElementById("preauditsentondate").value= baseResponse.getElementsByTagName("SENT_TO_PRE_AUDIT_ON")[0].firstChild.nodeValue;
	
	document.getElementById("txtBillAmount").value= baseResponse.getElementsByTagName("billamt")[0].firstChild.nodeValue;
	
	if(baseResponse.getElementsByTagName("treDate")[0].firstChild.nodeValue=="null")
	{
    document.getElementById("txtApprovalDate").value="-";
	}
	else{ 
		 document.getElementById("txtApprovalDate").value=baseResponse.getElementsByTagName("appdate")[0].firstChild.nodeValue;
	}
	
	if(baseResponse.getElementsByTagName("treDate")[0].firstChild.nodeValue=="null")
	{
    document.getElementById("txtVerificationDate").value="-";
	}
	else{ 
		 document.getElementById("txtVerificationDate").value=baseResponse.getElementsByTagName("treDate")[0].firstChild.nodeValue;
	}
	
    var major_code = baseResponse.getElementsByTagName("major_type")[0].firstChild.nodeValue;
	var major_desc = baseResponse.getElementsByTagName("major_desc")[0].firstChild.nodeValue;
	document.getElementById("cmbMajorType").options[document.getElementById("cmbMajorType").selectedIndex].text=major_desc;
    document.getElementById("cmbMajorType").options[document.getElementById("cmbMajorType").selectedIndex].value=major_code;
    
    var minortype=baseResponse.getElementsByTagName("minortype")[0].firstChild.nodeValue;
    var minordesc=baseResponse.getElementsByTagName("minordesc")[0].firstChild.nodeValue;
   
    document.getElementById("minor_desc_id").value=minordesc;
    document.getElementById("txtBillMinorType").value=minortype;
    
    var subtype=baseResponse.getElementsByTagName("subtype")[0].firstChild.nodeValue;
    var subdesc=baseResponse.getElementsByTagName("subdesc")[0].firstChild.nodeValue;
    document.getElementById("sub_desc_id").value=subdesc;
    document.getElementById("txtBillSubType").value=subtype;
    document.getElementById("ori_unit").value=baseResponse.getElementsByTagName("Accounting_Unit_Id")[0].firstChild.nodeValue;
    document.getElementById("ori_office").value=baseResponse.getElementsByTagName("Accounting_Unit_Office_Id")[0].firstChild.nodeValue;
    
	}
	else
	{
		alert("NO Data In Bill Register Table");
	}
}
function getValues(baseResponse)
{
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	alert("Record inserted Successfully");
	refreash();
	LoadBillNo();
	}
	else
	{
		alert("Have Some Error in Insert");
	}
	
}
function LoadBillNo()
{
	//alert("enter");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year=document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month=document.getElementById("cboCashBook_Month").value;
	var url = "../../../../../Pre_AuditDetails?command=loadbillno&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&cboCashBook_Year="+cboCashBook_Year+"&cboCashBook_Month="+cboCashBook_Month;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
		
}
function LoadBillNo1(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	    var len =baseResponse.getElementsByTagName("billno").length;
		var se = document.getElementById("cmbBillNO");
		document.getElementById("majorhidden").value=baseResponse.getElementsByTagName("major")[0].firstChild.nodeValue;
		
		se.length=1;
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("billno")[i].firstChild.nodeValue;
			var sancid = baseResponse.getElementsByTagName("sancid")[i].firstChild.nodeValue;
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1id+" ("+sancid+")");
			op.appendChild(txt);
			se.appendChild(op);
		}
	}
	else if(flag=="Nodata")
	{
		alert("NO Data For BillNumber");
		var se = document.getElementById("cmbBillNO");
		se.length=1;
	}
	
	else {
		alert("Fail to Load");
	}
	
	
}
function loadEmpid(path,id){
	
	document.getElementById("txtEmpID_mas").value=id;
	   var url="";
       //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
         url = "../../../../../Pre_AuditDetails?command=getEmpName&empid="+id;
        
         var req=getTransport();
          req.open("GET",url,true);        
          req.onreadystatechange=function()
          {
        	  if (req.readyState == 4) {
        			if (req.status == 200) {

        				var baseResponse = req.responseXML.getElementsByTagName("response")[0];
        				
        				var tagCommand = baseResponse.getElementsByTagName("command")[0];
        				var command = tagCommand.firstChild.nodeValue;
        		        if(command=="loadempdetails"){

        	                 
        	                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
        	                  if(flag=="success")
        	                  {                       
        	                         var emp_name=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
        	                        // var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
        	                        // var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
        	                        
        	                                
        	                                document.frmPre_AuditDetails.txtSanction_Estimate_PreparedBy.value=emp_name;
        	                         
        	                }
        	               
        	               

        		        	}
        		        }
          } }  ;
          req.send(null);
 
	
}
function loadEmpid1(path,id){
	
	document.getElementById("txtEmpID_mas1").value=id;
	   var url="";
       //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
         url = "../../../../../Pre_AuditDetails?command=getEmpName&empid="+id;
     
         var req=getTransport();
          req.open("GET",url,true);        
          req.onreadystatechange=function()
          {

        	  if (req.readyState == 4) {
        			if (req.status == 200) {

        				var baseResponse = req.responseXML.getElementsByTagName("response")[0];
        				
        				var tagCommand = baseResponse.getElementsByTagName("command")[0];
        				var command = tagCommand.firstChild.nodeValue;
        		        if(command=="loadempdetails"){

        	                 
        	                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
        	                  if(flag=="success")
        	                  {                       
        	                         var emp_name=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
        	                        // var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
        	                        // var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
        	                        
        	                                document.frmPre_AuditDetails.txtSanction_Estimate_ApprovedBy.value=emp_name;
        	                               
        	                         
        	                }
        	               
        	               

        		        	}
        		        }
          } 
          } ;  
          req.send(null);
 
	
}


function getBillDetails()
{
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year=document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month=document.getElementById("cboCashBook_Month").value;
	var cmbBillNO=document.getElementById("cmbBillNO").value;
	
	var majorhidden=document.getElementById("majorhidden").value;
     var url = "../../../../../Pre_AuditDetails?command=getbilldatails&cmbAcc_UnitCode="+cmbAcc_UnitCode+
     "&cmbOffice_code="+cmbOffice_code+"&cboCashBook_Year="+cboCashBook_Year+"&cmbBillNO="+cmbBillNO+
     "&cboCashBook_Month="+cboCashBook_Month+"&majorhidden="+majorhidden;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);		
}

function loadMajor(baseResponse)
{
	
var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {
	
		//alert( baseResponse.getElementsByTagName("date3")[0].firstChild.nodeValue);
		
	    var len =baseResponse.getElementsByTagName("majorid").length;
	
	    for ( var i = 0; i < len; i++) {
			var com1id = baseResponse.getElementsByTagName("majorid")[i].firstChild.nodeValue;
			var com1name = baseResponse.getElementsByTagName("majorname")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbMajorType");
			var op = document.createElement("OPTION");
			op.value = com1id;
			var txt = document.createTextNode(com1name);
			op.appendChild(txt);
			se.appendChild(op);

		}
	}
	else if(flag=="NoData")
	{
		
		alert("No Record is Found");
	}
	
	
	
	else {
		alert("Fail to Load");
	}	
}

function loadgrid()
{
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year=document.getElementById("cboCashBook_Year").value;
	var cboCashBook_Month=document.getElementById("cboCashBook_Month").value;
	var url = "../../../../../Pre_AuditDetails?command=loadgrid&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="
	+cmbOffice_code+"&cboCashBook_Year="+cboCashBook_Year+"&cboCashBook_Month="+cboCashBook_Month;
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	
}


function LoadGridValues(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	if (flag == "success") {		
		var len= baseResponse.getElementsByTagName("descode").length;
		 try
		  {
		  var tbody = document.getElementById("tblList");
		    var rowcount=tbody.rows.length;

		    //alert("rowcount"+rowcount);
		    var al= new Array() ;
		  
		    for(var i=0;i<rowcount;i++)
		        {
		    	var ri=tbody.rows[i];
		    	   tbody.deleteRow(ri);
		        }

		  }
		  catch(e)
		  {
		      //alert(e);
		  }
		      
		   for(k=0;k<len;k++)
       	    {
			 try
	          {
		var descode = baseResponse.getElementsByTagName("descode")[k].firstChild.nodeValue;
		var des = baseResponse.getElementsByTagName("des")[k].firstChild.nodeValue;
		var mandate = baseResponse.getElementsByTagName("mandate")[k].firstChild.nodeValue;
		var app = baseResponse.getElementsByTagName("app")[k].firstChild.nodeValue;
		//alert("enter into try ");
		var tbody = document.getElementById("tblList");
	    var table = document.getElementById("Existing");
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = seq;
		var cell = document.createElement("TD");
		var anc = document.createElement("input");
		anc.type = "checkbox";
		anc.id=seq;
		anc.name="check";
		anc.checked=true;
		anc.value=seq;
		
		
		
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);
		//alert("one"+cmbBillNO);
		
		var cell1 = document.createElement("TD");
		var descode = document.createTextNode(descode);
		cell1.appendChild(descode);
		mycurrent_row.appendChild(cell1);

		var cell2 = document.createElement("TD");
		var des = document.createTextNode(des);
		cell2.appendChild(des);
		mycurrent_row.appendChild(cell2);
        
		var cell3 = document.createElement("TD");
		var mandate = document.createTextNode(mandate);
		cell3.appendChild(mandate);
		mycurrent_row.appendChild(cell3);
		
		var cell4 = document.createElement("TD");
		var app = document.createTextNode(app);
		cell4.appendChild(app);
		mycurrent_row.appendChild(cell4);
        
		
		tbody.appendChild(mycurrent_row);
	    seq++;
	 
	}
		
	catch(errorObject)
	{
		
	}
	}
		   LoadBillNo();
	}
	else if(flag=="NoData")
	{
		alert("Pre Audit Entry List Not found");
	}
	else 
	{
		alert("Error in Loading Data");
	}
		
}

function loadmajortype()
{
	//alert("enter");
	  var url = "../../../../../Pre_AuditDetails?command=loadmajortype";
    
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
}
function add()
{
	var k=0;
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var cmbMajorType=document.getElementById("cmbMajorType").value;
	var cmbBillNO=document.getElementById("cmbBillNO").value;
	var txtBillDate=document.getElementById("txtBillDate").value;
	var txtBillAmount=document.getElementById("txtBillAmount").value;
	var txtBillMinorType=document.getElementById("txtBillMinorType").value;
	var txtBillSubType=document.getElementById("txtBillSubType").value;
	var txtApprovalDate=document.getElementById("txtApprovalDate").value;
	var txtVerificationDate=document.getElementById("txtVerificationDate").value;
	var txtDateOfReceipt=document.getElementById("txtDateOfReceipt").value;
	var receivedBy= document.getElementById("txtEmpID_mas").value;
	var txtAuditDate=document.getElementById("txtAuditDate").value;
	var auditBy=document.getElementById("txtEmpID_mas1").value;
	
	

	  var tbody=document.getElementById("tblList");

	  var checkbox = document.getElementsByName('verify_select');
	
	    var chvalue=document.getElementsByName("verify_select_status");
	  
	    var ln = checkbox.length;
	var count_nam=0;
	var count_chk=0;
		for(var i=0;i<ln;i++)
		{
			
			//alert("pro:::"+document.frm_Bill_Scrutiny_Details.manda1[i].value);
			 if(document.frmPre_AuditDetails.manda1[i].value=="Y")
			 {
				 count_nam++;
				
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
	
	
	if(count_chk!=count_nam)
	{
		alert("Please Select the checkBox for Mandatory Field Y");
		return false;
	}
	
	
	 if(document.frmPre_AuditDetails.r1[0].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[0].value;
	 }
	 else if(document.frmPre_AuditDetails.r1[1].checked==true)
	 {
		 var r1=document.frmPre_AuditDetails.r1[1].value; 
	 }
	
	 var sendtreasury=document.getElementById("txtDate").value
	 var mtxtRemarks=document.getElementById("mtxtRemarks").value
	 var valid=txt_empty('cmbAcc_UnitCode|cmbOffice_code|cmbMajorType|cmbBillNO|txtBillDate|txtBillAmount|txtBillMinorType|txtBillSubType|txtApprovalDate|txtVerificationDate|txtEmpID_mas|txtAuditDate|txtEmpID_mas1|mtxtRemarks');
	
	if(valid)
	{
	
	 var tbody = document.getElementById("tblList");
		var rowcount=tbody.rows.length;
		
	
		var al= new Array() ;
	   
	    for(var i=0;i<rowcount;i++)
	    	{
	    	   var r=tbody.rows[i];
	    	   var s=r.cells.length;
	    	  
		// alert("s:::"+s);
	    	   for(var j=0;j<s;j++)
	    		   {
	    		   //alert("t:"+r.cells[3].firstChild.checked);
	    		   if(r.cells[3].firstChild.checked)
	    		   {
	    		//		alert("checked");
	    		   
	    		   al[k]=r.cells[j].firstChild.nodeValue;
	    		 //  alert( al[i]);
	    		    k++; 
	    		     //alert(r.cells[j].firstChild.nodeValue);
	    		   }
	    		   }
	    	
	    	}
	//alert(al);
    var url = "../../../../../Pre_AuditDetails?command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbMajorType="+cmbMajorType+"&txtBillMinorType="+txtBillMinorType+"&txtBillSubType="+txtBillSubType+"&cmbBillNO="+cmbBillNO+
    "&txtBillDate="+txtBillDate+"&txtBillAmount="+txtBillAmount+"&receivedBy="+receivedBy+"&txtAuditDate="+txtAuditDate+"&auditBy="+auditBy+"&sendtreasury="+sendtreasury+"&r1="
       +r1+"&mtxtRemarks="+mtxtRemarks+"&grid="+al+"&txtApprovalDate="+txtApprovalDate+"&txtDateOfReceipt="+txtDateOfReceipt+"&txtVerificationDate="+txtVerificationDate+"&ori_unit="+document.getElementById("ori_unit").value+"&ori_office="+document.getElementById("ori_office").value;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		}

	xmlrequest.send(null);	
	
	}
	
}

function exitfun()
{
	//alert("exit");
	window.close();
	}

function refreash()
{
	document.getElementById("cmbMajorType").value="s";
	document.getElementById("cmbBillNO").value="s";
	document.getElementById("txtBillDate").value="";
	document.getElementById("txtBillAmount").value="";
	document.getElementById("txtBillMinorType").value="";
	document.getElementById("txtBillSubType").value="";
	document.getElementById("txtApprovalDate").value="";
	document.getElementById("txtVerificationDate").value="";
	document.getElementById("txtDateOfReceipt").value="";
	document.getElementById("txtSanction_Estimate_PreparedBy").value="";
	document.getElementById("txtEmpID_mas").value="";
	document.getElementById("txtAuditDate").value="";
	document.getElementById("txtSanction_Estimate_ApprovedBy").value="";
	document.getElementById("txtEmpID_mas1").value="";
	document.getElementById("txtDate").value="";
	document.getElementById("mtxtRemarks").value="";
	document.frmPre_AuditDetails.r1[0].checked=false;
	document.frmPre_AuditDetails.r1[1].checked=false;
	
	
	 try
	  {
	  var tbody = document.getElementById("tblList");
	    var rowcount=tbody.rows.length;

	    //alert("rowcount"+rowcount);
	    var al= new Array() ;
	  
	    for(var i=0;i<rowcount;i++)
	        {
	    	var ri=tbody.rows[i];
	    	   tbody.deleteRow(ri);
	        }

	  }
	  catch(e)
	  {
	      //alert(e);
	  }

	
}

///////////////////////////////Employeeee Code And Employee Name

function emp_sanction_preparedby()
{
        emp_flag=1;    
        Load_emp_details();
}
function emp_popup_sanction_preparedby()
{
        
        emp_flag=1;    
        servicepopup();
        
}
function emp_sanction_approvedby()
{
        emp_flag=2;  
        Load_emp_details();
}
function emp_popup_sanction_approvedby()
{
        emp_flag=2;    
        servicepopup();
}


var winemp;
function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(emp)
{
        if(emp_flag==1)
        {
                document.frmPre_AuditDetails.txtEmpID_mas.value=emp;
                Load_emp_details();
        }
        else if(emp_flag==2)
        {
                document.frmPre_AuditDetails.txtEmpID_mas1.value=emp;
                Load_emp_details();
        }
}
function Load_emp_details()
{
       
        if(emp_flag==1)
        {
                var emp_id=document.getElementById("txtEmpID_mas").value;
              
        }
        else if(emp_flag==2)
        {
                var emp_id=document.getElementById("txtEmpID_mas1").value;
              
        }

             var url="";
           //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             url = "../../../../../Pre_AuditDetails?command=getEmpName&empid="+emp_id;
             //alert(url);
             var req=getTransport();
              req.open("GET",url,true);        
              req.onreadystatechange=function()
              {
            	  manipulate(req);
              }   
              req.send(null);
     
}

function LoadEmpDetails(baseResponse)
{
                 
                  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {                       
                         var emp_name=baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue;
                        // var desig_name=baseResponse.getElementsByTagName("desig_name")[0].firstChild.nodeValue;
                        // var office_name=baseResponse.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                         if(emp_flag==1)
                         {
                                
                                document.frmPre_AuditDetails.txtSanction_Estimate_PreparedBy.value=emp_name;
                         }
                         else if(emp_flag==2)
                         {
                                document.frmPre_AuditDetails.txtSanction_Estimate_ApprovedBy.value=emp_name;
                               
                         }
                }
                else if(flag=="nodata")
                {
                        alert("Invalid Employee Id");
                        document.frmPre_AuditDetails.txtEmpID_mas.value="";
                        document.frmPre_AuditDetails.txtSanction_Estimate_PreparedBy.value="";
                        document.frmPre_AuditDetails.txtEmpID_mas1.value="";
                        document.frmPre_AuditDetails.txtSanction_Estimate_ApprovedBy.value="";
                }
                else
                {
                        alert("Failed to load");
                }
}
function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
	          try{t.blur(); }catch(e){}
	          return true;
        
         }
         if (unicode!=8 && unicode !=9)
         {
	          if (unicode<48||unicode>57 ) 
	          {
	                return false 
	          }
         }
}
function txt_empty(txt) 
{
	var k=0;
	var s=txt.split('|');

	for(var i=0;i<s.length;i++)
	{
		//alert(document.getElementById(s[i]).value);
	if(document.getElementById(s[i]).value == "") 
	{ 
		
		var a=s[i].split('txt');
		if(a[1]=="EmpID_mas1")
		{
			alert("Audited By  Should Not Be Blank");
			document.getElementById(s[i]).focus();
			return false;
		}
		else if(a[1]=="EmpID_mas")
		{
			alert("Received By Should Not Be Blank");
			document.getElementById(s[i]).focus();
			return false;
		}
		else{
			alert(a[1]+"   Should Not Be Blank");
			document.getElementById(s[i]).focus();
			return false;
		}
	} 
	else if(document.getElementById(s[i]).value == "s")
	{

		var a=s[i].split('cmb');
		alert(a[1]+"   Should Not Be Blank");
		document.getElementById(s[i]).focus();
	   return false;
		
	}
	
	}
    return true;
}


function selectAll(Opt)
{

  var len=  document.getElementById("tblList").rows.length;

  if(len==1)
  {
          if (Opt =="ALL")
          {
        	 document.frmPre_AuditDetails.verify_select.checked=true;
          
          }
          else if (Opt=="UNSelect" )
          {
          document.frmPre_AuditDetails.verify_select.checked=false;
        
          }
  }
  else if(len>1)
  {
          for(var i=0;i<len;i++)
          {
                if ( Opt =="ALL")
                {
                    document.frmPre_AuditDetails.verify_select[i].checked=true;
                }
                else if(Opt=="UNSelect")
                {
                    document.frmPre_AuditDetails.verify_select[i].checked=false;
                }
          }
  }

}

function numbersonly(e,t)
{
         var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
         {
                try{t.blur(); }catch(e){}
                return true;
        
         }
         if (unicode!=8 && unicode !=9  )
         {
                if (unicode<48||unicode>57 ) 
                {
                     return false 
                }
         }
} 
