var jobflag;
var officeid;
var seq=1;
var cur;
var winListAllBudget;


function Load_circles()
{
	url="../../../../../Asset_Rendering?Command=LoadCicles";
    var req=getTransport();
    req.open("POST",url,true); 
    req.onreadystatechange=function()
    {
    	LoadCirclesResponse(req);
    };   
    
    req.send(null);

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
       winListAllBudget=null
    }        
    winListAllBudget= window.open("../../../../../org/FAS/FAS1/Masters/jsps/Asset_Rendering_ListAll.jsp","ListAllAccounting","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
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
	
	if((Command != "Delete") && (Command != "LoadRenFor"))
	{
		var flag = domainCheckDate();
		if(flag == false)
		{
			return;
		}
	}
    if(Command=="Add") {
        var accUnitId=document.frmAccountUnit.cmbAcc_UnitCode.value;
        var accOfficeId=document.frmAccountUnit.cmbOffice_code.value;
       // var accUnitRenderedFor=document.frmAccountUnit.accUnitRenderedFor.value;
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
        var flag=(nullcheck(accUnitId) && nullcheck(accOfficeId) && nullcheck(dtFrm) && nullcheck(active));
        if(flag==true)
        {
	        url="../../../../../Asset_Rendering?Command=Add&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+"&dtFrm="+dtFrm+"&active="+active+"&dtTo="+dtTo;
	        var req=getTransport();
	        //alert(url);
	        req.open("POST",url,true); 
	        req.onreadystatechange=function()
	        {
	        	AddRecordResponse(req);
	        };   
	        
	        req.send(null);
        }
        else
        {
        	alert("Enter all the fields");
        }
    }
    
    if(Command=="Update")
    {
	    
	    var accUnitId=document.frmAccountUnit.cmbAcc_UnitCode.value;
	    var accOfficeId=document.frmAccountUnit.cmbOffice_code.value;
	    //var accUnitRenderedFor=document.frmAccountUnit.accUnitRenderedFor.value;
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
	    var flag=(nullcheck(accUnitId) && nullcheck(accOfficeId) && nullcheck(dtFrm) && nullcheck(active));
	    if(flag==true)
	    {
	        url="../../../../../Asset_Rendering?Command=Update&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+"&dtFrm="+dtFrm+"&active="+active+"&dtTo="+dtTo;
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
	    var accUnitId=document.frmAccountUnit.cmbAcc_UnitCode.value;
	    var accOfficeId=document.frmAccountUnit.cmbOffice_code.value;
	    var flag=nullcheck(accUnitId);
	    if(flag==true)
	    {
	        url="../../../../../Asset_Rendering?Command=Delete&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId;
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
	    	alert("Please select a Rendering unit to delete");
	    }
    }
    
    if(Command=="LoadRenFor")
    {
	    var accUnitId=document.frmAccountUnit.cmbAcc_UnitCode.value;
	    var accOfficeId=document.frmAccountUnit.cmbOffice_code.value;
        url="../../../../../Asset_Rendering?Command=LoadRenFor";
        var req=getTransport();
        req.open("POST",url,true); 
        req.onreadystatechange=function()
        {
        	LoadRenForRecordResponse(req);
        };   
        req.send(null);
    }
}
function call_clear()
{
               document.frmAccountUnit.cmbAcc_UnitCode.value="";
               document.frmAccountUnit.cmbOffice_code.value="";
               document.frmAccountUnit.txtDtFrm.value="";
               document.frmAccountUnit.trDtTo1.value="";
               document.frmAccountUnit.txtDtTo.value="";
              // document.frmAccountUnit.accUnitRenderedFor.value="";        
}
function LoadCirclesResponse(req)
{
    
	if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
            	var len=baseResponse.getElementsByTagName("rec").length;
                var tbody = document.getElementById('cmbAcc_UnitCode');
                var off_id;
                var off_name;
                var opt;

                for(var i=0; i<len; i++)
            	{
                	off_id = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
                	off_name = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_NAME")[i].firstChild.nodeValue;
            		
            		opt = document.createElement("option");
            		opt.value = off_id;
            		opt.innerHTML = off_name;
            		
            		tbody.appendChild(opt);
            	}
                common_LoadOffice();
                
            }
            
            else if(flag=="failure")
            {
            	alert("Could not Load circles");
            }
            
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
            
            else if(flag=="failure")
            {
            	alert("Could not add the record");
            }
            if(flag=="AlreadyExist")
            {
                alert("Record Already Exist");
                clearAll();
                
                
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
                clearAll();;
            }
            else
            {
            	alert("Could not delete the record");
            }
        }
    }
}


function LoadRenForRecordResponse(req)
{
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
        	var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
                var len=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID").length;
                var tbody = document.getElementById('accUnitRenderedFor');
                var acUnitId;
                var acUnitName;
                var opt;

                for(var i=0; i<len; i++)
            	{
            		acUnitId = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[i].firstChild.nodeValue;
            		acUnitName = baseResponse.getElementsByTagName("ACCOUNTING_UNIT_NAME")[i].firstChild.nodeValue;
            		
            		opt = document.createElement("option");
            		opt.value = acUnitId;
            		opt.innerHTML = acUnitName;
            		
            		tbody.appendChild(opt);
            	}
            }
            else
            {
            	alert("Failed to load A/c Units in the 'Rendered For' combo");
            }
        }
        
    }
}


function goBack(unitId,frmDt,actv,toDt)
{
	document.frmAccountUnit.cmbAcc_UnitCode.value = unitId;
	LoadOffice(document.frmAccountUnit.cmbAcc_UnitCode.value);
	//document.frmAccountUnit.accUnitRenderedFor.value = renForId;
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

function clearAll()
{
	goBack('','','N','');
	refreshButtons();
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
