var currentlyEditing=0;
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


function clearAllWing()
{

        //document.frmOffice.txtSl_No.value="";
        document.frmOffice.txtWing_Name.value="";
        document.frmOffice.cmbWing_Head.selectedIndex=0
        document.frmOffice.txtDateCreated.value="";
        document.frmOffice.txtEmailId.value="";
        document.frmOffice.txtPhone_No.value="";
        document.frmOffice.txtFax_No.value="";
        document.frmOffice.Work_Nature.value="";
        var slno=slnocheck();
        //document.frmOffice.txtSl_No.value=++slno;
        document.frmOffice.cmdAdd.style.display="block";
        document.frmOffice.cmdUpdate.style.display="none";
        document.frmOffice.cmdDelete.style.display="none";
}

//Clear All
function clearAll()
{
        //document.frmOffice.txtSl_No.value="";
        document.frmOffice.txtWing_Name.value="";
        document.frmOffice.cmbWing_Head.selectedIndex=0
        document.frmOffice.txtDateCreated.value="";
        document.frmOffice.txtEmailId.value="";
        document.frmOffice.txtPhone_No.value="";
        document.frmOffice.txtFax_No.value="";
        document.frmOffice.Work_Nature.value="";
        
        var tbody=document.getElementById("tblList");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
            tbody.deleteRow(0);
        }
        //document.frmOffice.cmdAdd.disabled=false;
        document.frmOffice.cmdAdd.style.display="block";
        document.frmOffice.cmdUpdate.style.display="none";
        document.frmOffice.cmdDelete.style.display="none";

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
                  var tbody=document.getElementById("tblList");
                  var length=tbody.rows.length;
                  //alert(length);
                  if(length<=0)
                  {
                    
                    alert("There is No Values in Grid");
                    return false;
                  
                  }
                  
                 /* if((document.frmOffice.txtOffice_Name.value=="") || (document.frmOffice.txtOffice_Name.value.length<=0))
                  {
                      alert("Please Enter Office Name");
                      document.frmOffice.txtOffice_Namee.focus();
                      return false;
                  }  
                  
                  if((document.frmOffice.txtOffice_Address1.value=="") || (document.frmOffice.txtOffice_Address1.value.length<=0))
                  {
                      alert("Please Enter Office Address1");
                      document.frmOffice.txtOffice_Address1.focus();
                      return false;
                  }*/
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
                  }
                  if((document.frmOffice.cmbDistrict.value=="") || (document.frmOffice.cmbDistrict.selectedIndex<=0))
                  {
                      alert("Please Select a District");
                      document.frmOffice.cmbDistrict.focus();
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
                      MakeDivVisible('contacts');
                      frmOffice.txtSTDCode.focus();
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
                
                function nullWing()
                {
                
                  /*if((document.frmOffice.txtSl_No.value=="") || (document.frmOffice.txtSl_No.value.length<=0))
                  {
                    alert("Please Enter Sl_No");
                    document.frmOffice.txtSl_No.focus();
                    return false;
                    
                  }*/
                  
                   if((document.frmOffice.txtWing_Name.value=="") || (document.frmOffice.txtWing_Name.value.length<=0))
                  {
                  //alert(document.frmOffice.cmbWing_Head.value);
                    alert("Please Enter WingName");
                    document.frmOffice.txtWing_Name.focus();
                    return false;
                    
                  }
                  
                   if((document.frmOffice.cmbWing_Head.value=="") || (document.frmOffice.cmbWing_Head.value.length<=0) || (document.frmOffice.cmbWing_Head.value==0))
                  {
                    alert("Please Select Head Of The Wing");
                    document.frmOffice.cmbWing_Head.focus();
                    return false;
                  }
                  if((document.frmOffice.txtDateCreated.value=="") || (document.frmOffice.txtDateCreated.value.length<=0))
                  {
                    alert("Please Enter DateCreated");
                    document.frmOffice.txtDateCreated.focus();
                    return false;
                  }
                  /*if((document.frmOffice.txtPhone_No.value=="") || (document.frmOffice.txtPhone_No.value.length<=0))
                  {
                    alert("Please Enter Phone Number");
                    document.frmOffice.txtPhone_No.focus();
                    return false;
                  }
                  if((document.frmOffice.txtFax_No.value=="") || (document.frmOffice.txtFax_No.value.length<=0))
                  {
                    alert("Please Enter Fax Number");
                    document.frmOffice.txtFax_No.focus();
                    return false;
                  }
                  if((document.frmOffice.Work_Nature.value=="") || (document.frmOffice.Work_Nature.value.length<=0))
                  {
                    alert("Please Enter Work Nature");
                    document.frmOffice.Work_Nature.focus();
                    return false;
                  }
                  if((document.frmOffice.txtEmailId.value=="") || (document.frmOffice.txtEmailId.value.length<=0))
                  {
                    alert("Please Enter EmailId");
                    document.frmOffice.txtEmailId.focus();
                    return false;
                  }*/
                  
                    return true;
                
                }
                

function callServer1(command,param)
{
//alert("Callserver Called"+command);
    var url="";
    var Office_Id="";
    if(command=="Load")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        //alert(Office_Id);
        url="../../../../../EditWingsDetails.con?command=Load&OfficeId="+Office_Id;
        //alert(url);
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        processResponse(req);
        }
        req.send(null);
    }
   else if(command=="Add")
    {
        
        //alert(email);
        //var email=ValidateForm();
        var flag=nullWing();
        
        //alert(flag);
        if(flag==true)
        {
        var Office_Id="";
        var Sl_No="";
        var Wing_Name="";
        var Head_Wing="";
        var DateCreated="";
        var PhoneNo="";
        var FaxNo="";
        var NatureWork="";
        var Email="";
        Office_Id=document.frmOffice.txtOffice_Id.value;
        //Sl_No=document.frmOffice.txtSl_No.value;
        //alert(Sl_No);
        Wing_Name=document.frmOffice.txtWing_Name.value;
        var Head_Wing1=document.frmOffice.cmbWing_Head.options[document.frmOffice.cmbWing_Head.selectedIndex].text;
        //alert(Head_Wing1);
        Head_Wing=document.frmOffice.cmbWing_Head.value;
        DateCreated=document.frmOffice.txtDateCreated.value;
        PhoneNo=document.frmOffice.txtPhone_No.value;
        NatureWork=document.frmOffice.Work_Nature.value;
        FaxNo=document.frmOffice.txtFax_No.value;
        Email=document.frmOffice.txtEmailId.value;
        
        //url="../../../../../ServletOfficeWingInsert.con?command=Add&Office_Id="+Office_Id;
        
        
        //Append a row
        var tbody=document.getElementById("tblList");
        var mycurrent_row=document.createElement("TR");
        mycurrent_row.id=Sl_No;
        var cell1=document.createElement("TD");
        var cell2=document.createElement("TD");
        
        var cell3=document.createElement("TD");
        var cell4=document.createElement("TD");
        var cell5=document.createElement("TD");
        var cell6=document.createElement("TD");
        var cell7=document.createElement("TD");
        var cell8=document.createElement("TD");
        var cell9=document.createElement("TD");
       
        var anc=document.createElement("A");       
        
        //var url="javascript:loadValuesFromTable('" + Sl_No + "')";              
        //anc.href=url;
        var txtedit=document.createTextNode("Edit");
        anc.appendChild(txtedit);
        cell1.appendChild(anc);
        
        /*var hidden9=document.createElement("input");
        hidden9.type="hidden";
        hidden9.name="old";
        hidden9.value="new";
        cell1.appendChild(hidden9);*/
        
        mycurrent_row.appendChild(cell1);
        
        var txtslno=document.createTextNode(Sl_No);
        cell2.appendChild(txtslno);
        var hidden1=document.createElement("input");
        hidden1.type="hidden";
        hidden1.name="sno";
        hidden1.value=Sl_No;
        cell2.appendChild(hidden1);
        mycurrent_row.appendChild(cell2);
        
        var txtwingname=document.createTextNode(Wing_Name);
        cell3.appendChild(txtwingname);
        
        var hidden2=document.createElement("input");
        hidden2.type="hidden";
        hidden2.name="wingname";
        hidden2.value=Wing_Name;
        cell3.appendChild(hidden2);
        mycurrent_row.appendChild(cell3);
        
        var txtheadwing=document.createTextNode(Head_Wing);
        cell4.appendChild(txtheadwing);
        
        var hidden3=document.createElement("input");
        hidden3.type="hidden";
        hidden3.name="headwing";
        hidden3.value=Head_Wing;
        cell4.appendChild(hidden3);
        mycurrent_row.appendChild(cell4);
        
        var txtDateCreated=document.createTextNode(DateCreated);
        cell5.appendChild(txtDateCreated);
        
        var hidden4=document.createElement("input");
        hidden4.type="hidden";
        hidden4.name="datecreated";
        hidden4.value=DateCreated;
        cell5.appendChild(hidden4);
        mycurrent_row.appendChild(cell5);
        
        var txtphone=document.createTextNode(PhoneNo);
        cell6.appendChild(txtphone);
        
        var hidden5=document.createElement("input");
        hidden5.type="hidden";
        hidden5.name="phoneno";
        hidden5.value=PhoneNo;
        cell6.appendChild(hidden5);
        mycurrent_row.appendChild(cell6);
        
        
        var txtfaxno=document.createTextNode(FaxNo);
        cell7.appendChild(txtfaxno);
        
        var hidden6=document.createElement("input");
        hidden6.type="hidden";
        hidden6.name="faxno";
        hidden6.value=FaxNo;
        cell7.appendChild(hidden6);
        mycurrent_row.appendChild(cell7);
        
        var txtnaturework=document.createTextNode(NatureWork);
        cell8.appendChild(txtnaturework);
        
        var hidden7=document.createElement("input");
        hidden7.type="hidden";
        hidden7.name="naturework";
        hidden7.value=NatureWork;
        cell8.appendChild(hidden7);
        mycurrent_row.appendChild(cell8);
        
        var txtemailid=document.createTextNode(Email);
        cell9.appendChild(txtemailid);
        var hidden8=document.createElement("input");
        hidden8.type="hidden";
        hidden8.name="email";
        hidden8.value=Email;
        cell9.appendChild(hidden8);
         mycurrent_row.appendChild(cell9);
         
        tbody.appendChild(mycurrent_row);
        Sl_No++;
        
        //Clear the fields
        document.frmOffice.txtSl_No.value=Sl_No;
        document.frmOffice.txtWing_Name.value="";
        document.frmOffice.cmbWing_Head.selectedIndex=0
        document.frmOffice.txtDateCreated.value="";
        document.frmOffice.txtEmailId.value="";
        document.frmOffice.txtPhone_No.value="";
        document.frmOffice.txtFax_No.value="";
        document.frmOffice.Work_Nature.value="";
      }  
        
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditing);
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        cells.item(1).firstChild.nodeValue=document.frmOffice.txtSl_No.value;
        cells.item(1).lastChild.value=document.frmOffice.txtSl_No.value;
        
        cells.item(2).firstChild.nodeValue=document.frmOffice.txtWing_Name.value;
        cells.item(2).lastChild.value=document.frmOffice.txtWing_Name.value;
        
        cells.item(3).firstChild.nodeValue=document.frmOffice.cmbWing_Head.value;
        cells.item(3).lastChild.value=document.frmOffice.cmbWing_Head.value;
        
        cells.item(4).firstChild.nodeValue=document.frmOffice.txtDateCreated.value;
        cells.item(4).lastChild.value=document.frmOffice.txtDateCreated.value;
        
        cells.item(5).firstChild.nodeValue=document.frmOffice.txtPhone_No.value;
        cells.item(5).lastChild.value=document.frmOffice.txtPhone_No.value;
        
        cells.item(6).firstChild.nodeValue=document.frmOffice.txtFax_No.value;
        cells.item(6).lastChild.value=document.frmOffice.txtFax_No.value;
        
        cells.item(7).firstChild.nodeValue=document.frmOffice.Work_Nature.value;
        cells.item(7).lastChild.value=document.frmOffice.Work_Nature.value;
        
        cells.item(8).firstChild.nodeValue=document.frmOffice.txtEmailId.value;
        cells.item(8).lastChild.value=document.frmOffice.txtEmailId.value;
        var slno=slnocheck();
        document.frmOffice.txtSl_No.value=++slno;
        //cells.item(0).lastChild.value="update";
        clearAllWing();
    }
    else if(command=="Delete")
    {
        var trow=currentlyEditing;
        //alert(trow);
        var tbody=document.getElementById("Existing"); 
        var r=document.getElementById(trow);    
        var ri=r.rowIndex;               
        tbody.deleteRow(ri);
        
    }
    else if(command=="SlNo")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        url="../../../../../EditWingsDetails.con?command=SlNo&OfficeId="+Office_Id;
        //alert(url);
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        SlNoResponse(req);
        }
        req.send(null);
    
    }
    
    else if(command=="TableView")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        url="../../../../../EditWingsDetails.con?command=TableView&OfficeId="+Office_Id;
        //alert(url);
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        TableViewResponse(req);
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
                      var OfficeName=document.getElementById("txtOfficeName");
                      var OfficeId=document.getElementById("txtOfficeId");
                     
                      var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("Invalid Office Id");
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
                              var Phone=tmpoption.getElementsByTagName("Phone")[0].firstChild.nodeValue;
                              var Fax=tmpoption.getElementsByTagName("Fax")[0].firstChild.nodeValue;
                              //alert(district);
                              //district=district.trim();
                              document.frmOffice.txtOffice_Name.value=name;
                              if(officeAddress1!="null")
                              {
                                document.frmOffice.txtOffice_Address1.value=officeAddress1+"\n";
                              }
                              else
                              {
                              
                              }
                              if(officeAddress2!="null")
                              {
                                document.frmOffice.txtOffice_Address1.value=document.frmOffice.txtOffice_Address1.value+officeAddress2+"\n";
                              }
                              else
                              {
                              
                              }
                              if(officeAddress3!="null")
                              {
                                document.frmOffice.txtOffice_Address1.value=document.frmOffice.txtOffice_Address1.value+officeAddress3;
                              }
                              
                              /*if(Phone!="null")
                              {
                                document.frmOffice.txtPhoneNo.value=Phone;
                              }
                              if(Fax!="null")
                              {
                                document.frmOffice.txtFaxNo.value=Fax;
                              }*/
                              //var length=document.getElementById("cmbDistrict").options.length;
                              //alert(length);
                              /*for(var j=0;j<document.frmOffice.cmbDistrict.options.length();j++)
                              {
                                var value=document.frmOffice.cmbDistrict.options[j].value;
                                alert(value);
                                
                              }*/
                              document.frmOffice.cmbDistrict1.value=district;
                              document.frmOffice.cmbDistrict1.disabled=true;
                              
                              callServer1("SlNo",null);
                              
                          }
                          
                      }   
            }
        }
    }
}



