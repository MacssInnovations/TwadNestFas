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
function clearAll_test()
{
//alert("clearAll_test");
document.getElementById("cmbBankAccNo").value="";
document.getElementById("txtBankName").value="";
document.getElementById("txtBranchName").value="";
document.getElementById("OB1").value="";
document.getElementById("OB2").value="";
document.getElementById("OB3").value="";

}
var bank_ac_no =new Array();
var acc_desc   =new Array();
var bank_name  = new Array();
var branch_name = new Array();
var bank_id  = new Array();
var branch_id = new Array();
var opr_mode  = new Array();
function LoadMonthYear(path) {
	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (txtCB_Year == "") {
		alert("Enter Cash Book Year in the Field");
		document.frmBRSTwad.txtCB_Year.focus();
	}
	else if(cmbBankAccNo=="")
	{
		alert("Select Account No");
		document.frmBRSTwad.cmbBankAccNo.focus();	
	}
	else{
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
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
	if (flag == "success") {
		var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
		var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
		document.getElementById("txtCB_Year").value=CB_Year1;
		document.getElementById("txtCB_Month").value=(CB_Month1-1);
	} else if (flag == "NoData") {
		alert("First Set BRS Initiation Month and Year");		
	} else {
		alert("Failed to Load Month and Year");		
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
			   alert("Enter Cashbook Month");
			   return false;    
		 }
		 if(document.getElementById("cmbBankAccNo").value=="")
		 {
			   alert("Please Select Bank Acc.No.");
			   return false;
		 }			 
		 else
		 {			   
			   var acc_no=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text.split("-");                        
			   if(acc_no[0]=='COL')
			   {
				   	 if(document.getElementById("OB1").value=="")
				   	 {
						    alert("Enter Opening Balance -(Part1_OB)");
						    return false;
				   	 }
				   	 else
				   		 	return true;
			   }
			   else if(acc_no[0]=='OPR')
			   {
				     if(document.getElementById("OB2").value=="")
				   	 {
						    alert("Enter Opening Balance -(Part2A_OB)");
						    return false;
				   	 }
				     if(document.getElementById("OB3").value=="")
				   	 {
						    alert("Enter Opening Balance -(Part2B_OB)");
						    return false;
				   	 }
				   	 else
				   		 	return true;
			   }
			   else
			   {
				   	 if(document.getElementById("OB1").value=="" && document.getElementById("OB2").value=="" && document.getElementById("OB3").value=="")
				   	 {
						    alert("Enter Opening Balance ");
						    return false;
				   	 }
				   	 else
				   		    return true;
			   }
		 }

}

function clearAll()
{		 
		 document.getElementById("cmbBankAccNo").value="";
		 document.getElementById("txtBankName").value="";
		 document.getElementById("txtBankID").value="";
		 document.getElementById("txtBranchName").value="";
		 document.getElementById("txtBranchID").value="";
		 document.getElementById("OB1").value="";
		 document.getElementById("OB2").value="";
		 document.getElementById("OB3").value="";
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
        	 	winemp=null
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
 			    alert("Enter Cashbook Month");
 			    return false;    
 	 	 }
 		 else
 		 {
 			 	LoadBankAccountNumber();
 			 	clearAll();
		        var ob_type="T"; 
		        var acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
		        var office_code=document.getElementById("cmbOffice_code").value;
		        var cashbook_yr=document.getElementById("txtCB_Year").value;
		        var cashbook_mn=document.getElementById("txtCB_Month").value;
		        winemp= window.open("../../../../../org/FAS/FAS1/BRS/jsps/BRS_ListAll.jsp?OB_Type="+ob_type+"&acc_unit_id="+acc_unit_id+"&office_code="+office_code+"&cashbook_yr="+cashbook_yr+"&cashbook_mn="+cashbook_mn,"List","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
		        winemp.moveTo(250,250);  
		        winemp.focus();
 		 }
}

function ListAll_ob()
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
	 if(document.getElementById("txtCB_Year_from").value=="")
	 {
		    alert("Enter From Cashbook Year");
		    return false;    
	 }
	 if(document.getElementById("txtCB_Month_from").value=="")
	 {
		    alert("Enter From Cashbook Month");
		    return false;    
	 }
	if(document.getElementById("txtCB_Year_to").value=="")
	 {
		    alert("Enter To Cashbook Year");
		    return false;    
	 }
	 if(document.getElementById("txtCB_Month_to").value=="")
	 {
		    alert("Enter To Cashbook Month");
		    return false;    
	 }
	 else
	 {
		 	
		 	//LoadBankAccountNumber();
		 	//clearAll();
	        var ob_type="ob_list"; 
	        var acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
	        
	        var office_code=document.getElementById("cmbOffice_code").value;
	        var cashbook_yr_from=document.getElementById("txtCB_Year_from").value;
	        var cashbook_mn_from=document.getElementById("txtCB_Month_from").value;
	        var cashbook_yr_to=document.getElementById("txtCB_Year_to").value;
	        var cashbook_mn_to=document.getElementById("txtCB_Month_to").value;
	        var BankAccNo=document.getElementById("cmbBankAccNo").value;
	      //  alert(BankAccNo);
	        winemp= window.open("../../../../../org/FAS/FAS1/BRS/jsps/BRS_ListAll_OB.jsp?OB_Type="+ob_type+"&acc_unit_id="+acc_unit_id+"&office_code="+office_code+"&cashbook_yr_from="+cashbook_yr_from+"&cashbook_mn_from="+cashbook_mn_from+"&cashbook_yr_to="+cashbook_yr_to+"&cashbook_mn_to="+cashbook_mn_to+"&BankAccNo="+BankAccNo,"List","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
	        winemp.moveTo(250,250);  
	        winemp.focus();
	 }

}

function ListAll_New()
{
//        alert("coming to ListAll_New"); 
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
 		 if(document.getElementById("txtCB_Year_from").value=="")
 		 {
 			    alert("Enter From Cashbook Year");
 			    return false;    
 		 }
 		 if(document.getElementById("txtCB_Month_from").value=="")
 		 {
 			    alert("Enter From Cashbook Month");
 			    return false;    
 	 	 }
 		if(document.getElementById("txtCB_Year_to").value=="")
		 {
			    alert("Enter To Cashbook Year");
			    return false;    
		 }
		 if(document.getElementById("txtCB_Month_to").value=="")
		 {
			    alert("Enter To Cashbook Month");
			    return false;    
	 	 }
 		 else
 		 {
 			 	
 			 	LoadBankAccountNumber();
 			 	//clearAll();
		        var ob_type="T"; 
		        var acc_unit_id=document.getElementById("cmbAcc_UnitCode").value;
		        
		        var office_code=document.getElementById("cmbOffice_code").value;
		        var cashbook_yr_from=document.getElementById("txtCB_Year_from").value;
		        var cashbook_mn_from=document.getElementById("txtCB_Month_from").value;
		        var cashbook_yr_to=document.getElementById("txtCB_Year_to").value;
		        var cashbook_mn_to=document.getElementById("txtCB_Month_to").value;
		        var BankAccNo=document.getElementById("cmbBankAccNo").value;
//		        alert(BankAccNo);
		        winemp= window.open("../../../../../org/FAS/FAS1/BRS/jsps/BRS_ListAll.jsp?OB_Type="+ob_type+"&acc_unit_id="+acc_unit_id+"&office_code="+office_code+"&cashbook_yr_from="+cashbook_yr_from+"&cashbook_mn_from="+cashbook_mn_from+"&cashbook_yr_to="+cashbook_yr_to+"&cashbook_mn_to="+cashbook_mn_to+"&BankAccNo="+BankAccNo,"List","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
		        winemp.moveTo(250,250);  
		        winemp.focus();
 		 }
}
/** Main Function */
function LoadBankAccountNumber()
{ 
	   var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	 
	   var url="../../../../../Common_Bank_Account_Number_Loading.kv?command=LoadBankAccountNumber&cmbAcc_UnitCode="+cmbAcc_UnitCode;
	   if(document.getElementById("txtOprCode"))
	   {
		 	var cashbook_yr=document.getElementById("txtCB_Year").value;	 
		 	var cashbook_mn=document.getElementById("txtCB_Month").value;	 
		 	url+="&option=nontwad";
	   }
           
            //alert(url);
	   var req=getTransport();
	   req.open("GET",url,true); 
	   req.onreadystatechange=function()
	   {
	    	 LoadBankAccountNumberRes(req);
	   }   
       req.send(null);
	  
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
        
        }
    }
}


