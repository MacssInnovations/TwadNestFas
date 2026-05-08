function nullCheck()
{
   // alert("called@");
    if((document.frmEditEvent.txtEventId.value=="") || (document.frmEditEvent.txtEventId.value.length<=0))
    {
         alert("Please Enter Event Id");
         document.frmEditEvent.txtEventId.focus();
         return false;
    } 
    if((document.frmEditEvent.txtEventDesc.value=="") || (document.frmEditEvent.txtEventDesc.value.length<=0))
    {
         alert("Please Enter Event Description");
         document.frmEditEvent.txtEventDesc.focus();
         return false;
    } 
     if((document.frmEditEvent.txtEventVenue.value=="") || (document.frmEditEvent.txtEventVenue.value.length<=0))
    {
         alert("Please Enter Event Venue");
         document.frmEditEvent.txtEventVenue.focus();
         return false;
    } 
    
     if((document.frmEditEvent.txtEventDate.value=="" ) || (document.frmEditEvent.txtEventDate.value.length<=0))
    {
         alert("Please Enter Date of Event");
         document.frmEditEvent.txtEventDate.focus();
         return false;
    }  
     if((document.frmEditEvent.txtTargetURL.value=="" ) || (document.frmEditEvent.txtTargetURL.value.length<=0))
    {
         alert("Please Enter Target URL");
         document.frmEditEvent.txtTargetURL.focus();
         return false;
    }   
    
     if((document.frmEditEvent.txtTargetURL.value!="" ) || (document.frmEditEvent.txtTargetURL.value.length>0))
    {
            var strURL=document.frmEditEvent.txtTargetURL.value.substring(3,0); 
            var strURL1=document.frmEditEvent.txtTargetURL.value.substring(5,0);
           // alert(strURL+"  "+strURL1);
            if((strURL!="www") &&(strURL1!="http:"))
            {
            alert("Enter Target URL in one of the Given Formats:\n\n \t(i) www.tn.nic.in\n\n \t(ii) http://www.tn.nic.in");
            document.frmEditEvent.txtTargetURL.value="";
            document.frmEditEvent.txtTargetURL.focus();
            return false;
            }
          
        return true;
    }    
    if(!chk_for_expired_date1(document.frmEditEvent.txtEventDate.value))
    {
    return false;
    }
    
    return true;
}

function chk_for_expired_date(t)
{
  //alert("called"+t);
  var entDate=document.frmEditEvent.txtEventDate.value;
  var now()=document.frmEditEvent.txtEventDate_h.value;
   //alert(entDate+"   "+now());
   check(entDate,now());
  
}
function check(c,todate)
{
	
   //alert("called"+c+" "+todate);
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
                   document.frmEditEvent.txtEventDate.value="";
                   document.frmEditEvent.txtEventDate.focus();
		
             }
             else
             {
             //alert("date in else");
             }
}

function chk_for_expired_date1(t)
{
  //alert("called"+t);
  var entDate=document.frmEditEvent.txtEventDate.value;
  var now()=document.frmEditEvent.txtEventDate_h.value;
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
                   document.frmEditEvent.txtEventDate.value="";
                   document.frmEditEvent.txtEventDate.focus();
		return false;
             }
             return true;
}