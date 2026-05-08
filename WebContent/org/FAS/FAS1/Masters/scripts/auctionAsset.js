/** To handle Errors **/
var txt="";
var response="";
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
       var financialYear=document.getElementById('cmbFinancial_Year').value;
       var surveyNo=document.getElementById('surveyReportNo').value;
       var surveyDate=document.getElementById('surveyReportDate').value;
       var auction=document.getElementById('assetSelectAuction').value;
       var auctionedOffice=document.getElementById('auctionedOffice').value;
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