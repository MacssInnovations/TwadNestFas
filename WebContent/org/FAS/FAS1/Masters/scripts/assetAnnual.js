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
	var len = response.getElementsByTagName("ASSET_CODE").length;	
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
	var len = response.getElementsByTagName("ASSET_CODE").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var assetCode = response.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;		
		var fairMarket = response.getElementsByTagName("FAIR_MARKET_VALUE")[i].firstChild.nodeValue;
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
        	sch_id.value="&assetCode="+assetCode;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}		
		var td4 = document.createElement("TD");
		var tides = document.createTextNode(assetCode);
		td4.appendChild(tides);
		tr.appendChild(td4);
		var td1 = document.createElement("TD");
		var tid = document.createTextNode(fairMarket);
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
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=5>There is No Data to Display</td></tr>";
		 }
             
	}

} 
 function clear(){
	document.getElementById('financialYear').value="";
	document.getElementById('assetCode').selectedIndex=0;
	document.getElementById('year').value="";
	document.getElementById('month').value="";
	document.getElementById('day').value="";
	document.getElementById('fairMarket').value="";
	document.getElementById('remarks').value="";
	document.getElementById('CmdAdd').disabled=false;
 	document.getElementById('CmdUpdate').disabled=true;
 	document.getElementById('CmdDelete').disabled=true;
 }
 function Exit(){
    self.close();
 }
 function nullCheck(){	 
	 if(document.getElementById('assetCode').value=="select"){ 
          alert("Please select the Asset Code");          
          return false;
     }
	 if(document.getElementById('financialYear').value==""){ 
          alert("Please enter financial year");          
          return false;
     }
	 if(document.getElementById('year').value==""){ 
         alert("Please enter year");          
         return false;
    }
	 if(document.getElementById('month').value==""){ 
         alert("Please enter month");          
         return false;
    }
	 if(document.getElementById('day').value==""){ 
         alert("Please enter day");          
         return false;
    }
	 if(document.getElementById('fairMarket').value==""){ 
         alert("Please enter fairMarket");          
         return false;
    }
    return true;
 }
 function callServer(command) { 	 
     var url="";
     	var accUnitId=document.getElementById('cmbAcc_UnitCode').value;
		var accOfficeId=document.getElementById('cmbOffice_code').value;        		
		var assetCode=document.getElementById('assetCode').value;
		var financialYear=document.getElementById('financialYear').value;
		var year=document.getElementById('year').value;
		var month=document.getElementById('month').value;
		var day=document.getElementById('day').value;		
		var fairMarket=document.getElementById('fairMarket').value;
		var remarks=document.getElementById('remarks').value;
       if(command=="Add"){
    	   	var flag=nullCheck();    	   	
            if(flag==true){            	        		
            	url="../../../../../AssetAnnual?command=Add&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+
            		"&assetCode="+assetCode+"&financialYear="+financialYear+"&year="+year+"&month="+month+
            		"&day="+day+"&fairMarket="+fairMarket+"&remarks="+remarks;            	
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
                    url="../../../../../AssetAnnual?command=Update&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+
                	"&assetCode="+assetCode+"&financialYear="+financialYear+"&year="+year+"&month="+month+
                	"&day="+day+"&fairMarket="+fairMarket+"&remarks="+remarks;            	
                    var req=getTransport();
                    req.open("POST",url,true);   
                    req.onreadystatechange=function(){
                       processResponse(req);
                    };   
                    req.send(null);
              }
        }else if(command=="Delete"){
        	var flag=nullCheck();
            if(flag==true){
        		url="../../../../../AssetAnnual?command=Delete&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+
            	"&assetCode="+assetCode+"&financialYear="+financialYear+"&year="+year+"&month="+month+
            	"&day="+day+"&fairMarket="+fairMarket+"&remarks="+remarks;
                var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function(){
                    processResponse(req);
                };   
                    req.send(null);
            }
        }else if(command=="get"){
    		url="../../../../../AssetAnnual?command=get&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId+"&assetCode="+assetCode;
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function(){
            	viewResponse(req);
            };        
            req.send(null);        	            
        }else if(command=="getAssetCode"){
        	accUnitId=document.getElementById('cmbAcc_UnitCode').value;
    		accOfficeId=document.getElementById('cmbOffice_code').value;
    		url="../../../../../AssetAnnual?command=assetCode&accUnitId="+accUnitId+"&accOfficeId="+accOfficeId;
            var req=getTransport();
            req.open("POST",url,true); 
            req.onreadystatechange=function(){
            	getReqId(req);
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
 		if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="add"){
 			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
 			if(flag=='success'){
 				alert("Records Added Successfully");
 			}else{
 				alert("Records are not saved");
 			}
 			clear();
 			callServer('get');
 		}else if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="update"){
 			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
 			if(flag=='success'){
 				alert("Records Updated Successfully");
 			}else{
 				alert("Records are not updated");
 			} 			
 			clear();
 			callServer('get');
 		}else if(response.getElementsByTagName("command")[0].firstChild.nodeValue=="delete"){
 		alert("Records Cancelled Successfully");
 		clear();
 		callServer('get');
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
	 url="../../../../../AssetAnnual?command=edit"+jid;
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
	 		document.getElementById('cmbAcc_UnitCode').value=response.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
	 		document.getElementById('cmbOffice_code').value=response.getElementsByTagName("ACCOUNTING_UNIT_OFFICE_ID")[0].firstChild.nodeValue;
	 		document.getElementById('financialYear').value=response.getElementsByTagName("FINANCIAL_YEAR")[0].firstChild.nodeValue;
	 		document.getElementById('assetCode').value=response.getElementsByTagName("ASSET_CODE")[0].firstChild.nodeValue;
	 		document.getElementById('year').value=response.getElementsByTagName("PENDING_YEARS")[0].firstChild.nodeValue;
	 		document.getElementById('month').value=response.getElementsByTagName("PENDING_MONTH")[0].firstChild.nodeValue;
	 		document.getElementById('day').value=response.getElementsByTagName("PENDING_DAY")[0].firstChild.nodeValue;
	 		document.getElementById('fairMarket').value=response.getElementsByTagName("FAIR_MARKET_VALUE")[0].firstChild.nodeValue; 			
	 		document.getElementById('remarks').value=response.getElementsByTagName("REMARKS")[0].firstChild.nodeValue;
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
function getReqId(req){
	if (req.readyState == 4) {
		if (req.status == 200) {
			var responsee = req.responseXML.getElementsByTagName("response")[0];
			var command=responsee.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag = responsee.getElementsByTagName("status")[0].firstChild.nodeValue;			
				if(command=="assetcode"){
					//alert("coming hereeeeee");
					var selectdiv=document.getElementById('assetCode');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Asset Code is Not Found for this Accounting unit ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag == "success"){
						var len=responsee.getElementsByTagName("ASSET_CODE").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");							
							listOpt.text=responsee.getElementsByTagName("ASSET_DESC")[i].firstChild.nodeValue;
							listOpt.value=responsee.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;
							//alert(listOpt.text);
							selectdiv.appendChild(listOpt);
						}
					}
				}			
			}
		}
}
function numbersonly(e, t) {
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

function common_LoadOffice(){
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;          
    if(unitID_val!=""){
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        };
        req.send(null);
    }     
}
function handle_loadOffice(req){  
    if(req.readyState==4){     
     if(req.status==200){      
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success"){ 
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
            for(i=0;i<offidvalues.length;i++){  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
                option.value=offid;
                try{
                    cmboffice.add(option);
                }catch(errorObject ){
                    cmboffice.add(option,null);
                }   
            }
            
        }else{
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try{
                cmboffice.add(option);
            }catch(errorObject){
                cmboffice.add(option,null);
            }
        }
        
     }
    }
}
function handle_loadOffice(req){  
    if(req.readyState==4){
     if(req.status==200){
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success"){ 
         try{
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
            for(i=0;i<offidvalues.length;i++){  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
                option.value=offid;
                try{
                    cmboffice.add(option);
                }catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
        //callServer('getAssetCode');  
             
     }
    }
}
function LoadOffice(unitID_val)
{
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        };
        req.send(null);
    }
}




function handle_loadOffice(req)
{
  
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
      
        //alert(req.responseText);
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        //alert(baseresponse);
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
           
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
          
            var offidvalues=baseresponse.getElementsByTagName("offid");
            //alert(offid.length)
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
        }
        else
        {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
        }
            
        callServer('getAssetCode');     
     }
    }
}
