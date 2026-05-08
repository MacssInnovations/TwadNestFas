/** To handle Errors **/
var com_id;
var seq=0;
var txt="";
var winemp;
var common = "";
var length = 0;
var response="";
var pagesize = 10;

/** To create javascript request object **/
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

function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false; 
        }
     }
//This function allows users to enter Numbers only (with or without decimal places)

 function Exit()
 {
    self.close();
 }
 
 
 
//******************************Validation Checking**************************//
function checknull(){
	if(document.getElementById('surveyReportNo').value=="select"){
		alert("Please Select Survey Report No");
		return false;
	}	
	if(document.getElementById('assetSelectAuction').value=="select"){
		alert("Please Select Asset Selected for Auction");
		return false;
	}
	if(document.getElementById('recoverYear').value==""){
		alert("Please Enter Cash book year");
		return false;
	}
	if(document.getElementById('cashbook').value=="select"){
		alert("Please Select Cashbook receipt No and date");
		return false;
	}
	if(document.getElementById('jvrYear').value==""){
		alert("Please Enter JVR year");
		return false;
	}
	if(document.getElementById('finalAdjust').value=="select"){
		alert("Please Select Journal No and Date");
		return false;
	}
	return true;
}

//******************************************** CallServer Coding *******************//
function callServer(command){  
       var url="";
       var accountId=document.getElementById('cmbAcc_UnitCode').value;
       var officeId=document.getElementById('cmbOffice_code').value;
       var financialYear=document.getElementById('cmbFinancial_Year').value;
       var surveyNo=document.getElementById('surveyReportNo').value;
       var assetAuction=document.getElementById('assetSelectAuction').value;
       var auctionDate=document.getElementById('auctionDate').value;
       var auctionNo=document.getElementById('auctionNo').value;
       var auctionedOffice=document.getElementById('auctionedOffice').value;
       var auctionAmount=document.getElementById('auctionAmount').value;
       var recoverYear=document.getElementById('recoverYear').value;
       var month=document.getElementById('month').value;
       var cashbook=document.getElementById('cashbook').value;
       var jvrYear=document.getElementById('jvrYear').value;
       var jvrmonth=document.getElementById('jvrmonth').value;
       var finalAdjust=document.getElementById('finalAdjust').value;
       if(command=="Add"){
    	   			var flag=checknull();
                    if(flag==true){
                    url="../../../../../AuctionAssetVoucher?command=add&accountId="+accountId+"&officeId="+officeId+
                    "&financialYear="+financialYear+"&surveyNo="+surveyNo+"&assetAuction="+assetAuction+
                    "&auctionDate="+auctionDate+"&auctionNo="+auctionNo+"&auctionedOffice="+auctionedOffice+
                    "&auctionAmount="+auctionAmount+"&recoverYear="+recoverYear+"&month="+month+
                    "&cashbook="+cashbook+"&jvrYear="+jvrYear+"&jvrmonth="+jvrmonth+"&finalAdjust="+finalAdjust;
                    var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function(){
                       processResponse(req);
                    };   
                    req.send(null);
                    }
         }else if(command=="assetCode"){
        			var unitCode=document.getElementById('cmbAcc_UnitCode').value;
        			var officeCode=document.getElementById('cmbOffice_code').value;
        			var surveyNo=document.getElementById('surveyReportNo').value;
        			if(surveyNo=="select"){
        				var selectdiv=document.getElementById('assetSelectAuction');
        				var listOpt=document.createElement("option");
        				selectdiv.length=1;
        				selectdiv.selectedIndex=0;
        			}else{
        				url="../../../../../AuctionAssetVoucher?command=assetcode&unitCode="+unitCode+"&officeCode="+officeCode+"&surveyNo="+surveyNo;
            			var req=getTransport();
                        req.open("POST",url,true);        
                        req.onreadystatechange=function()
                        {
                        	getassetCode(req);
                        };   
                        req.send(null);
        			}
        			
        }else if(command=="getoffice"){        		
        		url="../../../../../AuctionAsset?command=getOffice";
            	var req=getTransport();
		        req.open("POST",url,true);  
		        req.onreadystatechange=function(){
		            getOfficeId(req);
		        };   
		        req.send(null);
        }else if(command=="auctionDetail"){
			var unitCode=document.getElementById('cmbAcc_UnitCode').value;
			var officeCode=document.getElementById('cmbOffice_code').value;
			var surveyNo=document.getElementById('surveyReportNo').value;
			var assetcode=document.getElementById('assetSelectAuction').value;
			//alert("assetcode "+assetcode);
			if(surveyNo=="select" && assetcode=="select"){
				var selectdiv=document.getElementById('assetSelectAuction');
				var listOpt=document.createElement("option");
				selectdiv.length=1;
				selectdiv.selectedIndex=0;
			}else{
				url="../../../../../AuctionAssetVoucher?command=auctionDetail&unitCode="+unitCode+
				"&officeCode="+officeCode+"&surveyNo="+surveyNo+"&assetcode="+assetcode;
    			var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function()
                {
                	getassetCode(req);
                };   
                req.send(null);
			}
			
        }else if(command=="cashReceipt"){
        	var unitCode=document.getElementById('cmbAcc_UnitCode').value;
			var officeCode=document.getElementById('cmbOffice_code').value;
			var year=document.getElementById('recoverYear').value;
			var month=document.getElementById('month').value;
			document.getElementById('cashbook').length=1;
			document.getElementById('cashbook').selectIndex=0;
			if(year==""){
				document.getElementById('cashbook').length=1;
				document.getElementById('cashbook').selectIndex=0;
			}else{
				url="../../../../../AuctionAssetVoucher?command=cashReceipt&unitCode="+unitCode+
				"&officeCode="+officeCode+"&year="+year+"&month="+month+"&check=cash";
				var req=getTransport();
	            req.open("POST",url,true);        
	            req.onreadystatechange=function()
	            {
	            	getassetCode(req);
	            };   
	            req.send(null);
			}			
        }else if(command=="journalreceipt"){
        	var unitCode=document.getElementById('cmbAcc_UnitCode').value;
			var officeCode=document.getElementById('cmbOffice_code').value;
			var year=document.getElementById('jvrYear').value;
			var month=document.getElementById('jvrmonth').value;
			document.getElementById('finalAdjust').length=1;
			document.getElementById('finalAdjust').selectIndex=0;
			if(year==""){
				document.getElementById('finalAdjust').length=1;
				document.getElementById('finalAdjust').selectIndex=0;
			}else{
				url="../../../../../AuctionAssetVoucher?command=cashReceipt&unitCode="+unitCode+
				"&officeCode="+officeCode+"&year="+year+"&month="+month+"&check=journal";
				var req=getTransport();
	            req.open("POST",url,true);        
	            req.onreadystatechange=function()
	            {
	            	getassetCode(req);
	            };   
	            req.send(null);
			}			
        }
}  
function surveyReportNum(){	
	var unitCode=document.getElementById('cmbAcc_UnitCode').value;
	var officeCode=document.getElementById('cmbOffice_code').value;
	var url="";
	url="../../../../../AuctionAssetVoucher?command=surveyNo&unitCode="+unitCode+"&officeCode="+officeCode;
	var req=getTransport();
    req.open("POST",url,true);  
    req.onreadystatechange=function(){
        getSurveyNo(req);
    };   
    req.send(null);
}

