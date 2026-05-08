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

if(deptid=="TWAD")
{
    document.frmNomenClature.txtOffice_Id.value=jobid;
    loadOffice(jobid);
    checkControlId();
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

//if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winjob && winjob.open && !winjob.closed) winjob.close();
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

                if((document.frmNomenClature.txtOffice_Id.value=="") || (document.frmNomenClature.txtOffice_Id.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmNomenClature.txtOffice_Id.focus();
                    return false;
                    
                }


}


function nullCheck()
{
    if((document.frmNomenClature.txtOffice_Id.value=="") || (document.frmNomenClature.txtOffice_Id.value.length<=0))
    {
        alert("Please Enter Office Id or Select ");
        document.frmNomenClature.txtOffice_Id.focus();
        return false;
        
    }
   var close= confirm("Are You Sure.You Delete this Record");
   if(close==false)
   {
        document.frmNomenClature.txtOffice_Id.value="";
        document.frmNomenClature.txtNewShortName.value="";
        document.frmNomenClature.txtOfficeName.value="";
        document.frmNomenClature.txtExistOfficeAddress.value="";
        document.frmNomenClature.txtconOfficeId.value="";
        document.frmNomenClature.txtDOF.value="";
        document.frmNomenClature.txtconOfficeName.value="";
        document.frmNomenClature.txtconOfficeAddress.value="";
        document.frmNomenClature.txtNewOfficeName.value="";
        document.frmNomenClature.cmbSecondaryID.value="";
        document.frmNomenClature.txtOffice_Id.focus();
        return false;

   }
   else
   {
    return true;
    }
    
}
//Loading Office Details values

function loadOffice(id)
{   

    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose...)");
        //document.frmNomenClature.txtAttachedOfficeID.focus();
    }
    else
    {
            //alert(id);
            startwaiting(document.frmNomenClature);
            var url="../../../../../ServletLoadOfficeDetails.con?ID="+id;            
            //alert(url);
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
                stopwaiting(document.frmNomenClature) ;
                document.frmNomenClature.txtOfficeName.value="";
                //document.frmNomenClature.txtOfficeAddress.value="";
                document.frmNomenClature.txtExistAddress.value="";
                document.frmNomenClature.txtOfficeName.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0];
                    var add=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    document.frmNomenClature.txtOfficeName.value=name.firstChild.nodeValue;
                    /*if(add!="null")
                    {
                    document.frmNomenClature.txtOfficeAddress.value=add;
                    }*/
                    
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                    //document.frmNomenClature.txtOfficeName.value=name;
                    
                    if(address1!="null")
                    {
                    document.frmNomenClature.txtExistAddress.value=address1+"\n";
                    }
                    else
                    {
                    //document.frmNomenClature.txtExistAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value+address2+"\n";
                    }
                    else
                    {
                       // document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value+city+"\n";
                    }
                    else
                    {
                       // document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value+"\n"+"";
                    }
                    if(district!="null")
                    {
                        document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value+district+"\n";
                    }
                    else
                    {
                       // document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value+"\n"+"";
                    }
                    if(pincode!=0)
                    {
                        if(pincode!="null")
                        {
                            document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value+pincode;
                        }
                    }
                    else
                    {
                        //document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value;
                    }
                    
                    document.frmNomenClature.txtExistAddress.value=document.frmNomenClature.txtExistAddress.value;
                    //document.frmNomenClature.txtDateOfRedepolyment.focus();
                    existingOffice();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.frmNomenClature.txtExistAddress.value="";
                    document.frmNomenClature.txtOfficeName.value="";
                    document.frmNomenClature.txtconOfficeId.value="";
                    document.frmNomenClature.txtconOfficeName.value="";
                    document.frmNomenClature.txtconOfficeAddress.value="";
                    document.frmNomenClature.txtOffice_Id.focus();
                } 
          }
    }       
}

function existingOffice()
{
            var officeid=document.frmNomenClature.txtOffice_Id.value;
            startwaiting(document.frmNomenClature) ;
            url="../../../../../ExistingOfficeServlet.con?OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                ExistingOfficeResponse(req);                
            }
            req.send(null);

}

function ExistingOfficeResponse(req)
{

        if(req.readyState==4)
        {
              if(req.status==200)
              {   
                stopwaiting(document.frmNomenClature) ;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                if(flag=="success")
                {
                    var id=response.getElementsByTagName("controllingid")[0].firstChild.nodeValue;
                    var name=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
                    document.frmNomenClature.txtconOfficeId.value=id;
                    document.frmNomenClature.txtconOfficeName.value=name;
                    ExistingOffice(id);
                }
                else
                {
                     
                }
              }
        }
        

}



