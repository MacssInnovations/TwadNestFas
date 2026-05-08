var seq=0;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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

function cmn_response(req)
{
	if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
         
            
           if(Command=="LoadBankAcc_No_FDW")
            {
        	   LoadBankAcc_No_FDW(baseResponse);
            }
		   if (Command == "loadbankdeatils") 
		    {
				LoadBankDetails(baseResponse);
			}
		   if(Command=="viewCombo")
			   {
			   LoadModule(baseResponse);
			   }
		   if(Command=="Add")
			   {
			   AddAcHead(baseResponse);
			   }
		   if(Command=="showGrid"){
			  
			   ShowGrid(baseResponse);
		   }
		   if(Command=="Modify"){
				  
			   ModifyGrid(baseResponse);
		   }
            
            
        }
    }
	
	}
function ModifyAc_Head()
{
	
	var url="../../../../../Bank_AccountHeadCode_Module.view?Command=Modify";
	var tbody=document.getElementById("grid_body");
	var count=tbody.rows.length;
	
	 for(var i=0;i<count;i++)  {  
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode"+i).value;
	var txtBankAccountNo=document.getElementById("txtBankAccountNo"+i).value;
	var txtBankId=document.getElementById("txtBankId"+i).value;
	var txtBranchId=document.getElementById("txtBranchId"+i).value;
	var txtBankAcc_type=document.getElementById("txtBankAcc_type"+i).value;
	var txtOperation_mode=document.getElementById("txtOperation_mode"+i).value;
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode"+i).value;
	var CrDrType=document.getElementById("CrDrType"+i).value;
	var cmbModule=document.getElementById("cmbModule"+i).value;
	var txtStatus=document.getElementById("txtStatus"+i).value;
	
	url+="&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&txtBankAccountNo="+txtBankAccountNo+"&txtBankId="+txtBankId+"&txtBranchId="+txtBranchId+
	"&txtBankAcc_type="+txtBankAcc_type+"&txtOperation_mode="+txtOperation_mode+"&txtAcc_HeadCode="+txtAcc_HeadCode+
	"&cmbModule="+cmbModule+"&CrDrType="+CrDrType+"&txtStatus="+txtStatus;
	
	 }
	//alert(url);
	 var req = getTransport();
     req.open("GET", url, true);
     req.onreadystatechange = function() {
     	cmn_response(req);
     }
     req.send(null); 
	}

function LoadBankAcc_No_FDW(baseResponse)
{
document.getElementById("txtBankAccountNo").length=1;
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
    var len = baseResponse.getElementsByTagName("BANK_AC_NO").length;
		for ( var k = 0; k < len; k++) {
        var BANK_AC_NO =baseResponse.getElementsByTagName("BANK_AC_NO")[k].firstChild.nodeValue;
        var se = document.getElementById("txtBankAccountNo");
		var op = document.createElement("OPTION");
		op.value = BANK_AC_NO;
		var txt = document.createTextNode(BANK_AC_NO);
		op.appendChild(txt);
		se.appendChild(op);
        }
        }else{
        alert("Fail to Load Bank Account No");
        }
}

function ModifyGrid(baseResponse)
{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success"){
		alert('Update SuccessFully !!!!!');
		AllClear();
		var tbody=document.getElementById("grid_body");
		for(var t=tbody.rows.length-1;t>=0;t--)
			{
			tbody.deleteRow(0);
			}
		document.getElementById("submit").style.display="inline";
		document.getElementById("Update").style.display="none";
		
	}
	}
function ShowGrid(baseResponse)
{
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	var tbody=document.getElementById("grid_body");
	for (var t = tbody.rows.length - 1; t >= 0; t--) {
		tbody.deleteRow(0);
	}
	if(flag=="success")
		{
		document.getElementById("submit").style.display="none";
		document.getElementById("Update").style.display="inline";
		var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
		alert("LEN==>"+len)
		if(len==0){
			alert('No Data!!!');
		}
		else{
			document.getElementById("grid").style.display="inline";
			seq=0;
			for(var i=0;i<len;i++){
				var unit_id=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
				var unit_Desc=baseResponse.getElementsByTagName("unit_name")[i].firstChild.nodeValue;
				var BANK_ID=baseResponse.getElementsByTagName("BANK_ID")[i].firstChild.nodeValue;
				var bank_desc=baseResponse.getElementsByTagName("bank_desc")[i].firstChild.nodeValue;
				var BRANCH_ID=baseResponse.getElementsByTagName("BRANCH_ID")[i].firstChild.nodeValue;
				var branch_desc=baseResponse.getElementsByTagName("branch_desc")[i].firstChild.nodeValue;
				var BANK_AC_NO=baseResponse.getElementsByTagName("BANK_AC_NO")[i].firstChild.nodeValue;
				var BANK_AC_TYPE_ID=baseResponse.getElementsByTagName("BANK_AC_TYPE_ID")[i].firstChild.nodeValue;
				var AC_OPERATIONAL_MODE_ID=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE_ID")[i].firstChild.nodeValue;
				var AC_HEAD_CODE=baseResponse.getElementsByTagName("AC_HEAD_CODE")[i].firstChild.nodeValue;
				//alert(AC_HEAD_CODE);
				var MODULE_ID=baseResponse.getElementsByTagName("MODULE_ID")[i].firstChild.nodeValue;
				var Module_Desc=baseResponse.getElementsByTagName("Module_Desc")[i].firstChild.nodeValue;
				var CR_DR_TYPE=baseResponse.getElementsByTagName("CR_DR_TYPE")[i].firstChild.nodeValue;
				var STATUS=baseResponse.getElementsByTagName("STATUS")[i].firstChild.nodeValue;
			
				
				var tbody=document.getElementById("grid_body");
				var t=0;
				
				var mycurrent_row=document.createElement("TR");
			
		    	mycurrent_row.id=seq;	
				var cell1=document.createElement("TD");
				var unit_name=document.createElement("input");
				unit_name.type="hidden";
				unit_name.id="cmbAcc_UnitCode"+seq;
				unit_name.name="cmbAcc_UnitCode"+seq;
				unit_name.value=unit_id;
				var curtext=document.createTextNode(unit_Desc);
				cell1.appendChild(curtext);
				cell1.appendChild(unit_name);
				mycurrent_row.appendChild(cell1);
				
			
				var cell2=document.createElement("TD");
				var Acc_no=document.createElement("input");
				Acc_no.type="hidden";
				Acc_no.id="txtBankAccountNo"+seq;
				Acc_no.name="txtBankAccountNo"+seq;
				Acc_no.value=BANK_AC_NO;
				var curtext=document.createTextNode(BANK_AC_NO);
				cell2.appendChild(curtext);
				cell2.appendChild(Acc_no);
				mycurrent_row.appendChild(cell2);
				
				var cell3=document.createElement("TD");
				var bank_id=document.createElement("input");
				bank_id.type="hidden";
				bank_id.id="txtBankId"+seq;
				bank_id.name="txtBankId"+seq;
				bank_id.value=BANK_ID;
				var curtext=document.createTextNode(bank_desc);
				cell3.appendChild(curtext);
				cell3.appendChild(bank_id);
				mycurrent_row.appendChild(cell3);
				
				var cell4=document.createElement("TD");
				var branch_id=document.createElement("input");
				branch_id.type="hidden";
				branch_id.id="txtBranchId"+seq;
				branch_id.name="txtBranchId"+seq;
				branch_id.value=BRANCH_ID;
				var curtext=document.createTextNode(branch_desc);
				cell4.appendChild(curtext);
				cell4.appendChild(branch_id);
				mycurrent_row.appendChild(cell4);
				
				var cell5=document.createElement("TD");
				var Acc_type=document.createElement("input");
				Acc_type.type="hidden";
				Acc_type.id="txtBankAcc_type"+seq;
				Acc_type.name="txtBankAcc_type"+seq;
				Acc_type.value=BANK_AC_TYPE_ID;
				var curtext=document.createTextNode(BANK_AC_TYPE_ID);
				cell5.appendChild(curtext);
				cell5.appendChild(Acc_type);
				mycurrent_row.appendChild(cell5);
				
				var cell6=document.createElement("TD");
				var opr_Mode=document.createElement("input");
				opr_Mode.type="hidden";
				opr_Mode.id="txtOperation_mode"+seq;
				opr_Mode.name="txtOperation_mode"+seq;
				opr_Mode.value=AC_OPERATIONAL_MODE_ID;
				var curtext=document.createTextNode(AC_OPERATIONAL_MODE_ID);
				cell6.appendChild(curtext);
				cell6.appendChild(opr_Mode);
				mycurrent_row.appendChild(cell6);
				
				
				var cell8=document.createElement("TD");
				var Acc_headCode=document.createElement("input");
				Acc_headCode.type="text";
				Acc_headCode.id="txtAcc_HeadCode"+seq;
				Acc_headCode.name="txtAcc_HeadCode"+seq;
				Acc_headCode.value=AC_HEAD_CODE;
				//var curtext=document.createTextNode(txtAcc_HeadCode);
				cell8.appendChild(Acc_headCode);
				
				mycurrent_row.appendChild(cell8);
				
				var cell9=document.createElement("TD");
				var Acc_Module=document.createElement("input");
				Acc_Module.type="hidden";
				Acc_Module.id="cmbModule"+seq;
				Acc_Module.name="cmbModule"+seq;
				Acc_Module.value=MODULE_ID;
				var curtext=document.createTextNode(Module_Desc);
				cell9.appendChild(curtext);
				cell9.appendChild(Acc_Module);
				mycurrent_row.appendChild(cell9);
				
				var cell10=document.createElement("TD");
				cell10.style.display="none";
				var cRdRType=document.createElement("input");
				cRdRType.type="hidden";
				cRdRType.id="CrDrType"+seq;
				cRdRType.name="CrDrType"+seq;
				cRdRType.value=CR_DR_TYPE;
				var curtext=document.createTextNode(CR_DR_TYPE);
				cell10.appendChild(curtext);
				cell10.appendChild(cRdRType);
				mycurrent_row.appendChild(cell10);
				
				var cell11=document.createElement("TD");
				cell11.style.display="none";
				var Status_val=document.createElement("input");
				Status_val.type="hidden";
				Status_val.id="txtStatus"+seq;
				Status_val.name="txtStatus"+seq;
				Status_val.value=STATUS;
				var curtext=document.createTextNode(STATUS);
				cell11.appendChild(curtext);
				cell11.appendChild(Status_val);
				mycurrent_row.appendChild(cell11);
				tbody.appendChild(mycurrent_row);
				seq+=1;
			}
		}
		}
}


