

function call_mainJSP_script(dateField,blur_flag)
{}

/* *****************************************************************************************
 * 			CREATE REQUEST OBJECT
 */
//////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////





/* *****************************************************************************************
 * 			CALCULATE BOOK VALUE
 */
////////////////////////////////////////////////////////////////////////////////////////

function calcBookValue(tab)
{
	var prefix="txt"+tab;
	document.getElementById(prefix+'BookValue').value = 	parseInt(document.getElementById(prefix+'CivilCost').value) + 
															parseInt(document.getElementById(prefix+'ElectricalCost').value) + 
															parseInt(document.getElementById(prefix+'ExternalServCost').value) + 
															parseInt(document.getElementById(prefix+'AdditionCost').value);
}
////////////////////////////////////////////////////////////////////////////////////////






/* *****************************************************************************************
 * 			OFFICE_TYPE SELECTED
 */
////////////////////////////////////////////////////////////////////////////////////////

function fncOfficeType(offTyp)
{
	document.getElementById("hidOfficeType").value = offTyp;
	if(offTyp=="T")
	{
		document.getElementById("cmbOffice").style.display = "inline";
		document.getElementById("txtOffice").style.display = "none";
	}
	else
	{
		document.getElementById("cmbOffice").style.display = "none";
		document.getElementById("txtOffice").style.display = "inline";
	}
}
////////////////////////////////////////////////////////////////////////////////////////






/* *****************************************************************************************
 * 			SELECT A/C HEAD - OPEN JSP
 */
////////////////////////////////////////////////////////////////////////////////////////

	var winAccHeadCode;
	
	function AccHeadpopup()
	{
	    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
	    {
	       winAccHeadCode.resizeTo(500,500);
	       winAccHeadCode.moveTo(250,250); 
	       winAccHeadCode.focus();
	    }
	    else
	    {
	        winAccHeadCode=null
	    }
	        
	    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
	    winAccHeadCode.moveTo(250,250);  
	    winAccHeadCode.focus();
	    
	}
	
	var AC_HEAD = "txtAcHeadCode";
	
	function doParentAccHead(code)
	{
	   document.getElementById(AC_HEAD).value=code;  // NOTE: AC_HEAD ==> txtAcHeadCode (or) txtFloorAcHeadCode === Building (or) Floor - respectively
	   return true;
	}
////////////////////////////////////////////////////////////////////////////////////////
	
	

	
	

/* *****************************************************************************************
 * 			DOMAIN CHECK - 6 digits only
 */
////////////////////////////////////////////////////////////////////////////////////////

	function sixdigit(txtBox)
	{
		var txt = txtBox.value;
		if(txt.length>6)
		{
			alert("Please enter a Valid A/c Head");
			txtBox.value="";
			txtBox.focus();
		}
	}
////////////////////////////////////////////////////////////////////////////////////////


	
	
	
/* *****************************************************************************************
 * 			CHECK LENGTH of Remarks
 */
////////////////////////////////////////////////////////////////////////////////////////

	function check_leng(txt)
	{
		var len = txt.length;
		return (len<200 ? true:false);
	}

