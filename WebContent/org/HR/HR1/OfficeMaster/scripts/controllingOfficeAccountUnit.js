// code for selecting the controlling office
            ///888888888888888888888888888888888888888888
            
            
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

    function getOfficesByType()
    {
        var type=document.frmOffice.cmbOfficeType.options[document.frmOffice.cmbOfficeType.selectedIndex].value;
        if(type!=0)
        {
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.frmOffice.cmbSelectOffice.style.visibility="visible";
            loadOfficesByType(type);
        }
    }
    
    function getOfficesByLevel()
    {
        var level=document.frmOffice.cmbControllingLevel.options[document.frmOffice.cmbControllingLevel.selectedIndex].value;
        var levelt=document.frmOffice.cmbControllingLevel.options[document.frmOffice.cmbControllingLevel.selectedIndex].text;
        if(levelt=="Division" || levelt=="Sub-Division" || levelt=="Section" )
        {
            var din=document.getElementById("divType1");
            din.style.visibility="visible";
            document.frmOffice.cmbOfficeType.style.visibility="visible";
            var din=document.getElementById("divType2");
            din.style.visibility="hidden";
            document.frmOffice.cmbSelectOffice.style.visibility="hidden";
            try
            {
            document.frmOffice.cmbOfficeType.focus();
            document.frmOffice.cmbOfficeType.select();
            }catch(e){}
        }
        else
        {
            var din=document.getElementById("divType1");
            din.style.visibility="hidden";
            document.frmOffice.cmbOfficeType.style.visibility="hidden";
            
            var din=document.getElementById("divType2");
            din.style.visibility="visible";
            document.frmOffice.cmbSelectOffice.style.visibility="visible";
            if(level!="----Select OfficeLevel----")
            {
                loadOfficesByLevel(level);
            }
        }
    }
    
    function loadOfficesByLevel(level)
    {
    
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=level&level="+level;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
        req.send(null);
    }
    
    function loadOfficesByType(type)
    {
        //alert(type);
        var level=document.frmOffice.cmbControllingLevel.options[document.frmOffice.cmbControllingLevel.selectedIndex].value;
        var url="../../../../../ServletGetOfficesByTypeOrLevel.con?command=type&level=" + level + "&type="+type;
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            loadOffices(req);
        }
        req.send(null);
    }
    
    function loadOffices(req)
    {
        if(req.readyState==4)
        {
          if(req.status==200)
          {  
                var cmboffices=document.getElementById("cmbSelectOffice");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                cmboffices.innerHTML="";
                //cmboffices.text="--select Office--";
                if(flag=="failure")
                {
                    alert("No Offices exists under this level");
                }
                else
                {                    
                    var value=response.getElementsByTagName("options");
                    var option=document.createElement("OPTION");
                    option.text="--Select Office--";
                    try
                    {
                        cmboffices.add(option);
                    }catch(errorObject)
                    {
                    cmboffices.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              cmboffices.add(option);
                          }
                          catch(errorObject)
                          {
                              cmboffices.add(option,null);
                          }
                    }
                }
            }
        }
    }
    
    function selectControllineOffice(type)
    {
    var ctlOffice=document.frmOffice.cmbSelectOffice.options[document.frmOffice.cmbSelectOffice.selectedIndex].value;
    //alert(ctlOffice);
        if(type=="controlling office")
        {
        
        
        if(ctlOffice!=0)
        {
            document.frmOffice.txtContrllingOfficeID.value=ctlOffice;
        }
        }
        else
        {
           document.frmOffice.txtOffice_Id.value=ctlOffice;
           document.frmOffice.txtOffice_Name.value=document.frmOffice.cmbSelectOffice.options[document.frmOffice.cmbSelectOffice.selectedIndex].text;
           callServer1("Load","null");
        }
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
    
    
    function callServer1(command,param)
{

    var url="";
    var Office_Id="";
    if(command=="Load")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        var accountunit=document.frmOffice.account_unit[0].checked;
        
            url="../../../../../ServletOfficeAccountingUnit.con?command=Load&OfficeId="+Office_Id;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true);        
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
                      var OfficeName=document.getElementById("txtOffice_Name");
                      var OfficeId=document.getElementById("txtOffice_Id");
                     
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                         document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                              var accunit=tmpoption.getElementsByTagName("AccUnit")[0].firstChild.nodeValue;
                              document.frmOffice.txtOffice_Name.value=name;
                              //document.frmOffice.Office_Address.value=officeAddress1+officeAddress3;
                              document.frmOffice.txtOffice_Address1.value="";
                              document.frmOffice.txtOffice_Address2.value="";
                              document.frmOffice.txtOffice_Address3.value="";
                              if(officeAddress1!="null")
                              {
                                document.frmOffice.txtOffice_Address1.value="";
                                document.frmOffice.txtOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.frmOffice.txtOffice_Address2.value="";
                                document.frmOffice.txtOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                document.frmOffice.txtOffice_Address3.value="";
                                document.frmOffice.txtOffice_Address3.value=officeAddress3;
                              }
                              if(accunit=="N")
                              {
                                document.frmOffice.account_unit[1].checked=true;
                                var divid=document.getElementById("account");
                                divid.style.display="block";
                                var attachoffice=tmpoption.getElementsByTagName("AttachOfficeId")[0].firstChild.nodeValue;
                                if(attachoffice!="null")
                                {
                                document.frmOffice.txtAttachedOfficeID.value=attachoffice;
                                document.frmOffice.txtAttachedOfficeID.focus();
                                callServer2('Attach','null');
                                }
                                
                              }
                              if(accunit=="Y")
                              {
                              document.frmOffice.account_unit[0].checked=true;
                              }
                                                     
                              
                          }
                          
                      }   
            }
        }
    }

}


  function callServer3(command,param)
{
//alert("callser3");
    var url="";
    var Office_Id="";
    if(command=="Load")
    {
        Office_Id=document.frmOffice.txtAttachedOfficeID.value;
        //alert("Offc"+Office_Id);
        var accountunit=document.frmOffice.account_unit[0].checked;
        
            url="../../../../../ServletOfficeAccountingUnit.con?command=Load&OfficeId="+Office_Id;
            //alert(url);
            var req=getTransport();
            req.open("POST",url,true);        
            req.onreadystatechange=function()
            {
            processResponse1(req);
            }
            req.send(null);
                       
    }
    
    function processResponse1(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      //alert("********"+req.responseText);
                      var OfficeName=document.getElementById("txtOffice_Name");
                      var OfficeId=document.getElementById("txtOffice_Id");
                     
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                         document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                              var accunit=tmpoption.getElementsByTagName("AccUnit")[0].firstChild.nodeValue;
                              document.frmOffice.AccountOffice_Name.value=name;
                              //document.frmOffice.Office_Address.value=officeAddress1+officeAddress3;
                              document.frmOffice.txtAccountOffice_Address1.value="";
                              document.frmOffice.txtAccountOffice_Address2.value="";
                              document.frmOffice.txtAccountOffice_Address3.value="";
                              if(officeAddress1!="null")
                              {
                                document.frmOffice.txtAccountOffice_Address1.value="";
                                document.frmOffice.txtAccountOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.frmOffice.txtAccountOffice_Address2.value="";
                                document.frmOffice.txtAccountOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                document.frmOffice.txtAccountOffice_Address3.value="";
                                document.frmOffice.txtAccountOffice_Address3.value=officeAddress3;
                              }
                               
                              
                          }
                          
                      }   
            }
        }
    }

}



