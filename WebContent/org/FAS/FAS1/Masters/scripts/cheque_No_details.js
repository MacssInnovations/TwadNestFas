var Common_branchID="";
var pagesize = 10;
var seq = 0;
var common = "";
var length = 0;
var flag, command, response="",chequecode="";
/** 
 * Browser Indentification 
 */
 
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
	var len = response.getElementsByTagName("chequeNo").length;	
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
	var len = response.getElementsByTagName("chequeNo").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{
		var chequeleaf=document.getElementById('startEndLeaf').value;
		var code=new Array();
		code=chequeleaf.split(',');		
		chequecode=code[0];
		document.getElementById("div1").style.visibility='visible';
		document.getElementById("div2").style.visibility='visible';
		document.getElementById('startCheque').value=response.getElementsByTagName("startchequeno")[0].firstChild.nodeValue;
		document.getElementById('startDate').value=response.getElementsByTagName("startdate")[0].firstChild.nodeValue;
		document.getElementById('endCheque').value=response.getElementsByTagName("endchequeno")[0].firstChild.nodeValue;
		document.getElementById('endDate').value=response.getElementsByTagName("enddate")[0].firstChild.nodeValue;
	for ( var i = ll; i < ul; i++) {
		var startno = response.getElementsByTagName("startNo")[i].firstChild.nodeValue;
		var endno = response.getElementsByTagName("endNo")[i].firstChild.nodeValue;
		
		var chequeno=response.getElementsByTagName("chequeNo")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq;
		var td0 = document.createElement("TD");
		var tid0 = document.createTextNode(chequecode);
		td0.appendChild(tid0);
		tr.appendChild(td0);
		var td1 = document.createElement("TD");
		var tides = document.createTextNode(startno);
		td1.appendChild(tides);
		tr.appendChild(td1);
		var td2 = document.createElement("TD");
		var tid = document.createTextNode(endno);
		td2.appendChild(tid);
		tr.appendChild(td2);
		var td3 = document.createElement("TD");
		var tides = document.createTextNode(chequeno);
		td3.appendChild(tides);
		tr.appendChild(td3);		
		if(brow=="iex"){
			var vartab = tlist.insertRow(-1);
			vartab.appendChild(td0);
			vartab.appendChild(td1);
			vartab.appendChild(td2);
			vartab.appendChild(td3);			
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
             iframe.innerHTML="<tr><td align=center colspan=4>There is No Data to Display</td></tr>";
             /*document.professionTax.dateEffectiveFrom.value="";
        	 document.professionTax.startPay.value="";*/
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=4>There is No Data to Display</td></tr>";			 
		 }
             
	}				
	
}
function ClearAll(){
    document.getElementById("cmbOffice_code").selectedIndex=0;
    document.getElementById("cmbOffice_code").length=1;        
    cleargrid();
}
function cleargrid(){
    document.getElementById("txtBankAccountNo").selectedIndex=0;    
    document.getElementById("startEndLeaf").selectedIndex=0;
    document.getElementById("startEndLeaf").length=1;    
	try {
		document.getElementById("tblList").innerHTML = "";
	} catch (e) {
		document.getElementById("tblList").innerText = "";
	}
	document.getElementById("div1").style.visibility='hidden';
	document.getElementById("div2").style.visibility='hidden';
    
}

function exit()
{
    self.close();
}

