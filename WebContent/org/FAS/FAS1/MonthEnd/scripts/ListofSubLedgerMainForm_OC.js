//alert("sdfsdfsdf");
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
function loadTabAll(AccHeadcode)
{

  var document=window.opener.document;

    var Acc_UnitCode=document.frmSubLedgerSystem.cmbAcc_UnitCode.value;
    var OffCode=document.frmSubLedgerSystem.cmbOffice_code.value;
    var FinanYr=document.frmSubLedgerSystem.cmbFinancialYear.value;
    var CashbookYear=document.frmSubLedgerSystem.txtCB_Year.value;
    var CashbookMonth=document.frmSubLedgerSystem.txtCB_Month.value;
    var cmbSL_Type=document.getElementById("cmbMas_SL_type").value;
    var cmbMas_SL_Code=document.getElementById("cmbMas_SL_Code").value; 
    var url="../../../../../SubLedgerMainFormListServlet_OC.con?command=fetch&accountHeadcode="+AccHeadcode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth+"&SL_type="+cmbSL_Type+"&Type_Code="+cmbMas_SL_Code;
          
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);
}



function handleResponse(req)
    {   
      if(req.readyState==4)
        {
          if(req.status==200)
          {               
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            
            if(Command=="fetch")
            {
                listRow(baseResponse);
            }
            
}
}
}


function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
        var j=0;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var finanYR=baseResponse.getElementsByTagName("finanYR")[0].firstChild.nodeValue;
        var straccountHeadCode=baseResponse.getElementsByTagName("straccountHeadCode")[0].firstChild.nodeValue;
        var CloseBal=baseResponse.getElementsByTagName("CloseBal")[0].firstChild.nodeValue;
        var CloseBalInd=baseResponse.getElementsByTagName("CloseBalInd")[0].firstChild.nodeValue;
           
        var doc=window.opener.document;

      doc.frmSubLedgerSystem.cmbAcHeadCode.value=straccountHeadCode;
      opener.doFunction('SubLedgerReturn','null');
      doc.frmSubLedgerSystem.txtCloseBal.value="";
      doc.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked=true;
      
      
  
      if(CloseBal!="0")
      {
      doc.frmSubLedgerSystem.txtCloseBal.value=CloseBal;
      }
      
      
      if(CloseBalInd=="CR")
      {
      doc.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked=true;
      }
      else
      {
        doc.frmSubLedgerSystem.radCloseBalCrDrInd[1].checked=true;
      }
                
       var d=doc.getElementById("cmdAdd");
        d.style.display="none";
    
        var d1=doc.getElementById("cmdUpdate");
        d1.style.display="block";
    
        var d2=doc.getElementById("cmdDelete");
        d2.style.display="block";
           
       //doc.frmSubLedgerSystem.txtFinanYr.readOnly=true;
       //doc.frmSubLedgerSystem.cmbAcHeadCode.readOnly=true;
       self.close(); 
    }
    else
    {
       
        alert("Records not found");
    }
    
    
}

