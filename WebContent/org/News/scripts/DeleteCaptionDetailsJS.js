function nullCheck()
{
    
    if((frmCreateNews.txtCaption.value=="") || (frmCreateNews.txtCaption.value.length<=0))
    {
         alert("Please Enter Caption");
         frmCreateNews.txtCaption.focus();
         return false;
    } 
    
    if((frmCreateNews.txtDesc.value=="") || (frmCreateNews.txtDesc.value.length<=0))
    {
         alert("Please Enter Brief Description");
         frmCreateNews.txtDesc.focus();
         return false;
    }   
    
    return true;
}

function getTransport()
{
  var req=false;
  
  try
  {
     req=new ActiveXObject("Msxml2.XMLHTTP");
  }
  catch(e)
  {
    try
    {
      req=new ActiveXObject("Microsoft.XMLHTTP");    
    }
    catch(e2)
    {
       req=false;
    }
  
  }
  
  if(!req && typeof XMLHttpRequest != 'undefined')
  {
     req=new XMLHttpRequest();
  }
  return req;

}

function callServer(command,param)
{
   var cid=document.frmCreateNews.txtEventId.value;
   
   if(command=='Existg')
   {
     var url="../../../ServletDeleteCaptionDetails.con?command=Existg&caption="+cid;
     
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
     //alert('inside success');
      var baseResponse=req.responseXML.getElementsByTagName("response")[0];
      //var baseResponse=req.responseXML.getElementsByTagName("response")[0];
      //alert(baseResponse);      
      var tagCommand=baseResponse.getElementsByTagName("command")[0];
      var command=tagCommand.firstChild.nodeValue;
      
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
  
  if(flag=='success')
  {
    var capt=baseResponse.getElementsByTagName("caption")[0].firstChild.nodeValue;
    var desc=baseResponse.getElementsByTagName("brief_desc")[0].firstChild.nodeValue;
    
    document.frmCreateNews.txtCaption.value=capt;
    document.frmCreateNews.txtDesc.value=desc;
  }
  else if(flag=='failure1')
  {
    alert('Given Caption Id doesnt exist');
    document.frmCreateNews.txtEventId.value="";
    document.frmCreateNews.txtEventId.focus();
    document.frmCreateNews.txtCaption.value="";
    document.frmCreateNews.txtDesc.value="";
  
  }
  else if(flag=='failure2')
  {
    alert('The News has been Deleted');
    document.frmCreateNews.txtEventId.value="";
    document.frmCreateNews.txtEventId.focus();
    document.frmCreateNews.txtCaption.value="";
    document.frmCreateNews.txtDesc.value="";
    
  
  }
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

function noEnter(e)
{
   
   isIE=document.all? 1:0
       
   keyEntry = !isIE? e.which:event.keyCode;
                  
   if(keyEntry=='38')
   {
     return false;
   }
}

