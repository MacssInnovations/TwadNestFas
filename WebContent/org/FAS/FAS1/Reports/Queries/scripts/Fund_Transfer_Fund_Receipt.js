
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

function addParticulars()
{
	var rem=document.getElementById("txtParticular").value;
	if(rem=="")
	{
		alert("Enter Particulars");
		return false;
	}
	else
	{

		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;		 
		 var cmbOffice_code=document.getElementById("cmbOffice_code").value;
		 var txtCB_Year=document.getElementById("txtCB_Year").value;
		 var txtParticular=document.getElementById("txtParticular").value;
		 
		 
        url="../../../../../../Fund_Transfer_Fund_Receipt_servlet?" +
        		"command=loadData&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtCB_Year="+txtCB_Year+"&cmbOffice_code="+cmbOffice_code+"&txtParticular="+txtParticular;
       // alert(url);
        req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {        	  
               res_form(req);
        }   
        req.send(null);  
	}
}

function res_form(req)
{
		 
 if(req.readyState==4)
		 {
                if(req.status==200)
                {  
                        var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                       // alert(baseResponse);
                        var tagcommand=baseResponse.getElementsByTagName("command")[0];
                        var Command=tagcommand.firstChild.nodeValue;                                  
                        var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(Command=="loadData")
                        {                                       
                             
                               if(flag=="success")
                               { 
                            	   alert("Record Inserted Successfully");
                            	 //  document.getElementById("txtParticular").value="";
                               }
                               else
                               {
                            	   alert("Record Not inserted");
                               }
                        }
                }
		 }
}
                               