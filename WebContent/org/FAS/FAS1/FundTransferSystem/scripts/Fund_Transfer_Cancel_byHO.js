//alert("gokul")
var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
var seq=0;
var job_flag;
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

function byUnitAndOfficeChange()
{
    doFunction('load_Voucher_No','null');
}  

/////////////////////////////////////////////   doFunction()  /////////////////////////////////////////////////////

function doFunction(Command,param)
{   
         if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmFundTrs_Cancel_byHO.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
           var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../Fund_Transfer_Cancel_byHO.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            //alert(url)
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
            var txtCrea_date= document.frmFundTrs_Cancel_byHO.txtCrea_date.value
            var  txtVoucher_No=document.getElementById("txtVoucher_No").value;
            if(txtVoucher_No!="")
            {
            var url="../../../../../Fund_Transfer_Cancel_byHO.view?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
 

///////////////////////////////////// loadoffice ///////
function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
        var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
        var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
        document.getElementById("txtSub_Office_code").value=oid;
        document.getElementById("txtOfficeName").value=oname;
        document.getElementById("txtDebitAccCode").value="";
        document.getElementById("txtSubBankAccountNo").value="";
        document.getElementById("txtSubBankId").value="";
        document.getElementById("txtSubBranchId").value="";
        document.getElementById("txtSubBankName").value="";
    }
    else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     alert("Office Id '"+oid+"' doesn't Exist");
     document.getElementById("txtSub_Office_code").value="";
     document.getElementById("txtOfficeName").value="";
    }
  
}

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
    document.getElementById("txtReferenceNo").value="";
    document.getElementById("txtReferenceDate").value="";
     document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    //document.getElementById("txtAuth_By").value="";
    //document.getElementById("Auth_By").value="";
    
    //document.getElementById("txtReferNO_edit").value="";
    //document.getElementById("txtReferDate_edit").value="";
    //document.getElementById("txtRemak_edit").value=""; 
    
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

/////////////////////////////////////////////   checkNull() by User /////////////////////////////////////////////////////

function checkNull()
{
var tbody=document.getElementById("grid_body");
 if(window.confirm('Do you want to Cancel?'))
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
                alert("Enter the Date of Creation");
                //document.getElementById("txtCrea_date").focus();
                return false;    
            }
            if(document.getElementById("txtVoucher_No").value=="")
            {
                alert("Select the Voucher Number");
                //document.getElementById("txtVoucher_No").focus();
                return false;    
            }
            /*
            if(document.getElementById("txtAuth_By").value.length==0)
            {
                 alert("Enter Name of the Authorized person under Modification Details");
                //document.getElementById("txtReferNO_edit").focus();
                return false;    
            }
            */
        return true;
    }
    else
     return false;
}