//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
  if(req.readyState==4)
  {
      if(req.status==200)
      {   

    	  response=req.responseXML.getElementsByTagName("response")[0];	  
          var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;                    
    	  var flag=response.getElementsByTagName("status")[0].firstChild.nodeValue;    	       
              if(command=="add"){
            	  if(flag=="success"){
            		  var status=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            		  if(status=="success"){
            			  alert("Record Inserted into Database successfully.");
            		  }else{
            			  alert("Record Not Inserted into Database");
            		  }            		  
            	  }else{
            		  alert("Failed to Insert the record.");
                  }
            	  clearAll();
              }
              
              else if(command=="Delete")
              { 
            	  if(flag=="success")
            	  {
            		  alert("Record Deleted from Database Successfully.");
            		  clearDetails();
            	  }
            	  else
                  {
            		  alert("Failed to Insert the record.");
                  }
              }
              
              else if(command=="Update")
              {
            	  if(flag=="success")
            	  {
            		  alert("Record Updated into Database Successfully.");
            	  }
            	  else
                  {
            		  alert("Failed to Update the record.");
                  }
              }            

    	  }
     }
}
function getOfficeId(req){
	if(req.readyState==4){
	      if(req.status==200){
	    	  	response = req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;
				if(command=="getOffice"){
					var selectdiv=document.getElementById('auctionedOffice');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="Select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Accounting office is Not Found for this Accounting unit ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag=="success"){
						var len=response.getElementsByTagName("OFFICE_ID").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							listOpt.text=response.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
							listOpt.value=response.getElementsByTagName("OFFICE_ID")[i].firstChild.nodeValue;
						}
					}
				}				
	      }
	  }
}
function getSurveyNo(req){
	response = req.responseXML.getElementsByTagName("response")[0];
	var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(command=="surveyNo"){
		var selectdiv=document.getElementById('surveyReportNo');
		var listOpt=document.createElement("option");		
		selectdiv.length=0;
		selectdiv.appendChild(listOpt);
		listOpt.text="Select";
		listOpt.value="select";		
		if (flag=="nodata"){
			alert("Sorry! Survey Report No is Not Found for this Accounting unit ");
			selectdiv.selectedIndex=0;
			selectdiv.length=1;
		}else if(flag=="success"){
			var len=response.getElementsByTagName("SURVEY_NO").length;						
			for(var i=0; i<len; i++){
				listOpt=document.createElement("option");
				selectdiv.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("SURVEY_NO")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("SURVEY_NO")[i].firstChild.nodeValue;				
			}
		}
	}
}
function getassetCode(req){
	if(req.readyState==4){
	      if(req.status==200){
	    	  	response = req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;
				if(command=="assetcode"){
					var selectdiv=document.getElementById('assetSelectAuction');
					var listOpt=document.createElement("option");
					selectdiv.length=0;
					selectdiv.appendChild(listOpt);
					listOpt.text="Select";
					listOpt.value="select";
					if (flag == "nodata"){
						alert("Sorry! Asset Code is Not Found for this Survey No ");
						selectdiv.selectedIndex=0;
						selectdiv.length=1;
					}else if(flag=="success"){
						var len=response.getElementsByTagName("ASSET_CODE").length;						
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							listOpt.text=response.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;
							listOpt.value=response.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;
						}
					}
				}else if(command=="assetdetails"){
					flag=response.getElementsByTagName("status")[0].firstChild.nodeValue;
					if(flag=="success"){
						document.getElementById('auctionDate').value=response.getElementsByTagName("AUCTION_DATE")[0].firstChild.nodeValue;
						document.getElementById('auctionNo').value=response.getElementsByTagName("AUCTION_NO")[0].firstChild.nodeValue;
						document.getElementById('auctionedOffice').value=response.getElementsByTagName("AUCTIONED_AT_OFFICE")[0].firstChild.nodeValue;
						document.getElementById('auctionAmount').value=response.getElementsByTagName("AUCTION_AMOUNT")[0].firstChild.nodeValue;
						document.getElementById('recoverYear').focus();
					}else{
						alert("Sorry! No data for this Asset Code");
					}
				}else if(command=="cashreceipt"){
					flag=response.getElementsByTagName("status")[0].firstChild.nodeValue;
					if(flag=="success"){
						var len=response.getElementsByTagName("RECEIPT_NO").length;
						var selectdiv=document.getElementById('cashbook');
						var listOpt=document.createElement("option");
						selectdiv.length=0;
						selectdiv.appendChild(listOpt);
						listOpt.text="Select";
						listOpt.value="select";
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							listOpt.text="No "+response.getElementsByTagName("RECEIPT_NO")[i].firstChild.nodeValue+
							" Date: "+response.getElementsByTagName("RECEIPT_DATE")[i].firstChild.nodeValue;
							listOpt.value=response.getElementsByTagName("RECEIPT_NO")[i].firstChild.nodeValue+
							":"+response.getElementsByTagName("RECEIPT_DATE")[i].firstChild.nodeValue;
						}
					}else{
						alert("Sorry! No data for this Year and Month");
					}
				}else if(command=="journalreceipt"){
					flag=response.getElementsByTagName("status")[0].firstChild.nodeValue;
					if(flag=="success"){
						var len=response.getElementsByTagName("RECEIPT_NO").length;
						var selectdiv=document.getElementById('finalAdjust');
						var listOpt=document.createElement("option");
						selectdiv.length=0;
						selectdiv.appendChild(listOpt);
						listOpt.text="Select";
						listOpt.value="select";
						for(var i=0; i<len; i++){
							listOpt=document.createElement("option");
							selectdiv.appendChild(listOpt);
							listOpt.text="No: "+response.getElementsByTagName("RECEIPT_NO")[i].firstChild.nodeValue+
							" Date: "+response.getElementsByTagName("RECEIPT_DATE")[i].firstChild.nodeValue;
							listOpt.value=response.getElementsByTagName("RECEIPT_NO")[i].firstChild.nodeValue+
							":"+response.getElementsByTagName("RECEIPT_DATE")[i].firstChild.nodeValue;
						}
					}else{
						alert("Sorry! No data for this Year and Month");
					}
				}
	      }
	  }
}
function loadyear_month(){
 var today = new Date();
	var day = today.getDate();
	var month = today.getMonth();
	month = month + 1;
	var year = today.getYear();
	var year1 = 0;
	var financialyear = 0;
	var financialyear1 = 0;
	if (year < 1900)
		year += 1900;
	if (month < 4) {
		year1 = year - 1;
	} else {
		year1 = year + 1;
	}

	if (month < 4) {
		financialyear = year1 + "-" + year;
		financialyear1 = (year1-1) + "-" + (year-1);
	} else {
		financialyear = year + "-" + year1;
		financialyear1 = (year-1) + "-" + (year1-1);
	}
	for(var k=0;k<2;k++)
	{
		if(k==0)
		{
			var se = document.getElementById("cmbFinancial_Year");
	  		var op = document.createElement("OPTION");
	  		op.value = financialyear1;
	  		var txt = document.createTextNode(financialyear1);
	  		op.appendChild(txt);
	  		se.appendChild(op);
		}else if(k==1)
		{
			var se = document.getElementById("cmbFinancial_Year");
	  		var op = document.createElement("OPTION");
	  		op.value = financialyear;
	  		var txt = document.createTextNode(financialyear);
	  		op.appendChild(txt);
	  		se.appendChild(op);
	  		
		}                           
	}    
	document.getElementById("cmbFinancial_Year").value=financialyear;          
}

