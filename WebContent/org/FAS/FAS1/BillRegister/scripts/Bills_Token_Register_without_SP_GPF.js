
var seq=0;
var sl_no=1;

var acc_head_code="";
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
/*function remark_chnge()
{
	alert('test');
	
	
	var minor=document.getElementById("cboBillMinorType").options[document.getElementById("cboBillMinorType").selectedIndex].text;
	var sub=document.getElementById("cboBillSubType").options[document.getElementById("cboBillSubType").selectedIndex].text;
	
	var prono=document.getElementById("txtManualProceedingNo").value;
	var prodate=document.getElementById("txtManualProceedingDate").value;
	var txtPayableTo=(document.getElementById("txtPayeeCodeLoad").options[document.getElementById("txtPayeeCodeLoad").selectedIndex].text).split("-")[1];
	
 document.getElementById("mtxtRemarks").value=minor+" - "+sub+" - "+prono+" - "+prodate+" - "+txtPayableTo;
alert('txtre'+document.getElementById("mtxtRemarks").value);     
//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.action = url;
//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.method = "GET";
//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.submit();	
	
}*/


function allnumericplusminus(inputtxt)
{
	
   var numbers = /^[-+]?[0-9]+$/;
   if(inputtxt.match(numbers))
   {
     return true;
   }
   else
   {
   alert('Manual Proceeding No should be Numbers .. ');
   document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtManualProceedingNo.value="";
   document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtManualProceedingNo.focus();
   return false;
   }
}

function checkNull()
{

	
	var tbody;
    if(document.getElementById("Proceeding_Year").value=="")
    {
            alert("Enter the Proceeding Year");
            return false;    
    }
    if(document.getElementById("txtManualProceedingNo").value=="")
    {
            alert("Enter the Proceeding No");
            return false;    
    } if(document.getElementById("txtManualProceedingDate").value=="")
    {
        alert("Enter the Proceeding Date");
        return false;    
}
    if(document.getElementById("Proceeding_Month").value=="s")
    {
            alert("Select the Proceeding Month");           
            return false;
    }
    if(document.getElementById("txtBillDate").value.length==0)
    {
            alert("Enter the Bill Date");           
            return false;    
    }if(document.getElementById("txtManualProceedingDate").value.length!=0) 
    	{
    	var bill_dte=document.getElementById("txtManualProceedingDate").value;
    	//alert("tets "+bill_dte);
    	if(bill_dte.split('/')[2]==document.getElementById("Proceeding_Year").value && bill_dte.split('/')[1]==document.getElementById("Proceeding_Month").value)
   {
    	return true	;
   }else{
	   alert("Proceeding Date should be with in Proceeding Month & Proceeding Year **** ");       
	   return false	;
   }
    		
    		/*	if(bill_dte.split('/')[2]=="2015")
    		{
    }		if(bill_dte.split('/')[1]!="01")
    			{
    			alert("Bill Date can not be Greater than 31 January 2015");
    			  return false;    
    			}
    		}*/
    	
    	}
    if(document.getElementById("txtBillDate").value.length!=0) 
    	{
    	var bill_dte=document.getElementById("txtBillDate").value;
    	var proc_dte=document.getElementById("txtManualProceedingDate").value;
    
    	if(bill_dte.split('/')[2] = proc_dte.split('/')[2])
    		{
    		if(bill_dte.split('/')[1] = proc_dte.split('/')[1]){
    			if(bill_dte.split('/')[0] >= proc_dte.split('/')[0]){
    				return true	;
    			}
    		}
    		if(bill_dte.split('/')[1] > proc_dte.split('/')[1]){
    			
    				return true	;
    		}
    		}else if(bill_dte.split('/')[2] > proc_dte.split('/')[2])
    			{
    			return true	;
    				
    			}
    		else{
    			
    			 alert("Bill Date can be Greater than or Equal to Proceeding Date **** ");
    			return false	;	
    		}
    			}
    	
    	
    	//alert("tets "+bill_dte);
    /*	if(bill_dte.split('/')[2] < proc_dte.split('/')[2] || bill_dte.split('/')[1] < proc_dte.split('/')[1])
   {
    	return false	;
   }else if(bill_dte.split('/')[2]>proc_dte.split('/')[2] && bill_dte.split('/')[1]>proc_dte.split('/')[2])
   {
   	return true	;
  }
    	else{
	   alert("Bill Date can be Greaterthan or Equal to Proceeding Date **** ");       
	   return false	;
   }*/
    		
    		/*	if(bill_dte.split('/')[2]=="2015")
    		{
    		if(bill_dte.split('/')[1]!="01")
    			{
    			alert("Bill Date can not be Greater than 31 January 2015");
    			  return false;    
    			}
    		}*/
    	
    	
   /* if(document.getElementById("notgpfdetails").style.display=="block")
    {
    	tbody=document.getElementById("grid_body");
    	 if(tbody.rows.length==0)
    	    {
    	            alert("Enter the Details Part");         
    	            return false; 
    	    }
    }
    if(document.getElementById("gpfdetails").style.display=="block")
    {
    	tbody=document.getElementById("grid_body1");
    	 if(tbody.rows.length==0)
    	    {
    	            alert("Enter the Details Part");         
    	            return false; 
    	    }
    }
    */

    var tbody=document.getElementById("grid_body");
    
    if(tbody.rows.length==0)
    {
        alert("Enter the Details Part");
        //document.getElementById("txtAmount").focus();
        return false; 
    }
    
           
    if(tbody.rows.length>0)
    {
            var check_amt=0;
            rows=tbody.getElementsByTagName("tr");
            for(i=0;i<rows.length;i++)
            {
                var cells=rows[i].cells;
                //alert(cells.item(2).lastChild.nodeValue);
             /* if(cells.item(2).lastChild.nodeValue=='DR')
              {*/
                  check_amt=parseFloat(check_amt) + parseFloat(cells.item(4).firstChild.value);
                // alert("i am in if part "+check_amt);
             /* }  
              else
              {*/
               // check_amt=parseFloat(check_amt) + parseFloat(cells.item(5).firstChild.value);
               // alert(" im in else part "+check_amt);               
             // }
              
            }
              
          //  check_amt=Math.abs(check_amt);
            
          //  alert("after math operation"+check_amt);
             
           // alert(document.getElementById("txtAmount").value+"  "+check_amt);
            if(parseFloat(document.getElementById("txtTotalSanctionAmount").value)!=parseFloat(check_amt))
            {
             alert("Amount doesn't Tally.. Difference " +(parseFloat(document.getElementById("txtTotalSanctionAmount").value)-parseFloat(check_amt)))
             return false; 
            }
    }
   
          

	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtManualProceedingDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	
	 if(biisp_grid[2]<passsplit[2])
	 {
		 alert("Bill Date Should not be less than Sanction Datesss");
		 document.getElementById("txtBillDate").value="";
		 return false;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
		
   	 if(biisp_grid[1]<passsplit[1])
   	 {
   		 alert("Bill Date Should not be less than Sanction Date");
		 document.getElementById("txtBillDate").value="";
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
   		 if(billspl<passsplit[0])
       	 {
   			 alert("Bill Date Should not be less then SanctionDate");
   			 document.getElementById("txtBillDate").value="";
   			 return false;
       	 } 
   	 }
	 }
	 
}

/*function mtc_hide()
{
	var minortypedesc=document.getElementById("cboBillMinorType").
			options[document.getElementById("cboBillMinorType").selectedIndex].text;
	//alert(minortypedesc);
	if(minortypedesc=="GPF")
	{
	document.getElementById("yesid").style.display="block";
	document.getElementById("noid").style.display="none";
	}
	else
	{
		document.getElementById("yesid").style.display="block";
		document.getElementById("noid").style.display="block";
	}
}*/

function checksan()
{
	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var txtScrutinyDate=document.getElementById("txtProceedingDate").value;
	 var passsplit=txtScrutinyDate.split("/");
	
	 if(biisp_grid[2]<passsplit[2])
	 {
		 alert("Bill Date Should not be less than Sanction Datesss");
		 document.getElementById("txtBillDate").value="";
		 return false;
		
	 }
	 else if(biisp_grid[2]==passsplit[2])
	 {
		// alert(":::"+biisp_grid[1]);
		// alert(":ppp::"+passsplit[1]);
   	 if(biisp_grid[1]<passsplit[1])
   	 {
   		 alert("Bill Date Should not be less than Sanction Date");
		 document.getElementById("txtBillDate").value="";
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
   		 if(billspl<passsplit[0])
       	 {
   			 alert("Bill Date Should not be less then SanctionDate");
   			 document.getElementById("txtBillDate").value="";
   			 return false;
       	 } 
   	 }
	 }
	
}


