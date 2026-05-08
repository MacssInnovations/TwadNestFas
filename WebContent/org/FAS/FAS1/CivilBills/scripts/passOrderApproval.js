var response="";

function doFunction(Command,param)
{
	
	if(param.length<4)
	{
		
		alert("Wrong office id");
		return;
	}
	
	if(Command=="office")
	  {
		
	            var oid=param;
	            
	            var url="../../../../../Create_Transfer_OrderServ?Command=office&oid="+oid;
	            //alert(url);
	            var req=getTransport();
	            req.open("GET",url,true); 
	            req.onreadystatechange=function()
	            {
	            	if(req.readyState==4)
	                {
	                  if(req.status==200)
	                  {
	            	
	            	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            	  
	            	  if(flag=="success")
	            	  {
	            		  document.getElementById("dofnm").value= baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
	            	  return true;
	            	  }
	            	  else
	            	  {
	            	   //  alert("Office Id '"+oid+"' doesn't Exists");
	            	     
	            	                document.getElementById("dofid").value="";
	            	                document.getElementById("dofnm").value="";
	            	                return false;

	            	  }
	            }
	  }

	            } ;
	                   if(window.XMLHttpRequest)
	                        req.send(null);
	                else req.send();
	                    
	   }
}
function loadbillDetails()
{
	var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
	 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	 var txtCB_Month=document.getElementById('txtCB_Month').value;
	 var txtCB_Year = document.getElementById("txtCB_Year").value;
	 
	url="../../../../../PassOrderApproval?command=loadbillDetails&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
	//alert(url);
   var req=getTransport();
   req.open("POST",url,true);   
   req.onreadystatechange=function(){
	   com_load_MTC(req);
    };   
    req.send(null);
    }

function loadbillMTC(val)
{
	var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
	 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	 var txtCB_Month=document.getElementById('txtCB_Month').value;
	 var txtCB_Year = document.getElementById("txtCB_Year").value;
	 
	url="../../../../../PassOrderApproval?command=loadbillMTC&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&BillNo="+val;
	//alert(url);
   var req=getTransport();
   req.open("POST",url,true);   
   req.onreadystatechange=function(){
	   com_load_MTC(req);
    };   
    req.send(null);
    }



function checkpassdt()
{
	var passOrderDate=document.getElementById("passOrderDate").value;
	var passOrderDate_one=passOrderDate.split("/");
	
	var approvalDate=document.getElementById("approvalDate").value;
	 var approvalDate_two=approvalDate.split("/");
	
	 if(passOrderDate_one[2]>approvalDate_two[2])
	 {
		document.getElementById("approvalDate").value="";
		alert("Approval Date Should not be less than PassOrder Date");
		return false;
		
	 }
	 else if(passOrderDate_one[2]==approvalDate_two[2])
	 {
		
   	 if(passOrderDate_one[1]>approvalDate_two[1])
   	 {
   		document.getElementById("approvalDate").value="";
		alert("Approval Date Should not be less than PassOrder Date");
		return false;
   	 }
   	 else if(passOrderDate_one[1]==approvalDate_two[1])
   	 {
   		 var apl;
   		 if(approvalDate_two[0].length==2)
   		 {
   			apl=approvalDate_two[0];
   		 }
   		 else
   		 {
   			apl="0"+approvalDate_two[0];
   		 }
   		 if(passOrderDate_one[0]>apl)
       	 {
	   		document.getElementById("approvalDate").value="";
			alert("Approval Date Should not be less than PassOrder Date");
			return false;
       	 } 
   	 }
	 }
	
}

function checkpassdtRe()
{
	var passOrderDate=document.getElementById("passOrderDate").value;
	var rejectedDate=document.getElementById("rejectedDate").value;
	if(passOrderDate>rejectedDate)
	{
		document.getElementById("passOrderDate").value="";
		document.getElementById("rejectedDate").value="";
		alert("Rejected Date Should not be less than PassOrder Date");
		return false;
	}
}

var check_memo_list;
function viewDetails(path)
{
	//alert(path);
	var unitcode = document.getElementById("cmbAcc_UnitCode").value;
    var offid = document.getElementById("cmbOffice_code").value;
   var passOrderDate=document.getElementById("passOrderDate").value;
    var billNo = document.getElementById("passOrderNo").value;
    if(billNo=="")
    {
    alert("Please Choose Passorder No");
    return false;
    }
    else{
	check_memo_list= window.open("../../../../../org/FAS/FAS1/CivilBills/jsps/Pass_order_list.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&billNo="+passOrderDate+"&passNo="+billNo.split("/")[0],"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes");
 	     //+"&billMajorType="+billMajorType+"&billNo="+billNo+"&billSubType="+billSubType,
	check_memo_list.moveTo(250,250);  
	check_memo_list.focus();
    }
}

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP_TRN.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}

