var __pagination=5;
var totalblock=0;
var record1=new Array();var record2=new Array();     
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

function loadRecord()
{		
	   var tbody=document.getElementById("tblList");
	   try{tbody.innerHTML="";
	   }catch(e) {tbody.innerText="";}
		var class_code=document.getElementById("class_code").value;
		var url = "../../../../../Assets_Classification_AC_HeadsServ?command=LoadRecord&class_code="+class_code;	
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    	processResponse(req);
	    }   
	    req.send(null);   
}

function callServer(command)
{
		
		 	var class_code=document.getElementById("class_code").value;
		 	var ac_head_code=document.getElementById("ac_head_code").value;
		 	if(command=="Add")
		 		var url = "../../../../../Assets_Classification_AC_HeadsServ?command=Add&class_code="+class_code+"&ac_head_code="+ac_head_code;	
		 	else if(command=="Update")
		 		var url = "../../../../../Assets_Classification_AC_HeadsServ?command=Update&class_code="+class_code+"&ac_head_code="+ac_head_code;
		 	else
		 		var url = "../../../../../Assets_Classification_AC_HeadsServ?command=Delete&class_code="+class_code+"&ac_head_code="+ac_head_code;
		    var req=getTransport();
		    req.open("GET",url,true); 
		    req.onreadystatechange=function()
		    {
		    	processResponse(req);
		    }   
		    req.send(null);     
}

function processResponse(req)
{   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var response=req.responseXML.getElementsByTagName("response")[0]; 
              var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue; 
              var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;              
                         
              if(flag=="Success")
              {
                                if(cmd=='Add')
                                	alert("Record Inserted Successfully")
                                else if(cmd=='Update')
                                	alert("Record Updated Successfully")
                                else if(cmd=='Delete')
                                	alert("Record Deleted Successfully")
                                	
            	  				var count=response.getElementsByTagName("count"); 
                                document.Asset_classifications.total.value=count.length;     
                                record1=new Array(); record2=new Array(); 
                                for(i=0;i<count.length;i++)
                                {
		                                    record1[i]=count[i].getElementsByTagName("head_code")[0].firstChild.nodeValue;
		                                    record2[i]=count[i].getElementsByTagName("head_desc")[0].firstChild.nodeValue;                                 
                                }                               
                                totalblock=0;
                                if(record1.length>0)
                                {
		                                    totalblock=parseInt(record1.length/__pagination);
		                                    if(record1.length%__pagination!=0)
		                                    {
		                                        	totalblock=totalblock+1;
		                                    }		                                    
		                                    var cmbpage=document.getElementById("cmbpage");
		                                    try
		                                    { 
		                                        	cmbpage.innerHTML="";
		                                    }
		                                    catch(e){
		                                         	cmbpage.innerText="";
		                                    }
		                                    
		                                    for(i=1;i<=totalblock;i++)
		                                    {
			                                        var option=document.createElement("OPTION");
			                                        option.text=i;
			                                        option.value=i;
			                                        try
			                                        {
			                                        cmbpage.add(option);
			                                        }
			                                        catch(errorObject)
			                                        {
			                                        cmbpage.add(option,null);
			                                        }
		                                    }  
		                              
		                                    loadRecordVal(1);
                                }
                                clearAll();
               }
               else if(flag=="NoRecords")
               {
            	  // alert("inside");
            	   document.Asset_classifications.total.value=0;
            	   var tbody=document.getElementById("tblList");
            	   try{tbody.innerHTML="";
            	   }catch(e) {tbody.innerText="";}
            	   var cell=document.getElementById("divcmbpage");
	           	   cell.style.display="none";
	           	   var cell=document.getElementById("divpage");
	           	   cell.style.display="none";
		           var cell=document.getElementById("divnext");
		 	       cell.style.display="none";
		 	       var cell=document.getElementById("divpre");
			       cell.style.display="none";
			       document.getElementById("ac_head_code").value="";
			       document.getElementById("class_code").disabled=false;
			       document.getElementById("ac_head_code").disabled=false;
               }       
               else
               {
            	   if(cmd=='Add')
                   	   alert("Insertion Failure")
                   else if(cmd=='Update')
                   	   alert("Updation Failure")
                   else if(cmd=='Delete')
                   	   alert("Deletion Failure")
                   else
                	   alert("Err in Loading")
               }
            }
        }
}

