
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
	var employeeId=document.forms[0].employeeId.value;
	
if(command=="fetch")
		  {
			
			
	url="../fetchMenu.html?mstcutoffentry.employeeId="+employeeId;
			var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
              
            }   
            req.send(null);
		  }
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
          if(command=="fetch")
          {
             addFetch(baseResponse);                 
          }
    	  
      }
   }
}


function addFetch(baseResponse)
{
	
	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(flag=="success")
	{	
       document.forms[0].pensionerInitial.value=baseResponse.getElementsByTagName("pensionerInitial")[0].firstChild.nodeValue;
       document.forms[0].pensionerName.value=baseResponse.getElementsByTagName("pensionerName")[0].firstChild.nodeValue;
       document.forms[0].dateOfBirth.value=baseResponse.getElementsByTagName("dateOfBirth")[0].firstChild.nodeValue; 
     var g=baseResponse.getElementsByTagName("gender")[0].firstChild.nodeValue;
    //   var p=baseResponse.getElementsByTagName("titleId")[0].firstChild.nodeValue;
//      alert("????????????????"+g);
//      // alert(">>>>>>>>>>>>"+p);
//      if(g=='M')
//      
//      {   alert("INSIDE GG>>>>"+g);
//   // document.forms[0].gender.value="1";
//      document.forms[0].gender(1).checked=true;
//      }
    	  
   } 
   else
   {
      // alert("Failed to update values");
   }                  
}
