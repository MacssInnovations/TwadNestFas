//alert("hello");

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
doFunction('loademp','null');
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
    winAccNo= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Bank_AccountNo.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode,"BankAccountNumber","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    
    winAccNo.moveTo(250,250);  
    winAccNo.focus();
}



function doParentAcc_NO(accno,bankid,br_id,B_name,micr,addr)
{
   document.chequeForm.txtBankAc.value=accno;
   document.chequeForm.BankName_ID.value=bankid;
   document.chequeForm.Branch_ID.value=br_id;
   document.chequeForm.txtMICRCode.value=micr;
   document.chequeForm.txtBankAddr.value=addr;
   document.chequeForm.txtBankName.value=B_name;
   return true;
}

function ParentCheque(scod)
{
        clearall();
        //alert("parent");
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value;
        var cmbOffice_code=document.getElementById("comOffCode").value;
        var url="../../../../../ChequeBookListServ.view?command=fetch&chequeBookcode="+scod+"&cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code;       
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
        ChequeBookListWindow=null
    }
        var cmbAcc_UnitCode=document.getElementById("cmbAcc_UnitCode").value  ;  
        var cmbOffice_code=document.getElementById("comOffCode").value;
        ChequeBookListWindow= window.open("ChequeBookList.jsp?cmbAcc_UnitCode="+cmbAcc_UnitCode+"&cmbOffice_code="+cmbOffice_code,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
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

function fillendleaf()
{
     var NoLeaves=document.chequeForm.txtNoLeaves.value;
     var StartLNO=document.chequeForm.txtStartLNO.value;
     document.chequeForm.txtEndLNO.value=parseInt(StartLNO)+(parseInt(NoLeaves)-1);
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
    }
    else if(YrN=="N")
    {
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
    
    var BankName_ID=document.chequeForm.BankName_ID.value;
    
    var Branch_ID=document.chequeForm.Branch_ID.value;
    
    var BankAc=document.chequeForm.txtBankAc.value;
    
    var MICRCode=document.chequeForm.txtMICRCode.value;
    
    var ChequeCode=document.chequeForm.txtChequeCode.value;
    
    var NoLeaves=document.chequeForm.txtNoLeaves.value;
    
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
            
        if(command=="Add")
        {
        var flag=nullcheck();
                if(flag==true)
                   {
                    var url="../../../../../ChequeBookServ.view?Command=Add&txtverifyBy="+verifyBy+"&txtMICRCode="+MICRCode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&BankName_ID="+BankName_ID+"&Branch_ID="+Branch_ID+"&txtBankAc="+BankAc+"&txtChequeCode="+ChequeCode+"&txtNoLeaves="+NoLeaves+"&radCheck_NoOfLeaf="+radvalue+"&txtPhyVerDate="+PhyVerDate+"&txtStartLNO="+StartLNO+"&txtEndLNO="+EndLNO+"&txtDateDest="+DateDest;
                   //alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
                     }
        }
        else if(command=="Update")
        {
                var flag=nullcheck();
                if(flag==true)
                  {
                    var url="../../../../../ChequeBookServ.view?Command=Update&txtverifyBy="+verifyBy+"&txtMICRCode="+MICRCode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&BankName_ID="+BankName_ID+"&Branch_ID="+Branch_ID+"&txtBankAc="+BankAc+"&txtChequeCode="+ChequeCode+"&txtNoLeaves="+NoLeaves+"&radCheck_NoOfLeaf="+radvalue+"&txtPhyVerDate="+PhyVerDate+"&txtStartLNO="+StartLNO+"&txtEndLNO="+EndLNO+"&txtDateDest="+DateDest;
                    //alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
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
                    }   
                            req.send(null);
                 }
        }
        else if(command=="loademp")
        {  
            var eid=document.getElementById("txtverifyBy").value;
            var offid=document.getElementById("comOffCode").value;
            if(document.getElementById("txtverifyBy").value.length>0)
            {
                var url="../../../../../Receipt_SL.view?Command=loademp&eid="+eid+"&offid="+offid+"&Acc_UnitCode="+Acc_UnitCode;
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
            else if(Command=="loademp")
            {
                loadEmployee(baseResponse);
            }
            else if(Command=="fetch")
            {
                listRow(baseResponse);
            }
        }
    }
}


