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
	var len = response.getElementsByTagName("BILL_CODE").length;	
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
//        alert('changepagesize');
	
}
function changepage() {
//	alert('changepage');
        var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var len = response.getElementsByTagName("BILL_CODE").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
//	alert("pageno"+pageno);
        var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var billCode = response.getElementsByTagName("BILL_CODE")[i].firstChild.nodeValue;
		var billDesc = response.getElementsByTagName("BILL_DESC")[i].firstChild.nodeValue;
		var remarks=response.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
		var view=response.getElementsByTagName("STATUSTYPE")[i].firstChild.nodeValue;
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
        	sch_id.value="&billCode="+billCode;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}		
		var td4 = document.createElement("TD");
		var tides = document.createTextNode(billCode);
		td4.appendChild(tides);
		tr.appendChild(td4);
		var td1 = document.createElement("TD");
		var tid = document.createTextNode(billDesc);
		td1.appendChild(tid);
		tr.appendChild(td1);
		var td2 = document.createElement("TD");
		var tides = document.createTextNode(remarks);
		td2.appendChild(tides);
		tr.appendChild(td2);		
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
			vartab.appendChild(td4);
			vartab.appendChild(td1);
			vartab.appendChild(td2);			
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
             /*document.professionTax.dateEffectiveFrom.value="";
        	 document.professionTax.startPay.value="";*/
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=5>There is No Data to Display</td></tr>";
			 /*document.professionTax.dateEffectiveFrom.value="";
			 document.professionTax.startPay.value="";*/
		 }
             
	}				
		/*document.professionTax.CmdAdd.disabled=false;
		document.professionTax.CmdUpdate.disabled=true;
		document.professionTax.CmdDelete.disabled=true;
		document.professionTax.CmdValidate.disabled=true;*/

}
 function clearAll(){
	 /*location.reload(true);*/
	document.getElementById('bill_type_code').value="";
	document.getElementById('bill_type_desc').value="";
	document.getElementById('bill_remarks').value="";	
	document.getElementById('CmdAdd').disabled=false;
	document.getElementById('CmdUpdate').disabled=true;
	document.getElementById('CmdDelete').disabled=true;
	 
 }
 function Exit(){
    self.close();
 }
 function nullCheck(){	 
	 if(document.professionTax.accountUnit.value=="select"){ 
          alert("Please select the account unit");          
          return false;
     }
	 if(document.professionTax.accountingOffice.value=="select"){ 
          alert("Please select the accounting office");          
          return false;
     }
	 if(document.professionTax.dateEffectiveFrom.value==""){ 
         alert("Please select effective date");          
         return false;
    }
	 if(document.professionTax.startPay.value==""){ 
         alert("Please enter starting pay");          
         return false;
    }
	 if(document.professionTax.endPay.value==""){ 
         alert("Please enter ending pay");          
         return false;
    }
	 if(document.professionTax.professionTaxAvail.value==""){ 
         alert("Please enter profession tax amount");          
         return false;
    }
    return true;
 }
 function callServer(command) {	 
	 var billDesc=document.getElementById('bill_type_desc').value;
	 var remarks=document.getElementById('bill_remarks').value;	 
     var url="";
       if(command=="Add"){
    	   	var flag=true;//nullCheck();    	   	
            if(flag==true){            	
            	url="../../../../../Bill_Major_Type_Mst?command=Add&billDesc="+billDesc+"&remarks="+remarks;            	
                var req=getTransport();
                req.open("POST",url,true);   
                req.onreadystatechange=function(){
                       processResponse(req);
                 };   
                 req.send(null);
                }
        }else if(command=="Update"){
                    var flag=true;//nullCheck();
                    if(flag==true){
                    	var billCode=document.getElementById('bill_type_code').value;
                    	url="../../../../../Bill_Major_Type_Mst?command=Update&billDesc="+billDesc+"&remarks="+remarks+"&billCode="+billCode;
                    	 var req=getTransport();
                    	 req.open("POST",url,true);        
                    	 req.onreadystatechange=function(){
                    		 processResponse(req);
                    	 };   
                    	 req.send(null);
                   }
        }else if(command=="Delete"){ 
        		var billCode=document.getElementById('bill_type_code').value;
        		url="../../../../../Bill_Major_Type_Mst?command=Delete&billDesc="+billDesc+"&remarks="+remarks+"&billCode="+billCode;
                var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function(){
                    processResponse(req);
                };   
                    req.send(null);
        }else if(command=="Validate"){
                    var flag=nullCheck();
                    if(flag==true){
                    	var billCode=document.getElementById('bill_type_code').value;
                    	url="../../../../../Bill_Major_Type_Mst?command=Update&billDesc="+billDesc+"&remarks="+remarks+"&billCode="+billCode;
                    	 var req=getTransport();
                    	 req.open("POST",url,true);        
                    	 req.onreadystatechange=function(){
                    		 processResponse(req);
                    	 };   
                    	 req.send(null);
                    }
        }else if(command=="Get"){        	
        		url="../../../../../Bill_Major_Type_Mst?command=Get";
                var req=getTransport();
                req.open("POST",url,true);                
                req.onreadystatechange=function(){
                	viewResponse(req);
                };
                req.send(null);        	            
        }        
}  
 function processResponse(req)
 {
	 if(req.readyState==4)
     { 
         if(req.status==200)
         {  
        	 var baseResponse=req.responseXML.getElementsByTagName("response")[0];        	 
      	   updateResponse(baseResponse);
         }
     }
 }
