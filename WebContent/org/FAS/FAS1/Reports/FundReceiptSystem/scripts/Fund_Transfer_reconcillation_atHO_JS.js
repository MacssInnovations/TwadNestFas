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


function test_one()
{  
           var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
      if(document.frmGeneralLedgerReport.displayingOrder[1].checked==true)
            {
            var url="../../../../../../Fund_Transfer_Reconciliation_atHo?command=loadBankWise&cmbAcc_UnitCode="+cmbAcc_UnitCode;
            
         //   alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
               }     
}
function handleResponse(req)
{ 
	    if(req.readyState==4)
	    {
			       if(req.status==200)
			       {  
				            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                                           // alert("baseResponse::::"+baseResponse);
				            var tagcommand=baseResponse.getElementsByTagName("command")[0];
				            var Command=tagcommand.firstChild.nodeValue;
				             
				            if(Command=="loadBankWise")
				            {
				                loadBankWise_data(baseResponse);
				            }
				            
			       }
	    }
}

function loadBankWise_data(baseResponse)
{
        var bAccno =document.getElementById("txtBankAccountNo");
        bAccno.length=0;
        var bankno=baseResponse.getElementsByTagName("bankno");
        var bankShow = baseResponse.getElementsByTagName("bankShow"); 
             for(var i=0; i<bankno.length; i++)
                 {
                     var opt = document.createElement('option');
                     opt.value = bankno[i].firstChild.nodeValue;
                     opt.innerHTML = bankShow[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                     bAccno.appendChild(opt);
                 }
        
}