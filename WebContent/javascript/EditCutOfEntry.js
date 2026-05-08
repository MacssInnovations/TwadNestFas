function getReq()
   {
	var req=false;
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




function editData(edtppono){
	alert("INSIDE EDIT FUNCTION");
	 alert("--------->"+edtppono);
	var url;
	url = 'editCutOfEntryPension.html';
	alert("url="+url);
	var HttpRequest1 = getReq();
     HttpRequest1.open("GET", url, true);
     
     req.onreadystatechange=function()
     {
        processResponse(HttpRequest1);
     }   
     HttpRequest1.send(null);
   }
	

