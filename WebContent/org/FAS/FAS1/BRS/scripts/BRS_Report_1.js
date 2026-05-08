function getTransport() {
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
			// alert("manipulate-command:--->>>"+command);

			if (command == "printFunc") {
				printFunc1(baseResponse);
			}
			else if (command == "checkPB") {
				                                  
                 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="success")
                        {
                        	  document.getElementById("pasdiv").style.display="block";
                        }
                        else
                        {    if(window.confirm("Do you want to Proceed Passbokkbalance Zero ?"))
							{
                        		document.getElementById("pasdiv").style.display="block";
							}
	                        else
	                        {
	                        	document.getElementById("pasdiv").style.display="none";
	                        }
                        }
               
			} 
		
		}
	}
}

function checkpassbookBal()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	if(cboCashBook_Year=="")
	{
		return false;	
	}
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	if(cboCashBook_Month=="")
	{
		return false;	
	}
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if(cmbBankAccNo=="s")
	{
		return false;	
	}
	else
	{
		var url="../../../../../BRSReport1?command=checkPB&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	
		var xmlrequest=getTransport();
		xmlrequest.open("GET",url,true); 
		xmlrequest.onreadystatechange=function()
		   {
			   manipulate(xmlrequest)
		   }   
		xmlrequest.send(null);	
	}
}


function freeze_brs_Report(path)
{

	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport1.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport1.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport1.cmbBankAccNo.focus();
	}else 
	{
	var url = path
			+ "/BRSReport1?command=Freezed_brs&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	//alert(url);
	   document.frmBRSReport1.action=url;
       document.frmBRSReport1.submit();

	
	}
}
function checkRecord(path)
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport1.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport1.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport1.cmbBankAccNo.focus();
	}
	else 
	{
	var url = path+ "/BRSReport1?command=chkRec&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	//alert("hhh:"+url);
	var xmlrequest=getTransport();
	xmlrequest.open("GET",url,true); 
	xmlrequest.onreadystatechange=function()
	   {
	    	 load_add(xmlrequest);
	   }   
	xmlrequest.send(null);
	}
}

//function freeze_brs(path)
//{
//	
//	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
//	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
//	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
//	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
//	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
//	
//	if (document.getElementById("txtCB_Year").value == "") {
//		alert("Enter Cash Book Year in the Field");
//		document.frmBRSReport1.cboCashBook_Year.focus();
//	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
//		alert("Select Cash Book Month in the Field");
//		document.frmBRSReport1.cboCashBook_Month.focus();
//	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
//		alert("Select Bank Account No in the Field");
//		document.frmBRSReport1.cmbBankAccNo.focus();
//	}
//	else 
//	{
//	var url = path
//			+ "/BRSReport1?command=f_brs&cmbAcc_UnitCode="
//			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
//			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
//			+ cboCashBook_Month + "&cmbBankAccNo="
//			+ cmbBankAccNo;
//	  document.frmBRSReport1.action=url;
//      document.frmBRSReport1.submit();
//	}
//}

function freeze_check(path)
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport1.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport1.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport1.cmbBankAccNo.focus();
	}
	else 
	{
	var url = path
			+ "/BRSReport1?command=f_brs_check&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	
	var xmlrequest=getTransport();
	xmlrequest.open("POST",url,true); 
	xmlrequest.onreadystatechange=function()
	   {
		f_brs_check(xmlrequest);
	   }; 
	xmlrequest.send(null);
	
	  
	}

}

function f_brs_check(xmlrequest)
{
	  if (xmlrequest.readyState == 4) {
                		//alert("xmlrequest.readyState==>"+xmlrequest.readyState);
                		if (xmlrequest.status == 200) {
                			//alert("xmlrequest.status==>"+xmlrequest.status);
                			//alert("response==>"+xmlrequest.responseText);
                			var baseResponse = xmlrequest.responseXML
                					.getElementsByTagName("response")[0];
                    var tagcommand=baseResponse.getElementsByTagName("command")[0];
                    var Command=tagcommand.firstChild.nodeValue;                                  
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                   //alert("flag==>"+flag);
                    
                    if(flag=="Already_Frozen")
                	{
                	alert("Already Part-1 Frozen");
                    document.getElementById("freezeBRS").disabled=true;
                    document.getElementById("onsubmit1").disabled=true;
                	}
                    else
                    	{
                    	document.getElementById("freezeBRS").disabled=false;
                        document.getElementById("onsubmit1").disabled=false;
                        
                    	}
                }
                
		 }
	  
}


function freeze_brs(path)
{
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport1.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport1.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport1.cmbBankAccNo.focus();
	}
	else 
	{
	var url = path
			+ "/BRSReport1?command=f_brs&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	
	var xmlrequest=getTransport();
	xmlrequest.open("POST",url,true); 
	xmlrequest.onreadystatechange=function()
	   {
		f_brs(xmlrequest);
	   } 
	xmlrequest.send(null);
	
	  
	}
}

