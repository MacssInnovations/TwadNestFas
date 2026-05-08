var seq=0;
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

function doFunction(Command,param)
{   
       if(Command=="load_Voucher_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.frmBankPay_FinalBill.txtCrea_date.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../BankPay_NonBill_Cancel.view?Command=load_Voucher_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
            var txtCrea_date= document.frmBankPay_FinalBill.txtCrea_date.value
            var  txtVoucher_No=document.getElementById("txtVoucher_No").value;
            if(txtVoucher_No!="")
            {
            var url="../../../../../BankPay_NonBill_Cancel.view?Command=load_Voucher_Details&txtVoucher_No="+txtVoucher_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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

function handleResponse(req)
{  
    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
           // alert("baseResponse:::"+baseResponse);
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
       var Mas_paid=baseResponse.getElementsByTagName("Mas_paid")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;
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
      
      var sltype =new Array(13);
       sltype[1]="Supplier";
       sltype[2]="Firms";
       sltype[3]="Assets";
       sltype[4]="ChequeBooks";
       sltype[5]="Offices";
       sltype[6]="Bank";
       sltype[7]="Employees";
       sltype[8]="Training";
       sltype[9]="Other Departments";
       sltype[10]="Project";
       sltype[11]="Contractors";
       sltype[12]="Scheme Owner";
       sltype[13]="Beneficiary";
                       
       var Mas_SL_type=baseResponse.getElementsByTagName("Mas_SL_type")[0].firstChild.nodeValue;
       var Mas_SL_code=baseResponse.getElementsByTagName("Mas_SL_code")[0].firstChild.nodeValue;
       
       if(Mas_SL_type!=0)
       {
           for(i=1;i<=13;i++)
           {
               if(Mas_SL_type==i)
               {
               document.getElementById("cmbMas_SL_type").value=sltype[i];
               break;
               }
           }
            var items_SLcode=new Array();
            var items_SLdesc=new Array();
            var Mas_SLCODE=baseResponse.getElementsByTagName("cid");
            
            for(var k=0;k<Mas_SLCODE.length;k++)
            {
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
        
        items[6]=baseResponse.getElementsByTagName("desc_type")[k].firstChild.nodeValue;
        if(items[6]=="null")
        items[6]="";
        
        if(baseResponse.getElementsByTagName("che_or_DD")[k] ==undefined)
        {
			items[7]="";
		}
		else
		{
			items[7]=baseResponse.getElementsByTagName("che_or_DD")[k].firstChild.nodeValue;
		}
       // items[7]=baseResponse.getElementsByTagName("che_or_DD")[k].firstChild.nodeValue;
        
         if(baseResponse.getElementsByTagName("che_DD_no")[k] ==undefined)
        {
			items[8]="";
		}
		else
		{
			items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;
		}
       // items[8]=baseResponse.getElementsByTagName("che_DD_no")[k].firstChild.nodeValue;
        
        /*if (items[8]=="null")
         {
           items[8]="";
         }
         */
		
		 if(baseResponse.getElementsByTagName("che_DD_date")[k] ==undefined)
        {
			items[9]="";
		}
		else
		{
			items[9]=baseResponse.getElementsByTagName("che_DD_date")[k].firstChild.nodeValue;
		}
      //  items[9]=baseResponse.getElementsByTagName("che_DD_date")[k].firstChild.nodeValue;
       /* if (items[9]=="null")
         {
           items[9]="";
         }*/
          if(baseResponse.getElementsByTagName("Agree_no")[k] ==undefined)
        {
			items[10]="";
		}
		else
		{
			items[10]=baseResponse.getElementsByTagName("Agree_no")[k].firstChild.nodeValue;
		}
        //items[10]=baseResponse.getElementsByTagName("Agree_no")[k].firstChild.nodeValue;
           if(baseResponse.getElementsByTagName("Agree_date")[k] ==undefined)
        {
			items[11]="";
		}
		else
		{
			items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
		}
       // items[11]=baseResponse.getElementsByTagName("Agree_date")[k].firstChild.nodeValue;
           if(baseResponse.getElementsByTagName("sub_paidto")[k] ==undefined)
        {
			items[12]="";
		}
		else
		{
			items[12]=baseResponse.getElementsByTagName("sub_paidto")[k].firstChild.nodeValue;
		}
        //items[12]=baseResponse.getElementsByTagName("sub_paidto")[k].firstChild.nodeValue;
               if(baseResponse.getElementsByTagName("sub_amount")[k] ==undefined)
        {
			items[13]="";
		}
		else
		{
			items[13]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
		}
       // items[13]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
                      if(baseResponse.getElementsByTagName("sub_part")[k] ==undefined)
        {
			items[14]="";
		}
		else
		{
			items[14]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
		}
       // items[14]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
       /* if(items[10]=="null")
        items[10]="";
        if(items[11]=="null")
        items[11]="";
        if(items[12]=="null")
        items[12]="";

        if(items[13]=="null")
        items[13]="";*/
        tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        mycurrent_row.id=seq;
        //alert("row ID"+mycurrent_row.id);
        
        var i=0;
        var cell2;
        
           cell2=document.createElement("TD");

                  
                  var currentText=document.createTextNode(items[0]+"-"+items[1]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
             cell2=document.createElement("TD");
                 
                   var currentText=document.createTextNode(items[2]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
             cell2=document.createElement("TD");
                  
                   var currentText=document.createTextNode(items[4]);
                  cell2.appendChild(currentText);
                   mycurrent_row.appendChild(cell2);
                   
            cell2=document.createElement("TD");
                
                   var currentText=document.createTextNode(items[6]);
                  cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
            cell2=document.createElement("TD"); 
                
                  var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
            cell2=document.createElement("TD");
               
                  var currentText=document.createTextNode(items[9]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
                
              cell2=document.createElement("TD");
                 // there are many hidden fields take out from here, which all needed only in edit form
                  var currentText=document.createTextNode(items[13]);
                  cell2.appendChild(currentText);
                  mycurrent_row.appendChild(cell2);
       
       
        tbody.appendChild(mycurrent_row);
        
        }
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
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRemarks").value="";
    document.getElementById("txtPaid_to").value="";
    document.getElementById("cmbMas_SL_type").value=""; 
    document.getElementById("cmbMas_SL_Code").value=""; 
    //clearall();
    //document.getElementById("txtAuth_By").value="";
    //document.getElementById("Auth_By").value="";
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

function checkNull_cancel()
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


///////////////////////////////////////////    TB_checking and Calender control return value handling



function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
    //alert(fromcal_dateCtrl.value+"GG"+blr_flag+fromcal_dateCtrl);
    if(blr_flag==1)             // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
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
