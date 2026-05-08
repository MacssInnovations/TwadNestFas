//			Bills_Token_Register_without_SP			//
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

function checksan()
{
	var billdate_grid=document.getElementById("txtBillDate").value;
	var biisp_grid=billdate_grid.split("/");
	
	var manualdate=document.getElementById("txtManualProceedingDate").value;
	 var manu_pro_date=manualdate.split("/");
	
	 if(biisp_grid[2]<manu_pro_date[2])
	 {
		 alert("Bill Date Should not be less than Manual Proceeding Date");
		 document.getElementById("txtBillDate").value="";
		 document.getElementById("txtManualProceedingDate").value="";
		 
		 return false;
		
	 }
	 else if(biisp_grid[2]==manu_pro_date[2])
	 {
	
   	 if(biisp_grid[1]<manu_pro_date[1])
   	 {
   		 alert("Bill Date Should not be less than Manual Proceeding Date");
		 document.getElementById("txtBillDate").value="";
		 document.getElementById("txtManualProceedingDate").value="";
		 return false;
   	 }
   	 else if(biisp_grid[1]==manu_pro_date[1])
   	 {
   		 var billspl;
   		 //alert(biisp_grid[0].length);
   		 if(biisp_grid[0].length==2)
   		 {
   			billspl=biisp_grid[0];
   		 }
   		 else
   		 {
   			billspl="0"+biisp_grid[0];
   		 }
   		 if(billspl<manu_pro_date[0])
       	 {
   			 alert("Bill Date Should not be less than  Manual Proceeding Date");
   			 document.getElementById("txtBillDate").value="";
   			document.getElementById("txtManualProceedingDate").value="";
   			 return false;
       	 } 
   	 }
	 }
	
}

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


function loadPayeeCode_DescNew(Payee_Code){
	
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtPayeeType=document.getElementById("txtPayeeType").value;
	//alert("txtpayyee "+txtPayeeType);
	if(parseInt(txtPayeeType)==7){
		 document.getElementById("txtPayeeCode").readOnly=false;
		 document.getElementById("txtPayeeCode").value=Payee_Code;
		// alert("Type Employee Code 555.....");
		 callpd();
		 document.getElementById("payname_desig").style.display="none";
	//callpd	
	}else{
		document.getElementById("payname_desig").style.display="block";
	
	var url="../../../../../Bills_Token_Register_without_SP?command=loadPayeeCode_Desccp&cboAcc_UnitCode="+cboAcc_UnitCode+"&cboOffice_code="+cboOffice_code+
	"&txtPayeeType="+txtPayeeType;
	
   // alert(url);
    var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
  
    {
    	var res=manipulate_payee(xmlrequest); 
    	if(res==true)document.getElementById("txtPayeeCodeLoad").value=Payee_Code;
    	loadPayyCp();
    };
   
    xmlrequest.send(null);
	}
   return true;
}
function manipulate_payee(xmlrequest){


	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			
			 if(command=="loadPayeeCode_Desccp")
	            {
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
					

	            }
			
			
		}
	}
	return true;

}

function loadPayeeCode_DescCp(baseResponse) {
	
	
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
        //   alert("cid.length "+cid.length);
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
	
}

function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			if (command == "getBillMajorType") {
				 //alert("first load ");
				firstLoad(baseResponse);
			} else if (command == "getBillMinorType") {
				// alert("manipulate");
				getBillMinorType1(baseResponse);
			} else if (command == "getBillsubType") {
				// getEstimateSanctionDate
				// alert("manipulate");
				getBillsubType1(baseResponse);
			} else if (command == "calculateBudget") {
				// alert("manipulate");
				calculateBudget1(baseResponse);
			} else if (command == "loadPayyeedesc") {
				loadPayyeedesc1(baseResponse);
			} 

			else if (command == "getOffice") {
				// alert("manipulate");
				getOffice1(baseResponse);
			} else if (command == "saveFunc") {
				// alert("manipulate saveFunc");
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
			else if (command == "loadPayeeType") {
				loadPayeeType1(baseResponse);
			}
			 else if(command=="Load_MasterSL_Code")
	            {
	                load_pay_des(baseResponse);
	            }
			 else if(command=="loadAccHead")
	            {
				 loadAccHead1(baseResponse);
	            }
			
			 else if(command=="checkinvoicee")
	            {
				 var r="";
				 r=checkinvoicee1(baseResponse);
				// alert("rrrrrrrrrrr---->"+r);
				 return r;
	            }
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

function loadAccDesc(){
	  var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	  if(txtAcc_HeadCode.length>=6)
      {
        
          
          var url="../../../../../Bills_Token_Register_without_SP?command=loadAccDesc&txtAcc_HeadCode="+txtAcc_HeadCode;
  		
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
		alert('Give a Correct Head Code');
		document.getElementById("txtAcc_HeadDesc").value="";
		
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

function loadPayeeCode_Desc1(baseResponse) {
	
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		document.getElementById("txtPayeeCodeLoad_two").length=0;
		 var payeecode1=document.getElementById("txtPayeeCodeLoad_two"); 

        var items_id=new Array();
        var items_name=new Array();
        var items_name_i=new Array();
        
        var hcode=document.getElementById("txtAcc_HeadCode").value;
        var txtPayeeType=document.getElementById("txtPayeeType").value;
        if(hcode==211508)
        {
        	if(txtPayeeType!=3)
        	{
        		alert("Please Check the Payee Type Before Proceeding");	
        	}
        }
        
           var cid=baseResponse.getElementsByTagName("payeecode");
           var cname=baseResponse.getElementsByTagName("payeecodeDesc");
       
           for(var k=0;k<cid.length;k++)
           {
               items_id[k]=baseResponse.getElementsByTagName("payeecode")[k].firstChild.nodeValue;
               items_name[k]=baseResponse.getElementsByTagName("payeecodeDesc")[k].firstChild.nodeValue;
               items_name_i[k]= baseResponse.getElementsByTagName("payeecodeDesc_Load")[k].firstChild.nodeValue;
              
           }
        
           for(var k=0;k<items_id.length;k++)
           {   
                 var option=document.createElement("OPTION");
              
                 option.text=items_name_i[k];
                 option.value=items_name_i[k];
                 
                  try
                 {
                	  payeecode1.add(option);
                 }
                 catch(errorObject)
                 {
                	 payeecode1.add(option,null);
                 }
           }
           document.getElementById("one").style.display="none";
	         document.getElementById("two").style.display="block";
          // document.getElementById("payname_desig").value=items_id[0]; 

	} else if (flag == "failure") {
		document.getElementById("one").style.display="none";
        document.getElementById("two").style.display="block";
	//	document.getElementById("payname_desig").length=0;
		document.getElementById("txtPayeeCodeLoad").length=0;
		document.getElementById("txtPayeeCodeLoad_two").length=0;
		
		document.getElementById("txtPayeeCode").value="";
		alert("No payee code");
	} 
	
}
function checkinvoicee1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
			var invoice = baseResponse.getElementsByTagName("invoice")[0].firstChild.nodeValue;
			//alert(invoice);
          if(invoice=="compulse"){
        	  
        		document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value = "Y";
        	        	  return 'Y';
        	  
          }else if(invoice=="option"){
        	//  alert("option ");
        	  document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value = "N";
        	  return 'N';
          }
	} else if (flag == "failure") {
		
		//alert(" no option code");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value = "N";
		return 'N';
	} 
	
}



