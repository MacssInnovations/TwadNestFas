var seq=0;
var Bank_popup_flag;
/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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
////////////////////////////////////////

function byUnitAndOfficeChange()
{
    document.getElementById("txtAmount").value="";
    doFunction('PendingReceipts','null');
}

var winAcc_Bank_No;     // Fteching Account Head No and Bank  No

function MainAccNopopup()
{
    Bank_popup_flag=true;
    if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) 
    {
       winAcc_Bank_No.resizeTo(500,500);
       winAcc_Bank_No.moveTo(250,250); 
       winAcc_Bank_No.focus();
    }
    else
    {
        winAcc_Bank_No=null
    }
    var txtRem_Type=document.getElementById("txtRem_Type").value;  
    var txtModule_Type="MF006";
    var cr_dr_indi="DR";
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    winAcc_Bank_No= window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_AccNo_Popup_Revised.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&txtModule_Type="+txtModule_Type+"&cr_dr_indi="+cr_dr_indi+"&txtRem_Type="+txtRem_Type,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAcc_Bank_No.moveTo(250,250);  
    winAcc_Bank_No.focus();
}

function doParentAcc_NO(Acc_Head_Code,Bank_Acc_No,bankid,br_id,B_name)
{
   if(Bank_popup_flag==true)
   {
       document.getElementById("txtCash_Acc_code").value=Acc_Head_Code;
       document.getElementById("txtBankAccountNo").value=Bank_Acc_No;
       document.getElementById("txtBankId").value=bankid;
       document.getElementById("txtBranchId").value=br_id;
       document.getElementById("txtBankName").value=B_name;
       Bank_popup_flag="";
       return true;
   }
  else if(Bank_popup_flag==false)
  {
       Bank_popup_flag="";
       return true;
   }
}
window.onunload=function()
{
if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) winAcc_Bank_No.close();

}
 
////////////////////////////////////////////////////////

function doFunction(Command,param)
{   
 
      if(Command=="PendingReceipts")
        {  
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.getElementById("txtCrea_date").value;
            var url="../../../../../CashRemit_Create.view?Command=PendingReceipts&cmbAcc_UnitCode="+cmbAcc_UnitCode
                    +"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
            if(txtCrea_date.length!=0)
            {
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
        }
        else if(Command=='GetBankInfo')
        {
            //cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            txtCash_Acc_code=document.getElementById("txtCash_Acc_code").value
            var url="../../../../../Receipt_SL.view?Command=GetBankInfo&txtCash_Acc_code="+txtCash_Acc_code+"&cmbOffice_code="+cmbOffice_code;//+"&cmbOffice_code="+cmbOffice_code;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
}


/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             //alert(Command)
            if(Command=="PendingReceipts")
            {
                PendingReceipts(baseResponse);
            }
            else if(Command=="GetBankInfo")
            {
                GetBankInfo(baseResponse);
            }
        }
    }
}

function GetBankInfo(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {  
        var bankID=baseResponse.getElementsByTagName("bankID")[0].firstChild.nodeValue;
        var branchID=baseResponse.getElementsByTagName("branchID")[0].firstChild.nodeValue;
        var bankAccNo=baseResponse.getElementsByTagName("bankAccNo")[0].firstChild.nodeValue;
        var bankName=baseResponse.getElementsByTagName("bankName")[0].firstChild.nodeValue;
        
        document.getElementById("txtBankAccountNo").value=bankAccNo;
        document.getElementById("txtBankName").value=bankName;
        document.getElementById("txtBankId").value=bankID;
        document.getElementById("txtBranchId").value=branchID;
     }
     else 
     {
        alert("Bank details not found");
        document.getElementById("txtBankAccountNo").value="";
        document.getElementById("txtBankName").value="";
        document.getElementById("txtBankId").value="";
        document.getElementById("txtBranchId").value="";
     }
}

