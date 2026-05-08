// code for creating XMLHTTPREQUEST object
//alert('detail');
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
           // alert(req.responseTEXT);
            //startwaiting(document.frmHelp);
            var cmbMinor=document.getElementById("cmbMinor");
            var response=req.responseXML.getElementsByTagName("response")[0];
            var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="success")
            {
            
            
                var issuepriority=response.getElementsByTagName("issuepriority")[0].firstChild.nodeValue;
                //var major=response.getElementsByTagName("majorsystem")[0].firstChild.nodeValue;
                var majordesc=response.getElementsByTagName("majorsystemdesc")[0].firstChild.nodeValue;
                //var minor=response.getElementsByTagName("minorsystem")[0].firstChild.nodeValue;
                var minordesc=response.getElementsByTagName("minorsystem")[0].firstChild.nodeValue;
                var issuetitle=response.getElementsByTagName("issuetitle")[0].firstChild.nodeValue;
                var issuedesc=response.getElementsByTagName("issuedesc")[0].firstChild.nodeValue;
                var issuestatus=response.getElementsByTagName("issuestatus")[0].firstChild.nodeValue;
              //  var userid=response.getElementsByTagName("userid")[0].firstChild.nodeValue;
                var reportdate=response.getElementsByTagName("reportdate")[0].firstChild.nodeValue;
                //document.frmHelp.txtUserName.value=userid;
               // document.frmHelp.txtHUserName.value=userid;
                
                var empname=response.getElementsByTagName("empname")[0].firstChild.nodeValue;
                //alert('empname');
                var officename=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
                // var officename1=response.getElementsByTagName("officename1")[0].firstChild.nodeValue;
               // var User=response.getElementsByTagName("User")[0].firstChild.nodeValue;
              //  var username=response.getElementsByTagName("username")[0].firstChild.nodeValue;
               
               
                
                document.frmHelp.txtofficename.value=officename;
               // document.frmHelp.txtofficename.value=officename1;
                document.frmHelp.txtMajor.value=majordesc;
                //document.frmHelp.cmbMajor.value=major;
                document.frmHelp.txtMinor.value=minordesc;
                
                //document.frmHelp.cmbMinor.value=minor;
               // document.frmHelp.cmbPriority.value=issuepriority;
                document.frmHelp.txtpriority.value=issuepriority;
                document.frmHelp.txtSubject.value=issuetitle;
                document.frmHelp.txtdesc.value=issuedesc;
               
               
              //  document.frmHelp.txtUserName.value=username;
                             
               
                 document.frmHelp.txtUserName.value=empname;
                 
                if(issuestatus=="O")
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
                }
                //callserver(minor);
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
                }
                document.frmHelp.cmbMinor.value=minorid;*/
            }
            else
            {
                document.frmHelp.txtSubject.value="";
                document.frmHelp.txtdesc.value="";
                document.frmHelp.status.value="";
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





function funclear()
{
              /* var cmbMinor=document.getElementById("cmbMinor");
                cmbMinor.innerHTML="";
                var option=document.createElement("OPTION");
                option.text="--Select Minor System--";
                try
                {
                    cmbMinor.add(option);
                }catch(errorobject)
                {
                    cmbMinor.add(option,null);
                }*/
    
}