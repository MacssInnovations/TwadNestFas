/**
 *    VARAIABLES DECLARATION 
 */

var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;



/**
 *    XML REQUEST 
 */

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




/** 
 *    DO FUNCTION 
 */ 

function doFunction(Command,param)
{   
        if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmBankPay_PendingBill_Cancel.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
//            var url="../../../../../BankPay_PendingBill_Cancel.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
//            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            	
            	var url="../../../../../BankPay_PendingBill_Cancel.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
                cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            	
//            alert(url)
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
            }         
        }
        else if(Command=="load_Voucher_Details")
        {  
            clearGeneral_Detail();
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.frmBankPay_PendingBill_Cancel.txtCrea_date.value
            var  txtVoucher_No=document.getElementById("txtVoucher_No").value;
            if(txtVoucher_No!="")
            {
            var url="../../../../../BankPay_PendingBill_Cancel.view?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
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




/**
 *    HANDLE RESPONSE 
 */ 

function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {     //  alert(req.responseText);
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
            var Command=tagcommand.firstChild.nodeValue;
           
           if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_Details")
            {
                load_Voucher_Details(baseResponse);
            }
        }
    }
}


/** 
 *   FORM CLEAR FUNCTIONS 
 */ 

function call_clr()
{
   document.getElementById("txtVoucher_No").value="";  
   clearGeneral_Detail();
}


function clearGeneral_Detail()
{
    document.getElementById("txtCash_Acc_code").value="";
    document.getElementById("txtBankAccountNo").value="";
    document.getElementById("txtBankName").value="";
    document.getElementById("txtBankId").value="";
    document.getElementById("txtBranchId").value="";
    document.getElementById("cmbMas_SL_type").value="";
    document.getElementById("cmbMas_SL_Code").value="";
    document.frmBankPay_PendingBill_Cancel.radPart_Amt[0].checked=true;
    document.getElementById("txtPart_Amount").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtPaid_to").value="";
    //clearall();
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
}


function clrForm()
{
   
 if(window.confirm("Do you want to clear ALL fields ?"))
 {
   
   call_clr();
 }
}




/**
 * CHECK NULL FUNCTION 
 */ 

function checkNull()
{
   var tbody=document.getElementById("grid_body");
   
if(document.getElementById("cmbAcc_UnitCode").value=="")
{
    alert("Select the Account Unit code");
    return false;    
}
if(document.getElementById("cmbOffice_code").value=="")
{
    alert("Select the Office Code");
    return false;
}
if(document.getElementById("txtCrea_date").value.length==0)
{
    alert("Enter the Date of Creation");
    return false;    
}

if(document.getElementById("txtVoucher_No").value=="")
{
    alert("Select the Voucher Number");
    return false;    
}

if(document.getElementById("cmbMas_SL_type").value=="")
{
    alert("Select The Sub Ledger Type in General");
    return false;
}
if(document.getElementById("cmbMas_SL_type").value!="")
{
    if(document.getElementById("cmbMas_SL_Code").value=="")
    {
    alert("Select The Sub Ledger Code in General");
    return false;
    }
}
if(document.frmBankPay_PendingBill_Cancel.radPart_Amt[0].checked==true)
{
 if(document.getElementById("txtPart_Amount").value.length==0)
 {
   alert("Enter the Part Amount ");
   return false;
 }
}

if(document.getElementById("txtPaid_to").value.length==0)
{
    alert("Enter the value in Paid to Field in General");
    return false;    
}
if(document.getElementById("txtPay_Vou_No").value.length==0)
{
    alert("Select Journal Voucher Number from Pending Bills");
    return false;    
}
if(document.getElementById("txtPay_Vou_date").value.length==0)
{
    alert("Select Journal Voucher Date from Pending Bills");
    return false;    
}

if(document.getElementById("txtAmount").value.length==0)
{
    alert("Enter the Total Amount in General");
    return false;    
}

if(tbody.rows.length==0)
{
    alert("Enter the Details Part");
    return false; 
}

}

