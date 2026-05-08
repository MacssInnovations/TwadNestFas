function nullCheck()
{
    //alert("called news");
    if((frmCreateNews.txtEventDesc.value=="") || (frmCreateNews.txtEventDesc.value.length<=0))
    {
         alert("Please Enter Event Description");
         frmCreateNews.txtEventDesc.focus();
         return false;
    } 
   /*  if((frmCreateNews.txtEventVenue.value=="") || (frmCreateNews.txtEventVenue.value.length<=0))
    {
         alert("Please Enter Event Venue");
         frmCreateNews.txtEventVenue.focus();
         return false;
    } 
    
     if((frmCreateNews.txtEventDate.value=="" ) || (frmCreateNews.txtEventDate.value.length<=0))
    {
         alert("Please Enter Date of Event");
         frmCreateNews.txtEventDate.focus();
         return false;
    }   
    */
    if((frmCreateNews.txtTargetURL.value=="" ) || (frmCreateNews.txtTargetURL.value.length<=0))
    {
         alert("Please Enter Target URL");
         frmCreateNews.txtTargetURL.focus();
         return false;
    }   
    
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
    }    
    
    return true;
}

/*
function chk_for_expired_date(t)
{
  //alert("called"+t);
  var entDate=document.frmCreateNews.txtEventDate.value;
  var now()=document.frmCreateNews.txtEventDate_h.value;
   check(entDate,now());
  
}
function check(c,todate)
{
	
   
     var fday=c.split("/");
     var todate=todate;
     //alert("todate"+todate);
     var frmDay = fday[0];
     var frmMonth = fday[1];
     var frmYear =fday[2];
   
     var frmday=new Date(frmYear,frmMonth-1,frmDay);
     tday=todate.split("/");
     var toDay = tday[0];
     var toMonth= tday[1];
     var toYear = tday[2];
  
	 var today=new Date(toYear,toMonth-1,toDay);
       //alert("frmday"+frmday);
       //alert("today"+today);
	
       if (frmday<today)
	     {
		   alert ("Date of Event Should Not Be Lesser Than Current Date");
                   document.frmCreateNews.txtEventDate.value="";
                  
		
             }
}
*/