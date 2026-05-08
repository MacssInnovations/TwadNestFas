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

       


/* NullCheck Validation */
            function nullcheck()
            {
                  if((document.HRE_EmployeeServiceDetails.txtOffice_Id.value=="") || (document.HRE_EmployeeServiceDetails.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.HRE_EmployeeServiceDetails.txtOffice_Id.value='';
                    document.HRE_EmployeeServiceDetails.txtOffice_Name.value='';
                    document.HRE_EmployeeServiceDetails.txtOffice_Address1.value='';
                    //document.HRE_EmployeeServiceDetails.txtOffice_Address2.value='';
                   // document.HRE_EmployeeServiceDetails.txtOffice_City.value='';
                           
                   /* document.HRE_EmployeeServiceDetails.cmbControllingLevel.selectedIndex=0;
                    document.HRE_EmployeeServiceDetails.cmbOfficeType.selectedIndex=0;
                    document.HRE_EmployeeServiceDetails.cmbSelectOffice.selectedIndex=0;*/
                    document.HRE_EmployeeServiceDetails.txtOffice_Id.focus();
                    return false;
                    
                  }
                  
                  return true;
            }

function callServer1(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    var c= nullcheck();
    if(c==true)
    {
    if(command=="Load")
    {
      //alert("inside these");
      var rsno=document.getElementById("txtSNo1").value;
      var r=document.getElementById(rsno);
      var rcells=r.cells;
      Dept_Id=rcells.item(7).firstChild.nodeValue;
    
      Office_Id=document.HRE_EmployeeServiceDetails.txtOffice_Id.value;
      
    //  var   Dept_Id=document.HRE_EmployeeServiceDetails.txtDept_Id.value;
       // alert(Dept_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
        //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
    
    
   else if(command=="Load1")
    {
    //alert("inside load1");
    
  
        Office_Id=document.HRE_EmployeeServiceDetails.txtOffice_Id.value;
      
   
    var   Dept_Id=document.HRE_EmployeeServiceDetails.txtDept_Id.value;
       //alert(Dept_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
       // alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        processResponse1(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
 }   
 }
 /*
 function callServer(command,param)
 {
 
   if(command=="Load1")
    {
    alert("inside load1");
    
  
        Office_Id=document.HRE_EmployeeServiceDetails.txtOffice_Id.value;
      
   
    var   Dept_Id=document.HRE_EmployeeServiceDetails.txtDept_Id.value;
       alert(Dept_Id);
        url="../../../../../HRE_OfficeDetailServ.view?command=Load&OfficeId="+Office_Id+"&txtDept_Id="+Dept_Id;
        alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
        processResponse1(req);
        }
        if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
    }
 }   
 
 */
 
    
    function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert(req.responseText);
                      var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOfficeId");
                      
                      
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        document.HRE_EmployeeServiceDetails.txtOffice_Id.value='';
                        //document.HRE_EmployeeServiceDetails.txtDept_Id.value='';
                        document.HRE_EmployeeServiceDetails.txtOffice_Name.value='';
                        document.HRE_EmployeeServiceDetails.txtOffice_Address1.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_Address2.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_City.value='';
                               
                        /*document.HRE_EmployeeServiceDetails.cmbControllingLevel.selectedIndex=0;
                        document.HRE_EmployeeServiceDetails.cmbOfficeType.selectedIndex=0;
                        document.HRE_EmployeeServiceDetails.cmbSelectOffice.selectedIndex=0;*/
                        document.HRE_EmployeeServiceDetails.txtOffice_Id.focus();
                      }
                      else
                      {
                            document.HRE_EmployeeServiceDetails.txtOffice_Name.value='';
                        document.HRE_EmployeeServiceDetails.txtOffice_Address1.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_Address2.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_City.value='';
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
                              
                              var district='';
                              try{
                               district=tmpoption.getElementsByTagName("district_name")[0].firstChild.nodeValue;
                              }catch(e){}
                            
                              document.HRE_EmployeeServiceDetails.txtOffice_Name.value=name;
                              
                              if(officeAddress1=='null')
                                officeAddress1='';
                             if(officeAddress2=='null')
                                officeAddress2='';
                            if(officeAddress3=='null')
                                officeAddress3='';
                            
                                var fulladd=officeAddress1;
                                fulladd=fulladd+'\n'+officeAddress2;
                                fulladd=fulladd+'\n'+officeAddress3;
                                 
                                 try{
                                 if(district=='null')
                                        district='';
                                fulladd=fulladd+'\n'+district;
                                }catch(e){}
                               
                               document.HRE_EmployeeServiceDetails.txtOffice_Address1.value=fulladd;
                             
                    
                    
                           var rsno=document.getElementById("txtSNo1").value;
      var r=document.getElementById(rsno);
     // var r=tb.;
   
     // alert(r);
    var rcells=r.cells;
   // alert(rcells);
    Dept_Id=rcells.item(7).firstChild.nodeValue;
        var cmbdeptid=document.getElementById("txtDept_Id");
     // alert(cmbdeptid);
      var option=document.createElement("option");
      cmbdeptid.innerHTML="";
      option.text=Dept_Id;
      option.value=Dept_Id;
      //alert("1");
      try
      {
     // alert("2");
      cmbdeptid.add(option);
      }
      catch(e)
      {
     // alert("3");
      cmbdeptid.add(option,null);
      }                       
       
                            
                              //document.HRE_EmployeeServiceDetails.cmbDistrict.value=district;
                            /*  if(officeAddress1!="null")
                              {
                              document.HRE_EmployeeServiceDetails.txtOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.HRE_EmployeeServiceDetails.txtOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                   document.HRE_EmployeeServiceDetails.txtOffice_City.value=officeAddress3;
                             }*/
                             
                             
                             
                          }
                          
                      }   
                     

            }
        }
    }


 function processResponse1(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert(req.responseText);
                      var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOfficeId");
                      
                      
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                        document.HRE_EmployeeServiceDetails.txtOffice_Id.value='';
                        //document.HRE_EmployeeServiceDetails.txtDept_Id.value='';
                        document.HRE_EmployeeServiceDetails.txtOffice_Name.value='';
                        document.HRE_EmployeeServiceDetails.txtOffice_Address1.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_Address2.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_City.value='';
                               
                        /*document.HRE_EmployeeServiceDetails.cmbControllingLevel.selectedIndex=0;
                        document.HRE_EmployeeServiceDetails.cmbOfficeType.selectedIndex=0;
                        document.HRE_EmployeeServiceDetails.cmbSelectOffice.selectedIndex=0;*/
                        document.HRE_EmployeeServiceDetails.txtOffice_Id.focus();
                      }
                      else
                      {
                            document.HRE_EmployeeServiceDetails.txtOffice_Name.value='';
                        document.HRE_EmployeeServiceDetails.txtOffice_Address1.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_Address2.value='';
                       // document.HRE_EmployeeServiceDetails.txtOffice_City.value='';
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
                              
                              var district='';
                              try{
                               district=tmpoption.getElementsByTagName("district_name")[0].firstChild.nodeValue;
                              }catch(e){}
                            
                              document.HRE_EmployeeServiceDetails.txtOffice_Name.value=name;
                              
                              if(officeAddress1=='null')
                                officeAddress1='';
                             if(officeAddress2=='null')
                                officeAddress2='';
                            if(officeAddress3=='null')
                                officeAddress3='';
                            
                                var fulladd=officeAddress1;
                                fulladd=fulladd+'\n'+officeAddress2;
                                fulladd=fulladd+'\n'+officeAddress3;
                                 
                                 try{
                                 if(district=='null')
                                        district='';
                                fulladd=fulladd+'\n'+district;
                                }catch(e){}
                               
                               document.HRE_EmployeeServiceDetails.txtOffice_Address1.value=fulladd;
                             
                             
                          }
                          
                      }   
                     

            }
        }
    }