////////////////////////////////////////////// load Voucher Number           /////////////////////

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
function load_Voucher_Details(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
       var MasHeadCode=baseResponse.getElementsByTagName("MasHeadCode")[0].firstChild.nodeValue;
       var accNo=baseResponse.getElementsByTagName("accNo")[0].firstChild.nodeValue;
       var bk_name=baseResponse.getElementsByTagName("bk_name")[0].firstChild.nodeValue;
       var bk_id=baseResponse.getElementsByTagName("bk_id")[0].firstChild.nodeValue;
       var br_id=baseResponse.getElementsByTagName("br_id")[0].firstChild.nodeValue;
       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       var REF_NO=baseResponse.getElementsByTagName("REF_NO")[0].firstChild.nodeValue;
       var referDate=baseResponse.getElementsByTagName("referDate")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
          
          document.getElementById("txtCash_Acc_code").value=MasHeadCode; 
          document.getElementById("txtBankAccountNo").value=accNo;
          document.getElementById("txtBankName").value=bk_name;
          document.getElementById("txtBankId").value=bk_id;
          document.getElementById("txtBranchId").value=br_id;
          if(REF_NO!="null")
            document.getElementById("txtReferenceNo").value=REF_NO;
          else
            document.getElementById("txtReferenceNo").value="";
          if(referDate!="null")
            document.getElementById("txtReferenceDate").value=referDate;
          else
             document.getElementById("txtReferenceDate").value="";
         
          document.getElementById("txtAmount").value=Total_amt;
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
              items[0]=baseResponse.getElementsByTagName("Office_id")[k].firstChild.nodeValue;   
              items[1]=baseResponse.getElementsByTagName("HO_acc_unitid")[k].firstChild.nodeValue;   
              var off_name=baseResponse.getElementsByTagName("Office_name")[k].firstChild.nodeValue;   
              var HO_unit_name=baseResponse.getElementsByTagName("HO_acc_unitName")[k].firstChild.nodeValue;   
              
              if(items[1]==0)
              {
                items[1]="";
              }
            
                if(HO_unit_name!="null")
                {
                    items[2]=off_name+"-"+HO_unit_name;
                }
                else
                {
                    items[2]=off_name;
                }
           
            items[3]=baseResponse.getElementsByTagName("AHcode")[k].firstChild.nodeValue;   
            items[4]=baseResponse.getElementsByTagName("off_bankAccNo")[k].firstChild.nodeValue;   
            items[5]=baseResponse.getElementsByTagName("off_bank_id")[k].firstChild.nodeValue;   
            
            items[6]=baseResponse.getElementsByTagName("off_branch_id")[k].firstChild.nodeValue;
            items[7]=baseResponse.getElementsByTagName("off_bank_name")[k].firstChild.nodeValue;
            
            items[8]=baseResponse.getElementsByTagName("che_or_DD")[k].firstChild.nodeValue;
            
            items[9]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;            
            items[10]=baseResponse.getElementsByTagName("che_DD_date")[k].firstChild.nodeValue;    
            
            
            if (items[9]=="null") 
            {
              items[9]="";
            }
            if (items[10]=="null") 
            {
              items[10]="";
            }
           
            
            
            items[11]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
            items[12]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
            if(items[12]=="null")
            items[12]="";

        tbody=document.getElementById("grid_body");
         var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
      /*
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        var url="javascript:loadTable('"+mycurrent_row.id+"')";
        anc.href=url;
        var txtedit=document.createTextNode("EDIT");
        anc.appendChild(txtedit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        var i=0; 
        */
        var cell2;
        
             cell2=document.createElement("TD");

                  var Sub_Office_code=document.createElement("input");
                  Sub_Office_code.type="hidden";
                  Sub_Office_code.name="Sub_Office_code";
                  Sub_Office_code.value=items[0];
                  cell2.appendChild(Sub_Office_code);
                  
                  var HO_Unit_ID=document.createElement("input");
                  HO_Unit_ID.type="hidden";
                  HO_Unit_ID.name="HO_Unit_ID";
                  HO_Unit_ID.value=items[1];
                  cell2.appendChild(HO_Unit_ID);
                  
                  var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD");
                  var Sub_Acc_Head_Code=document.createElement("input");
                  Sub_Acc_Head_Code.type="hidden";
                  Sub_Acc_Head_Code.name="Sub_Acc_Head_Code";
                  Sub_Acc_Head_Code.value=items[3];
                  cell2.appendChild(Sub_Acc_Head_Code);
                  
                   var Sub_Bank_Acc_No=document.createElement("input");
                  Sub_Bank_Acc_No.type="hidden";
                  Sub_Bank_Acc_No.name="Sub_Bank_Acc_No";
                  Sub_Bank_Acc_No.value=items[4];
                  cell2.appendChild(Sub_Bank_Acc_No);
                  
                  var Sub_Bank_Id=document.createElement("input");
                  Sub_Bank_Id.type="hidden";
                  Sub_Bank_Id.name="Sub_Bank_Id";
                  Sub_Bank_Id.value=items[5];
                  cell2.appendChild(Sub_Bank_Id);
                  
                  var Sub_Branch_Id=document.createElement("input");
                  Sub_Branch_Id.type="hidden";
                  Sub_Branch_Id.name="Sub_Branch_Id";
                  Sub_Branch_Id.value=items[6];
                  cell2.appendChild(Sub_Branch_Id);
                  
                  var Sub_Bank_Name=document.createElement("input");
                  Sub_Bank_Name.type="hidden";
                  Sub_Bank_Name.name="Sub_Bank_Name";
                  Sub_Bank_Name.value=items[7];
                  cell2.appendChild(Sub_Bank_Name);
                  
                 var Cheque_DD=document.createElement("input");
                  Cheque_DD.type="hidden";
                  Cheque_DD.name="Cheque_DD";
                  Cheque_DD.value=items[8];
                  cell2.appendChild(Cheque_DD);

                 var Cheque_DD_NO=document.createElement("input");
                  Cheque_DD_NO.type="hidden";
                  Cheque_DD_NO.name="Cheque_DD_NO";
                  Cheque_DD_NO.value=items[9];
                  cell2.appendChild(Cheque_DD_NO);
                  var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
            cell2=document.createElement("TD");
                 var Cheque_DD_date=document.createElement("input");
                  Cheque_DD_date.type="hidden";
                  Cheque_DD_date.name="Cheque_DD_date";
                  Cheque_DD_date.value=items[10];
                  cell2.appendChild(Cheque_DD_date);
                  var currentText=document.createTextNode(items[10]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
              cell2=document.createElement("TD");
                 var sl_amt=document.createElement("input");
                  sl_amt.type="hidden";
                  sl_amt.name="sl_amt";
                  sl_amt.value=items[11];
                  cell2.appendChild(sl_amt);

                  var particular=document.createElement("input");           // Particulars Added to grid b4 the Amount hidden Node but after  amount hidden box    
                  particular.type="hidden";
                  particular.name="particular";
                  particular.value=items[12];
            //    particular.style.display='none';
                  cell2.appendChild(particular);

                  var currentText=document.createTextNode(items[11]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
       
        tbody.appendChild(mycurrent_row);
        
        
        }
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
        var cmbSL_Code=document.getElementById("txtVoucher_No");   
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
                    var cmbSL_Code=document.getElementById("txtVoucher_No");   
                    cmbSL_Code.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="-- Select Receipt Number --";
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
