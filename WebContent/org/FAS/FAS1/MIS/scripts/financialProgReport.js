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
function changepagesize() {	
	pagesize = document.getElementById("cmbpagination").value;
	var len = response.getElementsByTagName("SUB_TYPE_CODE").length;	
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
	var len = response.getElementsByTagName("MAJOR_TYPE_CODE").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var fundType = response.getElementsByTagName("FUND_TYPE")[i].firstChild.nodeValue;		
		var fundCode=response.getElementsByTagName("FUND_CODE")[i].firstChild.nodeValue;
		var subDesc=response.getElementsByTagName("FUND_NAME")[i].firstChild.nodeValue;
		var view=response.getElementsByTagName("STATUSTYPE")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");
		var anc = document.createElement("A");
		if (view == "C") {
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
        	sch_id.value="&fundCode="+fundCode;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}
		var td1 = document.createElement("TD");
		var tid1 =""; 
		if(fundType=="U"){
			tid1=document.createTextNode("Urban");
		}else{
			tid1=document.createTextNode("Rural");
		}
		td1.appendChild(tid1);
		tr.appendChild(td1);
		var td2 = document.createElement("TD");
		var tides = document.createTextNode(fundCode);
		td2.appendChild(tides);
		tr.appendChild(td2);
		var td3 = document.createElement("TD");
		var ties = document.createTextNode(subDesc);
		td3.appendChild(ties);
		tr.appendChild(td3);
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
			vartab.appendChild(td1);
			vartab.appendChild(td2);
			vartab.appendChild(td3);
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
             iframe.innerHTML="<tr><td align=center colspan=5>There is No Data to Display</td></tr>";
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=5>There is No Data to Display</td></tr>";			 
		 }
             
	}				
	
}
 function clearAll(){
	document.getElementById('fundType').selectedIndex=0;
	document.getElementById('fundCode').value="";
	document.getElementById('fundDesc').value="";	
	document.getElementById('butSub').disabled=false;
	document.getElementById('butCan').disabled=true;
	document.getElementById('update').disabled=true;
	 
 }
 function Exit(){
    self.close();
 }
 
function callServer(command) {	 
	 var fundType=document.getElementById('fundType').value;
	 var fundDesc=document.getElementById('fundDesc').value;
     var url="";
       if(command=="Add"){
    	   	var flag=nullCheck();    	   	
            if(flag==true){            	
            	url="../../../../../MISFundProgress?command=add&fundType="+fundType+
            	"&fundDesc="+fundDesc;
                var req=getTransport();
                req.open("POST",url,true);   
                req.onreadystatechange=function(){
                       processResponse(req);
                 };   
                 req.send(null);
                }
        }else if(command=="update"){
                    var flag=nullCheck();
                    if(flag==true){
                    	var fundCode=document.getElementById('fundCode').value;
                    	url="../../../../../MISFundProgress?command=update&fundType="+fundType+
                    	"&fundCode="+fundCode+"&fundDesc="+fundDesc;
                    	 var req=getTransport();
                    	 req.open("POST",url,true);        
                    	 req.onreadystatechange=function(){
                    		 processResponse(req);
                    	 };   
                    	 req.send(null);
                   }
        }else if(command=="delete"){        		
	        	var fundCode=document.getElementById('fundCode').value;
            	url="../../../../../MISFundProgress?command=deleted&fundType="+fundType+
            	"&fundCode="+fundCode+"&fundDesc="+fundDesc;
                var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function(){
                    processResponse(req);
                };   
                    req.send(null);
        }else if(command=="Get"){        	
        		url="../../../../../MISFundProgress?command=getGrid";
                var req=getTransport();
                req.open("POST",url,true);                
                req.onreadystatechange=function(){
                	viewResponse(req);
                };
                req.send(null);        	            
        }else if(command=="mainCat"){      	
     		url="../../../../../MISFundProgress?command=maincombo";
            var req=getTransport();
            req.open("POST",url,true);                
            req.onreadystatechange=function(){
            	loadCombo(req);
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
		var select=document.getElementById('fundType');
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
 function viewResponse(req){
	 if(req.readyState==4){ 
         if(req.status==200){
        	response=req.responseXML.getElementsByTagName("response")[0];        	
           	changepagesize();			
            statusflag=true;
         }
     }
 }
 function viewDetails(id){
	 var jid=document.getElementById("id"+id).value;
	 var url="../../../../../MISFundProgress?command=edit"+jid;
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
	 		document.getElementById('fundType').value=response.getElementsByTagName("FUND_TYPE")[0].firstChild.nodeValue;
	 		document.getElementById('fundCode').value=response.getElementsByTagName("FUND_CODE")[0].firstChild.nodeValue;
	 		document.getElementById('fundDesc').value=response.getElementsByTagName("FUND_NAME")[0].firstChild.nodeValue;
	 		 			
	 	}else{
	 		alert("Process Failure");
	 	}
	 	document.getElementById('butSub').disabled=true;
	 	document.getElementById('butCan').disabled=false;
	 	document.getElementById('update').disabled=false;
 }

function nullCheck(){
	if(document.getElementById('fundType').value=="select"){
		alert("Please select the Fund Type");
		return false;
	}
	if(document.getElementById('fundDesc').value==""){
		alert("Please Enter the Fund Description");
		return false;
	}	
	return true;
}