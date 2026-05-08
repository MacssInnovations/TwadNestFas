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
function loadTabAll(AccHeadcode,sltype,slcode)
{
alert(AccHeadcode+"  "+sltype+"  "+slcode);
  var doc=window.opener.document;
//alert(doc);
    var Acc_UnitCode=doc.frmSubLedgerSystem.cmbAcc_UnitCode.value;
    var OffCode=doc.frmSubLedgerSystem.cmbOffice_code.value;
    var FinanYr=doc.frmSubLedgerSystem.cmbFinancialYear.value;
    var CashbookYear=doc.frmSubLedgerSystem.txtCB_Year.value;
    var CashbookMonth=doc.frmSubLedgerSystem.txtCB_Month.value;
   
   // var SL_TYPE=doc.getElementById("cmbMas_SL_type").value;
   //  alert(SL_TYPE);
 //  var SL_CODE=doc.getElementById("cmbMas_SL_Code").value;
 //   alert(SL_CODE)
 //  var SL_TYPE=document.frmSubLedgerSystem.cmbMas_SL_type.value;
  //  var SL_CODE=document.frmSubLedgerSystem.cmbMas_SL_type.value;
   
     var url="../../../../../SubLedgerMainFormListServlet_CB.con?command=fetch&accountHeadcode="+AccHeadcode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&SL_TYPE="+sltype+"&SL_CODE="+slcode+"&txtFinanYr="+FinanYr+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
 // var url="../../../../../SubLedgerMainFormListServlet_CB.con?command=fetch&accountHeadcode="+AccHeadcode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
 
    alert(url);       
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadyStatechange=function()
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
    //alert(flag);
        var j=0;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var finanYR=baseResponse.getElementsByTagName("finanYR")[0].firstChild.nodeValue;
        var straccountHeadCode=baseResponse.getElementsByTagName("straccountHeadCode")[0].firstChild.nodeValue;
        var CloseBal=baseResponse.getElementsByTagName("CloseBal")[0].firstChild.nodeValue;
        var CloseBalInd=baseResponse.getElementsByTagName("CloseBalInd")[0].firstChild.nodeValue;
        var SLDesc=baseResponse.getElementsByTagName("SL_TYPE")[0].firstChild.nodeValue;
        var SLCodeDesc=baseResponse.getElementsByTagName("SL_CODE_DESC")[0].firstChild.nodeValue;
        var AcHeadName=baseResponse.getElementsByTagName("AcHeadName")[0].firstChild.nodeValue;
       // var SLCodeDesc=baseResponse.getElementsByTagName("SL_CODE")[0].firstChild.nodeValue;
   //  alert(SLDesc)
   alert(SLCodeDesc)
           var SL_TYPE_CODE=baseResponse.getElementsByTagName("SL_TYPE_CODE")[0].firstChild.nodeValue;
           var SL_CODE=baseResponse.getElementsByTagName("SL_CODE")[0].firstChild.nodeValue;
        var doc=window.opener.document;

      doc.frmSubLedgerSystem.cmbAcHeadCode.value=straccountHeadCode;
     // doFunction('SubLedgerReturn','null');
      doc.frmSubLedgerSystem.txtCloseBal.value="";
      doc.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked=true;
     doc.frmSubLedgerSystem.txtaccountheadname.value=AcHeadName;
     alert(AcHeadName);
      if(CloseBal!="0")
      {
      doc.frmSubLedgerSystem.txtCloseBal.value=CloseBal;
      }
      var obj=doc.frmSubLedgerSystem.cmbMas_SL_type;
     // alert(obj.name);
      obj.options[0]=new Option(SLDesc,SL_TYPE_CODE);
      var obj=doc.frmSubLedgerSystem.cmbMas_SL_Code;
      //alert(obj.name);
      obj.options[0]=new Option(SLCodeDesc,SL_CODE);
    
    
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
           
      
       self.close(); 
    }
    else
    {
       
        alert("Records not found");
    }
    
    
}


