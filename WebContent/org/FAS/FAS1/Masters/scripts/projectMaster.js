function getTransport() {
	var req = false;
	try {
		req = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e2) {
			req = false;
		}
	}
	if (!req && typeof XMLHttpRequest != 'undefined') {
		req = new XMLHttpRequest();
	}
	return req;
}

function findHeadOffice() {
	var officeid = document.frmProjectMaster.txtOfficeId.value;
	if (officeid == 5000) {
		document.getElementById("cmbWing").style.visibility = "visible";

		url = "../../../../../ProjectMasterServlet.con?command=loadWing&Office="
				+ officeid;
		// alert(url);
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			processResponse(req);
		}
		req.send(null);
	}
}
function ClearAll() {
	document.frmProjectMaster.txtProjectId.value = "";
	document.frmProjectMaster.txtProjectName.value = "";
	document.frmProjectMaster.txtProjectCode.value = "";
	document.frmProjectMaster.txtCname.value = "";
	document.frmProjectMaster.cmdAdd.style.display = "block";
	document.frmProjectMaster.cmdUpdate.style.display = "none";
	document.frmProjectMaster.cmdDelete.style.display = "none";
}

function nullCheck() {
	var office = document.frmProjectMaster.txtOfficeId.value;
	if (office == 5000) {
		if ((document.frmProjectMaster.txtProjectName.value == "")
				|| (document.frmProjectMaster.txtProjectName.value.length <= 0)) {
			alert("Please Enter the Project Type Description");
			document.frmProjectMaster.txtProjectName.focus();
			return false;
		} else if (document.frmProjectMaster.cmbWing.value == "") {
			alert("Please Select Wing");
			document.frmProjectMaster.cmbWing.focus();
			return false;
		}
	} else {
		if ((document.frmProjectMaster.txtProjectName.value == "")
				|| (document.frmProjectMaster.txtProjectName.value.length <= 0)) {
			alert("Please Enter the Project Type Description");
			document.frmProjectMaster.txtProjectName.focus();
			return false;
		}
	}
	return true;
}
function nullCheck1() {
	if ((document.frmProjectMaster.txtProjectCode.value == "")
			|| (document.frmProjectMaster.txtProjectCode.value.length <= 0)) {
		alert("Please Enter the Project Alias Code");
		document.frmProjectMaster.txtProjectCode.focus();
		return false;
	}
	return true;
}
function nullCheck2() {
	if ((document.frmProjectMaster.txtCname.value == "")
			|| (document.frmProjectMaster.txtCname.value.length <= 0)) {
		alert("Please Enter the Project Alias Code");
		document.frmProjectMaster.txtCname.focus();
		return false;
	}
	return true;
}