function doParentJob(jobid,deptid)
{

    document.getElementById('dofid').value=jobid;
    doFunction('office',jobid);
    
}

function AjaxFunction()
    {
        var xmlrequest=false;
        try
            {
               xmlrequest=new ActiveXObject("Msxml2.XMLHTTP"); 
            }
         catch(e1)
          {
                 try
                 {
                     xmlrequest=new ActiveXObject("Microsoft.XMLHTTP"); 
                 }
                 catch(e2)
                 {     
                     xmlrequest=false;
                 }
          }
          if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
                {
                     xmlrequest=new XMLHttpRequest();
                }
        return xmlrequest;
    } 

var winemp;
var browserName=navigator.appName;
var brow="";
if (browserName=="Netscape")
{ 	brow="nets";}
else if (browserName=="Microsoft Internet Explorer")
{ 	brow="iex";}


function callmeth1(){
	
		//alert("loadpassno");
		var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
		 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		url="../../../../../PassOrderApproval?command=loadpassno&cmbOffice_code="+cmbOffice_code+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
		//alert(url);
        var req=getTransport();
        req.open("POST",url,true);   
        req.onreadystatechange=function(){
        	getReqId1(req);
         };   
         req.send(null);
	
}

function callServer(command){
	var url="";
	if(command=='getbillmajor'){
		url="../../../../../PassOrderApproval?command=get";            	
        //alert(url);
		var req=getTransport();
        req.open("POST",url,true);   
        req.onreadystatechange=function(){
               processResponse(req);
         };   
         req.send(null);
	}
	
	if(command=='passorderdetails'){
	//	var billMajor=document.getElementById('billMajorType').value;
		//var billNo=document.getElementById('billNo').value;
		var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
		var passOrderNo=document.getElementById('passOrderNo').value;
		var passpli=passOrderNo.split("/");
		//alert("pass order details ::::"+passpli);
		url="../../../../../PassOrderApproval?command=passOrderDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&pasSplit="+passpli[0]+"&pass_amt="+passpli[1]+"&Bill_no="+passpli[2];            	
		//alert("passorderdetails"+url);
		var req=getTransport();
        req.open("POST",url,true);   
        req.onreadystatechange=function(){        	
        	processApproval(req);
         };   
         req.send(null);
	   
}if(command=='savechangeOff'){
	var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
	 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	 var txtCB_Month=document.getElementById('txtCB_Month').value;
	 var txtCB_Year = document.getElementById("txtCB_Year").value;
	 var hid_rad = document.getElementById("hid_rad").value;
	/*var SanctionPro= document.getElementById('SanctionPro').value;
	var totalSanctionAmt=	document.getElementById('totalSanctionAmt').value;
	var totalDeductionAmt=	document.getElementById('totalDeductionAmt').value;

	var txtMajor=	document.getElementById('txtMajor').value;
	var txtMinor=	document.getElementById('txtMinor').value;
	var txtSub=	document.getElementById('txtSub').value;
	var passOrderDate=	document.getElementById('passOrderDate').value;
	var passOrderPrepared=	document.getElementById('passOrderPrepared').value;
	var netamt=	document.getElementById('netamt').value;
	*/
	var BillNo=	document.getElementById('BillNo').value;
	
	url="../../../../../PassOrderApproval?command=savechangeOff&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&BillNo="+BillNo;  	
	if(hid_rad=="Y")
	{
		url+="&chan_Unit="+document.getElementById('cmbAcc_UnitCode1').value+"&chan_Office="+document.getElementById("cmbOffice_code1").value;
	}
	else{
		url+="&chan_Unit="+document.getElementById('cmbAcc_UnitCode').value+"&chan_Office="+document.getElementById("cmbOffice_code").value;
	}
	alert("Officve Change"+url);
	var req=getTransport();
    req.open("POST",url,true);   
    req.onreadystatechange=function(){        	
    	com_load_MTC(req);
     };   
     req.send(null);
}
	if(command=='update'){
		
		var txtEmpId=document.getElementById("txtEmpId").value;
		if(txtEmpId==0)
		{
			alert("Please Enter DrawingOfficerId in Masters");
			document.getElementById("txtEmpId").value="";
			return false;
		}
		else if(txtEmpId=="")
		{
			alert("Please Enter DrawingOfficerId in Masters");
			document.getElementById("txtEmpId").value="";
			return false;
		}
		
		var passOrderDate=document.getElementById("passOrderDate").value;
		var passOrderDate_one=passOrderDate.split("/");
		
		var approvalDate=document.getElementById("approvalDate").value;
		 var approvalDate_two=approvalDate.split("/");
		
		 if(passOrderDate_one[2]>approvalDate_two[2])
		 {
			document.getElementById("approvalDate").value="";
			alert("Approval Date Should not be less than PassOrder Date");
			return false;
			
		 }
		 else if(passOrderDate_one[2]==approvalDate_two[2])
		 {
			
	   	 if(passOrderDate_one[1]>approvalDate_two[1])
	   	 {
	   		document.getElementById("approvalDate").value="";
			alert("Approval Date Should not be less than PassOrder Date");
			return false;
	   	 }
	   	 else if(passOrderDate_one[1]==approvalDate_two[1])
	   	 {
	   		 var apl;
	   		 if(approvalDate_two[0].length==2)
	   		 {
	   			apl=approvalDate_two[0];
	   		 }
	   		 else
	   		 {
	   			apl="0"+approvalDate_two[0];
	   		 }
	   		 if(passOrderDate_one[0]>apl)
	       	 {
		   		document.getElementById("approvalDate").value="";
				alert("Approval Date Should not be less than PassOrder Date");
				return false;
	       	 } 
	   	 }
		 }
			
	
		var drawOff=document.getElementById('txtEmpId').value;	
		var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
		 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		 var passOrderNo = document.getElementById("passOrderNo").value;
		 var spl=passOrderNo.split("/");
		
		var isApprove="";
		for(var i=0; i<document.paaApproval.approveReject.length; i++){
			if(document.paaApproval.approveReject[i].checked){
				isApprove=document.paaApproval.approveReject[i].value;
			}
		}
		var approveDate="";
		if(isApprove=="Y"){
			approveDate=document.getElementById('approvalDate').value;
		}else{
			approveDate=document.getElementById('rejectedDate').value;
		}
		var rejectReason=document.getElementById('rejectReason').value;
		var year=spl[1];
		 var month=spl[2];
		url="../../../../../PassOrderApproval?command=update&drawOff="+drawOff+"&isApprove="+isApprove+"&approveDate="+approveDate+
			"&rejectReason="+rejectReason+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+
			cmbOffice_code+"&sancno=0&year="+year+"&month="+month+"&passno="+spl[0]+"&passOrderDate="+passOrderDate;            	
		alert(url);
		var req=getTransport();
        req.open("POST",url,true);   
        req.onreadystatechange=function(){        	
        	processApproval(req);
         };   
         req.send(null);
	}
	if(command=='delete'){
		var drawOff=document.getElementById('txtEmpId').value;	
		var cmbAcc_UnitCode=document.getElementById('cmbAcc_UnitCode').value;
		 var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		 var passOrderNo = document.getElementById("passOrderNo").value;
		 var spl=passOrderNo.split("/");
		
		var isApprove="";
		for(var i=0; i<document.paaApproval.approveReject.length; i++){
			if(document.paaApproval.approveReject[i].checked){
				isApprove=document.paaApproval.approveReject[i].value;
			}
		}
		var approveDate="";
		if(isApprove=="Y"){
			approveDate=document.getElementById('approvalDate').value;
		}else{
			approveDate=document.getElementById('rejectedDate').value;
		}
		var rejectReason=document.getElementById('rejectReason').value;
		url="../../../../../PassOrderApproval?command=delete&drawOff="+drawOff+"&isApprove="+isApprove+"&approveDate="+approveDate+
			"&rejectReason="+rejectReason+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&sancno="+spl[1];               	
       
		var req=getTransport();
        req.open("POST",url,true);   
        req.onreadystatechange=function(){        	
        	processApproval(req);
         };   
         req.send(null);
	}
}

