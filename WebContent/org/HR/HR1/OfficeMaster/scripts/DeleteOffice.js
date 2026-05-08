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
    var url="../../../../../ServletOfficeCadre.con?level="+level;  
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
                    var cadreid=response.getElementsByTagName("cadreid")[0].firstChild.nodeValue;                    
                    var cadrename=response.getElementsByTagName("cadrename")[0].firstChild.nodeValue;
                    //document.frmOffice.cmbHeadCode.value=cadreid;
                    //document.frmOffice.cmbHeadCode.text=cadrename;
                    //cmbHeadCode.innerHTML="";
                    //var option1=document.createElement("OPTION");
                    //option1.text="----Select Cadre------------------";
                    /*try
                                {
                                    cmbHeadCode.add(option1);
                            }catch(errorobject)
                            { 
                                     cmbHeadCode.add(option1,null);
                            }*/
                    /*var option=document.createElement("OPTION");
                     option.text=cadrename;
                     option.value=cadreid;
                              try
                                {
                                    cmbHeadCode.add(option);
                            }catch(errorobject)
                            { 
                                     cmbHeadCode.add(option,null);
                            }*/
                            document.frmOffice.cmbHeadCode.value=cadreid;
                }                
          }
    }       
}

function funclear()
{

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
                                //document.frmOffice.txtconOfficeAddress.value="";
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
                    alert("Please Enter Office Id or Select ");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                }
                
                var close= confirm("Are You Sure.You Delete this Record");
                //alert(close);
                if(close==false)
                {
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
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                }
                else
                {
                    return true;
                }
                
                
                /*if((document.frmOffice.txtOff_Name.value=="") || (document.frmOffice.txtOff_Name.value.length<=0))
                  {
                      alert("Please Enter Office Name");
                      document.frmOffice.txtOff_Name.focus();
                      return false;
                  }  
                  
                  if((document.frmOffice.txtShortName.value=="") || (document.frmOffice.txtShortName.value.length<=0))
                  {
                      alert("Please Enter Office Short Name");
                      document.frmOffice.txtShortName.focus();
                      return false;
                  }
                      
                             
                  if((document.frmOffice.cmbLevelId.value=="0") || (document.frmOffice.cmbLevelId.selectedIndex<=0))
                  {
                      alert("Please Select a Office Level");
                      document.frmOffice.cmbLevelId.focus();
                      return false;
                  }*/
                  
                  /*if((document.frmOffice.cmbHeadCode.value=="0") || (document.frmOffice.cmbHeadCode.selectedIndex<=0))
                  {
                      alert("Please Select a Head Cadre");
                      document.frmOffice.cmbHeadCode.focus();
                      return false;
                  } */
           // return true;
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
            var url="../../../../../DeleteOfficeTest.con?officeid="+officeid;
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
                            var officestatus=response.getElementsByTagName("officestatus")[0].firstChild.nodeValue;
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
                            
                            document.frmOffice.cmbHeadCode.value=headcode;
                            document.frmOffice.cmbLevelId.value=officelevel;
                            setDefaultCadre(officelevel);
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
                            if(officestatus=="DL")
                                {
                                    alert("Office Id is Deleted");
                                    document.frmOffice.cmdSub.disabled=true;
                                }
                                else if(officestatus=="null")
                                {
                                document.frmOffice.cmdSub.disabled=false;
                                }
                                else
                                {
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
                            document.frmOffice.cmbOffice_Id.disabled=true;
                            officename1();
                            
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
                            document.frmOffice.txtconOfficeName.value="";
                            document.frmOffice.txtconOfficeAddress.value="";
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
        var conofficeid=document.frmOffice.txtContrllingOfficeID.value
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
                            }
                                                      
                            if(district!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+district+"\n";
                            }
                            else
                            {
                            
                            }
                            if(pincode!=0)
                            {
                                if(pincode!="null")
                                {
                                document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+pincode+"\n";
                                }
                            }
                            else
                            {
                            
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
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
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
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
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
         
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be greater than or equal to 1970');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
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

function funclear1()
{
document.frmOffice.cmbOffice_Id.disabled=false;
    document.frmOffice.cmdSub.disabled=false;
    
}

function callOffice()
{
    var cmbOffice_Id=document.frmOffice.cmbOffice_Id.value;
    document.frmOffice.txtOffice_Id.value=cmbOffice_Id;
    officedetails(cmbOffice_Id);
}