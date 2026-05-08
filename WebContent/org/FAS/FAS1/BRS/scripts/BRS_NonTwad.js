var seq=0;


/** Get Browser Object */
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
function LoadMonthYear(path) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	//var txtCB_Year = document.getElementById("txtCB_Year").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	
	var url = path
			+ "/BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

	//alert(url);
	var req = getTransport();

	req.open("POST", url, true);

	req.onreadystatechange = function() {
		manipulate(req);
	}
	req.send(null);
	
}
function manipulate(req) {

	if (req.readyState == 4) {
		if (req.status == 200) {

			var baseResponse = req.responseXML
					.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "LoadMonthYear") {
				LoadMonthYear1(baseResponse);
			}
		}
	}
}
function LoadMonthYear1(baseResponse) {
	
	var txtCB_Year1 = document.getElementById("txtCB_Year").value;
	var txtCB_Month1 = document.getElementById("txtCB_Month").value;
	txtCB_Year = parseInt(txtCB_Year1);
	txtCB_Month = parseInt(txtCB_Month1);  
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		//alert("CB_Year1  CB_Month1 "+CB_Year1+"-"+CB_Month1);
		//alert("CB_Year  "+CB_Year1);
		//alert(CB_Month1);
		
			
		
		//document.getElementById("txtCB_Year").value=CB_Year1;
		//document.getElementById("txtCB_Month").value=CB_Month1;
		
		/* if(CB_Year1<txtCB_Year1)
                {
                alert("CashBook Year should be less than start month and year");
                document.getElementById("txtCB_Year").value="";
                }
                else if(CB_Year1>txtCB_Year1)
                {
                
                }
                else if(CB_Year1==txtCB_Year1)
                {
           
                    if(CB_Month1<=txtCB_Month)
                    {
                    alert("CashBook Month should be less than start month and year");
                     document.getElementById("txtCB_Month").value="";
                    }
                    
                } */  
		if(CB_Year1<txtCB_Year)
         {
         alert("CashBook Year should be less than start month and year");
         document.getElementById("txtCB_Year").value="";
         }
         else if(CB_Year1>txtCB_Year)
         {
         
         }
         else if(CB_Year1==txtCB_Year)
         {
    
             if(CB_Month1<=txtCB_Month)
             {
             alert("CashBook Month should be less than start month and year");
              document.getElementById("txtCB_Month").value="";
             }
             
         }
		loadOtherDetails();

	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");		
	} else {
		alert("Failed to Load Month and Year");		
	}
}


function checkMonthYear()
{
 var pbDate=document.getElementById("txtPassBook_date").value;
 var txtCB_Year=document.getElementById("txtCB_Year").value;
 var txtCB_Month=document.getElementById("txtCB_Month").value;
 var splittedDate=pbDate.split("/");
 
 if(parseInt(splittedDate[2])==txtCB_Year)
 {
     if(parseInt(splittedDate[1])==parseInt(txtCB_Month))
     {
     
     }
     else
     {
       alert("Please Enter PassBookDate between CashBookyear and Month");
       document.getElementById("txtPassBook_date").value="";
     }
 }
 else 
 {
        alert("Please Enter PassBookDate between CashBookyear and Month::::::");
        document.getElementById("txtPassBook_date").value="";
 }
 
}

function check_leng(param,val)
{	 
		 if((val.length)>=190)
		 {
			   if(param=='Details')			  
				  	alert("Please Enter Details below 200 characters");			           			  
			   else			  
				    alert("Please Enter Paticulars below 200 characters");				  	  
			  
		 }
		
}