/**
 *     LOAD VOUCHER NUMBER 
 */ 

function load_Voucher_No(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    var txtVoucher_No=document.getElementById("txtVoucher_No");
    if(flag=="success")
    {
           var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
            
            for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }
         
            txtVoucher_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtVoucher_No.add(option);
            }catch(errorObject)
            {
                txtVoucher_No.add(option,null);
            }
            
            for(var k=0;k<items_id.length;k++)
            {   
                  var option=document.createElement("OPTION");
                  option.text=items_id[k];
                  option.value=items_id[k];
                   try
                  {
                      txtVoucher_No.add(option);
                  }
                  catch(errorObject)
                  {
                      txtVoucher_No.add(option,null);
                  }
            }
    }
    else if(flag=="failure")
    {
            txtVoucher_No.innerHTML="";
            var option=document.createElement("OPTION");
            option.text="--Select Voucher Number--";
            option.value="";
            try
            {
                txtVoucher_No.add(option);
            }catch(errorObject)
            {
                txtVoucher_No.add(option,null);
            }
         alert("No Voucher Found");
    }
}




/**
 *  LOAD VOUCHER DETAILS 
 */ 

function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
//    alert(flag);
    {
       var MasHeadCode=baseResponse.getElementsByTagName("MasHeadCode")[0].firstChild.nodeValue;
       var accNo   = baseResponse.getElementsByTagName("accNo")[0].firstChild.nodeValue;
       var bk_name = baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue;
       var bk_id   = baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
       var br_id   = baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
       var Total_amt = baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       var Mas_paid  = baseResponse.getElementsByTagName("Mas_paid")[0].firstChild.nodeValue;
       var Remak     = baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
     
       var partPay = baseResponse.getElementsByTagName("partPay")[0].firstChild.nodeValue;
       var partAmt = baseResponse.getElementsByTagName("partAmt")[0].firstChild.nodeValue;
       
     
      if(partPay=="Y")
      document.frmBankPay_PendingBill_Cancel.radPart_Amt[0].checked=true;
      else if(partPay=="N")
      document.frmBankPay_PendingBill_Cancel.radPart_Amt[1].checked=true;
      
      if(partPay=="Y")
      {
        document.getElementById("txtPart_Amount").value=partAmt;
      }
      
    
      document.getElementById("txtCash_Acc_code").value=MasHeadCode;
      document.getElementById("txtBankAccountNo").value=accNo;
      document.getElementById("txtBankName").value=bk_name;
      document.getElementById("txtBankId").value=bk_id;
      document.getElementById("txtBranchId").value=br_id;
      
       document.getElementById("txtAmount").value=Total_amt;
      if(Mas_paid!="null")
      document.getElementById("txtPaid_to").value=Mas_paid;
      else
      document.getElementById("txtPaid_to").value="";
      if(baseResponse.getElementsByTagName("jour_code")[0]!=undefined)
      var jour=baseResponse.getElementsByTagName("jour_code")[0].firstChild.nodeValue;
      
      document.getElementById("jour_hid").value=jour;
            
        var sltype =new Array(4);
        sltype[11]="Contractors";
        sltype[1]="Supplier";
        sltype[2]="Firms";
        sltype[9]="Others departments";
        sltype[17]="Miscellaneous";
        sltype[7]="Employees";
        sltype[96]="Miscellaneous";
        
      
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
       
      // alert("Mas_SL_type"+Mas_SL_type);
       //alert("Mas_SL_code"+Mas_SL_code);
       if(Mas_SL_type!=0)
       {
    	 //  alert("if Entered.");
             for(i=1;i<=96;i++)
           {
            	// alert("for Entered.");
               if(Mas_SL_type==i)
               {
            	  // alert("if Entered.");
               document.getElementById("cmbMas_SL_type").value=sltype[i];
              //alert(document.getElementById("cmbMas_SL_type").value);
               break;
               }
           }
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var Mas_SLCODE=baseResponse.getElementsByTagName("cid");
             //alert("Mas_SLCODE"+Mas_SLCODE.length);
            for(var k=0;k<Mas_SLCODE.length;k++)
            {
            	//alert(baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue);
            	//alert(baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue);
                items_SLcode[k]=baseResponse.getElementsByTagName("cid")[k].firstChild.nodeValue;
                items_SLdesc[k]=baseResponse.getElementsByTagName("cname")[k].firstChild.nodeValue;
                
                if(items_SLcode[k]==Mas_SL_code)
                {
                   
                 document.getElementById("cmbMas_SL_Code").value=items_SLdesc[k];
                 break;
                }
            }
       }
      
        if(Remak!="null")
         document.getElementById("txtRemarks").value=Remak;
        else
        document.getElementById("txtRemarks").value="";
       
       
       var tbody=document.getElementById("grid_body");
        var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
        var AHcode=baseResponse.getElementsByTagName("AHcode");
        var items=new Array();
        
        for(var k=0;k<AHcode.length;k++)
        {
        items[0]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;   
        items[1]=baseResponse.getElementsByTagName("AHdesc")[k].firstChild.nodeValue;   
        
        items[2]=baseResponse.getElementsByTagName("CR_DR_ind")[k].firstChild.nodeValue;
        items[3]=baseResponse.getElementsByTagName("SL_Type")[k].firstChild.nodeValue;
        if(items[3]==0)
        items[3]="";
         
        items[4]=baseResponse.getElementsByTagName("SL_Desc")[k].firstChild.nodeValue;
        if(items[4]=="null")
        items[4]="";
        
        items[5]=baseResponse.getElementsByTagName("SL_Code")[k].firstChild.nodeValue;
        if(items[5]==0)
        items[5]="";
        if(baseResponse.getElementsByTagName("desc_type")[k]!=undefined){
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
        }
        if(items[6]=="null")
        items[6]="";
        if(baseResponse.getElementsByTagName("che_or_DD")[k]!=undefined)
        items[7]=baseResponse.getElementsByTagName("che_or_DD")[k].firstChild.nodeValue;
        
       /* if(baseResponse.getElementsByTagName("che_DD_no")[k]!=undefined)
        items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;*/
        
        if((baseResponse.getElementsByTagName("che_DD_no")[k]== undefined)||(baseResponse.getElementsByTagName("che_DD_no")[k].firstChild == null)){
			items[8]="";   }
			else{
      	  items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;  }
       
       
       /* 
        if(baseResponse.getElementsByTagName("che_DD_no")[k]== null){
			items[8]="";
		}else{
			      	  items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;  
		}
		
        */
        
        
        if(baseResponse.getElementsByTagName("che_DD_date")[k]!=undefined)
        items[9]=baseResponse.getElementsByTagName("che_DD_date")[k].firstChild.nodeValue;
        
         if(baseResponse.getElementsByTagName("billno")[k]!=undefined)    
        items[10]=baseResponse.getElementsByTagName("billno")[k].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("billdate")[k]!=undefined)
        items[11]=baseResponse.getElementsByTagName("billdate")[k].firstChild.nodeValue;
       // if(baseResponse.getElementsByTagName("billtype")[k]!=undefined)
       // items[12]=baseResponse.getElementsByTagName("billtype")[k].firstChild.nodeValue;
        
      //    if(baseResponse.getElementsByTagName("billtype")[k].firstChild==null)
       // 	items[12]="";
      //    else
        //  	items[12]=baseResponse.getElementsByTagName("billtype")[k].firstChild.nodeValue;
        
        if((baseResponse.getElementsByTagName("billtype")[k]== undefined) || (baseResponse.getElementsByTagName("billtype")[k].firstChild == null)){
			items[12]="";
		}
          else{
			  items[12]=baseResponse.getElementsByTagName("billtype")[k].firstChild.nodeValue;
		  }
          
         	
        
        
        
        
          
           if((baseResponse.getElementsByTagName("Agree_no")[k]== undefined) || (baseResponse.getElementsByTagName("Agree_no")[k].firstChild == null))
        	items[13]="";
          else
          	items[13]=baseResponse.getElementsByTagName("Agree_no")[k].firstChild.nodeValue;
        
        if(baseResponse.getElementsByTagName("Agree_date")[k]!=undefined)
        items[14]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
        
         if((baseResponse.getElementsByTagName("sub_paidto")[k]== undefined) || (baseResponse.getElementsByTagName("sub_paidto")[k].firstChild == null))
        	items[15]="";
          else
          	 items[15]=baseResponse.getElementsByTagName("sub_paidto")[k].firstChild.nodeValue;
        
        //if(baseResponse.getElementsByTagName("sub_paidto")[k]!=undefined)
       // items[15]=baseResponse.getElementsByTagName("sub_paidto")[k].firstChild.nodeValue;
        
        if(baseResponse.getElementsByTagName("sub_amount")[k]!=undefined)
        items[16]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("sub_part")[k]!=undefined)
        items[17]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        if(baseResponse.getElementsByTagName("pay_vou_no")[k]!=undefined)
       items[18]=baseResponse.getElementsByTagName("pay_vou_no")[k].firstChild.nodeValue;
       if(baseResponse.getElementsByTagName("pay_vou_date")[k]!=undefined)
       items[19]=baseResponse.getElementsByTagName("pay_vou_date")[k].firstChild.nodeValue;
       if(baseResponse.getElementsByTagName("jour_code")[k]!=undefined)
       items[20]=baseResponse.getElementsByTagName("jour_code")[k].firstChild.nodeValue;
      // document.getElementById(elementId)
        
        
        if(items[8]=="null")
        items[8]="";
        if(items[9]=="null")
        items[9]="";
        if(items[10]=="null")
        items[10]="";
        if(items[11]=="null")
        items[11]="";
         if(items[12]=="null")
        items[12]="";
         if(items[13]=="null")
        items[13]="";
         if(items[14]=="null")
        items[14]="";

        if(items[15]=="null")
        items[15]="";
         if(items[17]=="null")
        items[17]="";
        
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        
              
        var cell2;
        
        
            cell2=document.createElement("TD");
            
             var Voucher_no=document.createElement("input");
              Voucher_no.type="hidden";
              Voucher_no.name="Voucher_no"+seq;
              Voucher_no.id="Voucher_no"+seq;
              Voucher_no.value=items[18];
              cell2.appendChild(Voucher_no);
              var currentText=document.createTextNode(items[18]);
              cell2.appendChild(currentText);
               mycurrent_row.appendChild(cell2);
               
         cell2=document.createElement("TD");
              var vou_date=document.createElement("input");
              vou_date.type="hidden";
              vou_date.name="vou_date"+seq;
              vou_date.id="vou_date"+seq;
              vou_date.value=items[19];
              cell2.appendChild(vou_date);
               var currentText=document.createTextNode(items[19]);
              cell2.appendChild(currentText);
            mycurrent_row.appendChild(cell2);
            
         cell2=document.createElement("TD");
               var journal_type_code=document.createElement("input");
              journal_type_code.type="hidden";
              journal_type_code.name="journal_type_code"+seq;
              journal_type_code.id="journal_type_code"+seq;
              journal_type_code.value=items[20];
              cell2.style.display="none";
              cell2.appendChild(journal_type_code);
               var currentText=document.createTextNode(items[20]);
              cell2.appendChild(currentText);
               mycurrent_row.appendChild(cell2);
          
           cell2=document.createElement("TD");

                 var H_code=document.createElement("input");
                  H_code.type="hidden";
                  H_code.name="H_code"+seq;
                  H_code.id="H_code"+seq;
                  H_code.value=items[0];
                  cell2.appendChild(H_code);
                  var currentText=document.createTextNode(items[0]+"-"+items[1]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD");
                  var CR_DR_type=document.createElement("input");
                  CR_DR_type.type="hidden";
                  CR_DR_type.name="CR_DR_type"+seq;
                  CR_DR_type.id="CR_DR_type"+seq;
                  CR_DR_type.value=items[2];
                  cell2.style.display="none";
                  cell2.appendChild(CR_DR_type);
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
             cell2=document.createElement("TD");
                   var SL_type=document.createElement("input");
                  SL_type.type="hidden";
                  SL_type.name="SL_type"+seq;
                  SL_type.id="SL_type"+seq;
                  SL_type.value=items[3];
                  cell2.appendChild(SL_type);
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
            cell2=document.createElement("TD");
                  var SL_code=document.createElement("input");
                  SL_code.type="hidden";
                  SL_code.name="SL_code"+seq;
                  SL_code.id="SL_code"+seq;
                  SL_code.value=items[5];
                  cell2.appendChild(SL_code);
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
        
            cell2=document.createElement("TD");
                  var Bill_NO=document.createElement("input");
                  Bill_NO.type="hidden";
                  Bill_NO.name="Bill_NO"+seq;
                  Bill_NO.id="Bill_NO"+seq;
                  Bill_NO.value=items[10];
                  cell2.appendChild(Bill_NO);
                                                                                       
                  var Bill_date=document.createElement("input");
                  Bill_date.type="hidden";
                  Bill_date.name="Bill_date"+seq;
                  Bill_date.id="Bill_date"+seq;
                  Bill_date.value=items[11];
                  cell2.appendChild(Bill_date);
                                                                                       
                                                                                       
                  var Bill_type=document.createElement("input");
                  Bill_type.type="hidden";
                  Bill_type.name="Bill_type"+seq;
                  Bill_type.id="Bill_type"+seq;
                  Bill_type.value=items[12];
                  cell2.appendChild(Bill_type);
                  
                                                                                       
                  var Agree_No=document.createElement("input");
                  Agree_No.type="hidden";
                  Agree_No.name="Agree_No"+seq;
                  Agree_No.id="Agree_No"+seq;
                  Agree_No.value=items[13];
                  cell2.appendChild(Agree_No);
                    
                  var Agree_date=document.createElement("input");
                  Agree_date.type="hidden";
                  Agree_date.name="Agree_date"+seq;
                  Agree_date.id="Agree_date"+seq;
                  Agree_date.value=items[14];
                  cell2.appendChild(Agree_date);
                             
                             
                  var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt"+seq;
                  sl_amt.id="sl_amt"+seq;
                  sl_amt.value=items[16];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount Text Node but after  amount text box    
                  particular.type="hidden";
                  particular.name="particular"+seq;
                  particular.id="particular"+seq;
                  particular.value=items[17];
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[16]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
       
        tbody.appendChild(mycurrent_row);
        
        }
     //   alert("seq >> "+seq)
        document.getElementById("Count_hid").value=seq;
    }
    if(flag=="failure")
    	{alert("probable subsequent payment has been made cannot be cancelled or combination of receipt and journal in sigle payment");}
    
}






function clear_Combo(combo)
{
        var cmbSL_Code=document.getElementById(combo.id);   
        cmbSL_Code.innerHTML="";
        var option=document.createElement("OPTION");
        option.text="--Select Code--";
        option.value="";
        try
        {
            cmbSL_Code.add(option);
        }catch(errorObject)
        {
            cmbSL_Code.add(option,null);
        }
}








///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)         // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
    {
             call_clr();
             cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
         cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
         cmbOffice_code=document.getElementById("cmbOffice_code").value;
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
                 doFunction('load_Voucher_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    document.getElementById("txtVoucher_No").value="";
               }
        }
    }
}