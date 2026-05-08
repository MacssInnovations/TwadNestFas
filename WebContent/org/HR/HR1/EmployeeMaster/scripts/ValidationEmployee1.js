
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
 
 // function to call servlet 
 function callServer()
 {     
 //alert("test");
          var url="";
            
         
              strSysId=document.frmEmployee.Native_District.value;
              //strSysDesc=document.frmMajorSystems.txtSysDesc.value;
              //strSysShortDesc=document.frmMajorSystems.txtSysShortDesc.value;
                    
              url="/major/ServletTaluk.con?txtSysId="+strSysId ;
              req.open("GET",url,true);        
              req.onreadystatechange=processResponse;
              if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
                  
    }

// code for processing the xml returned by servlet  
   
    function processResponse()
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {             
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var flagtag=baseResponse.getElementsByTagName("flag")[0];
              var flag=flagtag.firstChild.nodeValue;
              var Native_Taluk=document.getElementById("Native_Taluk");
              if(flag=="success")
              {
                  
                 //alert(baseResponse.getElementsByTagName("id")[0].firstChild.nodeValue);
                 //document.frmEmployee.Native_Taluk.value=
                 var value=baseResponse.getElementsByTagName("id");
                 var value1=baseResponse.getElementsByTagName("value");
                 //alert(value.length);
                 Native_Taluk.innerHTML="";
                 //var option=document.createElement("OPTION");
                 for(var i=0;i<value.length;i++)
                 {
                      var combovalue=value.item(i).firstChild.nodeValue;
                      var combovalue1=value1.item(i).firstChild.nodeValue;
                      var option=document.createElement("OPTION");
                      option.text=combovalue;
                      option.value=combovalue1;
                      try
                      {
                        Native_Taluk.add(option);
                      }catch(errorobject)
                      { 
                        Native_Taluk.add(option,null);
                      }
                 }
              }
              else if(flag=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
              else
              {                 
                  //alert("Failed to Insert values into Database.");                          
              }              
          }
        }
    }   


