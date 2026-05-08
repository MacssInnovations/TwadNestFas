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


function callGrid()
{     
				  var tbody=document.getElementById("grid_body1");
			      try{tbody.innerHTML="";}
			  catch(e) {tbody.innerText="";} 
			  
			  
			  document.getElementById("one_id").style.display="none";
         	 document.getElementById("two_id").style.display="block";
			  
         	document.getElementById("txtParticular").value="";
		 		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
		        var txtCB_Year=document.getElementById("txtCB_Year").value;
		        if(document.tda_tca_grid.txtoption[0].checked==true)
		        {
		        	
		        	 document.getElementById("hTable1").style.display="block";
		        	 document.getElementById("hTable2").style.display="none";
		        	 document.getElementById("hTable3").style.display="none";
		        	
		        	  url="../../../../../../Twad_common_servlet?" +"command=marReg&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year;
			        // alert(url);
			         req=getTransport();
			         req.open("GET",url,true);        
			         req.onreadystatechange=function()
			         {        	  
			                manipulate_tda(req);
			         }   
			         req.send(null);  
		        }
		        else if(document.tda_tca_grid.txtoption[1].checked==true)
		        {
		        	
		        	 document.getElementById("hTable1").style.display="none";
		        	 document.getElementById("hTable2").style.display="block";
		        	 document.getElementById("hTable3").style.display="none";
	        		
		            url="../../../../../../Twad_common_servlet?command=marSupp&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year;
		             req=getTransport();
		            req.open("GET",url,true);        
		            req.onreadystatechange=function()
		            {        	  
		            	manipulate_tda(req);
		            }   
		            req.send(null);  
		        }
		        else if(document.tda_tca_grid.txtoption[2].checked==true)
		        {
		        	
		        	 document.getElementById("hTable1").style.display="none";
		        	 document.getElementById("hTable2").style.display="none";
		        	 document.getElementById("hTable3").style.display="block";
	        		
		            url="../../../../../../Twad_common_servlet?command=aprReg&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year;
		             req=getTransport();
		            req.open("GET",url,true);        
		            req.onreadystatechange=function()
		            {        	  
		            	manipulate_tda(req);
		            }   
		            req.send(null);  
		        }
        
}


function  manipulate_tda(req)
    {
    if(req.readyState==4)
      {
          if(req.status==200)
          {
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
               var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
               var command=tagCommand.firstChild.nodeValue; 
               
                   if(command=="marReg")
                  {
                	   marReg_checking(baseResponse);
                     
                  }
                  else if(command=="marSupp")
                  {
                	  marReg_checking(baseResponse);
                     
                  }
                  else if(command=="aprReg")
                  {
                	  marReg_checking(baseResponse);
                     
                  }
                  
                 
          }
      }
}  
function marReg_checking(baseResponse)
    {
             var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
           var incr=0;
             if(flag=="success"){
             
            	   var count=baseResponse.getElementsByTagName("Dif_march");
                 var tbody=document.getElementById("grid_body1");
                 try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";}               
                 for(var i=0;i<count.length;i++)
                 {                                    	   	   
                         var accountCode=baseResponse.getElementsByTagName("accountCode")[i].firstChild.nodeValue;
                         var Dramt=baseResponse.getElementsByTagName("Dramt")[i].firstChild.nodeValue;
                         var Cramt=baseResponse.getElementsByTagName("Cramt")[i].firstChild.nodeValue;
                         var netTrn=baseResponse.getElementsByTagName("netTrn")[i].firstChild.nodeValue;
                         
                         var hcode=baseResponse.getElementsByTagName("hcode")[i].firstChild.nodeValue;
                         var drA=baseResponse.getElementsByTagName("drA")[i].firstChild.nodeValue;
                         var crA=baseResponse.getElementsByTagName("crA")[i].firstChild.nodeValue;
                         var netTrial=baseResponse.getElementsByTagName("netTrial")[i].firstChild.nodeValue;
                         var Dif_march=baseResponse.getElementsByTagName("Dif_march")[i].firstChild.nodeValue;
                         
                         
                        // alert(":::"+accountCode);
                         if(accountCode=="null")
                         {
                        	 if(hcode!="null"){
                        	 accountCode=hcode;
                        	 }
                         }
                         else if(hcode=="null")
                         {
                        	 if(accountCode!="null"){
                            	 accountCode=accountCode;
                            	 }
                         }
                        
                         //countId
//                         if(Dif_march=="Tally")
//                         {
//                        	 incr++;
//                         }
                         
                         
                         var mycurrent_row=document.createElement("TR");
                         mycurrent_row.id=seq;
                    
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(accountCode);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                    
                         var cell2=document.createElement("TD");   	                                           
                         var currentText1=document.createTextNode(Dramt);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
             
                         var cell2=document.createElement("TD");                                              
                         var currentText1=document.createTextNode(Cramt);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");                                              
                         var currentText1=document.createTextNode(netTrn);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(drA);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(crA);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2);
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(netTrial);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                         var cell2=document.createElement("TD");
                         var currentText1=document.createTextNode(Dif_march);
                         cell2.appendChild(currentText1);
                         mycurrent_row.appendChild(cell2); 
                         
                         
                         
                         tbody.appendChild(mycurrent_row);
                         seq++;	                                          
                 }
                 if(seq>0)
                 {
                	 document.getElementById("one_id").style.display="block";
                	 document.getElementById("two_id").style.display="none";
                 }
	               
                 }
                 else
                 {
              
                 var tbody=document.getElementById("grid_body1");
                 try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";} 
             alert("No Record Exist");
             
                 document.getElementById("one_id").style.display="none";
            	 document.getElementById("two_id").style.display="block";
                
                //  document.forms[0].advnumber.value="0";
                 
                 }
    }
 function  changeAdvNochecking(baseResponse)
 {
 
 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         
             if(flag=="success"){ 
             var tda_type = baseResponse.getElementsByTagName("tda_type")[0].firstChild.nodeValue;
           
             document.acceptanceDebitOrCredit.tdaType.value=tda_type;
             }
 }

function checkNull()
{
	
	var tbody=document.getElementById("grid_body1");
	if(tbody.rows.length==0)
	{
	    alert("Enter the Details Part");
	    return false; 
	}

	if(document.getElementById("txtParticular").value=="")
	{
		 alert("Enter Particulars");
		    return false; 
	}
    
    
 return true;
}
function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
        {
          
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false;
        }
}