////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
	
	/* *****************************************************************************************
	 * 			CHECK FOR NULL
	 */
	////////////////////////////////////////////////////////////////////////////////////////

		function nullcheck(ind){  
			var index = document.getElementById(ind).value;
			if((index == null)||(index == '')||(index == ' '))
			{
				document.getElementById(ind).focus();
				return false;
			}
			else
			{
				return true;
			}
		}
	////////////////////////////////////////////////////////////////////////////////////////


		



	/* *****************************************************************************************
	 * 			NULL CHECK TEMPORARY FLOORS
	 */
	////////////////////////////////////////////////////////////////////////////////////////

	function nullCheckTempFloor()
	{
		var flag = (nullcheck('txtFloorNo')) && (nullcheck('txtFloorConsYear')) && (nullcheck('txtFloorHeight')) && (nullcheck('txtPlinthArea')) && (nullcheck('txtFloorCivilCost')) && (nullcheck('txtFloorElectricalCost')) && (nullcheck('txtFloorExternalServCost')) && (nullcheck('txtFloorAdditionCost')) && (nullcheck('txtFloorBookValue')) && (nullcheck('txtBPNo')) && (nullcheck('txtFloorAcHeadCode'));
		if(flag)
		{
			var flgRmrk = (document.frmBuildingDetails.txtFloorRemarks.value == "")?confirm("Do you want to save Floor without remarks?"):true;
		}
		flagFloor = flag && flgRmrk;
		
		return(flagFloor);
	}
	////////////////////////////////////////////////////////////////////////////////////////






	/* *****************************************************************************************
	 * 			ADD FLOOR - (CHECK FOR NULL TEMPORARY FLOOR & ADD)
	 */
	////////////////////////////////////////////////////////////////////////////////////////

		function addFloor()
		{
			var flagFloor = nullCheckTempFloor();
			if(flagFloor)
			{
				addTempRow('funEditFloor','txtFloorNo','tblListFloor/txtFloorNo/txtFloorConsYear/txtFloorHeight/txtPlinthArea/txtFloorCivilCost/txtFloorElectricalCost/txtFloorExternalServCost/txtFloorAdditionCost/txtFloorBookValue/txtBPNo/txtFloorAcHeadCode/txtFloorRemarks'); 
				hideRemarks('tblListFloor','txtFloorRemarks'); 
				clearFloor();
			}
			else
			{
				alert("Please fill all the fields");
			}
		}
	////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		
		
	/* *****************************************************************************************
	 * 			UPDATE FLOOR - (CHECK FOR NULL TEMPORARY FLOOR & UPDATE)
	 */
	////////////////////////////////////////////////////////////////////////////////////////

		function updateFloor()
		{
			var flagFloor = nullCheckTempFloor();
			if(flagFloor)
			{
				updateTempRow(SELECTED_FLOOR,'tblListFloor/txtFloorNo/txtFloorConsYear/txtFloorHeight/txtPlinthArea/txtFloorCivilCost/txtFloorElectricalCost/txtFloorExternalServCost/txtFloorAdditionCost/txtFloorBookValue/txtBPNo/txtFloorAcHeadCode/txtFloorRemarks');  
				hideRemarks('tblListFloor','txtFloorRemarks'); 
			}
			else
			{
				alert("Please fill all the fields");
			}
		}
	////////////////////////////////////////////////////////////////////////////////////////
		
			
			
			

		
	/* *****************************************************************************************
	 * 			NULL CHECK TEMPORARY OFFICE
	 */
	////////////////////////////////////////////////////////////////////////////////////////

		function nullcheckTempOffice()
		{
			var flag = (nullcheck('txtOfficeFloorNo')) && ((nullcheck('cmbOffice')) || (nullcheck('txtOffice'))) && (nullcheck('hidOfficeType'));
			if(flag)
			{
				var flgRmrk = (document.frmBuildingDetails.txtOfficeRemarks.value == "")?confirm("Do you want to save Building without remarks?"):true;
			}
			flagOffice = flag && flgRmrk;
			//txtOfficeFloorNo cmbOffice txtOffice radOffice hidOfficeType txtOfficeRemarks
			return(flagOffice);
		}
	////////////////////////////////////////////////////////////////////////////////////////






	/* *****************************************************************************************
	 * 			ADD OFFICE - (CHECK FOR NULL TEMPORARY OFFICE & ADD)
	 */
	////////////////////////////////////////////////////////////////////////////////////////

		function addOffice()
		{
			var flagOffice = nullCheckTempOffice();
			if(flagOffice)
			{
				addTempRow('funEditOffice','txtOfficeFloorNo/cmbOffice','tblListOffice/txtOfficeFloorNo/cmbOffice/hidOfficeType/txtOfficeRemarks'); 
				hideRemarks('tblListOffice','txtOfficeRemarks');  
				cmbCaptionGrid('tblListOffice/cmbOffice'); 
				clearOffice();		
			}
			else
			{
				alert("Please fill all the fields");
			}
		}
	////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		
		
	/* *****************************************************************************************
	 * 			UPDATE OFFICE - (CHECK FOR NULL TEMPORARY OFFICE & UPDATE)
	 */
	////////////////////////////////////////////////////////////////////////////////////////

		function updateOffice()
		{
			var flagOffice = nullCheckTempOffice();
			if(flagOffice)
			{
				updateTempRow(SELECTED_OFFICE,'tblListOffice/txtOfficeFloorNo/cmbOffice/hidOfficeType/txtOfficeRemarks'); 
				cmbCaptionGrid('tblListOffice/cmbOffice'); 
				hideRemarks('tblListOffice','txtOfficeRemarks'); 		
			}
			else
			{
				alert("Please fill all the fields");
			}
		}
	////////////////////////////////////////////////////////////////////////////////////////



				
				
				