function loadPayyeedesc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert("flao "+flag);
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
	   		   	   document.getElementById("txtPayeeCode").value="";
	   		       var payname_desig1=document.getElementById("payname_desig");
	   		       payname_desig1.length=0;
	   		    //   clear_Combo(payname_desig1);
	   	   }
	
	
}
function load_pay_des(baseResponse)
{
	
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
         var cmbSL_Code=document.getElementById("cmbMas_SL_Code");      // value assigned to same local variable name
         
         var items_id=new Array();
         var items_name=new Array();
        
            var cid=baseResponse.getElementsByTagName("cid");
            var cname=baseResponse.getElementsByTagName("cname");
            for(var k=0;k<cid.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_name[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
               
            }
           
           clear_Combo(cmbSL_Code);
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_name[k]+"("+items_id[k]+")";
                  option.value=items_id[k];
                   try
                  {
                      cmbSL_Code.add(option);
                  }
                  catch(errorObject)
                  {
                      cmbSL_Code.add(option,null);
                  }
            }
            
            document.getElementById("cmbMas_SL_Code").value=items_id[0];
            loadName_Mas(items_name[0]);
          if(document.getElementById("cmbMas_SL_type").value==5)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state!="CR")
                alert("Office is not in working status");
           }
           
          if(document.getElementById("cmbMas_SL_type").value==7)
           {
                var state=baseResponse.getElementsByTagName("state")[0].firstChild.nodeValue;
                if(state=="DPN")
                alert("Employee in Deputation");
           }
    }
    else if(flag=="failure")
    {
        alert("No data found");
        var cmbSL_Code=document.getElementById("cmbMas_SL_Code");   // value assigned to same local variable name
        clear_Combo(cmbSL_Code);
    }
}
//Lakshmi
/*function calbillAmt(){
	 document.getElementById("txtTotalBillAmount").value=parseInt(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount.value)-
	 parseInt(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.value);
	
}*/
function callbills(){
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==2){
		document.getElementById("txtTotalBillAmount").value=parseInt(document.getElementById("txtTotalSanctionAmount").value);	
	}
}
//joe
function calbillAmt(){
	
	
	//else{
	if(parseInt(document.getElementById("txtTotalSanctionAmount").value)>parseInt(document.getElementById("txtDeductedAmount").value))
	{
	 document.getElementById("txtTotalBillAmount").value=parseInt(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount.value)-
	 parseInt(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.value);
	}else{
		alert("TotalSanctionAmount is must Greater than DeductedAmount ..... ");
		document.getElementById("txtTotalSanctionAmount").value="";
		document.getElementById("txtDeductedAmount").value="";
		document.getElementById("txtTotalBillAmount").value="";
	}
	//}
}
function loadPayeeCode_Desc(){
	
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var txtPayeeType=document.getElementById("txtPayeeType").value;
	//alert("txtpayyee "+txtPayeeType);
	if(parseInt(txtPayeeType)==7){
		 document.getElementById("txtPayeeCode").readOnly=false;
		 alert("Type Employee Code");
		 document.getElementById("one").style.display="block";
	         document.getElementById("two").style.display="none";
	       //  document.getElementById("payname_desig").style.display="none";
	         
	}else{
	
		// document.getElementById("payname_desig").style.display="block";
	var url="../../../../../Bills_Token_Register_without_SP?command=loadPayeeCode_Desc&cboAcc_UnitCode="+cboAcc_UnitCode+"&cboOffice_code="+cboOffice_code+
	"&txtPayeeType="+txtPayeeType;
	
   // alert(url);
    var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
    
    {
    	manipulate(xmlrequest); 
    };
   
    xmlrequest.send(null);
	}
   
}
function initialLoad(path) {
	//alert(path);

	var url = path+ "/Bills_Token_Register_without_SP?command=getBillMajorType";
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	};

	xmlrequest.send(null);

}
function checkinvoicee(){
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType==""||cboBillSubType=="s"){
		cboBillSubType=0;
	}
	else if(cboBillMajorType==2 && cboBillMinorType==2 && cboBillSubType==2){
		
		document.getElementById("txtNoofInvoices").value=0;
		 document.getElementById("txtNoofInvoices").readOnly=true; 
         document.getElementById("txtInvoiceReceivedDate").readOnly=true;
         document.getElementById("txtInvoiceReceivedDate").style.backgroundColor = "lightgrey";
         document.getElementById("txtNoofInvoices").style.backgroundColor = "lightgrey";
         document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value="N";
         document.getElementById("txtDeductedAmount").value=0;
         document.getElementById("txtDeductedAmount").readOnly=true;
         //document.getElementById("txtTotalBillAmount").value= document.getElementById("txtTotalSanctionAmount").value;
         //calbillAmt();
         
         
	}else{
	
	var url="../../../../../Bills_Token_Register_without_SP?command=checkinvoicee&cboAcc_UnitCode="+cboAcc_UnitCode+"&cmbOffice_code="+cboOffice_code+
	"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType;
	var rr="";
    //alert(url);
    var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
    
    {
    	rr=manipulate(xmlrequest); 
    };
   
    xmlrequest.send(null);
   // alert("iiiiiiiiii--> "+rr);
    return rr;
	}
   
    
    
}
function getOffice(path) {
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	var url = path
			+ "/Bills_Token_Register_without_SP?command=getOffice&txtEmpID_mas="
			+ txtEmpID_mas;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}

	xmlrequest.send(null);
}

/*function getOffice1(baseResponse) {
	document.getElementById("cboOffice").length = 1;
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

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
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}
}*/

function firstLoad(baseResponse) {
	//document.getElementById("cboOffice").length = 1;
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

	
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas.value = empid;

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

		/*var len6 = baseResponse.getElementsByTagName("OfficeID").length;
		for ( var i = 0; i < len6; i++) {
			var OfficeID = baseResponse.getElementsByTagName("OfficeID")[i].firstChild.nodeValue;
			var OfficeName = baseResponse.getElementsByTagName("OfficeName")[i].firstChild.nodeValue;
			var se = document.getElementById("cboOffice");
			var op = document.createElement("OPTION");
			op.value = OfficeID;
			var txt = document.createTextNode(OfficeName);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.value = OfficeID;
		}*/

	} else {
		alert("Fail to Load Bill Major Type");
	}
}

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
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_without_SP?command=calculateBudget&cboAcc_UnitCode="
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
}

function calculateBudget1(baseResponse) {
	// alert("RKsbg");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision.value = BudgetProvided;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flag == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}
}

function getBillMinorType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_without_SP?command=getBillMinorType&cboBillMajorType="
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
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = BillNo;
	} else {
		alert("Failed to Generate Bill No");
	}
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.length = 1;
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

		var len5 = baseResponse.getElementsByTagName("EstimateSanctionNo").length;
		// alert(len5);
		for ( var i = 0; i < len5; i++) {
			var EstimateSanctionNo = baseResponse
					.getElementsByTagName("EstimateSanctionNo")[i].firstChild.nodeValue;
			// alert(EstimateSanctionNo);
			var se = document.getElementById("cboEstimateSanctionNumber");
			var op = document.createElement("OPTION");
			op.value = EstimateSanctionNo;
			var txt = document.createTextNode(EstimateSanctionNo);
			op.appendChild(txt);
			se.appendChild(op);
		}

	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Bill Minor Type");
	}
}

function callpd()
{
	var txtPayeeCode1 = document.getElementById("txtPayeeCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
   
	var url="../../../../../Bills_Token_Register_without_SP?command=loadPayyeedesc&txtPayeeCode="+txtPayeeCode1+"&cmbOffice_code="+cboOffice_code;

    //alert(url);
    var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
    {
       
    	manipulate(xmlrequest);
    }   
    xmlrequest.send(null);
}

function getBillsubType(path) {
	// alert(path);
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	// alert(cboBillMajorType);
	if ((document.getElementById("cboBillMajorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMajorType").value == "s")) {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else if ((document.getElementById("cboBillMinorType").value == "")
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value <= 0)
			|| (cboPassOrderNo = document.getElementById("cboBillMinorType").value == "s")) {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_without_SP?command=getBillsubType&cboBillMajorType="
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
function getBillDetails(path)
{
	
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType==""||cboBillSubType=="s"){
		cboBillSubType=0;
	}
	var cboBillNo=document.getElementById("txtBillNo").value;
	var url= path
	+ "/Bills_Token_Register_without_SP?command=getBillDetails&cboAcc_UnitCode="+cboAcc_UnitCode+"&cmbOffice_code="+cboOffice_code+
	"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType+"&cboBillNo="+cboBillNo;
   // alert(url);
	var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
    {
    	manipulateProcess(xmlrequest); 
    };
    xmlrequest.send(null);
	}
function getBillNo(path){
	
	var cboAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cboOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType==""||cboBillSubType=="s"){
		cboBillSubType=0;
	}
	var url= path
	+ "/Bills_Token_Register_without_SP?command=getBillNo&cboAcc_UnitCode="+cboAcc_UnitCode+"&cmbOffice_code="+cboOffice_code+
	"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType;
    var xmlrequest=AjaxFunction();
    xmlrequest.open("POST",url,true); 
    xmlrequest.onreadystatechange=function()
    {
    	manipulateProcess(xmlrequest); 
    };
    xmlrequest.send(null);
}

function manipulateProcess(xmlrequest){
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
			if (command == "getBillNo") {
				
				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if (flag == "success") {
					var len4 = baseResponse.getElementsByTagName("billno").length;
					var se = document.getElementById("txtBillNo");
					se.length=0;
					var op = document.createElement("OPTION");
					op.value = "";
					op.innerHTML="--Select--";					
					se.appendChild(op);
					for ( var i = 0; i < len4; i++) {
						var se = document.getElementById("txtBillNo");
						var op = document.createElement("OPTION");
						var billCode = baseResponse
								.getElementsByTagName("billno")[i].firstChild.nodeValue;
										
						op.value = billCode;
						op.innerHTML = billCode;
						se.appendChild(op);
						
					}
			}if (flag == "failure") {
				var se = document.getElementById("txtBillNo");
				se.length=0;
				alert("No Data Found .....");
			}
		}if(command=="getBillDetails"){
			var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if (flag == "success") {
			loadDetails(baseResponse);
			}	if (flag == "failure") {
				alert("No Data Found .....");
			}
		}
	}
}
}