function nullCheck()
{
//alert("test");
 //alert(document.frmEmployee.Employee_Id.value);
    /*if((frmEmployee.txtEmpId.value=="") || (frmEmployee.txtEmpId.value.length<=0))
    {
        //alert(document.frmEmployee.Employee_Id.value);
         alert("Please Enter Employee_Id");
         frmEmployee.txtEmpId.focus();
         return false;
    }*/  
   
    if((document.frmEmployee.Employee_Prefix.value=="") || (document.frmEmployee.Employee_Prefix.value.length<=0))
    {
         alert("Please Enter Employee_Prefix");
         document.frmEmployee.Employee_Prefix.focus();
         return false;
    }
 
    if((document.frmEmployee.Employee_Initial.value=="") || (document.frmEmployee.Employee_Initial.value.length<=0))
    {
         alert("Please Enter Employee_Initial");
         document.frmEmployee.Employee_Initial.focus();
         return false;
    }
    if((document.frmEmployee.Gpf_Number.value=="") || (document.frmEmployee.Gpf_Number.value.length<=0))
    {
         alert("Please Enter Gpf_Number");
         document.frmEmployee.Gpf_Number.focus();
         return false;
    }
 
    if((document.frmEmployee.Employee_Name.value=="") || (document.frmEmployee.Employee_Name.value.length<=0))
    {
         alert("Please Enter Employee_Name");
         document.frmEmployee.Employee_Name.focus();
         return false;
    }
    if((document.frmEmployee.Date_Of_Birth.value=="") || (document.frmEmployee.Date_Of_Birth.value.length<=0))
    {
         alert("Please Enter or Select Date_Of_Birth");
         document.frmEmployee.Date_Of_Birth.focus();
         return false;
    }
    if((document.frmEmployee.Blood_Group.value=="") || (document.frmEmployee.Blood_Group.value.length<=0))
    {
         alert("Please Enter Blood_Group");
         document.frmEmployee.Blood_Group.focus();
         return false;
    }
    if((document.frmEmployee.Community.value=="") || (document.frmEmployee.Community.value.length<=0))
    {
         alert("Please Select Community");
         document.frmEmployee.Community.focus();
         return false;
    }
    if((document.frmEmployee.Religion.value=="") || (document.frmEmployee.Religion.value.length<=0))
    {
         alert("Please Select Religion");
         document.frmEmployee.Religion.focus();
         return false;
    }
    if((document.frmEmployee.Mother_Tongue.value=="") || (document.frmEmployee.Mother_Tongue.value.length<=0))
    {
         alert("Please Select Mother_Tongue");
         document.frmEmployee.Mother_Tongue.focus();
         return false;
    }
    if((document.frmEmployee.Medium_Study.value=="") || (document.frmEmployee.Medium_Study.value.length<=0))
    {
         alert("Please Select Medium_Study");
         document.frmEmployee.Medium_Study.focus();
         return false;
    }
    if((document.frmEmployee.Native_District.value=="") || (document.frmEmployee.Native_District.value.length<=0))
    {
         alert("Please Select Native_District");
         document.frmEmployee.Native_District.focus();
         return false;
    }
    if((document.frmEmployee.Native_Taluk.value=="") || (document.frmEmployee.Native_Taluk.value.length<=0))
    {
         alert("Please Native_Taluk");
         document.frmEmployee.Native_Taluk.focus();
         return false;
    }
    if((document.frmEmployee.Qualification.value=="") || (document.frmEmployee.Qualification.value.length<=0))
    {
         alert("Please Qualification");
         document.frmEmployee.Qualification.focus();
         return false;
    }
    if((document.frmEmployee.Post_Status.value=="") || (document.frmEmployee.Post_Status.value.length<=0))
    {
         alert("Please Post_Status");
         document.frmEmployee.Post_Status.focus();
         return false;
    }
    
    if((document.frmEmployee.Pan_Number.value=="") || (document.frmEmployee.Pan_Number.value.length<=0))
    {
         alert("Please Enter Pan_Number");
         document.frmEmployee.Pan_Number.focus();
         return false;
    }
    if((document.frmEmployee.Date_Of_Twad.value=="") || (document.frmEmployee.Date_Of_Twad.value.length<=0))
    {
         alert("Please Enter Date_Of_Twad");
         document.frmEmployee.Date_Of_Twad.focus();
         return false;
    }
    if((document.frmEmployee.Designation.value=="") || (document.frmEmployee.Designation.value.length<=0))
    {
         alert("Please Select Designation");
         document.frmEmployee.Designation.focus();
         return false;
    }
    if((document.frmEmployee.Recruitment_Mode.value=="") || (document.frmEmployee.Recruitment_Mode.value.length<=0))
    {
         alert("Please Select Recruitment_Mode");
         document.frmEmployee.Recruitment_Mode.focus();
         return false;
    }
    if((document.frmEmployee.Date_Counted.value=="") || (document.frmEmployee.Date_Counted.value.length<=0))
    {
         alert("Please Enter Date_Counted");
         document.frmEmployee.Date_Counted.focus();
         return false;
    }
    /*if((document.frmEmployee.Temporary_Residence_Address1.value=="") || (document.frmEmployee.Temporary_Residence_Address1.value.length<=0))
    {
         alert("Please Enter Temporary_Residence_Address1");
         document.frmEmployee.Temporary_Residence_Address1.focus();
         return false;
    }
    if((document.frmEmployee.Temporary_Residence_Address2.value=="") || (document.frmEmployee.Temporary_Residence_Address2.value.length<=0))
    {
         alert("Please Enter Temporary_Residence_Address2");
         document.frmEmployee.Temporary_Residence_Address2.focus();
         return false;
    }
    
    if((document.frmEmployee.Temporary_Residence_Address3.value=="") || (document.frmEmployee.Temporary_Residence_Address3.value.length<=0))
    {
         alert("Please Enter Temporary_Residence_Address3");
         document.frmEmployee.Temporary_Residence_Address3.focus();
         return false;
    }
    if((document.frmEmployee.Temporary_Residence_Pincode.value=="") || (document.frmEmployee.Temporary_Residence_Pincode.value.length<=0))
    {
         alert("Please Enter Temporary_Residence_Pincode");
         document.frmEmployee.Temporary_Residence_Pincode.focus();
         return false;
    }
    if((document.frmEmployee.Temporary_Residence_PhoneNo.value=="") || (document.frmEmployee.Temporary_Residence_PhoneNo.value.length<=0))
    {
         alert("Please Enter Temporary_Residence_PhoneNo");
         document.frmEmployee.Temporary_Residence_PhoneNo.focus();
         return false;
    }
    if((document.frmEmployee.Permanent_Residence_Address1.value=="") || (document.frmEmployee.Permanent_Residence_Address1.value.length<=0))
    {
         alert("Please Enter Permanent_Residence_Address1");
         document.frmEmployee.Permanent_Residence_Address1.focus();
         return false;
    }
    if((document.frmEmployee.Permanent_Residence_Address2.value=="") || (document.frmEmployee.Permanent_Residence_Address2.value.length<=0))
    {
         alert("Please Enter Permanent_Residence_Address2");
         document.frmEmployee.Permanent_Residence_Address2.focus();
         return false;
    }
    if((document.frmEmployee.Permanent_Residence_Address3.value=="") || (document.frmEmployee.Permanent_Residence_Address3.value.length<=0))
    {
         alert("Please Enter Permanent_Residence_Address3");
         document.frmEmployee.Permanent_Residence_Address3.focus();
         return false;
    }
    if((document.frmEmployee.Permanent_Residence_Pincode.value=="") || (document.frmEmployee.Permanent_Residence_Pincode.value.length<=0))
    {
         alert("Please Enter Permanent_Residence_Pincode");
         document.frmEmployee.Permanent_Residence_Pincode.focus();
         return false;
    }
    if((document.frmEmployee.Permanent_Residence_Phoneno.value=="") || (document.frmEmployee.Permanent_Residence_Phoneno.value.length<=0))
    {
         alert("Please Enter Permanent_Residence_Phoneno");
         document.frmEmployee.Permanent_Residence_Phoneno.focus();
         return false;
    }
    if((document.frmEmployee.Contact_Cell_No.value=="") || (document.frmEmployee.Contact_Cell_No.value.length<=0))
    {
         alert("Please Enter Contact_Cell_No");
         document.frmEmployee.Contact_Cell_No.focus();
         return false;
    }
    if((document.frmEmployee.Contact_Email.value=="") || (document.frmEmployee.Contact_Email.value.length<=0))
    {
         alert("Please Enter Contact_Email");
         document.frmEmployee.Contact_Email.focus();
         return false;
    }*/
    if((document.frmEmployee.SpouseDistrict.value=="") || (document.frmEmployee.SpouseDistrict.value.length<=0))
    {
         alert("Please Select SpouseDistrict");
         document.frmEmployee.SpouseDistrict.focus();
         return false;
    }
    if((document.frmEmployee.SpouseOffice.value=="") || (document.frmEmployee.SpouseOffice.value.length<=0))
    {
         alert("Please Enter SpouseOffice");
         document.frmEmployee.SpouseOffice.focus();
         return false;
    }
    if((document.frmEmployee.Employee_Signature.value=="") || (document.frmEmployee.Employee_Signature.value.length<=0))
    {
         alert("Please Select  Employee_Signature");
         document.frmEmployee.Employee_Signature.focus();
         return false;
    }
    if((document.frmEmployee.Remarks.value=="") || (document.frmEmployee.Remarks.value.length<=0))
    {
         alert("Please Enter Remarks");
         document.frmEmployee.Remarks.focus();
         return false;
    }
    if((document.frmEmployee.Employee_Status.value=="") || (document.frmEmployee.Employee_Status.value.length<=0))
    {
         alert("Please Select Employee_Status");
         document.frmEmployee.Employee_Status.focus();
         return false;
    }
    
    return true;
  
}



