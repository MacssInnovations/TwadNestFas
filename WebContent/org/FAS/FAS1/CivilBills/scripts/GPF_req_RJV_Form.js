function getTransport()
{
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

function goclick(path)
{
	
	if(document.frmGPF_RJV_Req1.txtCB_Year.value=="")
    {
        alert("Select CashBook Year");
        document.frmGPF_RJV_Req1.txtCB_Year.focus();
        return false;
    }
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	var url=path+"/GPF_req_RJV_Form?Command=Goclick&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
	
	var req=getTransport();
	req.open("GET",url,true); 
	
	req.onreadystatechange=function()
	{
		conmm_load(req)	;
	
	};	req.send(null);
}

function goclickApproved(path)
		{

	var seq=1;
	if(document.frmGPF_RJV_Req2.txtCB_Year.value=="")
    {
        alert("Select CashBook Year");
        document.frmGPF_RJV_Req2.txtCB_Year.focus();
        return false;
    }
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	var url=path+"/GPF_req_RJV_Form?Command=GoclickApp&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	
	var req=getTransport();
	req.open("GET",url,true); 
	
	req.onreadystatechange=function()
	{
		conmm_load(req)	;
	
	};	req.send(null);

		}


function doParentAcc_NOcp(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
	
   if(Bank_popup_flag1==true)
   {
       document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
       document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtBankId").value=bankid;
       document.getElementById("txtBranchId").value=br_id;
       document.getElementById("txtBankName").value=B_name;
       Bank_popup_flag1="";
       return true;
   }
  else if(Bank_popup_flag1==false)
  {
      
       Bank_popup_flag1="";
       return true;
   }
}function loadAccHeadnew(path){
	if(document.getElementById("type_1").value=="CR")
	{  
		//var radios = document.formName.disableme;


		document.frmGPF_RJV_Req.txtcDr_Type[1].disabled = true;
		document.frmGPF_RJV_Req.txtcDr_Type[0].checked=true;
		document.frmGPF_RJV_Req.txtcDr_Type[1].checked=false;
		document.getElementById("txtCash_Acc_code").value="820101";
	
	document.getElementById("cap1").style.display="block";
	document.getElementById("cap2").style.display="none";
	document.getElementById("cap1_img").style.display="block";
	document.getElementById("cap2_img").style.display="none";
	document.getElementById("cap3_img").style.display="none";
	document.getElementById("txtCash_Acc_code").style.display="block";	
	document.getElementById("txtRecei_from").style.display="block";	
	
	}
	else if(document.getElementById("type_1").value=="BR")
	{
		  Bank_popup_flag1=true;
		var unit=document.getElementById("cmbAcc_UnitCode").value;
		document.frmGPF_RJV_Req.txtcDr_Type[1].disabled = true;
		
		document.frmGPF_RJV_Req.txtcDr_Type[0].checked=true;
		document.frmGPF_RJV_Req.txtcDr_Type[1].checked=false;
		var url=path+"/GPF_req_RJV_Form?Command=loadBAHead&cmbAcc_UnitCode="+unit;
		
		var req=getTransport();
		req.open("GET",url,true); 
		
		req.onreadystatechange=function()
		{
			 if(req.readyState==4)
			    {
			        if(req.status==200)
			        {  
			            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			            var tagcommand=baseResponse.getElementsByTagName("command")[0];
			            var Command=tagcommand.firstChild.nodeValue;
			            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			            var bankID=baseResponse.getElementsByTagName("bankID")[0].firstChild.nodeValue;
			            var branchID=baseResponse.getElementsByTagName("branchID")[0].firstChild.nodeValue;
			            var bankAccNo=baseResponse.getElementsByTagName("bankAccNo")[0].firstChild.nodeValue;
			            var bk_br_city=baseResponse.getElementsByTagName("bk_br_city")[0].firstChild.nodeValue;
			          
			            var head=baseResponse.getElementsByTagName("head")[0].firstChild.nodeValue;
			            
			         
			            if(Command=="loadBAHead"){
			            if(flag=="success"){
			            	 document.getElementById("txtCash_Acc_code").value=head;
			            	 document.getElementById("txtBankAccountNo").value=bankAccNo;
			            	 document.getElementById("txtBankId").value=bankID;
			            	 document.getElementById("txtBankName").value=bk_br_city;
			            	 document.getElementById("txtBranchId").value=branchID;
			            	 document.getElementById("cap1").style.display="none";
			            		document.getElementById("cap2").style.display="block";
			            		document.getElementById("cap1_img").style.display="none";
			            		document.getElementById("cap2_img").style.display="block";
			            		document.getElementById("cap3_img").style.display="block";
			            		document.getElementById("txtCash_Acc_code").style.display="block";	
			            		document.getElementById("txtRecei_from").style.display="block";	   
			            
			            
			            }	
			            }
			            }
			        }
			    };
		
		
			req.send(null);
		
	}else{
		 document.getElementById("cap1").style.display="none";
 		document.getElementById("cap2").style.display="none";
 		document.getElementById("cap1_img").style.display="none";
 		document.getElementById("cap2_img").style.display="none";	
 		document.getElementById("txtCash_Acc_code").style.display="none";	
 		document.getElementById("cap3_img").style.display="none";
 		document.frmGPF_RJV_Req.txtcDr_Type[1].disabled = false;
	}
		
}
var winAcc_Bank_Nonew;
function MainAccNopopupnew()
{
	Bank_popup_flag1=true;
    if (winAcc_Bank_Nonew && winAcc_Bank_Nonew.open && !winAcc_Bank_Nonew.closed) 
    {
    	winAcc_Bank_Nonew.resizeTo(500,500);
    	winAcc_Bank_Nonew.moveTo(250,250); 
    	winAcc_Bank_Nonew.focus();
    }
    else
    {
    	winAcc_Bank_Nonew=null
    }
    //var Office_code=document.getElementById("cmbOffice_code").value;  
    var txtModule_Type="MF004";
    var cr_dr_indi="DR";
    var unspent_OR_col="COL";
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtSub_Office_code=0;
    winAcc_Bank_Nonew= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised1.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&txtSub_Office_code="+txtSub_Office_code+"&unspent_OR_col="+unspent_OR_col,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_Nonew.moveTo(250,250);  
    winAcc_Bank_Nonew.focus();
}
function conmm_load(req)
{

	 if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var tagcommand=baseResponse.getElementsByTagName("command")[0];
	            var Command=tagcommand.firstChild.nodeValue;
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            var len=baseResponse.getElementsByTagName("request_no").length;
	           
	            	var tbody = document.getElementById("grid_body");
	            	var seq=0;
	            	 var t=0; for(t=tbody.rows.length-1;t>=0;t--) { tbody.deleteRow(0); }
	            if(flag=="success")
	              {
	            
	            	for(var i=0;i<len;i++){
	          
	            		  var request_no=baseResponse.getElementsByTagName("request_no")[i].firstChild.nodeValue;
	            		  var ACCOUNTING_UNIT_ID=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
	            		  var unit_name=baseResponse.getElementsByTagName("unit_name")[i].firstChild.nodeValue;
	            		  var VOUCHER_DATE=baseResponse.getElementsByTagName("VOUCHER_DATE")[i].firstChild.nodeValue;
	            		  var Amount=baseResponse.getElementsByTagName("Amount")[i].firstChild.nodeValue;
	            	var TYPE_OPTION=baseResponse.getElementsByTagName("TYPE_OPTION")[i].firstChild.nodeValue;
	            		  var myrow = document.createElement("tr");
	            	myrow.id=seq;
	            	var cell1 = document.createElement("td");
	            	var inp_1 = document.createElement("input");
	            	inp_1.type = "checkbox";
	            	inp_1.id = "chk";
	            	inp_1.name = "chk";
	            	inp_1.value = 'Unchecked';
	            	if(Command=="goClick"){
	            	inp_1.setAttribute('onclick','unchk('+seq+',1)');
	            	var hid1 = document.createElement("input");
	            	hid1.type = "hidden";
	            	hid1.id = "hid";
	            	hid1.name = "hid";
	            	hid1.value =  'Unchecked';
	            	cell1.appendChild(hid1);
	            	
	            	}
	            	else{
	            		inp_1.setAttribute('onclick','unchk('+seq+',2)');
	            	var hid = document.createElement("input");
	            	hid.type = "hidden";
	            	hid.id = "hid_o";
	            	hid.name = "hid_o";
	            	hid.value =  'Unchecked';
	            	cell1.appendChild(hid);
	            
	            	}
	            	cell1.appendChild(inp_1);
                	myrow.appendChild(cell1);
                	
                	
	            	var cell2 = document.createElement("td");
	            	var inp_2 = document.createElement("input");
	            	inp_2.type = "hidden";
	            	inp_2.id = "request_no";
	            	inp_2.name = "request_no";
	            	inp_2.value = request_no;
	            	cell2.appendChild(inp_2);
	            	var text_2 = document.createTextNode(request_no);
	            	cell2.appendChild(text_2);
	            	myrow.appendChild(cell2);

	            	var cell3 = document.createElement("td");
	            	var inp_3 = document.createElement("input");
	            	inp_3.type = "hidden";
	            	inp_3.id = "cmbAcc_UnitCode";
	            	inp_3.name = "cmbAcc_UnitCode";
	            	inp_3.value = ACCOUNTING_UNIT_ID;
	            	cell3.appendChild(inp_3);
	            	var text_3 = document.createTextNode(unit_name);
	            	cell3.appendChild(text_3);
	            	myrow.appendChild(cell3);
	            	
	            	var cell4 = document.createElement("td");
	            	var inp_4 = document.createElement("input");
	            	inp_4.type = "hidden";
	            	inp_4.id = "VOUCHER_DATE";
	            	inp_4.name = "VOUCHER_DATE";
	            	inp_4.value = VOUCHER_DATE;
	            	cell4.appendChild(inp_4);
	            	var text_4 = document.createTextNode(VOUCHER_DATE);
	            	cell4.appendChild(text_4);
	            	myrow.appendChild(cell4);

	            	var cell5 = document.createElement("td");
	            	var inp_5 = document.createElement("input");
	            	inp_5.type = "hidden";
	            	inp_5.id = "Amount";
	            	inp_5.name = "Amount";
	            	inp_5.value = Amount;
	            	cell5.appendChild(inp_5);
	            	var text_5 = document.createTextNode(Amount);
	            	cell5.appendChild(text_5);
	            	var inp_51 = document.createElement("input");
	            	inp_51.type = "hidden";
	            	inp_51.id = "type_opt";
	            	inp_51.name = "type_opt";
	            	inp_51.value =TYPE_OPTION;
	            	cell5.appendChild(inp_51);
	            
	            	myrow.appendChild(cell5);

	            	var cell6 = document.createElement("td");
	            	var inp_6 = document.createElement("a");
	            	inp_6.href = "Javascript:show("+ACCOUNTING_UNIT_ID+","+request_no+",'"+VOUCHER_DATE+"',"+Amount+")";
	            	
	            	inp_6.text = 'Details';
	            	cell6.appendChild(inp_6);
	            	
	            	myrow.appendChild(cell6);

	            	tbody.appendChild(myrow);
	            	seq++;
	            	}
	              }else{
	            	  alert('No Data');
	              }
	        }
	    }
}