function loadDetails(baseResponse){
	
	var  len=baseResponse.getElementsByTagName("Accounting_Unit_Id").length;
//	alert("length"+len);
	var Payee_Code=null;

	var  Accounting_Unit_Id=baseResponse.getElementsByTagName("Accounting_Unit_Id")[0].firstChild.nodeValue;
					var ACCOUNTING_UNIT_OFFICE_ID=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[0].firstChild.nodeValue;
					var Cashbook_Year=baseResponse.getElementsByTagName("Cashbook_Year")[0].firstChild.nodeValue;
					var Cashbook_Month=baseResponse.getElementsByTagName("Cashbook_Month")[0].firstChild.nodeValue;
					var Bill_No=baseResponse.getElementsByTagName("Bill_No")[0].firstChild.nodeValue;
					var SANCTION_PROC_NO=baseResponse.getElementsByTagName("SANCTION_PROC_NO")[0].firstChild.nodeValue;
					
					var BILL_DATE=baseResponse.getElementsByTagName("BILL_DATE")[0].firstChild.nodeValue;
					if(BILL_DATE!=null){
					var arry_billDate=BILL_DATE.split('-');
					document.getElementById("txtBillDate").value=arry_billDate[2]+"/"+arry_billDate[1]+"/"+arry_billDate[0];
					}else{document.getElementById("txtBillDate").value="";}
					var Bill_Processing_Done_By=baseResponse.getElementsByTagName("Bill_Processing_Done_By")[0].firstChild.nodeValue;
			
					document.getElementById("txtEmpID_mas").value=Bill_Processing_Done_By;
					var ACCOUNT_HEAD_CODE=baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
					//loadAccHead();

						
					if (Cashbook_Month == 4 || Cashbook_Month >4 ) {
		fin_year = Cashbook_Year + "-" + Cashbook_Year.substring(2,4) + 1;
	} else if (Cashbook_Month == 3 || Cashbook_Month< 3) {
		fin_year = Cashbook_Year - 1 + "-" + Cashbook_Year.substring(2,4);
	} else {
		alert('error');
	}
					//alert(">>> "+ACCOUNT_HEAD_CODE+Cashbook_Month+Cashbook_Year+fin_year);
					loadAccHeadNew(fin_year,ACCOUNT_HEAD_CODE);
				
					var Remarks=baseResponse.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
					document.getElementById("mtxtRemarks").value=Remarks; 
					
					var Bill_Scrutiny_Done=baseResponse.getElementsByTagName("Bill_Scrutiny_Done")[0].firstChild.nodeValue;
					var Bill_Scrutiny_By=baseResponse.getElementsByTagName("Bill_Scrutiny_By")[0].firstChild.nodeValue;
					var BILL_SCRUTINY_DATE=baseResponse.getElementsByTagName("BILL_SCRUTINY_DATE")[0].firstChild.nodeValue;
					var Status=baseResponse.getElementsByTagName("Status")[0].firstChild.nodeValue;
					var Bill_Minor_Type_Code=baseResponse.getElementsByTagName("Bill_Minor_Type_Code")[0].firstChild.nodeValue;
					var BILL_MAJOR_TYPE=baseResponse.getElementsByTagName("BILL_MAJOR_TYPE")[0].firstChild.nodeValue;
					var Bill_Sub_Type_Code=baseResponse.getElementsByTagName("Bill_Sub_Type_Code")[0].firstChild.nodeValue;
					//document.getElementById("cboBillMajorType").value=BILL_MAJOR_TYPE;
					//document.getElementById("cboBillMinorType").value=Bill_Minor_Type_Code;
					//document.getElementById("cboBillSubType").value=Bill_Sub_Type_Code;
					
					
					var Proceeding_Recd_Date=baseResponse.getElementsByTagName("Proceeding_Recd_Date")[0].firstChild.nodeValue;
					var Invoice_Received_Date=baseResponse.getElementsByTagName("Invoice_Received_Date")[0].firstChild.nodeValue;
					if(BILL_DATE!=null){
						var arry_Invoice_Received_Date=Invoice_Received_Date.split('-');
						document.getElementById("txtInvoiceReceivedDate").value=arry_Invoice_Received_Date[2]+"/"+arry_Invoice_Received_Date[1]+"/"+arry_Invoice_Received_Date[0];
						}else{document.getElementById("txtInvoiceReceivedDate").value="";}
					var No_Ofinvoices=baseResponse.getElementsByTagName("No_Ofinvoices")[0].firstChild.nodeValue;
					document.getElementById("txtNoofInvoices").value=No_Ofinvoices;
					var PAYEE_TYPE_CODE=baseResponse.getElementsByTagName("PAYEE_TYPE_CODE")[0].firstChild.nodeValue;
					var Payee_Code=baseResponse.getElementsByTagName("Payee_Code")[0].firstChild.nodeValue;
					document.getElementById("txtPayeeType").value=PAYEE_TYPE_CODE;
					//alert("MMMM "+Payee_Code);
					
				
					//alert("????? value "+document.getElementById("txtPayeeCodeLoad").value);
					
					//loadPayy();
					
					var Total_Sanctioned_Amount=baseResponse.getElementsByTagName("Total_Sanctioned_Amount")[0].firstChild.nodeValue;
					document.getElementById("txtTotalSanctionAmount").value=Total_Sanctioned_Amount;
					var Total_Bill_Amount=baseResponse.getElementsByTagName("Total_Bill_Amount")[0].firstChild.nodeValue;
					document.getElementById("txtTotalBillAmount").value=Total_Bill_Amount;
					var Mtc70entry=baseResponse.getElementsByTagName("Mtc70entry")[0].firstChild.nodeValue;
					if(Mtc70entry=='Y')
						document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].checked = true;
					if(Mtc70entry=='N')
						document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].checked = true;
					var REF_NO=baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
					document.getElementById("txtRefNo").value=REF_NO;
					var Ref_Date=baseResponse.getElementsByTagName("Ref_Date")[0].firstChild.nodeValue;
					
					if(Ref_Date!=null){
						var array_Ref_Date=Ref_Date.split('-');
						document.getElementById("txtRefDate").value=array_Ref_Date[2]+"/"+array_Ref_Date[1]+"/"+array_Ref_Date[0];
						}else{
							document.getElementById("txtRefDate").value="";
						}
					var Drawing_Officer_Approve_Date=baseResponse.getElementsByTagName("Drawing_Officer_Approve_Date")[0].firstChild.nodeValue;
					var TREASURY_VERIFY_DATE=baseResponse.getElementsByTagName("TREASURY_VERIFY_DATE")[0].firstChild.nodeValue;
					var Dor_By_Pre_Audit=baseResponse.getElementsByTagName("Dor_By_Pre_Audit")[0].firstChild.nodeValue;
					var Pre_Audit_Received_By=baseResponse.getElementsByTagName("Pre_Audit_Received_By")[0].firstChild.nodeValue;
					var PRE_AUDIT_BY=baseResponse.getElementsByTagName("PRE_AUDIT_BY")[0].firstChild.nodeValue;
					var Pre_Audit_Date=baseResponse.getElementsByTagName("Pre_Audit_Date")[0].firstChild.nodeValue;
					var Bill_Approved=baseResponse.getElementsByTagName("Bill_Approved")[0].firstChild.nodeValue;
					var Date_Sent_To_Treasury_Section=baseResponse.getElementsByTagName("Date_Sent_To_Treasury_Section")[0].firstChild.nodeValue;
					var Pre_Audit_Remarks=baseResponse.getElementsByTagName("Pre_Audit_Remarks")[0].firstChild.nodeValue;
					var Budget_Provision=baseResponse.getElementsByTagName("Budget_Provision")[0].firstChild.nodeValue;
					document.getElementById("txtBudgetProvision").value=Budget_Provision;
					var Budget_So_Far_Spent=baseResponse.getElementsByTagName("Budget_So_Far_Spent")[0].firstChild.nodeValue;
					document.getElementById("txtBudgetSpent").value=Budget_So_Far_Spent;
					var Manual_Proceeding_No=baseResponse.getElementsByTagName("Manual_Proceeding_No")[0].firstChild.nodeValue;
					document.getElementById("txtManualProceedingNo").value=Manual_Proceeding_No;
					var Manual_Proceeding_Date=baseResponse.getElementsByTagName("Manual_Proceeding_Date")[0].firstChild.nodeValue;
					if(Manual_Proceeding_Date!=null){
					var array_ManProDate=Manual_Proceeding_Date.split('-');
					document.getElementById("txtManualProceedingDate").value=array_ManProDate[2]+"/"+array_ManProDate[1]+"/"+array_ManProDate[0];
					}else{
						document.getElementById("txtManualProceedingDate").value="";
					}
					var Mtc_70_Register_Date=baseResponse.getElementsByTagName("Mtc_70_Register_Date")[0].firstChild.nodeValue;
					var Deducted_Amount=baseResponse.getElementsByTagName("Deducted_Amount")[0].firstChild.nodeValue;
					document.getElementById("txtDeductedAmount").value=Deducted_Amount;
					var Pass_Order_Date=baseResponse.getElementsByTagName("Pass_Order_Date")[0].firstChild.nodeValue;
					var Pass_Order_By=baseResponse.getElementsByTagName("Pass_Order_By")[0].firstChild.nodeValue;
					var Pass_Order_Amount=baseResponse.getElementsByTagName("Pass_Order_Amount")[0].firstChild.nodeValue;
					var Drawing_Officer_Code=baseResponse.getElementsByTagName("Drawing_Officer_Code")[0].firstChild.nodeValue;
					var Reason_For_Reject=baseResponse.getElementsByTagName("Reason_For_Reject")[0].firstChild.nodeValue;
					var PAYABLE_TO=baseResponse.getElementsByTagName("PAYABLE_TO")[0].firstChild.nodeValue;
					var Memo_Entry=baseResponse.getElementsByTagName("Memo_Entry")[0].firstChild.nodeValue;
					var Memo_Updated_Date=baseResponse.getElementsByTagName("Memo_Updated_Date")[0].firstChild.nodeValue;
				//	alert("BILL_DATE csfs>>> "+BILL_DATE);
					loadPayeeCode_DescNew(Payee_Code);	
					//alert(Payee_Code+"payee code");
					if(Memo_Entry=='Y')
						{
						alert('Not allowed');
						//document.getElementById("onupdate").style.display="none";
						document.frm_BillTokenRegisterEntry_WithoutProceeding.onupdate.disabled = true;
						
						}else{
							document.frm_BillTokenRegisterEntry_WithoutProceeding.onupdate.disabled = false;
						}
	