function chequeRange(){
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var accountNo=document.getElementById('txtBankAccountNo').value;
	var url="../../../../../Cheque_Number_details?command=chequeRange&accunitId="+accunitId+"&accountNo="+accountNo;
  // alert(url);
	var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function(){
    	loadCombo(req);
    };   
    req.send(null);
}
function loadCombo(req){
	 if(req.readyState==4){ 
        if(req.status==200){
       	response=req.responseXML.getElementsByTagName("response")[0]; 
       	chequeCombo();
        }
    }
}
function chequeCombo(){
	 var len1 = response.getElementsByTagName("leafvalue").length;	
		var select=document.getElementById('startEndLeaf');
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){			
			listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("leafdesc")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("chequecode")[i].firstChild.nodeValue+","+response.getElementsByTagName("leafvalue")[i].firstChild.nodeValue;
			}
		}else if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="fail"){
			//alert("Fail");
			try {
				document.getElementById("tblList").innerHTML = "";
			} catch (e) {
				document.getElementById("tblList").innerText = "";
			}
			document.getElementById("div1").style.visibility='hidden';
			document.getElementById("div2").style.visibility='hidden';
			alert("Cheque not found for this Account number");
		}
}
function showMis(){
	var tlist = document.getElementById("tblList");
	try {
		tlist.innerHTML = "";
	} catch (e) {
		tlist.innerText = "";
	}
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var accountNo=document.getElementById('txtBankAccountNo').value;
	var startEndLeaf=document.getElementById('startEndLeaf').value;
	var asonDate="";
	var txtCB_Year="";
	var txtCB_Month="";
	if(document.getElementById('txtfromdate').value!=""){
		asonDate=document.getElementById('txtfromdate').value;
		txtCB_Year="";
		txtCB_Month="";
		var url="../../../../../Cheque_Number_details?cmbAcc_UnitCode="+accunitId+"&check=asondate"+
		"&txtBankAccountNo="+accountNo+"&startEndLeaf="+startEndLeaf+"&asonDate="+asonDate;
	}else{
		asonDate="";
		txtCB_Year=document.getElementById('txtCB_Year').value;
		txtCB_Month=document.getElementById('txtCB_Month').value;
		var url="../../../../../Cheque_Number_details?cmbAcc_UnitCode="+accunitId+"&txtCB_Year="+txtCB_Year+
		"&txtCB_Month="+txtCB_Month+"&txtBankAccountNo="+accountNo+"&startEndLeaf="+startEndLeaf+"&check=formonth";
               
	}
	/*alert("alert1 "+asonDate);
	alert("alert2 year "+txtCB_Year+" month "+txtCB_Month);*/	
    var req=getTransport();
    req.open("POST",url,true); 
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
        }
    }
}
function cb_month_year(id){		
	ClearAll();
   var particular=document.getElementById("particular");
   var more=document.getElementById("more");   
  if(id=="particular_cb"){
	  var currentTime = new Date();
	  var month = currentTime.getMonth() + 1;
	  var day = currentTime.getDate();
	  var year = currentTime.getFullYear();
	  if(day<9){
		  day="0"+day;
	  }
	 document.getElementById("txtfromdate").value=day + "/" + month + "/" + year;	 
     particular.style.display="block";
     more.style.display="none";
  }
  if(id=="more_cb"){
    more.style.display="block";
    particular.style.display="none";
    document.getElementById("txtfromdate").value="";
  }
}
function loadAccountingUnitID1(COMMAND){
        command_for_office = COMMAND;
        var url="../../../../../Load_Accounting_Unit_ID.kv?COMMAND="+COMMAND;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function(){
          handleLoadAccountingUnitID(req);
        };        
        req.send(null);
    
}
function handleLoadAccountingUnitID(req){
    if(req.readyState==4){
     if(req.status==200){
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(flag=="success"){ 
            var cmbAcc_UnitCode = document.getElementById("cmbAcc_UnitCode");         
                cmbAcc_UnitCode.length=0;
            var option_count=baseresponse.getElementsByTagName("option");                       
            var root = null;
            for(var i=0;i<option_count.length;i++){  
                var option=document.createElement("OPTION");
                root = baseresponse.getElementsByTagName("option")[i];
                var accounting_unit_id=root.getElementsByTagName("accounting_unit_id")[0].firstChild.nodeValue;
                var accounting_unit_name=root.getElementsByTagName("accounting_unit_name")[0].firstChild.nodeValue;
                option.text=accounting_unit_name;
                option.value=accounting_unit_id;
                try{   
                    cmbAcc_UnitCode.add(option);
                }catch(errorObject){
                    cmbAcc_UnitCode.add(option,null);
                }   
            }            
            /** Load Accounting Office ID */ 
            if ( (command_for_office == "ONLY_UNITS") || (command_for_office=="LIST_ALL_UNITS_ONLY") || (command_for_office=="FOR_LIST_0" ) ){
            
            }else{               
            	commonLoadOffice1();            
            }         
        }else{
          alert("Failed to Load Accounting Unit");
        }
     }
    }
}
function commonLoadOffice1(){
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;        
    cleargrid();
    if(unitID_val!=""){
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function(){
            handleLoadOffice1(req);
        };
        req.send(null);
    }     
}
function handleLoadOffice1(req){  
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
                }catch(errorObject ){
                    cmboffice.add(option,null);
                }   
            }
            
         }catch(err){
            alert("Problem in Loading Office code ");
         }
            
        }else{
         try{
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try{
                cmboffice.add(option);
            }catch(errorObject ){
                cmboffice.add(option,null);
            }
         }catch(err){
            alert("Problem in Loading Office code ");
         }         
            
        }
     }
    }
    var officeId=cmboffice.value;
    loadBankAccountNo(officeId);
}
function loadBankAccountNo(officeId){
	var accunitId=document.getElementById('cmbAcc_UnitCode').value;
	var accountNo=document.getElementById('txtBankAccountNo').value;
	var url="../../../../../Cheque_Number_details?command=bankaccNo&accunitId="+accunitId+"&accountNo="+accountNo;
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function(){
    	loadBankCombo(req);
    };   
    req.send(null);
}
function loadBankCombo(req){
	 if(req.readyState==4){ 
       if(req.status==200){
      	response=req.responseXML.getElementsByTagName("response")[0]; 
      	bankCombo();
       }
   }
}
function bankCombo(){
	 var len1 = response.getElementsByTagName("bankacno").length;	
		var select=document.getElementById('txtBankAccountNo');
		if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success"){			
			listOpt=document.createElement("option");
			select.length=0;
			select.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";	
			for(var i=0; i<len1; i++){
				listOpt=document.createElement("option");
				select.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("bankactype")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("bankacno")[i].firstChild.nodeValue;
			}
		}else{
			//alert("Fail");
		}
}