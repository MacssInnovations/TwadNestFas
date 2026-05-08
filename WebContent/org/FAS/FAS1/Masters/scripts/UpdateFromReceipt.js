//UpdateFromReceipt
/** To handle Errors **/
onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page. \n\n";
	txt+="Error: " + msg + " \n";
	txt+="URL: " + url + " \n";
	txt+="Line: " + l + " \n\n";
	txt+="Click OK to continue.\n\n";
	alert(txt);
	return true;
}

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
 if (!req && typeof XMLHttpRequest!='undefined') 
 {
	
       req = new XMLHttpRequest();
 }  
 
 return req;
 
}


/*function checkNull_verify()
{
	  var tbody=document.getElementById("tblList");
	if(tbody.rows.length==0){
	alert("No values Found");
	return false;
	}
	document.getElementById("updateTotally").disabled=true;
	return true;
}*/
function checkNull_verify()
{
      var tbody=document.getElementById("tblList");
    /*if(tbody.rows.length==0){
    alert("No values Found");
    return false;
    }*/
      if((tbody.rows.length==1)){
            var rowcount=tbody.rows.length;
             for(var i=0;i<rowcount;i++)
                 {
                    var r=tbody.rows[i];
                    var s=r.cells.length;
                    if(s==1){
                       alert("No values found to update");
                       return false;
                    }else{
                        return true;
                    }
                 }
        }else if((tbody.rows.length==0)){
            alert("No values Found to update");
            return false;
        }
     // document.getElementById("updateTotally").disabled=true;
        return true;
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
                return false ;
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
	            return false ;
	        }
        }
}


 function Exit()
 {
    self.close();
 }
 /*function UpdateTotallyValues()
 {
 	//alert("update all values ");
 	
 	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 	var cmbOffice_code=document.getElementById("cmbOffice_code").value;
 	var cmbFinancialYear=document.getElementById("cmbFinancialYear").value;
 	var assetmajor=document.getElementById("cmbmajorasset").value;
     var tbody = document.getElementById("tblList");
     var rowcount=tbody.rows.length;
   // alert(rowcount+"rowCount update");
    
    
 	var t;
	var k=0;
 	if(rowcount==0)
 	{
 		alert("Wait..... The Grid Not Yet Loaded");
 		
 		return true;
 	}
 	//alert(rowcount);
 	var al= new Array() ;
    
     for(var i=0;i<rowcount;i++)
     	{
     	   var r=tbody.rows[i];
     	   var s=r.cells.length;
     	  //alert("s  "+s);
 	   
     	   for(var j=1;j<s;j++)
     		   {

     		   al[k]=r.cells[j].firstChild.nodeValue;
     		  // alert( al[i]);
     		    k++; 
     		    //alert(r.cells[j].firstChild.nodeValue);
     		   
     		   }
     	//alert("al "+al);
     	}
     
     var url="../../../../../UpdateFromReceipt?command=updateTotally&unit_id="+cmbAcc_UnitCode
     +"&office_id="+cmbOffice_code+"&financial_year="+cmbFinancialYear+"&assetmajor="+assetmajor+"&rowcount="+rowcount+"&grid="+al;
           
 	alert(url);
 	var req=getTransport();
    req.open("GET",url,true);   
    req.onreadystatechange=function()
    {
       processResponse(req);
    };   
    req.send(null);
 }*/
//******************************************** CallServer Coding *******************//
function callServer(command){  
           var accounting_unit_id=document.frmupdateReceipt.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmupdateReceipt.cmbOffice_code.value;
	   var assetmajor=document.frmupdateReceipt.cmbmajorasset.value;
	   var financial_year = document.frmupdateReceipt.cmbFinancialYear.value;  

       var url="";
        if(command=="Go")
        {  
        			url="../../../../../UpdateFromReceipt?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;
        			var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponseNew(req);
                    };   
                    req.send(null);
        }
        else if(command=="loadMajor"){
        	url="../../../../../UpdateFromReceipt?command=loadMajor";
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
            	//alert('TEST');
            	processResponseNew(req);
            };   
            req.send(null);
        	
        }/*else if(command=="checkVerifyA52"){
        	//alert("inside check verfied");
        	url="../../../../../UpdateFromReceipt?command=checkVerifyA52&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id;
    		var req=getTransport();
            req.open("GET",url,true);  
            req.onreadystatechange=function(){
               processResponse(req);
            };   
            req.send(null);
        	
        }*/
}  


//********************************* CallServer Response Coding ***************************************//