//alert("fun " +loadPayeeCode_DescNew());
/*	if(loadPayeeCode_DescNew(Payee_Code)==true){
		alert('y');
		document.getElementById("txtPayeeCodeLoad").value=Payee_Code;
	}*/
	//document.getElementById("txtPayeeCode").value=Payee_Code;
	}
function loadAccHeadNew(fin_year,ACCOUNT_HEAD_CODE){
	//alert("fin_year >> "+fin_year);
	
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	if(cboBillSubType==""||cboBillSubType=="s"){
		cboBillSubType=0;
	}
	
	/*  var currentTime = new Date();
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
               var finanical_yr=fin_year_from+"-"+ssyr1;*/
                
               var url="../../../../../Bills_Token_Register_without_SP?command=loadAccHead"+
           	"&cboBillMajorType="+cboBillMajorType+"&cboBillMinorType="+cboBillMinorType+"&cboBillSubType="+cboBillSubType+"&finanical_yr="+fin_year;
	
	


var xmlrequest = AjaxFunction();
xmlrequest.open("POST", url, true);
xmlrequest.onreadystatechange = function() {
	var res=manipulate_head(xmlrequest);
	//alert("res>> "+res);
	if(res==true)
		document.getElementById("txtAcc_HeadCode").value=ACCOUNT_HEAD_CODE;
	
	loadAccDesc();
}

xmlrequest.send(null);
return true;
}


function manipulate_head(xmlrequest)
{
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			// alert("manipulate-command:--->>>"+command);

			if (command == "loadAccHead") {

				var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.length = 1;
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
		}
	}
	return true;
}