/*
function checkinvdate()
{
    var manualdate=document.getElementById("txtManualProceedingDate").value;
	var manu_pro_date=manualdate.split("/");
	
	 		var invdate_grid=document.getElementById("txtInvoiceReceivedDate").value;
	var invoice_date=invdate_grid.split("/");
	 
	 if(manu_pro_date[2]<invoice_date[2])
	 {
		 alert("Manual Proceeding Date Should not be less than Last Invoce Date");
		 document.getElementById("txtManualProceedingDate").value="";
		 document.getElementById("txtInvoiceReceivedDate").value="";
		 
		 return false;
		
	 }
	 else if(invoice_date[2]==manu_pro_date[2])
	 {
	
   	 if(manu_pro_date[1]<invoice_date[1])
   	 {
   		 alert("Manual Proceeding Date Should not be less than Last Invoce Date");
   		 document.getElementById("txtManualProceedingDate").value="";
		 document.getElementById("txtInvoiceReceivedDate").value="";
		 return false;
   	 }
   	 else if(manu_pro_date[1]==invoice_date[1])
   	 {
   		 var manuspl;
   		 //alert(biisp_grid[0].length);
   		 if(manu_pro_date[0].length==2)
   		 {
   			manuspl=manu_pro_date[0];
   		 }
   		 else
   		 {
   			manuspl="0"+manu_pro_date[0];
   		 }
   		 if(manuspl<invoice_date[0])
       	 {
       	 
   			alert("Manual Proceeding Date Should not be less than Last Invoce Date");
      		 document.getElementById("txtManualProceedingDate").value="";
      		 document.getElementById("txtInvoiceReceivedDate").value="";
   			 return false;
       	 } 
   	 }
	 }
}*/
function totAmt(){
	//alert("call");
	var maj=document.getElementById("cboBillMajorType").value;
	var min=document.getElementById("cboBillMinorType").value;
	var subb=document.getElementById("cboBillSubType").value;
	
	if((maj==2)&&(min==1)&&(subb==5)){
	document.getElementById("txtTotalSanctionAmount").value=0;
	 document.getElementById("txtTotalBillAmount").value=0;
	}
}
function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			
			/*if (command == "getProceedingNo") {
				getProceedingNo_res(baseResponse);
			}
//			
			else if (command == "Acc_Head_Code") {
				 //alert("manipulate");
				Acc_Head_Code11(baseResponse);
			}
			else  if (command == "getempname_off") {
				 //alert("manipulate");
				getempname_re(baseResponse);
			}
			*/
				/*else if (command == "getSLSProDetails") {
				 //alert("manipulate");
				jourDetLoad(baseResponse);
			} */
			
			 if (command == "getBillMajorType") {

				firstLoad(baseResponse);
			} else if (command == "getBillMinorType") {

				getBillMinorType1(baseResponse);
			} else if (command == "getBillsubType") {

				getBillsubType1(baseResponse);
			} else if(command == "AddNew"){

				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

				if (flag == "success") {
					alert("Record Inserted Successfully");
					document.getElementById("butSub").disabled=false;
					document.getElementById("imgfld").style.visibility = "hidden";
//					refresh();
					window.location.reload(true);
					
				} else {
					
					document.getElementById("butSub").disabled=false;
					document.getElementById("imgfld").style.visibility = "hidden";
					alert("Record Insertion Failed");
				}

			}
			else if (command == "loadPayyeedesc") {
				loadPayyeedesc1(baseResponse);
			} 
			
			else if (command == "loadPayeeType") {
				loadPayeeType1(baseResponse);
			}
			
			/*else if (command == "calculateBudget") {
				// alert("manipulate");
				calculateBudget1(baseResponse);
			}*/ else if (command == "getOffice") {
				// alert("manipulate");
				getOffice1(baseResponse);
			} else if (command == "saveFunc") {
				 //alert("manipulate saveFunc");
				saveFunc1(baseResponse);
			} else if (command == "Edit") {
				// alert("manipulate");
				Edit1(baseResponse);
			} else if (command == "deleted") {
				// alert("manipulate");
				deleteRow(baseResponse);
			} else if (command == "update") {
				updateRow(baseResponse);
			} else if (command == "ClearAll") {
				ClearAll1(baseResponse);
			} else if (command == "IVno") {
				IVno1(baseResponse);
			} else if (command == "InvoiceDetails") {
				InvoiceDetails1(baseResponse);
			}
			
			/*else if (command == "getProceedingDetails") {
				getProceedingDetails_res(baseResponse);
			}
			else if (command == "loadHead") {
				loadHead_res(baseResponse);
			}
			else if (command == "getAccDesc") {
				getAccDesc_res(baseResponse);
			}
			else if (command == "budgetProv") {
				budgetProv_res(baseResponse);
			}*/
			
			 else if(command=="loadAccHead")
	            {
				 loadAccHead1(baseResponse);
	            }
			
			/* else if(command=="checkinvoicee")
				 var r="";
				 r=checkinvoicee1(baseResponse);
				// alert("rrrrrrrrrrr---->"+r);
				 return r;
	            }*/
			 else if(command=="loadPayeeCode_Desc")
	            {
				 //manipulate_payee
				// loadPayeeCode_Desc1(baseResponse);

					// loadPayeeCode_DescCp(baseResponse);

						
						
						var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
						if (flag == "success") {
						
							
							 var payeecode1=document.getElementById("txtPayeeCodeLoad"); 
							/* var option=document.createElement("OPTION");
							  option.text="";
					          option.value="--Select--";
					          payeecode1.add(option);
					         */
					           payeecode1.length=0;
					          
					        var items_id=new Array();
					        var items_name=new Array();
					        var items_name_i=new Array();
					       
					           var cid=baseResponse.getElementsByTagName("payeecode");
					           var cname=baseResponse.getElementsByTagName("payeecodeDesc");
					          //alert("cid.length "+cid.length);
					          // var bothnameid=
					           for(var k=0;k<cid.length;k++)
					           {
					               items_id[k]=baseResponse.getElementsByTagName("payeecode")[k].firstChild.nodeValue;
					               items_name[k]=baseResponse.getElementsByTagName("payeecodeDesc")[k].firstChild.nodeValue;
					               items_name_i[k]= baseResponse.getElementsByTagName("payeecodeDesc_Load")[k].firstChild.nodeValue;
					              
					           }
					          //alert("items id.length "+items_id.length);
					         // clear_Combo(payeecode1);
					           for(var k=0;k<items_id.length;k++)
					           {   
					                 var option=document.createElement("OPTION");
					               //  option.text=items_name[k]+"("+items_id[k]+")";
					               //  option.value=items_id[k];
					                 //option.text=items_name[k];
					                 option.text=items_name_i[k];
					                 option.value=items_id[k];
					                 
					                  try
					                 {
					                	  payeecode1.add(option);
					                 }
					                 catch(errorObject)
					                 {
					                	 payeecode1.add(option,null);
					                 }
					           }
					           
					          // document.getElementById("payname_desig").value=items_id[0]; 

						} else if (flag == "failure") {
							
							document.getElementById("payname_desig").length=0;
							document.getElementById("txtPayeeCodeLoad").length=0;
							document.getElementById("txtPayeeCode").value="";
							alert("No payee code");
						} 
						
						loadPayyCp();
		            
	            } else if(command=="loadPayeeCode_Desc1")
	            {
					 loadPayeeCode_DescCp(baseResponse);
		            }
			 else if(command==="loadAccDesc"){
				 loadAccDesc1(baseResponse);
					}
		}
	}
}
function loadPayyeedesc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert("flag "+flag);
	 if(flag=="success")
	   	   {
		 document.getElementById("one").style.display="block";
	         document.getElementById("two").style.display="none";
 		   var cid=baseResponse.getElementsByTagName("cid")[0].firstChild.nodeValue;
            var cname=baseResponse.getElementsByTagName("cname")[0].firstChild.nodeValue;
 	       
            document.getElementById("txtPayeeCodeLoad").length=0;
            //document.getElementById("txtPayeeCodeLoad").value=items_id[0];
 	          var cmbSL_Code=document.getElementById("txtPayeeCodeLoad");
 	          var option=document.createElement("OPTION");
              option.text=cname;
              option.value=cid;
               try
              {
                  cmbSL_Code.add(option);
              }
              catch(errorObject)
              {
                  cmbSL_Code.add(option,null);
              }
          
	   	   }
	   	   else
	   	   {
	   		   	   alert("Selected Employee Id is invalid");
	   		   	  // document.getElementById("txtPayeeCode").value="";
	   		      // var payname_desig1=document.getElementById("payname_desig");
	   		      // payname_desig1.length=0;
	   		    //   clear_Combo(payname_desig1);
	   	   }
	
	
}
function loadPayyCp(){
	///alert('test');
		
		var codeDe=document.getElementById("txtPayeeCodeLoad").options[document.getElementById("txtPayeeCodeLoad").selectedIndex].text;
		
		var fy1=codeDe.split('-');
		var va1 = fy1[0];
		var va2 = fy1[1];
		
		document.getElementById("txtPayeeCode").value=va1; 
		//document.getElementById("payname_desig").value=va2;
			var payeecode22=document.getElementById("payname_desig");
		payeecode22.length=0;

		 for(var tt=0;tt<1;tt++)
	     {
		
	       	
	        var opt = document.createElement("option");
			  opt.value = va2;
			  opt.innerHTML = va2;
			  payeecode22.appendChild(opt);
	     }

		 document.getElementById("txtPayeeCode").readOnly=true;
		
	}
function loadAccDesc(){
	  var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	  if(txtAcc_HeadCode.length>=6)
      {
        
          
          var url="../../../../../Bills_Token_Register_without_SP_GPF?command=loadAccDesc&txtAcc_HeadCode="+txtAcc_HeadCode;
  		
       //  alert(url);
          var xmlrequest=AjaxFunction();
  	    xmlrequest.open("POST",url,true); 
  	    xmlrequest.onreadystatechange=function()
  	    
  	    {
  	    	manipulate(xmlrequest); 
  	    };
  	   
  	    xmlrequest.send(null);
      }   
 	    
}

function loadAccDesc1(baseResponse) {
	
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		document.getElementById("txtAcc_HeadDesc").value="";
		
               items_id=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
               items_name=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[0].firstChild.nodeValue;
               document.getElementById("txtAcc_HeadDesc").value=items_name;
              

	} else if (flag == "failure") {
		
		document.getElementById("txtAcc_HeadDesc").value="";
		
	} 
	
}
function loadAccHead(){
	
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType==""||cboBillSubType=="s"){
		cboBillSubType=0;
	}
	
	  var currentTime = new Date();
      var month = currentTime.getMonth() + 1;  
      var day = currentTime.getDate();
      var year = currentTime.getFullYear();	
      var ssyr1="";
      fin_year_from="",fin_year_to="",fin_year_to_s="";
    
      if(month<4)
             year=year-1;
       
                 fin_year_from=year;
                 fin_year_to=year+1;
         // alert("fin_year_to "+fin_year_to);
           fin_year_to_s=fin_year_to.toString();
					 ssyr1=fin_year_to_s.substring(2,4);
               var finanical_yr=fin_year_from+"-"+ssyr1;
                
               var url="../../../../../Bills_Token_Register_without_SP_GPF?command=loadAccHead"+
           	"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType+"&finanical_yr="+finanical_yr;
	
	

 //alert(url);
