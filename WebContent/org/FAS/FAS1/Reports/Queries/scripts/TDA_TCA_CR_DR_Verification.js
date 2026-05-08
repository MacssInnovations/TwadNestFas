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
	  
 		var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
        var txtCB_Year=document.getElementById("txtCB_Year").value;
        
        	  url="../../../../../../TDA_TCA_CR_DR_Verification?" +"command=verify_cr_dr&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year;
	       //  alert("Loading");
	         req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate_tda(req);
	         }   
	         req.send(null);  
		        
}


function manipulate_tda(req)
    {
    if(req.readyState==4)
      {
          if(req.status==200)
          {
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
               var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
               var command=tagCommand.firstChild.nodeValue; 
               
                   if(command=="verify_cr_dr")
                  {
                	   verify_cr_dr_checking(baseResponse);
                     
                  }
                 
          }
      }
}
function verify_cr_dr_checking(baseResponse)
{
	var c_mn;
         var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       var incr=0;
         if(flag=="success"){
         
        	   var count=baseResponse.getElementsByTagName("month");
             var tbody=document.getElementById("grid_body1");
             try{tbody.innerHTML="";}
             catch(e) {tbody.innerText="";}               
             for(var i=0;i<count.length;i++)
             {                                    	   	   
                     var month=baseResponse.getElementsByTagName("month")[i].firstChild.nodeValue;
                     var achead=baseResponse.getElementsByTagName("achead")[i].firstChild.nodeValue;
                     var trndr=baseResponse.getElementsByTagName("trndr")[i].firstChild.nodeValue;
                     var trncr=baseResponse.getElementsByTagName("trncr")[i].firstChild.nodeValue;
                     
                     var Tda_Dr=baseResponse.getElementsByTagName("Tda_Dr")[i].firstChild.nodeValue;
                     var Tda_Cr=baseResponse.getElementsByTagName("Tda_Cr")[i].firstChild.nodeValue;
                     var Trn_Net=baseResponse.getElementsByTagName("Trn_Net")[i].firstChild.nodeValue;
                     var Tda_Net=baseResponse.getElementsByTagName("Tda_Net")[i].firstChild.nodeValue;
                     var difference=baseResponse.getElementsByTagName("difference")[i].firstChild.nodeValue;
                     
                     
                     if(month==1)
                     {
                    	c_mn="January";
                     }
                     else if(month==2)
                     {
                     	c_mn="February";
                     }
                     else if(month==3)
                     {
                     	c_mn="March";
                     }
                     else if(month==4)
                     {
                     	c_mn="April";
                     }
                     else if(month==5)
                     {
                     	c_mn="May";
                     }
                     else if(month==6)
                     {
                     	c_mn="June";
                     }
                     else if(month==7)
                     {
                     	c_mn="July";
                     }
                     else if(month==8)
                     {
                     	c_mn="August";
                     }
                     else if(month==9)
                     {
                     	c_mn="September";
                     }
                     else if(month==10)
                     {
                     	c_mn="October";
                     }
                     else if(month==11)
                     {
                     	c_mn="November";
                     }
                     else if(month==12)
                     {
                     	c_mn="December";
                     }
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=seq;
                
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(c_mn);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                
                     var cell2=document.createElement("TD");   	                                           
                     var currentText1=document.createTextNode(achead);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
         
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(trndr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Tda_Dr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                     
                     var cell2=document.createElement("TD");                                              
                     var currentText1=document.createTextNode(trncr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2);
                    
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Tda_Cr);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Trn_Net);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(Tda_Net);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     var cell2=document.createElement("TD");
                     var currentText1=document.createTextNode(difference);
                     cell2.appendChild(currentText1);
                     mycurrent_row.appendChild(cell2); 
                     
                     tbody.appendChild(mycurrent_row);
                     seq++;	                                          
             }
             
               
             }
             else
             {
          
             var tbody=document.getElementById("grid_body1");
             try{tbody.innerHTML="";}
         catch(e) {tbody.innerText="";} 
         alert("No Record Exist");
         
            
             
             }
}