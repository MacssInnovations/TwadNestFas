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


// loading office details

function loadOffice(id)
{   
//alert('loadoffice');
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose..)");
        document.frmConversion.txtOffice_Id.focus();
    }
    else
    {
            startwaiting(document.frmConversion) ;
            var url="../../../../../ServletLoadOfficeDetails.con?ID="+id;            
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               LoadOfficeDetails(req);             
            }
            req.send(null);        
    }
}

function LoadOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {                
                stopwaiting(document.frmConversion) ;
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0];
                    var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                    if(level=="H.O.")
                    {
                        alert("As this Office controls some other offices,so this Office cannot be Attached");
                        document.frmConversion.txtOffice_Id.value="";
                        document.frmConversion.txtOfficeName.value="";
                        document.frmConversion.txtOfficeType.value="";
                        document.frmConversion.txtOfficeAddress.value="";
                        document.frmConversion.txtAttachedOfficeID.value="";
                        document.frmConversion.txtAttachedOfficeName.value="";
                        document.frmConversion.txtAttachedOfficeAddress.value="";
                        document.frmConversion.txtDOC.value="";
                        document.frmConversion.cmbPrimaryID.selectedIndex=0;
                        document.frmConversion.txtExistOfficeId.value="";
                        document.frmConversion.txtExistOfficeName.value="";
                        document.frmConversion.txtExistOfficeAddress.value="";
                        document.frmConversion.txtOffice_Id.focus();
                    }
                    else
                    {
                    document.frmConversion.txtOfficeName.value=name.firstChild.nodeValue;
                    if(level=="Division" || level=="Sub-Division")
                        document.frmConversion.txtOfficeType.value=type;
                    else
                        document.frmConversion.txtOfficeType.value="Office level : " + level;
                    
                    if(address1!="null")
                    {
                    document.frmConversion.txtOfficeAddress.value=address1+"\n";
                    }
                    else
                    {
                    //document.frmConversion.txtOfficeAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value+address2+"\n";
                    }
                    else
                    {
                      //  document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value+city+"\n";
                    }
                    else
                    {
                       // document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value+"\n"+"";
                    }
                    if(district!="null")
                    {
                        document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value+district+"\n";
                    }
                    else
                    {
                      //  document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value+"\n"+"";
                    }
                    
                    if(pincode!=0)
                    {
                        if(pincode!="null")
                        {
                            document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value+pincode;
                        }
                    }
                    else
                    {
                     //document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value;
                    }
                        
                    
                    document.frmConversion.txtOfficeAddress.value=document.frmConversion.txtOfficeAddress.value;
                    
                    //document.frmConversion.txtAttachedOfficeID.focus();
                    OfficeLevel();
                    }
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess); 
                    document.frmConversion.txtOfficeName.value="";
                    document.frmConversion.txtOfficeType.value="";
                    document.frmConversion.txtOfficeAddress.value="";
                    document.frmConversion.txtAttachedOfficeID.value="";
                    document.frmConversion.txtAttachedOfficeName.value="";
                    document.frmConversion.txtAttachedOfficeAddress.value="";
                    document.frmConversion.txtDOC.value="";
                    document.frmConversion.cmbPrimaryID.selectedIndex=0;
                } 
          }
    }       
}

//Office Level Coding
function OfficeLevel()
{
    //alert("officelevel");
            var officeid=document.frmConversion.txtOffice_Id.value;
            //alert(officeid);
            startwaiting(document.frmConversion) ;
            url="../../../../../ServletOfficeLevel.con?OfficeId="+officeid;
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
               stopwaiting(document.frmConversion) ;
                var cmbAttachedOfficeLevel=document.getElementById("cmbAttachedOfficeLevel");
                //alert(cmbAttachedOfficeLevel.innerHTML);
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var option=document.createElement("OPTION");
                    cmbAttachedOfficeLevel.innerHTML="";
                    option.text="--Select OfficeLevel--";
                    try
                                {
                                    cmbAttachedOfficeLevel.add(option);
                            }catch(errorobject)
                            { 
                                     cmbAttachedOfficeLevel.add(option,null);
                            }
                    var value=response.getElementsByTagName("options");
                    //alert(value.length);
                    for(var j=0;j<value.length;j++)
                    {
                        var tmpoption=value.item(j);
                        var levelid=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var value4=tmpoption.getElementsByTagName("value")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                        option.text=value4;
                        option.value=levelid;
                              try
                                {
                                    cmbAttachedOfficeLevel.add(option);
                            }catch(errorobject)
                            { 
                                     cmbAttachedOfficeLevel.add(option,null);
                            }
                    }
                    checkif();
                }
                else
                {
                    //var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    //alert("Error Occured : \n" + mess);  
                }
                 
           }     
           
        }
        
       
}

