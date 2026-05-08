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
	
	if(command=='loadCombo1')
	{
	
	var	url="../loadGradeCombo.html";
    var req=getTransport();
    alert(url)
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
          
          
          if(command=="Get")
          { 
        	  alert(command)
        	  getRow(baseResponse);
          }
          
      }
  }
}





		
		
		
		function getRow(baseResponse)
		{   
		      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
		      alert(flag)
		      if(flag=="success")
		      {     
		    				                    
					var len=baseResponse.getElementsByTagName("record").length;
					alert(len)
					for(var k=0;k<len;k++)
		            {
		             alert('loop')
		             var Desc = baseResponse.getElementsByTagName("combo1")[k].firstChild.nodeValue;
		            
		         	 alert(Desc);         
		           
		            
		            }
		      }
		      else
		      {
		        alert("Failed to Load Values");
		      }
		}

	



