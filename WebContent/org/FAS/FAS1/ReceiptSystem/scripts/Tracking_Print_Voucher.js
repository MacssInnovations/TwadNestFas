
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



function doFunction(Command,param)
{   
 
      if(Command=="load_Voucher_No")
        {  
            cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            cmbSubSystemType=document.getElementById("cmbSubSystemType").value;
            txtVou_date=document.getElementById("txtVou_date").value;
            if(cmbSubSystemType=="")
            {
                alert("Select Sub-system type ");
                return false;
            }
            if( txtVou_date.length==0)
            {
                alert("Enter the date");
                return false;
            }
            
            if(cmbSubSystemType!="" && txtVou_date.length!=0)
            {
                var url="../../../../../Tracking_Print_Voucher.view?Command=load_Voucher_No&txtVou_date="+txtVou_date+"&cmbSubSystemType="+
                cmbSubSystemType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;
                var req=getTransport();
                
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
        }
        else if(Command=="load_Voucher_details")
        {
             cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
            cmbOffice_code=document.getElementById("cmbOffice_code").value;
            cmbSubSystemType=document.getElementById("cmbSubSystemType").value;
            txtVou_date=document.getElementById("txtVou_date").value;
            txtVoucher_No=document.getElementById("txtVoucher_No").value;
            if(cmbSubSystemType=="")
            {
                alert("Select Sub-system type ");
                return false;
            }
            if( txtVou_date.length==0)
            {
                alert("Enter the date");
                return false;
            }
            
            if(cmbSubSystemType!="" && txtVou_date.length!=0 && txtVoucher_No!="")
            {
                var url="../../../../../Tracking_Print_Voucher.view?Command=load_Voucher_details&txtVou_date="+txtVou_date+"&cmbSubSystemType="+
                cmbSubSystemType+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtVoucher_No="+txtVoucher_No;
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
           
            if(Command=="load_Voucher_No")
            {
                load_Voucher_No(baseResponse);
            }
            else if(Command=="load_Voucher_details")
            {
                load_Voucher_details(baseResponse);
            }
        
        }
    }
}

function load_Voucher_details(baseResponse)
{
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      if(flag=="success")
      {
         document.getElementById("com_value").value=baseResponse.getElementsByTagName("com_value")[0].firstChild.nodeValue;
         document.getElementById("amt").value=baseResponse.getElementsByTagName("amt")[0].firstChild.nodeValue;
      }
      else
      {
        document.getElementById("com_value").value="";  
        document.getElementById("amt").value="";  
        alert("Details not found");
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


function checkNull()
{
    
    if(document.getElementById("cmbAcc_UnitCode").value=="")
    {
        alert("Select the Account Unit code");
        document.getElementById("txtAcc_HeadDesc").focus();
        return false;    
    }
    if(document.getElementById("cmbOffice_code").value=="")
    {
        alert("Select the Office Code");
        document.getElementById("cmbOffice_code").focus();
        return false;
    }
    if(document.getElementById("txtVou_date").value.length==0)
    {
        alert("Enter the  Date");
        document.getElementById("txtVou_date").focus();
        return false;    
    }
     if(document.getElementById("cmbSubSystemType").value=="")
    {
        alert("Select the Sub-System type");
        document.getElementById("cmbSubSystemType").focus();
        return false;    
    }
    if(document.getElementById("txtVoucher_No").value=="")
    {
        alert("Select the Voucher Number");
        document.getElementById("txtVoucher_No").focus();
        return false;    
    }
    
return true;
}

///////////////////////////////////////////    TB_checking and Calender control return value handling

function call_mainJSP_script(fromcal_dateCtrl,blr_flag)     /////////Calender control return value handling
{
   // Nothing need to do... just leave it.. Because here we don't need to check TB..
       call_clr();
}

function call_date(dateCtrl)                        // TB_checking 
{
    call_clr();
    if(checkdt(dateCtrl))
    {
       // Nothing need to do... just leave it.. Because here we don't need to check TB..
    }
       
}

function call_clr()
{
        document.getElementById("cmbSubSystemType").value="";
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
        
        document.getElementById("com_value").value="";  
        document.getElementById("amt").value="";  
       
}

function clrForm()
{
    call_clr();
}
function exit()
{
    self.close();
}