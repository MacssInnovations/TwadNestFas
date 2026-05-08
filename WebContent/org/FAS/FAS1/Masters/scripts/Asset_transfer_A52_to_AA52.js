

var assetCodeArr=new Array();

function ajaxFun()
{
	
	var req = false;
	try 
	{
		req = new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch(e) 
	{
		try
		{
			
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}
		catch(e1) 
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

function test(req) 
{
	if (req.readyState==4) 
	{
		if (req.status==200) 
		{
			var baseResponse = req.responseXML.getElementsByTagName("response")[0];
			var tagCommand = baseResponse.getElementsByTagName("command")[0];
			var command = tagCommand.firstChild.nodeValue;

			if (command == "submi") 
			{
				
				subm(baseResponse);
			}
			
			else if(command=="dis")
			{
				dis_row(baseResponse);
			}
			
		}
	}
}


function disp()
{
	var req = ajaxFun();
	var url = "../../../../../Asset_transfer_A52_to_AA52?command=dis";
	//alert(url);
	req.open("GET", url, true);
	req.onreadystatechange = function() 
	{
		
		test(req);
	}
	req.send(null);

}

function callServer(command)  {  
	 
	var accounting_unit_id=document.frmtransfer_A52_AA52.cmbAcc_UnitCode.value;
var accounting_unit_office_id = document.frmtransfer_A52_AA52.cmbOffice_code.value;
var assetmajor=document.frmtransfer_A52_AA52.cmbmajorasset.value;
var financial_year = document.frmtransfer_A52_AA52.cmbFinancialYear.value; 

var url="";

if(command=="loadMajor"){

 	url="../../../../../Asset_transfer_A52_to_AA52?command=loadMajor";
 	
		var req=getTransport();
		//alert("loadMajor ");
     req.open("GET",url,true);  
    
     req.onreadystatechange=function(){
        processResponse(req);
     };   
     req.send(null);
 	
 }
else if(command=="checkStatus"){  

	//if(checkNull()){
 	url="../../../../../Asset_transfer_A52_to_AA52?command=checkStatus&unit_id="+accounting_unit_id+"&office_id="+accounting_unit_office_id+"&financial_year="+financial_year+"&assetmajor="+assetmajor;

		var req=getTransport();
     req.open("GET",url,true);  
     req.onreadystatechange=function(){
     	processResponse(req);
     };   
     req.send(null);
 	// }
}
}
function processResponse(req)
{   
if(req.readyState==4)
{
if(req.status==200)
{   
//alert('Enter in response');
var baseResponse=req.responseXML.getElementsByTagName("response")[0];
   //alert("baseResponse "+baseResponse);
   var tagCommand=baseResponse.getElementsByTagName("command")[0];
   
   var command=tagCommand.firstChild.nodeValue; 
 // alert("command=="+command);
	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
	 //   alert("Flag command "+flag+"------------"+command);   

	 
       
       if(command=="loadMajor")
       {
    	 loadMajo(baseResponse);
 	  
       }
     
     else if(command=="checkStatus")
     {
   	  // alert("command  inside checkStatus ");
   	  if(flag=="success")
   	  {
   		 var exists=baseResponse.getElementsByTagName("exists")[0].firstChild.nodeValue;
   		// alert("exists"+exists);
   		  if(exists=="Yes"){
   			  
   			 var accunitid=baseResponse.getElementsByTagName("ACCOUNTING_UNIT_ID")[0].firstChild.nodeValue;
   			 var a52status=baseResponse.getElementsByTagName("A52_STATUS")[0].firstChild.nodeValue;
   			// alert("a52status "+a52status);
   			 if(a52status=="Y"){
   				 disp();
   				/*alert("You already Freezed A52 Unit,So you cant edit the content"); 
   				//clearAll();
*/   			 }
   			// return true;
   			 
   		  }else {
   			  
   			 // alert("Not verify");
   			 alert(" Verification Freeze not done");
   			 //return true;
   		  }
   		// return true;
   	  }
   	  else
         {
   		  alert("Failed to check.");
   		 
         }
     }
	  }
}
}
function loadMajo(baseResponse){
	
	  var cmbMajorClass = document.getElementById("cmbmajorasset");
	  cmbMajorClass.length=0;
	  var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

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
}

var seq=0;
function dis_row(baseResponse)
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
                                  
           var len=baseResponse.getElementsByTagName("asset_code").length;  
           for(var k=0;k<len;k++)
               {
        	        var asset_code= baseResponse.getElementsByTagName("asset_code")[k].firstChild.nodeValue;
        	        var asset_maj_code= baseResponse.getElementsByTagName("asset_maj_code")[k].firstChild.nodeValue;
        	        var minor_code= baseResponse.getElementsByTagName("minor_code")[k].firstChild.nodeValue;
          	       // alert(asset_maj_code);
                    var phy_loc= baseResponse.getElementsByTagName("phy_loc")[k].firstChild.nodeValue;
                    var phy_loc_verdat = baseResponse.getElementsByTagName("phy_loc_verdat")[k].firstChild.nodeValue;
                    var obs_item = baseResponse.getElementsByTagName("obs_item")[k].firstChild.nodeValue;
                    var dep_dat = baseResponse.getElementsByTagName("dep_dat")[k].firstChild.nodeValue;
                    var pro_dep_dat= baseResponse.getElementsByTagName("pro_dep_dat")[k].firstChild.nodeValue;
                    var dep_cost= baseResponse.getElementsByTagName("dep_cost")[k].firstChild.nodeValue;
                    //var pro_dep_cost = baseResponse.getElementsByTagName("pro_dep_cost")[k].firstChild.nodeValue;
                    var clos_bal = baseResponse.getElementsByTagName("clos_bal")[k].firstChild.nodeValue;
                    var acc_unitid = baseResponse.getElementsByTagName("acc_unitid")[k].firstChild.nodeValue;
                    var acc_offid = baseResponse.getElementsByTagName("acc_offid")[k].firstChild.nodeValue;
                    var remarks = baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
                    var fin_yr = baseResponse.getElementsByTagName("fin_yr")[k].firstChild.nodeValue;
                    //var app_grant = baseResponse.getElementsByTagName("app_grant")[k].firstChild.nodeValue;
                    var mycurrent_row=document.createElement("TR");
                    mycurrent_row.id=seq;
                    var cell=document.createElement("TD");   
                    var anc=document.createElement("A");       
                    var url="javascript:viewDetails('" +seq + "')";
                    anc.href=url;
                    var sch_id=document.createElement("input");
			        sch_id.type="checkbox";
			        sch_id.name="accName";
			        sch_id.id="id"+seq;
			        sch_id.value=asset_code+"/"+minor_code+"/"+acc_unitid+"/"+acc_offid+"/"+fin_yr+"/"+dep_dat+"/"+clos_bal+"/"+dep_cost+"/"+asset_maj_code;
			        sch_id.checked = false;
			        sch_id.setAttribute("onclick", "viewDetails('" + seq + "');");
			        cell.appendChild(sch_id);
					mycurrent_row.appendChild(cell);
                    var cell2 =document.createElement("TD"); 
                    var tasset_code=document.createTextNode(asset_code);     
                    cell2.appendChild(tasset_code);       
                    mycurrent_row.appendChild(cell2);  

				/*	var cell3 =document.createElement("TD"); 
                    var tphy_loc=document.createTextNode(phy_loc);     
                    cell3.appendChild(tphy_loc);       
                    mycurrent_row.appendChild(cell3); */
                	var cell3 =document.createElement("TD"); 
                    var tphy_loc=document.createTextNode(remarks);     
                    cell3.appendChild(tphy_loc);       
                    mycurrent_row.appendChild(cell3); 
                   
                    var cell4 =document.createElement("TD");    
                    var tphy_loc_verdat=document.createTextNode(phy_loc_verdat);                         
                    cell4.appendChild(tphy_loc_verdat);       
                    mycurrent_row.appendChild(cell4);
                    
                    var cell5 =document.createElement("TD");    
                    var tobs_item=document.createTextNode(obs_item);                         
                    cell5.appendChild(tobs_item);       
                    mycurrent_row.appendChild(cell5);
                            
                    var cell6 =document.createElement("TD");    
                    var tdep_dat=document.createTextNode(dep_dat);                         
                    cell6.appendChild(tdep_dat);       
                    mycurrent_row.appendChild(cell6);
                    
                    var cell7 =document.createElement("TD");    
                    var tpro_dep_dat=document.createTextNode(pro_dep_dat);                         
                    cell7.appendChild(tpro_dep_dat);
                    mycurrent_row.appendChild(cell7);
                    
                    var cell8=document.createElement("TD");    
                    var tdep_cost=document.createTextNode(dep_cost);                         
                    cell8.appendChild(tdep_cost);       
                    mycurrent_row.appendChild(cell8);
//                    var cell9=document.createElement("TD");    
//                    var tpro_dep_cost =document.createTextNode(pro_dep_cost );                         
//                    cell9.appendChild(tpro_dep_cost );       
//                    mycurrent_row.appendChild(cell9);
               /*     var cell9 = document.createElement("TD");
                    var tpro_dep_cost = document.createElement("input");
                    tpro_dep_cost.type="text";
                    tpro_dep_cost.name="tpro_dep_cost";
                    tpro_dep_cost.id="tpro_dep_cost";
                    tpro_dep_cost.value=0;
                    tpro_dep_cost.maxLength=14;
                    tpro_dep_cost.size=8;
			        cell9.appendChild(tpro_dep_cost);       
                    mycurrent_row.appendChild(cell9);*/
                    
                    var cell10 =document.createElement("TD");    
                    var tclos_bal =document.createTextNode(clos_bal );                         
                    cell10.appendChild(tclos_bal );       
                    mycurrent_row.appendChild(cell10);
                    
                    var cell11 = document.createElement("TD");
                    /*   var tpro_clos_bal = document.createElement("input");
                 tpro_clos_bal.type="text";
                    tpro_clos_bal.name="tpro_clos_bal";
                    tpro_clos_bal.id="tpro_clos_bal";
                  
                    tpro_clos_bal.value=0;
                    tpro_clos_bal.maxLength=14;
                    tpro_clos_bal.size=8;*/
                    var tpro_clos_bal =document.createTextNode(0);                         
			        cell11.appendChild(tpro_clos_bal);       
                    mycurrent_row.appendChild(cell11);
                    
                                      
                    tbody.appendChild(mycurrent_row);
                    seq++;
                    //alert("seq::::"+seq);
               }
               }
            else if(flag=="nodata")
            {
    	         alert("No data");
               }
    		 else
    		 {
     			 alert("Failed to Load Values");
   			 }
	 		}
	 		catch(e){
	 		}

}