function callFunction(command, param) {

	var url = "";
	var status = "";
	if (document.frmProjectMaster.txtstatus[0].checked == true)
		status = "L";
	else
		status = "C";

	if (command == 'Add') {
		//   alert(command);        
		var office = document.frmProjectMaster.txtOfficeId.value;
		var txtprojectname = document.frmProjectMaster.txtProjectName.value;
		var txtprojectcode = document.frmProjectMaster.txtProjectCode.value;
		var txtcompname = document.frmProjectMaster.txtCname.value;
		var cmbWing = document.frmProjectMaster.cmbWing.value;
		var flag = nullCheck();
		if (flag == true) {
			if (office == 5000) {
				url = "../../../../../ProjectMasterServlet.con?command=Add&Office="
						+ office
						+ "&ProjectTypeDesc="
						+ txtprojectname
						+ "&ProjectTypeCode="
						+ txtprojectcode
						+ "&ComponentName="
						+ txtcompname
						+ "&status="
						+ status
						+ "&cmbWing=" + cmbWing;
			} else {
				url = "../../../../../ProjectMasterServlet.con?command=Add&Office="
						+ office
						+ "&ProjectTypeDesc="
						+ txtprojectname
						+ "&ProjectTypeCode="
						+ txtprojectcode
						+ "&ComponentName=" + txtcompname + "&status=" + status;
			}
			//alert(url);
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				processResponse(req);
			}
			req.send(null);
		}
	} else if (command == 'Update') {
		var office = document.frmProjectMaster.txtOfficeId.value;
		var txtprojectname = document.frmProjectMaster.txtProjectName.value;
		var txtprojectcode = document.frmProjectMaster.txtProjectCode.value;
		var txtcompname = document.frmProjectMaster.txtCname.value;
		var projectid = document.frmProjectMaster.txtProjectId.value;
		var cmbWing = document.frmProjectMaster.cmbWing.value;
		var flag = nullCheck();
		if (flag == true) {
			if (office == 5000) {
				url = "../../../../../ProjectMasterServlet.con?command=Update&Office="
					+ office
					+ "&ProjectTypeDesc="
					+ txtprojectname
					+ "&ProjectId="
					+ projectid
					+ "&ProjectCode="
					+ txtprojectcode
					+ "&ComponentName="
					+ txtcompname
					+ "&status=" + status + "&cmbWing=" + cmbWing;
			}else{
			url = "../../../../../ProjectMasterServlet.con?command=Update&Office="
					+ office
					+ "&ProjectTypeDesc="
					+ txtprojectname
					+ "&ProjectId="
					+ projectid
					+ "&ProjectCode="
					+ txtprojectcode
					+ "&ComponentName="
					+ txtcompname
					+ "&status=" + status;
			}
			var req = getTransport();
			req.open("GET", url, true);
			req.onreadystatechange = function() {
				processResponse(req);
			}
			req.send(null);
		}

	}

	else if (command == 'Cancel') {
		var office = document.frmProjectMaster.txtOfficeId.value;
		var txtprojectname = document.frmProjectMaster.txtProjectName.value;
		var txtprojectcode = document.frmProjectMaster.txtProjectCode.value;
		var txtcompname = document.frmProjectMaster.txtCname.value;
		var projectid = document.frmProjectMaster.txtProjectId.value;
		var flag = nullCheck();
		url = "../../../../../ProjectMasterServlet.con?command=Cancel&Office="
				+ office + "&ProjectTypeDesc=" + txtprojectname + "&ProjectId="
				+ projectid + "&ProjectCode=" + txtprojectcode
				+ "&ComponentName=" + txtcompname + "&status=" + status;
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			processResponse(req);
		}
		req.send(null);
	} else if (command == 'List') {
		var office = document.frmProjectMaster.txtOfficeId.value;
		var txtprojectname = document.frmProjectMaster.txtProjectName.value;
		var txtprojectcode = document.frmProjectMaster.txtProjectCode.value;
		var txtcompname = document.frmProjectMaster.txtCname.value;
		url = "../../../../../ProjectMasterServlet.con?command=List&Office="
				+ office;
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			processResponse(req);
		}
		req.send(null);
	}

}

//********************************* CallServer Response Coding ***************************************//

function processResponse(req) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var Command = tagCommand.firstChild.nodeValue;

			if (Command == "Add") {
				addRow(baseResponse);
			} else if (Command == "Cancel") {
				deleteRow(baseResponse)
			}

			else if (Command == "Update") {
				updateRow(baseResponse);
			} else if (Command == "List") {
				getRow(baseResponse);
			} else if (Command == "loadWing") {
				loadWing1(baseResponse);
			} else if (Command == "loadWing1") {
				loadValuesFromTable2(baseResponse);
			}

		}
	}
}
function loadWing1(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		var len45 = baseResponse.getElementsByTagName("wing_id").length;
		if (len45 > 0) {
			for ( var i = 0; i < len45; i++) {
				var wing_id = baseResponse.getElementsByTagName("wing_id")[i].firstChild.nodeValue;
				var wing_name = baseResponse.getElementsByTagName("wing_name")[i].firstChild.nodeValue;
				var se = document.getElementById("cmbWing");
				var op = document.createElement("OPTION");
				op.value = wing_id;
				var txt = document.createTextNode(wing_name);
				op.appendChild(txt);
				se.appendChild(op);
			}
		} else {
			alert("Wing Details Does Not Exist");
		}
	} else {
		alert("Failed to Load Wing");
	}
}