function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
         // allow "." for one time 
         if(charCode==46)
         {
                        //	("Position of . "+item.value.indexOf("."));
                        if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                        else return false;
         }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57)))
         {
                		// to avoid over flow
                        if(item.value.indexOf(".")<0)
                        {
                        		//			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                        // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0)
                        {
                        		//	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
         }else
         {
                        return false;
         }
}


/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

function checkNull()
{
		 if(document.getElementById("cmbAcc_UnitCode").value=="")
		 {
			   alert("Select the Account Unit code");
			   return false;    
		 }
		 if(document.getElementById("cmbOffice_code").value=="")
		 {
			   alert("Select the Office Code");
			   return false;
		 }
		 if(document.getElementById("txtCB_Year").value=="")
		 {
			   alert("Enter Cashbook Year");
			   return false;    
		 }
		 if(document.getElementById("txtCB_Month").value=="")
		 {
			 //  alert("Enter Cashbook Month");
			   return false;    
		 }
		 if(document.getElementById("cmbBankAccNo").value=="")
		 {
			   alert("Please Select Bank Acc.No.");
			   return false;
		 }		
		 if(document.getElementById("txtPassBook_date").value=="")
		 {
			   alert("Enter Pass Book Date");
			   return false;
		 }
		 if(document.getElementById("txtCr_Amount").value=="" && document.getElementById("txtDr_Amount").value=="")
		 {
                            alert("Enter CR Amount or DR Amount");
			   return false;
                	   
		 }
		 if(document.getElementById("txtDr_Amount").value=="" && document.getElementById("txtCr_Amount").value=="")
		 {
                 document.getElementById("txtDr_Amount").value="";
                 document.getElementById("txtCr_Amount").value="";
			   alert("Enter Either CR or DR Amount");
			   return false;
		 }
                 var yr=document.getElementById("txtCB_Year").value;
                 var mn=document.getElementById("txtCB_Month").value;
                // alert("mn:"+mn);
                 var passdate=document.getElementById("txtPassBook_date").value.split("/");
               //  alert("passdate[1]:"+passdate[1]);
                
                 if(parseInt(passdate[2])<yr)
                 {
                	 return true; 
                 }
                 else if(parseInt(passdate[2])>yr)
                 {
                	 alert("PassbookDate Should be within Cashbookyear and Month");
                     return false;
                 }
                 else if(parseInt(passdate[2])==yr)
                 {
                	 if(parseInt(passdate[1])==mn) 
                	 {
                		 return true;
                	 }
                	 else if(parseInt(passdate[1])>mn) 
                	 {
                		 alert("PassbookDate Month Should be within CashbookMonth");
                         return false; 
                	 }
                	 else
                	 {
                		 return true;
                	 }
                 }
                 
                /* if(parseInt(passdate[2])!=yr)
                 {
                 alert("Passbook Date Should be within Cashbookyear and Cashbookmonth");
                 return false;
                 }
                 else if(parseInt(passdate[1])!=parseInt(mn))
                 {
                 alert("Passbook Date Should be within Cashbookyear & Cashbookmonth");
                 return false;
                 }   */
                 
		 else			 	
			   return true;

}
function loadVlue(){
	
	 var acc_unit_id=document.getElementById("acc_unit_id").value;
 	 var office_code=document.getElementById("office_code").value;
 	 var month=document.getElementById("month").value;
		 var year=document.getElementById("year").value;
		 var pas_dt=document.getElementById("pas_dt").value;
		 var reason=document.getElementById("reason").value;
		 var chk_no=document.getElementById("chk_no").value;
	 	 var details=document.getElementById("details").value;
	 	 var Cr_amt=document.getElementById("Cr_amt").value;
			 var Dr_amt=document.getElementById("Dr_amt").value;
			 var act_req=document.getElementById("act_req").value;
			 var BankAccNo=document.getElementById("BankAccNo").value;
			 var slno=document.getElementById("slno").value;
			 alert(acc_unit_id);
			document.getElementById("cmbAcc_UnitCode").value=acc_unit_id;
			 //alert('cmbAcc_UnitCode'+document.getElementById("cmbAcc_UnitCode").value);
			 common_LoadOffice(acc_unit_id);
			 document.getElementById("txtCB_Year").value=year;
			 document.getElementById("txtCB_Month").value=month;
			 //document.getElementById("cmbOffice_code").value=office_code;
			 LoadBankAccountNumberNew(BankAccNo);
			document.getElementById("txtPassBook_date").value=pas_dt;
			document.getElementById("txtParticular").value=reason;
			document.getElementById("txtCheque_NO").value=chk_no;
			document.getElementById("txtDetails").value=details;
			document.getElementById("txtCr_Amount").value=Cr_amt;
			document.getElementById("txtDr_Amount").value=Dr_amt;
			document.getElementById("serialno").value=slno;
			
		//alert("act_req"+act_req);
		
			var Ac_req = document.getElementsByName("action_required");
		//alert(Ac_req.length);
		if(act_req=='Y'){
		  Ac_req[0].checked=true;
		 Ac_req[1].checked=false;}
		if(act_req=='N'){
			 Ac_req[1].checked=true;
		Ac_req[0].checked=false;}
		
		 
		
		
}


function LoadBankAccountNumberNew(acc_no)
{ 
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	//   alert("cmbAcc_UnitCode:::"+cmbAcc_UnitCode);
           var url;
        
         
            url="../../../../../Common_Bank_Account_Number_Loading.kv?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   
    
           if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
           
           // alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   var res;
	   req.onreadystatechange=function()
	   {
	    	 rec=LoadBankAccountNumberRes(req);
	    	 if(rec==true)
	    		 document.getElementById("cmbBankAccNo").value=acc_no;
	    	 var val_Acc=document.getElementById("cmbBankAccNo").value;
	    	 //alert(val_Acc)
	    	 loadmode(val_Acc);
	   }   
       req.send(null);
	  return true;
}



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
            	           
            	           /** Get Combo box Object */
            	           var cmbBankAccNo = document.getElementById("cmbBankAccNo");
            	           
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
        
        }  return true;
    }

}


