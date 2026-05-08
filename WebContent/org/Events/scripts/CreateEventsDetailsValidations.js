function nullCheck()
{
   // alert("called");
    if((document.frmCreateEvent.txtEventDesc.value=="") || (document.frmCreateEvent.txtEventDesc.value.length<=0))
    {
         alert("Please Enter Event Description");
         document.frmCreateEvent.txtEventDesc.focus();
         return false;
    } 
     if((document.frmCreateEvent.txtEventVenue.value=="") || (document.frmCreateEvent.txtEventVenue.value.length<=0))
    {
         alert("Please Enter Event Venue");
         document.frmCreateEvent.txtEventVenue.focus();
         return false;
    } 
    
     if((document.frmCreateEvent.txtEventDate.value=="" ) || (document.frmCreateEvent.txtEventDate.value.length<=0))
    {
         alert("Please Enter Date of Event");
         document.frmCreateEvent.txtEventDate.focus();
         return false;
    }  
    if((document.frmCreateEvent.txtTargetURL.value=="" ) || (document.frmCreateEvent.txtTargetURL.value.length<=0))
    {
         alert("Please Enter Target URL");
         document.frmCreateEvent.txtTargetURL.focus();
         return false;
    }  
    
      if((document.frmCreateEvent.txtTargetURL.value!="" ) || (document.frmCreateEvent.txtTargetURL.value.length>0))
    {
            var strURL=document.frmCreateEvent.txtTargetURL.value.substring(3,0); 
            var strURL1=document.frmCreateEvent.txtTargetURL.value.substring(5,0);
           // alert(strURL+"  "+strURL1);
            if((strURL!="www") &&(strURL1!="http:"))
            {
            alert("Enter Target URL in one of the Given Formats:\n\n \t(i) www.tn.nic.in\n\n \t(ii) http://www.tn.nic.in");
            document.frmCreateEvent.txtTargetURL.value="";
            document.frmCreateEvent.txtTargetURL.focus();
            return false;
            }
          
        return true;
    } 
    
    if(!chk_for_expired_date1(document.frmCreateEvent.txtEventDate.value))
    {
    return false;
    }
    return true;
}
function chk_for_expired_date(t)
{
  //alert("called"+t);
  var entDate=document.frmCreateEvent.txtEventDate.value;
  var now()=document.frmCreateEvent.txtEventDate_h.value;
   check(entDate,now());
  
}
function check(c,todate)
{
	
   //alert("called");
   // document.workdemand.elements["txt_from"+c].value=""
     //fday=document.workdemand.elements["txt_from"+c].value.split("/");
     /*var tbody=document.getElementById("tblList");
     var rows=tbody.rows;
     var todate=rows[todate].cells[3].firstChild.nodeValue;*/
     //alert("todate"+todate);
     //var todate=document.frmClosure.txtDateOfFormation.value;
     var fday=c.split("/");
     var todate=todate;
     //alert("todate"+todate);
     var frmDay = fday[0];
     var frmMonth = fday[1];
     var frmYear =fday[2];
    /* var frmDay = document.workdemand.elements["txt_from"+c].value.substr(0,2);
     var frmMonth = document.workdemand.elements["txt_from"+c].value.substr(3,2);
     var frmYear = document.workdemand.elements["txt_from"+c].value.substr(6,4)*/
     var frmday=new Date(frmYear,frmMonth-1,frmDay);
     tday=todate.split("/");
     var toDay = tday[0];
     var toMonth= tday[1];
     var toYear = tday[2];
  	/* var toDay = todate.value.substr(0,2);
     var toMonth = todate.value.substr(3,2);
     var toYear = todate.value.substr(6,4)*/
	 var today=new Date(toYear,toMonth-1,toDay);
       //alert("frmday"+frmday);
       //alert("today"+today);
	
       if (frmday<today)
	     {
		   alert ("Date of Event Should Not Be Lesser Than Current Date");
                   document.frmCreateEvent.txtEventDate.value="";
                   document.frmCreateEvent.txtEventDate.focus();
		
             }
}



function chk_for_expired_date1(t)
{
  //alert("called"+t);
  var entDate=document.frmCreateEvent.txtEventDate.value;
  var now()=document.frmCreateEvent.txtEventDate_h.value;
   if(check1(entDate,now()))
   {
   return true;
   //alert("returned");
   }
   else
   {
    return false; 
    }
}
function check1(c,todate)
{
	
   //alert("called2");
   // document.workdemand.elements["txt_from"+c].value=""
     //fday=document.workdemand.elements["txt_from"+c].value.split("/");
     /*var tbody=document.getElementById("tblList");
     var rows=tbody.rows;
     var todate=rows[todate].cells[3].firstChild.nodeValue;*/
     //alert("todate"+todate);
     //var todate=document.frmClosure.txtDateOfFormation.value;
     var fday=c.split("/");
     var todate=todate;
     //alert("todate"+todate);
     var frmDay = fday[0];
     var frmMonth = fday[1];
     var frmYear =fday[2];
    /* var frmDay = document.workdemand.elements["txt_from"+c].value.substr(0,2);
     var frmMonth = document.workdemand.elements["txt_from"+c].value.substr(3,2);
     var frmYear = document.workdemand.elements["txt_from"+c].value.substr(6,4)*/
     var frmday=new Date(frmYear,frmMonth-1,frmDay);
     tday=todate.split("/");
     var toDay = tday[0];
     var toMonth= tday[1];
     var toYear = tday[2];
  	/* var toDay = todate.value.substr(0,2);
     var toMonth = todate.value.substr(3,2);
     var toYear = todate.value.substr(6,4)*/
	 var today=new Date(toYear,toMonth-1,toDay);
       //alert("frmday"+frmday);
       //alert("today"+today);
	
       if (frmday<today)
	     {
		   alert ("Date of Event Should Not Be Lesser Than Current Date");
                   document.frmCreateEvent.txtEventDate.value="";
                   document.frmCreateEvent.txtEventDate.focus();
		return false;
             }
             return true;
}