function submi()
{
	var req = ajaxFun();
	
	var r=confirm("Do u want to Move this record to AA52 Register?");
	//alert("confirm");
     if(r==true)
     {
		
		var url = "../../../../../Asset_transfer_A52_to_AA52?command=subm&asset_code="+assetCodeArr;
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			test(req);
		};
		req.send(null);
		
		    try{
			         document.getElementById('tblList').innerHTML="";
		           }catch(e){
			         document.getElementById('tblList').innerText="";
		           }
		alert("Record Moved to AA52 Register.");
		disp();
	}
	else
	{
		alert("Record Not Moved.");
		
	}

}


function subm()
{
	
	var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;

	if (flag == "success")
	{
		
		clearAll();
	}else 
	{
		alert("Flag Failure.");
	}
	

}



 function viewDetails(id){	 
	 var jid=document.getElementById("id"+id).value;
	 if(document.getElementById("id"+id).checked==true){		 
		assetCodeArr.push(jid);
		//alert(assetCodeArr);
	 }
	 if(document.getElementById("id"+id).checked==false){
		 var rem=document.getElementById("id"+id).value;
		 for(var x=0; x<assetCodeArr.length; x++){
			 if(assetCodeArr[x]==rem){
				 assetCodeArr.splice(x,1); 
				 //alert(assetCodeArr);
			 }
		 }		 
	 }	
 }

//clearin all rows
function clearAll(){

}
