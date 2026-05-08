/**
 *  Variables Declaration
 */
var unitID=0;
var empName="";


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
///////////////////////////

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
document.getElementById("txtverifyBy").value=emp;
//document.getElementById("txtverifyByName").value=EMPname;
doFunction('loademp','null');
//alert("dofun");
}

////////////////////////////////////////////  Account Number Popup //////////////////////////////
var winAccNo;
function AccNopopup()
{
    if (winAccNo && winAccNo.open && !winAccNo.closed) 
    {
       winAccNo.resizeTo(500,500);
       winAccNo.moveTo(250,250); 
       winAccNo.focus();
    }
    else
    {
        winAccNo=null
    }
    //var cmbOffice_code=document.getElementById("comOffCode").value;  
    //winAccNo= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Bank_AccountNo.jsp?cmbOffice_code="+cmbOffice_code,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
    //alert("ac "+cmbAcc_UnitCode);
    winAccNo= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Bank_AccountNo.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    
    winAccNo.moveTo(250,250);  
    winAccNo.focus();
}

function checkCode()
{
             var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
             var cmbOffice_code=document.getElementById("comOffCode").value;
             var txtChequeCode=document.getElementById("txtChequeCode").value;
             var url="../../../../../ChequeBookServ.view?Command=checkCode&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&comOffCode="+cmbOffice_code+"&txtChequeCode="+txtChequeCode;
          // alert(url);
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            };   
            req.send(null);
}

function doParentAcc_NO(accno,bankid,br_id,B_name,micr,addr,bid,branchid)
{
    document.chequeForm.txtBankAc.value=accno;
    document.chequeForm.BankName_ID.value=bankid;
    document.chequeForm.Bank_ID.value=bid;
    document.chequeForm.Br_ID.value=branchid;
    document.chequeForm.Branch_ID.value=br_id;
    document.chequeForm.txtMICRCode.value=micr;
    document.chequeForm.txtBankAddr.value=addr;
    document.chequeForm.txtBankName.value=B_name;
    
    document.chequeForm.txtChequeCode.readOnly=false;
    document.chequeForm.txtChequeCode.value="";
    document.chequeForm.txtNoLeaves.value="";
    
    document.chequeForm.txtPhyVerDate.value="";
    document.chequeForm.txtStartLNO.value="";
    document.chequeForm.txtEndLNO.value="";
    document.chequeForm.txtDateDest.value="";
    
   return true;
}

function ParentCheque(scod)
{
        clearall();
        //alert("parent");
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("comOffCode").value;
        var url="../../../../../ChequeBookListServ.view?command=fetch&chequeBookcode="+scod+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;       
    // alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
        {
            handleResponse(req);
        }   
         req.send(null);
}

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (winAccNo && winAccNo.open && !winAccNo.closed) winAccNo.close();
if (ChequeBookListWindow && ChequeBookListWindow.open && !ChequeBookListWindow.closed) ChequeBookListWindow.close();
}

//////////////////////////////

