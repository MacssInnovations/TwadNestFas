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

function callServer(id)
{
    var url="";
    //var major=document.frmHelp.cmbMajor.value;
    //startwaiting(document.frmHelp);
    //alert(id);
    url="../../../ServletHelp.con?MajorId="+id;
    var req=getTransport();
    req.open("Get",url,false);
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
            //startwaiting(document.frmHelp);
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


function displaydetails()
{


    var url="";
    var issueid=document.frmHelp.cmbissue_id.value;
    //startwaiting(document.frmHelp);
    
    url="../../../ServletDisplayIssue.con?IssueId="+issueid;
    
    var req=getTransport();
    req.open("Get",url,true);
    req.onreadystatechange=function()
    {
    processresponse1(req);
    
    }
    req.send(null);


}

function processresponse1(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            //alert(req.responseTEXT);
            //startwaiting(document.frmHelp);
            var cmbMinor=document.getElementById("cmbMinor");
            
            var response=req.responseXML.getElementsByTagName("response")[0];
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
           
            
                var issuepriority=response.getElementsByTagName("issuepriority")[0].firstChild.nodeValue;
                var major=response.getElementsByTagName("majorsystem")[0].firstChild.nodeValue;
                var majordesc=response.getElementsByTagName("majorsystemdesc")[0].firstChild.nodeValue;
                var minor=response.getElementsByTagName("minorsystemid")[0].firstChild.nodeValue;
                var minordesc=response.getElementsByTagName("minorsystem")[0].firstChild.nodeValue;
                var issuetitle=response.getElementsByTagName("issuetitle")[0].firstChild.nodeValue;
                var issuedesc=response.getElementsByTagName("issuedesc")[0].firstChild.nodeValue;
                var issuestatus=response.getElementsByTagName("issuestatus")[0].firstChild.nodeValue;
                var userid=response.getElementsByTagName("userid")[0].firstChild.nodeValue;
                var reportdate=response.getElementsByTagName("reportdate")[0].firstChild.nodeValue;
                
                var empname=response.getElementsByTagName("empname")[0].firstChild.nodeValue;
                var officename=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
                
                //document.frmHelp.txtUserName.value=userid;
                document.frmHelp.txtUserName.value=empname;
                document.frmHelp.txtHUserName.value=userid;
                
                document.frmHelp.txtofficename.value=officename;
                //document.frmHelp.txtMajor.value=majordesc;
                document.frmHelp.cmbMajor.value=major;
                //document.frmHelp.txtMinor.value=minordesc;
                
                //document.frmHelp.cmbMinor.value=minor;
               // document.frmHelp.cmbPriority.value=issuepriority;
                document.frmHelp.cmbPriority.value=issuepriority;
                document.frmHelp.txtSubject.value=issuetitle;
                document.frmHelp.txtdesc.value=issuedesc;
                /*if(issuestatus=="O")
                {
                document.frmHelp.status.value="Open";
                }
                else
                {
                document.frmHelp.status.value="Closed";
                }
                if(reportdate=="Not Specified")
                {
                    document.frmHelp.txtdate.value="";
                }
                else
                {
                    document.frmHelp.txtdate.value=reportdate;
                }*/
                callServer(major);
                /*cmbMinor.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Minor System--";
                try
                {
                    cmbMinor.add(option);
                }catch(errorobject)
                {
                    cmbMinor.add(option,null);
                }
                var minor=response.getElementsByTagName("minorsystem");
                for(var i=0;i<minor.length;i++)
                {
                    
                    var minorid=response.getElementsByTagName("minorsystemid")[0].firstChild.nodeValue;
                    var minordesc=response.getElementsByTagName("minorsystem")[0].firstChild.nodeValue;
                    
                    //var option=document.createElement("OPTION");
                    var combovalue=minorid;
                    var combotext=minordesc;
                    option.text=combotext;
                    
                      try
                        {
                            cmbMinor.add(option);
                    }catch(errorobject)
                    { 
                             cmbMinor.add(option,null);
                    }
                }*/
                //alert(minor);
                document.frmHelp.cmbMinor.value=minor;
            }
            else
            {
                document.frmHelp.txtSubject.value="";
                document.frmHelp.txtdesc.value="";
                //document.frmHelp.status.value="";
                document.frmHelp.txtHUserName.value="";
                document.frmHelp.txtUserName.value="";
                document.frmHelp.cmbMajor.selectedIndex=0;
                document.frmHelp.cmbMinor.selectedIndex=0;
                document.frmHelp.cmbPriority.selectedIndex=0;
                document.frmHelp.txtofficename.value="";
                
                
            }
        }
        
    }
    

}


function minor()
{
    var url="";
    var major=document.frmHelp.cmbMajor.value;
    //startwaiting(document.frmHelp);
    //alert(id);
    url="../../../ServletHelp.con?MajorId="+major;
    var req=getTransport();
    req.open("Get",url,true);
    req.onreadystatechange=function()
    {
    processresponse2(req);
    
    }
    req.send(null);
    
}

function processresponse2(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {
            //alert(req.responseTEXT);
            //startwaiting(document.frmHelp);
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
    if((document.frmHelp.cmbissue_id.value=="0") || (document.frmHelp.cmbissue_id.selectedIndex<=0))
    {
         alert("Please Select IssueId");
         document.frmHelp.cmbissue_id.focus();
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

function funclear()
{
    var cmbMinor=document.getElementById("cmbMinor");
    cmbMinor.innerHTML="";
    var option=document.createElement("OPTION");
    option.text="--Select Minor System--";
    try
              {
                  cmbMinor.add(option);
              }
              catch(errorObject)
              {
                  cmbMinor.add(option,null);
              }
    document.frmHelp.cmbMinor.selectedIndex=0;

}

function issue()
{
    //alert('hai'+document.frmHelp.cmbissue_id.value);
    if((document.frmHelp.cmbissue_id.value=="")||(document.frmHelp.cmbissue_id.value.length<=0)||(document.frmHelp.cmbissue_id.value=="0"))
    {
        alert("Please Select IssueId");
        document.frmHelp.cmbissue_id.focus();
        return false;
    }
   // return true;

}