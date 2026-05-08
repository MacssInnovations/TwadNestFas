
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


function checkNull()
{
        if(document.getElementById("cmbAcc_UnitCode").value=="")
        {
            alert("Select the Account Unit code");
            //document.getElementById("txtAcc_HeadDesc").focus();
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
              alert("Enter the Cheque Dishonour Date");
    //document.getElementById("txtCrea_date").focus();
            return false;    
         }
        if(document.getElementById("txtDoc_date").value.length==0)
        {
            alert("Enter the Date of Creation");
            //document.getElementById("txtDoc_date").focus();
            return false;    
        }
        
        if(document.getElementById("txtReceipt_No").value=="")
        {
            alert("Select the Receipt Number");
            //document.getElementById("txtDoc_date").focus();
            return false;    
        }
        if(document.frmCheque_Dishonour.txtCheque_DD2[0].checked==true )
        {
             if(document.frmCheque_Dishonour.txtCheque_DD3[0].checked==true || document.frmCheque_Dishonour.txtCheque_DD3[1].checked==true)
             {
                  if(document.frmCheque_Dishonour.txtCrea_date2.value=="")
                  {
                    alert("Enter Received Date");
                    return false;    
                   }
                   else  if(document.frmCheque_Dishonour.txtCheque_DD_NO2.value=="")
                   {
                    alert("Enter Cheque No");
                    return false;    
                   }
                   else  if(document.frmCheque_Dishonour.txtCheque_DD_date2.value=="")
                   {
                    alert("Enter Cheque Date");
                    return false;    
                   }
             }
           
        }
    return true;
}



function clrForm()
{
   if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
 }
}

function call_clr()
{
    
 document.getElementById("txtReceipt_No").value="";  
 clearGeneral_Detail();
}
function clearGeneral_Detail()
{
    //document.getElementById("txtCB_Year").value="";
    //document.getElementById("txtCB_Month").value="";
    document.getElementById("txtDoc_date").value="";
    document.getElementById("doc_type").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtCheque_No").value="";
    document.getElementById("txtCheque_DD_date").value="";
    document.getElementById("txtCheque_DD_NO2").value="";
    document.getElementById("txtCheque_DD_date2").value="";
    document.getElementById("txtCheque_DD").value="";
    document.getElementById("txtAmount2").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtCrea_date2").value="";
  
}
function check_currentdate()
{
var datefrom=document.frmCheque_Dishonour.txtCrea_date.value;
//alert("the date from is"+datefrom);
var dateto=document.frmCheque_Dishonour.txtCheque_DD_date2.value;
//alert("the date to is"+dateto);
var flag=0;
var d = datefrom.split("/");
var e = dateto.split("/");
if(e[2]<d[2])
{
//alert("newcheque year should be greater than oldcheque Year");
alert("New Cheque Date should be within current cashbook year and month")
flag++;
    document.frmCheque_Dishonour.txtCheque_DD_date2.value="";
    document.frmCheque_Dishonour.txtCheque_DD_date2.focus();
}


if(e[2]==d[2])
{
  if (e[1]<d[1])
  {
   
      alert("New Cheque Date should be within current cashbook year and month")
    flag++;
    document.frmCheque_Dishonour.txtCheque_DD_date2.value="";
    document.frmCheque_Dishonour.txtCheque_DD_date2.focus();
    }
 
     }
    if(flag>0)
    {
     return false
     }

  }
