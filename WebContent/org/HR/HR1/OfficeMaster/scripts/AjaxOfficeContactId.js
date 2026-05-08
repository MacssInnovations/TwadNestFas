// code for creating XMLHTTPREQUEST object
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

//Integer Checking
function isInteger(param,e)
{     
       var nav4 = window.Event ? true : false;
       //alert(e.keyCode);
       if (nav4)    // Navigator 4.0x
            var whichCode = e.which
       else         // Internet Explorer 4.0x
            var whichCode = e.keyCode
      if((whichCode>=48 && whichCode<=57 )||(whichCode==189))
      {
          return true;
      }
      var str=param.value;
      param.value=str.substring(0,str.length-1);
      return false;              
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
                  var emailID=document.frmOffice.txtEmailId;
                   //alert(emailID);
                   if ((emailID.value==null)||(emailID.value=="")){
                          alert("Please Enter Correct Email ID")
                          //alert("hai");
                          emailID.focus();
                       return false;
                   }
                   if (echeck(emailID.value)==false){
                           emailID.value="";
                           //alert("echeck");
                            emailID.focus();
                       return false;
                   }
                   return true
        } 
        function ValidateForm1()
            {
                  var emailID=document.frmOffice.txtAddEmailId;
                   
                   if ((emailID.value==null)||(emailID.value=="")){
                          alert("Please Enter Correct Email ID")
                          emailID.focus();
                       return false;
                   }
                   if (echeck(emailID.value)==false){
                           emailID.value="";
                            emailID.focus();
                       return false;
                   }
                   return true
        } 
            


/* NullCheck Validation */
            function nullcheck()
            {
                  if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                  }
                  if((document.frmOffice.txtOffice_Name.value=="") || (document.frmOffice.txtOffice_Name.value.length<=0))
                  {
                      alert("Please Enter Office Name");
                      document.frmOffice.txtOffice_Namee.focus();
                      return false;
                  }  
                  
                  if((document.frmOffice.txtOffice_Address1.value=="") || (document.frmOffice.txtOffice_Address1.value.length<=0))
                  {
                      alert("Please Enter Office Address");
                      document.frmOffice.txtOffice_Address1.focus();
                      return false;
                  }
                  if((document.frmOffice.cmbDistrict.value=="") || (document.frmOffice.cmbDistrict.selectedIndex<=0) || (document.frmOffice.cmbDistrict.value==0))
                  {
                      alert("Please Select a District");
                      document.frmOffice.cmbDistrict.focus();
                      return false;
                  }
                  /*if((document.frmOfficeContact.txtOffice_Address2.value=="") || (document.frmOfficeContact.txtOffice_Address2.value.length<=0))
                  {
                      alert("Please Enter Office Address2");
                      document.frmOfficeContact.txtOffice_Address2.focus();
                      return false;
                  }*/
                  /*if((document.frmOffice.txtOffice_City.value=="") || (document.frmOffice.txtOffice_City.value.length<=0))
                  {
                      alert("Please Enter City");
                      document.frmOffice.txtOffice_City.focus();
                      return false;
                  }*/
                  
                  
                  /*if((document.frmOffice.txtPhone_No.value=="") || (document.frmOffice.txtPhone_No.value.length<=0))
                  {
                      alert("Please Enter Phone No");
                      document.frmOffice.txtPhone_No.focus();
                      return false;
                  }
                   if((document.frmOffice.txtEmailId.value=="")|| (document.frmOffice.txtEmailId.value.length<=0))
                  {
                      alert("Please Enter Email Id");
                      document.frmOffice.txtEmailId.focus();
                      return false;
                  }
                  
                  if((document.frmOffice.txtFax_No.value=="") || (document.frmOffice.txtFax_No.value.length<=0))
                  {
                      alert("Please Enter FAX Number");
                      document.frmOfficet.txtFax_No.focus();
                      return false;
                  }*/
                  
                 
                 /* if((document.frmOffice.txtPinCode.value=="") || (document.frmOffice.txtPinCode.value.length<=0))
                  {
                      alert("Please Enter PinCode");
                      document.frmOffice.txtPinCode.focus();
                      return false;
                  }
                  
                  if((frmOffice.txtSTDCode.value=="") || (frmOffice.txtSTDCode.value.length<=0))
                  {
                      alert("Please Enter STD Code");
                      
                      document.frmOffice.txtSTDCode.focus();
                      return false;
                  }
                  if((document.frmOffice.txtShortName.value=="") || (document.frmOfficet.txtShortName.value.length<=0))
                  {
                      alert("Please Enter Office Short Name");
                      document.frmOffice.txtShortName.focus();
                      return false;
                  }
                  
                  if((document.frmOfficeContact.cmbHeadCode.value=="") || (document.frmOfficeContacte.cmbHeadCode.selectedIndex<=0))
                  {
                      alert("Please Select a Head Cadre");
                      document.frmOfficeContact.cmbHeadCode.focus();
                      return false;
                  }*/
                  
                  //var phone=document.frmOfficeContact.txtPhone_No.value;
                  //alert(phone);
                  
                  return true;
                }