function Edit_fun(path){


	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	var txtManualProceedingNo = document
			.getElementById("txtManualProceedingNo").value;
	var txtManualProceedingDate = document
			.getElementById("txtManualProceedingDate").value;
	var txtInvoiceReceivedDate = document
			.getElementById("txtInvoiceReceivedDate").value;
	var txtNoofInvoices = document.getElementById("txtNoofInvoices").value;
	var rdoMTC_70_Register=null;
	
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].value;
	}
	var txtTotalSanctionAmount = document.getElementById("txtTotalSanctionAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtPayeeType = document.getElementById("txtPayeeType").value;
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	//var cboOffice = document.getElementById("cboOffice").value;
	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;

	var BalanceAmount = (parseInt(txtBudgetProvision) - parseInt(txtBudgetSpent));
	if (cboBillMajorType == "" || cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else if (cboBillMinorType == "" || cboBillMinorType == "s") {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType
				.focus();
	} /*else if (cboBillSubType == "" || cboBillSubType == "s") {
		
		
	var bill_sub_type_applica0=	document.getElementById("bill_sub_type_applica").value;
		
	if(){
	}else{
	}
		alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType
				.focus();
		
		
	}*/ /*else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.focus();
	}*/ else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate
				.focus();
	} else if (document.getElementById("txtManualProceedingNo").value == "") {
		alert("Enter Manual Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingNo
				.focus();
	} else if (document.getElementById("txtManualProceedingDate").value == "") {
		alert("Enter Manual Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingDate
				.focus();
	} /*else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
		
		alert("Enter Invoice Received Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
				.focus();
	} else if (document.getElementById("txtNoofInvoices").value == "") {
		
		alert("Enter No of Invoices in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
				.focus();
	} else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	}*/
	else if (document.getElementById("txtTotalSanctionAmount").value == "") {
		alert("Enter Total Sanction Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	}
	else if (document.getElementById("txtDeductedAmount").value == "") {
		alert("Enter Deducted Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount
				.focus();
	} else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType
				.focus();
	} else if (document.getElementById("txtPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas
				.focus();
	} /*else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.focus();
	} */else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate
				.focus();
	} /*else if (parseInt(txtTotalBillAmount) > parseInt(BalanceAmount)) {
		alert("txtTotalBillAmount "+txtTotalBillAmount+" > "+" BalanceAmount ");
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	} else if (parseInt(txtTotalBillAmount) < parseInt(txtDeductedAmount)) {
		alert("Deducted Amount Should Less than Total Bill Amount");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.focus();
	} */
	/*else if (parseInt(txtTotalSanctionAmount) > parseInt(BalanceAmount)) {
		alert("txtTotalSanctionAmount "+txtTotalSanctionAmount+" > "+BalanceAmount+" BalanceAmount ");
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount.focus();
	} else if (parseInt(txtTotalSanctionAmount) < parseInt(txtDeductedAmount)) {
		alert("Deducted Amount Should Less than Total Sanction Amount");
		//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.focus();
	}*/
	
	else {
		if (parseInt(txtTotalSanctionAmount) > parseInt(BalanceAmount)) {
			alert("txtTotalSanctionAmount "+txtTotalSanctionAmount+" > "+BalanceAmount+" BalanceAmount ");
			alert("Total Bill Amount is greater than Balance Amount in the Current Year");
			//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount.focus();
		} else if (parseInt(txtTotalSanctionAmount) < parseInt(txtDeductedAmount)) {
			alert("Deducted Amount Should Less than Total Sanction Amount");
			//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.focus();
		}
		//alert("first   "+checkinvoicee());
		//var g=checkinvoicee();
			
			//var bill_sub_type_applica0=	document.getElementById("bill_sub_type_applica").value;
		var appl=document.frm_BillTokenRegisterEntry_WithoutProceeding.bill_sub_type_applica.value;
		//var appl1=document.getElementById("bill_sub_type_applica").value;
			//alert("appl-->"+appl);	
			
				if (cboBillSubType == "" || cboBillSubType == "s") {
				alert("Select Bill Sub Type in the Field");
				document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType
						.focus();
				}else{
								
					var flag=document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value;
					//cboBillSubType=0;
					
					//alert("flag "+flag);
				
						
						 if (document.getElementById("txtNoofInvoices").value == ""||document.getElementById("txtNoofInvoices").value==0) {
							
							alert("Enter valid No of Invoices in the Field");
							document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
									.focus();
						}
					
								
							 else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
									
									alert("Enter Invoice Received Date in the Field");
									document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
											.focus();
								}else{
									var url = path
									+ "/Bills_Token_Register_without_SP?command=EditFunc&cmbAcc_UnitCode="
									+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
									+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
									+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
									+ "&cboBillSubType=" + cboBillSubType +
									"&txtBillNo="+txtBillNo+"&txtBillDate=" + txtBillDate
									+ "&txtManualProceedingDate=" + txtManualProceedingDate
									+ "&txtManualProceedingNo=" + txtManualProceedingNo
									+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
									+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
									+ "&txtNoofInvoices=" + txtNoofInvoices
									+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
									+ "&txtDeductedAmount=" + txtDeductedAmount
									+ "&txtTotalBillAmount=" + txtTotalBillAmount
									+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
									+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
									+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
									+ "&txtBudgetProvision=" + txtBudgetProvision
									+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
									+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
							// alert(url);
							var xmlrequest = AjaxFunction();
							xmlrequest.open("POST", url, true);
							xmlrequest.onreadystatechange = function() {
								if (xmlrequest.readyState == 4) {
									if (xmlrequest.status == 200) {

										var baseResponse = xmlrequest.responseXML
												.getElementsByTagName("response")[0];

										var tagCommand = baseResponse.getElementsByTagName("command")[0];

										var command = tagCommand.firstChild.nodeValue;
										//alert("testing >>> "+command);
										if (command == "EditFunc") {
											var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
											if (flag == "success"){
											alert("Updated Successfully ... ");
											refresh();}
											else
												{alert("Not Updated Successfully ... ");}
										}
									}
								}
							
										
										
							};
								}
							
				}
			

		xmlrequest.send(null);
	}
		

}


function getBillsubType1(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//alert("flag "+flag);
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.length = 1;
	if (flag == "success") {
	var	billSubTypeCode_app2=baseResponse.getElementsByTagName("billSubTypeCode_app")[0].firstChild.nodeValue;
	document.getElementById("bill_sub_type_applica").value =billSubTypeCode_app2;
	
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
		
		
		//alert("Record Does Not Exist");
	} else if (flag == "NotApply") {
		var billSubTypeCode_app1 = baseResponse.getElementsByTagName("billSubTypeCode_app")[0].firstChild.nodeValue;
		document.getElementById("bill_sub_type_applica").value =billSubTypeCode_app1;
		//alert("Fail to Load Bill Minor Type");
	}else {
		
		alert("Fail to Load Bill Minor Type");
	}
}
function IVno(path) {
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].checked == true) {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[0].value;
	} else {
		rdoInvoiceEntryOption = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoInvoiceEntryOption[1].value;
	}

	if (rdoInvoiceEntryOption == "Entry") {
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.disabled = true;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.disabled = false;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.value = "";
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = "";
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = "";
	} else {
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.disabled = true;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.disabled = false;

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
				+ "/Bills_Token_Register_without_SP?command=IVno&cmbAcc_UnitCode="
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
                
               var url="../../../../../Bills_Token_Register_without_SP?command=loadAccHead"+
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
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.length = 1;
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
function InvoiceDetails(path) {
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
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList
				.focus();
	} else {
		var url = path
				+ "/Bills_Token_Register_without_SP?command=InvoiceDetails&cmbAcc_UnitCode="
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
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = InvoiceDate;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = InvoiceAmount;
	} else if (flag == "NoData") {
		alert("Record Does Not Exist");
	} else {
		alert("Fail to Load Invoice No");
	}
}

function saveFunc(path) {

	 //alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	// var cboBillMinorDESc=document.getElementById("cboBillMinorType").options[document.getElementById("cboBillMinorType").selectedIndex].text;
   //  alert("cboBillMinorDESc  "+cboBillMinorDESc);
	var cboBillSubType = document.getElementById("cboBillSubType").value;
//	 var cboBillSubDESc=document.getElementById("cboBillSubType").options[document.getElementById("cboBillSubType").selectedIndex].text;
  //   alert("cboBillSubDESc  "+cboBillSubDESc);
	//var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	var txtManualProceedingNo = document
			.getElementById("txtManualProceedingNo").value;
	var txtManualProceedingDate = document
			.getElementById("txtManualProceedingDate").value;
	var txtInvoiceReceivedDate = document
			.getElementById("txtInvoiceReceivedDate").value;
	var txtNoofInvoices = document.getElementById("txtNoofInvoices").value;
	
	
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].value;
	}
	var txtTotalSanctionAmount = document.getElementById("txtTotalSanctionAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtPayeeType = document.getElementById("txtPayeeType").value;
	// var PayeeTypeDESc=document.getElementById("txtPayeeType").options[document.getElementById("txtPayeeType").selectedIndex].text;
     ///alert("PayeeTypeDESc  "+PayeeTypeDESc);
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;

	
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	//var cboOffice = document.getElementById("cboOffice").value;
	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;

	
	if (cboBillMajorType == "" || cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else if (cboBillMinorType == "" || cboBillMinorType == "s") {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType
				.focus();
	}else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate
				.focus();
	} else if (document.getElementById("txtManualProceedingNo").value == "") {
		alert("Enter Manual Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingNo
				.focus();
	} else if (document.getElementById("txtManualProceedingDate").value == "") {
		alert("Enter Manual Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingDate
				.focus();
	} 
	else if (document.getElementById("txtTotalSanctionAmount").value == "") {
		alert("Enter Total Sanction Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	}
	else if (document.getElementById("txtDeductedAmount").value == "") {
		alert("Enter Deducted Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount
				.focus();
	} else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType
				.focus();
	} else if (document.getElementById("txtPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas
				.focus();
	} /*else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.focus();
	} *//*else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent
				.focus();
	} */else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate
				.focus();
	} /*else if (parseInt(txtTotalBillAmount) > parseInt(BalanceAmount)) {
		alert("txtTotalBillAmount "+txtTotalBillAmount+" > "+" BalanceAmount ");
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	} else if (parseInt(txtTotalBillAmount) < parseInt(txtDeductedAmount)) {
		alert("Deducted Amount Should Less than Total Bill Amount");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.focus();
	} */
	/*else if (parseInt(txtTotalSanctionAmount) > parseInt(BalanceAmount)) {
		alert("txtTotalSanctionAmount "+txtTotalSanctionAmount+" > "+" BalanceAmount ");
		alert("Total Bill Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount
				.focus();
	} else if (parseInt(txtTotalSanctionAmount) < parseInt(txtDeductedAmount)) {
		alert("Deducted Amount Should Less than Total Sanction Amount");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.focus();
	}*/
	
	else {
		
		var BalanceAmount = parseInt(txtBudgetProvision) - parseInt(txtBudgetSpent);
		alert("BalanceAmount  "+BalanceAmount );
		
		if (parseInt(txtTotalSanctionAmount) > parseInt(BalanceAmount)) {
			alert("txtTotalSanctionAmount "+txtTotalSanctionAmount+" > "+" BalanceAmount ");
			alert("Total Bill Amount is greater than Balance Amount in the Current Year");
			//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount.focus();
		} else if (parseInt(txtTotalSanctionAmount) < parseInt(txtDeductedAmount)) {
			alert("Deducted Amount Should Less than Total Sanction Amount");
			//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.focus();
		}
		var appl=document.frm_BillTokenRegisterEntry_WithoutProceeding.bill_sub_type_applica.value;
			
			if(appl=="Y"){
				if (cboBillSubType == "" || cboBillSubType == "s") {
				alert("Select Bill Sub Type in the Field");
				document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType
						.focus();
				}else{
								
					var flag=document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value;
					
					if(flag=="Y"){
						
						 if (document.getElementById("txtNoofInvoices").value == ""||document.getElementById("txtNoofInvoices").value==0) {
							
							alert("Enter valid No of Invoices in the Field");
							document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
									.focus();
						}
					
								
							 else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
									
									alert("Enter Invoice Received Date in the Field");
									document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
											.focus();
								}else{
									
									var billdate_grid=document.getElementById("txtBillDate").value;
									var biisp_grid=billdate_grid.split("/");
									
									var manualdate=document.getElementById("txtManualProceedingDate").value;
									 var manu_pro_date=manualdate.split("/");
									
									 if(biisp_grid[2]<manu_pro_date[2])
									 {
										 alert("Bill Date Should not be less than Manual Proceeding Date");
										 document.getElementById("txtBillDate").value="";
										 return false;
										
									 }
									 else if(biisp_grid[2]==manu_pro_date[2])
									 {
									
								   	 if(biisp_grid[1]<manu_pro_date[1])
								   	 {
								   		 alert("Bill Date Should not be less than Manual Proceeding Date");
										 document.getElementById("txtBillDate").value="";
										 return false;
								   	 }
								   	 else if(biisp_grid[1]==manu_pro_date[1])
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
								   		 if(billspl<manu_pro_date[0])
								       	 {
								   			 alert("Bill Date Should not be less than  Manual Proceeding Date");
								   			 document.getElementById("txtBillDate").value="";
								   			 return false;
								       	 } 
								   	 }
									 }
									
									var url = path
									+ "/Bills_Token_Register_without_SP?command=saveFunc&cmbAcc_UnitCode="
									+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
									+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
									+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
									+ "&cboBillSubType=" + cboBillSubType +
									"&txtBillDate=" + txtBillDate
									+ "&txtManualProceedingDate=" + txtManualProceedingDate
									+ "&txtManualProceedingNo=" + txtManualProceedingNo
									+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
									+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
									+ "&txtNoofInvoices=" + txtNoofInvoices
									+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
									+ "&txtDeductedAmount=" + txtDeductedAmount
									+ "&txtTotalBillAmount=" + txtTotalBillAmount
									+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
									+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
									+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
									+ "&txtBudgetProvision=" + txtBudgetProvision
									+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
									+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
								//	+"&cboBillSubDESc="+cboBillSubDESc+"&cboBillMinorDESc="+cboBillMinorDESc
									//+"&PayeeTypeDESc="+PayeeTypeDESc;
							// alert(url);
							var xmlrequest = AjaxFunction();
							xmlrequest.open("POST", url, true);
							xmlrequest.onreadystatechange = function() {
								manipulate(xmlrequest);
							};
								}
							
					
					}else if(flag=="N") {
						
						var billdate_grid=document.getElementById("txtBillDate").value;
						var biisp_grid=billdate_grid.split("/");
						
						var manualdate=document.getElementById("txtManualProceedingDate").value;
						 var manu_pro_date=manualdate.split("/");
						
						 if(biisp_grid[2]<manu_pro_date[2])
						 {
							 alert("Bill Date Should not be less than Manual Proceeding Date");
							 document.getElementById("txtBillDate").value="";
							 return false;
							
						 }
						 else if(biisp_grid[2]==manu_pro_date[2])
						 {
						
					   	 if(biisp_grid[1]<manu_pro_date[1])
					   	 {
					   		 alert("Bill Date Should not be less than Manual Proceeding Date");
							 document.getElementById("txtBillDate").value="";
							 return false;
					   	 }
					   	 else if(biisp_grid[1]==manu_pro_date[1])
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
					   		 if(billspl<manu_pro_date[0])
					       	 {
					   			 alert("Bill Date Should not be less than  Manual Proceeding Date");
					   			 document.getElementById("txtBillDate").value="";
					   			 return false;
					       	 } 
					   	 }
						 }

						var url = path
								+ "/Bills_Token_Register_without_SP?command=saveFunc&cmbAcc_UnitCode="
								+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
								+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
								+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
								+ "&cboBillSubType=" + cboBillSubType +
								"&txtBillDate=" + txtBillDate
								+ "&txtManualProceedingDate=" + txtManualProceedingDate
								+ "&txtManualProceedingNo=" + txtManualProceedingNo
								+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
								+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
								+ "&txtNoofInvoices=" + txtNoofInvoices
								/*+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount*/
								+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount
								+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
								+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
								+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
								+ "&txtBudgetProvision=" + txtBudgetProvision
								+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
								+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
								//+"&cboBillSubDESc="+cboBillSubDESc+"&cboBillMinorDESc="+cboBillMinorDESc;
						// alert(url);
						var xmlrequest = AjaxFunction();
						xmlrequest.open("POST", url, true);
						xmlrequest.onreadystatechange = function() {
							manipulate(xmlrequest);
						}
					}
					
				
				}
			}else{
				//alert("enter into else ");
				
				var flag=document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value;
				cboBillSubType=0;
				
				//alert("flag "+flag);
				if(flag=="Y"){
					//alert("if...");
					 if (document.getElementById("txtNoofInvoices").value == ""||document.getElementById("txtNoofInvoices").value==0) {
						
						alert("Enter valid No of Invoices in the Field");
						document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
								.focus();
					}
					// else if (document.getElementById("txtNoofInvoices").value >0) {
							
						 else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
								
								alert("Enter Invoice Received Date in the Field");
								document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
										.focus();
							}else{
								
								var billdate_grid=document.getElementById("txtBillDate").value;
								var biisp_grid=billdate_grid.split("/");
								
								var manualdate=document.getElementById("txtManualProceedingDate").value;
								 var manu_pro_date=manualdate.split("/");
								
								 if(biisp_grid[2]<manu_pro_date[2])
								 {
									 alert("Bill Date Should not be less than Manual Proceeding Date");
									 document.getElementById("txtBillDate").value="";
									 return false;
									
								 }
								 else if(biisp_grid[2]==manu_pro_date[2])
								 {
								
							   	 if(biisp_grid[1]<manu_pro_date[1])
							   	 {
							   		 alert("Bill Date Should not be less than Manual Proceeding Date");
									 document.getElementById("txtBillDate").value="";
									 return false;
							   	 }
							   	 else if(biisp_grid[1]==manu_pro_date[1])
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
							   		 if(billspl<manu_pro_date[0])
							       	 {
							   			 alert("Bill Date Should not be less than  Manual Proceeding Date");
							   			 document.getElementById("txtBillDate").value="";
							   			 return false;
							       	 } 
							   	 }
								 }
								var url = path
								+ "/Bills_Token_Register_without_SP?command=saveFunc&cmbAcc_UnitCode="
								+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
								+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
								+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
								+ "&cboBillSubType=" + cboBillSubType +
								"&txtBillDate=" + txtBillDate
								+ "&txtManualProceedingDate=" + txtManualProceedingDate
								+ "&txtManualProceedingNo=" + txtManualProceedingNo
								+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
								+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
								+ "&txtNoofInvoices=" + txtNoofInvoices
								/*+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount*/
								+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount
								+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
								+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
								+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
								+ "&txtBudgetProvision=" + txtBudgetProvision
								+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
								+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
						// alert(url);
						var xmlrequest = AjaxFunction();
						xmlrequest.open("POST", url, true);
						xmlrequest.onreadystatechange = function() {
							manipulate(xmlrequest);
						};
							}
										
				}else if(flag=="N") {

					var billdate_grid=document.getElementById("txtBillDate").value;
					var biisp_grid=billdate_grid.split("/");
					
					var manualdate=document.getElementById("txtManualProceedingDate").value;
					 var manu_pro_date=manualdate.split("/");
					
					 if(biisp_grid[2]<manu_pro_date[2])
					 {
						 alert("Bill Date Should not be less than Manual Proceeding Date");
						 document.getElementById("txtBillDate").value="";
						 return false;
						
					 }
					 else if(biisp_grid[2]==manu_pro_date[2])
					 {
					
				   	 if(biisp_grid[1]<manu_pro_date[1])
				   	 {
				   		 alert("Bill Date Should not be less than Manual Proceeding Date");
						 document.getElementById("txtBillDate").value="";
						 return false;
				   	 }
				   	 else if(biisp_grid[1]==manu_pro_date[1])
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
				   		 if(billspl<manu_pro_date[0])
				       	 {
				   			 alert("Bill Date Should not be less than  Manual Proceeding Date");
				   			 document.getElementById("txtBillDate").value="";
				   			 return false;
				       	 } 
				   	 }
					 }
					
					var url = path
							+ "/Bills_Token_Register_without_SP?command=saveFunc&cmbAcc_UnitCode="
							+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
							+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
							+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
							+ "&cboBillSubType=" + cboBillSubType +
							"&txtBillDate=" + txtBillDate
							+ "&txtManualProceedingDate=" + txtManualProceedingDate
							+ "&txtManualProceedingNo=" + txtManualProceedingNo
							+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
							+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
							+ "&txtNoofInvoices=" + txtNoofInvoices
							+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
							+ "&txtDeductedAmount=" + txtDeductedAmount
							+ "&txtTotalBillAmount=" + txtTotalBillAmount
							+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
							+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
							+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
							+ "&txtBudgetProvision=" + txtBudgetProvision
							+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
							+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
					// alert(url);
					var xmlrequest = AjaxFunction();
					xmlrequest.open("POST", url, true);
					xmlrequest.onreadystatechange = function() {
						manipulate(xmlrequest);
					}
				}
				
			}

		xmlrequest.send(null);
	}
		
}

