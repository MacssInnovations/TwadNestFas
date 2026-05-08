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
            alert("Please Enter News ID");
            document.frmPublishNews.txtOffice_Id.focus();
        }
        else
        {
            var eventId=eve_id;
            //alert(officeid);
            startwaiting(document.frmPublishNews);
            var url="../../../ServletEditNewsDetAjax.con?eventId="+eventId;
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
                        stopwaiting(document.frmPublishNews) ;
                        var response=req.responseXML.getElementsByTagName("response")[0];
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="success")
                        {
                        //alert("success");
                        
                            var Event_Id=response.getElementsByTagName("EventId")[0].firstChild.nodeValue;
                            var Event_Desc=response.getElementsByTagName("EventDesc")[0].firstChild.nodeValue;
                           // var Event_Venue=response.getElementsByTagName("EventVenue")[0].firstChild.nodeValue;
                           // var Event_Date=response.getElementsByTagName("EventDate")[0].firstChild.nodeValue;
                            var Target_Url=response.getElementsByTagName("TargetUrl")[0].firstChild.nodeValue;
                            var Event_Status=response.getElementsByTagName("EventStatus")[0].firstChild.nodeValue;
                        //alert("id :"+Event_Id+" des "+Event_Desc+" ven "+Event_Venue+" date "+Event_Date+" url "+Target_Url+" stat "+Event_Status);
                          
                           // clearing the fields
                        
                           
                            document.frmPublishNews.txtEventId.value="";
                            document.frmPublishNews.txtEventDesc.value="";
                            //document.frmPublishNews.txtEventVenue.value="";
                            //document.frmPublishNews.txtEventDate.value="";
                            document.frmPublishNews.txtTargetURL.value="";
                            document.frmPublishNews.cmbStatus.selectedIndex=0;

                           // populating with new values
                         if(Event_Status=="PD")
                           {
                               alert("U Can't Edit This News\nThis News is Freezed");
                               document.frmPublishNews.txtEventId.focus();
                           }
                           else
                           {   
                           
                            document.frmPublishNews.txtEventId.value=Event_Id;
                            document.frmPublishNews.txtEventDesc.value=Event_Desc;
                            //document.frmPublishNews.txtEventVenue.value=Event_Venue;
                          /*  
                            if(Event_Date!="null")
                            {
                                document.frmPublishNews.txtEventDate.value=Event_Date;
                            }
                          */  
                            
                            if(Target_Url!="null")
                            {
                                document.frmPublishNews.txtTargetURL.value=Target_Url;
                            }
                            
                            document.frmPublishNews.cmbStatus.value=Event_Status;
                            
                            // setting focus to the first field
                            
                            document.frmPublishNews.txtEventId.focus();
                            /*
                            document.frmPublishNews.cmbPrimaryID.value=primaryid;
                           
                           
                            if(date!="null")
                            {
                                document.frmPublishNews.txtDOF.value=date;
                            }
                            if(remarks!="null")
                            {
                                document.frmPublishNews.txtRemarks.value=remarks;
                            }
                            if(recordstatus=="FR")
                            {
                                alert("Office Id is Freezed");
                                document.frmPublishNews.cmdSub.disabled=true;
                            }
                            officename1();
                          */  
                          }
                        }
                        else
                        {
                        
                        alert("Invalid News Id");
                            //alert("Invalid Event Id");
                             // clearing the fields
                           
                           
                            document.frmPublishNews.txtEventId.value="";
                            document.frmPublishNews.txtEventDesc.value="";
                            //document.frmPublishNews.txtEventVenue.value="";
                           // document.frmPublishNews.txtEventDate.value="";
                            document.frmPublishNews.txtTargetURL.value="";
                            
                            // setting focus to the first field
                            
                            document.frmPublishNews.txtEventId.focus();
                            
                       
                        }
                   }
            }
                    
}

