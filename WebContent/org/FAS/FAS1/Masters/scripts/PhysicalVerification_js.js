var service;
var __pagination=11;
var destid;
var totalblock=0;
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
function callServer(command){  
    var accounting_unit_id=document.phyVerification.cmbAcc_UnitCode.value;
var accounting_unit_office_id = document.phyVerification.cmbOffice_code.value;
var assetmajor=document.phyVerification.cmbmajorasset.value;
 var assetminor = document.phyVerification.cmbminorasset.value;
var financial_year = document.phyVerification.fin_year.value;  
var url="";
  if(command=="loadMajor"){
 	url="../../../../../PhysicalVerification_Servlet?Command=loadMajor";
		var req=getTransport();
     req.open("GET",url,true);  
     req.onreadystatechange=function(){
    	 fnHandleResponse(req);
    	// alert("get reaponse");
     };   
     req.send(null);
 	
 }else if(command=="loadMinor"){
 	url="../../../../../PhysicalVerification_Servlet?Command=loadMinor&assetmajor="+assetmajor;
		var req=getTransport();
     req.open("GET",url,true);  
     req.onreadystatechange=function(){
    	 fnHandleResponse(req);
     };   
     req.send(null);
 	
 }
}  


function addBtn()
{
	//alert(" inside ad  dbt");
	var k=0;
	//var url;
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;
     var finYear=document.getElementById("fin_year").value;
     var txtDtFrm=document.getElementById("txtDtFrm").value;
     if(txtDtFrm==null || txtDtFrm==""){
    	 alert('Enter Verification Done On Date ... ');
     }else{
    /* var verifyEmp=document.getElementById("verify_emp").value;*/
     var assetmajor=document.getElementById("cmbmajorasset").value;
     var assetminor = document.getElementById("cmbminorasset").value;
     
    var tbody = document.getElementById("grid_body");
 	 var rowcount=tbody.rows.length;
 	
   // alert(rowcount+"rowcount");
    var url="../../../../../PhysicalVerification_Servlet?Command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
    "&cmbOffice_code="+cmbOffice_code+"&finYear="+finYear+"&txtDtFrm="+txtDtFrm+"&assetmajor="+assetmajor+"&assetminor="+assetminor;
   
     for(var i=0;i<rowcount;i++)    
     {
    	 //alert(i);
    	if(document.getElementById("check"+i).checked == true){
    	    var assetCode=document.getElementById("assetCode"+i).value;
	    	 var availQty =document.getElementById("availQty"+i).value;		
				var excessQty =document.getElementById("excessQty"+i).value;
				var shQty=document.getElementById("shQty"+i).value;
				var underUsable =document.getElementById("underUsable"+i).value;
				var qtyNonuse=document.getElementById("qtyNonuse"+i).value;
				var qtyusable=document.getElementById("qtyusable"+i).value;
				var qtytobe = document.getElementById("qtytobe"+i).value;
				var Reason=document.getElementById("Reason"+i).value;
				var offcode=document.getElementById("accounting_unit_office_id"+i).value;
    	   url+="&assetCode="+assetCode+"&availQty="+availQty+"&excessQty="+excessQty+
    	 	"&shQty="+shQty+"&underUsable="+underUsable+"&qtyNonuse="+qtyNonuse+"&qtyusable="+qtyusable+
    	 	"&qtytobe="+qtytobe+"&Reason="+Reason+"&offcode="+offcode; 
    	}
    	}
 	/*//alert("rowcount"+rowcount);
 	var al= new Array() ;
    for(var i=0;i<rowcount;i++)
    	{
    	   var r=tbody.rows[i];
    	   var s=r.cells.length;
    	//  alert("iii==="+i);
	  for(var j=0;j<s;j++)
    		   {
		//  alert("j=outside ="+j);
	    		   al[k]=r.cells[j].firstChild.value;  		
	    	//alert(":::::"+al[k]);
	    		    k++; 
	    		
    		   }
    	
    	}
     
     var url="../../../../../PhysicalVerification_Servlet?Command=add&cmbAcc_UnitCode="+cmbAcc_UnitCode+
     "&cmbOffice_code="+cmbOffice_code+"&finYear="+finYear+"&txtDtFrm="+txtDtFrm+"&assetmajor="+assetmajor+"&assetminor="+assetminor+"&grid="+al;*/
   //alert(url);
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        fnHandleResponse(req);
     }; 
    req.send(null);
     }     
}
function checkValueValidation(t,rowno){
	//alert("tttt=== "+t+"  rowno --> "+rowno);
	 var tbody = document.getElementById("grid_body");
 	 var rowcount=tbody.rows.length;
 	 
 	 for(var i=0;i<rowcount;i++)    
     {
 		 if(i==rowno){
 			// alert("i=="+i);
 	    	 var assetCode=document.getElementById("assetCode"+i).value;
 	    	 var availQty =document.getElementById("availQty"+i).value;		
 				var excessQty =document.getElementById("excessQty"+i).value;
 				var shQty=document.getElementById("shQty"+i).value;
 				var underUsable =document.getElementById("underUsable"+i).value;
 				var qtyNonuse=document.getElementById("qtyNonuse"+i).value;
 				var qtyusable=document.getElementById("qtyusable"+i).value;
 				var qtytobe = document.getElementById("qtytobe"+i).value;
 				if(t==2)
 				  {
 					if(excessQty!=""){
 						document.getElementById("shQty"+i).value="";
 						document.getElementById("shQty"+i).disabled=false;
 					  if(parseInt(excessQty)>0){
 						 document.getElementById("shQty"+i).value=0;
  						document.getElementById("shQty"+i).disabled=true;
 						return false;
 					  } 
 					}else{
 						alert("Enter Excess Qty");
 					}
 				  }
 				  else if(t==3)
 				    		  {
 					 if(shQty!=""){
 				    			  if(parseInt(shQty)==0){
 				    				alert("No sht qty");	    				
 				    				//document.getElementById("shQty"+i).disabled=disabled;
 				    				document.getElementById("shQty"+i).disabled=true;
 				    				return false;
 				    			  }		    			  
 				    		  }else{
 				    			  alert("Enter Shortage Qty");
 				    		  }
 				    		  }
 			    		  else if(t==4){
 			    			// alert("j=="+j);
 			    			 // if(parseInt(QtyUsable)<=((parseInt(QtyperA52)+parseInt(ExQty))&&(parseInt(QtyperA52)-parseInt(ShQty))))
 			    			 if(underUsable!=""){
 			    			 if(parseInt(shQty)==0){
 			    			 if((parseInt(underUsable)>(parseInt(availQty)+parseInt(excessQty))))
 			    			  {	 
 			    				 
 			    				 alert("Invalid Qty Usable");
 			    				// alert("invalid underUsable Qty "+(parseInt(underUsable)+">"+(parseInt(availQty)+parseInt(excessQty))));
  			    				document.getElementById("underUsable"+i).focus();
 			    				document.getElementById("underUsable"+i).value="";
 			    				document.getElementById("qtyNonuse"+i).value="";
			    				document.getElementById("qtyusable"+i).value="";
			    				document.getElementById("qtytobe"+i).value="";
 			    				return false;
 			    			  }
 			    			  /*else{
 			    				 alert("correct underUsable Qty "+(parseInt(underUsable)+">"+(parseInt(availQty)+parseInt(excessQty))));
 			    			  }*/
 			    			 }else {
 			    				 if((parseInt(underUsable)>(parseInt(availQty)-parseInt(shQty)))){
 			    					// alert("invalid underUsable Qty else"+(parseInt(underUsable)+">+"(parseInt(availQty)-parseInt(shQty))));
 			    					 alert("Invalid Qty Usable ");
 	  			    				document.getElementById("underUsable"+i).focus();
 	 			    				document.getElementById("underUsable"+i).value="";
 	 			    				document.getElementById("qtyNonuse"+i).value="";
				    				document.getElementById("qtyusable"+i).value="";
				    				document.getElementById("qtytobe"+i).value="";
 	 			    				return false; 
 			    				 }/*else{
 			    					alert("correct underUsable Qty not 0 else"+(parseInt(underUsable)+">+"(parseInt(availQty)-parseInt(shQty))));
 			    				 }*/
 			    			 }
 			    			 }else{
 			    				 alert("Enter Qty Usable");
 			    			 }
 			    		  }	else if(t==5){
 			    			 if(qtyNonuse!=""){
 			    			 if(parseInt(shQty)==0){
 			    				 if((parseInt(qtyNonuse)>((parseInt(availQty)+parseInt(excessQty))-parseInt(underUsable))))
				    			  {	
 			    					//alert("invalid qtyNonuse qty "+(parseInt(qtyNonuse)+">"+((parseInt(availQty)+parseInt(excessQty))-parseInt(underUsable))));
				    				 alert("Invalid Qty Non-Usable" );
				    				 document.getElementById("qtyNonuse"+i).value="";
					    				document.getElementById("qtyusable"+i).value="";
					    				document.getElementById("qtytobe"+i).value="";
	 			    				document.getElementById("qtyNonuse"+i).focus();
				    				 return false;
				    			  }
				    			  /*else{
				    				  alert("correct  qtyNonuse sht 0 qty "+(parseInt(qtyNonuse)+">"+((parseInt(availQty)+parseInt(excessQty))-parseInt(underUsable))));
				    			  }*/
 			    			 }else {
 			    				 if((parseInt(qtyNonuse)>((parseInt(availQty)-parseInt(shQty))-parseInt(underUsable))) )
				    			  {	 
 			    					// alert("invalid qtyNonuse qty else"+(parseInt(qtyNonuse)+">"+((parseInt(availQty)-parseInt(shQty))-parseInt(underUsable))));
				    				 alert("Invalid Qty Non-Usable ");
				    				document.getElementById("qtyNonuse"+i).value="";
				    				document.getElementById("qtyusable"+i).value="";
				    				document.getElementById("qtytobe"+i).value="";
	 			    				document.getElementById("qtyNonuse"+i).focus();
				    				 return false;
				    			  }
				    			  /*else{
				    				  alert("correct not 0 qtyNonuse qty else"+(parseInt(qtyNonuse)+">"+((parseInt(availQty)-parseInt(shQty))-parseInt(underUsable))));
				    			  }*/
 			    				 
 			    			 }
 			    			}else{
			    				 alert("Enter Qty Non-Usable");
			    			 } 
 				    		  }	
 			    		  else if(t==6){
 				    			// alert("j=="+j);
 			    			 if(qtyusable!=""){
 			    			 if(parseInt(shQty)==0){
 			    				if((parseInt(qtyusable)>((parseInt(availQty)+parseInt(excessQty))-parseInt(underUsable)-(parseInt(qtyNonuse))))) 				    				
				    			  {	 
				    				// alert("made to be use "+(parseInt(qtyusable)+parseInt(underUsable)+parseInt(qtyNonuse)) +"if condition "+(parseInt(qtyusable)+">="+((parseInt(availQty)+parseInt(excessQty))-parseInt(underUsable)-(parseInt(qtyNonuse)))));
				    				 alert("Invalid Qty can be made usable ");
				    				document.getElementById("qtyusable"+i).value="";
				    				document.getElementById("qtytobe"+i).value="";
	 			    				document.getElementById("qtyusable"+i).focus();
				    				 return false;
				    			  }
				    			 /* else{
				    				  alert("made to be use corrct  "+(parseInt(qtyusable)+parseInt(underUsable)+parseInt(qtyNonuse)) +"if condition "+(parseInt(qtyusable)+">="+((parseInt(availQty)+parseInt(excessQty))-parseInt(underUsable)-(parseInt(qtyNonuse)))));
				    			  }*/
 			    			 }else{
 			    				if((parseInt(qtyusable)>((parseInt(availQty)-parseInt(shQty))-parseInt(underUsable)-(parseInt(qtyNonuse))))) 				    				
				    			  {	 
				    				// alert("made to be use else "+(parseInt(qtyusable)+parseInt(underUsable)+parseInt(qtyNonuse))+" else con "+(parseInt(qtyusable)+">="+((parseInt(availQty)-parseInt(shQty))-parseInt(underUsable)-(parseInt(qtyNonuse)))));
				    				 alert("Invalid Qty can be made usable ");
				    				document.getElementById("qtyusable"+i).value="";
				    				document.getElementById("qtytobe"+i).value="";
	 			    				document.getElementById("qtyusable"+i).focus();
	 			    				
				    				 return false;
				    			  }
				    			  /*else{
				    				  alert("made to be correct  else "+(parseInt(qtyusable)+parseInt(underUsable)+parseInt(qtyNonuse))+" else con "+(parseInt(qtyusable)+">="+((parseInt(availQty)-parseInt(shQty))-parseInt(underUsable)-(parseInt(qtyNonuse)))));
				    			  }*/
 			    			 }
 			    		 }else{
			    				 alert("Enter Qty can be made usable");
			    			 }  
 				    		  }	
 			    		  else if(t==7){
 				    			if(qtytobe!=""){
 				    			  if(parseInt(qtytobe)!=(parseInt(qtyNonuse)))
 				    			  {	 
 				    				// alert("invalid qtytobe qty,Enter correct valid parseInt(qtytobe)!=(parseInt(qtyNonuse))"+parseInt(qtytobe)+"!="+(parseInt(qtyNonuse)));
 				    				 alert("Invalid Qty to be Condemned /Disposed ,Enter correct value ");
 				    				document.getElementById("qtytobe"+i).value="";
 	 			    				document.getElementById("qtytobe"+i).focus();
 				    				 return false;
 				    			  }
 				    			  else{
 				    				// alert("qtytobe ok..");
 				    			  }
 			    		 }else{
			    				 alert("Enter Qty to be Condemned /Disposed");
			    			 }
 				    		  }		
 			    		  
 		 }
    	
     
     }
 	 
 	//alert("rowcount"+rowcount);
  /*  for(var i=0;i<=rowno;i++)
    	{
    	   var r=tbody.rows[i];
    	   var s=r.cells.length;

	  for(var j=0;j<s;j++)
    		   {
		//  alert("j=outside ="+j);
		  var QtyperA52=r.cells[1].firstChild.value; 
		  var ExQty =r.cells[2].firstChild.value;	
		  var ShQty=r.cells[3].firstChild.value; 	
		  var QtyUsable=r.cells[4].firstChild.value; 	
		  var QtyNoUsable=r.cells[5].firstChild.value; 	
		  var Qtycanmadeusable=r.cells[6].firstChild.value;
		  var QtytobeCondemned=r.cells[7].firstChild.value;
		/*  alert("parseInt(QtyperA52) "+parseInt(QtyperA52));
		  alert("parseInt(ExQty) "+parseInt(ExQty));
		  alert("parseInt(ShQty) "+parseInt(ShQty));
		  alert("parseInt(QtyUsable) "+parseInt(QtyUsable));
		  alert("parseInt(QtyNoUsable) "+parseInt(QtyNoUsable));
		  alert("parseInt(Qtycanmadeusable) "+parseInt(Qtycanmadeusable));
		  alert("parseInt(QtytobeCondemned) "+parseInt(QtytobeCondemned));*/
		 
		/*  if(t==2)
		  {
			  if(parseInt(ExQty)<parseInt(QtyperA52)){
				alert("exces qty ");	    				
				r.cells[3].firstChild.value=0;
				return false;
			  }		    			  
		  }
		  else if(t==3)
		    		  {
		    			  if(parseInt(ExQty)<parseInt(QtyperA52)){
		    				alert("No sht qty");	    				
		    				//r.cells[3].firstChild.disable="true";
		    				return false;
		    			  }		    			  
		    		  }
	    		  else if(t==4){
	    			 alert("j=="+j);
	    			 // if(parseInt(QtyUsable)<=((parseInt(QtyperA52)+parseInt(ExQty))&&(parseInt(QtyperA52)-parseInt(ShQty))))
	    			 if(parseInt(QtyUsable)>=(parseInt(ExQty)))
	    			  {	 
	    				 alert("QtyUsable not greater excess qty ");
	    				 return false;
	    			  }
	    			  else{
	    				 alert("QtyUsable ok..");
	    			  }
	    		  }	else if(t==5){
		    			 alert("j=="+j);
		    			  if(parseInt(QtyNoUsable)>=(parseInt(ExQty)))
		    			  {	 
		    				 alert("QtyNoUsable not greater excess qty");
		    				 return false;
		    			  }
		    			  else{
		    				 alert("QtyNoUsable ok..");
		    			  }
		    		  }	
	    		  else if(t==6){
		    			 alert("j=="+j);
		    			  if(parseInt(Qtycanmadeusable)>=(parseInt(ExQty)))
		    			  {	 
		    				 alert("Qtycanmadeusable not greater ");
		    				 return false;
		    			  }
		    			  else{
		    				 alert("Qtycanmadeusable ok..");
		    			  }
		    		  }	
	    		  else if(t==7){
		    			 alert("j=="+j);
		    			  if(parseInt(QtytobeCondemned)>=(parseInt(ExQty)))
		    			  {	 
		    				 alert("QtytobeCondemned not greater excess qty");
		    				 return false;
		    			  }
		    			  else{
		    				 alert("QtytobeCondemned ok..");
		    			  }
		    		  }		
	    		  
    		   }
    	
    	}*/
}