function saveFunc1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	var BillNo1 = baseResponse.getElementsByTagName("BillNo")[0].firstChild.nodeValue;
	//var BillNo = parseInt(BillNo1);
	//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = BillNo + 1;

	if (flag == "success") {
		alert("Record Inserted Successfully");
window.location.reload(true);
		//refresh();
	} else {
		alert("Record Insertion Failed");
	}
}

function forList(path) {
	 //alert(path);
	 var unit_id=document.getElementById("cmbAcc_UnitCode").value;
     var office_id=document.getElementById("cmbOffice_code").value;
    // alert("unit_id "+unit_id+" office_id "+office_id);
     
	winemp = window.open("Bills_Token_Register_without_SP_List.jsp?unit_id="+unit_id+"&office_id="+office_id, "list",
			"status=1,height=550,width=1200,resizable=YES, scrollbars=yes");
	winemp.moveTo(30, 150);
	winemp.focus();
}

function ParentDrawing( v1,v2,v3, v4, v5, v6, v7, v8, v9,v22, v10, v11, v12, v13,
		v14, v15, v16, v17, v18, v19, v20 , v21) {
	// alert(v3);
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

	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbAcc_UnitCode.value = v1;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbOffice_code.value = v2;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType.value = v3;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = v6;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate.value = v7;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate.value = v8;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices.value = v9;
	//TotalSanctionAmount
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount.value = v22;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount.value = v10;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.value = v21;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.value = v11;
	
	/*var op = document.createElement("OPTION");
	op.value = v11;
	var txt = document.createTextNode(v11);
	op.appendChild(txt);
	se.appendChild(op);
	*/
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType.value = v12;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode.value = v13;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas.value = v14;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.value = v15;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate.value = v16;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtRemarks.value = v17;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingNo.value = v18;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingDate.value = v19;
	if (v20 == "Y") {
		document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].checked = true;
	} else {
		document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].checked = true;
	}

	document.frm_BillTokenRegisterEntry_WithoutProceeding.onsubmit.disabled = true;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.ondelete.disabled = false;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.onupdate.disabled = false;

	var url = "../../../../../Bills_Token_Register_without_SP?command=Edit&txtBillNo="
			+ v6
			+ "&txtEmpID_mas="
			+ v14
			+ "&cmbAcc_UnitCode="
			+ v1
			+ "&cmbOffice_code="
			+ v2
			+ "&year="
			+ year
			+ "&year1="
			+ year1
			+ "&txtAcc_HeadCode=" + v11+"&paydesc="+v13;
	// alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
	}
	xmlrequest.send(null);

}