function callServer1(command,param)
{

    var url="";
    var Office_Id="";
    if(command=="Load")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        //alert(Office_Id);
        startwaiting(document.frmOffice) ;
        url="../../../../../ServletOfficeFilter.con?command=Load&OfficeId="+Office_Id;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
        req.send(null);
    }
    
    function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert(req.responseText);
                      stopwaiting(document.frmOffice) ;
                      var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOfficeId");
                      
                      
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("Invalid Office Id");
                         document.frmOffice.txtOffice_Address1.value="";
                         document.frmOffice.txtOffice_Address2.value="";
                         document.frmOffice.txtOffice_City.value="";
                         document.frmOffice.txtPhone_No.value="";
                         document.frmOffice.txtAdd_Phone_No.value="";
                         
                         document.frmOffice.txtFax_No.value="";
                         document.frmOffice.txtAdd_Fax_No.value="";
                         document.frmOffice.txtEmailId.value="";
                         document.frmOffice.txtAdd_EmailId.value="";
                         document.frmOffice.txtStd_Code.value="";
                         document.frmOffice.txtPin_Code.value="";
                         document.frmOffice.txtOffice_Name.value="";
                         document.frmOffice.cmbDistrict.selectedIndex=0;
                         
                         document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          //alert(value);
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                              var district=tmpoption.getElementsByTagName("District")[0].firstChild.nodeValue;
                              var phoneno=tmpoption.getElementsByTagName("PhoneNo")[0].firstChild.nodeValue;
                              var addphone=tmpoption.getElementsByTagName("AddPhone")[0].firstChild.nodeValue;
                              var faxno=tmpoption.getElementsByTagName("FaxNo")[0].firstChild.nodeValue;
                              var addfaxno=tmpoption.getElementsByTagName("AddFaxNo")[0].firstChild.nodeValue;
                              var emailid=tmpoption.getElementsByTagName("EmailId")[0].firstChild.nodeValue;
                              var addemail=tmpoption.getElementsByTagName("AddEmail")[0].firstChild.nodeValue;
                              var stdcode=tmpoption.getElementsByTagName("StdCode")[0].firstChild.nodeValue;
                              var pincode=tmpoption.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                              
                              document.frmOffice.txtOffice_Address1.value="";
                             document.frmOffice.txtOffice_Address2.value="";
                             document.frmOffice.txtOffice_City.value="";
                             document.frmOffice.txtPhone_No.value="";
                             document.frmOffice.txtAdd_Phone_No.value="";
                             
                             document.frmOffice.txtFax_No.value="";
                             document.frmOffice.txtAdd_Fax_No.value="";
                             document.frmOffice.txtEmailId.value="";
                             document.frmOffice.txtAdd_EmailId.value="";
                             document.frmOffice.txtStd_Code.value="";
                             document.frmOffice.txtPin_Code.value="";
                             document.frmOffice.txtOffice_Name.value="";
                             document.frmOffice.cmbDistrict.selectedIndex=0;
                              document.frmOffice.txtOffice_Name.value="";
                              
                              document.frmOffice.txtOffice_Name.value=name;
                              if(district!=0)
                              {
                              document.frmOffice.cmbDistrict.value=district;
                              }
                              if(officeAddress1!="null")
                              {
                              document.frmOffice.txtOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.frmOffice.txtOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                   document.frmOffice.txtOffice_City.value=officeAddress3;
                             }
                             if(phoneno!="null" && phoneno!=0)
                              {
                                   document.frmOffice.txtPhone_No.value=phoneno;
                             }
                             if(addphone!="null" && addphone!=0)
                              {
                                   document.frmOffice.txtAdd_Phone_No.value=addphone;
                             }
                             if(faxno!="null" && faxno!=0)
                              {
                                   document.frmOffice.txtFax_No.value=faxno;
                             }
                             if(addfaxno!="null" && addfaxno!=0)
                              {
                                   document.frmOffice.txtAdd_Fax_No.value=addfaxno;
                             }
                             if(emailid!="null")
                              {
                                   document.frmOffice.txtEmailId.value=emailid;
                             }
                             if(addemail!="null")
                              {
                                   document.frmOffice.txtAdd_EmailId.value=addemail;
                             }
                             if(stdcode!="null" && stdcode!=0)
                              {
                                   document.frmOffice.txtStd_Code.value=stdcode;
                             }
                             if(pincode!="null" && pincode!=0)
                             {
                                document.frmOffice.txtPin_Code.value=pincode;
                             }
                             
                          }
                          
                      }   
            }
            
            
        }
    }
}