function checkStatus() {
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
	    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
	    var finYear=document.getElementById("fin_year").value;
	   
	    //if(nullcheck()){
	    	var url="../../../../../PhysicalVerification_Servlet?Command=checkStatus&cmbAcc_UnitCode="+cmbAcc_UnitCode+
	        "&cmbOffice_code="+cmbOffice_code+"&finYear="+finYear;
	//    alert(" checkStatus ---> "+url);
	    var req=getTransport();
	    req.open("GET",url,true); 
	    req.onreadystatechange=function()
	    {
	    	 fnHandleResponse(req);
	    }  ; 
	            req.send(null);
	   // }
	
	
}
function checkStatus1(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var d=document.getElementById("CmdAdd");
    d.disabled=false;
   
    if(flag=="freeze")
    {
    	 var d=document.getElementById("CmdAdd");
 	    d.disabled=true;
      alert("Already Frozen you can't do Physical Verification");
    }
    else if(flag=="notfreeze")
    {
    	
    	callGridItems();       
    }
    else
    {
        alert("failure");
    }
} 

//Lakshmi
function callGridItems()
{  

        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;    
        var cmbOffice_code=document.getElementById("cmbOffice_code").value;
        var finYear=document.getElementById("fin_year").value;
        var assetmajor=document.getElementById("cmbmajorasset").value;//document.phyVerification.cmbmajorasset.value;
        var assetminor = document.getElementById("cmbminorasset").value;//document.phyVerification.cmbminorasset.value;
        
        var url="../../../../../PhysicalVerification_Servlet?Command=goCmd&cmbAcc_UnitCode="+
        cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&finYear="+finYear+"&assetmajor="+assetmajor+"&assetminor="+assetminor;
      
        var req=getTransport();
     //   alert(url);
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
           fnHandleResponse(req);
        } ;  
                req.send(null);
          
}

