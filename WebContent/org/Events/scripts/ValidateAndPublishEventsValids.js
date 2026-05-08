function nullCheck()
{
   // alert("called@");
    if((document.frmPublishEvent.txtEventId.value=="") || (document.frmPublishEvent.txtEventId.value.length<=0))
    {
         alert("Please Enter Event Id");
         document.frmPublishEvent.txtEventId.focus();
         return false;
    } 
    if((document.frmPublishEvent.txtEventDesc.value=="") || (document.frmPublishEvent.txtEventDesc.value.length<=0))
    {
         alert("Please Enter Event Description");
         document.frmPublishEvent.txtEventDesc.focus();
         return false;
    } 
     if((document.frmPublishEvent.txtEventVenue.value=="") || (document.frmPublishEvent.txtEventVenue.value.length<=0))
    {
         alert("Please Enter Event Venue");
         document.frmPublishEvent.txtEventVenue.focus();
         return false;
    } 
    
     if((document.frmPublishEvent.txtEventDate.value=="" ) || (document.frmPublishEvent.txtEventDate.value.length<=0))
    {
         alert("Please Enter Date of Event");
         document.frmPublishEvent.txtEventDate.focus();
         return false;
    } 
    
     if((document.frmPublishEvent.txtTargetURL.value=="" ) || (document.frmPublishEvent.txtTargetURL.value.length<=0))
    {
         alert("Please Enter Target URL");
         document.frmPublishEvent.txtTargetURL.focus();
         return false;
    }   
    
     if((document.frmPublishEvent.txtTargetURL.value!="" ) || (document.frmPublishEvent.txtTargetURL.value.length>0))
    {
            var strURL=document.frmPublishEvent.txtTargetURL.value.substring(3,0); 
            var strURL1=document.frmPublishEvent.txtTargetURL.value.substring(5,0);
           // alert(strURL+"  "+strURL1);
            if((strURL!="www") &&(strURL1!="http:"))
            {
            alert("Enter Target URL in one of the Given Formats:\n\n \t(i) www.tn.nic.in\n\n \t(ii) http://www.tn.nic.in");
            document.frmPublishEvent.txtTargetURL.value="";
            document.frmPublishEvent.txtTargetURL.focus();
            return false;
            }
          
        return true;
    }    
    
    if((document.frmPublishEvent.cmbStatus.value==0)  || (document.frmPublishEvent.cmbStatus.value.length<=0))
    {
    alert("Please Select Event Status");
    document.frmPublishEvent.cmbStatus.focus();
    return false;
    }
    
     if(!chk_for_expired_date1(document.frmPublishEvent.txtEventDate.value))
    {
    return false;
    }
    
    var chk=confirm("Make Sure of the Details Entered. U can't make any changes after submission."+"\n"+"\t\tWould U Like To Submit ?");
//alert("ck val :"+chk);
    if(chk==true)
    {
         //alert("cd true");
    }
    else
    {
         //alert("cd false");
         return false;
    }
return true;
}

function officeCheck()
{
//alert("called");
                if((document.frmPublishEvent.txtEventId.value=="") || (document.frmPublishEvent.txtEventId.value.length<=0))
                {
                    alert("Please Enter Event Id");
                    document.frmPublishEvent.txtEventId.focus();
                    return false;
                    
                }


}
function confirmIt()
{
alert("confirm");
var chk=confirm(" U can't make any changes after submission. The Event 'll be Freezed");
alert("ck val :"+chk);
    if(chk==true)
    {
         alert("cd true");
    }
    else
    {
         alert("cd false");
    }

}

function chk_for_expired_date(t)
{
  //alert("called"+t);
  var entDate=document.frmPublishEvent.txtEventDate.value;
  var now()=document.frmPublishEvent.txtEventDate_h.value;
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
                   document.frmPublishEvent.txtEventDate.value="";
                   document.frmPublishEvent.txtEventDate.focus();
		
             }
             else
             {
             //alert("date in else");
             }
}


function chk_for_expired_date1(t)
{
  //alert("called"+t);
  var entDate=document.frmPublishEvent.txtEventDate.value;
  var now()=document.frmPublishEvent.txtEventDate_h.value;
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
                   document.frmPublishEvent.txtEventDate.value="";
                   document.frmPublishEvent.txtEventDate.focus();
		return false;
             }
             return true;
}