//Check If coding

function checkif()
{
//alert('hai');

            var officeid=document.frmConversion.txtOffice_Id.value;
            startwaiting(document.frmConversion) ;
            url="../../../../../ServletCheckAttachment.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckResponse(req);                
            }
            req.send(null);

}   


function CheckResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {   
          stopwaiting(document.frmConversion) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    //alert("Office Id is Already Attached");
                    var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                    //var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    //alert(date);
                    //var primaryid=response.getElementsByTagName("primaryid")[0].firstChild.nodeValue;
                    document.frmConversion.txtExistOfficeId.value=id;
                    /*if(date!="null")
                    {
                          document.frmConversion.txtDOC.value=date;
                    }
                    if(date!="null")
                    {
                        
                        //alert("Office Id is Already Attached");
                        //document.frmConversion.txtDOC.value="";
                    }
                    if(primaryid!="null")
                    {
                    document.frmConversion.cmbPrimaryID.value=primaryid;
                    }*/
                    ExistingOffice(document.frmConversion.txtExistOfficeId.value);
                    //  loadOffice1(document.frmConversion.txtAttachedOfficeID.value,'attach');
                    
                   
                }
                else
                {
                    //document.frmClosure.submit.value="Submit";
                    //var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    //alert("Error Occured : \n" + mess);  
                }
                
           }     
        }
}


//Existing Office Details

function ExistingOffice(id)
{   
//alert("***");
    if(id=="" || id==null)
    {
        //alert("Enter or (Select An Office..Then Click choose..)");
        //document.frmConversion.txtAttachedOfficeID.focus();
    }
    else
    {
            startwaiting(document.frmConversion) ;
            var url="../../../../../ServletLoadOfficeDetails.con?ID="+id;            
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               ExistingOfficeDetails1(req);             
            }
            req.send(null);        
    }
}

function ExistingOfficeDetails1(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {   
                stopwaiting(document.frmConversion) ;
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0];
                    var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                    /*if(level=="Division" || level=="Sub-Division")
                        document.frmConversion.txtOfficeType.value=type;
                    else
                        document.frmConversion.txtOfficeType.value="Office level : " + level;*/
                        document.frmConversion.txtExistOfficeName.value="";
                        document.frmConversion.txtExistOfficeAddress.value="";
                    document.frmConversion.txtExistOfficeName.value=name.firstChild.nodeValue;
                    if(address1!="null")
                    {
                    document.frmConversion.txtExistOfficeAddress.value=address1+"\n";
                    }
                    else
                    {
                   // document.frmConversion.txtExistOfficeAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value+address2+"\n";
                    }
                    else
                    {
                       // document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value+city+"\n";
                    }
                    else
                    {
                       // document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value+"\n"+"";
                    }
                    if(district!="null")
                     {
                         document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value+district+"\n";
                     }
                     else
                     {
                        // document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value+"\n"+"";
                     }  
                     if(pincode!=0)
                     {
                        if(pincode!="null")
                        {
                            document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value+pincode;
                        }
                     }
                     else
                     {
                        //document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value;
                     }
                                     
                    document.frmConversion.txtExistOfficeAddress.value=document.frmConversion.txtExistOfficeAddress.value;
                    checkofficedetails();
                    //document.frmConversion.txtAttachedOfficeID.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);                    
                } 
          }
    }       
}

//Controlling Office Details Display Coding

function checkofficedetails()
{
//alert('hai');

            var officeid=document.frmConversion.txtOffice_Id.value;
            startwaiting(document.frmConversion) ;
            url="../../../../../ServletAttachmentDetails.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckOfficeResponse1(req);                
            }
            req.send(null);

}   


