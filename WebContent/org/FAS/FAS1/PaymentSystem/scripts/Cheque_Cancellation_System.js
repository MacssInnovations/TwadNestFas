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
        if(document.getElementById("txtDoc_date").value.length==0)
        {
            alert("Enter the Date of Creation");
            //document.getElementById("txtDoc_date").focus();
            return false;    
        }
        if(document.getElementById("txtCrea_date").value.length==0)
        {
              alert("Enter the Cheque Cancelation Date");
    //document.getElementById("txtCrea_date").focus();
            return false;    
         }
        if(document.getElementById("txtReceipt_No").value=="")
        {
            alert("Select the Receipt Number");
            //document.getElementById("txtDoc_date").focus();
            return false;    
        }
      
       
    if(document.frmCheque_Cancel.txtCheque_DD2[0].checked==true)
    {
        if(document.getElementById("txtCheque_DD_date2").value=="")
        {
            alert("Enter the New Cheque Date");
            document.getElementById("txtCheque_DD_date2").focus();
            return false;
        } 
        if(document.getElementById("txtCheque_DD_NO2").value=="")
        {
            alert("Enter the New Cheque Number");
            document.getElementById("txtCheque_DD_NO2").focus();
            return false;
        }
    }
  
    if(document.getElementById("txtAmount2").value.length==0)
    {
        alert("Enter the new cheque amount");
        //document.getElementById("txtOperation_mode").focus();
        return false;
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
    document.getElementById("txtDoc_date").value="";
    document.getElementById("doc_type").value="";
    document.getElementById("txtAmount").value="";
    document.getElementById("txtCheque_DD_date").value="";
    document.getElementById("txtCheque_DD_NO2").value="";
    document.getElementById("txtCheque_DD_date2").value="";
    document.getElementById("txtCheque_DD").value="";
    document.getElementById("txtAmount2").value="";
    document.getElementById("txtRemarks").value="";
   
  
}

