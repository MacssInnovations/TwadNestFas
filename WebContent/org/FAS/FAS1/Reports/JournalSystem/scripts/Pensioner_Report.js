//alert("Report js");
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


function btncancel()
{
 self.close();
}
function checkNull()
{
	//var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	 var txtCB_Year=document.getElementById("txtCB_Year").value;
	 var txtCB_Month=document.getElementById("txtCB_Month").value;
	var listtype=document.getElementById("listtype").value;
	 var grouptype=document.getElementById("grouptype").value;
	  var penfamily=document.getElementById("penfamily").value;
	/*	if(document.getElementById("cmbAcc_UnitCode").value=="")
		{
		    alert("Select the Account Unit code");
		    return false;    
		}
		else*/ if(document.getElementById("cmbOffice_code").value=="")
			{
			    alert("Select the Office Code");
			   return false;
			}
	
	
	else if(listtype=="")
		  {
		  alert("Choose CheckList Type");
		  return false;
		  }
	 
	 
	else if(grouptype=="")
		  {
		  alert("Choose CheckList Group");
		  return false;
		  }
	
	else if(penfamily=="")
	  {
	  alert("Choose Pensioner or Family");
	  return false;
	  } 
return true;
}

function loadofficeid(path)
{
	
  var xmlrequest = getTransport();
 
	     var url =path+"/Pensioner_Report.view?command=loadofficeid";
		
		xmlrequest.open("GET",url,true);
	
		xmlrequest.onreadystatechange = function() 
		{
			 if (xmlrequest.readyState == 4) 
				{
				if (xmlrequest.status == 200) 
				{	
				
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;		

			 if (flag3 == "Success") {
				
				// var len45 = baseResponse.getElementsByTagName("ListID").length;
				 var len45 = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
				 if (len45 != 0) {
				 	for ( var i = 0; i < len45; i++) {
				 		var OFFICE_ID = baseResponse
				 				.getElementsByTagName("OFFICE_ID")[i].firstChild.nodeValue;
				 		var OFFICE_NAME = baseResponse
				 				.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
				 		var op = document.createElement("OPTION");
				 		op.value = OFFICE_ID;
				 		 var se = document.getElementById("cmbOffice_code");
				 		var txt = document.createTextNode(OFFICE_NAME);
				 		op.appendChild(txt);
				 		se.appendChild(op);
				 	}
				 	} else {
				 		alert("Does Not Office id load");
				 	}
				 }else {
				alert("Fail to Load office id");
			} 
		   }
		  }
		};
		xmlrequest.send(null);
}

function listtype(path)
{
	
  var xmlrequest = getTransport();
 
	     var url =path+"/Pensioner_Report.view?command=listtype";
		
		xmlrequest.open("GET",url,true);
	
		xmlrequest.onreadystatechange = function() 
		{
			 if (xmlrequest.readyState == 4) 
				{
				if (xmlrequest.status == 200) 
				{	
				
			var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
			
			var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;		

			 if (flag3 == "success") {
				
				// var len45 = baseResponse.getElementsByTagName("ListID").length;
				 var len45 = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
				 if (len45 != 0) {
				 	for ( var i = 0; i < len45; i++) {
				 		var ListID = baseResponse
				 				.getElementsByTagName("ListID")[i].firstChild.nodeValue;
				 		var ListDesc = baseResponse
				 				.getElementsByTagName("ListDesc")[i].firstChild.nodeValue;
				 		var op = document.createElement("OPTION");
				 		op.value = ListID;
				 		 var se = document.getElementById("listtype");
				 		var txt = document.createTextNode(ListDesc);
				 		op.appendChild(txt);
				 		se.appendChild(op);
				 	}
				 	} else {
				 		alert("Budget Desc Does Not Exist");
				 	}
				 }else {
				alert("Fail to Load Desc");
			} 
		   }
		  }
		};
		xmlrequest.send(null);
}

