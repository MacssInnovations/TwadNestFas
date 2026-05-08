var currentlyEditing=0;
var serial_number=0;
var branch=0;
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


function clearAllBank()
{

        //document.frmOffice.txtSl_No.value="";
        document.frmOffice.cmbBank.selectedIndex=0;
        document.frmOffice.cmbBranch.selectedIndex=0
        document.frmOffice.txtBank_Address1.disabled=false;
        document.frmOffice.txtBank_Address1.value="";
        document.frmOffice.txtBank_Address1.disabled=true;
        document.frmOffice.txtBank_Address2.disabled=false;
        document.frmOffice.txtBank_Address2.value="";
        document.frmOffice.txtBank_Address2.disabled=true;
        document.frmOffice.txtBank_Address3.disabled=false;
        document.frmOffice.txtBank_Address3.value="";
        document.frmOffice.txtBank_Address3.disabled=true;
        document.frmOffice.txtMicr_Code.disabled=false;
        document.frmOffice.txtMicr_Code.value="";
        document.frmOffice.txtMicr_Code.disabled=true;
        document.frmOffice.txtAcc_No.value="";
        document.frmOffice.cmbAcc_Type.selectedIndex=0;
        document.frmOffice.cmbOperationalMode.selectedIndex=0;
        document.frmOffice.txtDateOfJoining.value="";
        document.frmOffice.txtHead_No.value="";
        document.frmOffice.txtInitial_Deposit.value="";
        
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
      if((whichCode>=48 && whichCode<=57) ||(whichCode==189))
      {
          return true;
      }
      var str=param.value;
      param.value=str.substring(0,str.length-1);
      return false;              
}  