function CheckOfficeResponse1(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {   
          stopwaiting(document.frmConversion) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    //alert("Office Id is Already Attached");
                    var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    //alert(date);
                    var primaryid=response.getElementsByTagName("primaryid")[0].firstChild.nodeValue;
                    var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                    document.frmConversion.txtAttachedOfficeID.value=id;
                    if(date!="null")
                    {
                          document.frmConversion.txtDOC.value=date;
                    }
                    
                    if(primaryid!="null")
                    {
                    document.frmConversion.cmbPrimaryID.value=primaryid;
                    }
                    if(recordstatus=="FR")
                    {
                        alert('This Record is Freezed');
                        document.frmConversion.cmdSub.disabled=true;
                    }
                    else
                    {
                        document.frmConversion.cmdSub.disabled=false;
                    }
                    //ExistingOffice(document.frmConversion.txtExistOfficeId.value);
                    loadOffice1(document.frmConversion.txtAttachedOfficeID.value,'attach');
                    
                    
                }
                else
                {
                    //document.frmClosure.submit.value="Submit";
                    //var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    //alert("Error Occured : \n" + mess);  
                }
                
           }     
        }
}


//Attached Office

function loadOffice1(id)
{   
//alert("***");
    if(id=="" || id==null)
    {
        //alert("Enter or (Select An Office..Then Click choose..)");
        //document.frmConversion.txtAttachedOfficeID.focus();
    }
    else
    {
            startwaiting(document.frmConversion) ;
            var url="../../../../../ServletLoadOfficeDetails.con?ID="+id;            
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {                
               LoadOfficeDetails1(req);             
            }
            req.send(null);        
    }
}

function LoadOfficeDetails1(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {   
                stopwaiting(document.frmConversion) ;
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0];
                    var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;
                    var type=response.getElementsByTagName("type")[0].firstChild.nodeValue;
                    var add=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    document.frmConversion.txtAttachedOfficeName.value=name.firstChild.nodeValue;
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                    /*if(level=="Division" || level=="Sub-Division")
                        document.frmConversion.txtOfficeType.value=type;
                    else
                        document.frmConversion.txtOfficeType.value="Office level : " + level;*/
                    
                    if(address1!="null")
                    {
                    document.frmConversion.txtAttachedOfficeAddress.value=address1+"\n";
                    }
                    else
                    {
                   // document.frmConversion.txtAttachedOfficeAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value+address2+"\n";
                    }
                    else
                    {
                      //  document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value+city+"\n";
                    }
                    else
                    {
                     //   document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value+"\n"+"";
                    }
                    if(district!="null")
                    {
                        document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value+district+"\n";
                    }
                    else
                    {
                       // document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value+"\n"+"";
                    } 
                    if(pincode!=0)
                    {
                        if(pincode!="null")
                        {
                            document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value+pincode;
                        }
                    }
                    else
                    {
                        //document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value;
                    }
                                     
                    document.frmConversion.txtAttachedOfficeAddress.value=document.frmConversion.txtAttachedOfficeAddress.value;
                   
                    //document.frmConversion.txtAttachedOfficeID.focus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess); 
                    //document.frmConversion.txtAttachedOfficeID.value="";
                    document.frmConversion.txtAttachedOfficeName.value="";
                    document.frmConversion.txtAttachedOfficeAddress.value="";
                    document.frmConversion.txtDOC.value="";
                    document.frmConversion.cmbPrimaryID.selectedIndex=0;
                } 
          }
    }       
}



// null checking