//Existing Controlling Address Coding
function ExistingOffice(id)
{   
    if(id=="" || id==null)
    {
        alert("Enter or (Select An Office..Then Click choose...)");
        //document.frmNomenClature.txtAttachedOfficeID.focus();
    }
    else
    {
            startwaiting(document.frmNomenClature) ;
            var url="../../../../../ServletLoadOfficeDetails.con?ID="+id;            
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                ExistingOfficeDetails(req);
                
            }
            req.send(null);        
    }
}

function ExistingOfficeDetails(req)
{
   if(req.readyState==4)
    {
          if(req.status==200)
          {    
                stopwaiting(document.frmNomenClature) ;
                //document.frmNomenClature.txtOfficeName.value="";
                //document.frmNomenClature.txtOfficeAddress.value="";
               // document.frmNomenClature.txtcontrolling.value="";
                //document.frmNomenClature.txtcontrollingname.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    var name=response.getElementsByTagName("name")[0];
                    var add=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    //document.frmNomenClature.txtOfficeName.value=name.firstChild.nodeValue;
                    /*if(add!="null")
                    {
                    document.frmNomenClature.txtOfficeAddress.value=add;
                    }*/
                    
                    var address1=response.getElementsByTagName("address1")[0].firstChild.nodeValue;
                    var address2=response.getElementsByTagName("address2")[0].firstChild.nodeValue;
                    var city=response.getElementsByTagName("address")[0].firstChild.nodeValue;
                    var district=response.getElementsByTagName("district")[0].firstChild.nodeValue;
                    var pincode=response.getElementsByTagName("pincode")[0].firstChild.nodeValue;
                    //document.frmNomenClature.txtOfficeName.value=name;
                    
                    if(address1!="null")
                    {
                    document.frmNomenClature.txtconOfficeAddress.value=address1+"\n";
                    }
                    else
                    {
                    //document.frmNomenClature.txtconOfficeAddress.value="";
                    }
                    if(address2!="null")
                    {
                        document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value+address2+"\n";
                    }
                    else
                    {
                       // document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value+"\n"+"";
                    }
                    if(city!="null")
                    {
                    document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value+city+"\n";
                    }
                    else
                    {
                     //  document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value+"\n"+"";
                    }
                    if(district!="null")
                    {
                        document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value+district+"\n";
                    }
                    else
                    {
                        //document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value+"\n"+"";
                    }
                    if(pincode!=0)
                    {
                        if(pincode!="null")
                        {
                            document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value+pincode;
                        }
                    }
                    else
                    {
                        //document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value;
                    }
                    document.frmNomenClature.txtconOfficeAddress.value=document.frmNomenClature.txtconOfficeAddress.value;
                    //document.frmNomenClature.txtDateOfRedepolyment.focus();
                    checkoffice();
                    checkif();
                    dateofformation();
                    //checkControlId();
                    //checkofficestatus();
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.frmNomenClature.txtconOfficeAddress.value="";
                    document.frmNomenClature.txtconOfficeName.value="";
                    
                } 
          }
    }       
}


function checkoffice()
{
//alert('hai');

            var officeid=document.frmNomenClature.txtOffice_Id.value;
            startwaiting(document.frmNomenClature) ;
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
          stopwaiting(document.frmNomenClature) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    alert("This Office is Closed");
                    document.frmNomenClature.submit.disabled=true;
                    return false;
                
                }
                else
                {
                document.frmNomenClature.submit.disabled=false;
                    //return true;
                }
           }
        }
}



function checkofficestatus()
{
//alert('hai');

            var officeid=document.frmNomenClature.txtOffice_Id.value;
            startwaiting(document.frmNomenClature) ;
           // alert(officeid);
            url="../../../../../ServletCheckNomenClature.con?OfficeId="+officeid;
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
                stopwaiting(document.frmNomenClature) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                if(flag=="success")
                {
                    var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                    if((recordstatus=="CR")||(recordstatus=="MD") )
                    {
                    alert("This Record has been Created now.so,Choose Edit Form or Validate Form");
                    document.frmNomenClature.txtOfficeName.value="";
                    //document.frmClosure.txtOfficeType.value="";
                    document.frmNomenClature.txtExistAddress.value="";
                    document.frmNomenClature.txtconOfficeId.value="";
                    document.frmNomenClature.txtconOfficeName.value="";
                    document.frmNomenClature.txtconOfficeAddress.value="";
                    
                    document.frmNomenClature.txtOffice_Id.value="";
                    document.frmNomenClature.txtOffice_Id.focus();
                    //document.frmConversion.cmdSub.disabled=true;
                    }
                    
                
                }
                else
                {
               // checkControlId();
                //document.frmConversion.cmdSub.disabled=false;
                    //return true;
                }
           }
        }
}


