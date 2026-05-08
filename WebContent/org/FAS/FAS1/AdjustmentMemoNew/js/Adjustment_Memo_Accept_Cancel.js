var com_id;
var seq=0;

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

function sixdigit()
{
	    if( document.getElementById("txtAcc_HeadCode").value!=0)
	    {
	        if((document.getElementById("txtAcc_HeadCode").value).length<6)
	        {
		        alert("Account Head Code Shouldn't be less than 6 digit number");
		        document.getElementById("txtAcc_HeadCode").focus();
		        return false;
	        }
	    }
}


function filter_real(evt,item,n,pre)
{
        var charCode = (evt.which) ? evt.which : event.keyCode;
// allow "." for one time 
        if(charCode==46){
                        //	alert("Position of . "+item.value.indexOf("."));
                        if(item.value.indexOf(".")<0)	return (item.value.length>0)?true:false;
                        else return false;
        }
        if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //			alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //	alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}


function check_leng(remarks)
{	 
	    if((remarks.length)>=190)
	    {
	    	alert("Please Enter Paticulars below 200 characters");
	    }	 
}

function clearall()
{
     document.getElementById("txtAcc_HeadCode").disabled=false;
     document.Adjustment_Memo_Form1.rad_sub_CR_DR[1].checked=true;
     document.getElementById("cmbSL_type").value=""; 
     document.getElementById("txtEmpID_trs").value="";
     document.getElementById("txtOfficeID_trs").value="";
     document.getElementById("txtsub_Amount").value="";
     document.getElementById("txtParticular").value="";              
     var cmbSL_Code1=document.getElementById("cmbSL_Code"); 
     clear_Combo(cmbSL_Code1);   
	 document.Adjustment_Memo_Form1.cmdadd.style.display='block';
	 document.Adjustment_Memo_Form1.cmdupdate.style.display='none';
	 document.Adjustment_Memo_Form1.cmddelete.disabled=true;
}

function clrForm()
{
	    if(window.confirm("Do you want to clear ALL fields ?"))
		{
		   		call_clr();
		}
}

function delete_GRID()
{
        if(confirm("Do you want to delete ?"))
        {
        var tbody=document.getElementById("mytable");
        var r=document.getElementById(com_id);
        var ri=r.rowIndex;
        tbody.deleteRow(ri);
        clearall();
        }
}

function checkNull()
{
	//alert("enter");
        var tbody=document.getElementById("grid_body");
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
        if(document.getElementById("txtDate").value=="")
        {
            alert("Enter the Date");           
            return false;    
        }          
        if(document.getElementById("cmbAdviceNO").selectedIndex==0)
        {
            alert("Enter the Advice No in General");          
            return false;    
        }
        if(tbody.rows.length==0)
        {
            alert("Enter the Details Part");         
            return false; 
        }
      
        else
        {
        	var credit_amount=0;
        	var debit_amount=0;
        	var tot_amount=0;
        	 rows=tbody.getElementsByTagName("TR");       
        	// alert(rows);
             for(i=0;i<rows.length;i++)
             {
            	 //alert(rows.length);
                         var cells=rows[i].cells;      
                         tot_amount=tot_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                         if(cells.item(2).lastChild.nodeValue=='CR')
                         {
                        	  credit_amount=credit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                         }
                         else
                         {
                        	 debit_amount=debit_amount+parseFloat(cells.item(5).lastChild.nodeValue);
                         }
             }
            
             
        }
               
} 


function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
	    if(blr_flag==1)                 // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
	    {
	            call_clr();
	            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	            cmbOffice_code=document.getElementById("cmbOffice_code").value;
	            var TB_date=fromcal_dateCtrl.value;            
	            if(fromcal_dateCtrl.value.length!=0)
	            {
		                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
		                 var req=getTransport();
		                 req.open("GET",url,true); 
		                 req.onreadystatechange=function()
		                 {
		                   check_TB(req,fromcal_dateCtrl);
		                 }   
		                 req.send(null);
	            }
	    }
}

function call_date(dateCtrl)                        // TB_checking 
{
	    call_clr();
	    if(checkdt(dateCtrl))
	    {       
		         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
		         cmbOffice_code=document.getElementById("cmbOffice_code").value;
		         var TB_date=dateCtrl.value;
		       
		         if(dateCtrl.value.length!=0)
		         {
			             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
			             var req=getTransport();
			             req.open("GET",url,true); 
			             req.onreadystatechange=function()
			             {
			               check_TB(req,dateCtrl);
			             }   
			             req.send(null);
		         }
	        
	    }
	    else
	    {
		         var cmbSL_Code=document.getElementById("txtReceipt_No");   
		         cmbSL_Code.innerHTML="";
		         var option=document.createElement("OPTION");
		         option.text="-- Select Receipt Number --";
		         option.value="";
		         try
		         {
		                 cmbSL_Code.add(option);
		         }catch(errorObject)
		         {
		        	 	 cmbSL_Code.add(option,null);
		         }
	    }
}