function processResponse(req){
	if(req.readyState==4) { 
        if(req.status==200) {  
       	 	response=req.responseXML.getElementsByTagName("response")[0];        	 
       	 	updateResponse(response);
        }
    }	
}
function updateResponse(){
	var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(res=="success"){
		var len=response.getElementsByTagName("BILL_CODE").length;
		var selectdiv=document.getElementById('billMajorType');
		var listOpt=document.createElement("option");
		selectdiv.length=0;
		selectdiv.appendChild(listOpt);
		listOpt.text="select";
		listOpt.value="select";								
		for(var i=0; i<len; i++){
			if(response.getElementsByTagName("STATUSTYPE")[i].firstChild.nodeValue=="L"){
				listOpt=document.createElement("option");
				selectdiv.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("BILL_DESC")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("BILL_CODE")[i].firstChild.nodeValue;
			}			
		}
	}else{
 		alert("Process failure");
 	}
}
function com_load_MTC(req)
{

	//alert("return ");
	if (req.readyState == 4) {
		if (req.status == 200) {
			response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;	
		
				if(command=="loadbillDetailsMTC"){
					var selectdiv=document.getElementById('BillNo');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Bill Number is Not Found ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag == "success"){
						var len=response.getElementsByTagName("BILL_NO").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							
							var val=response.getElementsByTagName("BILL_NO")[i].firstChild.nodeValue;
							listOpt.text=response.getElementsByTagName("BILL_NO")[i].firstChild.nodeValue;;
							listOpt.value=val;
							selectdiv.appendChild(listOpt);
						}
					}
				} else if (command == "savechangeOff") {
				if (status == "Failure") {
					alert('RECORD NOT SAVED SUCCESSFULLY ... ');
					refresh_new();
				} else {
					alert('RECORD SAVED SUCCESSFULLY ... ');
					refresh_new();
				}
				}else if(command=="loadbillMTC"){
					document.getElementById('SanctionPro').value=response.getElementsByTagName("Sanction_Proc_No")[0].firstChild.nodeValue;
					document.getElementById('totalSanctionAmt').value=response.getElementsByTagName("TOTAL_SANCTIONED_AMOUNT")[0].firstChild.nodeValue;
					document.getElementById('totalDeductionAmt').value=response.getElementsByTagName("DEDUCTED_AMOUNT")[0].firstChild.nodeValue;
					document.getElementById('txtMajor1').value=(response.getElementsByTagName("BILL_MAJOR_TYPE")[0].firstChild.nodeValue).split('/')[1];
					document.getElementById('txtMinor1').value=(response.getElementsByTagName("Bill_Minor_Type_Code")[0].firstChild.nodeValue).split('/')[1];
					document.getElementById('txtSub1').value=(response.getElementsByTagName("Bill_Sub_Type_Code")[0].firstChild.nodeValue).split('/')[1];
					document.getElementById('txtMajor').value=(response.getElementsByTagName("BILL_MAJOR_TYPE")[0].firstChild.nodeValue).split('/')[0];
					document.getElementById('txtMinor').value=(response.getElementsByTagName("Bill_Minor_Type_Code")[0].firstChild.nodeValue).split('/')[0];
					document.getElementById('txtSub').value=(response.getElementsByTagName("Bill_Sub_Type_Code")[0].firstChild.nodeValue).split('/')[0];
					document.getElementById('passOrderDate').value=response.getElementsByTagName("Pass_Order_Date")[0].firstChild.nodeValue;
					document.getElementById('passOrderPrepared').value=response.getElementsByTagName("Pass_Order_By")[0].firstChild.nodeValue.split('/')[0];
					document.getElementById('passOrderPrepared1').value=response.getElementsByTagName("Pass_Order_By")[0].firstChild.nodeValue.split('/')[1];
					document.getElementById('passOrderAmt').value=response.getElementsByTagName("PASS_ORDER_AMOUNT")[0].firstChild.nodeValue;
					document.getElementById('netamt').value=response.getElementsByTagName("NET_AMOUNT")[0].firstChild.nodeValue;
					
				}			
			}
		}
}
function refresh_new(){
	document.getElementById('BillNo').value="select";
	document.getElementById('SanctionPro').value="";
	document.getElementById('totalSanctionAmt').value="";
	document.getElementById('totalDeductionAmt').value="";
	document.getElementById('txtMajor1').value="";
	document.getElementById('txtMinor1').value="";
	document.getElementById('txtSub1').value="";
	document.getElementById('txtMajor').value="";
	document.getElementById('txtMinor').value="";
	document.getElementById('txtSub').value="";
	document.getElementById('passOrderDate').value="";
	document.getElementById('passOrderPrepared').value="";
	document.getElementById('passOrderPrepared1').value="";
	document.getElementById('passOrderAmt').value="";
	document.getElementById('netamt').value="";
}
function getReqId1(req){
	//alert("return ");
	if (req.readyState == 4) {
		if (req.status == 200) {
			response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;			
				if(command=="passorder"){
					var selectdiv=document.getElementById('passOrderNo');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Pass order Number is Not Found for this Bill Type ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag == "success"){
						var len=response.getElementsByTagName("PASS_ORDER_NO").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							var val=response.getElementsByTagName("PASS_ORDER_NO")[i].firstChild.nodeValue+"/"+response.getElementsByTagName("CASHBOOK_YEAR")[i].firstChild.nodeValue+"/"
							+response.getElementsByTagName("CASHBOOK_MONTH")[i].firstChild.nodeValue+"/Rs."+response.getElementsByTagName("BILL_AMOUNT")[i].firstChild.nodeValue;
							listOpt.text=response.getElementsByTagName("PASS_ORDER_NO")[i].firstChild.nodeValue+"/"+response.getElementsByTagName("CASHBOOK_YEAR")[i].firstChild.nodeValue+"/"
							+response.getElementsByTagName("CASHBOOK_MONTH")[i].firstChild.nodeValue+"/Rs."+response.getElementsByTagName("BILL_AMOUNT")[i].firstChild.nodeValue;
							listOpt.value=val;
						}
					}
				}			
			}
		}
}
/*function getReqId(req){
	if (req.readyState == 4) {
		if (req.status == 200) {
			response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;			
				if(command=="account"){
					var selectdiv=document.getElementById('billNo');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Bill Number is Not Found for this Bill Type ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag == "success"){
						var len=response.getElementsByTagName("BILL_NO").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							listOpt.text=response.getElementsByTagName("BILL_NO")[i].firstChild.nodeValue;
							listOpt.value=response.getElementsByTagName("BILL_NO")[i].firstChild.nodeValue;
						}
					}
				}			
			}
		}
}*/
function processApproval(req){
	if(req.readyState==4) { 
        if(req.status==200) {  
       	 	response=req.responseXML.getElementsByTagName("response")[0];        	 
       	 	viewApproval();
        }
    }	
}