/* *****************************************************************************************
 * 			CLEAR ALL - Resets the whole form (Resets all the 3 tabs)
 */
////////////////////////////////////////////////////////////////////////////////////////

function clearAll()
{
	clearBuild();
	clearFloor();
	clearOffice();
	clearAllGrids(document.getElementById('tblListFloor'));
	clearAllGrids(document.getElementById('tblListOffice'));
}
////////////////////////////////////////////////////////////////////////////////////////






/* *****************************************************************************************
 * 			REFRESH BUTTONS - 'Add' button displayed   ;  Update & Delete buttons are hidden
 */
////////////////////////////////////////////////////////////////////////////////////////

function refreshButtonsFloor()
{
	//document.getElementById('txtFloorNo').disabled = false;
	document.getElementById('txtFloorNo').readonly = false;
	document.frmBuildingDetails.CmdAddFloor.style.display = "inline";
	document.frmBuildingDetails.CmdUpdateFloor.style.display = "none";
    document.frmBuildingDetails.CmdDeleteFloor.style.display = "none";
    SELECTED_FLOOR = "";
}

function refreshButtonsOffice()
{
	document.getElementById('txtOfficeFloorNo').disabled = false;
	document.frmBuildingDetails.CmdAddOffice.style.display = "inline";
	//document.frmBuildingDetails.CmdUpdateOffice.style.display = "none";
    document.frmBuildingDetails.CmdDeleteOffice.style.display = "none";
    SELECTED_OFFICE = "";
}
////////////////////////////////////////////////////////////////////////////////////////






/* *****************************************************************************************
 * 			CLEAR FUNCTIONS - Clears only the input boxes  ;  NOT the grids
 */
////////////////////////////////////////////////////////////////////////////////////////

function clearBuild()
{
	document.frmBuildingDetails.cmbAcc_UnitCode.value = 0;
	document.frmBuildingDetails.cmbOffice_code.value = 0;
	document.frmBuildingDetails.txtDate.value = "";
	document.frmBuildingDetails.cmbFinyr.value = "";
	document.frmBuildingDetails.txtAsset.value = "";
	document.frmBuildingDetails.txtBuilding.value = "";
	document.frmBuildingDetails.cmbOwner.value = "";
	document.frmBuildingDetails.txtSurvey.value = "";
	document.frmBuildingDetails.txtDoorNo.value = "";
	document.frmBuildingDetails.txtVillage.value = "";
	document.frmBuildingDetails.txtBuidConsYear.value = "";
	document.frmBuildingDetails.txtBuildType.value = "";
	document.frmBuildingDetails.txtFloors.value = "";
	document.frmBuildingDetails.txtFoundationType.value = "";
	document.frmBuildingDetails.txtStructuralElements.value = "";
	document.frmBuildingDetails.txtCivilCost.value = "";
	document.frmBuildingDetails.txtElectricalCost.value = "";
	document.frmBuildingDetails.txtExternalServCost.value = "";
	document.frmBuildingDetails.txtAdditionCost.value = "";
	document.frmBuildingDetails.txtBookValue.value = "";
	document.frmBuildingDetails.txtAcHeadCode.value = "";
	document.frmBuildingDetails.txtRemarks.value = "";
	
	//refreshButtonsBuild();
}


function clearFloor()
{
	//txtFloorNo txtFloorConsYear txtFloorHeight txtPlinthArea txtFloorCivilCost txtFloorElectricalCost 
	//txtFloorExternalServCost txtFloorAdditionCost txtFloorBookValue txtBPNo txtFloorAcHeadCode txtFloorRemarks
	document.frmBuildingDetails.txtFloorNo.value = "";
	document.frmBuildingDetails.txtFloorConsYear.value = "";
	document.frmBuildingDetails.txtFloorHeight.value = "";
	document.frmBuildingDetails.txtPlinthArea.value = "";
	document.frmBuildingDetails.txtFloorCivilCost.value = "";
	document.frmBuildingDetails.txtFloorElectricalCost.value = "";
	document.frmBuildingDetails.txtFloorExternalServCost.value = "";
	document.frmBuildingDetails.txtFloorAdditionCost.value = "";
	document.frmBuildingDetails.txtFloorBookValue.value = "";
	document.frmBuildingDetails.txtBPNo.value = "";
	document.frmBuildingDetails.txtFloorAcHeadCode.value = "";
	document.getElementById("txtFloorRemarks").value = "";

	refreshButtonsFloor();
}

