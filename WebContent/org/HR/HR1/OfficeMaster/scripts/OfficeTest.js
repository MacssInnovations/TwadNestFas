var global_headcode=0;
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
    
    
    
function checkLevel()
{
                                 
    var level=document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].text;
    var levelvalue=document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].value;
    
    //alert(level);
    if(level=="Head Office" || level=="Region")// || level=="Lab" || level=="Circle")
    {      
      //alert(level);
      disableControllingOffice();
      /*document.frmOffice.txtContrllingOfficeID.value=levelvalue; 
      document.frmOffice.txtHContrllingOfficeID.value=levelvalue;
      document.frmOffice.txtconOfficeName.value="";
      document.frmOffice.txtconOfficeName.value=level;*/
      document.frmOffice.cmbPrimaryID.disabled=true;
      fun1(levelvalue);
      
      if(level=="Head Office")
      {
        /*document.frmOffice.txtContrllingOfficeID.value=levelvalue; 
        document.frmOffice.txtHContrllingOfficeID.value=levelvalue; 
        document.frmOffice.txtconOfficeName.value="";*/
        document.frmOffice.txtconOfficeName.value=level;
      }
    } 
    else if(level=="Lab" || level=="Circle")
    {
        enableControllingOffice();      
        //document.frmOffice.txtconOfficeName.value="";
        //document.frmOffice.txtconOfficeAddress.value="";
        //document.frmOffice.txtconOfficeAddress1.value="";
        document.frmOffice.cmbPrimaryID.disabled=true;
    }
    else if(level != "----Select OfficeLevel----")
    {        
        enableControllingOffice();
        //document.frmOffice.txtContrllingOfficeID.value=""; 
        //document.frmOffice.txtconOfficeName.value="";
        //document.frmOffice.txtconOfficeAddress.value="";
        //document.frmOffice.txtconOfficeAddress1.value="";
        document.frmOffice.cmbPrimaryID.disabled=false;
    }
    else
    {     
        // nothing        
    }
    //setDefaultCadre(document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].value);
}

function disableControllingOffice()
{
    document.frmOffice.txtContrllingOfficeID.disabled=true;
    document.frmOffice.cmbControllingLevel.disabled=true; 
    document.frmOffice.txtContrllingOfficeID.value=""; 
    document.frmOffice.txtHContrllingOfficeID.value="";
}

function enableControllingOffice()
{
    //document.frmOffice.txtContrllingOfficeID.disabled=false;
    document.frmOffice.cmbControllingLevel.disabled=false;
   // document.frmOffice.txtContrllingOfficeID.value=""; 
    //document.frmOffice.txtHContrllingOfficeID.value="";
}

// loading office details

function setDefaultCadre(level)
{  
    startwaiting(document.frmOffice) ;
    var url="../../../../../ServletCadreTest.con?level="+level;  
    //alert("calling"+url);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               LoadDefaultCadre(req);             
            }
            req.send(null);     
}

function LoadDefaultCadre(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {     
                stopwaiting(document.frmOffice) ;
                var cmbHeadCode=document.getElementById("cmbHeadCode");
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {                    
                   cmbHeadCode.innerHTML="";
                    var option1=document.createElement("OPTION");
                    
                    option1.text="----Select Cadre------------------";
                    option1.value="0";
                    try
                    {
                        cmbHeadCode.add(option1);
                    }catch(errorobject)
                    { 
                
                         cmbHeadCode.add(option1,null);
                    }
                     var option=document.createElement("OPTION");
                     var value=response.getElementsByTagName("options");
                     //alert(value.length);
                     for(var i=0;i<value.length;i++)
                     {
                        var tmpoption=value.item(i);
                        var cadreid=tmpoption.getElementsByTagName("cadreid")[0].firstChild.nodeValue;
                        var cadrename=tmpoption.getElementsByTagName("cadrename")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                        option.text=cadrename;
                        option.value=cadreid;
                      try
                        {
                            cmbHeadCode.add(option);
                        }catch(errorobject)
                        { 
                                 cmbHeadCode.add(option,null);
                        }
                     }
                     cmbHeadCode.value=global_headcode;
                     global_headcode=0;
                }                
          }
    }       
}

