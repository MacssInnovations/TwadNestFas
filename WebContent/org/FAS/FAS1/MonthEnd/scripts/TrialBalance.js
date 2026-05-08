function checknull()
{
 
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    /*if((document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="") || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value.length<=0) || (document.frmGeneralLedgerSystem.cmbAccHeadCode.value=="0"))
    {
        alert("Please Select Account Head Code");
        document.frmGeneralLedgerSystem.cmbAccHeadCode.focus();
        return false;
    }*/
 return true;
}





function checkTBOperations()
{
	//alert("test");
	 var unitcode=document.getElementById("cmbAcc_UnitCode").value;
	 var txtCB_Year=document.getElementById("txtCB_Year").value;
	 var txtCB_Month=document.getElementById("txtCB_Month").value;
     var url="../../../../../tb_check_onload?Command=tbcheck&unitcode="+unitcode+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
     //alert(url);
     var req=getTransport();
     req.open("GET",url,true); 
     req.onreadystatechange=function()
     {
        
        handleResponse(req);
     }   
        req.send(null);
}

function handleResponse(req)
{  
     
    if(req.readyState==4)
    { 
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="tbcheck")
            {
            	var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            	 if(flag=="success")
                 {
            		 var result=baseResponse.getElementsByTagName("result")[0].firstChild.nodeValue;
            		 if(result=="resultEqual")
            		 {
            		 }
            		 else
            		 {
            			 alert("Fund Trf(Collection) Amount is not Equal to the Total Collection Amount");
            			 return false;
            		 }
                 }
            	 else
            	 {
            		 
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