function doFunction_voucher(Command,param)
{  //alert("dofuntion");
       var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
       var txtCB_Year=document.getElementById("txtCB_Year").value;
       var txtCB_Month=document.getElementById("txtCB_Month").value;
       var txtDoc_date= document.getElementById("txtDoc_date").value;
       var doc_type=document.getElementById("doc_type").value;
       var cheq_type=document.getElementById("txtCheque_DD").value;
  //     alert(cheq_type);
       var txtReceipt_No=document.getElementById("txtReceipt_No").value;
       var txtamount=document.getElementById("txtAmount").value;
       var txtCheque_DD_date=document.getElementById("txtCheque_DD_date").value;
       var txtCheque_DD2=document.getElementById("txtCheque_DD2").value;
       var iss_che=document.getElementById("txtCheque_DD").value;
       var iss_che_no=document.getElementById("txtCheque_DD_NO2").value
       var txtCheque_DD_date2=document.getElementById("txtCheque_DD_date2").value
       var iss_amt=document.getElementById("txtAmount2").value
       var remarks=document.getElementById("txtRemarks").value
       var txtCheque_DD_New=document.getElementById("txtCheq_DD_Issued").value;
       var txtoption=document.getElementById("txtoption").value;
       var txtCash_year=document.getElementById("txtCash_year").value;
       var txtCash_Month=document.getElementById("txtCash_Month").value;
       var txtCheque_DD=document.getElementById("txtCheque_DD").value;
       var txtCheq_DD_Issued=document.getElementById("txtCheq_DD_Issued").value;
       var txtCheque_No=document.getElementById("txtCheque_No").value;
       var txtCrea_date=document.getElementById("txtCrea_date").value;
       //alert("Creation Date****"+txtCrea_date);
     //  var txtCheque_DD3=document.getElementById("txtCheque_DD3");
       var txtAmount2=document.getElementById("txtAmount2").value;
             for (var k=0; k < document.frmCheque_Cancel.txtCheque_DD2.length; k++)
   {
   if (document.frmCheque_Cancel.txtCheque_DD2[k].checked)
      {
      var rad_val3 = document.frmCheque_Cancel.txtCheque_DD2[k].value;
     // alert("rad_val2"+rad_val2);
      }
   }
          for (var j=0; j < document.frmCheque_Cancel.txtCheque_DD3.length; j++)
   {
   if (document.frmCheque_Cancel.txtCheque_DD3[j].checked)
      {
      var rad_val2 = document.frmCheque_Cancel.txtCheque_DD3[j].value;
     // alert("rad_val2"+rad_val2);
      }
   }
       for (var i=0; i < document.frmCheque_Cancel.txtoption.length; i++)
   {
   if (document.frmCheque_Cancel.txtoption[i].checked)
      {
      var rad_val = document.frmCheque_Cancel.txtoption[i].value;
   //   alert("rad_val"+rad_val);
      }
   }
//alert("Command >> "+Command);
       //var txtAUTHORIZED_TO='C';
        if(Command=="chequenodetails")
        {  
           //clearGeneral_Detail();
          //  if(txtDoc_date.length!=0)
        	 var  txtCheque_No=document.getElementById("txtCheque_No").value;
            {
            var url="../../../../../Cheque_Cancellation_System.view?Command=chequenodetails&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
      
        else if(Command=="Voucher_Details")
        {  var opId=document.getElementById("optionid");
           // alert("opId"+opId.value);
           var txtoption=document.getElementById("txtoption").value;
           // alert(txtoption.checked)
            var  txtCheque_No=document.getElementById("txtCheque_No").value;
           // if(txtReceipt_No!="")
           
            { 
            	//alert("rad_val >> "+rad_val);
            if(rad_val=="M"){
            var url="../../../../../Cheque_Cancellation_System.view?Command=Voucher_Details&txtCheque_No="+txtCheque_No+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
            }
            else if(rad_val=="C") {
            var url="../../../../../Cheque_Cancellation_System_New.view?Command=Voucher_Details&txtCheque_No="+txtCheque_No+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            }
           //alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse_voucher(req);
            }   ;
                    req.send(null);
            }         
        }
       else if(Command=="Other_Details")
        {  
           // clearGeneral_Detail();
             var txtoption=document.getElementById("txtoption").value;
            var  txtCheque_No=document.getElementById("txtCheque_No").value;
            var  txtReceipt_No=document.getElementById("txtReceipt_No").value;
           // if(txtReceipt_No!="")
            {
           
             if(rad_val=="M"){
            var url="../../../../../Cheque_Cancellation_System.view?Command=Other_Details&txtCheque_No="+txtCheque_No+"&txtReceipt_No="+txtReceipt_No+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&txtDoc_date="+
            txtDoc_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
            }
            else if(rad_val=="C") {
            var url="../../../../../Cheque_Cancellation_System_New.view?Command=Other_Details&txtCheque_No="+txtCheque_No+"&txtReceipt_No="+txtReceipt_No+"&txtDoc_date="+
            txtDoc_date+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
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
        else if(Command=="Add"){
         var txtoption=document.getElementById("txtoption").value;
       if(rad_val=="M"){  
       var url="../../../../../Cheque_Cancellation_System.view?Command=Add";
      
           }
      else if (rad_val=="C"){
       var url= "../../../../../Cheque_Cancellation_System_New.view?Command=Add";
      
           }
        
                document.frmCheque_Cancel.action=url;
                document.frmCheque_Cancel.method="Post";
                document.frmCheque_Cancel.submit();

       
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
                chequenodetails_cheqwise(baseResponse);
            }        
        
            else if(Command=="Voucher_Details")
            {
               
                vouchernodetails(baseResponse);
            } 
            else if(Command=="Other_Details")
            {
               
                otherdetails(baseResponse);
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
           var items_dt=new Array();
           var Cheq_No=baseResponse.getElementsByTagName("Cheq_No");
           
         //  var doctype=baseResponse.getElementsByTagName("doctype");
        //   alert("dcoctype"+doctype);
            for(var k=0;k<Cheq_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Cheq_No")[k].firstChild.nodeValue;
               // items_dt[k]=baseResponse.getElementsByTagName("doctype")[k].firstChild.nodeValue;
                //alert("itmes"+items_dt[k]);
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
function  chequenodetails_cheqwise(baseResponse)
{
        
  var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  var txtCheque_No=document.getElementById("txtCheque_No");
  
  var txtcashyr=document.getElementById("txtCheque_No");
  if(flag=="success")
    {
           var items_id=new Array();
           var items_yr=new Array();
           var items_mon=new Array();
           var Cheq_No=baseResponse.getElementsByTagName("Cheq_No");
       //    var cashbookyear=baseResponse.getElementsByTagName("cashyr");
       //    var cashbookmonth=baseResponse.getElementsByTagName("cashmonth");
         //  var doctype=baseResponse.getElementsByTagName("doctype");
        //   alert("dcoctype"+doctype);
            for(var k=0;k<Cheq_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Cheq_No")[k].firstChild.nodeValue;
              //  items_yr[k]=baseResponse.getElementsByTagName("cashyr")[k].firstChild.nodeValue;
             //   items_mon[k]=baseResponse.getElementsByTagName("cashmonth")[k].firstChild.nodeValue;
               // items_dt[k]=baseResponse.getElementsByTagName("doctype")[k].firstChild.nodeValue;
                //alert("itmes"+items_dt[k]);
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
           //var items_id=new Array();
           var Rec_No=baseResponse.getElementsByTagName("Rec_No");
        
            /*for(var k=0;k<Rec_No.length;k++)
            {
                items_id[k]=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                
            }*/
       //  alert("len "+Rec_No.length);
            txtReceipt_No.length=0;
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
            
            for(var k=0;k<Rec_No.length;k++)
            {   
            	  var items_id=baseResponse.getElementsByTagName("Rec_No")[k].firstChild.nodeValue;
                  var option=document.createElement("OPTION");
                  option.text=items_id;
                  option.value=items_id;
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
       var newcheq=document.frmCheque_Cancel.txtCheque_DD.value;
       if(newcheq=='C')
        document.frmCheque_Cancel.txtCheq_DD_Issued[0].checked=true;
       else
        document.frmCheque_Cancel.txtCheq_DD_Issued[1].checked=true;
       //document.getElementById("txtCheq_DD_Issued").value=newcheq;
     //  alert("newcheq"+newcheq);
    var doctype=baseResponse.getElementsByTagName("doctype")[0].firstChild.nodeValue;
    if (doctype=='Fund Transfer'){
    alert("THIS IS FUND TRANSFER CHEQUE:ARE YOU SURE YOU WANT TO CANCEL THIS CHEQUE");
    
            
    }
      if (doctype=='Fund Transfer HO'){
    alert("THIS IS FUND TRANSFER FROM HEAD OFFICE CHEQUE:ARE YOU SURE YOU WANT TO CANCEL THIS CHEQUE");
               
    }
  }  
    else if(flag=="failure")
    {
        alert("Loading failed")
    }
}
/*function checkdt(t)
{
  //alert("show")
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date ');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        //alert("here it comes");
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
             if(currentYear<_Service_Period_Beg_Year)
            {
            alert('Entered date should be greater than or equal to '+_Service_Period_Beg_Year);
            t.value="";
            t.focus();
            return false;
            }
            if(currentYear > getCurrentYear())
            {
            
                    alert('Entered date should be less than current date');
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than current date');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date ');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}*/
/*function checkdt1(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){}
        //exception  start
        
         t.value=c;
      
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}*/
/*function checkdat()
{
     var dt=document.frmCheque_Cancel.txtCheque_DD_date2.value;
     //alert('dt...'+dt);
     
         var dateParts = dt.split("/");
        selectedDay = dateParts[0];
        selectedMonth = dateParts[1];
        selectedYear = dateParts[2];
        //alert('selectedYear...'+selectedYear);
        
        var year = new Date().getYear();
        var mon = new Date().getMonth();
        var d=new Date().getDate();
        
       
        if(selectedYear<year)
        {
           alert('Year should be greater than current Year');
           document.frmCheque_Cancel.txtCheque_DD_date2.value="";
           document.frmCheque_Cancel.txtCheque_DD_date2.focus();
       }
       
       else if(selectedYear==year)
       {
       if(selectedMonth<(mon+1))
       {
         alert('Month should be greater than current Month');
           document.frmCheque_Cancel.txtCheque_DD_date2.value="";
           document.frmCheque_Cancel.txtCheque_DD_date2.focus();
       }
       
       if(selectedMonth==(mon+1))
        {
          //alert('equal');
           if(selectedDay<d)
           {
           alert('Date should be greater than current Date');
           document.frmCheque_Cancel.txtCheque_DD_date2.value="";
           document.frmCheque_Cancel.txtCheque_DD_date2.focus();
           }
       }
       }
       
       return true;

}*/


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

function cheque(id){
// alert("here it comes"+id);
try
{
    var txtCheque_DD2=document.getElementById("div_cheque");
  //  alert(txtCheque_DD2.id);
   if(id=="N")
   {
    txtCheque_DD2.style.display="none";
     txtCheque_DD3.style.display="none";
     txtCheq_DD_Issued.style.display="none";
     
     //document.getElementByID("txtCheque_DD3").value="Y";
     document.getElementById("txtCheque_DD_NO2").value="";
     document.getElementById("txtCheque_DD_date2").value="";
     //document.getElementByID("txtCheq_DD_Issued").value="";
     document.getElementById("txtAmount2").value="";
   }
   else
   {
   txtCheque_DD2.style.display="block";
   txtCheque_DD3.style.display="block";
   txtCheq_DD_Issued.style.display="block";
   
   
   
   alert(document.getElementByID("txtCheque_DD3").value);
   if(document.getElementByID("txtCheque_DD3").value=="N"){
   document.getElementByID("txtAmount2").value==document.getElementByID("txtAmount").value
   }
    if(document.getElementById("txtCheque_DD_NO2").value=="")
    {
        alert("Enter the cheqnumber");
        document.getElementById("txtCheque_DD_NO2").focus();
        return false;
    }
  
   
   if(document.getElementById("txtCheque_DD_date2").value=="")
    {
        alert("select the date");
        document.getElementById("txtCheque_DD_date2").focus();
        return false;
    }
    if(document.getElementById("txtAmount2").value.length==0)
    {
        alert("Enter the new cheque amount");
        //document.getElementById("txtOperation_mode").focus();
        return false;
    }
   }
}
catch(e){ }
}


function check_amount(id){
var txtCheque_DD3=document.getElementById("txtCheque_DD3");
var txtAmount2=document.getElementById("txtAmount2");
var viewbtn=document.getElementById("viewbtn");

if(id=="N")
   {
    txtAmount2.disabled=true;
     viewbtn.disabled=true;
   }
else
    {
    txtAmount2.disabled=false;
    viewbtn.disabled=false;
    }
}


function validityfromdate()
{
var datefrom=document.frmCheque_Cancel.txtCheque_DD_date.value;

flag=0;
var d = datefrom.split("-");
if(d[2]>year)
{
alert("From Date Greater than current Date");
flag++;
document.frmCheque_Cancel.txt_datef.value="";
document.frmCheque_Cancel.txt_datef.value="";
}

if(d[2]==year)
{
  if (d[1]>month)
  {
    alert("From Date Greater than Current Date");
    flag++;
    document.frmCheque_Cancel.txt_datef.value="";
    document.frmCheque_Cancel.txt_datef.value="";
    }
   else if(d[1]==month)
   {
     if(d[0]>day)
     {
       alert("From Date Greater than Current Date");
       flag++;
       document.frmCheque_Cancel.txt_datef.value="";
       document.frmCheque_Cancel.txt_datef.value="";
        }
      }
     }
    if(flag>0)
    {
     return false
     }
   else
    return validitytodate();
  }

function check()
{
var datefrom=document.frmCheque_Cancel.txtCheque_DD_date.value;
//alert("the date from is"+datefrom);
var dateto=document.frmCheque_Cancel.txtCheque_DD_date2.value;
//alert("the date to is"+dateto);
var flag=0;
var d = datefrom.split("/");
var e = dateto.split("/");
if(e[2]<d[2])
{
//alert("newcheque year should be greater than oldcheque Year");
alert("New Cheque Date should be Greater than Oldcheque Date")
flag++;
    document.frmCheque_Cancel.txtCheque_DD_date2.value="";
    document.frmCheque_Cancel.txtCheque_DD_date2.focus();
}


if(e[2]==d[2])
{
  if (e[1]<d[1])
  {
   // alert("New Cheque month should be Greater than Oldcheque Month");
      alert("New Cheque Date should be Greater than Oldcheque Date")
    flag++;
    document.frmCheque_Cancel.txtCheque_DD_date2.value="";
    document.frmCheque_Cancel.txtCheque_DD_date2.focus();
    }
  else if(e[1]==d[1])
   {
     if(e[0]<d[0])
     {
       alert("New Cheque date should be Greater than Oldcheque Date");
       flag++;
      document.frmCheque_Cancel.txtCheque_DD_date2.value="";
      document.frmCheque_Cancel.txtCheque_DD_date2.focus();
        }
      }
     }
    if(flag>0)
    {
     return false
     }

  }

function check_dd_cheque()
 {
      var cheque_no= document.getElementById("txtCheque_DD_NO2").value;
      var doctype=document.getElementById("doc_type").value;
      
      var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
      if(doctype=='Payment'){
      //alert("inside payment");
      var url="../../../../../Cheque_Number_Check_forPAY.kv?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
     var req=getTransport();
      req.open("GET",url,true); 
      req.onreadystatechange=function()
         {
            handleResponse_cheque_no(req);
         }   
      req.send(null);
      }
      else{
      var url="../../../../../Cheque_Number_Check_FT_fromOffice.kv?Command=check_cheque_no&cheque_no="+cheque_no+"&cmbAcc_UnitCode="+cmbAcc_UnitCode;
      
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
                  temp=temp+"Voucher Number = "+baseResponse.getElementsByTagName("vo_no")[k].firstChild.nodeValue+"\n";
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
    {     //alert("insidecheckdt");
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
//BRS_BANK_BALANCE_UPDATE
//------------------to check the New Cheque Date-----------------

function Check_Date(_date)
{   check 
    var doc_date;
    doc_date=document.getElementById("txtCrea_date").value  
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
function check_currentdate()
{
var datefrom=document.frmCheque_Cancel.txtCrea_date.value;
alert("the date from is"+datefrom);
var dateto=document.frmCheque_Cancel.txtCheque_DD_date2.value;
//alert("the date to is"+dateto);
var flag=0;
var d = datefrom.split("/");
var e = dateto.split("/");
if(e[2]<d[2])
{
//alert("newcheque year should be greater than oldcheque Year");
alert("New Cheque Date should be within current cashbook year and month")
flag++;
    document.frmCheque_Cancel.txtCheque_DD_date2.value="";
    document.frmCheque_Cancel.txtCheque_DD_date2.focus();
}


if(e[2]==d[2])
{
  if (e[1]<d[1])
  {
   // alert("New Cheque month should be Greater than Oldcheque Month");
      alert("New Cheque Date should be within current cashbook year and month")
    flag++;
    document.frmCheque_Cancel.txtCheque_DD_date2.value="";
    document.frmCheque_Cancel.txtCheque_DD_date2.focus();
    }
 /* else if(e[1]==d[1])
   {
     if(e[0]<d[0])
     {
       alert("New Cheque date should be Greater than Oldcheque Date");
       flag++;
      document.frmCheque_Cancel.txtCheque_DD_date2.value="";
      document.frmCheque_Cancel.txtCheque_DD_date2.focus();
        }
      }*/
     }
    if(flag>0)
    {
     return false;
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
         //  alert(doc_type);
          var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;
            var yr=document.getElementById("txtCB_Year").value;
       var mon=document.getElementById("txtCB_Month").value;
       if(doc_type=="Payment"){
     window_BankAccNumber = window.open("../../../../../org/FAS/FAS1/PaymentSystem/jsps/BankPay_NonBill_ListAll_Cheq.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
     }
     else if(doc_type=="Fund Transfer"){
      window_BankAccNumber = window.open("../../../../../org/FAS/FAS1/FundTransferSystem/jsps/Fund_Transfer_ListAll_byOffice_cheq.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
     }
     else if(doc_type=="Fund Transfer HO"){
      window_BankAccNumber = window.open("../../../../../org/FAS/FAS1/FundTransferSystem/jsps/Fund_Transfer_ListAll_byHO_cheq.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
     }   //  window_BankAccNumber= window.open("BankPayNonBill_ListAll_SL.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode"&cmbOffice_code="+cmbOffice_code+"&yr="+yr+"&mon="+mon,"mywindow1","resizable=YES,scrollbars=yes"); 
         window_BankAccNumber.moveTo(250,250);    
    }

window.onunload=function()
{
if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
}
  function doParentBankAccNumbers(cheqno,cheqdate,cheqamount)
{
  // alert("inside doparent");
 //  alert(cheqno);
    document.getElementById("txtCheque_DD_NO2").value=cheqno;
    document.getElementById("txtCheque_DD_date2").value=cheqdate;
   // document.getElementById("txtBranch_Name").value=BranchName;
    document.getElementById("txtAmount2").value=cheqamount;
    check_currentdate();
    
   // if (window_BankAccNumber && window_BankAccNumber.open && !window_BankAccNumber.closed) window_BankAccNumber.close();
   // if this closed here it won't get result from servlet for bank's branches, so it's closed after get branches
}
function checkoption(id){

var txtCheque_DD3=document.getElementById("txtCheque_DD3");
var txtAmount2=document.getElementById("txtAmount2");
 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
       var cmbOffice_code=document.getElementById("cmbOffice_code").value;
      
//alert(id);
//alert(txtAmount2.value);
if(id=="M")
   {
            var opId=document.getElementById("optionid");
            opId.style.display="block";
             var txtCB_Year=document.getElementById("txtCB_Year").value;
     //  var txtCB_Month=document.getElementById("txtCB_Month").value;
             var txtCB_Month=document.getElementById("txtCB_Month").value;
            var url="../../../../../Cheque_Cancellation_System.view?Command=chequenodetails&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&cmbAcc_UnitCode="+
            cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
           // alert(url)
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
  
            var url="../../../../../Cheque_Cancellation_System_New.view?Command=chequenodetails_cheqwise&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCrea_date="+txtCrea_date;
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
function check_monthwise(id){
// alert("here it comes"+id);
try
{
    var txtCheque_DD2=document.getElementById("div_cheque");
  //  alert(txtCheque_DD2.id);
   if(id=="N")
   {
    txtCheque_DD2.style.display="none";
     txtCheque_DD3.style.display="none";
     txtCheq_DD_Issued.style.display="none";
   }
   else
   {
   txtCheque_DD2.style.display="block";
   txtCheque_DD3.style.display="block";
   txtCheq_DD_Issued.style.display="block";
  // alert(document.getElementByID("txtCheque_DD3").value);
   if(document.getElementByID("txtCheque_DD3").value=="N"){
   document.getElementByID("txtAmount2").value==document.getElementByID("txtAmount").value
   }
    if(document.getElementById("txtCheque_DD_NO2").value=="")
    {
        alert("Enter the cheqnumber");
        document.getElementById("txtCheque_DD_NO2").focus();
        return false;
    }
  
   
   if(document.getElementById("txtCheque_DD_date2").value=="")
    {
        alert("select the date");
        document.getElementById("txtCheque_DD_date2").focus();
        return false;
    }
    if(document.getElementById("txtAmount2").value.length==0)
    {
        alert("Enter the new cheque amount");
        //document.getElementById("txtOperation_mode").focus();
        return false;
    }
   }
}
catch(e){ }
}
/*function check_currentdate()
         {
       var flag=0;
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       alert(month);
       alert(year);
     //  var cur_date=day+"/"+month+"/"+year;
     //  alert (cur_date);
       var newcheque_date=document.frmCheque_Cancel.txtCheque_DD_date2.value;
       alert(newcheque_date);
       var td=today.split('/');
       var chdate=newcheque_date.split('/');
       var chmonth=chdate[1];
       var chyear=chdate[2]
       if(td[2]<chdate[2])
{
//alert("newcheque year should be greater than oldcheque Year");
alert("New Cheque Date should be within the current cash book month and year")
flag++;
    document.frmCheque_Cancel.txtCheque_DD_date2.value="";
    document.frmCheque_Cancel.txtCheque_DD_date2.focus();
}


if(year==chyear)
{
  if (month<chmonth)
  {
   // alert("New Cheque month should be Greater than Oldcheque Month");
      alert("New Cheque Date should be within the current cash book month and year")
    flag++;
    document.frmCheque_Cancel.txtCheque_DD_date2.value="";
    document.frmCheque_Cancel.txtCheque_DD_date2.focus();
    }
  else if(month[1]>chmonth)
   {
     alert("New Cheque Date should be within the current cash book month and year")
    flag++;
    document.frmCheque_Cancel.txtCheque_DD_date2.value="";
    document.frmCheque_Cancel.txtCheque_DD_date2.focus();
      }
     }
    if(flag>0)
    {
     return false
     } 
    
   
        
         }
*/
