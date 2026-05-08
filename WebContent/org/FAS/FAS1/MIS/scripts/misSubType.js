var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, response="";
var gridrepsonse="";
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
function changepagesize() {	
	pagesize = document.getElementById("cmbpagination").value;
	var len = gridrepsonse.getElementsByTagName("SUB_TYPE_CODE").length;	
	var cmbpage = document.getElementById("cmbpage");
	try {	
		cmbpage.innerHTML = "";
	} catch (e) {
		cmbpage.innerText = "";
	}	
	var i = 1;
	for (i = 1; i <= ((len / pagesize) + 1); i++) {
		var option = document.createElement("OPTION");
		option.text = i;
		option.value = i;
		try {
			cmbpage.add(option);
		} catch (errorObject) {
			cmbpage.add(option, null);
		}
	}
	changepage();
	
}
function changepage() {
	var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var len = gridrepsonse.getElementsByTagName("SUB_TYPE_CODE").length;	
	if(gridrepsonse.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var majorCode = gridrepsonse.getElementsByTagName("MAJOR_TYPE_CODE")[i].firstChild.nodeValue;
		var majorDesc = gridrepsonse.getElementsByTagName("MAJOR_TYPE_NAME")[i].firstChild.nodeValue;
		var mainCode=gridrepsonse.getElementsByTagName("MAIN_TYPE_CODE")[i].firstChild.nodeValue;
		var mainDesc=gridrepsonse.getElementsByTagName("MAIN_TYPE_DESC")[i].firstChild.nodeValue;
		var subType=gridrepsonse.getElementsByTagName("SUB_TYPE_CODE")[i].firstChild.nodeValue;
		var subTypeDesc=gridrepsonse.getElementsByTagName("SUB_TYPE_NAME")[i].firstChild.nodeValue;
		var view=gridrepsonse.getElementsByTagName("STATUSTYPE")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");
		var anc = document.createElement("A");
		if (view == "C") {
			//var tid = document.createTextNode("Cancel");			
			var priceSpan = document.createElement("span");
			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
			priceSpan.appendChild(document.createTextNode("Cancel"));			
			td.appendChild(priceSpan);
			tr.appendChild(td);
		}else{
			var url = "javascript:viewDetails('" + seq + "')";			
			anc.href = url;
			var edit = document.createTextNode("Edit");
			anc.appendChild(edit);
			td.appendChild(anc);
			var sch_id=document.createElement("TEXT");
        	sch_id.type="hidden";
        	sch_id.name="name"+seq;
        	sch_id.id="id"+seq;
        	sch_id.value="&majorCode="+majorCode+"&mainTypeCode="+mainCode+"&subType="+subType;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}		
		/*var td4 = document.createElement("TD");
		var tides = document.createTextNode(majorCode);
		td4.appendChild(tides);
		tr.appendChild(td4);*/
		var td1 = document.createElement("TD");
		var tid = document.createTextNode(majorDesc);
		td1.appendChild(tid);
		tr.appendChild(td1);
		var td2 = document.createElement("TD");
		var tides = document.createTextNode(mainCode);
		td2.appendChild(tides);
		tr.appendChild(td2);
		var td3 = document.createElement("TD");
		var ties = document.createTextNode(mainDesc);
		td3.appendChild(ties);
		tr.appendChild(td3);
		var td6 = document.createElement("TD");
		var ti6 = document.createTextNode(subType);
		td6.appendChild(ti6);
		tr.appendChild(td6);
		var td7 = document.createElement("TD");
		var ti7 = document.createTextNode(subTypeDesc);
		td7.appendChild(ti7);
		tr.appendChild(td7);
		var td5 = document.createElement("TD");
		if(view=="C"){
			var tdst = document.createTextNode("CANCEL");
		}else{
			var tdst = document.createTextNode("LIVE");
		}
		td5.appendChild(tdst);
		tr.appendChild(td5);
		if(brow=="iex"){
			var vartab = tlist.insertRow(-1);			
			vartab.appendChild(td);
			//vartab.appendChild(td4);
			vartab.appendChild(td1);
			vartab.appendChild(td2);
			vartab.appendChild(td3);
			vartab.appendChild(td6);
			vartab.appendChild(td7);
			vartab.appendChild(td5);
		}else
		{
			tlist.appendChild(tr);
		}		
		seq++;
	}
	}catch(err){		
	}
	}
	else{
		 var iframe=document.getElementById("tblList");
         iframe.focus();
		 if(navigator.appName.indexOf('Microsoft')!=-1){
             iframe.innerHTML="<tr><td align=center colspan=7>There is No Data to Display</td></tr>";             
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=7>There is No Data to Display</td></tr>";			 
		 }
             
	}				
	
}
 function clearAll(){
	document.getElementById('categoryCode').selectedIndex=0;
	document.getElementById('mainTypeCode').value="";
	document.getElementById('butSub').disabled=false;
	document.getElementById('butCan').disabled=true;
	document.getElementById('update').disabled=true;
	 
 }
 function Exit(){
    self.close();
 }
 
