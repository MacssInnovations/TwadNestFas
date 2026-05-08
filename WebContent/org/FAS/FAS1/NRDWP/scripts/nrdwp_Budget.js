//nrdwp Budget
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

function addbtn()
{
	var accounting_unit_id=document.frmnrdwpBudget.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmnrdwpBudget.cmbOffice_code.value;
	var financial_year = document.frmnrdwpBudget.cmbFinancialYear.value;  
	var tbody = document.getElementById("tblList");
	var rowcount=tbody.rows.length;
	var k=0;
	var al=new Array();
	var rowad=parseInt(rowcount-1);
	for(var i=1;i<rowad;i++)
	{
	   var r=tbody.rows[i];
	   var s=r.cells.length;
	
	   for(var j=1;j<s;j++)
		   {
		 // alert("cell 1val :"+r.cells[j].firstChild.nodeValue);
		 // alert("cell 2val :"+r.cells[j].lastChild.value);
			 al[k]=r.cells[j].lastChild.value;
			 k++; 
		   
		   }
	   
	
	}
    if(checkNull_verify()){ 
    var url="../../../../../nrdwp_Budget?command=addDetails&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&rowcount="+rowad+"&grid="+al;
   // alert(url);
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {
		processResponse(xmlrequest);
		}

	xmlrequest.send(null);
    }
}
 function updatebtn(){
    var accounting_unit_id=document.frmnrdwpBudget.cmbAcc_UnitCode.value;
	var accounting_unit_office_id = document.frmnrdwpBudget.cmbOffice_code.value;
	var financial_year = document.frmnrdwpBudget.cmbFinancialYear.value;  
	var tbody = document.getElementById("tblList");
	var rowcount=tbody.rows.length;
	var k=0;
	var al=new Array();
	//var rowad=parseInt(rowcount-1);
	for(var i=0;i<rowcount;i++)
	{
	   var r=tbody.rows[i];
	   var s=r.cells.length;
	
	   for(var j=0;j<s;j++)
		   {
			 al[k]=r.cells[j].lastChild.value;
			 k++; 
		   
		   }
	   
	
	}
	//alert("rowcount "+rowcount)
	//alert("haddddi:"+al.length);
  if(checkNull_verify()){ 
  var url="../../../../../nrdwp_Budget?command=updateDetails&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&rowcount="+rowcount+"&grid="+al;
 // alert(url);
	var xmlrequest = getTransport();
	xmlrequest.open("GET", url, true);
	xmlrequest.onreadystatechange = function() {

		processResponse(xmlrequest);
		}

	xmlrequest.send(null);
  }
 }
 function deletebtn(){
	    var accounting_unit_id=document.frmnrdwpBudget.cmbAcc_UnitCode.value;
		var accounting_unit_office_id = document.frmnrdwpBudget.cmbOffice_code.value;
		var financial_year = document.frmnrdwpBudget.cmbFinancialYear.value;  
		var tbody = document.getElementById("tblList");
		var rowcount=tbody.rows.length;
		var k=0;
		var al=new Array();
		for(var i=0;i<rowcount;i++)
		{
		   var r=tbody.rows[i];
		   var s=r.cells.length;
		
		   for(var j=0;j<s;j++)
			   {
				 al[k]=r.cells[j].lastChild.value;
				 k++; 
			   
			   }
		   
		
		}
		//alert("rowcount "+rowcount)
		//alert("haddddi:"+al.length);
	  if(checkNull_verify()){ 
	  var url="../../../../../nrdwp_Budget?command=deleteDetails&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&rowcount="+rowcount+"&grid="+al;
	 // alert(url);
		var xmlrequest = getTransport();
		xmlrequest.open("GET", url, true);
		xmlrequest.onreadystatechange = function() {

			processResponse(xmlrequest);
			}

		xmlrequest.send(null);
	  }
	 }