function viewApproval(){
	
	var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	
		if(command=="passorderdetail"){
			if(flag=="success"){
			document.getElementById('bill_num').value=response.getElementsByTagName("BILL_NO")[0].firstChild.nodeValue;
			document.getElementById('totalSanctionAmt').value=response.getElementsByTagName("TOTAL_SANCTIONED_AMOUNT")[0].firstChild.nodeValue;
			document.getElementById('totalDeductionAmt').value=response.getElementsByTagName("DEDUCTED_AMOUNT")[0].firstChild.nodeValue;
			document.getElementById('netAmt').value=response.getElementsByTagName("NET_AMOUNT")[0].firstChild.nodeValue;
			
			document.getElementById('passOrderDate').value=response.getElementsByTagName("PASSDATE")[0].firstChild.nodeValue;
			document.getElementById('passOrderPrepared').value=response.getElementsByTagName("PASSNO")[0].firstChild.nodeValue;
			document.getElementById('passOrderAmt').value=response.getElementsByTagName("PASS_ORDER_AMOUNT")[0].firstChild.nodeValue;			
			if(response.getElementsByTagName("BILL_APPROVED")[0].firstChild.nodeValue=="Y"){
				document.paaApproval.approveReject[0].checked=true;
				var apdat=response.getElementsByTagName("APPROVE_DATE")[0].firstChild.nodeValue;
				if(!apdat=='null'){
					
					document.getElementById('approvalDate').value=apdat;
				}
				
				
				isApprove();
			}
			if(response.getElementsByTagName("BILL_APPROVED")[0].firstChild.nodeValue=="N"){
				
				document.paaApproval.approveReject[1].checked=true;
				var redat=response.getElementsByTagName("APPROVE_DATE")[0].firstChild.nodeValue;
					var resre=response.getElementsByTagName("REJECT_REASON")[0].firstChild.nodeValue;
				
					if(!redat=='null'){
						document.getElementById('rejectedDate').value=redat;	
					}
					if((resre=='null')||(resre=="null")||(resre=="")){
				
					}else{
						 document.getElementById('rejectReason').value=resre;
				
					}
				
				isApprove();
			}
			else{
				document.paaApproval.approveReject[0].checked=true;
				var apdat=response.getElementsByTagName("APPROVE_DATE")[0].firstChild.nodeValue;
				if(!apdat=='null'){
					
					document.getElementById('approvalDate').value=apdat;
				}
				
				
				isApprove();
			}
			
			document.getElementById('txtEmpId').value=response.getElementsByTagName("DRAWING_OFFICER")[0].firstChild.nodeValue;
			Load_emp_details();
			document.getElementById('ondelete').disabled=false;
			
		
		}else{
			alert("no data");
		}
		
		}		
		
		
	//}else{
		//alert("no data");
	//}
	
	if(command=="update"){
		if(flag=="success"){
			alert("PassOrder Is Approved successfully");
			clearAll();
			callmeth1();
		}else{
			alert("Error in updation");
		}
	}
	if(command=="delete"){
		if(flag=="success"){
			
			alert("PassOrder Is Rejected successfully");
			clearAll();
		}else{
			alert("Error in deletion");
		}
	}
}