function funclear()
{

    var cmbHeadCode=document.getElementById("cmbHeadCode");
    cmbHeadCode.innerHTML="";
    var option1=document.createElement("OPTION");
    option1.text="----Select Cadre------------------";
    try
                {
                    cmbHeadCode.add(option1);
            }catch(errorobject)
            { 
                     cmbHeadCode.add(option1,null);
            }
            
    var cmbControllingLevel=document.getElementById("cmbControllingLevel");
    cmbControllingLevel.disabled=false;
    var cmbPrimaryID=document.getElementById("cmbPrimaryID");
    cmbPrimaryID.disabled=false;
    document.frmOffice.cmdSub.disabled=false;
}
function fun1(levelvalue)
{
    //alert(levelvalue);
            startwaiting(document.frmOffice) ;
            var url="../../../../../ServletOfficeName1.con?ConOfficeId="+levelvalue;  
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {     
                OfficeLevelResponse(req);
            }
            req.send(null);
}


function OfficeLevelResponse(req)
     {
            if(req.readyState==4)
            {
                  if(req.status==200)
                  {      
                        stopwaiting(document.frmOffice) ;
                        var response=req.responseXML.getElementsByTagName("response")[0];                
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                        if(flag=="success")
                        {   
                            var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                            var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                            var address=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                            var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                            var city=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                            var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                            var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                            document.frmOffice.txtContrllingOfficeID.value=id;
                            document.frmOffice.txtHContrllingOfficeID.value=id;
                            document.frmOffice.txtconOfficeName.value=name;
                            document.frmOffice.txtconOfficeAddress.value="";
                            //document.frmOffice.txtconOfficeAddress1.value="";
                            if(address!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=address+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value="";
                            }
                            if(address2!="null")
                            {
                                document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+address2+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            
                            if(city!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+city+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(district!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+district+"\n";
                            }
                            else
                            {
                                //document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(pincode!=0 )
                            {
                                if(pincode!="null")
                                {
                                    document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+pincode;
                                }
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                        }
                        else
                        {
                            alert("Valid Information Not Found");
                            document.frmOffice.txtconOfficeName.value="";
                            document.frmOffice.txtconOfficeAddress.value="";
                            //document.frmOffice.txtconOfficeAddress1.value="";
                        }
                
                }
           }     
     
     }    
    
    //Function for NullCheck
   function nullcheck()
   {
   //alert('hai');
                if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                {
                    alert("Please Enter Office Id or Select in Office Details");
                    //document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                }
                if((document.frmOffice.txtOff_Name.value=="") || (document.frmOffice.txtOff_Name.value.length<=0))
                  {
                      alert("Please Enter Office Name in Office Details");
                      //document.frmOffice.txtOff_Name.focus();
                      return false;
                  }  
                  
                  if((document.frmOffice.txtShortName.value=="") || (document.frmOffice.txtShortName.value.length<=0))
                  {
                      alert("Please Enter Office Short Name in Office Details");
                      //document.frmOffice.txtShortName.focus();
                      return false;
                  }
                      
                             
                  if((document.frmOffice.cmbLevelId.value=="0") || (document.frmOffice.cmbLevelId.selectedIndex<=0))
                  {
                      alert("Please Select a Office Level in Office Details");
                      //document.frmOffice.cmbLevelId.focus();
                      return false;
                  }
                  if((document.frmOffice.cmbHeadCode.value=="0") || (document.frmOffice.cmbHeadCode.selectedIndex<=0))
                  {
                      alert("Please Select a Head Cadre in Office Details");
                      //document.frmOffice.cmbHeadCode.focus();
                      return false;
                  }
                  if((document.frmOffice.cmbDistrict.value=="0") || (document.frmOffice.cmbDistrict.selectedIndex<=0))
                  {
                      alert("Please Select a District in Contact Details");
                      //MakeDivVisible('addresses');
                      //document.frmOffice.cmbDistrict.focus();
                      return false;
                  }  
                  
            return true;
        }
    
    
function officedetails(ctlOffice)
{
        //alert('hai');
        
        if(ctlOffice=="" || ctlOffice==null)
        {
            alert("Please Enter OfficeId or Select");
            //document.frmOffice.txtOffice_Id.focus();
        }
        else
        {
            var officeid=ctlOffice;
            //alert(officeid);
            startwaiting(document.frmOffice) ;
            var url="../../../../../ServletOfficeTest.con?officeid="+officeid;
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
                OfficeDetailsResponse(req);
            }
            req.send(null);
        }

}
function OfficeDetailsResponse(req)
{
    
        if(req.readyState==4)
            {
                  if(req.status==200)
                  {      
                        stopwaiting(document.frmOffice) ;
                        //alert(req.responseTEXT);
                        var response=req.responseXML.getElementsByTagName("response")[0];
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                        if(flag=="success")
                        {
                            var officename=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
                            var officeshortname=response.getElementsByTagName("officeshortname")[0].firstChild.nodeValue;
                            var headcode=response.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                            var officelevel=response.getElementsByTagName("officelevel")[0].firstChild.nodeValue;
                            
                            if(officelevel=="RN" || officelevel=="HO")
                            {
                                document.frmOffice.cmbControllingLevel.disabled=true;
                            }
                            var primaryid=response.getElementsByTagName("primaryid")[0].firstChild.nodeValue;
                            var controllingofficeid=response.getElementsByTagName("controlofficeid")[0].firstChild.nodeValue;
                            var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                            var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                            var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                            var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                            var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                            var city=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                            var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                            var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                            var std=response.getElementsByTagName("std")[0].firstChild.nodeValue;
                            var phone=response.getElementsByTagName("phone")[0].firstChild.nodeValue;
                            var addphone=response.getElementsByTagName("addphone")[0].firstChild.nodeValue;
                            var fax=response.getElementsByTagName("fax")[0].firstChild.nodeValue;
                            var addfax=response.getElementsByTagName("addfax")[0].firstChild.nodeValue;
                            var email=response.getElementsByTagName("email")[0].firstChild.nodeValue;
                            var addemail=response.getElementsByTagName("addemail")[0].firstChild.nodeValue;
                            document.frmOffice.txtShortName.value="";
                            document.frmOffice.txtOff_Name.value="";
                            document.frmOffice.cmbHeadCode.selectedIndex=0;
                            document.frmOffice.cmbLevelId.selectedIndex=0;
                            document.frmOffice.txtContrllingOfficeID.value="";
                            document.frmOffice.txtHContrllingOfficeID.value="";
                            document.frmOffice.cmbPrimaryID.value="";
                            document.frmOffice.txtDOF.value="";
                            document.frmOffice.txtRemarks.value="";
                            document.frmOffice.txtAdd1.value="";
                            document.frmOffice.txtAdd2.value="";
                            document.frmOffice.txtAdd3.value="";
                            document.frmOffice.cmbDistrict.selectedIndex=0;
                            document.frmOffice.txtPinCode.value="";
                            document.frmOffice.txtSTDCode.value="";
                            document.frmOffice.txtPhoneNo.value="";
                            document.frmOffice.txtAddPhoneNo.value="";
                            document.frmOffice.txtFAXNo.value="";
                            document.frmOffice.txtAddFAXNo.value="";
                            document.frmOffice.txtEMail.value="";
                            document.frmOffice.txtAddEMail.value="";
                            document.frmOffice.txtShortName.value=officeshortname;
                            document.frmOffice.txtOff_Name.value=officename;
                            
                            
                            setDefaultCadre(officelevel);
                            document.frmOffice.cmbLevelId.value=officelevel;
                            global_headcode=headcode;
                            document.frmOffice.txtContrllingOfficeID.value=controllingofficeid;
                            document.frmOffice.txtContrllingOfficeID.disabled=true;
                            document.frmOffice.txtHContrllingOfficeID.value=controllingofficeid;
                            if(primaryid!="null")
                            {
                            
                            document.frmOffice.cmbPrimaryID.value=primaryid;
                            }
                            else
                            {
                            document.frmOffice.cmbPrimaryID.disabled=true;
                            
                            }
                            if(date!="null")
                            {
                                document.frmOffice.txtDOF.value=date;
                            }
                            if(remarks!="null")
                            {
                                document.frmOffice.txtRemarks.value=remarks;
                            }
                            if(recordstatus=="FR")
                            {
                                alert("Office Id is Freezed");
                                document.frmOffice.cmdSub.disabled=true;
                            }
                            else
                            {
                                document.frmOffice.cmdSub.disabled=false;
                            }
                            if(address1!="null")
                            {
                                document.frmOffice.txtAdd1.value=address1;
                            }
                            if(address2!="null")
                            {
                                document.frmOffice.txtAdd2.value=address2;
                            }
                            if(city!="null")
                            {
                                document.frmOffice.txtAdd3.value=city;
                            }
                            if(district!="0")
                            {
                                if(district!="null")
                                {
                                    document.frmOffice.cmbDistrict.value=district;
                                }
                            }
                            if(pincode!="0")
                            {
                                if(pincode!="null")
                                {
                                    document.frmOffice.txtPinCode.value=pincode;
                                }
                            }
                            if(std!="null")
                            {
                                if(std!="0")
                                {
                                document.frmOffice.txtSTDCode.value=std;
                                }
                            }
                            if(phone!="null")
                            {
                                document.frmOffice.txtPhoneNo.value=phone;
                            }
                            if(addphone!="null")
                            {
                                document.frmOffice.txtAddPhoneNo.value=addphone;
                            }
                            if(fax!="null")
                            {
                                document.frmOffice.txtFAXNo.value=fax;
                            }
                            if(addfax!="null")
                            {
                                document.frmOffice.txtAddFAXNo.value=addfax;
                            }
                            if(email!="null")
                            {
                                document.frmOffice.txtEMail.value=email;
                            }
                            if(addemail!="null")
                            {
                                document.frmOffice.txtAddEMail.value=addemail;
                            }
                            setOfficeLevel();
                            document.frmOffice.cmbOffice_Id.disabled=true;
                            officename1();
                            deleteCheck();
                            
                        }
                        else
                        {
                            alert("Invalid Office Id");
                            document.frmOffice.txtOffice_Id.value="";
                            document.frmOffice.txtShortName.value="";
                            document.frmOffice.txtOff_Name.value="";
                            document.frmOffice.cmbHeadCode.selectedIndex=0;
                            document.frmOffice.cmbLevelId.selectedIndex=0;
                            document.frmOffice.txtContrllingOfficeID.value="";
                            document.frmOffice.txtHContrllingOfficeID.value="";
                            document.frmOffice.cmbPrimaryID.value="";
                            document.frmOffice.txtDOF.value="";
                            document.frmOffice.txtRemarks.value="";
                            document.frmOffice.txtconOfficeName.value="";
                            document.frmOffice.txtconOfficeAddress.value="";
                            document.frmOffice.txtAdd1.value="";
                            document.frmOffice.txtAdd2.value="";
                            document.frmOffice.txtAdd3.value="";
                            document.frmOffice.cmbDistrict.selectedIndex=0;
                            document.frmOffice.txtPinCode.value="";
                            document.frmOffice.txtSTDCode.value="";
                            document.frmOffice.txtPhoneNo.value="";
                            document.frmOffice.txtAddPhoneNo.value="";
                            document.frmOffice.txtFAXNo.value="";
                            document.frmOffice.txtAddFAXNo.value="";
                            document.frmOffice.txtEMail.value="";
                            document.frmOffice.txtAddEMail.value="";
                            //document.frmOffice.txtconOfficeAddress1.value="";
                            document.frmOffice.txtOffice_Id.focus();
                        }
                   }
            }
                    
}
function officename1()
{
    //alert('hai');
    officename();
}

function officename()
     {
        var conofficeid=document.frmOffice.txtContrllingOfficeID.value;
        //alert(conofficeid);
        document.frmOffice.txtHContrllingOfficeID.value=document.frmOffice.txtContrllingOfficeID.value;
        startwaiting(document.frmOffice);
            var url="../../../../../ServletOfficeName.con?ConOfficeId="+conofficeid;  
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {     
                OfficeNameResponse(req);
            }
            req.send(null);
     }
     
     function OfficeNameResponse(req)
     {
            if(req.readyState==4)
            {
                  if(req.status==200)
                  {     
                        stopwaiting(document.frmOffice);
                        var response=req.responseXML.getElementsByTagName("response")[0];                
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                        if(flag=="success")
                        {                    
                            var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                            var address=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                            var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                            var city=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                            var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                            var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                            document.frmOffice.txtconOfficeName.value=name;
                            document.frmOffice.txtconOfficeAddress.value="";
                            //document.frmOffice.txtconOfficeAddress1.value="";
                            
                            if(address!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=address+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(address2!="null")
                            {
                                document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+address2+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(city!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+city+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(district!="null")
                            {
                                document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+district+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(pincode!=0)
                            {       if(pincode!="null")
                                    {
                                           document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+pincode;
                                    }
                            }
                            else
                            {
                              //  document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            
                        }
                        else
                        {
                            alert("Valid Information Not Found");
                            document.frmOffice.txtconOfficeName.value="";
                            document.frmOffice.txtconOfficeAddress.value="";
                            //document.frmOffice.txtconOfficeAddress1.value="";
                        }
                
                }
           }     
     
     }
     
     
var winjob;

function jobpopup()
{
    if (winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/OfficePopupTemp.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
    
}
function forChildOption()
{

  if (winjob && winjob.open && !winjob.closed) 
         winjob.officeSelection(true,true,true,false);
}

function doParentJob(jobid,deptid)
{
//alert(deptid);
if(deptid=="TWAD")
{
    document.frmOffice.txtOffice_Id.value=jobid;
    //document.HRE_EmployeeServiceDetails.txtDept_Id.value=deptid;
    officedetails(jobid);
    //deleteCheck();
    return true
}
else
{
        alert('Please select a TWAD Office');
        if (winjob && winjob.open && !winjob.closed) 
        {
           winjob.resizeTo(500,500);
           winjob.moveTo(250,250); 
           winjob.focus();
        }
        return false
}
}

window.onunload=function()
{
//alert('hello');
//if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
}


function numbersonly1(e,t)
    {
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
function officeCheck()
{

                if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                }


}

//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}

function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
} 


function checkAttachedoffice()
{
//alert('hai');

            var officeid=document.frmOffice.txtHContrllingOfficeID.value;
            startwaiting(document.frmOffice) ;
            url="../../../../../ServletCheckAttachmentOffice.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckAttachedOfficeResponse(req);                
            }
            req.send(null);

}   


function CheckAttachedOfficeResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {     
          stopwaiting(document.frmOffice) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    alert("This Office is Closed");
                    document.frmOffice.cmdSub.disabled=true;
                    return false;
                
                }
                else
                {
                document.frmOffice.cmdSub.disabled=false;
                    //return true;
                }
           }
        }
}


function funclear1()
{
    //alert('calling');
    document.frmOffice.cmbOffice_Id.disabled=false;
    document.frmOffice.cmdSub.disabled=false;
}


function callOffice()
{
    var cmbOffice_Id=document.frmOffice.cmbOffice_Id.value;
    document.frmOffice.txtOffice_Id.value=cmbOffice_Id;
    officedetails(cmbOffice_Id);
}


function deleteCheck()
{
    
           var officeid=document.frmOffice.txtOffice_Id.value;
            startwaiting(document.frmOffice) ;
            url="../../../../../ServletOfficeDeleteCheck.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,false);         
            req.onreadystatechange=function()
            {
                CheckOfficeDelete(req);                
            }
            req.send(null);

}

function CheckOfficeDelete(req)
{
    
    if(req.readyState==4)
        {
          if(req.status==200)
          {     
              stopwaiting(document.frmOffice) ;
              var response=req.responseXML.getElementsByTagName("response")[0];
              var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
              var officestatus=response.getElementsByTagName("officestatus")[0].firstChild.nodeValue;
                if(officestatus=="DL")
                {
                    alert("This Office is Deleted");
                    document.frmOffice.txtOffice_Id.value="";
                    document.frmOffice.txtOff_Name.value="";
                    document.frmOffice.txtShortName.value="";
                    document.frmOffice.txtContrllingOfficeID.value="";
                    document.frmOffice.txtHContrllingOfficeID.value="";
                    document.frmOffice.txtconOfficeName.value="";
                    document.frmOffice.txtconOfficeAddress.value="";
                    document.frmOffice.txtDOF.value="";
                    document.frmOffice.txtRemarks.value="";
                    document.frmOffice.cmbPrimaryID.selectedIndex=0;
                    document.frmOffice.cmbLevelId.selectedIndex=0;
                    document.frmOffice.cmbHeadCode.selectedIndex=0;
                    document.frmOffice.cmdSub.disabled=true;
                    return false;
                
                }
                else
                {
               // document.frmOffice.cmdSub.disabled=false;
                    //return true;
                }
            }
        }
}


function checkpincode()
{

         if(isNaN(document.frmOffice.txtPinCode.value))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtPinCode.value="";
            ddocument.frmOffice.txtPinCode.focus();
            return false;
    }
     if((document.frmOffice.txtPinCode.value.length!=0) && !( document.frmOffice.txtPinCode.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtPinCode.value.length==1  ))
    {
           if((document.frmOffice.txtPinCode.value.length!=6 || document.frmOffice.txtPinCode.value==0) && document.frmOffice.txtPinCode.value.length!=0 )
            {
                    alert("Pincode Start should be 6. Zero not allowed");
                    document.frmOffice.txtPinCode.focus();
                    return false;
            }
    }
    return true;

}

function checkstd()
{
    if(isNaN(document.frmOffice.txtSTDCode.value) && !( document.frmOffice.txtSTDCode.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtSTDCode.value.length==1  ))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtSTDCode.value="";
            document.frmOffice.txtSTDCode.focus();
            return false;
    }
     if((document.frmOffice.txtSTDCode.value.length!=0) && !( document.frmOffice.txtSTDCode.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtSTDCode.value.length==1  ))
    {
        if((document.frmOffice.txtSTDCode.value.length <2 || document.frmOffice.txtSTDCode.value==0)  && document.frmOffice.txtSTDCode.value.length !=0)
        {
                    alert("STD Code Length should be between 2 and 5.  Zero not allowed");
                    document.frmOffice.txtSTDCode.focus();
                    return false;
        }
    }
    return true;

}

function checkphone()
{
    if(isNaN(document.frmOffice.txtPhoneNo.value))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtPhoneNo.value="";
            document.frmOffice.txtPhoneNo.focus();
            return false;
    }
     if((document.frmOffice.txtPhoneNo.value.length!=0) && !( document.frmOffice.txtPhoneNo.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtPhoneNo.value.length==1  ))
    {
        if(document.frmOffice.txtPhoneNo.value.length <6  || document.frmOffice.txtPhoneNo.value==0 )
        {
                    alert("Phone No. Length atleast 6.  Zero not allowed");
                    document.frmOffice.txtPhoneNo.focus();
                    return false;
        }
    }
    return true;
}


function checkaddphone()
{
   
     if((document.frmOffice.txtAddPhoneNo.value.length!=0) && !( document.frmOffice.txtAddPhoneNo.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAddPhoneNo.value.length==1  ))
    {
        var no=document.frmOffice.txtAddPhoneNo.value;
        var s=no.split(',');
        
        for(i=0;i<s.length;i++)
        {
        
            if(s[i].indexOf('-')!=-1)
            {
                
                var t=s[i].split('-');
                if(t[0].length<2 || t[0].length >5 || t[1].length<6 || t[0].value==0 || t[1].value==0)
                {
                    alert(s[i]+ " not a valid phone No.\n Phone No. Length atleast 6\nSTD Code Length should be between 2 and 5.\n  Zero not allowed.");
                    document.frmOffice.txtAddPhoneNo.focus();
                    return false;
                }
                
            }
            else if(s[i].length <6 || s[i].value==0)
            {
                    alert(s[i]+ " not a valid phone No.\n Phone No. Length atleast 6\nSTD Code Length should be between 2 and 5.\n  Zero not allowed.");
                    document.frmOffice.txtAddPhoneNo.focus();
                    return false;
            }
                    
        
        }
    }
    return true;
}

function checkfax()
{
    if(isNaN(document.frmOffice.txtFAXNo.value))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtFAXNo.value="";
            document.frmOffice.txtFAXNo.focus();
            return false;
    }
     if((document.frmOffice.txtFAXNo.value.length!=0) && !( document.frmOffice.txtFAXNo.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtFAXNo.value.length==1  ))
    {
        if(document.frmOffice.txtFAXNo.value.length <6  )
        {
                    alert("Fax No. Length atleast 6");
                    document.frmOffice.txtFAXNo.focus();
                    return false;
        }
    }
    return true;
}

function checkfaxno()
{
   
     if((document.frmOffice.txtAddFAXNo.value.length!=0) && !( document.frmOffice.txtAddFAXNo.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAddFAXNo.value.length==1  ))
    {
        var no=document.frmOffice.txtAddFAXNo.value;
        var s=no.split(',');
        
        for(i=0;i<s.length;i++)
        {
        
            if(s[i].indexOf('-')!=-1)
            {
                
                var t=s[i].split('-');
                if(t[0].length<2 || t[0].length >5 || t[1].length<6 || t[0].value==0 || t[1].value==0)
                {
                    alert(s[i]+ " not a valid fax no.\n Fax No. Length atleast 6 Length. Zero not allowed.");
                    document.frmOffice.txtAddFAXNo.focus();
                    return false;
                }
                
            }
            else if(s[i].length <6 || s[i].value==0)
            {
                    alert(s[i]+ " not a valid Fax No.\n Fax No. Length atleast 6 Length.Zero not allowed.");
                    document.frmOffice.txtAddFAXNo.focus();
                    return false;
            }
                    
        
        }
    }
    return true;
}


function checkemail()
{
if((document.frmOffice.txtEMail.value.length!=0) && !( document.frmOffice.txtEMail.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtEMail.value.length==1  ))
    {
        var x = document.frmOffice.txtEMail.value;
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test(x))
	 {alert('Enter correct email address');

        document.frmOffice.txtEMail.value="";
        document.frmOffice.txtEMail.focus();
        return false;
        }
    }
    return true;
}