function Edit1(baseResponse) {
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.length = "1";

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
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.value = billMinorTypeCode;
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
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.value = billSubTypeCode;
		}
		var len51 = baseResponse.getElementsByTagName("billSubTypeCode_app")[0].firstChild.nodeValue;
		//xml = xml + "<flag>NotApply</flag>";
		//xml = xml + "<billSubTypeCode_app>N</billSubTypeCode_app>";
		document.frm_BillTokenRegisterEntry_WithoutProceeding.bill_sub_type_applica.value =len51;
		
		var empid = baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.length = "1";
		for ( var i = 0; i < len; i++) {
			var BillProcessingDoneBy = baseResponse.getElementsByTagName("BillProcessingDoneBy")[0].firstChild.nodeValue;
			var se = document.getElementById("cmbMas_SL_Code");
			var op = document.createElement("OPTION");
			op.value = empid;
			var txt = document.createTextNode(BillProcessingDoneBy);
			op.appendChild(txt);
			se.appendChild(op);
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.value = empid;
		}

		var hid1 = baseResponse.getElementsByTagName("hid")[0].firstChild.nodeValue;
		var hdesc1 = baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadDesc.value =hdesc1;
		
		var payid = baseResponse.getElementsByTagName("payid")[0].firstChild.nodeValue;
	//	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.length = "1";
		var len11 = baseResponse.getElementsByTagName("payid").length;
		for ( var i = 0; i < len11; i++) {
			var paydesc1 = baseResponse
					.getElementsByTagName("paydesc")[0].firstChild.nodeValue;
			var se = document.getElementById("payname_desig");
			var op = document.createElement("OPTION");
			op.value = payid;
			op.innerHTML=paydesc1;
		//	var txt = document.createTextNode(paydesc1);
			//op.appendChild(txt);
			se.appendChild(op);
			//document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.value = empid;
		}
		//var acchid = baseResponse.getElementsByTagName("accheadid")[0].firstChild.nodeValue;
		//var len121 = baseResponse.getElementsByTagName("accheadid").length;
	//	for ( var i = 0; i < len121; i++) {
			/*var paydesc1 = baseResponse
					.getElementsByTagName("paydesc")[0].firstChild.nodeValue;*/
		   document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.length = 0;
		   for ( var i = 0; i < hid1.length; i++) {
			var se1 = document.getElementById("txtAcc_HeadCode");  
			var op1 = document.createElement("OPTION");
			op1.value = hid1;
			op1.innerHTML=hid1;
			//var txt1 = document.createTextNode(hid1);
			se1.appendChild(op1);
		   }
			//document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.value = empid;
		//}
		
		var flagg1 = baseResponse.getElementsByTagName("flag20")[0].firstChild.nodeValue;
		if (flagg1== "success") {
			var voi= baseResponse.getElementsByTagName("supporting_invoice")[0].firstChild.nodeValue;
			if(voi=="Y"){
				document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value ="Y";
			}else{
				document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value ="N";
			}
		}	
	}

	var flagg = baseResponse.getElementsByTagName("flagg")[0].firstChild.nodeValue;
	if (flagg == "success") {

		var BudgetProvided = baseResponse
				.getElementsByTagName("BudgetProvided")[0].firstChild.nodeValue;
		var BudgetSoFarSpent = baseResponse
				.getElementsByTagName("BudgetSoFarSpent")[0].firstChild.nodeValue;

		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision.value = BudgetProvided;
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent.value = BudgetSoFarSpent;

	} else if (flagg == "NoData") {
		alert("Budget Does not Alloted for Current Year");
	} else {
		alert("Fail to Load Budget Details");
	}

/*	var flag2 = baseResponse.getElementsByTagName("flag2")[0].firstChild.nodeValue;
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
			document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.value = OfficeID;
		}
	} else {
		alert("Fail to Load Bill Major Type");
	}*/
}

