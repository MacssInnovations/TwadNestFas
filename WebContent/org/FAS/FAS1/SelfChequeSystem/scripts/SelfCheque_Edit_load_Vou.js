function doFunction_voucher(Command,param)
{
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCrea_date= document.getElementById("txtCrea_date").value
       var txtAUTHORIZED_TO='M';
        if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../SelfCheque_Edit_load_Vou.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtAUTHORIZED_TO="+txtAUTHORIZED_TO;
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
        else if(Command=="load_Voucher_Details")
        {  
            clearGeneral_Detail();
            var  txtReceipt_No=document.getElementById("txtReceipt_No").value;
            if(txtReceipt_No!="")
            {
            var url="../../../../../SelfCheque_Edit_load_Vou.view?Command=load_Voucher_Details&txtReceipt_No="+txtReceipt_No+"&txtCrea_date="+
            txtCrea_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
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
            //alert(req.responseTEXT)
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

function  load_Voucher_No(baseResponse)
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
         alert("No Voucher Found");
    }
}


function load_Voucher_Details(baseResponse)
{
    
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        document.getElementById("txtCash_Acc_code").value=baseResponse.getElementsByTagName("MasHeadCode")[0].firstChild.nodeValue;
        document.getElementById("txtBankAccountNo").value=baseResponse.getElementsByTagName("accNo")[0].firstChild.nodeValue;
        document.getElementById("txtBankName").value=baseResponse.getElementsByTagName("bankNAME")[0].firstChild.nodeValue;
        document.getElementById("txtBankId").value=baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
        document.getElementById("txtBranchId").value=baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
        document.getElementById("txtAmount").value=baseResponse.getElementsByTagName("main_tot_amt")[0].firstChild.nodeValue;
        document.getElementById("txtCheque_DD_NO").value=baseResponse.getElementsByTagName("cheq_no")[0].firstChild.nodeValue;
        document.getElementById("txtCheque_DD_date").value=baseResponse.getElementsByTagName("getCheq_date")[0].firstChild.nodeValue;
        var main_remark=baseResponse.getElementsByTagName("main_remark")[0].firstChild.nodeValue;
        if(main_remark!="null")
            document.getElementById("txtRemarks").value=main_remark;
        else
            document.getElementById("txtRemarks").value="";
        
      
        var paymentVrNo=baseResponse.getElementsByTagName("paymentVrNo");
        //-- payment main grid starting         
        for(var i=0;i<paymentVrNo.length;i++)
        {
            var tbody=document.getElementById("grid_body_PAYWISE");
            
            var items=new Array();
            items[0]=baseResponse.getElementsByTagName("Acq_roll_number")[i].firstChild.nodeValue;
            items[1]=baseResponse.getElementsByTagName("paymentVrNo")[i].firstChild.nodeValue;
            items[2]=baseResponse.getElementsByTagName("tot_pay_amt")[i].firstChild.nodeValue;
            items[3]=baseResponse.getElementsByTagName("remarks")[i].firstChild.nodeValue;
             
            seq_PAYWISE_VrNo=items[0];      
            seq_PAYWISE_VrNo=parseInt(seq_PAYWISE_VrNo)+parseInt(500);  // 500 is added with the last retrived payment Vr number , bcoz if new Voucher SL number should not be an Existing payment Vr number (even in database)
                                                                        // here all the previous Vr's loaded from database.. So if you add new Vr afterwards it start with addtion of 500
            if(items[3]=="null")
                items[3]="";
                
            var mycurrent_row=document.createElement("TR");
                seq_toLoad_PAYWISE=seq_toLoad_PAYWISE+1;
                
                mycurrent_row.id=seq_toLoad_PAYWISE;
                //alert("row ID"+mycurrent_row.id);
                var cell=document.createElement("TD");
                var anc=document.createElement("A");
                var url="javascript:loadTable_PAYWISE('"+mycurrent_row.id+"')";
                anc.href=url;
                var txtedit=document.createTextNode("EDIT");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
                mycurrent_row.appendChild(cell);
              
                var cell2;
               // for(i=0;i<10;i++)
                 // alert(items[i]);
            cell2=document.createElement("TD");

                  var paywise_AcqNumber=document.createElement("input");
                  paywise_AcqNumber.type="hidden";
                  paywise_AcqNumber.name="paywise_AcqNumber";
                  paywise_AcqNumber.value=items[0];
                  cell2.appendChild(paywise_AcqNumber);
                  var currentText=document.createTextNode(items[0]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                   
            cell2=document.createElement("TD");

                  var paywise_vouNo=document.createElement("input");
                  paywise_vouNo.type="hidden";
                  paywise_vouNo.name="paywise_vouNo";
                  paywise_vouNo.value=items[1];
                  cell2.appendChild(paywise_vouNo);
                  var currentText=document.createTextNode(items[1]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
                  
             
            cell2=document.createElement("TD");
                  var paywise_Amount=document.createElement("input");
                  paywise_Amount.type="hidden";
                  paywise_Amount.name="paywise_Amount";
                  paywise_Amount.value=items[2];
                  cell2.appendChild(paywise_Amount);
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
            
            cell2=document.createElement("TD");
                  var paywise_remark=document.createElement("input");
                  paywise_remark.type="hidden";
                  paywise_remark.name="paywise_remark";
                  paywise_remark.value=items[3];
                  cell2.appendChild(paywise_remark);
                   var currentText=document.createTextNode(items[3]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
                tbody.appendChild(mycurrent_row);  
        }
       //-- payment main grid ending
       
       //-- payment sub grid starting 
        var pay_det_VoucherNo=baseResponse.getElementsByTagName("pay_det_VoucherNo");
        
        for(var j=0;j<pay_det_VoucherNo.length;j++)
        {
             var details_tbody=document.getElementById("grid_body_PAYWISE_DETAILS");
         
                var items=new Array();
                items[0]=baseResponse.getElementsByTagName("pay_det_VoucherNo")[j].firstChild.nodeValue;
                items[1]=baseResponse.getElementsByTagName("AHcode")[j].firstChild.nodeValue;
                items[2]=baseResponse.getElementsByTagName("AHdesc")[j].firstChild.nodeValue;
                items[3]=baseResponse.getElementsByTagName("SL_Type")[j].firstChild.nodeValue;
                 
                 if(items[3]==0)
                    items[3]="";  
                    
                items[4]=baseResponse.getElementsByTagName("SL_Desc")[j].firstChild.nodeValue;

                if(items[4]=="null")
                    items[4]="";           
                
                items[5]=baseResponse.getElementsByTagName("SL_Code")[j].firstChild.nodeValue;
                
                 if(items[5]==0)
                    items[5]="";  
                    
                items[6]=baseResponse.getElementsByTagName("desc_type")[j].firstChild.nodeValue;
    
                if(items[6]=="null")
                    items[6]="";                //"Not Available";
                
                items[7]=baseResponse.getElementsByTagName("sub_amount")[j].firstChild.nodeValue;
                items[8]=baseResponse.getElementsByTagName("sub_part")[j].firstChild.nodeValue;
                
                if(items[8]=="null")
                  items[8]="";
                
                var mycurrent_row=document.createElement("TR");
                seq_toLoad_PAYWISE_DETAILS=seq_toLoad_PAYWISE_DETAILS+1;
                
                mycurrent_row.id=seq_toLoad_PAYWISE_DETAILS;
                //alert("row ID"+mycurrent_row.id);
                var cell=document.createElement("TD");
                var anc=document.createElement("A");
                var url="javascript:loadTable_PAYWISE_DETAILS('"+mycurrent_row.id+"')";
                anc.href=url;
                var txtedit=document.createTextNode("EDIT");
                anc.appendChild(txtedit);
                cell.appendChild(anc);
                mycurrent_row.appendChild(cell);
              
                var cell2;
              
                     cell2=document.createElement("TD");
        
                          var paywiseDETAILS_vouNo=document.createElement("input");
                          paywiseDETAILS_vouNo.type="hidden";
                          paywiseDETAILS_vouNo.name="paywiseDETAILS_vouNo";
                          paywiseDETAILS_vouNo.value=items[0];
                          cell2.appendChild(paywiseDETAILS_vouNo);
                          var currentText=document.createTextNode(items[0]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                          
                      cell2=document.createElement("TD");
        
                          var paywiseDETAILS_AccHEAD=document.createElement("input");
                          paywiseDETAILS_AccHEAD.type="hidden";
                          paywiseDETAILS_AccHEAD.name="paywiseDETAILS_AccHEAD";
                          paywiseDETAILS_AccHEAD.value=items[1];
                          cell2.appendChild(paywiseDETAILS_AccHEAD);
                          var currentText=document.createTextNode(items[1]+'-'+items[2]);
                          cell2.appendChild(currentText);
                          mycurrent_row.appendChild(cell2);
                    
                      cell2=document.createElement("TD");
                           var SL_type_DETAILS=document.createElement("input");
                          SL_type_DETAILS.type="hidden";
                          SL_type_DETAILS.name="SL_type_DETAILS";
                          SL_type_DETAILS.value=items[3];
                          cell2.appendChild(SL_type_DETAILS);
                           var currentText=document.createTextNode(items[4]);
                          cell2.appendChild(currentText);
                           mycurrent_row.appendChild(cell2);
                           
                     cell2=document.createElement("TD");
                          var SL_code_DETAILS=document.createElement("input");
                          SL_code_DETAILS.type="hidden";
                          SL_code_DETAILS.name="SL_code_DETAILS";
                          SL_code_DETAILS.value=items[5];
                          cell2.appendChild(SL_code_DETAILS);
                           var currentText=document.createTextNode(items[6]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                        
                    cell2=document.createElement("TD");
                          var paywiseDETAILS_Amount=document.createElement("input");
                          paywiseDETAILS_Amount.type="hidden";
                          paywiseDETAILS_Amount.name="paywiseDETAILS_Amount";
                          paywiseDETAILS_Amount.value=items[7];
                          cell2.appendChild(paywiseDETAILS_Amount);
                           var currentText=document.createTextNode(items[7]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                    
                    cell2=document.createElement("TD");
                          var paywiseDETAILS_parti=document.createElement("input");
                          paywiseDETAILS_parti.type="hidden";
                          paywiseDETAILS_parti.name="paywiseDETAILS_parti";
                          paywiseDETAILS_parti.value=items[8];
                          cell2.appendChild(paywiseDETAILS_parti);
                           var currentText=document.createTextNode(items[8]);
                          cell2.appendChild(currentText);
                        mycurrent_row.appendChild(cell2);
                     
                details_tbody.appendChild(mycurrent_row);
        }
        //-- payment sub grid ending 
        var acq_XML_flag_count=baseResponse.getElementsByTagName("acq_XML_flag");
        //var count=0; 
        
        for(var v=0;v<acq_XML_flag_count.length;v++)
        {
            var acq_XML_flag=baseResponse.getElementsByTagName("acq_XML_flag")[v].firstChild.nodeValue;
            //alert(acq_XML_flag)
            if(acq_XML_flag=="true")
            {
                var Acq_tbody=document.getElementById("grid_body_Acq");
               
                var acq_Vou_NO=baseResponse.getElementsByTagName("acq_Vou_NO");
                
                for(var k=0;k<acq_Vou_NO.length;k++)
                {
                        var items=new Array();
                        items[0]=baseResponse.getElementsByTagName("acq_roll_NO")[k].firstChild.nodeValue;
                        items[1]=baseResponse.getElementsByTagName("acq_Vou_NO")[k].firstChild.nodeValue;
                        items[2]=baseResponse.getElementsByTagName("disbur_off_id")[k].firstChild.nodeValue;
                        items[3]=baseResponse.getElementsByTagName("disbur_off_name")[k].firstChild.nodeValue;
                        items[4]=baseResponse.getElementsByTagName("emp_id")[k].firstChild.nodeValue;
                        items[5]=baseResponse.getElementsByTagName("emp_disgn")[k].firstChild.nodeValue;
                        items[6]=baseResponse.getElementsByTagName("tot_Acq_amt")[k].firstChild.nodeValue;
                        items[7]=baseResponse.getElementsByTagName("acq_remarks")[k].firstChild.nodeValue;
                        
                        if(items[7]=="null")
                            items[7]="";
                        
                        var mycurrent_row=document.createElement("TR");
                        seq_toLoad_Acq=parseInt(seq_toLoad_Acq)+1;
                        mycurrent_row.id=seq_toLoad_Acq;
                        //alert("row ID"+mycurrent_row.id);
                        var cell=document.createElement("TD");
                        var anc=document.createElement("A");
                        var url="javascript:loadTable_Acq('"+mycurrent_row.id+"')";
                        anc.href=url;
                        var txtedit=document.createTextNode("EDIT");
                        anc.appendChild(txtedit);
                        cell.appendChild(anc);
                        mycurrent_row.appendChild(cell);
                      
                        var cell2;
                      
                              cell2=document.createElement("TD");
                
                                  var Acq_rollNo=document.createElement("input");
                                  Acq_rollNo.type="hidden";
                                  Acq_rollNo.name="Acq_rollNo";
                                  Acq_rollNo.value=items[0];
                                  cell2.appendChild(Acq_rollNo);
                                  var currentText=document.createTextNode(items[0]);
                                  cell2.appendChild(currentText);
                                  mycurrent_row.appendChild(cell2);
                            
                             cell2=document.createElement("TD");
                
                                  var Acq_vouNo=document.createElement("input");
                                  Acq_vouNo.type="hidden";
                                  Acq_vouNo.name="Acq_vouNo";
                                  Acq_vouNo.value=items[1];
                                  cell2.appendChild(Acq_vouNo);
                                  var currentText=document.createTextNode(items[1]);
                                  cell2.appendChild(currentText);
                                  mycurrent_row.appendChild(cell2);
                                  
                            
                              cell2=document.createElement("TD");
                                   var Acq_offID=document.createElement("input");
                                  Acq_offID.type="hidden";
                                  Acq_offID.name="Acq_offID";
                                  Acq_offID.value=items[2];
                                  cell2.appendChild(Acq_offID);
                                   var currentText=document.createTextNode(items[3]);
                                  cell2.appendChild(currentText);
                                   mycurrent_row.appendChild(cell2);
                                   
                             cell2=document.createElement("TD");
                                  var Acq_empID=document.createElement("input");
                                  Acq_empID.type="hidden";
                                  Acq_empID.name="Acq_empID";
                                  Acq_empID.value=items[4];
                                  cell2.appendChild(Acq_empID);
                                   var currentText=document.createTextNode(items[5]);
                                  cell2.appendChild(currentText);
                                mycurrent_row.appendChild(cell2);
                                
                             cell2=document.createElement("TD");
                                  var Acq_Amount=document.createElement("input");
                                  Acq_Amount.type="text";
                                  Acq_Amount.name="Acq_Amount";
                                  Acq_Amount.value=items[6];
                                  if (window.navigator.appName.toLowerCase().indexOf("microsoft") > -1)         // IE;
                                  { 
                                          Acq_Amount.onkeypress=function()
                                          {
                                             return limit_amt(this,event);
                                          }
                                          Acq_Amount.onblur=function()
                                          {
                                             valid_amt(this);
                                          }
                                  }
                                  else
                                  {
                                      if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
                                      { 
                                                Acq_Amount.setAttribute("onkeypress","return limit_amt(this,event)");
                                                Acq_Amount.setAttribute("onblur","valid_amt(this)");
                                      }
                                      else
                                        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
                                    }
                                  cell2.appendChild(Acq_Amount);
                                mycurrent_row.appendChild(cell2);
                            
                         cell2=document.createElement("TD");
                                  var Acq_particular=document.createElement("input");
                                  Acq_particular.type="text";
                                  Acq_particular.name="Acq_particular";
                                  Acq_particular.value=items[7];
                                  if(window.navigator.appName.toLowerCase().indexOf("microsoft") > -1)         // IE;
                                  { 
                                          Acq_particular.onkeypress=function()
                                          {
                                             return check_leng(this.value);
                                          }
                                  }
                                  else
                                  {
                                      if (window.navigator.appName.toLowerCase().indexOf("netscape") > -1) // Firefox
                                      { 
                                                Acq_particular.setAttribute("onkeypress","return check_leng(this.value)");
                                      }
                                      else
                                        alert("Error: This application does not support your browser.  Try again using IE or Firefox.");
                                    }
                                  cell2.appendChild(Acq_particular);
                                  mycurrent_row.appendChild(cell2);
                             
                        Acq_tbody.appendChild(mycurrent_row);
                }
            }
      }
            
    }
    else if(flag=="failure")
    {
        alert("Loading failed")
    }
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
        //doFunction('load_Voucher_No','null');
    }
    else
    {
      document.getElementById("txtReceipt_No").value="";
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
                 doFunction_voucher('load_Voucher_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
                    cmbSL_Code.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="-- Select Voucher Number --";
                    option.value="";
                    try
                    {
                        cmbSL_Code.add(option);
                    }catch(errorObject)
                    {
                        cmbSL_Code.add(option,null);
                    }
               }
        }
    }
}

/*
//-----------------------
function loadTable_PAYWISE_existing_Vrs(scod)       // This is specially to avoid deletion of previous payment vouchers
{
        com_PAYWISE_id=scod;                                    // to identify in UPDATE_GRID ,which row loaded 
        clearall_PAYWISE();
     
        var r=document.getElementById(scod);
        var rcells=r.cells;
        try{document.getElementById("txtPayWise_Voucher_No").value=rcells.item(1).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayWise_date").value=rcells.item(2).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayWise_Amount").value=rcells.item(3).firstChild.value;}catch(e){}
        try{document.getElementById("txtPayWiseRemarks").value=rcells.item(4).firstChild.value;}catch(e){}
        try{document.getElementById("txtNo_of_AcqRolls").value=rcells.item(5).firstChild.value;}catch(e){}
        
    document.frmSelfCheque_Edit.cmdupdate_PAYWISE.style.display='block';
    //document.frmSelfCheque_Edit.cmddelete_PAYWISE.disabled=false;
    document.frmSelfCheque_Edit.cmdadd_PAYWISE.style.display='none';
}
*/