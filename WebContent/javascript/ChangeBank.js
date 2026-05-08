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

function getSelectedBranch(bankId,dest,slct){
	
	 var url= 'Chbnk_branch.html?bankId='+bankId+"&rnd="+new Date().getTime();
	 var HttpRequest = getTransport();
     HttpRequest.open("GET", url, true);
     HttpRequest.onreadystatechange=function()
     {    
    	 iterateList1(HttpRequest, dest,slct);
     };   
     HttpRequest.send(null);
	
}


function iterateList1(HttpRequest,dest,slct)
{
  	if(HttpRequest.readyState == 4)
	{
		if(HttpRequest.status == 200)
		{
        	var root = HttpRequest.responseXML.getElementsByTagName('response')[0]; 
        	var flag = root.getElementsByTagName('flag')[0].firstChild.nodeValue;
        	 if(flag == "false")
            {   
            	for(var i = dest.length; i > 0; i--)
            	{
            		dest.options[i] = null;
				}          
            }
            else
            {            	
            	var listCount = root.getElementsByTagName('record');
            	     	for(i = dest.length; i > 0; i--)
            	     	{
            	           	dest.options[i] = null;
          	            }
    
            	var str="";

                    for(i = 0; i < listCount.length; i++)
                    {	
            		var code = root.getElementsByTagName('code')[i].firstChild.nodeValue;
					var name = root.getElementsByTagName('name')[i].firstChild.nodeValue;
								
					dest.options[i+1] = new Option(name,code);
					str+=code+" : "+slct+"\n";
					
					  if(code==slct)
					    {					
					    str+="Ok\n";	
					   dest.options[i+1].selected=true;
					   }	
				   }
              }             	           	
         }
     }
}



function changeBk(ppoNo)
{
	   // alert("im in change bank...") ;
	    var url="changeOfBank.html?newobj.ppoNo="+ppoNo;
		var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
         processResponse(req);       
        }   
        req.send(null);	
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
         var flag = baseResponse.getElementsByTagName('flag')[0].firstChild.nodeValue;
         var len=baseResponse.getElementsByTagName("record").length;
         if(len==0)
        	 {
        	 
        	 document.getElementById("ChBnkPpoNo").value="";
        	 document.getElementById("ChBnkEmpNo").value="";
        	 document.getElementById("ChBnkEmpName").value="";
        	 document.getElementById("ChBnkBankAcNo").value="";
        	 document.getElementById("ChBnkBankAcNo1").value="";
        	 document.getElementById("bankId1").value="";	
        	 document.getElementById("bankId").value="";
        	 document.getElementById("branchId1").value="";
        	 document.getElementById("branchId").value="";
        	 alert('No Record For the Respective Data ');
        	 alert('Please Fetch Some Other PPO Number...');
        	 
        	 }
         
        if(len>0)
         {    
          if(command=="bank")
           {
            if(flag)
        	 {
        	  document.getElementById("ChBnkBankAcNo").value=req.responseXML.getElementsByTagName("bankAcNo")[0].firstChild.nodeValue;
        	  document.getElementById("ChBnkBankAcNo1").value=req.responseXML.getElementsByTagName("bankAcNo")[0].firstChild.nodeValue;
        	  document.getElementById("bankId1").value=req.responseXML.getElementsByTagName("bankId")[0].firstChild.nodeValue
        	  document.getElementById("bankId").value=req.responseXML.getElementsByTagName("bankId")[0].firstChild.nodeValue
        	  var bnkid=req.responseXML.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        	  var brnchid=req.responseXML.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        	  getSelectedBranch(bnkid,document.getElementById('branchId1'),brnchid);
        	  getSelectedBranch(bnkid,document.getElementById('branchId'),brnchid);
        	  enablebuttons();            	    
        	 }
           }
        }    	  
      }
   }
}
        	
function enablebuttons()

{	
document.getElementById("Clear").disabled = false;
document.getElementById("Submit").disabled = false; 
}

function fetchBranch(bankId,dest){
	 
	 var url = 'chbnk.html?bankId='+bankId;
	 var HttpRequest = getTransport();
     HttpRequest.open("GET", url, true);
     HttpRequest.onreadystatechange=function()
     {    
    	 iterateList2(HttpRequest, dest);
     };   
     HttpRequest.send(null);
	
}
     
function iterateList2(HttpRequest, dest) {

	
	if(HttpRequest.readyState == 4){
		if(HttpRequest.status == 200){
        	var root = HttpRequest.responseXML.getElementsByTagName('response')[0]; 
        	alert("root"+root);
        	var flag = root.getElementsByTagName('flag')[0].firstChild.nodeValue;
        	alert("flag"+flag);
            if(flag == "false") {   
            	for(var i = dest.length; i > 0; i--){
            		dest.options[i] = null;
				}          
            }
            else {            	
            	var listCount = root.getElementsByTagName('record');
                  	for(i = dest.length; i > 0; i--){
            		dest.options[i] = null;
				}	
            	for(i = 0; i < listCount.length; i++){	
            		
					var code = root.getElementsByTagName('code')[i].firstChild.nodeValue;
					var name = root.getElementsByTagName('name')[i].firstChild.nodeValue;
					dest.options[i+1] = new Option(name,code);
					}	            	
             }
         }
	 }
 }
       
// for Validate


        	