function Load_emp_details(){
      
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;  
        
             var url="";
           //  url="../../../../../phone_master_servlet?command=loadempdetails&emp_id="+emp_id;
             url="../../../../../Bills_Token_Register_with_SP?command=getempname&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
          //  alert("inside loadempdetails"+url);
             var req=getTransport();
              req.open("post",url,true);        
              req.onreadystatechange=function()
              {
                       aprocessResponse(req);
              };   
              req.send(null);        
        
}
function aprocessResponse(req){   
      if(req.readyState==4){
          if(req.status==200){   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              if(command=="getempname"){
                    LoadEmpDetails(baseResponse);
              }
        }    
    }
}
function LoadEmpDetails(baseResponse){
	document.getElementById("txtemp_name").value="";
       var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
       if(flag=="success"){                       
               var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;               
               var empname=baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;               
               var officename=baseResponse.getElementsByTagName("officename")[0].firstChild.nodeValue;               
               //document.getElementById("txtemp_desig").value=desig_name;
               //document.getElementById("txtemp_office").value=office_name;
               document.getElementById("txtEmpId").value=empid;
               document.getElementById("txtemp_name").value=empname+","+officename;
        }else if(flag=="nodata"){
             alert("Invalid Employee Id");
         }else{
             alert("Failed to load");
         }
}

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
        winemp=null;
    }
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
}
function doParentEmp(emp){
document.getElementById('txtEmpId').value=emp;
Load_emp_details();
}

