var seq=0;
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
////////////////////////////////////////////////////////

function doFunction(Command,param)
{   
 
      if(Command=="PendingReceipts")
        {  
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.getElementById("txtCrea_date").value;
            var txtBankAccountNo=document.getElementById("txtBankAccountNo").value;
            
            var url="../../../../../BankRemit_Create.view?Command=PendingReceipts&cmbAcc_UnitCode="+cmbAcc_UnitCode
                    +"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date+"&txtBankAccountNo="+txtBankAccountNo;
          //  alert(url);
            if(txtCrea_date.length!=0 && txtBankAccountNo!="")
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
            //alert('command...'+Command);
            if(Command=="PendingReceipts")
            {
                PendingReceipts(baseResponse);
            }
        }
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
        var Remitted_amount=0;
        
        
        seq=0;                                  // make into zero
        for(var k=0;k<RecNo.length;k++)
        {
            try
            {
             items[0]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;   
             items[1]=baseResponse.getElementsByTagName("Rec_Date")[k].firstChild.nodeValue;   
             items[2]=baseResponse.getElementsByTagName("Rec_From")[k].firstChild.nodeValue;
             
             items[4]=baseResponse.getElementsByTagName("cheqDD_date")[k].firstChild.nodeValue;   
             items[5]=baseResponse.getElementsByTagName("Bank_name")[k].firstChild.nodeValue;
             items[6]=baseResponse.getElementsByTagName("Total_amt")[k].firstChild.nodeValue;
             
             if(baseResponse.getElementsByTagName("cheqDD_No")[k].firstChild==null)
             	items[3]="";
             else
             	items[3]=baseResponse.getElementsByTagName("cheqDD_No")[k].firstChild.nodeValue;
             
            }
            catch(e)
            {
              alert("Value Retrieved Failed");
            }
            
           try
           {
               // Here find the total amount
             Remitted_amount=parseFloat(Remitted_amount)+parseFloat(items[6]);
             
             
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
                         
                    /*
                       cell2=document.createElement("TD");
                       var seq_No=document.createElement("input");
                       seq_No.type="hidden";
                       seq_No.name="seq_No";
                       seq_No.value=seq;
                       cell2.appendChild(seq_No);
                       var currentText=document.createTextNode(seq);     // serial number generation
                       cell2.appendChild(currentText);
                       mycurrent_row.appendChild(cell2);
                       */
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
                           var currentText=document.createTextNode(items[2]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                    
                     cell2=document.createElement("TD");
                           var currentText=document.createTextNode(items[3]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                     cell2=document.createElement("TD");
                           var currentText=document.createTextNode(items[4]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                    
                     cell2=document.createElement("TD");
                           var currentText=document.createTextNode(items[5]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                     cell2=document.createElement("TD");
                           var currentText=document.createTextNode(items[6]);
                           cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                    
              tbody.appendChild(mycurrent_row);
            }
            catch(e)
            {
              alert("Error");
            }
           
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
        document.getElementById("txtAmount").value="";
        alert("No Receipt Found for Remittance");
     }
}

function cal_amount(getId)
{
    //alert(getId)
    var getrow=document.getElementById(getId);
    var get_cells=getrow.cells;
   
    //alert(get_cells.item(8).firstChild.nodeValue);
    //alert(parseFloat(get_cells.item(8).firstChild.nodeValue));
    //alert(parseFloat(document.getElementById("txtAmount").value));

    if(get_cells.item(0).firstChild.checked==true)           // if it's checked by user and add the amount to total amount for that row
    {
        document.getElementById("txtAmount").value=parseFloat(document.getElementById("txtAmount").value)+parseFloat(get_cells.item(7).firstChild.nodeValue);  
    }
    else if(get_cells.item(0).firstChild.checked==false)           // if it's unchecked by user and subtract the amount to total amount for that row
    {
        document.getElementById("txtAmount").value=parseFloat(document.getElementById("txtAmount").value)-parseFloat(get_cells.item(7).firstChild.nodeValue);  
    }
   
    
    
}

function call_clr()
{
   
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
     //alert('jd');
}
function clrForm()
{
call_clr();
document.getElementById("butSub").disabled=false;
}
function verify_one()
{
 var tbody=document.getElementById("grid_body");
   if(tbody.rows.length>0)
         {
        // alert("enter");
                rows=tbody.getElementsByTagName("tr");
                var amount=0;
                for(i=0;i<rows.length;i++)
                {
                    var cells=rows[i].cells;
                
                    if(cells.item(0).firstChild.checked==true)
                    {
                            
                            amount=parseFloat(amount) + parseFloat(cells.item(7).lastChild.nodeValue);
                           // alert("amount::"+amount);
                    }
                   
                }
            var totalamount=document.getElementById("txtAmount").value;
                 if(totalamount==amount)
                 {
                 return true;
                 }
                 else
                 {
                 alert("Amount Doesn't Tally");
                 return false;
                 }
         }
 
if(window.confirm("Please verify the voucher details.  \nBecause you will not be able to modify the receipt and remittance after preparing the challan. \nIf you want to continue,click 'OK'?"))
          {
//alert("Please verify the voucher details.  \nBecause you will not be able to modify the receipt and remittance after preparing the challan" );
            return true;
         }
else
        return false;
}
function checkNull()
{alert("checkNull");
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
        if(tbody.rows.length==0)
        {
            alert("There is no voucher found for Remittance");
            //document.getElementById("txtAmount").focus();
            return false; 
        }
         if(tbody.rows.length>0)
         {
         alert("enter");
                rows=tbody.getElementsByTagName("tr");
                var amount=0;
                for(i=0;i<rows.length;i++)
                {
                    var cells=rows[i].cells;
                
                    if(cells.item(0).firstChild.checked==true)
                    {
                            
                            amount=parseFloat(amount) + parseFloat(cells.item(7).lastChild.nodeValue);
                            alert("amount"+amount);
                    }
                   
                }
            var totalamount=document.getElementById("txtAmount").value;
                 if(totalamount==amount)
                 {
                 return true;
                 }
                 else
                 {
                 alert("Amount Doesn't Tally");
                 return false;
                 }
         }
        if(document.getElementById("txtAmount").value.length==0 || document.getElementById("txtAmount").value==0)
        {
            alert("Amount not Populated");
            return false;
        }
        if(document.getElementById("txtBankAccountNo").value=="")
        {
            alert("Select the Bank Account Number ");
            //document.getElementById("cmbOffice_code").focus();
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
                 doFunction('PendingReceipts','null');
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
                 //doFunction('load_Receipt_No','null');                 //return true;
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