function show(unitID,reqNo,VrDte,amount)
{   
	winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/CivilBills/jsps/GPF_req_RJV_List.jsp?cmbAcc_UnitCode="+unitID+"&request_no="+reqNo+"&VOUCHER_DATE="+VrDte+"&Amount="+amount,"GPF_req_RJVList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
winAcc_Bank_No.moveTo(250,250);  
winAcc_Bank_No.focus();
	
	}
function nullcheck1()
{
	
	var cr_amt=0;var dr_amt=0;var tot_amt=0;
	var tbody=document.getElementById("grid_body");
	

	if(document.frmGPF_RJV_Req.txtCB_Year.value=="")
    {
        alert("Enter CashBook Year");
        document.frmGPF_RJV_Req.txtCB_Year.focus();
        return false;
    }
	/*if(document.frmGPF_RJV_Req.txtCB_Year.value==document.frmGPF_RJV_Req.txtrel_Year.value)
	{
		if(document.frmGPF_RJV_Req.txtCB_Month.value < document.frmGPF_RJV_Req.txtrel_Month.value)
			{
			alert("Enter Relative CashBook Month");
	        document.frmGPF_RJV_Req.txtrel_Year.focus();
	        return false;
			}
	}else if(document.frmGPF_RJV_Req.txtCB_Year.value < document.frmGPF_RJV_Req.txtrel_Year.value)
	{
		alert("Enter Relative CashBook Year");
        document.frmGPF_RJV_Req.txtrel_Year.focus();
        return false;
	}
	*/
	
	if(document.frmGPF_RJV_Req.txtrel_Year.value=="")
    {
		alert("Enter Relative CashBook Year");
        document.frmGPF_RJV_Req.txtrel_Year.focus();
        return false;
    }else if(document.frmGPF_RJV_Req.txtrel_Month.value=="")
    {
        alert("Select Relative CashBook Month ");
        document.frmGPF_RJV_Req.txtrel_Month.focus();
        return false;
    }else if(document.frmGPF_RJV_Req.txtVrDate.value=="")
    {
        alert("Select VoucherDate Year");
        document.frmGPF_RJV_Req.txtVrDate.focus();
        return false;
    }else if(document.frmGPF_RJV_Req.txtVrDate.value != ""){
           var dte=document.frmGPF_RJV_Req.txtVrDate.value;
    	var txtCB_Year=document.getElementById("txtCB_Year").value;
    	var txtCB_Month=document.getElementById("txtCB_Month").value;
    	if(dte.split("/")[1]!=txtCB_Month || dte.split("/")[2]!=txtCB_Year){
    		alert('Select Valid VoucherDate between selected CashBook Month & Year ... ');
    		document.getElementById("txtVrDate").value="";
    		document.getElementById("txtVrDate").focus();
    		 return false;
    	}
    }	
	if(document.frmGPF_RJV_Req.type_1.value=="")
		{
		  alert("Select Option");
	        document.frmGPF_RJV_Req.type_1.focus();
	        return false;
		}
	 if(document.frmGPF_RJV_Req.txtJnl_Amt.value=="")
    {
		
        alert("Select CashBook Year");
        document.frmGPF_RJV_Req.txtJnl_Amt.focus();
        return false;
    }	
     else if(document.frmGPF_RJV_Req.txtJnl_Amt.value != "")
     {
    
    	
		if (tbody.rows.length > 0) {
			
			tot_amt = document.frmGPF_RJV_Req.txtJnl_Amt.value;
		
			mylen=tbody.rows.length;
		
			for (var i = 1; i <= mylen; i++) {
			
				var rows = document.getElementById(i).cells;
				
				if (rows.item(1).lastChild.nodeValue == 'CR') {
				
						
					cr_amt = parseInt(cr_amt) + parseInt(rows.item(4).lastChild.nodeValue);
				} else if (rows.item(1).lastChild.nodeValue == 'DR') {
					
					
					dr_amt = parseInt(dr_amt) + parseInt(rows.item(4).lastChild.nodeValue);
				}
			}
	
			var type_val=document.getElementById("type_1").options[document.getElementById("type_1").selectedIndex].value;
		
			
			//var amount=parseInt(cr_amt)+parseInt(dr_amt);
			if(type_val=="JR"){
			if(parseInt(cr_amt)==parseInt(dr_amt))
				{
				/*if(parseInt(tot_amt)==parseInt(amount)){
					return true;
				}else */
					if(parseInt(tot_amt)!=parseInt(cr_amt)){
					alert('Total Journal Amount should be equal to Sum of Cr amount ... ');
					 var tbody=document.getElementById("grid_body");
		              var t=0;
		              for(t=tbody.rows.length-1;t>=0;t--)
		              {
		                 tbody.deleteRow(0);
		              }
					return false;
					
				}else if(parseInt(tot_amt)!=parseInt(dr_amt)){
					alert('Total Journal Amount should be equal to Sum of Dr amount ... ');
					 var tbody=document.getElementById("grid_body");
		              var t=0;
		              for(t=tbody.rows.length-1;t>=0;t--)
		              {
		                 tbody.deleteRow(0);
		              }
					return false;
					
				}
			
				}
			else if(parseInt(cr_amt)!=parseInt(dr_amt))
				{
				alert('Dr amount should be equal to Cr amount ... ');
				 var tbody=document.getElementById("grid_body");
	              var t=0;
	              for(t=tbody.rows.length-1;t>=0;t--)
	              {
	                 tbody.deleteRow(0);
	              }
				return false;
				}
		}else{
			
			var amount=parseInt(cr_amt);
		
			
			if(parseInt(tot_amt)!=parseInt(amount)){
				alert('Total Amount should be equal to Sum of Detail part amount ... ');
				 var tbody=document.getElementById("grid_body");
	              var t=0;
	              for(t=tbody.rows.length-1;t>=0;t--)
	              {
	                 tbody.deleteRow(0);
	              }
				return false;
				
			}
			
		}
		}
	}
	
	
        
      else{
            return true;
      }
	//alert(cr_amt+" >> "+dr_amt);
   
}
var priorsince=1;
function tohidedoc()
{
	
	var adjyear=document.getElementById("adjyear").value;
	if(adjyear=="")
	{
		alert('Please Enter Adjustment year');
		return false;
	}
	
	var adjmonth=document.getElementById("adjmonth").value;	
	if(adjyear>2007)
	{
		document.getElementById("since").style.display='block';	
		document.getElementById("prior").style.display='none';	
		document.getElementById("since2007").style.display='block';	
		document.getElementById("prior2007").style.display='none';	
		priorsince=1;
		
	}else if(adjyear==2007)
	{
		if(adjmonth<9)
		{
			document.getElementById("since").style.display='none';	
			document.getElementById("prior").style.display='block';	
			document.getElementById("since2007").style.display='none';	
			document.getElementById("prior2007").style.display='block';	
			priorsince=2;
		}else{
			document.getElementById("since").style.display='block';	
			document.getElementById("prior").style.display='none';	
			document.getElementById("since2007").style.display='block';	
			document.getElementById("prior2007").style.display='none';	
			priorsince=1;
		}
	}else if(adjyear<2007)
	{
		document.getElementById("since").style.display='none';	
		document.getElementById("prior").style.display='block';	
		document.getElementById("since2007").style.display='none';	
		document.getElementById("prior2007").style.display='block';
		priorsince=2;
	}

}

