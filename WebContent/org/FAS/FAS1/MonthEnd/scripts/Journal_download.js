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
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
         url="../../../../../TDA_Raised_Create?command=forJournalTBstatus&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                TDA_Raised_ServletRes(req);
         }   
         req.send(null);  
        
}
 function loadsupplement()
{
	 document.getElementById('Supplement').style .display = "none";
	 document.getElementById('Supplement1').style .display = "none";

}
function loadjournalUnit()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
		// alert(from_txtCB_Month);
		 if (from_txtCB_Month==3)
			 {
			 
			document.getElementById("select").style .display = "block";
		   document.getElementById("select1").style .display = "block";
		   url="../../../../../TDA_Raised_Create?command=forJournalUnits&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
		   req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 journalUnit_ServletRes(req);
	         }   
	         req.send(null); 
	         
			 }else{
		 document.getElementById("select").style .display = "none";
		 document.getElementById('Supplement').style .display = "none";
		 document.getElementById("select1").style .display = "none";
		 document.getElementById('Supplement1').style .display = "none";
			
         url="../../../../../TDA_Raised_Create?command=forJournalUnits&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
        	 journalUnit_ServletRes(req);
         }   
         req.send(null);  
			 }
        
}

function loadsupplno()
{
	//alert('test');
	document.getElementById('Supplement').style .display = "block";
	 document.getElementById('Supplement1').style .display = "block";
	 var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
	 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
	 
     url="../../../../../TDA_Raised_Create?command=loadsupplno&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
    // alert(url);
     req=getTransport();
     req.open("GET",url,true);        
     req.onreadystatechange=function()
     {        	  
    	 loadsuppl_no(req);
     }   
     req.send(null);  
		 
}

function loadsuppl_no(req)
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
                   if(Command=="loadsupplno")
                   {                                       
                        
                          if(flag=="success")
                          {                                      
                                  var txtSupplno=document.getElementById("txtSupplno");  
                                  var child=txtSupplno.childNodes;
                                  for(var i=child.length-1;i>1;i--)
                                  {
                                	  txtSupplno.removeChild(child[i]);
                                  }                                              
                                  var items_id=new Array();
                                  var items_name=new Array();                                    
                                  var oid=baseResponse.getElementsByTagName("supple_no");
                                  for(var k=0;k<oid.length;k++)
                                  {
                                           items_id[k]=baseResponse.getElementsByTagName("supple_no")[k].firstChild.nodeValue;
                                          // items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;				       	                                                  
                                          //document.getElementById("myDiv").innerHTML=txt;
                                           
                                           var option=document.createElement("OPTION");
                                           option.text=items_id[k];
                                           option.value=items_id[k];
                                           if(items_id[k]==0){
                                        	   option.text='Select Supplement No';
                                               option.value=""; 
                                           }
                                           try
                                           {
                                        	   txtSupplno.add(option);
                                           }
                                           catch(errorObject)
                                           {
                                        	   txtSupplno.add(option,null);
                                           }
                                  }
                          }
                          else
                          {                                                   
                                  document.getElementById("txtSupplno").value="";
                          }
                  }
         }
	 }    
	
	
}
function journalUnit_ServletRes(req)
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
                        if(Command=="forJournalUnits")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
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

function loadPaymentUnit()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
         url="../../../../../TDA_Raised_Create?command=forPaymentUnits&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
        // alert(url);
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
        	 PaymentUnit_ServletRes(req);
         }   
         req.send(null);  
        
}

function PaymentUnit_ServletRes(req)
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
                        if(Command=="forPaymentUnits")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
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
                        if(Command=="forJournalTBstatus")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
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

//siva added on2016-06-04
//**********start


function loadReceiptUnit()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
		// alert(from_txtCB_Month);

		   url="../../../../../TDA_Raised_Create?command=forReceiptUnits&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
		   req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 ReceiptUnit_ServletRes(req);
	         };   
	         req.send(null);      
			
        
}

function ReceiptUnit_ServletRes(req)
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
                        if(Command=="forReceiptUnits")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
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


//**********end


