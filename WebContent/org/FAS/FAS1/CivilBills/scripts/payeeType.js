/////////////////////////////creating AJAX object////////////////////////
var emp_flag;
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
///////////////////////////////exit mathod////////////////////////////////////////////////////////////
function exitmethod()
{
      window.close();
}
//////////////////////////////////////////////////////////////////////////////////////////////////////
function callpayeeAdd()
{
        if(document.getElementById("txtPayeetypeDesc").value==0)
        {
                alert("Enter the Payee Type Description");
        }
        else
        {
            var PayeetypeDesc=document.getElementById("txtPayeetypeDesc").value;
            var url="../../../../../Payeetype?command=add&PayeetypeDesc1="+PayeetypeDesc;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                    processResponse(req);
            }   
            req.send(null);
        }
}
function callpayeeUpdate()
{
            //alert("update");
            var PayeeypeCode=document.getElementById("txtPayeeypeCode").value;
            var PayeetypeDesc=document.getElementById("txtPayeetypeDesc").value;
            var url="../../../../../Payeetype?command=updated&PayeeypeCode1="+PayeeypeCode+"&PayeetypeDesc1="+PayeetypeDesc;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                    processResponse(req);
            };   
            req.send(null);
}
function callpayeeDelete()
    {
            var PayeeypeCode=document.getElementById("txtPayeeypeCode").value;
            var r=confirm("Are U Sure?");
            if(r==true)
                {
                     var url="../../../../../Payeetype?command=deleted&PayeeypeCode1="+PayeeypeCode;
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
                        var payeecode=baseResponse.getElementsByTagName("payeecode")[0].firstChild.nodeValue;
                        alert("Payee Type Code " + payeecode + " is inserted into the database successfully...");
                        getpayee();
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
	               	getpayee();
                    clearAll();
                }
        }    
    }
}
function clearAll()
    {
            document.getElementById("txtPayeeypeCode").value="";
            document.getElementById("txtPayeetypeDesc").value="";
            
            document.forms[0].butAdd.disabled=false;  
            document.forms[0].butUpdate.disabled=true;
            document.forms[0].butDelete.disabled=true; 
    }

/************************************************************************************************************/
function callpayeeList()
    {
        winemp= window.open("payeeType_list.jsp","list1","status=1,height=500,width=600,resizable=YES,scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }
function doParentEmp(payee_code)    
    {   
       // clearAll();
        document.forms[0].txtPayeeypeCode.value=payee_code;
        var url="../../../../../Payeetype?command=retrieve&payee_code1="+payee_code;
        var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                        processResponse(req);
                }   
                req.send(null);
              
    }  
function retrieveChecking(baseResponse)   
    {
          var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                  if(flag=="success")
                  {       
                      //cheqmemo_code =  baseResponse.getElementsByTagName("cheqmemo_code");
                      var payee_desc =  baseResponse.getElementsByTagName("payee_desc");
                      
                      //document.forms[0].txtChequeMemoTypeCode.value=cheqmemo_code[0].firstChild.nodeValue;
                      document.forms[0].txtPayeetypeDesc.value=payee_desc[0].firstChild.nodeValue;
                      
                      document.forms[0].butAdd.disabled=true;  
                      document.forms[0].butUpdate.disabled=false;
                      document.forms[0].butDelete.disabled=false;  
                      
                    }

    }
 
function updateChecking(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
           {   
               alert("Record Updated Successfully.");
               getpayee();
               clearAll();
           }
       else
           {
               alert("Failed to update values");
           }                                  
    }
    


var response="";  
var seq=0;
var pagesize = 10;
function getpayee(){	
	var url="../../../../../Payeetype?command=getpayee";
    var req=getTransport();
    req.open("GET",url,true);                
    req.onreadystatechange=function(){
    	viewResponse(req);
    };
    req.send(null);	
}
var statusflag=true;
function viewResponse(req){
	 if(req.readyState==4){ 
        if(req.status==200){
       	response=req.responseXML.getElementsByTagName("response")[0];        	
          	changepagesize();			
           statusflag=true;
        }
    }
}
var browserName=navigator.appName;
var brow="";
if (browserName=="Netscape")
{ 	brow="nets";}
else if (browserName=="Microsoft Internet Explorer")
{ 	brow="iex";} 
function changepagesize() {	
	pagesize = document.getElementById("cmbpagination").value;
	var len = response.getElementsByTagName("payee_code").length;	
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
	var len = response.getElementsByTagName("payee_code").length;	
	
	if(response.getElementsByTagName("flag")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var billCode = response.getElementsByTagName("payee_code")[i].firstChild.nodeValue;
		var billDesc = response.getElementsByTagName("payee_desc")[i].firstChild.nodeValue;		
		var view=response.getElementsByTagName("status")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");
		//var anc = document.createElement("A");
		if (view == "C") {
			//var tid = document.createTextNode("Cancel");			
			var priceSpan = document.createElement("span");
			priceSpan.setAttribute('style','font-weight:bold; color:green; font-family:Verdana');
			priceSpan.appendChild(document.createTextNode("Cancel"));			
			td.appendChild(priceSpan);
			tr.appendChild(td);
		}else{
			var anc=document.createElement("A");   
			var url = "javascript:viewDetails('" + seq + "')";
			anc.href = url;
			var edit = document.createTextNode("Edit");
			anc.appendChild(edit);
			td.appendChild(anc);
			var sch_id=document.createElement("TEXT");
        	sch_id.type="hidden";
        	sch_id.name="name"+seq;
        	sch_id.id="id"+seq;
        	sch_id.value="&payCode="+billCode;	       
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
function viewDetails(id){
	 var jid=document.getElementById("id"+id).value;	 	 	 
	 url="../../../../../Payeetype?command=edit"+jid;
    var req=getTransport();
     req.open("GET",url,true);        
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
	 var res=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	 	if(res=="success"){ 		
	 		document.getElementById('txtPayeeypeCode').value=response.getElementsByTagName("payee_code")[0].firstChild.nodeValue;
	 		document.getElementById('txtPayeetypeDesc').value=response.getElementsByTagName("payee_desc")[0].firstChild.nodeValue;	 		 			
	 	}else{
	 		alert("Process Failure");
	 	}
	 	document.getElementById('butAdd').disabled=true;
	 	document.getElementById('butUpdate').disabled=false;
	 	document.getElementById('butDelete').disabled=false;
}
 
    
    
    