function clearOffice()
{
	//txtOfficeFloorNo cmbOffice radOffice hidOfficeType txtOfficeRemarks
	document.frmBuildingDetails.txtOfficeFloorNo.value = "";
	document.frmBuildingDetails.cmbOffice.value = "0";
	document.frmBuildingDetails.txtOffice.value = "";
	document.frmBuildingDetails.radOffice[0].checked = true;
	fncOfficeType("T");
	document.getElementById("txtOfficeRemarks").value = "";
	
	refreshButtonsOffice();
}
////////////////////////////////////////////////////////////////////////////////////////







//txtFloorNo txtFloorConsYear txtFloorHeight txtPlinthArea txtFloorCivilCost txtFloorElectricalCost 
//txtFloorExternalServCost txtFloorAdditionCost txtFloorBookValue txtBPNo txtFloorAcHeadCode txtFloorRemarks

/* *****************************************************************************************
 * 			GRID FUNCTIONS
 */
////////////////////////////////////////////////////////////////////////////////////////

var OFFICE_ID;// DELETE IF UNUSED !!
var FLOOR_ID;// DELETE IF UNUSED !!

var SELECTED_FLOOR = "";
var SELECTED_OFFICE = "";
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			GRID ADD - To add current input onto grid   ;   without storing data into DB  
 */
////////////////////////////////////////////////////////////////////////////////////////
/*
     SYNTAX : 	addTempRow(funEdit,keyField,fields)
     
     		  funEdit  ==> Name of the Function ('funEditOffice' or 'funEditFloor') that is to be invoked on clicking 'EDIT' link from grid.
     		  keyField ==> Key Input fields' names, separated by '/'    -   NOTE this generally depends on Primary Key set
     		  fields   ==> fields[0] - tbody name
     		  			   fields[1],fields[2],.. - Names of all input fields of the tab 
  
     EXAMPLE : addTempRow('funEditOffice','txtOfficeFloorNo/cmbOffice','tblListOffice/txtOfficeFloorNo/cmbOffice/hidOfficeType/txtOfficeRemarks');
*/
function addTempRow(funEdit,keyField,fields)
{
	var col = fields.split('/');
	var len = col.length;
	var tbody=document.getElementById(col[0]);
		
	var multiKeys = keyField.split('/');
	var l = multiKeys.length;
	var key = "";
	for(i=0; i<l-1; i++)
	{
		key = key + document.getElementById(multiKeys[i]).value + "_";
	}
	key = key + document.getElementById(multiKeys[l-1]).value;
	var mycurrent_row=document.createElement("TR");
	mycurrent_row.id=tbody.id + key;
	var cell=document.createElement("TD");
	var anc=document.createElement("A");       
	var url="javascript:"+funEdit+"('" + tbody.id + key + "')";              
	anc.href=url;
	var txtedit=document.createTextNode("Edit");
	anc.appendChild(txtedit);
	cell.appendChild(anc);
	mycurrent_row.appendChild(cell);
	
	var k=tbody.rows.length;
	
	for(i=1;i<len;i++)
	{
		var cell = document.createElement("TD");
		var txt = document.getElementById(col[i]).value;

		if(col[i]=="cmbOffice")
		{
			var txtOffice = document.getElementById("txtOffice");
			if(txtOffice.style.display == "inline")
			{
				txt = txtOffice.value;
			}
		}
		
		var txtNode = document.createTextNode(txt);    
		cell.id = "TD" + k + col[i];
		cell.appendChild(txtNode);
		
		var txtBox = document.createElement("INPUT");
		txtBox.type = "text";
		txtBox.name = "HID" + col[i];
		txtBox.value = txt; //document.getElementById(col[i]).value;
		txtBox.style.display = "none";
		cell.appendChild(txtBox);
		
		mycurrent_row.appendChild(cell);
	}
	
	tbody.appendChild(mycurrent_row);

}
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			GRID UPDATE - To update grid entries   ;   Updated before adding records onto DB  
 */
