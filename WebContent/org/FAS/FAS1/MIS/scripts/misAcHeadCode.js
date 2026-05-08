var winemp;
var seq = 0;
var common = "";
var length = 0;
var flag, command, response="";
var pagesize = 10;
var pagesize1 = 10;
var z=0;
var accHead=new Array();
var statusflag=false;
var headcode_list_SL;
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
	var len = response.getElementsByTagName("ACCOUNT_CODE").length;
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
	var len = response.getElementsByTagName("ACCOUNT_CODE").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{
		document.getElementById("gridDiv").style.visibility='visible';
	for ( var i = ll; i < ul; i++) {		
		var accCode=response.getElementsByTagName("ACCOUNT_CODE")[i].firstChild.nodeValue;
		var accName=response.getElementsByTagName("ACCOUNT_NAME")[i].firstChild.nodeValue;		
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");		
		var sch_id=document.createElement("input");
        sch_id.type="checkbox";
        sch_id.name="accName";
        sch_id.id="id"+seq;
        sch_id.value=accCode;
        sch_id.checked = false;
        sch_id.setAttribute("onclick", "viewDetails('" + seq + "');");
        td.appendChild(sch_id);
		tr.appendChild(td);			
		
		var td8 = document.createElement("TD");
		var ti8 = document.createTextNode(accCode);
		td8.appendChild(ti8);
		tr.appendChild(td8);
		var td9 = document.createElement("TD");
		var ti9 = document.createTextNode(accName);
		td9.appendChild(ti9);
		tr.appendChild(td9);		
		if(brow=="iex"){
			var vartab = tlist.insertRow(-1);			
			vartab.appendChild(td);
			vartab.appendChild(td8);
			vartab.appendChild(td9);
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
             iframe.innerHTML="<tr><td align=center colspan=3>There is No Data to Display</td></tr>";             
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=3>There is No Data to Display</td></tr>";			 
		 }
             
	}				
	listAccountHeadCode();
}
 function clearAll(){
	document.getElementById('categoryCode').selectedIndex=0;
	document.getElementById('subCategoryCode').selectedIndex=0;
	document.getElementById('subCategoryCode').length=1;
	document.getElementById('fundExpend').selectedIndex=0;
	document.getElementById('txtaccountheadcode').selectedIndex=0;
	document.getElementById('txtaccountheadcode').length=1;
	document.getElementById('txtaccountheadname').value="";
	document.getElementById('txtaccountheadcode').disabled=true;
	try {	
		document.getElementById('tblList').innerHTML="";
	} catch (e) {
		document.getElementById('tblList').innerText="";
	}
	document.getElementById('gridDiv').style.visibility='hidden';
	document.getElementById('butSub').disabled=false;
	document.getElementById('butCan').disabled=true;
	document.getElementById('update').disabled=true;
	document.getElementById('categoryCode').disabled=false;
 	document.getElementById('subCategoryCode').disabled=false;
 	document.getElementById('fundExpend').disabled=false;
	 
 }
 function Exit(){
    self.close();
 }
 