// code for loading the values from the table to the input boxes
    // functionality for edit anchor
    function loadValuesFromTable(rid)
    {  
    //alert("hai");
      var r=document.getElementById(rid);      
      //alert("R is:"+r);
      var rcells=r.cells;
      //alert(rcells.item(0).lastChild.value);
      currentlyEditing=rcells.item(0).lastChild.value;
      //alert("currentlyEditing in Load"+currentlyEditing);
      //alert(rcells.item(1).lastChild.value);
      //alert(rcells.item(2).lastChild.value);
      //alert(rcells.item(3).lastChild.value);
      //alert(rcells.item(4).lastChild.value);
      //alert(rcells.item(5).lastChild.value);
      //alert(rcells.item(6).lastChild.value);
      //alert(rcells.item(7).lastChild.value);
      //alert(rcells.item(8).lastChild.value);
      
      //document.frmOffice.txtSl_No.value=rcells.item(1).firstChild.nodeValue;
      document.frmOffice.txtWing_Name.value=rcells.item(1).firstChild.nodeValue;
      document.frmOffice.cmbWing_Head.value=rcells.item(2).firstChild.nodeValue;
      document.frmOffice.txtDateCreated.value=rcells.item(3).firstChild.nodeValue;
      document.frmOffice.txtPhone_No.value=rcells.item(4).firstChild.nodeValue;
      document.frmOffice.txtFax_No.value=rcells.item(5).firstChild.nodeValue;
      document.frmOffice.Work_Nature.value=rcells.item(6).firstChild.nodeValue;
      document.frmOffice.txtEmailId.value=rcells.item(7).firstChild.nodeValue;
      
      document.frmOffice.cmdAdd.style.display="none";
      document.frmOffice.cmdUpdate.style.display="block";
      document.frmOffice.cmdDelete.style.display="block";
    }
    
    
    
    
    function SlNoResponse(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
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
                              //var id1=id+1;
                              //document.frmOffice.txtSl_No.value=++id;
                              
                          }
                          callServer1("TableView",null);
                      }
                }
            }
   }         
   
   
   
   function slnocheck()
   {
        var table=document.getElementById("tblList");
        var rows=table.rows;
        var value;
        //alert(rows.length);
   /*     for(var i=0;i<rows.length;i++)
        {
            //alert("inside for");
            var cells=rows[i].cells;
            value=cells[1].lastChild.value;
            for(var j=1;j<rows.length;j++)
            {
                if(value<cells[1].lastChild.value)
            }
        }*/
        var max=rows[0].cells[1].lastChild.value;
        //alert("max is:"+max);
        for(var i=1;i<rows.length;i++)
        {
            if(max<rows[i].cells[1].lastChild.value)
            {
                max=rows[i].cells[1].lastChild.value;
                
                
            }
        
        }
        
        //alert("Max Returned is:"+max);  
        return max;
            
   
   }
   
   
   function TableViewResponse(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                //alert("hai");
                //var cmbBranch=document.getElementById("cmbBranch");
                //alert(req.responseText);
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                 
                var tbody=document.getElementById("tblList");
                var t=0;
                     for(t=tbody.rows.length-1;t>=0;t--)
                     {
                         tbody.deleteRow(0);
                     }     
                      if(flag=="failure")
                      {
                         alert("There is No Values in Grid");
                         //document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("SlNo");
                          //alert(value.length);
                          
                          for(var i=0;i<value.length;i++)
                          {
                                var tmpoption=value.item(i);
                                
                                var Sl_No=response.getElementsByTagName("SlNo")[i].firstChild.nodeValue;
                                var Wing_Name=response.getElementsByTagName("wingname")[i].firstChild.nodeValue;
                                var Head_Wing=response.getElementsByTagName("winghead")[i].firstChild.nodeValue;
                                var DateCreated=response.getElementsByTagName("Date")[i].firstChild.nodeValue;
                                var PhoneNo=response.getElementsByTagName("PhoneNo")[i].firstChild.nodeValue;
                                var FaxNo=response.getElementsByTagName("FaxNo")[i].firstChild.nodeValue;
                                var NatureWork=response.getElementsByTagName("wingnature")[i].firstChild.nodeValue;
                                var Email=response.getElementsByTagName("Email")[i].firstChild.nodeValue;
                                var cadre_name=response.getElementsByTagName("cadrename")[i].firstChild.nodeValue;
                                //alert("slno"+Sl_No);
                                //alert("wingname"+Wing_Name);
                                //alert("Head_Wing"+Head_Wing);
                                //alert("DateCreated"+DateCreated);
                                //alert("PhoneNo:"+PhoneNo);
                                //alert("FaxNo"+FaxNo);
                                //alert("NatureWork"+NatureWork);
                                //alert("Email"+Email);
                                if(PhoneNo!="null")
                                {
                                    PhoneNo=PhoneNo;
                                }
                                else
                                {
                                    PhoneNo="";
                                }
                                
                                if(FaxNo!="null")
                                {
                                    FaxNo=FaxNo;
                                }
                                else
                                {
                                    FaxNo="";
                                }
                                if(NatureWork!="null")
                                {
                                    NatureWork=NatureWork;
                                }
                                else
                                {
                                    NatureWork="";
                                }
                                if(Email!="null")
                                {
                                    Email=Email;
                                }
                                else
                                {
                                    Email="";
                                }
                                
                                //Append a row
                                    var tbody=document.getElementById("tblList");
                                    var mycurrent_row=document.createElement("TR");
                                    mycurrent_row.id=Sl_No;
                                    var cell1=document.createElement("TD");
                                    var cell2=document.createElement("TD");
                                    
                                    var cell3=document.createElement("TD");
                                    var cell4=document.createElement("TD");
                                    var cell5=document.createElement("TD");
                                    var cell6=document.createElement("TD");
                                    var cell7=document.createElement("TD");
                                    var cell8=document.createElement("TD");
                                    var cell9=document.createElement("TD");
                                   
                                    var anc=document.createElement("A");       
                                    
                                    //var url="javascript:loadValuesFromTable('" + Sl_No + "')";              
                                    //anc.href=url;
                                    var txtedit=document.createTextNode("Edit");
                                    anc.appendChild(txtedit);
                                    cell1.appendChild(anc);
                                    
                                    var hidden1=document.createElement("input");
                                    hidden1.type="hidden";
                                    hidden1.name="sno";
                                    hidden1.value=Sl_No;
                                    cell1.appendChild(hidden1);
                                    
                                    mycurrent_row.appendChild(cell1);
                                    
                                    /*var txtslno=document.createTextNode(Sl_No);
                                    cell2.appendChild(txtslno);
                                    var hidden1=document.createElement("input");
                                    hidden1.type="hidden";
                                    hidden1.name="sno";
                                    hidden1.value=Sl_No;
                                    cell2.appendChild(hidden1);
                                    mycurrent_row.appendChild(cell2);*/
                                    
                                    var txtwingname=document.createTextNode(Wing_Name);
                                    cell2.appendChild(txtwingname);
                                    
                                    var hidden2=document.createElement("input");
                                    hidden2.type="hidden";
                                    hidden2.name="wingname";
                                    hidden2.value=Wing_Name;
                                    cell2.appendChild(hidden2);
                                    mycurrent_row.appendChild(cell2);
                                    
                                    var txtheadwing=document.createTextNode(cadre_name);
                                    cell3.appendChild(txtheadwing);
                                    
                                    var hidden3=document.createElement("input");
                                    hidden3.type="hidden";
                                    hidden3.name="headwing";
                                    hidden3.value=Head_Wing;
                                    cell3.appendChild(hidden3);
                                    mycurrent_row.appendChild(cell3);
                                    
                                    var txtDateCreated=document.createTextNode(DateCreated);
                                    cell4.appendChild(txtDateCreated);
                                    
                                    var hidden4=document.createElement("input");
                                    hidden4.type="hidden";
                                    hidden4.name="datecreated";
                                    hidden4.value=DateCreated;
                                    cell4.appendChild(hidden4);
                                    mycurrent_row.appendChild(cell4);
                                    
                                    var txtphone=document.createTextNode(PhoneNo);
                                    cell5.appendChild(txtphone);
                                    
                                    var hidden5=document.createElement("input");
                                    hidden5.type="hidden";
                                    hidden5.name="phoneno";
                                    hidden5.value=PhoneNo;
                                    cell5.appendChild(hidden5);
                                    mycurrent_row.appendChild(cell5);
                                    
                                    
                                    var txtfaxno=document.createTextNode(FaxNo);
                                    cell6.appendChild(txtfaxno);
                                    
                                    var hidden6=document.createElement("input");
                                    hidden6.type="hidden";
                                    hidden6.name="faxno";
                                    hidden6.value=FaxNo;
                                    cell6.appendChild(hidden6);
                                    mycurrent_row.appendChild(cell6);
                                    
                                    var txtnaturework=document.createTextNode(NatureWork);
                                    cell7.appendChild(txtnaturework);
                                    
                                    var hidden7=document.createElement("input");
                                    hidden7.type="hidden";
                                    hidden7.name="naturework";
                                    hidden7.value=NatureWork;
                                    cell7.appendChild(hidden7);
                                    mycurrent_row.appendChild(cell7);
                                    
                                    var txtemailid=document.createTextNode(Email);
                                    cell8.appendChild(txtemailid);
                                    var hidden8=document.createElement("input");
                                    hidden8.type="hidden";
                                    hidden8.name="email";
                                    hidden8.value=Email;
                                    cell8.appendChild(hidden8);
                                     mycurrent_row.appendChild(cell8);
                                     
                                    tbody.appendChild(mycurrent_row);
                                
                                
                          }
                      }
                }      
                
             }
     } 
     
     
     