function checkNull_verify()
{
	var unitid= document.getElementById('cmbAcc_UnitCode').value;
	 var office= document.getElementById('cmbOffice_code').value;
	  var year1=document.getElementById('cmbFinancialYear').value;
	  if((unitid=="")||(unitid==0)){
		  alert("Select Accounting unit id");
		  return false;
	  }
	  if((office=="")||(office==0)){
		  alert("Select Accounting office id");
		  return false;
	  }
	  if((year1=="")||(year1==0)){
		  alert("Select Financial Year");
		  return false;
	  }
	  var tbody=document.getElementById("tblList");
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
function checkNulll(){
	var unitid= document.getElementById('cmbAcc_UnitCode').value;
	 var office= document.getElementById('cmbOffice_code').value;
	  var year1=document.getElementById('cmbFinancialYear').value;
	  if((unitid=="")||(unitid==0)){
		  alert("Select Accounting unit id");
		  return false;
	  }
	  if((office=="")||(office==0)){
		  alert("Select Accounting office id");
		  return false;
	  }
	  if((year1=="")||(year1==0)){
		  alert("Select Financial Year");
		  return false;
	  }
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
//******************************************** CallServer Coding *******************//
function callServer(command){  
       var accounting_unit_id=document.frmnrdwpBudget.cmbAcc_UnitCode.value;
	   var accounting_unit_office_id = document.frmnrdwpBudget.cmbOffice_code.value;
	   var financial_year = document.frmnrdwpBudget.cmbFinancialYear.value;  

       var url="";
        if(command=="Go")
        {  
        	if(checkNulll()){
        		
        	
        			url="../../../../../nrdwp_Budget?command=GoGet&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year;
        			var req=getTransport();
                    req.open("GET",url,true);        
                    req.onreadystatechange=function()
                    {
                       processResponse(req);
                    };   
                    req.send(null);
        	}
        }else if(command=="Edit1")
        {  
        	if(checkNulll()){
			url="../../../../../nrdwp_Budget?command=Edit1&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year;
			var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
               processResponse(req);
            };   
            req.send(null);
        	}
}
     
}  


//********************************* CallServer Response Coding ***************************************//

function processResponse(req)
{   
  if(req.readyState==4)
  {
      if(req.status==200)
      {   

    	  var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          
          var tagCommand=baseResponse.getElementsByTagName("command")[0];
          
          var command=tagCommand.firstChild.nodeValue; 
         // alert("command=="+command);
    	 // var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
             if(command=="GoGet"){
            	  GoGet(baseResponse);
            	  
              }else  if(command=="Edit1"){
            	  Edit1(baseResponse);
            	  
              }
             else if(command=="addDetails"){
            	  addDetails1(baseResponse);
              }
             else if(command=="updateDetails"){
            	 updateDetails1(baseResponse);
             }else if(command=="deleteDetails"){
            	 deleteDetails1(baseResponse);
             }
         
    	  }
     }
}
function deleteDetails1(baseResponse){
	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	  if(flag=="success")
	    {
		  alert("Record successfully Deleted");
		  clearAll();
	    }else{
	    	alert("Fail to insert");
	    }
}
function updateDetails1(baseResponse){
	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	  if(flag=="success")
	    {
		  alert("Record successfully updated");
		  clearAll();
	    }else{
	    	alert("Fail to insert");
	    }
}
function addDetails1(baseResponse){
	 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	  if(flag=="success")
	    {
		  alert("Record inserted successfully");
		  clearAll();
	    }else{
	    	alert("Fail to insert");
	    }
}

function Edit1(baseResponse) {
	document.getElementById("addDetails").disabled=true;
	document.getElementById("CmdGo").disabled=true;
	document.getElementById("updateDetails").disabled=false;
	document.getElementById("deleteDetails").disabled=false;
		
	var checkFr=baseResponse.getElementsByTagName("checkFreeze")[0].firstChild.nodeValue; 
	if(checkFr=="freeze"){
		alert("NRDWP Freeze done ");
	}else if(checkFr=="Notfreeze"){

	var check=baseResponse.getElementsByTagName("check")[0].firstChild.nodeValue; 
	if(check=="valueinTB"){
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
   if(flag=="success")
    {          
        var tbody = document.getElementById("tblList");
        var table = document.getElementById("Existing");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
            {
               tbody.deleteRow(0);
            }    

        var lenn=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  	
        	 var lll=1;
        	 var item = new Array();
               for(var k=0;k<lenn;k++)
                  {
            		item[0] =baseResponse.getElementsByTagName("activity")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("actdesc")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("COMPONENT_CODE")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("CompDesc")[k].firstChild.nodeValue;
					item[4] =baseResponse.getElementsByTagName("SHARE_CODE")[k].firstChild.nodeValue;
					item[5] =baseResponse.getElementsByTagName("ShareDesc")[k].firstChild.nodeValue;
					item[6]=baseResponse.getElementsByTagName("allocation")[k].firstChild.nodeValue;
					
					var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=seq;                     

        		/*	var cell1 = document.createElement("TD");
        			cell1.style.display="none";
           			var sno2=document.createElement("input");
           			sno2.type="hidden";
           			sno2.name="serialNo";
           			sno2.id="serialNo";
           			sno2.value=lll;
           			cell1.appendChild(sno2);
           			var sno2 = document.createTextNode(lll);
           			cell1.appendChild(sno2);
           			mycurrent_row.appendChild(cell1);*/
        			
           			var cell1 = document.createElement("TD");
           			var activity_code1=document.createElement("input");
           			activity_code1.type="hidden";
           			activity_code1.name="activity_code";
           			activity_code1.id="activity_code";
           			activity_code1.value=item[0];
           			var activity_code = document.createTextNode(item[1]);
           			activity_code.size=7;
           			cell1.align="left";
           			cell1.appendChild(activity_code);
           			cell1.appendChild(activity_code1);
           			mycurrent_row.appendChild(cell1);
           			
           			var cell2 = document.createElement("TD");
           			var COMPONENT_CODE1=document.createElement("input");
           			COMPONENT_CODE1.type="hidden";
           			COMPONENT_CODE1.name="COMPONENT_CODE";
           			COMPONENT_CODE1.id="COMPONENT_CODE";
           			COMPONENT_CODE1.value=item[2];
           			var COMPONENT_CODE = document.createTextNode(item[3]);
           			COMPONENT_CODE.size=7;
           			cell2.appendChild(COMPONENT_CODE);
           			cell2.appendChild(COMPONENT_CODE1);
           			cell2.align="left";
           			mycurrent_row.appendChild(cell2);
           			
           			var cell3 = document.createElement("TD");
           			var shareCode=document.createElement("input");
           			shareCode.type="hidden";
           			shareCode.name="SHARE_CODE";
           			shareCode.id="SHARE_CODE";
           			shareCode.value=item[4];
           			var shareCode1 = document.createTextNode(item[5]);
           			shareCode1.size=7;		
           			cell3.appendChild(shareCode1);         		
           			cell3.appendChild(shareCode);
           			mycurrent_row.appendChild(cell3);
           			
           			
           			var cell4 = document.createElement("TD");
           			var allocation=document.createElement("input");
           			allocation.type="text";
           			allocation.name="allocation";
           			allocation.id="allocation";
           			allocation.value=item[6];
           			allocation.setAttribute("onkeypress", "return numbersonly1(event,this)");
           			allocation.size=7;
           			cell4.appendChild(allocation);
           			mycurrent_row.appendChild(cell4);

           			lll++;
                    tbody.appendChild(mycurrent_row);
                    seq+=1;  
                  }
              
       			
       			}
				  else
				  {
				    alert("Failed to Load Values");
				  }
			}else{
					alert("There is no value for Edit,First Add Datas");
					document.getElementById("addDetails").disabled=false;
					document.getElementById("CmdGo").disabled=false;
				}
	}
}
var len1=0;
var seq=0;
var len=0;
function  GoGet(baseResponse)
{  
	
	var checkFr=baseResponse.getElementsByTagName("checkFreeze")[0].firstChild.nodeValue; 
	if(checkFr=="freeze"){
		alert("NRDWP Freeze done ");
	}else if(checkFr=="Notfreeze"){
	
	var check=baseResponse.getElementsByTagName("check")[0].firstChild.nodeValue; 
	if(check=="NotvalueinTB"){
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

		 len1=baseResponse.getElementsByTagName("sharecount")[0].firstChild.nodeValue;  
	        var shareval=baseResponse.getElementsByTagName("SHARE_value")[0].firstChild.nodeValue;	
	   	 var item1 = new Array();
	   	var mycurrent_row2=document.createElement("TR");
        mycurrent_row2.id="header";   
        var cell1 = document.createElement("TH");
 			mycurrent_row2.appendChild(cell1);
 			
 			var cell2 = document.createElement("TH");
     			mycurrent_row2.appendChild(cell2);
	        for(var k=0;k<len1;k++)
            {
      		item1[0] =baseResponse.getElementsByTagName("SHARE_CODE")[k].firstChild.nodeValue;
			item1[1] =baseResponse.getElementsByTagName("SHARE_DESC")[k].firstChild.nodeValue;                 

         			var cell1 = document.createElement("TH");
             			var sno2=document.createElement("input");
             			sno2.type="hidden";
             			sno2.name="SHARE_CODE";
             			sno2.id="SHARE_CODE";
             			sno2.value=item1[0];
             			cell1.appendChild(sno2);
             			var sno2 = document.createTextNode(item1[1]);
             			cell1.appendChild(sno2);
             			mycurrent_row2.appendChild(cell1);
     			
            }
	        var cell1 = document.createElement("TH");
     			
     			var sno2 = document.createTextNode("Total");
     			cell1.appendChild(sno2);
     			mycurrent_row2.appendChild(cell1); 
     			
     			 tbody.appendChild(mycurrent_row2);	
     			
	        
        len=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;  
        var verfiyst=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;	
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
               for(var k=0;k<len;k++)
                  {
            		item[0] =baseResponse.getElementsByTagName("activity_code")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("Activty_Desc")[k].firstChild.nodeValue;
					item[2] =baseResponse.getElementsByTagName("COMPONENT_CODE")[k].firstChild.nodeValue;
					item[3]=baseResponse.getElementsByTagName("component_desc")[k].firstChild.nodeValue;
	
					var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=seq;                     

        			var cell1 = document.createElement("TD");
        			cell1.style.display="none";
           			var sno2=document.createElement("input");
           			sno2.type="hidden";
           			sno2.name="serialNo";
           			sno2.id="serialNo";
           			sno2.value=lll;
           			cell1.appendChild(sno2);
           			/*var sno2 = document.createTextNode(lll);
           			cell1.appendChild(sno2);*/
           			mycurrent_row.appendChild(cell1);
        			
           			var cell1 = document.createElement("TD");
           			var activity_code1=document.createElement("input");
           			activity_code1.type="hidden";
           			activity_code1.name="activity_code";
           			activity_code1.id="activity_code";
           			activity_code1.value=item[0];
           			var activity_code = document.createTextNode(item[1]);
           			activity_code.size=7;
           			cell1.align="left";
           			cell1.appendChild(activity_code);
           			cell1.appendChild(activity_code1);
           			mycurrent_row.appendChild(cell1);
           			
           			var cell2 = document.createElement("TD");
           			var COMPONENT_CODE1=document.createElement("input");
           			COMPONENT_CODE1.type="hidden";
           			COMPONENT_CODE1.name="COMPONENT_CODE";
           			COMPONENT_CODE1.id="COMPONENT_CODE";
           			COMPONENT_CODE1.value=item[2];
           			var COMPONENT_CODE = document.createTextNode(item[3]);
           			COMPONENT_CODE.size=7;
           			cell2.appendChild(COMPONENT_CODE);
           			cell2.appendChild(COMPONENT_CODE1);
           			cell2.align="left";
           			mycurrent_row.appendChild(cell2);
           			
           			for(var ii=0;ii<len1;ii++){
           				//item1[0] =baseResponse.getElementsByTagName("SHARE_CODE")[ii].firstChild.nodeValue;
           				//item1[1] =baseResponse.getElementsByTagName("SHARE_DESC")[ii].firstChild.nodeValue;   
           			var cell3 = document.createElement("TD");
           			var NRDWP1=document.createElement("input");
           			NRDWP1.type="text";
           			//NRDWP1.name=item1[1];
           			//NRDWP1.id=item1[1];
           			
           			NRDWP1.name="gridval"+seq+ii;
           			NRDWP1.id="gridval"+seq+ii;
           			NRDWP1.setAttribute("onkeypress", "return numbersonly1(event,this)");
           			NRDWP1.setAttribute("onchange", "loadRowTotal('"+mycurrent_row.id+"'),loadColNrdTotal('"+ii+"')");
           			NRDWP1.setAttribute("onblur", "loadColRowTotal()");
           			NRDWP1.size=7;
           			cell3.appendChild(NRDWP1);
           			mycurrent_row.appendChild(cell3);
           			}
           			
           		/*	var cell4 = document.createElement("TD");
           			var SMS1=document.createElement("input");
           			SMS1.type="text";
           			SMS1.name="SMS";
           			SMS1.id="SMS";
           			SMS1.setAttribute("onkeypress", "return numbersonly1(event,this)");
           			SMS1.setAttribute("onchange", "loadRowTotal('"+mycurrent_row.id+"'),loadColSmsTotal('"+mycurrent_row.id+"')");
           			//SMS1.setAttribute("onchange", "loadColSmsTotal('"+mycurrent_row.id+"')");
           			SMS1.size=7;
           			cell4.appendChild(SMS1);
           			mycurrent_row.appendChild(cell4);
	           		  */
           			
           			var cell5 = document.createElement("TD");
           			var totalrow1=document.createElement("input");
           			totalrow1.type="text";
           			totalrow1.name="totalrow"+seq;
           			totalrow1.id="totalrow"+seq;	
           			totalrow1.disabled=true;
           			//totalrow1.readonly='readonly';
           			//totalrow1.setAttribute("onchange", "loadColRowTotal();");
           			totalrow1.size=7;
           			cell5.appendChild(totalrow1);
           			mycurrent_row.appendChild(cell5);
           			
           			lll++;
                    tbody.appendChild(mycurrent_row);
                    seq+=1;  
                  }
               var mycurrent_row1=document.createElement("TR");
               mycurrent_row1.id='undeetot';   
               var cell1 = document.createElement("TD");
      			var tot2= document.createTextNode('Grand Total');
      			tot2.size=7;
      			cell1.appendChild(tot2);
      			mycurrent_row1.appendChild(cell1);
                
      			 var cell2 = document.createElement("TD");
       			var tot_2= document.createTextNode(' ');
       			tot_2.size=7;
       			cell2.appendChild(tot_2);
       			mycurrent_row1.appendChild(cell2);
      			
       			for(var ii=0;ii<len1;ii++){

       			var cell3 = document.createElement("TD");
       			var totalcolNdr1=document.createElement("input");
       			totalcolNdr1.type="text";
       			totalcolNdr1.name="totalcolNdr"+ii;
       			totalcolNdr1.id="totalcolNdr"+ii;
       			//totalcolNdr1.setAttribute("onchange", "loadColNrdTotal('"+ii+"')");
       			totalcolNdr1.disabled=true;
       			totalcolNdr1.size=7;
       			cell3.appendChild(totalcolNdr1);
       			mycurrent_row1.appendChild(cell3);
       			
       			}
       			/*var cell4 = document.createElement("TD");
       			var totalcolsms1=document.createElement("input");
       			totalcolsms1.type="text";
       			totalcolsms1.name="totalcolsms";
       			totalcolsms1.id="totalcolsms";	
       			//totalcolsms1.disabled=true;
       			//totalrow1.setAttribute("onkeypress", "return numbersonly1(event,this)");
       			totalcolsms1.size=7;
       			cell4.appendChild(totalcolsms1);
       			mycurrent_row1.appendChild(cell4);*/

       			var cell5 = document.createElement("TD");
       			var totalcolrow1=document.createElement("input");
       			totalcolrow1.type="text";
       			totalcolrow1.name="totalcolrow";
       			totalcolrow1.id="totalcolrow";	
       			totalcolrow1.disabled=true;
       			//totalcolrow1.setAttribute("onblur", "loadColRowTotal()");
       			totalcolrow1.size=7;
       			cell5.appendChild(totalcolrow1);
       			mycurrent_row1.appendChild(cell5);
       			
       		 tbody.appendChild(mycurrent_row1);
		        }
		         }
				  else
				  {
				    alert("Failed to Load Values");
				  }
			}
	else if(check=="valueinTB"){
					alert("Already data added,Only edit option is possible");
					document.getElementById("addDetails").disabled=true;
					document.getElementById("CmdGo").disabled=true;
					document.getElementById("editDetails").disabled=false;
					document.getElementById("updateDetails").disabled=false;
					document.getElementById("deleteDetails").disabled=false;
					
	
				}
	}
  
  
}

