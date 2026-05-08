var slno=0;
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

function loadcheqmemocode()
{
    var url="../../../../../Cheqmemo_payee?command=loadCheqmemocode";
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
            processResponse(req);
    };   
    req.send(null);
}
function loadpayeecode()
{
    var url="../../../../../Cheqmemo_payee?command=loadPayeecode";
    //alert(url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
            processResponse(req);
    }   
    req.send(null);
}
function callcheqpayeeAdd()
{
        if(document.getElementById("cmbcheqmemo_code").value==0)
        {
                alert("Enter the Cheque Memo Code");
                document.getElementById("cmbcheqmemo_code").focus();
        }
        else if(document.getElementById("cmbpayee_code").value==0)
        {
                alert("Enter the Payee Code");
                document.getElementById("cmbpayee_code").focus();
        }
        else
        {
            var cheqmemocode=document.getElementById("cmbcheqmemo_code").value;
            var payeecode=document.getElementById("cmbpayee_code").value;
            var url="../../../../../Cheqmemo_payee?command=add&cheqmemocode1="+cheqmemocode+"&payeecode1="+payeecode;
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
function callcheqpayeeUpdate()
{
            //alert("update");
            var cheqmemocode=document.getElementById("cmbcheqmemo_code").value;
            var payeecode=document.getElementById("cmbpayee_code").value;
            var url="../../../../../Cheqmemo_payee?command=updated&cheqmemocode1="+cheqmemocode+"&payeecode1="+payeecode+"&serialno1="+slno;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                    processResponse(req);
            };   
            req.send(null);
}
function callcheqpayeeDelete()
    {
            var cheqmemocode=document.getElementById("cmbcheqmemo_code").value;
            var payeecode=document.getElementById("cmbpayee_code").value;
            var r=confirm("Are U Sure?");
            if(r==true)
                {
                     var url="../../../../../Cheqmemo_payee?command=deleted&cheqmemocode1="+cheqmemocode+"&payeecode1="+payeecode+"&serialno1="+slno;
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
              if(command=="loadCheqcode")
              {
                //alert(command);
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                    {
                        //alert(flag);
                        var option=baseResponse.getElementsByTagName("option");
                        var cheqmemo_code=document.getElementById("cmbcheqmemo_code");
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            cheqmemo_code.appendChild(opt);
                         }
                    }
                    else if(flag=="nodata")
                    {
                        alert("No records to load ");
                    }
                    else
                    {
                        alert("Failed to load records");
                    }    
              }
              else if(command=="loadPayeecode")
              {
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                    {
                        var option=baseResponse.getElementsByTagName("option");
                        var payee_code=document.getElementById("cmbpayee_code");
                        for(var i=0;i<option.length;i++)
                         {
                            var code=option[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                            var desc=option[i].getElementsByTagName("desc")[0].firstChild.nodeValue;
                            //alert(code+"   "+desc);
                            var opt=document.createElement("option");
                            opt.setAttribute("value",code);
                            var opttext=document.createTextNode(desc);
                            opt.appendChild(opttext);
                            payee_code.appendChild(opt);
                         }
                    }
                    else if(flag=="nodata")
                    {
                        alert("No records to load ");
                    }
                    else
                    {
                        alert("Failed to load records");
                    }    
              }
             else if(command=="addResponse")
              {
                    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                    if(flag=="success")
                    {
                        var slno=baseResponse.getElementsByTagName("slno")[0].firstChild.nodeValue;
                        alert("Cheque Memo & Payee Type Code " + slno + " is inserted into the database successfully...");
                        clearAll();
                    }
                    else if(flag=="AlreadyExist")
                    {
                         alert("Record AlreadyExist.so,can't Inserted");
                    }
                    else
                       {
                               alert("Failed to Add values");
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
                else if(command=="deleted")
                { 
                     deleteChecking(baseResponse);
                     
                }
        }    
    }
}
function clearAll()
    {
            document.getElementById("cmbcheqmemo_code").value=0;
            document.getElementById("cmbpayee_code").value=0;
            
            document.forms[0].butAdd.disabled=false;  
            document.forms[0].butUpdate.disabled=true;
            document.forms[0].butDelete.disabled=true; 
    }
function callcheqpayeeList()
    {
        var cheqtype=document.getElementById("cmbcheqmemo_code").value;
//        alert("cheqtype**************"+cheqtype);
		winemp= window.open("cheqmemo_payeeList.jsp?&cheqtype="+cheqtype,"list1","status=1,height=500,width=600,resizable=YES,scrollbars=yes"); 
        winemp.moveTo(250,250);  
        winemp.focus();
    }
function doParentEmp(serial_no)    
    {   
        slno=serial_no;
        var url="../../../../../Cheqmemo_payee?command=retrieve&serial_no1="+serial_no;
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
                  //alert(flag);
                  if(flag=="success")
                  {       
                      var cheqmemo_code =  baseResponse.getElementsByTagName("cheqmemo_code");
                      var payee_code =  baseResponse.getElementsByTagName("payee_code");
                      
                      document.forms[0].cmbcheqmemo_code.value=cheqmemo_code[0].firstChild.nodeValue;
                      document.forms[0].cmbpayee_code.value=payee_code[0].firstChild.nodeValue;
                      
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
               clearAll();
           }
       else
           {
               alert("Failed to update values");
           }                                  
    }
function deleteChecking(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       if(flag=="success")
           {   
               alert("Record cancelled Successfully.");
               clearAll();
           }
    }   



var response="";  
var seq=0;
var pagesize = 10;
function loadlist1(){	
	
	 var cheqtype=document.getElementById("cmbcheqmemo_code").value;
	
	
	var url="../../../../../Cheqmemo_payee?command=loadlist1&cheqtype="+cheqtype;
	//alert(url);
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

          var serialCode = response.getElementsByTagName("serial_no")[i].firstChild.nodeValue;
          var cheCode = response.getElementsByTagName("cheqmemo_code")[i].firstChild.nodeValue;
  		var cheDesc = response.getElementsByTagName("cheqmemo_desc")[i].firstChild.nodeValue;		
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
        	sch_id.value="&serial_no1="+serialCode;	       
        	td.appendChild(sch_id);
			tr.appendChild(td);			
		}		
		
		var td3 = document.createElement("TD");
		var tides = document.createTextNode(serialCode);
		td3.appendChild(tides);
		tr.appendChild(td3);
		
		var td4 = document.createElement("TD");
		var tides = document.createTextNode(cheDesc);
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
			vartab.appendChild(td3);
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
	// alert("jid "+jid);
	 url="../../../../../Cheqmemo_payee?command=edit"+jid;
	 //alert("edit "+url);
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
	// alert(res);
	 	if(res=="success"){ 	
	 		
	 		var checde=response.getElementsByTagName("cheqmemo_code")[0].firstChild.nodeValue;
	 		var chede=response.getElementsByTagName("cheqmemo_desc")[0].firstChild.nodeValue;	
	 		
	 		var payc=response.getElementsByTagName("payee_code")[0].firstChild.nodeValue;
	 		var paydesc=response.getElementsByTagName("payee_desc")[0].firstChild.nodeValue;	
	 		//alert(checde+"  "+chede+" "+payc+"  "+paydesc);
	 		
	 		document.formcheqPayeeType_Master.cmbcheqmemo_code.value=checde;	
	 		document.formcheqPayeeType_Master.cmbpayee_code.value=payc;	
	 		
	 		//document.getElementById('cmbcheqmemo_code').value=checde;
	 		//document.getElementById('cmbpayee_code').value=payc;
	 		
	 		
	 		//document.getElementById('cmbcheqmemo_code').selectedIndex.value=response.getElementsByTagName("cheqmemo_code")[0].firstChild.nodeValue;
	 		//document.getElementById('cmbcheqmemo_code').selectedIndex.text=response.getElementsByTagName("cheqmemo_desc")[0].firstChild.nodeValue;	
	 		
	 		//document.getElementById('cmbpayee_code').selectedIndex.value=response.getElementsByTagName("payee_code")[0].firstChild.nodeValue;
	 		//document.getElementById('cmbpayee_code').selectedIndex.text=response.getElementsByTagName("payee_desc")[0].firstChild.nodeValue;	
	 		
	 	}else{
	 		alert("Process Failure");
	 	}
	 	document.getElementById('butAdd').disabled=true;
	 	document.getElementById('butUpdate').disabled=false;
	 	document.getElementById('butDelete').disabled=false;
}
 
  