var window_BankAccNumber;
function ListCheq()
    {
    
     if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) 
    {
       window_BankAccNumber.resizeTo(500,500);
       window_BankAccNumber.moveTo(250,250); 
       window_BankAccNumber.focus();
    }
    else
    {
        window_BankAccNumber=null
    }   
           var doc_type=document.getElementById("doc_type").value;
          // alert(doc_type);
          var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_old").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code_old").value;
            var yr=document.getElementById("txtCB_Year").value;
       var mon=document.getElementById("txtCB_Month").value;
       
       if(doc_type=="Bank Receipt"){
     window_BankAccNumber = window.open("../../../../../org/FAS/FAS1/ReceiptSystem/jsps/Bank_Receipt_ListAll_cheq.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
     }
     else if(doc_type=="Fund Receipt"){
      window_BankAccNumber = window.open("../../../../../org/FAS/FAS1/FundReceiptSystem/jsps/Fund_Receipt_ListAll_byOffice_cheq.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
     }
     else if(doc_type=="Fund Receipt HO"){
      window_BankAccNumber = window.open("../../../../../org/FAS/FAS1/FundReceiptSystem/jsps/Fund_Receipt_ListAll_byHO_cheq.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
     }   //  window_BankAccNumber= window.open("BankPayNonBill_ListAll_SL.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"mywindow1","resizable=YES,scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}
  function doParentBankAccNumbers(cheqno,cheqdate,cheqamount)
{
   //alert("inside doparent");
   //alert(cheqno);
    document.getElementById("txtCheque_DD_NO2").value=cheqno;
    document.getElementById("txtCheque_DD_date2").value=cheqdate;
   // document.getElementById("txtBranch_Name").value=BranchName;
    document.getElementById("txtAmount2").value=cheqamount;
    check_currentdate();
    
   // if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
   // if this closed here it won't get result from servlet for bank's branches, so it's closed after get branches
}

function doFunction_voucher(Command,param)
{
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var cmbAcc_UnitCode_old=document.getElementById("cmbAcc_UnitCode_old").value;
       var cmbOffice_code_old=document.getElementById("cmbOffice_code_old").value;
       var txtCB_Year=document.getElementById("txtCB_Year").value;
       var txtCB_Month=document.getElementById("txtCB_Month").value;
       var txtDoc_date= document.getElementById("txtDoc_date").value;
       var doc_type=document.getElementById("doc_type").value;
       var cheq_type=document.getElementById("txtCheque_DD").value;
       var txtReceipt_No=document.getElementById("txtReceipt_No").value;
       var txtamount=document.getElementById("txtAmount").value;
       var txtCheque_No=document.getElementById("txtCheque_No").value
       var che_DD_no=document.getElementById("txtCheque_No").value
       var che_DD_date=document.getElementById("txtCheque_DD_date").value;
       var txtCheque_DD2=document.getElementById("txtCheque_DD2").value;
       var iss_che=document.getElementById("txtCheque_DD").value;
       var iss_che_no=document.getElementById("txtCheque_DD_NO2").value
       var iss_che_date=document.getElementById("txtCheque_DD_date2").value
       var iss_amt=document.getElementById("txtAmount2").value
       var remarks=document.getElementById("txtRemarks").value
       var txtCheque_DD_New=document.getElementById("txtCheq_DD_Issued").value;
       var test=document.frmCheque_Dishonour.txtCheque_DD2.length;
      // alert("sss** length******"+test);
                 for (var k=0; k < document.frmCheque_Dishonour.txtCheque_DD2.length; k++)
                   {
                   if (document.frmCheque_Dishonour.txtCheque_DD2[k].checked)
                      {
                      var rad_val3 = document.frmCheque_Dishonour.txtCheque_DD2[k].value;
                     // alert("rad_val2"+rad_val2);
                      }
                   }
                for (var j=0; j < document.frmCheque_Dishonour.txtCheque_DD3.length; j++)
               {
               if (document.frmCheque_Dishonour.txtCheque_DD3[j].checked)
                  {
                  var rad_val2 = document.frmCheque_Dishonour.txtCheque_DD3[j].value;
                 // alert("rad_val2"+rad_val2);
                  }
               }
            for (var i=0; i < document.frmCheque_Dishonour.txtoption.length; i++)
            {
           if (document.frmCheque_Dishonour.txtoption[i].checked)
              {
              var rad_val = document.frmCheque_Dishonour.txtoption[i].value;
           //   alert("rad_val"+rad_val);
              }
           }
       if(Command=="chequenodetails")
        {  
           //clearGeneral_Detail();
          //  if(txtDoc_date.length!=0)
            {
            var url="../../../../../Cheque_Dishonour_System.view?Command=chequenodetails&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode_old+"&cmbOffice_code="+cmbOffice_code_old;
            //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
      
        else if(Command=="Voucher_Details")
        {  
           // clearGeneral_Detail();
           // alert("txtDoc_date**********"+txtDoc_date);
            var  txtCheque_No=document.getElementById("txtCheque_No").value;
           // if(txtReceipt_No!="")
            {
            if(rad_val=="M"){
            var url="../../../../../Cheque_Dishonour_System.view?Command=Voucher_Details&txtCheque_No="+txtCheque_No+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtDoc_date="+
            txtDoc_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode_old+"&cmbOffice_code="+cmbOffice_code_old;
            }
            if(rad_val=="C"){
            var url="../../../../../Cheque_Dishonour_System_New.view?Command=Voucher_Details&txtCheque_No="+txtCheque_No+"&txtDoc_date="+txtDoc_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode_old+"&cmbOffice_code="+cmbOffice_code_old;
            }
        //    alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }
       else if(Command=="Other_Details")
        {  
           // clearGeneral_Detail();
            
            var  txtCheque_No=document.getElementById("txtCheque_No").value;
            var  txtReceipt_No=document.getElementById("txtReceipt_No").value;
           // if(txtReceipt_No!="")
            {
            if(rad_val=="M"){
            var url="../../../../../Cheque_Dishonour_System.view?Command=Other_Details&txtCheque_No="+txtCheque_No+"&txtReceipt_No="+txtReceipt_No+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtDoc_date="+
            txtDoc_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode_old+"&cmbOffice_code="+cmbOffice_code_old;
            }
            if(rad_val=="C"){
             var url="../../../../../Cheque_Dishonour_System_New.view?Command=Other_Details&txtCheque_No="+txtCheque_No+"&txtReceipt_No="+txtReceipt_No+"&txtDoc_date="+txtDoc_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode_old+"&cmbOffice_code="+cmbOffice_code_old;
            }
           // alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            }         
        }  
    
       
}

/////////////////////////////////////////////   handleResponse()  /////////////////////////////////////////////////////
function handleResponse_voucher(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
           // alert(req.responseTEXT)
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
             
            if(Command=="chequenodetails")
            {
                chequenodetails(baseResponse);
            }
             if(Command=="chequenodetails_cheqwise")
            {
               chequenodetails(baseResponse);
            }      
        
            else if(Command=="Voucher_Details")
            {
               
                vouchernodetails(baseResponse);
            } 
            else if(Command=="Other_Details")
            {
               
                otherdetails(baseResponse);
            } 
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            
        }
    }
}

function  chequenodetails(baseResponse)
{
        
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 var txtCheque_No=document.getElementById("txtCheque_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Cheq_No=baseResponse.getElementsByTagName("Cheq_No");
        
            for(var k=0;k<Cheq_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Cheq_No")[k].firstChild.nodeValue;
                
            }
         
            txtCheque_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Cheque Number--";
            option.value="";
            try
            {
                txtCheque_No.add(option);
            }catch(errorObject)
            {
                txtCheque_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtCheque_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtCheque_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtCheque_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Cheque Number--";
            option.value="";
            try
            {
                txtCheque_No.add(option);
            }catch(errorObject)
            {
                txtCheque_No.add(option,null);
            }
         alert("No Cheque Found");
    }
}
function  vouchernodetails(baseResponse)
{
        
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  var txtReceipt_No=document.getElementById("txtReceipt_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
        
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtReceipt_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtReceipt_No.add(option);
            }catch(errorObject)
            {
                txtReceipt_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtReceipt_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtReceipt_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtReceipt_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtReceipt_No.add(option);
            }catch(errorObject)
            {
                txtReceipt_No.add(option,null);
            }
         alert("No Cheque Found");
    }
}

function  otherdetails(baseResponse)
{   //alert("otherdetails");
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   // var  =document.getElementById("txtReceipt_No").value;
   // var txtCheque_DD_NO=document.getElementById("txtCheque_DD_NO");
    if(flag=="success")
    {
        document.getElementById("txtDoc_date").value=baseResponse.getElementsByTagName("transdate")[0].firstChild.nodeValue;
      // document.getElementById("txtReceipt_No").value=baseResponse.getElementsByTagName("Rec_No")[0].firstChild.nodeValue;
        document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
        document.getElementById("txtCheque_DD_date").value=baseResponse.getElementsByTagName("che_DD_date")[0].firstChild.nodeValue;
       document.getElementById("txtAmount2").value=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       document.getElementById("doc_type").value=baseResponse.getElementsByTagName("doctype")[0].firstChild.nodeValue;
       
       document.getElementById("txtCheque_DD").value=baseResponse.getElementsByTagName("cheq_dd")[0].firstChild.nodeValue;
       
       // document.getElementById("txtRemarks").value=baseResponse.getElementsByTagName("txtRemarks")[0].firstChild.nodeValue;
       if (doctype=='Fund Receipt'){
    alert("THIS IS FUND RECEIPT CHEQUE:ARE YOU SURE YOU WANT TO CANCEL THIS CHEQUE");
    
            
    }
    var doctype=baseResponse.getElementsByTagName("doctype")[0].firstChild.nodeValue;
      if (doctype=='Fund Receipt HO'){
    alert("THIS IS FUND RECEIPT FROM HEAD OFFICE CHEQUE:ARE YOU SURE YOU WANT TO CANCEL THIS CHEQUE");
               
    }
            
    }
    else if(flag=="failure")
    {
        alert("Loading failed")
    }
}

function check_oldcheqno(c)
{

 if(document.getElementById("txtCheque_DD_NO2").value==document.getElementById("txtCheque_No").value)
        {
            alert("This cheque number already exists");
           document.getElementById("txtCheque_DD_NO2").value=""; 
           document.getElementById("txtCheque_DD_NO2").focus();
           // return false;
        }
   
}
function call_date(dateCtrl)                        // TB_checking 
{
    call_clr();
    if(checkdt(dateCtrl))
    {   //  alert("insidecheckdt");
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
        //doFunction('load_Voucher_No','null');
    }
    else
    {
      document.getElementById("txtVoucher_No").value="";
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
                 //doFunction('load_Voucher_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtVoucher_No").value="";
               }
              else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtVoucher_No").value="";     
               }
        }
    }

}

function check_amount(id)
{
        var txtCheque_DD3=document.getElementById("txtCheque_DD3");
        var txtAmount2=document.getElementById("txtAmount2");
        //alert(id);
        //alert(txtAmount2.value);
        if(id=="N")
           {
            txtAmount2.disabled=true;
            
           }
        else
        {
                txtAmount2.disabled=false;
        }
}
function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Record inserted into database");
        ClearAll();
    }
    else
    {
        alert("Record not inserted into database");
    }
}
function cheque(id)
{
        //alert("here it comes"+id);
            try
            {
                var txtCheque_DD2=document.getElementById("div_cheque");
                
               if(id=="N")
               {
                txtCheque_DD2.style.display="none";
                 txtCheque_DD3.style.display="none";
                 txtCheq_DD_Issued.style.display="none";
               }
               else if(id=="Y")
               {
                       txtCheque_DD2.style.display="inline";
                       //txtCheque_DD3.style.display="block";
                       //txtCheq_DD_Issued.style.display="block";
              }
            }
            catch(e){ }
}    

function freshreceiptclick(choice)
{
          
             //alert("Fresh receipt received yes/No ***********"+choice);
           //if(document.getElementByID("txtCheque_DD3").value=="N")
           if(choice=="N")
           {
                document.getElementByID("txtAmount2").value=document.getElementByID("txtAmount").value;
           }
             else
             {
                    if(document.getElementById("txtCheque_DD_NO2").value=="")
                    {
                        alert("Enter the cheqnumber");
                        //document.getElementById("txtCheque_DD_NO2").focus();
                        return false;
                    }
                  
                   
                   else if(document.getElementById("txtCheque_DD_date2").value=="")
                    {
                        alert("select the date");
                        //document.getElementById("txtCheque_DD_date2").focus();
                        return false;
                    }
                    else if(document.getElementById("txtAmount2").value.length==0)
                    {
                        alert("Enter the new cheque amount");
                        //document.getElementById("txtOperation_mode").focus();
                        return false;
                    }
            }
          
}


function check()
{
            var datefrom=document.frmCheque_Dishonour.txtCheque_DD_date.value;
            //alert("the date from is"+datefrom);
            var dateto=document.frmCheque_Dishonour.txtCheque_DD_date2.value;
            //alert("the date to is"+dateto);
            var flag=0;
            var d = datefrom.split("/");
            var e = dateto.split("/");
            if(e[2]<d[2])
            {
            alert("Newcheque year should be greater than oldcheque Year");
            flag++;
                document.frmCheque_Dishonour.txtCheque_DD_date2.value="";
                document.frmCheque_Dishonour.txtCheque_DD_date2.focus();
            }
            
            
            if(e[2]==d[2])
            {
              if (e[1]<d[1])
              {
                alert("New Cheque month should be Greater than Oldcheque Month");
                flag++;
                document.frmCheque_Dishonour.txtCheque_DD_date2.value="";
                document.frmCheque_Dishonour.txtCheque_DD_date2.focus();
                }
               else if(e[1]==d[1])
               {
                 if(e[0]<d[0])
                 {
                   alert("New Cheque date should be Greater than Oldcheque Date");
                   flag++;
                  document.frmCheque_Dishonour.txtCheque_DD_date2.value="";
                  document.frmCheque_Dishonour.txtCheque_DD_date2.focus();
                    }
                  }
                 }
                if(flag>0)
                {
                 return false
                 }
              
  }
  
  function Check_Date(_date)
        {   
            var doc_date;
            doc_date=document.getElementById("txtCrea_date").value;  
           // alert("doc_date"+doc_date);
            
            if( _date != "")
            {
                var url="../../../../../Date_Check.kv?Command=Date_Check&_date="+_date+"&doc_date="+doc_date; 
             //   alert(url);
                var req=getTransport();
                req.open("GET",url,true);
                req.onreadystatechange=function()
                {
                   handle_loadOffice(req);
                }
                req.send(null); 
            } 
        }




function handle_loadOffice(req)
{
   if(req.readyState==4)
    {
    
     if(req.status==200)
     {
             
        var baseresponse=req.responseXML.getElementsByTagName("response")[0];
        var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;       
        if(flag=="success")
        { 
           var check=baseresponse.getElementsByTagName("check")[0].firstChild.nodeValue;   
            if (check=="invalid")
             {
                alert("Cheque exceeds 180 days"); 
                document.getElementById("txtCheque_DD_date2").value="";   
                document.getElementById("txtCheque_DD_date2").focus();
             }
            else if (check=="valid")
            {
                
            }
            
        }
            
      }       
             
   }
}
function check_dd_cheque()
 {
      var cheque_no= document.getElementById("txtCheque_DD_NO2").value;
      var doctype=document.getElementById("doc_type").value;
      
      var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode_old").value;
      if(doctype=='Bank Receipt'){
      //alert("inside Bank receipt");
      var url="../../../../../Cheque_Number_Check_forRECEIPT?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
   // alert(url);
     var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
         {
            handleResponse_cheque_no(req);
         }   
      req.send(null);
      }
      else{
      var url="../../../../../Cheque_Number_Check_FR_byOffice.kv?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
      
      var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
         {
            handleResponse_cheque_no_fund(req);
         }   
      req.send(null); 
      }
 }
 
function handleResponse_cheque_no(req) 
{ 
   
    if(req.readyState==4)
    {
       
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
              {
              
                 var cheque_no = baseResponse.getElementsByTagName("cheq_no");   
                
                 var max=cheque_no.length;
               
                 if(max > 1 )
                    max--;
                  
                              
                 var temp="";
                 for(var k=0;k<max;k++)
                 { 
                    temp=temp+"----------------------------------------------------\n";
                  temp=temp+"Receipt Number = "+baseResponse.getElementsByTagName("vo_no")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Amount  = "+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Created By Module = "+baseResponse.getElementsByTagName("crm")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Year  =  "+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Month =  "+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue+"\n";
                  temp=temp+"\n";
                 
                 }  
                              
                 alert("  Cheque Number  "+ baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue + " already exits "+'\n'+temp);   
                 
                   
                 
              }
       }
   }    
}

