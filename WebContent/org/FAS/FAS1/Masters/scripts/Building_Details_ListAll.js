

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
 * 			CHECK FOR NULL
 */
////////////////////////////////////////////////////////////////////////////////////////

	function nullcheck(index)
	{
		if((index == null)||(index == '')||(index == ' '))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			GRID CLEAR ALL - To delete all the grids of the tbody mentioned
 */
////////////////////////////////////////////////////////////////////////////////////////

function clearAllGrids(tbl)
{
	var tbody = document.getElementById(tbl);
	var k = tbody.childNodes.length;
	for(var i=k-1;i>=0;i--)
	{
		tbody.deleteRow(i-1);
	}
}
////////////////////////////////////////////////////////////////////////////////////////







/* *****************************************************************************************
 * 			SUBMIT FORM I/P TO SERVER 
 */
////////////////////////////////////////////////////////////////////////////////////////

	function callServer(command)
	{
		if(command=="LoadBuild")
	    {
	        var cmbAcc_UnitCode=document.frmListBuildings.cmbAcc_UnitCode.value;
	        var cmbOffice_code=document.frmListBuildings.cmbOffice_code.value;
	        var cmbFinyr=document.frmListBuildings.cmbFinyr.value;
	        var flag=(nullcheck(cmbAcc_UnitCode) && nullcheck(cmbOffice_code) && nullcheck(cmbFinyr));
	        if(flag==true)																																								 
	        {
	        	url="../../../../../Building_Details_ListAll?command=LoadBuild&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinyr="+cmbFinyr;
		        var req=getTransport();		
		        req.open("POST",url,true); 
		        req.onreadystatechange=function()
		        {
		        	LoadRecordResponse(req);
		        }   

		        req.send(null);
	        }
	        else
	        {
	        	alert("Please fill all the fields");
	        }
	    }
		
/*		
		if(command=="LoadFloor")
	    {
	        var cmbAcc_UnitCode=document.frmListBuildings.cmbAcc_UnitCode.value;
	        var cmbOffice_code=document.frmListBuildings.cmbOffice_code.value;
	        var cmbFinyr=document.frmListBuildings.cmbFinyr.value;
	        var flag=(nullcheck(cmbAcc_UnitCode) && nullcheck(cmbOffice_code) && nullcheck(cmbFinyr));
	        if(flag=true)																																								 
	        {
	        	url="../../../../../Building_Details_ListAll?command=LoadFloor&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinyr="+cmbFinyr;
		        var req=getTransport();		
		        req.open("POST",url,true); 
		        req.onreadystatechange=function()
		        {
		        	LoadRecordResponse(req);
		        }   

		        req.send(null);
	        }
	        else
	        {
	        	alert("Please fill all the fields");
	        }
	    }
		
		
		if(command=="LoadOffice")
	    {
	        var cmbAcc_UnitCode=document.frmListBuildings.cmbAcc_UnitCode.value;
	        var cmbOffice_code=document.frmListBuildings.cmbOffice_code.value;
	        var cmbFinyr=document.frmListBuildings.cmbFinyr.value;
	        var flag=(nullcheck(cmbAcc_UnitCode) && nullcheck(cmbOffice_code) && nullcheck(cmbFinyr));
	        if(flag=true)																																								 
	        {
	        	url="../../../../../Building_Details_ListAll?command=LoadOffice&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&cmbFinyr="+cmbFinyr;
		        var req=getTransport();		
		        req.open("POST",url,true); 
		        req.onreadystatechange=function()
		        {
		        	LoadRecordResponse(req);
		        }   

		        req.send(null);
	        }
	        else
	        {
	        	alert("Please fill all the fields");
	        }
	    }
	*/
	}
////////////////////////////////////////////////////////////////////////////////////////




	
	
	
/* *****************************************************************************************
 * 			HANDLE RESPONSE
 */
////////////////////////////////////////////////////////////////////////////////////////


	function LoadRecordResponse(req)
	{
	    if(req.readyState==4)
	    {
	        if(req.status==200)
	        {  
	            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
        
	            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	            var command=baseResponse.getElementsByTagName("command")[0].firstChild.nodeValue;
	            
	            
	            
	            if(command=="LoadBuild")
	            {
		            if(flag=="success")
		            {   

		            	var len = baseResponse.getElementsByTagName("ASSET_CODE").length;
		            	
		            	for(var i=0; i<len; i++)
		            	{
			    	        //document.frmListBuildings.cmbAcc_UnitCode.value = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
			    	        //document.frmListBuildings.cmbOffice_code.value = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[0].firstChild.nodeValue;
			    	        //document.frmListBuildings.cmbFinyr.value = baseResponse.getElementsByTagName("FINANCIAL_YEAR")[0].firstChild.nodeValue;
			    	        var val = '';
			    	        
		            		val += baseResponse.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;
			    	           
			            	//document.frmListBuildings.txtDate.value = baseResponse.getElementsByTagName("TRANS_DATE")[i].firstChild.nodeValue;
		            		val += '#@/' + baseResponse.getElementsByTagName("BUILDING_NAME")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.cmbOwner.value = baseResponse.getElementsByTagName("OWNER_CODE")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtSurvey.value = baseResponse.getElementsByTagName("SURVEY_NO")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtDoorNo.value = baseResponse.getElementsByTagName("DOOR_NO")[i].firstChild.nodeValue;
		
			    	        //document.frmListBuildings.txtVillage.value = baseResponse.getElementsByTagName("VILLAGE")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtBuidConsYear.value = baseResponse.getElementsByTagName("YEAR_OF_CONSTRUCTION")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtBuildType.value = baseResponse.getElementsByTagName("TYPE_OF_BUILDING")[i].firstChild.nodeValue;
		            		val += '#@/' + baseResponse.getElementsByTagName("NO_OF_FLOORS")[i].firstChild.nodeValue;
		            		val += '#@/' + baseResponse.getElementsByTagName("TYPE_OF_FOUNDATION")[i].firstChild.nodeValue;
		
			    	        //document.frmListBuildings.txtStructuralElements.value = baseResponse.getElementsByTagName("STRUCTURED_BLDG_ELEMENTS")[i].firstChild.nodeValue;
		            		val += '#@/' + baseResponse.getElementsByTagName("TOTAL_CIVIL_COST")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtElectricalCost.value = baseResponse.getElementsByTagName("TOTAL_ELECTRICAL_COST")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtExternalServCost.value = baseResponse.getElementsByTagName("TOTAL_EXTERNAL_COST")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtAdditionCost.value = baseResponse.getElementsByTagName("TOTAL_ADDITIONS_COST")[i].firstChild.nodeValue;
		
		            		val += '#@/' + baseResponse.getElementsByTagName("TOTAL_BOOK_VALUE")[i].firstChild.nodeValue;
		            		//val += '#@/' + baseResponse.getElementsByTagName("ACCOUNT_HEAD_CODE")[i].firstChild.nodeValue;
		            		val += '#@/' + baseResponse.getElementsByTagName("ACCOUNT_HEAD_DESC")[i].firstChild.nodeValue;
			    	        //document.frmListBuildings.txtRemarks.value = baseResponse.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
		            		
		            		addRow('tblBuild',val,true);
		            	}
		            }
	            	else
	            	{
	            		alert("Failed to load Buildings!");
	            	}
	            }    
		            
	            
	            
	            
		            
		            
	            if(command=="LoadFloor")
	            {
		            if(flag=="success")
		            {   
		    	        var floor = baseResponse.getElementsByTagName("FLOOR");
		    	        var flen = floor.length;
		    	        	    	        
		    	        for(var i=0; i<flen; i++)
		    	        {
		    	        	var val = '';
		    	        	
		    	        	val += floor[i].getElementsByTagName("FLOOR_NO")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("YEAR_OF_CONSTRUCTION")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("FLOOR_HEIGHT")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("PLINTH_AREA")[0].firstChild.nodeValue;
		
		    	        	val += '#@/' + floor[i].getElementsByTagName("CIVIL_COST")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("ELECTRICAL_COST")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("EXTERNAL_COST")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("ADDITIONAL_COST")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("BOOK_VALUE")[0].firstChild.nodeValue;
		
		    	        	val += '#@/' + floor[i].getElementsByTagName("BP_NO_FOR_CONSTRUCTION")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("ACCOUNT_HEAD_CODE")[0].firstChild.nodeValue;
		    	        	val += '#@/' + floor[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
			    	        
			    	        addRow('tblListFloor',val,true);
		    	        }
		            }
	            	else
	            	{
	            		alert("Failed to load Floor!");
	            	}
	            }    
		            
	    	        
	    	        
	    	        
	            
	            if(command=="LoadOffice")
	            {         alert(flag);
		            if(flag=="success")
		            {   alert("inside loadoffice");
		                var office = baseResponse.getElementsByTagName("OFFICE");
		    	        var olen = office.length;
		    	        for(var i=0; i<olen; i++)
		    	        {
		    	        	document.frmListBuildings.txtOfficeFloorNo.value = office[i].getElementsByTagName("FLOOR_NO")[0].firstChild.nodeValue;
			    	        document.frmListBuildings.hidOfficeType.value = office[i].getElementsByTagName("TYPE_OF_OCCUPYING_OFFICE")[0].firstChild.nodeValue;
			    	        document.frmListBuildings.cmbOffice.value = office[i].getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
			    	        document.frmListBuildings.txtOfficeRemarks.value = office[i].getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
			    	        
			    	        addRow('tblListOffice',val,false);
			    	        //addTempRow('funEditOffice','txtOfficeFloorNo/cmbOffice','tblListOffice/txtOfficeFloorNo/cmbOffice/hidOfficeType/txtOfficeRemarks'); 
			    	        //hideRemarks('tblListOffice','txtOfficeRemarks');  
			    	        //cmbCaptionGrid('tblListOffice/cmbOffice'); 
			    	        //clearOffice();
		    	        }
		            }
	            	else
	            	{
	            		alert("Failed to load Office!");
	            	}
	            }    
		            
	            
	            
	            
	        }
	    }
	}
////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
/* *****************************************************************************************
 * 			ADD GRID ROW - with/without 'More Details' link
 */
////////////////////////////////////////////////////////////////////////////////////////

	function addRow(tbl,val,link)
	{
		var tbody = document.getElementById(tbl);

		var col = val.split('#@/');
		var len = col.length;
		var k = tbody.childNodes.length;

		
		var mycurrent_row=document.createElement("TR");
		mycurrent_row.id = k;


		for(var i=0;i<len;i++)
		{
			var cell = document.createElement("TD");
			var txtNode = document.createTextNode(col[i]);    
			cell.appendChild(txtNode);
			
			mycurrent_row.appendChild(cell);
		}

		if(link==true)
		{
			var cell=document.createElement("TD");
			var anc=document.createElement("A");       
			var url="javascript:MoreDetails('" + k + "')";              
			anc.href=url;
			var txtMore=document.createTextNode("Click here");
			anc.appendChild(txtMore);
			cell.appendChild(anc);
			mycurrent_row.appendChild(cell);
		}
		tbody.appendChild(mycurrent_row);
	}
////////////////////////////////////////////////////////////////////////////////////////
	
	



	
	
/* *****************************************************************************************
 * 			ON CLICK 'MORE DETAILS'
 */
////////////////////////////////////////////////////////////////////////////////////////
	
	function MoreDetails(rowid)
	{
		var asset_code = document.getElementById(rowid).cells.item(0).firstChild.nodeValue;
		ListFloors(asset_code);
	}
////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
/* *****************************************************************************************
 * 			LIST ALL - FLOORS
 */
////////////////////////////////////////////////////////////////////////////////////////

	var winListAllFloors;

	function ListFloors(asset)
	{
	    if (winListAllFloors && winListAllFloors.open && !winListAllFloors.closed) 
	    {
	       winListAllFloors.resizeTo(500,500);
	       winListAllFloors.moveTo(250,250); 
	       winListAllFloors.focus();
	    }
	    else
	    {
	       winListAllFloors=null
	    }        
	    
	    var accounting_unit_id = document.frmListBuildings.cmbAcc_UnitCode.value;
	    var accounting_unit_office_id = document.frmListBuildings.cmbOffice_code.value;
	    var financial_year = document.frmListBuildings.cmbFinyr.value;
	    var asset_code = asset;
	    
	    winListAllFloors= window.open("../../../../../org/FAS/FAS1/Masters/jsps/Building_Floor_ListAll.jsp?accounting_unit_id="+accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code,"ListAllFloors","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
	    winListAllFloors.moveTo(250,250);  
	    winListAllFloors.focus();
	}
////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
	

/* *****************************************************************************************
 * 			LIST ALL - OFFICES
 */
////////////////////////////////////////////////////////////////////////////////////////
	
	var winListAllOffices;
	
	function ListOffices(acUnitId,acUnitOfficeId,finYr,assetCode,floorNo)
	{
	    if (winListAllOffices && winListAllOffices.open && !winListAllOffices.closed)
	    {
	       winListAllOffices.resizeTo(500,500);
	       winListAllOffices.moveTo(250,250); 
	       winListAllOffices.focus();
	    }
	    else
	    {
	       winListAllOffices=null
	    }        

	    var accounting_unit_id = acUnitId;
	    var accounting_unit_office_id = acUnitOfficeId;
	    var financial_year = finYr;
	    var asset_code = assetCode;
	    var floor_no = floorNo;
	    
	    winListAllOffices= window.open("../../../../../org/FAS/FAS1/Masters/jsps/Building_Office_ListAll.jsp?accounting_unit_id="+accounting_unit_id+"&accounting_unit_office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&asset_code="+asset_code+"&floor_no="+floor_no,"ListAllOffices","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
	    winListAllOffices.moveTo(250,250);  
	    winListAllOffices.focus();
	}
////////////////////////////////////////////////////////////////////////////////////////