function f_brs(xmlrequest)
{
	  if(xmlrequest.readyState==4)
		  //alert("xmlrequest.readyState==>"+xmlrequest.readyState);
		 {
                if(xmlrequest.status==200)
          		  //alert("xmlrequest.status==>"+xmlrequest.status);

                {
                //alert("response==>"+xmlrequest.responseText);
                    var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
                    var tagcommand=baseResponse.getElementsByTagName("command")[0];
                    var Command=tagcommand.firstChild.nodeValue;                                  
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                   //alert("flag==>"+flag);
                    
                    
                    
                    if(flag=="success")
                    {
                    	//
                       window.confirm("Report freeze done");
                       alert("Record Inserted into DataBase Successfully");
                 	   document.getElementById("freezeBRS").disabled=true;
                 	   document.getElementById("onsubmit1").disabled=false;
                    }
                    else if(flag=="failure")
                    	{
                    	alert("Records Not Inserted into Part-1");
                        document.getElementById("freezeBRS").disabled=true;
                        document.getElementById("onsubmit1").disabled=true;
                    	}
                    else if(flag=="Already_Frozen")
                	{
                	alert("Already Part-1 Frozen");
                    document.getElementById("freezeBRS").disabled=true;
                    document.getElementById("onsubmit1").disabled=true;
                	}
                    else
                    	{
                    	alert("Error Occur");
                    	}
                }
                
		 }
}

function load_add(xmlrequest)
{
	  if(xmlrequest.readyState==4)
		 {
                if(xmlrequest.status==200)
                {  
                       // var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
                        var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
                     //   alert("baseResponse:"+baseResponse);
                       var tagcommand=baseResponse.getElementsByTagName("command")[0];
                     //  alert(tagcommand);
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(Command=="free_brs")
                        {                                       
                             
                               if(flag=="success")
                               {
                            	   alert("Record Inserted into DataBase Successfully");
                            	   document.getElementById("freezeBRS").disabled=true;
                               }
                               else
                               {                                                   
                                      alert("Error in Insertion");
                                      document.getElementById("freezeBRS").disabled=false;
                               }
                       }
                        else if(Command=="chkRec")
                        {                                       
                            
                            if(flag=="success")
                            {
                         	  var proceed=baseResponse.getElementsByTagName("proceed")[0].firstChild.nodeValue;
                         	   if(proceed=="stop")
                         		   {
                         		   alert("Record Already Freezed For this Account No");
                         		  document.getElementById("freezeBRS").disabled=true;
                         		   }
                         	   else
                         		   {
                         		  document.getElementById("freezeBRS").disabled=false;
                         		   }
                         	  
                            }
                            else
                            {                                                   
                                   alert("Error in Onload");
                                 
                            }
                    }
              }
		 }    
}

function printFunc(path)
{
	//alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport1.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport1.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport1.cmbBankAccNo.focus();
	}else 
	{
	var url = path
			+ "/BRSReport1?command=printFunc&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	//alert(url);
	   document.frmBRSReport1.action=url;
       document.frmBRSReport1.submit();

	
	}
}
var bank_ac_no =new Array();
var acc_desc   =new Array();
var bank_name  = new Array();
var branch_name = new Array();
var bank_id  = new Array();
var branch_id = new Array();
var opr_mode  = new Array();
function printFunc1()
{
	
}

function refresh()
{
	 var today= new Date(); 
     var day=today.getDate();
     var month=today.getMonth();
     month=month+1;
     var year=today.getYear();
     if(year < 1900) year += 1900;
     
	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.frmBRSReport1.txtCB_Year.value = year;
	document.frmBRSReport1.txtCB_Month.value = month;
	document.frmBRSReport1.cmbBankAccNo.value = "";	
	document.getElementById("freezeBRS").disabled=false;
	document.getElementById("onsubmit1").disabled=false;
}

function LoadBankAccountNumber_col()
{  	
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   var url="../../../../../BRSReport1?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
	//   alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 LoadBankAccountNumberRes_new(req);
	   }   
       req.send(null);
	  
}

function LoadBankAccountNumberRes_new(req)
{
 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
           
            if(Command=="LoadBankAccountNumber")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	          
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	        //   alert(acc_no.length);
            	            for(var k=0;k<acc_no.length;k++)
            	            {
            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
            	            }
            	         
            	            cmbBankAccNo.innerHTML="";
            	            var option=document.createElement("OPTION");
            	            option.text="--Select Bank Acc. Number--";
            	            option.value="";
            	            try
            	            {
            	            	cmbBankAccNo.add(option);
            	            }catch(errorObject)
            	            {
            	            	cmbBankAccNo.add(option,null);
            	            }
            	            
            	            for(var k=0;k<acc_no.length;k++)
            	            {   
            	                  var option=document.createElement("OPTION");
            	                  option.text=acc_desc[k];
            	                  option.value=bank_ac_no[k];
            	                  try
            	                  {
            	                	  cmbBankAccNo.add(option);
            	                  }
            	                  catch(errorObject)
            	                  {
            	                	  cmbBankAccNo.add(option,null);
            	                  }
            	            }
            	    }
              }
              else
              {
            	    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
             	    if(flag=="success")
             	    {
             	    		var acc_head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
             	    		document.getElementById("txtOprCode").value=acc_head_code;
             	    }
              }
        
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

function exitfun(path) {
	window.close();
}
