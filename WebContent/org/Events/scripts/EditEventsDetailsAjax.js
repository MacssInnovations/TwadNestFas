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
        //alert("edit entered"+eve_id);
        
        if(eve_id=="" || eve_id==null)
        {
            alert("Please Enter EventID");
            document.frmEditEvent.txtOffice_Id.focus();
        }
        else
        {
            var eventId=eve_id;
            //alert(officeid);
            startwaiting(document.frmEditEvent);
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
    //alert("edit in resp");
        if(req.readyState==4)
            {
                  if(req.status==200)
                  {      
                        stopwaiting(document.frmEditEvent) ;
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
                       // alert("id :"+Event_Id+" des "+Event_Desc+" ven "+Event_Venue+" date "+Event_Date+" url "+Target_Url+" stat "+Event_Status);
                          
                           // clearing the fields
                           
                           
                            document.frmEditEvent.txtEventId.value="";
                            document.frmEditEvent.txtEventDesc.value="";
                            document.frmEditEvent.txtEventVenue.value="";
                            document.frmEditEvent.txtEventDate.value="";
                            document.frmEditEvent.txtTargetURL.value="";
                            
                           // populating with new values
                         
                           if(Event_Status=="PD")
                           {
                               alert("U Can't Edit This Event\nThis Event is Freezed");
                               document.frmEditEvent.txtEventId.focus();
                           }
                           else
                           {
                                document.frmEditEvent.txtEventId.value=Event_Id;
                                document.frmEditEvent.txtEventDesc.value=Event_Desc;
                                document.frmEditEvent.txtEventVenue.value=Event_Venue;
                                if(Event_Date!="null")
                                {
                                    document.frmEditEvent.txtEventDate.value=Event_Date;
                                }
                                if(Target_Url!="null")
                                {
                                    document.frmEditEvent.txtTargetURL.value=Target_Url;
                                }
                                
                                // setting focus to the first field
                                
                                document.frmEditEvent.txtEventId.focus();
                          }
                        }
                        else
                        {
                        
                        
                            //alert("Invalid Event Id");
                             // clearing the fields
                           alert("Invalid Event Id");
                           
                            document.frmEditEvent.txtEventId.value="";
                            document.frmEditEvent.txtEventDesc.value="";
                            document.frmEditEvent.txtEventVenue.value="";
                            document.frmEditEvent.txtEventDate.value="";
                            document.frmEditEvent.txtTargetURL.value="";
                            
                            // setting focus to the first field
                            
                            document.frmEditEvent.txtEventId.focus();
                            
                        
                        }
                   }
            }
                    
}

