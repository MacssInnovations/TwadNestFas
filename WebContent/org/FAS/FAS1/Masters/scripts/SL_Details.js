
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

function clrForm()
{
   
 if(window.confirm("Do you want to clear ALL fields ?"))
 {
   call_clr(document.getElementById("cmbMas_SL_type"));
 }
}

function call_clr(combo)
{
        alert(combo.id);
        var SL_type=document.getElementById(combo.id);   
        SL_type.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select--";
        option.value="";
        try
        {
        	SL_type.add(option);
        }catch(errorObject)
        {
        	SL_type.add(option,null);
        }
}


function doo(path) {
    //alert(path);	    
	
	var cmbOffice_code1 = document.getElementById("cmbOffice_code1").value;
	var cmbOffice_code2 = document.getElementById("cmbOffice_code2").value;
	var cmbMas_SL_type = document.getElementById("cmbMas_SL_type").value;
	
		var url = path + "/SL_Details?command=Add&cmbOffice_code1="
				+ cmbOffice_code1 + "&cmbOffice_code2=" + cmbOffice_code2+"&cmbMas_SL_type="+cmbMas_SL_type;

		//alert(url);
		var xmlrequest = AjaxFunction();

		xmlrequest.open("POST", url, true);

		xmlrequest.onreadystatechange = function() {
			manipulate(xmlrequest);
		};
		xmlrequest.send(null);
	
}


function manipulate(xmlrequest) {

	if (xmlrequest.readyState == 4) {
//		alert("xmlrequest.readyState======>"+xmlrequest.readyState);
		if (xmlrequest.status == 200) {
//			alert("xmlrequest.status======>"+xmlrequest.status);
			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			if (command == "Add") {
				dofunc(baseResponse);
			}
		}
	}
}


function dofunc(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;	
	//alert(flag);
	if (flag == "success") {
			alert("Datas are Moved from  Pass Sheet Transactions to BRS Transaction Successfully");

	}
	else if(flag == "Exist"){
		alert("Data Already Existed!..");
	}
	else
		{
		alert("Data Transfer Failed");
		}
}