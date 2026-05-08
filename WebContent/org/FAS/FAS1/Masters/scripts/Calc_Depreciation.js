var seq=0;
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
function callServer(command)
{
	if(command=="loadMajor"){
    	url="../../../../../Calc_Depreciation?command=loadMajor";
		var req=getTransport();
        req.open("POST",url,true);  
        req.onreadystatechange=function(){
        	
        	processResponse(req);
        };   
        req.send(null);}
}
	function callDepRate(command){
		var val=document.getElementById("cmbmajorasset").value;
	if(command=="loadDepRate")
		{
	  	url="../../../../../Calc_Depreciation?command=loadDepRate&cmbmajorasset="+val;
		var req=getTransport();
        req.open("POST",url,true);  
        req.onreadystatechange=function(){
        	
        	processResponse(req);
        };   
        req.send(null);}
}



	function processResponse(req)
	{   
	  if(req.readyState==4)
	  {
	      if(req.status==200)
	      {   
	    	  
	    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
	          var tagCommand=baseResponse.getElementsByTagName("command")[0];
	          
	          var command=tagCommand.firstChild.nodeValue; 
	          
	    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	    	    if(command=="loadMajor")
	              {
	        		  var cmbMajorClass = document.getElementById('cmbmajorasset');
	        		  cmbMajorClass.length=0;
	            	  if(flag=="success"){
	            		  
	            		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
	              		  if(exists=="Yes"){
	              		
	            		  var mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE');
	            		  
	                	  var len = mjrCode.length;
	                	  var opt = document.createElement("option");
	                	  opt.value="";
	                	  opt.innerHTML="--Select--";
	                	  cmbMajorClass.appendChild(opt);
	            	  for(i=0; i<len; i++)
	            	  {
	            		  mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE')[i].firstChild.nodeValue;
	            		  var mjrDesc = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_DESC')[i].firstChild.nodeValue;
	            		  var opt = document.createElement("option");
	            		  opt.value = mjrCode;
	            		  opt.innerHTML = mjrDesc;
	            		  
	            		  cmbMajorClass.appendChild(opt);
	            	  }
	              		 }else{
	             			  alert("No Records");
	             		  }
	            	  } else
				        {
					        ("No Major AssetCode in Table");
					     
					        
					        }
	              }
	    	   if(command=="loadDepRate")
	    	    	{
	    	    	var cmbdepRateCode=baseResponse.getElementsByTagName('DEPRECIATION_CATE_CODE')[0].firstChild.nodeValue;
	    	    	var len=cmbdepRateCode.length;
	    	    	if(len==0){
	    	    		alert('No Data !!!!');
	    	    	}
	    	    	if(len!=0){
	    	    	document.getElementById("cmbdepRateCode").value=cmbdepRateCode;
	    	    	var cmbdepRate=baseResponse.getElementsByTagName('DEPRECIATION_RATE')[0].firstChild.nodeValue;
	    	    	document.getElementById("cmbdepRate").innerHTML=cmbdepRate+"%";
	    	    	document.getElementById("cmbdepRateVAl").value=cmbdepRate;
	    	    	}
	    	    	}
	    	   if(command=="Submit")
	    	   {
	    		   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
		    	     if(flag=="success")
		    	    	 {
		    	    	 alert("Updated Successfully");
		    	    	 }
		    	     if(flag=="failure")
		    	    	 {
		    	    	 alert("No Update");
		    	    	 }
	    	   }
	    	   if(command=="listData")
	    		   {
	    		   try{
	    				document.getElementById('tblList').innerHTML="";
	    			}catch(e){
	    			document.getElementById('tblList').innerText="";
	    			}
	    		 try{
	    		 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	    	     if(flag=="success")
	    	       {          
	    	           
	    	           var tbody=document.getElementById('tblList');
	    	                                  
	    	           var len=baseResponse.getElementsByTagName("ASSET_CODE").length;  
	    	           for(var k=0;k<len;k++)
	    	               {
	    	        	        var ASSET_CODE= baseResponse.getElementsByTagName("ASSET_CODE")[k].firstChild.nodeValue;
	    	        	        var PARTICULARS= baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;
	    	        	        var OPEN_BAL_QTY= baseResponse.getElementsByTagName("OPEN_BAL_QTY")[k].firstChild.nodeValue;
	    	        	        var OPENING_BAL_VALUE= baseResponse.getElementsByTagName("OPENING_BAL_VALUE")[k].firstChild.nodeValue;
	    	        	        var totl_amt= baseResponse.getElementsByTagName("totl_amt")[k].firstChild.nodeValue;
	    	        	        var previous_amt= baseResponse.getElementsByTagName("previous_amt")[k].firstChild.nodeValue;
	    	        	        var RECIEPTS_YEAR_QTY= baseResponse.getElementsByTagName("RECIEPTS_YEAR_QTY")[k].firstChild.nodeValue;
	    	        	        var RECIEPTS_YR_VALUE= baseResponse.getElementsByTagName("RECIEPTS_YR_VALUE")[k].firstChild.nodeValue;
	    	        	        var ISSUES_YEAR_QTY= baseResponse.getElementsByTagName("ISSUES_YEAR_QTY")[k].firstChild.nodeValue;
	    	        	        var ISSUES_YR_VALUE= baseResponse.getElementsByTagName("ISSUES_YR_VALUE")[k].firstChild.nodeValue;
	    	        	        var DEP_PREV_YEAR= baseResponse.getElementsByTagName("DEP_PREV_YEAR")[k].firstChild.nodeValue;
	    	        	        var DEPRE_REC_AC= baseResponse.getElementsByTagName("DEPRE_REC_AC")[k].firstChild.nodeValue;
	    	        	        var DEPRE_ALLOWED_YR= baseResponse.getElementsByTagName("DEPRE_ALLOWED_YR")[k].firstChild.nodeValue;
	    	        	        var DEPRE_TR_AC= baseResponse.getElementsByTagName("DEPRE_TR_AC")[k].firstChild.nodeValue;
	    	        	        var DEPRE_UPTO_DATE= baseResponse.getElementsByTagName("DEPRE_UPTO_DATE")[k].firstChild.nodeValue;
	    	        	        var NET_DEPRE_COST= baseResponse.getElementsByTagName("NET_DEPRE_COST")[k].firstChild.nodeValue;
	    	        	        var tot_amt=Math.round(totl_amt,2);
	    	        	        var pre_amt=Math.round(previous_amt,2);
	    	        	        
	    	        	        var mycurrent_row=document.createElement("TR");
	    	                    mycurrent_row.id=seq;
	    	                    

	    						var cell1 =document.createElement("TD"); 
	    						var txtASSET_CODE=document.createElement("input");
	    						txtASSET_CODE.type="hidden";
	    						txtASSET_CODE.id="ASSET_CODE"+seq;
	    						txtASSET_CODE.value=ASSET_CODE;
	    						cell1.appendChild(txtASSET_CODE);
	    						var node_ASSET_CODE=document.createTextNode(ASSET_CODE);     
		    	                cell1.appendChild(node_ASSET_CODE);       
	    						mycurrent_row.appendChild(cell1);
	    						
	    						var cell2 =document.createElement("TD"); 
	    						var txtPARTICULARS=document.createElement("input");
	    						txtPARTICULARS.type="hidden";
	    						txtPARTICULARS.id="PARTICULARS"+seq;
	    						cell2.setAttribute('align','left');
	    						txtPARTICULARS.value=PARTICULARS;
	    					
	    						cell2.appendChild(txtPARTICULARS);
	    						var node_PARTICULARS=document.createTextNode(PARTICULARS);     
		    	                cell2.appendChild(node_PARTICULARS);       
	    						mycurrent_row.appendChild(cell2);
	    						
	    						var cell6 =document.createElement("TD"); 
	    						var txt_OPENING_BAL_VALUE=document.createElement("input");
	    						txt_OPENING_BAL_VALUE.type="hidden";
	    						txt_OPENING_BAL_VALUE.id="OPENING_BAL_VALUE"+seq;
	    						cell6.setAttribute('align','right');
	    						txt_OPENING_BAL_VALUE.value=OPENING_BAL_VALUE;
	    						cell6.appendChild(txt_OPENING_BAL_VALUE);
	    						var node_OPENING_BAL_VALUE=document.createTextNode(OPENING_BAL_VALUE);     
		    	                cell6.appendChild(node_OPENING_BAL_VALUE);       
	    						
	    						
	    						
	    						var txt_OPENING_BAL_QTY=document.createElement("input");
	    						txt_OPENING_BAL_QTY.type="hidden";
	    						txt_OPENING_BAL_QTY.id="OPEN_BAL_QTY"+seq;
	    						
	    						txt_OPENING_BAL_QTY.value=OPEN_BAL_QTY;
	    						cell6.appendChild(txt_OPENING_BAL_QTY);
	    						     
	    						mycurrent_row.appendChild(cell6);
	    						
	    						var cell3 =document.createElement("TD"); 
	    						var txt_totl_amt=document.createElement("input");
	    						txt_totl_amt.type="hidden";
	    						txt_totl_amt.id="totl_amt"+seq;
	    						cell3.setAttribute('align','right');
	    						txt_totl_amt.value=totl_amt;
	    						cell3.appendChild(txt_totl_amt);
	    						var node_totl_amt=document.createTextNode(totl_amt);     
		    	                cell3.appendChild(node_totl_amt);       
	    						mycurrent_row.appendChild(cell3);
	    						
	    						
	    						var cell5 =document.createElement("TD"); 
	    						var txt_previous_amt=document.createElement("input");
	    						txt_previous_amt.type="hidden";
	    						txt_previous_amt.id="previous_amt"+seq;
	    						cell5.setAttribute('align','right');
	    						txt_previous_amt.value=previous_amt;
	    						cell5.appendChild(txt_previous_amt);
	    						var node_previous_amt=document.createTextNode(previous_amt);     
		    	                cell5.appendChild(node_previous_amt);       
	    						mycurrent_row.appendChild(cell5);
	    						
	    					tbody.appendChild(mycurrent_row);
	    	                 seq++;  
	    	               }
}
	  
	}catch (e) {
	alert(e);
	}
	    		   }
	      }
	  }
	}