function PendingReceipts(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=="success")
     {
        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        
        var RecNo=baseResponse.getElementsByTagName("Rec_No");
        
        var items=new Array();
        //var Remitted_amount=baseResponse.getElementsByTagName("Remitted_amount")[0].firstChild.nodeValue;
        // var Acc_headOf_Receipt=baseResponse.getElementsByTagName("Acc_headOf_Receipt")[0].firstChild.nodeValue;
        
        //document.getElementById("txtAmount").value=Remitted_amount;
        //document.getElementById("txtAcc_headOf_Receipt").value=Acc_headOf_Receipt;      // Careful This is Hidden in Form
        
        var Remitted_amount=0;
        
        for(var k=0;k<RecNo.length;k++)
        {
            
             items[0]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;   
             items[1]=baseResponse.getElementsByTagName("Rec_Date")[k].firstChild.nodeValue;   
             items[2]=baseResponse.getElementsByTagName("Rec_From")[k].firstChild.nodeValue;
             items[3]=baseResponse.getElementsByTagName("Total_amt")[k].firstChild.nodeValue;
             
            // Here find the total amount
             Remitted_amount=parseFloat(Remitted_amount)+parseFloat(items[3]);
             
             if( items[2]=="null")
              items[2]="Name not found";
              
              seq=parseInt(seq)+1;
              
              var mycurrent_row=document.createElement("TR");
                mycurrent_row.id=seq;                               // assign row id
                
                     cell2=document.createElement("TD");
                    
                     var sel="";            // variable to find checked receipts and automatically calculated the amount in cal_amount() function 
                     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
                     {
                        sel=document.createElement("<INPUT type='checkbox' name='sel' id='sel' checked value="+items[0]+" onclick='cal_amount("+seq+")'>");
                     }
                     else
                     {    
                       sel=document.createElement("input");     // serial number generation
                       sel.type="checkbox";             
                       sel.name="sel";
                       sel.id="sel";
                       sel.checked=true;
                       sel.setAttribute('onclick','cal_amount('+seq+')');
                       sel.value=items[0];                          
                     }
                   cell2.appendChild(sel);
                   mycurrent_row.appendChild(cell2);
                       
                       
                var cell2;
                
                        cell2=document.createElement("TD");
        
                    //alert("hello "+i+"  " + items[i])
                          var Rec_No=document.createElement("input");
                          Rec_No.type="hidden";
                          Rec_No.name="Rec_No";
                          Rec_No.value=items[0];
                          cell2.appendChild(Rec_No);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                           
                     cell2=document.createElement("TD");
                          var Rec_Date=document.createElement("input");
                          Rec_Date.type="hidden";
                          Rec_Date.name="Rec_Date";
                          Rec_Date.value=items[1];
                          cell2.appendChild(Rec_Date); 
                          var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                        
                     cell2=document.createElement("TD");
                          /*var CR_DR_type=document.createElement("input");
                          CR_DR_type.type="hidden";
                          CR_DR_type.name="CR_DR_type";
                          CR_DR_type.value=items[2];
                          cell2.appendChild(CR_DR_type);*/
                          var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                     cell2=document.createElement("TD");
                          /*var CR_DR_type=document.createElement("input");
                          CR_DR_type.type="hidden";
                          CR_DR_type.name="CR_DR_type";
                          CR_DR_type.value=items[2];
                          cell2.appendChild(CR_DR_type);*/
                          var currentText=document.createTextNode(items[3]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                        
              tbody.appendChild(mycurrent_row);
           
        }
          document.getElementById("txtAmount").value=Remitted_amount;  
        // Actually above code used for showing the total amount in the field of document.getElementById("txtAmount")  ,, 
        // bcoz previously all amount paid in one challon.. but later it is chosen by user so one checkbox added..
        // based on the checked vouchers , amount calculated here.. so it may change when you click the checkbox..
       
        
     }
     else if(flag=="failure")
     {
         var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        alert("No Receipt Found for Remittance");
     }
}

//////////////////////////////////////////////////////////////////////////////////
//   CALCULATE AMOUNT 
/////////////////////////////////////////////////////////////////////////////////

function cal_amount(getId)
{
    var getrow=document.getElementById(getId);
    var get_cells=getrow.cells;
   
    /*
     *  If it is checked by user Add the amount to Total Amount for that row 
     *  If it is unchecked by user Subtract the amount from Total Amount for that row
     */ 
    if(get_cells.item(0).firstChild.checked==true)          
    {   
       document.getElementById("txtAmount").value=parseFloat(document.getElementById("txtAmount").value)+parseFloat(get_cells.item(4).firstChild.nodeValue);  
    }
    else if(get_cells.item(0).firstChild.checked==false)           
    {   
       document.getElementById("txtAmount").value=parseFloat(document.getElementById("txtAmount").value)-parseFloat(get_cells.item(4).firstChild.nodeValue);  
    }
}



function call_clr()
{
   /*
    document.getElementById("txtCash_Acc_code").value="";
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBranchId").value="";
    */
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
}
function clrForm()
{
call_clr();
document.getElementById("butSub").disabled=false;
}