var xmlrequest = AjaxFunction();
xmlrequest.open("POST", url, true);
xmlrequest.onreadystatechange = function() {
manipulate(xmlrequest);
}

xmlrequest.send(null);
}
function loadAccHead1(baseResponse){
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtAcc_HeadCode.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("accHead").length;
		for ( var i = 0; i < len4; i++) {
			var headNo = baseResponse.getElementsByTagName("accHead")[i].firstChild.nodeValue;
			var se = document.getElementById("txtAcc_HeadCode");
			var op = document.createElement("OPTION");
			op.value = headNo;
			var txt = document.createTextNode(headNo);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "Nohead") {
		alert("AccountHead Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}
/*function jourDetLoad(baseResponse)
{
var name_emp="";
var common_name="";
	var procdate = baseResponse.getElementsByTagName("procdate");
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success"){
		
		var tbody=document.getElementById("tblList");
	   var t=0;
       for(t=tbody.rows.length-1;t>=0;t--)
           {
              tbody.deleteRow(0);
           }   
       var s_no=1;
       var crtotal = baseResponse.getElementsByTagName("tot_cr")[0].firstChild.nodeValue;
   	var tot_dr = baseResponse.getElementsByTagName("tot_dr")[0].firstChild.nodeValue;
   //alert(crtotal);
   document.getElementById("crtotal").value=crtotal;
   document.getElementById("drtotal").value=tot_dr;  
  // document.getElementById("coutn_sanc").value=procdate.length;
	var PROCESS_FLOW_ID=baseResponse.getElementsByTagName("PROCESS_FLOW_ID");
     /// alert(bill_no);
	document.getElementById("coutn_sanc").value=PROCESS_FLOW_ID.length;
	

	for(var i=0;i<PROCESS_FLOW_ID.length;i++){
		var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
		var EMPLOYEE_NAME = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
		var bill_no=baseResponse.getElementsByTagName("bill_no")[i].firstChild.nodeValue;
		var BILL_DATE=baseResponse.getElementsByTagName("BILL_DATE")[i].firstChild.nodeValue;
	var procdate = baseResponse.getElementsByTagName("procdate")[i].firstChild.nodeValue;
	var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("PAYMENT_HEAD_OF_AC")[i].firstChild.nodeValue;
	var ACCOUNT_HEAD_DESC=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
	var cr_amt=baseResponse.getElementsByTagName("cr_amt")[i].firstChild.nodeValue;
	var dr_amt=baseResponse.getElementsByTagName("dr_amt")[i].firstChild.nodeValue;
	var cr_dr=baseResponse.getElementsByTagName("cr_dr")[i].firstChild.nodeValue;
	
//	var sanAmt = baseResponse.getElementsByTagName("sanAmt")[i].firstChild.nodeValue;
	var PAYMENT_AMOUNT = baseResponse.getElementsByTagName("PAYMENT_AMOUNT")[i].firstChild.nodeValue;
	
	var sanc_id=baseResponse.getElementsByTagName("HRMS_SANCTION_ID")[i].firstChild.nodeValue;
	var Office_Id=baseResponse.getElementsByTagName("Office_Id")[i].firstChild.nodeValue;
	//var off_name=baseResponse.getElementsByTagName("off_name")[i].firstChild.nodeValue;
	
	var name = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
	if(name==name_emp)
	{
		common_name="";
	}
	else
	{
		common_name=name;
		name_emp=name;
	}
	
	
	var tbody=document.getElementById("tblList");
	var tr_ele=document.createElement("TR");
	
	var cell1=document.createElement("TD");
	
	var text=document.createTextNode(s_no);
	cell1.appendChild(text);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','left');
	var empname=  document.createElement("input");
	empname.type="hidden";
	empname.id="employeeid";
	empname.name="employeeid";
	empname.value=empid;
	var text=document.createTextNode(common_name);
	cell1.appendChild(text);
	cell1.appendChild(empname);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','left');
	var head=  document.createElement("input");
	head.type="hidden";
	head.id="head";
	head.name="head";
	head.value=ACCOUNT_HEAD_CODE;
	var text=document.createTextNode(ACCOUNT_HEAD_CODE+"-"+ACCOUNT_HEAD_DESC);
	cell1.appendChild(text);
	cell1.appendChild(head);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','Right');
	var dr_amt1=  document.createElement("input");
	dr_amt1.type="hidden";
	dr_amt1.id="dr";
	dr_amt1.name="dr";
	dr_amt1.value=dr_amt;
	var text_dr_amt=document.createTextNode(dr_amt);
	cell1.appendChild(text_dr_amt);
	cell1.appendChild(dr_amt1);
	tr_ele.appendChild(cell1);
	
	var cell1=document.createElement("TD");
	cell1.setAttribute('align','Right');
	var cr_amt1=  document.createElement("input");
	cr_amt1.type="hidden";
	cr_amt1.id="cr";
	cr_amt1.name="cr";
	cr_amt1.value=cr_amt;
	var text_cr_amt=document.createTextNode(cr_amt);
	cell1.appendChild(text_cr_amt);
	cell1.appendChild(cr_amt1);
	tr_ele.appendChild(cell1);
	
	
	
	var pay_amt=  document.createElement("input");
	pay_amt.type="hidden";
	pay_amt.id="payment";
	pay_amt.name="payment";
	pay_amt.value=PAYMENT_AMOUNT;	
	cell1.appendChild(pay_amt);
	

	var cr_dr_val=  document.createElement("input");
	cr_dr_val.type="hidden";
	cr_dr_val.id="cr_dr_val";
	cr_dr_val.name="cr_dr_val";
	cr_dr_val.value=cr_dr;	
	cell1.appendChild(cr_dr_val);
	
	var emp_id_val=  document.createElement("input");
	emp_id_val.type="hidden";
	emp_id_val.id="emp_id";
	emp_id_val.name="emp_id";
	emp_id_val.value=empid;	
	cell1.appendChild(emp_id_val);
	
	var bill_no_val=  document.createElement("input");
	bill_no_val.type="hidden";
	bill_no_val.id="bill_no";
	bill_no_val.name="bill_no";
	bill_no_val.value=bill_no;	
	cell1.appendChild(bill_no_val);

	var BILL_DATE_val=  document.createElement("input");
	BILL_DATE_val.type="hidden";
	BILL_DATE_val.id="BILL_DATE";
	BILL_DATE_val.name="BILL_DATE";
	BILL_DATE_val.value=BILL_DATE;	
	cell1.appendChild(BILL_DATE_val);
	
	tr_ele.appendChild(cell1);
	tbody.appendChild(tr_ele);
	s_no++;
}
	}else if(flag=="nodata1"){
		alert('Already SLS Jounal Created ..... ');
	}

	}

*/
function initialLoad(path) {
	//alert(path);

	var url = path
			+ "/Bills_Token_Register_without_SP_GPF?command=getBillMajorType";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}

function getOffice(path) {
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var url = path
			+ "/Bills_Token_Register_without_SP_GPF?command=getOffice&txtEmpID_mas="
			+ txtEmpID_mas;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}
/*
function loadProceedingNo(path)
{
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}

	var Proceeding_Year = document.getElementById("Proceeding_Year").value;
	var Proceeding_Month = document.getElementById("Proceeding_Month").value;
	var url =url = path+ "/Bills_Token_Register_without_SP_GPF?command=getProceedingNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType
	+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType+"&Proceeding_Year="+Proceeding_Year+"&Proceeding_Month="+Proceeding_Month;;

//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}*/
function numbersonly1(e, t) {
	//alert("t.length "+t.value.length);
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
			return false;
	}	
}
function calins(e,t) {
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
         
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false; 
        }
}

function loadAccountHeadDesc(path)
{
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	if(txtAcc_HeadCode=="0")
		{
		alert("Select Account HeadCode");
		return false;
		}
	var url = path+ "/Bills_Token_Register_without_SP_GPF?command=getAccDesc&txtAcc_HeadCode="+txtAcc_HeadCode;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

function checkdt1(t)
{

   if(t.value.length==0)
       return false;
   if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
   {


       // var c=t.value.replace(/-/g,'/');
        var c=t.value;
       try{
       var f=DateFormat(t,c,event,true,'3');
       }catch(e){
       //exception  start

        t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;
            if(err!=0)
               {
                   t.value="";
                   return false;
               }
           return true;


       //exception end

       }
       if( f==true)
       {
           //alert(f);
           //t.value=c.replace(/\//g,'-');
           t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;

           return true;

       }
       else
       {
               if(err!=0)
               {
                   t.value="";
                   return false;
               }
       }

   }
   else
   {
           alert('Date format  should be (dd/mm/yyyy)');
           t.value="";
           //t.focus();
           return false
   }

}
/*function loadprocDetails(path)
{
	//alert("%%%%%%");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if(cboBillMajorType=="0")
		{
		alert("Choose Major Type");
		return false;
		}
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	if(cboBillMinorType=="0")
		{
		alert("Choose Minor Type");
		return false;
		}
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType=="0")
		{
		alert("Choose Sub Type");
		return false;
		}
	


    var combo1 = document.getElementById("txtProceedingNo");
    var txtProceedingNo = combo1.options[combo1.selectedIndex].text;

    var proValue = document.getElementById("txtProceedingNo").value;
	
	
	//var txtProceedingNo = document.getElementById("txtProceedingNo").value;
	//alert(txtProceedingNo);
	
	
	if(txtProceedingNo=="")
		{
		alert("Choose Proceeding No");
		return false;
		}
	
	var url = path+ "/Bills_Token_Register_without_SP_GPF?command=getProceedingDetails&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+
	"&cboBillSubType="+cboBillSubType+"&txtProceedingNo="+txtProceedingNo+"&proValue="+proValue;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);	
}*/

function getOffice1(baseResponse) {
	document.getElementById("cboOffice").length = 0;
	document.getElementById("cmbMas_SL_Code").length=0;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++)
		{
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.value = OfficeID;
		}
		
		var lenths = baseResponse.getElementsByTagName("EMPLOYEE_ID").length;
		for ( var i = 0; i < lenths; i++)
		{
			var empid = baseResponse.getElementsByTagName("EMPLOYEE_ID")[i].firstChild.nodeValue;
			var empname = baseResponse.getElementsByTagName("EMPLOYEE_NAME")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(empname);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmbMas_SL_Code.value = empid;
		}
		
	} else {
		alert("Fail to Load Bill Major Type");
	}
}


