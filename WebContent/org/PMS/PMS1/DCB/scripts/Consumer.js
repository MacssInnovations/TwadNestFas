
var CONS = 'Get';

var DIS=0;
var BLK=0;
var PAN=0;
var PRIV=0;
var ULB=0;

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
		
		
		function clrBen()
		{
			document.getElementById('cname').value="";
		}
		

		
		function Exit()
		{
		   self.close();
		}
		
		
		function nullCheck(idx)
		{
			var ele = document.getElementById(idx);      
			if((ele.value=="")||(ele.value==null))
			{ 
				alert("Please Enter the mandatory fields");
				return false;
			}
			return true;
		}
		  


    	function numonly(txt,dig,event)
    	{
    		var unicode=event.charCode? event.charCode : event.keyCode;
    		
   			if (unicode!=8 && unicode!=9 && unicode!=37 && unicode!=39 && unicode!=46)
	        {
	             if (unicode<48 || unicode>57 || txt.length>=dig) 
	                return false 
	        }
    	}
    	
    	
    	
    	function landline(txt,event)
    	{
    		var unicode=event.charCode? event.charCode : event.keyCode;

   			if (unicode!=8 && unicode!=9 && unicode!=37 && unicode!=39 && unicode!=46 && unicode!=45)
	        {
	             if (unicode<48 || unicode>57 || txt.length>=14) 
	                return false 
	        }
	        else if(unicode==45)
	        {
	        	var idx = txt.indexOf('-');
	        	if((idx >= 0) || (txt == ""))
	        	{
	        		return false
	        	}
	        }
    	}


		function llnoLastCheck(txt)
		{
			var len = txt.length;
			if((txt.substring(len-1) == '-') || (txt.substring(0,1) == '-'))
			{
				setTimeout("document.getElementById('llno').select()",0);
				alert("Enter a Valid Landline no.");
			}
		}
		
  		function emailCheck(txt)
  		{  
       		var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;  
       		if(!emailPattern.test(txt))
       		{
       			setTimeout("document.getElementById('email').select()",0);
       			alert("Enter a Valid e-mail id");
       		}  
     	}  
   
		
		
		function copyContact(chkd)
		{
			if(chkd)
			{
				document.getElementById('adr1off').value=document.getElementById('adr1').value; 
				document.getElementById('adr2off').value=document.getElementById('adr2').value; 
				document.getElementById('adr3off').value=document.getElementById('adr3').value; 
				document.getElementById('pinoff').value=document.getElementById('pin').value; 
				document.getElementById('llnooff').value=document.getElementById('llno').value;
				document.getElementById('celloff').value=document.getElementById('cell').value;
				document.getElementById('emailoff').value=document.getElementById('email').value;
			}
			else
			{
				document.getElementById('adr1off').value=""; 
				document.getElementById('adr2off').value=""; 
				document.getElementById('adr3off').value=""; 
				document.getElementById('pinoff').value=""; 
				document.getElementById('llnooff').value="";
				document.getElementById('celloff').value=""; 
				document.getElementById('emailoff').value=""; 
			}
		}
		
		
   		function callServer(command)
   		 {
   		//cid dis ctype blk TPcat cname group alias adr1off adr2off adr3off pinoff llnooff celloff emailoff adr1 adr2 adr3 pin llno cell email
				
   				var oid = OID;
   			
   				var cid = document.getElementById('cid').value;

				var ctype = document.getElementById('ctype').value;
				var cname = document.getElementById('cname').value;

				var dis = DIS;//document.getElementById('dis').value;
				var blk = BLK;//document.getElementById('blk').value;
				var pan = PAN;//document.getElementById('pan').value;
				var priv = PRIV;//document.getElementById('priv').value;
				var ulb = ULB;//document.getElementById('ulb').value;
				
				var adr1off = document.getElementById('adr1off').value;
				var adr2off = document.getElementById('adr2off').value;
				var adr3off = document.getElementById('adr3off').value;
				var pinoff = document.getElementById('pinoff').value;
				var llnooff = document.getElementById('llnooff').value;
				var celloff = document.getElementById('celloff').value;
				var emailoff = document.getElementById('emailoff').value;

				var adr1 = document.getElementById('adr1').value;
				var adr2 = document.getElementById('adr2').value;
				var adr3 = document.getElementById('adr3').value;
				var pin = document.getElementById('pin').value;
				var llno = document.getElementById('llno').value;
				var cell = document.getElementById('cell').value;
				var email = document.getElementById('email').value;

				var group = "";//document.getElementById('group').value;
				var ccode = "";//document.getElementById('ccode').value;
				
				
				var consumption;
				if(document.getElementById('petty').checked)
				{
					consumption = document.getElementById('petty').value;
				}
				else if(document.getElementById('bulk').checked)
				{
					consumption = document.getElementById('bulk').value;
				}

				
				
   		       var url="";

   		       if(command=="Add")
   		        {              
   		    	   			var flag =  (  nullCheck('ctype')
   		    	   						&& nullCheck('cname')
   		    	   						
   		    	   						&& nullCheck('adr1')
   		    	   						&& nullCheck('adr2')  );
   		    	   			
   		                    if(flag==true)
   		                    { // cid dis blk pan priv ctype cname adr1off adr2off adr3off pinoff llnooff celloff emailoff adr1 adr2 adr3 pin llno cell email consumption group ccode
	   		                    url="../../../../../Consumer?command=Add&cid=" + cid+"&dis="+dis+"&blk="+blk+"&pan="+pan+"&priv="+priv+"&ulb="+ulb+"&ctype="+ctype+"&cname="+cname+"&adr1off="+adr1off+"&adr2off="+adr2off+"&adr3off="+adr3off+"&pinoff="+pinoff+"&llnooff="+llnooff+"&celloff="+celloff+"&emailoff="+emailoff+"&adr1="+adr1+"&adr2="+adr2+"&adr3="+adr3+"&pin="+pin+"&llno="+llno+"&cell="+cell+"&email="+email+"&group="+group+"&ccode="+ccode+"&consumption="+consumption+"&oid="+oid;
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
		    	   			var flag =  (  nullCheck('ctype')
		    	   						&& nullCheck('cname')
		    	   						
		    	   						&& nullCheck('adr1')
		    	   						&& nullCheck('adr2')  );
		    	   			
		                    if(flag==true)
   		                    {
   		                    	url="../../../../../Consumer?command=Update&cid=" + cid+"&dis="+dis+"&blk="+blk+"&pan="+pan+"&priv="+priv+"&ulb="+ulb+"&ctype="+ctype+"&cname="+cname+"&adr1off="+adr1off+"&adr2off="+adr2off+"&adr3off="+adr3off+"&pinoff="+pinoff+"&llnooff="+llnooff+"&celloff="+celloff+"&emailoff="+emailoff+"&adr1="+adr1+"&adr2="+adr2+"&adr3="+adr3+"&pin="+pin+"&llno="+llno+"&cell="+cell+"&email="+email+"&group="+group+"&ccode="+ccode+"&consumption="+consumption+"&oid="+oid;
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
   		        			url="../../../../../Consumer?command=Delete&cid=" + cid;
   		        			var req=getTransport();
   		                    req.open("GET",url,true);        
   		                    req.onreadystatechange=function()
   		                    {
   		                       processResponse(req);
   		                    }   
   		                    req.send(null);
   		        }
   		        else if(command=="Get")
   		        {               
						 	document.getElementById('divcmbpage').style.display = "inline";
						 	document.getElementById('divpage').style.display = "inline";
		
						 	var page = document.getElementById('cmbpage').value;
						 	var limit = document.getElementById('cmbpagination').value;
		             		
   		        			url="../../../../../Consumer?command=Get&oid="+OID+"&page="+page+"&limit="+limit+"&ctype="+ctype;
   				            var req=getTransport();
   				            req.open("GET",url,true); 
   				            req.onreadystatechange=function()
   				            {
   				               processResponse(req);
   				            }   
   				            req.send(null);
   		        }
   		        else if(command=="Group")
   		        {          
   		        			url="../../../../../Consumer?command=Group";
   				            var req=getTransport();
   				            req.open("GET",url,true); 
   				            req.onreadystatechange=function()
   				            {
   				               processResponse(req);
   				            }   
   				            req.send(null);
   		        }
   		        else if(command=="Type")
   		        {          
   		        			url="../../../../../Consumer?command=Type";
   				            var req=getTransport();
   				            req.open("GET",url,true); 
   				            req.onreadystatechange=function()
   				            {
   				               processResponse(req);
   				            }   
   				            req.send(null);
   		        }

   		}


   		
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
                                            refresh();
                                    }
                                    else if(flag=='duplicate')
                                    {
                                    		alert("This Beneficiary has already been saved");
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
                                            refresh();
                                    }
                                    else if(flag=='duplicate')
                                    {
                                    		alert("Please check the Beneficiary. This Beneficiary already exists.");
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
                                            refresh();
                                    }
                                    else
                                    {
                                            alert("This Beneficiary cannot be deleted now. It has been referred by some module.");
                                    }
                            }
                            
                            
                            else if(cmd == 'Get')
                            {
        	            		
                            	    var tbody = document.getElementById('tbody');
                                    
                                    var row = baseResponse.getElementsByTagName('row');
                                    var len = row.length;

                                    paginate(baseResponse);
                                    
                                    for(var i=0; i<len; i++)
                                    {
                                    		var cid = row[i].getElementsByTagName('cid')[0].firstChild.nodeValue;
                                    		var ctype = row[i].getElementsByTagName('ctype')[0].firstChild.nodeValue;
                                    		var ctypedesc = row[i].getElementsByTagName('ctypedesc')[0].firstChild.nodeValue;
                                    		var cname = row[i].getElementsByTagName('cname')[0].firstChild.nodeValue;
                                    		
                                            
                                    		var dis = row[i].getElementsByTagName('dis')[0].firstChild.nodeValue;
        	                            	var blk = row[i].getElementsByTagName('blk')[0].firstChild.nodeValue;
                                    		var pan = row[i].getElementsByTagName('pan')[0].firstChild.nodeValue;
                                    		var priv = row[i].getElementsByTagName('priv')[0].firstChild.nodeValue;
                                    		var ulb = row[i].getElementsByTagName('ulb')[0].firstChild.nodeValue;


                                    		var adr1off = row[i].getElementsByTagName('adr1off')[0].firstChild.nodeValue;
                                    		var adr2off = row[i].getElementsByTagName('adr2off')[0].firstChild.nodeValue;
                                    		var adr3off = row[i].getElementsByTagName('adr3off')[0].firstChild.nodeValue;
                                    		var pinoff = row[i].getElementsByTagName('pinoff')[0].firstChild.nodeValue;
                                    		var llnooff = row[i].getElementsByTagName('llnooff')[0].firstChild.nodeValue;
                                    		var celloff = row[i].getElementsByTagName('celloff')[0].firstChild.nodeValue;
                                    		var emailoff = row[i].getElementsByTagName('emailoff')[0].firstChild.nodeValue;

                                    		var adr1 = row[i].getElementsByTagName('adr1')[0].firstChild.nodeValue;
                                    		var adr2 = row[i].getElementsByTagName('adr2')[0].firstChild.nodeValue;
                                    		var adr3 = row[i].getElementsByTagName('adr3')[0].firstChild.nodeValue;
                                    		var pin = row[i].getElementsByTagName('pin')[0].firstChild.nodeValue;
                                    		
                                    		var llno = row[i].getElementsByTagName('llno')[0].firstChild.nodeValue;
                                    		var cell = row[i].getElementsByTagName('cell')[0].firstChild.nodeValue;
                                    		var email = row[i].getElementsByTagName('email')[0].firstChild.nodeValue;
                                    		
                                    	
                                    		var consumption = row[i].getElementsByTagName('consumption')[0].firstChild.nodeValue;
                                    	/*
                                    		var group = row[i].getElementsByTagName('group')[0].firstChild.nodeValue;
                                    		var ccode = row[i].getElementsByTagName('ccode')[0].firstChild.nodeValue;
                                    	*/	
                                            var tr = document.createElement('tr');
                                            tr.id = cid;
                     
                                            var td;
                                            
                                            td = document.createElement('td');
                                            var anc = document.createElement('a');
                                            anc.href = 'javascript:edit(' + cid + ')';
                                            anc.innerHTML = 'Edit';
                                            td.rowspan = 4;
                                            td.appendChild(anc);
                                            tr.appendChild(td);
                                    
                                            td = document.createElement('td');
                                            td.innerHTML = cid;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = ctypedesc;
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = ctype;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = cname;
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = consumption;
                                            td.style.display='none';
                                            td.rowspan = 4;
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = adr1off;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = adr2off;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = adr3off;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = pinoff;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = llnooff;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = celloff;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = emailoff;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = adr1;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = adr2;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = adr3;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = pin;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = llno;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = cell;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = email;
                                            td.style.display='none';
                                            tr.appendChild(td);

                                            
                                            
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = dis;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = blk;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = pan;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = priv;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = ulb;
                                            td.style.display='none';
                                            tr.appendChild(td);
                                            


                                            var address3="-";
                                            var pincode="-";
                                            
                                            if((adr3!=null)&&(adr3!=""))
                                            {
                                            	address3=adr3;
                                            }

                                            if((pin!=null)&&(pin!=""))
                                            {
                                            	pincode=pin;
                                            }
                                            
                                            td = document.createElement('td');
                                            td.innerHTML = adr1 + '<br>' + adr2 + '<br>' + address3 + '-' + pincode;
                                            tr.appendChild(td);


                                         // cid dis blk pan priv ctype cname consumption adr1off adr2off adr3off 
                                         // pinoff llnooff celloff emailoff adr1 adr2 adr3 
                                         // pin llno cell email     group ccode                                            
                                            
                                            tbody.appendChild(tr);
                                    }
                            }
                            
                            
                            
  /*                          
                            else if(cmd == 'Group')
                            {
                                    if(flag=='success')
                                    {
                                    		var cmb = document.getElementById("group");
                                            var row = baseResponse.getElementsByTagName('row');
                                            for(var i=0; i<row.length; i++)
                                            {
	                                            var grp = row[i].getElementsByTagName('grp')[0].firstChild.nodeValue;
	                                            var group = row[i].getElementsByTagName('group')[0].firstChild.nodeValue;
	                                            
	                                            var opt = document.createElement('option');
	                                            opt.value = grp;
	                                            opt.innerHTML = group;
	                                            
	                                            cmb.appendChild(opt);
                                            }
                                    }
                                    else
                                    {
                                            alert("Failed to load Beneficiary Groups");
                                    }
                            }
*/
                            else if(cmd == 'Type')
                            {
                                    if(flag=='success')
                                    {
                                    		var cmb = document.getElementById("ctype");
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
                            
  
                            
/*                            
                            else if(cmd == 'District')
                            {
                                    if(flag=='success')
                                    {
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
                                            alert("Failed to load Districts");
                                    }
                            }
                            
                            else if(cmd == 'Block')
                            {
                                    if(flag=='success')
                                    {
                                    		var cmb = document.getElementById("blk");
                                            var row = baseResponse.getElementsByTagName('row');
                                            for(var i=0; i<row.length; i++)
                                            {
	                                            var blk = row[i].getElementsByTagName('blk')[0].firstChild.nodeValue;
	                                            var block = row[i].getElementsByTagName('block')[0].firstChild.nodeValue;
	                                            
	                                            var opt = document.createElement('option');
	                                            opt.value = blk;
	                                            opt.innerHTML = block;
	                                            
	                                            cmb.appendChild(opt);
                                            }
                                    }
                                    else
                                    {
                                            alert("Failed to load Blocks");
                                    }
                            }
                            
                            else if(cmd == 'Panch')
                            {
                                    if(flag=='success')
                                    {
                                    		var cmb = document.getElementById("pan");
                                            var row = baseResponse.getElementsByTagName('row');
                                            for(var i=0; i<row.length; i++)
                                            {
	                                            var pan = row[i].getElementsByTagName('pan')[0].firstChild.nodeValue;
	                                            var panch = row[i].getElementsByTagName('panch')[0].firstChild.nodeValue;
	                                            
	                                            var opt = document.createElement('option');
	                                            opt.value = pan;
	                                            opt.innerHTML = panch;
	                                            
	                                            cmb.appendChild(opt);
                                            }
                                    }
                                    else
                                    {
                                            alert("Failed to load Panchayats");
                                    }
                            }
*/                            
                            
                            

                        
                        }
                    }
   			
   		}

		
   		
   		function edit(rid)
   		{
   			var r = document.getElementById(rid);
   			rcells = r.cells;

            // cid dis blk pan priv ctype cname consumption adr1off adr2off adr3off 
            // pinoff llnooff celloff emailoff adr1 adr2 adr3 
            // pin llno cell email     group ccode                                            

   			document.getElementById('cid').value = rcells.item(1).firstChild.nodeValue;
   			document.getElementById('ctype').value = rcells.item(3).firstChild.nodeValue;
   			document.getElementById('cname').value = rcells.item(4).firstChild.nodeValue;
   			
   			//callServer('Get');
   			
   			var consmp = rcells.item(5).firstChild.nodeValue;
   			document.forms[0].consumption[consmp].checked = true;
   			
  			
   			document.getElementById('adr1off').value = removeNull(rcells.item(6).firstChild.nodeValue);
   			document.getElementById('adr2off').value = removeNull(rcells.item(7).firstChild.nodeValue);	
   			document.getElementById('adr3off').value = removeNull(rcells.item(8).firstChild.nodeValue);
   			document.getElementById('pinoff').value = removeNull(rcells.item(9).firstChild.nodeValue);
   			document.getElementById('llnooff').value = removeNull(rcells.item(10).firstChild.nodeValue);
   			document.getElementById('celloff').value = removeNull(rcells.item(11).firstChild.nodeValue);
   			document.getElementById('emailoff').value = removeNull(rcells.item(12).firstChild.nodeValue);
   			document.getElementById('adr1').value = removeNull(rcells.item(13).firstChild.nodeValue);
   			document.getElementById('adr2').value = removeNull(rcells.item(14).firstChild.nodeValue);
   			document.getElementById('adr3').value = removeNull(rcells.item(15).firstChild.nodeValue);
   			document.getElementById('pin').value = removeNull(rcells.item(16).firstChild.nodeValue);
   			document.getElementById('llno').value = removeNull(rcells.item(17).firstChild.nodeValue);
   			document.getElementById('cell').value = removeNull(rcells.item(18).firstChild.nodeValue);
   			document.getElementById('email').value = removeNull(rcells.item(19).firstChild.nodeValue);
   			
   			DIS = removeNull(rcells.item(20).firstChild.nodeValue);
   			BLK = removeNull(rcells.item(21).firstChild.nodeValue);
   			PAN = removeNull(rcells.item(22).firstChild.nodeValue);
   			PRIV = removeNull(rcells.item(23).firstChild.nodeValue);
   			ULB = removeNull(rcells.item(24).firstChild.nodeValue);
   		/*	
   			document.getElementById('group').value = rcells.item(12).firstChild.nodeValue;
   			document.getElementById('ccode').value = rcells.item(12).firstChild.nodeValue;
   		*/
   			
   			document.getElementById('Add').style.display = "none";
   			document.getElementById('Update').style.display = "inline";
   			document.getElementById('Delete').style.display = "inline";
   		}
   		
   		
   		function removeNull(val)
   		{
   			if((val==null)||(val=="")||(val=='null'))
   			{
   				return "";
   			}
   			return val;
   		}
   		

   		
		function temp()
		{
					url="../../../../../Consumer?command=ABC";
	            	var req=getTransport();
		            req.open("GET",url,true); 
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            }   
		            req.send(null);
		}
		


		
		
		function searchConsumer()
		{
			var bentyp = document.getElementById('ctype');
			
			var ctype = bentyp.value;
			var ctypedesc = bentyp.options[ctype-1].text;
			if(ctype!="")
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
			    winSearchConsumer= window.open("../../../../../org/PMS/PMS1/DCB/jsps/Consumer_Search.jsp?ctype="+ctype+"&ctypedesc='"+ctypedesc+"'","searchConsumer","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
			    winSearchConsumer.moveTo(250,250);  
			    winSearchConsumer.focus();
			}
			else
			{
				alert("Please select a Beneficiary Type");
			}
		}


		function doParent(cname,dis1,blk1,pan1,priv1,ulb1)
		{
			document.getElementById('cname').value = cname;
			DIS = new Number(dis1);
			BLK = new Number(blk1);
			PAN = new Number(pan1);
			PRIV = new Number(priv1);
			ULB = new Number(ulb1);
	
		}

		
		
		function report()
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
		    winSearchConsumer= window.open("../../../../../org/PMS/PMS1/DCB/jsps/Ben_Report.jsp","BenReport","status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
		    winSearchConsumer.moveTo(250,250);  
		    winSearchConsumer.focus();
		}
		
		
		function refresh()
		{
			window.location.reload();
			clearAll();
            document.getElementById('ctype').Value="";
            callServer('Get');
		}

		function clearAll()
		{
			document.forms[0].reset();
			document.getElementById('Add').disabled = false;
			document.getElementById('Update').disabled = true;
			document.getElementById('Delete').disabled = true;
		}