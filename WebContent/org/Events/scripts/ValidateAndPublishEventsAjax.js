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
 
    
function editEventDet(eve_id)
{
        //alert("entered"+eve_id);
        
        if(eve_id=="" || eve_id==null)
        {
            alert("Please Enter EventID");
            document.frmPublishEvent.txtOffice_Id.focus();
        }
        else
        {
            var eventId=eve_id;
            //alert(officeid);
            startwaiting(document.frmPublishEvent);
            var url="../../../ServletEditEventsDetAjax.con?eventId="+eventId;
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
                editEventDetResponse(req);
            }
            req.send(null);
        }

}

function editEventDetResponse(req)
{
    //alert("in resp");
        if(req.readyState==4)
            {
                  if(req.status==200)
                  {      
                        stopwaiting(document.frmPublishEvent) ;
                        var response=req.responseXML.getElementsByTagName("response")[0];
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="success")
                        {
                        //alert("success");
                        
                            var Event_Id=response.getElementsByTagName("EventId")[0].firstChild.nodeValue;
                            var Event_Desc=response.getElementsByTagName("EventDesc")[0].firstChild.nodeValue;
                            var Event_Venue=response.getElementsByTagName("EventVenue")[0].firstChild.nodeValue;
                            var Event_Date=response.getElementsByTagName("EventDate")[0].firstChild.nodeValue;
                            var Target_Url=response.getElementsByTagName("TargetUrl")[0].firstChild.nodeValue;
                            var Event_Status=response.getElementsByTagName("EventStatus")[0].firstChild.nodeValue;
                        //alert("id :"+Event_Id+" des "+Event_Desc+" ven "+Event_Venue+" date "+Event_Date+" url "+Target_Url+" stat "+Event_Status);
                          
                           // clearing the fields
                           
                           
                            document.frmPublishEvent.txtEventId.value="";
                            document.frmPublishEvent.txtEventDesc.value="";
                            document.frmPublishEvent.txtEventVenue.value="";
                            document.frmPublishEvent.txtEventDate.value="";
                            document.frmPublishEvent.txtTargetURL.value="";
                            document.frmPublishEvent.cmbStatus.selectedIndex=0;

                           // populating with new values
                           if(Event_Status=="PD")
                           {
                               alert("U Can't Edit This Event\nThis Event is Freezed");
                               document.frmPublishEvent.txtEventId.focus();
                           }
                           else
                           {
                           
                            document.frmPublishEvent.txtEventId.value=Event_Id;
                            document.frmPublishEvent.txtEventDesc.value=Event_Desc;
                            document.frmPublishEvent.txtEventVenue.value=Event_Venue;
                            
                            if(Event_Date!="null")
                            {
                                document.frmPublishEvent.txtEventDate.value=Event_Date;
                            }
                            if(Target_Url!="null")
                            {
                                document.frmPublishEvent.txtTargetURL.value=Target_Url;
                            }
                            
                            document.frmPublishEvent.cmbStatus.value=Event_Status;
                            
                            // setting focus to the first field
                            
                            document.frmPublishEvent.txtEventId.focus();
                          }  /*
                            document.frmPublishEvent.cmbPrimaryID.value=primaryid;
                           
                           
                            if(date!="null")
                            {
                                document.frmPublishEvent.txtDOF.value=date;
                            }
                            if(remarks!="null")
                            {
                                document.frmPublishEvent.txtRemarks.value=remarks;
                            }
                            if(recordstatus=="FR")
                            {
                                alert("Office Id is Freezed");
                                document.frmPublishEvent.cmdSub.disabled=true;
                            }
                            officename1();
                          */  
                        }
                        else
                        {
                        
                        alert("Invalid Event Id");
                            //alert("Invalid Event Id");
                             // clearing the fields
                           
                           
                            document.frmPublishEvent.txtEventId.value="";
                            document.frmPublishEvent.txtEventDesc.value="";
                            document.frmPublishEvent.txtEventVenue.value="";
                            document.frmPublishEvent.txtEventDate.value="";
                            document.frmPublishEvent.txtTargetURL.value="";
                            
                            // setting focus to the first field
                            
                            document.frmPublishEvent.txtEventId.focus();
                            
                       
                        }
                   }
            }
                    
}