function payreceipt()
{
	
	var adjyear=document.getElementById("adjyear").value;
	var adjmonth=document.getElementById("adjmonth").value;
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var doctype=document.getElementById("paymentreceipt").value;
        var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
    
	var req=getTransport();
  
	 var url="../../../../../Rectificational_Journal?command=paymentreceipt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&txtAcc_HeadCode="+txtAcc_HeadCode;
	 //var url="../../../../../Rectificational_Journal?command=paymentreceipt&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&adjyear="+adjyear+"&adjmonth="+adjmonth+"&doctype="+doctype+"&txtAcc_HeadCode="+txtAcc_HeadCode;
     //alert(url);
	 url=url+"&sid="+Math.random();
	 req.open("GET",url,true);
		
		req.onreadystatechange=function()
		{
			stateChanged(req)	;
		
		};
	
	 req.send(null);  
 }
function stateChanged(req)
{
    var flag,command,response;
   
    if(req.readyState==4)
    {
    	
       if(req.status==200)
       {
            response=req.responseXML.getElementsByTagName("response")[0];
          
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="paymentreceipt")
            {
            	
                if(flag=='success')
                {
                	
                	try{
                		
              		  var len=response.getElementsByTagName("receiptno").length;
                 	var billno=document.getElementById("receiptno");
                 
              	 var items_id=new Array();
              	 var items_desc=new Array();                        
                          for(var i=0;i<len;i++)
                          {
                                 items_id[i]=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
                                 var cflag=response.getElementsByTagName("commonFlag")[0].firstChild.nodeValue;
                                 if(cflag=="yes")
                                 {
                                     var r1=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;  
                                     var r2=response.getElementsByTagName("slno")[i].firstChild.nodeValue;  
                                    items_desc[i]=r1+'-'+r2;  
                                 }
                                 else
                                 {
                                 items_desc[i]=response.getElementsByTagName("receiptno")[i].firstChild.nodeValue;
                                 }
                             //   alert('minor'+items_desc[i]);
                         }
                     clear_Combo(billno);
                    
                          //alert('here second');
                          for(var k=0;k<len;k++)
                          {   
                          	//alert(items_name[k]);
                                var option=document.createElement("OPTION");
                                option.text=items_desc[k];
                                option.value=items_id[k];
                             
                                 try
                                {
                                	 billno.add(option);
                                	
                                }
                                catch(errorObject)
                                {
                                	billno.add(option,null);
                                	
                                   // alert('error');
                                }
                          }
              	
              	}catch(e){alert("Error in lat"+e);}      
                
                }
                else
                    {
                	var billno=document.getElementById("receiptno");
                	clear_Combo(billno);                 
                    }
                 }
       }
    }
}

