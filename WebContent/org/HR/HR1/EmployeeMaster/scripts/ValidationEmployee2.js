
var req1 = false;
 try 
 {
       req1= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req1 = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req1 = false;
       }
 }
 if (!req1 && typeof XMLHttpRequest != 'undefined') 
 {
       req1 = new XMLHttpRequest();
 }   
 
 // function to call servlet 
 
 function callServer1()
 {     
 //alert("test");
          var url="";
              strSysId=document.frmEmployee.Native_District.value;
              if(strSysId!=999)
              {
                var d=document.getElementById("original");
                d.style.display="block";
                var d=document.getElementById("other");
                d.style.display="none";
                
                url="../../../../../ServletTaluk.con?txtSysId="+strSysId ;
              req1.open("GET",url,false);        
              req1.onreadystatechange=processResponse;
               if(window.XMLHttpRequest)
                        req1.send(null);
                else req1.send();
              
              }
              else
              {
              var d=document.getElementById("original");
                d.style.display="none";
                var d=document.getElementById("other");
                d.style.display="block";
              
              
              }
              //strSysDesc=document.frmMajorSystems.txtSysDesc.value;
              //strSysShortDesc=document.frmMajorSystems.txtSysShortDesc.value;
                    
              
                  
    }
 
 
 
 function callServer()
 {     
 //alert("test");
          var url="";
              strSysId=document.frmEmployee.Native_District.value;
              if(strSysId!=999)
              {
                var d=document.getElementById("original");
                d.style.display="block";
                var d=document.getElementById("other");
                d.style.display="none";
                
                url="../../../../../ServletTaluk.con?txtSysId="+strSysId ;
              req1.open("GET",url,true);        
              req1.onreadystatechange=processResponse;
               if(window.XMLHttpRequest)
                        req1.send(null);
                else req1.send();
              
              }
              else
              {
              var d=document.getElementById("original");
                d.style.display="none";
                var d=document.getElementById("other");
                d.style.display="block";
              
              
              }
              //strSysDesc=document.frmMajorSystems.txtSysDesc.value;
              //strSysShortDesc=document.frmMajorSystems.txtSysShortDesc.value;
                    
              
                  
    }