function processResponseNew(req)
{   
  if(req.readyState==4)
  {
	 // alert("readyState=="+req.readyState);
      if(req.status==200)
      {   
    	 // alert("status=="+req.status);
    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    	//  alert("baseResponse=="+baseResponse);
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          
          var command=tagCommand.firstChild.nodeValue; 
        //  alert("command=="+command);
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
             if(command=="GoGet"){
            	 // alert("goget");
            	  GoGet(baseResponse);
            	  
              }
            else if(command=="loadMajor")
              {
        		  var cmbMajorClass = document.getElementById('cmbmajorasset');
        		  cmbMajorClass.length=0;
        		 // var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
        		
            	  if(flag=="success"){
            		  
            		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
              		  if(exists=="Yes"){
              		
            		  var mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE');
            		  
                	  var len = mjrCode.length;
                	 // alert("length >> "+len);
            	  for(var i=0; i<len; i++)
            	  {
            		  mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE')[i].firstChild.nodeValue;
            		  var mjrDesc = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_DESC')[i].firstChild.nodeValue;
            		 //alert("'mm "+mjrCode);
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
				        alert("No Major AssetCode in Table");
				        }
            	  
              }
       
         
    	  }
     }
}

function  GoGet(baseResponse)
{  
var seq=0;
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
  if(flag=="success")
    {          
        var tbody = document.getElementById("tblList");
       /* try{tbody.innerHTML="";}
        catch(e) {tbody.innerText="";}  */
        
        var table = document.getElementById("Existing");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            }                        
        var len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
      
        if(len==0){
        	var mycurrent_row22=document.createElement("TR");
            mycurrent_row22.id=0;                     
            mycurrent_row22.align="center";
            var cell1 =document.createElement("TD");    
            cell1.setAttribute('colspan','20');
            var code=document.createTextNode("No records");                         
            cell1.appendChild(code);  
            cell1.align="center";
            mycurrent_row22.appendChild(cell1);        						

        	   tbody.appendChild(mycurrent_row22);
        	
        }else{
        	 var lll=1;
        	 var item = new Array();
        	 var codeMinor = new Array();
     		var descMinor = new Array();
     		
               for(var k=0;k<len;k++)
                  {
            		item[0] =baseResponse.getElementsByTagName("CASHBOOK_YEAR")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("CASHBOOK_MONTH")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("asset_code")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("RECEIPT_NO")[k].firstChild.nodeValue;
					item[4] =baseResponse.getElementsByTagName("RECEIPT_DATE")[k].firstChild.nodeValue;
					item[5]=baseResponse.getElementsByTagName("RECEIVED_FROM_LEVEL_ID")[k].firstChild.nodeValue;			
					item[6]=baseResponse.getElementsByTagName("RECEIVED_FROM_OFFICE")[k].firstChild.nodeValue;	
					item[7]=baseResponse.getElementsByTagName("MBOOK_NO")[k].firstChild.nodeValue;			
					item[8]=baseResponse.getElementsByTagName("MBOOK_DATE")[k].firstChild.nodeValue;	
					item[9]=baseResponse.getElementsByTagName("RECEIVED_QTY")[k].firstChild.nodeValue;	
					item[10]=baseResponse.getElementsByTagName("RECEIVED_VALUE")[k].firstChild.nodeValue;	
					item[11]=baseResponse.getElementsByTagName("REMARKS")[k].firstChild.nodeValue;	
					item[12]=baseResponse.getElementsByTagName("accounting_unit_office_id")[k].firstChild.nodeValue;	
					item[13]=baseResponse.getElementsByTagName("PARTICULARS")[k].firstChild.nodeValue;
					
					var mycurrent_row=document.createElement("TR");
                    mycurrent_row.id=item[0];                     

                    var cell0 = document.createElement("TD");
                    var serialNo1=document.createElement("input");
                    serialNo1.type="hidden";
                    serialNo1.name="serialNo";
                    serialNo1.id="serialNo";
                    serialNo1.value=lll;
            		var sno2 = document.createTextNode(lll);
            		cell0.appendChild(sno2);
            		cell0.appendChild(serialNo1);
            		mycurrent_row.appendChild(cell0);
            		
            		var cell712 = document.createElement("TD");
           			var remark2=document.createElement("input");   
           			remark2.type="hidden";
           			remark2.name="PARTICULARS";
           			remark2.id="PARTICULARS";
           			remark2.value=item[13];
           			var remark3 = document.createTextNode(item[13]);
           			remark3.size=7;
           			cell712.appendChild(remark2);
           			cell712.appendChild(remark3);
           			cell712.align="left";
           			mycurrent_row.appendChild(cell712);
            		        		

           			var cell1 = document.createElement("TD");
           			var CASHBOOK_YEAR1=document.createElement("input");
           			CASHBOOK_YEAR1.type="hidden";
           			CASHBOOK_YEAR1.name="CASHBOOK_YEAR";
           			CASHBOOK_YEAR1.id="CASHBOOK_YEAR";
           			CASHBOOK_YEAR1.value=item[0];
           			var CASHBOOK_YEAR = document.createTextNode(item[0]);
           			CASHBOOK_YEAR.size=7;
           			cell1.appendChild(CASHBOOK_YEAR);
           			cell1.appendChild(CASHBOOK_YEAR1);
           			mycurrent_row.appendChild(cell1);
           			
           			var cell2 = document.createElement("TD");
           			var CASHBOOK_MONTH1=document.createElement("input");
           			CASHBOOK_MONTH1.type="hidden";
           			CASHBOOK_MONTH1.name="CASHBOOK_MONTH";
           			CASHBOOK_MONTH1.id="CASHBOOK_MONTH";
           			CASHBOOK_MONTH1.value=item[1];
           			var CASHBOOK_MONTH = document.createTextNode(item[1]);
           			CASHBOOK_MONTH.size=7;
           			cell2.appendChild(CASHBOOK_MONTH);
           			cell2.appendChild(CASHBOOK_MONTH1);
           			cell2.align="right";
           			mycurrent_row.appendChild(cell2);
           			
           			var cell3 = document.createElement("TD");
           			var asset_code1=document.createElement("input");
           			asset_code1.type="hidden";
           			asset_code1.name="asset_code";
           			asset_code1.id="asset_code";
           			asset_code1.value=item[2];
           			var asset_code = document.createTextNode(item[2]);
           			asset_code.size=7;
           			cell3.appendChild(asset_code);
           			cell3.appendChild(asset_code1);
           			//cell3.align="right";
           			mycurrent_row.appendChild(cell3);
           			
           			var cell4 = document.createElement("TD");
           			var RECEIPT_NO1=document.createElement("input");
           			RECEIPT_NO1.type="hidden";
           			RECEIPT_NO1.name="RECEIPT_NO";
           			RECEIPT_NO1.id="RECEIPT_NO";
           			RECEIPT_NO1.value=item[3];         			
           			var RECEIPT_NO = document.createTextNode(item[3]);
           			RECEIPT_NO.size=7;
           			cell4.appendChild(RECEIPT_NO);
           			cell4.appendChild(RECEIPT_NO1);
           			//cell4.align="left";
           			mycurrent_row.appendChild(cell4);
           			
           			var cell5 = document.createElement("TD");
           			var RECEIPT_DATE1=document.createElement("input");
           			RECEIPT_DATE1.type="hidden";
           			RECEIPT_DATE1.name="RECEIPT_DATE";
           			RECEIPT_DATE1.id="RECEIPT_DATE";
           			RECEIPT_DATE1.value=item[4];
           			var RECEIPT_DATE = document.createTextNode(item[4]);
           			RECEIPT_DATE.size=7;
           			cell5.appendChild(RECEIPT_DATE);
           			cell5.appendChild(RECEIPT_DATE1);
           			mycurrent_row.appendChild(cell5);
           			
           			var cell6 = document.createElement("TD");
           			var RECEIVED_FROM_OFFICE1=document.createElement("input");
           			RECEIVED_FROM_OFFICE1.type="hidden";
           			RECEIVED_FROM_OFFICE1.name="RECEIVED_FROM_OFFICE";
           			RECEIVED_FROM_OFFICE1.id="RECEIVED_FROM_OFFICE";
           			RECEIVED_FROM_OFFICE1.value=item[6];
           			var RECEIVED_FROM_OFFICE = document.createTextNode(item[6]);
           			RECEIVED_FROM_OFFICE.size=7;
           			cell6.align="left";
           			cell6.appendChild(RECEIVED_FROM_OFFICE);
           			cell6.appendChild(RECEIVED_FROM_OFFICE1);
           			mycurrent_row.appendChild(cell6);
           			
           			var cell7 = document.createElement("TD");
           			var MBOOK_NO=document.createElement("input");
           			MBOOK_NO.type="hidden";
           			MBOOK_NO.name="MBOOK_NO";
           			MBOOK_NO.id="MBOOK_NO";
           			MBOOK_NO.value=item[7];
           			var MBOOK_NO1 = document.createTextNode(item[7]);
           			MBOOK_NO1.size=7;
           			cell7.appendChild(MBOOK_NO);
           			cell7.appendChild(MBOOK_NO1);
           			mycurrent_row.appendChild(cell7);
           			
           			
           			
           			var cell70 = document.createElement("TD");
           			var MBOOK_DATE=document.createElement("input");
           			MBOOK_DATE.type="hidden";
           			MBOOK_DATE.name="MBOOK_DATE";
           			MBOOK_DATE.id="MBOOK_DATE";
			        MBOOK_DATE.value=item[8];
			      // MBOOK_DATE.setAttribute("onkeypress", "return numbersonly1(event,this)");
           			var MBOOK_DATE1 = document.createTextNode(item[8]);
			        MBOOK_DATE1.size=7;
           			cell70.appendChild(MBOOK_DATE1);
           			cell70.appendChild(MBOOK_DATE);
           			mycurrent_row.appendChild(cell70);
           			
           			var cell72 = document.createElement("TD");
           			var RECEIVED_QTY=document.createElement("input");
           			RECEIVED_QTY.type="hidden";
           			RECEIVED_QTY.name="RECEIVED_QTY";
           			RECEIVED_QTY.id="RECEIVED_QTY";
           			RECEIVED_QTY.value=item[9];
           			var RECEIVED_QTY1 = document.createTextNode(item[9]);
           			RECEIVED_QTY1.size=7;
           			cell72.appendChild(RECEIVED_QTY1);
           			cell72.appendChild(RECEIVED_QTY);
           			mycurrent_row.appendChild(cell72);
           			
           			var cell721 = document.createElement("TD");
           			var RECEIVED_VALUE=document.createElement("input");
           			RECEIVED_VALUE.type="hidden";
           			RECEIVED_VALUE.name="RECEIVED_VALUE";
           			RECEIVED_VALUE.id="RECEIVED_VALUE";
           			RECEIVED_VALUE.value=item[10];
           			var RECEIVED_VALUE1 = document.createTextNode(item[10]);
           			RECEIVED_VALUE1.size=7;
           			cell721.appendChild(RECEIVED_VALUE1);
           			cell721.appendChild(RECEIVED_VALUE);
           			mycurrent_row.appendChild(cell721);
           			
           			
           			
           			var cell71 = document.createElement("TD");
           			var remark=document.createElement("input");   
           			remark.type="hidden";
           			remark.name="remarks";
           			remark.id="remarks";
           			remark.value=item[11];
           			var remark1 = document.createTextNode(item[11]);
           			remark1.size=7;
           			cell71.appendChild(remark1);
           			cell71.appendChild(remark);
           			cell71.align="left";
           			mycurrent_row.appendChild(cell71);
           			           		            			
           			//hidden
           			
           			var cell8 = document.createElement("TD");
           			cell8.style.display="none";
           			var unitid1=document.createElement("input");
           			unitid1.type="hidden";
           			unitid1.name="unitid1";
           			unitid1.id="unitid1";
           			unitid1.value=item[5]; 
           			var unitid2= document.createTextNode(item[5]);
           			unitid2.size=7;
           			cell8.appendChild(unitid2);
           			cell8.appendChild(unitid1);
           			mycurrent_row.appendChild(cell8);
           			
           			var cell9 = document.createElement("TD");
           			cell9.style.display="none";
           			var officeid1=document.createElement("input");
           			officeid1.type="hidden";
           			officeid1.name="officeid1";
           			officeid1.id="officeid1";
           			officeid1.value=item[12];   
           			var officeid2 = document.createTextNode(item[12]);
           			officeid2.size=7;
           			cell9.appendChild(officeid2);
           			cell9.appendChild(officeid1);
           			mycurrent_row.appendChild(cell9);
           			
           			lll++;
                    tbody.appendChild(mycurrent_row);
                    seq+=1;  
                  }
			
		        }
		         }
				  else
				  {
				    alert("Failed to Load Values");
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
            return false ;
    }
 }
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   if(unicode==13)
    {
      //t.blur();
      //return true;-------------------- for taking action when press ENTER
    
    }
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48 || unicode>57 ) 
            return false ;
    }
 }
function clearAll()
{
	  document.getElementById('cmbAcc_UnitCode').options[0].selected = "selected";
	  document.getElementById('cmbOffice_code').options[0].selected = "selected";
	  document.getElementById('cmbFinancialYear').value = "selected";
	  document.getElementById('cmbmajorasset').value = 0;
	  var tbody = document.getElementById("tblList");
       
       var table = document.getElementById("Existing");
       var t=0;
       for(t=tbody.rows.length-1;t>=0;t--)
           {
              tbody.deleteRow(0);
           } 

}