function changepagesize()
{
		__pagination=document.Asset_classifications.cmbpagination.value;    	
        totalblock=0;
        if(record1.length>0)
        {
            totalblock=parseInt(record1.length/__pagination);
            if(record1.length%__pagination!=0)
            {
                totalblock=totalblock+1;
            }
            var cmbpage=document.getElementById("cmbpage");
            try
            {
                cmbpage.innerHTML="";
            }
            catch(e)
            {
                cmbpage.innerText="";
            }
            for(i=1;i<=totalblock;i++)
            {
                var option=document.createElement("OPTION");
                option.text=i;
                option.value=i;
                try
                {
                    cmbpage.add(option);
                }
                catch(errorObject)
                {
                    cmbpage.add(option,null);
                }
            } 
        }
        loadRecordVal(1);   
}


function loadRecordVal(page)
{
	    var i=0;
	    var c=0;    
	    var p=__pagination*(page-1);
	    var sno=0;
	    var tbody=document.getElementById("tblList");
	    try{tbody.innerHTML="";}
	    catch(e) {tbody.innerText="";}
	    document.Asset_classifications.cmbpage.selectedIndex=page-1;
	    for(i=p;i<record1.length && c<__pagination;i++)
	    {
	            c++;
	            sno++;
	            var mycurrent_row=document.createElement("TR"); 
	            mycurrent_row.id=sno;
	            cell2=document.createElement("TD");
	            var anc=document.createElement("A");
	            var url="javascript:loadValuesFromTable('"+record1[i]+"')";
	            anc.href=url;
	            var txtedit=document.createTextNode("Edit");
	            anc.appendChild(txtedit);
	            cell2.appendChild(anc);
	            mycurrent_row.appendChild(cell2);
	            
	            cell1=document.createElement("TD");
	            var currentText=document.createTextNode(record1[i]);
	            cell1.appendChild(currentText);
	            mycurrent_row.appendChild(cell1);
	            
	            cell1=document.createElement("TD");
	            var currentText=document.createTextNode(record2[i]);
	            cell1.appendChild(currentText);
	            mycurrent_row.appendChild(cell1);
	            
	            tbody.appendChild(mycurrent_row);
	            
	    }
	   
	    
	    var cell=document.getElementById("divcmbpage");
	    cell.style.display="block";
	    var cell=document.getElementById("divpage");
	    cell.style.display="block";
	    try
	    {cell.innerHTML='/'+totalblock;
	    }
	    catch(e){cell.innerText='/'+totalblock;
	    }
	    if(page<totalblock)
	    {
	        var cell=document.getElementById("divnext");
	        cell.style.display="block";
	        try
	        {
	            cell.innerHTML="";
	        }
	        catch(e)
	        {
	            cell.innerText="";
	        }
	        var anc=document.createElement("A");
	        var url="javascript:loadRecordVal("+(page+1)+")";
	        anc.href=url;
	        var txtedit=document.createTextNode("<<Next>>");
	        anc.appendChild(txtedit);
	        cell.appendChild(anc);
	    }
	    else
	    {
	        var cell=document.getElementById("divnext");
	        cell.style.display="block";
	        try{cell.innerHTML="";}
	        catch(e) {cell.innerText="";}
	    }
	    if(page>1)
	    {
	        var cell=document.getElementById("divpre");
	        cell.style.display="block";
	        try{cell.innerHTML="";}
	        catch(e) {cell.innerText="";}
	        var anc=document.createElement("A");
	        var url="javascript:loadRecord("+(page-1)+")";
	        anc.href=url;
	        var txtedit=document.createTextNode("<<Previous>>");
	        anc.appendChild(txtedit);
	        cell.appendChild(anc);
	    }
	    else
	    {
	        var cell=document.getElementById("divpre");
	        cell.style.display="block";
	        try{cell.innerHTML="";}
	        catch(e) {cell.innerText="";}
	    }

}

function changepage()
{
	    var page=document.Asset_classifications.cmbpage.value;
	    loadRecordVal(parseInt(page));
}

function loadValuesFromTable(head_code)
{
		document.getElementById("ac_head_code").value=head_code;
		document.getElementById("ac_head_code").disabled=true;
		document.getElementById("class_code").disabled=true;
		document.getElementById("add").disabled=true;
		document.getElementById("del").disabled=false;
		//document.getElementById("update").disabled=false;
}

function clearAll()
{
		document.getElementById("ac_head_code").value="";
		document.getElementById("add").disabled=false;
		document.getElementById("del").disabled=true;
		document.getElementById("ac_head_code").disabled=false;
		document.getElementById("class_code").disabled=false;
		//document.getElementById("update").disabled=true;
}