function pinlength()
{

    var pincode=document.frmOffice.txtPin_Code.value;
    pincode=pincode.length;
    //alert(pincode);
    if(pincode<6)
    {
        alert("Please Enter Correct Pincode");
        document.frmOffice.txtPin_Code.focus();
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
    document.frmOffice.txtOffice_Id.value=jobid;
    //document.HRE_EmployeeServiceDetails.txtDept_Id.value=deptid;
    callServer1('Load','null');
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

//This is for given only numbers checking
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


//This coding for without given officeid
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


function checkpincode()
{

         if(isNaN(document.frmOffice.txtPin_Code.value))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtPin_Code.value="";
            document.frmOffice.txtPin_Code.focus();
            return false;
    }
     if((document.frmOffice.txtPin_Code.value.length!=0) && !( document.frmOffice.txtPin_Code.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtPin_Code.value.length==1  ))
    {
           if((document.frmOffice.txtPin_Code.value.length!=6 || document.frmOffice.txtPin_Code.value==0) && document.frmOffice.txtPin_Code.value.length!=0 )
            {
                    alert("Pincode Start should be 6. Zero not allowed");
                    document.frmOffice.txtPin_Code.focus();
                    return false;
            }
    }
    return true;

}

function checkstd()
{
    if(isNaN(document.frmOffice.txtStd_Code.value) && !( document.frmOffice.txtStd_Code.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtStd_Code.value.length==1  ))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtStd_Code.value="";
            document.frmOffice.txtStd_Code.focus();
            return false;
    }
     if((document.frmOffice.txtStd_Code.value.length!=0) && !( document.frmOffice.txtStd_Code.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtStd_Code.value.length==1  ))
    {
        if((document.frmOffice.txtStd_Code.value.length <2 || document.frmOffice.txtStd_Code.value==0)  && document.frmOffice.txtStd_Code.value.length !=0)
        {
                    alert("STD Code Length should be between 2 and 5.  Zero not allowed");
                    document.frmOffice.txtStd_Code.focus();
                    return false;
        }
    }
    return true;

}

function checkphone()
{
    if(isNaN(document.frmOffice.txtPhone_No.value))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtPhone_No.value="";
            document.frmOffice.txtPhone_No.focus();
            return false;
    }
     if((document.frmOffice.txtPhone_No.value.length!=0) && !( document.frmOffice.txtPhone_No.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtPhone_No.value.length==1  ))
    {
        if(document.frmOffice.txtPhone_No.value.length <6  || document.frmOffice.txtPhone_No.value==0 )
        {
                    alert("Phone No. Length atleast 6.  Zero not allowed");
                    document.frmOffice.txtPhone_No.focus();
                    return false;
        }
    }
    return true;
}


function checkaddphone()
{
   
     if((document.frmOffice.txtAdd_Phone_No.value.length!=0) && !( document.frmOffice.txtAdd_Phone_No.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAdd_Phone_No.value.length==1  ))
    {
        var no=document.frmOffice.txtAdd_Phone_No.value;
        var s=no.split(',');
        
        for(i=0;i<s.length;i++)
        {
        
            if(s[i].indexOf('-')!=-1)
            {
                
                var t=s[i].split('-');
                if(t[0].length<2 || t[0].length >5 || t[1].length<6 || t[0].value==0 || t[1].value==0)
                {
                    alert(s[i]+ " not a valid phone No.\n Phone No. Length atleast 6\nSTD Code Length should be between 2 and 5.\n  Zero not allowed.");
                    document.frmOffice.txtAdd_Phone_No.focus();
                    return false;
                }
                
            }
            else if(s[i].length <6 || s[i].value==0)
            {
                    alert(s[i]+ " not a valid phone No.\n Phone No. Length atleast 6\nSTD Code Length should be between 2 and 5.\n  Zero not allowed.");
                    document.frmOffice.txtAdd_Phone_No.focus();
                    return false;
            }
                    
        
        }
    }
    return true;
}

