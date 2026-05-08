
/**
 *  Browser Identification 
 */ 

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




function byUnitAndOfficeChange()
{
    doFunction('loadPendingRemittance','null');
}




var loadRemit_ReceiptDetails


function Show(unitcode,offid,challanNo,challanDate)
{
    if (loadRemit_ReceiptDetails && loadRemit_ReceiptDetails.open && !loadRemit_ReceiptDetails.closed) 
    {
       loadRemit_ReceiptDetails.resizeTo(500,500);
       loadRemit_ReceiptDetails.moveTo(250,250); 
       loadRemit_ReceiptDetails.focus();
    }
    else
    {
        loadRemit_ReceiptDetails=null
    }
    loadRemit_ReceiptDetails= window.open("../../../../../org/FAS/FAS1/RemittanceSystem/jsps/Cash_Remittance_Cancel_Show.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&challanNo="+challanNo+"&challanDate="+challanDate,"ReceiptDetails","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    loadRemit_ReceiptDetails.moveTo(250,250);  
    loadRemit_ReceiptDetails.focus();
    
}


window.onunload=function()
{
if (loadRemit_ReceiptDetails && loadRemit_ReceiptDetails.open && !loadRemit_ReceiptDetails.closed) loadRemit_ReceiptDetails.close();
}



function printing(unitcode,offid,challanNo,challanDate)
{
    var cmbAcc_UnitCode=unitcode; 
    var cmbOffice_code=offid;
    
    var challanNo=challanNo;  
    
    var challanDate=challanDate;
    var url="../../../../../Remittance_print.view?Command=CashRemit_print&cmbAcc_UnitCode="+cmbAcc_UnitCode+
    "&cmbOffice_code="+cmbOffice_code+                                  
    "&challanNo="+challanNo+ "&challanDate="+challanDate;
    document.frmFundRemit_Cancel.action=url;
    document.frmFundRemit_Cancel.method="POST";
    document.frmFundRemit_Cancel.submit();
      return true;
}

/**
 *  Display Pending Remittance for Cancellation 
 */

function doFunction(Command,param)
{   
       if(Command=="loadPendingRemittance")
        {  
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.getElementById("txtCrea_date").value;
            var url="../../../../../Fund_Remittance_Cancel.kv?Command=loadPendingRemittance&cmbAcc_UnitCode="+cmbAcc_UnitCode
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
}


function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {   
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="loadPendingRemittance")
            {
                PendingRemittance(baseResponse);
            }
        }
    }
}


function PendingRemittance(baseResponse)
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
        
        
        var Challan=baseResponse.getElementsByTagName("Challan_no");
        
        var items=new Array();
        var Ucode=document.getElementById("cmbAcc_UnitCode").value;
        var Offid=document.getElementById("cmbOffice_code").value;
        for(var k=0;k<Challan.length;k++)
        {

             items[0]=baseResponse.getElementsByTagName("Challan_no")[k].firstChild.nodeValue;   
             items[1]=baseResponse.getElementsByTagName("challan_date")[k].firstChild.nodeValue;   //******* remitted on is filled with challan date, bcoz both are same
             items[2]=baseResponse.getElementsByTagName("Amount")[k].firstChild.nodeValue;
             items[3]=baseResponse.getElementsByTagName("remarks")[k].firstChild.nodeValue;
           
            
             if( items[3]=="null")
                 items[3]="";
             tbody=document.getElementById("grid_body");
             
                var mycurrent_row=document.createElement("TR");
                
                var cell2;
                
                     /** Displaying Challan Number */                      
                     cell2=document.createElement("TD");
        
                          var Challan_no=document.createElement("input");
                          Challan_no.type="hidden";
                          Challan_no.name="Challan_no";
                          Challan_no.value=items[0];
                          cell2.appendChild(Challan_no);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                           
                     /** Displaying Challan Date */                                 
                     cell2=document.createElement("TD");
                          var challan_date=document.createElement("input");
                          challan_date.type="hidden";
                          challan_date.name="challan_date";
                          challan_date.value=items[1];
                          cell2.appendChild(challan_date); 
                          var currentText=document.createTextNode(items[1]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                        
                        
                     /** Displaying Amount */                         
                     cell2=document.createElement("TD");
                          var currentText=document.createTextNode(items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                     
                     /** Displaying Remarks */ 
                     
                     cell2=document.createElement("TD");
                        var currentText=document.createTextNode(items[3]);
                        cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                      
                    
                    /** Displaying Show Link */   
                      
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("A");
                        var url="javascript:Show('"+Ucode+"','"+Offid+"','"+items[0]+"','"+items[1]+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("Show Details");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                       
                       
                     /** Displaying Check Box For Cancellation */
                     
                        var cell=document.createElement("TD");
                        cell.align='CENTER';
                        var anc=document.createElement("input");
                        anc.type="checkbox";
                        anc.value=k+"~"+items[0]+"~"+items[1];
                        anc.id="cancel_select";
                        anc.name="cancel_select"; 
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);                         
                     
                     
              tbody.appendChild(mycurrent_row);
        }
        
     }
    else if(flag=="failure")
     {
        alert("No Remittance Record found ");
         var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
     }
}


function call_clr()
{

        var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
}


function exit()
{
       self.close();
}


function checkNull()
{
  var tbody=document.getElementById("grid_body");
  if(tbody.rows.length==0) 
   {
    alert("No data for verification");
    //document.getElementById("txtAmount").focus();
    return false; 
   }
  return true;
}




//function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
//{
//    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
//    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
//    {
//        
//            call_clr();
//            doFunction('loadPendingRemittance','null');
//    }
//}


function call_date(dateCtrl)                        
{
    call_clr();
    if(checkdt(dateCtrl))
    {
      doFunction('loadPendingRemittance','null');
    }
    else
    {
      document.getElementById("txtCrea_date").value="";
    }
}

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
             var TB_date=fromcal_dateCtrl.value;
             //alert(fromcal_dateCtrl.value+"b4url")
             if(fromcal_dateCtrl.value.length!=0)
             {
                 var url="../../../../../Receipt_SL.view?Command=check_TB_Jrnl&TB_date="+TB_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
                 //doFunction('load_Receipt_No','null');                 //return true;
            	call_clr();
            	doFunction('loadPendingRemittance','null');
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    //document.getElementById("txtReceipt_No").value="";
               }
             else if(flag=="finyear")
               {
                          // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                    dateCtrl.value="";
                    alert("Cash Book Control Not Found ");//return false;//
                    dateCtrl.focus();
                   // document.getElementById("txtReceipt_No").value="";     
               }
             else if(flag=="finyearLJVN")
             {
                        // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date 
                  dateCtrl.value="";
                  alert("Cash Book Control Not Found for Journal");//return false;//
                  dateCtrl.focus();
                 // document.getElementById("txtReceipt_No").value="";     
             }
            dateCheck(dateCtrl);
        }
    }
}

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