function loadopr_col()
{
	 var cmbBankAccNo=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
	// alert("ttt::"+cmbBankAccNo);
	 var acc=cmbBankAccNo.split("-");
	 if(acc[0]=="COL")
	 {
		 document.getElementById("OB2").disabled=true;
		 document.getElementById("OB3").disabled=true;
		 document.getElementById("OB1").disabled=false;
	 }
	 else if(acc[0]=="OPR")
	 {
		 document.getElementById("OB2").disabled=false;
		 document.getElementById("OB3").disabled=false;
		 document.getElementById("OB1").disabled=true;
	 }
	 else
	 {
		 document.getElementById("OB2").disabled=false;
		 document.getElementById("OB3").disabled=false;
		 document.getElementById("OB1").disabled=false; 
	 }
}

function Bank_Branch_Name()
{

    accnumber = document.getElementById("cmbBankAccNo").value;    
  //  alert(accnumber);
        
    for(var k=0;k<bank_ac_no.length;k++)
    {
		if ( bank_ac_no[k]== accnumber  )
		{
			/* Display Bank Name */
			document.getElementById("txtBankName").value =bank_name[k];
			
			/* Display Branch Name */
			if ( branch_name[k]!='null')
			{
			  document.getElementById("txtBranchName").value =branch_name[k];
			}
			/* Display Bank ID */			
			document.getElementById("txtBankID").value =bank_id[k];
			
			/* Branch ID */			
			document.getElementById("txtBranchID").value =branch_id[k];
			
			/* Operation Mode */			
			document.getElementById("txtOprMode").value =opr_mode[k];			
			if(document.getElementById("txtOprCode"))
			{
					var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
					var url="../../../../../Common_Bank_Account_Number_Loading.kv?command=LoadOprCode&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&bank_id="+bank_id[k]+"&branch_id="+branch_id[k]+"&opr_mode="+opr_mode[k]+"&acc_no="+bank_ac_no[k];					
					var req=getTransport();
				    req.open("GET",url,true); 
				    req.onreadystatechange=function()
				    {
				    	 	LoadBankAccountNumberRes(req);
				    }   
				    req.send(null);
			}
		}
		
    }
	 
}


