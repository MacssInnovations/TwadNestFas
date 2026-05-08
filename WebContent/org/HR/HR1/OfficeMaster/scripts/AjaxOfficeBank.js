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

        document.frmOffice.txtSl_No.value="";
        document.frmOffice.txtWing_Name.value="";
        document.frmOffice.cmbWing_Head.selectedIndex=0
        document.frmOffice.txtDateCreated.value="";
        document.frmOffice.txtEmailId.value="";
        document.frmOffice.txtPhone_No.value="";
        document.frmOffice.txtFax_No.value="";
        document.frmOffice.Work_Nature.value="";
        document.frmOffice.cmdAdd.disabled=false;
        document.frmOffice.cmdUpdate.disabled=true;
        document.frmOffice.cmdDelete.disabled=true;
}

//Clear All
function clearAll()
{
        document.frmOffice.txtSl_No.value="";
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
        document.frmOffice.cmdAdd.disabled=false;
        document.frmOffice.cmdUpdate.disabled=true;
        document.frmOffice.cmdDelete.disabled=true;

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
            /*function echeck(str) 
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
        }*/ 
            


/* NullCheck Validation */
            function nullcheck()
            {
                  if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                  }
                  //alert(document.frmOffice.cmbBank.value);
                  //alert(document.frmOffice.cmbBank.value.length);
                  if((document.frmOffice.cmbBank.value=="") || (document.frmOffice.cmbBank.value.length<=0) ||(document.frmOffice.cmbBank.value==0))
                  {
                    alert("Please Select Bank Name");
                    document.frmOffice.cmbBank.focus();
                    return false;
                    
                  }
                  //alert(document.frmOffice.cmbBranch.value);
                  //alert(document.frmOffice.cmbBranch.value.length);
                  if((document.frmOffice.cmbBranch.value=="") || (document.frmOffice.cmbBranch.value.length<=0) ||(document.frmOffice.cmbBank.value==0))
                  {
                    alert("Please Select Branch Name");
                    document.frmOffice.cmbBranch.focus();
                    return false;
                    
                  }
                  if((document.frmOffice.txtDateOfClosure.value=="") || (document.frmOffice.txtDateOfClosure.value.length<=0))
                  {
                    alert("Please Enter Date ");
                    document.frmOffice.txtDateOfClosure.focus();
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
                  
                        
                  return true;
                }
                function fun1()
                  {
                  //alert("hai");
                  var tbody=document.getElementById("tblList");
                  var rows=tbody.rows;
                  if(rows.length!=0)
                  {
                    for(var i=0;i<rows.length;i++)
                    {
                        //alert("hai");
                        //alert(document.frmOffice.txtDateOfClosure.value);
                        //alert(rows[i].cells[3].firstChild.nodeValue);
                        var from=document.frmOffice.txtDateOfClosure.value;
                        //var todate=rows[i].cells[3].firstChild.nodeValue;
                        var todate=i;
                        check(from,todate);
                        
                    }
                  }
                  
                  }
                
                function nullWing()
                {
                
                  if((document.frmOffice.txtSl_No.value=="") || (document.frmOffice.txtSl_No.value.length<=0))
                  {
                    alert("Please Enter Sl_No");
                    document.frmOffice.txtSl_No.focus();
                    return false;
                    
                  }
                   if((document.frmOffice.txtWing_Name.value=="") || (document.frmOffice.txtWing_Name.value.length<=0))
                  {
                    alert("Please Enter WingName");
                    document.frmOffice.txtWing_Name.focus();
                    return false;
                    
                  }
                   if((document.frmOffice.cmbWing_Head.value=="") || (document.frmOffice.cmbWing_Head.value.length<=0))
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
                  if((document.frmOffice.txtPhone_No.value=="") || (document.frmOffice.txtPhone_No.value.length<=0))
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
                  }
                  
                    return true;
                
                }
                

