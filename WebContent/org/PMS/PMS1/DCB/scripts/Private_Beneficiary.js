


function callServer(command)
 {
   
       var sno=document.getElementById('sno').value;
       var type=document.getElementById('type').value;
       var desc=document.getElementById('desc').value;
       var dis=document.getElementById('dis').value;
       var adr=document.getElementById('adr').value;
       
       var url="";
       if(command=="Type")
       {               
		            url="../../../../../Private_Beneficiary?command=Type"; // &dis=" + dis + 
		            var req=createObject();
		            req.open("GET",url,true); 
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            }
		            req.send(null);
       }
       else if(command=="District")
       {               
		            url="../../../../../Private_Beneficiary?command=District";
		            var req=createObject();
		            req.open("GET",url,true); 
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            }
		            req.send(null);
       }
       else if(command=="Add")
        {              
    	   			var flag = ( nullCheck('type') && nullCheck('desc') && nullCheck('dis') && nullCheck('adr') );
                    if(flag==true)
                    {
	                    url="../../../../../Private_Beneficiary?command=Add&sno="+sno+"&type="+type+"&desc="+desc+"&dis="+dis+"&adr="+adr;
	                    var req=createObject();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }   
	                    req.send(null);
                    }
                    else
                    {
                    	alert('Please give all values');
                    }
         }
        else if(command=="Update")
        {
        			var flag = ( nullCheck('sno') && nullCheck('type') && nullCheck('desc') && nullCheck('dis') && nullCheck('adr') );
                    if(flag==true)
                    {
	                    url="../../../../../Private_Beneficiary?command=Update&sno="+sno+"&type="+type+"&desc="+desc+"&dis="+dis+"&adr="+adr;
	                    var req=createObject();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
                    }
                    else
                    {
                    	alert('Please give all values');
                    }
        }
        else if(command=="Delete")
        {  
        			var flag = nullCheck('sno');
        			if(flag==true)
        			{
	        			url="../../../../../Private_Beneficiary?command=Delete&sno="+sno;
	        			var req=createObject();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
        			}
                    else
                    {
                    	alert('Please select a Private Beneficiary');
                    }
        }
        else if(command=="Get")
        {               
        			url="../../../../../Private_Beneficiary?command=Get&type="+type;
		            var req=createObject();
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
                                    clearAll();
                                    callServer('Get');
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
                                    clearAll();
                                    callServer('Get');
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
                                    clearAll();
                                    callServer('Get');
                            }
                            else if(flag=='reference')
                            {
                            		alert("Private Beneficiary Cannot be deleted. Refered by Beneficiary Master.");
                            }
                            else
                            {
                                    alert("Private Beneficiary Cannot be deleted. Refered by some module.");
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
                            		var typ = row[i].getElementsByTagName('typ')[0].firstChild.nodeValue;
                            		var type = row[i].getElementsByTagName('type')[0].firstChild.nodeValue;
                            		var desc = row[i].getElementsByTagName('desc')[0].firstChild.nodeValue;
                            		var dis = row[i].getElementsByTagName('dis')[0].firstChild.nodeValue;
                            		var district = row[i].getElementsByTagName('district')[0].firstChild.nodeValue;
                            		var adr = row[i].getElementsByTagName('adr')[0].firstChild.nodeValue;
                            		
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
                                    td.innerHTML = typ;
                                    td.style.display='none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = type;
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = dis;
                                    td.style.display='none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = district;
                                    tr.appendChild(td);

                                    td = document.createElement('td');
                                    td.innerHTML = adr;
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
                    
                    else if(cmd == 'District')
                    {
                            if(flag=='success')
                            {
                            		unloadCombo('dis');
                            		var cmb = document.getElementById("dis");
                                    var row = baseResponse.getElementsByTagName('row');
                                    for(var i=0; i<row.length; i++)
                                    {
                                        var dis = row[i].getElementsByTagName('dis')[0].firstChild.nodeValue;
                                        var district = row[i].getElementsByTagName('district')[0].firstChild.nodeValue;
                                        
                                        var opt = document.createElement('option');
                                        opt.value = dis;
                                        opt.innerHTML = district;
                                        
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
		document.getElementById('type').value = rcells.item(3).firstChild.nodeValue;
		document.getElementById('dis').value = rcells.item(5).firstChild.nodeValue;
		document.getElementById('adr').value = rcells.item(7).firstChild.nodeValue;

		document.getElementById('type').disabled = true;
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
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

