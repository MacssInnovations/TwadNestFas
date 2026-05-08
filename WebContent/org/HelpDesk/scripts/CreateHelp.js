// code for creating XMLHTTPREQUEST object
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

function minor()
{
    var url="";
    var major=document.frmHelp.cmbMajor.value;
    startwaiting(document.frmHelp);
    url="../../../ServletHelp.con?MajorId="+major;
    var req=getTransport();
    req.open("Get",url,true);
    req.onreadystatechange=function()
    {
    processresponse(req);
    
    }
    req.send(null);
    
}

function processresponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            //alert(req.responseTEXT);
            stopwaiting(document.frmHelp);
            var cmbMinor=document.getElementById("cmbMinor");
            var response=req.responseXML.getElementsByTagName("response")[0];
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                cmbMinor.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Minor System--";
                try
                {
                    cmbMinor.add(option);
                }catch(errorobject)
                {
                    cmbMinor.add(option,null);
                }
                var minor=response.getElementsByTagName("options");
                for(var i=0;i<minor.length;i++)
                {
                    var tmpoption=minor.item(i);
                    var minorid=tmpoption.getElementsByTagName("minorid")[0].firstChild.nodeValue;
                    var minordesc=tmpoption.getElementsByTagName("minordesc")[0].firstChild.nodeValue;
                    var combovalue=minorid;
                    var combotext=minordesc;
                    var option=document.createElement("OPTION");
                    option.text=combotext;
                    option.value=combovalue;
                      try
                        {
                            cmbMinor.add(option);
                    }catch(errorobject)
                    { 
                             cmbMinor.add(option,null);
                    }
                }
            }
        }
        
    }
    

}

function nullcheck()
{

     if((document.frmHelp.cmbMajor.value=="0") || (document.frmHelp.cmbMajor.selectedIndex<=0))
    {
         alert("Please Select Major System");
         document.frmHelp.cmbMajor.focus();
         return false;
    }  
    if((document.frmHelp.cmbMinor.value=="0") || (document.frmHelp.cmbMinor.selectedIndex<=0))
    {
         alert("Please Select Minor System");
         document.frmHelp.cmbMinor.focus();
         return false;
    }
    
    /*if((document.frmHelp.cmbPriority.value=="0") || (document.frmHelp.cmbPriority.selectedIndex<=0))
    {
         alert("Please Select Priority");
         document.frmHelp.cmbPriority.focus();
         return false;
    }*/
    
    if((document.frmHelp.txtSubject.value=="") || (document.frmHelp.txtSubject.value.length<=0))
    {
          alert("Please Enter Subject");
          document.frmHelp.txtSubject.focus();
          return false;
    }
    
    if((document.frmHelp.txtdesc.value=="") || (document.frmHelp.txtdesc.value.length<=0))
    {
          alert("Please Enter Description");
          document.frmHelp.txtdesc.focus();
          return false;
    }
    return true;
}


function issue()
{
    //alert('hai'+document.frmHelp.cmbissue_id.value);
    if((document.frmHelp.cmbMajor.value=="")||(document.frmHelp.cmbMajor.value.length<=0)||(document.frmHelp.cmbMajor.value=="0"))
    {
        alert("Please Select Major System");
        document.frmHelp.cmbMajor.focus();
        return false;
    }
   // return true;

}