function listgroup(path)
{
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	
     var xmlrequest = getTransport();
	 var url =path+"/Pensioner_Report.view?command=listgroup&cmbOffice_code="+cmbOffice_code;
	 
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() 
		{
	  if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{	
		
	var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
	
	var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;		

	if (flag3 == "Success1") {
		var len4 = baseResponse.getElementsByTagName("ListID").length;
		var se = document.getElementById("grouptype");
		if (len4 != 0) {
			
			for ( var i = 0; i < len4; i++) {
				var ListID1 = baseResponse.getElementsByTagName("ListID")[i].firstChild.nodeValue;
				var ListDesc1 = baseResponse.getElementsByTagName("ListDesc")[i].firstChild.nodeValue;
				var op = document.createElement("OPTION");
				op.value = ListID1;
				var txt = document.createTextNode(ListDesc1);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Budget Desc Does Not Exist");
		}
	}else {
		alert("Fail to Load Desc");
	} 
   }
  }
};
xmlrequest.send(null);
}
/*function manipulate1(xmlrequest) {
alert("frist");
	if (xmlrequest.readyState == 4) {
		alert("sec");
		if (xmlrequest.status == 200) {
			
			alert("333333");
			var baseResponse1 = xmlrequest.responseXML.getElementsByTagName("response")[0];

			var tagCommand = baseResponse1.getElementsByTagName("command")[0].firstChild.nodeValue;
			alert("5555555  tagCommand  "+tagCommand);
			var command = tagCommand;
			
		   alert(command);
			 if (command=="listtype")
			{
				 var se = document.getElementById("listtype");
					se.length=0;
				 var flag3 = baseResponse.getElementsByTagName("flag3")[0].firstChild.nodeValue;
				 if (flag3 == "success") {
				 alert(flag3);
				// var len45 = baseResponse.getElementsByTagName("ListID").length;
				 var len45 = baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
				 if (len45 != 0) {
				 	for ( var i = 0; i < len45; i++) {
				 		var ListID = baseResponse
				 				.getElementsByTagName("ListID")[i].firstChild.nodeValue;
				 		var ListDesc = baseResponse
				 				.getElementsByTagName("ListDesc")[i].firstChild.nodeValue;
				 		var op = document.createElement("OPTION");
				 		op.value = ListID;
				 		var txt = document.createTextNode(ListDesc);
				 		op.appendChild(txt);
				 		se.appendChild(op);
				 	}
				 	} else {
				 		alert("Budget Desc Does Not Exist");
				 	}
				 } else {
				 	alert("Fail to Load Desc");
				 }
			}else{
				alert("no command    not notttt ");
			}
			
	}
}
}



if(xmlrequest.readyState == 4) 
{
if (xmlrequest.status == 200) 
{
alert(xmlrequest.responseText);

var baseResponse = xmlrequest.responseXML.getElementsByTagName("response")[0];
alert(baseResponse);

}
}

*/

/*function createComboBox(name, cmbId )
{	
	var option = document.createElement("option");
	option.value = "" ;
	option.text = " -- Select -- " +  name;

	try
	{
		document.getElementById( cmbId ).innerHTML = "";
	}
	catch(e)
	{
		document.getElementById( cmbId ).innerText = "";
	}

	try
	{
		document.getElementById( cmbId ).add(option);
	}
	catch(errorObject)
	{
		document.getElementById( cmbId ).add(option,null);
	}
}
*/
/*function loadHead()
{
   var xmlrequest = getTransport();
   
   var url="../../../../../PensionJournal?command=loadHead";
   xmlrequest.open("POST", url, true);
		xmlrequest.onreadystatechange = function() 
		{
	  if (xmlrequest.readyState == 4) 
		{
		if (xmlrequest.status == 200) 
		{
		var baseResponse=xmlrequest.responseXML.getElementsByTagName("response")[0];
		
		var flag1=baseResponse.getElementsByTagName("flag1")[0].firstChild.nodeValue;
		
		
		if(flag1=="Success")
		{
		
		}
		}
	}
};
xmlrequest.send(null);
}
*/