function isIntegerAndDot(param,e)
{     
       var nav4 = window.Event ? true : false;
       //alert(e.keyCode);
       if (nav4)    // Navigator 4.0x
            var whichCode = e.which
       else         // Internet Explorer 4.0x
            var whichCode = e.keyCode
      if((whichCode>=48 && whichCode<=57) ||(whichCode==189) ||(whichCode==190))
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
                  if((document.frmOffice.txtOffice_Id.value=="") || (document.frmOffice.txtOffice_Id.value.length<=0))
                  {
                    alert("Please Enter Office_Id");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                  }
                  //alert(document.frmOffice.cmbBank.value);
                  //alert(document.frmOffice.cmbBank.value.length);
                  /*if((document.frmOffice.cmbBank.value=="") || (document.frmOffice.cmbBank.value.length<=0))
                  {
                    alert("Please Select Bank Name");
                    document.frmOffice.cmbBank.focus();
                    return false;
                    
                  }
                  //alert(document.frmOffice.cmbBranch.value);
                  //alert(document.frmOffice.cmbBranch.value.length);
                  if((document.frmOffice.cmbBranch.value=="") || (document.frmOffice.cmbBranch.value.length<=0))
                  {
                    alert("Please Select Branch Name");
                    document.frmOffice.cmbBranch.focus();
                    return false;
                    
                  }*/
                  /*if((document.frmOffice.txtDateOfClosure.value=="") || (document.frmOffice.txtDateOfClosure.value.length<=0))
                  {
                    alert("Please Enter Date ");
                    document.frmOffice.txtDateOfClosure.focus();
                    return false;
                    
                  }*/
                  
                  /*var tbody=document.getElementById("tblList");
                  var length=tbody.rows.length;
                  //alert(length);
                  if(length<=0)
                  {
                    
                    alert("There is No Values in Grid");
                    return false;
                  
                  }*/
                  
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
                
                function nullBank()
                {
                
                  if((document.frmOffice.txtSl_No.value=="") || (document.frmOffice.txtSl_No.value.length<=0))
                  {
                    alert("Please Select OfficeId for Sl_No");
                    document.frmOffice.txtOffice_Id.focus();
                    return false;
                    
                  }
                  //alert(document.frmOffice.cmbBank.value);
                  //alert(document.frmOffice.cmbBank.value.length);
                   if((document.frmOffice.cmbBank.value=="") || (document.frmOffice.cmbBank.value.length<=0) || (document.frmOffice.cmbBank.value==0))
                  {
                    alert("Please Select BankName");
                    document.frmOffice.cmbBank.focus();
                    return false;
                    
                  }
                   if((document.frmOffice.cmbBranch.value=="") || (document.frmOffice.cmbBranch.value.length<=0) || (document.frmOffice.cmbBranch.value==0))
                  {
                    alert("Please Select Branch Name");
                    document.frmOffice.cmbBranch.focus();
                    return false;
                  }
                  
                  if((document.frmOffice.cmbAcc_Type.value=="") || (document.frmOffice.cmbAcc_Type.value.length<=0) || (document.frmOffice.cmbAcc_Type.value==0))
                  {
                    alert("Please Select Account Type");
                    document.frmOffice.cmbAcc_Type.focus();
                    return false;
                    
                  }
                   if((document.frmOffice.cmbOperationalMode.value=="") || (document.frmOffice.cmbOperationalMode.value.length<=0) || (document.frmOffice.cmbOperationalMode.value==0))
                  {
                    alert("Please Select Operational Mode");
                    document.frmOffice.cmbOperationalMode.focus();
                    return false;
                  }
                  
                  if((document.frmOffice.txtAcc_No.value=="") || (document.frmOffice.txtAcc_No.value.length<=0))
                  {
                    alert("Please Enter Account Number");
                    document.frmOffice.txtAcc_No.focus();
                    return false;
                  }
                  
                  if((document.frmOffice.txtDateOfJoining.value=="") || (document.frmOffice.txtDateOfJoining.value.length<=0))
                  {
                    alert("Please Enter DateOfJoining");
                    document.frmOffice.txtDateOfJoining.focus();
                    return false;
                  }
                  
                  if((document.frmOffice.txtHead_No.value=="") || (document.frmOffice.txtHead_No.value.length<=0))
                  {
                    alert("Please Enter Head Number");
                    document.frmOffice.txtHead_No.focus();
                    return false;
                  }
                  if((document.frmOffice.txtInitial_Deposit.value=="") || (document.frmOffice.txtInitial_Deposit.value.length<=0))
                  {
                    alert("Please Enter Initial Deposit");
                    document.frmOffice.txtInitial_Deposit.focus();
                    return false;
                  }
                  
                  
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
        url="../../../../../ServletOfficeUpdateBank.con?command=Load&OfficeId="+Office_Id;
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
        //var email=ValidateForm();
        //alert(email);
        var flag=nullBank();
        //alert(flag);
        if(flag==true)
        {
        var Office_Id="";
        var Sl_No="";
        var Bank_Name="";
        var Branch_Name="";
        var Micr_Code="";
        var AccNo="";
        var AccType="";
        var OperationalMode="";
        var DateJoining="";
        var AccHeadNo="";
        var InitialDeposit="";
        var NatureWork="";
        var Email="";
        Office_Id=document.frmOffice.txtOffice_Id.value;
        Sl_No=document.frmOffice.txtSl_No.value;
        Bank_Name=document.frmOffice.cmbBank.value;
        var Bank_Name1=document.frmOffice.cmbBank.options[document.frmOffice.cmbBank.selectedIndex].text;
        Branch_Name=document.frmOffice.cmbBranch.value;
        var Branch_Name1=document.frmOffice.cmbBranch.options[document.frmOffice.cmbBranch.selectedIndex].text;
        Micr_Code=document.frmOffice.txtMicr_Code1.value;
        AccNo=document.frmOffice.txtAcc_No.value;
        AccType=document.frmOffice.cmbAcc_Type.value;
        OperationalMode=document.frmOffice.cmbOperationalMode.value;
        DateJoining=document.frmOffice.txtDateOfJoining.value;
        AccHeadNo=document.frmOffice.txtHead_No.value;
        InitialDeposit=document.frmOffice.txtInitial_Deposit.value;
        
        
        
        //url="../../../../../ServletOfficeWingInsert.con?command=Add&Office_Id="+Office_Id;
        
        
        //Append a row
        i=0;
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
        var cell10=document.createElement("TD");
        var cell11=document.createElement("TD");
        var cell12=document.createElement("TD");
       
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
        
        var cmbbankname=document.createTextNode(Bank_Name1);
        cell3.appendChild(cmbbankname);
        
        var hidden2=document.createElement("input");
        hidden2.type="hidden";
        hidden2.name="bankname";
        hidden2.value=Bank_Name;
        cell3.appendChild(hidden2);
        mycurrent_row.appendChild(cell3);
        
        var cmbbranchname=document.createTextNode(Branch_Name1);
        cell4.appendChild(cmbbranchname);
        
        var hidden3=document.createElement("input");
        hidden3.type="hidden";
        hidden3.name="branchname";
        hidden3.value=Branch_Name;
        cell4.appendChild(hidden3);
        mycurrent_row.appendChild(cell4);
        
        
        var txtmicrcode=document.createTextNode(Micr_Code);
        cell5.appendChild(txtmicrcode);
        
        var hidden4=document.createElement("input");
        hidden4.type="hidden";
        hidden4.name="micrcode";
        hidden4.value=Micr_Code;
        cell5.appendChild(hidden4);
        mycurrent_row.appendChild(cell5);
        
        
        var txtaccno=document.createTextNode(AccNo);
        cell6.appendChild(txtaccno);
         
        var hidden5=document.createElement("input");
        hidden5.type="hidden";
        hidden5.name="accno";
        hidden5.value=AccNo;
        cell6.appendChild(hidden5);
        mycurrent_row.appendChild(cell6);
        
        
        var cmbacctype=document.createTextNode(AccType);
        cell7.appendChild(cmbacctype);
        
        var hidden6=document.createElement("input");
        hidden6.type="hidden";
        hidden6.name="acctype";
        hidden6.value=AccType;
        cell7.appendChild(hidden6);
        mycurrent_row.appendChild(cell7);
        
        var cmboperationalmode=document.createTextNode(OperationalMode);
        cell8.appendChild(cmboperationalmode);
        
        var hidden7=document.createElement("input");
        hidden7.type="hidden";
        hidden7.name="operationalmode";
        hidden7.value=OperationalMode;
        cell8.appendChild(hidden7);
        mycurrent_row.appendChild(cell8);
        
        var txtDateCreated=document.createTextNode(DateJoining);
        cell9.appendChild(txtDateCreated);
        
        var hidden8=document.createElement("input");
        hidden8.type="hidden";
        hidden8.name="datecreated";
        hidden8.value=DateJoining;
        cell9.appendChild(hidden8);
        mycurrent_row.appendChild(cell9);
        
        var txtheadaccno=document.createTextNode(AccHeadNo);
        cell10.appendChild(txtheadaccno);
        
        var hidden9=document.createElement("input");
        hidden9.type="hidden";
        hidden9.name="headaccno";
        hidden9.value=AccHeadNo;
        cell10.appendChild(hidden9);
        mycurrent_row.appendChild(cell10);
        
        
        var txtinitialdeposit=document.createTextNode(InitialDeposit);
        cell11.appendChild(txtinitialdeposit);
        
        var hidden10=document.createElement("input");
        hidden10.type="hidden";
        hidden10.name="initialdeposit";
        hidden10.value=InitialDeposit;
        cell11.appendChild(hidden10);
        mycurrent_row.appendChild(cell11);
        
        
         
        tbody.appendChild(mycurrent_row);
        Sl_No++;
        
        //Clear the fields
        document.frmOffice.txtSl_No.value=Sl_No;
        document.frmOffice.txtSl_No.disabled=true;
        document.frmOffice.cmbBank.selectedIndex=0;
        document.frmOffice.cmbBranch.selectedIndex=0;
        document.frmOffice.txtBank_Address1.disabled=false;
        document.frmOffice.txtBank_Address1.value="";
        document.frmOffice.txtBank_Address1.disabled=true;
        document.frmOffice.txtBank_Address2.disabled=false;
        document.frmOffice.txtBank_Address2.value="";
        document.frmOffice.txtBank_Address2.disabled=true;
        document.frmOffice.txtBank_Address3.disabled=false;
        document.frmOffice.txtBank_Address3.value="";
        document.frmOffice.txtBank_Address3.disabled=true;
        document.frmOffice.txtMicr_Code.disabled=false;
        document.frmOffice.txtMicr_Code.value="";
        document.frmOffice.txtMicr_Code.disabled=true;
        document.frmOffice.txtAcc_No.value="";
        document.frmOffice.cmbAcc_Type.selectedIndex=0;
        document.frmOffice.cmbOperationalMode.selectedIndex=0;
        document.frmOffice.txtDateOfJoining.value="";
        document.frmOffice.txtHead_No.value="";
        document.frmOffice.txtInitial_Deposit.value="";
      }  
        
    }
    else if(command=="Update")
    {
        //alert("Update");
        //alert("currnetlyEditing:"+currentlyEditing);
        var trow=document.getElementById(""+currentlyEditing);
        var cells=trow.cells; 
        
        /*cells.item(1).firstChild.nodeValue=document.frmOffice.txtSl_No.value;
        cells.item(1).lastChild.value=document.frmOffice.txtSl_No.value;*/
        
        cells.item(2).firstChild.nodeValue=document.frmOffice.cmbBank.options[document.frmOffice.cmbBank.selectedIndex].text;
        cells.item(2).lastChild.value=document.frmOffice.cmbBank.value;
        
        cells.item(3).firstChild.nodeValue=document.frmOffice.cmbBranch.options[document.frmOffice.cmbBranch.selectedIndex].text;
        cells.item(3).lastChild.value=document.frmOffice.cmbBranch.value;
        
        //cells.item(4).firstChild.nodeValue=document.frmOffice.txtDateCreated.value;
        //cells.item(4).lastChild.value=document.frmOffice.txtDateCreated.value;
        
        cells.item(5).firstChild.nodeValue=document.frmOffice.txtAcc_No.value;
        cells.item(5).lastChild.value=document.frmOffice.txtAcc_No.value;
        
        cells.item(6).firstChild.nodeValue=document.frmOffice.cmbAcc_Type.options[document.frmOffice.cmbAcc_Type.selectedIndex].text;
        //alert(document.frmOffice.cmbAcc_Type.value);
        cells.item(6).lastChild.value=document.frmOffice.cmbAcc_Type.value;
        
        cells.item(7).firstChild.nodeValue=document.frmOffice.cmbOperationalMode.options[document.frmOffice.cmbOperationalMode.selectedIndex].text;
        //alert(document.frmOffice.cmbOperationalMode.value);
        cells.item(7).lastChild.value=document.frmOffice.cmbOperationalMode.value;
        
        cells.item(8).firstChild.nodeValue=document.frmOffice.txtDateOfJoining.value;
        cells.item(8).lastChild.value=document.frmOffice.txtDateOfJoining.value;
        
        
        cells.item(9).firstChild.nodeValue=document.frmOffice.txtHead_No.value;
        cells.item(9).lastChild.value=document.frmOffice.txtHead_No.value;
        
        cells.item(10).firstChild.nodeValue=document.frmOffice.txtInitial_Deposit.value;
        cells.item(10).lastChild.value=document.frmOffice.txtInitial_Deposit.value;
        
        //document.frmOffice.txtSl_No.disabled=false;
        var slno=slnocheck();
        //var slno=document.frmOffice.txtSl_No.value;
        
        document.frmOffice.txtSl_No.value=++slno;
        //document.frmOffice.txtSl_No.disabled=true;
        
        clearAllBank();
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
        url="../../../../../ServletOfficeUpdateBank.con?command=SlNo&OfficeId="+Office_Id;
        //alert(url);
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
        //alert(branch);
        url="../../../../../ServletOfficeUpdateBank.con?command=BankId&BankId="+branch;
        //alert(url);
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
        
        var bank=document.frmOffice.cmbBank.value;
        var branch=document.frmOffice.cmbBranch.value;
        url="../../../../../ServletOfficeUpdateBank.con?command=Branch&BankId="+bank+"&BranchId="+branch;
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
        AddressResponse(req);
        }
        req.send(null);
    
    }
    else if(command=="TableView")
    {
        Office_Id=document.frmOffice.txtOffice_Id.value;
        url="../../../../../ServletOfficeUpdateBank.con?command=TableView&OfficeId="+Office_Id;
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
                              //alert(district);
                              //district=district.trim();
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
                              //document.frmOffice.cmbDistrict1.value=district;
                              //document.frmOffice.cmbDistrict1.disabled=true;
                              
                              callServer1("SlNo",null);
                              
                          }
                          
                      }   
            }
        }
    }
}