function doParentEmp(bid)
{		
		 document.getElementById("cmbBankAccNo").value=bid;
		 Bank_Branch_Name(bid);
		 loadOtherDetails();
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
			    alert("Enter Cashbook Month");
			    return false;    
	 	 }
		
		 
		 else
		 {			 	
				var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
				var cmbOffice_code=document.getElementById("cmbOffice_code").value;
				var txtCB_Year=document.getElementById("txtCB_Year").value;
				var txtCB_Month=document.getElementById("txtCB_Month").value;
				var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
				var url="../../../../../BRS_Twad?command=LoadList&option=Edit&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&ob_type=T";				
				var req=getTransport();
			    req.open("GET",url,true); 
			    req.onreadystatechange=function()
			    {
			    	 	LoadBRSNonTwadResponse(req);
			    }   
			    req.send(null);	 
		 }	 
}

function listPDF()
{
	//alert("listPDF ");
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
		 if(document.getElementById("txtCB_Year_from").value=="")
		 {
			    alert("Enter Cashbook Year");
			    return false;    
		 }
		 if(document.getElementById("txtCB_Month_from").value=="")
		 {
			    alert("Enter Cashbook Month");
			    return false;    
	 	 }
		 if(document.getElementById("txtCB_Year_to").value=="")
		 {
			    alert("Enter Cashbook Year");
			    return false;    
		 }
		 if(document.getElementById("txtCB_Month_to").value=="")
		 {
			    alert("Enter Cashbook Month");
			    return false;    
	 	 }
		 
		 else
		 {		
			// alert("list pdf ");
				var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
				var cmbOffice_code=document.getElementById("cmbOffice_code").value;
				/*var txtCB_Year=document.getElementById("txtCB_Year").value;
				var txtCB_Month=document.getElementById("txtCB_Month").value;*/
				
				 var cashbook_yr_from=document.getElementById("txtCB_Year_from").value;
			        var cashbook_mn_from=document.getElementById("txtCB_Month_from").value;
			        var cashbook_yr_to=document.getElementById("txtCB_Year_to").value;
			        var cashbook_mn_to=document.getElementById("txtCB_Month_to").value;
				
				var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
				var url="../../../../../BRS_Twad_Report?command=PDFBANK&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+
				"&txtCB_Year_from="+cashbook_yr_from+"&txtCB_Month_from="+cashbook_mn_from+
				"&txtCB_Year_to="+cashbook_yr_to+"&txtCB_Month_to="+cashbook_mn_to+
				"&cmbBankAccNo="+cmbBankAccNo+"&ob_type=T";		
				
				

				   document.frmBRSTwad_OBList.action=url;
			       document.frmBRSTwad_OBList.submit();

				
				
				/*var req=getTransport();
			    req.open("POST",url,true);
			    alert(url);
			    req.onreadystatechange=function()
			    {
			    	 	//LoadBRSNonTwadResponse(req);
			    }   
			    req.send(null);*/	 
		 }	 
}
function callMonth()
{
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	var url = "../../../../../BRS_Start_Month_and_Year?command=LoadMonthYear&cmbAcc_UnitCode="
			+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code+"&cmbBankAccNo="+cmbBankAccNo;

	//alert(url);
	var req = getTransport();
	req.open("POST", url, true);

	req.onreadystatechange = function() {
		if (req.readyState == 4) {
			if (req.status == 200) {

				var baseResponse = req.responseXML
						.getElementsByTagName("response")[0];
				var tagCommand = baseResponse.getElementsByTagName("command")[0];

				var command = tagCommand.firstChild.nodeValue;
				if (command == "LoadMonthYear") {
					
					var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
				       
					if (flag == "success") {
						
						var CB_Year1 = baseResponse.getElementsByTagName("CB_Year")[0].firstChild.nodeValue;
						var CB_Month1 = baseResponse.getElementsByTagName("CB_Month")[0].firstChild.nodeValue;
						
						document.getElementById("txtCB_Year").readOnly="true";
					/*	var mn;
						var cbyear;
						if(CB_Month1==1){
							cbyear=parseInt(CB_Year1)-1;
							mn=12;
						}else{
							cbyear=CB_Year1;
						mn=parseInt(CB_Month1)-1;
						}  */
						document.getElementById("txtCB_Year").value=CB_Year1;
						document.getElementById("txtCB_Month").value=CB_Month1;
						document.getElementById("txtCB_Month").setAttribute('readonly','readonly');
						
						loadOtherDetails();
						
						
					} else if (flag == "NoData") {
						alert("First Set BRS Initiation Month and Year");
						document.getElementById("txtCB_Year").value="";
						document.getElementById("txtCB_Month").value="";
					} else {
						alert("Failed to Load Month and Year");		
					}
				}
			}
		}
	}
	req.send(null);
	
}