function callServer2(command,param)
{
    
    var url="";
    var Office_Id="";
    Office_Id=document.frmOffice.txtAttachedOfficeID.value;
    //alert("office id:"+Office_Id);
    var Office_Id1=document.frmOffice.txtOffice_Id.value;
    if(Office_Id==Office_Id1)
    {
        alert("Already OfficeId Exists");
        document.frmOffice.txtAttachedOfficeID.focus();
        //return;
    }
    else
    {
            if(command=="Attach")
            {
                Office_Id=document.frmOffice.txtAttachedOfficeID.value;
                url="../../../../../ServletOfficeAccountingUnit.con?command=Attach&txtOfficeId="+Office_Id+"&OfficeId="+Office_Id1;
                var req=getTransport();
                req.open("POST",url,true);        
                req.onreadystatechange=function()
                {
                processResponse(req);
                }
                req.send(null);
            }
    }
    function processResponse(req)
          {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                      var OfficeName=document.getElementById("txtOffice_Name");
                      var OfficeId=document.getElementById("txtOffice_Id");
                     
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                         document.frmOffice.txtAttachedOfficeID.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var officeAddress1=tmpoption.getElementsByTagName("officeAddress1")[0].firstChild.nodeValue;
                              var officeAddress2=tmpoption.getElementsByTagName("officeAddress2")[0].firstChild.nodeValue;
                              var officeAddress3=tmpoption.getElementsByTagName("officeAddress3")[0].firstChild.nodeValue;
                              var accunit=tmpoption.getElementsByTagName("AccUnit")[0].firstChild.nodeValue;
                              document.frmOffice.txtAccountOffice_Address1.value="";
                              document.frmOffice.txtAccountOffice_Address2.value="";
                              document.frmOffice.txtAccountOffice_Address3.value="";
                              document.frmOffice.txtDateCreated.value="";
                              document.frmOffice.Remarks.value="";
                              
                              if(accunit=="N")
                              {
                                      callServer3('Load','null');
                                      
                                      
                                      var Date=tmpoption.getElementsByTagName("Date")[0].firstChild.nodeValue;
                                      var Remarks=tmpoption.getElementsByTagName("Remarks")[0].firstChild.nodeValue;
                                      if(Date!="null")
                                      {
                                        document.frmOffice.txtDateCreated.value=Date;
                                      }
                                      if(Remarks!="null")
                                      {
                                        document.frmOffice.Remarks.value=Remarks;
                                      }
                              }
                              document.frmOffice.AccountOffice_Name.value=name;
                              
                              if(officeAddress1!="null")
                              {
                                document.frmOffice.txtAccountOffice_Address1.value=officeAddress1;
                              }
                              if(officeAddress2!="null")
                              {
                                document.frmOffice.txtAccountOffice_Address2.value=officeAddress2;
                              }
                              if(officeAddress3!="null")
                              {
                                document.frmOffice.txtAccountOffice_Address3.value=officeAddress3;
                              }
                          
                          }
                          
                      }   
            }
        }
    }

}


function OfficeId()
{
    var divid=document.getElementById("account");
    divid.style.display="block";
}
function OfficeId1()
{
    var divid=document.getElementById("account");
    divid.style.display="none";
}


function nullCheck()
{   
    var account_unit=document.frmOffice.account_unit[0].checked;
    
    if(account_unit)
    {
                if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                  }
        return true;
    }
    
    else 
    {
        if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                  }
        if((document.frmOffice.txtAttachedOfficeID.value=="") || (document.frmOffice.txtAttachedOfficeID.value.length<=0))
               {
                    alert("Please Enter AccountOffice_Id");
                    document.frmOffice.txtAttachedOfficeID.focus();
                    return false;
                    
                  }
                  if((document.frmOffice.txtDateCreated.value=="") || (document.frmOffice.txtDateCreated.value.length<=0))
               {
                    alert("Please Enter DateCreated");
                    document.frmOffice.txtDateCreated.focus();
                    return false;
                    
                  }
    return true;
    
    
    }

}