function fnHandleResponse(req)
{ 
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
         // alert("command=="+Command);
            if(Command=="goCmd")
            {
                loadTable(baseResponse);
            }
            else if(Command=="checkStatus"){
            	checkStatus1(baseResponse);
            }
            else if(Command=="adding")
            {
            	
            	var tbody = document.getElementById("grid_body");
           	 
           //	 var table = document.getElementById("Existing");
           	 var t=0;
           	 for(t=tbody.rows.length-1;t>=0;t--)
           	     {
           	        tbody.deleteRow(0);
           	     }   
           	 
            	 if(flag=="success"){
            	alert("Record Inserted into Database Successfully");
            	 }
            	 else if (flag=="failure"){
            		 alert("Exception in insert");
            	 }else {
            		 alert("Fail in Default");
            	 }
            }  
           
            else if(Command=="loadMajor")
                {
          		  var cmbMajorClass = document.getElementById('cmbmajorasset');
          		  cmbMajorClass.length=0;
          		 // var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
          		
              	  if(flag=="success"){
              		  
              		  var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
                		  if(exists=="Yes"){
                		
              		  var mjrCode = baseResponse.getElementsByTagName('ASSET_MAJOR_CLASS_CODE');
              		  
                  	  var len = mjrCode.length;
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
         			        alert("No Major AssetCode in Table");
         			     
         			        
         			        }
              	 // fetchAlias();
              	  
                }
             else if(Command=="loadMinor")
              {
         		  var cmbMinorClass = document.getElementById('cmbminorasset');
         		cmbMinorClass.length=0;
         		 // alert("loadMinor");
         		//  var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         		
            	  if(flag=="success"){
            		 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
           		  if(exists=="Yes"){
           		
            		  var minorCode = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_CODE');
            		  
                	  var len = minorCode.length;
            	  for(i=0; i<len; i++)
            	  {
            		  minorCode = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_CODE')[i].firstChild.nodeValue;
            		  var minorDesc = baseResponse.getElementsByTagName('ASSET_MINOR_CLASS_DESC')[i].firstChild.nodeValue;
            		  var opt = document.createElement("option");
            		  opt.value = minorCode;
            		  opt.innerHTML = minorDesc;  		  
            		cmbMinorClass.appendChild(opt);
            	  }
           		 }else{
           			  alert("choose other major code...No minor ");
           		  }
            	  } 
            	  else
         		        {
            		  alert("No Minor AssetCode in Table");   
         			        }
              }
          
           
         	  }
        }
    }