function listData()
{
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var FinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbdepRate=document.getElementById("cmbdepRateVAl").value;
	var val=document.getElementById("cmbmajorasset").value;
	var year=FinancialYear.split("-");
	var cmbFinancialYear=year[0]+"-"+year[1].substring(2);
	url="../../../../../Calc_Depreciation?command=listData&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+
	"&cmbdepRate="+cmbdepRate+"&cmbmajorasset="+val;
	
	var req=getTransport();
    req.open("POST",url,true);  
    req.onreadystatechange=function(){
    	
    	processResponse(req);
    };   
    req.send(null);
    }

function SubmitValues() {

	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	var FinancialYear=document.getElementById("cmbFinancialYear").value;
	var cmbdepRate=document.getElementById("cmbdepRateVAl").value;
	var val=document.getElementById("cmbmajorasset").value;
	var tblist=document.getElementById("tblList");
	var year=FinancialYear.split("-");
	var cmbFinancialYear=year[0]+"-"+year[1].substring(2);
	url="../../../../../Calc_Depreciation?command=Submit&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	"&cmbOffice_code="+cmbOffice_code+"&cmbFinancialYear="+cmbFinancialYear+
	"&cmbdepRate="+cmbdepRate+"&cmbmajorasset="+val;
	for(var i=0;i<tblist.rows.length;i++)
		{
		var txtASSET_CODE=document.getElementById("ASSET_CODE"+i).value;
		var txtPARTICULARS=document.getElementById("PARTICULARS"+i).value;
		var txtOPENING_BAL_VALUE=document.getElementById("OPENING_BAL_VALUE"+i).value;
		var txtOPEN_BAL_QTY=document.getElementById("OPEN_BAL_QTY"+i).value;
		var txttotl_amt=document.getElementById("totl_amt"+i).value;
		var to_amt=Math.round(txttotl_amt);
		var txtprevious_amt=document.getElementById("previous_amt"+i).value;
		var txttotl_amt=document.getElementById("totl_amt"+i).value;
		url+="&txtASSET_CODE="+txtASSET_CODE+"&txtPARTICULARS="+txtPARTICULARS+
		"&txtOPENING_BAL_VALUE="+txtOPENING_BAL_VALUE+"&txttotl_amt="+to_amt+
		"&txtprevious_amt="+txtprevious_amt+"&txtOPEN_BAL_QTY="+txtOPEN_BAL_QTY;
		
		}
	
	url+="&tblist="+tblist.rows.length;
	//alert(url);
	var req=getTransport();
    req.open("POST",url,true);  
    req.onreadystatechange=function(){
    	
    	processResponse(req);
    };   
    req.send(null);
    }