function addRow(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Inserted Into Database successfully.");
		did = baseResponse.getElementsByTagName("ProjectId")[0].firstChild.nodeValue;
		// alert("Your Asset Type Code Is " + did);
		var cadid = baseResponse.getElementsByTagName("ProjectId")[0].firstChild.nodeValue;
		if ((cadid == "") || (cadid == 'null')) {
			cadid = "";
		}
		var sdesc = baseResponse.getElementsByTagName("ProjectTypeDesc")[0].firstChild.nodeValue;
		if ((sdesc == "") || (sdesc == 'null')) {
			sdesc = "";
		}
		var acode = baseResponse.getElementsByTagName("ProjectCode")[0].firstChild.nodeValue;
		if ((acode == "") || (acode == 'null')) {
			acode = "";
		}
		var cname = baseResponse.getElementsByTagName("CompName")[0].firstChild.nodeValue;
		if ((cname == "") || (cname == 'null')) {
			cname = "";
		}
		var status = baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
		if ((status == "") || (status == 'null')) {
			status = "";
		}
		// document.frmProjectMaster.txtassettypecode.value=cadid;
		// document.frmProjectMaster.txtassettypedesc.value=document.frmProjectMaster.txtassettypedesc.value;

		var tbody = document.getElementById("tab_body");
		var table = document.getElementById("Existing");
		/*
		 * var t=0; for(t=tbody.rows.length-1;t>=0;t--) { tbody.deleteRow(0); }
		 */
		var mycurrent_row = document.createElement("TR");
		mycurrent_row.id = cadid;
		var cell = document.createElement("TD");
		var anc = document.createElement("A");
		var url = "javascript:loadValuesFromTable('" + cadid + "')";
		anc.href = url;
		var txtedit = document.createTextNode("Edit");
		anc.appendChild(txtedit);
		cell.appendChild(anc);
		mycurrent_row.appendChild(cell);

		var cell2 = document.createElement("TD");
		var txtcadid = document.createTextNode(cadid);
		cell2.appendChild(txtcadid);
		mycurrent_row.appendChild(cell2);

		var cell3 = document.createElement("TD");
		var txtsdesc = document.createTextNode(sdesc);
		cell3.appendChild(txtsdesc);
		mycurrent_row.appendChild(cell3);

		var cell4 = document.createElement("TD");
		var txtscode = document.createTextNode(acode);
		cell4.appendChild(txtscode);
		mycurrent_row.appendChild(cell4);

		var cell5 = document.createElement("TD");
		var txtcname = document.createTextNode(cname);
		cell5.appendChild(txtcname);
		mycurrent_row.appendChild(cell5);

		var cell6 = document.createElement("TD");
		var txtstatus = document.createTextNode(status);
		cell6.appendChild(txtstatus);
		mycurrent_row.appendChild(cell6);

		document.frmProjectMaster.cmdAdd.style.display = "block";
		document.frmProjectMaster.cmdUpdate.style.display = "none";
		document.frmProjectMaster.cmdDelete.style.display = "none";
		document.frmProjectMaster.txtProjectId.value = "";
		document.frmProjectMaster.txtProjectName.value = "";
		document.frmProjectMaster.txtProjectCode.value = "";
		document.frmProjectMaster.txtCname.value = "";
		document.frmProjectMaster.cmbWing.value = "";
		tbody.appendChild(mycurrent_row);
		// getRow(baseResponse)
	}
}

function deleteRow(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		var ProjectId = baseResponse.getElementsByTagName("ProjectId")[0].firstChild.nodeValue;
		var tbody = document.getElementById("Existing");
		var r = document.getElementById(ProjectId);
		var ri = r.rowIndex;
		tbody.deleteRow(ri);

		document.frmProjectMaster.cmdAdd.style.display = "block";
		document.frmProjectMaster.cmdUpdate.style.display = "none";
		document.frmProjectMaster.cmdDelete.style.display = "none";
		document.frmProjectMaster.txtProjectId.value = "";
		document.frmProjectMaster.txtProjectName.value = "";
		document.frmProjectMaster.txtCname.value = "";
		document.frmProjectMaster.txtProjectCode.value = "";
		document.frmProjectMaster.cmbWing.value = "";
		alert("Selected Project Details are Canceled");
	} else {
		alert("Unable to Cancel");
	}

}

