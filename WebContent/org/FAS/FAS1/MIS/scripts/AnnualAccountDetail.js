var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, response="";
var pagesize = 10;
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
var browserName=navigator.appName;
var brow="";
if (browserName=="Netscape")
{ 	brow="nets";}
else if (browserName=="Microsoft Internet Explorer")
{ 	brow="iex";} 

 function clearAll(){
	document.getElementById('categoryCode').value="";
	document.getElementById('categoryDesc').value="";
	document.getElementById('butSub').disabled=false;
	document.getElementById('butCan').disabled=true;
	document.getElementById('update').disabled=true;
	 
 }
 function Exit(){
    self.close();
 }
function callServer(command) {	 
     var url="";
       if(command=="majorGroup"){
    	   	var flag=true;    	   	
            if(flag==true){            	
            	url="../../../../../mis24report.pdf?command=majorGroup";            	            	            	
                var req=getTransport();
                req.open("POST",url,true);   
                req.onreadystatechange=function(){
                	loadCombo(req);
                 };   
                 req.send(null);
                }
        }else if(command=="minorGroup"){
                    var flag=true;
                    if(flag==true){
                    	var majorGroupCode=document.getElementById('majorGroupCode').value;
                    	url="../../../../../mis24report.pdf?command=minorGroup1&majorCode="+majorGroupCode;                    			
                    	 var req=getTransport();
                    	 req.open("POST",url,true);        
                    	 req.onreadystatechange=function(){
                    		 loadMinorCombo(req);
                    	 };   
                    	 req.send(null);
                   }
        }else if(command=="Delete"){        		
        		var categoryCode=document.getElementById('categoryCode').value;
        		url="../../../../../MISMainCategory?command=deleted&mainCategoryId="+categoryCode+
    			"&mainCategoryDesc="+categoryDesc;
                var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function(){
                    processResponse(req);
                };   
                    req.send(null);
        }        
}  
 function processResponse(req){
	 if(req.readyState==4){ 
         if(req.status==200){  
        	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];        	 
      	   		updateResponse(baseResponse);
         }
     }
 }
var checkflag=false;
 function updateResponse(response){
	 	var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	 	if(res=="success"){
	 		if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="added"){
	 			alert("Records Added Successfully, Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
	 			clearAll();
	 			callServer('Get');
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="Notadded"){
	 			alert("Data already added for the particular Category code & Category name");
	 			clearAll();
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="update"){
	 			alert("Records Updated Successfully for Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
	 			clearAll();
	 			callServer('Get');
	 		}		 		
	 	}
	 	else
	 	{
	 		alert("Process failure");
	 	}
	 }
 
 function loadCombo(req){
	 if(req.readyState==4){ 
         if(req.status==200){
        	response=req.responseXML.getElementsByTagName("response")[0]; 
        	mainCategoryCombo();
         }
     }
 }
 function mainCategoryCombo(){
	 var len1 = response.getElementsByTagName("MAJOR_HEAD_CODE").length;	
		var select=document.getElementById('majorGroupCode');
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){		
			listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("MAJOR_HEAD_DESC")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("MAJOR_HEAD_CODE")[i].firstChild.nodeValue;
			}
		}else{
			select.length=1;
			select.selectedIndex=0;
			//alert("Fail");
		}
 }
 function loadMinorCombo(req){
	 if(req.readyState==4){ 
         if(req.status==200){
        	response=req.responseXML.getElementsByTagName("response")[0]; 
        	minorCategoryCombo();
         }
     }
 }
 function minorCategoryCombo(){
	 var len1 = response.getElementsByTagName("MINOR_HEAD_CODE").length;	
		var select=document.getElementById('minorGroupCode');
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){		
			listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="0";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("MINOR_HEAD_DESC")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("MINOR_HEAD_CODE")[i].firstChild.nodeValue;
			}
		}else{
			select.length=1;
			select.selectedIndex=0;
			//alert("Fail");
		}
 }