var checkflag=false;
 function updateResponse(response){
 	var res=response.getElementsByTagName("status")[0].firstChild.nodeValue;
 	if(res=="success"){
 		if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="added"){
 			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
 			if(flag=='success'){
 				alert("Records Added Successfully");
 			}else{
 				alert("Records are not saved");
 			}
 			//clear();
 			callServer('Get');
 		}else if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="Notadded"){
 			alert("Data already added for the particular major code & major name");
 			//clear();
 			callServer('Get');
 		}else if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="update"){
 			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
 			if(flag=='success'){
 				alert("Records Updated Successfully");
 			}else{
 				alert("Records are not updated");
 			} 			
 			//clear();
 			callServer('Get');
 		}else if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="validate"){
 			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
 			if(flag=='success'){
 				alert("Records Validated Successfully");
 			}else if(flag=='failure1'){
 				alert("Start pay is overlaped.\nRecords are not saved");
 			}else if(flag=='failure2'){
 				alert("End pay is overlaped.\nRecords are not saved");
 			}else if(flag=='failure3'){
 				alert("Existing pay are overlaped.\nRecords are not saved");
 			}else{
 				alert("Records are not validated");
 			} 			
 			clear();
 			callServer('Get');
 		}else if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="delete"){
 		alert("Records Cancelled Successfully");
 		//clear();
 		callServer('Get');
 		}
 	}else{
 		alert("Process failure");
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
	 url="../../../../../Bill_Major_Type_Mst?command=edit"+jid;
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
	 		document.getElementById('bill_type_code').value=response.getElementsByTagName("BILL_CODE")[0].firstChild.nodeValue;
	 		document.getElementById('bill_type_desc').value=response.getElementsByTagName("BILL_DESC")[0].firstChild.nodeValue;
	 		document.getElementById('bill_remarks').value=response.getElementsByTagName("REMARKS")[0].firstChild.nodeValue; 			
	 	}else{
	 		alert("Process Failure");
	 	}
	 	document.getElementById('CmdAdd').disabled=true;
	 	document.getElementById('CmdUpdate').disabled=false;
	 	document.getElementById('CmdDelete').disabled=false;
 }

 function calins(e,t)
 {
     var unicode=e.charCode? e.charCode : e.keyCode;
         //alert(unicode);
         //if(unicode !=8)
          
         if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
         {
             if(t.value.length==2 || t.value.length==5)
                 t.value=t.value + '/';
              if (unicode<48||unicode>57 ) 
                 return false; 
         }
 }
 //Account for office Id
 getAccountingOffice = function() {	
		document.getElementById('accountingOffice').selectedIndex=0;
		document.getElementById('accountingOffice').length=1;
		xmlhttp = getTransport();
		if (xmlhttp == null) {
			alert("Your borwser doesnot support AJAX");
			return;
		}
		var offid = document.getElementById("offid").value;
		var accountId= document.getElementById("accountUnit").value;		
		if ((offid == "")||(accountId=="select")) {
			alert("please select office id and account unit");
			clearAll();
		}else{		
			var req=getTransport();
			var urlhh = "../../../../Bill_Major_Type_Mst?command=accoffid&accountId="+accountId;
			req.open("POST", urlhh, true);
			req.onreadystatechange = function() {
				getReqId(req);
			};		
			req.send(null);
		}

};
function getReqId(req){
	if (req.readyState == 4) {
		if (req.status == 200) {
			var responsee = req.responseXML.getElementsByTagName("response")[0];
			var command=responsee.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag = responsee.getElementsByTagName("status")[0].firstChild.nodeValue;			
				if(command=="account"){
					var selectdiv=document.getElementById('accountingOffice');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Accounting office is Not Found for this Accounting unit ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag == "success"){
						var len=responsee.getElementsByTagName("ACCOUNTING_OFFICE_ID").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							listOpt.text=responsee.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
							listOpt.value=responsee.getElementsByTagName("ACCOUNTING_OFFICE_ID")[i].firstChild.nodeValue;
						}
					}
				}			
			}
		}
}
function numbersonly1(e, t) {
	//alert("t.length "+t.value.length);
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode == 13) {
		try {			
			t.blur();
		} catch (e) {
		}
		return true;

	}
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57)
			return false;
	}	
}