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
   
    
    
    /*
     if((document.frmCreateNews.txtTargetURL.value!="" ) || (document.frmCreateNews.txtTargetURL.value.length>0))
    {
            var strURL=document.frmCreateNews.txtTargetURL.value.substring(3,0); 
            var strURL1=document.frmCreateNews.txtTargetURL.value.substring(5,0);
           // alert(strURL+"  "+strURL1);
            if((strURL!="www") &&(strURL1!="http:"))
            {
            alert("Enter Target URL in one of the Given Formats:\n\n \t(i) www.tn.nic.in\n\n \t(ii) http://www.tn.nic.in");
            document.frmCreateNews.txtTargetURL.value="";
            document.frmCreateNews.txtTargetURL.focus();
            return false;
            }
          
        return true;
    }    */
    
    return true;
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