function loadRowTotal(scod)
{   
		var nrd;
		var r=document.getElementById(scod);
        var rcells=r.cells;
        var ll=parseInt(len1)+3;
        var rowtot=0;
        for(var ii=0;ii<len1;ii++){
        	 try{
        		 nrd=document.getElementById("gridval"+scod+ii).value;
          	   if(nrd=="")nrd=0;
        	 } 
             catch(e){
          	   nrd=0;
          	   } 
             rowtot=parseInt(rowtot)+parseInt(nrd);
        } 
        document.getElementById("totalrow"+scod).value=rowtot;
}

function loadColNrdTotal(noo)
{  
	var colnrdtot=0;
		var tbody = document.getElementById("tblList");
		for(var i=0;i<=len;i++){	
        var r=tbody.rows[i];
        var rcells=r.cells;
       try{
    	 nrd1=document.getElementById("gridval"+i+noo).value;
    	   if(nrd1=="")nrd1=0;
    	   } 
       catch(e){
    	   nrd1=0;
    	   }   
      colnrdtot=parseInt(colnrdtot)+parseInt(nrd1);
		}
		document.getElementById("totalcolNdr"+noo).value=colnrdtot;	
}

function loadColRowTotal()
{    	   
	var colrowtot=0;
	var tot;
	var tbody = document.getElementById("tblList");
	for(var i=0;i<=len;i++){	
    var r=tbody.rows[i];
    var rcells=r.cells;
   try{
	 tot=document.getElementById("totalrow"+i).value;
	   if(tot=="")tot=0;
	   } 
   catch(e){
	   tot=0;
	   }   
   colrowtot=parseInt(colrowtot)+parseInt(tot);
	}
	
	document.getElementById("totalcolrow").value=colrowtot;	
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
	  document.getElementById('cmbFinancialYear').value = "";
	  var tbody = document.getElementById("tblList");
       
       var table = document.getElementById("Existing");
       var t=0;
       for(t=tbody.rows.length-1;t>=0;t--)
           {
              tbody.deleteRow(0);
           } 
       document.getElementById("addDetails").disabled=false;
		document.getElementById("CmdGo").disabled=false;
		document.getElementById("editDetails").disabled=false;
		document.getElementById("updateDetails").disabled=true;
		document.getElementById("deleteDetails").disabled=true;
		
}