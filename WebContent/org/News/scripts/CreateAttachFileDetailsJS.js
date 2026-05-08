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


function nullCheck()
{
    
    if((frmCreateNews.txtEventId.value=="") || (frmCreateNews.txtEventId.value.length<=0))
    {
         alert("Please Enter Caption Id");
         frmCreateNews.txtEventId.focus();
         return false;
    } 
    
    if((frmCreateNews.txtattachFile.value=="") || (frmCreateNews.txtattachFile.value.length<=0))
    {
         alert("Please Attach the File");
         frmCreateNews.txtattachFile.focus();
         return false;
    }   

    
    return true;
}

function numbersonly1(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
}

function callServer(command,param)
{
      if(command=="Existg")
       {
       
         var strCapId=document.frmCreateNews.txtEventId.value;
       
       
       //startwaiting(document.frmEmployeeProfile) ; 
          url="../../../UploadAttachFileServ.con?Command=Existg&CapId=" + strCapId;
          //alert(url);
          
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               processResponse(req);
            }   
            req.send(null);
        
       }
       
        
}

function processResponse(req)
{   
      if(req.readyState==4)
        {
          if(req.status==200)
          {       
          //stopwaiting(document.frmEmployeeProfile) ;
             //alert('inside success');
             
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             //alert(baseResponse);
             var tagCommand=baseResponse.getElementsByTagName("command")[0];
             var command=tagCommand.firstChild.nodeValue;
             //alert('command...'+command);
              
             if(command=="Existg")
             {
                  existRow(baseResponse);                 
             } 
              
          }
        }
}
  
 
function existRow(baseResponse)
{
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
              if(flag=="Success")
              { 
                 //alert("success");
                document.frmCreateNews.txtCaption.value=baseResponse.getElementsByTagName("caption")[0].firstChild.nodeValue; 
                document.frmCreateNews.txtatt_slno.value=baseResponse.getElementsByTagName("attach_slno")[0].firstChild.nodeValue;  
              }
              
              else if(flag=="Failure")
              {
                alert("Given Caption Id doesnt exist");
                document.frmCreateNews.txtEventId.focus();
                document.frmCreateNews.txtEventId.value='';
                document.frmCreateNews.txtCaption.value='';
                document.frmCreateNews.txtatt_slno.value='';
                document.frmCreateNews.txtattachFile.value='';
              }
              else if(flag=="Failure1")
              {
                alert("Given Caption Id has already Published");
                document.frmCreateNews.txtEventId.focus();
                document.frmCreateNews.txtEventId.value='';
                document.frmCreateNews.txtCaption.value='';
                document.frmCreateNews.txtatt_slno.value='';
                document.frmCreateNews.txtattachFile.value='';
              }
              
}


function loadFileUpload()
{
var txtattachFile=document.frmCreateNews.txtattachFile.value;
var txtEventId=document.frmCreateNews.txtEventId.value;
var txtCaption=document.frmCreateNews.txtCaption.value;
var txtatt_slno=document.frmCreateNews.txtatt_slno.value;
//alert(txtattachFile);

//document.frmCreateNews.action="../jsps/PDFLoad_db2.jsp?txtattachFile="+txtattachFile+"&txtEventId="+txtEventId+"&txtCaption="+txtCaption+"&txtatt_slno="+txtatt_slno;
document.frmCreateNews.action="../../../UploadAttachFileServ.con?txtattachFile="+txtattachFile+"&txtEventId="+txtEventId+"&txtatt_slno="+txtatt_slno+"&txtCaption="+txtCaption;
document.frmCreateNews.method="POST";
document.frmCreateNews.enctype="multipart/form-data";
document.frmCreateNews.submit();
}


