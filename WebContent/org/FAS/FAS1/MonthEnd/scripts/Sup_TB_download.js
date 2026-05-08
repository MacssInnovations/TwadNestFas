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
         url="../../../../../TDA_Raised_Create?command=forSupTBstatus&from_txtCB_Year="+from_txtCB_Year+"&from_txtCB_Month="+from_txtCB_Month;
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
//////Supplement Check//////////////
function Suppl_Number_CheckNew()
{
    //alert("hereeee Suppl_Number_CheckNew");       
	var cb_month="";
            var cb_year="";
            var acc_unit_d="";
              //alert("Supplement No New loading");
                cb_month = document.getElementById("from_txtCB_Month").value;
           //alert("CB month ***********"+cb_month);
                cb_year = document.getElementById("from_txtCB_Year").value;             
              // alert("CB year ***********"+cb_year);        
                acc_unit_d = document.getElementById("txtUnitId").value;            
           //alert("acc_unit_d ***********"+acc_unit_d);
                var url;
             if(acc_unit_d=="All")
            	 {
            	  url="../../../../../Supplement_Number_Check.kv?Command=check_SJVNumber&cb_month="+cb_month+"&cb_year="+cb_year;
            	 }
             else
            	 {
              url="../../../../../Supplement_Number_Check.kv?Command=check_SJVNumberTB&cb_month="+cb_month+"&cb_year="+cb_year+"&acc_unit_d="+acc_unit_d;
            	 }
                //alert(url);
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
                check_Suppl(req);
             }   
             req.send(null);       
             
}

function check_Suppl(req)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="Success")
              {
                  var TB=baseResponse.getElementsByTagName("Suppl_Status")[0].firstChild.nodeValue;
                  if (TB =="Avail") 
                  {
                       /** Get Supplement Combo Object */
                       var txtsupplement_no = document.getElementById("txtsupplement_no");
                       txtsupplement_no.innerHTML="";
                       
                       
                       var suppl_no=baseResponse.getElementsByTagName("suppl_no");                            
                       for(i=0;i<suppl_no.length;i++)
                       {  
                                var option=document.createElement("OPTION");
                                var txt = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                var val = baseResponse.getElementsByTagName("suppl_no")[i].firstChild.nodeValue;
                                option.text=txt;
                                option.value=val;
                                try
                                {
                                    txtsupplement_no.add(option);
                                }
                                catch(errorObject )
                                {
                                    txtsupplement_no.add(option,null);
                                }  
                       }
                       
                       
                  }  
                  else if ( TB=="NotAvail") 
                  {  
                  
                        alert("Supplement Number Not Available");                   
                       
                        var txtsupplement_no = document.getElementById("txtsupplement_no");
                        txtsupplement_no.innerHTML="";
                           
                        var option=document.createElement("OPTION");
                        option.text="-- Select Suppl No. --";
                        option.value="";
                        try
                        {
                            txtsupplement_no.add(option);
                        }
                        catch(errorObject )
                        {
                            txtsupplement_no.add(option,null);
                        }
                  }  
               
              }
             else if(flag=="Failure")
             {
                 alert("Supplement Number Load Failed");
             }
        }
    }
}