function callServer(command) {	 
	 var categoryDesc=document.getElementById('categoryCode').value;
	 var mainTypeCode=document.getElementById('mainTypeCode').value;
	 var subTypeDesc=document.getElementById('subTypeDesc').value;
     var url="";
       if(command=="Add"){
    	   	var flag=nullCheck();    	   	
            if(flag==true){            	
            	url="../../../../../MISSubType?command=add&mainCategoryId="+categoryDesc+
            	"&mainTypeCode="+mainTypeCode+"&subTypeDesc="+subTypeDesc;
                var req=getTransport();
                req.open("POST",url,true);   
                req.onreadystatechange=function(){
                       processResponse(req);
                 };   
                 req.send(null);
                }
        }else if(command=="Update"){
                    var flag=nullCheck();
                    if(flag==true){
                    	var subTypeCode=document.getElementById('subTypeCode').value;
                    	url="../../../../../MISSubType?command=update&mainCategoryId="+categoryDesc+
                    	"&mainTypeCode="+mainTypeCode+"&subTypeDesc="+subTypeDesc+"&subTypeCode="+subTypeCode;
                    	 var req=getTransport();
                    	 req.open("POST",url,true);        
                    	 req.onreadystatechange=function(){
                    		 processResponse(req);
                    	 };   
                    	 req.send(null);
                   }
        }else if(command=="Delete"){        		
	        	var subTypeCode=document.getElementById('subTypeCode').value;
	        	url="../../../../../MISSubType?command=deleted&mainCategoryId="+categoryDesc+
            	"&mainTypeCode="+mainTypeCode+"&subTypeDesc="+subTypeDesc+"&subTypeCode="+subTypeCode;
                var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function(){
                    processResponse(req);
                };   
                    req.send(null);
        }else if(command=="Get"){        	
        		url="../../../../../MISSubType?command=getGrid&cateCode="+categoryDesc+"&mainTypeCode="+mainTypeCode;
                var req=getTransport();
                req.open("POST",url,true);                
                req.onreadystatechange=function(){
                	viewResponse(req);
                };
                req.send(null);        	            
        }else if(command=="mainCat"){      	
     		url="../../../../../MISSubCategory?command=maincombo";
            var req=getTransport();
            req.open("POST",url,true);                
            req.onreadystatechange=function(){
            	loadCombo(req);
            };
            req.send(null);        	            
   	  }else if(command=="mainType"){
    	  if(categoryDesc=="select"){
	    		alert("Please select the Main Category Code");
	    	}else{
	    		url="../../../../../MISSubType?command=mainType&majorCode="+categoryDesc;
		        var req=getTransport();
		        req.open("POST",url,true);                
		        req.onreadystatechange=function(){
		        	loadSubCombo(req);
		        };
		        req.send(null);
	    	}
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
	 			alert("Records Added Successfully, Sub Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
	 			clearAll();
	 			callServer('Get');
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="Notadded"){
	 			alert("Data already added for the particular Sub Category code & Sub Category name");
	 			clearAll();
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="update"){
	 			alert("Records Updated Successfully for Sub Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
	 			clearAll();
	 			callServer('Get');
	 		}	 		 		
	 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="delete")
	 		{
	 		alert("Records Cancelled Successfully,Sub Category Id : "+response.getElementsByTagName("code")[0].firstChild.nodeValue);
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
	 var len1 = response.getElementsByTagName("categoryid").length;	
		var select=document.getElementById('categoryCode');
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){		
			listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("categorydesc")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("categoryid")[i].firstChild.nodeValue;
			}
		}else{
			//alert("Fail");
		}
 }
 function loadSubCombo(req){
	 if(req.readyState==4){ 
        if(req.status==200){
       	response=req.responseXML.getElementsByTagName("response")[0]; 
       	subCategoryCombo();
        }
    }
}
 function subCategoryCombo(){
	 var len1 = response.getElementsByTagName("MAIN_TYPE_CODE").length;	
		var select=document.getElementById('mainTypeCode');
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){		
			listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("MAIN_TYPE_DESC")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("MAIN_TYPE_CODE")[i].firstChild.nodeValue;
			}
		}else{
			//alert("Fail");
			select.length=1;
			select.selectedIndex=0;
		}
}
 function viewResponse(req){
	 if(req.readyState==4){ 
         if(req.status==200){
        	gridrepsonse=req.responseXML.getElementsByTagName("response")[0];        	
           	changepagesize();			
            statusflag=true;
         }
     }
 }
 function viewDetails(id){
	 var jid=document.getElementById("id"+id).value;
	 var url="../../../../../MISSubType?command=edit"+jid;
     var req=getTransport();
      req.open("POST",url,true);        
      req.onreadystatechange=function()
      {
         editview(req);
      };  
      req.send(null);
 }
 function editview(req){
	 if(req.readyState==4){ 
         if(req.status==200){        	 
        	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];
      	   	 editResponse(baseResponse);
         }
     } 
 }
 function editResponse(response){
	 var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	 	if(res=="success"){ 		
	 		document.getElementById('categoryCode').value=response.getElementsByTagName("MAJOR_TYPE_CODE")[0].firstChild.nodeValue;
	 		document.getElementById('subTypeCode').value=response.getElementsByTagName("SUB_TYPE_CODE")[0].firstChild.nodeValue;
	 		document.getElementById('subTypeDesc').value=response.getElementsByTagName("SUB_TYPE_NAME")[0].firstChild.nodeValue;
	 		var select=document.getElementById('mainTypeCode');					
			var listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			//listOpt.text="select";
			//listOpt.value="select";	
			for(var i=0; i<1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("MAIN_TYPE_NAME")[0].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("MAIN_TYPE_CODE")[0].firstChild.nodeValue;
			}
			document.getElementById('mainTypeCode').value=response.getElementsByTagName("MAIN_TYPE_CODE")[0].firstChild.nodeValue;
			 			
	 	}else{
	 		alert("Process Failure");
	 	}
	 	document.getElementById('categoryCode').disabled=true;
	 	document.getElementById('mainTypeCode').disabled=true;
	 	document.getElementById('butSub').disabled=true;
	 	document.getElementById('butCan').disabled=false;
	 	document.getElementById('update').disabled=false;
 }

function nullCheck(){
	if(document.getElementById('categoryCode').value=="select"){
		alert("Please select the Main Category Code");
		return false;
	}
	if(document.getElementById('mainTypeCode').value==""){
		alert("Please Enter the Main Type Description");
		return false;
	}	
	return true;
}