function callServer(command) {	 
	 var categoryDesc=document.getElementById('categoryCode').value;
	 var subcategoryDesc=document.getElementById('subCategoryCode').value;
	 var fundExpend=document.getElementById('fundExpend').value;
     var url="";
       if(command=="Add"){
    	   	var flag=nullCheck();    	   	
            if(flag==true){          	
            	url="../../../../../MISAcHeadCode?command=add&mainCategoryId="+categoryDesc+
            	"&subCategoryId="+subcategoryDesc+"&fundExpend="+fundExpend+"&accHead="+accHead; 
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
                    	var accHeadCode=document.getElementById('txtaccountheadcode').value;
                    	var accHeadDesc=document.getElementById('txtaccountheadname').value;
                    	url="../../../../../MISAcHeadCode?command=update&mainCategoryId="+categoryDesc+
                    	"&subCategoryId="+subcategoryDesc+"&accountHeadCode="+accHeadCode+
                    	"&fundExpend="+fundExpend+"&preAcchead="+accHeadDesc;
                    	 var req=getTransport();
                    	 req.open("POST",url,true);        
                    	 req.onreadystatechange=function(){
                    		 processResponse(req);
                    	 };   
                    	 req.send(null);
                   }
        }else if(command=="Delete"){
        	var accHeadCode=document.getElementById('txtaccountheadcode').value;
        	var accHeadDesc=document.getElementById('txtaccountheadname').value;
        	url="../../../../../MISAcHeadCode?command=deleted&mainCategoryId="+categoryDesc+
        	"&subCategoryId="+subcategoryDesc+"&accountHeadCode="+accHeadCode+
        	"&fundExpend="+fundExpend+"&preAcchead="+accHeadDesc;
                var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function(){
                    processResponse(req);
                };   
                    req.send(null);
        }else if(command=="Get"){
        	var flag=nullCheck();
        	var fundExpend=document.getElementById('fundExpend').value;
            if(flag==true){
            	url="../../../../../MISAcHeadCode?command=getGrid&fundExpend="+fundExpend;
                var req=getTransport();
                req.open("POST",url,true);                
                req.onreadystatechange=function(){
                	viewResponse(req);
                };
                req.send(null);
            }        	           		        	            
        }else if(command=="mainCat"){      	
     		url="../../../../../MISSubCategory?command=maincombo";
            var req=getTransport();
            req.open("POST",url,true);                
            req.onreadystatechange=function(){
            	loadCombo(req);
            };
            req.send(null);        	            
   	  }else if(command=="loadAcc"){
   		  	var flag=nullCheck();
	   		var fundExpend=document.getElementById('fundExpend').value;
	   		if(flag==true){
	   			url="../../../../../MISAcHeadCode?command=loadExist&fundExpend="+fundExpend+
    			"&mainCategoryId="+categoryDesc+"&subCategoryId="+subcategoryDesc;
    	        var req=getTransport();
    	        req.open("POST",url,true);                
    	        req.onreadystatechange=function(){
    	        	viewPopupResponse(req);
    	        };
    	        req.send(null);
	   		}	   					
      }else if(command=="subCat"){
    	  if(categoryDesc=="select"){
  	    		alert("Please select the Main Category Code");
  	    	}else{
  	    		url="../../../../../MISAcHeadCode?command=subcat&majorCode="+categoryDesc;
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
	 			var len = response.getElementsByTagName("code").length;
	 			var cod = new Array();;
	 			for(var i=0; i<len; i++){
	 				cod.push(response.getElementsByTagName("code")[i].firstChild.nodeValue);	 				
	 			}
	 			alert("Accout Head Code "+cod+" are added Successfully");
	 			clearAll();
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="Notadded"){
	 			alert("Data already added for the particular Sub Category code & Sub Category name");
	 			clearAll();
	 		}else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="update"){
	 			alert("Records Updated Successfully");
	 			clearAll();
	 		}	 		 		
	 		else if(response.getElementsByTagName("value")[0].firstChild.nodeValue=="delete")
	 		{
	 		alert("Records Cancelled Successfully");
	 		clearAll();
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
			select.length=1;
			select.selectedIndex=0;
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
	 if(document.getElementById("id"+id).checked==true){		 
		 accHead.push(jid);
	 }
	 if(document.getElementById("id"+id).checked==false){
		 var rem=document.getElementById("id"+id).value;
		 for(var x=0; x<accHead.length; x++){
			 if(accHead[x]==rem){
				 accHead.splice(x,1); 
			 }
		 }		 
	 }	
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
	 		opener.document.getElementById('categoryCode').value=response.getElementsByTagName("MAJOR_TYPE_CODE")[0].firstChild.nodeValue;
	 		opener.document.getElementById('subCategoryCode').value=response.getElementsByTagName("SUB_TYPE_CODE")[0].firstChild.nodeValue;
	 		opener.document.getElementById('fundExpend').value=response.getElementsByTagName("FUND_TYPE")[0].firstChild.nodeValue;	 		
	 		opener.document.getElementById('txtaccountheadcode').value=response.getElementsByTagName("ACCOUNT_CODE")[0].firstChild.nodeValue;
	 		opener.document.getElementById('txtaccountheadcode').disabled=false;
	 		opener.document.getElementById('txtaccountheadname').value=response.getElementsByTagName("ACCOUNT_CODE")[0].firstChild.nodeValue;
	 		 			
	 	}else{
	 		alert("Process Failure");
	 	}
	 	opener.document.getElementById('categoryCode').disabled=true;
	 	opener.document.getElementById('subCategoryCode').disabled=true;
	 	opener.document.getElementById('fundExpend').disabled=true;
	 	opener.document.getElementById('butSub').disabled=true;
	 	opener.document.getElementById('butCan').disabled=false;
	 	opener.document.getElementById('update').disabled=false;
	 	self.close();
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
	 var len1 = response.getElementsByTagName("categoryid").length;	
		var select=document.getElementById('subCategoryCode');
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
			select.length=1;
			select.selectedIndex=0;
			//alert("Fail");
		}
}

