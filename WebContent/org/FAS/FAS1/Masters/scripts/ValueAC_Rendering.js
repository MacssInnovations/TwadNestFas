var jobflag;
var officeid;
var seq=1;
var cur;
var winListAllBudget;

function LoadAssetRenderingUnits(offid)
{
	//alert(offid);
    url="../../../../../ValueAC_Rendering?Command=AssetRender&offid="+offid;
    var req=getTransport();
    req.open("POST",url,true); 
    req.onreadystatechange=function()
    {
    	handle_loadAssetRenderingUnits(req);
    };   
    
    req.send(null);
}


function handle_loadAssetRenderingUnits(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	var cmb = document.getElementById("cmbRenderAccUnit");
        	cmb.length =0;
        	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            var len = baseResponse.getElementsByTagName("record").length;
            if(flag=="success")
            {
            	for(i=0;i<len;i++)
            	{
            		//var AR_UnitId = baseResponse.getElementsByTagName("ASSET_RENDERING_UNIT_ID")[0].firstChild.nodeValue;
            		//var AR_UnitName = baseResponse.getElementsByTagName("ASSET_RENDERING_UNIT_NAME")[0].firstChild.nodeValue;
            		
            		
                    var option=document.createElement("OPTION");
                    var root = baseResponse.getElementsByTagName("record")[i];
                    var AR_UnitId=root.getElementsByTagName("ASSET_RENDERING_UNIT_ID")[0].firstChild.nodeValue;
                    var AR_UnitName=root.getElementsByTagName("ASSET_RENDERING_UNIT_NAME")[0].firstChild.nodeValue;
                    
                    option.text=AR_UnitName;
                    option.value=AR_UnitId;
                    try
                    {   
                    	cmb.add(option);
                    }
                    catch(errorObject)
                    {
                    	cmb.add(option,null);
                    }   

            	}
            	var unit=baseResponse.getElementsByTagName("ASSET_RENDERING_UNIT_ID")[0].firstChild.nodeValue;
            	LoadaccUnits(unit);
            	
            }
            else
            {
            	alert("Could not load Asset Rendeering Units");
            }
        }
        
    }
}


function call_mainJSP_script(dateField,blur_flag)
{}