function promptID()
{        
      edit=true;
      document.frmEmployee.cmdSelect.disabled=false;
      alert("Please Enter or Select a Employee Id");
      document.frmEmployee.Employee_Id.focus(); 
}


function popupWindow()
{
      my_window= window.open("LoadEmployee.jsp","mywindow1","status=1,height=110,width=500,resizable"); 
      my_window.moveTo(250,250);    
}

function isInteger(param,e)
{     
       var nav4 = window.Event ? true : false;
       //alert(nav4);
       var nav41=window.event ? true : false;
       //alert("nav41"+nav41);
       if (nav4)  
       {// Navigator 4.0x
            var whichCode = e.which
       }
       
       if(nav41)        // Internet Explorer 4.0x
       {    
            var whichCode = e.keyCode
       }
      if((whichCode>=48 && whichCode<=57) || (whichCode>=97 && whichCode<=105))
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
        //alert("test");
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
                    eval('document.' + formName + '.' + textName + '.focus()');
                    //alert(eval);
                    eval('document.' + formName + '.' + textName + '.select()');
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
                  var emailID=document.frmEmployee.Contact_Email;
                      
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
            
            
            
            //Load Image
 function LoadImage()
 {
 alert("loading");
            //document.frmEmployee.EmpImage.src=document.frmEmployee.EmpBrowse.value;
            //alert("hi "+document.images.length);
            var image=document.getElementById("EmpImage");
            image.src=document.frmEmployee.EmpBrowse.value;
 alert("loaded");
 }
 
 function fun1()
 {
    var post=document.getElementById("Post_Status");
    //alert(post.value);
    var value=post.options[post.selectedIndex].text;
    if(value=="Regular")
    {
      document.frmEmployee.Gpf_Number.disabled=false;
      document.frmEmployee.Gpf_Number.style.background="White";
    }
    else
    {
      document.frmEmployee.Gpf_Number.disabled=true;
      document.frmEmployee.Gpf_Number.style.background="Silver";
    }
 }
 
 function image()
 {
window.open("ImageLoad.jsp");
 }
 
 function dateCheck()
 {
 //alert("test");
 var Date_Of_Twad=document.getElementById("Date_Of_Twad");
 var value=Date_Of_Twad.value;
 var Date_Counted=document.getElementById("Date_Counted");
 var value1=Date_Counted.value;
 if(value1<value)
 {
  alert("Your Servive Counted Date is Less Than Joining Date ");
  document.frmEmployee.Date_Counted.focus();
 }
 
}
function check(todate) 
{
//alert("test");
// document.workdemand.elements["txt_from"+c].value="" 
var from=document.frmEmployee.Date_Of_Twad.value;
//alert(from);
var fday=from.split("/");
//alert(fday);
var frmDay = fday[0]; 
var frmMonth = fday[1]; 
var frmYear =fday[2]; 

/* var frmDay = document.workdemand.elements["txt_from"+c].value.substr(0,2); 
var frmMonth = document.workdemand.elements["txt_from"+c].value.substr(3,2);
var frmYear = document.workdemand.elements["txt_from"+c].value.substr(6,4)*/

var frmday=new Date(frmYear,frmMonth-1,frmDay);
//alert("frmDay:"+frmday);
var to=document.frmEmployee.Date_Counted.value;
//alert(to);
var tday=to.split("/"); 
//alert(tday);
var toDay = tday[0]; 
var toMonth= tday[1];
var toYear = tday[2]; 

/* var toDay = todate.value.substr(0,2);
var toMonth = todate.value.substr(3,2); 
var toYear = todate.value.substr(6,4)*/

var today=new Date(toYear,toMonth-1,toDay);
//alert("Today:"+today);
if (todate.value != "" ) 
    {
        if (today>frmday) {
        alert ("Your Service Date Should Be Greater Than Entry Date")
        document.frmEmployee.Date_Counted.value="";
        document.frmEmployee.Date_Counted.focus();
        return false;
        }
        else
        {
        //document.frmEmployee.Marital_Status.focus();
        return true;
        }
    }
}

function fun1()
{
var id=document.getElementById("id").value;
//alert(id);
if((frmEmployee.txtEmpId.value=="") || (frmEmployee.txtEmpId.value.length<=0))
    {
        //alert(document.frmEmployee.Employee_Id.value);
         alert("Please Enter Employee_Id");
         frmEmployee.txtEmpId.focus();
         return false;
    }
    if(id!=null)
    {
    //document.frmEmployee.txtEmpId.disabled=true;
    window.location.href="EditEmployeeBasic.jsp?txtEmpId="+id;
    }

}