/** To handle Errors **/
//alert("list js...");
onerror=handleErr;
var txt="";
function handleErr(msg,url,l)
{
	txt="There was an error on this page.\n\n";
	txt+="Error: " + msg + "\n";
	txt+="URL: " + url + "\n";
	txt+="Line: " + l + "\n\n";
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
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
} 
/*
function checkNull_verify()
{
	  var tbody=document.getElementById("tblList");
	if(tbody.rows.length==0){
	alert("No values Found");
	return false;
	}
}

*/


function exitfun() {
	window.close();
}
 
//******************************************** CallList Coding *******************//
function callList(path){  
          
					
					//var  url="../../../../../List_PPOs?command=GetList";
	                var  url=path+"/List_PPOs?command=GetList";
        			
        			var req=getTransport();
        			
                    req.open("GET",url,true);  
                  //alert(url);
                    req.onreadystatechange=function()
                    {
                    	processResponse(req);
                    };   
                    req.send(null);       
}  


//********************************* CallList Response Coding ***************************************//

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
    	  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
    	       
               if(command=="GetList"){
            	// alert("GetList");
            	  getRow(baseResponse);
            	  
              }
    	  }
     }
}

function  getRow(baseResponse)
{  
var seq=0;
//alert("inside get row from java to js");
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
        	
               for(var k=0;k<len;k++)
                  {
					
            		item[0] =baseResponse.getElementsByTagName("OFFICE_ID")[k].firstChild.nodeValue;
					item[1] =baseResponse.getElementsByTagName("OFFICE_NAME")[k].firstChild.nodeValue;
					
                       var mycurrent_row=document.createElement("TR");
                       mycurrent_row.id=item[0];                     
                    
                       var cell0 = document.createElement("TD");
            			var sno1=document.createElement("input");
            			sno1.type="hidden";
            			sno1.name="sno"+seq;
            			sno1.id="sno"+seq;
            			//sno1.value=k++;
            			var sno2 = document.createTextNode(lll);
            			cell0.appendChild(sno2);
            			cell0.appendChild(sno1);
            			mycurrent_row.appendChild(cell0);

           			var cell2 = document.createElement("TD");
           			/*var OFFICE_ID1=document.createElement("input");
           			OFFICE_ID1.type="text";
           			OFFICE_ID1.name="OFFICE_ID"+seq;
           			OFFICE_ID1.id="OFFICE_ID"+seq;
           			OFFICE_ID1.value=item[0];
           			OFFICE_ID1.size=7;
           			cell2.appendChild(OFFICE_ID1);*/
           			var OFFICE_ID2 = document.createTextNode(item[0]);
           			cell2.appendChild(OFFICE_ID2);
           			mycurrent_row.appendChild(cell2);
           			
           			var cell3 = document.createElement("TD");
           			/*var OPEN_BAL_QTY1=document.createElement("input");
           			OFFICE_NAME1.type="text";
           			OFFICE_NAME1.name="OFFICE_NAME"+seq;
           			OFFICE_NAME1.id="OFFICE_NAME"+seq;
           			OFFICE_NAME1.value=item[1];
           			OFFICE_NAME1.size=7;
           			cell3.appendChild(OFFICE_NAME1);*/
           			var OFFICE_NAME2 = document.createTextNode(item[1]);
           			cell3.appendChild(OFFICE_NAME2);
           			mycurrent_row.appendChild(cell3);
           			
           		
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