var payreceipt;

function payreceiptdetails()
{
	
	//var w = document.frmJournal_General.receiptno.selectedIndex;
//    var selected_text = document.frmJournal_General.receiptno.options[w].text;
   // alert("ttttt");
    var selected_text = document.frmGPF_RJV_Req.receiptno.value;
 	
	var adjyear=document.getElementById("adjyear").value;
	var adjmonth=document.getElementById("adjmonth").value;
	
	var type=document.getElementById("paymentreceipt").value;
       //var docno=document.getElementById("receiptno").value;
	
	var docno=selected_text;
	
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
     
     
	if(adjyear=="")
	{
		alert('Please Enter Adjustment year');
		return false;
	}
	if(type=="")
	{
		alert('Please Select Doc.Type');
		return false;
	}
	if(docno=="")
	{
		alert('Please Select Doc.No');
		return false;
	}
	
	
	if (payreceipt && payreceipt.open && !payreceipt.closed) 
	    {
		 payreceipt.resizeTo(500,500);
		 payreceipt.moveTo(250,250); 
		 payreceipt.focus();
	    }
	    else
	    {
	    	payreceipt=null;
	    }

	 payreceipt= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/Rectification_Pay_Receipt_Details.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+adjyear+"&mon="+adjmonth+"&recNo="+docno+"&type="+type,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	 payreceipt.moveTo(250,250);  
	 payreceipt.focus();
	
}