////////////////////////////////////////////////////////////////////////////////////////
function updateTempRow(key,fields)
{
	var rcells = document.getElementById(key).cells;
	
	var col = fields.split('/');
	var len = col.length;

	// NOTE:    col[0] ==> tbody.id      (tbody of corresponding table)
	
	for(i=1; i<len; i++)
	{
		var txt = document.getElementById(col[i]).value;
		//----------------------
		if(col[i]=="cmbOffice")
		{
			var txtOffice = document.getElementById("txtOffice");
			if(txtOffice.style.display != "none")
			{
				txt = txtOffice.value;
			}
		}
		//----------------------
		rcells.item(i).firstChild.nodeValue = document.getElementById(col[i]).value;
	}
}
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			GRID DELETE  - To delete a single ROW from grids
 */
////////////////////////////////////////////////////////////////////////////////////////

function delTempRow(key,table)
{
	var tbody = document.getElementById(table);
	var row = document.getElementById(key);

	tbody.removeChild(row);
	
}
////////////////////////////////////////////////////////////////////////////////////////






/* *****************************************************************************************
 * 		GRID - COMBO CAPTION replacement - To replace combo.value with the actual caption
 * 					onto grids ;  To be used after "addTempRow(..)" & "updateTempRow(..)"
 */
////////////////////////////////////////////////////////////////////////////////////////

function cmbCaptionGrid(fields)
{
	var col = fields.split('/');
	var len = col.length;
	var tbody = document.getElementById(col[0]);
	var k = tbody.rows.length;
	for(i=1;i<len;i++)
	{
		
			var grd = document.getElementById("TD"+(k-1)+col[i]);
			var val = grd.firstChild.nodeValue;
			var txt;
			
			if(document.getElementById(col[i]).style.display != "none")
			{
				var cmb = document.getElementById(col[i]);
				txt = cmb.options[cmb.selectedIndex].text;
			}
			else
			{
				txt = document.getElementById("txtOffice").value;
				val = txt;
			}
			
			grd.firstChild.nodeValue = txt;
			
			var row = grd.parentNode;
			var cell = document.createElement("TD");
			cell.id = "hidTD"+(k-1)+col[i];
			cell.innerHTML = val;
			cell.style.display = "none";
			
			row.appendChild(cell);
	}
}

////////////////////////////////////////////////////////////////////////////////////////






/* *****************************************************************************************
 * 			GRID CLEAR ALL - To delete all the grids of the tbody mentioned
 */
////////////////////////////////////////////////////////////////////////////////////////

function clearAllGrids(tbody)
{
	var k = tbody.childNodes.length;
	for(i=k-1;i>=0;i--)
	{
		tbody.deleteRow(i-1);
	}
}
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			EDIT GRID FUNCTIONS
 */
////////////////////////////////////////////////////////////////////////////////////////

function funEditFloor(rid)
{
	SELECTED_FLOOR = rid;
	
	r = document.getElementById(rid);
	rcells = r.cells;
	//txtFloorNo txtFloorConsYear txtFloorHeight txtPlinthArea txtFloorCivilCost txtFloorElectricalCost 
	//txtFloorExternalServCost txtFloorAdditionCost txtFloorBookValue txtBPNo txtFloorAcHeadCode txtFloorRemarks

	document.getElementById('txtFloorNo').value = rcells.item(1).firstChild.nodeValue;
	document.getElementById('txtFloorConsYear').value = rcells.item(2).firstChild.nodeValue;
	document.getElementById('txtFloorHeight').value = rcells.item(3).firstChild.nodeValue;
	document.getElementById('txtPlinthArea').value = rcells.item(4).firstChild.nodeValue;
	document.getElementById('txtFloorCivilCost').value = rcells.item(5).firstChild.nodeValue;
	document.getElementById('txtFloorElectricalCost').value = rcells.item(6).firstChild.nodeValue;
	document.getElementById('txtFloorExternalServCost').value = rcells.item(7).firstChild.nodeValue;
	document.getElementById('txtFloorAdditionCost').value = rcells.item(8).firstChild.nodeValue;
	document.getElementById('txtFloorBookValue').value = rcells.item(9).firstChild.nodeValue;
	document.getElementById('txtBPNo').value = rcells.item(10).firstChild.nodeValue;	
	document.getElementById('txtFloorAcHeadCode').value = rcells.item(11).firstChild.nodeValue;
	document.getElementById('txtFloorRemarks').value = rcells.item(12).firstChild.nodeValue;
	
	document.getElementById('txtFloorNo').readonly = true;
	document.getElementById('CmdAddFloor').style.display = "none";
	document.getElementById('CmdUpdateFloor').style.display = "inline";
	document.getElementById('CmdDeleteFloor').style.display = "inline";
}

