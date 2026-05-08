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

function callbillno()
{
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
             var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
             var txtCB_Year=document.getElementById("txtCB_Year").value;	
             var txtCB_Month=document.getElementById("txtCB_Month").value;	
            var form=document.getElementById("bill_report"); 
            //alert("SanProYN_len"+form);
             var SanProYN;
//             alert("SanProYN_len"+form.SanProYN.length);
             for(var i=0;i<form.SanProYN.length;i++){
            	 if(form.SanProYN[i].checked){
            		 SanProYN = form.SanProYN[i].value;
            	    }
             }
             var  url="../../../../../Bills_Token_Register_Report_serv?command=loadBillNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&SanProYN="+SanProYN;
           //  alert("url"+url);
             req=getTransport();
	         req.open("GET",url,true);        
	         req.onreadystatechange=function()
	         {        	  
	                manipulate(req);
	         }   
	         req.send(null);  
}
function ChangesancNo()
{
	 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
     var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
     var txtCB_Year=document.getElementById("txtCB_Year").value;	
     var txtCB_Month=document.getElementById("txtCB_Month").value;	
     var advnumber=document.getElementById("advnumber").value;	
     var form=document.getElementById("bill_report"); 
     //alert("SanProYN_len"+form);
     var SanProYN;
    // alert("SanProYN_len"+form.SanProYN.length);
     for(var i=0;i<form.SanProYN.length;i++){
    	 if(form.SanProYN[i].checked){
    		 SanProYN = form.SanProYN[i].value;
    	    }
     }
     var  url="../../../../../Bills_Token_Register_Report_serv?command=loadsancNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&billno="+advnumber+"&SanProYN="+SanProYN;
  //alert("url"+url);
     req=getTransport();
     req.open("GET",url,true);        
     req.onreadystatechange=function()
     {        	  
            manipulate(req);
     }   
     req.send(null);  
}
function  manipulate(req)
    {
    if(req.readyState==4)
      {
          if(req.status==200)
          {
               var baseResponse=req.responseXML.getElementsByTagName("response")[0];  
               var tagCommand=baseResponse.getElementsByTagName("command")[0]; 
               var command=tagCommand.firstChild.nodeValue; 
               
                   if(command=="loadBillNo")
                  {
                	   loadBillNochecking(baseResponse);
                     
                  }
                  else if(command=="loadsancNo")
                  {
                	  loadsancNochecking(baseResponse);
                     
                  }
                  
                 
          }
      }
}  
function loadBillNochecking(baseResponse)
    {
             var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
             
             if(flag=="success"){
             
                    var advnumber = document.forms[0].advnumber;
                     advnumber.length=0;
                     var bill_no = baseResponse.getElementsByTagName("bill_no");
                     var sancid = baseResponse.getElementsByTagName("sancid");
                    // var amtName=baseResponse.getElementsByTagName("amtName");
                     var sanction_ID="";
                     for(var i=0; i<bill_no.length; i++)
                         {
                         
                           if(bill_no.length==0)
                          {
                                var opt1 = document.createElement('option');
                                opt1.value = 0;
                                opt1.innerHTML ="select Voucher"; 
                                advnumber.appendChild(opt1);
                                  
                          }
                           sanction_ID=sancid[i].firstChild.nodeValue;
                        //   alert(sanction_ID);
                           if(sanction_ID=="null")
                        	   sanction_ID="";
                           else
                        	   sanction_ID=" ("+sanction_ID+")";
                             var opt = document.createElement('option');
                             opt.value = bill_no[i].firstChild.nodeValue;
                             opt.innerHTML = bill_no[i].firstChild.nodeValue+sanction_ID;
                             advnumber.appendChild(opt);
                         }
                     var amtname=baseResponse.getElementsByTagName("amtName")[0].firstChild.nodeValue;
                  //   alert('testing'+amtname);
                     if(amtname=="null")amtname="";
                  
                     document.forms[0].sanctionid.value= baseResponse.getElementsByTagName("sancNo")[0].firstChild.nodeValue;
                     document.forms[0].amtName1.value=amtname;
                 }
                 else
                 {
                 alert("No Record Exist");
                  var advnumber = document.forms[0].advnumber;
                  advnumber.length=0;
                  document.forms[0].sanctionid.value="";
                  document.forms[0].amtName1.value="";
                 }
    }
 function  loadsancNochecking(baseResponse)
 {
 
 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         
             if(flag=="success"){ 
             var sancNo = baseResponse.getElementsByTagName("sancNo")[0].firstChild.nodeValue;
             var amtName = baseResponse.getElementsByTagName("amtName")[0].firstChild.nodeValue;
             document.bill_report.sanctionid.value=sancNo;  
             document.bill_report.amtName1.value=amtName;
             }
 }

function checknull()
{

    if((document.bill_report.cmbOffice_code.value=="") || (document.bill_report.cmbOffice_code.value.length<=0) || (document.bill_report.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.bill_report.cmbOffice_code.focus();
        return false;
    
    }
    if((document.getElementById("txtCB_Year").value.length!=4)|| (document.getElementById("txtCB_Year").value.length<=0) ||(document.getElementById("txtCB_Year").value==""))
    {
        alert("Enter the correct year");
        document.getElementById("txtCB_Year").focus();
        return false;
    }
     if(document.getElementById("txtCB_Month").value=="")
    {
        alert("Select a month");
        return false;
    }
    
 return true;
}
function numbersonly(e)
{   
        var unicode=e.charCode? e.charCode : e.keyCode;
         if(unicode==13)
        {
          
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false;
        }
}