function addphone(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode
        if (unicode!=8)
        {
            if ((unicode>=48 && unicode<=57) || unicode==45  || unicode==44   )
                return true;
            else
                return false;
        }
    }



function addcheckemail()
{
if((document.frmOffice.txtAddEMail.value.length!=0) && !( document.frmOffice.txtAddEMail.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAddEMail.value.length==1  ))
    {
    
        var x = document.frmOffice.txtAddEMail.value;
       
        if((x.lastIndexOf(',')+1 == x.length) && x.length!=0)
        {
            x=x.substring(0,x.length-1);
            document.frmOffice.txtAddEMail.value=x;
        }
        var a=x.split(',');
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	for(i=0;i<a.length;i++)
        {
            if (!filter.test(a[i]))
             {
             alert('Enter correct email address');
             document.frmOffice.txtAddEMail.value="";
             document.frmOffice.txtAddEMail.focus();
            return false;
            }
        }
    }
    return true;
}

function setOfficeLevel()
{  
    var level=document.frmOffice.cmbLevelId.value;
    //alert(level);
    startwaiting(document.frmOffice) ;
    var url="../../../../../ServletCadreLevel.con?level="+level;  
    //alert("calling"+url);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               LoadOfficeLevel(req);             
            }
            req.send(null);     
}

