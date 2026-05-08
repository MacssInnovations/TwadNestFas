
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


function unloadChildren(eleName)
{
	var prnt = document.getElementById(eleName);
	var len = prnt.childNodes.length;
	for(var i=len; i>0; i--)
	{
		prnt.removeChild(prnt.lastChild);
	}
}


function nullCheck(inx)
{
	var index = document.getElementById(inx);
	if((index == null)||(index == '')||(index == ' '))
		return false;
	else
		return true;
}



function clearForm()
{
	document.forms[0].reset();
	
	dispAdr('P');
	
	document.getElementById('Add').disabled = false;
	document.getElementById('Update').disabled = true;
	document.getElementById('Delete').disabled = true;
}


function dispAdr(val)
{
	if(val=="L")
	{
		document.getElementsByName("prvlb")[1].checked = true;
		document.getElementById("adrlbl").style.display="inline";
		document.getElementById("adr").style.display="inline";
		
		document.getElementById('Tariff').disabled = false;
		document.getElementById('Interest').disabled = false;
	}
	else
	{
		document.getElementsByName("prvlb")[0].checked = true;
		document.getElementById("adrlbl").style.display="none";
		document.getElementById("adr").style.display="none";
		
		document.getElementById('Tariff').disabled = true;
		document.getElementById('Interest').disabled = true;
	}
}


function callServer(command)
 {
   
       var sno=document.getElementById('sno').value;
       var sdesc=document.getElementById('sdesc').value;
       var desc=document.getElementById('desc').value;
       
       var prvlb;
       if(document.getElementsByName('prvlb')[0].checked)
       {
    	   prvlb=document.getElementsByName('prvlb')[0].value;
       }
       else
       {
    	   prvlb=document.getElementsByName('prvlb')[1].value;
       }
       
       var adr=document.getElementById('adr').value;
       
       var url="";
       if(command=="Add")
        {              
    	   			var flag = ( nullCheck('sno') && nullCheck('desc') && nullCheck('sdesc') && (prvlb=="P" || nullCheck('adr')) );
                    if(flag==true)
                    {
	                    url="../../../../../BeneficiaryType?command=Add&sno="+sno+"&sdesc="+sdesc+"&desc="+desc+"&prvlb="+prvlb+"&adr="+adr;
	                    var req=getTransport();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
                    }
         }
        else if(command=="Update")
        {
        			var flag = ( nullCheck('sno') && nullCheck('sno') && nullCheck('sno') );
                    if(flag==true)
                    {
	                    url="../../../../../BeneficiaryType?command=Update&sno="+sno+"&sdesc="+sdesc+"&desc="+desc+"&prvlb="+prvlb+"&adr="+adr;
	                    var req=getTransport();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
                    }
        }
        else if(command=="Delete")
        {  
        			var flag = nullCheck('sno');
        			if(flag==true)
        			{
	        			url="../../../../../BeneficiaryType?command=Delete&sno="+sno;
	        			var req=getTransport();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }   
	                    req.send(null);
        			}
        }
        else if(command=="Get")
        {               
        			url="../../../../../BeneficiaryType?command=Get";
		            var req=getTransport();
		            req.open("GET",url,true); 
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            }   
		            req.send(null);
        }
}  


