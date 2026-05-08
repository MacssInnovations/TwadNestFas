// BRSReport_Part2B

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
			// alert("manipulate-command:--->>>"+command);

			if (command == "printFunc") {
				// alert("manipulate saveFunc");
				printFunc1(baseResponse);
			}
			else if (command == "checkPB") {
                
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                       if(flag=="success")
                       {
                       	  document.getElementById("pasdiv").style.display="block";
                       }
                       else
                       {                                                   
                              alert("Please Enter PassBookBalance");
                              document.getElementById("pasdiv").style.display="none";
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
	var url = path+ "/BRSReport_Part2B?command=chkRec&cmbAcc_UnitCode="
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
	var url = path	+ "/BRSReport_Part2B?command=f_brs&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	 document.frmBRSReport_Part2B.action=url;
     document.frmBRSReport_Part2B.submit();
	}
}

function freeze_brs_report(path)
{
	alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport_Part2B.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport_Part2B.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport_Part2B.cmbBankAccNo.focus();
	}else 
	{
	var url = path
			+ "/BRSReport_Part2B?command=brs_Freeze&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	//alert(url);
	   document.frmBRSReport_Part2B.action=url;
       document.frmBRSReport_Part2B.submit();

	
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
		document.frmBRSReport_Part2B.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport_Part2B.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport_Part2B.cmbBankAccNo.focus();
	}else 
	{
	var url = path
			+ "/ClearanceReport?command=printFunc&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	//alert(url);
	   document.frmBRSReport_Part2B.action=url;
       document.frmBRSReport_Part2B.submit();

	
	}
}

function printFunc1()
{
	//alert("Entered");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport_Part2B.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport_Part2B.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport_Part2B.cmbBankAccNo.focus();
	}else 
	{
	var url = "../../../../../ClearanceReport?command=printFunc1&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
//	alert(url);
	   document.BRS_download.action=url;
       document.BRS_download.submit();

	
	}
	
}
function printFunc2()
{
	//alert("Entered");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboCashBook_Year = document.getElementById("txtCB_Year").value;
	var cboCashBook_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	if (document.getElementById("txtCB_Year").value == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSReport_Part2B.cboCashBook_Year.focus();
	} else if (cboCashBook_Month == "" || cboCashBook_Month == "s") {
		alert("Select Cash Book Month in the Field");
		document.frmBRSReport_Part2B.cboCashBook_Month.focus();
	} else if (cmbBankAccNo == "" || cmbBankAccNo == "s") {
		alert("Select Bank Account No in the Field");
		document.frmBRSReport_Part2B.cmbBankAccNo.focus();
	}else 
	{
	var url = "../../../../../ClearanceReport?command=printFunc2&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			+ "&cboCashBook_Year=" + cboCashBook_Year + "&cboCashBook_Month="
			+ cboCashBook_Month + "&cmbBankAccNo="
			+ cmbBankAccNo;
	//alert(url);
	   document.BRS_download.action=url;
       document.BRS_download.submit();

	
	}
	
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
	document.frmBRSReport_Part2B.txtCB_Year.value = year;
	document.frmBRSReport_Part2B.txtCB_Month.value = month;
	document.frmBRSReport_Part2B.cmbBankAccNo.innerHTML = "";	
}
function refresh1()
{
	 var today= new Date(); 
     var day=today.getDate();
     var month=today.getMonth();
     month=month+1;
     var year=today.getYear();
     if(year < 1900) year += 1900;
     
	LoadAccountingUnitID('LIST_ALL_UNITS');
	document.BRS_download.txtCB_Year.value = year;
	document.BRS_download.txtCB_Month.value = month;
	document.BRS_download.cmbBankAccNo.innerHTML= "";	
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

function LoadBankAccountNumber_opr1()
{  	
	
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   var url="../../../../../ClearanceReport?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
	// alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 LoadBankAccountNumberRes(req);
	   };
       req.send(null);
	 
}
/*function LoadBankAccountNo()
{  	
	
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	   var cashbook_yr=document.getElementById("txtCB_Year").value;	 
	 	var cashbook_mn=document.getElementById("txtCB_Month").value;	
	   var url="../../../../../ClearanceReport?command=LoadBankAccountNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cashbook_yr="+cashbook_yr+"&cashbook_mn="+cashbook_mn;
	  
		 	 
		
	// alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 LoadBankAccountNumberRes(req);
	   };
       req.send(null);
	 
}
function LoadBankAccountNo1()
{  	
	
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	   var cashbook_yr=document.getElementById("txtCB_Year").value;	 
	 	var cashbook_mn=document.getElementById("txtCB_Month").value;	
	 	var cmbAcc_officecode=document.getElementById("cmbOffice_code").value;
	 	
	   var url="../../../../../ClearanceReport?command=LoadBankAccountNo1&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbAcc_officecode="+cmbAcc_officecode+"&cashbook_yr="+cashbook_yr+"&cashbook_mn="+cashbook_mn;
	  
		 	 
		
	// alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 LoadBankAccountNumberRes(req);
	   };
       req.send(null);
	 
}
*/