function checkdt2(dte){
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	if(dte.split("/")[1]!=txtCB_Month || dte.split("/")[2]!=txtCB_Year){
		alert('Select Valid Date between selected CashBook Month & Year ... ');
		document.getElementById("txtVrDate").value="";
		document.getElementById("txtVrDate").focus();
	}
}
function check_db(path)
{

	
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtCB_Year=document.getElementById("txtCB_Year").value;
	var txtCB_Month=document.getElementById("txtCB_Month").value;
	var TB_date="01/"+txtCB_Month+"/"+txtCB_Year;
	//alert(fromcal_dateCtrl.value+"b4url")
	
	var url=path+"/Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
	//alert(url);
	var req=getTransport();
	req.open("GET",url,true); 
	req.onreadystatechange=function()
	{
	//check_TB(req,fromcal_dateCtrl);

		 if(req.readyState==4)
		    {
		        if(req.status==200)
		        {  
		            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		            var tagcommand=baseResponse.getElementsByTagName("command")[0];
		            var Command=tagcommand.firstChild.nodeValue;
		            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		           // alert("flag"+flag);
		            if(flag=="success")
		              {
		                 //doFunction('load_Receipt_No','null');   
		            	return true;
		              }
		             else if(flag=="failure")
		               {
		                   
		                    alert("Trial Balance Closed");//return false;//
		                    return false;
		                    			                   
		               }
		             else if(flag=="finyear")
		               {
		                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
		                 
		                    alert("Cash Book Control Not Found ");//return false;//
		                    return false;
		                    
		               }
		        }
		    }

	};   
	req.send(null);
	
}