function ListAll()
{
    if (winListAllBudget && winListAllBudget.open && !winListAllBudget.closed) 
    {
       winListAllBudget.resizeTo(500,500);
       winListAllBudget.moveTo(250,250); 
       winListAllBudget.focus();
    }
    else
    {
       winListAllBudget=null;
    }  
   var cmbRenderAccUnit = document.getElementById("cmbRenderAccUnit").value;
   var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode").value;
   var cmbOffice_code = document.getElementById("cmbOffice_code").value;
   //alert("cmbRenderAccUnit--"+cmbRenderAccUnit+" cmbAcc_UnitCode--"+cmbAcc_UnitCode+" cmbOffice_code --"+cmbOffice_code);
 
 	     
    winListAllBudget= window.open("../../../../../org/FAS/FAS1/Masters/jsps/ValueAC_Rendering_ListAll.jsp?cmbRenderAccUnit="+cmbRenderAccUnit+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"ListAllAccounting","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
    winListAllBudget.moveTo(250,250);  
    winListAllBudget.focus();
}



/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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


function nullcheck(index)
{
	if((index == null)||(index == '')||(index == ' '))
		return false;
	else
		return true;
}



function callServer(Command,param)
{
	var flag = domainCheckDate();
	if(Command != "Delete")
	{
		if(flag == false)
		{
			return;
		}
	}
    if(Command=="Add")
    {
    	var renderUnitId=document.frmAccountUnit.cmbRenderAccUnit.value;
    	alert(renderUnitId);
        var accUnitId=document.frmAccountUnit.cmbAcc_UnitCode.value;
        var accOfficeId=document.frmAccountUnit.cmbOffice_code.value;
        var dtFrm=document.frmAccountUnit.txtDtFrm.value;
        var active;
        var dtTo;
        
        if(document.frmAccountUnit.radActive[0].checked)
        {
        	active = "Y";
        	dtTo="";
        }
        else
        {
        	active = "N";
        	dtTo=document.frmAccountUnit.txtDtTo.value;
        	if(nullcheck(dtTo) == false)
        	{
        		alert("Enter all the fields");
        		return;
        	}
        }
        var flag=(nullcheck(renderUnitId) && nullcheck(accUnitId) && nullcheck(accOfficeId) && nullcheck(dtFrm) && nullcheck(active));
        if(flag==true)
        {
	        url="../../../../../ValueAC_Rendering?Command=Add&renderUnitId="+renderUnitId+"&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+"&dtFrm="+dtFrm+"&active="+active+"&dtTo="+dtTo;
	        var req=getTransport();
	        req.open("POST",url,true); 
	        req.onreadystatechange=function()
	        {
	        	AddRecordResponse(req);
	        } ;  
	        
	        req.send(null);
        }
        else
        {
        	alert("Enter all the fields");
        }
    }
    
    if(Command=="Update")
    {
    	var renderUnitId=document.frmAccountUnit.cmbRenderAccUnit.value;
	    var accUnitId=document.frmAccountUnit.cmbAcc_UnitCode.value;
	    var accOfficeId=document.frmAccountUnit.cmbOffice_code.value;
	    var dtFrm=document.frmAccountUnit.txtDtFrm.value;
	    var active;
	    var dtTo="";
	    if(document.frmAccountUnit.radActive[0].checked)
	    {
	    	active = "Y";
	    }
	    else
	    {
	    	active = "N";
	    	dtTo=document.frmAccountUnit.txtDtTo.value;
        	if(nullcheck(dtTo)==false)
        	{
        		alert("Enter all the fields");
        		return;
        	}
	    }
	    var flag=(nullcheck(renderUnitId) && nullcheck(accUnitId) && nullcheck(accOfficeId) && nullcheck(dtFrm) && nullcheck(active));
	    if(flag==true)
	    {
	        url="../../../../../ValueAC_Rendering?Command=Update&renderUnitId="+renderUnitId+"&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+"&dtFrm="+dtFrm+"&active="+active+"&dtTo="+dtTo;
	      // alert(url);
	        var req=getTransport();
	        req.open("POST",url,true);
	        req.onreadystatechange=function()
	        {
	           UpdateRecordResponse(req);
	        };   
	        req.send(null);
        }
	    else
	    {
	    	alert("Enter all the fields");
	    }
    }
    
    if(Command=="Delete")
    {
    	var renderUnitId=document.frmAccountUnit.cmbRenderAccUnit.value;
    	//alert(renderUnitId);
//    	var accUnitId=document.frmAccountUnit.cmbAcc_UnitCode.value;
//	    var accOfficeId=document.frmAccountUnit.cmbOffice_code.value;
	    var flag=nullcheck(renderUnitId);
	    if(flag==true)
	    {
	        url="../../../../../ValueAC_Rendering?Command=Delete&renderUnitId="+renderUnitId; //+"&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId;
	        var req=getTransport();
	        req.open("POST",url,true); 
	        req.onreadystatechange=function()
	        {
	            DeleteRecordResponse(req);
	        };   
	        req.send(null);
        }
	    else
	    {
	    	alert("Please select an Asset Rendering unit");
	    }
    }
}


function AddRecordResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
            	alert("Record Added Successfully!");
                clearAll();
            }
            else
            {
            	alert("Could not add the record");
            }
        }
        
    }
}
function UpdateRecordResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
            	alert("Record Updated Successfully!");
                clearAll();
            }
            else
            {
            	alert("Could not update the record");
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
            	alert("Record Cancel Successfully!");
                clearAll();
            }
            else
            {
            	alert("Could not delete the record");
            }
        }
    }
}

function goBack(cmbRenderAccUnit,cmbAcc_UnitCode,frmDt,actv,toDt)
{
	document.frmAccountUnit.cmbRenderAccUnit.value = cmbRenderAccUnit;
	LoadaccUnits(document.frmAccountUnit.cmbRenderAccUnit.value);
	//alert("Loading****");
	document.frmAccountUnit.cmbAcc_UnitCode.value = cmbAcc_UnitCode;
	LoadOffice(document.frmAccountUnit.cmbAcc_UnitCode.value);
    dtFrm=document.frmAccountUnit.txtDtFrm.value = frmDt;

    if(actv == 'Y')
    	document.frmAccountUnit.radActive[0].checked = true;
    else
    	document.frmAccountUnit.radActive[1].checked = true;
    
    if((toDt == ' ')||(toDt == null)||(toDt == '-'))
    {
    	document.getElementById('txtDtTo').value = '';
    	
    	document.getElementById('trDtTo1').style.display='none';
    	document.getElementById('trDtTo2').style.display='none';
    }
    else
    {
    	document.getElementById('trDtTo1').style.display='inline';
    	document.getElementById('trDtTo2').style.display='inline';

    	document.frmAccountUnit.txtDtTo.value = toDt;	
    }
    
    document.frmAccountUnit.CmdAdd.disabled = true;
    document.frmAccountUnit.CmdUpdate.disabled = false;
    document.frmAccountUnit.CmdDelete.disabled = false;
}

function refreshButtons()
{
    document.frmAccountUnit.CmdAdd.disabled = false;
    document.frmAccountUnit.CmdUpdate.disabled = true;
    document.frmAccountUnit.CmdDelete.disabled = true;
}

