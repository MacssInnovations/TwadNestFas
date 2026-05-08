

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
function checkNull(){
    var cmbAcc_UnitCode=document.frmCivil_Budget_Statement_Reg.cmbAcc_UnitCode.value;
    var cmbOffice_code=document.frmCivil_Budget_Statement_Reg.cmbOffice_code.value;
       var statementGp = document.frmCivil_Budget_Statement_Reg.statementGp.value;
       var cmbStatementName=document.frmCivil_Budget_Statement_Reg.cmbStatementName.value;
       var financial_year = document.frmCivil_Budget_Statement_Reg.cmbFinancialYear.value;
       var tpe_wise = document.frmCivil_Budget_Statement_Reg.tpe_wise.value;
       if(document.frmCivil_Budget_Statement_Reg.tpe_wise[0].checked==true)
        {
           tpe_wise=document.getElementById("tpe_wise").value;
        }else{
             tpe_wise=document.frmCivil_Budget_Statement_Reg.tpe_wise[1].value;
        }
       if(cmbAcc_UnitCode==""){
           alert("Select Accounting unit Id");
           return false;
       }
       else if(cmbOffice_code==""){
           alert("Select Accounting Office Id");
           return false;
       }
      
       else if(financial_year==""){
           alert("select Finanical year");
           return false;
       }else if(cmbStatementName==""){
           alert("select Statement Name");
           return false;
       }
       else if(tpe_wise=="GRP") {
         //  alert("inside grp");
           if(statementGp==""){
               alert("Select Statement Group") ;
                  return false;
               }
          // return false;
           }
      
           
       return true;
}
function loadstatname()
{
	

	var url = "../../../../../Civil_Budget_Statement_1?command=getStatementName";
	//alert(url);
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		
		getStatementName1(xmlrequest);
	};
	xmlrequest.send(null);
}

function getStatementName1(xmlrequest) {

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "getStatementName") {
	document.frmCivil_Budget_Statement_Reg.cmbStatementName.length = 1;
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {

		var len4 = baseResponse.getElementsByTagName("STATEMENT_NO").length;
		
		for ( var i = 0; i < len4; i++) {
			var STATEMENT_NO = baseResponse
					.getElementsByTagName("STATEMENT_NO")[i].firstChild.nodeValue;
			var STATEMENT_DESC = baseResponse
					.getElementsByTagName("STATEMENT_DESC")[i].firstChild.nodeValue;

			var se = document.getElementById("cmbStatementName");
			var op = document.createElement("OPTION");
			op.value = STATEMENT_NO;
			var txt = document.createTextNode(STATEMENT_DESC);
			op.appendChild(txt);
			se.appendChild(op);
		}
	} else {
		alert("Failed to Load Statement Name");
	}
}
		}
	}
}
function chooseGroup()
{

var statement=document.getElementById("cmbStatementName").value;
var url = "../../../../../Civil_Budget_Statement_1?command=groupch&statement="+statement;


var xmlrequest = AjaxFunction();
xmlrequest.open("GET", url, true);
xmlrequest.onreadystatechange = function() {
	
	groupch_load(xmlrequest);
};
xmlrequest.send(null);
}


 

function groupch_load(xmlrequest)
{
		if (xmlrequest.readyState == 4) {
			if (xmlrequest.status == 200) {

				var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];

				var tagCommand = baseResponse.getElementsByTagName("command")[0];

				var command = tagCommand.firstChild.nodeValue;
			
				if (command == "groupch") {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	
	
    if(flag=="success"){
    
           var statementgp = document.getElementById("statementGp");
           statementgp.length=0;
            var gp_no = baseResponse.getElementsByTagName("gp_no");
            var gp_desc = baseResponse.getElementsByTagName("gp_desc");
            for(var i=0; i<gp_no.length; i++)
                {
                
                    var opt = document.createElement('option');
                    opt.value = gp_no[i].firstChild.nodeValue;
                    opt.innerHTML = gp_desc[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                    statementgp.appendChild(opt);
                }
        }
        else
        {
        alert("No Record Exist");
         var statementgp = document.forms[0].statementGp;
         statementgp.length=0;
       //  document.forms[0].advnumber.value="0";
        
        }
}
			}
		}
}