function Cmb_BankAcc_No()
{
	
var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;  
            var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=LoadBankAcc_No_FDW&cmbAcc_UnitCode="
                    + cmbAcc_UnitCode;
           // alert(url);
            var req = getTransport();
     
            req.open("POST", url, true);
            req.onreadystatechange = function() {
                cmn_response(req);
            }
            req.send(null);        
    
}


function LoadBankAccount_No1(baseResponse)
{
document.getElementById("txtBankAccountNo").length=1;
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

    if(flag=="success")
    {
    var len = baseResponse.getElementsByTagName("BANK_AC_NO").length;
		for ( var k = 0; k < len; k++) {
        var BANK_AC_NO =baseResponse.getElementsByTagName("BANK_AC_NO")[k].firstChild.nodeValue;
        var se = document.getElementById("txtBankAccountNo");
		var op = document.createElement("OPTION");
		op.value = BANK_AC_NO;
		var txt = document.createTextNode(BANK_AC_NO);
		op.appendChild(txt);
		se.appendChild(op);
        }
        }else{
        alert("Fail to Load Bank Account No");
        }
}

function LoadBankAcc_Details()
{
	  var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	    var txtBankAccountNo = document.getElementById("txtBankAccountNo").value;
	  
	   /* if (cmbAcc_UnitCode == "select") {
	        alert("Select Accounting Unit Code in the Field");
	        document.frmBank_AccountHeadCode_Module.cmbAcc_UnitCode.focus();
	    } else if (txtBankAccountNo == "") {
	        alert("Enter Bank Account No  in the Field");
	        document.frmBank_AccountHeadCode_Module.txtBankAccountNo.focus();
	    } else {*/
	            var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=loadbankdeatils&cmbAcc_UnitCode="
	                    + cmbAcc_UnitCode + "&txtBankAccountNo=" + txtBankAccountNo;
	            var req = getTransport();
	            //alert(url);
	            req.open("GET", url, true);
	            req.onreadystatechange = function() {
	            	cmn_response(req);
	            }
	            req.send(null);        
	    //}
	} 


function LoadBankDetails(baseResponse)
{
	 
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	    if(flag=="success")
	    {               
	   //  document.getElementById("accpopup").style.display="block";
	        document.getElementById("txtBankId").value=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
	        document.getElementById("txtBankId_name").value=baseResponse.getElementsByTagName("BANK_NAME")[0].firstChild.nodeValue;
	        document.getElementById("txtBranchId").value=baseResponse.getElementsByTagName("BRANCH_ID")[0].firstChild.nodeValue;
	        document.getElementById("txtBranchId_name").value=baseResponse.getElementsByTagName("BRANCH_CITY")[0].firstChild.nodeValue;
	        document.getElementById("txtBankAcc_type").value=baseResponse.getElementsByTagName("BANK_AC_TYPE_ID")[0].firstChild.nodeValue;
	        document.getElementById("txtBankAcc_type_name").value=baseResponse.getElementsByTagName("ACCOUNT_TYPE")[0].firstChild.nodeValue;
	        document.getElementById("txtOperation_mode").value=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE_ID")[0].firstChild.nodeValue;
	        document.getElementById("txtOperation_mode_name").value=baseResponse.getElementsByTagName("AC_OPERATIONAL_MODE")[0].firstChild.nodeValue;
	        //document.getElementById("txtBankAccountNo").value=baseResponse.getElementsByTagName("BANK_ID")[0].firstChild.nodeValue;
	       
	        
	        var flag_mode=baseResponse.getElementsByTagName("flag_mode")[0].firstChild.nodeValue;
	        if(flag_mode=="failure")
	        {
	            alert("There is no Account Head found for the combination of \n Bank name and Opertional mode in Bank Account Head Master");
	            document.getElementById("txtBankId").value="";
	            document.getElementById("txtBankId_name").value="";
	            document.getElementById("txtBranchId").value="";
	            document.getElementById("txtBranchId_name").value="";
	            document.getElementById("txtBankAcc_type").value="";
	            document.getElementById("txtBankAcc_type_name").value="";
	            document.getElementById("txtOperation_mode").value="";
	            document.getElementById("txtOperation_mode_name").value="";
	            document.getElementById("txtAcc_HeadCode").value="";
	            document.getElementById("txtAcc_HeadDesc").value="";
	        }
	        else
	        {
	              document.getElementById("txtAcc_HeadCode").value=baseResponse.getElementsByTagName("AC_HEAD_CODE")[0].firstChild.nodeValue;
	              document.getElementById("txtAcc_HeadDesc").value=baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[0].firstChild.nodeValue;
	        }
	    }
	    else if(flag=="failure")
	    {
	        alert("No such Account number");
	        document.getElementById("txtBankAccountNo").value="";
	        document.getElementById("txtBankId").value="";
	        document.getElementById("txtBankId_name").value="";
	        document.getElementById("txtBranchId").value="";
	        document.getElementById("txtBranchId_name").value="";
	        document.getElementById("txtBankAcc_type").value="";
	        document.getElementById("txtBankAcc_type_name").value="";
	        document.getElementById("txtOperation_mode").value="";
	        document.getElementById("txtOperation_mode_name").value="";
	        document.getElementById("txtAcc_HeadCode").value="";
	        document.getElementById("txtAcc_HeadDesc").value="";
	    }
	   
	}


