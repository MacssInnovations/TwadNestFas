//alert("AA52_Register_OB_Report....");//AA52_Register_OB_Report	//

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
function checkOffice(){
	 document.frmAA52_Register.allid[0].checked=true;
	 var unit_rendered = document.getElementById("unit_rendered");
	 unit_rendered.length=0;
	 var codeHeads = "--All Units--";
    
               var opt1 = document.createElement('option');
               opt1.value = 0;
               opt1.innerHTML = codeHeads; //instead of using textnode ,we use innerhtml
               unit_rendered.appendChild(opt1);             
               return true;
}
function loadUnits()
{
	
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
	var url ="../../../../../FAS_AA52_Report_Servlet?command=loadUnitRendering&unitid="+unitid ;
			
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("POST", url, true);
	xmlrequest.onreadystatechange = function() {
		
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
}
function blockHead()
{
	if(document.frmAA52_Register.allasset[0].checked==true)
	{
		document.getElementById("head_div1").style.display="none";
		document.getElementById("head_div2").style.display="none";
	}
	else
	{
		document.getElementById("head_div1").style.display="block";
		document.getElementById("head_div2").style.display="block";
		
	}
}
function loadUnitsDelete() {
	 var unit_rendered = document.getElementById("unit_rendered");
	 unit_rendered.length=0;
	 var codeHeads = "--All Units--";
    
               var opt1 = document.createElement('option');
               opt1.value = 0;
               opt1.innerHTML = codeHeads; //instead of using textnode ,we use innerhtml
               unit_rendered.appendChild(opt1);             
               return true;
	
}
function checkNull(){
	//alert("check null ");
	var accounting_unit_id=document.frmAA52_Register.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmAA52_Register.cmbOffice_code.value;
	var assetmajor=document.frmAA52_Register.cmbasset.value;
	var financial_year = document.frmAA52_Register.cmbFinancialYear.value; 
	
	if((accounting_unit_id=="")||(accounting_unit_id=="0")){
		alert("Select Accounting unit id");
		return false;
	}
	if((accounting_unit_office_id=="")||(accounting_unit_office_id=="0")){
		alert("Select Accounting Office id");
		return false;
	}

	if(financial_year==""){
		   alert("select Finanical year");
		   return false;
	}
	if((assetmajor==0)||(assetmajor=="0")||(assetmajor=="")){
		   alert("select Asset Major Code");
		   return false;
	}
	return true;

}
function manipulate1(xmlrequest) {

	if (xmlrequest.readyState == 4) {
	
		if (xmlrequest.status == 200) {
			

			var baseResponse1 = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			
			var tagCommand = baseResponse1.getElementsByTagName("command")[0];
		

			var command = tagCommand.firstChild.nodeValue;
			
			
			 if (command=="unitLoad")
			{
				
				 var i = 0;
					var flag = baseResponse1.getElementsByTagName("flag")[0].firstChild.nodeValue;
					var count = baseResponse1.getElementsByTagName("count")[0].firstChild.nodeValue;
				    if(flag=="success"){
				 
				    	var len4 = baseResponse1.getElementsByTagName("unit_No").length;
				    	var se = document.getElementById("unit_rendered");
				    	se.length=0;
				    	//alert("len4 "+len4);
						for (i=0 ; i < len4; i++) {
							var unit_No = baseResponse1.getElementsByTagName("unit_No")[i].firstChild.nodeValue;
							var desc = baseResponse1.getElementsByTagName("desc")[i].firstChild.nodeValue;
							var op = document.createElement("OPTION");
							op.value = unit_No;
							var txt = document.createTextNode(desc);
							op.appendChild(txt);
							se.appendChild(op);
						}
				        }
				        else
				        {	
				        alert("No Record Exist");
				        document.frmAA52_Register.allid[0].checked=true;
				        document.frmAA52_Register.allid[1].checked=false;
				        }
				//unitLoad(baseResponse);
			}
			
	}
}
}
function exitfun() {
	window.close();
}

/*
function unitLoad(baseResponse1)
{
	var flag = baseResponse1.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
    if(flag=="success"){
    	alert("unit_rendered");
    
           var unit_rendered = document.getElementById("unit_rendered");
          //  advnumber.length=0;
            var unit_No = baseResponse1.getElementsByTagName("unit_No");
            var unit_desc = baseResponse1.getElementsByTagName("desc");
            for(var i=0; i<unit_No.length; i++)
                {
                
                    var opt = document.createElement('option');
                    opt.value = unit_No[i].firstChild.nodeValue;
                    opt.innerHTML = unit_desc[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                    unit_rendered.appendChild(opt);
                }
        }
        else
        {
        alert("No Record Exist");
         var unit_rendered = document.forms[0].statementGp;
         unit_rendered.length=0;
       //  document.forms[0].advnumber.value="0";
        
        }
}
*/


    