function deleteeee(path) {
	// alert(path);
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cboBillMajorType = document.getElementById("cboBillMajorType").value;

	var txtBillNo = document.getElementById("txtBillNo").value;
	var r = confirm("Are U Sure?");
	if (r == true) {
		var url = path
				+ "/Bills_Token_Register_without_SP?command=deleted&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtBillNo=" + txtBillNo + "&cboBillMajorType="
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

function deleteRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Cancelled Successfully");
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
	// alert("update");
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	if (year < 1900)
		year += 1900;

	var cboBillMajorType = document.getElementById("cboBillMajorType").value;
	var cboBillMinorType = document.getElementById("cboBillMinorType").value;
	var cboBillSubType = document.getElementById("cboBillSubType").value;
	var txtBillNo = document.getElementById("txtBillNo").value;
	var txtBillDate = document.getElementById("txtBillDate").value;
	var txtManualProceedingNo = document
			.getElementById("txtManualProceedingNo").value;
	var txtManualProceedingDate = document
			.getElementById("txtManualProceedingDate").value;
	var txtInvoiceReceivedDate = document
			.getElementById("txtInvoiceReceivedDate").value;
	var txtNoofInvoices = document.getElementById("txtNoofInvoices").value;
	if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[0].value;
	} else if (document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].checked == true) {
		rdoMTC_70_Register = document.frm_BillTokenRegisterEntry_WithoutProceeding.rdoMTC_70_Register[1].value;
	}
	var txtTotalSanctionAmount = document.getElementById("txtTotalSanctionAmount").value;
	var txtTotalBillAmount = document.getElementById("txtTotalBillAmount").value;
	var txtDeductedAmount = document.getElementById("txtDeductedAmount").value;
	var txtAcc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	var txtPayeeType = document.getElementById("txtPayeeType").value;
	var txtPayeeCode = document.getElementById("txtPayeeCode").value;
	var txtEmpID_mas = document.getElementById("txtEmpID_mas").value;
	//var cboOffice = document.getElementById("cboOffice").value;
	var txtBudgetProvision = document.getElementById("txtBudgetProvision").value;
	var txtBudgetSpent = document.getElementById("txtBudgetSpent").value;
	var txtRefNo = document.getElementById("txtRefNo").value;
	var txtRefDate = document.getElementById("txtRefDate").value;
	var mtxtRemarks = document.getElementById("mtxtRemarks").value;

	var BalanceAmount = (parseInt(txtBudgetProvision) - parseInt(txtBudgetSpent));
	if (cboBillMajorType == "" || cboBillMajorType == "s") {
		alert("Select Bill Major Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType
				.focus();
	} else if (cboBillMinorType == "" || cboBillMinorType == "s") {
		alert("Select Bill Minor Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType
				.focus();
	}/* else if (cboBillSubType == "" || cboBillSubType == "s") {
		alert("Select Bill Sub Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType
				.focus();
	}*/ else if (document.getElementById("txtBillNo").value == "") {
		alert("Enter Bill No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.focus();
	} else if (document.getElementById("txtBillDate").value == "") {
		alert("Enter Bill Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate
				.focus();
	} else if (document.getElementById("txtManualProceedingNo").value == "") {
		alert("Enter Manual Proceeding No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingNo
				.focus();
	} else if (document.getElementById("txtManualProceedingDate").value == "") {
		alert("Enter Manual Proceeding Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingDate
				.focus();
	} /*else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
		alert("Enter Invoice Received Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
				.focus();
	} else if (document.getElementById("txtNoofInvoices").value == "") {
		alert("Enter No of Invoices in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
				.focus();
	}*/
	else if (document.getElementById("txtTotalSanctionAmount").value == "") {
		alert("Enter Total Bill Sanction in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount
				.focus();
	} 
	else if (document.getElementById("txtTotalBillAmount").value == "") {
		alert("Enter Total Bill Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount
				.focus();
	} else if (document.getElementById("txtDeductedAmount").value == "") {
		alert("Enter Deducted Amount in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount
				.focus();
	} else if (document.getElementById("txtAcc_HeadCode").value == "") {
		alert("Enter Account Head code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode
				.focus();
	} else if (document.getElementById("txtPayeeType").value == "") {
		alert("Enter Payee Type in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType
				.focus();
	} else if (document.getElementById("txtPayeeCode").value == "") {
		alert("Enter Payee Code in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode
				.focus();
	} else if (document.getElementById("txtEmpID_mas").value == "") {
		alert("Enter Bill Processing Done By in the field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas
				.focus();
	} /*else if (cboOffice == "" || cboOffice == "s") {
		alert("Select Office in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.focus();
	}*/ else if (document.getElementById("txtBudgetProvision").value == "") {
		alert("Enter Budget Provision in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision
				.focus();
	} else if (document.getElementById("txtBudgetSpent").value == "") {
		alert("Enter BudgetSpent in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent
				.focus();
	} else if (document.getElementById("txtRefNo").value == "") {
		alert("Enter Ref No in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.focus();
	} else if (document.getElementById("txtRefDate").value == "") {
		alert("Enter Ref Date in the Field");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate
				.focus();
	} else if (txtTotalSanctionAmount > BalanceAmount) {
		alert("Total Sanction Amount is greater than Balance Amount in the Current Year");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount
				.focus();
	} else if (parseInt(txtTotalSanctionAmount) < parseInt(txtDeductedAmount)) {
		alert("Deducted Amount Should Less than Total Sanction Amount");
		document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.focus();
	} else {

		
		/*var flag=document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value;
		//alert("flag update "+flag);
		if(flag=="Y"){
			//alert("if...");
			if (document.getElementById("txtInvoiceReceivedDate").value == "") {
				
				alert("Enter Invoice Received Date in the Field");
				document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
						.focus();
			} else if (document.getElementById("txtNoofInvoices").value == "") {
				
				alert("Enter No of Invoices in the Field");
				document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
						.focus();
			}else{
				var url = path
				+ "/Bills_Token_Register_without_SP?command=update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtManualProceedingDate=" + txtManualProceedingDate
				+ "&txtManualProceedingNo=" + txtManualProceedingNo
				+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
				+ "&txtNoofInvoices=" + txtNoofInvoices
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtDeductedAmount=" + txtDeductedAmount
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
				+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
				+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
				+ "&txtBudgetProvision=" + txtBudgetProvision
				+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
				+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
			}
		
		}else if(flag=="N") {
		var url = path
				+ "/Bills_Token_Register_without_SP?command=update&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
				+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
				+ "&cboBillSubType=" + cboBillSubType + "&txtBillNo="
				+ txtBillNo + "&txtBillDate=" + txtBillDate
				+ "&txtManualProceedingDate=" + txtManualProceedingDate
				+ "&txtManualProceedingNo=" + txtManualProceedingNo
				+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
				+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
				+ "&txtNoofInvoices=" + txtNoofInvoices
				+ "&txtTotalBillAmount=" + txtTotalBillAmount
				+ "&txtDeductedAmount=" + txtDeductedAmount
				+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
				+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
				+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
				+ "&txtBudgetProvision=" + txtBudgetProvision
				+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
				+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
		// alert(url);
		var xmlrequest = AjaxFunction();
		xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		}

		xmlrequest.send(null);
	}*/
		
		//var bill_sub_type_applica0=	document.getElementById("bill_sub_type_applica").value;
		var appl=document.frm_BillTokenRegisterEntry_WithoutProceeding.bill_sub_type_applica.value;
		//var appl1=document.getElementById("bill_sub_type_applica").value;
			//alert("appl-->"+appl);	
			if(appl=="Y"){
				if (cboBillSubType == "" || cboBillSubType == "s") {
				alert("Select Bill Sub Type in the Field");
				document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType
						.focus();
				}else{
					//	alert("cboBillSubType "+cboBillSubType);		
					var flag=document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value;
					//cboBillSubType=0;
					
					//alert("flag "+flag);
					if(flag=="Y"){
						
						 if (document.getElementById("txtNoofInvoices").value == ""||document.getElementById("txtNoofInvoices").value==0) {
							
							alert("Enter valid No of Invoices in the Field");
							document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
									.focus();
						}
					
								
							 else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
									
									alert("Enter Invoice Received Date in the Field");
									document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
											.focus();
								}else{
									//alert("cboBillSubType "+cboBillSubType);		
									var url = path
									+ "/Bills_Token_Register_without_SP?command=update&cmbAcc_UnitCode="
									+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
									+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
									+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
									+ "&cboBillSubType=" + cboBillSubType 
									+ "&txtBillNo="	+ txtBillNo +
									"&txtBillDate=" + txtBillDate
									+ "&txtManualProceedingDate=" + txtManualProceedingDate
									+ "&txtManualProceedingNo=" + txtManualProceedingNo
									+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
									+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
									+ "&txtNoofInvoices=" + txtNoofInvoices
									+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
									+ "&txtDeductedAmount=" + txtDeductedAmount
									+ "&txtTotalBillAmount=" + txtTotalBillAmount
									+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
									+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
									+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
									+ "&txtBudgetProvision=" + txtBudgetProvision
									+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
									+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
							// alert(url);
							var xmlrequest = AjaxFunction();
							xmlrequest.open("POST", url, true);
							xmlrequest.onreadystatechange = function() {
								manipulate(xmlrequest);
							};
								}
							
					
					}else if(flag=="N") {

						var url = path
								+ "/Bills_Token_Register_without_SP?command=update&cmbAcc_UnitCode="
								+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
								+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
								+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
								+ "&cboBillSubType=" + cboBillSubType 
								+ "&txtBillNo="	+ txtBillNo +
								"&txtBillDate=" + txtBillDate
								+ "&txtManualProceedingDate=" + txtManualProceedingDate
								+ "&txtManualProceedingNo=" + txtManualProceedingNo
								+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
								+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
								+ "&txtNoofInvoices=" + txtNoofInvoices
								/*+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount*/
								+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount
								+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
								+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
								+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
								+ "&txtBudgetProvision=" + txtBudgetProvision
								+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
								+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
						// alert(url);
						var xmlrequest = AjaxFunction();
						xmlrequest.open("POST", url, true);
						xmlrequest.onreadystatechange = function() {
							manipulate(xmlrequest);
						}
					}
					
				
				}
			}else{
				//alert("enter into else ");
				
				var flag=document.frm_BillTokenRegisterEntry_WithoutProceeding.checkFlag.value;
				cboBillSubType=0;
				//alert("else cboBillSubType "+cboBillSubType);		
				//alert("flag "+flag);
				if(flag=="Y"){
					//alert("if...");
					 if (document.getElementById("txtNoofInvoices").value == ""||document.getElementById("txtNoofInvoices").value==0) {
						
						alert("Enter valid No of Invoices in the Field");
						document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices
								.focus();
					}
					// else if (document.getElementById("txtNoofInvoices").value >0) {
							
						 else if (document.getElementById("txtInvoiceReceivedDate").value == "") {
								
								alert("Enter Invoice Received Date in the Field");
								document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate
										.focus();
							}else{
								//alert("flag Y cboBillSubType "+cboBillSubType);		
								var url = path
								+ "/Bills_Token_Register_without_SP?command=update&cmbAcc_UnitCode="
								+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
								+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
								+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
								+ "&cboBillSubType="+ cboBillSubType 
								+ "&txtBillNo="	+ txtBillNo 
								+ "&txtBillDate=" + txtBillDate
								+ "&txtManualProceedingDate=" + txtManualProceedingDate
								+ "&txtManualProceedingNo=" + txtManualProceedingNo
								+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
								+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
								+ "&txtNoofInvoices=" + txtNoofInvoices
								/*+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount*/
								+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
								+ "&txtDeductedAmount=" + txtDeductedAmount
								+ "&txtTotalBillAmount=" + txtTotalBillAmount
								+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
								+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
								+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
								+ "&txtBudgetProvision=" + txtBudgetProvision
								+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
								+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
						//alert(url);
						var xmlrequest = AjaxFunction();
						xmlrequest.open("POST", url, true);
						xmlrequest.onreadystatechange = function() {
							manipulate(xmlrequest);
						};
							}
										
				}else if(flag=="N") {

					//alert("flag N cboBillSubType "+cboBillSubType);		
					var url = path
							+ "/Bills_Token_Register_without_SP?command=update&cmbAcc_UnitCode="
							+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
							+ "&year=" + year + "&month=" + month + "&cboBillMajorType="
							+ cboBillMajorType + "&cboBillMinorType=" + cboBillMinorType
							+ "&cboBillSubType="+ cboBillSubType
							+ "&txtBillNo="	+ txtBillNo +
							"&txtBillDate=" + txtBillDate
							+ "&txtManualProceedingDate=" + txtManualProceedingDate
							+ "&txtManualProceedingNo=" + txtManualProceedingNo
							+ "&txtInvoiceReceivedDate=" + txtInvoiceReceivedDate
							+ "&rdoMTC_70_Register=" + rdoMTC_70_Register
							+ "&txtNoofInvoices=" + txtNoofInvoices
							+ "&txtTotalSanctionAmount=" + txtTotalSanctionAmount
							+ "&txtDeductedAmount=" + txtDeductedAmount
							+ "&txtTotalBillAmount=" + txtTotalBillAmount
							+ "&txtAcc_HeadCode=" + txtAcc_HeadCode + "&txtPayeeType="
							+ txtPayeeType + "&txtPayeeCode=" + txtPayeeCode
							+ "&txtEmpID_mas=" + txtEmpID_mas //+ "&cboOffice=" + cboOffice
							+ "&txtBudgetProvision=" + txtBudgetProvision
							+ "&txtBudgetSpent=" + txtBudgetSpent + "&txtRefNo=" + txtRefNo
							+ "&txtRefDate=" + txtRefDate + "&mtxtRemarks=" + mtxtRemarks;
					// alert("fay N "+url);
					var xmlrequest = AjaxFunction();
					xmlrequest.open("POST", url, true);
					xmlrequest.onreadystatechange = function() {
						manipulate(xmlrequest);
					}
				}
				
			}

		xmlrequest.send(null);

	}
}

function updateRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	if (flag == "success") {
		alert("Record Updated Successfully.");
		refresh();
	} else {
		alert("Failed to update values");
	}
}

function refresh() {
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMajorType.value = "s";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillMinorType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cboBillSubType.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtNoofInvoices.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalBillAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtTotalSanctionAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtDeductedAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadCode.length = 0;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtAcc_HeadDesc.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeType.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCode.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.cmbMas_SL_Code.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.payname_desig.value = 0;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.payname_desig.length = 0;
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtPayeeCodeLoad.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding.txtEmpID_mas.value = "";
	//document.frm_BillTokenRegisterEntry_WithoutProceeding.cboOffice.length = "1";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetSpent.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBudgetProvision.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtRemarks.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceNo.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtIfSelectfromList.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceAmount.value = "";
	document.frm_BillTokenRegisterEntry_WithoutProceeding.mtxtParticulars1.value = "";
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