function LoadBankAccountNumberRes(req)
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
            	          // alert(acc_no);
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	          
            	           var option=document.createElement("OPTION");
           	            option.text="--Select--";
           	            option.value="";
           	            cmbBankAccNo.appendChild(option);
            	    //alert(cmbBankAccNo);
            	            /*for(var k=0;k<acc_no.length;k++)
            	            {
            	            	bank_ac_no[k]=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	acc_desc[k]=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name[k]=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name[k]=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id[k] =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id[k] =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode[k] =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
            	            }*/
           	         var bank_ac_no=baseResponse.getElementsByTagName("acc_no");
          // alert(bank_ac_no.length);
            	            cmbBankAccNo.innerHTML="";
            	            
            	            for(var k=0;k<bank_ac_no.length;k++)
            	            {   
            	            	
            	            	bank_ac_no=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	
            	            	acc_desc=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
                                     //alert(bank_ac_no);
            	            	var option=document.createElement("OPTION");
            	            option.text=opr_mode+" - "+bank_ac_no.split("/")[1]+" - "+bank_name;
            	            option.value=bank_ac_no;
            	            cmbBankAccNo.appendChild(option);
            	            }
            	            
            	           /* for(var k=0;k<acc_no.length;k++)
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
            	            }*/
            	    }
            	 if(flag=="NoData")
          	    {
             		 document.getElementById("cmbBankAccNo").innerHTML="";
             		alert("No Data Found"); 
             		
          	    }
              }
            
            else  if(Command=="LoadBankAccountNo")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	          
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	          // alert(acc_no);
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo1");
            	          
            	           var option=document.createElement("OPTION");
           	            option.text="--Select--";
           	            option.value="";
           	            cmbBankAccNo.appendChild(option);
            	    
           	         var bank_ac_no=baseResponse.getElementsByTagName("acc_no");
        
            	            cmbBankAccNo.innerHTML="";
            	            
            	            for(var k=0;k<bank_ac_no.length;k++)
            	            {   
            	            	
            	            	bank_ac_no=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	//alert(bank_ac_no);
            	            	acc_desc=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
      
            	            	var option=document.createElement("OPTION");
            	            option.text=opr_mode+" - "+bank_ac_no.split("-")[1]+" - "+bank_name;
            	            option.value=bank_ac_no;
            	            cmbBankAccNo.appendChild(option);
            	            }
            	            
            	           
            	    }
            	 if(flag=="NoData")
         	    {
            		 document.getElementById("cmbBankAccNo1").innerHTML="";
            		alert("No Account Number in this Account Unit id"); 
            		
         	    }
            	 
              }
            else  if(Command=="LoadBankAccountNo1")
            {
            	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 
            	 if(flag=="success")
            	    {
            	          
            	           
            	           /** Bank Account Number Object to find length */ 
            	           var acc_no=baseResponse.getElementsByTagName("acc_no");
            	          // alert(acc_no);
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo1");
            	          
            	           var option=document.createElement("OPTION");
           	            option.text="--Select--";
           	            option.value="";
           	            cmbBankAccNo.appendChild(option);
            	    
           	         var bank_ac_no=baseResponse.getElementsByTagName("acc_no");
        
            	            cmbBankAccNo.innerHTML="";
            	            
            	            for(var k=0;k<bank_ac_no.length;k++)
            	            {   
            	            	
            	            	bank_ac_no=baseResponse.getElementsByTagName("acc_no")[k].firstChild.nodeValue;
            	            	//alert(bank_ac_no);
            	            	/*acc_desc=baseResponse.getElementsByTagName("acc_desc")[k].firstChild.nodeValue;
            	            	bank_name=baseResponse.getElementsByTagName("bank_name")[k].firstChild.nodeValue;
            	            	branch_name=baseResponse.getElementsByTagName("branch_name")[k].firstChild.nodeValue;
            	            	bank_id =baseResponse.getElementsByTagName("bank_id")[k].firstChild.nodeValue;
            	            	branch_id =baseResponse.getElementsByTagName("branch_id")[k].firstChild.nodeValue;
            	            	opr_mode =baseResponse.getElementsByTagName("opr_mode")[k].firstChild.nodeValue;            	            	
      */
            	            	var option=document.createElement("OPTION");
            	            option.text=bank_ac_no;
            	            option.value=bank_ac_no;
            	            cmbBankAccNo.appendChild(option);
            	            }
            	            
            	           
            	    }
            	 if(flag=="NoData")
         	    {
            		alert("No Account Number in this Account Unit id"); 
            		document.getElementById("cmbBankAccNo1").innerHTML="";
         	    }
            	 
              }
              else
              {
            	    var flag =baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;             	 
             	    if(flag=="success")
             	    {
             	    		var acc_head_code=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;
             	    		document.getElementById("txtOprCode").value=acc_head_code;
             	    }
              }
        
        }
    }
}