function checkControlId()
{
            var officeid=document.frmNomenClature.txtOffice_Id.value;
            //alert(officeid);
            startwaiting(document.frmNomenClature) ;
            var url="../../../../../ServletNomenControlIdCheck.con?OfficeId="+officeid;            
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                CheckControlIdResponse(req);
            }
            req.send(null);    

}


function CheckControlIdResponse(req)
{

        if(req.readyState==4)
        {
          if(req.status==200)
          {         
                stopwaiting(document.frmNomenClature);
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                //alert(flag);
               // alert(req.responseTEXT);
                if(flag=="failure")
                {
                    var ctl=document.getElementById("divcontrol");
                        ctl.style.display="none";
                    alert('As this Office controls some other offices,so this Office cannot be Closed');
                    window.open('','_parent',''); 
                    window.close(); 
                   // window.opener.focus();
                   //if (winjob && winjob.open && !winjob.closed) winjob.close();
                   //winjob.close();
                   return false;
                }
                else if(flag=="success")
                {
                        var officeid=response.getElementsByTagName("officeid");
                        var ctl=document.getElementById("divcontrol");
                        ctl.style.display="block";
                        var tbody=document.getElementById("tblList");
                            var t=0;
                            for(t=tbody.rows.length-1;t>=0;t--)
                            {
                                tbody.deleteRow(0);
                            }
                        for(var i=0;i<officeid.length;i++)
                        {
                            
                            //alert(officeid[i].firstChild.nodeValue);
                            var officeid1=response.getElementsByTagName("officeid")[i].firstChild.nodeValue;
                            var officename=response.getElementsByTagName("officename")[i].firstChild.nodeValue;
                            var officeaddress1=response.getElementsByTagName("officeaddress1")[i].firstChild.nodeValue;
                            var officeaddress2=response.getElementsByTagName("officeaddress2")[i].firstChild.nodeValue;
                            var city=response.getElementsByTagName("city")[i].firstChild.nodeValue;
                            var district=response.getElementsByTagName("district")[i].firstChild.nodeValue;
                            var pincode=response.getElementsByTagName("pincode")[i].firstChild.nodeValue;
                            var address="";
                            
                            if(officeaddress1!="null")
                            {
                            officeaddress1=officeaddress1;
                            }
                            else
                            {
                                //alert('elseaddres1');
                                officeaddress1="";
                            }
                            if(officeaddress2!="null")
                            {
                                officeaddress2=officeaddress1+officeaddress2;
                            }
                            else
                            {
                                
                                officeaddress2=officeaddress1;
                            }
                            if((city!="null"))
                            {
                                 address=officeaddress1+officeaddress2+city;
                            }
                            else
                            {
                                
                                address=officeaddress2;
                            }
                            if(district!="null")
                            {
                                if(district!=0)
                                {
                                district=district;
                                }
                                else
                                {
                                district="";
                                }
                            }
                            else
                            {
                                district="";
                            }
                            //alert('after'+address);
                            //This coding for adding the values in table grid
                            
                            var table=document.getElementById("Existing");
                            var mycurrent_row=document.createElement("TR");
                            var cell1=document.createElement("TD");
                            var cell2=document.createElement("TD");
                            var cell3=document.createElement("TD");
                            var cell4=document.createElement("TD");
                            var cell5=document.createElement("TD");
                            
                            var txtofficeid=document.createTextNode(officeid1);
                            cell1.appendChild(txtofficeid);
                            var hidden1=document.createElement("input");
                            hidden1.type="hidden";
                            hidden1.name="officeid";
                            hidden1.value=officeid1;
                            cell1.appendChild(hidden1);
                            mycurrent_row.appendChild(cell1);
                            
                            var txtofficename=document.createTextNode(officename);
                            cell2.appendChild(txtofficename);
                            var hidden2=document.createElement("input");
                            hidden2.type="hidden";
                            hidden2.name="officename";
                            hidden2.value=txtofficename;
                            cell2.appendChild(hidden2);
                            mycurrent_row.appendChild(cell2);
                            
                            var txtofficeaddress1=document.createTextNode(address);
                            //txtofficeaddress1=txtofficeaddress1+","+officeaddress2;
                            cell3.appendChild(txtofficeaddress1);
                            var hidden3=document.createElement("input");
                            hidden3.type="hidden";
                            hidden3.name="officeaddress1";
                            hidden3.value=txtofficeaddress1;
                            cell3.appendChild(hidden3);
                            mycurrent_row.appendChild(cell3);
                            
                            var txtdistrict=document.createTextNode(district);
                            cell4.appendChild(txtdistrict);
                            var hidden5=document.createElement("input");
                            hidden5.type="hidden";
                            hidden5.name="district";
                            hidden5.value=txtdistrict;
                            cell4.appendChild(txtdistrict);
                            mycurrent_row.appendChild(cell4);
                            tbody.appendChild(mycurrent_row);
                       
                        }
                       /*var con=confirm("The following offices will also be closed.Is it Ok");
                       if(con==false)
                       {
                        window.open('','_parent',''); 
                    window.close(); 
                    //window.opener.focus();
                       }*/
                       
                }
                else if(flag=="failedcontrol")
                {
                        var ctl=document.getElementById("divcontrol");
                        ctl.style.display="none";
                        var tbody=document.getElementById("tblList");
                            var t=0;
                            for(t=tbody.rows.length-1;t>=0;t--)
                            {
                                tbody.deleteRow(0);
                            }
                }
                
                
           }
           
           
        }   

}