function Acc_Head_Code11(baseResponse)
{
	
	var count = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert(flag);
	if(flag=="success")
		{
	//	alert(count);
		for ( var i = 0; i < count; i++) {
			
			var Acc_Head_Code = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
			//alert("*****/////==== "+Acc_Head_Code);
			var combo = document.getElementById("txtAcc_HeadCode");
			var option = document.createElement("OPTION");
			option.text = Acc_Head_Code;
		    option.value = Acc_Head_Code;
		    try {
		        combo.add(option, null); //Standard
		    }catch(error) {
		        combo.add(option); // IE only
		    }
			
		}
		}
}
/*function getProceedingNo_res(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
	{
	
			var k_count=0;
			document.getElementById("txtProceedingNo").length = 1;
			var len1 = baseResponse.getElementsByTagName("procNo").length;
		
			for ( var i = 0; i < len1; i++) {
			//	alert("ttt");
				var procNo = baseResponse.getElementsByTagName("procNo")[i].firstChild.nodeValue;
				var procId = baseResponse.getElementsByTagName("sanc_id")[i].firstChild.nodeValue;
				var se = document.getElementById("txtProceedingNo");
				var op = document.createElement("OPTION");
				op.value = procId;
				var txt = document.createTextNode(procNo);
				op.appendChild(txt);
				se.appendChild(op);
				k_count++;
			}
			if(k_count>0)
			{
				//document.getElementById("sanc_id").value="";
				
				var cboBillMajorType = document.getElementById("cboBillMajorType").value;
				if(cboBillMajorType=="0")
					{
					alert("Choose Major Type");
					return false;
					}
				var cboBillMinorType = document.getElementById("cboBillMinorType").value;
				if(cboBillMinorType=="0")
					{
					alert("Choose Minor Type");
					return false;
					}
				var cboBillSubType = document.getElementById("cboBillSubType").value;
				if(cboBillSubType=="0")
					{
					alert("Choose Sub Type");
					return false;
					}
		  	}
	}
	else
	{
		alert("No Data Found");
		return false;
	}
}

function loadHead_res(baseResponse)
{
	document.getElementById("txtAcc_HeadCode").length=0;
	var len1 = baseResponse.getElementsByTagName("acchead").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("acchead")[i].firstChild.nodeValue;
		 //alert(empName);
		var se = document.getElementById("txtAcc_HeadCode");
		var op = document.createElement("OPTION");
		op.value = empName;
		var txt = document.createTextNode(empName);
		op.appendChild(txt);
		se.appendChild(op);

	}
	var budgetAllo = baseResponse.getElementsByTagName("budgetAllo")[0].firstChild.nodeValue;
	var spent = baseResponse.getElementsByTagName("spent")[0].firstChild.nodeValue;
	var finttl = baseResponse.getElementsByTagName("finttl")[0].firstChild.nodeValue;
	var head_desc = baseResponse.getElementsByTagName("head_desc")[0].firstChild.nodeValue;
	
	
	//document.getElementById("txtBudgetProvision").value=budgetAllo;
	//document.getElementById("txtBudgetSpent").value=spent;
	document.getElementById("budAvail").value=finttl;
	document.getElementById("txtAcc_HeadDesc").value=head_desc;
}

function getAccDesc_res(baseResponse)
{
	
		var headdesc = baseResponse.getElementsByTagName("headdesc")[0].firstChild.nodeValue;
		document.getElementById("txtAcc_HeadDesc").value=headdesc;
		
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		
		var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
		
		var url ="../../../../../Bills_Token_Register_without_SP_GPF?command=budgetProv&txtAcc_HeadCode="+txtAcc_HeadCode+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};

		xmlrequest.send(null);
		
}
*/


function loadPayeeType(path){
	var url = path
	+ "/Bills_Token_Register_without_SP?command=loadPayeeType";

 //alert(url);
var xmlrequest = AjaxFunction();
xmlrequest.open("POST", url, true);
xmlrequest.onreadystatechange = function() {
manipulate(xmlrequest);
}

xmlrequest.send(null);
}

function loadPayeeType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType.length = 0;
	if (flag == "success1") {
		var len4 = baseResponse.getElementsByTagName("payee_type_code").length;
		for ( var i = 0; i < len4; i++) {
			var payee_type_code1 = baseResponse.getElementsByTagName("payee_type_code")[i].firstChild.nodeValue;
			var PAYEE_TYPE_DESC1 = baseResponse.getElementsByTagName("PAYEE_TYPE_DESC")[i].firstChild.nodeValue;
			
			var se = document.getElementById("txtPayeeType");
			var op = document.createElement("OPTION");
			op.value = payee_type_code1;
			var txt = document.createTextNode(PAYEE_TYPE_DESC1);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load payee type ");
	}
}

