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
function LoadOffice(unitID_val)
{
   
    if((unitID_val!="") && (unitID_val!=999))
    {
        
        var cmbAcc_UnitCode=unitID_val;
        var url="../../../../../../Receipt_SL.view?Command=LoadUnitWise_Office&cmbAcc_UnitCode="+cmbAcc_UnitCode;
        var req=getTransport();
        req.open("GET",url,true);
        req.onreadystatechange=function()
        {
            handle_loadOffice(req);
        }
        req.send(null);
    }
    else if(unitID_val==999)
    {
         var cmboffice=document.getElementById("cmbOffice_code");
         cmboffice.innerHTML="";
    }
}

function handle_loadOffice(req)
{
    if(req.readyState==4)
    {
     if(req.status==200)
     {
        //alert(req.responseText);
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
       // alert(baseresponse);
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(flag=="success")
        { 
            var cmboffice=document.getElementById("cmbOffice_code");
            cmboffice.innerHTML="";
          
            var offidvalues=baseresponse.getElementsByTagName("offid");
            //alert(offid.length)
            for(i=0;i<offidvalues.length;i++)
            {  
                var option=document.createElement("OPTION");
                var offid=baseresponse.getElementsByTagName("offid")[i].firstChild.nodeValue;
                var offname=baseresponse.getElementsByTagName("offname")[i].firstChild.nodeValue;
                option.text=offname;
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
        else
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
            
             
     }
    }
}