function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
var ChequeBookListWindow;
function ListAll()
{
    
      if (ChequeBookListWindow && ChequeBookListWindow.open && !ChequeBookListWindow.closed) 
    {
       ChequeBookListWindow.resizeTo(500,500);
       ChequeBookListWindow.moveTo(250,250); 
       ChequeBookListWindow.focus();
    }
    else
    {
        ChequeBookListWindow=null;
    }
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value  ;  
        var cmbOffice_code=document.getElementById("comOffCode").value;
        var txtBankAc=document.getElementById("txtBankAc").value;
        if(txtBankAc=="")
        {
        	txtBankAc=0;
        }
      //  alert("txtBankAc:::"+txtBankAc);
        ChequeBookListWindow= window.open("ChequeBookList.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code+"&txtBankAc="+txtBankAc,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
        ChequeBookListWindow.moveTo(250,250);    
        ChequeBookListWindow.focus();
}


function nullcheck()
{
            
     if((document.chequeForm.comOffCode.value==null)||(document.chequeForm.comOffCode.value.length==0))
        {
            alert("Null Value not accepted...Select Office Id");
            document.chequeForm.comOffCode.focus();
            return false;
        }
     if((document.chequeForm.txtBankAc.value==null)||(document.chequeForm.txtBankAc.value.length==0))
        {
            alert("Null Value not accepted...Select Account number");
            document.chequeForm.txtBankAc.focus();
            return false;
        }
     if(document.chequeForm.BankName_ID.value.length==0)
        {
            alert("Bank name not populated");
            document.chequeForm.BankName_ID.focus();
            return false;
        }
     if(document.chequeForm.Branch_ID.value.length==0)
        {
            alert("Branch name not populated");
            document.chequeForm.Branch_ID.focus();
            return false;
        }
     if(document.chequeForm.txtMICRCode.value.length==0)
     {
        alert("MICR code not populated");
        document.chequeForm.txtMICRCode.focus();
        return false;
     }
        if(document.chequeForm.txtChequeCode.value.length==0)
        {
            alert("Cheque code shouldn't empty");
            document.chequeForm.txtChequeCode.focus();
            return false;
        }
         if(document.chequeForm.txtNoLeaves.value.length==0 || document.chequeForm.txtNoLeaves.value==0)
            {
                alert("Number of leaves code shouldn't empty");
                document.chequeForm.txtNoLeaves.focus();
                return false;
            }
        if(document.chequeForm.txtStartLNO.value.length==0)
            {
                alert("Start leaf number  shouldn't empty");
                document.chequeForm.txtStartLNO.focus();
                return false;
            }
        if(document.chequeForm.txtEndLNO.value.length==0)
            {
                alert("End leaf number shouldn't empty");
                document.chequeForm.txtEndLNO.focus();
                return false;
            }
        if(document.chequeForm.radCheck_NoOfLeaf[0].checked==true)
        {
            if(document.chequeForm.txtverifyBy.value.length==0)
            {
                alert ("Select Employee Name, who physically verified it ");
                return false;
            }     
           
            if(document.chequeForm.txtPhyVerDate.value.length==0)
            {
                alert("Physical Verification Date shouldn't empty");
                document.chequeForm.txtPhyVerDate.focus();
                return false;
            }
         }
            
        
   /*  
    if(document.chequeForm.txtDateDest.value.length==0)
        {
            alert("Date Of Destruction  shouldn't empty");
            document.chequeForm.txtDateDest.focus();
            return false;
        }*/
    if(document.chequeForm.txtNoLeaves.value.length!=0)
    {
        var NoLeaves=document.chequeForm.txtNoLeaves.value;
        var StartLNO=document.chequeForm.txtStartLNO.value;
        var EndLNO=document.chequeForm.txtEndLNO.value;
        var no=EndLNO-StartLNO;
        if(no!=NoLeaves-1)
        {
            alert("Check the limit of the leaves");
            document.chequeForm.txtStartLNO.focus();
            document.chequeForm.txtEndLNO.value="";
            document.chequeForm.txtStartLNO.value="";
            return false;
        }
    }
return true;
    
}

function TestLeafNos()
{
var unitid=document.chequeForm.cmbAcc_UnitCode.value;
fillendleaf();
var StartLNO=document.chequeForm.txtStartLNO.value;
var txtEndLNO=document.chequeForm.txtEndLNO.value;
var txtBankAc=document.chequeForm.txtBankAc.value;

//alert("StartLNO::"+StartLNO);
//alert("txtEndLNO::"+txtEndLNO);
 var url="../../../../../ChequeBookServ.view?Command=TestLeaf&cmbAcc_UnitCode="+unitid+"&txtStartLNO="+StartLNO+"&txtEndLNO="+txtEndLNO+"&txtBankAc="+txtBankAc;
               //    alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };   
                            req.send(null);
}

function fillendleaf()
{
     var NoLeaves=document.chequeForm.txtNoLeaves.value;
     var StartLNO=document.chequeForm.txtStartLNO.value;
        var value1;
        var final_one;//1849
      if(StartLNO.charAt(0)==0 && StartLNO.charAt(1)!=0)
      {
      value1=StartLNO.substring(1,6);
      final_one=parseInt(value1)+(parseInt(NoLeaves)-1)
        final_two="0"+final_one;
    //  alert(final_two);
      }
      else if(StartLNO.charAt(0)==0 && StartLNO.charAt(1)==0)
      {
    value1=StartLNO.substring(2,6);
     final_one=parseInt(value1)+(parseInt(NoLeaves)-1)
      final_two="00"+final_one;
      }
      else
      {
      final_one=parseInt(StartLNO)+(parseInt(NoLeaves)-1)
      final_two=final_one;
      }
    document.chequeForm.txtEndLNO.value=final_two;
   //  document.chequeForm.txtEndLNO.value=parseInt(StartLNO)+(parseInt(NoLeaves)-1);
}


function clearall()
{
        document.chequeForm.txtChequeCode.readOnly=false;
        document.chequeForm.txtBankName.value="";
        document.chequeForm.txtBankAc.value="";
        document.chequeForm.txtBankAddr.value="";
        document.chequeForm.txtMICRCode.value="";
        document.chequeForm.BankName_ID.value="";
        document.chequeForm.Branch_ID.value="";
        document.chequeForm.txtChequeCode.value="";
        document.chequeForm.txtNoLeaves.value="";
        document.chequeForm.txtPhyVerDate.value="";
        document.chequeForm.txtStartLNO.value="";
        document.chequeForm.txtEndLNO.value="";
        document.chequeForm.txtDateDest.value="";
        document.getElementById("txtverifyByName").value="";
        document.chequeForm.txtverifyBy.value="";
        document.chequeForm.radCheck_NoOfLeaf[0].checked=true;
        
          var d=document.getElementById("cmdAdd");
                d.style.display="block";
            
                var d1=document.getElementById("cmdUpdate");
                d1.style.display="none";
                
                var d3=document.getElementById("cmdDelete");
                d3.style.display="none";

}

/////to check for the limit in leaf number////
function checkLeaf()
{
        var NoLeaves=document.chequeForm.txtNoLeaves.value;
        var StartLNO=document.chequeForm.txtStartLNO.value;
        var EndLNO=document.chequeForm.txtEndLNO.value;
        var no=EndLNO-StartLNO;
        if(no!=NoLeaves-1)
        {
        alert("Check the limit of the leaves");
        document.chequeForm.txtStartLNO.focus();
        document.chequeForm.txtEndLNO.value="";
        document.chequeForm.txtStartLNO.value="";
        return false;
        }

}

function showicons(YrN)
{
    if(YrN=="Y")
    {
        
        document.getElementById("showemplist").style.display='block';
        document.getElementById("showdate").style.display='block';
        document.getElementById("txtverifyBy").readOnly=false;
        document.getElementById("txtPhyVerDate").readOnly=false;
        
        document.getElementById("txtverifyBy").value = unitID;
        document.getElementById("txtverifyByName").value = empName; 
        
    }
    else if(YrN=="N")
    {
        unitID= document.getElementById("txtverifyBy").value;
        empName = document.getElementById("txtverifyByName").value;
        
        document.getElementById("showemplist").style.display='none';
        document.getElementById("showdate").style.display='none';
        document.getElementById("txtverifyBy").value="";
        document.getElementById("txtverifyByName").value="";
        document.getElementById("txtPhyVerDate").value="";
        document.getElementById("txtverifyBy").readOnly=true;
        document.getElementById("txtPhyVerDate").readOnly=true;
        
    }
}




/////////////////////////
function doFunction(command,param)
{
    var Acc_UnitCode=document.chequeForm.cmbAcc_UnitCode.value;
    
    var OffCode=document.chequeForm.comOffCode.value;
    //dhana
   // var BankName_ID=document.chequeForm.BankName_ID.value;
     var BankName_ID=document.chequeForm.Bank_ID.value;
   // alert("bankid"+BankName_ID);
    
    var Branch_ID=document.chequeForm.Br_ID.value;
  //  alert("branchid"+Branch_ID);
    
    var BankAc=document.chequeForm.txtBankAc.value;
    
    var MICRCode=document.chequeForm.txtMICRCode.value;
    
    var ChequeCode=document.chequeForm.txtChequeCode.value;
    
    var NoLeaves=document.chequeForm.txtNoLeaves.value;
    var empname=document.chequeForm.txtverifyByName.value;
    var radvalue;
    
    if(document.chequeForm.radCheck_NoOfLeaf[0].checked==true)
    {
            radvalue=document.chequeForm.radCheck_NoOfLeaf[0].value;
    }
    else
    {
            radvalue=document.chequeForm.radCheck_NoOfLeaf[1].value;
    }
    
    var PhyVerDate=document.chequeForm.txtPhyVerDate.value;
    
    var StartLNO=document.chequeForm.txtStartLNO.value;
    
    var EndLNO=document.chequeForm.txtEndLNO.value;
    
    var DateDest=document.chequeForm.txtDateDest.value;
    
    //var userId=document.chequeForm.txtuserId.value;
    
    var verifyBy=document.chequeForm.txtverifyBy.value;
   //alert("command"+ command);         
        if(command=="Add")
        {
        //alert("aaa");
        var flag=nullcheck();
                if(flag==true)
                   {
                    var url="../../../../../ChequeBookServ.view?Command=Add&txtverifyBy="+verifyBy+"&txtMICRCode="+MICRCode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&BankName_ID="+BankName_ID+"&Branch_ID="+Branch_ID+"&txtBankAc="+BankAc+"&txtChequeCode="+ChequeCode+"&txtNoLeaves="+NoLeaves+"&radCheck_NoOfLeaf="+radvalue+"&txtPhyVerDate="+PhyVerDate+"&txtStartLNO="+StartLNO+"&txtEndLNO="+EndLNO+"&txtDateDest="+DateDest;
                  // alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };   
                            req.send(null);
                     }
        }
        else if(command=="Update")
        {
                var flag=nullcheck();
                if(flag==true)
                  {
                    var url="../../../../../ChequeBookServ.view?Command=Update&txtverifyBy="+verifyBy+"&txtMICRCode="+MICRCode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&BankName_ID="+BankName_ID+"&Branch_ID="+Branch_ID+"&txtBankAc="+BankAc+"&txtChequeCode="+ChequeCode+"&txtNoLeaves="+NoLeaves+"&radCheck_NoOfLeaf="+radvalue+"&txtPhyVerDate="+PhyVerDate+"&txtStartLNO="+StartLNO+"&txtEndLNO="+EndLNO+"&txtDateDest="+DateDest;
                    alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };   
                            req.send(null);
                 }
        }
        
        
        else if(command=="Delete")
        {
             if(document.chequeForm.txtChequeCode.value.length==0)
                {
                    alert("Cheque code shouldn't empty");
                    document.chequeForm.txtChequeCode.focus();
                    return false;
                }
              else
               {
                    var url="../../../../../ChequeBookServ.view?Command=Delete&txtChequeCode="+ChequeCode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode;
                   // alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    };   
                            req.send(null);
                 }
        }
        else if(command=="loademp")
        {
        document.chequeForm.txtverifyByName.value="";
            var eid=document.getElementById("txtverifyBy").value;
            var offid=document.getElementById("comOffCode").value;
            if(document.getElementById("txtverifyBy").value.length>0)
            {
                var url="../../../../../Receipt_SL.view?Command=loadEmployee&employeeID="+eid+"&Office_code="+offid+"&Acc_UnitCode="+Acc_UnitCode;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                  // alert("handle");
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
          {  // alert("handleresponse"); 
          //    var ename=baseResponse.getElementsByTagName("EMPname")[0].firstChild.nodeValue;
           //   alert("ename"+ename);          
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
               var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var Command=tagCommand.firstChild.nodeValue; 
            if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            
            else if(Command=="Update")
            {
                UpdateRow(baseResponse);
            }
            else if(Command=="loadEmployee")
            {
            
                loadEmployee(baseResponse);
            }
            else if(Command=="fetch")
            {
                listRow(baseResponse);
            }
            else if(Command=="TestLeaf")
            {
                TestLeafrow(baseResponse);
            }
            else if(Command=="checkCode")
            {
                checkCode_one(baseResponse);
            }
            
        }
    }
}

function checkCode_one(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
  
    if(flag=="success")
    {
    var flag_test=baseResponse.getElementsByTagName("flag_test")[0].firstChild.nodeValue; 
            if(flag_test=="recordIsThere")
            {
            alert("Cheque Book Code Already Entered");
             document.getElementById("txtChequeCode").value="";
            return false;
            }
            else
            {
            
            }
    }
    else
    {
      alert("Error in Checking Existing Cheque Code");
      return false;
    }
}

function TestLeafrow(baseResponse)
{
var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 //  alert("sss:::::::::"+flag);
    if(flag=="success")
    {
            var flag_test=baseResponse.getElementsByTagName("flag_test")[0].firstChild.nodeValue; 
            if(flag_test=="recordIsThere")
            {
            alert("This Leaf No Already Existed");
            document.getElementById("txtStartLNO").value="";
            document.getElementById("txtEndLNO").value="";
            }
            else
            {
            
            }
    }
    else
    {
    alert("Error in Checking Leaf NO:");
    }
}

function loadEmployee(baseResponse)
{ 
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    if(flag=="success")
    { 
        var eid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue; 
        var ename=baseResponse.getElementsByTagName("EMPname")[0].firstChild.nodeValue;
        
    //    var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
       // alert("eid"+eid);
   //     alert("ename"+ename);
     //   document.chequeForm.txtverifyByName.value="";
     //   document.chequeForm.txtverifyByName.value=ename;
       document.getElementById("txtverifyBy").value=eid;
       document.getElementById("txtverifyByName").value=ename;//+" - "+desig;
    }
    else 
    {
        var eid=document.getElementById("txtverifyBy").value;
        alert("Employee Id '"+eid+"' doesn't Exists under this office");
        document.getElementById("txtverifyBy").value="";
        document.getElementById("txtverifyByName").value="";
    }
}

function listRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
    
    if(flag=="success")
    {
                     
        var j=0;
        var bankId=baseResponse.getElementsByTagName("bankId")[0].firstChild.nodeValue;
        //alert("bankId"+bankId);
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bID=baseResponse.getElementsByTagName("bID")[0].firstChild.nodeValue;
     //   alert("bID"+bID);
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
      //  alert(bankAccNO);
        var micr_code=baseResponse.getElementsByTagName("micr_code")[0].firstChild.nodeValue;
     
        var bankName=baseResponse.getElementsByTagName("bankName")[0].firstChild.nodeValue;
       
        var final_addr=baseResponse.getElementsByTagName("final_addr")[0].firstChild.nodeValue;
       // alert(final_addr);
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
       // alert(NoOfLeaf);
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var verifiedID=baseResponse.getElementsByTagName("verifiedID")[0].firstChild.nodeValue;
        var verifiedbyname=baseResponse.getElementsByTagName("verifiedbyname")[0].firstChild.nodeValue;
       // alert(verifiedbyname);
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
       // alert(chequeBookcode);
        if(verifiedID==0)
             verifiedID="";
        if(verifiedbyname=="")
             verifiedbyname="";
        if(NoOfLeaf==0)
             NoOfLeaf="";
        if(StartLeaf==0)
             StartLeaf="";
        if(EndLeaf==0)
             EndLeaf="";
        if(verifyON=="null")
             verifyON="";
        if(DestDate=="null")
             DestDate="";
            
          document.chequeForm.Bank_ID.value=bankId;
        document.chequeForm.Br_ID.value=bID;   
             
        document.chequeForm.txtBankName.value=bankName;
        document.chequeForm.txtBankAc.value=bankAccNO;
        document.chequeForm.txtBankAddr.value=final_addr;
        document.chequeForm.txtMICRCode.value=micr_code;
        document.chequeForm.BankName_ID.value=bankId;
        document.chequeForm.Branch_ID.value=branchId;
        document.chequeForm.txtChequeCode.value=chequeBookcode;
        document.chequeForm.txtNoLeaves.value=NoOfLeaf;
        document.chequeForm.txtPhyVerDate.value=verifyON;
        document.chequeForm.txtStartLNO.value=StartLeaf;
        document.chequeForm.txtEndLNO.value=EndLeaf;
        document.chequeForm.txtDateDest.value=DestDate;
        
        document.getElementById("txtverifyBy").value=verifiedID;
        document.getElementById("txtverifyByName").value=verifiedbyname;
        
        if(PhyVerify=="Y")
        {
            document.chequeForm.radCheck_NoOfLeaf[0].checked=true;
            document.getElementById("showemplist").style.display='block';
            document.getElementById("showdate").style.display='block';
            document.getElementById("txtverifyBy").readOnly=false;
            document.getElementById("txtPhyVerDate").readOnly=false;
            
        }
        else
        {
            document.chequeForm.radCheck_NoOfLeaf[1].checked=true;
            document.getElementById("showemplist").style.display='none';
            document.getElementById("showdate").style.display='none';
            document.getElementById("txtverifyBy").value="";
            document.getElementById("txtverifyByName").value="";
            document.getElementById("txtPhyVerDate").value="";
            document.getElementById("txtverifyBy").readOnly=true;
            document.getElementById("txtPhyVerDate").readOnly=true;
        }
        
      
        
         document.chequeForm.txtChequeCode.readOnly=true;
            var d=document.getElementById("cmdUpdate");
            d.style.display="block";
            
            var d2=document.getElementById("cmdDelete");
            d2.style.display="block";
            
            var d1=document.getElementById("cmdAdd");
            d1.style.display="none";  
            
            if (ChequeBookListWindow && ChequeBookListWindow.open && !ChequeBookListWindow.closed) ChequeBookListWindow.close();
    }
    else
    {
       
        alert("Records not found");
    }
    
    
}

function addRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
   
     
    if(flag=="success")
    {   
         alert("Records inserted into database");
         clearall();
    }
    else
    {
        alert("Records r not inserted");
    }
}




function deleteRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {
        alert("Records deleted from database");
        clearall();        
    }
    else
    {
        alert("Records r not deleted");
    }
}





function UpdateRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {
        alert("Record Updated");
       clearall();
    }
    else
    {
        alert("Record not Updated");
    }
}





function bankDetailsRow(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
   var break1=document.createElement('br');
     
    if(flag=="success")
    {   
    var branchName=baseResponse.getElementsByTagName("branchName")[0].firstChild.nodeValue;
    
    var addr1=baseResponse.getElementsByTagName("addr1")[0].firstChild.nodeValue;
    
    var addr2=baseResponse.getElementsByTagName("addr2")[0].firstChild.nodeValue;
    
    var city=baseResponse.getElementsByTagName("city")[0].firstChild.nodeValue;
    
    var bankID=baseResponse.getElementsByTagName("bankID")[0].firstChild.nodeValue;
    
    var branchID=baseResponse.getElementsByTagName("branchID")[0].firstChild.nodeValue;
    
    
    
        document.chequeForm.txtBankAddr.value=branchName+","+addr1+","+addr2+","+city;
        
        document.chequeForm.BankName_ID.value=bankID;
        
        document.chequeForm.Branch_ID.value=branchID;
        
        
       
    }
    else
    {
        alert("Records r not inserted");
    }
}