function funEditOffice(rid)
{
	SELECTED_OFFICE = rid;
	
	r = document.getElementById(rid);
	rcells = r.cells;
	document.getElementById('txtOfficeFloorNo').value = rcells.item(1).firstChild.nodeValue;
	var officeType = document.getElementsByName('radOffice');
	if(officeType[0].value == rcells.item(3).firstChild.nodeValue)
	{
		officeType[0].checked = true;
		fncOfficeType("T");
		document.getElementById('cmbOffice').value = rcells.item(5).firstChild.nodeValue;
	}
	else
	{
		officeType[1].checked = true;
		fncOfficeType("N");
		document.getElementById('txtOffice').value = rcells.item(5).firstChild.nodeValue;
	}

	document.getElementById('txtOfficeRemarks').value = rcells.item(4).firstChild.nodeValue;
	
	document.getElementById('txtOfficeFloorNo').disabled = true;
	document.getElementById('CmdAddOffice').style.display = "none";
	document.getElementById('CmdDeleteOffice').style.display = "inline";
}
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			HIDE "REMARKS" CELL FROM GRIDS
 */
////////////////////////////////////////////////////////////////////////////////////////

function hideRemarks(tbl,ele)
{
	var k = document.getElementById(tbl).rows.length - 1;
	if(tbl=="tblListFloor")
	{
		if(SELECTED_FLOOR != "")
		{
			k = SELECTED_FLOOR;
		}
	}
	else if(tbl=="tblListOffice")
	{
		if(SELECTED_OFFICE != "")
		{
			k = SELECTED_OFFICE;
		}
	}
	document.getElementById("TD"+k+ele).style.display = "none";
}
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			SUBMIT FORM I/P TO SERVER 
 */
////////////////////////////////////////////////////////////////////////////////////////

	function callServer(command)
	{
		if(command=="Load")
	    {
	        var flag=(nullcheck('cmbAcc_UnitCode') && nullcheck('cmbOffice_code') && nullcheck('cmbFinyr') && nullcheck('txtAsset'));
                var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
                var cmbOffice_code=document.getElementById("cmbOffice_code").value;
                var cmbFinyr=document.getElementById("cmbFinyr").value;
                var txtAsset=document.getElementById("txtAsset").value;
                if(flag==true){        
	        	url="../../../../../Building_Details_Load?command=Load&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinyr="+cmbFinyr+"&txtAsset="+txtAsset;
		        var req=getTransport();		
		        req.open("POST",url,true); 
		        req.onreadystatechange=function()
		        {
		        	LoadRecordResponse(req);
		        };   

		        req.send(null);
	        }
	        else
	        {
	        	alert("Please fill all the fields");
	        }
	    }

		else if((command=="Delete")&&(confirm('This operation deletes the building along with the foors & offices in it. Do you want to continue?')))
	    {
	        var flag=(nullcheck('cmbAcc_UnitCode') && nullcheck('cmbOffice_code') && nullcheck('cmbFinyr') && nullcheck('txtAsset'));
	        if(flag==true)																																								 
	        {
	        	url="../../../../../Building_Details_Edit?command=Delete&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinyr="+cmbFinyr+"&txtAsset="+txtAsset;
		        var req=getTransport();		
		        req.open("GET",url,true); 
		        req.onreadystatechange=function()
		        {
		        	DeleteRecordResponse(req);
		        }   

		        req.send(null);
	        }
	        else
	        {
	        	alert("Please fill the essential fields");
	        }
	    }
	}
////////////////////////////////////////////////////////////////////////////////////////




	
	
	
/* *****************************************************************************************
 * 			HANDLE RESPONSE
 */