function getRow(baseResponse) {

	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success") {
		var tbody = document.getElementById("tab_body");
		var table = document.getElementById("Existing");
		var t = 0;
		for (t = tbody.rows.length - 1; t >= 0; t--) {
			tbody.deleteRow(0);
		}

		var ProjectId = baseResponse.getElementsByTagName("ProjectId");
             //   alert(":::"+ProjectId.length);
                var lengt=ProjectId.length;
		for ( var k = 0; k<lengt; k++) {
			var ProjectId = baseResponse.getElementsByTagName("ProjectId")[k].firstChild.nodeValue;
                       if ((ProjectId == "") || (ProjectId == 'null')) {
				ProjectId = "";
			}
                   	var ProjectTypeDesc = baseResponse.getElementsByTagName("ProjectTypeDesc")[k].firstChild.nodeValue;
			if ((ProjectTypeDesc == "") || (ProjectTypeDesc == 'null')) {
				ProjectTypeDesc = "";
			}
			var ProjectCode = baseResponse.getElementsByTagName("ProjectCode")[k].firstChild.nodeValue;
                      //  alert("ProjectCode:::"+ProjectCode);
			if ((ProjectCode == "") || (ProjectCode == 'null')) {
				ProjectCode = "";
			}
			var Component = baseResponse.getElementsByTagName("CompName")[k].firstChild.nodeValue;
                     //        alert("Component:::"+Component);
			
			if ((Component == "") || (Component == 'null')) {
				Component = "";
			}
			var Status = baseResponse.getElementsByTagName("status")[k].firstChild.nodeValue;
			if ((Status == "") || (Status == 'null')) {
				Status = "";
			}
                    
			var cProjectId = ProjectId;
                       
			if ((cProjectId == "") || (cProjectId == 'null')) {
				cProjectId = "";
			}
                    //     alert("cProjectId"+cProjectId);
			var cProjectTypeDesc =ProjectTypeDesc;
			if ((cProjectTypeDesc == "") || (cProjectTypeDesc == 'null')) {
				cProjectTypeDesc = "";
			}
			var cProjectCode = ProjectCode;
			if ((cProjectCode == "") || (cProjectCode == 'null')) {
				cProjectCode = "";
			}
			var cComponent = Component;
			if ((cComponent == "") || (cComponent == 'null')) {
				cComponent = "";
			}
			var cStatus = Status;
			if ((cStatus == "") || (cStatus == 'null')) {
				cStatus = "";
			}  

			var mycurrent_row = document.createElement("TR");
			mycurrent_row.id = cProjectId;
			var cell = document.createElement("TD");
			var anc = document.createElement("A");
			var url = "javascript:loadValuesFromTable('" + cProjectId + "')";
			anc.href = url;
			var txtedit = document.createTextNode("Edit");
			anc.appendChild(txtedit);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);

			var cell2 = document.createElement("TD");
			var txtassetledgercode = document.createTextNode(cProjectId);
			cell2.appendChild(txtassetledgercode);
			mycurrent_row.appendChild(cell2);

			var cell3 = document.createElement("TD");
			var txtassetledgerdesc = document.createTextNode(cProjectTypeDesc);
			cell3.appendChild(txtassetledgerdesc);
			mycurrent_row.appendChild(cell3);

			var cell4 = document.createElement("TD");
			var txtassetledgeracode = document.createTextNode(cProjectCode);
			cell4.appendChild(txtassetledgeracode);
			mycurrent_row.appendChild(cell4);

			var cell5 = document.createElement("TD");
			var txtassetledgeracomp = document.createTextNode(cComponent);
			cell5.appendChild(txtassetledgeracomp);
			mycurrent_row.appendChild(cell5);

			var cell6 = document.createElement("TD");
			var txtstatus = document.createTextNode(cStatus);
			cell6.appendChild(txtstatus);
			mycurrent_row.appendChild(cell6);

			tbody.appendChild(mycurrent_row);
			document.frmProjectMaster.cmdAdd.style.display = "block";
			document.frmProjectMaster.cmdUpdate.style.display = "none";
			document.frmProjectMaster.cmdDelete.style.display = "none";
			document.frmProjectMaster.txtProjectId.value = "";
			document.frmProjectMaster.txtProjectName.value = "";
			document.frmProjectMaster.txtProjectCode.value = "";
			document.frmProjectMaster.txtCname.value = "";
			document.frmProjectMaster.cmbWing.value = "";
		}
	} else {
		alert("Failed to Load Values");
	}
}

