function nullCheck()
{
    //alert("called@");
    if((frmPublishNews.txtEventId.value=="") || (frmPublishNews.txtEventId.value.length<=0))
    {
         alert("Please Enter News Id");
         frmPublishNews.txtEventId.focus();
         return false;
    } 
    if((frmPublishNews.txtEventDesc.value=="") || (frmPublishNews.txtEventDesc.value.length<=0))
    {
         alert("Please Enter News Description");
         frmPublishNews.txtEventDesc.focus();
         return false;
    } 
 /*    if((frmPublishNews.txtEventVenue.value=="") || (frmPublishNews.txtEventVenue.value.length<=0))
    {
         alert("Please Enter Event Venue");
         frmPublishNews.txtEventVenue.focus();
         return false;
    } 
    
     if((frmPublishNews.txtEventDate.value=="" ) || (frmPublishNews.txtEventDate.value.length<=0))
    {
         alert("Please Enter Date of News");
         frmPublishNews.txtEventDate.focus();
         return false;
    }  */
    
    if((frmPublishNews.txtTargetURL.value=="" ) || (frmPublishNews.txtTargetURL.value.length<=0))
    {
         alert("Please Enter Target URL");
         frmPublishNews.txtTargetURL.focus();
         return false;
    }   
    
     if((document.frmPublishNews.txtTargetURL.value!="" ) || (document.frmPublishNews.txtTargetURL.value.length>0))
    {
            var strURL=document.frmPublishNews.txtTargetURL.value.substring(3,0); 
            var strURL1=document.frmPublishNews.txtTargetURL.value.substring(5,0);
           // alert(strURL+"  "+strURL1);
            if((strURL!="www") &&(strURL1!="http:"))
            {
            alert("Enter Target URL in one of the Given Formats:\n\n \t(i) www.tn.nic.in\n\n \t(ii) http://www.tn.nic.in");
            document.frmPublishNews.txtTargetURL.value="";
            document.frmPublishNews.txtTargetURL.focus();
            return false;
            }
          
        return true;
    }    
    
    
    if((frmPublishNews.cmbStatus.value==0)  || (frmPublishNews.cmbStatus.value.length<=0))
    {
    alert("Please Select News Status");
    frmPublishNews.cmbStatus.focus();
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
                if((frmPublishNews.txtEventId.value=="") || (frmPublishNews.txtEventId.value.length<=0))
                {
                    alert("Please Enter News Id ");
                    frmPublishNews.txtEventId.focus();
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
