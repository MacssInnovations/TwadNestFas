
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

function popupWindow()
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
}
//validations

function checkLevel()
{
                                 
    var level=document.frmOffice.cmbLevelId.options[document.frmOffice.cmbLevelId.selectedIndex].text;
    //alert(level);
    if(level=="Head Office" || level=="Region")// || level=="Lab" || level=="Circle")
    {      
      //alert(level);
      disableControllingOffice();
      document.frmOffice.txtContrllingOfficeID.value="Default"; 
      document.frmOffice.txtHContrllingOfficeID.value="Default";
      document.frmOffice.cmbPrimaryID.disabled=true;
      if(level=="Head Office")
      {
        document.frmOffice.txtContrllingOfficeID.value="Head Office"; 
        document.frmOffice.txtHContrllingOfficeID.value="Head Office";        
      }
    } 
    else if(level=="Lab" || level=="Circle")
    {
        enableControllingOffice();      
        document.frmOffice.cmbPrimaryID.disabled=true;
    }
    else if(level != "----Select OfficeLevel----")
    {        
        enableControllingOffice();
        document.frmOffice.txtContrllingOfficeID.value="";       
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
    var url="../../../../../ServletLoadDefaultCadre.con?level="+level;  
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
                var response=req.responseXML.getElementsByTagName("response")[0];                
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {                    
                    var level=response.getElementsByTagName("level")[0].firstChild.nodeValue;                    
                    document.frmOffice.cmbHeadCode.value=level;                    
                }                
          }
    }       
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
                      

            /* NullCheck Validation */
            function nullcheck()
            {
               try
               {
                  if((document.frmOffice.txtOffName.value=="") || (document.frmOffice.txtOffName.value.length<=0))
                  {
                      alert("Please Enter Office Name");
                      document.frmOffice.txtOffName.focus();
                      return false;
                  }  
                  
                  if((document.frmOffice.txtShortName.value=="") || (document.frmOffice.txtShortName.value.length<=0))
                  {
                      alert("Please Enter Office Short Name");
                      document.frmOfficeOffice.txtShortName.focus();
                      return false;
                  }       
                             
                  if((document.frmOffice.cmbLevelId.value=="0") || (document.frmOffice.cmbLevelId.selectedIndex<=0))
                  {
                      alert("Please Select a Office Level");
                      document.frmOffice.cmbLevelId.focus();
                      return false;
                  }
                  
                  if((document.frmOffice.cmbHeadCode.value=="0") || (document.frmOffice.cmbHeadCode.selectedIndex<=0))
                  {
                      alert("Please Select a Head Cadre");
                      document.frmOffice.cmbHeadCode.focus();
                      return false;
                  } 
                  
                  if((document.frmOffice.txtAdd3.value=="") || (document.frmOffice.txtAdd3.value.length<=0))
                  {
                      alert("Please Enter Office City / Town Name");
                      MakeDivVisible('addresses');
                      document.frmOffice.txtAdd3.focus();
                      return false;
                  }                 
              
                  if((document.frmOffice.cmbDistrict.value=="0") || (document.frmOffice.cmbDistrict.selectedIndex<=0))
                  {
                      alert("Please Select a District");
                      MakeDivVisible('addresses');
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
                        alert("Please Select a Primary Work Nature");
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
            
            
            