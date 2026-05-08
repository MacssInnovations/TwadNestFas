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


function displaydetails()
{


    var url="";
    var issueid=document.frmHelp.txt_iss_id.value;
    startwaiting(document.frmHelp);
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
            //alert(req.responseTEXT);
            stopwaiting(document.frmHelp);
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
                var userid=response.getElementsByTagName("userid")[0].firstChild.nodeValue;
                var reportdate=response.getElementsByTagName("reportdate")[0].firstChild.nodeValue;
                var solution=response.getElementsByTagName("solution")[0].firstChild.nodeValue;
                //alert(solution);
                document.frmHelp.txtsolution.value="";
                document.frmHelp.txtSubject.value="";
                document.frmHelp.txtdesc.value="";
                document.frmHelp.cmbstatus.selectedIndex=0;
                document.frmHelp.txtHUserName.value="";
                document.frmHelp.txtUserName.value="";
                document.frmHelp.txtMajor.value="";
                document.frmHelp.txtMinor.value="";
                document.frmHelp.txtpriority.value="";
                document.frmHelp.txtofficename.value="";
                //document.frmHelp.txtUserName.value=userid;
                
                
                 var empname=response.getElementsByTagName("empname")[0].firstChild.nodeValue;
                var officename=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
               
                //document.frmHelp.txtUserName.value=userid;
                document.frmHelp.txtUserName.value=empname;
                document.frmHelp.txtofficename.value=officename;
                document.frmHelp.txtHUserName.value=userid;
                
                document.frmHelp.txtHUserName.value=userid;
                document.frmHelp.txtMajor.value=majordesc;
                //document.frmHelp.cmbMajor.value=major;
                document.frmHelp.txtMinor.value=minordesc;
                
                //document.frmHelp.cmbMinor.value=minor;
               // document.frmHelp.cmbPriority.value=issuepriority;
                document.frmHelp.txtpriority.value=issuepriority;
                document.frmHelp.txtSubject.value=issuetitle;
                document.frmHelp.txtdesc.value=issuedesc;
                document.frmHelp.cmbstatus.value=issuestatus;
                if(issuestatus=="O")
                {
                document.frmHelp.cmbstatus.value=issuestatus;
                }
                else if(issuestatus=="C")
                {
                alert("This Issue Id is Closed");
                //document.frmHelp.status.value="Closed";
                document.frmHelp.submit.disabled=true;
                }
                if(reportdate=="Not Specified")
                {
                    document.frmHelp.txtdate.value="";
                }
                else
                {
                    document.frmHelp.txtdate.value=reportdate;
                }
                
                /*if(solution!="null")
                {
                    document.frmHelp.txtsolution.value=solution;
                }*/
                
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
                document.frmHelp.cmbstatus.selectedIndex=0;
                document.frmHelp.txtHUserName.value="";
                document.frmHelp.txtUserName.value="";
                document.frmHelp.txtMajor.value="";
                document.frmHelp.txtMinor.value="";
                document.frmHelp.txtpriority.value="";
                document.frmHelp.txtsolution.value="";
                document.frmHelp.txtofficename.value="";
                document.frmHelp.txt_iss_id.value="";
                
            }
        }
        
    }
    

}


function nullcheck()
{
    if((document.frmHelp.txtsolution.value=="") || (document.frmHelp.txtsolution.value.length<=0))
    {
        alert("Please Enter Solution");
        document.frmHelp.txtsolution.focus();
        return false;
    }
    if((document.frmHelp.cmbstatus.value=="") || (document.frmHelp.cmbstatus.value.length<=0) || (document.frmHelp.cmbstatus.value=="0"))
    {
        alert("Please Select Status");
        document.frmHelp.cmbstatus.focus();
        return false;
    }
    return true;

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
    document.frmHelp.submit.disabled=false;
}

function issue()
{
    //alert('hai'+document.frmHelp.cmbissue_id.value);
    if((document.frmHelp.txt_iss_id.value=="")||(document.frmHelp.txt_iss_id.value.length<=0)||(document.frmHelp.txt_iss_id.value=="0"))
    {
        alert("Please Type the IssueId");
        document.frmHelp.txt_iss_id.focus();
        return false;
    }
   // return true;

}

function check()
{
  var st=document.frmHelp.cmbstatus.value;
  //alert(st);
  if(st="O")
  {
    alert('Please check the Status');
    return false;
  }
  return true;
}


function numbersonly1(e,t)
{
   var unicode=e.charCode? e.charCode : e.keyCode;
     if(unicode==13)
      {
        try
        {
         t.blur();
        }
        catch(e){}
       return true;        
     }
     if ( unicode!=8 && unicode !=9  )
     {
        if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
        return false 
     }
}