function checkNull()
{
        var tbody=document.getElementById("grid_body");
           //alert("tbody.rows.length :"+tbody.rows.length);   
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");
           // document.getElementById("txtAcc_HeadDesc").focus();
            return false;    
        }
        if(document.getElementById("cmbOffice_code").value=="")
        {
            alert("Select the Office Code");
            //document.getElementById("cmbOffice_code").focus();
            return false;
        }
                
        if(document.getElementById("txtCrea_date").value.length==0)
        {
            alert("Enter the Date of Creation");
            //document.getElementById("txtCrea_date").focus();
            return false;    
        }
              
        if(document.getElementById("txtCash_Acc_code").value.length==0 || document.getElementById("txtCash_Acc_code").value==0)
        {
            alert("Enter the Collection A/c Code");
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
        if(document.getElementById("txtBankAccountNo").value.length==0 || document.getElementById("txtBankAccountNo").value==0)
        {
            alert("Enter the Account Number");
            //document.getElementById("txtRecei_from").focus();
            return false;    
        }
        
        if(document.getElementById("txtBankId").value.length==0 || document.getElementById("txtBankId").value==0)
        {
            alert("Bank Id not populated in General");
            //document.getElementById("txtAmount").focus();
            return false;    
        }
        
        if(document.getElementById("txtBranchId").value.length==0 || document.getElementById("txtBranchId").value==0) 
        {
            alert("Branch Id not populated in General");
            //document.getElementById("txtAmount").focus();
            return false;    
        }

        if(tbody.rows.length==0)
        {
            alert("There is no voucher found for Remittance");
            //document.getElementById("txtAmount").focus();
            return false; 
        }
        if(document.getElementById("txtAmount").value.length==0)
        {
            alert("Amount not Populated");
            return false;
        }
    document.getElementById("butSub").disabled=true;
    return true;
}



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                        //alert(url);
                 var req=getTransport();
                 req.open("GET",url,true); 
                 req.onreadystatechange=function()
                 {
                   check_TB(req,fromcal_dateCtrl);
                 }   
                 req.send(null);
            }
    }
}

function call_date(dateCtrl)                        // TB_checking 
{
    call_clr();
    if(checkdt(dateCtrl))
    {
        //doFunction('check_TB',dateCtrl.value);
         var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         var cmbOffice_code=document.getElementById("cmbOffice_code").value;
         var TB_date=dateCtrl.value;
       
         if(dateCtrl.value.length!=0)
         {
             var url="../../../../../Receipt_SL.view?Command=check_TB&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
             var req=getTransport();
             req.open("GET",url,true); 
             req.onreadystatechange=function()
             {
               check_TB(req,dateCtrl);
             }   
             req.send(null);
       }
        //doFunction('load_Receipt_No','null');
    }
    else
    {
      //document.getElementById("txtReceipt_No").value="";
    }
}



function check_TB(req,dateCtrl)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
              {
                 doFunction('PendingReceipts','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    var tbody=document.getElementById("grid_body");
                    var t=0;
                    for(t=tbody.rows.length-1;t>=0;t--)
                    {
                       tbody.deleteRow(0);
                    }
                    //document.getElementById("txtReceipt_No").value="";
               }
               else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // doFunction('PendingReceipts','null'); 
               }
            dateCheck(dateCtrl); 
        }
    }
}




///////////////////////////////////////////Date validation on 04-01-2018 //////////////////////

function dateCheck(datechk)
{
	//alert("WELCOME!.........");
	
	var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    var cmbOffice_code=document.getElementById("cmbOffice_code").value;
    //var txtCrea_date=document.getElementById("txtCrea_date").value;
    var txtCrea_date=datechk.value;
    
    if(datechk.value.length!=0)
    {
    var url="../../../../../Receipt_SL.view?Command=check_Date&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
    //alert("URL===>"+url);
    var req=getTransport();
    req.open("GET",url,true); 
    req.onreadystatechange=function()
    {
      check_Date(req,datechk);
    } ;  
    req.send(null);
    }

}
function check_Date(req,datechk)
{
 if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            //alert("Flag----->"+flag);
            if(flag=="success")
              {
                // doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
              }
            else if(flag=="failure")
            {
            	datechk.value=""; 
            	alert("Document Date is Less than DATE_EFFECTIVE_FROM");
            	datechk.focus();
            	document.getElementById("butSub").disabled=true;
            	
            	document.getElementById("txtReceipt_No").value="";
                 
            }
            else if(flag=="success1")
            {
               //doFunction('load_Receipt_No','null');                 //return true;
            	document.getElementById("butSub").disabled=false;
            }
           else if(flag=="failure1")
           {
        	  alert("Document Date is Greater than DATE_OF_CLOSURE");
        	  datechk.value=""; 
          		//alert("Document Date is Less than DATE_ALLOWED_UPTO date");
          		datechk.focus();
          		document.getElementById("butSub").disabled=true;
          		document.getElementById("txtReceipt_No").value="";
           }
           else 
        	   {
        	    datechk.value=""; 
        	    alert("Date Value is Null");
           		datechk.focus();
           		document.getElementById("butSub").disabled=true;
           		document.getElementById("txtReceipt_No").value="";
        	   }
        }
    }
}