function loadEmployee(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  //alert("success");
        var eid=baseResponse.getElementsByTagName("eid")[0].firstChild.nodeValue;
        var ename=baseResponse.getElementsByTagName("ename")[0].firstChild.nodeValue;
        var desig=baseResponse.getElementsByTagName("desig")[0].firstChild.nodeValue;
        
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
        var branchId=baseResponse.getElementsByTagName("branchId")[0].firstChild.nodeValue;
        var bankAccNO=baseResponse.getElementsByTagName("bankAccNO")[0].firstChild.nodeValue;
        var micr_code=baseResponse.getElementsByTagName("micr_code")[0].firstChild.nodeValue;
        var bankName=baseResponse.getElementsByTagName("bankName")[0].firstChild.nodeValue;
        var final_addr=baseResponse.getElementsByTagName("final_addr")[0].firstChild.nodeValue;
        var NoOfLeaf=baseResponse.getElementsByTagName("NoOfLeaf")[0].firstChild.nodeValue;
        var PhyVerify=baseResponse.getElementsByTagName("PhyVerify")[0].firstChild.nodeValue;
        var verifiedID=baseResponse.getElementsByTagName("verifiedID")[0].firstChild.nodeValue;
        var verifiedbyname=baseResponse.getElementsByTagName("verifiedbyname")[0].firstChild.nodeValue;
        
        var StartLeaf=baseResponse.getElementsByTagName("StartLeaf")[0].firstChild.nodeValue;
        
        var EndLeaf=baseResponse.getElementsByTagName("EndLeaf")[0].firstChild.nodeValue;
        var verifyON=baseResponse.getElementsByTagName("verifyON")[0].firstChild.nodeValue;
        var DestDate=baseResponse.getElementsByTagName("DestDate")[0].firstChild.nodeValue;
        var chequeBookcode=baseResponse.getElementsByTagName("chequeBookcode")[0].firstChild.nodeValue;
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
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
//        try{
//        var f=DateFormat(t,c,event,true,'3');
//        }
         try{
             var f=isValidDate(c);
            
             }
        catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to 1970');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
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
         
            if(currentYear<1970)
            {
            
                    alert('Entered date should be greater than or equal to 1970');
                    t.value="";
                    t.focus();
                    return false;
           } 
           /*else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be greater than or equal to 1970');
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be greater than or equal to 1970');
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }*/
            
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
    
}
     
 /*
 
 



function  handleOutput(req)
{
if(req.readyState==4)
{
  if(req.status==200)
   {
    var i;
   var j;
   var first=document.getElementById("txtBankAc");
   first.innerHTML="";
   
   var sel=req.responseXML.getElementsByTagName("select")[0];
   
   var options=sel.getElementsByTagName("option");
   var htop=document.createElement("OPTION");
    htop.text="--Select--";
    try
    {
    first.add(htop);
    }
    catch(e)
    {
    first.add(htop,null);
    }
   for(i=0;i<options.length;i++)
   {
   
    var desc=options[i].getElementsByTagName("bankACNo")[0].firstChild.nodeValue;
   var id=options[i].getElementsByTagName("bankACNo")[0].firstChild.nodeValue;
   var htoption=document.createElement("OPTION");
   htoption.text=desc;
   htoption.value=id;
   try
   {
    first.add(htoption);
   }
   catch(e)
   {
     first.add(htoption,null);
   }
}

}
}
}

function  handleOutputList(req)
{
if(req.readyState==4)
{
  if(req.status==200)
   {
    var i;
   var j;
   var first=document.getElementById("txtBankAc");
   first.innerHTML="";
   
   var sel=req.responseXML.getElementsByTagName("select")[0];
   
   var options=sel.getElementsByTagName("option");
   var htop=document.createElement("OPTION");
    htop.text="--Select--";
    try
    {
    first.add(htop);
    }
    catch(e)
    {
    first.add(htop,null);
    }
   for(i=0;i<options.length;i++)
   {
   
    var desc=options[i].getElementsByTagName("bankACNo")[0].firstChild.nodeValue;
   var id=options[i].getElementsByTagName("bankACNo")[0].firstChild.nodeValue;
   var htoption=document.createElement("OPTION");
   htoption.text=desc;
   htoption.value=id;
   try
   {
    first.add(htoption);
   }
   catch(e)
   {
     first.add(htoption,null);
   }
}

}
}

}


function validate_date(formName,textName)
 {
                var errMsg="", lenErr=false, dateErr=false;
                var testObj=eval('document.' + formName + '.' + textName + '.value');
                var testStr=testObj.split('/');
                if(testStr.length>3 || testStr.length<3)
                {
                    lenErr=true;
                    dateErr=true;
                    errMsg+="There is an error in the date format.";
                }
                var monthsArr = new Array("01", "02", "03", "04", "05", "06", "07", "08" ,"09", "10", "11", "12");
                var daysArr = new Array;
                for (var i=0; i<12; i++)
                {
                    if(i!=1)
                    {
                       if((i/2)==(Math.round(i/2)))
                       {
                          if(i<=6)
                          {
                              daysArr[i]="31";
                           }
                           else
                           {
                               daysArr[i]="30";
                           }
                        }
                       else
                       {
                          if(i<=6)
                          {
                                daysArr[i]="30";
                          }
                          else
                          {
                               daysArr[i]="31";
                          }
                       }
                    }
                    else
                    {
                        if((testStr[2]/4)==(Math.round(testStr[2]/4)))
                        {
                            daysArr[i]="29";
                        }
                        else
                        {
                            daysArr[i]="28";
                        }
                    }
                } 
                var monthErr=false, yearErr=false;
                if(testStr[2]<1000 && !lenErr)
                {
                    yearErr=true;
                    dateErr=true;
                    errMsg+="\nThe year \"" + testStr[2] + "\" is not correct.";
                }
                for(var i=0; i<12; i++)
                {
                    if(testStr[1]==monthsArr[i])
                    {
                      var setMonth=i;
                      break;
                    }
                }
                if(!lenErr && (setMonth==undefined))
                {
                    monthErr=true;
                    errMsg+="\nThe month \"" + testStr[1] + "\" is not correct.";
                    dateErr=true;
                }
                if(!monthErr && !yearErr && !lenErr)
                {
                    if(testStr[0]>daysArr[setMonth])
                    {
                        errMsg+=testStr[1] + ' ' + testStr[2] + ' does not have ' + testStr[0] + ' days.';
                        dateErr=true;
                    }
                }
                if(!dateErr)
                {
                    //eval('document.' + formName + '.' + 'submit()');
                }
                else
                {
                    alert(errMsg + '\n____________________________\n\nSample Date Format :\n dd/MM/yyyy');
                    eval('document.' + formName + '.' + textName + '.focus()');
                    //alert(eval);
                    eval('document.' + formName + '.' + textName + '.select()');
                    
                    return false;
                }
                
                 return true;  
                     
 }
 
 
 
 
  else if(command=="bankAC")
        {
        var Acc_UnitCode=document.chequeForm.cmbAcc_UnitCode.value;
        var url="../../../../../ChequeBookServ.view?Command=bankAC&cmbAcc_UnitCode="+Acc_UnitCode;
                  // alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleOutput(req);
                    }   
                            req.send(null);
        }
        
        
        else if(command=="bankDetails")
        {
        var url="../../../../../ChequeBookServ.view?Command=bankDetails&cmbAcc_UnitCode="+Acc_UnitCode;
                   // alert(url);
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       //handleOutputList(req);
                       handleResponse(req);
                    }   
                            req.send(null);
        }
        
        else if(command=="ForList")
        {
        var hiddenAccount=document.chequeForm.txthiddenAccount.value;
        var url="../../../../../ChequeBookServ.view?Command=bankAC&cmbAcc_UnitCode="+Acc_UnitCode;
                   // alert(url);
                    var req=getTransport();
                    req.open("GET",url,false); 
                    req.onreadystatechange=function()
                    {
                       handleOutputList(req);
                    }   
                            req.send(null);
                            
               document.chequeForm.txtBankAc.value=hiddenAccount;             
                         
        }
        
        
          else if(Command=="bankDetails")
            {
                bankDetailsRow(baseResponse);
            }
            else if(Command=="ForList")
            {
                ForListRow(baseResponse);
            }
 */