function IVno1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.length = 1;
	if (flag == "success1") {
		var len4 = baseResponse.getElementsByTagName("InvoiceNo").length;
		for ( var i = 0; i < len4; i++) {
			var InvoiceNo = baseResponse.getElementsByTagName("InvoiceNo")[i].firstChild.nodeValue;
			var se = document.getElementById("txtIfSelectfromList");
			var op = document.createElement("OPTION");
			op.value = InvoiceNo;
			var txt = document.createTextNode(InvoiceNo);
			op.appendChild(txt);
			se.appendChild(op);
			
		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}
function loadPayy(){

	
	var codeDe=document.getElementById("txtPayeeCodeLoad").value;
	//alert("ff 	codeDe); "+codeDe);
	var fy1=codeDe.split('-');
	var va1 = fy1[0];
	var va2 = fy1[1];
	//alert("va1 "+va1);
	//alert("codeDe.length "+codeDe.length);
	document.getElementById("txtPayeeCode").value=va1; 
	var payeecode22=document.getElementById("payname_desig");
	payeecode22.length=0;
	//alert("hi 11");
	 for(var tt=0;tt<1;tt++)
     {
	 /* var option=document.createElement("OPTION");
        option.text=va2;
        option.value=va1;
       	payeecode22.add(option);*/
       	
        var opt = document.createElement("option");
		  opt.value = va2;
		  opt.innerHTML = va2;
		  payeecode22.appendChild(opt);
     }
	// document.getElementById("txtPayeeCode").setattribute("readonly","readonly");
	 document.getElementById("txtPayeeCode").readOnly=true;
	
}
function callpd()
{
	//alert("callpd");
	var txtPayeeCode1 = document.getElementById("txtPayeeCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var maj=document.getElementById("cboBillMajorType").value;
	var min=document.getElementById("cboBillMinorType").value;
	var subb=document.getElementById("cboBillSubType").value;
   
	if(maj=="0")
	{
	alert("Choose Major Type");
	return false;
	}else if(min=="0")
	{
	alert("Choose Minor Type");
	return false;
	}else if(subb=="0")
	{
	alert("Choose Sub Type");
	return false;
	}
	else{
	var url="../../../../../Bills_Token_Register_without_SP_GPF?command=loadPayyeedesc&txtPayeeCode="+txtPayeeCode1+
	"&cmbOffice_code="+cboOffice_code+"&maj="+maj+"&min="+min+"&subb="+subb;

   //alert(url);
    var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
    {
       
    	manipulate(xmlrequest);
    }   
    xmlrequest.send(null);
	}
}

function loadPayeeCode_Desc(){
	
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtPayeeType=document.getElementById("txtPayeeType").value;
	//alert("txtpayyee "+txtPayeeType);
	if(parseInt(txtPayeeType)==7){
		 document.getElementById("txtPayeeCode").readOnly=false;
		 alert("Type Employee Code");
		 document.getElementById("one").style.display="none";
	         document.getElementById("two").style.display="block";
	         document.getElementById("txtPayeeCodeLoad").length=0;
	         document.getElementById("txtPayeeCodec").length="";
	         
	}else{
		 document.getElementById("one").style.display="block";
         document.getElementById("two").style.display="none";
		// document.getElementById("payname_desig").style.display="block";
	var url="../../../../../Bills_Token_Register_without_SP_GPF?command=loadPayeeCode_Desc&cboAcc_UnitCode="+cboAcc_UnitCode+"&cboOffice_code="+cboOffice_code+
	"&txtPayeeType="+txtPayeeType;
	
    //alert(url);
    var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
    
    {
    	manipulate(xmlrequest); 
    };
   
    xmlrequest.send(null);
	}
   
}
function calbillAmt(){
	/*
	if(parseInt(document.getElementById("txtTotalSanctionAmount").value)>parseInt(document.getElementById("txtDeductedAmount").value))
	{
	 document.getElementById("txtTotalBillAmount").value=parseInt(document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalSanctionAmount.value)-
	 parseInt(document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtDeductedAmount.value);
	}else{
		alert("TotalSanctionAmount is must Greater than DeductedAmount ..... ");
		document.getElementById("txtTotalSanctionAmount").value="";
		document.getElementById("txtDeductedAmount").value="";
		document.getElementById("txtTotalBillAmount").value="";
	}*/
	var maj=document.getElementById("cboBillMajorType").value;
	var min=document.getElementById("cboBillMinorType").value;
	var subb=document.getElementById("cboBillSubType").value;
	
	if((maj==2)&&(min==1)&&(subb==5)){
		document.getElementById("txtTotalBillAmount").value=0;
		document.getElementById("txtTotalSanctionAmount").value=0;
	}else{
	
	document.getElementById("txtTotalBillAmount").value=parseInt(document.getElementById("txtTotalSanctionAmount").value);
	}
}
function callemp(path)
{
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	
		var url = path+ "/Bills_Token_Register_without_SP_GPF?command=getempname_off&txtEmpID_mas="+ txtEmpID_mas+"&cmbOffice_code="+cmbOffice_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

}
/*
function getempname_re(baseResponse)
{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
	var empname = baseResponse.getElementsByTagName("empname")[0].firstChild.nodeValue;
	
	document.getElementById("cmbMas_SL_Code").length = 0;
	var len1 = baseResponse.getElementsByTagName("empname").length;
	for ( var i = 0; i < len1; i++) {
		var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
		var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empid;
		var txt = document.createTextNode(empname);
		op.appendChild(txt);
		se.appendChild(op);
	
	}
	}
	else
	{
		alert("Enter Relevant EmployeeId For This Office");
		document.getElementById("cmbMas_SL_Code").length = 0;
		document.getElementById("txtEmpID_mas").value="";
	}
}*/
function firstLoad(baseResponse) {
	 //alert("RKsbg");
	var len1 = baseResponse.getElementsByTagName("empName").length;
	for ( var i = 0; i < len1; i++) {
		var empName = baseResponse.getElementsByTagName("empName")[i].firstChild.nodeValue;
		var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		// alert(empName);
		var se = document.getElementById("cmbMas_SL_Code");
		var op = document.createElement("OPTION");
		op.value = empid;
		var empvalue=empName+"("+empid+")";
		var txt = document.createTextNode(empvalue);
		op.appendChild(txt);
		se.appendChild(op);

	}

	
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtEmpID_mas.value = empid;

	

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("billMajorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMajorTypeCode = baseResponse
					.getElementsByTagName("billMajorTypeCode")[i].firstChild.nodeValue;
			var billMajorTypeDesc = baseResponse
					.getElementsByTagName("billMajorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMajorType");
			var op = document.createElement("OPTION");
			op.value = billMajorTypeCode;
			var txt = document.createTextNode(billMajorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
		}

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.value = OfficeID;
		}
		
		
		document.getElementById("txtEmpID_mas").value= baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		for ( var i = 0; i < len6; i++) {
			var empid = baseResponse.getElementsByTagName("empid")[i].firstChild.nodeValue;
			var empname = baseResponse.getElementsByTagName("empname")[i].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(empname);
			op.appendChild(txt);
			se.appendChild(op);
		//	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.value = OfficeID;
		}

	} else {
		alert("Fail to Load Bill Major Type");
	}
}
/*
function calculateBudget(path) {
	// alert(path);
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtaccountheadcode = document.getElementById("txtAcc_HeadCode").value;

	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	if (txtaccountheadcode == "") {
		alert("Enter Account Head Code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtAcc_HeadCode
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=calculateBudget&cboAcc_UnitCode="
				+ cboAcc_UnitCode + "&cboOffice_code=" + cboOffice_code
				+ "&year=" + year + "&year1=" + year1 + "&txtaccountheadcode="
				+ txtaccountheadcode;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}*/
/*
function calculateBudget1(baseResponse) {
	 //alert("RKsbg");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetProvision.value = BudgetProvided;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flag == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}
}
*/
function getBillMinorType(path) {
//	if
	callemp(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMajorType
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=getBillMinorType&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}

}

function getBillMinorType1(baseResponse) {
      	document.getElementById("txtBillNo").value = "";
	var flag1 = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	        if (flag1 == "success") {
		var BillNo = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillNo.value = BillNo;
	} else {
		alert("Failed to Generate Bill No");
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMinorType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}

	/*	var len5 = baseResponse.getElementsByTagName("EstimateSanctionNo").length;
		// alert(len5);
		var se = document.getElementById("cboEstimateSanctionNumber");
		//se.length=0;
		for ( var i = 0; i < len5; i++) {
			var EstimateSanctionNo = baseResponse
					.getElementsByTagName("EstimateSanctionNo")[i].firstChild.nodeValue;
			// alert(EstimateSanctionNo);
			
			var op = document.createElement("OPTION");
			op.value = EstimateSanctionNo;
			var txt = document.createTextNode(EstimateSanctionNo);
			op.appendChild(txt);
			se.appendChild(op);
		}*/

	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}

function getBillsubType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;

	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	//alert(document.getElementById("cboBillMinorType").value);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMajorType
				.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMinorType
				.focus();
	} 
	
	
	else {
		
	/*	if(document.getElementById("cboBillMinorType").value=="1" || document.getElementById("cboBillMinorType").value=="2")
		{
			//alert(document.getElementById("cboBillMinorType").value);
			document.getElementById("txtPayeeType").length=0;
		var se = document.getElementById("txtPayeeType");
		var op = document.createElement("OPTION");
		op.value = 7;
		var txt = document.createTextNode("Employee");
		op.appendChild(txt);
		se.appendChild(op);
		}
	
		else{
			alert('No Payee Type');
		}  */
			var url = path
					+ "/Bills_Token_Register_without_SP_GPF?command=getBillsubType&cboBillMajorType="
					+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType;
			// alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() {
				manipulate(xmlrequest);
			}
	
			xmlrequest.send(null);
	}

}

function getBillsubType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillSubType.length = 1;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		for ( var i = 0; i < len4; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}
/*function IVno(path) {
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoInvoiceEntryOption[0].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoInvoiceEntryOption[0].value;
	} else {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoInvoiceEntryOption[1].value;
	}

	if (rdoInvoiceEntryOption == "Entry") {
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtIfSelectfromList.disabled = true;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceNo.disabled = false;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceNo.value = "";
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceDate.value = "";
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceAmount.value = "";
	} else {
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceNo.disabled = true;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtIfSelectfromList.disabled = false;

		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var today = new Date();
		var day = today.getDate();
		var month = today.getMonth();
		month = month + 1;
		var year = today.getYear();
		if (year < 1900)
			year += 1900;

		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=IVno&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&month=" + month + "&year=" + year;

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);

	}
}

function IVno1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtIfSelectfromList.length = 1;
	if (flag == "success1") {
		var len4 = baseResponse.getElementsByTagName("InvoiceNo").length;
		for ( var i = 0; i < len4; i++) {
			var InvoiceNo = baseResponse.getElementsByTagName("InvoiceNo")[i].firstChild.nodeValue;
			var se = document.getElementById("txtIfSelectfromList");
			var op = document.createElement("OPTION");
			op.value = InvoiceNo;
			var txt = document.createTextNode(InvoiceNo);
			op.appendChild(txt);
			se.appendChild(op);

		}
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}
*/
/*function InvoiceDetails(path) {
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtIfSelectfromList = document.getElementById("txtIfSelectfromList").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (txtIfSelectfromList == "" || txtIfSelectfromList == "s") {
		alert("Select Invoice No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtIfSelectfromList
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=InvoiceDetails&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&month=" + month + "&year=" + year + "&txtIfSelectfromList="
				+ txtIfSelectfromList;

		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function InvoiceDetails1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
	if (flag == "success1") {
		var InvoiceDate = baseResponse.getElementsByTagName("InvoiceDate")[0].firstChild.nodeValue;
		var InvoiceAmount = baseResponse.getElementsByTagName("InvoiceAmount")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceDate.value = InvoiceDate;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceAmount.value = InvoiceAmount;
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}

*/
function saveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	var BillNo1 = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
	var BillNo = parseInt(BillNo1);
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillNo.value = BillNo + 1;

	if (flag == "success") {
		alert("Record Inserted Successfully");
		refresh();
	} else {
		alert("Record Insertion Failed");
	}
}

/*function forList(path) {
	// alert(path);
	var unitcode=document.getElementById("cmbAcc_UnitCode").value;
	winemp = window.open("Bills_Token_Register_with_SP_List.jsp?cmbAcc_UnitCode="+unitcode, "list",
			"status=1,height=550,width=1200,resizable=YES, scrollbars=yes");
	winemp.moveTo(30, 150);
	winemp.focus();
}
*/
/*function ParentDrawing(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13,
		v14, v15, v16, v17, v18, v19, v20 , v21, v22) {
	 //alert(v3);
                 
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;
	if (month <= 3) {
		var year1 = year - 1;
	} else {
		var year1 = year + 1;
	}

	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmbAcc_UnitCode.value = v1;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmbOffice_code.value = v2;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMajorType.value = v3;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillNo.value = v6;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillDate.value = v7;
//	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingNo.value = v8;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingDate.value = v9;
//	document.getElementById("txtPayeeType").length=0;
//	if(v10==4)
//	{
//		
//		var paytype=document.getElementById("txtPayeeType");
//		var opt=document.createElement("OPTION");
//		opt.value=4;
//		opt.text="Employee";
//		paytype.appendChild(opt);
//		
//	}
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeType.value = v10;
	
//	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeCode.value = v11;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalSanctionedAmount.value = v12;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalBillAmount.value = v13;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayableTo.value = v14;
        document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtEmpID_mas.value = v15;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtAcc_HeadCode.value = v17;
        //document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetProvision.value = v18;
        //document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetSpent.value = v19;
        document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefNo.value = v20;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefDate.value = v21;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.mtxtRemarks.value = v22;
	
	if (v16 == "Y") {
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[0].checked = true;
	} else {
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[1].checked = true;
	}
       	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.onsubmit.disabled = true;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.ondelete.disabled = false;
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.onupdate.disabled = false;

	var url = "../../../../../Bills_Token_Register_without_SP_GPF?command=Edit&txtBillNo="
			+ v6
			+ "&txtEmpID_mas="
			+ v15
			+ "&cmbAcc_UnitCode="
			+ v1
			+ "&cmbOffice_code="
			+ v2
			+ "&year="
			+ year
			+ "&year1="
			+ year1
			+ "&txtAcc_HeadCode=" + v17+"&procNo="+v8;
	 //alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);

}*/