function clearAll()
{		 
//		 document.getElementById("cmbBankAccNo").value="";
//		 document.getElementById("txtBankName").value="";
//		 document.getElementById("txtBankID").value="";
//		 document.getElementById("txtBranchName").value="";
//		 document.getElementById("txtBranchID").value="";
//		 document.getElementById("txtOprCode").value="";
		 document.getElementById("txtParticular").value="";
		 document.getElementById("txtCheque_NO").value="";
		 document.getElementById("txtDetails").value="";
		 document.getElementById("txtCr_Amount").value="";
		 document.getElementById("txtDr_Amount").value="";
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)
{
}

function ListAll()
{
         var winemp;
         var my_window;
         var wininterval;
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
         if(document.getElementById("cmbAcc_UnitCode").value=="")
 		 {
 			    alert("Select the Account Unit code");
 			    return false;    
 		 }
 		 if(document.getElementById("cmbOffice_code").value=="")
 	 	 {
 			    alert("Select the Office Code");
 			    return false;
 	 	 }
 		 if(document.getElementById("txtCB_Year").value=="")
 		 {
 			    alert("Enter Cashbook Year");
 			    return false;    
 		 }
 		 if(document.getElementById("txtCB_Month").value=="")
 		 {
 			   // alert("Enter Cashbook Month");
 			    return false;    
 	 	 }
 		 if(document.getElementById("cmbBankAccNo").value=="")
 		 {
 			    alert("Select Bank A/C No");
 			    return false;    
 	 	 }
 		 else
 		 {
 			 //	LoadBankAccountNumber();
 			 //	clearAll();
 						
		        var ob_type="NT"; 
		        var acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
		        var office_code=document.getElementById("cmbOffice_code").value;
		        var cashbook_yr=document.getElementById("txtCB_Year").value;
		        var cashbook_mn=document.getElementById("txtCB_Month").value;
		        var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
		     
		        winemp= window.open("../../../../../org/FAS/FAS1/BRS/jsps/BRS_ListAll.jsp?OB_Type="+ob_type+"&acc_unit_id="+acc_unit_id+"&office_code="+office_code+"&cashbook_yr="+cashbook_yr+"&cashbook_mn="+cashbook_mn+"&cmbBankAccNo="+cmbBankAccNo,"List","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
		        winemp.moveTo(250,250);  
		        winemp.focus();
 		 }
}

function doParentEmp(ac,pdate,rea,cno,cdetail,cr,dr,follow,slno,yr,mn)
{		
		 
//	alert("ac::"+ac);
//	alert("pdate:"+pdate);
//	alert("rea::"+rea);
//	alert("cno::"+cno);
//	alert("cdetail::"+cdetail);
//	alert("cr::"+cr);
//	alert("dr::"+dr);
//	alert("follow::"+follow);
//	alert("slno::"+slno);
	
	document.getElementById("constantacno").value=ac;
	document.getElementById("txtPassBook_date").value=pdate;
	document.getElementById("txtParticular").value=rea;
	if(cno==0)
		cno="";
	document.getElementById("txtCheque_NO").value=cno;
	if(cdetail=="null")
		cdetail="";
		
	document.getElementById("txtDetails").value=cdetail;
	if(cr==.00)
		cr="";
	document.getElementById("txtCr_Amount").value=cr;
	if(dr==.00)
		dr="";
	document.getElementById("txtDr_Amount").value=dr;
	if(follow=="Y")
	document.frmBRSNonTwad.action_required[0].checked=true;
	else
		document.frmBRSNonTwad.action_required[1].checked=true;
	
	document.getElementById("trnyear").value=yr;
	document.getElementById("trnmonth").value=mn;
	document.getElementById("serialno").value=slno;
	  	document.getElementById("butAdd").disabled=true;
	 	document.getElementById("butUpd").disabled=false;
	 	document.getElementById("butDel").disabled=true;
	 	document.getElementById("List").disabled=false;
	
}

function loadmode(val)
{
	var bankopr=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
	
	var bankacc=(bankopr).split("-");
	document.getElementById("txtOprCode").value=bankacc[2];
	
	Bank_Branch_Name(val);
	recAlreadyExists();
}

function recAlreadyExists()
{
	 var ucode=document.getElementById("cmbAcc_UnitCode").value;
	
	 var acno=document.getElementById("cmbBankAccNo").value;
	 var url="../../../../../BRS_NonTwad?command=checkNTstatus&option=Edit&cmbAcc_UnitCode="+ucode+"&acno="+acno;			
	
	 var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    	 	LoadBRSNonTwadResponse(req);
	    }   
	    req.send(null);	 
}

