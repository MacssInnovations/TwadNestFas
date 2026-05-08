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

function testlen()
{
	//alert("llll");
	 var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
	 if(from_txtCB_Year=="")
		 {
		 alert("Enter CashBookYear");
		 return false;
		 }
	 else if(document.getElementById("from_txtCB_Year").value.length<4)
		 {
		 document.getElementById("from_txtCB_Year").value="";
		 return false;
		 }
	 loadTransferUnit();
}

function loadTransferUnit()
{       
		 var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
         url="../../../../../TDA_Raised_Create?command=forTBstatus&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                TDA_Raised_ServletRes(req);
         }   
         req.send(null);  
        
}



function TDA_Raised_ServletRes(req)
{
		 
                 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                      //  alert(baseResponse);
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(Command=="forTBstatus")
                        {                                       
                             
                               if(flag=="success")
                               {  
                            	   
                            	   
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }   
                                       var option=document.createElement("OPTION");
                                       option.text="All";
                                       option.value="All";
                                       txtUnitId.appendChild(option);
                                       var items_id=new Array();
                                       var items_name=new Array();                                    
                                       var oid=baseResponse.getElementsByTagName("unit_id");
                                       for(var k=0;k<oid.length;k++)
                                       {
                                                items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                                items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;				       	                                                  
                                               //document.getElementById("myDiv").innerHTML=txt;
                                                var option=document.createElement("OPTION");
                                                option.text=items_name[k]+"("+items_id[k]+")";
                                                option.value=items_id[k];
                                                try
                                                {
                                                        txtUnitId.add(option);
                                                }
                                                catch(errorObject)
                                                {
                                                        txtUnitId.add(option,null);
                                                }
                                       }
                               }
                               else
                               {                                                   
                                       document.getElementById("txtUnitId").value="";
                               }
                       }
              }
		 }    
}
function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }