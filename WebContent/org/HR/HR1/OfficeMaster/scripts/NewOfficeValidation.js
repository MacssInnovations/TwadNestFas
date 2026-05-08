
//pop up division
function ShowOrHideDiv(divID)
          {           
            var div=document.getElementById(divID)
            if(div)
            {           
              if(div.style.display=='block')
              {
                div.style.display='none';
              }
              else
              {
                div.style.display='block';
              }
            }
          }

function MakeDivVisible(divID)
          {           
            var div=document.getElementById(divID)
            if(div)
            {             
                try
                {
                    div.style.display='block';              
                }catch(e){}
            }
          }

/*function popupWindow()
{
      if((document.frmOffice.cmbLevelId.value=="0") || (document.frmOffice.cmbLevelId.selectedIndex<=0))
      {
            alert("Please Select a Office Level");
            document.frmOffice.cmbLevelId.focus();            
      }
      else
      {
            var level=document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].text;
            var url="SelectOfficeId.jsp?level=" + level;
            alert(url);
            var mw= window.open(url,"seloffice","status=1,height=170"); 
            mw.moveTo(50,50);    
      }
}*/
//validations

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
        document.frmOffice.txtconOfficeName.value="";
        document.frmOffice.txtconOfficeAddress.value="";
        //document.frmOffice.txtconOfficeAddress1.value="";
        document.frmOffice.cmbOfficeType.selectedIndex=0;
        document.frmOffice.cmbSelectOffice.selectedIndex=0;
        
        document.frmOffice.cmbPrimaryID.disabled=true;
    }
    else if(level != "----Select OfficeLevel----")
    {        
        enableControllingOffice();
        document.frmOffice.txtContrllingOfficeID.value=""; 
        document.frmOffice.txtconOfficeName.value="";
        document.frmOffice.txtconOfficeAddress.value="";
        //document.frmOffice.txtconOfficeAddress1.value="";
        document.frmOffice.cmbPrimaryID.disabled=false;
    }
    else
    {     
        // nothing        
    }
    setDefaultCadre(document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].value);
}

function disableControllingOffice()
{
    document.frmOffice.txtContrllingOfficeID.disabled=true;
    document.frmOffice.cmbControllingLevel.disabled=true;
    //document.frmOffice.cmbOfficeType.disabled=true;
    //document.frmOffice.cmbSelectOffice.disabled=true;
    document.frmOffice.txtContrllingOfficeID.value=""; 
    document.frmOffice.txtHContrllingOfficeID.value="";
}

function enableControllingOffice()
{
    document.frmOffice.txtContrllingOfficeID.disabled=false;
    document.frmOffice.cmbControllingLevel.disabled=false;
    document.frmOffice.txtContrllingOfficeID.value=""; 
    document.frmOffice.txtHContrllingOfficeID.value="";
}

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
                     
                     var id=response.getElementsByTagName("id")[0].firstChild.nodeValue;                    
                     var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                     
                              document.frmOffice.cmbHeadCode.value=id;
                } 
                else
                {
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
}
/*function controlAdditionalWorkNature(level)
{
    if(level=="Division" || level=="Sub-Division" || level=="Section")
    {
        frmOffice.cmbSecondaryID.disabled=false;
    }
    else
    {
        frmOffice.cmbSecondaryID.disabled=true;
    }
}*/