function callServer1(command,param)
{

    var url="";
    var Office_Id="";
    if(command=="Load")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        url="../../../../../ServletOfficeBank.con?command=Load&OfficeId="+Office_Id;
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
        var email=ValidateForm();
        var flag=nullWing();
        if(flag==true && email==true)
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
        Sl_No=document.frmOffice.txtSl_No.value;
        Wing_Name=document.frmOffice.txtWing_Name.value;
        Head_Wing=document.frmOffice.cmbWing_Head.value;
        DateCreated=document.frmOffice.txtDateCreated.value;
        PhoneNo=document.frmOffice.txtPhone_No.value;
        NatureWork=document.frmOffice.Work_Nature.value;
        FaxNo=document.frmOffice.txtFax_No.value;
        Email=document.frmOffice.txtEmailId.value;
             
        //Append a row
        i=0;
        var tbody=document.getElementById("tblList");
        var mycurrent_row=document.createElement("TR");
        mycurrent_row.id=i;
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
        
        var url="javascript:loadValuesFromTable('" + Sl_No + "')";              
        anc.href=url;
        var txtedit=document.createTextNode("Edit");
        anc.appendChild(txtedit);
        cell1.appendChild(anc);
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
        
        clearAllWing();
    }
    else if(command=="Delete")
    {
        var trow=currentlyEditing;
        var tbody=document.getElementById("Existing"); 
        var r=document.getElementById(trow);    
        var ri=r.rowIndex;               
        tbody.deleteRow(ri);
        
    }
    else if(command=="SlNo")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        url="../../../../../ServletOfficeBank.con?command=SlNo&OfficeId="+Office_Id;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        SlNoResponse(req);
        }
        req.send(null);
    
    }
    else if(command=="BankId")
    {
        var branch=document.frmOffice.cmbBank.value;
        url="../../../../../ServletOfficeBank.con?command=BankId&BankId="+branch;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        BranchResponse(req);
        }
        req.send(null);
        
    
    }
    
    else if(command=="Branch")
    {
        var office=document.frmOffice.txtOffice_Id.value;
        var bank=document.frmOffice.cmbBank.value;
        var branch=document.frmOffice.cmbBranch.value;
        url="../../../../../ServletOfficeBank.con?command=Branch&BankId="+bank+"&BranchId="+branch+"&OfficeId="+office;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        TableResponse(req);
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
                         alert("failed to retrieve the values");
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
                              document.frmOffice.txtOffice_Name.value=name;
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
                                document.frmOffice.txtOffice_Address3.value=officeAddress3;
                              }
                              
                              if(Phone!="null")
                              {
                                //document.frmOffice.txtPhoneNo.value=Phone;
                              }
                              if(Fax!="null")
                              {
                                //document.frmOffice.txtFaxNo.value=Fax;
                              }
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
      var r=document.getElementById(rid);      
      var rcells=r.cells;
      currentlyEditing=rcells.item(1).firstChild.nodeValue;
      document.frmOffice.txtSl_No.value=rcells.item(1).firstChild.nodeValue;
      document.frmOffice.txtWing_Name.value=rcells.item(2).firstChild.nodeValue;
      document.frmOffice.cmbWing_Head.value=rcells.item(3).firstChild.nodeValue;
      document.frmOffice.txtDateCreated.value=rcells.item(4).firstChild.nodeValue;
      document.frmOffice.txtPhone_No.value=rcells.item(5).firstChild.nodeValue;
      document.frmOffice.txtFax_No.value=rcells.item(6).firstChild.nodeValue;
      document.frmOffice.Work_Nature.value=rcells.item(7).firstChild.nodeValue;
      document.frmOffice.txtEmailId.value=rcells.item(8).firstChild.nodeValue;
      
      document.frmOffice.cmdAdd.disabled=true;
      document.frmOffice.cmdUpdate.disabled=false;
      document.frmOffice.cmdDelete.disabled=false;
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
                              document.frmOffice.txtSl_No.value=++id;
                              
                          }
                      }
                }
            }
   }         
   
   
   
   function funBranch()
   {
    var branch=document.frmOffice.cmbBank.value;
    //alert(branch);
   callServer1("BankId",null); 
   
   }
   

   function BranchResponse(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                var cmbBranch=document.getElementById("cmbBranch");
                var response=req.responseXML.getElementsByTagName("response")[0];
                      var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("failed to retrieve the values");
                         //document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          cmbBranch.innerHTML="";
                          var option=document.createElement("OPTION");
                          option.text="--Select Branch--";
                          //option.value="0";
                          try
                                {
                                    cmbBranch.add(option);
                            }catch(errorobject)
                            { 
                                     cmbBranch.add(option,null);
                            }
                          
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                              var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                              var combovalue=id;
                              var combotext=name;
                              var option=document.createElement("OPTION");
                              option.text=combotext;
                              option.value=combovalue;
                              try
                                {
                                    cmbBranch.add(option);
                            }catch(errorobject)
                            { 
                                     cmbBranch.add(option,null);
                            }
                                                         
                              
                          }
                      }
                }
            }
   }         
  
   function TableResponse(req)
    {
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                var cmbBranch=document.getElementById("cmbBranch");
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      if(flag=="failure")
                      {
                         alert("There is No Values in Grid");
                         //document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("options");
                          
                          
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              
                              var BankId=tmpoption.getElementsByTagName("bankid")[0].firstChild.nodeValue;
                              var BranchId=tmpoption.getElementsByTagName("branchid")[0].firstChild.nodeValue;
                              var AccNo=tmpoption.getElementsByTagName("AccNo")[0].firstChild.nodeValue;
                              var AccType=tmpoption.getElementsByTagName("AccType")[0].firstChild.nodeValue;
                              var OperationalMode=tmpoption.getElementsByTagName("operationalmode")[0].firstChild.nodeValue;
                              var AccHead=tmpoption.getElementsByTagName("AccHead")[0].firstChild.nodeValue;
                              var AccDate=tmpoption.getElementsByTagName("AccDate")[0].firstChild.nodeValue;
                              var InitialAmount=tmpoption.getElementsByTagName("InitialAmount")[0].firstChild.nodeValue;
                              var tbody=document.getElementById("tblList");
                              var table=document.getElementById("Existing");
                              var mycurrent_row=document.createElement("TR");
                              mycurrent_row.id=i;
                                var cell1=document.createElement("TD");
                                var cell2=document.createElement("TD");
                                var cell3=document.createElement("TD");
                                var cell4=document.createElement("TD");
                                var cell5=document.createElement("TD");
                                var cell6=document.createElement("TD");
                                var cell7=document.createElement("TD");
                                var cell8=document.createElement("TD");
                                var cell9=document.createElement("TD");
                                
                                /*var txtbankid=document.createTextNode(BankId);
                                cell1.appendChild(txtbankid);
                                var hidden1=document.createElement("input");
                                hidden1.type="hidden";
                                hidden1.name="bankname";
                                hidden1.value=BankId;
                                cell1.appendChild(hidden1);
                                
                                mycurrent_row.appendChild(cell1);
                                
                                var txtbranchid=document.createTextNode(BranchId);
                                cell2.appendChild(txtbranchid);
                                var hidden2=document.createElement("input");
                                hidden2.type="hidden";
                                hidden2.name="branchname";
                                hidden2.value=BranchId;
                                cell2.appendChild(hidden2);
        
                                mycurrent_row.appendChild(cell2);*/
                                
                                var txtaccno=document.createTextNode(AccNo);
                                cell3.appendChild(txtaccno);
                                var hidden3=document.createElement("input");
                                hidden3.type="hidden";
                                hidden3.name="accno";
                                hidden3.value=AccNo;
                                cell3.appendChild(hidden3);
                                mycurrent_row.appendChild(cell3);
                                
                                var txtacctype=document.createTextNode(AccType);
                                cell4.appendChild(txtacctype);
                                var hidden4=document.createElement("input");
                                hidden4.type="hidden";
                                hidden4.name="acctype";
                                hidden4.value=AccType;
                                cell4.appendChild(hidden4);
                                mycurrent_row.appendChild(cell4);
                                
                                var txtoperationalmode=document.createTextNode(OperationalMode);
                                cell5.appendChild(txtoperationalmode);
                                var hidden5=document.createElement("input");
                                hidden5.type="hidden";
                                hidden5.name="operationalmode";
                                hidden5.value=OperationalMode;
                                cell5.appendChild(hidden5);
                                mycurrent_row.appendChild(cell5);
                                
                                var txtaccdate=document.createTextNode(AccDate);
                                cell6.appendChild(txtaccdate);
                                var hidden6=document.createElement("input");
                                hidden6.type="hidden";
                                hidden6.name="datecreated";
                                hidden6.value=AccDate;
                                cell6.appendChild(hidden6);
                                mycurrent_row.appendChild(cell6);
                                
                                var txtacchead=document.createTextNode(AccHead);
                                cell7.appendChild(txtacchead);
                                var hidden7=document.createElement("input");
                                hidden7.type="hidden";
                                hidden7.name="headaccno";
                                hidden7.value=AccHead;
                                cell7.appendChild(hidden7);
                                mycurrent_row.appendChild(cell7);
                                
                                
                                
                                var txtinitialamount=document.createTextNode(InitialAmount);
                                cell8.appendChild(txtinitialamount);
                                var hidden8=document.createElement("input");
                                hidden8.type="hidden";
                                hidden8.name="initialamount";
                                hidden8.value=InitialAmount;
                                cell8.appendChild(hidden8);
                                mycurrent_row.appendChild(cell8);
                                tbody.appendChild(mycurrent_row);
                                                         
                          }
                      }
                    
                }
            }
   }



