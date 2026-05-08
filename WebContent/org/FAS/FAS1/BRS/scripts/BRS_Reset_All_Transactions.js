var seq=0;
var seq1=0;
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
function LoadBRSDetails()
{
       //alert("inside LoadBRSDetails.....");
       var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
       var cmbBankAccNo = document.getElementById("cmbBankAccNo").value;
       //alert("selected account no is ::::"+cmbBankAccNo);
       if (cmbBankAccNo == "")
       {
    		alert("Select Account No to Reset");
    		document.frmBRS_Reset_all_Trn.cmbBankAccNo.focus();
       }
       var url ="../../../../../BRS_Reset_All_Transactions?command=ListAllBRS&cmbAcc_UnitCode="
		+ cmbAcc_UnitCode + "&cmbBankAccNo=" + cmbBankAccNo;
       //alert(url);

		var xmlrequest = AjaxFunction();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {
		manipulate(xmlrequest);
		};
		xmlrequest.send(null);
}


function manipulate(xmlrequest) {
	//alert("test");

	if (xmlrequest.readyState == 4) {
		if (xmlrequest.status == 200) {

			var baseResponse = xmlrequest.responseXML
					.getElementsByTagName("response")[0];

			var tagCommand = baseResponse.getElementsByTagName("command")[0];

			var command = tagCommand.firstChild.nodeValue;
			//alert("manipulate-command:--->>>"+command);

			if (command == "ListAllBRS") {
				LoadList(baseResponse);
			}
                        
		}
	}
}

function LoadList(baseResponse)
{
   // alert("coming here LoadList");
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	//var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
	if(flag=="failure"){
		alert('No Records to load !!!!!');
	}
	else{
	if (flag == "success") {
		//document.getElementById("LoadList").style.display="block";
		
		
		  var tbody = document.getElementById("grid_body");
		  var rowcount=tbody.rows.length;
		    for(var i=0;i<rowcount;i++)
		        {
		    	 var r=i.rowIndex;	   
		           tbody.deleteRow(r);
		        }	  
		    
			/*  var tbody1 = document.getElementById("grid_body_NONTWAD");
			  var rowcount1=tbody1.rows.length;
			    for(var i=0;i<rowcount1;i++)
			        {
			    	 var r1=i.rowIndex;	   
			           tbody1.deleteRow(r1);
			        }	*/
		var len = baseResponse.getElementsByTagName("accunitid").length;
		for ( var k = 0; k < len; k++) {
			var accunitid = baseResponse.getElementsByTagName("accunitid")[k].firstChild.nodeValue;
			if(accunitid == 'null' )
        	{
				accunitid="-";
        	}  
			var accountno = baseResponse.getElementsByTagName("accountno")[k].firstChild.nodeValue;
			if(accountno == 'null' )
        	{
				accountno="-";
        	}
			var startmonth = baseResponse.getElementsByTagName("startmonth")[k].firstChild.nodeValue;
			if(startmonth == 'null' )
        	{
				startmonth="-";
        	}
			var startyear = baseResponse.getElementsByTagName("startyear")[k].firstChild.nodeValue;
			if(startyear == 'null' )
        	{
				startyear="-";
        	}
			var accunitname = baseResponse.getElementsByTagName("accunitname")[k].firstChild.nodeValue;
			if(accunitname == 'null' )
        	{
				accunitname="-";
        	}
			
			var tbody = document.getElementById("grid_body");
			var table = document.getElementById("mytable");
			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = seq;
			
			/** Sl No */
        	var cell0 = document.createElement("TD");
 			var slno = document.createTextNode(seq+1);
 			cell0.appendChild(slno);
 			mycurrent_row.appendChild(cell0);
 			
			var accidname = accunitid + "--" + accunitname ;
			//alert(accidname);
 			
 			var cell2 = document.createElement("TD");
			var accidname = document.createTextNode(accidname );
			cell2.appendChild(accidname);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var accountno = document.createTextNode(accountno);
			cell3.appendChild(accountno);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var startmonth = document.createTextNode(startmonth);
			cell4.appendChild(startmonth);
			mycurrent_row.appendChild(cell4);
			
			var cell5 = document.createElement("TD");
			var startyear = document.createTextNode(startyear);
			cell5.appendChild(startyear);
			mycurrent_row.appendChild(cell5);

			tbody.appendChild(mycurrent_row);
			seq++;
		}
	}
  }	
} 
function exitfun(path) {
	window.close();
}