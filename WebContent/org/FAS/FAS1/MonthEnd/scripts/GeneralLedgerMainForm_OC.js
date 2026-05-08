var common_cmbSL_Code="";
var common_cmbSL_type="";
var job_flag;



/* 
 *  Browser Indentification 
 */

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



var winAccHeadCode;

function AccHeadpopup()
{
    if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
    {
       winAccHeadCode.resizeTo(500,500);
       winAccHeadCode.moveTo(250,250); 
       winAccHeadCode.focus();
    }
    else
    {
        winAccHeadCode=null
    }
        
    winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winAccHeadCode.moveTo(250,250);  
    winAccHeadCode.focus();
    
}

function doParentAccHead(code)
{
   document.getElementById("cmbAcHeadCode").value=code;
   doFunction('checkCode','null');
   return true;
}
window.onunload=function()
{
if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) winAccHeadCode.close();
if (listPopupwindow && listPopupwindow.open && !listPopupwindow.closed) listPopupwindow.close();
}
function clearall()
{
       
        document.frmSubLedgerSystem.cmbAcHeadCode.value="";
      
     
        document.frmSubLedgerSystem.cmbAcHeadCode.readOnly=false;
        
     
       document.frmSubLedgerSystem.txtCloseBal.value="";
      
       document.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked=true;
       document.frmSubLedgerSystem.txtaccountheadname.value="";
     var d=document.getElementById("cmdAdd");
     d.style.display="block";
    
     var d1=document.getElementById("cmdUpdate");
     d1.style.display="none";
    
    var d2=document.getElementById("cmdDelete");
    d2.style.display="none";
    
   
}

function sixdigit()
{
 if(document.getElementById("cmbAcHeadCode").value.length!=0)
    {
        if((document.getElementById("cmbAcHeadCode").value).length<6)
        {
        alert("Account Head Code shouldn't less than 6 digit number");
        document.getElementById("cmbAcHeadCode").focus();
        return false;
        }
    }
}


/**
 *  Number Checking --1 
 */
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


/**
 *  Number Checking --2 
 */

function numbersonly(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode;
       
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<48||unicode>57 ) 
                return false 
        }
     }
     
     
     
     
function nullcheck()
{
   
    if(document.frmSubLedgerSystem.txtCB_Year.value.length==0)
    {
        alert("Enter Cashbook Year");
        document.frmSubLedgerSystem.cmbAcHeadCode.focus();
        return false;
    }
    
    if(document.frmSubLedgerSystem.cmbAcHeadCode.value.length==0)
    {
        alert("Enter the Account Head Code");
        document.frmSubLedgerSystem.cmbAcHeadCode.focus();
        return false;
    }    
    return true;
    
}




/**
 *  List Function 
 */
var listPopupwindow;
function ListAll()
{
        if(document.frmSubLedgerSystem.txtCB_Year.value.length==0)
        {
           alert("Enter Cashbook Year");
           document.frmSubLedgerSystem.cmbAcHeadCode.focus();
           return false;
        }
   
        var Acc_UnitCode=document.frmSubLedgerSystem.cmbAcc_UnitCode.value;
        var OffCode=document.frmSubLedgerSystem.cmbOffice_code.value;       
        var CashbookYear=document.frmSubLedgerSystem.txtCB_Year.value;
        var CashbookMonth=document.frmSubLedgerSystem.txtCB_Month.value;
        listPopupwindow= window.open("ListofGeneralLedgerMainForm_OC.jsp?cmbAcc_UnitCode="+Acc_UnitCode+"&cmbOffice_code="+OffCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth,"mywindow1","status=1,height=400,width=500,resizable=YES, scrollbars=yes"); 
        listPopupwindow.moveTo(250,250);       
        document.frmSubLedgerSystem.txtCB_Month.disabled=true;
        
}