function addGrid() {
	var seq=1;
	var cDr_Type = "";
	var Acc_HeadCode = document.getElementById("txtAcc_HeadCode").value;
	//alert(Acc_HeadCode+">>>>");
	 if (Acc_HeadCode == 390302 ||  Acc_HeadCode == 390303 || Acc_HeadCode == 390305 || Acc_HeadCode == 391002 || Acc_HeadCode == 391003 || Acc_HeadCode == 391302 || Acc_HeadCode == 391303 || Acc_HeadCode == 391502 || Acc_HeadCode == 391503)
	{
	if (document.frmGPF_RJV_Req.txtcDr_Type[0].checked == true)
		cDr_Type = 'CR';
	else if (document.frmGPF_RJV_Req.txtcDr_Type[1].checked == true)
		cDr_Type = 'DR';
	var txt_amt = document.getElementById("txt_amt").value;
	var txtPayeeType = document.getElementById("cmbSL_type").value;
	var Particulars=document.getElementById("txtParticular").value;
	var txtPayeeTypedesc = document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
	var txtPayeeCodeLoad = document.getElementById("cmbSL_Code").value;
	var txtPayeeCode = document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
	var items15;var items14;
	var items16;var items17;
	  if(priorsince==1){
	        items16=document.getElementById("paymentreceipt").value;
	        items17=document.getElementById("receiptno").value;
	       // items[17]=selected_text;
	        
	        }else{
	        	items16=document.getElementById("paymentreceipt1").value;
	            items17=document.getElementById("receiptno1").value;
	        }
	   items14=document.getElementById("adjyear").value;
       items15=document.getElementById("adjmonth").value;
	var tbody = document.getElementById("grid_body");
	/*
	 * var t=0; for(t=tbody.rows.length-1;t>=0;t--) { tbody.deleteRow(0); }
	 */
	if(tbody.rows.length==0 || tbody.rows.length == null){
		tbody.rows.length=0;
	}
	seq=seq+tbody.rows.length;
	var myrow = document.createElement("tr");
	myrow.id=seq;
	var cell1 = document.createElement("td");
	var inp_1 = document.createElement("input");
	inp_1.type = "hidden";
	inp_1.id = "head";
	inp_1.name = "head";
	inp_1.value = Acc_HeadCode;
	cell1.appendChild(inp_1);
	var text_1 = document.createTextNode(Acc_HeadCode);
	cell1.appendChild(text_1);
	myrow.appendChild(cell1);

	var cell2 = document.createElement("td");
	var inp_2 = document.createElement("input");
	inp_2.type = "hidden";
	inp_2.id = "cDr_Type";
	inp_2.name = "cDr_Type";
	inp_2.value = cDr_Type;
	cell2.appendChild(inp_2);
	var text_2 = document.createTextNode(cDr_Type);
	cell2.appendChild(text_2);
	myrow.appendChild(cell2);

	var cell4 = document.createElement("td");
	var inp_4 = document.createElement("input");
	inp_4.type = "hidden";
	inp_4.id = "grid_Sltype";
	inp_4.name = "grid_Sltype";
	inp_4.value = txtPayeeType;
	cell4.appendChild(inp_4);
	var text_4 = document.createTextNode(txtPayeeTypedesc);
	cell4.appendChild(text_4);
	myrow.appendChild(cell4);

	var cell31 = document.createElement("td");
	var inp_31 = document.createElement("input");
	inp_31.type = "hidden";
	inp_31.id = "grid_SlCode";
	inp_31.name = "grid_SlCode";
	inp_31.value = txtPayeeCodeLoad;
	cell31.appendChild(inp_31);
	var text_31 = document.createTextNode(txtPayeeCode);
	cell31.appendChild(text_31);
	myrow.appendChild(cell31);

	
	var cell3 = document.createElement("td");
	var inp_3 = document.createElement("input");
	inp_3.type = "hidden";
	inp_3.id = "grid_amt";
	inp_3.name = "grid_amt";
	inp_3.value = txt_amt;
	cell3.appendChild(inp_3);
	var text_3 = document.createTextNode(txt_amt);
	cell3.appendChild(text_3);
	myrow.appendChild(cell3);
	
	var cell32 = document.createElement("td");
	var inp_32 = document.createElement("input");
	inp_32.type = "hidden";
	inp_32.id = "grid_part";
	inp_32.name = "grid_part";
	inp_32.value = Particulars;
	cell32.appendChild(inp_32);
	var text_32 = document.createTextNode(Particulars);
	cell32.appendChild(text_32);
	myrow.appendChild(cell32);
	
  
    
    cell2=document.createElement("TD");
    var adj_year=document.createElement("input");
    adj_year.type="hidden";
    adj_year.name="adj_year";
    adj_year.value=items14;
    cell2.appendChild(adj_year);
     var currentText=document.createTextNode(items14);
    cell2.appendChild(currentText);
    myrow.appendChild(cell2);
  
  cell2=document.createElement("TD");
  var adj_month=document.createElement("input");
  adj_month.type="hidden";
  adj_month.name="adj_month";
  adj_month.value=items15;
  cell2.appendChild(adj_month);
   var currentText=document.createTextNode(items15);
  cell2.appendChild(currentText);
  myrow.appendChild(cell2);
 
cell2=document.createElement("TD");
var doc_type=document.createElement("input");
doc_type.type="hidden";
doc_type.name="doc_type";
doc_type.value=items16;
cell2.appendChild(doc_type);
 var currentText=document.createTextNode(items16);
cell2.appendChild(currentText);
myrow.appendChild(cell2);

cell2=document.createElement("TD");
var doc_no=document.createElement("input");
doc_no.type="hidden";
doc_no.name="doc_no";
doc_no.value=items17;
cell2.appendChild(doc_no);
var currentText=document.createTextNode(items17);
cell2.appendChild(currentText);
myrow.appendChild(cell2);
	
	tbody.appendChild(myrow);
	 document.getElementById("txtAcc_HeadCode").value="";
	 document.getElementById("txtAcc_HeadDesc").value="";
	 document.getElementById("txt_amt").value="";
	 document.getElementById("txtParticular").value="";

	   document.getElementById("adjyear").value="";
	    document.getElementById("adjmonth").value="";
  
   document.getElementById("paymentreceipt").value="";
   document.getElementById("receiptno").value="";
   
   document.getElementById("paymentreceipt1").value="";
   document.getElementById("receiptno1").value="";
	}
	 else if(document.getElementById("type_1").value=="JR")
     {
         if (Acc_HeadCode == 390603)
             {
             if (document.frmGPF_RJV_Req.txtcDr_Type[0].checked == true)
                 cDr_Type = 'CR';
             else if (document.frmGPF_RJV_Req.txtcDr_Type[1].checked == true)
                 cDr_Type = 'DR';
             var txt_amt = document.getElementById("txt_amt").value;
             var txtPayeeType = document.getElementById("cmbSL_type").value;
             var Particulars=document.getElementById("txtParticular").value;
             var txtPayeeTypedesc = document.getElementById("cmbSL_type").options[document.getElementById("cmbSL_type").selectedIndex].text;
             var txtPayeeCodeLoad = document.getElementById("cmbSL_Code").value;
             var txtPayeeCode = document.getElementById("cmbSL_Code").options[document.getElementById("cmbSL_Code").selectedIndex].text;
             var items15;var items14;
             var items16;var items17;
               if(priorsince==1){
                     items16=document.getElementById("paymentreceipt").value;
                     items17=document.getElementById("receiptno").value;
                    // items[17]=selected_text;
                    
                     }else{
                         items16=document.getElementById("paymentreceipt1").value;
                         items17=document.getElementById("receiptno1").value;
                     }
                items14=document.getElementById("adjyear").value;
                items15=document.getElementById("adjmonth").value;
             var tbody = document.getElementById("grid_body");
             /*
              * var t=0; for(t=tbody.rows.length-1;t>=0;t--) { tbody.deleteRow(0); }
              */
             if(tbody.rows.length==0 || tbody.rows.length == null){
                 tbody.rows.length=0;
             }
             seq=seq+tbody.rows.length;
             var myrow = document.createElement("tr");
             myrow.id=seq;
             var cell1 = document.createElement("td");
             var inp_1 = document.createElement("input");
             inp_1.type = "hidden";
             inp_1.id = "head";
             inp_1.name = "head";
             inp_1.value = Acc_HeadCode;
             cell1.appendChild(inp_1);
             var text_1 = document.createTextNode(Acc_HeadCode);
             cell1.appendChild(text_1);
             myrow.appendChild(cell1);

             var cell2 = document.createElement("td");
             var inp_2 = document.createElement("input");
             inp_2.type = "hidden";
             inp_2.id = "cDr_Type";
             inp_2.name = "cDr_Type";
             inp_2.value = cDr_Type;
             cell2.appendChild(inp_2);
             var text_2 = document.createTextNode(cDr_Type);
             cell2.appendChild(text_2);
             myrow.appendChild(cell2);

             var cell4 = document.createElement("td");
             var inp_4 = document.createElement("input");
             inp_4.type = "hidden";
             inp_4.id = "grid_Sltype";
             inp_4.name = "grid_Sltype";
             inp_4.value = txtPayeeType;
             cell4.appendChild(inp_4);
             var text_4 = document.createTextNode(txtPayeeTypedesc);
             cell4.appendChild(text_4);
             myrow.appendChild(cell4);

             var cell31 = document.createElement("td");
             var inp_31 = document.createElement("input");
             inp_31.type = "hidden";
             inp_31.id = "grid_SlCode";
             inp_31.name = "grid_SlCode";
             inp_31.value = txtPayeeCodeLoad;
             cell31.appendChild(inp_31);
             var text_31 = document.createTextNode(txtPayeeCode);
             cell31.appendChild(text_31);
             myrow.appendChild(cell31);

            
             var cell3 = document.createElement("td");
             var inp_3 = document.createElement("input");
             inp_3.type = "hidden";
             inp_3.id = "grid_amt";
             inp_3.name = "grid_amt";
             inp_3.value = txt_amt;
             cell3.appendChild(inp_3);
             var text_3 = document.createTextNode(txt_amt);
             cell3.appendChild(text_3);
             myrow.appendChild(cell3);
            
             var cell32 = document.createElement("td");
             var inp_32 = document.createElement("input");
             inp_32.type = "hidden";
             inp_32.id = "grid_part";
             inp_32.name = "grid_part";
             inp_32.value = Particulars;
             cell32.appendChild(inp_32);
             var text_32 = document.createTextNode(Particulars);
             cell32.appendChild(text_32);
             myrow.appendChild(cell32);
            
          
            
             cell2=document.createElement("TD");
             var adj_year=document.createElement("input");
             adj_year.type="hidden";
             adj_year.name="adj_year";
             adj_year.value=items14;
             cell2.appendChild(adj_year);
              var currentText=document.createTextNode(items14);
             cell2.appendChild(currentText);
             myrow.appendChild(cell2);
          
           cell2=document.createElement("TD");
           var adj_month=document.createElement("input");
           adj_month.type="hidden";
           adj_month.name="adj_month";
           adj_month.value=items15;
           cell2.appendChild(adj_month);
            var currentText=document.createTextNode(items15);
           cell2.appendChild(currentText);
           myrow.appendChild(cell2);
          
         cell2=document.createElement("TD");
         var doc_type=document.createElement("input");
         doc_type.type="hidden";
         doc_type.name="doc_type";
         doc_type.value=items16;
         cell2.appendChild(doc_type);
          var currentText=document.createTextNode(items16);
         cell2.appendChild(currentText);
         myrow.appendChild(cell2);

         cell2=document.createElement("TD");
         var doc_no=document.createElement("input");
         doc_no.type="hidden";
         doc_no.name="doc_no";
         doc_no.value=items17;
         cell2.appendChild(doc_no);
         var currentText=document.createTextNode(items17);
         cell2.appendChild(currentText);
         myrow.appendChild(cell2);
            
             tbody.appendChild(myrow);
              document.getElementById("txtAcc_HeadCode").value="";
              document.getElementById("txtAcc_HeadDesc").value="";
              document.getElementById("txt_amt").value="";
              document.getElementById("txtParticular").value="";

                document.getElementById("adjyear").value="";
                 document.getElementById("adjmonth").value="";
          
            document.getElementById("paymentreceipt").value="";
            document.getElementById("receiptno").value="";
           
            document.getElementById("paymentreceipt1").value="";
            document.getElementById("receiptno1").value="";
             }
     }
	 
	 else {
		alert("Not allowed this Head Code"+Acc_HeadCode);
	document.getElementById("txtAcc_HeadCode").value="";
	document.getElementById("txtAcc_HeadCode").focus();
	}
}
function unchk(s,cmd)
{
	
	 var sele=document.getElementsByName("chk").length;

	 if(cmd==2){
	  for(var j=0;j<sele;j++)
     { 
		
		    //var m=parseInt(s)-1;
	if(sele == 1)	{
		 if(document.frmGPF_RJV_Req2.chk.checked==false) 
    		 document.frmGPF_RJV_Req2.hid_o.value='UnChecked';
    	 else if(document.frmGPF_RJV_Req2.chk.checked==true) 
    		 document.frmGPF_RJV_Req2.hid_o.value='Checked';
    	 
    	   
	}else{
         if(j==s){
        	
        	 if(document.frmGPF_RJV_Req2.chk[j].checked==false) 
        		 document.frmGPF_RJV_Req2.hid_o[j].value='UnChecked';
        	 else if(document.frmGPF_RJV_Req2.chk[j].checked==true) 
        		 document.frmGPF_RJV_Req2.hid_o[j].value='Checked';
        	 
        	    document.frmGPF_RJV_Req2.chk[j].disabled=false;
        	  
         }else{
        	  document.frmGPF_RJV_Req2.hid_o[j].value='unChecked';
        
        document.frmGPF_RJV_Req2.chk[j].disabled=true;
     
         }
     }
     }
	 }else if(cmd==1){

		  for(var j=0;j<sele;j++)
	     { 
			   //var m=parseInt(s)-1;
			  
			   if(sele ==1){

					 if(document.frmGPF_RJV_Req1.chk.checked==true) 
			    		 document.frmGPF_RJV_Req1.hid.value='Checked';
			    	 else if(document.frmGPF_RJV_Req1.chk.checked==false) 
			    		 document.frmGPF_RJV_Req1.hid.value='UnChecked';
			    	 
			    	   
				
			   }else{
	         if(parseInt(j)==parseInt(s)){
	        	
	        	  if(document.frmGPF_RJV_Req1.chk[j].checked==true)
	        	{
	        	    document.frmGPF_RJV_Req1.hid[j].value='Checked';
	        	}else{
	        		  document.frmGPF_RJV_Req1.hid[j].value='UnChecked';
	        	}
	        	    document.frmGPF_RJV_Req1.chk[j].disabled=false;
	         }else{
	       
	        document.frmGPF_RJV_Req1.hid[j].value='UnChecked';
	        document.frmGPF_RJV_Req1.chk[j].disabled=true;
	         }
	     }
	     }
		 
	 }
}
var winAcc_Bank_No1;    
function MainAccNopopupcs()
{
	
	
     Bank_popup_flag1=true;
  if (winAcc_Bank_No1 && winAcc_Bank_No1.open && !winAcc_Bank_No1.closed) 
    {
      winAcc_Bank_No1.resizeTo(500,500);
      winAcc_Bank_No1.moveTo(250,250); 
       winAcc_Bank_No1.focus();
    }
    else
    {
        winAcc_Bank_No1=null;
    }
    //var Office_code=document.getElementById("cmbOffice_code").value;  
    var txtModule_Type="MF004";
    var cr_dr_indi="DR";
    var unspent_OR_col="OPR-NRDWP-Support";
   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var txtSub_Office_code=0;
    winAcc_Bank_No1= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised1.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&txtSub_Office_code="+txtSub_Office_code+"&unspent_OR_col="+unspent_OR_col,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No1.moveTo(250,250);  
    winAcc_Bank_No1.focus();
}
function doParentAcc_NOcp(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
	
	document.getElementById("cap3_img").style.display="block";
   if(Bank_popup_flag1==true)
   {
       document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
       document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtBankId").value=bankid;
       document.getElementById("txtBranchId").value=br_id;
       document.getElementById("txtBankName").value=B_name;
       Bank_popup_flag="";
       return true;
   }
  else if(Bank_popup_flag1==false)
  {
      
	  Bank_popup_flag1="";
       return true;
   }
}