function check_TB(req,dateCtrl)
{
	    if(req.readyState==4)
	    {
		        if(req.status==200)
		        {  
			             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
			             var tagcommand=baseResponse.getElementsByTagName("command")[0];
			             var Command=tagcommand.firstChild.nodeValue;
			             var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
			            
			             if(flag=="success")
			             {
			            	 	//doFunction('load_Receipt_No','null');                 //return true;
			            	 loadMemoNO1();
			             }
			             else if(flag=="failure")
			             {
			                    dateCtrl.value="";
			                    alert("Trial Balance Closed");//return false;//
			                    dateCtrl.focus();
			                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
			                    cmbSL_Code.innerHTML="";
			                    var option=document.createElement("OPTION");
			                    option.text="-- Select Receipt Number --";
			                    option.value="";
			                    try
			                    {
			                        cmbSL_Code.add(option);
			                    }catch(errorObject)
			                    {
			                        cmbSL_Code.add(option,null);
			                    }
			               }
			             
		        }
	    }
}

function loadMemoNO1() {
//	alert("enter into loadmemo1");
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtDate=document.getElementById("txtDate").value;

	var url = "../../../../../Adjustment_Memo_Accept_Cancel?command=loadmomono&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtDate="+txtDate;
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
		}

	xmlrequest.send(null);

}

function AjaxFunction() {
	
	//alert("enter into ajax function");
	
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
function manipulate1(xmlrequest) {
//alert("enter manipulate");
	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
		//alert("command"+command);

			if (command == "memodetails") {
				getValues(baseResponse);
			}
			else if(command=="loadmomono")
			{
				loadMomoNo(baseResponse);
			}
}
}
}
//function loadMomoNo(baseResponse)
//{
//	//alert("after servlet");
//var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
////alert("after servlet"+flag);
//	if (flag == "success") {
//	    var len =baseResponse.getElementsByTagName("memono").length;
//	 if(len>0)
//	 {
//		var se = document.getElementById("cmbAdviceNO");
//		se.length=1;
//	    for ( var i = 0; i < len; i++) {
//			var com1id = baseResponse.getElementsByTagName("memono")[i].firstChild.nodeValue;
//			var slno = baseResponse.getElementsByTagName("slno")[i].firstChild.nodeValue;
//			var op = document.createElement("OPTION");
//			op.value = com1id+"-"+slno;
//			var txt = document.createTextNode(com1id+"-"+slno);
//			op.appendChild(txt);
//			se.appendChild(op);
//		}
//	}
//	 else
//	 {
//		 alert("NO Data For MemoNumber");
//	 }
//	}
//	else if(flag=="Nodata")
//	{
//		alert("NO Data For MemoNumber");
//		var se = document.getElementById("cmbAdviceNO");
//		se.length=1;
//	}
//	else {
//		alert("Fail to Load");
//	}
//	
//	
//}



function loadMomoNo(baseResponse)
{
	//alert("after servlet");
//var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

if(baseResponse.getElementsByTagName("flag")[0] == undefined){
	var flag = ";"
}else{
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
}
//alert("after servlet"+flag);
	if (flag == "success") {
	    var len =baseResponse.getElementsByTagName("memono").length;
	   // var Memo_No=baseResponse.getElementsByTagName("memono");
	    var com1id=new Array();
	    var slno=new Array();
	    
	 if(len>0)
	 {
		var se = document.getElementById("cmbAdviceNO");
		se.length=1;
	    for ( var i = 0; i < len; i++) {
			 com1id[i] = baseResponse.getElementsByTagName("memono")[i].firstChild.nodeValue;
			 slno[i] = baseResponse.getElementsByTagName("slno")[i].firstChild.nodeValue;

		}
	    
	    for(var k=0;k<len;k++)
        {   
              var option=document.createElement("OPTION");
              option.text=com1id[k]+"-"+slno[k];
              option.value=com1id[k]+"-"+slno[k];
            //  alert(option.value);
             // alert("Select"+se);
               try
              {
            	   se.add(option);
              }
              catch(errorObject)
              {
            	  se.add(option,null);
              }
        }
	    
	    
	}
	 else
	 {
		 alert("NO Data For MemoNumber");
	 }
	}
	else if(flag=="Nodata")
	{
		alert("NO Data For MemoNumber");
		var se = document.getElementById("cmbAdviceNO");
		se.length=1;
	}
	else {
		alert("Fail to Load");
	}
	
	
}