function isInteger(param,e)
{     
       var nav4 = window.Event ? true : false;
       if (nav4)    // Navigator 4.0x
            var whichCode = e.which
       else         // Internet Explorer 4.0x
            var whichCode = e.keyCode
      if(whichCode>=48 && whichCode<=57)
      {
          return true;
      }
      var str=param.value;
      param.value=str.substring(0,str.length-1);
      return false;              
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
     
function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
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
        
        
        
         /* Email Validation Checking */
            function echeck(str) 
            {
                var at="@"
                var dot="."
                var lat=str.indexOf(at)
                var lstr=str.length
                var ldot=str.indexOf(dot)
                   if (str.indexOf(at)==-1)
                   {
                        alert("Invalid E-mail ID")
                       return false
                   }
                   if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr)
                   {
                         alert("Invalid E-mail ID")
                      return false
                   }
                   if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr)
                   {
                         alert("Invalid E-mail ID")
                     return false
                   }
                   if (str.indexOf(at,(lat+1))!=-1)
                   {
                         alert("Invalid E-mail ID")
                     return false
                   }
                   if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot)
                   {
                          alert("Invalid E-mail ID")
                       return false
                   }
                   if (str.indexOf(dot,(lat+2))==-1)
                   {
                          alert("Invalid E-mail ID")
                       return false
                   }
                   if (str.indexOf(" ")!=-1)
                   {
                          alert("Invalid E-mail ID")
                       return false
                   }
                   return true
            }
            
            function ValidateForm()
            {
                  var emailID=document.frmOffice.txtEMail;
                      
                   if ((emailID.value==null)||(emailID.value=="")){
                          alert("Please Enter your Email ID")
                          emailID.focus()
                       return false
                   }
                   if (echeck(emailID.value)==false){
                           emailID.value=""
                            emailID.focus()
                       return false
                   }
                   return true
             }             

            /* NullCheck Validation */
            function nullcheck()
            {
               try
               {
                  if((document.frmOffice.txtOffName.value=="") || (document.frmOffice.txtOffName.value.length<=0))
                  {
                      alert("Please Enter Office Name in Office Details");
                      //document.frmOffice.txtOffName.focus();
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
                  /*if((document.frmOffice.txtDate_Effective_From.value=="") || (document.frmOffice.txtDate_Effective_From.value.length<=0))
                  {
                      alert("Please Enter Effective Date");
                      document.frmOffice.txtDate_Effective_From.focus();
                      return false;
                  }*/   
                  
                  /*if((document.frmOffice.txtAdd1.value=="") || (document.frmOffice.txtAdd1.value.length<=0))
                  {
                      alert("Please Enter Address in Contact Details");
                      //MakeDivVisible('addresses');
                      document.frmOffice.txtAdd1.focus();
                      return false;
                  }*/                 
              
                  if((document.frmOffice.cmbDistrict.value=="0") || (document.frmOffice.cmbDistrict.selectedIndex<=0))
                  {
                      alert("Please Select a District in Contact Details");
                      //MakeDivVisible('addresses');
                      document.frmOffice.cmbDistrict.focus();
                      return false;
                  }  
                  
                  var level=document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].text;
                //alert(level);
                if(level=="Head Office" || level=="Region")// || level=="Lab" || level=="Circle")
                {      
                    // no controlling office and primary work nature
                } 
                else if(level=="Lab" || level=="Circle")
                {
                    // controlling office should be given
                    // but no primary work nature
                    if((document.frmOffice.txtHContrllingOfficeID.value=="")||(document.frmOffice.txtHContrllingOfficeID.value.length<=0))
                    {
                        alert("Please Enter Controlling Office Id");
                        document.frmOffice.txtContrllingOfficeID.value="";
                        document.frmOffice.txtContrllingOfficeID.focus();
                        return false;
                    }
                }
                else if(level != "----Select OfficeLevel----")
                {        
                    // both the controlling office and work nature shhould be given
                    if((document.frmOffice.txtHContrllingOfficeID.value=="")||(document.frmOffice.txtHContrllingOfficeID.value.length<=0))
                    {
                        alert("Please Enter Controlling Office Id");
                        document.frmOffice.txtContrllingOfficeID.value="";
                        document.frmOffice.txtContrllingOfficeID.focus();
                        return false;
                    }
                    if((document.frmOffice.cmbPrimaryID.value=="0") || (document.frmOffice.cmbPrimaryID.value.length<=0))
                    {
                        alert("Please Select a Primary Work Nature in Office Details");
                        document.frmOffice.cmbPrimaryID.focus();
                        return false;
                    }
                }
                else
                {     
                    // nothing        
                }
                  
                /*  var level=document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].text;
                  if(level!="Head Office")
                  {
                      if(level!="Region")
                      {
                          if((document.frmOffice.txtHContrllingOfficeID.value=="")||(document.frmOffice.txtHContrllingOfficeID.value.length<=0))
                          {
                              alert("Please Enter Controlling Office Id");
                              document.frmOffice.txtContrllingOfficeID.value="";
                              return false;
                          }
                          if((document.frmOffice.cmbPrimaryID.value=="0") || (document.frmOffice.cmbPrimaryID.value.length<=0))
                          {
                              alert("Please Select a Primary Work Nature");
                              document.frmOffice.cmbPrimaryID.focus();
                              return false;
                          }
                      } 
                      else
                      {
                      
                      }
                  }
                  else
                  {}*/
                
                 return true; 
              }
              catch(e)
              {
                return false;
              }
            }
            
            
     function officename()
     {
        var conofficeid=document.frmOffice.txtContrllingOfficeID.value
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
                               // document.frmOffice.txtconOfficeAddress.value="";
                            }
                            if(address2!="null")
                            {
                                document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+address2+"\n";
                            }
                            else
                            {
                                //document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(city!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+city+"\n";
                            }
                            else
                            {
                              //  document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            
                            if(district!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+district+"\n";
                            }
                            else
                            {
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
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
                            alert("Valid Information not found");
                            document.frmOffice.txtconOfficeName.value="";
                            document.frmOffice.txtconOfficeAddress.value="";
                        }
                
                }
           }     
     
     }
     
