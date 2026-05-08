
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



function common_LoadOffice()
{
	//alert("loaddd");
    var unitID_val=document.getElementById("cmbAcc_UnitCode").value;          
    if(unitID_val!="")
    {
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
        	handle_loadOffice_common(req);
        }
        req.send(null);
    }     
}




function handle_loadOffice_common(req)
{
  
    if(req.readyState==4)
    {
     
     if(req.status==200)
     {
     // alert("be44");
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     //  alert(flag);
        if(flag=="success")
        { 
         
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var offidvalues=baseresponse.getElementsByTagName("offid");
       
            for(i=0;i<offidvalues.length;i++)
            {  
            	//alert("comesssss");
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname+"("+offid+")";
                option.value=offid;
                try
                {
                    cmboffice.add(option);
                }
                catch(errorObject )
                {
                    cmboffice.add(option,null);
                }   
            }
            
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }
            
        }
        else
        {
          
         try
         {
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--select office--";
            option.value="";
            try
            {
                cmboffice.add(option);
            }
            catch(errorObject )
            {
                cmboffice.add(option,null);
            }
         }
         catch(err)
         {
            alert("Problem in Loading Office code ");
         }         
            
            
        }
            
             
     }
    }
}
