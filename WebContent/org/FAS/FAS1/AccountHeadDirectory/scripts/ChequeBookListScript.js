function Minimize() 
{
window.resizeTo(0,0);
window.screenX = screen.width;
window.screenY = screen.height;
opener.window.focus();
}
function doFunction(command,param)
{
       
        var doc=window.opener.document;

        var cmbAcc_UnitCode=doc.chequeForm.cmbAcc_UnitCode.value;
        var cmbOffice_code=doc.chequeForm.comOffCode.value;
        alert(cmbAcc_UnitCode);
        alert(cmbOffice_code);
      //  var txtFinYear=document.AssetList.txtFinYearvalue.value;
      /*  if(txtFinYear.length!=9 || txtFinYear.length==0)
        {
            alert("Enter the Valid Financial year");
            document.AssetList.comClasAss.value="";
            document.AssetList.comOwnerShip.value="";
            document.AssetList.txtFinYearvalue.focus();
            return false;
        }*/
        if(command=="loadTabAll")
        {

        // var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value  ;  
       //  var cmbOffice_code=document.getElementById("comOffId").value;
         
         var url="../../../../../ChequeBookListServ.view?command=fetch&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;;
           alert(url);
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                    {
                        handleResponse(req);
                    }   
                     req.send(null);
}

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
            if(Command=="loadTabAll")
            {
                loadRow(baseResponse);
            }
            if(Command=="fetch")
            {
                listRow(baseResponse);
            }
}
}
}
function loadRow(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
   
      
   var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
    
    
        var j=0;
        var AssetCode=baseResponse.getElementsByTagName("AssetCode");
        
        var month=baseResponse.getElementsByTagName("month");
        var year=baseResponse.getElementsByTagName("year");
        var Location=baseResponse.getElementsByTagName("Location");
        var Original_cost=baseResponse.getElementsByTagName("Original_cost");
        var curr_val=baseResponse.getElementsByTagName("curr_val");
        var status =baseResponse.getElementsByTagName("status");
       
        var len=AssetCode.length;
     for(j=0;j<len;j++)
     {
        var tbody=document.getElementById("tb");
        
         var AssetCode=baseResponse.getElementsByTagName("AssetCode");
         var Assetdesc=baseResponse.getElementsByTagName("asset_desc");
        var month=baseResponse.getElementsByTagName("month");
        var year=baseResponse.getElementsByTagName("year");
        var Location=baseResponse.getElementsByTagName("Location");
        var Original_cost=baseResponse.getElementsByTagName("Original_cost");
        var curr_val=baseResponse.getElementsByTagName("curr_val");
        var status =baseResponse.getElementsByTagName("status");
      
        var AssetCode1=AssetCode.item(j).firstChild.nodeValue;
        var Assetdesc1=Assetdesc.item(j).firstChild.nodeValue;
        var month1=month.item(j).firstChild.nodeValue;
        var year1=year.item(j).firstChild.nodeValue;
        var Location1=Location.item(j).firstChild.nodeValue;
        var Original_cost1=Original_cost.item(j).firstChild.nodeValue;
        var curr_val1=curr_val.item(j).firstChild.nodeValue;
        var status1=status.item(j).firstChild.nodeValue;
        AssetId=AssetCode1;
        
         var items=new Array();
        
        
        items[0]=AssetCode1;
        items[1]=Assetdesc1;
        items[2]=month1;
        items[3]=year1;
        if(Location1=="null")
        items[4]="";
        else
        items[4]=Location1;
        
        items[5]=Original_cost1;
        items[6]=curr_val1;
        items[7]=status1
       
       
       
       
        var tbody=document.getElementById("tb");
                    
                     var mycurrent_row=document.createElement("TR");
                     mycurrent_row.id=AssetId;
                     var cell=document.createElement("TD");
                     
                     var anc=document.createElement("A");       
                     var url="javascript:loadTabAll('" + AssetId + "')";              
                     anc.href=url;
                     var txtedit=document.createTextNode("Edit");
                     anc.appendChild(txtedit);
                     cell.appendChild(anc);
                     mycurrent_row.appendChild(cell);
                     
                     var cell1=document.createElement("TD");  
                     var AssetCode=document.createTextNode(items[0]);
                     cell1.appendChild(AssetCode);
                     mycurrent_row.appendChild(cell1);
                     
                     var cell1=document.createElement("TD");  
                     var Assetdesc=document.createTextNode(items[1]);
                     cell1.appendChild(Assetdesc);
                     mycurrent_row.appendChild(cell1);
        
                    var cell2=document.createElement("TD");  
                     var Monthyr=document.createTextNode(items[2]+ " / " +items[3]);
                     cell2.appendChild(Monthyr);
                     mycurrent_row.appendChild(cell2);
                     
                  /*
                  //Location will bge added later  ( start on 30th nov2006)
                    
                     var cell3=document.createElement("TD");  
                     var Loc=document.createTextNode(items[3]);
                     cell3.appendChild(Loc);
                     mycurrent_row.appendChild(cell3);
                    
                    */
                     
                     
                     var cell4=document.createElement("TD");  
                     var OriCost=document.createTextNode(items[5]);
                     cell4.appendChild(OriCost);
                     mycurrent_row.appendChild(cell4);
                     
                     var cell5=document.createElement("TD");  
                     var CurrVal=document.createTextNode(items[6]);
                     cell5.appendChild(CurrVal);
                     mycurrent_row.appendChild(cell5);
        
                     var cell6=document.createElement("TD");  
                     var status=document.createTextNode(items[7]);
                     cell6.appendChild(status);
                     mycurrent_row.appendChild(cell6);
        
        
              
        tbody.appendChild(mycurrent_row);
        }
       
    }
    else
    {
     
     
        var tbody=document.getElementById("tb");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
        alert("Records not found");
       
    }
    
}


function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    alert("listRow:::::"+flag);
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
/*
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
    
    
}  */
/*
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
    
    
}  */

/*
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
    
    
}  */
/* function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                    
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var verifyBy=baseResponse.getElementsByTagName("verifyBy")[0].firstChild.nodeValue;
        var userId=baseResponse.getElementsByTagName("userId")[0].firstChild.nodeValue;
        var acOffId=baseResponse.getElementsByTagName("acOffId")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
        var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
                      
     
      var doc=window.opener.document;
      
      //doc.chequeForm.txtuserId.value=empid;
      //doc.chequeForm.txtBankName.value=bankName;
      
      doc.chequeForm.comOffCode.value=acOffId;
      
      doc.chequeForm.txtChequeCode.value=chequeBookcode;                 
      
       //doc.chequeForm.txtBankAc.value=bankAccNO;
       
       //doc.chequeForm.txtAssCode.value=verifyBy;
       
       //doc.chequeForm.txtAliasCode.value=userId;
       
       
       
       doc.chequeForm.txtNoLeaves.value=NoOfLeaf;
      
       if(PhyVerify=='Y')
       doc.chequeForm.radCheck_NoOfLeaf[0].checked=true;
       else
       doc.chequeForm.radCheck_NoOfLeaf[1].checked=true;
       
       doc.chequeForm.txtStartLNO.value=StartLeaf;
       
       doc.chequeForm.txtEndLNO.value=EndLeaf;
       
       //doc.chequeForm.txthiddenAccount.value=bankAccNO;
       
      // doc.chequeForm.txtAccCode.focus();
       
       doc.chequeForm.txtPhyVerDate.value=verifyON;
      
       
       doc.chequeForm.txtDateDest.value=DestDate;
       
              
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
} */