
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

function typeChange()
{
                var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
                 var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
                 var txtCB_Year=document.getElementById("txtCB_Year").value;	
                 var txtCB_Month=document.getElementById("txtCB_Month").value;	
                 var advnumber=document.getElementById("advnumber").value;
    url="../../../../../../Twad_common_servlet?command=changeAdvNo&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month+"&advnumber="+advnumber;
         req=getTransport();
         req.open("GET",url,true);        
         req.onreadystatechange=function()
         {        	  
                manipulate(req);
         }   
         req.send(null);  
}

function callAdvice()
{      
		 var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;	
                 var cmbOffice_code=document.getElementById("cmbOffice_code").value;	
                 var txtCB_Year=document.getElementById("txtCB_Year").value;	
                 var txtCB_Month=document.getElementById("txtCB_Month").value;	
                 
         url="../../../../../../Twad_common_servlet?command=advno_a89&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
       //  alert(url);
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
               
                   if(command=="advno_a89")
                  {
                         advno_a89checking(baseResponse);
                     
                  }
                  else if(command=="changeAdvNo")
                  {
                         changeAdvNochecking(baseResponse);
                     
                  }
                  
                 
          }
      }
}  
function advno_a89checking(baseResponse)
    {
             var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
             
             if(flag=="success"){
             
                    var advnumber = document.forms[0].advnumber;
                     advnumber.length=0;
                     var voucherno = baseResponse.getElementsByTagName("voucherno");
                     for(var i=0; i<voucherno.length; i++)
                         {
                         
                           if(advnumber.length==0)
                          {
                                                //document.forms[0].txtJournalVou_No.length=0;
                                        var opt1 = document.createElement('option');
                                opt1.value = 0;
                                opt1.innerHTML ="select Voucher"; 
                                advnumber.appendChild(opt1);
                                  
                          }
                         
                         
                             var opt = document.createElement('option');
                             opt.value = voucherno[i].firstChild.nodeValue;
                             opt.innerHTML = voucherno[i].firstChild.nodeValue; //instead of using textnode ,we use innerhtml
                             advnumber.appendChild(opt);
                         }
                 }
                 else
                 {
                 alert("No Record Exist");
                  var advnumber = document.forms[0].advnumber;
                  advnumber.length=0;
                //  document.forms[0].advnumber.value="0";
                 
                 }
    }
 function  changeAdvNochecking(baseResponse)
 {
 
 var flag = baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
         
             if(flag=="success"){ 
             var tda_type = baseResponse.getElementsByTagName("tda_type")[0].firstChild.nodeValue;
           
             document.acceptanceDebitOrCredit.tdaType.value=tda_type;
             }
 }

function checknull()
{

    if((document.acceptanceDebitOrCredit.cmbOffice_code.value=="") || (document.acceptanceDebitOrCredit.cmbOffice_code.value.length<=0) || (document.acceptanceDebitOrCredit.cmbOffice_code.value=="0"))
    {
        alert("Please Select Accounting Office Code");
        document.acceptanceDebitOrCredit.cmbOffice_code.focus();
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