function check(c,todate)
{
	
   // document.workdemand.elements["txt_from"+c].value=""
     //fday=document.workdemand.elements["txt_from"+c].value.split("/");
     var tbody=document.getElementById("tblList");
     var rows=tbody.rows;
     var todate=rows[todate].cells[3].firstChild.nodeValue;
     //alert("todate"+todate);
     var fday=document.frmOffice.txtDateOfClosure.value.split("/");
     var todate=todate;
     //alert("todate"+todate);
     var frmDay = fday[0];
     var frmMonth = fday[1];
     var frmYear =fday[2];
    /* var frmDay = document.workdemand.elements["txt_from"+c].value.substr(0,2);
     var frmMonth = document.workdemand.elements["txt_from"+c].value.substr(3,2);
     var frmYear = document.workdemand.elements["txt_from"+c].value.substr(6,4)*/
     var frmday=new Date(frmYear,frmMonth-1,frmDay);
     tday=todate.split("/");
     var toDay = tday[0];
     var toMonth= tday[1];
     var toYear = tday[2];
  	/* var toDay = todate.value.substr(0,2);
     var toMonth = todate.value.substr(3,2);
     var toYear = todate.value.substr(6,4)*/
	 var today=new Date(toYear,toMonth-1,toDay);
       //alert("frmday"+frmday);
       //alert("today"+today);
	
       if (frmday<today)
	     {
		   alert ("Date Closure Should Not Be LesserThan Opening Date");
                   document.frmOffice.txtDateOfClosure.focus();
		   //todate.value=""
		   //todate.focus();
		   
         
	   
     }
}