function view_combo()
{
	 var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;	
 var txtOperation_mode=document.getElementById("txtOperation_mode").value;
 var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
 var Bank_id= document.getElementById("txtBankId").value;
 var Branch_id=document.getElementById("txtBranchId").value;
 var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
 var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=ShowCombo&txtOperation_mode="+ txtOperation_mode+"&Acc_HeadCode="+txtAcc_HeadCode
 +"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtBankId="+Bank_id+"&txtBranchId="+Branch_id+"&txtBankAccountNo="+txtBankAccountNo;

 var req = getTransport();
            req.open("GET", url, true);
            req.onreadystatechange = function() {
            	cmn_response(req);
            }
            req.send(null); 
}

function LoadModule(baseResponse)
{
	
	var i=0;
	try{
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    if(flag=="success")
	    {
	    	 var op=document.getElementById("cmbModule"); 
	    	 op.length=0;
	    	 
	    	
	    	 var id=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	    	
	    	
	    	 for(i=0;i<id;i++){
	    		 var option=document.createElement("OPTION");
	    		var id_val=baseResponse.getElementsByTagName("moduleid")[i].firstChild.nodeValue;
	    		var desc=baseResponse.getElementsByTagName("moduleiddesc")[i].firstChild.nodeValue;
	    		
	    		option.value=id_val;
	    		option.text=desc;
	    		 try
	             {   
	                 op.add(option);
	             }
	             catch(errorObject)
	             {
	                 op.add(option,null);
	             } 
	    		//i++;
	    	}op.add(option);
	    	 
	    	 
	    }

	}catch (err) {
		alert("Problem in Loading Office code ");
	}
	}


function add_grid()