function LoadOfficeLevel(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {     
                stopwaiting(document.frmOffice) ;
                var cmbControllingLevel=document.getElementById("cmbControllingLevel");
                //alert(req.responseTEXT);
                
                var response=req.responseXML.getElementsByTagName("response")[0];                
                //alert("response:"+response);
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                //var option=document.createElem
                
                //alert(flag);
                if(flag=="success")
                {                    
                    
                    //document.frmOffice.cmbHeadCode.value=cadreid;
                    //document.frmOffice.cmbHeadCode.text=cadrename;
                    cmbControllingLevel.innerHTML="";
                    var option1=document.createElement("OPTION");
                    
                    option1.text="----Select Office Level------------------";
                    try
                    {
                        cmbControllingLevel.add(option1);
                    }catch(errorobject)
                    { 
                
                         cmbControllingLevel.add(option1,null);
                    }
                     var option=document.createElement("OPTION");
                     var value=response.getElementsByTagName("options");
                     //alert(value.length);
                     for(var i=0;i<value.length;i++)
                     {
                        var tmpoption=value.item(i);
                        var officelevelid=tmpoption.getElementsByTagName("officelevel")[0].firstChild.nodeValue;
                        var officelevelname=tmpoption.getElementsByTagName("officelevelname")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                        option.text=officelevelname;
                        option.value=officelevelid;
                      try
                        {
                            cmbControllingLevel.add(option);
                        }catch(errorobject)
                        { 
                                 cmbControllingLevel.add(option,null);
                        }
                     }
                     
                    // var cadreid=response.getElementsByTagName("cadreid")[0].firstChild.nodeValue;                    
                     //var cadrename=response.getElementsByTagName("cadrename")[0].firstChild.nodeValue;
                     
                               // document.frmOffice.cmbHeadCode.value=cadreid;
                } 
                else
                {
                }
          }
    }       
}