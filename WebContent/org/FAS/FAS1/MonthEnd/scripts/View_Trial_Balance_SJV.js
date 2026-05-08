
function nullcheckNew()
{
 
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct cashbook year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
   
 return true;
}
//added sathya on 16Jul2012
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
function checkstatus()
{
	//alert("coming hereeeee");
	var month_chosen=document.getElementById("txtCB_Month").value;
	var year_chosen=document.getElementById("txtCB_Year").value;
	var acc_unitid=document.getElementById("cmbAcc_UnitCode").value;
	var url="../../../../../unfreezeSuspRaised.htm?command=check_TB&TB_Month="+month_chosen+"&TB_Year="+year_chosen+"&Accunitid="+acc_unitid;
   // alert(url);
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function()
		{
		check_TBres(req);
		}   
		req.send(null); 
	
	//return false;
}
function checkstatusSJV()
{
	//alert("coming hereeeee");
	var month_chosen=document.getElementById("txtCB_Month").value;
	var year_chosen=document.getElementById("txtCB_Year").value;
	var acc_unitid=document.getElementById("cmbAcc_UnitCode").value;
	var sup_no=document.getElementById("txtsupplement_no").value;
	var url="../../../../../unfreezeSuspRaised.htm?command=check_TBSJV&TB_Month="+month_chosen+"&TB_Year="+year_chosen+"&Accunitid="+acc_unitid+"&sup_No="+sup_no;
    //alert(url);
		var req=getTransport();
		req.open("POST",url,true); 
		req.onreadystatechange=function()
		{
		check_TBresSJV(req);
		}   
		req.send(null); 
	
	//return false;
}
function check_TBres(req)
{
	if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command == "loadstat")
            	{
		            var flag=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
		            
		            if(flag=="success")
		              {
		                   alert("Raise the Request for subsequent month");  
		                   //return true;
		              }
		             else if(flag=="nodata")
		               {
		                    //return false;
		               }
            	}
        }
    }	
}
function check_TBresSJV(req)
{
	if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command == "loadstatSJV")
            	{
		            var flag=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
		            
		            if(flag=="success")
		              {
		                   alert("Raise the Request for subsequent supplement Number first");  
		                   return true;
		              }
		             else if(flag=="nodata")
		               {
		                    return false;
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