function loadTable(baseResponse)
{
	var seq=0;
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="failure")
                {
                    s=0;
                    var tbody=document.getElementById("grid_body");
                      try{tbody.innerHTML="";}
                  catch(e) {tbody.innerText="";}
                  
                     
                    alert("No Record exists");
                }
                else
                {                       
                     service=baseResponse.getElementsByTagName("leng");
                    
                    if(service)
                    { 
//                    	 acc_code=baseResponse.getElementsByTagName("acc_code")[0].firstChild.nodeValue;
//                         year_qty=baseResponse.getElementsByTagName("year_qty")[0].firstChild.nodeValue;
                                   
                        var i=0;
                        var c=0;
                        var cell2;
                        
                        var tbody=document.getElementById("grid_body");
                        try{tbody.innerHTML="";}
                        catch(e) {tbody.innerText="";}  
                        
                        
                        for(i=0;i<service.length;i++)
                        {
                       
                                c++;
                                 var items=new Array();
                                items[0]=service[i].getElementsByTagName("acc_code")[0].firstChild.nodeValue;
                                items[1]=service[i].getElementsByTagName("year_qty")[0].firstChild.nodeValue;
                                items[2]=service[i].getElementsByTagName("accounting_unit_office_id")[0].firstChild.nodeValue;
                                items[3]=service[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
                                
                                var tbody=document.getElementById("grid_body");
                                var mycurrent_row=document.createElement("TR");
                                
                                var cell22=document.createElement("TD");
                              //  cell2.setAttribute('align','right');
                                var chk_code=document.createElement("input");
                                chk_code.type="checkbox";
                                chk_code.name="check"+seq;
                                chk_code.id="check"+seq;
                                chk_code.value=seq;
                                cell22.appendChild(chk_code);
                            
                                mycurrent_row.appendChild(cell22);
                                
                                                           
                                var cell2=document.createElement("TD");
                               cell2.setAttribute('align','right');
                               var a_code=document.createElement("input");
                               a_code.type="hidden";
                               a_code.name="assetCode"+seq;
                               a_code.id="assetCode"+seq;
                               a_code.value=items[0];
                               cell2.appendChild(a_code);
                               var currentText=document.createTextNode(items[0]);
                               cell2.appendChild(currentText);
                               mycurrent_row.appendChild(cell2);
                               
                               
                               var cell21=document.createElement("TD");
                               
                               var rem_code=document.createElement("input");
                               rem_code.type="hidden";
                               rem_code.name="rema"+seq;
                               rem_code.id="rema"+seq;
                               rem_code.value=items[3];
                               cell21.appendChild(rem_code);
                               var currentText=document.createTextNode(items[3]);
                               cell21.appendChild(currentText);
                               mycurrent_row.appendChild(cell21);
                               
                               var cell3=document.createElement("TD");
                               cell3.setAttribute('align','right');
                               var avl_qty=document.createElement("input");
                               avl_qty.type="hidden";
                               avl_qty.name="availQty"+seq;
                               avl_qty.id="availQty"+seq;
                               avl_qty.value=items[1];
                               cell3.appendChild(avl_qty);
                               var currentText=document.createTextNode(items[1]);
                             
                               cell3.appendChild(currentText);
                              
                               mycurrent_row.appendChild(cell3);
                               
                               
                               var cell4 = document.createElement("TD");
                               var excess_qty = document.createElement("input");
                               excess_qty.type = "text";
                               excess_qty.name = "excessQty"+seq;
                               excess_qty.id = "excessQty"+seq; 
                               excess_qty.size="7";
                               excess_qty.setAttribute("onkeypress", "return numbersonly1(event,this)");
                               excess_qty.setAttribute("onblur", "return checkValueValidation(2,"+seq+")");
                              // excess_qty.onkeypress="return numbersonly(event)";
                               cell4.appendChild(excess_qty);
                               mycurrent_row.appendChild(cell4);
                               
                               var cell5 = document.createElement("TD");
                               var sh_Qty = document.createElement("input");
                               sh_Qty.type = "text";
                               sh_Qty.name = "shQty"+seq;
                               sh_Qty.id = "shQty"+seq;
                               sh_Qty.size="7";
                               sh_Qty.setAttribute("onkeypress", "return numbersonly1(event,this)");
                               sh_Qty.setAttribute("onchange", "return checkValueValidation(3,"+seq+")");
                               cell5.appendChild(sh_Qty);
                               mycurrent_row.appendChild(cell5);
                               
                                /*cell2=document.createElement("TD");
                                var check="";
                       			check=document.createElement("input");
                       			check.type="checkbox";
                       			check.name="working";
                       			check.id="working";
                       			check.value="Y";
                       			check.size="22";
                                cell2.appendChild(check);
                                mycurrent_row.appendChild(cell2);*/
                               
                               var cell6 = document.createElement("TD");
                               var under_usable = document.createElement("input");
                               under_usable.type = "text";
                               under_usable.name = "underUsable"+seq;
                               under_usable.id = "underUsable"+seq; 
                               under_usable.size="7";
                               under_usable.setAttribute("onkeypress", "return numbersonly1(event,this)");
                               under_usable.setAttribute("onchange", "return checkValueValidation(4,"+seq+")");
                               cell6.appendChild(under_usable);
                               mycurrent_row.appendChild(cell6);
                               
                              /* cell2 = document.createElement("TD");
                               var check1="";
                               check1=document.createElement("input");
                               check1.type="checkbox";
                               check1.name="canbeUsable";
                               check1.id="canbeUsable";
                               check1.value="Y";
                               cell2.appendChild(check1);
                              mycurrent_row.appendChild(cell2);*/
                               
                               var cell7 = document.createElement("TD");
                               var qty_nonuse = document.createElement("input");
                               qty_nonuse.type = "text";
                               qty_nonuse.name = "qtyNonuse"+seq;
                               qty_nonuse.id = "qtyNonuse"+seq; 
                               qty_nonuse.size="7";
                               qty_nonuse.setAttribute("onkeypress", "return numbersonly1(event,this)");
                               qty_nonuse.setAttribute("onchange", "return checkValueValidation(5,"+seq+")");
                               cell7.appendChild(qty_nonuse);
                               mycurrent_row.appendChild(cell7);
                               
                                /*cell2 = document.createElement("TD");
                               var canbe_usable = document.createElement("input");
                               canbe_usable.type = "checkbox";
                               canbe_usable.name = "whetherusable";
                               canbe_usable.id = "whetherusable";
                               canbe_usable.value="Y";
                               cell2.appendChild(canbe_usable);
                               mycurrent_row.appendChild(cell2);*/
                               
                              var cell8 = document.createElement("TD");
                               var whether_usable = document.createElement("input");
                               whether_usable.type = "text";
                               whether_usable.name = "qtyusable"+seq;
                               whether_usable.id = "qtyusable"+seq;
                               whether_usable.size="7";
                               whether_usable.setAttribute("onkeypress", "return numbersonly1(event,this)");
                               whether_usable.setAttribute("onchange", "return checkValueValidation(6,"+seq+")");
                               cell8.appendChild(whether_usable);
                               mycurrent_row.appendChild(cell8);

/*
                             cell2 = document.createElement("TD");
                               var qty_usable = document.createElement("input");
                               qty_usable.type = "checkbox";
                               qty_usable.name = "tobecondemned";
                               qty_usable.id = "tobecondemned"; 
                               qty_usable.value="Y";
                               cell2.appendChild(qty_usable);
                              // mycurrent_row.appendChild(cell2);
*/                               
                              var cell9 = document.createElement("TD");
                               var to_be = document.createElement("input");
                               to_be.type = "text";
                               to_be.name = "qtytobe"+seq;
                               to_be.id = "qtytobe"+seq; 
                               to_be.size="7";
                               to_be.setAttribute("onkeypress", "return numbersonly1(event,this)");
                               to_be.setAttribute("onchange", "return checkValueValidation(7,"+seq+")");
                               cell9.appendChild(to_be);
                               mycurrent_row.appendChild(cell9); 
                               
                               
                               var cell10 = document.createElement("TD");
                               var Reason_for_Variation = document.createElement('TEXTAREA','option1');
                               Reason_for_Variation.name = "Reason"+seq;
                               Reason_for_Variation.id = "Reason"+seq;
                               Reason_for_Variation.setAttribute("cols", "4");
                               Reason_for_Variation.style.height = "40px";
                               Reason_for_Variation.style.width = "120px";
                               cell10.appendChild(Reason_for_Variation);
                               mycurrent_row.appendChild(cell10);
                              
                               
                               var cell11=document.createElement("TD"); 
                               cell11.style.display="none";
                               var aoff_code=document.createElement("input");
                               aoff_code.type="hidden";
                               aoff_code.name="accounting_unit_office_id"+seq;
                               aoff_code.id="accounting_unit_office_id"+seq;
                               aoff_code.value=items[2];
                               cell11.appendChild(aoff_code);
                               mycurrent_row.appendChild(cell11);
                               
                                tbody.appendChild(mycurrent_row);
                                seq++;
                            }
                           
                        }
                   }
}



function btncancel()
{

 self.close();
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



/////////////////////////////////////////////   Check Date() by User /////////////////////////////////////////////////////
function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }
function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }
function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }catch(e){
         
       ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
         try{
             var f=isValidDate(c);
            }
        catch(e){
         
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            
            if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
             if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false;
    }
    
}