//********************************* CallServer Response Coding ***************************************//

		
	function processResponse(req)
	{
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var baseResponse = req.responseXML.getElementsByTagName('response')[0];
                    var cmd = baseResponse.getElementsByTagName('command')[0].firstChild.nodeValue;
                    var flag = baseResponse.getElementsByTagName('flag')[0].firstChild.nodeValue;
                    
                    if(cmd == 'Add')
                    {
                            if(flag=='success')
                            {
                                    alert("Saved Successfully");
                                    callServer('Get');
                                    clearForm();
                                    
                                    var prvlb = baseResponse.getElementsByTagName('prvlb')[0].firstChild.nodeValue;
                                    if(prvlb == 'L')
                                    {
	                                    openWindow("../../../../../org/PMS/PMS1/DCB/jsps/Pms_Dcb_Mst_Tariff.jsp","Tariff");
	                                    openWindow("../../../../../org/PMS/PMS1/DCB/jsps/pms_dcb_mst_int.jsp","Interest");
                                    }
                            }
                            else if(flag=='duplicate')
                            {
                            		alert("This Private Beneficiary has already been saved");
                            }
                            else
                            {
                                    alert("Failed to Save");
                            }
                    }
                    
                    
                    else if(cmd == 'Update')
                    {
                            if(flag=='success')
                            {
                                    alert("Changes Saved Successfully");
                                    callServer('Get');
                                    clearForm();
                            }
                            else if(flag=='duplicate')
                            {
                            		alert("Please check the Name of the Private Beneficiary. It already exists.");
                            }
                            else
                            {
                                    alert("Failed to Save Changes");
                            }
                    }
                    
                    
                    else if(cmd == 'Delete')
                    {
                            if(flag=='success')
                            {
                                    alert("Deleted Successfully");
                                    callServer('Get');
                                    clearForm();
                            }
                            else
                            {
                                    alert("This Private Beneficiary cannot be deleted now. It has been referred by some module.");
                            }
                    }
                    
                    
                    else if(cmd == 'Get')
                    {
	            		
                    	    var tbody = document.getElementById('tblList');
                    	    unloadChildren('tblList');
                    	    
                            var row = baseResponse.getElementsByTagName('row');
                            var len = row.length;

                            
                            for(var i=0; i<len; i++)
                            {
                            		var sno = row[i].getElementsByTagName('sno')[0].firstChild.nodeValue;
                            		var sdesc = row[i].getElementsByTagName('sdesc')[0].firstChild.nodeValue;
                            		var desc = row[i].getElementsByTagName('desc')[0].firstChild.nodeValue;
                            		var adr = row[i].getElementsByTagName('adr')[0].firstChild.nodeValue;
                            		var prvlb = row[i].getElementsByTagName('prvlb')[0].firstChild.nodeValue;
                            		
                            		var tr = document.createElement('tr');
                                    tr.id = sno;
             
                                    var td;
                                    
                                    td = document.createElement('td');
                                    var anc = document.createElement('a');
                                    anc.href = 'javascript:edit(' + sno + ')';
                                    anc.innerHTML = 'Edit';
                                    td.appendChild(anc);
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = sno;
                                    td.style.display='none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = desc;
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = sdesc;
                                    tr.appendChild(td);
                                    
                                    
                                    
                                    
                                    /*
                                    var prvlb = "L";
                                    if((adr=="null")||(adr==null)||(adr==""))
                                    {
                                    	adr = "-";
                                    	prvlb = "P";
                                    }*/
                                    
                                    if(prvlb == "P")
                                    {
                                    	adr = "-";
                                    }
                                    	
                                    	
                                    td = document.createElement('td');
                                    td.innerHTML = adr;
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = prvlb;
                                    td.style.display='none';
                                    tr.appendChild(td);
                                    
                                    tbody.appendChild(tr);
                            }
                    }
                    
                    

                    
                    else if(cmd == 'Type')
                    {
                            if(flag=='success')
                            {
                            		unloadCombo('type');
                            		var cmb = document.getElementById("type");
                                    var row = baseResponse.getElementsByTagName('row');
                                    for(var i=0; i<row.length; i++)
                                    {
                                        var typ = row[i].getElementsByTagName('typ')[0].firstChild.nodeValue;
                                        var type = row[i].getElementsByTagName('type')[0].firstChild.nodeValue;
                                        
                                        var opt = document.createElement('option');
                                        opt.value = typ;
                                        opt.innerHTML = type;
                                        
                                        cmb.appendChild(opt);
                                    }
                            }
                            else
                            {
                                    alert("Failed to load Beneficiary Types");
                            }
                    }
                    
 
                
                }
            }
		
	}


	
	function edit(rid)
	{
		var r = document.getElementById(rid);
		rcells = r.cells;

                                

		document.getElementById('sno').value = rcells.item(1).firstChild.nodeValue;
		document.getElementById('desc').value = rcells.item(2).firstChild.nodeValue;
		document.getElementById('sdesc').value = rcells.item(3).firstChild.nodeValue;
		
		var prvlb = rcells.item(5).firstChild.nodeValue;
		dispAdr(prvlb);
		
		if(prvlb == "L")
		{
			document.getElementById('adr').value = rcells.item(4).firstChild.nodeValue;
		}
		else
		{
			document.getElementById('adr').value = "";
		}
		
			

		
		document.getElementById('Add').disabled = true;
		document.getElementById('Update').disabled = false;
		document.getElementById('Delete').disabled = false;
	}
	
	
	function removeNull(val)
	{
		if((val==null)||(val=="")||(val=='null'))
		{
			return "";
		}
		return val;
	}
	

	function openWindow(url,lbl)
	{
		var winSearchConsumer;
		if (winSearchConsumer && winSearchConsumer.open && !winSearchConsumer.closed) 
	    {
	       winSearchConsumer.resizeTo(500,500);
	       winSearchConsumer.moveTo(250,250); 
	       winSearchConsumer.focus();
	    }
	    else
	    {
	       winSearchConsumer=null
	    }        
	    winSearchConsumer= window.open(url,lbl,"status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
	    winSearchConsumer.moveTo(250,250);  
	    winSearchConsumer.focus();
	}
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