/*function Edit1(baseResponse) {
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillSubType.length = "1";

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len4 = baseResponse.getElementsByTagName("billMinorTypeCode").length;
		var len = baseResponse.getElementsByTagName("BillProcessingDoneBy").length;

		for ( var i = 0; i < len4; i++) {
			var billMinorTypeCode = baseResponse
					.getElementsByTagName("billMinorTypeCode")[i].firstChild.nodeValue;
			var billMinorTypeDesc = baseResponse
					.getElementsByTagName("billMinorTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillMinorType");
			var op = document.createElement("OPTION");
			op.value = billMinorTypeCode;
			var txt = document.createTextNode(billMinorTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMinorType.value = billMinorTypeCode;
		}
		var len5 = baseResponse.getElementsByTagName("billSubTypeCode").length;
		// alert(len5);
		for ( var i = 0; i < len5; i++) {
			var billSubTypeCode = baseResponse
					.getElementsByTagName("billSubTypeCode")[i].firstChild.nodeValue;
			var billsubTypeDesc = baseResponse
					.getElementsByTagName("billsubTypeDesc")[i].firstChild.nodeValue;

			var se = document.getElementById("cboBillSubType");
			var op = document.createElement("OPTION");
			op.value = billSubTypeCode;
			var txt = document.createTextNode(billsubTypeDesc);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillSubType.value = billSubTypeCode;
		}

		var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmbMas_SL_Code.length = "1";
		for ( var i = 0; i < len; i++) {
			var BillProcessingDoneBy = baseResponse
					.getElementsByTagName("BillProcessingDoneBy")[0].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(BillProcessingDoneBy);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmbMas_SL_Code.value = empid;
		}
	}

	var flagg = baseResponse.getElementsByTagName("flagg")[0].firstChild.nodeValue;
	if (flagg == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

	//	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetProvision.value = BudgetProvided;
	//	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flagg == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}

	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
	if (flag2 == "success") {

		var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
	
	var flag_no = baseResponse.getElementsByTagName("flag_no")[0].firstChild.nodeValue;
	if (flag_no == "success") {
		document.getElementById("txtProceedingNo").length=0;
		for ( var i = 0; i <1; i++) {
			var sancid = baseResponse.getElementsByTagName("sancid")[i].firstChild.nodeValue;
			var sancno = baseResponse.getElementsByTagName("sancno")[i].firstChild.nodeValue;
			var se = document.getElementById("txtProceedingNo");
			var op = document.createElement("OPTION");
			op.value = sancid;
			var txt = document.createTextNode(sancno);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
	
}*/

function deleteeee(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;

	var txtBillNo = document.getElementById("txtBillNo").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=deleted&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtBillNo=" + txtBillNo + "&cboBillMajorType="
				+ cboBillMajorType;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	}
}

function Acc_Head_Code(path)
{
	//alert("*****");
	var url =  "../../../../../Bills_Token_Register_without_SP_GPF?command=Acc_Head_Code";
 //alert(url);
var xmlrequest = AjaxFunction();
xmlrequest.open("POST", url, true);
xmlrequest.onreadystatechange = function() {
manipulate(xmlrequest);
};
xmlrequest.send(null);
}

function jasper() {
	// alert("******");
	
	//alert(acc_head_code);
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cmbReportType = document.getElementById("cmbReportType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	//var r = confirm("Are U Sure?");
	
		var url = "../../../../../Bills_Token_Register_without_SP_GPF?command=Report&cmbReportType="+cmbReportType+"&head_code="+head_code;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}
		xmlrequest.send(null);
	
}




function deleteRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Deleted Successfully");
		refresh();
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
		refresh();
	} else {
		alert("Unable to Delete");
		refresh();
	}
}

function update(path) {
//	 alert("update");
	
	
	
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = document.getElementById("cash_month").value;
	
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	
	
	var selectnews=document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingNo;
	var txtProceedingNo=selectnews.options[selectnews.selectedIndex].text;
	
	//var txtProceedingNo =document.getElementByID("txtProceedingNo").options[document.getElementByID("txtProceedingNo").selectedIndex].text;
	//var txtProceedingNo = document
	//		.getElementById("txtProceedingNo").value;
	var txtProceedingDate = document
			.getElementById("txtProceedingDate").value;
	
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[1].value;
	}
	//var txtPayeeType = document.getElementById("txtPayeeType").value;
	//var txtPayeeCode = document.getElementById("txtPayeeCode").value;
        var txtTotalSanctionedAmount= document.getElementById("txtTotalSanctionedAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
        var txtPayableTo=document.getElementById("txtPayableTo").value;
      	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var cboOffice = document.getElementById("cboOffice").value;
//	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	//var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
/*	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;*/
	
	var sanction_id=document.getElementById("sanc_id").value;
	
	if (cboBillMajorType == "" || cboBillMajorType == 0) 
        {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMajorType
				.focus();
	} 
       
         if((document.getElementById("cboBillMinorType").length > 1) && (cboBillMinorType == "" || cboBillMinorType == 0))
        {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMinorType
				.focus();
	} 
         if((document.getElementById("cboBillSubType").length >1) && (cboBillSubType == "" || cboBillSubType == 0))
        {
               alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillSubType
				.focus();
        } else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillDate
				.focus();
	}else if (document.getElementById("txtProceedingNo").value == "") {
		alert("Enter Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingNo
				.focus();
	} else if (document.getElementById("txtProceedingDate").value == "") {
		alert("Enter Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingDate
				.focus();
	}
        else if (document.getElementById("txtTotalSanctionedAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalSanctionedAmount
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalBillAmount
				.focus();
	}  else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtAcc_HeadCode
				.focus();
	} 
//	else if (document.getElementById("txtPayeeType").value == "") {
//		alert("Enter Payee Type in the Field");
//		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeType
//				.focus();
//	}
//	else if (document.getElementById("txtPayeeCode").value == "") {
//		alert("Enter Payee Code in the Field");
//		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeCode
//				.focus();
//	}
	else if (document.getElementById("txtPayableTo").value == "") {
		alert("Enter Payable To in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayableTo
				.focus();
	} 
        else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtEmpID_mas
				.focus();
	} /*else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.focus();
	} else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefDate
				.focus();
	} else if (parseInt(txtTotalBillAmount) > parseInt(BalanceAmount)) {
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalBillAmount
				.focus();
	}*/ else {

		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtProceedingDate=" + txtProceedingDate
				+ "&txtProceedingNo=" + txtProceedingNo
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
				+ "&txtPayableTo=" +txtPayableTo
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtTotalSanctionedAmount=" + txtTotalSanctionedAmount
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode
				+ "&txtEmpID_mas=" + txtEmpID_mas + "&cboOffice=" + cboOffice
				+ "&txtRefNo=" + txtRefNo
				+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks+"&sanction_id="+sanction_id;
				var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}
}

function updateRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	if (flag == "success") {
		alert("Record Updated Successfully.");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.onsubmit.disabled=true;
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.Print.disabled=false;
		//refresh();
	} else {
		alert("Failed to update values");
	}
}

function refresh() 
        {
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMajorType.value = "0";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillSubType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillNo.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingDate.value = "";
	
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.sanc_id.value = "";
	
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalBillAmount.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalSanctionedAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtAcc_HeadCode.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtAcc_HeadDesc.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeType.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeCode.value = "";
        document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayableTo.value = "";
        
	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmbMas_SL_Code.length = "1";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtEmpID_mas.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.length = "1";
//	document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetSpent.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetProvision.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefNo.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefDate.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.mtxtRemarks.value = "";
        
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














function LoadAccountingUnitID(COMMAND)
{
	
        command_for_office = COMMAND;
        var url="../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
       // alert("command_for_office&&&&&&&&"+url+"ssssss"+command_for_office);
        var req=AjaxFunction();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        	//alert("gsfg");
          handle_loadAccountingUnitID(req);
        };        
        req.send(null);
   // alert("ebd");
}


