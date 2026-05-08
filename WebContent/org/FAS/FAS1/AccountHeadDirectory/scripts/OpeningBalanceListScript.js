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

    var Acc_UnitCode=document.OpeningBalForm.cmbAcc_UnitCode.value;
    var OffCode=document.OpeningBalForm.comOffCode.value;
    var FinanYr=document.OpeningBalForm.txtFinanYr.value;
    var CashbookYear=document.OpeningBalForm.txtCB_Year.value;
    var CashbookMonth=document.OpeningBalForm.txtCB_Month.value;
    var url="../../../../../OpeningBalListServ.view?command=fetch&accountHeadcode="+AccHeadcode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&txtFinanYr="+FinanYr+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
         // alert(url);
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
              //alert(baseResponse);
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            //alert(Command);
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
        var UptoCR=baseResponse.getElementsByTagName("UptoCR")[0].firstChild.nodeValue;
        var UptoDB=baseResponse.getElementsByTagName("UptoDB")[0].firstChild.nodeValue;
        var currCR=baseResponse.getElementsByTagName("currCR")[0].firstChild.nodeValue;
        var currDB=baseResponse.getElementsByTagName("currDB")[0].firstChild.nodeValue;
       var straccountHeadCode=baseResponse.getElementsByTagName("straccountHeadCode")[0].firstChild.nodeValue;
        var OpenBal=baseResponse.getElementsByTagName("OpenBal")[0].firstChild.nodeValue;
        var OpenBalInd=baseResponse.getElementsByTagName("OpenBalInd")[0].firstChild.nodeValue;
        var CurrDebit=baseResponse.getElementsByTagName("CurrDebit")[0].firstChild.nodeValue;
        var CurrCredit=baseResponse.getElementsByTagName("CurrCredit")[0].firstChild.nodeValue;
        var CloseBal=baseResponse.getElementsByTagName("CloseBal")[0].firstChild.nodeValue;
        var CloseBalInd=baseResponse.getElementsByTagName("CloseBalInd")[0].firstChild.nodeValue;
        var DrUpdateDate=baseResponse.getElementsByTagName("DrUpdateDate")[0].firstChild.nodeValue;
        var CrUpdateDate=baseResponse.getElementsByTagName("CrUpdateDate")[0].firstChild.nodeValue;
        var majorDesc=baseResponse.getElementsByTagName("majorDesc")[0].firstChild.nodeValue;
       var minordesc=baseResponse.getElementsByTagName("minordesc")[0].firstChild.nodeValue;
       var subdesc1=baseResponse.getElementsByTagName("subdesc1")[0].firstChild.nodeValue;
       var subdesc2=baseResponse.getElementsByTagName("subdesc2")[0].firstChild.nodeValue;
       
       
      var doc=window.opener.document;

      doc.OpeningBalForm.cmbAcHeadCode.value=straccountHeadCode;
      doc.OpeningBalForm.txtCredit.value="";
      doc.OpeningBalForm.txtDebit.value="";
      doc.OpeningBalForm.txtYrCredit.value="";
      doc.OpeningBalForm.txtYrDebit.value="";
      doc.OpeningBalForm.txtOpenBal.value="";
      doc.OpeningBalForm.txtCurrMonDebit.value="";
      doc.OpeningBalForm.txtCurrMonCredit.value="";
      doc.OpeningBalForm.txtCloseBal.value="";
      doc.OpeningBalForm.txtDrLUpdate.value="";
      doc.OpeningBalForm.txtCrLUpdate.value="";
      doc.OpeningBalForm.radOpenBalCrDrInd[0].checked=true;
      doc.OpeningBalForm.radCloseBalCrDrInd[0].checked=true;
      
      if(UptoCR!="0")
      {
      doc.OpeningBalForm.txtCredit.value=UptoCR;
      }
      if(UptoDB!="0")
      {
      doc.OpeningBalForm.txtDebit.value=UptoDB;
      }
      if(currCR!="0")
      {
      doc.OpeningBalForm.txtYrCredit.value=currCR;
      }
      if(currDB!="0")
      {
      doc.OpeningBalForm.txtYrDebit.value=currDB;
      }
      if(OpenBal!="0")
      {
      doc.OpeningBalForm.txtOpenBal.value=OpenBal;
      }
      if(CurrDebit!="0")
      {
      doc.OpeningBalForm.txtCurrMonDebit.value=CurrDebit;
      }
      if(CurrCredit!="0")
      {
      doc.OpeningBalForm.txtCurrMonCredit.value=CurrCredit;
      }
      if(CloseBal!="0")
      {
      doc.OpeningBalForm.txtCloseBal.value=CloseBal;
      }
      if(DrUpdateDate!="Not Specified")
      {
      doc.OpeningBalForm.txtDrLUpdate.value=DrUpdateDate;
      }
      if(CrUpdateDate!="Not Specified")
      {
      doc.OpeningBalForm.txtCrLUpdate.value=CrUpdateDate;
      }
      //alert(OpenBalInd);
      //alert(CloseBalInd);
      if(OpenBalInd=="CR")
      {
      doc.OpeningBalForm.radOpenBalCrDrInd[0].checked=true;
      }
      else
      {
        doc.OpeningBalForm.radOpenBalCrDrInd[1].checked=true;
      }
      if(CloseBalInd=="CR")
      {
      doc.OpeningBalForm.radCloseBalCrDrInd[0].checked=true;
      }
      else
      {
        doc.OpeningBalForm.radCloseBalCrDrInd[1].checked=true;
      }
      doc.OpeningBalForm.cmbMajorGroup.value=majorDesc;
       doc.OpeningBalForm.cmbMinorGroup.value=minordesc;
       if(subdesc1=="null")
       subdesc1="";
       if(subdesc2=="null")
       subdesc2="";
       
       doc.OpeningBalForm.cmbSubGroup1.value=subdesc1;
       doc.OpeningBalForm.cmbSubGroup2.value=subdesc2;
       
                   
       var d=doc.getElementById("cmdAdd");
        d.style.display="none";
    
        var d1=doc.getElementById("cmdUpdate");
        d1.style.display="block";
    
        var d2=doc.getElementById("cmdDelete");
        d2.style.display="block";
           
       //doc.OpeningBalForm.txtFinanYr.readOnly=true;
       doc.OpeningBalForm.cmbAcHeadCode.readOnly=true;
       self.close();
    }
    else
    {
       
        alert("Records not found");
    }
    
    
}

