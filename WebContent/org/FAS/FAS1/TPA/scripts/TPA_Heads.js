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

function getAccountHeadcode(){	
	var url="../../../../../ChequeMemoType?command=gethead";
    var req=getTransport();
    req.open("GET",url,true);                
    req.onreadystatechange=function(){
    	viewResponse(req);
    };
    req.send(null);	
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

function changepagesize() {	
	pagesize = document.getElementById("cmbpagination").value;
	var len = response.getElementsByTagName("cheqmemo_code").length;	
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
	var len = response.getElementsByTagName("Headcode").length;	
	if(response.getElementsByTagName("flag")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var hcode = response.getElementsByTagName("Headcode")[i].firstChild.nodeValue;
		var billDesc = response.getElementsByTagName("cheqmemo_desc")[i].firstChild.nodeValue;		
		var view=response.getElementsByTagName("status")[i].firstChild.nodeValue;
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
        	sch_id.value="&chequeCode="+billCode;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}		
		var td4 = document.createElement("TD");
		var tides = document.createTextNode(billCode);
		td4.appendChild(tides);
		tr.appendChild(td4);
		
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
	
}

function callchequeMemoAdd()
{
        if(document.getElementById("acheadcode").value==0)
        {
                alert("Enter the headcode");
        }
        else
        {
            var url="../../../../../ChequeMemoType?command=add&acheadcode="+acheadcode;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                 processResponse(req);
            };   
            req.send(null);
        }
}

function processResponse(req)
{   
    if(req.readyState==4)   // Completed
      {
          if(req.status==200)   // No error
          {   
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              
              if(command=="addResponse")
              {
                    //alert(command);
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                        var acheadcode=baseResponse.getElementsByTagName("acheadcode")[0].firstChild.nodeValue;
                        alert("Account HeadCode " + acheadcode + " is inserted into the database successfully...");
                        getAccountHeadcode();
                        clearAll();
                    }
                }
               else if(command=="retrieve")
               {
                  retrieveChecking(baseResponse);
               }
                else if(command=="updated")
                {
                   updateChecking(baseResponse);
                }
                else if(command=="deleted"){
                	 var paytype=baseResponse.getElementsByTagName("paytype")[0].firstChild.nodeValue;
                	 if(paytype=="n"){
                		 alert("Record cancel successfully");
                	 }else{
                		 alert("Cancel fail. Record Already in Cheque Memo Payee Type Master");
                	 }                     
                	 getAccountHeadcode();
                     clearAll();
                }
        }    
    }
}

function clearAll()
{
     
        document.forms[0].butAdd.disabled=false;  
        document.forms[0].butUpdate.disabled=true;
        document.forms[0].butDelete.disabled=true; 
}
function callheadUpdate()
{
            //alert("update");
            var ChequeMemoTypecode=document.getElementById("txtChequeMemoTypeCode").value;
            var ChequeMemoTypeDesc=document.getElementById("txtChequeMemoTypeDesc").value;
            var url="../../../../../ChequeMemoType?command=updated&ChequeMemoTypecode1="+ChequeMemoTypecode+"&ChequeMemoTypeDesc1="+ChequeMemoTypeDesc;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                    processResponse(req);
            };   
            req.send(null);
}
function callheadDelete(){
	var ChequeMemoTypecode=document.getElementById("txtChequeMemoTypeCode").value;
    var ChequeMemoTypeDesc=document.getElementById("txtChequeMemoTypeDesc").value;
    var url="../../../../../ChequeMemoType?command=deleted&ChequeMemoTypecode1="+ChequeMemoTypecode+"&ChequeMemoTypeDesc1="+ChequeMemoTypeDesc;
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
            processResponse(req);
    };   
    req.send(null);        
}