function clearAll(){
	document.getElementById("surveyReportNo").selectedIndex=0;
	document.getElementById("assetSelectAuction").selectedIndex=0;
	document.getElementById("assetSelectAuction").length=1;	
	document.getElementById("cashbook").selectedIndex=0;
	document.getElementById("cashbook").length=1;
	document.getElementById("finalAdjust").selectedIndex=0;
	document.getElementById("finalAdjust").length=1;
	document.getElementById("auctionDate").value="";
	document.getElementById("auctionNo").value="";
	document.getElementById("auctionedOffice").value="";
	document.getElementById("auctionAmount").value="";
}
var auction_list_SL;
function auctionAssetList(){
	var accId=document.getElementById('cmbAcc_UnitCode').value;
	var offId=document.getElementById('cmbOffice_code').value;
    if (auction_list_SL && auction_list_SL.open && !auction_list_SL.closed){
    	auction_list_SL.resizeTo(500,500);
    	auction_list_SL.moveTo(250,250); 
    	auction_list_SL.focus();
    }else{
    	auction_list_SL=null;
    }
    auction_list_SL=window.open("../jsps/auctionAssetVoucherList.jsp?accId="+accId+"&offId="+offId,"AuctionAsset","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    auction_list_SL.moveTo(250,250);  
    auction_list_SL.focus();
    
}

window.onunload=function(){
    if (auction_list_SL && auction_list_SL.open && !auction_list_SL.closed) 
        auction_list_SL.close();
};

function getAuctionDetails(command){
	var unitCode=document.getElementById('cmbAcc_UnitCode').value;
	var officeCode=document.getElementById('cmbOffice_code').value;	
	url="../../../../../AuctionAssetVoucher?command=Get&unitCode="+unitCode+"&officeCode="+officeCode;
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
	var len = response.getElementsByTagName("SURVEY_NO").length;	
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
	var len = response.getElementsByTagName("SURVEY_NO").length;	
	if(response.getElementsByTagName("status")[0].firstChild.nodeValue=="success")
		{
	var pageno = document.getElementById("cmbpage").value;
	var ul = 0, ll = 0;
	ul = pageno * pagesize;
	ll = ul - pagesize;
	try{		
	for ( var i = ll; i < ul; i++) {
		var surveyno = response.getElementsByTagName("SURVEY_NO")[i].firstChild.nodeValue;		
		var assetcode=response.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;
		var refno=response.getElementsByTagName("REF_NO")[i].firstChild.nodeValue;
		var refdate=response.getElementsByTagName("REF_DATE")[i].firstChild.nodeValue;
		var journalno=response.getElementsByTagName("JOURNAL_NO")[i].firstChild.nodeValue;
		var journaldat=response.getElementsByTagName("JOURNAL_DATE")[i].firstChild.nodeValue;
		var status=response.getElementsByTagName("STATUS")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");
		var tid1 = document.createTextNode(surveyno);
		td.appendChild(tid1);
		tr.appendChild(td);
		
		var td4 = document.createElement("TD");
		var tid2 = document.createTextNode(assetcode);
		td4.appendChild(tid2);
		tr.appendChild(td4);
		var td2 = document.createElement("TD");
		var tid3 = document.createTextNode(refno);
		td2.appendChild(tid3);
		tr.appendChild(td2);		
		var td5 = document.createElement("TD");
		var tdst = document.createTextNode(refdate);
		td5.appendChild(tdst);
		tr.appendChild(td5);
		var td6 = document.createElement("TD");
		var tid4 = document.createTextNode(journalno);
		td6.appendChild(tid4);
		tr.appendChild(td6);
		var td7 = document.createElement("TD");
		var tid5 = document.createTextNode(journaldat);
		td7.appendChild(tid5);
		tr.appendChild(td7);				
		if(brow=="iex"){
			var vartab = tlist.insertRow(-1);			
			vartab.appendChild(td);			
			vartab.appendChild(td4);
			vartab.appendChild(td2);			
			vartab.appendChild(td5);
			vartab.appendChild(td6);
			vartab.appendChild(td7);						
		}else{
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
             iframe.innerHTML="<tr><td align=center colspan=6>There is No Data to Display</td></tr>";             
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=6>There is No Data to Display</td></tr>";			 
		 }
             
	}				
	
}
function year_cal(){
	var d = new Date();
	var curr_date = d.getDate();
	var curr_month = d.getMonth();
	var curr_year = d.getFullYear();
	document.auctionAssetDoneCash.recoverYear.value=curr_year;
	document.auctionAssetDoneCash.jvrYear.value=curr_year;
	loadMonth();
	loadMonthJvr();
}
function loadMonth(){	
	var sales_year=document.auctionAssetDoneCash.recoverYear.value;
	var length=sales_year.length;	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
    var sales_year=document.auctionAssetDoneCash.recoverYear.value;
    var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
    var sales_month=document.getElementById("month");    	
    if(sales_year!=year){
    	 month=12;    	    
    }       
    var child=sales_month.childNodes;
    for(var c=child.length-1;c>0;c--) {
       sales_month.removeChild(child[c]);
   }
    for(i=month-1;i>=0;i--){
        var opt =document.createElement("option"); 
        var text=document.createTextNode(monthNames[i]);        
        opt.setAttribute("value",i+1);
        opt.appendChild(text);
        sales_month.appendChild(opt);       
    }    
}
function loadMonthJvr(){	
	var sales_year=document.auctionAssetDoneCash.jvrYear.value;
	var length=sales_year.length;	
    var dt=new Date();
    var year=dt.getFullYear();
    var month=dt.getMonth();
    month=month+1;
    var sales_year=document.auctionAssetDoneCash.jvrYear.value;
    var monthNames = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
    var sales_month=document.getElementById("jvrmonth");    	
    if(sales_year!=year){
    	 month=12;    	    
    }       
    var child=sales_month.childNodes;
    for(var c=child.length-1;c>0;c--) {
       sales_month.removeChild(child[c]);
   }
    for(i=month-1;i>=0;i--){
        var opt =document.createElement("option"); 
        var text=document.createTextNode(monthNames[i]);        
        opt.setAttribute("value",i+1);
        opt.appendChild(text);
        sales_month.appendChild(opt);       
    }    
}
function check_field(){
	 if(document.getElementById("recoverYear").value=="" || document.getElementById("recoverYear").length==0){
	        alert("Please fill the   Cash Year");
	    }
	 if(document.getElementById("recoverYear").value=="" || document.getElementById("recoverYear").length==0){
	        alert("Please fill the   JVR Year");
	    }
	
}