function callServer(cmd)
{
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		 var txtCB_Year=document.getElementById("txtCB_Year").value;
		 var txtCB_Month=document.getElementById("txtCB_Month").value;
		 var cmbBankAccNo=document.getElementById("cmbBankAccNo").value;
		 //var txtOprCode=document.getElementById("txtOprMode").value
		 var txtBankID=document.getElementById("txtBankID").value;
		 var txtBranchID=document.getElementById("txtBranchID").value;
		 var accNo=document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
		// alert(accNo);
		 var splac=accNo.split("-");
		// alert(splac);
		if(splac[2]=="OPR")
		{
			var OB1=0;
			var OB2=document.getElementById("OB2").value;
			 var OB3=document.getElementById("OB3").value;	
		}else if(splac[2]=="FDW")
		{
			var OB1=document.getElementById("OB1").value;
			var OB2=document.getElementById("OB2").value;
			 var OB3=document.getElementById("OB3").value;	
		}else if(splac[2]=="SCH")
		{
			var OB1=document.getElementById("OB1").value;
			var OB2=document.getElementById("OB2").value;
			 var OB3=document.getElementById("OB3").value;	
		}
		else
		{
			var OB1=document.getElementById("OB1").value;	
			var OB2=0;
			var OB3=0;
		}
		 
		 
		 var val=checkNull();
		 if(cmd!='delete')
		 {
			   if(val==true)
			   {
				   	 var url="cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo+"&txtBankID="+txtBankID+"&txtBranchID="+txtBranchID+"&OB1="+OB1+"&OB2="+OB2+"&OB3="+OB3;
				   //alert("url:::"+url);
				   	 if(cmd=="add")
				   		 url="../../../../../BRS_Twad?command=add&"+url;
				   	 else
				   		 url="../../../../../BRS_Twad?command=update&"+url;
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
			   var url="../../../../../BRS_Twad?command=delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbBankAccNo="+cmbBankAccNo;
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
		            	 			alert("Record Already Exists For This Account No");
		             }
		             else if(Command=="update")
		             {
		            	 	if(flag=="success")
		            	 	{
		            	 			alert("Updated Successfully");
		            	 			clearAll();
		            	 	}
		            	 	else
		            	 			alert("Updation Failure");
		             }
		             else if(Command=="delete")
		             {
		            	 	if(flag=="success")
		            	 	{
		            	 			alert("Deleted Successfully");
		            	 			clearAll();
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
			                        items[4]=baseResponse.getElementsByTagName("OB1")[0].firstChild.nodeValue;
			                        items[5]=baseResponse.getElementsByTagName("OB2")[0].firstChild.nodeValue;
			                        items[6]=baseResponse.getElementsByTagName("OB3")[0].firstChild.nodeValue;  
			                        
			                   
			               		    document.getElementById("OB1").value=items[4];
			               		    document.getElementById("OB2").value=items[5];
			               		    document.getElementById("OB3").value=items[6];
			                        
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
			                        items[4]=baseResponse.getElementsByTagName("OB1")[0].firstChild.nodeValue;
			                        items[5]=baseResponse.getElementsByTagName("OB2")[0].firstChild.nodeValue;
			                        items[6]=baseResponse.getElementsByTagName("OB3")[0].firstChild.nodeValue;  
			                        
			                   
			               		    document.getElementById("OB1").value=items[4];
			               		    document.getElementById("OB2").value=items[5];
			               		    document.getElementById("OB3").value=items[6];
			                        
			                        document.getElementById("butAdd").disabled=true;
		            	 		 	document.getElementById("butUpd").disabled=true;
		            	 		 	document.getElementById("butDel").disabled=true;
		            	 		 	document.getElementById("butCan").disabled=true;
		            	 		 	document.getElementById("butExit").disabled=false;
		            	 		}
		            	 	}
		            	 	else
		            	 	{
			            	 		document.getElementById("OB1").value="";
			               		    document.getElementById("OB2").value="";
			               		    document.getElementById("OB3").value="";
		            	 		 	document.getElementById("butAdd").disabled=false;
		            	 		 	document.getElementById("butUpd").disabled=true;
		            	 		 	document.getElementById("butDel").disabled=true;
		            	 	}
		             }
			   }
		 }
}