function loadOtherDetails()
{
		 if(document.getElementById("cmbAcc_UnitCode").value=="")
		 {
			    alert("Select the Account Unit code");
			    return false;    
		 }
		 if(document.getElementById("cmbOffice_code").value=="")
	 	 {
			    alert("Select the Office Code");
			    return false;
	 	 }
		 if(document.getElementById("txtCB_Year").value=="")
		 {
			    alert("Enter Cashbook Year");
			    return false;    
		 }
		 if(document.getElementById("txtCB_Month").value=="")
		 {
			  //  alert("Enter Cashbook Month");
			    return false;    
	 	 }
		 else
		 {			 	
				var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
				var cmbOffice_code=document.getElementById("cmbOffice_code").value;
				var txtCB_Year=document.getElementById("txtCB_Year").value;
				var txtCB_Month=document.getElementById("txtCB_Month").value;
				var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
				var url="../../../../../BRS_NonTwad?command=LoadList&option=Edit&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&ob_type=NT";			
				//alert(url);
				var req=getTransport();
			    req.open("GET",url,true); 
			    req.onreadystatechange=function()
			    {
			    	 	LoadBRSNonTwadResponse(req);
			    }   
			    req.send(null);	 
		 }	 
}
function callServer(cmd)
{
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		 var txtCB_Year=document.getElementById("txtCB_Year").value;
		 var txtCB_Month=document.getElementById("txtCB_Month").value;
		 var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
		 var txtOprCode=document.getElementById("txtOprMode").value;
		 var txtBankID=document.getElementById("txtBankID").value;
		 var txtBranchID=document.getElementById("txtBranchID").value;
		 var txtOprCode=document.getElementById("txtOprCode").value;
		 var txtPassBook_date = document.getElementById("txtPassBook_date").value; 
	     if (txtPassBook_date == "" || txtPassBook_date == null) {
		document.getElementById("txtPassBook_date").focus();
		return false;
	     }
	    var txtParticular = document.getElementById("txtParticular").value;
	    if (txtParticular == "") {
		document.getElementById("txtParticular").focus();
	  	return false;
	     }
		 var txtCheque_NO=document.getElementById("txtCheque_NO").value;
		 var txtDetails=document.getElementById("txtDetails").value;
		 var txtCr_Amount=document.getElementById("txtCr_Amount").value;
		 var txtDr_Amount=document.getElementById("txtDr_Amount").value;
		 if(txtCr_Amount==0 && txtDr_Amount==0)
			 {
			 alert('Both Cr & Dr Amount Not allowed Zero .. ');
			 document.getElementById("txtCr_Amount").value="";
			 document.getElementById("txtDr_Amount").value="";
			 document.getElementById("txtCr_Amount").focus();
			 document.getElementById("txtDr_Amount").focus();
			 }else  if(txtCr_Amount > 0 && txtDr_Amount > 0)
			 {
				 alert('Anyone Cr or Dr Amount Should be Zero .. ');
				 document.getElementById("txtCr_Amount").value="";
				 document.getElementById("txtDr_Amount").value="";
				 document.getElementById("txtCr_Amount").focus();
				 document.getElementById("txtDr_Amount").focus();
				 }
		 if(document.forms[0].action_required[0].checked==true)
		 {
			 action_required="Y";
		 }
		 else
		 {
			 action_required="N";
		 }
		// var action_required=document.getElementById("action_required").value;
		 var clearance_entry=document.getElementById("clearance_entry").value;
		 
		 var val=checkNull();
		
		 if(cmd!='delete')
		 {
			   if(val==true)
			   {
				   
				   	 var url="cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&txtBankID="+txtBankID+"&txtBranchID="+txtBranchID+"&txtOprCode="+txtOprCode+"&txtPassBook_date="+txtPassBook_date+"&txtParticular="+txtParticular+"&txtCheque_NO="+txtCheque_NO+"&txtDetails="+txtDetails+"&txtCr_Amount="+txtCr_Amount+"&txtDr_Amount="+txtDr_Amount+"&action_required="+action_required+"&clearance_entry="+clearance_entry;
				   	
                     if(cmd=="add")
                     {
				   		 url="../../../../../BRS_NonTwad?command=add&"+url;
				   	//	 alert(url);
                     }
				   	 else
				   	 {
				   		 var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
				   		 var constantacno=document.getElementById("constantacno").value;
				   	//joe modify	 if(cmbBankAccNo==constantacno)
				   		 //{
				   			 var serialno=document.getElementById("serialno").value;
				   	/*	var trnyear=document.getElementById("trnyear").value;
				   		var trnmonth=document.getElementById("trnmonth").value;*/
				   			var trnyear=document.getElementById("txtCB_Year").value;
					   		var trnmonth=document.getElementById("txtCB_Month").value;
				   		 url="../../../../../BRS_NonTwad?command=update&"+url+"&serialno="+serialno+"&trnyear="+trnyear+"&trnmonth="+trnmonth;
				   		/// alert(url);
				   		/* }
				   		 else
				   		 {
				   			alert("Account No Should be Constant");
				   			return false;
				   		 }*/
				   	 }
				     var req=getTransport();
				     req.open("POST",url,true); 
				     req.onreadystatechange=function()
				     {
				    	 	LoadBRSNonTwadResponse(req);
				     }   
				     req.send(null);	 
				   		 
				   	 
			   }
		 }
		 else
		 {
			 var serialno=document.getElementById("serialno").value;
			   var url="../../../../../BRS_NonTwad?command=delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&serialno="+serialno;
			   var req=getTransport();
			   req.open("POST",url,true); 
			   req.onreadystatechange=function()
			   {
				   	LoadBRSNonTwadResponse(req);
			   }   
			   req.send(null);	
		 }
		 
}