function updateRow(baseResponse) {
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		alert("Record Updated Successfully.");
		var items = new Array();
		items[0] = document.frmProjectMaster.txtProjectId.value;
		items[1] = document.frmProjectMaster.txtProjectName.value;
		items[2] = document.frmProjectMaster.txtProjectCode.value;
		items[3] = document.frmProjectMaster.txtCname.value;
		items[4] = document.frmProjectMaster.txtstatus.value;
	
		var r = document.getElementById(items[0]);
		var rcells = r.cells;
		rcells.item(1).firstChild.nodeValue = items[0];
		rcells.item(2).firstChild.nodeValue = items[1];
		rcells.item(3).firstChild.nodeValue = items[2];
		rcells.item(4).firstChild.nodeValue = items[3];
		rcells.item(5).firstChild.nodeValue = items[4];

		document.frmProjectMaster.cmdAdd.style.display = "block";
		document.frmProjectMaster.cmdUpdate.style.display = "none";
		document.frmProjectMaster.cmdDelete.style.display = "none";
		document.frmProjectMaster.txtProjectId.value = "";
		document.frmProjectMaster.txtProjectName.value = "";
		document.frmProjectMaster.txtProjectCode.value = "";
		document.frmProjectMaster.txtCname.value = "";
		document.frmProjectMaster.cmbWing.value = "";

	} else {
		alert("failed to update values");
	}
}
function loadValuesFromTable(rid) {
	//alert(rid);
	var office = document.frmProjectMaster.txtOfficeId.value;
	if (office == 5000) {
		url = "../../../../../ProjectMasterServlet.con?command=loadWing1&rid="
				+ rid;
		// alert(url);
		var req = getTransport();
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			processResponse(req);
		}
		req.send(null);
	} else {
		loadValuesFromTable1(rid);
	}
}

function loadValuesFromTable2(baseResponse) {

	var rid = baseResponse.getElementsByTagName("rid")[0].firstChild.nodeValue;	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if (flag == "success") {
		document.frmProjectMaster.cmbWing.value = baseResponse
				.getElementsByTagName("wing_id")[0].firstChild.nodeValue;
	} else {
		document.frmProjectMaster.cmbWing.value = "";
	}
	var r = document.getElementById(rid);
	var rcells = r.cells;
	var tbody = document.getElementById("tab_body");
	var table = document.getElementById("Existing");
	// alert(rcells.item(1).firstChild.nodeValue);
	// alert(rcells.item(2).firstChild.nodeValue);
	document.frmProjectMaster.txtProjectId.value = rcells.item(1).firstChild.nodeValue;
	document.frmProjectMaster.txtProjectName.value = rcells.item(2).firstChild.nodeValue;
	document.frmProjectMaster.txtProjectCode.value = rcells.item(3).firstChild.nodeValue;
	document.frmProjectMaster.txtCname.value = rcells.item(4).firstChild.nodeValue;
	document.frmProjectMaster.txtstatus.value = rcells.item(5).firstChild.nodeValue;
	var status = rcells.item(5).firstChild.nodeValue;
	// alert("status========"+status);
	if (status == "L")
		document.frmProjectMaster.txtstatus[0].checked = true;
	else
		document.frmProjectMaster.txtstatus[1].checked = true;
	document.frmProjectMaster.cmdAdd.style.display = "none";
	document.frmProjectMaster.cmdUpdate.style.display = "block";
	document.frmProjectMaster.cmdDelete.style.display = "block";
}
function loadValuesFromTable1(rid) {
	var r = document.getElementById(rid);
	var rcells = r.cells;
	var tbody = document.getElementById("tab_body");
	var table = document.getElementById("Existing");
	// alert(rcells.item(1).firstChild.nodeValue);
	// alert(rcells.item(2).firstChild.nodeValue);
	document.frmProjectMaster.txtProjectId.value = rcells.item(1).firstChild.nodeValue;
	document.frmProjectMaster.txtProjectName.value = rcells.item(2).firstChild.nodeValue;
	document.frmProjectMaster.txtProjectCode.value = rcells.item(3).firstChild.nodeValue;
	document.frmProjectMaster.txtCname.value = rcells.item(4).firstChild.nodeValue;
	document.frmProjectMaster.txtstatus.value = rcells.item(5).firstChild.nodeValue;
	var status = rcells.item(5).firstChild.nodeValue;
	// alert("status========"+status);
	if (status == "L")
		document.frmProjectMaster.txtstatus[0].checked = true;
	else
		document.frmProjectMaster.txtstatus[1].checked = true;
	document.frmProjectMaster.cmdAdd.style.display = "none";
	document.frmProjectMaster.cmdUpdate.style.display = "block";
	document.frmProjectMaster.cmdDelete.style.display = "block";

	// document.frmProjectMaster.cmdDelete.focus();

}

/*



 <%
 String sql2="select PROJECT_ID,PROJECT_NAME from PMS_MST_PROJECTS_VIEW where OFFICE_ID="+oid;
 PreparedStatement ps3=con.prepareStatement(sql2);
 ps3.setInt(1,oid);
 results=ps3.executeQuery();
 while(results.next())
 {
 out.println("");
 }

 %>


 */