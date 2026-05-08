//alert("A52_Register Abstract....");	//

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
	 document.frmA52_Register_Abstract.allid[0].checked=true;
	 var unit_rendered = document.getElementById("unit_rendered");
	 unit_rendered.length=0;
	 var codeHeads = "--All Units--";
    
               var opt1 = document.createElement('option');
               opt1.value = 0;
               opt1.innerHTML = codeHeads; //instead of using textnode ,we use innerhtml
               unit_rendered.appendChild(opt1);             
               return true;
}

function blockHead()
{
	if(document.frmA52_Register_Abstract.allasset[0].checked==true)
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

function loadUnits()
{
	
	var unitid=document.getElementById("cmbAcc_UnitCode").value;
	var url ="../../../../../A52Register_OBEntry?command=loadUnitRendering&unitid="+unitid ;
			
	
	var xmlrequest = AjaxFunction();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		
		manipulate1(xmlrequest);
	};
	xmlrequest.send(null);	
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

function manipulate1(xmlrequest) {

	if (xmlrequest.readyState == 4) {
	
		if (xmlrequest.status == 200) {
			

			var baseResponse1 = xmlrequest.responseXML
			.getElementsByTagName("response")[0];
			
			
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
				        alert("No Accounting Unit Rendered");
				        document.frmA52_Register_Abstract.allid[0].checked=true;
				        document.frmA52_Register_Abstract.allid[1].checked=false;
				        }
				//unitLoad(baseResponse);
			}
			
	}
}
}





function exitfun() {
	window.close();
}
