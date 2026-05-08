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


function callServer(command)
{  
		    var ass_code  = document.getElementById("ass_code").value;
		    var ass_desc  = document.getElementById("ass_desc").value;
		    var ass_type  = document.getElementById("ass_type").value;
		    var alios_code  = document.getElementById("alios_code").value;
		    var major_class  = "";
		    if(document.Asset_classifications.major_class[0].checked==true)
		    	major_class="Y";
		    else 
		    	major_class="N";
		    var folio_maintained="";
		    if(document.Asset_classifications.folio_maintained[0].checked==true)
		    	folio_maintained="Y";
		    else
		    	folio_maintained="N";
		    var mc_applicable="";
		    if(document.Asset_classifications.mc_applicable[0].checked==true)
		    	mc_applicable="Y";
		    else
		    	mc_applicable="N";
		    var ass_depreciable="";
		    if(document.Asset_classifications.ass_depreciable[0].checked==true)
		    	ass_depreciable="Y";
		    else
		    	ass_depreciable="N";
		    if(command!="Delete")
		    {
					    var validation=CheckValidate();
					    if(validation==true)
					    {
						    	if(command=="Add")           
							         var url = "../../../../../Assets_Major_Classification_Serv?command=Add&ass_code="+ass_code+"&ass_desc="+ass_desc+"&ass_type="+ass_type+"&alios_code="+alios_code+"&major_class="+major_class+"&folio_maintained="+folio_maintained+"&mc_applicable="+mc_applicable+"&ass_depreciable="+ass_depreciable;    
							    else if(command=="Update")    
							         var url = "../../../../../Assets_Major_Classification_Serv?command=Update&ass_code="+ass_code+"&ass_desc="+ass_desc+"&ass_type="+ass_type+"&alios_code="+alios_code+"&major_class="+major_class+"&folio_maintained="+folio_maintained+"&mc_applicable="+mc_applicable+"&ass_depreciable="+ass_depreciable;
					    }    
		    }
		    else
		    {
				    	if(document.getElementById("ass_code").value==""||document.getElementById("ass_code").value.length==0)
				    			alert("Select Major Asset Class Code");
				    	else
				    			var url = "../../../../../Assets_Major_Classification_Serv?command=Delete&ass_code="+ass_code;
		    }
		    var req=getTransport();
		    req.open("GET",url,true); 
		    req.onreadystatechange=function()
		    {
		       manipulate(req);
		    };   
		    req.send(null);     
}

function ListAll()
{   //alert("inside listall");
	        var winemp;
	        var my_window;
	        var wininterval;
	        if (winemp && winemp.open && !winemp.closed) 
	        {
	           winemp.resizeTo(500,600);
	           winemp.moveTo(200,200); 
	           winemp.focus();
	           return ;
	        }
	        else
	        {
	            winemp=null;
	        }          
	        winemp= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Asset_Classifications.jsp","InvoiceNumberSearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
	        winemp.moveTo(250,250);  
	        winemp.focus();   
}
function doParentEmp(class_code)
{        // alert("parent");
			document.getElementById("ass_code").value=class_code;
		    LoadRecord(class_code);
}

function manipulate(req)
{	
		    if(req.readyState==4)
		    {
			      if(req.status==200)
			      { 
				           var response=req.responseXML.getElementsByTagName("response")[0];
				           var cmd=response.getElementsByTagName("command")[0].firstChild.nodeValue;  
                                          // alert(cmd);
				           var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                                       //    alert(flag);
				           if(flag=="Success")
				           {        
					               
					               	   
							        	   if(cmd=="Add") {
							            	   alert("Inserted Succesfully");}
							               else if(cmd=="Update"){
							            	   alert("Updated Succesfully");}
							               else if(cmd=="Delete"){
							            	   alert("Deleted Succesfully");
							        	   clearAll();}
					             
					             else if(cmd=="Load")   
                                                      {
                                                                           var class_desc=response.getElementsByTagName("class_desc")[0].firstChild.nodeValue;
							                   var asset_type=response.getElementsByTagName("asset_type")[0].firstChild.nodeValue;
							                   var alias_code=response.getElementsByTagName("alias_code")[0].firstChild.nodeValue;
							                   var major_class=response.getElementsByTagName("major_class")[0].firstChild.nodeValue;
							                   var individual_folio=response.getElementsByTagName("individual_folio")[0].firstChild.nodeValue;
							                   var minor_class=response.getElementsByTagName("minor_class")[0].firstChild.nodeValue;
							                   var depreciable=response.getElementsByTagName("depreciable")[0].firstChild.nodeValue;                 
							                   document.getElementById("ass_desc").value=class_desc;
							                   document.getElementById("ass_type").value=asset_type;
							                   document.getElementById("alios_code").value=alias_code;
							                  // alert(major_class);
							                   if(major_class=="Y")
							                	   document.Asset_classifications.major_class[0].checked=true;
							                   else
							                	   document.Asset_classifications.major_class[1].checked=true;
							                   //alert(individual_folio);
							                   if(individual_folio=="Y")
							                	   document.Asset_classifications.folio_maintained[0].checked=true;
							                   else
							                	   document.Asset_classifications.folio_maintained[1].checked=true;
							                   //alert(minor_class);
							                   if(minor_class=="Y")
							                	   document.Asset_classifications.mc_applicable[0].checked=true;
							                   else
							                	   document.Asset_classifications.mc_applicable[1].checked=true;
							                   //alert(depreciable);
							                   if(depreciable=="Y")
							                	   document.Asset_classifications.ass_depreciable[0].checked=true;
							                   else
							                	   document.Asset_classifications.ass_depreciable[1].checked=true;
					               }
				              
				           }   
				           else
				           {
					        	   if(cmd=="Add") 
					            	   alert("Insertion Failure");
					               else if(cmd=="Update")
					            	   alert("Updation Failure");
					               else 
					            	   alert("Deletion Failure");
				           }
			          
			      }
		    }
	
}

function LoadRecord(code)
{           // alert("LoadRecord");

			clearFun();
			document.getElementById("add").disabled=true;
		 	document.getElementById("del").disabled=false;
		 	document.getElementById("update").disabled=false;		 	
			var url = "../../../../../Assets_Major_Classification_Serv?command=Load&ass_code="+code;	
		    var req=getTransport();
		    req.open("GET",url,true); 
		    req.onreadystatechange=function()
		    {
		       manipulate(req);
		    };   
		    req.send(null);     
}

function clearAll()
{
		   document.getElementById("add").disabled=false;
		   document.getElementById("del").disabled=true;
		   document.getElementById("update").disabled=true;
		   document.getElementById("ass_code").value="";
		   clearFun();
}
function clearFun()
{
		   document.getElementById("ass_desc").value="";
		   document.getElementById("ass_type").selectedIndex=0;
		   document.getElementById("alios_code").value="";
		   document.Asset_classifications.major_class[0].checked=true;    
		   document.Asset_classifications.folio_maintained[0].checked=true;
		   document.Asset_classifications.mc_applicable[0].checked=true;
           document.Asset_classifications.ass_depreciable[0].checked=true;   	
}
function CheckValidate()
{
		   if(document.getElementById("ass_desc").value==""||document.getElementById("ass_desc").value.length==0)
		   {
			   alert("Enter Asset Class Description");
			   return false;
		   }
		   else if(document.getElementById("alios_code").value==""||document.getElementById("alios_code").value.length==0)
		   {
			   alert("Enter Alias Code");
			   return false;
		   } 
		   else
			   return true;
			
}

function exit()
{
        self.close();

}