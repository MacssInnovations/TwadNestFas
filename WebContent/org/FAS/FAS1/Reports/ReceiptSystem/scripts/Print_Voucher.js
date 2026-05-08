


//Null check Validation
function nullcheck()
{
    if((document.frmReport.cmbAcc_UnitCode.value=="") || (document.frmReport.cmbAcc_UnitCode.value.length<=0) || (document.frmReport.cmbAcc_UnitCode.value=="0"))
    {
        alert("Please Select Accounting Unit");
        document.frmReport.cmbAcc_UnitCode.focus();
        return false;
    }
    if((document.frmReport.cmbOffice_code.value=="") || (document.frmReport.cmbOffice_code.value.length<=0) || (document.frmReport.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.frmReport.cmbOffice_code.focus();
        return false;
    
    }
    if((document.frmReport.cmbdoctype.value=="") || (document.frmReport.cmbdoctype.value.length<=0) || (document.frmReport.cmbdoctype.value=="0"))
    {
        alert("Please Select Document Type");
        document.frmReport.cmbdoctype.focus();
        return false;
    }
    
    if((document.frmReport.cbtype.value=="") || (document.frmReport.cbtype.value.length<=0) || (document.frmReport.cbtype.value=="0"))
    {
        alert("Please Select Receipt Type");
        document.frmReport.cbtype.focus();
        return false;
    }
   
return true;
}

function nullvoucher()
{
if((document.frmReport.fromid.value=="") || (document.frmReport.fromid.value.length<=0) || (document.frmReport.fromid.value=="0"))
    {
    alert("Please Enter From voucher value");
        document.frmReport.fromid.focus();
        return false;
    }
    if((document.frmReport.toid.value=="") || (document.frmReport.toid.value.length<=0) || (document.frmReport.toid.value=="0"))
    {
    alert("Please Enter To voucher value");
        document.frmReport.toid.focus();
        return false;
    }
    return true;
}

function nulldate()
{
if((document.frmReport.txtfromdate.value=="") || (document.frmReport.txtfromdate.value.length<=0))
    {
        alert("Please Enter From Date");
        document.frmReport.txtfromdate.focus();
        return false;
    }
    if((document.frmReport.txttodate.value=="") || (document.frmReport.txttodate.value.length<=0))
    {
        alert("Please Enter To Date");
        document.frmReport.txttodate.focus();
        return false;
    }
    return true;
}




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
    
    
function loadDocNo()
{
            
           ///var cboffid=document.getElementById("offid").value;
            var cbaccunit=document.getElementById("cmbAcc_UnitCode").value;
           
            //var cbaccunit=document.getElementById("accunit").value;
            var cboffid=document.getElementById("cmbOffice_code").value;
           
            var cbmon=document.getElementById("txtCB_Month").value;
            var cbye=document.getElementById("txtCB_Year").value;
            var rec_type=document.frmReport.cmbdoctype.value;
            var cbtype=document.frmReport.cbtype.value;
            if(cbye==null)
                alert("Enter year");
            //if(command=="voucherwise")
             // {
                    var url="../../../../../../Print_Voucher_LoadServ?cbaccunit="+cbaccunit+"&cboffid="+cboffid+"&cbmonth="+cbmon+"&cbyear="+cbye+"&rectype="+rec_type+"&cbtype="+cbtype;
                                //alert("callinf the servlet:::::::::"+url);
                                var req=getTransport();
                                req.open("GET",url,true);        
                                req.onreadystatechange=function()
                                {
                                    editDocNo(req);
                                }
                                req.send(null);
 
}


function loadDocNo1()
{
//alert("called.........");
var cboffid=document.getElementById("offid").value;
//alert(cboffid);
var cbaccunit=document.getElementById("accunit").value;
//alert(cbaccunit);
var cbmon=document.getElementById("txtCB_Month").value;
alert(cbmon);
var cbye=document.getElementById("txtCB_Year").value;
alert(cbye);
var rec_type=document.frmReport.cmbdoctype.value;
var cbtype=document.frmReport.cbtype.value;
if(cbye==null)
    alert("enter year");

var url="../../../../../../Print_Voucher_LoadServ?cbaccunit="+cbaccunit+"&cboffid="+cboffid+"&cbmonth="+cbmon+"&cbyear="+cbye+"&rectype="+rec_type+"&cbtype="+cbtype;
            var req=getTransport();
            req.open("GET",url,true);        
            req.onreadystatechange=function()
            {
            }
            req.send(null);
 
}
function editDocNo(req)
{
//alert("called");

if(req.readyState==4)
            {
                  if(req.status==200)
                  {      
                  
                        var vno=new Array();
                        var fromid="";
                        var toid="";
                        var response=req.responseXML.getElementsByTagName("response")[0];
                        var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                         if(flag=="success")
                        {
                        //alert("success");
                        var len=response.getElementsByTagName("voucher").length;
                        //alert(len);
                        fromid=document.getElementById("fromid");
                        toid=document.getElementById("toid");
                        fromid.innerHTML="";
                        toid.innerHTML="";
                       
                            for( var i=0;i<len;i++)
                            {
                            vno[i]=response.getElementsByTagName("voucher")[i].firstChild.nodeValue;
                            //alert(vno[i]);
                            }
                            var option=document.createElement("OPTION");
                             var option1=document.createElement("OPTION");
        option.text="--Select any doc no--";
        option.value="";
        option1.text="--Select any doc no--";
        option1.value="";
        
        //alert("show");
        try
            {
            fromid.add(option);
            toid.add(option1);
            }
            
            catch(errorObject)
                {
                fromid.add(option,null);
                toid.add(option1,null);
                }
        for(i=0;i<len;i++)
            {
            var option=document.createElement("OPTION");
            option.value=vno[i];
             option.text=vno[i];         
            var option1=document.createElement("OPTION");
            option1.value=vno[i];
             option1.text=vno[i];         
            try{
                fromid.add(option);
                toid.add(option1)
                }catch(error)
                {
                fromid.add(option,null);
                toid.add(option1,null);
                }
             }
             
         }    
        else 
                {
                fromid=document.getElementById("fromid");
                toid=document.getElementById("toid");
                fromid.innerHTML="";
                toid.innerHTML="";
                alert("No data found");
                }
            }  
        }
}


function loadType()
{
        //alert("called");
        var cmbtype=document.getElementById("cmbdoctype").value;
        //alert("first"+cmbtype);
        var cbtype=document.getElementById("cbtype");
        //alert("second"+cbtype);
        if(cmbtype=="REC")
            {
             cbtype.innerHTML="";
                    var option=document.createElement("OPTION");
                    var optionAll=document.createElement("OPTION");
                    var option1=document.createElement("OPTION");
                    var option2=document.createElement("OPTION");
                    option.text="--Select Type--";
                    option.value="";
                  
                    try
                    {
                        cbtype.add(option);            
                    }catch(errorObject)
                    {
                        cbtype.add(option,null);               
                    }
                    optionAll.text="All";
                    optionAll.value="ALL";
                  
                    try
                    {
                        cbtype.add(optionAll);            
                    }catch(errorObject)
                    {
                        cbtype.add(optionAll,null);               
                    }
                    
                    option1.text="Cash Receipt";
                    option1.value="CR";
                    try
                    {
                    cbtype.add(option1);
                    }
                    catch(errorObject)
                    {
                     cbtype.add(option1,null);
                    }
                    
                    option2.text="Bank Receipt";
                    option2.value="BR";
                    try
                    {
                    cbtype.add(option2);
                    }
                    catch(errorObject)
                    {
                    cbtype.add(option2,null);
                    }
                    
                    
            }
            else if(cmbtype=="PAY")
            {
             cbtype.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="--Select Type--";
                    option.value="";
                    try
                    {
                        cbtype.add(option);
                    }catch(errorObject)
                    {
                        cbtype.add(option,null);
                    }
                    var optionAll=document.createElement("OPTION");
                    optionAll.text="All";
                    optionAll.value="ALL";
                  
                    try
                    {
                        cbtype.add(optionAll);            
                    }catch(errorObject)
                    {
                        cbtype.add(optionAll,null);               
                    }
                    var option1=document.createElement("OPTION");
                    option1.text="Pending Bill";
                    option1.value="BPP";
                    try
                    {
                        cbtype.add(option1);
                    }catch(errorObject)
                    {
                        cbtype.add(option1,null);
                    }
                    var option2=document.createElement("OPTION");
                    option2.text="Final Head";
                    option2.value="BPF";
                    try
                    {
                        cbtype.add(option2);
                    }catch(errorObject)
                    {
                        cbtype.add(option2,null);
                    }
                    var option3=document.createElement("OPTION");
                    option3.text="Nil Payment";
                    option3.value="NP";
                    try
                    {
                       cbtype.add(option3);
                    }catch(errorObject)
                    {
                        cbtype.add(option3,null);
                    }
            }
            else if(cmbtype=="JOU")
            {
            cbtype.innerHTML="";
                    var option=document.createElement("OPTION");
                    option.text="--Select Type--";
                    option.value="";
                    try
                    {
                        cbtype.add(option);
                    }catch(errorObject)
                    {
                        cbtype.add(option,null);
                    }
                    var optionAll=document.createElement("OPTION");
                    optionAll.text="All";
                    optionAll.value="ALL";
                  
                    try
                    {
                        cbtype.add(optionAll);            
                    }catch(errorObject)
                    {
                        cbtype.add(optionAll,null);               
                    }
                    var option1=document.createElement("OPTION");
        
                    option1.text="Liablity Journal";
                    option1.value="LJV";
                    try
                    {
                        cbtype.add(option1);
                    }catch(errorObject)
                    {
                        cbtype.add(option1,null);
                    }
                    var option2=document.createElement("OPTION");
        
                    option2.text="General Journal";
                    option2.value="GJV";
                    try
                    {
                        cbtype.add(option2);
                    }catch(errorObject)
                    {
                        cbtype.add(option2,null);
                    }
                     var option3=document.createElement("OPTION");
        
                    option3.text="SJV";
                    option3.value="SJV";
                    try
                    {
                        cbtype.add(option3);
                    }catch(errorObject)
                    {
                        cbtype.add(option3,null);
                    }
            }
        if(cmbtype=="FR")
        {
         cbtype.innerHTML="";
                var option=document.createElement("OPTION");
//                var optionAll=document.createElement("OPTION");
                var option1=document.createElement("OPTION");
                var option2=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
              
                try
                {
                    cbtype.add(option);            
                }catch(errorObject)
                {
                    cbtype.add(option,null);               
                }
//                optionAll.text="All";
//                optionAll.value="ALL";
//              
//                try
//                {
//                    cbtype.add(optionAll);            
//                }catch(errorObject)
//                {
//                    cbtype.add(optionAll,null);               
//                }
                
                option1.text="By Office";
                option1.value="Office";
                try
                {
                cbtype.add(option1);
                }
                catch(errorObject)
                {
                 cbtype.add(option1,null);
                }
                
                option2.text="By HO";
                option2.value="HO";
                try
                {
                cbtype.add(option2);
                }
                catch(errorObject)
                {
                cbtype.add(option2,null);
                }
                
                
        }
        if(cmbtype=="FT")
        {
         cbtype.innerHTML="";
                var option=document.createElement("OPTION");
//                var optionAll=document.createElement("OPTION");
                var option1=document.createElement("OPTION");
                var option2=document.createElement("OPTION");
                option.text="--Select Type--";
                option.value="";
              
                try
                {
                    cbtype.add(option);            
                }catch(errorObject)
                {
                    cbtype.add(option,null);               
                }
//                optionAll.text="All";
//                optionAll.value="ALL";
//              
//                try
//                {
//                    cbtype.add(optionAll);            
//                }catch(errorObject)
//                {
//                    cbtype.add(optionAll,null);               
//                }
                
                option1.text="From Office";
                option1.value="From_Office";
                try
                {
                cbtype.add(option1);
                }
                catch(errorObject)
                {
                 cbtype.add(option1,null);
                }
                
                option2.text="From HO";
                option2.value="From_HO";
                try
                {
                cbtype.add(option2);
                }
                catch(errorObject)
                {
                cbtype.add(option2,null);
                }
                
                
        }
        
}


 function loadDate1()
         {
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
                 var monthArray =new Array("January", "February", "March", 
                           "April", "May", "June", "July", "August",
                           "September", "October", "November", "December");
                document.frmReport.txtCB_Yearwise.value=year;
                document.frmReport.txtCB_Monthwise.value=month;
                
                
     }

function enable_cheque(selection)
{
    if(selection=="voucherwise")
    {
        document.getElementById("VW").style.display='block';
//        document.frmReport.fromid.innerHTML="Select voucher no from";
//        document.frmReport.fromid.innerText="Select voucher no from";
//        document.frmReport.toid.innerHTML="Select voucher no to";
//        document.frmReport.toid.innerText="Select voucher no to";

                fromid=document.getElementById("fromid");
                toid=document.getElementById("toid");
                fromid.innerHTML="Select voucher no from";
                fromid.innerText="Select voucher no from";
                toid.innerHTML="Select voucher no to";
                toid.innerText="Select voucher no to";
               
       
        
         //var fromid=document.getElementById("fromid");
        //var toid=document.getElementById("toid");
         
         /*var option=document.createElement("OPTION");
         option.text="Select voucher no from";
            option.value="";
            try
            {
                fromid.add(option);
            }catch(errorObject)
            {
                fromid.add(option,null);
            }*/
       loadDocNo();
    } 
    else
    {
    document.getElementById("VW").style.display='none';
    }
    
    if(selection=="datewise")
    {
         document.getElementById("DW").style.display='block';
    }
    else
    {
    document.getElementById("DW").style.display='none';
    }
    if(selection=="monthwise")
    {
         document.getElementById("MW").style.display='block';
         var cbtype=document.getElementById("cbtype").value;
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         document.frmReport.txtCB_Yearwise.value=year;
          document.frmReport.txtCB_Year.value=year;
     
         if (cbtype=="SJV")
         {                  
                     
                     document.frmReport.txtCB_Monthwise.value=3;
                     document.frmReport.txtCB_Month.value=3;
                     document.getElementById("txtCB_Month").disabled=true;
         }
         else
         {
                     document.frmReport.txtCB_Monthwise.value=month;
                     document.frmReport.txtCB_Month.value=month;
                     document.getElementById("txtCB_Month").disabled=false;
         }
    }
    else
    {
        document.getElementById("MW").style.display='none';
    }
    
   return true;
   
}
function loadmonth()
{
  var cbtype=document.getElementById("cbtype").value;
  //alert(cbtype);
 
  if (cbtype=="SJV")
  {
      //  alert("inside if");
        document.getElementById("txtCB_Month").value=3;
        document.getElementById("txtCB_Month").disabled=true;
  }
  else
  {   
     // alert("inside else");
     // document.getElementById("txtCB_Month").value="";
     var today= new Date(); 
     var day=today.getDate();
     var month=today.getMonth();
     month=month+1;
     var year=today.getYear();
     if(year < 1900) year += 1900;
                
     document.frmReport.txtCB_Year.value=year;
     document.frmReport.txtCB_Month.value=month;
     document.frmReport.txtCB_Yearwise.value=year;
     document.frmReport.txtCB_Monthwise.value=month;
     document.getElementById("txtCB_Month").disabled=false;
  }  
}