//Office Loading Using Icon
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
    checkofficestatus();
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
                    alert("Fax No. Length atleast 6");
                    document.frmOffice.txtFax_No.focus();
                    return false;
        }
    }
    return true;
}


function checkofficestatus()
{


            var officeid=document.frmOffice.txtOffice_Id.value;
            //startwaiting(document.frmOffice) ;
            //alert(officeid);
            url="../../../../../ServletCheckStatusWing.con?OfficeId="+officeid;
            //alert(url);
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
                //stopwaiting(document.frmConversion) ;
                //document.frmClosure.txtClosureDate.value="";
                //document.frmClosure.txtRemarks.value="";
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
                if(flag=="success")
                {
                    var recordstatus=response.getElementsByTagName("recordstatus")[0].firstChild.nodeValue;
                       if(recordstatus=="FR")
                       {
                        alert("Record is Freezed");
                        document.frmOffice.cmbSubmit.disabled=true;
                       }
                       else
                       {
                            /*if((recordstatus=="CR") || (recordstatus=="MD"))
                            {
                                alert("This Record has been Created Now,Choose Edit Form or Validate Form");
                                document.frmOffice.txtOffice_Id.value="";
                                document.frmOffice.txtSl_No.value="";
                                document.frmOffice.txtWing_Name.value="";
                                document.frmOffice.cmbWing_Head.selectedIndex=0
                                document.frmOffice.txtDateCreated.value="";
                                document.frmOffice.txtEmailId.value="";
                                document.frmOffice.txtPhone_No.value="";
                                document.frmOffice.txtFax_No.value="";
                                document.frmOffice.Work_Nature.value="";
                                document.frmOffice.txtOffice_Name.value="";
                                document.frmOffice.txtOffice_Address1.value="";
                                var tbody=document.getElementById("tblList");
                                var t=0;
                                 for(t=tbody.rows.length-1;t>=0;t--)
                                 {
                                     tbody.deleteRow(0);
                                 }    
                                 document.frmOffice.txtOffice_Id.focus();*/
                                 document.frmOffice.cmbSubmit.disabled=false;
                            }
                            
                       }
                }
            }
        }


function clear1()
{
    var tbody=document.getElementById("tblList");
    var t=0;
     for(t=tbody.rows.length-1;t>=0;t--)
     {
         tbody.deleteRow(0);
     }    
    document.frmOffice.cmbSubmit.disabled=false;
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

                if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                {
                    alert("Please Enter Office Id or Select ");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                }


}