function callBank(Bank,Branch)
    {            
        url="../../../../../ServletOfficeUpdateBank.con?command=BankId&BankId="+Bank+"&Branch="+Branch;
        //alert("called");
        var req=getTransport();
        req.open("POST",url,true);        
        req.onreadystatechange=function()
        {
            BranchResponse(req);
        }
        req.send(null);
    }


// code for loading the values from the table to the input boxes
    // functionality for edit anchor
    function loadValuesFromTable(rid)
    {  
    
      var r=document.getElementById(rid);      
      var rcells=r.cells;
      currentlyEditing=rcells.item(1).firstChild.nodeValue;
      //alert("currentlyEditing in Load"+currentlyEditing);
      //alert(rcells.item(1).lastChild.value);
      //alert(rcells.item(2).lastChild.value);
      //alert(rcells.item(3).lastChild.value);
      //alert(rcells.item(4).lastChild.value);
      //alert(rcells.item(5).lastChild.value);
      //alert(rcells.item(6).lastChild.value);
      //alert(rcells.item(7).lastChild.value);
      //alert(rcells.item(8).lastChild.value);
      
      document.frmOffice.txtSl_No.value=rcells.item(1).firstChild.nodeValue;
      document.frmOffice.cmbBank.value=rcells.item(2).lastChild.value;
      var Bank=rcells.item(2).lastChild.value;
      var Branch=rcells.item(3).lastChild.value;
      callBank(Bank,Branch);      
      //callServer1('Branch',null);
      //document.frmOffice.txtDateCreated.value=rcells.item(4).firstChild.nodeValue;
      document.frmOffice.txtAcc_No.value=rcells.item(5).firstChild.nodeValue;
      document.frmOffice.cmbAcc_Type.value=rcells.item(6).lastChild.value;
      document.frmOffice.cmbOperationalMode.value=rcells.item(7).lastChild.value;
      document.frmOffice.txtDateOfJoining.value=rcells.item(8).firstChild.nodeValue;
      document.frmOffice.txtHead_No.value=rcells.item(9).firstChild.nodeValue;
      document.frmOffice.txtInitial_Deposit.value=rcells.item(10).firstChild.nodeValue;
      
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
                              //document.frmOffice.txtSl_No.disabled=false;
                              document.frmOffice.txtSl_No.value=++id;
                              //document.frmOffice.txtSl_No.disabled=true;
                              callServer1("TableView",null);
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
                          var branch=response.getElementsByTagName("Branch")[0].firstChild.nodeValue;
                          //alert(branch);
                          if(branch)
                          {
                              cmbBranch.value=branch;
                              callServer1('Branch',null);
                          }
                      }
                }
            }
   }         
   
   
   
   
   function AddressResponse(req)
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
                          
                          
                          for(var i=0;i<value.length;i++)
                          {
                              var tmpoption=value.item(i);
                              var address1=tmpoption.getElementsByTagName("Address1")[0].firstChild.nodeValue;
                              var address2=tmpoption.getElementsByTagName("Address2")[0].firstChild.nodeValue;
                              var address3=tmpoption.getElementsByTagName("Address3")[0].firstChild.nodeValue;
                              var district=tmpoption.getElementsByTagName("District")[0].firstChild.nodeValue;
                              var Micr=tmpoption.getElementsByTagName("Micr")[0].firstChild.nodeValue;
                              //alert(address1);
                              //alert(address2);
                              //alert(address3);
                              if(address1!="null")
                              {
                                
                                document.frmOffice.txtBank_Address1.value=address1;
                                document.frmOffice.txtBank_Address11.value=address1;
                                
                              }
                              if(address2!="null")
                              {
                                document.frmOffice.txtBank_Address2.value=address2;
                                document.frmOffice.txtBank_Address22.value=address2;
                              }
                              if(address3!="null")
                              {
                                document.frmOffice.txtBank_Address3.value=address3;
                                document.frmOffice.txtBank_Address33.value=address3;
                              }
                              document.frmOffice.txtMicr_Code.value=Micr;
                              document.frmOffice.txtMicr_Code1.value=Micr;
                              
                              
                              
                              
                              
                          }
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
                
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                      
                      if(flag=="failure")
                      {
                         alert("There is No Values in Grid");
                         //document.frmOffice.txtOffice_Id.focus();
                      }
                      else
                      {
                          var value=response.getElementsByTagName("BankId");
                          
                          //alert(value);
                          
                          for(var i=0;i<value.length;i++)
                          {
                                var tmpoption=value.item(i);
                                var Sl_No=response.getElementsByTagName("SlNo")[i].firstChild.nodeValue;
                                var Bank_Id=response.getElementsByTagName("BankId")[i].firstChild.nodeValue;
                                var Bank_Name=response.getElementsByTagName("BankName")[i].firstChild.nodeValue;
                                var Branch_Id=response.getElementsByTagName("BranchId")[i].firstChild.nodeValue;
                                var Branch_Name=response.getElementsByTagName("BranchName")[i].firstChild.nodeValue;
                                var Micr_Code=response.getElementsByTagName("Micr")[i].firstChild.nodeValue;
                                var AccNo=response.getElementsByTagName("AccNo")[i].firstChild.nodeValue;
                                var AccType=response.getElementsByTagName("AccType")[i].firstChild.nodeValue;
                                var OperationalMode=response.getElementsByTagName("OperationalMode")[i].firstChild.nodeValue;
                                var AccDate=response.getElementsByTagName("AccDate")[i].firstChild.nodeValue;
                                var AccHeadNo=response.getElementsByTagName("AccHead")[i].firstChild.nodeValue;
                                var InitialDeposit=response.getElementsByTagName("InitialDeposit")[i].firstChild.nodeValue;
                                //alert(Bank_Id);
                                //alert(Branch_Id);
                                
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
                                var cell10=document.createElement("TD");
                                var cell11=document.createElement("TD");
                                var cell12=document.createElement("TD");
                               
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
                                
                                var cmbbankname=document.createTextNode(Bank_Name);
                                cell3.appendChild(cmbbankname);
                                
                                var hidden2=document.createElement("input");
                                hidden2.type="hidden";
                                hidden2.name="bankname";
                                hidden2.value=Bank_Id;
                                cell3.appendChild(hidden2);
                                mycurrent_row.appendChild(cell3);
                                
                                var cmbbranchname=document.createTextNode(Branch_Name);
                                cell4.appendChild(cmbbranchname);
                                
                                var hidden3=document.createElement("input");
                                hidden3.type="hidden";
                                hidden3.name="branchname";
                                hidden3.value=Branch_Id;
                                cell4.appendChild(hidden3);
                                mycurrent_row.appendChild(cell4);
                                
                                
                                var txtmicrcode=document.createTextNode(Micr_Code);
                                cell5.appendChild(txtmicrcode);
                                
                                var hidden4=document.createElement("input");
                                hidden4.type="hidden";
                                hidden4.name="micrcode";
                                hidden4.value=Micr_Code;
                                cell5.appendChild(hidden4);
                                mycurrent_row.appendChild(cell5);
                                
                                
                                var txtaccno=document.createTextNode(AccNo);
                                cell6.appendChild(txtaccno);
                                 
                                var hidden5=document.createElement("input");
                                hidden5.type="hidden";
                                hidden5.name="accno";
                                hidden5.value=AccNo;
                                cell6.appendChild(hidden5);
                                mycurrent_row.appendChild(cell6);
                                
                                
                                var cmbacctype=document.createTextNode(AccType);
                                cell7.appendChild(cmbacctype);
                                
                                var hidden6=document.createElement("input");
                                hidden6.type="hidden";
                                hidden6.name="acctype";
                                hidden6.value=AccType;
                                cell7.appendChild(hidden6);
                                mycurrent_row.appendChild(cell7);
                                
                                var cmboperationalmode=document.createTextNode(OperationalMode);
                                cell8.appendChild(cmboperationalmode);
                                
                                var hidden7=document.createElement("input");
                                hidden7.type="hidden";
                                hidden7.name="operationalmode";
                                hidden7.value=OperationalMode;
                                cell8.appendChild(hidden7);
                                mycurrent_row.appendChild(cell8);
                                
                                var txtDateCreated=document.createTextNode(AccDate);
                                cell9.appendChild(txtDateCreated);
                                
                                var hidden8=document.createElement("input");
                                hidden8.type="hidden";
                                hidden8.name="datecreated";
                                hidden8.value=AccDate;
                                cell9.appendChild(hidden8);
                                mycurrent_row.appendChild(cell9);
                                
                                var txtheadaccno=document.createTextNode(AccHeadNo);
                                cell10.appendChild(txtheadaccno);
                                
                                var hidden9=document.createElement("input");
                                hidden9.type="hidden";
                                hidden9.name="headaccno";
                                hidden9.value=AccHeadNo;
                                cell10.appendChild(hidden9);
                                mycurrent_row.appendChild(cell10);
                                
                                
                                var txtinitialdeposit=document.createTextNode(InitialDeposit);
                                cell11.appendChild(txtinitialdeposit);
                                
                                var hidden10=document.createElement("input");
                                hidden10.type="hidden";
                                hidden10.name="initialdeposit";
                                hidden10.value=InitialDeposit;
                                cell11.appendChild(hidden10);
                                mycurrent_row.appendChild(cell11);
                                
                                
                                 
                                tbody.appendChild(mycurrent_row);
                          }
                      }
                }      
                
             }
     }        
     
    