function pinlength()
{

    var pincode=document.frmOffice.txtPinCode.value;
    pincode=pincode.length;
    //alert(pincode);
    if(pincode<6)
    {
        alert("Please Enter Correct Pincode");
        document.frmOffice.txtPinCode.focus();
        return false;
        
    }
    return true;


}

function fun1(levelvalue)
{
    //alert(levelvalue);
            var url="../../../../../ServletOfficeName1.con?ConOfficeId="+levelvalue;  
            startwaiting(document.frmOffice);
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
                        stopwaiting(document.frmOffice);
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
                               // document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(address2!="null")
                            {
                                document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+address2+"\n";
                            }
                            else
                            {
                              //  document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
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
                            alert("No information found");
                        }
                
                }
           }     
     
     }


 function officename1()
     {
        var conofficeid=document.frmOffice.txtContrllingOfficeID.value
            var url="../../../../../ServletOfficeName.con?ConOfficeId="+conofficeid;  
            startwaiting(document.frmOffice);
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {     
                OfficeNameResponse1(req);
            }
            req.send(null);
     }
     
     function OfficeNameResponse1(req)
     {
            if(req.readyState==4)
            {
                  if(req.status==200)
                  {      stopwaiting(document.frmOffice);          
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
                                //document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
                            }
                            if(address2!="null")
                            {
                                document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+address2+"\n";
                                
                            }
                            else
                            {
                                //document.frmOffice.txtconOfficeAddress.value=document.frmOffice.txtconOfficeAddress.value+"\n"+"";
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
                            if((pincode!=0) )
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
                            alert("Valid Information not found");
                        }
                
                }
           }     
     
     }
     
     
/*function popup()
{

    //alert('hai');
    var url="NewPopup.jsp";
    window.open(url,"seloffice","height=300,resizable=yes");

}*/




//this function for Edit the values for officeid(NewpromptOfficeId.jsp)

/*function officeprompt()
     {
        var conofficeid=document.prompt.txtOffice_Id.value
            var url="../../../../../ServletOfficeName.con?ConOfficeId="+conofficeid;  
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {     
                OfficePromptResponse(req);
            }
            req.send(null);
     }*/
     
     /*function OfficePromptResponse(req)
     {
            if(req.readyState==4)
            {
                  if(req.status==200)
                  {                
                        var response=req.responseXML.getElementsByTagName("response")[0];                
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                        if(flag=="success")
                        {                    
                            var name=response.getElementsByTagName("name")[0].firstChild.nodeValue;
                            //var address=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                            //var city=response.getElementsByTagName("city")[0].firstChild.nodeValue;
                            document.prompt.txtOffice_Name.value=name;*/
                            //document.frmOffice.txtconOfficeAddress.value="";
                            //document.frmOffice.txtconOfficeAddress1.value="";
                            /*if(address!="null")
                            {
                            document.frmOffice.txtconOfficeAddress.value=address;
                            }
                            if(city!="null")
                            {
                            document.frmOffice.txtconOfficeAddress1.value=city;
                            }*/
                        /*}
                        else
                        {
                            alert("Valid Information not found");
                        }
                
                }
           }     
     
     }*/
     
     



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
if((document.frmOffice.txtAddEMail.value.length!=0) && !( document.frmOffice.txtAddEMail.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAddEMail.value.length==1  ))
    {
        var x = document.frmOffice.txtAddEMail.value;
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test(x))
	 {alert('Enter correct email address');

        document.frmOffice.txtAddEMail.value="";
        document.frmOffice.txtAddEMail.focus();
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


function officeCheck()
{

                if((document.frmOffice.txtOffName.value=="") || (document.frmOffice.txtOffName.value.length<=0))
                {
                    alert("Please Enter Office Name ");
                    document.frmOffice.txtOffName.focus();
                    return false;
                    
                }


}

function officeCheck1()
{
    
        if((document.frmOffice.txtOffName.value=="") || (document.frmOffice.txtOffName.value.length<=0))
                {
                    alert("Please Enter Office Name in General Details ");
                    //document.frmOffice.txtOffName.focus();
                    return false;
                    
                }

}


function checkAttachedoffice()
{
//alert('hai');

            var officeid=document.frmOffice.txtContrllingOfficeID.value;
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
                    alert("This Office is Closed, So did't Control to Other Office");
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

function clear1()
{
    document.frmOffice.cmdSub.disabled=false;
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