{
	var sl_no=1,no=2;
document.getElementById("grid").style.display="inline";
var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbAcc_UnitCode_txt=document.getElementById("cmbAcc_UnitCode").options[document.getElementById("cmbAcc_UnitCode").selectedIndex].text;
	var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
	var txtBankId=document.getElementById("txtBankId").value;
	var txtBankId_name=document.getElementById("txtBankId_name").value;
	var txtBranchId=document.getElementById("txtBranchId").value;
	var txtBranchId_name=document.getElementById("txtBranchId_name").value;
	var txtBankAcc_type=document.getElementById("txtBankAcc_type").value;
	var txtBankAcc_type_name=document.getElementById("txtBankAcc_type_name").value;
	var txtOperation_mode=document.getElementById("txtOperation_mode").value;
	var txtOperation_mode_name=document.getElementById("txtOperation_mode_name").value;
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	var txtAcc_HeadDesc=document.getElementById("txtAcc_HeadDesc").value;
	
	
	//var cmbModule=document.getElementById("cmbModule").value;
	//var cmbModule_txt=document.getElementById("cmbModule").options[document.getElementById("cmbModule").selectedIndex].text;
	
	seq=0;
	seq1=3;
	seq2=4
	var tbody=document.getElementById("grid_body");
	var t=0;
	var mycurrent_row=document.createElement("TR");
	var mycurrent_row1=document.createElement("TR");
	mycurrent_row.id=seq;
	mycurrent_row.id1=seq;	
	
	
	/*	
		var cell1=document.createElement("TD");
		var unit_name=document.createElement("input");
		unit_name.type="hidden";
		unit_name.id="cmbAcc_UnitCode"+seq;
		unit_name.name="cmbAcc_UnitCode"+seq;
		unit_name.value=cmbAcc_UnitCode;
		var curtext=document.createTextNode(cmbAcc_UnitCode_txt);
		cell1.appendChild(curtext);
		cell1.appendChild(unit_name);
		mycurrent_row.appendChild(cell1);
		
		
		var cell2=document.createElement("TD");
		var Acc_no=document.createElement("select");
		var cell2=document.createElement("TD");
		var Acc_no=document.createElement("input");
		Acc_no.type="hidden";
		Acc_no.id="txtBankAccountNo"+seq;
		Acc_no.name="txtBankAccountNo"+seq;
		Acc_no.value=txtBankAccountNo;
		var curtext=document.createTextNode(txtBankAccountNo);
		cell2.appendChild(curtext);
		cell2.appendChild(Acc_no);
		mycurrent_row.appendChild(cell2);
		
		var cell3=document.createElement("TD");
		var bank_id=document.createElement("input");
		bank_id.type="hidden";
		bank_id.id="txtBankId"+seq;
		bank_id.name="txtBankId"+seq;
		bank_id.value=txtBankId;
		var curtext=document.createTextNode(txtBankId_name);
		cell3.appendChild(curtext);
		cell3.appendChild(bank_id);
		mycurrent_row.appendChild(cell3);
		
		var cell4=document.createElement("TD");
		var branch_id=document.createElement("input");
		branch_id.type="hidden";
		branch_id.id="txtBranchId"+seq;
		branch_id.name="txtBranchId"+seq;
		branch_id.value=txtBranchId;
		var curtext=document.createTextNode(txtBranchId_name);
		cell4.appendChild(curtext);
		cell4.appendChild(branch_id);
		mycurrent_row.appendChild(cell4);
		
		var cell5=document.createElement("TD");
		var Acc_type=document.createElement("input");
		Acc_type.type="hidden";
		Acc_type.id="txtBankAcc_type"+seq;
		Acc_type.name="txtBankAcc_type"+seq;
		Acc_type.value=txtBankAcc_type;
		var curtext=document.createTextNode(txtBankAcc_type_name);
		cell5.appendChild(curtext);
		cell5.appendChild(Acc_type);
		mycurrent_row.appendChild(cell5);
		
		var cell6=document.createElement("TD");
		var opr_Mode=document.createElement("input");
		opr_Mode.type="hidden";
		opr_Mode.id="txtOperation_mode"+seq;
		opr_Mode.name="txtOperation_mode"+seq;
		opr_Mode.value=txtOperation_mode;
		var curtext=document.createTextNode(txtOperation_mode_name);
		cell6.appendChild(curtext);
		cell6.appendChild(opr_Mode);
		mycurrent_row.appendChild(cell6);
		
		
		var cell8=document.createElement("TD");
		var Acc_headCode=document.createElement("input");
		Acc_headCode.type="hidden";
		Acc_headCode.id="txtAcc_HeadCode"+seq;
		Acc_headCode.name="txtAcc_HeadCode"+seq;
		Acc_headCode.value=txtAcc_HeadCode;
		var curtext=document.createTextNode(txtAcc_HeadCode);
		cell8.appendChild(curtext);
		cell8.appendChild(Acc_headCode);
		mycurrent_row.appendChild(cell8);
		
		var cell9=document.createElement("TD");
		var Acc_Module=document.createElement("input");
		Acc_Module.type="hidden";
		Acc_Module.id="cmbModule"+seq;
		Acc_Module.name="cmbModule"+seq;
		Acc_Module.value="MF005";
		var curtext=document.createTextNode("Payment");
		cell9.appendChild(curtext);
		cell9.appendChild(Acc_Module);
		mycurrent_row.appendChild(cell9);
		
		var cell10=document.createElement("TD");
		cell10.style.display="none";
		var off_cODE=document.createElement("input");
		off_cODE.type="TEXT";
		off_cODE.id="cmbOffice_code"+sl_no;
		off_cODE.name="cmbOffice_code"+sl_no;
		off_cODE.value=cmbOffice_code;
		//var curtext=document.createTextNode("MF004");
		//cell10.appendChild(curtext);
		cell10.appendChild(off_cODE);
		mycurrent_row1.appendChild(cell10);
		
		var cell11=document.createElement("TD");
		cell11.style.display="none";
		var cRdRType=document.createElement("input");
		cRdRType.type="TEXT";
		cRdRType.id="CrDrType"+seq;
		cRdRType.name="CrDrType"+seq;
		cRdRType.value='CR';
		//var curtext=document.createTextNode("MF004");
		//cell10.appendChild(curtext);
		cell11.appendChild(cRdRType);
		mycurrent_row.appendChild(cell11);
		
	
		
		
		
		//row 2//
		
		var mycurrent_row1=document.createElement("TR");
		
		mycurrent_row1.id=sl_no;	
		var cell1=document.createElement("TD");
		var unit_name=document.createElement("input");
		unit_name.type="hidden";
		unit_name.id="cmbAcc_UnitCode"+sl_no;
		unit_name.name="cmbAcc_UnitCode"+sl_no;
		unit_name.value=cmbAcc_UnitCode;
		var curtext=document.createTextNode(cmbAcc_UnitCode_txt);
		cell1.appendChild(curtext);
		cell1.appendChild(unit_name);
		mycurrent_row1.appendChild(cell1);
		
		
		var cell2=document.createElement("TD");
		var Acc_no=document.createElement("select");
		var cell2=document.createElement("TD");
		var Acc_no=document.createElement("input");
		Acc_no.type="hidden";
		Acc_no.id="txtBankAccountNo"+sl_no;
		Acc_no.name="txtBankAccountNo"+sl_no;
		Acc_no.value=txtBankAccountNo;
		var curtext=document.createTextNode(txtBankAccountNo);
		cell2.appendChild(curtext);
		cell2.appendChild(Acc_no);
		mycurrent_row1.appendChild(cell2);
		
		var cell3=document.createElement("TD");
		var bank_id=document.createElement("input");
		bank_id.type="hidden";
		bank_id.id="txtBankId"+sl_no;
		bank_id.name="txtBankId"+sl_no;
		bank_id.value=txtBankId;
		var curtext=document.createTextNode(txtBankId_name);
		cell3.appendChild(curtext);
		cell3.appendChild(bank_id);
		mycurrent_row1.appendChild(cell3);
		
		var cell4=document.createElement("TD");
		var branch_id=document.createElement("input");
		branch_id.type="hidden";
		branch_id.id="txtBranchId"+sl_no;
		branch_id.name="txtBranchId"+sl_no;
		branch_id.value=txtBranchId;
		var curtext=document.createTextNode(txtBranchId_name);
		cell4.appendChild(curtext);
		cell4.appendChild(branch_id);
		mycurrent_row1.appendChild(cell4);
		
		var cell5=document.createElement("TD");
		var Acc_type=document.createElement("input");
		Acc_type.type="hidden";
		Acc_type.id="txtBankAcc_type"+sl_no;
		Acc_type.name="txtBankAcc_type"+sl_no;
		Acc_type.value=txtBankAcc_type;
		var curtext=document.createTextNode(txtBankAcc_type_name);
		cell5.appendChild(curtext);
		cell5.appendChild(Acc_type);
		mycurrent_row1.appendChild(cell5);
		
		var cell6=document.createElement("TD");
		var opr_Mode=document.createElement("input");
		opr_Mode.type="hidden";
		opr_Mode.id="txtOperation_mode"+sl_no;
		opr_Mode.name="txtOperation_mode"+sl_no;
		opr_Mode.value=txtOperation_mode;
		var curtext=document.createTextNode(txtOperation_mode_name);
		cell6.appendChild(curtext);
		cell6.appendChild(opr_Mode);
		mycurrent_row1.appendChild(cell6);
		
		
		var cell8=document.createElement("TD");
		var Acc_headCode=document.createElement("input");
		Acc_headCode.type="hidden";
		Acc_headCode.id="txtAcc_HeadCode"+sl_no;
		Acc_headCode.name="txtAcc_HeadCode"+sl_no;
		Acc_headCode.value=txtAcc_HeadCode;
		var curtext=document.createTextNode(txtAcc_HeadCode);
		cell8.appendChild(curtext);
		cell8.appendChild(Acc_headCode);
		mycurrent_row1.appendChild(cell8);
		
		var cell9=document.createElement("TD");
		var Acc_Module=document.createElement("input");
		Acc_Module.type="hidden";
		Acc_Module.id="cmbModule"+sl_no;
		Acc_Module.name="cmbModule"+sl_no;
		Acc_Module.value="MF004";
		var curtext=document.createTextNode("Receipt");
		cell9.appendChild(curtext);
		cell9.appendChild(Acc_Module);
		mycurrent_row1.appendChild(cell9);
		
		var cell10=document.createElement("TD");
		cell10.style.display="none";
		var off_cODE=document.createElement("input");
		off_cODE.type="TEXT";
		off_cODE.id="cmbOffice_code"+seq;
		off_cODE.name="cmbOffice_code"+seq;
		off_cODE.value=cmbOffice_code;
		//var curtext=document.createTextNode("MF004");
		//cell10.appendChild(curtext);
		cell10.appendChild(off_cODE);
		mycurrent_row.appendChild(cell10);
		
		var cell11=document.createElement("TD");
		cell11.style.display="none";
		var cRdRType=document.createElement("input");
		cRdRType.type="TEXT";
		cRdRType.id="CrDrType"+sl_no;
		cRdRType.name="CrDrType"+sl_no;
		cRdRType.value='DR';
		//var curtext=document.createTextNode("MF004");
		//cell10.appendChild(curtext);
		cell11.appendChild(cRdRType);
		mycurrent_row1.appendChild(cell11);
		
		
		
		
		tbody.appendChild(mycurrent_row);
		tbody.appendChild(mycurrent_row1);
	*/
	// row1
	var cell1=document.createElement("TD");
	var unit_name=document.createElement("input");
	unit_name.type="hidden";
	unit_name.id="cmbAcc_UnitCode"+seq;
	unit_name.name="cmbAcc_UnitCode"+seq;
	unit_name.value=cmbAcc_UnitCode;
	var curtext=document.createTextNode(cmbAcc_UnitCode_txt);
	cell1.appendChild(curtext);
	cell1.appendChild(unit_name);
	mycurrent_row.appendChild(cell1);
	
	
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("select");
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("input");
	Acc_no.type="hidden";
	Acc_no.id="txtBankAccountNo"+seq;
	Acc_no.name="txtBankAccountNo"+seq;
	Acc_no.value=txtBankAccountNo;
	var curtext=document.createTextNode(txtBankAccountNo);
	cell2.appendChild(curtext);
	cell2.appendChild(Acc_no);
	mycurrent_row.appendChild(cell2);
	
	var cell3=document.createElement("TD");
	var bank_id=document.createElement("input");
	bank_id.type="hidden";
	bank_id.id="txtBankId"+seq;
	bank_id.name="txtBankId"+seq;
	bank_id.value=txtBankId;
	var curtext=document.createTextNode(txtBankId_name);
	cell3.appendChild(curtext);
	cell3.appendChild(bank_id);
	mycurrent_row.appendChild(cell3);
	
	var cell4=document.createElement("TD");
	var branch_id=document.createElement("input");
	branch_id.type="hidden";
	branch_id.id="txtBranchId"+seq;
	branch_id.name="txtBranchId"+seq;
	branch_id.value=txtBranchId;
	var curtext=document.createTextNode(txtBranchId_name);
	cell4.appendChild(curtext);
	cell4.appendChild(branch_id);
	mycurrent_row.appendChild(cell4);
	
	var cell5=document.createElement("TD");
	var Acc_type=document.createElement("input");
	Acc_type.type="hidden";
	Acc_type.id="txtBankAcc_type"+seq;
	Acc_type.name="txtBankAcc_type"+seq;
	Acc_type.value=txtBankAcc_type;
	var curtext=document.createTextNode(txtBankAcc_type_name);
	cell5.appendChild(curtext);
	cell5.appendChild(Acc_type);
	mycurrent_row.appendChild(cell5);
	
	var cell6=document.createElement("TD");
	var opr_Mode=document.createElement("input");
	opr_Mode.type="hidden";
	opr_Mode.id="txtOperation_mode"+seq;
	opr_Mode.name="txtOperation_mode"+seq;
	opr_Mode.value=txtOperation_mode;
	var curtext=document.createTextNode(txtOperation_mode_name);
	cell6.appendChild(curtext);
	cell6.appendChild(opr_Mode);
	mycurrent_row.appendChild(cell6);
	
	
	var cell8=document.createElement("TD");
	var Acc_headCode=document.createElement("input");
	Acc_headCode.type="hidden";
	Acc_headCode.id="txtAcc_HeadCode"+seq;
	Acc_headCode.name="txtAcc_HeadCode"+seq;
	Acc_headCode.value=txtAcc_HeadCode;
	var curtext=document.createTextNode(txtAcc_HeadCode);
	cell8.appendChild(curtext);
	cell8.appendChild(Acc_headCode);
	mycurrent_row.appendChild(cell8);
	
	var cell9=document.createElement("TD");
	var Acc_Module=document.createElement("input");
	Acc_Module.type="hidden";
	Acc_Module.id="cmbModule"+seq;
	Acc_Module.name="cmbModule"+seq;
	Acc_Module.value="MF005";
	var curtext=document.createTextNode("Payment");
	cell9.appendChild(curtext);
	cell9.appendChild(Acc_Module);
	mycurrent_row.appendChild(cell9);
	
	/*var cell10=document.createElement("TD");
	cell10.style.display="none";
	var off_cODE=document.createElement("input");
	off_cODE.type="TEXT";
	off_cODE.id="cmbOffice_code"+sl_no;
	off_cODE.name="cmbOffice_code"+sl_no;
	off_cODE.value=cmbOffice_code;
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell10.appendChild(off_cODE);
	mycurrent_row1.appendChild(cell10);*/
	
	var cell11=document.createElement("TD");
	cell11.style.display="none";
	var cRdRType=document.createElement("input");
	cRdRType.type="TEXT";
	cRdRType.id="CrDrType"+seq;
	cRdRType.name="CrDrType"+seq;
	cRdRType.value='CR';
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell11.appendChild(cRdRType);
	mycurrent_row.appendChild(cell11);
	

	
	


	//row2//
	
	var mycurrent_row1=document.createElement("TR");
	
	mycurrent_row1.id=sl_no;	
	var cell1=document.createElement("TD");
	var unit_name=document.createElement("input");
	unit_name.type="hidden";
	unit_name.id="cmbAcc_UnitCode"+sl_no;
	unit_name.name="cmbAcc_UnitCode"+sl_no;
	unit_name.value=cmbAcc_UnitCode;
	var curtext=document.createTextNode(cmbAcc_UnitCode_txt);
	cell1.appendChild(curtext);
	cell1.appendChild(unit_name);
	mycurrent_row1.appendChild(cell1);
	
	
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("select");
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("input");
	Acc_no.type="hidden";
	Acc_no.id="txtBankAccountNo"+sl_no;
	Acc_no.name="txtBankAccountNo"+sl_no;
	Acc_no.value=txtBankAccountNo;
	var curtext=document.createTextNode(txtBankAccountNo);
	cell2.appendChild(curtext);
	cell2.appendChild(Acc_no);
	mycurrent_row1.appendChild(cell2);
	
	var cell3=document.createElement("TD");
	var bank_id=document.createElement("input");
	bank_id.type="hidden";
	bank_id.id="txtBankId"+sl_no;
	bank_id.name="txtBankId"+sl_no;
	bank_id.value=txtBankId;
	var curtext=document.createTextNode(txtBankId_name);
	cell3.appendChild(curtext);
	cell3.appendChild(bank_id);
	mycurrent_row1.appendChild(cell3);
	
	var cell4=document.createElement("TD");
	var branch_id=document.createElement("input");
	branch_id.type="hidden";
	branch_id.id="txtBranchId"+sl_no;
	branch_id.name="txtBranchId"+sl_no;
	branch_id.value=txtBranchId;
	var curtext=document.createTextNode(txtBranchId_name);
	cell4.appendChild(curtext);
	cell4.appendChild(branch_id);
	mycurrent_row1.appendChild(cell4);
	
	var cell5=document.createElement("TD");
	var Acc_type=document.createElement("input");
	Acc_type.type="hidden";
	Acc_type.id="txtBankAcc_type"+sl_no;
	Acc_type.name="txtBankAcc_type"+sl_no;
	Acc_type.value=txtBankAcc_type;
	var curtext=document.createTextNode(txtBankAcc_type_name);
	cell5.appendChild(curtext);
	cell5.appendChild(Acc_type);
	mycurrent_row1.appendChild(cell5);
	
	var cell6=document.createElement("TD");
	var opr_Mode=document.createElement("input");
	opr_Mode.type="hidden";
	opr_Mode.id="txtOperation_mode"+sl_no;
	opr_Mode.name="txtOperation_mode"+sl_no;
	opr_Mode.value=txtOperation_mode;
	var curtext=document.createTextNode(txtOperation_mode_name);
	cell6.appendChild(curtext);
	cell6.appendChild(opr_Mode);
	mycurrent_row1.appendChild(cell6);
	
	
	var cell8=document.createElement("TD");
	var Acc_headCode=document.createElement("input");
	Acc_headCode.type="hidden";
	Acc_headCode.id="txtAcc_HeadCode"+sl_no;
	Acc_headCode.name="txtAcc_HeadCode"+sl_no;
	Acc_headCode.value=txtAcc_HeadCode;
	var curtext=document.createTextNode(txtAcc_HeadCode);
	cell8.appendChild(curtext);
	cell8.appendChild(Acc_headCode);
	mycurrent_row1.appendChild(cell8);
	
	var cell9=document.createElement("TD");
	var Acc_Module=document.createElement("input");
	Acc_Module.type="hidden";
	Acc_Module.id="cmbModule"+sl_no;
	Acc_Module.name="cmbModule"+sl_no;
	Acc_Module.value="MF004";
	var curtext=document.createTextNode("Receipt");
	cell9.appendChild(curtext);
	cell9.appendChild(Acc_Module);
	mycurrent_row1.appendChild(cell9);
	
	/*var cell10=document.createElement("TD");
	cell10.style.display="none";
	var off_cODE=document.createElement("input");
	off_cODE.type="TEXT";
	off_cODE.id="cmbOffice_code"+sl_no;
	off_cODE.name="cmbOffice_code"+sl_no;
	off_cODE.value=cmbOffice_code;
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell10.appendChild(off_cODE);
	mycurrent_row.appendChild(cell10);*/
	
	var cell11=document.createElement("TD");
	cell11.style.display="none";
	var cRdRType=document.createElement("input");
	cRdRType.type="TEXT";
	cRdRType.id="CrDrType"+sl_no;
	cRdRType.name="CrDrType"+sl_no;
	cRdRType.value='DR';
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell11.appendChild(cRdRType);
	mycurrent_row1.appendChild(cell11);
	
	
	//row3
	
var mycurrent_row2=document.createElement("TR");
	
	mycurrent_row2.id=no;	
	var cell1=document.createElement("TD");
	var unit_name=document.createElement("input");
	unit_name.type="hidden";
	unit_name.id="cmbAcc_UnitCode"+no;
	unit_name.name="cmbAcc_UnitCode"+no;
	unit_name.value=cmbAcc_UnitCode;
	var curtext=document.createTextNode(cmbAcc_UnitCode_txt);
	cell1.appendChild(curtext);
	cell1.appendChild(unit_name);
	mycurrent_row2.appendChild(cell1);
	
	
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("select");
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("input");
	Acc_no.type="hidden";
	Acc_no.id="txtBankAccountNo"+no;
	Acc_no.name="txtBankAccountNo"+no;
	Acc_no.value=txtBankAccountNo;
	var curtext=document.createTextNode(txtBankAccountNo);
	cell2.appendChild(curtext);
	cell2.appendChild(Acc_no);
	mycurrent_row2.appendChild(cell2);
	
	var cell3=document.createElement("TD");
	var bank_id=document.createElement("input");
	bank_id.type="hidden";
	bank_id.id="txtBankId"+no;
	bank_id.name="txtBankId"+no;
	bank_id.value=txtBankId;
	var curtext=document.createTextNode(txtBankId_name);
	cell3.appendChild(curtext);
	cell3.appendChild(bank_id);
	mycurrent_row2.appendChild(cell3);
	
	var cell4=document.createElement("TD");
	var branch_id=document.createElement("input");
	branch_id.type="hidden";
	branch_id.id="txtBranchId"+no;
	branch_id.name="txtBranchId"+no;
	branch_id.value=txtBranchId;
	var curtext=document.createTextNode(txtBranchId_name);
	cell4.appendChild(curtext);
	cell4.appendChild(branch_id);
	mycurrent_row2.appendChild(cell4);
	
	var cell5=document.createElement("TD");
	var Acc_type=document.createElement("input");
	Acc_type.type="hidden";
	Acc_type.id="txtBankAcc_type"+no;
	Acc_type.name="txtBankAcc_type"+no;
	Acc_type.value=txtBankAcc_type;
	var curtext=document.createTextNode(txtBankAcc_type_name);
	cell5.appendChild(curtext);
	cell5.appendChild(Acc_type);
	mycurrent_row2.appendChild(cell5);
	
	var cell6=document.createElement("TD");
	var opr_Mode=document.createElement("input");
	opr_Mode.type="hidden";
	opr_Mode.id="txtOperation_mode"+no;
	opr_Mode.name="txtOperation_mode"+no;
	opr_Mode.value=txtOperation_mode;
	var curtext=document.createTextNode(txtOperation_mode_name);
	cell6.appendChild(curtext);
	cell6.appendChild(opr_Mode);
	mycurrent_row2.appendChild(cell6);
	
	
	var cell8=document.createElement("TD");
	var Acc_headCode=document.createElement("input");
	Acc_headCode.type="hidden";
	Acc_headCode.id="txtAcc_HeadCode"+no;
	Acc_headCode.name="txtAcc_HeadCode"+no;
	Acc_headCode.value=txtAcc_HeadCode;
	//Acc_headCode.value=820604;
	//var curtext=document.createTextNode(820604);
	var curtext=document.createTextNode(txtAcc_HeadCode);
	
	cell8.appendChild(curtext);
	cell8.appendChild(Acc_headCode);
	mycurrent_row2.appendChild(cell8);
	
	var cell9=document.createElement("TD");
	var Acc_Module=document.createElement("input");
	Acc_Module.type="hidden";
	Acc_Module.id="cmbModule"+no;
	Acc_Module.name="cmbModule"+no;
	Acc_Module.value="MF009";
	var curtext=document.createTextNode("Fund Receipt");
	cell9.appendChild(curtext);
	cell9.appendChild(Acc_Module);
	mycurrent_row2.appendChild(cell9);
	
	
	
	
/*	var cell10=document.createElement("TD");
	cell10.style.display="none";
	var off_cODE=document.createElement("input");
	off_cODE.type="TEXT";
	off_cODE.id="cmbOffice_code"+no;
	off_cODE.name="cmbOffice_code"+no;
	off_cODE.value=cmbOffice_code;
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell10.appendChild(off_cODE);
	mycurrent_row.appendChild(cell10);*/
	
	var cell11=document.createElement("TD");
	cell11.style.display="none";
	var cRdRType=document.createElement("input");
	cRdRType.type="TEXT";
	cRdRType.id="CrDrType"+no;
	cRdRType.name="CrDrType"+no;
	cRdRType.value='DR';
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell11.appendChild(cRdRType);
	mycurrent_row2.appendChild(cell11);
	
	
	//row4
	
var mycurrent_row3=document.createElement("TR");
	
	mycurrent_row3.id=seq1;	
	var cell1=document.createElement("TD");
	var unit_name=document.createElement("input");
	unit_name.type="hidden";
	unit_name.id="cmbAcc_UnitCode"+seq1;
	unit_name.name="cmbAcc_UnitCode"+seq1;
	unit_name.value=cmbAcc_UnitCode;
	var curtext=document.createTextNode(cmbAcc_UnitCode_txt);
	cell1.appendChild(curtext);
	cell1.appendChild(unit_name);
	mycurrent_row3.appendChild(cell1);
	
	
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("select");
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("input");
	Acc_no.type="hidden";
	Acc_no.id="txtBankAccountNo"+seq1;
	Acc_no.name="txtBankAccountNo"+seq1;
	Acc_no.value=txtBankAccountNo;
	var curtext=document.createTextNode(txtBankAccountNo);
	cell2.appendChild(curtext);
	cell2.appendChild(Acc_no);
	mycurrent_row3.appendChild(cell2);
	
	var cell3=document.createElement("TD");
	var bank_id=document.createElement("input");
	bank_id.type="hidden";
	bank_id.id="txtBankId"+seq1;
	bank_id.name="txtBankId"+seq1;
	bank_id.value=txtBankId;
	var curtext=document.createTextNode(txtBankId_name);
	cell3.appendChild(curtext);
	cell3.appendChild(bank_id);
	mycurrent_row3.appendChild(cell3);
	
	var cell4=document.createElement("TD");
	var branch_id=document.createElement("input");
	branch_id.type="hidden";
	branch_id.id="txtBranchId"+seq1;
	branch_id.name="txtBranchId"+seq1;
	branch_id.value=txtBranchId;
	var curtext=document.createTextNode(txtBranchId_name);
	cell4.appendChild(curtext);
	cell4.appendChild(branch_id);
	mycurrent_row3.appendChild(cell4);
	
	var cell5=document.createElement("TD");
	var Acc_type=document.createElement("input");
	Acc_type.type="hidden";
	Acc_type.id="txtBankAcc_type"+seq1;
	Acc_type.name="txtBankAcc_type"+seq1;
	Acc_type.value=txtBankAcc_type;
	var curtext=document.createTextNode(txtBankAcc_type_name);
	cell5.appendChild(curtext);
	cell5.appendChild(Acc_type);
	mycurrent_row3.appendChild(cell5);
	
	var cell6=document.createElement("TD");
	var opr_Mode=document.createElement("input");
	opr_Mode.type="hidden";
	opr_Mode.id="txtOperation_mode"+seq1;
	opr_Mode.name="txtOperation_mode"+seq1;
	opr_Mode.value=txtOperation_mode;
	var curtext=document.createTextNode(txtOperation_mode_name);
	cell6.appendChild(curtext);
	cell6.appendChild(opr_Mode);
	mycurrent_row3.appendChild(cell6);
	
	
	var cell8=document.createElement("TD");
	var Acc_headCode=document.createElement("input");
	Acc_headCode.type="hidden";
	Acc_headCode.id="txtAcc_HeadCode"+seq1;
	Acc_headCode.name="txtAcc_HeadCode"+seq1;
	Acc_headCode.value=txtAcc_HeadCode;
	//Acc_headCode.value=820604;
	//var curtext=document.createTextNode(820604);
	var curtext=document.createTextNode(txtAcc_HeadCode);
	
	cell8.appendChild(curtext);
	cell8.appendChild(Acc_headCode);
	mycurrent_row3.appendChild(cell8);
	
	var cell9=document.createElement("TD");
	var Acc_Module=document.createElement("input");
	Acc_Module.type="hidden";
	Acc_Module.id="cmbModule"+seq1;
	Acc_Module.name="cmbModule"+seq1;
	Acc_Module.value="MF010";
	var curtext=document.createTextNode("Inter Bank Transfer");
	cell9.appendChild(curtext);
	cell9.appendChild(Acc_Module);
	mycurrent_row3.appendChild(cell9);
	
	
	
	
/*	var cell10=document.createElement("TD");
	cell10.style.display="none";
	var off_cODE=document.createElement("input");
	off_cODE.type="TEXT";
	off_cODE.id="cmbOffice_code"+no;
	off_cODE.name="cmbOffice_code"+no;
	off_cODE.value=cmbOffice_code;
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell10.appendChild(off_cODE);
	mycurrent_row.appendChild(cell10);*/
	
	var cell11=document.createElement("TD");
	cell11.style.display="none";
	var cRdRType=document.createElement("input");
	cRdRType.type="TEXT";
	cRdRType.id="CrDrType"+seq1;
	cRdRType.name="CrDrType"+seq1;
	cRdRType.value='DR';
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell11.appendChild(cRdRType);
	mycurrent_row3.appendChild(cell11);
	
	//row 5
	
var mycurrent_row4=document.createElement("TR");
	
	mycurrent_row4.id=seq2;	
	var cell1=document.createElement("TD");
	var unit_name=document.createElement("input");
	unit_name.type="hidden";
	unit_name.id="cmbAcc_UnitCode"+seq2;
	unit_name.name="cmbAcc_UnitCode"+seq2;
	unit_name.value=cmbAcc_UnitCode;
	var curtext=document.createTextNode(cmbAcc_UnitCode_txt);
	cell1.appendChild(curtext);
	cell1.appendChild(unit_name);
	mycurrent_row4.appendChild(cell1);
	
	
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("select");
	var cell2=document.createElement("TD");
	var Acc_no=document.createElement("input");
	Acc_no.type="hidden";
	Acc_no.id="txtBankAccountNo"+seq2;
	Acc_no.name="txtBankAccountNo"+seq2;
	Acc_no.value=txtBankAccountNo;
	var curtext=document.createTextNode(txtBankAccountNo);
	cell2.appendChild(curtext);
	cell2.appendChild(Acc_no);
	mycurrent_row4.appendChild(cell2);
	
	var cell3=document.createElement("TD");
	var bank_id=document.createElement("input");
	bank_id.type="hidden";
	bank_id.id="txtBankId"+seq2;
	bank_id.name="txtBankId"+seq2;
	bank_id.value=txtBankId;
	var curtext=document.createTextNode(txtBankId_name);
	cell3.appendChild(curtext);
	cell3.appendChild(bank_id);
	mycurrent_row4.appendChild(cell3);
	
	var cell4=document.createElement("TD");
	var branch_id=document.createElement("input");
	branch_id.type="hidden";
	branch_id.id="txtBranchId"+seq2;
	branch_id.name="txtBranchId"+seq2;
	branch_id.value=txtBranchId;
	var curtext=document.createTextNode(txtBranchId_name);
	cell4.appendChild(curtext);
	cell4.appendChild(branch_id);
	mycurrent_row4.appendChild(cell4);
	
	var cell5=document.createElement("TD");
	var Acc_type=document.createElement("input");
	Acc_type.type="hidden";
	Acc_type.id="txtBankAcc_type"+seq2;
	Acc_type.name="txtBankAcc_type"+seq2;
	Acc_type.value=txtBankAcc_type;
	var curtext=document.createTextNode(txtBankAcc_type_name);
	cell5.appendChild(curtext);
	cell5.appendChild(Acc_type);
	mycurrent_row4.appendChild(cell5);
	
	var cell6=document.createElement("TD");
	var opr_Mode=document.createElement("input");
	opr_Mode.type="hidden";
	opr_Mode.id="txtOperation_mode"+seq2;
	opr_Mode.name="txtOperation_mode"+seq2;
	opr_Mode.value=txtOperation_mode;
	var curtext=document.createTextNode(txtOperation_mode_name);
	cell6.appendChild(curtext);
	cell6.appendChild(opr_Mode);
	mycurrent_row4.appendChild(cell6);
	
	
	var cell8=document.createElement("TD");
	var Acc_headCode=document.createElement("input");
	Acc_headCode.type="hidden";
	Acc_headCode.id="txtAcc_HeadCode"+seq2;
	Acc_headCode.name="txtAcc_HeadCode"+seq2;
	Acc_headCode.value=txtAcc_HeadCode;
	//Acc_headCode.value=820604;
	//var curtext=document.createTextNode(820604);
	var curtext=document.createTextNode(txtAcc_HeadCode);
	
	cell8.appendChild(curtext);
	cell8.appendChild(Acc_headCode);
	mycurrent_row4.appendChild(cell8);
	
	var cell9=document.createElement("TD");
	var Acc_Module=document.createElement("input");
	Acc_Module.type="hidden";
	Acc_Module.id="cmbModule"+seq2;
	Acc_Module.name="cmbModule"+seq2;
	Acc_Module.value="MF010";
	var curtext=document.createTextNode("Inter Bank Transfer");
	cell9.appendChild(curtext);
	cell9.appendChild(Acc_Module);
	mycurrent_row4.appendChild(cell9);
	
	
	
	
/*	var cell10=document.createElement("TD");
	cell10.style.display="none";
	var off_cODE=document.createElement("input");
	off_cODE.type="TEXT";
	off_cODE.id="cmbOffice_code"+no;
	off_cODE.name="cmbOffice_code"+no;
	off_cODE.value=cmbOffice_code;
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell10.appendChild(off_cODE);
	mycurrent_row.appendChild(cell10);*/
	
	
	
	
	var cell11=document.createElement("TD");
	cell11.style.display="none";
	var cRdRType=document.createElement("input");
	cRdRType.type="TEXT";
	cRdRType.id="CrDrType"+seq2;
	cRdRType.name="CrDrType"+seq2;
	cRdRType.value='CR';
	//var curtext=document.createTextNode("MF004");
	//cell10.appendChild(curtext);
	cell11.appendChild(cRdRType);
	mycurrent_row4.appendChild(cell11);
	
	tbody.appendChild(mycurrent_row);
	tbody.appendChild(mycurrent_row1);
	tbody.appendChild(mycurrent_row2);
	tbody.appendChild(mycurrent_row3);
	tbody.appendChild(mycurrent_row4);
	
}