function nullcheck()
{
//alert(document.frmConversion.txtAttachedOfficeID.value);
    if((document.frmConversion.txtOffice_Id.value=="") || (document.frmConversion.txtOffice_Id.value.length<=0))
    {
         alert("Please Enter Office id");
         document.frmConversion.txtOffice_Id.focus();
         return false;
    } 
     /*if((document.frmConversion.txtNewOfficeName.value=="") || (document.frmConversion.txtNewOfficeName.value.length<=0))
    {
         alert("Please enter new name Office");
         document.frmConversion.txtNewOfficeName.focus();
         return false;
    } 
    if((document.frmConversion.txtAttachedOfficeID.value=="") ||(document.frmConversion.txtAttachedOfficeID.value.length<=0))
    {
        alert("Please Enter AttachedOfficeId");
        document.frmConversion.txtAttachedOfficeID.focus();
        return false;
    }
     if((document.frmConversion.cmbPrimaryID.value=="" || document.frmConversion.cmbPrimaryID.value==0) || (document.frmConversion.cmbPrimaryID.value.length<=0))
    {
         alert("Please Select Primary Id");
         document.frmConversion.cmbPrimaryID.focus();
         return false;
    }  
     if((document.frmConversion.txtDOC.value=="" ) || (document.frmConversion.txtDOC.value.length<=0))
    {
         alert("Please Enter the Date");
         document.frmConversion.txtDOC.focus();
         return false;
    } */ 
    
    var close=confirm("Are You Sure.Your delete this Record");
    if(close==false)
    {
        document.frmConversion.txtOfficeName.value="";
        document.frmConversion.txtOfficeType.value="";
        document.frmConversion.txtOfficeAddress.value="";
        document.frmConversion.txtAttachedOfficeID.value="";
        document.frmConversion.txtAttachedOfficeName.value="";
        document.frmConversion.txtAttachedOfficeAddress.value="";
        document.frmConversion.txtDOC.value="";
        document.frmConversion.cmbPrimaryID.selectedIndex=0;
        document.frmConversion.txtExistOfficeName.value="";
        document.frmConversion.txtExistOfficeAddress.value="";
        document.frmConversion.txtExistOfficeId.value="";
        document.frmConversion.txtOffice_Id.value="";
        document.frmConversion.txtOffice_Id.focus();


       return false; 
    }
    else
    {
        return true;
    }
   // return true;
}


// date validation 

/* Date Validation Checking */
        function validate_date(formName, textName)
        {
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    //eval('document.' + formName + '.' + textName + '.focus()');
                    //alert(eval);
                    //eval('document.' + formName + '.' + textName + '.select()');
                    return false;
                }
                return true;
        }
        



//Function for Icon Office Selection
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
    document.frmConversion.txtOffice_Id.value=jobid;
    //document.HRE_EmployeeServiceDetails.txtDept_Id.value=deptid;
    loadOffice(jobid);
    //checkif();
    checkoffice();
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

//Attached OfficeId Checking
function checkAttachedoffice()
{


            var officeid=document.frmConversion.txtAttachedOfficeID.value;
            
            startwaiting(document.frmConversion) ;
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
          stopwaiting(document.frmConversion) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    alert("This Office is Closed, So did't Attached");
                    document.frmConversion.cmdSub.disabled=true;
                    return false;
                
                }
                else
                {
                document.frmConversion.cmdSub.disabled=false;
                    //return true;
                }
           }
        }
}

function checkoffice()
{

            //alert('checkoffice');
            var officeid=document.frmConversion.txtOffice_Id.value;
            startwaiting(document.frmConversion) ;
            url="../../../../../ServletCheckAttachmentOffice.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckOfficeResponse(req);                
            }
            req.send(null);

}   


function CheckOfficeResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {     
          stopwaiting(document.frmConversion) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    alert("This Office is Closed, So did't Attached");
                    document.frmConversion.cmdSub.disabled=true;
                    return false;
                
                }
                else
                {
                document.frmConversion.cmdSub.disabled=false;
                    //return true;
                }
           }
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

function officeCheck()
{

                if((document.frmConversion.txtOffice_Id.value=="") || (document.frmConversion.txtOffice_Id.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmConversion.txtOffice_Id.focus();
                    return false;
                    
                }


}

function checkofficestatus()
{
//alert('hai');

            var officeid=document.frmConversion.txtOffice_Id.value;
            startwaiting(document.frmConversion) ;
           // alert(officeid);
            url="../../../../../ServletCheckStatusAttachment.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckOfficeStatusResponse(req);                
            }
            req.send(null);

}   


function CheckOfficeStatusResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {     
                stopwaiting(document.frmConversion) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                if(flag=="success")
                {
                    var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                    if(recordstatus=="FR")
                    {
                    alert("This Record is Freezed");
                    document.frmConversion.cmdSub.disabled=true;
                    }
                    
                
                }
                else
                {
                document.frmConversion.cmdSub.disabled=false;
                    //return true;
                }
           }
        }
}







function funclear()
{

    document.frmConversion.cmdSub.disabled=false;
    
}