function isApprove(){
	if(document.paaApproval.approveReject[0].checked==true){
		document.getElementById('rejectedDate').value="";
		document.getElementById('rejectReason').value="";
		document.getElementById('rejectedDate').disabled="disabled";
		document.getElementById('rejectReason').disabled="disabled";
		document.getElementById('approvalDate').disabled=false;
		document.getElementById('appImg').style.display='inline';
		document.getElementById('regImg').style.display='none';
	}else{
		document.getElementById('approvalDate').value="";
		document.getElementById('approvalDate').disabled="disabled";
		document.getElementById('rejectedDate').disabled=false;
		document.getElementById('rejectReason').disabled=false;
		document.getElementById('regImg').style.display='inline';
		document.getElementById('appImg').style.display='none';
	}
}
function clearAll(){
	//document.getElementById('billMajorType').selectedIndex=0;
	//document.getElementById('billMajorType').value="";
	
	document.getElementById('passOrderNo').selectedIndex=0;
	//document.getElementById('passOrderNo').length=1;
	//document.getElementById('billNo').selectedIndex=0;
	//document.getElementById('billNo').length=1;
	//document.getElementById('billMinorType').value="";
	//document.getElementById('billSubType').value="";
	//document.getElementById('billDate').value="";
	//document.paaApproval.micEntry[0].checked=0;
	//document.paaApproval.micEntry.value="";
	//document.getElementById('micEntryDate').value="";
	document.getElementById('totalSanctionAmt').value="";
	document.getElementById('totalDeductionAmt').value="";
	document.getElementById('netAmt').value="";
	//document.getElementById('billParticular').value="";
	document.getElementById('passOrderDate').value="";
	document.getElementById('passOrderPrepared').value="";
	document.getElementById('passOrderAmt').value="";
	document.getElementById('txtEmpId').value="";
	document.getElementById('txtemp_name').value="";
	document.paaApproval.approveReject[0].checked=true;
	document.getElementById('approvalDate').value="";
	document.getElementById('rejectedDate').value="";
	document.getElementById('rejectReason').value="";
	document.getElementById('approvalDate').disabled=false;
	document.getElementById('rejectedDate').disabled="disabled";
	document.getElementById('rejectReason').disabled="disabled";
	document.getElementById('appImg').style.display='inline';
	document.getElementById('regImg').style.display='none';
}