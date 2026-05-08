var com_id;
var common_cmbSL_Code="";
var common_cmbSL_type="";
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
    doFunction('load_Receipt_No','null');
}
//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;

function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,500);
       winemp.moveTo(250,250); 
       winemp.focus();
    }
    else
    {
        winemp=null
    }
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.getElementById("txtAuth_By").value=emp;
doFunction('loademp','null');
}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}


function doFunction(Command,param)
{   
 
     
       if(Command=="load_Receipt_No")
        {  
           clearGeneral_Detail();
           var txtCrea_date= document.getElementById("txtCrea_date").value
           //var txtCash_Month_hid= document.Cash_Receipt_Edit_Form.txtCash_Month_hid.value
           //var txtCash_Month= document.Cash_Receipt_Edit_Form.txtCash_Month.value
           var  cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            
            if(txtCrea_date.length!=0)
            {
            var url="../../../../../DCBCash_Receipt_Cancel?Command=load_Receipt_No&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
        else if(Command=="loademp")
        {   //alert(eid);
            var eid=document.getElementById("txtAuth_By").value;
            var offid=document.getElementById("cmbOffice_code").value;
            var url="../../../../../Receipt_SL.view?Command=loademp&eid="+eid+"&offid="+offid;
            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
                    
        }
        else if(Command=="load_Receipt_Details")
        {  
            clearGeneral_Detail();
            var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var txtCrea_date= document.Cash_Receipt_Edit_Form.txtCrea_date.value
           var  txtReceipt_No=document.getElementById("txtReceipt_No").value;
           
            if(txtReceipt_No!="")
            {
            var url="../../../../../DCBCash_Receipt_Cancel?Command=load_Receipt_Details&txtReceipt_No="+txtReceipt_No+"&txtCrea_date="+txtCrea_date+"&cmbAcc_UnitCode="+
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
             //alert(Command)
            if(Command=="load_Receipt_No")
            {
                load_Receipt_No(baseResponse);
            }
            else if(Command=="loademp")
            {
                loadEmployee(baseResponse);
            }
            else if(Command=="load_Receipt_Details")
            {
                load_Receipt_Details(baseResponse);
            }
            
        }
    }
}

function loadEmployee(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  //alert("success");
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
        var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
        
        document.getElementById("txtAuth_By").value=eid;
        document.getElementById("Auth_By").value=ename+" - "+desig;
    }
    else 
    {
        var eid=document.getElementById("txtAuth_By").value;
        alert("Employee Id '"+eid+"' doesn't Exists under this office");
        document.getElementById("txtAuth_By").value="";
        document.getElementById("Auth_By").value="";
    }
} 


/////////////////////////////////////////////   CHANGE IN CR/DR () by User /////////////////////////////////////////////////////

function clearGeneral_Detail()
{
    document.getElementById("txtRecei_from").value="";
    //document.getElementById("txtReceipt_No").value="";  
    document.getElementById("txtAmount").value="";
    document.getElementById("txtRef_no").value="";
    document.getElementById("txtRef_date").value="";
    document.getElementById("txtRemarks").value="";
     
    document.getElementById("cmbMas_SL_type").value="";
    document.getElementById("cmbMas_SL_Code").value="";
   /* document.getElementById("txtD_ODep_OffId").value="";
    document.getElementById("txtD_ODep_Id").value="";
    document.getElementById("txtD_DepName").value="";
    document.getElementById("txtD_OffName").value="";
    */
    //document.getElementById("txtAuth_By").value="";
    //document.getElementById("Auth_By").value="";
    var tbody=document.getElementById("grid_body");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
}

function call_clr()
{
    document.getElementById("txtReceipt_No").value="";  
    clearGeneral_Detail();
}

function clrForm()
{
   // document.getElementById("txtCrea_date").value="";
    //document.getElementById("txtCash_year").value="";
    //document.getElementById("txtCash_Month_hid").value="";
   // document.getElementById("txtCash_Month").value="";
 if(window.confirm("Do you want to clear ALL fields ?"))
 {
    call_clr();
 }
}


/////////////////////////////////////// From checkNull_cancel  ///////////

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
         if(document.getElementById("txtCash_Acc_code").value.length==0)
        {
            alert("Enter the Cash A/c Code");
            //document.getElementById("txtCash_Acc_code").focus();
            return false;
        }
        
    /*    if(document.getElementById("txtCash_year").value.length==0)
        {
            alert("Enter the Cash Year");
            document.getElementById("txtCash_year").focus();
            return false;
        }
        
         if(document.getElementById("txtCash_Month_hid").value.length==0)
        {
            alert("Enter the Cash Month");
            document.getElementById("txtCash_Month").focus();
            return false;    
        }
        */
        if(document.getElementById("txtReceipt_No").value=="")
        {
            alert("Select the Receipt Number");
            //document.getElementById("txtReceipt_No").focus();
            return false;    
        }
        /*  Here Modification has taken out of form , b'coz it has separate interface
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

//////////////////////////////////////////////////////////////////////// from Cash_Receipt_edittion_rec/////////

function load_Receipt_No(baseResponse)
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
            option.text="--Select Receipt Number--";
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
            option.text="--Select Receipt Number--";
            option.value="";
            try
            {
                txtReceipt_No.add(option);
            }catch(errorObject)
            {
                txtReceipt_No.add(option,null);
            }
         alert("No Receipt Found");
    }
}
function load_Receipt_Details(baseResponse)
{
    
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    //alert("FF");
    if(flag=="success")
    {
      var Ref_No=baseResponse.getElementsByTagName("Ref_No")[0].firstChild.nodeValue;
      var Ref_Date=baseResponse.getElementsByTagName("Ref_Date")[0].firstChild.nodeValue;
       var Total_amt=baseResponse.getElementsByTagName("Total_amt")[0].firstChild.nodeValue;
       var Rec_From=baseResponse.getElementsByTagName("Rec_From")[0].firstChild.nodeValue;
       var Remak=baseResponse.getElementsByTagName("Remak")[0].firstChild.nodeValue;

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
       
      if(Ref_No!="null")
      document.getElementById("txtRef_no").value=Ref_No;
      else
      document.getElementById("txtRef_no").value="";
      
       if(Ref_Date!="null")
       document.getElementById("txtRef_date").value=Ref_Date;
       else
       document.getElementById("txtRef_date").value="";
      
       document.getElementById("txtAmount").value=Total_amt;
       if(Rec_From!="null")
       document.getElementById("txtRecei_from").value=Rec_From;
       else
       document.getElementById("txtRecei_from").value="";
     
      
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
        if(baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild!=null)
        items[7]=baseResponse.getElementsByTagName("sub_rec_frm")[k].firstChild.nodeValue;
        if(items[7]=="null")
        items[7]="";
        items[8]=baseResponse.getElementsByTagName("sub_amount")[k].firstChild.nodeValue;
        items[9]=baseResponse.getElementsByTagName("sub_part")[k].firstChild.nodeValue;
        if(items[9]=="null")
        items[9]="";
        
         tbody=document.getElementById("grid_body");
        var mycurrent_row=document.createElement("TR");
        seq=seq+1;
        
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
            
          /*
          cell2=document.createElement("TD");  
                 
                var currentText=document.createTextNode(items[7]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
           */  
             cell2=document.createElement("TD"); 
                 
                var currentText=document.createTextNode(items[8]);
                cell2.appendChild(currentText);
                mycurrent_row.appendChild(cell2);
              
              cell2=document.createElement("TD");
             
                 var currentText=document.createTextNode(items[9]);
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
    if(blr_flag==1)                 // which is used to find the receipt or voucher or payment (creation) date calling field,if so check trial balance
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
        //doFunction('load_Receipt_No','null');
    }
    else
    {
        var cmbSL_Code=document.getElementById("txtReceipt_No");   
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
                 doFunction('load_Receipt_No','null');                 //return true;
              }
             else if(flag=="failure")
               {
                    dateCtrl.value="";
                    alert("Trial Balance Closed");//return false;//
                    dateCtrl.focus();
                    var cmbSL_Code=document.getElementById("txtReceipt_No");   
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