function UpdateAc_Head_old()
{
	
	var url="../../../../../Bank_AccountHeadCode_Module.view?Command=AddOrUpdate";
	var tbody=document.getElementById("grid_body");
	var count=tbody.rows.length;
	
	 for(var i=0;i<count;i++)  {  
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode"+i).value;
	var txtBankAccountNo=document.getElementById("txtBankAccountNo"+i).value;
	var txtBankId=document.getElementById("txtBankId"+i).value;
	var txtBranchId=document.getElementById("txtBranchId"+i).value;
	var txtBankAcc_type=document.getElementById("txtBankAcc_type"+i).value;
	var txtOperation_mode=document.getElementById("txtOperation_mode"+i).value;
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode"+i).value;
	
	var cmbModule=document.getElementById("cmbModule"+i).value;
	
	url+="&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&txtBankAccountNo="+txtBankAccountNo+"&txtBankId="+txtBankId+"&txtBranchId="+txtBranchId+
	"&txtBankAcc_type="+txtBankAcc_type+"&txtOperation_mode="+txtOperation_mode+"&txtAcc_HeadCode="+txtAcc_HeadCode+
	"&cmbModule="+cmbModule;
	 }
	//alert(url);
	 var req = getTransport();
     req.open("GET", url, true);
     req.onreadystatechange = function() {
     	cmn_response(req);
     }
     req.send(null); 
     
}
function UpdateAc_Head()
{
	
	var url="../../../../../Bank_AccountHeadCode_Module.view?Command=AddOrUpdate";
	var tbody=document.getElementById("grid_body");
	var count=tbody.rows.length;
	//alert("count==>"+count)
	 for(var i=0;i<count;i++)  {  
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode"+i).value;
	//alert("cmbAcc_UnitCode==>"+cmbAcc_UnitCode+"count==>"+i)
	var txtBankAccountNo=document.getElementById("txtBankAccountNo"+i).value;
	var txtBankId=document.getElementById("txtBankId"+i).value;
	var txtBranchId=document.getElementById("txtBranchId"+i).value;
	var txtBankAcc_type=document.getElementById("txtBankAcc_type"+i).value;
	var txtOperation_mode=document.getElementById("txtOperation_mode"+i).value;
	var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode"+i).value;
	var CrDrType=document.getElementById("CrDrType"+i).value;
	var cmbModule=document.getElementById("cmbModule"+i).value;
	
	url+="&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&txtBankAccountNo="+txtBankAccountNo+"&txtBankId="+txtBankId+"&txtBranchId="+txtBranchId+
	"&txtBankAcc_type="+txtBankAcc_type+"&txtOperation_mode="+txtOperation_mode+"&txtAcc_HeadCode="+txtAcc_HeadCode+
	"&cmbModule="+cmbModule+"&CrDrType="+CrDrType;
	 }
	//alert(url);
	 var req = getTransport();
     req.open("GET", url, true);
     req.onreadystatechange = function() {
     	cmn_response(req);
     }
     req.send(null); 
     
}