function checkfax()
{
    if(isNaN(document.frmOffice.txtFax_No.value))
    {
            alert("Enter Numeric value");
            document.frmOffice.txtFax_No.value="";
            document.frmOffice.txtFax_No.focus();
            return false;
    }
     if((document.frmOffice.txtFax_No.value.length!=0) && !( document.frmOffice.txtFax_No.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtFax_No.value.length==1  ))
    {
        if(document.frmOffice.txtFax_No.value.length <6  )
        {
                    alert("Phone No. Length atleast 6");
                    document.frmOffice.txtFax_No.focus();
                    return false;
        }
    }
    return true;
}

function checkfaxno()
{
   
     if((document.frmOffice.txtAdd_Fax_No.value.length!=0) && !( document.frmOffice.txtAdd_Fax_No.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAdd_Fax_No.value.length==1  ))
    {
        var no=document.frmOffice.txtAdd_Fax_No.value;
        var s=no.split(',');
        
        for(i=0;i<s.length;i++)
        {
        
            if(s[i].indexOf('-')!=-1)
            {
                
                var t=s[i].split('-');
                if(t[0].length<2 || t[0].length >5 || t[1].length<6 || t[0].value==0 || t[1].value==0)
                {
                    alert(s[i]+ " not a valid fax no.\n Fax No. Length atleast 6 Length. Zero not allowed.");
                    document.frmOffice.txtAdd_Fax_No.focus();
                    return false;
                }
                
            }
            else if(s[i].length <6 || s[i].value==0)
            {
                    alert(s[i]+ " not a valid Fax No.\n Fax No. Length atleast 6 Length.Zero not allowed.");
                    document.frmOffice.txtAdd_Fax_No.focus();
                    return false;
            }
                    
        
        }
    }
    return true;
}

function checkemail()
{
if((document.frmOffice.txtAdd_EmailId.value.length!=0) && !( document.frmOffice.txtAdd_EmailId.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAdd_EmailId.value.length==1  ))
    {
        var x = document.frmOffice.txtAdd_EmailId.value;
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test(x))
	 {alert('Enter correct email address');

        document.frmOffice.txtAdd_EmailId.value="";
        document.frmOffice.txtAdd_EmailId.focus();
        return false;
        }
    }
    return true;
}


function addcheckemail()
{
if((document.frmOffice.txtAdd_EmailId.value.length!=0) && !( document.frmOffice.txtAdd_EmailId.value.charAt(0)==String.fromCharCode(160) && document.frmOffice.txtAdd_EmailId.value.length==1  ))
    {
    
        var x = document.frmOffice.txtAdd_EmailId.value;
       
        if((x.lastIndexOf(',')+1 == x.length) && x.length!=0)
        {
            x=x.substring(0,x.length-1);
            document.frmOffice.txtAdd_EmailId.value=x;
        }
        var a=x.split(',');
	var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	for(i=0;i<a.length;i++)
        {
            if (!filter.test(a[i]))
             {
             alert('Enter correct email address');
             document.frmOffice.txtAdd_EmailId.value="";
             document.frmOffice.txtAdd_EmailId.focus();
            return false;
            }
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



function checkoffice()
{
//alert('hai');

            var officeid=document.frmOffice.txtOffice_Id.value;
            startwaiting(document.frmOffice) ;
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
          stopwaiting(document.frmOffice) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    document.frmOffice.txtOffice_Name.value="";
                    document.frmOffice.txtOffice_Address1.value="";
                         document.frmOffice.txtOffice_Address2.value="";
                         document.frmOffice.txtOffice_City.value="";
                         document.frmOffice.txtPhone_No.value="";
                         document.frmOffice.txtAdd_Phone_No.value="";
                         
                         document.frmOffice.txtFax_No.value="";
                         document.frmOffice.txtAdd_Fax_No.value="";
                         document.frmOffice.txtEmailId.value="";
                         document.frmOffice.txtAdd_EmailId.value="";
                         document.frmOffice.txtStd_Code.value="";
                         document.frmOffice.txtPin_Code.value="";
                         document.frmOffice.txtOffice_Name.value="";
                         document.frmOffice.cmbDistrict.selectedIndex=0;
                    alert("This Office is Closed, So did't Updated");
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

function funclear()
{
    document.frmOffice.cmdSub.disabled=false;

}