////////////////////////////////////////////////////////////////////////////////////////


	function DeleteRecordResponse(req)
	{
	    if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            if(flag=="success")
	            {
	            	alert("Record Deleted Successfully!");
	            }
	            else
	            {
	            	alert("Sorry.. Could not delete the record");
	            }
	        }
	    }
	}

	function DeleteRecordResponse(req)
	{
	    if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            if(flag=="success")
	            {   
	            	alert("Building Deleted successfully!");
	            }
	            else
	            {
	            	alert("Delete Building Operation Failed..");
	            }
	        }
	    }
	}
	
	function LoadRecordResponse(req)
	{
	    if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            if(flag=="success")
	            {   
	    	        document.frmBuildingDetails.cmbAcc_UnitCode.value = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.cmbOffice_code.value = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.cmbFinyr.value = baseResponse.getElementsByTagName("FINANCIAL_YEAR")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtAsset.value = baseResponse.getElementsByTagName("ASSET_CODE")[0].firstChild.nodeValue;
	    	           
	            	document.frmBuildingDetails.txtDate.value = baseResponse.getElementsByTagName("TRANS_DATE")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtBuilding.value = baseResponse.getElementsByTagName("BUILDING_NAME")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.cmbOwner.value = baseResponse.getElementsByTagName("OWNER_CODE")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtSurvey.value = baseResponse.getElementsByTagName("SURVEY_NO")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtDoorNo.value = baseResponse.getElementsByTagName("DOOR_NO")[0].firstChild.nodeValue;

	    	        document.frmBuildingDetails.txtVillage.value = baseResponse.getElementsByTagName("VILLAGE")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtBuidConsYear.value = baseResponse.getElementsByTagName("YEAR_OF_CONSTRUCTION")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtBuildType.value = baseResponse.getElementsByTagName("TYPE_OF_BUILDING")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtFloors.value = baseResponse.getElementsByTagName("NO_OF_FLOORS")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtFoundationType.value = baseResponse.getElementsByTagName("TYPE_OF_FOUNDATION")[0].firstChild.nodeValue;

	    	        document.frmBuildingDetails.txtStructuralElements.value = baseResponse.getElementsByTagName("STRUCTURED_BLDG_ELEMENTS")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtCivilCost.value = baseResponse.getElementsByTagName("TOTAL_CIVIL_COST")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtElectricalCost.value = baseResponse.getElementsByTagName("TOTAL_ELECTRICAL_COST")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtExternalServCost.value = baseResponse.getElementsByTagName("TOTAL_EXTERNAL_COST")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtAdditionCost.value = baseResponse.getElementsByTagName("TOTAL_ADDITIONS_COST")[0].firstChild.nodeValue;

	    	        document.frmBuildingDetails.txtBookValue.value = baseResponse.getElementsByTagName("TOTAL_BOOK_VALUE")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtAcHeadCode.value = baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
	    	        document.frmBuildingDetails.txtRemarks.value = baseResponse.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;

	    	        
	            	
                         var floor = baseResponse.getElementsByTagName("FLOOR");
		    	        var flen = floor.length;
	    	        
	    	      /*  var floor = baseResponse.getElementsByTagName("FLOOR")[0].firstChild.nodeValue;
                        alert("floor"+floor);
	    	        var flen = floor.length;*/
	    	        	    	        
	    	        for(j=0; j<flen; j++)
	    	        {
		    	        document.frmBuildingDetails.txtFloorNo.value = floor[j].getElementsByTagName("FLOOR_NO")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorConsYear.value = floor[j].getElementsByTagName("YEAR_OF_CONSTRUCTION")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorHeight.value = floor[j].getElementsByTagName("FLOOR_HEIGHT")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtPlinthArea.value = floor[j].getElementsByTagName("PLINTH_AREA")[0].firstChild.nodeValue;
	
		    	        document.frmBuildingDetails.txtFloorCivilCost.value = floor[j].getElementsByTagName("CIVIL_COST")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorElectricalCost.value = floor[j].getElementsByTagName("ELECTRICAL_COST")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorExternalServCost.value = floor[j].getElementsByTagName("EXTERNAL_COST")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorAdditionCost.value = floor[j].getElementsByTagName("ADDITIONAL_COST")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorBookValue.value = floor[j].getElementsByTagName("BOOK_VALUE")[0].firstChild.nodeValue;
	
		    	        document.frmBuildingDetails.txtBPNo.value = floor[j].getElementsByTagName("BP_NO_FOR_CONSTRUCTION")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorAcHeadCode.value = floor[j].getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
		    	        document.frmBuildingDetails.txtFloorRemarks.value = floor[j].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
	    	        
		    	        addTempRow('funEditFloor','txtFloorNo','tblListFloor/txtFloorNo/txtFloorConsYear/txtFloorHeight/txtPlinthArea/txtFloorCivilCost/txtFloorElectricalCost/txtFloorExternalServCost/txtFloorAdditionCost/txtFloorBookValue/txtBPNo/txtFloorAcHeadCode/txtFloorRemarks'); 
		    	        hideRemarks('tblListFloor','txtFloorRemarks'); 
		    	        clearFloor();
	    	        }
	    	        
	    	        
	    	        
	    	        var office = baseResponse.getElementsByTagName("OFFICE");
	    	        var olen = office.length;
	    	        for(k=0; k<olen; k++)
	    	        {
	    	        	document.frmBuildingDetails.txtOfficeFloorNo.value = office[k].getElementsByTagName("FLOOR_NO")[0].firstChild.nodeValue;
	    	        	var OffType = office[k].getElementsByTagName("TYPE_OF_OCCUPYING_OFFICE")[0].firstChild.nodeValue;
	    	        	document.frmBuildingDetails.hidOfficeType.value = OffType;
	    	        	fncOfficeType(OffType);
		    	        
	    	        	if(OffType=="T")
	    	        	{
	    	        		document.frmBuildingDetails.cmbOffice.value = office[k].getElementsByTagName("TWAD_OFFICE_ID")[0].firstChild.nodeValue;
	    	        	}
	    	        	else
	    	        	{
	    	        		document.frmBuildingDetails.txtOffice.value = office[k].getElementsByTagName("NONTWAD_OFFICE_NAME")[0].firstChild.nodeValue;
	    	        	}
		    	        document.frmBuildingDetails.txtOfficeRemarks.value = office[k].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
		    	        
		    	        addTempRow('funEditOffice','txtOfficeFloorNo/cmbOffice','tblListOffice/txtOfficeFloorNo/cmbOffice/hidOfficeType/txtOfficeRemarks'); 
		    	        hideRemarks('tblListOffice','txtOfficeRemarks');  
		    	        cmbCaptionGrid('tblListOffice/cmbOffice'); 
		    	        clearOffice();
	    	        }

	    	        
	            }
	            else
	            {
	            	alert("Sorry.. Could not load records");
	            }
	            
	            
	        }
	    }
	}
	function editbutton(){
		var len1=document.getElementById('txtFloors').value;	
		var len2=document.getElementById('tblListFloor').rows.length;	
		var len3=document.getElementById('tblListOffice').rows.length;	
		var ret="",aret="";
		var count=0;
		var rcells = "";	
		var fcells = "";
		if(len1!=""){
			if(len1==len2){			
				if(len3<len2){				
					ret="Occupying Floor No Must be greater than Floor Details Floor No";
				}else{
					for(var i=0; i<len3; i++){
						for(var j=0; j<=i; j++){					
							rcells=parseInt(document.getElementById('tblListOffice').rows[j].cells[1].firstChild.nodeValue);
							fcells=parseInt(document.getElementById('tblListFloor').rows[i].cells[1].firstChild.nodeValue);
							//alert("rcels "+rcells+" fcells "+fcells);
							if(rcells>fcells){
								count++;							
								break;
							}
						}								
					}
					//alert("count "+count);
					if(count==0){
						ret=true;
					}else{
						ret="Invalid Floor No";
					}				
				}
			}else{
				ret="Floor Details Floor No and No Of Floor Must be equal";	
			}		
		}else{
			ret="Enter No Of Floor";
		}
		/*alert("len "+document.getElementById('txtFloorAcHeadCode').length);
		if(document.getElementById('txtFloorAcHeadCode').length==6){
			aret=true;
		}else{
			aret="Account Head Code Must be 6 digit";
		}*/
		if(ret==true){
			document.frmBuildingDetails.submit();
		}else{
			alert(ret);
		}
	};
////////////////////////////////////////////////////////////////////////////////////////