function clearAll(officeid)
{
	//goBack(0,0,'','','','');
	alert("clear ");
	
	LoadAssetRenderingUnits(officeid);
	
	document.frmAccountUnit.cmbRenderAccUnit.value = 0;
	//document.frmAccountUnit.cmbOffice_code.value="";
	document.frmAccountUnit.radActive[0].checked = true;
	document.frmAccountUnit.radActive[1].checked = false;
	var unitcode=document.getElementById("cmbOffice_code");
	unitcode.length=0;
	document.frmAccountUnit.cmbAcc_UnitCode.value="";
	document.getElementById('trDtTo1').style.display='none';
	document.getElementById('trDtTo2').style.display='none';
	document.frmAccountUnit.txtDtFrm.value="";	
	 document.frmAccountUnit.CmdAdd.disabled = false;
	 document.frmAccountUnit.CmdUpdate.disabled = true;
	 document.frmAccountUnit.CmdDelete.disabled = true;
	 document.frmAccountUnit.CmdClear.disabled = false;
	 document.frmAccountUnit.CmdList.disabled = false;
	 document.frmAccountUnit.CmdClose.disabled = false;
	 alert("all finish");
}


function domainCheckDate()
{
    var txtDtFrm = document.getElementById('txtDtFrm').value;
    var txtDtTo = document.getElementById('txtDtTo').value;
    var flag = true;
    
    if(txtDtFrm == "")
    {
        alert("Enter the 'Date Effect From' field");
        document.getElementById('txtDtTo').value = "";
        flag = false;
    }
    else 
    {
        var redep=txtDtFrm.split('/');
        var rel=txtDtTo.split('/');
        var redepDay=redep[0];
        var redepMon=redep[1];
        var redepYear=redep[2];
        var rday=rel[0];
        var rmon=rel[1];
        var ryear=rel[2];
        if(redepYear>ryear)
        {
        	alert('Date Effect Upto should be greater than or equal to Date Effect From');
        	document.getElementById('txtDtTo').value = "";
        	flag = false;
        }
        else if(redepYear==ryear)
        {
            if(redepMon>rmon)
            {
        		alert('Date Effect Upto should be greater than or equal to Date Effect From');
        		document.getElementById('txtDtTo').value = "";
        		flag = false;
            }
            else if(redepMon==rmon)
            {
                if(redepDay>rday)
                {
        			alert('Date Effect Upto should be greater than or equal to Date Effect From');
        			document.getElementById('txtDtTo').value = "";
        			flag = false;
                }
            }
        }
    }
    return flag;
}
//added sathya 06/07/2012
function LoadaccUnits(acc_unit)
{
		//alert("Loading the units of circle chosen");
		var circle_off_id=acc_unit;
		var cmboffice=document.getElementById("cmbOffice_code");
        cmboffice.innerHTML="";
        cmboffice.length=0;
		
		//alert("circle chosen*********"+circle_off_id);
		url="../../../../../ValueAC_Rendering?Command=LoadDivisions&circle_id="+circle_off_id;
	    var req=getTransport();
	    req.open("POST",url,true); 
	    req.onreadystatechange=function()
	    {
	    	handle_loadDivisionUnits(req);
	    };   
	    
	    req.send(null);
}
function handle_loadDivisionUnits(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            var len = baseResponse.getElementsByTagName("record").length;
            var cmb = document.getElementById("cmbAcc_UnitCode");
            //alert(cmb.length);
            var i;
            for(i=cmb.length;i>0;i--)
            	{
            	//alert(i);
            	cmb.innerHTML = "";
            	cmb.innerText = "";
            	//deleteRows(i);
               	}
            if(flag=="success")
            {
            	for(i=0;i<len;i++)
            	{
            		//var AR_UnitId = baseResponse.getElementsByTagName("ASSET_RENDERING_UNIT_ID")[0].firstChild.nodeValue;
            		//var AR_UnitName = baseResponse.getElementsByTagName("ASSET_RENDERING_UNIT_NAME")[0].firstChild.nodeValue;
            		
            		
                    var option=document.createElement("OPTION");
                    var root = baseResponse.getElementsByTagName("record")[i];
                    var AR_UnitId=root.getElementsByTagName("ASSET_RENDERING_UNIT_ID")[0].firstChild.nodeValue;
                    var AR_UnitName=root.getElementsByTagName("ASSET_RENDERING_UNIT_NAME")[0].firstChild.nodeValue;
                    
                    option.text=AR_UnitName;
                    option.value=AR_UnitId;
                    try
                    {   
                    	cmb.add(option);
                    }
                    catch(errorObject)
                    {
                    	cmb.add(option,null);
                    }   

            	}
            	var unit=baseResponse.getElementsByTagName("ASSET_RENDERING_UNIT_ID")[0].firstChild.nodeValue;
            	var cmboffice=document.getElementById("cmbOffice_code");
                cmboffice.innerHTML="";
                cmboffice.length=0;
            	LoadOffice(unit);
            }
            else
            {
            	alert("Could not load Division Units");
            }
        }
        
    }
}
function LoadOffice(unitID_val)
{
//alert("clall ll");
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../ValueAC_Rendering?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        // alert(url);
        var req=getTransport();
        req.open("POST",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        };
        req.send();
    }
}

function handle_loadOffice(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {
       
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];       
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            cmboffice.length=0;
            var offidvalues=baseresponse.getElementsByTagName("offid");            
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
        }
        else
        {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
        }
            
             
     }
    }
}