//added this to check for the cases :if the difference between Remittance Date and Receipt Date is 0 or >1 
//if the Remittace Date is >1 and it it is not coming under the general/Local Holiday msg must say that receipt must be remiited
function  checkDateDiifference()
{
//      alert("check Date Diifference....");
      var accunit_id=document.getElementById("cmbAcc_UnitCode").value;
      var acc_officeid=document.getElementById("cmbOffice_code").value;
      var tbody=document.getElementById("grid_body");
      //alert("No of rows in the grid **"+tbody.rows.length);
      var r=0;
      for(r=1;r<=tbody.rows.length;r++)
      {
              //alert("inside");
              var getrow=document.getElementById(r);
              
              var get_cells=getrow.cells;
             //alert("testtttttttt"+get_cells.item(0).firstChild.checked);
              /*
               *  if the row is checked before submitting, the receipt date is checked with the remittance date 
               *  if the differece is 0 or =1 No Problem
               *  If it is >1 check whether the day is holiday then it should be remitted on the immediate next workinf Day,otherwise error msg should be displayed
               */ 
              if(get_cells.item(0).firstChild.checked==true)          
              {   
                 var receipt_date=get_cells.item(2).lastChild.nodeValue;
//                 alert("Receipt Date from Grid *********"+receipt_date);
                 var remit_date=document.getElementById("txtCrea_date").value;
                  s1=remit_date.split("/");
                 //alert("String S1 Day*****"+s1[0]+"month** "+s1[1]+"Year ***"+s1[2]);
                 s2=receipt_date.split("/");
                // alert("String S2 Day*****"+s2[0]+"month** "+s2[1]+"Year ***"+s2[2]);
                 
                 if((s1[2]==s2[2]) && (s1[1]==s2[1]))
                  {
                      var diffindate=s1[0]-s2[0];
                      //alert("Difference in Date***"+diffindate);
                  }
                  else
                  {
                      alert("Remittance to be done within the current month");
                  }
                  if(diffindate>1)
                  {
                      //Check whether the Remittance Date is a National or Bank Holiday..........
                      var url="../../../../../CashRemit_Create.view?Command=CheckHoliday&remitdate="+remit_date+"&acc_unitid="+accunit_id+"&acc_officeid="+acc_officeid;
//                      alert(url);
                      var req=getTransport();
                      req.open("GET",url,true); 
                      req.onreadystatechange=function()
                      {
                         handleResponseNew(req);
                      }   
                              req.send(null);
                  }
                  else if((diffindate==0) || (diffindate==1))
                  {
                          alert("Remittance to be done within tomoroow if not a Holiday..");
                          //document.getElementById("txtCrea_date").value="";
                          return true;
                  }
              }
              else if(get_cells.item(0).firstChild.checked==false)           
              {   
                  alert("Please select the Receipt to be remitted...");
              }
          return false;
      }
      return true;
}
function handleResponseNew(req)
{  
  if(req.readyState==4)
  {
      if(req.status==200)
      {   
          var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          var tagcommand=baseResponse.getElementsByTagName("command")[0];
          var Command=tagcommand.firstChild.nodeValue;
          alert(Command);
          if(Command=="CheckHoliday")
          {
              CheckHoliday(baseResponse);
          }
      }
  }
}
function CheckHoliday(baseResponse)
{
   var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  // alert(flag);
   if(flag=="success")
   {
     // var RecNo=baseResponse.getElementsByTagName("holidays").length;
     var holiday_date=baseResponse.getElementsByTagName("Holiday_Date")[0].firstChild.nodeValue;
      var reason=baseResponse.getElementsByTagName("Reason")[0].firstChild.nodeValue;
      
      alert("The Remiited Date "+holiday_date+" is a Holiday...Reason is "+reason+" so enter the Remittance Date again");
      document.getElementById("txtCrea_date").value="";
      clrForm();
   }
   else if(flag=="failure")
   {
      alert("The Remiited Date "+holiday_date+" is not a Holiday...Prepare the Challan");
      document.getElementById("butSub").focus();
   }
}

