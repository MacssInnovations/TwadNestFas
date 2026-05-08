
var seq=0;
var seq1=0;
function AjaxFunction() 
{
	var xmlrequest = false;
	try 
        {
		xmlrequest = new ActiveXObject("Msxml2.XMLHTTP");
	} 
        catch (e1) 
        {
		try 
                {
			xmlrequest = new ActiveXObject("Microsoft.XMLHTTP");
		} 
                catch (e2) 
                {
			xmlrequest = false;
		}
	}
	if (!xmlrequest && typeof XMLHttpRequest != 'undefined') 
        {
		xmlrequest = new XMLHttpRequest();
	}
	return xmlrequest;
}

function manipulate(xmlrequest) 
{
	if (xmlrequest.readyState == 4) 
        {
		if (xmlrequest.status == 200) 
                {
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;
			//alert("manipulate-command:--->>>"+command);
			if (command == "ListAll") 
                        {
				//alert("manipulate");
				LoadList(baseResponse);
			}
		}
	}
}

function ListAll(path) 
{
	        //alert(path);
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		//alert(cmbAcc_UnitCode);
                //alert(cmbOffice_code);
                //alert(txtCB_Year);
                //alert(txtCB_Month);
                //alert(cmbBankAccNo);
		if (txtCB_Year == "")
                {
			alert("Enter Cash Book Year in the Field");
			document.frmBRSListUnAck.txtCB_Year.focus();
		}
                else if (txtCB_Month == "")
                {
			alert("Enter Cash Book Month in the Field");
			document.frmBRSListUnAck.txtCB_Month.focus();
		}
                else if (cmbBankAccNo == "")
                {
			alert("Enter Bank Account No in the Field");
			document.frmBRSListUnAck.cmbBankAccNo.focus();
		} 
                else 
                {
			var url = path + "/BRS_ListUnAck?command=ListAll&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			//alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() 
                        {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
}

function ListAll_prev(path) 
{
	        //alert(path);
		var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
		var cmbOffice_code = document.getElementById("cmbOffice_code").value;
		var txtCB_Year = document.getElementById("txtCB_Year").value;
		var txtCB_Month = document.getElementById("txtCB_Month").value;
		var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
		
		if (txtCB_Year == "")
                {
			alert("Enter Cash Book Year in the Field");
			document.frmBRSListUnAck.txtCB_Year.focus();
		}
                else if (txtCB_Month == "")
                {
			alert("Enter Cash Book Month in the Field");
			document.frmBRSListUnAck.txtCB_Month.focus();
		}
                else if (cmbBankAccNo == "")
                {
			alert("Enter Bank Account No in the Field");
			document.frmBRSListUnAck.cmbBankAccNo.focus();
		} 
                else 
                {
			var url = path + "/BRS_ListUnAck?command=ListAll_prev&cmbAcc_UnitCode="
					+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
					+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
					+ "&cmbBankAccNo=" + cmbBankAccNo;

			//alert(url);
			var xmlrequest = AjaxFunction();
			xmlrequest.open("POST", url, true);
			xmlrequest.onreadystatechange = function() 
                        {
				manipulate(xmlrequest);
			};
			xmlrequest.send(null);
		}
}

function LoadList(baseResponse) 
{    
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") 
        {
		//alert(flag);
		var len = baseResponse.getElementsByTagName("RemitanceDate").length;
		document.getElementById('txtNoofRecords').value=len;
		for ( var k = 0; k < len; k++) 
                {
			var slno = baseResponse.getElementsByTagName("SerialNumber")[k].firstChild.nodeValue;
                        //alert(slno);
                        var RemitanceDate = baseResponse.getElementsByTagName("RemitanceDate")[k].firstChild.nodeValue;
			if(RemitanceDate == 'null' )
                        {
				RemitanceDate="-";
                        }  
			var WithdrawlDate = baseResponse.getElementsByTagName("WithdrawlDate")[k].firstChild.nodeValue;
			if(WithdrawlDate == 'null' )
                        {
				WithdrawlDate="-";
                        }
			var Voucher_or_ChallanNo = baseResponse.getElementsByTagName("Voucher_or_ChallanNo")[k].firstChild.nodeValue;
			if(Voucher_or_ChallanNo == 'null' )
                        {   
				Voucher_or_ChallanNo="-";
                        }
			var Cheqe_or_DDNo = baseResponse.getElementsByTagName("Cheqe_or_DDNo")[k].firstChild.nodeValue;
			if(Cheqe_or_DDNo == 'null' )
                        {
				Cheqe_or_DDNo="-";
                        }
			var CRAmount = baseResponse.getElementsByTagName("CRAmount")[k].firstChild.nodeValue;
			if(CRAmount == 'null' )
                        {
				CRAmount="-";
                        }
			var DRAmount = baseResponse.getElementsByTagName("DRAmount")[k].firstChild.nodeValue;
			if(DRAmount == 'null' )
                        {
				DRAmount="-";
                        }
			
			var tbody = document.getElementById("grid_body");
			var table = document.getElementById("mytable");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
			
			var cell8 = document.createElement("TD");
                        cell8.align="left";
			var slno = document.createTextNode(slno);
			cell8.appendChild(slno);
			mycurrent_row.appendChild(cell8);
                        
                        var cell2 = document.createElement("TD");
                        cell2.align="left";
			var RemitanceDate = document.createTextNode(RemitanceDate);
			cell2.appendChild(RemitanceDate);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
                        cell3.align="left";
			var WithdrawlDate = document.createTextNode(WithdrawlDate);
			cell3.appendChild(WithdrawlDate);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
                        cell4.align="left";
			var Voucher_or_ChallanNo = document.createTextNode(Voucher_or_ChallanNo);
			cell4.appendChild(Voucher_or_ChallanNo);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
                        cell5.align="left";
			var Cheqe_or_DDNo = document.createTextNode(Cheqe_or_DDNo);
			cell5.appendChild(Cheqe_or_DDNo);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
                        cell6.align="right";
			var CRAmount = document.createTextNode(CRAmount);
			cell6.appendChild(CRAmount);
			mycurrent_row.appendChild(cell6);

			var cell7 = document.createElement("TD");
                        cell7.align="right";
			var DRAmount = document.createTextNode(DRAmount);
			cell7.appendChild(DRAmount);
			mycurrent_row.appendChild(cell7);

			tbody.appendChild(mycurrent_row);
			seq++;
		}
		document.getElementById("crdrdiv1").style.visibility='visible';
              //  alert(baseResponse.getElementsByTagName("crAmount")[0].firstChild.nodeValue);
		document.getElementById('crAmount').value=baseResponse.getElementsByTagName("crAmount")[0].firstChild.nodeValue;
		document.getElementById('drAmount').value=baseResponse.getElementsByTagName("drAmount")[0].firstChild.nodeValue;
		
	} 
        else 
        {
		alert("Fail to Load Grid Values");
	}
}

function numbersonly1(e, t) 
{
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) 
        {
		try 
                {
			t.blur();
		} catch (e) 
                {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) 
        {
		if (unicode < 48 || unicode > 57)
			return false
	}
}

function calltwofunc()
{
	var s='<%=request.getContextPath()%>';
	
	refresh();ListAll('<%=s%>');	
}
function calltwofunc1()
{
	refresh();ListAll_prev('<%=s%>');	
}
function refresh() 
{
	//alert("REfresshhhhhhhhhhhhhhh");  
	//document.frmBRSListUnAck.cmbBankAccNo.value = "s";
	  //alert("SSS**"+document.frmBRSListUnAck.cmbBankAccNo.value);
	  var tbody = document.getElementById("grid_body");
	  var rowcount=tbody.rows.length;
	    for(var i=0;i<rowcount;i++)
	        {
	    	   var r=i.rowIndex;	   
	           tbody.deleteRow(r);
	        }
	    document.getElementById("crAmount").value="";
	    document.getElementById("drAmount").value="";
	    document.getElementById("crdrdiv1").style.visibility='hidden';
}

function exitfun() 
{
	window.close();
} 

/** Allows Number only */
function numbersonly1(e,t)
{
   var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      try{t.blur();}catch(e){}
      return true;
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false; 
    }
}
function printAll(path) {	
	var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code = document.getElementById("cmbOffice_code").value;
	var txtCB_Year = document.getElementById("txtCB_Year").value;
	var txtCB_Month = document.getElementById("txtCB_Month").value;
	var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
	if (txtCB_Year == ""){
		alert("Enter Cash Book Year in the Field");
		document.frmBRSListUnAck.txtCB_Year.focus();
	}else if (txtCB_Month == ""){
		alert("Enter Cash Book Month in the Field");
		document.frmBRSListUnAck.txtCB_Month.focus();
	}else if (cmbBankAccNo == ""){
		alert("Enter Bank Account No in the Field");
		document.frmBRSListUnAck.cmbBankAccNo.focus();
	}else{
		var url = path + "/BRS_ListUnAck?command=printAll&cmbAcc_UnitCode="
				+ cmbAcc_UnitCode + "&cmbOffice_code=" + cmbOffice_code
				+ "&txtCB_Year=" + txtCB_Year + "&txtCB_Month=" + txtCB_Month
				+ "&cmbBankAccNo=" + cmbBankAccNo;

		document.frmBRSListUnAck.method="POST";
		document.frmBRSListUnAck.action=url;
		document.frmBRSListUnAck.submit();
	}
}