//siva  added on 2016-06-09 at the purpose of  fund_transfer
//*******start
function loadFundTransferUnit()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
		// alert(from_txtCB_Month);

		   url="../../../../../TDA_Raised_Create?command=forFundTransferUnits&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
		   req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 FundTransferUnit_ServletRes(req);
	         };   
	         req.send(null);      
			
        
}

function FundTransferUnit_ServletRes(req)
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
                        if(Command=="forFundTransferUnits")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
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

function forFundTransferBank()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
		// alert(from_txtCB_Month);

		   url="../../../../../TDA_Raised_Create?command=forFundTransferBank&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
		   req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 forFundTransferBank_ServletRes(req);
	         };   
	         req.send(null);      
			
        
}

function forFundTransferBank_ServletRes(req)
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
                        if(Command=="forFundTransferBank")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
                                       var items_id=new Array();
                                       var items_name=new Array();                                    
                                       var oid=baseResponse.getElementsByTagName("unit_id");
                                       for(var k=0;k<oid.length;k++)
                                       {
                                                items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                                items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;
                                               // items_name[k]=baseResponse.getElementsByTagName("Office_Id")[k].firstChild.nodeValue;
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

function forFundReceiptUnit()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
		// alert(from_txtCB_Month);

		   url="../../../../../TDA_Raised_Create?command=forFundReceiptUnit&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
		   req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 forFundReceiptUnit_ServletRes(req);
	         };   
	         req.send(null);      
			
        
}

function forFundReceiptUnit_ServletRes(req)
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
                        if(Command=="forFundReceiptUnit")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
                                       var items_id=new Array();
                                       var items_name=new Array();                                    
                                       var oid=baseResponse.getElementsByTagName("unit_id");
                                       for(var k=0;k<oid.length;k++)
                                       {
                                                items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                                items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;
                                               // items_name[k]=baseResponse.getElementsByTagName("Office_Id")[k].firstChild.nodeValue;
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


function forFundReceiptBank()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
		// alert(from_txtCB_Month);

		   url="../../../../../TDA_Raised_Create?command=forFundReceiptBank&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
		   req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 forFundReceiptBank_ServletRes(req);
	         };   
	         req.send(null);      
			
        
}

function forFundReceiptBank_ServletRes(req)
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
                        if(Command=="forFundReceiptBank")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
                                       var items_id=new Array();
                                       var items_name=new Array();                                    
                                       var oid=baseResponse.getElementsByTagName("unit_id");
                                       for(var k=0;k<oid.length;k++)
                                       {
                                                items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                                items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;
                                               // items_name[k]=baseResponse.getElementsByTagName("Office_Id")[k].firstChild.nodeValue;
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

function loadIBTUnit()
{       
		// alert("coming here in journal download.js");
	     var from_txtCB_Year=document.getElementById("from_txtCB_Year").value;
		 var from_txtCB_Month=document.getElementById("from_txtCB_Month").value;
		// alert(from_txtCB_Month);

		   url="../../../../../TDA_Raised_Create?command=loadIBTUnit&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
		   req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	        	 loadIBTUnit_ServletRes(req);
	         };   
	         req.send(null);      
			
        
}

function loadIBTUnit_ServletRes(req)
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
                        if(Command=="loadIBTUnit")
                        {                                       
                             
                               if(flag=="success")
                               {                                      
                                       var txtUnitId=document.getElementById("txtUnitId");  
                                       var child=txtUnitId.childNodes;
                                       for(var i=child.length-1;i>1;i--)
                                       {
                                    	   		txtUnitId.removeChild(child[i]);
                                       }                                              
                                       var items_id=new Array();
                                       var items_name=new Array();                                    
                                       var oid=baseResponse.getElementsByTagName("unit_id");
                                       for(var k=0;k<oid.length;k++)
                                       {
                                                items_id[k]=baseResponse.getElementsByTagName("unit_id")[k].firstChild.nodeValue;
                                                items_name[k]=baseResponse.getElementsByTagName("unit_name")[k].firstChild.nodeValue;
                                               // items_name[k]=baseResponse.getElementsByTagName("Office_Id")[k].firstChild.nodeValue;
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




//****end 

function numbersonly(e)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
   
    if (unicode!=8 && unicode !=9  )
    {
        if (unicode<48||unicode>57 ) 
            return false 
    }
 }