function funclear()
{
    var ctl=document.getElementById("divcontrol");
    ctl.style.display="none";
    var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
            tbody.deleteRow(0);
        }
    document.frmNomenClature.submit.disabled=false;
    
}


function checkif()
{
//alert('hai');

            var officeid=document.frmNomenClature.txtOffice_Id.value;
            startwaiting(document.frmNomenClature) ;
            url="../../../../../ServletEditNomenClature.con?OfficeId="+officeid;
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
                stopwaiting(document.frmNomenClature) ;
                document.frmNomenClature.txtNewOfficeName.value="";
                document.frmNomenClature.txtNewShortName.value="";
                document.frmNomenClature.cmbSecondaryID.selectedIndex=0;
                document.frmNomenClature.txtDateOfNomen.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    //alert("Office Id is Already Closed");
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    var officename=response.getElementsByTagName("officename")[0].firstChild.nodeValue;
                    var shortname=response.getElementsByTagName("shortname")[0].firstChild.nodeValue;
                    var primaryid=response.getElementsByTagName("primaryid")[0].firstChild.nodeValue;
                    //var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                    var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                    document.frmNomenClature.txtDateOfNomen.value=date;
                    document.frmNomenClature.txtNewOfficeName.value=officename;
                    document.frmNomenClature.txtNewShortName.value=shortname;
                    document.frmNomenClature.cmbSecondaryID.value=primaryid;
                    if(recordstatus=="FR")
                    {
                        alert('This Record is Freezed.');
                        //document.frmClosure.cmbRecordStatus.value=recordstatus;
                        document.frmNomenClature.submit.disabled=true;
                    }
                    else
                    {
                        //document.frmClosure.cmbRecordStatus.value=recordstatus;
                        document.frmNomenClature.submit.disabled=false;
                    }
                    //document.frmClosure.submit.value="Update";
                }
                else
                {
                    document.frmNomenClature.submit.disabled=false;
                    document.frmNomenClature.submit.value="Submit";
                    //var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    //alert("Error Occured : \n" + mess);  
                }
                
           }     
        }
}


function dateofformation()
{
            var url="";
            //alert('hai');
            var officeid=document.frmNomenClature.txtOffice_Id.value;
            startwaiting(document.frmNomenClature) ;
            url="../../../../../ServletDate.con?command=Date&OfficeId="+officeid;
            var req=getTransport();
            req.open("GET",url,true);         
            req.onreadystatechange=function()
            {
                DateResponse(req);                
            }
            req.send(null);
}

function DateResponse(req)
{
        if(req.readyState==4)
        {
          if(req.status==200)
          {         
                stopwaiting(document.frmNomenClature) ;
                document.frmNomenClature.txtDateOfFormation.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;               
                if(flag=="success")
                {
                    
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    if(date!="Not Specified")
                    {
                    document.frmNomenClature.txtDateOfFormation.value=date;
                    
                    document.frmNomenClature.HDateOfFormation.value=date;
                    }
                    
                  
                }
                else
                {
                    var mess=response.getElementsByTagName("message")[0].firstChild.nodeValue;
                    alert("Error Occured : \n" + mess);
                    document.frmNomenClature.txtDateOfFormation.value="";
                    document.frmNomenClature.txtClosureDate.value="";
                    //document.frmClosure.submit.value="Submit";
                }
                
           }     
        }
    

}