function listData()
{
	 var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	 var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
	 var Bank_id= document.getElementById("txtBankId").value;
	 var Branch_id=document.getElementById("txtBranchId").value;
	 var txtBankAcc_type=document.getElementById("txtBankAcc_type").value;
	 var txtOperation_mode=document.getElementById("txtOperation_mode").value;
	 var txtAcc_HeadCode=document.getElementById("txtAcc_HeadCode").value;
	 var url = "../../../../../Bank_AccountHeadCode_Module.view?Command=showGrid&txtOperation_mode="+ txtOperation_mode+"&Acc_HeadCode="+txtAcc_HeadCode
	 +"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtBankId="+Bank_id+"&txtBranchId="+Branch_id+"&txtBankAccountNo="+txtBankAccountNo+"&txtBankAcc_type="+txtBankAcc_type;

	 var req = getTransport();
	            req.open("GET", url, true);
	            req.onreadystatechange = function() {
	            	cmn_response(req);
	            }
	            req.send(null); 	
	
	}

	
	function AddAcHead(baseResponse)
	{
		
		var i=0;
		try{
		var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
		    if(flag=="success")
		    {
		    	alert('Saved Successfully !!!!!');
		    	AllClear();
		    	var tbody=document.getElementById("grid_body");
		    	
		    	for (var t = tbody.rows.length - 1; t >= 0; t--) {
		    		tbody.deleteRow(0);
		    	}
		    	document.getElementById("grid").style.display="none";
		    	}
		    else
		    	{
		    	alert('Not Saved!!!!!');}
		    	}catch (e) {
					
				}
		
	}
	
	function AllClear()
	{
		
//		    template("clr");
		    document.getElementById("cmbAcc_UnitCode").value="";
		   
		    document.getElementById("txtBankId").value="";
		    document.getElementById("txtBankId_name").value="";
		    document.getElementById("txtBranchId").value="";
		    document.getElementById("txtBranchId_name").value="";
		    document.getElementById("txtBankAcc_type").value="";
		    document.getElementById("txtBankAcc_type_name").value="";
		    document.getElementById("txtOperation_mode").value="";
		    document.getElementById("txtOperation_mode_name").value="";
		    document.getElementById("txtAcc_HeadCode").value="";
		    document.getElementById("txtAcc_HeadDesc").value="";
		   
		  // document.getElementById("cmbModule").value="";
		    document.getElementById("txtBankAccountNo").value="";
		    document.getElementById("txtBankAccountNo").readOnly=false;
			var tbody=document.getElementById("grid_body");
	    	
	    	for (var t = tbody.rows.length - 1; t >= 0; t--) {
	    		tbody.deleteRow(0);
	    	}
		    
		/*   // document.getElementById("cmbModule").disabled=false;
		        var d=document.getElementById("cmdAdd");
		        d.style.display="block";
		        var d1=document.getElementById("cmdUpdate");
		        d1.style.display="none";
		        //var d3=document.getElementById("cmdDelete");
		        //d3.style.display="none";
*/		       
		}
	