function getValues(baseResponse)
{
	var authName="",authaddress="";
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	var items=new Array();
	if (flag == "success") {



		if(baseResponse.getElementsByTagName("authorityname")[0].firstChild==null)
		{
			authName="";
		}
		else
		{
			authName=baseResponse.getElementsByTagName("authorityname")[0].firstChild.nodeValue;
		}
		document.getElementById("txtAuthority").value=authName;
		
		if(baseResponse.getElementsByTagName("authorityaddress")[0].firstChild==null)
		{
			authaddress="";
		}
		else
		{
			authaddress=baseResponse.getElementsByTagName("authorityaddress")[0].firstChild.nodeValue;
		}
		document.getElementById("txtAuthorityaddress").value=authaddress;
		
		document.getElementById("txtLetterNO").value=baseResponse.getElementsByTagName("lNo")[0].firstChild.nodeValue
		document.getElementById("txtLetterDate").value=baseResponse.getElementsByTagName("ldate")[0].firstChild.nodeValue
		
		
		if(baseResponse.getElementsByTagName("remarks")[0].firstChild == null ){
			document.getElementById("txtRemarks1").value = "";
		}else{
			document.getElementById("txtRemarks1").value=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue
		}
		
		document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("AMOUNT")[0].firstChild.nodeValue
		
		items[0]=baseResponse.getElementsByTagName("accCode")[0].firstChild.nodeValue;
		items[1]=baseResponse.getElementsByTagName("crdr")[0].firstChild.nodeValue;
		items[2]=baseResponse.getElementsByTagName("type_code")[0].firstChild.nodeValue;
		items[3]=baseResponse.getElementsByTagName("type_desc")[0].firstChild.nodeValue;
		items[4]=baseResponse.getElementsByTagName("code")[0].firstChild.nodeValue;
		items[5]=baseResponse.getElementsByTagName("code_desc")[0].firstChild.nodeValue;
		items[6]=baseResponse.getElementsByTagName("AMOUNT")[0].firstChild.nodeValue;
		items[7]=baseResponse.getElementsByTagName("headdesc")[0].firstChild.nodeValue;
		
		if(baseResponse.getElementsByTagName("remarks")[0].firstChild == null){
			items[8]= "";
		} else{
			items[8]=baseResponse.getElementsByTagName("remarks")[0].firstChild.nodeValue;
		}
		
		
		 tbody=document.getElementById("grid_body");
		
	        var t=0;
	        for(t=tbody.rows.length-1;t>=0;t--)
	        {
	                tbody.deleteRow(0);
	        }
		 
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        
        var i=0;
        var cell2;
        
       
            cell2=document.createElement("TD");
           
                  var H_code=document.createElement("input");
                  H_code.type="hidden";
                  H_code.name="H_code";
                  H_code.value=items[0];
                  cell2.appendChild(H_code);
                  var currentText=document.createTextNode(items[0]+"-"+items[7]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
             cell2=document.createElement("TD"); 
                  var CR_DR_type=document.createElement("input");
                  CR_DR_type.type="hidden";
                  CR_DR_type.name="CR_DR_type";
                  CR_DR_type.value=items[1];
                  cell2.appendChild(CR_DR_type);
                   var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
             
             cell2=document.createElement("TD");
                  var SL_type=document.createElement("input");
                  SL_type.type="hidden";
                  SL_type.name="SL_type";
                  SL_type.value=items[2];
                  cell2.appendChild(SL_type);
                   var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
            
             cell2=document.createElement("TD");
                  var SL_code=document.createElement("input");
                  SL_code.type="hidden";
                  SL_code.name="SL_code";
                  SL_code.value=items[4];
                  cell2.appendChild(SL_code);
                   var currentText=document.createTextNode(items[5]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                    
             cell2=document.createElement("TD"); 
                  var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[6];
                  cell2.appendChild(sl_amt);
                  var currentText=document.createTextNode(items[6]);
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
                mycurrent_row.appendChild(cell2);

        tbody.appendChild(mycurrent_row);
         
		
		
	}
	 else if(flag=="nodata")
	{
		alert ("Record Does Not Exists");
	}
	else
	{
		alert("Fail To Load");
	}
	
	
}
function loadMemoDetails() {
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var txtDate=document.getElementById("txtDate").value;
	var cmbAdviceNO=document.getElementById("cmbAdviceNO").value;
var splvno=cmbAdviceNO.split("-");
	var url = "../../../../../Adjustment_Memo_Accept_Cancel?command=details&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtDate="+txtDate+"&cmbAdviceNO="+splvno[0]+"&slno="+splvno[1];
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		manipulate1(xmlrequest);
		}

	xmlrequest.send(null);

}





function call_clr()
{
        //document.getElementById("txtVoucher_No").value="";  
        var cmbAdviceNO=document.getElementById("cmbAdviceNO");   
        cmbAdviceNO.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select No--";
        option.value="";
        try
        {
        	cmbAdviceNO.add(option);
        }catch(errorObject)
        {
        	cmbAdviceNO.add(option,null);
        }
        
       // document.getElementById("cmbSubSystemType").value="";  
//        document.getElementById("com_value").value="";  
//        document.getElementById("amt").value="";  
//        document.getElementById("txtReferNO_edit").value="";
//        document.getElementById("txtReferDate_edit").value="";
//        document.getElementById("txtRemak_edit").value="";
//        
}