function handleResponse_cheque_no_fund(req) 
{ 
   
    if(req.readyState==4)
    {
       
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(flag=="Success")
              {
              
                 var cheque_no = baseResponse.getElementsByTagName("cheq_no");   
                
                 var max=cheque_no.length;
               
                 if(max > 1 )
                    max--;
                  
                              
                 var temp="";
                 for(var k=0;k<max;k++)
                 { 
                  temp=temp+"----------------------------------------------------\n";
                  temp=temp+"Receipt Number = "+baseResponse.getElementsByTagName("voc_no")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Amount  = "+baseResponse.getElementsByTagName("amount")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Year  =  "+baseResponse.getElementsByTagName("cbyear")[k].firstChild.nodeValue+"\n";
                  temp=temp+"Cashbook Month =  "+baseResponse.getElementsByTagName("cbmonth")[k].firstChild.nodeValue+"\n";
                  temp=temp+"\n";
                 
                 }  
                              
                 alert("  Cheque Number  "+ baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue + " already exits "+'\n'+temp);   
               //  document.getElementById("txtCheque_DD_NO").value="";
                 
                 
              }
       }
   }    
}

function checkoption(id){

var txtCheque_DD3=document.getElementById("txtCheque_DD3");
var txtAmount2=document.getElementById("txtAmount2");
 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
 var cmbAcc_UnitCode_old=document.getElementById("cmbAcc_UnitCode_old").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var cmbOffice_code_old=document.getElementById("cmbOffice_code_old").value;
      
//alert(id);
//alert(txtAmount2.value);
if(id=="M")
   {
            var opId=document.getElementById("optionid");
            opId.style.display="block";
             var txtCB_Year=document.getElementById("txtCB_Year").value;
     //  var txtCB_Month=document.getElementById("txtCB_Month").value;
             var txtCB_Month=document.getElementById("txtCB_Month").value;
            var url="../../../../../Cheque_Dishonour_System.view?Command=chequenodetails&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode_old+"&cmbOffice_code="+cmbOffice_code_old;
            //alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            
    
   }
else if(id=="C") {
             var opId=document.getElementById("optionid");
            opId.style.display="none";
    var txtCrea_date=document.getElementById("txtCrea_date").value
  
  
   //  txtCheque_DD3.style.display="none";
     //txtCheq_DD_Issued.style.display="none";
  
            var url="../../../../../Cheque_Dishonour_System_New.view?Command=chequenodetails_cheqwise&cmbAcc_UnitCode="+cmbAcc_UnitCode_old+"&cmbOffice_code="+cmbOffice_code_old+"&txtCrea_date="+txtCrea_date;
            //alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   
                    req.send(null);
            
}
}

