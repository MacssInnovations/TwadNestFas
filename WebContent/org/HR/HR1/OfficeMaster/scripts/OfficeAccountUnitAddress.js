  // loading office details

function loadOffice(id)
{   
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.frmOffice.txtAttachedOfficeID.focus();
    }
    else
    {
            var url="../../../../../ServletLoadOfficeDetails.con?ID="+id;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               LoadOfficeDetails(req);             
            }
            req.send(null);        
    }
}

function LoadOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0];
                    var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var add2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var add3=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    if(add1!="null")
                    {
                        document.frmOffice.txtAccountOffice_Address1.value=add1;
                    }
                    if(add2!="null")
                    {
                        document.frmOffice.txtAccountOffice_Address2.value=add2;
                    }
                    if(add3!="null")
                    {
                        document.frmOffice.txtAccountOffice_City.value=add3;
                    }
                    document.frmOffice.AccountOffice_Name.value=name.firstChild.nodeValue;
                    
                    /*if(level=="Division" || level=="Sub-Division")
                        document.frmOffice.txtOfficeType.value=type;
                    else
                        document.frmOffice.txtOfficeType.value="Office level : " + level;*/
                        
                    //document.frmOffice.txtOfficeAddress.value=add;
                    //document.frmOffice.txtNewOfficeName.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);                    
                } 
          }
    }       
}