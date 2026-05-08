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


//This Coding for Date Validation and Checking     
function numInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=35 && unicode !=36 )
        {
             if (unicode<48||unicode>57 ) 
                return false 
        }
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
function numFloatInt(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(t.value.indexOf('.') != -1)alert('YES !');
        if((unicode == 8) || (unicode == 46) || (unicode == 37) || (unicode == 39)) 
        {
        	return true;
        	/*  8  --> Backspace
 			    46 --> Delete
 			    37 --> Left arrow
 			    39 --> Right arrow
        	 */
        }
        else
        {
	        var decimalPointIndex = (t.value.indexOf('.'));
	        if (unicode == 46)
	        {            
	            if (decimalPointIndex>=0)
	            return false;
	        }
	        else if (unicode<48 || unicode>57)
	        {
	            return false; 
	        }
        }
}


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
	if(document.getElementById('surveyReportDate').value==""){
		alert("Please Enter Survey Report Date");
		return false;
	}
	if(document.getElementById('assetSelectAuction').value=="select"){
		alert("Please Select Asset Selected for Auction");
		return false;
	}
	if(document.getElementById('auctionedOffice').value=="select"){
		alert("Please Select Auctioned Office");
		return false;
	}
	return true;
}

//******************************************** CallServer Coding *******************//
function callServer(command){  
       var url="";
       var accountId=document.getElementById('cmbAcc_UnitCode').value;
       var officeId=document.getElementById('cmbOffice_code').value;
       //var financialYear=document.getElementById('cmbFinancial_Year').value;
       var surveyNo=document.getElementById('surveyReportNo').value;
       //var surveyDate=document.getElementById('surveyReportDate').value;
       //var auction=document.getElementById('assetSelectAuction').value;
       //var auctionedOffice=document.getElementById('auctionedOffice').value;
       if(command=="Add"){              
    	   			var flag=checknull();
                    if(flag==true){
                    url="../../../../../AuctionAsset?command=add&accountId="+accountId+"&officeId="+officeId+
                    "&financialYear="+financialYear+"&surveyNo="+surveyNo+"&surveyDate="+surveyDate+
                    "&auction="+auction+"&auctionOffice="+auctionedOffice;
                    var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }
         }
        else if(command=="Update")
        {
                    var flag=nullCheck() ;
                    if(flag==true){
                    url="../../../../../AuctionAsset?command=add";
                    var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
                    }
        }
        else if(command=="assetCode"){
        			var unitCode=document.getElementById('cmbAcc_UnitCode').value;
        			var officeCode=document.getElementById('cmbOffice_code').value;
        			var surveyNo=document.getElementById('surveyReportNo').value;
        			url="../../../../../AuctionAsset?command=assetcode&unitCode="+unitCode+"&officeCode="+officeCode+"&surveyNo="+surveyNo;
        			var req=getTransport();
                    req.open("POST",url,true);        
                    req.onreadystatechange=function()
                    {
                    	getassetCode(req);
                    };   
                    req.send(null);
        }
        else if(command=="getoffice"){        		
        		url="../../../../../AuctionAsset?command=getOffice";
            	var req=getTransport();
		        req.open("POST",url,true);  
		        req.onreadystatechange=function(){
		            getOfficeId(req);
		        };   
		        req.send(null);
        }
}  
function surveyReportNum(){	
	var unitCode=document.getElementById('cmbAcc_UnitCode').value;
	var officeCode=document.getElementById('cmbOffice_code').value;
	var url="";
	url="../../../../../AuctionAsset?command=surveyNo&unitCode="+unitCode+"&officeCode="+officeCode;
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
					var selectdiv=document.getElementById('assetAuctioin');
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
function addRow(){
       var flag=true;
       if(flag)
       {
               var items=new Array();
               items[0]=document.getElementById("assetAuctioin").value;
               items[1]=document.getElementById("assetAuctioin").options[document.getElementById("assetAuctioin").selectedIndex].text;
               items[2]=document.getElementById("referenceNo").value;
               items[3]=document.getElementById("referenceDate").value;    
               items[4]=document.getElementById("auctioneer").value;
               items[5]=document.getElementById("auctionAmount").value;
               if(document.getElementById("remarks").value==""){
                    items[6]="";   
               }else{
                    items[6]=document.getElementById("remarks").value;   
               }              
       var tbody=document.getElementById("grid_body");
       var t=0;
       var mycurrent_row=document.createElement("TR");
       seq=seq+1;
       mycurrent_row.id=seq;
        
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0;
        var cell2;
        
        cell2=document.createElement("TD");            
        var H_seqno=document.createElement("input");
	    H_seqno.type="hidden";
	    H_seqno.name="h_seqno";
	    H_seqno.value=tbody.rows.length+1;
        cell2.appendChild(H_seqno);
        
        cell2=document.createElement("TD");
        var H_purposeOR=document.createElement("input");
	    H_purposeOR.type="hidden";
	    H_purposeOR.name="h_assetcode";
	    H_purposeOR.value=items[0];
	    cell2.appendChild(H_purposeOR);
        var currentText=document.createTextNode(items[1]);
	    cell2.appendChild(currentText);
	    mycurrent_row.appendChild(cell2);
	            
	    cell2=document.createElement("TD");	    
	    var H_connection_type=document.createElement("input");
	    H_connection_type.type="hidden";
	    H_connection_type.name="h_referenceNo";
	    H_connection_type.value=items[2];
	    cell2.appendChild(H_connection_type);
        var currentText=document.createTextNode(items[2]);
	    cell2.appendChild(currentText);
	    mycurrent_row.appendChild(cell2);
	    
	    cell2=document.createElement("TD");
	    var H_STD_code=document.createElement("input");
	    H_STD_code.type="hidden";
	    H_STD_code.name="h_referenceDate";
	    H_STD_code.value=items[3];
	    cell2.appendChild(H_STD_code);
        var currentText=document.createTextNode(items[3]);
	    cell2.appendChild(currentText);
	    mycurrent_row.appendChild(cell2);
            
        cell2=document.createElement("TD");
        var H_phone_no=document.createElement("input");
	    H_phone_no.type="hidden";
	    H_phone_no.name="h_auctioner";
	    H_phone_no.value=items[4];
	    cell2.appendChild(H_phone_no);
        var currentText=document.createTextNode(items[4]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);
                
        cell2=document.createElement("TD");
        var H_SerProName=document.createElement("input");
	    H_SerProName.type="hidden";
	    H_SerProName.name="h_auctioAmt";
	    H_SerProName.value=items[5];
	    cell2.appendChild(H_SerProName);
        var currentText=document.createTextNode(items[5]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);
        
        cell2=document.createElement("TD");
        var H_SerProName=document.createElement("input");
	    H_SerProName.type="hidden";
	    H_SerProName.name="h_remarks";
	    H_SerProName.value=items[6];
	    cell2.appendChild(H_SerProName);
        var currentText=document.createTextNode(items[6]);
        cell2.appendChild(currentText);
        mycurrent_row.appendChild(cell2);
         
        tbody.appendChild(mycurrent_row);
        clear_details();
        }
}
function updateGrid(){      
        var items=new Array();        
       items[0]=document.getElementById("assetAuctioin").value;
       items[1]=document.getElementById("assetAuctioin").options[document.getElementById("assetAuctioin").selectedIndex].text;
       items[2]=document.getElementById("referenceNo").value;
       items[3]=document.getElementById("referenceDate").value;       
       items[4]=document.getElementById("auctioneer").value;
       items[5]=document.getElementById("auctionAmount").value;
       if(document.getElementById("remarks").value=="")
       {
            items[6]="";   
       }else{
            items[6]=document.getElementById("remarks").value;   
       }
       	alert("values assigned");
        var r=document.getElementById(com_id);
        var rcells=r.cells;
        
        try{rcells.item(1).firstChild.value=items[0];}catch(e){}
        try{rcells.item(1).lastChild.nodeValue=items[1];}catch(e){}
      
        try{rcells.item(2).firstChild.value=items[2];}catch(e){}
        try{rcells.item(2).lastChild.nodeValue=items[2];}catch(e){}
    
        try{rcells.item(3).firstChild.value=items[3];}catch(e){}
        try{rcells.item(3).lastChild.nodeValue=items[3];}catch(e){}
        
        try{rcells.item(4).firstChild.value=items[4];}catch(e){}
        try{rcells.item(4).lastChild.nodeValue=items[4];}catch(e){}
      
        try{rcells.item(5).firstChild.value=items[5];}catch(e){}
         try{rcells.item(5).lastChild.nodeValue=items[5];}catch(e){}
       
        try{rcells.item(6).firstChild.value=items[6];}catch(e){}
        try{rcells.item(6).lastChild.nodeValue=items[6];}catch(e){}
        
        alert("Record Updated");
        clear_details();
}
function deleteRow(){
    if(confirm("Do you want to delete ?")){
	        var tbody=document.getElementById("mytable");
	        var r=document.getElementById(com_id);
	        var ri=r.rowIndex;
	        tbody.deleteRow(ri);
	        clear_details();
	}
}
function clear_details(){
	document.getElementById("assetAuctioin").selectedIndex=0;
	document.getElementById("referenceNo").value="";
	document.getElementById("referenceDate").value="";
	document.getElementById("auctioneer").value=""; 
	document.getElementById("auctionAmount").value="";
	document.getElementById("remarks").value="";	
        
    document.auctionAssetDone.cmdadd.style.display='block';
	document.auctionAssetDone.cmdupdate.style.display='none';
	document.auctionAssetDone.cmddelete.disabled=true;
}
function loadTable(scod){		
        //com_id=parseInt(scod)+1;
		com_id=scod;
        //clearall();
        alert("com_id "+com_id);
        var r=document.getElementById(com_id);
        var rcells=r.cells;
       
        try {document.getElementById("assetAuctioin").value=rcells.item(1).firstChild.value;}catch(e){}
        //alert(rcells.item(1).firstChild.value);
        //alert(document.getElementById("cmb_purpose").value);
        try{document.getElementById("referenceNo").value=rcells.item(2).firstChild.value; } catch(e){}
        //alert(document.getElementById("cmb_connection_type").value);
        try{document.getElementById("referenceDate").value=rcells.item(3).firstChild.value;} catch(e){} 
        //alert(document.getElementById("txtSTD_code").value);
         try{document.getElementById("auctioneer").value=rcells.item(4).firstChild.value;} catch(e){} 
         //alert(document.getElementById("txtphone_no").value);
         try{document.getElementById("auctionAmount").value=rcells.item(5).firstChild.value;} catch(e){} 
        //alert(document.getElementById("txtSerProName").value);
         try{document.getElementById("remarks").value=rcells.item(6).firstChild.value;} catch(e){} 
         //alert(document.getElementById("txtSerProType").value);
       document.auctionAssetDone.cmdadd.style.display='none';
	   document.auctionAssetDone.cmdupdate.style.display='block';
	   document.auctionAssetDone.cmddelete.disabled=false;
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
    auction_list_SL=window.open("../jsps/auctionAssetsDoneList.jsp?accId="+accId+"&offId="+offId,"AuctionAsset","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
	var surveyNo=document.getElementById('surveyReportNo').value;
	var assetAuctioin=document.getElementById('assetAuctioin').value;
	url="../../../../../AuctionAssetDone?command=Get&unitCode="+unitCode+"&officeCode="+officeCode
		+"&surveyNo="+surveyNo+"&assetAuctioin="+assetAuctioin+"&command="+command;	
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
		var auctionno = response.getElementsByTagName("AUCTION_NO")[i].firstChild.nodeValue;
		var assetcode=response.getElementsByTagName("ASSET_CODE")[i].firstChild.nodeValue;
		var refno=response.getElementsByTagName("REF_NO")[i].firstChild.nodeValue;
		var refdate=response.getElementsByTagName("REF_DATE")[i].firstChild.nodeValue;
		var auctner=response.getElementsByTagName("AUCTIONER")[i].firstChild.nodeValue;
		var auctionamt=response.getElementsByTagName("AUCTION_AMOUNT")[i].firstChild.nodeValue;
		var remarks=response.getElementsByTagName("REMARKS")[i].firstChild.nodeValue;
		var tr = document.createElement("TR");
		tr.id = seq;
		var td = document.createElement("TD");
		var tid1 = document.createTextNode(surveyno);
		td.appendChild(tid1);
		tr.appendChild(td);
		
		var td1 = document.createElement("TD");
		var tid = document.createTextNode(auctionno);
		td1.appendChild(tid);
		tr.appendChild(td1);
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
		var tid4 = document.createTextNode(auctner);
		td6.appendChild(tid4);
		tr.appendChild(td6);
		var td7 = document.createElement("TD");
		var tid5 = document.createTextNode(auctionamt);
		td7.appendChild(tid5);
		tr.appendChild(td7);
		var td8 = document.createElement("TD");
		var tid6 = document.createTextNode(remarks);
		td8.appendChild(tid6);
		tr.appendChild(td8);		
		if(brow=="iex"){
			var vartab = tlist.insertRow(-1);			
			vartab.appendChild(td);
			vartab.appendChild(td1);
			vartab.appendChild(td4);
			vartab.appendChild(td2);			
			vartab.appendChild(td5);
			vartab.appendChild(td6);
			vartab.appendChild(td7);
			vartab.appendChild(td8);			
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
             iframe.innerHTML="<tr><td align=center colspan=8>There is No Data to Display</td></tr>";             
		 } else{
			 iframe.innerText="There is No Data to Display";
	         iframe.innerHTML="<tr><td align=center colspan=8>There is No Data to Display</td></tr>";			 
		 }
             
	}				
	
}