function handle_loadAccountingUnitID(req)
{
   
    if(req.readyState==4)
    {
   
     if(req.status==200)
     {
    	 //alert("200");
   
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        
       var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
   
      
        if(flag=="success")
        { 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
                cmbAcc_UnitCode.length=0;
          
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            for(var i=0;i<option_count.length;i++)
            {  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                
                option.text=accounting_unit_name+"("+accounting_unit_id+")";
                option.value=accounting_unit_id;
                try
                {   
                    cmbAcc_UnitCode.add(option);
                }
                catch(errorObject)
                {
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
                       
        
            /** Load Accounting Office ID */ 
            if ( (command_for_office == "ONLY_UNITS") || (command_for_office=="LIST_ALL_UNITS_ONLY") || (command_for_office=="FOR_LIST_0" ) )
            {
            
            }
            else
            {
               common_LoadOffice();            
            }         
            
            
        }
        else
        {
          alert("Failed to Load Accounting Unit");
        }
                 
     }
    }
}

function sixdigit()
{
 if( document.getElementById("txtAcc_HeadCode").value!=0)
    {
        if(( document.getElementById("txtAcc_HeadCode").value).length<6)
        {
        alert("Account Head Code Shouldn't be less than 6 digit number");
        document.getElementById("txtAcc_HeadCode").value="";
        document.getElementById("txtAcc_HeadCode").focus();
        return false;
        }
    }
}
function clear_Combo(combo)
{       	    
        
         var cmbSL_Code=document.getElementById(combo.id);   
        
         cmbSL_Code.innerHTML="";
         var option=document.createElement("OPTION");
         option.text="--Select Code--";
         option.value="";
         try
         {
        	
        	 cmbSL_Code.add(option);
         }catch(errorObject)
         {
        	 cmbSL_Code.add(option,null);
         } 
}
/*
function common_LoadOffice()
{
	//alert("comes");
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;     
   // alert("unitID_val"+unitID_val);
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Bills_Token_Register_without_SP_GPF?command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
       //alert(url);
        var req=AjaxFunction();
        req.open("POST",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        }
        req.send(null);
    }     
} */




function handle_loadOffice(req)
{
  //alert("handle_loadOffice");
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
    	
      
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success")
        { 
         
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
       
            for(i=0;i<offidvalues.length;i++)
            {  //alert("i"+offidvalues.length);
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")";
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
            
             
     }
    }
}


seq=0;
function ADD_GRID()
{
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        }

       // var acc=document.getElementById("txtAcc_HeadCode").value;
       // var kk=acc.charAt(0)+acc.charAt(1);

        var tbody=document.getElementById("grid_body");
          /* if(tbody.rows.length>0)
            {
             rows=tbody.getElementsByTagName("tr");
             var txtAcc_HeadCode= document.getElementById("txtAcc_HeadCode").value;
              for(i=0;i<rows.length;i++)
               {
                    var cells=rows[i].cells;
                  
                
                    var ac_code=cells.item(1).lastChild.nodeValue;
                    var units=cells.item(3).lastChild.nodeValue;
                  //  alert("units"+units);
                    var code1= ac_code.split("-");   
                 //  alert(code1[0]);
                    if(code1[0]==900108)
                    {
                        if(units=="Accounting Units")
                        {
                        if(code1[0]==txtAcc_HeadCode){
                            var offcode=cells.item(4).lastChild.nodeValue;
                           var chkslcode= document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
                       
                           if(offcode==chkslcode)
                            {
                            alert("Already Added For this A/c unit. Choose Different Unit For 900108");
                             return false;
                            }
                            }
                        }
                    }
                }
       }*/
        
      
        var tbody=document.getElementById("grid_body");
                             
        var t=0;
        var exist=document.getElementById("txtAcc_HeadCode").value;
     
      
        
        
        var items=new Array();
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
       // if(document.Cash_Receipt_Form.rad_sub_CR_DR[0].checked==true)
         // items[2]=document.Cash_Receipt_Form.rad_sub_CR_DR[0].value;
        //else if(document.Cash_Receipt_Form.rad_sub_CR_DR[1].checked==true)
          //items[2]=document.Cash_Receipt_Form.rad_sub_CR_DR[1].value;
        //  items[2]=document.getElementById("txtPayeeType").value;
      //  items[3]=document.getElementById("txtPayeeCodeLoad").value;
       /* if(document.getElementById("cmbSL_type").value=="")
        {
        //items[4]="Not Available";
        items[4]="";//document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
        }
        else*/
      //  items[4]=document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text; 
      //  items[4]=document.getElementById("txtPayeeCode").value;
      //  items[5]=document.getElementById("cmbMas_SL_Code").value;
        
     //   items[6]=document.getElementById("txtOfficeID_mas").value;
     //   items[7]=document.getElementById("cmbSL_type").value;
        
        items[2]=document.getElementById("txtPayeeType").value;
        items[3]=document.getElementById("txtPayeeCode").value;
        items[4]=document.getElementById("txtBudgetAmount").value;
       /* items[4]=document.getElementById("txtBudgetProvision").value;
        items[5]=document.getElementById("txtBudgetSpent").value;
        
        items[6]=document.getElementById("txtRefNo").value;
        items[7]=document.getElementById("txtRefDate").value;
        items[8]=document.getElementById("txtParticular").value;*/
        
        
       
        
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        
        seq=seq+1;
     
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
     //   var br=document.createElement("br");  
    /*    var br=  document.createTextNode('\u00a0');
        cell.appendChild(br); 
        var anc1=document.createElement("input");
        anc1.type="button";
        anc1.value="Delete";
      anc1.setAttribute('onclick','javascript:DleteRow(this)');
       // anc1.href=url1;
        var txtDel=document.createTextNode("Delete");
        anc1.appendChild(txtDel);
        cell.appendChild(anc1); */
        
        mycurrent_row.appendChild(cell);
        var i=0;
        var cell2;
        
       
       
            cell2=document.createElement("TD");
           
                  var H_code=document.createElement("input");
                  H_code.type="hidden";
                  H_code.name="H_code";
                  H_code.value=items[0];
                  cell2.appendChild(H_code);
                  var currentText=document.createTextNode(items[0]+"-"+items[1]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             
                   cell2=document.createElement("TD"); 
                  var pay_type=document.createElement("input");
                  pay_type.type="hidden";
                  pay_type.name="pay_type";
                  pay_type.value=items[2];
                  cell2.appendChild(pay_type);
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                
                cell2=document.createElement("TD"); 
                var pay_code=document.createElement("input");
                pay_code.type="hidden";
                pay_code.name="pay_code";
                pay_code.value=items[3];
                cell2.appendChild(pay_code);
                 var currentText=document.createTextNode(items[3]);
                cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
              
              cell2=document.createElement("TD"); 
              var pay_amt1=document.createElement("input");
              pay_amt1.type="hidden";
              pay_amt1.name="pay_amt";
              pay_amt1.value=items[4];
              cell2.appendChild(pay_amt1);
               var currentText=document.createTextNode(items[4]);
              cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
             
            /* cell2=document.createElement("TD");
                  var Budget_Provision=document.createElement("input");
                  Budget_Provision.type="hidden";
                  Budget_Provision.name="Budget_Provision";
                  Budget_Provision.value=items[4];
                  cell2.appendChild(Budget_Provision);
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                  var Budget_Spent=document.createElement("input");
                  Budget_Spent.type="hidden";
                  Budget_Spent.name="Budget_Spent";
                  Budget_Spent.value=items[5];
                  cell2.appendChild(Budget_Spent);
                   var currentText=document.createTextNode(items[5]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
             
             cell2=document.createElement("TD"); 
                  var ref_no=document.createElement("input");
                  ref_no.type="hidden";
                  ref_no.name="ref_no";
                  ref_no.value=items[6];
                  cell2.appendChild(ref_no);
                  var currentText=document.createTextNode(items[6]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
              
                cell2=document.createElement("TD"); 
                var ref_date=document.createElement("input");
                ref_date.type="hidden";
                ref_date.name="ref_date";
                ref_date.value=items[7];
                cell2.appendChild(ref_date);
                var currentText=document.createTextNode(items[7]);
              cell2.appendChild(currentText);
              mycurrent_row.appendChild(cell2);
                
              cell2=document.createElement("TD");
             
                  var particular=document.createElement("input");
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[8];
                  cell2.appendChild(particular);
                  var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);*/

        tbody.appendChild(mycurrent_row);

    	//alert('test');
    	
    	
    	var minor=document.getElementById("cboBillMinorType").options[document.getElementById("cboBillMinorType").selectedIndex].text;
    	var sub=document.getElementById("cboBillSubType").options[document.getElementById("cboBillSubType").selectedIndex].text;
    	
    	var prono=document.getElementById("txtManualProceedingNo").value;
    	var prodate=document.getElementById("txtManualProceedingDate").value;
    	var txtPayableTo=(document.getElementById("txtPayeeCodeLoad").options[document.getElementById("txtPayeeCodeLoad").selectedIndex].text).split("-")[1];
    	
     document.getElementById("mtxtRemarks").value=minor+" - "+sub+" - "+prono+" - "+prodate+" - "+txtPayableTo;
    //alert('txtre'+document.getElementById("mtxtRemarks").value);     
         clearall();
}
function clearall()
{
    
     
    document.getElementById("txtAcc_HeadCode").value="s";
    //document.getElementById("txtAcc_HeadCode").readOnly=false;
    document.getElementById("txtAcc_HeadDesc").value="";
    
    document.getElementById("txtPayeeType").value="";
    document.getElementById("txtPayeeCode").value="";
   // document.getElementById("txtBudgetProvision").value="";
   // document.getElementById("txtBudgetSpent").value="";
   // document.getElementById("txtRefNo").value="";
   // document.getElementById("txtRefDate").value="";
    
  //  document.getElementById("txtParticular").value="";
 document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmdadd.style.display='block';
 document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmdupdate.style.display='none';
 document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmddelete.disabled=true;
}
function loadTable(scod)
{
        com_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall();
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try {document.getElementById("txtAcc_HeadCode").value=rcells.item(1).firstChild.value;}catch(e){}
       
        try{document.getElementById("txtPayeeType").value=rcells.item(2).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayeeCode").value=rcells.item(3).firstChild.value;}catch(e){}
        try{document.getElementById("txtBudgetAmount").value=rcells.item(4).firstChild.value;}catch(e){}
       // try{document.getElementById("txtAcc_HeadDesc").value=rcells.item(1).lastChild.nodeValue}catch(e){}
      //  try{document.getElementById("txtBudgetProvision").value=rcells.item(4).firstChild.value;}catch(e){}
      //  try{document.getElementById("txtBudgetSpent").value=rcells.item(5).firstChild.value;}catch(e){}
       // try{document.getElementById("txtRefNo").value=rcells.item(6).firstChild.value;}catch(e){}
        
        
        
      // try{document.getElementById("txtRefDate").value=rcells.item(7).firstChild.value;}catch(e){}
      // try{document.getElementById("txtParticular").value=rcells.item(8).firstChild.value;}catch(e){}
       
    document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmdupdate.style.display='block';
    document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmddelete.disabled=false;
    document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cmdadd.style.display='none';
}
function update_GRID()
{      
        if(document.getElementById("txtAcc_HeadCode").value.length==0)
        {
        alert("Enter A/c Head Code");
        return false;
        } 
       
       /* if(document.getElementById("txtsub_Amount").value.length==0)
        {
            alert("Enter the Amount ");
            document.getElementById("txtsub_Amount").focus();
            return false;    
        }*/
       // var exist=document.getElementById("txtAcc_HeadCode").value;
        var items=new Array();
       
        items[0]=document.getElementById("txtAcc_HeadCode").value;
        items[1]=document.getElementById("txtAcc_HeadDesc").value;
        
        items[2]=document.getElementById("txtPayeeType").value;
        items[3]=document.getElementById("txtPayeeCode").value;
        items[4]=document.getElementById("txtBudgetAmount").value;
      /*  items[4]=document.getElementById("txtBudgetProvision").value;
        items[5]=document.getElementById("txtBudgetSpent").value;
        
        items[6]=document.getElementById("txtRefNo").value;
        items[7]=document.getElementById("txtRefDate").value;
        items[8]=document.getElementById("txtParticular").value;*/
        
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
               try{rcells.item(1).firstChild.value=items[0];}catch(e){}
               try{rcells.item(1).lastChild.nodeValue=items[0]+"-"+items[1];}catch(e){}
             
                try{rcells.item(2).firstChild.value=items[2];}catch(e){}
                try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
              
                try{rcells.item(3).firstChild.value=items[3];}catch(e){}
                try{rcells.item(3).lastChild.nodeValue=items[3];}catch(e){}

               try{rcells.item(4).firstChild.value=items[4];}catch(e){}
                try{rcells.item(4).lastChild.nodeValue=items[4];}catch(e){}
                /* 
              //  try{rcells.item(5).firstChild.value=items[7];}catch(e){}
               // try{rcells.item(5).lastChild.nodeValue=items[7];}catch(e){}
            
                try{rcells.item(5).firstChild.value=items[5];}catch(e){}
                try{rcells.item(5).lastChild.nodeValue=items[5];}catch(e){}
             
                try{rcells.item(6).firstChild.value=items[6];}catch(e){}
                try{rcells.item(6).lastChild.nodeValue=items[6];}catch(e){}
                
                try{rcells.item(7).firstChild.value=items[7];}catch(e){}
                try{rcells.item(7).lastChild.nodeValue=items[7];}catch(e){}
                
                try{rcells.item(8).firstChild.value=items[8];}catch(e){}
                try{rcells.item(8).lastChild.nodeValue=items[8];}catch(e){}*/
            
            
        alert("Record Updated");
        clearall();
  }

function delete_GRID()
{
	alert(com_id);
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("grid_body");
        var r=document.getElementById(com_id);
       
        var ri=r.rowIndex;
        tbody.deleteRow(ri-1);
             clearall();
      /*    var i = com_id.parentNode.parentNode.rowIndex;
          alert("iiii ")
    document.getElementById("grid_body").deleteRow(i);*/
        /*var t=0;
           for(t=tbody.rows.length-1;t>=0;t--)
           {
           //   tbody.deleteRow(0);
           }  
           for()
        tbody.deleteRow(ri);
*/        clearall();
        }
}/*
function cash_monthss()
{
	//alert("******");
	var combo1 = document.getElementById("cash_month");
    var txtProceedingNo = combo1.options[combo1.selectedIndex].text;
    document.getElementById("cash_mont").value=txtProceedingNo;
}
*/

function funAddNew(path){

//	 alert("update");
	
	
	var detail_amt=0;
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	//var month = document.getElementById("cash_month").value;
	
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	/*if(txtBillDate.split("/")[1]==5 && txtBillDate.split("/")[2]==2015)
	{
		document.getElementById("txtBillDate").value='';
		alert('Try with May month entry Tomorrow...');
		document.getElementById("txtBillDate").focus();
	}*/
	
	/*var selectnews=document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtProceedingNo;
	var txtProceedingNo=selectnews.options[selectnews.selectedIndex].text;*/
	var txtProceedingNo=document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtManualProceedingNo.value;
	//var txtProceedingNo =document.getElementByID("txtProceedingNo").options[document.getElementByID("txtProceedingNo").selectedIndex].text;
	//var txtProceedingNo = document
	//		.getElementById("txtProceedingNo").value;
	var txtProceedingDate = document
			.getElementById("txtManualProceedingDate").value;
	
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.rdoMTC_70_Register[1].value;
	}
	//var txtPayeeType = document.getElementById("txtPayeeType").value;
	//var txtPayeeCode = document.getElementById("txtPayeeCode").value;
       var txtTotalSanctionedAmount= document.getElementById("txtTotalSanctionAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
    //   var txtPayableTo=document.getElementById("txtPayableTo").value;
     //	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	//var cboOffice = document.getElementById("cboOffice").value;
//	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	//var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
/*	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;*/
	
	//var sanction_id=document.getElementById("sanc_id").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;
	if (cboBillMajorType == "" || cboBillMajorType == 0) 
       {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMajorType
				.focus();
	} 
      
        if((document.getElementById("cboBillMinorType").length > 1) && (cboBillMinorType == "" || cboBillMinorType == 0))
       {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillMinorType
				.focus();
	} 
        if((document.getElementById("cboBillSubType").length >1) && (cboBillSubType == "" || cboBillSubType == 0))
       {
              alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboBillSubType
				.focus();
       } else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillDate
				.focus();
	}else if (document.getElementById("txtManualProceedingNo").value == "") {
		alert("Enter Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtManualProceedingNo
				.focus();
	} else if (document.getElementById("txtManualProceedingDate").value == "") {
		alert("Enter Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtManualProceedingDate
				.focus();
	}
       else if (document.getElementById("txtTotalSanctionAmount").value == "") {
		alert("Enter Total Sanctioned Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalSanctionAmount
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalBillAmount
				.focus();
	}  /*else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtAcc_HeadCode
				.focus();
	} */
//	else if (document.getElementById("txtPayeeType").value == "") {
//		alert("Enter Payee Type in the Field");
//		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeType
//				.focus();
//	}
//	else if (document.getElementById("txtPayeeCode").value == "") {
//		alert("Enter Payee Code in the Field");
//		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayeeCode
//				.focus();
//	}
/*	else if (document.getElementById("txtPayableTo").value == "") {
		alert("Enter Payable To in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtPayableTo
				.focus();
	} */
       else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtEmpID_mas
				.focus();
	} /*else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.cboOffice.focus();
	} else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefDate
				.focus();
	} else if (parseInt(txtTotalBillAmount) > parseInt(BalanceAmount)) {
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtTotalBillAmount
				.focus();
	}*/ else {

		var url = path
				+ "/Bills_Token_Register_without_SP_GPF?command=AddNew&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
			//	+ "&year=" + year + "&month=" + month 
				+ "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtManualProceedingDate=" + txtProceedingDate
				+ "&txtManualProceedingNo=" + txtProceedingNo
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
			//	+ "&txtPayableTo=" +txtPayableTo
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtTotalSanctionAmount=" + txtTotalSanctionedAmount
				//+ "&txtAcc_HeadCode=" + txtAcc_HeadCode
				+ "&txtEmpID_mas=" + txtEmpID_mas 
				//+ "&cboOffice=" + cboOffice
			//	+ "&txtRefNo=" + txtRefNo
			//	+ "&txtRefDate=" + txtRefDate +
				+"&mtxtRemarks=" + mtxtRemarks;
				//+"&sanction_id="+sanction_id;
		var t;
		/*if(document.getElementById("notgpfdetails").style.display=="none")
			 t=document.getElementById("grid_body1").rows.length;
		else if(document.getElementById("gpfdetails").style.display=="none")*/
		 t=document.getElementById("grid_body").rows.length;
		
		var slno,H_code,b_alloted,budget_spent,ref_no,ref_date,pay_type,pay_code,pay_amt;
	    
        
		  slno=new Array();
		  H_code=new Array();
		  b_alloted=new Array();
		  budget_spent=new Array();
		  ref_no=new Array();
		  ref_date=new Array();
		  pay_type=new Array();
		  pay_code=new Array();
		  pay_amt=new Array();
		
		  
		for(var k=0;k<t;k++){
			
			var j=k+1;
		
			var rcells=document.getElementById(j).cells;
	             //alert(rcells.item(2).firstChild.value);
				
				H_code[k]=rcells.item(1).firstChild.value;
				pay_amt[k]=rcells.item(4).firstChild.value;
				pay_type[k]=rcells.item(2).firstChild.value;
				pay_code[k]=rcells.item(3).firstChild.value;
				detail_amt=parseInt(detail_amt)+parseInt(pay_amt[k]);	
		}
		
		if(detail_amt!=parseInt(txtTotalSanctionedAmount)){
			alert('Check Total Sanction Amount and sum of Pay amount should be equal ... ');
			 document.getElementById("txtTotalSanctionAmount").focus();
		}else{
		url=url+"&H_code="+H_code+"&pay_amt="+pay_amt+"&pay_type="+pay_type
		+"&pay_code="+pay_code;
	
		//alert("NEW >>> "+url);
		var xmlrequest = AjaxFunction();
	
		xmlrequest.open("POST", url, true);
		document.getElementById("butSub").disabled=true;
		document.getElementById("imgfld").style.visibility = "visible";
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};

		xmlrequest.send(null);
	}
		}

}

function DleteRow(s)
{
	
    var i = s.parentNode.parentNode.rowIndex;
   
document.getElementById("grid_body").deleteRow(i-1);
	}