// code for processing the xml returned by servlet  
   
    function processResponse()
    {   
      if(req1.readyState==4)
        {
          if(req1.status==200)
          {             
              var baseResponse=req1.responseXML.getElementsByTagName("response")[0];
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
                      option.text=combovalue1;
                      option.value=combovalue;
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


/*function nullCheck(n)
{
   if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value.length<=0))
    {
         alert("Please Enter EmpId ");
         document.frmEmployee.txtEmpId1.focus();
         return false;
    }
  
    else if((document.frmEmployee.Office_Id.value=="") || (document.frmEmployee.Office_Id.value.length<=0))
    {
         alert("Please Enter Office_Id");
         document.frmEmployee.Office_Id.focus();
         return false;
    }
    else if((document.frmEmployee.Office_Name.value=="") || (document.frmEmployee.Office_Name.value.length<=0))
    {
         alert("Please Enter Office_Name");
         document.frmEmployee.Office_Name.focus();
         return false;
    }
    else if((document.frmEmployee.Office_Address.value=="") || (document.frmEmployee.Office_Address.value.length<=0))
    {
         alert("Please Enter Office_Address");
         document.frmEmployee.Office_Address.focus();
         return false;
    }
    else if((document.frmEmployee.Office_Grade.value=="") )
    {
         alert("Please Enter Office_Grade");
         document.frmEmployee.Office_Grade.focus();
         return false;
    }
    
    
   else if((document.frmEmployee.Date_Of_Birth.value=="") || (document.frmEmployee.Date_Of_Birth.value.length<=0))
    {
         alert("Please Enter or Select Date_Of_Birth");
         document.frmEmployee.Date_Of_Birth.focus();
         return false;
    }
     else if((document.frmEmployee.Community.value==0) || (document.frmEmployee.Community.value.length==0))
    {
         alert("Please Select Community");
         document.frmEmployee.Community.focus();
         return false;
    }
    else if((document.frmEmployee.Native_District.value==0) || (document.frmEmployee.Native_District.value.length==0))
    {
         alert("Please Select Native_District");
         document.frmEmployee.Native_District.focus();
         return false;
    }
   else if((document.frmEmployee.Native_Taluk.value=="") || (document.frmEmployee.Native_Taluk.value.length<=0))
    {
         alert("Please select Native_Taluk");
         document.frmEmployee.Native_Taluk.focus();
         return false;
    }
    else if((document.frmEmployee.Qualification.value=="") || (document.frmEmployee.Qualification.value<=0))
    {
         alert("Please enter Qualification");
         document.frmEmployee.Qualification.focus();
         return false;
    }
    else if((document.frmEmployee.Post_Status.value=="NoVal") || (document.frmEmployee.Post_Status.value.length==0))
    {
         alert("Please select Post_Status");
         document.frmEmployee.Post_Status.focus();
         return false;
    }
    else if((document.frmEmployee.Date_Of_Twad.value=="") || (document.frmEmployee.Date_Of_Twad.value.length<=0))
    {
         alert("Please Enter Date_Of_Twad");
         document.frmEmployee.Date_Of_Twad.focus();
         return false;
    }
    else if((document.frmEmployee.Designation.value==0) || (document.frmEmployee.Designation.value.length==0))
    {
         alert("Please Select Designation");
         document.frmEmployee.Designation.focus();
         return false;
    }
    else if((document.frmEmployee.Remarks.value=="") || (document.frmEmployee.Remarks.value.length<=0))
    {
         alert("Please Enter Remarks");
         document.frmEmployee.Remarks.focus();
         return false;
    }
    else if((document.frmEmployee.Employee_Status.value==0) || (document.frmEmployee.Employee_Status.value.length==0))
    {
         alert("Please Select Employee_Status");
         document.frmEmployee.Employee_Status.focus();
         return false;
    }
    
    return true;
  
}

*/

function promptID()
{        
      edit=true;
      document.frmEmployee.cmdSelect.disabled=false;
      alert("Please Enter or Select a Employee Id");
      document.frmEmployee.Employee_Id.focus(); 
}

var my_window;
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
    if((frmEmployee.txtEmpId.value=="") || (frmEmployee.txtEmpId.value.length<=0))
    {
        //alert(document.frmEmployee.Employee_Id.value);
         alert("Please Enter Employee_Id");
         frmEmployee.txtEmpId.focus();
         return false;
    }
    if(id!=null)
    {
    //document.frmEmployee.id.disabled=true;
    window.location.href="New_EditEmpBasic.jsp?txtEmpId="+id;
    }

}

function spouse()
{
    //var spouse=document.frmEmployee.Spouse.value;
    //var spouse=document.getElementById("spouse");
    //alert(spouse);
     document.frmEmployee.SpouseDistrict.disabled=true;
     document.frmEmployee.SpouseOffice.disabled=true;
}
function spouse1()
{

document.frmEmployee.SpouseDistrict.disabled=false;
document.frmEmployee.SpouseOffice.disabled=false;
}
function single()
{
//alert(document.frmEmployee.Spouse[0].value);
document.frmEmployee.Spouse[0].disabled=true;
document.frmEmployee.Spouse[1].disabled=true;
document.frmEmployee.SpouseDistrict.disabled=true;
document.frmEmployee.SpouseOffice.disabled=true;
}

function married()
{
document.frmEmployee.Spouse[0].disabled=false;
document.frmEmployee.Spouse[1].disabled=false;
document.frmEmployee.SpouseDistrict.disabled=false;
document.frmEmployee.SpouseOffice.disabled=false;
}