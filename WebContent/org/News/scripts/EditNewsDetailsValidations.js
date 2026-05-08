function nullCheck()
{
   // alert("called@");
    if((frmEditNews.txtEventId.value=="") || (frmEditNews.txtEventId.value.length<=0))
    {
         alert("Please Enter News Id");
         frmEditNews.txtEventId.focus();
         return false;
    } 
    if((frmEditNews.txtEventDesc.value=="") || (frmEditNews.txtEventDesc.value.length<=0))
    {
         alert("Please Enter News Description");
         frmEditNews.txtEventDesc.focus();
         return false;
    } 
   /*  if((frmEditNews.txtEventVenue.value=="") || (frmEditNews.txtEventVenue.value.length<=0))
    {
         alert("Please Enter Event Venue");
         frmEditNews.txtEventVenue.focus();
         return false;
    } 
  
     if((frmEditNews.txtEventDate.value=="" ) || (frmEditNews.txtEventDate.value.length<=0))
    {
         alert("Please Enter Date of News");
         frmEditNews.txtEventDate.focus();
         return false;
    }  */   
    if((frmEditNews.txtTargetURL.value=="" ) || (frmEditNews.txtTargetURL.value.length<=0))
    {
         alert("Please Enter Target URL");
         frmEditNews.txtTargetURL.focus();
         return false;
    }   
    
     if((document.frmEditNews.txtTargetURL.value!="" ) || (document.frmEditNews.txtTargetURL.value.length>0))
    {
            var strURL=document.frmEditNews.txtTargetURL.value.substring(3,0); 
            var strURL1=document.frmEditNews.txtTargetURL.value.substring(5,0);
           // alert(strURL+"  "+strURL1);
            if((strURL!="www") &&(strURL1!="http:"))
            {
            alert("Enter Target URL in one of the Given Formats:\n\n \t(i) www.tn.nic.in\n\n \t(ii) http://www.tn.nic.in");
            document.frmEditNews.txtTargetURL.value="";
            document.frmEditNews.txtTargetURL.focus();
            return false;
            }
          
        return true;
    }    
    
    return true;
}