function doFunction(Command,param)
{  
    var Acc_UnitCode=document.frmSubLedgerSystem.cmbAcc_UnitCode.value;
    var OffCode=document.frmSubLedgerSystem.cmbOffice_code.value;  
  
    var AcHeadCode=document.frmSubLedgerSystem.cmbAcHeadCode.value;
  
    var CloseBal=document.frmSubLedgerSystem.txtCloseBal.value;
    var CloseBalInd;
    if(document.frmSubLedgerSystem.radCloseBalCrDrInd[0].checked==true)
    {
        CloseBalInd=document.frmSubLedgerSystem.radCloseBalCrDrInd[0].value;
    }
    else
    {
        CloseBalInd=document.frmSubLedgerSystem.radCloseBalCrDrInd[1].value;
    }
    
  
    var CashbookYear=document.frmSubLedgerSystem.txtCB_Year.value;
    var CashbookMonth=document.frmSubLedgerSystem.txtCB_Month.value;
  
        if(Command=="office")
        {   
            var oid=param;
            var url="../../../../../SubsidiaryLedgerServlet.con?Command=office&oid="+oid;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    req.send(null);
        }
        if(Command=="checkCode")
        {
            
            var headcode=document.frmSubLedgerSystem.cmbAcHeadCode.value;
            url="../../../../../GeneralLedgerMainFormServlet_OC.con?Command=HeadCode&txtAcc_Head_code="+headcode+"&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode;
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               headcodeResponse(req);
            }   
                    req.send(null);
          // CheckGL();       
        }
        
        if(Command=="Add")
        {
            if(nullcheck())
            {
                var url="../../../../../GeneralLedgerMainFormServlet_OC.con?Command=Add&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&cmbAcHeadCode="+AcHeadCode+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
                var req=getTransport();
                req.open("GET",url,true); 
                req.onreadystatechange=function()
                {
                   handleResponse(req);
                }   
                        req.send(null);
            }
    }
    else if(Command=="Update")
    {
               
              //  if(CheckGL())
                {
                    var url="../../../../../GeneralLedgerMainFormServlet_OC.con?Command=Update&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&cmbAcHeadCode="+AcHeadCode+"&CloseBal="+CloseBal+"&CloseBalInd="+CloseBalInd+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
                    
                 //   alert(url);
                    
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);                 
                }
    }
    else if(Command=="Delete")
    {
        if(confirm("Do u really want to delete the record"))
        {
                if(nullcheck())
                {
                    var url="../../../../../GeneralLedgerMainFormServlet_OC.con?Command=Delete&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&cmbAcHeadCode="+AcHeadCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
                    var req=getTransport();
                    req.open("GET",url,true); 
                    req.onreadystatechange=function()
                    {
                       handleResponse(req);
                    }   
                            req.send(null);
                }
         }
         else
         {
            alert("Record not Deleted");
         }
    }
    if(Command=="SubLedgerReturn")
        {
            var headcode=document.frmSubLedgerSystem.cmbAcHeadCode.value;
            url="../../../../../BudgetMasterServlet.con?Command=HeadCode&txtAcc_Head_code="+headcode;            
            var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
               SubLedgerReturnResponse(req);
            }   
                    req.send(null);
        }

    
}


function CheckGL()
{
        var Acc_UnitCode=document.frmSubLedgerSystem.cmbAcc_UnitCode.value;
        var OffCode=document.frmSubLedgerSystem.cmbOffice_code.value;        
        var CashbookYear=document.frmSubLedgerSystem.txtCB_Year.value;
        var CashbookMonth=document.frmSubLedgerSystem.txtCB_Month.value;
        url="../../../../../GeneralLedgerMainFormServlet_OC.con?Command=CheckGL&cmbAcc_UnitCode="+Acc_UnitCode+"&comOffCode="+OffCode+"&CashbookYear="+CashbookYear+"&CashbookMonth="+CashbookMonth;
        alert(url);
        var req=getTransport();
        req.open("GET",url,true); 
        req.onreadystatechange=function()
            {
               CheckGLResponse(req);
            }   
               req.send(null);
}


function headcodeResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];            
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;            
            if(flag=="success")
            {
                var headname=baseResponse.getElementsByTagName("hdesc")[0].firstChild.nodeValue;
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value=headname;                               
                
            }
            else
            {
              
                document.frmSubLedgerSystem.cmbAcHeadCode.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmSubLedgerSystem.cmbAcHeadCode.focus();
                
                
            }
        }
        
    }

}



function SubLedgerReturnResponse(req)
{

    if(req.readyState==4)
    {
        if(req.status==200)
        {  
            
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
            
            var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
            
            if(flag=="success")
            {
                var headname=baseResponse.getElementsByTagName("headcode")[0].firstChild.nodeValue;
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value=headname;
            }
            else
            {
               
                document.frmSubLedgerSystem.cmbAcHeadCode.value="";
                document.frmSubLedgerSystem.txtaccountheadname.value="";
                alert("Invalid HeadCode");
                document.frmSubLedgerSystem.cmbAcHeadCode.focus();
            }
        }
    }
}


function CheckGLResponse(req)
{
    var baseResponse=req.responseXML.getElementsByTagName("response")[0];
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("GL Already Freezed");
         clearall();
    }
    else if(flag=="failure")
    {
        alert("Not yet freezed GL ");
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
            if(Command=="office")
            {
                loadOffice(baseResponse);
            }
            else if(Command=="Add")
            {
                addRow(baseResponse);
            }
            else if(Command=="Update")
            {
                updateRow(baseResponse);
            }
            else if(Command=="Delete")
            {
                deleteRow(baseResponse);
            }
            /////////////////////////////////////////////  For MASTER Combo SL Code //////////////////////////////////
         }
    }
}

function addRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record inserted successfully");
         clearall();
    }
    else if(flag=="failure")
    {
        alert("Records not inserted");
    }
    if(flag=="AlreadyExist")
    {
        alert("Account Head Code Already Exist");
        
    }
}
function updateRow(baseResponse)
{

 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record updated successfully");
         document.frmSubLedgerSystem.txtCB_Year.disabled=false;
     document.frmSubLedgerSystem.txtCB_Month.disabled=false;
         clearall();
    }
    else
    {
        alert("Record not updated");
    }
}


function deleteRow(baseResponse)
{
 var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=="success")
    {   
         alert("Record deleted successfully");
         clearall();
    }
    else
    {
        alert("Record not deleted");
    }

}



function loadOffice(baseResponse)
{
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    
    if(flag=="success")
    {  
       var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
       var oname=baseResponse.getElementsByTagName("oname")[0].firstChild.nodeValue;
       if(job_flag==true)
       if(job_flag==false)
            var option=document.createElement("OPTION");
            option.text=oname;
            option.value=oid;
           
    }
    else 
    {
     var oid=baseResponse.getElementsByTagName("oid")[0].firstChild.nodeValue;
     alert("Office Id '"+oid+"' doesn't Exist");
    }
    job_flag="";
}





function jobpopup_master()
{
    job_flag=true;
    jobpopup();
}



var winjob;
function jobpopup()
{
    if(winjob && winjob.open && !winjob.closed) 
    {
       winjob.resizeTo(500,500);
       winjob.moveTo(250,250); 
       winjob.focus();
    }
    else
    {
        winjob=null
    }
        
    winjob= window.open("../../../../../org/HR/HR1/OfficeMaster/jsps/JobPopupJSP.jsp","JobSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    winjob.moveTo(250,250);  
    winjob.focus();
}




function forChildOption()
{
      if (winjob && winjob.open && !winjob.closed) 
             winjob.officeSelection(true,true,true,false);
}



function doParentJob(jobid,deptid)
{
       if(deptid=='TWAD')
        {
            doFunction('office',jobid);
        }
        else
        {
                alert('Please select an Office ');
                if (winjob && winjob.open && !winjob.closed) 
                {
                   winjob.resizeTo(500,500);
                   winjob.moveTo(250,250); 
                   winjob.focus();
                }
                return false
        }
   
    return true
}


function valid_amt(field)
{
    
    amt=field.value;
    if(amt.indexOf(".")!=amt.lastIndexOf("."))
    {
        alert("Enter a Valid Amount");
        field.value="";
        field.focus();
    }
}



function limit_amt(field,e)
{
      var unicode=e.charCode? e.charCode : e.keyCode;
      if(field.value.length<17)
      {
        if(field.value.length==14 && field.value.indexOf('.')==-1  )
        field.value=field.value+'.';
        if (unicode!=8 && unicode !=9  )
        {
            if (unicode<46 || unicode==47 || unicode>57   ) 
                return false 
        }
      }
      else 
      return false;
      
}


/**
 *  Exit Function 
 */

function Exit()
 {
    window.open('','_parent','');
    window.close();
 }

function loadAccountOffice(){
	var req = getTransport();
	if (req == null) {
		alert("Your borwser doesnot support AJAX");
		return;
	}
	var accOff = document.getElementById("cmbAcc_UnitCode").value;
	//alert("accOff "+accOff);
	if (accOff == "select") {
	}else{
		url = "../../../../../ChequeBookServ.view?Command=loadAccOff" + "&accOff="+accOff;		
		req.open("GET", url, true);
		req.onreadystatechange = function() {
			loadAccountHeadCode(req);
		};
		req.send(null);
	}
}
function loadAccountHeadCode(req){
	if (req.readyState == 4) {
		if (req.status == 200) {
			//alert("in added");
			response = req.responseXML.getElementsByTagName("response")[0];
			viewAccountHeadCode();
		}
	}
}
function viewAccountHeadCode(){
	var command = response.getElementsByTagName("command")[0].firstChild.nodeValue;
	var flag = response.getElementsByTagName("status")[0].firstChild.nodeValue;
	if(command=="getaccoff"){
		if(flag=="success"){
			var len=response.getElementsByTagName("ACCOUNTHEADCODE").length;
			var selectdiv=document.getElementById('cmbOffice_code');
			var listOpt=document.createElement("option");
			selectdiv.length=0;
			selectdiv.appendChild(listOpt);
			listOpt.text="select";
			listOpt.value="select";
			for(var i=0; i<len; i++){
				listOpt=document.createElement("option");
				selectdiv.appendChild(listOpt);
				listOpt.text=response.getElementsByTagName("ACCOUNTHEADNAME")[i].firstChild.nodeValue;
				listOpt.value=response.getElementsByTagName("ACCOUNTHEADCODE")[i].firstChild.nodeValue;
			}
			document.getElementById('cmbOffice_code').selectedIndex=1;
		}else{
			alert("There is no accounting for office code for this accounting unit code");
		}
	}else{
		alert("Process Failure");
	}
	
}