function LoadBRSNonTwadResponse(req)
{
		 if(req.readyState==4)
		 {
			   if(req.status==200)
			   {  
		             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
		             var tagcommand=baseResponse.getElementsByTagName("command")[0];
		             var Command=tagcommand.firstChild.nodeValue;
		             var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		             if(Command=="add")
		             {
		            	 	if(flag=="success")
		            	 	{
		            	 			alert("Inserted Successfully");
		            	 			clearAll();
		            	 	}
		            	 	else
		            	 			alert("Insertion Failure");
		             }
		             else if(Command=="update")
		             {
		            	 	if(flag=="success")
		            	 	{
		            	 			alert("Updated Successfully");
		            	 			clearAll();
		            	 			
		            	 			window.close();
		            	 	}
		            	 	else
		            	 			alert("Updation Failure");
		             }
		             else if(Command=="delete")
		             {
		            	 	if(flag=="success")
		            	 	{
		            	 			alert("Deleted Successfully");
		            	 			clearAll();window.close();
		            	 	}
		            	 	else
		            	 			alert("Deletion Failure");
		             }
		             else if(Command=="LoadList")
		             {
		            	 	if(flag=="success")
		            	 	{
		            	 		var obStatus=baseResponse.getElementsByTagName("obStatus")[0].firstChild.nodeValue;
		            	 		if(obStatus=="null"){
			            	 		var items=new Array();
		             			 	items[0]=baseResponse.getElementsByTagName("acc_no")[0].firstChild.nodeValue;
			                        items[1]=baseResponse.getElementsByTagName("bank_name")[0].firstChild.nodeValue;
			                        items[2]=baseResponse.getElementsByTagName("branch_name")[0].firstChild.nodeValue;
			                        items[3]=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;     
			                        items[4]=baseResponse.getElementsByTagName("passbook_dt")[0].firstChild.nodeValue;
			                        items[5]=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
			                        items[6]=baseResponse.getElementsByTagName("cheque_no")[0].firstChild.nodeValue;
			                        items[7]=baseResponse.getElementsByTagName("cheque_details")[0].firstChild.nodeValue;
			                        items[8]=baseResponse.getElementsByTagName("cr_amount")[0].firstChild.nodeValue;
			                        items[9]=baseResponse.getElementsByTagName("dr_amount")[0].firstChild.nodeValue;  
			                        
			                   
			               		    document.getElementById("txtPassBook_date").value=items[4];
			               		    document.getElementById("txtParticular").value=items[5];
			               		    document.getElementById("txtCheque_NO").value=items[6];
			               		    document.getElementById("txtDetails").value=items[7];
			               		    document.getElementById("txtCr_Amount").value=items[8];
			               		    document.getElementById("txtDr_Amount").value=items[9];
			                        
			                        document.getElementById("butAdd").disabled=true;
		            	 		 	document.getElementById("butUpd").disabled=false;
		            	 		 	document.getElementById("butDel").disabled=false;
		            	 		}
		            	 		else
		            	 		{
		            	 			var items=new Array();
		             			 	items[0]=baseResponse.getElementsByTagName("acc_no")[0].firstChild.nodeValue;
			                        items[1]=baseResponse.getElementsByTagName("bank_name")[0].firstChild.nodeValue;
			                        items[2]=baseResponse.getElementsByTagName("branch_name")[0].firstChild.nodeValue;
			                        items[3]=baseResponse.getElementsByTagName("acc_head_code")[0].firstChild.nodeValue;     
			                        items[4]=baseResponse.getElementsByTagName("passbook_dt")[0].firstChild.nodeValue;
			                        items[5]=baseResponse.getElementsByTagName("particulars")[0].firstChild.nodeValue;
			                        items[6]=baseResponse.getElementsByTagName("cheque_no")[0].firstChild.nodeValue;
			                        items[7]=baseResponse.getElementsByTagName("cheque_details")[0].firstChild.nodeValue;
			                        items[8]=baseResponse.getElementsByTagName("cr_amount")[0].firstChild.nodeValue;
			                        items[9]=baseResponse.getElementsByTagName("dr_amount")[0].firstChild.nodeValue;  
			                        
			                   
			               		    document.getElementById("txtPassBook_date").value=items[4];
			               		    document.getElementById("txtParticular").value=items[5];
			               		    document.getElementById("txtCheque_NO").value=items[6];
			               		    document.getElementById("txtDetails").value=items[7];
			               		    document.getElementById("txtCr_Amount").value=items[8];
			               		    document.getElementById("txtDr_Amount").value=items[9];
			                        
			               		    document.getElementById("butAdd").disabled=true;
		            	 		 	document.getElementById("butUpd").disabled=true;
		            	 		 	document.getElementById("butDel").disabled=true;
		            	 		 	document.getElementById("butCan").disabled=true;
		            	 		 	document.getElementById("butExit").disabled=false;
		            	 		}
		            	 	}
		            	 	else
		            	 	{
		            	 		
		            	 		var disabled=baseResponse.getElementsByTagName("disabled")[0].firstChild.nodeValue;
		            	 		if(disabled=="true")
		            	 		{
		            	 			
		            	 			document.getElementById("butAdd").disabled=true;
		            	 		 	document.getElementById("butUpd").disabled=true;
		            	 		 	document.getElementById("butDel").disabled=true;
		            	 		 	document.getElementById("butCan").disabled=true;
		            	 		 	document.getElementById("butExit").disabled=false;
		            	 		}
		            	 		else
		            	 		{
		            	 		 	document.getElementById("butAdd").disabled=false;
		            	 		 	document.getElementById("butUpd").disabled=true;
		            	 		 	document.getElementById("butDel").disabled=true;
		            	 	    }
		            	 	}
		             }
		             else if(Command=="checkNTstatus")
		             {
		            	 var disabled=baseResponse.getElementsByTagName("disabled")[0].firstChild.nodeValue;
	            	 		if(disabled=="true")
	            	 		{
	            	 			
	            	 			document.getElementById("butAdd").disabled=true;
	            	 		 	document.getElementById("butUpd").disabled=true;
	            	 		 	document.getElementById("butDel").disabled=true;
	            	 		 	document.getElementById("butCan").disabled=true;
	            	 		 	document.getElementById("butExit").disabled=false;
	            	 		}
	            	 		else
	            	 		{
	            	 		 	document.getElementById("butAdd").disabled=false;
	            	 		 	document.getElementById("butUpd").disabled=true;
	            	 		 	document.getElementById("butDel").disabled=true;
	            	 	    }
		             }
			   }
		 }
}