//This is to allow only numbers in control
function numbersonly1(e,t)
{
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
        
}
     
     
function CheckNoofLeaves()
{
   var NoLeaves= parseInt(document.getElementById("txtNoLeaves").value);
   if ( NoLeaves >1000)
    {
      document.getElementById("txtNoLeaves").value="";
      alert("Number of Leaves Should not exceed 1000");
      return false;
    }
    return true;
}

     
     
     
     
//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
}
function isValidDate(dateStr) {
	  
	  // Checks for the following valid date formats:
	  // MM/DD/YYYY
	  // Also separates date into month, day, and year variables
	  var datePat = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
	  
	  var matchArray = dateStr.match(datePat); // is the format ok?
	  if (matchArray == null) {
	   alert("Date must be in MM/DD/YYYY format")
	   return false;
	  }
	  
	  month = matchArray[3]; // parse date into variables
	  day = matchArray[1];
	  year = matchArray[4];
	  if (month < 1 || month > 12) { // check month range
	   alert("Month must be between 1 and 12");
	   return false;
	  }
	  if (day < 1 || day > 31) {
	   alert("Day must be between 1 and 31");
	   return false;
	  }
	  if ((month==4 || month==6 || month==9 || month==11) && day==31) {
	   alert("Month "+month+" doesn't have 31 days!")
	   return false;
	  }
	  if (month == 2) { // check for february 29th
	   var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
	   if (day>29 || (day==29 && !isleap)) {
	    alert("February " + year + " doesn't have " + day + " days!");
	    return false;
	     }
	  }
	  return true;  // date is valid
	 }


function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
        var c=t.value;
      ///New code implemented on 28-03-2019  for year 2019 wrongly displayed 201 
        try{
            var f=isValidDate(c);
           }
       catch(e){
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
                
        }
        if( f==true)
        {
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
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
            return false
    }
    
}
     