function accountList(){
	var mainCatId=document.getElementById('categoryCode').value;
	var subCatId=document.getElementById('subCategoryCode').value;
	var fundType=document.getElementById('fundExpend').value;
    if (headcode_list_SL && headcode_list_SL.open && !headcode_list_SL.closed){
    	headcode_list_SL.resizeTo(500,500);
    	headcode_list_SL.moveTo(250,250); 
    	headcode_list_SL.focus();
    }else{
    	headcode_list_SL=null;
    }
    headcode_list_SL=window.open("../jsps/misAcHeadCodeList.jsp?mainCatId="+mainCatId+"&subCatId="+subCatId+"&fundType="+fundType,"AccountHeadCodeList","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    headcode_list_SL.moveTo(250,250);  
    headcode_list_SL.focus();
    
}

window.onunload=function(){
    if (headcode_list_SL && headcode_list_SL.open && !headcode_list_SL.closed) 
        headcode_list_SL.close();
};
function viewPopupResponse(req){
	 if(req.readyState==4){ 
        if(req.status==200){
       	response=req.responseXML.getElementsByTagName("response")[0];        	
          	changepagesize1();			
           statusflag=true;
        }
    }
}
function changepagesize1() {	
	pagesize1 = document.getElementById("cmbpagination1").value;
	var len = response.getElementsByTagName("ACCOUNT_CODE").length;
	var cmbpage = document.getElementById("cmbpage1");
	try {	
		cmbpage.innerHTML = "";
	} catch (e) {
		cmbpage.innerText = "";
	}	
	var i = 1;
	for (i = 1; i <= ((len / pagesize1) + 1); i++) {
		var option = document.createElement("OPTION");
		option.text = i;
		option.value = i;
		try {
			cmbpage.add(option);
		} catch (errorObject) {
			cmbpage.add(option, null);
		}
	}
	changepage1();
	
}
var seq1=0;
function changepage1() {
	var tlist = document.getElementById("tblList1");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var len = response.getElementsByTagName("ACCOUNT_CODE").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage1").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize1;
	ll = ul - pagesize1;
	try{
		//document.getElementById("gridDiv").style.visibility='visible';
	for ( var i = ll; i < ul; i++) {		
		var accCode=response.getElementsByTagName("ACCOUNT_CODE")[i].firstChild.nodeValue;
		var accName=response.getElementsByTagName("ACCOUNT_NAME")[i].firstChild.nodeValue;
		var majorCode=response.getElementsByTagName("MAJOR_TYPE_CODE")[i].firstChild.nodeValue;
		var subCode=response.getElementsByTagName("SUB_TYPE_CODE")[i].firstChild.nodeValue;
		var fundtype=response.getElementsByTagName("FUND_TYPE")[i].firstChild.nodeValue;
		var view=response.getElementsByTagName("STATUSTYPE")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq1;
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
			var url = "javascript:viewListDetails('" + seq1 + "')";			
			anc.href = url;
			var edit = document.createTextNode("Edit");
			anc.appendChild(edit);
			td.appendChild(anc);
			var sch_id=document.createElement("TEXT");
        	sch_id.type="hidden";
        	sch_id.name="name"+seq1;
        	sch_id.id="id"+seq1;
        	sch_id.value="&majorCode="+majorCode+"&subCode="+subCode+"&fundtype="+fundtype+"&accCode="+accCode;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}			
		
		var td8 = document.createElement("TD");
		var ti8 = document.createTextNode(accCode);
		td8.appendChild(ti8);
		tr.appendChild(td8);
		var td9 = document.createElement("TD");
		var ti9 = document.createTextNode(accName);
		td9.appendChild(ti9);
		tr.appendChild(td9);		
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
			vartab.appendChild(td8);
			vartab.appendChild(td9);
			vartab.appendChild(td5);
		}else
		{
			tlist.appendChild(tr);
		}		
		seq1++;
	}
	}catch(err){
	}
	}
	else{
		 var iframe=document.getElementById("tblList1");
         iframe.focus();
		 if(navigator.appName.indexOf('Microsoft')!=-1){
             iframe.innerHTML="<tr><td align=center colspan=4>There is No Data to Display</td></tr>";             
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=4>There is No Data to Display</td></tr>";			 
		 }
             
	}				
	
}
function viewListDetails(id){
	 var jid=document.getElementById("id"+id).value;	 	 	 
	 var url="../../../../../MISAcHeadCode?command=edit"+jid;
	 var req=getTransport();
     req.open("POST",url,true);        
     req.onreadystatechange=function()
     {
        editview(req);
     };  
     req.send(null);
}
function listAccountHeadCode(){
	var fundType=document.getElementById('fundExpend').value;
	var url="../../../../../MISAcHeadCode?command=getGrid&fundExpend="+fundType;
    var req=getTransport();
    req.open("POST",url,true);                
    req.onreadystatechange=function(){
    	loadAccCombo(req);
    };
    req.send(null);
	
}
function loadAccCombo(req){
	if(req.readyState==4){ 
        if(req.status==200){
	       	response=req.responseXML.getElementsByTagName("response")[0]; 
	       	var select=document.getElementById('txtaccountheadcode');
	       	var len=response.getElementsByTagName("ACCOUNT_CODE").length;
	    	listOpt=document.createElement("option");
	    	select.length=0;
	    	select.appendChild(listOpt);
	    	listOpt.text="select";
	    	listOpt.value="select";	
	    	for(var i=0; i<len; i++){
	    		listOpt=document.createElement("option");
	    		select.appendChild(listOpt);
	    		listOpt.text=response.getElementsByTagName("ACCOUNT_CODE")[i].firstChild.nodeValue
	    		+"-"+response.getElementsByTagName("ACCOUNT_NAME")[i].firstChild.nodeValue;
	    		listOpt.value=response.getElementsByTagName("ACCOUNT_CODE")[i].firstChild.nodeValue;
	    	}
	   }
    }	
}
function nullCheck(){
	if(document.getElementById('categoryCode').value=="select"){
		alert("Please select the Main Category Code");
		return false;
	}
	if(document.getElementById('subCategoryCode').value=="select"){
		alert("Please select the Sub Category Code");
		return false;
	}
	if(document.getElementById('fundExpend').value=="select"){
		alert("Please select the Fund or Expenditure");
		return false;
	}
	return true;
}